/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.net.InetAddress;
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
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class ConfigureMailNWeb_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   44 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   47 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   48 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   49 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   56 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   61 */     ArrayList list = null;
/*   62 */     StringBuffer sbf = new StringBuffer();
/*   63 */     ManagedApplication mo = new ManagedApplication();
/*   64 */     if (distinct)
/*      */     {
/*   66 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   70 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   73 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   75 */       ArrayList row = (ArrayList)list.get(i);
/*   76 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   77 */       if (distinct) {
/*   78 */         sbf.append(row.get(0));
/*      */       } else
/*   80 */         sbf.append(row.get(1));
/*   81 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   84 */     return sbf.toString(); }
/*      */   
/*   86 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   89 */     if (severity == null)
/*      */     {
/*   91 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   93 */     if (severity.equals("5"))
/*      */     {
/*   95 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*   97 */     if (severity.equals("1"))
/*      */     {
/*   99 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  104 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  111 */     if (severity == null)
/*      */     {
/*  113 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  115 */     if (severity.equals("1"))
/*      */     {
/*  117 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  119 */     if (severity.equals("4"))
/*      */     {
/*  121 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  123 */     if (severity.equals("5"))
/*      */     {
/*  125 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  130 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  136 */     if (severity == null)
/*      */     {
/*  138 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  140 */     if (severity.equals("5"))
/*      */     {
/*  142 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  144 */     if (severity.equals("1"))
/*      */     {
/*  146 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  150 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  156 */     if (severity == null)
/*      */     {
/*  158 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  160 */     if (severity.equals("1"))
/*      */     {
/*  162 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  164 */     if (severity.equals("4"))
/*      */     {
/*  166 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  168 */     if (severity.equals("5"))
/*      */     {
/*  170 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  174 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  180 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  186 */     if (severity == 5)
/*      */     {
/*  188 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  190 */     if (severity == 1)
/*      */     {
/*  192 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  197 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  203 */     if (severity == null)
/*      */     {
/*  205 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  207 */     if (severity.equals("5"))
/*      */     {
/*  209 */       if (isAvailability) {
/*  210 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  213 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  216 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  218 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  220 */     if (severity.equals("1"))
/*      */     {
/*  222 */       if (isAvailability) {
/*  223 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  226 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  233 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  240 */     if (severity == null)
/*      */     {
/*  242 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  244 */     if (severity.equals("5"))
/*      */     {
/*  246 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  248 */     if (severity.equals("4"))
/*      */     {
/*  250 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  252 */     if (severity.equals("1"))
/*      */     {
/*  254 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  259 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  265 */     if (severity == null)
/*      */     {
/*  267 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  269 */     if (severity.equals("5"))
/*      */     {
/*  271 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  273 */     if (severity.equals("4"))
/*      */     {
/*  275 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  277 */     if (severity.equals("1"))
/*      */     {
/*  279 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  284 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  291 */     if (severity == null)
/*      */     {
/*  293 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  295 */     if (severity.equals("5"))
/*      */     {
/*  297 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  299 */     if (severity.equals("4"))
/*      */     {
/*  301 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  303 */     if (severity.equals("1"))
/*      */     {
/*  305 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  310 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  318 */     StringBuffer out = new StringBuffer();
/*  319 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  320 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  321 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  322 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  323 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  324 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  325 */     out.append("</tr>");
/*  326 */     out.append("</form></table>");
/*  327 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  334 */     if (val == null)
/*      */     {
/*  336 */       return "-";
/*      */     }
/*      */     
/*  339 */     String ret = FormatUtil.formatNumber(val);
/*  340 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  341 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  344 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  348 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  356 */     StringBuffer out = new StringBuffer();
/*  357 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  358 */     out.append("<tr>");
/*  359 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  361 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  363 */     out.append("</tr>");
/*  364 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  368 */       if (j % 2 == 0)
/*      */       {
/*  370 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  374 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  377 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  379 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  382 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  386 */       out.append("</tr>");
/*      */     }
/*  388 */     out.append("</table>");
/*  389 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  390 */     out.append("<tr>");
/*  391 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  392 */     out.append("</tr>");
/*  393 */     out.append("</table>");
/*  394 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  400 */     StringBuffer out = new StringBuffer();
/*  401 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  402 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  403 */     out.append("<tr>");
/*  404 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  405 */     out.append("<tr>");
/*  406 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  407 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  408 */     out.append("</tr>");
/*  409 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  412 */       out.append("<tr>");
/*  413 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  414 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  415 */       out.append("</tr>");
/*      */     }
/*      */     
/*  418 */     out.append("</table>");
/*  419 */     out.append("</table>");
/*  420 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  425 */     if (severity.equals("0"))
/*      */     {
/*  427 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  431 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  438 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  451 */     StringBuffer out = new StringBuffer();
/*  452 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  453 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  455 */       out.append("<tr>");
/*  456 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  457 */       out.append("</tr>");
/*      */       
/*      */ 
/*  460 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  462 */         String borderclass = "";
/*      */         
/*      */ 
/*  465 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  467 */         out.append("<tr>");
/*      */         
/*  469 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  470 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  471 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  477 */     out.append("</table><br>");
/*  478 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  479 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  481 */       List sLinks = secondLevelOfLinks[0];
/*  482 */       List sText = secondLevelOfLinks[1];
/*  483 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  486 */         out.append("<tr>");
/*  487 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  488 */         out.append("</tr>");
/*  489 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  491 */           String borderclass = "";
/*      */           
/*      */ 
/*  494 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  496 */           out.append("<tr>");
/*      */           
/*  498 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  499 */           if (sLinks.get(i).toString().length() == 0) {
/*  500 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  503 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  505 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  509 */     out.append("</table>");
/*  510 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  517 */     StringBuffer out = new StringBuffer();
/*  518 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  519 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  521 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  523 */         out.append("<tr>");
/*  524 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  525 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  529 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  531 */           String borderclass = "";
/*      */           
/*      */ 
/*  534 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  536 */           out.append("<tr>");
/*      */           
/*  538 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  539 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  540 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  543 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  546 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  551 */     out.append("</table><br>");
/*  552 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  553 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  555 */       List sLinks = secondLevelOfLinks[0];
/*  556 */       List sText = secondLevelOfLinks[1];
/*  557 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  560 */         out.append("<tr>");
/*  561 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  562 */         out.append("</tr>");
/*  563 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  565 */           String borderclass = "";
/*      */           
/*      */ 
/*  568 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  570 */           out.append("<tr>");
/*      */           
/*  572 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  573 */           if (sLinks.get(i).toString().length() == 0) {
/*  574 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  577 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  579 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  583 */     out.append("</table>");
/*  584 */     return out.toString();
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
/*  597 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  600 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  603 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  606 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  609 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  612 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  615 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  618 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  626 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  631 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  636 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  641 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  646 */     if (val != null)
/*      */     {
/*  648 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  652 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  657 */     if (val == null) {
/*  658 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  662 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  667 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  673 */     if (val != null)
/*      */     {
/*  675 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  679 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  685 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  690 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  694 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  699 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  704 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  709 */     String hostaddress = "";
/*  710 */     String ip = request.getHeader("x-forwarded-for");
/*  711 */     if (ip == null)
/*  712 */       ip = request.getRemoteAddr();
/*  713 */     InetAddress add = null;
/*  714 */     if (ip.equals("127.0.0.1")) {
/*  715 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  719 */       add = InetAddress.getByName(ip);
/*      */     }
/*  721 */     hostaddress = add.getHostName();
/*  722 */     if (hostaddress.indexOf('.') != -1) {
/*  723 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  724 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  728 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  733 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  739 */     if (severity == null)
/*      */     {
/*  741 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  743 */     if (severity.equals("5"))
/*      */     {
/*  745 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  747 */     if (severity.equals("1"))
/*      */     {
/*  749 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  754 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  759 */     ResultSet set = null;
/*  760 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  761 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  763 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  764 */       if (set.next()) { String str1;
/*  765 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  766 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  769 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  774 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  777 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  779 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  783 */     StringBuffer rca = new StringBuffer();
/*  784 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  785 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  788 */     int rcalength = key.length();
/*  789 */     String split = "6. ";
/*  790 */     int splitPresent = key.indexOf(split);
/*  791 */     String div1 = "";String div2 = "";
/*  792 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  794 */       if (rcalength > 180) {
/*  795 */         rca.append("<span class=\"rca-critical-text\">");
/*  796 */         getRCATrimmedText(key, rca);
/*  797 */         rca.append("</span>");
/*      */       } else {
/*  799 */         rca.append("<span class=\"rca-critical-text\">");
/*  800 */         rca.append(key);
/*  801 */         rca.append("</span>");
/*      */       }
/*  803 */       return rca.toString();
/*      */     }
/*  805 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  806 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  807 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  808 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  809 */     getRCATrimmedText(div1, rca);
/*  810 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  813 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  814 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  815 */     getRCATrimmedText(div2, rca);
/*  816 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  818 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  823 */     String[] st = msg.split("<br>");
/*  824 */     for (int i = 0; i < st.length; i++) {
/*  825 */       String s = st[i];
/*  826 */       if (s.length() > 180) {
/*  827 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  829 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  833 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  834 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  836 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  840 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  841 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  842 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  845 */       if (key == null) {
/*  846 */         return ret;
/*      */       }
/*      */       
/*  849 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  850 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  853 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  854 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  855 */       set = AMConnectionPool.executeQueryStmt(query);
/*  856 */       if (set.next())
/*      */       {
/*  858 */         String helpLink = set.getString("LINK");
/*  859 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  862 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  868 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  887 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  878 */         if (set != null) {
/*  879 */           AMConnectionPool.closeStatement(set);
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
/*  893 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  894 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  896 */       String entityStr = (String)keys.nextElement();
/*  897 */       String mmessage = temp.getProperty(entityStr);
/*  898 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  899 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  901 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  907 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  908 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  910 */       String entityStr = (String)keys.nextElement();
/*  911 */       String mmessage = temp.getProperty(entityStr);
/*  912 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  913 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  915 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  920 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  930 */     String des = new String();
/*  931 */     while (str.indexOf(find) != -1) {
/*  932 */       des = des + str.substring(0, str.indexOf(find));
/*  933 */       des = des + replace;
/*  934 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  936 */     des = des + str;
/*  937 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  944 */       if (alert == null)
/*      */       {
/*  946 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  948 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  950 */         return "&nbsp;";
/*      */       }
/*      */       
/*  953 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  955 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  958 */       int rcalength = test.length();
/*  959 */       if (rcalength < 300)
/*      */       {
/*  961 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  965 */       StringBuffer out = new StringBuffer();
/*  966 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  967 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  968 */       out.append("</div>");
/*  969 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  970 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  971 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  976 */       ex.printStackTrace();
/*      */     }
/*  978 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  984 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  989 */     ArrayList attribIDs = new ArrayList();
/*  990 */     ArrayList resIDs = new ArrayList();
/*  991 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  993 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  995 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/*  997 */       String resourceid = "";
/*  998 */       String resourceType = "";
/*  999 */       if (type == 2) {
/* 1000 */         resourceid = (String)row.get(0);
/* 1001 */         resourceType = (String)row.get(3);
/*      */       }
/* 1003 */       else if (type == 3) {
/* 1004 */         resourceid = (String)row.get(0);
/* 1005 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1008 */         resourceid = (String)row.get(6);
/* 1009 */         resourceType = (String)row.get(7);
/*      */       }
/* 1011 */       resIDs.add(resourceid);
/* 1012 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1013 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1015 */       String healthentity = null;
/* 1016 */       String availentity = null;
/* 1017 */       if (healthid != null) {
/* 1018 */         healthentity = resourceid + "_" + healthid;
/* 1019 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1022 */       if (availid != null) {
/* 1023 */         availentity = resourceid + "_" + availid;
/* 1024 */         entitylist.add(availentity);
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
/* 1038 */     Properties alert = getStatus(entitylist);
/* 1039 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1044 */     int size = monitorList.size();
/*      */     
/* 1046 */     String[] severity = new String[size];
/*      */     
/* 1048 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1050 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1051 */       String resourceName1 = (String)row1.get(7);
/* 1052 */       String resourceid1 = (String)row1.get(6);
/* 1053 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1054 */       if (severity[j] == null)
/*      */       {
/* 1056 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1060 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1062 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1064 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1067 */         if (sev > 0) {
/* 1068 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1069 */           monitorList.set(k, monitorList.get(j));
/* 1070 */           monitorList.set(j, t);
/* 1071 */           String temp = severity[k];
/* 1072 */           severity[k] = severity[j];
/* 1073 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1079 */     int z = 0;
/* 1080 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1083 */       int i = 0;
/* 1084 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1087 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1091 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1095 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1097 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1100 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1104 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1107 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1108 */       String resourceName1 = (String)row1.get(7);
/* 1109 */       String resourceid1 = (String)row1.get(6);
/* 1110 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1111 */       if (hseverity[j] == null)
/*      */       {
/* 1113 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1118 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1120 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1123 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1126 */         if (hsev > 0) {
/* 1127 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1128 */           monitorList.set(k, monitorList.get(j));
/* 1129 */           monitorList.set(j, t);
/* 1130 */           String temp1 = hseverity[k];
/* 1131 */           hseverity[k] = hseverity[j];
/* 1132 */           hseverity[j] = temp1;
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
/* 1144 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1145 */     boolean forInventory = false;
/* 1146 */     String trdisplay = "none";
/* 1147 */     String plusstyle = "inline";
/* 1148 */     String minusstyle = "none";
/* 1149 */     String haidTopLevel = "";
/* 1150 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1152 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1154 */         haidTopLevel = request.getParameter("haid");
/* 1155 */         forInventory = true;
/* 1156 */         trdisplay = "table-row;";
/* 1157 */         plusstyle = "none";
/* 1158 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1165 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1168 */     ArrayList listtoreturn = new ArrayList();
/* 1169 */     StringBuffer toreturn = new StringBuffer();
/* 1170 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1171 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1172 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1174 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1176 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1177 */       String childresid = (String)singlerow.get(0);
/* 1178 */       String childresname = (String)singlerow.get(1);
/* 1179 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1180 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1181 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1182 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1183 */       String unmanagestatus = (String)singlerow.get(5);
/* 1184 */       String actionstatus = (String)singlerow.get(6);
/* 1185 */       String linkclass = "monitorgp-links";
/* 1186 */       String titleforres = childresname;
/* 1187 */       String titilechildresname = childresname;
/* 1188 */       String childimg = "/images/trcont.png";
/* 1189 */       String flag = "enable";
/* 1190 */       String dcstarted = (String)singlerow.get(8);
/* 1191 */       String configMonitor = "";
/* 1192 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1193 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1195 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1197 */       if (singlerow.get(7) != null)
/*      */       {
/* 1199 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1201 */       String haiGroupType = "0";
/* 1202 */       if ("HAI".equals(childtype))
/*      */       {
/* 1204 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1206 */       childimg = "/images/trend.png";
/* 1207 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1208 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1209 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1211 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1213 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1215 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1216 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1219 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1221 */         linkclass = "disabledtext";
/* 1222 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1224 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1225 */       String availmouseover = "";
/* 1226 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1228 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1230 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1231 */       String healthmouseover = "";
/* 1232 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1234 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1237 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1238 */       int spacing = 0;
/* 1239 */       if (level >= 1)
/*      */       {
/* 1241 */         spacing = 40 * level;
/*      */       }
/* 1243 */       if (childtype.equals("HAI"))
/*      */       {
/* 1245 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1246 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1247 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1249 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1250 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1251 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1252 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1253 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1254 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1255 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1256 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1257 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1258 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1259 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1261 */         if (!forInventory)
/*      */         {
/* 1263 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1266 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1268 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1270 */           actions = editlink + actions;
/*      */         }
/* 1272 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1274 */           actions = actions + associatelink;
/*      */         }
/* 1276 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1277 */         String arrowimg = "";
/* 1278 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1280 */           actions = "";
/* 1281 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1282 */           checkbox = "";
/* 1283 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1285 */         if (isIt360)
/*      */         {
/* 1287 */           actionimg = "";
/* 1288 */           actions = "";
/* 1289 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1290 */           checkbox = "";
/*      */         }
/*      */         
/* 1293 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1295 */           actions = "";
/*      */         }
/* 1297 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1299 */           checkbox = "";
/*      */         }
/*      */         
/* 1302 */         String resourcelink = "";
/*      */         
/* 1304 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1306 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1310 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1313 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1314 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1315 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1316 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1317 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1318 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1319 */         if (!isIt360)
/*      */         {
/* 1321 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1325 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1328 */         toreturn.append("</tr>");
/* 1329 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1331 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1332 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1336 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1337 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1340 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1344 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1346 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1347 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1348 */             toreturn.append(assocMessage);
/* 1349 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1350 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1351 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1352 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1358 */         String resourcelink = null;
/* 1359 */         boolean hideEditLink = false;
/* 1360 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1362 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1363 */           hideEditLink = true;
/* 1364 */           if (isIt360)
/*      */           {
/* 1366 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1370 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1372 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1374 */           hideEditLink = true;
/* 1375 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1376 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1381 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1384 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1385 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1386 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1387 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1388 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1389 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1390 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1391 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1392 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1393 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1394 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1395 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1396 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1398 */         if (hideEditLink)
/*      */         {
/* 1400 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1402 */         if (!forInventory)
/*      */         {
/* 1404 */           removefromgroup = "";
/*      */         }
/* 1406 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1407 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1408 */           actions = actions + configcustomfields;
/*      */         }
/* 1410 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1412 */           actions = editlink + actions;
/*      */         }
/* 1414 */         String managedLink = "";
/* 1415 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1417 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1418 */           actions = "";
/* 1419 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1420 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1423 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1425 */           checkbox = "";
/*      */         }
/*      */         
/* 1428 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1430 */           actions = "";
/*      */         }
/* 1432 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1433 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1434 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1435 */         if (isIt360)
/*      */         {
/* 1437 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1441 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1443 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1444 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1445 */         if (!isIt360)
/*      */         {
/* 1447 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1451 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1453 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1456 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1463 */       StringBuilder toreturn = new StringBuilder();
/* 1464 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1465 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1466 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1467 */       String title = "";
/* 1468 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1469 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1470 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1471 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1473 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1475 */       else if ("5".equals(severity))
/*      */       {
/* 1477 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1481 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1483 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1484 */       toreturn.append(v);
/*      */       
/* 1486 */       toreturn.append(link);
/* 1487 */       if (severity == null)
/*      */       {
/* 1489 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1491 */       else if (severity.equals("5"))
/*      */       {
/* 1493 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1495 */       else if (severity.equals("4"))
/*      */       {
/* 1497 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1499 */       else if (severity.equals("1"))
/*      */       {
/* 1501 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1506 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1508 */       toreturn.append("</a>");
/* 1509 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1513 */       ex.printStackTrace();
/*      */     }
/* 1515 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1522 */       StringBuilder toreturn = new StringBuilder();
/* 1523 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1524 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1525 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1526 */       if (message == null)
/*      */       {
/* 1528 */         message = "";
/*      */       }
/*      */       
/* 1531 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1532 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1534 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1535 */       toreturn.append(v);
/*      */       
/* 1537 */       toreturn.append(link);
/*      */       
/* 1539 */       if (severity == null)
/*      */       {
/* 1541 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1543 */       else if (severity.equals("5"))
/*      */       {
/* 1545 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1547 */       else if (severity.equals("1"))
/*      */       {
/* 1549 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1554 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1556 */       toreturn.append("</a>");
/* 1557 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1563 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1566 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1567 */     if (invokeActions != null) {
/* 1568 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1569 */       while (iterator.hasNext()) {
/* 1570 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1571 */         if (actionmap.containsKey(actionid)) {
/* 1572 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1577 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1581 */     String actionLink = "";
/* 1582 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1583 */     String query = "";
/* 1584 */     ResultSet rs = null;
/* 1585 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1586 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1587 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1588 */       actionLink = "method=" + methodName;
/*      */     }
/* 1590 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1591 */       actionLink = methodName;
/*      */     }
/* 1593 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1594 */     Iterator itr = methodarglist.iterator();
/* 1595 */     boolean isfirstparam = true;
/* 1596 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1597 */     while (itr.hasNext()) {
/* 1598 */       HashMap argmap = (HashMap)itr.next();
/* 1599 */       String argtype = (String)argmap.get("TYPE");
/* 1600 */       String argname = (String)argmap.get("IDENTITY");
/* 1601 */       String paramname = (String)argmap.get("PARAMETER");
/* 1602 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1603 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1604 */         isfirstparam = false;
/* 1605 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1607 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1611 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1615 */         actionLink = actionLink + "&";
/*      */       }
/* 1617 */       String paramValue = null;
/* 1618 */       String tempargname = argname;
/* 1619 */       if (commonValues.getProperty(tempargname) != null) {
/* 1620 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1623 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1624 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1625 */           if (dbType.equals("mysql")) {
/* 1626 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1629 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1631 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1633 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1634 */             if (rs.next()) {
/* 1635 */               paramValue = rs.getString("VALUE");
/* 1636 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1640 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1644 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1647 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1652 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1653 */           paramValue = rowId;
/*      */         }
/* 1655 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1656 */           paramValue = managedObjectName;
/*      */         }
/* 1658 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1659 */           paramValue = resID;
/*      */         }
/* 1661 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1662 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1665 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1667 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1668 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1669 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1671 */     return actionLink;
/*      */   }
/*      */   
/* 1674 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1675 */     String dependentAttribute = null;
/* 1676 */     String align = "left";
/*      */     
/* 1678 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1679 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1680 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1681 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1682 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1683 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1684 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1685 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1686 */       align = "center";
/*      */     }
/*      */     
/* 1689 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1690 */     String actualdata = "";
/*      */     
/* 1692 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1693 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1694 */         actualdata = availValue;
/*      */       }
/* 1696 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1697 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1701 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1702 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1705 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1711 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1712 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1713 */       toreturn.append("<table>");
/* 1714 */       toreturn.append("<tr>");
/* 1715 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1716 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1717 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1718 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1719 */         String toolTip = "";
/* 1720 */         String hideClass = "";
/* 1721 */         String textStyle = "";
/* 1722 */         boolean isreferenced = true;
/* 1723 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1724 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1725 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1726 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1728 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1729 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1730 */           while (valueList.hasMoreTokens()) {
/* 1731 */             String dependentVal = valueList.nextToken();
/* 1732 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1733 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1734 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1736 */               toolTip = "";
/* 1737 */               hideClass = "";
/* 1738 */               isreferenced = false;
/* 1739 */               textStyle = "disabledtext";
/* 1740 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1744 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1745 */           toolTip = "";
/* 1746 */           hideClass = "";
/* 1747 */           isreferenced = false;
/* 1748 */           textStyle = "disabledtext";
/* 1749 */           if (dependentImageMap != null) {
/* 1750 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1751 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1754 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1758 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1759 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1760 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1761 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1762 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1763 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1765 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1766 */           if (isreferenced) {
/* 1767 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1771 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1772 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1773 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1774 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1775 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1776 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1778 */           toreturn.append("</span>");
/* 1779 */           toreturn.append("</a>");
/* 1780 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1783 */       toreturn.append("</tr>");
/* 1784 */       toreturn.append("</table>");
/* 1785 */       toreturn.append("</td>");
/*      */     } else {
/* 1787 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1790 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1794 */     String colTime = null;
/* 1795 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1796 */     if ((rows != null) && (rows.size() > 0)) {
/* 1797 */       Iterator<String> itr = rows.iterator();
/* 1798 */       String maxColQuery = "";
/* 1799 */       for (;;) { if (itr.hasNext()) {
/* 1800 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1801 */           ResultSet maxCol = null;
/*      */           try {
/* 1803 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1804 */             while (maxCol.next()) {
/* 1805 */               if (colTime == null) {
/* 1806 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1809 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1818 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1820 */               if (maxCol != null)
/* 1821 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1823 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1818 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1820 */               if (maxCol != null)
/* 1821 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1823 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1828 */     return colTime;
/*      */   }
/*      */   
/* 1831 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1832 */     tablename = null;
/* 1833 */     ResultSet rsTable = null;
/* 1834 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1836 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1837 */       while (rsTable.next()) {
/* 1838 */         tablename = rsTable.getString("DATATABLE");
/* 1839 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1840 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1853 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1844 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1847 */         if (rsTable != null)
/* 1848 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1850 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1856 */     String argsList = "";
/* 1857 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1859 */       if (showArgsMap.get(row) != null) {
/* 1860 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1861 */         if (showArgslist != null) {
/* 1862 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1863 */             if (argsList.trim().equals("")) {
/* 1864 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1867 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1874 */       e.printStackTrace();
/* 1875 */       return "";
/*      */     }
/* 1877 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1882 */     String argsList = "";
/* 1883 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1886 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1888 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1889 */         if (hideArgsList != null)
/*      */         {
/* 1891 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1893 */             if (argsList.trim().equals(""))
/*      */             {
/* 1895 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1899 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1907 */       ex.printStackTrace();
/*      */     }
/* 1909 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1913 */     StringBuilder toreturn = new StringBuilder();
/* 1914 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1921 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1922 */       Iterator itr = tActionList.iterator();
/* 1923 */       while (itr.hasNext()) {
/* 1924 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1925 */         String confirmmsg = "";
/* 1926 */         String link = "";
/* 1927 */         String isJSP = "NO";
/* 1928 */         HashMap tactionMap = (HashMap)itr.next();
/* 1929 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1930 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1931 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1932 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1933 */           (actionmap.containsKey(actionId))) {
/* 1934 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1935 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1936 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1937 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1938 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1940 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1946 */           if (isTableAction) {
/* 1947 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1950 */             tableName = "Link";
/* 1951 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1952 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1953 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1954 */             toreturn.append("</a></td>");
/*      */           }
/* 1956 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1957 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1958 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1959 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1965 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1971 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1973 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1974 */       Properties prop = (Properties)node.getUserObject();
/* 1975 */       String mgID = prop.getProperty("label");
/* 1976 */       String mgName = prop.getProperty("value");
/* 1977 */       String isParent = prop.getProperty("isParent");
/* 1978 */       int mgIDint = Integer.parseInt(mgID);
/* 1979 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1981 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1983 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1984 */       if (node.getChildCount() > 0)
/*      */       {
/* 1986 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1988 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1990 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1992 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 1996 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2001 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2003 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2005 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2007 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2011 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2014 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2015 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2017 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2021 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2023 */       if (node.getChildCount() > 0)
/*      */       {
/* 2025 */         builder.append("<UL>");
/* 2026 */         printMGTree(node, builder);
/* 2027 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2032 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2033 */     StringBuffer toReturn = new StringBuffer();
/* 2034 */     String table = "-";
/*      */     try {
/* 2036 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2037 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2038 */       float total = 0.0F;
/* 2039 */       while (it.hasNext()) {
/* 2040 */         String attName = (String)it.next();
/* 2041 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2042 */         boolean roundOffData = false;
/* 2043 */         if ((data != null) && (!data.equals(""))) {
/* 2044 */           if (data.indexOf(",") != -1) {
/* 2045 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2048 */             float value = Float.parseFloat(data);
/* 2049 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2052 */             total += value;
/* 2053 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2056 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2061 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2062 */       while (attVsWidthList.hasNext()) {
/* 2063 */         String attName = (String)attVsWidthList.next();
/* 2064 */         String data = (String)attVsWidthProps.get(attName);
/* 2065 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2066 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2067 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2068 */         String className = (String)graphDetails.get("ClassName");
/* 2069 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2070 */         if (percentage < 1.0F)
/*      */         {
/* 2072 */           data = percentage + "";
/*      */         }
/* 2074 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2076 */       if (toReturn.length() > 0) {
/* 2077 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2081 */       e.printStackTrace();
/*      */     }
/* 2083 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2089 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2090 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2091 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2092 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2093 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2094 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2095 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2096 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2097 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2100 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2101 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2102 */       splitvalues[0] = multiplecondition.toString();
/* 2103 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2106 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2111 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2112 */     if (thresholdType != 3) {
/* 2113 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2114 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2115 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2116 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2117 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2118 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2120 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2121 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2122 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2123 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2124 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2125 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2127 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2128 */     if (updateSelected != null) {
/* 2129 */       updateSelected[0] = "selected";
/*      */     }
/* 2131 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2136 */       StringBuffer toreturn = new StringBuffer("");
/* 2137 */       if (commaSeparatedMsgId != null) {
/* 2138 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2139 */         int count = 0;
/* 2140 */         while (msgids.hasMoreTokens()) {
/* 2141 */           String id = msgids.nextToken();
/* 2142 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2143 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2144 */           count++;
/* 2145 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2146 */             if (toreturn.length() == 0) {
/* 2147 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2149 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2150 */             if (!image.trim().equals("")) {
/* 2151 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2153 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2154 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2157 */         if (toreturn.length() > 0) {
/* 2158 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2162 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2165 */       e.printStackTrace(); }
/* 2166 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2172 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2178 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2179 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2203 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2207 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2224 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2228 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2229 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2230 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2231 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2232 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2233 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2235 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.release();
/* 2236 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.release();
/* 2237 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2239 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.release();
/* 2240 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.release();
/* 2241 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2242 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2250 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2253 */     JspWriter out = null;
/* 2254 */     Object page = this;
/* 2255 */     JspWriter _jspx_out = null;
/* 2256 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2260 */       response.setContentType("text/html;charset=UTF-8");
/* 2261 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2263 */       _jspx_page_context = pageContext;
/* 2264 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2265 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2266 */       session = pageContext.getSession();
/* 2267 */       out = pageContext.getOut();
/* 2268 */       _jspx_out = out;
/*      */       
/* 2270 */       out.write("<!--$Id$-->\n");
/*      */       
/* 2272 */       request.setAttribute("HelpKey", "Configure Mail Server");
/*      */       
/* 2274 */       out.write("\n\n\n\n\n\n\n\n");
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
/* 2431 */                     out.write("\n\n<script>\n function showSMTP() \n         { \n    \n           toggleDiv('smtpauthinfo'); \n           if(document.AMActionForm.smtpUserName.value=='') \n           { \n                   val=document.AMActionForm.emailid.value \n                           if(val.indexOf('@')!=-1) \n                           { \n                                   document.AMActionForm.smtpUserName.value=val.substring(0,val.indexOf('@')); \n    \n                           } \n                           else \n                           { \n                                   document.AMActionForm.smtpUserName.value=val; \n                           } \n           } \n    \n         } \nfunction Call1()  \n{\n\tif(document.AMActionForm.popenabled.checked==true)\n\t{\n\n\t\t{\n\t\t    showDiv('popHost');\n\t\t\tshowDiv('popPort');\n\t\t\tshowDiv('username');\n\t\t\tshowDiv('password');\n\t\t}\n\t}\n\telse\n\t{\n\t\thideDiv('popHost');\n\t\thideDiv('popPort');\n\t\thideDiv('username');\n\t\thideDiv('password');\n\t}\n}\n\nfunction submitForm()\n{\n\t");
/* 2432 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2434 */                     out.write(10);
/* 2435 */                     out.write(9);
/*      */                     
/* 2437 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2438 */                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2439 */                     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                     
/* 2441 */                     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2442 */                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2443 */                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                       for (;;) {
/* 2445 */                         out.write(" \n\tif(trimAll(document.AMActionForm.displayname.value)=='')\n\t{\n\t  alert(\"");
/* 2446 */                         out.print(FormatUtil.getString("am.webclient.newscript.alert.displaynameempty.text"));
/* 2447 */                         out.write("\");\n\t  document.AMActionForm.displayname.select();\n\t  return;\n\t}\n\tvar tempType = document.AMActionForm.type.value;\n\tif(tempType=='PHP')\n\t{\n            var val=trimAll(document.AMActionForm.serverpath.value);\n            if(val=='')\n            {\n                alert(\"");
/* 2448 */                         out.print(FormatUtil.getString("am.webclient.php.phppath.text"));
/* 2449 */                         out.write("\");\n                return;\n            }\n\t}\n    else if(tempType=='MAIL-server')\n\t{\n\t var val1=trimAll(document.AMActionForm.smtpPort.value);\n\t if(val1=='')\n\t {\n\t    alert('");
/* 2450 */                         out.print(FormatUtil.getString("am.webclient.mailserver.js.smtp.alert"));
/* 2451 */                         out.write("');\n\t\treturn;\n\t }\n                if(document.AMActionForm.popenabled.checked == true)\n\t\t{\n                    temp = trimAll(document.AMActionForm.popHost.value); \n                    if((temp == '')  )\n                    {\n                        alert('");
/* 2452 */                         out.print(FormatUtil.getString("am.webclient.mailserver.js.pop.alert"));
/* 2453 */                         out.write("');\n                            document.AMActionForm.popHost.select();\n                            return;\n                    }\n                    temp = trimAll(document.AMActionForm.popPort.value); \n                    if((temp == '') || !(isPositiveInteger(temp)))\n                    {\n                        alert('");
/* 2454 */                         out.print(FormatUtil.getString("am.webclient.mailserver.js.popport.alert"));
/* 2455 */                         out.write("');\n                            document.AMActionForm.popPort.select();\n                            return;\n                    }\n\n                    temp = trimAll(document.AMActionForm.username.value); \n                    if((temp == '')  )\n                    {\n                        alert('");
/* 2456 */                         out.print(FormatUtil.getString("am.webclient.mailserver.js.popusername.alert"));
/* 2457 */                         out.write("');\n                            document.AMActionForm.username.select();\n                            return;\n                    }\n                    temp = trimAll(document.AMActionForm.password.value); \n                    if((temp == '')  )\n                    {\n                        if(!confirm('");
/* 2458 */                         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/*      */                           return;
/* 2460 */                         out.write("'))\n                        {\n                            return;\n                        }\n                    }\n                    temp = trimAll(document.AMActionForm.emailid.value); \n                    if((temp == '')  )\n                    {\n                         alert('");
/* 2461 */                         out.print(FormatUtil.getString("am.webclient.mailserver.js.emailid.alert"));
/* 2462 */                         out.write("');\n                            document.AMActionForm.emailid.select();\n                            return;\n                            \n                    }\n\t\t}\n\t \n\t}\n\t\t\n\tvar poll=trimAll(document.AMActionForm.pollInterval.value);\n\tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0' )\n\t{\n                alert(\"");
/* 2463 */                         out.print(FormatUtil.getString("am.webclient.common.validpollinginterval.text"));
/* 2464 */                         out.write("\");\n\t\treturn;\n\t}\n\t\n\tdocument.AMActionForm.submit();\n\t");
/* 2465 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2466 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2470 */                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2471 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */                     }
/*      */                     else {
/* 2474 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2475 */                       out.write("\n}\n</script>\n");
/* 2476 */                       Hashtable motypedisplaynames = null;
/* 2477 */                       synchronized (application) {
/* 2478 */                         motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2479 */                         if (motypedisplaynames == null) {
/* 2480 */                           motypedisplaynames = new Hashtable();
/* 2481 */                           _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                         }
/*      */                       }
/* 2484 */                       out.write(10);
/*      */                       
/* 2486 */                       String type = request.getParameter("type");
/* 2487 */                       String appname = request.getParameter("name");
/* 2488 */                       String haid = request.getParameter("haid");
/* 2489 */                       String smtpPort = request.getParameter("smtpPort");
/* 2490 */                       String resourceName = request.getParameter("resourcename");
/* 2491 */                       if (smtpPort == null)
/* 2492 */                         smtpPort = "";
/* 2493 */                       String popPort = request.getParameter("popPort");
/* 2494 */                       if (popPort == null)
/* 2495 */                         popPort = "";
/* 2496 */                       String popHost = request.getParameter("popHost");
/* 2497 */                       if (popHost == null)
/* 2498 */                         popHost = "";
/* 2499 */                       String mailMsg = request.getParameter("mailMsg");
/* 2500 */                       if (mailMsg == null)
/* 2501 */                         mailMsg = "";
/* 2502 */                       String pollInterval = request.getParameter("pollInterval");
/* 2503 */                       if (pollInterval == null)
/* 2504 */                         pollInterval = "5";
/* 2505 */                       String username = request.getParameter("username");
/* 2506 */                       if (username == null)
/* 2507 */                         username = "";
/* 2508 */                       String password = (String)request.getAttribute("password");
/* 2509 */                       if (password == null) {
/* 2510 */                         password = "";
/*      */                       }
/*      */                       
/* 2513 */                       String resID = request.getParameter("resourceid");
/*      */                       
/*      */ 
/* 2516 */                       out.write(10);
/* 2517 */                       out.write(10);
/*      */                       
/* 2519 */                       org.apache.struts.util.TokenProcessor token = org.apache.struts.util.TokenProcessor.getInstance();
/* 2520 */                       token.saveToken(request);
/*      */                       
/*      */ 
/* 2523 */                       out.write("\n\n\n  \n\n  ");
/*      */                       
/* 2525 */                       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 2526 */                       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2527 */                       _jspx_th_html_005fform_005f0.setParent(null);
/*      */                       
/* 2529 */                       _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*      */                       
/* 2531 */                       _jspx_th_html_005fform_005f0.setMethod("post");
/*      */                       
/* 2533 */                       _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2534 */                       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2535 */                       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                         for (;;) {
/* 2537 */                           out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n    <td height=\"28\"   class=\"tableheading\">");
/* 2538 */                           out.print(FormatUtil.getString("am.webclient.common.configurationdetails.text"));
/* 2539 */                           out.write("\n      \n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 2540 */                           out.print(request.getParameter("name"));
/* 2541 */                           out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 2542 */                           out.print(request.getParameter("haid"));
/* 2543 */                           out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 2544 */                           out.print(type);
/* 2545 */                           out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 2546 */                           out.print(type);
/* 2547 */                           out.write(34);
/* 2548 */                           out.write(62);
/* 2549 */                           out.write(10);
/* 2550 */                           if ((type.equals("IIS-server")) || (type.equals("TELNET")))
/*      */                           {
/*      */ 
/* 2553 */                             out.write("\n<input type=\"hidden\" name=\"method\" value=\"configureIIS\">\n");
/*      */                           }
/* 2555 */                           else if (type.equals("PHP"))
/*      */                           {
/*      */ 
/* 2558 */                             out.write("\n    <input type=\"hidden\" name=\"method\" value=\"configurePHP\">\n");
/*      */                           } else {
/* 2560 */                             out.write("\n    \n<input type=\"hidden\" name=\"method\" value=\"configureMailOrWeb\">\n ");
/*      */                           }
/* 2562 */                           out.write("\n       \n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2563 */                           out.print(request.getParameter("resourceid"));
/* 2564 */                           out.write("\">\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 2565 */                           out.print(request.getParameter("resourcename"));
/* 2566 */                           out.write("\">\n<input type=\"hidden\" name=\"moname\" value=\"");
/* 2567 */                           out.print(request.getParameter("moname"));
/* 2568 */                           out.write("\">\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n<span class=\"bodytextboldwhiteun\" ><a href=\"javascript:hideDiv('edit')\" class=\"staticlinks\">");
/* 2569 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 2570 */                           out.write("</a></span>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n\t<TR>\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 2571 */                           out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 2572 */                           out.write("<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"77%\"> \n\t");
/* 2573 */                           if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 2575 */                           out.write("\t\t\n\t</TD>\n\t</TR>\n");
/*      */                           
/* 2577 */                           if ((type.equals("PHP")) || (type.equals("IIS-server")))
/*      */                           {
/*      */ 
/* 2580 */                             out.write("\n<TR> \n\t           <TD height=\"28\" class=\"bodytext\" >&nbsp;</TD>\t           \n\t           ");
/*      */                             
/* 2582 */                             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2583 */                             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2584 */                             _jspx_th_c_005fif_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                             
/* 2586 */                             _jspx_th_c_005fif_005f0.setTest("${sslenabled_val}");
/* 2587 */                             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2588 */                             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                               for (;;) {
/* 2590 */                                 out.write("\n                    <TD height=\"28\" colspan=\"2\" > ");
/* 2591 */                                 if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2593 */                                 out.write("<span class=\"bodytext\"> ");
/* 2594 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssl"));
/* 2595 */                                 out.write(" </span>\n\t           ");
/* 2596 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2597 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2601 */                             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2602 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                             }
/*      */                             
/* 2605 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2606 */                             out.write("\n\t           ");
/*      */                             
/* 2608 */                             IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2609 */                             _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2610 */                             _jspx_th_c_005fif_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                             
/* 2612 */                             _jspx_th_c_005fif_005f1.setTest("${!sslenabled_val}");
/* 2613 */                             int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2614 */                             if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                               for (;;) {
/* 2616 */                                 out.write("\n                    <TD height=\"28\" colspan=\"2\" > ");
/* 2617 */                                 if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                   return;
/* 2619 */                                 out.write("<span class=\"bodytext\"> ");
/* 2620 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssl"));
/* 2621 */                                 out.write("</span>\n\t           ");
/* 2622 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2623 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2627 */                             if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2628 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                             }
/*      */                             
/* 2631 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2632 */                             out.write("\n\t \n\t           </TD>\n\t</TR>\n");
/*      */                           }
/*      */                           
/* 2635 */                           if (type.equals("MAIL-server"))
/*      */                           {
/*      */ 
/* 2638 */                             out.write("\n\t\t<TR>\n                <TD height=\"28\" class=\"bodytext\">");
/* 2639 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.smtpport"));
/* 2640 */                             out.write("<span class=\"mandatory\">*</span></TD>\n\t\t<TD height=\"28\"> <INPUT NAME=\"smtpPort\" TYPE=\"text\" class=\"formtext\" VALUE=\"");
/* 2641 */                             out.print(smtpPort);
/* 2642 */                             out.write("\" SIZE=\"10\">\n\t\t</TD>\n\t\t</TR>\n\t\t\n\t\t<TR>\n\t\t<TD height=\"28\" class=\"bodytext\" colspan=\"2\">\n\t\t");
/* 2643 */                             if (_jspx_meth_html_005fcheckbox_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                               return;
/* 2645 */                             out.write(32);
/* 2646 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.smtpauth"));
/* 2647 */                             out.write("\n\t\t                   <div id=\"smtpauthinfo\"   style=\"DISPLAY: none\">\n\t\t\t\t                    <TABLE  BORDER=\"0\" CELLSPACING=2 CELLPADDING=5 WIDTH=\"60%\"  class=\"lrtbdarkborder\" align=\"center\">\n\t\t\t\t         \t\n\t\t\t\t         \t   <TR  >\n\t\t\t\t         \t   \t<TD class=\"bodytext\" height=\"28\" width=\"25%\">");
/* 2648 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.smtp.username"));
/* 2649 */                             out.write(" </TD>\n\t\t\t\t         \t   \t<TD> ");
/* 2650 */                             if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                               return;
/* 2652 */                             out.write(" </TD>\n\t\t\t\t         \t   \t</tr>\n\t\t\t\t         \t\n\t\t\t\t         \t   <TR  >\n\t\t\t\t         \t   \t<TD class=\"bodytext\">");
/* 2653 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.smtp.password"));
/* 2654 */                             out.write("  </TD>\n\t\t\t\t         \t   \t<TD> ");
/* 2655 */                             if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                               return;
/* 2657 */                             out.write(" </TD>\n\t\t\t\t         \t   \t</tr>\t   \t\n\t\t\t\t         \t   </TABLE>\n\t\t\t\t         \t   \n                 </div>\n                 </TD>\n\t\t</TR>\n\n\n\n    <td height=\"28\" class=\"bodytext\" colspan=\"3\">");
/* 2658 */                             if (_jspx_meth_html_005fcheckbox_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                               return;
/* 2660 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.enablepop"));
/* 2661 */                             out.write("</td>\n\t</tr>\n\t</TR>\n\t<tr>\n\t<td width=\"100%\" colspan=\"2\">\n\n\t");
/* 2662 */                             if (_jspx_meth_c_005fif_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                               return;
/* 2664 */                             out.write(10);
/* 2665 */                             out.write(9);
/* 2666 */                             if (_jspx_meth_c_005fif_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                               return;
/* 2668 */                             out.write("\n\n\t\n\t\n\t<table width=\"100%\">\n\t<tr>\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 2669 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.pophost"));
/* 2670 */                             out.write(" </TD>\n\t<TD height=\"28\" width=\"78%\">");
/* 2671 */                             if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                               return;
/* 2673 */                             out.write(" &nbsp;<span class=\"bodytext\"></span> </TD>\n\t</TR>\n\t</table>\n\t</div>\n\t</td>\n\t</tr>\n\n\t<tr>\n\t<td width=\"100%\" colspan=\"2\">\n\t<div id=\"popPort\" style=\"display:none\">\n\t<table width=\"100%\">\n\t<tr>\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 2674 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.popport"));
/* 2675 */                             out.write("<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"78%\">");
/*      */                             
/* 2677 */                             TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 2678 */                             _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 2679 */                             _jspx_th_html_005ftext_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                             
/* 2681 */                             _jspx_th_html_005ftext_005f3.setProperty("popPort");
/*      */                             
/* 2683 */                             _jspx_th_html_005ftext_005f3.setValue(popPort);
/*      */                             
/* 2685 */                             _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */                             
/* 2687 */                             _jspx_th_html_005ftext_005f3.setSize("15");
/* 2688 */                             int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 2689 */                             if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 2690 */                               this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3); return;
/*      */                             }
/*      */                             
/* 2693 */                             this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 2694 */                             out.write(" &nbsp;<span class=\"bodytext\"></span> </TD>\n\t</TR>\n\t</table>\n\t</div>\n\t</td>\n\t</tr>\n\t<tr>\n\t<td width=\"100%\" colspan=\"2\">\n\t<div id=\"username\" style=\"display:none\">\n\t<table width=\"100%\">\n\t<tr>\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 2695 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 2696 */                             out.write("<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"78%\">");
/*      */                             
/* 2698 */                             TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 2699 */                             _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 2700 */                             _jspx_th_html_005ftext_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                             
/* 2702 */                             _jspx_th_html_005ftext_005f4.setProperty("username");
/*      */                             
/* 2704 */                             _jspx_th_html_005ftext_005f4.setValue(username);
/*      */                             
/* 2706 */                             _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/*      */                             
/* 2708 */                             _jspx_th_html_005ftext_005f4.setSize("15");
/* 2709 */                             int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 2710 */                             if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 2711 */                               this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4); return;
/*      */                             }
/*      */                             
/* 2714 */                             this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 2715 */                             out.write(" &nbsp;\n<span class=\"mandatory\"></span> </TD>\n\t</TR>\n\t</table>\n\t</div>\n\t</td>\n\t</tr>\n\n\t<tr>\n\t<td width=\"100%\" colspan=\"2\">\n\t<div id=\"password\" style=\"display:none\">\n\t<table width=\"100%\">\n\t<tr>\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 2716 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/* 2717 */                             out.write("<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"78%\">");
/* 2718 */                             if (_jspx_meth_html_005fpassword_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                               return;
/* 2720 */                             out.write("</TD>\n\t</TR>\n\t</table>\n\t</div>\n\t</td>\n\t</tr>\n\n\n \n    <!--");
/*      */                             
/* 2722 */                             IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2723 */                             _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2724 */                             _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                             
/* 2726 */                             _jspx_th_c_005fif_005f4.setTest("${!empty param.username}");
/* 2727 */                             int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2728 */                             if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                               for (;;) {
/* 2730 */                                 out.write(" \n\n\t<TR>\n\t<TD height=\"28\" width=\"13%\" class=\"bodytext\">POP User Name <span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"87%\"> <INPUT NAME=\"username\" TYPE=\"text\" class=\"formtext\" VALUE=\"");
/* 2731 */                                 out.print(username);
/* 2732 */                                 out.write("\" SIZE=\"15\">\n\t</TD>\n\t</TR>\n\t<TR>\n\t<TD height=\"28\" class=\"bodytext\">POP Password <span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\"> <INPUT NAME=\"password\" TYPE=\"password\" class=\"formtext\" SIZE=\"15\">\n\t</TD>\n\t</TR>\n   ");
/* 2733 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2734 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2738 */                             if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2739 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                             }
/*      */                             
/* 2742 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2743 */                             out.write("\t\n\t<TR>\n        <TD height=\"28\" class=\"bodytext\">Smtp Port <span class=\"mandatory\">*</span></TD>\n        <TD height=\"28\"> <INPUT NAME=\"smtpPort\" TYPE=\"text\" class=\"formtext\" VALUE=\"");
/* 2744 */                             out.print(smtpPort);
/* 2745 */                             out.write("\" SIZE=\"10\">\n        </TD>\n        </TR>\n\n\t<TR>\n        <TD height=\"28\" class=\"bodytext\">Pop Port <span class=\"mandatory\">*</span></TD>\n        <TD height=\"28\"> <INPUT NAME=\"popPort\" TYPE=\"text\" class=\"formtext\" VALUE=\"");
/* 2746 */                             out.print(popPort);
/* 2747 */                             out.write("\" SIZE=\"10\">\n        </TD>\n        </TR>-->\n\n   <TR>\n        <TD height=\"28\" class=\"bodytext\">");
/* 2748 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.emailid"));
/* 2749 */                             out.write("<span class=\"mandatory\">*</span></TD>\n        <TD height=\"28\"> <INPUT NAME=\"emailid\" TYPE=\"text\" class=\"formtext\" VALUE=\"");
/* 2750 */                             if (_jspx_meth_c_005fout_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                               return;
/* 2752 */                             out.write("\" SIZE=\"50\">\n        </TD>\n        </TR>\n        <TR>\n    <TD height=\"28\" class=\"bodytext\">");
/* 2753 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.message"));
/* 2754 */                             out.write(" <span class=\"mandatory\">*</span></TD>\n        <TD height=\"28\"> <INPUT NAME=\"mailMsg\" TYPE=\"text\" class=\"formtext\" VALUE=\"");
/* 2755 */                             out.print(mailMsg);
/* 2756 */                             out.write("\" SIZE=\"50\">\n        </TD>\n        </TR>\n");
/*      */                           }
/*      */                           
/* 2759 */                           if (type.equals("PHP"))
/*      */                           {
/* 2761 */                             AMConnectionPool pool1 = AMConnectionPool.getInstance();
/* 2762 */                             String path = "/phpstats.php";
/*      */                             try
/*      */                             {
/* 2765 */                               ResultSet rs1 = AMConnectionPool.executeQueryStmt("select path from AM_PHPPATH where resourceid=" + resID);
/* 2766 */                               if (rs1.next())
/*      */                               {
/* 2768 */                                 path = rs1.getString(1);
/*      */                               }
/*      */                             }
/*      */                             catch (Exception exc) {}
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/* 2776 */                             out.write("\n        <TR>\n\t<TD height=\"28\" class=\"bodytext\">");
/* 2777 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.phppath"));
/* 2778 */                             out.write("<span class=\"mandatory\">*</span></TD>\n\t<!--TD height=\"28\"> ");
/*      */                             
/* 2780 */                             TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 2781 */                             _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 2782 */                             _jspx_th_html_005ftext_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                             
/* 2784 */                             _jspx_th_html_005ftext_005f5.setProperty("serverpath");
/*      */                             
/* 2786 */                             _jspx_th_html_005ftext_005f5.setValue(path);
/*      */                             
/* 2788 */                             _jspx_th_html_005ftext_005f5.setStyleClass("formtext");
/*      */                             
/* 2790 */                             _jspx_th_html_005ftext_005f5.setSize("15");
/* 2791 */                             int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 2792 */                             if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 2793 */                               this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5); return;
/*      */                             }
/*      */                             
/* 2796 */                             this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 2797 */                             out.write("<span class=\"bodytext\"></span-->\n\t<TD height=\"28\"> <input name=\"serverpath\" type=\"text\" value=\"");
/* 2798 */                             out.print(path);
/* 2799 */                             out.write("\" class=\"formtext\" size=\"15\"><span class=\"bodytext\"></span>\n\t</TD>\n\t</TR>\n");
/*      */                           }
/*      */                           
/* 2802 */                           if (type.equals("IIS-server"))
/*      */                           {
/* 2804 */                             pollInterval = (String)request.getAttribute("polling");
/*      */                           }
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
/* 2833 */                           if (type.equals("TELNET"))
/*      */                           {
/* 2835 */                             pollInterval = (String)request.getAttribute("telnetpolling");
/*      */                           }
/*      */                           
/* 2838 */                           out.write("\n\t<TR>\n\t<TD height=\"28\" class=\"bodytext\">");
/* 2839 */                           out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 2840 */                           out.write("<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\"> <INPUT NAME=\"pollInterval\" TYPE=\"text\" value=\"");
/* 2841 */                           out.print(pollInterval);
/* 2842 */                           out.write("\" class=\"formtext\"  SIZE=\"15\"><span class=\"bodytext\">&nbsp;");
/* 2843 */                           out.print(FormatUtil.getString("am.webclient.urlmonitor.unitofpoll.text"));
/* 2844 */                           out.write("</span>\n\t</TD>\n\t</TR>\n \n</table>\n");
/*      */                           
/* 2846 */                           String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/*      */                           
/* 2848 */                           out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr> \n    <td width=\"13%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"87%\" class=\"tablebottom\">\n\n    <input name=\"but1\" type=\"button\"  class=\"buttons\" value=\"");
/*      */                           
/* 2850 */                           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2851 */                           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2852 */                           _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                           
/* 2854 */                           _jspx_th_c_005fif_005f5.setTest("${empty enabled}");
/* 2855 */                           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2856 */                           if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                             for (;;) {
/* 2858 */                               out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 2859 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2860 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2864 */                           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2865 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                           }
/*      */                           
/* 2868 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/*      */                           
/* 2870 */                           IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2871 */                           _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2872 */                           _jspx_th_c_005fif_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */                           
/* 2874 */                           _jspx_th_c_005fif_005f6.setTest("${!empty enabled}");
/* 2875 */                           int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2876 */                           if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                             for (;;) {
/* 2878 */                               out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 2879 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2880 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2884 */                           if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2885 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                           }
/*      */                           
/* 2888 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2889 */                           out.write("\" onClick=\"javascript:submitForm()\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons\" value=\"");
/* 2890 */                           out.print(cancel);
/* 2891 */                           out.write("\" onClick=\"javascript:toggleDiv('edit')\" /> \n     </td>\n  </tr>\n</table>\n");
/* 2892 */                           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2893 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2897 */                       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2898 */                         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */                       }
/*      */                       else {
/* 2901 */                         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2902 */                         out.write(10);
/* 2903 */                         out.write(9);
/* 2904 */                         if (_jspx_meth_c_005fif_005f7(_jspx_page_context))
/*      */                           return;
/* 2906 */                         out.write(10);
/* 2907 */                         out.write(9);
/* 2908 */                         out.write(9);
/* 2909 */                         if (_jspx_meth_c_005fif_005f8(_jspx_page_context))
/*      */                           return;
/* 2911 */                         out.write(10);
/*      */                       }
/* 2913 */                     } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2914 */         out = _jspx_out;
/* 2915 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2916 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2917 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2920 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2926 */     PageContext pageContext = _jspx_page_context;
/* 2927 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2929 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2930 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2931 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 2933 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2934 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2935 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 2937 */         out.write(" \n\talertUser();\n\treturn;\n\t");
/* 2938 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2939 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2943 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2944 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2945 */       return true;
/*      */     }
/* 2947 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2948 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2953 */     PageContext pageContext = _jspx_page_context;
/* 2954 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2956 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2957 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2958 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 2959 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 2960 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 2961 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2962 */         out = _jspx_page_context.pushBody();
/* 2963 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2964 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2967 */         out.write("password.empty.message");
/* 2968 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 2969 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2972 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2973 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2976 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 2977 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2978 */       return true;
/*      */     }
/* 2980 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2981 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2986 */     PageContext pageContext = _jspx_page_context;
/* 2987 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2989 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 2990 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 2991 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2993 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 2995 */     _jspx_th_html_005ftext_005f0.setSize("25");
/*      */     
/* 2997 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/* 2998 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 2999 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3000 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3001 */       return true;
/*      */     }
/* 3003 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3004 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3009 */     PageContext pageContext = _jspx_page_context;
/* 3010 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3012 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.get(CheckboxTag.class);
/* 3013 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 3014 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3016 */     _jspx_th_html_005fcheckbox_005f0.setProperty("sslenabled");
/*      */     
/* 3018 */     _jspx_th_html_005fcheckbox_005f0.setValue("checked");
/* 3019 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 3020 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 3021 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 3022 */       return true;
/*      */     }
/* 3024 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 3025 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3030 */     PageContext pageContext = _jspx_page_context;
/* 3031 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3033 */     CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 3034 */     _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 3035 */     _jspx_th_html_005fcheckbox_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3037 */     _jspx_th_html_005fcheckbox_005f1.setProperty("sslenabled");
/* 3038 */     int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 3039 */     if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 3040 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 3041 */       return true;
/*      */     }
/* 3043 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 3044 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3049 */     PageContext pageContext = _jspx_page_context;
/* 3050 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3052 */     CheckboxTag _jspx_th_html_005fcheckbox_005f2 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 3053 */     _jspx_th_html_005fcheckbox_005f2.setPageContext(_jspx_page_context);
/* 3054 */     _jspx_th_html_005fcheckbox_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3056 */     _jspx_th_html_005fcheckbox_005f2.setProperty("smtpauthenabled");
/*      */     
/* 3058 */     _jspx_th_html_005fcheckbox_005f2.setValue("true");
/*      */     
/* 3060 */     _jspx_th_html_005fcheckbox_005f2.setOnclick("javascript:showSMTP()");
/* 3061 */     int _jspx_eval_html_005fcheckbox_005f2 = _jspx_th_html_005fcheckbox_005f2.doStartTag();
/* 3062 */     if (_jspx_th_html_005fcheckbox_005f2.doEndTag() == 5) {
/* 3063 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 3064 */       return true;
/*      */     }
/* 3066 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 3067 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3072 */     PageContext pageContext = _jspx_page_context;
/* 3073 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3075 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 3076 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3077 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3079 */     _jspx_th_html_005ftext_005f1.setProperty("smtpUserName");
/*      */     
/* 3081 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/* 3082 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 3083 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 3084 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3085 */       return true;
/*      */     }
/* 3087 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3088 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3093 */     PageContext pageContext = _jspx_page_context;
/* 3094 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3096 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/* 3097 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 3098 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3100 */     _jspx_th_html_005fpassword_005f0.setProperty("smtpPassword");
/*      */     
/* 3102 */     _jspx_th_html_005fpassword_005f0.setValue("");
/*      */     
/* 3104 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/* 3105 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 3106 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 3107 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 3108 */       return true;
/*      */     }
/* 3110 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 3111 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3116 */     PageContext pageContext = _jspx_page_context;
/* 3117 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3119 */     CheckboxTag _jspx_th_html_005fcheckbox_005f3 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 3120 */     _jspx_th_html_005fcheckbox_005f3.setPageContext(_jspx_page_context);
/* 3121 */     _jspx_th_html_005fcheckbox_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3123 */     _jspx_th_html_005fcheckbox_005f3.setProperty("popenabled");
/*      */     
/* 3125 */     _jspx_th_html_005fcheckbox_005f3.setOnclick("javascript:Call1()");
/* 3126 */     int _jspx_eval_html_005fcheckbox_005f3 = _jspx_th_html_005fcheckbox_005f3.doStartTag();
/* 3127 */     if (_jspx_th_html_005fcheckbox_005f3.doEndTag() == 5) {
/* 3128 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 3129 */       return true;
/*      */     }
/* 3131 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 3132 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3137 */     PageContext pageContext = _jspx_page_context;
/* 3138 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3140 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3141 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3142 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3144 */     _jspx_th_c_005fif_005f2.setTest("${AMActionForm.popenabled==true}");
/* 3145 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3146 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3148 */         out.write("\n\t\t<div id=\"popHost\" style=\"display:block\">\n\t");
/* 3149 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3150 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3154 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3155 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3156 */       return true;
/*      */     }
/* 3158 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3159 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3164 */     PageContext pageContext = _jspx_page_context;
/* 3165 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3167 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3168 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3169 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3171 */     _jspx_th_c_005fif_005f3.setTest("${AMActionForm.popenabled==false}");
/* 3172 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3173 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 3175 */         out.write("\n\t\t<div id=\"popHost\" style=\"display:none\">\n\t");
/* 3176 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3177 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3181 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3182 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3183 */       return true;
/*      */     }
/* 3185 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3186 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3191 */     PageContext pageContext = _jspx_page_context;
/* 3192 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3194 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3195 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 3196 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3198 */     _jspx_th_html_005ftext_005f2.setProperty("popHost");
/*      */     
/* 3200 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 3202 */     _jspx_th_html_005ftext_005f2.setSize("15");
/* 3203 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 3204 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 3205 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3206 */       return true;
/*      */     }
/* 3208 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3209 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3214 */     PageContext pageContext = _jspx_page_context;
/* 3215 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3217 */     PasswordTag _jspx_th_html_005fpassword_005f1 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 3218 */     _jspx_th_html_005fpassword_005f1.setPageContext(_jspx_page_context);
/* 3219 */     _jspx_th_html_005fpassword_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3221 */     _jspx_th_html_005fpassword_005f1.setProperty("password");
/*      */     
/* 3223 */     _jspx_th_html_005fpassword_005f1.setValue("");
/*      */     
/* 3225 */     _jspx_th_html_005fpassword_005f1.setStyleClass("formtext");
/*      */     
/* 3227 */     _jspx_th_html_005fpassword_005f1.setSize("15");
/* 3228 */     int _jspx_eval_html_005fpassword_005f1 = _jspx_th_html_005fpassword_005f1.doStartTag();
/* 3229 */     if (_jspx_th_html_005fpassword_005f1.doEndTag() == 5) {
/* 3230 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
/* 3231 */       return true;
/*      */     }
/* 3233 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
/* 3234 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3239 */     PageContext pageContext = _jspx_page_context;
/* 3240 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3242 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3243 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3244 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3246 */     _jspx_th_c_005fout_005f0.setValue("${param.emailid}");
/* 3247 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3248 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3249 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3250 */       return true;
/*      */     }
/* 3252 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3253 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3258 */     PageContext pageContext = _jspx_page_context;
/* 3259 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3261 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3262 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3263 */     _jspx_th_c_005fif_005f7.setParent(null);
/*      */     
/* 3265 */     _jspx_th_c_005fif_005f7.setTest("${AMActionForm.popenabled==true}");
/* 3266 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3267 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 3269 */         out.write("\n\t\t<script>\n\t\tCall1();\n\t\t</script>\n\t");
/* 3270 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3271 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3275 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3276 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3277 */       return true;
/*      */     }
/* 3279 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3280 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3285 */     PageContext pageContext = _jspx_page_context;
/* 3286 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3288 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3289 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3290 */     _jspx_th_c_005fif_005f8.setParent(null);
/*      */     
/* 3292 */     _jspx_th_c_005fif_005f8.setTest("${AMActionForm.smtpauthenabled==true}");
/* 3293 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3294 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 3296 */         out.write("\n\t\t\t<script>\n\t\t\tshowSMTP();\n\t\t\t</script>\n\t");
/* 3297 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3298 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3302 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3303 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3304 */       return true;
/*      */     }
/* 3306 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3307 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ConfigureMailNWeb_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */