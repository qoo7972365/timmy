/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*      */ import com.adventnet.appmanager.cam.CAMJMXUtil;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
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
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class cam_005fshowmbeans_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   50 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   53 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   54 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   55 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   62 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   67 */     ArrayList list = null;
/*   68 */     StringBuffer sbf = new StringBuffer();
/*   69 */     ManagedApplication mo = new ManagedApplication();
/*   70 */     if (distinct)
/*      */     {
/*   72 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   76 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   79 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   81 */       ArrayList row = (ArrayList)list.get(i);
/*   82 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   83 */       if (distinct) {
/*   84 */         sbf.append(row.get(0));
/*      */       } else
/*   86 */         sbf.append(row.get(1));
/*   87 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   90 */     return sbf.toString(); }
/*      */   
/*   92 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   95 */     if (severity == null)
/*      */     {
/*   97 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   99 */     if (severity.equals("5"))
/*      */     {
/*  101 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  103 */     if (severity.equals("1"))
/*      */     {
/*  105 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  110 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  117 */     if (severity == null)
/*      */     {
/*  119 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  121 */     if (severity.equals("1"))
/*      */     {
/*  123 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  125 */     if (severity.equals("4"))
/*      */     {
/*  127 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  129 */     if (severity.equals("5"))
/*      */     {
/*  131 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  136 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  142 */     if (severity == null)
/*      */     {
/*  144 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  146 */     if (severity.equals("5"))
/*      */     {
/*  148 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  150 */     if (severity.equals("1"))
/*      */     {
/*  152 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  156 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  162 */     if (severity == null)
/*      */     {
/*  164 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  166 */     if (severity.equals("1"))
/*      */     {
/*  168 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  170 */     if (severity.equals("4"))
/*      */     {
/*  172 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  174 */     if (severity.equals("5"))
/*      */     {
/*  176 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  180 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  186 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  192 */     if (severity == 5)
/*      */     {
/*  194 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  196 */     if (severity == 1)
/*      */     {
/*  198 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  203 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  209 */     if (severity == null)
/*      */     {
/*  211 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  213 */     if (severity.equals("5"))
/*      */     {
/*  215 */       if (isAvailability) {
/*  216 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  219 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  222 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  224 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  226 */     if (severity.equals("1"))
/*      */     {
/*  228 */       if (isAvailability) {
/*  229 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  232 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  239 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  246 */     if (severity == null)
/*      */     {
/*  248 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  250 */     if (severity.equals("5"))
/*      */     {
/*  252 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  254 */     if (severity.equals("4"))
/*      */     {
/*  256 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  258 */     if (severity.equals("1"))
/*      */     {
/*  260 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  265 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  271 */     if (severity == null)
/*      */     {
/*  273 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  275 */     if (severity.equals("5"))
/*      */     {
/*  277 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  279 */     if (severity.equals("4"))
/*      */     {
/*  281 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  283 */     if (severity.equals("1"))
/*      */     {
/*  285 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  290 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  297 */     if (severity == null)
/*      */     {
/*  299 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  301 */     if (severity.equals("5"))
/*      */     {
/*  303 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  305 */     if (severity.equals("4"))
/*      */     {
/*  307 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  309 */     if (severity.equals("1"))
/*      */     {
/*  311 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  316 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  324 */     StringBuffer out = new StringBuffer();
/*  325 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  326 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  327 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  328 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  329 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  330 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  331 */     out.append("</tr>");
/*  332 */     out.append("</form></table>");
/*  333 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  340 */     if (val == null)
/*      */     {
/*  342 */       return "-";
/*      */     }
/*      */     
/*  345 */     String ret = FormatUtil.formatNumber(val);
/*  346 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  347 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  350 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  354 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  362 */     StringBuffer out = new StringBuffer();
/*  363 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  364 */     out.append("<tr>");
/*  365 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  367 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  369 */     out.append("</tr>");
/*  370 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  374 */       if (j % 2 == 0)
/*      */       {
/*  376 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  380 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  383 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  385 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  388 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  392 */       out.append("</tr>");
/*      */     }
/*  394 */     out.append("</table>");
/*  395 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  396 */     out.append("<tr>");
/*  397 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  398 */     out.append("</tr>");
/*  399 */     out.append("</table>");
/*  400 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  406 */     StringBuffer out = new StringBuffer();
/*  407 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  408 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  409 */     out.append("<tr>");
/*  410 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  411 */     out.append("<tr>");
/*  412 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  413 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  414 */     out.append("</tr>");
/*  415 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  418 */       out.append("<tr>");
/*  419 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  420 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  421 */       out.append("</tr>");
/*      */     }
/*      */     
/*  424 */     out.append("</table>");
/*  425 */     out.append("</table>");
/*  426 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  431 */     if (severity.equals("0"))
/*      */     {
/*  433 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  437 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  444 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  457 */     StringBuffer out = new StringBuffer();
/*  458 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  459 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  461 */       out.append("<tr>");
/*  462 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  463 */       out.append("</tr>");
/*      */       
/*      */ 
/*  466 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  468 */         String borderclass = "";
/*      */         
/*      */ 
/*  471 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  473 */         out.append("<tr>");
/*      */         
/*  475 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  476 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  477 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  483 */     out.append("</table><br>");
/*  484 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  485 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  487 */       List sLinks = secondLevelOfLinks[0];
/*  488 */       List sText = secondLevelOfLinks[1];
/*  489 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  492 */         out.append("<tr>");
/*  493 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  494 */         out.append("</tr>");
/*  495 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  497 */           String borderclass = "";
/*      */           
/*      */ 
/*  500 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  502 */           out.append("<tr>");
/*      */           
/*  504 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  505 */           if (sLinks.get(i).toString().length() == 0) {
/*  506 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  509 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  511 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  515 */     out.append("</table>");
/*  516 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  523 */     StringBuffer out = new StringBuffer();
/*  524 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  525 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  527 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  529 */         out.append("<tr>");
/*  530 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  531 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  535 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  537 */           String borderclass = "";
/*      */           
/*      */ 
/*  540 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  542 */           out.append("<tr>");
/*      */           
/*  544 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  545 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  546 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  549 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  552 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  557 */     out.append("</table><br>");
/*  558 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  559 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  561 */       List sLinks = secondLevelOfLinks[0];
/*  562 */       List sText = secondLevelOfLinks[1];
/*  563 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  566 */         out.append("<tr>");
/*  567 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  568 */         out.append("</tr>");
/*  569 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  571 */           String borderclass = "";
/*      */           
/*      */ 
/*  574 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  576 */           out.append("<tr>");
/*      */           
/*  578 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  579 */           if (sLinks.get(i).toString().length() == 0) {
/*  580 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  583 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  585 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  589 */     out.append("</table>");
/*  590 */     return out.toString();
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
/*  603 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  606 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  609 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  612 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  615 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  618 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  621 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  624 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  632 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  637 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  642 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  647 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  652 */     if (val != null)
/*      */     {
/*  654 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  658 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  663 */     if (val == null) {
/*  664 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  668 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  673 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  679 */     if (val != null)
/*      */     {
/*  681 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  685 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  691 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  696 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  700 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  705 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  710 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  715 */     String hostaddress = "";
/*  716 */     String ip = request.getHeader("x-forwarded-for");
/*  717 */     if (ip == null)
/*  718 */       ip = request.getRemoteAddr();
/*  719 */     InetAddress add = null;
/*  720 */     if (ip.equals("127.0.0.1")) {
/*  721 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  725 */       add = InetAddress.getByName(ip);
/*      */     }
/*  727 */     hostaddress = add.getHostName();
/*  728 */     if (hostaddress.indexOf('.') != -1) {
/*  729 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  730 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  734 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  739 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  745 */     if (severity == null)
/*      */     {
/*  747 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  749 */     if (severity.equals("5"))
/*      */     {
/*  751 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  753 */     if (severity.equals("1"))
/*      */     {
/*  755 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  760 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  765 */     ResultSet set = null;
/*  766 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  767 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  769 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  770 */       if (set.next()) { String str1;
/*  771 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  772 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  775 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  780 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  783 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  785 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  789 */     StringBuffer rca = new StringBuffer();
/*  790 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  791 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  794 */     int rcalength = key.length();
/*  795 */     String split = "6. ";
/*  796 */     int splitPresent = key.indexOf(split);
/*  797 */     String div1 = "";String div2 = "";
/*  798 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  800 */       if (rcalength > 180) {
/*  801 */         rca.append("<span class=\"rca-critical-text\">");
/*  802 */         getRCATrimmedText(key, rca);
/*  803 */         rca.append("</span>");
/*      */       } else {
/*  805 */         rca.append("<span class=\"rca-critical-text\">");
/*  806 */         rca.append(key);
/*  807 */         rca.append("</span>");
/*      */       }
/*  809 */       return rca.toString();
/*      */     }
/*  811 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  812 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  813 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  814 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  815 */     getRCATrimmedText(div1, rca);
/*  816 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  819 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  820 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  821 */     getRCATrimmedText(div2, rca);
/*  822 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  824 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  829 */     String[] st = msg.split("<br>");
/*  830 */     for (int i = 0; i < st.length; i++) {
/*  831 */       String s = st[i];
/*  832 */       if (s.length() > 180) {
/*  833 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  835 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  839 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  840 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  842 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  846 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  847 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  848 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  851 */       if (key == null) {
/*  852 */         return ret;
/*      */       }
/*      */       
/*  855 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  856 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  859 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  860 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  861 */       set = AMConnectionPool.executeQueryStmt(query);
/*  862 */       if (set.next())
/*      */       {
/*  864 */         String helpLink = set.getString("LINK");
/*  865 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  868 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  874 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  893 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  884 */         if (set != null) {
/*  885 */           AMConnectionPool.closeStatement(set);
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
/*  899 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  900 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  902 */       String entityStr = (String)keys.nextElement();
/*  903 */       String mmessage = temp.getProperty(entityStr);
/*  904 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  905 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  907 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  913 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  914 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  916 */       String entityStr = (String)keys.nextElement();
/*  917 */       String mmessage = temp.getProperty(entityStr);
/*  918 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  919 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  921 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  926 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  936 */     String des = new String();
/*  937 */     while (str.indexOf(find) != -1) {
/*  938 */       des = des + str.substring(0, str.indexOf(find));
/*  939 */       des = des + replace;
/*  940 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  942 */     des = des + str;
/*  943 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  950 */       if (alert == null)
/*      */       {
/*  952 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  954 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  956 */         return "&nbsp;";
/*      */       }
/*      */       
/*  959 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  961 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  964 */       int rcalength = test.length();
/*  965 */       if (rcalength < 300)
/*      */       {
/*  967 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  971 */       StringBuffer out = new StringBuffer();
/*  972 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  973 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  974 */       out.append("</div>");
/*  975 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  976 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  977 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  982 */       ex.printStackTrace();
/*      */     }
/*  984 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  990 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  995 */     ArrayList attribIDs = new ArrayList();
/*  996 */     ArrayList resIDs = new ArrayList();
/*  997 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  999 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1001 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1003 */       String resourceid = "";
/* 1004 */       String resourceType = "";
/* 1005 */       if (type == 2) {
/* 1006 */         resourceid = (String)row.get(0);
/* 1007 */         resourceType = (String)row.get(3);
/*      */       }
/* 1009 */       else if (type == 3) {
/* 1010 */         resourceid = (String)row.get(0);
/* 1011 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1014 */         resourceid = (String)row.get(6);
/* 1015 */         resourceType = (String)row.get(7);
/*      */       }
/* 1017 */       resIDs.add(resourceid);
/* 1018 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1019 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1021 */       String healthentity = null;
/* 1022 */       String availentity = null;
/* 1023 */       if (healthid != null) {
/* 1024 */         healthentity = resourceid + "_" + healthid;
/* 1025 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1028 */       if (availid != null) {
/* 1029 */         availentity = resourceid + "_" + availid;
/* 1030 */         entitylist.add(availentity);
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
/* 1044 */     Properties alert = getStatus(entitylist);
/* 1045 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1050 */     int size = monitorList.size();
/*      */     
/* 1052 */     String[] severity = new String[size];
/*      */     
/* 1054 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1056 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1057 */       String resourceName1 = (String)row1.get(7);
/* 1058 */       String resourceid1 = (String)row1.get(6);
/* 1059 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1060 */       if (severity[j] == null)
/*      */       {
/* 1062 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1066 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1068 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1070 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1073 */         if (sev > 0) {
/* 1074 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1075 */           monitorList.set(k, monitorList.get(j));
/* 1076 */           monitorList.set(j, t);
/* 1077 */           String temp = severity[k];
/* 1078 */           severity[k] = severity[j];
/* 1079 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1085 */     int z = 0;
/* 1086 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1089 */       int i = 0;
/* 1090 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1093 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1097 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1101 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1103 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1106 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1110 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1113 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1114 */       String resourceName1 = (String)row1.get(7);
/* 1115 */       String resourceid1 = (String)row1.get(6);
/* 1116 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1117 */       if (hseverity[j] == null)
/*      */       {
/* 1119 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1124 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1126 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1129 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1132 */         if (hsev > 0) {
/* 1133 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1134 */           monitorList.set(k, monitorList.get(j));
/* 1135 */           monitorList.set(j, t);
/* 1136 */           String temp1 = hseverity[k];
/* 1137 */           hseverity[k] = hseverity[j];
/* 1138 */           hseverity[j] = temp1;
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
/* 1150 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1151 */     boolean forInventory = false;
/* 1152 */     String trdisplay = "none";
/* 1153 */     String plusstyle = "inline";
/* 1154 */     String minusstyle = "none";
/* 1155 */     String haidTopLevel = "";
/* 1156 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1158 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1160 */         haidTopLevel = request.getParameter("haid");
/* 1161 */         forInventory = true;
/* 1162 */         trdisplay = "table-row;";
/* 1163 */         plusstyle = "none";
/* 1164 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1171 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1174 */     ArrayList listtoreturn = new ArrayList();
/* 1175 */     StringBuffer toreturn = new StringBuffer();
/* 1176 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1177 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1178 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1180 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1182 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1183 */       String childresid = (String)singlerow.get(0);
/* 1184 */       String childresname = (String)singlerow.get(1);
/* 1185 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1186 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1187 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1188 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1189 */       String unmanagestatus = (String)singlerow.get(5);
/* 1190 */       String actionstatus = (String)singlerow.get(6);
/* 1191 */       String linkclass = "monitorgp-links";
/* 1192 */       String titleforres = childresname;
/* 1193 */       String titilechildresname = childresname;
/* 1194 */       String childimg = "/images/trcont.png";
/* 1195 */       String flag = "enable";
/* 1196 */       String dcstarted = (String)singlerow.get(8);
/* 1197 */       String configMonitor = "";
/* 1198 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1199 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1201 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1203 */       if (singlerow.get(7) != null)
/*      */       {
/* 1205 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1207 */       String haiGroupType = "0";
/* 1208 */       if ("HAI".equals(childtype))
/*      */       {
/* 1210 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1212 */       childimg = "/images/trend.png";
/* 1213 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1214 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1215 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1217 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1219 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1221 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1222 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1225 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1227 */         linkclass = "disabledtext";
/* 1228 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1230 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1231 */       String availmouseover = "";
/* 1232 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1234 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1236 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1237 */       String healthmouseover = "";
/* 1238 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1240 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1243 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1244 */       int spacing = 0;
/* 1245 */       if (level >= 1)
/*      */       {
/* 1247 */         spacing = 40 * level;
/*      */       }
/* 1249 */       if (childtype.equals("HAI"))
/*      */       {
/* 1251 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1252 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1253 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1255 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1256 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1257 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1258 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1259 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1260 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1261 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1262 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1263 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1264 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1265 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1267 */         if (!forInventory)
/*      */         {
/* 1269 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1272 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1274 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1276 */           actions = editlink + actions;
/*      */         }
/* 1278 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1280 */           actions = actions + associatelink;
/*      */         }
/* 1282 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1283 */         String arrowimg = "";
/* 1284 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1286 */           actions = "";
/* 1287 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1288 */           checkbox = "";
/* 1289 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1291 */         if (isIt360)
/*      */         {
/* 1293 */           actionimg = "";
/* 1294 */           actions = "";
/* 1295 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1296 */           checkbox = "";
/*      */         }
/*      */         
/* 1299 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1301 */           actions = "";
/*      */         }
/* 1303 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1305 */           checkbox = "";
/*      */         }
/*      */         
/* 1308 */         String resourcelink = "";
/*      */         
/* 1310 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1312 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1316 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1319 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1320 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1321 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1322 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1323 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1324 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1325 */         if (!isIt360)
/*      */         {
/* 1327 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1331 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1334 */         toreturn.append("</tr>");
/* 1335 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1337 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1338 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1342 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1343 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1346 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1350 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1352 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1353 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1354 */             toreturn.append(assocMessage);
/* 1355 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1356 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1357 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1358 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1364 */         String resourcelink = null;
/* 1365 */         boolean hideEditLink = false;
/* 1366 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1368 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1369 */           hideEditLink = true;
/* 1370 */           if (isIt360)
/*      */           {
/* 1372 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1376 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1378 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1380 */           hideEditLink = true;
/* 1381 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1382 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1387 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1390 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1391 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1392 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1393 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1394 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1395 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1396 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1397 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1398 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1399 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1400 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1401 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1402 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1404 */         if (hideEditLink)
/*      */         {
/* 1406 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1408 */         if (!forInventory)
/*      */         {
/* 1410 */           removefromgroup = "";
/*      */         }
/* 1412 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1413 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1414 */           actions = actions + configcustomfields;
/*      */         }
/* 1416 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1418 */           actions = editlink + actions;
/*      */         }
/* 1420 */         String managedLink = "";
/* 1421 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1423 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1424 */           actions = "";
/* 1425 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1426 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1429 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1431 */           checkbox = "";
/*      */         }
/*      */         
/* 1434 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1436 */           actions = "";
/*      */         }
/* 1438 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1439 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1440 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1441 */         if (isIt360)
/*      */         {
/* 1443 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1447 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1449 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1450 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1451 */         if (!isIt360)
/*      */         {
/* 1453 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1457 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1459 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1462 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1469 */       StringBuilder toreturn = new StringBuilder();
/* 1470 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1471 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1472 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1473 */       String title = "";
/* 1474 */       message = EnterpriseUtil.decodeString(message);
/* 1475 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1476 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1477 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1479 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1481 */       else if ("5".equals(severity))
/*      */       {
/* 1483 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1487 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1489 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1490 */       toreturn.append(v);
/*      */       
/* 1492 */       toreturn.append(link);
/* 1493 */       if (severity == null)
/*      */       {
/* 1495 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1497 */       else if (severity.equals("5"))
/*      */       {
/* 1499 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1501 */       else if (severity.equals("4"))
/*      */       {
/* 1503 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1505 */       else if (severity.equals("1"))
/*      */       {
/* 1507 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1512 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1514 */       toreturn.append("</a>");
/* 1515 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1519 */       ex.printStackTrace();
/*      */     }
/* 1521 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1528 */       StringBuilder toreturn = new StringBuilder();
/* 1529 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1530 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1531 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1532 */       if (message == null)
/*      */       {
/* 1534 */         message = "";
/*      */       }
/*      */       
/* 1537 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1538 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1540 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1541 */       toreturn.append(v);
/*      */       
/* 1543 */       toreturn.append(link);
/*      */       
/* 1545 */       if (severity == null)
/*      */       {
/* 1547 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1549 */       else if (severity.equals("5"))
/*      */       {
/* 1551 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1553 */       else if (severity.equals("1"))
/*      */       {
/* 1555 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1560 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1562 */       toreturn.append("</a>");
/* 1563 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1569 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1572 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1573 */     if (invokeActions != null) {
/* 1574 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1575 */       while (iterator.hasNext()) {
/* 1576 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1577 */         if (actionmap.containsKey(actionid)) {
/* 1578 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1583 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1587 */     String actionLink = "";
/* 1588 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1589 */     String query = "";
/* 1590 */     ResultSet rs = null;
/* 1591 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1592 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1593 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1594 */       actionLink = "method=" + methodName;
/*      */     }
/* 1596 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1597 */       actionLink = methodName;
/*      */     }
/* 1599 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1600 */     Iterator itr = methodarglist.iterator();
/* 1601 */     boolean isfirstparam = true;
/* 1602 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1603 */     while (itr.hasNext()) {
/* 1604 */       HashMap argmap = (HashMap)itr.next();
/* 1605 */       String argtype = (String)argmap.get("TYPE");
/* 1606 */       String argname = (String)argmap.get("IDENTITY");
/* 1607 */       String paramname = (String)argmap.get("PARAMETER");
/* 1608 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1609 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1610 */         isfirstparam = false;
/* 1611 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1613 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1617 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1621 */         actionLink = actionLink + "&";
/*      */       }
/* 1623 */       String paramValue = null;
/* 1624 */       String tempargname = argname;
/* 1625 */       if (commonValues.getProperty(tempargname) != null) {
/* 1626 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1629 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1630 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1631 */           if (dbType.equals("mysql")) {
/* 1632 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1635 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1637 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1639 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1640 */             if (rs.next()) {
/* 1641 */               paramValue = rs.getString("VALUE");
/* 1642 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1646 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1650 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1653 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1658 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1659 */           paramValue = rowId;
/*      */         }
/* 1661 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1662 */           paramValue = managedObjectName;
/*      */         }
/* 1664 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1665 */           paramValue = resID;
/*      */         }
/* 1667 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1668 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1671 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1673 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1674 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1675 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1677 */     return actionLink;
/*      */   }
/*      */   
/* 1680 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1681 */     String dependentAttribute = null;
/* 1682 */     String align = "left";
/*      */     
/* 1684 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1685 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1686 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1687 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1688 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1689 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1690 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1691 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1692 */       align = "center";
/*      */     }
/*      */     
/* 1695 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1696 */     String actualdata = "";
/*      */     
/* 1698 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1699 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1700 */         actualdata = availValue;
/*      */       }
/* 1702 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1703 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1707 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1708 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1711 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1717 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1718 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1719 */       toreturn.append("<table>");
/* 1720 */       toreturn.append("<tr>");
/* 1721 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1722 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1723 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1724 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1725 */         String toolTip = "";
/* 1726 */         String hideClass = "";
/* 1727 */         String textStyle = "";
/* 1728 */         boolean isreferenced = true;
/* 1729 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1730 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1731 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1732 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1734 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1735 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1736 */           while (valueList.hasMoreTokens()) {
/* 1737 */             String dependentVal = valueList.nextToken();
/* 1738 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1739 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1740 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1742 */               toolTip = "";
/* 1743 */               hideClass = "";
/* 1744 */               isreferenced = false;
/* 1745 */               textStyle = "disabledtext";
/* 1746 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1750 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1751 */           toolTip = "";
/* 1752 */           hideClass = "";
/* 1753 */           isreferenced = false;
/* 1754 */           textStyle = "disabledtext";
/* 1755 */           if (dependentImageMap != null) {
/* 1756 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1757 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1760 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1764 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1765 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1766 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1767 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1768 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1769 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1771 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1772 */           if (isreferenced) {
/* 1773 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1777 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1778 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1779 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1780 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1781 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1782 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1784 */           toreturn.append("</span>");
/* 1785 */           toreturn.append("</a>");
/* 1786 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1789 */       toreturn.append("</tr>");
/* 1790 */       toreturn.append("</table>");
/* 1791 */       toreturn.append("</td>");
/*      */     } else {
/* 1793 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1796 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1800 */     String colTime = null;
/* 1801 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1802 */     if ((rows != null) && (rows.size() > 0)) {
/* 1803 */       Iterator<String> itr = rows.iterator();
/* 1804 */       String maxColQuery = "";
/* 1805 */       for (;;) { if (itr.hasNext()) {
/* 1806 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1807 */           ResultSet maxCol = null;
/*      */           try {
/* 1809 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1810 */             while (maxCol.next()) {
/* 1811 */               if (colTime == null) {
/* 1812 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1815 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1824 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1826 */               if (maxCol != null)
/* 1827 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1829 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1824 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1826 */               if (maxCol != null)
/* 1827 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1829 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1834 */     return colTime;
/*      */   }
/*      */   
/* 1837 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1838 */     tablename = null;
/* 1839 */     ResultSet rsTable = null;
/* 1840 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1842 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1843 */       while (rsTable.next()) {
/* 1844 */         tablename = rsTable.getString("DATATABLE");
/* 1845 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1846 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1859 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1850 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1853 */         if (rsTable != null)
/* 1854 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1856 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1862 */     String argsList = "";
/* 1863 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1865 */       if (showArgsMap.get(row) != null) {
/* 1866 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1867 */         if (showArgslist != null) {
/* 1868 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1869 */             if (argsList.trim().equals("")) {
/* 1870 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1873 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1880 */       e.printStackTrace();
/* 1881 */       return "";
/*      */     }
/* 1883 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1888 */     String argsList = "";
/* 1889 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1892 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1894 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1895 */         if (hideArgsList != null)
/*      */         {
/* 1897 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1899 */             if (argsList.trim().equals(""))
/*      */             {
/* 1901 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1905 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1913 */       ex.printStackTrace();
/*      */     }
/* 1915 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1919 */     StringBuilder toreturn = new StringBuilder();
/* 1920 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1927 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1928 */       Iterator itr = tActionList.iterator();
/* 1929 */       while (itr.hasNext()) {
/* 1930 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1931 */         String confirmmsg = "";
/* 1932 */         String link = "";
/* 1933 */         String isJSP = "NO";
/* 1934 */         HashMap tactionMap = (HashMap)itr.next();
/* 1935 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1936 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1937 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1938 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1939 */           (actionmap.containsKey(actionId))) {
/* 1940 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1941 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1942 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1943 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1944 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1946 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1952 */           if (isTableAction) {
/* 1953 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1956 */             tableName = "Link";
/* 1957 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1958 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1959 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1960 */             toreturn.append("</a></td>");
/*      */           }
/* 1962 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1963 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1964 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1965 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1971 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1977 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1979 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1980 */       Properties prop = (Properties)node.getUserObject();
/* 1981 */       String mgID = prop.getProperty("label");
/* 1982 */       String mgName = prop.getProperty("value");
/* 1983 */       String isParent = prop.getProperty("isParent");
/* 1984 */       int mgIDint = Integer.parseInt(mgID);
/* 1985 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1987 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1989 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1990 */       if (node.getChildCount() > 0)
/*      */       {
/* 1992 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1994 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1996 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1998 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2002 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2007 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2009 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2011 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2013 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2017 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2020 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2021 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2023 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2027 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2029 */       if (node.getChildCount() > 0)
/*      */       {
/* 2031 */         builder.append("<UL>");
/* 2032 */         printMGTree(node, builder);
/* 2033 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2038 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2039 */     StringBuffer toReturn = new StringBuffer();
/* 2040 */     String table = "-";
/*      */     try {
/* 2042 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2043 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2044 */       float total = 0.0F;
/* 2045 */       while (it.hasNext()) {
/* 2046 */         String attName = (String)it.next();
/* 2047 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2048 */         boolean roundOffData = false;
/* 2049 */         if ((data != null) && (!data.equals(""))) {
/* 2050 */           if (data.indexOf(",") != -1) {
/* 2051 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2054 */             float value = Float.parseFloat(data);
/* 2055 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2058 */             total += value;
/* 2059 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2062 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2067 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2068 */       while (attVsWidthList.hasNext()) {
/* 2069 */         String attName = (String)attVsWidthList.next();
/* 2070 */         String data = (String)attVsWidthProps.get(attName);
/* 2071 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2072 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2073 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2074 */         String className = (String)graphDetails.get("ClassName");
/* 2075 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2076 */         if (percentage < 1.0F)
/*      */         {
/* 2078 */           data = percentage + "";
/*      */         }
/* 2080 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2082 */       if (toReturn.length() > 0) {
/* 2083 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2087 */       e.printStackTrace();
/*      */     }
/* 2089 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2095 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2096 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2097 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2098 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2099 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2100 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2101 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2102 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2103 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2106 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2107 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2108 */       splitvalues[0] = multiplecondition.toString();
/* 2109 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2112 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2117 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2118 */     if (thresholdType != 3) {
/* 2119 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2120 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2121 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2122 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2123 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2124 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2126 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2127 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2128 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2129 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2130 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2131 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2133 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2134 */     if (updateSelected != null) {
/* 2135 */       updateSelected[0] = "selected";
/*      */     }
/* 2137 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2142 */       StringBuffer toreturn = new StringBuffer("");
/* 2143 */       if (commaSeparatedMsgId != null) {
/* 2144 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2145 */         int count = 0;
/* 2146 */         while (msgids.hasMoreTokens()) {
/* 2147 */           String id = msgids.nextToken();
/* 2148 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2149 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2150 */           count++;
/* 2151 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2152 */             if (toreturn.length() == 0) {
/* 2153 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2155 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2156 */             if (!image.trim().equals("")) {
/* 2157 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2159 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2160 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2163 */         if (toreturn.length() > 0) {
/* 2164 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2168 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2171 */       e.printStackTrace(); }
/* 2172 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2178 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2184 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2185 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2202 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2206 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2207 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2216 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2220 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2221 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2222 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2223 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2226 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2227 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2228 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2235 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2238 */     JspWriter out = null;
/* 2239 */     Object page = this;
/* 2240 */     JspWriter _jspx_out = null;
/* 2241 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2245 */       response.setContentType("text/html;charset=UTF-8");
/* 2246 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2248 */       _jspx_page_context = pageContext;
/* 2249 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2250 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2251 */       session = pageContext.getSession();
/* 2252 */       out = pageContext.getOut();
/* 2253 */       _jspx_out = out;
/*      */       
/* 2255 */       out.write("<!-- $Id$ -->\n\n\n\n\n\n\n\n \n\n\n\n");
/*      */       
/* 2257 */       request.setAttribute("HelpKey", "Adding JMX MBeans Attributes");
/*      */       
/* 2259 */       out.write("\n<!--\n \nJMX IMPORTS NOT ALLOWED IN CAM JSPs as JMX 1.2 and JMX 1.0 are using the same jsp files and the system classpath has JMX 1.0\nHence only Map / User object based passing of data structures allowed.\n\n-->\n\n\n\n\n\n\n");
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
/* 2416 */                     out.write("\n\n\n\n");
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2423 */                     String camID = (String)request.getAttribute("camid");
/* 2424 */                     String haid = (String)request.getAttribute("haid");
/* 2425 */                     String screenID = (String)request.getAttribute("screenid");
/*      */                     
/* 2427 */                     String camname = "";
/* 2428 */                     Map resourceInfoFromResourcePage = null;
/*      */                     
/* 2430 */                     if ("true".equals((String)request.getAttribute("isfromresourcepage"))) {
/* 2431 */                       resourceInfoFromResourcePage = CAMDBUtil.getAMMOInfo(Integer.parseInt(camID));
/*      */                     } else {
/* 2433 */                       List list = CAMDBUtil.getCAMDetails(camID);
/* 2434 */                       camname = (String)list.get(0);
/*      */                     }
/*      */                     
/* 2437 */                     String hostName = (String)request.getAttribute("hostname");
/* 2438 */                     String portNumber = (String)request.getAttribute("portnumber");
/* 2439 */                     String resourceType = (String)request.getAttribute("resourcetype");
/* 2440 */                     String resourceID = (String)request.getAttribute("resourceid");
/* 2441 */                     Map globalInfoMap = (Map)request.getAttribute("mbeaninfo");
/* 2442 */                     Map similarMons = (Map)request.getAttribute("similarMons");
/* 2443 */                     Map mbeanInfoMap = (Map)globalInfoMap.get("mbeaninfo");
/* 2444 */                     Map tableInfoMap = (Map)globalInfoMap.get("tablecolsinfo");
/* 2445 */                     List screenInfo = CAMDBUtil.getScreenInfo(Long.parseLong(screenID));
/* 2446 */                     String NAME_TYPESEPARATOR = "|";
/* 2447 */                     int posOfScreenType = 3;
/* 2448 */                     int screenType = Integer.parseInt((String)screenInfo.get(posOfScreenType));
/* 2449 */                     int keyValScreen = 0;
/* 2450 */                     int integerAttribScreen = 1;
/* 2451 */                     String screenTypeAsString = "Normal Screens (Key - Value Pairs)";
/* 2452 */                     String quickNote = FormatUtil.getString("am.webclient.cam.showbeans.quicknote");
/* 2453 */                     String topDesc = "Select attributes to be added to the screen. [ Step 2 / 2 ] ";
/*      */                     
/* 2455 */                     if (screenType == integerAttribScreen)
/*      */                     {
/* 2457 */                       topDesc = FormatUtil.getString("am.webclient.cam.showbeans.desc1") + FormatUtil.getString("am.webclient.camshowbean.step");
/*      */                       
/* 2459 */                       quickNote = "";
/* 2460 */                       screenTypeAsString = FormatUtil.getString("am.webclient.cam.text");
/*      */                     }
/* 2462 */                     request.setAttribute("quicknote", quickNote);
/*      */                     
/* 2464 */                     request.setAttribute("needCustomizeScreen", "hint for cam_leftlinks.jsp");
/* 2465 */                     request.setAttribute("needDeleteScreen", "hint for cam_leftlinks.jsp");
/*      */                     
/*      */ 
/* 2468 */                     out.write("\n\n\n\n");
/*      */                     
/* 2470 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2471 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2472 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2474 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2475 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2476 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2478 */                         out.write(10);
/* 2479 */                         if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2481 */                         out.write(10);
/* 2482 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2484 */                         out.write(10);
/* 2485 */                         out.write(10);
/* 2486 */                         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2488 */                         out.write(10);
/*      */                         
/* 2490 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2491 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2492 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2494 */                         _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                         
/* 2496 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 2497 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2498 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2499 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2500 */                             out = _jspx_page_context.pushBody();
/* 2501 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2502 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2505 */                             out.write("\n\n\n\n  <script>\nfunction submitAttribs() {\n\n    var sel = false;\n    if(document.forms.length > 0) { \n        var frmEle = document.CAMUpdateScreenAttributesForm.elements;\n        if(typeof(frmEle) != \"undefined\" && frmEle.length > 0 && typeof(frmEle[\"attributes\"]) != \"undefined\") { \n            var siz = frmEle[\"attributes\"].length;\n            if(typeof(siz0)==\"undefined\")\n            { \n                sel=frmEle[\"attributes\"].checked;\n            }\n            if(!sel)\n            {\n            \tfor(i=0;i<siz;i++) {\n            \t    if(frmEle[\"attributes\"][i].checked) {\n            \t        sel = true;\n            \t        break;\n            \t    }\n            \t} \n            }\n            setOptionsAsSelected();\n            if(sel) \n            {    \n            \tdocument.CAMUpdateScreenAttributesForm.submit();\n            }\n            else\n            {\n                alert(\"Cannot proceed. No attributes have been selected to add to the screen.\");\n            }\n            \n        } else {\n                alert(\"Cannot proceed. There are no attributes that can be added to the screen.\");\n");
/* 2506 */                             out.write("        }\n\n    } else {\n                alert(\"Cannot proceed. There are no attributes that can be added to the screen.\");\n    }\n}\n\nfunction setOptionsAsSelected() {\n\tvar selectedOptions = document.getElementById('selectedresource');\n\tvar len = selectedOptions.length;\n\tfor(var i=0;i<len;i++) {\n\t\tselectedOptions.options[i].selected = true;\t\n\t}\n}\n\nfunction cancel() {\n    \n");
/*      */                             
/* 2508 */                             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2509 */                             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2510 */                             _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2512 */                             _jspx_th_c_005fif_005f0.setTest("${(!empty isfromresourcepage)}");
/* 2513 */                             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2514 */                             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                               for (;;) {
/* 2516 */                                 out.write("    \n    var url = \"/showresource.do?resourceid=");
/* 2517 */                                 out.print(camID);
/* 2518 */                                 out.write("&method=showResourceForResourceID\";\n");
/* 2519 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2520 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2524 */                             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2525 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                             }
/*      */                             
/* 2528 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2529 */                             out.write(10);
/* 2530 */                             out.write(10);
/*      */                             
/* 2532 */                             IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2533 */                             _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2534 */                             _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2536 */                             _jspx_th_c_005fif_005f1.setTest("${(empty isfromresourcepage)}");
/* 2537 */                             int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2538 */                             if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                               for (;;) {
/* 2540 */                                 out.write("    \n    var url = \"/ShowCAM.do?method=configureScreen&screenid=\" + \"");
/* 2541 */                                 out.print(screenID);
/* 2542 */                                 out.write("\" + \"&camid=\" + \"");
/* 2543 */                                 out.print(camID);
/* 2544 */                                 out.write("\" + \"&haid=\" + \"");
/* 2545 */                                 out.print(haid);
/* 2546 */                                 out.write(34);
/* 2547 */                                 out.write(59);
/* 2548 */                                 out.write(10);
/* 2549 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2550 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2554 */                             if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2555 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                             }
/*      */                             
/* 2558 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2559 */                             out.write("\n\n    document.CAMUpdateScreenAttributesForm.action = url;\n    document.CAMUpdateScreenAttributesForm.submit();\n}\n\n\nfunction selectAllChildCKbs(obname,ckb,myfrm) {\n\n    if(typeof(myfrm.length)==\"undefined\") {\n\n        return;\n    }\n\n    for(i=0;i<myfrm.length;i++) {\n\n        if(myfrm.elements[i].type == \"checkbox\" && myfrm.elements[i].value.indexOf(obname) != -1) {\n                myfrm.elements[i].checked = ckb.checked;\n        }\n    }\n\n}\n\nfunction deselectParentCKbs(parent,myfrm) {\n    \n\n    for(i=0;i<myfrm.length;i++) {\n\n        if(myfrm.elements[i].value == parent) {\n                myfrm.elements[i].checked = false;\n        }\n    }\n\n\n}\n\nfunction deselectMultipleParentCKbs(parent1,parent2,myfrm) {\n    \n\n    for(i=0;i<myfrm.length;i++) {\n\n        if(myfrm.elements[i].value == parent1 || myfrm.elements[i].value == parent2) {\n                myfrm.elements[i].checked = false;\n        }\n    }\n}\n\nfunction selectChildAndDeselectParent(child,parent,ckb,myfrm)\n{\n    \n    for(i=0;i<myfrm.length;i++) \n    {\n    \n            if(myfrm.elements[i].type == \"checkbox\" && myfrm.elements[i].value.indexOf(child+\"|\") != -1) {\n");
/* 2560 */                             out.write("                    myfrm.elements[i].checked = ckb.checked;\n            }\n            \n            if(myfrm.elements[i].value == parent) {\n\t                    myfrm.elements[i].checked = false;\n            }\n            \n    }\n\n}\n\nfunction showOrHide(checkboxObj)\n{\n\tif(!checkboxObj.checked)\n\t{\n    \tdocument.getElementById('showSimilarMonitorsRow').style.display = \"none\";\n\t}\n\telse\n\t{\n    \tdocument.getElementById('showSimilarMonitorsRow').style.display = \"table-row\";\n\t}       \n      \n}      \n        \nfunction alterFromHashes(combo)\n{\n  var count = combo.length;\t  //No I18N\n  var myhashs={};\t  //No I18N\t  \n  for(k=0;k<parseInt(count);k++)\n   {\n       if(combo.options[k].selected)\n       {\n\t   value = combo.options[k].value;\t//No I18N\n\t   if(combo.name.toLowerCase().indexOf(\"availableresource\")!=-1\t) {\n\t\t\t $.each(allHash, function(key, myhashs) {\n\t\t\t    if(myhashs[combo.options[k].text]!=null)\n\t\t\t    {\t\t\t\t    \t\n\t\t\t    \tif(key!=1){\n\t\t\t    \t\tselectedHash[combo.options[k].text]=key;\n\t\t\t    \t}\t\t\t\t    \t\n\t\t\t    \tdelete myhashs[combo.options[k].text]; //No I18N\n");
/* 2561 */                             out.write("\t\t\t    }\t\t\t \t\n\t\t\t\t});\n\n\t\t\t}\n\t\t\telse if(combo.name.toLowerCase().indexOf('selectedresource')!=-1) {\n\t\t\t\tvar alllist=allHash['All-Monitors'];//No I18N\n\t\t\t\talllist[combo.options[k].text] = value; //No I18N\n\t\t\t\tvar key=selectedHash[combo.options[k].text];\n\n\t\t\t\tvar myhashs=allHash[key];\t\t\t\t\n\t\t\t    myhashs[combo.options[k].text] = value; //No I18N \n\t\t\t\tdelete selectedHash[combo.options[k].text];\n\t\t\t}\n\t   }\n   }\t\n}     \n\nfunction fnAddToRightCombo(availableresource,selectedcombo)\n{\n  var count = availableresource.length;\n   for(k=0;k<parseInt(count);k++)\n   {\n       if(availableresource.options[k].selected)\n       {\n\t   value = availableresource.options[k].value; //No I18N\n\t   //alert('calling additoion');\n\t   fnAddOption(selectedcombo,value,availableresource.options[k].text); //No I18N\n       }\n   }\n   return; //No I18N\n}\n\nfunction alterallHashesfromcombo(combo)\n{\nvar count = combo.length; \nvar myhashs={};\t  //No I18N\t\n for(k=0;k<parseInt(count);k++)\n   {\n     value = combo.options[k].value;\t//No I18N\n     if(combo.name.toLowerCase().indexOf(\"availableresource\")!=-1)\n");
/* 2562 */                             out.write("\t\t\t{\t\t\t\t\n\t\t\t$.each(allHash, function(key, myhashs) {\n\t\t\t    if(myhashs[combo.options[k].text]!=null)\n\t\t\t    {\n\t\t\t    \tif(key!=1){\n\t\t\t    \t\tselectedHash[combo.options[k].text]=key;\n\t\t\t    \t}\t\t\t\t    \t\n\t\t\t    \tdelete myhashs[combo.options[k].text]; //No I18N\n\t\t\t    }\t\t\t \t\n\t\t\t\t});\n\t\t\t}\n\t\t\telse if(combo.name.toLowerCase().indexOf('selectedresource')!=-1)//No I18N\n\t\t\t{\t\t\t\t\n\t\t\t\tvar alllist=allHash['All-Monitors'];//No I18N\n\t\t\t\talllist[combo.options[k].text] = value; //No I18N\n\t\t\t\tvar key=selectedHash[combo.options[k].text];\n\t\t\t\tvar myhashs=allHash[key];\t\t\t\t\n\t\t\t    myhashs[combo.options[k].text] = value; //No I18N \n\t\t\t\tdelete selectedHash[combo.options[k].text];\n\t\t\t\t}\n   }\n\n}\n\n</SCRIPT>\n\n\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr> \n    ");
/*      */                             
/* 2564 */                             Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2565 */                             String aid = request.getParameter("haid");
/* 2566 */                             String haName = null;
/* 2567 */                             if (aid != null)
/*      */                             {
/* 2569 */                               haName = (String)ht.get(aid);
/*      */                             }
/*      */                             
/* 2572 */                             out.write("\n    ");
/* 2573 */                             if (_jspx_meth_c_005fcatch_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2575 */                             out.write(" \n    ");
/*      */                             
/* 2577 */                             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2578 */                             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2579 */                             _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2581 */                             _jspx_th_c_005fif_005f2.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2582 */                             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2583 */                             if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                               for (;;) {
/* 2585 */                                 out.write(" \n    <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2586 */                                 out.print(BreadcrumbUtil.getHomePage(request));
/* 2587 */                                 out.write(" \n      &gt; ");
/* 2588 */                                 out.print(BreadcrumbUtil.getHAPage(aid, haName));
/* 2589 */                                 out.write(" &gt;\n\t");
/*      */                                 
/* 2591 */                                 IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2592 */                                 _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2593 */                                 _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fif_005f2);
/*      */                                 
/* 2595 */                                 _jspx_th_c_005fif_005f3.setTest("${('true'!=isfromresourcepage)}");
/* 2596 */                                 int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2597 */                                 if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                   for (;;) {
/* 2599 */                                     out.write("      \n\t      ");
/* 2600 */                                     out.print(BreadcrumbUtil.getCAMDetailsPage(camID, aid, camname));
/* 2601 */                                     out.write(32);
/* 2602 */                                     out.write(10);
/* 2603 */                                     out.write(9);
/* 2604 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2605 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2609 */                                 if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2610 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                 }
/*      */                                 
/* 2613 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2614 */                                 out.write("      \n\t");
/*      */                                 
/* 2616 */                                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2617 */                                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2618 */                                 _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fif_005f2);
/*      */                                 
/* 2620 */                                 _jspx_th_c_005fif_005f4.setTest("${('true'==isfromresourcepage)}");
/* 2621 */                                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2622 */                                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                   for (;;) {
/* 2624 */                                     out.write("      \n\t      ");
/* 2625 */                                     out.print(BreadcrumbUtil.getServerDetailsPage(camID, (String)resourceInfoFromResourcePage.get("DISPLAYNAME")));
/* 2626 */                                     out.write(32);
/* 2627 */                                     out.write(10);
/* 2628 */                                     out.write(9);
/* 2629 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2630 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2634 */                                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2635 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                 }
/*      */                                 
/* 2638 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2639 */                                 out.write("      \n      \n      &gt; <span class=\"bcactive\"> Custom Attributes </span></td>\n    ");
/* 2640 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2641 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2645 */                             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2646 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                             }
/*      */                             
/* 2649 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2650 */                             out.write(" \n    \n    ");
/*      */                             
/* 2652 */                             IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2653 */                             _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2654 */                             _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2656 */                             _jspx_th_c_005fif_005f5.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 2657 */                             int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2658 */                             if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                               for (;;) {
/* 2660 */                                 out.write("\t\n    <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2661 */                                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 2662 */                                 out.write(" \n      &gt; \n      \n");
/*      */                                 
/* 2664 */                                 IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2665 */                                 _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2666 */                                 _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f5);
/*      */                                 
/* 2668 */                                 _jspx_th_c_005fif_005f6.setTest("${('true'!=isfromresourcepage)}");
/* 2669 */                                 int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2670 */                                 if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                   for (;;) {
/* 2672 */                                     out.write("      \n      ");
/* 2673 */                                     out.print(BreadcrumbUtil.getMonitorResourceTypes("Custom-Application"));
/* 2674 */                                     out.write(" \n      &gt; ");
/* 2675 */                                     out.print(BreadcrumbUtil.getCAMDetailsPage(camID, aid, camname));
/* 2676 */                                     out.write(32);
/* 2677 */                                     out.write(10);
/* 2678 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2679 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2683 */                                 if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2684 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                 }
/*      */                                 
/* 2687 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2688 */                                 out.write("      \n \n \n");
/*      */                                 
/* 2690 */                                 IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2691 */                                 _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2692 */                                 _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f5);
/*      */                                 
/* 2694 */                                 _jspx_th_c_005fif_005f7.setTest("${('true'==isfromresourcepage)}");
/* 2695 */                                 int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2696 */                                 if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                   for (;;) {
/* 2698 */                                     out.write(10);
/* 2699 */                                     out.write(9);
/* 2700 */                                     out.print(BreadcrumbUtil.getMonitorResourceTypes((String)resourceInfoFromResourcePage.get("TYPE")));
/* 2701 */                                     out.write(" \n\t&gt;\n\t\n\t");
/* 2702 */                                     out.print(BreadcrumbUtil.getServerDetailsPage(camID, (String)resourceInfoFromResourcePage.get("DISPLAYNAME")));
/* 2703 */                                     out.write(10);
/* 2704 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2705 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2709 */                                 if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2710 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                 }
/*      */                                 
/* 2713 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2714 */                                 out.write("\n\t&gt; <span class=\"bcactive\"> \n      ");
/* 2715 */                                 out.print(FormatUtil.getString("am.webclient.cam.addattributes.link"));
/* 2716 */                                 out.write("</span> \n      </td>\n    ");
/* 2717 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2718 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2722 */                             if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2723 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                             }
/*      */                             
/* 2726 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2727 */                             out.write(" </tr>\n  <tr> \n    <td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n  </tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n  \n</table>\n  \n<script>\nfunction validateAndSubmit()\n{\n   if(trimAll(document.AMActionForm.camname.value)==\"\")\n   {\n        alert('");
/* 2728 */                             out.print(FormatUtil.getString("am.webclient.cam.namealert"));
/* 2729 */                             out.write("');\n        return;\n   }\n   document.AMActionForm.submit();\n}\n\n</script>\n\n");
/*      */                             
/* 2731 */                             List list1 = CAMDBUtil.getCAMDetails(camID);
/* 2732 */                             String camName = (String)list1.get(0);
/* 2733 */                             String camDescription = (String)list1.get(2);
/*      */                             
/* 2735 */                             out.write("\n\n\n\n<div id=\"edit\" style=\"DISPLAY: none\">\n\n");
/*      */                             
/* 2737 */                             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 2738 */                             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2739 */                             _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2741 */                             _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*      */                             
/* 2743 */                             _jspx_th_html_005fform_005f0.setMethod("get");
/*      */                             
/* 2745 */                             _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2746 */                             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2747 */                             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                               for (;;) {
/* 2749 */                                 out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n<td height=\"28\"   class=\"tableheading\">");
/* 2750 */                                 out.print(FormatUtil.getString("am.webclient.common.configurationdetails.text"));
/* 2751 */                                 out.write("\n\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 2752 */                                 out.print(request.getParameter("name"));
/* 2753 */                                 out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 2754 */                                 out.print(request.getParameter("haid"));
/* 2755 */                                 out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 2756 */                                 out.print(request.getParameter("type"));
/* 2757 */                                 out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 2758 */                                 out.print(request.getParameter("type"));
/* 2759 */                                 out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"configureJMX\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2760 */                                 out.print(request.getParameter("resourceid"));
/* 2761 */                                 out.write("\">\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 2762 */                                 out.print(request.getParameter("resourcename"));
/* 2763 */                                 out.write("\">\n<input type=\"hidden\" name=\"moname\" value=\"");
/* 2764 */                                 out.print(request.getParameter("moname"));
/* 2765 */                                 out.write("\">\n \n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\">\n<span class=\"bodytextboldwhiteun\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n");
/* 2766 */                                 out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 2767 */                                 out.write("</span>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n<tr>\n    <td width=\"20%\" height=\"32\" valign=='top' class=\"bodytext\"> ");
/* 2768 */                                 out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2769 */                                 out.write("\n</td>\n    <td width=\"80%\" class=\"bodytext\"> <textarea name=\"camname\" cols=\"38\" rows=\"1\" class=\"formtextarea\">");
/* 2770 */                                 out.print(camName);
/* 2771 */                                 out.write(" </textarea>\n</td>\n</tr>\n\n<tr>\n    <td valign='top'  class=\"bodytext\"> ");
/* 2772 */                                 out.print(FormatUtil.getString("Description"));
/* 2773 */                                 out.write("</td>\n    <td  class=\"bodytext\"> <textarea name=\"camdesc\" cols=\"38\" rows=\"3\" class=\"formtextarea\" >");
/* 2774 */                                 out.print(camDescription);
/* 2775 */                                 out.write("</textarea>\n    </td>\n  </tr>\n</table>\n");
/*      */                                 
/* 2777 */                                 String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/*      */                                 
/* 2779 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"20%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"80%\" class=\"tablebottom\" >\n<input name=\"addcustomapp\" type=\"button\" class=\"buttons\" \" value=\"");
/* 2780 */                                 out.print(FormatUtil.getString("am.webclient.common.startmonitoring.text"));
/* 2781 */                                 out.write("\" onClick=\"validateAndSubmit()\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons\" value=\"");
/* 2782 */                                 out.print(cancel);
/* 2783 */                                 out.write("\" onClick=\"javascript:toggleDiv('edit')\"/>\n     </td>\n  </tr>\n</table>\n");
/* 2784 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2785 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2789 */                             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2790 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                             }
/*      */                             
/* 2793 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2794 */                             out.write("\n</div>\n  \n");
/*      */                             
/* 2796 */                             FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(FormTag.class);
/* 2797 */                             _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 2798 */                             _jspx_th_html_005fform_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2800 */                             _jspx_th_html_005fform_005f1.setMethod("POST");
/*      */                             
/* 2802 */                             _jspx_th_html_005fform_005f1.setAction("/CAMUpdateScreenAttributes");
/* 2803 */                             int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 2804 */                             if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                               for (;;) {
/* 2806 */                                 out.write(" \n<input type=hidden name=\"method\" value=\"updateScreenAttributes\"/>\n<input type=\"hidden\" name=\"camid\" value=\"");
/* 2807 */                                 out.print(camID);
/* 2808 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 2809 */                                 out.print(haid);
/* 2810 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"screenid\" value=\"");
/* 2811 */                                 out.print(screenID);
/* 2812 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"hostname\" value=\"");
/* 2813 */                                 out.print(hostName);
/* 2814 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"portnumber\" value=\"");
/* 2815 */                                 out.print(portNumber);
/* 2816 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 2817 */                                 out.print(resourceType);
/* 2818 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2819 */                                 out.print(resourceID);
/* 2820 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"isfromresourcepage\" value=\"");
/* 2821 */                                 out.print(request.getAttribute("isfromresourcepage"));
/* 2822 */                                 out.write("\"/>  \n<table valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  width=\"99%\" class=\"lrtbdarkborder\">\n<input type=\"hidden\" name=\"displayname\" value=\"default\" />\n<input type=\"hidden\" name=\"type\" value=\"0\" />\n<tr> \n\n\n<td  height=\"25\" colspan=\"3\" class=\"tableheadingbborder\">");
/* 2823 */                                 out.print(FormatUtil.getString("am.webclient.cam.mbeanslist.heading"));
/* 2824 */                                 out.write("</td>\n    </tr>\n<tr>\n<td colspan=\"3\">\n<table width=\"100%\" valign=\"center\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" >\n  <tr> \n          <td width=\"10%\" class=\"columnheadingnotop\"> <b>");
/* 2825 */                                 out.print(FormatUtil.getString("am.webclient.cam.choosedomain.agent.text"));
/* 2826 */                                 out.write(" :</b></td>\n    <td width=\"35%\" class=\"columnheadingnotop\"> ");
/* 2827 */                                 out.print(getTrimmedText(hostName, 40));
/* 2828 */                                 out.write("</td>\n          <td width=\"8%\" class=\"columnheadingnotop\"> <b>");
/* 2829 */                                 out.print(FormatUtil.getString("am.webclient.cam.choosedomain.port.text"));
/* 2830 */                                 out.write(" :</b></td>\n    <td width=\"21%\" class=\"columnheadingnotop\"> ");
/* 2831 */                                 out.print(portNumber);
/* 2832 */                                 out.write("</td>\n          <td width=\"8%\" class=\"columnheadingnotop\"><b>");
/* 2833 */                                 out.print(FormatUtil.getString("am.webclient.cam.choosedomain.type.text"));
/* 2834 */                                 out.write(" :</b></td>\n    <td width=\"24%\" class=\"columnheadingnotop\">");
/* 2835 */                                 out.print(resourceType);
/* 2836 */                                 out.write("</td>\n  </tr>\n</table>\n</td></tr>\n    \n    <tr> \n          <td  height=\"40\" colspan=\"2\" class=\"columnheading\"> <span class='bodytextbold'>");
/* 2837 */                                 out.print(FormatUtil.getString("am.webclient.cam.mbeansobjectname.text"));
/* 2838 */                                 out.write(" </span></td>\n      <td width=\"60%\"  height=\"26\" class=\"columnheading\"> <span class='bodytextbold'>&nbsp;");
/* 2839 */                                 out.print(FormatUtil.getString("am.webclient.cam.attributes.text"));
/* 2840 */                                 out.write(" \n        </span></td>\n    </tr>\n");
/*      */                                 
/* 2842 */                                 Set keys = mbeanInfoMap.keySet();
/* 2843 */                                 Iterator it = keys.iterator();
/* 2844 */                                 Object key = null;
/* 2845 */                                 String className = "whitegrayborder";
/* 2846 */                                 int ctr = 1;
/* 2847 */                                 while (it.hasNext())
/*      */                                 {
/*      */ 
/* 2850 */                                   if (ctr % 2 == 0)
/*      */                                   {
/* 2852 */                                     className = "yellowgrayborder";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2856 */                                     className = "whitegrayborder";
/*      */                                   }
/* 2858 */                                   key = it.next();
/*      */                                   
/* 2860 */                                   List mbInfoList = (List)mbeanInfoMap.get(key);
/* 2861 */                                   String mbAttribname = null;
/* 2862 */                                   String mbAttribtype = null;
/* 2863 */                                   String mbAttribClassName = null;
/* 2864 */                                   int size = mbInfoList.size();
/* 2865 */                                   boolean atleastOnePresent = false;
/* 2866 */                                   StringBuffer buffer = new StringBuffer();
/* 2867 */                                   if (size > 0)
/*      */                                   {
/* 2869 */                                     ctr++;
/*      */                                     
/* 2871 */                                     out.write("\n\t\t\t\t <tr class='");
/* 2872 */                                     out.print(className);
/* 2873 */                                     out.write("'> \n\t\t\t\t\t<td width=\"3%\" valign=\"top\" class=\"");
/* 2874 */                                     out.print(className);
/* 2875 */                                     out.write("\"> \n\t\t\t\t\t   <input type=\"checkbox\" name=\"multipleselectioncheckbox\" value=\"");
/* 2876 */                                     out.print(key.toString().replaceAll("\"", "&quot;"));
/* 2877 */                                     out.write("\" onClick=\"selectAllChildCKbs('");
/* 2878 */                                     out.print(key.toString().replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 2879 */                                     out.write("',this,this.form)\" />   \n\t\t\t\t\t\t&nbsp;</td> \n\t\t\t\t\t<td width=\"37%\" valign=\"top\" class=\"");
/* 2880 */                                     out.print(className);
/* 2881 */                                     out.write("\" title=\"");
/* 2882 */                                     out.print(key.toString());
/* 2883 */                                     out.write("\"><span class=\"bodytext\"> ");
/* 2884 */                                     if (key.toString().length() > 140) out.println(key.toString().substring(0, 70) + "<br>" + key.toString().substring(70, 140) + "<br>" + getTrimmedText(key.toString().substring(140), 70)); else if (key.toString().length() > 70) out.println(key.toString().substring(0, 70) + "<br>" + key.toString().substring(70)); else out.println(key.toString());
/* 2885 */                                     out.write("&nbsp;&nbsp;</span></td>\n\t\t\t\t <td class=\"bodytext\" valign=\"top\"  >\n\t\t\t\t <table class='");
/* 2886 */                                     out.print(className);
/* 2887 */                                     out.write("' valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  width=\"100%\">\n\t\t\t\t ");
/*      */                                   }
/*      */                                   
/* 2890 */                                   for (int i = 0; i < size; i++)
/*      */                                   {
/* 2892 */                                     Map minfoMap = (Map)mbInfoList.get(i);
/* 2893 */                                     mbAttribtype = (String)minfoMap.get("TYPE");
/* 2894 */                                     mbAttribname = (String)minfoMap.get("NAME");
/* 2895 */                                     mbAttribClassName = (String)minfoMap.get("MBEAN_CLASSNAME");
/*      */                                     
/* 2897 */                                     if (screenType == integerAttribScreen)
/*      */                                     {
/* 2899 */                                       if (!CAMJMXUtil.isNumeric(mbAttribtype)) {
/*      */                                         continue;
/*      */                                       }
/* 2902 */                                       out.write("\n\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t<td width='4%'><input type=checkbox name=\"attributes\" value=\"");
/* 2903 */                                       out.print((key + "|" + mbAttribname + "|0").replaceAll("\"", "&quot;"));
/* 2904 */                                       out.write("\" onclick=\"deselectParentCKbs('");
/* 2905 */                                       out.print(key.toString().replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 2906 */                                       out.write("',this.form)\" />\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width='96%'>&nbsp;");
/* 2907 */                                       out.print(getTrimmedText(mbAttribname, 30));
/* 2908 */                                       out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                     }
/* 2916 */                                     else if (screenType == keyValScreen)
/*      */                                     {
/*      */ 
/* 2919 */                                       if (CAMJMXUtil.isNumeric(mbAttribtype))
/*      */                                       {
/*      */ 
/* 2922 */                                         out.write("\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width='4%'><input type=checkbox name=\"attributes\" value=\"");
/* 2923 */                                         out.print((key + "|" + mbAttribname + "|0").replaceAll("\"", "&quot;"));
/* 2924 */                                         out.write("\" onclick=\"deselectParentCKbs('");
/* 2925 */                                         out.print(key.toString().replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 2926 */                                         out.write("',this.form);\" />\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width='96%'>&nbsp;");
/* 2927 */                                         out.print(getTrimmedText(mbAttribname, 30));
/* 2928 */                                         out.write("</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t");
/*      */ 
/*      */                                       }
/* 2931 */                                       else if ((mbAttribtype.equals("javax.jmx.openmbean.TabularData")) || (mbAttribtype.equals("javax.management.openmbean.TabularData")) || (mbAttribtype.equals("javax.management.openmbean.CompositeData")))
/*      */                                       {
/*      */ 
/* 2934 */                                         if (mbAttribClassName.equals("javax.management.modelmbean.ModelMBeanAttributeInfo"))
/*      */                                         {
/*      */ 
/*      */ 
/* 2938 */                                           List list = (List)minfoMap.get("ModelMBeanAttributeInfo_ExtraInfo");
/* 2939 */                                           if (list != null)
/*      */                                           {
/* 2941 */                                             for (int l = 0; l < list.size(); l++)
/*      */                                             {
/*      */ 
/*      */ 
/* 2945 */                                               String nameAndType = (String)list.get(l);
/* 2946 */                                               String name = nameAndType.substring(0, nameAndType.indexOf(NAME_TYPESEPARATOR));
/* 2947 */                                               String type = nameAndType.substring(name.length() + 1, nameAndType.length());
/* 2948 */                                               if (type.equals("java.lang.String"))
/*      */                                               {
/*      */ 
/*      */ 
/* 2952 */                                                 out.write("\n\t\t\t\t\t  \t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td width='4%'><input type=checkbox name=\"attributes\" value=\"");
/* 2953 */                                                 out.print((key + "|" + name + "|2").replaceAll("\"", "&quot;"));
/* 2954 */                                                 out.write("\" onclick=\"deselectParentCKbs('");
/* 2955 */                                                 out.print(key.toString().replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 2956 */                                                 out.write("',this.form)\" />\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t<td width='96%'>&nbsp;");
/* 2957 */                                                 out.print(name);
/* 2958 */                                                 out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t");
/*      */ 
/*      */                                               }
/* 2961 */                                               else if (CAMJMXUtil.isNumeric(type))
/*      */                                               {
/*      */ 
/* 2964 */                                                 out.write("\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td width='4%'><input type=checkbox name=\"attributes\" value=\"");
/* 2965 */                                                 out.print((key + "|" + name + "|3").replaceAll("\"", "&quot;"));
/* 2966 */                                                 out.write("\" onclick=\"deselectParentCKbs('");
/* 2967 */                                                 out.print(key.toString().replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 2968 */                                                 out.write("',this.form)\" />\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t<td width='96%'>&nbsp;");
/* 2969 */                                                 out.print(name);
/* 2970 */                                                 out.write("</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t");
/*      */                                               }
/*      */                                               
/*      */                                             }
/*      */                                             
/*      */                                           }
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 2979 */                                           out.write("\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td valign=\"top\" width='4%'><input type=checkbox name=\"attributes\" value=\"");
/* 2980 */                                           out.print((key + "|" + mbAttribname + "|4").replaceAll("\"", "&quot;"));
/* 2981 */                                           out.write("\" onclick=\"selectChildAndDeselectParent('");
/* 2982 */                                           out.print((key + "|" + mbAttribname).replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 2983 */                                           out.write(39);
/* 2984 */                                           out.write(44);
/* 2985 */                                           out.write(39);
/* 2986 */                                           out.print(key.toString().replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 2987 */                                           out.write("',this,this.form)\" />\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td width='96%'>&nbsp;");
/* 2988 */                                           out.print(getTrimmedText(mbAttribname, 30));
/* 2989 */                                           out.write("\n\t\t\t\t\t\t\t\t");
/*      */                                           
/* 2991 */                                           Vector colNamesVec = (Vector)tableInfoMap.get(key + "_" + mbAttribname + "_columnnames");
/* 2992 */                                           Vector colTypesVec = (Vector)tableInfoMap.get(key + "_" + mbAttribname + "_columntypes");
/*      */                                           
/* 2994 */                                           for (int ab = 0; (colNamesVec != null) && (ab < colNamesVec.size()); ab++)
/*      */                                           {
/*      */ 
/* 2997 */                                             out.write("\n\t\t\t\t\t\t\t\t\t<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"bodystyle\">\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td width='4%'><input type=checkbox name=\"attributes\" value=\"");
/* 2998 */                                             out.print((key + "|" + mbAttribname + "|4|" + colNamesVec.get(ab) + "|" + colTypesVec.get(ab)).replaceAll("\"", "&quot;"));
/* 2999 */                                             out.write("\" onclick=\"deselectMultipleParentCKbs('");
/* 3000 */                                             out.print((key + "|" + mbAttribname + "|4").replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 3001 */                                             out.write(39);
/* 3002 */                                             out.write(44);
/* 3003 */                                             out.write(39);
/* 3004 */                                             out.print(key.toString().replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 3005 */                                             out.write("',this.form)\" />\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t<td width='96%'>&nbsp;<i>");
/* 3006 */                                             out.print(colNamesVec.get(ab));
/* 3007 */                                             out.write("</i></td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t");
/*      */                                           }
/* 3009 */                                           out.write("\n\t\t\t\t\t\t\t        </td></tr>  ");
/*      */                                         }
/*      */                                         
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3017 */                                         out.write("\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width='4%'><input type=checkbox name=\"attributes\" value=\"");
/* 3018 */                                         out.print((key + "|" + mbAttribname + "|1").replaceAll("\"", "&quot;").replaceAll("\\n", ""));
/* 3019 */                                         out.write("\" onclick=\"deselectParentCKbs('");
/* 3020 */                                         out.print(key.toString().replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 3021 */                                         out.write("',this.form)\" />\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width='96%'>&nbsp;");
/* 3022 */                                         out.print(getTrimmedText(mbAttribname, 30));
/* 3023 */                                         out.write("</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t");
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/*      */ 
/* 3029 */                                     atleastOnePresent = true;
/*      */                                   }
/* 3031 */                                   if (atleastOnePresent)
/*      */                                   {
/*      */ 
/* 3034 */                                     out.write("\n\t   </table>\n\t  </td>\n\t   ");
/*      */                                   }
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3044 */                                   out.write("\n   </tr>\n    ");
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/* 3049 */                                 out.write("\n\t<tr>\n\t\t<td width=\"10px\"><input style=\"padding-left:0px;\" id=\"multimonid\"  type=\"checkbox\" name=\"multimonitors\" value=\"applyselected\" onClick=\"javascript:showOrHide(this);\"></td>\n\t\t<td class=\"bodytext\"><b>");
/* 3050 */                                 out.print(FormatUtil.getString("am.webclient.configurealert.selectmonitors"));
/* 3051 */                                 out.write("</b></td>\t\t\n    </tr>    \t\n\t\t<tr id=\"showSimilarMonitorsRow\" style=\"display: none;\">\n\t\t<td colspan=\"3\">\n\t\t<table align=\"center\">\n\t\t<tr>\n\t\t<td width=\"31%\" align=\"right\">\n\t\t\t<!-- <div id=\"divAvailableMonitors\" style=\"OVERFLOW: auto;width: 380px;height: 130px\"> -->\n\t\t\t\t<select name=\"similarmonitors_available\" style=\"OVERFLOW: auto;width: 300px;height: 130px\" size=\"4\"  multiple  id=\"availableresource\">\n           ");
/*      */                                 
/* 3053 */                                 Set simSet = similarMons.keySet();
/* 3054 */                                 Iterator iterate = simSet.iterator();
/* 3055 */                                 Object resID = null;
/* 3056 */                                 while (iterate.hasNext()) {
/* 3057 */                                   resID = iterate.next();
/* 3058 */                                   String similarMonID = (String)resID;
/* 3059 */                                   String similarMonName = (String)similarMons.get(resID.toString());
/*      */                                   
/* 3061 */                                   out.write("\n              <option value='");
/* 3062 */                                   out.print(similarMonID);
/* 3063 */                                   out.write(39);
/* 3064 */                                   out.write(62);
/* 3065 */                                   out.write(32);
/* 3066 */                                   out.print(similarMonName);
/* 3067 */                                   out.write(" </option>\n            ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3071 */                                 out.write("\n\t\t\t\t</select>\n\t\t\t<!-- </div> -->\n\t\t</td>\n\t\t<td width=\"7%\" align=\"center\" class=\"bodytext\">\n\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n      \t\t\t<tr>\n\t            \t\t<td align=\"center\" ><input name=\"AddButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:alterFromHashes(document.getElementById('availableresource')),fnAddToRightCombo(document.getElementById('availableresource'),document.getElementById('selectedresource')),fnRemoveFromRightCombo(document.getElementById('availableresource'))\" value=\"&nbsp;&gt;&nbsp;\"></td>\n      \t\t\t</tr>\n             \t\t\t<tr>\n\t    \t\t       <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n      \t\t\t</tr>\n      \t\t\t<tr>\n\t            \t\t<td align=\"center\"><input name=\"AddButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:alterallHashesfromcombo(document.getElementById('availableresource')),fnAddAllFromCombo(document.getElementById('availableresource'),document.getElementById('selectedresource')),fnRemoveAllFromCombo(document.getElementById('availableresource'))\" value=\"&gt;&gt;\"></td>\n");
/* 3072 */                                 out.write("      \t\t\t</tr>\n             \t\t\t<tr>\n\t    \t\t       <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n      \t\t\t</tr>\n             \t\t\t<tr>\n\t\t\t       <td align=\"center\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:alterFromHashes(document.getElementById('selectedresource')),fnAddToRightCombo(document.getElementById('selectedresource'),document.getElementById('availableresource')),fnRemoveFromRightCombo(document.getElementById('selectedresource'))\" value=\"&nbsp;&lt;&nbsp;\"></td>\n\t                </tr>\n             \t\t\t<tr>\n\t    \t\t       <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n      \t\t\t</tr>\n             \t\t\t<tr>\n\t\t\t       <td align=\"center\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:alterallHashesfromcombo(document.getElementById('selectedresource')),fnAddAllFromCombo(document.getElementById('selectedresource'),document.getElementById('availableresource')),fnRemoveAllFromCombo(document.getElementById('selectedresource'));\" value=\"&lt;&lt;\"></td>\n");
/* 3073 */                                 out.write("\t                </tr>\n\t\t      </table>\n\t\t</td>\n\t\t<td>\n\t\t\t<div id=\"divAvailableMonitors\" style=\"OVERFLOW: auto;width: 380px;height: 130px\">\n\t\t\t\t<select name=\"similarmonitorsselected\" style=\"OVERFLOW: auto;width: 300px;height: 130px\" size=\"4\"  multiple  id=\"selectedresource\"> \t\n\t\t\t\t</select>\n\t\t\t</div>\n\t\t</td>\n\t\t</tr>\n\t\t</table>\n\t\t</td>\n\t\t</tr>  \n    <tr> \n      <td height=\"29\" colspan=\"2\" align=\"right\" class=\"tablebottom\"><input type=\"button\" value=\"");
/* 3074 */                                 out.print(FormatUtil.getString("am.webclient.cam.showmbeans.addattributes"));
/* 3075 */                                 out.write("\" onClick=\"javascript:submitAttribs();\" class='buttons' />&nbsp;</td>\n      <td class=\"tablebottom\"> \n        &nbsp;&nbsp;<input type=\"button\" value=\"");
/* 3076 */                                 out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3077 */                                 out.write("\" class='buttons' onClick=\"javascript:cancel();\" />\n      </td>\n    </tr>\n  </table>\n  \n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr> \n    <td>&nbsp;</td>\n  </tr>\n</table>\n");
/* 3078 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 3079 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3083 */                             if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 3084 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */                             }
/*      */                             
/* 3087 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 3088 */                             out.write(10);
/* 3089 */                             out.write(32);
/* 3090 */                             out.write(32);
/* 3091 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3092 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3095 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3096 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3099 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3100 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 3103 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3104 */                         out.write(" \n\n\n ");
/* 3105 */                         if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 3107 */                         out.write(10);
/* 3108 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3109 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3113 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3114 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 3117 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3118 */                       out.write(10);
/*      */                     }
/* 3120 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3121 */         out = _jspx_out;
/* 3122 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3123 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3124 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3127 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3133 */     PageContext pageContext = _jspx_page_context;
/* 3134 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3136 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3137 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 3138 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3140 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 3142 */     _jspx_th_tiles_005fput_005f0.setValue("Choose MBeans and Attributes");
/* 3143 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 3144 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 3145 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3146 */       return true;
/*      */     }
/* 3148 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3149 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3154 */     PageContext pageContext = _jspx_page_context;
/* 3155 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3157 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3158 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3159 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3161 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3163 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 3164 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3165 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3166 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3167 */       return true;
/*      */     }
/* 3169 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3170 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3175 */     PageContext pageContext = _jspx_page_context;
/* 3176 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3178 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3179 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3180 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3182 */     _jspx_th_tiles_005fput_005f2.setName("ServerLeftArea");
/*      */     
/* 3184 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/cam_leftlinksarea.jsp");
/* 3185 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3186 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3187 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3188 */       return true;
/*      */     }
/* 3190 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3191 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3196 */     PageContext pageContext = _jspx_page_context;
/* 3197 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3199 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 3200 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 3201 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3203 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 3204 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 3206 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 3207 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 3209 */           out.write(32);
/* 3210 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 3211 */             return true;
/* 3212 */           out.write(" \n    ");
/* 3213 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 3214 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3218 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 3219 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3222 */         int tmp184_183 = 0; int[] tmp184_181 = _jspx_push_body_count_c_005fcatch_005f0; int tmp186_185 = tmp184_181[tmp184_183];tmp184_181[tmp184_183] = (tmp186_185 - 1); if (tmp186_185 <= 0) break;
/* 3223 */         out = _jspx_page_context.popBody(); }
/* 3224 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3226 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 3227 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 3229 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 3234 */     PageContext pageContext = _jspx_page_context;
/* 3235 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3237 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 3238 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 3239 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 3241 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 3243 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 3244 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 3245 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 3246 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3247 */       return true;
/*      */     }
/* 3249 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3250 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3255 */     PageContext pageContext = _jspx_page_context;
/* 3256 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3258 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3259 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3260 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3262 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 3264 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 3265 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3266 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3267 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3268 */       return true;
/*      */     }
/* 3270 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3271 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\cam_005fshowmbeans_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */