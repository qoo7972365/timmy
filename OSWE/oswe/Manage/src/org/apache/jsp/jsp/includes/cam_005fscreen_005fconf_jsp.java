/*      */ package org.apache.jsp.jsp.includes;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
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
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ 
/*      */ public final class cam_005fscreen_005fconf_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   48 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   51 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   52 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   53 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   60 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   65 */     ArrayList list = null;
/*   66 */     StringBuffer sbf = new StringBuffer();
/*   67 */     ManagedApplication mo = new ManagedApplication();
/*   68 */     if (distinct)
/*      */     {
/*   70 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   74 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   77 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   79 */       ArrayList row = (ArrayList)list.get(i);
/*   80 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   81 */       if (distinct) {
/*   82 */         sbf.append(row.get(0));
/*      */       } else
/*   84 */         sbf.append(row.get(1));
/*   85 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   88 */     return sbf.toString(); }
/*      */   
/*   90 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   93 */     if (severity == null)
/*      */     {
/*   95 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   97 */     if (severity.equals("5"))
/*      */     {
/*   99 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  101 */     if (severity.equals("1"))
/*      */     {
/*  103 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  108 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  115 */     if (severity == null)
/*      */     {
/*  117 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  119 */     if (severity.equals("1"))
/*      */     {
/*  121 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  123 */     if (severity.equals("4"))
/*      */     {
/*  125 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  127 */     if (severity.equals("5"))
/*      */     {
/*  129 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  134 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  140 */     if (severity == null)
/*      */     {
/*  142 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  144 */     if (severity.equals("5"))
/*      */     {
/*  146 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  148 */     if (severity.equals("1"))
/*      */     {
/*  150 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  154 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  160 */     if (severity == null)
/*      */     {
/*  162 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  164 */     if (severity.equals("1"))
/*      */     {
/*  166 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  168 */     if (severity.equals("4"))
/*      */     {
/*  170 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  172 */     if (severity.equals("5"))
/*      */     {
/*  174 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  178 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  184 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  190 */     if (severity == 5)
/*      */     {
/*  192 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  194 */     if (severity == 1)
/*      */     {
/*  196 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  201 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  207 */     if (severity == null)
/*      */     {
/*  209 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  211 */     if (severity.equals("5"))
/*      */     {
/*  213 */       if (isAvailability) {
/*  214 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  217 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  220 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  222 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  224 */     if (severity.equals("1"))
/*      */     {
/*  226 */       if (isAvailability) {
/*  227 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  230 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  237 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  244 */     if (severity == null)
/*      */     {
/*  246 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  248 */     if (severity.equals("5"))
/*      */     {
/*  250 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  252 */     if (severity.equals("4"))
/*      */     {
/*  254 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  256 */     if (severity.equals("1"))
/*      */     {
/*  258 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  263 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  269 */     if (severity == null)
/*      */     {
/*  271 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  273 */     if (severity.equals("5"))
/*      */     {
/*  275 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  277 */     if (severity.equals("4"))
/*      */     {
/*  279 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  281 */     if (severity.equals("1"))
/*      */     {
/*  283 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  288 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  295 */     if (severity == null)
/*      */     {
/*  297 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  299 */     if (severity.equals("5"))
/*      */     {
/*  301 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  303 */     if (severity.equals("4"))
/*      */     {
/*  305 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  307 */     if (severity.equals("1"))
/*      */     {
/*  309 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  314 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  322 */     StringBuffer out = new StringBuffer();
/*  323 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  324 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  325 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  326 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  327 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  328 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  329 */     out.append("</tr>");
/*  330 */     out.append("</form></table>");
/*  331 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  338 */     if (val == null)
/*      */     {
/*  340 */       return "-";
/*      */     }
/*      */     
/*  343 */     String ret = FormatUtil.formatNumber(val);
/*  344 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  345 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  348 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  352 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  360 */     StringBuffer out = new StringBuffer();
/*  361 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  362 */     out.append("<tr>");
/*  363 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  365 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  367 */     out.append("</tr>");
/*  368 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  372 */       if (j % 2 == 0)
/*      */       {
/*  374 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  378 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  381 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  383 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  386 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  390 */       out.append("</tr>");
/*      */     }
/*  392 */     out.append("</table>");
/*  393 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  394 */     out.append("<tr>");
/*  395 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  396 */     out.append("</tr>");
/*  397 */     out.append("</table>");
/*  398 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  404 */     StringBuffer out = new StringBuffer();
/*  405 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  406 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  407 */     out.append("<tr>");
/*  408 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  409 */     out.append("<tr>");
/*  410 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  411 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  412 */     out.append("</tr>");
/*  413 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  416 */       out.append("<tr>");
/*  417 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  418 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  419 */       out.append("</tr>");
/*      */     }
/*      */     
/*  422 */     out.append("</table>");
/*  423 */     out.append("</table>");
/*  424 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  429 */     if (severity.equals("0"))
/*      */     {
/*  431 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  435 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  442 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  455 */     StringBuffer out = new StringBuffer();
/*  456 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  457 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  459 */       out.append("<tr>");
/*  460 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  461 */       out.append("</tr>");
/*      */       
/*      */ 
/*  464 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  466 */         String borderclass = "";
/*      */         
/*      */ 
/*  469 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  471 */         out.append("<tr>");
/*      */         
/*  473 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  474 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  475 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  481 */     out.append("</table><br>");
/*  482 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  483 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  485 */       List sLinks = secondLevelOfLinks[0];
/*  486 */       List sText = secondLevelOfLinks[1];
/*  487 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  490 */         out.append("<tr>");
/*  491 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  492 */         out.append("</tr>");
/*  493 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  495 */           String borderclass = "";
/*      */           
/*      */ 
/*  498 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  500 */           out.append("<tr>");
/*      */           
/*  502 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  503 */           if (sLinks.get(i).toString().length() == 0) {
/*  504 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  507 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  509 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  513 */     out.append("</table>");
/*  514 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  521 */     StringBuffer out = new StringBuffer();
/*  522 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  523 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  525 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  527 */         out.append("<tr>");
/*  528 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  529 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  533 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  535 */           String borderclass = "";
/*      */           
/*      */ 
/*  538 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  540 */           out.append("<tr>");
/*      */           
/*  542 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  543 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  544 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  547 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  550 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  555 */     out.append("</table><br>");
/*  556 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  557 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  559 */       List sLinks = secondLevelOfLinks[0];
/*  560 */       List sText = secondLevelOfLinks[1];
/*  561 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  564 */         out.append("<tr>");
/*  565 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  566 */         out.append("</tr>");
/*  567 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  569 */           String borderclass = "";
/*      */           
/*      */ 
/*  572 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  574 */           out.append("<tr>");
/*      */           
/*  576 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  577 */           if (sLinks.get(i).toString().length() == 0) {
/*  578 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  581 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  583 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  587 */     out.append("</table>");
/*  588 */     return out.toString();
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
/*  601 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  604 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  607 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  610 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  613 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  616 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  619 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  622 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  630 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  635 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  640 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  645 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  650 */     if (val != null)
/*      */     {
/*  652 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  656 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  661 */     if (val == null) {
/*  662 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  666 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  671 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  677 */     if (val != null)
/*      */     {
/*  679 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  683 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  689 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  694 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  698 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  703 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  708 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  713 */     String hostaddress = "";
/*  714 */     String ip = request.getHeader("x-forwarded-for");
/*  715 */     if (ip == null)
/*  716 */       ip = request.getRemoteAddr();
/*  717 */     InetAddress add = null;
/*  718 */     if (ip.equals("127.0.0.1")) {
/*  719 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  723 */       add = InetAddress.getByName(ip);
/*      */     }
/*  725 */     hostaddress = add.getHostName();
/*  726 */     if (hostaddress.indexOf('.') != -1) {
/*  727 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  728 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  732 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  737 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  743 */     if (severity == null)
/*      */     {
/*  745 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  747 */     if (severity.equals("5"))
/*      */     {
/*  749 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  751 */     if (severity.equals("1"))
/*      */     {
/*  753 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  758 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  763 */     ResultSet set = null;
/*  764 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  765 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  767 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  768 */       if (set.next()) { String str1;
/*  769 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  770 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  773 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  778 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  781 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  783 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  787 */     StringBuffer rca = new StringBuffer();
/*  788 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  789 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  792 */     int rcalength = key.length();
/*  793 */     String split = "6. ";
/*  794 */     int splitPresent = key.indexOf(split);
/*  795 */     String div1 = "";String div2 = "";
/*  796 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  798 */       if (rcalength > 180) {
/*  799 */         rca.append("<span class=\"rca-critical-text\">");
/*  800 */         getRCATrimmedText(key, rca);
/*  801 */         rca.append("</span>");
/*      */       } else {
/*  803 */         rca.append("<span class=\"rca-critical-text\">");
/*  804 */         rca.append(key);
/*  805 */         rca.append("</span>");
/*      */       }
/*  807 */       return rca.toString();
/*      */     }
/*  809 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  810 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  811 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  812 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  813 */     getRCATrimmedText(div1, rca);
/*  814 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  817 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  818 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  819 */     getRCATrimmedText(div2, rca);
/*  820 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  822 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  827 */     String[] st = msg.split("<br>");
/*  828 */     for (int i = 0; i < st.length; i++) {
/*  829 */       String s = st[i];
/*  830 */       if (s.length() > 180) {
/*  831 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  833 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  837 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  838 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  840 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  844 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  845 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  846 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  849 */       if (key == null) {
/*  850 */         return ret;
/*      */       }
/*      */       
/*  853 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  854 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  857 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  858 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  859 */       set = AMConnectionPool.executeQueryStmt(query);
/*  860 */       if (set.next())
/*      */       {
/*  862 */         String helpLink = set.getString("LINK");
/*  863 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  866 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  872 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  891 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  882 */         if (set != null) {
/*  883 */           AMConnectionPool.closeStatement(set);
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
/*  897 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  898 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  900 */       String entityStr = (String)keys.nextElement();
/*  901 */       String mmessage = temp.getProperty(entityStr);
/*  902 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  903 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  905 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  911 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/*      */   private void debug(String debugMessage)
/*      */   {
/*  924 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  934 */     String des = new String();
/*  935 */     while (str.indexOf(find) != -1) {
/*  936 */       des = des + str.substring(0, str.indexOf(find));
/*  937 */       des = des + replace;
/*  938 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  940 */     des = des + str;
/*  941 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  948 */       if (alert == null)
/*      */       {
/*  950 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  952 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  954 */         return "&nbsp;";
/*      */       }
/*      */       
/*  957 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  959 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  962 */       int rcalength = test.length();
/*  963 */       if (rcalength < 300)
/*      */       {
/*  965 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  969 */       StringBuffer out = new StringBuffer();
/*  970 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  971 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  972 */       out.append("</div>");
/*  973 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  974 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  975 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  980 */       ex.printStackTrace();
/*      */     }
/*  982 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  988 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  993 */     ArrayList attribIDs = new ArrayList();
/*  994 */     ArrayList resIDs = new ArrayList();
/*  995 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  997 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  999 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1001 */       String resourceid = "";
/* 1002 */       String resourceType = "";
/* 1003 */       if (type == 2) {
/* 1004 */         resourceid = (String)row.get(0);
/* 1005 */         resourceType = (String)row.get(3);
/*      */       }
/* 1007 */       else if (type == 3) {
/* 1008 */         resourceid = (String)row.get(0);
/* 1009 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1012 */         resourceid = (String)row.get(6);
/* 1013 */         resourceType = (String)row.get(7);
/*      */       }
/* 1015 */       resIDs.add(resourceid);
/* 1016 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1017 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1019 */       String healthentity = null;
/* 1020 */       String availentity = null;
/* 1021 */       if (healthid != null) {
/* 1022 */         healthentity = resourceid + "_" + healthid;
/* 1023 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1026 */       if (availid != null) {
/* 1027 */         availentity = resourceid + "_" + availid;
/* 1028 */         entitylist.add(availentity);
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
/* 1042 */     Properties alert = getStatus(entitylist);
/* 1043 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1048 */     int size = monitorList.size();
/*      */     
/* 1050 */     String[] severity = new String[size];
/*      */     
/* 1052 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1054 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1055 */       String resourceName1 = (String)row1.get(7);
/* 1056 */       String resourceid1 = (String)row1.get(6);
/* 1057 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1058 */       if (severity[j] == null)
/*      */       {
/* 1060 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1064 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1066 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1068 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1071 */         if (sev > 0) {
/* 1072 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1073 */           monitorList.set(k, monitorList.get(j));
/* 1074 */           monitorList.set(j, t);
/* 1075 */           String temp = severity[k];
/* 1076 */           severity[k] = severity[j];
/* 1077 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1083 */     int z = 0;
/* 1084 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1087 */       int i = 0;
/* 1088 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1091 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1095 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1099 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1101 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1104 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1108 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1111 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1112 */       String resourceName1 = (String)row1.get(7);
/* 1113 */       String resourceid1 = (String)row1.get(6);
/* 1114 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1115 */       if (hseverity[j] == null)
/*      */       {
/* 1117 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1122 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1124 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1127 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1130 */         if (hsev > 0) {
/* 1131 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1132 */           monitorList.set(k, monitorList.get(j));
/* 1133 */           monitorList.set(j, t);
/* 1134 */           String temp1 = hseverity[k];
/* 1135 */           hseverity[k] = hseverity[j];
/* 1136 */           hseverity[j] = temp1;
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
/* 1148 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1149 */     boolean forInventory = false;
/* 1150 */     String trdisplay = "none";
/* 1151 */     String plusstyle = "inline";
/* 1152 */     String minusstyle = "none";
/* 1153 */     String haidTopLevel = "";
/* 1154 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1156 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1158 */         haidTopLevel = request.getParameter("haid");
/* 1159 */         forInventory = true;
/* 1160 */         trdisplay = "table-row;";
/* 1161 */         plusstyle = "none";
/* 1162 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1169 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1172 */     ArrayList listtoreturn = new ArrayList();
/* 1173 */     StringBuffer toreturn = new StringBuffer();
/* 1174 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1175 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1176 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1178 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1180 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1181 */       String childresid = (String)singlerow.get(0);
/* 1182 */       String childresname = (String)singlerow.get(1);
/* 1183 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1184 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1185 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1186 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1187 */       String unmanagestatus = (String)singlerow.get(5);
/* 1188 */       String actionstatus = (String)singlerow.get(6);
/* 1189 */       String linkclass = "monitorgp-links";
/* 1190 */       String titleforres = childresname;
/* 1191 */       String titilechildresname = childresname;
/* 1192 */       String childimg = "/images/trcont.png";
/* 1193 */       String flag = "enable";
/* 1194 */       String dcstarted = (String)singlerow.get(8);
/* 1195 */       String configMonitor = "";
/* 1196 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1197 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1199 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1201 */       if (singlerow.get(7) != null)
/*      */       {
/* 1203 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1205 */       String haiGroupType = "0";
/* 1206 */       if ("HAI".equals(childtype))
/*      */       {
/* 1208 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1210 */       childimg = "/images/trend.png";
/* 1211 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1212 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1213 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1215 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1217 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1219 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1220 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1223 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1225 */         linkclass = "disabledtext";
/* 1226 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1228 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1229 */       String availmouseover = "";
/* 1230 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1232 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1234 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1235 */       String healthmouseover = "";
/* 1236 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1238 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1241 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1242 */       int spacing = 0;
/* 1243 */       if (level >= 1)
/*      */       {
/* 1245 */         spacing = 40 * level;
/*      */       }
/* 1247 */       if (childtype.equals("HAI"))
/*      */       {
/* 1249 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1250 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1251 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1253 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1254 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1255 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1256 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1257 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1258 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1259 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1260 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1261 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1262 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1263 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1265 */         if (!forInventory)
/*      */         {
/* 1267 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1270 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1272 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1274 */           actions = editlink + actions;
/*      */         }
/* 1276 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1278 */           actions = actions + associatelink;
/*      */         }
/* 1280 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1281 */         String arrowimg = "";
/* 1282 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1284 */           actions = "";
/* 1285 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1286 */           checkbox = "";
/* 1287 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1289 */         if (isIt360)
/*      */         {
/* 1291 */           actionimg = "";
/* 1292 */           actions = "";
/* 1293 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1294 */           checkbox = "";
/*      */         }
/*      */         
/* 1297 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1299 */           actions = "";
/*      */         }
/* 1301 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1303 */           checkbox = "";
/*      */         }
/*      */         
/* 1306 */         String resourcelink = "";
/*      */         
/* 1308 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1310 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1314 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1317 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1318 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1319 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1320 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1321 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1322 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1323 */         if (!isIt360)
/*      */         {
/* 1325 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1329 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1332 */         toreturn.append("</tr>");
/* 1333 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1335 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1336 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1340 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1341 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1344 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1348 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1350 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1351 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1352 */             toreturn.append(assocMessage);
/* 1353 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1354 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1355 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1356 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1362 */         String resourcelink = null;
/* 1363 */         boolean hideEditLink = false;
/* 1364 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1366 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1367 */           hideEditLink = true;
/* 1368 */           if (isIt360)
/*      */           {
/* 1370 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1374 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1376 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1378 */           hideEditLink = true;
/* 1379 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1380 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1385 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1388 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1389 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1390 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1391 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1392 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1393 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1394 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1395 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1396 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1397 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1398 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1399 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1400 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1402 */         if (hideEditLink)
/*      */         {
/* 1404 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1406 */         if (!forInventory)
/*      */         {
/* 1408 */           removefromgroup = "";
/*      */         }
/* 1410 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1411 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1412 */           actions = actions + configcustomfields;
/*      */         }
/* 1414 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1416 */           actions = editlink + actions;
/*      */         }
/* 1418 */         String managedLink = "";
/* 1419 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1421 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1422 */           actions = "";
/* 1423 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1424 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1427 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1429 */           checkbox = "";
/*      */         }
/*      */         
/* 1432 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1434 */           actions = "";
/*      */         }
/* 1436 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1437 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1438 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1439 */         if (isIt360)
/*      */         {
/* 1441 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1445 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1447 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1448 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1449 */         if (!isIt360)
/*      */         {
/* 1451 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1455 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1457 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1460 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1467 */       StringBuilder toreturn = new StringBuilder();
/* 1468 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1469 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1470 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1471 */       String title = "";
/* 1472 */       message = EnterpriseUtil.decodeString(message);
/* 1473 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1474 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1475 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1477 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1479 */       else if ("5".equals(severity))
/*      */       {
/* 1481 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1485 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1487 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1488 */       toreturn.append(v);
/*      */       
/* 1490 */       toreturn.append(link);
/* 1491 */       if (severity == null)
/*      */       {
/* 1493 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1495 */       else if (severity.equals("5"))
/*      */       {
/* 1497 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1499 */       else if (severity.equals("4"))
/*      */       {
/* 1501 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1503 */       else if (severity.equals("1"))
/*      */       {
/* 1505 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1510 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1512 */       toreturn.append("</a>");
/* 1513 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1517 */       ex.printStackTrace();
/*      */     }
/* 1519 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1526 */       StringBuilder toreturn = new StringBuilder();
/* 1527 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1528 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1529 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1530 */       if (message == null)
/*      */       {
/* 1532 */         message = "";
/*      */       }
/*      */       
/* 1535 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1536 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1538 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1539 */       toreturn.append(v);
/*      */       
/* 1541 */       toreturn.append(link);
/*      */       
/* 1543 */       if (severity == null)
/*      */       {
/* 1545 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1547 */       else if (severity.equals("5"))
/*      */       {
/* 1549 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1551 */       else if (severity.equals("1"))
/*      */       {
/* 1553 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1558 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1560 */       toreturn.append("</a>");
/* 1561 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1567 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1570 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1571 */     if (invokeActions != null) {
/* 1572 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1573 */       while (iterator.hasNext()) {
/* 1574 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1575 */         if (actionmap.containsKey(actionid)) {
/* 1576 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1581 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1585 */     String actionLink = "";
/* 1586 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1587 */     String query = "";
/* 1588 */     ResultSet rs = null;
/* 1589 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1590 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1591 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1592 */       actionLink = "method=" + methodName;
/*      */     }
/* 1594 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1595 */       actionLink = methodName;
/*      */     }
/* 1597 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1598 */     Iterator itr = methodarglist.iterator();
/* 1599 */     boolean isfirstparam = true;
/* 1600 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1601 */     while (itr.hasNext()) {
/* 1602 */       HashMap argmap = (HashMap)itr.next();
/* 1603 */       String argtype = (String)argmap.get("TYPE");
/* 1604 */       String argname = (String)argmap.get("IDENTITY");
/* 1605 */       String paramname = (String)argmap.get("PARAMETER");
/* 1606 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1607 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1608 */         isfirstparam = false;
/* 1609 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1611 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1615 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1619 */         actionLink = actionLink + "&";
/*      */       }
/* 1621 */       String paramValue = null;
/* 1622 */       String tempargname = argname;
/* 1623 */       if (commonValues.getProperty(tempargname) != null) {
/* 1624 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1627 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1628 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1629 */           if (dbType.equals("mysql")) {
/* 1630 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1633 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1635 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1637 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1638 */             if (rs.next()) {
/* 1639 */               paramValue = rs.getString("VALUE");
/* 1640 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1644 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1648 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1651 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1656 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1657 */           paramValue = rowId;
/*      */         }
/* 1659 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1660 */           paramValue = managedObjectName;
/*      */         }
/* 1662 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1663 */           paramValue = resID;
/*      */         }
/* 1665 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1666 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1669 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1671 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1672 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1673 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1675 */     return actionLink;
/*      */   }
/*      */   
/* 1678 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1679 */     String dependentAttribute = null;
/* 1680 */     String align = "left";
/*      */     
/* 1682 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1683 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1684 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1685 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1686 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1687 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1688 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1689 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1690 */       align = "center";
/*      */     }
/*      */     
/* 1693 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1694 */     String actualdata = "";
/*      */     
/* 1696 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1697 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1698 */         actualdata = availValue;
/*      */       }
/* 1700 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1701 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1705 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1706 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1709 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1715 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1716 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1717 */       toreturn.append("<table>");
/* 1718 */       toreturn.append("<tr>");
/* 1719 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1720 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1721 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1722 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1723 */         String toolTip = "";
/* 1724 */         String hideClass = "";
/* 1725 */         String textStyle = "";
/* 1726 */         boolean isreferenced = true;
/* 1727 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1728 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1729 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1730 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1732 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1733 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1734 */           while (valueList.hasMoreTokens()) {
/* 1735 */             String dependentVal = valueList.nextToken();
/* 1736 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1737 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1738 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1740 */               toolTip = "";
/* 1741 */               hideClass = "";
/* 1742 */               isreferenced = false;
/* 1743 */               textStyle = "disabledtext";
/* 1744 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1748 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1749 */           toolTip = "";
/* 1750 */           hideClass = "";
/* 1751 */           isreferenced = false;
/* 1752 */           textStyle = "disabledtext";
/* 1753 */           if (dependentImageMap != null) {
/* 1754 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1755 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1758 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1762 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1763 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1764 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1765 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1766 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1767 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1769 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1770 */           if (isreferenced) {
/* 1771 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1775 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1776 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1777 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1778 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1779 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1780 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1782 */           toreturn.append("</span>");
/* 1783 */           toreturn.append("</a>");
/* 1784 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1787 */       toreturn.append("</tr>");
/* 1788 */       toreturn.append("</table>");
/* 1789 */       toreturn.append("</td>");
/*      */     } else {
/* 1791 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1794 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1798 */     String colTime = null;
/* 1799 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1800 */     if ((rows != null) && (rows.size() > 0)) {
/* 1801 */       Iterator<String> itr = rows.iterator();
/* 1802 */       String maxColQuery = "";
/* 1803 */       for (;;) { if (itr.hasNext()) {
/* 1804 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1805 */           ResultSet maxCol = null;
/*      */           try {
/* 1807 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1808 */             while (maxCol.next()) {
/* 1809 */               if (colTime == null) {
/* 1810 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1813 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1822 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1824 */               if (maxCol != null)
/* 1825 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1827 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1822 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1824 */               if (maxCol != null)
/* 1825 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1827 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1832 */     return colTime;
/*      */   }
/*      */   
/* 1835 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1836 */     tablename = null;
/* 1837 */     ResultSet rsTable = null;
/* 1838 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1840 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1841 */       while (rsTable.next()) {
/* 1842 */         tablename = rsTable.getString("DATATABLE");
/* 1843 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1844 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1857 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1848 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1851 */         if (rsTable != null)
/* 1852 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1854 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1860 */     String argsList = "";
/* 1861 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1863 */       if (showArgsMap.get(row) != null) {
/* 1864 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1865 */         if (showArgslist != null) {
/* 1866 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1867 */             if (argsList.trim().equals("")) {
/* 1868 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1871 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1878 */       e.printStackTrace();
/* 1879 */       return "";
/*      */     }
/* 1881 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1886 */     String argsList = "";
/* 1887 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1890 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1892 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1893 */         if (hideArgsList != null)
/*      */         {
/* 1895 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1897 */             if (argsList.trim().equals(""))
/*      */             {
/* 1899 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1903 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1911 */       ex.printStackTrace();
/*      */     }
/* 1913 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1917 */     StringBuilder toreturn = new StringBuilder();
/* 1918 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1925 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1926 */       Iterator itr = tActionList.iterator();
/* 1927 */       while (itr.hasNext()) {
/* 1928 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1929 */         String confirmmsg = "";
/* 1930 */         String link = "";
/* 1931 */         String isJSP = "NO";
/* 1932 */         HashMap tactionMap = (HashMap)itr.next();
/* 1933 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1934 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1935 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1936 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1937 */           (actionmap.containsKey(actionId))) {
/* 1938 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1939 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1940 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1941 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1942 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1944 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1950 */           if (isTableAction) {
/* 1951 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1954 */             tableName = "Link";
/* 1955 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1956 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1957 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1958 */             toreturn.append("</a></td>");
/*      */           }
/* 1960 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1961 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1962 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1963 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1969 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1975 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1977 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1978 */       Properties prop = (Properties)node.getUserObject();
/* 1979 */       String mgID = prop.getProperty("label");
/* 1980 */       String mgName = prop.getProperty("value");
/* 1981 */       String isParent = prop.getProperty("isParent");
/* 1982 */       int mgIDint = Integer.parseInt(mgID);
/* 1983 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1985 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1987 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1988 */       if (node.getChildCount() > 0)
/*      */       {
/* 1990 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1992 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1994 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1996 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2000 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2005 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2007 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2009 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2011 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2015 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2018 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2019 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2021 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2025 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2027 */       if (node.getChildCount() > 0)
/*      */       {
/* 2029 */         builder.append("<UL>");
/* 2030 */         printMGTree(node, builder);
/* 2031 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2036 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2037 */     StringBuffer toReturn = new StringBuffer();
/* 2038 */     String table = "-";
/*      */     try {
/* 2040 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2041 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2042 */       float total = 0.0F;
/* 2043 */       while (it.hasNext()) {
/* 2044 */         String attName = (String)it.next();
/* 2045 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2046 */         boolean roundOffData = false;
/* 2047 */         if ((data != null) && (!data.equals(""))) {
/* 2048 */           if (data.indexOf(",") != -1) {
/* 2049 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2052 */             float value = Float.parseFloat(data);
/* 2053 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2056 */             total += value;
/* 2057 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2060 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2065 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2066 */       while (attVsWidthList.hasNext()) {
/* 2067 */         String attName = (String)attVsWidthList.next();
/* 2068 */         String data = (String)attVsWidthProps.get(attName);
/* 2069 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2070 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2071 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2072 */         String className = (String)graphDetails.get("ClassName");
/* 2073 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2074 */         if (percentage < 1.0F)
/*      */         {
/* 2076 */           data = percentage + "";
/*      */         }
/* 2078 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2080 */       if (toReturn.length() > 0) {
/* 2081 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2085 */       e.printStackTrace();
/*      */     }
/* 2087 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2093 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2094 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2095 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2096 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2097 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2098 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2099 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2100 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2101 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2104 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2105 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2106 */       splitvalues[0] = multiplecondition.toString();
/* 2107 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2110 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2115 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2116 */     if (thresholdType != 3) {
/* 2117 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2118 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2119 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2120 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2121 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2122 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2124 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2125 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2126 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2127 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2128 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2129 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2131 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2132 */     if (updateSelected != null) {
/* 2133 */       updateSelected[0] = "selected";
/*      */     }
/* 2135 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2140 */       StringBuffer toreturn = new StringBuffer("");
/* 2141 */       if (commaSeparatedMsgId != null) {
/* 2142 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2143 */         int count = 0;
/* 2144 */         while (msgids.hasMoreTokens()) {
/* 2145 */           String id = msgids.nextToken();
/* 2146 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2147 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2148 */           count++;
/* 2149 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2150 */             if (toreturn.length() == 0) {
/* 2151 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2153 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2154 */             if (!image.trim().equals("")) {
/* 2155 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2157 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2158 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2161 */         if (toreturn.length() > 0) {
/* 2162 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2166 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2169 */       e.printStackTrace(); }
/* 2170 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String addBreakAt(String str, int len)
/*      */   {
/* 2179 */     if (len == 0) return str;
/* 2180 */     String temp = str;
/* 2181 */     StringBuffer ret = new StringBuffer("");
/* 2182 */     while (temp.length() > len)
/*      */     {
/* 2184 */       ret.append(temp.substring(0, len));
/* 2185 */       ret.append("<br>");
/* 2186 */       temp = temp.substring(len);
/*      */     }
/* 2188 */     ret.append(temp);
/* 2189 */     return ret.toString();
/*      */   }
/*      */   
/* 2192 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2198 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2199 */   static { _jspx_dependants.put("/jsp/includes/mop_actions.jspf", Long.valueOf(1473429417000L));
/* 2200 */     _jspx_dependants.put("/jsp/includes/cam_screen.jspf", Long.valueOf(1473429417000L));
/* 2201 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2219 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2223 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2234 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2238 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2242 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.release();
/* 2243 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.release();
/* 2244 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.release();
/* 2245 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2246 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2247 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2254 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2257 */     JspWriter out = null;
/* 2258 */     Object page = this;
/* 2259 */     JspWriter _jspx_out = null;
/* 2260 */     PageContext _jspx_page_context = null;
/*      */     
/* 2262 */     Object _jspx_acolumn_1 = null;
/* 2263 */     Integer _jspx_i_1 = null;
/*      */     try
/*      */     {
/* 2266 */       response.setContentType("text/html;charset=UTF-8");
/* 2267 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2269 */       _jspx_page_context = pageContext;
/* 2270 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2271 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2272 */       session = pageContext.getSession();
/* 2273 */       out = pageContext.getOut();
/* 2274 */       _jspx_out = out;
/*      */       
/* 2276 */       out.write("<!--$Id$-->\n\n");
/* 2277 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2279 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2280 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2282 */       out.write(10);
/* 2283 */       out.write(10);
/* 2284 */       out.write(10);
/* 2285 */       out.write(10);
/* 2286 */       out.write(10);
/*      */       
/* 2288 */       String resourceid1 = request.getParameter("resourceid");
/* 2289 */       String tabSel = request.getAttribute("tabId") + "";
/* 2290 */       String granularSel = request.getAttribute("granularitySelected") + "";
/* 2291 */       String periodSel = request.getAttribute("perioicitySelected") + "";
/* 2292 */       String fromConfPage = "#tabId-" + tabSel + "&periodicity-" + periodSel + "&granularity-" + granularSel;
/* 2293 */       request.setAttribute("fromConfPage", fromConfPage);
/* 2294 */       String encodeurl1 = URLEncoder.encode("/showresource.do?resourceid=" + resourceid1 + "&method=showResourceForResourceID" + fromConfPage);
/* 2295 */       String mopRedirectString = encodeurl1;
/* 2296 */       String linkForMopAction = "/MBeanOperationAction.do?method=showInitialScreen&resourceid=" + resourceid1 + "&redirectto=" + mopRedirectString;
/* 2297 */       ArrayList mopActions = FaultUtil.getMBeanOperationActionsForResourceID(resourceid1, request.getRemoteUser(), request.isUserInRole("OPERATOR"));
/* 2298 */       if (mopActions.size() > 0)
/*      */       {
/* 2300 */         request.setAttribute("executeMopActions", mopActions);
/*      */       }
/* 2302 */       HashMap threadProps = FaultUtil.getThreadDumpOperationForResourceID(resourceid1);
/* 2303 */       if ((threadProps != null) && (threadProps.size() > 0))
/*      */       {
/* 2305 */         request.setAttribute("threadProps", threadProps);
/*      */       }
/* 2307 */       request.setAttribute("showrefreshnowoption", "true");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2312 */       request.setAttribute("isfromresourcepage", "true");
/*      */       
/* 2314 */       out.write("\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td width=\"50%\" style=\"padding-top:20px;\">\n");
/* 2315 */       out.write("<!--$Id$-->\n");
/* 2316 */       com.adventnet.appmanager.cam.beans.CAMGraphs camGraph = null;
/* 2317 */       camGraph = (com.adventnet.appmanager.cam.beans.CAMGraphs)_jspx_page_context.getAttribute("camGraph", 1);
/* 2318 */       if (camGraph == null) {
/* 2319 */         camGraph = new com.adventnet.appmanager.cam.beans.CAMGraphs();
/* 2320 */         _jspx_page_context.setAttribute("camGraph", camGraph, 1);
/*      */       }
/* 2322 */       out.write("\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n\n");
/*      */       
/* 2324 */       long camStartTime = System.currentTimeMillis();
/*      */       
/* 2326 */       String camIDI = (String)request.getAttribute("camid");
/* 2327 */       String screenIDI = (String)request.getAttribute("screenid");
/* 2328 */       List screenInfoI = (List)request.getAttribute("screeninfo");
/* 2329 */       boolean perfAvailResourceScreen = false;
/* 2330 */       String resourceID = "";
/* 2331 */       String fromConfPage1 = request.getAttribute("fromConfPage") + "";
/* 2332 */       String haidI = request.getParameter("haid");
/* 2333 */       if ((haidI == null) || (haidI.trim().length() == 0)) {
/* 2334 */         haidI = request.getParameter("haid");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2339 */       String isFromResourcePage = (String)request.getAttribute("isfromresourcepage");
/* 2340 */       if (isFromResourcePage == null) {
/* 2341 */         isFromResourcePage = "NA";
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2346 */       if ("true".equals(isFromResourcePage))
/*      */       {
/*      */ 
/*      */ 
/* 2350 */         resourceID = (String)request.getAttribute("resourceid");
/* 2351 */         if ((resourceID == null) || (resourceID.trim().length() == 0)) {
/* 2352 */           resourceID = request.getParameter("resourceid");
/*      */         }
/*      */         
/* 2355 */         camIDI = resourceID;
/* 2356 */         perfAvailResourceScreen = true;
/*      */         
/*      */ 
/* 2359 */         request.setAttribute("screenInfo", screenInfoI);
/* 2360 */         List tmpList = CAMDBUtil.getScreens(Integer.parseInt(camIDI));
/* 2361 */         if (tmpList.size() == 0)
/*      */         {
/* 2363 */           CAMDBUtil.createDefaultScreenForResource(Integer.parseInt(camIDI));
/* 2364 */           tmpList = CAMDBUtil.getScreens(Integer.parseInt(camIDI));
/* 2365 */           screenInfoI = (List)tmpList.get(0);
/*      */         }
/*      */         else {
/* 2368 */           screenInfoI = (List)tmpList.get(0);
/*      */         }
/*      */         
/* 2371 */         screenIDI = (String)screenInfoI.get(0);
/* 2372 */         request.setAttribute("screenid", screenIDI);
/*      */       }
/*      */       
/*      */ 
/* 2376 */       Map fromDB = CAMDBUtil.getCustomizedDataForScreenAdminActivity(Long.parseLong(screenIDI));
/* 2377 */       List screenConfigList = (List)fromDB.get("completedata");
/*      */       
/*      */ 
/* 2380 */       List reportsData = (List)fromDB.get("reportsdata");
/*      */       
/*      */ 
/* 2383 */       Map dcTimeMap = CAMDBUtil.getLatestCollectionTimes(Long.parseLong(screenIDI));
/* 2384 */       Map attribResourceMap = CAMDBUtil.getResourceNamesForAttributesInScreen(Integer.parseInt(screenIDI));
/*      */       
/* 2386 */       List screenAttribInfo = CAMDBUtil.getScreenAttributeInfo(Long.parseLong(screenIDI), Integer.parseInt((String)screenInfoI.get(3)), dcTimeMap);
/* 2387 */       boolean scalarAttribsPresent = screenAttribInfo.size() > 0;
/* 2388 */       List colScreenAttribInfo = CAMDBUtil.getScreenInfoForColumnarData(Long.parseLong(screenIDI));
/* 2389 */       boolean columnarAttribsPresent = colScreenAttribInfo.size() > 0;
/* 2390 */       boolean attribsPresent = (scalarAttribsPresent == true) || (columnarAttribsPresent == true);
/* 2391 */       String quickNote = "This page displays the attributes monitored from various resources as configured during design time. Placing the mouse over the value for table data will display the time when the data was collected. Time intervals will be different if the attributes are from different resources.";
/*      */       
/*      */ 
/*      */ 
/* 2395 */       if (request.getAttribute("quicknote") == null) {
/* 2396 */         request.setAttribute("quicknote", quickNote);
/*      */       }
/* 2398 */       String configureLink = "/ShowCAM.do?method=configureScreen&screenid=" + screenIDI + "&camid=" + camIDI + "&haid=" + haidI + "&isfromresourcepage=" + isFromResourcePage;
/* 2399 */       if ((request.isUserInRole("ENTERPRISEADMIN")) && (com.adventnet.appmanager.util.Constants.isSsoEnabled()))
/*      */       {
/* 2401 */         StringBuilder builder = new StringBuilder();
/* 2402 */         builder.append("https:").append("//");
/* 2403 */         builder.append(com.adventnet.appmanager.util.Constants.getAppHostName()).append(":");
/* 2404 */         builder.append(com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.getsslport()).append(configureLink);
/* 2405 */         configureLink = builder.toString();
/*      */       }
/* 2407 */       request.setAttribute("configurelink", configureLink);
/* 2408 */       String secondLevelLinkTitle; if (!perfAvailResourceScreen)
/*      */       {
/*      */ 
/* 2411 */         List sLinks = new ArrayList();
/* 2412 */         List sText = new ArrayList();
/*      */         
/* 2414 */         sLinks.add("Add ScreenXXXX");
/* 2415 */         sText.add("/ShowCAM.do?method=addScreen&camid=" + camIDI + "&haid=" + haidI);
/*      */         
/*      */ 
/*      */ 
/* 2419 */         sLinks.add("Customize");
/* 2420 */         sText.add(configureLink);
/*      */         
/*      */ 
/* 2423 */         sLinks.add("<a href=\"/CAMDeleteScreen.do?method=deleteScreen&screenid=" + screenIDI + "&camid=" + camIDI + "&haid=" + haidI + "\" onclick=\"return deleteScreen();\" class='staticlinks'>Delete Screen</a>");
/* 2424 */         sText.add("");
/*      */         
/*      */ 
/*      */ 
/* 2428 */         List[] secondLevelOfLinks = { sText, sLinks };
/* 2429 */         secondLevelLinkTitle = "Admin Activity";
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2438 */       String configureThresholdRedirectLink = "/ShowCAM.do?method=showScreen&haid=" + haidI + "&camid=" + camIDI + "&screenid=" + screenIDI;
/*      */       
/* 2440 */       if ("true".equals(isFromResourcePage)) {
/* 2441 */         configureThresholdRedirectLink = "/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID";
/*      */       }
/*      */       
/* 2444 */       configureThresholdRedirectLink = URLEncoder.encode(configureThresholdRedirectLink);
/*      */       
/*      */ 
/*      */ 
/* 2448 */       out.write("\n\n\n\n<script language=\"JavaScript1.2\">\n\nfunction showAttribGraph(attribID,mbean) {\n       var featurelist = \"toolbar=no,status=no,menubar=no,width=450,height=300,scrollbars=yes\";\n       var url = \"/ShowCAM.do?method=showSingleGraphScreen&attributeid=\" + attribID+\"&mbean=\" +mbean;\n       popUp = window.open(url,'popUp',featurelist);\n       return false;\n}\n\n</SCRIPT>\n<!--This script is used to show horizontal bar if customer attributes tables have more number of attributes in SNMP Devices.--> \n<script>\n    jQuery(document).ready(function(){\n        var windowWidth = jQuery(window).width();\n        windowWidth = windowWidth*(81/100);\n        jQuery('.horTableScroll').css({'width':windowWidth});//No I18N\n\n    });\n</script>\n\n<style>\n    .horTableScroll {\n        overflow-x:auto;\n    }    \n</style>\n<!--");
/*      */       
/* 2450 */       String camid = request.getParameter("camid");
/*      */       
/*      */ 
/* 2453 */       out.write("-->\n\n\n<script>\nfunction validateAndSubmit()\n{\n   if(trimAll(document.AMActionForm.camname.value)==\"\")\n   {\n        alert('");
/* 2454 */       out.print(FormatUtil.getString("am.webclient.cam.namealert"));
/* 2455 */       out.write("');\n        return;\n   }\n   document.AMActionForm.submit();\n}\n\n</script>\n\n");
/*      */       
/* 2457 */       List list = CAMDBUtil.getCAMDetails(camIDI);
/* 2458 */       String camName = (String)list.get(0);
/* 2459 */       String camDescription = (String)list.get(2);
/*      */       
/* 2461 */       out.write("\n\n\n\n");
/*      */       
/* 2463 */       if ("true".equals(request.getParameter("editPage")))
/*      */       {
/* 2465 */         out.write("\n<div id=\"edit\" style=\"display:block\">\n");
/*      */       } else {
/* 2467 */         out.write("\n<div id=\"edit\" style=\"display:none\">\n");
/*      */       }
/* 2469 */       out.write("\n\n<html:form action=\"/adminAction\"  method=\"get\" style=\"display:inline;\" >\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n<td height=\"28\"   class=\"tableheading\">");
/* 2470 */       out.print(FormatUtil.getString("am.webclient.common.configurationdetails.text"));
/* 2471 */       out.write("\n\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 2472 */       out.print(request.getParameter("name"));
/* 2473 */       out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 2474 */       out.print(request.getParameter("haid"));
/* 2475 */       out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 2476 */       out.print(request.getParameter("type"));
/* 2477 */       out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 2478 */       out.print(request.getParameter("type"));
/* 2479 */       out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"configureJMX\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2480 */       out.print(request.getParameter("resourceid"));
/* 2481 */       out.write("\">\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 2482 */       out.print(request.getParameter("resourcename"));
/* 2483 */       out.write("\">\n<input type=\"hidden\" name=\"moname\" value=\"");
/* 2484 */       out.print(request.getParameter("moname"));
/* 2485 */       out.write("\">\n\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\">\n<span class=\"bodytextboldwhiteun\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n");
/* 2486 */       out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 2487 */       out.write("</span>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n<tr>\n    <td width=\"20%\" height=\"32\" valign=='top' class=\"bodytext\"> ");
/* 2488 */       out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2489 */       out.write("\n</td>\n    <td width=\"80%\" class=\"bodytext\"> <textarea name=\"camname\" cols=\"38\" rows=\"1\" class=\"formtextarea\">");
/* 2490 */       out.print(camName);
/* 2491 */       out.write(" </textarea>\n</td>\n</tr>\n\n<tr>\n    <td valign='top'  class=\"bodytext\"> ");
/* 2492 */       out.print(FormatUtil.getString("Description"));
/* 2493 */       out.write("</td>\n    <td  class=\"bodytext\"> <textarea name=\"camdesc\" cols=\"38\" rows=\"3\" class=\"formtextarea\" >");
/* 2494 */       out.print(camDescription);
/* 2495 */       out.write("</textarea>\n    </td>\n  </tr>\n</table>\n");
/*      */       
/* 2497 */       String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/*      */       
/* 2499 */       out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"20%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"80%\" class=\"tablebottom\" >\n<input name=\"addcustomapp\" type=\"button\" class=\"buttons btn_highlt\" \" value=\"");
/* 2500 */       out.print(FormatUtil.getString("am.webclient.common.startmonitoring.text"));
/* 2501 */       out.write("\" onClick=\"validateAndSubmit()\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons btn_link\" value=\"");
/* 2502 */       out.print(cancel);
/* 2503 */       out.write("\" onClick=\"javascript:toggleDiv('edit')\"/>\n     </td>\n  </tr>\n</table>\n</html:form>\n</div>\n");
/*      */       
/* 2505 */       if (!attribsPresent)
/*      */       {
/*      */ 
/* 2508 */         out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n    <td colspan=3  height=\"19\" class=\"tableheadingbborder\" >");
/* 2509 */         out.print(camName);
/* 2510 */         out.write("\n      ");
/* 2511 */         if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 2512 */           out.write(": <span class=\"topdesc\">");
/* 2513 */           out.print(FormatUtil.getString(camDescription));
/* 2514 */           out.write(" </span>");
/*      */         }
/* 2516 */         out.write("</td>\n     <td  height=\"19\" width=\"20%\" class=\"tableheadingbborder\">\n     ");
/*      */         
/* 2518 */         if ((attribsPresent) && (request.getAttribute("showrefreshnowoption") != null))
/*      */         {
/*      */ 
/* 2521 */           out.write("\n       <a class=\"bodytextboldwhiteun\" href=\"/deleteMO.do?method=fetchDataNowForResource&resourceid=");
/* 2522 */           out.print(camIDI);
/* 2523 */           out.write("&redirectto=");
/* 2524 */           out.print(URLEncoder.encode("/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID" + fromConfPage1));
/* 2525 */           out.write(34);
/* 2526 */           out.write(62);
/* 2527 */           out.print(FormatUtil.getString("am.webclient.availabilityperf.fetchvalue"));
/* 2528 */           out.write("</a>\n     ");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/* 2534 */           out.write("\n     &nbsp;\n     ");
/*      */         }
/*      */         
/*      */ 
/* 2538 */         out.write("\n\n     </td>\n</tr>\n\n<tr>\n    <td colspan=4  height=\"29\" ><span class=\"bodytext\">&nbsp;");
/* 2539 */         out.print(FormatUtil.getString("am.webclient.cam.addcustomattributes.message"));
/*      */         
/* 2541 */         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2542 */         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2543 */         _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */         
/* 2545 */         _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 2546 */         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2547 */         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */           for (;;) {
/* 2549 */             out.write(" <a class='staticlinks' href=\"");
/* 2550 */             out.print(configureLink);
/* 2551 */             out.write("\">\n      ");
/* 2552 */             out.print(FormatUtil.getString("am.webclient.cam.addattributes.link"));
/* 2553 */             out.write("</a>.");
/* 2554 */             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2555 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2559 */         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2560 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */         }
/*      */         
/* 2563 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2564 */         out.write("</span></td>\n</tr>\n</table>\n");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2569 */         if (!scalarAttribsPresent) {
/* 2570 */           out.write(10);
/* 2571 */           out.write(10);
/*      */ 
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*      */ 
/* 2579 */           List row = null;
/* 2580 */           int posOfAttribName = 2;
/* 2581 */           int posOfDispType = 4;
/* 2582 */           int posOfAttribValue = 7;
/* 2583 */           int posOfResourceID = 6;
/* 2584 */           int posOfAttribID = 0;
/* 2585 */           int posOfAttribType = 3;
/* 2586 */           String className = "whitegrayborder";
/* 2587 */           String currentResourceName = null;
/* 2588 */           String currentMBeanName = null;
/* 2589 */           Map infoMap = CAMDBUtil.getMBeanBasedScreenData(Long.parseLong(screenIDI));
/* 2590 */           Map idVsMBeanAndRes = (HashMap)infoMap.get("attrIdVsMBeanName");
/* 2591 */           List ordListFromDB = (ArrayList)infoMap.get("attributeidsorderedlist");
/* 2592 */           List orderedList = new ArrayList(screenAttribInfo.size());
/*      */           
/*      */ 
/* 2595 */           out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n    <td colspan=3  height=\"19\" class=\"tableheadingbborder\" >");
/* 2596 */           out.print(camName);
/* 2597 */           out.write("\n    ");
/* 2598 */           if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 2599 */             out.write("  : <span class=\"topdesc\">");
/* 2600 */             out.print(FormatUtil.getString(camDescription));
/* 2601 */             out.write(" </span> ");
/*      */           }
/* 2603 */           out.write("</td>\n\t<td width=\"30%\" nowrap class=\"tableheadingbborder\">\n\t");
/* 2604 */           if ((attribsPresent) && (request.getAttribute("showrefreshnowoption") != null)) {
/* 2605 */             out.write("\n       <a class=\"bodytextboldwhiteun\" href=\"/deleteMO.do?method=fetchDataNowForResource&resourceid=");
/* 2606 */             out.print(camIDI);
/* 2607 */             out.write("&redirectto=");
/* 2608 */             out.print(URLEncoder.encode("/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID" + fromConfPage1));
/* 2609 */             out.write(34);
/* 2610 */             out.write(62);
/* 2611 */             out.print(FormatUtil.getString("am.webclient.availabilityperf.fetchvalue"));
/* 2612 */             out.write("</a>\n       ");
/*      */           } else {
/* 2614 */             out.write("\n       <a class=\"staticlinks\" href=\"javascript:void(0);\" onclick=\"getCustomFields('");
/* 2615 */             out.print(camIDI);
/* 2616 */             out.write("','noalarms',false,'CustomFieldValues',false);\">");
/* 2617 */             out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 2618 */             out.write("</a>&nbsp;");
/* 2619 */             out.write("\n       ");
/*      */           }
/* 2621 */           out.write("\n       </td>\n\n<tr>\n                <td width=\"36%\" class=\"columnheading\" > ");
/* 2622 */           out.print(FormatUtil.getString("am.webclient.camscreen.attributename"));
/* 2623 */           out.write("</td>\n            <td width=\"30%\" class=\"columnheading\" > ");
/* 2624 */           out.print(FormatUtil.getString("am.webclient.camscreen.value"));
/* 2625 */           out.write("</td>\n        <td width=\"20%\" class=\"columnheading\" > ");
/* 2626 */           if ((request.getParameter("type") != null) && (request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 2627 */             out.write(" &nbsp; ");
/*      */           } else {
/* 2629 */             out.write(32);
/* 2630 */             out.print(FormatUtil.getString("am.webclient.camscreen.datacollectiontime"));
/* 2631 */             out.write("</td> ");
/*      */           }
/* 2633 */           out.write("\n    <td width=\"9%\" class=\"columnheading\" >");
/* 2634 */           out.print(FormatUtil.getString("am.webclient.camscreen.actions.text"));
/* 2635 */           out.write("</td>\n</tr>\n");
/*      */           
/* 2637 */           Hashtable token = new Hashtable();
/*      */           
/* 2639 */           for (int i = 0; i < screenAttribInfo.size(); i++)
/*      */           {
/* 2641 */             row = (List)screenAttribInfo.get(i);
/* 2642 */             if (i % 2 == 0) {
/* 2643 */               className = "whitegrayborder";
/*      */             } else {
/* 2645 */               className = "yellowgrayborder";
/*      */             }
/*      */             
/* 2648 */             boolean newResource = false;
/* 2649 */             boolean newMBean = false;
/* 2650 */             boolean addMBeanRow = false;
/* 2651 */             String date = "Could not be obtained";
/* 2652 */             String shortDate = "Could not be obtained";
/* 2653 */             String longFormatDate = "Could not be obtained";
/* 2654 */             String resourceName4Attrib = "";
/*      */             try
/*      */             {
/* 2657 */               resourceName4Attrib = (String)attribResourceMap.get(row.get(posOfAttribID));
/* 2658 */               String attribID = (String)row.get(posOfAttribID);
/* 2659 */               String mBeanName = (String)idVsMBeanAndRes.get(attribID);
/* 2660 */               if (currentMBeanName == null)
/*      */               {
/* 2662 */                 currentMBeanName = mBeanName;
/* 2663 */                 newMBean = true;
/*      */               }
/* 2665 */               else if (!currentMBeanName.equals(mBeanName))
/*      */               {
/* 2667 */                 currentMBeanName = mBeanName;
/* 2668 */                 newMBean = true;
/*      */               }
/* 2670 */               if (currentResourceName == null)
/*      */               {
/* 2672 */                 currentResourceName = resourceName4Attrib;
/* 2673 */                 newResource = true;
/*      */               }
/* 2675 */               else if (!currentResourceName.equals(resourceName4Attrib))
/*      */               {
/* 2677 */                 currentResourceName = resourceName4Attrib;
/* 2678 */                 newResource = true;
/*      */               }
/* 2680 */               addMBeanRow = (newMBean) || (newResource);
/* 2681 */               date = String.valueOf(Long.parseLong((String)dcTimeMap.get(row.get(posOfAttribID))));
/* 2682 */               shortDate = formatDT(date);
/* 2683 */               longFormatDate = new Date(Long.parseLong(date)).toString();
/*      */             }
/*      */             catch (Exception e) {}
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2692 */             String value = (String)row.get(posOfAttribValue);
/* 2693 */             if (row.get(posOfAttribType).equals("0"))
/*      */             {
/* 2695 */               if (value.equals("-1"))
/*      */               {
/* 2697 */                 value = FormatUtil.getString("am.webclient.cam.nodata");
/*      */               }
/*      */             }
/*      */             
/*      */ 
/* 2702 */             out.write(10);
/*      */             
/* 2704 */             if (addMBeanRow)
/*      */             {
/* 2706 */               if (((String)attribResourceMap.get(row.get(posOfAttribID) + "_RESTYPE")).equals("SNMP"))
/*      */               {
/*      */ 
/* 2709 */                 out.write("\n<tr>\n       <td height=\"20\" class=\"secondchildnode\" colspan=\"4\"><span class=\"bodytextbold\"><span class=\"bodytext\">(");
/* 2710 */                 out.print(currentResourceName);
/* 2711 */                 out.write(")</span></span></td>\n</tr>\n\n");
/*      */ 
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/* 2718 */                 out.write("\n\n<tr>\n<td height=\"20\"   class=\"secondchildnode\"  colspan=\"4\" onmouseover=\"ddrivetip(this,event,'");
/* 2719 */                 out.print(addBreakAt((currentMBeanName + " - " + currentResourceName).replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"), 80));
/* 2720 */                 out.write("',null,true,'#000000',300)\" onmouseout=\"hideddrivetip()\" <span class=\"bodytextbold\">");
/* 2721 */                 out.print(addBreakAt(currentMBeanName, 80));
/* 2722 */                 out.write(" <span class=\"availablity-arrow\">&raquo;&nbsp;</span> ");
/* 2723 */                 out.print(getTrimmedText(currentResourceName, 25));
/* 2724 */                 out.write("</span> </td> ");
/* 2725 */                 out.write("\n</tr>\n");
/*      */               }
/*      */             }
/*      */             try
/*      */             {
/* 2730 */               StringTokenizer mbean = new StringTokenizer(currentResourceName, "_");
/* 2731 */               int j = 0;
/* 2732 */               while (mbean.hasMoreTokens()) {
/* 2733 */                 String t = mbean.nextToken();
/* 2734 */                 token.put(Integer.valueOf(j), t);
/* 2735 */                 j++;
/*      */               }
/*      */               
/*      */ 
/* 2739 */               String attrbId = (String)row.get(posOfAttribID);
/* 2740 */               String resType = (String)attribResourceMap.get(attrbId + "_RESTYPE");
/* 2741 */               if ((resType != null) && (resType.equalsIgnoreCase("snmp"))) {
/* 2742 */                 String resourceId = (String)row.get(row.size() - 2);
/* 2743 */                 if ((resourceId != null) && (resourceId.length() > 0)) {
/* 2744 */                   List l = DBUtil.getRows("SELECT RESOURCENAME FROM AM_ManagedObject where RESOURCEID=" + resourceId);
/* 2745 */                   if ((l != null) && (l.size() == 1)) {
/* 2746 */                     j = 0;
/* 2747 */                     String actualResourceName = (String)((ArrayList)l.get(0)).get(0);
/* 2748 */                     mbean = new StringTokenizer(actualResourceName, "_");
/* 2749 */                     while (mbean.hasMoreTokens()) {
/* 2750 */                       String t = mbean.nextToken();
/* 2751 */                       token.put(Integer.valueOf(j), t);
/* 2752 */                       j++;
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */             } catch (Exception e) {}
/* 2758 */             String toshow = getTrimmedText(value, 30);
/* 2759 */             request.setAttribute("toshow", toshow);
/* 2760 */             request.setAttribute("fullvalue", value);
/* 2761 */             int len = value.length();
/* 2762 */             String tooltiptype = (String)row.get(posOfDispType);
/*      */             
/* 2764 */             if (tooltiptype.equals("1")) {
/* 2765 */               tooltiptype = "Counter";
/* 2766 */               if ((toshow.equals(" ")) || (value.equals(" ")))
/*      */               {
/* 2768 */                 Map fromMap = new HashMap();
/* 2769 */                 fromMap = (HashMap)com.adventnet.appmanager.cam.CAMServerUtil.collectFirstData;
/* 2770 */                 if (fromMap != null) {
/* 2771 */                   List lst = new ArrayList();
/* 2772 */                   lst = (ArrayList)fromMap.get((String)row.get(posOfAttribID));
/* 2773 */                   if (lst != null) {
/* 2774 */                     request.setAttribute("toshow", lst.get(2));
/* 2775 */                     request.setAttribute("fullvalue", lst.get(3));
/*      */                   }
/*      */                 }
/*      */               }
/*      */             } else {
/* 2780 */               tooltiptype = "Non-Counter";
/*      */             }
/*      */             
/* 2783 */             out.write("\n\n<tr>\n\t<td class=\"");
/* 2784 */             out.print(className);
/* 2785 */             out.write("\" onmouseover=\"ddrivetip(this,event,'");
/* 2786 */             out.print(FormatUtil.getString("am.webclient.snmp.tootipMsg", new String[] { (String)row.get(posOfAttribName), resourceName4Attrib, tooltiptype }));
/* 2787 */             out.write("',null,true,'#000000','len')\" onmouseout=\"hideddrivetip()\"> <span class=\"bodytext\">");
/* 2788 */             out.print(getTrimmedText((String)row.get(posOfAttribName), 25));
/* 2789 */             out.write(" </span></td>\n\n");
/*      */             
/* 2791 */             if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 2792 */               if (len >= 30)
/*      */               {
/* 2794 */                 out.write(10);
/* 2795 */                 out.write(10);
/* 2796 */                 String breaktext = addBreakAt(value, 50);
/* 2797 */                 out.write("\n     <td class=\"");
/* 2798 */                 out.print(className);
/* 2799 */                 out.write("\" onmouseover=\"ddrivetip(this,event,'");
/* 2800 */                 out.print(value.replaceAll("\\n", " "));
/* 2801 */                 out.write("',null,true,'#000000','len')\" onmouseout=\"hideddrivetip()\" ><span class=\"bodytext\"> ");
/* 2802 */                 if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                   return;
/* 2804 */                 out.write(" </span></td>\n\n");
/*      */               }
/*      */               else {
/* 2807 */                 out.write("\n\n<td class=\"");
/* 2808 */                 out.print(className);
/* 2809 */                 out.write("\" ><span class=\"bodytext\"> ");
/* 2810 */                 if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */                   return;
/* 2812 */                 out.write(" </span></td>\n\n");
/*      */               }
/*      */               
/* 2815 */               out.write("\n\n        <td class=\"");
/* 2816 */               out.print(className);
/* 2817 */               out.write("\" title=\"Time : ");
/* 2818 */               out.print(longFormatDate);
/* 2819 */               out.write(" Resource : ");
/* 2820 */               out.print(resourceName4Attrib);
/* 2821 */               out.write("\"> <span class=\"bodytext\">");
/* 2822 */               out.print(shortDate);
/* 2823 */               out.write("</span></td>\n");
/*      */             } else {
/* 2825 */               out.write("\n<td colspan=2 class=\"");
/* 2826 */               out.print(className);
/* 2827 */               out.write("\"><span class=\"bodytext\">");
/* 2828 */               out.print(addBreakAt(value, 55));
/* 2829 */               out.write("</span></td>\n");
/*      */             }
/* 2831 */             out.write("\n        <td class=\"");
/* 2832 */             out.print(className);
/* 2833 */             out.write("\" >\n        ");
/* 2834 */             if ((row.get(posOfAttribType).equals("0")) || (row.get(posOfAttribType).equals("1"))) {
/* 2835 */               if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS")))
/*      */               {
/* 2837 */                 out.write("\n\n<a  style=\"cursor: pointer;\" onClick=\"javascript:MM_openBrWindow('/jsp/attribute_edit.jsp?resourceid=");
/* 2838 */                 out.print(row.get(posOfResourceID));
/* 2839 */                 out.write("&attributeid=");
/* 2840 */                 out.print(row.get(posOfAttribID));
/* 2841 */                 out.write("&camid=");
/* 2842 */                 out.print(camIDI);
/* 2843 */                 out.write("&haid=");
/* 2844 */                 out.print(haidI);
/* 2845 */                 out.write("&screenid=");
/* 2846 */                 out.print(screenIDI);
/* 2847 */                 out.write("&resourcename=");
/* 2848 */                 out.print(currentResourceName);
/* 2849 */                 out.write("&hostname=");
/* 2850 */                 out.print(token.get(Integer.valueOf(0)));
/* 2851 */                 out.write("&resourcetype=");
/* 2852 */                 out.print(token.get(Integer.valueOf(1)));
/* 2853 */                 out.write("&portno=");
/* 2854 */                 out.print(token.get(Integer.valueOf(2)));
/* 2855 */                 out.write("&attributes=");
/* 2856 */                 out.print(URLEncoder.encode(currentMBeanName + "|" + (String)row.get(1) + "|" + row.get(posOfAttribType)));
/* 2857 */                 out.write("&displayname=");
/* 2858 */                 out.print((String)row.get(posOfAttribName));
/* 2859 */                 out.write("&isfromeditpage=");
/* 2860 */                 out.print("true");
/* 2861 */                 out.write("&resourceid=");
/* 2862 */                 out.print(row.get(posOfResourceID));
/* 2863 */                 out.write("&dispType=");
/* 2864 */                 out.print(row.get(posOfDispType));
/* 2865 */                 out.write("','Personalize','width=390,height=200,screenX=100,screenY=300,scrollbars=yes')\"><img src=\"/images/icon_edit.gif\"  border=\"0\" title='");
/* 2866 */                 out.print(FormatUtil.getString("jmxnotification.edit"));
/* 2867 */                 out.write("'></a>\n");
/*      */               }
/* 2869 */               out.write("\n\n    <A class='staticlinks' href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2870 */               out.print(row.get(posOfResourceID));
/* 2871 */               out.write("&attributeIDs=");
/* 2872 */               out.print(row.get(posOfAttribID));
/* 2873 */               out.write("&attributeToSelect=");
/* 2874 */               out.print(row.get(posOfAttribID));
/* 2875 */               out.write("&redirectto=");
/* 2876 */               out.print(configureThresholdRedirectLink);
/* 2877 */               out.write("'>\n    <img src=\"/images/icon_associateaction.gif\" title='");
/* 2878 */               out.print(ALERTCONFIG_TEXT);
/* 2879 */               out.write("' border=\"0\" ></A>\n\n    ");
/*      */               
/* 2881 */               if (row.get(posOfAttribType).equals("0"))
/*      */               {
/*      */ 
/* 2884 */                 out.write("\n    <a style=\"cursor: pointer;\" onclick=\"showAttribGraph(");
/* 2885 */                 out.print(row.get(posOfAttribID));
/* 2886 */                 out.write(44);
/* 2887 */                 out.write(39);
/* 2888 */                 out.print(getTrimmedText(currentMBeanName, 50).replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 2889 */                 out.write("')\" ><img src='/images/icon_linegraph.gif' title='");
/* 2890 */                 out.print(FormatUtil.getString("jmxnotification.showgraph"));
/* 2891 */                 out.write("' border='0' ></a>\n\n\n        ");
/*      */               }
/*      */               
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/* 2898 */               out.write("\n\t\t&nbsp;\n\t");
/*      */             }
/*      */             
/*      */ 
/* 2902 */             out.write("\n</td>\n\n</tr>\n");
/*      */           }
/*      */           
/*      */ 
/* 2906 */           out.write("\n</tr>\n\n<tr>\n\t<td colspan=4  height='25' class=\"tablebottom\"><span class=\"bodytext\">");
/*      */           
/* 2908 */           PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2909 */           _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2910 */           _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */           
/* 2912 */           _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 2913 */           int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2914 */           if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */             for (;;) {
/* 2916 */               out.write("\n       <a href=\"");
/* 2917 */               out.print(configureLink);
/* 2918 */               out.write("\" class='staticlinks'>");
/* 2919 */               out.print(FormatUtil.getString("am.webclient.cam.adddeleteattributes.text"));
/* 2920 */               out.write("</a> ");
/* 2921 */               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2922 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2926 */           if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2927 */             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */           }
/*      */           
/* 2930 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2931 */           out.write("</span></td>\n</tr>\n</table>\n");
/*      */         }
/*      */         
/*      */ 
/* 2935 */         out.write("\n<br>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 2936 */         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/MyField_div.jsp", out, false);
/* 2937 */         out.write("</td></tr></table>\n");
/*      */         
/* 2939 */         if (columnarAttribsPresent)
/*      */         {
/* 2941 */           int k = 0;
/* 2942 */           String titlePrefix = FormatUtil.getString("am.webclient.common.name.text");
/* 2943 */           for (int i = 0; i < colScreenAttribInfo.size(); i++)
/*      */           {
/* 2945 */             out.write("\n\t<div class=\"horTableScroll\">\n\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"99%\" class=\"lrtbdarkborder\">\n\t");
/*      */             
/* 2947 */             List temp1 = (List)colScreenAttribInfo.get(i);
/* 2948 */             if (temp1.size() > 0)
/*      */             {
/* 2950 */               Properties tmpProp = (Properties)((List)temp1.get(0)).get(0);
/* 2951 */               String mbeanName = tmpProp.getProperty("mbeanname");
/* 2952 */               List h = (List)tmpProp.get("columnnames");
/*      */               
/* 2954 */               out.write("\n\t\t<tr>\n\t\t<td height=\"24\" width=\"75%\" class=\"tableheadingbborder\" colspan=\"");
/* 2955 */               out.print(h.size() + 1);
/* 2956 */               out.write("\">\n\t\t");
/* 2957 */               out.print(titlePrefix);
/* 2958 */               out.write(32);
/* 2959 */               out.write(58);
/* 2960 */               out.write(32);
/* 2961 */               out.print(getTrimmedText(mbeanName, 50));
/* 2962 */               out.write("</td>\n\t\t</tr>\n\t\t");
/*      */             }
/*      */             
/* 2965 */             int cnt = 0; for (int size = temp1.size(); cnt < size; cnt++)
/*      */             {
/*      */ 
/* 2968 */               out.write("\n\t\t<tr><td width=\"100%\" style=\"vertical-align: top;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t");
/*      */               
/*      */ 
/* 2971 */               List oneTableList = (List)temp1.get(cnt);
/* 2972 */               Properties camprops = (Properties)oneTableList.get(0);
/* 2973 */               List headers = (List)camprops.get("columnnames");
/* 2974 */               List thresholdPossibleIDs = (List)camprops.get("thresholdpossibleattrids");
/* 2975 */               if ("snmp table".equals(camprops.get("TableType"))) {
/* 2976 */                 titlePrefix = "SNMP Table Name";
/*      */               } else {
/* 2978 */                 titlePrefix = FormatUtil.getString("am.webclient.camscreen.titleprefix");
/*      */               }
/*      */               
/*      */ 
/* 2982 */               out.write("\n\t\t\t<tr >\n\t\t     ");
/*      */               
/* 2984 */               String attrs = "";
/* 2985 */               for (int a = 0; a < thresholdPossibleIDs.size(); a++)
/*      */               {
/* 2987 */                 attrs = attrs + (String)thresholdPossibleIDs.get(a) + ",";
/*      */               }
/*      */               
/*      */ 
/* 2991 */               out.write("\n\t\t<td height=\"24\" width=\"75%\" class=\"secondchildnode\" colspan=\"");
/* 2992 */               out.print(headers.size());
/* 2993 */               out.write("\">\n\t\t");
/* 2994 */               String temp = (String)camprops.get("attrName");
/* 2995 */               out.write("\n\t\t<span class=\"bodytextbold\">");
/* 2996 */               out.print(FormatUtil.getString("am.webclient.camscreen.attributegraphs.attribute.text"));
/* 2997 */               out.write(32);
/* 2998 */               out.write(58);
/* 2999 */               out.write(32);
/* 3000 */               out.print(getTrimmedText(temp, 50));
/* 3001 */               out.write("</span></td>\n\n\t<td class=\"secondchildnode\" width=\"25%\">\n\n\t");
/*      */               
/* 3003 */               if (thresholdPossibleIDs.size() > 0)
/*      */               {
/*      */ 
/*      */ 
/* 3007 */                 out.write("\n\n\t\t<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3008 */                 out.print(camprops.get("resourceid"));
/* 3009 */                 out.write("&attributeIDs=");
/* 3010 */                 out.print(attrs.substring(0, attrs.length() - 1));
/* 3011 */                 out.write("&attributeToSelect=");
/* 3012 */                 out.print(thresholdPossibleIDs.get(0));
/* 3013 */                 out.write("&redirectto=");
/* 3014 */                 out.print(configureThresholdRedirectLink);
/* 3015 */                 out.write("' class=\"staticlinks\">\n        <img src=\"/images/icon_associateaction.gif\" alt=\"Associate Action\" border=\"0\" align=\"absmiddle\" hspace=\"5\" >");
/* 3016 */                 out.print(ALERTCONFIG_TEXT);
/* 3017 */                 out.write("</a>\n        ");
/*      */               }
/*      */               
/*      */ 
/* 3021 */               out.write("\n\t\t</td>\n\t\t</tr>\n\t        <tr>\n\t\t");
/*      */               
/* 3023 */               for (k = 0; k < headers.size(); k++)
/*      */               {
/*      */ 
/* 3026 */                 out.write("\n\t\t\t\t<td class=\"columnheading\" align=left>\n\t\t\t\t");
/* 3027 */                 out.print(headers.get(k));
/* 3028 */                 out.write("\n\t\t\t\t</td>\n\t\t\t");
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 3033 */               out.write("\n\t\t<td class=\"columnheading\" width=\"19%\">&nbsp;</td>\n\t        </tr>\n\t        ");
/*      */               
/* 3035 */               for (int j = 1; j < oneTableList.size(); j++)
/*      */               {
/* 3037 */                 String bgclass = "class=\"whitegrayrightalign-reports\"";
/* 3038 */                 if (j % 2 != 0)
/*      */                 {
/* 3040 */                   bgclass = "class=\"whitegrayrightalign-reports\"";
/*      */                 }
/*      */                 
/* 3043 */                 out.write("\n\t        \t\t<tr>\n\t        \t\t");
/*      */                 
/* 3045 */                 for (int l = 0; l < headers.size(); l++)
/*      */                 {
/*      */ 
/* 3048 */                   out.write("\n\t\t\t\t\t<td height=\"28\"  ");
/* 3049 */                   out.print(bgclass);
/* 3050 */                   out.write(" align=\"left\" title=\"");
/* 3051 */                   out.print(formatDT((String)camprops.get("dctime")));
/* 3052 */                   out.write("\">\n\t\t\t\t\t\t<div  style=\"WORD-BREAK:BREAK-ALL; word-wrap: break-word; width:100px; white-space:-moz-pre-wrap; white-space: normal;\">");
/* 3053 */                   out.print(((List)oneTableList.get(j)).get(l));
/* 3054 */                   out.write("</div>\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/*      */                 }
/*      */                 
/*      */ 
/* 3058 */                 out.write("\n\n\t\t\t<td ");
/* 3059 */                 out.print(bgclass);
/* 3060 */                 out.write(" width=\"19%\">&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t");
/*      */               }
/*      */               
/*      */ 
/* 3064 */               out.write("\n\t</table></td></tr>\n\t");
/*      */             }
/*      */             
/*      */ 
/* 3068 */             out.write("\n<tr>\n        <td colspan=");
/* 3069 */             out.print(k + 1);
/* 3070 */             out.write("  height='25' class=\"tablebottom\"><span class=\"bodytext\">");
/*      */             
/* 3072 */             PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3073 */             _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3074 */             _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */             
/* 3076 */             _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 3077 */             int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3078 */             if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */               for (;;) {
/* 3080 */                 out.write("\n       <a href=\"");
/* 3081 */                 out.print(configureLink);
/* 3082 */                 out.write("\" class='staticlinks'>");
/* 3083 */                 out.print(FormatUtil.getString("am.webclient.cam.adddeleteattributes.text"));
/* 3084 */                 out.write("</a> ");
/* 3085 */                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3086 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 3090 */             if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3091 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */             }
/*      */             
/* 3094 */             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3095 */             out.write("</span></td>\n</tr>\n\n\n</table><br></div>\n");
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 3101 */       out.write("\n<br><br>\n\n<!-- Added graphs to the Normal Screen -->\n<div id=\"status\" style='Display:none'>");
/* 3102 */       out.print(FormatUtil.getString("am.webclient.gengraph.text"));
/* 3103 */       out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n<div id=\"attributegraphs\"></div>\n\n\n\n<script>\nmyOnLoad();\nfunction myOnLoad()\n{\n/**\t");
/* 3104 */       if ((request.getParameter("type") != null) && (request.getParameter("type").equals("JBOSS-server"))) {
/* 3105 */         out.write("\n\tmyOnJbossLoad();\n\t");
/*      */       }
/* 3107 */       out.write("\n\t**/\n\tattributegraphs();\n}\nvar http1;\nfunction attributegraphs()\n{\n        document.getElementById(\"status\").style.display='block';\n        URL='/jsp/cam_graphs.jsp?camIDI=");
/* 3108 */       out.print(camIDI);
/* 3109 */       out.write("&haidI=");
/* 3110 */       out.print(haidI);
/* 3111 */       out.write("&screenIDI=");
/* 3112 */       out.print(screenIDI);
/* 3113 */       out.write("&isfromresourcepage=");
/* 3114 */       out.print(isFromResourcePage);
/* 3115 */       out.write("';\n        http1=getHTTPObject();\n        http1.open(\"GET\", URL, true);\n        //http1.onreadystatechange = new Function('if(http1.readyState == 4 && http1.status == 200){document.getElementById(\"status\").innerHTML =\"&nbsp;\",document.getElementById(\"attributegraphs\").innerHTML = http1.responseText;}' );\n\thttp1.onreadystatechange =handleResponse1;\n        http1.send(null);\n}\n\nfunction handleResponse1()\n{\n        if(http1.readyState == 4)\n        {\n                var result = http1.responseText;\n\t\tdocument.getElementById(\"status\").innerHTML =\"&nbsp;\"\n                document.getElementById(\"attributegraphs\").innerHTML = result;\n                document.getElementById(\"attributegraphs\").style.display='block';\n        //      alert('Div similarmonitor display' + document.getElementById(\"multimonitors\").checked);\n        }\n}\n\n\nfunction subAddCustom(linkS) {\n\tlinkS.href = \"");
/* 3116 */       out.print(configureLink);
/* 3117 */       out.write("\";\n\treturn true;\n}\n\n$(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 3118 */       out.write("\n\t{\n\t\t");
/* 3119 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */         return;
/* 3121 */       out.write(10);
/* 3122 */       out.write(9);
/* 3123 */       out.write(9);
/* 3124 */       if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*      */         return;
/* 3126 */       out.write("\n\t\tgetCustomFields('");
/* 3127 */       if (_jspx_meth_c_005fout_005f4(_jspx_page_context))
/*      */         return;
/* 3129 */       out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 3130 */       out.write("\n\t}\n\n});\n</script>\n\n\n");
/* 3131 */       out.write(10);
/* 3132 */       out.write(10);
/* 3133 */       out.write("\n</td>\n<td>&nbsp;</td>\n<td width=\"50%\" valign=\"top\">\n");
/* 3134 */       out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n\n\n\n\n<script>\nfunction deleteMopSelections()\n{\n\tvar sel = false;\n \tfor(i=0;i<document.formab.elements.length;i++)\n \t{\n \t\tif(document.formab.elements[i].type==\"checkbox\")\n \t               {\n \t                        var name = document.formab.elements[i].name;\n \t                        if(name==\"execmopcheckbox\")\n \t                        {\n \t                        \tvar value = document.formab.elements[i].value;\n \t                        \tsel=document.formab.elements[i].checked;\n \t                        \tif(sel)\n \t                        \t{\n \t                        \t\tbreak;\n \t                        \t}\n \t                        }\n \t                 }\n         }\n \tif(!sel)\n \t{\n \t     alert('");
/* 3135 */       out.print(FormatUtil.getString("am.webclient.viewaction.alertmbeandelete"));
/* 3136 */       out.write("');\n \t}\n \telse if(confirm('");
/* 3137 */       out.print(FormatUtil.getString("am.webclient.viewaction.alertdeleteconfirm"));
/* 3138 */       out.write("'))\n\t{\n\t    document.formab.action=\"/adminAction.do?method=deleteMBeanOperationAction\";\n\t    document.formab.method=\"Post\"\n\t    document.formab.submit();\n\t}\n}\nfunction deleteThreadDump(url,id)\n{\n      \tif(!confirm(\"");
/* 3139 */       out.print(FormatUtil.getString("am.javaruntime.confirm.delete.text"));
/* 3140 */       out.write("\"))\n      \t{\n       \t\treturn;//No I18N\n      \t}\n\tvar url =\"/CAMUpdateScreenAttributes.do?method=deletethreadURL&url=\"+url; //NO I18N\n\thttp=getHTTPObject(); //NO I18N\n\thttp.onreadystatechange = handleResponse3;//NO I18N\n\thttp.open(\"GET\",url,true);\n\thttp.send(null); //NO I18N\n}\nfunction getThreadDumpData(rid,tabval)\n{\n\tif(document.getElementById('exturl').style.display=='block')\n\t{\n\t    var showall = document.getElementById('more'); //NO I18N\n\t    showall.innerHTML=\"More...\"; //NO I18N\n\t    toggleDiv('exturl'); //NO I18N\n\t    return;\n\t}\n\tvar date = new Date(); //NO I18N\n\tvar url ='/CAMUpdateScreenAttributes.do?resourceid='+rid+'&method=getThreadDump'; //NO I18N\n\thttp=getHTTPObject(); //NO I18N\n\thttp.onreadystatechange = handleResponse2 //NO I18N\n\thttp.open(\"POST\", url, true); //NO I18N\n\thttp.send(null); //NO I18N\n\n}\nfunction handleResponse2() \n{\n\t if(http.readyState == 4 && http.status == 200)\n\t {\n\t\tvar result = http.responseText;\n\t\tdocument.getElementById('exturl').innerHTML = result; //NO I18N\n\t\tvar showall = document.getElementById('more'); //NO I18N\n");
/* 3141 */       out.write("\t\tshowall.innerHTML=\"Hide...\"; //NO I18N\n\t\ttoggleDiv('exturl'); //NO I18N\n\t }\n}\nfunction handleResponse3() {\n\tif (http.readyState == 4) {\n\t\tvar result = http.responseText;\n\t\tvar ele = document.getElementById('groupContent');\n\t\tif(ele)\n\t\t{\n\t\t\tele.innerHTML = result;\n\t\t\tconfBodyLoad();\n\t\t}\n\t}\n\t\n}\n</script>\n<form name=\"formab\">\n<input type=\"hidden\" name=\"redirectto\" value=\"");
/* 3142 */       out.print(java.net.URLDecoder.decode(mopRedirectString));
/* 3143 */       out.write("\">\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t\t    <tr>\n\t\t\t\t          <td width=\"100%\" height=\"31\" class=\"tableheading\" >");
/* 3144 */       out.print(FormatUtil.getString("am.webclient.common.mbeanoperations.text"));
/* 3145 */       out.write(" :</td>\n\t\t\t\t        </tr>\n\t\t\t\t      </table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\n\n            ");
/*      */       
/* 3147 */       PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.get(PresentTag.class);
/* 3148 */       _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3149 */       _jspx_th_logic_005fpresent_005f3.setParent(null);
/*      */       
/* 3151 */       _jspx_th_logic_005fpresent_005f3.setName("executeMopActions");
/* 3152 */       int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3153 */       if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */         for (;;) {
/* 3155 */           out.write("\n            <tr>\n              <td> <SCRIPT LANGUAGE=\"javascript\" SRC=\"../webclient/common/js/windowFunctions.js\"></SCRIPT>\n              </td>\n            </tr>\n            <tr>\n              <td>\n\n\n \t\t<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">\n\t\t<tr>\n\t\t<td width=\"2%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">\n\t\t<input type=\"checkbox\" name=\"headercheckbox\"  onClick=\"javascript:ToggleAll(this,document.formab,'execmopcheckbox')\">\n\t\t</td>\n\n\t\t<td width=\"12%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 3156 */           out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 3157 */           out.write("</td>\n\t\t<td width=\"21%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 3158 */           out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3159 */           out.write("</td>\n\t\t<td width=\"27%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 3160 */           out.print(FormatUtil.getString("am.reporttab.mbeanname.text"));
/* 3161 */           out.write("</td>\n\t\t<td width=\"23%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 3162 */           out.print(FormatUtil.getString("am.webclient.viewaction.operation"));
/* 3163 */           out.write(" </td>\n\t\t<!--td width=\"23%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 3164 */           out.print(FormatUtil.getString("am.webclient.viewaction.argscount"));
/* 3165 */           out.write(" </td-->\n\t\t<td width=\"23%\" height=\"28\" valign=\"center\" class=\"columnheadingnotop\">");
/* 3166 */           out.print(FormatUtil.getString("am.webclient.viewaction.targetemail.text"));
/* 3167 */           out.write("</td>\n\t\t<td width=\"11%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 3168 */           out.print(FormatUtil.getString("am.webclient.threshold.editview"));
/* 3169 */           out.write("</td>\n\t\t<td width=\"6%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\"> ");
/* 3170 */           out.print(FormatUtil.getString("am.webclient.viewaction.execute"));
/* 3171 */           out.write("</td>\n\t\t<td width=\"6%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\"> ");
/* 3172 */           out.print(FormatUtil.getString("am.webclient.viewaction.manualexecution"));
/* 3173 */           out.write("</td>\n\t\t</tr>\n\t\t");
/*      */           
/* 3175 */           IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.get(IterateTag.class);
/* 3176 */           _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 3177 */           _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */           
/* 3179 */           _jspx_th_logic_005fiterate_005f0.setName("executeMopActions");
/*      */           
/* 3181 */           _jspx_th_logic_005fiterate_005f0.setScope("request");
/*      */           
/* 3183 */           _jspx_th_logic_005fiterate_005f0.setId("moprow");
/*      */           
/* 3185 */           _jspx_th_logic_005fiterate_005f0.setIndexId("mopm");
/* 3186 */           int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 3187 */           if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 3188 */             Object moprow = null;
/* 3189 */             Integer mopm = null;
/* 3190 */             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3191 */               out = _jspx_page_context.pushBody();
/* 3192 */               _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 3193 */               _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */             }
/* 3195 */             moprow = _jspx_page_context.findAttribute("moprow");
/* 3196 */             mopm = (Integer)_jspx_page_context.findAttribute("mopm");
/*      */             for (;;) {
/* 3198 */               out.write(10);
/* 3199 */               out.write(9);
/* 3200 */               out.write(9);
/*      */               
/* 3202 */               String bgclass = "whitegrayborder";
/* 3203 */               if (mopm.intValue() % 2 != 0)
/*      */               {
/* 3205 */                 bgclass = "yellowgrayborder";
/*      */               }
/*      */               
/* 3208 */               out.write("\n\t\t<tr>\n\n\t\t<td class=\"");
/* 3209 */               out.print(bgclass);
/* 3210 */               out.write("\">\n\t\t<input type=\"checkbox\" name=\"execmopcheckbox\" value=\"");
/* 3211 */               out.print(((ArrayList)moprow).get(0));
/* 3212 */               out.write("\">\n\t\t</td>\n\t\t<td height=\"22\" class=\"");
/* 3213 */               out.print(bgclass);
/* 3214 */               out.write("\"><!--a href=\"#\" class=\"resourcename\" onClick=\"MM_openBrWindow('/showActionProfiles.do?method=getActionDetails&actionid=");
/* 3215 */               out.print(((ArrayList)moprow).get(0));
/* 3216 */               out.write("','','resizable=yes,width=450,height=185')\"-->\n\t\t");
/* 3217 */               out.print(getTrimmedText((String)((ArrayList)moprow).get(1), 25));
/* 3218 */               out.write("</a>\n\t\t</td>\n\t\t");
/*      */               
/* 3220 */               boolean hasArgs = false;
/*      */               
/* 3222 */               out.write("\n\t\t\t");
/*      */               
/* 3224 */               IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.get(IterateTag.class);
/* 3225 */               _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 3226 */               _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */               
/* 3228 */               _jspx_th_logic_005fiterate_005f1.setName("moprow");
/*      */               
/* 3230 */               _jspx_th_logic_005fiterate_005f1.setId("acolumn");
/*      */               
/* 3232 */               _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/*      */               
/* 3234 */               _jspx_th_logic_005fiterate_005f1.setOffset("2");
/* 3235 */               int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 3236 */               if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 3237 */                 Object acolumn = null;
/* 3238 */                 Integer i = null;
/* 3239 */                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3240 */                   out = _jspx_page_context.pushBody();
/* 3241 */                   _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 3242 */                   _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                 }
/* 3244 */                 acolumn = _jspx_page_context.findAttribute("acolumn");
/* 3245 */                 i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                 for (;;) {
/* 3247 */                   out.write("\n\t\t\t");
/*      */                   
/* 3249 */                   if (i.intValue() == 6)
/*      */                   {
/* 3251 */                     if (Integer.parseInt((String)acolumn) > 0)
/*      */                     {
/* 3253 */                       hasArgs = true;
/*      */                     }
/*      */                     
/*      */                   }
/*      */                   else
/*      */                   {
/* 3259 */                     out.write("\n\t\t\t<td height='22' class='");
/* 3260 */                     out.print(bgclass);
/* 3261 */                     out.write("' title='");
/* 3262 */                     out.print((String)acolumn);
/* 3263 */                     out.write("'>\n\n\t\t\t");
/* 3264 */                     out.print(getTrimmedText((String)acolumn, 25));
/* 3265 */                     out.write("\n\t\t\t</td>\n\t\t\t");
/*      */                   }
/*      */                   
/*      */ 
/* 3269 */                   out.write("\n\t\t\t");
/* 3270 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 3271 */                   acolumn = _jspx_page_context.findAttribute("acolumn");
/* 3272 */                   i = (Integer)_jspx_page_context.findAttribute("i");
/* 3273 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3276 */                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3277 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3280 */               if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 3281 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */               }
/*      */               
/* 3284 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 3285 */               out.write("\n\t\t<td class=\"");
/* 3286 */               out.print(bgclass);
/* 3287 */               out.write("\"><a href=\"/adminAction.do?method=showMBeanOperationAction&actionID=");
/* 3288 */               out.print(((ArrayList)moprow).get(0));
/* 3289 */               out.write("&haid=");
/* 3290 */               out.print(request.getParameter("haid"));
/* 3291 */               out.write("&redirectto=");
/* 3292 */               out.print(mopRedirectString);
/* 3293 */               out.write("\"><img src=\"/images/icon_edit.gif\"  border=\"0\"></a></td>\n\t\t<td width=\"4%\"height=\"28\" align=\"center\"  class=\"");
/* 3294 */               out.print(bgclass);
/* 3295 */               out.write("\">\n\t\t");
/* 3296 */               if (_jspx_meth_logic_005fpresent_005f4(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                 return;
/* 3298 */               out.write(10);
/* 3299 */               out.write(9);
/* 3300 */               out.write(9);
/*      */               
/* 3302 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3303 */               _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3304 */               _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */               
/* 3306 */               _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO,ENTERPRISEADMIN");
/* 3307 */               int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3308 */               if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                 for (;;) {
/* 3310 */                   out.write("\n \t\t\t<a href=\"/GlobalActions.do?method=testAction&actionID=");
/* 3311 */                   out.print(((ArrayList)moprow).get(0));
/* 3312 */                   out.write("&haid=");
/* 3313 */                   out.print(request.getParameter("haid") + "&redirectto=" + mopRedirectString);
/* 3314 */                   out.write("\"><img src=\"/images/icon_executeaction.gif\"  border=\"0\"></a></td>\n \t\t\t<td class=\"");
/* 3315 */                   out.print(bgclass);
/* 3316 */                   out.write("\">\n \t\t\t");
/*      */                   
/* 3318 */                   if (hasArgs)
/*      */                   {
/*      */ 
/* 3321 */                     out.write("\n \t\t\t<a href=\"javascript:void(0)\" onclick=\"javascript:MM_openBrWindow('/MBeanOperationAction.do?method=executeMBeanOperationActionWithUserIntervention&actionID=");
/* 3322 */                     out.print(((ArrayList)moprow).get(0));
/* 3323 */                     out.write("&haid=");
/* 3324 */                     out.print(request.getParameter("haid"));
/* 3325 */                     out.write("','','width=580,height=385')\"><img src=\"/images/icon_execute_user.gif\"  border=\"0\" title='");
/* 3326 */                     out.print(FormatUtil.getString("am.webclient.viewaction.mbeantitle"));
/* 3327 */                     out.write("'></a>\n \t\t\t");
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/*      */ 
/* 3333 */                     out.write("\n \t\t\t");
/* 3334 */                     out.print(FormatUtil.getString("am.webclient.viewaction.na"));
/* 3335 */                     out.write("\n \t\t\t");
/*      */                   }
/*      */                   
/*      */ 
/* 3339 */                   out.write("\n \t\t\t</td>\n\t\t");
/* 3340 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3341 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3345 */               if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3346 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */               }
/*      */               
/* 3349 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3350 */               out.write("\n\t\t</tr>\n\t\t");
/* 3351 */               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 3352 */               moprow = _jspx_page_context.findAttribute("moprow");
/* 3353 */               mopm = (Integer)_jspx_page_context.findAttribute("mopm");
/* 3354 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3357 */             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3358 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3361 */           if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 3362 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */           }
/*      */           
/* 3365 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 3366 */           out.write("\n\t\t</table>\n\t  \t\t\t\t<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"Tablebottom\">\n\t  \t\t\t\t<tr>\n\t  \t\t\t\t<td width=\"72%\" height=\"26\" class=\"bodytext\">\n\t  \t\t\t\t");
/*      */           
/* 3368 */           AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3369 */           _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 3370 */           _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */           
/* 3372 */           _jspx_th_am_005fadminlink_005f0.setHref("javascript:deleteMopSelections(this.form);");
/*      */           
/* 3374 */           _jspx_th_am_005fadminlink_005f0.setEnableClass("staticlinks");
/* 3375 */           int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 3376 */           if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 3377 */             if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3378 */               out = _jspx_page_context.pushBody();
/* 3379 */               _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 3380 */               _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 3383 */               out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3384 */               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 3385 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3388 */             if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3389 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3392 */           if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 3393 */             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */           }
/*      */           
/* 3396 */           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 3397 */           out.write("\n\t  \t\t\t\t|\n\t  \t\t\t\t");
/*      */           
/* 3399 */           AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3400 */           _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 3401 */           _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */           
/* 3403 */           _jspx_th_am_005fadminlink_005f1.setHref(linkForMopAction);
/*      */           
/* 3405 */           _jspx_th_am_005fadminlink_005f1.setEnableClass("staticlinks");
/* 3406 */           int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 3407 */           if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 3408 */             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 3409 */               out = _jspx_page_context.pushBody();
/* 3410 */               _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 3411 */               _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 3414 */               out.write("\n\t  \t\t\t\t");
/* 3415 */               out.print(FormatUtil.getString("am.webclient.threshold.addnew"));
/* 3416 */               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 3417 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3420 */             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 3421 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3424 */           if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 3425 */             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */           }
/*      */           
/* 3428 */           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 3429 */           out.write("</td>\n\t  \t\t\t\t</tr>\n\t  \t\t\t\t</table>\n\n              </td>\n            </tr>\n            ");
/* 3430 */           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3431 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3435 */       if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3436 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f3);
/*      */       }
/*      */       else {
/* 3439 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3440 */         out.write("\n            ");
/*      */         
/* 3442 */         NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.get(NotPresentTag.class);
/* 3443 */         _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3444 */         _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */         
/* 3446 */         _jspx_th_logic_005fnotPresent_005f1.setName("executeMopActions");
/* 3447 */         int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3448 */         if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */           for (;;) {
/* 3450 */             out.write("\n            <tr>\n            <td>\n           \t\t<table>\n           \t\t<tr>\n\n<td class=\"bodytext\" height=\"29\" valign=\"center\">&nbsp;");
/* 3451 */             out.print(FormatUtil.getString("am.webclient.viewaction.noactionscreated"));
/* 3452 */             out.write(32);
/*      */             
/* 3454 */             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3455 */             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3456 */             _jspx_th_c_005fif_005f2.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */             
/* 3458 */             _jspx_th_c_005fif_005f2.setTest("${!empty ADMIN}");
/* 3459 */             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3460 */             if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */               for (;;) {
/* 3462 */                 out.write(10);
/* 3463 */                 out.write(32);
/* 3464 */                 out.write(32);
/* 3465 */                 out.print(FormatUtil.getString("am.webclient.viewaction.clickto"));
/* 3466 */                 out.write(" <a href=\"");
/* 3467 */                 out.print(linkForMopAction);
/* 3468 */                 out.write("\" class=\"resourcename\">\n  ");
/* 3469 */                 out.print(FormatUtil.getString("am.webclient.threshold.creatembean"));
/* 3470 */                 out.write("</a>");
/* 3471 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3472 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 3476 */             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3477 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */             }
/*      */             
/* 3480 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3481 */             out.write("</td>\n           \t\t</tr>\n               \t</table>\n           </td>\n           </tr>\n           ");
/* 3482 */             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3483 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 3487 */         if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3488 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*      */         }
/*      */         else {
/* 3491 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3492 */           out.write("\n          \t</table>\n\n");
/*      */           
/* 3494 */           HashMap threadProps11 = (HashMap)request.getAttribute("threadProps");
/*      */           try {
/* 3496 */             if (threadProps11 != null) {
/* 3497 */               ArrayList threaddumphistory = (ArrayList)threadProps11.get("threadurls");
/* 3498 */               int rowCount = ((Integer)threadProps11.get("ROW_COUNT")).intValue();
/* 3499 */               String resourceid11 = "" + request.getParameter("resourceid");
/*      */               
/* 3501 */               out.write(10);
/*      */               
/* 3503 */               PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3504 */               _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3505 */               _jspx_th_logic_005fpresent_005f5.setParent(null);
/*      */               
/* 3507 */               _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 3508 */               int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3509 */               if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                 for (;;) {
/* 3511 */                   out.write("\n <br>\n     <input class=\"buttons\" value=\"");
/* 3512 */                   out.print(FormatUtil.getString("am.webclient.jdk15.threadinfo.text"));
/* 3513 */                   out.write("\" type=\"button\" onClick=\"javascript:MM_openBrWindow('/JavaRuntime.do?method=getThreadInfo&resourceid=");
/* 3514 */                   out.print(resourceid11);
/* 3515 */                   out.write("','ThreadInfo','scrollbars=yes,resizable=yes')\"> \n <br>\n");
/* 3516 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3517 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3521 */               if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3522 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */               }
/*      */               
/* 3525 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3526 */               out.write("\n\n<br>\n<table width=\"99%\"   border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n<td colspan=\"2\" height=\"31\" class=\"tableheading\">");
/* 3527 */               out.print(FormatUtil.getString("am.javaruntime.threaddumphistory"));
/* 3528 */               out.write("</td>\n</tr> \n");
/*      */               
/* 3530 */               if (threaddumphistory.size() > 0)
/*      */               {
/*      */ 
/* 3533 */                 out.write("\n\t<tr>\n\t<td width=\"80%\" class=\"columnheadingb\">");
/* 3534 */                 out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 3535 */                 out.write(32);
/* 3536 */                 out.write(38);
/* 3537 */                 out.write(32);
/* 3538 */                 out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/* 3539 */                 out.write("</td>\n");
/* 3540 */                 if ((request.isUserInRole("ADMIN")) && (!EnterpriseUtil.isAdminServer()))
/*      */                 {
/*      */ 
/* 3543 */                   out.write("\n\t<td width=\"20%%\" class=\"columnheadingb\">");
/* 3544 */                   out.print(FormatUtil.getString("am.webclient.rbm.delete.text"));
/* 3545 */                   out.write("</td>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 3549 */                 out.write("\n</tr>\n\t\n");
/*      */                 
/* 3551 */                 boolean extra = false;
/* 3552 */                 for (int k = 0; k < threaddumphistory.size(); k++)
/*      */                 {
/* 3554 */                   Properties url = (Properties)threaddumphistory.get(k);
/*      */                   
/* 3556 */                   out.write("\n\t<tr>\n\t<td style=\"padding-left:26px\" class=\"whitegrayborderbr\" title=\"");
/* 3557 */                   out.print(url.getProperty("URL"));
/* 3558 */                   out.write("\">\n\t<a class=\"staticlinks-blue\" href=\"javascript:void(0);\" onclick=\"javascript:MM_openBrWindow('");
/* 3559 */                   out.print(url.getProperty("URL"));
/* 3560 */                   out.write("','ThreadInfo','scrollbars=yes,resizable=yes')\">");
/* 3561 */                   out.print(url.getProperty("DSPNAME"));
/* 3562 */                   out.write("</a>\n\t</td>\n");
/* 3563 */                   if ((request.isUserInRole("ADMIN")) && (!EnterpriseUtil.isAdminServer()))
/*      */                   {
/*      */ 
/* 3566 */                     out.write("\t\n\t<td class=\"whitegrayborderbr\">\n\t<a title=\"Delete Thread Dump File\"  class=\"staticlinks\" href=\"javascript:void(0);\" onclick=\"javascript:deleteThreadDump('");
/* 3567 */                     out.print(url.getProperty("ABSURL"));
/* 3568 */                     out.write(39);
/* 3569 */                     out.write(44);
/* 3570 */                     out.write(39);
/* 3571 */                     out.print(resourceid11);
/* 3572 */                     out.write("');return false;\">\n\t<img hspace=\"5\" border=\"0\" src=\"/images/deleteWidget.gif\"/>\n\t</a>\n\t</td>\n");
/*      */                   }
/*      */                   
/*      */ 
/* 3576 */                   out.write("\n\t</tr>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 3580 */                 out.write("\t \n\t<tr>\n\t<td colspan=\"2\">\n\t<div id=\"exturl\" style=\"display:none\">\n\n\t</div>\n\t</td>\n\t</tr>\n");
/*      */                 
/* 3582 */                 if (rowCount > 5)
/*      */                 {
/*      */ 
/* 3585 */                   out.write("\n\t<tr>\n\t<td class=\"columnheadingb\" colspan=\"2\" align=\"right\"><a class=\"bodytext-nounderline\" href=\"javascript:void(0)\" id=\"more\" onclick=\"javascript:getThreadDumpData('");
/* 3586 */                   out.print(resourceid11);
/* 3587 */                   out.write("');\" >More...</a></td>");
/* 3588 */                   out.write("\n\t</tr>\n");
/*      */                 }
/*      */                 
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/* 3595 */                 out.write("\n\t<tr>\n\t<td  colspan=\"2\" class=\"whitegrayborderbr\" align=\"center\">");
/* 3596 */                 out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3597 */                 out.write("</td>\n\t</tr>\n");
/*      */               }
/*      */               
/*      */ 
/* 3601 */               out.write("\n</table>\n");
/*      */             }
/*      */           } catch (Exception ex) {
/* 3604 */             ex.printStackTrace();
/*      */           }
/* 3606 */           out.write("\n</form>\n");
/* 3607 */           out.write("\n</td>\n</tr>\n</table>");
/*      */         }
/* 3609 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3610 */         out = _jspx_out;
/* 3611 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3612 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3613 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3616 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3622 */     PageContext pageContext = _jspx_page_context;
/* 3623 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3625 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3626 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3627 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3629 */     _jspx_th_c_005fout_005f0.setValue("${fullvalue}");
/* 3630 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3631 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3632 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3633 */       return true;
/*      */     }
/* 3635 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3636 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3641 */     PageContext pageContext = _jspx_page_context;
/* 3642 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3644 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3645 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3646 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 3648 */     _jspx_th_c_005fout_005f1.setValue("${fullvalue}");
/* 3649 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3650 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3651 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3652 */       return true;
/*      */     }
/* 3654 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3655 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3660 */     PageContext pageContext = _jspx_page_context;
/* 3661 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3663 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3664 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3665 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 3667 */     _jspx_th_c_005fif_005f0.setTest("${not empty param.haid}");
/* 3668 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3669 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3671 */         out.write(10);
/* 3672 */         out.write(9);
/* 3673 */         out.write(9);
/* 3674 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3675 */           return true;
/* 3676 */         out.write(10);
/* 3677 */         out.write(9);
/* 3678 */         out.write(9);
/* 3679 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3680 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3684 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3685 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3686 */       return true;
/*      */     }
/* 3688 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3689 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3694 */     PageContext pageContext = _jspx_page_context;
/* 3695 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3697 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3698 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3699 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3701 */     _jspx_th_c_005fset_005f0.setVar("myfield_paramresid");
/*      */     
/* 3703 */     _jspx_th_c_005fset_005f0.setScope("page");
/* 3704 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3705 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 3706 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3707 */         out = _jspx_page_context.pushBody();
/* 3708 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 3709 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3712 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 3713 */           return true;
/* 3714 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 3715 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3718 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3719 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3722 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3723 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 3724 */       return true;
/*      */     }
/* 3726 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 3727 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3732 */     PageContext pageContext = _jspx_page_context;
/* 3733 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3735 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3736 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3737 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 3739 */     _jspx_th_c_005fout_005f2.setValue("${param.haid}");
/* 3740 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3741 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3742 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3743 */       return true;
/*      */     }
/* 3745 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3746 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3751 */     PageContext pageContext = _jspx_page_context;
/* 3752 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3754 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3755 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3756 */     _jspx_th_c_005fif_005f1.setParent(null);
/*      */     
/* 3758 */     _jspx_th_c_005fif_005f1.setTest("${not empty param.resourceid}");
/* 3759 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3760 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3762 */         out.write(10);
/* 3763 */         out.write(9);
/* 3764 */         out.write(9);
/* 3765 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3766 */           return true;
/* 3767 */         out.write(10);
/* 3768 */         out.write(9);
/* 3769 */         out.write(9);
/* 3770 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3771 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3775 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3776 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3777 */       return true;
/*      */     }
/* 3779 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3780 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3785 */     PageContext pageContext = _jspx_page_context;
/* 3786 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3788 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3789 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 3790 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3792 */     _jspx_th_c_005fset_005f1.setVar("myfield_paramresid");
/*      */     
/* 3794 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 3795 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 3796 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 3797 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3798 */         out = _jspx_page_context.pushBody();
/* 3799 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 3800 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3803 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 3804 */           return true;
/* 3805 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 3806 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3809 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3810 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3813 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 3814 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 3815 */       return true;
/*      */     }
/* 3817 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 3818 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3823 */     PageContext pageContext = _jspx_page_context;
/* 3824 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3826 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3827 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3828 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 3830 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 3831 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3832 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3833 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3834 */       return true;
/*      */     }
/* 3836 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3837 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3842 */     PageContext pageContext = _jspx_page_context;
/* 3843 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3845 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3846 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3847 */     _jspx_th_c_005fout_005f4.setParent(null);
/*      */     
/* 3849 */     _jspx_th_c_005fout_005f4.setValue("${myfield_paramresid}");
/* 3850 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3851 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3852 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3853 */       return true;
/*      */     }
/* 3855 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3856 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f4(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3861 */     PageContext pageContext = _jspx_page_context;
/* 3862 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3864 */     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3865 */     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3866 */     _jspx_th_logic_005fpresent_005f4.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 3868 */     _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 3869 */     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3870 */     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */       for (;;) {
/* 3872 */         out.write("\n\t\t<a href=\"javascript:alertUser()\"><img src=\"/images/icon_executeaction.gif\"  border=\"0\"></a></td>\n\t\t");
/* 3873 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3874 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3878 */     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3879 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3880 */       return true;
/*      */     }
/* 3882 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3883 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\includes\cam_005fscreen_005fconf_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */