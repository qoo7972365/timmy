/*      */ package org.apache.jsp.jsp;
/*      */ 
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
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.MultiboxTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.ResetTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class urlconf_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*   71 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
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
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
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
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, javax.servlet.http.HttpSession session)
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
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, javax.servlet.http.HttpSession session)
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
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, javax.servlet.http.HttpSession session, HttpServletRequest request)
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
/*  721 */     java.net.InetAddress add = null;
/*  722 */     if (ip.equals("127.0.0.1")) {
/*  723 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  727 */       add = java.net.InetAddress.getByName(ip);
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
/* 1383 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1428 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/* 1476 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
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
/*      */           catch (java.sql.SQLException e) {
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
/* 1987 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
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
/*      */   public java.util.Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
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
/* 2180 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2186 */   private static java.util.Map<String, Long> _jspx_dependants = new HashMap(5);
/* 2187 */   static { _jspx_dependants.put("/jsp/includes/ShowUrlPerformanceLeftPage.jspf", Long.valueOf(1473429417000L));
/* 2188 */     _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2189 */     _jspx_dependants.put("/jsp/includes/urlUtil.jspf", Long.valueOf(1473429417000L));
/* 2190 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2191 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/* 2234 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2238 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2266 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2267 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2268 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2269 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2270 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2271 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2272 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2273 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2274 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2278 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2279 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2280 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2281 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2282 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2283 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/* 2284 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2285 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2286 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2287 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2288 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2289 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2290 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2291 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/* 2292 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2293 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2294 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/* 2295 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest_005fnobody.release();
/* 2296 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction.release();
/* 2297 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2298 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2299 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.release();
/* 2300 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2301 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2302 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.release();
/* 2303 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2304 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/* 2305 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2306 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.release();
/* 2307 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.release();
/* 2308 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.release();
/* 2309 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody.release();
/* 2310 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty.release();
/* 2311 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/* 2312 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2319 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/* 2322 */     JspWriter out = null;
/* 2323 */     Object page = this;
/* 2324 */     JspWriter _jspx_out = null;
/* 2325 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2329 */       response.setContentType("text/html;charset=UTF-8");
/* 2330 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2332 */       _jspx_page_context = pageContext;
/* 2333 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2334 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2335 */       session = pageContext.getSession();
/* 2336 */       out = pageContext.getOut();
/* 2337 */       _jspx_out = out;
/*      */       
/* 2339 */       out.write("<!DOCTYPE html>\n");
/* 2340 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> \n");
/* 2341 */       out.write(10);
/*      */       
/* 2343 */       request.setAttribute("HelpKey", "Configuring URL");
/*      */       
/* 2345 */       out.write("\n\n\n\n\n\n\n\n");
/* 2346 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2347 */       out.write(10);
/* 2348 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2350 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2351 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2352 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2354 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2356 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2358 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2360 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2361 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2362 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2363 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2366 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2367 */         String available = null;
/* 2368 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2369 */         out.write(10);
/*      */         
/* 2371 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2372 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2373 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2375 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2377 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2379 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2381 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2382 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2383 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2384 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2387 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2388 */           String unavailable = null;
/* 2389 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2390 */           out.write(10);
/*      */           
/* 2392 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2393 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2394 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2396 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2398 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2400 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2402 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2403 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2404 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2405 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2408 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2409 */             String unmanaged = null;
/* 2410 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2411 */             out.write(10);
/*      */             
/* 2413 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2414 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2415 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2417 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2419 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2421 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2423 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2424 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2425 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2426 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2429 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2430 */               String scheduled = null;
/* 2431 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2432 */               out.write(10);
/*      */               
/* 2434 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2435 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2436 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2438 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2440 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2442 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2444 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2445 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2446 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2447 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2450 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2451 */                 String critical = null;
/* 2452 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2453 */                 out.write(10);
/*      */                 
/* 2455 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2456 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2457 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2459 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2461 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2463 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2465 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2466 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2467 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2468 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2471 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2472 */                   String clear = null;
/* 2473 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2474 */                   out.write(10);
/*      */                   
/* 2476 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2477 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2478 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2480 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2482 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2484 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2486 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2487 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2488 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2489 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2492 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2493 */                     String warning = null;
/* 2494 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2495 */                     out.write(10);
/* 2496 */                     out.write(10);
/*      */                     
/* 2498 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2499 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2501 */                     out.write(10);
/* 2502 */                     out.write(10);
/* 2503 */                     out.write(10);
/* 2504 */                     out.write(10);
/* 2505 */                     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = null;
/* 2506 */                     mo = (com.adventnet.appmanager.client.resourcemanagement.ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2507 */                     if (mo == null) {
/* 2508 */                       mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
/* 2509 */                       _jspx_page_context.setAttribute("mo", mo, 1);
/*      */                     }
/* 2511 */                     out.write(10);
/* 2512 */                     Hashtable applications = null;
/* 2513 */                     synchronized (application) {
/* 2514 */                       applications = (Hashtable)_jspx_page_context.getAttribute("applications", 4);
/* 2515 */                       if (applications == null) {
/* 2516 */                         applications = new Hashtable();
/* 2517 */                         _jspx_page_context.setAttribute("applications", applications, 4);
/*      */                       }
/*      */                     }
/* 2520 */                     out.write(32);
/* 2521 */                     out.write(10);
/* 2522 */                     out.write(10);
/*      */                     
/* 2524 */                     String customHeaders = (String)((org.apache.struts.action.DynaActionForm)request.getAttribute("UrlForm")).get("customHeaders");
/* 2525 */                     customHeaders = (customHeaders == null) || (customHeaders.equals("null")) || (customHeaders.equals("")) ? "" : customHeaders;
/* 2526 */                     String resourceid = request.getParameter("resourceid");
/* 2527 */                     String haid = request.getParameter("haid");
/* 2528 */                     String host = "http://localhost";
/* 2529 */                     String port = "80";
/* 2530 */                     org.apache.struts.util.TokenProcessor token = org.apache.struts.util.TokenProcessor.getInstance();
/* 2531 */                     token.saveToken(request);
/*      */                     
/* 2533 */                     out.write("\n<html>\n\t<head>\n\t\t<LINK REL=StyleSheet HREF=\"/images/urlForm.css\" TYPE=\"text/css\" />\n\t\t<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n\t</head>    \n    \n<!--  Your area begins here -->   \n\n\t");
/*      */                     
/* 2535 */                     org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f0 = (org.apache.struts.taglib.tiles.InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(org.apache.struts.taglib.tiles.InsertTag.class);
/* 2536 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2537 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2539 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2540 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2541 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2543 */                         out.write(10);
/* 2544 */                         out.write(9);
/* 2545 */                         if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2547 */                         out.write(10);
/* 2548 */                         out.write(9);
/* 2549 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2551 */                         out.write(10);
/* 2552 */                         out.write(9);
/* 2553 */                         if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2555 */                         out.write(10);
/* 2556 */                         out.write(9);
/*      */                         
/* 2558 */                         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2559 */                         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2560 */                         _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2562 */                         _jspx_th_c_005fif_005f1.setTest("${!empty method}");
/* 2563 */                         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2564 */                         if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                           for (;;) {
/* 2566 */                             out.write(10);
/* 2567 */                             out.write(9);
/* 2568 */                             out.write(9);
/* 2569 */                             out.write(10);
/* 2570 */                             out.write(9);
/* 2571 */                             out.write(9);
/*      */                             
/* 2573 */                             PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2574 */                             _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2575 */                             _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_c_005fif_005f1);
/*      */                             
/* 2577 */                             _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */                             
/* 2579 */                             _jspx_th_tiles_005fput_005f3.setType("string");
/* 2580 */                             int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2581 */                             if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2582 */                               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2583 */                                 out = _jspx_page_context.pushBody();
/* 2584 */                                 _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2585 */                                 _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2588 */                                 out.write(10);
/* 2589 */                                 out.write(9);
/* 2590 */                                 out.write(9);
/* 2591 */                                 if (_jspx_meth_c_005fset_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 2593 */                                 out.write(10);
/* 2594 */                                 out.write(9);
/* 2595 */                                 out.write(9);
/*      */                                 
/* 2597 */                                 ArrayList attribIDs = new ArrayList();
/* 2598 */                                 ArrayList resIDs = new ArrayList();
/* 2599 */                                 resIDs.add(resourceid);
/*      */                                 
/* 2601 */                                 out.write(10);
/* 2602 */                                 out.write(9);
/* 2603 */                                 out.write(9);
/*      */                                 
/* 2605 */                                 IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2606 */                                 _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2607 */                                 _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 2609 */                                 _jspx_th_c_005fif_005f2.setTest("${param.type=='UrlMonitor'}");
/* 2610 */                                 int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2611 */                                 if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                   for (;;) {
/* 2613 */                                     out.write("   \n\t\t\t");
/* 2614 */                                     if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                       return;
/* 2616 */                                     out.write("\n\t\t\t");
/* 2617 */                                     if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                       return;
/* 2619 */                                     out.write("\n\t\t\t");
/* 2620 */                                     if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                       return;
/* 2622 */                                     out.write("\n\t\t\t");
/*      */                                     
/* 2624 */                                     attribIDs.add("400");
/* 2625 */                                     attribIDs.add("401");
/* 2626 */                                     attribIDs.add("402");
/*      */                                     
/* 2628 */                                     out.write(10);
/* 2629 */                                     out.write(9);
/* 2630 */                                     out.write(9);
/* 2631 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2632 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2636 */                                 if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2637 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                                 }
/*      */                                 
/* 2640 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2641 */                                 out.write(10);
/* 2642 */                                 out.write(9);
/* 2643 */                                 out.write(9);
/*      */                                 
/* 2645 */                                 IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2646 */                                 _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2647 */                                 _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 2649 */                                 _jspx_th_c_005fif_005f3.setTest("${param.type=='UrlEle'}");
/* 2650 */                                 int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2651 */                                 if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                   for (;;) {
/* 2653 */                                     out.write("\n\t\t\t");
/* 2654 */                                     if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 2656 */                                     out.write("\n\t\t\t");
/* 2657 */                                     if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 2659 */                                     out.write("\n\t\t\t");
/* 2660 */                                     if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 2662 */                                     out.write("\n\t\t\t");
/*      */                                     
/* 2664 */                                     attribIDs.add("408");
/* 2665 */                                     attribIDs.add("409");
/* 2666 */                                     attribIDs.add("410");
/*      */                                     
/* 2668 */                                     out.write(10);
/* 2669 */                                     out.write(9);
/* 2670 */                                     out.write(9);
/* 2671 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2672 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2676 */                                 if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2677 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                 }
/*      */                                 
/* 2680 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2681 */                                 out.write(10);
/* 2682 */                                 out.write(9);
/* 2683 */                                 out.write(9);
/*      */                                 
/* 2685 */                                 Properties alert = getStatus(resIDs, attribIDs);
/* 2686 */                                 String healthid = (String)pageContext.findAttribute("healthid");
/* 2687 */                                 String availabilityid = (String)pageContext.findAttribute("availabilityid");
/* 2688 */                                 String responseid = (String)pageContext.findAttribute("responseid");
/*      */                                 
/* 2690 */                                 out.write(10);
/* 2691 */                                 out.write(9);
/* 2692 */                                 out.write(9);
/* 2693 */                                 out.write("\n<!--$Id$-->\n\n\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td width=\"90%\" height=\"21\" class=\"leftlinksheading\">\n    ");
/* 2694 */                                 out.print(FormatUtil.getString("am.webclient.urlmonitor.type.text"));
/* 2695 */                                 out.write("\n      </td>\n  </tr>\n      ");
/* 2696 */                                 if (_jspx_meth_c_005fif_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 2698 */                                 out.write("\n      ");
/* 2699 */                                 if (_jspx_meth_c_005fif_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 2701 */                                 out.write(10);
/* 2702 */                                 out.write(9);
/* 2703 */                                 out.write(9);
/* 2704 */                                 if (_jspx_meth_c_005fif_005f6(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 2706 */                                 out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/* 2707 */                                 if (_jspx_meth_c_005fif_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 2709 */                                 out.write("\n    ");
/* 2710 */                                 out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2711 */                                 out.write("\n    ");
/* 2712 */                                 if (_jspx_meth_c_005fif_005f8(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 2714 */                                 out.write("\n     </td>\n  </tr>\n  ");
/*      */                                 
/* 2716 */                                 IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2717 */                                 _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2718 */                                 _jspx_th_c_005fif_005f9.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 2720 */                                 _jspx_th_c_005fif_005f9.setTest("${!empty ADMIN || !empty DEMO}");
/* 2721 */                                 int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2722 */                                 if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                   for (;;) {
/* 2724 */                                     out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n    <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 2725 */                                     if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f9, _jspx_page_context))
/*      */                                       return;
/* 2727 */                                     out.write("\" class=\"new-left-links\">\n    ");
/* 2728 */                                     out.print(ALERTCONFIG_TEXT);
/* 2729 */                                     out.write("\n    </a>\n\n     </td>\n  </tr>\n  ");
/* 2730 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2731 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2735 */                                 if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2736 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                 }
/*      */                                 
/* 2739 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2740 */                                 out.write(10);
/* 2741 */                                 if (com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled()) {
/* 2742 */                                   out.write(32);
/* 2743 */                                   out.write(10);
/*      */                                   
/* 2745 */                                   PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2746 */                                   _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2747 */                                   _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                   
/* 2749 */                                   _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2750 */                                   int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2751 */                                   if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                     for (;;) {
/* 2753 */                                       out.write("\n  <tr>\n   <td height=\"21\" class=\"leftlinkstd\">\n   ");
/*      */                                       
/* 2755 */                                       String urlType = request.getParameter("type");
/* 2756 */                                       if ((urlType != null) && (urlType.equals("RBMURL")))
/*      */                                       {
/*      */ 
/* 2759 */                                         out.write("\n\t\t\t<a href=\"javascript:toggleDiv('edit')\"  class=\"new-left-links\">\n\t\t");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 2763 */                                         String temphaid = request.getParameter("haid");
/*      */                                         try
/*      */                                         {
/* 2766 */                                           Integer.parseInt(temphaid);
/*      */                                           
/* 2768 */                                           out.write("\n        \t<a target=\"mas_window\" href=\"/updateUrl.do?haid=");
/* 2769 */                                           if (_jspx_meth_c_005fout_005f7(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                                             return;
/* 2771 */                                           out.write("&actionmethod=editUrlMonitor&resourceid=");
/* 2772 */                                           if (_jspx_meth_c_005fout_005f8(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                                             return;
/* 2774 */                                           out.write("&type=");
/* 2775 */                                           out.print(request.getParameter("type"));
/* 2776 */                                           if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                                             return;
/* 2778 */                                           out.write("&aam_jump=true&editPage=true\"  class=\"new-left-links\">\n        \t");
/*      */ 
/*      */                                         }
/*      */                                         catch (NumberFormatException ne)
/*      */                                         {
/*      */ 
/* 2784 */                                           out.write("\n        \t<a target=\"mas_window\" href=\"/updateUrl.do?actionmethod=editUrlMonitor&resourceid=");
/* 2785 */                                           if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                                             return;
/* 2787 */                                           out.write("&type=");
/* 2788 */                                           out.print(request.getParameter("type"));
/* 2789 */                                           if (_jspx_meth_c_005fout_005f11(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                                             return;
/* 2791 */                                           out.write("&aam_jump=true&editPage=true\"  class=\"new-left-links\">\n        \t");
/*      */                                         }
/*      */                                       }
/*      */                                       
/*      */ 
/*      */ 
/* 2797 */                                       out.write(10);
/* 2798 */                                       out.write(32);
/* 2799 */                                       out.write(32);
/* 2800 */                                       out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2801 */                                       out.write("\n   </td>\n  </tr>\n ");
/* 2802 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2803 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2807 */                                   if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2808 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                   }
/*      */                                   
/* 2811 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2812 */                                   out.write(10);
/*      */                                 }
/* 2814 */                                 out.write(10);
/* 2815 */                                 out.write(32);
/* 2816 */                                 out.write(32);
/*      */                                 
/* 2818 */                                 IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2819 */                                 _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2820 */                                 _jspx_th_c_005fif_005f10.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 2822 */                                 _jspx_th_c_005fif_005f10.setTest("${!empty ADMIN || !empty DEMO}");
/* 2823 */                                 int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2824 */                                 if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                   for (;;) {
/* 2826 */                                     out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                                     
/* 2828 */                                     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2829 */                                     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2830 */                                     _jspx_th_c_005fif_005f11.setParent(_jspx_th_c_005fif_005f10);
/*      */                                     
/* 2832 */                                     _jspx_th_c_005fif_005f11.setTest("${param.actionmethod!='editUrlMonitor'}");
/* 2833 */                                     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2834 */                                     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                                       for (;;) {
/* 2836 */                                         out.write("\n    ");
/*      */                                         
/* 2838 */                                         String urlType = request.getParameter("type");
/* 2839 */                                         if ((urlType != null) && (urlType.equals("RBMURL")))
/*      */                                         {
/*      */ 
/* 2842 */                                           out.write("\n\t\t\t\t<a href=\"javascript:toggleDiv('edit')\"  class=\"new-left-links\">\n\t\t\t");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 2847 */                                           String temphaid = request.getParameter("haid");
/*      */                                           try
/*      */                                           {
/* 2850 */                                             Integer.parseInt(temphaid);
/*      */                                             
/* 2852 */                                             out.write("\n    \t<a href=\"/updateUrl.do?haid=");
/* 2853 */                                             if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                               return;
/* 2855 */                                             out.write("&actionmethod=editUrlMonitor&resourceid=");
/* 2856 */                                             if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                               return;
/* 2858 */                                             out.write("&type=");
/* 2859 */                                             out.print(request.getParameter("type"));
/* 2860 */                                             if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                               return;
/* 2862 */                                             out.write("\"  class=\"new-left-links\">\n    \t");
/*      */ 
/*      */ 
/*      */                                           }
/*      */                                           catch (NumberFormatException ne)
/*      */                                           {
/*      */ 
/* 2869 */                                             out.write("\n    \t<a href=\"/updateUrl.do?actionmethod=editUrlMonitor&resourceid=");
/* 2870 */                                             if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                               return;
/* 2872 */                                             out.write("&type=");
/* 2873 */                                             out.print(request.getParameter("type"));
/* 2874 */                                             if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                               return;
/* 2876 */                                             out.write("\"  class=\"new-left-links\">\n    \t");
/*      */                                           }
/*      */                                         }
/*      */                                         
/*      */ 
/* 2881 */                                         out.write(10);
/* 2882 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 2883 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2887 */                                     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 2888 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                                     }
/*      */                                     
/* 2891 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2892 */                                     out.write(10);
/* 2893 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2894 */                                     out.write("\n\n    ");
/* 2895 */                                     if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                       return;
/* 2897 */                                     out.write("</td>\n  </tr>\n  ");
/* 2898 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2899 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2903 */                                 if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2904 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                 }
/*      */                                 
/* 2907 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2908 */                                 out.write(10);
/* 2909 */                                 out.write(32);
/* 2910 */                                 out.write(32);
/* 2911 */                                 String type = request.getParameter("type");
/* 2912 */                                 if (type != null)
/*      */                                 {
/* 2914 */                                   out.write("\n\n\n\n\n\n  <script language=\"JavaScript\">\n\tfunction confirmDelete()\n \t {\n ");
/*      */                                   
/* 2916 */                                   String rtype = request.getParameter("type");
/* 2917 */                                   if ((rtype != null) && ((rtype.equals("RBMURL")) || (rtype.equals("RBM"))))
/*      */                                   {
/* 2919 */                                     rtype = "RBM";
/*      */                                   }
/* 2921 */                                   else if ((rtype != null) && (rtype.equals("UrlEle")))
/*      */                                   {
/* 2923 */                                     rtype = "UrlSeq";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2927 */                                     rtype = "UrlMonitor";
/*      */                                   }
/*      */                                   
/* 2930 */                                   out.write("\n          var s = confirm(\"");
/* 2931 */                                   out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 2932 */                                   out.write("\")\n           if (s)\n            document.location.href=\"/deleteMO.do?method=deleteMO&type=");
/* 2933 */                                   out.print(rtype);
/* 2934 */                                   out.write("&select=");
/* 2935 */                                   if (_jspx_meth_c_005fout_005f17(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                     return;
/* 2937 */                                   out.write("\";\n\t }\n\t function confirmManage()\n \t {\n\t  var s = confirm(\"");
/* 2938 */                                   out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2939 */                                   out.write("\");\n\t  if (s)\n \t\tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2940 */                                   if (_jspx_meth_c_005fout_005f18(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                     return;
/* 2942 */                                   out.write("\";\n\t }\n\n         function confirmUnManage()\n \t {\n        \t ");
/* 2943 */                                   if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                     return;
/* 2945 */                                   out.write("\n\t\t  var show_msg=\"false\";\n\t      var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2946 */                                   out.print(request.getParameter("resourceid"));
/* 2947 */                                   out.write("; //No i18n\n\t      $.ajax({\n\t        type:'POST', //No i18n\n\t        url:url,\n\t        async:false,\n\t        success: function(data)\n\t        {\n\t          show_msg=data\n\t        }\n\t      });\n\t      if(show_msg.indexOf(\"true\")>-1)\n\t      {\n\t          alert(\"");
/* 2948 */                                   out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2949 */                                   out.write("\");\n    \t      document.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2950 */                                   if (_jspx_meth_c_005fout_005f19(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                     return;
/* 2952 */                                   out.write("\";\n         }\n       else { \n    \t   var s = confirm(\"");
/* 2953 */                                   out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2954 */                                   out.write("\");\n    \t\tif (s){\n  \t\t      document.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2955 */                                   if (_jspx_meth_c_005fout_005f20(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                     return;
/* 2957 */                                   out.write("\"; //No I18N\n\t\t\t\t}\n  \t\t } \n\t}\n  </script>\n  ");
/*      */                                   
/* 2959 */                                   IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2960 */                                   _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 2961 */                                   _jspx_th_c_005fif_005f13.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                   
/* 2963 */                                   _jspx_th_c_005fif_005f13.setTest("${!empty ADMIN || !empty DEMO}");
/* 2964 */                                   int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 2965 */                                   if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                     for (;;) {
/* 2967 */                                       out.write(10);
/*      */                                       
/* 2969 */                                       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2970 */                                       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2971 */                                       _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f13);
/*      */                                       
/* 2973 */                                       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2974 */                                       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2975 */                                       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                         for (;;) {
/* 2977 */                                           out.write("\n  <tr>\n    <td class=\"leftlinkstd\" >\n  <A href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 2978 */                                           out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 2979 */                                           out.write("</A></td>\n\n  </tr>\n");
/* 2980 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2981 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2985 */                                       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2986 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                       }
/*      */                                       
/* 2989 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2990 */                                       out.write(10);
/* 2991 */                                       out.write(10);
/*      */                                       
/* 2993 */                                       PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2994 */                                       _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2995 */                                       _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f13);
/*      */                                       
/* 2997 */                                       _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 2998 */                                       int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2999 */                                       if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                         for (;;) {
/* 3001 */                                           out.write("\n\n<td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3002 */                                           out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3003 */                                           out.write("</a></td>\n\n");
/* 3004 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3005 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3009 */                                       if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3010 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                       }
/*      */                                       
/* 3013 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3014 */                                       out.write("\n\n\n  ");
/* 3015 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3016 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3020 */                                   if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3021 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                                   }
/*      */                                   
/* 3024 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3025 */                                   out.write("\n\n\n\n  <!--tr>\n    <td height=\"21\" class=\"leftlinkstd\"> <a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=UrlMonitor\"\n      class=\"new-left-links\">Add URL monitor</a></td>\n  </tr-->\n\n\n\n");
/*      */                                 }
/*      */                                 
/* 3028 */                                 if ((type != null) && (!type.equals("UrlEle")))
/*      */                                 {
/* 3030 */                                   Hashtable globals = (Hashtable)application.getAttribute("globalconfig");
/* 3031 */                                   String allowOperatorManage = (String)globals.get("allowOperatorManage");
/* 3032 */                                   Boolean showManageUnmanage = Boolean.valueOf(false);
/*      */                                   
/* 3034 */                                   out.write(10);
/* 3035 */                                   out.write(32);
/* 3036 */                                   out.write(32);
/*      */                                   
/* 3038 */                                   PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3039 */                                   _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3040 */                                   _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                   
/* 3042 */                                   _jspx_th_logic_005fpresent_005f3.setRole("ADMIN,DEMO");
/* 3043 */                                   int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3044 */                                   if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                     for (;;) {
/* 3046 */                                       out.write("\n    ");
/*      */                                       
/* 3048 */                                       showManageUnmanage = Boolean.valueOf(true);
/*      */                                       
/* 3050 */                                       out.write(10);
/* 3051 */                                       out.write(32);
/* 3052 */                                       out.write(32);
/* 3053 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3054 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3058 */                                   if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3059 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                                   }
/*      */                                   
/* 3062 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3063 */                                   out.write(10);
/* 3064 */                                   out.write(32);
/* 3065 */                                   out.write(32);
/*      */                                   
/* 3067 */                                   PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3068 */                                   _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3069 */                                   _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                   
/* 3071 */                                   _jspx_th_logic_005fpresent_005f4.setRole("OPERATOR");
/* 3072 */                                   int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3073 */                                   if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                     for (;;) {
/* 3075 */                                       out.write("\n    ");
/*      */                                       
/* 3077 */                                       if (allowOperatorManage.equals("false")) {
/* 3078 */                                         showManageUnmanage = Boolean.valueOf(false);
/*      */                                       }
/* 3080 */                                       else if (allowOperatorManage.equals("true")) {
/* 3081 */                                         showManageUnmanage = Boolean.valueOf(true);
/*      */                                       }
/*      */                                       
/* 3084 */                                       out.write(10);
/* 3085 */                                       out.write(32);
/* 3086 */                                       out.write(32);
/* 3087 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3088 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3092 */                                   if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3093 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                   }
/*      */                                   
/* 3096 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3097 */                                   out.write("\n    ");
/* 3098 */                                   if (showManageUnmanage.booleanValue()) {
/* 3099 */                                     out.write("\n    <tr>\n    ");
/*      */                                     
/* 3101 */                                     if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                                     {
/*      */ 
/* 3104 */                                       out.write("\n      <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 3105 */                                       out.print(FormatUtil.getString("Manage"));
/* 3106 */                                       out.write("</A></td>\n      ");
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/* 3112 */                                       out.write("\n      <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 3113 */                                       out.print(FormatUtil.getString("UnManage"));
/* 3114 */                                       out.write("</A></td>\n      ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 3118 */                                     out.write("\n    </tr>\n          ");
/*      */                                   }
/*      */                                 }
/* 3121 */                                 out.write(10);
/* 3122 */                                 out.write(32);
/* 3123 */                                 out.write(32);
/*      */                                 
/* 3125 */                                 IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3126 */                                 _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3127 */                                 _jspx_th_c_005fif_005f14.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3129 */                                 _jspx_th_c_005fif_005f14.setTest("${!empty ADMIN || !empty DEMO }");
/* 3130 */                                 int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3131 */                                 if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                   for (;;) {
/* 3133 */                                     out.write("\n      \t<tr>\n          \t <td colspan=\"2\" class=\"leftlinkstd\">\n          \t ");
/* 3134 */                                     out.print(com.adventnet.appmanager.fault.FaultUtil.getAlertTemplateURL(resourceid, request.getParameter("resourcename"), "UrlMonitor", "HTTP URL monitor"));
/* 3135 */                                     out.write("\n          \t </td>\n         \t</tr>\n  ");
/* 3136 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3137 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3141 */                                 if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3142 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                 }
/*      */                                 
/* 3145 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3146 */                                 out.write(10);
/* 3147 */                                 out.write(32);
/* 3148 */                                 out.write(32);
/*      */                                 
/* 3150 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3151 */                                 _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3152 */                                 _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3154 */                                 _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 3155 */                                 int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3156 */                                 if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                   for (;;) {
/* 3158 */                                     out.write("\n    ");
/*      */                                     
/* 3160 */                                     String resourceid_poll = request.getParameter("resourceid");
/* 3161 */                                     String resourcetype_poll = request.getParameter("type");
/* 3162 */                                     if (!resourcetype_poll.equals("UrlEle"))
/*      */                                     {
/*      */ 
/* 3165 */                                       out.write("\n      <tr>\n      <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n      <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3166 */                                       out.print(resourceid_poll);
/* 3167 */                                       out.write("&resourcetype=");
/* 3168 */                                       out.print(resourcetype_poll);
/* 3169 */                                       out.write("\" class=\"new-left-links\"> ");
/* 3170 */                                       out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3171 */                                       out.write("</a></td>\n    </tr>\n    ");
/*      */                                     }
/* 3173 */                                     out.write("\n    ");
/* 3174 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3175 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3179 */                                 if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3180 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                 }
/*      */                                 
/* 3183 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3184 */                                 out.write("\n    ");
/*      */                                 
/* 3186 */                                 PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3187 */                                 _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3188 */                                 _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3190 */                                 _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 3191 */                                 int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3192 */                                 if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 3194 */                                     out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3195 */                                     out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3196 */                                     out.write("</a></td>\n          </td>\n    ");
/* 3197 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3198 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3202 */                                 if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3203 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                                 }
/*      */                                 
/* 3206 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3207 */                                 out.write("\n</table>\n <br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3208 */                                 out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 3209 */                                 out.write("</td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3210 */                                 out.print(resourceid);
/* 3211 */                                 out.write("&attributeid=");
/* 3212 */                                 if (_jspx_meth_c_005fout_005f21(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 3214 */                                 out.write("')\" class=\"new-left-links\">");
/* 3215 */                                 out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3216 */                                 out.write("</a> </td>\n    <td><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3217 */                                 out.print(resourceid);
/* 3218 */                                 out.write("&attributeid=");
/* 3219 */                                 if (_jspx_meth_c_005fout_005f22(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 3221 */                                 out.write("')\">");
/* 3222 */                                 out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + healthid)));
/* 3223 */                                 out.write("</a></td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3224 */                                 out.print(resourceid);
/* 3225 */                                 out.write("&attributeid=");
/* 3226 */                                 if (_jspx_meth_c_005fout_005f23(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 3228 */                                 out.write("')\" class=\"new-left-links\">");
/* 3229 */                                 out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 3230 */                                 out.write("</a> </td>\n    <td height=\"25\"><a  href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3231 */                                 out.print(resourceid);
/* 3232 */                                 out.write("&attributeid=");
/* 3233 */                                 if (_jspx_meth_c_005fout_005f24(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 3235 */                                 out.write("')\">");
/* 3236 */                                 out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + availabilityid)));
/* 3237 */                                 out.write("</a></td>\n  </tr>\n</table>\n<br>\n");
/* 3238 */                                 out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                                 
/*      */ 
/*      */ 
/* 3242 */                                 boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3243 */                                 if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition)
/*      */                                 {
/* 3245 */                                   showAssociatedBSG = false;
/*      */                                   
/*      */ 
/*      */ 
/* 3249 */                                   com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getInstance();Properties assBsgSiteProp = com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getSiteProp(request);
/* 3250 */                                   com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getInstance();String customerId = com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getCustomerId(request);
/* 3251 */                                   String loginName = request.getUserPrincipal().getName();
/* 3252 */                                   com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getInstance();boolean showAllBSGs = com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                                   
/* 3254 */                                   if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                                   {
/* 3256 */                                     showAssociatedBSG = true;
/*      */                                   }
/*      */                                 }
/* 3259 */                                 String monitorType = request.getParameter("type");
/* 3260 */                                 ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3261 */                                 boolean mon = conf1.isConfMonitor(monitorType);
/* 3262 */                                 if (showAssociatedBSG)
/*      */                                 {
/* 3264 */                                   Hashtable associatedmgs = new Hashtable();
/* 3265 */                                   String resId = request.getParameter("resourceid");
/* 3266 */                                   request.setAttribute("associatedmgs", com.adventnet.appmanager.fault.FaultUtil.getAdminAssociatedMG(resId, request));
/* 3267 */                                   if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                                   {
/* 3269 */                                     mon = false;
/*      */                                   }
/*      */                                   
/* 3272 */                                   if (!mon)
/*      */                                   {
/* 3274 */                                     out.write(10);
/* 3275 */                                     out.write(10);
/*      */                                     
/* 3277 */                                     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3278 */                                     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3279 */                                     _jspx_th_c_005fif_005f15.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                     
/* 3281 */                                     _jspx_th_c_005fif_005f15.setTest("${!empty associatedmgs}");
/* 3282 */                                     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3283 */                                     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                                       for (;;) {
/* 3285 */                                         out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3286 */                                         out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3287 */                                         out.write("</td>\n        </tr>\n        ");
/*      */                                         
/* 3289 */                                         ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3290 */                                         _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3291 */                                         _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f15);
/*      */                                         
/* 3293 */                                         _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                                         
/* 3295 */                                         _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                                         
/* 3297 */                                         _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3298 */                                         int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                         try {
/* 3300 */                                           int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3301 */                                           if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                             for (;;) {
/* 3303 */                                               out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3304 */                                               if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3362 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 3363 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 3306 */                                               out.write("&method=showApplication\" title=\"");
/* 3307 */                                               if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3362 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 3363 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 3309 */                                               out.write("\"  class=\"new-left-links\">\n         ");
/* 3310 */                                               if (_jspx_meth_c_005fset_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3362 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 3363 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 3312 */                                               out.write("\n    \t");
/* 3313 */                                               out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3314 */                                               out.write("\n         </a></td>\n        <td>");
/*      */                                               
/* 3316 */                                               PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3317 */                                               _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3318 */                                               _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                               
/* 3320 */                                               _jspx_th_logic_005fpresent_005f6.setRole("ADMIN");
/* 3321 */                                               int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3322 */                                               if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                                 for (;;) {
/* 3324 */                                                   out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3325 */                                                   if (_jspx_meth_c_005fout_005f28(_jspx_th_logic_005fpresent_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3362 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3363 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 3327 */                                                   out.write(39);
/* 3328 */                                                   out.write(44);
/* 3329 */                                                   out.write(39);
/* 3330 */                                                   out.print(resId);
/* 3331 */                                                   out.write(39);
/* 3332 */                                                   out.write(44);
/* 3333 */                                                   out.write(39);
/* 3334 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3335 */                                                   out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3336 */                                                   out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3337 */                                                   out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3338 */                                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3339 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3343 */                                               if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3344 */                                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/*      */                                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3362 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 3363 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 3347 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3348 */                                               out.write("</td>\n        </tr>\n\t");
/* 3349 */                                               int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3350 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3354 */                                           if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3362 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3363 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*      */                                         }
/*      */                                         catch (Throwable _jspx_exception)
/*      */                                         {
/*      */                                           for (;;)
/*      */                                           {
/* 3358 */                                             int tmp6730_6729 = 0; int[] tmp6730_6727 = _jspx_push_body_count_c_005fforEach_005f0; int tmp6732_6731 = tmp6730_6727[tmp6730_6729];tmp6730_6727[tmp6730_6729] = (tmp6732_6731 - 1); if (tmp6732_6731 <= 0) break;
/* 3359 */                                             out = _jspx_page_context.popBody(); }
/* 3360 */                                           _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                         } finally {
/* 3362 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 3363 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                         }
/* 3365 */                                         out.write("\n      </table>\n ");
/* 3366 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3367 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3371 */                                     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3372 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                                     }
/*      */                                     
/* 3375 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3376 */                                     out.write(10);
/* 3377 */                                     out.write(32);
/*      */                                     
/* 3379 */                                     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3380 */                                     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3381 */                                     _jspx_th_c_005fif_005f16.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                     
/* 3383 */                                     _jspx_th_c_005fif_005f16.setTest("${empty associatedmgs}");
/* 3384 */                                     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3385 */                                     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                                       for (;;) {
/* 3387 */                                         out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 3388 */                                         out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3389 */                                         out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 3390 */                                         out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3391 */                                         out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 3392 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3393 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3397 */                                     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3398 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                                     }
/*      */                                     
/* 3401 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3402 */                                     out.write(10);
/* 3403 */                                     out.write(32);
/* 3404 */                                     out.write(10);
/*      */ 
/*      */                                   }
/* 3407 */                                   else if (mon)
/*      */                                   {
/*      */ 
/*      */ 
/* 3411 */                                     out.write(10);
/*      */                                     
/* 3413 */                                     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3414 */                                     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 3415 */                                     _jspx_th_c_005fif_005f17.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                     
/* 3417 */                                     _jspx_th_c_005fif_005f17.setTest("${!empty associatedmgs}");
/* 3418 */                                     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 3419 */                                     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                                       for (;;) {
/* 3421 */                                         out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 3422 */                                         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                                           return;
/* 3424 */                                         out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                                         
/* 3426 */                                         ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3427 */                                         _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3428 */                                         _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f17);
/*      */                                         
/* 3430 */                                         _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                                         
/* 3432 */                                         _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                                         
/* 3434 */                                         _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 3435 */                                         int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                                         try {
/* 3437 */                                           int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3438 */                                           if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                             for (;;) {
/* 3440 */                                               out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 3441 */                                               if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3502 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 3503 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 3443 */                                               out.write("&method=showApplication\" title=\"");
/* 3444 */                                               if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3502 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 3503 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 3446 */                                               out.write("\"  class=\"staticlinks\">\n         ");
/* 3447 */                                               if (_jspx_meth_c_005fset_005f11(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3502 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 3503 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 3449 */                                               out.write("\n    \t");
/* 3450 */                                               out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3451 */                                               out.write("</a></span>\t\n\t\t ");
/*      */                                               
/* 3453 */                                               PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3454 */                                               _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3455 */                                               _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                               
/* 3457 */                                               _jspx_th_logic_005fpresent_005f7.setRole("ADMIN");
/* 3458 */                                               int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3459 */                                               if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                                 for (;;) {
/* 3461 */                                                   out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 3462 */                                                   if (_jspx_meth_c_005fout_005f32(_jspx_th_logic_005fpresent_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3502 */                                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 3503 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                   }
/* 3464 */                                                   out.write(39);
/* 3465 */                                                   out.write(44);
/* 3466 */                                                   out.write(39);
/* 3467 */                                                   out.print(resId);
/* 3468 */                                                   out.write(39);
/* 3469 */                                                   out.write(44);
/* 3470 */                                                   out.write(39);
/* 3471 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3472 */                                                   out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 3473 */                                                   out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3474 */                                                   out.write("\"  title=\"");
/* 3475 */                                                   if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3502 */                                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 3503 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                   }
/* 3477 */                                                   out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 3478 */                                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3479 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3483 */                                               if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3484 */                                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/*      */                                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3502 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 3503 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 3487 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3488 */                                               out.write("\n\n\t\t \t");
/* 3489 */                                               int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3490 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3494 */                                           if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3502 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 3503 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/*      */                                         }
/*      */                                         catch (Throwable _jspx_exception)
/*      */                                         {
/*      */                                           for (;;)
/*      */                                           {
/* 3498 */                                             int tmp7756_7755 = 0; int[] tmp7756_7753 = _jspx_push_body_count_c_005fforEach_005f1; int tmp7758_7757 = tmp7756_7753[tmp7756_7755];tmp7756_7753[tmp7756_7755] = (tmp7758_7757 - 1); if (tmp7758_7757 <= 0) break;
/* 3499 */                                             out = _jspx_page_context.popBody(); }
/* 3500 */                                           _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                                         } finally {
/* 3502 */                                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 3503 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                         }
/* 3505 */                                         out.write("\n\t\n\t\t\t</td>\n\t ");
/* 3506 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 3507 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3511 */                                     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 3512 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                                     }
/*      */                                     
/* 3515 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 3516 */                                     out.write(10);
/* 3517 */                                     out.write(32);
/* 3518 */                                     if (_jspx_meth_c_005fif_005f18(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                       return;
/* 3520 */                                     out.write(32);
/* 3521 */                                     out.write(10);
/*      */                                   }
/*      */                                   
/*      */                                 }
/* 3525 */                                 else if (mon)
/*      */                                 {
/*      */ 
/* 3528 */                                   out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 3529 */                                   if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                     return;
/* 3531 */                                   out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3535 */                                 out.write(9);
/* 3536 */                                 out.write(9);
/* 3537 */                                 out.write(10);
/*      */                                 
/* 3539 */                                 ArrayList menupos = new ArrayList(5);
/* 3540 */                                 if (request.isUserInRole("OPERATOR"))
/*      */                                 {
/*      */ 
/* 3543 */                                   menupos.add("179");
/* 3544 */                                   menupos.add("200");
/* 3545 */                                   menupos.add("222");
/* 3546 */                                   menupos.add("242");
/* 3547 */                                   menupos.add("158");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3553 */                                   menupos.add("350");
/*      */                                 }
/* 3555 */                                 pageContext.setAttribute("menupos", menupos);
/*      */                                 
/* 3557 */                                 out.write(10);
/* 3558 */                                 out.write(10);
/* 3559 */                                 out.write(9);
/* 3560 */                                 out.write(9);
/* 3561 */                                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3562 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3565 */                               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3566 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3569 */                             if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3570 */                               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                             }
/*      */                             
/* 3573 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3574 */                             out.write(10);
/* 3575 */                             out.write(9);
/* 3576 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3577 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3581 */                         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3582 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                         }
/*      */                         
/* 3585 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3586 */                         out.write(10);
/* 3587 */                         out.write(10);
/* 3588 */                         out.write(9);
/*      */                         
/* 3590 */                         PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3591 */                         _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3592 */                         _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 3594 */                         _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                         
/* 3596 */                         _jspx_th_tiles_005fput_005f4.setType("string");
/* 3597 */                         int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3598 */                         if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 3599 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 3600 */                             out = _jspx_page_context.pushBody();
/* 3601 */                             _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 3602 */                             _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 3605 */                             out.write(10);
/* 3606 */                             out.write(9);
/* 3607 */                             out.write(9);
/* 3608 */                             if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3610 */                             out.write(10);
/* 3611 */                             out.write(9);
/* 3612 */                             out.write(9);
/*      */                             
/* 3614 */                             Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 3615 */                             String aid = request.getParameter("haid");
/* 3616 */                             String haName = null;
/* 3617 */                             if (aid != null)
/*      */                             {
/* 3619 */                               haName = (String)ht.get(aid);
/*      */                             }
/* 3621 */                             String urlType = request.getParameter("type");
/* 3622 */                             if (urlType == null)
/*      */                             {
/* 3624 */                               urlType = "UrlMonitor";
/*      */ 
/*      */ 
/*      */                             }
/* 3628 */                             else if (urlType.equals("UrlEle"))
/*      */                             {
/* 3630 */                               urlType = "UrlSeq";
/*      */                             }
/*      */                             
/* 3633 */                             String parentName = request.getParameter("parentname");
/* 3634 */                             String parentId = request.getParameter("parentid");
/*      */                             
/* 3636 */                             out.write("\n\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >  \n\t\t");
/*      */                             
/* 3638 */                             IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3639 */                             _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 3640 */                             _jspx_th_c_005fif_005f19.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3642 */                             _jspx_th_c_005fif_005f19.setTest("${!empty wiz}");
/* 3643 */                             int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 3644 */                             if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                               for (;;) {
/* 3646 */                                 out.write("\n\t\t\t<tr>    \n\t\t\t    ");
/* 3647 */                                 if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                                   return;
/* 3649 */                                 out.write(" \n\t\t\t    ");
/*      */                                 
/* 3651 */                                 IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3652 */                                 _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 3653 */                                 _jspx_th_c_005fif_005f20.setParent(_jspx_th_c_005fif_005f19);
/*      */                                 
/* 3655 */                                 _jspx_th_c_005fif_005f20.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 3656 */                                 int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 3657 */                                 if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */                                   for (;;) {
/* 3659 */                                     out.write(" \n \t\t\t\t   <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 3660 */                                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 3661 */                                     out.write(" \n      \t\t\t\t\t&gt; ");
/* 3662 */                                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 3663 */                                     out.write(" \n      \t\t\t\t\t&gt; \n      \t\t\t\t\t");
/*      */                                     
/* 3665 */                                     if (!urlType.equals("UrlMonitor"))
/*      */                                     {
/*      */ 
/* 3668 */                                       out.write("\n      \t\t\t\t\t");
/* 3669 */                                       out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getURLSeqDetailsPage(aid, parentId, parentName, haName));
/* 3670 */                                       out.write(" \n     \t\t\t\t\t &gt; \n     \t\t\t\t\t ");
/*      */                                     }
/* 3672 */                                     out.write("\n     \t\t\t\t\t <span class=\"bcactive\">\n      \t\t\t\t\t");
/* 3673 */                                     if (_jspx_meth_c_005fout_005f33(_jspx_th_c_005fif_005f20, _jspx_page_context))
/*      */                                       return;
/* 3675 */                                     out.write("\n      \t\t\t\t</td>\n    \t\t\t");
/* 3676 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 3677 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3681 */                                 if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 3682 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */                                 }
/*      */                                 
/* 3685 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3686 */                                 out.write(" \n    \t\t\t");
/*      */                                 
/* 3688 */                                 IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3689 */                                 _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 3690 */                                 _jspx_th_c_005fif_005f21.setParent(_jspx_th_c_005fif_005f19);
/*      */                                 
/* 3692 */                                 _jspx_th_c_005fif_005f21.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 3693 */                                 int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 3694 */                                 if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                                   for (;;) {
/* 3696 */                                     out.write("\t\n    \t\t\t\t<td class=\"bcsign\"  height=\"22\" valign=\"top\"> \n    \t\t\t\t\t");
/* 3697 */                                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 3698 */                                     out.write(" \n      \t\t\t\t\t&gt; ");
/* 3699 */                                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes(urlType));
/* 3700 */                                     out.write(" &gt; \n      \t\t\t\t\t");
/*      */                                     
/* 3702 */                                     if (!urlType.equals("UrlMonitor"))
/*      */                                     {
/*      */ 
/* 3705 */                                       out.write("\n      \t\t\t\t\t");
/* 3706 */                                       out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getURLSeqDetailsPage(null, parentId, parentName, null));
/* 3707 */                                       out.write(" \n      \t\t\t\t\t&gt; \n      \t\t\t\t\t");
/*      */                                     }
/* 3709 */                                     out.write("\n      \t\t\t\t\t<span class=\"bcactive\">\n      \t\t\t\t\t");
/*      */                                     
/* 3711 */                                     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 3712 */                                     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 3713 */                                     _jspx_th_c_005fout_005f34.setParent(_jspx_th_c_005fif_005f21);
/*      */                                     
/* 3715 */                                     _jspx_th_c_005fout_005f34.setValue("${requestScope.urlmonitorname}");
/* 3716 */                                     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 3717 */                                     if (_jspx_eval_c_005fout_005f34 != 0) {
/* 3718 */                                       if (_jspx_eval_c_005fout_005f34 != 1) {
/* 3719 */                                         out = _jspx_page_context.pushBody();
/* 3720 */                                         _jspx_th_c_005fout_005f34.setBodyContent((BodyContent)out);
/* 3721 */                                         _jspx_th_c_005fout_005f34.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3724 */                                         out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/* 3725 */                                         int evalDoAfterBody = _jspx_th_c_005fout_005f34.doAfterBody();
/* 3726 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3729 */                                       if (_jspx_eval_c_005fout_005f34 != 1) {
/* 3730 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3733 */                                     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 3734 */                                       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f34); return;
/*      */                                     }
/*      */                                     
/* 3737 */                                     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f34);
/* 3738 */                                     out.write("\n      \t\t\t \t</td>\n    \t\t\t");
/* 3739 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 3740 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3744 */                                 if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 3745 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                                 }
/*      */                                 
/* 3748 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 3749 */                                 out.write(" \n    \t\t</tr>\n\t\t");
/* 3750 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 3751 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3755 */                             if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 3756 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                             }
/*      */                             
/* 3759 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3760 */                             out.write("    \n    \t</table>\n    \t");
/* 3761 */                             if (_jspx_meth_c_005fif_005f22(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3763 */                             out.write("\n\t\t\t");
/*      */                             
/* 3765 */                             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction.get(FormTag.class);
/* 3766 */                             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 3767 */                             _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3769 */                             _jspx_th_html_005fform_005f0.setAction("/updateUrl");
/*      */                             
/* 3771 */                             _jspx_th_html_005fform_005f0.setFocus("url");
/* 3772 */                             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 3773 */                             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                               for (;;) {
/* 3775 */                                 out.write(" \n\t\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">    \n  \t\t\t\t\t");
/* 3776 */                                 if (_jspx_meth_logic_005fnotEmpty_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3778 */                                 out.write(" \n  \t\t\t\t\t");
/* 3779 */                                 if (_jspx_meth_logic_005fempty_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3781 */                                 out.write(" \n  \t\t\t\t\t<tr> \n    \t\t\t\t\t<td height=\"26\" class=\"tableheading\">");
/* 3782 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlconf.tableheading.text"));
/* 3783 */                                 out.write(" \n\t\t\t\t      : <span class=\"topdesc\"> ");
/* 3784 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlconf.headingappend.text"));
/* 3785 */                                 out.write("</span></td>\n   \t\t\t\t\t</tr>\n \t\t\t\t</table>\n\t\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"1\" class=\"lrborder\">\n   \t\t\t\t\t<tr> \n     \t\t\t\t\t<td height=\"20\" width=\"30%\" class=\"bodytext label-align\">");
/* 3786 */                                 out.print(FormatUtil.getString("am.webclient.newaction.displayname"));
/* 3787 */                                 out.write("<span class=\"mandatory\">*</span></td>\n     \t\t\t\t\t<td height=\"20\" colspan=\"2\"> ");
/* 3788 */                                 if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3790 */                                 out.write(" </td>\n   \t\t\t\t\t</tr>\n   \t\t\t\t\t<tr> \n     \t\t\t\t\t<td width=\"25%\" height=\"20\" class=\"bodytext label-align\">");
/* 3791 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.urladd"));
/* 3792 */                                 out.write("<span class=\"mandatory\">*</span></td>\n     \t\t\t\t\t<td height=\"20\" colspan=\"2\"> ");
/* 3793 */                                 if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3795 */                                 out.write("</td>\n   \t\t\t\t\t</tr>\n   \t\t\t\t\t");
/* 3796 */                                 if (_jspx_meth_c_005fcatch_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3798 */                                 out.write(" \n   \t\t\t\t\t");
/* 3799 */                                 if (_jspx_meth_c_005fcatch_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3801 */                                 out.write(" \n   \t\t\t\t\t");
/* 3802 */                                 if (_jspx_meth_c_005fif_005f23(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3804 */                                 out.write(" \n \t\t\t\t\t<tr> \n     \t\t\t\t\t<td width=\"25%\" height=\"28\" class=\"bodytext label-align\">");
/* 3805 */                                 out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.pollinginterval"));
/* 3806 */                                 out.write(" <span class=\"mandatory\">*</span></td>\n     \t\t\t\t\t<td class=\"bodytext\"> ");
/* 3807 */                                 if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3809 */                                 out.write(" \n       \t\t\t\t\t");
/* 3810 */                                 out.print(FormatUtil.getString("am.webclient.urlmonitor.unitofpoll.text"));
/* 3811 */                                 out.write("</td>\n   \t\t\t\t\t</tr>\n   \t\t\t\t\t<tr> \n     \t\t\t\t\t<td width=\"24%\" height=\"25\" class=\"bodytext label-align\">");
/* 3812 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlconf.timeout.text"));
/* 3813 */                                 out.write("</td>\n     \t\t\t\t\t<td height=\"25\" class=\"bodytext\"> ");
/* 3814 */                                 if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3816 */                                 out.write(" \n       \t\t\t\t\t");
/* 3817 */                                 out.print(FormatUtil.getString("am.webclient.newaction.seconds"));
/* 3818 */                                 out.write(" </td>\n     \t\t\t\t</tr>\n\t\t\t\t</table>\n \t\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" class=\"lrborder\">\n   \t\t\t\t\t<tr> \n         \t\t\t\t<td colspan=\"2\" id=\"http_heading\">\n           \t\t\t\t\t<image id=\"http_plus\" src=\"images/arrow_right.png\" class=\"arrows\"/>&nbsp;\n             \t\t\t\t<label style=\"font-size:15px;\">");
/* 3819 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.httpconfig"));
/* 3820 */                                 out.write("</label>\n             \t\t\t\t<hr />\n         \t\t\t\t</td>\n   \t\t\t\t\t</tr>\n   \t\t\t\t\t<tr> \n     \t\t\t\t\t<td colspan=\"2\">\n         \t\t\t\t\t<div id=\"httpConfigDiv\" style=\"display:none;\">\n\t         \t\t\t\t\t<table id=\"httpConfig\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"1\">\n   \t\t\t\t\t\t\t\t\t<tr> \n    \t\t\t\t\t\t\t\t\t<td width=\"25%\" height=\"28\" class=\"bodytext label-align\">");
/* 3821 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.formsub"));
/* 3822 */                                 out.write("</td>\n    \t\t\t\t\t\t\t\t\t<td>\n    \t\t\t\t\t\t\t\t\t\t");
/* 3823 */                                 if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3825 */                                 out.write(" <span class=\"bodytext\">");
/* 3826 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.post"));
/* 3827 */                                 out.write("&nbsp;&nbsp;</span>");
/* 3828 */                                 if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3830 */                                 out.write(" <span class=\"bodytext\">");
/* 3831 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.get"));
/* 3832 */                                 out.write("</span>\n     \t\t\t\t\t\t\t\t\t</td>\n   \t\t\t\t\t\t\t\t\t</tr>\n   \t\t\t\t\t\t\t\t\t<tr> \n    \t\t\t\t \t\t\t\t\t<td height=\"20\" class=\"bodytext label-align\" valign=\"top\" >");
/* 3833 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.reqparams"));
/* 3834 */                                 out.write("<br> </td>\n    \t\t\t\t\t\t\t\t\t<td  height=\"20\" class=\"bodytext\"> ");
/* 3835 */                                 if (_jspx_meth_html_005ftextarea_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3837 */                                 out.write(" <br> ");
/* 3838 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.optionname"));
/* 3839 */                                 out.write(" </td>\n  \t\t\t\t\t\t\t\t\t</tr>\n  \t\t\t\t\t\t\t\t\t<tr> \n    \t\t\t\t\t\t\t\t\t<td height=\"25\" width=\"25%\" class=\"bodytext label-align addmonitor-label\" valign=\"top\">");
/* 3840 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.urlauthinfo"));
/* 3841 */                                 out.write("</td>\n    \t\t\t\t\t\t\t\t\t<td height=\"25\" width=\"75%\" class=\"bodytext\">\n        \t\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\">\n          \t\t\t\t\t\t\t\t\t\t<tr style=\"padding-top:5px;padding-bottom:5px;\">\n              \t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3842 */                                 if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3844 */                                 out.write("</td>\n  \t \t\t\t\t\t\t\t\t\t\t\t</tr>\n            \t\t\t\t\t\t\t\t\t<tr style=\"padding-top:5px;padding-bottom:5px;\">\n            \t\t\t\t\t\t\t\t\t\t<td colspan=\"2\" class=\"bodytext\">\n            \t\t\t\t\t\t\t\t\t\t\t<span id=\"tdSpan_password\"> </span>\n          \t\t\t\t\t\t\t\t\t\t\t\t<span id=\"modifySpan_password\"><a href=\"javascript:void(0)\" style=\"color:blue;text-decoration:underline;\" onclick=\"modifyPassword('password')\">");
/* 3845 */                                 if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3847 */                                 out.write("&nbsp;</a></span>\n           \t\t\t\t\t\t\t\t\t\t\t</td>\n         \t\t\t\t\t\t\t\t\t\t</tr>\n        \t\t\t\t\t\t\t\t\t</table>\n           \t\t\t\t\t\t\t\t</td>\n        \t\t\t\t\t\t\t</tr>\n  \t\t\t\t\t\t\t\t\t<tr> \n    \t\t\t\t\t\t\t\t\t<td width=\"25%\" height=\"25\" class=\"bodytext label-align\">");
/* 3848 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.responsecodeerror"));
/* 3849 */                                 out.write(" </td>\n    \t\t\t\t\t\t\t\t\t<td height=\"25\" class=\"bodytext\"> ");
/* 3850 */                                 if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3852 */                                 out.write(32);
/* 3853 */                                 if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3855 */                                 out.write(" </td>\n   \t\t\t\t\t\t\t\t\t</tr>\n   \t\t\t\t\t\t\t\t\t<tr>\n    \t\t\t\t\t\t\t\t\t<td></td>\n    \t\t\t\t\t\t\t\t\t<td height=\"25\"  class=\"bodytext\"> ");
/* 3856 */                                 if (_jspx_meth_html_005fmultibox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3858 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.errormessage"));
/* 3859 */                                 out.write("</td>\n  \t \t\t\t\t\t\t\t\t</tr>\n  \t \t\t\t\t\t\t\t\t<tr>\n       \t\t\t\t\t\t\t\t     <td width=\"25%\" height=\"25\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 3860 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.useragent"));
/* 3861 */                                 out.write(" </label></td>\n          \t\t\t\t\t\t\t\t <td width=\"75%\" height=\"25\" class=\"bodytext\"> ");
/* 3862 */                                 if (_jspx_meth_html_005ftextarea_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3864 */                                 out.write("</td>\n          \t\t\t\t\t\t\t</tr>\n  \t\t\t\t\t\t\t\t\t<tr id=\"advanceOptionDiv\" class=\"bodytext\">\n        \t\t\t\t\t\t\t\t<td height=\"25\" class=\"bodytext label-align addmonitor-label\" width=\"30%\" valign=\"top\" style=\"padding-top:10px;\">");
/* 3865 */                                 out.print(FormatUtil.getString("urlcreation.customheaders"));
/* 3866 */                                 out.write("</td>\n        \t\t\t\t\t\t\t\t<td colspan=\"1\" width=\"100%\">\n\t\t  \t\t\t\t\t\t\t\t\t<table border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" id=\"tableToInsert\">\n\t\t\t \t\t\t\t\t\t");
/*      */                                 
/* 3868 */                                 if (!customHeaders.equals(""))
/*      */                                 {
/* 3870 */                                   StringTokenizer headerToken = new StringTokenizer(customHeaders, "#");
/* 3871 */                                   int i = 0;
/* 3872 */                                   while (headerToken.hasMoreElements())
/*      */                                   {
/*      */                                     try
/*      */                                     {
/* 3876 */                                       i++;
/* 3877 */                                       String eachToken = headerToken.nextToken();
/* 3878 */                                       String[] header = eachToken.split("_sep_");
/*      */                                       
/* 3880 */                                       out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<tr id=\"customHeadersTr");
/* 3881 */                                       out.print(i);
/* 3882 */                                       out.write("\" class=\"trToRemove\">\n\t\t\t\t\t\t  \t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t  \t\t\t\t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"5\" border=\"0\" class=\"customHeadersTable\" width=\"65%\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"15%\"  valign=\"top\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input id=\"headerName");
/* 3883 */                                       out.print(i);
/* 3884 */                                       out.write("\" type=\"text\" name=\"headerInput\" class=\"formtext headername\" value=\"");
/* 3885 */                                       out.print(header[0]);
/* 3886 */                                       out.write("\" autocomplete=\"off\" size=\"25\" placeholder=\"Header Name\" />\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"suggestName");
/* 3887 */                                       out.print(i);
/* 3888 */                                       out.write("\" class=\"suggestname\" style=\"display:none;\"></div>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" valign=\"top\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input  id=\"headerValue");
/* 3889 */                                       out.print(i);
/* 3890 */                                       out.write("\" type=\"text\" name=\"headerInput\" class=\"formtext headervalue\" value=\"");
/* 3891 */                                       out.print(header[1]);
/* 3892 */                                       out.write("\" autocomplete=\"off\" size=\"25\" placeholder=\"Header Value\" />\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"suggestValue");
/* 3893 */                                       out.print(i);
/* 3894 */                                       out.write("\" class=\"suggestvalue\" style=\"display:none;\"></div>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" valign=\"middle\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0);\" class=\"addFieldSelector\">\t<image src=\"images/icon_plus.gif\"></image></a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" valign=\"middle\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0);\" class=\"removeFieldSelector\"><image src=\"images/icon_minus.gif\"></image></a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t  ");
/*      */                                     }
/*      */                                     catch (Exception e)
/*      */                                     {
/* 3898 */                                       e.printStackTrace();
/*      */                                     }
/*      */                                   }
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3904 */                                   out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<tr id=\"customHeadersTr1\" class=\"trToRemove\">\n\t\t\t\t  \t\t\t\t\t\t\t\t\t<td>\n                    \t\t\t\t\t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"5\" border=\"0\" class=\"customHeadersTable\" width=\"65%\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n                      \t\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" valign=\"top\">\n                    \t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" id=\"headerName\" name=\"headerInput\" class=\"formtext headername\" autocomplete=\"off\" size=\"25\" placeholder=\"Header Name\" />\n                    \t\t\t\t\t\t\t\t\t\t\t\t<div id=\"suggestName\" class=\"suggestname\" style=\"display:none;\"></div>\n                      \t\t\t\t\t\t\t\t\t\t\t</td>\n                      \t\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" valign=\"top\">\n                    \t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" id=\"headerValue\" name=\"headerInput\" class=\"formtext headervalue\" autocomplete=\"off\" size=\"25\" placeholder=\"Header Value\" />\n                    \t\t\t\t\t\t\t\t\t\t\t\t<div id=\"suggestValue\" class=\"suggestvalue\" style=\"display:none;\"></div>\n                      \t\t\t\t\t\t\t\t\t\t\t</td>\n                      \t\t\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" valign=\"middle\">\n");
/* 3905 */                                   out.write("                    \t\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0);\" class=\"addFieldSelector\"><image src=\"images/icon_plus.gif\"></image></a>\n                      \t\t\t\t\t\t\t\t\t\t\t</td>\n                      \t\t\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" valign=\"middle\">\n                    \t\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0);\" class=\"removeFieldSelector\"><image src=\"images/icon_minus.gif\"></image></a>\n                      \t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t \t\t\t\t\t\t\t");
/*      */                                 }
/* 3907 */                                 out.write("  \t\n\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t   \t\t\t\t</td>\n     \t\t\t\t\t\t\t\t</tr>\n\t         \t\t\t\t\t</table>\n\t         \t\t\t\t</div>\n\t\t\t\t\t\t</td>\n   \t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"1\" class=\"lrborder\">\n      \t\t\t\t<tr id=\"content_heading\"> \n        \t\t\t\t<td colspan=\"2\">\n          \t\t\t\t\t<image src=\"images/arrow_right.png\" id=\"content_plus\" class=\"arrows\">&nbsp;\n            \t\t\t\t<label style=\"font-size:15px;\">");
/* 3908 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.contentcheck"));
/* 3909 */                                 out.write("</label>\n            \t\t\t\t<hr />\n        \t\t\t\t</td>\n      \t\t\t\t</tr>\n      \t\t\t\t<tr>\n        \t\t\t\t<td colspan=\"2\">\n       \t\t\t\t \t\t<div id=\"contentCheckDiv\" style=\"display:none;\">\n          \t\t\t\t\t\t<table id=\"contentCheck\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"1\">\n            \t\t\t\t\t\t<tr>\n              \t\t\t\t\t\t\t<td height=\"20\" width=\"30%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 3910 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.htmlcontent"));
/* 3911 */                                 out.write(" </label></td>\n              \t\t\t\t\t\t\t<td height=\"20\" colspan=\"2\">");
/* 3912 */                                 if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3914 */                                 out.write(32);
/*      */                                 
/* 3916 */                                 CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty.get(CheckboxTag.class);
/* 3917 */                                 _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 3918 */                                 _jspx_th_html_005fcheckbox_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 3920 */                                 _jspx_th_html_005fcheckbox_005f0.setProperty("regEx");
/* 3921 */                                 int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 3922 */                                 if (_jspx_eval_html_005fcheckbox_005f0 != 0) {
/* 3923 */                                   if (_jspx_eval_html_005fcheckbox_005f0 != 1) {
/* 3924 */                                     out = _jspx_page_context.pushBody();
/* 3925 */                                     _jspx_th_html_005fcheckbox_005f0.setBodyContent((BodyContent)out);
/* 3926 */                                     _jspx_th_html_005fcheckbox_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3929 */                                     out.print(FormatUtil.getString("am.webclient.filedir.regexp.text"));
/* 3930 */                                     int evalDoAfterBody = _jspx_th_html_005fcheckbox_005f0.doAfterBody();
/* 3931 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3934 */                                   if (_jspx_eval_html_005fcheckbox_005f0 != 1) {
/* 3935 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3938 */                                 if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 3939 */                                   this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty.reuse(_jspx_th_html_005fcheckbox_005f0); return;
/*      */                                 }
/*      */                                 
/* 3942 */                                 this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 3943 */                                 out.write("</td>\n            \t\t\t\t\t\t</tr> \n            \t\t\t\t\t\t<tr>\n             \t\t\t\t\t\t\t <td width=\"25%\" height=\"25\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 3944 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.matcherror"));
/* 3945 */                                 out.write(" </label></td>\n             \t\t\t\t\t\t\t <td width=\"76%\" height=\"25\" class=\"bodytext\"> ");
/* 3946 */                                 if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3948 */                                 out.write("</td>\n           \t\t\t\t\t\t\t </tr>\n           \t\t\t\t\t\t\t <tr>\n              \t\t\t\t\t\t\t<td></td>\n              \t\t\t\t\t\t\t<td height=\"25\" class=\"bodytext\"> ");
/*      */                                 
/* 3950 */                                 CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty.get(CheckboxTag.class);
/* 3951 */                                 _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 3952 */                                 _jspx_th_html_005fcheckbox_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 3954 */                                 _jspx_th_html_005fcheckbox_005f1.setProperty("caseSensitive");
/* 3955 */                                 int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 3956 */                                 if (_jspx_eval_html_005fcheckbox_005f1 != 0) {
/* 3957 */                                   if (_jspx_eval_html_005fcheckbox_005f1 != 1) {
/* 3958 */                                     out = _jspx_page_context.pushBody();
/* 3959 */                                     _jspx_th_html_005fcheckbox_005f1.setBodyContent((BodyContent)out);
/* 3960 */                                     _jspx_th_html_005fcheckbox_005f1.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3963 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.caseSensitive"));
/* 3964 */                                     int evalDoAfterBody = _jspx_th_html_005fcheckbox_005f1.doAfterBody();
/* 3965 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3968 */                                   if (_jspx_eval_html_005fcheckbox_005f1 != 1) {
/* 3969 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3972 */                                 if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 3973 */                                   this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty.reuse(_jspx_th_html_005fcheckbox_005f1); return;
/*      */                                 }
/*      */                                 
/* 3976 */                                 this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 3977 */                                 out.write("</td>  ");
/* 3978 */                                 out.write("\n            \t\t\t\t\t\t</tr>\n            \t\t\t\t\t\t<tr>\n              \t\t\t\t\t\t\t<td><br /></td>\n            \t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n          \t\t\t\t\t</div>\n\t\t\t\t\t\t</td>\n  \t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  \t\t\t\t\t<tr> \n    \t\t\t\t\t<td width=\"30%\" height=\"28\" class=\"tablebottom\">&nbsp;</td>\n    \t\t\t\t\t<td width=\"75%\" height=\"28\" class=\"tablebottom\"> \n    \t\t\t\t\t\t");
/*      */                                 
/* 3980 */                                 IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3981 */                                 _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 3982 */                                 _jspx_th_c_005fif_005f24.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 3984 */                                 _jspx_th_c_005fif_005f24.setTest("${empty method}");
/* 3985 */                                 int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 3986 */                                 if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */                                   for (;;) {
/* 3988 */                                     out.write("\n      \t\t\t\t\t\t");
/*      */                                     
/* 3990 */                                     ButtonTag _jspx_th_html_005fbutton_005f0 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 3991 */                                     _jspx_th_html_005fbutton_005f0.setPageContext(_jspx_page_context);
/* 3992 */                                     _jspx_th_html_005fbutton_005f0.setParent(_jspx_th_c_005fif_005f24);
/*      */                                     
/* 3994 */                                     _jspx_th_html_005fbutton_005f0.setOnclick("return validateAndSubmit();");
/*      */                                     
/* 3996 */                                     _jspx_th_html_005fbutton_005f0.setStyleClass("buttons btn_highlt");
/*      */                                     
/* 3998 */                                     _jspx_th_html_005fbutton_005f0.setProperty("submitbutton1");
/*      */                                     
/* 4000 */                                     _jspx_th_html_005fbutton_005f0.setValue(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.button.addurl"));
/* 4001 */                                     int _jspx_eval_html_005fbutton_005f0 = _jspx_th_html_005fbutton_005f0.doStartTag();
/* 4002 */                                     if (_jspx_th_html_005fbutton_005f0.doEndTag() == 5) {
/* 4003 */                                       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0); return;
/*      */                                     }
/*      */                                     
/* 4006 */                                     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0);
/* 4007 */                                     out.write("\n      \t\t\t\t\t\t");
/* 4008 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 4009 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4013 */                                 if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 4014 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24); return;
/*      */                                 }
/*      */                                 
/* 4017 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 4018 */                                 out.write(" \n      \t\t\t\t\t\t");
/*      */                                 
/* 4020 */                                 IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4021 */                                 _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 4022 */                                 _jspx_th_c_005fif_005f25.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 4024 */                                 _jspx_th_c_005fif_005f25.setTest("${!empty method}");
/* 4025 */                                 int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 4026 */                                 if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */                                   for (;;) {
/* 4028 */                                     out.write("\n      \t\t\t\t\t\t\t");
/*      */                                     
/* 4030 */                                     ButtonTag _jspx_th_html_005fbutton_005f1 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 4031 */                                     _jspx_th_html_005fbutton_005f1.setPageContext(_jspx_page_context);
/* 4032 */                                     _jspx_th_html_005fbutton_005f1.setParent(_jspx_th_c_005fif_005f25);
/*      */                                     
/* 4034 */                                     _jspx_th_html_005fbutton_005f1.setOnclick("return validateAndSubmit();");
/*      */                                     
/* 4036 */                                     _jspx_th_html_005fbutton_005f1.setStyleClass("buttons btn_highlt");
/*      */                                     
/* 4038 */                                     _jspx_th_html_005fbutton_005f1.setProperty("submitbutton1");
/*      */                                     
/* 4040 */                                     _jspx_th_html_005fbutton_005f1.setValue(FormatUtil.getString("am.webclient.hostdiscovery.qengine.button.update"));
/* 4041 */                                     int _jspx_eval_html_005fbutton_005f1 = _jspx_th_html_005fbutton_005f1.doStartTag();
/* 4042 */                                     if (_jspx_th_html_005fbutton_005f1.doEndTag() == 5) {
/* 4043 */                                       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1); return;
/*      */                                     }
/*      */                                     
/* 4046 */                                     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1);
/* 4047 */                                     out.write("\n      \t\t\t\t\t\t");
/* 4048 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 4049 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4053 */                                 if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 4054 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*      */                                 }
/*      */                                 
/* 4057 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 4058 */                                 out.write(" &nbsp;");
/*      */                                 
/* 4060 */                                 ResetTag _jspx_th_html_005freset_005f0 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.get(ResetTag.class);
/* 4061 */                                 _jspx_th_html_005freset_005f0.setPageContext(_jspx_page_context);
/* 4062 */                                 _jspx_th_html_005freset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 4064 */                                 _jspx_th_html_005freset_005f0.setStyleClass("buttons btn_link");
/*      */                                 
/* 4066 */                                 _jspx_th_html_005freset_005f0.setValue(FormatUtil.getString("am.webclient.admintab.servicedesk.cancel"));
/*      */                                 
/* 4068 */                                 _jspx_th_html_005freset_005f0.setOnclick("javascript:history.back();");
/* 4069 */                                 int _jspx_eval_html_005freset_005f0 = _jspx_th_html_005freset_005f0.doStartTag();
/* 4070 */                                 if (_jspx_th_html_005freset_005f0.doEndTag() == 5) {
/* 4071 */                                   this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f0); return;
/*      */                                 }
/*      */                                 
/* 4074 */                                 this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f0);
/* 4075 */                                 out.write("\n      \t\t\t\t\t</td>\n  \t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t");
/* 4076 */                                 if (_jspx_meth_c_005fif_005f26(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 4078 */                                 out.write("\n        \t\t");
/* 4079 */                                 if (_jspx_meth_c_005fif_005f27(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 4081 */                                 out.write("\n\t\t\t");
/* 4082 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 4083 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4087 */                             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 4088 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                             }
/*      */                             
/* 4091 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 4092 */                             out.write(32);
/* 4093 */                             out.write(10);
/* 4094 */                             out.write(9);
/* 4095 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 4096 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4099 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 4100 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4103 */                         if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4104 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                         }
/*      */                         
/* 4107 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 4108 */                         out.write(10);
/* 4109 */                         out.write(9);
/* 4110 */                         if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 4112 */                         out.write(10);
/* 4113 */                         out.write(9);
/* 4114 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4115 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 4119 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4120 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 4123 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4124 */                       out.write("\n\n<!--  Your area ends here -->\n");
/* 4125 */                       out.write("\n\n\n\n\n\n<script type=\"text/javascript\">\n//startsWith function is not supported in IE so adding a shim for it. \nif (!String.prototype.startsWith) {\n  String.prototype.startsWith = function (str){\n    return this.lastIndexOf(str, 0) === 0;\n  };\n}\n\nfunction cancelModify(idString)\n{    \n    var toReplaceInput = \"\";    \n    var toReplaceModifyPart = \"<a href=\\\"javascript:void(0)\\\" style=\\\"color:blue;text-decoration:underline;\\\" onclick=\\\"modifyPassword(\\'\"+idString+\"\\')\\\">");
/* 4126 */                       if (_jspx_meth_fmt_005fmessage_005f6(_jspx_page_context))
/*      */                         return;
/* 4128 */                       out.write("&nbsp;\"+idString+\"</a>\";\n    $(\"#tdSpan_\"+idString).html(toReplaceInput);\n    $(\"#modifySpan_\"+idString).html(toReplaceModifyPart);\n}\n\nfunction modifyPassword(idString)\n{    \n  var toReplaceInput = \"<input id=\\\"toCheck\\\" class=\\\"formtext xlarge\\\" type=\\\"password\\\" value=\\\"\\\" name=\\\"\"+idString+\"\\\" placeholder=\\\"Password\\\">\";\n    var toReplaceModifyPart = \"<a href=\\\"javascript:void(0)\\\" style=\\\"color:blue\\\" onclick=\\\"cancelModify(\\'\"+idString+\"\\')\\\">");
/* 4129 */                       if (_jspx_meth_fmt_005fmessage_005f7(_jspx_page_context))
/*      */                         return;
/* 4131 */                       out.write("</a>\";\n    $(\"#tdSpan_\"+idString).html(toReplaceInput);\n    $(\"#modifySpan_\"+idString).html(toReplaceModifyPart);\n    $(\"#toCheck\").focus();\n}\n\nfunction fnOpenWindow()\n{\n  var window2=open('/html/reqparams.html','secondWindow','resizable=no,scrollbars=no,width=250,height=140'); //No I18N\n  window2.focus();\n}\n\nfunction createSuggestionList(items,id,className)\n{\n    var list = document.createElement(\"ul\");\n      for(var i in items)\n      {\n        var listItem = document.createElement(\"li\");\n        listItem.innerHTML='<a>'+items[i]+'</a>';\n        listItem.className=className;\n        listItem.id=items[i];\n        list.appendChild(listItem);\n      }\n      $(id).empty();\n      $(id).append(list);\n      $(id).show();\n}\n\nvar headersPresent = {\n        'Accept':['text/plain', 'text/html', 'text/javascript', 'application/json'],  //No I18N\n        'Accept-Charset':['utf-8', 'utf-16', 'iso-8859-1'], //No I18N\n        'Accept-Datetime':[], //No I18N\n        'Accept-Encoding':['compress','gzip','deflate','identity'], //No I18N\n");
/* 4132 */                       out.write("        'Accept-Language':['en-US'], //No I18N\n        'Authorization':['Basic '], //No I18N\n        'Cache-Control':['no-cache'], //No I18N\n        'Connection':['close'], //No I18N\n        'Content-MD5':[], //No I18N\n        'Content-Length':[], //No I18N\n        'Content-Type':['application/atom+xml','application/json','application/xml','application/x-www-form-urlencoded','multipart/form-data'],    //No I18N\n        'Cookie':[], //No I18N\n        'Date':[], //No I18N\n        'DNT':[0,1], //No I18N\n        'Expect':['100-continue'], //No I18N\n        'From':[], //No I18N\n        'Front-End-Https':['on','off'], //No I18N\n        'Host':[], //No I18N\n        'If-Match':[], //No I18N\n        'If-Modified-Since':[], //No I18N\n        'If-None-Match':[], //No I18N\n        'If-Range':[], //No I18N\n        'If-Unmodified-Since':[], //No I18N\n        'Max-Forwards':[], //No I18N\n        'Origin':[], //No I18N\n        'Pragma':['no-cache'], //No I18N\n        'Proxy-Authorization':[], //No I18N\n        'Proxy-Connection':['keep-alive'], //No I18N\n");
/* 4133 */                       out.write("        'Range':[], //No I18N\n        'Referer':[], //No I18N\n        'TE':[], //No I18N\n        'Upgrade':[], //No I18N\n        'User-Agent':[], //No I18N\n        'Via':[], //No I18N\n        'Warning':[], //No I18N\n        'X-ATT-DeviceId':[], //No I18N\n        'X-Forwarded-For':[], //No I18N\n        'X-Forwarded-Proto':['http','https'], //No I18N\n        'X-Requested-With':['XMLHttpRequest'], //No I18N\n        'X-Wap-Profile':[] //No I18N\n};\n\n$(document).ready(function()\n{\n  var position=0,valposition=0;\n  var idCount = 1;\n  $('.addFieldSelector').click(function() //NO I18N \n  {\n    idCount++;\n    var actualObj = $(this).closest('.trToRemove'); //NO I18N\n    var clonedObj = $(actualObj).clone(true);\n    $(clonedObj).find(\"input:text[name='headerInput']\").val(\"\"); //NO I18N\n    clonedObj.attr('id', 'customHeadersTr'+idCount); //NO I18N\n      $(clonedObj).find(\".headername\").attr(\"id\",\"headerName\"+idCount); //No I18N\n        $(clonedObj).find(\".headervalue\").attr(\"id\",\"headerValue\"+idCount); //No I18N\n        $(clonedObj).find(\"div.suggestname\").attr(\"id\",\"suggestName\"+idCount); //No I18N\n");
/* 4134 */                       out.write("        $(clonedObj).find(\"div.suggestvalue\").attr(\"id\",\"suggestValue\"+idCount); //No I18N\n        $(clonedObj).find(\"div\").empty();\n    clonedObj.insertAfter(actualObj);\n  });\n  $('.removeFieldSelector').click(function() //NO I18N \n  {\n    if($(\".customHeadersTable\").length > 1)\n    {\n      $(this).closest('.trToRemove').remove(); //NO I18N\n    }\n  });\n  $(\"#errorcontent\").attr(\"placeholder\", \"Error,failed,...\"); //No I18N\n  $(\"#checkfor\").attr(\"placeholder\", \"Success,Hello World,...\"); //No I18N\n  $(\"#regEx\").attr(\"placeholder\", \"[a-z]*,...\"); //No I18N\n  $(\"#userid\").attr(\"placeholder\",'");
/* 4135 */                       out.print(FormatUtil.getString("am.webclient.common.username.text"));
/* 4136 */                       out.write("'); //No I18N\n  $('.headername').keyup(function(e) //NO I18N \n  {\n      var findEle,childEle;\n      if(e.which==$.ui.keyCode.UP)\n      {\n        findEle = \":nth-child(\"+position+\")\";  //No I18N\n        childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n        document.getElementById(''+childEle[0].id).style.backgroundColor=\"#fff\";\n        position=(position>1)?position-1:1;\n        findEle = \":nth-child(\"+position+\")\"; //No I18N\n        childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n        document.getElementById(''+childEle[0].id).style.backgroundColor=\"#e6e6e6\";\n      }\n      else if(e.which==$.ui.keyCode.DOWN)\n      {   \n        var len = $(this).siblings(\"div\").children().children().length; //No I18N\n        if(position != len)\n        {\n          if(position!=0)\n          {\n            findEle = \":nth-child(\"+position+\")\"; //No I18N\n            childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n            document.getElementById(''+childEle[0].id).style.backgroundColor=\"#fff\";\n");
/* 4137 */                       out.write("          }\n          position++;\n          findEle = \":nth-child(\"+position+\")\"; //No I18N\n          childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n          document.getElementById(''+childEle[0].id).style.backgroundColor=\"#e6e6e6\";\n        }\n        else\n        {\n          findEle = \":nth-child(\"+position+\")\"; //No I18N\n          childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n          document.getElementById(''+childEle[0].id).style.backgroundColor=\"#fff\";\n          position=1;\n          findEle = \":nth-child(\"+position+\")\"; //No I18N\n          childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n          document.getElementById(''+childEle[0].id).style.backgroundColor=\"#e6e6e6\";\n        }\n      }\n      else if(e.which==$.ui.keyCode.ENTER)\n      {\n        findEle = \":nth-child(\"+position+\")\"; //No I18N\n        childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n        $(this).val(childEle[0].id);\n        $(this).siblings(\"div\").empty(); //No I18N\n");
/* 4138 */                       out.write("        $(this).siblings(\"div\").hide(); //No I18N\n      }\n      else \n      {\n        position=0;\n        var count = $(this).attr('id'); //No I18N\n        count=count.substring(10,count.length);\n        var valEntered = $(this).val();\n        var headers = Object.keys(headersPresent).filter(function(key)\n        {\n          if(key.toLowerCase().startsWith(valEntered.toLowerCase()))\n          {\n            return key;\n          }\n        });\n        if(headers.length!=0)\n        {\n          if(headers.length==1 && headers[0]==valEntered)\n          {\n            $(\"#suggestName\"+count).hide();\n            position=0;\n          }\n          else\n          {\n            createSuggestionList(headers,\"#suggestName\"+count,\"headerNameList\"); //No I18N\n          }\n        }\n        else\n        {\n          $(\"#suggestName\"+count).hide();\n          position=0;\n        }     \n      }   \n  }); \n  $('.suggestname').delegate('.headerNameList','mousedown',function(event) //No I18N\n  {\n    $(event.target).closest('.customHeadersTable').find('.headername').first().val($(event.target).text()); //No I18N\n");
/* 4139 */                       out.write("    $(event.target).closest('.suggestname').empty(); //No I18N\n    $(event.target).closest('.suggestname').hide(); //No I18N\n  });\n  $('.headername').focusout(function()\n  {\n    $('#'+$(this).siblings('.suggestname')[0].id).empty();\n    $('#'+$(this).siblings('.suggestname')[0].id).hide();\n  });\n  $('.headervalue').keyup(function(e) //NO I18N \n  {\n    if(e.which==$.ui.keyCode.UP)\n    {\n      findEle = \":nth-child(\"+valposition+\")\"; //No I18N\n      childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n      document.getElementById(''+childEle[0].id).style.backgroundColor=\"#fff\";\n      valposition=(valposition>1)?valposition-1:1;\n      findEle = \":nth-child(\"+valposition+\")\"; //No I18N\n      childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n      document.getElementById(''+childEle[0].id).style.backgroundColor=\"#e6e6e6\";\n    }\n    else if(e.which==$.ui.keyCode.DOWN)\n    {   \n      var len = $(this).siblings(\"div\").children().children().length; //No I18N\n      if(valposition != len)\n");
/* 4140 */                       out.write("      {\n        if(valposition!=0)\n        {\n          findEle = \":nth-child(\"+valposition+\")\"; //No I18N\n          childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n          document.getElementById(''+childEle[0].id).style.backgroundColor=\"#fff\";\n        }\n        valposition++;\n        findEle = \":nth-child(\"+valposition+\")\"; //No I18N\n        childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n        document.getElementById(''+childEle[0].id).style.backgroundColor=\"#e6e6e6\";\n      }\n      else\n      {\n        findEle = \":nth-child(\"+valposition+\")\"; //No I18N\n        childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n        document.getElementById(''+childEle[0].id).style.backgroundColor=\"#fff\";\n        valposition=1;\n        findEle = \":nth-child(\"+valposition+\")\"; //No I18N\n        childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n        document.getElementById(''+childEle[0].id).style.backgroundColor=\"#e6e6e6\";\n");
/* 4141 */                       out.write("      }\n    }\n    else if(e.which==$.ui.keyCode.ENTER)\n    {\n      findEle = \":nth-child(\"+valposition+\")\"; //No I18N\n      childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n      $(this).val(childEle[0].id);\n      $(this).siblings(\"div\").empty(); //No I18N\n      $(this).siblings(\"div\").hide(); //No I18N\n    }\n    else \n    {\n      valposition=0; \n      var count = $(this).attr('id'); //No I18N\n      count=count.substring(11,count.length);\n      var valEntered = $(this).val();\n      if(valEntered!==\"\")\n      {\n        var header = $(this).closest('.customHeadersTable').find('.headername').first().val(); //No I18N\n        var values = headersPresent[header];\n        var matchedValues=[];\n        for(var val in values)\n        {\n          if(values[val].toLowerCase().startsWith(valEntered.toLowerCase()))\n          {\n            matchedValues.push(values[val]);\n          }\n        }\n        if(matchedValues.length!=0)\n        {\n          createSuggestionList(matchedValues,\"#suggestValue\"+count,\"headerValueList\"); //No I18N\n");
/* 4142 */                       out.write("        }\n        else\n        {\n          $(\"#suggestValue\"+count).hide();\n        }       \n      }\n      else\n      {\n        $(\"#suggestValue\"+count).hide();\n      }\n    }\n  });\n  $('.headervalue').focusout(function()\n  {\n    $('#'+$(this).siblings('.suggestvalue')[0].id).empty();\n    $('#'+$(this).siblings('.suggestvalue')[0].id).hide();\n  }),\n  $('.suggestvalue').delegate('.headerValueList','mousedown',function(event) //No I18N\n  {\n    $(event.target).closest('.customHeadersTable').find('.headervalue').first().val($(event.target).text()); //No I18N\n    $(event.target).closest('.suggestvalue').empty(); //No I18N\n    $(event.target).closest('.suggestvalue').hide(); //No I18N\n  });\n  $('#http_heading').click(function(e)\n  {\n    if($('#http_plus').attr('src').indexOf(\"right\")>-1)\n    {\n      $('#httpConfigDiv').slideToggle(\"slow\"); //No I18N\n      $('#http_plus').attr('src','images/arrow_down.png'); //No I18N\n    }\n    else\n    {\n      $('#httpConfigDiv').slideToggle(\"slow\"); //No I18N\n      $('#http_plus').attr('src','images/arrow_right.png'); //No I18N\n");
/* 4143 */                       out.write("    }\n  });\n  $('#content_heading').click(function(e)\n  {\n    if($('#content_plus').attr('src').indexOf(\"right\")>-1)\n    {\n      $('#contentCheckDiv').slideToggle(\"slow\"); //No I18N\n      $('#content_plus').attr('src','images/arrow_down.png'); //No I18N\n    }\n    else\n    {\n      $('#contentCheckDiv').slideToggle(\"slow\"); //No I18N\n      $('#content_plus').attr('src','images/arrow_right.png'); //No I18N\n    }\n  });\n});\n\nfunction loadSite()\n{\n  document.UrlForm.haid.options.length=0;\n  var formCustomerId = document.UrlForm.organization.value;\n  var siteName;\n  var siteId;\n  var customerId;\n  var rowCount=0;\n  document.UrlForm.haid.options[rowCount++] = new Option('-Select Site-','-'); //No I18N\n  ");
/* 4144 */                       if (_jspx_meth_c_005fforEach_005f2(_jspx_page_context))
/*      */                         return;
/* 4146 */                       out.write("\n}\n\nfunction resetname(name)\n{\n  if(name='groupname')\n  {\n    document.UrlForm.groupname.value='';\n  }\n  if(name='subgroupname')\n  {\n    document.UrlForm.subgroupname.value='';\n  }\n}\n\n</script>\n");
/* 4147 */                       out.write("\n<script type=\"text/javascript\">\n\nfunction validateAndSubmit()\n{\n\tvar empties = $(\"input:text[name='headerInput']\").filter(function () {\n\t\treturn $.trim($(this).val()) == '';\n\t});\n\tif (empties.length==$(\"input:text[name='headerInput']\").length) \n \t{\n      $(\"#customHeadersId\").val(\"\");\n  \t}\n  \telse\n  \t{\n  \t\tvar temp = \"\";\n\t\ttemp = $.map($(\"input:text[name='headerInput']\"), function(vals, i) // NO I18N\n  \t\t{\n      \t\tif(vals.value=='')\n      \t\t{\n        \t\treturn \"\";\n      \t\t}\n      \t\telse if(i==0)\n  \t\t\t{\n      \t\t\treturn vals.value;\n    \t\t}\n    \t\telse if(i%2 ==0)\n    \t\t{\n      \t\t\treturn \"#\"+vals.value;  \n    \t\t}\n    \t\telse\n    \t\t{\n      \t\t\treturn \"_sep_\"+vals.value; // NO I18N\n    \t\t}\n    \t}).join('');\n  \t\t$(\"#customHeadersId\").val(temp);\n  \t}\n\tvar dispname=document.UrlForm.monitorname.value\n        if(dispname== '') \n          {\n           \n            alert('");
/* 4148 */                       out.print(FormatUtil.getString("am.webclient.common.jsalertfordisplayname.text"));
/* 4149 */                       out.write("');\n             document.UrlForm.monitorname.focus();\n                return;\n           }\n           var quote1=\"'\" ;\n\tif(dispname.indexOf(quote1) != -1) \n        {\n            alert(\"");
/* 4150 */                       out.print(FormatUtil.getString("am.webclient.common.jsalertforsinglequote.text"));
/* 4151 */                       out.write("\");\n                document.UrlForm.monitorname.focus();\n                return;\n         }\n\n\tvar url=trimAll(document.UrlForm.url.value);\t\n\tif(url == ''  )\n\t{\n\t\talert(\"");
/* 4152 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.alert.url"));
/* 4153 */                       out.write("\");\t\n\t\tdocument.UrlForm.url.focus();\n\t\treturn;\n\t}\n\tif(!checkUrlPattern(url))\n\t{\n\t\talert(\"");
/* 4154 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.alert.urladd"));
/* 4155 */                       out.write("\");\t\n\t\tdocument.UrlForm.url.focus();\n\t\treturn;\t\t\n\t}\n\tif(document.UrlForm.regEx.checked)\n    {\n      if(document.UrlForm.checkfor.value === '')\n      {\n        alert('");
/* 4156 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.regEx.emptycontent"));
/* 4157 */                       out.write("');\n        document.UrlForm.checkfor.select();\n        return;\n      }  \n    }\n\t");
/* 4158 */                       if (_jspx_meth_logic_005fpresent_005f8(_jspx_page_context))
/*      */                         return;
/* 4160 */                       out.write("\n\tvar poll=trimAll(document.UrlForm.pollInterval.value);\t\n\tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0' )\n\t{\n\t\talert(\"");
/* 4161 */                       out.print(FormatUtil.getString("am.webclient.newscript.alert.pollingintervalzero.text"));
/* 4162 */                       out.write("\");\t\n\t\tdocument.UrlForm.pollInterval.focus();\n\t\treturn;\n\t}\n\t");
/* 4163 */                       if (_jspx_meth_logic_005fpresent_005f9(_jspx_page_context))
/*      */                         return;
/* 4165 */                       out.write("\n\t\n\t\n        \n\tfrmSelectAll(document.UrlForm.requestparams)\n\tdocument.UrlForm.submit();\t\n}\n\n</script>\n</html>\n");
/*      */                     }
/* 4167 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4168 */         out = _jspx_out;
/* 4169 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4170 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4171 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4174 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4180 */     PageContext pageContext = _jspx_page_context;
/* 4181 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4183 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4184 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 4185 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4187 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 4189 */     _jspx_th_tiles_005fput_005f0.setValue("URL Monitoring");
/* 4190 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 4191 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 4192 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4193 */       return true;
/*      */     }
/* 4195 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4196 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4201 */     PageContext pageContext = _jspx_page_context;
/* 4202 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4204 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4205 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4206 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4208 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4210 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 4211 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4212 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4213 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4214 */       return true;
/*      */     }
/* 4216 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4217 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4222 */     PageContext pageContext = _jspx_page_context;
/* 4223 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4225 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4226 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 4227 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4229 */     _jspx_th_c_005fif_005f0.setTest("${empty method}");
/* 4230 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 4231 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 4233 */         out.write(10);
/* 4234 */         out.write(9);
/* 4235 */         out.write(9);
/* 4236 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 4237 */           return true;
/* 4238 */         out.write(10);
/* 4239 */         out.write(9);
/* 4240 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 4241 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4245 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4246 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4247 */       return true;
/*      */     }
/* 4249 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4250 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4255 */     PageContext pageContext = _jspx_page_context;
/* 4256 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4258 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4259 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 4260 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4262 */     _jspx_th_tiles_005fput_005f2.setName("ServerLeftArea");
/*      */     
/* 4264 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/UrlMonitorsLeftPage.jsp");
/* 4265 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 4266 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 4267 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4268 */       return true;
/*      */     }
/* 4270 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4271 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4276 */     PageContext pageContext = _jspx_page_context;
/* 4277 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4279 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 4280 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4281 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4283 */     _jspx_th_c_005fset_005f0.setVar("urlmonitorname");
/*      */     
/* 4285 */     _jspx_th_c_005fset_005f0.setScope("request");
/*      */     
/* 4287 */     _jspx_th_c_005fset_005f0.setValue("${UrlForm.map.monitorname}");
/* 4288 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4289 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4290 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4291 */       return true;
/*      */     }
/* 4293 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4294 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4299 */     PageContext pageContext = _jspx_page_context;
/* 4300 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4302 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4303 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4304 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4306 */     _jspx_th_c_005fset_005f1.setVar("availabilityid");
/*      */     
/* 4308 */     _jspx_th_c_005fset_005f1.setValue("400");
/* 4309 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4310 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4311 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 4312 */       return true;
/*      */     }
/* 4314 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 4315 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4320 */     PageContext pageContext = _jspx_page_context;
/* 4321 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4323 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4324 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 4325 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4327 */     _jspx_th_c_005fset_005f2.setVar("healthid");
/*      */     
/* 4329 */     _jspx_th_c_005fset_005f2.setValue("401");
/* 4330 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 4331 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 4332 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 4333 */       return true;
/*      */     }
/* 4335 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 4336 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4341 */     PageContext pageContext = _jspx_page_context;
/* 4342 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4344 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4345 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 4346 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4348 */     _jspx_th_c_005fset_005f3.setVar("responseid");
/*      */     
/* 4350 */     _jspx_th_c_005fset_005f3.setValue("402");
/* 4351 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 4352 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 4353 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f3);
/* 4354 */       return true;
/*      */     }
/* 4356 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f3);
/* 4357 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4362 */     PageContext pageContext = _jspx_page_context;
/* 4363 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4365 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4366 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 4367 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4369 */     _jspx_th_c_005fset_005f4.setVar("availabilityid");
/*      */     
/* 4371 */     _jspx_th_c_005fset_005f4.setValue("408");
/* 4372 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 4373 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 4374 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/* 4375 */       return true;
/*      */     }
/* 4377 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/* 4378 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4383 */     PageContext pageContext = _jspx_page_context;
/* 4384 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4386 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4387 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 4388 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4390 */     _jspx_th_c_005fset_005f5.setVar("healthid");
/*      */     
/* 4392 */     _jspx_th_c_005fset_005f5.setValue("409");
/* 4393 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 4394 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 4395 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 4396 */       return true;
/*      */     }
/* 4398 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 4399 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4404 */     PageContext pageContext = _jspx_page_context;
/* 4405 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4407 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4408 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 4409 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4411 */     _jspx_th_c_005fset_005f6.setVar("responseid");
/*      */     
/* 4413 */     _jspx_th_c_005fset_005f6.setValue("410");
/* 4414 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 4415 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 4416 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f6);
/* 4417 */       return true;
/*      */     }
/* 4419 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f6);
/* 4420 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4425 */     PageContext pageContext = _jspx_page_context;
/* 4426 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4428 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4429 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 4430 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4432 */     _jspx_th_c_005fif_005f4.setTest("${!empty param.parentid}");
/* 4433 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 4434 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 4436 */         out.write("\n      ");
/* 4437 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 4438 */           return true;
/* 4439 */         out.write("\n      ");
/* 4440 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 4441 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4445 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 4446 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4447 */       return true;
/*      */     }
/* 4449 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4450 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4455 */     PageContext pageContext = _jspx_page_context;
/* 4456 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4458 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4459 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 4460 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 4462 */     _jspx_th_c_005fset_005f7.setVar("parentids");
/* 4463 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 4464 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 4465 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 4466 */         out = _jspx_page_context.pushBody();
/* 4467 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 4468 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4471 */         out.write("\n      &parentname=");
/* 4472 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 4473 */           return true;
/* 4474 */         out.write("&parentid=");
/* 4475 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 4476 */           return true;
/* 4477 */         out.write("\n      ");
/* 4478 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 4479 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4482 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 4483 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4486 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 4487 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 4488 */       return true;
/*      */     }
/* 4490 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 4491 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4496 */     PageContext pageContext = _jspx_page_context;
/* 4497 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4499 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4500 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4501 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 4503 */     _jspx_th_c_005fout_005f0.setValue("${param.parentname}");
/* 4504 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4505 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4506 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4507 */       return true;
/*      */     }
/* 4509 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4510 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4515 */     PageContext pageContext = _jspx_page_context;
/* 4516 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4518 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4519 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4520 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 4522 */     _jspx_th_c_005fout_005f1.setValue("${param.parentid}");
/* 4523 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4524 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4525 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4526 */       return true;
/*      */     }
/* 4528 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4529 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4534 */     PageContext pageContext = _jspx_page_context;
/* 4535 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4537 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4538 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 4539 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4541 */     _jspx_th_c_005fif_005f5.setTest("${empty param.parentid}");
/* 4542 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 4543 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 4545 */         out.write("\n            ");
/* 4546 */         if (_jspx_meth_c_005fset_005f8(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 4547 */           return true;
/* 4548 */         out.write("\n      ");
/* 4549 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 4550 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4554 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 4555 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4556 */       return true;
/*      */     }
/* 4558 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4559 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f8(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4564 */     PageContext pageContext = _jspx_page_context;
/* 4565 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4567 */     SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4568 */     _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 4569 */     _jspx_th_c_005fset_005f8.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 4571 */     _jspx_th_c_005fset_005f8.setVar("parentids");
/*      */     
/* 4573 */     _jspx_th_c_005fset_005f8.setValue("");
/* 4574 */     int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 4575 */     if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 4576 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f8);
/* 4577 */       return true;
/*      */     }
/* 4579 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f8);
/* 4580 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4585 */     PageContext pageContext = _jspx_page_context;
/* 4586 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4588 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4589 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 4590 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4592 */     _jspx_th_c_005fif_005f6.setTest("${!empty param.haid}");
/* 4593 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 4594 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 4596 */         out.write(10);
/* 4597 */         out.write(9);
/* 4598 */         out.write(9);
/* 4599 */         if (_jspx_meth_c_005fset_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 4600 */           return true;
/* 4601 */         out.write(10);
/* 4602 */         out.write(9);
/* 4603 */         out.write(9);
/* 4604 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 4605 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4609 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 4610 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4611 */       return true;
/*      */     }
/* 4613 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4614 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4619 */     PageContext pageContext = _jspx_page_context;
/* 4620 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4622 */     SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4623 */     _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 4624 */     _jspx_th_c_005fset_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4626 */     _jspx_th_c_005fset_005f9.setVar("haid");
/* 4627 */     int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 4628 */     if (_jspx_eval_c_005fset_005f9 != 0) {
/* 4629 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 4630 */         out = _jspx_page_context.pushBody();
/* 4631 */         _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 4632 */         _jspx_th_c_005fset_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4635 */         out.write("\n\t\t&haid=");
/* 4636 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fset_005f9, _jspx_page_context))
/* 4637 */           return true;
/* 4638 */         out.write(10);
/* 4639 */         out.write(9);
/* 4640 */         out.write(9);
/* 4641 */         int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 4642 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4645 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 4646 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4649 */     if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 4650 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 4651 */       return true;
/*      */     }
/* 4653 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 4654 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fset_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4659 */     PageContext pageContext = _jspx_page_context;
/* 4660 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4662 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4663 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4664 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fset_005f9);
/*      */     
/* 4666 */     _jspx_th_c_005fout_005f2.setValue("${param.haid}");
/* 4667 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4668 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4669 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4670 */       return true;
/*      */     }
/* 4672 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4673 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4678 */     PageContext pageContext = _jspx_page_context;
/* 4679 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4681 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4682 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 4683 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4685 */     _jspx_th_c_005fif_005f7.setTest("${param.actionmethod=='editUrlMonitor'}");
/* 4686 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 4687 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 4689 */         out.write("\n    <a href=\"/showresource.do?resourceid=");
/* 4690 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 4691 */           return true;
/* 4692 */         out.write("&method=showResourceForResourceID&haid");
/* 4693 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 4694 */           return true;
/* 4695 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 4696 */           return true;
/* 4697 */         out.write("\"  class=\"new-left-links\">\n    ");
/* 4698 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 4699 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4703 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 4704 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4705 */       return true;
/*      */     }
/* 4707 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4708 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4713 */     PageContext pageContext = _jspx_page_context;
/* 4714 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4716 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4717 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4718 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4720 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 4721 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4722 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4723 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4724 */       return true;
/*      */     }
/* 4726 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4727 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4732 */     PageContext pageContext = _jspx_page_context;
/* 4733 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4735 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4736 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4737 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4739 */     _jspx_th_c_005fout_005f4.setValue("${parentids}");
/* 4740 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4741 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4742 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4743 */       return true;
/*      */     }
/* 4745 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4746 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4751 */     PageContext pageContext = _jspx_page_context;
/* 4752 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4754 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4755 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4756 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4758 */     _jspx_th_c_005fout_005f5.setValue("${haid}");
/* 4759 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4760 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4761 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4762 */       return true;
/*      */     }
/* 4764 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4765 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4770 */     PageContext pageContext = _jspx_page_context;
/* 4771 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4773 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4774 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 4775 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4777 */     _jspx_th_c_005fif_005f8.setTest("${param.actionmethod=='editUrlMonitor'}");
/* 4778 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 4779 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 4781 */         out.write("\n    </a>\n    ");
/* 4782 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 4783 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4787 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 4788 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4789 */       return true;
/*      */     }
/* 4791 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4792 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4797 */     PageContext pageContext = _jspx_page_context;
/* 4798 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4800 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4801 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4802 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4804 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 4805 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4806 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4807 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4808 */       return true;
/*      */     }
/* 4810 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4811 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4816 */     PageContext pageContext = _jspx_page_context;
/* 4817 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4819 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4820 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4821 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 4823 */     _jspx_th_c_005fout_005f7.setValue("${param.haid}");
/* 4824 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4825 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4826 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4827 */       return true;
/*      */     }
/* 4829 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4830 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4835 */     PageContext pageContext = _jspx_page_context;
/* 4836 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4838 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4839 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4840 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 4842 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 4843 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4844 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4845 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4846 */       return true;
/*      */     }
/* 4848 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4849 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4854 */     PageContext pageContext = _jspx_page_context;
/* 4855 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4857 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4858 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4859 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 4861 */     _jspx_th_c_005fout_005f9.setValue("${parentids}");
/* 4862 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4863 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4864 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4865 */       return true;
/*      */     }
/* 4867 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4868 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4873 */     PageContext pageContext = _jspx_page_context;
/* 4874 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4876 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4877 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4878 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 4880 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 4881 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4882 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4883 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4884 */       return true;
/*      */     }
/* 4886 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4887 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4892 */     PageContext pageContext = _jspx_page_context;
/* 4893 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4895 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4896 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4897 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 4899 */     _jspx_th_c_005fout_005f11.setValue("${parentids}");
/* 4900 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4901 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4902 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4903 */       return true;
/*      */     }
/* 4905 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4906 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4911 */     PageContext pageContext = _jspx_page_context;
/* 4912 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4914 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4915 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4916 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4918 */     _jspx_th_c_005fout_005f12.setValue("${param.haid}");
/* 4919 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4920 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4921 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4922 */       return true;
/*      */     }
/* 4924 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4925 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4930 */     PageContext pageContext = _jspx_page_context;
/* 4931 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4933 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4934 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4935 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4937 */     _jspx_th_c_005fout_005f13.setValue("${param.resourceid}");
/* 4938 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4939 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4940 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4941 */       return true;
/*      */     }
/* 4943 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4944 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4949 */     PageContext pageContext = _jspx_page_context;
/* 4950 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4952 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4953 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4954 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4956 */     _jspx_th_c_005fout_005f14.setValue("${parentids}");
/* 4957 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4958 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4959 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4960 */       return true;
/*      */     }
/* 4962 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4963 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4968 */     PageContext pageContext = _jspx_page_context;
/* 4969 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4971 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4972 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4973 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4975 */     _jspx_th_c_005fout_005f15.setValue("${param.resourceid}");
/* 4976 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4977 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4978 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4979 */       return true;
/*      */     }
/* 4981 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4982 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4987 */     PageContext pageContext = _jspx_page_context;
/* 4988 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4990 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4991 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4992 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4994 */     _jspx_th_c_005fout_005f16.setValue("${parentids}");
/* 4995 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4996 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4997 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4998 */       return true;
/*      */     }
/* 5000 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5001 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5006 */     PageContext pageContext = _jspx_page_context;
/* 5007 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5009 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5010 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 5011 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 5013 */     _jspx_th_c_005fif_005f12.setTest("${param.actionmethod!='editUrlMonitor'}");
/* 5014 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 5015 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 5017 */         out.write("\n    </a>\n    ");
/* 5018 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 5019 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5023 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 5024 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 5025 */       return true;
/*      */     }
/* 5027 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 5028 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5033 */     PageContext pageContext = _jspx_page_context;
/* 5034 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5036 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5037 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 5038 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5040 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid} ");
/* 5041 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 5042 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 5043 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5044 */       return true;
/*      */     }
/* 5046 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5047 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5052 */     PageContext pageContext = _jspx_page_context;
/* 5053 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5055 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5056 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 5057 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5059 */     _jspx_th_c_005fout_005f18.setValue("${param.resourceid}");
/* 5060 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 5061 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 5062 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5063 */       return true;
/*      */     }
/* 5065 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5066 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5071 */     PageContext pageContext = _jspx_page_context;
/* 5072 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5074 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5075 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 5076 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5078 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 5079 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 5080 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 5082 */         out.write("\n\t\t\t alertUser();\n\t\t \treturn;\n\t\t");
/* 5083 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 5084 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5088 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 5089 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 5090 */       return true;
/*      */     }
/* 5092 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 5093 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5098 */     PageContext pageContext = _jspx_page_context;
/* 5099 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5101 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5102 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 5103 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5105 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 5106 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 5107 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 5108 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5109 */       return true;
/*      */     }
/* 5111 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5112 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5117 */     PageContext pageContext = _jspx_page_context;
/* 5118 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5120 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5121 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 5122 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5124 */     _jspx_th_c_005fout_005f20.setValue("${param.resourceid}");
/* 5125 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 5126 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 5127 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5128 */       return true;
/*      */     }
/* 5130 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5131 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5136 */     PageContext pageContext = _jspx_page_context;
/* 5137 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5139 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5140 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 5141 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5143 */     _jspx_th_c_005fout_005f21.setValue("${healthid}");
/* 5144 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 5145 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 5146 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5147 */       return true;
/*      */     }
/* 5149 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5150 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5155 */     PageContext pageContext = _jspx_page_context;
/* 5156 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5158 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5159 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 5160 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5162 */     _jspx_th_c_005fout_005f22.setValue("${healthid}");
/* 5163 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 5164 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 5165 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5166 */       return true;
/*      */     }
/* 5168 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5169 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5174 */     PageContext pageContext = _jspx_page_context;
/* 5175 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5177 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5178 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 5179 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5181 */     _jspx_th_c_005fout_005f23.setValue("${availabilityid}");
/* 5182 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 5183 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 5184 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5185 */       return true;
/*      */     }
/* 5187 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5188 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5193 */     PageContext pageContext = _jspx_page_context;
/* 5194 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5196 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5197 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 5198 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5200 */     _jspx_th_c_005fout_005f24.setValue("${availabilityid}");
/* 5201 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 5202 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 5203 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5204 */       return true;
/*      */     }
/* 5206 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5207 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5212 */     PageContext pageContext = _jspx_page_context;
/* 5213 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5215 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5216 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 5217 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5219 */     _jspx_th_c_005fout_005f25.setValue("${ha.key}");
/* 5220 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 5221 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 5222 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5223 */       return true;
/*      */     }
/* 5225 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5226 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5231 */     PageContext pageContext = _jspx_page_context;
/* 5232 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5234 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5235 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 5236 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5238 */     _jspx_th_c_005fout_005f26.setValue("${ha.value}");
/* 5239 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 5240 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 5241 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5242 */       return true;
/*      */     }
/* 5244 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5245 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5250 */     PageContext pageContext = _jspx_page_context;
/* 5251 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5253 */     SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5254 */     _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 5255 */     _jspx_th_c_005fset_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5257 */     _jspx_th_c_005fset_005f10.setVar("monitorName");
/* 5258 */     int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 5259 */     if (_jspx_eval_c_005fset_005f10 != 0) {
/* 5260 */       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 5261 */         out = _jspx_page_context.pushBody();
/* 5262 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 5263 */         _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 5264 */         _jspx_th_c_005fset_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5267 */         if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fset_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5268 */           return true;
/* 5269 */         int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 5270 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5273 */       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 5274 */         out = _jspx_page_context.popBody();
/* 5275 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 5278 */     if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 5279 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 5280 */       return true;
/*      */     }
/* 5282 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 5283 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fset_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5288 */     PageContext pageContext = _jspx_page_context;
/* 5289 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5291 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5292 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 5293 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fset_005f10);
/*      */     
/* 5295 */     _jspx_th_c_005fout_005f27.setValue("${ha.value}");
/* 5296 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 5297 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 5298 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5299 */       return true;
/*      */     }
/* 5301 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5302 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5307 */     PageContext pageContext = _jspx_page_context;
/* 5308 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5310 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5311 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 5312 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_logic_005fpresent_005f6);
/*      */     
/* 5314 */     _jspx_th_c_005fout_005f28.setValue("${ha.key}");
/* 5315 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 5316 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 5317 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5318 */       return true;
/*      */     }
/* 5320 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5321 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5326 */     PageContext pageContext = _jspx_page_context;
/* 5327 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5329 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5330 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 5331 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 5333 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 5334 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 5335 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 5336 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5337 */       return true;
/*      */     }
/* 5339 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5340 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5345 */     PageContext pageContext = _jspx_page_context;
/* 5346 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5348 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5349 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 5350 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5352 */     _jspx_th_c_005fout_005f29.setValue("${ha.key}");
/* 5353 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 5354 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 5355 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5356 */       return true;
/*      */     }
/* 5358 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5359 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5364 */     PageContext pageContext = _jspx_page_context;
/* 5365 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5367 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5368 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 5369 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5371 */     _jspx_th_c_005fout_005f30.setValue("${ha.value}");
/* 5372 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 5373 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 5374 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5375 */       return true;
/*      */     }
/* 5377 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5378 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f11(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5383 */     PageContext pageContext = _jspx_page_context;
/* 5384 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5386 */     SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5387 */     _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 5388 */     _jspx_th_c_005fset_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5390 */     _jspx_th_c_005fset_005f11.setVar("monitorName");
/* 5391 */     int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 5392 */     if (_jspx_eval_c_005fset_005f11 != 0) {
/* 5393 */       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 5394 */         out = _jspx_page_context.pushBody();
/* 5395 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 5396 */         _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 5397 */         _jspx_th_c_005fset_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5400 */         if (_jspx_meth_c_005fout_005f31(_jspx_th_c_005fset_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5401 */           return true;
/* 5402 */         int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 5403 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5406 */       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 5407 */         out = _jspx_page_context.popBody();
/* 5408 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 5411 */     if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 5412 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 5413 */       return true;
/*      */     }
/* 5415 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 5416 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_c_005fset_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5421 */     PageContext pageContext = _jspx_page_context;
/* 5422 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5424 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5425 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 5426 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_c_005fset_005f11);
/*      */     
/* 5428 */     _jspx_th_c_005fout_005f31.setValue("${ha.value}");
/* 5429 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 5430 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 5431 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 5432 */       return true;
/*      */     }
/* 5434 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 5435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_logic_005fpresent_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5440 */     PageContext pageContext = _jspx_page_context;
/* 5441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5443 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5444 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 5445 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_logic_005fpresent_005f7);
/*      */     
/* 5447 */     _jspx_th_c_005fout_005f32.setValue("${ha.key}");
/* 5448 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 5449 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 5450 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 5451 */       return true;
/*      */     }
/* 5453 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 5454 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5459 */     PageContext pageContext = _jspx_page_context;
/* 5460 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5462 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5463 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 5464 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f7);
/*      */     
/* 5466 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 5467 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 5468 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 5469 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5470 */       return true;
/*      */     }
/* 5472 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5473 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f18(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5478 */     PageContext pageContext = _jspx_page_context;
/* 5479 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5481 */     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5482 */     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 5483 */     _jspx_th_c_005fif_005f18.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5485 */     _jspx_th_c_005fif_005f18.setTest("${empty associatedmgs}");
/* 5486 */     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 5487 */     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */       for (;;) {
/* 5489 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 5490 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f18, _jspx_page_context))
/* 5491 */           return true;
/* 5492 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 5493 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f18, _jspx_page_context))
/* 5494 */           return true;
/* 5495 */         out.write("</td>\n\t ");
/* 5496 */         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 5497 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5501 */     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 5502 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 5503 */       return true;
/*      */     }
/* 5505 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 5506 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5511 */     PageContext pageContext = _jspx_page_context;
/* 5512 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5514 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5515 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 5516 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f18);
/*      */     
/* 5518 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 5519 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 5520 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 5521 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5522 */       return true;
/*      */     }
/* 5524 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5525 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5530 */     PageContext pageContext = _jspx_page_context;
/* 5531 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5533 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5534 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 5535 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f18);
/*      */     
/* 5537 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.none.text");
/* 5538 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 5539 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 5540 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5541 */       return true;
/*      */     }
/* 5543 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5544 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5549 */     PageContext pageContext = _jspx_page_context;
/* 5550 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5552 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5553 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 5554 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5556 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 5557 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 5558 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 5559 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5560 */       return true;
/*      */     }
/* 5562 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5563 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5568 */     PageContext pageContext = _jspx_page_context;
/* 5569 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5571 */     com.adventnet.appmanager.tags.HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (com.adventnet.appmanager.tags.HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(com.adventnet.appmanager.tags.HiddenParam.class);
/* 5572 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 5573 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5575 */     _jspx_th_am_005fhiddenparam_005f0.setName("wiz");
/* 5576 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 5577 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 5578 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 5579 */       return true;
/*      */     }
/* 5581 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 5582 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5587 */     PageContext pageContext = _jspx_page_context;
/* 5588 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5590 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 5591 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 5592 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 5594 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 5595 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 5597 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 5598 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 5600 */           out.write(32);
/* 5601 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 5602 */             return true;
/* 5603 */           out.write(32);
/* 5604 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 5605 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5609 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 5610 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5613 */         int tmp183_182 = 0; int[] tmp183_180 = _jspx_push_body_count_c_005fcatch_005f0; int tmp185_184 = tmp183_180[tmp183_182];tmp183_180[tmp183_182] = (tmp185_184 - 1); if (tmp185_184 <= 0) break;
/* 5614 */         out = _jspx_page_context.popBody(); }
/* 5615 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 5617 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 5618 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 5620 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 5625 */     PageContext pageContext = _jspx_page_context;
/* 5626 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5628 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 5629 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 5630 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 5632 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 5634 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 5635 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 5636 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 5637 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 5638 */       return true;
/*      */     }
/* 5640 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 5641 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_c_005fif_005f20, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5646 */     PageContext pageContext = _jspx_page_context;
/* 5647 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5649 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5650 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 5651 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_c_005fif_005f20);
/*      */     
/* 5653 */     _jspx_th_c_005fout_005f33.setValue("${requestScope.urlmonitorname}");
/* 5654 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 5655 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 5656 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 5657 */       return true;
/*      */     }
/* 5659 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 5660 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f22(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5665 */     PageContext pageContext = _jspx_page_context;
/* 5666 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5668 */     IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest_005fnobody.get(IfTag.class);
/* 5669 */     _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 5670 */     _jspx_th_c_005fif_005f22.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5672 */     _jspx_th_c_005fif_005f22.setTest("${param.actionmethod=='editUrlMonitor'}");
/* 5673 */     int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 5674 */     if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 5675 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest_005fnobody.reuse(_jspx_th_c_005fif_005f22);
/* 5676 */       return true;
/*      */     }
/* 5678 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest_005fnobody.reuse(_jspx_th_c_005fif_005f22);
/* 5679 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5684 */     PageContext pageContext = _jspx_page_context;
/* 5685 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5687 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5688 */     _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 5689 */     _jspx_th_logic_005fnotEmpty_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5691 */     _jspx_th_logic_005fnotEmpty_005f0.setName("method");
/* 5692 */     int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 5693 */     if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */       for (;;) {
/* 5695 */         out.write(32);
/* 5696 */         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/* 5697 */           return true;
/* 5698 */         out.write(" \n  \t\t\t\t\t\t<input type=\"hidden\" name=\"actionmethod\" value=\"updateUrlMonitor\">\n    \t\t\t\t\t");
/* 5699 */         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/* 5700 */           return true;
/* 5701 */         out.write("\n  \t\t\t\t\t");
/* 5702 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 5703 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5707 */     if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 5708 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 5709 */       return true;
/*      */     }
/* 5711 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 5712 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5717 */     PageContext pageContext = _jspx_page_context;
/* 5718 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5720 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 5721 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 5722 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 5724 */     _jspx_th_html_005fhidden_005f0.setProperty("urlid");
/* 5725 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 5726 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 5727 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 5728 */       return true;
/*      */     }
/* 5730 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 5731 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5736 */     PageContext pageContext = _jspx_page_context;
/* 5737 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5739 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.get(HiddenTag.class);
/* 5740 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 5741 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 5743 */     _jspx_th_html_005fhidden_005f1.setStyleId("customHeadersId");
/*      */     
/* 5745 */     _jspx_th_html_005fhidden_005f1.setProperty("customHeaders");
/* 5746 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 5747 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 5748 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 5749 */       return true;
/*      */     }
/* 5751 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 5752 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fempty_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5757 */     PageContext pageContext = _jspx_page_context;
/* 5758 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5760 */     EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 5761 */     _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 5762 */     _jspx_th_logic_005fempty_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5764 */     _jspx_th_logic_005fempty_005f0.setName("method");
/* 5765 */     int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 5766 */     if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */       for (;;) {
/* 5768 */         out.write(" \n  \t\t\t\t\t\t<input type=\"hidden\" name=\"actionmethod\" value=\"createUrlMonitor\">\n  \t\t\t\t\t");
/* 5769 */         int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 5770 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5774 */     if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 5775 */       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 5776 */       return true;
/*      */     }
/* 5778 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 5779 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5784 */     PageContext pageContext = _jspx_page_context;
/* 5785 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5787 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 5788 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 5789 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5791 */     _jspx_th_html_005ftext_005f0.setProperty("monitorname");
/*      */     
/* 5793 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext xlarge");
/* 5794 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 5795 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 5796 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5797 */       return true;
/*      */     }
/* 5799 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5800 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5805 */     PageContext pageContext = _jspx_page_context;
/* 5806 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5808 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 5809 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 5810 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5812 */     _jspx_th_html_005ftextarea_005f0.setProperty("url");
/*      */     
/* 5814 */     _jspx_th_html_005ftextarea_005f0.setCols("50");
/*      */     
/* 5816 */     _jspx_th_html_005ftextarea_005f0.setRows("5");
/*      */     
/* 5818 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea xlarge");
/* 5819 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 5820 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 5821 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 5822 */       return true;
/*      */     }
/* 5824 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 5825 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5830 */     PageContext pageContext = _jspx_page_context;
/* 5831 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5833 */     CatchTag _jspx_th_c_005fcatch_005f1 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 5834 */     _jspx_th_c_005fcatch_005f1.setPageContext(_jspx_page_context);
/* 5835 */     _jspx_th_c_005fcatch_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5837 */     _jspx_th_c_005fcatch_005f1.setVar("invalidseqid");
/* 5838 */     int[] _jspx_push_body_count_c_005fcatch_005f1 = { 0 };
/*      */     try {
/* 5840 */       int _jspx_eval_c_005fcatch_005f1 = _jspx_th_c_005fcatch_005f1.doStartTag();
/* 5841 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f1 != 0) {
/*      */         for (;;) {
/* 5843 */           out.write(32);
/* 5844 */           if (_jspx_meth_fmt_005fparseNumber_005f1(_jspx_th_c_005fcatch_005f1, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f1))
/* 5845 */             return true;
/* 5846 */           out.write(32);
/* 5847 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f1.doAfterBody();
/* 5848 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5852 */       if (_jspx_th_c_005fcatch_005f1.doEndTag() == 5)
/* 5853 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5856 */         int tmp183_182 = 0; int[] tmp183_180 = _jspx_push_body_count_c_005fcatch_005f1; int tmp185_184 = tmp183_180[tmp183_182];tmp183_180[tmp183_182] = (tmp185_184 - 1); if (tmp185_184 <= 0) break;
/* 5857 */         out = _jspx_page_context.popBody(); }
/* 5858 */       _jspx_th_c_005fcatch_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 5860 */       _jspx_th_c_005fcatch_005f1.doFinally();
/* 5861 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f1);
/*      */     }
/* 5863 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f1(JspTag _jspx_th_c_005fcatch_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f1) throws Throwable
/*      */   {
/* 5868 */     PageContext pageContext = _jspx_page_context;
/* 5869 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5871 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f1 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 5872 */     _jspx_th_fmt_005fparseNumber_005f1.setPageContext(_jspx_page_context);
/* 5873 */     _jspx_th_fmt_005fparseNumber_005f1.setParent((Tag)_jspx_th_c_005fcatch_005f1);
/*      */     
/* 5875 */     _jspx_th_fmt_005fparseNumber_005f1.setVar("wnhaid");
/*      */     
/* 5877 */     _jspx_th_fmt_005fparseNumber_005f1.setValue("${UrlForm.userseqid}");
/* 5878 */     int _jspx_eval_fmt_005fparseNumber_005f1 = _jspx_th_fmt_005fparseNumber_005f1.doStartTag();
/* 5879 */     if (_jspx_th_fmt_005fparseNumber_005f1.doEndTag() == 5) {
/* 5880 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f1);
/* 5881 */       return true;
/*      */     }
/* 5883 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f1);
/* 5884 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5889 */     PageContext pageContext = _jspx_page_context;
/* 5890 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5892 */     CatchTag _jspx_th_c_005fcatch_005f2 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 5893 */     _jspx_th_c_005fcatch_005f2.setPageContext(_jspx_page_context);
/* 5894 */     _jspx_th_c_005fcatch_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5896 */     _jspx_th_c_005fcatch_005f2.setVar("invalidhaid");
/* 5897 */     int[] _jspx_push_body_count_c_005fcatch_005f2 = { 0 };
/*      */     try {
/* 5899 */       int _jspx_eval_c_005fcatch_005f2 = _jspx_th_c_005fcatch_005f2.doStartTag();
/* 5900 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f2 != 0) {
/*      */         for (;;) {
/* 5902 */           out.write(32);
/* 5903 */           if (_jspx_meth_fmt_005fparseNumber_005f2(_jspx_th_c_005fcatch_005f2, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f2))
/* 5904 */             return true;
/* 5905 */           out.write(32);
/* 5906 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f2.doAfterBody();
/* 5907 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5911 */       if (_jspx_th_c_005fcatch_005f2.doEndTag() == 5)
/* 5912 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5915 */         int tmp183_182 = 0; int[] tmp183_180 = _jspx_push_body_count_c_005fcatch_005f2; int tmp185_184 = tmp183_180[tmp183_182];tmp183_180[tmp183_182] = (tmp185_184 - 1); if (tmp185_184 <= 0) break;
/* 5916 */         out = _jspx_page_context.popBody(); }
/* 5917 */       _jspx_th_c_005fcatch_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/* 5919 */       _jspx_th_c_005fcatch_005f2.doFinally();
/* 5920 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f2);
/*      */     }
/* 5922 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f2(JspTag _jspx_th_c_005fcatch_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f2) throws Throwable
/*      */   {
/* 5927 */     PageContext pageContext = _jspx_page_context;
/* 5928 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5930 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f2 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 5931 */     _jspx_th_fmt_005fparseNumber_005f2.setPageContext(_jspx_page_context);
/* 5932 */     _jspx_th_fmt_005fparseNumber_005f2.setParent((Tag)_jspx_th_c_005fcatch_005f2);
/*      */     
/* 5934 */     _jspx_th_fmt_005fparseNumber_005f2.setVar("wnhaid");
/*      */     
/* 5936 */     _jspx_th_fmt_005fparseNumber_005f2.setValue("${param.haid}");
/* 5937 */     int _jspx_eval_fmt_005fparseNumber_005f2 = _jspx_th_fmt_005fparseNumber_005f2.doStartTag();
/* 5938 */     if (_jspx_th_fmt_005fparseNumber_005f2.doEndTag() == 5) {
/* 5939 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f2);
/* 5940 */       return true;
/*      */     }
/* 5942 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f2);
/* 5943 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f23(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5948 */     PageContext pageContext = _jspx_page_context;
/* 5949 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5951 */     IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5952 */     _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 5953 */     _jspx_th_c_005fif_005f23.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5955 */     _jspx_th_c_005fif_005f23.setTest("${(empty invalidseqid)}");
/* 5956 */     int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 5957 */     if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */       for (;;) {
/* 5959 */         out.write(32);
/* 5960 */         if (_jspx_meth_html_005fhidden_005f2(_jspx_th_c_005fif_005f23, _jspx_page_context))
/* 5961 */           return true;
/* 5962 */         out.write(32);
/* 5963 */         int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 5964 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5968 */     if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 5969 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 5970 */       return true;
/*      */     }
/* 5972 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 5973 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_c_005fif_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5978 */     PageContext pageContext = _jspx_page_context;
/* 5979 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5981 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 5982 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 5983 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_c_005fif_005f23);
/*      */     
/* 5985 */     _jspx_th_html_005fhidden_005f2.setProperty("userseqid");
/* 5986 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 5987 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 5988 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 5989 */       return true;
/*      */     }
/* 5991 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 5992 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5997 */     PageContext pageContext = _jspx_page_context;
/* 5998 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6000 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6001 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 6002 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6004 */     _jspx_th_html_005ftext_005f1.setProperty("pollInterval");
/*      */     
/* 6006 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext small");
/*      */     
/* 6008 */     _jspx_th_html_005ftext_005f1.setSize("5");
/* 6009 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 6010 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 6011 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 6012 */       return true;
/*      */     }
/* 6014 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 6015 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6020 */     PageContext pageContext = _jspx_page_context;
/* 6021 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6023 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6024 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 6025 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6027 */     _jspx_th_html_005ftext_005f2.setProperty("timeout");
/*      */     
/* 6029 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext small");
/*      */     
/* 6031 */     _jspx_th_html_005ftext_005f2.setSize("5");
/* 6032 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 6033 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 6034 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 6035 */       return true;
/*      */     }
/* 6037 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 6038 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6043 */     PageContext pageContext = _jspx_page_context;
/* 6044 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6046 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6047 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 6048 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6050 */     _jspx_th_html_005fradio_005f0.setProperty("method");
/*      */     
/* 6052 */     _jspx_th_html_005fradio_005f0.setValue("P");
/* 6053 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 6054 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 6055 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 6056 */       return true;
/*      */     }
/* 6058 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 6059 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6064 */     PageContext pageContext = _jspx_page_context;
/* 6065 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6067 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6068 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 6069 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6071 */     _jspx_th_html_005fradio_005f1.setProperty("method");
/*      */     
/* 6073 */     _jspx_th_html_005fradio_005f1.setValue("G");
/* 6074 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 6075 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 6076 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 6077 */       return true;
/*      */     }
/* 6079 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 6080 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6085 */     PageContext pageContext = _jspx_page_context;
/* 6086 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6088 */     TextareaTag _jspx_th_html_005ftextarea_005f1 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 6089 */     _jspx_th_html_005ftextarea_005f1.setPageContext(_jspx_page_context);
/* 6090 */     _jspx_th_html_005ftextarea_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6092 */     _jspx_th_html_005ftextarea_005f1.setProperty("requestparams");
/*      */     
/* 6094 */     _jspx_th_html_005ftextarea_005f1.setStyleClass("formtextarea xlarge");
/*      */     
/* 6096 */     _jspx_th_html_005ftextarea_005f1.setRows("5");
/*      */     
/* 6098 */     _jspx_th_html_005ftextarea_005f1.setCols("50");
/* 6099 */     int _jspx_eval_html_005ftextarea_005f1 = _jspx_th_html_005ftextarea_005f1.doStartTag();
/* 6100 */     if (_jspx_th_html_005ftextarea_005f1.doEndTag() == 5) {
/* 6101 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f1);
/* 6102 */       return true;
/*      */     }
/* 6104 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f1);
/* 6105 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6110 */     PageContext pageContext = _jspx_page_context;
/* 6111 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6113 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6114 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 6115 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6117 */     _jspx_th_html_005ftext_005f3.setProperty("userid");
/*      */     
/* 6119 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext xlarge");
/*      */     
/* 6121 */     _jspx_th_html_005ftext_005f3.setSize("20");
/*      */     
/* 6123 */     _jspx_th_html_005ftext_005f3.setStyleId("userid");
/* 6124 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 6125 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 6126 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 6127 */       return true;
/*      */     }
/* 6129 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 6130 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6135 */     PageContext pageContext = _jspx_page_context;
/* 6136 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6138 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6139 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 6140 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6142 */     _jspx_th_fmt_005fmessage_005f5.setKey("Modify password");
/* 6143 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 6144 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 6145 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6146 */       return true;
/*      */     }
/* 6148 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6149 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6154 */     PageContext pageContext = _jspx_page_context;
/* 6155 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6157 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.get(SelectTag.class);
/* 6158 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 6159 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6161 */     _jspx_th_html_005fselect_005f0.setProperty("httpcondition");
/* 6162 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 6163 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 6164 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 6165 */         out = _jspx_page_context.pushBody();
/* 6166 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 6167 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6170 */         out.write(32);
/* 6171 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 6172 */           return true;
/* 6173 */         out.write(32);
/* 6174 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 6175 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6178 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 6179 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6182 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 6183 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 6184 */       return true;
/*      */     }
/* 6186 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 6187 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6192 */     PageContext pageContext = _jspx_page_context;
/* 6193 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6195 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.get(OptionsCollectionTag.class);
/* 6196 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 6197 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 6199 */     _jspx_th_html_005foptionsCollection_005f0.setName("AMActionForm");
/*      */     
/* 6201 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("conditions");
/* 6202 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 6203 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 6204 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 6205 */       return true;
/*      */     }
/* 6207 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 6208 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6213 */     PageContext pageContext = _jspx_page_context;
/* 6214 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6216 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6217 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 6218 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6220 */     _jspx_th_html_005ftext_005f4.setProperty("httpvalue");
/*      */     
/* 6222 */     _jspx_th_html_005ftext_005f4.setSize("16");
/*      */     
/* 6224 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext medium");
/* 6225 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 6226 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 6227 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 6228 */       return true;
/*      */     }
/* 6230 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 6231 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6236 */     PageContext pageContext = _jspx_page_context;
/* 6237 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6239 */     MultiboxTag _jspx_th_html_005fmultibox_005f0 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6240 */     _jspx_th_html_005fmultibox_005f0.setPageContext(_jspx_page_context);
/* 6241 */     _jspx_th_html_005fmultibox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6243 */     _jspx_th_html_005fmultibox_005f0.setProperty("verifyerror");
/*      */     
/* 6245 */     _jspx_th_html_005fmultibox_005f0.setValue("true");
/* 6246 */     int _jspx_eval_html_005fmultibox_005f0 = _jspx_th_html_005fmultibox_005f0.doStartTag();
/* 6247 */     if (_jspx_th_html_005fmultibox_005f0.doEndTag() == 5) {
/* 6248 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 6249 */       return true;
/*      */     }
/* 6251 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 6252 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6257 */     PageContext pageContext = _jspx_page_context;
/* 6258 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6260 */     TextareaTag _jspx_th_html_005ftextarea_005f2 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 6261 */     _jspx_th_html_005ftextarea_005f2.setPageContext(_jspx_page_context);
/* 6262 */     _jspx_th_html_005ftextarea_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6264 */     _jspx_th_html_005ftextarea_005f2.setRows("5");
/*      */     
/* 6266 */     _jspx_th_html_005ftextarea_005f2.setCols("50");
/*      */     
/* 6268 */     _jspx_th_html_005ftextarea_005f2.setProperty("userAgent");
/*      */     
/* 6270 */     _jspx_th_html_005ftextarea_005f2.setStyleClass("formtextarea xlarge");
/* 6271 */     int _jspx_eval_html_005ftextarea_005f2 = _jspx_th_html_005ftextarea_005f2.doStartTag();
/* 6272 */     if (_jspx_th_html_005ftextarea_005f2.doEndTag() == 5) {
/* 6273 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f2);
/* 6274 */       return true;
/*      */     }
/* 6276 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f2);
/* 6277 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6282 */     PageContext pageContext = _jspx_page_context;
/* 6283 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6285 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6286 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 6287 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6289 */     _jspx_th_html_005ftext_005f5.setProperty("checkfor");
/*      */     
/* 6291 */     _jspx_th_html_005ftext_005f5.setStyleId("checkfor");
/*      */     
/* 6293 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext xlarge");
/* 6294 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 6295 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 6296 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 6297 */       return true;
/*      */     }
/* 6299 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 6300 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6305 */     PageContext pageContext = _jspx_page_context;
/* 6306 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6308 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6309 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 6310 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6312 */     _jspx_th_html_005ftext_005f6.setStyleId("errorcontent");
/*      */     
/* 6314 */     _jspx_th_html_005ftext_005f6.setProperty("errorcontent");
/*      */     
/* 6316 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext xlarge");
/* 6317 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 6318 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 6319 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 6320 */       return true;
/*      */     }
/* 6322 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 6323 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f26(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6328 */     PageContext pageContext = _jspx_page_context;
/* 6329 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6331 */     IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6332 */     _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/* 6333 */     _jspx_th_c_005fif_005f26.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6335 */     _jspx_th_c_005fif_005f26.setTest("${!empty param.haid && empty invalidhaid}");
/* 6336 */     int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/* 6337 */     if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */       for (;;) {
/* 6339 */         out.write("\n\t\t\t\t\t<input type=\"hidden\" name=\"haid\"  value='");
/* 6340 */         if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fif_005f26, _jspx_page_context))
/* 6341 */           return true;
/* 6342 */         out.write("'>\n\t\t\t\t");
/* 6343 */         int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/* 6344 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6348 */     if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/* 6349 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 6350 */       return true;
/*      */     }
/* 6352 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 6353 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6358 */     PageContext pageContext = _jspx_page_context;
/* 6359 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6361 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6362 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 6363 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 6365 */     _jspx_th_c_005fout_005f35.setValue("${param.haid}");
/* 6366 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 6367 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 6368 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 6369 */       return true;
/*      */     }
/* 6371 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 6372 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f27(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6377 */     PageContext pageContext = _jspx_page_context;
/* 6378 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6380 */     IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6381 */     _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/* 6382 */     _jspx_th_c_005fif_005f27.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6384 */     _jspx_th_c_005fif_005f27.setTest("${!empty param.name}");
/* 6385 */     int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/* 6386 */     if (_jspx_eval_c_005fif_005f27 != 0) {
/*      */       for (;;) {
/* 6388 */         out.write("\n        \t\t\t<input type=\"hidden\" name=\"name\"  value='");
/* 6389 */         if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fif_005f27, _jspx_page_context))
/* 6390 */           return true;
/* 6391 */         out.write("'>\n        \t\t");
/* 6392 */         int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/* 6393 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6397 */     if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/* 6398 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 6399 */       return true;
/*      */     }
/* 6401 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 6402 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fif_005f27, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6407 */     PageContext pageContext = _jspx_page_context;
/* 6408 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6410 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6411 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 6412 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fif_005f27);
/*      */     
/* 6414 */     _jspx_th_c_005fout_005f36.setValue("${param.name}");
/* 6415 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 6416 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 6417 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 6418 */       return true;
/*      */     }
/* 6420 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 6421 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6426 */     PageContext pageContext = _jspx_page_context;
/* 6427 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6429 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6430 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 6431 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6433 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 6435 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 6436 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 6437 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 6438 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 6439 */       return true;
/*      */     }
/* 6441 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 6442 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6447 */     PageContext pageContext = _jspx_page_context;
/* 6448 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6450 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6451 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 6452 */     _jspx_th_fmt_005fmessage_005f6.setParent(null);
/*      */     
/* 6454 */     _jspx_th_fmt_005fmessage_005f6.setKey("Modify");
/* 6455 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 6456 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 6457 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6458 */       return true;
/*      */     }
/* 6460 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6466 */     PageContext pageContext = _jspx_page_context;
/* 6467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6469 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6470 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 6471 */     _jspx_th_fmt_005fmessage_005f7.setParent(null);
/*      */     
/* 6473 */     _jspx_th_fmt_005fmessage_005f7.setKey("Cancel");
/* 6474 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 6475 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 6476 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6477 */       return true;
/*      */     }
/* 6479 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6480 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6485 */     PageContext pageContext = _jspx_page_context;
/* 6486 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6488 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 6489 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 6490 */     _jspx_th_c_005fforEach_005f2.setParent(null);
/*      */     
/* 6492 */     _jspx_th_c_005fforEach_005f2.setItems("${dynamicSites}");
/*      */     
/* 6494 */     _jspx_th_c_005fforEach_005f2.setVar("a");
/*      */     
/* 6496 */     _jspx_th_c_005fforEach_005f2.setVarStatus("rowCounter");
/* 6497 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */     try {
/* 6499 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 6500 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */         for (;;) {
/* 6502 */           out.write("\n    ");
/* 6503 */           if (_jspx_meth_c_005fforEach_005f3(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 6504 */             return true;
/* 6505 */           out.write("\n    if(formCustomerId == customerId)\n    {\n      document.UrlForm.haid.options[rowCount++] = new Option(siteName,siteId);\n    }\n  ");
/* 6506 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 6507 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6511 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/* 6512 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6515 */         int tmp195_194 = 0; int[] tmp195_192 = _jspx_push_body_count_c_005fforEach_005f2; int tmp197_196 = tmp195_192[tmp195_194];tmp195_192[tmp195_194] = (tmp197_196 - 1); if (tmp197_196 <= 0) break;
/* 6516 */         out = _jspx_page_context.popBody(); }
/* 6517 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/* 6519 */       _jspx_th_c_005fforEach_005f2.doFinally();
/* 6520 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */     }
/* 6522 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f3(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 6527 */     PageContext pageContext = _jspx_page_context;
/* 6528 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6530 */     ForEachTag _jspx_th_c_005fforEach_005f3 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 6531 */     _jspx_th_c_005fforEach_005f3.setPageContext(_jspx_page_context);
/* 6532 */     _jspx_th_c_005fforEach_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 6534 */     _jspx_th_c_005fforEach_005f3.setItems("${a}");
/*      */     
/* 6536 */     _jspx_th_c_005fforEach_005f3.setVar("b");
/*      */     
/* 6538 */     _jspx_th_c_005fforEach_005f3.setVarStatus("rowCounter1");
/* 6539 */     int[] _jspx_push_body_count_c_005fforEach_005f3 = { 0 };
/*      */     try {
/* 6541 */       int _jspx_eval_c_005fforEach_005f3 = _jspx_th_c_005fforEach_005f3.doStartTag();
/* 6542 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f3 != 0) {
/*      */         for (;;) {
/* 6544 */           out.write("\n      ");
/* 6545 */           boolean bool; if (_jspx_meth_c_005fif_005f28(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 6546 */             return true;
/* 6547 */           out.write("\n      ");
/* 6548 */           if (_jspx_meth_c_005fif_005f29(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 6549 */             return true;
/* 6550 */           out.write("\n      ");
/* 6551 */           if (_jspx_meth_c_005fif_005f30(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 6552 */             return true;
/* 6553 */           out.write("\n    ");
/* 6554 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f3.doAfterBody();
/* 6555 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6559 */       if (_jspx_th_c_005fforEach_005f3.doEndTag() == 5)
/* 6560 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6563 */         int tmp282_281 = 0; int[] tmp282_279 = _jspx_push_body_count_c_005fforEach_005f3; int tmp284_283 = tmp282_279[tmp282_281];tmp282_279[tmp282_281] = (tmp284_283 - 1); if (tmp284_283 <= 0) break;
/* 6564 */         out = _jspx_page_context.popBody(); }
/* 6565 */       _jspx_th_c_005fforEach_005f3.doCatch(_jspx_exception);
/*      */     } finally {
/* 6567 */       _jspx_th_c_005fforEach_005f3.doFinally();
/* 6568 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f3);
/*      */     }
/* 6570 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f28(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 6575 */     PageContext pageContext = _jspx_page_context;
/* 6576 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6578 */     IfTag _jspx_th_c_005fif_005f28 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6579 */     _jspx_th_c_005fif_005f28.setPageContext(_jspx_page_context);
/* 6580 */     _jspx_th_c_005fif_005f28.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 6582 */     _jspx_th_c_005fif_005f28.setTest("${rowCounter1.count == 1}");
/* 6583 */     int _jspx_eval_c_005fif_005f28 = _jspx_th_c_005fif_005f28.doStartTag();
/* 6584 */     if (_jspx_eval_c_005fif_005f28 != 0) {
/*      */       for (;;) {
/* 6586 */         out.write("\n        siteName = '");
/* 6587 */         if (_jspx_meth_c_005fout_005f37(_jspx_th_c_005fif_005f28, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 6588 */           return true;
/* 6589 */         out.write("';\n      ");
/* 6590 */         int evalDoAfterBody = _jspx_th_c_005fif_005f28.doAfterBody();
/* 6591 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6595 */     if (_jspx_th_c_005fif_005f28.doEndTag() == 5) {
/* 6596 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 6597 */       return true;
/*      */     }
/* 6599 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 6600 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_c_005fif_005f28, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 6605 */     PageContext pageContext = _jspx_page_context;
/* 6606 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6608 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6609 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 6610 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_c_005fif_005f28);
/*      */     
/* 6612 */     _jspx_th_c_005fout_005f37.setValue("${b}");
/* 6613 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 6614 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 6615 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 6616 */       return true;
/*      */     }
/* 6618 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 6619 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f29(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 6624 */     PageContext pageContext = _jspx_page_context;
/* 6625 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6627 */     IfTag _jspx_th_c_005fif_005f29 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6628 */     _jspx_th_c_005fif_005f29.setPageContext(_jspx_page_context);
/* 6629 */     _jspx_th_c_005fif_005f29.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 6631 */     _jspx_th_c_005fif_005f29.setTest("${rowCounter1.count == 2}");
/* 6632 */     int _jspx_eval_c_005fif_005f29 = _jspx_th_c_005fif_005f29.doStartTag();
/* 6633 */     if (_jspx_eval_c_005fif_005f29 != 0) {
/*      */       for (;;) {
/* 6635 */         out.write("\n        siteId = '");
/* 6636 */         if (_jspx_meth_c_005fout_005f38(_jspx_th_c_005fif_005f29, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 6637 */           return true;
/* 6638 */         out.write("';\n      ");
/* 6639 */         int evalDoAfterBody = _jspx_th_c_005fif_005f29.doAfterBody();
/* 6640 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6644 */     if (_jspx_th_c_005fif_005f29.doEndTag() == 5) {
/* 6645 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 6646 */       return true;
/*      */     }
/* 6648 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 6649 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_c_005fif_005f29, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 6654 */     PageContext pageContext = _jspx_page_context;
/* 6655 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6657 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6658 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 6659 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_c_005fif_005f29);
/*      */     
/* 6661 */     _jspx_th_c_005fout_005f38.setValue("${b}");
/* 6662 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 6663 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 6664 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 6665 */       return true;
/*      */     }
/* 6667 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 6668 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f30(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 6673 */     PageContext pageContext = _jspx_page_context;
/* 6674 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6676 */     IfTag _jspx_th_c_005fif_005f30 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6677 */     _jspx_th_c_005fif_005f30.setPageContext(_jspx_page_context);
/* 6678 */     _jspx_th_c_005fif_005f30.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 6680 */     _jspx_th_c_005fif_005f30.setTest("${rowCounter1.count == 3}");
/* 6681 */     int _jspx_eval_c_005fif_005f30 = _jspx_th_c_005fif_005f30.doStartTag();
/* 6682 */     if (_jspx_eval_c_005fif_005f30 != 0) {
/*      */       for (;;) {
/* 6684 */         out.write("\n        customerId = '");
/* 6685 */         if (_jspx_meth_c_005fout_005f39(_jspx_th_c_005fif_005f30, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 6686 */           return true;
/* 6687 */         out.write("';\n      ");
/* 6688 */         int evalDoAfterBody = _jspx_th_c_005fif_005f30.doAfterBody();
/* 6689 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6693 */     if (_jspx_th_c_005fif_005f30.doEndTag() == 5) {
/* 6694 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 6695 */       return true;
/*      */     }
/* 6697 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 6698 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_c_005fif_005f30, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 6703 */     PageContext pageContext = _jspx_page_context;
/* 6704 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6706 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6707 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 6708 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_c_005fif_005f30);
/*      */     
/* 6710 */     _jspx_th_c_005fout_005f39.setValue("${b}");
/* 6711 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 6712 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 6713 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 6714 */       return true;
/*      */     }
/* 6716 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 6717 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6722 */     PageContext pageContext = _jspx_page_context;
/* 6723 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6725 */     PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6726 */     _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 6727 */     _jspx_th_logic_005fpresent_005f8.setParent(null);
/*      */     
/* 6729 */     _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 6730 */     int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 6731 */     if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */       for (;;) {
/* 6733 */         out.write("\n\tif(url.indexOf(\"google.com\") != -1 || url.indexOf(\"google.co\") != -1)\n        {\n                alert(\"We have disabled adding URLMonitors for google.com. Kindly try configuring other websites.\");\n                document.UrlForm.url.value='http://';\n                return ;\n        }\n        if(url.indexOf(\"yahoo.com\") != -1 || url.indexOf(\"yahoo.co\") != -1)\n        {\n                alert(\"We have disabled adding URLMonitors for yahoo.com. Kindly try configuring other websites.\");\n                document.UrlForm.url.value='http://';\n                return ;\n        }\n\n\t");
/* 6734 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 6735 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6739 */     if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 6740 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 6741 */       return true;
/*      */     }
/* 6743 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 6744 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6749 */     PageContext pageContext = _jspx_page_context;
/* 6750 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6752 */     PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6753 */     _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 6754 */     _jspx_th_logic_005fpresent_005f9.setParent(null);
/*      */     
/* 6756 */     _jspx_th_logic_005fpresent_005f9.setRole("DEMO");
/* 6757 */     int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 6758 */     if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */       for (;;) {
/* 6760 */         out.write("\n\t if(parseInt(poll) <15)\n        {\n                alert(\"Poll interval of less than 15 minutes is not allowed in the online demo. This precaution is taken to prevent excessive requests being sent from our servers to 3rd party websites.\");\n                document.UrlForm.pollInterval.value=15;\n                document.UrlForm.pollInterval.focus();\n                return;\n        }\n\t");
/* 6761 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 6762 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6766 */     if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 6767 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 6768 */       return true;
/*      */     }
/* 6770 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 6771 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\urlconf_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */