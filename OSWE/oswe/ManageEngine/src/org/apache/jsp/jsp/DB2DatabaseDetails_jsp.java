/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.db2.bean.DB2Graphs;
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
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
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class DB2DatabaseDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  713 */     java.net.InetAddress add = null;
/*  714 */     if (ip.equals("127.0.0.1")) {
/*  715 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  719 */       add = java.net.InetAddress.getByName(ip);
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
/*      */           catch (java.sql.SQLException e) {
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
/*      */   public java.util.Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
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
/* 2178 */   private static java.util.Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2179 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/* 2198 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2202 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2203 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2204 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2205 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2206 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2207 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2214 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2218 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2219 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
/* 2220 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2222 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange.release();
/* 2223 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/* 2224 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.release();
/* 2225 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2226 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2227 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2228 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
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
/* 2255 */       out.write("<!--$Id$-->\n");
/* 2256 */       DB2Graphs db2graph = null;
/* 2257 */       db2graph = (DB2Graphs)_jspx_page_context.getAttribute("db2graph", 1);
/* 2258 */       if (db2graph == null) {
/* 2259 */         db2graph = new DB2Graphs();
/* 2260 */         _jspx_page_context.setAttribute("db2graph", db2graph, 1);
/*      */       }
/* 2262 */       out.write(10);
/* 2263 */       ManagedApplication mo = null;
/* 2264 */       synchronized (application) {
/* 2265 */         mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 4);
/* 2266 */         if (mo == null) {
/* 2267 */           mo = new ManagedApplication();
/* 2268 */           _jspx_page_context.setAttribute("mo", mo, 4);
/*      */         }
/*      */       }
/* 2271 */       out.write("\n\n\n\n\n\n\n\n\n\n");
/* 2272 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2274 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2275 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2276 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2278 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2280 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2282 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2284 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2285 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2286 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2287 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2290 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2291 */         String available = null;
/* 2292 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2293 */         out.write(10);
/*      */         
/* 2295 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2296 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2297 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2299 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2301 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2303 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2305 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2306 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2307 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2308 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2311 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2312 */           String unavailable = null;
/* 2313 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2314 */           out.write(10);
/*      */           
/* 2316 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2317 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2318 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2320 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2322 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2324 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2326 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2327 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2328 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2329 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2332 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2333 */             String unmanaged = null;
/* 2334 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2335 */             out.write(10);
/*      */             
/* 2337 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2338 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2339 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2341 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2343 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2345 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2347 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2348 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2349 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2350 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2353 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2354 */               String scheduled = null;
/* 2355 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2356 */               out.write(10);
/*      */               
/* 2358 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2359 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2360 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2362 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2364 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2366 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2368 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2369 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2370 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2371 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2374 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2375 */                 String critical = null;
/* 2376 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2377 */                 out.write(10);
/*      */                 
/* 2379 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2380 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2381 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2383 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2385 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2387 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2389 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2390 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2391 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2392 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2395 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2396 */                   String clear = null;
/* 2397 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2398 */                   out.write(10);
/*      */                   
/* 2400 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2401 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2402 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2404 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2406 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2408 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2410 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2411 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2412 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2413 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2416 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2417 */                     String warning = null;
/* 2418 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2419 */                     out.write(10);
/* 2420 */                     out.write(10);
/*      */                     
/* 2422 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2423 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2425 */                     out.write(10);
/* 2426 */                     out.write(10);
/* 2427 */                     out.write(10);
/* 2428 */                     out.write(10);
/*      */                     
/* 2430 */                     FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(FormTag.class);
/* 2431 */                     _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2432 */                     _jspx_th_html_005fform_005f0.setParent(null);
/*      */                     
/* 2434 */                     _jspx_th_html_005fform_005f0.setAction("/showresource");
/*      */                     
/* 2436 */                     _jspx_th_html_005fform_005f0.setMethod("get");
/* 2437 */                     int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2438 */                     if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                       for (;;) {
/* 2440 */                         out.write("\n<input type=\"hidden\" name=\"method\" value=\"showdetails\">\n");
/* 2441 */                         if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2443 */                         out.write(10);
/* 2444 */                         if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2446 */                         out.write(10);
/* 2447 */                         if (_jspx_meth_am_005fhiddenparam_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2449 */                         out.write(10);
/* 2450 */                         if (_jspx_meth_am_005fhiddenparam_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2452 */                         out.write(10);
/* 2453 */                         if (_jspx_meth_am_005fhiddenparam_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2455 */                         out.write(10);
/* 2456 */                         if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2458 */                         out.write("\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n      <td align=\"center\" class=\"bodytext\">\n      ");
/* 2459 */                         out.print(FormatUtil.getString("am.webclient.db2.databaseselect"));
/* 2460 */                         out.write(" :\n\t\t");
/* 2461 */                         if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2463 */                         out.write("\n      </td>\n    </tr>\n  </table>\n");
/* 2464 */                         int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2465 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2469 */                     if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2470 */                       this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */                     }
/*      */                     else {
/* 2473 */                       this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2474 */                       out.write("\n<br>\n");
/*      */                       
/*      */ 
/* 2477 */                       String tooltip_databasename = FormatUtil.getString("am.webclient.db2.tooltip");
/* 2478 */                       String tooltip_aliasname = FormatUtil.getString("am.webclient.db2.aliasname.tooltip");
/* 2479 */                       String tooltip_databasepath = FormatUtil.getString("am.webclient.db2.dbpath.tooltip");
/* 2480 */                       String tooltip_databasestatus = FormatUtil.getString("am.webclient.db2.status.tooltip");
/* 2481 */                       String tooltip_databasesize = FormatUtil.getString("am.webclient.db2.dbsize.tooltip");
/* 2482 */                       String tooltip_connectedtime = FormatUtil.getString("am.webclient.db2.contime.tooltip");
/* 2483 */                       String tooltip_deadlockrate = FormatUtil.getString("am.webclient.db2.deadlockrate.tooltip");
/* 2484 */                       String tooltip_logutilization = FormatUtil.getString("am.webclient.db2.log.tooltip");
/* 2485 */                       String tooltip_sortsoverflowed = FormatUtil.getString("am.webclient.db2.overflow.tooltip");
/* 2486 */                       String tooltip_successfulsql = FormatUtil.getString("am.webclient.db2.success.tooltip");
/* 2487 */                       String tooltip_failedsql = FormatUtil.getString("am.webclient.db2.failed.tooltip");
/* 2488 */                       String tooltip_unitsofwork = FormatUtil.getString("am.webclient.db2.unitsofwork.tooltip");
/* 2489 */                       String tooltip_package = FormatUtil.getString("am.webclient.db2.package.tooltip");
/* 2490 */                       String tooltip_catalog = FormatUtil.getString("am.webclient.db2.catalog.tooltip");
/* 2491 */                       String tooltip_bufferpool = FormatUtil.getString("am.webclient.db2.bufferpool.tooltip");
/* 2492 */                       String tooltip_indexpage = FormatUtil.getString("am.webclient.db2.indexpage.tooltip");
/* 2493 */                       String tooltip_datapage = FormatUtil.getString("am.webclient.db2.datapage.tooltip");
/* 2494 */                       String tooltip_directreads = FormatUtil.getString("am.webclient.db2.directreads.tooltip");
/* 2495 */                       String tooltip_directwrites = FormatUtil.getString("am.webclient.db2.directwrites.tooltip");
/*      */                       
/* 2497 */                       Properties db2details = (Properties)request.getAttribute("db2details");
/* 2498 */                       Properties db = (Properties)db2details.get("db");
/* 2499 */                       String dbresourceid = db.getProperty("RESOURCEID");
/* 2500 */                       ArrayList tablestatsarray = (ArrayList)db.get("TABLESTATS");
/* 2501 */                       if ((tablestatsarray != null) && (tablestatsarray.size() > 0))
/*      */                       {
/* 2503 */                         request.setAttribute("tablestatsarray", tablestatsarray);
/*      */                       }
/* 2505 */                       String redirect = (String)request.getAttribute("redirect");
/* 2506 */                       redirect = redirect + "&databasesId=" + dbresourceid;
/* 2507 */                       String encodeurl = java.net.URLEncoder.encode(redirect);
/* 2508 */                       Properties alert = (Properties)request.getAttribute("alert");
/* 2509 */                       String displayname = null;
/* 2510 */                       if (request.getAttribute("displayname") == null)
/*      */                       {
/* 2512 */                         displayname = request.getParameter("resourcename");
/*      */                       }
/*      */                       else
/*      */                       {
/* 2516 */                         displayname = (String)request.getAttribute("displayname");
/*      */                       }
/* 2518 */                       db2graph.setdbresid(Integer.parseInt(dbresourceid));
/*      */                       
/* 2520 */                       out.write("\n<table width=\"99%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n      <td valign=\"top\">\n\t\t<table width=\"100%\"  border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"lrbtborder\">\n      \t<tr>\n      \t  <td  colspan=\"2\" class=\"tableheadingbborder\">");
/* 2521 */                       out.print(FormatUtil.getString("am.webclient.db2.databaseinformation"));
/* 2522 */                       out.write("</td>\n\t\t</tr>\n        <tr>\n\t\t  <td class=\"monitorinfoodd\" title=\"");
/* 2523 */                       out.print(tooltip_databasename);
/* 2524 */                       out.write(34);
/* 2525 */                       out.write(62);
/* 2526 */                       out.print(FormatUtil.getString("am.webclient.common.databasename"));
/* 2527 */                       out.write(" </td>\n\t\t  <td class=\"monitorinfoodd\" title=\"");
/* 2528 */                       out.print(db.getProperty("DBNAME"));
/* 2529 */                       out.write(34);
/* 2530 */                       out.write(62);
/* 2531 */                       out.print(db.getProperty("DBNAME"));
/* 2532 */                       out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                       
/* 2534 */                       String databaseHealthStatus = alert.getProperty(dbresourceid + "#" + "2610");
/*      */                       
/* 2536 */                       out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoeven\" valign=\"top\">");
/* 2537 */                       out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2538 */                       out.write("</td>\n\t\t  <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2539 */                       out.print(dbresourceid);
/* 2540 */                       out.write("&attributeid=2610')\">");
/* 2541 */                       out.print(getSeverityImageForHealth(databaseHealthStatus));
/* 2542 */                       out.write("</a>\n\t\t   ");
/* 2543 */                       out.print(getHideAndShowRCAMessage(alert.getProperty(dbresourceid + "#" + "2610" + "#" + "MESSAGE"), "2610", alert.getProperty(dbresourceid + "#" + "2610"), dbresourceid));
/* 2544 */                       out.write("\n\t\t  </td>\n\t\t</tr>\n\t\t<tr>\n\t\t <td class=\"monitorinfoodd\" title=\"");
/* 2545 */                       out.print(tooltip_aliasname);
/* 2546 */                       out.write(34);
/* 2547 */                       out.write(62);
/* 2548 */                       out.print(FormatUtil.getString("am.webclient.db2.aliasname"));
/* 2549 */                       out.write("</td>\n\t\t <td class=\"monitorinfoodd\" height=\"21\"  title=\"");
/* 2550 */                       out.print(db.getProperty("DBALIAS"));
/* 2551 */                       out.write(34);
/* 2552 */                       out.write(62);
/* 2553 */                       out.print(db.getProperty("DBALIAS"));
/* 2554 */                       out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td class=\"monitorinfoeven\" title=\"");
/* 2555 */                       out.print(tooltip_databasepath);
/* 2556 */                       out.write(34);
/* 2557 */                       out.write(62);
/* 2558 */                       out.print(FormatUtil.getString("am.webclient.db2.databasepath"));
/* 2559 */                       out.write("</td>\n\t\t  <td  height=\"21\"  class=\"monitorinfoeven\" title=\"");
/* 2560 */                       out.print(db.getProperty("DBPATH"));
/* 2561 */                       out.write(34);
/* 2562 */                       out.write(62);
/* 2563 */                       out.print(getTrimmedText(db.getProperty("DBPATH"), 30));
/* 2564 */                       out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                       
/* 2566 */                       String status = db.getProperty("DBSTATUS");
/* 2567 */                       String mouseOver = null;
/* 2568 */                       if (db.getProperty("COLLECTIONTIME").equals("-1"))
/*      */                       {
/* 2570 */                         status = "-";
/* 2571 */                         mouseOver = FormatUtil.getString("am.webclient.db2.mouseover");
/*      */                       }
/* 2573 */                       else if (status.equals("0"))
/*      */                       {
/* 2575 */                         status = FormatUtil.getString("am.webclient.db2.status.text");
/* 2576 */                         mouseOver = FormatUtil.getString("am.webclient.db2.mouseover1");
/*      */                       }
/* 2578 */                       else if (status.equals("1"))
/*      */                       {
/* 2580 */                         status = FormatUtil.getString("am.webclient.db2.status3.text");
/* 2581 */                         mouseOver = FormatUtil.getString("am.webclient.db2.mouseover4");
/*      */                       }
/* 2583 */                       else if (status.equals("2"))
/*      */                       {
/* 2585 */                         status = FormatUtil.getString("am.webclient.db2.status1.text");
/* 2586 */                         mouseOver = FormatUtil.getString("am.webclient.db2.mouseover2");
/*      */ 
/*      */                       }
/* 2589 */                       else if (status.equals("3"))
/*      */                       {
/* 2591 */                         status = FormatUtil.getString("am.webclient.db2.status2.text");
/* 2592 */                         mouseOver = FormatUtil.getString("am.webclient.db2.mouseover2");
/*      */                       }
/*      */                       else
/*      */                       {
/* 2596 */                         status = "-";
/* 2597 */                         mouseOver = FormatUtil.getString("am.webclient.db2.mouseover3");
/*      */                       }
/*      */                       
/* 2600 */                       out.write("\n\t\t<tr>\n\t\t  <td  class=\"monitorinfoodd\" title=\"");
/* 2601 */                       out.print(tooltip_databasestatus);
/* 2602 */                       out.write(34);
/* 2603 */                       out.write(62);
/* 2604 */                       out.print(FormatUtil.getString("am.webclient.db2.databasestatus"));
/* 2605 */                       out.write("</td>\n\t\t  <td  height=\"21\"  class=\"monitorinfoodd\" title=\"");
/* 2606 */                       out.print(mouseOver);
/* 2607 */                       out.write(34);
/* 2608 */                       out.write(62);
/* 2609 */                       out.print(status);
/* 2610 */                       out.write("</td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 2611 */                       out.print(tooltip_connectedtime);
/* 2612 */                       out.write("\">\n\t\t  <td  class=\"monitorinfoeven\">");
/* 2613 */                       out.print(FormatUtil.getString("am.webclient.db2.connectedtime"));
/* 2614 */                       out.write("</td>\n\t\t  <td  height=\"21\"  class=\"monitorinfoodd\">");
/* 2615 */                       out.print(formatDT(db.get("DATABASECONNECTEDTIME") + ""));
/* 2616 */                       out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td height=\"21\" colspan=\"2\" align=\"right\" class=\"monitorinfoeven\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2617 */                       out.print(dbresourceid);
/* 2618 */                       out.write("&attributeIDs=2610&attributeToSelect=2610&redirectto=");
/* 2619 */                       out.print(encodeurl);
/* 2620 */                       out.write("'class=\"staticlinks\">");
/* 2621 */                       out.print(ALERTCONFIG_TEXT);
/* 2622 */                       out.write("</a></td>\n\t\t</tr>\n        </table>\n      </td>\n\t</tr>\n</table>\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n\t<tr>\n\t  <td width=\"100%\" height=\"31\" class=\"tableheadingtrans\">");
/* 2623 */                       out.print(FormatUtil.getString("am.webclient.db2.datbaseutilization"));
/* 2624 */                       out.write(32);
/* 2625 */                       out.write(45);
/* 2626 */                       out.write(32);
/* 2627 */                       out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 2628 */                       out.write("</td>\n\t</tr>\n\t");
/*      */                       
/* 2630 */                       ArrayList repdbsizelst = mo.getRows("select REPORTS_ENABLED from AM_ATTRIBUTES_EXT where attributeid=2631");
/* 2631 */                       String repdbsizeenabled = (String)((ArrayList)repdbsizelst.get(0)).get(0);
/* 2632 */                       if (repdbsizeenabled.equals("1"))
/*      */                       {
/*      */ 
/* 2635 */                         out.write("\n\t<tr>\n\t  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2636 */                         out.print(dbresourceid);
/* 2637 */                         out.write("&attributeid=2631&period=-7&resourcename=");
/* 2638 */                         out.print(displayname);
/* 2639 */                         out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 2640 */                         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2641 */                         out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2642 */                         out.print(dbresourceid);
/* 2643 */                         out.write("&attributeid=2631&period=-30&resourcename=");
/* 2644 */                         out.print(displayname);
/* 2645 */                         out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 2646 */                         out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 2647 */                         out.write("'></a></td>\n\t</tr>\n\t");
/*      */                       }
/*      */                       
/*      */ 
/* 2651 */                       out.write(10);
/* 2652 */                       out.write(9);
/* 2653 */                       db2graph.settype("DBSIZE");
/* 2654 */                       out.write("\n\t<tr>\n\t  <td align=\"center\" width=\"100%\">");
/*      */                       
/* 2656 */                       TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 2657 */                       _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 2658 */                       _jspx_th_awolf_005ftimechart_005f0.setParent(null);
/*      */                       
/* 2660 */                       _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("db2graph");
/*      */                       
/* 2662 */                       _jspx_th_awolf_005ftimechart_005f0.setWidth("600");
/*      */                       
/* 2664 */                       _jspx_th_awolf_005ftimechart_005f0.setHeight("185");
/*      */                       
/* 2666 */                       _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */                       
/* 2668 */                       _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                       
/* 2670 */                       _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.db2.databasesize"));
/* 2671 */                       int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 2672 */                       if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 2673 */                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f0);
/*      */                       }
/*      */                       else {
/* 2676 */                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 2677 */                         out.write("</td>\n\t</tr>\n\t<tr>\n\t  <td valign=\"top\" >\n\t  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\t\t<tr>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 2678 */                         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */                           return;
/* 2680 */                         out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 2681 */                         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */                           return;
/* 2683 */                         out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 2684 */                         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*      */                           return;
/* 2686 */                         out.write("</span></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 2687 */                         out.print(tooltip_databasesize);
/* 2688 */                         out.write("\">\n\t\t  <td class=\"yellowgrayborder\">");
/* 2689 */                         out.print(FormatUtil.getString("am.webclient.db2.databasesize"));
/* 2690 */                         out.write("</td>\n\t\t  <td width=\"27%\" class=\"yellowgrayborder\">");
/* 2691 */                         out.print(db.get("DBSIZE"));
/* 2692 */                         out.write("</td>\n\t\t  <td width=\"36%\" class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2693 */                         out.print(dbresourceid);
/* 2694 */                         out.write("&attributeid=2631')\">");
/* 2695 */                         out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2631")));
/* 2696 */                         out.write("</a></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 2697 */                         out.print(tooltip_deadlockrate);
/* 2698 */                         out.write("\">\n\t\t  <td  class=\"whitegrayborder\">");
/* 2699 */                         out.print(FormatUtil.getString("am.webclient.db2.deadlockrate"));
/* 2700 */                         out.write("</td>\n\t\t  <td  class=\"whitegrayborder\">");
/* 2701 */                         out.print(db.get("RATEOFDEADLOCKS"));
/* 2702 */                         out.write("</td>\n\t\t  <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2703 */                         out.print(dbresourceid);
/* 2704 */                         out.write("&attributeid=2611')\">");
/* 2705 */                         out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2611")));
/* 2706 */                         out.write("</a></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 2707 */                         out.print(tooltip_logutilization);
/* 2708 */                         out.write("\">\n\t\t  <td  class=\"yellowgrayborder\">");
/* 2709 */                         out.print(FormatUtil.getString("am.webclient.db2.logutilization"));
/* 2710 */                         out.write("</td>\n\t\t  <td  class=\"yellowgrayborder\">");
/* 2711 */                         out.print(db.get("LOGUTIL"));
/* 2712 */                         out.write("&nbsp;%</td>\n\t\t  <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2713 */                         out.print(dbresourceid);
/* 2714 */                         out.write("&attributeid=2612')\">");
/* 2715 */                         out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2612")));
/* 2716 */                         out.write("</a></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 2717 */                         out.print(tooltip_sortsoverflowed);
/* 2718 */                         out.write("\">\n\t\t  <td  class=\"whitegrayborder\">");
/* 2719 */                         out.print(FormatUtil.getString("am.webclient.db2.sortsoverflowed"));
/* 2720 */                         out.write("</td>\n\t\t  <td  class=\"whitegrayborder\">");
/* 2721 */                         out.print(db.get("SORTSOVERFLOWED"));
/* 2722 */                         out.write("&nbsp;%</td>\n\t\t  <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2723 */                         out.print(dbresourceid);
/* 2724 */                         out.write("&attributeid=2613')\">");
/* 2725 */                         out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2613")));
/* 2726 */                         out.write("</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" align=\"right\" class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2727 */                         out.print(dbresourceid);
/* 2728 */                         out.write("&attributeIDs=2611,2612,2613,2631&attributeToSelect=2631&redirectto=");
/* 2729 */                         out.print(encodeurl);
/* 2730 */                         out.write("'class=\"staticlinks\">");
/* 2731 */                         out.print(ALERTCONFIG_TEXT);
/* 2732 */                         out.write("</a></td>\n\t\t</tr>\n  \t  </table>\n  \t  </td>\n\t</tr>\n</table>\n\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n<tr>\n<td>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n<tr>\n\t  <td width=\"100%\" height=\"31\" class=\"tableheadingbborder\" colspan=\"7\">");
/* 2733 */                         out.print(FormatUtil.getString("am.webclient.oracle.tablespacestatus"));
/* 2734 */                         out.write(" </td>\n</tr>\n");
/*      */                         
/* 2736 */                         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2737 */                         _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2738 */                         _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */                         
/* 2740 */                         _jspx_th_logic_005fnotEmpty_005f0.setName("tablestatsarray");
/* 2741 */                         int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2742 */                         if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                           for (;;) {
/* 2744 */                             out.write("\n<tr  height=\"22\">\n<td class=\"columnheading\" align=\"left\" height=\"28\" width=\"20%\">");
/* 2745 */                             out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2746 */                             out.write("</td>\n<td class=\"columnheading\" align=\"left\" height=\"28\" width=\"15%\">");
/* 2747 */                             out.print(FormatUtil.getString("am.webclient.db2.tablespaceusage.text"));
/* 2748 */                             out.write("</td>\n<td class=\"columnheading\" align=\"left\" height=\"28\" width=\"15%\">");
/* 2749 */                             out.print(FormatUtil.getString("am.webclient.db2.allocbytes.text"));
/* 2750 */                             out.write("</td>\n<td class=\"columnheading\" align=\"left\" height=\"28\" width=\"15%\">");
/* 2751 */                             out.print(FormatUtil.getString("am.webclient.db2.freemegbytes.text"));
/* 2752 */                             out.write("</td>\n<td class=\"columnheading\" align=\"left\" height=\"28\" width=\"15%\">");
/* 2753 */                             out.print(FormatUtil.getString("am.webclient.db2.freebytes.percent.text"));
/* 2754 */                             out.write("</td>\n<td class=\"columnheading\" align=\"center\" height=\"28\" width=\"10%\">");
/* 2755 */                             out.print(FormatUtil.getString("Health"));
/* 2756 */                             out.write("</td>\n<td class=\"columnheading\" align=\"center\" height=\"28\" width=\"10%\">&nbsp;</td>\n</tr>\n");
/*      */                             
/* 2758 */                             IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2759 */                             _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2760 */                             _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                             
/* 2762 */                             _jspx_th_logic_005fiterate_005f0.setName("tablestatsarray");
/*      */                             
/* 2764 */                             _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                             
/* 2766 */                             _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                             
/* 2768 */                             _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2769 */                             int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2770 */                             if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2771 */                               ArrayList row = null;
/* 2772 */                               Integer j = null;
/* 2773 */                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2774 */                                 out = _jspx_page_context.pushBody();
/* 2775 */                                 _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2776 */                                 _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                               }
/* 2778 */                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2779 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                               for (;;) {
/* 2781 */                                 out.write(10);
/*      */                                 
/* 2783 */                                 String bgclass = "class=\"whitegrayborder\"";
/* 2784 */                                 if (j.intValue() % 2 == 0)
/*      */                                 {
/* 2786 */                                   bgclass = "class=\"whitegrayborder\"";
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 2790 */                                   bgclass = "class=\"yellowgrayborder\"";
/*      */                                 }
/* 2792 */                                 String tablespaceid = (String)row.get(0);
/* 2793 */                                 String tablespace_name = (String)row.get(1);
/* 2794 */                                 String allocmem = (String)row.get(2);
/* 2795 */                                 String perc_free_mem = (String)row.get(3);
/* 2796 */                                 String freemem_Meg = (String)row.get(4);
/* 2797 */                                 String thresholdurl = "/jsp/ThresholdActionConfiguration.jsp?resourceid=" + tablespaceid + "&attributeIDs=2624,2625,2626&attributeToSelect=2624&redirectto=" + encodeurl;
/* 2798 */                                 int Free = Integer.parseInt(perc_free_mem);
/* 2799 */                                 if (Free < 0) {
/* 2800 */                                   Free = 0;
/*      */                                 }
/* 2802 */                                 int Used = 100 - Free;
/* 2803 */                                 if (Used < 0) {
/* 2804 */                                   Used = 0;
/*      */                                 }
/*      */                                 
/* 2807 */                                 out.write("\n<tr>\n<td align=\"left\" ");
/* 2808 */                                 out.print(bgclass);
/* 2809 */                                 out.write(" height=\"28\">");
/* 2810 */                                 out.print(getTrimmedText(tablespace_name, 24));
/* 2811 */                                 out.write("</td>\n<td width=\"20%\" height=\"28\" align=\"left\" ");
/* 2812 */                                 out.print(bgclass);
/* 2813 */                                 out.write(">\n<table align=\"left\" width =\"70%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\">\n\t");
/* 2814 */                                 if ((Used > 0) || (Free > 0)) {
/* 2815 */                                   out.write("\n\t<tr>\n\t   ");
/* 2816 */                                   if (Used > 0) {
/* 2817 */                                     out.write("\n\t\t<td class=\"criticalbar\" width=\"");
/* 2818 */                                     out.print(Used);
/* 2819 */                                     out.write("%\" title=\"Used-");
/* 2820 */                                     out.print(Used);
/* 2821 */                                     out.write("%\"><img src=\"/images/spacer.gif\"  height=\"9\" width=\"1\" ></td>\n\t   ");
/*      */                                   }
/* 2823 */                                   if (Free > 0) {
/* 2824 */                                     out.write("\n\t\t<td class=\"availabilitybar\" width=\"");
/* 2825 */                                     out.print(Free);
/* 2826 */                                     out.write("%\" title=\"Free-");
/* 2827 */                                     out.print(Free);
/* 2828 */                                     out.write("%\"> <img src=\"/images/spacer.gif\"  height=\"9\" width=\"1\"></td>\n\t   ");
/*      */                                   }
/* 2830 */                                   out.write("\n\t</tr>\n\t");
/*      */                                 } else {
/* 2832 */                                   out.write("\n\t<tr>\n\t   <td ><img src=\"/images/Blue/bg_tabnotselected.gif\" height=\"9\" width=\"100%\"></td>\n\t<tr>\n\t");
/*      */                                 }
/* 2834 */                                 out.write("\n</table>\n</td>\n<td align=\"left\" ");
/* 2835 */                                 out.print(bgclass);
/* 2836 */                                 out.write(" height=\"28\">");
/* 2837 */                                 out.print(allocmem);
/* 2838 */                                 out.write("</td>\n<td align=\"left\" ");
/* 2839 */                                 out.print(bgclass);
/* 2840 */                                 out.write(" height=\"28\">");
/* 2841 */                                 out.print(freemem_Meg);
/* 2842 */                                 out.write("</td>\n<td align=\"left\" ");
/* 2843 */                                 out.print(bgclass);
/* 2844 */                                 out.write(" height=\"28\">");
/* 2845 */                                 out.print(perc_free_mem);
/* 2846 */                                 out.write("</td>\n<td align=\"center\" ");
/* 2847 */                                 out.print(bgclass);
/* 2848 */                                 out.write(" height=\"28\"><a href=\"javascript:void(0)\"  onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2849 */                                 out.print(tablespaceid);
/* 2850 */                                 out.write("&attributeid=2624')\">");
/* 2851 */                                 out.print(getSeverityImageForHealth(alert.getProperty(tablespaceid + "#" + "2624")));
/* 2852 */                                 out.write("</a></td>\n<td align=\"center\"  ");
/* 2853 */                                 out.print(bgclass);
/* 2854 */                                 out.write(" height=\"28\">\n <a href='");
/* 2855 */                                 out.print(thresholdurl);
/* 2856 */                                 out.write("' align=\"center\" class=\"staticlinks\">  <img title=\"");
/* 2857 */                                 out.print(ALERTCONFIG_TEXT);
/* 2858 */                                 out.write("\" src=\"/images/icon_associateaction.gif\" alt=\"");
/* 2859 */                                 out.print(ALERTCONFIG_TEXT);
/* 2860 */                                 out.write("\" border=\"0\" hspace=\"5\" align=\"absmiddle\" ></a>\n</td>\n</tr>\n");
/* 2861 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2862 */                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2863 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/* 2864 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2867 */                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2868 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2871 */                             if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2872 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                             }
/*      */                             
/* 2875 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2876 */                             out.write(10);
/* 2877 */                             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2878 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2882 */                         if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2883 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                         }
/*      */                         else {
/* 2886 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2887 */                           out.write(10);
/* 2888 */                           out.write(10);
/*      */                           
/* 2890 */                           EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2891 */                           _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2892 */                           _jspx_th_logic_005fempty_005f0.setParent(null);
/*      */                           
/* 2894 */                           _jspx_th_logic_005fempty_005f0.setName("tablestatsarray");
/* 2895 */                           int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2896 */                           if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                             for (;;) {
/* 2898 */                               out.write("\n<tr>\n<td height=\"25\" class=\"whitegrayborder\">");
/* 2899 */                               out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 2900 */                               out.write("</td>\n</tr>\n");
/* 2901 */                               int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2902 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2906 */                           if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2907 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/*      */                           }
/*      */                           else {
/* 2910 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2911 */                             out.write("\n</table>\n</td>\n</tr>\n<tr>\n<td colspan=8 height=\"5\" align=\"right\" class=\"tablebottom\">\n<table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"55%\">\n\t<tr>\n\t<td valign=\"top\">\n\t<table  align=\"center\" width=\"30%\" >\n\t\t<tr>\n\t\t<td style=\"background-repeat:no-repeat;\" ><img src=\"/images/Blue/bg_availability_red.gif\"  style=\"width:10px; height:9px;\" ></td>\n\t\t<td class=\"bodytext\">");
/* 2912 */                             out.print(FormatUtil.getString("Used %"));
/* 2913 */                             out.write("</td>\n\t\t<td style=\"background-repeat:no-repeat;\"> <img src=\"/images/Blue/bg_availability_green.gif\"  style=\"width:10px; height:9px;\"  ></td>\n\t\t<td class=\"bodytext\">");
/* 2914 */                             out.print(FormatUtil.getString("Free %"));
/* 2915 */                             out.write("</td>\n\t\t</tr>\n\t</table>\n\t</td>\n\t</tr>\n</table>\n</td>\n</tr>\n</table>\n\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t<tr>\n\t  <td width=\"50%\" height=\"31\" class=\"tableheadingtrans\">");
/* 2916 */                             out.print(FormatUtil.getString("am.webclient.db2.transactionstatistics"));
/* 2917 */                             out.write(32);
/* 2918 */                             out.write(45);
/* 2919 */                             out.write(32);
/* 2920 */                             out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 2921 */                             out.write("</td>\n\t  <td width=\"50%\" height=\"31\" align=\"left\" class=\"tableheadingtrans\">");
/* 2922 */                             out.print(FormatUtil.getString("am.webclient.db2.cacheperformance"));
/* 2923 */                             out.write(32);
/* 2924 */                             out.write(45);
/* 2925 */                             out.write(32);
/* 2926 */                             out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 2927 */                             out.write("</td>\n\t</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\t<tr>\n\t  <td width=\"50%\" height=\"38\"  class=\"rbborder\">\n\t  <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t<tr>\n\t\t  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2928 */                             out.print(dbresourceid);
/* 2929 */                             out.write("&attributeid=2616&period=-7&resourcename=");
/* 2930 */                             out.print(displayname);
/* 2931 */                             out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 2932 */                             out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2933 */                             out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2934 */                             out.print(dbresourceid);
/* 2935 */                             out.write("&attributeid=2616&period=-30&resourcename=");
/* 2936 */                             out.print(displayname);
/* 2937 */                             out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 2938 */                             out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 2939 */                             out.write("'></a></td>\n\t\t</tr>\n\t\t");
/* 2940 */                             db2graph.settype("RATEOFWORK");
/* 2941 */                             out.write("\n\t\t<tr>\n\t\t  <td>");
/*      */                             
/* 2943 */                             TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 2944 */                             _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 2945 */                             _jspx_th_awolf_005ftimechart_005f1.setParent(null);
/*      */                             
/* 2947 */                             _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("db2graph");
/*      */                             
/* 2949 */                             _jspx_th_awolf_005ftimechart_005f1.setWidth("330");
/*      */                             
/* 2951 */                             _jspx_th_awolf_005ftimechart_005f1.setHeight("185");
/*      */                             
/* 2953 */                             _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */                             
/* 2955 */                             _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                             
/* 2957 */                             _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(FormatUtil.getString("am.webclient.db2.unitsofwork"));
/* 2958 */                             int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 2959 */                             if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 2960 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f1);
/*      */                             }
/*      */                             else {
/* 2963 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 2964 */                               out.write("</td>\n\t\t</tr>\n\t  </table>\n\t  </td>\n\t  <td width=\"49%\" height=\"38\" class=\"bottomborder\">\n\t  <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t<tr>\n\t\t  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2965 */                               out.print(dbresourceid);
/* 2966 */                               out.write("&attributeid=2617&period=-7&resourcename=");
/* 2967 */                               out.print(displayname);
/* 2968 */                               out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 2969 */                               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2970 */                               out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2971 */                               out.print(dbresourceid);
/* 2972 */                               out.write("&attributeid=2617&period=-30&resourcename=");
/* 2973 */                               out.print(displayname);
/* 2974 */                               out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 2975 */                               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 2976 */                               out.write("'></a></td>\n\t\t</tr>\n\t\t");
/* 2977 */                               db2graph.settype("PKGCACHEHITRATIO");
/* 2978 */                               out.write("\n\t\t<tr>\n\t\t  <td>");
/*      */                               
/* 2980 */                               TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 2981 */                               _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 2982 */                               _jspx_th_awolf_005ftimechart_005f2.setParent(null);
/*      */                               
/* 2984 */                               _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("db2graph");
/*      */                               
/* 2986 */                               _jspx_th_awolf_005ftimechart_005f2.setWidth("330");
/*      */                               
/* 2988 */                               _jspx_th_awolf_005ftimechart_005f2.setHeight("185");
/*      */                               
/* 2990 */                               _jspx_th_awolf_005ftimechart_005f2.setLegend("true");
/*      */                               
/* 2992 */                               _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                               
/* 2994 */                               _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(FormatUtil.getString("am.webclient.db2.packagecache"));
/* 2995 */                               int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 2996 */                               if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 2997 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f2);
/*      */                               }
/*      */                               else {
/* 3000 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3001 */                                 out.write("</td>\n\t\t</tr>\n\t  </table>\n\t  </td>\n\t</tr>\n\t<tr>\n\t  <td valign=\"top\" class=\"rborder\">\n\t  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\t\t<tr>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3002 */                                 if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*      */                                   return;
/* 3004 */                                 out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3005 */                                 if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*      */                                   return;
/* 3007 */                                 out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3008 */                                 if (_jspx_meth_fmt_005fmessage_005f5(_jspx_page_context))
/*      */                                   return;
/* 3010 */                                 out.write("</span></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3011 */                                 out.print(tooltip_successfulsql);
/* 3012 */                                 out.write("\">\n\t\t  <td class=\"whitegrayborder\">");
/* 3013 */                                 out.print(FormatUtil.getString("am.webclient.db2.successfulqueries"));
/* 3014 */                                 out.write("</td>\n\t\t  <td width=\"27%\" class=\"whitegrayborder\">");
/* 3015 */                                 out.print(db.get("RATEOFSUCCESSFULSQL"));
/* 3016 */                                 out.write("</td>\n\t  \t  <td width=\"36%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3017 */                                 out.print(dbresourceid);
/* 3018 */                                 out.write("&attributeid=2614')\">");
/* 3019 */                                 out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2614")));
/* 3020 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3021 */                                 out.print(tooltip_failedsql);
/* 3022 */                                 out.write("\">\n\t\t  <td class=\"yellowgrayborder\">");
/* 3023 */                                 out.print(FormatUtil.getString("am.webclient.db2.failedqueries"));
/* 3024 */                                 out.write("</td>\n\t\t  <td class=\"yellowgrayborder\">");
/* 3025 */                                 out.print(db.getProperty("RATEOFFAILEDSQL"));
/* 3026 */                                 out.write("</td>\n\t\t  <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3027 */                                 out.print(dbresourceid);
/* 3028 */                                 out.write("&attributeid=2615')\">");
/* 3029 */                                 out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2615")));
/* 3030 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3031 */                                 out.print(tooltip_unitsofwork);
/* 3032 */                                 out.write("\">\n\t\t  <td class=\"whitegrayborder\">");
/* 3033 */                                 out.print(FormatUtil.getString("am.webclient.db2.unitsofwork"));
/* 3034 */                                 out.write("</td>\n\t\t  <td class=\"whitegrayborder\">");
/* 3035 */                                 out.print(db.getProperty("RATEOFWORK"));
/* 3036 */                                 out.write("</td>\n\t\t  <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3037 */                                 out.print(dbresourceid);
/* 3038 */                                 out.write("&attributeid=2616')\">");
/* 3039 */                                 out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2616")));
/* 3040 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" align=\"right\" ><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3041 */                                 out.print(dbresourceid);
/* 3042 */                                 out.write("&attributeIDs=2614,2615,2616&attributeToSelect=2614&redirectto=");
/* 3043 */                                 out.print(encodeurl);
/* 3044 */                                 out.write("'class=\"staticlinks\">");
/* 3045 */                                 out.print(ALERTCONFIG_TEXT);
/* 3046 */                                 out.write("</a></td>\n\t\t</tr>\n  \t  </table>\n  \t  </td>\n\t  <td valign=\"top\">\n\t  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\t\t<tr>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3047 */                                 if (_jspx_meth_fmt_005fmessage_005f6(_jspx_page_context))
/*      */                                   return;
/* 3049 */                                 out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3050 */                                 if (_jspx_meth_fmt_005fmessage_005f7(_jspx_page_context))
/*      */                                   return;
/* 3052 */                                 out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3053 */                                 if (_jspx_meth_fmt_005fmessage_005f8(_jspx_page_context))
/*      */                                   return;
/* 3055 */                                 out.write("</span></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3056 */                                 out.print(tooltip_package);
/* 3057 */                                 out.write("\">\n\t\t  <td class=\"whitegrayborder\">");
/* 3058 */                                 out.print(FormatUtil.getString("am.webclient.db2.packagecache"));
/* 3059 */                                 out.write("</td>\n\t\t  <td class=\"whitegrayborder\">");
/* 3060 */                                 out.print(db.getProperty("PKGCACHEHITRATIO"));
/* 3061 */                                 out.write("&nbsp;%</td>\n\t\t  <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3062 */                                 out.print(dbresourceid);
/* 3063 */                                 out.write("&attributeid=2617')\">");
/* 3064 */                                 out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2617")));
/* 3065 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3066 */                                 out.print(tooltip_catalog);
/* 3067 */                                 out.write("\">\n\t\t  <td class=\"yellowgrayborder\">");
/* 3068 */                                 out.print(FormatUtil.getString("am.webclient.db2.catalogcache"));
/* 3069 */                                 out.write("</td>\n\t\t  <td class=\"yellowgrayborder\">");
/* 3070 */                                 out.print(db.getProperty("CATCACHEHITRATIO"));
/* 3071 */                                 out.write("&nbsp;%</td>\n\t\t  <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3072 */                                 out.print(dbresourceid);
/* 3073 */                                 out.write("&attributeid=2618')\">");
/* 3074 */                                 out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2618")));
/* 3075 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" align=\"right\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3076 */                                 out.print(dbresourceid);
/* 3077 */                                 out.write("&attributeIDs=2617,2618&attributeToSelect=2617&redirectto=");
/* 3078 */                                 out.print(encodeurl);
/* 3079 */                                 out.write("'class=\"staticlinks\">");
/* 3080 */                                 out.print(ALERTCONFIG_TEXT);
/* 3081 */                                 out.write("</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" class=\"yellowgrayborder\"></td>\n\t\t</tr>\n  \t  </table>\n  \t  </td>\n\t</tr>\n</table>\n<br>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n\t<tr>\n\t  <td width=\"100%\" height=\"31\" class=\"tableheadingtrans\">");
/* 3082 */                                 out.print(FormatUtil.getString("am.webclient.db2.bufferstatistics"));
/* 3083 */                                 out.write(32);
/* 3084 */                                 out.write(45);
/* 3085 */                                 out.write(32);
/* 3086 */                                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3087 */                                 out.write("</td>\n\t</tr>\n\t<tr>\n\t  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3088 */                                 out.print(dbresourceid);
/* 3089 */                                 out.write("&attributeid=2619&period=-7&resourcename=");
/* 3090 */                                 out.print(displayname);
/* 3091 */                                 out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3092 */                                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3093 */                                 out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3094 */                                 out.print(dbresourceid);
/* 3095 */                                 out.write("&attributeid=2619&period=-30&resourcename=");
/* 3096 */                                 out.print(displayname);
/* 3097 */                                 out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3098 */                                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3099 */                                 out.write("'></a></td>\n\t</tr>\n\t");
/* 3100 */                                 db2graph.settype("BUFFERPOOLSTATS");
/* 3101 */                                 out.write("\n\t<tr>\n\t  <td align=\"center\" width=\"100%\">");
/*      */                                 
/* 3103 */                                 TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 3104 */                                 _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 3105 */                                 _jspx_th_awolf_005ftimechart_005f3.setParent(null);
/*      */                                 
/* 3107 */                                 _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("db2graph");
/*      */                                 
/* 3109 */                                 _jspx_th_awolf_005ftimechart_005f3.setWidth("600");
/*      */                                 
/* 3111 */                                 _jspx_th_awolf_005ftimechart_005f3.setHeight("185");
/*      */                                 
/* 3113 */                                 _jspx_th_awolf_005ftimechart_005f3.setLegend("true");
/*      */                                 
/* 3115 */                                 _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                 
/* 3117 */                                 _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel(FormatUtil.getString("am.webclient.db2.bufferpool"));
/* 3118 */                                 int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 3119 */                                 if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 3120 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f3);
/*      */                                 }
/*      */                                 else {
/* 3123 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 3124 */                                   out.write("</td>\n\t</tr>\n\t<tr>\n\t  <td valign=\"top\" >\n\t  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\t\t<tr>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3125 */                                   if (_jspx_meth_fmt_005fmessage_005f9(_jspx_page_context))
/*      */                                     return;
/* 3127 */                                   out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3128 */                                   if (_jspx_meth_fmt_005fmessage_005f10(_jspx_page_context))
/*      */                                     return;
/* 3130 */                                   out.write("</span></td>\n\t\t  <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3131 */                                   if (_jspx_meth_fmt_005fmessage_005f11(_jspx_page_context))
/*      */                                     return;
/* 3133 */                                   out.write("</span></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3134 */                                   out.print(tooltip_bufferpool);
/* 3135 */                                   out.write("\">\n\t\t  <td class=\"whitegrayborder\">");
/* 3136 */                                   out.print(FormatUtil.getString("am.webclient.db2.bufferpool"));
/* 3137 */                                   out.write("</td>\n\t\t  <td width=\"27%\" class=\"whitegrayborder\">");
/* 3138 */                                   out.print(db.get("BUFFERPOOLHITRATIO"));
/* 3139 */                                   out.write("&nbsp;%</td>\n\t  \t  <td width=\"36%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3140 */                                   out.print(dbresourceid);
/* 3141 */                                   out.write("&attributeid=2619')\">");
/* 3142 */                                   out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2619")));
/* 3143 */                                   out.write("</a></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3144 */                                   out.print(tooltip_indexpage);
/* 3145 */                                   out.write("\">\n\t\t  <td class=\"yellowgrayborder\">");
/* 3146 */                                   out.print(FormatUtil.getString("am.webclient.db2.indexpage"));
/* 3147 */                                   out.write("</td>\n\t\t  <td class=\"yellowgrayborder\">");
/* 3148 */                                   out.print(db.getProperty("INDEXHITRATIO"));
/* 3149 */                                   out.write("&nbsp;%</td>\n\t\t  <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3150 */                                   out.print(dbresourceid);
/* 3151 */                                   out.write("&attributeid=2620')\">");
/* 3152 */                                   out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2620")));
/* 3153 */                                   out.write("</a></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3154 */                                   out.print(tooltip_datapage);
/* 3155 */                                   out.write("\">\n\t\t  <td class=\"whitegrayborder\">");
/* 3156 */                                   out.print(FormatUtil.getString("am.webclient.db2.datapage"));
/* 3157 */                                   out.write("</td>\n\t\t  <td class=\"whitegrayborder\">");
/* 3158 */                                   out.print(db.getProperty("DATAPAGEHITRATIO"));
/* 3159 */                                   out.write("&nbsp;%</td>\n\t\t  <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3160 */                                   out.print(dbresourceid);
/* 3161 */                                   out.write("&attributeid=2621')\">");
/* 3162 */                                   out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2621")));
/* 3163 */                                   out.write("</a></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3164 */                                   out.print(tooltip_directreads);
/* 3165 */                                   out.write("\">\n\t\t  <td class=\"yellowgrayborder\">");
/* 3166 */                                   out.print(FormatUtil.getString("am.webclient.db2.directreads"));
/* 3167 */                                   out.write("</td>\n\t\t  <td class=\"yellowgrayborder\">");
/* 3168 */                                   out.print(db.getProperty("DIRECTREADS"));
/* 3169 */                                   out.write("</td>\n\t\t  <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3170 */                                   out.print(dbresourceid);
/* 3171 */                                   out.write("&attributeid=2622')\">");
/* 3172 */                                   out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2622")));
/* 3173 */                                   out.write("</a></td>\n\t\t</tr>\n\t\t<tr title=\"");
/* 3174 */                                   out.print(tooltip_directwrites);
/* 3175 */                                   out.write("\">\n\t\t  <td class=\"whitegrayborder\">");
/* 3176 */                                   out.print(FormatUtil.getString("am.webclient.db2.directwrites"));
/* 3177 */                                   out.write("</td>\n\t\t  <td class=\"whitegrayborder\">");
/* 3178 */                                   out.print(db.getProperty("DIRECTWRITES"));
/* 3179 */                                   out.write("</td>\n\t\t  <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3180 */                                   out.print(dbresourceid);
/* 3181 */                                   out.write("&attributeid=2623')\">");
/* 3182 */                                   out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2623")));
/* 3183 */                                   out.write("</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t  <td colspan=\"3\" align=\"right\" class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3184 */                                   out.print(dbresourceid);
/* 3185 */                                   out.write("&attributeIDs=2619,2620,2621,2622,2623&attributeToSelect=2619&redirectto=");
/* 3186 */                                   out.print(encodeurl);
/* 3187 */                                   out.write("'class=\"staticlinks\">");
/* 3188 */                                   out.print(ALERTCONFIG_TEXT);
/* 3189 */                                   out.write("</a></td>\n\t\t</tr>\n  \t  </table>\n  \t  </td>\n\t</tr>\n</table>\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\" >\n\n\t \t  <tr>\n\t \t  <td height=\"31\" class=\"tableheadingtrans\" colspan=\"3\">\n\t \t  ");
/* 3190 */                                   out.print(FormatUtil.getString("am.webclient.db2rowdata.text"));
/* 3191 */                                   out.write(" </td>\n\t \t  </td>\n\t \t  <td height=\"31\" class=\"tableheadingtrans\">&nbsp;</td>\n\t \t  </tr>\n\t \t  <tr>\n\t \t ");
/* 3192 */                                   db2graph.settype("ROW_STATS");
/* 3193 */                                   out.write("\n\t \t <td align=\"center\" width=\"100%\" colspan=\"3\">");
/*      */                                   
/* 3195 */                                   TimeChart _jspx_th_awolf_005ftimechart_005f4 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 3196 */                                   _jspx_th_awolf_005ftimechart_005f4.setPageContext(_jspx_page_context);
/* 3197 */                                   _jspx_th_awolf_005ftimechart_005f4.setParent(null);
/*      */                                   
/* 3199 */                                   _jspx_th_awolf_005ftimechart_005f4.setDataSetProducer("db2graph");
/*      */                                   
/* 3201 */                                   _jspx_th_awolf_005ftimechart_005f4.setWidth("600");
/*      */                                   
/* 3203 */                                   _jspx_th_awolf_005ftimechart_005f4.setHeight("185");
/*      */                                   
/* 3205 */                                   _jspx_th_awolf_005ftimechart_005f4.setLegend("true");
/*      */                                   
/* 3207 */                                   _jspx_th_awolf_005ftimechart_005f4.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                   
/* 3209 */                                   _jspx_th_awolf_005ftimechart_005f4.setYaxisLabel(FormatUtil.getString("webclient.performance.reports.index.transmittraffic.yaxisname"));
/* 3210 */                                   int _jspx_eval_awolf_005ftimechart_005f4 = _jspx_th_awolf_005ftimechart_005f4.doStartTag();
/* 3211 */                                   if (_jspx_th_awolf_005ftimechart_005f4.doEndTag() == 5) {
/* 3212 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f4);
/*      */                                   }
/*      */                                   else {
/* 3215 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f4);
/* 3216 */                                     out.write("</td>\n\t \t  </tr>\n\t \t  <tr>\n\t\t\t\t  <td class=\"yellowgrayborder\" width=\"37%\"><span class=\"bodytextbold\">");
/* 3217 */                                     if (_jspx_meth_fmt_005fmessage_005f12(_jspx_page_context))
/*      */                                       return;
/* 3219 */                                     out.write("</span></td>\n\t\t\t\t  <td class=\"yellowgrayborder\" width=\"27%\"><span class=\"bodytextbold\">");
/* 3220 */                                     if (_jspx_meth_fmt_005fmessage_005f13(_jspx_page_context))
/*      */                                       return;
/* 3222 */                                     out.write("</span></td>\n\t\t\t\t  <td class=\"yellowgrayborder\" width=\"36%\"><span class=\"bodytextbold\">");
/* 3223 */                                     if (_jspx_meth_fmt_005fmessage_005f14(_jspx_page_context))
/*      */                                       return;
/* 3225 */                                     out.write("</span></td>\n\t\t  </tr>\n\t \t  <tr>\n\t\t\t\t   <td class=\"whitegrayborder\" width=\"37%\">");
/* 3226 */                                     out.print(FormatUtil.getString("Rows Inserted/Sec"));
/* 3227 */                                     out.write("</td>\n\t\t\t\t   <td class=\"whitegrayborder\" width=\"27%\" align=\"left\">&nbsp;");
/* 3228 */                                     out.print(db.getProperty("ROWSINSERTEDRATE"));
/* 3229 */                                     out.write("</td>\n\t\t\t\t   <td  class=\"whitegrayborder\" width=\"36%\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3230 */                                     out.print(dbresourceid);
/* 3231 */                                     out.write("&attributeid=2627')\">");
/* 3232 */                                     out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2627")));
/* 3233 */                                     out.write("</a></td>\n\t \t  </tr>\n\t \t   <tr>\n\t\t  \t \t   <td class=\"yellowgrayborder\" width=\"37%\">");
/* 3234 */                                     out.print(FormatUtil.getString("Rows Deleted/Sec"));
/* 3235 */                                     out.write("</td>\n\t\t             \t   <td class=\"yellowgrayborder\" width=\"27%\" align=\"left\">&nbsp;");
/* 3236 */                                     out.print(db.getProperty("ROWSDELETEDRATE"));
/* 3237 */                                     out.write("</td>\n\t\t             \t   <td  class=\"yellowgrayborder\" width=\"36%\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3238 */                                     out.print(dbresourceid);
/* 3239 */                                     out.write("&attributeid=2628')\">");
/* 3240 */                                     out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2628")));
/* 3241 */                                     out.write("</a></td>\n\t \t  </tr>\n\t \t   <tr>\n\t\t  \t \t   <td class=\"whitegrayborder\" width=\"37%\">");
/* 3242 */                                     out.print(FormatUtil.getString("Rows Selected/Sec"));
/* 3243 */                                     out.write("</td>\n\t\t             \t   <td class=\"whitegrayborder\" width=\"27%\" align=\"left\">&nbsp;");
/* 3244 */                                     out.print(db.getProperty("ROWSSELECTEDRATE"));
/* 3245 */                                     out.write("</td>\n\t\t             \t   <td  class=\"whitegrayborder\" width=\"36%\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3246 */                                     out.print(dbresourceid);
/* 3247 */                                     out.write("&attributeid=2629')\">");
/* 3248 */                                     out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2629")));
/* 3249 */                                     out.write("</a></td>\n\t \t  </tr>\n\t \t   <tr>\n\t\t  \t \t   <td class=\"yellowgrayborder\" width=\"37%\">");
/* 3250 */                                     out.print(FormatUtil.getString("Rows Updated/Sec"));
/* 3251 */                                     out.write("</td>\n\t\t             \t   <td class=\"yellowgrayborder\" width=\"27%\" align=\"left\">&nbsp;");
/* 3252 */                                     out.print(db.getProperty("ROWSUPDATEDRATE"));
/* 3253 */                                     out.write("</td>\n\t\t             \t   <td  class=\"yellowgrayborder\" width=\"36%\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3254 */                                     out.print(dbresourceid);
/* 3255 */                                     out.write("&attributeid=2630')\">");
/* 3256 */                                     out.print(getSeverityImage(alert.getProperty(dbresourceid + "#" + "2630")));
/* 3257 */                                     out.write("</a></td>\n\t \t  </tr>\n\t \t  <tr>\n\t\t  \t\t  <td colspan=\"3\" align=\"right\" class=\"whitegrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3258 */                                     out.print(dbresourceid);
/* 3259 */                                     out.write("&attributeIDs=2627,2628,2629,2630&attributeToSelect=2627&redirectto=");
/* 3260 */                                     out.print(encodeurl);
/* 3261 */                                     out.write("'class=\"staticlinks\">");
/* 3262 */                                     out.print(ALERTCONFIG_TEXT);
/* 3263 */                                     out.write("</a></td>\n\t\t </tr>\n</table>\n\n<br>\n");
/*      */                                   }
/* 3265 */                                 } } } } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3266 */         out = _jspx_out;
/* 3267 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3268 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3269 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3272 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3278 */     PageContext pageContext = _jspx_page_context;
/* 3279 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3281 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 3282 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 3283 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3285 */     _jspx_th_am_005fhiddenparam_005f0.setName("resourceid");
/* 3286 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 3287 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 3288 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 3289 */       return true;
/*      */     }
/* 3291 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 3292 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3297 */     PageContext pageContext = _jspx_page_context;
/* 3298 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3300 */     HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 3301 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/* 3302 */     _jspx_th_am_005fhiddenparam_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3304 */     _jspx_th_am_005fhiddenparam_005f1.setName("resourcename");
/* 3305 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/* 3306 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/* 3307 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 3308 */       return true;
/*      */     }
/* 3310 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 3311 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3316 */     PageContext pageContext = _jspx_page_context;
/* 3317 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3319 */     HiddenParam _jspx_th_am_005fhiddenparam_005f2 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 3320 */     _jspx_th_am_005fhiddenparam_005f2.setPageContext(_jspx_page_context);
/* 3321 */     _jspx_th_am_005fhiddenparam_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3323 */     _jspx_th_am_005fhiddenparam_005f2.setName("type");
/* 3324 */     int _jspx_eval_am_005fhiddenparam_005f2 = _jspx_th_am_005fhiddenparam_005f2.doStartTag();
/* 3325 */     if (_jspx_th_am_005fhiddenparam_005f2.doEndTag() == 5) {
/* 3326 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 3327 */       return true;
/*      */     }
/* 3329 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 3330 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3335 */     PageContext pageContext = _jspx_page_context;
/* 3336 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3338 */     HiddenParam _jspx_th_am_005fhiddenparam_005f3 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 3339 */     _jspx_th_am_005fhiddenparam_005f3.setPageContext(_jspx_page_context);
/* 3340 */     _jspx_th_am_005fhiddenparam_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3342 */     _jspx_th_am_005fhiddenparam_005f3.setName("moname");
/* 3343 */     int _jspx_eval_am_005fhiddenparam_005f3 = _jspx_th_am_005fhiddenparam_005f3.doStartTag();
/* 3344 */     if (_jspx_th_am_005fhiddenparam_005f3.doEndTag() == 5) {
/* 3345 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f3);
/* 3346 */       return true;
/*      */     }
/* 3348 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f3);
/* 3349 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3354 */     PageContext pageContext = _jspx_page_context;
/* 3355 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3357 */     HiddenParam _jspx_th_am_005fhiddenparam_005f4 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 3358 */     _jspx_th_am_005fhiddenparam_005f4.setPageContext(_jspx_page_context);
/* 3359 */     _jspx_th_am_005fhiddenparam_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3361 */     _jspx_th_am_005fhiddenparam_005f4.setName("name");
/* 3362 */     int _jspx_eval_am_005fhiddenparam_005f4 = _jspx_th_am_005fhiddenparam_005f4.doStartTag();
/* 3363 */     if (_jspx_th_am_005fhiddenparam_005f4.doEndTag() == 5) {
/* 3364 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f4);
/* 3365 */       return true;
/*      */     }
/* 3367 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f4);
/* 3368 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3373 */     PageContext pageContext = _jspx_page_context;
/* 3374 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3376 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3377 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3378 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3380 */     _jspx_th_c_005fif_005f0.setTest("${ !empty param.haid && param.haid!='null' }");
/* 3381 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3382 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3384 */         out.write(10);
/* 3385 */         if (_jspx_meth_am_005fhiddenparam_005f5(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3386 */           return true;
/* 3387 */         out.write(10);
/* 3388 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3389 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3393 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3394 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3395 */       return true;
/*      */     }
/* 3397 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3398 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f5(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3403 */     PageContext pageContext = _jspx_page_context;
/* 3404 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3406 */     HiddenParam _jspx_th_am_005fhiddenparam_005f5 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 3407 */     _jspx_th_am_005fhiddenparam_005f5.setPageContext(_jspx_page_context);
/* 3408 */     _jspx_th_am_005fhiddenparam_005f5.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3410 */     _jspx_th_am_005fhiddenparam_005f5.setName("haid");
/* 3411 */     int _jspx_eval_am_005fhiddenparam_005f5 = _jspx_th_am_005fhiddenparam_005f5.doStartTag();
/* 3412 */     if (_jspx_th_am_005fhiddenparam_005f5.doEndTag() == 5) {
/* 3413 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f5);
/* 3414 */       return true;
/*      */     }
/* 3416 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f5);
/* 3417 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3422 */     PageContext pageContext = _jspx_page_context;
/* 3423 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3425 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange.get(SelectTag.class);
/* 3426 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3427 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3429 */     _jspx_th_html_005fselect_005f0.setProperty("databasesId");
/*      */     
/* 3431 */     _jspx_th_html_005fselect_005f0.setOnchange("javascript:this.form.submit()");
/* 3432 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3433 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3434 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3435 */         out = _jspx_page_context.pushBody();
/* 3436 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3437 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3440 */         out.write(10);
/* 3441 */         out.write(9);
/* 3442 */         out.write(9);
/* 3443 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 3444 */           return true;
/* 3445 */         out.write(10);
/* 3446 */         out.write(9);
/* 3447 */         out.write(9);
/* 3448 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 3449 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3452 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3453 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3456 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 3457 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 3458 */       return true;
/*      */     }
/* 3460 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 3461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3466 */     PageContext pageContext = _jspx_page_context;
/* 3467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3469 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3470 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 3471 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 3473 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("databases");
/* 3474 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 3475 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 3476 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3477 */       return true;
/*      */     }
/* 3479 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3480 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3485 */     PageContext pageContext = _jspx_page_context;
/* 3486 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3488 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3489 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3490 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/* 3491 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3492 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 3493 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3494 */         out = _jspx_page_context.pushBody();
/* 3495 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 3496 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3499 */         out.write("table.heading.attribute");
/* 3500 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 3501 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3504 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3505 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3508 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3509 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3510 */       return true;
/*      */     }
/* 3512 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3513 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3518 */     PageContext pageContext = _jspx_page_context;
/* 3519 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3521 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3522 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3523 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/* 3524 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3525 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 3526 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3527 */         out = _jspx_page_context.pushBody();
/* 3528 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 3529 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3532 */         out.write("table.heading.value");
/* 3533 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 3534 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3537 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3538 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3541 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3542 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3543 */       return true;
/*      */     }
/* 3545 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3546 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3551 */     PageContext pageContext = _jspx_page_context;
/* 3552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3554 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3555 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 3556 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/* 3557 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 3558 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 3559 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3560 */         out = _jspx_page_context.pushBody();
/* 3561 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 3562 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3565 */         out.write("table.heading.status");
/* 3566 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 3567 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3570 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3571 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3574 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3575 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3576 */       return true;
/*      */     }
/* 3578 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3579 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3584 */     PageContext pageContext = _jspx_page_context;
/* 3585 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3587 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3588 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 3589 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/* 3590 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 3591 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 3592 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3593 */         out = _jspx_page_context.pushBody();
/* 3594 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 3595 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3598 */         out.write("table.heading.attribute");
/* 3599 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 3600 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3603 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3604 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3607 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3608 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3609 */       return true;
/*      */     }
/* 3611 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3612 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3617 */     PageContext pageContext = _jspx_page_context;
/* 3618 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3620 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3621 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3622 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/* 3623 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3624 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 3625 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3626 */         out = _jspx_page_context.pushBody();
/* 3627 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 3628 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3631 */         out.write("table.heading.value");
/* 3632 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 3633 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3636 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3637 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3640 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3641 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3642 */       return true;
/*      */     }
/* 3644 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3645 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3650 */     PageContext pageContext = _jspx_page_context;
/* 3651 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3653 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3654 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3655 */     _jspx_th_fmt_005fmessage_005f5.setParent(null);
/* 3656 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3657 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 3658 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3659 */         out = _jspx_page_context.pushBody();
/* 3660 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 3661 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3664 */         out.write("table.heading.status");
/* 3665 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 3666 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3669 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3670 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3673 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3674 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3675 */       return true;
/*      */     }
/* 3677 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3678 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3683 */     PageContext pageContext = _jspx_page_context;
/* 3684 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3686 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3687 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 3688 */     _jspx_th_fmt_005fmessage_005f6.setParent(null);
/* 3689 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 3690 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 3691 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3692 */         out = _jspx_page_context.pushBody();
/* 3693 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 3694 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3697 */         out.write("table.heading.attribute");
/* 3698 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 3699 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3702 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3703 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3706 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 3707 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3708 */       return true;
/*      */     }
/* 3710 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3711 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3716 */     PageContext pageContext = _jspx_page_context;
/* 3717 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3719 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3720 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 3721 */     _jspx_th_fmt_005fmessage_005f7.setParent(null);
/* 3722 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 3723 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 3724 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3725 */         out = _jspx_page_context.pushBody();
/* 3726 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 3727 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3730 */         out.write("table.heading.value");
/* 3731 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 3732 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3735 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3736 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3739 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 3740 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3741 */       return true;
/*      */     }
/* 3743 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3744 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3749 */     PageContext pageContext = _jspx_page_context;
/* 3750 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3752 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3753 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 3754 */     _jspx_th_fmt_005fmessage_005f8.setParent(null);
/* 3755 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 3756 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 3757 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3758 */         out = _jspx_page_context.pushBody();
/* 3759 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 3760 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3763 */         out.write("table.heading.status");
/* 3764 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 3765 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3768 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3769 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3772 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 3773 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3774 */       return true;
/*      */     }
/* 3776 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3777 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3782 */     PageContext pageContext = _jspx_page_context;
/* 3783 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3785 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3786 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 3787 */     _jspx_th_fmt_005fmessage_005f9.setParent(null);
/* 3788 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 3789 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 3790 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 3791 */         out = _jspx_page_context.pushBody();
/* 3792 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 3793 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3796 */         out.write("table.heading.attribute");
/* 3797 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 3798 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3801 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 3802 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3805 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 3806 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3807 */       return true;
/*      */     }
/* 3809 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3810 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3815 */     PageContext pageContext = _jspx_page_context;
/* 3816 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3818 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3819 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 3820 */     _jspx_th_fmt_005fmessage_005f10.setParent(null);
/* 3821 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 3822 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 3823 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 3824 */         out = _jspx_page_context.pushBody();
/* 3825 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 3826 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3829 */         out.write("table.heading.value");
/* 3830 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 3831 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3834 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 3835 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3838 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 3839 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3840 */       return true;
/*      */     }
/* 3842 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3843 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3848 */     PageContext pageContext = _jspx_page_context;
/* 3849 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3851 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3852 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 3853 */     _jspx_th_fmt_005fmessage_005f11.setParent(null);
/* 3854 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 3855 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 3856 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 3857 */         out = _jspx_page_context.pushBody();
/* 3858 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 3859 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3862 */         out.write("table.heading.status");
/* 3863 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 3864 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3867 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 3868 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3871 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 3872 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 3873 */       return true;
/*      */     }
/* 3875 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 3876 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3881 */     PageContext pageContext = _jspx_page_context;
/* 3882 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3884 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3885 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 3886 */     _jspx_th_fmt_005fmessage_005f12.setParent(null);
/* 3887 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 3888 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 3889 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 3890 */         out = _jspx_page_context.pushBody();
/* 3891 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 3892 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3895 */         out.write("table.heading.attribute");
/* 3896 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 3897 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3900 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 3901 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3904 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 3905 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 3906 */       return true;
/*      */     }
/* 3908 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 3909 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3914 */     PageContext pageContext = _jspx_page_context;
/* 3915 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3917 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3918 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 3919 */     _jspx_th_fmt_005fmessage_005f13.setParent(null);
/* 3920 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 3921 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 3922 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 3923 */         out = _jspx_page_context.pushBody();
/* 3924 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 3925 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3928 */         out.write("table.heading.value");
/* 3929 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 3930 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3933 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 3934 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3937 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 3938 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 3939 */       return true;
/*      */     }
/* 3941 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 3942 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3947 */     PageContext pageContext = _jspx_page_context;
/* 3948 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3950 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3951 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 3952 */     _jspx_th_fmt_005fmessage_005f14.setParent(null);
/* 3953 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 3954 */     if (_jspx_eval_fmt_005fmessage_005f14 != 0) {
/* 3955 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 3956 */         out = _jspx_page_context.pushBody();
/* 3957 */         _jspx_th_fmt_005fmessage_005f14.setBodyContent((BodyContent)out);
/* 3958 */         _jspx_th_fmt_005fmessage_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3961 */         out.write("table.heading.status");
/* 3962 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f14.doAfterBody();
/* 3963 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3966 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 3967 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3970 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 3971 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 3972 */       return true;
/*      */     }
/* 3974 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 3975 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\DB2DatabaseDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */