/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.net.InetAddress;
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
/*      */ import org.apache.commons.lang3.StringEscapeUtils;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.FileTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.LinkTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ThresholdProfiles_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  668 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
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
/*  840 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/* 1381 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1426 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/*      */           catch (java.sql.SQLException e) {
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
/* 2178 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2184 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2185 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/* 2186 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005flink_0026_005fstyleClass_005fforward;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2211 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fhtml_005flink_0026_005fstyleClass_005fforward = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2233 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2239 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2241 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.release();
/* 2242 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.release();
/* 2243 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.release();
/* 2244 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2246 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.release();
/* 2247 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.release();
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2249 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2250 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2251 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2252 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.release();
/* 2253 */     this._005fjspx_005ftagPool_005fhtml_005flink_0026_005fstyleClass_005fforward.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2260 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2263 */     JspWriter out = null;
/* 2264 */     Object page = this;
/* 2265 */     JspWriter _jspx_out = null;
/* 2266 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2270 */       response.setContentType("text/html;charset=UTF-8");
/* 2271 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2273 */       _jspx_page_context = pageContext;
/* 2274 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2275 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2276 */       session = pageContext.getSession();
/* 2277 */       out = pageContext.getOut();
/* 2278 */       _jspx_out = out;
/*      */       
/* 2280 */       out.write("\n\n\n\n\n\n\n");
/* 2281 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2282 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/* 2284 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 2285 */       out.write(10);
/* 2286 */       out.write(10);
/* 2287 */       out.write(10);
/* 2288 */       out.write(10);
/* 2289 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2291 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2292 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2293 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2295 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2297 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2299 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2301 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2302 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2303 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2304 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2307 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2308 */         String available = null;
/* 2309 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2310 */         out.write(10);
/*      */         
/* 2312 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2313 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2314 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2316 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2318 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2320 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2322 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2323 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2324 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2325 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2328 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2329 */           String unavailable = null;
/* 2330 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2331 */           out.write(10);
/*      */           
/* 2333 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2334 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2335 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2337 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2339 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2341 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2343 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2344 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2345 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2346 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2349 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2350 */             String unmanaged = null;
/* 2351 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2352 */             out.write(10);
/*      */             
/* 2354 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2355 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2356 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2358 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2360 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2362 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2364 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2365 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2366 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2367 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2370 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2371 */               String scheduled = null;
/* 2372 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2373 */               out.write(10);
/*      */               
/* 2375 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2376 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2377 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2379 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2381 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2383 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2385 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2386 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2387 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2388 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2391 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2392 */                 String critical = null;
/* 2393 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2394 */                 out.write(10);
/*      */                 
/* 2396 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2397 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2398 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2400 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2402 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2404 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2406 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2407 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2408 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2409 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2412 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2413 */                   String clear = null;
/* 2414 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2415 */                   out.write(10);
/*      */                   
/* 2417 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2418 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2419 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2421 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2423 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2425 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2427 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2428 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2429 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2430 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2433 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2434 */                     String warning = null;
/* 2435 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2436 */                     out.write(10);
/* 2437 */                     out.write(10);
/*      */                     
/* 2439 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2440 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2442 */                     out.write(10);
/* 2443 */                     out.write(10);
/* 2444 */                     out.write(10);
/* 2445 */                     out.write("\n<html>\n<head>\n\n");
/*      */                     
/*      */ 
/* 2448 */                     request.setAttribute("HelpKey", "New Threshold Profile");
/* 2449 */                     String anomalyvalue = null;
/* 2450 */                     String editanomaly = null;
/* 2451 */                     if (request.getParameter("isanomaly") != null) {
/* 2452 */                       anomalyvalue = request.getParameter("isanomaly");
/*      */                     } else {
/* 2454 */                       anomalyvalue = (String)request.getAttribute("isanomaly");
/*      */                     }
/* 2456 */                     if (request.getAttribute("iseditanomaly") != null) {
/* 2457 */                       editanomaly = (String)request.getAttribute("iseditanomaly");
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2463 */                     out.write("\n<script>\n\n var title_text=$(\"<div/>\").html('");
/* 2464 */                     out.print(FormatUtil.getString("am.webclient.thresholdandactionscsv.header"));
/* 2465 */                     out.write("').text();\n jQuery(document).ready(function() //No I18N\n {\n\t ");
/* 2466 */                     if (com.adventnet.appmanager.util.Constants.isPrivilegedUser(request)) {
/* 2467 */                       out.write("\n\t \t$($('input:checkbox[name=\"unCatMonitors\"]')[0]).parent().hide();\n\t ");
/*      */                     }
/* 2469 */                     out.write("\n \t$('#ThresholdCSV').bind('click', function() //NO I18N \n \t{\n \t\t$(\"#thresholdModel\").dialog({ show: {effect: 'fade', duration: 800}, hide: {effect: 'fade', duration: 700},modal: true, zIndex:9000, //NO I18N\n\t\tminHeight: 300, minWidth: 800,title:title_text,closeText:\"");
/* 2470 */                     out.print(FormatUtil.getString("am.webclient.tooltip.close.text"));
/* 2471 */                     out.write("\",position:[\"top\",10], //NO I18N\n\t\tbuttons: \n\t\t\t\t{\n \t\t\t  \t\t'");
/* 2472 */                     out.print(FormatUtil.getString("am.webclient.createcsv.button.text"));
/* 2473 */                     out.write("': \n\t \t\t\t  \tfunction()  //NO I18N\n\t \t\t\t  \t{\n\t\t\t\t\t\tif(submitThresholdForm())\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\t$( this ).dialog( \"close\" ); //NO I18N\n\t\t\t\t\t\t}\n\t \t\t\t\t},\n\t \t\t\t\t'");
/* 2474 */                     out.print(FormatUtil.getString("webclient.fault.alarm.customview.button.cancel"));
/* 2475 */                     out.write("':\n\t \t\t\t\t function() \n\t \t\t\t  \t{\n\t \t\t\t  \t\t\t$( this ).dialog( \"close\" ); //NO I18N\n\t \t\t\t  \t}\n\t\t\t\t}\n \t\t\t}\n \t)}),\n\t$('.selectAllCheckCSS').change(function()  //NO I18N\n \t{\n \t    if($(this).is(':checked'))  //NO I18N\n \t    {\n \t    \tjQuery(\"input:checkbox[name=selectedGroups]\").attr('checked', 'checked'); //NO I18N\n \t    \tjQuery(\"input:checkbox[name=unCatMonitors]\").attr('checked', 'checked'); //NO I18N\n\t\tjQuery(\"input:checkbox[name=unAssociatedMonitors]\").attr('checked', 'checked'); //NO I18N\n \t    } \n \t    else \n \t    {\n \t    \tjQuery(\"input:checkbox[name=selectedGroups]\").removeAttr('checked'); //NO I18N\n \t    \tjQuery(\"input:checkbox[name=unCatMonitors]\").removeAttr('checked'); //NO I18N\n\t\tjQuery(\"input:checkbox[name=unAssociatedMonitors]\").removeAttr('checked'); //NO I18N\n \t    }\n \t}),\n \tjQuery(\"input:checkbox[name=selectedGroups],[name=unCatMonitors],[name=unAssociatedMonitors]\").change(function() //NO I18N\n \t{\n \t\tif(!$(this).is(':checked')) //NO I18N\n \t\t{\n \t\t\tjQuery(\"input:checkbox[name=allSelected]\").removeAttr('checked'); //NO I18N\n");
/* 2476 */                     out.write(" \t\t}\n \t\tif(jQuery(\"input:checkbox[name=selectedGroups]\").not(\":checked\").length ==0 && jQuery(\"input:checkbox[name=unCatMonitors]\").is(':checked') && jQuery(\"input:checkbox[name=unAssociatedMonitors]\").is(':checked') ) //NO I18N\n \t\t{\n \t\t\tjQuery(\"input:checkbox[name=allSelected]\").attr('checked', 'checked'); //NO I18N\n \t\t}\n \t})\n \t\n });\n\n function submitThresholdForm()\n {\n    \n\tif(jQuery(\"input:checkbox[name=selectedGroups]:checked\").length ==0 && !jQuery(\"input:checkbox[name=unCatMonitors]\").is(':checked') && !jQuery(\"input:checkbox[name=unAssociatedMonitors]\").is(':checked'))\n\t{\n\t\talert('");
/* 2477 */                     out.print(FormatUtil.getString("am.webclient.thresholdcsv.validation.alert"));
/* 2478 */                     out.write("'); //NO I18N\n\t\treturn false;\n\t}\n\t$.ajax(\n\t{\n\t\turl: '/createThresholdCSV.do?method=createThresholdConfCSV', //NO I18N\n        \ttype: 'POST', //NO I18N\n        \tdata: $(\"#ThresholdAsCSVForm\").serialize(), //NO I18N\n        \tbeforeSend: function() \n        \t{\n\t\t\t\t$('#ThresholdCSV').prepend('<img id=\"loadingImgId\" src=\"/images/LoadingTC.gif\"/>'); //NO I18N\n        \t},\n        \tsuccess: function(data, textStatus, xhr) \n        \t{\n        \t\t$(\"#loadingImgId\").remove(); //NO I18N\n\t\t\t    $(\"#CSVContainerDiv\").show(); //NO I18N\n\t\t\t    $(\"#containerSpan\").attr(\"href\", \"/Reports/\"+data); //NO I18N\n\t\t\t    $(\"#closeIcon\").click(function() //NO I18N\n\t\t\t    {\n\t\t\t    \t$(\"#CSVContainerDiv\").hide(); //NO I18N\n\t\t\t    });\t\t\t\t\n\t\t\t},\n        \terror: function(xhr, textStatus, errorThrown) \n        \t{\n        \t\t$(\"#CSVContainerDiv\").show().dialog(\"open\").delay(60000).fadeOut(\"slow\"); //NO I18N\n\t\t\t\t$('#containerSpan').html(textStatus); //NO I18N\n  \t  \t \t}\n\t\t\t\n\t});\n\treturn true;\n }\n\nfunction myOnLoad()\n{\n        ");
/* 2479 */                     if ("yes".equals(editanomaly)) {
/* 2480 */                       out.write("\n\n            window.opener.editAnomalyInAjax();\n            window.close();\n        ");
/*      */                     }
/* 2482 */                     out.write("\n\n\tSORTTABLENAME = 'thresholdTable';\n\tvar numberOfColumnsToBeSorted =2;\n\tvar ignoreCheckBox =true;\n\tsortables_init(numberOfColumnsToBeSorted,ignoreCheckBox);\n        //the below code for adding anomaly profile tab....\n\n\n        ");
/* 2483 */                     if ("true".equals(anomalyvalue))
/*      */                     {
/* 2485 */                       out.write("\n\n         showHide('anomaly');//no i18n\n        ");
/*      */                     } else {
/* 2487 */                       out.write("\n\n              showHide('threshold');//no i18n\n            ");
/*      */                     }
/* 2489 */                     out.write("\n}\n\nfunction fnSelectAll(e)\n{\n\tToggleAll(e,document.form2,\"checkbox\")\n}\nfunction fnanomalySelectAll(e)\n{\n\tToggleAll(e,document.form2,\"anomalycheckbox\")\n}\nfunction fnanomalyexpressionSelectAll(e)\n{\n\tToggleAll(e,document.form2,\"anomalyexpressioncheckbox\")\n}\n/**\n             * Comment\n             */\n\nfunction deleteAnomalySelections() {\n                ");
/* 2490 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2492 */                     out.write("\n\n        if(!checkforOneSelected(document.form2,'anomalycheckbox'))//no i18n\n\t{\n\t\talert('");
/* 2493 */                     out.print(FormatUtil.getString("am.webclient.anomalyprofile.jaalertdelete.text"));
/* 2494 */                     out.write("'); //no i18n\n\t\treturn;\n\t}\n\n\tif(confirm('");
/* 2495 */                     out.print(FormatUtil.getString("am.webclient.anomalyprofile.jaalertdeleteconfirm.text"));
/* 2496 */                     out.write("'))\n\t{\n\tdocument.form2.action=\"/adminAction.do?method=deleteAnomalyProfiles\";//no i18n\n\tdocument.form2.method=\"Post\";//no i18n\n\tdocument.form2.submit();//no i18n\n\n\t}\n\n  }\n  function deleteAnomalyExpressionSelections() {\n                ");
/* 2497 */                     if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                       return;
/* 2499 */                     out.write("\n\n        if(!checkforOneSelected(document.form2,'anomalyexpressioncheckbox'))//no i18n\n\t{\n\t\talert('");
/* 2500 */                     out.print(FormatUtil.getString("am.webclient.anomalyprofile.jaalertdelete.text"));
/* 2501 */                     out.write("'); //no i18n\n\t\treturn;\n\t}\n\n\tif(confirm('");
/* 2502 */                     out.print(FormatUtil.getString("am.webclient.anomalyprofile.jaalertdeleteconfirm.text"));
/* 2503 */                     out.write("'))\n\t{\n\tdocument.form2.action=\"/adminAction.do?method=deleteAnomalyProfiles\";//no i18n\n\tdocument.form2.method=\"Post\";//no i18n\n\tdocument.form2.submit();//no i18n\n\n\t}\n\n  }\n\n  //the below for tabs and anomaly inclusion..\n\n    function showHide(tab)\n{\n\n\tif(tab==\"threshold\")\n\t{\n\n\n\t//document.getElementById(\"acolumn\").style.display='none'\t;\n\tdocument.getElementById(\"customreplink-left\").className = \"tbSelected_Left\";\n\tdocument.getElementById(\"customreplink\").className = \"tbSelected_Middle\";\n\tdocument.getElementById(\"customreplink-right\").className = \"tbSelected_Right\";\n\n\tdocument.getElementById(\"downsumreplink-left\").className = \"tbUnSelected_Left\";\n\tdocument.getElementById(\"downsumreplink\").className = \"tbUnSelected_Middle\";\n\tdocument.getElementById(\"downsumreplink-right\").className = \"tbUnSelected_Right\";\n\n\tjavascript:showDiv('thresholdviewdiv');\n\tjavascript:showDiv('config');\n\n\tjavascript:hideDiv('anomalydiv');\n\n\t}\n\n\telse if(tab==\"anomaly\")\n\t{\n\n\t//document.getElementById(\"tcolumn\").style.display='none'\t;\n\tdocument.getElementById(\"downsumreplink-left\").className = \"tbSelected_Left\";\n");
/* 2504 */                     out.write("\tdocument.getElementById(\"downsumreplink\").className = \"tbSelected_Middle\";\n\tdocument.getElementById(\"downsumreplink-right\").className = \"tbSelected_Right\";\n\n\tdocument.getElementById(\"customreplink-left\").className = \"tbUnSelected_Left\";\n\tdocument.getElementById(\"customreplink\").className = \"tbUnSelected_Middle\";\n\tdocument.getElementById(\"customreplink-right\").className = \"tbUnSelected_Right\";\n\tjavascript:showDiv('anomalydiv');\n\tjavascript:hideDiv('thresholdviewdiv');\n\tjavascript:hideDiv('config');\n\n\t}\n\n}\n\n\n\nfunction deleteSelections()\n{\n\t");
/* 2505 */                     if (_jspx_meth_logic_005fpresent_005f2(_jspx_page_context))
/*      */                       return;
/* 2507 */                     out.write(10);
/* 2508 */                     out.write(9);
/*      */                     
/* 2510 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2511 */                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2512 */                     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                     
/* 2514 */                     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2515 */                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2516 */                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                       for (;;) {
/* 2518 */                         out.write("\n\tvar sel = false;\n\tfor(i=0;i<document.form2.elements.length;i++)\n\t{\n\t\tif(document.form2.elements[i].type==\"checkbox\")\n\t               {\n\t                        var name = document.form2.elements[i].name;\n\t                        if(name==\"checkbox\")\n\t                        {\n\t                        \tvar value = document.form2.elements[i].value;\n\t                        \tsel=document.form2.elements[i].checked;\n\t                        \tif(sel)\n\t                        \t{\n\t                        \t\tbreak;\n\t                        \t}\n\t                        }\n\t                 }\n               }\n               if(!sel)\n               {\n                  alert(\"");
/* 2519 */                         out.print(FormatUtil.getString("am.webclient.threshold.alertselect"));
/* 2520 */                         out.write("\");\n               }\nelse if(confirm('");
/* 2521 */                         out.print(FormatUtil.getString("am.webclient.threshold.alertdelete"));
/* 2522 */                         out.write("'))\n{\n\tdocument.form2.action=\"/adminAction.do?method=deleteThresholds\";\n\tdocument.form2.method=\"Post\"\n\tdocument.form2.submit();\n}\n");
/* 2523 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2524 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2528 */                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2529 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */                     }
/*      */                     else {
/* 2532 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2533 */                       out.write("\n}\n\n//PDAM\n\nfunction importThresholds()\n{\n\tvar val = document.AMActionForm.theFile.value;\n\tif(trimAll(val)==\"\")\n\t{\n\t\twindow.alert('");
/* 2534 */                       out.print(FormatUtil.getString("am.webclient.fileupload.alert1.text"));
/* 2535 */                       out.write("');\n\t\treturn false;\n\t}\n\tif(val.match(/\\.xml$/gi)!=\".xml\")\n\t{\n\t\talert('");
/* 2536 */                       out.print(FormatUtil.getString("am.webclient.admin.pdam.import.notxml"));
/* 2537 */                       out.write("');\n\t\treturn false;\n\t}\n\t\t\n\n\n  \tdocument.getElementById(\"method\").value=\"importMapping\";//No I18N\n \tdocument.AMActionForm.submit();\n}\n\nfunction exportThresholds()\n{\n\tdocument.getElementById(\"method\").value=\"exportMapping\";//No I18N\n\tdocument.AMActionForm.submit();\n}\n\n\nfunction showImportThresholdsDiv()\n{\n\tdocument.getElementById(\"importThresholdsDiv\").style.display=\"block\";\n}\n\nfunction hideImportThresholdsDiv()\n{\n\tdocument.getElementById(\"importThresholdsDiv\").style.display=\"none\";\n}\n\n//PDAM\n</script>\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n<body>\n");
/* 2538 */                       out.write("\n\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" align=\"center\" id=\"CSVContainerDiv\" style=\"display: none;\">\n\t\t<tr>\n\t\t\t<td  class=\"msg-status-tp-left-corn\"></td>\n\t\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t\t<td  class=\"msg-status-tp-right-corn\"></td>\n\t\t</tr>\n\t    <tr>\n\t\t\t<td class=\"msg-status-left-bg\" width=\"1%\">&nbsp;</td>\n\t\t\t<td class=\"msg-table-width\" width=\"99%\" nowrap=\"nowrap\">\n\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"99%\" style=\"width: 100%;\" border=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"4%\" class=\"msg-table-width-bg\" nowrap=\"nowrap\"><img src=\"../images/icon_message_success.gif\" alt=\"icon\" height=\"25\" width=\"25\"></td>\n\t\t            <td width=\"96%\" class=\"msg-table-width\" style=\"padding-left: 10px;\" nowrap=\"nowrap\">");
/* 2539 */                       out.print(FormatUtil.getString("am.webclient.thresholdandactionscsv.success.text"));
/* 2540 */                       out.write(".&nbsp;<a class=\"staticlinks\" id=\"containerSpan\" href=\"\" target=\"_blank\">");
/* 2541 */                       out.print(FormatUtil.getString("am.webclient.admin.pdam.export.threshold.download"));
/* 2542 */                       out.write("</a></td>\n\t\t            <td id=\"closeIcon\" align=\"right\" style=\"float: right;\" nowrap=\"nowrap\">\n\t\t\t\t\t\t\t<img src=\"/images/delete.png\" width=\"12\" height=\"12\" hspace=\"0\" border=\"0\" style=\"cursor: pointer;float: right;\"/>\n\t\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t\t<td class=\"msg-status-right-bg\">&nbsp;</td>\n\t     </tr>\n\t\n\t<tr>\n\t<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t<td class=\"msg-status-btm-right-corn\" >&nbsp;</td>\n\t</tr>\n</table>\n\n");
/*      */                       
/* 2544 */                       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.get(FormTag.class);
/* 2545 */                       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2546 */                       _jspx_th_html_005fform_005f0.setParent(null);
/*      */                       
/* 2548 */                       _jspx_th_html_005fform_005f0.setAction("/PreDefinedAttributeMapperAction.do");
/*      */                       
/* 2550 */                       _jspx_th_html_005fform_005f0.setEnctype("multipart/form-data");
/*      */                       
/* 2552 */                       _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2553 */                       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2554 */                       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                         for (;;) {
/* 2556 */                           out.write("\n<input type=\"hidden\" id=\"method\" name=\"method\" value=\"importMapping\"/>\t\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"align=\"center\">\n\n  <tr>\n\t  <td align=\"right\">\n\t\t <a class=\"staticlinks\" href=\"#\" id=\"ThresholdCSV\">");
/* 2557 */                           out.print(FormatUtil.getString("am.webclient.thresholdandactionscsv.header"));
/* 2558 */                           out.write("</a>\n\t  </td>\n  </tr>\n  </table>\n  <div id=\"importThresholdsDiv\" style=\"display:none\">\n\n\t  <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=\"center\">\n\t\t  \n<tr>\n<td class=\"tableheading\" width=\"72%\" height=\"31\">");
/* 2559 */                           out.print(FormatUtil.getString("am.webclient.admin.pdam.import.threshold.profiles"));
/* 2560 */                           out.write("</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\" align=\"center\">\t \n\t<tr/>\t\n<tr>   \t\t  \n  <td width=\"15%\" height=\"35\" class=\"bodytext\" align=\"center\">");
/* 2561 */                           out.print(FormatUtil.getString("am.webclient.fileupload.filetoupload.text"));
/* 2562 */                           out.write("    :</td>\n  <td width=\"85%\" height=\"35\" class=\"bodytext\"> ");
/* 2563 */                           if (_jspx_meth_html_005ffile_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 2565 */                           out.write("(Ex:Thresholds.xml)</td> ");
/* 2566 */                           out.write("\n\t  </tr>\n\t  <tr/>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\" align=\"center\">\n\t<tr>\n    <td height=\"40\" width=\"40%\" align=\"right\"  class=\"tablebottom\">\n\t    <input id=\"button1\" name=\"button1\" type=\"button\" class=\"buttons btn_import\" value='");
/* 2567 */                           out.print(FormatUtil.getString("am.webclient.admin.pdam.import.threshold.import"));
/* 2568 */                           out.write("' onClick=\"javascript:importThresholds();\">\n\t    <input name=\"button2\" type=\"button\" class=\"buttons btn_link\" value='");
/* 2569 */                           out.print(FormatUtil.getString("am.webclient.newaction.buttonLabel.cancel"));
/* 2570 */                           out.write("' onClick=\"javascript:hideImportThresholdsDiv();\">\n</td>\n    <td height=\"40\" width=\"60%\"  align=\"center\"  class=\"tablebottom\">&nbsp; </td>\n  </tr>\n</table>\n<br/>\n\t\t  \n  </div>\n\t\n");
/* 2571 */                           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2572 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2576 */                       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2577 */                         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */                       }
/*      */                       else {
/* 2580 */                         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2581 */                         out.write(9);
/* 2582 */                         out.write(10);
/* 2583 */                         out.write("\n\n\n   <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"itrest-hide\">\n  <tr class=\"tabBtmLine\">\n  <td><table id=\"InnerTab\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"innertab_btm_space\">\n               <tbody>\n                 <tr>\n                   <td width=\"17\">\n\n                   </td>\n\n                   <td id=\"tcolumn\"><table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                       <tbody>\n\n                         <tr>\n                           <td class=\"tbSelected_Left\" id=\"customreplink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                           <td class=\"tbSelected_Middle\" id=\"customreplink\">\n                        <a href=\"javascript:showHide('threshold')\">&nbsp;<span class=\"tabLink\">");
/* 2584 */                         out.print(FormatUtil.getString("am.webclient.admin.thresholddetails.link"));
/* 2585 */                         out.write("</span></a></a>\n                           </td>\n                           <td class=\"tbselected_Right\" id=\"customreplink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                         </tr>\n                       </tbody>\n\n                     </table>\n                   </td>\n\n                   <td id=\"acolumn\"><table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                       <tbody>\n                         <tr>\n                           <td class=\"tbUnselected_Left\" id=\"downsumreplink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                           <td class=\"tbUnselected_Middle\" id=\"downsumreplink\">\n                           <a href=\"javascript:showHide('anomaly')\">&nbsp;<span class=\"tabLink\">");
/* 2586 */                         out.print(FormatUtil.getString("am.webclient.anomalyprofile.heading.text"));
/* 2587 */                         out.write("</span></a>\n                           </td>\n                           <td class=\"tbUnselected_Right\" id=\"downsumreplink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n                         </tr>\n                       </tbody>\n                     </table>\n                   </td>\n\n  </tr>\n  </table></td>\n\n  </tr>\n</table>\n\t \n\t<!-- Do not delete this form, since this one will be loaded in jQuery.Dialog while page ready. -->\t \n\t<div id=\"myThresholdForm\" style=\"display: none;\">\n\t\t<form name=\"ThresholdAsCSVForm\">\n\t\t</form>\n\t</div>\n   \n\t<div id=\"thresholdModel\" style=\"display: none;\">\n\t\t<form id=\"ThresholdAsCSVForm\">\n\t\t\t<table class=\"bodytext\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\" width=\"99%\">\n\t\t\t\t<tr> \n\t\t\t\t\t<td class=\"bodystyle\">\n\t\t\t\t\t\t<input name=\"allSelected\" type=\"checkbox\" class=\"selectAllCheckCSS\">");
/* 2588 */                         out.print(FormatUtil.getString("am.mypage.category.selectall.text"));
/* 2589 */                         out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"bodystyle\">\n\t\t\t\t\t\t<input name=\"unCatMonitors\" type=\"checkbox\" class=\"unCatMonitorsCheckCSS\">");
/* 2590 */                         out.print(FormatUtil.getString("am.webclient.thresholdcsv.unassociated.mg.monitors"));
/* 2591 */                         out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"bodystyle\">\n\t\t\t\t\t\t<input name=\"unAssociatedMonitors\" type=\"checkbox\" class=\"unAssociatedMonitorsCheckCSS\">");
/* 2592 */                         out.print(FormatUtil.getString("am.webclient.thresholdcsv.unassociated.actionthreshold.monitors"));
/* 2593 */                         out.write("\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\n\t\t\t<tr>\n\t\t\t     \t");
/*      */                         
/* 2595 */                         IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 2596 */                         _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2597 */                         _jspx_th_logic_005fiterate_005f0.setParent(null);
/*      */                         
/* 2599 */                         _jspx_th_logic_005fiterate_005f0.setName("mgIdVsDisp");
/*      */                         
/* 2601 */                         _jspx_th_logic_005fiterate_005f0.setId("mgs");
/*      */                         
/* 2603 */                         _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/* 2604 */                         int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2605 */                         if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2606 */                           Object mgs = null;
/* 2607 */                           Integer j = null;
/* 2608 */                           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2609 */                             out = _jspx_page_context.pushBody();
/* 2610 */                             _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2611 */                             _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                           }
/* 2613 */                           mgs = _jspx_page_context.findAttribute("mgs");
/* 2614 */                           j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                           for (;;) {
/* 2616 */                             out.write("\n\t\t\t\t\t<td nowrap=\"nowrap\" class=\"bodystyle\">\n\t\t\t\t\t\t<input type=\"checkbox\" name=\"selectedGroups\" value='");
/* 2617 */                             if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                               return;
/* 2619 */                             out.write(39);
/* 2620 */                             out.write(62);
/* 2621 */                             if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                               return;
/* 2623 */                             out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/* 2624 */                             if (_jspx_meth_c_005fif_005f0(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                               return;
/* 2626 */                             out.write("\n\t\t\t\t");
/* 2627 */                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2628 */                             mgs = _jspx_page_context.findAttribute("mgs");
/* 2629 */                             j = (Integer)_jspx_page_context.findAttribute("j");
/* 2630 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2633 */                           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2634 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2637 */                         if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2638 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*      */                         }
/*      */                         else {
/* 2641 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2642 */                           out.write("\n\t\t\t\t</tr>\n\t\t\t\n\t\t\t<tr>\n\t\t\n<!-- \t\t\t\t<td class=\"bodystyle\">\n\t\t\t\t\t <input name=\"showAll\" type=\"checkbox\" >");
/* 2643 */                           out.print(FormatUtil.getString("am.mypage.category.unconfig.text"));
/* 2644 */                           out.write("\n\t\t\t\t </td> -->\n\t\t\t</tr>\n\t\t\t</table>\n   \t\t</form>\n   \t</div>\n   \n   <div id=\"thresholdviewdiv\"  style=\"display:none\">\n\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtborder\">\n    <tr>\n\n      <td width=\"72%\" height=\"31\" class=\"tableheading itrest-hide\" >&nbsp;");
/* 2645 */                           out.print(FormatUtil.getString("am.webclient.threshold.thresholdprofile"));
/* 2646 */                           out.write("</td>\n          </tr>\n        </table>\n\n<form name=\"form2\" style=\"display:inline\">\n<input type=\"hidden\" name=\"deletealerts\" value=\"true\"/>\n");
/*      */                           
/* 2648 */                           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2649 */                           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2650 */                           _jspx_th_c_005fif_005f1.setParent(null);
/*      */                           
/* 2652 */                           _jspx_th_c_005fif_005f1.setTest("${!empty param.returnpath}");
/* 2653 */                           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2654 */                           if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                             for (;;) {
/* 2656 */                               out.write("\n<input name=\"returnpath\" type=\"hidden\" value=\"");
/* 2657 */                               out.print(request.getParameter("returnpath"));
/* 2658 */                               out.write("\">\n\n");
/* 2659 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2660 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2664 */                           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2665 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */                           }
/*      */                           else {
/* 2668 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2669 */                             out.write(10);
/*      */                             
/* 2671 */                             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2672 */                             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2673 */                             _jspx_th_c_005fif_005f2.setParent(null);
/*      */                             
/* 2675 */                             _jspx_th_c_005fif_005f2.setTest("${!empty param.haid}");
/* 2676 */                             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2677 */                             if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                               for (;;) {
/* 2679 */                                 out.write("\n<input name=\"haid\" type=\"hidden\" value=\"");
/* 2680 */                                 out.print(request.getParameter("haid"));
/* 2681 */                                 out.write("\">\n\n");
/* 2682 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2683 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2687 */                             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2688 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */                             }
/*      */                             else {
/* 2691 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2692 */                               out.write("\n\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n    <tr>\n\n          <td height=\"28\">\n");
/*      */                               
/* 2694 */                               PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.get(PresentTag.class);
/* 2695 */                               _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2696 */                               _jspx_th_logic_005fpresent_005f3.setParent(null);
/*      */                               
/* 2698 */                               _jspx_th_logic_005fpresent_005f3.setName("ThresholdConfig");
/* 2699 */                               int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2700 */                               if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                 for (;;) {
/* 2702 */                                   out.write("\n\n        <table align=\"center\" width=\"100%\" id=\"thresholdTable\" cellpadding=\"1\" cellspacing=\"0\"  border=\"0\" >\n          <tr>\n\t        <td width=\"3%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">\n<input type=\"checkbox\" name=\"headercheckbox\"  onClick=\"javascript:fnSelectAll(this)\">\t</td>\n\t        <td width=\"16%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">");
/* 2703 */                                   out.print(FormatUtil.getString("am.webclient.threshold.name"));
/* 2704 */                                   out.write("</td>\n\t        <td width=\"18%\"height=\"28\" valign=\"center\"  class=\"columnheadingrightborder\">");
/* 2705 */                                   out.print(FormatUtil.getString("am.webclient.threshold.description"));
/* 2706 */                                   out.write("</td>\n\t        <td height=\"28\" valign=\"center\" class=\"columnheadingrightborder\" >\n              \t<table bordercolor=\"#000000\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" height=\"30\" width=\"100%\" align=\"center\" >\n                <tr>\n\t\t          <td colspan=\"3\" align=\"center\" height=\"15\" class=\"bodytextbold\">");
/* 2707 */                                   out.print(FormatUtil.getString("am.webclient.threshold.criteria"));
/* 2708 */                                   out.write("</td>\n                </tr><tr>\n\t\t          <td class=\"sptrborder\" width=\"33%\">");
/* 2709 */                                   out.print(FormatUtil.getString("am.webclient.threshold.criticalalert"));
/* 2710 */                                   out.write("</td>\n\t\t          <td class=\"sptrborder\" width=\"38%\">");
/* 2711 */                                   out.print(FormatUtil.getString("am.webclient.threshold.warningalert"));
/* 2712 */                                   out.write("</td>\n\t\t          <td class=\"sptborder\" width=\"29%\">");
/* 2713 */                                   out.print(FormatUtil.getString("am.webclient.threshold.clearalert"));
/* 2714 */                                   out.write("</td>\n                </tr>\n\t\t</table>\n\t</td>\n\t<td width=\"6%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">");
/* 2715 */                                   out.print(FormatUtil.getString("Used by"));
/* 2716 */                                   out.write("</td>\n\n\t        <td width=\"4%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">");
/*      */                                   
/* 2718 */                                   PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2719 */                                   _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 2720 */                                   _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                                   
/* 2722 */                                   _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 2723 */                                   int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 2724 */                                   if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                     for (;;) {
/* 2726 */                                       out.print(FormatUtil.getString("am.webclient.threshold.editview"));
/* 2727 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 2728 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2732 */                                   if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 2733 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                   }
/*      */                                   
/* 2736 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 2737 */                                   out.write("</td>\n\t</tr>\n\n\t");
/*      */                                   
/* 2739 */                                   IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.get(IterateTag.class);
/* 2740 */                                   _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 2741 */                                   _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                                   
/* 2743 */                                   _jspx_th_logic_005fiterate_005f1.setName("ThresholdConfig");
/*      */                                   
/* 2745 */                                   _jspx_th_logic_005fiterate_005f1.setScope("request");
/*      */                                   
/* 2747 */                                   _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                                   
/* 2749 */                                   _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/* 2750 */                                   int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 2751 */                                   if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 2752 */                                     Object row = null;
/* 2753 */                                     Integer j = null;
/* 2754 */                                     if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2755 */                                       out = _jspx_page_context.pushBody();
/* 2756 */                                       _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 2757 */                                       _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                     }
/* 2759 */                                     row = _jspx_page_context.findAttribute("row");
/* 2760 */                                     j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                     for (;;) {
/* 2762 */                                       out.write(10);
/* 2763 */                                       out.write(9);
/*      */                                       
/* 2765 */                                       String bgclass = "whitegrayborder";
/* 2766 */                                       if (j.intValue() % 2 != 0)
/*      */                                       {
/* 2768 */                                         bgclass = "yellowgrayborder";
/*      */                                       }
/*      */                                       
/*      */ 
/*      */ 
/* 2773 */                                       String met = "showThresholdAction";
/* 2774 */                                       boolean isPatternMatcher = ((ArrayList)row).get(12).equals("3");
/* 2775 */                                       if (isPatternMatcher)
/*      */                                       {
/* 2777 */                                         met = "showPatternAction";
/*      */                                       }
/*      */                                       
/*      */ 
/* 2781 */                                       out.write("\n        \t<tr onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\" class=\"alarmheader\">\n\t\t<td height=\"22\" class=\"");
/* 2782 */                                       out.print(bgclass);
/* 2783 */                                       out.write("\">\n\t\t");
/*      */                                       
/* 2785 */                                       IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2786 */                                       _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2787 */                                       _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                       
/* 2789 */                                       _jspx_th_c_005fif_005f3.setTest("${!row[13]}");
/* 2790 */                                       int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2791 */                                       if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                         for (;;) {
/* 2793 */                                           out.write("\n\t\t<input type=\"checkbox\" name=\"checkbox\" value=\"");
/* 2794 */                                           out.print(((ArrayList)row).get(0));
/* 2795 */                                           out.write("\">\n\t\t");
/* 2796 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2797 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2801 */                                       if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2802 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                       }
/*      */                                       
/* 2805 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2806 */                                       out.write("\n\t\t</td>\n                <td height=\"22\" class=\"");
/* 2807 */                                       out.print(bgclass);
/* 2808 */                                       out.write("\" title=\"");
/* 2809 */                                       out.print(FormatUtil.getString((String)((ArrayList)row).get(1)));
/* 2810 */                                       out.write("\"><a class=\"alarm-links\" href=\"/adminAction.do?method=");
/* 2811 */                                       out.print(met);
/* 2812 */                                       out.write("&type=");
/* 2813 */                                       out.print(((ArrayList)row).get(12));
/* 2814 */                                       out.write("&thresholdID=");
/* 2815 */                                       out.print(((ArrayList)row).get(0));
/* 2816 */                                       out.write("&haid=");
/* 2817 */                                       out.print(request.getParameter("haid"));
/* 2818 */                                       out.write(34);
/* 2819 */                                       out.write(62);
/* 2820 */                                       out.print(getTrimmedText(FormatUtil.getString((String)((ArrayList)row).get(1)), 25));
/* 2821 */                                       out.write("</a></td>\n\n        <td height=\"22\" class=\"");
/* 2822 */                                       out.print(bgclass);
/* 2823 */                                       out.write("\" title=\"");
/* 2824 */                                       out.print(FormatUtil.getString((String)((ArrayList)row).get(2)));
/* 2825 */                                       out.write(34);
/* 2826 */                                       out.write(62);
/* 2827 */                                       out.write(32);
/* 2828 */                                       out.print(getTrimmedText(FormatUtil.getString((String)((ArrayList)row).get(2)), 28));
/* 2829 */                                       out.write("&nbsp;\n\t</td>\n        <td height=\"22\" class=\"");
/* 2830 */                                       out.print(bgclass);
/* 2831 */                                       out.write("\">\n        <table width=\"100%\">\n                <tr>\n        \t      <td class=\"bodytext\" width=\"33%\" title=\"");
/* 2832 */                                       out.print(StringEscapeUtils.escapeHtml4(FormatUtil.getString((String)((ArrayList)row).get(4))));
/* 2833 */                                       out.write(34);
/* 2834 */                                       out.write(62);
/* 2835 */                                       out.print(FormatUtil.getString("am.webclient.value.threshold.text"));
/* 2836 */                                       out.write("&nbsp;");
/* 2837 */                                       out.print(FormatUtil.getString((String)((ArrayList)row).get(3)));
/* 2838 */                                       out.write("\n                    ");
/* 2839 */                                       out.print(getTrimmedText(StringEscapeUtils.escapeHtml4((String)((ArrayList)row).get(4)), 30));
/* 2840 */                                       out.write("</td>\n        \t      <td class=\"bodytext\" width=\"38%\" title=\"");
/* 2841 */                                       out.print(StringEscapeUtils.escapeHtml4(FormatUtil.getString((String)((ArrayList)row).get(7))));
/* 2842 */                                       out.write(34);
/* 2843 */                                       out.write(62);
/* 2844 */                                       out.print(FormatUtil.getString("am.webclient.value.threshold.text"));
/* 2845 */                                       out.write("&nbsp;");
/* 2846 */                                       out.print(FormatUtil.getString((String)((ArrayList)row).get(6)));
/* 2847 */                                       out.write("\n                    ");
/* 2848 */                                       out.print(getTrimmedText(StringEscapeUtils.escapeHtml4((String)((ArrayList)row).get(7)), 30));
/* 2849 */                                       out.write("</td>\n        \t      <td class=\"bodytext\" width=\"30%\" title=\"");
/* 2850 */                                       out.print(StringEscapeUtils.escapeHtml4(FormatUtil.getString((String)((ArrayList)row).get(10))));
/* 2851 */                                       out.write(34);
/* 2852 */                                       out.write(62);
/* 2853 */                                       out.print(FormatUtil.getString("am.webclient.value.threshold.text"));
/* 2854 */                                       out.write("&nbsp;");
/* 2855 */                                       out.print(FormatUtil.getString((String)((ArrayList)row).get(9)));
/* 2856 */                                       out.write("\n                    ");
/* 2857 */                                       out.print(getTrimmedText(StringEscapeUtils.escapeHtml4((String)((ArrayList)row).get(10)), 30));
/* 2858 */                                       out.write("</td></tr>\n        </table>\n</td>\n<td height=\"22\" class=\"");
/* 2859 */                                       out.print(bgclass);
/* 2860 */                                       out.write("\" width=\"2%\" align=\"left\" title='");
/* 2861 */                                       out.print(FormatUtil.getString("am.webclient.threshold.thresholdprofile.conutofmonitor.link"));
/* 2862 */                                       out.write("'>\n\t\t");
/* 2863 */                                       if (_jspx_meth_c_005fchoose_005f0(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                                         return;
/* 2865 */                                       out.write("\n\t\t</td>\n\t\t");
/*      */                                       
/* 2867 */                                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2868 */                                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2869 */                                       _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                       
/* 2871 */                                       _jspx_th_c_005fif_005f4.setTest("${!row[13]}");
/* 2872 */                                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2873 */                                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                         for (;;) {
/* 2875 */                                           out.write("\n\t        <td height=\"22\" class=\"");
/* 2876 */                                           out.print(bgclass);
/* 2877 */                                           out.write("\" style=\"padding-left:10px\">\n\t\n\t        <a href=\"/adminAction.do?method=");
/* 2878 */                                           out.print(met);
/* 2879 */                                           out.write("&type=");
/* 2880 */                                           out.print(((ArrayList)row).get(12));
/* 2881 */                                           out.write("&thresholdID=");
/* 2882 */                                           out.print(((ArrayList)row).get(0));
/* 2883 */                                           out.write("&haid=");
/* 2884 */                                           out.print(request.getParameter("haid"));
/* 2885 */                                           out.write("\">\n\t       ");
/* 2886 */                                           if (_jspx_meth_logic_005fpresent_005f5(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                             return;
/* 2888 */                                           out.write("</a>\n\t        </td>\n        ");
/* 2889 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2890 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2894 */                                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2895 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                       }
/*      */                                       
/* 2898 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2899 */                                       out.write("\n\n        </tr>\n\n");
/* 2900 */                                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 2901 */                                       row = _jspx_page_context.findAttribute("row");
/* 2902 */                                       j = (Integer)_jspx_page_context.findAttribute("j");
/* 2903 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 2906 */                                     if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2907 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 2910 */                                   if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 2911 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                   }
/*      */                                   
/* 2914 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 2915 */                                   out.write("\n</table>\n\n\t<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"tablebottom\" >\n\t<tr>\n             <td height=\"26\" align=\"left\">\n             \t");
/*      */                                   
/* 2917 */                                   PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2918 */                                   _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 2919 */                                   _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                                   
/* 2921 */                                   _jspx_th_logic_005fpresent_005f6.setRole("ADMIN,ENTERPRISEADMIN");
/* 2922 */                                   int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 2923 */                                   if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                     for (;;) {
/* 2925 */                                       out.write("\n\t             \t<A HREF=\"javascript:deleteSelections();\" class=\"staticlinks\">");
/* 2926 */                                       out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 2927 */                                       out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n\t\t\t\t\t<a href=\"/showTile.do?TileName=.ThresholdConf&haid=null\" class=\"staticlinks\">");
/* 2928 */                                       out.print(FormatUtil.getString("am.webclient.threshold.addnew"));
/* 2929 */                                       out.write("</a>&nbsp;&nbsp;\n\t\t\t\t");
/* 2930 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 2931 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2935 */                                   if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 2936 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                                   }
/*      */                                   
/* 2939 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 2940 */                                   out.write("\n\t\t\t </td>\n          </tr>\n\t</table>\n\n");
/* 2941 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2942 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2946 */                               if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2947 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f3);
/*      */                               }
/*      */                               else {
/* 2950 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2951 */                                 out.write(10);
/* 2952 */                                 out.write(10);
/*      */                                 
/* 2954 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.get(NotPresentTag.class);
/* 2955 */                                 _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2956 */                                 _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */                                 
/* 2958 */                                 _jspx_th_logic_005fnotPresent_005f1.setName("ThresholdConfig");
/* 2959 */                                 int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2960 */                                 if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                   for (;;) {
/* 2962 */                                     out.write("\n\n        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n          <tr>\n            <td class=\"bodytext emptyTableMsg\">");
/* 2963 */                                     out.print(FormatUtil.getString("am.webclient.threshold.nothresholdprofiles"));
/* 2964 */                                     out.write("\n            ");
/*      */                                     
/* 2966 */                                     PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2967 */                                     _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 2968 */                                     _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                                     
/* 2970 */                                     _jspx_th_logic_005fpresent_005f7.setRole("ADMIN,ENTERPRISEADMIN");
/* 2971 */                                     int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 2972 */                                     if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                       for (;;) {
/* 2974 */                                         out.write(32);
/*      */                                         
/* 2976 */                                         LinkTag _jspx_th_html_005flink_005f0 = (LinkTag)this._005fjspx_005ftagPool_005fhtml_005flink_0026_005fstyleClass_005fforward.get(LinkTag.class);
/* 2977 */                                         _jspx_th_html_005flink_005f0.setPageContext(_jspx_page_context);
/* 2978 */                                         _jspx_th_html_005flink_005f0.setParent(_jspx_th_logic_005fpresent_005f7);
/*      */                                         
/* 2980 */                                         _jspx_th_html_005flink_005f0.setForward("ThresholdForm");
/*      */                                         
/* 2982 */                                         _jspx_th_html_005flink_005f0.setStyleClass("resourcename");
/* 2983 */                                         int _jspx_eval_html_005flink_005f0 = _jspx_th_html_005flink_005f0.doStartTag();
/* 2984 */                                         if (_jspx_eval_html_005flink_005f0 != 0) {
/* 2985 */                                           if (_jspx_eval_html_005flink_005f0 != 1) {
/* 2986 */                                             out = _jspx_page_context.pushBody();
/* 2987 */                                             _jspx_th_html_005flink_005f0.setBodyContent((BodyContent)out);
/* 2988 */                                             _jspx_th_html_005flink_005f0.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 2991 */                                             out.write(32);
/* 2992 */                                             out.print(FormatUtil.getString("am.webclient.threshold.newthreshold"));
/* 2993 */                                             int evalDoAfterBody = _jspx_th_html_005flink_005f0.doAfterBody();
/* 2994 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 2997 */                                           if (_jspx_eval_html_005flink_005f0 != 1) {
/* 2998 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3001 */                                         if (_jspx_th_html_005flink_005f0.doEndTag() == 5) {
/* 3002 */                                           this._005fjspx_005ftagPool_005fhtml_005flink_0026_005fstyleClass_005fforward.reuse(_jspx_th_html_005flink_005f0); return;
/*      */                                         }
/*      */                                         
/* 3005 */                                         this._005fjspx_005ftagPool_005fhtml_005flink_0026_005fstyleClass_005fforward.reuse(_jspx_th_html_005flink_005f0);
/* 3006 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3007 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3011 */                                     if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3012 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                                     }
/*      */                                     
/* 3015 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3016 */                                     out.write("\n            </td>\n          </tr></table>\n");
/* 3017 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3018 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3022 */                                 if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3023 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*      */                                 }
/*      */                                 else {
/* 3026 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3027 */                                   out.write("\n\n          </td>\n          </tr>\n        </table>\n        </div>\n\n<!--  /******************************* Display Global Default values for polls************************************/  -->\n\n<div id=\"config\" style=\"display:none\">\n\n\t<br>\n\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"   class=\"cont-main-table\">\n\t<tr>\n\t<td class=\"conf-mon-tp-lft-corn\"></td>\n\t<td class=\"conf-mon-tp-mid-tile\">\n\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t<tr>\n\t<td valign=\"middle\" align=\"left\" class=\"conf-mon-content-bg\"><span class=\"conf-mon-txt\">");
/* 3028 */                                   out.print(FormatUtil.getString("am.webclient.global.retry.header.text"));
/* 3029 */                                   out.write("</span></td>\n\t<td valign=\"middle\" align=\"left\" class=\"conf-mon-tp-angle\">&nbsp;</td>\n\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t</tr>\n\t</table></td>\n\t<td class=\"conf-mon-tp-rig-corn\">&nbsp;</td>\n\n\t</tr>\n\n\t<tr>\n\n\t<td  class=\"conf-mon-content\">&nbsp;</td>\n\t<td valign=\"top\">\n\t<!--//include your Helpcard template table here..-->\n\n\n\n\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"3\" border=\"0\" align=\"center\">\n\n\t<tr>\n\t<td class=\"conf-mon-content\">\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\n\t<tr>\n\n\t<td>\n\n\n\t<table width=\"100%\"  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t<tr>\n\t<td class=\"conf-mon-inner-top-left \"><img src=\"/images/spacer.gif\" width=\"6\" height=\"6\"/></td>\n\t<td class=\"conf-mon-inner-top-tile\"><img src=\"/images/spacer.gif\"  height=\"6\"/></td>\n\t<td class=\"conf-mon-inner-top-right\"><img src=\"/images/spacer.gif\" width=\"6\" height=\"6\"/></td>\n\t</tr>\n\n\n\n\n\t<tr>\n\t<td colspan=\"3\" class=\"conf-mon-inner-content-bg\">\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" width=\"100%\" class=\"conf-tbl-hdr\">\n");
/* 3030 */                                   out.write("\n\n\t<tr>\n\t<td style=\"background-color:#fff;\">\n\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t \t <tr><td></td></tr>\n\t    \t<tr>\n\t\t\t\t");
/*      */                                   
/* 3032 */                                   HashMap<String, String> h1 = DBUtil.getConsecutivePollsCountValue();
/*      */                                   
/* 3034 */                                   out.write("\n\n\t\t\t\t<td  style=\"padding-left:10px;padding-top:10px;padding-bottom:10px\" class=\"bodytext\">");
/* 3035 */                                   out.print(FormatUtil.getString("am.webclient.common.check.text"));
/* 3036 */                                   out.write("&nbsp;");
/* 3037 */                                   out.print((String)h1.get("meam.min_critical.polls.count"));
/* 3038 */                                   out.write("&nbsp;");
/* 3039 */                                   out.print(FormatUtil.getString("am.webclient.threshold.out.text"));
/* 3040 */                                   out.write("&nbsp;");
/* 3041 */                                   out.print(FormatUtil.getString("of"));
/* 3042 */                                   out.write("&nbsp;");
/* 3043 */                                   out.print((String)h1.get("meam.critical.polls.count"));
/* 3044 */                                   out.write("&nbsp;");
/* 3045 */                                   out.print(FormatUtil.getString("am.webclient.global.cricticalpollcount.text"));
/* 3046 */                                   out.write("</td>\n\t\t\t\t<td height=\"28\"></td>\n\t   \t   </tr>\n\t\t\t\t\t\t\t\t<tr>\n\t\t   \t\t<td  style=\"padding-left:10px;padding-top:10px;padding-bottom:10px\" class=\"bodytext\">");
/* 3047 */                                   out.print(FormatUtil.getString("am.webclient.common.check.text"));
/* 3048 */                                   out.write("&nbsp;");
/* 3049 */                                   out.print((String)h1.get("meam.min_warning.polls.count"));
/* 3050 */                                   out.write("&nbsp;");
/* 3051 */                                   out.print(FormatUtil.getString("am.webclient.threshold.out.text"));
/* 3052 */                                   out.write("&nbsp;");
/* 3053 */                                   out.print(FormatUtil.getString("of"));
/* 3054 */                                   out.write("&nbsp;");
/* 3055 */                                   out.print((String)h1.get("meam.warning.polls.count"));
/* 3056 */                                   out.write("&nbsp;");
/* 3057 */                                   out.print(FormatUtil.getString("am.webclient.global.warningpollcount.text"));
/* 3058 */                                   out.write("</td>\n\t\t\t\t\t\t\t\t\t<td >&nbsp;</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t<td  style=\"padding-left:10px;padding-top:10px;padding-bottom:10px\"  class=\"bodytext\">");
/* 3059 */                                   out.print(FormatUtil.getString("am.webclient.common.check.text"));
/* 3060 */                                   out.write("&nbsp;");
/* 3061 */                                   out.print((String)h1.get("meam.min_clear.polls.count"));
/* 3062 */                                   out.write("&nbsp;");
/* 3063 */                                   out.print(FormatUtil.getString("am.webclient.threshold.out.text"));
/* 3064 */                                   out.write("&nbsp;");
/* 3065 */                                   out.print(FormatUtil.getString("of"));
/* 3066 */                                   out.write("&nbsp;");
/* 3067 */                                   out.print((String)h1.get("meam.clear.polls.count"));
/* 3068 */                                   out.write("&nbsp;");
/* 3069 */                                   out.print(FormatUtil.getString("am.webclient.global.clearpollcount.text"));
/* 3070 */                                   out.write("</td>\n\t\t\t\t\t\t\t\t\t<td >&nbsp;</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t");
/* 3071 */                                   boolean isdelegAdmin = DBUtil.isDelegatedAdmin(request.getRemoteUser());
/* 3072 */                                   if (!isdelegAdmin)
/*      */                                   {
/* 3074 */                                     out.write("\n\t\t\t");
/*      */                                     
/* 3076 */                                     PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3077 */                                     _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 3078 */                                     _jspx_th_logic_005fpresent_005f8.setParent(null);
/*      */                                     
/* 3080 */                                     _jspx_th_logic_005fpresent_005f8.setRole("ADMIN");
/* 3081 */                                     int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 3082 */                                     if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                                       for (;;) {
/* 3084 */                                         out.write("\n\t    \t<tr>\n\t         <td>\n\t\t\t\t<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"tablebottom whitegrayborder\" >\n\t\t\t\t<tr>\n\t         \t   <td  style=\"padding-left:5px;padding-top:5px;padding-bottom:10px\"  >\n\n\t\t\t\t\t<a class=\"staticlinks\" href=\"/adminAction.do?method=showGlobalSettingsConfiguration&amp;typetoshow=Actionalert&fromwhere=viewthreshold\">\n\t\t\t\t\t\t");
/* 3085 */                                         out.print(FormatUtil.getString("am.webclient.global.configurepoll.text"));
/* 3086 */                                         out.write("\n\t\t\t\t\t</a>\n\t\t\t\t</td>\n\n\t\t\t   </tr>\n\t\t\t\t</table>\n\t\t\t </td>\n\t\t\t <td  class=\"tablebottom whitegrayborder\" >&nbsp;</td>\n\t\t</tr>\n\t\t");
/* 3087 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 3088 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3092 */                                     if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 3093 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                                     }
/*      */                                     
/* 3096 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 3097 */                                     out.write(10);
/* 3098 */                                     out.write(9);
/* 3099 */                                     out.write(9);
/*      */                                   }
/* 3101 */                                   out.write("\n\t\t</table>\n\n\n</div>\n\t</td>\n\t</tr>\n\t</table>\n\n\t</td>\n\t</tr>\n\n\n\n\t</table>\n\n\n\n\t</td>\n\n\t</tr>\n\t</table>\n\t</td>\n\t</tr>\n\t</table>\n\t</td>\n\n\t<td  class=\"conf-mon-content\">&nbsp;</td>\n\t</tr>\n\t<tr>\n\t<td class=\"conf-mon-btm-lft-corn\"></td>\n\n\t<td class=\"conf-mon-btm-mid-tile\"></td>\n\t<td class=\"conf-mon-btm-rig-corn\"></td>\n\n\t</tr>\n\t</table>\n</div>\n\n<!--  /******************************* Display Global Default values for polls ends************************************/  -->\n\n            <div id=\"anomalydiv\" style=\"display:none\">\n                 ");
/* 3102 */                                   if (FreeEditionDetails.getFreeEditionDetails().isAnomalyAllowed()) {
/* 3103 */                                     out.write("\n            <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"marg-btm\">\n    <tr>\n\n      <td width=\"72%\" height=\"31\" class=\"box-header\" >&nbsp;");
/* 3104 */                                     out.print(FormatUtil.getString("am.webclient.anomaly.predefined.heading.text"));
/* 3105 */                                     out.write("</td>\n          </tr>\n        </table>\n\n\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n    <tr>\n\n          <td height=\"28\">\n");
/*      */                                     
/* 3107 */                                     PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.get(PresentTag.class);
/* 3108 */                                     _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 3109 */                                     _jspx_th_logic_005fpresent_005f9.setParent(null);
/*      */                                     
/* 3111 */                                     _jspx_th_logic_005fpresent_005f9.setName("anomalyprofiles");
/* 3112 */                                     int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 3113 */                                     if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                                       for (;;) {
/* 3115 */                                         out.write("\n\n        <table align=\"center\" width=\"100%\" id=\"anomalyTable\" cellpadding=\"1\" cellspacing=\"0\"  border=\"0\" >\n          <tr>\n\t        <td width=\"5%\"height=\"28\" valign=\"center\"  class=\"columnheadingrightborder\">\n<input type=\"checkbox\" name=\"anomalyheadercheckbox\"  onClick=\"javascript:fnanomalySelectAll(this)\">\t</td>\n\t        <td width=\"22%\"height=\"28\" valign=\"center\"  class=\"columnheadingrightborder\">");
/* 3116 */                                         out.print(FormatUtil.getString("am.webclient.anomalyprofile.name.text"));
/* 3117 */                                         out.write("</td>\n\t        <td width=\"22%\"height=\"28\" valign=\"center\"  class=\"columnheadingrightborder\">");
/* 3118 */                                         out.print(FormatUtil.getString("am.webclient.anomalyprofile.baselinerange.text"));
/* 3119 */                                         out.write("</td>\n\t         <td width=\"22%\"height=\"28\" valign=\"center\"  class=\"columnheadingrightborder\">");
/* 3120 */                                         out.print(FormatUtil.getString("am.webclient.anomaly.upperlimit.heading.text"));
/* 3121 */                                         out.write("</td>\n\t        <td width=\"20%\"height=\"28\" valign=\"center\"  class=\"columnheadingrightborder\">");
/* 3122 */                                         out.print(FormatUtil.getString("am.webclient.anomaly.lowerlimit.heading.text"));
/* 3123 */                                         out.write("</td>\n\t        <td width=\"6%\"height=\"28\" valign=\"center\"  align=\"center\" class=\"columnheadingrightborder\">");
/*      */                                         
/* 3125 */                                         PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3126 */                                         _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 3127 */                                         _jspx_th_logic_005fpresent_005f10.setParent(_jspx_th_logic_005fpresent_005f9);
/*      */                                         
/* 3129 */                                         _jspx_th_logic_005fpresent_005f10.setRole("ADMIN");
/* 3130 */                                         int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 3131 */                                         if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                                           for (;;) {
/* 3133 */                                             out.print(FormatUtil.getString("am.webclient.threshold.editview"));
/* 3134 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 3135 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3139 */                                         if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 3140 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10); return;
/*      */                                         }
/*      */                                         
/* 3143 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 3144 */                                         out.write("</td>\n\t</tr>\n\t");
/*      */                                         
/* 3146 */                                         IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.get(IterateTag.class);
/* 3147 */                                         _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 3148 */                                         _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fpresent_005f9);
/*      */                                         
/* 3150 */                                         _jspx_th_logic_005fiterate_005f2.setName("anomalyprofiles");
/*      */                                         
/* 3152 */                                         _jspx_th_logic_005fiterate_005f2.setScope("request");
/*      */                                         
/* 3154 */                                         _jspx_th_logic_005fiterate_005f2.setId("row");
/*      */                                         
/* 3156 */                                         _jspx_th_logic_005fiterate_005f2.setIndexId("j");
/* 3157 */                                         int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 3158 */                                         if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 3159 */                                           Object row = null;
/* 3160 */                                           Integer j = null;
/* 3161 */                                           if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 3162 */                                             out = _jspx_page_context.pushBody();
/* 3163 */                                             _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 3164 */                                             _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                                           }
/* 3166 */                                           row = _jspx_page_context.findAttribute("row");
/* 3167 */                                           j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                           for (;;) {
/* 3169 */                                             out.write(10);
/* 3170 */                                             out.write(9);
/*      */                                             
/* 3172 */                                             String bgclass = "whitegrayborder";
/* 3173 */                                             if (j.intValue() % 2 != 0)
/*      */                                             {
/* 3175 */                                               bgclass = "yellowgrayborder";
/*      */                                             }
/*      */                                             
/*      */ 
/* 3179 */                                             String met = "showAnomalyAction";
/*      */                                             
/*      */ 
/* 3182 */                                             out.write("\n\t<tr >\n\n\t\t<td height=\"22\" class=\"");
/* 3183 */                                             out.print(bgclass);
/* 3184 */                                             out.write("\">\n\t\t<input type=\"checkbox\" name=\"anomalycheckbox\" value=\"");
/* 3185 */                                             out.print(((ArrayList)row).get(0));
/* 3186 */                                             out.write("\"></td>\n                <td height=\"22\" class=\"");
/* 3187 */                                             out.print(bgclass);
/* 3188 */                                             out.write("\" title=\"");
/* 3189 */                                             out.print(FormatUtil.getString((String)((ArrayList)row).get(1)));
/* 3190 */                                             out.write("\"><a href=\"/adminAction.do?method=");
/* 3191 */                                             out.print(met);
/* 3192 */                                             out.write("&anomalyID=");
/* 3193 */                                             out.print(((ArrayList)row).get(0));
/* 3194 */                                             out.write("&haid=");
/* 3195 */                                             out.print(request.getParameter("haid"));
/* 3196 */                                             out.write("&isanomaly=true\"><span class=\"staticlinks\">");
/* 3197 */                                             out.print(getTrimmedText(FormatUtil.getString((String)((ArrayList)row).get(1)), 25));
/* 3198 */                                             out.write("</span></a></td>\n\n        ");
/* 3199 */                                             String weeks = com.adventnet.appmanager.util.ReportDataUtilities.returnWeek(Integer.parseInt((String)((ArrayList)row).get(3)));
/* 3200 */                                             String months = com.adventnet.appmanager.util.ReportDataUtilities.retrunMonth(Integer.parseInt((String)((ArrayList)row).get(4)));
/* 3201 */                                             String years = weeks + " , " + months + " " + (String)((ArrayList)row).get(5);
/* 3202 */                                             String typecalc = (String)((ArrayList)row).get(6);
/* 3203 */                                             String btype = (String)((ArrayList)row).get(2);
/* 3204 */                                             if ("0".equals(btype)) {
/* 3205 */                                               years = FormatUtil.getString("am.webclient.anomaly.previous.message");
/*      */                                             }
/*      */                                             
/* 3208 */                                             if ("1".equals(typecalc)) {
/* 3209 */                                               typecalc = "%";
/*      */                                             } else {
/* 3211 */                                               typecalc = " ";
/*      */                                             }
/*      */                                             
/* 3214 */                                             out.write("\n\n\t<td height=\"22\" class=\"");
/* 3215 */                                             out.print(bgclass);
/* 3216 */                                             out.write("\" > ");
/* 3217 */                                             out.print(years);
/* 3218 */                                             out.write("\n\t</td>\n        <td height=\"22\" class=\"");
/* 3219 */                                             out.print(bgclass);
/* 3220 */                                             out.write("\">\n        ");
/* 3221 */                                             out.print(((ArrayList)row).get(7));
/* 3222 */                                             out.write("&nbsp; ");
/* 3223 */                                             out.print(typecalc);
/* 3224 */                                             out.write("\n        </td>\n         <td height=\"22\" class=\"");
/* 3225 */                                             out.print(bgclass);
/* 3226 */                                             out.write("\">\n        ");
/* 3227 */                                             out.print(((ArrayList)row).get(8));
/* 3228 */                                             out.write("&nbsp;");
/* 3229 */                                             out.print(typecalc);
/* 3230 */                                             out.write("\n        </td>\n        <td height=\"22\" class=\"");
/* 3231 */                                             out.print(bgclass);
/* 3232 */                                             out.write("\" align=\"center\">\n\n        <a href=\"/adminAction.do?method=");
/* 3233 */                                             out.print(met);
/* 3234 */                                             out.write("&anomalyID=");
/* 3235 */                                             out.print(((ArrayList)row).get(0));
/* 3236 */                                             out.write("&haid=");
/* 3237 */                                             out.print(request.getParameter("haid"));
/* 3238 */                                             out.write("&isanomaly=true\">\n       ");
/* 3239 */                                             if (_jspx_meth_logic_005fpresent_005f11(_jspx_th_logic_005fiterate_005f2, _jspx_page_context))
/*      */                                               return;
/* 3241 */                                             out.write("</a>\n        </td>\n\n        </tr>\n\n");
/* 3242 */                                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 3243 */                                             row = _jspx_page_context.findAttribute("row");
/* 3244 */                                             j = (Integer)_jspx_page_context.findAttribute("j");
/* 3245 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3248 */                                           if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 3249 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3252 */                                         if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 3253 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                                         }
/*      */                                         
/* 3256 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 3257 */                                         out.write("\n</table>\n\n\t<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"tablebottom\" >\n\t<tr>\n             <td height=\"26\" align=\"left\">");
/*      */                                         
/* 3259 */                                         PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3260 */                                         _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 3261 */                                         _jspx_th_logic_005fpresent_005f12.setParent(_jspx_th_logic_005fpresent_005f9);
/*      */                                         
/* 3263 */                                         _jspx_th_logic_005fpresent_005f12.setRole("ADMIN,ENTERPRISEADMIN");
/* 3264 */                                         int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 3265 */                                         if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */                                           for (;;) {
/* 3267 */                                             out.write("<A HREF=\"javascript:deleteAnomalySelections();\" class=\"staticlinks\">");
/* 3268 */                                             out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3269 */                                             out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n\t<a href=\"/showTile.do?TileName=.ThresholdConf&haid=null&isanomaly=true\" class=\"staticlinks\">");
/* 3270 */                                             out.print(FormatUtil.getString("am.webclient.threshold.addnew"));
/* 3271 */                                             out.write("</a>&nbsp;&nbsp;");
/* 3272 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 3273 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3277 */                                         if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 3278 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12); return;
/*      */                                         }
/*      */                                         
/* 3281 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 3282 */                                         out.write("</td>\n          </tr>\n\t</table>\n\n");
/* 3283 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 3284 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3288 */                                     if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 3289 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                                     }
/*      */                                     
/* 3292 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f9);
/* 3293 */                                     out.write(10);
/* 3294 */                                     out.write(10);
/*      */                                     
/* 3296 */                                     NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.get(NotPresentTag.class);
/* 3297 */                                     _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3298 */                                     _jspx_th_logic_005fnotPresent_005f2.setParent(null);
/*      */                                     
/* 3300 */                                     _jspx_th_logic_005fnotPresent_005f2.setName("anomalyprofiles");
/* 3301 */                                     int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3302 */                                     if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                                       for (;;) {
/* 3304 */                                         out.write("\n\n        <table cellpadding=\"10\" cellspacing=\"0\" width=\"100%\">\n          <tr>\n            <td class=\"bodytext emptyTableMsg\">");
/* 3305 */                                         out.print(FormatUtil.getString("am.webclient.anomaly.nomessage.text"));
/* 3306 */                                         out.write("&nbsp;");
/*      */                                         
/* 3308 */                                         PresentTag _jspx_th_logic_005fpresent_005f13 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3309 */                                         _jspx_th_logic_005fpresent_005f13.setPageContext(_jspx_page_context);
/* 3310 */                                         _jspx_th_logic_005fpresent_005f13.setParent(_jspx_th_logic_005fnotPresent_005f2);
/*      */                                         
/* 3312 */                                         _jspx_th_logic_005fpresent_005f13.setRole("ADMIN,ENTERPRISEADMIN");
/* 3313 */                                         int _jspx_eval_logic_005fpresent_005f13 = _jspx_th_logic_005fpresent_005f13.doStartTag();
/* 3314 */                                         if (_jspx_eval_logic_005fpresent_005f13 != 0) {
/*      */                                           for (;;) {
/* 3316 */                                             out.print(FormatUtil.getString("am.webclient.anomaly.create.link.text", new String[] { "" }));
/* 3317 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f13.doAfterBody();
/* 3318 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3322 */                                         if (_jspx_th_logic_005fpresent_005f13.doEndTag() == 5) {
/* 3323 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13); return;
/*      */                                         }
/*      */                                         
/* 3326 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13);
/* 3327 */                                         out.write("</td>\n          </tr></table>\n");
/* 3328 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3329 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3333 */                                     if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3334 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                                     }
/*      */                                     
/* 3337 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3338 */                                     out.write("\n\n          </td>\n          </tr>\n        </table>\n         <br>\n\n               <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"marg-btm\">\n    <tr>\n\n      <td width=\"72%\" height=\"31\" class=\"box-header\" >&nbsp;");
/* 3339 */                                     out.print(FormatUtil.getString("am.webclient.anomaly.expression.heading.text"));
/* 3340 */                                     out.write("</td>\n          </tr>\n        </table>\n\n\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n    <tr>\n\n          <td height=\"28\">\n");
/*      */                                     
/* 3342 */                                     PresentTag _jspx_th_logic_005fpresent_005f14 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.get(PresentTag.class);
/* 3343 */                                     _jspx_th_logic_005fpresent_005f14.setPageContext(_jspx_page_context);
/* 3344 */                                     _jspx_th_logic_005fpresent_005f14.setParent(null);
/*      */                                     
/* 3346 */                                     _jspx_th_logic_005fpresent_005f14.setName("anomalyexpressionprofiles");
/* 3347 */                                     int _jspx_eval_logic_005fpresent_005f14 = _jspx_th_logic_005fpresent_005f14.doStartTag();
/* 3348 */                                     if (_jspx_eval_logic_005fpresent_005f14 != 0) {
/*      */                                       for (;;) {
/* 3350 */                                         out.write("\n\n        <table align=\"center\" width=\"100%\" id=\"anomalyexpressionTable\" cellpadding=\"1\" cellspacing=\"0\"  border=\"0\" >\n          <tr>\n\t        <td width=\"5%\"height=\"28\" valign=\"center\"  class=\"columnheadingrightborder\">\n<input type=\"checkbox\" name=\"anomalyexpressionheadercheckbox\"  onClick=\"javascript:fnanomalyexpressionSelectAll(this)\">\t</td>\n\t        <td width=\"22%\"height=\"28\" valign=\"center\"  class=\"columnheadingrightborder\">");
/* 3351 */                                         out.print(FormatUtil.getString("am.webclient.anomalyprofile.name.text"));
/* 3352 */                                         out.write("</td>\n\t        <td width=\"22%\"height=\"28\" valign=\"center\"  class=\"columnheadingrightborder\">");
/* 3353 */                                         out.print(FormatUtil.getString("am.webclient.anomaly.expression1.text"));
/* 3354 */                                         out.write("</td>\n                <td width=\"12%\"height=\"28\" valign=\"center\"  class=\"columnheadingrightborder\">");
/* 3355 */                                         out.print(FormatUtil.getString("am.webclient.anomalyprofile.alarm.text"));
/* 3356 */                                         out.write("</td>\n\t         <td width=\"22%\"height=\"28\" valign=\"center\"  class=\"columnheadingrightborder\">");
/* 3357 */                                         out.print(FormatUtil.getString("am.webclient.anomaly.expression2.text"));
/* 3358 */                                         out.write("</td>\n                 <td width=\"12%\"height=\"28\" valign=\"center\"  class=\"columnheadingrightborder\">");
/* 3359 */                                         out.print(FormatUtil.getString("am.webclient.anomalyprofile.alarm.text"));
/* 3360 */                                         out.write("</td>\n\n\t        <td width=\"4%\"height=\"28\" valign=\"center\" align=\"center\" class=\"columnheadingrightborder\">");
/*      */                                         
/* 3362 */                                         PresentTag _jspx_th_logic_005fpresent_005f15 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3363 */                                         _jspx_th_logic_005fpresent_005f15.setPageContext(_jspx_page_context);
/* 3364 */                                         _jspx_th_logic_005fpresent_005f15.setParent(_jspx_th_logic_005fpresent_005f14);
/*      */                                         
/* 3366 */                                         _jspx_th_logic_005fpresent_005f15.setRole("ADMIN");
/* 3367 */                                         int _jspx_eval_logic_005fpresent_005f15 = _jspx_th_logic_005fpresent_005f15.doStartTag();
/* 3368 */                                         if (_jspx_eval_logic_005fpresent_005f15 != 0) {
/*      */                                           for (;;) {
/* 3370 */                                             out.print(FormatUtil.getString("am.webclient.threshold.editview"));
/* 3371 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f15.doAfterBody();
/* 3372 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3376 */                                         if (_jspx_th_logic_005fpresent_005f15.doEndTag() == 5) {
/* 3377 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f15); return;
/*      */                                         }
/*      */                                         
/* 3380 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f15);
/* 3381 */                                         out.write("</td>\n\t</tr>\n\t");
/*      */                                         
/* 3383 */                                         IterateTag _jspx_th_logic_005fiterate_005f3 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.get(IterateTag.class);
/* 3384 */                                         _jspx_th_logic_005fiterate_005f3.setPageContext(_jspx_page_context);
/* 3385 */                                         _jspx_th_logic_005fiterate_005f3.setParent(_jspx_th_logic_005fpresent_005f14);
/*      */                                         
/* 3387 */                                         _jspx_th_logic_005fiterate_005f3.setName("anomalyexpressionprofiles");
/*      */                                         
/* 3389 */                                         _jspx_th_logic_005fiterate_005f3.setScope("request");
/*      */                                         
/* 3391 */                                         _jspx_th_logic_005fiterate_005f3.setId("row1");
/*      */                                         
/* 3393 */                                         _jspx_th_logic_005fiterate_005f3.setIndexId("k");
/* 3394 */                                         int _jspx_eval_logic_005fiterate_005f3 = _jspx_th_logic_005fiterate_005f3.doStartTag();
/* 3395 */                                         if (_jspx_eval_logic_005fiterate_005f3 != 0) {
/* 3396 */                                           Object row1 = null;
/* 3397 */                                           Integer k = null;
/* 3398 */                                           if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 3399 */                                             out = _jspx_page_context.pushBody();
/* 3400 */                                             _jspx_th_logic_005fiterate_005f3.setBodyContent((BodyContent)out);
/* 3401 */                                             _jspx_th_logic_005fiterate_005f3.doInitBody();
/*      */                                           }
/* 3403 */                                           row1 = _jspx_page_context.findAttribute("row1");
/* 3404 */                                           k = (Integer)_jspx_page_context.findAttribute("k");
/*      */                                           for (;;) {
/* 3406 */                                             out.write(10);
/* 3407 */                                             out.write(9);
/*      */                                             
/* 3409 */                                             String bgclass = "whitegrayborder";
/* 3410 */                                             if (k.intValue() % 2 != 0)
/*      */                                             {
/* 3412 */                                               bgclass = "yellowgrayborder";
/*      */                                             }
/*      */                                             
/* 3415 */                                             out.write("\n\t<tr >\n            ");
/*      */                                             
/* 3417 */                                             String met = "showAnomalyAction";
/*      */                                             
/*      */ 
/* 3420 */                                             out.write("\n\t\t<td height=\"22\" class=\"");
/* 3421 */                                             out.print(bgclass);
/* 3422 */                                             out.write("\">\n\t\t<input type=\"checkbox\" name=\"anomalyexpressioncheckbox\" value=\"");
/* 3423 */                                             out.print(((ArrayList)row1).get(0));
/* 3424 */                                             out.write("\"></td>\n                <td height=\"22\" class=\"");
/* 3425 */                                             out.print(bgclass);
/* 3426 */                                             out.write("\" title=\"");
/* 3427 */                                             out.print(FormatUtil.getString((String)((ArrayList)row1).get(1)));
/* 3428 */                                             out.write("\"><a href=\"/adminAction.do?method=");
/* 3429 */                                             out.print(met);
/* 3430 */                                             out.write("&anomalyID=");
/* 3431 */                                             out.print(((ArrayList)row1).get(0));
/* 3432 */                                             out.write("&haid=");
/* 3433 */                                             out.print(request.getParameter("haid"));
/* 3434 */                                             out.write("&isanomaly=true\"><span class=\"staticlinks\">");
/* 3435 */                                             out.print(getTrimmedText(FormatUtil.getString((String)((ArrayList)row1).get(1)), 25));
/* 3436 */                                             out.write("</span></a></td>\n\n        ");
/* 3437 */                                             String exp1 = (String)((ArrayList)row1).get(14);
/* 3438 */                                             String exp2 = (String)((ArrayList)row1).get(15);
/*      */                                             
/* 3440 */                                             out.write("\n\t<td height=\"22\" class=\"");
/* 3441 */                                             out.print(bgclass);
/* 3442 */                                             out.write("\" > ");
/* 3443 */                                             out.print(exp1);
/* 3444 */                                             out.write("\n\t</td>\n        <td height=\"22\" class=\"");
/* 3445 */                                             out.print(bgclass);
/* 3446 */                                             out.write("\" > ");
/* 3447 */                                             out.print(com.adventnet.appmanager.util.CustomExpressionUtil.getAlarmTypeAsString((String)((ArrayList)row1).get(9)));
/* 3448 */                                             out.write("\n\t</td>\n        <td height=\"22\" class=\"");
/* 3449 */                                             out.print(bgclass);
/* 3450 */                                             out.write("\" > ");
/* 3451 */                                             out.print(exp2);
/* 3452 */                                             out.write("\n\t</td>\n        <td height=\"22\" class=\"");
/* 3453 */                                             out.print(bgclass);
/* 3454 */                                             out.write("\" > ");
/* 3455 */                                             out.print(com.adventnet.appmanager.util.CustomExpressionUtil.getAlarmTypeAsString((String)((ArrayList)row1).get(10)));
/* 3456 */                                             out.write("\n\t</td>\n\n        <td height=\"22\" class=\"");
/* 3457 */                                             out.print(bgclass);
/* 3458 */                                             out.write("\" align=\"center\">\n\n        <a href=\"/adminAction.do?method=");
/* 3459 */                                             out.print(met);
/* 3460 */                                             out.write("&anomalyID=");
/* 3461 */                                             out.print(((ArrayList)row1).get(0));
/* 3462 */                                             out.write("&haid=");
/* 3463 */                                             out.print(request.getParameter("haid"));
/* 3464 */                                             out.write("&isanomaly=true\">\n       ");
/* 3465 */                                             if (_jspx_meth_logic_005fpresent_005f16(_jspx_th_logic_005fiterate_005f3, _jspx_page_context))
/*      */                                               return;
/* 3467 */                                             out.write("</a>\n        </td>\n\n        </tr>\n\n");
/* 3468 */                                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f3.doAfterBody();
/* 3469 */                                             row1 = _jspx_page_context.findAttribute("row1");
/* 3470 */                                             k = (Integer)_jspx_page_context.findAttribute("k");
/* 3471 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3474 */                                           if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 3475 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3478 */                                         if (_jspx_th_logic_005fiterate_005f3.doEndTag() == 5) {
/* 3479 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3); return;
/*      */                                         }
/*      */                                         
/* 3482 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3);
/* 3483 */                                         out.write("\n</table>\n\n\t<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"tablebottom\" >\n\t<tr>\n             <td height=\"26\" align=\"left\">");
/*      */                                         
/* 3485 */                                         PresentTag _jspx_th_logic_005fpresent_005f17 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3486 */                                         _jspx_th_logic_005fpresent_005f17.setPageContext(_jspx_page_context);
/* 3487 */                                         _jspx_th_logic_005fpresent_005f17.setParent(_jspx_th_logic_005fpresent_005f14);
/*      */                                         
/* 3489 */                                         _jspx_th_logic_005fpresent_005f17.setRole("ADMIN,ENTERPRISEADMIN");
/* 3490 */                                         int _jspx_eval_logic_005fpresent_005f17 = _jspx_th_logic_005fpresent_005f17.doStartTag();
/* 3491 */                                         if (_jspx_eval_logic_005fpresent_005f17 != 0) {
/*      */                                           for (;;) {
/* 3493 */                                             out.write("<A HREF=\"javascript:deleteAnomalyExpressionSelections();\" class=\"staticlinks\">");
/* 3494 */                                             out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3495 */                                             out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n\t<a href=\"/showTile.do?TileName=.ThresholdConf&haid=null&isanomaly=true&typeofformula=expression\" class=\"staticlinks\">");
/* 3496 */                                             out.print(FormatUtil.getString("am.webclient.threshold.addnew"));
/* 3497 */                                             out.write("</a>&nbsp;&nbsp;");
/* 3498 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f17.doAfterBody();
/* 3499 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3503 */                                         if (_jspx_th_logic_005fpresent_005f17.doEndTag() == 5) {
/* 3504 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f17); return;
/*      */                                         }
/*      */                                         
/* 3507 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f17);
/* 3508 */                                         out.write("</td>\n          </tr>\n\t</table>\n\n");
/* 3509 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f14.doAfterBody();
/* 3510 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3514 */                                     if (_jspx_th_logic_005fpresent_005f14.doEndTag() == 5) {
/* 3515 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f14); return;
/*      */                                     }
/*      */                                     
/* 3518 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f14);
/* 3519 */                                     out.write(10);
/* 3520 */                                     out.write(10);
/*      */                                     
/* 3522 */                                     NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.get(NotPresentTag.class);
/* 3523 */                                     _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 3524 */                                     _jspx_th_logic_005fnotPresent_005f3.setParent(null);
/*      */                                     
/* 3526 */                                     _jspx_th_logic_005fnotPresent_005f3.setName("anomalyexpressionprofiles");
/* 3527 */                                     int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 3528 */                                     if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                                       for (;;) {
/* 3530 */                                         out.write("\n\n        <table cellpadding=\"10\" cellspacing=\"0\" width=\"100%\">\n          <tr>\n            <td class=\"bodytext emptyTableMsg\">");
/* 3531 */                                         out.print(FormatUtil.getString("am.webclient.anomaly.nomessage.text"));
/* 3532 */                                         out.write("&nbsp;");
/*      */                                         
/* 3534 */                                         PresentTag _jspx_th_logic_005fpresent_005f18 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3535 */                                         _jspx_th_logic_005fpresent_005f18.setPageContext(_jspx_page_context);
/* 3536 */                                         _jspx_th_logic_005fpresent_005f18.setParent(_jspx_th_logic_005fnotPresent_005f3);
/*      */                                         
/* 3538 */                                         _jspx_th_logic_005fpresent_005f18.setRole("ADMIN,ENTERPRISEADMIN");
/* 3539 */                                         int _jspx_eval_logic_005fpresent_005f18 = _jspx_th_logic_005fpresent_005f18.doStartTag();
/* 3540 */                                         if (_jspx_eval_logic_005fpresent_005f18 != 0) {
/*      */                                           for (;;) {
/* 3542 */                                             out.print(FormatUtil.getString("am.webclient.anomaly.create.link.text", new String[] { "&anomalyType=customExp" }));
/* 3543 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f18.doAfterBody();
/* 3544 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3548 */                                         if (_jspx_th_logic_005fpresent_005f18.doEndTag() == 5) {
/* 3549 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f18); return;
/*      */                                         }
/*      */                                         
/* 3552 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f18);
/* 3553 */                                         out.write("</td>\n          </tr></table>\n");
/* 3554 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 3555 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3559 */                                     if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 3560 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                                     }
/*      */                                     
/* 3563 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 3564 */                                     out.write("\n\n          </td>\n          </tr>\n        </table>\n          ");
/*      */                                   } else {
/* 3566 */                                     out.write("\n           <table class=\"messageboxfailure\" width=\"100%\" cellspacing=\"2\" cellpadding=\"2\" border=\"0\">\n                   <tr>\n\n\n\t    \t\t      <td width=\"6%\" align=\"center\">\n<img height=\"23\" width=\"23\" alt=\"Icon\" src=\"../images/icon_message_failure.gif\"/>\n</td>\n\t                   <td class=\"message\" height=\"34\" width=\"94%\">\n                       ");
/* 3567 */                                     String link = "<a style=\"font-size: 10px;\" href=\"mailto:" + FormatUtil.getString("product.talkback.mailid") + "\" class=\"new-login-email-link\"><b>" + FormatUtil.getString("product.talkback.mailid") + "</b></a> ";
/* 3568 */                                     FreeEditionDetails.getFreeEditionDetails(); if (FreeEditionDetails.anomalyMessage != null) {
/* 3569 */                                       out.write("\n                        ");
/* 3570 */                                       FreeEditionDetails.getFreeEditionDetails();out.print(FreeEditionDetails.anomalyMessage);
/* 3571 */                                       out.write("\n                        ");
/*      */                                     } else {
/* 3573 */                                       out.write("\n                        ");
/* 3574 */                                       out.print(FormatUtil.getString("am.webclient.anomaly.noanomalyaddon.text", new String[] { link }));
/* 3575 */                                       out.write("\n                        ");
/*      */                                     }
/* 3577 */                                     out.write("\n                  </td>\n\n                   </tr>\n               </table>\n\n\n        ");
/*      */                                   }
/* 3579 */                                   out.write("\n        </div>\n\n  <p class=\"tooltip\">&nbsp;</p>\n</form>\n</body>\n");
/*      */                                 }
/* 3581 */                               } } } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3582 */         out = _jspx_out;
/* 3583 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3584 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3585 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3588 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3594 */     PageContext pageContext = _jspx_page_context;
/* 3595 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3597 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3598 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3599 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3601 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3603 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3604 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3605 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3606 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3607 */       return true;
/*      */     }
/* 3609 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3610 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3615 */     PageContext pageContext = _jspx_page_context;
/* 3616 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3618 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3619 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3620 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 3622 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3623 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3624 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3626 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 3627 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3628 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3632 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3633 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3634 */       return true;
/*      */     }
/* 3636 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3642 */     PageContext pageContext = _jspx_page_context;
/* 3643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3645 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3646 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3647 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 3649 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3650 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3651 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3653 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 3654 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3655 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3659 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3660 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3661 */       return true;
/*      */     }
/* 3663 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3664 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3669 */     PageContext pageContext = _jspx_page_context;
/* 3670 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3672 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3673 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3674 */     _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */     
/* 3676 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3677 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3678 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 3680 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 3681 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3682 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3686 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3687 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3688 */       return true;
/*      */     }
/* 3690 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3691 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ffile_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3696 */     PageContext pageContext = _jspx_page_context;
/* 3697 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3699 */     FileTag _jspx_th_html_005ffile_005f0 = (FileTag)this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.get(FileTag.class);
/* 3700 */     _jspx_th_html_005ffile_005f0.setPageContext(_jspx_page_context);
/* 3701 */     _jspx_th_html_005ffile_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3703 */     _jspx_th_html_005ffile_005f0.setSize("30");
/*      */     
/* 3705 */     _jspx_th_html_005ffile_005f0.setProperty("theFile");
/* 3706 */     int _jspx_eval_html_005ffile_005f0 = _jspx_th_html_005ffile_005f0.doStartTag();
/* 3707 */     if (_jspx_th_html_005ffile_005f0.doEndTag() == 5) {
/* 3708 */       this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 3709 */       return true;
/*      */     }
/* 3711 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 3712 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3717 */     PageContext pageContext = _jspx_page_context;
/* 3718 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3720 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 3721 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 3722 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 3724 */     _jspx_th_bean_005fwrite_005f0.setName("mgs");
/*      */     
/* 3726 */     _jspx_th_bean_005fwrite_005f0.setProperty("label");
/* 3727 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 3728 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 3729 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 3730 */       return true;
/*      */     }
/* 3732 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 3733 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3738 */     PageContext pageContext = _jspx_page_context;
/* 3739 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3741 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 3742 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 3743 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 3745 */     _jspx_th_bean_005fwrite_005f1.setName("mgs");
/*      */     
/* 3747 */     _jspx_th_bean_005fwrite_005f1.setProperty("value");
/* 3748 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 3749 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 3750 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 3751 */       return true;
/*      */     }
/* 3753 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 3754 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3759 */     PageContext pageContext = _jspx_page_context;
/* 3760 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3762 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3763 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3764 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 3766 */     _jspx_th_c_005fif_005f0.setTest("${((j+1)%3) == 0}");
/* 3767 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3768 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3770 */         out.write("\n\t\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t");
/* 3771 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3772 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3776 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3777 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3778 */       return true;
/*      */     }
/* 3780 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3781 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3786 */     PageContext pageContext = _jspx_page_context;
/* 3787 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3789 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3790 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 3791 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/* 3792 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 3793 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 3795 */         out.write("\n\t\t\t");
/* 3796 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 3797 */           return true;
/* 3798 */         out.write("\n\t\t\t");
/* 3799 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 3800 */           return true;
/* 3801 */         out.write(10);
/* 3802 */         out.write(9);
/* 3803 */         out.write(9);
/* 3804 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3805 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3809 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3810 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3811 */       return true;
/*      */     }
/* 3813 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3814 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3819 */     PageContext pageContext = _jspx_page_context;
/* 3820 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3822 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3823 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3824 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 3826 */     _jspx_th_c_005fwhen_005f0.setTest("${row[14] == 0}");
/* 3827 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3828 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 3830 */         out.write("\n\t\t\t   ");
/* 3831 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 3832 */           return true;
/* 3833 */         out.write("\n\t\t\t");
/* 3834 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3835 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3839 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3840 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3841 */       return true;
/*      */     }
/* 3843 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3849 */     PageContext pageContext = _jspx_page_context;
/* 3850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3852 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3853 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3854 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 3856 */     _jspx_th_c_005fout_005f1.setValue("${row[14]}");
/* 3857 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3858 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3859 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3860 */       return true;
/*      */     }
/* 3862 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3863 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3868 */     PageContext pageContext = _jspx_page_context;
/* 3869 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3871 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3872 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3873 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 3874 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3875 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 3877 */         out.write("\n\t\t\t\t<a class=\"alarm-links\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindowWithHeightWidthPlacement('/common/viewThreshold.do?thresholdconfigid=");
/* 3878 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 3879 */           return true;
/* 3880 */         out.write("&threshname=");
/* 3881 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 3882 */           return true;
/* 3883 */         out.write("','950','500','100','100')\">");
/* 3884 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 3885 */           return true;
/* 3886 */         out.write("</a>\n\t\t\t");
/* 3887 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3888 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3892 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3893 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3894 */       return true;
/*      */     }
/* 3896 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3897 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3902 */     PageContext pageContext = _jspx_page_context;
/* 3903 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3905 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3906 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3907 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 3909 */     _jspx_th_c_005fout_005f2.setValue("${row[0]}");
/* 3910 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3911 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3912 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3913 */       return true;
/*      */     }
/* 3915 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3916 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3921 */     PageContext pageContext = _jspx_page_context;
/* 3922 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3924 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3925 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3926 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 3928 */     _jspx_th_c_005fout_005f3.setValue("${row[1]}");
/* 3929 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3930 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3931 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3932 */       return true;
/*      */     }
/* 3934 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3935 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3940 */     PageContext pageContext = _jspx_page_context;
/* 3941 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3943 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3944 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3945 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 3947 */     _jspx_th_c_005fout_005f4.setValue("${row[14]}");
/* 3948 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3949 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3950 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3951 */       return true;
/*      */     }
/* 3953 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3954 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f5(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3959 */     PageContext pageContext = _jspx_page_context;
/* 3960 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3962 */     PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3963 */     _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3964 */     _jspx_th_logic_005fpresent_005f5.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 3966 */     _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 3967 */     int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3968 */     if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */       for (;;) {
/* 3970 */         out.write(" <img src=\"/images/icon_edit.gif\"  border=\"0\">");
/* 3971 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3972 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3976 */     if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3977 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3978 */       return true;
/*      */     }
/* 3980 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3981 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f11(JspTag _jspx_th_logic_005fiterate_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3986 */     PageContext pageContext = _jspx_page_context;
/* 3987 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3989 */     PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3990 */     _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 3991 */     _jspx_th_logic_005fpresent_005f11.setParent((Tag)_jspx_th_logic_005fiterate_005f2);
/*      */     
/* 3993 */     _jspx_th_logic_005fpresent_005f11.setRole("ADMIN,ENTERPRISEADMIN");
/* 3994 */     int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 3995 */     if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */       for (;;) {
/* 3997 */         out.write(" <img src=\"/images/icon_edit.gif\"  border=\"0\">");
/* 3998 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 3999 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4003 */     if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 4004 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 4005 */       return true;
/*      */     }
/* 4007 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 4008 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f16(JspTag _jspx_th_logic_005fiterate_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4013 */     PageContext pageContext = _jspx_page_context;
/* 4014 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4016 */     PresentTag _jspx_th_logic_005fpresent_005f16 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4017 */     _jspx_th_logic_005fpresent_005f16.setPageContext(_jspx_page_context);
/* 4018 */     _jspx_th_logic_005fpresent_005f16.setParent((Tag)_jspx_th_logic_005fiterate_005f3);
/*      */     
/* 4020 */     _jspx_th_logic_005fpresent_005f16.setRole("ADMIN,ENTERPRISEADMIN");
/* 4021 */     int _jspx_eval_logic_005fpresent_005f16 = _jspx_th_logic_005fpresent_005f16.doStartTag();
/* 4022 */     if (_jspx_eval_logic_005fpresent_005f16 != 0) {
/*      */       for (;;) {
/* 4024 */         out.write(" <img src=\"/images/icon_edit.gif\"  border=\"0\">");
/* 4025 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f16.doAfterBody();
/* 4026 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4030 */     if (_jspx_th_logic_005fpresent_005f16.doEndTag() == 5) {
/* 4031 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f16);
/* 4032 */       return true;
/*      */     }
/* 4034 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f16);
/* 4035 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ThresholdProfiles_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */