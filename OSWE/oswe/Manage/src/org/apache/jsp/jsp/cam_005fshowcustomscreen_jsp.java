/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*      */ import com.adventnet.appmanager.cam.beans.CAMGraphs;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
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
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class cam_005fshowcustomscreen_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  855 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  856 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  859 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  860 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  861 */       set = AMConnectionPool.executeQueryStmt(query);
/*  862 */       if (set.next())
/*      */       {
/*  864 */         String helpLink = set.getString("LINK");
/*  865 */         DBUtil.searchLinks.put(key, helpLink);
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
/*  926 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
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
/* 1474 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
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
/* 1824 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1826 */               if (maxCol != null)
/* 1827 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1829 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1824 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
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
/* 1985 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
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
/*      */ 
/*      */   public String addBreakAt(String str, int len)
/*      */   {
/* 2181 */     if (len == 0) return str;
/* 2182 */     String temp = str;
/* 2183 */     StringBuffer ret = new StringBuffer("");
/* 2184 */     while (temp.length() > len)
/*      */     {
/* 2186 */       ret.append(temp.substring(0, len));
/* 2187 */       ret.append("<br>");
/* 2188 */       temp = temp.substring(len);
/*      */     }
/* 2190 */     ret.append(temp);
/* 2191 */     return ret.toString();
/*      */   }
/*      */   
/* 2194 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2200 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2201 */   static { _jspx_dependants.put("/jsp/includes/cam_screen.jspf", Long.valueOf(1473429417000L));
/* 2202 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2223 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2227 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2241 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2245 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2247 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2248 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2249 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2250 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2251 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2253 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2254 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2256 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2257 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2264 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2267 */     JspWriter out = null;
/* 2268 */     Object page = this;
/* 2269 */     JspWriter _jspx_out = null;
/* 2270 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2274 */       response.setContentType("text/html;charset=UTF-8");
/* 2275 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2277 */       _jspx_page_context = pageContext;
/* 2278 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2279 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2280 */       session = pageContext.getSession();
/* 2281 */       out = pageContext.getOut();
/* 2282 */       _jspx_out = out;
/*      */       
/* 2284 */       out.write("<!DOCTYPE html>\n");
/* 2285 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n    <!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n  \n");
/* 2286 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2288 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2289 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2290 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2292 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2294 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2296 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2298 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2299 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2300 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2301 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2304 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2305 */         String available = null;
/* 2306 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2307 */         out.write(10);
/*      */         
/* 2309 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2310 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2311 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2313 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2315 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2317 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2319 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2320 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2321 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2322 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2325 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2326 */           String unavailable = null;
/* 2327 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2328 */           out.write(10);
/*      */           
/* 2330 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2331 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2332 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2334 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2336 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2338 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2340 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2341 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2342 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2343 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2346 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2347 */             String unmanaged = null;
/* 2348 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2349 */             out.write(10);
/*      */             
/* 2351 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2352 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2353 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2355 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2357 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2359 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2361 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2362 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2363 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2364 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2367 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2368 */               String scheduled = null;
/* 2369 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2370 */               out.write(10);
/*      */               
/* 2372 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2373 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2374 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2376 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2378 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2380 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2382 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2383 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2384 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2385 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2388 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2389 */                 String critical = null;
/* 2390 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2391 */                 out.write(10);
/*      */                 
/* 2393 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2394 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2395 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2397 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2399 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2401 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2403 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2404 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2405 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2406 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2409 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2410 */                   String clear = null;
/* 2411 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2412 */                   out.write(10);
/*      */                   
/* 2414 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2415 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2416 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2418 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2420 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2422 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2424 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2425 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2426 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2427 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2430 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2431 */                     String warning = null;
/* 2432 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2433 */                     out.write(10);
/* 2434 */                     out.write(10);
/*      */                     
/* 2436 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2437 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2439 */                     out.write(10);
/* 2440 */                     out.write(10);
/* 2441 */                     out.write(10);
/* 2442 */                     out.write("\n<link href=\"/images/");
/* 2443 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2445 */                     out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGAUGE =\"javascript\" SRC=\"../template/appmanager.js\" ></SCRIPT>\n");
/*      */                     
/* 2447 */                     request.setAttribute("HelpKey", "Create New CAM");
/*      */                     
/* 2449 */                     out.write(10);
/*      */                     
/* 2451 */                     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/* 2452 */                     String camID = (String)request.getAttribute("camid");
/* 2453 */                     String haid = (String)request.getAttribute("haid");
/* 2454 */                     String screenID = (String)request.getAttribute("screenid");
/* 2455 */                     if ((hideFieldsForIT360 == null) || (!hideFieldsForIT360.equals("true")))
/*      */                     {
/*      */ 
/* 2458 */                       out.write(10);
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2468 */                       List list = CAMDBUtil.getCAMDetails(camID);
/* 2469 */                       String camName = (String)list.get(0);
/* 2470 */                       String camDescription = (String)list.get(2);
/*      */                       
/* 2472 */                       request.setAttribute("camname", camName);
/* 2473 */                       request.setAttribute("camdesc", camDescription);
/* 2474 */                       request.setAttribute("needCustomizeScreen", "hint for cam_leftlinks.jsp");
/* 2475 */                       request.setAttribute("needDeleteScreen", "hint for cam_leftlinks.jsp");
/*      */                       
/*      */ 
/* 2478 */                       List screenInfo = null;
/*      */                       
/* 2480 */                       if (request.getAttribute("screeninfo") == null) {
/* 2481 */                         screenInfo = CAMDBUtil.getScreenInfo(Long.parseLong(screenID));
/*      */                       } else {
/* 2483 */                         screenInfo = (List)request.getAttribute("screeninfo");
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/* 2488 */                       out.write("\n\n\n\n\n");
/*      */                       
/* 2490 */                       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2491 */                       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2492 */                       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                       
/* 2494 */                       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2495 */                       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2496 */                       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                         for (;;) {
/* 2498 */                           out.write(10);
/* 2499 */                           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2501 */                           out.write(10);
/* 2502 */                           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2504 */                           out.write(10);
/* 2505 */                           out.write(10);
/* 2506 */                           if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2508 */                           out.write(10);
/* 2509 */                           out.write(10);
/*      */                           
/* 2511 */                           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2512 */                           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2513 */                           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2515 */                           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                           
/* 2517 */                           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2518 */                           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2519 */                           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2520 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2521 */                               out = _jspx_page_context.pushBody();
/* 2522 */                               _jspx_th_tiles_005fput_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2523 */                               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2526 */                               out.write("\n\n<!-- Bread Crumbs -->\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t");
/*      */                               
/* 2528 */                               Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2529 */                               String aid = request.getParameter("haid");
/* 2530 */                               String haName = null;
/* 2531 */                               if (aid != null)
/*      */                               {
/* 2533 */                                 haName = (String)ht.get(aid);
/*      */                               }
/*      */                               
/*      */ 
/* 2537 */                               out.write(10);
/* 2538 */                               out.write(9);
/* 2539 */                               if (_jspx_meth_c_005fcatch_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 2541 */                               out.write(10);
/* 2542 */                               out.write(9);
/*      */                               
/* 2544 */                               IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2545 */                               _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2546 */                               _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 2548 */                               _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2549 */                               int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2550 */                               if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                 for (;;) {
/* 2552 */                                   out.write("\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2553 */                                   out.print(BreadcrumbUtil.getHomePage(request));
/* 2554 */                                   out.write(" &gt; ");
/* 2555 */                                   out.print(BreadcrumbUtil.getHAPage(aid, haName));
/* 2556 */                                   out.write(" &gt; <span class=\"bcactive\"> ");
/* 2557 */                                   out.print(camName);
/* 2558 */                                   out.write("</span></td>\n\t");
/* 2559 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2560 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2564 */                               if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2565 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                               }
/*      */                               
/* 2568 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2569 */                               out.write(10);
/* 2570 */                               out.write(9);
/*      */                               
/* 2572 */                               IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2573 */                               _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2574 */                               _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 2576 */                               _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 2577 */                               int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2578 */                               if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                 for (;;) {
/* 2580 */                                   out.write("\t\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2581 */                                   out.print(BreadcrumbUtil.getMonitorsPage());
/* 2582 */                                   out.write(" &gt; ");
/* 2583 */                                   out.print(BreadcrumbUtil.getMonitorResourceTypes("Custom-Application"));
/* 2584 */                                   out.write(" &gt; <span class=\"bcactive\"> ");
/* 2585 */                                   out.print(camName);
/* 2586 */                                   out.write("</span></td>\n\t");
/* 2587 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2588 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2592 */                               if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2593 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                               }
/*      */                               
/* 2596 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2597 */                               out.write("\t\n    </tr>\n\t<tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\n\n\n");
/* 2598 */                               out.write("<!--$Id$-->\n");
/* 2599 */                               CAMGraphs camGraph = null;
/* 2600 */                               camGraph = (CAMGraphs)_jspx_page_context.getAttribute("camGraph", 1);
/* 2601 */                               if (camGraph == null) {
/* 2602 */                                 camGraph = new CAMGraphs();
/* 2603 */                                 _jspx_page_context.setAttribute("camGraph", camGraph, 1);
/*      */                               }
/* 2605 */                               out.write("\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n\n");
/*      */                               
/* 2607 */                               long camStartTime = System.currentTimeMillis();
/*      */                               
/* 2609 */                               String camIDI = (String)request.getAttribute("camid");
/* 2610 */                               String screenIDI = (String)request.getAttribute("screenid");
/* 2611 */                               List screenInfoI = (List)request.getAttribute("screeninfo");
/* 2612 */                               boolean perfAvailResourceScreen = false;
/* 2613 */                               String resourceID = "";
/* 2614 */                               String fromConfPage1 = request.getAttribute("fromConfPage") + "";
/* 2615 */                               String haidI = request.getParameter("haid");
/* 2616 */                               if ((haidI == null) || (haidI.trim().length() == 0)) {
/* 2617 */                                 haidI = request.getParameter("haid");
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/* 2622 */                               String isFromResourcePage = (String)request.getAttribute("isfromresourcepage");
/* 2623 */                               if (isFromResourcePage == null) {
/* 2624 */                                 isFromResourcePage = "NA";
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/* 2629 */                               if ("true".equals(isFromResourcePage))
/*      */                               {
/*      */ 
/*      */ 
/* 2633 */                                 resourceID = (String)request.getAttribute("resourceid");
/* 2634 */                                 if ((resourceID == null) || (resourceID.trim().length() == 0)) {
/* 2635 */                                   resourceID = request.getParameter("resourceid");
/*      */                                 }
/*      */                                 
/* 2638 */                                 camIDI = resourceID;
/* 2639 */                                 perfAvailResourceScreen = true;
/*      */                                 
/*      */ 
/* 2642 */                                 request.setAttribute("screenInfo", screenInfoI);
/* 2643 */                                 List tmpList = CAMDBUtil.getScreens(Integer.parseInt(camIDI));
/* 2644 */                                 if (tmpList.size() == 0)
/*      */                                 {
/* 2646 */                                   CAMDBUtil.createDefaultScreenForResource(Integer.parseInt(camIDI));
/* 2647 */                                   tmpList = CAMDBUtil.getScreens(Integer.parseInt(camIDI));
/* 2648 */                                   screenInfoI = (List)tmpList.get(0);
/*      */                                 }
/*      */                                 else {
/* 2651 */                                   screenInfoI = (List)tmpList.get(0);
/*      */                                 }
/*      */                                 
/* 2654 */                                 screenIDI = (String)screenInfoI.get(0);
/* 2655 */                                 request.setAttribute("screenid", screenIDI);
/*      */                               }
/*      */                               
/*      */ 
/* 2659 */                               Map fromDB = CAMDBUtil.getCustomizedDataForScreenAdminActivity(Long.parseLong(screenIDI));
/* 2660 */                               List screenConfigList = (List)fromDB.get("completedata");
/*      */                               
/*      */ 
/* 2663 */                               List reportsData = (List)fromDB.get("reportsdata");
/*      */                               
/*      */ 
/* 2666 */                               Map dcTimeMap = CAMDBUtil.getLatestCollectionTimes(Long.parseLong(screenIDI));
/* 2667 */                               Map attribResourceMap = CAMDBUtil.getResourceNamesForAttributesInScreen(Integer.parseInt(screenIDI));
/*      */                               
/* 2669 */                               List screenAttribInfo = CAMDBUtil.getScreenAttributeInfo(Long.parseLong(screenIDI), Integer.parseInt((String)screenInfoI.get(3)), dcTimeMap);
/* 2670 */                               boolean scalarAttribsPresent = screenAttribInfo.size() > 0;
/* 2671 */                               List colScreenAttribInfo = CAMDBUtil.getScreenInfoForColumnarData(Long.parseLong(screenIDI));
/* 2672 */                               boolean columnarAttribsPresent = colScreenAttribInfo.size() > 0;
/* 2673 */                               boolean attribsPresent = (scalarAttribsPresent == true) || (columnarAttribsPresent == true);
/* 2674 */                               String quickNote = "This page displays the attributes monitored from various resources as configured during design time. Placing the mouse over the value for table data will display the time when the data was collected. Time intervals will be different if the attributes are from different resources.";
/*      */                               
/*      */ 
/*      */ 
/* 2678 */                               if (request.getAttribute("quicknote") == null) {
/* 2679 */                                 request.setAttribute("quicknote", quickNote);
/*      */                               }
/* 2681 */                               String configureLink = "/ShowCAM.do?method=configureScreen&screenid=" + screenIDI + "&camid=" + camIDI + "&haid=" + haidI + "&isfromresourcepage=" + isFromResourcePage;
/* 2682 */                               if ((request.isUserInRole("ENTERPRISEADMIN")) && (com.adventnet.appmanager.util.Constants.isSsoEnabled()))
/*      */                               {
/* 2684 */                                 StringBuilder builder = new StringBuilder();
/* 2685 */                                 builder.append("https:").append("//");
/* 2686 */                                 builder.append(com.adventnet.appmanager.util.Constants.getAppHostName()).append(":");
/* 2687 */                                 builder.append(com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.getsslport()).append(configureLink);
/* 2688 */                                 configureLink = builder.toString();
/*      */                               }
/* 2690 */                               request.setAttribute("configurelink", configureLink);
/* 2691 */                               String secondLevelLinkTitle; if (!perfAvailResourceScreen)
/*      */                               {
/*      */ 
/* 2694 */                                 List sLinks = new ArrayList();
/* 2695 */                                 List sText = new ArrayList();
/*      */                                 
/* 2697 */                                 sLinks.add("Add ScreenXXXX");
/* 2698 */                                 sText.add("/ShowCAM.do?method=addScreen&camid=" + camIDI + "&haid=" + haidI);
/*      */                                 
/*      */ 
/*      */ 
/* 2702 */                                 sLinks.add("Customize");
/* 2703 */                                 sText.add(configureLink);
/*      */                                 
/*      */ 
/* 2706 */                                 sLinks.add("<a href=\"/CAMDeleteScreen.do?method=deleteScreen&screenid=" + screenIDI + "&camid=" + camIDI + "&haid=" + haidI + "\" onclick=\"return deleteScreen();\" class='staticlinks'>Delete Screen</a>");
/* 2707 */                                 sText.add("");
/*      */                                 
/*      */ 
/*      */ 
/* 2711 */                                 List[] secondLevelOfLinks = { sText, sLinks };
/* 2712 */                                 secondLevelLinkTitle = "Admin Activity";
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2721 */                               String configureThresholdRedirectLink = "/ShowCAM.do?method=showScreen&haid=" + haidI + "&camid=" + camIDI + "&screenid=" + screenIDI;
/*      */                               
/* 2723 */                               if ("true".equals(isFromResourcePage)) {
/* 2724 */                                 configureThresholdRedirectLink = "/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID";
/*      */                               }
/*      */                               
/* 2727 */                               configureThresholdRedirectLink = URLEncoder.encode(configureThresholdRedirectLink);
/*      */                               
/*      */ 
/*      */ 
/* 2731 */                               out.write("\n\n\n\n<script language=\"JavaScript1.2\">\n\nfunction showAttribGraph(attribID,mbean) {\n       var featurelist = \"toolbar=no,status=no,menubar=no,width=450,height=300,scrollbars=yes\";\n       var url = \"/ShowCAM.do?method=showSingleGraphScreen&attributeid=\" + attribID+\"&mbean=\" +mbean;\n       popUp = window.open(url,'popUp',featurelist);\n       return false;\n}\n\n</SCRIPT>\n<!--This script is used to show horizontal bar if customer attributes tables have more number of attributes in SNMP Devices.--> \n<script>\n    jQuery(document).ready(function(){\n        var windowWidth = jQuery(window).width();\n        windowWidth = windowWidth*(81/100);\n        jQuery('.horTableScroll').css({'width':windowWidth});//No I18N\n\n    });\n</script>\n\n<style>\n    .horTableScroll {\n        overflow-x:auto;\n    }    \n</style>\n<!--");
/*      */                               
/* 2733 */                               String camid = request.getParameter("camid");
/*      */                               
/*      */ 
/* 2736 */                               out.write("-->\n\n\n<script>\nfunction validateAndSubmit()\n{\n   if(trimAll(document.AMActionForm.camname.value)==\"\")\n   {\n        alert('");
/* 2737 */                               out.print(FormatUtil.getString("am.webclient.cam.namealert"));
/* 2738 */                               out.write("');\n        return;\n   }\n   document.AMActionForm.submit();\n}\n\n</script>\n\n");
/*      */                               
/* 2740 */                               list = CAMDBUtil.getCAMDetails(camIDI);
/* 2741 */                               camName = (String)list.get(0);
/* 2742 */                               camDescription = (String)list.get(2);
/*      */                               
/* 2744 */                               out.write("\n\n\n\n");
/*      */                               
/* 2746 */                               if ("true".equals(request.getParameter("editPage")))
/*      */                               {
/* 2748 */                                 out.write("\n<div id=\"edit\" style=\"display:block\">\n");
/*      */                               } else {
/* 2750 */                                 out.write("\n<div id=\"edit\" style=\"display:none\">\n");
/*      */                               }
/* 2752 */                               out.write(10);
/* 2753 */                               out.write(10);
/*      */                               
/* 2755 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 2756 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2757 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 2759 */                               _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*      */                               
/* 2761 */                               _jspx_th_html_005fform_005f0.setMethod("get");
/*      */                               
/* 2763 */                               _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2764 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2765 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 2767 */                                   out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n<td height=\"28\"   class=\"tableheading\">");
/* 2768 */                                   out.print(FormatUtil.getString("am.webclient.common.configurationdetails.text"));
/* 2769 */                                   out.write("\n\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 2770 */                                   out.print(request.getParameter("name"));
/* 2771 */                                   out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 2772 */                                   out.print(request.getParameter("haid"));
/* 2773 */                                   out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 2774 */                                   out.print(request.getParameter("type"));
/* 2775 */                                   out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 2776 */                                   out.print(request.getParameter("type"));
/* 2777 */                                   out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"configureJMX\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2778 */                                   out.print(request.getParameter("resourceid"));
/* 2779 */                                   out.write("\">\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 2780 */                                   out.print(request.getParameter("resourcename"));
/* 2781 */                                   out.write("\">\n<input type=\"hidden\" name=\"moname\" value=\"");
/* 2782 */                                   out.print(request.getParameter("moname"));
/* 2783 */                                   out.write("\">\n\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\">\n<span class=\"bodytextboldwhiteun\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n");
/* 2784 */                                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 2785 */                                   out.write("</span>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n<tr>\n    <td width=\"20%\" height=\"32\" valign=='top' class=\"bodytext\"> ");
/* 2786 */                                   out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2787 */                                   out.write("\n</td>\n    <td width=\"80%\" class=\"bodytext\"> <textarea name=\"camname\" cols=\"38\" rows=\"1\" class=\"formtextarea\">");
/* 2788 */                                   out.print(camName);
/* 2789 */                                   out.write(" </textarea>\n</td>\n</tr>\n\n<tr>\n    <td valign='top'  class=\"bodytext\"> ");
/* 2790 */                                   out.print(FormatUtil.getString("Description"));
/* 2791 */                                   out.write("</td>\n    <td  class=\"bodytext\"> <textarea name=\"camdesc\" cols=\"38\" rows=\"3\" class=\"formtextarea\" >");
/* 2792 */                                   out.print(camDescription);
/* 2793 */                                   out.write("</textarea>\n    </td>\n  </tr>\n</table>\n");
/*      */                                   
/* 2795 */                                   String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/*      */                                   
/* 2797 */                                   out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"20%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"80%\" class=\"tablebottom\" >\n<input name=\"addcustomapp\" type=\"button\" class=\"buttons btn_highlt\" \" value=\"");
/* 2798 */                                   out.print(FormatUtil.getString("am.webclient.common.startmonitoring.text"));
/* 2799 */                                   out.write("\" onClick=\"validateAndSubmit()\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons btn_link\" value=\"");
/* 2800 */                                   out.print(cancel);
/* 2801 */                                   out.write("\" onClick=\"javascript:toggleDiv('edit')\"/>\n     </td>\n  </tr>\n</table>\n");
/* 2802 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2803 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2807 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2808 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 2811 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2812 */                               out.write("\n</div>\n");
/*      */                               
/* 2814 */                               if (!attribsPresent)
/*      */                               {
/*      */ 
/* 2817 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n    <td colspan=3  height=\"19\" class=\"tableheadingbborder\" >");
/* 2818 */                                 out.print(camName);
/* 2819 */                                 out.write("\n      ");
/* 2820 */                                 if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 2821 */                                   out.write(": <span class=\"topdesc\">");
/* 2822 */                                   out.print(FormatUtil.getString(camDescription));
/* 2823 */                                   out.write(" </span>");
/*      */                                 }
/* 2825 */                                 out.write("</td>\n     <td  height=\"19\" width=\"20%\" class=\"tableheadingbborder\">\n     ");
/*      */                                 
/* 2827 */                                 if ((attribsPresent) && (request.getAttribute("showrefreshnowoption") != null))
/*      */                                 {
/*      */ 
/* 2830 */                                   out.write("\n       <a class=\"bodytextboldwhiteun\" href=\"/deleteMO.do?method=fetchDataNowForResource&resourceid=");
/* 2831 */                                   out.print(camIDI);
/* 2832 */                                   out.write("&redirectto=");
/* 2833 */                                   out.print(URLEncoder.encode("/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID" + fromConfPage1));
/* 2834 */                                   out.write(34);
/* 2835 */                                   out.write(62);
/* 2836 */                                   out.print(FormatUtil.getString("am.webclient.availabilityperf.fetchvalue"));
/* 2837 */                                   out.write("</a>\n     ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 2843 */                                   out.write("\n     &nbsp;\n     ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 2847 */                                 out.write("\n\n     </td>\n</tr>\n\n<tr>\n    <td colspan=4  height=\"29\" ><span class=\"bodytext\">&nbsp;");
/* 2848 */                                 out.print(FormatUtil.getString("am.webclient.cam.addcustomattributes.message"));
/*      */                                 
/* 2850 */                                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2851 */                                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2852 */                                 _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 2854 */                                 _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 2855 */                                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2856 */                                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 2858 */                                     out.write(" <a class='staticlinks' href=\"");
/* 2859 */                                     out.print(configureLink);
/* 2860 */                                     out.write("\">\n      ");
/* 2861 */                                     out.print(FormatUtil.getString("am.webclient.cam.addattributes.link"));
/* 2862 */                                     out.write("</a>.");
/* 2863 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2864 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2868 */                                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2869 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 2872 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2873 */                                 out.write("</span></td>\n</tr>\n</table>\n");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/* 2878 */                                 if (!scalarAttribsPresent) {
/* 2879 */                                   out.write(10);
/* 2880 */                                   out.write(10);
/*      */ 
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/*      */ 
/* 2888 */                                   List row = null;
/* 2889 */                                   int posOfAttribName = 2;
/* 2890 */                                   int posOfDispType = 4;
/* 2891 */                                   int posOfAttribValue = 7;
/* 2892 */                                   int posOfResourceID = 6;
/* 2893 */                                   int posOfAttribID = 0;
/* 2894 */                                   int posOfAttribType = 3;
/* 2895 */                                   String className = "whitegrayborder";
/* 2896 */                                   String currentResourceName = null;
/* 2897 */                                   String currentMBeanName = null;
/* 2898 */                                   Map infoMap = CAMDBUtil.getMBeanBasedScreenData(Long.parseLong(screenIDI));
/* 2899 */                                   Map idVsMBeanAndRes = (HashMap)infoMap.get("attrIdVsMBeanName");
/* 2900 */                                   List ordListFromDB = (ArrayList)infoMap.get("attributeidsorderedlist");
/* 2901 */                                   List orderedList = new ArrayList(screenAttribInfo.size());
/*      */                                   
/*      */ 
/* 2904 */                                   out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n    <td colspan=3  height=\"19\" class=\"tableheadingbborder\" >");
/* 2905 */                                   out.print(camName);
/* 2906 */                                   out.write("\n    ");
/* 2907 */                                   if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 2908 */                                     out.write("  : <span class=\"topdesc\">");
/* 2909 */                                     out.print(FormatUtil.getString(camDescription));
/* 2910 */                                     out.write(" </span> ");
/*      */                                   }
/* 2912 */                                   out.write("</td>\n\t<td width=\"30%\" nowrap class=\"tableheadingbborder\">\n\t");
/* 2913 */                                   if ((attribsPresent) && (request.getAttribute("showrefreshnowoption") != null)) {
/* 2914 */                                     out.write("\n       <a class=\"bodytextboldwhiteun\" href=\"/deleteMO.do?method=fetchDataNowForResource&resourceid=");
/* 2915 */                                     out.print(camIDI);
/* 2916 */                                     out.write("&redirectto=");
/* 2917 */                                     out.print(URLEncoder.encode("/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID" + fromConfPage1));
/* 2918 */                                     out.write(34);
/* 2919 */                                     out.write(62);
/* 2920 */                                     out.print(FormatUtil.getString("am.webclient.availabilityperf.fetchvalue"));
/* 2921 */                                     out.write("</a>\n       ");
/*      */                                   } else {
/* 2923 */                                     out.write("\n       <a class=\"staticlinks\" href=\"javascript:void(0);\" onclick=\"getCustomFields('");
/* 2924 */                                     out.print(camIDI);
/* 2925 */                                     out.write("','noalarms',false,'CustomFieldValues',false);\">");
/* 2926 */                                     out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 2927 */                                     out.write("</a>&nbsp;");
/* 2928 */                                     out.write("\n       ");
/*      */                                   }
/* 2930 */                                   out.write("\n       </td>\n\n<tr>\n                <td width=\"36%\" class=\"columnheading\" > ");
/* 2931 */                                   out.print(FormatUtil.getString("am.webclient.camscreen.attributename"));
/* 2932 */                                   out.write("</td>\n            <td width=\"30%\" class=\"columnheading\" > ");
/* 2933 */                                   out.print(FormatUtil.getString("am.webclient.camscreen.value"));
/* 2934 */                                   out.write("</td>\n        <td width=\"20%\" class=\"columnheading\" > ");
/* 2935 */                                   if ((request.getParameter("type") != null) && (request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 2936 */                                     out.write(" &nbsp; ");
/*      */                                   } else {
/* 2938 */                                     out.write(32);
/* 2939 */                                     out.print(FormatUtil.getString("am.webclient.camscreen.datacollectiontime"));
/* 2940 */                                     out.write("</td> ");
/*      */                                   }
/* 2942 */                                   out.write("\n    <td width=\"9%\" class=\"columnheading\" >");
/* 2943 */                                   out.print(FormatUtil.getString("am.webclient.camscreen.actions.text"));
/* 2944 */                                   out.write("</td>\n</tr>\n");
/*      */                                   
/* 2946 */                                   Hashtable token = new Hashtable();
/*      */                                   
/* 2948 */                                   for (int i = 0; i < screenAttribInfo.size(); i++)
/*      */                                   {
/* 2950 */                                     row = (List)screenAttribInfo.get(i);
/* 2951 */                                     if (i % 2 == 0) {
/* 2952 */                                       className = "whitegrayborder";
/*      */                                     } else {
/* 2954 */                                       className = "yellowgrayborder";
/*      */                                     }
/*      */                                     
/* 2957 */                                     boolean newResource = false;
/* 2958 */                                     boolean newMBean = false;
/* 2959 */                                     boolean addMBeanRow = false;
/* 2960 */                                     String date = "Could not be obtained";
/* 2961 */                                     String shortDate = "Could not be obtained";
/* 2962 */                                     String longFormatDate = "Could not be obtained";
/* 2963 */                                     String resourceName4Attrib = "";
/*      */                                     try
/*      */                                     {
/* 2966 */                                       resourceName4Attrib = (String)attribResourceMap.get(row.get(posOfAttribID));
/* 2967 */                                       String attribID = (String)row.get(posOfAttribID);
/* 2968 */                                       String mBeanName = (String)idVsMBeanAndRes.get(attribID);
/* 2969 */                                       if (currentMBeanName == null)
/*      */                                       {
/* 2971 */                                         currentMBeanName = mBeanName;
/* 2972 */                                         newMBean = true;
/*      */                                       }
/* 2974 */                                       else if (!currentMBeanName.equals(mBeanName))
/*      */                                       {
/* 2976 */                                         currentMBeanName = mBeanName;
/* 2977 */                                         newMBean = true;
/*      */                                       }
/* 2979 */                                       if (currentResourceName == null)
/*      */                                       {
/* 2981 */                                         currentResourceName = resourceName4Attrib;
/* 2982 */                                         newResource = true;
/*      */                                       }
/* 2984 */                                       else if (!currentResourceName.equals(resourceName4Attrib))
/*      */                                       {
/* 2986 */                                         currentResourceName = resourceName4Attrib;
/* 2987 */                                         newResource = true;
/*      */                                       }
/* 2989 */                                       addMBeanRow = (newMBean) || (newResource);
/* 2990 */                                       date = String.valueOf(Long.parseLong((String)dcTimeMap.get(row.get(posOfAttribID))));
/* 2991 */                                       shortDate = formatDT(date);
/* 2992 */                                       longFormatDate = new Date(Long.parseLong(date)).toString();
/*      */                                     }
/*      */                                     catch (Exception e) {}
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3001 */                                     String value = (String)row.get(posOfAttribValue);
/* 3002 */                                     if (row.get(posOfAttribType).equals("0"))
/*      */                                     {
/* 3004 */                                       if (value.equals("-1"))
/*      */                                       {
/* 3006 */                                         value = FormatUtil.getString("am.webclient.cam.nodata");
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/* 3011 */                                     out.write(10);
/*      */                                     
/* 3013 */                                     if (addMBeanRow)
/*      */                                     {
/* 3015 */                                       if (((String)attribResourceMap.get(row.get(posOfAttribID) + "_RESTYPE")).equals("SNMP"))
/*      */                                       {
/*      */ 
/* 3018 */                                         out.write("\n<tr>\n       <td height=\"20\" class=\"secondchildnode\" colspan=\"4\"><span class=\"bodytextbold\"><span class=\"bodytext\">(");
/* 3019 */                                         out.print(currentResourceName);
/* 3020 */                                         out.write(")</span></span></td>\n</tr>\n\n");
/*      */ 
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3027 */                                         out.write("\n\n<tr>\n<td height=\"20\"   class=\"secondchildnode\"  colspan=\"4\" onmouseover=\"ddrivetip(this,event,'");
/* 3028 */                                         out.print(addBreakAt((currentMBeanName + " - " + currentResourceName).replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"), 80));
/* 3029 */                                         out.write("',null,true,'#000000',300)\" onmouseout=\"hideddrivetip()\" <span class=\"bodytextbold\">");
/* 3030 */                                         out.print(addBreakAt(currentMBeanName, 80));
/* 3031 */                                         out.write(" <span class=\"availablity-arrow\">&raquo;&nbsp;</span> ");
/* 3032 */                                         out.print(getTrimmedText(currentResourceName, 25));
/* 3033 */                                         out.write("</span> </td> ");
/* 3034 */                                         out.write("\n</tr>\n");
/*      */                                       }
/*      */                                     }
/*      */                                     try
/*      */                                     {
/* 3039 */                                       StringTokenizer mbean = new StringTokenizer(currentResourceName, "_");
/* 3040 */                                       int j = 0;
/* 3041 */                                       while (mbean.hasMoreTokens()) {
/* 3042 */                                         String t = mbean.nextToken();
/* 3043 */                                         token.put(Integer.valueOf(j), t);
/* 3044 */                                         j++;
/*      */                                       }
/*      */                                       
/*      */ 
/* 3048 */                                       String attrbId = (String)row.get(posOfAttribID);
/* 3049 */                                       String resType = (String)attribResourceMap.get(attrbId + "_RESTYPE");
/* 3050 */                                       if ((resType != null) && (resType.equalsIgnoreCase("snmp"))) {
/* 3051 */                                         String resourceId = (String)row.get(row.size() - 2);
/* 3052 */                                         if ((resourceId != null) && (resourceId.length() > 0)) {
/* 3053 */                                           List l = DBUtil.getRows("SELECT RESOURCENAME FROM AM_ManagedObject where RESOURCEID=" + resourceId);
/* 3054 */                                           if ((l != null) && (l.size() == 1)) {
/* 3055 */                                             j = 0;
/* 3056 */                                             String actualResourceName = (String)((ArrayList)l.get(0)).get(0);
/* 3057 */                                             mbean = new StringTokenizer(actualResourceName, "_");
/* 3058 */                                             while (mbean.hasMoreTokens()) {
/* 3059 */                                               String t = mbean.nextToken();
/* 3060 */                                               token.put(Integer.valueOf(j), t);
/* 3061 */                                               j++;
/*      */                                             }
/*      */                                           }
/*      */                                         }
/*      */                                       }
/*      */                                     } catch (Exception e) {}
/* 3067 */                                     String toshow = getTrimmedText(value, 30);
/* 3068 */                                     request.setAttribute("toshow", toshow);
/* 3069 */                                     request.setAttribute("fullvalue", value);
/* 3070 */                                     int len = value.length();
/* 3071 */                                     String tooltiptype = (String)row.get(posOfDispType);
/*      */                                     
/* 3073 */                                     if (tooltiptype.equals("1")) {
/* 3074 */                                       tooltiptype = "Counter";
/* 3075 */                                       if ((toshow.equals(" ")) || (value.equals(" ")))
/*      */                                       {
/* 3077 */                                         Map fromMap = new HashMap();
/* 3078 */                                         fromMap = (HashMap)com.adventnet.appmanager.cam.CAMServerUtil.collectFirstData;
/* 3079 */                                         if (fromMap != null) {
/* 3080 */                                           List lst = new ArrayList();
/* 3081 */                                           lst = (ArrayList)fromMap.get((String)row.get(posOfAttribID));
/* 3082 */                                           if (lst != null) {
/* 3083 */                                             request.setAttribute("toshow", lst.get(2));
/* 3084 */                                             request.setAttribute("fullvalue", lst.get(3));
/*      */                                           }
/*      */                                         }
/*      */                                       }
/*      */                                     } else {
/* 3089 */                                       tooltiptype = "Non-Counter";
/*      */                                     }
/*      */                                     
/* 3092 */                                     out.write("\n\n<tr>\n\t<td class=\"");
/* 3093 */                                     out.print(className);
/* 3094 */                                     out.write("\" onmouseover=\"ddrivetip(this,event,'");
/* 3095 */                                     out.print(FormatUtil.getString("am.webclient.snmp.tootipMsg", new String[] { (String)row.get(posOfAttribName), resourceName4Attrib, tooltiptype }));
/* 3096 */                                     out.write("',null,true,'#000000','len')\" onmouseout=\"hideddrivetip()\"> <span class=\"bodytext\">");
/* 3097 */                                     out.print(getTrimmedText((String)row.get(posOfAttribName), 25));
/* 3098 */                                     out.write(" </span></td>\n\n");
/*      */                                     
/* 3100 */                                     if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 3101 */                                       if (len >= 30)
/*      */                                       {
/* 3103 */                                         out.write(10);
/* 3104 */                                         out.write(10);
/* 3105 */                                         String breaktext = addBreakAt(value, 50);
/* 3106 */                                         out.write("\n     <td class=\"");
/* 3107 */                                         out.print(className);
/* 3108 */                                         out.write("\" onmouseover=\"ddrivetip(this,event,'");
/* 3109 */                                         out.print(value.replaceAll("\\n", " "));
/* 3110 */                                         out.write("',null,true,'#000000','len')\" onmouseout=\"hideddrivetip()\" ><span class=\"bodytext\"> ");
/* 3111 */                                         if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                           return;
/* 3113 */                                         out.write(" </span></td>\n\n");
/*      */                                       }
/*      */                                       else {
/* 3116 */                                         out.write("\n\n<td class=\"");
/* 3117 */                                         out.print(className);
/* 3118 */                                         out.write("\" ><span class=\"bodytext\"> ");
/* 3119 */                                         if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                           return;
/* 3121 */                                         out.write(" </span></td>\n\n");
/*      */                                       }
/*      */                                       
/* 3124 */                                       out.write("\n\n        <td class=\"");
/* 3125 */                                       out.print(className);
/* 3126 */                                       out.write("\" title=\"Time : ");
/* 3127 */                                       out.print(longFormatDate);
/* 3128 */                                       out.write(" Resource : ");
/* 3129 */                                       out.print(resourceName4Attrib);
/* 3130 */                                       out.write("\"> <span class=\"bodytext\">");
/* 3131 */                                       out.print(shortDate);
/* 3132 */                                       out.write("</span></td>\n");
/*      */                                     } else {
/* 3134 */                                       out.write("\n<td colspan=2 class=\"");
/* 3135 */                                       out.print(className);
/* 3136 */                                       out.write("\"><span class=\"bodytext\">");
/* 3137 */                                       out.print(addBreakAt(value, 55));
/* 3138 */                                       out.write("</span></td>\n");
/*      */                                     }
/* 3140 */                                     out.write("\n        <td class=\"");
/* 3141 */                                     out.print(className);
/* 3142 */                                     out.write("\" >\n        ");
/* 3143 */                                     if ((row.get(posOfAttribType).equals("0")) || (row.get(posOfAttribType).equals("1"))) {
/* 3144 */                                       if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS")))
/*      */                                       {
/* 3146 */                                         out.write("\n\n<a  style=\"cursor: pointer;\" onClick=\"javascript:MM_openBrWindow('/jsp/attribute_edit.jsp?resourceid=");
/* 3147 */                                         out.print(row.get(posOfResourceID));
/* 3148 */                                         out.write("&attributeid=");
/* 3149 */                                         out.print(row.get(posOfAttribID));
/* 3150 */                                         out.write("&camid=");
/* 3151 */                                         out.print(camIDI);
/* 3152 */                                         out.write("&haid=");
/* 3153 */                                         out.print(haidI);
/* 3154 */                                         out.write("&screenid=");
/* 3155 */                                         out.print(screenIDI);
/* 3156 */                                         out.write("&resourcename=");
/* 3157 */                                         out.print(currentResourceName);
/* 3158 */                                         out.write("&hostname=");
/* 3159 */                                         out.print(token.get(Integer.valueOf(0)));
/* 3160 */                                         out.write("&resourcetype=");
/* 3161 */                                         out.print(token.get(Integer.valueOf(1)));
/* 3162 */                                         out.write("&portno=");
/* 3163 */                                         out.print(token.get(Integer.valueOf(2)));
/* 3164 */                                         out.write("&attributes=");
/* 3165 */                                         out.print(URLEncoder.encode(currentMBeanName + "|" + (String)row.get(1) + "|" + row.get(posOfAttribType)));
/* 3166 */                                         out.write("&displayname=");
/* 3167 */                                         out.print((String)row.get(posOfAttribName));
/* 3168 */                                         out.write("&isfromeditpage=");
/* 3169 */                                         out.print("true");
/* 3170 */                                         out.write("&resourceid=");
/* 3171 */                                         out.print(row.get(posOfResourceID));
/* 3172 */                                         out.write("&dispType=");
/* 3173 */                                         out.print(row.get(posOfDispType));
/* 3174 */                                         out.write("','Personalize','width=390,height=200,screenX=100,screenY=300,scrollbars=yes')\"><img src=\"/images/icon_edit.gif\"  border=\"0\" title='");
/* 3175 */                                         out.print(FormatUtil.getString("jmxnotification.edit"));
/* 3176 */                                         out.write("'></a>\n");
/*      */                                       }
/* 3178 */                                       out.write("\n\n    <A class='staticlinks' href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3179 */                                       out.print(row.get(posOfResourceID));
/* 3180 */                                       out.write("&attributeIDs=");
/* 3181 */                                       out.print(row.get(posOfAttribID));
/* 3182 */                                       out.write("&attributeToSelect=");
/* 3183 */                                       out.print(row.get(posOfAttribID));
/* 3184 */                                       out.write("&redirectto=");
/* 3185 */                                       out.print(configureThresholdRedirectLink);
/* 3186 */                                       out.write("'>\n    <img src=\"/images/icon_associateaction.gif\" title='");
/* 3187 */                                       out.print(ALERTCONFIG_TEXT);
/* 3188 */                                       out.write("' border=\"0\" ></A>\n\n    ");
/*      */                                       
/* 3190 */                                       if (row.get(posOfAttribType).equals("0"))
/*      */                                       {
/*      */ 
/* 3193 */                                         out.write("\n    <a style=\"cursor: pointer;\" onclick=\"showAttribGraph(");
/* 3194 */                                         out.print(row.get(posOfAttribID));
/* 3195 */                                         out.write(44);
/* 3196 */                                         out.write(39);
/* 3197 */                                         out.print(getTrimmedText(currentMBeanName, 50).replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 3198 */                                         out.write("')\" ><img src='/images/icon_linegraph.gif' title='");
/* 3199 */                                         out.print(FormatUtil.getString("jmxnotification.showgraph"));
/* 3200 */                                         out.write("' border='0' ></a>\n\n\n        ");
/*      */                                       }
/*      */                                       
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 3207 */                                       out.write("\n\t\t&nbsp;\n\t");
/*      */                                     }
/*      */                                     
/*      */ 
/* 3211 */                                     out.write("\n</td>\n\n</tr>\n");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3215 */                                   out.write("\n</tr>\n\n<tr>\n\t<td colspan=4  height='25' class=\"tablebottom\"><span class=\"bodytext\">");
/*      */                                   
/* 3217 */                                   PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3218 */                                   _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3219 */                                   _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                   
/* 3221 */                                   _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 3222 */                                   int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3223 */                                   if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                     for (;;) {
/* 3225 */                                       out.write("\n       <a href=\"");
/* 3226 */                                       out.print(configureLink);
/* 3227 */                                       out.write("\" class='staticlinks'>");
/* 3228 */                                       out.print(FormatUtil.getString("am.webclient.cam.adddeleteattributes.text"));
/* 3229 */                                       out.write("</a> ");
/* 3230 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3231 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3235 */                                   if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3236 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                   }
/*      */                                   
/* 3239 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3240 */                                   out.write("</span></td>\n</tr>\n</table>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3244 */                                 out.write("\n<br>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3245 */                                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/MyField_div.jsp", out, false);
/* 3246 */                                 out.write("</td></tr></table>\n");
/*      */                                 
/* 3248 */                                 if (columnarAttribsPresent)
/*      */                                 {
/* 3250 */                                   int k = 0;
/* 3251 */                                   String titlePrefix = FormatUtil.getString("am.webclient.common.name.text");
/* 3252 */                                   for (int i = 0; i < colScreenAttribInfo.size(); i++)
/*      */                                   {
/* 3254 */                                     out.write("\n\t<div class=\"horTableScroll\">\n\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"99%\" class=\"lrtbdarkborder\">\n\t");
/*      */                                     
/* 3256 */                                     List temp1 = (List)colScreenAttribInfo.get(i);
/* 3257 */                                     if (temp1.size() > 0)
/*      */                                     {
/* 3259 */                                       Properties tmpProp = (Properties)((List)temp1.get(0)).get(0);
/* 3260 */                                       String mbeanName = tmpProp.getProperty("mbeanname");
/* 3261 */                                       List h = (List)tmpProp.get("columnnames");
/*      */                                       
/* 3263 */                                       out.write("\n\t\t<tr>\n\t\t<td height=\"24\" width=\"75%\" class=\"tableheadingbborder\" colspan=\"");
/* 3264 */                                       out.print(h.size() + 1);
/* 3265 */                                       out.write("\">\n\t\t");
/* 3266 */                                       out.print(titlePrefix);
/* 3267 */                                       out.write(32);
/* 3268 */                                       out.write(58);
/* 3269 */                                       out.write(32);
/* 3270 */                                       out.print(getTrimmedText(mbeanName, 50));
/* 3271 */                                       out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                                     }
/*      */                                     
/* 3274 */                                     int cnt = 0; for (int size = temp1.size(); cnt < size; cnt++)
/*      */                                     {
/*      */ 
/* 3277 */                                       out.write("\n\t\t<tr><td width=\"100%\" style=\"vertical-align: top;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t");
/*      */                                       
/*      */ 
/* 3280 */                                       List oneTableList = (List)temp1.get(cnt);
/* 3281 */                                       Properties camprops = (Properties)oneTableList.get(0);
/* 3282 */                                       List headers = (List)camprops.get("columnnames");
/* 3283 */                                       List thresholdPossibleIDs = (List)camprops.get("thresholdpossibleattrids");
/* 3284 */                                       if ("snmp table".equals(camprops.get("TableType"))) {
/* 3285 */                                         titlePrefix = "SNMP Table Name";
/*      */                                       } else {
/* 3287 */                                         titlePrefix = FormatUtil.getString("am.webclient.camscreen.titleprefix");
/*      */                                       }
/*      */                                       
/*      */ 
/* 3291 */                                       out.write("\n\t\t\t<tr >\n\t\t     ");
/*      */                                       
/* 3293 */                                       String attrs = "";
/* 3294 */                                       for (int a = 0; a < thresholdPossibleIDs.size(); a++)
/*      */                                       {
/* 3296 */                                         attrs = attrs + (String)thresholdPossibleIDs.get(a) + ",";
/*      */                                       }
/*      */                                       
/*      */ 
/* 3300 */                                       out.write("\n\t\t<td height=\"24\" width=\"75%\" class=\"secondchildnode\" colspan=\"");
/* 3301 */                                       out.print(headers.size());
/* 3302 */                                       out.write("\">\n\t\t");
/* 3303 */                                       String temp = (String)camprops.get("attrName");
/* 3304 */                                       out.write("\n\t\t<span class=\"bodytextbold\">");
/* 3305 */                                       out.print(FormatUtil.getString("am.webclient.camscreen.attributegraphs.attribute.text"));
/* 3306 */                                       out.write(32);
/* 3307 */                                       out.write(58);
/* 3308 */                                       out.write(32);
/* 3309 */                                       out.print(getTrimmedText(temp, 50));
/* 3310 */                                       out.write("</span></td>\n\n\t<td class=\"secondchildnode\" width=\"25%\">\n\n\t");
/*      */                                       
/* 3312 */                                       if (thresholdPossibleIDs.size() > 0)
/*      */                                       {
/*      */ 
/*      */ 
/* 3316 */                                         out.write("\n\n\t\t<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3317 */                                         out.print(camprops.get("resourceid"));
/* 3318 */                                         out.write("&attributeIDs=");
/* 3319 */                                         out.print(attrs.substring(0, attrs.length() - 1));
/* 3320 */                                         out.write("&attributeToSelect=");
/* 3321 */                                         out.print(thresholdPossibleIDs.get(0));
/* 3322 */                                         out.write("&redirectto=");
/* 3323 */                                         out.print(configureThresholdRedirectLink);
/* 3324 */                                         out.write("' class=\"staticlinks\">\n        <img src=\"/images/icon_associateaction.gif\" alt=\"Associate Action\" border=\"0\" align=\"absmiddle\" hspace=\"5\" >");
/* 3325 */                                         out.print(ALERTCONFIG_TEXT);
/* 3326 */                                         out.write("</a>\n        ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 3330 */                                       out.write("\n\t\t</td>\n\t\t</tr>\n\t        <tr>\n\t\t");
/*      */                                       
/* 3332 */                                       for (k = 0; k < headers.size(); k++)
/*      */                                       {
/*      */ 
/* 3335 */                                         out.write("\n\t\t\t\t<td class=\"columnheading\" align=left>\n\t\t\t\t");
/* 3336 */                                         out.print(headers.get(k));
/* 3337 */                                         out.write("\n\t\t\t\t</td>\n\t\t\t");
/*      */                                       }
/*      */                                       
/*      */ 
/*      */ 
/* 3342 */                                       out.write("\n\t\t<td class=\"columnheading\" width=\"19%\">&nbsp;</td>\n\t        </tr>\n\t        ");
/*      */                                       
/* 3344 */                                       for (int j = 1; j < oneTableList.size(); j++)
/*      */                                       {
/* 3346 */                                         String bgclass = "class=\"whitegrayrightalign-reports\"";
/* 3347 */                                         if (j % 2 != 0)
/*      */                                         {
/* 3349 */                                           bgclass = "class=\"whitegrayrightalign-reports\"";
/*      */                                         }
/*      */                                         
/* 3352 */                                         out.write("\n\t        \t\t<tr>\n\t        \t\t");
/*      */                                         
/* 3354 */                                         for (int l = 0; l < headers.size(); l++)
/*      */                                         {
/*      */ 
/* 3357 */                                           out.write("\n\t\t\t\t\t<td height=\"28\"  ");
/* 3358 */                                           out.print(bgclass);
/* 3359 */                                           out.write(" align=\"left\" title=\"");
/* 3360 */                                           out.print(formatDT((String)camprops.get("dctime")));
/* 3361 */                                           out.write("\">\n\t\t\t\t\t\t<div  style=\"WORD-BREAK:BREAK-ALL; word-wrap: break-word; width:100px; white-space:-moz-pre-wrap; white-space: normal;\">");
/* 3362 */                                           out.print(((List)oneTableList.get(j)).get(l));
/* 3363 */                                           out.write("</div>\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/*      */                                         }
/*      */                                         
/*      */ 
/* 3367 */                                         out.write("\n\n\t\t\t<td ");
/* 3368 */                                         out.print(bgclass);
/* 3369 */                                         out.write(" width=\"19%\">&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t");
/*      */                                       }
/*      */                                       
/*      */ 
/* 3373 */                                       out.write("\n\t</table></td></tr>\n\t");
/*      */                                     }
/*      */                                     
/*      */ 
/* 3377 */                                     out.write("\n<tr>\n        <td colspan=");
/* 3378 */                                     out.print(k + 1);
/* 3379 */                                     out.write("  height='25' class=\"tablebottom\"><span class=\"bodytext\">");
/*      */                                     
/* 3381 */                                     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3382 */                                     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3383 */                                     _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                     
/* 3385 */                                     _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 3386 */                                     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3387 */                                     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                       for (;;) {
/* 3389 */                                         out.write("\n       <a href=\"");
/* 3390 */                                         out.print(configureLink);
/* 3391 */                                         out.write("\" class='staticlinks'>");
/* 3392 */                                         out.print(FormatUtil.getString("am.webclient.cam.adddeleteattributes.text"));
/* 3393 */                                         out.write("</a> ");
/* 3394 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3395 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3399 */                                     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3400 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                     }
/*      */                                     
/* 3403 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3404 */                                     out.write("</span></td>\n</tr>\n\n\n</table><br></div>\n");
/*      */                                   }
/*      */                                 }
/*      */                               }
/*      */                               
/*      */ 
/* 3410 */                               out.write("\n<br><br>\n\n<!-- Added graphs to the Normal Screen -->\n<div id=\"status\" style='Display:none'>");
/* 3411 */                               out.print(FormatUtil.getString("am.webclient.gengraph.text"));
/* 3412 */                               out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n<div id=\"attributegraphs\"></div>\n\n\n\n<script>\nmyOnLoad();\nfunction myOnLoad()\n{\n/**\t");
/* 3413 */                               if ((request.getParameter("type") != null) && (request.getParameter("type").equals("JBOSS-server"))) {
/* 3414 */                                 out.write("\n\tmyOnJbossLoad();\n\t");
/*      */                               }
/* 3416 */                               out.write("\n\t**/\n\tattributegraphs();\n}\nvar http1;\nfunction attributegraphs()\n{\n        document.getElementById(\"status\").style.display='block';\n        URL='/jsp/cam_graphs.jsp?camIDI=");
/* 3417 */                               out.print(camIDI);
/* 3418 */                               out.write("&haidI=");
/* 3419 */                               out.print(haidI);
/* 3420 */                               out.write("&screenIDI=");
/* 3421 */                               out.print(screenIDI);
/* 3422 */                               out.write("&isfromresourcepage=");
/* 3423 */                               out.print(isFromResourcePage);
/* 3424 */                               out.write("';\n        http1=getHTTPObject();\n        http1.open(\"GET\", URL, true);\n        //http1.onreadystatechange = new Function('if(http1.readyState == 4 && http1.status == 200){document.getElementById(\"status\").innerHTML =\"&nbsp;\",document.getElementById(\"attributegraphs\").innerHTML = http1.responseText;}' );\n\thttp1.onreadystatechange =handleResponse1;\n        http1.send(null);\n}\n\nfunction handleResponse1()\n{\n        if(http1.readyState == 4)\n        {\n                var result = http1.responseText;\n\t\tdocument.getElementById(\"status\").innerHTML =\"&nbsp;\"\n                document.getElementById(\"attributegraphs\").innerHTML = result;\n                document.getElementById(\"attributegraphs\").style.display='block';\n        //      alert('Div similarmonitor display' + document.getElementById(\"multimonitors\").checked);\n        }\n}\n\n\nfunction subAddCustom(linkS) {\n\tlinkS.href = \"");
/* 3425 */                               out.print(configureLink);
/* 3426 */                               out.write("\";\n\treturn true;\n}\n\n$(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 3427 */                               out.write("\n\t{\n\t\t");
/* 3428 */                               if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 3430 */                               out.write(10);
/* 3431 */                               out.write(9);
/* 3432 */                               out.write(9);
/* 3433 */                               if (_jspx_meth_c_005fif_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 3435 */                               out.write("\n\t\tgetCustomFields('");
/* 3436 */                               if (_jspx_meth_c_005fout_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 3438 */                               out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 3439 */                               out.write("\n\t}\n\n});\n</script>\n\n\n");
/* 3440 */                               out.write(10);
/* 3441 */                               out.write(10);
/* 3442 */                               out.write(" \n\n<br>\n\n\n");
/* 3443 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3444 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3447 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3448 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3451 */                           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3452 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                           }
/*      */                           
/* 3455 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3456 */                           out.write(32);
/* 3457 */                           out.write(10);
/* 3458 */                           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 3460 */                           out.write(10);
/* 3461 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3462 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3466 */                       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3467 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */                       }
/*      */                       
/* 3470 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3471 */                       out.write(10);
/* 3472 */                       out.write(10);
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/*      */ 
/* 3478 */                       out.write(10);
/*      */                       
/* 3480 */                       InsertTag _jspx_th_tiles_005finsert_005f1 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 3481 */                       _jspx_th_tiles_005finsert_005f1.setPageContext(_jspx_page_context);
/* 3482 */                       _jspx_th_tiles_005finsert_005f1.setParent(null);
/*      */                       
/* 3484 */                       _jspx_th_tiles_005finsert_005f1.setPage("/jsp/ServerLayout.jsp");
/* 3485 */                       int _jspx_eval_tiles_005finsert_005f1 = _jspx_th_tiles_005finsert_005f1.doStartTag();
/* 3486 */                       if (_jspx_eval_tiles_005finsert_005f1 != 0) {
/*      */                         for (;;) {
/* 3488 */                           out.write(10);
/* 3489 */                           if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f1, _jspx_page_context))
/*      */                             return;
/* 3491 */                           out.write(10);
/* 3492 */                           if (_jspx_meth_tiles_005fput_005f6(_jspx_th_tiles_005finsert_005f1, _jspx_page_context))
/*      */                             return;
/* 3494 */                           out.write(10);
/* 3495 */                           if (_jspx_meth_tiles_005fput_005f7(_jspx_th_tiles_005finsert_005f1, _jspx_page_context))
/*      */                             return;
/* 3497 */                           out.write(10);
/* 3498 */                           if (_jspx_meth_tiles_005fput_005f8(_jspx_th_tiles_005finsert_005f1, _jspx_page_context))
/*      */                             return;
/* 3500 */                           out.write(10);
/* 3501 */                           if (_jspx_meth_tiles_005fput_005f9(_jspx_th_tiles_005finsert_005f1, _jspx_page_context))
/*      */                             return;
/* 3503 */                           out.write("\n<br><br>\n<table width=\"98%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" class=\"grayfullborder\">\n<tr>\n    <td height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3504 */                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 3505 */                           out.write("</span></td>\n    </tr>\n    <tr>\n    <td class=\"bodytext\">\n        <img border=\"0\" align=\"absmiddle\" alt=\"Monitoring Initiated successfully\" src=\"/images/icon_monitor_success.gif\"/>&nbsp;&nbsp;\n\t");
/* 3506 */                           out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 3507 */                           out.write(46);
/* 3508 */                           out.write(32);
/* 3509 */                           out.write(32);
/* 3510 */                           out.print(FormatUtil.getString("am.webclient.cam.associate.attrib.txt"));
/* 3511 */                           out.write("  \n    </td>\n    </tr>\n    </table>\n<br><br>\n<TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n  <tr>\n    <td align=\"center\">  \n      <input name=\"closeButton\" type=\"button\" class=\"buttons\" value=\"");
/* 3512 */                           out.print(FormatUtil.getString("Close Window"));
/* 3513 */                           out.write("\" onClick=\"closePopUpWindow();\">\n      </td>\n      </tr>\n      </table>\n\n");
/* 3514 */                           if (_jspx_meth_tiles_005fput_005f10(_jspx_th_tiles_005finsert_005f1, _jspx_page_context))
/*      */                             return;
/* 3516 */                           out.write(10);
/* 3517 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f1.doAfterBody();
/* 3518 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3522 */                       if (_jspx_th_tiles_005finsert_005f1.doEndTag() == 5) {
/* 3523 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f1); return;
/*      */                       }
/*      */                       
/* 3526 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f1);
/* 3527 */                       out.write(10);
/*      */                     }
/*      */                     
/*      */ 
/* 3531 */                     out.write(10);
/*      */                   }
/* 3533 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3534 */         out = _jspx_out;
/* 3535 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3536 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3537 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3540 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3546 */     PageContext pageContext = _jspx_page_context;
/* 3547 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3549 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3550 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3551 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3553 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3555 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3556 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3557 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3558 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3559 */       return true;
/*      */     }
/* 3561 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3562 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3567 */     PageContext pageContext = _jspx_page_context;
/* 3568 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3570 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3571 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 3572 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3574 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 3576 */     _jspx_th_tiles_005fput_005f0.setValue("Custom Screen");
/* 3577 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 3578 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 3579 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3580 */       return true;
/*      */     }
/* 3582 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3583 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3588 */     PageContext pageContext = _jspx_page_context;
/* 3589 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3591 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3592 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3593 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3595 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3597 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 3598 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3599 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3600 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3601 */       return true;
/*      */     }
/* 3603 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3604 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3609 */     PageContext pageContext = _jspx_page_context;
/* 3610 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3612 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3613 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3614 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3616 */     _jspx_th_tiles_005fput_005f2.setName("ServerLeftArea");
/*      */     
/* 3618 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/cam_leftlinksarea.jsp");
/* 3619 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3620 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3621 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3622 */       return true;
/*      */     }
/* 3624 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3625 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3630 */     PageContext pageContext = _jspx_page_context;
/* 3631 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3633 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 3634 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 3635 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3637 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 3638 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 3640 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 3641 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 3643 */           out.write("\n\t    ");
/* 3644 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 3645 */             return true;
/* 3646 */           out.write(10);
/* 3647 */           out.write(9);
/* 3648 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 3649 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3653 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 3654 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3657 */         int tmp191_190 = 0; int[] tmp191_188 = _jspx_push_body_count_c_005fcatch_005f0; int tmp193_192 = tmp191_188[tmp191_190];tmp191_188[tmp191_190] = (tmp193_192 - 1); if (tmp193_192 <= 0) break;
/* 3658 */         out = _jspx_page_context.popBody(); }
/* 3659 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3661 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 3662 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 3664 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 3669 */     PageContext pageContext = _jspx_page_context;
/* 3670 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3672 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 3673 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 3674 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 3676 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 3678 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 3679 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 3680 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 3681 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3682 */       return true;
/*      */     }
/* 3684 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3685 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3690 */     PageContext pageContext = _jspx_page_context;
/* 3691 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3693 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3694 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3695 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3697 */     _jspx_th_c_005fout_005f1.setValue("${fullvalue}");
/* 3698 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3699 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3700 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3701 */       return true;
/*      */     }
/* 3703 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3704 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3709 */     PageContext pageContext = _jspx_page_context;
/* 3710 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3712 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3713 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3714 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3716 */     _jspx_th_c_005fout_005f2.setValue("${fullvalue}");
/* 3717 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3718 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3719 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3720 */       return true;
/*      */     }
/* 3722 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3723 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3728 */     PageContext pageContext = _jspx_page_context;
/* 3729 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3731 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3732 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3733 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3735 */     _jspx_th_c_005fif_005f2.setTest("${not empty param.haid}");
/* 3736 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3737 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3739 */         out.write(10);
/* 3740 */         out.write(9);
/* 3741 */         out.write(9);
/* 3742 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3743 */           return true;
/* 3744 */         out.write(10);
/* 3745 */         out.write(9);
/* 3746 */         out.write(9);
/* 3747 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3748 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3752 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3753 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3754 */       return true;
/*      */     }
/* 3756 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3757 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3762 */     PageContext pageContext = _jspx_page_context;
/* 3763 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3765 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3766 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3767 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3769 */     _jspx_th_c_005fset_005f0.setVar("myfield_paramresid");
/*      */     
/* 3771 */     _jspx_th_c_005fset_005f0.setScope("page");
/* 3772 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3773 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 3774 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3775 */         out = _jspx_page_context.pushBody();
/* 3776 */         _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3777 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3780 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 3781 */           return true;
/* 3782 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 3783 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3786 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3787 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3790 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3791 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 3792 */       return true;
/*      */     }
/* 3794 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 3795 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3800 */     PageContext pageContext = _jspx_page_context;
/* 3801 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3803 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3804 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3805 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 3807 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 3808 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3809 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3810 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3811 */       return true;
/*      */     }
/* 3813 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3814 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3819 */     PageContext pageContext = _jspx_page_context;
/* 3820 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3822 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3823 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3824 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3826 */     _jspx_th_c_005fif_005f3.setTest("${not empty param.resourceid}");
/* 3827 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3828 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 3830 */         out.write(10);
/* 3831 */         out.write(9);
/* 3832 */         out.write(9);
/* 3833 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 3834 */           return true;
/* 3835 */         out.write(10);
/* 3836 */         out.write(9);
/* 3837 */         out.write(9);
/* 3838 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3839 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3843 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3844 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3845 */       return true;
/*      */     }
/* 3847 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3848 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3853 */     PageContext pageContext = _jspx_page_context;
/* 3854 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3856 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3857 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 3858 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3860 */     _jspx_th_c_005fset_005f1.setVar("myfield_paramresid");
/*      */     
/* 3862 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 3863 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 3864 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 3865 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3866 */         out = _jspx_page_context.pushBody();
/* 3867 */         _jspx_th_c_005fset_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3868 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3871 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 3872 */           return true;
/* 3873 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 3874 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3877 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3878 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3881 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 3882 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 3883 */       return true;
/*      */     }
/* 3885 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 3886 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3891 */     PageContext pageContext = _jspx_page_context;
/* 3892 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3894 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3895 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3896 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 3898 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 3899 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3900 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3901 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3902 */       return true;
/*      */     }
/* 3904 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3905 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3910 */     PageContext pageContext = _jspx_page_context;
/* 3911 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3913 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3914 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3915 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3917 */     _jspx_th_c_005fout_005f5.setValue("${myfield_paramresid}");
/* 3918 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3919 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3920 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3921 */       return true;
/*      */     }
/* 3923 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3924 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3929 */     PageContext pageContext = _jspx_page_context;
/* 3930 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3932 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3933 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3934 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3936 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 3938 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 3939 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3940 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3941 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3942 */       return true;
/*      */     }
/* 3944 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3945 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3950 */     PageContext pageContext = _jspx_page_context;
/* 3951 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3953 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.get(PutTag.class);
/* 3954 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 3955 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f1);
/*      */     
/* 3957 */     _jspx_th_tiles_005fput_005f5.setName("title");
/*      */     
/* 3959 */     _jspx_th_tiles_005fput_005f5.setType("string");
/* 3960 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 3961 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 3962 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 3963 */       return true;
/*      */     }
/* 3965 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 3966 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f6(JspTag _jspx_th_tiles_005finsert_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3971 */     PageContext pageContext = _jspx_page_context;
/* 3972 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3974 */     PutTag _jspx_th_tiles_005fput_005f6 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.get(PutTag.class);
/* 3975 */     _jspx_th_tiles_005fput_005f6.setPageContext(_jspx_page_context);
/* 3976 */     _jspx_th_tiles_005fput_005f6.setParent((Tag)_jspx_th_tiles_005finsert_005f1);
/*      */     
/* 3978 */     _jspx_th_tiles_005fput_005f6.setName("Header");
/*      */     
/* 3980 */     _jspx_th_tiles_005fput_005f6.setType("string");
/* 3981 */     int _jspx_eval_tiles_005fput_005f6 = _jspx_th_tiles_005fput_005f6.doStartTag();
/* 3982 */     if (_jspx_th_tiles_005fput_005f6.doEndTag() == 5) {
/* 3983 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f6);
/* 3984 */       return true;
/*      */     }
/* 3986 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f6);
/* 3987 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f7(JspTag _jspx_th_tiles_005finsert_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3992 */     PageContext pageContext = _jspx_page_context;
/* 3993 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3995 */     PutTag _jspx_th_tiles_005fput_005f7 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.get(PutTag.class);
/* 3996 */     _jspx_th_tiles_005fput_005f7.setPageContext(_jspx_page_context);
/* 3997 */     _jspx_th_tiles_005fput_005f7.setParent((Tag)_jspx_th_tiles_005finsert_005f1);
/*      */     
/* 3999 */     _jspx_th_tiles_005fput_005f7.setName("LeftArea");
/*      */     
/* 4001 */     _jspx_th_tiles_005fput_005f7.setType("string");
/* 4002 */     int _jspx_eval_tiles_005fput_005f7 = _jspx_th_tiles_005fput_005f7.doStartTag();
/* 4003 */     if (_jspx_th_tiles_005fput_005f7.doEndTag() == 5) {
/* 4004 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f7);
/* 4005 */       return true;
/*      */     }
/* 4007 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f7);
/* 4008 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f8(JspTag _jspx_th_tiles_005finsert_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4013 */     PageContext pageContext = _jspx_page_context;
/* 4014 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4016 */     PutTag _jspx_th_tiles_005fput_005f8 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.get(PutTag.class);
/* 4017 */     _jspx_th_tiles_005fput_005f8.setPageContext(_jspx_page_context);
/* 4018 */     _jspx_th_tiles_005fput_005f8.setParent((Tag)_jspx_th_tiles_005finsert_005f1);
/*      */     
/* 4020 */     _jspx_th_tiles_005fput_005f8.setName("ServerLeftArea");
/*      */     
/* 4022 */     _jspx_th_tiles_005fput_005f8.setType("string");
/* 4023 */     int _jspx_eval_tiles_005fput_005f8 = _jspx_th_tiles_005fput_005f8.doStartTag();
/* 4024 */     if (_jspx_th_tiles_005fput_005f8.doEndTag() == 5) {
/* 4025 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f8);
/* 4026 */       return true;
/*      */     }
/* 4028 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f8);
/* 4029 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f9(JspTag _jspx_th_tiles_005finsert_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4034 */     PageContext pageContext = _jspx_page_context;
/* 4035 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4037 */     PutTag _jspx_th_tiles_005fput_005f9 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.get(PutTag.class);
/* 4038 */     _jspx_th_tiles_005fput_005f9.setPageContext(_jspx_page_context);
/* 4039 */     _jspx_th_tiles_005fput_005f9.setParent((Tag)_jspx_th_tiles_005finsert_005f1);
/*      */     
/* 4041 */     _jspx_th_tiles_005fput_005f9.setName("UserArea");
/*      */     
/* 4043 */     _jspx_th_tiles_005fput_005f9.setType("string");
/* 4044 */     int _jspx_eval_tiles_005fput_005f9 = _jspx_th_tiles_005fput_005f9.doStartTag();
/* 4045 */     if (_jspx_th_tiles_005fput_005f9.doEndTag() == 5) {
/* 4046 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f9);
/* 4047 */       return true;
/*      */     }
/* 4049 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f9);
/* 4050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f10(JspTag _jspx_th_tiles_005finsert_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4055 */     PageContext pageContext = _jspx_page_context;
/* 4056 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4058 */     PutTag _jspx_th_tiles_005fput_005f10 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.get(PutTag.class);
/* 4059 */     _jspx_th_tiles_005fput_005f10.setPageContext(_jspx_page_context);
/* 4060 */     _jspx_th_tiles_005fput_005f10.setParent((Tag)_jspx_th_tiles_005finsert_005f1);
/*      */     
/* 4062 */     _jspx_th_tiles_005fput_005f10.setName("footer");
/*      */     
/* 4064 */     _jspx_th_tiles_005fput_005f10.setType("string");
/* 4065 */     int _jspx_eval_tiles_005fput_005f10 = _jspx_th_tiles_005fput_005f10.doStartTag();
/* 4066 */     if (_jspx_th_tiles_005fput_005f10.doEndTag() == 5) {
/* 4067 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f10);
/* 4068 */       return true;
/*      */     }
/* 4070 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f10);
/* 4071 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\cam_005fshowcustomscreen_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */