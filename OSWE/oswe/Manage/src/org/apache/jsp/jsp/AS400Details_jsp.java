/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
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
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class AS400Details_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  719 */     java.net.InetAddress add = null;
/*  720 */     if (ip.equals("127.0.0.1")) {
/*  721 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  725 */       add = java.net.InetAddress.getByName(ip);
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
/*  855 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  856 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  859 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  860 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  861 */       set = AMConnectionPool.executeQueryStmt(query);
/*  862 */       if (set.next())
/*      */       {
/*  864 */         String helpLink = set.getString("LINK");
/*  865 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
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
/* 2185 */   static { _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2186 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2210 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2214 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2231 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2235 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2236 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/* 2239 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2242 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2244 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2249 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2257 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2260 */     JspWriter out = null;
/* 2261 */     Object page = this;
/* 2262 */     JspWriter _jspx_out = null;
/* 2263 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2267 */       response.setContentType("text/html;charset=UTF-8");
/* 2268 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2270 */       _jspx_page_context = pageContext;
/* 2271 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2272 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2273 */       session = pageContext.getSession();
/* 2274 */       out = pageContext.getOut();
/* 2275 */       _jspx_out = out;
/*      */       
/* 2277 */       out.write("<!DOCTYPE html>\n");
/* 2278 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2279 */       out.write(10);
/*      */       
/* 2281 */       request.setAttribute("HelpKey", "Monitors AS400 Server Details");
/*      */       
/* 2283 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2285 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2286 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2287 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2289 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayoutNoLeft.jsp");
/* 2290 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2291 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2293 */           out.write(10);
/* 2294 */           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2296 */           out.write(10);
/* 2297 */           out.write(10);
/* 2298 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2300 */           out.write(10);
/* 2301 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2303 */           out.write(10);
/* 2304 */           if (_jspx_meth_c_005fset_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2306 */           out.write(10);
/*      */           
/* 2308 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2309 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2310 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2312 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */           
/* 2314 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2315 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2316 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2317 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2318 */               out = _jspx_page_context.pushBody();
/* 2319 */               _jspx_th_tiles_005fput_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2320 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2323 */               out.write("\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/as400.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/Dialog.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/Utils.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/sortTable.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n\n<script>\nfunction submitForm(editform)\n{\n\t");
/* 2324 */               if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2326 */               out.write(10);
/* 2327 */               out.write(9);
/*      */               
/* 2329 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2330 */               _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2331 */               _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2333 */               _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2334 */               int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2335 */               if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                 for (;;) {
/* 2337 */                   out.write("\n        var dispname = trimAll(editform.displayname.value);\n\tif(dispname=='')\n\t{\n\t\talert('");
/* 2338 */                   out.print(FormatUtil.getString("am.webclient.common.validatename.text"));
/* 2339 */                   out.write("');\n\t\treturn;\n\t}\n\tpoll=trimAll(editform.pollInterval.value);\n\tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0' )\n\t{\n\t\talert('");
/* 2340 */                   out.print(FormatUtil.getString("am.webclient.common.validpollinginterval.text"));
/* 2341 */                   out.write("');\n\t\treturn;\n\t}\n        var poll=trimAll(editform.username.value);\n\tif(poll == '')\n\t{\n\t\talert('");
/* 2342 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/* 2343 */                   out.write("');\n\t\treturn;\n\t}\n        poll=trimAll(editform.password.value);\n\tif(poll == '')\n\t{\n\t\talert('");
/* 2344 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password"));
/* 2345 */                   out.write("');\n\t\treturn;\n\t}\n\n\teditform.submit();\n\t");
/* 2346 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2347 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2351 */               if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2352 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */               }
/*      */               
/* 2355 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2356 */               out.write("\n}\n\n     function toggledivmo(id,state) {\n\tif (state==\"1\"){\n                  $(\"#\"+id).css(\"visibility\",\"visible\");   //No I18N\n                     }\n\telse if (state==\"0\"){\n                 $(\"#\"+id).css(\"visibility\",\"hidden\");    //No I18N\n                    }\n\n\t}\n\t\n\tfunction fnGetCheckAndSubmit(selObj) {\n        ");
/* 2357 */               if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2359 */               out.write("\n var selObjId=\"\";\n var formId=\"\";\n var selObjVal=\"\";\n var checkboxObj=\"\";\n var checkboxVals=\"\";\n var count=0;\n var res = \"\";\n   selObjId=$(selObj).attr('id');  //NO I18N\n   formId=$(selObj).closest('form').attr('id');  //NO I18N\n   if(selObjId== \"removejob\"){\n   selObjVal=\"removejob\"; //NO I18N\n   }else if(selObjId== \"removesub\"){ //NO I18N\n   selObjVal=\"removesub\"; //NO I18N\n        }else if(selObjId== \"removedtaq\" || selObjId== \"removejobq\" || selObjId== \"removeoutq\"){ //NO I18N\n            selObjVal=\"removequeue\"; //NO I18N\n        }else if(selObjId.indexOf(\"editmon\")!=-1){\n            selObjVal=\"editmon\"; //NO I18N\n   }\n   else{\n   selObjVal=$(\"#\"+selObjId).val()\n   }\n  checkboxObj=$(\"#\"+formId+\" input[type='checkbox'].checkthis\"); //NO I18N\n\n  $(checkboxObj).each(function(){\n  if (this.checked) {\n  var tempId=$(this).val();\n  if(count==0){\n  checkboxVals=tempId;\n  }else{\n  checkboxVals+=\",\"+tempId;\n  }\n  count++;\n  }\n  });\n\n  if(count>0)\n    {\n        if(selObjVal == \"Actions\"){\n        alert(\"");
/* 2360 */               if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2362 */               out.write("\")\n        }else if(selObjVal == \"removejob\"){ //NO I18N\n\t\tres = confirm(\"");
/* 2363 */               if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2365 */               out.write("\"+\"?\")\n\t\t}else if(selObjVal == \"removesub\"){ //NO I18N\n\t\tres = confirm(\"");
/* 2366 */               if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2368 */               out.write("\"+\"?\")\n            }else if(selObjVal == \"editmon\"){ //NO I18N\n\t\treturn checkboxVals;\n            }else if(selObjVal == \"removequeue\"){ //NO I18N\n                res = confirm(\"");
/* 2369 */               if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2371 */               out.write("\"+\"?\")\n\t\t}\n\t\telse{\n        res = confirm(\"");
/* 2372 */               if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2374 */               out.write(" \"+selObjVal+\"?\")\n        }\n        if(res==true)\n        {\n\t\t$('input[name=rowids]').val(checkboxVals); //NO I18N\n\t\t$('input[name=fn]').val(selObjVal); //NO I18N\n\t\t$(\"#\"+formId).submit();\n         }\n    }\n    else\n    {\n    alert(\"");
/* 2375 */               if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2377 */               out.write("\");\n    }\n}\n\n    function showOptions(obj,id){\n\t$(document).ready(function(){\n\t$(obj).dblclick(function(event) {\n\t\tvar posX=event.pageX;\n\t\tvar posY=event.pageY-document.body.scrollTop;\n                showDialog(document.getElementById(id).innerHTML, 'position=absolute,closeButton=no,closeOnBodyClick=yes,left=' + posX + ',top=' + posY);//No I18N\n\t});\n});\t\t\n}\n</script>\n\n\n");
/*      */               
/* 2379 */               String mon_type = "AS400/iSeries";
/* 2380 */               String configure_link = (String)request.getAttribute("configurelink");
/* 2381 */               String resourceid = request.getParameter("resourceid");
/* 2382 */               int datatype = 1;
/* 2383 */               String datatypestr = null;
/* 2384 */               if (session.getAttribute("datatype") != null) {
/* 2385 */                 datatypestr = session.getAttribute("datatype").toString();
/*      */               }
/*      */               
/* 2388 */               if (datatypestr != null) {
/* 2389 */                 datatype = Integer.parseInt(datatypestr);
/*      */               }
/*      */               
/* 2392 */               String epage = request.getParameter("editPage");
/* 2393 */               request.setAttribute("fullpercent", "true");
/* 2394 */               Hashtable globals = (Hashtable)application.getAttribute("globalconfig");
/* 2395 */               String allowOperatorManage = (String)globals.get("allowOperatorManage");
/* 2396 */               boolean allowJOB = false;
/* 2397 */               boolean allowSPL = false;
/* 2398 */               String allowJob = (String)globals.get("allowJOB");
/* 2399 */               String allowSpl = (String)globals.get("allowSPL");
/* 2400 */               if ("true".equals(allowJob))
/*      */               {
/* 2402 */                 allowJOB = true;
/*      */               }
/* 2404 */               if ("true".equals(allowSpl))
/*      */               {
/* 2406 */                 allowSPL = true;
/*      */               }
/*      */               
/*      */ 
/* 2410 */               out.write("\n\n<script language=\"JavaScript\">\n\tfunction confirmDelete()\n \t {\n        ");
/* 2411 */               if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2413 */               out.write("\n        ");
/*      */               
/* 2415 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2416 */               _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2417 */               _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2419 */               _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 2420 */               int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2421 */               if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                 for (;;) {
/* 2423 */                   out.write("\n        var s = confirm(\"");
/* 2424 */                   out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 2425 */                   out.write("\");\n        if (s)\n        document.location.href=\"/deleteMO.do?method=deleteMO&type=AS400/iSeries&select=");
/* 2426 */                   out.print(resourceid);
/* 2427 */                   out.write("\";\n        ");
/* 2428 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2429 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2433 */               if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2434 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */               }
/*      */               
/* 2437 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2438 */               out.write("\n\t }\n\t        function confirmManage()\n \t {\n        ");
/* 2439 */               if (_jspx_meth_logic_005fpresent_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2441 */               out.write("\n        ");
/*      */               
/* 2443 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2444 */               _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 2445 */               _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2447 */               _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 2448 */               int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 2449 */               if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                 for (;;) {
/* 2451 */                   out.write("\n  var s = confirm(\"");
/* 2452 */                   out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2453 */                   out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2454 */                   if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fnotPresent_005f2, _jspx_page_context))
/*      */                     return;
/* 2456 */                   out.write("\";\n        ");
/* 2457 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 2458 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2462 */               if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 2463 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */               }
/*      */               
/* 2466 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 2467 */               out.write("\n\t }\n\n         function confirmUnManage()\n \t {\n        ");
/* 2468 */               if (_jspx_meth_logic_005fpresent_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2470 */               out.write("\n        ");
/*      */               
/* 2472 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2473 */               _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 2474 */               _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2476 */               _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 2477 */               int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 2478 */               if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                 for (;;) {
/* 2480 */                   out.write("\n        var show_msg=\"false\";\n        var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2481 */                   out.print(request.getParameter("resourceid"));
/* 2482 */                   out.write("; //No i18n\n        $.ajax({\n          type:'POST', //No i18n\n          url:url,\n          async:false,\n          success: function(data)\n          {\n            show_msg=data\n          }\n        });\n        if(show_msg.indexOf(\"true\")>-1)\n        {\n            alert(\"");
/* 2483 */                   out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2484 */                   out.write("\");\n\t\t  \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2485 */                   if (_jspx_meth_c_005fout_005f1(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                     return;
/* 2487 */                   out.write("\";\n\t\n        }\n      else { \n   \n   var s = confirm(\"");
/* 2488 */                   out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2489 */                   out.write("\");\n   if (s){\n    \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2490 */                   if (_jspx_meth_c_005fout_005f2(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                     return;
/* 2492 */                   out.write("\"; //No I18N\n\t\n }\n\n  } \n \t \n        ");
/* 2493 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 2494 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2498 */               if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 2499 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */               }
/*      */               
/* 2502 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 2503 */               out.write("\n\t }\n\t \n\t function reconfigure()\n{\n\t$(document).ready(function(){\n\n    $(\"#Reconfigure\").slideToggle(\"normal\");  //No I18N\n });\n\n}\n\n    function getJobLog(resid,jobname,user,number)\n    {\n        ");
/*      */               
/* 2505 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2506 */               _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 2507 */               _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2509 */               _jspx_th_logic_005fnotPresent_005f4.setRole("ADMIN");
/* 2510 */               int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 2511 */               if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                 for (;;) {
/* 2513 */                   out.write("\n        ");
/* 2514 */                   if (!allowJOB) {
/* 2515 */                     out.write("\n        alert(\"");
/* 2516 */                     if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                       return;
/* 2518 */                     out.write("\");\n        return;\n        ");
/*      */                   }
/* 2520 */                   out.write("\n        ");
/* 2521 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 2522 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2526 */               if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 2527 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */               }
/*      */               
/* 2530 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 2531 */               out.write("\n        var s = confirm('");
/* 2532 */               if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2534 */               out.write("');\n        if(s){\n            fnOpenNewWindow('/as400.do?method=dspjoblog&resourceid='+resid+'&jobName='+jobname+'&user='+user+'&jobNumber='+number); //No I18N\n        }\n    }\n\n    function viewSpool(rowid,resid)\n    {\n        ");
/*      */               
/* 2536 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2537 */               _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 2538 */               _jspx_th_logic_005fnotPresent_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2540 */               _jspx_th_logic_005fnotPresent_005f5.setRole("ADMIN");
/* 2541 */               int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 2542 */               if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                 for (;;) {
/* 2544 */                   out.write("\n        ");
/* 2545 */                   if (!allowSPL) {
/* 2546 */                     out.write("\n        alert(\"");
/* 2547 */                     if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_logic_005fnotPresent_005f5, _jspx_page_context))
/*      */                       return;
/* 2549 */                     out.write("\");\n        return;\n        ");
/*      */                   }
/* 2551 */                   out.write("\n        ");
/* 2552 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 2553 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2557 */               if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 2558 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5); return;
/*      */               }
/*      */               
/* 2561 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 2562 */               out.write("\n        var s = confirm('");
/* 2563 */               if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2565 */               out.write("');\n        if(s){\n            fnOpenNewWindow('/as400.do?method=spoolview&rowids='+rowid+'&resourceid='+resid); //No I18N\n        }\n    }\n</script>\n");
/* 2566 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 2568 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2569 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2570 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2572 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 2574 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 2576 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 2578 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2579 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2580 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2581 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 2584 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2585 */               String available = null;
/* 2586 */               available = (String)_jspx_page_context.findAttribute("available");
/* 2587 */               out.write(10);
/*      */               
/* 2589 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2590 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2591 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2593 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 2595 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 2597 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 2599 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2600 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2601 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2602 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 2605 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2606 */               String unavailable = null;
/* 2607 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2608 */               out.write(10);
/*      */               
/* 2610 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2611 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2612 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2614 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 2616 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 2618 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 2620 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2621 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2622 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2623 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 2626 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2627 */               String unmanaged = null;
/* 2628 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2629 */               out.write(10);
/*      */               
/* 2631 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2632 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2633 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2635 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 2637 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 2639 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 2641 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2642 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2643 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2644 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 2647 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2648 */               String scheduled = null;
/* 2649 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2650 */               out.write(10);
/*      */               
/* 2652 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2653 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2654 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2656 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2658 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2660 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2662 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2663 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2664 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2665 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 2668 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2669 */               String critical = null;
/* 2670 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 2671 */               out.write(10);
/*      */               
/* 2673 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2674 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2675 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2677 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 2679 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 2681 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 2683 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2684 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2685 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2686 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 2689 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2690 */               String clear = null;
/* 2691 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 2692 */               out.write(10);
/*      */               
/* 2694 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2695 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2696 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2698 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 2700 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 2702 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 2704 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2705 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2706 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2707 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 2710 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2711 */               String warning = null;
/* 2712 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 2713 */               out.write(10);
/* 2714 */               out.write(10);
/*      */               
/* 2716 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2717 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 2719 */               out.write(10);
/* 2720 */               out.write(10);
/* 2721 */               out.write(10);
/* 2722 */               out.write("\n \n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t");
/*      */               
/* 2724 */               Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/*      */               
/* 2726 */               String haid = request.getParameter("haid");
/* 2727 */               String haName = null;
/* 2728 */               if (haid != null)
/*      */               {
/* 2730 */                 haName = (String)ht.get(haid);
/*      */               }
/* 2732 */               String restyp = BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName);
/*      */               
/* 2734 */               out.write(10);
/* 2735 */               out.write(10);
/* 2736 */               out.write(9);
/*      */               
/* 2738 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2739 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2740 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2742 */               _jspx_th_c_005fif_005f2.setTest("${!empty param.haid  && (empty invalidhaid) && param.haid != 'null'}");
/* 2743 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2744 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/* 2746 */                   out.write("\n      \t\t<td class=\"bcsign\" valign=\"top\"> ");
/* 2747 */                   out.print(BreadcrumbUtil.getHomePage(request));
/* 2748 */                   out.write(" &gt; ");
/* 2749 */                   out.print(BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2750 */                   out.write(" &gt; <span class=\"bcactive\">");
/* 2751 */                   out.print(request.getParameter("resourcename"));
/* 2752 */                   out.write("</span></td>\n\t");
/* 2753 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2754 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2758 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2759 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */               }
/*      */               
/* 2762 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2763 */               out.write(10);
/* 2764 */               out.write(9);
/*      */               
/* 2766 */               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2767 */               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2768 */               _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2770 */               _jspx_th_c_005fif_005f3.setTest("${(empty param.haid || param.haid=='null'|| (!empty invalidhaid))}");
/* 2771 */               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2772 */               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                 for (;;) {
/* 2774 */                   out.write("\n      <td class=\"bcsign\" valign=\"top\"> ");
/* 2775 */                   out.print(BreadcrumbUtil.getMonitorsPage());
/* 2776 */                   out.write(" &gt; ");
/* 2777 */                   out.print(BreadcrumbUtil.getMonitorResourceTypes("AS400/iSeries"));
/* 2778 */                   out.write(" &gt; <span class=\"bcactive\">");
/* 2779 */                   out.print(request.getParameter("resourcename"));
/* 2780 */                   out.write(" </span></td>\n\t");
/* 2781 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2782 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2786 */               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2787 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */               }
/*      */               
/* 2790 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2791 */               out.write("\n<td>\n<table  cellspacing=\"0\" cellpadding=\"0\" align=\"right\">\n\t\t\t<tr>\n\t\t\t\t<td class=\"buttons btn_action\" id=\"apmdetails\" align=\"center\" onclick=\"globalShowMenuInDialogwithXandY('apmdetails', 'apmdetailsDiv',132,0);\">\n\t\t\t\t\t");
/* 2792 */               if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2794 */               out.write(" <img src=\"/images/icon_black_arrow.gif\" border=\"0\" style=\"margin-bottom:2px;\"/>\n\t\t\t\t\t<div style=\"display:none;\" id=\"apmdetailsDiv\" >\n\t\t\t\t<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"apmdetailsDivMenu\">\n\n  <!--Alert configuration should be enabled for Admin and Demo users alone-->\n  ");
/*      */               
/* 2796 */               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2797 */               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2798 */               _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2800 */               _jspx_th_c_005fif_005f4.setTest("${!empty ADMIN || !empty DEMO}");
/* 2801 */               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2802 */               if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                 for (;;) {
/* 2804 */                   out.write("\n   <tr>\n          <td  class=\"leftlinkstd-conf\">\n       \t");
/*      */                   
/* 2806 */                   ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2807 */                   _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2808 */                   _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_c_005fif_005f4);
/* 2809 */                   int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2810 */                   if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                     for (;;) {
/* 2812 */                       out.write("\n         ");
/*      */                       
/* 2814 */                       WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2815 */                       _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2816 */                       _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                       
/* 2818 */                       _jspx_th_c_005fwhen_005f0.setTest("${param.all=='true'}");
/* 2819 */                       int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2820 */                       if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                         for (;;) {
/* 2822 */                           out.write("\n                <img src=\"images/icon_associateaction.gif\" border=\"0\" /> &nbsp;");
/* 2823 */                           out.print(ALERTCONFIG_TEXT);
/* 2824 */                           out.write("\n         ");
/* 2825 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2826 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2830 */                       if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2831 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                       }
/*      */                       
/* 2834 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2835 */                       out.write("\n  \t");
/*      */                       
/* 2837 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2838 */                       _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2839 */                       _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2840 */                       int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2841 */                       if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                         for (;;) {
/* 2843 */                           out.write("\n                                                    <a href=");
/* 2844 */                           if (_jspx_meth_logic_005fnotPresent_005f6(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                             return;
/* 2846 */                           if (_jspx_meth_logic_005fpresent_005f5(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                             return;
/* 2848 */                           out.write("\n                                                    class=\"new-left-links\">\n                                                    <img src=\"images/icon_associateaction.gif\" border=\"0\" /> &nbsp;");
/* 2849 */                           out.print(ALERTCONFIG_TEXT);
/* 2850 */                           out.write("</a>\n              ");
/* 2851 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2852 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2856 */                       if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2857 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                       }
/*      */                       
/* 2860 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2861 */                       out.write("\n\t      ");
/* 2862 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2863 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2867 */                   if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2868 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                   }
/*      */                   
/* 2871 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2872 */                   out.write("\n            </td>\n        </tr>\n  ");
/* 2873 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2874 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2878 */               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2879 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */               }
/*      */               
/* 2882 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2883 */               out.write("\n  <!-- Edit link should be enabled for Enterprise Edition when SSO is enabled -->\n  ");
/* 2884 */               if (com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled()) {
/* 2885 */                 out.write(" \n   ");
/*      */                 
/* 2887 */                 PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2888 */                 _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 2889 */                 _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 2891 */                 _jspx_th_logic_005fpresent_005f6.setRole("ENTERPRISEADMIN");
/* 2892 */                 int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 2893 */                 if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                   for (;;) {
/* 2895 */                     out.write("\n\t<tr>\n\t\t<td class=\"leftlinkstd-conf\">\n\t\t\t<a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 2896 */                     out.print(request.getParameter("resourceid"));
/* 2897 */                     out.write("&method=showResourceForResourceID&aam_jump=true&editPage=true\" class=\"new-left-links\"><img src=\"images/icon_edit.gif\" border=\"0\"/>&nbsp;");
/* 2898 */                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2899 */                     out.write("</a>\n\t\t</td>\n\t</tr>\n  ");
/* 2900 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 2901 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2905 */                 if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 2906 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                 }
/*      */                 
/* 2909 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 2910 */                 out.write(10);
/* 2911 */                 out.write(32);
/* 2912 */                 out.write(32);
/*      */               }
/* 2914 */               out.write("\n  \n<!-- Edit link should be enabled for ADMIN and DEMO Users alone -->\n");
/*      */               
/* 2916 */               IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2917 */               _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2918 */               _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2920 */               _jspx_th_c_005fif_005f5.setTest("${!empty ADMIN || !empty DEMO}");
/* 2921 */               int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2922 */               if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                 for (;;) {
/* 2924 */                   out.write("\n  <tr>\n    <td class=\"leftlinkstd-conf\">\n ");
/*      */                   
/* 2926 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2927 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2928 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fif_005f5);
/* 2929 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2930 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/* 2932 */                       out.write("\n    ");
/*      */                       
/* 2934 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2935 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2936 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/* 2938 */                       _jspx_th_c_005fwhen_005f1.setTest("${uri=='/reconfigure.do'}");
/* 2939 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2940 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/* 2942 */                           out.write("\n        <img src=\"images/icon_edit.gif\" border=\"0\"/>&nbsp;");
/* 2943 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2944 */                           out.write(10);
/* 2945 */                           out.write(32);
/* 2946 */                           out.write(32);
/* 2947 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2948 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2952 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2953 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/* 2956 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2957 */                       out.write(10);
/* 2958 */                       out.write(32);
/* 2959 */                       out.write(32);
/*      */                       
/* 2961 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2962 */                       _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2963 */                       _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2964 */                       int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2965 */                       if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                         for (;;) {
/* 2967 */                           out.write("\n    <a href=\"javascript:reconfigure()\"\n      class=\"new-left-links\"><img src=\"images/icon_edit.gif\" border=\"0\"/>&nbsp;");
/* 2968 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2969 */                           out.write("</a>\n  ");
/* 2970 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2971 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2975 */                       if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2976 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                       }
/*      */                       
/* 2979 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2980 */                       out.write(10);
/* 2981 */                       out.write(32);
/* 2982 */                       out.write(32);
/* 2983 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2984 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2988 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2989 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/* 2992 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2993 */                   out.write("\n  </td>\n  </tr>\n");
/* 2994 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2995 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2999 */               if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3000 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */               }
/*      */               
/* 3003 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3004 */               out.write(10);
/* 3005 */               out.write(10);
/*      */               
/* 3007 */               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3008 */               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3009 */               _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3011 */               _jspx_th_c_005fif_005f6.setTest("${!empty ADMIN || !empty DEMO}");
/* 3012 */               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3013 */               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                 for (;;) {
/* 3015 */                   out.write("\n  <tr>\n    <td class=\"leftlinkstd-conf\" >\n     <A href=\"javascript:confirmDelete();\" class=\"new-left-links\"><img src=\"images/deleteWidget.gif\" border=\"0\" />&nbsp;");
/* 3016 */                   out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3017 */                   out.write("</A></td>\n  \t\n  </tr>\n \n  ");
/* 3018 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3019 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3023 */               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3024 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */               }
/*      */               
/* 3027 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3028 */               out.write(10);
/*      */               
/* 3030 */               IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3031 */               _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3032 */               _jspx_th_c_005fif_005f7.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3034 */               _jspx_th_c_005fif_005f7.setTest("${!empty ADMIN || !empty DEMO}");
/* 3035 */               int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3036 */               if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                 for (;;) {
/* 3038 */                   out.write("\n  <tr>\n  ");
/*      */                   
/* 3040 */                   if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                   {
/*      */ 
/* 3043 */                     out.write("\n    <td class=\"leftlinkstd-conf\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\"> <img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3044 */                     out.print(FormatUtil.getString("Manage"));
/* 3045 */                     out.write("</A></td>\n    ");
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/*      */ 
/* 3051 */                     out.write("\n    <td class=\"leftlinkstd-conf\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\"> <img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3052 */                     out.print(FormatUtil.getString("UnManage"));
/* 3053 */                     out.write("</A></td>\n    ");
/*      */                   }
/*      */                   
/*      */ 
/* 3057 */                   out.write("\n  </tr>\n  ");
/* 3058 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3059 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3063 */               if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3064 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */               }
/*      */               
/* 3067 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3068 */               out.write("\n\n  ");
/*      */               
/* 3070 */               IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3071 */               _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3072 */               _jspx_th_c_005fif_005f8.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3074 */               _jspx_th_c_005fif_005f8.setTest("${OPERATOR }");
/* 3075 */               int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3076 */               if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                 for (;;) {
/* 3078 */                   out.write(10);
/* 3079 */                   out.write(32);
/* 3080 */                   out.write(32);
/*      */                   
/* 3082 */                   if (allowOperatorManage.equals("true"))
/*      */                   {
/*      */ 
/* 3085 */                     out.write("\n  <tr>\n  ");
/*      */                     
/* 3087 */                     if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                     {
/*      */ 
/* 3090 */                       out.write("\n    <td class=\"leftlinkstd-conf\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\"> <img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3091 */                       out.print(FormatUtil.getString("Manage"));
/* 3092 */                       out.write("</A></td>\n    ");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/*      */ 
/* 3098 */                       out.write("\n    <td class=\"leftlinkstd-conf\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\"> <img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3099 */                       out.print(FormatUtil.getString("UnManage"));
/* 3100 */                       out.write("</A></td>\n    ");
/*      */                     }
/*      */                     
/*      */ 
/* 3104 */                     out.write("\n  </tr>\n   ");
/*      */                   }
/*      */                   
/*      */ 
/* 3108 */                   out.write(10);
/* 3109 */                   out.write(32);
/* 3110 */                   out.write(32);
/* 3111 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3112 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3116 */               if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3117 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */               }
/*      */               
/* 3120 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3121 */               out.write(10);
/* 3122 */               out.write(10);
/* 3123 */               out.write(10);
/*      */               
/* 3125 */               if ((configure_link != null) && (!configure_link.equals("null")))
/*      */               {
/*      */ 
/* 3128 */                 out.write("\n\n   ");
/*      */                 
/* 3130 */                 IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3131 */                 _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3132 */                 _jspx_th_c_005fif_005f9.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 3134 */                 _jspx_th_c_005fif_005f9.setTest("${!empty ADMIN || !empty DEMO}");
/* 3135 */                 int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3136 */                 if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                   for (;;) {
/* 3138 */                     out.write("\n    <tr>\n    <td class=\"leftlinkstd-conf\" >\n\n    \t<a href=\"");
/* 3139 */                     out.print(request.getAttribute("configurelink"));
/* 3140 */                     out.write("\" onclick=\"javascript:return subAddCustom(this);\" class=\"new-left-links\">\n    \t  ");
/* 3141 */                     out.print(FormatUtil.getString("am.webclient.common.addcustomattributes.text"));
/* 3142 */                     out.write("\n    \t</a>\n    </td>\n  </tr>\n   ");
/* 3143 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3144 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3148 */                 if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3149 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                 }
/*      */                 
/* 3152 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3153 */                 out.write("\n   ");
/*      */               }
/*      */               
/*      */ 
/* 3157 */               out.write("\n                                ");
/*      */               
/* 3159 */               IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3160 */               _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3161 */               _jspx_th_c_005fif_005f10.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3163 */               _jspx_th_c_005fif_005f10.setTest("${!empty ADMIN || !empty DEMO}");
/* 3164 */               int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3165 */               if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                 for (;;) {
/* 3167 */                   out.write("\n  \t   \t<tr>\n  \t       \t <td class=\"leftlinkstd-conf\" >\n             \t<a  href=\"javascript:void(0)\" class=\"new-left-links\" onclick=");
/* 3168 */                   if (_jspx_meth_logic_005fnotPresent_005f7(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                     return;
/* 3170 */                   out.write("\n                ");
/* 3171 */                   if (_jspx_meth_logic_005fpresent_005f7(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                     return;
/* 3173 */                   out.write(" >\n                <img src=\"images/icon_actions_disabled.gif\" border=\"0\" />&nbsp;");
/* 3174 */                   if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                     return;
/* 3176 */                   out.write("</a>\n  \t       \t </td>\n  \t      \t</tr>\n\n\t\t  <tr>\n\t\t  <td  class=\"leftlinkstd-conf\" >\n          \t<a href=");
/*      */                   
/* 3178 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f8 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3179 */                   _jspx_th_logic_005fnotPresent_005f8.setPageContext(_jspx_page_context);
/* 3180 */                   _jspx_th_logic_005fnotPresent_005f8.setParent(_jspx_th_c_005fif_005f10);
/*      */                   
/* 3182 */                   _jspx_th_logic_005fnotPresent_005f8.setRole("DEMO");
/* 3183 */                   int _jspx_eval_logic_005fnotPresent_005f8 = _jspx_th_logic_005fnotPresent_005f8.doStartTag();
/* 3184 */                   if (_jspx_eval_logic_005fnotPresent_005f8 != 0) {
/*      */                     for (;;) {
/* 3186 */                       out.write("\"/customReports.do?method=showCustomReports&selectedType=");
/* 3187 */                       out.print(mon_type);
/* 3188 */                       out.write(34);
/* 3189 */                       out.write(32);
/* 3190 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f8.doAfterBody();
/* 3191 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3195 */                   if (_jspx_th_logic_005fnotPresent_005f8.doEndTag() == 5) {
/* 3196 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8); return;
/*      */                   }
/*      */                   
/* 3199 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 3200 */                   out.write("\n            ");
/* 3201 */                   if (_jspx_meth_logic_005fpresent_005f8(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                     return;
/* 3203 */                   out.write(" class=\"new-left-links\">\n            <img src=\"images/icon-anamoly-responsetime.gif\" border=\"0\" /> &nbsp;");
/* 3204 */                   out.print(FormatUtil.getString("am.webclient.cam.enablereports.link"));
/* 3205 */                   out.write("</a></td>\n\t\t  </tr>\n\n  ");
/* 3206 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3207 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3211 */               if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3212 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */               }
/*      */               
/* 3215 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3216 */               out.write("\n   ");
/*      */               
/* 3218 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f9 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3219 */               _jspx_th_logic_005fnotPresent_005f9.setPageContext(_jspx_page_context);
/* 3220 */               _jspx_th_logic_005fnotPresent_005f9.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3222 */               _jspx_th_logic_005fnotPresent_005f9.setRole("DEMO");
/* 3223 */               int _jspx_eval_logic_005fnotPresent_005f9 = _jspx_th_logic_005fnotPresent_005f9.doStartTag();
/* 3224 */               if (_jspx_eval_logic_005fnotPresent_005f9 != 0) {
/*      */                 for (;;) {
/* 3226 */                   out.write("\n    ");
/*      */                   
/* 3228 */                   String resourceid_poll = request.getParameter("resourceid");
/* 3229 */                   String resourcetype_poll = request.getParameter("type");
/*      */                   
/* 3231 */                   out.write("\n      <tr>\n      <td  class=\"leftlinkstd-conf\" >\n      <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3232 */                   out.print(resourceid_poll);
/* 3233 */                   out.write("&resourcetype=");
/* 3234 */                   out.print(resourcetype_poll);
/* 3235 */                   out.write("\" class=\"new-left-links\"><img src=\"images/cam_report_enabled.gif\" border=\"0\" style=\"position:relative; top:3px;\"/> &nbsp; ");
/* 3236 */                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3237 */                   out.write("</a></td>\n    </tr>\n\n    ");
/* 3238 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f9.doAfterBody();
/* 3239 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3243 */               if (_jspx_th_logic_005fnotPresent_005f9.doEndTag() == 5) {
/* 3244 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f9); return;
/*      */               }
/*      */               
/* 3247 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f9);
/* 3248 */               out.write("\n    ");
/*      */               
/* 3250 */               PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3251 */               _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 3252 */               _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3254 */               _jspx_th_logic_005fpresent_005f9.setRole("DEMO");
/* 3255 */               int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 3256 */               if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                 for (;;) {
/* 3258 */                   out.write("\n          <tr>\n          <td  class=\"leftlinkstd-conf\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\"><img src=\"images/cam_report_enabled.gif\" border=\"0\" style=\"position:relative; top:3px;\"/> &nbsp;");
/* 3259 */                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3260 */                   out.write("</a></td>\n          </tr>\n    ");
/* 3261 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 3262 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3266 */               if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 3267 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */               }
/*      */               
/* 3270 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 3271 */               out.write("\n    ");
/* 3272 */               out.write("<!-- $Id$-->\n\n\n  \n");
/*      */               
/* 3274 */               if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */               {
/* 3276 */                 Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 3277 */                 String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                 
/* 3279 */                 String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 3280 */                 String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 3281 */                 if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                 {
/* 3283 */                   if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                   {
/*      */ 
/* 3286 */                     out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3287 */                     out.print(ciInfoUrl);
/* 3288 */                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3289 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3290 */                     out.write("</a></td>");
/* 3291 */                     out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3292 */                     out.print(ciRLUrl);
/* 3293 */                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3294 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3295 */                     out.write("</a></td>");
/* 3296 */                     out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                   }
/* 3300 */                   else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                   {
/*      */ 
/* 3303 */                     out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 3304 */                     out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 3305 */                     out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3306 */                     out.print(ciInfoUrl);
/* 3307 */                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3308 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3309 */                     out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3310 */                     out.print(ciRLUrl);
/* 3311 */                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3312 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3313 */                     out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/* 3318 */               out.write("\n \n \n\n");
/* 3319 */               out.write("\n  </table>\n</div>\n</td>\n  </tr>\n\n</table>\n</td>\n    </tr>\n\n\n</table>\n\n\n\n");
/* 3320 */               if ((epage != null) && (epage.equals("true")))
/*      */               {
/* 3322 */                 out.write("\n<div id=\"Reconfigure\" style=\"display:block\">\n");
/*      */               } else {
/* 3324 */                 out.write("\n<div id=\"Reconfigure\" style=\"display:none\">\n");
/*      */               }
/* 3326 */               out.write("\n<br>\n");
/*      */               
/* 3328 */               String type = request.getParameter("type");
/* 3329 */               String pollInterval = request.getParameter("pollInterval");
/* 3330 */               if (pollInterval == null) {
/* 3331 */                 pollInterval = "5";
/*      */               }
/* 3333 */               String displayname = request.getParameter("resourcename");
/* 3334 */               String username = (String)request.getAttribute("username");
/*      */               
/* 3336 */               out.write(10);
/* 3337 */               out.write(10);
/*      */               
/* 3339 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 3340 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 3341 */               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3343 */               _jspx_th_html_005fform_005f0.setAction("/as400");
/*      */               
/* 3345 */               _jspx_th_html_005fform_005f0.setMethod("get");
/*      */               
/* 3347 */               _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 3348 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 3349 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                 for (;;) {
/* 3351 */                   out.write("\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 3352 */                   out.print(request.getParameter("name"));
/* 3353 */                   out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 3354 */                   out.print(type);
/* 3355 */                   out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"editMonitor\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 3356 */                   out.print(request.getParameter("resourceid"));
/* 3357 */                   out.write("\">\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n    <td height=\"28\"   class=\"tableheading\">");
/* 3358 */                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3359 */                   out.write("\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:reconfigure()\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n<span class=\"bodytextboldwhiteun\" ><a href=\"javascript:hideDiv('edit')\" class=\"staticlinks\">");
/* 3360 */                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 3361 */                   out.write("</a></span>\n</td>\n</tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n\t<TR>\n\t<TD height=\"28\" width=\"20%\" class=\"bodytext label-align\">");
/* 3362 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.displayname"));
/* 3363 */                   out.write("<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"80%\">\n\t");
/* 3364 */                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3366 */                   out.write("\n\t</TD>\n\t</TR>\n        <TR>\n\t<TD height=\"28\" width=\"20%\" class=\"bodytext label-align\">");
/* 3367 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 3368 */                   out.write(" <span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"80%\">\n        ");
/* 3369 */                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3371 */                   out.write("\n\t</TD>\n\t</TR>\n        <TR>\n\t<TD height=\"28\" width=\"20%\" class=\"bodytext label-align\">");
/* 3372 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/* 3373 */                   out.write(" <span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"80%\">\n        ");
/* 3374 */                   if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3376 */                   out.write("\n\t</TD>\n\t</TR>\n\t<TR>\n\t<TD height=\"28\" width=\"20%\" class=\"bodytext label-align\">");
/* 3377 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.pollinginterval"));
/* 3378 */                   out.write(" <span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"80%\">\n        ");
/* 3379 */                   if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3381 */                   out.write("<span class=\"bodytext\">&nbsp;");
/* 3382 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.pollinginterval.units"));
/* 3383 */                   out.write("</span>\n\t</TD>\n\t</TR>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"20%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"80%\" class=\"tablebottom\">\n\n    <input name=\"but1\" type=\"button\"  class=\"buttons btn_highlt\" value=\"");
/*      */                   
/* 3385 */                   IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3386 */                   _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3387 */                   _jspx_th_c_005fif_005f11.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 3389 */                   _jspx_th_c_005fif_005f11.setTest("${empty enabled}");
/* 3390 */                   int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3391 */                   if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                     for (;;) {
/* 3393 */                       out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 3394 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3395 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3399 */                   if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3400 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                   }
/*      */                   
/* 3403 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3404 */                   if (_jspx_meth_c_005fif_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3406 */                   out.write("\" onClick=\"javascript:submitForm(this.form)\"/>");
/* 3407 */                   out.write(" \n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons btn_link\" value=\"");
/* 3408 */                   out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3409 */                   out.write("\" onClick=\"javascript:reconfigure()\" />\n     </td>\n  </tr>\n</table>\n");
/* 3410 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3411 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3415 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3416 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */               }
/*      */               
/* 3419 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3420 */               out.write("\n<br>\n<br>\n</div>\n\n\n<br>\n\n");
/*      */               
/* 3422 */               String selected = "";
/* 3423 */               if (datatype == 1) {
/* 3424 */                 selected = "am.webclient.as400innertabs.overview";
/*      */               }
/* 3426 */               else if (datatype == 2) {
/* 3427 */                 selected = "am.webclient.as400innertabs.Status";
/*      */               }
/* 3429 */               else if (datatype == 3) {
/* 3430 */                 selected = "am.webclient.as400innertabs.Pool";
/*      */               }
/* 3432 */               else if (datatype == 4) {
/* 3433 */                 selected = "am.webclient.as400innertabs.Jobs";
/*      */               }
/* 3435 */               else if (datatype == 5) {
/* 3436 */                 selected = "am.webclient.as400innertabs.Messages";
/*      */               }
/* 3438 */               else if (datatype == 6) {
/* 3439 */                 selected = "am.webclient.as400innertabs.Spool";
/*      */               }
/* 3441 */               else if (datatype == 7) {
/* 3442 */                 selected = "am.webclient.as400innertabs.Printer";
/*      */               }
/* 3444 */               else if (datatype == 8) {
/* 3445 */                 selected = "am.webclient.as400innertabs.Disk";
/*      */               }
/* 3447 */               else if (datatype == 9) {
/* 3448 */                 selected = "am.webclient.as400innertabs.Problem";
/*      */               }
/* 3450 */               else if (datatype == 10) {
/* 3451 */                 selected = "am.webclient.as400innertabs.Subsystem";
/*      */               }
/* 3453 */               else if (datatype == 11) {
/* 3454 */                 selected = "am.webclient.as400innertabs.HistoryLog";
/*      */               }
/* 3456 */               else if (datatype == 12) {
/* 3457 */                 selected = "am.webclient.as400innertabs.Queues";
/*      */               }
/* 3459 */               else if (datatype == 13) {
/* 3460 */                 selected = "am.webclient.as400.as400innertabs.IFS";
/*      */               }
/* 3462 */               else if (datatype == 14) {
/* 3463 */                 selected = "webclient.common.tree.adminnode.name";
/*      */               }
/*      */               
/*      */ 
/* 3467 */               out.write(10);
/* 3468 */               out.write(10);
/* 3469 */               out.write(10);
/* 3470 */               JspRuntimeLibrary.include(request, response, "/jsp/includes/CommonDetailsHeader.jsp" + ("/jsp/includes/CommonDetailsHeader.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitorname", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(displayname), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("healthId", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("2702", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("availabilityId", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("2701", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resID", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()), out, false);
/* 3471 */               out.write("\n\n\n\n\n");
/* 3472 */               JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.as400innertabs.overview,am.webclient.as400innertabs.Status,am.webclient.as400innertabs.Pool,am.webclient.as400innertabs.Jobs,am.webclient.as400innertabs.Messages,am.webclient.as400innertabs.Spool,am.webclient.as400innertabs.Printer,am.webclient.as400innertabs.Disk,am.webclient.as400innertabs.Problem,am.webclient.as400innertabs.Subsystem,am.webclient.as400innertabs.HistoryLog,am.webclient.as400innertabs.Queues,webclient.common.tree.adminnode.name", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.as400innertabs.overview,am.webclient.as400innertabs.Status,am.webclient.as400innertabs.Pool,am.webclient.as400innertabs.Jobs,am.webclient.as400innertabs.Messages,am.webclient.as400innertabs.Spool,am.webclient.as400innertabs.Printer,am.webclient.as400innertabs.Disk,am.webclient.as400innertabs.Problem,am.webclient.as400innertabs.Subsystem,am.webclient.as400innertabs.HistoryLog,am.webclient.as400innertabs.Queues,webclient.common.tree.adminnode.name", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getOverviewData,getStatusData,getPoolData,getJobsData,getMessagesData,getSpoolData,getPrinterData,getDiskData,getProblemData,getSubsystemData,getHistoryLogData,getQueueData,getAdminData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selected), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()), out, true);
/* 3473 */               out.write("\n\n<table border=\"0\" width=\"100%\">\n<tr>\n<td>\n\n<div id=\"data\">\n\n<script language=\"JavaScript\">\n\nvar datatype = ");
/* 3474 */               out.print(datatype);
/* 3475 */               out.write(";\nif (datatype == 1) {\t\t\n\tgetOverviewData(\"");
/* 3476 */               out.print(resourceid);
/* 3477 */               out.write(34);
/* 3478 */               out.write(44);
/* 3479 */               out.write(34);
/* 3480 */               out.print(type);
/* 3481 */               out.write("\");\n} else if (datatype == 2) {\n\n\tgetStatusData(\"");
/* 3482 */               out.print(resourceid);
/* 3483 */               out.write(34);
/* 3484 */               out.write(44);
/* 3485 */               out.write(34);
/* 3486 */               out.print(type);
/* 3487 */               out.write("\");\n} else if (datatype==3) {\n\n\tgetPoolData(\"");
/* 3488 */               out.print(resourceid);
/* 3489 */               out.write(34);
/* 3490 */               out.write(44);
/* 3491 */               out.write(34);
/* 3492 */               out.print(type);
/* 3493 */               out.write("\");\n} else if (datatype==4) {\n\t\n\tgetJobsData(\"");
/* 3494 */               out.print(resourceid);
/* 3495 */               out.write(34);
/* 3496 */               out.write(44);
/* 3497 */               out.write(34);
/* 3498 */               out.print(type);
/* 3499 */               out.write("\");\n} else if (datatype==5) {\n\n\tgetMessagesData(\"");
/* 3500 */               out.print(resourceid);
/* 3501 */               out.write(34);
/* 3502 */               out.write(44);
/* 3503 */               out.write(34);
/* 3504 */               out.print(type);
/* 3505 */               out.write("\");\n} else if (datatype==6) {\n\n\tgetSpoolData(\"");
/* 3506 */               out.print(resourceid);
/* 3507 */               out.write(34);
/* 3508 */               out.write(44);
/* 3509 */               out.write(34);
/* 3510 */               out.print(type);
/* 3511 */               out.write("\");\n} else if (datatype==7) {\n\t\n\tgetPrinterData(\"");
/* 3512 */               out.print(resourceid);
/* 3513 */               out.write(34);
/* 3514 */               out.write(44);
/* 3515 */               out.write(34);
/* 3516 */               out.print(type);
/* 3517 */               out.write("\");\n}else if (datatype==8) {\n\n\tgetDiskData(\"");
/* 3518 */               out.print(resourceid);
/* 3519 */               out.write(34);
/* 3520 */               out.write(44);
/* 3521 */               out.write(34);
/* 3522 */               out.print(type);
/* 3523 */               out.write("\");\n} else if (datatype==9) {\n\n\tgetProblemData(\"");
/* 3524 */               out.print(resourceid);
/* 3525 */               out.write(34);
/* 3526 */               out.write(44);
/* 3527 */               out.write(34);
/* 3528 */               out.print(type);
/* 3529 */               out.write("\");\n} else if (datatype==10) {\n\t\n\tgetSubsystemData(\"");
/* 3530 */               out.print(resourceid);
/* 3531 */               out.write(34);
/* 3532 */               out.write(44);
/* 3533 */               out.write(34);
/* 3534 */               out.print(type);
/* 3535 */               out.write("\");\n}\nelse if (datatype==11) {\n\t\n\tgetHistoryLogData(\"");
/* 3536 */               out.print(resourceid);
/* 3537 */               out.write(34);
/* 3538 */               out.write(44);
/* 3539 */               out.write(34);
/* 3540 */               out.print(type);
/* 3541 */               out.write("\");\n}\nelse if (datatype==12) {\n\t\n    getQueueData(\"");
/* 3542 */               out.print(resourceid);
/* 3543 */               out.write(34);
/* 3544 */               out.write(44);
/* 3545 */               out.write(34);
/* 3546 */               out.print(type);
/* 3547 */               out.write("\");\n}\nelse if (datatype==13) {\n\n    getIFSData(\"");
/* 3548 */               out.print(resourceid);
/* 3549 */               out.write(34);
/* 3550 */               out.write(44);
/* 3551 */               out.write(34);
/* 3552 */               out.print(type);
/* 3553 */               out.write("\");\n}\nelse if (datatype==14) {\n\n\tgetAdminData(\"");
/* 3554 */               out.print(resourceid);
/* 3555 */               out.write(34);
/* 3556 */               out.write(44);
/* 3557 */               out.write(34);
/* 3558 */               out.print(type);
/* 3559 */               out.write("\");\n}\n\n\n</script>\n \n</div>\n\n</td>\n</tr>\n</table>\n");
/* 3560 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3561 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3564 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3565 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3568 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3569 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 3572 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3573 */           out.write(10);
/* 3574 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 3576 */           out.write(10);
/* 3577 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3578 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3582 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3583 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 3586 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3587 */         out.write(10);
/* 3588 */         out.write(10);
/*      */       }
/* 3590 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3591 */         out = _jspx_out;
/* 3592 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3593 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3594 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3597 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3603 */     PageContext pageContext = _jspx_page_context;
/* 3604 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3606 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3607 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 3608 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3610 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 3612 */     _jspx_th_tiles_005fput_005f0.setValue("AS400 - Snapshot");
/* 3613 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 3614 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 3615 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3616 */       return true;
/*      */     }
/* 3618 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3619 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3624 */     PageContext pageContext = _jspx_page_context;
/* 3625 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3627 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3628 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3629 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3631 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 3632 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3633 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3635 */         out.write(10);
/* 3636 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3637 */           return true;
/* 3638 */         out.write(10);
/* 3639 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3640 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3644 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3645 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3646 */       return true;
/*      */     }
/* 3648 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3649 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3654 */     PageContext pageContext = _jspx_page_context;
/* 3655 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3657 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3658 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3659 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3661 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3663 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 3664 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3665 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3666 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3667 */       return true;
/*      */     }
/* 3669 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3670 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3675 */     PageContext pageContext = _jspx_page_context;
/* 3676 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3678 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3679 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3680 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3682 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 3683 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3684 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3686 */         out.write(10);
/* 3687 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3688 */           return true;
/* 3689 */         out.write(10);
/* 3690 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3691 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3695 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3696 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3697 */       return true;
/*      */     }
/* 3699 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3700 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3705 */     PageContext pageContext = _jspx_page_context;
/* 3706 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3708 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3709 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3710 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3712 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 3714 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 3715 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3716 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3717 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3718 */       return true;
/*      */     }
/* 3720 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3721 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3726 */     PageContext pageContext = _jspx_page_context;
/* 3727 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3729 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 3730 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3731 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3733 */     _jspx_th_c_005fset_005f0.setVar("showFooter");
/*      */     
/* 3735 */     _jspx_th_c_005fset_005f0.setValue("true");
/*      */     
/* 3737 */     _jspx_th_c_005fset_005f0.setScope("request");
/* 3738 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3739 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3740 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3741 */       return true;
/*      */     }
/* 3743 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3744 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3749 */     PageContext pageContext = _jspx_page_context;
/* 3750 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3752 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3753 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3754 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3756 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3757 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3758 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3760 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 3761 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3762 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3766 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3767 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3768 */       return true;
/*      */     }
/* 3770 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3771 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3776 */     PageContext pageContext = _jspx_page_context;
/* 3777 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3779 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3780 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3781 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3783 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3784 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3785 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3787 */         out.write("\n        alertUser();\n        return;\n        ");
/* 3788 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3789 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3793 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3794 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3795 */       return true;
/*      */     }
/* 3797 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3798 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3803 */     PageContext pageContext = _jspx_page_context;
/* 3804 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3806 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3807 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3808 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3810 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.as400.selatleastone");
/* 3811 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3812 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3813 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3814 */       return true;
/*      */     }
/* 3816 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3817 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3822 */     PageContext pageContext = _jspx_page_context;
/* 3823 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3825 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3826 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3827 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3829 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.as400.remove.job.alert");
/* 3830 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3831 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3832 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3833 */       return true;
/*      */     }
/* 3835 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3836 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3841 */     PageContext pageContext = _jspx_page_context;
/* 3842 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3844 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3845 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 3846 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3848 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.as400.remove.subsystem.alert");
/* 3849 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 3850 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3851 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3852 */       return true;
/*      */     }
/* 3854 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3855 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3860 */     PageContext pageContext = _jspx_page_context;
/* 3861 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3863 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3864 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 3865 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3867 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.as400.remove.queue.alert");
/* 3868 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 3869 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3870 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3871 */       return true;
/*      */     }
/* 3873 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3874 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3879 */     PageContext pageContext = _jspx_page_context;
/* 3880 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3882 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3883 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3884 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3886 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.as400.ruswantto");
/* 3887 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3888 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3889 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3890 */       return true;
/*      */     }
/* 3892 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3893 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3898 */     PageContext pageContext = _jspx_page_context;
/* 3899 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3901 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3902 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3903 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3905 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.as400.selatleastone");
/* 3906 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3907 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3908 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3909 */       return true;
/*      */     }
/* 3911 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3912 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3917 */     PageContext pageContext = _jspx_page_context;
/* 3918 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3920 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3921 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3922 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3924 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3925 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3926 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 3928 */         out.write("\n        alertUser();\n        return;\n        ");
/* 3929 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3930 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3934 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3935 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3936 */       return true;
/*      */     }
/* 3938 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3939 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3944 */     PageContext pageContext = _jspx_page_context;
/* 3945 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3947 */     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3948 */     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3949 */     _jspx_th_logic_005fpresent_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3951 */     _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/* 3952 */     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3953 */     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */       for (;;) {
/* 3955 */         out.write("\n        alertUser();\n        return;\n        ");
/* 3956 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3957 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3961 */     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3962 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3963 */       return true;
/*      */     }
/* 3965 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3966 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_logic_005fnotPresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3971 */     PageContext pageContext = _jspx_page_context;
/* 3972 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3974 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3975 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3976 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f2);
/*      */     
/* 3978 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 3979 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3980 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3981 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3982 */       return true;
/*      */     }
/* 3984 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3985 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3990 */     PageContext pageContext = _jspx_page_context;
/* 3991 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3993 */     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3994 */     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3995 */     _jspx_th_logic_005fpresent_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3997 */     _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 3998 */     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3999 */     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */       for (;;) {
/* 4001 */         out.write("\n        alertUser();\n        return;\n        ");
/* 4002 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 4003 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4007 */     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 4008 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 4009 */       return true;
/*      */     }
/* 4011 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 4012 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4017 */     PageContext pageContext = _jspx_page_context;
/* 4018 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4020 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4021 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4022 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 4024 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 4025 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4026 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4027 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4028 */       return true;
/*      */     }
/* 4030 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4031 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4036 */     PageContext pageContext = _jspx_page_context;
/* 4037 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4039 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4040 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4041 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 4043 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 4044 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4045 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4046 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4047 */       return true;
/*      */     }
/* 4049 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4055 */     PageContext pageContext = _jspx_page_context;
/* 4056 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4058 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4059 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 4060 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 4062 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.webclient.userauthorization.unaunthorised");
/* 4063 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 4064 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 4065 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 4066 */       return true;
/*      */     }
/* 4068 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 4069 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4074 */     PageContext pageContext = _jspx_page_context;
/* 4075 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4077 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4078 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 4079 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4081 */     _jspx_th_fmt_005fmessage_005f7.setKey("am.webclient.as400.joblog.view");
/* 4082 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 4083 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 4084 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 4085 */       return true;
/*      */     }
/* 4087 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 4088 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_logic_005fnotPresent_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4093 */     PageContext pageContext = _jspx_page_context;
/* 4094 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4096 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4097 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 4098 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_logic_005fnotPresent_005f5);
/*      */     
/* 4100 */     _jspx_th_fmt_005fmessage_005f8.setKey("am.webclient.userauthorization.unaunthorised");
/* 4101 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 4102 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 4103 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 4104 */       return true;
/*      */     }
/* 4106 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 4107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4112 */     PageContext pageContext = _jspx_page_context;
/* 4113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4115 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4116 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 4117 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4119 */     _jspx_th_fmt_005fmessage_005f9.setKey("am.webclient.as400.spool.view");
/* 4120 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 4121 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 4122 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 4123 */       return true;
/*      */     }
/* 4125 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 4126 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4131 */     PageContext pageContext = _jspx_page_context;
/* 4132 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4134 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4135 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 4136 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4138 */     _jspx_th_fmt_005fmessage_005f10.setKey("am.mypage.actions.button.text");
/* 4139 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 4140 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 4141 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 4142 */       return true;
/*      */     }
/* 4144 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 4145 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f6(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4150 */     PageContext pageContext = _jspx_page_context;
/* 4151 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4153 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4154 */     _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 4155 */     _jspx_th_logic_005fnotPresent_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 4157 */     _jspx_th_logic_005fnotPresent_005f6.setRole("DEMO");
/* 4158 */     int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 4159 */     if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */       for (;;) {
/* 4161 */         out.write("\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 4162 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_logic_005fnotPresent_005f6, _jspx_page_context))
/* 4163 */           return true;
/* 4164 */         out.write(34);
/* 4165 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 4166 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4170 */     if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 4171 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 4172 */       return true;
/*      */     }
/* 4174 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 4175 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_logic_005fnotPresent_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4180 */     PageContext pageContext = _jspx_page_context;
/* 4181 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4183 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4184 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4185 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_logic_005fnotPresent_005f6);
/*      */     
/* 4187 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 4188 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4189 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4190 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4191 */       return true;
/*      */     }
/* 4193 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4194 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f5(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4199 */     PageContext pageContext = _jspx_page_context;
/* 4200 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4202 */     PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4203 */     _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 4204 */     _jspx_th_logic_005fpresent_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 4206 */     _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 4207 */     int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 4208 */     if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */       for (;;) {
/* 4210 */         out.write("\"javascript:alertUser()\"");
/* 4211 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 4212 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4216 */     if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 4217 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 4218 */       return true;
/*      */     }
/* 4220 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 4221 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f7(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4226 */     PageContext pageContext = _jspx_page_context;
/* 4227 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4229 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4230 */     _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 4231 */     _jspx_th_logic_005fnotPresent_005f7.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 4233 */     _jspx_th_logic_005fnotPresent_005f7.setRole("DEMO");
/* 4234 */     int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 4235 */     if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */       for (;;) {
/* 4237 */         out.write("\"fnOpenNewWindow('/jsp/FaultTemplateOptions.jsp?&resourceid=");
/* 4238 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_logic_005fnotPresent_005f7, _jspx_page_context))
/* 4239 */           return true;
/* 4240 */         out.write("&resourcename=");
/* 4241 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_logic_005fnotPresent_005f7, _jspx_page_context))
/* 4242 */           return true;
/* 4243 */         out.write("&resourcetype=AS400/iSeries&resourcetypedisplayname=AS400/iSeries')\"");
/* 4244 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 4245 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4249 */     if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 4250 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 4251 */       return true;
/*      */     }
/* 4253 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 4254 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_logic_005fnotPresent_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4259 */     PageContext pageContext = _jspx_page_context;
/* 4260 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4262 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4263 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4264 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_logic_005fnotPresent_005f7);
/*      */     
/* 4266 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 4267 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4268 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4269 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4270 */       return true;
/*      */     }
/* 4272 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4273 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_logic_005fnotPresent_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4278 */     PageContext pageContext = _jspx_page_context;
/* 4279 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4281 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4282 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4283 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_logic_005fnotPresent_005f7);
/*      */     
/* 4285 */     _jspx_th_c_005fout_005f5.setValue("${param.name}");
/* 4286 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4287 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4288 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4289 */       return true;
/*      */     }
/* 4291 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4292 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f7(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4297 */     PageContext pageContext = _jspx_page_context;
/* 4298 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4300 */     PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4301 */     _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 4302 */     _jspx_th_logic_005fpresent_005f7.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 4304 */     _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 4305 */     int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 4306 */     if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */       for (;;) {
/* 4308 */         out.write("\"javascript:alertUser()\"");
/* 4309 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 4310 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4314 */     if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 4315 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 4316 */       return true;
/*      */     }
/* 4318 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 4319 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4324 */     PageContext pageContext = _jspx_page_context;
/* 4325 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4327 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4328 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 4329 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 4331 */     _jspx_th_fmt_005fmessage_005f11.setKey("am.webclient.common.alerttemplate.text");
/* 4332 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 4333 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 4334 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 4335 */       return true;
/*      */     }
/* 4337 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 4338 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f8(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4343 */     PageContext pageContext = _jspx_page_context;
/* 4344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4346 */     PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4347 */     _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 4348 */     _jspx_th_logic_005fpresent_005f8.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 4350 */     _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 4351 */     int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 4352 */     if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */       for (;;) {
/* 4354 */         out.write("\"javascript:alertUser()\"");
/* 4355 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 4356 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4360 */     if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 4361 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4362 */       return true;
/*      */     }
/* 4364 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4365 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4370 */     PageContext pageContext = _jspx_page_context;
/* 4371 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4373 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4374 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 4375 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4377 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 4379 */     _jspx_th_html_005ftext_005f0.setSize("25");
/*      */     
/* 4381 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/* 4382 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 4383 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 4384 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4385 */       return true;
/*      */     }
/* 4387 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4393 */     PageContext pageContext = _jspx_page_context;
/* 4394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4396 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4397 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 4398 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4400 */     _jspx_th_html_005ftext_005f1.setProperty("username");
/*      */     
/* 4402 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext normal");
/*      */     
/* 4404 */     _jspx_th_html_005ftext_005f1.setSize("15");
/* 4405 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 4406 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 4407 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4408 */       return true;
/*      */     }
/* 4410 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4411 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4416 */     PageContext pageContext = _jspx_page_context;
/* 4417 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4419 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 4420 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 4421 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4423 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 4425 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext normal");
/*      */     
/* 4427 */     _jspx_th_html_005fpassword_005f0.setSize("15");
/* 4428 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 4429 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 4430 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 4431 */       return true;
/*      */     }
/* 4433 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 4434 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4439 */     PageContext pageContext = _jspx_page_context;
/* 4440 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4442 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4443 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 4444 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4446 */     _jspx_th_html_005ftext_005f2.setProperty("pollInterval");
/*      */     
/* 4448 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext small");
/*      */     
/* 4450 */     _jspx_th_html_005ftext_005f2.setSize("15");
/* 4451 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 4452 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 4453 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4454 */       return true;
/*      */     }
/* 4456 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4457 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4462 */     PageContext pageContext = _jspx_page_context;
/* 4463 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4465 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4466 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 4467 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4469 */     _jspx_th_c_005fif_005f12.setTest("${!empty enabled}");
/* 4470 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 4471 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 4473 */         out.write("Edit Monitor");
/* 4474 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 4475 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4479 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 4480 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4481 */       return true;
/*      */     }
/* 4483 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4484 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4489 */     PageContext pageContext = _jspx_page_context;
/* 4490 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4492 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4493 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 4494 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4496 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 4498 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 4499 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 4500 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4501 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4502 */       return true;
/*      */     }
/* 4504 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4505 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AS400Details_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */