/*      */ package org.apache.jsp.jsp.includes;
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
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class restapi_005fcustomAttr_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   42 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   45 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   46 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   47 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   54 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   59 */     ArrayList list = null;
/*   60 */     StringBuffer sbf = new StringBuffer();
/*   61 */     ManagedApplication mo = new ManagedApplication();
/*   62 */     if (distinct)
/*      */     {
/*   64 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   68 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   71 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   73 */       ArrayList row = (ArrayList)list.get(i);
/*   74 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   75 */       if (distinct) {
/*   76 */         sbf.append(row.get(0));
/*      */       } else
/*   78 */         sbf.append(row.get(1));
/*   79 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   82 */     return sbf.toString(); }
/*      */   
/*   84 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   87 */     if (severity == null)
/*      */     {
/*   89 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   91 */     if (severity.equals("5"))
/*      */     {
/*   93 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*   95 */     if (severity.equals("1"))
/*      */     {
/*   97 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  102 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  109 */     if (severity == null)
/*      */     {
/*  111 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  113 */     if (severity.equals("1"))
/*      */     {
/*  115 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  117 */     if (severity.equals("4"))
/*      */     {
/*  119 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  121 */     if (severity.equals("5"))
/*      */     {
/*  123 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  128 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  134 */     if (severity == null)
/*      */     {
/*  136 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  138 */     if (severity.equals("5"))
/*      */     {
/*  140 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  142 */     if (severity.equals("1"))
/*      */     {
/*  144 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  148 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  154 */     if (severity == null)
/*      */     {
/*  156 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  158 */     if (severity.equals("1"))
/*      */     {
/*  160 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  162 */     if (severity.equals("4"))
/*      */     {
/*  164 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  166 */     if (severity.equals("5"))
/*      */     {
/*  168 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  172 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  178 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  184 */     if (severity == 5)
/*      */     {
/*  186 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  188 */     if (severity == 1)
/*      */     {
/*  190 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  195 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  201 */     if (severity == null)
/*      */     {
/*  203 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  205 */     if (severity.equals("5"))
/*      */     {
/*  207 */       if (isAvailability) {
/*  208 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  211 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  214 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  216 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  218 */     if (severity.equals("1"))
/*      */     {
/*  220 */       if (isAvailability) {
/*  221 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  224 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  231 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  238 */     if (severity == null)
/*      */     {
/*  240 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  242 */     if (severity.equals("5"))
/*      */     {
/*  244 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  246 */     if (severity.equals("4"))
/*      */     {
/*  248 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  250 */     if (severity.equals("1"))
/*      */     {
/*  252 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  257 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  263 */     if (severity == null)
/*      */     {
/*  265 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  267 */     if (severity.equals("5"))
/*      */     {
/*  269 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  271 */     if (severity.equals("4"))
/*      */     {
/*  273 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  275 */     if (severity.equals("1"))
/*      */     {
/*  277 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  282 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  289 */     if (severity == null)
/*      */     {
/*  291 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  293 */     if (severity.equals("5"))
/*      */     {
/*  295 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  297 */     if (severity.equals("4"))
/*      */     {
/*  299 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  301 */     if (severity.equals("1"))
/*      */     {
/*  303 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  308 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  316 */     StringBuffer out = new StringBuffer();
/*  317 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  318 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  319 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  320 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  321 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  322 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  323 */     out.append("</tr>");
/*  324 */     out.append("</form></table>");
/*  325 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  332 */     if (val == null)
/*      */     {
/*  334 */       return "-";
/*      */     }
/*      */     
/*  337 */     String ret = FormatUtil.formatNumber(val);
/*  338 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  339 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  342 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  346 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  354 */     StringBuffer out = new StringBuffer();
/*  355 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  356 */     out.append("<tr>");
/*  357 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  359 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  361 */     out.append("</tr>");
/*  362 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  366 */       if (j % 2 == 0)
/*      */       {
/*  368 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  372 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  375 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  377 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  380 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  384 */       out.append("</tr>");
/*      */     }
/*  386 */     out.append("</table>");
/*  387 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  388 */     out.append("<tr>");
/*  389 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  390 */     out.append("</tr>");
/*  391 */     out.append("</table>");
/*  392 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  398 */     StringBuffer out = new StringBuffer();
/*  399 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  400 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  401 */     out.append("<tr>");
/*  402 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  403 */     out.append("<tr>");
/*  404 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  405 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  406 */     out.append("</tr>");
/*  407 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  410 */       out.append("<tr>");
/*  411 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  412 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  413 */       out.append("</tr>");
/*      */     }
/*      */     
/*  416 */     out.append("</table>");
/*  417 */     out.append("</table>");
/*  418 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  423 */     if (severity.equals("0"))
/*      */     {
/*  425 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  429 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  436 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  449 */     StringBuffer out = new StringBuffer();
/*  450 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  451 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  453 */       out.append("<tr>");
/*  454 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  455 */       out.append("</tr>");
/*      */       
/*      */ 
/*  458 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  460 */         String borderclass = "";
/*      */         
/*      */ 
/*  463 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  465 */         out.append("<tr>");
/*      */         
/*  467 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  468 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  469 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  475 */     out.append("</table><br>");
/*  476 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  477 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  479 */       List sLinks = secondLevelOfLinks[0];
/*  480 */       List sText = secondLevelOfLinks[1];
/*  481 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  484 */         out.append("<tr>");
/*  485 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  486 */         out.append("</tr>");
/*  487 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  489 */           String borderclass = "";
/*      */           
/*      */ 
/*  492 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  494 */           out.append("<tr>");
/*      */           
/*  496 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  497 */           if (sLinks.get(i).toString().length() == 0) {
/*  498 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  501 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  503 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  507 */     out.append("</table>");
/*  508 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  515 */     StringBuffer out = new StringBuffer();
/*  516 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  517 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  519 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  521 */         out.append("<tr>");
/*  522 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  523 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  527 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  529 */           String borderclass = "";
/*      */           
/*      */ 
/*  532 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  534 */           out.append("<tr>");
/*      */           
/*  536 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  537 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  538 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  541 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  544 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  549 */     out.append("</table><br>");
/*  550 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  551 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  553 */       List sLinks = secondLevelOfLinks[0];
/*  554 */       List sText = secondLevelOfLinks[1];
/*  555 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  558 */         out.append("<tr>");
/*  559 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  560 */         out.append("</tr>");
/*  561 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  563 */           String borderclass = "";
/*      */           
/*      */ 
/*  566 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  568 */           out.append("<tr>");
/*      */           
/*  570 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  571 */           if (sLinks.get(i).toString().length() == 0) {
/*  572 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  575 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  577 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  581 */     out.append("</table>");
/*  582 */     return out.toString();
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
/*  595 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  598 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  601 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  604 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  607 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  610 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  613 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  616 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  624 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  629 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  634 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  639 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  644 */     if (val != null)
/*      */     {
/*  646 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  650 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  655 */     if (val == null) {
/*  656 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  660 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  665 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  671 */     if (val != null)
/*      */     {
/*  673 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  677 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  683 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  688 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  692 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  697 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  702 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  707 */     String hostaddress = "";
/*  708 */     String ip = request.getHeader("x-forwarded-for");
/*  709 */     if (ip == null)
/*  710 */       ip = request.getRemoteAddr();
/*  711 */     java.net.InetAddress add = null;
/*  712 */     if (ip.equals("127.0.0.1")) {
/*  713 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  717 */       add = java.net.InetAddress.getByName(ip);
/*      */     }
/*  719 */     hostaddress = add.getHostName();
/*  720 */     if (hostaddress.indexOf('.') != -1) {
/*  721 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  722 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  726 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  731 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  737 */     if (severity == null)
/*      */     {
/*  739 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  741 */     if (severity.equals("5"))
/*      */     {
/*  743 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  745 */     if (severity.equals("1"))
/*      */     {
/*  747 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  752 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  757 */     ResultSet set = null;
/*  758 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  759 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  761 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  762 */       if (set.next()) { String str1;
/*  763 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  764 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  767 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  772 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  775 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  777 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  781 */     StringBuffer rca = new StringBuffer();
/*  782 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  783 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  786 */     int rcalength = key.length();
/*  787 */     String split = "6. ";
/*  788 */     int splitPresent = key.indexOf(split);
/*  789 */     String div1 = "";String div2 = "";
/*  790 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  792 */       if (rcalength > 180) {
/*  793 */         rca.append("<span class=\"rca-critical-text\">");
/*  794 */         getRCATrimmedText(key, rca);
/*  795 */         rca.append("</span>");
/*      */       } else {
/*  797 */         rca.append("<span class=\"rca-critical-text\">");
/*  798 */         rca.append(key);
/*  799 */         rca.append("</span>");
/*      */       }
/*  801 */       return rca.toString();
/*      */     }
/*  803 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  804 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  805 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  806 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  807 */     getRCATrimmedText(div1, rca);
/*  808 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  811 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  812 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  813 */     getRCATrimmedText(div2, rca);
/*  814 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  816 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  821 */     String[] st = msg.split("<br>");
/*  822 */     for (int i = 0; i < st.length; i++) {
/*  823 */       String s = st[i];
/*  824 */       if (s.length() > 180) {
/*  825 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  827 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  831 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  832 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  834 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  838 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  839 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  840 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  843 */       if (key == null) {
/*  844 */         return ret;
/*      */       }
/*      */       
/*  847 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  848 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  851 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  852 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  853 */       set = AMConnectionPool.executeQueryStmt(query);
/*  854 */       if (set.next())
/*      */       {
/*  856 */         String helpLink = set.getString("LINK");
/*  857 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  860 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  866 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  885 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  876 */         if (set != null) {
/*  877 */           AMConnectionPool.closeStatement(set);
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
/*  891 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  892 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  894 */       String entityStr = (String)keys.nextElement();
/*  895 */       String mmessage = temp.getProperty(entityStr);
/*  896 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  897 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  899 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  905 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  906 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  908 */       String entityStr = (String)keys.nextElement();
/*  909 */       String mmessage = temp.getProperty(entityStr);
/*  910 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  911 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  913 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  918 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  928 */     String des = new String();
/*  929 */     while (str.indexOf(find) != -1) {
/*  930 */       des = des + str.substring(0, str.indexOf(find));
/*  931 */       des = des + replace;
/*  932 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  934 */     des = des + str;
/*  935 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  942 */       if (alert == null)
/*      */       {
/*  944 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  946 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  948 */         return "&nbsp;";
/*      */       }
/*      */       
/*  951 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  953 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  956 */       int rcalength = test.length();
/*  957 */       if (rcalength < 300)
/*      */       {
/*  959 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  963 */       StringBuffer out = new StringBuffer();
/*  964 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  965 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  966 */       out.append("</div>");
/*  967 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  968 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  969 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  974 */       ex.printStackTrace();
/*      */     }
/*  976 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  982 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  987 */     ArrayList attribIDs = new ArrayList();
/*  988 */     ArrayList resIDs = new ArrayList();
/*  989 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  991 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  993 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/*  995 */       String resourceid = "";
/*  996 */       String resourceType = "";
/*  997 */       if (type == 2) {
/*  998 */         resourceid = (String)row.get(0);
/*  999 */         resourceType = (String)row.get(3);
/*      */       }
/* 1001 */       else if (type == 3) {
/* 1002 */         resourceid = (String)row.get(0);
/* 1003 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1006 */         resourceid = (String)row.get(6);
/* 1007 */         resourceType = (String)row.get(7);
/*      */       }
/* 1009 */       resIDs.add(resourceid);
/* 1010 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1011 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1013 */       String healthentity = null;
/* 1014 */       String availentity = null;
/* 1015 */       if (healthid != null) {
/* 1016 */         healthentity = resourceid + "_" + healthid;
/* 1017 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1020 */       if (availid != null) {
/* 1021 */         availentity = resourceid + "_" + availid;
/* 1022 */         entitylist.add(availentity);
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
/* 1036 */     Properties alert = getStatus(entitylist);
/* 1037 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1042 */     int size = monitorList.size();
/*      */     
/* 1044 */     String[] severity = new String[size];
/*      */     
/* 1046 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1048 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1049 */       String resourceName1 = (String)row1.get(7);
/* 1050 */       String resourceid1 = (String)row1.get(6);
/* 1051 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1052 */       if (severity[j] == null)
/*      */       {
/* 1054 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1058 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1060 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1062 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1065 */         if (sev > 0) {
/* 1066 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1067 */           monitorList.set(k, monitorList.get(j));
/* 1068 */           monitorList.set(j, t);
/* 1069 */           String temp = severity[k];
/* 1070 */           severity[k] = severity[j];
/* 1071 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1077 */     int z = 0;
/* 1078 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1081 */       int i = 0;
/* 1082 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1085 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1089 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1093 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1095 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1098 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1102 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1105 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1106 */       String resourceName1 = (String)row1.get(7);
/* 1107 */       String resourceid1 = (String)row1.get(6);
/* 1108 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1109 */       if (hseverity[j] == null)
/*      */       {
/* 1111 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1116 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1118 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1121 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1124 */         if (hsev > 0) {
/* 1125 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1126 */           monitorList.set(k, monitorList.get(j));
/* 1127 */           monitorList.set(j, t);
/* 1128 */           String temp1 = hseverity[k];
/* 1129 */           hseverity[k] = hseverity[j];
/* 1130 */           hseverity[j] = temp1;
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
/* 1142 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1143 */     boolean forInventory = false;
/* 1144 */     String trdisplay = "none";
/* 1145 */     String plusstyle = "inline";
/* 1146 */     String minusstyle = "none";
/* 1147 */     String haidTopLevel = "";
/* 1148 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1150 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1152 */         haidTopLevel = request.getParameter("haid");
/* 1153 */         forInventory = true;
/* 1154 */         trdisplay = "table-row;";
/* 1155 */         plusstyle = "none";
/* 1156 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1163 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1166 */     ArrayList listtoreturn = new ArrayList();
/* 1167 */     StringBuffer toreturn = new StringBuffer();
/* 1168 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1169 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1170 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1172 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1174 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1175 */       String childresid = (String)singlerow.get(0);
/* 1176 */       String childresname = (String)singlerow.get(1);
/* 1177 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1178 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1179 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1180 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1181 */       String unmanagestatus = (String)singlerow.get(5);
/* 1182 */       String actionstatus = (String)singlerow.get(6);
/* 1183 */       String linkclass = "monitorgp-links";
/* 1184 */       String titleforres = childresname;
/* 1185 */       String titilechildresname = childresname;
/* 1186 */       String childimg = "/images/trcont.png";
/* 1187 */       String flag = "enable";
/* 1188 */       String dcstarted = (String)singlerow.get(8);
/* 1189 */       String configMonitor = "";
/* 1190 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1191 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1193 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1195 */       if (singlerow.get(7) != null)
/*      */       {
/* 1197 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1199 */       String haiGroupType = "0";
/* 1200 */       if ("HAI".equals(childtype))
/*      */       {
/* 1202 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1204 */       childimg = "/images/trend.png";
/* 1205 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1206 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1207 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1209 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1211 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1213 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1214 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1217 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1219 */         linkclass = "disabledtext";
/* 1220 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1222 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1223 */       String availmouseover = "";
/* 1224 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1226 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1228 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1229 */       String healthmouseover = "";
/* 1230 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1232 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1235 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1236 */       int spacing = 0;
/* 1237 */       if (level >= 1)
/*      */       {
/* 1239 */         spacing = 40 * level;
/*      */       }
/* 1241 */       if (childtype.equals("HAI"))
/*      */       {
/* 1243 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1244 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1245 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1247 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1248 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1249 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1250 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1251 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1252 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1253 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1254 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1255 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1256 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1257 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1259 */         if (!forInventory)
/*      */         {
/* 1261 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1264 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1266 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1268 */           actions = editlink + actions;
/*      */         }
/* 1270 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1272 */           actions = actions + associatelink;
/*      */         }
/* 1274 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1275 */         String arrowimg = "";
/* 1276 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1278 */           actions = "";
/* 1279 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1280 */           checkbox = "";
/* 1281 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1283 */         if (isIt360)
/*      */         {
/* 1285 */           actionimg = "";
/* 1286 */           actions = "";
/* 1287 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1288 */           checkbox = "";
/*      */         }
/*      */         
/* 1291 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1293 */           actions = "";
/*      */         }
/* 1295 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1297 */           checkbox = "";
/*      */         }
/*      */         
/* 1300 */         String resourcelink = "";
/*      */         
/* 1302 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1304 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1308 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1311 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1312 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1313 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1314 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1315 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1316 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1317 */         if (!isIt360)
/*      */         {
/* 1319 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1323 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1326 */         toreturn.append("</tr>");
/* 1327 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1329 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1330 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1334 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1335 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1338 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1342 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1344 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1345 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1346 */             toreturn.append(assocMessage);
/* 1347 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1348 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1349 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1350 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1356 */         String resourcelink = null;
/* 1357 */         boolean hideEditLink = false;
/* 1358 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1360 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1361 */           hideEditLink = true;
/* 1362 */           if (isIt360)
/*      */           {
/* 1364 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1368 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1370 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1372 */           hideEditLink = true;
/* 1373 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1374 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1379 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1382 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1383 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1384 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1385 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1386 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1387 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1388 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1389 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1390 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1391 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1392 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1393 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1394 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1396 */         if (hideEditLink)
/*      */         {
/* 1398 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1400 */         if (!forInventory)
/*      */         {
/* 1402 */           removefromgroup = "";
/*      */         }
/* 1404 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1405 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1406 */           actions = actions + configcustomfields;
/*      */         }
/* 1408 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1410 */           actions = editlink + actions;
/*      */         }
/* 1412 */         String managedLink = "";
/* 1413 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1415 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1416 */           actions = "";
/* 1417 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1418 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1421 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1423 */           checkbox = "";
/*      */         }
/*      */         
/* 1426 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1428 */           actions = "";
/*      */         }
/* 1430 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1431 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1432 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1433 */         if (isIt360)
/*      */         {
/* 1435 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1439 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1441 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1442 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1443 */         if (!isIt360)
/*      */         {
/* 1445 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1449 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1451 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1454 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1461 */       StringBuilder toreturn = new StringBuilder();
/* 1462 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1463 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1464 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1465 */       String title = "";
/* 1466 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1467 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1468 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1469 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1471 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1473 */       else if ("5".equals(severity))
/*      */       {
/* 1475 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1479 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1481 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1482 */       toreturn.append(v);
/*      */       
/* 1484 */       toreturn.append(link);
/* 1485 */       if (severity == null)
/*      */       {
/* 1487 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1489 */       else if (severity.equals("5"))
/*      */       {
/* 1491 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1493 */       else if (severity.equals("4"))
/*      */       {
/* 1495 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1497 */       else if (severity.equals("1"))
/*      */       {
/* 1499 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1504 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1506 */       toreturn.append("</a>");
/* 1507 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1511 */       ex.printStackTrace();
/*      */     }
/* 1513 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1520 */       StringBuilder toreturn = new StringBuilder();
/* 1521 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1522 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1523 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1524 */       if (message == null)
/*      */       {
/* 1526 */         message = "";
/*      */       }
/*      */       
/* 1529 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1530 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1532 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1533 */       toreturn.append(v);
/*      */       
/* 1535 */       toreturn.append(link);
/*      */       
/* 1537 */       if (severity == null)
/*      */       {
/* 1539 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1541 */       else if (severity.equals("5"))
/*      */       {
/* 1543 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1545 */       else if (severity.equals("1"))
/*      */       {
/* 1547 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1552 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1554 */       toreturn.append("</a>");
/* 1555 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1561 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1564 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1565 */     if (invokeActions != null) {
/* 1566 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1567 */       while (iterator.hasNext()) {
/* 1568 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1569 */         if (actionmap.containsKey(actionid)) {
/* 1570 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1575 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1579 */     String actionLink = "";
/* 1580 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1581 */     String query = "";
/* 1582 */     ResultSet rs = null;
/* 1583 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1584 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1585 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1586 */       actionLink = "method=" + methodName;
/*      */     }
/* 1588 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1589 */       actionLink = methodName;
/*      */     }
/* 1591 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1592 */     Iterator itr = methodarglist.iterator();
/* 1593 */     boolean isfirstparam = true;
/* 1594 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1595 */     while (itr.hasNext()) {
/* 1596 */       HashMap argmap = (HashMap)itr.next();
/* 1597 */       String argtype = (String)argmap.get("TYPE");
/* 1598 */       String argname = (String)argmap.get("IDENTITY");
/* 1599 */       String paramname = (String)argmap.get("PARAMETER");
/* 1600 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1601 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1602 */         isfirstparam = false;
/* 1603 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1605 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1609 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1613 */         actionLink = actionLink + "&";
/*      */       }
/* 1615 */       String paramValue = null;
/* 1616 */       String tempargname = argname;
/* 1617 */       if (commonValues.getProperty(tempargname) != null) {
/* 1618 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1621 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1622 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1623 */           if (dbType.equals("mysql")) {
/* 1624 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1627 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1629 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1631 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1632 */             if (rs.next()) {
/* 1633 */               paramValue = rs.getString("VALUE");
/* 1634 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1638 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1642 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1645 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1650 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1651 */           paramValue = rowId;
/*      */         }
/* 1653 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1654 */           paramValue = managedObjectName;
/*      */         }
/* 1656 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1657 */           paramValue = resID;
/*      */         }
/* 1659 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1660 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1663 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1665 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1666 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1667 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1669 */     return actionLink;
/*      */   }
/*      */   
/* 1672 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1673 */     String dependentAttribute = null;
/* 1674 */     String align = "left";
/*      */     
/* 1676 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1677 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1678 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1679 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1680 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1681 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1682 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1683 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1684 */       align = "center";
/*      */     }
/*      */     
/* 1687 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1688 */     String actualdata = "";
/*      */     
/* 1690 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1691 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1692 */         actualdata = availValue;
/*      */       }
/* 1694 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1695 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1699 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1700 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1703 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1709 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1710 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1711 */       toreturn.append("<table>");
/* 1712 */       toreturn.append("<tr>");
/* 1713 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1714 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1715 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1716 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1717 */         String toolTip = "";
/* 1718 */         String hideClass = "";
/* 1719 */         String textStyle = "";
/* 1720 */         boolean isreferenced = true;
/* 1721 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1722 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1723 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1724 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1726 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1727 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1728 */           while (valueList.hasMoreTokens()) {
/* 1729 */             String dependentVal = valueList.nextToken();
/* 1730 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1731 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1732 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1734 */               toolTip = "";
/* 1735 */               hideClass = "";
/* 1736 */               isreferenced = false;
/* 1737 */               textStyle = "disabledtext";
/* 1738 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1742 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1743 */           toolTip = "";
/* 1744 */           hideClass = "";
/* 1745 */           isreferenced = false;
/* 1746 */           textStyle = "disabledtext";
/* 1747 */           if (dependentImageMap != null) {
/* 1748 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1749 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1752 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1756 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1757 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1758 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1759 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1760 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1761 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1763 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1764 */           if (isreferenced) {
/* 1765 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1769 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1770 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1771 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1772 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1773 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1774 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1776 */           toreturn.append("</span>");
/* 1777 */           toreturn.append("</a>");
/* 1778 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1781 */       toreturn.append("</tr>");
/* 1782 */       toreturn.append("</table>");
/* 1783 */       toreturn.append("</td>");
/*      */     } else {
/* 1785 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1788 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1792 */     String colTime = null;
/* 1793 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1794 */     if ((rows != null) && (rows.size() > 0)) {
/* 1795 */       Iterator<String> itr = rows.iterator();
/* 1796 */       String maxColQuery = "";
/* 1797 */       for (;;) { if (itr.hasNext()) {
/* 1798 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1799 */           ResultSet maxCol = null;
/*      */           try {
/* 1801 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1802 */             while (maxCol.next()) {
/* 1803 */               if (colTime == null) {
/* 1804 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1807 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1816 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1818 */               if (maxCol != null)
/* 1819 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1821 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1816 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1818 */               if (maxCol != null)
/* 1819 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1821 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1826 */     return colTime;
/*      */   }
/*      */   
/* 1829 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1830 */     tablename = null;
/* 1831 */     ResultSet rsTable = null;
/* 1832 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1834 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1835 */       while (rsTable.next()) {
/* 1836 */         tablename = rsTable.getString("DATATABLE");
/* 1837 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1838 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1851 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1842 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1845 */         if (rsTable != null)
/* 1846 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1848 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1854 */     String argsList = "";
/* 1855 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1857 */       if (showArgsMap.get(row) != null) {
/* 1858 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1859 */         if (showArgslist != null) {
/* 1860 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1861 */             if (argsList.trim().equals("")) {
/* 1862 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1865 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1872 */       e.printStackTrace();
/* 1873 */       return "";
/*      */     }
/* 1875 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1880 */     String argsList = "";
/* 1881 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1884 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1886 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1887 */         if (hideArgsList != null)
/*      */         {
/* 1889 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1891 */             if (argsList.trim().equals(""))
/*      */             {
/* 1893 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1897 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1905 */       ex.printStackTrace();
/*      */     }
/* 1907 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1911 */     StringBuilder toreturn = new StringBuilder();
/* 1912 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1919 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1920 */       Iterator itr = tActionList.iterator();
/* 1921 */       while (itr.hasNext()) {
/* 1922 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1923 */         String confirmmsg = "";
/* 1924 */         String link = "";
/* 1925 */         String isJSP = "NO";
/* 1926 */         HashMap tactionMap = (HashMap)itr.next();
/* 1927 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1928 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1929 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1930 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1931 */           (actionmap.containsKey(actionId))) {
/* 1932 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1933 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1934 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1935 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1936 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1938 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1944 */           if (isTableAction) {
/* 1945 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1948 */             tableName = "Link";
/* 1949 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1950 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1951 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1952 */             toreturn.append("</a></td>");
/*      */           }
/* 1954 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1955 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1956 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1957 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1963 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1969 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1971 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1972 */       Properties prop = (Properties)node.getUserObject();
/* 1973 */       String mgID = prop.getProperty("label");
/* 1974 */       String mgName = prop.getProperty("value");
/* 1975 */       String isParent = prop.getProperty("isParent");
/* 1976 */       int mgIDint = Integer.parseInt(mgID);
/* 1977 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1979 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1981 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1982 */       if (node.getChildCount() > 0)
/*      */       {
/* 1984 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1986 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1988 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1990 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 1994 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 1999 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2001 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2003 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2005 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2009 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2012 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2013 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2015 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2019 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2021 */       if (node.getChildCount() > 0)
/*      */       {
/* 2023 */         builder.append("<UL>");
/* 2024 */         printMGTree(node, builder);
/* 2025 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2030 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2031 */     StringBuffer toReturn = new StringBuffer();
/* 2032 */     String table = "-";
/*      */     try {
/* 2034 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2035 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2036 */       float total = 0.0F;
/* 2037 */       while (it.hasNext()) {
/* 2038 */         String attName = (String)it.next();
/* 2039 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2040 */         boolean roundOffData = false;
/* 2041 */         if ((data != null) && (!data.equals(""))) {
/* 2042 */           if (data.indexOf(",") != -1) {
/* 2043 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2046 */             float value = Float.parseFloat(data);
/* 2047 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2050 */             total += value;
/* 2051 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2054 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2059 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2060 */       while (attVsWidthList.hasNext()) {
/* 2061 */         String attName = (String)attVsWidthList.next();
/* 2062 */         String data = (String)attVsWidthProps.get(attName);
/* 2063 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2064 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2065 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2066 */         String className = (String)graphDetails.get("ClassName");
/* 2067 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2068 */         if (percentage < 1.0F)
/*      */         {
/* 2070 */           data = percentage + "";
/*      */         }
/* 2072 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2074 */       if (toReturn.length() > 0) {
/* 2075 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2079 */       e.printStackTrace();
/*      */     }
/* 2081 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2087 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2088 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2089 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2090 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2091 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2092 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2093 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2094 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2095 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2098 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2099 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2100 */       splitvalues[0] = multiplecondition.toString();
/* 2101 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2104 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2109 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2110 */     if (thresholdType != 3) {
/* 2111 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2112 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2113 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2114 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2115 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2116 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2118 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2119 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2120 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2121 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2122 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2123 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2125 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2126 */     if (updateSelected != null) {
/* 2127 */       updateSelected[0] = "selected";
/*      */     }
/* 2129 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2134 */       StringBuffer toreturn = new StringBuffer("");
/* 2135 */       if (commaSeparatedMsgId != null) {
/* 2136 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2137 */         int count = 0;
/* 2138 */         while (msgids.hasMoreTokens()) {
/* 2139 */           String id = msgids.nextToken();
/* 2140 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2141 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2142 */           count++;
/* 2143 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2144 */             if (toreturn.length() == 0) {
/* 2145 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2147 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2148 */             if (!image.trim().equals("")) {
/* 2149 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2151 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2152 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2155 */         if (toreturn.length() > 0) {
/* 2156 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2160 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2163 */       e.printStackTrace(); }
/* 2164 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2170 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2176 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2177 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2178 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2195 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2199 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2200 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2201 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2202 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2203 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2204 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2205 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2206 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2207 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2209 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2217 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2218 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2228 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2231 */     JspWriter out = null;
/* 2232 */     Object page = this;
/* 2233 */     JspWriter _jspx_out = null;
/* 2234 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2238 */       response.setContentType("text/html;charset=UTF-8");
/* 2239 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2241 */       _jspx_page_context = pageContext;
/* 2242 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2243 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2244 */       session = pageContext.getSession();
/* 2245 */       out = pageContext.getOut();
/* 2246 */       _jspx_out = out;
/*      */       
/* 2248 */       out.write("<!--$Id$-->\n\n");
/* 2249 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2250 */       out.write(10);
/* 2251 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2253 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2254 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2256 */       out.write(10);
/* 2257 */       out.write(10);
/* 2258 */       out.write(10);
/* 2259 */       out.write("\n\n\n\n");
/* 2260 */       com.adventnet.appmanager.client.wsm.WSMGraph wsmGraph = null;
/* 2261 */       wsmGraph = (com.adventnet.appmanager.client.wsm.WSMGraph)_jspx_page_context.getAttribute("wsmGraph", 2);
/* 2262 */       if (wsmGraph == null) {
/* 2263 */         wsmGraph = new com.adventnet.appmanager.client.wsm.WSMGraph();
/* 2264 */         _jspx_page_context.setAttribute("wsmGraph", wsmGraph, 2);
/*      */       }
/* 2266 */       out.write(10);
/*      */       
/* 2268 */       String resourceid = request.getParameter("resourceid");
/* 2269 */       String encodeurl = java.net.URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid);
/* 2270 */       ArrayList attribIDs = new ArrayList();
/* 2271 */       ArrayList resIDs = new ArrayList();
/* 2272 */       Properties alert = null;
/* 2273 */       HashMap customAttrDetails = (HashMap)request.getAttribute("customAttr");
/* 2274 */       if (!customAttrDetails.isEmpty())
/*      */       {
/* 2276 */         HashMap customAttrs = (HashMap)customAttrDetails.get("customAttr");
/* 2277 */         Object[] arr = customAttrs.keySet().toArray();
/* 2278 */         for (int i = 0; i < arr.length; i++)
/*      */         {
/* 2280 */           attribIDs.add(arr[i].toString());
/*      */         }
/*      */       }
/* 2283 */       resIDs.add(resourceid);
/* 2284 */       alert = getStatus(resIDs, attribIDs);
/*      */       
/* 2286 */       out.write("\n<table width=\"99%\" cellspacing=\"1\" cellpadding=\"1\" border=\"0\" align=\"center\">\n<tr>\n<td width=\"70%\">\n<table width=\"99%\" class=\"lrtbdarkborder\" cellspacing=\"1\" cellpadding=\"1\" border=\"0\" align=\"center\">\n ");
/*      */       
/* 2288 */       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2289 */       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2290 */       _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2291 */       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2292 */       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */         for (;;) {
/* 2294 */           out.write(10);
/* 2295 */           out.write(32);
/* 2296 */           out.write(32);
/*      */           
/* 2298 */           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2299 */           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2300 */           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/* 2302 */           _jspx_th_c_005fwhen_005f0.setTest("${!empty requestScope.apiConfig.apiResp}");
/* 2303 */           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2304 */           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */             for (;;) {
/* 2306 */               out.write("\n\t<tr>\n\t\t<td align=\"center\" colspan=\"1\">\n\t\t\t<br />\n\t\t\t<textarea rows=\"19\" cols=\"85\" accept=\"xml\" id=\"apiResp\" name=\"apiResp\" style=\"overflow-y: scroll;\" readonly> ");
/* 2307 */               if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/* 2309 */               out.write(" </textarea> <br />\n\t\t\t");
/* 2310 */               if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/* 2312 */               out.write("\n\t\t\t");
/* 2313 */               if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/* 2315 */               out.write("\n\t\t\t<br />\n\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td align=\"center\" colspan=\"1\" id=\"XSLTInputTD\" style=\"display:none;\">\n\t\t\t<br />\n\t\t\t<textarea rows =\"19\" cols=\"85\" accept=\"xml\" id=\"xsltInput\" style=\"overflow-y: scroll;\">");
/* 2316 */               if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/* 2318 */               out.write("</textarea>\n\t\t\t<br />\n\t\t\t");
/* 2319 */               if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/* 2321 */               out.write("\n\t\t\t<input type=\"button\" class=\"buttons\" value=\"Cancel\" id=\"cancelXSLT\" onclick=\"hideInput()\" />\n\t\t</td>\n\t\t<td align=\"center\" colspan=\"1\" id=\"JSONSchemaTD\" style=\"display:none;\">\n\t\t\t<br />\n\t\t\t<textarea rows =\"19\" cols=\"85\" id=\"jsonSchema\" style=\"overflow-y: scroll;\">");
/* 2322 */               if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/* 2324 */               out.write("</textarea>\n\t\t\t<br />\n\t\t\t");
/* 2325 */               if (_jspx_meth_logic_005fnotPresent_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/* 2327 */               out.write("\n\t\t\t<input type=\"button\" class=\"buttons\" value=\"Cancel\" id=\"cancelJSON\" onclick=\"hideInput()\" />\n\t\t</td>\n\t</tr>\n\t");
/*      */               
/* 2329 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2330 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2331 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */               
/* 2333 */               _jspx_th_c_005fif_005f2.setTest("${!empty requestScope.customAttr.customAttr}");
/* 2334 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2335 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/* 2337 */                   out.write("\n\t\t<tr><td><br /></td></tr>\n\t\t<tr>\n\t\t\t<td colspan=\"2\">\n\t\t\t\t<form id=\"custAttrTable\" name=\"custAttrs\" method=\"post\" action=\"/RestAPIAction.do\">\n\t\t\t\t<input type=\"hidden\" name=\"method\" value=\"deleteoperations\" />\n\t\t\t\t<input type=\"hidden\" name=\"enable\" value=\"false\" />\n\t\t\t\t<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2338 */                   out.print(resourceid);
/* 2339 */                   out.write("\" />\n\t\t\t\t<table width=\"80%\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"lrbtborder\">\n        \t\t\t<tr>\n        \t\t\t\t");
/* 2340 */                   if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                     return;
/* 2342 */                   out.write("\t\t\t\n       \t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 2343 */                   out.print(FormatUtil.getString("Attribute"));
/* 2344 */                   out.write("</span></td>  ");
/* 2345 */                   out.write("\n       \t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 2346 */                   out.print(FormatUtil.getString("Value"));
/* 2347 */                   out.write("</span></td>  ");
/* 2348 */                   out.write("\n     \t\t\t\t\t<td class=\"columnheadingnotop\" align=\"center\"><span class=\"bodytextbold\">");
/* 2349 */                   out.print(FormatUtil.getString("Threshold"));
/* 2350 */                   out.write("</span></td> ");
/* 2351 */                   out.write("\n          \t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 2352 */                   out.print(FormatUtil.getString("am.webclient.rule.action"));
/* 2353 */                   out.write("</span></td>  ");
/* 2354 */                   out.write("\n          \t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 2355 */                   out.print(FormatUtil.getString("am.webclient.sap.bg.history"));
/* 2356 */                   out.write("</span></td>  ");
/* 2357 */                   out.write("\n        \t\t\t</tr>\n        \t\t\t");
/*      */                   
/* 2359 */                   ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 2360 */                   _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 2361 */                   _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f2);
/*      */                   
/* 2363 */                   _jspx_th_c_005fforEach_005f0.setItems("${requestScope.customAttr.customAttr}");
/*      */                   
/* 2365 */                   _jspx_th_c_005fforEach_005f0.setVar("custAttr");
/* 2366 */                   int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                   try {
/* 2368 */                     int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 2369 */                     if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                       for (;;) {
/* 2371 */                         out.write("\n\t\t\t\t\t\t");
/* 2372 */                         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                         {
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
/* 2575 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/* 2374 */                         out.write("        \t\t\t\t\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t");
/* 2375 */                         if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                         {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2575 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/* 2377 */                         out.write("\n\t\t\t\t\t\t\t<td width=\"47%\" height=\"25\" class=\"yellowgrayborderbr\">");
/* 2378 */                         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                         {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2575 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/* 2380 */                         out.write("</td>\n        \t\t\t\t\t<td width=\"25%\" height=\"25\" class=\"yellowgrayborderbr\">\n        \t\t\t\t\t\t");
/*      */                         
/* 2382 */                         ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2383 */                         _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2384 */                         _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fforEach_005f0);
/* 2385 */                         int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2386 */                         if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                           for (;;) {
/* 2388 */                             out.write("\n        \t\t\t\t\t\t\t");
/*      */                             
/* 2390 */                             WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2391 */                             _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2392 */                             _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                             
/* 2394 */                             _jspx_th_c_005fwhen_005f2.setTest("${custAttr.value.Type==\"0\"}");
/* 2395 */                             int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2396 */                             if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                               for (;;) {
/* 2398 */                                 out.write("\n        \t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&reporttype=html&businessPeriod=oni&resourceid=");
/* 2399 */                                 out.print(resourceid);
/* 2400 */                                 out.write("&period=20&childid=null&attributeid=");
/* 2401 */                                 if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                 {
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
/* 2575 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 2403 */                                 out.write("&resourcename=");
/* 2404 */                                 out.print(request.getParameter("resourcename"));
/* 2405 */                                 out.write("')\">\n\t    \t\t    \t\t\t\t\t\t<u>");
/* 2406 */                                 if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fwhen_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                 {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2575 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 2408 */                                 out.write("</u>\n\t        \t\t\t\t\t\t\t</a>\n        \t\t\t\t\t\t\t");
/* 2409 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2410 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2414 */                             if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2415 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
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
/* 2575 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2418 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2419 */                             out.write("\n        \t\t\t\t\t\t\t");
/* 2420 */                             if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
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
/* 2575 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2422 */                             out.write("\n        \t\t\t\t\t\t");
/* 2423 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2424 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2428 */                         if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2429 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
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
/* 2575 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/* 2432 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2433 */                         out.write("\n        \t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td height=\"25\" class=\"yellowgrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2434 */                         out.print(resourceid);
/* 2435 */                         out.write("&attributeid=");
/* 2436 */                         out.print(pageContext.getAttribute("attrId").toString());
/* 2437 */                         out.write("')\">");
/* 2438 */                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + pageContext.getAttribute("attrId").toString())));
/* 2439 */                         out.write("</a></td>\n\t\t\t\t\t\t\t<td height=\"25\" class=\"yellowgrayborderbr\" align=\"center\">\n        \t  \t\t\t\t\t");
/*      */                         
/* 2441 */                         NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2442 */                         _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 2443 */                         _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                         
/* 2445 */                         _jspx_th_logic_005fnotPresent_005f2.setRole("ENTERPRISEADMIN");
/* 2446 */                         int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 2447 */                         if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                           for (;;) {
/* 2449 */                             out.write("\n        \t  \t\t\t\t\t\t<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2450 */                             out.print(resourceid);
/* 2451 */                             out.write("&attributeIDs=");
/* 2452 */                             out.print(pageContext.getAttribute("attrId").toString());
/* 2453 */                             out.write("&attributeToSelect=");
/* 2454 */                             out.print(pageContext.getAttribute("attrId").toString());
/* 2455 */                             out.write("&redirectto=");
/* 2456 */                             out.print(encodeurl);
/* 2457 */                             out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" title=\"");
/* 2458 */                             out.print(FormatUtil.getString("am.webclient.toolbar.configurealert.text"));
/* 2459 */                             out.write("\" border=\"0\" /></a>\n        \t  \t\t\t\t\t");
/* 2460 */                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 2461 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2465 */                         if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 2466 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
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
/* 2575 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/* 2469 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 2470 */                         out.write("\n        \t  \t\t\t\t</td>\n        \t  \t\t\t\t<td height=\"30\" class=\"yellowgrayborderbr\" align=\"center\">\n        \t  \t\t\t\t\t");
/*      */                         
/* 2472 */                         ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2473 */                         _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2474 */                         _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fforEach_005f0);
/* 2475 */                         int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2476 */                         if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                           for (;;) {
/* 2478 */                             out.write("\n        \t  \t\t\t\t\t\t");
/*      */                             
/* 2480 */                             WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2481 */                             _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2482 */                             _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                             
/* 2484 */                             _jspx_th_c_005fwhen_005f3.setTest("${custAttr.value.Reports==\"1\"}");
/* 2485 */                             int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2486 */                             if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                               for (;;) {
/* 2488 */                                 out.write("\n        \t  \t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2489 */                                 out.print(resourceid);
/* 2490 */                                 out.write("&attributeid=");
/* 2491 */                                 if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fwhen_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                 {
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
/*      */ 
/* 2575 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 2493 */                                 out.write("&period=-7&resourcename=");
/* 2494 */                                 out.print(request.getParameter("resourcename"));
/* 2495 */                                 out.write("')\">\n        \t  \t\t\t\t\t\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"15\" hspace=\"5\" vspace=\"2\" border=\"0\"  title=\"");
/* 2496 */                                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2497 */                                 out.write("\"/>\n        \t  \t\t\t\t\t\t\t</a>\n        \t  \t\t\t\t\t\t");
/* 2498 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2499 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2503 */                             if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2504 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
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
/* 2575 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2507 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2508 */                             out.write("\n        \t  \t\t\t\t\t\t");
/*      */                             
/* 2510 */                             OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2511 */                             _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2512 */                             _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f3);
/* 2513 */                             int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2514 */                             if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                               for (;;) {
/* 2516 */                                 out.write("\n        \t  \t\t\t\t\t\t\t");
/*      */                                 
/* 2518 */                                 IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2519 */                                 _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2520 */                                 _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                                 
/* 2522 */                                 _jspx_th_c_005fif_005f3.setTest("${custAttr.value.Type==\"0\"}");
/* 2523 */                                 int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2524 */                                 if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                   for (;;) {
/* 2526 */                                     out.write("\n        \t  \t\t\t\t\t\t\t\t<img src=\"../images/icon_7days_disabled.gif\" width=\"24\" height=\"15\" hspace=\"5\" vspace=\"2\" border=\"0\"  title=\"");
/* 2527 */                                     out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text.disabled"));
/* 2528 */                                     out.write("\"/>\n        \t  \t\t\t\t\t\t\t");
/* 2529 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2530 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2534 */                                 if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2535 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
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
/* 2575 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 2538 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2539 */                                 out.write("\n        \t  \t\t\t\t\t\t");
/* 2540 */                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2541 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2545 */                             if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2546 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
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
/*      */ 
/* 2575 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2549 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2550 */                             out.write("\n        \t  \t\t\t\t\t");
/* 2551 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2552 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2556 */                         if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2557 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
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
/* 2575 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/* 2560 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2561 */                         out.write("\n\t\t   \t  \t\t\t\t</td>\t\t\t\t\t\t\t\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t");
/* 2562 */                         int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 2563 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2567 */                     if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2575 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                     }
/*      */                   }
/*      */                   catch (Throwable _jspx_exception)
/*      */                   {
/*      */                     for (;;)
/*      */                     {
/* 2571 */                       int tmp2607_2606 = 0; int[] tmp2607_2604 = _jspx_push_body_count_c_005fforEach_005f0; int tmp2609_2608 = tmp2607_2604[tmp2607_2606];tmp2607_2604[tmp2607_2606] = (tmp2609_2608 - 1); if (tmp2609_2608 <= 0) break;
/* 2572 */                       out = _jspx_page_context.popBody(); }
/* 2573 */                     _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                   } finally {
/* 2575 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2576 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                   }
/* 2578 */                   out.write("\n\t\t\t\t\t");
/*      */                   
/* 2580 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2581 */                   _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 2582 */                   _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_c_005fif_005f2);
/*      */                   
/* 2584 */                   _jspx_th_logic_005fnotPresent_005f3.setRole("ENTERPRISEADMIN");
/* 2585 */                   int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 2586 */                   if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                     for (;;) {
/* 2588 */                       out.write("\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td align=\"left\" class=\"tablebottom\" colspan=\"6\"><a class=\"staticlinks\" href=\"javascript:enablereports()\">");
/* 2589 */                       out.print(FormatUtil.getString("am.webclient.wsm.enablereports.text"));
/* 2590 */                       out.write("</a>&nbsp;<span style=\"color:#000; height:17px;\">|</span>&nbsp;<a class=\"staticlinks\" href=\"javascript:disablereports()\">");
/* 2591 */                       out.print(FormatUtil.getString("am.webclient.wsm.disablereports.text"));
/* 2592 */                       out.write("</a>&nbsp;</td>\t");
/* 2593 */                       out.write("\t\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t");
/* 2594 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 2595 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2599 */                   if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 2600 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                   }
/*      */                   
/* 2603 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 2604 */                   out.write("\t\t\n        \t\t</table>\n        \t\t</form>\n   \t\t\t</td>\n\t\t</tr>\n\t\t<tr><td><br /></td></tr>\n\t");
/* 2605 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2606 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2610 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2611 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */               }
/*      */               
/* 2614 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2615 */               out.write("\n   ");
/* 2616 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2617 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2621 */           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2622 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */           }
/*      */           
/* 2625 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2626 */           out.write("\n   ");
/*      */           
/* 2628 */           OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2629 */           _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2630 */           _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f0);
/* 2631 */           int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2632 */           if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */             for (;;) {
/* 2634 */               out.write("\n   \t\t<tbody>\n   \t\t\t<tr>\n\t\t\t\t<td width=\"6%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"23\" height=\"23\"> \n    \t\t\t</td>\n   \t\t\t\t<td width=\"94%\" height=\"34\" class=\"message\">");
/* 2635 */               out.print(FormatUtil.getString("weblogic.datacollection.notstarted"));
/* 2636 */               out.write(".</td>\n    \t\t</tr>\n\t\t</tbody>\n   ");
/* 2637 */               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2638 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2642 */           if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2643 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */           }
/*      */           
/* 2646 */           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2647 */           out.write(10);
/* 2648 */           out.write(32);
/* 2649 */           out.write(32);
/* 2650 */           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2651 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2655 */       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2656 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */       }
/*      */       else {
/* 2659 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2660 */         out.write("\n</table>\n</td>\n<td valign=\"top\">\n\t");
/* 2661 */         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.restapi.custom.attr.helpcard.text")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("showContent", request.getCharacterEncoding()), out, false);
/* 2662 */         out.write("\n</td>\n</tr>\n</table>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<script>\n$(document).ready(function()\n{\n\t$('#addXSLT').click(function(){\n\t\t$('#XSLTInputTD').show();\n\t\t$('#xsltInput').focus();\n\t});\n\t$('#addJSON').click(function(){\n\t\t$('#JSONSchemaTD').show();\n\t\t$('#jsonSchema').focus();\n\t});\n\t$(':checkbox[name=reportsCheck]').click (function () {\n  \t\t$(':checkbox[name=custom]').prop('checked', this.checked); //No I18N\n\t});\n});\n\nfunction hideInput()\n{\n\t$('#XSLTInputTD').hide();\n\t$('#JSONSchemaTD').hide();\n\t$('#apiResp').focus();\n}\n\nfunction updateInput(inputType)\n{\n\tvar resID = ");
/* 2663 */         out.print(request.getParameter("resourceid"));
/* 2664 */         out.write(";\n\tvar input='';\n\tif(inputType==='XSLTINPUT')\n\t{\n\t\tinput = document.getElementById(\"xsltInput\").value;\n\t\tif(input==\"\")\n\t\t{\n\t\t\talert('");
/* 2665 */         out.print(FormatUtil.getString("am.webclient.restapi.empty.xslt"));
/* 2666 */         out.write("');\n\t\t\t$('#xsltInput').focus();\n\t\t\treturn null;\n\t\t}\n\t\tif(!input.startsWith(\"<?xml\"))\n\t\t{\n\t\t\talert('");
/* 2667 */         out.print(FormatUtil.getString("am.webclient.restapi.invalid.xslt"));
/* 2668 */         out.write("');\n\t\t\t$('#xsltInput').focus();\n\t\t\treturn null;\n\t\t}\n\t\telse if(!input.includes(\"<xsl:output method=\"))\n\t\t{\n\t\t\talert('");
/* 2669 */         out.print(FormatUtil.getString("am.webclient.restapi.xslt.output.notdefined"));
/* 2670 */         out.write("');\n\t\t\t$('#xsltInput').focus();\n\t\t\treturn null;\n\t\t}\n\t}\n\telse if(inputType==='JSONSCHEMA')\n\t{\n\t\tinput = document.getElementById('jsonSchema').value;\n\t\tif(input==\"\")\n\t\t{\n\t\t\talert('");
/* 2671 */         out.print(FormatUtil.getString("am.webclient.restapi.empty.json"));
/* 2672 */         out.write("');\n\t\t\t$('#jsonSchema').focus();\n\t\t\treturn null;\n\t\t}\n\t\telse if(!input.includes(\"::\"))\n\t\t{\n\t\t\talert('");
/* 2673 */         out.print(FormatUtil.getString("am.webclient.restapi.json.syntax.incorrect"));
/* 2674 */         out.write("');\n\t\t\t$('#jsonSchema').focus();\n\t\t\treturn null;\n\t\t}\n\t}\n\tvar dataString=\"method=updateInput&resID=\"+resID+\"&inputType=\"+inputType+\"&input=\"+input; //No I18N\n\t$.ajax({\n\t\ttype:'POST', //No I18N\n\t\turl:'/RestAPIAction.do', //No I18N\n\t\tdata:dataString,\n\t\tasync:false,\n\t\tsuccess : function(data)\n\t\t{\n\t\t\talert(data);\n\t\t\thideInput();\n\t\t}\n\t});\n}\nfunction enablereports()\n{\n\tif(!checkforOneSelected(document.custAttrs,'custom'))\n\t{\n\t\talert(\"");
/* 2675 */         out.print(FormatUtil.getString("am.webclient.wsm.jsalertselectattribute.text"));
/* 2676 */         out.write("\");\n\t\treturn;\n\t}\n\tdocument.forms.custAttrs.method.value=\"enabledisablereports\"; //No I18N\n\tdocument.forms.custAttrs.enable.value=\"true\";\n\tdocument.forms.custAttrs.submit();\n}\n\nfunction disablereports()\n{\n\tif(!checkforOneSelected(document.custAttrs,'custom'))\n\t{\n\t\talert(\"");
/* 2677 */         out.print(FormatUtil.getString("am.webclient.wsm.jsalertselectattribute.text"));
/* 2678 */         out.write("\");\n\t\treturn;\n\t}\n\tdocument.forms.custAttrs.method.value=\"enabledisablereports\"; //No I18N\n\tdocument.forms.custAttrs.enable.value=\"false\";\n\tdocument.forms.custAttrs.submit();\n}\n\n</script>\n\n");
/*      */       }
/* 2680 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2681 */         out = _jspx_out;
/* 2682 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2683 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2684 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2687 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2693 */     PageContext pageContext = _jspx_page_context;
/* 2694 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2696 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2697 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2698 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2700 */     _jspx_th_c_005fout_005f0.setValue("${requestScope.apiConfig.apiResp}");
/* 2701 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2702 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2703 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2704 */       return true;
/*      */     }
/* 2706 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2707 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2712 */     PageContext pageContext = _jspx_page_context;
/* 2713 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2715 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2716 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2717 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2719 */     _jspx_th_c_005fif_005f0.setTest("${requestScope.responseType=='XML'}");
/* 2720 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2721 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 2723 */         out.write("\n\t\t\t\t<input type=\"button\" class=\"buttons\" value=\"Add/View XSLT Code\" id=\"addXSLT\"/>\n\t\t\t");
/* 2724 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2725 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2729 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2730 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2731 */       return true;
/*      */     }
/* 2733 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2734 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2739 */     PageContext pageContext = _jspx_page_context;
/* 2740 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2742 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2743 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2744 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2746 */     _jspx_th_c_005fif_005f1.setTest("${requestScope.responseType=='JSON'}");
/* 2747 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2748 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 2750 */         out.write("\n\t\t\t\t<input type=\"button\" class=\"buttons\" value=\"Add/View JSON Schema\" id=\"addJSON\" />\n\t\t\t");
/* 2751 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2752 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2756 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2757 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2758 */       return true;
/*      */     }
/* 2760 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2761 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2766 */     PageContext pageContext = _jspx_page_context;
/* 2767 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2769 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2770 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2771 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2773 */     _jspx_th_c_005fout_005f1.setValue("${requestScope.apiConfig.xsltInput}");
/* 2774 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2775 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2776 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2777 */       return true;
/*      */     }
/* 2779 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2780 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2785 */     PageContext pageContext = _jspx_page_context;
/* 2786 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2788 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2789 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2790 */     _jspx_th_logic_005fnotPresent_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2792 */     _jspx_th_logic_005fnotPresent_005f0.setRole("ENTERPRISEADMIN,DEMO");
/* 2793 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2794 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 2796 */         out.write("\n\t\t\t\t<input type=\"button\" class=\"buttons\" value=\"Update XSLT Code\" id=\"updateXSLT\" onclick=\"updateInput('XSLTINPUT')\" />\n\t\t\t");
/* 2797 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2798 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2802 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2803 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2804 */       return true;
/*      */     }
/* 2806 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2812 */     PageContext pageContext = _jspx_page_context;
/* 2813 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2815 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2816 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2817 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2819 */     _jspx_th_c_005fout_005f2.setValue("${requestScope.apiConfig.jsonSchema}");
/* 2820 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2821 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2822 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2823 */       return true;
/*      */     }
/* 2825 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2826 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2831 */     PageContext pageContext = _jspx_page_context;
/* 2832 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2834 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2835 */     _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2836 */     _jspx_th_logic_005fnotPresent_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2838 */     _jspx_th_logic_005fnotPresent_005f1.setRole("ENTERPRISEADMIN,DEMO");
/* 2839 */     int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2840 */     if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */       for (;;) {
/* 2842 */         out.write("\n\t\t\t\t<input type=\"button\" class=\"buttons\" value=\"Update JSON Schema\" id=\"updateJSON\" onclick=\"updateInput('JSONSCHEMA')\"/>\n\t\t\t");
/* 2843 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2844 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2848 */     if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2849 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2850 */       return true;
/*      */     }
/* 2852 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2853 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2858 */     PageContext pageContext = _jspx_page_context;
/* 2859 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2861 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2862 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2863 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 2865 */     _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,DEMO");
/* 2866 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2867 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 2869 */         out.write("\n        \t\t\t\t\t<td class=\"columnheadingnotop\" width=\"5%\" align=\"center\"><input type=\"checkbox\" name=\"reportsCheck\"></td>\n        \t\t\t\t");
/* 2870 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2871 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2875 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2876 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2877 */       return true;
/*      */     }
/* 2879 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2880 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2885 */     PageContext pageContext = _jspx_page_context;
/* 2886 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2888 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 2889 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2890 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2892 */     _jspx_th_c_005fset_005f0.setVar("attrId");
/*      */     
/* 2894 */     _jspx_th_c_005fset_005f0.setValue("${custAttr.key}");
/* 2895 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2896 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2897 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2898 */       return true;
/*      */     }
/* 2900 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2901 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2906 */     PageContext pageContext = _jspx_page_context;
/* 2907 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2909 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2910 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2911 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2913 */     _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,DEMO");
/* 2914 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2915 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 2917 */         out.write("\n\t\t\t\t\t\t\t\t<td height=\"25\" width=\"5%\" align=\"center\" class=\"yellowgrayborderbr\">\n\t\t\t\t\t\t\t\t\t");
/* 2918 */         if (_jspx_meth_c_005fchoose_005f1(_jspx_th_logic_005fpresent_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2919 */           return true;
/* 2920 */         out.write("\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t");
/* 2921 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2922 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2926 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2927 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2928 */       return true;
/*      */     }
/* 2930 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2931 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2936 */     PageContext pageContext = _jspx_page_context;
/* 2937 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2939 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2940 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2941 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/* 2942 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2943 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 2945 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 2946 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2947 */           return true;
/* 2948 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 2949 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2950 */           return true;
/* 2951 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 2952 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2953 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2957 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2958 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2959 */       return true;
/*      */     }
/* 2961 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2962 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2967 */     PageContext pageContext = _jspx_page_context;
/* 2968 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2970 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2971 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2972 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 2974 */     _jspx_th_c_005fwhen_005f1.setTest("${custAttr.value.Type==\"0\"}");
/* 2975 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2976 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 2978 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"custom\" value=\"");
/* 2979 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2980 */           return true;
/* 2981 */         out.write("\"/>\n\t\t\t\t\t\t\t\t\t\t");
/* 2982 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2983 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2987 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2988 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2989 */       return true;
/*      */     }
/* 2991 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2992 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2997 */     PageContext pageContext = _jspx_page_context;
/* 2998 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3000 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3001 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3002 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 3004 */     _jspx_th_c_005fout_005f3.setValue("${attrId}");
/* 3005 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3006 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3007 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3008 */       return true;
/*      */     }
/* 3010 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3011 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3016 */     PageContext pageContext = _jspx_page_context;
/* 3017 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3019 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3020 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3021 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 3022 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3023 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 3025 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"custom\" value=\"");
/* 3026 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3027 */           return true;
/* 3028 */         out.write("\" disabled/>\n\t\t\t\t\t\t\t\t\t\t");
/* 3029 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3030 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3034 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3035 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3036 */       return true;
/*      */     }
/* 3038 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3039 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3044 */     PageContext pageContext = _jspx_page_context;
/* 3045 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3047 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3048 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3049 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 3051 */     _jspx_th_c_005fout_005f4.setValue("${attrId}");
/* 3052 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3053 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3054 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3055 */       return true;
/*      */     }
/* 3057 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3058 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3063 */     PageContext pageContext = _jspx_page_context;
/* 3064 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3066 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3067 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3068 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3070 */     _jspx_th_c_005fout_005f5.setValue("${custAttr.value.Name}");
/* 3071 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3072 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3073 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3074 */       return true;
/*      */     }
/* 3076 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3077 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3082 */     PageContext pageContext = _jspx_page_context;
/* 3083 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3085 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3086 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3087 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 3089 */     _jspx_th_c_005fout_005f6.setValue("${attrId}");
/* 3090 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3091 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3092 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3093 */       return true;
/*      */     }
/* 3095 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3096 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3101 */     PageContext pageContext = _jspx_page_context;
/* 3102 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3104 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3105 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3106 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 3108 */     _jspx_th_c_005fout_005f7.setValue("${custAttr.value.Value}");
/* 3109 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3110 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3111 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3112 */       return true;
/*      */     }
/* 3114 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3115 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3120 */     PageContext pageContext = _jspx_page_context;
/* 3121 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3123 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3124 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 3125 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 3126 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 3127 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 3129 */         out.write("\n        \t\t\t\t\t\t\t\t");
/* 3130 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3131 */           return true;
/* 3132 */         out.write("\n\t        \t\t\t\t\t\t");
/* 3133 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3134 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3138 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3139 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3140 */       return true;
/*      */     }
/* 3142 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3143 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3148 */     PageContext pageContext = _jspx_page_context;
/* 3149 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3151 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3152 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3153 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3155 */     _jspx_th_c_005fout_005f8.setValue("${custAttr.value.Value}");
/* 3156 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3157 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3158 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3159 */       return true;
/*      */     }
/* 3161 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3167 */     PageContext pageContext = _jspx_page_context;
/* 3168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3170 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3171 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3172 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 3174 */     _jspx_th_c_005fout_005f9.setValue("${attrId}");
/* 3175 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3176 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3177 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3178 */       return true;
/*      */     }
/* 3180 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3181 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\includes\restapi_005fcustomAttr_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */