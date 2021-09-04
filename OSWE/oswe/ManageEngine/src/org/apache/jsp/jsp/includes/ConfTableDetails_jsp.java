/*      */ package org.apache.jsp.jsp.includes;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
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
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.EqualTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotEqualTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ 
/*      */ public final class ConfTableDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  814 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  815 */     getRCATrimmedText(div1, rca);
/*  816 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  819 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  820 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  926 */     AMLog.debug("JSP : " + debugMessage);
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
/*  973 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/* 1474 */       message = EnterpriseUtil.decodeString(message);
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
/* 1824 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1826 */               if (maxCol != null)
/* 1827 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1829 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1824 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
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
/* 1985 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
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
/* 2185 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2186 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2203 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2207 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2217 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2221 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2222 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.release();
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2224 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.release();
/* 2225 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.release();
/* 2226 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2227 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.release();
/* 2228 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody.release();
/* 2229 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2236 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2239 */     JspWriter out = null;
/* 2240 */     Object page = this;
/* 2241 */     JspWriter _jspx_out = null;
/* 2242 */     PageContext _jspx_page_context = null;
/*      */     
/* 2244 */     Object _jspx_attList_1 = null;
/* 2245 */     Integer _jspx_j_1 = null;
/*      */     try
/*      */     {
/* 2248 */       response.setContentType("text/html;charset=UTF-8");
/* 2249 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2251 */       _jspx_page_context = pageContext;
/* 2252 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2253 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2254 */       session = pageContext.getSession();
/* 2255 */       out = pageContext.getOut();
/* 2256 */       _jspx_out = out;
/*      */       
/* 2258 */       out.write("<!--$Id$-->\n  \n\n\n\n\n\n\n\n\n\n");
/* 2259 */       Hashtable availabilitykeys = null;
/* 2260 */       synchronized (application) {
/* 2261 */         availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2262 */         if (availabilitykeys == null) {
/* 2263 */           availabilitykeys = new Hashtable();
/* 2264 */           _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */         }
/*      */       }
/* 2267 */       out.write(10);
/* 2268 */       Hashtable healthkeys = null;
/* 2269 */       synchronized (application) {
/* 2270 */         healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2271 */         if (healthkeys == null) {
/* 2272 */           healthkeys = new Hashtable();
/* 2273 */           _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */         }
/*      */       }
/* 2276 */       out.write(10);
/* 2277 */       out.write(10);
/* 2278 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2280 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2281 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2282 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2284 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2286 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2288 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2290 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2291 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2292 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2293 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2296 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2297 */         String available = null;
/* 2298 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2299 */         out.write(10);
/*      */         
/* 2301 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2302 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2303 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2305 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2307 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2309 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2311 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2312 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2313 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2314 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2317 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2318 */           String unavailable = null;
/* 2319 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2320 */           out.write(10);
/*      */           
/* 2322 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2323 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2324 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2326 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2328 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2330 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2332 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2333 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2334 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2335 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2338 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2339 */             String unmanaged = null;
/* 2340 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2341 */             out.write(10);
/*      */             
/* 2343 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2344 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2345 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2347 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2349 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2351 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2353 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2354 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2355 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2356 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2359 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2360 */               String scheduled = null;
/* 2361 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2362 */               out.write(10);
/*      */               
/* 2364 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2365 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2366 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2368 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2370 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2372 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2374 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2375 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2376 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2377 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2380 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2381 */                 String critical = null;
/* 2382 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2383 */                 out.write(10);
/*      */                 
/* 2385 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2386 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2387 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2389 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2391 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2393 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2395 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2396 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2397 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2398 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2401 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2402 */                   String clear = null;
/* 2403 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2404 */                   out.write(10);
/*      */                   
/* 2406 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2407 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2408 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2410 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2412 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2414 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2416 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2417 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2418 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2419 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2422 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2423 */                     String warning = null;
/* 2424 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2425 */                     out.write(10);
/* 2426 */                     out.write(10);
/*      */                     
/* 2428 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2429 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2431 */                     out.write(10);
/* 2432 */                     out.write(10);
/* 2433 */                     out.write(10);
/* 2434 */                     out.write(10);
/*      */                     
/*      */ 
/* 2437 */                     String resourcetype = (String)request.getAttribute("resourceType");
/* 2438 */                     String tableId = (String)request.getAttribute("tableId");
/*      */                     
/* 2440 */                     String tableDisName = (String)request.getAttribute("tableDisName");
/* 2441 */                     String resID = (String)request.getAttribute("resID");
/* 2442 */                     String columnType = "";
/* 2443 */                     String managedObjectName = "";
/* 2444 */                     boolean showAvailability = false;
/* 2445 */                     boolean isreplacable = false;
/* 2446 */                     boolean isNumericAttributesAvailable = ((Boolean)request.getAttribute("isNumericAttributesAvailable")).booleanValue();
/*      */                     
/* 2448 */                     String userRole = "";
/* 2449 */                     String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resID);
/* 2450 */                     if (request.isUserInRole("ADMIN")) {
/* 2451 */                       userRole = "ADMIN";
/*      */                     }
/* 2453 */                     else if (request.isUserInRole("OPERATOR")) {
/* 2454 */                       userRole = "OPERATOR";
/*      */                     }
/* 2456 */                     else if (request.isUserInRole("USER")) {
/* 2457 */                       userRole = "USER";
/*      */                     }
/* 2459 */                     else if (request.isUserInRole("USERS")) {
/* 2460 */                       userRole = "USERS";
/*      */                     }
/* 2462 */                     else if (request.isUserInRole("ENTERPRISEADMIN")) {
/* 2463 */                       userRole = "ENTERPRISEADMIN";
/*      */                     }
/*      */                     
/* 2466 */                     HashMap actionmap = ConfMonitorConfiguration.getInstance().getActionDetails(resourcetype, userRole);
/* 2467 */                     request.setAttribute("actionMap", actionmap);
/*      */                     
/*      */ 
/* 2470 */                     boolean isQMExTimeTable = false;
/* 2471 */                     int num_count = 0;
/* 2472 */                     int colspan = 0;
/* 2473 */                     HashMap groupDetails = ConfMonitorConfiguration.getInstance().getUITableDetails(resourcetype + "_" + tableDisName);
/* 2474 */                     ArrayList configAttributesList = (ArrayList)groupDetails.get("Tab-" + tableDisName + "-CONFIG");
/* 2475 */                     String tableName = (String)groupDetails.get("NAME");
/* 2476 */                     String childType = groupDetails.get("CHILD-TYPE") != null ? (String)groupDetails.get("CHILD-TYPE") : "";
/* 2477 */                     String monType = !childType.trim().equals("") ? childType : resourcetype;
/* 2478 */                     boolean createMosForRows = ConfMonitorConfiguration.getInstance().createNewMOForRows(monType, tableName);
/* 2479 */                     ArrayList attributesList = (ArrayList)groupDetails.get("COLUMNLIST");
/* 2480 */                     pageContext.setAttribute("attributesList", attributesList);
/* 2481 */                     ArrayList graphList = (ArrayList)groupDetails.get("GRAPHLIST");
/* 2482 */                     pageContext.setAttribute("graphList", graphList);
/* 2483 */                     ArrayList tActionList = (ArrayList)groupDetails.get("TABLE-ACTION");
/* 2484 */                     int addColCount = 0;
/* 2485 */                     String primaryKey = groupDetails.get("PRIMARY-COLUMNID") != null ? (String)groupDetails.get("PRIMARY-COLUMNID") : "PrimaryColumn";
/* 2486 */                     int graphColumnCount = groupDetails.get("GRAPH-COLUMN-COUNT") != null ? ((Integer)groupDetails.get("GRAPH-COLUMN-COUNT")).intValue() : 0;
/* 2487 */                     HashMap attributeDetails = new HashMap();
/* 2488 */                     String att_to_select = "";
/* 2489 */                     HashMap tableValuesold = (HashMap)request.getAttribute("valueMap");
/* 2490 */                     TreeMap tableValues = new TreeMap();
/* 2491 */                     tableValues.putAll(tableValuesold);
/* 2492 */                     if (tableValues.get("ORDERBY-VALUES") != null) {
/* 2493 */                       LinkedHashMap orderedValues = (LinkedHashMap)tableValues.get("ORDERBY-VALUES");
/* 2494 */                       pageContext.setAttribute("rowIds", orderedValues);
/*      */                     }
/*      */                     else {
/* 2497 */                       pageContext.setAttribute("rowIds", tableValues);
/*      */                     }
/* 2499 */                     boolean showRowStatus = ((Boolean)request.getAttribute("showRowStatus")).booleanValue();
/* 2500 */                     boolean isthreshold = ((String)groupDetails.get("DISPLAY-THRESHOLD")).equals("YES");
/* 2501 */                     boolean ishealth = (((String)groupDetails.get("DISPLAY-HEALTH")).equals("YES")) && (showRowStatus);
/* 2502 */                     boolean isActions = ((String)groupDetails.get("DISPLAY-ACTIONS")).equals("YES");
/* 2503 */                     boolean showAllLink = (((String)groupDetails.get("SHOW-ALL-LINK")).equals("YES")) && (request.getAttribute("OpenInNewWindow") == null);
/* 2504 */                     pageContext.setAttribute("OpenInNewWindow", request.getAttribute("OpenInNewWindow") != null ? "true" : "false");
/* 2505 */                     if ((isthreshold) && (createMosForRows)) {
/* 2506 */                       addColCount += 1;
/*      */                     }
/* 2508 */                     if (ishealth) {
/* 2509 */                       addColCount += 1;
/*      */                     }
/* 2511 */                     if ((((Boolean)groupDetails.get("SHOW-AVIALBILITY")).booleanValue()) && (!showRowStatus)) {
/* 2512 */                       addColCount -= 1;
/*      */                     }
/* 2514 */                     int totNoOfCols = attributesList.size() + addColCount;
/* 2515 */                     float width = 98.0F / totNoOfCols - graphColumnCount * 5.0F;
/* 2516 */                     String fname = "forms" + tableId + "_" + resID;
/* 2517 */                     String formid = "fid" + tableId + "_" + resID;
/* 2518 */                     String checkname = "resrows" + tableId;
/* 2519 */                     String checkid = "checkID" + tableId;
/* 2520 */                     String colavaid = createMosForRows == true ? (String)availabilitykeys.get(childType) : childType.trim().equals("") ? (String)groupDetails.get("AVAILABILITYATTRIBUTEID") : "";
/* 2521 */                     String table_healthid = createMosForRows == true ? (String)healthkeys.get(childType) : childType.trim().equals("") ? (String)groupDetails.get("HEALTHATTRIBUTEID") : "";
/* 2522 */                     ArrayList attribIDs = new ArrayList();
/* 2523 */                     attribIDs.add("" + table_healthid);
/* 2524 */                     attribIDs.add("" + colavaid);
/* 2525 */                     ArrayList resIDs = new ArrayList();
/* 2526 */                     resIDs.add(resID);
/* 2527 */                     HashMap colVsactionsMap = new HashMap();
/* 2528 */                     Properties commonValues = new Properties();
/* 2529 */                     boolean showColumn = true;
/* 2530 */                     String uiElement = groupDetails.get("Display") != null ? (String)groupDetails.get("Display") : groupDetails.get("CAPTION") != null ? (String)groupDetails.get("CAPTION") : (String)groupDetails.get("ID");
/* 2531 */                     String noDataMsg = "No_Data_Available";
/* 2532 */                     ArrayList uIElementsToRemove = (ArrayList)request.getAttribute("RemoveUIElements");
/* 2533 */                     boolean isDCDisabled = false;boolean showMessageOnConditionFailure = false;
/* 2534 */                     if ((uIElementsToRemove != null) && (uIElementsToRemove.contains("UITABLE_" + tableDisName))) {
/* 2535 */                       isDCDisabled = true;
/* 2536 */                       showMessageOnConditionFailure = (groupDetails.containsKey("Show-MsgOn-Failure")) && ("YES".equals((String)groupDetails.get("Show-MsgOn-Failure")));
/* 2537 */                       noDataMsg = groupDetails.get("NO-DATA-MSG") != null ? FormatUtil.getString((String)groupDetails.get("NO-DATA-MSG")) : FormatUtil.getString("am.webclient.conf.datacollection.disabled.msg");
/*      */                     }
/*      */                     
/* 2540 */                     out.write(10);
/* 2541 */                     out.print(getCustomMessage(resourcetype, (String)groupDetails.get("Message-Id"), "TABLE", uIElementsToRemove));
/* 2542 */                     out.write("<!-- No i18n -->\n");
/*      */                     
/* 2544 */                     EqualTag _jspx_th_logic_005fequal_005f0 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.get(EqualTag.class);
/* 2545 */                     _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
/* 2546 */                     _jspx_th_logic_005fequal_005f0.setParent(null);
/*      */                     
/* 2548 */                     _jspx_th_logic_005fequal_005f0.setName("OpenInNewWindow");
/*      */                     
/* 2550 */                     _jspx_th_logic_005fequal_005f0.setValue("true");
/* 2551 */                     int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
/* 2552 */                     if (_jspx_eval_logic_005fequal_005f0 != 0) {
/*      */                       for (;;) {
/* 2554 */                         out.write("\n    <SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/confMonitor.js\"></SCRIPT>\n    <SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n    ");
/* 2555 */                         out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2556 */                         out.write("\n   <link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n  <link href=\"/images/");
/* 2557 */                         if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fequal_005f0, _jspx_page_context))
/*      */                           return;
/* 2559 */                         out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n  <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\" height=\"55\">\n      <tbody><tr> <td><span class=\"headingboldwhite\">");
/* 2560 */                         out.print(FormatUtil.getString(groupDetails.get("DISPLAY") != null ? (String)groupDetails.get("DISPLAY") : tableName));
/* 2561 */                         out.write("</span>\n                <span class=\"headingwhite\"> </span> </td></tr>\n   </tbody>\n  </table>\n  <br>\n\n");
/* 2562 */                         int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
/* 2563 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2567 */                     if (_jspx_th_logic_005fequal_005f0.doEndTag() == 5) {
/* 2568 */                       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/*      */                     }
/*      */                     else {
/* 2571 */                       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 2572 */                       out.write("\n\n    <form name=\"");
/* 2573 */                       out.print(fname);
/* 2574 */                       out.write("\" id=\"");
/* 2575 */                       out.print(formid);
/* 2576 */                       out.write("\" method=post class=\"confForm\">\n        <input type=\"hidden\" name=\"method\" value=\"deleteTableByUser\"/>\n        <input type=\"hidden\" name=\"resourceid\" value=\"\"/>\n        <input type=\"hidden\" name=\"isActions\" value=\"");
/* 2577 */                       out.print(isActions);
/* 2578 */                       out.write("\"/>\n        <input type=\"hidden\" name=\"noOfRows\" value=\"");
/* 2579 */                       out.print(tableValues != null ? Integer.valueOf(tableValues.size()) : "0");
/* 2580 */                       out.write("\"/>        \n        <input type=\"hidden\" name=\"isPopUpPage\" id=\"isPopUpPage\" value=\"");
/* 2581 */                       out.print(request.getAttribute("OpenInNewWindow") != null ? "true" : "false");
/* 2582 */                       out.write("\"/>\n        <input type=\"hidden\" id=\"am.webclient.scriptrow.selectone.disable\" name=\"am.webclient.scriptrow.selectone.disable\" value='");
/* 2583 */                       out.print(FormatUtil.getString("am.webclient.scriptrow.selectone.disable"));
/* 2584 */                       out.write("'>\n        <input type=\"hidden\" id=\"am.webclient.scriptrow.disable.confirm\" name=\"am.webclient.scriptrow.disable.confirm\" value='");
/* 2585 */                       out.print(FormatUtil.getString("am.webclient.scriptrow.disable.confirm"));
/* 2586 */                       out.write("'>\n        <input type=\"hidden\" id=\"am.webclient.scriptrow.enable.confirm\" name=\"am.webclient.scriptrow.enable.confirm\" value='");
/* 2587 */                       out.print(FormatUtil.getString("am.webclient.scriptrow.enable.confirm"));
/* 2588 */                       out.write("'>\n        <input type=\"hidden\" id=\"am.webclient.scriptrow.selectone.delete\" name=\"am.webclient.scriptrow.selectone.delete\" value='");
/* 2589 */                       out.print(FormatUtil.getString("am.webclient.scriptrow.selectone.delete"));
/* 2590 */                       out.write("'>\n        <input type=\"hidden\" id=\"am.webclient.scriptrow.delete.confirm\" name=\"am.webclient.scriptrow.delete.confirm\" value='");
/* 2591 */                       out.print(FormatUtil.getString("am.webclient.scriptrow.delete.confirm"));
/* 2592 */                       out.write("'>\n        <input type=\"hidden\" id=\"am.webclient.vmware.jsalertfordeletevm.text\" name=\"am.webclient.vmware.jsalertfordeletevm.text\" value='");
/* 2593 */                       out.print(FormatUtil.getString("am.webclient.vmware.jsalertfordeletevm.text"));
/* 2594 */                       out.write("'>\n        <input type=\"hidden\" id=\"am.webclient.ConfTable.actionReqOut\" name=\"am.webclient.ConfTable.actionReqOut\" value='");
/* 2595 */                       out.print(FormatUtil.getString("am.webclient.ConfTable.actionReqOut"));
/* 2596 */                       out.write("'>\n        <input type=\"hidden\" id=\"am.webclient.scriptrow.delete.confirm.mo\" name=\"am.webclient.scriptrow.delete.confirm.mo\" value='");
/* 2597 */                       out.print(FormatUtil.getString("am.webclient.scriptrow.delete.confirm.mo", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") }));
/* 2598 */                       out.write("'>\n        <div id=\"loading");
/* 2599 */                       out.print(tableId);
/* 2600 */                       out.write("\" style=\"display:none;\">\n          <table id=\"bFocus\" width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n            <tr>\n              <td>\n                &nbsp;&nbsp;<img alt=\"Loading...\" src=\"/images/icon_cogwheel.gif\" border=\"0\"/>\n                <span class=\"bodytextbold\">\n                  ");
/* 2601 */                       out.print(FormatUtil.getString("am.webclient.ConfTable.executeActionMsg"));
/* 2602 */                       out.write("\n                </span>\n              </td>\n            </tr>\n            <tr>\n              <td>\n                <img src=\"../images/spacer.gif\" width=\"10\" height=\"12\">\n              </td>\n            </tr>\n          </table>\n        </div>\n        <div id=\"actionMessage");
/* 2603 */                       out.print(tableId);
/* 2604 */                       out.write("\" style=\"display:none;\">\n        <table width=\"100%\">\n          <tr>\n            <td height=\"24\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n              <table width='99%' border='0' cellspacing='2' cellpadding='2' class='messagebox'>\n                <tr>\n                  <td width='5%' align='center'><span id=\"actionStatus\"></span></td>\n                  <td width='95%' height='28' class='message'><span id=\"actionMessage\"></span>\n                    <span id=\"reloadLink\" ><a href=\"javascript:reloadTable()\" class=\"staticlinks\">");
/* 2605 */                       out.print(FormatUtil.getString("am.webclient.ConfTable.refreshClick"));
/* 2606 */                       out.write("</a></span>\n                  </td>\n                </tr>\n              </table>\n\n            </td>\n          </tr>\n\n        </table><br>\n\n        </div>\n        \n        \n        \n        \n\n");
/* 2607 */                       if (_jspx_meth_logic_005fequal_005f1(_jspx_page_context))
/*      */                         return;
/* 2609 */                       out.write(10);
/* 2610 */                       out.write(10);
/*      */                       
/* 2612 */                       NotEqualTag _jspx_th_logic_005fnotEqual_005f0 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.get(NotEqualTag.class);
/* 2613 */                       _jspx_th_logic_005fnotEqual_005f0.setPageContext(_jspx_page_context);
/* 2614 */                       _jspx_th_logic_005fnotEqual_005f0.setParent(null);
/*      */                       
/* 2616 */                       _jspx_th_logic_005fnotEqual_005f0.setName("OpenInNewWindow");
/*      */                       
/* 2618 */                       _jspx_th_logic_005fnotEqual_005f0.setValue("true");
/* 2619 */                       int _jspx_eval_logic_005fnotEqual_005f0 = _jspx_th_logic_005fnotEqual_005f0.doStartTag();
/* 2620 */                       if (_jspx_eval_logic_005fnotEqual_005f0 != 0) {
/*      */                         for (;;) {
/* 2622 */                           out.write(10);
/* 2623 */                           if ((!isDCDisabled) || (showMessageOnConditionFailure)) {
/* 2624 */                             out.write("\n<div class=\"apmconf-table-frame\">\n  <div id=\"apmconf-tld-nav conf-mon-txt\"  style=\"height:25px; margin-left:10px;\">\n  <table cellpadding=\"0\" cellspacing=\"0\"  width=\"100%\"><tr><td>\n  <div style=\"display:inline;float:left\">\n  ");
/* 2625 */                             if ((groupDetails.get("TOOLTIP") != null) && (!((String)groupDetails.get("TOOLTIP")).equals(""))) {
/* 2626 */                               out.write("\n       <a style=\"cursor:pointer\" class=\"conf-mon-txt\" onMouseOver=\"ddrivetip(this,event,'");
/* 2627 */                               out.print(FormatUtil.getString((String)groupDetails.get("TOOLTIP")));
/* 2628 */                               out.write("',false,true,'#000000',150,'lightyellow')\" onmouseout=\"hideddrivetip()\">\n    ");
/* 2629 */                               out.print(FormatUtil.getString(groupDetails.get("DISPLAY") != null ? (String)groupDetails.get("DISPLAY") : tableName));
/* 2630 */                               out.write("</a>\n  ");
/*      */                             } else {
/* 2632 */                               out.write("<span class=\"conf-mon-txt\"> ");
/* 2633 */                               out.print(FormatUtil.getString(groupDetails.get("DISPLAY") != null ? (String)groupDetails.get("DISPLAY") : tableName));
/* 2634 */                               out.write("</a>");
/*      */                             }
/* 2636 */                             out.write("</span>\n  </div>\n  </td>\n  ");
/* 2637 */                             if ((!showAllLink) && ((createMosForRows) || (!isNumericAttributesAvailable)) && (!groupDetails.containsKey("LINK"))) {
/* 2638 */                               out.write("\n    <td><img src=\"../images/spacer.gif\" width=\"10\" height=\"12\"></td>\n  ");
/*      */                             } else {
/* 2640 */                               out.write("\n      <td  align=\"right\" style=\"padding-right:10px\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr>\n    ");
/* 2641 */                               if (showAllLink) {
/* 2642 */                                 out.write("  \n      <td  align=\"right\" style=\"padding-right:10px\"><a class=\"bodytextboldwhiteun\" style='cursor:pointer' onClick=\"javascript:showAllRows('");
/* 2643 */                                 out.print(tableId);
/* 2644 */                                 out.write(39);
/* 2645 */                                 out.write(44);
/* 2646 */                                 out.write(39);
/* 2647 */                                 out.print(FormatUtil.getString(groupDetails.get("DISPLAY") != null ? (String)groupDetails.get("DISPLAY") : tableName));
/* 2648 */                                 out.write("')\">");
/* 2649 */                                 out.print(FormatUtil.getString("am.webclient.common.bulkconfigview.showall.text"));
/* 2650 */                                 out.write("</a></td>\n    "); }
/* 2651 */                               if (groupDetails.containsKey("LINK")) {
/* 2652 */                                 out.write("\n    \t ");
/* 2653 */                                 out.print(getTableActionsList((ArrayList)groupDetails.get("LINK"), actionmap, tableName, commonValues, resID, resourcetype));
/* 2654 */                                 out.write("    \t\n    "); }
/* 2655 */                               if ((!createMosForRows) && (isthreshold) && (isNumericAttributesAvailable) && (!((String)groupDetails.get("THRESHOLD-ATTRIBUTES")).trim().equals(""))) {
/* 2656 */                                 out.write(" \n      <td  align=\"right\" style=\"padding-right:10px\"><img src=\"../images/icon_associateaction.gif\" hspace=\"4\" border=\"0\" align=\"absmiddle\"><a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2657 */                                 out.print(resID);
/* 2658 */                                 out.write("&attributeIDs=");
/* 2659 */                                 out.print((String)groupDetails.get("THRESHOLD-ATTRIBUTES"));
/* 2660 */                                 out.write("&attributeToSelect=");
/* 2661 */                                 out.print((String)groupDetails.get("FIRST-ATTRIBUTE-TO-SELECT"));
/* 2662 */                                 out.write("&redirectto=");
/* 2663 */                                 out.print(encodeurl);
/* 2664 */                                 out.write("' class=\"staticlinks\">");
/* 2665 */                                 out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 2666 */                                 out.write("</a></td>\n     ");
/*      */                               }
/* 2668 */                               out.write("</tr></table></td>");
/*      */                             }
/* 2670 */                             out.write("  \n  </tr>\n  </table>\n  </div>\n  \n  ");
/*      */                           }
/* 2672 */                           out.write(10);
/* 2673 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f0.doAfterBody();
/* 2674 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2678 */                       if (_jspx_th_logic_005fnotEqual_005f0.doEndTag() == 5) {
/* 2679 */                         this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
/*      */                       }
/*      */                       else {
/* 2682 */                         this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
/* 2683 */                         out.write(" \n\n\n  <div id=\"");
/* 2684 */                         out.print(tableId);
/* 2685 */                         out.write("\" style=\"overflow:auto;\">        \n\n    \n");
/* 2686 */                         if ((tableValues.get("IsRows") != null) && (((Boolean)tableValues.get("IsRows")).booleanValue()) && (!isDCDisabled)) {
/* 2687 */                           out.write("\n  <table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-data-table\" width=\"100%\" id=\"");
/* 2688 */                           out.print(tableId);
/* 2689 */                           out.write("Sort\">\n        <tr>\n        ");
/* 2690 */                           if ((createMosForRows) && ((isNumericAttributesAvailable) || (isActions))) {
/* 2691 */                             out.write("\n            <td height=\"26\" align=\"center\" width=\"2%\" class=\"whitegrayborder-conf-mon\"><input type=\"checkbox\"  name=\"headercheckbox\" onclick=\"javascript:fnSelectAllform(this,this.form,'");
/* 2692 */                             out.print(checkname);
/* 2693 */                             out.write("')\" /></td>\n"); }
/* 2694 */                           String colsp = "0";
/* 2695 */                           out.write(10);
/*      */                           
/* 2697 */                           out.write("\n      ");
/*      */                           
/* 2699 */                           IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 2700 */                           _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2701 */                           _jspx_th_logic_005fiterate_005f0.setParent(null);
/*      */                           
/* 2703 */                           _jspx_th_logic_005fiterate_005f0.setName("attributesList");
/*      */                           
/* 2705 */                           _jspx_th_logic_005fiterate_005f0.setId("colnames");
/*      */                           
/* 2707 */                           _jspx_th_logic_005fiterate_005f0.setIndexId("k");
/* 2708 */                           int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2709 */                           if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2710 */                             Object colnames = null;
/* 2711 */                             Integer k = null;
/* 2712 */                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2713 */                               out = _jspx_page_context.pushBody();
/* 2714 */                               _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2715 */                               _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                             }
/* 2717 */                             colnames = _jspx_page_context.findAttribute("colnames");
/* 2718 */                             k = (Integer)_jspx_page_context.findAttribute("k");
/*      */                             for (;;) {
/* 2720 */                               out.write(10);
/*      */                               
/* 2722 */                               HashMap columnDetails = (HashMap)attributesList.get(k.intValue());
/* 2723 */                               columnType = (String)columnDetails.get("TYPE");
/* 2724 */                               String align = (String)columnDetails.get("ALIGN");
/* 2725 */                               showColumn = true;
/* 2726 */                               String unit = columnDetails.get("ATTRIBUTES") != null ? (String)((HashMap)columnDetails.get("ATTRIBUTES")).get("UNIT") : "";
/* 2727 */                               String col_name = (String)columnDetails.get("DISPLAY");
/* 2728 */                               if ((!childType.trim().equals("")) && (columnDetails.get("ATTRIBUTES") != null) && (((HashMap)columnDetails.get("ATTRIBUTES")).get("DISPLAY") != null)) {
/* 2729 */                                 col_name = (String)((HashMap)columnDetails.get("ATTRIBUTES")).get("DISPLAY");
/*      */                               }
/* 2731 */                               if (col_name.equals("Health")) {
/* 2732 */                                 colsp = "2";
/*      */                               }
/*      */                               else {
/* 2735 */                                 if ((col_name.equals("Availability")) || ((columnDetails.get("DISPLAYTYPE") != null) && (((String)columnDetails.get("DISPLAYTYPE")).equals("Image")))) {
/* 2736 */                                   align = "center";
/*      */                                 }
/* 2738 */                                 String hideClass = "";
/* 2739 */                                 String cursorStyle = "cursor:text";
/* 2740 */                                 if ((columnDetails.get("TOOLTIP") != null) && (!((String)columnDetails.get("TOOLTIP")).trim().equals(""))) {
/* 2741 */                                   hideClass = "hideddrivetip()";
/* 2742 */                                   cursorStyle = "cursor:pointer";
/*      */                                 }
/* 2744 */                                 if (columnType.equals("ACTION"))
/*      */                                 {
/* 2746 */                                   if ((actionmap != null) && (actionmap.size() > 0)) {
/* 2747 */                                     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 2748 */                                     ArrayList actionsavailable = getPermittedActions(actionmap, invokeActionsMap);
/* 2749 */                                     colVsactionsMap.put(col_name, actionsavailable);
/*      */                                     
/* 2751 */                                     if (actionsavailable.isEmpty()) {
/*      */                                       continue;
/*      */                                     }
/*      */                                   }
/* 2755 */                                   else if ((actionmap != null) && (actionmap.size() <= 0)) {
/* 2756 */                                     colspan += 2;
/*      */                                   }
/*      */                                 }
/*      */                                 
/* 2760 */                                 if (col_name.equalsIgnoreCase("Availability")) {
/* 2761 */                                   if (!showRowStatus) {
/* 2762 */                                     showColumn = false;
/*      */                                   }
/* 2764 */                                   else if (columnType.equals("MO")) {
/* 2765 */                                     attribIDs.add("" + (String)attributeDetails.get("ATTRIBUTREID"));
/*      */                                   }
/*      */                                 }
/* 2768 */                                 if (showColumn) {
/* 2769 */                                   if (columnType.equals("GRAPH")) {
/* 2770 */                                     out.write("\n      <td height=\"26\" align=\"");
/* 2771 */                                     out.print(align);
/* 2772 */                                     out.write("\" width=\"");
/* 2773 */                                     out.print(width + 5.0F);
/* 2774 */                                     out.write("%\"  class=\"monitorinfoodd-conf apmconf-dullhead\">\n    ");
/*      */                                   } else {
/* 2776 */                                     out.write("\n      <td  height=\"26\" align=\"");
/* 2777 */                                     out.print(align);
/* 2778 */                                     out.write("\" width=\"");
/* 2779 */                                     out.print(width);
/* 2780 */                                     out.write("%\"  class=\"monitorinfoodd-conf apmconf-dullhead\">         \n    "); }
/* 2781 */                                   if (columnType.equals("MO")) {
/* 2782 */                                     out.write("\n      <a id=\"");
/* 2783 */                                     out.print(tableId);
/* 2784 */                                     out.write("Sort_header");
/* 2785 */                                     out.print(k);
/* 2786 */                                     out.write("\" href=\"#\" class=\"tooltip apmconf-dullhead\" onclick=\"ts_resortTable(this,'");
/* 2787 */                                     out.print(tableId);
/* 2788 */                                     out.write("Sort',0);return false;\">\n    ");
/* 2789 */                                     out.print(FormatUtil.getString(col_name));
/* 2790 */                                     out.write("\n    ");
/*      */                                   } else {
/* 2792 */                                     if ((!col_name.equalsIgnoreCase("availability")) && (!columnType.equals("GRAPH"))) {
/* 2793 */                                       out.write("\n        <a id=\"");
/* 2794 */                                       out.print(tableId);
/* 2795 */                                       out.write("Sort_header");
/* 2796 */                                       out.print(k);
/* 2797 */                                       out.write("\" href=\"#\" class=\"tooltip apmconf-dullhead\" onMouseOver=\"ddrivetip(this,event,'");
/* 2798 */                                       out.print(FormatUtil.getString((String)columnDetails.get("TOOLTIP")));
/* 2799 */                                       out.write("',false,true,'#000000',150,'lightyellow')\" onmouseout=\"");
/* 2800 */                                       out.print(hideClass);
/* 2801 */                                       out.write("\" onclick=\"ts_resortTable(this,'");
/* 2802 */                                       out.print(tableId);
/* 2803 */                                       out.write("Sort',0);return false;\">\n        ");
/*      */                                     } else {
/* 2805 */                                       out.write("\n          <a  class=\"tooltip apmconf-dullhead\" onMouseOver=\"ddrivetip(this,event,'");
/* 2806 */                                       out.print(FormatUtil.getString((String)columnDetails.get("TOOLTIP")));
/* 2807 */                                       out.write("',false,true,'#000000',150,'lightyellow')\" onmouseout=\"");
/* 2808 */                                       out.print(hideClass);
/* 2809 */                                       out.write("\">\n        ");
/*      */                                     }
/* 2811 */                                     out.write("\n                              ");
/* 2812 */                                     out.print(FormatUtil.getString(col_name));
/* 2813 */                                     out.write("\n                              ");
/* 2814 */                                     if ((unit != null) && (!unit.trim().equals("")) && (!unit.equals("-"))) {
/* 2815 */                                       out.write("\n                  &nbsp;(");
/* 2816 */                                       out.print(FormatUtil.getString(unit));
/* 2817 */                                       out.write(")\n  ");
/*      */                                     } }
/* 2819 */                                   out.write("\n              <span class=\"sortarrow\">&nbsp;</span></a>\n        </td>\n      ");
/*      */                                 }
/* 2821 */                                 out.write("  \n      ");
/* 2822 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2823 */                                 colnames = _jspx_page_context.findAttribute("colnames");
/* 2824 */                                 k = (Integer)_jspx_page_context.findAttribute("k");
/* 2825 */                                 if (evalDoAfterBody != 2) break;
/*      */                               }
/*      */                             }
/* 2828 */                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2829 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 2832 */                           if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2833 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                           }
/*      */                           
/* 2836 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2837 */                           out.write("\n      ");
/*      */                           
/* 2839 */                           if (colspan > 0) {
/* 2840 */                             colspan += Integer.parseInt(colsp);
/* 2841 */                             colsp = Integer.toString(colspan);
/*      */                           }
/*      */                           
/* 2844 */                           if (ishealth)
/*      */                           {
/* 2846 */                             out.write("      <td height=\"26\" align=\"center\" width=\"");
/* 2847 */                             out.print(width);
/* 2848 */                             out.write("%\"   class=\"monitorinfoodd-conf apmconf-dullhead\">");
/* 2849 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2850 */                             out.write("</td>\n");
/*      */                           }
/* 2852 */                           if ((isthreshold) && (createMosForRows)) {
/* 2853 */                             out.write("\n    <td height=\"26\" align=\"center\" colspan=\"");
/* 2854 */                             out.print(colsp);
/* 2855 */                             out.write("\" width=\"");
/* 2856 */                             out.print(width);
/* 2857 */                             out.write("%\"  class=\"monitorinfoodd-conf apmconf-dullhead\">");
/* 2858 */                             out.print(ALERTCONFIG_TEXT);
/* 2859 */                             out.write("</td>\n");
/*      */                           }
/* 2861 */                           out.write("\n</tr>   \n\n");
/*      */                           
/* 2863 */                           out.write(10);
/* 2864 */                           out.write(10);
/*      */                           
/* 2866 */                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2867 */                           _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2868 */                           _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */                           
/* 2870 */                           _jspx_th_logic_005fnotEmpty_005f0.setName("rowIds");
/* 2871 */                           int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2872 */                           if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                             for (;;) {
/* 2874 */                               out.write(10);
/* 2875 */                               out.write(32);
/* 2876 */                               out.write(32);
/*      */                               
/* 2878 */                               IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 2879 */                               _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 2880 */                               _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                               
/* 2882 */                               _jspx_th_logic_005fiterate_005f1.setName("rowIds");
/*      */                               
/* 2884 */                               _jspx_th_logic_005fiterate_005f1.setId("rowidasKey");
/*      */                               
/* 2886 */                               _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/* 2887 */                               int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 2888 */                               if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 2889 */                                 Object rowidasKey = null;
/* 2890 */                                 Integer i = null;
/* 2891 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2892 */                                   out = _jspx_page_context.pushBody();
/* 2893 */                                   _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 2894 */                                   _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                 }
/* 2896 */                                 rowidasKey = _jspx_page_context.findAttribute("rowidasKey");
/* 2897 */                                 i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                 for (;;) {
/* 2899 */                                   out.write(" \n      ");
/*      */                                   
/* 2901 */                                   NotEqualTag _jspx_th_logic_005fnotEqual_005f1 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.get(NotEqualTag.class);
/* 2902 */                                   _jspx_th_logic_005fnotEqual_005f1.setPageContext(_jspx_page_context);
/* 2903 */                                   _jspx_th_logic_005fnotEqual_005f1.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                   
/* 2905 */                                   _jspx_th_logic_005fnotEqual_005f1.setValue("IsRows");
/*      */                                   
/* 2907 */                                   _jspx_th_logic_005fnotEqual_005f1.setProperty("key");
/*      */                                   
/* 2909 */                                   _jspx_th_logic_005fnotEqual_005f1.setName("rowidasKey");
/* 2910 */                                   int _jspx_eval_logic_005fnotEqual_005f1 = _jspx_th_logic_005fnotEqual_005f1.doStartTag();
/* 2911 */                                   if (_jspx_eval_logic_005fnotEqual_005f1 != 0) {
/*      */                                     for (;;) {
/* 2913 */                                       out.write("    \n      ");
/*      */                                       
/* 2915 */                                       DefineTag _jspx_th_bean_005fdefine_005f7 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2916 */                                       _jspx_th_bean_005fdefine_005f7.setPageContext(_jspx_page_context);
/* 2917 */                                       _jspx_th_bean_005fdefine_005f7.setParent(_jspx_th_logic_005fnotEqual_005f1);
/*      */                                       
/* 2919 */                                       _jspx_th_bean_005fdefine_005f7.setId("rowid");
/*      */                                       
/* 2921 */                                       _jspx_th_bean_005fdefine_005f7.setName("rowidasKey");
/*      */                                       
/* 2923 */                                       _jspx_th_bean_005fdefine_005f7.setProperty("key");
/*      */                                       
/* 2925 */                                       _jspx_th_bean_005fdefine_005f7.setType("java.lang.String");
/* 2926 */                                       int _jspx_eval_bean_005fdefine_005f7 = _jspx_th_bean_005fdefine_005f7.doStartTag();
/* 2927 */                                       if (_jspx_th_bean_005fdefine_005f7.doEndTag() == 5) {
/* 2928 */                                         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f7); return;
/*      */                                       }
/*      */                                       
/* 2931 */                                       this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f7);
/* 2932 */                                       String rowid = null;
/* 2933 */                                       rowid = (String)_jspx_page_context.findAttribute("rowid");
/* 2934 */                                       out.write("\n      ");
/*      */                                       
/* 2936 */                                       DefineTag _jspx_th_bean_005fdefine_005f8 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2937 */                                       _jspx_th_bean_005fdefine_005f8.setPageContext(_jspx_page_context);
/* 2938 */                                       _jspx_th_bean_005fdefine_005f8.setParent(_jspx_th_logic_005fnotEqual_005f1);
/*      */                                       
/* 2940 */                                       _jspx_th_bean_005fdefine_005f8.setId("attidMap");
/*      */                                       
/* 2942 */                                       _jspx_th_bean_005fdefine_005f8.setName("rowidasKey");
/*      */                                       
/* 2944 */                                       _jspx_th_bean_005fdefine_005f8.setProperty("value");
/*      */                                       
/* 2946 */                                       _jspx_th_bean_005fdefine_005f8.setType("java.util.HashMap");
/* 2947 */                                       int _jspx_eval_bean_005fdefine_005f8 = _jspx_th_bean_005fdefine_005f8.doStartTag();
/* 2948 */                                       if (_jspx_th_bean_005fdefine_005f8.doEndTag() == 5) {
/* 2949 */                                         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f8); return;
/*      */                                       }
/*      */                                       
/* 2952 */                                       this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f8);
/* 2953 */                                       HashMap attidMap = null;
/* 2954 */                                       attidMap = (HashMap)_jspx_page_context.findAttribute("attidMap");
/* 2955 */                                       out.write("      \n        ");
/*      */                                       
/*      */ 
/* 2958 */                                       resIDs.add("" + rowid);
/* 2959 */                                       Properties alert = getStatus(resIDs, attribIDs);
/* 2960 */                                       int temp_val = i.intValue();
/* 2961 */                                       String bgclass = "whitegrayborder-conf-mon";
/* 2962 */                                       String textclass = (attidMap.get("IsDisabled") != null) && (((Boolean)attidMap.get("IsDisabled")).booleanValue() == true) ? "class=\"disabledtext\"" : "";
/*      */                                       
/* 2964 */                                       out.write("  \n        <tr onmouseout=\"this.className='confheader'\" onmouseover=\"this.className='confHeaderHover'\" class=\"confheader\">\n         ");
/* 2965 */                                       if ((createMosForRows) && ((isNumericAttributesAvailable) || (isActions))) {
/* 2966 */                                         out.write("\n              <td width=\"5%\" align=\"center\" class=\"");
/* 2967 */                                         out.print(bgclass);
/* 2968 */                                         out.write("\"><input type=\"checkbox\" name=\"");
/* 2969 */                                         out.print(checkname);
/* 2970 */                                         out.write("\" id=\"");
/* 2971 */                                         out.print(checkid);
/* 2972 */                                         out.write("\" value=\"");
/* 2973 */                                         out.print(rowid);
/* 2974 */                                         out.write("\"/></td>\n          ");
/*      */                                       }
/* 2976 */                                       out.write("\n      ");
/*      */                                       
/* 2978 */                                       IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 2979 */                                       _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 2980 */                                       _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fnotEqual_005f1);
/*      */                                       
/* 2982 */                                       _jspx_th_logic_005fiterate_005f2.setName("attributesList");
/*      */                                       
/* 2984 */                                       _jspx_th_logic_005fiterate_005f2.setId("attList");
/*      */                                       
/* 2986 */                                       _jspx_th_logic_005fiterate_005f2.setIndexId("j");
/* 2987 */                                       int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 2988 */                                       if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 2989 */                                         Object attList = null;
/* 2990 */                                         Integer j = null;
/* 2991 */                                         if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 2992 */                                           out = _jspx_page_context.pushBody();
/* 2993 */                                           _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 2994 */                                           _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                                         }
/* 2996 */                                         attList = _jspx_page_context.findAttribute("attList");
/* 2997 */                                         j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                         for (;;) {
/* 2999 */                                           out.write(" \n      ");
/*      */                                           
/* 3001 */                                           DefineTag _jspx_th_bean_005fdefine_005f9 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3002 */                                           _jspx_th_bean_005fdefine_005f9.setPageContext(_jspx_page_context);
/* 3003 */                                           _jspx_th_bean_005fdefine_005f9.setParent(_jspx_th_logic_005fiterate_005f2);
/*      */                                           
/* 3005 */                                           _jspx_th_bean_005fdefine_005f9.setId("columnDetails");
/*      */                                           
/* 3007 */                                           _jspx_th_bean_005fdefine_005f9.setName("attList");
/*      */                                           
/* 3009 */                                           _jspx_th_bean_005fdefine_005f9.setType("java.util.HashMap");
/* 3010 */                                           int _jspx_eval_bean_005fdefine_005f9 = _jspx_th_bean_005fdefine_005f9.doStartTag();
/* 3011 */                                           if (_jspx_th_bean_005fdefine_005f9.doEndTag() == 5) {
/* 3012 */                                             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f9); return;
/*      */                                           }
/*      */                                           
/* 3015 */                                           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f9);
/* 3016 */                                           HashMap columnDetails = null;
/* 3017 */                                           columnDetails = (HashMap)_jspx_page_context.findAttribute("columnDetails");
/* 3018 */                                           out.write("\n      ");
/*      */                                           
/* 3020 */                                           String align = (String)columnDetails.get("ALIGN");
/* 3021 */                                           showColumn = true;
/* 3022 */                                           showAvailability = false;
/* 3023 */                                           if (((String)columnDetails.get("TYPE")).equals("ACTION")) {
/* 3024 */                                             String availValue = alert.getProperty(rowid + "#" + colavaid);
/* 3025 */                                             String healthValue = alert.getProperty(rowid + "#" + table_healthid);
/* 3026 */                                             out.write("  \n            \n            ");
/* 3027 */                                             out.print(getActionColDetails(columnDetails, (ArrayList)colVsactionsMap.get((String)columnDetails.get("DISPLAY")), actionmap, width, attidMap, rowid, resourcetype, resID, tableId, availValue, healthValue, bgclass, (Boolean)attidMap.get("IsDisabled"), (String)groupDetails.get("PRIMARYCOLUMN"), commonValues));
/* 3028 */                                             out.write("\n            \n");
/*      */                                           }
/* 3030 */                                           else if (((String)columnDetails.get("TYPE")).equals("GRAPH")) {
/* 3031 */                                             out.write("            \n            <td width=\"");
/* 3032 */                                             out.print(width + 5.0F);
/* 3033 */                                             out.write("%\"  align=\"");
/* 3034 */                                             out.print(align);
/* 3035 */                                             out.write("\" class=\"");
/* 3036 */                                             out.print(bgclass);
/* 3037 */                                             out.write("\" >\n              ");
/* 3038 */                                             out.print(getColumnGraph((LinkedHashMap)columnDetails.get("GRAPH_DATA"), attidMap));
/* 3039 */                                             out.write("\n            </td>\n");
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 3043 */                                             attributeDetails = (HashMap)columnDetails.get("ATTRIBUTES");
/* 3044 */                                             String attId = (attributeDetails != null) && (attributeDetails.get("ATTRIBUTEID") != null) ? (String)attributeDetails.get("ATTRIBUTEID") : "";
/* 3045 */                                             String attname = (attributeDetails != null) && (attributeDetails.get("NAME") != null) ? (String)attributeDetails.get("NAME") : "";
/*      */                                             
/* 3047 */                                             if ((groupDetails.get("ResponseTimeId") != null) && (((String)groupDetails.get("ResponseTimeId")).equals(attId))) {
/* 3048 */                                               attname = "responsetime";
/*      */                                             }
/* 3050 */                                             if (colavaid.equals(attId))
/*      */                                             {
/* 3052 */                                               if (!showRowStatus) {
/* 3053 */                                                 showColumn = false;
/*      */                                               }
/*      */                                               else {
/* 3056 */                                                 showAvailability = true;
/* 3057 */                                                 align = "center";
/*      */                                               }
/*      */                                             }
/*      */                                             
/*      */ 
/* 3062 */                                             if ((!table_healthid.equals(attId)) && (showColumn)) {
/* 3063 */                                               out.write("\n                <td width=\"");
/* 3064 */                                               out.print(width);
/* 3065 */                                               out.write("%\"  align=\"");
/* 3066 */                                               out.print(align);
/* 3067 */                                               out.write("\" class=\"");
/* 3068 */                                               out.print(bgclass);
/* 3069 */                                               out.write("\" >\n                ");
/*      */                                               
/* 3071 */                                               String data1 = attidMap.get(attname.toUpperCase()) != null ? (String)attidMap.get(attname.toUpperCase()) : "-";
/* 3072 */                                               if ("MO".equals((String)columnDetails.get("TYPE"))) {
/* 3073 */                                                 String href = "<a href=\"/showresource.do?resourceid=" + rowid + "&method=showResourceForResourceID&fromwhere=parentview&parentId=" + resID + "\" class=\"staticlinks\">" + (String)attidMap.get("PrimaryColumn") + "</a>";
/* 3074 */                                                 data1 = attidMap.get("PrimaryColumn") != null ? href : "-";
/*      */                                               }
/* 3076 */                                               boolean isPrimaryColumn = attname.equals((String)groupDetails.get("PRIMARYCOLUMN"));
/* 3077 */                                               data1 = (isPrimaryColumn) && (createMosForRows) ? (String)attidMap.get((String)groupDetails.get("PRIMARYCOLUMN")) : data1;
/* 3078 */                                               HashMap replace = ConfMonitorConfiguration.getInstance().getAttIdVsReplaceValues(attId) != null ? (HashMap)ConfMonitorConfiguration.getInstance().getAttIdVsReplaceValues(attId).get(data1) : null;
/* 3079 */                                               String toolTip = replace != null ? data1 : replace.containsKey("ToolTip") ? (String)replace.get("ToolTip") : "";
/* 3080 */                                               String hideClass = (toolTip == null) || (toolTip.trim().equals("")) ? "" : "hideddrivetip()";
/* 3081 */                                               String cursorStyle = hideClass.equals("") ? "cursor:text" : "cursor:pointer";
/* 3082 */                                               if (showAvailability)
/*      */                                               {
/* 3084 */                                                 out.write("\n                    <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3085 */                                                 out.print(rowid);
/* 3086 */                                                 out.write("&attributeid=");
/* 3087 */                                                 out.print(colavaid);
/* 3088 */                                                 out.write("&alertconfigurl=");
/* 3089 */                                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + rowid + "&attributeIDs=" + availabilitykeys.get(monType) + "&attributeToSelect=" + availabilitykeys.get(monType) + "&redirectto=" + encodeurl));
/* 3090 */                                                 out.write("')\">\n                        ");
/* 3091 */                                                 out.print(getSeverityImageForAvailability(alert.getProperty(rowid + "#" + colavaid)));
/* 3092 */                                                 out.write("\n                      </a>\n                  ");
/*      */                                               }
/*      */                                               else {
/* 3095 */                                                 if ((configAttributesList != null) && (configAttributesList.contains(attId))) {
/* 3096 */                                                   out.write("\n                      <a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/* 3097 */                                                   out.print(rowid);
/* 3098 */                                                   out.write("&attributeid=");
/* 3099 */                                                   out.print(attId);
/* 3100 */                                                   out.write("&childType=");
/* 3101 */                                                   out.print(childType);
/* 3102 */                                                   out.write("&period=-7',740,550)\" class=\"staticlinks\" onMouseOver=\"ddrivetip(this,event,'");
/* 3103 */                                                   out.print(FormatUtil.getString(toolTip));
/* 3104 */                                                   out.write("',false,true,'#000000',150,'lightyellow')\" onmouseout=\"");
/* 3105 */                                                   out.print(hideClass);
/* 3106 */                                                   out.write("\">\n                    ");
/* 3107 */                                                 } else if (((data1 == null) || (!data1.equals("-"))) && (!isPrimaryColumn) && (createMosForRows) && (groupDetails.get("NUMERICIDS") != null) && (((ArrayList)groupDetails.get("NUMERICIDS")).contains(attId)) && (childType.trim().equals(""))) {
/* 3108 */                                                   out.write("\n                        <a href=\"javascript:showGraphForRowData('");
/* 3109 */                                                   out.print(attId);
/* 3110 */                                                   out.write(39);
/* 3111 */                                                   out.write(44);
/* 3112 */                                                   out.write(39);
/* 3113 */                                                   out.print(rowid);
/* 3114 */                                                   out.write(39);
/* 3115 */                                                   out.write(44);
/* 3116 */                                                   out.write(39);
/* 3117 */                                                   out.print((String)request.getAttribute("reportURL"));
/* 3118 */                                                   out.write(39);
/* 3119 */                                                   out.write(44);
/* 3120 */                                                   out.write(39);
/* 3121 */                                                   out.print(System.currentTimeMillis());
/* 3122 */                                                   out.write("')\" style=\"cursor:pointer\" class=\"staticlinks\" onMouseOver=\"ddrivetip(this,event,'");
/* 3123 */                                                   out.print(FormatUtil.getString(toolTip));
/* 3124 */                                                   out.write("',false,true,'#000000',150,'lightyellow')\" onmouseout=\"");
/* 3125 */                                                   out.print(hideClass);
/* 3126 */                                                   out.write("\">   \n                  ");
/* 3127 */                                                 } else if (!toolTip.trim().equals("")) {
/* 3128 */                                                   out.write("\n                        <a style=");
/* 3129 */                                                   out.print(cursorStyle);
/* 3130 */                                                   out.write(" class=\"tooltip\" onMouseOver=\"ddrivetip(this,event,'");
/* 3131 */                                                   out.print(FormatUtil.getString(toolTip));
/* 3132 */                                                   out.write("',false,true,'#000000',150,'lightyellow')\" onmouseout=\"");
/* 3133 */                                                   out.print(hideClass);
/* 3134 */                                                   out.write("\">                      \n                    ");
/*      */                                                 }
/* 3136 */                                                 out.write("\n                      <span ");
/* 3137 */                                                 out.print(textclass);
/* 3138 */                                                 out.write(">\n                      ");
/* 3139 */                                                 if (replace != null) {
/* 3140 */                                                   if (((String)replace.get("Type")).equals("Image")) {
/* 3141 */                                                     out.write("                    \n                          <img src=\"");
/* 3142 */                                                     out.print((String)replace.get("Value"));
/* 3143 */                                                     out.write("\" hspace=\"4\" border=\"0\" align=\"absmiddle\">\n");
/*      */                                                   } else {
/* 3145 */                                                     out.write("\n                        ");
/* 3146 */                                                     out.print(FormatUtil.getString((String)replace.get("Value")));
/* 3147 */                                                     out.write(10);
/*      */                                                   }
/*      */                                                 } else {
/* 3150 */                                                   out.write("\n                    ");
/* 3151 */                                                   out.print(data1);
/* 3152 */                                                   out.write(10);
/*      */                                                 }
/* 3154 */                                                 out.write("\n                      \n                      </span>\n                      ");
/* 3155 */                                                 if (((data1 != null) && (!data1.equals("-")) && (groupDetails.get("NUMERICIDS") != null) && (((ArrayList)groupDetails.get("NUMERICIDS")).contains(attId)) && (childType.trim().equals(""))) || (!toolTip.trim().equals("")) || ((configAttributesList != null) && (configAttributesList.contains(attId)))) {
/* 3156 */                                                   out.write("\n                      </a>\n                      ");
/*      */                                                 }
/* 3158 */                                                 out.write("\n                  ");
/*      */                                               }
/* 3160 */                                               out.write("\n                </td>\n               ");
/*      */                                             }
/* 3162 */                                             out.write("\n            ");
/*      */                                           }
/* 3164 */                                           out.write("\n      \n              \n                       \n        \n    ");
/* 3165 */                                           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 3166 */                                           attList = _jspx_page_context.findAttribute("attList");
/* 3167 */                                           j = (Integer)_jspx_page_context.findAttribute("j");
/* 3168 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3171 */                                         if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 3172 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3175 */                                       if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 3176 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                                       }
/*      */                                       
/* 3179 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 3180 */                                       out.write(10);
/* 3181 */                                       out.write(32);
/* 3182 */                                       out.write(32);
/* 3183 */                                       if (ishealth) {
/* 3184 */                                         out.write("\n            <td width=\"");
/* 3185 */                                         out.print(width);
/* 3186 */                                         out.write("%\"   align=\"center\" height=\"25\" align=\"left\" class=\"");
/* 3187 */                                         out.print(bgclass);
/* 3188 */                                         out.write("\">\n              <a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3189 */                                         out.print(rowid);
/* 3190 */                                         out.write("&attributeid=");
/* 3191 */                                         out.print(table_healthid);
/* 3192 */                                         out.write("&alertconfigurl=");
/* 3193 */                                         out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + rowid + "&attributeIDs=" + availabilitykeys.get(monType) + "&attributeToSelect=" + availabilitykeys.get(monType) + "&redirectto=" + encodeurl));
/* 3194 */                                         out.write("')\">\n                  ");
/* 3195 */                                         out.print(getSeverityImageForHealth(alert.getProperty(rowid + "#" + table_healthid)));
/* 3196 */                                         out.write("\n              </a>\n            </td>\n            ");
/*      */                                       }
/* 3198 */                                       if ((isthreshold) && (createMosForRows)) {
/* 3199 */                                         out.write("\n            <td width=\"");
/* 3200 */                                         out.print(width);
/* 3201 */                                         out.write("%\" colspan=\"2\" align=\"center\" class=\"");
/* 3202 */                                         out.print(bgclass);
/* 3203 */                                         out.write("\">\n              ");
/* 3204 */                                         if (!childType.trim().equals("")) {
/* 3205 */                                           out.write("\n                <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 3206 */                                           out.print(rowid);
/* 3207 */                                           out.write("&mtype=");
/* 3208 */                                           out.print(monType);
/* 3209 */                                           out.write("&viewBy=monitors\" class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" hspace=\"4\" border=\"0\" align=\"absmiddle\"></a>\n              ");
/*      */                                         } else {
/* 3211 */                                           out.write("\n                <a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3212 */                                           out.print(rowid);
/* 3213 */                                           out.write("&attributeIDs=");
/* 3214 */                                           out.print((String)groupDetails.get("THRESHOLD-ATTRIBUTES"));
/* 3215 */                                           out.write("&attributeToSelect=");
/* 3216 */                                           out.print((String)groupDetails.get("FIRST-ATTRIBUTE-TO-SELECT"));
/* 3217 */                                           out.write("&redirectto=");
/* 3218 */                                           out.print(encodeurl);
/* 3219 */                                           out.write("' class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" hspace=\"4\" border=\"0\" align=\"absmiddle\"></a>\n                ");
/*      */                                         }
/* 3221 */                                         out.write("\n            </td>\n            ");
/*      */                                       }
/* 3223 */                                       out.write("\n            </tr>\n          \n  ");
/* 3224 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f1.doAfterBody();
/* 3225 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3229 */                                   if (_jspx_th_logic_005fnotEqual_005f1.doEndTag() == 5) {
/* 3230 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f1); return;
/*      */                                   }
/*      */                                   
/* 3233 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f1);
/* 3234 */                                   out.write(10);
/* 3235 */                                   out.write(32);
/* 3236 */                                   out.write(32);
/* 3237 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 3238 */                                   rowidasKey = _jspx_page_context.findAttribute("rowidasKey");
/* 3239 */                                   i = (Integer)_jspx_page_context.findAttribute("i");
/* 3240 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3243 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3244 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3247 */                               if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 3248 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                               }
/*      */                               
/* 3251 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 3252 */                               out.write(10);
/* 3253 */                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3254 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3258 */                           if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3259 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                           }
/*      */                           
/* 3262 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3263 */                           out.write(" \n</table>\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" >     \n            <tr>\n              <td  align=\"left\" colspan=\"6\" ></td>\n              <td colspan=\"3\" align=\"left\" >\n                <div id=\"Bottom_");
/* 3264 */                           out.print(tableId);
/* 3265 */                           out.write(95);
/* 3266 */                           out.print(resID);
/* 3267 */                           out.write("\" style=\"display:block; padding-top:10px;padding-bottom:7px;\">         \n                  <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n                    <tr>\n                      <td  width=\"10%\" class=\"bodytext\">&nbsp;\n                      ");
/* 3268 */                           if (isActions) {
/* 3269 */                             String actionsList = getTableActionsList(tActionList, actionmap, tableName, commonValues, resID, resourcetype);
/* 3270 */                             if ((userRole.equals("ADMIN")) || (!actionsList.trim().equals(""))) {
/* 3271 */                               out.write("\n                          ");
/* 3272 */                               out.print(FormatUtil.getString("am.webclient.ConfTable.action"));
/* 3273 */                               out.write("&nbsp;\n                          <select name=\"tableActions\" class=\"formtext\" onchange=\"javascript:changeAction(this,'");
/* 3274 */                               out.print(fname);
/* 3275 */                               out.write(39);
/* 3276 */                               out.write(44);
/* 3277 */                               out.write(39);
/* 3278 */                               out.print(checkname);
/* 3279 */                               out.write(39);
/* 3280 */                               out.write(44);
/* 3281 */                               out.print(fname);
/* 3282 */                               out.write(44);
/* 3283 */                               out.write(39);
/* 3284 */                               out.print(formid);
/* 3285 */                               out.write(39);
/* 3286 */                               out.write(44);
/* 3287 */                               out.write(39);
/* 3288 */                               out.print(tableId);
/* 3289 */                               out.write(39);
/* 3290 */                               out.write(44);
/* 3291 */                               out.write(39);
/* 3292 */                               out.print(table_healthid);
/* 3293 */                               out.write(39);
/* 3294 */                               out.write(44);
/* 3295 */                               out.write(39);
/* 3296 */                               out.print(childType);
/* 3297 */                               out.write(39);
/* 3298 */                               out.write(44);
/* 3299 */                               out.write(39);
/* 3300 */                               out.print(encodeurl);
/* 3301 */                               out.write(39);
/* 3302 */                               out.write(44);
/* 3303 */                               out.write(39);
/* 3304 */                               out.print(tableName);
/* 3305 */                               out.write("')\">\n                              <option SELECTED value=\"Default\">--");
/* 3306 */                               out.print(FormatUtil.getString("am.webclient.ConfTable.selectaction"));
/* 3307 */                               out.write("--</option>\n                              ");
/*      */                               
/* 3309 */                               PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3310 */                               _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3311 */                               _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                               
/* 3313 */                               _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 3314 */                               int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3315 */                               if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                 for (;;) {
/* 3317 */                                   out.write("\n                                ");
/* 3318 */                                   if (!childType.trim().equals("")) {
/* 3319 */                                     out.write("\n                                  <option value=\"deleteMos\">");
/* 3320 */                                     out.print(FormatUtil.getString("am.webclient.cam.delete.link"));
/* 3321 */                                     out.write("</option>\n                                ");
/*      */                                   } else {
/* 3323 */                                     out.write("\n                                <option value=\"Disable\">");
/* 3324 */                                     out.print(FormatUtil.getString("Disable"));
/* 3325 */                                     out.write("</option>\n                                 <option value=\"Enable\">");
/* 3326 */                                     out.print(FormatUtil.getString("Enable"));
/* 3327 */                                     out.write("</option>\n                                 <option value=\"Delete\">");
/* 3328 */                                     out.print(FormatUtil.getString("am.webclient.cam.delete.link"));
/* 3329 */                                     out.write("</option>\n                                 ");
/*      */                                   }
/* 3331 */                                   out.write("\n                              ");
/* 3332 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3333 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3337 */                               if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3338 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                               }
/*      */                               
/* 3341 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3342 */                               out.write("\n                               ");
/* 3343 */                               out.print(actionsList);
/* 3344 */                               out.write("\n                          </select>&nbsp; &nbsp;\n                          ");
/*      */                             }
/* 3346 */                             out.write("\n                      "); }
/* 3347 */                           if ((createMosForRows) && (isNumericAttributesAvailable) && (childType.trim().equals(""))) {
/* 3348 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 3350 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3351 */                             _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3352 */                             _jspx_th_logic_005fnotEmpty_005f1.setParent(null);
/*      */                             
/* 3354 */                             _jspx_th_logic_005fnotEmpty_005f1.setName("attributesList");
/* 3355 */                             int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3356 */                             if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                               for (;;) {
/* 3358 */                                 out.write("\n                            ");
/* 3359 */                                 out.print(FormatUtil.getString("am.webclient.ConfTable.CompareReports"));
/* 3360 */                                 out.write("\n                            <select name=\"attList\" class=\"formtext\" onchange=\"javascript:showGraph(this,'");
/* 3361 */                                 out.print(fname);
/* 3362 */                                 out.write(39);
/* 3363 */                                 out.write(44);
/* 3364 */                                 out.write(39);
/* 3365 */                                 out.print(checkname);
/* 3366 */                                 out.write(39);
/* 3367 */                                 out.write(44);
/* 3368 */                                 out.write(39);
/* 3369 */                                 out.print(formid);
/* 3370 */                                 out.write(39);
/* 3371 */                                 out.write(44);
/* 3372 */                                 out.write(39);
/* 3373 */                                 out.print(isQMExTimeTable);
/* 3374 */                                 out.write(39);
/* 3375 */                                 out.write(44);
/* 3376 */                                 out.write(39);
/* 3377 */                                 out.print(resourcetype);
/* 3378 */                                 out.write(39);
/* 3379 */                                 out.write(44);
/* 3380 */                                 out.write(39);
/* 3381 */                                 out.print((String)request.getAttribute("baseid1"));
/* 3382 */                                 out.write(39);
/* 3383 */                                 out.write(44);
/* 3384 */                                 out.write(39);
/* 3385 */                                 out.print(resID);
/* 3386 */                                 out.write(39);
/* 3387 */                                 out.write(44);
/* 3388 */                                 out.write(39);
/* 3389 */                                 out.print(tableName);
/* 3390 */                                 out.write(39);
/* 3391 */                                 out.write(44);
/* 3392 */                                 out.write(39);
/* 3393 */                                 out.print(FormatUtil.getString("am.comparereport.jsalert.text"));
/* 3394 */                                 out.write("')\">\n                                        <option SELECTED value=\"Default\">--");
/* 3395 */                                 out.print(FormatUtil.getString("am.webclient.ConfTable.selectmetric"));
/* 3396 */                                 out.write("--</option>\n                                              ");
/*      */                                 
/* 3398 */                                 IterateTag _jspx_th_logic_005fiterate_005f3 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 3399 */                                 _jspx_th_logic_005fiterate_005f3.setPageContext(_jspx_page_context);
/* 3400 */                                 _jspx_th_logic_005fiterate_005f3.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                 
/* 3402 */                                 _jspx_th_logic_005fiterate_005f3.setName("attributesList");
/*      */                                 
/* 3404 */                                 _jspx_th_logic_005fiterate_005f3.setId("colnames");
/*      */                                 
/* 3406 */                                 _jspx_th_logic_005fiterate_005f3.setIndexId("l");
/* 3407 */                                 int _jspx_eval_logic_005fiterate_005f3 = _jspx_th_logic_005fiterate_005f3.doStartTag();
/* 3408 */                                 if (_jspx_eval_logic_005fiterate_005f3 != 0) {
/* 3409 */                                   Object colnames = null;
/* 3410 */                                   Integer l = null;
/* 3411 */                                   if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 3412 */                                     out = _jspx_page_context.pushBody();
/* 3413 */                                     _jspx_th_logic_005fiterate_005f3.setBodyContent((BodyContent)out);
/* 3414 */                                     _jspx_th_logic_005fiterate_005f3.doInitBody();
/*      */                                   }
/* 3416 */                                   colnames = _jspx_page_context.findAttribute("colnames");
/* 3417 */                                   l = (Integer)_jspx_page_context.findAttribute("l");
/*      */                                   for (;;) {
/* 3419 */                                     out.write("                                             \n                                              ");
/* 3420 */                                     HashMap columnDetails = (HashMap)attributesList.get(l.intValue());
/* 3421 */                                     if (((String)columnDetails.get("TYPE")).equals("RAW")) {
/* 3422 */                                       String attributeId = (String)((HashMap)columnDetails.get("ATTRIBUTES")).get("ATTRIBUTEID");
/* 3423 */                                       if (((ArrayList)groupDetails.get("NUMERICIDS")).contains(attributeId)) {
/* 3424 */                                         out.write("                                \n                                              \n                                                 <option value=\"");
/* 3425 */                                         out.print(attributeId);
/* 3426 */                                         out.write(34);
/* 3427 */                                         out.write(62);
/* 3428 */                                         out.print(FormatUtil.getString((String)columnDetails.get("DISPLAY")));
/* 3429 */                                         out.write("</option>\n                                  \n                                            ");
/*      */                                       } }
/* 3431 */                                     out.write("   \n                                          ");
/* 3432 */                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f3.doAfterBody();
/* 3433 */                                     colnames = _jspx_page_context.findAttribute("colnames");
/* 3434 */                                     l = (Integer)_jspx_page_context.findAttribute("l");
/* 3435 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3438 */                                   if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 3439 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3442 */                                 if (_jspx_th_logic_005fiterate_005f3.doEndTag() == 5) {
/* 3443 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3); return;
/*      */                                 }
/*      */                                 
/* 3446 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3);
/* 3447 */                                 out.write("\n                          </select>\n\t\t\t\t\t\t");
/* 3448 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3449 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3453 */                             if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3454 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                             }
/*      */                             
/* 3457 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3458 */                             out.write("\n                      "); }
/* 3459 */                           if (request.getAttribute("SHOW-INSTANT-MSG") != null) {
/* 3460 */                             out.write("\n                        <span  style=\"color: #B4B4B4; font-size: 11px;\">");
/* 3461 */                             out.print(FormatUtil.getString("am.webclient.conf.tables.instance.msg.text"));
/* 3462 */                             out.write("</span>\n                                            ");
/*      */                           }
/* 3464 */                           out.write("\n                    </td>\n                  </tr>\n              </table>\n          </div>\n        </td>\n      </tr>\n  </table>  \n    \n");
/*      */ 
/*      */ 
/*      */                         }
/* 3468 */                         else if ((!isDCDisabled) || (showMessageOnConditionFailure)) {
/* 3469 */                           out.write("\n<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-data-table\" width=\"100%\">\n<tr height=\"40\">\n    <td class=\"bodytextbold\" align=\"center\">");
/* 3470 */                           out.print(FormatUtil.getString(noDataMsg));
/* 3471 */                           out.write("\n      ");
/* 3472 */                           if (showMessageOnConditionFailure) {
/* 3473 */                             out.write("\n      ");
/*      */                             
/* 3475 */                             PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3476 */                             _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3477 */                             _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */                             
/* 3479 */                             _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 3480 */                             int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3481 */                             if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                               for (;;) {
/* 3483 */                                 out.write(32);
/* 3484 */                                 out.write(32);
/* 3485 */                                 out.print(FormatUtil.getString("am.webclient.conf.datacollection.enable.link", new String[] { resourcetype }));
/* 3486 */                                 out.write("  \n      ");
/* 3487 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3488 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3492 */                             if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3493 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                             }
/*      */                             
/* 3496 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3497 */                             out.write("\n     ");
/*      */                           }
/* 3499 */                           out.write("\n    </td>\n</tr>\n</table>\n");
/*      */                         }
/* 3501 */                         out.write("\n          \n          </div>\n          </div>\n          </form>\n      \n          \n");
/*      */                       }
/* 3503 */                     } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3504 */         out = _jspx_out;
/* 3505 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3506 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3507 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3510 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fequal_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3516 */     PageContext pageContext = _jspx_page_context;
/* 3517 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3519 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3520 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3521 */     _jspx_th_c_005fout_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fequal_005f0);
/*      */     
/* 3523 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3525 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3526 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3527 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3528 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3529 */       return true;
/*      */     }
/* 3531 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3532 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3537 */     PageContext pageContext = _jspx_page_context;
/* 3538 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3540 */     EqualTag _jspx_th_logic_005fequal_005f1 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.get(EqualTag.class);
/* 3541 */     _jspx_th_logic_005fequal_005f1.setPageContext(_jspx_page_context);
/* 3542 */     _jspx_th_logic_005fequal_005f1.setParent(null);
/*      */     
/* 3544 */     _jspx_th_logic_005fequal_005f1.setName("OpenInNewWindow");
/*      */     
/* 3546 */     _jspx_th_logic_005fequal_005f1.setValue("true");
/* 3547 */     int _jspx_eval_logic_005fequal_005f1 = _jspx_th_logic_005fequal_005f1.doStartTag();
/* 3548 */     if (_jspx_eval_logic_005fequal_005f1 != 0) {
/*      */       for (;;) {
/* 3550 */         out.write("\n  <div class=\"apmconf-table-showAllframe\">\n");
/* 3551 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f1.doAfterBody();
/* 3552 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3556 */     if (_jspx_th_logic_005fequal_005f1.doEndTag() == 5) {
/* 3557 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/* 3558 */       return true;
/*      */     }
/* 3560 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/* 3561 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\includes\ConfTableDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */