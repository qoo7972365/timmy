/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
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
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ImportTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParamTag;
/*      */ 
/*      */ public final class TemplateProcessChooser_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 2178 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2179 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2180 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparam;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2203 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2207 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005ffmt_005fparam = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2223 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2227 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2229 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2233 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.release();
/* 2234 */     this._005fjspx_005ftagPool_005ffmt_005fparam.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2237 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2239 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody.release();
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2248 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2251 */     JspWriter out = null;
/* 2252 */     Object page = this;
/* 2253 */     JspWriter _jspx_out = null;
/* 2254 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2258 */       response.setContentType("text/html;charset=UTF-8");
/* 2259 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2261 */       _jspx_page_context = pageContext;
/* 2262 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2263 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2264 */       session = pageContext.getSession();
/* 2265 */       out = pageContext.getOut();
/* 2266 */       _jspx_out = out;
/*      */       
/* 2268 */       out.write("<!DOCTYPE html>\n");
/* 2269 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2270 */       out.write("\n\n\n\n\n\n\n\n\n\n");
/* 2271 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2273 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2274 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2275 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2277 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2279 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2281 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2283 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2284 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2285 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2286 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2289 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2290 */         String available = null;
/* 2291 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2292 */         out.write(10);
/*      */         
/* 2294 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2295 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2296 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2298 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2300 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2302 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2304 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2305 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2306 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2307 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2310 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2311 */           String unavailable = null;
/* 2312 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2313 */           out.write(10);
/*      */           
/* 2315 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2316 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2317 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2319 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2321 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2323 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2325 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2326 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2327 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2328 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2331 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2332 */             String unmanaged = null;
/* 2333 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2334 */             out.write(10);
/*      */             
/* 2336 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2337 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2338 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2340 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2342 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2344 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2346 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2347 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2348 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2349 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2352 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2353 */               String scheduled = null;
/* 2354 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2355 */               out.write(10);
/*      */               
/* 2357 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2358 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2359 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2361 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2363 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2365 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2367 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2368 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2369 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2370 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2373 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2374 */                 String critical = null;
/* 2375 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2376 */                 out.write(10);
/*      */                 
/* 2378 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2379 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2380 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2382 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2384 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2386 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2388 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2389 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2390 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2391 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2394 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2395 */                   String clear = null;
/* 2396 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2397 */                   out.write(10);
/*      */                   
/* 2399 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2400 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2401 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2403 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2405 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2407 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2409 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2410 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2411 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2412 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2415 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2416 */                     String warning = null;
/* 2417 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2418 */                     out.write(10);
/* 2419 */                     out.write(10);
/*      */                     
/* 2421 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2422 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2424 */                     out.write(10);
/* 2425 */                     out.write(10);
/* 2426 */                     out.write(10);
/* 2427 */                     out.write(10);
/* 2428 */                     out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2429 */                     out.write("\n\n<link href=\"/images/");
/* 2430 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2432 */                     out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 2433 */                     out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2434 */                     out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<script language=\"javascript\" src=\"../webclient/common/js/windowFunctions.js\"></script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/chosen.jquery.min.js\"></SCRIPT>\n\n<script>\nvar http = getHTTPObject(); // We create the HTTP Object\nfunction getHTTPObject() {\n  var xmlhttp;\n  if (window.ActiveXObject){\n    try {\n      xmlhttp = new ActiveXObject(\"Msxml2.XMLHTTP\");\n    } catch (e) {\n      try {\n        xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n      } catch (E) {\n        xmlhttp = false;\n      }\n    }\n}\n  else if (typeof XMLHttpRequest != 'undefined') {\n    try {\n      xmlhttp = new XMLHttpRequest();\n    } catch (e) {\n      xmlhttp = false;\n    }\n  }\n  return xmlhttp;\n}\n\n function fnAdd(z)\n{\n   ");
/* 2435 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2437 */                     out.write("\n  z=z-1;\n  var j=0;\n  ");
/* 2438 */                     if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*      */                       return;
/* 2440 */                     out.write("\n  {\n   var x='check'+k;  ");
/* 2441 */                     out.write("\n   if(document.getElementById(x)!=null && document.getElementById(x).checked)\n   {\n    j++;\n        break;\n   }\n  }\nif(j==0)\n    {\n  alert('");
/* 2442 */                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */                       return;
/* 2444 */                     out.write(39);
/* 2445 */                     out.write(41);
/* 2446 */                     out.write(59);
/* 2447 */                     out.write("\n         return;\n    }\n    else\n    {\n        ");
/* 2448 */                     if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */                       return;
/* 2450 */                     out.write("\n\n                ");
/* 2451 */                     if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*      */                       return;
/* 2453 */                     out.write("\n\n        document.forms[0].submit();\n    }\n\n}\n \n\nfunction getProcessList()\n{\n  showDiv(\"collectingservices\");\n        hideDiv('processlistdiv');\n\n    var restypeselectedidx=document.getElementById(\"servertypelist\").selectedIndex;\n    var restype=document.getElementById(\"servertypelist\").options[restypeselectedidx].text;\n    var restypeId=document.getElementById(\"servertypelist\").options[restypeselectedidx].value;\n    var residindex=document.getElementById(restypeId+'_select').selectedIndex;\n    var resid=document.getElementById(restypeId+'_select').options[residindex].value\n    ");
/* 2454 */                     if (_jspx_meth_c_005fchoose_005f1(_jspx_page_context))
/*      */                       return;
/* 2456 */                     out.write("\n    http.open(\"GET\",url,true);\n    http.onreadystatechange = displayProcessList ;\n    http.send(null);\n}\n\nfunction displayProcessList()\n{\n        if(http.readyState == 4){\n                var result = http.responseText;\n                if(result != null){\n      hideDiv('collectingservices');\n                        showDiv('processlistdiv');\n                        document.getElementById('processlistdiv').innerHTML=result;\n                }else{\n                        hideDiv('processlistdiv');\n                }\n        }\n}\n\n\n\nfunction getServerList()\n{\n        var servertypelist=document.getElementById(\"servertypelist\");\n        for(var i=0;i<servertypelist.length;i++){\n                var servertype=servertypelist.options[i].value;\n                if(servertypelist.options[i].selected){\n                        document.getElementById(servertype).style.display='block';\n                }else{\n                        document.getElementById(servertype).style.display='none';\n                }\n        }\n}\n\nfunction addProcessToTemplate()\n");
/* 2457 */                     out.write("{\n\t\tvar type=-1;\n        var retArr = new Array();\n        ");
/* 2458 */                     if (_jspx_meth_c_005fchoose_005f2(_jspx_page_context))
/*      */                       return;
/* 2460 */                     out.write("\n\n        var selectedprocess=getSelectedCheckBox(processcheckbox);        \n        if (selectedprocess.length != 0) { // if there was something selected\n      retArr.length = selectedprocess.length;\n      for (var i=0; i<selectedprocess.length; i++) {\n          var bgcolor=\"whitegrayborder\"  ");
/* 2461 */                     out.write("\n          if(i%2 == 0){\n                bgcolor=\"whitegrayborder\";  ");
/* 2462 */                     out.write("\n         }else{\n                bgcolor=\"yellowgrayborder\";  ");
/* 2463 */                     out.write("\n         }\n         if (processcheckbox[selectedprocess[i]]) { // Make sure it's an array\n\n            retArr[i] = processcheckbox[selectedprocess[i]].value;\n            ");
/* 2464 */                     if (_jspx_meth_c_005fchoose_005f3(_jspx_page_context))
/*      */                       return;
/* 2466 */                     out.write("\n                if(commandtd.firstChild && commandtd.firstChild.data != '-'){\n                        window.opener.insertRow(nametd.firstChild.data,nametd.firstChild.data,commandtd.firstChild.data,bgcolor,'false','false',type);\n\n                }else{\n                        window.opener.insertRow(nametd.firstChild.data,nametd.firstChild.data,'',bgcolor,'false','false',type);\n                }\n         }else { // It's not an array (there's just one check box and it's selected)\nretArr[i] = processcheckbox.value;// return that value\n         }\n      }\n        if( window.opener.document.getElementById(\"noprocessdiv\")){\n                window.opener.document.getElementById(\"noprocessdiv\").style.display='none';\n        }\n   }\n}\n\n\nfunction getSelectedCheckBox(selectedcheckbox)\n{\n         var retArr = new Array();\n   var lastElement = 0;\n   \n   if (selectedcheckbox[0]) {\n      for (var i=0; i<selectedcheckbox.length; i++) {\n         if (selectedcheckbox[i].checked) {\n            retArr.length = lastElement;\n            retArr[lastElement] = i;\n");
/* 2467 */                     out.write("            lastElement++;\n         }\n      }\n   } else {\n      if (selectedcheckbox.checked) { // if the one check box is checked\n         retArr.length = lastElement;\n         retArr[lastElement] = 0; // return zero as the only array value\n      }\n   }\n   return retArr;\n}\n\nfunction getProcessChooser(processchooser)\n{\n    var isAdminServer = '");
/* 2468 */                     out.print(com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer());
/* 2469 */                     out.write("'\n        var selectedvalue=processchooser.value;\n        if(selectedvalue == 'manualprocessentry'){\n                showDiv('manualprocessdetail');\n                hideDiv('autoprocessdetail');\n        } else {\n            url='/ProcessTemplates.do?method=getAllServerList&templatetype=");
/* 2470 */                     if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*      */                       return;
/* 2472 */                     out.write("&templatetypestr=");
/* 2473 */                     if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*      */                       return;
/* 2475 */                     out.write("';  ");
/* 2476 */                     out.write("\n            http.open(\"GET\",url,true);\n            http.onreadystatechange = displayServers ;\n            http.send(null);\n            showDiv('autoprocessdetail');\n            hideDiv('manualprocessdetail');       \n        }\n}\n\nfunction displayServers()\n{\n        if(http.readyState == 4){\n                var result = http.responseText;\n                if(result != null){\n                        showDiv('autoprocessdetail');\n                        $('#autoprocessdetail').html(result);\n                        hideDiv('manualprocessdetail');\n                }else{\n                        hideDiv('manualprocessdetail');\n                }\n        }\n\n}\n\nfunction addProcessManualEntry()\n{\n  ");
/* 2477 */                     if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                       return;
/* 2479 */                     out.write("\n\t\t var type=-1;\n\t\t var dispnametd;\n         var bgcolor=\"whitegrayborder\"; ");
/* 2480 */                     out.write("\n         var displayregex='false';\n         var serviceregex='false';\n        ");
/* 2481 */                     if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*      */                       return;
/* 2483 */                     out.write("\n        ");
/* 2484 */                     if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
/*      */                       return;
/* 2486 */                     out.write("\n        \n  if(commandtd.trim().length > 0){\n \twindow.opener.insertRow(dispnametd,nametd,commandtd,bgcolor,displayregex,serviceregex,type);\n  }else{\n  \twindow.opener.insertRow(dispnametd,nametd,'',bgcolor,displayregex,serviceregex,type);  \n  } \n        window.opener.hideDiv(\"noprocessdiv\"); ");
/* 2487 */                     out.write("\n   fnClose();\n}\n\nfunction editProcessDefinition(processid)\n{\n  var type=-1;\n  var modifieddispnametd;\t\n  var displayregex='false';\n  var serviceregex='false';\n  \n   ");
/* 2488 */                     if (_jspx_meth_c_005fif_005f4(_jspx_page_context))
/*      */                       return;
/* 2490 */                     out.write("\n        ");
/* 2491 */                     if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
/*      */                       return;
/* 2493 */                     out.write("\n  window.opener.editProcess(processid,modifieddispnametd,modifiedname,modifiedcmd,displayregex,serviceregex,type);  \n   fnClose();\n}\n\nfunction dispAutoServices()\n{      \n        if(http.readyState == 4){\n             var result = http.responseText;\n             if(result != null){\n               $('#autoservicedetail').html(result);\n               showDiv('autoservicedetail');\n                 hideDiv('templateservicedetail');\n             }\n        }\n\n}\n\nfunction onMyLoad()\n{   \n  var radioObj = document.getElementById('autoservice');\n  if (radioObj != null) {\n    radioObj.checked = true;\n    getWinServiceDetails(radioObj);\n  }\n}\n\nfunction getWinServiceDetails(processchooser)\n{\n    \n        var selectedvalue=processchooser.value;   \n        if(selectedvalue == 'templateservice'){\n              url='/HostResourceDispatch.do?method=getServicesFromTemplate&winservaction=true';  ");
/* 2494 */                     out.write("\n              http.open(\"GET\",url,true);\n              http.onreadystatechange = dispTemplateDetails ;\n              http.send(null);\n\n        }else{\n                url='/HostResource.do?addtotemplate=true&templatetype=");
/* 2495 */                     if (_jspx_meth_c_005fout_005f10(_jspx_page_context))
/*      */                       return;
/* 2497 */                     out.write("';  ");
/* 2498 */                     out.write("\n                http.open(\"GET\",url,true);\n                http.onreadystatechange = dispAutoServices ;\n                http.send(null);\n        }\n}\n\nfunction getServicesForTemplate(templateList)\n{\n  var selectedindex = templateList.selectedIndex;\n  for(var j=0;j<templateList.length;j++){\n    var tableid=document.getElementById(templateList.options[j].value + \"_services\");\n    if(j==selectedindex){\n      if (document.all) {\n        tableid.style.display=\"block\";//IE4+ specific code\n      } else {\n        tableid.style.display=\"table-row\";//Netscape and Mozilla\n      }\n    }else{\n      tableid.style.display=\"none\";\n    }\n  }\n}\n\nfunction dispTemplateDetails()\n{\n        if(http.readyState == 4){\n            var result = http.responseText;\n            if(result != null){\n                    $('#templateservicedetail').html(result);\n                    showDiv('templateservicedetail');\n                    hideDiv('autoservicedetail');                        \n            }\n        }\n\n}\n\nfunction fnSelectAll(e)\n{\n  var value = \"-1\";\n");
/* 2499 */                     out.write("  var templateDiv = document.getElementById(\"templateservicedetail\");\n  if (templateDiv != null && templateDiv.style.display != \"none\") {\n    var selObj = document.getElementById(\"templateList\");\n    if (selObj != null) {\n      value = selObj.options[selObj.selectedIndex].value;\n    }                 \n  }     \n  if (value != \"-1\") {\n    ToggleAll(e,document.AMActionForm,\"services_\"+value) ; //NO I18N\n  } \n}\n\nfunction fnAdd_1()\n{\n  \n  var value = \"-1\";\n  var templateDiv = document.getElementById(\"templateservicedetail\");\n  if (templateDiv != null && templateDiv.style.display != \"none\") {\n    var selObj = document.getElementById(\"templateList\");\n    if (selObj != null) {\n      value = selObj.options[selObj.selectedIndex].value;\n    }                 \n  }\n  if (value == \"-1\") {\n        fnClose();\n        return;   \n  }\n  var z = document.getElementById(\"services_\"+value+\"_count\").value;\n    var j=0;  \n    for(k=1;k<=z;k++) {\n      var x='check_'+value+\"_\"+k;\n      if(document.getElementById(x)!=null && document.getElementById(x).checked)\n");
/* 2500 */                     out.write("      {\n        j++;\n          break;\n      }\n    }\n  if(j==0)\n    {\n    alert('");
/* 2501 */                     if (_jspx_meth_fmt_005fmessage_005f9(_jspx_page_context))
/*      */                       return;
/* 2503 */                     out.write(39);
/* 2504 */                     out.write(41);
/* 2505 */                     out.write(59);
/* 2506 */                     out.write("\n        return;\n    }\n    else\n    {\n        addSelectedServices();\n        fnClose();\n        return;\n        document.forms[0].submit();\n    }\n}\n\nfunction addSelectedServices()\n{\n        var retArr = new Array();\n        var value = \"-1\";\n        var templateDiv = document.getElementById(\"templateservicedetail\");\n        if (templateDiv != null && templateDiv.style.display != \"none\") {\n          var selObj = document.getElementById(\"templateList\");\n          if (selObj != null) {\n        value = selObj.options[selObj.selectedIndex].value;\n      }                 \n        }     \n      processcheckbox=document.getElementsByName(\"services_\"+value);\n        var selectedprocess=getSelectedCheckBox(processcheckbox);        \n        if (selectedprocess.length != 0) { // if there was something selected\n          retArr.length = selectedprocess.length;\n          for (var i=0; i<selectedprocess.length; i++) {\n            var bgcolor=\"whitegrayborder\"  ");
/* 2507 */                     out.write("\n            if(i%2 == 0){\n                bgcolor=\"whitegrayborder\";  ");
/* 2508 */                     out.write("\n          }else{\n                bgcolor=\"yellowgrayborder\";  ");
/* 2509 */                     out.write("\n          }\n          if (processcheckbox[selectedprocess[i]]) { // Make sure it's an array\n              retArr[i] = processcheckbox[selectedprocess[i]].value;\n                var nametd=document.getElementById('servicename_'+value+\"_\"+retArr[i]);\n                var commandtd=document.getElementById('name_'+value+\"_\"+retArr[i]);\n                if(commandtd.firstChild && commandtd.firstChild.data != '-'){\n                        window.opener.insertRow(nametd.firstChild.data,commandtd.firstChild.data,bgcolor,'false','false',1);\n                }else{\n                        window.opener.insertRow(nametd.firstChild.data,'',bgcolor,'false','false',1);\n                }\n          }else { // It's not an array (there's just one check box and it's selected)\n        retArr[i] = processcheckbox.value;// return that value\n          }\n        }\n        if( window.opener.document.getElementById(\"noprocessdiv\")){\n            window.opener.document.getElementById(\"noprocessdiv\").style.display='none';\n        }\n    }\n}\n");
/* 2510 */                     out.write("\nfunction createServiceTemplate()\n{\n  fnClose();\n  window.opener.location.href=\"/ProcessTemplates.do?method=createProcessTemplate&templatetype=1\";\n  return;\n}\n\nfunction fnClose()\n{\n  window.close();\n}\n</script>\n");
/*      */                     
/* 2512 */                     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2513 */                     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2514 */                     _jspx_th_c_005fchoose_005f4.setParent(null);
/* 2515 */                     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2516 */                     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                       for (;;) {
/* 2518 */                         out.write(10);
/*      */                         
/* 2520 */                         WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2521 */                         _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 2522 */                         _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                         
/* 2524 */                         _jspx_th_c_005fwhen_005f4.setTest("${param.winservaction == \"true\"}");
/* 2525 */                         int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 2526 */                         if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                           for (;;) {
/* 2528 */                             out.write("\n<title>");
/* 2529 */                             if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                               return;
/* 2531 */                             out.write("</title>");
/* 2532 */                             out.write("\n  <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"55\" class=\"darkheaderbg\">\n    <tr>\n      <td class=\"headingboldwhite\" colspan=\"4\" style=\"height:40px;\">&nbsp;");
/* 2533 */                             if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                               return;
/* 2535 */                             out.write("</td>");
/* 2536 */                             out.write("\n    </tr>\n  </table> \n  <table width=\"98%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"  align=\"center\" class=\"lrtbdarkborder\" style=\"margin-top:10px;margin-left:9px;\">\n        <tr ");
/* 2537 */                             if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2538 */                               out.write(" style=\"display:none\" ");
/*      */                             }
/* 2540 */                             out.write(" >\n          <td style=\"width:8px;\" class=\"bodytext\">&nbsp;</td>\n          <td height=\"29\" width=\"50%\" class=\"bodytext\" valig=\"middle\" style=\"height:50px;\">\n        <input type=\"radio\" id=\"autoservice\" name=\"servicechooser\" value=\"autoservice\"  onclick=\"javascript:getWinServiceDetails(this)\" checked/>");
/* 2541 */                             if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                               return;
/* 2543 */                             out.write("</td>");
/* 2544 */                             out.write("\n          <td height=\"29\"  width=\"50%\" class=\"bodytext\" align=\"left\">\n            <input type=\"radio\" id=\"templateservice\" name=\"servicechooser\" value=\"templateservice\" onclick=\"javascript:getWinServiceDetails(this)\"/>");
/* 2545 */                             if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                               return;
/* 2547 */                             out.write("</td>");
/* 2548 */                             out.write("      \n        </tr>\n        <tr>\n          <td colspan=\"4\">\n            <div id=\"templateservicedetail\" styple=\"display:none\">\n                             \n        </div>\n          </td>\n     </tr>  \n        <tr>\n          <td colspan=\"4\" >\n            <table cellpadding=\"0\" cellspacing=\"0\"  width=\"100%\" border=\"0\">\n              <tr>\n                <td><div id=\"autoservicedetail\" style=\"display:block\"></div></td>\n              </tr>\n            </table>\n          </td>\n        </tr>\n</table>\n<script>\nonMyLoad();\n</script>\n");
/* 2549 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 2550 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2554 */                         if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 2555 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                         }
/*      */                         
/* 2558 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2559 */                         out.write(10);
/* 2560 */                         out.write(10);
/* 2561 */                         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/*      */                           return;
/* 2563 */                         out.write(10);
/* 2564 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2565 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2569 */                     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2570 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/*      */                     }
/*      */                     else {
/* 2573 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2574 */                       out.write(10);
/*      */                     }
/* 2576 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2577 */         out = _jspx_out;
/* 2578 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2579 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2580 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2583 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2589 */     PageContext pageContext = _jspx_page_context;
/* 2590 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2592 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2593 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2594 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2596 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2598 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2599 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2600 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2601 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2602 */       return true;
/*      */     }
/* 2604 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2605 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2610 */     PageContext pageContext = _jspx_page_context;
/* 2611 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2613 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2614 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2615 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 2617 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2618 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2619 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 2621 */         out.write("\n     alertUser();\n     return;\n     ");
/* 2622 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2623 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2627 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2628 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2629 */       return true;
/*      */     }
/* 2631 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2632 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2637 */     PageContext pageContext = _jspx_page_context;
/* 2638 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2640 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2641 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2642 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2643 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2644 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 2646 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2647 */           return true;
/* 2648 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2649 */           return true;
/* 2650 */         out.write("\n    ");
/* 2651 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2652 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2656 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2657 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2658 */       return true;
/*      */     }
/* 2660 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2661 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2666 */     PageContext pageContext = _jspx_page_context;
/* 2667 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2669 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2670 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2671 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 2673 */     _jspx_th_c_005fwhen_005f0.setTest("${param.templatetype=='0'}");
/* 2674 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2675 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 2677 */         out.write("\n  for(k=1;k<=z;k++)\n  ");
/* 2678 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2679 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2683 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2684 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2685 */       return true;
/*      */     }
/* 2687 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2688 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2693 */     PageContext pageContext = _jspx_page_context;
/* 2694 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2696 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2697 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2698 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 2699 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2700 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 2702 */         out.write("\n  for(k=0;k<=z;k++)\n  ");
/* 2703 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2704 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2708 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2709 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2710 */       return true;
/*      */     }
/* 2712 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2718 */     PageContext pageContext = _jspx_page_context;
/* 2719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2721 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 2722 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2723 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*      */     
/* 2725 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.template.processadd");
/* 2726 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 2727 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 2728 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2729 */         out = _jspx_page_context.pushBody();
/* 2730 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 2731 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2734 */         if (_jspx_meth_fmt_005fparam_005f0(_jspx_th_fmt_005fmessage_005f0, _jspx_page_context))
/* 2735 */           return true;
/* 2736 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 2737 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2740 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2741 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2744 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 2745 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2746 */       return true;
/*      */     }
/* 2748 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2749 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f0(JspTag _jspx_th_fmt_005fmessage_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2754 */     PageContext pageContext = _jspx_page_context;
/* 2755 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2757 */     ParamTag _jspx_th_fmt_005fparam_005f0 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 2758 */     _jspx_th_fmt_005fparam_005f0.setPageContext(_jspx_page_context);
/* 2759 */     _jspx_th_fmt_005fparam_005f0.setParent((Tag)_jspx_th_fmt_005fmessage_005f0);
/* 2760 */     int _jspx_eval_fmt_005fparam_005f0 = _jspx_th_fmt_005fparam_005f0.doStartTag();
/* 2761 */     if (_jspx_eval_fmt_005fparam_005f0 != 0) {
/* 2762 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 2763 */         out = _jspx_page_context.pushBody();
/* 2764 */         _jspx_th_fmt_005fparam_005f0.setBodyContent((BodyContent)out);
/* 2765 */         _jspx_th_fmt_005fparam_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2768 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_fmt_005fparam_005f0, _jspx_page_context))
/* 2769 */           return true;
/* 2770 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f0.doAfterBody();
/* 2771 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2774 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 2775 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2778 */     if (_jspx_th_fmt_005fparam_005f0.doEndTag() == 5) {
/* 2779 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 2780 */       return true;
/*      */     }
/* 2782 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 2783 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_fmt_005fparam_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2788 */     PageContext pageContext = _jspx_page_context;
/* 2789 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2791 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2792 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2793 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_fmt_005fparam_005f0);
/*      */     
/* 2795 */     _jspx_th_c_005fout_005f1.setValue("${paramtemplatetypestr}");
/* 2796 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2797 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2798 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2799 */       return true;
/*      */     }
/* 2801 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2802 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2807 */     PageContext pageContext = _jspx_page_context;
/* 2808 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2810 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2811 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2812 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 2814 */     _jspx_th_c_005fif_005f0.setTest("${not empty param.templatetype }");
/* 2815 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2816 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 2818 */         out.write("\n                        addProcessToTemplate();\n                fnClose();\n                        return;\n                ");
/* 2819 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2820 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2824 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2825 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2826 */       return true;
/*      */     }
/* 2828 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2829 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2834 */     PageContext pageContext = _jspx_page_context;
/* 2835 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2837 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2838 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2839 */     _jspx_th_c_005fif_005f1.setParent(null);
/*      */     
/* 2841 */     _jspx_th_c_005fif_005f1.setTest("${not empty param.winservaction}");
/* 2842 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2843 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 2845 */         out.write("\n                addProcessToTemplate();\n        fnClose();\n                return;\n        ");
/* 2846 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2847 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2851 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2852 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2853 */       return true;
/*      */     }
/* 2855 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2856 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2861 */     PageContext pageContext = _jspx_page_context;
/* 2862 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2864 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2865 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2866 */     _jspx_th_c_005fchoose_005f1.setParent(null);
/* 2867 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2868 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 2870 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 2871 */           return true;
/* 2872 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 2873 */           return true;
/* 2874 */         out.write("\n    ");
/* 2875 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2876 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2880 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2881 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2882 */       return true;
/*      */     }
/* 2884 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2885 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2890 */     PageContext pageContext = _jspx_page_context;
/* 2891 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2893 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2894 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2895 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 2897 */     _jspx_th_c_005fwhen_005f1.setTest("${param.templatetype=='0'}");
/* 2898 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2899 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 2901 */         out.write("\n    url=\"/HostResource.do?getProcessList=true&resourceid=\"+resid+\"&resType=\"+restype+\"&addtotemplate=true&templatetype=0\";  ");
/* 2902 */         out.write("\n    ");
/* 2903 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2904 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2908 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2909 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2910 */       return true;
/*      */     }
/* 2912 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2913 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2918 */     PageContext pageContext = _jspx_page_context;
/* 2919 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2921 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2922 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2923 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 2924 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2925 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 2927 */         out.write("\n    url=\"/HostResource.do?getServiceList=true&resourceid=\"+resid+\"&resType=\"+restype+\"&addtotemplate=true&templatetype=1\";  ");
/* 2928 */         out.write("\n    ");
/* 2929 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2930 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2934 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2935 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2936 */       return true;
/*      */     }
/* 2938 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2939 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2944 */     PageContext pageContext = _jspx_page_context;
/* 2945 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2947 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2948 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2949 */     _jspx_th_c_005fchoose_005f2.setParent(null);
/* 2950 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2951 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 2953 */         out.write("\n          ");
/* 2954 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 2955 */           return true;
/* 2956 */         out.write("\n            ");
/* 2957 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 2958 */           return true;
/* 2959 */         out.write("\n    ");
/* 2960 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2961 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2965 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2966 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2967 */       return true;
/*      */     }
/* 2969 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2970 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2975 */     PageContext pageContext = _jspx_page_context;
/* 2976 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2978 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2979 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2980 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 2982 */     _jspx_th_c_005fwhen_005f2.setTest("${param.templatetype=='0'}");
/* 2983 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2984 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 2986 */         out.write("\n            processcheckbox=document.getElementsByName(\"process\");\n            type=0;\n            ");
/* 2987 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2988 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2992 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2993 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2994 */       return true;
/*      */     }
/* 2996 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2997 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3002 */     PageContext pageContext = _jspx_page_context;
/* 3003 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3005 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3006 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 3007 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 3008 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 3009 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 3011 */         out.write("\n            processcheckbox=document.getElementsByName(\"services\");\n            type=1;\n            ");
/* 3012 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3013 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3017 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3018 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3019 */       return true;
/*      */     }
/* 3021 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3027 */     PageContext pageContext = _jspx_page_context;
/* 3028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3030 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3031 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 3032 */     _jspx_th_c_005fchoose_005f3.setParent(null);
/* 3033 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 3034 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 3036 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 3037 */           return true;
/* 3038 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 3039 */           return true;
/* 3040 */         out.write("\n    ");
/* 3041 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 3042 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3046 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 3047 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 3048 */       return true;
/*      */     }
/* 3050 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 3051 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3056 */     PageContext pageContext = _jspx_page_context;
/* 3057 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3059 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3060 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 3061 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 3063 */     _jspx_th_c_005fwhen_005f3.setTest("${param.templatetype=='0'}");
/* 3064 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 3065 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 3067 */         out.write("\n                var nametd=document.getElementById('name'+retArr[i]);\n                var commandtd=document.getElementById('command'+retArr[i]);\n                 ");
/* 3068 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 3069 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3073 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 3074 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3075 */       return true;
/*      */     }
/* 3077 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3078 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3083 */     PageContext pageContext = _jspx_page_context;
/* 3084 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3086 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3087 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 3088 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 3089 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 3090 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 3092 */         out.write("\n                 var nametd=document.getElementById('servicename'+retArr[i]);\n                var commandtd=document.getElementById('name'+retArr[i]);\n                 ");
/* 3093 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 3094 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3098 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 3099 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3100 */       return true;
/*      */     }
/* 3102 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3103 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3108 */     PageContext pageContext = _jspx_page_context;
/* 3109 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3111 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3112 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3113 */     _jspx_th_c_005fout_005f2.setParent(null);
/*      */     
/* 3115 */     _jspx_th_c_005fout_005f2.setValue("${param.templatetype}");
/* 3116 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3117 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3118 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3119 */       return true;
/*      */     }
/* 3121 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3122 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3127 */     PageContext pageContext = _jspx_page_context;
/* 3128 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3130 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3131 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3132 */     _jspx_th_c_005fout_005f3.setParent(null);
/*      */     
/* 3134 */     _jspx_th_c_005fout_005f3.setValue("${paramtemplatetypestr}");
/* 3135 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3136 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3137 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3138 */       return true;
/*      */     }
/* 3140 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3141 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3146 */     PageContext pageContext = _jspx_page_context;
/* 3147 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3149 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3150 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3151 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 3153 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3154 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3155 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3157 */         out.write("\n  alertUser();\n  return false;\n");
/* 3158 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3159 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3163 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3164 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3165 */       return true;
/*      */     }
/* 3167 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3168 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3173 */     PageContext pageContext = _jspx_page_context;
/* 3174 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3176 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3177 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3178 */     _jspx_th_c_005fif_005f2.setParent(null);
/*      */     
/* 3180 */     _jspx_th_c_005fif_005f2.setTest("${param.templatetype == PROCESSTEMPLATE}");
/* 3181 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3182 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3184 */         out.write("\n        type=0;\n        if(!document.getElementById(\"processDispNameTB\").value){\n            alert('");
/* 3185 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3186 */           return true;
/* 3187 */         out.write("'); ");
/* 3188 */         out.write("\n            document.getElementById(\"processDispNameTB\").focus();\n            return;\n    \t}\n        if(!document.getElementById(\"processNameTB\").value){\n            alert('");
/* 3189 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3190 */           return true;
/* 3191 */         out.write("'); ");
/* 3192 */         out.write("\n            document.getElementById(\"processNameTB\").focus();\n            return;\n        }\n        dispnametd=document.getElementById(\"processDispNameTB\").value;\n        var nametd=document.getElementById(\"processNameTB\").value;\n        var commandtd=document.getElementById(\"processCommandArgs\").value;\n         if ($('#pnameRegex').is(\":checked\"))\n        {\n          displayregex='true';\n        }\n        if ($('#cmdRegex').is(\":checked\"))\n        {\n          serviceregex='true';\n        }\n        ");
/* 3193 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3194 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3198 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3199 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3200 */       return true;
/*      */     }
/* 3202 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3203 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3208 */     PageContext pageContext = _jspx_page_context;
/* 3209 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3211 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3212 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3213 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3215 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.processtemplate.enter.process.display.name");
/* 3216 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3217 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3218 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3219 */       return true;
/*      */     }
/* 3221 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3222 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3227 */     PageContext pageContext = _jspx_page_context;
/* 3228 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3230 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 3231 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 3232 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3234 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.processtemplate.enterprocessname");
/* 3235 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 3236 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 3237 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3238 */         out = _jspx_page_context.pushBody();
/* 3239 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 3240 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3243 */         if (_jspx_meth_fmt_005fparam_005f1(_jspx_th_fmt_005fmessage_005f2, _jspx_page_context))
/* 3244 */           return true;
/* 3245 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 3246 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3249 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3250 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3253 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3254 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3255 */       return true;
/*      */     }
/* 3257 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3258 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f1(JspTag _jspx_th_fmt_005fmessage_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3263 */     PageContext pageContext = _jspx_page_context;
/* 3264 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3266 */     ParamTag _jspx_th_fmt_005fparam_005f1 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 3267 */     _jspx_th_fmt_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3268 */     _jspx_th_fmt_005fparam_005f1.setParent((Tag)_jspx_th_fmt_005fmessage_005f2);
/* 3269 */     int _jspx_eval_fmt_005fparam_005f1 = _jspx_th_fmt_005fparam_005f1.doStartTag();
/* 3270 */     if (_jspx_eval_fmt_005fparam_005f1 != 0) {
/* 3271 */       if (_jspx_eval_fmt_005fparam_005f1 != 1) {
/* 3272 */         out = _jspx_page_context.pushBody();
/* 3273 */         _jspx_th_fmt_005fparam_005f1.setBodyContent((BodyContent)out);
/* 3274 */         _jspx_th_fmt_005fparam_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3277 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_fmt_005fparam_005f1, _jspx_page_context))
/* 3278 */           return true;
/* 3279 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f1.doAfterBody();
/* 3280 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3283 */       if (_jspx_eval_fmt_005fparam_005f1 != 1) {
/* 3284 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3287 */     if (_jspx_th_fmt_005fparam_005f1.doEndTag() == 5) {
/* 3288 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f1);
/* 3289 */       return true;
/*      */     }
/* 3291 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f1);
/* 3292 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_fmt_005fparam_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3297 */     PageContext pageContext = _jspx_page_context;
/* 3298 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3300 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3301 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3302 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_fmt_005fparam_005f1);
/*      */     
/* 3304 */     _jspx_th_c_005fout_005f4.setValue("${paramtemplatetypestr}");
/* 3305 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3306 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3307 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3308 */       return true;
/*      */     }
/* 3310 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3311 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3316 */     PageContext pageContext = _jspx_page_context;
/* 3317 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3319 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3320 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3321 */     _jspx_th_c_005fif_005f3.setParent(null);
/*      */     
/* 3323 */     _jspx_th_c_005fif_005f3.setTest("${param.templatetype == SERVICETEMPLATE}");
/* 3324 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3325 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 3327 */         out.write("\n        type=1;\n  if(!document.getElementById(\"serviceDisplayName\").value){ // Check valid service display name\n    alert('");
/* 3328 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 3329 */           return true;
/* 3330 */         out.write("');  ");
/* 3331 */         out.write("\n    return;\n  }\n        if(!document.getElementById(\"serviceName\").value){ // Check valid service name\n                alert('");
/* 3332 */         if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 3333 */           return true;
/* 3334 */         out.write("'); ");
/* 3335 */         out.write("\n                return;\n        }\n        var nametd=document.getElementById(\"serviceDisplayName\").value;\n        var commandtd=document.getElementById(\"serviceName\").value;\n        dispnametd=nametd;\n        if ($('#displayregex').is(\":checked\"))\n        {\n          displayregex='true';\n        }\n        if ($('#serviceregex').is(\":checked\"))\n        {\n          serviceregex='true';\n        }\n        ");
/* 3336 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3337 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3341 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3342 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3343 */       return true;
/*      */     }
/* 3345 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3346 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3351 */     PageContext pageContext = _jspx_page_context;
/* 3352 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3354 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 3355 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 3356 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3358 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.processtemplate.enterservicedispname");
/* 3359 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 3360 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 3361 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3362 */         out = _jspx_page_context.pushBody();
/* 3363 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 3364 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3367 */         if (_jspx_meth_fmt_005fparam_005f2(_jspx_th_fmt_005fmessage_005f3, _jspx_page_context))
/* 3368 */           return true;
/* 3369 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 3370 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3373 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3374 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3377 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3378 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3379 */       return true;
/*      */     }
/* 3381 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3382 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f2(JspTag _jspx_th_fmt_005fmessage_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3387 */     PageContext pageContext = _jspx_page_context;
/* 3388 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3390 */     ParamTag _jspx_th_fmt_005fparam_005f2 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 3391 */     _jspx_th_fmt_005fparam_005f2.setPageContext(_jspx_page_context);
/* 3392 */     _jspx_th_fmt_005fparam_005f2.setParent((Tag)_jspx_th_fmt_005fmessage_005f3);
/* 3393 */     int _jspx_eval_fmt_005fparam_005f2 = _jspx_th_fmt_005fparam_005f2.doStartTag();
/* 3394 */     if (_jspx_eval_fmt_005fparam_005f2 != 0) {
/* 3395 */       if (_jspx_eval_fmt_005fparam_005f2 != 1) {
/* 3396 */         out = _jspx_page_context.pushBody();
/* 3397 */         _jspx_th_fmt_005fparam_005f2.setBodyContent((BodyContent)out);
/* 3398 */         _jspx_th_fmt_005fparam_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3401 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_fmt_005fparam_005f2, _jspx_page_context))
/* 3402 */           return true;
/* 3403 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f2.doAfterBody();
/* 3404 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3407 */       if (_jspx_eval_fmt_005fparam_005f2 != 1) {
/* 3408 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3411 */     if (_jspx_th_fmt_005fparam_005f2.doEndTag() == 5) {
/* 3412 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f2);
/* 3413 */       return true;
/*      */     }
/* 3415 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f2);
/* 3416 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_fmt_005fparam_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3421 */     PageContext pageContext = _jspx_page_context;
/* 3422 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3424 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3425 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3426 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_fmt_005fparam_005f2);
/*      */     
/* 3428 */     _jspx_th_c_005fout_005f5.setValue("${paramtemplatetypestr}");
/* 3429 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3430 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3431 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3432 */       return true;
/*      */     }
/* 3434 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3440 */     PageContext pageContext = _jspx_page_context;
/* 3441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3443 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 3444 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3445 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3447 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.processtemplate.enterprocessname");
/* 3448 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3449 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 3450 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3451 */         out = _jspx_page_context.pushBody();
/* 3452 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 3453 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3456 */         if (_jspx_meth_fmt_005fparam_005f3(_jspx_th_fmt_005fmessage_005f4, _jspx_page_context))
/* 3457 */           return true;
/* 3458 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 3459 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3462 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3463 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3466 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3467 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3468 */       return true;
/*      */     }
/* 3470 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3471 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f3(JspTag _jspx_th_fmt_005fmessage_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3476 */     PageContext pageContext = _jspx_page_context;
/* 3477 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3479 */     ParamTag _jspx_th_fmt_005fparam_005f3 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 3480 */     _jspx_th_fmt_005fparam_005f3.setPageContext(_jspx_page_context);
/* 3481 */     _jspx_th_fmt_005fparam_005f3.setParent((Tag)_jspx_th_fmt_005fmessage_005f4);
/* 3482 */     int _jspx_eval_fmt_005fparam_005f3 = _jspx_th_fmt_005fparam_005f3.doStartTag();
/* 3483 */     if (_jspx_eval_fmt_005fparam_005f3 != 0) {
/* 3484 */       if (_jspx_eval_fmt_005fparam_005f3 != 1) {
/* 3485 */         out = _jspx_page_context.pushBody();
/* 3486 */         _jspx_th_fmt_005fparam_005f3.setBodyContent((BodyContent)out);
/* 3487 */         _jspx_th_fmt_005fparam_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3490 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_fmt_005fparam_005f3, _jspx_page_context))
/* 3491 */           return true;
/* 3492 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f3.doAfterBody();
/* 3493 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3496 */       if (_jspx_eval_fmt_005fparam_005f3 != 1) {
/* 3497 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3500 */     if (_jspx_th_fmt_005fparam_005f3.doEndTag() == 5) {
/* 3501 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f3);
/* 3502 */       return true;
/*      */     }
/* 3504 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f3);
/* 3505 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_fmt_005fparam_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3510 */     PageContext pageContext = _jspx_page_context;
/* 3511 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3513 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3514 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3515 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_fmt_005fparam_005f3);
/*      */     
/* 3517 */     _jspx_th_c_005fout_005f6.setValue("${paramtemplatetypestr}");
/* 3518 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3519 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3520 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3521 */       return true;
/*      */     }
/* 3523 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3524 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3529 */     PageContext pageContext = _jspx_page_context;
/* 3530 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3532 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3533 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3534 */     _jspx_th_c_005fif_005f4.setParent(null);
/*      */     
/* 3536 */     _jspx_th_c_005fif_005f4.setTest("${param.templatetype == PROCESSTEMPLATE}");
/* 3537 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3538 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 3540 */         out.write("\n   \t\ttype=0;\n\t    if(!document.getElementById(\"processDispNameTB\").value){\n\t       alert('");
/* 3541 */         if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 3542 */           return true;
/* 3543 */         out.write("'); ");
/* 3544 */         out.write("\n\t       return;\n\t\t}\n        if(!document.getElementById(\"processNameTB\").value){\n           alert('");
/* 3545 */         if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 3546 */           return true;
/* 3547 */         out.write("'); ");
/* 3548 */         out.write("\n           return;\n        }\n        modifieddispnametd=document.getElementById(\"processDispNameTB\").value;\n        var modifiedname=document.getElementById(\"processNameTB\").value;\n        var modifiedcmd=document.getElementById(\"processCommandArgs\").value;\n\n         if ($('#pnameRegex').is(\":checked\"))\n        {\n          displayregex='true';\n        }\n        if ($('#cmdRegex').is(\":checked\"))\n        {\n          serviceregex='true';\n        }\n        ");
/* 3549 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3550 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3554 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3555 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3556 */       return true;
/*      */     }
/* 3558 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3559 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3564 */     PageContext pageContext = _jspx_page_context;
/* 3565 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3567 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3568 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3569 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 3571 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.processtemplate.enter.process.display.name");
/* 3572 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3573 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3574 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3575 */       return true;
/*      */     }
/* 3577 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3578 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3583 */     PageContext pageContext = _jspx_page_context;
/* 3584 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3586 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 3587 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 3588 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 3590 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.webclient.processtemplate.enterprocessname");
/* 3591 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 3592 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 3593 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3594 */         out = _jspx_page_context.pushBody();
/* 3595 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 3596 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3599 */         if (_jspx_meth_fmt_005fparam_005f4(_jspx_th_fmt_005fmessage_005f6, _jspx_page_context))
/* 3600 */           return true;
/* 3601 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 3602 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3605 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3606 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3609 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 3610 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3611 */       return true;
/*      */     }
/* 3613 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3614 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f4(JspTag _jspx_th_fmt_005fmessage_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3619 */     PageContext pageContext = _jspx_page_context;
/* 3620 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3622 */     ParamTag _jspx_th_fmt_005fparam_005f4 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 3623 */     _jspx_th_fmt_005fparam_005f4.setPageContext(_jspx_page_context);
/* 3624 */     _jspx_th_fmt_005fparam_005f4.setParent((Tag)_jspx_th_fmt_005fmessage_005f6);
/* 3625 */     int _jspx_eval_fmt_005fparam_005f4 = _jspx_th_fmt_005fparam_005f4.doStartTag();
/* 3626 */     if (_jspx_eval_fmt_005fparam_005f4 != 0) {
/* 3627 */       if (_jspx_eval_fmt_005fparam_005f4 != 1) {
/* 3628 */         out = _jspx_page_context.pushBody();
/* 3629 */         _jspx_th_fmt_005fparam_005f4.setBodyContent((BodyContent)out);
/* 3630 */         _jspx_th_fmt_005fparam_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3633 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_fmt_005fparam_005f4, _jspx_page_context))
/* 3634 */           return true;
/* 3635 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f4.doAfterBody();
/* 3636 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3639 */       if (_jspx_eval_fmt_005fparam_005f4 != 1) {
/* 3640 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3643 */     if (_jspx_th_fmt_005fparam_005f4.doEndTag() == 5) {
/* 3644 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f4);
/* 3645 */       return true;
/*      */     }
/* 3647 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f4);
/* 3648 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_fmt_005fparam_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3653 */     PageContext pageContext = _jspx_page_context;
/* 3654 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3656 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3657 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3658 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_fmt_005fparam_005f4);
/*      */     
/* 3660 */     _jspx_th_c_005fout_005f7.setValue("${paramtemplatetypestr}");
/* 3661 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3662 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3663 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3664 */       return true;
/*      */     }
/* 3666 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3667 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3672 */     PageContext pageContext = _jspx_page_context;
/* 3673 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3675 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3676 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3677 */     _jspx_th_c_005fif_005f5.setParent(null);
/*      */     
/* 3679 */     _jspx_th_c_005fif_005f5.setTest("${param.templatetype == SERVICETEMPLATE}");
/* 3680 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3681 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 3683 */         out.write("\n    \ttype=1;\n        if(!document.getElementById(\"serviceDisplayName\").value){ // Check valid service display name\n                alert('");
/* 3684 */         if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 3685 */           return true;
/* 3686 */         out.write("');  ");
/* 3687 */         out.write("\n                return;\n        }\n        if(!document.getElementById(\"serviceName\").value){ // Check valid service name\n                alert('");
/* 3688 */         if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 3689 */           return true;
/* 3690 */         out.write("'); ");
/* 3691 */         out.write("\n                return;\n        }\n        var modifiedname=document.getElementById(\"serviceDisplayName\").value;\n        var modifiedcmd=document.getElementById(\"serviceName\").value;\n        modifieddispnametd=modifiedname;\n         if ($('#displayregex').is(\":checked\"))\n        {\n          displayregex='true';\n        }\n        if ($('#serviceregex').is(\":checked\"))\n        {\n          serviceregex='true';\n        }\n        ");
/* 3692 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3693 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3697 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3698 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3699 */       return true;
/*      */     }
/* 3701 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3702 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3707 */     PageContext pageContext = _jspx_page_context;
/* 3708 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3710 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 3711 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 3712 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 3714 */     _jspx_th_fmt_005fmessage_005f7.setKey("am.webclient.processtemplate.enterservicedispname");
/* 3715 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 3716 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 3717 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3718 */         out = _jspx_page_context.pushBody();
/* 3719 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 3720 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3723 */         if (_jspx_meth_fmt_005fparam_005f5(_jspx_th_fmt_005fmessage_005f7, _jspx_page_context))
/* 3724 */           return true;
/* 3725 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 3726 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3729 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3730 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3733 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 3734 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3735 */       return true;
/*      */     }
/* 3737 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3738 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f5(JspTag _jspx_th_fmt_005fmessage_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3743 */     PageContext pageContext = _jspx_page_context;
/* 3744 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3746 */     ParamTag _jspx_th_fmt_005fparam_005f5 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 3747 */     _jspx_th_fmt_005fparam_005f5.setPageContext(_jspx_page_context);
/* 3748 */     _jspx_th_fmt_005fparam_005f5.setParent((Tag)_jspx_th_fmt_005fmessage_005f7);
/* 3749 */     int _jspx_eval_fmt_005fparam_005f5 = _jspx_th_fmt_005fparam_005f5.doStartTag();
/* 3750 */     if (_jspx_eval_fmt_005fparam_005f5 != 0) {
/* 3751 */       if (_jspx_eval_fmt_005fparam_005f5 != 1) {
/* 3752 */         out = _jspx_page_context.pushBody();
/* 3753 */         _jspx_th_fmt_005fparam_005f5.setBodyContent((BodyContent)out);
/* 3754 */         _jspx_th_fmt_005fparam_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3757 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_fmt_005fparam_005f5, _jspx_page_context))
/* 3758 */           return true;
/* 3759 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f5.doAfterBody();
/* 3760 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3763 */       if (_jspx_eval_fmt_005fparam_005f5 != 1) {
/* 3764 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3767 */     if (_jspx_th_fmt_005fparam_005f5.doEndTag() == 5) {
/* 3768 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f5);
/* 3769 */       return true;
/*      */     }
/* 3771 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f5);
/* 3772 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_fmt_005fparam_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3777 */     PageContext pageContext = _jspx_page_context;
/* 3778 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3780 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3781 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3782 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_fmt_005fparam_005f5);
/*      */     
/* 3784 */     _jspx_th_c_005fout_005f8.setValue("${paramtemplatetypestr}");
/* 3785 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3786 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3787 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3788 */       return true;
/*      */     }
/* 3790 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3791 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3796 */     PageContext pageContext = _jspx_page_context;
/* 3797 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3799 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 3800 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 3801 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 3803 */     _jspx_th_fmt_005fmessage_005f8.setKey("am.webclient.processtemplate.enterprocessname");
/* 3804 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 3805 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 3806 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3807 */         out = _jspx_page_context.pushBody();
/* 3808 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 3809 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3812 */         if (_jspx_meth_fmt_005fparam_005f6(_jspx_th_fmt_005fmessage_005f8, _jspx_page_context))
/* 3813 */           return true;
/* 3814 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 3815 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3818 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3819 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3822 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 3823 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3824 */       return true;
/*      */     }
/* 3826 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3827 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f6(JspTag _jspx_th_fmt_005fmessage_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3832 */     PageContext pageContext = _jspx_page_context;
/* 3833 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3835 */     ParamTag _jspx_th_fmt_005fparam_005f6 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 3836 */     _jspx_th_fmt_005fparam_005f6.setPageContext(_jspx_page_context);
/* 3837 */     _jspx_th_fmt_005fparam_005f6.setParent((Tag)_jspx_th_fmt_005fmessage_005f8);
/* 3838 */     int _jspx_eval_fmt_005fparam_005f6 = _jspx_th_fmt_005fparam_005f6.doStartTag();
/* 3839 */     if (_jspx_eval_fmt_005fparam_005f6 != 0) {
/* 3840 */       if (_jspx_eval_fmt_005fparam_005f6 != 1) {
/* 3841 */         out = _jspx_page_context.pushBody();
/* 3842 */         _jspx_th_fmt_005fparam_005f6.setBodyContent((BodyContent)out);
/* 3843 */         _jspx_th_fmt_005fparam_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3846 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_fmt_005fparam_005f6, _jspx_page_context))
/* 3847 */           return true;
/* 3848 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f6.doAfterBody();
/* 3849 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3852 */       if (_jspx_eval_fmt_005fparam_005f6 != 1) {
/* 3853 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3856 */     if (_jspx_th_fmt_005fparam_005f6.doEndTag() == 5) {
/* 3857 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f6);
/* 3858 */       return true;
/*      */     }
/* 3860 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f6);
/* 3861 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_fmt_005fparam_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3866 */     PageContext pageContext = _jspx_page_context;
/* 3867 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3869 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3870 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3871 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_fmt_005fparam_005f6);
/*      */     
/* 3873 */     _jspx_th_c_005fout_005f9.setValue("${paramtemplatetypestr}");
/* 3874 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3875 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3876 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3877 */       return true;
/*      */     }
/* 3879 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3880 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3885 */     PageContext pageContext = _jspx_page_context;
/* 3886 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3888 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3889 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3890 */     _jspx_th_c_005fout_005f10.setParent(null);
/*      */     
/* 3892 */     _jspx_th_c_005fout_005f10.setValue("${param.templatetype}");
/* 3893 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3894 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3895 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3896 */       return true;
/*      */     }
/* 3898 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3899 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3904 */     PageContext pageContext = _jspx_page_context;
/* 3905 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3907 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 3908 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 3909 */     _jspx_th_fmt_005fmessage_005f9.setParent(null);
/*      */     
/* 3911 */     _jspx_th_fmt_005fmessage_005f9.setKey("am.webclient.template.processadd");
/* 3912 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 3913 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 3914 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 3915 */         out = _jspx_page_context.pushBody();
/* 3916 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 3917 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3920 */         if (_jspx_meth_fmt_005fparam_005f7(_jspx_th_fmt_005fmessage_005f9, _jspx_page_context))
/* 3921 */           return true;
/* 3922 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 3923 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3926 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 3927 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3930 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 3931 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3932 */       return true;
/*      */     }
/* 3934 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3935 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f7(JspTag _jspx_th_fmt_005fmessage_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3940 */     PageContext pageContext = _jspx_page_context;
/* 3941 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3943 */     ParamTag _jspx_th_fmt_005fparam_005f7 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 3944 */     _jspx_th_fmt_005fparam_005f7.setPageContext(_jspx_page_context);
/* 3945 */     _jspx_th_fmt_005fparam_005f7.setParent((Tag)_jspx_th_fmt_005fmessage_005f9);
/* 3946 */     int _jspx_eval_fmt_005fparam_005f7 = _jspx_th_fmt_005fparam_005f7.doStartTag();
/* 3947 */     if (_jspx_eval_fmt_005fparam_005f7 != 0) {
/* 3948 */       if (_jspx_eval_fmt_005fparam_005f7 != 1) {
/* 3949 */         out = _jspx_page_context.pushBody();
/* 3950 */         _jspx_th_fmt_005fparam_005f7.setBodyContent((BodyContent)out);
/* 3951 */         _jspx_th_fmt_005fparam_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3954 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_fmt_005fparam_005f7, _jspx_page_context))
/* 3955 */           return true;
/* 3956 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f7.doAfterBody();
/* 3957 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3960 */       if (_jspx_eval_fmt_005fparam_005f7 != 1) {
/* 3961 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3964 */     if (_jspx_th_fmt_005fparam_005f7.doEndTag() == 5) {
/* 3965 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f7);
/* 3966 */       return true;
/*      */     }
/* 3968 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f7);
/* 3969 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_fmt_005fparam_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3974 */     PageContext pageContext = _jspx_page_context;
/* 3975 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3977 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3978 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 3979 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_fmt_005fparam_005f7);
/*      */     
/* 3981 */     _jspx_th_c_005fout_005f11.setValue("${paramtemplatetypestr}");
/* 3982 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 3983 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 3984 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3985 */       return true;
/*      */     }
/* 3987 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3988 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3993 */     PageContext pageContext = _jspx_page_context;
/* 3994 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3996 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3997 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 3998 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/* 3999 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 4000 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 4001 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 4002 */         out = _jspx_page_context.pushBody();
/* 4003 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 4004 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4007 */         out.write("am.windowsservices.action.service.select.popup.title");
/* 4008 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 4009 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4012 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 4013 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4016 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 4017 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 4018 */       return true;
/*      */     }
/* 4020 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 4021 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4026 */     PageContext pageContext = _jspx_page_context;
/* 4027 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4029 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4030 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 4031 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/* 4032 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 4033 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 4034 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 4035 */         out = _jspx_page_context.pushBody();
/* 4036 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 4037 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4040 */         out.write("am.windowsservices.action.service.select.popup.title");
/* 4041 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 4042 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4045 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 4046 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4049 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 4050 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 4051 */       return true;
/*      */     }
/* 4053 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 4054 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4059 */     PageContext pageContext = _jspx_page_context;
/* 4060 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4062 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4063 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 4064 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/* 4065 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 4066 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 4067 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 4068 */         out = _jspx_page_context.pushBody();
/* 4069 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 4070 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4073 */         out.write("am.windowsservices.action.service.select.popup.server");
/* 4074 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 4075 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4078 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 4079 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4082 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 4083 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 4084 */       return true;
/*      */     }
/* 4086 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 4087 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4092 */     PageContext pageContext = _jspx_page_context;
/* 4093 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4095 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4096 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 4097 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/* 4098 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 4099 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 4100 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 4101 */         out = _jspx_page_context.pushBody();
/* 4102 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 4103 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4106 */         out.write("am.windowsservices.action.service.select.popup.template");
/* 4107 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 4108 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4111 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 4112 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4115 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 4116 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 4117 */       return true;
/*      */     }
/* 4119 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 4120 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4125 */     PageContext pageContext = _jspx_page_context;
/* 4126 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4128 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4129 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 4130 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 4131 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 4132 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 4134 */         out.write(10);
/* 4135 */         if (_jspx_meth_c_005fif_005f6(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/* 4136 */           return true;
/* 4137 */         out.write(10);
/* 4138 */         if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/* 4139 */           return true;
/* 4140 */         out.write(10);
/* 4141 */         if (_jspx_meth_c_005fchoose_005f5(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/* 4142 */           return true;
/* 4143 */         out.write("\n\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"55\" class=\"darkheaderbg\">\n  <tr>\n  ");
/* 4144 */         if (_jspx_meth_c_005fchoose_005f6(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/* 4145 */           return true;
/* 4146 */         out.write("\n  </tr>\n</table>   \n     \n<table width=\"98%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"  align=\"center\" class=\"lrtbdarkborder\" style=\"margin-top:10px;margin-left:9px;\">\n        <tr>\n      ");
/* 4147 */         if (_jspx_meth_c_005fchoose_005f7(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/* 4148 */           return true;
/* 4149 */         out.write("\n        </tr>        \n        \n        <tr>\n        <td colspan=\"4\">\n                <div id=\"manualprocessdetail\" styple=\"display:block\">\n                        ");
/* 4150 */         if (_jspx_meth_c_005fimport_005f0(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/* 4151 */           return true;
/* 4152 */         out.write("\n                </div>\n        </td>\n        </tr>\n        <tr>\n        <td colspan=\"4\" >\n\n        <table cellpadding=\"0\" cellspacing=\"0\"  width=\"100%\" border=\"0\">\n        <tr>\n        <td><div id=\"autoprocessdetail\" style=\"display:none\"></td>\n        </tr>\n        </table>\n\n  </div>\n        </td></tr>\n</table>\n\n");
/* 4153 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 4154 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4158 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 4159 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 4160 */       return true;
/*      */     }
/* 4162 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 4163 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4168 */     PageContext pageContext = _jspx_page_context;
/* 4169 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4171 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4172 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 4173 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 4175 */     _jspx_th_c_005fif_005f6.setTest("${param.templatetype == PROCESSTEMPLATE}");
/* 4176 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 4177 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 4179 */         out.write(10);
/* 4180 */         if (_jspx_meth_fmt_005fmessage_005f14(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 4181 */           return true;
/* 4182 */         out.write(10);
/* 4183 */         if (_jspx_meth_fmt_005fmessage_005f15(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 4184 */           return true;
/* 4185 */         out.write(10);
/* 4186 */         out.write(10);
/* 4187 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 4188 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4192 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 4193 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4194 */       return true;
/*      */     }
/* 4196 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4197 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4202 */     PageContext pageContext = _jspx_page_context;
/* 4203 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4205 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody.get(MessageTag.class);
/* 4206 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 4207 */     _jspx_th_fmt_005fmessage_005f14.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4209 */     _jspx_th_fmt_005fmessage_005f14.setVar("paramtemplatetypestr");
/*      */     
/* 4211 */     _jspx_th_fmt_005fmessage_005f14.setScope("request");
/*      */     
/* 4213 */     _jspx_th_fmt_005fmessage_005f14.setKey("am.webclient.templatetype.process");
/* 4214 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 4215 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 4216 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 4217 */       return true;
/*      */     }
/* 4219 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 4220 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4225 */     PageContext pageContext = _jspx_page_context;
/* 4226 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4228 */     MessageTag _jspx_th_fmt_005fmessage_005f15 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody.get(MessageTag.class);
/* 4229 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 4230 */     _jspx_th_fmt_005fmessage_005f15.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4232 */     _jspx_th_fmt_005fmessage_005f15.setVar("lowerparamtemplatetypestr");
/*      */     
/* 4234 */     _jspx_th_fmt_005fmessage_005f15.setScope("request");
/*      */     
/* 4236 */     _jspx_th_fmt_005fmessage_005f15.setKey("am.webclient.processtemplate.lowercaseprocess");
/* 4237 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 4238 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 4239 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 4240 */       return true;
/*      */     }
/* 4242 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 4243 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4248 */     PageContext pageContext = _jspx_page_context;
/* 4249 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4251 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4252 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 4253 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 4255 */     _jspx_th_c_005fif_005f7.setTest("${param.templatetype == SERVICETEMPLATE}");
/* 4256 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 4257 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 4259 */         out.write(10);
/* 4260 */         if (_jspx_meth_fmt_005fmessage_005f16(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 4261 */           return true;
/* 4262 */         out.write(10);
/* 4263 */         if (_jspx_meth_fmt_005fmessage_005f17(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 4264 */           return true;
/* 4265 */         out.write(10);
/* 4266 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 4267 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4271 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 4272 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4273 */       return true;
/*      */     }
/* 4275 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4276 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f16(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4281 */     PageContext pageContext = _jspx_page_context;
/* 4282 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4284 */     MessageTag _jspx_th_fmt_005fmessage_005f16 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody.get(MessageTag.class);
/* 4285 */     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 4286 */     _jspx_th_fmt_005fmessage_005f16.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4288 */     _jspx_th_fmt_005fmessage_005f16.setVar("paramtemplatetypestr");
/*      */     
/* 4290 */     _jspx_th_fmt_005fmessage_005f16.setScope("request");
/*      */     
/* 4292 */     _jspx_th_fmt_005fmessage_005f16.setKey("am.webclient.templatetype.service");
/* 4293 */     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 4294 */     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 4295 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 4296 */       return true;
/*      */     }
/* 4298 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 4299 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f17(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4304 */     PageContext pageContext = _jspx_page_context;
/* 4305 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4307 */     MessageTag _jspx_th_fmt_005fmessage_005f17 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody.get(MessageTag.class);
/* 4308 */     _jspx_th_fmt_005fmessage_005f17.setPageContext(_jspx_page_context);
/* 4309 */     _jspx_th_fmt_005fmessage_005f17.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4311 */     _jspx_th_fmt_005fmessage_005f17.setVar("lowerparamtemplatetypestr");
/*      */     
/* 4313 */     _jspx_th_fmt_005fmessage_005f17.setScope("request");
/*      */     
/* 4315 */     _jspx_th_fmt_005fmessage_005f17.setKey("am.webclient.servicetemplate.lowercaseservice");
/* 4316 */     int _jspx_eval_fmt_005fmessage_005f17 = _jspx_th_fmt_005fmessage_005f17.doStartTag();
/* 4317 */     if (_jspx_th_fmt_005fmessage_005f17.doEndTag() == 5) {
/* 4318 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 4319 */       return true;
/*      */     }
/* 4321 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fvar_005fscope_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 4322 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f5(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4327 */     PageContext pageContext = _jspx_page_context;
/* 4328 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4330 */     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4331 */     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 4332 */     _jspx_th_c_005fchoose_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/* 4333 */     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 4334 */     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */       for (;;) {
/* 4336 */         out.write(10);
/* 4337 */         out.write(32);
/* 4338 */         out.write(32);
/* 4339 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 4340 */           return true;
/* 4341 */         out.write(10);
/* 4342 */         out.write(32);
/* 4343 */         out.write(32);
/* 4344 */         if (_jspx_meth_c_005fotherwise_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 4345 */           return true;
/* 4346 */         out.write(10);
/* 4347 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 4348 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4352 */     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 4353 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 4354 */       return true;
/*      */     }
/* 4356 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 4357 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4362 */     PageContext pageContext = _jspx_page_context;
/* 4363 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4365 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4366 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 4367 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 4369 */     _jspx_th_c_005fwhen_005f5.setTest("${empty param.editprocess}");
/* 4370 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 4371 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 4373 */         out.write("\n  <title>");
/* 4374 */         if (_jspx_meth_fmt_005fmessage_005f18(_jspx_th_c_005fwhen_005f5, _jspx_page_context))
/* 4375 */           return true;
/* 4376 */         out.write("</title>");
/* 4377 */         out.write(10);
/* 4378 */         out.write(32);
/* 4379 */         out.write(32);
/* 4380 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 4381 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4385 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 4386 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 4387 */       return true;
/*      */     }
/* 4389 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 4390 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f18(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4395 */     PageContext pageContext = _jspx_page_context;
/* 4396 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4398 */     MessageTag _jspx_th_fmt_005fmessage_005f18 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 4399 */     _jspx_th_fmt_005fmessage_005f18.setPageContext(_jspx_page_context);
/* 4400 */     _jspx_th_fmt_005fmessage_005f18.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 4402 */     _jspx_th_fmt_005fmessage_005f18.setKey("am.webclient.processtemplate.associateprocess");
/* 4403 */     int _jspx_eval_fmt_005fmessage_005f18 = _jspx_th_fmt_005fmessage_005f18.doStartTag();
/* 4404 */     if (_jspx_eval_fmt_005fmessage_005f18 != 0) {
/* 4405 */       if (_jspx_eval_fmt_005fmessage_005f18 != 1) {
/* 4406 */         out = _jspx_page_context.pushBody();
/* 4407 */         _jspx_th_fmt_005fmessage_005f18.setBodyContent((BodyContent)out);
/* 4408 */         _jspx_th_fmt_005fmessage_005f18.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4411 */         if (_jspx_meth_fmt_005fparam_005f8(_jspx_th_fmt_005fmessage_005f18, _jspx_page_context))
/* 4412 */           return true;
/* 4413 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f18.doAfterBody();
/* 4414 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4417 */       if (_jspx_eval_fmt_005fmessage_005f18 != 1) {
/* 4418 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4421 */     if (_jspx_th_fmt_005fmessage_005f18.doEndTag() == 5) {
/* 4422 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 4423 */       return true;
/*      */     }
/* 4425 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 4426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f8(JspTag _jspx_th_fmt_005fmessage_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4431 */     PageContext pageContext = _jspx_page_context;
/* 4432 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4434 */     ParamTag _jspx_th_fmt_005fparam_005f8 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 4435 */     _jspx_th_fmt_005fparam_005f8.setPageContext(_jspx_page_context);
/* 4436 */     _jspx_th_fmt_005fparam_005f8.setParent((Tag)_jspx_th_fmt_005fmessage_005f18);
/* 4437 */     int _jspx_eval_fmt_005fparam_005f8 = _jspx_th_fmt_005fparam_005f8.doStartTag();
/* 4438 */     if (_jspx_eval_fmt_005fparam_005f8 != 0) {
/* 4439 */       if (_jspx_eval_fmt_005fparam_005f8 != 1) {
/* 4440 */         out = _jspx_page_context.pushBody();
/* 4441 */         _jspx_th_fmt_005fparam_005f8.setBodyContent((BodyContent)out);
/* 4442 */         _jspx_th_fmt_005fparam_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4445 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_fmt_005fparam_005f8, _jspx_page_context))
/* 4446 */           return true;
/* 4447 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f8.doAfterBody();
/* 4448 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4451 */       if (_jspx_eval_fmt_005fparam_005f8 != 1) {
/* 4452 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4455 */     if (_jspx_th_fmt_005fparam_005f8.doEndTag() == 5) {
/* 4456 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f8);
/* 4457 */       return true;
/*      */     }
/* 4459 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f8);
/* 4460 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_fmt_005fparam_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4465 */     PageContext pageContext = _jspx_page_context;
/* 4466 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4468 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4469 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4470 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_fmt_005fparam_005f8);
/*      */     
/* 4472 */     _jspx_th_c_005fout_005f12.setValue("${paramtemplatetypestr}");
/* 4473 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4474 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4475 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4476 */       return true;
/*      */     }
/* 4478 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4479 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4484 */     PageContext pageContext = _jspx_page_context;
/* 4485 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4487 */     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4488 */     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 4489 */     _jspx_th_c_005fotherwise_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 4490 */     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 4491 */     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */       for (;;) {
/* 4493 */         out.write("\n  <title>");
/* 4494 */         if (_jspx_meth_fmt_005fmessage_005f19(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/* 4495 */           return true;
/* 4496 */         out.write("</title>");
/* 4497 */         out.write(10);
/* 4498 */         out.write(32);
/* 4499 */         out.write(32);
/* 4500 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 4501 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4505 */     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 4506 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 4507 */       return true;
/*      */     }
/* 4509 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 4510 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f19(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4515 */     PageContext pageContext = _jspx_page_context;
/* 4516 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4518 */     MessageTag _jspx_th_fmt_005fmessage_005f19 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 4519 */     _jspx_th_fmt_005fmessage_005f19.setPageContext(_jspx_page_context);
/* 4520 */     _jspx_th_fmt_005fmessage_005f19.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 4522 */     _jspx_th_fmt_005fmessage_005f19.setKey("am.webclient.processtemplate.editprocess");
/* 4523 */     int _jspx_eval_fmt_005fmessage_005f19 = _jspx_th_fmt_005fmessage_005f19.doStartTag();
/* 4524 */     if (_jspx_eval_fmt_005fmessage_005f19 != 0) {
/* 4525 */       if (_jspx_eval_fmt_005fmessage_005f19 != 1) {
/* 4526 */         out = _jspx_page_context.pushBody();
/* 4527 */         _jspx_th_fmt_005fmessage_005f19.setBodyContent((BodyContent)out);
/* 4528 */         _jspx_th_fmt_005fmessage_005f19.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4531 */         if (_jspx_meth_fmt_005fparam_005f9(_jspx_th_fmt_005fmessage_005f19, _jspx_page_context))
/* 4532 */           return true;
/* 4533 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f19.doAfterBody();
/* 4534 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4537 */       if (_jspx_eval_fmt_005fmessage_005f19 != 1) {
/* 4538 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4541 */     if (_jspx_th_fmt_005fmessage_005f19.doEndTag() == 5) {
/* 4542 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 4543 */       return true;
/*      */     }
/* 4545 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 4546 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f9(JspTag _jspx_th_fmt_005fmessage_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4551 */     PageContext pageContext = _jspx_page_context;
/* 4552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4554 */     ParamTag _jspx_th_fmt_005fparam_005f9 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 4555 */     _jspx_th_fmt_005fparam_005f9.setPageContext(_jspx_page_context);
/* 4556 */     _jspx_th_fmt_005fparam_005f9.setParent((Tag)_jspx_th_fmt_005fmessage_005f19);
/* 4557 */     int _jspx_eval_fmt_005fparam_005f9 = _jspx_th_fmt_005fparam_005f9.doStartTag();
/* 4558 */     if (_jspx_eval_fmt_005fparam_005f9 != 0) {
/* 4559 */       if (_jspx_eval_fmt_005fparam_005f9 != 1) {
/* 4560 */         out = _jspx_page_context.pushBody();
/* 4561 */         _jspx_th_fmt_005fparam_005f9.setBodyContent((BodyContent)out);
/* 4562 */         _jspx_th_fmt_005fparam_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4565 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_fmt_005fparam_005f9, _jspx_page_context))
/* 4566 */           return true;
/* 4567 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f9.doAfterBody();
/* 4568 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4571 */       if (_jspx_eval_fmt_005fparam_005f9 != 1) {
/* 4572 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4575 */     if (_jspx_th_fmt_005fparam_005f9.doEndTag() == 5) {
/* 4576 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f9);
/* 4577 */       return true;
/*      */     }
/* 4579 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f9);
/* 4580 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_fmt_005fparam_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4585 */     PageContext pageContext = _jspx_page_context;
/* 4586 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4588 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4589 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4590 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_fmt_005fparam_005f9);
/*      */     
/* 4592 */     _jspx_th_c_005fout_005f13.setValue("${paramtemplatetypestr}");
/* 4593 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4594 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4595 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4596 */       return true;
/*      */     }
/* 4598 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4599 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f6(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4604 */     PageContext pageContext = _jspx_page_context;
/* 4605 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4607 */     ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4608 */     _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 4609 */     _jspx_th_c_005fchoose_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/* 4610 */     int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 4611 */     if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */       for (;;) {
/* 4613 */         out.write("\n    ");
/* 4614 */         if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f6, _jspx_page_context))
/* 4615 */           return true;
/* 4616 */         out.write("\n    ");
/* 4617 */         if (_jspx_meth_c_005fotherwise_005f6(_jspx_th_c_005fchoose_005f6, _jspx_page_context))
/* 4618 */           return true;
/* 4619 */         out.write(10);
/* 4620 */         out.write(32);
/* 4621 */         out.write(32);
/* 4622 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 4623 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4627 */     if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 4628 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 4629 */       return true;
/*      */     }
/* 4631 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 4632 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4637 */     PageContext pageContext = _jspx_page_context;
/* 4638 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4640 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4641 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 4642 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/*      */     
/* 4644 */     _jspx_th_c_005fwhen_005f6.setTest("${empty param.editprocess}");
/* 4645 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 4646 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/* 4648 */         out.write("\n    <td class=\"headingboldwhite\" colspan=\"4\" style=\"height:40px;\">&nbsp;");
/* 4649 */         if (_jspx_meth_fmt_005fmessage_005f20(_jspx_th_c_005fwhen_005f6, _jspx_page_context))
/* 4650 */           return true;
/* 4651 */         out.write("</td>");
/* 4652 */         out.write("\n    ");
/* 4653 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 4654 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4658 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 4659 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 4660 */       return true;
/*      */     }
/* 4662 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 4663 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f20(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4668 */     PageContext pageContext = _jspx_page_context;
/* 4669 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4671 */     MessageTag _jspx_th_fmt_005fmessage_005f20 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 4672 */     _jspx_th_fmt_005fmessage_005f20.setPageContext(_jspx_page_context);
/* 4673 */     _jspx_th_fmt_005fmessage_005f20.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 4675 */     _jspx_th_fmt_005fmessage_005f20.setKey("am.webclient.processtemplate.associateprocess");
/* 4676 */     int _jspx_eval_fmt_005fmessage_005f20 = _jspx_th_fmt_005fmessage_005f20.doStartTag();
/* 4677 */     if (_jspx_eval_fmt_005fmessage_005f20 != 0) {
/* 4678 */       if (_jspx_eval_fmt_005fmessage_005f20 != 1) {
/* 4679 */         out = _jspx_page_context.pushBody();
/* 4680 */         _jspx_th_fmt_005fmessage_005f20.setBodyContent((BodyContent)out);
/* 4681 */         _jspx_th_fmt_005fmessage_005f20.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4684 */         if (_jspx_meth_fmt_005fparam_005f10(_jspx_th_fmt_005fmessage_005f20, _jspx_page_context))
/* 4685 */           return true;
/* 4686 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f20.doAfterBody();
/* 4687 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4690 */       if (_jspx_eval_fmt_005fmessage_005f20 != 1) {
/* 4691 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4694 */     if (_jspx_th_fmt_005fmessage_005f20.doEndTag() == 5) {
/* 4695 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 4696 */       return true;
/*      */     }
/* 4698 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 4699 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f10(JspTag _jspx_th_fmt_005fmessage_005f20, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4704 */     PageContext pageContext = _jspx_page_context;
/* 4705 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4707 */     ParamTag _jspx_th_fmt_005fparam_005f10 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 4708 */     _jspx_th_fmt_005fparam_005f10.setPageContext(_jspx_page_context);
/* 4709 */     _jspx_th_fmt_005fparam_005f10.setParent((Tag)_jspx_th_fmt_005fmessage_005f20);
/* 4710 */     int _jspx_eval_fmt_005fparam_005f10 = _jspx_th_fmt_005fparam_005f10.doStartTag();
/* 4711 */     if (_jspx_eval_fmt_005fparam_005f10 != 0) {
/* 4712 */       if (_jspx_eval_fmt_005fparam_005f10 != 1) {
/* 4713 */         out = _jspx_page_context.pushBody();
/* 4714 */         _jspx_th_fmt_005fparam_005f10.setBodyContent((BodyContent)out);
/* 4715 */         _jspx_th_fmt_005fparam_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4718 */         if (_jspx_meth_c_005fout_005f14(_jspx_th_fmt_005fparam_005f10, _jspx_page_context))
/* 4719 */           return true;
/* 4720 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f10.doAfterBody();
/* 4721 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4724 */       if (_jspx_eval_fmt_005fparam_005f10 != 1) {
/* 4725 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4728 */     if (_jspx_th_fmt_005fparam_005f10.doEndTag() == 5) {
/* 4729 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f10);
/* 4730 */       return true;
/*      */     }
/* 4732 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f10);
/* 4733 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_fmt_005fparam_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4738 */     PageContext pageContext = _jspx_page_context;
/* 4739 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4741 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4742 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4743 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_fmt_005fparam_005f10);
/*      */     
/* 4745 */     _jspx_th_c_005fout_005f14.setValue("${paramtemplatetypestr}");
/* 4746 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4747 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4748 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4749 */       return true;
/*      */     }
/* 4751 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4752 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f6(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4757 */     PageContext pageContext = _jspx_page_context;
/* 4758 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4760 */     OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4761 */     _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 4762 */     _jspx_th_c_005fotherwise_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/* 4763 */     int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 4764 */     if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */       for (;;) {
/* 4766 */         out.write("\n    <td class=\"headingboldwhite\" colspan=\"4\" style=\"height:40px;\">&nbsp;");
/* 4767 */         if (_jspx_meth_fmt_005fmessage_005f21(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/* 4768 */           return true;
/* 4769 */         out.write("</td>");
/* 4770 */         out.write("\n    ");
/* 4771 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 4772 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4776 */     if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 4777 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 4778 */       return true;
/*      */     }
/* 4780 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 4781 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f21(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4786 */     PageContext pageContext = _jspx_page_context;
/* 4787 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4789 */     MessageTag _jspx_th_fmt_005fmessage_005f21 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 4790 */     _jspx_th_fmt_005fmessage_005f21.setPageContext(_jspx_page_context);
/* 4791 */     _jspx_th_fmt_005fmessage_005f21.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 4793 */     _jspx_th_fmt_005fmessage_005f21.setKey("am.webclient.processtemplate.editprocess");
/* 4794 */     int _jspx_eval_fmt_005fmessage_005f21 = _jspx_th_fmt_005fmessage_005f21.doStartTag();
/* 4795 */     if (_jspx_eval_fmt_005fmessage_005f21 != 0) {
/* 4796 */       if (_jspx_eval_fmt_005fmessage_005f21 != 1) {
/* 4797 */         out = _jspx_page_context.pushBody();
/* 4798 */         _jspx_th_fmt_005fmessage_005f21.setBodyContent((BodyContent)out);
/* 4799 */         _jspx_th_fmt_005fmessage_005f21.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4802 */         if (_jspx_meth_fmt_005fparam_005f11(_jspx_th_fmt_005fmessage_005f21, _jspx_page_context))
/* 4803 */           return true;
/* 4804 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f21.doAfterBody();
/* 4805 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4808 */       if (_jspx_eval_fmt_005fmessage_005f21 != 1) {
/* 4809 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4812 */     if (_jspx_th_fmt_005fmessage_005f21.doEndTag() == 5) {
/* 4813 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 4814 */       return true;
/*      */     }
/* 4816 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 4817 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f11(JspTag _jspx_th_fmt_005fmessage_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4822 */     PageContext pageContext = _jspx_page_context;
/* 4823 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4825 */     ParamTag _jspx_th_fmt_005fparam_005f11 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 4826 */     _jspx_th_fmt_005fparam_005f11.setPageContext(_jspx_page_context);
/* 4827 */     _jspx_th_fmt_005fparam_005f11.setParent((Tag)_jspx_th_fmt_005fmessage_005f21);
/* 4828 */     int _jspx_eval_fmt_005fparam_005f11 = _jspx_th_fmt_005fparam_005f11.doStartTag();
/* 4829 */     if (_jspx_eval_fmt_005fparam_005f11 != 0) {
/* 4830 */       if (_jspx_eval_fmt_005fparam_005f11 != 1) {
/* 4831 */         out = _jspx_page_context.pushBody();
/* 4832 */         _jspx_th_fmt_005fparam_005f11.setBodyContent((BodyContent)out);
/* 4833 */         _jspx_th_fmt_005fparam_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4836 */         if (_jspx_meth_c_005fout_005f15(_jspx_th_fmt_005fparam_005f11, _jspx_page_context))
/* 4837 */           return true;
/* 4838 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f11.doAfterBody();
/* 4839 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4842 */       if (_jspx_eval_fmt_005fparam_005f11 != 1) {
/* 4843 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4846 */     if (_jspx_th_fmt_005fparam_005f11.doEndTag() == 5) {
/* 4847 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f11);
/* 4848 */       return true;
/*      */     }
/* 4850 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f11);
/* 4851 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_fmt_005fparam_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4856 */     PageContext pageContext = _jspx_page_context;
/* 4857 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4859 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4860 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4861 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_fmt_005fparam_005f11);
/*      */     
/* 4863 */     _jspx_th_c_005fout_005f15.setValue("${paramtemplatetypestr}");
/* 4864 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4865 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4866 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4867 */       return true;
/*      */     }
/* 4869 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4870 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f7(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4875 */     PageContext pageContext = _jspx_page_context;
/* 4876 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4878 */     ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4879 */     _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 4880 */     _jspx_th_c_005fchoose_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/* 4881 */     int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 4882 */     if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */       for (;;) {
/* 4884 */         out.write("\n      ");
/* 4885 */         if (_jspx_meth_c_005fwhen_005f7(_jspx_th_c_005fchoose_005f7, _jspx_page_context))
/* 4886 */           return true;
/* 4887 */         out.write("\n      ");
/* 4888 */         if (_jspx_meth_c_005fotherwise_005f7(_jspx_th_c_005fchoose_005f7, _jspx_page_context))
/* 4889 */           return true;
/* 4890 */         out.write("\n      ");
/* 4891 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 4892 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4896 */     if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 4897 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 4898 */       return true;
/*      */     }
/* 4900 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 4901 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f7(JspTag _jspx_th_c_005fchoose_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4906 */     PageContext pageContext = _jspx_page_context;
/* 4907 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4909 */     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4910 */     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 4911 */     _jspx_th_c_005fwhen_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f7);
/*      */     
/* 4913 */     _jspx_th_c_005fwhen_005f7.setTest("${empty param.editprocess}");
/* 4914 */     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 4915 */     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */       for (;;) {
/* 4917 */         out.write("\n      ");
/* 4918 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fwhen_005f7, _jspx_page_context))
/* 4919 */           return true;
/* 4920 */         out.write("\n        </tr>\n        <tr>\n          <td width=\"2%\"class=\"bodytext\"> &nbsp;</td>\n          <td height=\"29\" width=\"29%\" class=\"bodytext\" valig=\"middle\" style=\"height:50px;\">\n      <input type=\"radio\" id=\"manualprocessentry\" name=\"processchooser\" value=\"manualprocessentry\"  onclick=\"javascript:getProcessChooser(this)\" checked/> ");
/* 4921 */         if (_jspx_meth_fmt_005fmessage_005f22(_jspx_th_c_005fwhen_005f7, _jspx_page_context))
/* 4922 */           return true;
/* 4923 */         out.write("</td> ");
/* 4924 */         out.write("\n          <td height=\"29\"  width=\"69%\" class=\"bodytext\" ><input type=\"radio\" id=\"autoprocessentry\" name=\"processchooser\" value=\"autoprocessentry\" onclick=\"javascript:getProcessChooser(this)\"/>");
/* 4925 */         if (_jspx_meth_fmt_005fmessage_005f23(_jspx_th_c_005fwhen_005f7, _jspx_page_context))
/* 4926 */           return true;
/* 4927 */         out.write("</td> ");
/* 4928 */         out.write("\n      ");
/* 4929 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 4930 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4934 */     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 4935 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 4936 */       return true;
/*      */     }
/* 4938 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 4939 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fwhen_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4944 */     PageContext pageContext = _jspx_page_context;
/* 4945 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4947 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4948 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4949 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f7);
/*      */     
/* 4951 */     _jspx_th_c_005fset_005f0.setVar("params");
/*      */     
/* 4953 */     _jspx_th_c_005fset_005f0.setValue("templatetype=${param.templatetype}&templatetypestr=${paramtemplatetypestr}");
/* 4954 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4955 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4956 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4957 */       return true;
/*      */     }
/* 4959 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4960 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f22(JspTag _jspx_th_c_005fwhen_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4965 */     PageContext pageContext = _jspx_page_context;
/* 4966 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4968 */     MessageTag _jspx_th_fmt_005fmessage_005f22 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 4969 */     _jspx_th_fmt_005fmessage_005f22.setPageContext(_jspx_page_context);
/* 4970 */     _jspx_th_fmt_005fmessage_005f22.setParent((Tag)_jspx_th_c_005fwhen_005f7);
/*      */     
/* 4972 */     _jspx_th_fmt_005fmessage_005f22.setKey("am.webclient.processtemplate.manualchooser");
/* 4973 */     int _jspx_eval_fmt_005fmessage_005f22 = _jspx_th_fmt_005fmessage_005f22.doStartTag();
/* 4974 */     if (_jspx_eval_fmt_005fmessage_005f22 != 0) {
/* 4975 */       if (_jspx_eval_fmt_005fmessage_005f22 != 1) {
/* 4976 */         out = _jspx_page_context.pushBody();
/* 4977 */         _jspx_th_fmt_005fmessage_005f22.setBodyContent((BodyContent)out);
/* 4978 */         _jspx_th_fmt_005fmessage_005f22.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4981 */         if (_jspx_meth_fmt_005fparam_005f12(_jspx_th_fmt_005fmessage_005f22, _jspx_page_context))
/* 4982 */           return true;
/* 4983 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f22.doAfterBody();
/* 4984 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4987 */       if (_jspx_eval_fmt_005fmessage_005f22 != 1) {
/* 4988 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4991 */     if (_jspx_th_fmt_005fmessage_005f22.doEndTag() == 5) {
/* 4992 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f22);
/* 4993 */       return true;
/*      */     }
/* 4995 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f22);
/* 4996 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f12(JspTag _jspx_th_fmt_005fmessage_005f22, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5001 */     PageContext pageContext = _jspx_page_context;
/* 5002 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5004 */     ParamTag _jspx_th_fmt_005fparam_005f12 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 5005 */     _jspx_th_fmt_005fparam_005f12.setPageContext(_jspx_page_context);
/* 5006 */     _jspx_th_fmt_005fparam_005f12.setParent((Tag)_jspx_th_fmt_005fmessage_005f22);
/* 5007 */     int _jspx_eval_fmt_005fparam_005f12 = _jspx_th_fmt_005fparam_005f12.doStartTag();
/* 5008 */     if (_jspx_eval_fmt_005fparam_005f12 != 0) {
/* 5009 */       if (_jspx_eval_fmt_005fparam_005f12 != 1) {
/* 5010 */         out = _jspx_page_context.pushBody();
/* 5011 */         _jspx_th_fmt_005fparam_005f12.setBodyContent((BodyContent)out);
/* 5012 */         _jspx_th_fmt_005fparam_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5015 */         if (_jspx_meth_c_005fout_005f16(_jspx_th_fmt_005fparam_005f12, _jspx_page_context))
/* 5016 */           return true;
/* 5017 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f12.doAfterBody();
/* 5018 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5021 */       if (_jspx_eval_fmt_005fparam_005f12 != 1) {
/* 5022 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5025 */     if (_jspx_th_fmt_005fparam_005f12.doEndTag() == 5) {
/* 5026 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f12);
/* 5027 */       return true;
/*      */     }
/* 5029 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f12);
/* 5030 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_fmt_005fparam_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5035 */     PageContext pageContext = _jspx_page_context;
/* 5036 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5038 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5039 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 5040 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_fmt_005fparam_005f12);
/*      */     
/* 5042 */     _jspx_th_c_005fout_005f16.setValue("${lowerparamtemplatetypestr}");
/* 5043 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 5044 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 5045 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5046 */       return true;
/*      */     }
/* 5048 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5049 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f23(JspTag _jspx_th_c_005fwhen_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5054 */     PageContext pageContext = _jspx_page_context;
/* 5055 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5057 */     MessageTag _jspx_th_fmt_005fmessage_005f23 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 5058 */     _jspx_th_fmt_005fmessage_005f23.setPageContext(_jspx_page_context);
/* 5059 */     _jspx_th_fmt_005fmessage_005f23.setParent((Tag)_jspx_th_c_005fwhen_005f7);
/*      */     
/* 5061 */     _jspx_th_fmt_005fmessage_005f23.setKey("am.webclient.processtemplate.autochooser");
/* 5062 */     int _jspx_eval_fmt_005fmessage_005f23 = _jspx_th_fmt_005fmessage_005f23.doStartTag();
/* 5063 */     if (_jspx_eval_fmt_005fmessage_005f23 != 0) {
/* 5064 */       if (_jspx_eval_fmt_005fmessage_005f23 != 1) {
/* 5065 */         out = _jspx_page_context.pushBody();
/* 5066 */         _jspx_th_fmt_005fmessage_005f23.setBodyContent((BodyContent)out);
/* 5067 */         _jspx_th_fmt_005fmessage_005f23.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5070 */         if (_jspx_meth_fmt_005fparam_005f13(_jspx_th_fmt_005fmessage_005f23, _jspx_page_context))
/* 5071 */           return true;
/* 5072 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f23.doAfterBody();
/* 5073 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5076 */       if (_jspx_eval_fmt_005fmessage_005f23 != 1) {
/* 5077 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5080 */     if (_jspx_th_fmt_005fmessage_005f23.doEndTag() == 5) {
/* 5081 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f23);
/* 5082 */       return true;
/*      */     }
/* 5084 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f23);
/* 5085 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f13(JspTag _jspx_th_fmt_005fmessage_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5090 */     PageContext pageContext = _jspx_page_context;
/* 5091 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5093 */     ParamTag _jspx_th_fmt_005fparam_005f13 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 5094 */     _jspx_th_fmt_005fparam_005f13.setPageContext(_jspx_page_context);
/* 5095 */     _jspx_th_fmt_005fparam_005f13.setParent((Tag)_jspx_th_fmt_005fmessage_005f23);
/* 5096 */     int _jspx_eval_fmt_005fparam_005f13 = _jspx_th_fmt_005fparam_005f13.doStartTag();
/* 5097 */     if (_jspx_eval_fmt_005fparam_005f13 != 0) {
/* 5098 */       if (_jspx_eval_fmt_005fparam_005f13 != 1) {
/* 5099 */         out = _jspx_page_context.pushBody();
/* 5100 */         _jspx_th_fmt_005fparam_005f13.setBodyContent((BodyContent)out);
/* 5101 */         _jspx_th_fmt_005fparam_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5104 */         if (_jspx_meth_c_005fout_005f17(_jspx_th_fmt_005fparam_005f13, _jspx_page_context))
/* 5105 */           return true;
/* 5106 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f13.doAfterBody();
/* 5107 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5110 */       if (_jspx_eval_fmt_005fparam_005f13 != 1) {
/* 5111 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5114 */     if (_jspx_th_fmt_005fparam_005f13.doEndTag() == 5) {
/* 5115 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f13);
/* 5116 */       return true;
/*      */     }
/* 5118 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f13);
/* 5119 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_fmt_005fparam_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5124 */     PageContext pageContext = _jspx_page_context;
/* 5125 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5127 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5128 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 5129 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_fmt_005fparam_005f13);
/*      */     
/* 5131 */     _jspx_th_c_005fout_005f17.setValue("${lowerparamtemplatetypestr}");
/* 5132 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 5133 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 5134 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5135 */       return true;
/*      */     }
/* 5137 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5138 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f7(JspTag _jspx_th_c_005fchoose_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5143 */     PageContext pageContext = _jspx_page_context;
/* 5144 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5146 */     OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5147 */     _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 5148 */     _jspx_th_c_005fotherwise_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f7);
/* 5149 */     int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 5150 */     if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */       for (;;) {
/* 5152 */         out.write("\n      ");
/* 5153 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fotherwise_005f7, _jspx_page_context))
/* 5154 */           return true;
/* 5155 */         out.write("\n      ");
/* 5156 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 5157 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5161 */     if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 5162 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 5163 */       return true;
/*      */     }
/* 5165 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 5166 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fotherwise_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5171 */     PageContext pageContext = _jspx_page_context;
/* 5172 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5174 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 5175 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 5176 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f7);
/*      */     
/* 5178 */     _jspx_th_c_005fset_005f1.setVar("params");
/*      */     
/* 5180 */     _jspx_th_c_005fset_005f1.setValue("templatetype=${param.templatetype}&templatetypestr=${paramtemplatetypestr}&editprocess=${param.editprocess}");
/* 5181 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 5182 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 5183 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 5184 */       return true;
/*      */     }
/* 5186 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 5187 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fimport_005f0(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5192 */     PageContext pageContext = _jspx_page_context;
/* 5193 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5195 */     ImportTag _jspx_th_c_005fimport_005f0 = (ImportTag)this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.get(ImportTag.class);
/* 5196 */     _jspx_th_c_005fimport_005f0.setPageContext(_jspx_page_context);
/* 5197 */     _jspx_th_c_005fimport_005f0.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 5199 */     _jspx_th_c_005fimport_005f0.setUrl("/jsp/ManualProcessAdder.jsp?${params}");
/* 5200 */     int[] _jspx_push_body_count_c_005fimport_005f0 = { 0 };
/*      */     try {
/* 5202 */       int _jspx_eval_c_005fimport_005f0 = _jspx_th_c_005fimport_005f0.doStartTag();
/* 5203 */       if (_jspx_th_c_005fimport_005f0.doEndTag() == 5)
/* 5204 */         return true;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5207 */         int tmp113_112 = 0; int[] tmp113_110 = _jspx_push_body_count_c_005fimport_005f0; int tmp115_114 = tmp113_110[tmp113_112];tmp113_110[tmp113_112] = (tmp115_114 - 1); if (tmp115_114 <= 0) break;
/* 5208 */         out = _jspx_page_context.popBody(); }
/* 5209 */       _jspx_th_c_005fimport_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 5211 */       _jspx_th_c_005fimport_005f0.doFinally();
/* 5212 */       this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.reuse(_jspx_th_c_005fimport_005f0);
/*      */     }
/* 5214 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\TemplateProcessChooser_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */