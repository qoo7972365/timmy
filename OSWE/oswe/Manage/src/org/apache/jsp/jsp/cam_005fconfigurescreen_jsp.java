/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.framework.ResourceName;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
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
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class cam_005fconfigurescreen_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   52 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   55 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   56 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   57 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   64 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   69 */     ArrayList list = null;
/*   70 */     StringBuffer sbf = new StringBuffer();
/*   71 */     ManagedApplication mo = new ManagedApplication();
/*   72 */     if (distinct)
/*      */     {
/*   74 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   78 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   81 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   83 */       ArrayList row = (ArrayList)list.get(i);
/*   84 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   85 */       if (distinct) {
/*   86 */         sbf.append(row.get(0));
/*      */       } else
/*   88 */         sbf.append(row.get(1));
/*   89 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   92 */     return sbf.toString(); }
/*      */   
/*   94 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   97 */     if (severity == null)
/*      */     {
/*   99 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  101 */     if (severity.equals("5"))
/*      */     {
/*  103 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  105 */     if (severity.equals("1"))
/*      */     {
/*  107 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  112 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  119 */     if (severity == null)
/*      */     {
/*  121 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  123 */     if (severity.equals("1"))
/*      */     {
/*  125 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  127 */     if (severity.equals("4"))
/*      */     {
/*  129 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  131 */     if (severity.equals("5"))
/*      */     {
/*  133 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  138 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  144 */     if (severity == null)
/*      */     {
/*  146 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  148 */     if (severity.equals("5"))
/*      */     {
/*  150 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  152 */     if (severity.equals("1"))
/*      */     {
/*  154 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  158 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  164 */     if (severity == null)
/*      */     {
/*  166 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  168 */     if (severity.equals("1"))
/*      */     {
/*  170 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  172 */     if (severity.equals("4"))
/*      */     {
/*  174 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  176 */     if (severity.equals("5"))
/*      */     {
/*  178 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  182 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  188 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  194 */     if (severity == 5)
/*      */     {
/*  196 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  198 */     if (severity == 1)
/*      */     {
/*  200 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  205 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  211 */     if (severity == null)
/*      */     {
/*  213 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  215 */     if (severity.equals("5"))
/*      */     {
/*  217 */       if (isAvailability) {
/*  218 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  221 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  224 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  226 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  228 */     if (severity.equals("1"))
/*      */     {
/*  230 */       if (isAvailability) {
/*  231 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  234 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  241 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  248 */     if (severity == null)
/*      */     {
/*  250 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  252 */     if (severity.equals("5"))
/*      */     {
/*  254 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  256 */     if (severity.equals("4"))
/*      */     {
/*  258 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  260 */     if (severity.equals("1"))
/*      */     {
/*  262 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  267 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  273 */     if (severity == null)
/*      */     {
/*  275 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  277 */     if (severity.equals("5"))
/*      */     {
/*  279 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  281 */     if (severity.equals("4"))
/*      */     {
/*  283 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  285 */     if (severity.equals("1"))
/*      */     {
/*  287 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  292 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  299 */     if (severity == null)
/*      */     {
/*  301 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  303 */     if (severity.equals("5"))
/*      */     {
/*  305 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  307 */     if (severity.equals("4"))
/*      */     {
/*  309 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  311 */     if (severity.equals("1"))
/*      */     {
/*  313 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  318 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  326 */     StringBuffer out = new StringBuffer();
/*  327 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  328 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  329 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  330 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  331 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  332 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  333 */     out.append("</tr>");
/*  334 */     out.append("</form></table>");
/*  335 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  342 */     if (val == null)
/*      */     {
/*  344 */       return "-";
/*      */     }
/*      */     
/*  347 */     String ret = FormatUtil.formatNumber(val);
/*  348 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  349 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  352 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  356 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  364 */     StringBuffer out = new StringBuffer();
/*  365 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  366 */     out.append("<tr>");
/*  367 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  369 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  371 */     out.append("</tr>");
/*  372 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  376 */       if (j % 2 == 0)
/*      */       {
/*  378 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  382 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  385 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  387 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  390 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  394 */       out.append("</tr>");
/*      */     }
/*  396 */     out.append("</table>");
/*  397 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  398 */     out.append("<tr>");
/*  399 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  400 */     out.append("</tr>");
/*  401 */     out.append("</table>");
/*  402 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  408 */     StringBuffer out = new StringBuffer();
/*  409 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  410 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  411 */     out.append("<tr>");
/*  412 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  413 */     out.append("<tr>");
/*  414 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  415 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  416 */     out.append("</tr>");
/*  417 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  420 */       out.append("<tr>");
/*  421 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  422 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  423 */       out.append("</tr>");
/*      */     }
/*      */     
/*  426 */     out.append("</table>");
/*  427 */     out.append("</table>");
/*  428 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  433 */     if (severity.equals("0"))
/*      */     {
/*  435 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  439 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  446 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  459 */     StringBuffer out = new StringBuffer();
/*  460 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  461 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  463 */       out.append("<tr>");
/*  464 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  465 */       out.append("</tr>");
/*      */       
/*      */ 
/*  468 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  470 */         String borderclass = "";
/*      */         
/*      */ 
/*  473 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  475 */         out.append("<tr>");
/*      */         
/*  477 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  478 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  479 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  485 */     out.append("</table><br>");
/*  486 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  487 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  489 */       List sLinks = secondLevelOfLinks[0];
/*  490 */       List sText = secondLevelOfLinks[1];
/*  491 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  494 */         out.append("<tr>");
/*  495 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  496 */         out.append("</tr>");
/*  497 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  499 */           String borderclass = "";
/*      */           
/*      */ 
/*  502 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  504 */           out.append("<tr>");
/*      */           
/*  506 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  507 */           if (sLinks.get(i).toString().length() == 0) {
/*  508 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  511 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  513 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  517 */     out.append("</table>");
/*  518 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  525 */     StringBuffer out = new StringBuffer();
/*  526 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  527 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  529 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  531 */         out.append("<tr>");
/*  532 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  533 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  537 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  539 */           String borderclass = "";
/*      */           
/*      */ 
/*  542 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  544 */           out.append("<tr>");
/*      */           
/*  546 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  547 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  548 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  551 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  554 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  559 */     out.append("</table><br>");
/*  560 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  561 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  563 */       List sLinks = secondLevelOfLinks[0];
/*  564 */       List sText = secondLevelOfLinks[1];
/*  565 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  568 */         out.append("<tr>");
/*  569 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  570 */         out.append("</tr>");
/*  571 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  573 */           String borderclass = "";
/*      */           
/*      */ 
/*  576 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  578 */           out.append("<tr>");
/*      */           
/*  580 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  581 */           if (sLinks.get(i).toString().length() == 0) {
/*  582 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  585 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  587 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  591 */     out.append("</table>");
/*  592 */     return out.toString();
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
/*  605 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  608 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  611 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  614 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  617 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  620 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  623 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  626 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  634 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  639 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  644 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  649 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  654 */     if (val != null)
/*      */     {
/*  656 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  660 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  665 */     if (val == null) {
/*  666 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  670 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  675 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  681 */     if (val != null)
/*      */     {
/*  683 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  687 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  693 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  698 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  702 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  707 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  712 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  717 */     String hostaddress = "";
/*  718 */     String ip = request.getHeader("x-forwarded-for");
/*  719 */     if (ip == null)
/*  720 */       ip = request.getRemoteAddr();
/*  721 */     InetAddress add = null;
/*  722 */     if (ip.equals("127.0.0.1")) {
/*  723 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  727 */       add = InetAddress.getByName(ip);
/*      */     }
/*  729 */     hostaddress = add.getHostName();
/*  730 */     if (hostaddress.indexOf('.') != -1) {
/*  731 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  732 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  736 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  741 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  747 */     if (severity == null)
/*      */     {
/*  749 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  751 */     if (severity.equals("5"))
/*      */     {
/*  753 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  755 */     if (severity.equals("1"))
/*      */     {
/*  757 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  762 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  767 */     ResultSet set = null;
/*  768 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  769 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  771 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  772 */       if (set.next()) { String str1;
/*  773 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  774 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  777 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  782 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  785 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  787 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  791 */     StringBuffer rca = new StringBuffer();
/*  792 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  793 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  796 */     int rcalength = key.length();
/*  797 */     String split = "6. ";
/*  798 */     int splitPresent = key.indexOf(split);
/*  799 */     String div1 = "";String div2 = "";
/*  800 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  802 */       if (rcalength > 180) {
/*  803 */         rca.append("<span class=\"rca-critical-text\">");
/*  804 */         getRCATrimmedText(key, rca);
/*  805 */         rca.append("</span>");
/*      */       } else {
/*  807 */         rca.append("<span class=\"rca-critical-text\">");
/*  808 */         rca.append(key);
/*  809 */         rca.append("</span>");
/*      */       }
/*  811 */       return rca.toString();
/*      */     }
/*  813 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  814 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  815 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  816 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  817 */     getRCATrimmedText(div1, rca);
/*  818 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  821 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  822 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  823 */     getRCATrimmedText(div2, rca);
/*  824 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  826 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  831 */     String[] st = msg.split("<br>");
/*  832 */     for (int i = 0; i < st.length; i++) {
/*  833 */       String s = st[i];
/*  834 */       if (s.length() > 180) {
/*  835 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  837 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  841 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  842 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  844 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  848 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  849 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  850 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  853 */       if (key == null) {
/*  854 */         return ret;
/*      */       }
/*      */       
/*  857 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  858 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  861 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  862 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  863 */       set = AMConnectionPool.executeQueryStmt(query);
/*  864 */       if (set.next())
/*      */       {
/*  866 */         String helpLink = set.getString("LINK");
/*  867 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  870 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  876 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  895 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  886 */         if (set != null) {
/*  887 */           AMConnectionPool.closeStatement(set);
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
/*  901 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  902 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  904 */       String entityStr = (String)keys.nextElement();
/*  905 */       String mmessage = temp.getProperty(entityStr);
/*  906 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  907 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  909 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  915 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  916 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  918 */       String entityStr = (String)keys.nextElement();
/*  919 */       String mmessage = temp.getProperty(entityStr);
/*  920 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  921 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  923 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  928 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  938 */     String des = new String();
/*  939 */     while (str.indexOf(find) != -1) {
/*  940 */       des = des + str.substring(0, str.indexOf(find));
/*  941 */       des = des + replace;
/*  942 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  944 */     des = des + str;
/*  945 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  952 */       if (alert == null)
/*      */       {
/*  954 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  956 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  958 */         return "&nbsp;";
/*      */       }
/*      */       
/*  961 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  963 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  966 */       int rcalength = test.length();
/*  967 */       if (rcalength < 300)
/*      */       {
/*  969 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  973 */       StringBuffer out = new StringBuffer();
/*  974 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  975 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  976 */       out.append("</div>");
/*  977 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  978 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  979 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  984 */       ex.printStackTrace();
/*      */     }
/*  986 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  992 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  997 */     ArrayList attribIDs = new ArrayList();
/*  998 */     ArrayList resIDs = new ArrayList();
/*  999 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1001 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1003 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1005 */       String resourceid = "";
/* 1006 */       String resourceType = "";
/* 1007 */       if (type == 2) {
/* 1008 */         resourceid = (String)row.get(0);
/* 1009 */         resourceType = (String)row.get(3);
/*      */       }
/* 1011 */       else if (type == 3) {
/* 1012 */         resourceid = (String)row.get(0);
/* 1013 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1016 */         resourceid = (String)row.get(6);
/* 1017 */         resourceType = (String)row.get(7);
/*      */       }
/* 1019 */       resIDs.add(resourceid);
/* 1020 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1021 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1023 */       String healthentity = null;
/* 1024 */       String availentity = null;
/* 1025 */       if (healthid != null) {
/* 1026 */         healthentity = resourceid + "_" + healthid;
/* 1027 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1030 */       if (availid != null) {
/* 1031 */         availentity = resourceid + "_" + availid;
/* 1032 */         entitylist.add(availentity);
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
/* 1046 */     Properties alert = getStatus(entitylist);
/* 1047 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1052 */     int size = monitorList.size();
/*      */     
/* 1054 */     String[] severity = new String[size];
/*      */     
/* 1056 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1058 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1059 */       String resourceName1 = (String)row1.get(7);
/* 1060 */       String resourceid1 = (String)row1.get(6);
/* 1061 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1062 */       if (severity[j] == null)
/*      */       {
/* 1064 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1068 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1070 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1072 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1075 */         if (sev > 0) {
/* 1076 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1077 */           monitorList.set(k, monitorList.get(j));
/* 1078 */           monitorList.set(j, t);
/* 1079 */           String temp = severity[k];
/* 1080 */           severity[k] = severity[j];
/* 1081 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1087 */     int z = 0;
/* 1088 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1091 */       int i = 0;
/* 1092 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1095 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1099 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1103 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1105 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1108 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1112 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1115 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1116 */       String resourceName1 = (String)row1.get(7);
/* 1117 */       String resourceid1 = (String)row1.get(6);
/* 1118 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1119 */       if (hseverity[j] == null)
/*      */       {
/* 1121 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1126 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1128 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1131 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1134 */         if (hsev > 0) {
/* 1135 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1136 */           monitorList.set(k, monitorList.get(j));
/* 1137 */           monitorList.set(j, t);
/* 1138 */           String temp1 = hseverity[k];
/* 1139 */           hseverity[k] = hseverity[j];
/* 1140 */           hseverity[j] = temp1;
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
/* 1152 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1153 */     boolean forInventory = false;
/* 1154 */     String trdisplay = "none";
/* 1155 */     String plusstyle = "inline";
/* 1156 */     String minusstyle = "none";
/* 1157 */     String haidTopLevel = "";
/* 1158 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1160 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1162 */         haidTopLevel = request.getParameter("haid");
/* 1163 */         forInventory = true;
/* 1164 */         trdisplay = "table-row;";
/* 1165 */         plusstyle = "none";
/* 1166 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1173 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1176 */     ArrayList listtoreturn = new ArrayList();
/* 1177 */     StringBuffer toreturn = new StringBuffer();
/* 1178 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1179 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1180 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1182 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1184 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1185 */       String childresid = (String)singlerow.get(0);
/* 1186 */       String childresname = (String)singlerow.get(1);
/* 1187 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1188 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1189 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1190 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1191 */       String unmanagestatus = (String)singlerow.get(5);
/* 1192 */       String actionstatus = (String)singlerow.get(6);
/* 1193 */       String linkclass = "monitorgp-links";
/* 1194 */       String titleforres = childresname;
/* 1195 */       String titilechildresname = childresname;
/* 1196 */       String childimg = "/images/trcont.png";
/* 1197 */       String flag = "enable";
/* 1198 */       String dcstarted = (String)singlerow.get(8);
/* 1199 */       String configMonitor = "";
/* 1200 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1201 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1203 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1205 */       if (singlerow.get(7) != null)
/*      */       {
/* 1207 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1209 */       String haiGroupType = "0";
/* 1210 */       if ("HAI".equals(childtype))
/*      */       {
/* 1212 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1214 */       childimg = "/images/trend.png";
/* 1215 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1216 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1217 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1219 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1221 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1223 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1224 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1227 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1229 */         linkclass = "disabledtext";
/* 1230 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1232 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1233 */       String availmouseover = "";
/* 1234 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1236 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1238 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1239 */       String healthmouseover = "";
/* 1240 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1242 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1245 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1246 */       int spacing = 0;
/* 1247 */       if (level >= 1)
/*      */       {
/* 1249 */         spacing = 40 * level;
/*      */       }
/* 1251 */       if (childtype.equals("HAI"))
/*      */       {
/* 1253 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1254 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1255 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1257 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1258 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1259 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1260 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1261 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1262 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1263 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1264 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1265 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1266 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1267 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1269 */         if (!forInventory)
/*      */         {
/* 1271 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1274 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1276 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1278 */           actions = editlink + actions;
/*      */         }
/* 1280 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1282 */           actions = actions + associatelink;
/*      */         }
/* 1284 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1285 */         String arrowimg = "";
/* 1286 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1288 */           actions = "";
/* 1289 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1290 */           checkbox = "";
/* 1291 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1293 */         if (isIt360)
/*      */         {
/* 1295 */           actionimg = "";
/* 1296 */           actions = "";
/* 1297 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1298 */           checkbox = "";
/*      */         }
/*      */         
/* 1301 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1303 */           actions = "";
/*      */         }
/* 1305 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1307 */           checkbox = "";
/*      */         }
/*      */         
/* 1310 */         String resourcelink = "";
/*      */         
/* 1312 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1314 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1318 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1321 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1322 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1323 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1324 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1325 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1326 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1327 */         if (!isIt360)
/*      */         {
/* 1329 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1333 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1336 */         toreturn.append("</tr>");
/* 1337 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1339 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1340 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1344 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1345 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1348 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1352 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1354 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1355 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1356 */             toreturn.append(assocMessage);
/* 1357 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1358 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1359 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1360 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1366 */         String resourcelink = null;
/* 1367 */         boolean hideEditLink = false;
/* 1368 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1370 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1371 */           hideEditLink = true;
/* 1372 */           if (isIt360)
/*      */           {
/* 1374 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1378 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1380 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1382 */           hideEditLink = true;
/* 1383 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1384 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1389 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1392 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1393 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1394 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1395 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1396 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1397 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1398 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1399 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1400 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1401 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1402 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1403 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1404 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1406 */         if (hideEditLink)
/*      */         {
/* 1408 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1410 */         if (!forInventory)
/*      */         {
/* 1412 */           removefromgroup = "";
/*      */         }
/* 1414 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1415 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1416 */           actions = actions + configcustomfields;
/*      */         }
/* 1418 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1420 */           actions = editlink + actions;
/*      */         }
/* 1422 */         String managedLink = "";
/* 1423 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1425 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1426 */           actions = "";
/* 1427 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1428 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1431 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1433 */           checkbox = "";
/*      */         }
/*      */         
/* 1436 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1438 */           actions = "";
/*      */         }
/* 1440 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1441 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1442 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1443 */         if (isIt360)
/*      */         {
/* 1445 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1449 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1451 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1452 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1453 */         if (!isIt360)
/*      */         {
/* 1455 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1459 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1461 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1464 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1471 */       StringBuilder toreturn = new StringBuilder();
/* 1472 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1473 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1474 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1475 */       String title = "";
/* 1476 */       message = EnterpriseUtil.decodeString(message);
/* 1477 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1478 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1479 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1481 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1483 */       else if ("5".equals(severity))
/*      */       {
/* 1485 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1489 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1491 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1492 */       toreturn.append(v);
/*      */       
/* 1494 */       toreturn.append(link);
/* 1495 */       if (severity == null)
/*      */       {
/* 1497 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1499 */       else if (severity.equals("5"))
/*      */       {
/* 1501 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1503 */       else if (severity.equals("4"))
/*      */       {
/* 1505 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1507 */       else if (severity.equals("1"))
/*      */       {
/* 1509 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1514 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1516 */       toreturn.append("</a>");
/* 1517 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1521 */       ex.printStackTrace();
/*      */     }
/* 1523 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1530 */       StringBuilder toreturn = new StringBuilder();
/* 1531 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1532 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1533 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1534 */       if (message == null)
/*      */       {
/* 1536 */         message = "";
/*      */       }
/*      */       
/* 1539 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1540 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1542 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1543 */       toreturn.append(v);
/*      */       
/* 1545 */       toreturn.append(link);
/*      */       
/* 1547 */       if (severity == null)
/*      */       {
/* 1549 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1551 */       else if (severity.equals("5"))
/*      */       {
/* 1553 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1555 */       else if (severity.equals("1"))
/*      */       {
/* 1557 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1562 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1564 */       toreturn.append("</a>");
/* 1565 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1571 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1574 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1575 */     if (invokeActions != null) {
/* 1576 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1577 */       while (iterator.hasNext()) {
/* 1578 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1579 */         if (actionmap.containsKey(actionid)) {
/* 1580 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1585 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1589 */     String actionLink = "";
/* 1590 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1591 */     String query = "";
/* 1592 */     ResultSet rs = null;
/* 1593 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1594 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1595 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1596 */       actionLink = "method=" + methodName;
/*      */     }
/* 1598 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1599 */       actionLink = methodName;
/*      */     }
/* 1601 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1602 */     Iterator itr = methodarglist.iterator();
/* 1603 */     boolean isfirstparam = true;
/* 1604 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1605 */     while (itr.hasNext()) {
/* 1606 */       HashMap argmap = (HashMap)itr.next();
/* 1607 */       String argtype = (String)argmap.get("TYPE");
/* 1608 */       String argname = (String)argmap.get("IDENTITY");
/* 1609 */       String paramname = (String)argmap.get("PARAMETER");
/* 1610 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1611 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1612 */         isfirstparam = false;
/* 1613 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1615 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1619 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1623 */         actionLink = actionLink + "&";
/*      */       }
/* 1625 */       String paramValue = null;
/* 1626 */       String tempargname = argname;
/* 1627 */       if (commonValues.getProperty(tempargname) != null) {
/* 1628 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1631 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1632 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1633 */           if (dbType.equals("mysql")) {
/* 1634 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1637 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1639 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1641 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1642 */             if (rs.next()) {
/* 1643 */               paramValue = rs.getString("VALUE");
/* 1644 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1648 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1652 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1655 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1660 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1661 */           paramValue = rowId;
/*      */         }
/* 1663 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1664 */           paramValue = managedObjectName;
/*      */         }
/* 1666 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1667 */           paramValue = resID;
/*      */         }
/* 1669 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1670 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1673 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1675 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1676 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1677 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1679 */     return actionLink;
/*      */   }
/*      */   
/* 1682 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1683 */     String dependentAttribute = null;
/* 1684 */     String align = "left";
/*      */     
/* 1686 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1687 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1688 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1689 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1690 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1691 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1692 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1693 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1694 */       align = "center";
/*      */     }
/*      */     
/* 1697 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1698 */     String actualdata = "";
/*      */     
/* 1700 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1701 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1702 */         actualdata = availValue;
/*      */       }
/* 1704 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1705 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1709 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1710 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1713 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1719 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1720 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1721 */       toreturn.append("<table>");
/* 1722 */       toreturn.append("<tr>");
/* 1723 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1724 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1725 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1726 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1727 */         String toolTip = "";
/* 1728 */         String hideClass = "";
/* 1729 */         String textStyle = "";
/* 1730 */         boolean isreferenced = true;
/* 1731 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1732 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1733 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1734 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1736 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1737 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1738 */           while (valueList.hasMoreTokens()) {
/* 1739 */             String dependentVal = valueList.nextToken();
/* 1740 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1741 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1742 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1744 */               toolTip = "";
/* 1745 */               hideClass = "";
/* 1746 */               isreferenced = false;
/* 1747 */               textStyle = "disabledtext";
/* 1748 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1752 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1753 */           toolTip = "";
/* 1754 */           hideClass = "";
/* 1755 */           isreferenced = false;
/* 1756 */           textStyle = "disabledtext";
/* 1757 */           if (dependentImageMap != null) {
/* 1758 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1759 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1762 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1766 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1767 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1768 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1769 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1770 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1771 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1773 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1774 */           if (isreferenced) {
/* 1775 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1779 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1780 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1781 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1782 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1783 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1784 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1786 */           toreturn.append("</span>");
/* 1787 */           toreturn.append("</a>");
/* 1788 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1791 */       toreturn.append("</tr>");
/* 1792 */       toreturn.append("</table>");
/* 1793 */       toreturn.append("</td>");
/*      */     } else {
/* 1795 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1798 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1802 */     String colTime = null;
/* 1803 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1804 */     if ((rows != null) && (rows.size() > 0)) {
/* 1805 */       Iterator<String> itr = rows.iterator();
/* 1806 */       String maxColQuery = "";
/* 1807 */       for (;;) { if (itr.hasNext()) {
/* 1808 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1809 */           ResultSet maxCol = null;
/*      */           try {
/* 1811 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1812 */             while (maxCol.next()) {
/* 1813 */               if (colTime == null) {
/* 1814 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1817 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1826 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1828 */               if (maxCol != null)
/* 1829 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1831 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1826 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1828 */               if (maxCol != null)
/* 1829 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1831 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1836 */     return colTime;
/*      */   }
/*      */   
/* 1839 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1840 */     tablename = null;
/* 1841 */     ResultSet rsTable = null;
/* 1842 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1844 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1845 */       while (rsTable.next()) {
/* 1846 */         tablename = rsTable.getString("DATATABLE");
/* 1847 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1848 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1861 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1852 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1855 */         if (rsTable != null)
/* 1856 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1858 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1864 */     String argsList = "";
/* 1865 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1867 */       if (showArgsMap.get(row) != null) {
/* 1868 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1869 */         if (showArgslist != null) {
/* 1870 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1871 */             if (argsList.trim().equals("")) {
/* 1872 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1875 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1882 */       e.printStackTrace();
/* 1883 */       return "";
/*      */     }
/* 1885 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1890 */     String argsList = "";
/* 1891 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1894 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1896 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1897 */         if (hideArgsList != null)
/*      */         {
/* 1899 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1901 */             if (argsList.trim().equals(""))
/*      */             {
/* 1903 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1907 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1915 */       ex.printStackTrace();
/*      */     }
/* 1917 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1921 */     StringBuilder toreturn = new StringBuilder();
/* 1922 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1929 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1930 */       Iterator itr = tActionList.iterator();
/* 1931 */       while (itr.hasNext()) {
/* 1932 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1933 */         String confirmmsg = "";
/* 1934 */         String link = "";
/* 1935 */         String isJSP = "NO";
/* 1936 */         HashMap tactionMap = (HashMap)itr.next();
/* 1937 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1938 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1939 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1940 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1941 */           (actionmap.containsKey(actionId))) {
/* 1942 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1943 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1944 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1945 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1946 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1948 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1954 */           if (isTableAction) {
/* 1955 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1958 */             tableName = "Link";
/* 1959 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1960 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1961 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1962 */             toreturn.append("</a></td>");
/*      */           }
/* 1964 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1965 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1966 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1967 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1973 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1979 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1981 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1982 */       Properties prop = (Properties)node.getUserObject();
/* 1983 */       String mgID = prop.getProperty("label");
/* 1984 */       String mgName = prop.getProperty("value");
/* 1985 */       String isParent = prop.getProperty("isParent");
/* 1986 */       int mgIDint = Integer.parseInt(mgID);
/* 1987 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1989 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1991 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1992 */       if (node.getChildCount() > 0)
/*      */       {
/* 1994 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1996 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1998 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2000 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2004 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2009 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2011 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2013 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2015 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2019 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2022 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2023 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2025 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2029 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2031 */       if (node.getChildCount() > 0)
/*      */       {
/* 2033 */         builder.append("<UL>");
/* 2034 */         printMGTree(node, builder);
/* 2035 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2040 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2041 */     StringBuffer toReturn = new StringBuffer();
/* 2042 */     String table = "-";
/*      */     try {
/* 2044 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2045 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2046 */       float total = 0.0F;
/* 2047 */       while (it.hasNext()) {
/* 2048 */         String attName = (String)it.next();
/* 2049 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2050 */         boolean roundOffData = false;
/* 2051 */         if ((data != null) && (!data.equals(""))) {
/* 2052 */           if (data.indexOf(",") != -1) {
/* 2053 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2056 */             float value = Float.parseFloat(data);
/* 2057 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2060 */             total += value;
/* 2061 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2064 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2069 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2070 */       while (attVsWidthList.hasNext()) {
/* 2071 */         String attName = (String)attVsWidthList.next();
/* 2072 */         String data = (String)attVsWidthProps.get(attName);
/* 2073 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2074 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2075 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2076 */         String className = (String)graphDetails.get("ClassName");
/* 2077 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2078 */         if (percentage < 1.0F)
/*      */         {
/* 2080 */           data = percentage + "";
/*      */         }
/* 2082 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2084 */       if (toReturn.length() > 0) {
/* 2085 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2089 */       e.printStackTrace();
/*      */     }
/* 2091 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2097 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2098 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2099 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2100 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2101 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2102 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2103 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2104 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2105 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2108 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2109 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2110 */       splitvalues[0] = multiplecondition.toString();
/* 2111 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2114 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2119 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2120 */     if (thresholdType != 3) {
/* 2121 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2122 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2123 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2124 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2125 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2126 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2128 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2129 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2130 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2131 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2132 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2133 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2135 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2136 */     if (updateSelected != null) {
/* 2137 */       updateSelected[0] = "selected";
/*      */     }
/* 2139 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2144 */       StringBuffer toreturn = new StringBuffer("");
/* 2145 */       if (commaSeparatedMsgId != null) {
/* 2146 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2147 */         int count = 0;
/* 2148 */         while (msgids.hasMoreTokens()) {
/* 2149 */           String id = msgids.nextToken();
/* 2150 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2151 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2152 */           count++;
/* 2153 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2154 */             if (toreturn.length() == 0) {
/* 2155 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2157 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2158 */             if (!image.trim().equals("")) {
/* 2159 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2161 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2162 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2165 */         if (toreturn.length() > 0) {
/* 2166 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2170 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2173 */       e.printStackTrace(); }
/* 2174 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2180 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2186 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2187 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2188 */     _jspx_dependants.put("/jsp/includes/MonitorDiscoveryStatus.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
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
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2229 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2233 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2234 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2235 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2236 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2238 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2239 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2241 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2242 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2246 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2253 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2256 */     JspWriter out = null;
/* 2257 */     Object page = this;
/* 2258 */     JspWriter _jspx_out = null;
/* 2259 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2263 */       response.setContentType("text/html;charset=UTF-8");
/* 2264 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2266 */       _jspx_page_context = pageContext;
/* 2267 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2268 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2269 */       session = pageContext.getSession();
/* 2270 */       out = pageContext.getOut();
/* 2271 */       _jspx_out = out;
/*      */       
/* 2273 */       out.write("<!DOCTYPE html>\n");
/* 2274 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!-- $Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2275 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2277 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2278 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2279 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2281 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2283 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2285 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2287 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2288 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2289 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2290 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2293 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2294 */         String available = null;
/* 2295 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2296 */         out.write(10);
/*      */         
/* 2298 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2299 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2300 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2302 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2304 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2306 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2308 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2309 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2310 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2311 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2314 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2315 */           String unavailable = null;
/* 2316 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2317 */           out.write(10);
/*      */           
/* 2319 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2320 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2321 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2323 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2325 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2327 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2329 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2330 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2331 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2332 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2335 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2336 */             String unmanaged = null;
/* 2337 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2338 */             out.write(10);
/*      */             
/* 2340 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2341 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2342 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2344 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2346 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2348 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2350 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2351 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2352 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2353 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2356 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2357 */               String scheduled = null;
/* 2358 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2359 */               out.write(10);
/*      */               
/* 2361 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2362 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2363 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2365 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2367 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2369 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2371 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2372 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2373 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2374 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2377 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2378 */                 String critical = null;
/* 2379 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2380 */                 out.write(10);
/*      */                 
/* 2382 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2383 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2384 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2386 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2388 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2390 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2392 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2393 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2394 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2395 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2398 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2399 */                   String clear = null;
/* 2400 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2401 */                   out.write(10);
/*      */                   
/* 2403 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2404 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2405 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2407 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2409 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2411 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2413 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2414 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2415 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2416 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2419 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2420 */                     String warning = null;
/* 2421 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2422 */                     out.write(10);
/* 2423 */                     out.write(10);
/*      */                     
/* 2425 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2426 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2428 */                     out.write(10);
/* 2429 */                     out.write(10);
/* 2430 */                     out.write(10);
/* 2431 */                     out.write(10);
/*      */                     
/* 2433 */                     request.setAttribute("HelpKey", "Create New CAM");
/*      */                     
/* 2435 */                     out.write(10);
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2444 */                     String camID = (String)request.getAttribute("camid");
/*      */                     
/*      */ 
/* 2447 */                     String camDescription = (String)request.getAttribute("camdesc");
/* 2448 */                     String camName = (String)request.getAttribute("camname");
/*      */                     
/* 2450 */                     if (camName == null) {
/* 2451 */                       List list = CAMDBUtil.getCAMDetails(camID);
/* 2452 */                       camName = (String)list.get(0);
/* 2453 */                       camDescription = (String)list.get(1);
/*      */                     }
/*      */                     
/* 2456 */                     Map resourceInfoFromResourcePage = null;
/*      */                     
/* 2458 */                     if ("true".equals((String)request.getAttribute("isfromresourcepage"))) {
/* 2459 */                       resourceInfoFromResourcePage = CAMDBUtil.getAMMOInfo(Integer.parseInt(camID));
/*      */                     }
/*      */                     
/*      */ 
/* 2463 */                     request.setAttribute("needDeleteScreen", "hint for cam_leftlinks.jsp");
/*      */                     
/* 2465 */                     String haid = (String)request.getAttribute("haid");
/* 2466 */                     String screenID = (String)request.getAttribute("screenid");
/* 2467 */                     List screenInfo = CAMDBUtil.getScreenInfo(Long.parseLong(screenID));
/* 2468 */                     Map fromDB = CAMDBUtil.getCustomizedDataForScreenAdminActivity(Long.parseLong(screenID));
/* 2469 */                     System.out.println("The fromDB value:" + fromDB);
/* 2470 */                     List screenConfigList = (List)fromDB.get("completedata");
/* 2471 */                     Map tableDetails = (HashMap)fromDB.get("tabledata");
/* 2472 */                     List reportsData = (List)fromDB.get("reportsdata");
/* 2473 */                     System.out.println("the reportsData:" + reportsData);
/* 2474 */                     String tableIDsAsString = "";
/* 2475 */                     if (request.getAttribute("datacollectableagents") == null) {
/* 2476 */                       List agentsList = CAMDBUtil.getDataCollectableAgentDetails(true, request);
/* 2477 */                       request.setAttribute("datacollectableagents", agentsList);
/*      */                     }
/*      */                     
/*      */ 
/* 2481 */                     String quickNote = FormatUtil.getString("am.webclient.cam.quicknote1") + FormatUtil.getString("am.webclient.cam.quicknote2");
/*      */                     
/* 2483 */                     String topDesc = FormatUtil.getString("am.webclient.cam.configurescreen.description");
/*      */                     
/*      */ 
/* 2486 */                     request.setAttribute("quicknote", quickNote);
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2492 */                     out.write(10);
/* 2493 */                     out.write(10);
/*      */                     
/* 2495 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2496 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2497 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2499 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2500 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2501 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2503 */                         out.write(10);
/*      */                         
/* 2505 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2506 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2507 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2509 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2511 */                         _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.cam.title"));
/* 2512 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2513 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2514 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2517 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2518 */                         out.write(10);
/* 2519 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2521 */                         out.write(10);
/* 2522 */                         out.write(10);
/* 2523 */                         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2525 */                         out.write("\n<script>\nfunction validateAndSubmit()\n{\n   if(trimAll(document.AMActionForm.camname.value)==\"\")\n   {\n        alert('");
/* 2526 */                         out.print(FormatUtil.getString("am.webclient.cam.namealert"));
/* 2527 */                         out.write("');\n        return;\n   }\n   document.AMActionForm.submit();\n}\n\n</script>\n");
/*      */                         
/* 2529 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2530 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2531 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2533 */                         _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                         
/* 2535 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 2536 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2537 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2538 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2539 */                             out = _jspx_page_context.pushBody();
/* 2540 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2541 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2544 */                             out.write("\n<script>\nfunction showDomains() {\n    var refSel = document.CAMJMXAgentDetailsForm.resourceid;\n\n   if(refSel.type != 'hidden') {\n\n    if(refSel.selectedIndex == -1 ) {\n    \talert(\"");
/* 2545 */                             out.print(FormatUtil.getString("am.webclient.cam.configurescreen.alert1"));
/* 2546 */                             out.write("\");\n    } else {\n\n    \tif(refSel.options[refSel.selectedIndex].value == -1) {\n    \t\talert(\"");
/* 2547 */                             out.print(FormatUtil.getString("am.webclient.cam.configurescreen.alert2"));
/* 2548 */                             out.write("\");\n    \t\treturn;\n    \t}\n\n    \tdocument.CAMJMXAgentDetailsForm.submit();\n    }\n\n\n   }//not hidden\n   else {\n   \tdocument.CAMJMXAgentDetailsForm.submit();\n   }\n}\nfunction cancel() {\n\n");
/*      */                             
/* 2550 */                             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2551 */                             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2552 */                             _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2554 */                             _jspx_th_c_005fif_005f0.setTest("${(!empty isfromresourcepage)}");
/* 2555 */                             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2556 */                             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                               for (;;) {
/* 2558 */                                 out.write("\n    var url = \"/showresource.do?resourceid=");
/* 2559 */                                 out.print(camID);
/* 2560 */                                 out.write("&method=showResourceForResourceID\";\n");
/* 2561 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2562 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2566 */                             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2567 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                             }
/*      */                             
/* 2570 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2571 */                             out.write(10);
/*      */                             
/* 2573 */                             IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2574 */                             _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2575 */                             _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2577 */                             _jspx_th_c_005fif_005f1.setTest("${(empty isfromresourcepage)}");
/* 2578 */                             int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2579 */                             if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                               for (;;) {
/* 2581 */                                 out.write("\n    var url = \"/ShowCAM.do?method=showScreen&screenid=");
/* 2582 */                                 out.print(screenID);
/* 2583 */                                 out.write("&camid=");
/* 2584 */                                 out.print(camID);
/* 2585 */                                 out.write("&haid=");
/* 2586 */                                 out.print(haid);
/* 2587 */                                 out.write(34);
/* 2588 */                                 out.write(59);
/* 2589 */                                 out.write(10);
/* 2590 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2591 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2595 */                             if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2596 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                             }
/*      */                             
/* 2599 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2600 */                             out.write("\n    document.CAMDeleteAttribsForm.action = url;\n    document.CAMDeleteAttribsForm.submit();\n}\n\n\n\n\nfunction deleteAttribs(){\n    var sel = false;\n        for(i=0;i<document.CAMDeleteAttribsForm.elements.length;i++)\n        {\n                if(document.CAMDeleteAttribsForm.elements[i].type==\"checkbox\")\n                {\n                        var name = document.CAMDeleteAttribsForm.elements[i].name;\n                        if(name==\"choosenattributeid\")\n                        {\n                                var value = document.CAMDeleteAttribsForm.elements[i].value;\n                                sel=document.CAMDeleteAttribsForm.elements[i].checked;\n                                if(sel)\n                                {\n                                        break;\n                                }\n                        }\n                }\n        }\n        if(!sel)\n        {\n                alert(\"");
/* 2601 */                             out.print(FormatUtil.getString("am.webclient.cam.configurescreen.alert4"));
/* 2602 */                             out.write("\");\n        }\n\n        else\n\t if(confirm('");
/* 2603 */                             out.print(FormatUtil.getString("am.webclient.cam.configurescreen.alert3"));
/* 2604 */                             out.write("'))\n\t{\n\t\tvar url = \"/CAMDeleteAttributes.do?method=deleteAttributeConfigureForScreen\";\n\t\tdocument.CAMDeleteAttribsForm.action = url;\n\t\tdocument.CAMDeleteAttribsForm.submit();\n\t}\n\n}\n\n\n\n\n\n\nfunction enableReports()\n{     var len = document.CAMDeleteAttribsForm.elements.length;\n                var ml=document.CAMDeleteAttribsForm;\n                var temp='';\n                for (var i = 0; i < len; i++) {\n                    var e = document.CAMDeleteAttribsForm.elements[i];\n                    if (ml.elements[i].name == 'choosenattributeid') {\n\n                        if(ml.elements[i].checked==true)\n                    {\n\n\n                                temp=temp+ml.elements[i].value;\n\n                    }\n                }\n\t\t}\n\t\tif(temp==''){\n\n\t\t\talert('");
/* 2605 */                             out.print(FormatUtil.getString("am.webclient.cam.configurescreen.enable.alert"));
/* 2606 */                             out.write("');\n\n\t\t}else{\n       var url = \"/CAMDeleteAttributes.do?method=enableReportsForScreen\";\n       document.CAMDeleteAttribsForm.action = url;\n       document.CAMDeleteAttribsForm.submit();\n       }\n}\n\nfunction disableReports()\n{var len = document.CAMDeleteAttribsForm.elements.length;\n                var ml=document.CAMDeleteAttribsForm;\n                var temp='';\n                for (var i = 0; i < len; i++) {\n                    var e = document.CAMDeleteAttribsForm.elements[i];\n                    if (ml.elements[i].name == 'choosenattributeid') {\n\n                        if(ml.elements[i].checked==true)\n                    {\n\n\n                                temp=temp+ml.elements[i].value;\n\n                    }\n                }\n\t\t}\n\t\tif(temp==''){\n\n\t\t\talert('");
/* 2607 */                             out.print(FormatUtil.getString("am.webclient.cam.configurescreen.disable.alert"));
/* 2608 */                             out.write("');\n\n\t\t}else{\n       var url = \"/CAMDeleteAttributes.do?method=disableReportsForScreen\";\n       document.CAMDeleteAttribsForm.action = url;\n       document.CAMDeleteAttribsForm.submit();\n         }\n}\n\nfunction bulkedit(){\n\n var len = document.CAMDeleteAttribsForm.elements.length;\n                var ml=document.CAMDeleteAttribsForm;\n                var temp='';\n                for (var i = 0; i < len; i++) {\n                    var e = document.CAMDeleteAttribsForm.elements[i];\n                    if (ml.elements[i].name == 'choosenattributeid') {\n\n                        if(ml.elements[i].checked==true){\n\n                         if(temp=='')\n                        {\n                                temp=temp+ml.elements[i].value;\n                        }\n                        else\n                        {\n                                temp=temp+','+ml.elements[i].value;\n                        }\n                    }\n                }\n\t\t}\n\t\tif(temp==''){\n\n\t\t\talert('");
/* 2609 */                             out.print(FormatUtil.getString("am.webclient.cam.configurescreen.edit.alert"));
/* 2610 */                             out.write("');\n\n\t\t}else{\n                 var action1=\"\";\n                 var d=new Date();\n                action1='/jsp/bulk_edit.jsp?values='+temp+'&screenid=");
/* 2611 */                             out.print(request.getAttribute("screenid"));
/* 2612 */                             out.write("';\n                MM_openBrWindow(action1+'&sid='+d,'secondWindow','resizable=yes,scrollbars=yes,width=450,height=200,top=300,left=300');\n\t\t}\n\n}\n\nfunction fnSelectAll(e)\n{\n\tToggleAll(e,document.CAMDeleteAttribsForm,\"choosenattributeid\")\n}\n</script>\n\n\n\n");
/*      */                             
/* 2614 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2615 */                             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2616 */                             _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2618 */                             _jspx_th_logic_005fnotEmpty_005f0.setName("discoverystatus");
/* 2619 */                             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2620 */                             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                               for (;;) {
/* 2622 */                                 out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n");
/*      */                                 
/* 2624 */                                 NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2625 */                                 _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2626 */                                 _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                 
/* 2628 */                                 _jspx_th_logic_005fnotEmpty_005f1.setName("discoverystatus");
/* 2629 */                                 int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2630 */                                 if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                   for (;;) {
/* 2632 */                                     out.write(10);
/*      */                                     
/*      */ 
/* 2635 */                                     if ((request.getAttribute("type") == null) || ((!request.getAttribute("type").equals("Script Monitor")) && ((request.getAttribute("basetype") == null) || ((request.getAttribute("basetype") != null) && (!request.getAttribute("basetype").equals("Script Monitor")))) && (!request.getAttribute("type").equals("QENGINE")) && (!request.getAttribute("type").equals("Web Service")) && (!request.getAttribute("type").equals("file")) && (!request.getAttribute("type").equals("directory")) && (!request.getAttribute("type").equals("File System Monitor")) && (!request.getAttribute("type").equals("Ping Monitor")) && (!request.getAttribute("type").equals("SAP-CCMS"))))
/*      */                                     {
/*      */ 
/* 2638 */                                       out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n  <tr>\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2639 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2640 */                                       out.write("</span> </td>\n    <td width=\"7%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2641 */                                       out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 2642 */                                       out.write("\n      </span> </td>\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2643 */                                       out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 2644 */                                       out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2645 */                                       out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2646 */                                       out.write("\n </span></td>\n  <tr>\n  ");
/*      */                                       
/* 2648 */                                       int failedNumber = 1;
/*      */                                       
/* 2650 */                                       out.write(10);
/*      */                                       
/* 2652 */                                       IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2653 */                                       _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2654 */                                       _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                       
/* 2656 */                                       _jspx_th_logic_005fiterate_005f0.setName("discoverystatus");
/*      */                                       
/* 2658 */                                       _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                       
/* 2660 */                                       _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */                                       
/* 2662 */                                       _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2663 */                                       int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2664 */                                       if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2665 */                                         ArrayList row = null;
/* 2666 */                                         Integer i = null;
/* 2667 */                                         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2668 */                                           out = _jspx_page_context.pushBody();
/* 2669 */                                           _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2670 */                                           _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                         }
/* 2672 */                                         row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2673 */                                         i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                         for (;;) {
/* 2675 */                                           out.write("\n<tr>\n<td height=\"18\" class=\"bodytext\">");
/* 2676 */                                           out.print(row.get(0));
/* 2677 */                                           out.write("</td>\n<td height=\"18\" class=\"bodytext\">");
/* 2678 */                                           out.print(row.get(1));
/* 2679 */                                           out.write("</td>\n\n    <td height=\"18\" class=\"bodytext\">\n      ");
/*      */                                           
/* 2681 */                                           if ((row.get(2).equals("Success")) || (row.get(2).equals("Present")))
/*      */                                           {
/* 2683 */                                             request.setAttribute("isDiscoverySuccess", "true");
/*      */                                             
/* 2685 */                                             out.write("\n      <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2686 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2687 */                                             out.write("\" align=\"absmiddle\">\n      ");
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 2692 */                                             request.setAttribute("isDiscoverySuccess", "false");
/*      */                                             
/* 2694 */                                             out.write("\n      <img alt=\"");
/* 2695 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.notiniatedimagetip.text"));
/* 2696 */                                             out.write("\" src=\"/images/icon_monitor_failure.gif\" align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                           }
/*      */                                           
/*      */ 
/* 2700 */                                           out.write("\n      <span class=\"bodytextbold\">");
/* 2701 */                                           out.print(FormatUtil.getString(String.valueOf(row.get(2))));
/* 2702 */                                           out.write("</span> </td>\n\n      ");
/*      */                                           
/* 2704 */                                           pageContext.setAttribute("ret_msg", String.valueOf(row.get(3)));
/*      */                                           
/* 2706 */                                           out.write("\n                     ");
/*      */                                           
/* 2708 */                                           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2709 */                                           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2710 */                                           _jspx_th_c_005fif_005f2.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                           
/* 2712 */                                           _jspx_th_c_005fif_005f2.setTest("${ret_msg=='Monitoring Initiated Succesfully'}");
/* 2713 */                                           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2714 */                                           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                             for (;;) {
/* 2716 */                                               out.write("\n                           <td height=\"18\" class=\"bodytext\">");
/* 2717 */                                               out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 2718 */                                               out.write("\n                                 ");
/* 2719 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2720 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 2724 */                                           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2725 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                                           }
/*      */                                           
/* 2728 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2729 */                                           out.write("\n                                       ");
/*      */                                           
/* 2731 */                                           IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2732 */                                           _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2733 */                                           _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                           
/* 2735 */                                           _jspx_th_c_005fif_005f3.setTest("${ret_msg!='Monitoring Initiated Succesfully'}");
/* 2736 */                                           int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2737 */                                           if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                             for (;;) {
/* 2739 */                                               out.write("\n                                             <td height=\"18\" class=\"bodytext\">");
/* 2740 */                                               out.print(row.get(3));
/* 2741 */                                               out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                               
/* 2743 */                                               if ((request.getAttribute("type") != null) && (!request.getAttribute("type").equals("All")) && (!request.getAttribute("type").equals("TELNET")) && (!request.getAttribute("type").equals("SNMP")) && (!request.getAttribute("type").equals("SERVICE")) && (!request.getAttribute("type").equals("JMX1.2-MX4J-RMI")) && (!request.getAttribute("type").equals("JDK1.5")) && (!request.getAttribute("type").equals("RMI")) && (!request.getAttribute("type").equals("PHP")) && (!request.getAttribute("type").equals("MAIL")) && (!request.getAttribute("type").equals("ORACLEDB")) && (!request.getAttribute("type").equals("MYSQLDB")) && (!request.getAttribute("type").equals("MSSQLDB")) && (!request.getAttribute("type").equals("DB2")) && (!request.getAttribute("type").equals("WTA")) && (!request.getAttribute("type").equals("SAP")))
/*      */                                               {
/* 2745 */                                                 if (((String)pageContext.getAttribute("ret_msg")).indexOf(FormatUtil.getString("am.webclient.discovery.host.monitoradded.text")) == -1)
/*      */                                                 {
/* 2747 */                                                   String fWhr = request.getParameter("hideFieldsForIT360");
/* 2748 */                                                   if (fWhr == null)
/*      */                                                   {
/* 2750 */                                                     fWhr = (String)request.getAttribute("hideFieldsForIT360");
/*      */                                                   }
/*      */                                                   
/* 2753 */                                                   if (((fWhr == null) || (!fWhr.equals("true"))) && 
/* 2754 */                                                     (!request.getAttribute("type").equals("SYBASEDB")))
/*      */                                                   {
/* 2756 */                                                     out.write("\n\t\t\t\t\t\t\t\t\t\t\t &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"bodytext\" href=\"javascript:void(0)\" onClick=\"getAllDetailsOfHost()\">");
/* 2757 */                                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.diagnose.link"));
/* 2758 */                                                     out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                   }
/*      */                                                 } }
/* 2761 */                                               if ((request.getAttribute("showSupportMessage") != null) && (failedNumber == 1) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                                               {
/* 2763 */                                                 failedNumber++;
/*      */                                                 
/*      */ 
/* 2766 */                                                 out.write("\n\t\t\t\t\t\t\t\t\t\t<br>");
/* 2767 */                                                 out.print(FormatUtil.getString("am.webclient.discovery.host.support.link", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.talkback.mailid"), com.adventnet.appmanager.util.OEMUtil.getOEMString("product.tollfree.number") }));
/* 2768 */                                                 out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                               }
/* 2770 */                                               out.write("\n                                                   ");
/* 2771 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2772 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 2776 */                                           if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2777 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                           }
/*      */                                           
/* 2780 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2781 */                                           out.write(10);
/* 2782 */                                           out.write(10);
/* 2783 */                                           out.write(10);
/*      */                                           
/*      */ 
/* 2786 */                                           if (row.size() > 4)
/*      */                                           {
/*      */ 
/* 2789 */                                             out.write("<br>\n");
/* 2790 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)row.get(4) }));
/* 2791 */                                             out.write(10);
/*      */                                           }
/*      */                                           
/*      */ 
/* 2795 */                                           out.write("\n</td>\n\n</tr>\n");
/* 2796 */                                           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2797 */                                           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2798 */                                           i = (Integer)_jspx_page_context.findAttribute("i");
/* 2799 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 2802 */                                         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2803 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 2806 */                                       if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2807 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                       }
/*      */                                       
/* 2810 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2811 */                                       out.write("\n</table>\n");
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 2816 */                                       ArrayList al1 = (ArrayList)request.getAttribute("discoverystatus");
/*      */                                       
/* 2818 */                                       out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n<tr>\n");
/* 2819 */                                       String mtype = (String)request.getAttribute("type");
/* 2820 */                                       out.write(10);
/* 2821 */                                       if (mtype.equals("File System Monitor")) {
/* 2822 */                                         out.write("\n  <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2823 */                                         out.print(FormatUtil.getString("File/Directory Name"));
/* 2824 */                                         out.write("</span> </td>\n");
/* 2825 */                                       } else if ((((String)request.getAttribute("type")).equals("Ping Monitor")) || (((String)request.getAttribute("type")).equals("SAP-CCMS"))) {
/* 2826 */                                         out.write("\n\t<td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2827 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2828 */                                         out.write("</span> </td>\n");
/*      */                                       } else {
/* 2830 */                                         out.write("\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2831 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 2832 */                                         out.write("</span> </td>\n");
/*      */                                       }
/* 2834 */                                       out.write("\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2835 */                                       out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 2836 */                                       out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2837 */                                       out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2838 */                                       out.write("</span></td>\n  </tr>\n  <tr>\n   <td height=\"18\" class=\"bodytext\">");
/* 2839 */                                       out.print(al1.get(0));
/* 2840 */                                       out.write("</td>\n   <td height=\"18\" class=\"bodytext\">\n   ");
/*      */                                       
/* 2842 */                                       if (al1.get(1).equals("Success"))
/*      */                                       {
/* 2844 */                                         request.setAttribute("isDiscoverySuccess", "true");
/*      */                                         
/* 2846 */                                         out.write("\n   <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2847 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2848 */                                         out.write("\" align=\"absmiddle\">\n    ");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 2853 */                                         request.setAttribute("isDiscoverySuccess", "false");
/*      */                                         
/*      */ 
/* 2856 */                                         out.write("\n      <img  src=\"/images/icon_monitor_failure.gif\"  align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 2860 */                                       out.write("\n<span class=\"bodytextbold\">");
/* 2861 */                                       out.print(FormatUtil.getString(String.valueOf(al1.get(1))));
/* 2862 */                                       out.write("</span> </td>\n");
/*      */                                       
/* 2864 */                                       if (al1.get(1).equals("Success"))
/*      */                                       {
/* 2866 */                                         boolean isAdminServer = EnterpriseUtil.isAdminServer();
/* 2867 */                                         if (isAdminServer) {
/* 2868 */                                           String masDisplayName = (String)al1.get(3);
/* 2869 */                                           String format = FormatUtil.getString("am.webclient.admin.add.monitor.successfully.configured.text", new String[] { masDisplayName, "", (String)al1.get(2) });
/*      */                                           
/* 2871 */                                           out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\">");
/* 2872 */                                           out.print(format);
/* 2873 */                                           out.write("</td>\n");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 2877 */                                           out.write("\t\t\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"> ");
/* 2878 */                                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2879 */                                           out.write("<br> ");
/* 2880 */                                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)al1.get(2) }));
/* 2881 */                                           out.write("</td>\n");
/*      */                                         }
/*      */                                         
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 2888 */                                         out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"><span class=\"bodytext\">");
/* 2889 */                                         out.print(al1.get(2));
/* 2890 */                                         out.write("</span></td>\n");
/*      */                                       }
/*      */                                       
/*      */ 
/* 2894 */                                       out.write("\n  </tr>\n</table>\n\n");
/*      */                                     }
/*      */                                     
/*      */ 
/* 2898 */                                     out.write(10);
/* 2899 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2900 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2904 */                                 if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2905 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                 }
/*      */                                 
/* 2908 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2909 */                                 out.write(10);
/* 2910 */                                 out.write("<br>\n");
/* 2911 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2912 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2916 */                             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2917 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                             }
/*      */                             
/* 2920 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2921 */                             out.write("\n\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    ");
/*      */                             
/* 2923 */                             Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2924 */                             String aid = request.getParameter("haid");
/* 2925 */                             String haName = null;
/* 2926 */                             if (aid != null)
/*      */                             {
/* 2928 */                               haName = (String)ht.get(aid);
/*      */                             }
/* 2930 */                             String camname = camName;
/*      */                             
/* 2932 */                             out.write("\n    ");
/* 2933 */                             if (_jspx_meth_c_005fcatch_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2935 */                             out.write(32);
/*      */                             
/* 2937 */                             IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2938 */                             _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2939 */                             _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2941 */                             _jspx_th_c_005fif_005f4.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2942 */                             int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2943 */                             if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                               for (;;) {
/* 2945 */                                 out.write("\n    <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2946 */                                 out.print(BreadcrumbUtil.getHomePage(request));
/* 2947 */                                 out.write("\n      &gt; ");
/* 2948 */                                 out.print(BreadcrumbUtil.getHAPage(aid, haName));
/* 2949 */                                 out.write(" &gt;\n");
/*      */                                 
/* 2951 */                                 IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2952 */                                 _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2953 */                                 _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f4);
/*      */                                 
/* 2955 */                                 _jspx_th_c_005fif_005f5.setTest("${('true'!=isfromresourcepage)}");
/* 2956 */                                 int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2957 */                                 if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                   for (;;) {
/* 2959 */                                     out.write("\n      ");
/* 2960 */                                     out.print(BreadcrumbUtil.getCAMDetailsPage(camID, aid, camname));
/* 2961 */                                     out.write(10);
/* 2962 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2963 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2967 */                                 if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2968 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                 }
/*      */                                 
/* 2971 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2972 */                                 out.write(10);
/*      */                                 
/* 2974 */                                 IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2975 */                                 _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2976 */                                 _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f4);
/*      */                                 
/* 2978 */                                 _jspx_th_c_005fif_005f6.setTest("${('true'==isfromresourcepage)}");
/* 2979 */                                 int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2980 */                                 if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                   for (;;) {
/* 2982 */                                     out.write("\n      ");
/* 2983 */                                     out.print(BreadcrumbUtil.getServerDetailsPage(camID, camname));
/* 2984 */                                     out.write(10);
/* 2985 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2986 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2990 */                                 if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2991 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                 }
/*      */                                 
/* 2994 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2995 */                                 out.write("\n\n      &gt; <span class=\"bcactive\"> ");
/* 2996 */                                 out.print(FormatUtil.getString("Custom Attributes"));
/* 2997 */                                 out.write(" </span></td>\n    ");
/* 2998 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2999 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3003 */                             if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3004 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                             }
/*      */                             
/* 3007 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3008 */                             out.write(32);
/*      */                             
/* 3010 */                             IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3011 */                             _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3012 */                             _jspx_th_c_005fif_005f7.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3014 */                             _jspx_th_c_005fif_005f7.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 3015 */                             int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3016 */                             if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                               for (;;) {
/* 3018 */                                 out.write("\n    <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 3019 */                                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 3020 */                                 out.write("\n      &gt;\n\n");
/*      */                                 
/* 3022 */                                 IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3023 */                                 _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3024 */                                 _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f7);
/*      */                                 
/* 3026 */                                 _jspx_th_c_005fif_005f8.setTest("${('true'!=isfromresourcepage)}");
/* 3027 */                                 int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3028 */                                 if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                   for (;;) {
/* 3030 */                                     out.write("\n      ");
/* 3031 */                                     out.print(BreadcrumbUtil.getMonitorResourceTypes("Custom-Application"));
/* 3032 */                                     out.write("\n      &gt; ");
/* 3033 */                                     out.print(BreadcrumbUtil.getCAMDetailsPage(camID, aid, camname));
/* 3034 */                                     out.write(10);
/* 3035 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3036 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3040 */                                 if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3041 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                 }
/*      */                                 
/* 3044 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3045 */                                 out.write(10);
/* 3046 */                                 out.write(10);
/* 3047 */                                 out.write(10);
/*      */                                 
/* 3049 */                                 IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3050 */                                 _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3051 */                                 _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f7);
/*      */                                 
/* 3053 */                                 _jspx_th_c_005fif_005f9.setTest("${('true'==isfromresourcepage)}");
/* 3054 */                                 int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3055 */                                 if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                   for (;;) {
/* 3057 */                                     out.write(10);
/* 3058 */                                     out.write(9);
/* 3059 */                                     out.print(BreadcrumbUtil.getMonitorResourceTypes((String)resourceInfoFromResourcePage.get("TYPE")));
/* 3060 */                                     out.write("\n\t&gt;\n\n\t");
/* 3061 */                                     out.print(BreadcrumbUtil.getServerDetailsPage(camID, (String)resourceInfoFromResourcePage.get("DISPLAYNAME")));
/* 3062 */                                     out.write(10);
/* 3063 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3064 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3068 */                                 if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3069 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                 }
/*      */                                 
/* 3072 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3073 */                                 out.write("\n&gt; <span class=\"bcactive\"> ");
/* 3074 */                                 out.print(FormatUtil.getString("am.webclient.cam.showmbeans.addattributes"));
/* 3075 */                                 out.write(" </span>\n      </td>\n    ");
/* 3076 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3077 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3081 */                             if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3082 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                             }
/*      */                             
/* 3085 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3086 */                             out.write(" </tr>\n  <tr>\n    <td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n  </tr>\n   <tr>\n\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n   </tr>\n\n</table>\n<div id=\"edit\" style=\"DISPLAY:none\">\n\n");
/*      */                             
/* 3088 */                             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 3089 */                             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 3090 */                             _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3092 */                             _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*      */                             
/* 3094 */                             _jspx_th_html_005fform_005f0.setMethod("get");
/*      */                             
/* 3096 */                             _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 3097 */                             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 3098 */                             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                               for (;;) {
/* 3100 */                                 out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n<td height=\"28\"   class=\"tableheading\">");
/* 3101 */                                 out.print(FormatUtil.getString("am.webclient.common.configurationdetails.text"));
/* 3102 */                                 out.write("\n\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 3103 */                                 out.print(request.getParameter("name"));
/* 3104 */                                 out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 3105 */                                 out.print(request.getParameter("haid"));
/* 3106 */                                 out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 3107 */                                 out.print(request.getParameter("type"));
/* 3108 */                                 out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 3109 */                                 out.print(request.getParameter("type"));
/* 3110 */                                 out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"configureJMX\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 3111 */                                 out.print(camID);
/* 3112 */                                 out.write("\")%>\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 3113 */                                 out.print(request.getParameter("resourcename"));
/* 3114 */                                 out.write("\">\n<input type=\"hidden\" name=\"moname\" value=\"");
/* 3115 */                                 out.print(request.getParameter("moname"));
/* 3116 */                                 out.write("\">\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\">\n<span class=\"bodytextboldwhiteun\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n");
/* 3117 */                                 out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 3118 */                                 out.write("</span>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n<tr>\n    <td width=\"20%\" height=\"32\" valign=='top' class=\"bodytext\"> ");
/* 3119 */                                 out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 3120 */                                 out.write("\n</td>\n    <td width=\"80%\" class=\"bodytext\"> <textarea name=\"camname\" cols=\"38\" rows=\"1\" class=\"formtextarea\">");
/* 3121 */                                 out.print(camName);
/* 3122 */                                 out.write(" </textarea>\n</td>\n</tr>\n\n<tr>\n    <td valign='top'  class=\"bodytext\"> ");
/* 3123 */                                 out.print(FormatUtil.getString("Description"));
/* 3124 */                                 out.write("</td>\n    <td  class=\"bodytext\"> <textarea name=\"camdesc\" cols=\"38\" rows=\"3\" class=\"formtextarea\" >");
/* 3125 */                                 out.print(camDescription);
/* 3126 */                                 out.write("</textarea>\n    </td>\n  </tr>\n</table>\n");
/*      */                                 
/* 3128 */                                 String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/*      */                                 
/* 3130 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"20%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"80%\" class=\"tablebottom\" >\n<input name=\"addcustomapp\" type=\"button\" class=\"buttons\" \" value=\"");
/* 3131 */                                 out.print(FormatUtil.getString("am.webclient.common.startmonitoring.text"));
/* 3132 */                                 out.write("\" onClick=\"validateAndSubmit()\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons\" value=\"");
/* 3133 */                                 out.print(cancel);
/* 3134 */                                 out.write("\" onClick=\"javascript:toggleDiv('edit')\"/>\n     </td>\n  </tr>\n</table><br><br>\n");
/* 3135 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3136 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3140 */                             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3141 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                             }
/*      */                             
/* 3144 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3145 */                             out.write("\n</div>\n\n\n");
/*      */                             
/* 3147 */                             FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 3148 */                             _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 3149 */                             _jspx_th_html_005fform_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3151 */                             _jspx_th_html_005fform_005f1.setAction("/CAMDeleteAttributes");
/*      */                             
/* 3153 */                             _jspx_th_html_005fform_005f1.setMethod("POST");
/*      */                             
/* 3155 */                             _jspx_th_html_005fform_005f1.setStyle("display:inline");
/* 3156 */                             int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 3157 */                             if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                               for (;;) {
/* 3159 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td colspan=\"7\" height=\"25\" class=\"tableheadingbborder\">");
/* 3160 */                                 out.print(FormatUtil.getString("am.webclient.cam.customize.link"));
/* 3161 */                                 out.write(32);
/* 3162 */                                 out.write(34);
/* 3163 */                                 out.print(getTrimmedText(FormatUtil.getString((String)screenInfo.get(1)), 30));
/* 3164 */                                 out.write("\"  </td>\n  </tr>\n");
/*      */                                 
/* 3166 */                                 boolean attribsPresent = screenConfigList.size() > 0;
/* 3167 */                                 if (attribsPresent)
/*      */                                 {
/*      */ 
/* 3170 */                                   out.write("<tr>\n    <td width=\"1%\" class=\"columnheading\"><input type=\"checkbox\" name='selectallckb' onClick=\"javascript:fnSelectAll(this)\"/>\n    </td>\n    <td width=\"12%\" class=\"columnheading\"> ");
/* 3171 */                                   out.print(FormatUtil.getString("am.webclient.camscreen.attribute.text"));
/* 3172 */                                   out.write(" </td>\n    ");
/*      */                                   
/* 3174 */                                   if ((resourceInfoFromResourcePage != null) && (resourceInfoFromResourcePage.get("TYPE") != null) && (resourceInfoFromResourcePage.get("TYPE").equals("SAP-CCMS")))
/*      */                                   {
/*      */ 
/* 3177 */                                     out.write("\n    <td width=\"80%\" class=\"columnheading\" colspan=\"4\" title=\"");
/* 3178 */                                     out.print(FormatUtil.getString("am.webclient.cam.groupname.mouseover"));
/* 3179 */                                     out.write(34);
/* 3180 */                                     out.write(62);
/* 3181 */                                     out.print(FormatUtil.getString("am.webclient.camscreen.groupname.text"));
/* 3182 */                                     out.write("</td>\n    ");
/*      */                                   } else {
/* 3184 */                                     out.write("\n    <td width=\"25%\" class=\"columnheading\" title=\"");
/* 3185 */                                     out.print(FormatUtil.getString("am.webclient.cam.groupname.mouseover"));
/* 3186 */                                     out.write(34);
/* 3187 */                                     out.write(62);
/* 3188 */                                     out.print(FormatUtil.getString("am.webclient.camscreen.groupname.text"));
/* 3189 */                                     out.write("</td>\n    <td width=\"25%\" class=\"columnheading\">&nbsp;&nbsp;");
/* 3190 */                                     out.print(FormatUtil.getString("am.webclient.cam.choosedomain.agent.text"));
/* 3191 */                                     out.write(" </td>\n    <td width=\"7%\" class=\"columnheading\">");
/* 3192 */                                     out.print(FormatUtil.getString("am.webclient.cam.choosedomain.port.text"));
/* 3193 */                                     out.write(" </td>\n    <td width=\"7%\" class=\"columnheading\"> ");
/* 3194 */                                     out.print(FormatUtil.getString("am.webclient.cam.choosedomain.type.text"));
/* 3195 */                                     out.write("</td>\n    ");
/*      */                                   }
/* 3197 */                                   out.write("\n    <td valign=\"center\" width=\"7%\" class=\"columnheading\" align=\"center\"> ");
/* 3198 */                                   out.print(FormatUtil.getString("am.webclient.camscreen.reports.text"));
/* 3199 */                                   out.write("</td>\n  </tr>\n  ");
/*      */                                   
/* 3201 */                                   List row = null;
/* 3202 */                                   List tableIDs = new ArrayList();
/* 3203 */                                   String moName = null;
/* 3204 */                                   String className = "whitegrayborder";
/* 3205 */                                   for (int i = 0; i < screenConfigList.size(); i++)
/*      */                                   {
/* 3207 */                                     row = (List)screenConfigList.get(i);
/* 3208 */                                     moName = (String)row.get(2);
/* 3209 */                                     Object tableID = row.get(7);
/* 3210 */                                     if (i % 2 == 0) {
/* 3211 */                                       className = "whitegrayborder";
/*      */                                     } else {
/* 3213 */                                       className = "yellowgrayborder";
/*      */                                     }
/*      */                                     
/*      */ 
/* 3217 */                                     out.write("\n<input type=\"hidden\" name=\"camid\" value=\"");
/* 3218 */                                     out.print(camID);
/* 3219 */                                     out.write("\"/>\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 3220 */                                     out.print(haid);
/* 3221 */                                     out.write("\"/>\n<input type=\"hidden\" name=\"screenid\" value=\"");
/* 3222 */                                     out.print(screenID);
/* 3223 */                                     out.write("\"/>\n<input type=\"hidden\" name=\"isfromresourcepage\" value=\"");
/* 3224 */                                     out.print(request.getAttribute("isfromresourcepage"));
/* 3225 */                                     out.write("\"/>\n  <tr>\n    <td width=\"1%\" class=\"");
/* 3226 */                                     out.print(className);
/* 3227 */                                     out.write("\"><input type=\"checkbox\" name=\"choosenattributeid\" onclick=\"javascript:document.CAMDeleteAttribsForm.selectallckb.checked=false;\" value=\"");
/* 3228 */                                     out.print(row.get(0));
/* 3229 */                                     out.write("\"/>\n    </td>\n    <td width=\"10%\" class=\"");
/* 3230 */                                     out.print(className);
/* 3231 */                                     out.write("\" title=\"");
/* 3232 */                                     out.print((String)row.get(1) + " (" + row.get(10) + ") ");
/* 3233 */                                     out.write(34);
/* 3234 */                                     out.write(62);
/* 3235 */                                     out.write(32);
/* 3236 */                                     out.print(getTrimmedText((String)row.get(1), 30));
/* 3237 */                                     out.write("\n    ");
/*      */                                     
/*      */ 
/* 3240 */                                     String val = "";
/* 3241 */                                     if (tableID != null)
/*      */                                     {
/* 3243 */                                       if (tableIDs.indexOf(tableID) == -1)
/*      */                                       {
/* 3245 */                                         tableIDs.add(tableID);
/* 3246 */                                         tableIDsAsString = tableIDsAsString + tableID + ",";
/*      */                                       }
/* 3248 */                                       String[] arr = (String[])tableDetails.get("" + tableID);
/* 3249 */                                       if (arr != null)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/* 3254 */                                         val = arr[1];
/*      */                                         
/* 3256 */                                         out.write("\n\n    ");
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/* 3263 */                                     out.write("\n    </td>\n    ");
/*      */                                     
/*      */ 
/* 3266 */                                     if (!row.get(3).equals("SNMP"))
/*      */                                     {
/*      */ 
/* 3269 */                                       val = (String)row.get(8);
/*      */                                     }
/* 3271 */                                     if ((resourceInfoFromResourcePage != null) && (resourceInfoFromResourcePage.get("TYPE") != null) && (resourceInfoFromResourcePage.get("TYPE").equals("SAP-CCMS")))
/*      */                                     {
/*      */ 
/* 3274 */                                       out.write("\n    <td colspan=\"4\" class=\"");
/* 3275 */                                       out.print(className);
/* 3276 */                                       out.write("\" title=\"");
/* 3277 */                                       out.print(val);
/* 3278 */                                       out.write(34);
/* 3279 */                                       out.write(62);
/* 3280 */                                       out.print(getTrimmedText(val, 90));
/* 3281 */                                       out.write("&nbsp;</td>\n    ");
/*      */                                     }
/*      */                                     else {
/* 3284 */                                       String hostname = "";String port = "-";
/* 3285 */                                       hostname = moName;
/*      */                                       try {
/* 3287 */                                         ResourceName rn = new ResourceName(moName);
/* 3288 */                                         hostname = rn.getHostName() + "";
/* 3289 */                                         port = rn.getPort() + "";
/*      */                                       } catch (Exception e) {}
/* 3291 */                                       String val1 = val;
/* 3292 */                                       if (val1.contains("\""))
/*      */                                       {
/* 3294 */                                         val1 = val1.replaceAll("\"", "&quot;");
/*      */                                       }
/*      */                                       
/* 3297 */                                       out.write("\n    <td width=\"25%\" class=\"");
/* 3298 */                                       out.print(className);
/* 3299 */                                       out.write("\" title=\"");
/* 3300 */                                       out.print(val1);
/* 3301 */                                       out.write(34);
/* 3302 */                                       out.write(62);
/* 3303 */                                       out.print(getTrimmedText(val, 30));
/* 3304 */                                       out.write("&nbsp;</td>\n    <td width=\"25%\" class=\"");
/* 3305 */                                       out.print(className);
/* 3306 */                                       out.write("\" title=\"");
/* 3307 */                                       out.print(hostname);
/* 3308 */                                       out.write("\">&nbsp;&nbsp;");
/* 3309 */                                       out.print(getTrimmedText(hostname, 25));
/* 3310 */                                       out.write(" </td>\n    <td width=\"15%\" class=\"");
/* 3311 */                                       out.print(className);
/* 3312 */                                       out.write(34);
/* 3313 */                                       out.write(62);
/* 3314 */                                       out.write(32);
/* 3315 */                                       out.print(port);
/* 3316 */                                       out.write(" </td>\n    <td width=\"5%\" class=\"");
/* 3317 */                                       out.print(className);
/* 3318 */                                       out.write(34);
/* 3319 */                                       out.write(62);
/* 3320 */                                       out.write(32);
/* 3321 */                                       out.print(row.get(3));
/* 3322 */                                       out.write("</td>\n    ");
/*      */                                     }
/*      */                                     
/* 3325 */                                     String reportsEnabled = "-";
/* 3326 */                                     String reportsStatus = FormatUtil.getString("am.webclient.script.reportsdisabled");
/*      */                                     
/* 3328 */                                     if (row.get(9).equals("0"))
/*      */                                     {
/* 3330 */                                       if (reportsData.indexOf(row.get(0)) == -1)
/*      */                                       {
/* 3332 */                                         reportsEnabled = "<img alt=\"Reports Disabled\" title='" + reportsStatus + "' src=\"../images/cam_report_disabled.gif\" border=\"0\">";
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 3336 */                                         reportsEnabled = "<img alt=\"Reports Enabled\" title=\"Reports Enabled\" src=\"../images/cam_report_enabled.gif\" border=\"0\">";
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/* 3341 */                                     out.write("\n    <td align=\"center\" width=\"20%\" class=\"");
/* 3342 */                                     out.print(className);
/* 3343 */                                     out.write(34);
/* 3344 */                                     out.write(62);
/* 3345 */                                     out.write(32);
/* 3346 */                                     out.print(reportsEnabled);
/* 3347 */                                     out.write("</td>\n  </tr>\n  ");
/*      */                                   }
/*      */                                   
/*      */ 
/*      */ 
/* 3352 */                                   out.write("\n  <tr>\n    <td  class=\"tablebottom\" colspan=\"7\" ><a class=\"staticlinks\" href=\"javascript:deleteAttribs()\">");
/* 3353 */                                   out.print(FormatUtil.getString("am.webclient.cam.delete.link"));
/* 3354 */                                   out.write("</a>\n      &nbsp;<span class=\"bodytext\">| </span> &nbsp; <a class=\"staticlinks\" href=\"javascript:enableReports()\">\n      ");
/* 3355 */                                   out.print(FormatUtil.getString("am.webclient.cam.enablereports.link"));
/* 3356 */                                   out.write("</a> &nbsp; <span class=\"bodytext\">| </span> &nbsp; <a class=\"staticlinks\" href=\"javascript:disableReports()\">\n      ");
/* 3357 */                                   out.print(FormatUtil.getString("am.webclient.cam.disablereports.link"));
/* 3358 */                                   out.write("</a>\n&nbsp; ");
/* 3359 */                                   if ((resourceInfoFromResourcePage == null) || (resourceInfoFromResourcePage.get("TYPE") == null) || (!resourceInfoFromResourcePage.get("TYPE").equals("SAP-CCMS"))) {
/* 3360 */                                     out.write("<span class=\"bodytext\">| </span> &nbsp; <a class=\"staticlinks\" href=\"javascript:bulkedit()\">   ");
/* 3361 */                                     out.print(FormatUtil.getString("Bulk Edit"));
/* 3362 */                                     out.write(" </a> ");
/*      */                                   }
/* 3364 */                                   out.write("\n\n </tr>\n  ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3370 */                                   out.write("\n <tr>\n    <td width=\"13%\" height=\"24\" colspan=\"7\">\n    ");
/*      */                                   
/* 3372 */                                   ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3373 */                                   _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 3374 */                                   _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_html_005fform_005f1);
/* 3375 */                                   int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 3376 */                                   if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                     for (;;) {
/* 3378 */                                       out.write("\n    ");
/*      */                                       
/* 3380 */                                       WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3381 */                                       _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3382 */                                       _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                       
/* 3384 */                                       _jspx_th_c_005fwhen_005f0.setTest("${('true'==isfromresourcepage)}");
/* 3385 */                                       int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3386 */                                       if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                         for (;;) {
/* 3388 */                                           out.write("\n    <SPAN class='bodytext'>&nbsp;&nbsp;");
/* 3389 */                                           out.print(FormatUtil.getString("am.webclient.cam.configurescreen.noattributes"));
/* 3390 */                                           out.write(" </SPAN>\n    ");
/* 3391 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3392 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3396 */                                       if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3397 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                       }
/*      */                                       
/* 3400 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3401 */                                       out.write("\n    ");
/*      */                                       
/* 3403 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3404 */                                       _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3405 */                                       _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 3406 */                                       int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3407 */                                       if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                         for (;;) {
/* 3409 */                                           out.write("\n    <SPAN class='bodytext'>&nbsp;&nbsp;");
/* 3410 */                                           out.print(FormatUtil.getString("am.webclient.cam.configurescreen.addmonitor"));
/* 3411 */                                           out.write(" </SPAN>\n    ");
/* 3412 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3413 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3417 */                                       if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3418 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                       }
/*      */                                       
/* 3421 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3422 */                                       out.write("\n    ");
/* 3423 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3424 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3428 */                                   if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3429 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                                   }
/*      */                                   
/* 3432 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3433 */                                   out.write("\n    </td>\n   </tr>\n  ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3437 */                                 out.write("\n</table>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n</table>\n<input type=\"hidden\" name=\"tableids\" value=\"");
/* 3438 */                                 out.print(tableIDsAsString);
/* 3439 */                                 out.write("\"/>\n");
/* 3440 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 3441 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3445 */                             if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 3446 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */                             }
/*      */                             
/* 3449 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 3450 */                             out.write(10);
/* 3451 */                             out.write(10);
/*      */                             
/* 3453 */                             String actionurl = "/CAMChooseDomains.do?method=chooseDomains";
/* 3454 */                             if ((resourceInfoFromResourcePage != null) && (resourceInfoFromResourcePage.get("TYPE") != null) && (resourceInfoFromResourcePage.get("TYPE").equals("SAP-CCMS"))) {
/* 3455 */                               actionurl = "/sap.do?method=showCCMSMonitors";
/*      */                             }
/* 3457 */                             out.write(10);
/*      */                             
/* 3459 */                             FormTag _jspx_th_html_005fform_005f2 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 3460 */                             _jspx_th_html_005fform_005f2.setPageContext(_jspx_page_context);
/* 3461 */                             _jspx_th_html_005fform_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 3463 */                             _jspx_th_html_005fform_005f2.setAction(actionurl);
/*      */                             
/* 3465 */                             _jspx_th_html_005fform_005f2.setStyle("display:inline");
/* 3466 */                             int _jspx_eval_html_005fform_005f2 = _jspx_th_html_005fform_005f2.doStartTag();
/* 3467 */                             if (_jspx_eval_html_005fform_005f2 != 0) {
/*      */                               for (;;) {
/* 3469 */                                 out.write(10);
/*      */                                 
/* 3471 */                                 IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3472 */                                 _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3473 */                                 _jspx_th_c_005fif_005f10.setParent(_jspx_th_html_005fform_005f2);
/*      */                                 
/* 3475 */                                 _jspx_th_c_005fif_005f10.setTest("${('true'==isfromresourcepage)}");
/* 3476 */                                 int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3477 */                                 if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                   for (;;) {
/* 3479 */                                     out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"20%\" height=\"25\"><center>\n    ");
/* 3480 */                                     if ((resourceInfoFromResourcePage != null) && (resourceInfoFromResourcePage.get("TYPE") != null) && (resourceInfoFromResourcePage.get("TYPE").equals("SAP-CCMS"))) {
/* 3481 */                                       out.write("\n    <input name=\"button\" type=\"submit\" class=\"buttons\" value=\"");
/* 3482 */                                       out.print(FormatUtil.getString("am.webclient.cam.showmbeans.addattributes"));
/* 3483 */                                       out.write("\"/>\n    ");
/*      */                                     } else {
/* 3485 */                                       out.write("\n    <input name=\"button\" type=\"button\" class=\"buttons\" onClick=\"javascript:showDomains();\" value=\"");
/* 3486 */                                       out.print(FormatUtil.getString("am.webclient.cam.showmbeans.addattributes"));
/* 3487 */                                       out.write("\"/>\n    ");
/*      */                                     }
/* 3489 */                                     out.write("\n&nbsp;&nbsp;\n        <input type=\"button\" value=\"");
/* 3490 */                                     out.print(FormatUtil.getString("am.webclient.camscreen.back.button"));
/* 3491 */                                     out.write("\" class='buttons' onClick=\"javascript:cancel();\" />\n</center></td>\n  </tr>\n</table>\n\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 3492 */                                     out.print(camID);
/* 3493 */                                     out.write(34);
/* 3494 */                                     out.write(62);
/* 3495 */                                     out.write(10);
/* 3496 */                                     if ((resourceInfoFromResourcePage == null) || (resourceInfoFromResourcePage.get("TYPE") == null) || (!resourceInfoFromResourcePage.get("TYPE").equals("SAP-CCMS"))) {
/* 3497 */                                       out.write("\n<input type=\"hidden\" name=\"method\" value=\"chooseDomains\">\n");
/*      */                                     }
/* 3499 */                                     out.write(10);
/* 3500 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3501 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3505 */                                 if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3506 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                 }
/*      */                                 
/* 3509 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3510 */                                 out.write("\n\n  <input type=\"hidden\" name=\"camid\" value=\"");
/* 3511 */                                 out.print(camID);
/* 3512 */                                 out.write("\"/>\n  <input type=\"hidden\" name=\"haid\" value=\"");
/* 3513 */                                 out.print(haid);
/* 3514 */                                 out.write("\"/>\n  <input type=\"hidden\" name=\"screenid\" value=\"");
/* 3515 */                                 out.print(screenID);
/* 3516 */                                 out.write("\"/>\n  <input type=\"hidden\" name=\"isfromresourcepage\" value=\"");
/* 3517 */                                 out.print(request.getAttribute("isfromresourcepage"));
/* 3518 */                                 out.write("\"/>\n\n\n");
/*      */                                 
/* 3520 */                                 IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3521 */                                 _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3522 */                                 _jspx_th_c_005fif_005f11.setParent(_jspx_th_html_005fform_005f2);
/*      */                                 
/* 3524 */                                 _jspx_th_c_005fif_005f11.setTest("${('true'!=isfromresourcepage)}");
/* 3525 */                                 int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3526 */                                 if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                                   for (;;) {
/* 3528 */                                     out.write("\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"20%\" height=\"25\" class=\"tableheading\">");
/* 3529 */                                     out.print(FormatUtil.getString("am.webclient.camscreen.addattributes.heading"));
/* 3530 */                                     out.write(" :</td>\n  </tr>\n</table>\n\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\" width=\"99%\">\n  <!-- table for the add new option outermost -->\n  <tr>\n    <td width=\"17%\" height=\"37\"><SPAN class='bodytext'>&nbsp;");
/* 3531 */                                     out.print(FormatUtil.getString("am.webclient.camscreen.monitor.text"));
/* 3532 */                                     out.write(" : </SPAN></td>\n    <td width=\"11%\" align=\"center\" valign=\"middle\">\n      <select name=\"resourceid\" class=\"bodytext\">\n        ");
/*      */                                     
/* 3534 */                                     IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3535 */                                     _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 3536 */                                     _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_c_005fif_005f11);
/*      */                                     
/* 3538 */                                     _jspx_th_logic_005fiterate_005f1.setName("datacollectableagents");
/*      */                                     
/* 3540 */                                     _jspx_th_logic_005fiterate_005f1.setId("agent");
/*      */                                     
/* 3542 */                                     _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                                     
/* 3544 */                                     _jspx_th_logic_005fiterate_005f1.setType("java.util.List");
/* 3545 */                                     int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 3546 */                                     if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 3547 */                                       List agent = null;
/* 3548 */                                       Integer j = null;
/* 3549 */                                       if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3550 */                                         out = _jspx_page_context.pushBody();
/* 3551 */                                         _jspx_th_logic_005fiterate_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3552 */                                         _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                       }
/* 3554 */                                       agent = (List)_jspx_page_context.findAttribute("agent");
/* 3555 */                                       j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                       for (;;) {
/* 3557 */                                         out.write("\n        <option value=\"");
/* 3558 */                                         out.print(agent.get(0));
/* 3559 */                                         out.write("\" selected>");
/* 3560 */                                         out.print(getTrimmedText((String)agent.get(1), 45));
/* 3561 */                                         out.write("</option>\n        ");
/* 3562 */                                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 3563 */                                         agent = (List)_jspx_page_context.findAttribute("agent");
/* 3564 */                                         j = (Integer)_jspx_page_context.findAttribute("j");
/* 3565 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3568 */                                       if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3569 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3572 */                                     if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 3573 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                     }
/*      */                                     
/* 3576 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 3577 */                                     out.write("\n\n        ");
/*      */                                     
/* 3579 */                                     if (((List)request.getAttribute("datacollectableagents")).size() == 0) { out.println("<option value='-1'>" + FormatUtil.getString("am.monitortab.nomonitors.text"));
/*      */                                     }
/* 3581 */                                     out.write("\n\n        </select>\n    <td width=\"74%\" align=\"left\"><span class=\"bodytext\">&nbsp;&nbsp;<a href='/adminAction.do?method=reloadHostDiscoveryForm&type=JMX1.2-MX4J-RMI&port=1099&redirectto=");
/* 3582 */                                     out.print(URLEncoder.encode("/ShowCAM.do?method=configureScreen&screenid=" + screenID + "&camid=" + camID + "&haid=" + haid));
/* 3583 */                                     out.write("' class='links'>");
/* 3584 */                                     out.print(FormatUtil.getString("am.webclient.camscreen.addmonitor.text"));
/* 3585 */                                     out.write("</a>\n    ");
/* 3586 */                                     out.print(FormatUtil.getString("am.webclient.camscreen.notpresent.text"));
/* 3587 */                                     out.write(" </span></tr>\n  <tr>\n    <td class=\"tablebottom\" colspan='3'> <center>\n        <input name=\"button\" type=\"button\" class=\"buttons\" onClick=\"javascript:showDomains();\" value=\"");
/* 3588 */                                     out.print(FormatUtil.getString("am.webclient.camscreen.add.button"));
/* 3589 */                                     out.write("\"/>\n        &nbsp;&nbsp;\n        <input type=\"button\" value=\"");
/* 3590 */                                     out.print(FormatUtil.getString("am.webclient.camscreen.back.button"));
/* 3591 */                                     out.write("\" class='buttons' onClick=\"javascript:cancel();\" />\n      </center></td>\n  </tr>\n</table>\n");
/* 3592 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3593 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3597 */                                 if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3598 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                                 }
/*      */                                 
/* 3601 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3602 */                                 out.write(10);
/* 3603 */                                 out.write(10);
/* 3604 */                                 out.write(10);
/* 3605 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f2.doAfterBody();
/* 3606 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3610 */                             if (_jspx_th_html_005fform_005f2.doEndTag() == 5) {
/* 3611 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f2); return;
/*      */                             }
/*      */                             
/* 3614 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f2);
/* 3615 */                             out.write(10);
/* 3616 */                             out.write(10);
/* 3617 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3618 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3621 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3622 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3625 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3626 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 3629 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3630 */                         out.write(10);
/* 3631 */                         out.write(32);
/* 3632 */                         if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 3634 */                         out.write(10);
/* 3635 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3636 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3640 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3641 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 3644 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3645 */                       out.write(10);
/*      */                     }
/* 3647 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3648 */         out = _jspx_out;
/* 3649 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3650 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3651 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3654 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3660 */     PageContext pageContext = _jspx_page_context;
/* 3661 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3663 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3664 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3665 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3667 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3669 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 3670 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3671 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3672 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3673 */       return true;
/*      */     }
/* 3675 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3676 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3681 */     PageContext pageContext = _jspx_page_context;
/* 3682 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3684 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3685 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3686 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3688 */     _jspx_th_tiles_005fput_005f2.setName("ServerLeftArea");
/*      */     
/* 3690 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/cam_leftlinksarea.jsp");
/* 3691 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3692 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3693 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3694 */       return true;
/*      */     }
/* 3696 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3697 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3702 */     PageContext pageContext = _jspx_page_context;
/* 3703 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3705 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 3706 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 3707 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3709 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 3710 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 3712 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 3713 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 3715 */           out.write(32);
/* 3716 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 3717 */             return true;
/* 3718 */           out.write("\n    ");
/* 3719 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 3720 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3724 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 3725 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3728 */         int tmp184_183 = 0; int[] tmp184_181 = _jspx_push_body_count_c_005fcatch_005f0; int tmp186_185 = tmp184_181[tmp184_183];tmp184_181[tmp184_183] = (tmp186_185 - 1); if (tmp186_185 <= 0) break;
/* 3729 */         out = _jspx_page_context.popBody(); }
/* 3730 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3732 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 3733 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 3735 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 3740 */     PageContext pageContext = _jspx_page_context;
/* 3741 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3743 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 3744 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 3745 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 3747 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 3749 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 3750 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 3751 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 3752 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3753 */       return true;
/*      */     }
/* 3755 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3756 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3761 */     PageContext pageContext = _jspx_page_context;
/* 3762 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3764 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3765 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3766 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3768 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 3770 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 3771 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3772 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3773 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3774 */       return true;
/*      */     }
/* 3776 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3777 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\cam_005fconfigurescreen_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */