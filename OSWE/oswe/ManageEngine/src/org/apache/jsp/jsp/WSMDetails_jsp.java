/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.client.wsm.WSMGraph;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
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
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class WSMDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  666 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
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
/*  838 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/*  853 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  854 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  857 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  858 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  859 */       set = AMConnectionPool.executeQueryStmt(query);
/*  860 */       if (set.next())
/*      */       {
/*  862 */         String helpLink = set.getString("LINK");
/*  863 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
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
/*  897 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
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
/*  911 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/* 1472 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
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
/*      */           catch (java.sql.SQLException e) {
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
/* 1983 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
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
/* 2176 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2182 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/* 2183 */   static { _jspx_dependants.put("/jsp/MyField_div.jsp", Long.valueOf(1473429417000L));
/* 2184 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2185 */     _jspx_dependants.put("/jsp/MyField_trstrip.jsp", Long.valueOf(1473429417000L));
/* 2186 */     _jspx_dependants.put("/jsp/wsmbreadcrumb.jsp", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2206 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2210 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2223 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2227 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2228 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2232 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2233 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2234 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2235 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2236 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2245 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2248 */     JspWriter out = null;
/* 2249 */     Object page = this;
/* 2250 */     JspWriter _jspx_out = null;
/* 2251 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2255 */       response.setContentType("text/html;charset=UTF-8");
/* 2256 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2258 */       _jspx_page_context = pageContext;
/* 2259 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2260 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2261 */       session = pageContext.getSession();
/* 2262 */       out = pageContext.getOut();
/* 2263 */       _jspx_out = out;
/*      */       
/* 2265 */       out.write("<!--$Id$-->\n\n");
/*      */       
/* 2267 */       request.setAttribute("HelpKey", "Monitoring Web Service Details");
/*      */       
/* 2269 */       out.write("\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n\n\n\n\n\n\n\n\n\n  \n\n");
/* 2270 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2272 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2273 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2274 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2276 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2278 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2280 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2282 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2283 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2284 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2285 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2288 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2289 */         String available = null;
/* 2290 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2291 */         out.write(10);
/*      */         
/* 2293 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2294 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2295 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2297 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2299 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2301 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2303 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2304 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2305 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2306 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2309 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2310 */           String unavailable = null;
/* 2311 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2312 */           out.write(10);
/*      */           
/* 2314 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2315 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2316 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2318 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2320 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2322 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2324 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2325 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2326 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2327 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2330 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2331 */             String unmanaged = null;
/* 2332 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2333 */             out.write(10);
/*      */             
/* 2335 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2336 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2337 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2339 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2341 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2343 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2345 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2346 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2347 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2348 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2351 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2352 */               String scheduled = null;
/* 2353 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2354 */               out.write(10);
/*      */               
/* 2356 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2357 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2358 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2360 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2362 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2364 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2366 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2367 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2368 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2369 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2372 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2373 */                 String critical = null;
/* 2374 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2375 */                 out.write(10);
/*      */                 
/* 2377 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2378 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2379 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2381 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2383 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2385 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2387 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2388 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2389 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2390 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2393 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2394 */                   String clear = null;
/* 2395 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2396 */                   out.write(10);
/*      */                   
/* 2398 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2399 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2400 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2402 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2404 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2406 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2408 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2409 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2410 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2411 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2414 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2415 */                     String warning = null;
/* 2416 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2417 */                     out.write(10);
/* 2418 */                     out.write(10);
/*      */                     
/* 2420 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2421 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2423 */                     out.write(10);
/* 2424 */                     out.write(10);
/* 2425 */                     out.write(10);
/* 2426 */                     out.write(10);
/* 2427 */                     out.write(10);
/* 2428 */                     WSMGraph wsmGraph = null;
/* 2429 */                     wsmGraph = (WSMGraph)_jspx_page_context.getAttribute("wsmGraph", 2);
/* 2430 */                     if (wsmGraph == null) {
/* 2431 */                       wsmGraph = new WSMGraph();
/* 2432 */                       _jspx_page_context.setAttribute("wsmGraph", wsmGraph, 2);
/*      */                     }
/* 2434 */                     out.write(10);
/* 2435 */                     com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2436 */                     wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 2);
/* 2437 */                     if (wlsGraph == null) {
/* 2438 */                       wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2439 */                       _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 2);
/*      */                     }
/* 2441 */                     out.write("\n\n<head>\n\n<script>\nfunction showArgs(oid1,insid1)\n{\n\tvar urlstr = \"/wsm.do?method=showargs&oid=\"+oid1+\"&insid=\"+insid1;\n\tvar win = window.open(urlstr,\"Arguments\",\"resizable=yes,scrollbars=yes,width=400,height=400,top=200,left=300\");\n\twin.focus();\n}\n\nfunction showInstanceGraph(resid,insid)\n{\n\tMM_openBrWindow('/jsp/wsmigraph.jsp?resourceid='+resid+'&instanceid='+insid,'ExecutionTimeStatistic','width=600,height=300,top=200,left=200,scrollbars=yes,resizable=yes');\n}\n\nfunction editwsm()\n{\n\t\n\t");
/* 2442 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2444 */                     out.write("\n\t\n\tvar poll = trimAll(document.forms.edit.pollinterval.value);\n\tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0' )\n\t{\n\t\talert(\"Polling Interval should be a number greater than 0.\");\n\t\treturn;\n\t}\n\tdocument.forms.edit.submit();\n}\n\nfunction showXMLContent(ele,event,operationid)\n{\n\tddrivetip(ele,event,document.getElementById(operationid).innerHTML,null,true,'#000000')\n}\n\n\n</script>    \n\n</head>\n");
/*      */                     
/* 2446 */                     String seven_days_text = FormatUtil.getString("am.webclient.common.sevendays.tooltip.text");
/* 2447 */                     String thiry_days_text = FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text");
/*      */                     
/* 2449 */                     String resid = request.getParameter("resourceid");
/* 2450 */                     HashMap configProps = (HashMap)request.getAttribute("WSProps");
/* 2451 */                     ArrayList<Properties> data = (ArrayList)request.getAttribute("WSOpData");
/*      */                     
/* 2453 */                     ArrayList attribIDs = new ArrayList();
/* 2454 */                     ArrayList resIDs = (ArrayList)request.getAttribute("resids");
/*      */                     
/* 2456 */                     for (int i = 3501; i < 3507; i++)
/*      */                     {
/* 2458 */                       attribIDs.add("" + i);
/*      */                     }
/* 2460 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2461 */                     System.out.println("Alert Properties\n" + alert);
/* 2462 */                     String encodeurl = URLEncoder.encode("/showresource.do?haid=" + request.getParameter("haid") + "&type=Web Service&method=showdetails&resourceid=" + resid);
/*      */                     
/* 2464 */                     wlsGraph.setParam(resid, "AVAILABILITY");
/*      */                     
/* 2466 */                     out.write(10);
/* 2467 */                     out.write(10);
/* 2468 */                     out.write("<!--$Id$-->\n\n\n\n\n\n\n\n  \n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n\t");
/*      */                     
/* 2470 */                     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2471 */                     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2472 */                     _jspx_th_c_005fif_005f0.setParent(null);
/*      */                     
/* 2474 */                     _jspx_th_c_005fif_005f0.setTest("${param.method=='showdetails'}");
/* 2475 */                     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2476 */                     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                       for (;;) {
/* 2478 */                         out.write("\t\n       <td class=\"bcsign\" height=\"22\">");
/* 2479 */                         out.print(BreadcrumbUtil.getMonitorsPage());
/* 2480 */                         out.write(" &gt; ");
/* 2481 */                         out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 2482 */                         out.write(" &gt; <span class=\"bcactive\">");
/* 2483 */                         out.print(request.getAttribute("displayname"));
/* 2484 */                         out.write(" </span></td>\n\t");
/* 2485 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2486 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2490 */                     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2491 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */                     }
/*      */                     else {
/* 2494 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2495 */                       out.write(10);
/* 2496 */                       out.write(9);
/*      */                       
/* 2498 */                       IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2499 */                       _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2500 */                       _jspx_th_c_005fif_005f1.setParent(null);
/*      */                       
/* 2502 */                       _jspx_th_c_005fif_005f1.setTest("${param.method=='manageoperations'}");
/* 2503 */                       int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2504 */                       if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                         for (;;) {
/* 2506 */                           out.write("\n\t<td class=\"bcsign\" height=\"22\">\n      ");
/* 2507 */                           out.print(BreadcrumbUtil.getMonitorsPage());
/* 2508 */                           out.write(" &gt; ");
/* 2509 */                           out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 2510 */                           out.write(" &gt; <a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2511 */                           if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                             return;
/* 2513 */                           out.write(34);
/* 2514 */                           out.write(62);
/* 2515 */                           out.print(request.getAttribute("displayname"));
/* 2516 */                           out.write("</a> &gt; <span class=\"bcactive\">");
/* 2517 */                           out.print(FormatUtil.getString("am.webclient.wsm.manageoperationsbc.text"));
/* 2518 */                           out.write("</span>\n      </td>\n\t");
/* 2519 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2520 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2524 */                       if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2525 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */                       }
/*      */                       else {
/* 2528 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2529 */                         out.write(10);
/* 2530 */                         out.write(10);
/* 2531 */                         out.write(9);
/*      */                         
/* 2533 */                         IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2534 */                         _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2535 */                         _jspx_th_c_005fif_005f2.setParent(null);
/*      */                         
/* 2537 */                         _jspx_th_c_005fif_005f2.setTest("${param.method=='showoperations'}");
/* 2538 */                         int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2539 */                         if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                           for (;;) {
/* 2541 */                             out.write("\n\t<td class=\"bcsign\" height=\"22\">\n      ");
/* 2542 */                             out.print(BreadcrumbUtil.getMonitorsPage());
/* 2543 */                             out.write(" &gt; ");
/* 2544 */                             out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 2545 */                             out.write(" &gt; <a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2546 */                             if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                               return;
/* 2548 */                             out.write(34);
/* 2549 */                             out.write(62);
/* 2550 */                             out.print(request.getAttribute("displayname"));
/* 2551 */                             out.write("</a> &gt; <span class=\"bcactive\">");
/* 2552 */                             out.print(FormatUtil.getString("am.webclient.wsm.showoperationsbc.text"));
/* 2553 */                             out.write("</span>\n      </td>\n\t");
/* 2554 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2555 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2559 */                         if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2560 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */                         }
/*      */                         else {
/* 2563 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2564 */                           out.write(10);
/* 2565 */                           out.write(9);
/*      */                           
/* 2567 */                           IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2568 */                           _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2569 */                           _jspx_th_c_005fif_005f3.setParent(null);
/*      */                           
/* 2571 */                           _jspx_th_c_005fif_005f3.setTest("${param.method=='getSOAPInfo'}");
/* 2572 */                           int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2573 */                           if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                             for (;;) {
/* 2575 */                               out.write("\n\t  <td class=\"bcsign\" height=\"22\">\n\t   \t");
/* 2576 */                               out.print(BreadcrumbUtil.getMonitorsPage());
/* 2577 */                               out.write(" &gt; ");
/* 2578 */                               out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 2579 */                               out.write(" &gt; <a class=\"bcinactive\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2580 */                               if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                 return;
/* 2582 */                               out.write(34);
/* 2583 */                               out.write(62);
/* 2584 */                               out.print(request.getAttribute("displayname"));
/* 2585 */                               out.write(" </a> &gt; <span class=\"bcactive\">");
/* 2586 */                               out.print(request.getAttribute("operationName"));
/* 2587 */                               out.write("</span>\n\t");
/* 2588 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2589 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2593 */                           if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2594 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*      */                           }
/*      */                           else {
/* 2597 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2598 */                             out.write("\n</tr>\n\t<tr>\n\t\t<td  class=\"bcstrip\" height=\"2\"><img src=\"../images/spacer.gif\" width=\"20\" height=\"2px\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\t\n");
/* 2599 */                             out.write("\n\n<div id=\"Reconfigure\" style=\"display:none\">\n<form name=\"edit\" action=\"/wsm.do\" >\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2600 */                             out.print(resid);
/* 2601 */                             out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"editmonitor\">\n<table border=\"0\" class=\"lrtbdarkborder\" cellspacing=\"0\" width=\"100%\">\n<tr class=\"tableheadingbborder\" valign=\"middle\"><td colspan=\"2\">");
/* 2602 */                             out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2603 */                             out.write("</td></tr>\n<tr class=\"whitegrayborder\"><td width=\"30%\">");
/* 2604 */                             out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 2605 */                             out.write("&nbsp;<span class=\"mandatory\">*</span></td>\n<td><input type=\"text\" name=\"displayname\" size=\"60\" class=\"formtext\" value=\"");
/* 2606 */                             out.print(request.getAttribute("displayname"));
/* 2607 */                             out.write("\"></td></tr>\n<tr class=\"whitegrayborder\"><td width=\"30%\">");
/* 2608 */                             out.print(FormatUtil.getString("am.webclient.wsm.endpoint.text"));
/* 2609 */                             out.write("&nbsp;<span class=\"mandatory\">*</span></td>\n<td><input type=\"text\" name=\"endpoint\" size=\"60\" class=\"formtext\" value=\"");
/* 2610 */                             out.print(configProps.get("endpoint"));
/* 2611 */                             out.write("\"></td></tr>\n<tr class=\"whitegrayborder\"><td width=\"30%\">");
/* 2612 */                             out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 2613 */                             out.write("&nbsp;<span class=\"mandatory\">*</span></td>\n<td><input type=\"text\" name=\"pollinterval\" size=\"5\" class=\"formtext\" value=\"");
/* 2614 */                             out.print(configProps.get("pollinterval"));
/* 2615 */                             out.write("\">&nbsp;");
/* 2616 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.minutes"));
/* 2617 */                             out.write("</td></tr>\n<tr class=\"whitegrayborder\"><td>");
/* 2618 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.timeout"));
/* 2619 */                             out.write("</td>\n<td><input type=\"text\" name=\"timeout\" size=\"5\" class=\"formtext\" value=\"");
/* 2620 */                             out.print(configProps.get("timeout"));
/* 2621 */                             out.write("\">&nbsp;");
/* 2622 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.second"));
/* 2623 */                             out.write("</td></tr>\n<tr class=\"whitegrayborder\"><td>");
/* 2624 */                             out.print(FormatUtil.getString("am.webclient.common.username.text"));
/* 2625 */                             out.write("</td>\n<td><input type=\"text\" name=\"username\" size=\"20\" class=\"formtext\" value=\"");
/* 2626 */                             out.print(configProps.get("username"));
/* 2627 */                             out.write("\"></td></tr>\n<tr class=\"whitegrayborder\"><td>");
/* 2628 */                             out.print(FormatUtil.getString("am.webclient.common.password.text"));
/* 2629 */                             out.write("</td>\n<td><input type=\"password\" name=\"password\" size=\"20\" class=\"formtext\" value=\"");
/* 2630 */                             out.print(configProps.get("password"));
/* 2631 */                             out.write("\"></td></tr>\n<tr class=\"whitegrayborder\">\n\t<td>\n\t\t");
/* 2632 */                             out.print(FormatUtil.getString("Headers"));
/* 2633 */                             out.write("\n\t</td>\n\t<td>\n\t\t<input type=\"text\" name=\"customHeaders\" size=\"20\" class=\"formtext\" value=\"");
/* 2634 */                             out.print(configProps.get("customHeaders"));
/* 2635 */                             out.write("\">\n\t</td>\n</tr>\n<tr class=\"whitegrayborder\">\n\t<td>\n\t\t");
/* 2636 */                             out.print(FormatUtil.getString("Authentication Token"));
/* 2637 */                             out.write("\n\t</td>\n\t<td>\n\t\t<input type=\"text\" name=\"tokenAndOperation\" size=\"20\" class=\"formtext\" value=\"");
/* 2638 */                             out.print(configProps.get("tokenAndOperation"));
/* 2639 */                             out.write("\">\n\t</td>\n</tr>\n<tr class=\"tablebottom\">\n<td colspan=\"2\" style=\"padding-left:300px\"> \n<input  type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 2640 */                             out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 2641 */                             out.write("\" onClick=\"editwsm()\">\n<input type=\"button\" class=\"buttons btn_link\" value=\"");
/* 2642 */                             out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2643 */                             out.write("\" onClick=\"toggleDiv('Reconfigure')\">\n</td>\n</tr>\n</table>\n</form>\n<br>\n</div>\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" align=\"center\">\n<tr>\n<td width=\"55%\" valign=\"top\">\n\t<table width=\"99%\" class=\"lrtbdarkborder\" cellspacing=\"1\" cellpadding=\"1\" border=\"0\">\n    <tr><td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2644 */                             out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2645 */                             out.write("</td></tr>\n\t<tr>\n\t<td class=\"monitorinfoodd\" width=\"20%\">");
/* 2646 */                             out.print(FormatUtil.getString("am.webclient.wsm.host.text"));
/* 2647 */                             out.write("</td>\n\t<td class=\"monitorinfoodd\" width=\"80%\">");
/* 2648 */                             out.print(configProps.get("host"));
/* 2649 */                             out.write("</td>\n\t</tr>\n\t<tr >\n\t<td class=\"monitorinfoodd\">");
/* 2650 */                             out.print(FormatUtil.getString("am.webclient.wsm.port.text"));
/* 2651 */                             out.write("</td>\n\t<td class=\"monitorinfoodd\">");
/* 2652 */                             out.print(configProps.get("port"));
/* 2653 */                             out.write("</td>\n\t</tr>\n\t<tr>\n\t<td class=\"monitorinfoodd\">");
/* 2654 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2655 */                             out.write("</td>\n\t<td class=\"monitorinfoodd\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2656 */                             out.print(resid);
/* 2657 */                             out.write("&attributeid=3502')\">");
/* 2658 */                             out.print(getSeverityImageForHealth(alert.getProperty(resid + "#" + "3502")));
/* 2659 */                             out.write("</a>\n\t");
/* 2660 */                             out.print(getHideAndShowRCAMessage(alert.getProperty(resid + "#" + "3502" + "#" + "MESSAGE"), "3502", alert.getProperty(resid + "#" + "3502"), resid));
/* 2661 */                             out.write(10);
/* 2662 */                             out.write(9);
/* 2663 */                             if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resid, "3502") != 0) {
/* 2664 */                               out.write("\n\t<br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2665 */                               out.print(resid + "_3502");
/* 2666 */                               out.write("')\">");
/* 2667 */                               out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2668 */                               out.write("</a></span>\n     ");
/*      */                             }
/* 2670 */                             out.write("\n\t</td>\n\t</tr>\n\t<tr>\n\t<td class=\"monitorinfoodd\">");
/* 2671 */                             out.print(FormatUtil.getString("am.webclient.wsm.servicename.text"));
/* 2672 */                             out.write("</td>\n\t<td class=\"monitorinfoodd\">");
/* 2673 */                             out.print(configProps.get("serviceName"));
/* 2674 */                             out.write("</td>\n\t</tr>\n\t<tr>\n\t<td class=\"monitorinfoodd\">");
/* 2675 */                             out.print(FormatUtil.getString("am.webclient.wsm.endpoint.text"));
/* 2676 */                             out.write("\n\t</td>\n\t<td class=\"monitorinfoodd\" title=\"");
/* 2677 */                             out.print(configProps.get("endpoint"));
/* 2678 */                             out.write("\">\n\t<a class=\"staticlinks\" style=\"text-decoration:none;\" href=\"javascript:MM_openBrWindow('");
/* 2679 */                             out.print((String)configProps.get("endpoint"));
/* 2680 */                             out.write("','extDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\"> <span class=\"bodytext\">");
/* 2681 */                             out.print(getTrimmedText((String)configProps.get("endpoint"), 50));
/* 2682 */                             out.write("<span></a></td>\n\t</tr>\n\t<tr>\n\t<td class=\"monitorinfoodd\">");
/* 2683 */                             out.print(FormatUtil.getString("am.webclient.wsm.wsdlpath.text"));
/* 2684 */                             out.write("</td>\n\t<td class=\"monitorinfoodd\" title=\"");
/* 2685 */                             out.print(configProps.get("wsdlpath"));
/* 2686 */                             out.write("\">\n\t<a class=\"staticlinks\" style=\"text-decoration:none;\" href=\"javascript:MM_openBrWindow('");
/* 2687 */                             out.print((String)configProps.get("wsdlpath"));
/* 2688 */                             out.write("','extDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\"> <span class=\"bodytext\">");
/* 2689 */                             out.print(getTrimmedText((String)configProps.get("wsdlpath"), 50));
/* 2690 */                             out.write("<span></a></td>\n\t</tr>\n\t<tr>\n\t<td class=\"monitorinfoodd\">");
/* 2691 */                             out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2692 */                             out.write("</td>\n\t");
/* 2693 */                             String lastpoll = (String)configProps.get("lastpoll");
/* 2694 */                             if (!lastpoll.equals("0"))
/*      */                             {
/* 2696 */                               out.write("\n\t<td class=\"monitorinfoodd\">");
/* 2697 */                               out.print(formatDT(lastpoll));
/* 2698 */                               out.write("</td>\n\t");
/*      */                             }
/*      */                             else
/*      */                             {
/* 2702 */                               out.write("\n\t<td class=\"monitorinfoodd\">-</td>\n\t");
/*      */                             }
/* 2704 */                             out.write("\n\t</tr>\n\t<tr>\n\t<td class=\"monitorinfoodd\">");
/* 2705 */                             out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2706 */                             out.write("</td>\n\t");
/* 2707 */                             String nextpoll = (String)configProps.get("nextpoll");
/* 2708 */                             if (!nextpoll.equals("0"))
/*      */                             {
/* 2710 */                               out.write("\n\t<td class=\"monitorinfoodd\">");
/* 2711 */                               out.print(formatDT(nextpoll));
/* 2712 */                               out.write("</td>\n\t");
/*      */                             }
/*      */                             else
/*      */                             {
/* 2716 */                               out.write("\n\t<td class=\"monitorinfoodd\">-</td>\n\t");
/*      */                             }
/* 2718 */                             out.write("\n\t</tr>\n\t");
/* 2719 */                             out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n<script>\n $(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 2720 */                             out.write("\n\t{\n\t\t");
/* 2721 */                             if (_jspx_meth_c_005fif_005f4(_jspx_page_context))
/*      */                               return;
/* 2723 */                             out.write(10);
/* 2724 */                             out.write(9);
/* 2725 */                             out.write(9);
/* 2726 */                             if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
/*      */                               return;
/* 2728 */                             out.write("\n\t\tgetCustomFields('");
/* 2729 */                             if (_jspx_meth_c_005fout_005f5(_jspx_page_context))
/*      */                               return;
/* 2731 */                             out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 2732 */                             out.write("\n\t}\n\n});\n</script>\n");
/* 2733 */                             if (_jspx_meth_c_005fif_005f6(_jspx_page_context))
/*      */                               return;
/* 2735 */                             out.write(10);
/* 2736 */                             out.write(10);
/* 2737 */                             if (_jspx_meth_c_005fif_005f7(_jspx_page_context))
/*      */                               return;
/* 2739 */                             out.write(10);
/* 2740 */                             out.write(10);
/* 2741 */                             if (_jspx_meth_c_005fset_005f4(_jspx_page_context))
/*      */                               return;
/* 2743 */                             out.write(10);
/* 2744 */                             if (_jspx_meth_c_005fset_005f5(_jspx_page_context))
/*      */                               return;
/* 2746 */                             out.write(10);
/* 2747 */                             out.write(10);
/* 2748 */                             out.write(10);
/* 2749 */                             if (_jspx_meth_c_005fif_005f8(_jspx_page_context))
/*      */                               return;
/* 2751 */                             out.write(10);
/* 2752 */                             out.write(10);
/* 2753 */                             out.write(10);
/* 2754 */                             if (_jspx_meth_c_005fif_005f9(_jspx_page_context))
/*      */                               return;
/* 2756 */                             out.write("\n\n\n<tr>\n<td colspan=\"2\" class=\"");
/* 2757 */                             if (_jspx_meth_c_005fout_005f12(_jspx_page_context))
/*      */                               return;
/* 2759 */                             out.write("\" align=\"right\" style=\"padding:2px;\">\n<input type=\"button\" value=\"");
/* 2760 */                             if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */                               return;
/* 2762 */                             out.write("\" onclick=\"getCustomFields('");
/* 2763 */                             if (_jspx_meth_c_005fout_005f13(_jspx_page_context))
/*      */                               return;
/* 2765 */                             out.write(39);
/* 2766 */                             out.write(44);
/* 2767 */                             out.write(39);
/* 2768 */                             if (_jspx_meth_c_005fout_005f14(_jspx_page_context))
/*      */                               return;
/* 2770 */                             out.write("',false,'CustomFieldValues',false);\" class=\"buttons btn_custom\"/>");
/* 2771 */                             out.write("\n</td>\n</tr>\n\n\n");
/* 2772 */                             out.write("\n\t</table>\n</td>\n<td  width=\"40%\" valign=\"top\" align=\"right\">\n\t<table width=\"100%\" class=\"lrtbdarkborder\" cellspacing=\"0\">\n\t<tr><td class=\"tableheadingbborder\" align=\"center\">");
/* 2773 */                             out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 2774 */                             out.write("</td></tr>\n\t<tr>\n\t<td align=\"right\">\n\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2775 */                             if (_jspx_meth_c_005fout_005f15(_jspx_page_context))
/*      */                               return;
/* 2777 */                             out.write("&period=1&resourcename=");
/* 2778 */                             if (_jspx_meth_c_005fout_005f16(_jspx_page_context))
/*      */                               return;
/* 2780 */                             out.write("')\">\n\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2781 */                             out.print(seven_days_text);
/* 2782 */                             out.write("\"></a>\n\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2783 */                             if (_jspx_meth_c_005fout_005f17(_jspx_page_context))
/*      */                               return;
/* 2785 */                             out.write("&period=2&resourcename=");
/* 2786 */                             if (_jspx_meth_c_005fout_005f18(_jspx_page_context))
/*      */                               return;
/* 2788 */                             out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2789 */                             out.print(thiry_days_text);
/* 2790 */                             out.write("\"></a>\n\t</td>\n\t</tr>\n\t<tr>\n\t<td style=\"height:170px\" align=\"center\">\n\t");
/*      */                             
/* 2792 */                             AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 2793 */                             _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 2794 */                             _jspx_th_awolf_005fpiechart_005f0.setParent(null);
/*      */                             
/* 2796 */                             _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                             
/* 2798 */                             _jspx_th_awolf_005fpiechart_005f0.setWidth("250");
/*      */                             
/* 2800 */                             _jspx_th_awolf_005fpiechart_005f0.setHeight("200");
/*      */                             
/* 2802 */                             _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                             
/* 2804 */                             _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                             
/* 2806 */                             _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                             
/* 2808 */                             _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 2809 */                             int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 2810 */                             if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 2811 */                               if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 2812 */                                 out = _jspx_page_context.pushBody();
/* 2813 */                                 _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 2814 */                                 _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2817 */                                 out.write(32);
/* 2818 */                                 out.write(10);
/* 2819 */                                 out.write(9);
/*      */                                 
/* 2821 */                                 Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 2822 */                                 _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 2823 */                                 _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                                 
/* 2825 */                                 _jspx_th_awolf_005fmap_005f0.setId("color");
/* 2826 */                                 int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 2827 */                                 if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 2828 */                                   if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 2829 */                                     out = _jspx_page_context.pushBody();
/* 2830 */                                     _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 2831 */                                     _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 2834 */                                     out.write(32);
/* 2835 */                                     out.write(10);
/* 2836 */                                     out.write(9);
/*      */                                     
/* 2838 */                                     AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2839 */                                     _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 2840 */                                     _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                     
/* 2842 */                                     _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                                     
/* 2844 */                                     _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 2845 */                                     int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 2846 */                                     if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 2847 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                     }
/*      */                                     
/* 2850 */                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 2851 */                                     out.write(32);
/* 2852 */                                     out.write(10);
/* 2853 */                                     out.write(9);
/*      */                                     
/* 2855 */                                     AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2856 */                                     _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 2857 */                                     _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                     
/* 2859 */                                     _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                                     
/* 2861 */                                     _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 2862 */                                     int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 2863 */                                     if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 2864 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                     }
/*      */                                     
/* 2867 */                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 2868 */                                     out.write(32);
/* 2869 */                                     out.write(10);
/* 2870 */                                     out.write(9);
/* 2871 */                                     int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 2872 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 2875 */                                   if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 2876 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 2879 */                                 if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 2880 */                                   this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                                 }
/*      */                                 
/* 2883 */                                 this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 2884 */                                 out.write(32);
/* 2885 */                                 out.write(10);
/* 2886 */                                 out.write(9);
/* 2887 */                                 int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 2888 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2891 */                               if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 2892 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2895 */                             if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 2896 */                               this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/*      */                             }
/*      */                             else {
/* 2899 */                               this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 2900 */                               out.write("\n\t</td>\n\t</tr>\n\t<tr>\n\t<td class=\"yellowgrayborder\" height=\"36\">\n\t\t<table border=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t<tr>\n\t\t<td>\n\t\t<span class=\"bodytext\">\n\t\t");
/* 2901 */                               out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 2902 */                               out.write(" :<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2903 */                               out.print(resid);
/* 2904 */                               out.write("&attributeid=3501&alertconfigurl=");
/* 2905 */                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resid + "&attributeIDs=3501&attributeToSelect=3501&redirectto=" + encodeurl));
/* 2906 */                               out.write("')\"></span>\n                ");
/* 2907 */                               out.print(getSeverityImageForAvailability(alert.getProperty(resid + "#" + "3501")));
/* 2908 */                               out.write("</a>\n\t\t</td>\n\t\t<td align=\"right\">\n\t\t<img src=\"/images/icon_associateaction.gif\" border=\"0\" align=\"absmiddle\">&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2909 */                               if (_jspx_meth_c_005fout_005f19(_jspx_page_context))
/*      */                                 return;
/* 2911 */                               out.write("&attributeIDs=3501,3502&attributeToSelect=3501&redirectto=");
/* 2912 */                               out.print(encodeurl);
/* 2913 */                               out.write("' class=\"links\"><span class=\"bodytext\">");
/* 2914 */                               out.print(ALERTCONFIG_TEXT);
/* 2915 */                               out.write("</span></a>\n\t\t</td>\n\t\t</tr>\n\t\t</table>\n\t</td>\n\t</tr>\n\t</table>\n</td>\n</tr>\n</table>\n <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 2916 */                               out.write("<!--$Id$-->\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td>\n<div id=\"customfieldsfullListDiv\" style='overflow: auto; display:none; width: 100%;'>\n</div>\n<div id=\"customfieldsloadingdiv\" style='text-align:center;height:200px;width: 100%;display: none;'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\n</td></tr></table>\n");
/* 2917 */                               out.write("</td></tr></table>\n");
/* 2918 */                               if (configProps.get("urlexecutiontime") != null)
/*      */                               {
/*      */ 
/* 2921 */                                 out.write("\n<br>\n");
/*      */                                 
/* 2923 */                                 wsmGraph.setParameter(resid, "urlresponsetime");
/*      */                                 
/* 2925 */                                 out.write("\n<table class=\"lrtbdarkborder\" width=\"99%\" cellspacing=\"0\" align=\"center\">\n<tr>\n<td colspan=\"2\"  class=\"tableheadingbborder\">");
/* 2926 */                                 out.print(FormatUtil.getString("am.webclient.wsm.wsdlresponsetime.text"));
/* 2927 */                                 out.write("</td>\n</tr>\n<tr>\n            <td width=\"95%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2928 */                                 if (_jspx_meth_c_005fout_005f20(_jspx_page_context))
/*      */                                   return;
/* 2930 */                                 out.write("&attributeid=3503&period=-7&resourcename=");
/* 2931 */                                 if (_jspx_meth_c_005fout_005f21(_jspx_page_context))
/*      */                                   return;
/* 2933 */                                 out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2934 */                                 out.print(seven_days_text);
/* 2935 */                                 out.write("\"></a></td>\n\t    <td width=\"5%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2936 */                                 if (_jspx_meth_c_005fout_005f22(_jspx_page_context))
/*      */                                   return;
/* 2938 */                                 out.write("&attributeid=3503&period=-30&resourcename=");
/* 2939 */                                 if (_jspx_meth_c_005fout_005f23(_jspx_page_context))
/*      */                                   return;
/* 2941 */                                 out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2942 */                                 out.print(thiry_days_text);
/* 2943 */                                 out.write("\"></a></td>\t\t\n\t</tr>\n<tr>\n<td align=\"center\">\n");
/*      */                                 
/* 2945 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 2946 */                                 _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 2947 */                                 _jspx_th_awolf_005ftimechart_005f0.setParent(null);
/*      */                                 
/* 2949 */                                 _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("wsmGraph");
/*      */                                 
/* 2951 */                                 _jspx_th_awolf_005ftimechart_005f0.setWidth("550");
/*      */                                 
/* 2953 */                                 _jspx_th_awolf_005ftimechart_005f0.setHeight("175");
/*      */                                 
/* 2955 */                                 _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                                 
/* 2957 */                                 _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                 
/* 2959 */                                 _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.wsm.wsdlresponsetime.text"));
/* 2960 */                                 int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 2961 */                                 if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 2962 */                                   if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 2963 */                                     out = _jspx_page_context.pushBody();
/* 2964 */                                     _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 2965 */                                     _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 2968 */                                     out.write(32);
/* 2969 */                                     out.write(10);
/* 2970 */                                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 2971 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 2974 */                                   if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 2975 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 2978 */                                 if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 2979 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                                 }
/*      */                                 
/* 2982 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 2983 */                                 out.write(" \n</td>\n</tr>\n<tr>\n<td align=\"center\">\n<table width=\"50%\" border=\"0\" class=\"lrbtborder\" cellspacing=\"0\">\n<tr>\n<td class=\"columnheadingnotop\">");
/* 2984 */                                 if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */                                   return;
/* 2986 */                                 out.write("</td>\n<td class=\"columnheadingnotop\" align=\"center\">");
/* 2987 */                                 if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*      */                                   return;
/* 2989 */                                 out.write("</td>\n<td class=\"columnheadingnotop\" align=\"center\">");
/* 2990 */                                 if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*      */                                   return;
/* 2992 */                                 out.write("</td>\n</tr>\n<tr class=\"whitegrayborder\">\n<td>");
/* 2993 */                                 out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 2994 */                                 out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 2995 */                                 out.write("</td>\n<td align=\"center\">");
/* 2996 */                                 out.print(configProps.get("urlexecutiontime"));
/* 2997 */                                 out.write("&nbsp;");
/* 2998 */                                 out.print(FormatUtil.getString("ms"));
/* 2999 */                                 out.write("</td>\n<td align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3000 */                                 out.print(resid);
/* 3001 */                                 out.write("&attributeid=3503')\">");
/* 3002 */                                 out.print(getSeverityImage(alert.getProperty(resid + "#" + "3503")));
/* 3003 */                                 out.write("</a></td>\n</tr>\n<tr>\n<td colspan=\"3\" align=\"right\" class=\"yellowgrayborder\">\n<img src=\"/images/icon_associateaction.gif\" border=\"0\" align=\"absmiddle\"> <span class=\"bodytext\"><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3004 */                                 out.print(resid);
/* 3005 */                                 out.write("&attributeIDs=3503&attributeToSelect=3503&redirectto=");
/* 3006 */                                 out.print(encodeurl);
/* 3007 */                                 out.write("\" class=\"staticlinks\">");
/* 3008 */                                 out.print(ALERTCONFIG_TEXT);
/* 3009 */                                 out.write("</a></span>\n</td>\n</tr>\n</table>\n      <br>\n    </td>\n</tr>\n</table>\n");
/*      */                               }
/*      */                               
/*      */ 
/* 3013 */                               out.write("\n<br>\n\n");
/* 3014 */                               if ((data != null) && (data.size() > 0))
/*      */                               {
/*      */ 
/* 3017 */                                 out.write("\n<table class=\"lrtbdarkborder\" width=\"99%\" cellspacing=\"0\" align=\"center\" border=\"0\">\n<tr>\n<td colspan=\"12\" class=\"tableheadingtrans\">");
/* 3018 */                                 out.print(FormatUtil.getString("am.webclient.wsm.operationstats.text"));
/* 3019 */                                 out.write("</td>\n</tr>\n<tr>\n<td class=\"columnheading\" width=\"30%\" align='left'>");
/* 3020 */                                 out.print(FormatUtil.getString("am.webclient.wsm.operationname.text"));
/* 3021 */                                 out.write("</td>\n<td class=\"columnheading\" width=\"30%\" align='left'></td>\n<td class=\"columnheading\" align=\"center\" width=\"30%\">");
/* 3022 */                                 out.print(FormatUtil.getString("am.webclient.common.status.text"));
/* 3023 */                                 out.write("</td>\n<td class=\"columnheading\" align=\"center\" width=\"15%\">");
/* 3024 */                                 out.print(FormatUtil.getString("am.webclient.wsm.executiontimetitle.text"));
/* 3025 */                                 out.write("</td>\n<td class=\"columnheading\" align=\"center\" width=\"15%\" >");
/* 3026 */                                 out.print(FormatUtil.getString("am.webclient.wsm.actions.text"));
/* 3027 */                                 out.write("</td>\n<td class=\"columnheading\" align=\"center\" width=\"5%\"></td>\n</tr>\n");
/*      */                                 
/* 3029 */                                 int i = 0; for (int size = data.size(); i < size; i++)
/*      */                                 {
/* 3031 */                                   Properties p = (Properties)data.get(i);
/* 3032 */                                   String opname = p.getProperty("name");
/* 3033 */                                   String insid = p.getProperty("operationid");
/* 3034 */                                   String exectime = p.getProperty("executiontime");
/* 3035 */                                   String status = p.getProperty("status");
/* 3036 */                                   java.util.TreeMap map = (java.util.TreeMap)p.get("values");
/* 3037 */                                   java.util.Collection valueC = null;
/* 3038 */                                   String value = "";
/* 3039 */                                   if (map != null)
/*      */                                   {
/* 3041 */                                     valueC = map.values();
/* 3042 */                                     if (valueC != null) {
/* 3043 */                                       value = valueC.toString();
/*      */                                     }
/*      */                                   }
/* 3046 */                                   String rowclass = "yellowgrayborder";
/* 3047 */                                   if (i % 2 == 0)
/*      */                                   {
/* 3049 */                                     rowclass = "whitegrayborder";
/*      */                                   }
/*      */                                   
/*      */ 
/*      */ 
/* 3054 */                                   out.write("\n\n<div id=\"SOAPRequest");
/* 3055 */                                   out.print(i);
/* 3056 */                                   out.write("\" style=\"display:none;\">");
/* 3057 */                                   if (_jspx_meth_c_005fout_005f24(_jspx_page_context))
/*      */                                     return;
/* 3059 */                                   out.write("</div>\n<div id=\"SOAPResponse");
/* 3060 */                                   out.print(i);
/* 3061 */                                   out.write("\" style=\"display:none;\">");
/* 3062 */                                   if (_jspx_meth_c_005fout_005f25(_jspx_page_context))
/*      */                                     return;
/* 3064 */                                   out.write("</div>\n<tr class=\"");
/* 3065 */                                   out.print(rowclass);
/* 3066 */                                   out.write("\" >\n<td height='30' align='left' style=\"padding-left:10px;\"> <a class=\"staticlinks\" href=\"/wsm.do?method=getSOAPInfo&operationId=");
/* 3067 */                                   out.print(insid);
/* 3068 */                                   out.write("&resId=");
/* 3069 */                                   out.print(resid);
/* 3070 */                                   out.write("&executiontime=");
/* 3071 */                                   out.print(exectime);
/* 3072 */                                   out.write("&type=response\">");
/* 3073 */                                   out.print(opname);
/* 3074 */                                   out.write("</a></td>\n<td align=\"left\" title='");
/* 3075 */                                   out.print(FormatUtil.getString("webclient.topo.objectdetails.network.subnets.details"));
/* 3076 */                                   out.write("'><a href=\"/wsm.do?method=getSOAPInfo&operationId=");
/* 3077 */                                   out.print(insid);
/* 3078 */                                   out.write("&resId=");
/* 3079 */                                   out.print(resid);
/* 3080 */                                   out.write("&executiontime=");
/* 3081 */                                   out.print(exectime);
/* 3082 */                                   out.write("&type=response\"><img border=\"0\" src=\"/images/icon_urlsequence_detail.gif\"></a></td>\n");
/* 3083 */                                   if (!status.equals("3")) {
/* 3084 */                                     out.write("\n \n<td align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3085 */                                     out.print(insid);
/* 3086 */                                     out.write("&attributeid=3505&alertconfigurl=");
/* 3087 */                                     out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + insid + "&attributeIDs=3504,3505,3506&attributeToSelect=3506&redirectto=" + encodeurl));
/* 3088 */                                     out.write("')\"></span>\n                ");
/* 3089 */                                     out.print(getSeverityImageForHealth(alert.getProperty(insid + "#" + "3505")));
/* 3090 */                                     out.write("</a>\n</td>\n<td align='center'>");
/* 3091 */                                     out.print(exectime);
/* 3092 */                                     out.write("\n\n<a href=\"javascript:showInstanceGraph(");
/* 3093 */                                     out.print(resid);
/* 3094 */                                     out.write(44);
/* 3095 */                                     out.print(insid);
/* 3096 */                                     out.write(")\"><img src=\"/images/icon_linegraph.gif\" title=\"");
/* 3097 */                                     out.print(FormatUtil.getString("am.webclient.wmi.showgraph.text"));
/* 3098 */                                     out.write("\" border=\"0\" ></a></td>\n<td align=\"center\">");
/*      */                                     
/* 3100 */                                     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3101 */                                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3102 */                                     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                                     
/* 3104 */                                     _jspx_th_logic_005fnotPresent_005f0.setRole("ENTERPRISEADMIN");
/* 3105 */                                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3106 */                                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                       for (;;) {
/* 3108 */                                         out.write("<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3109 */                                         out.print(insid);
/* 3110 */                                         out.write("&attributeIDs=3504,3505,3506,3507&attributeToSelect=3506&redirectto=");
/* 3111 */                                         out.print(encodeurl);
/* 3112 */                                         out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" title=\"");
/* 3113 */                                         out.print(FormatUtil.getString("am.webclient.toolbar.configurealert.text"));
/* 3114 */                                         out.write("\" border=\"0\"></a>");
/* 3115 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3116 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3120 */                                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3121 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                     }
/*      */                                     
/* 3124 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3125 */                                     out.write("</td>\n<td align=\"left\" width=><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3126 */                                     out.print(insid);
/* 3127 */                                     out.write("&attributeid=3506&period=-7&resourcename=");
/* 3128 */                                     out.print(opname);
/* 3129 */                                     out.write("')\">\n<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3130 */                                     out.print(seven_days_text);
/* 3131 */                                     out.write("\"></a></td>\n</td>\n");
/*      */                                   } else {
/* 3133 */                                     out.write("<td colspan=7 align=\"center\">");
/* 3134 */                                     out.print(FormatUtil.getString("am.webclient.wsm.deleteoperations.alert.text"));
/* 3135 */                                     out.write("</td>");
/*      */                                   }
/* 3137 */                                   out.write(10);
/*      */                                 }
/* 3139 */                                 out.write("\n\n</tr>\n\n\n");
/*      */                                 
/* 3141 */                                 PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3142 */                                 _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3143 */                                 _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */                                 
/* 3145 */                                 _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,DEMO");
/* 3146 */                                 int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3147 */                                 if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                   for (;;) {
/* 3149 */                                     out.write("\n<tr>\n<td colspan=\"3\" align=\"left\" class=\"tablebottom\"><a class=\"staticlinks\" href=\"/wsm.do?method=manageoperations&resourceid=");
/* 3150 */                                     out.print(resid);
/* 3151 */                                     out.write(34);
/* 3152 */                                     out.write(62);
/* 3153 */                                     out.print(FormatUtil.getString("am.webclient.wsm.manageoperationsbc.text"));
/* 3154 */                                     out.write("</a></td>\n<td colspan=\"5\" style=\"padding-right: 10px\" align=\"right\" class=\"tablebottom\"><a class=\"staticlinks\" href=\"/wsm.do?method=showoperations&resourceid=");
/* 3155 */                                     out.print(resid);
/* 3156 */                                     out.write(34);
/* 3157 */                                     out.write(62);
/* 3158 */                                     out.print(FormatUtil.getString("am.webclient.wsm.showoperationsbc.text"));
/* 3159 */                                     out.write("</a></td>\n</tr>\t\n");
/* 3160 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3161 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3165 */                                 if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3166 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                 }
/*      */                                 
/* 3169 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3170 */                                 out.write("\n</table>\n\n<br>\n<!--Graph starts here -->\n");
/*      */                                 
/* 3172 */                                 wsmGraph.setParameter(resid, "allmethods");
/* 3173 */                                 int height = 175;
/* 3174 */                                 if (data != null)
/*      */                                 {
/* 3176 */                                   int size = data.size();
/* 3177 */                                   if (size >= 5)
/*      */                                   {
/* 3179 */                                     height += (size - 5) * 30;
/*      */                                   }
/*      */                                 }
/* 3182 */                                 String heightStr = String.valueOf(height);
/*      */                                 
/* 3184 */                                 out.write("\n<table width=\"99%\" class=\"lrtbdarkborder\" cellspacing=\"0\" align=\"center\">\n<tr >\n<td class=\"tableheadingbborder\">");
/* 3185 */                                 out.print(FormatUtil.getString("am.webclient.wsm.operationexecutiontime.text"));
/* 3186 */                                 out.write("</td>\n</tr>\n<tr>\n<td align=\"center\">\n");
/*      */                                 
/* 3188 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3189 */                                 _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3190 */                                 _jspx_th_awolf_005ftimechart_005f1.setParent(null);
/*      */                                 
/* 3192 */                                 _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("wsmGraph");
/*      */                                 
/* 3194 */                                 _jspx_th_awolf_005ftimechart_005f1.setWidth("550");
/*      */                                 
/* 3196 */                                 _jspx_th_awolf_005ftimechart_005f1.setHeight(heightStr);
/*      */                                 
/* 3198 */                                 _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */                                 
/* 3200 */                                 _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                 
/* 3202 */                                 _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(FormatUtil.getString("am.webclient.wsm.executiontime.text"));
/* 3203 */                                 int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3204 */                                 if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3205 */                                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3206 */                                     out = _jspx_page_context.pushBody();
/* 3207 */                                     _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3208 */                                     _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3211 */                                     out.write(32);
/* 3212 */                                     out.write(10);
/* 3213 */                                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3214 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3217 */                                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3218 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3221 */                                 if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3222 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                                 }
/*      */                                 
/* 3225 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3226 */                                 out.write(" \n</td>\n</tr>\n</table>\n");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3232 */                                 out.write(10);
/* 3233 */                                 out.write(9);
/*      */                                 
/* 3235 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3236 */                                 _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3237 */                                 _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */                                 
/* 3239 */                                 _jspx_th_logic_005fnotPresent_005f1.setRole("ENTERPRISEADMIN");
/* 3240 */                                 int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3241 */                                 if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                   for (;;) {
/* 3243 */                                     out.write("\n<table class=\"lrtbdarkborder\" width=\"99%\" cellspacing=\"0\" align=\"center\">\n<tr>\n<td class=\"tableheadingtrans\">");
/* 3244 */                                     out.print(FormatUtil.getString("am.webclient.wsm.operationstats.text"));
/* 3245 */                                     out.write("</td>\n</tr>\n<tr>\n<td height=\"25px\">\n<span class=\"bodytext\">");
/* 3246 */                                     out.print(FormatUtil.getString("am.webclient.wsm.nooperations.text"));
/* 3247 */                                     out.write(" <a class=\"staticlinks\" href=\"/wsm.do?method=showoperations&resourceid=");
/* 3248 */                                     out.print(resid);
/* 3249 */                                     out.write(34);
/* 3250 */                                     out.write(62);
/* 3251 */                                     out.print(FormatUtil.getString("am.webclient.wsm.showoperationsbc.text"));
/* 3252 */                                     out.write("</a></span>\n</td>\n</tr>\n</table>\n");
/* 3253 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3254 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3258 */                                 if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3259 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                 }
/*      */                                 
/* 3262 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3263 */                                 out.write(10);
/*      */                               }
/*      */                               
/*      */ 
/* 3267 */                               out.write(10);
/*      */                             }
/* 3269 */                           } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3270 */         out = _jspx_out;
/* 3271 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3272 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3273 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3276 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3282 */     PageContext pageContext = _jspx_page_context;
/* 3283 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3285 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3286 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3287 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 3289 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3290 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3291 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3293 */         out.write("\n\t alertUser();\n\treturn;\n\t");
/* 3294 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3295 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3299 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3300 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3301 */       return true;
/*      */     }
/* 3303 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3304 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3309 */     PageContext pageContext = _jspx_page_context;
/* 3310 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3312 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3313 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3314 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3316 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 3317 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3318 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3319 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3320 */       return true;
/*      */     }
/* 3322 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3323 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3328 */     PageContext pageContext = _jspx_page_context;
/* 3329 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3331 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3332 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3333 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3335 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 3336 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3337 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3338 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3339 */       return true;
/*      */     }
/* 3341 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3342 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3347 */     PageContext pageContext = _jspx_page_context;
/* 3348 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3350 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3351 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3352 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3354 */     _jspx_th_c_005fout_005f2.setValue("${param.resId}");
/* 3355 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3356 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3357 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3358 */       return true;
/*      */     }
/* 3360 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3361 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3366 */     PageContext pageContext = _jspx_page_context;
/* 3367 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3369 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3370 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3371 */     _jspx_th_c_005fif_005f4.setParent(null);
/*      */     
/* 3373 */     _jspx_th_c_005fif_005f4.setTest("${not empty param.haid}");
/* 3374 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3375 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 3377 */         out.write(10);
/* 3378 */         out.write(9);
/* 3379 */         out.write(9);
/* 3380 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 3381 */           return true;
/* 3382 */         out.write(10);
/* 3383 */         out.write(9);
/* 3384 */         out.write(9);
/* 3385 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3386 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3390 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3391 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3392 */       return true;
/*      */     }
/* 3394 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3395 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3400 */     PageContext pageContext = _jspx_page_context;
/* 3401 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3403 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3404 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3405 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 3407 */     _jspx_th_c_005fset_005f0.setVar("myfield_paramresid");
/*      */     
/* 3409 */     _jspx_th_c_005fset_005f0.setScope("page");
/* 3410 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3411 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 3412 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3413 */         out = _jspx_page_context.pushBody();
/* 3414 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 3415 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3418 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 3419 */           return true;
/* 3420 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 3421 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3424 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3425 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3428 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3429 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 3430 */       return true;
/*      */     }
/* 3432 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 3433 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3438 */     PageContext pageContext = _jspx_page_context;
/* 3439 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3441 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3442 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3443 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 3445 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 3446 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3447 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3448 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3449 */       return true;
/*      */     }
/* 3451 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3452 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3457 */     PageContext pageContext = _jspx_page_context;
/* 3458 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3460 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3461 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3462 */     _jspx_th_c_005fif_005f5.setParent(null);
/*      */     
/* 3464 */     _jspx_th_c_005fif_005f5.setTest("${not empty param.resourceid}");
/* 3465 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3466 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 3468 */         out.write(10);
/* 3469 */         out.write(9);
/* 3470 */         out.write(9);
/* 3471 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 3472 */           return true;
/* 3473 */         out.write(10);
/* 3474 */         out.write(9);
/* 3475 */         out.write(9);
/* 3476 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3477 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3481 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3482 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3483 */       return true;
/*      */     }
/* 3485 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3486 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3491 */     PageContext pageContext = _jspx_page_context;
/* 3492 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3494 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3495 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 3496 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 3498 */     _jspx_th_c_005fset_005f1.setVar("myfield_paramresid");
/*      */     
/* 3500 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 3501 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 3502 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 3503 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3504 */         out = _jspx_page_context.pushBody();
/* 3505 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 3506 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3509 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 3510 */           return true;
/* 3511 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 3512 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3515 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3516 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3519 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 3520 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 3521 */       return true;
/*      */     }
/* 3523 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 3524 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3529 */     PageContext pageContext = _jspx_page_context;
/* 3530 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3532 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3533 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3534 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 3536 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 3537 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3538 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3539 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3540 */       return true;
/*      */     }
/* 3542 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3543 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3548 */     PageContext pageContext = _jspx_page_context;
/* 3549 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3551 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3552 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3553 */     _jspx_th_c_005fout_005f5.setParent(null);
/*      */     
/* 3555 */     _jspx_th_c_005fout_005f5.setValue("${myfield_paramresid}");
/* 3556 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3557 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3558 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3559 */       return true;
/*      */     }
/* 3561 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3562 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3567 */     PageContext pageContext = _jspx_page_context;
/* 3568 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3570 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3571 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3572 */     _jspx_th_c_005fif_005f6.setParent(null);
/*      */     
/* 3574 */     _jspx_th_c_005fif_005f6.setTest("${not empty param.haid}");
/* 3575 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3576 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 3578 */         out.write(10);
/* 3579 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 3580 */           return true;
/* 3581 */         out.write(10);
/* 3582 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3583 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3587 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3588 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3589 */       return true;
/*      */     }
/* 3591 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3592 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3597 */     PageContext pageContext = _jspx_page_context;
/* 3598 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3600 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3601 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 3602 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 3604 */     _jspx_th_c_005fset_005f2.setVar("myfield_resid");
/*      */     
/* 3606 */     _jspx_th_c_005fset_005f2.setScope("page");
/* 3607 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 3608 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 3609 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3610 */         out = _jspx_page_context.pushBody();
/* 3611 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 3612 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3615 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 3616 */           return true;
/* 3617 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 3618 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3621 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3622 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3625 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 3626 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 3627 */       return true;
/*      */     }
/* 3629 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 3630 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3635 */     PageContext pageContext = _jspx_page_context;
/* 3636 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3638 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3639 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3640 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 3642 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 3643 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3644 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3645 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3646 */       return true;
/*      */     }
/* 3648 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3649 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3654 */     PageContext pageContext = _jspx_page_context;
/* 3655 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3657 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3658 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3659 */     _jspx_th_c_005fif_005f7.setParent(null);
/*      */     
/* 3661 */     _jspx_th_c_005fif_005f7.setTest("${not empty param.resourceid}");
/* 3662 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3663 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 3665 */         out.write(10);
/* 3666 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 3667 */           return true;
/* 3668 */         out.write(10);
/* 3669 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3670 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3674 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3675 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3676 */       return true;
/*      */     }
/* 3678 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3679 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3684 */     PageContext pageContext = _jspx_page_context;
/* 3685 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3687 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3688 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 3689 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 3691 */     _jspx_th_c_005fset_005f3.setVar("myfield_resid");
/*      */     
/* 3693 */     _jspx_th_c_005fset_005f3.setScope("page");
/* 3694 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 3695 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 3696 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3697 */         out = _jspx_page_context.pushBody();
/* 3698 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 3699 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3702 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 3703 */           return true;
/* 3704 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 3705 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3708 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3709 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3712 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 3713 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 3714 */       return true;
/*      */     }
/* 3716 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 3717 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3722 */     PageContext pageContext = _jspx_page_context;
/* 3723 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3725 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3726 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3727 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 3729 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 3730 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3731 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3732 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3733 */       return true;
/*      */     }
/* 3735 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3736 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3741 */     PageContext pageContext = _jspx_page_context;
/* 3742 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3744 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3745 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 3746 */     _jspx_th_c_005fset_005f4.setParent(null);
/*      */     
/* 3748 */     _jspx_th_c_005fset_005f4.setVar("trstripclass");
/*      */     
/* 3750 */     _jspx_th_c_005fset_005f4.setScope("page");
/* 3751 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 3752 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 3753 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 3754 */         out = _jspx_page_context.pushBody();
/* 3755 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 3756 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3759 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fset_005f4, _jspx_page_context))
/* 3760 */           return true;
/* 3761 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 3762 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3765 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 3766 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3769 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 3770 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 3771 */       return true;
/*      */     }
/* 3773 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 3774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3779 */     PageContext pageContext = _jspx_page_context;
/* 3780 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3782 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3783 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3784 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 3786 */     _jspx_th_c_005fout_005f8.setValue("");
/* 3787 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3788 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3789 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3790 */       return true;
/*      */     }
/* 3792 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3793 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3798 */     PageContext pageContext = _jspx_page_context;
/* 3799 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3801 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3802 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 3803 */     _jspx_th_c_005fset_005f5.setParent(null);
/*      */     
/* 3805 */     _jspx_th_c_005fset_005f5.setVar("myfield_entity");
/*      */     
/* 3807 */     _jspx_th_c_005fset_005f5.setScope("page");
/* 3808 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 3809 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 3810 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 3811 */         out = _jspx_page_context.pushBody();
/* 3812 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 3813 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3816 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 3817 */           return true;
/* 3818 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 3819 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3822 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 3823 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3826 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 3827 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 3828 */       return true;
/*      */     }
/* 3830 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 3831 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3836 */     PageContext pageContext = _jspx_page_context;
/* 3837 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3839 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3840 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3841 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 3843 */     _jspx_th_c_005fout_005f9.setValue("noalarms");
/* 3844 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3845 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3846 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3847 */       return true;
/*      */     }
/* 3849 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3850 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3855 */     PageContext pageContext = _jspx_page_context;
/* 3856 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3858 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3859 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3860 */     _jspx_th_c_005fif_005f8.setParent(null);
/*      */     
/* 3862 */     _jspx_th_c_005fif_005f8.setTest("${not empty param.entity}");
/* 3863 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3864 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 3866 */         out.write(10);
/* 3867 */         if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fif_005f8, _jspx_page_context))
/* 3868 */           return true;
/* 3869 */         out.write(10);
/* 3870 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3871 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3875 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3876 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3877 */       return true;
/*      */     }
/* 3879 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3880 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3885 */     PageContext pageContext = _jspx_page_context;
/* 3886 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3888 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3889 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 3890 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 3892 */     _jspx_th_c_005fset_005f6.setVar("myfield_entity");
/*      */     
/* 3894 */     _jspx_th_c_005fset_005f6.setScope("page");
/* 3895 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 3896 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 3897 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 3898 */         out = _jspx_page_context.pushBody();
/* 3899 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 3900 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3903 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fset_005f6, _jspx_page_context))
/* 3904 */           return true;
/* 3905 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 3906 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3909 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 3910 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3913 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 3914 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 3915 */       return true;
/*      */     }
/* 3917 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 3918 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3923 */     PageContext pageContext = _jspx_page_context;
/* 3924 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3926 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3927 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3928 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 3930 */     _jspx_th_c_005fout_005f10.setValue("${param.entity}");
/* 3931 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3932 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3933 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3934 */       return true;
/*      */     }
/* 3936 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3937 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3942 */     PageContext pageContext = _jspx_page_context;
/* 3943 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3945 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3946 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3947 */     _jspx_th_c_005fif_005f9.setParent(null);
/*      */     
/* 3949 */     _jspx_th_c_005fif_005f9.setTest("${not empty param.includeClass}");
/* 3950 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3951 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 3953 */         out.write(10);
/* 3954 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 3955 */           return true;
/* 3956 */         out.write(10);
/* 3957 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3958 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3962 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3963 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3964 */       return true;
/*      */     }
/* 3966 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3972 */     PageContext pageContext = _jspx_page_context;
/* 3973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3975 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3976 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 3977 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 3979 */     _jspx_th_c_005fset_005f7.setVar("trstripclass");
/*      */     
/* 3981 */     _jspx_th_c_005fset_005f7.setScope("page");
/* 3982 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 3983 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 3984 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 3985 */         out = _jspx_page_context.pushBody();
/* 3986 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 3987 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3990 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 3991 */           return true;
/* 3992 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 3993 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3996 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 3997 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4000 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 4001 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 4002 */       return true;
/*      */     }
/* 4004 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 4005 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4010 */     PageContext pageContext = _jspx_page_context;
/* 4011 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4013 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4014 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4015 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 4017 */     _jspx_th_c_005fout_005f11.setValue("${param.includeClass}");
/* 4018 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4019 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4020 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4021 */       return true;
/*      */     }
/* 4023 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4024 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4029 */     PageContext pageContext = _jspx_page_context;
/* 4030 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4032 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4033 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4034 */     _jspx_th_c_005fout_005f12.setParent(null);
/*      */     
/* 4036 */     _jspx_th_c_005fout_005f12.setValue("${trstripclass}");
/* 4037 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4038 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4039 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4040 */       return true;
/*      */     }
/* 4042 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4043 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4048 */     PageContext pageContext = _jspx_page_context;
/* 4049 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4051 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4052 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4053 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/* 4054 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4055 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 4056 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 4057 */         out = _jspx_page_context.pushBody();
/* 4058 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 4059 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4062 */         out.write("am.myfield.customfield.text");
/* 4063 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 4064 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4067 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 4068 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4071 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4072 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4073 */       return true;
/*      */     }
/* 4075 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4076 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4081 */     PageContext pageContext = _jspx_page_context;
/* 4082 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4084 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4085 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4086 */     _jspx_th_c_005fout_005f13.setParent(null);
/*      */     
/* 4088 */     _jspx_th_c_005fout_005f13.setValue("${myfield_resid}");
/* 4089 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4090 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4091 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4092 */       return true;
/*      */     }
/* 4094 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4095 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4100 */     PageContext pageContext = _jspx_page_context;
/* 4101 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4103 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4104 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4105 */     _jspx_th_c_005fout_005f14.setParent(null);
/*      */     
/* 4107 */     _jspx_th_c_005fout_005f14.setValue("${myfield_entity}");
/* 4108 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4109 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4110 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4111 */       return true;
/*      */     }
/* 4113 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4114 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4119 */     PageContext pageContext = _jspx_page_context;
/* 4120 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4122 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4123 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4124 */     _jspx_th_c_005fout_005f15.setParent(null);
/*      */     
/* 4126 */     _jspx_th_c_005fout_005f15.setValue("${param.resourceid}");
/* 4127 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4128 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4129 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4130 */       return true;
/*      */     }
/* 4132 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4133 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4138 */     PageContext pageContext = _jspx_page_context;
/* 4139 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4141 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4142 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4143 */     _jspx_th_c_005fout_005f16.setParent(null);
/*      */     
/* 4145 */     _jspx_th_c_005fout_005f16.setValue("${param.resourcename}");
/* 4146 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4147 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4148 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4149 */       return true;
/*      */     }
/* 4151 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4152 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4157 */     PageContext pageContext = _jspx_page_context;
/* 4158 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4160 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4161 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 4162 */     _jspx_th_c_005fout_005f17.setParent(null);
/*      */     
/* 4164 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid}");
/* 4165 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 4166 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 4167 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4168 */       return true;
/*      */     }
/* 4170 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4171 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4176 */     PageContext pageContext = _jspx_page_context;
/* 4177 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4179 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4180 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 4181 */     _jspx_th_c_005fout_005f18.setParent(null);
/*      */     
/* 4183 */     _jspx_th_c_005fout_005f18.setValue("${param.resourcename}");
/* 4184 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 4185 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 4186 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4187 */       return true;
/*      */     }
/* 4189 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4190 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4195 */     PageContext pageContext = _jspx_page_context;
/* 4196 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4198 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4199 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 4200 */     _jspx_th_c_005fout_005f19.setParent(null);
/*      */     
/* 4202 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 4203 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 4204 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 4205 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4206 */       return true;
/*      */     }
/* 4208 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4209 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4214 */     PageContext pageContext = _jspx_page_context;
/* 4215 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4217 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4218 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 4219 */     _jspx_th_c_005fout_005f20.setParent(null);
/*      */     
/* 4221 */     _jspx_th_c_005fout_005f20.setValue("${param.resourceid}");
/* 4222 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 4223 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 4224 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4225 */       return true;
/*      */     }
/* 4227 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4228 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4233 */     PageContext pageContext = _jspx_page_context;
/* 4234 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4236 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4237 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 4238 */     _jspx_th_c_005fout_005f21.setParent(null);
/*      */     
/* 4240 */     _jspx_th_c_005fout_005f21.setValue("${displayname}");
/* 4241 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 4242 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 4243 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 4244 */       return true;
/*      */     }
/* 4246 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 4247 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4252 */     PageContext pageContext = _jspx_page_context;
/* 4253 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4255 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4256 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 4257 */     _jspx_th_c_005fout_005f22.setParent(null);
/*      */     
/* 4259 */     _jspx_th_c_005fout_005f22.setValue("${param.resourceid}");
/* 4260 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 4261 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 4262 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 4263 */       return true;
/*      */     }
/* 4265 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 4266 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4271 */     PageContext pageContext = _jspx_page_context;
/* 4272 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4274 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4275 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 4276 */     _jspx_th_c_005fout_005f23.setParent(null);
/*      */     
/* 4278 */     _jspx_th_c_005fout_005f23.setValue("${displayname}");
/* 4279 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 4280 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 4281 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 4282 */       return true;
/*      */     }
/* 4284 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 4285 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4290 */     PageContext pageContext = _jspx_page_context;
/* 4291 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4293 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4294 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4295 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/* 4296 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4297 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 4298 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 4299 */         out = _jspx_page_context.pushBody();
/* 4300 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 4301 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4304 */         out.write("table.heading.attribute");
/* 4305 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 4306 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4309 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 4310 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4313 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4314 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4315 */       return true;
/*      */     }
/* 4317 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4318 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4323 */     PageContext pageContext = _jspx_page_context;
/* 4324 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4326 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4327 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 4328 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/* 4329 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 4330 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 4331 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 4332 */         out = _jspx_page_context.pushBody();
/* 4333 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 4334 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4337 */         out.write("table.heading.value");
/* 4338 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 4339 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4342 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 4343 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4346 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 4347 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4348 */       return true;
/*      */     }
/* 4350 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4351 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4356 */     PageContext pageContext = _jspx_page_context;
/* 4357 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4359 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4360 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 4361 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/* 4362 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 4363 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 4364 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 4365 */         out = _jspx_page_context.pushBody();
/* 4366 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 4367 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4370 */         out.write("table.heading.status");
/* 4371 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 4372 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4375 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 4376 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4379 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 4380 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4381 */       return true;
/*      */     }
/* 4383 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4384 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4389 */     PageContext pageContext = _jspx_page_context;
/* 4390 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4392 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 4393 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 4394 */     _jspx_th_c_005fout_005f24.setParent(null);
/*      */     
/* 4396 */     _jspx_th_c_005fout_005f24.setValue("${SOAPRequest}");
/*      */     
/* 4398 */     _jspx_th_c_005fout_005f24.setEscapeXml("true");
/* 4399 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 4400 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 4401 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 4402 */       return true;
/*      */     }
/* 4404 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 4405 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4410 */     PageContext pageContext = _jspx_page_context;
/* 4411 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4413 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 4414 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 4415 */     _jspx_th_c_005fout_005f25.setParent(null);
/*      */     
/* 4417 */     _jspx_th_c_005fout_005f25.setValue("${SOAPResponse}");
/*      */     
/* 4419 */     _jspx_th_c_005fout_005f25.setEscapeXml("true");
/* 4420 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 4421 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 4422 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 4423 */       return true;
/*      */     }
/* 4425 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 4426 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\WSMDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */