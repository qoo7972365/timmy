/*      */ package org.apache.jsp.jsp.includes;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
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
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
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
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ShowGroupsDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 2184 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2185 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange_005fmultiple;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2210 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2214 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange_005fmultiple = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2232 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2236 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.release();
/* 2239 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick.release();
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2241 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.release();
/* 2242 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2243 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2244 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2245 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2246 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2247 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange_005fmultiple.release();
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.release();
/* 2249 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2250 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2259 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2262 */     JspWriter out = null;
/* 2263 */     Object page = this;
/* 2264 */     JspWriter _jspx_out = null;
/* 2265 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2269 */       response.setContentType("text/html;charset=UTF-8");
/* 2270 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2272 */       _jspx_page_context = pageContext;
/* 2273 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2274 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2275 */       session = pageContext.getSession();
/* 2276 */       out = pageContext.getOut();
/* 2277 */       _jspx_out = out;
/*      */       
/* 2279 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/chosen.jquery.min.js\"></SCRIPT>\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n<script>\njQuery(document).ready(function(){\n\tjQuery(\".chosenselectmas\").chosen({ // NO I18N\n\t\tno_results_text: '");
/* 2280 */       out.print(FormatUtil.getString("am.common.search.no.result.match.text"));
/* 2281 */       out.write("',\n\t\tdisable_search_threshold: 5\n\t});\n\tjQuery(\".chosenselectmasgroup\").chosen({ // NO I18N\n\t\tno_results_text: '");
/* 2282 */       out.print(FormatUtil.getString("am.common.search.no.result.match.text"));
/* 2283 */       out.write("',\n\t\tdisable_search_threshold: 5\n\t})\n\tjQuery(\".chosenmultiselect\").chosen({ // NO I18N\n\t\tplaceholder_text_multiple: '");
/* 2284 */       out.print(FormatUtil.getString("am.webclient.fault.alarm.search.text") + "....");
/* 2285 */       out.write("',\t\t\n\t\tno_results_text: '");
/* 2286 */       out.print(FormatUtil.getString("am.common.search.no.result.match.text"));
/* 2287 */       out.write("',\n\t\t//enable_split_word_search: false\n\t\tsearch_contains : true\n\t});\n});\nfunction loadMGroupsForMAS() {\n\tvar type=\"admin\";// NO I18N\n\tvar selectedMasServer = \"\";\n\tvar selectedMasServerCopy = \"\";\n\tvar resourceType = \"");
/* 2288 */       out.print(request.getAttribute("type"));
/* 2289 */       out.write("\";\n\tif (resourceType == \"UrlMonitor\") {\n\t\tselectedMasServer=document.UrlForm.selectedServer.value;\n\t\tselectedMasServerCopy=document.UrlForm.selectedServer.value;\n\t} else {\n\t\tif (document.AMActionForm == null || document.AMActionForm == 'undefined' || document.AMActionForm == \"null\") {\n\t\t\treturn;\n\t\t}\n\t\tselectedMasServer = document.AMActionForm.selectedServer.value;\n\t\tselectedMasServerCopy = document.AMActionForm.selectedServer.value;\t\t\n\t}\n\tif (selectedMasServer != \"-\") {\n\t\ttype=\"mas\";// NO I18N\n\t} else {\n\t\tselectedMasServer = \"0\";\n\t}\n\tselectedMasServer = parseInt(selectedMasServer);\n\tvar range = \"");
/* 2290 */       out.print(com.adventnet.appmanager.server.framework.comm.Constants.RANGE);
/* 2291 */       out.write("\";\n\trange = parseInt(range);\n\tvar allMonitorGroupsInAdminServer = '");
/* 2292 */       out.print(request.getAttribute("AllMonitorGroupsInAdminServer"));
/* 2293 */       out.write("';\n\tvar j=1;\n\tvar x=0;\n\tvar y=0;\n\tvar n=0;\n\tvar monitorGrpSelObj;\t\n\tif (resourceType == \"UrlMonitor\") {\n\t\tmonitorGrpSelObj=document.UrlForm.haid;\n\t} else {\n\t\tmonitorGrpSelObj=document.AMActionForm.haid\n\t}\n\t$('.chosenmultiselect').empty();\n\tvar arrofOptionsAAM = new Array();\n\tvar arrofOptionsMAS = new Array();\n\tvar arrObj1 = allMonitorGroupsInAdminServer.split(\"],\");\n\tfor (var i=0;i<arrObj1.length;i++) {\t\t\n\t\tvar arrObj2 = arrObj1[i].split(\",\");\n\t\tvar mgName = arrObj2[0].replace(\"[[\",\"\").replace(\"[\",\"\");\n\t\tmgName = mgName.replace(new RegExp('&nbsp;', 'g'),\"\\u00A0\"); // NO I18N\n\t\tvar mgId = arrObj2[1].trim();\n\t\tvar masName = arrObj2[3].replace(\"]]\",\"\").replace(\"]\",\"\").trim();\n\t\tvar startRange = selectedMasServer * range;\n\t\tvar endRange = (selectedMasServer * range) + range;\n\t\tmgId = parseInt(mgId);\n\t\tif ((type == \"mas\") && ((mgId >= startRange && mgId < endRange) || (mgId < range))) {\n\t\t\toptObj = document.createElement(\"OPTION\");\n\t\t\toptObj.value=mgId;\n\t\t\tif (masName == \"Admin Server\") {\n\t\t\t\toptObj.text=mgName;\n\t\t\t\tarrofOptionsAAM[x++]=optObj;\n");
/* 2294 */       out.write("\t\t\t} else {\n\t\t\t\toptObj.text=mgName+\"_\"+masName;\n\t\t\t\tarrofOptionsMAS[y++]=optObj\n\t\t\t}\n\t\t} else if((type == \"admin\") && (mgId < range)) { // NO I18N\n\t\t\toptObj = document.createElement(\"OPTION\");\n\t\t\toptObj.value=mgId\n\t\t\toptObj.text=mgName\t\n\t\t\tarrofOptionsAAM[x++]=optObj;\n\t\t}\n\t}\t\n\twhile(n < x) {\n\t\tmonitorGrpSelObj[j++] = arrofOptionsAAM[n++]\n\t}\n\tn=0;\n\twhile(n < y) {\n\t\tmonitorGrpSelObj[j++] = arrofOptionsMAS[n++]\n\t}\t\n\t$('.chosenmultiselect').trigger(\"liszt:updated\"); // NO I18N\n\tvar agentObj = document.getElementById(\"runOnAgent\");\n\tif(agentObj != null && agentObj != 'undefined' && agentObj.checked) {\n\t\tif (selectedMasServerCopy == \"-\")  {\n\t\t\tagentObj.checked=false;\n\t\t\t$('.agentLoactionDiv').slideUp(\"slow\"); //NO I18N\n\t\t} else {\n\t\t\t$.post(\"/showAgent.do?method=getAgentDetails&selectedServer=\"+selectedMasServerCopy+\"&ajaxRequest=true\", function (data){$('.agentLoactionDiv').html(data);});//NO I18N\t\t\t\n\t\t\t$('.agentLoactionDiv').slideDown(\"slow\"); //NO I18N\n\t\t}\n\t}\n}\n\nfunction showAsPerSelection(value) {\n\t\n\tif (value == '0') {\n");
/* 2295 */       out.write("\t\thideRow(\"masListBox\"); // NO I18N\n\t\thideRow(\"masDomainListBox\");// NO I18N\n\t} else if (value == '1') {\n\t\thideRow(\"masListBox\");// NO I18N\n\t\tshowRow(\"masDomainListBox\");\t// NO I18N\t\n\t} else {\n\t\tshowRow(\"masListBox\");// NO I18N\n\t\thideRow(\"masDomainListBox\");\t// NO I18N\t\n\t}\n\t$('.chosenselectmasgroup').trigger(\"liszt:updated\"); // NO I18N\n\t$('.chosenselectmasgroup').chosen().find('option:eq(0)').prop(\"selected\", \"selected\");// NO I18N\n\t\n\t$('.chosenselectmas').trigger(\"liszt:updated\");// NO I18N\n\t$('.chosenselectmas').chosen().find('option:eq(0)').prop(\"selected\", \"selected\");// NO I18N\t\n\tloadMGroupsForMAS();\t\n}\nfunction resetname(name)\n{\n\tdocument.AMActionForm.groupname.value='';\n}\nfunction createGroup()\n{\n\tvar grpname = \"\";\n\tvar resourceType = \"");
/* 2296 */       out.print(request.getAttribute("type"));
/* 2297 */       out.write("\";\n\tif (resourceType == \"UrlMonitor\") {\n\t\tgrpname=document.UrlForm.groupname.value;\n\t} else {\n\t\tgrpname=document.AMActionForm.groupname.value;\n\t}\n\tif(grpname=='')\n\t{\n\t\talert(\"");
/* 2298 */       out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2299 */       out.write("\");\n\t\thideDiv('group'); // NO I18N\n\t\tshowDiv('creategroup'); // NO I18N\n\t\tif (resourceType == \"UrlMonitor\") {\n\t\t\tdocument.UrlForm.groupname.focus();\n\t\t} else {\n\t\t\tdocument.AMActionForm.groupname.focus();\n\t\t}\n\t\tdocument.AMActionForm.groupname.focus();\n\t\treturn false;\n\t}\n\telse\n\t{\n\t\thideDiv('creategroup'); // NO I18N\n\t\turl=\"/adminAction.do?method=createMonitorGroup&groupname=\"+encodeURIComponent(grpname);\t//NO I18N\n\t\thttp.open(\"GET\",url,true);\n\t\thttp.onreadystatechange = getActionTypes;\n\t\thttp.send(null);\n\t\tshowDiv('group'); // NO I18N\n\t}\n}\nfunction getActionTypes()\n{\n    if(http.readyState == 4)\n    {\n\t\tvar result = http.responseText;\n\t\tvar id=result;\n\t\tvar stringtokens=id.split(\",\");\n\t\tsid = stringtokens[2];\n\t\tsname=stringtokens[1];\n\t\tsname=decodeURIComponent(stringtokens[1]);\n\t\tsmessage=stringtokens[0];\n\t\tif (sname==null || sname=='undefined')\n\t\t{\n\t\t\tshowDiv(\"groupmessage\"); // NO I18N\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tvar resourceType = \"");
/* 2300 */       out.print(request.getAttribute("type"));
/* 2301 */       out.write("\";\n\t\t\tif (resourceType == \"UrlMonitor\") {\n\t\t\t\tdocument.UrlForm.haid.options[document.UrlForm.haid.length] =new Option(sname,sid,false,true);\n\t\t\t} else {\n\t\t\t\tdocument.AMActionForm.haid.options[document.AMActionForm.haid.length] =new Option(sname,sid,false,true);\n\t\t\t}\n\t\t\t\n\t\t\thideDiv(\"creategroup\"); // NO I18N\n\t\t\thideDiv(\"group\"); // NO I18N\n\t\t\tshowDiv(\"groupmessage\"); // NO I18N\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t\t\t$('.chosenmultiselect').trigger(\"liszt:updated\"); // NO I18N\n\t  \t}\n\t}\n}\n</script>\n");
/* 2302 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2304 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2305 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2306 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2308 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2310 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2312 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2314 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2315 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2316 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2317 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2320 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2321 */         String available = null;
/* 2322 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2323 */         out.write(10);
/*      */         
/* 2325 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2326 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2327 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2329 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2331 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2333 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2335 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2336 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2337 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2338 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2341 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2342 */           String unavailable = null;
/* 2343 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2344 */           out.write(10);
/*      */           
/* 2346 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2347 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2348 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2350 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2352 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2354 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2356 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2357 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2358 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2359 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2362 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2363 */             String unmanaged = null;
/* 2364 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2365 */             out.write(10);
/*      */             
/* 2367 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2368 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2369 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2371 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2373 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2375 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2377 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2378 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2379 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2380 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2383 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2384 */               String scheduled = null;
/* 2385 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2386 */               out.write(10);
/*      */               
/* 2388 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2389 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2390 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2392 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2394 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2396 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2398 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2399 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2400 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2401 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2404 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2405 */                 String critical = null;
/* 2406 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2407 */                 out.write(10);
/*      */                 
/* 2409 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2410 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2411 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2413 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2415 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2417 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2419 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2420 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2421 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2422 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2425 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2426 */                   String clear = null;
/* 2427 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2428 */                   out.write(10);
/*      */                   
/* 2430 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2431 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2432 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2434 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2436 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2438 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2440 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2441 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2442 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2443 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2446 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2447 */                     String warning = null;
/* 2448 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2449 */                     out.write(10);
/* 2450 */                     out.write(10);
/*      */                     
/* 2452 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2453 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2455 */                     out.write(10);
/* 2456 */                     out.write(10);
/* 2457 */                     out.write(10);
/* 2458 */                     out.write(10);
/*      */                     
/* 2460 */                     String hideStyle = request.getParameter("hideStyle");
/* 2461 */                     if ((hideStyle == null) || (hideStyle.equalsIgnoreCase("null"))) {
/* 2462 */                       hideStyle = "";
/*      */                     }
/* 2464 */                     String showDetailsOf = request.getParameter("showDetailsOf");
/* 2465 */                     if ((showDetailsOf == null) || (showDetailsOf.length() == 0)) {
/* 2466 */                       showDetailsOf = "managedServerGroups";
/*      */                     }
/* 2468 */                     if ((EnterpriseUtil.isAdminServer()) && (showDetailsOf.equals("managedServerGroups")))
/*      */                     {
/* 2470 */                       List listOfMas = com.adventnet.appmanager.server.framework.comm.CommDBUtil.getDistributedServers(-1);
/* 2471 */                       request.setAttribute("managedServerList", listOfMas);
/*      */                       
/* 2473 */                       List listOfMasGroups = EnterpriseUtil.getMASGroupNameList();
/* 2474 */                       request.setAttribute("managedServerDomainList", listOfMasGroups);
/*      */                       
/* 2476 */                       out.write("\n\t<TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"8\" CELLSPACING=\"0\" class=\"lrborder ");
/* 2477 */                       out.print(hideStyle);
/* 2478 */                       out.write("\" CELLPADING=\"0\">\n        <tr>\n          <td colspan=\"2\" class=\"tablebottom bodytextbold\" style=\"height:30px;\">");
/* 2479 */                       out.print(FormatUtil.getString("am.webclient.admin.addmonitor.mas.heading.text"));
/* 2480 */                       out.write("</td>\n        </tr>       \n        <tr>\n        \t<td class=\"bodytext\" style=\"width:25%\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"19\"></td>\n        \t<td  align=\"left\">\n\t\t\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t\t");
/* 2481 */                       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */                         return;
/* 2483 */                       out.write("\n\t\t\t\t\t<tr><td align=\"left\" height=\"30\">");
/*      */                       
/* 2485 */                       RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick.get(RadioTag.class);
/* 2486 */                       _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 2487 */                       _jspx_th_html_005fradio_005f0.setParent(null);
/*      */                       
/* 2489 */                       _jspx_th_html_005fradio_005f0.setProperty("masSelectType");
/*      */                       
/* 2491 */                       _jspx_th_html_005fradio_005f0.setOnclick("javascript:showAsPerSelection('0');");
/*      */                       
/* 2493 */                       _jspx_th_html_005fradio_005f0.setValue("0");
/* 2494 */                       int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 2495 */                       if (_jspx_eval_html_005fradio_005f0 != 0) {
/* 2496 */                         if (_jspx_eval_html_005fradio_005f0 != 1) {
/* 2497 */                           out = _jspx_page_context.pushBody();
/* 2498 */                           _jspx_th_html_005fradio_005f0.setBodyContent((BodyContent)out);
/* 2499 */                           _jspx_th_html_005fradio_005f0.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2502 */                           out.write("<span class=\"bodytext\">");
/* 2503 */                           out.print(FormatUtil.getString("am.webclient.admin.add.monitor.select.mas.load.factor.text"));
/* 2504 */                           out.write("</span>");
/* 2505 */                           int evalDoAfterBody = _jspx_th_html_005fradio_005f0.doAfterBody();
/* 2506 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2509 */                         if (_jspx_eval_html_005fradio_005f0 != 1) {
/* 2510 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2513 */                       if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 2514 */                         this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick.reuse(_jspx_th_html_005fradio_005f0); return;
/*      */                       }
/*      */                       
/* 2517 */                       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick.reuse(_jspx_th_html_005fradio_005f0);
/* 2518 */                       out.write("</td></tr>\n\t\t\t\t\t<tr><td align=\"left\" height=\"30\">");
/*      */                       
/* 2520 */                       RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick.get(RadioTag.class);
/* 2521 */                       _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 2522 */                       _jspx_th_html_005fradio_005f1.setParent(null);
/*      */                       
/* 2524 */                       _jspx_th_html_005fradio_005f1.setProperty("masSelectType");
/*      */                       
/* 2526 */                       _jspx_th_html_005fradio_005f1.setOnclick("javascript:showAsPerSelection('1');");
/*      */                       
/* 2528 */                       _jspx_th_html_005fradio_005f1.setValue("1");
/* 2529 */                       int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 2530 */                       if (_jspx_eval_html_005fradio_005f1 != 0) {
/* 2531 */                         if (_jspx_eval_html_005fradio_005f1 != 1) {
/* 2532 */                           out = _jspx_page_context.pushBody();
/* 2533 */                           _jspx_th_html_005fradio_005f1.setBodyContent((BodyContent)out);
/* 2534 */                           _jspx_th_html_005fradio_005f1.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2537 */                           out.write("<span class=\"bodytext\">");
/* 2538 */                           out.print(FormatUtil.getString("am.webclient.admin.add.monitor.select.mas.load.factor.masgroup.text"));
/* 2539 */                           out.write("</span>");
/* 2540 */                           int evalDoAfterBody = _jspx_th_html_005fradio_005f1.doAfterBody();
/* 2541 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2544 */                         if (_jspx_eval_html_005fradio_005f1 != 1) {
/* 2545 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2548 */                       if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 2549 */                         this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick.reuse(_jspx_th_html_005fradio_005f1); return;
/*      */                       }
/*      */                       
/* 2552 */                       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick.reuse(_jspx_th_html_005fradio_005f1);
/* 2553 */                       out.write("</td></tr>\n\t\t\t\t\t<tr><td align=\"left\" height=\"30\">");
/*      */                       
/* 2555 */                       RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick.get(RadioTag.class);
/* 2556 */                       _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 2557 */                       _jspx_th_html_005fradio_005f2.setParent(null);
/*      */                       
/* 2559 */                       _jspx_th_html_005fradio_005f2.setProperty("masSelectType");
/*      */                       
/* 2561 */                       _jspx_th_html_005fradio_005f2.setOnclick("javascript:showAsPerSelection('2');");
/*      */                       
/* 2563 */                       _jspx_th_html_005fradio_005f2.setValue("2");
/* 2564 */                       int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 2565 */                       if (_jspx_eval_html_005fradio_005f2 != 0) {
/* 2566 */                         if (_jspx_eval_html_005fradio_005f2 != 1) {
/* 2567 */                           out = _jspx_page_context.pushBody();
/* 2568 */                           _jspx_th_html_005fradio_005f2.setBodyContent((BodyContent)out);
/* 2569 */                           _jspx_th_html_005fradio_005f2.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2572 */                           out.write("<span class=\"bodytext\">");
/* 2573 */                           out.print(FormatUtil.getString("am.webclient.admin.add.monitor.select.specific.mas.text"));
/* 2574 */                           out.write("</span>");
/* 2575 */                           int evalDoAfterBody = _jspx_th_html_005fradio_005f2.doAfterBody();
/* 2576 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2579 */                         if (_jspx_eval_html_005fradio_005f2 != 1) {
/* 2580 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2583 */                       if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 2584 */                         this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick.reuse(_jspx_th_html_005fradio_005f2); return;
/*      */                       }
/*      */                       
/* 2587 */                       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick.reuse(_jspx_th_html_005fradio_005f2);
/* 2588 */                       out.write("</td></tr>\n\t\t\t\t</table> \n\t\t\t</td>\n\t\t</tr>        \n\t\t<tr id=\"masDomainListBox\" name=\"masDomainListBox\">\n\t\t\t");
/*      */                       
/* 2590 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2591 */                       _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2592 */                       _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */                       
/* 2594 */                       _jspx_th_logic_005fnotEmpty_005f0.setName("managedServerDomainList");
/* 2595 */                       int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2596 */                       if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                         for (;;) {
/* 2598 */                           out.write("\n\t\t    <td class=\"label-align\" style=\"width:25%\">");
/* 2599 */                           out.print(FormatUtil.getString("am.webclient.admin.add.monitor.select.masgroup.text"));
/* 2600 */                           out.write("</td>\n\t\t    <td  align=\"left\">\n\t\t\t\t<table width=\"100%\" BORDER=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t<tr height=\"0\">\n\t\t\t\t\t\t<td width=\"25%\" style=\"padding-left:0px;padding-bottom:5px\" class=\"z-index-overwrite\">\n\t\t\t\t\t\t\t");
/*      */                           
/* 2602 */                           SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 2603 */                           _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2604 */                           _jspx_th_html_005fselect_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                           
/* 2606 */                           _jspx_th_html_005fselect_005f0.setProperty("masGroupName");
/*      */                           
/* 2608 */                           _jspx_th_html_005fselect_005f0.setStyleClass("formtext chosenselectmasgroup");
/*      */                           
/* 2610 */                           _jspx_th_html_005fselect_005f0.setStyle("width:255px");
/* 2611 */                           int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2612 */                           if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2613 */                             if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2614 */                               out = _jspx_page_context.pushBody();
/* 2615 */                               _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2616 */                               _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2619 */                               out.write("\n\t\t\t\t\t      \t\t");
/*      */                               
/* 2621 */                               OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2622 */                               _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2623 */                               _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                               
/* 2625 */                               _jspx_th_html_005foption_005f0.setValue("-");
/* 2626 */                               int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2627 */                               if (_jspx_eval_html_005foption_005f0 != 0) {
/* 2628 */                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2629 */                                   out = _jspx_page_context.pushBody();
/* 2630 */                                   _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 2631 */                                   _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 2634 */                                   out.print(FormatUtil.getString("am.webclient.admin.add.mas.select.masgroup.text"));
/* 2635 */                                   int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 2636 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 2639 */                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2640 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 2643 */                               if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2644 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                               }
/*      */                               
/* 2647 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 2648 */                               out.write("\n\t\t\t\t\t      \t\t");
/*      */                               
/* 2650 */                               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2651 */                               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2652 */                               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                               
/* 2654 */                               _jspx_th_logic_005fiterate_005f0.setName("managedServerDomainList");
/*      */                               
/* 2656 */                               _jspx_th_logic_005fiterate_005f0.setId("row1");
/*      */                               
/* 2658 */                               _jspx_th_logic_005fiterate_005f0.setIndexId("k");
/*      */                               
/* 2660 */                               _jspx_th_logic_005fiterate_005f0.setType("java.lang.String");
/* 2661 */                               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2662 */                               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2663 */                                 String row1 = null;
/* 2664 */                                 Integer k = null;
/* 2665 */                                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2666 */                                   out = _jspx_page_context.pushBody();
/* 2667 */                                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2668 */                                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                 }
/* 2670 */                                 row1 = (String)_jspx_page_context.findAttribute("row1");
/* 2671 */                                 k = (Integer)_jspx_page_context.findAttribute("k");
/*      */                                 for (;;) {
/* 2673 */                                   out.write("\n\t\t\t\t\t      \t\t\t");
/*      */                                   
/* 2675 */                                   OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2676 */                                   _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2677 */                                   _jspx_th_html_005foption_005f1.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                   
/* 2679 */                                   _jspx_th_html_005foption_005f1.setValue(row1);
/* 2680 */                                   int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2681 */                                   if (_jspx_eval_html_005foption_005f1 != 0) {
/* 2682 */                                     if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2683 */                                       out = _jspx_page_context.pushBody();
/* 2684 */                                       _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 2685 */                                       _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 2688 */                                       out.print(row1);
/* 2689 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 2690 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 2693 */                                     if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2694 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 2697 */                                   if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2698 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                   }
/*      */                                   
/* 2701 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 2702 */                                   out.write("\n\t\t\t\t\t      \t\t");
/* 2703 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2704 */                                   row1 = (String)_jspx_page_context.findAttribute("row1");
/* 2705 */                                   k = (Integer)_jspx_page_context.findAttribute("k");
/* 2706 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 2709 */                                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2710 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 2713 */                               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2714 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                               }
/*      */                               
/* 2717 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2718 */                               out.write(" \n\t\t\t\t\t      \t");
/* 2719 */                               int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 2720 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 2723 */                             if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2724 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 2727 */                           if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 2728 */                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                           }
/*      */                           
/* 2731 */                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 2732 */                           out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t");
/* 2733 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2734 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2738 */                       if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2739 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                       }
/*      */                       
/* 2742 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2743 */                       out.write(32);
/* 2744 */                       out.write("\n\t\t\t");
/*      */                       
/* 2746 */                       EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2747 */                       _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2748 */                       _jspx_th_logic_005fempty_005f0.setParent(null);
/*      */                       
/* 2750 */                       _jspx_th_logic_005fempty_005f0.setName("managedServerDomainList");
/* 2751 */                       int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2752 */                       if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                         for (;;) {
/* 2754 */                           out.write("\n\t\t    <td align=\"left\" colspan=\"2\" style=\"padding-left:230px\">");
/* 2755 */                           out.print(FormatUtil.getString("am.webclient.admin.add.monitor.no.masgroup.available.text"));
/* 2756 */                           out.write("</td>\n\t\t    ");
/* 2757 */                           int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2758 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2762 */                       if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2763 */                         this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                       }
/*      */                       
/* 2766 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2767 */                       out.write("\t\t\t\t\n\t\t</tr>\n\t\t<tr id=\"masListBox\" name=\"masListBox\">\n\t\t    <td class=\"label-align\" style=\"width:25%\">");
/* 2768 */                       out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.mas.text"));
/* 2769 */                       out.write("</td>\n\t\t    <td  align=\"left\">\n\t\t\t\t<table width=\"100%\" BORDER=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t<tr height=\"0\">\n\t\t\t\t\t\t<td width=\"25%\" style=\"padding-left:0px;padding-bottom:5px\" class=\"z-index-overwrite\">\n\t\t\t\t\t\t\t");
/*      */                       
/* 2771 */                       SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 2772 */                       _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 2773 */                       _jspx_th_html_005fselect_005f1.setParent(null);
/*      */                       
/* 2775 */                       _jspx_th_html_005fselect_005f1.setProperty("selectedServer");
/*      */                       
/* 2777 */                       _jspx_th_html_005fselect_005f1.setStyleClass("formtext chosenselectmas");
/*      */                       
/* 2779 */                       _jspx_th_html_005fselect_005f1.setOnchange("javascript:loadMGroupsForMAS()");
/*      */                       
/* 2781 */                       _jspx_th_html_005fselect_005f1.setStyle("width:255px");
/* 2782 */                       int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 2783 */                       if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 2784 */                         if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 2785 */                           out = _jspx_page_context.pushBody();
/* 2786 */                           _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 2787 */                           _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2790 */                           out.write("\n\t\t\t\t\t      \t\t");
/*      */                           
/* 2792 */                           OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2793 */                           _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 2794 */                           _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f1);
/*      */                           
/* 2796 */                           _jspx_th_html_005foption_005f2.setValue("-");
/* 2797 */                           int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 2798 */                           if (_jspx_eval_html_005foption_005f2 != 0) {
/* 2799 */                             if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2800 */                               out = _jspx_page_context.pushBody();
/* 2801 */                               _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 2802 */                               _jspx_th_html_005foption_005f2.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2805 */                               out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.value.mas.text"));
/* 2806 */                               int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 2807 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 2810 */                             if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2811 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 2814 */                           if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 2815 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                           }
/*      */                           
/* 2818 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 2819 */                           out.write("\n\t\t\t\t\t      \t\t");
/*      */                           
/* 2821 */                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2822 */                           _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2823 */                           _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_html_005fselect_005f1);
/*      */                           
/* 2825 */                           _jspx_th_logic_005fnotEmpty_005f1.setName("managedServerList");
/* 2826 */                           int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2827 */                           if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                             for (;;) {
/* 2829 */                               out.write(" \n\t\t\t\t\t      \t\t");
/*      */                               
/* 2831 */                               IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2832 */                               _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 2833 */                               _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                               
/* 2835 */                               _jspx_th_logic_005fiterate_005f1.setName("managedServerList");
/*      */                               
/* 2837 */                               _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                               
/* 2839 */                               _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                               
/* 2841 */                               _jspx_th_logic_005fiterate_005f1.setType("java.util.HashMap");
/* 2842 */                               int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 2843 */                               if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 2844 */                                 HashMap row = null;
/* 2845 */                                 Integer j = null;
/* 2846 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2847 */                                   out = _jspx_page_context.pushBody();
/* 2848 */                                   _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 2849 */                                   _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                 }
/* 2851 */                                 row = (HashMap)_jspx_page_context.findAttribute("row");
/* 2852 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                 for (;;) {
/* 2854 */                                   out.write("\n\t\t\t\t\t      \t\t\t");
/*      */                                   
/* 2856 */                                   OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2857 */                                   _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 2858 */                                   _jspx_th_html_005foption_005f3.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                   
/* 2860 */                                   _jspx_th_html_005foption_005f3.setValue((String)row.get("SERVERID"));
/* 2861 */                                   int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 2862 */                                   if (_jspx_eval_html_005foption_005f3 != 0) {
/* 2863 */                                     if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2864 */                                       out = _jspx_page_context.pushBody();
/* 2865 */                                       _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 2866 */                                       _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 2869 */                                       out.print((String)row.get("DISPLAYNAME") + " (" + (String)row.get("LOADFACTOR") + ")");
/* 2870 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 2871 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 2874 */                                     if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2875 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 2878 */                                   if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 2879 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                   }
/*      */                                   
/* 2882 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 2883 */                                   out.write("\n\t\t\t\t\t      \t\t");
/* 2884 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 2885 */                                   row = (HashMap)_jspx_page_context.findAttribute("row");
/* 2886 */                                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 2887 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 2890 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2891 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 2894 */                               if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 2895 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                               }
/*      */                               
/* 2898 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 2899 */                               out.write(" \n\t\t\t\t\t      \t\t");
/* 2900 */                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2901 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2905 */                           if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2906 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                           }
/*      */                           
/* 2909 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2910 */                           out.write(32);
/* 2911 */                           out.write("\n\t\t\t\t\t      \t");
/* 2912 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 2913 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2916 */                         if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 2917 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2920 */                       if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 2921 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                       }
/*      */                       
/* 2924 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 2925 */                       out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\t\t\n\t</table>\n");
/*      */                     }
/*      */                     
/* 2928 */                     if (showDetailsOf.equals("monitorGroups"))
/*      */                     {
/*      */ 
/* 2931 */                       out.write("\n\t    \t<TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"6\" CELLSPACING=\"0\" class=\"lrborder ");
/* 2932 */                       out.print(hideStyle);
/* 2933 */                       out.write("\" CELLPADING=\"0\">\n\t        <tr>\n\t          <td height=\"28\" colspan=\"3\" class=\"tablebottom bodytextbold\" style=\"height:30px;\">");
/* 2934 */                       out.print(FormatUtil.getString("am.webclient.newscript.associatemonitorinstance.text"));
/* 2935 */                       out.write(32);
/* 2936 */                       out.print(MGSTR);
/* 2937 */                       out.write("</td>\n\t        </tr>        \t      \n  ");
/*      */                       
/* 2939 */                       if (EnterpriseUtil.isIt360MSPEdition())
/*      */                       {
/* 2941 */                         out.write("\n\t        <tr><td style=\"height:25px;\"></td></tr>\n\t\t    <tr>\n\t\t    \t<td width=\"25%\" height=\"28\" valign=\"middle\" class=\"bodytext\">");
/* 2942 */                         out.print(FormatUtil.getString("it360.sp.customermgmt.customer.txt", new String[] { MGSTR }));
/* 2943 */                         out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t    <td height=\"28\" align=\"left\" >\n\t\t\t\t\t");
/*      */                         
/* 2945 */                         SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 2946 */                         _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 2947 */                         _jspx_th_html_005fselect_005f2.setParent(null);
/*      */                         
/* 2949 */                         _jspx_th_html_005fselect_005f2.setProperty("organization");
/*      */                         
/* 2951 */                         _jspx_th_html_005fselect_005f2.setStyleClass("formtext");
/*      */                         
/* 2953 */                         _jspx_th_html_005fselect_005f2.setOnchange("javascript:loadSite()");
/* 2954 */                         int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 2955 */                         if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 2956 */                           if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 2957 */                             out = _jspx_page_context.pushBody();
/* 2958 */                             _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 2959 */                             _jspx_th_html_005fselect_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2962 */                             out.write("\n\t\t\t      \t");
/*      */                             
/* 2964 */                             OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2965 */                             _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 2966 */                             _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f2);
/*      */                             
/* 2968 */                             _jspx_th_html_005foption_005f4.setValue("-");
/* 2969 */                             int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 2970 */                             if (_jspx_eval_html_005foption_005f4 != 0) {
/* 2971 */                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2972 */                                 out = _jspx_page_context.pushBody();
/* 2973 */                                 _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 2974 */                                 _jspx_th_html_005foption_005f4.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2977 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.customer", new String[] { MGSTR }));
/* 2978 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 2979 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2982 */                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2983 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2986 */                             if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 2987 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                             }
/*      */                             
/* 2990 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 2991 */                             out.write("\n\t\t\t      \t");
/*      */                             
/* 2993 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2994 */                             _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 2995 */                             _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_html_005fselect_005f2);
/*      */                             
/* 2997 */                             _jspx_th_logic_005fnotEmpty_005f2.setName("customers");
/* 2998 */                             int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 2999 */                             if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                               for (;;) {
/* 3001 */                                 out.write(32);
/*      */                                 
/* 3003 */                                 IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3004 */                                 _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 3005 */                                 _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                 
/* 3007 */                                 _jspx_th_logic_005fiterate_005f2.setName("customers");
/*      */                                 
/* 3009 */                                 _jspx_th_logic_005fiterate_005f2.setId("row");
/*      */                                 
/* 3011 */                                 _jspx_th_logic_005fiterate_005f2.setIndexId("j");
/*      */                                 
/* 3013 */                                 _jspx_th_logic_005fiterate_005f2.setType("java.util.ArrayList");
/* 3014 */                                 int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 3015 */                                 if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 3016 */                                   ArrayList row = null;
/* 3017 */                                   Integer j = null;
/* 3018 */                                   if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 3019 */                                     out = _jspx_page_context.pushBody();
/* 3020 */                                     _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 3021 */                                     _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                                   }
/* 3023 */                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3024 */                                   j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                   for (;;) {
/* 3026 */                                     out.write("\n\t\t\t      \t");
/*      */                                     
/* 3028 */                                     OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3029 */                                     _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 3030 */                                     _jspx_th_html_005foption_005f5.setParent(_jspx_th_logic_005fiterate_005f2);
/*      */                                     
/* 3032 */                                     _jspx_th_html_005foption_005f5.setValue((String)row.get(1));
/* 3033 */                                     int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 3034 */                                     if (_jspx_eval_html_005foption_005f5 != 0) {
/* 3035 */                                       if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3036 */                                         out = _jspx_page_context.pushBody();
/* 3037 */                                         _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 3038 */                                         _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3041 */                                         out.print(row.get(0));
/* 3042 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 3043 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3046 */                                       if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3047 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3050 */                                     if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 3051 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                     }
/*      */                                     
/* 3054 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 3055 */                                     out.write("\n\t\t\t      \t");
/* 3056 */                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 3057 */                                     row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3058 */                                     j = (Integer)_jspx_page_context.findAttribute("j");
/* 3059 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3062 */                                   if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 3063 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3066 */                                 if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 3067 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                                 }
/*      */                                 
/* 3070 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 3071 */                                 out.write(32);
/* 3072 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 3073 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3077 */                             if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 3078 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                             }
/*      */                             
/* 3081 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 3082 */                             out.write(32);
/* 3083 */                             int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 3084 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3087 */                           if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3088 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3091 */                         if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 3092 */                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2); return;
/*      */                         }
/*      */                         
/* 3095 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/* 3096 */                         out.write("\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td width=\"25%\" height=\"28\" valign=\"middle\" class=\"bodytext\">");
/* 3097 */                         out.print(FormatUtil.getString("it360.sp.customermgmt.site.txt", new String[] { MGSTR }));
/* 3098 */                         out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t\t<td height=\"28\" align=\"left\" >\n\t\t\t\t\t");
/*      */                         
/* 3100 */                         SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 3101 */                         _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 3102 */                         _jspx_th_html_005fselect_005f3.setParent(null);
/*      */                         
/* 3104 */                         _jspx_th_html_005fselect_005f3.setProperty("haid");
/*      */                         
/* 3106 */                         _jspx_th_html_005fselect_005f3.setStyleClass("formtext");
/*      */                         
/* 3108 */                         _jspx_th_html_005fselect_005f3.setOnchange("");
/* 3109 */                         int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 3110 */                         if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 3111 */                           if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3112 */                             out = _jspx_page_context.pushBody();
/* 3113 */                             _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 3114 */                             _jspx_th_html_005fselect_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 3117 */                             out.write("\n\t\t\t\t\t      ");
/*      */                             
/* 3119 */                             OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3120 */                             _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 3121 */                             _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f3);
/*      */                             
/* 3123 */                             _jspx_th_html_005foption_005f6.setValue("-");
/* 3124 */                             int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 3125 */                             if (_jspx_eval_html_005foption_005f6 != 0) {
/* 3126 */                               if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3127 */                                 out = _jspx_page_context.pushBody();
/* 3128 */                                 _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 3129 */                                 _jspx_th_html_005foption_005f6.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3132 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.siteGroup", new String[] { MGSTR }));
/* 3133 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 3134 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3137 */                               if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3138 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3141 */                             if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 3142 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                             }
/*      */                             
/* 3145 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 3146 */                             out.write("\n\t\t\t\t\t      ");
/*      */                             
/* 3148 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3149 */                             _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 3150 */                             _jspx_th_logic_005fnotEmpty_005f3.setParent(_jspx_th_html_005fselect_005f3);
/*      */                             
/* 3152 */                             _jspx_th_logic_005fnotEmpty_005f3.setName("applications");
/* 3153 */                             int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 3154 */                             if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */                               for (;;) {
/* 3156 */                                 out.write(32);
/*      */                                 
/* 3158 */                                 IterateTag _jspx_th_logic_005fiterate_005f3 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3159 */                                 _jspx_th_logic_005fiterate_005f3.setPageContext(_jspx_page_context);
/* 3160 */                                 _jspx_th_logic_005fiterate_005f3.setParent(_jspx_th_logic_005fnotEmpty_005f3);
/*      */                                 
/* 3162 */                                 _jspx_th_logic_005fiterate_005f3.setName("applications");
/*      */                                 
/* 3164 */                                 _jspx_th_logic_005fiterate_005f3.setId("row");
/*      */                                 
/* 3166 */                                 _jspx_th_logic_005fiterate_005f3.setIndexId("j");
/*      */                                 
/* 3168 */                                 _jspx_th_logic_005fiterate_005f3.setType("java.util.ArrayList");
/* 3169 */                                 int _jspx_eval_logic_005fiterate_005f3 = _jspx_th_logic_005fiterate_005f3.doStartTag();
/* 3170 */                                 if (_jspx_eval_logic_005fiterate_005f3 != 0) {
/* 3171 */                                   ArrayList row = null;
/* 3172 */                                   Integer j = null;
/* 3173 */                                   if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 3174 */                                     out = _jspx_page_context.pushBody();
/* 3175 */                                     _jspx_th_logic_005fiterate_005f3.setBodyContent((BodyContent)out);
/* 3176 */                                     _jspx_th_logic_005fiterate_005f3.doInitBody();
/*      */                                   }
/* 3178 */                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3179 */                                   j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                   for (;;) {
/* 3181 */                                     out.write("\n\t\t\t\t\t      ");
/*      */                                     
/* 3183 */                                     OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3184 */                                     _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 3185 */                                     _jspx_th_html_005foption_005f7.setParent(_jspx_th_logic_005fiterate_005f3);
/*      */                                     
/* 3187 */                                     _jspx_th_html_005foption_005f7.setValue((String)row.get(1));
/* 3188 */                                     int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 3189 */                                     if (_jspx_eval_html_005foption_005f7 != 0) {
/* 3190 */                                       if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3191 */                                         out = _jspx_page_context.pushBody();
/* 3192 */                                         _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 3193 */                                         _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3196 */                                         out.print(row.get(0));
/* 3197 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 3198 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3201 */                                       if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3202 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3205 */                                     if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 3206 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                     }
/*      */                                     
/* 3209 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 3210 */                                     out.write("\n\t\t\t\t\t      ");
/* 3211 */                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f3.doAfterBody();
/* 3212 */                                     row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3213 */                                     j = (Integer)_jspx_page_context.findAttribute("j");
/* 3214 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3217 */                                   if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 3218 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3221 */                                 if (_jspx_th_logic_005fiterate_005f3.doEndTag() == 5) {
/* 3222 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3); return;
/*      */                                 }
/*      */                                 
/* 3225 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3);
/* 3226 */                                 out.write(32);
/* 3227 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 3228 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3232 */                             if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 3233 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3); return;
/*      */                             }
/*      */                             
/* 3236 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 3237 */                             out.write(" \n\t\t\t\t\t");
/* 3238 */                             int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 3239 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3242 */                           if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3243 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3246 */                         if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 3247 */                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3); return;
/*      */                         }
/*      */                         
/* 3250 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3);
/* 3251 */                         out.write("\n\t      \t\t</td>\n\t      \t</tr>\n ");
/*      */                       }
/*      */                       else
/*      */                       {
/* 3255 */                         out.write("\n\t\t\t<tr height=\"35\">\n\t\t    <td width=\"25%\" style=\"padding-left:0px;\" class=\"bodytext label-align\">");
/* 3256 */                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.monitorgroupselect", new String[] { MGSTR }));
/* 3257 */                         out.write("</td>\n\t\t    <td  align=\"left\" width=\"75%\">\n\t\t\t\t<table width=\"100%\" BORDER=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t<tr height=\"35\">\n\t\t\t\t\t\t<td width=\"40%\" style=\"padding-left:0px;\" colspan=\"2\">\n      \t\t\t");
/*      */                         
/* 3259 */                         if (EnterpriseUtil.isAdminServer()) {
/* 3260 */                           Pattern p = Pattern.compile("&nbsp;");
/*      */                           
/* 3262 */                           out.write("\n\t\t\t\t\t\t\t");
/*      */                           
/* 3264 */                           SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange_005fmultiple.get(SelectTag.class);
/* 3265 */                           _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 3266 */                           _jspx_th_html_005fselect_005f4.setParent(null);
/*      */                           
/* 3268 */                           _jspx_th_html_005fselect_005f4.setProperty("haid");
/*      */                           
/* 3270 */                           _jspx_th_html_005fselect_005f4.setStyleClass("formtext chosenmultiselect");
/*      */                           
/* 3272 */                           _jspx_th_html_005fselect_005f4.setOnchange("");
/*      */                           
/* 3274 */                           _jspx_th_html_005fselect_005f4.setMultiple("true");
/*      */                           
/* 3276 */                           _jspx_th_html_005fselect_005f4.setStyle("width:300px");
/*      */                           
/* 3278 */                           _jspx_th_html_005fselect_005f4.setTabindex("2");
/* 3279 */                           int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 3280 */                           if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 3281 */                             if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 3282 */                               out = _jspx_page_context.pushBody();
/* 3283 */                               _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 3284 */                               _jspx_th_html_005fselect_005f4.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3287 */                               out.write("\n\t\t\t\t\t\t      \t\t");
/*      */                               
/* 3289 */                               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f4 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3290 */                               _jspx_th_logic_005fnotEmpty_005f4.setPageContext(_jspx_page_context);
/* 3291 */                               _jspx_th_logic_005fnotEmpty_005f4.setParent(_jspx_th_html_005fselect_005f4);
/*      */                               
/* 3293 */                               _jspx_th_logic_005fnotEmpty_005f4.setName("MonitorGroupsCreatedInAdminServer");
/* 3294 */                               int _jspx_eval_logic_005fnotEmpty_005f4 = _jspx_th_logic_005fnotEmpty_005f4.doStartTag();
/* 3295 */                               if (_jspx_eval_logic_005fnotEmpty_005f4 != 0) {
/*      */                                 for (;;) {
/* 3297 */                                   out.write(" \n\t\t\t\t\t\t      \t\t");
/*      */                                   
/* 3299 */                                   IterateTag _jspx_th_logic_005fiterate_005f4 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3300 */                                   _jspx_th_logic_005fiterate_005f4.setPageContext(_jspx_page_context);
/* 3301 */                                   _jspx_th_logic_005fiterate_005f4.setParent(_jspx_th_logic_005fnotEmpty_005f4);
/*      */                                   
/* 3303 */                                   _jspx_th_logic_005fiterate_005f4.setName("MonitorGroupsCreatedInAdminServer");
/*      */                                   
/* 3305 */                                   _jspx_th_logic_005fiterate_005f4.setId("row");
/*      */                                   
/* 3307 */                                   _jspx_th_logic_005fiterate_005f4.setIndexId("j");
/*      */                                   
/* 3309 */                                   _jspx_th_logic_005fiterate_005f4.setType("java.util.ArrayList");
/* 3310 */                                   int _jspx_eval_logic_005fiterate_005f4 = _jspx_th_logic_005fiterate_005f4.doStartTag();
/* 3311 */                                   if (_jspx_eval_logic_005fiterate_005f4 != 0) {
/* 3312 */                                     ArrayList row = null;
/* 3313 */                                     Integer j = null;
/* 3314 */                                     if (_jspx_eval_logic_005fiterate_005f4 != 1) {
/* 3315 */                                       out = _jspx_page_context.pushBody();
/* 3316 */                                       _jspx_th_logic_005fiterate_005f4.setBodyContent((BodyContent)out);
/* 3317 */                                       _jspx_th_logic_005fiterate_005f4.doInitBody();
/*      */                                     }
/* 3319 */                                     row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3320 */                                     j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                     for (;;) {
/* 3322 */                                       out.write("\n\t\t\t\t\t\t      \t\t ");
/*      */                                       
/* 3324 */                                       String mgName = (String)row.get(0);
/* 3325 */                                       String mdId = (String)row.get(1);
/* 3326 */                                       Matcher m = p.matcher(mgName);
/* 3327 */                                       int count = 0;
/* 3328 */                                       while (m.find()) {
/* 3329 */                                         count++;
/*      */                                       }
/* 3331 */                                       mgName = mgName.replaceAll("&nbsp;", "");
/*      */                                       
/* 3333 */                                       out.write("\t\t\t\t\t\t      \t\t\n\t\t\t\t\t\t      \t\t\t");
/*      */                                       
/* 3335 */                                       OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 3336 */                                       _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 3337 */                                       _jspx_th_html_005foption_005f8.setParent(_jspx_th_logic_005fiterate_005f4);
/*      */                                       
/* 3339 */                                       _jspx_th_html_005foption_005f8.setValue(mdId);
/*      */                                       
/* 3341 */                                       _jspx_th_html_005foption_005f8.setStyle("padding-left:" + count * 10 + "px");
/* 3342 */                                       int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 3343 */                                       if (_jspx_eval_html_005foption_005f8 != 0) {
/* 3344 */                                         if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3345 */                                           out = _jspx_page_context.pushBody();
/* 3346 */                                           _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 3347 */                                           _jspx_th_html_005foption_005f8.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3350 */                                           out.print(mgName);
/* 3351 */                                           int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 3352 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3355 */                                         if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3356 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3359 */                                       if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 3360 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                       }
/*      */                                       
/* 3363 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f8);
/* 3364 */                                       out.write("\n\t\t\t\t\t\t      \t\t");
/* 3365 */                                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f4.doAfterBody();
/* 3366 */                                       row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3367 */                                       j = (Integer)_jspx_page_context.findAttribute("j");
/* 3368 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3371 */                                     if (_jspx_eval_logic_005fiterate_005f4 != 1) {
/* 3372 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3375 */                                   if (_jspx_th_logic_005fiterate_005f4.doEndTag() == 5) {
/* 3376 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f4); return;
/*      */                                   }
/*      */                                   
/* 3379 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f4);
/* 3380 */                                   out.write(" \n\t\t\t\t      \t\t\t");
/* 3381 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f4.doAfterBody();
/* 3382 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3386 */                               if (_jspx_th_logic_005fnotEmpty_005f4.doEndTag() == 5) {
/* 3387 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4); return;
/*      */                               }
/*      */                               
/* 3390 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 3391 */                               out.write(" \n\t\t\t\t      \t\t");
/* 3392 */                               int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 3393 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3396 */                             if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 3397 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3400 */                           if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 3401 */                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange_005fmultiple.reuse(_jspx_th_html_005fselect_005f4); return;
/*      */                           }
/*      */                           
/* 3404 */                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange_005fmultiple.reuse(_jspx_th_html_005fselect_005f4);
/* 3405 */                           out.write("\n\t      \t\t");
/*      */                         }
/*      */                         else {
/* 3408 */                           Pattern p = Pattern.compile("&nbsp;");
/*      */                           
/* 3410 */                           out.write("\n\t\t\t\t\t\t\t");
/*      */                           
/* 3412 */                           SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange_005fmultiple.get(SelectTag.class);
/* 3413 */                           _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 3414 */                           _jspx_th_html_005fselect_005f5.setParent(null);
/*      */                           
/* 3416 */                           _jspx_th_html_005fselect_005f5.setProperty("haid");
/*      */                           
/* 3418 */                           _jspx_th_html_005fselect_005f5.setStyleClass("formtext chosenmultiselect");
/*      */                           
/* 3420 */                           _jspx_th_html_005fselect_005f5.setOnchange("");
/*      */                           
/* 3422 */                           _jspx_th_html_005fselect_005f5.setMultiple("true");
/*      */                           
/* 3424 */                           _jspx_th_html_005fselect_005f5.setStyle("width:300px");
/*      */                           
/* 3426 */                           _jspx_th_html_005fselect_005f5.setTabindex("2");
/* 3427 */                           int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 3428 */                           if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 3429 */                             if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 3430 */                               out = _jspx_page_context.pushBody();
/* 3431 */                               _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 3432 */                               _jspx_th_html_005fselect_005f5.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3435 */                               out.write("\n\t\t\t\t\t\t      \t\t");
/*      */                               
/* 3437 */                               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f5 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3438 */                               _jspx_th_logic_005fnotEmpty_005f5.setPageContext(_jspx_page_context);
/* 3439 */                               _jspx_th_logic_005fnotEmpty_005f5.setParent(_jspx_th_html_005fselect_005f5);
/*      */                               
/* 3441 */                               _jspx_th_logic_005fnotEmpty_005f5.setName("applications");
/* 3442 */                               int _jspx_eval_logic_005fnotEmpty_005f5 = _jspx_th_logic_005fnotEmpty_005f5.doStartTag();
/* 3443 */                               if (_jspx_eval_logic_005fnotEmpty_005f5 != 0) {
/*      */                                 for (;;) {
/* 3445 */                                   out.write(" \n\t\t\t\t\t\t      \t\t");
/*      */                                   
/* 3447 */                                   IterateTag _jspx_th_logic_005fiterate_005f5 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3448 */                                   _jspx_th_logic_005fiterate_005f5.setPageContext(_jspx_page_context);
/* 3449 */                                   _jspx_th_logic_005fiterate_005f5.setParent(_jspx_th_logic_005fnotEmpty_005f5);
/*      */                                   
/* 3451 */                                   _jspx_th_logic_005fiterate_005f5.setName("applications");
/*      */                                   
/* 3453 */                                   _jspx_th_logic_005fiterate_005f5.setId("row");
/*      */                                   
/* 3455 */                                   _jspx_th_logic_005fiterate_005f5.setIndexId("j");
/*      */                                   
/* 3457 */                                   _jspx_th_logic_005fiterate_005f5.setType("java.util.ArrayList");
/* 3458 */                                   int _jspx_eval_logic_005fiterate_005f5 = _jspx_th_logic_005fiterate_005f5.doStartTag();
/* 3459 */                                   if (_jspx_eval_logic_005fiterate_005f5 != 0) {
/* 3460 */                                     ArrayList row = null;
/* 3461 */                                     Integer j = null;
/* 3462 */                                     if (_jspx_eval_logic_005fiterate_005f5 != 1) {
/* 3463 */                                       out = _jspx_page_context.pushBody();
/* 3464 */                                       _jspx_th_logic_005fiterate_005f5.setBodyContent((BodyContent)out);
/* 3465 */                                       _jspx_th_logic_005fiterate_005f5.doInitBody();
/*      */                                     }
/* 3467 */                                     row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3468 */                                     j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                     for (;;) {
/* 3470 */                                       out.write("\n\t\t\t\t\t\t      \t\t ");
/*      */                                       
/* 3472 */                                       String mgName = (String)row.get(0);
/* 3473 */                                       String mdId = (String)row.get(1);
/* 3474 */                                       Matcher m = p.matcher(mgName);
/* 3475 */                                       int count = 0;
/* 3476 */                                       while (m.find()) {
/* 3477 */                                         count++;
/*      */                                       }
/* 3479 */                                       mgName = mgName.replaceAll("&nbsp;", "");
/*      */                                       
/* 3481 */                                       out.write("\n\t\t\t\t\t\t      \t\t\t");
/*      */                                       
/* 3483 */                                       OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 3484 */                                       _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 3485 */                                       _jspx_th_html_005foption_005f9.setParent(_jspx_th_logic_005fiterate_005f5);
/*      */                                       
/* 3487 */                                       _jspx_th_html_005foption_005f9.setValue(mdId);
/*      */                                       
/* 3489 */                                       _jspx_th_html_005foption_005f9.setStyle("padding-left:" + count * 10 + "px");
/* 3490 */                                       int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 3491 */                                       if (_jspx_eval_html_005foption_005f9 != 0) {
/* 3492 */                                         if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3493 */                                           out = _jspx_page_context.pushBody();
/* 3494 */                                           _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 3495 */                                           _jspx_th_html_005foption_005f9.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3498 */                                           out.print(mgName);
/* 3499 */                                           int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 3500 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3503 */                                         if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3504 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3507 */                                       if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 3508 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                       }
/*      */                                       
/* 3511 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f9);
/* 3512 */                                       out.write("\n\t\t\t\t\t\t      \t\t");
/* 3513 */                                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f5.doAfterBody();
/* 3514 */                                       row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3515 */                                       j = (Integer)_jspx_page_context.findAttribute("j");
/* 3516 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3519 */                                     if (_jspx_eval_logic_005fiterate_005f5 != 1) {
/* 3520 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3523 */                                   if (_jspx_th_logic_005fiterate_005f5.doEndTag() == 5) {
/* 3524 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f5); return;
/*      */                                   }
/*      */                                   
/* 3527 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f5);
/* 3528 */                                   out.write("\n\t\t\t\t      \t\t\t");
/* 3529 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f5.doAfterBody();
/* 3530 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3534 */                               if (_jspx_th_logic_005fnotEmpty_005f5.doEndTag() == 5) {
/* 3535 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f5); return;
/*      */                               }
/*      */                               
/* 3538 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f5);
/* 3539 */                               out.write("\n\t\t\t\t      \t\t");
/* 3540 */                               int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 3541 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3544 */                             if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 3545 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3548 */                           if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 3549 */                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange_005fmultiple.reuse(_jspx_th_html_005fselect_005f5); return;
/*      */                           }
/*      */                           
/* 3552 */                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange_005fmultiple.reuse(_jspx_th_html_005fselect_005f5);
/* 3553 */                           out.write(" \n\t      \t\t");
/*      */                         }
/*      */                         
/*      */ 
/* 3557 */                         out.write("\t\t      \t\t\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td  width=\"60%\">\n\t\t\t\t\t\t\t<table BORDER=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td colspan=\"2\">\n\t\t\t\t\t\t\t\t\t<div id=\"group\" width=\"15%\" nowrap=\"nowrap\" style=\"padding-left:20px;white-space: nowrap;\"><a href=\"javascript:void(0)\" class=\"staticlinks\" onClick=\"javascript:hideDiv('group');hideDiv('groupmessage');showDiv('creategroup');resetname(); return false;\">");
/* 3558 */                         out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.browsertitle"));
/* 3559 */                         out.write("</a></div>\n\t\t\t\t\t\t\t\t\t<div id=\"creategroup\" style=\"display:none;padding-left:15px;\">\n\t\t\t\t\t\t\t\t\t\t<span class=\"bodytext\">");
/* 3560 */                         out.print(FormatUtil.getString("webclient.map.mapsymboldetails.groupname"));
/* 3561 */                         out.write(":</span>\n\t\t\t\t\t\t\t\t\t\t");
/* 3562 */                         if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*      */                           return;
/* 3564 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t<span id=\"separatediv_save_4\" style=\"vertical-align: middle; display: inline;padding-top:5px\"><a href=\"javascript:void(0)\" onclick=\"javascript:createGroup();\"><img title=\"");
/* 3565 */                         out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 3566 */                         out.write("\" src=\"/images/icon_customfield_save.gif\" class=\"icon_custon_save\" border=\"0\"></a></span>\n\t\t\t\t\t\t\t\t\t\t<span id=\"separatediv_close_4\" style=\"vertical-align: middle; display: inline;padding-top:5px\"><a href=\"javascript:void(0)\" onclick=\"javascript:hideDiv('creategroup'); showDiv('group'); return false;\"> <img title=\"");
/* 3567 */                         out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3568 */                         out.write("\" src=\"/images/icon_customfield_delete.gif\" class=\"icon_custon_del\" border=\"0\"></a></span>\n\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t<div id=\"groupmessage\" style=\"display:block; padding-left:20px;\" class='error-text'></div>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t\t</table>\n");
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/* 3574 */             } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3575 */         out = _jspx_out;
/* 3576 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3577 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3578 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3581 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3587 */     PageContext pageContext = _jspx_page_context;
/* 3588 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3590 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3591 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3592 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 3594 */     _jspx_th_c_005fif_005f0.setTest("${not empty AMActionForm}");
/* 3595 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3596 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3598 */         out.write("\n\t\t\t\t\t");
/* 3599 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3600 */           return true;
/* 3601 */         out.write("\n\t\t\t\t\t");
/* 3602 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3603 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3607 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3608 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3609 */       return true;
/*      */     }
/* 3611 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3612 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3617 */     PageContext pageContext = _jspx_page_context;
/* 3618 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3620 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3621 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3622 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3624 */     _jspx_th_c_005fset_005f0.setTarget("${AMActionForm}");
/*      */     
/* 3626 */     _jspx_th_c_005fset_005f0.setProperty("masSelectType");
/*      */     
/* 3628 */     _jspx_th_c_005fset_005f0.setValue("0");
/* 3629 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3630 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3631 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3632 */       return true;
/*      */     }
/* 3634 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3635 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3640 */     PageContext pageContext = _jspx_page_context;
/* 3641 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3643 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3644 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 3645 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 3646 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 3647 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 3649 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 3650 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 3651 */           return true;
/* 3652 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 3653 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 3654 */           return true;
/* 3655 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 3656 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3657 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3661 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3662 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3663 */       return true;
/*      */     }
/* 3665 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3666 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3671 */     PageContext pageContext = _jspx_page_context;
/* 3672 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3674 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3675 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3676 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 3678 */     _jspx_th_c_005fwhen_005f0.setTest("${requestScope.type=='UrlMonitor'}");
/* 3679 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3680 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 3682 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 3683 */         if (_jspx_meth_html_005ftext_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 3684 */           return true;
/* 3685 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 3686 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3687 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3691 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3692 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3693 */       return true;
/*      */     }
/* 3695 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3696 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3701 */     PageContext pageContext = _jspx_page_context;
/* 3702 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3704 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 3705 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3706 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 3708 */     _jspx_th_html_005ftext_005f0.setProperty("groupname");
/*      */     
/* 3710 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/* 3711 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3712 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3713 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3714 */       return true;
/*      */     }
/* 3716 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3717 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3722 */     PageContext pageContext = _jspx_page_context;
/* 3723 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3725 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3726 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3727 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 3728 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3729 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 3731 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<input name=\"groupname\" type=\"text\" class=\"formtext\">\n\t\t\t\t\t\t\t\t\t\t\t");
/* 3732 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3733 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3737 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3738 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3739 */       return true;
/*      */     }
/* 3741 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3742 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\includes\ShowGroupsDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */