/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
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
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.ButtonTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class ServiceNow_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*   63 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
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
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
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
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, javax.servlet.http.HttpSession session)
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
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, javax.servlet.http.HttpSession session)
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
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, javax.servlet.http.HttpSession session, HttpServletRequest request)
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
/* 1701 */           String attributeName = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
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
/* 2142 */           String message = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2143 */           String image = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
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
/* 2172 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2178 */   private static java.util.Map<String, Long> _jspx_dependants = new HashMap(5);
/* 2179 */   static { _jspx_dependants.put("/jsp/includes/HelpDeskChooser.jspf", Long.valueOf(1473429417000L));
/* 2180 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/* 2181 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2182 */     _jspx_dependants.put("/jsp/includes/ServiceNow.jspf", Long.valueOf(1473429417000L));
/* 2183 */     _jspx_dependants.put("/jsp/includes/SDeskSettings.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/* 2213 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2217 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2240 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2244 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2248 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2249 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2250 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2252 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/* 2253 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2254 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2256 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2257 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.release();
/* 2258 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/* 2259 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2260 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.release();
/* 2261 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.release();
/* 2262 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/* 2263 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2264 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2265 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2272 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/* 2275 */     JspWriter out = null;
/* 2276 */     Object page = this;
/* 2277 */     JspWriter _jspx_out = null;
/* 2278 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2282 */       response.setContentType("text/html;charset=UTF-8");
/* 2283 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2285 */       _jspx_page_context = pageContext;
/* 2286 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2287 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2288 */       session = pageContext.getSession();
/* 2289 */       out = pageContext.getOut();
/* 2290 */       _jspx_out = out;
/*      */       
/* 2292 */       out.write("<!DOCTYPE html>\n");
/* 2293 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2294 */       out.write(10);
/* 2295 */       out.write(10);
/*      */       
/* 2297 */       request.setAttribute("HelpKey", "ServiceDesk Settings");
/*      */       
/* 2299 */       out.write("\n\n\n\n\n\n\n\n\n");
/* 2300 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2302 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2303 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2304 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2306 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2308 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2310 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2312 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2313 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2314 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2315 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2318 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2319 */         String available = null;
/* 2320 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2321 */         out.write(10);
/*      */         
/* 2323 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2324 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2325 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2327 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2329 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2331 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2333 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2334 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2335 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2336 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2339 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2340 */           String unavailable = null;
/* 2341 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2342 */           out.write(10);
/*      */           
/* 2344 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2345 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2346 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2348 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2350 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2352 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2354 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2355 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2356 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2357 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2360 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2361 */             String unmanaged = null;
/* 2362 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2363 */             out.write(10);
/*      */             
/* 2365 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2366 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2367 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2369 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2371 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2373 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2375 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2376 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2377 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2378 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2381 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2382 */               String scheduled = null;
/* 2383 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2384 */               out.write(10);
/*      */               
/* 2386 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2387 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2388 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2390 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2392 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2394 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2396 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2397 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2398 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2399 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2402 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2403 */                 String critical = null;
/* 2404 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2405 */                 out.write(10);
/*      */                 
/* 2407 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2408 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2409 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2411 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2413 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2415 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2417 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2418 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2419 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2420 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2423 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2424 */                   String clear = null;
/* 2425 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2426 */                   out.write(10);
/*      */                   
/* 2428 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2429 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2430 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2432 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2434 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2436 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2438 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2439 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2440 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2441 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2444 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2445 */                     String warning = null;
/* 2446 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2447 */                     out.write(10);
/* 2448 */                     out.write(10);
/*      */                     
/* 2450 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2451 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2453 */                     out.write(10);
/* 2454 */                     out.write(10);
/* 2455 */                     out.write(10);
/* 2456 */                     out.write(10);
/* 2457 */                     out.write(10);
/* 2458 */                     out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2459 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2461 */                     out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 2462 */                     out.write(10);
/* 2463 */                     out.write(10);
/* 2464 */                     out.write(10);
/*      */                     
/* 2466 */                     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2467 */                     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2468 */                     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2469 */                     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2470 */                     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                       for (;;) {
/* 2472 */                         out.write(10);
/*      */                         
/* 2474 */                         WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2475 */                         _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2476 */                         _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                         
/* 2478 */                         _jspx_th_c_005fwhen_005f0.setTest("${param.global!='true'}");
/* 2479 */                         int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2480 */                         if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                           for (;;) {
/* 2482 */                             out.write(10);
/*      */                             
/* 2484 */                             org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f0 = (org.apache.struts.taglib.tiles.InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(org.apache.struts.taglib.tiles.InsertTag.class);
/* 2485 */                             _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2486 */                             _jspx_th_tiles_005finsert_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 2488 */                             _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2489 */                             int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2490 */                             if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                               for (;;) {
/* 2492 */                                 out.write(10);
/* 2493 */                                 out.write(32);
/* 2494 */                                 if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                                   return;
/* 2496 */                                 out.write("\n   ");
/* 2497 */                                 if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                                   return;
/* 2499 */                                 out.write(10);
/* 2500 */                                 out.write(10);
/*      */                                 
/* 2502 */                                 PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2503 */                                 _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2504 */                                 _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                                 
/* 2506 */                                 _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                                 
/* 2508 */                                 _jspx_th_tiles_005fput_005f2.setType("string");
/* 2509 */                                 int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2510 */                                 if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2511 */                                   if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2512 */                                     out = _jspx_page_context.pushBody();
/* 2513 */                                     _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2514 */                                     _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 2517 */                                     out.write("\n\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2518 */                                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 2519 */                                     out.write(" &gt;<span class=\"bcactive\"> ");
/* 2520 */                                     out.print(FormatUtil.getString("am.webclient.addonproduct.servicedesk.title"));
/* 2521 */                                     out.write("</span></td>\n  </tr>\n</table>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\n\t\t<tr>\n\t\t\t<td width=\"100%\" valign=\"top\">\n\t\t");
/* 2522 */                                     out.write("<!-- $Id$ -->\n");
/*      */                                     
/* 2524 */                                     FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2525 */                                     _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2526 */                                     _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                     
/* 2528 */                                     _jspx_th_html_005fform_005f0.setAction("/adminAction.do");
/*      */                                     
/* 2530 */                                     _jspx_th_html_005fform_005f0.setStyle("display:inline");
/* 2531 */                                     int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2532 */                                     if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                       for (;;) {
/* 2534 */                                         out.write("\n<am:hiddenparam name=\"global\"/>\n<am:hiddenparam name=\"returnpath\"/>\n\n\n\n<style type=\"text/css\">\n\n.ulstyleforcf \n{\nlist-style-type: none;\nmargin: 0px;\npadding:0px;\n}\n\n.listyleforcf \n{\n\tfloat: left;\n\tposition: relative;\n\tpadding-left: 0px;\n\tpadding-right: 0px;\n\n}\n\n.ulanchor{\nbackground-image: url(/images/icon_customfield_sep.gif);\nbackground-repeat: no-repeat;\nbackground-position: right;\npadding-right: 20px;\npadding-left: 20px;\nfloat:left;\nline-height: 50px;\ntext-decoration: none;\ncolor: #595959;\nfont-size: 11px;\nfont-weight: normal;\n}\n\n.ulanchoractive{\nbackground-image: url(/images/icon_customfield_sep.gif);\nbackground-repeat: no-repeat;\nbackground-position: right;\npadding-right: 20px;\npadding-left: 20px;\nfloat:left;\nline-height: 50px;\ntext-decoration: none;\ncolor: #595959;\nfont-size: 11px;\nfont-weight: bold;\n}\n\n\n/* #SDPTabs .liselectedforcf a span.tabarrows \n{\n\tdisplay: block;\n} */\n\n#SDPTabs span.tabarrows {\ntext-decoration: underline;\nbackground-image: url(/images/icon_customfield_select.gif);\nbackground-repeat: no-repeat;\n");
/* 2535 */                                         out.write("padding-top: 10px;\ntop: 52px;\nposition: absolute;\nwidth: 100%;\nhorizontal-align: center;\ndisplay: none;\nleft: 40%;\nheight: 18px;\nwidth: 34px;\n}\n</style>\n<input type=\"hidden\" id=\"methodName\" name=\"method\" value=\"ServiceNowConfiguration\" />\n");
/* 2536 */                                         out.write("<!-- $Id$ -->\n<am:hiddenparam name=\"global\"/>\n<table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\">\n\t<tr>\n\t\t<td class=\"bodytext label-align\" width=\"25%\" nowrap=\"\" >\n\t\t\t\t");
/* 2537 */                                         out.print(FormatUtil.getString("am.webclient.select.helpDesk.product"));
/* 2538 */                                         out.write("\n\t\t</td> \n\t\t<td colspan=\"2\" class=\"bodytext\" width=\"75%\">\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t<td class=\"bodytext\">\n\t\t\t\t\t\t\t");
/* 2539 */                                         if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2541 */                                         out.print(FormatUtil.getString("ManageEngine ServiceDesk Plus"));
/* 2542 */                                         out.write("&nbsp;&nbsp;\n\t\t\t\t\t\t\t");
/* 2543 */                                         if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2545 */                                         out.print(FormatUtil.getString("ServiceNow"));
/* 2546 */                                         out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t</td>\n\t</tr> \n</table>\n");
/* 2547 */                                         out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t<!-- Heading tab -->\n\t<tr >\n\t\t<td width=\"72%\" height=\"31\" class=\"tableheading\" >");
/* 2548 */                                         out.print(FormatUtil.getString("am.webclient.servicenow.integration.product.details"));
/* 2549 */                                         out.write("</td>\n\t</tr>\n\t\n\t<tr>\n\t\t<td>\n\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td>\n  \n\t\t\t\t\t<!-- Details Heading -->\n\t\t\t\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"whitegreyborder\">\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"25%\" class=\"bodytext label-align\">");
/* 2550 */                                         out.print(FormatUtil.getString("am.webclient.servicenow.details.instanceName"));
/* 2551 */                                         out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t\t\t\t\t\t<td width=\"75%\" class=\"bodytext\">");
/* 2552 */                                         if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2554 */                                         out.write("</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"25%\" class=\"bodytext label-align\">");
/* 2555 */                                         out.print(FormatUtil.getString("am.webclient.servicenow.details.username"));
/* 2556 */                                         out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t\t\t\t\t\t<td width=\"75%\" class=\"bodytext\">");
/* 2557 */                                         if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2559 */                                         out.write("</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t<td width=\"25%\" class=\"bodytext label-align\">");
/* 2560 */                                         out.print(FormatUtil.getString("am.webclient.servicenow.details.password"));
/* 2561 */                                         out.write("\n\t\t\t\t\t\t\t\t<span class=\"mandatory\">*</span></td>\n\t\t\t\t\t\t\t\t<td width=\"75%\" class=\"bodytext\">\n\t\t\t\t\t\t\t\t");
/* 2562 */                                         if (_jspx_meth_c_005fchoose_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2564 */                                         out.write("\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n\t<tr>\n\t<td>\n\t\t");
/* 2565 */                                         out.write("<!--$Id$-->\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" id=\"advanced_api_settings\">\n   <tr>\n      <td>\n         <h3 class=\"head-settings\"><input type=\"checkbox\" name=\"advanced_settings\" onChange=\"javascript:showAdvnSettings(),handleMSPDesk()\" id=\"advanced_settings\" />");
/* 2566 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.advancedsettings"));
/* 2567 */                                         out.write("</h3>\n         <div id=\"settings_container\" style=\"display: none;\">\n\t\t \n          <h7 class=\"head-settings\">");
/* 2568 */                                         if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2570 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ticket.enableticketing"));
/* 2571 */                                         out.write(" </h7>");
/* 2572 */                                         out.write("\n            <div class=\"sub-info\" id=\"sub-info-ticket\">\n            <fieldset class=\"fieldset_replace\">\n               <legend> ");
/* 2573 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ticket.ticketsetting"));
/* 2574 */                                         out.write(" </legend>\n               <table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"ci-setting-container\">\n                  <tbody>\n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2575 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopenpolicy"));
/* 2576 */                                         out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"input-align-td\">\n                           <tbody>\n                                 <tr>\n                                    <td>");
/* 2577 */                                         if (_jspx_meth_html_005fradio_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2579 */                                         out.write("</td>\t\t\t\t\t\t\t\t\n                                    <td>");
/* 2580 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopen.always"));
/* 2581 */                                         out.write("</td>\n                                 </tr>\n                                 <tr>\n                                    <td>");
/* 2582 */                                         if (_jspx_meth_html_005fradio_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2584 */                                         out.write("</td>\n                                    <td>");
/* 2585 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopen.period.1"));
/* 2586 */                                         out.write(32);
/* 2587 */                                         if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2589 */                                         out.write(32);
/* 2590 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopen.period.2"));
/* 2591 */                                         out.write("</td>\n                                 </tr>\n                                \n                                 <tr>\n                                    <td>");
/* 2592 */                                         if (_jspx_meth_html_005fradio_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2594 */                                         out.write("</td>\n                                    <td>");
/* 2595 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ticket.createticket"));
/* 2596 */                                         out.write("</td>\n                                 </tr>\n                                 \n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2597 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ticket.autoclosepolicy"));
/* 2598 */                                         out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"input-align-td\">\n                           <tbody>\n                                 <tr>\n                                    <td>");
/* 2599 */                                         if (_jspx_meth_html_005fradio_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2601 */                                         out.write("</td>\t\t\t\t\t\t\t\t\n                                    <td>");
/* 2602 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ticket.closeticket"));
/* 2603 */                                         out.write("</td>\n                                 </tr>\n                                 <tr>\n                                    <td>");
/* 2604 */                                         if (_jspx_meth_html_005fradio_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2606 */                                         out.write("</td>\n                                    <td>");
/* 2607 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ticket.updatenotes"));
/* 2608 */                                         out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2609 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ticket.addnotes"));
/* 2610 */                                         out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2611 */                                         if (_jspx_meth_html_005fradio_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2613 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2614 */                                         out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 2615 */                                         if (_jspx_meth_html_005fradio_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2617 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2618 */                                         out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2619 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ticket.dynamicticketing.action"));
/* 2620 */                                         out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2621 */                                         if (_jspx_meth_html_005fradio_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2623 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2624 */                                         out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 2625 */                                         if (_jspx_meth_html_005fradio_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2627 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2628 */                                         out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2629 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ticket.dynamicticketing.form"));
/* 2630 */                                         out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2631 */                                         if (_jspx_meth_html_005fradio_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2633 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2634 */                                         out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 2635 */                                         if (_jspx_meth_html_005fradio_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2637 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2638 */                                         out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                       ");
/* 2639 */                                         out.write("\n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2640 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ticket.ticketdetailsfromalrm", new Object[] { FormatUtil.getString("product.name") }));
/* 2641 */                                         out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2642 */                                         if (_jspx_meth_html_005fradio_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2644 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2645 */                                         out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 2646 */                                         if (_jspx_meth_html_005fradio_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2648 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2649 */                                         out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/*      */                                         
/* 2651 */                                         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2652 */                                         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2653 */                                         _jspx_th_c_005fif_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                         
/* 2655 */                                         _jspx_th_c_005fif_005f0.setTest("${showAllSettings=='true'}");
/* 2656 */                                         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2657 */                                         if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                           for (;;) {
/* 2659 */                                             out.write("\n\t\t\t\t\t <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2660 */                                             out.print(FormatUtil.getString("me.sdp.cmdb.ticket.readonly"));
/* 2661 */                                             out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2662 */                                             if (_jspx_meth_html_005fradio_005f15(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                               return;
/* 2664 */                                             out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2665 */                                             out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 2666 */                                             if (_jspx_meth_html_005fradio_005f16(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                               return;
/* 2668 */                                             out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2669 */                                             out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t \n\t\t\t\t\t <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2670 */                                             out.print(FormatUtil.getString("me.sdp.cmdb.update.ticket.status.update"));
/* 2671 */                                             out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2672 */                                             if (_jspx_meth_html_005fradio_005f17(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                               return;
/* 2674 */                                             out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2675 */                                             out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 2676 */                                             if (_jspx_meth_html_005fradio_005f18(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                               return;
/* 2678 */                                             out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2679 */                                             out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/*      */                                             
/* 2681 */                                             IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2682 */                                             _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2683 */                                             _jspx_th_c_005fif_005f1.setParent(_jspx_th_c_005fif_005f0);
/*      */                                             
/* 2685 */                                             _jspx_th_c_005fif_005f1.setTest("${isServiceNow ne 'true'}");
/* 2686 */                                             int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2687 */                                             if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                               for (;;) {
/* 2689 */                                                 out.write("\n\t\t\t\t\t  <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2690 */                                                 out.print(FormatUtil.getString("me.sdp.cmdb.reqTemplate.overwrite"));
/* 2691 */                                                 out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2692 */                                                 if (_jspx_meth_html_005fradio_005f19(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                                   return;
/* 2694 */                                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2695 */                                                 out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 2696 */                                                 if (_jspx_meth_html_005fradio_005f20(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                                   return;
/* 2698 */                                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2699 */                                                 out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/* 2700 */                                                 int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2701 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 2705 */                                             if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2706 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                                             }
/*      */                                             
/* 2709 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2710 */                                             out.write("\n                    ");
/* 2711 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2712 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2716 */                                         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2717 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                                         }
/*      */                                         
/* 2720 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2721 */                                         out.write("\n                 </tbody>\n               </table></fieldset>\n            </div>\n\t\t\t <h5 class=\"head-settings\">");
/* 2722 */                                         if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2724 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ci.cisync.enable"));
/* 2725 */                                         out.write(" </h5>");
/* 2726 */                                         out.write("\n         <div class=\"sub-info\" id=\"sub-info-ci\">\n            <fieldset class=\"fieldset_replace\">\n               <legend> ");
/* 2727 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ci.cisettings"));
/* 2728 */                                         out.write(" </legend>\n               <table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"ci-setting-container\">\n                  <tbody>\n                     <tr>\n                        <td class=\"bodytext label-align\">");
/* 2729 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ci.deleteci"));
/* 2730 */                                         out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2731 */                                         if (_jspx_meth_html_005fradio_005f21(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2733 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2734 */                                         out.write("</td>\n                                    <td width=\"75\">");
/* 2735 */                                         if (_jspx_meth_html_005fradio_005f22(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2737 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2738 */                                         out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     <tr width=\"100%\">\n                     \t<td width=\"45%\" valign=\"top\" class=\"bodytext label-align\">");
/* 2739 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ci.firstlevel.excludetype"));
/* 2740 */                                         out.write("</td>\n                        <td width=\"25%\">\n                        ");
/* 2741 */                                         if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2743 */                                         out.write("\t\t\t\t\n                        </td>\n                        <td width=\"5%\" align=\"center\" class=\"bodytext\">\n                           <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tr>\n                                 <td align=\"center\">");
/* 2744 */                                         if (_jspx_meth_html_005fbutton_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2746 */                                         out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\">");
/* 2747 */                                         if (_jspx_meth_html_005fbutton_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2749 */                                         out.write("</td>\n                             </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 2750 */                                         if (_jspx_meth_html_005fbutton_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2752 */                                         out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 2753 */                                         if (_jspx_meth_html_005fbutton_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2755 */                                         out.write("</td>\n                              </tr>\n                           </table>\n                        </td>\n                        <td width=\"25%\">\t\t\t\t\t\t\t\n                           ");
/* 2756 */                                         if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2758 */                                         out.write("\t\t\t\t\t\t\t\n                        </td>\n                     </tr>\n                     \n                     ");
/*      */                                         
/* 2760 */                                         IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2761 */                                         _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2762 */                                         _jspx_th_c_005fif_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                         
/* 2764 */                                         _jspx_th_c_005fif_005f2.setTest("${showAllSettings=='true'}");
/* 2765 */                                         int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2766 */                                         if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                           for (;;) {
/* 2768 */                                             out.write("\n                     <tr width=\"100%\">\n                     \t<td width=\"45%\" valign=\"top\" class=\"bodytext label-align\">");
/* 2769 */                                             out.print(FormatUtil.getString("me.sdp.cmdb.ci.secondlevel.includetype"));
/* 2770 */                                             out.write("</td>\n                        <td width=\"25%\">\t\t\t\t\t\n                           ");
/* 2771 */                                             if (_jspx_meth_html_005fselect_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                               return;
/* 2773 */                                             out.write("\t\t\t\t\t\n                        </td>\n                        <td width=\"5%\" align=\"center\" class=\"bodytext\">\n                           <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tr>\n                                 <td align=\"center\">");
/* 2774 */                                             if (_jspx_meth_html_005fbutton_005f4(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                               return;
/* 2776 */                                             out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\">");
/* 2777 */                                             if (_jspx_meth_html_005fbutton_005f5(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                               return;
/* 2779 */                                             out.write("</td>\n                             </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 2780 */                                             if (_jspx_meth_html_005fbutton_005f6(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                               return;
/* 2782 */                                             out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 2783 */                                             if (_jspx_meth_html_005fbutton_005f7(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                               return;
/* 2785 */                                             out.write("</td>\n                              </tr>\n                           </table>\n                        </td>\n                        <td width=\"25%\">\t\t\t\t\t\t\t\n                           ");
/* 2786 */                                             if (_jspx_meth_html_005fselect_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                               return;
/* 2788 */                                             out.write("\t\t\t\t\t\t\t\t\n                        </td>\n                     </tr>\n                     ");
/* 2789 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2790 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2794 */                                         if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2795 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                                         }
/*      */                                         
/* 2798 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2799 */                                         out.write("\n                     <tr>\n                     \t<td class=\"bodytext label-align\">");
/* 2800 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ci.customattributes"));
/* 2801 */                                         out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2802 */                                         if (_jspx_meth_html_005fradio_005f23(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2804 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2805 */                                         out.write("</td>\n                                    \n                                    <td width=\"75\">");
/* 2806 */                                         if (_jspx_meth_html_005fradio_005f24(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2808 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2809 */                                         out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     <tr>\n                     \t<td class=\"bodytext label-align\">");
/* 2810 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.ci.rlmapfromsnapshot", new Object[] { FormatUtil.getString("product.name") }));
/* 2811 */                                         out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2812 */                                         if (_jspx_meth_html_005fradio_005f25(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2814 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2815 */                                         out.write("</td>\n                                    \n                                    <td width=\"75\">");
/* 2816 */                                         if (_jspx_meth_html_005fradio_005f26(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 2818 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2819 */                                         out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/*      */                                         
/* 2821 */                                         IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2822 */                                         _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2823 */                                         _jspx_th_c_005fif_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                                         
/* 2825 */                                         _jspx_th_c_005fif_005f3.setTest("${showAllSettings=='true'}");
/* 2826 */                                         int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2827 */                                         if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                           for (;;) {
/* 2829 */                                             out.write("\n\t\t\t\t\t   <tr>\n                     \t<td class=\"bodytext label-align\">");
/* 2830 */                                             out.print(FormatUtil.getString("me.sdp.cmdb.ci.rlmap.withlist"));
/* 2831 */                                             out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 2832 */                                             if (_jspx_meth_html_005fradio_005f27(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                               return;
/* 2834 */                                             out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 2835 */                                             out.write("</td>\n                                    \n                                    <td width=\"75\">");
/* 2836 */                                             if (_jspx_meth_html_005fradio_005f28(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                               return;
/* 2838 */                                             out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 2839 */                                             out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/* 2840 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2841 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2845 */                                         if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2846 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                         }
/*      */                                         
/* 2849 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2850 */                                         out.write("\n                  </tbody>\n               </table></fieldset>\n            </div>\n            </div>\n      </td>\n\t </tr>\n</table>\n");
/* 2851 */                                         out.write("\n\t</td>\n\t<tr>\n\t<tr>\n\t\t<td>\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" >\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"25%\" class=\"tablebottom\">&nbsp;</td>\n\t\t\t\t\t<td width=\"75%\" height=\"31\" class=\"tablebottom\" ><input name=\"Submit\" value=\"");
/* 2852 */                                         out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.submit"));
/* 2853 */                                         out.write("\" type=\"button\" class=\"buttons btn_highlt\" onclick=\"fnFormSubmit();\">\n\t\t\t\t\t\t<input type=\"reset\" align=\"center\" class=\"buttons btn_reset\" value=\"");
/* 2854 */                                         out.print(FormatUtil.getString("webclient.admin.adduser.clear"));
/* 2855 */                                         out.write("\">\n\t\n\t\t\t\t\t\t<input type=\"button\" align=\"center\" class=\"buttons btn_link\" value=\"");
/* 2856 */                                         out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.cancel"));
/* 2857 */                                         out.write("\" onclick=\"javascript:history.back();\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n</table>\n\n");
/* 2858 */                                         int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2859 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2863 */                                     if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2864 */                                       this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                                     }
/*      */                                     
/* 2867 */                                     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2868 */                                     out.write("\n<script type=\"text/javascript\">\n\n jQuery(document).ready(function() //No I18N\n {\n\t//showAdvnSettings1();\n\tshowAdvnSettings2(); \n});\n \n\nfunction handleMSPDesk() {//Used for serviceNow\n\tjQuery(\"#sub_settings1\").hide();//NO I18N\n\tjQuery('h5').hide();//NO I18N\n\tjQuery(\"#sub-info-ci\").hide();//NO I18N\n\tdocument.AMActionForm.cISyncEnabled.value='false';//Need to remove this when CMDB is enabled for ServiceNow\t\n   }\n   \nfunction showAdvnSettings() {\n if(document.getElementById(\"advanced_settings\").checked == 1)\n  {\n   jQuery(\"#settings_container\").show();//NO I18N\n  }\n else {\n   jQuery(\"#settings_container\").hide();//NO I18N\n  }\n\n}\nfunction showAdvnSettings1() {\n if(document.getElementById(\"sub_settings1\").checked == 0)\n  {\n   jQuery(\"#sub-info-ci\").hide();//NO I18N\n  }\n else {\n   jQuery(\"#sub-info-ci\").show();//NO I18N\n  }\n}\nfunction showAdvnSettings2() {\n if(document.getElementById(\"sub_settings2\").checked == 0)\n  {\n   jQuery(\"#sub-info-ticket\").hide();//NO I18N\n  }\n else {\n   jQuery(\"#sub-info-ticket\").show();//NO I18N\n");
/* 2869 */                                     out.write("  }\n}\n\n\tfunction cancelModify()\n{    \n    var toReplaceInput = \"\";    \n    var toReplaceModifyPart = \"<a href=\\\"javascript:void(0)\\\" style=\\\"color:blue;text-decoration:underline;\\\" onclick=\\\"modifyPassword()\\\">");
/* 2870 */                                     if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                       return;
/* 2872 */                                     out.write("</a>\";\n    $(\"#tdSpan_password\").html(toReplaceInput);\n    $(\"#modifySpan_password\").html(toReplaceModifyPart);\n}\n\nfunction modifyPassword()\n{    \n\tvar toReplaceInput = \"<input type=\\\"password\\\" name =\\\"servicenowPassword\\\" styleClass=\\\"formtext default\\\" size=\\\"30\\\"/> \";\n\tvar toReplaceModifyPart = \"<a href=\\\"javascript:void(0)\\\" style=\\\"color:blue\\\" onclick=\\\"cancelModify()\\\">");
/* 2873 */                                     if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                       return;
/* 2875 */                                     out.write("</a>\";\n    $(\"#tdSpan_password\").html(toReplaceInput);\n    $(\"#modifySpan_password\").html(toReplaceModifyPart);\n\t$(\"#servicenowPassword\").focus();\n}\n\n</script>");
/* 2876 */                                     out.write("\n\t\t\n</td>\n</tr>\n\n\n<tr>\n<td width=\"100%\" valign=\"top\">\n");
/* 2877 */                                     org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.servicenow.helpcard")), request.getCharacterEncoding()), out, false);
/* 2878 */                                     out.write("\n</td>\n</tr>\n</table>\n ");
/* 2879 */                                     int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 2880 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 2883 */                                   if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2884 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 2887 */                                 if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2888 */                                   this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                                 }
/*      */                                 
/* 2891 */                                 this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 2892 */                                 out.write("\n     ");
/* 2893 */                                 if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                                   return;
/* 2895 */                                 out.write(10);
/* 2896 */                                 out.write(32);
/* 2897 */                                 int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 2898 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2902 */                             if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2903 */                               this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */                             }
/*      */                             
/* 2906 */                             this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2907 */                             out.write(32);
/* 2908 */                             out.write(10);
/* 2909 */                             out.write(32);
/* 2910 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2911 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2915 */                         if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2916 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                         }
/*      */                         
/* 2919 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2920 */                         out.write(10);
/*      */                         
/* 2922 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2923 */                         _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2924 */                         _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f0);
/* 2925 */                         int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2926 */                         if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                           for (;;) {
/* 2928 */                             out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n\t\t\t<td width=\"100%\" valign=\"top\">\n\t\t");
/* 2929 */                             out.write("<!-- $Id$ -->\n");
/*      */                             
/* 2931 */                             FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2932 */                             _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 2933 */                             _jspx_th_html_005fform_005f1.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                             
/* 2935 */                             _jspx_th_html_005fform_005f1.setAction("/adminAction.do");
/*      */                             
/* 2937 */                             _jspx_th_html_005fform_005f1.setStyle("display:inline");
/* 2938 */                             int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 2939 */                             if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                               for (;;) {
/* 2941 */                                 out.write("\n<am:hiddenparam name=\"global\"/>\n<am:hiddenparam name=\"returnpath\"/>\n\n\n\n<style type=\"text/css\">\n\n.ulstyleforcf \n{\nlist-style-type: none;\nmargin: 0px;\npadding:0px;\n}\n\n.listyleforcf \n{\n\tfloat: left;\n\tposition: relative;\n\tpadding-left: 0px;\n\tpadding-right: 0px;\n\n}\n\n.ulanchor{\nbackground-image: url(/images/icon_customfield_sep.gif);\nbackground-repeat: no-repeat;\nbackground-position: right;\npadding-right: 20px;\npadding-left: 20px;\nfloat:left;\nline-height: 50px;\ntext-decoration: none;\ncolor: #595959;\nfont-size: 11px;\nfont-weight: normal;\n}\n\n.ulanchoractive{\nbackground-image: url(/images/icon_customfield_sep.gif);\nbackground-repeat: no-repeat;\nbackground-position: right;\npadding-right: 20px;\npadding-left: 20px;\nfloat:left;\nline-height: 50px;\ntext-decoration: none;\ncolor: #595959;\nfont-size: 11px;\nfont-weight: bold;\n}\n\n\n/* #SDPTabs .liselectedforcf a span.tabarrows \n{\n\tdisplay: block;\n} */\n\n#SDPTabs span.tabarrows {\ntext-decoration: underline;\nbackground-image: url(/images/icon_customfield_select.gif);\nbackground-repeat: no-repeat;\n");
/* 2942 */                                 out.write("padding-top: 10px;\ntop: 52px;\nposition: absolute;\nwidth: 100%;\nhorizontal-align: center;\ndisplay: none;\nleft: 40%;\nheight: 18px;\nwidth: 34px;\n}\n</style>\n<input type=\"hidden\" id=\"methodName\" name=\"method\" value=\"ServiceNowConfiguration\" />\n");
/* 2943 */                                 out.write("<!-- $Id$ -->\n<am:hiddenparam name=\"global\"/>\n<table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\">\n\t<tr>\n\t\t<td class=\"bodytext label-align\" width=\"25%\" nowrap=\"\" >\n\t\t\t\t");
/* 2944 */                                 out.print(FormatUtil.getString("am.webclient.select.helpDesk.product"));
/* 2945 */                                 out.write("\n\t\t</td> \n\t\t<td colspan=\"2\" class=\"bodytext\" width=\"75%\">\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t<td class=\"bodytext\">\n\t\t\t\t\t\t\t");
/* 2946 */                                 if (_jspx_meth_html_005fradio_005f29(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 2948 */                                 out.print(FormatUtil.getString("ManageEngine ServiceDesk Plus"));
/* 2949 */                                 out.write("&nbsp;&nbsp;\n\t\t\t\t\t\t\t");
/* 2950 */                                 if (_jspx_meth_html_005fradio_005f30(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 2952 */                                 out.print(FormatUtil.getString("ServiceNow"));
/* 2953 */                                 out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t</td>\n\t</tr> \n</table>\n");
/* 2954 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t<!-- Heading tab -->\n\t<tr >\n\t\t<td width=\"72%\" height=\"31\" class=\"tableheading\" >");
/* 2955 */                                 out.print(FormatUtil.getString("am.webclient.servicenow.integration.product.details"));
/* 2956 */                                 out.write("</td>\n\t</tr>\n\t\n\t<tr>\n\t\t<td>\n\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td>\n  \n\t\t\t\t\t<!-- Details Heading -->\n\t\t\t\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"whitegreyborder\">\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"25%\" class=\"bodytext label-align\">");
/* 2957 */                                 out.print(FormatUtil.getString("am.webclient.servicenow.details.instanceName"));
/* 2958 */                                 out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t\t\t\t\t\t<td width=\"75%\" class=\"bodytext\">");
/* 2959 */                                 if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 2961 */                                 out.write("</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"25%\" class=\"bodytext label-align\">");
/* 2962 */                                 out.print(FormatUtil.getString("am.webclient.servicenow.details.username"));
/* 2963 */                                 out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t\t\t\t\t\t<td width=\"75%\" class=\"bodytext\">");
/* 2964 */                                 if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 2966 */                                 out.write("</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t<td width=\"25%\" class=\"bodytext label-align\">");
/* 2967 */                                 out.print(FormatUtil.getString("am.webclient.servicenow.details.password"));
/* 2968 */                                 out.write("\n\t\t\t\t\t\t\t\t<span class=\"mandatory\">*</span></td>\n\t\t\t\t\t\t\t\t<td width=\"75%\" class=\"bodytext\">\n\t\t\t\t\t\t\t\t");
/* 2969 */                                 if (_jspx_meth_c_005fchoose_005f2(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 2971 */                                 out.write("\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n\t<tr>\n\t<td>\n\t\t");
/* 2972 */                                 out.write("<!--$Id$-->\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" id=\"advanced_api_settings\">\n   <tr>\n      <td>\n         <h3 class=\"head-settings\"><input type=\"checkbox\" name=\"advanced_settings\" onChange=\"javascript:showAdvnSettings(),handleMSPDesk()\" id=\"advanced_settings\" />");
/* 2973 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.advancedsettings"));
/* 2974 */                                 out.write("</h3>\n         <div id=\"settings_container\" style=\"display: none;\">\n\t\t \n          <h7 class=\"head-settings\">");
/* 2975 */                                 if (_jspx_meth_html_005fcheckbox_005f2(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 2977 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ticket.enableticketing"));
/* 2978 */                                 out.write(" </h7>");
/* 2979 */                                 out.write("\n            <div class=\"sub-info\" id=\"sub-info-ticket\">\n            <fieldset class=\"fieldset_replace\">\n               <legend> ");
/* 2980 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ticket.ticketsetting"));
/* 2981 */                                 out.write(" </legend>\n               <table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"ci-setting-container\">\n                  <tbody>\n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 2982 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopenpolicy"));
/* 2983 */                                 out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"input-align-td\">\n                           <tbody>\n                                 <tr>\n                                    <td>");
/* 2984 */                                 if (_jspx_meth_html_005fradio_005f31(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 2986 */                                 out.write("</td>\t\t\t\t\t\t\t\t\n                                    <td>");
/* 2987 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopen.always"));
/* 2988 */                                 out.write("</td>\n                                 </tr>\n                                 <tr>\n                                    <td>");
/* 2989 */                                 if (_jspx_meth_html_005fradio_005f32(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 2991 */                                 out.write("</td>\n                                    <td>");
/* 2992 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopen.period.1"));
/* 2993 */                                 out.write(32);
/* 2994 */                                 if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 2996 */                                 out.write(32);
/* 2997 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ticket.reopen.period.2"));
/* 2998 */                                 out.write("</td>\n                                 </tr>\n                                \n                                 <tr>\n                                    <td>");
/* 2999 */                                 if (_jspx_meth_html_005fradio_005f33(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3001 */                                 out.write("</td>\n                                    <td>");
/* 3002 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ticket.createticket"));
/* 3003 */                                 out.write("</td>\n                                 </tr>\n                                 \n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3004 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ticket.autoclosepolicy"));
/* 3005 */                                 out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"input-align-td\">\n                           <tbody>\n                                 <tr>\n                                    <td>");
/* 3006 */                                 if (_jspx_meth_html_005fradio_005f34(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3008 */                                 out.write("</td>\t\t\t\t\t\t\t\t\n                                    <td>");
/* 3009 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ticket.closeticket"));
/* 3010 */                                 out.write("</td>\n                                 </tr>\n                                 <tr>\n                                    <td>");
/* 3011 */                                 if (_jspx_meth_html_005fradio_005f35(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3013 */                                 out.write("</td>\n                                    <td>");
/* 3014 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ticket.updatenotes"));
/* 3015 */                                 out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3016 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ticket.addnotes"));
/* 3017 */                                 out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3018 */                                 if (_jspx_meth_html_005fradio_005f36(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3020 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3021 */                                 out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 3022 */                                 if (_jspx_meth_html_005fradio_005f37(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3024 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3025 */                                 out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3026 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ticket.dynamicticketing.action"));
/* 3027 */                                 out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3028 */                                 if (_jspx_meth_html_005fradio_005f38(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3030 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3031 */                                 out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 3032 */                                 if (_jspx_meth_html_005fradio_005f39(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3034 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3035 */                                 out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3036 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ticket.dynamicticketing.form"));
/* 3037 */                                 out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3038 */                                 if (_jspx_meth_html_005fradio_005f40(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3040 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3041 */                                 out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 3042 */                                 if (_jspx_meth_html_005fradio_005f41(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3044 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3045 */                                 out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                       ");
/* 3046 */                                 out.write("\n                     \n                     <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3047 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ticket.ticketdetailsfromalrm", new Object[] { FormatUtil.getString("product.name") }));
/* 3048 */                                 out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3049 */                                 if (_jspx_meth_html_005fradio_005f42(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3051 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3052 */                                 out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 3053 */                                 if (_jspx_meth_html_005fradio_005f43(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3055 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3056 */                                 out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/*      */                                 
/* 3058 */                                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3059 */                                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3060 */                                 _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fform_005f1);
/*      */                                 
/* 3062 */                                 _jspx_th_c_005fif_005f4.setTest("${showAllSettings=='true'}");
/* 3063 */                                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3064 */                                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                   for (;;) {
/* 3066 */                                     out.write("\n\t\t\t\t\t <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3067 */                                     out.print(FormatUtil.getString("me.sdp.cmdb.ticket.readonly"));
/* 3068 */                                     out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3069 */                                     if (_jspx_meth_html_005fradio_005f44(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                       return;
/* 3071 */                                     out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3072 */                                     out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 3073 */                                     if (_jspx_meth_html_005fradio_005f45(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                       return;
/* 3075 */                                     out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3076 */                                     out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t \n\t\t\t\t\t <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3077 */                                     out.print(FormatUtil.getString("me.sdp.cmdb.update.ticket.status.update"));
/* 3078 */                                     out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3079 */                                     if (_jspx_meth_html_005fradio_005f46(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                       return;
/* 3081 */                                     out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3082 */                                     out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 3083 */                                     if (_jspx_meth_html_005fradio_005f47(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                       return;
/* 3085 */                                     out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3086 */                                     out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/*      */                                     
/* 3088 */                                     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3089 */                                     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3090 */                                     _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f4);
/*      */                                     
/* 3092 */                                     _jspx_th_c_005fif_005f5.setTest("${isServiceNow ne 'true'}");
/* 3093 */                                     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3094 */                                     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                       for (;;) {
/* 3096 */                                         out.write("\n\t\t\t\t\t  <tr>\n                        <td width=\"45%\" class=\"bodytext label-align align-top\">");
/* 3097 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.reqTemplate.overwrite"));
/* 3098 */                                         out.write("</td>\n                        <td colspan=\"3\" width=\"55%\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3099 */                                         if (_jspx_meth_html_005fradio_005f48(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                           return;
/* 3101 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3102 */                                         out.write("</td>\n                                    \n                                    <td width=\"75\">\n                                       ");
/* 3103 */                                         if (_jspx_meth_html_005fradio_005f49(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                           return;
/* 3105 */                                         out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3106 */                                         out.write("\n                                    </td>\n                                    \n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/* 3107 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3108 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3112 */                                     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3113 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                     }
/*      */                                     
/* 3116 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3117 */                                     out.write("\n                    ");
/* 3118 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3119 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3123 */                                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3124 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                 }
/*      */                                 
/* 3127 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3128 */                                 out.write("\n                 </tbody>\n               </table></fieldset>\n            </div>\n\t\t\t <h5 class=\"head-settings\">");
/* 3129 */                                 if (_jspx_meth_html_005fcheckbox_005f3(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3131 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ci.cisync.enable"));
/* 3132 */                                 out.write(" </h5>");
/* 3133 */                                 out.write("\n         <div class=\"sub-info\" id=\"sub-info-ci\">\n            <fieldset class=\"fieldset_replace\">\n               <legend> ");
/* 3134 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ci.cisettings"));
/* 3135 */                                 out.write(" </legend>\n               <table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"ci-setting-container\">\n                  <tbody>\n                     <tr>\n                        <td class=\"bodytext label-align\">");
/* 3136 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ci.deleteci"));
/* 3137 */                                 out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3138 */                                 if (_jspx_meth_html_005fradio_005f50(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3140 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3141 */                                 out.write("</td>\n                                    <td width=\"75\">");
/* 3142 */                                 if (_jspx_meth_html_005fradio_005f51(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3144 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3145 */                                 out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     \n                     \n                     <tr width=\"100%\">\n                     \t<td width=\"45%\" valign=\"top\" class=\"bodytext label-align\">");
/* 3146 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ci.firstlevel.excludetype"));
/* 3147 */                                 out.write("</td>\n                        <td width=\"25%\">\n                        ");
/* 3148 */                                 if (_jspx_meth_html_005fselect_005f4(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3150 */                                 out.write("\t\t\t\t\n                        </td>\n                        <td width=\"5%\" align=\"center\" class=\"bodytext\">\n                           <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tr>\n                                 <td align=\"center\">");
/* 3151 */                                 if (_jspx_meth_html_005fbutton_005f8(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3153 */                                 out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\">");
/* 3154 */                                 if (_jspx_meth_html_005fbutton_005f9(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3156 */                                 out.write("</td>\n                             </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 3157 */                                 if (_jspx_meth_html_005fbutton_005f10(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3159 */                                 out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 3160 */                                 if (_jspx_meth_html_005fbutton_005f11(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3162 */                                 out.write("</td>\n                              </tr>\n                           </table>\n                        </td>\n                        <td width=\"25%\">\t\t\t\t\t\t\t\n                           ");
/* 3163 */                                 if (_jspx_meth_html_005fselect_005f5(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3165 */                                 out.write("\t\t\t\t\t\t\t\n                        </td>\n                     </tr>\n                     \n                     ");
/*      */                                 
/* 3167 */                                 IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3168 */                                 _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3169 */                                 _jspx_th_c_005fif_005f6.setParent(_jspx_th_html_005fform_005f1);
/*      */                                 
/* 3171 */                                 _jspx_th_c_005fif_005f6.setTest("${showAllSettings=='true'}");
/* 3172 */                                 int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3173 */                                 if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                   for (;;) {
/* 3175 */                                     out.write("\n                     <tr width=\"100%\">\n                     \t<td width=\"45%\" valign=\"top\" class=\"bodytext label-align\">");
/* 3176 */                                     out.print(FormatUtil.getString("me.sdp.cmdb.ci.secondlevel.includetype"));
/* 3177 */                                     out.write("</td>\n                        <td width=\"25%\">\t\t\t\t\t\n                           ");
/* 3178 */                                     if (_jspx_meth_html_005fselect_005f6(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3180 */                                     out.write("\t\t\t\t\t\n                        </td>\n                        <td width=\"5%\" align=\"center\" class=\"bodytext\">\n                           <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tr>\n                                 <td align=\"center\">");
/* 3181 */                                     if (_jspx_meth_html_005fbutton_005f12(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3183 */                                     out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\">");
/* 3184 */                                     if (_jspx_meth_html_005fbutton_005f13(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3186 */                                     out.write("</td>\n                             </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 3187 */                                     if (_jspx_meth_html_005fbutton_005f14(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3189 */                                     out.write("</td>\n                              </tr>\n                              <tr>\n                                 <td  align=\"center\" valign=\"bottom\">");
/* 3190 */                                     if (_jspx_meth_html_005fbutton_005f15(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3192 */                                     out.write("</td>\n                              </tr>\n                           </table>\n                        </td>\n                        <td width=\"25%\">\t\t\t\t\t\t\t\n                           ");
/* 3193 */                                     if (_jspx_meth_html_005fselect_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3195 */                                     out.write("\t\t\t\t\t\t\t\t\n                        </td>\n                     </tr>\n                     ");
/* 3196 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3197 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3201 */                                 if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3202 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                 }
/*      */                                 
/* 3205 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3206 */                                 out.write("\n                     <tr>\n                     \t<td class=\"bodytext label-align\">");
/* 3207 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ci.customattributes"));
/* 3208 */                                 out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3209 */                                 if (_jspx_meth_html_005fradio_005f52(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3211 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3212 */                                 out.write("</td>\n                                    \n                                    <td width=\"75\">");
/* 3213 */                                 if (_jspx_meth_html_005fradio_005f53(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3215 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3216 */                                 out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n                     <tr>\n                     \t<td class=\"bodytext label-align\">");
/* 3217 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.ci.rlmapfromsnapshot", new Object[] { FormatUtil.getString("product.name") }));
/* 3218 */                                 out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3219 */                                 if (_jspx_meth_html_005fradio_005f54(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3221 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3222 */                                 out.write("</td>\n                                    \n                                    <td width=\"75\">");
/* 3223 */                                 if (_jspx_meth_html_005fradio_005f55(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                   return;
/* 3225 */                                 out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3226 */                                 out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/*      */                                 
/* 3228 */                                 IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3229 */                                 _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3230 */                                 _jspx_th_c_005fif_005f7.setParent(_jspx_th_html_005fform_005f1);
/*      */                                 
/* 3232 */                                 _jspx_th_c_005fif_005f7.setTest("${showAllSettings=='true'}");
/* 3233 */                                 int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3234 */                                 if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                   for (;;) {
/* 3236 */                                     out.write("\n\t\t\t\t\t   <tr>\n                     \t<td class=\"bodytext label-align\">");
/* 3237 */                                     out.print(FormatUtil.getString("me.sdp.cmdb.ci.rlmap.withlist"));
/* 3238 */                                     out.write("</td>\n                        <td colspan=\"3\">\n                           <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                              <tbody>\n                                 <tr>\n                                    <td width=\"75\">");
/* 3239 */                                     if (_jspx_meth_html_005fradio_005f56(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 3241 */                                     out.print(FormatUtil.getString("me.sdp.cmdb.option.yes"));
/* 3242 */                                     out.write("</td>\n                                    \n                                    <td width=\"75\">");
/* 3243 */                                     if (_jspx_meth_html_005fradio_005f57(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 3245 */                                     out.print(FormatUtil.getString("me.sdp.cmdb.option.no"));
/* 3246 */                                     out.write("</td>\n                                 </tr>\n                              </tbody>\n                           </table>\n                        </td>\n                     </tr>\n\t\t\t\t\t ");
/* 3247 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3248 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3252 */                                 if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3253 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                 }
/*      */                                 
/* 3256 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3257 */                                 out.write("\n                  </tbody>\n               </table></fieldset>\n            </div>\n            </div>\n      </td>\n\t </tr>\n</table>\n");
/* 3258 */                                 out.write("\n\t</td>\n\t<tr>\n\t<tr>\n\t\t<td>\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" >\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"25%\" class=\"tablebottom\">&nbsp;</td>\n\t\t\t\t\t<td width=\"75%\" height=\"31\" class=\"tablebottom\" ><input name=\"Submit\" value=\"");
/* 3259 */                                 out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.submit"));
/* 3260 */                                 out.write("\" type=\"button\" class=\"buttons btn_highlt\" onclick=\"fnFormSubmit();\">\n\t\t\t\t\t\t<input type=\"reset\" align=\"center\" class=\"buttons btn_reset\" value=\"");
/* 3261 */                                 out.print(FormatUtil.getString("webclient.admin.adduser.clear"));
/* 3262 */                                 out.write("\">\n\t\n\t\t\t\t\t\t<input type=\"button\" align=\"center\" class=\"buttons btn_link\" value=\"");
/* 3263 */                                 out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.cancel"));
/* 3264 */                                 out.write("\" onclick=\"javascript:history.back();\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n</table>\n\n");
/* 3265 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 3266 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3270 */                             if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 3271 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */                             }
/*      */                             
/* 3274 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 3275 */                             out.write("\n<script type=\"text/javascript\">\n\n jQuery(document).ready(function() //No I18N\n {\n\t//showAdvnSettings1();\n\tshowAdvnSettings2(); \n});\n \n\nfunction handleMSPDesk() {//Used for serviceNow\n\tjQuery(\"#sub_settings1\").hide();//NO I18N\n\tjQuery('h5').hide();//NO I18N\n\tjQuery(\"#sub-info-ci\").hide();//NO I18N\n\tdocument.AMActionForm.cISyncEnabled.value='false';//Need to remove this when CMDB is enabled for ServiceNow\t\n   }\n   \nfunction showAdvnSettings() {\n if(document.getElementById(\"advanced_settings\").checked == 1)\n  {\n   jQuery(\"#settings_container\").show();//NO I18N\n  }\n else {\n   jQuery(\"#settings_container\").hide();//NO I18N\n  }\n\n}\nfunction showAdvnSettings1() {\n if(document.getElementById(\"sub_settings1\").checked == 0)\n  {\n   jQuery(\"#sub-info-ci\").hide();//NO I18N\n  }\n else {\n   jQuery(\"#sub-info-ci\").show();//NO I18N\n  }\n}\nfunction showAdvnSettings2() {\n if(document.getElementById(\"sub_settings2\").checked == 0)\n  {\n   jQuery(\"#sub-info-ticket\").hide();//NO I18N\n  }\n else {\n   jQuery(\"#sub-info-ticket\").show();//NO I18N\n");
/* 3276 */                             out.write("  }\n}\n\n\tfunction cancelModify()\n{    \n    var toReplaceInput = \"\";    \n    var toReplaceModifyPart = \"<a href=\\\"javascript:void(0)\\\" style=\\\"color:blue;text-decoration:underline;\\\" onclick=\\\"modifyPassword()\\\">");
/* 3277 */                             if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                               return;
/* 3279 */                             out.write("</a>\";\n    $(\"#tdSpan_password\").html(toReplaceInput);\n    $(\"#modifySpan_password\").html(toReplaceModifyPart);\n}\n\nfunction modifyPassword()\n{    \n\tvar toReplaceInput = \"<input type=\\\"password\\\" name =\\\"servicenowPassword\\\" styleClass=\\\"formtext default\\\" size=\\\"30\\\"/> \";\n\tvar toReplaceModifyPart = \"<a href=\\\"javascript:void(0)\\\" style=\\\"color:blue\\\" onclick=\\\"cancelModify()\\\">");
/* 3280 */                             if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                               return;
/* 3282 */                             out.write("</a>\";\n    $(\"#tdSpan_password\").html(toReplaceInput);\n    $(\"#modifySpan_password\").html(toReplaceModifyPart);\n\t$(\"#servicenowPassword\").focus();\n}\n\n</script>");
/* 3283 */                             out.write("\n</td>\n</tr>\n\n<br>\n");
/* 3284 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3285 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3289 */                         if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3290 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                         }
/*      */                         
/* 3293 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3294 */                         out.write(10);
/* 3295 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3296 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3300 */                     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3301 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */                     }
/*      */                     else {
/* 3304 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3305 */                       out.write("\n<script>\n\nfunction fnFormSubmit()\n{\n\tvar isEdit = '");
/* 3306 */                       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */                         return;
/* 3308 */                       out.write("';\n\t// alert(\"isEdit:\"+isEdit);\n\t");
/* 3309 */                       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                         return;
/* 3311 */                       out.write(10);
/* 3312 */                       out.write(9);
/*      */                       
/* 3314 */                       org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (org.apache.struts.taglib.logic.NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(org.apache.struts.taglib.logic.NotPresentTag.class);
/* 3315 */                       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3316 */                       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                       
/* 3318 */                       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 3319 */                       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3320 */                       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                         for (;;) {
/* 3322 */                           out.write("\n\t\tif(trimAll(document.AMActionForm.servicenowInstance.value)=='')\n\t\t{\n\t\t\talert(\"");
/* 3323 */                           out.print(FormatUtil.getString("am.webclient.servicenow.instance.alert.name"));
/* 3324 */                           out.write("\");\n\t\t\tdocument.AMActionForm.servicenowInstance.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse if(trimAll(document.AMActionForm.servicenowUserName.value)=='')\n\t\t{\n\t\t\talert(\"");
/* 3325 */                           out.print(FormatUtil.getString("webclient.login.username.required.message"));
/* 3326 */                           out.write("\");\n\t\t\tdocument.AMActionForm.servicenowUserName.focus();\n\t\t\treturn false;\n\t\t}\n\t\tif(isEdit=='false')\n\t\t{\n\t\t\tif(trimAll(document.AMActionForm.servicenowPassword.value)=='')\n\t\t\t{\n\t\t\t\talert(\"");
/* 3327 */                           out.print(FormatUtil.getString("webclient.login.password.required.message"));
/* 3328 */                           out.write("\");\n\t\t\t\tdocument.AMActionForm.servicenowPassword.focus();\n\t\t\t\treturn false;\n\t\t\t}\n\t\t}\n\t\t\n\t\tdocument.AMActionForm.submit();\n\t");
/* 3329 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3330 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3334 */                       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3335 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */                       }
/*      */                       else {
/* 3338 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3339 */                         out.write("\n}\nfunction changeProduct(productName)\n    {\n    \tif(productName==\"ServiceDesk\")\n\t    {\n    \t\tlocation.href= \"/adminAction.do?method=showSdeskConfiguration\";\n\t    }\n\t    else\n\t    {\n\t    \tlocation.href= \"/adminAction.do?method=showServiceNowConfiguration\";\t    \t\t\n\t    }\n    }\n\t\n\n</script>");
/*      */                       }
/* 3341 */                     } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3342 */         out = _jspx_out;
/* 3343 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3344 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3345 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3348 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3354 */     PageContext pageContext = _jspx_page_context;
/* 3355 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3357 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3358 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3359 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3361 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3363 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3364 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3365 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3366 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3367 */       return true;
/*      */     }
/* 3369 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3370 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3375 */     PageContext pageContext = _jspx_page_context;
/* 3376 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3378 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3379 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 3380 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3382 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 3384 */     _jspx_th_tiles_005fput_005f0.setValue("Add-on/Products Settings");
/* 3385 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 3386 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 3387 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3388 */       return true;
/*      */     }
/* 3390 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3391 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3396 */     PageContext pageContext = _jspx_page_context;
/* 3397 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3399 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3400 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3401 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3403 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3405 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 3406 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3407 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3408 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3409 */       return true;
/*      */     }
/* 3411 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3412 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3417 */     PageContext pageContext = _jspx_page_context;
/* 3418 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3420 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3421 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 3422 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3424 */     _jspx_th_html_005fradio_005f0.setProperty("helpDeskProduct");
/*      */     
/* 3426 */     _jspx_th_html_005fradio_005f0.setValue("SERVICEDESK");
/*      */     
/* 3428 */     _jspx_th_html_005fradio_005f0.setOnclick("javascript:changeProduct('ServiceDesk')");
/* 3429 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 3430 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 3431 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 3432 */       return true;
/*      */     }
/* 3434 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 3435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3440 */     PageContext pageContext = _jspx_page_context;
/* 3441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3443 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3444 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 3445 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3447 */     _jspx_th_html_005fradio_005f1.setProperty("helpDeskProduct");
/*      */     
/* 3449 */     _jspx_th_html_005fradio_005f1.setValue("SERVICENOW");
/*      */     
/* 3451 */     _jspx_th_html_005fradio_005f1.setOnclick("javascript:changeProduct('ServiceNow')");
/* 3452 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 3453 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 3454 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 3455 */       return true;
/*      */     }
/* 3457 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 3458 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3463 */     PageContext pageContext = _jspx_page_context;
/* 3464 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3466 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3467 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3468 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3470 */     _jspx_th_html_005ftext_005f0.setProperty("servicenowInstance");
/*      */     
/* 3472 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/*      */     
/* 3474 */     _jspx_th_html_005ftext_005f0.setSize("65");
/* 3475 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3476 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3477 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3478 */       return true;
/*      */     }
/* 3480 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3481 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3486 */     PageContext pageContext = _jspx_page_context;
/* 3487 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3489 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3490 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3491 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3493 */     _jspx_th_html_005ftext_005f1.setProperty("servicenowUserName");
/*      */     
/* 3495 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext default");
/*      */     
/* 3497 */     _jspx_th_html_005ftext_005f1.setSize("30");
/* 3498 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 3499 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 3500 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3501 */       return true;
/*      */     }
/* 3503 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3504 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3509 */     PageContext pageContext = _jspx_page_context;
/* 3510 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3512 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3513 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 3514 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 3515 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 3516 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 3518 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 3519 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 3520 */           return true;
/* 3521 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 3522 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 3523 */           return true;
/* 3524 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 3525 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 3526 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3530 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 3531 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3532 */       return true;
/*      */     }
/* 3534 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3535 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3540 */     PageContext pageContext = _jspx_page_context;
/* 3541 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3543 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3544 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 3545 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 3547 */     _jspx_th_c_005fwhen_005f1.setTest("${editServiceNow eq \"false\"}");
/* 3548 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 3549 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 3551 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 3552 */         if (_jspx_meth_html_005fpassword_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 3553 */           return true;
/* 3554 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 3555 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 3556 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3560 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 3561 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3562 */       return true;
/*      */     }
/* 3564 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3565 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3570 */     PageContext pageContext = _jspx_page_context;
/* 3571 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3573 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 3574 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 3575 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 3577 */     _jspx_th_html_005fpassword_005f0.setProperty("servicenowPassword");
/*      */     
/* 3579 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext default");
/*      */     
/* 3581 */     _jspx_th_html_005fpassword_005f0.setSize("30");
/* 3582 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 3583 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 3584 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 3585 */       return true;
/*      */     }
/* 3587 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 3588 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3593 */     PageContext pageContext = _jspx_page_context;
/* 3594 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3596 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3597 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3598 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 3599 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3600 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 3602 */         out.write("\n\t\t\t\t\t\t\t\t<span id=\"tdSpan_password\"></span>\n\t\t\t\t\t\t\t\t <span id=\"modifySpan_password\">\n\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" style=\"color:blue;text-decoration:underline;\" onclick=\"modifyPassword()\">");
/* 3603 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 3604 */           return true;
/* 3605 */         out.write("</a>\n\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t");
/* 3606 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3607 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3611 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3612 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3613 */       return true;
/*      */     }
/* 3615 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3616 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3621 */     PageContext pageContext = _jspx_page_context;
/* 3622 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3624 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3625 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3626 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 3628 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.servicenow.details.modifyPassword");
/* 3629 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3630 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3631 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3632 */       return true;
/*      */     }
/* 3634 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3635 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3640 */     PageContext pageContext = _jspx_page_context;
/* 3641 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3643 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.get(CheckboxTag.class);
/* 3644 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 3645 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3647 */     _jspx_th_html_005fcheckbox_005f0.setProperty("ticketingEnabled");
/*      */     
/* 3649 */     _jspx_th_html_005fcheckbox_005f0.setStyleId("sub_settings2");
/*      */     
/* 3651 */     _jspx_th_html_005fcheckbox_005f0.setOnchange("javascript:showAdvnSettings2()");
/*      */     
/* 3653 */     _jspx_th_html_005fcheckbox_005f0.setValue("true");
/* 3654 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 3655 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 3656 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 3657 */       return true;
/*      */     }
/* 3659 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 3660 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3665 */     PageContext pageContext = _jspx_page_context;
/* 3666 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3668 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3669 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 3670 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3672 */     _jspx_th_html_005fradio_005f2.setProperty("reOpenTicketPolicy");
/*      */     
/* 3674 */     _jspx_th_html_005fradio_005f2.setValue("REOPEN_TICKET");
/* 3675 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 3676 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 3677 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 3678 */       return true;
/*      */     }
/* 3680 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 3681 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3686 */     PageContext pageContext = _jspx_page_context;
/* 3687 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3689 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3690 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 3691 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3693 */     _jspx_th_html_005fradio_005f3.setProperty("reOpenTicketPolicy");
/*      */     
/* 3695 */     _jspx_th_html_005fradio_005f3.setValue("REOPEN_TICKET_CUSTOM_PERIOD");
/* 3696 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 3697 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 3698 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 3699 */       return true;
/*      */     }
/* 3701 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 3702 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3707 */     PageContext pageContext = _jspx_page_context;
/* 3708 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3710 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3711 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 3712 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3714 */     _jspx_th_html_005ftext_005f2.setProperty("reOpenPeriod");
/*      */     
/* 3716 */     _jspx_th_html_005ftext_005f2.setSize("3");
/*      */     
/* 3718 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/* 3719 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 3720 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 3721 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3722 */       return true;
/*      */     }
/* 3724 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3725 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3730 */     PageContext pageContext = _jspx_page_context;
/* 3731 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3733 */     RadioTag _jspx_th_html_005fradio_005f4 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3734 */     _jspx_th_html_005fradio_005f4.setPageContext(_jspx_page_context);
/* 3735 */     _jspx_th_html_005fradio_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3737 */     _jspx_th_html_005fradio_005f4.setProperty("reOpenTicketPolicy");
/*      */     
/* 3739 */     _jspx_th_html_005fradio_005f4.setValue("NEW_TICKET");
/* 3740 */     int _jspx_eval_html_005fradio_005f4 = _jspx_th_html_005fradio_005f4.doStartTag();
/* 3741 */     if (_jspx_th_html_005fradio_005f4.doEndTag() == 5) {
/* 3742 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 3743 */       return true;
/*      */     }
/* 3745 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 3746 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3751 */     PageContext pageContext = _jspx_page_context;
/* 3752 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3754 */     RadioTag _jspx_th_html_005fradio_005f5 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3755 */     _jspx_th_html_005fradio_005f5.setPageContext(_jspx_page_context);
/* 3756 */     _jspx_th_html_005fradio_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3758 */     _jspx_th_html_005fradio_005f5.setProperty("closeTicketPolicy");
/*      */     
/* 3760 */     _jspx_th_html_005fradio_005f5.setValue("CLOSE_TICKET_UPDATE_NOTES");
/* 3761 */     int _jspx_eval_html_005fradio_005f5 = _jspx_th_html_005fradio_005f5.doStartTag();
/* 3762 */     if (_jspx_th_html_005fradio_005f5.doEndTag() == 5) {
/* 3763 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 3764 */       return true;
/*      */     }
/* 3766 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 3767 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3772 */     PageContext pageContext = _jspx_page_context;
/* 3773 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3775 */     RadioTag _jspx_th_html_005fradio_005f6 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3776 */     _jspx_th_html_005fradio_005f6.setPageContext(_jspx_page_context);
/* 3777 */     _jspx_th_html_005fradio_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3779 */     _jspx_th_html_005fradio_005f6.setProperty("closeTicketPolicy");
/*      */     
/* 3781 */     _jspx_th_html_005fradio_005f6.setValue("UPDATE_NOTES");
/* 3782 */     int _jspx_eval_html_005fradio_005f6 = _jspx_th_html_005fradio_005f6.doStartTag();
/* 3783 */     if (_jspx_th_html_005fradio_005f6.doEndTag() == 5) {
/* 3784 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 3785 */       return true;
/*      */     }
/* 3787 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 3788 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3793 */     PageContext pageContext = _jspx_page_context;
/* 3794 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3796 */     RadioTag _jspx_th_html_005fradio_005f7 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3797 */     _jspx_th_html_005fradio_005f7.setPageContext(_jspx_page_context);
/* 3798 */     _jspx_th_html_005fradio_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3800 */     _jspx_th_html_005fradio_005f7.setProperty("notesToBeAddedForTicket");
/*      */     
/* 3802 */     _jspx_th_html_005fradio_005f7.setValue("true");
/* 3803 */     int _jspx_eval_html_005fradio_005f7 = _jspx_th_html_005fradio_005f7.doStartTag();
/* 3804 */     if (_jspx_th_html_005fradio_005f7.doEndTag() == 5) {
/* 3805 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f7);
/* 3806 */       return true;
/*      */     }
/* 3808 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f7);
/* 3809 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3814 */     PageContext pageContext = _jspx_page_context;
/* 3815 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3817 */     RadioTag _jspx_th_html_005fradio_005f8 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3818 */     _jspx_th_html_005fradio_005f8.setPageContext(_jspx_page_context);
/* 3819 */     _jspx_th_html_005fradio_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3821 */     _jspx_th_html_005fradio_005f8.setProperty("notesToBeAddedForTicket");
/*      */     
/* 3823 */     _jspx_th_html_005fradio_005f8.setValue("false");
/* 3824 */     int _jspx_eval_html_005fradio_005f8 = _jspx_th_html_005fradio_005f8.doStartTag();
/* 3825 */     if (_jspx_th_html_005fradio_005f8.doEndTag() == 5) {
/* 3826 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f8);
/* 3827 */       return true;
/*      */     }
/* 3829 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f8);
/* 3830 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3835 */     PageContext pageContext = _jspx_page_context;
/* 3836 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3838 */     RadioTag _jspx_th_html_005fradio_005f9 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3839 */     _jspx_th_html_005fradio_005f9.setPageContext(_jspx_page_context);
/* 3840 */     _jspx_th_html_005fradio_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3842 */     _jspx_th_html_005fradio_005f9.setProperty("dynamicTicketingUsingAction");
/*      */     
/* 3844 */     _jspx_th_html_005fradio_005f9.setValue("true");
/* 3845 */     int _jspx_eval_html_005fradio_005f9 = _jspx_th_html_005fradio_005f9.doStartTag();
/* 3846 */     if (_jspx_th_html_005fradio_005f9.doEndTag() == 5) {
/* 3847 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f9);
/* 3848 */       return true;
/*      */     }
/* 3850 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f9);
/* 3851 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3856 */     PageContext pageContext = _jspx_page_context;
/* 3857 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3859 */     RadioTag _jspx_th_html_005fradio_005f10 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3860 */     _jspx_th_html_005fradio_005f10.setPageContext(_jspx_page_context);
/* 3861 */     _jspx_th_html_005fradio_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3863 */     _jspx_th_html_005fradio_005f10.setProperty("dynamicTicketingUsingAction");
/*      */     
/* 3865 */     _jspx_th_html_005fradio_005f10.setValue("false");
/* 3866 */     int _jspx_eval_html_005fradio_005f10 = _jspx_th_html_005fradio_005f10.doStartTag();
/* 3867 */     if (_jspx_th_html_005fradio_005f10.doEndTag() == 5) {
/* 3868 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f10);
/* 3869 */       return true;
/*      */     }
/* 3871 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f10);
/* 3872 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3877 */     PageContext pageContext = _jspx_page_context;
/* 3878 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3880 */     RadioTag _jspx_th_html_005fradio_005f11 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3881 */     _jspx_th_html_005fradio_005f11.setPageContext(_jspx_page_context);
/* 3882 */     _jspx_th_html_005fradio_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3884 */     _jspx_th_html_005fradio_005f11.setProperty("dynamicTicketingUsingForm");
/*      */     
/* 3886 */     _jspx_th_html_005fradio_005f11.setValue("true");
/* 3887 */     int _jspx_eval_html_005fradio_005f11 = _jspx_th_html_005fradio_005f11.doStartTag();
/* 3888 */     if (_jspx_th_html_005fradio_005f11.doEndTag() == 5) {
/* 3889 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f11);
/* 3890 */       return true;
/*      */     }
/* 3892 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f11);
/* 3893 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3898 */     PageContext pageContext = _jspx_page_context;
/* 3899 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3901 */     RadioTag _jspx_th_html_005fradio_005f12 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3902 */     _jspx_th_html_005fradio_005f12.setPageContext(_jspx_page_context);
/* 3903 */     _jspx_th_html_005fradio_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3905 */     _jspx_th_html_005fradio_005f12.setProperty("dynamicTicketingUsingForm");
/*      */     
/* 3907 */     _jspx_th_html_005fradio_005f12.setValue("false");
/* 3908 */     int _jspx_eval_html_005fradio_005f12 = _jspx_th_html_005fradio_005f12.doStartTag();
/* 3909 */     if (_jspx_th_html_005fradio_005f12.doEndTag() == 5) {
/* 3910 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f12);
/* 3911 */       return true;
/*      */     }
/* 3913 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f12);
/* 3914 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3919 */     PageContext pageContext = _jspx_page_context;
/* 3920 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3922 */     RadioTag _jspx_th_html_005fradio_005f13 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3923 */     _jspx_th_html_005fradio_005f13.setPageContext(_jspx_page_context);
/* 3924 */     _jspx_th_html_005fradio_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3926 */     _jspx_th_html_005fradio_005f13.setProperty("ticketLinkToBeShown");
/*      */     
/* 3928 */     _jspx_th_html_005fradio_005f13.setValue("true");
/* 3929 */     int _jspx_eval_html_005fradio_005f13 = _jspx_th_html_005fradio_005f13.doStartTag();
/* 3930 */     if (_jspx_th_html_005fradio_005f13.doEndTag() == 5) {
/* 3931 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f13);
/* 3932 */       return true;
/*      */     }
/* 3934 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f13);
/* 3935 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3940 */     PageContext pageContext = _jspx_page_context;
/* 3941 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3943 */     RadioTag _jspx_th_html_005fradio_005f14 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3944 */     _jspx_th_html_005fradio_005f14.setPageContext(_jspx_page_context);
/* 3945 */     _jspx_th_html_005fradio_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3947 */     _jspx_th_html_005fradio_005f14.setProperty("ticketLinkToBeShown");
/*      */     
/* 3949 */     _jspx_th_html_005fradio_005f14.setValue("false");
/* 3950 */     int _jspx_eval_html_005fradio_005f14 = _jspx_th_html_005fradio_005f14.doStartTag();
/* 3951 */     if (_jspx_th_html_005fradio_005f14.doEndTag() == 5) {
/* 3952 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f14);
/* 3953 */       return true;
/*      */     }
/* 3955 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f14);
/* 3956 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f15(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3961 */     PageContext pageContext = _jspx_page_context;
/* 3962 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3964 */     RadioTag _jspx_th_html_005fradio_005f15 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3965 */     _jspx_th_html_005fradio_005f15.setPageContext(_jspx_page_context);
/* 3966 */     _jspx_th_html_005fradio_005f15.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3968 */     _jspx_th_html_005fradio_005f15.setProperty("readonlyTicket");
/*      */     
/* 3970 */     _jspx_th_html_005fradio_005f15.setValue("true");
/* 3971 */     int _jspx_eval_html_005fradio_005f15 = _jspx_th_html_005fradio_005f15.doStartTag();
/* 3972 */     if (_jspx_th_html_005fradio_005f15.doEndTag() == 5) {
/* 3973 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f15);
/* 3974 */       return true;
/*      */     }
/* 3976 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f15);
/* 3977 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f16(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3982 */     PageContext pageContext = _jspx_page_context;
/* 3983 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3985 */     RadioTag _jspx_th_html_005fradio_005f16 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3986 */     _jspx_th_html_005fradio_005f16.setPageContext(_jspx_page_context);
/* 3987 */     _jspx_th_html_005fradio_005f16.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3989 */     _jspx_th_html_005fradio_005f16.setProperty("readonlyTicket");
/*      */     
/* 3991 */     _jspx_th_html_005fradio_005f16.setValue("false");
/* 3992 */     int _jspx_eval_html_005fradio_005f16 = _jspx_th_html_005fradio_005f16.doStartTag();
/* 3993 */     if (_jspx_th_html_005fradio_005f16.doEndTag() == 5) {
/* 3994 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f16);
/* 3995 */       return true;
/*      */     }
/* 3997 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f16);
/* 3998 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f17(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4003 */     PageContext pageContext = _jspx_page_context;
/* 4004 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4006 */     RadioTag _jspx_th_html_005fradio_005f17 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4007 */     _jspx_th_html_005fradio_005f17.setPageContext(_jspx_page_context);
/* 4008 */     _jspx_th_html_005fradio_005f17.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4010 */     _jspx_th_html_005fradio_005f17.setProperty("updateTicketForchangeInStatusAlone");
/*      */     
/* 4012 */     _jspx_th_html_005fradio_005f17.setValue("true");
/* 4013 */     int _jspx_eval_html_005fradio_005f17 = _jspx_th_html_005fradio_005f17.doStartTag();
/* 4014 */     if (_jspx_th_html_005fradio_005f17.doEndTag() == 5) {
/* 4015 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f17);
/* 4016 */       return true;
/*      */     }
/* 4018 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f17);
/* 4019 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f18(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4024 */     PageContext pageContext = _jspx_page_context;
/* 4025 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4027 */     RadioTag _jspx_th_html_005fradio_005f18 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4028 */     _jspx_th_html_005fradio_005f18.setPageContext(_jspx_page_context);
/* 4029 */     _jspx_th_html_005fradio_005f18.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4031 */     _jspx_th_html_005fradio_005f18.setProperty("updateTicketForchangeInStatusAlone");
/*      */     
/* 4033 */     _jspx_th_html_005fradio_005f18.setValue("false");
/* 4034 */     int _jspx_eval_html_005fradio_005f18 = _jspx_th_html_005fradio_005f18.doStartTag();
/* 4035 */     if (_jspx_th_html_005fradio_005f18.doEndTag() == 5) {
/* 4036 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f18);
/* 4037 */       return true;
/*      */     }
/* 4039 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f18);
/* 4040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f19(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4045 */     PageContext pageContext = _jspx_page_context;
/* 4046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4048 */     RadioTag _jspx_th_html_005fradio_005f19 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4049 */     _jspx_th_html_005fradio_005f19.setPageContext(_jspx_page_context);
/* 4050 */     _jspx_th_html_005fradio_005f19.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4052 */     _jspx_th_html_005fradio_005f19.setProperty("allowOverWriteOfReqTemplate");
/*      */     
/* 4054 */     _jspx_th_html_005fradio_005f19.setValue("true");
/* 4055 */     int _jspx_eval_html_005fradio_005f19 = _jspx_th_html_005fradio_005f19.doStartTag();
/* 4056 */     if (_jspx_th_html_005fradio_005f19.doEndTag() == 5) {
/* 4057 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f19);
/* 4058 */       return true;
/*      */     }
/* 4060 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f19);
/* 4061 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f20(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4066 */     PageContext pageContext = _jspx_page_context;
/* 4067 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4069 */     RadioTag _jspx_th_html_005fradio_005f20 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4070 */     _jspx_th_html_005fradio_005f20.setPageContext(_jspx_page_context);
/* 4071 */     _jspx_th_html_005fradio_005f20.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4073 */     _jspx_th_html_005fradio_005f20.setProperty("allowOverWriteOfReqTemplate");
/*      */     
/* 4075 */     _jspx_th_html_005fradio_005f20.setValue("false");
/* 4076 */     int _jspx_eval_html_005fradio_005f20 = _jspx_th_html_005fradio_005f20.doStartTag();
/* 4077 */     if (_jspx_th_html_005fradio_005f20.doEndTag() == 5) {
/* 4078 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f20);
/* 4079 */       return true;
/*      */     }
/* 4081 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f20);
/* 4082 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4087 */     PageContext pageContext = _jspx_page_context;
/* 4088 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4090 */     CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.get(CheckboxTag.class);
/* 4091 */     _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 4092 */     _jspx_th_html_005fcheckbox_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4094 */     _jspx_th_html_005fcheckbox_005f1.setProperty("cISyncEnabled");
/*      */     
/* 4096 */     _jspx_th_html_005fcheckbox_005f1.setStyleId("sub_settings1");
/*      */     
/* 4098 */     _jspx_th_html_005fcheckbox_005f1.setOnchange("javascript:showAdvnSettings1()");
/*      */     
/* 4100 */     _jspx_th_html_005fcheckbox_005f1.setValue("true");
/* 4101 */     int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 4102 */     if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 4103 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 4104 */       return true;
/*      */     }
/* 4106 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 4107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f21(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4112 */     PageContext pageContext = _jspx_page_context;
/* 4113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4115 */     RadioTag _jspx_th_html_005fradio_005f21 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4116 */     _jspx_th_html_005fradio_005f21.setPageContext(_jspx_page_context);
/* 4117 */     _jspx_th_html_005fradio_005f21.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4119 */     _jspx_th_html_005fradio_005f21.setProperty("ciToBeDeleted");
/*      */     
/* 4121 */     _jspx_th_html_005fradio_005f21.setValue("true");
/* 4122 */     int _jspx_eval_html_005fradio_005f21 = _jspx_th_html_005fradio_005f21.doStartTag();
/* 4123 */     if (_jspx_th_html_005fradio_005f21.doEndTag() == 5) {
/* 4124 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f21);
/* 4125 */       return true;
/*      */     }
/* 4127 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f21);
/* 4128 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f22(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4133 */     PageContext pageContext = _jspx_page_context;
/* 4134 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4136 */     RadioTag _jspx_th_html_005fradio_005f22 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4137 */     _jspx_th_html_005fradio_005f22.setPageContext(_jspx_page_context);
/* 4138 */     _jspx_th_html_005fradio_005f22.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4140 */     _jspx_th_html_005fradio_005f22.setProperty("ciToBeDeleted");
/*      */     
/* 4142 */     _jspx_th_html_005fradio_005f22.setValue("false");
/* 4143 */     int _jspx_eval_html_005fradio_005f22 = _jspx_th_html_005fradio_005f22.doStartTag();
/* 4144 */     if (_jspx_th_html_005fradio_005f22.doEndTag() == 5) {
/* 4145 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f22);
/* 4146 */       return true;
/*      */     }
/* 4148 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f22);
/* 4149 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4154 */     PageContext pageContext = _jspx_page_context;
/* 4155 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4157 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 4158 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 4159 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4161 */     _jspx_th_html_005fselect_005f0.setProperty("toAdd");
/*      */     
/* 4163 */     _jspx_th_html_005fselect_005f0.setStyle("width:100%");
/*      */     
/* 4165 */     _jspx_th_html_005fselect_005f0.setMultiple("true");
/*      */     
/* 4167 */     _jspx_th_html_005fselect_005f0.setSize("5");
/* 4168 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 4169 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 4170 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4171 */         out = _jspx_page_context.pushBody();
/* 4172 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 4173 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4176 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4177 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 4178 */           return true;
/* 4179 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4180 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 4181 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4184 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4185 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4188 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 4189 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f0);
/* 4190 */       return true;
/*      */     }
/* 4192 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f0);
/* 4193 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4198 */     PageContext pageContext = _jspx_page_context;
/* 4199 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4201 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 4202 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 4203 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 4205 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("firstLevelMonitorTypesOptions");
/*      */     
/* 4207 */     _jspx_th_html_005foptionsCollection_005f0.setLabel("label");
/*      */     
/* 4209 */     _jspx_th_html_005foptionsCollection_005f0.setValue("value");
/* 4210 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 4211 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 4212 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 4213 */       return true;
/*      */     }
/* 4215 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 4216 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4221 */     PageContext pageContext = _jspx_page_context;
/* 4222 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4224 */     ButtonTag _jspx_th_html_005fbutton_005f0 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 4225 */     _jspx_th_html_005fbutton_005f0.setPageContext(_jspx_page_context);
/* 4226 */     _jspx_th_html_005fbutton_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4228 */     _jspx_th_html_005fbutton_005f0.setStyleClass("buttons btn_small");
/*      */     
/* 4230 */     _jspx_th_html_005fbutton_005f0.setProperty("AddButton2");
/*      */     
/* 4232 */     _jspx_th_html_005fbutton_005f0.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.toAdd,document.AMActionForm.excludeFirstLevelMonitorTypes),fnRemoveFromRightCombo(document.AMActionForm.toAdd);");
/*      */     
/* 4234 */     _jspx_th_html_005fbutton_005f0.setValue("&nbsp;&gt;&nbsp;");
/* 4235 */     int _jspx_eval_html_005fbutton_005f0 = _jspx_th_html_005fbutton_005f0.doStartTag();
/* 4236 */     if (_jspx_th_html_005fbutton_005f0.doEndTag() == 5) {
/* 4237 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0);
/* 4238 */       return true;
/*      */     }
/* 4240 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0);
/* 4241 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4246 */     PageContext pageContext = _jspx_page_context;
/* 4247 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4249 */     ButtonTag _jspx_th_html_005fbutton_005f1 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 4250 */     _jspx_th_html_005fbutton_005f1.setPageContext(_jspx_page_context);
/* 4251 */     _jspx_th_html_005fbutton_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4253 */     _jspx_th_html_005fbutton_005f1.setStyleClass("buttons btn_small");
/*      */     
/* 4255 */     _jspx_th_html_005fbutton_005f1.setProperty("AddButton2");
/*      */     
/* 4257 */     _jspx_th_html_005fbutton_005f1.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.toAdd,document.AMActionForm.excludeFirstLevelMonitorTypes),fnRemoveAllFromCombo(document.AMActionForm.toAdd);");
/*      */     
/* 4259 */     _jspx_th_html_005fbutton_005f1.setValue("&gt;&gt;");
/* 4260 */     int _jspx_eval_html_005fbutton_005f1 = _jspx_th_html_005fbutton_005f1.doStartTag();
/* 4261 */     if (_jspx_th_html_005fbutton_005f1.doEndTag() == 5) {
/* 4262 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1);
/* 4263 */       return true;
/*      */     }
/* 4265 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1);
/* 4266 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4271 */     PageContext pageContext = _jspx_page_context;
/* 4272 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4274 */     ButtonTag _jspx_th_html_005fbutton_005f2 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 4275 */     _jspx_th_html_005fbutton_005f2.setPageContext(_jspx_page_context);
/* 4276 */     _jspx_th_html_005fbutton_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4278 */     _jspx_th_html_005fbutton_005f2.setStyleClass("buttons btn_small");
/*      */     
/* 4280 */     _jspx_th_html_005fbutton_005f2.setProperty("AddButton2");
/*      */     
/* 4282 */     _jspx_th_html_005fbutton_005f2.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.excludeFirstLevelMonitorTypes,document.AMActionForm.toAdd),fnRemoveAllFromCombo(document.AMActionForm.excludeFirstLevelMonitorTypes);");
/*      */     
/* 4284 */     _jspx_th_html_005fbutton_005f2.setValue("&lt;&lt;");
/* 4285 */     int _jspx_eval_html_005fbutton_005f2 = _jspx_th_html_005fbutton_005f2.doStartTag();
/* 4286 */     if (_jspx_th_html_005fbutton_005f2.doEndTag() == 5) {
/* 4287 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f2);
/* 4288 */       return true;
/*      */     }
/* 4290 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f2);
/* 4291 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4296 */     PageContext pageContext = _jspx_page_context;
/* 4297 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4299 */     ButtonTag _jspx_th_html_005fbutton_005f3 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 4300 */     _jspx_th_html_005fbutton_005f3.setPageContext(_jspx_page_context);
/* 4301 */     _jspx_th_html_005fbutton_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4303 */     _jspx_th_html_005fbutton_005f3.setStyleClass("buttons btn_small");
/*      */     
/* 4305 */     _jspx_th_html_005fbutton_005f3.setProperty("AddButton2");
/*      */     
/* 4307 */     _jspx_th_html_005fbutton_005f3.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.excludeFirstLevelMonitorTypes,document.AMActionForm.toAdd),fnRemoveFromRightCombo(document.AMActionForm.excludeFirstLevelMonitorTypes);");
/*      */     
/* 4309 */     _jspx_th_html_005fbutton_005f3.setValue("&nbsp;&lt;&nbsp;");
/* 4310 */     int _jspx_eval_html_005fbutton_005f3 = _jspx_th_html_005fbutton_005f3.doStartTag();
/* 4311 */     if (_jspx_th_html_005fbutton_005f3.doEndTag() == 5) {
/* 4312 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f3);
/* 4313 */       return true;
/*      */     }
/* 4315 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f3);
/* 4316 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4321 */     PageContext pageContext = _jspx_page_context;
/* 4322 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4324 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 4325 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 4326 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4328 */     _jspx_th_html_005fselect_005f1.setProperty("excludeFirstLevelMonitorTypes");
/*      */     
/* 4330 */     _jspx_th_html_005fselect_005f1.setStyle("width:100%");
/*      */     
/* 4332 */     _jspx_th_html_005fselect_005f1.setMultiple("true");
/*      */     
/* 4334 */     _jspx_th_html_005fselect_005f1.setSize("5");
/* 4335 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 4336 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 4337 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 4338 */         out = _jspx_page_context.pushBody();
/* 4339 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 4340 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4343 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4344 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 4345 */           return true;
/* 4346 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4347 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 4348 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4351 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 4352 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4355 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 4356 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f1);
/* 4357 */       return true;
/*      */     }
/* 4359 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f1);
/* 4360 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4365 */     PageContext pageContext = _jspx_page_context;
/* 4366 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4368 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 4369 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 4370 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 4372 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("excludeFirstLevelMonitorTypesOptions");
/*      */     
/* 4374 */     _jspx_th_html_005foptionsCollection_005f1.setLabel("label");
/*      */     
/* 4376 */     _jspx_th_html_005foptionsCollection_005f1.setValue("value");
/* 4377 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 4378 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 4379 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 4380 */       return true;
/*      */     }
/* 4382 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 4383 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4388 */     PageContext pageContext = _jspx_page_context;
/* 4389 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4391 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 4392 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 4393 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4395 */     _jspx_th_html_005fselect_005f2.setProperty("toAddg");
/*      */     
/* 4397 */     _jspx_th_html_005fselect_005f2.setStyle("width:100%");
/*      */     
/* 4399 */     _jspx_th_html_005fselect_005f2.setMultiple("true");
/*      */     
/* 4401 */     _jspx_th_html_005fselect_005f2.setSize("5");
/* 4402 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 4403 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 4404 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 4405 */         out = _jspx_page_context.pushBody();
/* 4406 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 4407 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4410 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4411 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 4412 */           return true;
/* 4413 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4414 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 4415 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4418 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 4419 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4422 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 4423 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f2);
/* 4424 */       return true;
/*      */     }
/* 4426 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f2);
/* 4427 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4432 */     PageContext pageContext = _jspx_page_context;
/* 4433 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4435 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 4436 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 4437 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 4439 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("secondLevelMonitorTypesOptions");
/*      */     
/* 4441 */     _jspx_th_html_005foptionsCollection_005f2.setLabel("label");
/*      */     
/* 4443 */     _jspx_th_html_005foptionsCollection_005f2.setValue("value");
/* 4444 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 4445 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 4446 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 4447 */       return true;
/*      */     }
/* 4449 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 4450 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f4(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4455 */     PageContext pageContext = _jspx_page_context;
/* 4456 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4458 */     ButtonTag _jspx_th_html_005fbutton_005f4 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 4459 */     _jspx_th_html_005fbutton_005f4.setPageContext(_jspx_page_context);
/* 4460 */     _jspx_th_html_005fbutton_005f4.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4462 */     _jspx_th_html_005fbutton_005f4.setStyleClass("buttons btn_small");
/*      */     
/* 4464 */     _jspx_th_html_005fbutton_005f4.setProperty("AddButton2");
/*      */     
/* 4466 */     _jspx_th_html_005fbutton_005f4.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.toAddg,document.AMActionForm.includeSecondLevelMonitorTypes),fnRemoveFromRightCombo(document.AMActionForm.toAddg);");
/*      */     
/* 4468 */     _jspx_th_html_005fbutton_005f4.setValue("&nbsp;&gt;&nbsp;");
/* 4469 */     int _jspx_eval_html_005fbutton_005f4 = _jspx_th_html_005fbutton_005f4.doStartTag();
/* 4470 */     if (_jspx_th_html_005fbutton_005f4.doEndTag() == 5) {
/* 4471 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f4);
/* 4472 */       return true;
/*      */     }
/* 4474 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f4);
/* 4475 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f5(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4480 */     PageContext pageContext = _jspx_page_context;
/* 4481 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4483 */     ButtonTag _jspx_th_html_005fbutton_005f5 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 4484 */     _jspx_th_html_005fbutton_005f5.setPageContext(_jspx_page_context);
/* 4485 */     _jspx_th_html_005fbutton_005f5.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4487 */     _jspx_th_html_005fbutton_005f5.setStyleClass("buttons btn_small");
/*      */     
/* 4489 */     _jspx_th_html_005fbutton_005f5.setProperty("AddButton2");
/*      */     
/* 4491 */     _jspx_th_html_005fbutton_005f5.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.toAddg,document.AMActionForm.includeSecondLevelMonitorTypes),fnRemoveAllFromCombo(document.AMActionForm.toAddg);");
/*      */     
/* 4493 */     _jspx_th_html_005fbutton_005f5.setValue("&gt;&gt;");
/* 4494 */     int _jspx_eval_html_005fbutton_005f5 = _jspx_th_html_005fbutton_005f5.doStartTag();
/* 4495 */     if (_jspx_th_html_005fbutton_005f5.doEndTag() == 5) {
/* 4496 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f5);
/* 4497 */       return true;
/*      */     }
/* 4499 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f5);
/* 4500 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f6(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4505 */     PageContext pageContext = _jspx_page_context;
/* 4506 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4508 */     ButtonTag _jspx_th_html_005fbutton_005f6 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 4509 */     _jspx_th_html_005fbutton_005f6.setPageContext(_jspx_page_context);
/* 4510 */     _jspx_th_html_005fbutton_005f6.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4512 */     _jspx_th_html_005fbutton_005f6.setStyleClass("buttons btn_small");
/*      */     
/* 4514 */     _jspx_th_html_005fbutton_005f6.setProperty("AddButton2");
/*      */     
/* 4516 */     _jspx_th_html_005fbutton_005f6.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.includeSecondLevelMonitorTypes,document.AMActionForm.toAddg),fnRemoveAllFromCombo(document.AMActionForm.includeSecondLevelMonitorTypes);");
/*      */     
/* 4518 */     _jspx_th_html_005fbutton_005f6.setValue("&lt;&lt;");
/* 4519 */     int _jspx_eval_html_005fbutton_005f6 = _jspx_th_html_005fbutton_005f6.doStartTag();
/* 4520 */     if (_jspx_th_html_005fbutton_005f6.doEndTag() == 5) {
/* 4521 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f6);
/* 4522 */       return true;
/*      */     }
/* 4524 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f6);
/* 4525 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f7(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4530 */     PageContext pageContext = _jspx_page_context;
/* 4531 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4533 */     ButtonTag _jspx_th_html_005fbutton_005f7 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 4534 */     _jspx_th_html_005fbutton_005f7.setPageContext(_jspx_page_context);
/* 4535 */     _jspx_th_html_005fbutton_005f7.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4537 */     _jspx_th_html_005fbutton_005f7.setStyleClass("buttons btn_small");
/*      */     
/* 4539 */     _jspx_th_html_005fbutton_005f7.setProperty("AddButton2");
/*      */     
/* 4541 */     _jspx_th_html_005fbutton_005f7.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.includeSecondLevelMonitorTypes,document.AMActionForm.toAddg),fnRemoveFromRightCombo(document.AMActionForm.includeSecondLevelMonitorTypes);");
/*      */     
/* 4543 */     _jspx_th_html_005fbutton_005f7.setValue("&nbsp;&lt;&nbsp;");
/* 4544 */     int _jspx_eval_html_005fbutton_005f7 = _jspx_th_html_005fbutton_005f7.doStartTag();
/* 4545 */     if (_jspx_th_html_005fbutton_005f7.doEndTag() == 5) {
/* 4546 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f7);
/* 4547 */       return true;
/*      */     }
/* 4549 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f7);
/* 4550 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4555 */     PageContext pageContext = _jspx_page_context;
/* 4556 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4558 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 4559 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 4560 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4562 */     _jspx_th_html_005fselect_005f3.setProperty("includeSecondLevelMonitorTypes");
/*      */     
/* 4564 */     _jspx_th_html_005fselect_005f3.setStyle("width:100%");
/*      */     
/* 4566 */     _jspx_th_html_005fselect_005f3.setMultiple("true");
/*      */     
/* 4568 */     _jspx_th_html_005fselect_005f3.setSize("5");
/* 4569 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 4570 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 4571 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 4572 */         out = _jspx_page_context.pushBody();
/* 4573 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 4574 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4577 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4578 */         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 4579 */           return true;
/* 4580 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4581 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 4582 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4585 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 4586 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4589 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 4590 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f3);
/* 4591 */       return true;
/*      */     }
/* 4593 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f3);
/* 4594 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4599 */     PageContext pageContext = _jspx_page_context;
/* 4600 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4602 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 4603 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 4604 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 4606 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("includeSecondLevelMonitorTypesOptions");
/*      */     
/* 4608 */     _jspx_th_html_005foptionsCollection_005f3.setLabel("label");
/*      */     
/* 4610 */     _jspx_th_html_005foptionsCollection_005f3.setValue("value");
/* 4611 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 4612 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 4613 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 4614 */       return true;
/*      */     }
/* 4616 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 4617 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f23(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4622 */     PageContext pageContext = _jspx_page_context;
/* 4623 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4625 */     RadioTag _jspx_th_html_005fradio_005f23 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4626 */     _jspx_th_html_005fradio_005f23.setPageContext(_jspx_page_context);
/* 4627 */     _jspx_th_html_005fradio_005f23.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4629 */     _jspx_th_html_005fradio_005f23.setProperty("ciAttributesToBeSynced");
/*      */     
/* 4631 */     _jspx_th_html_005fradio_005f23.setValue("true");
/* 4632 */     int _jspx_eval_html_005fradio_005f23 = _jspx_th_html_005fradio_005f23.doStartTag();
/* 4633 */     if (_jspx_th_html_005fradio_005f23.doEndTag() == 5) {
/* 4634 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f23);
/* 4635 */       return true;
/*      */     }
/* 4637 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f23);
/* 4638 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f24(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4643 */     PageContext pageContext = _jspx_page_context;
/* 4644 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4646 */     RadioTag _jspx_th_html_005fradio_005f24 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4647 */     _jspx_th_html_005fradio_005f24.setPageContext(_jspx_page_context);
/* 4648 */     _jspx_th_html_005fradio_005f24.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4650 */     _jspx_th_html_005fradio_005f24.setProperty("ciAttributesToBeSynced");
/*      */     
/* 4652 */     _jspx_th_html_005fradio_005f24.setValue("false");
/* 4653 */     int _jspx_eval_html_005fradio_005f24 = _jspx_th_html_005fradio_005f24.doStartTag();
/* 4654 */     if (_jspx_th_html_005fradio_005f24.doEndTag() == 5) {
/* 4655 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f24);
/* 4656 */       return true;
/*      */     }
/* 4658 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f24);
/* 4659 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f25(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4664 */     PageContext pageContext = _jspx_page_context;
/* 4665 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4667 */     RadioTag _jspx_th_html_005fradio_005f25 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4668 */     _jspx_th_html_005fradio_005f25.setPageContext(_jspx_page_context);
/* 4669 */     _jspx_th_html_005fradio_005f25.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4671 */     _jspx_th_html_005fradio_005f25.setProperty("ciLinksToBeShown");
/*      */     
/* 4673 */     _jspx_th_html_005fradio_005f25.setValue("true");
/* 4674 */     int _jspx_eval_html_005fradio_005f25 = _jspx_th_html_005fradio_005f25.doStartTag();
/* 4675 */     if (_jspx_th_html_005fradio_005f25.doEndTag() == 5) {
/* 4676 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f25);
/* 4677 */       return true;
/*      */     }
/* 4679 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f25);
/* 4680 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f26(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4685 */     PageContext pageContext = _jspx_page_context;
/* 4686 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4688 */     RadioTag _jspx_th_html_005fradio_005f26 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4689 */     _jspx_th_html_005fradio_005f26.setPageContext(_jspx_page_context);
/* 4690 */     _jspx_th_html_005fradio_005f26.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4692 */     _jspx_th_html_005fradio_005f26.setProperty("ciLinksToBeShown");
/*      */     
/* 4694 */     _jspx_th_html_005fradio_005f26.setValue("false");
/* 4695 */     int _jspx_eval_html_005fradio_005f26 = _jspx_th_html_005fradio_005f26.doStartTag();
/* 4696 */     if (_jspx_th_html_005fradio_005f26.doEndTag() == 5) {
/* 4697 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f26);
/* 4698 */       return true;
/*      */     }
/* 4700 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f26);
/* 4701 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f27(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4706 */     PageContext pageContext = _jspx_page_context;
/* 4707 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4709 */     RadioTag _jspx_th_html_005fradio_005f27 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4710 */     _jspx_th_html_005fradio_005f27.setPageContext(_jspx_page_context);
/* 4711 */     _jspx_th_html_005fradio_005f27.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4713 */     _jspx_th_html_005fradio_005f27.setProperty("ciRlMapalongWithListView");
/*      */     
/* 4715 */     _jspx_th_html_005fradio_005f27.setValue("true");
/* 4716 */     int _jspx_eval_html_005fradio_005f27 = _jspx_th_html_005fradio_005f27.doStartTag();
/* 4717 */     if (_jspx_th_html_005fradio_005f27.doEndTag() == 5) {
/* 4718 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f27);
/* 4719 */       return true;
/*      */     }
/* 4721 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f27);
/* 4722 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f28(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4727 */     PageContext pageContext = _jspx_page_context;
/* 4728 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4730 */     RadioTag _jspx_th_html_005fradio_005f28 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4731 */     _jspx_th_html_005fradio_005f28.setPageContext(_jspx_page_context);
/* 4732 */     _jspx_th_html_005fradio_005f28.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4734 */     _jspx_th_html_005fradio_005f28.setProperty("ciRlMapalongWithListView");
/*      */     
/* 4736 */     _jspx_th_html_005fradio_005f28.setValue("false");
/* 4737 */     int _jspx_eval_html_005fradio_005f28 = _jspx_th_html_005fradio_005f28.doStartTag();
/* 4738 */     if (_jspx_th_html_005fradio_005f28.doEndTag() == 5) {
/* 4739 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f28);
/* 4740 */       return true;
/*      */     }
/* 4742 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f28);
/* 4743 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4748 */     PageContext pageContext = _jspx_page_context;
/* 4749 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4751 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4752 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4753 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 4755 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.servicenow.details.modifyPassword");
/* 4756 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4757 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4758 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4759 */       return true;
/*      */     }
/* 4761 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4762 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4767 */     PageContext pageContext = _jspx_page_context;
/* 4768 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4770 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4771 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 4772 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 4774 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.servicenow.details.cancel");
/* 4775 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 4776 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 4777 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4778 */       return true;
/*      */     }
/* 4780 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4781 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4786 */     PageContext pageContext = _jspx_page_context;
/* 4787 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4789 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4790 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 4791 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4793 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 4795 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 4796 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 4797 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4798 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 4799 */       return true;
/*      */     }
/* 4801 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 4802 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f29(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4807 */     PageContext pageContext = _jspx_page_context;
/* 4808 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4810 */     RadioTag _jspx_th_html_005fradio_005f29 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 4811 */     _jspx_th_html_005fradio_005f29.setPageContext(_jspx_page_context);
/* 4812 */     _jspx_th_html_005fradio_005f29.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4814 */     _jspx_th_html_005fradio_005f29.setProperty("helpDeskProduct");
/*      */     
/* 4816 */     _jspx_th_html_005fradio_005f29.setValue("SERVICEDESK");
/*      */     
/* 4818 */     _jspx_th_html_005fradio_005f29.setOnclick("javascript:changeProduct('ServiceDesk')");
/* 4819 */     int _jspx_eval_html_005fradio_005f29 = _jspx_th_html_005fradio_005f29.doStartTag();
/* 4820 */     if (_jspx_th_html_005fradio_005f29.doEndTag() == 5) {
/* 4821 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f29);
/* 4822 */       return true;
/*      */     }
/* 4824 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f29);
/* 4825 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f30(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4830 */     PageContext pageContext = _jspx_page_context;
/* 4831 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4833 */     RadioTag _jspx_th_html_005fradio_005f30 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 4834 */     _jspx_th_html_005fradio_005f30.setPageContext(_jspx_page_context);
/* 4835 */     _jspx_th_html_005fradio_005f30.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4837 */     _jspx_th_html_005fradio_005f30.setProperty("helpDeskProduct");
/*      */     
/* 4839 */     _jspx_th_html_005fradio_005f30.setValue("SERVICENOW");
/*      */     
/* 4841 */     _jspx_th_html_005fradio_005f30.setOnclick("javascript:changeProduct('ServiceNow')");
/* 4842 */     int _jspx_eval_html_005fradio_005f30 = _jspx_th_html_005fradio_005f30.doStartTag();
/* 4843 */     if (_jspx_th_html_005fradio_005f30.doEndTag() == 5) {
/* 4844 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f30);
/* 4845 */       return true;
/*      */     }
/* 4847 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f30);
/* 4848 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4853 */     PageContext pageContext = _jspx_page_context;
/* 4854 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4856 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4857 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 4858 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4860 */     _jspx_th_html_005ftext_005f3.setProperty("servicenowInstance");
/*      */     
/* 4862 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext default");
/*      */     
/* 4864 */     _jspx_th_html_005ftext_005f3.setSize("65");
/* 4865 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 4866 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 4867 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4868 */       return true;
/*      */     }
/* 4870 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4871 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4876 */     PageContext pageContext = _jspx_page_context;
/* 4877 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4879 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4880 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 4881 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4883 */     _jspx_th_html_005ftext_005f4.setProperty("servicenowUserName");
/*      */     
/* 4885 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext default");
/*      */     
/* 4887 */     _jspx_th_html_005ftext_005f4.setSize("30");
/* 4888 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 4889 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 4890 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 4891 */       return true;
/*      */     }
/* 4893 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 4894 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4899 */     PageContext pageContext = _jspx_page_context;
/* 4900 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4902 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4903 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 4904 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_html_005fform_005f1);
/* 4905 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 4906 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 4908 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 4909 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 4910 */           return true;
/* 4911 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 4912 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 4913 */           return true;
/* 4914 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 4915 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 4916 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4920 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 4921 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 4922 */       return true;
/*      */     }
/* 4924 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 4925 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4930 */     PageContext pageContext = _jspx_page_context;
/* 4931 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4933 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4934 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 4935 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 4937 */     _jspx_th_c_005fwhen_005f2.setTest("${editServiceNow eq \"false\"}");
/* 4938 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 4939 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 4941 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 4942 */         if (_jspx_meth_html_005fpassword_005f1(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/* 4943 */           return true;
/* 4944 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 4945 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 4946 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4950 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 4951 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 4952 */       return true;
/*      */     }
/* 4954 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 4955 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f1(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4960 */     PageContext pageContext = _jspx_page_context;
/* 4961 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4963 */     PasswordTag _jspx_th_html_005fpassword_005f1 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 4964 */     _jspx_th_html_005fpassword_005f1.setPageContext(_jspx_page_context);
/* 4965 */     _jspx_th_html_005fpassword_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 4967 */     _jspx_th_html_005fpassword_005f1.setProperty("servicenowPassword");
/*      */     
/* 4969 */     _jspx_th_html_005fpassword_005f1.setStyleClass("formtext default");
/*      */     
/* 4971 */     _jspx_th_html_005fpassword_005f1.setSize("30");
/* 4972 */     int _jspx_eval_html_005fpassword_005f1 = _jspx_th_html_005fpassword_005f1.doStartTag();
/* 4973 */     if (_jspx_th_html_005fpassword_005f1.doEndTag() == 5) {
/* 4974 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
/* 4975 */       return true;
/*      */     }
/* 4977 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
/* 4978 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4983 */     PageContext pageContext = _jspx_page_context;
/* 4984 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4986 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4987 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 4988 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 4989 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 4990 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 4992 */         out.write("\n\t\t\t\t\t\t\t\t<span id=\"tdSpan_password\"></span>\n\t\t\t\t\t\t\t\t <span id=\"modifySpan_password\">\n\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" style=\"color:blue;text-decoration:underline;\" onclick=\"modifyPassword()\">");
/* 4993 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/* 4994 */           return true;
/* 4995 */         out.write("</a>\n\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t");
/* 4996 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 4997 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5001 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 5002 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 5003 */       return true;
/*      */     }
/* 5005 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 5006 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5011 */     PageContext pageContext = _jspx_page_context;
/* 5012 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5014 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5015 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 5016 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 5018 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.servicenow.details.modifyPassword");
/* 5019 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 5020 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 5021 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5022 */       return true;
/*      */     }
/* 5024 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5025 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f2(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5030 */     PageContext pageContext = _jspx_page_context;
/* 5031 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5033 */     CheckboxTag _jspx_th_html_005fcheckbox_005f2 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.get(CheckboxTag.class);
/* 5034 */     _jspx_th_html_005fcheckbox_005f2.setPageContext(_jspx_page_context);
/* 5035 */     _jspx_th_html_005fcheckbox_005f2.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5037 */     _jspx_th_html_005fcheckbox_005f2.setProperty("ticketingEnabled");
/*      */     
/* 5039 */     _jspx_th_html_005fcheckbox_005f2.setStyleId("sub_settings2");
/*      */     
/* 5041 */     _jspx_th_html_005fcheckbox_005f2.setOnchange("javascript:showAdvnSettings2()");
/*      */     
/* 5043 */     _jspx_th_html_005fcheckbox_005f2.setValue("true");
/* 5044 */     int _jspx_eval_html_005fcheckbox_005f2 = _jspx_th_html_005fcheckbox_005f2.doStartTag();
/* 5045 */     if (_jspx_th_html_005fcheckbox_005f2.doEndTag() == 5) {
/* 5046 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 5047 */       return true;
/*      */     }
/* 5049 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 5050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f31(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5055 */     PageContext pageContext = _jspx_page_context;
/* 5056 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5058 */     RadioTag _jspx_th_html_005fradio_005f31 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5059 */     _jspx_th_html_005fradio_005f31.setPageContext(_jspx_page_context);
/* 5060 */     _jspx_th_html_005fradio_005f31.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5062 */     _jspx_th_html_005fradio_005f31.setProperty("reOpenTicketPolicy");
/*      */     
/* 5064 */     _jspx_th_html_005fradio_005f31.setValue("REOPEN_TICKET");
/* 5065 */     int _jspx_eval_html_005fradio_005f31 = _jspx_th_html_005fradio_005f31.doStartTag();
/* 5066 */     if (_jspx_th_html_005fradio_005f31.doEndTag() == 5) {
/* 5067 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f31);
/* 5068 */       return true;
/*      */     }
/* 5070 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f31);
/* 5071 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f32(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5076 */     PageContext pageContext = _jspx_page_context;
/* 5077 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5079 */     RadioTag _jspx_th_html_005fradio_005f32 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5080 */     _jspx_th_html_005fradio_005f32.setPageContext(_jspx_page_context);
/* 5081 */     _jspx_th_html_005fradio_005f32.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5083 */     _jspx_th_html_005fradio_005f32.setProperty("reOpenTicketPolicy");
/*      */     
/* 5085 */     _jspx_th_html_005fradio_005f32.setValue("REOPEN_TICKET_CUSTOM_PERIOD");
/* 5086 */     int _jspx_eval_html_005fradio_005f32 = _jspx_th_html_005fradio_005f32.doStartTag();
/* 5087 */     if (_jspx_th_html_005fradio_005f32.doEndTag() == 5) {
/* 5088 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f32);
/* 5089 */       return true;
/*      */     }
/* 5091 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f32);
/* 5092 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5097 */     PageContext pageContext = _jspx_page_context;
/* 5098 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5100 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5101 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 5102 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5104 */     _jspx_th_html_005ftext_005f5.setProperty("reOpenPeriod");
/*      */     
/* 5106 */     _jspx_th_html_005ftext_005f5.setSize("3");
/*      */     
/* 5108 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext");
/* 5109 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 5110 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 5111 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 5112 */       return true;
/*      */     }
/* 5114 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 5115 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f33(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5120 */     PageContext pageContext = _jspx_page_context;
/* 5121 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5123 */     RadioTag _jspx_th_html_005fradio_005f33 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5124 */     _jspx_th_html_005fradio_005f33.setPageContext(_jspx_page_context);
/* 5125 */     _jspx_th_html_005fradio_005f33.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5127 */     _jspx_th_html_005fradio_005f33.setProperty("reOpenTicketPolicy");
/*      */     
/* 5129 */     _jspx_th_html_005fradio_005f33.setValue("NEW_TICKET");
/* 5130 */     int _jspx_eval_html_005fradio_005f33 = _jspx_th_html_005fradio_005f33.doStartTag();
/* 5131 */     if (_jspx_th_html_005fradio_005f33.doEndTag() == 5) {
/* 5132 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f33);
/* 5133 */       return true;
/*      */     }
/* 5135 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f33);
/* 5136 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f34(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5141 */     PageContext pageContext = _jspx_page_context;
/* 5142 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5144 */     RadioTag _jspx_th_html_005fradio_005f34 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5145 */     _jspx_th_html_005fradio_005f34.setPageContext(_jspx_page_context);
/* 5146 */     _jspx_th_html_005fradio_005f34.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5148 */     _jspx_th_html_005fradio_005f34.setProperty("closeTicketPolicy");
/*      */     
/* 5150 */     _jspx_th_html_005fradio_005f34.setValue("CLOSE_TICKET_UPDATE_NOTES");
/* 5151 */     int _jspx_eval_html_005fradio_005f34 = _jspx_th_html_005fradio_005f34.doStartTag();
/* 5152 */     if (_jspx_th_html_005fradio_005f34.doEndTag() == 5) {
/* 5153 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f34);
/* 5154 */       return true;
/*      */     }
/* 5156 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f34);
/* 5157 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f35(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5162 */     PageContext pageContext = _jspx_page_context;
/* 5163 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5165 */     RadioTag _jspx_th_html_005fradio_005f35 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5166 */     _jspx_th_html_005fradio_005f35.setPageContext(_jspx_page_context);
/* 5167 */     _jspx_th_html_005fradio_005f35.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5169 */     _jspx_th_html_005fradio_005f35.setProperty("closeTicketPolicy");
/*      */     
/* 5171 */     _jspx_th_html_005fradio_005f35.setValue("UPDATE_NOTES");
/* 5172 */     int _jspx_eval_html_005fradio_005f35 = _jspx_th_html_005fradio_005f35.doStartTag();
/* 5173 */     if (_jspx_th_html_005fradio_005f35.doEndTag() == 5) {
/* 5174 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f35);
/* 5175 */       return true;
/*      */     }
/* 5177 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f35);
/* 5178 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f36(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5183 */     PageContext pageContext = _jspx_page_context;
/* 5184 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5186 */     RadioTag _jspx_th_html_005fradio_005f36 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5187 */     _jspx_th_html_005fradio_005f36.setPageContext(_jspx_page_context);
/* 5188 */     _jspx_th_html_005fradio_005f36.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5190 */     _jspx_th_html_005fradio_005f36.setProperty("notesToBeAddedForTicket");
/*      */     
/* 5192 */     _jspx_th_html_005fradio_005f36.setValue("true");
/* 5193 */     int _jspx_eval_html_005fradio_005f36 = _jspx_th_html_005fradio_005f36.doStartTag();
/* 5194 */     if (_jspx_th_html_005fradio_005f36.doEndTag() == 5) {
/* 5195 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f36);
/* 5196 */       return true;
/*      */     }
/* 5198 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f36);
/* 5199 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f37(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5204 */     PageContext pageContext = _jspx_page_context;
/* 5205 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5207 */     RadioTag _jspx_th_html_005fradio_005f37 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5208 */     _jspx_th_html_005fradio_005f37.setPageContext(_jspx_page_context);
/* 5209 */     _jspx_th_html_005fradio_005f37.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5211 */     _jspx_th_html_005fradio_005f37.setProperty("notesToBeAddedForTicket");
/*      */     
/* 5213 */     _jspx_th_html_005fradio_005f37.setValue("false");
/* 5214 */     int _jspx_eval_html_005fradio_005f37 = _jspx_th_html_005fradio_005f37.doStartTag();
/* 5215 */     if (_jspx_th_html_005fradio_005f37.doEndTag() == 5) {
/* 5216 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f37);
/* 5217 */       return true;
/*      */     }
/* 5219 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f37);
/* 5220 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f38(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5225 */     PageContext pageContext = _jspx_page_context;
/* 5226 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5228 */     RadioTag _jspx_th_html_005fradio_005f38 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5229 */     _jspx_th_html_005fradio_005f38.setPageContext(_jspx_page_context);
/* 5230 */     _jspx_th_html_005fradio_005f38.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5232 */     _jspx_th_html_005fradio_005f38.setProperty("dynamicTicketingUsingAction");
/*      */     
/* 5234 */     _jspx_th_html_005fradio_005f38.setValue("true");
/* 5235 */     int _jspx_eval_html_005fradio_005f38 = _jspx_th_html_005fradio_005f38.doStartTag();
/* 5236 */     if (_jspx_th_html_005fradio_005f38.doEndTag() == 5) {
/* 5237 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f38);
/* 5238 */       return true;
/*      */     }
/* 5240 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f38);
/* 5241 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f39(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5246 */     PageContext pageContext = _jspx_page_context;
/* 5247 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5249 */     RadioTag _jspx_th_html_005fradio_005f39 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5250 */     _jspx_th_html_005fradio_005f39.setPageContext(_jspx_page_context);
/* 5251 */     _jspx_th_html_005fradio_005f39.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5253 */     _jspx_th_html_005fradio_005f39.setProperty("dynamicTicketingUsingAction");
/*      */     
/* 5255 */     _jspx_th_html_005fradio_005f39.setValue("false");
/* 5256 */     int _jspx_eval_html_005fradio_005f39 = _jspx_th_html_005fradio_005f39.doStartTag();
/* 5257 */     if (_jspx_th_html_005fradio_005f39.doEndTag() == 5) {
/* 5258 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f39);
/* 5259 */       return true;
/*      */     }
/* 5261 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f39);
/* 5262 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f40(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5267 */     PageContext pageContext = _jspx_page_context;
/* 5268 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5270 */     RadioTag _jspx_th_html_005fradio_005f40 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5271 */     _jspx_th_html_005fradio_005f40.setPageContext(_jspx_page_context);
/* 5272 */     _jspx_th_html_005fradio_005f40.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5274 */     _jspx_th_html_005fradio_005f40.setProperty("dynamicTicketingUsingForm");
/*      */     
/* 5276 */     _jspx_th_html_005fradio_005f40.setValue("true");
/* 5277 */     int _jspx_eval_html_005fradio_005f40 = _jspx_th_html_005fradio_005f40.doStartTag();
/* 5278 */     if (_jspx_th_html_005fradio_005f40.doEndTag() == 5) {
/* 5279 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f40);
/* 5280 */       return true;
/*      */     }
/* 5282 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f40);
/* 5283 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f41(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5288 */     PageContext pageContext = _jspx_page_context;
/* 5289 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5291 */     RadioTag _jspx_th_html_005fradio_005f41 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5292 */     _jspx_th_html_005fradio_005f41.setPageContext(_jspx_page_context);
/* 5293 */     _jspx_th_html_005fradio_005f41.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5295 */     _jspx_th_html_005fradio_005f41.setProperty("dynamicTicketingUsingForm");
/*      */     
/* 5297 */     _jspx_th_html_005fradio_005f41.setValue("false");
/* 5298 */     int _jspx_eval_html_005fradio_005f41 = _jspx_th_html_005fradio_005f41.doStartTag();
/* 5299 */     if (_jspx_th_html_005fradio_005f41.doEndTag() == 5) {
/* 5300 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f41);
/* 5301 */       return true;
/*      */     }
/* 5303 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f41);
/* 5304 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f42(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5309 */     PageContext pageContext = _jspx_page_context;
/* 5310 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5312 */     RadioTag _jspx_th_html_005fradio_005f42 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5313 */     _jspx_th_html_005fradio_005f42.setPageContext(_jspx_page_context);
/* 5314 */     _jspx_th_html_005fradio_005f42.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5316 */     _jspx_th_html_005fradio_005f42.setProperty("ticketLinkToBeShown");
/*      */     
/* 5318 */     _jspx_th_html_005fradio_005f42.setValue("true");
/* 5319 */     int _jspx_eval_html_005fradio_005f42 = _jspx_th_html_005fradio_005f42.doStartTag();
/* 5320 */     if (_jspx_th_html_005fradio_005f42.doEndTag() == 5) {
/* 5321 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f42);
/* 5322 */       return true;
/*      */     }
/* 5324 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f42);
/* 5325 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f43(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5330 */     PageContext pageContext = _jspx_page_context;
/* 5331 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5333 */     RadioTag _jspx_th_html_005fradio_005f43 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5334 */     _jspx_th_html_005fradio_005f43.setPageContext(_jspx_page_context);
/* 5335 */     _jspx_th_html_005fradio_005f43.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5337 */     _jspx_th_html_005fradio_005f43.setProperty("ticketLinkToBeShown");
/*      */     
/* 5339 */     _jspx_th_html_005fradio_005f43.setValue("false");
/* 5340 */     int _jspx_eval_html_005fradio_005f43 = _jspx_th_html_005fradio_005f43.doStartTag();
/* 5341 */     if (_jspx_th_html_005fradio_005f43.doEndTag() == 5) {
/* 5342 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f43);
/* 5343 */       return true;
/*      */     }
/* 5345 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f43);
/* 5346 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f44(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5351 */     PageContext pageContext = _jspx_page_context;
/* 5352 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5354 */     RadioTag _jspx_th_html_005fradio_005f44 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5355 */     _jspx_th_html_005fradio_005f44.setPageContext(_jspx_page_context);
/* 5356 */     _jspx_th_html_005fradio_005f44.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 5358 */     _jspx_th_html_005fradio_005f44.setProperty("readonlyTicket");
/*      */     
/* 5360 */     _jspx_th_html_005fradio_005f44.setValue("true");
/* 5361 */     int _jspx_eval_html_005fradio_005f44 = _jspx_th_html_005fradio_005f44.doStartTag();
/* 5362 */     if (_jspx_th_html_005fradio_005f44.doEndTag() == 5) {
/* 5363 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f44);
/* 5364 */       return true;
/*      */     }
/* 5366 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f44);
/* 5367 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f45(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5372 */     PageContext pageContext = _jspx_page_context;
/* 5373 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5375 */     RadioTag _jspx_th_html_005fradio_005f45 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5376 */     _jspx_th_html_005fradio_005f45.setPageContext(_jspx_page_context);
/* 5377 */     _jspx_th_html_005fradio_005f45.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 5379 */     _jspx_th_html_005fradio_005f45.setProperty("readonlyTicket");
/*      */     
/* 5381 */     _jspx_th_html_005fradio_005f45.setValue("false");
/* 5382 */     int _jspx_eval_html_005fradio_005f45 = _jspx_th_html_005fradio_005f45.doStartTag();
/* 5383 */     if (_jspx_th_html_005fradio_005f45.doEndTag() == 5) {
/* 5384 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f45);
/* 5385 */       return true;
/*      */     }
/* 5387 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f45);
/* 5388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f46(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5393 */     PageContext pageContext = _jspx_page_context;
/* 5394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5396 */     RadioTag _jspx_th_html_005fradio_005f46 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5397 */     _jspx_th_html_005fradio_005f46.setPageContext(_jspx_page_context);
/* 5398 */     _jspx_th_html_005fradio_005f46.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 5400 */     _jspx_th_html_005fradio_005f46.setProperty("updateTicketForchangeInStatusAlone");
/*      */     
/* 5402 */     _jspx_th_html_005fradio_005f46.setValue("true");
/* 5403 */     int _jspx_eval_html_005fradio_005f46 = _jspx_th_html_005fradio_005f46.doStartTag();
/* 5404 */     if (_jspx_th_html_005fradio_005f46.doEndTag() == 5) {
/* 5405 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f46);
/* 5406 */       return true;
/*      */     }
/* 5408 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f46);
/* 5409 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f47(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5414 */     PageContext pageContext = _jspx_page_context;
/* 5415 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5417 */     RadioTag _jspx_th_html_005fradio_005f47 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5418 */     _jspx_th_html_005fradio_005f47.setPageContext(_jspx_page_context);
/* 5419 */     _jspx_th_html_005fradio_005f47.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 5421 */     _jspx_th_html_005fradio_005f47.setProperty("updateTicketForchangeInStatusAlone");
/*      */     
/* 5423 */     _jspx_th_html_005fradio_005f47.setValue("false");
/* 5424 */     int _jspx_eval_html_005fradio_005f47 = _jspx_th_html_005fradio_005f47.doStartTag();
/* 5425 */     if (_jspx_th_html_005fradio_005f47.doEndTag() == 5) {
/* 5426 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f47);
/* 5427 */       return true;
/*      */     }
/* 5429 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f47);
/* 5430 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f48(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5435 */     PageContext pageContext = _jspx_page_context;
/* 5436 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5438 */     RadioTag _jspx_th_html_005fradio_005f48 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5439 */     _jspx_th_html_005fradio_005f48.setPageContext(_jspx_page_context);
/* 5440 */     _jspx_th_html_005fradio_005f48.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 5442 */     _jspx_th_html_005fradio_005f48.setProperty("allowOverWriteOfReqTemplate");
/*      */     
/* 5444 */     _jspx_th_html_005fradio_005f48.setValue("true");
/* 5445 */     int _jspx_eval_html_005fradio_005f48 = _jspx_th_html_005fradio_005f48.doStartTag();
/* 5446 */     if (_jspx_th_html_005fradio_005f48.doEndTag() == 5) {
/* 5447 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f48);
/* 5448 */       return true;
/*      */     }
/* 5450 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f48);
/* 5451 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f49(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5456 */     PageContext pageContext = _jspx_page_context;
/* 5457 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5459 */     RadioTag _jspx_th_html_005fradio_005f49 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5460 */     _jspx_th_html_005fradio_005f49.setPageContext(_jspx_page_context);
/* 5461 */     _jspx_th_html_005fradio_005f49.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 5463 */     _jspx_th_html_005fradio_005f49.setProperty("allowOverWriteOfReqTemplate");
/*      */     
/* 5465 */     _jspx_th_html_005fradio_005f49.setValue("false");
/* 5466 */     int _jspx_eval_html_005fradio_005f49 = _jspx_th_html_005fradio_005f49.doStartTag();
/* 5467 */     if (_jspx_th_html_005fradio_005f49.doEndTag() == 5) {
/* 5468 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f49);
/* 5469 */       return true;
/*      */     }
/* 5471 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f49);
/* 5472 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f3(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5477 */     PageContext pageContext = _jspx_page_context;
/* 5478 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5480 */     CheckboxTag _jspx_th_html_005fcheckbox_005f3 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.get(CheckboxTag.class);
/* 5481 */     _jspx_th_html_005fcheckbox_005f3.setPageContext(_jspx_page_context);
/* 5482 */     _jspx_th_html_005fcheckbox_005f3.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5484 */     _jspx_th_html_005fcheckbox_005f3.setProperty("cISyncEnabled");
/*      */     
/* 5486 */     _jspx_th_html_005fcheckbox_005f3.setStyleId("sub_settings1");
/*      */     
/* 5488 */     _jspx_th_html_005fcheckbox_005f3.setOnchange("javascript:showAdvnSettings1()");
/*      */     
/* 5490 */     _jspx_th_html_005fcheckbox_005f3.setValue("true");
/* 5491 */     int _jspx_eval_html_005fcheckbox_005f3 = _jspx_th_html_005fcheckbox_005f3.doStartTag();
/* 5492 */     if (_jspx_th_html_005fcheckbox_005f3.doEndTag() == 5) {
/* 5493 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 5494 */       return true;
/*      */     }
/* 5496 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyleId_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 5497 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f50(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5502 */     PageContext pageContext = _jspx_page_context;
/* 5503 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5505 */     RadioTag _jspx_th_html_005fradio_005f50 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5506 */     _jspx_th_html_005fradio_005f50.setPageContext(_jspx_page_context);
/* 5507 */     _jspx_th_html_005fradio_005f50.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5509 */     _jspx_th_html_005fradio_005f50.setProperty("ciToBeDeleted");
/*      */     
/* 5511 */     _jspx_th_html_005fradio_005f50.setValue("true");
/* 5512 */     int _jspx_eval_html_005fradio_005f50 = _jspx_th_html_005fradio_005f50.doStartTag();
/* 5513 */     if (_jspx_th_html_005fradio_005f50.doEndTag() == 5) {
/* 5514 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f50);
/* 5515 */       return true;
/*      */     }
/* 5517 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f50);
/* 5518 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f51(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5523 */     PageContext pageContext = _jspx_page_context;
/* 5524 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5526 */     RadioTag _jspx_th_html_005fradio_005f51 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5527 */     _jspx_th_html_005fradio_005f51.setPageContext(_jspx_page_context);
/* 5528 */     _jspx_th_html_005fradio_005f51.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5530 */     _jspx_th_html_005fradio_005f51.setProperty("ciToBeDeleted");
/*      */     
/* 5532 */     _jspx_th_html_005fradio_005f51.setValue("false");
/* 5533 */     int _jspx_eval_html_005fradio_005f51 = _jspx_th_html_005fradio_005f51.doStartTag();
/* 5534 */     if (_jspx_th_html_005fradio_005f51.doEndTag() == 5) {
/* 5535 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f51);
/* 5536 */       return true;
/*      */     }
/* 5538 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f51);
/* 5539 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f4(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5544 */     PageContext pageContext = _jspx_page_context;
/* 5545 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5547 */     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 5548 */     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 5549 */     _jspx_th_html_005fselect_005f4.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5551 */     _jspx_th_html_005fselect_005f4.setProperty("toAdd");
/*      */     
/* 5553 */     _jspx_th_html_005fselect_005f4.setStyle("width:100%");
/*      */     
/* 5555 */     _jspx_th_html_005fselect_005f4.setMultiple("true");
/*      */     
/* 5557 */     _jspx_th_html_005fselect_005f4.setSize("5");
/* 5558 */     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 5559 */     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 5560 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 5561 */         out = _jspx_page_context.pushBody();
/* 5562 */         _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 5563 */         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5566 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5567 */         if (_jspx_meth_html_005foptionsCollection_005f4(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/* 5568 */           return true;
/* 5569 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5570 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 5571 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5574 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 5575 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5578 */     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 5579 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f4);
/* 5580 */       return true;
/*      */     }
/* 5582 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f4);
/* 5583 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f4(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5588 */     PageContext pageContext = _jspx_page_context;
/* 5589 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5591 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f4 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 5592 */     _jspx_th_html_005foptionsCollection_005f4.setPageContext(_jspx_page_context);
/* 5593 */     _jspx_th_html_005foptionsCollection_005f4.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 5595 */     _jspx_th_html_005foptionsCollection_005f4.setProperty("firstLevelMonitorTypesOptions");
/*      */     
/* 5597 */     _jspx_th_html_005foptionsCollection_005f4.setLabel("label");
/*      */     
/* 5599 */     _jspx_th_html_005foptionsCollection_005f4.setValue("value");
/* 5600 */     int _jspx_eval_html_005foptionsCollection_005f4 = _jspx_th_html_005foptionsCollection_005f4.doStartTag();
/* 5601 */     if (_jspx_th_html_005foptionsCollection_005f4.doEndTag() == 5) {
/* 5602 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 5603 */       return true;
/*      */     }
/* 5605 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 5606 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f8(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5611 */     PageContext pageContext = _jspx_page_context;
/* 5612 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5614 */     ButtonTag _jspx_th_html_005fbutton_005f8 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5615 */     _jspx_th_html_005fbutton_005f8.setPageContext(_jspx_page_context);
/* 5616 */     _jspx_th_html_005fbutton_005f8.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5618 */     _jspx_th_html_005fbutton_005f8.setStyleClass("buttons btn_small");
/*      */     
/* 5620 */     _jspx_th_html_005fbutton_005f8.setProperty("AddButton2");
/*      */     
/* 5622 */     _jspx_th_html_005fbutton_005f8.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.toAdd,document.AMActionForm.excludeFirstLevelMonitorTypes),fnRemoveFromRightCombo(document.AMActionForm.toAdd);");
/*      */     
/* 5624 */     _jspx_th_html_005fbutton_005f8.setValue("&nbsp;&gt;&nbsp;");
/* 5625 */     int _jspx_eval_html_005fbutton_005f8 = _jspx_th_html_005fbutton_005f8.doStartTag();
/* 5626 */     if (_jspx_th_html_005fbutton_005f8.doEndTag() == 5) {
/* 5627 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f8);
/* 5628 */       return true;
/*      */     }
/* 5630 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f8);
/* 5631 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f9(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5636 */     PageContext pageContext = _jspx_page_context;
/* 5637 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5639 */     ButtonTag _jspx_th_html_005fbutton_005f9 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5640 */     _jspx_th_html_005fbutton_005f9.setPageContext(_jspx_page_context);
/* 5641 */     _jspx_th_html_005fbutton_005f9.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5643 */     _jspx_th_html_005fbutton_005f9.setStyleClass("buttons btn_small");
/*      */     
/* 5645 */     _jspx_th_html_005fbutton_005f9.setProperty("AddButton2");
/*      */     
/* 5647 */     _jspx_th_html_005fbutton_005f9.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.toAdd,document.AMActionForm.excludeFirstLevelMonitorTypes),fnRemoveAllFromCombo(document.AMActionForm.toAdd);");
/*      */     
/* 5649 */     _jspx_th_html_005fbutton_005f9.setValue("&gt;&gt;");
/* 5650 */     int _jspx_eval_html_005fbutton_005f9 = _jspx_th_html_005fbutton_005f9.doStartTag();
/* 5651 */     if (_jspx_th_html_005fbutton_005f9.doEndTag() == 5) {
/* 5652 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f9);
/* 5653 */       return true;
/*      */     }
/* 5655 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f9);
/* 5656 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f10(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5661 */     PageContext pageContext = _jspx_page_context;
/* 5662 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5664 */     ButtonTag _jspx_th_html_005fbutton_005f10 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5665 */     _jspx_th_html_005fbutton_005f10.setPageContext(_jspx_page_context);
/* 5666 */     _jspx_th_html_005fbutton_005f10.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5668 */     _jspx_th_html_005fbutton_005f10.setStyleClass("buttons btn_small");
/*      */     
/* 5670 */     _jspx_th_html_005fbutton_005f10.setProperty("AddButton2");
/*      */     
/* 5672 */     _jspx_th_html_005fbutton_005f10.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.excludeFirstLevelMonitorTypes,document.AMActionForm.toAdd),fnRemoveAllFromCombo(document.AMActionForm.excludeFirstLevelMonitorTypes);");
/*      */     
/* 5674 */     _jspx_th_html_005fbutton_005f10.setValue("&lt;&lt;");
/* 5675 */     int _jspx_eval_html_005fbutton_005f10 = _jspx_th_html_005fbutton_005f10.doStartTag();
/* 5676 */     if (_jspx_th_html_005fbutton_005f10.doEndTag() == 5) {
/* 5677 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f10);
/* 5678 */       return true;
/*      */     }
/* 5680 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f10);
/* 5681 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f11(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5686 */     PageContext pageContext = _jspx_page_context;
/* 5687 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5689 */     ButtonTag _jspx_th_html_005fbutton_005f11 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5690 */     _jspx_th_html_005fbutton_005f11.setPageContext(_jspx_page_context);
/* 5691 */     _jspx_th_html_005fbutton_005f11.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5693 */     _jspx_th_html_005fbutton_005f11.setStyleClass("buttons btn_small");
/*      */     
/* 5695 */     _jspx_th_html_005fbutton_005f11.setProperty("AddButton2");
/*      */     
/* 5697 */     _jspx_th_html_005fbutton_005f11.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.excludeFirstLevelMonitorTypes,document.AMActionForm.toAdd),fnRemoveFromRightCombo(document.AMActionForm.excludeFirstLevelMonitorTypes);");
/*      */     
/* 5699 */     _jspx_th_html_005fbutton_005f11.setValue("&nbsp;&lt;&nbsp;");
/* 5700 */     int _jspx_eval_html_005fbutton_005f11 = _jspx_th_html_005fbutton_005f11.doStartTag();
/* 5701 */     if (_jspx_th_html_005fbutton_005f11.doEndTag() == 5) {
/* 5702 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f11);
/* 5703 */       return true;
/*      */     }
/* 5705 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f11);
/* 5706 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f5(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5711 */     PageContext pageContext = _jspx_page_context;
/* 5712 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5714 */     SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 5715 */     _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 5716 */     _jspx_th_html_005fselect_005f5.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5718 */     _jspx_th_html_005fselect_005f5.setProperty("excludeFirstLevelMonitorTypes");
/*      */     
/* 5720 */     _jspx_th_html_005fselect_005f5.setStyle("width:100%");
/*      */     
/* 5722 */     _jspx_th_html_005fselect_005f5.setMultiple("true");
/*      */     
/* 5724 */     _jspx_th_html_005fselect_005f5.setSize("5");
/* 5725 */     int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 5726 */     if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 5727 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 5728 */         out = _jspx_page_context.pushBody();
/* 5729 */         _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 5730 */         _jspx_th_html_005fselect_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5733 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5734 */         if (_jspx_meth_html_005foptionsCollection_005f5(_jspx_th_html_005fselect_005f5, _jspx_page_context))
/* 5735 */           return true;
/* 5736 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5737 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 5738 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5741 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 5742 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5745 */     if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 5746 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f5);
/* 5747 */       return true;
/*      */     }
/* 5749 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f5);
/* 5750 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f5(JspTag _jspx_th_html_005fselect_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5755 */     PageContext pageContext = _jspx_page_context;
/* 5756 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5758 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f5 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 5759 */     _jspx_th_html_005foptionsCollection_005f5.setPageContext(_jspx_page_context);
/* 5760 */     _jspx_th_html_005foptionsCollection_005f5.setParent((Tag)_jspx_th_html_005fselect_005f5);
/*      */     
/* 5762 */     _jspx_th_html_005foptionsCollection_005f5.setProperty("excludeFirstLevelMonitorTypesOptions");
/*      */     
/* 5764 */     _jspx_th_html_005foptionsCollection_005f5.setLabel("label");
/*      */     
/* 5766 */     _jspx_th_html_005foptionsCollection_005f5.setValue("value");
/* 5767 */     int _jspx_eval_html_005foptionsCollection_005f5 = _jspx_th_html_005foptionsCollection_005f5.doStartTag();
/* 5768 */     if (_jspx_th_html_005foptionsCollection_005f5.doEndTag() == 5) {
/* 5769 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 5770 */       return true;
/*      */     }
/* 5772 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 5773 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f6(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5778 */     PageContext pageContext = _jspx_page_context;
/* 5779 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5781 */     SelectTag _jspx_th_html_005fselect_005f6 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 5782 */     _jspx_th_html_005fselect_005f6.setPageContext(_jspx_page_context);
/* 5783 */     _jspx_th_html_005fselect_005f6.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5785 */     _jspx_th_html_005fselect_005f6.setProperty("toAddg");
/*      */     
/* 5787 */     _jspx_th_html_005fselect_005f6.setStyle("width:100%");
/*      */     
/* 5789 */     _jspx_th_html_005fselect_005f6.setMultiple("true");
/*      */     
/* 5791 */     _jspx_th_html_005fselect_005f6.setSize("5");
/* 5792 */     int _jspx_eval_html_005fselect_005f6 = _jspx_th_html_005fselect_005f6.doStartTag();
/* 5793 */     if (_jspx_eval_html_005fselect_005f6 != 0) {
/* 5794 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 5795 */         out = _jspx_page_context.pushBody();
/* 5796 */         _jspx_th_html_005fselect_005f6.setBodyContent((BodyContent)out);
/* 5797 */         _jspx_th_html_005fselect_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5800 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5801 */         if (_jspx_meth_html_005foptionsCollection_005f6(_jspx_th_html_005fselect_005f6, _jspx_page_context))
/* 5802 */           return true;
/* 5803 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5804 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f6.doAfterBody();
/* 5805 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5808 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 5809 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5812 */     if (_jspx_th_html_005fselect_005f6.doEndTag() == 5) {
/* 5813 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f6);
/* 5814 */       return true;
/*      */     }
/* 5816 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f6);
/* 5817 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f6(JspTag _jspx_th_html_005fselect_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5822 */     PageContext pageContext = _jspx_page_context;
/* 5823 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5825 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f6 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 5826 */     _jspx_th_html_005foptionsCollection_005f6.setPageContext(_jspx_page_context);
/* 5827 */     _jspx_th_html_005foptionsCollection_005f6.setParent((Tag)_jspx_th_html_005fselect_005f6);
/*      */     
/* 5829 */     _jspx_th_html_005foptionsCollection_005f6.setProperty("secondLevelMonitorTypesOptions");
/*      */     
/* 5831 */     _jspx_th_html_005foptionsCollection_005f6.setLabel("label");
/*      */     
/* 5833 */     _jspx_th_html_005foptionsCollection_005f6.setValue("value");
/* 5834 */     int _jspx_eval_html_005foptionsCollection_005f6 = _jspx_th_html_005foptionsCollection_005f6.doStartTag();
/* 5835 */     if (_jspx_th_html_005foptionsCollection_005f6.doEndTag() == 5) {
/* 5836 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f6);
/* 5837 */       return true;
/*      */     }
/* 5839 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f6);
/* 5840 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f12(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5845 */     PageContext pageContext = _jspx_page_context;
/* 5846 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5848 */     ButtonTag _jspx_th_html_005fbutton_005f12 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5849 */     _jspx_th_html_005fbutton_005f12.setPageContext(_jspx_page_context);
/* 5850 */     _jspx_th_html_005fbutton_005f12.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5852 */     _jspx_th_html_005fbutton_005f12.setStyleClass("buttons btn_small");
/*      */     
/* 5854 */     _jspx_th_html_005fbutton_005f12.setProperty("AddButton2");
/*      */     
/* 5856 */     _jspx_th_html_005fbutton_005f12.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.toAddg,document.AMActionForm.includeSecondLevelMonitorTypes),fnRemoveFromRightCombo(document.AMActionForm.toAddg);");
/*      */     
/* 5858 */     _jspx_th_html_005fbutton_005f12.setValue("&nbsp;&gt;&nbsp;");
/* 5859 */     int _jspx_eval_html_005fbutton_005f12 = _jspx_th_html_005fbutton_005f12.doStartTag();
/* 5860 */     if (_jspx_th_html_005fbutton_005f12.doEndTag() == 5) {
/* 5861 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f12);
/* 5862 */       return true;
/*      */     }
/* 5864 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f12);
/* 5865 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f13(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5870 */     PageContext pageContext = _jspx_page_context;
/* 5871 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5873 */     ButtonTag _jspx_th_html_005fbutton_005f13 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5874 */     _jspx_th_html_005fbutton_005f13.setPageContext(_jspx_page_context);
/* 5875 */     _jspx_th_html_005fbutton_005f13.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5877 */     _jspx_th_html_005fbutton_005f13.setStyleClass("buttons btn_small");
/*      */     
/* 5879 */     _jspx_th_html_005fbutton_005f13.setProperty("AddButton2");
/*      */     
/* 5881 */     _jspx_th_html_005fbutton_005f13.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.toAddg,document.AMActionForm.includeSecondLevelMonitorTypes),fnRemoveAllFromCombo(document.AMActionForm.toAddg);");
/*      */     
/* 5883 */     _jspx_th_html_005fbutton_005f13.setValue("&gt;&gt;");
/* 5884 */     int _jspx_eval_html_005fbutton_005f13 = _jspx_th_html_005fbutton_005f13.doStartTag();
/* 5885 */     if (_jspx_th_html_005fbutton_005f13.doEndTag() == 5) {
/* 5886 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f13);
/* 5887 */       return true;
/*      */     }
/* 5889 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f13);
/* 5890 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f14(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5895 */     PageContext pageContext = _jspx_page_context;
/* 5896 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5898 */     ButtonTag _jspx_th_html_005fbutton_005f14 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5899 */     _jspx_th_html_005fbutton_005f14.setPageContext(_jspx_page_context);
/* 5900 */     _jspx_th_html_005fbutton_005f14.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5902 */     _jspx_th_html_005fbutton_005f14.setStyleClass("buttons btn_small");
/*      */     
/* 5904 */     _jspx_th_html_005fbutton_005f14.setProperty("AddButton2");
/*      */     
/* 5906 */     _jspx_th_html_005fbutton_005f14.setOnclick("javascript:fnAddAllFromCombo(document.AMActionForm.includeSecondLevelMonitorTypes,document.AMActionForm.toAddg),fnRemoveAllFromCombo(document.AMActionForm.includeSecondLevelMonitorTypes);");
/*      */     
/* 5908 */     _jspx_th_html_005fbutton_005f14.setValue("&lt;&lt;");
/* 5909 */     int _jspx_eval_html_005fbutton_005f14 = _jspx_th_html_005fbutton_005f14.doStartTag();
/* 5910 */     if (_jspx_th_html_005fbutton_005f14.doEndTag() == 5) {
/* 5911 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f14);
/* 5912 */       return true;
/*      */     }
/* 5914 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f14);
/* 5915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fbutton_005f15(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5920 */     PageContext pageContext = _jspx_page_context;
/* 5921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5923 */     ButtonTag _jspx_th_html_005fbutton_005f15 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5924 */     _jspx_th_html_005fbutton_005f15.setPageContext(_jspx_page_context);
/* 5925 */     _jspx_th_html_005fbutton_005f15.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5927 */     _jspx_th_html_005fbutton_005f15.setStyleClass("buttons btn_small");
/*      */     
/* 5929 */     _jspx_th_html_005fbutton_005f15.setProperty("AddButton2");
/*      */     
/* 5931 */     _jspx_th_html_005fbutton_005f15.setOnclick("javascript:fnAddToRightCombo(document.AMActionForm.includeSecondLevelMonitorTypes,document.AMActionForm.toAddg),fnRemoveFromRightCombo(document.AMActionForm.includeSecondLevelMonitorTypes);");
/*      */     
/* 5933 */     _jspx_th_html_005fbutton_005f15.setValue("&nbsp;&lt;&nbsp;");
/* 5934 */     int _jspx_eval_html_005fbutton_005f15 = _jspx_th_html_005fbutton_005f15.doStartTag();
/* 5935 */     if (_jspx_th_html_005fbutton_005f15.doEndTag() == 5) {
/* 5936 */       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f15);
/* 5937 */       return true;
/*      */     }
/* 5939 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f15);
/* 5940 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5945 */     PageContext pageContext = _jspx_page_context;
/* 5946 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5948 */     SelectTag _jspx_th_html_005fselect_005f7 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 5949 */     _jspx_th_html_005fselect_005f7.setPageContext(_jspx_page_context);
/* 5950 */     _jspx_th_html_005fselect_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5952 */     _jspx_th_html_005fselect_005f7.setProperty("includeSecondLevelMonitorTypes");
/*      */     
/* 5954 */     _jspx_th_html_005fselect_005f7.setStyle("width:100%");
/*      */     
/* 5956 */     _jspx_th_html_005fselect_005f7.setMultiple("true");
/*      */     
/* 5958 */     _jspx_th_html_005fselect_005f7.setSize("5");
/* 5959 */     int _jspx_eval_html_005fselect_005f7 = _jspx_th_html_005fselect_005f7.doStartTag();
/* 5960 */     if (_jspx_eval_html_005fselect_005f7 != 0) {
/* 5961 */       if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 5962 */         out = _jspx_page_context.pushBody();
/* 5963 */         _jspx_th_html_005fselect_005f7.setBodyContent((BodyContent)out);
/* 5964 */         _jspx_th_html_005fselect_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5967 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5968 */         if (_jspx_meth_html_005foptionsCollection_005f7(_jspx_th_html_005fselect_005f7, _jspx_page_context))
/* 5969 */           return true;
/* 5970 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5971 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f7.doAfterBody();
/* 5972 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5975 */       if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 5976 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5979 */     if (_jspx_th_html_005fselect_005f7.doEndTag() == 5) {
/* 5980 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f7);
/* 5981 */       return true;
/*      */     }
/* 5983 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f7);
/* 5984 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f7(JspTag _jspx_th_html_005fselect_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5989 */     PageContext pageContext = _jspx_page_context;
/* 5990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5992 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f7 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.get(OptionsCollectionTag.class);
/* 5993 */     _jspx_th_html_005foptionsCollection_005f7.setPageContext(_jspx_page_context);
/* 5994 */     _jspx_th_html_005foptionsCollection_005f7.setParent((Tag)_jspx_th_html_005fselect_005f7);
/*      */     
/* 5996 */     _jspx_th_html_005foptionsCollection_005f7.setProperty("includeSecondLevelMonitorTypesOptions");
/*      */     
/* 5998 */     _jspx_th_html_005foptionsCollection_005f7.setLabel("label");
/*      */     
/* 6000 */     _jspx_th_html_005foptionsCollection_005f7.setValue("value");
/* 6001 */     int _jspx_eval_html_005foptionsCollection_005f7 = _jspx_th_html_005foptionsCollection_005f7.doStartTag();
/* 6002 */     if (_jspx_th_html_005foptionsCollection_005f7.doEndTag() == 5) {
/* 6003 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f7);
/* 6004 */       return true;
/*      */     }
/* 6006 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fvalue_005fproperty_005flabel_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f7);
/* 6007 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f52(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6012 */     PageContext pageContext = _jspx_page_context;
/* 6013 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6015 */     RadioTag _jspx_th_html_005fradio_005f52 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6016 */     _jspx_th_html_005fradio_005f52.setPageContext(_jspx_page_context);
/* 6017 */     _jspx_th_html_005fradio_005f52.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6019 */     _jspx_th_html_005fradio_005f52.setProperty("ciAttributesToBeSynced");
/*      */     
/* 6021 */     _jspx_th_html_005fradio_005f52.setValue("true");
/* 6022 */     int _jspx_eval_html_005fradio_005f52 = _jspx_th_html_005fradio_005f52.doStartTag();
/* 6023 */     if (_jspx_th_html_005fradio_005f52.doEndTag() == 5) {
/* 6024 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f52);
/* 6025 */       return true;
/*      */     }
/* 6027 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f52);
/* 6028 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f53(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6033 */     PageContext pageContext = _jspx_page_context;
/* 6034 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6036 */     RadioTag _jspx_th_html_005fradio_005f53 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6037 */     _jspx_th_html_005fradio_005f53.setPageContext(_jspx_page_context);
/* 6038 */     _jspx_th_html_005fradio_005f53.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6040 */     _jspx_th_html_005fradio_005f53.setProperty("ciAttributesToBeSynced");
/*      */     
/* 6042 */     _jspx_th_html_005fradio_005f53.setValue("false");
/* 6043 */     int _jspx_eval_html_005fradio_005f53 = _jspx_th_html_005fradio_005f53.doStartTag();
/* 6044 */     if (_jspx_th_html_005fradio_005f53.doEndTag() == 5) {
/* 6045 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f53);
/* 6046 */       return true;
/*      */     }
/* 6048 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f53);
/* 6049 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f54(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6054 */     PageContext pageContext = _jspx_page_context;
/* 6055 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6057 */     RadioTag _jspx_th_html_005fradio_005f54 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6058 */     _jspx_th_html_005fradio_005f54.setPageContext(_jspx_page_context);
/* 6059 */     _jspx_th_html_005fradio_005f54.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6061 */     _jspx_th_html_005fradio_005f54.setProperty("ciLinksToBeShown");
/*      */     
/* 6063 */     _jspx_th_html_005fradio_005f54.setValue("true");
/* 6064 */     int _jspx_eval_html_005fradio_005f54 = _jspx_th_html_005fradio_005f54.doStartTag();
/* 6065 */     if (_jspx_th_html_005fradio_005f54.doEndTag() == 5) {
/* 6066 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f54);
/* 6067 */       return true;
/*      */     }
/* 6069 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f54);
/* 6070 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f55(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6075 */     PageContext pageContext = _jspx_page_context;
/* 6076 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6078 */     RadioTag _jspx_th_html_005fradio_005f55 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6079 */     _jspx_th_html_005fradio_005f55.setPageContext(_jspx_page_context);
/* 6080 */     _jspx_th_html_005fradio_005f55.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 6082 */     _jspx_th_html_005fradio_005f55.setProperty("ciLinksToBeShown");
/*      */     
/* 6084 */     _jspx_th_html_005fradio_005f55.setValue("false");
/* 6085 */     int _jspx_eval_html_005fradio_005f55 = _jspx_th_html_005fradio_005f55.doStartTag();
/* 6086 */     if (_jspx_th_html_005fradio_005f55.doEndTag() == 5) {
/* 6087 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f55);
/* 6088 */       return true;
/*      */     }
/* 6090 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f55);
/* 6091 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f56(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6096 */     PageContext pageContext = _jspx_page_context;
/* 6097 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6099 */     RadioTag _jspx_th_html_005fradio_005f56 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6100 */     _jspx_th_html_005fradio_005f56.setPageContext(_jspx_page_context);
/* 6101 */     _jspx_th_html_005fradio_005f56.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6103 */     _jspx_th_html_005fradio_005f56.setProperty("ciRlMapalongWithListView");
/*      */     
/* 6105 */     _jspx_th_html_005fradio_005f56.setValue("true");
/* 6106 */     int _jspx_eval_html_005fradio_005f56 = _jspx_th_html_005fradio_005f56.doStartTag();
/* 6107 */     if (_jspx_th_html_005fradio_005f56.doEndTag() == 5) {
/* 6108 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f56);
/* 6109 */       return true;
/*      */     }
/* 6111 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f56);
/* 6112 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f57(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6117 */     PageContext pageContext = _jspx_page_context;
/* 6118 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6120 */     RadioTag _jspx_th_html_005fradio_005f57 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6121 */     _jspx_th_html_005fradio_005f57.setPageContext(_jspx_page_context);
/* 6122 */     _jspx_th_html_005fradio_005f57.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6124 */     _jspx_th_html_005fradio_005f57.setProperty("ciRlMapalongWithListView");
/*      */     
/* 6126 */     _jspx_th_html_005fradio_005f57.setValue("false");
/* 6127 */     int _jspx_eval_html_005fradio_005f57 = _jspx_th_html_005fradio_005f57.doStartTag();
/* 6128 */     if (_jspx_th_html_005fradio_005f57.doEndTag() == 5) {
/* 6129 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f57);
/* 6130 */       return true;
/*      */     }
/* 6132 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f57);
/* 6133 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6138 */     PageContext pageContext = _jspx_page_context;
/* 6139 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6141 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6142 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 6143 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 6145 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.servicenow.details.modifyPassword");
/* 6146 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 6147 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 6148 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6149 */       return true;
/*      */     }
/* 6151 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6152 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6157 */     PageContext pageContext = _jspx_page_context;
/* 6158 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6160 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6161 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 6162 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 6164 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.servicenow.details.cancel");
/* 6165 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 6166 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 6167 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6168 */       return true;
/*      */     }
/* 6170 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6171 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6176 */     PageContext pageContext = _jspx_page_context;
/* 6177 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6179 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6180 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 6181 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 6183 */     _jspx_th_c_005fout_005f1.setValue("${editServiceNow}");
/* 6184 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 6185 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 6186 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6187 */       return true;
/*      */     }
/* 6189 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6190 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6195 */     PageContext pageContext = _jspx_page_context;
/* 6196 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6198 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6199 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 6200 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 6202 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 6203 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 6204 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 6206 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 6207 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 6208 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6212 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 6213 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 6214 */       return true;
/*      */     }
/* 6216 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 6217 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ServiceNow_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */