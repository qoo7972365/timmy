/*      */ package org.apache.jsp.jsp.mssql;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.PerformanceBean;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.mssql.bean.MsSqlGraphs;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import java.net.InetAddress;
/*      */ import java.sql.ResultSet;
/*      */ import java.text.DecimalFormat;
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
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class overview_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*   69 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
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
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
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
/* 2042 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
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
/* 2178 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2184 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/* 2185 */   static { _jspx_dependants.put("/jsp/mssql/../MyField_div.jsp", Long.valueOf(1473429417000L));
/* 2186 */     _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2187 */     _jspx_dependants.put("/jsp/mssql/../MyField_trstrip.jsp", Long.valueOf(1473429417000L));
/* 2188 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2213 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2217 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2235 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2239 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2246 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2247 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2248 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2249 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2250 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2251 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2252 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2254 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.release();
/* 2255 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2262 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2265 */     JspWriter out = null;
/* 2266 */     Object page = this;
/* 2267 */     JspWriter _jspx_out = null;
/* 2268 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2272 */       response.setContentType("text/html;charset=UTF-8");
/* 2273 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2275 */       _jspx_page_context = pageContext;
/* 2276 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2277 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2278 */       session = pageContext.getSession();
/* 2279 */       out = pageContext.getOut();
/* 2280 */       _jspx_out = out;
/*      */       
/* 2282 */       out.write("<!--$Id$-->\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n");
/*      */       
/* 2284 */       request.setAttribute("HelpKey", "Monitors MSSQL Details");
/*      */       
/* 2286 */       out.write(10);
/* 2287 */       out.write(10);
/* 2288 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2289 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2290 */       if (wlsGraph == null) {
/* 2291 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2292 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2294 */       out.write(10);
/* 2295 */       MsSqlGraphs mssqlgraph = null;
/* 2296 */       mssqlgraph = (MsSqlGraphs)_jspx_page_context.getAttribute("mssqlgraph", 1);
/* 2297 */       if (mssqlgraph == null) {
/* 2298 */         mssqlgraph = new MsSqlGraphs();
/* 2299 */         _jspx_page_context.setAttribute("mssqlgraph", mssqlgraph, 1);
/*      */       }
/* 2301 */       out.write(10);
/* 2302 */       PerformanceBean perfgraph = null;
/* 2303 */       perfgraph = (PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2304 */       if (perfgraph == null) {
/* 2305 */         perfgraph = new PerformanceBean();
/* 2306 */         _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */       }
/* 2308 */       out.write("\n\n\n\n\n\n\n\n\n");
/* 2309 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2311 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2312 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2313 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2315 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2317 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2319 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2321 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2322 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2323 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2324 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2327 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2328 */         String available = null;
/* 2329 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2330 */         out.write(10);
/*      */         
/* 2332 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2333 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2334 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2336 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2338 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2340 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2342 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2343 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2344 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2345 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2348 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2349 */           String unavailable = null;
/* 2350 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2351 */           out.write(10);
/*      */           
/* 2353 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2354 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2355 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2357 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2359 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2361 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2363 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2364 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2365 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2366 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2369 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2370 */             String unmanaged = null;
/* 2371 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2372 */             out.write(10);
/*      */             
/* 2374 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2375 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2376 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2378 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2380 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2382 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2384 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2385 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2386 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2387 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2390 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2391 */               String scheduled = null;
/* 2392 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2393 */               out.write(10);
/*      */               
/* 2395 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2396 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2397 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2399 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2401 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2403 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2405 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2406 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2407 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2408 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2411 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2412 */                 String critical = null;
/* 2413 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2414 */                 out.write(10);
/*      */                 
/* 2416 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2417 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2418 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2420 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2422 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2424 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2426 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2427 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2428 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2429 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2432 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2433 */                   String clear = null;
/* 2434 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2435 */                   out.write(10);
/*      */                   
/* 2437 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2438 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2439 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2441 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2443 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2445 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2447 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2448 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2449 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2450 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2453 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2454 */                     String warning = null;
/* 2455 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2456 */                     out.write(10);
/* 2457 */                     out.write(10);
/*      */                     
/* 2459 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2460 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2462 */                     out.write(10);
/* 2463 */                     out.write(10);
/* 2464 */                     out.write(10);
/* 2465 */                     out.write("\n\n\n\n\n\n\n\n\n<script>\n");
/* 2466 */                     if (_jspx_meth_logic_005fnotEmpty_005f0(_jspx_page_context))
/*      */                       return;
/* 2468 */                     out.write(" \n</script>\n\n");
/*      */                     
/*      */ 
/* 2471 */                     String name = null;
/* 2472 */                     float downtime = 0.0F;
/*      */                     
/* 2474 */                     Properties data = (Properties)request.getAttribute("performance");
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2479 */                     name = (String)request.getAttribute("name");
/* 2480 */                     String haid = null;
/* 2481 */                     String appname = null;
/* 2482 */                     String search = null;
/* 2483 */                     String tab = "1";
/* 2484 */                     String bgcolour = "class=\"whitegrayborder\"";
/* 2485 */                     String resourceid = (String)request.getAttribute("resourceid");
/* 2486 */                     mssqlgraph.setresid(Integer.parseInt(resourceid));
/* 2487 */                     haid = (String)request.getAttribute("haid");
/* 2488 */                     appname = (String)request.getAttribute("appName");
/* 2489 */                     String details = request.getParameter("details");
/* 2490 */                     String showdata = (String)request.getAttribute("showdata");
/*      */                     
/* 2492 */                     if (details == null) {
/* 2493 */                       details = "Availability";
/*      */                     }
/* 2495 */                     String displayname = null;
/* 2496 */                     ArrayList attribIDs = new ArrayList();
/* 2497 */                     ArrayList resIDs = new ArrayList();
/* 2498 */                     for (int i = 3100; i <= 3151; i++)
/*      */                     {
/* 2500 */                       attribIDs.add("" + i);
/*      */                     }
/* 2502 */                     for (int i = 3158; i <= 3161; i++) {
/* 2503 */                       attribIDs.add("" + i);
/*      */                     }
/* 2505 */                     attribIDs.add("3164");
/* 2506 */                     for (int i = 3865; i <= 3869; i++) {
/* 2507 */                       attribIDs.add("" + i);
/*      */                     }
/* 2509 */                     for (int i = 3809; i <= 3812; i++) {
/* 2510 */                       attribIDs.add("" + i);
/*      */                     }
/* 2512 */                     resIDs.add(resourceid);
/*      */                     
/* 2514 */                     Hashtable dbinfo = (Hashtable)request.getAttribute("dbdetails");
/* 2515 */                     for (Enumeration e = dbinfo.keys(); e.hasMoreElements();)
/*      */                     {
/* 2517 */                       String dbname = (String)e.nextElement();
/* 2518 */                       Properties dbprop = (Properties)dbinfo.get(dbname);
/* 2519 */                       resIDs.add(dbprop.getProperty("DBID"));
/*      */                     }
/*      */                     
/* 2522 */                     DecimalFormat df = new DecimalFormat("#.##");
/*      */                     
/* 2524 */                     ArrayList jobDetails1 = (ArrayList)request.getAttribute("JOBS");
/* 2525 */                     if (jobDetails1 != null)
/*      */                     {
/* 2527 */                       for (int j = 0; j < jobDetails1.size(); j++)
/*      */                       {
/* 2529 */                         Properties jobProperties1 = new Properties();
/* 2530 */                         jobProperties1 = (Properties)jobDetails1.get(j);
/*      */                         
/* 2532 */                         resIDs.add(jobProperties1.getProperty("JOBID"));
/*      */                       }
/*      */                     }
/* 2535 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2536 */                     System.out.println("Alerts for MSSQL " + alert);
/* 2537 */                     HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/*      */                     
/* 2539 */                     if (request.getAttribute("displayname") == null)
/*      */                     {
/* 2541 */                       displayname = request.getParameter("resourcename");
/*      */                     }
/*      */                     else
/*      */                     {
/* 2545 */                       displayname = (String)request.getAttribute("displayname");
/*      */                     }
/*      */                     
/* 2548 */                     String redirect = "/MSSql.do?name=" + name + "&haid=" + haid + "&appName=" + appname + "&resourceid=" + resourceid + "&details=" + details + "&resourcename=" + displayname;
/* 2549 */                     String encodeurl = java.net.URLEncoder.encode(redirect);
/* 2550 */                     request.setAttribute("configured", "true");
/* 2551 */                     wlsGraph.setParam(resourceid, "AVAILABILITY");
/* 2552 */                     perfgraph.setresourceid(Integer.parseInt(resourceid));
/* 2553 */                     perfgraph.setEntity("Response Time");
/* 2554 */                     if (details.equals("Availability"))
/*      */                     {
/* 2556 */                       out.write(10);
/* 2557 */                       out.write(10);
/* 2558 */                       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */                         return;
/* 2560 */                       out.write(10);
/* 2561 */                       out.write(10);
/* 2562 */                       if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*      */                         return;
/* 2564 */                       out.write(10);
/* 2565 */                       out.write(10);
/* 2566 */                       if (_jspx_meth_c_005fset_005f2(_jspx_page_context))
/*      */                         return;
/* 2568 */                       out.write(10);
/* 2569 */                       if (_jspx_meth_c_005fset_005f3(_jspx_page_context))
/*      */                         return;
/* 2571 */                       out.write(10);
/* 2572 */                       out.write(10);
/* 2573 */                       if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*      */                         return;
/* 2575 */                       out.write(10);
/* 2576 */                       out.write(10);
/* 2577 */                       if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
/*      */                         return;
/* 2579 */                       out.write(10);
/* 2580 */                       out.write(10);
/*      */                       
/* 2582 */                       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2583 */                       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2584 */                       _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2585 */                       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2586 */                       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                         for (;;) {
/* 2588 */                           out.write(10);
/*      */                           
/* 2590 */                           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2591 */                           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2592 */                           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                           
/* 2594 */                           _jspx_th_c_005fwhen_005f0.setTest("${ param.alert!='true' && param.all!='true' }");
/* 2595 */                           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2596 */                           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                             for (;;) {
/* 2598 */                               out.write("\n<table width=\"99%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td valign=\"top\">\n      <table width=\"96%\"  border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n        <tr>\n      <td  colspan=\"2\" class=\"tableheadingbborder\">");
/* 2599 */                               out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2600 */                               out.write(" <span class=\"resourceheading\">\n    \t </span></td>\n\t\t </tr>\n        \t<tr>\n\t\t   <td class=\"monitorinfoodd\">");
/* 2601 */                               out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2602 */                               out.write(" </td>\n\t\t   <td class=\"monitorinfoodd\">");
/* 2603 */                               out.print(getTrimmedText(displayname, 30));
/* 2604 */                               out.write("</td>\n\t\t   </tr>\n\t\t   ");
/* 2605 */                               out.write("<!--$Id$-->\n");
/*      */                               
/* 2607 */                               String hostName = "localhost";
/*      */                               try {
/* 2609 */                                 hostName = InetAddress.getLocalHost().getHostName();
/*      */                               } catch (Exception ex) {
/* 2611 */                                 ex.printStackTrace();
/*      */                               }
/* 2613 */                               String portNumber = System.getProperty("webserver.port");
/* 2614 */                               String styleClass = "monitorinfoodd";
/* 2615 */                               if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 2616 */                                 styleClass = "whitegrayborder-conf-mon";
/*      */                               }
/*      */                               
/* 2619 */                               out.write(10);
/*      */                               
/* 2621 */                               PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2622 */                               _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2623 */                               _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                               
/* 2625 */                               _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2626 */                               int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2627 */                               if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                 for (;;) {
/* 2629 */                                   out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 2630 */                                   out.print(styleClass);
/* 2631 */                                   out.write(34);
/* 2632 */                                   out.write(62);
/* 2633 */                                   out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 2634 */                                   out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 2635 */                                   out.print(styleClass);
/* 2636 */                                   out.write(34);
/* 2637 */                                   out.write(62);
/* 2638 */                                   out.print(hostName);
/* 2639 */                                   out.write(95);
/* 2640 */                                   out.print(portNumber);
/* 2641 */                                   out.write("</td>\n</tr>\n");
/* 2642 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2643 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2647 */                               if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2648 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                               }
/*      */                               
/* 2651 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2652 */                               out.write(10);
/* 2653 */                               out.write("\n\t\t   ");
/*      */                               
/* 2655 */                               String healthStatus = alert.getProperty(resourceid + "#" + "3101");
/*      */                               
/* 2657 */                               out.write("\n\t\t   <tr>\n\t\t   <td class=\"monitorinfoeven\" valign=\"top\">");
/* 2658 */                               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2659 */                               out.write("</td>\n\t\t   <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2660 */                               out.print(resourceid);
/* 2661 */                               out.write("&attributeid=3101')\">");
/* 2662 */                               out.print(getSeverityImageForHealth(healthStatus));
/* 2663 */                               out.write("</a>\n\t\t   ");
/* 2664 */                               out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + "3101" + "#" + "MESSAGE"), "3101", alert.getProperty(resourceid + "#" + "3101"), resourceid));
/* 2665 */                               out.write("\n\t\t   ");
/* 2666 */                               if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, "3101") != 0) {
/* 2667 */                                 out.write("\n\t\t   <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2668 */                                 out.print(resourceid + "_3101");
/* 2669 */                                 out.write("&monitortype=MSSQL-DB-server')\">");
/* 2670 */                                 out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2671 */                                 out.write("</a></span>\n           ");
/*      */                               }
/* 2673 */                               out.write("\n\t\t   </td>\n\t\t   </tr>\n\t\t   <tr>\n\t\t   <td class=\"monitorinfoodd\">");
/* 2674 */                               out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2675 */                               out.write(" </td>\n\t\t   <td class=\"monitorinfoodd\">");
/* 2676 */                               out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 2677 */                               out.write("</td>\n\t\t   </tr>\n      ");
/*      */                               
/* 2679 */                               Properties mssqldetails = (Properties)request.getAttribute("details");
/* 2680 */                               if (mssqldetails.size() != 0)
/*      */                               {
/* 2682 */                                 out.write("\n      \t  <tr>\n          <td class=\"monitorinfoeven\" >");
/* 2683 */                                 out.print(FormatUtil.getString("am.webclient.mssqldetails.version"));
/* 2684 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\" height=\"21\"  title=\"");
/* 2685 */                                 out.print(mssqldetails.getProperty("VERSION"));
/* 2686 */                                 out.write(34);
/* 2687 */                                 out.write(62);
/* 2688 */                                 out.print(getTrimmedText(mssqldetails.getProperty("VERSION"), 40));
/* 2689 */                                 out.write("</td>\n\n\t\t\t</tr>\n\t\t\t<tr>\n\n          <td class=\"monitorinfoodd\">");
/* 2690 */                                 out.print(FormatUtil.getString("am.webclient.mssqldetails.port"));
/* 2691 */                                 out.write("</td>\n\n          <td  height=\"21\"  class=\"monitorinfoodd\">");
/* 2692 */                                 out.print(mssqldetails.getProperty("PORT"));
/* 2693 */                                 out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\n          <td  class=\"monitorinfoeven\">");
/* 2694 */                                 out.print(FormatUtil.getString("am.webclient.mssqldetails.odbcversion"));
/* 2695 */                                 out.write("</td>\n\t\t  <td  height=\"21\"  class=\"monitorinfoeven\" title=\"");
/* 2696 */                                 out.print(mssqldetails.getProperty("ODBC"));
/* 2697 */                                 out.write(34);
/* 2698 */                                 out.write(62);
/* 2699 */                                 out.print(getTrimmedText(mssqldetails.getProperty("ODBC"), 40));
/* 2700 */                                 out.write("</td>\n\t\t  </tr>\n\t\t  ");
/*      */                               }
/*      */                               
/*      */ 
/* 2704 */                               out.write("\n\t\t\t\t");
/*      */                               
/* 2706 */                               EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2707 */                               _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2708 */                               _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                               
/* 2710 */                               _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 2711 */                               int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2712 */                               if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                                 for (;;) {
/* 2714 */                                   out.write("\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2715 */                                   out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2716 */                                   out.write("</td>\n\t\t\t\t<td class=\"monitorinfoodd\">-&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2717 */                                   out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2718 */                                   out.write("</td>\n\t\t\t\t<td class=\"monitorinfoeven\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2719 */                                   out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2720 */                                   out.write("</td>\n\t\t\t\t<td class=\"monitorinfoeven\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2721 */                                   out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2722 */                                   out.write("</td>\n\t\t\t\t<td class=\"monitorinfoodd\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 2723 */                                   int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2724 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2728 */                               if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2729 */                                 this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                               }
/*      */                               
/* 2732 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2733 */                               out.write("\n\t\t\t\t");
/*      */                               
/* 2735 */                               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2736 */                               _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2737 */                               _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                               
/* 2739 */                               _jspx_th_logic_005fnotEmpty_005f1.setName("systeminfo");
/* 2740 */                               int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2741 */                               if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                 for (;;) {
/* 2743 */                                   out.write("\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2744 */                                   out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2745 */                                   out.write("</td>\n\t\t\t\t");
/*      */                                   
/* 2747 */                                   if (systeminfo.get("host_resid") != null)
/*      */                                   {
/* 2749 */                                     out.write("\n\t\t    <td class=\"monitorinfoeven\"><a href=\"showresource.do?resourceid=");
/* 2750 */                                     out.print(systeminfo.get("host_resid"));
/* 2751 */                                     out.write("&method=showResourceForResourceID\" class=\"staticlinks\" title=\"");
/* 2752 */                                     out.print(systeminfo.get("HOSTNAME"));
/* 2753 */                                     out.write(34);
/* 2754 */                                     out.write(32);
/* 2755 */                                     out.write(62);
/* 2756 */                                     out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 2757 */                                     out.write("&nbsp;(");
/* 2758 */                                     out.print(systeminfo.get("HOSTIP"));
/* 2759 */                                     out.write(")</a></td>\n\t\t\t");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2764 */                                     out.write("\n             <td class=\"monitorinfoeven\" title=\"");
/* 2765 */                                     out.print(systeminfo.get("HOSTNAME"));
/* 2766 */                                     out.write(34);
/* 2767 */                                     out.write(32);
/* 2768 */                                     out.write(62);
/* 2769 */                                     out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 2770 */                                     out.write("&nbsp;(");
/* 2771 */                                     out.print(systeminfo.get("HOSTIP"));
/* 2772 */                                     out.write(")</td>\n\t\t\t");
/*      */                                   }
/* 2774 */                                   out.write("\n\t\t\t\t<!--<td class=\"monitorinfoodd\">");
/* 2775 */                                   out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 30));
/* 2776 */                                   out.write("&nbsp;(");
/* 2777 */                                   out.print(systeminfo.get("HOSTIP"));
/* 2778 */                                   out.write(")</td>-->\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2779 */                                   out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2780 */                                   out.write("</td>\n                                <td class=\"monitorinfoeven\">");
/* 2781 */                                   out.print(FormatUtil.getString((String)systeminfo.get("HOSTOS")));
/* 2782 */                                   out.write("</td>");
/* 2783 */                                   out.write("\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */                                   
/* 2785 */                                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2786 */                                   _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 2787 */                                   _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                   
/* 2789 */                                   _jspx_th_logic_005fnotEmpty_005f2.setName("recent5Alarms");
/* 2790 */                                   int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 2791 */                                   if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                                     for (;;) {
/* 2793 */                                       out.write("\n\t\t\t\t");
/*      */                                       
/* 2795 */                                       ArrayList recent = (ArrayList)((ArrayList)request.getAttribute("recent5Alarms")).get(0);
/*      */                                       
/* 2797 */                                       out.write("\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2798 */                                       out.print(FormatUtil.getString("am.webclient.db2.lastalarm"));
/* 2799 */                                       out.write("</td>\n\t\t\t\t<td class=\"monitorinfoodd\"><a href=\"/fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2800 */                                       out.print(recent.get(2));
/* 2801 */                                       out.write("&source=");
/* 2802 */                                       out.print(recent.get(4));
/* 2803 */                                       out.write("&category=");
/* 2804 */                                       out.print(recent.get(0));
/* 2805 */                                       out.write("&redirectto=");
/* 2806 */                                       out.print(encodeurl);
/* 2807 */                                       out.write("\"  class=\"resourcename\">");
/* 2808 */                                       out.print(getTruncatedAlertMessage((String)recent.get(3)));
/* 2809 */                                       out.write("</a></td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 2810 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 2811 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2815 */                                   if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 2816 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                                   }
/*      */                                   
/* 2819 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 2820 */                                   out.write("\n\t\t\t\t");
/*      */                                   
/* 2822 */                                   EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2823 */                                   _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 2824 */                                   _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                   
/* 2826 */                                   _jspx_th_logic_005fempty_005f1.setName("recent5Alarms");
/* 2827 */                                   int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 2828 */                                   if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */                                     for (;;) {
/* 2830 */                                       out.write("\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2831 */                                       out.print(FormatUtil.getString("am.webclient.db2.lastalarm"));
/* 2832 */                                       out.write("</td>\n\t\t\t\t<td class=\"monitorinfoodd\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 2833 */                                       int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 2834 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2838 */                                   if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 2839 */                                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */                                   }
/*      */                                   
/* 2842 */                                   this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 2843 */                                   out.write("\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2844 */                                   out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2845 */                                   out.write("</td>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2846 */                                   out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 2847 */                                   out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2848 */                                   out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2849 */                                   out.write("</td>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2850 */                                   out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 2851 */                                   out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t");
/* 2852 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2853 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2857 */                               if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2858 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                               }
/*      */                               
/* 2861 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2862 */                               out.write("\n\t\t\t\t");
/* 2863 */                               out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n<script>\n $(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 2864 */                               out.write("\n\t{\n\t\t");
/* 2865 */                               if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2867 */                               out.write(10);
/* 2868 */                               out.write(9);
/* 2869 */                               out.write(9);
/* 2870 */                               if (_jspx_meth_c_005fif_005f5(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2872 */                               out.write("\n\t\tgetCustomFields('");
/* 2873 */                               if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2875 */                               out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 2876 */                               out.write("\n\t}\n\n});\n</script>\n");
/* 2877 */                               if (_jspx_meth_c_005fif_005f6(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2879 */                               out.write(10);
/* 2880 */                               out.write(10);
/* 2881 */                               if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2883 */                               out.write(10);
/* 2884 */                               out.write(10);
/* 2885 */                               if (_jspx_meth_c_005fset_005f10(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2887 */                               out.write(10);
/* 2888 */                               if (_jspx_meth_c_005fset_005f11(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2890 */                               out.write(10);
/* 2891 */                               out.write(10);
/* 2892 */                               out.write(10);
/* 2893 */                               if (_jspx_meth_c_005fif_005f8(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2895 */                               out.write(10);
/* 2896 */                               out.write(10);
/* 2897 */                               out.write(10);
/* 2898 */                               if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2900 */                               out.write("\n\n\n<tr>\n<td colspan=\"2\" class=\"");
/* 2901 */                               if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2903 */                               out.write("\" align=\"right\" style=\"padding:2px;\">\n<input type=\"button\" value=\"");
/* 2904 */                               if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2906 */                               out.write("\" onclick=\"getCustomFields('");
/* 2907 */                               if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2909 */                               out.write(39);
/* 2910 */                               out.write(44);
/* 2911 */                               out.write(39);
/* 2912 */                               if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2914 */                               out.write("',false,'CustomFieldValues',false);\" class=\"buttons btn_custom\"/>");
/* 2915 */                               out.write("\n</td>\n</tr>\n\n\n");
/* 2916 */                               out.write("\n        </table>\n        ");
/*      */                               
/* 2918 */                               PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2919 */                               _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2920 */                               _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                               
/* 2922 */                               _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 2923 */                               int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2924 */                               if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                 for (;;) {
/* 2926 */                                   out.write(10);
/* 2927 */                                   out.write(9);
/* 2928 */                                   out.write(9);
/*      */                                   
/* 2930 */                                   IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2931 */                                   _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2932 */                                   _jspx_th_c_005fif_005f10.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */                                   
/* 2934 */                                   _jspx_th_c_005fif_005f10.setTest("${showdata=='1'}");
/* 2935 */                                   int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2936 */                                   if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                     for (;;) {
/* 2938 */                                       out.write("\n\t\t\t\t<div align=\"center\"><a style=cursor:pointer;><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" onclick=\"javascript:toggleDiv('edit')\">\n\n            <tr>\n              <td>&nbsp;</td>\n            </tr>\n            <tr>\n              <td><table width=\"75%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"getmoredatatable\">\n                  <tr>\n                    <td width=\"13%\" background=\"../images/getmoredata_bg.gif\"><img src=\"../images/icon_getmoredata.gif\" width=\"35\" height=\"35\" border=\"0\" vspace=\"2\" hspace=\"5\"></td>\n                    <td width=\"87%\" background=\"../images/getmoredata_bg.gif\">");
/* 2939 */                                       out.print(FormatUtil.getString("am.webclient.configureimage.mssql.text"));
/* 2940 */                                       out.write("</td>\n                  </tr>\n                </table></td>\n            </tr>\n      </table></a></div>\n\t\t\t\t");
/* 2941 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2942 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2946 */                                   if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2947 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                   }
/*      */                                   
/* 2950 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2951 */                                   out.write("\n\t\t\t\t");
/* 2952 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2953 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2957 */                               if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2958 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                               }
/*      */                               
/* 2961 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2962 */                               out.write("\n        </td>\n    <td width=\"40%\" height=\"31\" class=\"bodytextbold\" valign=\"top\" >\n\n\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n        <tbody>\n        <tr>\n        <td colspan=\"4\" height=\"31\" class=\"tableheadingbborder\">\n      ");
/* 2963 */                               out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 2964 */                               out.write(" <a name=\"Availability\" id=\"Availability\"></a></td>\n      </tr>\n\n<tr> <td colspan=\"4\">      <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n          \t<tr>\n          \t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2965 */                               if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2967 */                               out.write("&period=1&resourcename=");
/* 2968 */                               if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2970 */                               out.write("')\">\n      <img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2971 */                               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2972 */                               out.write("\"></a></td>\n            <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2973 */                               if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2975 */                               out.write("&period=2&resourcename=");
/* 2976 */                               if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2978 */                               out.write("')\"><img src=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2979 */                               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 2980 */                               out.write("\"></a></td>\n      </tr>\n</table>\n</td>\n</tr>\n\n\n                <tr>\n\n                <td colspan=\"4\" align=\"center\">\n                 ");
/*      */                               
/* 2982 */                               AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 2983 */                               _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 2984 */                               _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                               
/* 2986 */                               _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                               
/* 2988 */                               _jspx_th_awolf_005fpiechart_005f0.setWidth("280");
/*      */                               
/* 2990 */                               _jspx_th_awolf_005fpiechart_005f0.setHeight("200");
/*      */                               
/* 2992 */                               _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                               
/* 2994 */                               _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                               
/* 2996 */                               _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                               
/* 2998 */                               _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 2999 */                               int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3000 */                               if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3001 */                                 if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3002 */                                   out = _jspx_page_context.pushBody();
/* 3003 */                                   _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3004 */                                   _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 3007 */                                   out.write("\n\t\t                   ");
/*      */                                   
/* 3009 */                                   Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3010 */                                   _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3011 */                                   _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                                   
/* 3013 */                                   _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3014 */                                   int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3015 */                                   if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3016 */                                     if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3017 */                                       out = _jspx_page_context.pushBody();
/* 3018 */                                       _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3019 */                                       _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3022 */                                       out.write("\n\t\t                \t\t");
/*      */                                       
/* 3024 */                                       AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3025 */                                       _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3026 */                                       _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                       
/* 3028 */                                       _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                                       
/* 3030 */                                       _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 3031 */                                       int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3032 */                                       if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3033 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                       }
/*      */                                       
/* 3036 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3037 */                                       out.write("\n\t\t                \t\t");
/*      */                                       
/* 3039 */                                       AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3040 */                                       _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3041 */                                       _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                       
/* 3043 */                                       _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                                       
/* 3045 */                                       _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 3046 */                                       int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3047 */                                       if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3048 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                       }
/*      */                                       
/* 3051 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3052 */                                       out.write("\n\t\t                \t");
/* 3053 */                                       int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3054 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3057 */                                     if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3058 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3061 */                                   if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3062 */                                     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                                   }
/*      */                                   
/* 3065 */                                   this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3066 */                                   out.write("\n    \t\t\t  ");
/* 3067 */                                   int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3068 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3071 */                                 if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3072 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3075 */                               if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3076 */                                 this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */                               }
/*      */                               
/* 3079 */                               this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3080 */                               out.write("\n                </td>\n        </tr>\n\t\t<tr>\n\t\t\t\t<td  width=\"49%\" class=\"yellowgrayborder\" colspan=\"2\">");
/* 3081 */                               out.print(FormatUtil.getString("am.webclient.mssqldetails.currnetstatus"));
/* 3082 */                               out.write("\n\t\t\t\t");
/*      */                               
/* 3084 */                               String avastatus = alert.getProperty(resourceid + "#" + "3100");
/*      */                               
/* 3086 */                               out.write("\n\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3087 */                               out.print(resourceid);
/* 3088 */                               out.write("&attributeid=3100')\">");
/* 3089 */                               out.print(getSeverityImageForAvailability(avastatus));
/* 3090 */                               out.write("</a></td>\n\t\t\t\t            <td width=\"50%\"  class=\"yellowgrayborder\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3091 */                               out.print(resourceid);
/* 3092 */                               out.write("&attributeIDs=3100,3101&attributeToSelect=3100&redirectto=");
/* 3093 */                               out.print(encodeurl);
/* 3094 */                               out.write("\" class=\"links\">");
/* 3095 */                               out.print(ALERTCONFIG_TEXT);
/* 3096 */                               out.write("</a></td>\n\t\t</tr>\n\n\n        </tbody>\n      </table></td>\n  </tr>\n  </table>\n  <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3097 */                               out.write("<!--$Id$-->\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td>\n<div id=\"customfieldsfullListDiv\" style='overflow: auto; display:none; width: 100%;'>\n</div>\n<div id=\"customfieldsloadingdiv\" style='text-align:center;height:200px;width: 100%;display: none;'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\n</td></tr></table>\n");
/* 3098 */                               out.write("</td></tr></table>\n  <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td>&nbsp;</td>\n  </tr>\n  </table>\n  ");
/*      */                               
/* 3100 */                               IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3101 */                               _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3102 */                               _jspx_th_c_005fif_005f11.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                               
/* 3104 */                               _jspx_th_c_005fif_005f11.setTest("${showdata=='1'}");
/* 3105 */                               int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3106 */                               if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                                 for (;;) {
/* 3108 */                                   out.write("\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n  <td width=\"100%\" height=\"29\" class=\"tableheadingtrans\" >");
/* 3109 */                                   out.print(FormatUtil.getString("am.webclient.hostResource.servers.response"));
/* 3110 */                                   out.write("&nbsp;</td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n  <td width=\"405\" height=\"127\" valign=\"top\">\n  <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n  <tr>\n  <td width=\"96%\" align=\"right\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3111 */                                   if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                     return;
/* 3113 */                                   out.write("&attributeid=3102&period=-7',740,550)\">\n  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3114 */                                   out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3115 */                                   out.write("\"></td>\n  <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3116 */                                   if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                     return;
/* 3118 */                                   out.write("&attributeid=3102&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3119 */                                   out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3120 */                                   out.write("\"></td>\n  </tr>\n  <tr>\n  <td colspan=\"2\">\n  ");
/*      */                                   
/* 3122 */                                   TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3123 */                                   _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3124 */                                   _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_c_005fif_005f11);
/*      */                                   
/* 3126 */                                   _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("perfgraph");
/*      */                                   
/* 3128 */                                   _jspx_th_awolf_005ftimechart_005f0.setWidth("300");
/*      */                                   
/* 3130 */                                   _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                                   
/* 3132 */                                   _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                                   
/* 3134 */                                   _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                   
/* 3136 */                                   _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.db2.graph.responsetimeinms"));
/* 3137 */                                   int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 3138 */                                   if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 3139 */                                     if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3140 */                                       out = _jspx_page_context.pushBody();
/* 3141 */                                       _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 3142 */                                       _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3145 */                                       out.write(10);
/* 3146 */                                       out.write(32);
/* 3147 */                                       out.write(32);
/* 3148 */                                       int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 3149 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3152 */                                     if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3153 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3156 */                                   if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 3157 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                                   }
/*      */                                   
/* 3160 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 3161 */                                   out.write("\n  </tr>\n  </table></td>\n  <td width=\"562\" valign=\"top\"> <br> <br>\n  <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n  <tr>\n  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3162 */                                   out.print(FormatUtil.getString("table.heading.attribute"));
/* 3163 */                                   out.write("</span></td>\n  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3164 */                                   out.print(FormatUtil.getString("table.heading.value"));
/* 3165 */                                   out.write("</span></td>\n  <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 3166 */                                   out.print(FormatUtil.getString("table.heading.status"));
/* 3167 */                                   out.write("</span></td>\n  </tr>\n  <tr>\n  <tr>\n  <td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3168 */                                   out.print(FormatUtil.getString("am.webclient.common.connectiontime.text"));
/* 3169 */                                   out.write(" </td>\n  <td width=\"26%\" height=\"19\" class=\"whitegrayborder\">\n  ");
/*      */                                   
/* 3171 */                                   if (perfgraph.getResponseTime(Integer.parseInt(resourceid)) == -1L)
/*      */                                   {
/*      */ 
/* 3174 */                                     out.write("\n\t\t  -\n\t\t  ");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 3180 */                                     out.write("\n          ");
/* 3181 */                                     out.print(formatNumber(perfgraph.getResponseTime(Integer.parseInt(resourceid))));
/* 3182 */                                     out.write(" \n          ms<!--No I18N-->\n\n\t\t  ");
/*      */                                   }
/*      */                                   
/*      */ 
/*      */ 
/* 3187 */                                   out.write("\n\t\t\t</td>\n\t\t\t");
/*      */                                   
/* 3189 */                                   String status2402 = alert.getProperty(resourceid + "#" + "3402");
/*      */                                   
/* 3191 */                                   out.write("\n\t\t\t<td class=\"whitegrayborder\" width=\"29%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3192 */                                   out.print(resourceid);
/* 3193 */                                   out.write("&attributeid=3402')\">");
/* 3194 */                                   out.print(getSeverityImage(status2402));
/* 3195 */                                   out.write("&nbsp;</a></td>\n\t\t\t</tr>\n\t\t\t<tr >\n\t\t\t<td  colspan=\"4\" height=\"21\" class=\"yellowgrayborder\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3196 */                                   out.print(resourceid);
/* 3197 */                                   out.write("&attributeIDs=3402&attributeToSelect=3402&redirectto=");
/* 3198 */                                   out.print(encodeurl);
/* 3199 */                                   out.write("\" class=\"links\">");
/* 3200 */                                   out.print(ALERTCONFIG_TEXT);
/* 3201 */                                   out.write("</a>&nbsp;</td>\n\t\t\t</tr>\n\t\t\t</table></td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t\t\t<tr>\n\t\t\t<td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t</table>\n");
/* 3202 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3203 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3207 */                               if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3208 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                               }
/*      */                               
/* 3211 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3212 */                               out.write(10);
/*      */                               
/* 3214 */                               IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3215 */                               _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3216 */                               _jspx_th_c_005fif_005f12.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                               
/* 3218 */                               _jspx_th_c_005fif_005f12.setTest("${showdata!='1'}");
/* 3219 */                               int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3220 */                               if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                                 for (;;) {
/* 3222 */                                   out.write("\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n\n    <td width=\"50%\" height=\"31\" class=\"tableheading\">");
/* 3223 */                                   out.print(FormatUtil.getString("Memory Usage"));
/* 3224 */                                   out.write(" </td>\n\n    <td width=\"50%\" height=\"31\" align=\"left\" class=\"tableheading\">");
/* 3225 */                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.buffermanstatics"));
/* 3226 */                                   out.write("</td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n  <td width=\"50%\" height=\"38\"  class=\"rbborder\"><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3227 */                                   out.print(resourceid);
/* 3228 */                                   out.write("&attributeid=3142&period=-7&resourcename=");
/* 3229 */                                   out.print(displayname);
/* 3230 */                                   out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3231 */                                   out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3232 */                                   out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3233 */                                   out.print(resourceid);
/* 3234 */                                   out.write("&attributeid=3142&period=-30&resourcename=");
/* 3235 */                                   out.print(displayname);
/* 3236 */                                   out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3237 */                                   out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3238 */                                   out.write("'></a></td>\n  </tr>\n  <tr>\n  ");
/* 3239 */                                   mssqlgraph.settype("MEMORY");
/* 3240 */                                   out.write("\n  <td> ");
/*      */                                   
/* 3242 */                                   TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3243 */                                   _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3244 */                                   _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_c_005fif_005f12);
/*      */                                   
/* 3246 */                                   _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("mssqlgraph");
/*      */                                   
/* 3248 */                                   _jspx_th_awolf_005ftimechart_005f1.setWidth("330");
/*      */                                   
/* 3250 */                                   _jspx_th_awolf_005ftimechart_005f1.setHeight("180");
/*      */                                   
/* 3252 */                                   _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */                                   
/* 3254 */                                   _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                   
/* 3256 */                                   _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(FormatUtil.getString("am.webclient.mssqldetails.memusage"));
/* 3257 */                                   int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3258 */                                   if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3259 */                                     if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3260 */                                       out = _jspx_page_context.pushBody();
/* 3261 */                                       _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3262 */                                       _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3265 */                                       out.write("\n      ");
/* 3266 */                                       int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3267 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3270 */                                     if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3271 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3274 */                                   if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3275 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                                   }
/*      */                                   
/* 3278 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3279 */                                   out.write("</td>\n  </tr>\n  </table>\n  <td width=\"50%\" height=\"38\" class=\"bottomborder\"> <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3280 */                                   out.print(resourceid);
/* 3281 */                                   out.write("&attributeid=3103&period=-7&resourcename=");
/* 3282 */                                   out.print(displayname);
/* 3283 */                                   out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3284 */                                   out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3285 */                                   out.write("'></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3286 */                                   out.print(resourceid);
/* 3287 */                                   out.write("&attributeid=3103&period=-30&resourcename=");
/* 3288 */                                   out.print(displayname);
/* 3289 */                                   out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 3290 */                                   out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3291 */                                   out.write("'></a></td>\n  </tr>\n  <tr>\n  ");
/* 3292 */                                   mssqlgraph.settype("BUFFER");
/* 3293 */                                   out.write("\n  <td>");
/*      */                                   
/* 3295 */                                   TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3296 */                                   _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 3297 */                                   _jspx_th_awolf_005ftimechart_005f2.setParent(_jspx_th_c_005fif_005f12);
/*      */                                   
/* 3299 */                                   _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("mssqlgraph");
/*      */                                   
/* 3301 */                                   _jspx_th_awolf_005ftimechart_005f2.setWidth("330");
/*      */                                   
/* 3303 */                                   _jspx_th_awolf_005ftimechart_005f2.setHeight("180");
/*      */                                   
/* 3305 */                                   _jspx_th_awolf_005ftimechart_005f2.setLegend("true");
/*      */                                   
/* 3307 */                                   _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                   
/* 3309 */                                   _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(FormatUtil.getString("am.webclient.mssqldetails.valueinper"));
/* 3310 */                                   int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 3311 */                                   if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 3312 */                                     if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3313 */                                       out = _jspx_page_context.pushBody();
/* 3314 */                                       _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 3315 */                                       _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3318 */                                       out.write("\n        ");
/* 3319 */                                       int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 3320 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3323 */                                     if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3324 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3327 */                                   if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 3328 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                                   }
/*      */                                   
/* 3331 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3332 */                                   out.write("</td>\n  </tr>\n  </table></td>\n\n  <tr>\n  <td valign=\"top\" class=\"rborder\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n  <tr>\n  <td width=\"60%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3333 */                                   out.print(FormatUtil.getString("table.heading.attribute"));
/* 3334 */                                   out.write("</span></td>\n  <td width=\"20%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3335 */                                   out.print(FormatUtil.getString("table.heading.value"));
/* 3336 */                                   out.write("</span></td>\n  <td width=\"20%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3337 */                                   out.print(FormatUtil.getString("table.heading.status"));
/* 3338 */                                   out.write("</span></td>\n  </tr>\n  <tr>\n  <td class=\"whitegrayborder\">");
/* 3339 */                                   out.print(FormatUtil.getString("Total Memory"));
/* 3340 */                                   out.write("</td>\n  <td class=\"whitegrayborder\">");
/* 3341 */                                   out.print(data.getProperty("TOTALMEMORY"));
/* 3342 */                                   out.write("&nbsp;");
/* 3343 */                                   out.print(FormatUtil.getString("KB"));
/* 3344 */                                   out.write("</td>\n  <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3345 */                                   out.print(resourceid);
/* 3346 */                                   out.write("&attributeid=3142')\">");
/* 3347 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3142")));
/* 3348 */                                   out.write("</a></td>\n  </tr>\n  <tr>\n  <td class=\"yellowgrayborder\">");
/* 3349 */                                   out.print(FormatUtil.getString("SQL Cache Memory"));
/* 3350 */                                   out.write("</td>\n  <td class=\"yellowgrayborder\">");
/* 3351 */                                   out.print(data.getProperty("SQLCACHEMEMORY"));
/* 3352 */                                   out.write("&nbsp;");
/* 3353 */                                   out.print(FormatUtil.getString("KB"));
/* 3354 */                                   out.write("</td>\n  <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3355 */                                   out.print(resourceid);
/* 3356 */                                   out.write("&attributeid=3143')\">");
/* 3357 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3143")));
/* 3358 */                                   out.write("</a></td>\n  </tr>\n  <tr>\n  <td class=\"whitegrayborder\">");
/* 3359 */                                   out.print(FormatUtil.getString("Lock Memory"));
/* 3360 */                                   out.write("</td>\n  <td class=\"whitegrayborder\">");
/* 3361 */                                   out.print(data.getProperty("LOCKMEMORY"));
/* 3362 */                                   out.write("&nbsp;");
/* 3363 */                                   out.print(FormatUtil.getString("KB"));
/* 3364 */                                   out.write("</td>\n  <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3365 */                                   out.print(resourceid);
/* 3366 */                                   out.write("&attributeid=3144')\">");
/* 3367 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3144")));
/* 3368 */                                   out.write("</a></td>\n  </tr>\n  <tr>\n  <td width=\"43%\" class=\"yellowgrayborder\">");
/* 3369 */                                   out.print(FormatUtil.getString("Optimizer Memory"));
/* 3370 */                                   out.write("</td>\n  <td class=\"yellowgrayborder\">");
/* 3371 */                                   out.print(data.getProperty("OPTIMIZERMEMORY"));
/* 3372 */                                   out.write(32);
/* 3373 */                                   out.print(FormatUtil.getString("KB"));
/* 3374 */                                   out.write(" </td>\n  <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3375 */                                   out.print(resourceid);
/* 3376 */                                   out.write("&attributeid=3149')\">");
/* 3377 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3149")));
/* 3378 */                                   out.write("</a></td>\n  </tr>\n  <tr>\n  <td class=\"whitegrayborder\">");
/* 3379 */                                   out.print(FormatUtil.getString("Connection Memory"));
/* 3380 */                                   out.write("</td>\n  <td class=\"whitegrayborder\">");
/* 3381 */                                   out.print(data.getProperty("CONNECTIONMEMORY"));
/* 3382 */                                   out.write(32);
/* 3383 */                                   out.print(FormatUtil.getString("KB"));
/* 3384 */                                   out.write(" </td>\n  <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3385 */                                   out.print(resourceid);
/* 3386 */                                   out.write("&attributeid=3145')\">");
/* 3387 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3145")));
/* 3388 */                                   out.write("</a></td>\n  </tr>\n  <tr>\n  <td class=\"yellowgrayborder\">");
/* 3389 */                                   out.print(FormatUtil.getString("Granted WorkSpace Memory"));
/* 3390 */                                   out.write("</td>\n  <td class=\"yellowgrayborder\">");
/* 3391 */                                   out.print(data.getProperty("GRANTEDWORKSPACEMEMORY"));
/* 3392 */                                   out.write(32);
/* 3393 */                                   out.print(FormatUtil.getString("KB"));
/* 3394 */                                   out.write(" </a></td>\n  <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3395 */                                   out.print(resourceid);
/* 3396 */                                   out.write("&attributeid=3146')\">");
/* 3397 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3146")));
/* 3398 */                                   out.write("</a></td>\n  </tr>\n  <tr>\n  <td class=\"whitegrayborder\">");
/* 3399 */                                   out.print(FormatUtil.getString("Memory Grants Pending"));
/* 3400 */                                   out.write("</td>\n  <td class=\"whitegrayborder\"> ");
/* 3401 */                                   out.print(data.getProperty("MEMORYGRANTSPENDING"));
/* 3402 */                                   out.write(" </td>\n  <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3403 */                                   out.print(resourceid);
/* 3404 */                                   out.write("&attributeid=3147')\">");
/* 3405 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3147")));
/* 3406 */                                   out.write("</a></td>\n  </tr>\n  <tr>\n  <td class=\"yellowgrayborder\">");
/* 3407 */                                   out.print(FormatUtil.getString("Memory Grant Success"));
/* 3408 */                                   out.write(" </td>\n  <td class=\"yellowgrayborder\">");
/* 3409 */                                   out.print(data.getProperty("MEMORYGRANTSSUCCESS"));
/* 3410 */                                   out.write("</td>\n  <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3411 */                                   out.print(resourceid);
/* 3412 */                                   out.write("&attributeid=3148')\">");
/* 3413 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3148")));
/* 3414 */                                   out.write("</a></td>\n  </tr>\n  <tr>\n  <td colspan=\"3\" align=\"right\" class=\"whitegrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3415 */                                   out.print(resourceid);
/* 3416 */                                   out.write("&attributeIDs=3142,3143,3144,3145,3146,3147,3148,3149&attributeToSelect=3142&redirectto=");
/* 3417 */                                   out.print(encodeurl);
/* 3418 */                                   out.write("'class=\"staticlinks\">");
/* 3419 */                                   out.print(ALERTCONFIG_TEXT);
/* 3420 */                                   out.write("</a></td>\n  </tr>\n  </table></td>\n  <td valign=\"top\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n        <tr>\n          <td width=\"60%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3421 */                                   out.print(FormatUtil.getString("table.heading.attribute"));
/* 3422 */                                   out.write("</span></td>\n          <td width=\"20%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3423 */                                   out.print(FormatUtil.getString("table.heading.value"));
/* 3424 */                                   out.write("</span></td>\n          <td width=\"20%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3425 */                                   out.print(FormatUtil.getString("table.heading.status"));
/* 3426 */                                   out.write("</span></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3427 */                                   out.print(FormatUtil.getString("Buffer Hit Ratio"));
/* 3428 */                                   out.write(" </td>\n          <td class=\"whitegrayborder\">");
/* 3429 */                                   out.print(data.getProperty("BUFFERHITRATIO"));
/* 3430 */                                   out.write(" </td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3431 */                                   out.print(resourceid);
/* 3432 */                                   out.write("&attributeid=3103')\">");
/* 3433 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3103")));
/* 3434 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 3435 */                                   out.print(FormatUtil.getString("Page Lookups/Min"));
/* 3436 */                                   out.write(" </td>\n          <td class=\"yellowgrayborder\">");
/* 3437 */                                   out.print(data.getProperty("PAGELOOKUPSPERMIN"));
/* 3438 */                                   out.write(" </td>\n          <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3439 */                                   out.print(resourceid);
/* 3440 */                                   out.write("&attributeid=3105')\">");
/* 3441 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3105")));
/* 3442 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3443 */                                   out.print(FormatUtil.getString("Page Reads/Min"));
/* 3444 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 3445 */                                   out.print(data.getProperty("PAGEREADSPERMIN"));
/* 3446 */                                   out.write("\n          </td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3447 */                                   out.print(resourceid);
/* 3448 */                                   out.write("&attributeid=3106')\">");
/* 3449 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3106")));
/* 3450 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td width=\"37%\" class=\"yellowgrayborder\">");
/* 3451 */                                   out.print(FormatUtil.getString("Page Writes/Min"));
/* 3452 */                                   out.write(" </td>\n          <td   class=\"yellowgrayborder\">");
/* 3453 */                                   out.print(data.getProperty("PAGEWRITESPERMIN"));
/* 3454 */                                   out.write("</td>\n          <td   class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3455 */                                   out.print(resourceid);
/* 3456 */                                   out.write("&attributeid=3107')\">");
/* 3457 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3107")));
/* 3458 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3459 */                                   out.print(FormatUtil.getString("Total Pages"));
/* 3460 */                                   out.write("</td>\n          <td   class=\"whitegrayborder\">");
/* 3461 */                                   if (data.getProperty("TOTALPAGES").equals("-1")) {
/* 3462 */                                     out.write(" <span class='bodytext'>");
/* 3463 */                                     out.print(FormatUtil.getString("am.webclient.mssqldetails.na"));
/* 3464 */                                     out.write("</span> ");
/*      */                                   } else {
/* 3466 */                                     out.write(32);
/* 3467 */                                     out.print(data.getProperty("TOTALPAGES"));
/* 3468 */                                     out.write(32);
/*      */                                   }
/* 3470 */                                   out.write("&nbsp;</td>\n          <td  class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3471 */                                   out.print(resourceid);
/* 3472 */                                   out.write("&attributeid=3108')\">");
/* 3473 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3108")));
/* 3474 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 3475 */                                   out.print(FormatUtil.getString("Database Pages"));
/* 3476 */                                   out.write("</td>\n          <td  class=\"yellowgrayborder\">");
/* 3477 */                                   if (data.getProperty("DATABASEPAGES").equals("-1")) {
/* 3478 */                                     out.write(" <span class='bodytext'>");
/* 3479 */                                     out.print(FormatUtil.getString("am.webclient.mssqldetails.na"));
/* 3480 */                                     out.write("</span> ");
/*      */                                   } else {
/* 3482 */                                     out.write(32);
/* 3483 */                                     out.write(32);
/* 3484 */                                     out.print(data.getProperty("DATABASEPAGES"));
/* 3485 */                                     out.write(32);
/* 3486 */                                     out.write(32);
/*      */                                   }
/* 3488 */                                   out.write("&nbsp;</td>\n          <td  class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3489 */                                   out.print(resourceid);
/* 3490 */                                   out.write("&attributeid=3104')\">");
/* 3491 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3104")));
/* 3492 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3493 */                                   out.print(FormatUtil.getString("Free Pages"));
/* 3494 */                                   out.write(" </td>\n          <td class=\"whitegrayborder\">");
/* 3495 */                                   if (data.getProperty("FREEPAGES").equals("-1")) {
/* 3496 */                                     out.write(" <span class='bodytext'>");
/* 3497 */                                     out.print(FormatUtil.getString("am.webclient.mssqldetails.na"));
/* 3498 */                                     out.write("</span> ");
/*      */                                   } else {
/* 3500 */                                     out.write(32);
/* 3501 */                                     out.write(32);
/* 3502 */                                     out.print(data.getProperty("FREEPAGES"));
/* 3503 */                                     out.write(32);
/* 3504 */                                     out.write(32);
/*      */                                   }
/* 3506 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3507 */                                   out.print(resourceid);
/* 3508 */                                   out.write("&attributeid=3109')\">");
/* 3509 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3109")));
/* 3510 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n        \t<td class=\"whitegrayborder\">");
/* 3511 */                                   out.print(FormatUtil.getString("Page Life Expectancy"));
/* 3512 */                                   out.write(" </td>\n            <td class=\"whitegrayborder\">");
/* 3513 */                                   if (data.getProperty("PAGELIFEEXP").equals("-1")) {
/* 3514 */                                     out.write(" <span class='bodytext'>");
/* 3515 */                                     out.print(FormatUtil.getString("am.webclient.mssqldetails.na"));
/* 3516 */                                     out.write("</span> ");
/*      */                                   } else {
/* 3518 */                                     out.write(32);
/* 3519 */                                     out.write(32);
/* 3520 */                                     out.print(data.getProperty("PAGELIFEEXP"));
/* 3521 */                                     out.write(32);
/* 3522 */                                     out.write(32);
/*      */                                   }
/* 3524 */                                   out.write("</td>\n            <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3525 */                                   out.print(resourceid);
/* 3526 */                                   out.write("&attributeid=3164')\">");
/* 3527 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3164")));
/* 3528 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td colspan=\"3\" align=\"right\"  class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3529 */                                   out.print(resourceid);
/* 3530 */                                   out.write("&attributeIDs=3103,3104,3105,3106,3107,3108,3109,3164&attributeToSelect=3103&redirectto=");
/* 3531 */                                   out.print(encodeurl);
/* 3532 */                                   out.write("'  class=\"staticlinks\">");
/* 3533 */                                   out.print(ALERTCONFIG_TEXT);
/* 3534 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td colspan=\"3\" align=\"right\"  class=\"whitegrayborder\">&nbsp;</td>\n        </tr>\n      </table></td>\n  </tr>\n  </table>\n  <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td>&nbsp;</td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"50%\" height=\"31\" class=\"tableheadingtrans\">");
/* 3535 */                                   out.print(FormatUtil.getString("am.webclient.mysql.connectionstatistics"));
/* 3536 */                                   out.write(" </td>\n    <td width=\"50%\" height=\"31\" align=\"left\" class=\"tableheading\">");
/* 3537 */                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.cachedetails"));
/* 3538 */                                   out.write("\n    </td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n  <td width=\"50%\" height=\"38\"  class=\"rbborder\"><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3539 */                                   out.print(resourceid);
/* 3540 */                                   out.write("&attributeid=3102&period=-7&resourcename=");
/* 3541 */                                   out.print(displayname);
/* 3542 */                                   out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3543 */                                   out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3544 */                                   out.write("\"></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3545 */                                   out.print(resourceid);
/* 3546 */                                   out.write("&attributeid=3102&period=-30&resourcename=");
/* 3547 */                                   out.print(displayname);
/* 3548 */                                   out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3549 */                                   out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3550 */                                   out.write("\"></a></td>\n  </tr>\n  <tr>\n   ");
/* 3551 */                                   mssqlgraph.settype("CONNECTION");
/* 3552 */                                   out.write("\n  <td>");
/*      */                                   
/* 3554 */                                   TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3555 */                                   _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 3556 */                                   _jspx_th_awolf_005ftimechart_005f3.setParent(_jspx_th_c_005fif_005f12);
/*      */                                   
/* 3558 */                                   _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("mssqlgraph");
/*      */                                   
/* 3560 */                                   _jspx_th_awolf_005ftimechart_005f3.setWidth("330");
/*      */                                   
/* 3562 */                                   _jspx_th_awolf_005ftimechart_005f3.setHeight("185");
/*      */                                   
/* 3564 */                                   _jspx_th_awolf_005ftimechart_005f3.setLegend("true");
/*      */                                   
/* 3566 */                                   _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */                                   
/* 3568 */                                   _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel(FormatUtil.getString("am.webclient.mssqldetails.connectiontime"));
/* 3569 */                                   int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 3570 */                                   if (_jspx_eval_awolf_005ftimechart_005f3 != 0) {
/* 3571 */                                     if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3572 */                                       out = _jspx_page_context.pushBody();
/* 3573 */                                       _jspx_th_awolf_005ftimechart_005f3.setBodyContent((BodyContent)out);
/* 3574 */                                       _jspx_th_awolf_005ftimechart_005f3.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3577 */                                       out.write("\n\t        ");
/* 3578 */                                       int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f3.doAfterBody();
/* 3579 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3582 */                                     if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3583 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3586 */                                   if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 3587 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3); return;
/*      */                                   }
/*      */                                   
/* 3590 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 3591 */                                   out.write("</td>\n  </tr>\n  </table>\n  <td width=\"50%\" height=\"38\" class=\"bottomborder\"> <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3592 */                                   out.print(resourceid);
/* 3593 */                                   out.write("&attributeid=3138&period=-7&resourcename=");
/* 3594 */                                   out.print(displayname);
/* 3595 */                                   out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3596 */                                   out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3597 */                                   out.write("\"></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3598 */                                   out.print(resourceid);
/* 3599 */                                   out.write("&attributeid=3138&period=-30&resourcename=");
/* 3600 */                                   out.print(displayname);
/* 3601 */                                   out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3602 */                                   out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3603 */                                   out.write("\"></a></td>\n  </tr>\n  <tr>\n   ");
/* 3604 */                                   mssqlgraph.settype("CACHE");
/* 3605 */                                   out.write("\n  <td>");
/*      */                                   
/* 3607 */                                   TimeChart _jspx_th_awolf_005ftimechart_005f4 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3608 */                                   _jspx_th_awolf_005ftimechart_005f4.setPageContext(_jspx_page_context);
/* 3609 */                                   _jspx_th_awolf_005ftimechart_005f4.setParent(_jspx_th_c_005fif_005f12);
/*      */                                   
/* 3611 */                                   _jspx_th_awolf_005ftimechart_005f4.setDataSetProducer("mssqlgraph");
/*      */                                   
/* 3613 */                                   _jspx_th_awolf_005ftimechart_005f4.setWidth("330");
/*      */                                   
/* 3615 */                                   _jspx_th_awolf_005ftimechart_005f4.setHeight("170");
/*      */                                   
/* 3617 */                                   _jspx_th_awolf_005ftimechart_005f4.setLegend("true");
/*      */                                   
/* 3619 */                                   _jspx_th_awolf_005ftimechart_005f4.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */                                   
/* 3621 */                                   _jspx_th_awolf_005ftimechart_005f4.setYaxisLabel(FormatUtil.getString("am.webclient.common.axisname.valueinper.text"));
/* 3622 */                                   int _jspx_eval_awolf_005ftimechart_005f4 = _jspx_th_awolf_005ftimechart_005f4.doStartTag();
/* 3623 */                                   if (_jspx_eval_awolf_005ftimechart_005f4 != 0) {
/* 3624 */                                     if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 3625 */                                       out = _jspx_page_context.pushBody();
/* 3626 */                                       _jspx_th_awolf_005ftimechart_005f4.setBodyContent((BodyContent)out);
/* 3627 */                                       _jspx_th_awolf_005ftimechart_005f4.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3630 */                                       out.write("\n\t        ");
/* 3631 */                                       int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f4.doAfterBody();
/* 3632 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3635 */                                     if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 3636 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3639 */                                   if (_jspx_th_awolf_005ftimechart_005f4.doEndTag() == 5) {
/* 3640 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4); return;
/*      */                                   }
/*      */                                   
/* 3643 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4);
/* 3644 */                                   out.write("</td>\n  </tr>\n  </table></td>\n  <tr>\n  <td valign=\"top\" class=\"rborder\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n        <tr>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3645 */                                   out.print(FormatUtil.getString("table.heading.attribute"));
/* 3646 */                                   out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3647 */                                   out.print(FormatUtil.getString("table.heading.value"));
/* 3648 */                                   out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3649 */                                   out.print(FormatUtil.getString("table.heading.status"));
/* 3650 */                                   out.write("</span></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3651 */                                   out.print(FormatUtil.getString("Connection Time"));
/* 3652 */                                   out.write("</td>\n          <td width=\"27%\" class=\"whitegrayborder\">");
/* 3653 */                                   out.print(data.getProperty("CONNECTIONTIME"));
/* 3654 */                                   out.write("\n            &nbsp; ");
/* 3655 */                                   out.print(FormatUtil.getString("ms"));
/* 3656 */                                   out.write("</td>\n          <td width=\"36%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3657 */                                   out.print(resourceid);
/* 3658 */                                   out.write("&attributeid=3102')\">");
/* 3659 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3102")));
/* 3660 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 3661 */                                   out.print(FormatUtil.getString("User Connections"));
/* 3662 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 3663 */                                   out.print(data.getProperty("CONNECTIONS"));
/* 3664 */                                   out.write("\n</td>\n\n\t <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3665 */                                   out.print(resourceid);
/* 3666 */                                   out.write("&attributeid=3110')\">");
/* 3667 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3110")));
/* 3668 */                                   out.write("</a></td>\n\n\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3669 */                                   out.print(FormatUtil.getString("Logins/Min"));
/* 3670 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 3671 */                                   out.print(data.getProperty("LOGINSPERMIN"));
/* 3672 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3673 */                                   out.print(resourceid);
/* 3674 */                                   out.write("&attributeid=3111')\">");
/* 3675 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3111")));
/* 3676 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td width=\"37%\" class=\"yellowgrayborder\">");
/* 3677 */                                   out.print(FormatUtil.getString("Logouts/Min"));
/* 3678 */                                   out.write(" </td>\n          <td class=\"yellowgrayborder\">");
/* 3679 */                                   out.print(data.getProperty("LOGOUTSPERMIN"));
/* 3680 */                                   out.write(" </td>\n          <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3681 */                                   out.print(resourceid);
/* 3682 */                                   out.write("&attributeid=3112')\">");
/* 3683 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3112")));
/* 3684 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td colspan=\"3\" align=\"right\" class=\"whitegrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3685 */                                   out.print(resourceid);
/* 3686 */                                   out.write("&attributeIDs=3102,3110,3111,3112&attributeToSelect=3102&redirectto=");
/* 3687 */                                   out.print(encodeurl);
/* 3688 */                                   out.write("'class=\"staticlinks\">");
/* 3689 */                                   out.print(ALERTCONFIG_TEXT);
/* 3690 */                                   out.write("</a></td>\n        </tr>\n      </table></td>\n  <td valign=\"top\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n        <tr>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3691 */                                   out.print(FormatUtil.getString("table.heading.attribute"));
/* 3692 */                                   out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3693 */                                   out.print(FormatUtil.getString("table.heading.value"));
/* 3694 */                                   out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3695 */                                   out.print(FormatUtil.getString("table.heading.status"));
/* 3696 */                                   out.write("</span></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3697 */                                   out.print(FormatUtil.getString("Cache Hit Ratio"));
/* 3698 */                                   out.write("</td>\n          <td width=\"27%\" class=\"whitegrayborder\">");
/* 3699 */                                   out.print(data.getProperty("CACHEHITRATIO"));
/* 3700 */                                   out.write("</td>\n          <td width=\"37%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3701 */                                   out.print(resourceid);
/* 3702 */                                   out.write("&attributeid=3138')\">");
/* 3703 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3138")));
/* 3704 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 3705 */                                   out.print(FormatUtil.getString("Cache Used/Min"));
/* 3706 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 3707 */                                   out.print(data.getProperty("CACHEUSEDPERMIN"));
/* 3708 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3709 */                                   out.print(resourceid);
/* 3710 */                                   out.write("&attributeid=3139')\">");
/* 3711 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3139")));
/* 3712 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3713 */                                   out.print(FormatUtil.getString("Cache Count"));
/* 3714 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 3715 */                                   out.print(data.getProperty("CACHECOUNT"));
/* 3716 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3717 */                                   out.print(resourceid);
/* 3718 */                                   out.write("&attributeid=3140')\">");
/* 3719 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3140")));
/* 3720 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td width=\"36%\" class=\"yellowgrayborder\">");
/* 3721 */                                   out.print(FormatUtil.getString("Cache Pages"));
/* 3722 */                                   out.write("</td>\n          <td  class=\"yellowgrayborder\">");
/* 3723 */                                   out.print(data.getProperty("CACHEPAGES"));
/* 3724 */                                   out.write("&nbsp;</td>\n          <td  class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3725 */                                   out.print(resourceid);
/* 3726 */                                   out.write("&attributeid=3141')\">");
/* 3727 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3141")));
/* 3728 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td colspan=\"3\" align=\"right\" class=\"whitegrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3729 */                                   out.print(resourceid);
/* 3730 */                                   out.write("&attributeIDs=3138,3139,3140,3141&attributeToSelect=3138&redirectto=");
/* 3731 */                                   out.print(encodeurl);
/* 3732 */                                   out.write("'class=\"staticlinks\">");
/* 3733 */                                   out.print(ALERTCONFIG_TEXT);
/* 3734 */                                   out.write("</a></td>\n        </tr>\n      </table></td>\n  </tr>\n  </table>\n  <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td>&nbsp;</td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"50%\" height=\"31\" class=\"tableheading\">");
/* 3735 */                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.lockdetails"));
/* 3736 */                                   out.write(" </td>\n    <td width=\"50%\" height=\"31\" align=\"left\" class=\"tableheading\">");
/* 3737 */                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.sqlstatistics"));
/* 3738 */                                   out.write("</td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n  <td width=\"50%\" height=\"38\"  class=\"rbborder\"><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3739 */                                   out.print(resourceid);
/* 3740 */                                   out.write("&attributeid=3113&period=-7&resourcename=");
/* 3741 */                                   out.print(displayname);
/* 3742 */                                   out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3743 */                                   out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3744 */                                   out.write("\"></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3745 */                                   out.print(resourceid);
/* 3746 */                                   out.write("&attributeid=3113&period=-30&resourcename=");
/* 3747 */                                   out.print(displayname);
/* 3748 */                                   out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3749 */                                   out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3750 */                                   out.write("\"></a></td>\n  </tr>\n  ");
/* 3751 */                                   mssqlgraph.settype("LOCKS");
/* 3752 */                                   out.write("\n  <tr>\n  <td>");
/*      */                                   
/* 3754 */                                   TimeChart _jspx_th_awolf_005ftimechart_005f5 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3755 */                                   _jspx_th_awolf_005ftimechart_005f5.setPageContext(_jspx_page_context);
/* 3756 */                                   _jspx_th_awolf_005ftimechart_005f5.setParent(_jspx_th_c_005fif_005f12);
/*      */                                   
/* 3758 */                                   _jspx_th_awolf_005ftimechart_005f5.setDataSetProducer("mssqlgraph");
/*      */                                   
/* 3760 */                                   _jspx_th_awolf_005ftimechart_005f5.setWidth("330");
/*      */                                   
/* 3762 */                                   _jspx_th_awolf_005ftimechart_005f5.setHeight("170");
/*      */                                   
/* 3764 */                                   _jspx_th_awolf_005ftimechart_005f5.setLegend("true");
/*      */                                   
/* 3766 */                                   _jspx_th_awolf_005ftimechart_005f5.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */                                   
/* 3768 */                                   _jspx_th_awolf_005ftimechart_005f5.setYaxisLabel(FormatUtil.getString("am.webclient.mssqldetails.valuepermin"));
/* 3769 */                                   int _jspx_eval_awolf_005ftimechart_005f5 = _jspx_th_awolf_005ftimechart_005f5.doStartTag();
/* 3770 */                                   if (_jspx_eval_awolf_005ftimechart_005f5 != 0) {
/* 3771 */                                     if (_jspx_eval_awolf_005ftimechart_005f5 != 1) {
/* 3772 */                                       out = _jspx_page_context.pushBody();
/* 3773 */                                       _jspx_th_awolf_005ftimechart_005f5.setBodyContent((BodyContent)out);
/* 3774 */                                       _jspx_th_awolf_005ftimechart_005f5.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3777 */                                       out.write("\n\t              ");
/* 3778 */                                       int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f5.doAfterBody();
/* 3779 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3782 */                                     if (_jspx_eval_awolf_005ftimechart_005f5 != 1) {
/* 3783 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3786 */                                   if (_jspx_th_awolf_005ftimechart_005f5.doEndTag() == 5) {
/* 3787 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f5); return;
/*      */                                   }
/*      */                                   
/* 3790 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f5);
/* 3791 */                                   out.write("</td>\n  </tr>\n  </table>\n  <td width=\"50%\" height=\"38\" class=\"bottomborder\"> <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3792 */                                   out.print(resourceid);
/* 3793 */                                   out.write("&attributeid=3118&period=-7&resourcename=");
/* 3794 */                                   out.print(displayname);
/* 3795 */                                   out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3796 */                                   out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3797 */                                   out.write("\"></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3798 */                                   out.print(resourceid);
/* 3799 */                                   out.write("&attributeid=3118&period=-30&resourcename=");
/* 3800 */                                   out.print(displayname);
/* 3801 */                                   out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3802 */                                   out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3803 */                                   out.write("\"></a></td>\n  </tr>\n  <tr>\n  ");
/* 3804 */                                   mssqlgraph.settype("SQL");
/* 3805 */                                   out.write("\n  <td>");
/*      */                                   
/* 3807 */                                   TimeChart _jspx_th_awolf_005ftimechart_005f6 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3808 */                                   _jspx_th_awolf_005ftimechart_005f6.setPageContext(_jspx_page_context);
/* 3809 */                                   _jspx_th_awolf_005ftimechart_005f6.setParent(_jspx_th_c_005fif_005f12);
/*      */                                   
/* 3811 */                                   _jspx_th_awolf_005ftimechart_005f6.setDataSetProducer("mssqlgraph");
/*      */                                   
/* 3813 */                                   _jspx_th_awolf_005ftimechart_005f6.setWidth("330");
/*      */                                   
/* 3815 */                                   _jspx_th_awolf_005ftimechart_005f6.setHeight("170");
/*      */                                   
/* 3817 */                                   _jspx_th_awolf_005ftimechart_005f6.setLegend("true");
/*      */                                   
/* 3819 */                                   _jspx_th_awolf_005ftimechart_005f6.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */                                   
/* 3821 */                                   _jspx_th_awolf_005ftimechart_005f6.setYaxisLabel(FormatUtil.getString("am.webclient.mssqldetails.valuepermin"));
/* 3822 */                                   int _jspx_eval_awolf_005ftimechart_005f6 = _jspx_th_awolf_005ftimechart_005f6.doStartTag();
/* 3823 */                                   if (_jspx_eval_awolf_005ftimechart_005f6 != 0) {
/* 3824 */                                     if (_jspx_eval_awolf_005ftimechart_005f6 != 1) {
/* 3825 */                                       out = _jspx_page_context.pushBody();
/* 3826 */                                       _jspx_th_awolf_005ftimechart_005f6.setBodyContent((BodyContent)out);
/* 3827 */                                       _jspx_th_awolf_005ftimechart_005f6.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3830 */                                       out.write("\n\t              ");
/* 3831 */                                       int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f6.doAfterBody();
/* 3832 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3835 */                                     if (_jspx_eval_awolf_005ftimechart_005f6 != 1) {
/* 3836 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3839 */                                   if (_jspx_th_awolf_005ftimechart_005f6.doEndTag() == 5) {
/* 3840 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f6); return;
/*      */                                   }
/*      */                                   
/* 3843 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f6);
/* 3844 */                                   out.write("</td>\n  </tr>\n  </table></td>\n  <tr>\n  <td valign=\"top\" class=\"rborder\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n        <tr>\n          <td width=\"60%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3845 */                                   out.print(FormatUtil.getString("table.heading.attribute"));
/* 3846 */                                   out.write("</span></td>\n          <td width=\"20%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3847 */                                   out.print(FormatUtil.getString("table.heading.value"));
/* 3848 */                                   out.write("</span></td>\n          <td width=\"20%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3849 */                                   out.print(FormatUtil.getString("table.heading.status"));
/* 3850 */                                   out.write("</span></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3851 */                                   out.print(FormatUtil.getString("Lock Requests/Min"));
/* 3852 */                                   out.write("</td>\n\t    ");
/*      */                                   
/* 3854 */                                   if ((!"-".equals(data.getProperty("LOCKREQUESTSPERMIN"))) && (!"-1".equals(data.getProperty("LOCKREQUESTSPERMIN")))) {
/* 3855 */                                     out.write("\n\t    <td width=\"29%\" class=\"whitegrayborder\">");
/* 3856 */                                     out.print(df.format(Double.parseDouble(data.getProperty("LOCKREQUESTSPERMIN"))));
/* 3857 */                                     out.write("</td>\n\t    ");
/*      */                                   } else {
/* 3859 */                                     out.write("\n\t    <td width=\"29%\" class=\"whitegrayborder\">");
/* 3860 */                                     out.print(data.getProperty("LOCKREQUESTSPERMIN"));
/* 3861 */                                     out.write("</td>\n\t    ");
/*      */                                   }
/* 3863 */                                   out.write("\n          <td width=\"34%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3864 */                                   out.print(resourceid);
/* 3865 */                                   out.write("&attributeid=3113')\">");
/* 3866 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3113")));
/* 3867 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 3868 */                                   out.print(FormatUtil.getString("Lock Waits/Min"));
/* 3869 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 3870 */                                   out.print(data.getProperty("LOCKWAITSPERMIN"));
/* 3871 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3872 */                                   out.print(resourceid);
/* 3873 */                                   out.write("&attributeid=3114')\">");
/* 3874 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3114")));
/* 3875 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3876 */                                   out.print(FormatUtil.getString("Lock Timeouts/Min"));
/* 3877 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 3878 */                                   out.print(data.getProperty("LOCKTIMEOUTSPERMIN"));
/* 3879 */                                   out.write("\n          </td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3880 */                                   out.print(resourceid);
/* 3881 */                                   out.write("&attributeid=3115')\">");
/* 3882 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3115")));
/* 3883 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td width=\"37%\" class=\"yellowgrayborder\">");
/* 3884 */                                   out.print(FormatUtil.getString("Deadlocks/Min"));
/* 3885 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 3886 */                                   out.print(data.getProperty("DEADLOCKSPERMIN"));
/* 3887 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3888 */                                   out.print(resourceid);
/* 3889 */                                   out.write("&attributeid=3116')\">");
/* 3890 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3116")));
/* 3891 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3892 */                                   out.print(FormatUtil.getString("Average Lock Wait Time"));
/* 3893 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 3894 */                                   if (data.getProperty("AVGLOCKWAITTIME").equals("-1.0")) {
/* 3895 */                                     out.write(" <span class='bodytext'>");
/* 3896 */                                     out.print(FormatUtil.getString("am.webclient.mssqldetails.na"));
/* 3897 */                                     out.write("</span> ");
/*      */                                   } else {
/* 3899 */                                     out.write(32);
/* 3900 */                                     out.print(data.getProperty("AVGLOCKWAITTIME"));
/* 3901 */                                     out.write(32);
/* 3902 */                                     out.print(FormatUtil.getString("ms"));
/*      */                                   }
/* 3904 */                                   out.write("\n            </td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3905 */                                   out.print(resourceid);
/* 3906 */                                   out.write("&attributeid=3117')\">");
/* 3907 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3117")));
/* 3908 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td colspan=\"3\" align=\"right\" class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3909 */                                   out.print(resourceid);
/* 3910 */                                   out.write("&attributeIDs=3113,3114,3115,3116,3117&attributeToSelect=3113&redirectto=");
/* 3911 */                                   out.print(encodeurl);
/* 3912 */                                   out.write("'class=\"staticlinks\">");
/* 3913 */                                   out.print(ALERTCONFIG_TEXT);
/* 3914 */                                   out.write("</a></td>\n        </tr>\n      </table></td>\n  <td valign=\"top\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n        <tr>\n          <td width=\"60%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3915 */                                   out.print(FormatUtil.getString("table.heading.attribute"));
/* 3916 */                                   out.write("</span></td>\n          <td width=\"20%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3917 */                                   out.print(FormatUtil.getString("table.heading.value"));
/* 3918 */                                   out.write("</span></td>\n          <td width=\"20%\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 3919 */                                   out.print(FormatUtil.getString("table.heading.status"));
/* 3920 */                                   out.write("</span></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3921 */                                   out.print(FormatUtil.getString("Batch Requests/Min"));
/* 3922 */                                   out.write("</td>\n          <td width=\"27%\" class=\"whitegrayborder\">");
/* 3923 */                                   out.print(data.getProperty("BATCHREQUESTSPERMIN"));
/* 3924 */                                   out.write("</td>\n          <td width=\"36%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3925 */                                   out.print(resourceid);
/* 3926 */                                   out.write("&attributeid=3118')\">");
/* 3927 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3118")));
/* 3928 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 3929 */                                   out.print(FormatUtil.getString("SQL Compilations/Min"));
/* 3930 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 3931 */                                   out.print(data.getProperty("SQLCOMPILATIONSPERMIN"));
/* 3932 */                                   out.write("\n          </td>\n          <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3933 */                                   out.print(resourceid);
/* 3934 */                                   out.write("&attributeid=3119')\">");
/* 3935 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3119")));
/* 3936 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3937 */                                   out.print(FormatUtil.getString("SQL Recompilations/Min"));
/* 3938 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 3939 */                                   out.print(data.getProperty("SQLRECOMPILATIONSPERMIN"));
/* 3940 */                                   out.write("&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3941 */                                   out.print(resourceid);
/* 3942 */                                   out.write("&attributeid=3120&period=-7&resourcename=");
/* 3943 */                                   out.print(displayname);
/* 3944 */                                   out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"0\" vspace=\"0\" border=\"0\"  title=\"");
/* 3945 */                                   out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3946 */                                   out.write("\" align='absmiddle'></a></td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3947 */                                   out.print(resourceid);
/* 3948 */                                   out.write("&attributeid=3120')\">");
/* 3949 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3120")));
/* 3950 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td width=\"37%\" class=\"yellowgrayborder\">");
/* 3951 */                                   out.print(FormatUtil.getString("AutoParams/Min"));
/* 3952 */                                   out.write("</td>\n          <td   class=\"yellowgrayborder\">");
/* 3953 */                                   out.print(data.getProperty("AUTOPARAMSPERMIN"));
/* 3954 */                                   out.write("&nbsp;</td>\n          <td  class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3955 */                                   out.print(resourceid);
/* 3956 */                                   out.write("&attributeid=3121')\">");
/* 3957 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3121")));
/* 3958 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3959 */                                   out.print(FormatUtil.getString("Failed AutoParams/Min"));
/* 3960 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 3961 */                                   out.print(data.getProperty("FAILEDAUTOPARAMS"));
/* 3962 */                                   out.write("</td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3963 */                                   out.print(resourceid);
/* 3964 */                                   out.write("&attributeid=3122')\">");
/* 3965 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3122")));
/* 3966 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td colspan=\"3\" align=\"right\" class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3967 */                                   out.print(resourceid);
/* 3968 */                                   out.write("&attributeIDs=3118,3119,3120,3121,3122&attributeToSelect=3118&redirectto=");
/* 3969 */                                   out.print(encodeurl);
/* 3970 */                                   out.write("'class=\"staticlinks\">");
/* 3971 */                                   out.print(ALERTCONFIG_TEXT);
/* 3972 */                                   out.write("</a></td>\n        </tr>\n      </table></td>\n  </tr>\n  </table>\n  <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td>&nbsp;</td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"50%\" height=\"31\" class=\"tableheading\">");
/* 3973 */                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.latchdetail"));
/* 3974 */                                   out.write(" </td>\n    <td width=\"50%\" height=\"31\" align=\"left\" class=\"tableheading\">");
/* 3975 */                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.accessmethod"));
/* 3976 */                                   out.write("</td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n  <td width=\"50%\" height=\"38\"  class=\"rbborder\"><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3977 */                                   out.print(resourceid);
/* 3978 */                                   out.write("&attributeid=3124&period=-7&resourcename=");
/* 3979 */                                   out.print(displayname);
/* 3980 */                                   out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3981 */                                   out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3982 */                                   out.write("\"></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3983 */                                   out.print(resourceid);
/* 3984 */                                   out.write("&attributeid=3124&period=-30&resourcename=");
/* 3985 */                                   out.print(displayname);
/* 3986 */                                   out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3987 */                                   out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3988 */                                   out.write("\"></a></td>\n  </tr>\n  <tr>\n   ");
/* 3989 */                                   mssqlgraph.settype("LATCH");
/* 3990 */                                   out.write("\n  <td>");
/*      */                                   
/* 3992 */                                   TimeChart _jspx_th_awolf_005ftimechart_005f7 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3993 */                                   _jspx_th_awolf_005ftimechart_005f7.setPageContext(_jspx_page_context);
/* 3994 */                                   _jspx_th_awolf_005ftimechart_005f7.setParent(_jspx_th_c_005fif_005f12);
/*      */                                   
/* 3996 */                                   _jspx_th_awolf_005ftimechart_005f7.setDataSetProducer("mssqlgraph");
/*      */                                   
/* 3998 */                                   _jspx_th_awolf_005ftimechart_005f7.setWidth("330");
/*      */                                   
/* 4000 */                                   _jspx_th_awolf_005ftimechart_005f7.setHeight("170");
/*      */                                   
/* 4002 */                                   _jspx_th_awolf_005ftimechart_005f7.setLegend("true");
/*      */                                   
/* 4004 */                                   _jspx_th_awolf_005ftimechart_005f7.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */                                   
/* 4006 */                                   _jspx_th_awolf_005ftimechart_005f7.setYaxisLabel(FormatUtil.getString("am.webclient.mssqldetails.valueinms"));
/* 4007 */                                   int _jspx_eval_awolf_005ftimechart_005f7 = _jspx_th_awolf_005ftimechart_005f7.doStartTag();
/* 4008 */                                   if (_jspx_eval_awolf_005ftimechart_005f7 != 0) {
/* 4009 */                                     if (_jspx_eval_awolf_005ftimechart_005f7 != 1) {
/* 4010 */                                       out = _jspx_page_context.pushBody();
/* 4011 */                                       _jspx_th_awolf_005ftimechart_005f7.setBodyContent((BodyContent)out);
/* 4012 */                                       _jspx_th_awolf_005ftimechart_005f7.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 4015 */                                       out.write("\n\t\t                  ");
/* 4016 */                                       int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f7.doAfterBody();
/* 4017 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 4020 */                                     if (_jspx_eval_awolf_005ftimechart_005f7 != 1) {
/* 4021 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 4024 */                                   if (_jspx_th_awolf_005ftimechart_005f7.doEndTag() == 5) {
/* 4025 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f7); return;
/*      */                                   }
/*      */                                   
/* 4028 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f7);
/* 4029 */                                   out.write("</td>\n  </tr>\n  </table>\n  <td width=\"50%\" height=\"38\" class=\"bottomborder\"> <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4030 */                                   out.print(resourceid);
/* 4031 */                                   out.write("&attributeid=3125&period=-7&resourcename=");
/* 4032 */                                   out.print(displayname);
/* 4033 */                                   out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4034 */                                   out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4035 */                                   out.write("\"></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4036 */                                   out.print(resourceid);
/* 4037 */                                   out.write("&attributeid=3125&period=-30&resourcename=");
/* 4038 */                                   out.print(displayname);
/* 4039 */                                   out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4040 */                                   out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4041 */                                   out.write("\"></a></td>\n  </tr>\n  <tr>\n   ");
/* 4042 */                                   mssqlgraph.settype("ACCESS");
/* 4043 */                                   out.write("\n  <td>");
/*      */                                   
/* 4045 */                                   TimeChart _jspx_th_awolf_005ftimechart_005f8 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 4046 */                                   _jspx_th_awolf_005ftimechart_005f8.setPageContext(_jspx_page_context);
/* 4047 */                                   _jspx_th_awolf_005ftimechart_005f8.setParent(_jspx_th_c_005fif_005f12);
/*      */                                   
/* 4049 */                                   _jspx_th_awolf_005ftimechart_005f8.setDataSetProducer("mssqlgraph");
/*      */                                   
/* 4051 */                                   _jspx_th_awolf_005ftimechart_005f8.setWidth("330");
/*      */                                   
/* 4053 */                                   _jspx_th_awolf_005ftimechart_005f8.setHeight("170");
/*      */                                   
/* 4055 */                                   _jspx_th_awolf_005ftimechart_005f8.setLegend("true");
/*      */                                   
/* 4057 */                                   _jspx_th_awolf_005ftimechart_005f8.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */                                   
/* 4059 */                                   _jspx_th_awolf_005ftimechart_005f8.setYaxisLabel(FormatUtil.getString("am.webclient.mssqldetails.scanspermin"));
/* 4060 */                                   int _jspx_eval_awolf_005ftimechart_005f8 = _jspx_th_awolf_005ftimechart_005f8.doStartTag();
/* 4061 */                                   if (_jspx_eval_awolf_005ftimechart_005f8 != 0) {
/* 4062 */                                     if (_jspx_eval_awolf_005ftimechart_005f8 != 1) {
/* 4063 */                                       out = _jspx_page_context.pushBody();
/* 4064 */                                       _jspx_th_awolf_005ftimechart_005f8.setBodyContent((BodyContent)out);
/* 4065 */                                       _jspx_th_awolf_005ftimechart_005f8.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 4068 */                                       out.write("\n   ");
/* 4069 */                                       int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f8.doAfterBody();
/* 4070 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 4073 */                                     if (_jspx_eval_awolf_005ftimechart_005f8 != 1) {
/* 4074 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 4077 */                                   if (_jspx_th_awolf_005ftimechart_005f8.doEndTag() == 5) {
/* 4078 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f8); return;
/*      */                                   }
/*      */                                   
/* 4081 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f8);
/* 4082 */                                   out.write("</td>\n  </tr>\n  </table></td>\n  <tr>\n  <tr>\n  <td valign=\"top\" class=\"rborder\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n        <tr>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 4083 */                                   out.print(FormatUtil.getString("table.heading.attribute"));
/* 4084 */                                   out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 4085 */                                   out.print(FormatUtil.getString("table.heading.value"));
/* 4086 */                                   out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 4087 */                                   out.print(FormatUtil.getString("table.heading.status"));
/* 4088 */                                   out.write("</span></td>\n        </tr>\n        <tr>\n          <td width=\"37%\" class=\"whitegrayborder\">");
/* 4089 */                                   out.print(FormatUtil.getString("Latch Waits/Min"));
/* 4090 */                                   out.write("</td>\n          <td width=\"26%\" class=\"whitegrayborder\">");
/* 4091 */                                   out.print(data.getProperty("LATCHWAITSPERMIN"));
/* 4092 */                                   out.write("</td>\n          <td width=\"37%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4093 */                                   out.print(resourceid);
/* 4094 */                                   out.write("&attributeid=3123')\">");
/* 4095 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3123")));
/* 4096 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 4097 */                                   out.print(FormatUtil.getString("Average Latch Wait Time"));
/* 4098 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4099 */                                   out.print(data.getProperty("AVGLATCHWAITTIME"));
/* 4100 */                                   out.write("\n              ms</td><!--No I18N-->\n          <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4101 */                                   out.print(resourceid);
/* 4102 */                                   out.write("&attributeid=3124')\">");
/* 4103 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3124")));
/* 4104 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td colspan=\"3\" align=\"right\" class=\"whitegrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4105 */                                   out.print(resourceid);
/* 4106 */                                   out.write("&attributeIDs=3123,3124&attributeToSelect=3123&redirectto=");
/* 4107 */                                   out.print(encodeurl);
/* 4108 */                                   out.write("'class=\"staticlinks\">");
/* 4109 */                                   out.print(ALERTCONFIG_TEXT);
/* 4110 */                                   out.write("</a></td>\n        </tr>\n      </table></td>\n  <td valign=\"top\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n        <tr>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 4111 */                                   out.print(FormatUtil.getString("table.heading.attribute"));
/* 4112 */                                   out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 4113 */                                   out.print(FormatUtil.getString("table.heading.value"));
/* 4114 */                                   out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 4115 */                                   out.print(FormatUtil.getString("table.heading.status"));
/* 4116 */                                   out.write("</span></td>\n        </tr>\n        <tr>\n          <td width=\"37%\" class=\"whitegrayborder\">");
/* 4117 */                                   out.print(FormatUtil.getString("Full Scans/Min"));
/* 4118 */                                   out.write("</td>\n          <td width=\"26%\" class=\"whitegrayborder\">");
/* 4119 */                                   out.print(data.getProperty("FULLSCANSPERMIN"));
/* 4120 */                                   out.write("</td>\n          <td width=\"37%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4121 */                                   out.print(resourceid);
/* 4122 */                                   out.write("&attributeid=3125')\">");
/* 4123 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3125")));
/* 4124 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 4125 */                                   out.print(FormatUtil.getString("Range Scans/Min"));
/* 4126 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4127 */                                   out.print(data.getProperty("RANGESCANSPERMIN"));
/* 4128 */                                   out.write("</td>\n          <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4129 */                                   out.print(resourceid);
/* 4130 */                                   out.write("&attributeid=3126')\">");
/* 4131 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3126")));
/* 4132 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 4133 */                                   out.print(FormatUtil.getString("Probe Scans/Min"));
/* 4134 */                                   out.write("</td>\n          ");
/* 4135 */                                   if ((!"-".equals(data.getProperty("PROBESCANSPERMIN"))) && (!"-1".equals(data.getProperty("PROBESCANSPERMIN")))) {
/* 4136 */                                     out.write("\n\t    <td class=\"whitegrayborder\">");
/* 4137 */                                     out.print(df.format(Double.parseDouble(data.getProperty("PROBESCANSPERMIN"))));
/* 4138 */                                     out.write("</td>\n\t    ");
/*      */                                   } else {
/* 4140 */                                     out.write("\n\t    <td class=\"whitegrayborder\">");
/* 4141 */                                     out.print(data.getProperty("PROBESCANSPERMIN"));
/* 4142 */                                     out.write("</td>\n          ");
/*      */                                   }
/* 4144 */                                   out.write("\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4145 */                                   out.print(resourceid);
/* 4146 */                                   out.write("&attributeid=3127')\">");
/* 4147 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3127")));
/* 4148 */                                   out.write("</a></td>\n        </tr>\n        <tr>\n          <td colspan=\"3\" align=\"right\" class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4149 */                                   out.print(resourceid);
/* 4150 */                                   out.write("&attributeIDs=3125,3126,3127&attributeToSelect=3125&redirectto=");
/* 4151 */                                   out.print(encodeurl);
/* 4152 */                                   out.write("'class=\"staticlinks\">");
/* 4153 */                                   out.print(ALERTCONFIG_TEXT);
/* 4154 */                                   out.write("</a></td>\n        </tr>\n      \t</table></td>\n  \t</tr>\n  \t</table>\n  \t<br>\n  \t");
/*      */                                   
/* 4156 */                                   Hashtable disableTable = com.adventnet.appmanager.util.EnterpriseUtil.getDisableTable();
/* 4157 */                                   String jobsenabled = (String)disableTable.get("MSSQL-DB-server#SCHEDULEDJOBS");
/* 4158 */                                   if ((jobsenabled != null) && (jobsenabled.equals("true")))
/*      */                                   {
/*      */ 
/* 4161 */                                     out.write("\n     \t<form name=\"sqlJobsAction\" action=\"/MSSqlDispatch.do\" >\n     \t<input type=\"hidden\" name=\"sqljobmanagement\" value=\"true\"/>\n     \t<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 4162 */                                     if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fif_005f12, _jspx_page_context))
/*      */                                       return;
/* 4164 */                                     out.write("\"/>\n\t\t<input type=\"hidden\" name=\"method\" value=\"overviewDetails\"/>\n\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t\t\t<tr><td width=\"100%\" height=\"31\" class=\"tableheading\">");
/* 4165 */                                     out.print(FormatUtil.getString("am.webclient.mssql.jobdetails.tableheading"));
/* 4166 */                                     out.write(" </td>\n\t\t</table>\n      \t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n      \t<tr class=\"bodytextbold\">\n      \t<td width=\"3%\"  align=\"center\"  class=\"columnheading\"><input type=\"checkbox\" name=\"sqljobs\"  onClick=\"javascript:fnSelectAll(this,'sqljobs')\"></td>\n        <td width=\"20%\" height=\"28\"  class=\"columnheading\">");
/* 4167 */                                     out.print(FormatUtil.getString("am.webclient.mssql.jobdetails.jobname"));
/* 4168 */                                     out.write("</td>\n        <td width=\"13%\" height=\"28\"  class=\"columnheading\">");
/* 4169 */                                     out.print(FormatUtil.getString("am.webclient.mssql.jobdetails.currentjobstatus"));
/* 4170 */                                     out.write("</td>\n        <td width=\"12%\" height=\"28\"  class=\"columnheading\">");
/* 4171 */                                     out.print(FormatUtil.getString("am.webclient.mssql.jobdetails.lastrunstatus"));
/* 4172 */                                     out.write("</td>\n        <td width=\"15%\" height=\"28\"  class=\"columnheading\">");
/* 4173 */                                     out.print(FormatUtil.getString("am.webclient.mssql.jobdetails.rundate"));
/* 4174 */                                     out.write("</td>\n        <td width=\"10%\" height=\"28\"  align=\"left\" class=\"columnheading\">");
/* 4175 */                                     out.print(FormatUtil.getString("am.webclient.mssql.jobdetails.runduration"));
/* 4176 */                                     out.write("</td>\n        <td width=\"15%\" height=\"28\"  align=\"left\" class=\"columnheading\">");
/* 4177 */                                     out.print(FormatUtil.getString("am.webclient.mssql.jobdetails.retries"));
/* 4178 */                                     out.write("</td>\n        <td width=\"5%\" height=\"28\" class=\"columnheading\" align=\"center\" >");
/* 4179 */                                     out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4180 */                                     out.write("</td>\n        <td width=\"7%\" height=\"28\"  class=\"columnheading\"><span class=\"bodytextbold\">&nbsp;</td>\n     \t</tr>\n");
/*      */                                     
/*      */                                     try
/*      */                                     {
/* 4184 */                                       ArrayList jobDetails = (ArrayList)request.getAttribute("JOBS");
/* 4185 */                                       int jobid = -1;
/* 4186 */                                       if ((jobDetails == null) || (jobDetails.size() == 0))
/*      */                                       {
/* 4188 */                                         out.write("\n\t<tr class=\"whitegrayborder\">\n       \t<td width=\"26%\"  colspan=\"9\" height=\"28\"  align=\"center\">");
/* 4189 */                                         out.print(FormatUtil.getString("am.webclient.mssqldetails.nojobdetails"));
/* 4190 */                                         out.write("</td>\n       \t</tr>\n");
/*      */                                       }
/*      */                                       else {
/* 4193 */                                         int l = 0;
/* 4194 */                                         String textclass = "";
/* 4195 */                                         pageContext.setAttribute("hasSqlJobs", Boolean.valueOf(true));
/* 4196 */                                         for (int i = 0; i < jobDetails.size(); i++) {
/* 4197 */                                           Properties jobProperties = new Properties();
/* 4198 */                                           jobProperties = (Properties)jobDetails.get(i);
/* 4199 */                                           if (jobProperties != null) {
/* 4200 */                                             jobid = Integer.parseInt(jobProperties.getProperty("JOBID"));
/*      */                                             try {
/* 4202 */                                               int isDCEnabled = com.adventnet.appmanager.util.DBUtil.getDCStarted(jobid);
/* 4203 */                                               if (isDCEnabled == 2) {
/* 4204 */                                                 textclass = "class=\"disabledtext\"";
/* 4205 */                                                 alert.remove(jobid + "#" + "3161");
/*      */                                               } else {
/* 4207 */                                                 textclass = "";
/*      */                                               }
/*      */                                             } catch (Exception ex) {
/* 4210 */                                               ex.printStackTrace();
/*      */                                             }
/*      */                                             
/*      */ 
/* 4214 */                                             String jobstatus = "";
/* 4215 */                                             int status = Integer.parseInt(jobProperties.getProperty("JOB_STATUS"));
/* 4216 */                                             if (status == 0) {
/* 4217 */                                               jobstatus = FormatUtil.getString("am.webclient.mssql.jobdetails.jobstatus.failed");
/* 4218 */                                             } else if (status == 1) {
/* 4219 */                                               jobstatus = FormatUtil.getString("am.webclient.mssql.jobdetails.jobstatus.success");
/* 4220 */                                             } else if (status == 2) {
/* 4221 */                                               jobstatus = FormatUtil.getString("am.webclient.mssql.jobdetails.jobstatus.retrying");
/* 4222 */                                             } else if (status == 3) {
/* 4223 */                                               jobstatus = FormatUtil.getString("am.webclient.mssql.jobdetails.jobstatus.cancelled");
/* 4224 */                                             } else if (status == 4) {
/* 4225 */                                               jobstatus = FormatUtil.getString("am.webclient.mssql.jobdetails.jobstatus.inprogress");
/* 4226 */                                             } else if (status == -1) {
/* 4227 */                                               jobstatus = "-";
/*      */                                             }
/* 4229 */                                             String jobcurstatus = "";
/* 4230 */                                             int curstatus = Integer.parseInt(jobProperties.getProperty("JOB_CURRENT_STATUS"));
/*      */                                             
/* 4232 */                                             if (curstatus == 0) {
/* 4233 */                                               jobcurstatus = FormatUtil.getString("am.webclient.mssql.jobdetails.jobcurstatus.notidle");
/* 4234 */                                             } else if (curstatus == 1) {
/* 4235 */                                               jobcurstatus = FormatUtil.getString("am.webclient.mssql.jobdetails.jobcurstatus.executing");
/* 4236 */                                             } else if (curstatus == 2) {
/* 4237 */                                               jobcurstatus = FormatUtil.getString("am.webclient.mssql.jobdetails.jobcurstatus.waitforthread");
/* 4238 */                                             } else if (curstatus == 3) {
/* 4239 */                                               jobcurstatus = FormatUtil.getString("am.webclient.mssql.jobdetails.jobcurstatus.betretries");
/* 4240 */                                             } else if (curstatus == 4) {
/* 4241 */                                               jobcurstatus = FormatUtil.getString("am.webclient.mssql.jobdetails.jobcurstatus.idle");
/* 4242 */                                             } else if (curstatus == 5) {
/* 4243 */                                               jobcurstatus = FormatUtil.getString("am.webclient.mssql.jobdetails.jobcurstatus.suspended");
/* 4244 */                                             } else if (curstatus == 7) {
/* 4245 */                                               jobcurstatus = FormatUtil.getString("am.webclient.mssql.jobdetails.jobcurstatus.completionaction");
/*      */                                             } else
/* 4247 */                                               jobcurstatus = FormatUtil.getString("Unknown");
/*      */                                             String bgcolor;
/* 4249 */                                             String bgcolor; if (l % 2 == 0)
/*      */                                             {
/* 4251 */                                               bgcolor = "whitegrayborder";
/*      */                                             }
/*      */                                             else {
/* 4254 */                                               bgcolor = "yellowgrayborder";
/*      */                                             }
/* 4256 */                                             l++;
/* 4257 */                                             if ("-1".equals(jobProperties.getProperty("RUN_DATE"))) {
/* 4258 */                                               jobProperties.setProperty("RUN_DATE", "-");
/*      */                                             }
/* 4260 */                                             if ("-1".equals(jobProperties.getProperty("RUN_DURATION"))) {
/* 4261 */                                               jobProperties.setProperty("RUN_DURATION", "-");
/*      */                                             }
/* 4263 */                                             if ("-1".equals(jobProperties.getProperty("RETRIES_ATTEMPTED"))) {
/* 4264 */                                               jobProperties.setProperty("RETRIES_ATTEMPTED", "-");
/*      */                                             }
/*      */                                             
/* 4267 */                                             out.write("\n    \t<tr class=\"");
/* 4268 */                                             out.print(bgcolor);
/* 4269 */                                             out.write("\">\n    \t<td width=\"3%\"  class=\"");
/* 4270 */                                             out.print(bgcolor);
/* 4271 */                                             out.write("\"> <input type=\"checkbox\" name=\"sqljobscheckbox\"  value=\"");
/* 4272 */                                             out.print(jobid);
/* 4273 */                                             out.write("\" ></input></td>\n    \t<td width=\"20%\"  class=\"");
/* 4274 */                                             out.print(bgcolor);
/* 4275 */                                             out.write("\" title='");
/* 4276 */                                             out.print(jobProperties.getProperty("JOBNAME"));
/* 4277 */                                             out.write("'><span ");
/* 4278 */                                             out.print(textclass);
/* 4279 */                                             out.write(62);
/* 4280 */                                             out.print(getTrimmedText(jobProperties.getProperty("JOBNAME"), 35));
/* 4281 */                                             out.write("</span></td>\n    \t<td width=\"13%\"  class=\"");
/* 4282 */                                             out.print(bgcolor);
/* 4283 */                                             out.write("\"><span ");
/* 4284 */                                             out.print(textclass);
/* 4285 */                                             out.write(62);
/* 4286 */                                             out.print(jobcurstatus);
/* 4287 */                                             out.write("</span></td>\n    \t<td width=\"12%\"  class=\"");
/* 4288 */                                             out.print(bgcolor);
/* 4289 */                                             out.write("\"><span ");
/* 4290 */                                             out.print(textclass);
/* 4291 */                                             out.write(62);
/* 4292 */                                             out.print(jobstatus);
/* 4293 */                                             out.write("</span></td>\n    \t<td width=\"15%\" class=\"");
/* 4294 */                                             out.print(bgcolor);
/* 4295 */                                             out.write("\"><span ");
/* 4296 */                                             out.print(textclass);
/* 4297 */                                             out.write(62);
/* 4298 */                                             out.print(jobProperties.getProperty("RUN_DATE"));
/* 4299 */                                             out.write("</span></td>\n    \t<td width=\"10%\" class=\"");
/* 4300 */                                             out.print(bgcolor);
/* 4301 */                                             out.write("\" align=\"left\" ><span ");
/* 4302 */                                             out.print(textclass);
/* 4303 */                                             out.write(62);
/* 4304 */                                             out.print(jobProperties.getProperty("RUN_DURATION"));
/* 4305 */                                             out.write("</span></td>\n    \t<td width=\"15%\" class=\"");
/* 4306 */                                             out.print(bgcolor);
/* 4307 */                                             out.write("\" align=\"left\" ><span ");
/* 4308 */                                             out.print(textclass);
/* 4309 */                                             out.write(62);
/* 4310 */                                             out.print(jobProperties.getProperty("RETRIES_ATTEMPTED"));
/* 4311 */                                             out.write("</span></td>\n    \t<td width=\"5%\"  class=\"");
/* 4312 */                                             out.print(bgcolor);
/* 4313 */                                             out.write("\" align=\"center\" ><span ");
/* 4314 */                                             out.print(textclass);
/* 4315 */                                             out.write("><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4316 */                                             out.print(jobid);
/* 4317 */                                             out.write("&attributeid=3161')\">");
/* 4318 */                                             out.print(getSeverityImageForHealth(alert.getProperty(jobid + "#" + "3161")));
/* 4319 */                                             out.write("</a></span></td>\n    \t<td width=\"7%\" class=\"");
/* 4320 */                                             out.print(bgcolor);
/* 4321 */                                             out.write("\"  align=\"center\" >\n\n    \t&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4322 */                                             out.print(jobid);
/* 4323 */                                             out.write("&attributeIDs=3159,3160,3161&attributeToSelect=3161&redirectto=");
/* 4324 */                                             out.print(encodeurl);
/* 4325 */                                             out.write("'class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" title=\"");
/* 4326 */                                             out.print(ALERTCONFIG_TEXT);
/* 4327 */                                             out.write("\" border=\"0\" /></a>\n    \t</td>\n\t</tr>\n");
/*      */                                           }
/*      */                                         }
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Exception exception) {
/* 4333 */                                       exception.printStackTrace();
/*      */                                     }
/*      */                                     
/* 4336 */                                     out.write("\n\t</table>\n\t\t");
/*      */                                     
/* 4338 */                                     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4339 */                                     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 4340 */                                     _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f12);
/*      */                                     
/* 4342 */                                     _jspx_th_c_005fif_005f13.setTest("${hasSqlJobs }");
/* 4343 */                                     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 4344 */                                     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                       for (;;) {
/* 4346 */                                         out.write("\n\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t\t      \t<tr>\n\t\t      \t\t<td width=\"100%\" height=\"31\" class=\"tableheading\">\n\t\t\t      \t\t");
/* 4347 */                                         out.print(FormatUtil.getString("am.webclient.common.actions.text"));
/* 4348 */                                         out.write("&nbsp;\n\t\t\t\t      \t<select class=\"formtext\" name=\"sqljobActions\" onchange=\"changeAction(this.value)\">\n\t\t\t\t      \t\t<option SELECTED value=\"Default\">--");
/* 4349 */                                         out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 4350 */                                         out.write("--</option>\n\t\t\t\t      \t\t<option value=\"enable\">");
/* 4351 */                                         out.print(FormatUtil.getString("Enable"));
/* 4352 */                                         out.write("</option>\n\t\t\t\t      \t\t<option value=\"disable\">");
/* 4353 */                                         out.print(FormatUtil.getString("Disable"));
/* 4354 */                                         out.write("</option>\n\t\t\t\t      \t</select>\n\t\t      \t\t</td>\n\t\t      \t</tr>\n\t      \t</table>\n      \t");
/* 4355 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 4356 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4360 */                                     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 4361 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                                     }
/*      */                                     
/* 4364 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4365 */                                     out.write("\n      \t</form>\n\t");
/*      */                                   }
/*      */                                   else {
/* 4368 */                                     out.write("\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t      \t<tr><td width=\"100%\" height=\"31\" class=\"tableheading\">");
/* 4369 */                                     out.print(FormatUtil.getString("am.webclient.mssql.jobdetails.tableheading"));
/* 4370 */                                     out.write(" </td>\n      \t</table>\n      \t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n      \t<tr   height=\"45\" >\n      \t \t");
/*      */                                     
/* 4372 */                                     if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (Integer.parseInt(request.getParameter("resourceid")) < com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */                                     {
/* 4374 */                                       out.write("\n\n      \t<td class=\"whitegrayborder\" align=\"center\">");
/* 4375 */                                       out.print(FormatUtil.getString("am.webclient.mssqljobs.configure"));
/* 4376 */                                       out.write("</td>\n\n\t\t\t  ");
/*      */                                     }
/*      */                                     else {
/* 4379 */                                       out.write("\n\t\t\t\t  ");
/*      */                                       
/* 4381 */                                       PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4382 */                                       _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 4383 */                                       _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f12);
/*      */                                       
/* 4385 */                                       _jspx_th_logic_005fpresent_005f2.setRole("ENTERPRISEADMIN");
/* 4386 */                                       int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 4387 */                                       if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                         for (;;) {
/* 4389 */                                           out.write("\n\n\t\t\t\t\t\t  <td class=\"whitegrayborder\" align=\"center\">");
/* 4390 */                                           out.print(FormatUtil.getString("am.webclient.mssql.jobdetails.disabled"));
/* 4391 */                                           out.write("</td>\n\n\t\t\t\t  ");
/* 4392 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 4393 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4397 */                                       if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 4398 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                       }
/*      */                                       
/* 4401 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4402 */                                       out.write("\n\t\t\t  ");
/*      */                                     }
/*      */                                     
/* 4405 */                                     out.write("\n\t\t ");
/*      */                                     
/* 4407 */                                     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4408 */                                     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 4409 */                                     _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f12);
/*      */                                     
/* 4411 */                                     _jspx_th_c_005fif_005f14.setTest("${!empty ADMIN || !empty DEMO}");
/* 4412 */                                     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 4413 */                                     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                       for (;;) {
/* 4415 */                                         out.write("\n\n\t\t\t\t<td class=\"whitegrayborder\" align=\"center\">");
/* 4416 */                                         out.print(FormatUtil.getString("am.webclient.mssqljobs.configure"));
/* 4417 */                                         out.write("</td>\n\n\t\t ");
/* 4418 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 4419 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4423 */                                     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 4424 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                     }
/*      */                                     
/* 4427 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 4428 */                                     out.write("\n\n      \t</tr>\n      \t</table>\n      \t");
/*      */                                   }
/* 4430 */                                   out.write("\n\t<br>\n\t<form name=\"sqlDatabaseAction\" action=\"/MSSqlDispatch.do\" >\n     \t<input type=\"hidden\" name=\"sqldbmanagement\" value=\"true\"/>\n     \t<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 4431 */                                   if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fif_005f12, _jspx_page_context))
/*      */                                     return;
/* 4433 */                                   out.write("\"/>\n\t\t<input type=\"hidden\" name=\"method\" value=\"overviewDetails\"/>\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr >\n  <td height=\"26\"  class=\"tableheadingtrans\">");
/* 4434 */                                   out.print(FormatUtil.getString("am.webclient.mysql.databasedetails"));
/* 4435 */                                   out.write("</td>\n  </tr>\n  </table>\n  <table width=\"99%\" id=\"databaseDetails\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr class=\"bodytextbold\">\n  <td width=\"3%\"  align=\"center\"  class=\"columnheading\"><input type=\"checkbox\" name=\"sqldatabase\"  onClick=\"javascript:fnSelectAll(this,'sqldb')\"></td>\n  <td width=\"23%\" height=\"28\"  class=\"columnheading\">");
/* 4436 */                                   out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 4437 */                                   out.write("</td>\n  <td width=\"11%\" height=\"28\"  class=\"columnheading\">");
/* 4438 */                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.sizeinmb"));
/* 4439 */                                   out.write("</td>\n  <td width=\"11%\" height=\"28\"  class=\"columnheading\">");
/* 4440 */                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.logfileusedsize.mb"));
/* 4441 */                                   out.write("</td>\n  <td width=\"11%\" height=\"28\"  class=\"columnheading\">");
/* 4442 */                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.logused"));
/* 4443 */                                   out.write("</td>\n  <td width=\"15%\" height=\"28\"  class=\"columnheading\">");
/* 4444 */                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.dbstatus"));
/* 4445 */                                   out.write("</td>\n  <td width=\"9%\" height=\"28\"  class=\"columnheading\">");
/* 4446 */                                   out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 4447 */                                   out.write("</td>\n  <td width=\"9%\" align=\"center\" height=\"28\"  class=\"columnheading\">");
/* 4448 */                                   out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4449 */                                   out.write("</td>\n  <td width=\"8\" height=\"12\"    class=\"columnheading\">");
/* 4450 */                                   out.print(ALERTCONFIG_TEXT);
/* 4451 */                                   out.write("</td>\n  </tr>\n  ");
/*      */                                   
/* 4453 */                                   EmptyTag _jspx_th_logic_005fempty_005f2 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4454 */                                   _jspx_th_logic_005fempty_005f2.setPageContext(_jspx_page_context);
/* 4455 */                                   _jspx_th_logic_005fempty_005f2.setParent(_jspx_th_c_005fif_005f12);
/*      */                                   
/* 4457 */                                   _jspx_th_logic_005fempty_005f2.setName("dbdetails");
/* 4458 */                                   int _jspx_eval_logic_005fempty_005f2 = _jspx_th_logic_005fempty_005f2.doStartTag();
/* 4459 */                                   if (_jspx_eval_logic_005fempty_005f2 != 0) {
/*      */                                     for (;;) {
/* 4461 */                                       out.write("\n  <tr class=\"whitegrayborder\">\n  <td width=\"26%\" colspan=\"9\" height=\"28\"  align=\"center\">");
/* 4462 */                                       out.print(FormatUtil.getString("am.webclient.mssqldetails.nodbdetails"));
/* 4463 */                                       out.write("</td>\n  </tr>\n  ");
/* 4464 */                                       int evalDoAfterBody = _jspx_th_logic_005fempty_005f2.doAfterBody();
/* 4465 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4469 */                                   if (_jspx_th_logic_005fempty_005f2.doEndTag() == 5) {
/* 4470 */                                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2); return;
/*      */                                   }
/*      */                                   
/* 4473 */                                   this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 4474 */                                   out.write(10);
/* 4475 */                                   out.write(32);
/* 4476 */                                   out.write(32);
/*      */                                   
/* 4478 */                                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4479 */                                   _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 4480 */                                   _jspx_th_logic_005fnotEmpty_005f3.setParent(_jspx_th_c_005fif_005f12);
/*      */                                   
/* 4482 */                                   _jspx_th_logic_005fnotEmpty_005f3.setName("dbdetails");
/* 4483 */                                   int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 4484 */                                   if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */                                     for (;;) {
/* 4486 */                                       out.write(10);
/* 4487 */                                       out.write(32);
/* 4488 */                                       out.write(32);
/*      */                                       
/* 4490 */                                       dbinfo = (Hashtable)request.getAttribute("dbdetails");
/* 4491 */                                       int l = 0;
/* 4492 */                                       for (Enumeration e = dbinfo.keys(); e.hasMoreElements();)
/*      */                                       {
/* 4494 */                                         String dbname = (String)e.nextElement();
/* 4495 */                                         Properties dbprop = (Properties)dbinfo.get(dbname);
/* 4496 */                                         String bgcolor; String bgcolor; if (l % 2 == 0)
/*      */                                         {
/* 4498 */                                           bgcolor = "whitegrayborder";
/*      */                                         }
/*      */                                         else {
/* 4501 */                                           bgcolor = "yellowgrayborder";
/*      */                                         }
/* 4503 */                                         l++;
/*      */                                         
/* 4505 */                                         out.write("\n  <tr class=\"");
/* 4506 */                                         out.print(bgcolor);
/* 4507 */                                         out.write("\">\n\n<td> <input type=\"checkbox\" name=\"sqldatabasecheckbox\"  value=\"");
/* 4508 */                                         out.print(dbprop.getProperty("DBID"));
/* 4509 */                                         out.write("\" ></input></td>\n<td ><a href=\"#\" onclick=\"javascript:getOverviewData('");
/* 4510 */                                         out.print(resourceid);
/* 4511 */                                         out.write("','','','DB','&name=");
/* 4512 */                                         out.print(name);
/* 4513 */                                         out.write("&haid=");
/* 4514 */                                         out.print(haid);
/* 4515 */                                         out.write("&appName=");
/* 4516 */                                         out.print(appname);
/* 4517 */                                         out.write("&resourcename=");
/* 4518 */                                         out.print(displayname);
/* 4519 */                                         out.write("&dbid=");
/* 4520 */                                         out.print(dbprop.getProperty("DBID"));
/* 4521 */                                         out.write("&dbname=");
/* 4522 */                                         out.print(java.net.URLEncoder.encode(dbname));
/* 4523 */                                         out.write("');\" class=\"ResourceName\">");
/* 4524 */                                         out.print(dbname);
/* 4525 */                                         out.write("</a></td>\n");
/*      */                                         
/* 4527 */                                         if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D))
/*      */                                         {
/* 4529 */                                           out.write("\n<td class=\"ResourceName\" align=\"center\">-&nbsp;</td>\n");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 4533 */                                           out.write("\n<td class=\"ResourceName\" align=\"center\"> ");
/* 4534 */                                           out.print(df.format(Double.parseDouble(dbprop.getProperty("DATAFILESSIZE"))));
/* 4535 */                                           out.write(" &nbsp;</td>\n");
/*      */                                         }
/* 4537 */                                         out.write(10);
/* 4538 */                                         out.write(10);
/*      */                                         
/* 4540 */                                         if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D))
/*      */                                         {
/* 4542 */                                           out.write("\n<td class=\"ResourceName\" align=\"center\">-&nbsp;</td>\n");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 4546 */                                           out.write("\n<td class=\"ResourceName\" align=\"center\"> ");
/* 4547 */                                           out.print(df.format(Double.parseDouble(dbprop.getProperty("LOGFILEUSEDSIZE"))));
/* 4548 */                                           out.write(" &nbsp;</td>\n");
/*      */                                         }
/* 4550 */                                         if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D))
/*      */                                         {
/* 4552 */                                           out.write("\n<td class=\"ResourceName\" align=\"center\">-&nbsp;</td>\n");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 4556 */                                           out.write("\n<td class=\"ResourceName\" align=\"center\"> ");
/* 4557 */                                           out.print(df.format(Double.parseDouble(dbprop.getProperty("LOGUSEDPERCENT"))));
/* 4558 */                                           out.write(" &nbsp;</td>\n");
/*      */                                         }
/* 4560 */                                         out.write("\n\n\n\n");
/* 4561 */                                         if (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE")) {
/* 4562 */                                           out.write("\n<td class=\"ResourceName\"> ");
/* 4563 */                                           out.print(dbprop.getProperty("STATUS"));
/* 4564 */                                           out.write(32);
/* 4565 */                                           out.write(45);
/* 4566 */                                           out.write(32);
/* 4567 */                                           out.print(dbprop.getProperty("STATUS1"));
/* 4568 */                                           out.write("</td>\n");
/*      */                                         } else {
/* 4570 */                                           out.write("\n<td class=\"ResourceName\"> ");
/* 4571 */                                           out.print(dbprop.getProperty("STATUS"));
/* 4572 */                                           out.write(" &nbsp;</td>\n");
/*      */                                         }
/* 4574 */                                         out.write("\n\n\n\n  <td align=\"left\">\n  <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4575 */                                         out.print(dbprop.getProperty("DBID"));
/* 4576 */                                         out.write("&attributeid=3151')\">");
/* 4577 */                                         out.print(getSeverityImageForAvailability(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3151")));
/* 4578 */                                         out.write("</a>\n &nbsp;&nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 4579 */                                         out.print(dbprop.getProperty("DBID"));
/* 4580 */                                         out.write("&period=1&resourcename=");
/* 4581 */                                         out.print(dbname);
/* 4582 */                                         out.write("')\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"0\" vspace=\"0\" border=\"0\"  title=\"");
/* 4583 */                                         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4584 */                                         out.write("\"></a></td>\n  <td align=\"left\"><center>\n  <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4585 */                                         out.print(dbprop.getProperty("DBID"));
/* 4586 */                                         out.write("&attributeid=3150')\">");
/* 4587 */                                         out.print(getSeverityImageForHealth(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3150")));
/* 4588 */                                         out.write("</a>\n  </center></td>\n  <td width=\"19%\" align=\"center\" ><a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4589 */                                         out.print(dbprop.getProperty("DBID"));
/* 4590 */                                         out.write("&attributeIDs=3150,3128,3129,3130&attributeToSelect=3150&redirectto=");
/* 4591 */                                         out.print(encodeurl);
/* 4592 */                                         out.write("'>\n  <img src=\"/images/icon_associateaction.gif\" title=\"");
/* 4593 */                                         out.print(ALERTCONFIG_TEXT);
/* 4594 */                                         out.write("\" border=\"0\" ></a></td>\n  </tr>\n  ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 4598 */                                       out.write(10);
/* 4599 */                                       out.write(32);
/* 4600 */                                       out.write(32);
/* 4601 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 4602 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4606 */                                   if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 4607 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3); return;
/*      */                                   }
/*      */                                   
/* 4610 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 4611 */                                   out.write("\n  </table>\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t\t      \t<tr>\n\t\t      \t\t<td width=\"100%\" height=\"31\" class=\"tableheading\">\n\t\t\t      \t\t");
/* 4612 */                                   out.print(FormatUtil.getString("am.webclient.common.actions.text"));
/* 4613 */                                   out.write("&nbsp;\n\t\t\t\t      \t<select class=\"formtext\" name=\"sqldbActions\" onchange=\"changeDBAction(this.value)\">\n\t\t\t\t      \t\t<option SELECTED value=\"Default\">--");
/* 4614 */                                   out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 4615 */                                   out.write("--</option>\n\t\t\t\t      \t\t<option value=\"enable\">");
/* 4616 */                                   out.print(FormatUtil.getString("Enable"));
/* 4617 */                                   out.write("</option>\n\t\t\t\t      \t\t<option value=\"disable\">");
/* 4618 */                                   out.print(FormatUtil.getString("Disable"));
/* 4619 */                                   out.write("</option>\n\t\t\t\t      \t</select>\n\t\t      \t\t</td>\n\t\t      \t</tr>\n\t</table>\n\t</form>\t\n  ");
/* 4620 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 4621 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4625 */                               if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 4626 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                               }
/*      */                               
/* 4629 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4630 */                               out.write(10);
/* 4631 */                               out.write(32);
/* 4632 */                               out.write(32);
/* 4633 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 4634 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4638 */                           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 4639 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                           }
/*      */                           
/* 4642 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4643 */                           out.write(10);
/* 4644 */                           out.write(32);
/* 4645 */                           out.write(32);
/*      */                           
/* 4647 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4648 */                           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 4649 */                           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 4650 */                           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 4651 */                           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                             for (;;) {
/* 4653 */                               out.write("\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n      <td align=\"center\" class=\"bodytextbold11\"><a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 4654 */                               if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                                 return;
/* 4656 */                               if (_jspx_meth_c_005fif_005f15(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                                 return;
/* 4658 */                               out.write("\" class=\"staticlinks\">");
/* 4659 */                               out.print(FormatUtil.getString("am.webclient.hostResource.servers.gotosnapshot"));
/* 4660 */                               out.write("\"></a></td>");
/* 4661 */                               out.write("\n    </tr>\n  </table>\n  ");
/* 4662 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 4663 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4667 */                           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 4668 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                           }
/*      */                           
/* 4671 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4672 */                           out.write(10);
/* 4673 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 4674 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4678 */                       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 4679 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                       }
/*      */                       
/* 4682 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4683 */                       out.write(10);
/*      */ 
/*      */                     }
/* 4686 */                     else if (details.equals("DB")) {
/* 4687 */                       redirect = "/MSSql.do?name=" + name + "&haid=" + haid + "&appName=" + appname + "&resourceid=" + resourceid + "&details=" + details + "&resourcename=" + displayname + "&dbid=" + request.getParameter("dbid") + "&dbname=" + request.getParameter("dbname");
/* 4688 */                       encodeurl = java.net.URLEncoder.encode(redirect);
/* 4689 */                       String dbname = request.getParameter("dbname");
/*      */                       
/* 4691 */                       out.write(10);
/* 4692 */                       out.write(9);
/*      */                       
/* 4694 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f4 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4695 */                       _jspx_th_logic_005fnotEmpty_005f4.setPageContext(_jspx_page_context);
/* 4696 */                       _jspx_th_logic_005fnotEmpty_005f4.setParent(null);
/*      */                       
/* 4698 */                       _jspx_th_logic_005fnotEmpty_005f4.setName("dbdetails");
/* 4699 */                       int _jspx_eval_logic_005fnotEmpty_005f4 = _jspx_th_logic_005fnotEmpty_005f4.doStartTag();
/* 4700 */                       if (_jspx_eval_logic_005fnotEmpty_005f4 != 0) {
/*      */                         for (;;) {
/* 4702 */                           out.write(10);
/* 4703 */                           out.write(9);
/*      */                           
/* 4705 */                           dbinfo = (Hashtable)request.getAttribute("dbdetails");
/* 4706 */                           Properties dbprop = (Properties)dbinfo.get(request.getParameter("dbname"));
/* 4707 */                           ResultSet results = null;
/* 4708 */                           mssqlgraph.setresid(Integer.parseInt(dbprop.getProperty("DBID")));
/* 4709 */                           String dbid = dbprop.getProperty("DBID");
/* 4710 */                           String dspname = dbprop.getProperty("DISPLAYNAME");
/*      */                           
/* 4712 */                           out.write("\n \t<table width=\"99%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n \t<tr>\n \t<td valign=\"top\">\n \t<table width=\"96%\"  border=\"0\" cellpadding=\"3\" cellspacing=\"1\" class=\"lrtbdarkborder\">\n \t<tr>\n \t<td  colspan=\"2\" class=\"tableheadingbborder\">");
/* 4713 */                           out.print(FormatUtil.getString("am.webclient.mysql.databasedetails"));
/* 4714 */                           out.write(" &nbsp;- ");
/* 4715 */                           out.print(dbname);
/* 4716 */                           out.write(" <span class=\"resourceheading\">\n \t</span></td>\n \t</tr>\n \t<tr>\n \t<td class=\"monitorinfoodd\">");
/* 4717 */                           out.print(FormatUtil.getString("am.webclient.common.databasename"));
/* 4718 */                           out.write("</td>\n \t<td class=\"monitorinfoodd\">");
/* 4719 */                           out.print(getTrimmedText(dbname, 30));
/* 4720 */                           out.write("</td>\n \t</tr>\n\n ");
/*      */                           
/* 4722 */                           String databaseStatus = alert.getProperty(dbid + "#" + "3150");
/*      */                           
/* 4724 */                           out.write("\n \t<tr>\n \t<td class=\"monitorinfoeven\" valign=\"top\">");
/* 4725 */                           out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4726 */                           out.write("</td>\n \t<td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4727 */                           out.print(dbid);
/* 4728 */                           out.write("&attributeid=3150')\">");
/* 4729 */                           out.print(getSeverityImageForHealth(databaseStatus));
/* 4730 */                           out.write("</a>\n \t");
/* 4731 */                           out.print(getHideAndShowRCAMessage(alert.getProperty(dbid + "#" + "3150" + "#" + "MESSAGE"), "3150", alert.getProperty(dbid + "#" + "3150"), dbid));
/* 4732 */                           out.write("\n \t</td>\n \t</tr>\n \t<tr>\n \t<td class=\"monitorinfoodd\">");
/* 4733 */                           out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 4734 */                           out.write(" </td>\n \t<td class=\"monitorinfoodd\">");
/* 4735 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4736 */                           out.write("-Database</td>");
/* 4737 */                           out.write("\n \t</tr>\n \t</table>\n \t</td>\n \t<td width=\"40%\" height=\"31\" class=\"bodytextbold\" valign=\"top\" >\n \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"lrtbdarkborder\">\n \t<tbody>\n \t<tr>\n \t<td colspan=\"4\" height=\"31\" class=\"tableheadingbborder\">\n \t");
/* 4738 */                           out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 4739 */                           out.write(" <a name=\"Availability\" id=\"Availability\"></a></td>\n \t</tr>\n \t<tr> <td colspan=\"4\"><table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n \t<tr>\n \t<td width = \"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 4740 */                           out.print(dbid);
/* 4741 */                           out.write("&attributeid=3151&period=-7&resourcename=");
/* 4742 */                           out.print(dbname);
/* 4743 */                           out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4744 */                           out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4745 */                           out.write("\"></a></td>\n \t<td width = \"4%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 4746 */                           out.print(dbid);
/* 4747 */                           out.write("&attributeid=3151&period=-30&resourcename=");
/* 4748 */                           out.print(dbname);
/* 4749 */                           out.write(",740,550')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4750 */                           out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4751 */                           out.write("\"></a></td>\n \t</tr>\n \t</table>\n \t</td>\n \t</tr>\n \t<tr>\n \t<td colspan=\"4\" align=\"center\">\n \t");
/*      */                           
/* 4753 */                           AMWolf _jspx_th_awolf_005fpiechart_005f1 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 4754 */                           _jspx_th_awolf_005fpiechart_005f1.setPageContext(_jspx_page_context);
/* 4755 */                           _jspx_th_awolf_005fpiechart_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f4);
/*      */                           
/* 4757 */                           _jspx_th_awolf_005fpiechart_005f1.setDataSetProducer("wlsGraph");
/*      */                           
/* 4759 */                           _jspx_th_awolf_005fpiechart_005f1.setWidth("280");
/*      */                           
/* 4761 */                           _jspx_th_awolf_005fpiechart_005f1.setHeight("200");
/*      */                           
/* 4763 */                           _jspx_th_awolf_005fpiechart_005f1.setLegend("true");
/*      */                           
/* 4765 */                           _jspx_th_awolf_005fpiechart_005f1.setUrl(true);
/*      */                           
/* 4767 */                           _jspx_th_awolf_005fpiechart_005f1.setUnits("%");
/*      */                           
/* 4769 */                           _jspx_th_awolf_005fpiechart_005f1.setDecimal(true);
/* 4770 */                           int _jspx_eval_awolf_005fpiechart_005f1 = _jspx_th_awolf_005fpiechart_005f1.doStartTag();
/* 4771 */                           if (_jspx_eval_awolf_005fpiechart_005f1 != 0) {
/* 4772 */                             if (_jspx_eval_awolf_005fpiechart_005f1 != 1) {
/* 4773 */                               out = _jspx_page_context.pushBody();
/* 4774 */                               _jspx_th_awolf_005fpiechart_005f1.setBodyContent((BodyContent)out);
/* 4775 */                               _jspx_th_awolf_005fpiechart_005f1.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 4778 */                               out.write(10);
/* 4779 */                               out.write(32);
/* 4780 */                               out.write(9);
/*      */                               
/* 4782 */                               Property _jspx_th_awolf_005fmap_005f1 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 4783 */                               _jspx_th_awolf_005fmap_005f1.setPageContext(_jspx_page_context);
/* 4784 */                               _jspx_th_awolf_005fmap_005f1.setParent(_jspx_th_awolf_005fpiechart_005f1);
/*      */                               
/* 4786 */                               _jspx_th_awolf_005fmap_005f1.setId("color");
/* 4787 */                               int _jspx_eval_awolf_005fmap_005f1 = _jspx_th_awolf_005fmap_005f1.doStartTag();
/* 4788 */                               if (_jspx_eval_awolf_005fmap_005f1 != 0) {
/* 4789 */                                 if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 4790 */                                   out = _jspx_page_context.pushBody();
/* 4791 */                                   _jspx_th_awolf_005fmap_005f1.setBodyContent((BodyContent)out);
/* 4792 */                                   _jspx_th_awolf_005fmap_005f1.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 4795 */                                   out.write(10);
/* 4796 */                                   out.write(32);
/* 4797 */                                   out.write(9);
/*      */                                   
/* 4799 */                                   AMParam _jspx_th_awolf_005fparam_005f2 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4800 */                                   _jspx_th_awolf_005fparam_005f2.setPageContext(_jspx_page_context);
/* 4801 */                                   _jspx_th_awolf_005fparam_005f2.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                   
/* 4803 */                                   _jspx_th_awolf_005fparam_005f2.setName("1");
/*      */                                   
/* 4805 */                                   _jspx_th_awolf_005fparam_005f2.setValue(available);
/* 4806 */                                   int _jspx_eval_awolf_005fparam_005f2 = _jspx_th_awolf_005fparam_005f2.doStartTag();
/* 4807 */                                   if (_jspx_th_awolf_005fparam_005f2.doEndTag() == 5) {
/* 4808 */                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2); return;
/*      */                                   }
/*      */                                   
/* 4811 */                                   this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2);
/* 4812 */                                   out.write(10);
/* 4813 */                                   out.write(32);
/* 4814 */                                   out.write(9);
/*      */                                   
/* 4816 */                                   AMParam _jspx_th_awolf_005fparam_005f3 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4817 */                                   _jspx_th_awolf_005fparam_005f3.setPageContext(_jspx_page_context);
/* 4818 */                                   _jspx_th_awolf_005fparam_005f3.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                   
/* 4820 */                                   _jspx_th_awolf_005fparam_005f3.setName("0");
/*      */                                   
/* 4822 */                                   _jspx_th_awolf_005fparam_005f3.setValue(unavailable);
/* 4823 */                                   int _jspx_eval_awolf_005fparam_005f3 = _jspx_th_awolf_005fparam_005f3.doStartTag();
/* 4824 */                                   if (_jspx_th_awolf_005fparam_005f3.doEndTag() == 5) {
/* 4825 */                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3); return;
/*      */                                   }
/*      */                                   
/* 4828 */                                   this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3);
/* 4829 */                                   out.write(10);
/* 4830 */                                   out.write(32);
/* 4831 */                                   out.write(9);
/* 4832 */                                   int evalDoAfterBody = _jspx_th_awolf_005fmap_005f1.doAfterBody();
/* 4833 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 4836 */                                 if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 4837 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 4840 */                               if (_jspx_th_awolf_005fmap_005f1.doEndTag() == 5) {
/* 4841 */                                 this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1); return;
/*      */                               }
/*      */                               
/* 4844 */                               this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1);
/* 4845 */                               out.write(10);
/* 4846 */                               out.write(32);
/* 4847 */                               out.write(9);
/* 4848 */                               int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f1.doAfterBody();
/* 4849 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 4852 */                             if (_jspx_eval_awolf_005fpiechart_005f1 != 1) {
/* 4853 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 4856 */                           if (_jspx_th_awolf_005fpiechart_005f1.doEndTag() == 5) {
/* 4857 */                             this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f1); return;
/*      */                           }
/*      */                           
/* 4860 */                           this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f1);
/* 4861 */                           out.write("\n \t</td>\n \t</tr>\n \t<tr>\n \t<td  width=\"32%\" class=\"yellowgrayborder\">");
/* 4862 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.currnetstatus"));
/* 4863 */                           out.write("</td>\n ");
/*      */                           
/* 4865 */                           String databaseAvailability = alert.getProperty(dbid + "#" + "3151");
/*      */                           
/* 4867 */                           out.write("\n \t<td height=\"21\"  class=\"yellowgrayborder\" colspan=\"2\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4868 */                           out.print(dbid);
/* 4869 */                           out.write("&attributeid=3151')\">");
/* 4870 */                           out.print(getSeverityImageForAvailability(databaseAvailability));
/* 4871 */                           out.write("</a></td>\n \t<td width=\"50%\"  class=\"yellowgrayborder\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4872 */                           out.print(dbid);
/* 4873 */                           out.write("&attributeIDs=3151,3150&attributeToSelect=3151&redirectto=");
/* 4874 */                           out.print(encodeurl);
/* 4875 */                           out.write("\" class=\"links\">");
/* 4876 */                           out.print(ALERTCONFIG_TEXT);
/* 4877 */                           out.write("</a></td>\n \t</tr>\n \t</tbody>\n \t</table></td>\n \t</tr>\n \t</table>\n\t");
/* 4878 */                           if ((dbprop.getProperty("STATUS").trim().equals("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equals("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equals("INACTIVE"))) {
/* 4879 */                             out.write("\n\t<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t<td>&nbsp;</td>\n\t</tr>\n\t</table>\n\t");
/*      */                           } else {
/* 4881 */                             out.write("\n\t<br>\n\t<br>\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t<td width=\"4%\" id=\"userAttribDetailsFrameTd\" valign=\"top\">\n\t</td>\n\n\t<td width=\"24%\" valign=\"top\">\n\t<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"> \n\t<tr> \n\t<td id=\"userAttribDetailsFrameTd\" valign=\"top\">\n\t\t\t<div style=\"float: left; width: 98%;\">\n\t\t\t\t<div align=\"left\" style=\"line-height:0px;\"><img src=\"images/green_dot1.gif\" width=\"5\" height=\"5\">&nbsp;<a onClick=\"fnOpenNewWindow('/MSSqlDispatch.do?method=systables&dbname=");
/* 4882 */                             out.print(dspname);
/* 4883 */                             out.write("&resourceid=");
/* 4884 */                             if (_jspx_meth_c_005fout_005f28(_jspx_th_logic_005fnotEmpty_005f4, _jspx_page_context))
/*      */                               return;
/* 4886 */                             out.write("')\" class=\"reportlinktxt\" href=\"#\" >");
/* 4887 */                             out.print(FormatUtil.getString("am.action.shortname.systables.text"));
/* 4888 */                             out.write("</a><img src=\"images/gray_line1.gif\" width=\"200\" height=\"1\"><br></div>   \n\t\t\t\t<div align=\"left\" style=\"line-height:0px;\"><img src=\"images/green_dot1.gif\" width=\"5\" height=\"5\">&nbsp;<a onClick=\"fnOpenNewWindow('/MSSqlDispatch.do?method=indexdetails&dbname=");
/* 4889 */                             out.print(dspname);
/* 4890 */                             out.write("&resourceid=");
/* 4891 */                             if (_jspx_meth_c_005fout_005f29(_jspx_th_logic_005fnotEmpty_005f4, _jspx_page_context))
/*      */                               return;
/* 4893 */                             out.write("')\" class=\"reportlinktxt\" href=\"#\" >");
/* 4894 */                             out.print(FormatUtil.getString("am.action.shortname.indexdetails.text"));
/* 4895 */                             out.write("</a><img src=\"images/gray_line1.gif\" width=\"200\" height=\"1\"></div>\n\t\t\t\t<br>\n\t\t\t</div>\t\t\n\t</td>\t\t\n\t</tr> \t \n\t</table>\n\t</td>\n\t \n\t<td width=\"24%\">\n\t<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"> \n\t<tr> \n\t<td id=\"userAttribDetailsFrameTd\" valign=\"top\">\n\t\t\t<div style=\"float: left; width: 98%;\">\n\t\t\t\t<div align=\"left\" style=\"line-height:0px;\"><img src=\"images/green_dot1.gif\" width=\"5\" height=\"5\">&nbsp;<a onClick=\"fnOpenNewWindow('/MSSqlDispatch.do?method=usertables&dbname=");
/* 4896 */                             out.print(dspname);
/* 4897 */                             out.write("&resourceid=");
/* 4898 */                             if (_jspx_meth_c_005fout_005f30(_jspx_th_logic_005fnotEmpty_005f4, _jspx_page_context))
/*      */                               return;
/* 4900 */                             out.write("')\" class=\"reportlinktxt\" href=\"#\" >");
/* 4901 */                             out.print(FormatUtil.getString("am.action.shortname.usertables.text"));
/* 4902 */                             out.write("</a><img src=\"images/gray_line1.gif\" width=\"200\" height=\"1\"><br></div>\n\t\t\t\t<div align=\"left\" style=\"line-height:0px;\"><img src=\"images/green_dot1.gif\" width=\"5\" height=\"5\">&nbsp;<a onClick=\"fnOpenNewWindow('/MSSqlDispatch.do?method=fragmentdetails&dbname=");
/* 4903 */                             out.print(dspname);
/* 4904 */                             out.write("&resourceid=");
/* 4905 */                             if (_jspx_meth_c_005fout_005f31(_jspx_th_logic_005fnotEmpty_005f4, _jspx_page_context))
/*      */                               return;
/* 4907 */                             out.write("')\" class=\"reportlinktxt\" href=\"#\" >");
/* 4908 */                             out.print(FormatUtil.getString("am.action.shortname.fragmentationdetails.text"));
/* 4909 */                             out.write("</a><img src=\"images/gray_line1.gif\" width=\"200\" height=\"1\"></div>  \n\t\t\t\t<br/>\n\t\t\t</div>\t\t\t\n\t</td>\t\t\n\t</tr> \t \n\t</table>\n\t</td>\n\t  \n\t<td width=\"24%\">\n\t<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"> \n\t<tr> \n\t<td id=\"userAttribDetailsFrameTd\" valign=\"top\">\n\t\t\t<div style=\"float: left; width: 98%;\">  \n\t\t\t\t<div align=\"left\" style=\"line-height:0px;\"><img src=\"images/green_dot1.gif\" width=\"5\" height=\"5\">&nbsp;<a onClick=\"fnOpenNewWindow('/MSSqlDispatch.do?method=tablerelation&dbname=");
/* 4910 */                             out.print(dspname);
/* 4911 */                             out.write("&resourceid=");
/* 4912 */                             if (_jspx_meth_c_005fout_005f32(_jspx_th_logic_005fnotEmpty_005f4, _jspx_page_context))
/*      */                               return;
/* 4914 */                             out.write("')\" class=\"reportlinktxt\" href=\"#\" >");
/* 4915 */                             out.print(FormatUtil.getString("am.action.shortname.tablerelationship.text"));
/* 4916 */                             out.write("</a><img src=\"images/gray_line1.gif\" width=\"200\" height=\"1\"><br></div>    \n\t\t\t\t<div align=\"left\" style=\"line-height:0px;\"><img src=\"images/green_dot1.gif\" width=\"5\" height=\"5\">&nbsp;<a onClick=\"fnOpenNewWindow('/MSSqlDispatch.do?method=indexnotused&dbname=");
/* 4917 */                             out.print(dspname);
/* 4918 */                             out.write("&resourceid=");
/* 4919 */                             if (_jspx_meth_c_005fout_005f33(_jspx_th_logic_005fnotEmpty_005f4, _jspx_page_context))
/*      */                               return;
/* 4921 */                             out.write("')\" class=\"reportlinktxt\" href=\"#\" >");
/* 4922 */                             out.print(FormatUtil.getString("am.action.shortname.indexnotused.text"));
/* 4923 */                             out.write("</a><img src=\"images/gray_line1.gif\" width=\"200\" height=\"1\"></div>\n\t\t\t\t<br>\n\t\t\t </div>\t\t\n\t</td>\t\t\n\t</tr> \t \n\t</table>\n\t</td>\n\t  \n\t<td width=\"24%\">\n\t<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"> \n\t<tr> \n\t<td id=\"userAttribDetailsFrameTd\" valign=\"top\">\n\t\t\t<div style=\"float: left; width: 98%;\">\n\t\t\t\t<div align=\"left\" style=\"line-height:0px;\"><img  src=\"images/green_dot1.gif\" width=\"5\" height=\"5\">&nbsp;<a onClick=\"fnOpenNewWindow('/MSSqlDispatch.do?method=viewsdetails&dbname=");
/* 4924 */                             out.print(dspname);
/* 4925 */                             out.write("&resourceid=");
/* 4926 */                             if (_jspx_meth_c_005fout_005f34(_jspx_th_logic_005fnotEmpty_005f4, _jspx_page_context))
/*      */                               return;
/* 4928 */                             out.write("')\" class=\"reportlinktxt\" href=\"#\" >");
/* 4929 */                             out.print(FormatUtil.getString("am.action.shortname.viewdetails.text"));
/* 4930 */                             out.write("</a><img src=\"images/gray_line1.gif\" width=\"200\" height=\"1\"> <br></div>    \n\t\t\t\t<div align=\"left\" style=\"line-height:0px;\"><img  src=\"images/green_dot1.gif\" width=\"5\" height=\"5\">&nbsp;<a onClick=\"fnOpenNewWindow('/MSSqlDispatch.do?method=indexused&dbname=");
/* 4931 */                             out.print(dspname);
/* 4932 */                             out.write("&resourceid=");
/* 4933 */                             if (_jspx_meth_c_005fout_005f35(_jspx_th_logic_005fnotEmpty_005f4, _jspx_page_context))
/*      */                               return;
/* 4935 */                             out.write("')\" class=\"reportlinktxt\" href=\"#\" >");
/* 4936 */                             out.print(FormatUtil.getString("am.action.shortname.indexused.text"));
/* 4937 */                             out.write("</a><img src=\"images/gray_line1.gif\" width=\"200\" height=\"1\"></div>\n\t\t\t\t<br>\n\t\t\t</div>\t\t\n\t</td>\t\t\n\t</tr> \t \n\t</table>\n\t</td>  \t\t\n\t</tr>\n\t</table>\n\t\t\n\t");
/*      */                           }
/* 4939 */                           out.write("\t\n\n\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t\t\t<tr>\n\n    <td width=\"50%\" height=\"31\" class=\"tableheading\">");
/* 4940 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.datafiledetails"));
/* 4941 */                           out.write("</td>\n    <td width=\"50%\" height=\"31\" align=\"left\" class=\"tableheading\">");
/* 4942 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.logfilesize"));
/* 4943 */                           out.write("</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\t\t\t<tr>\n\t\t\t<td width=\"50%\" height=\"38\"  class=\"rbborder\"><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t<tr>\n\t\t\t<td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4944 */                           out.print(dbprop.getProperty("DBID"));
/* 4945 */                           out.write("&attributeid=3128&period=-7&resourcename=");
/* 4946 */                           out.print(dbname);
/* 4947 */                           out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4948 */                           out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4949 */                           out.write("\"></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4950 */                           out.print(dbprop.getProperty("DBID"));
/* 4951 */                           out.write("&attributeid=3128&period=-30&resourcename=");
/* 4952 */                           out.print(dbname);
/* 4953 */                           out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4954 */                           out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4955 */                           out.write("\"></a></td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t");
/* 4956 */                           mssqlgraph.settype("DATAFILE");
/* 4957 */                           out.write("\n\n\t\t\t ");
/* 4958 */                           if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 4959 */                             out.write("\n\t\t\t <td width=\"330\" height=\"170\" class=\"bodytext\" align=\"center\">");
/* 4960 */                             out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4961 */                             out.write("</td>\n\t\t\t ");
/*      */                           } else {
/* 4963 */                             out.write("\n\t\t\t  <td>");
/*      */                             
/* 4965 */                             TimeChart _jspx_th_awolf_005ftimechart_005f9 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 4966 */                             _jspx_th_awolf_005ftimechart_005f9.setPageContext(_jspx_page_context);
/* 4967 */                             _jspx_th_awolf_005ftimechart_005f9.setParent(_jspx_th_logic_005fnotEmpty_005f4);
/*      */                             
/* 4969 */                             _jspx_th_awolf_005ftimechart_005f9.setDataSetProducer("mssqlgraph");
/*      */                             
/* 4971 */                             _jspx_th_awolf_005ftimechart_005f9.setWidth("330");
/*      */                             
/* 4973 */                             _jspx_th_awolf_005ftimechart_005f9.setHeight("170");
/*      */                             
/* 4975 */                             _jspx_th_awolf_005ftimechart_005f9.setLegend("true");
/*      */                             
/* 4977 */                             _jspx_th_awolf_005ftimechart_005f9.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */                             
/* 4979 */                             _jspx_th_awolf_005ftimechart_005f9.setYaxisLabel(FormatUtil.getString("am.webclient.mssqldetails.sizeinmb"));
/* 4980 */                             int _jspx_eval_awolf_005ftimechart_005f9 = _jspx_th_awolf_005ftimechart_005f9.doStartTag();
/* 4981 */                             if (_jspx_th_awolf_005ftimechart_005f9.doEndTag() == 5) {
/* 4982 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f9); return;
/*      */                             }
/*      */                             
/* 4985 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f9);
/* 4986 */                             out.write("</td>\n\t\t\t  ");
/*      */                           }
/* 4988 */                           out.write("\n\n\t\t\t</tr>\n\t\t\t</table>\n\n\n\t\t\t<td width=\"50%\" height=\"38\" class=\"bottomborder\"> <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t<tr>\n\t\t\t<td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4989 */                           out.print(dbprop.getProperty("DBID"));
/* 4990 */                           out.write("&attributeid=3129&period=-7&resourcename=");
/* 4991 */                           out.print(dbname);
/* 4992 */                           out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4993 */                           out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4994 */                           out.write("\"></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4995 */                           out.print(dbprop.getProperty("DBID"));
/* 4996 */                           out.write("&attributeid=3129&period=-30&resourcename=");
/* 4997 */                           out.print(dbname);
/* 4998 */                           out.write(",740,550')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4999 */                           out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 5000 */                           out.write("\"></a></td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t");
/* 5001 */                           mssqlgraph.settype("LOGSIZE");
/* 5002 */                           out.write("\n\n\n\t\t  ");
/* 5003 */                           if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5004 */                             out.write("\n\t\t   <td width=\"330\" height=\"170\" class=\"bodytext\" align=\"center\">");
/* 5005 */                             out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5006 */                             out.write("</td>\n\t\t ");
/*      */                           } else {
/* 5008 */                             out.write("\n\t\t <td>");
/*      */                             
/* 5010 */                             TimeChart _jspx_th_awolf_005ftimechart_005f10 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 5011 */                             _jspx_th_awolf_005ftimechart_005f10.setPageContext(_jspx_page_context);
/* 5012 */                             _jspx_th_awolf_005ftimechart_005f10.setParent(_jspx_th_logic_005fnotEmpty_005f4);
/*      */                             
/* 5014 */                             _jspx_th_awolf_005ftimechart_005f10.setDataSetProducer("mssqlgraph");
/*      */                             
/* 5016 */                             _jspx_th_awolf_005ftimechart_005f10.setWidth("330");
/*      */                             
/* 5018 */                             _jspx_th_awolf_005ftimechart_005f10.setHeight("170");
/*      */                             
/* 5020 */                             _jspx_th_awolf_005ftimechart_005f10.setLegend("true");
/*      */                             
/* 5022 */                             _jspx_th_awolf_005ftimechart_005f10.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */                             
/* 5024 */                             _jspx_th_awolf_005ftimechart_005f10.setYaxisLabel(FormatUtil.getString("am.webclient.mssqldetails.sizeinmb"));
/* 5025 */                             int _jspx_eval_awolf_005ftimechart_005f10 = _jspx_th_awolf_005ftimechart_005f10.doStartTag();
/* 5026 */                             if (_jspx_th_awolf_005ftimechart_005f10.doEndTag() == 5) {
/* 5027 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f10); return;
/*      */                             }
/*      */                             
/* 5030 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f10);
/* 5031 */                             out.write(" </td>\n\t\t");
/*      */                           }
/* 5033 */                           out.write("\n\n\t\t\t</tr>\n\t\t\t</table></td>\n\n\n\t\t\t<tr>\n\t\t\t<td valign=\"top\" class=\"rborder\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n        <tr>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 5034 */                           out.print(FormatUtil.getString("table.heading.attribute"));
/* 5035 */                           out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 5036 */                           out.print(FormatUtil.getString("table.heading.value"));
/* 5037 */                           out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 5038 */                           out.print(FormatUtil.getString("table.heading.status"));
/* 5039 */                           out.write("</span></td>\n        </tr>\n        <tr>\n          <td width=\"41%\" class=\"whitegrayborder\">");
/* 5040 */                           out.print(FormatUtil.getString("Data File Size"));
/* 5041 */                           out.write("</td>\n\n\n\n          <td width=\"24%\" class=\"whitegrayborder\">\n\n\t\t  \t\t  ");
/* 5042 */                           if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5043 */                             out.write("\n\t\t  \t\t  -\n\t\t  \t\t  ");
/*      */                           } else {
/* 5045 */                             out.write("\n\t\t  \t\t  ");
/* 5046 */                             out.print(df.format(Double.parseDouble(dbprop.getProperty("DATAFILESSIZE"))));
/* 5047 */                             out.write(" MB\n\n\t\t  ");
/*      */                           }
/* 5049 */                           out.write("\n          </td>\n\n\n\n          <td width=\"42%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5050 */                           out.print(dbprop.getProperty("DBID"));
/* 5051 */                           out.write("&attributeid=3128')\">");
/* 5052 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3128")));
/* 5053 */                           out.write("</a></td>\n        </tr>\n        <tr>\n          <td colspan=\"3\" align=\"right\" class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5054 */                           out.print(dbprop.getProperty("DBID"));
/* 5055 */                           out.write("&attributeIDs=3128&attributeToSelect=3128&redirectto=");
/* 5056 */                           out.print(encodeurl);
/* 5057 */                           out.write("'class=\"staticlinks\">");
/* 5058 */                           out.print(ALERTCONFIG_TEXT);
/* 5059 */                           out.write("</a></td>\n        </tr>\n      </table></td>\n\t\t\t<td valign=\"top\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n        <tr>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 5060 */                           out.print(FormatUtil.getString("table.heading.attribute"));
/* 5061 */                           out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 5062 */                           out.print(FormatUtil.getString("table.heading.value"));
/* 5063 */                           out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 5064 */                           out.print(FormatUtil.getString("table.heading.status"));
/* 5065 */                           out.write("</span></td>\n        </tr>\n        <tr>\n          <td width=\"41%\" class=\"whitegrayborder\">");
/* 5066 */                           out.print(FormatUtil.getString("Log File Size"));
/* 5067 */                           out.write("</td>\n           <td width=\"24%\" class=\"whitegrayborder\">\n\n\n          ");
/* 5068 */                           if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5069 */                             out.write("\n\t\t  \t\t  -\n\t\t  ");
/*      */                           } else {
/* 5071 */                             out.write("\n\t\t  ");
/* 5072 */                             out.print(df.format(Double.parseDouble(dbprop.getProperty("LOGFILESSIZE"))));
/* 5073 */                             out.write(" MB\n\t\t  ");
/*      */                           }
/* 5075 */                           out.write("\n\n            </td>\n\n\n\n\n          <td width=\"44%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5076 */                           out.print(dbprop.getProperty("DBID"));
/* 5077 */                           out.write("&attributeid=3129')\">");
/* 5078 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3129")));
/* 5079 */                           out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 5080 */                           out.print(FormatUtil.getString("Log File Used Size"));
/* 5081 */                           out.write("</td>\n\n\n          <td class=\"yellowgrayborder\">\n\n\n\t\t  ");
/* 5082 */                           if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("LOGFILEUSEDSIZE")) == -1.0D)) {
/* 5083 */                             out.write("\n\t\t  -\n\t\t  ");
/*      */                           } else {
/* 5085 */                             out.write("\n\t\t  ");
/* 5086 */                             out.print(df.format(Double.parseDouble(dbprop.getProperty("LOGFILEUSEDSIZE"))));
/* 5087 */                             out.write(" MB\n\t\t  ");
/*      */                           }
/* 5089 */                           out.write("\n\n\t\t  </td>\n\n\n          <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5090 */                           out.print(dbprop.getProperty("DBID"));
/* 5091 */                           out.write("&attributeid=3130')\">");
/* 5092 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3130")));
/* 5093 */                           out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 5094 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.logfileused"));
/* 5095 */                           out.write("</td>\n          <td class=\"whitegrayborder\">\n\n\t\t  ");
/* 5096 */                           if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5097 */                             out.write("\n\t\t   -\n\t\t  ");
/*      */                           } else {
/* 5099 */                             out.write("\n\t\t  ");
/* 5100 */                             out.print(df.format(Double.parseDouble(dbprop.getProperty("LOGUSEDPERCENT"))));
/* 5101 */                             out.write(" %\n\t\t  ");
/*      */                           }
/* 5103 */                           out.write("\n\t\t  </td>\n\n\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5104 */                           out.print(dbprop.getProperty("DBID"));
/* 5105 */                           out.write("&attributeid=3131')\">");
/* 5106 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3131")));
/* 5107 */                           out.write("</a></td>\n        </tr>\n        <tr>\n          <td colspan=\"3\" align=\"right\" class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5108 */                           out.print(dbprop.getProperty("DBID"));
/* 5109 */                           out.write("&attributeIDs=3129,3130,3131&attributeToSelect=3129&redirectto=");
/* 5110 */                           out.print(encodeurl);
/* 5111 */                           out.write("'class=\"staticlinks\">");
/* 5112 */                           out.print(ALERTCONFIG_TEXT);
/* 5113 */                           out.write("</a></td>\n        </tr>\n      </table></td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t<tr>\n\t\t\t<td>&nbsp;</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t\t\t<tr>\n\n    <td width=\"50%\" height=\"31\" class=\"tableheading\">");
/* 5114 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.transactiondetails"));
/* 5115 */                           out.write(" </td>\n    <td width=\"50%\" height=\"31\" align=\"left\" class=\"tableheading\">");
/* 5116 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.logflush"));
/* 5117 */                           out.write("</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\t\t\t<tr>\n\t\t\t<td width=\"50%\" height=\"38\"  class=\"rbborder\"><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t<tr>\n\t\t\t<td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5118 */                           out.print(dbprop.getProperty("DBID"));
/* 5119 */                           out.write("&attributeid=3132&period=-7&resourcename=");
/* 5120 */                           out.print(dbname);
/* 5121 */                           out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 5122 */                           out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 5123 */                           out.write("\"></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5124 */                           out.print(dbprop.getProperty("DBID"));
/* 5125 */                           out.write("&attributeid=3132&period=-30&resourcename=");
/* 5126 */                           out.print(dbname);
/* 5127 */                           out.write(",740,550')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 5128 */                           out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 5129 */                           out.write("\"></a></td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t");
/* 5130 */                           mssqlgraph.settype("TRANS");
/* 5131 */                           out.write("\n\n\t\t\t");
/* 5132 */                           if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5133 */                             out.write("\n\t\t\t <td width=\"330\" height=\"170\" class=\"bodytext\" align=\"center\">");
/* 5134 */                             out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5135 */                             out.write("</td>\n\t\t\t ");
/*      */                           } else {
/* 5137 */                             out.write("\n\t\t\t <td>");
/*      */                             
/* 5139 */                             TimeChart _jspx_th_awolf_005ftimechart_005f11 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 5140 */                             _jspx_th_awolf_005ftimechart_005f11.setPageContext(_jspx_page_context);
/* 5141 */                             _jspx_th_awolf_005ftimechart_005f11.setParent(_jspx_th_logic_005fnotEmpty_005f4);
/*      */                             
/* 5143 */                             _jspx_th_awolf_005ftimechart_005f11.setDataSetProducer("mssqlgraph");
/*      */                             
/* 5145 */                             _jspx_th_awolf_005ftimechart_005f11.setWidth("330");
/*      */                             
/* 5147 */                             _jspx_th_awolf_005ftimechart_005f11.setHeight("170");
/*      */                             
/* 5149 */                             _jspx_th_awolf_005ftimechart_005f11.setLegend("true");
/*      */                             
/* 5151 */                             _jspx_th_awolf_005ftimechart_005f11.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */                             
/* 5153 */                             _jspx_th_awolf_005ftimechart_005f11.setYaxisLabel(FormatUtil.getString("am.webclient.mssqldetails.valuepermin"));
/* 5154 */                             int _jspx_eval_awolf_005ftimechart_005f11 = _jspx_th_awolf_005ftimechart_005f11.doStartTag();
/* 5155 */                             if (_jspx_eval_awolf_005ftimechart_005f11 != 0) {
/* 5156 */                               if (_jspx_eval_awolf_005ftimechart_005f11 != 1) {
/* 5157 */                                 out = _jspx_page_context.pushBody();
/* 5158 */                                 _jspx_th_awolf_005ftimechart_005f11.setBodyContent((BodyContent)out);
/* 5159 */                                 _jspx_th_awolf_005ftimechart_005f11.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 5162 */                                 out.write(32);
/* 5163 */                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f11.doAfterBody();
/* 5164 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 5167 */                               if (_jspx_eval_awolf_005ftimechart_005f11 != 1) {
/* 5168 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 5171 */                             if (_jspx_th_awolf_005ftimechart_005f11.doEndTag() == 5) {
/* 5172 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f11); return;
/*      */                             }
/*      */                             
/* 5175 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f11);
/* 5176 */                             out.write("</td>\n\t\t\t");
/*      */                           }
/* 5178 */                           out.write("\n\n\n\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<td width=\"50%\" height=\"38\" class=\"bottomborder\"> <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t<tr>\n\t\t\t<td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5179 */                           out.print(dbprop.getProperty("DBID"));
/* 5180 */                           out.write("&attributeid=3135&period=-7&resourcename=");
/* 5181 */                           out.print(dbname);
/* 5182 */                           out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 5183 */                           out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 5184 */                           out.write("\"></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5185 */                           out.print(dbprop.getProperty("DBID"));
/* 5186 */                           out.write("&attributeid=3135&period=-30&resourcename=");
/* 5187 */                           out.print(dbname);
/* 5188 */                           out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 5189 */                           out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 5190 */                           out.write("\"></a></td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t");
/* 5191 */                           mssqlgraph.settype("LOGFLUSH");
/* 5192 */                           out.write("\n\t\t\t");
/* 5193 */                           if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5194 */                             out.write("\n\t\t\t <td width=\"330\" height=\"170\" class=\"bodytext\" align=\"center\">");
/* 5195 */                             out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5196 */                             out.write("</td>\n\t\t\t ");
/*      */                           } else {
/* 5198 */                             out.write("\n\t\t\t<td>");
/*      */                             
/* 5200 */                             TimeChart _jspx_th_awolf_005ftimechart_005f12 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 5201 */                             _jspx_th_awolf_005ftimechart_005f12.setPageContext(_jspx_page_context);
/* 5202 */                             _jspx_th_awolf_005ftimechart_005f12.setParent(_jspx_th_logic_005fnotEmpty_005f4);
/*      */                             
/* 5204 */                             _jspx_th_awolf_005ftimechart_005f12.setDataSetProducer("mssqlgraph");
/*      */                             
/* 5206 */                             _jspx_th_awolf_005ftimechart_005f12.setWidth("330");
/*      */                             
/* 5208 */                             _jspx_th_awolf_005ftimechart_005f12.setHeight("170");
/*      */                             
/* 5210 */                             _jspx_th_awolf_005ftimechart_005f12.setLegend("true");
/*      */                             
/* 5212 */                             _jspx_th_awolf_005ftimechart_005f12.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */                             
/* 5214 */                             _jspx_th_awolf_005ftimechart_005f12.setYaxisLabel(FormatUtil.getString("am.webclient.mssqldetails.valuepermin"));
/* 5215 */                             int _jspx_eval_awolf_005ftimechart_005f12 = _jspx_th_awolf_005ftimechart_005f12.doStartTag();
/* 5216 */                             if (_jspx_eval_awolf_005ftimechart_005f12 != 0) {
/* 5217 */                               if (_jspx_eval_awolf_005ftimechart_005f12 != 1) {
/* 5218 */                                 out = _jspx_page_context.pushBody();
/* 5219 */                                 _jspx_th_awolf_005ftimechart_005f12.setBodyContent((BodyContent)out);
/* 5220 */                                 _jspx_th_awolf_005ftimechart_005f12.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 5223 */                                 out.write(32);
/* 5224 */                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f12.doAfterBody();
/* 5225 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 5228 */                               if (_jspx_eval_awolf_005ftimechart_005f12 != 1) {
/* 5229 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 5232 */                             if (_jspx_th_awolf_005ftimechart_005f12.doEndTag() == 5) {
/* 5233 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f12); return;
/*      */                             }
/*      */                             
/* 5236 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f12);
/* 5237 */                             out.write("</td>\n\t\t\t");
/*      */                           }
/* 5239 */                           out.write("\n\n\t\t\t</tr>\n\t\t\t</table></td>\n\t\t\t<tr>\n\t\t\t<td valign=\"top\" class=\"rborder\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n        <tr>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 5240 */                           out.print(FormatUtil.getString("table.heading.attribute"));
/* 5241 */                           out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 5242 */                           out.print(FormatUtil.getString("table.heading.value"));
/* 5243 */                           out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 5244 */                           out.print(FormatUtil.getString("table.heading.status"));
/* 5245 */                           out.write("</span></td>\n        </tr>\n        <tr>\n          <td width=\"41%\" class=\"whitegrayborder\">");
/* 5246 */                           out.print(FormatUtil.getString("Transactions/Min"));
/* 5247 */                           out.write("</td>\n\n\n          <td width=\"24%\" class=\"whitegrayborder\">\n\n\t\t  \t\t  ");
/* 5248 */                           if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5249 */                             out.write("\n\t\t  \t\t  -\n\t\t  \t\t  ");
/*      */                           } else {
/* 5251 */                             out.write("\n\t\t  \t\t  ");
/* 5252 */                             out.print(df.format(Double.parseDouble(dbprop.getProperty("TRANSACTIONSPERMIN"))));
/* 5253 */                             out.write("\n\t\t  \t\t  ");
/*      */                           }
/* 5255 */                           out.write("\n\n\t\t  </td>\n\n          <td width=\"35%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5256 */                           out.print(dbprop.getProperty("DBID"));
/* 5257 */                           out.write("&attributeid=3131')\">");
/* 5258 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3132")));
/* 5259 */                           out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 5260 */                           out.print(FormatUtil.getString("Replication Transactions/Min"));
/* 5261 */                           out.write("</td>\n\n          <td class=\"yellowgrayborder\">\n\n\t\t  ");
/* 5262 */                           if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5263 */                             out.write("\n\t\t  -\n\t\t  ");
/*      */                           } else {
/* 5265 */                             out.write("\n\t\t  ");
/* 5266 */                             out.print(df.format(Double.parseDouble(dbprop.getProperty("REPLICATIONTRANSACTIONPERMIN"))));
/* 5267 */                             out.write("\n\t\t  ");
/*      */                           }
/* 5269 */                           out.write("\n\n\t\t  </td>\n\n\n\n          <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5270 */                           out.print(dbprop.getProperty("DBID"));
/* 5271 */                           out.write("&attributeid=3133')\">");
/* 5272 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3133")));
/* 5273 */                           out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 5274 */                           out.print(FormatUtil.getString("Active Transactions"));
/* 5275 */                           out.write("</td>\n\n\n           <td class=\"whitegrayborder\">\n\n\t\t  ");
/* 5276 */                           if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5277 */                             out.write("\n\t\t  -\n\t\t  ");
/*      */                           } else {
/* 5279 */                             out.write("\n\t\t  ");
/* 5280 */                             out.print(df.format(Double.parseDouble(dbprop.getProperty("ACTIVETRANSACTIONS"))));
/* 5281 */                             out.write("\n\t\t  ");
/*      */                           }
/* 5283 */                           out.write("\n\n\t\t  </td>\n\n\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5284 */                           out.print(dbprop.getProperty("DBID"));
/* 5285 */                           out.write("&attributeid=3134')\">");
/* 5286 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3134")));
/* 5287 */                           out.write("</a></td>\n        </tr>\n\t\t<tr> \n          <td class=\"whitegrayborder\">");
/* 5288 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.bulkcopyrows"));
/* 5289 */                           out.write("</td>\n          <td class=\"whitegrayborder\">\n\t\t  \n\t\t  ");
/* 5290 */                           if ((dbprop.getProperty("STATUS").trim().equals("OFFLINE")) || (dbprop.getProperty("STATUS1").trim().equals("INACTIVE")) || (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5291 */                             out.write("\n\t\t  -\n\t\t  ");
/*      */                           } else {
/* 5293 */                             out.write("  \n\t\t  ");
/* 5294 */                             out.print(df.format(Double.parseDouble(dbprop.getProperty("BULKCPYROWSPERMIN"))));
/* 5295 */                             out.write("\n\t\t  ");
/*      */                           }
/* 5297 */                           out.write("\n\t\t  \n\t\t  </td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5298 */                           out.print(dbprop.getProperty("DBID"));
/* 5299 */                           out.write("&attributeid=3809')\">");
/* 5300 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3809")));
/* 5301 */                           out.write("</a></td>\n        </tr>\t\n\t\t<tr> \n          <td class=\"whitegrayborder\">");
/* 5302 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.bulkcopythroughput"));
/* 5303 */                           out.write("</td>\n          <td class=\"whitegrayborder\">\n\t\t  \n\t\t  ");
/* 5304 */                           if ((dbprop.getProperty("STATUS").trim().equals("OFFLINE")) || (dbprop.getProperty("STATUS1").trim().equals("INACTIVE")) || (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5305 */                             out.write("\n\t\t  -\n\t\t  ");
/*      */                           } else {
/* 5307 */                             out.write("  \n          ");
/* 5308 */                             out.print(df.format(Double.parseDouble(dbprop.getProperty("BULKCPYTPUTPERMIN"))));
/* 5309 */                             out.write("\n\t\t  ");
/*      */                           }
/* 5311 */                           out.write("\n\t\t  \n\t\t  </td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5312 */                           out.print(dbprop.getProperty("DBID"));
/* 5313 */                           out.write("&attributeid=3810')\">");
/* 5314 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3810")));
/* 5315 */                           out.write("</a></td>\n        </tr>\n\t\t\n\t\t<tr> \n          <td class=\"whitegrayborder\">");
/* 5316 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.backuprestorethroughput"));
/* 5317 */                           out.write("</td>\n          <td class=\"whitegrayborder\">\n\t\t  \n\t\t  ");
/* 5318 */                           if ((dbprop.getProperty("STATUS").trim().equals("OFFLINE")) || (dbprop.getProperty("STATUS1").trim().equals("INACTIVE")) || (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5319 */                             out.write("\n\t\t  -\n\t\t  ");
/*      */                           } else {
/* 5321 */                             out.write("  \n          ");
/* 5322 */                             out.print(df.format(Double.parseDouble(dbprop.getProperty("BKUPRSTTPUTPERMIN"))));
/* 5323 */                             out.write("\n\t\t  ");
/*      */                           }
/* 5325 */                           out.write("\n\t\t  \n\t\t  </td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5326 */                           out.print(dbprop.getProperty("DBID"));
/* 5327 */                           out.write("&attributeid=3811')\">");
/* 5328 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3811")));
/* 5329 */                           out.write("</a></td>\n        </tr>\t\t\t\n\t\t<tr> \n          <td class=\"whitegrayborder\">");
/* 5330 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.logcachereads"));
/* 5331 */                           out.write("</td>\n          <td class=\"whitegrayborder\">\n\t\t  \n\t\t  ");
/* 5332 */                           if ((dbprop.getProperty("STATUS").trim().equals("OFFLINE")) || (dbprop.getProperty("STATUS1").trim().equals("INACTIVE")) || (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5333 */                             out.write("\n\t\t  -\n\t\t  ");
/*      */                           } else {
/* 5335 */                             out.write("  \n          ");
/* 5336 */                             out.print(df.format(Double.parseDouble(dbprop.getProperty("LOGCHEREADSPERMIN"))));
/* 5337 */                             out.write("\n\t\t  ");
/*      */                           }
/* 5339 */                           out.write("\n\t\t  \n\t\t  </td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5340 */                           out.print(dbprop.getProperty("DBID"));
/* 5341 */                           out.write("&attributeid=3812')\">");
/* 5342 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3812")));
/* 5343 */                           out.write("</a></td>\n        </tr>\n        <tr>\n          <td colspan=\"3\" align=\"right\" class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5344 */                           out.print(dbprop.getProperty("DBID"));
/* 5345 */                           out.write("&attributeIDs=3132,3133,3134,3809,3810,3811,3812&attributeToSelect=3132&redirectto=");
/* 5346 */                           out.print(encodeurl);
/* 5347 */                           out.write("'class=\"staticlinks\">");
/* 5348 */                           out.print(ALERTCONFIG_TEXT);
/* 5349 */                           out.write("</a></td>\n        </tr>\n      </table></td>\n\t\t\t<td valign=\"top\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n        <tr>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 5350 */                           out.print(FormatUtil.getString("table.heading.attribute"));
/* 5351 */                           out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 5352 */                           out.print(FormatUtil.getString("table.heading.value"));
/* 5353 */                           out.write("</span></td>\n          <td class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 5354 */                           out.print(FormatUtil.getString("table.heading.status"));
/* 5355 */                           out.write("</span></td>\n        </tr>\n        <tr>\n          <td width=\"41%\" class=\"whitegrayborder\">");
/* 5356 */                           out.print(FormatUtil.getString("Log Flush/Min"));
/* 5357 */                           out.write("</td>\n\n          <td width=\"24%\" class=\"whitegrayborder\">\n\n\t\t  ");
/* 5358 */                           if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5359 */                             out.write("\n\t\t  -\n\t\t  ");
/*      */                           } else {
/* 5361 */                             out.write("\n\t\t  ");
/* 5362 */                             out.print(df.format(Double.parseDouble(dbprop.getProperty("LOGFULSHESPERMIN"))));
/* 5363 */                             out.write("\n\t\t  ");
/*      */                           }
/* 5365 */                           out.write("\n\n\t\t  </td>\n\n\n\n\n          <td width=\"42%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5366 */                           out.print(dbprop.getProperty("DBID"));
/* 5367 */                           out.write("&attributeid=3135')\">");
/* 5368 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3135")));
/* 5369 */                           out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 5370 */                           out.print(FormatUtil.getString("Log Flush Waits/Min"));
/* 5371 */                           out.write("</td>\n\n\n          <td class=\"yellowgrayborder\">\n\n\t\t  ");
/* 5372 */                           if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5373 */                             out.write("\n\t\t  -\n\t\t  ");
/*      */                           } else {
/* 5375 */                             out.write("\n\t\t  ");
/* 5376 */                             out.print(df.format(Double.parseDouble(dbprop.getProperty("LOGFLUSHWAITSPERMIN"))));
/* 5377 */                             out.write("\n\t\t  ");
/*      */                           }
/* 5379 */                           out.write("\n\n\t\t  </td>\n\n\n          <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5380 */                           out.print(dbprop.getProperty("DBID"));
/* 5381 */                           out.write("&attributeid=3136')\">");
/* 5382 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3136")));
/* 5383 */                           out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 5384 */                           out.print(FormatUtil.getString("Log Flush Wait Time"));
/* 5385 */                           out.write("</td>\n\n          <td class=\"whitegrayborder\">\n\n\t\t  ");
/* 5386 */                           if (((dbprop.getProperty("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (dbprop.getProperty("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (dbprop.getProperty("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) && (Double.parseDouble(dbprop.getProperty("DATAFILESSIZE")) == -1.0D)) {
/* 5387 */                             out.write("\n\t\t  -\n\t\t  ");
/*      */                           } else {
/* 5389 */                             out.write("\n\t\t ");
/* 5390 */                             out.print(df.format(Double.parseDouble(dbprop.getProperty("LOGFLUSHWAITTIME"))));
/* 5391 */                             out.write(" ms\n\t\t  ");
/*      */                           }
/* 5393 */                           out.write("\n\n          </td>\n\n\n        <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5394 */                           out.print(dbprop.getProperty("DBID"));
/* 5395 */                           out.write("&attributeid=3137')\">");
/* 5396 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3137")));
/* 5397 */                           out.write("</a></td>\n        </tr>\n        <tr>\n          <td colspan=\"3\" align=\"right\" class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5398 */                           out.print(dbprop.getProperty("DBID"));
/* 5399 */                           out.write("&attributeIDs=3135,3136,3137&attributeToSelect=3135&redirectto=");
/* 5400 */                           out.print(encodeurl);
/* 5401 */                           out.write("'class=\"staticlinks\">");
/* 5402 */                           out.print(ALERTCONFIG_TEXT);
/* 5403 */                           out.write("</a></td>\n        </tr>\n      </table></td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t\n\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t<td>&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"50%\" height=\"31\" class=\"tableheading\">\n\t\t\t\t\t\t");
/* 5404 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.header.databaseproperties"));
/* 5405 */                           out.write("\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td valign=\"top\" width=\"50%\" class=\"rborder\"> \n\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\t\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t\t\t<td class=\"yellowgrayborder\">\n\t\t\t\t\t\t\t\t\t<span class=\"bodytextbold\"  >");
/* 5406 */                           out.print(FormatUtil.getString("table.heading.attribute"));
/* 5407 */                           out.write("</span>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td class=\"yellowgrayborder\" >\n\t\t\t\t\t\t\t\t\t<span class=\"bodytextbold\" >");
/* 5408 */                           out.print(FormatUtil.getString("table.heading.value"));
/* 5409 */                           out.write("</span>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td class=\"yellowgrayborder\">\n\t\t\t\t\t\t\t\t\t<span class=\"bodytextbold\">");
/* 5410 */                           out.print(FormatUtil.getString("table.heading.status"));
/* 5411 */                           out.write("</span>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t\t\t<td width=\"41%\"  class=\"whitegrayborder\">");
/* 5412 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.creationdate"));
/* 5413 */                           out.write("</td>\n\t\t\t\t\t\t\t\t<td width=\"24%\"  class=\"whitegrayborder\" >\n\t\t\t \n\t\t\t\t\t\t\t\t  ");
/* 5414 */                           if ((dbprop.getProperty("STATUS").trim().equals("OFFLINE")) || (dbprop.getProperty("STATUS1").trim().equals("INACTIVE")) || (dbprop.getProperty("CREATIONDATE") == null)) {
/* 5415 */                             out.write("\n\t\t\t\t\t\t\t\t  -\n\t\t\t\t\t\t\t\t  ");
/*      */                           } else {
/* 5417 */                             out.write("\n\t\t\t\t\t\t\t\t  ");
/* 5418 */                             out.print(dbprop.getProperty("CREATIONDATE").substring(0, 10));
/* 5419 */                             out.write(" \n\t\t\t\t\t\t\t\t  ");
/*      */                           }
/* 5421 */                           out.write("\n\t\t\t\t\t\t \n\t\t\t\t\t  \n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td width=\"41%\"  class=\"whitegrayborder\" align=\"center\">&nbsp;</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\">");
/* 5422 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.DatabaseMode"));
/* 5423 */                           out.write("</td>\n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\" >\t\t\t\t  \n\t\t\t\t\t\t\t\t  ");
/* 5424 */                           out.print(dbprop.getProperty("DBMODE"));
/* 5425 */                           out.write(" \t  \n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\">&nbsp;\n\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5426 */                           out.print(dbprop.getProperty("DBID"));
/* 5427 */                           out.write("&attributeid=3865')\">");
/* 5428 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3865")));
/* 5429 */                           out.write("</a>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\">");
/* 5430 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.pageverify"));
/* 5431 */                           out.write("</td>\n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\" >\n\t\t\t\t\t\t\t\t  ");
/* 5432 */                           out.print(dbprop.getProperty("PAGEVERIFY"));
/* 5433 */                           out.write("\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\">&nbsp;\n\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5434 */                           out.print(dbprop.getProperty("DBID"));
/* 5435 */                           out.write("&attributeid=3866')\">");
/* 5436 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3866")));
/* 5437 */                           out.write("</a>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\t\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\" style=\"border-bottom:none;\">");
/* 5438 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.compatibilitylevel"));
/* 5439 */                           out.write("</td>\n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\" style=\"border-bottom:none;\">\t\t\t\t  \n\t\t\t\t\t\t\t\t  ");
/* 5440 */                           out.print(FormatUtil.getString(dbprop.getProperty("COMPATIBILITYLEVEL")));
/* 5441 */                           out.write(" \t  \n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\" align=\"center\" style=\"border-bottom:none;\">&nbsp;</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td valign=\"top\" width=\"50%\" class=\"rborder\"> \n\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\t\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t\t\t<td class=\"yellowgrayborder\">\n\t\t\t\t\t\t\t\t\t<span class=\"bodytextbold\"  >");
/* 5442 */                           out.print(FormatUtil.getString("table.heading.attribute"));
/* 5443 */                           out.write("</span>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td class=\"yellowgrayborder\" >\n\t\t\t\t\t\t\t\t\t<span class=\"bodytextbold\" >");
/* 5444 */                           out.print(FormatUtil.getString("table.heading.value"));
/* 5445 */                           out.write("</span>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td class=\"yellowgrayborder\">\n\t\t\t\t\t\t\t\t\t<span class=\"bodytextbold\">");
/* 5446 */                           out.print(FormatUtil.getString("table.heading.status"));
/* 5447 */                           out.write("</span>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t\t\t<td width=\"41%\"  class=\"whitegrayborder\">");
/* 5448 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.autoshrink"));
/* 5449 */                           out.write("</td>\n\t\t\t\t\t\t\t\t<td width=\"24%\"  class=\"whitegrayborder\" >\n\t\t\t\t\t\t\t\t");
/*      */                           
/* 5451 */                           if (dbprop.getProperty("AUTOSHRINK").equals("-")) {
/* 5452 */                             out.write("\n\t\t\t\t\t\t\t\t\t- \n\t\t\t\t\t\t\t\t");
/*      */                           }
/*      */                           else {
/* 5455 */                             out.write("\t  \n\t\t\t\t\t\t\t\t  ");
/* 5456 */                             out.print(FormatUtil.getString("am.webclient." + dbprop.getProperty("AUTOSHRINK")));
/* 5457 */                             out.write(" \t\n\t\t\t\t\t\t\t\t");
/*      */                           }
/* 5459 */                           out.write("  \n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td width=\"41%\" class=\"whitegrayborder\">&nbsp;\n\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5460 */                           out.print(dbprop.getProperty("DBID"));
/* 5461 */                           out.write("&attributeid=3867')\">");
/* 5462 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3867")));
/* 5463 */                           out.write("</a>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\">");
/* 5464 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.autocreatestats"));
/* 5465 */                           out.write("</td>\n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\">\n\t\t\t\t\t\t\t\t");
/*      */                           
/* 5467 */                           if (dbprop.getProperty("AUTOCREATESTATS").equals("-")) {
/* 5468 */                             out.write("\n\t\t\t\t\t\t\t\t\t- \n\t\t\t\t\t\t\t\t");
/*      */                           }
/*      */                           else {
/* 5471 */                             out.write("\t  \n\t\t\t\t\t\t\t\t  ");
/* 5472 */                             out.print(FormatUtil.getString("am.webclient." + dbprop.getProperty("AUTOCREATESTATS")));
/* 5473 */                             out.write(" \t\n\t\t\t\t\t\t\t\t");
/*      */                           }
/* 5475 */                           out.write("  \n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\">&nbsp;\n\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5476 */                           out.print(dbprop.getProperty("DBID"));
/* 5477 */                           out.write("&attributeid=3868')\">");
/* 5478 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3868")));
/* 5479 */                           out.write("</a>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\t\t\n\t\t\t\t\t\n\t\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\">");
/* 5480 */                           out.print(FormatUtil.getString("am.webclient.mssqldetails.autoupdatestats"));
/* 5481 */                           out.write("</td>\n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\" >\t\t\t\t  \n\t\t\t\t\t\t\t\t");
/*      */                           
/* 5483 */                           if (dbprop.getProperty("AUTOUPDATESTATS").equals("-")) {
/* 5484 */                             out.write("\n\t\t\t\t\t\t\t\t\t- \n\t\t\t\t\t\t\t\t");
/*      */                           }
/*      */                           else {
/* 5487 */                             out.write("\t  \n\t\t\t\t\t\t\t\t  ");
/* 5488 */                             out.print(FormatUtil.getString("am.webclient." + dbprop.getProperty("AUTOUPDATESTATS")));
/* 5489 */                             out.write(" \t\n\t\t\t\t\t\t\t\t");
/*      */                           }
/* 5491 */                           out.write("    \n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\">&nbsp;\n\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5492 */                           out.print(dbprop.getProperty("DBID"));
/* 5493 */                           out.write("&attributeid=3869')\">");
/* 5494 */                           out.print(getSeverityImage(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3869")));
/* 5495 */                           out.write("</a>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t\t\t<td colspan=\"3\" align=\"right\" class=\"yellowgrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5496 */                           out.print(dbprop.getProperty("DBID"));
/* 5497 */                           out.write("&attributeIDs=3865,3866,3867,3868,3869&attributeToSelect=3865&redirectto=");
/* 5498 */                           out.print(encodeurl);
/* 5499 */                           out.write("'class=\"staticlinks\">");
/* 5500 */                           out.print(ALERTCONFIG_TEXT);
/* 5501 */                           out.write("</a></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n");
/* 5502 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f4.doAfterBody();
/* 5503 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5507 */                       if (_jspx_th_logic_005fnotEmpty_005f4.doEndTag() == 5) {
/* 5508 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4); return;
/*      */                       }
/*      */                       
/* 5511 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 5512 */                       out.write(10);
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5519 */                     out.write(10);
/* 5520 */                     out.write(9);
/* 5521 */                     out.write(9);
/*      */                     
/* 5523 */                     IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5524 */                     _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 5525 */                     _jspx_th_logic_005fiterate_005f0.setParent(null);
/*      */                     
/* 5527 */                     _jspx_th_logic_005fiterate_005f0.setName("script_ids");
/*      */                     
/* 5529 */                     _jspx_th_logic_005fiterate_005f0.setId("attribute");
/*      */                     
/* 5531 */                     _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                     
/* 5533 */                     _jspx_th_logic_005fiterate_005f0.setType("java.lang.String");
/* 5534 */                     int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 5535 */                     if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 5536 */                       String attribute = null;
/* 5537 */                       Integer j = null;
/* 5538 */                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 5539 */                         out = _jspx_page_context.pushBody();
/* 5540 */                         _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 5541 */                         _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                       }
/* 5543 */                       attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 5544 */                       j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                       for (;;) {
/* 5546 */                         out.write(10);
/* 5547 */                         out.write(9);
/* 5548 */                         out.write(32);
/*      */                         
/* 5550 */                         String query = "select scriptname,displayname from AM_ScriptArgs where resourceid=" + attribute;
/* 5551 */                         String monitorname1 = null;
/* 5552 */                         String displayname1 = null;
/*      */                         try
/*      */                         {
/* 5555 */                           AMConnectionPool cp = AMConnectionPool.getInstance();
/* 5556 */                           ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 5557 */                           if (rs.next())
/*      */                           {
/* 5559 */                             monitorname1 = rs.getString("scriptname");
/* 5560 */                             displayname1 = rs.getString("displayname");
/*      */                           }
/* 5562 */                           rs.close();
/*      */                         }
/*      */                         catch (Exception exc) {}
/*      */                         
/*      */ 
/* 5567 */                         String url2 = "/showresource.do?resourceid=" + attribute + "&type=Script Monitor&moname=" + monitorname1 + "&method=showScriptMonitorDetails&resourcename=" + displayname1 + "&fromhost=true";
/* 5568 */                         String url3 = "/jsp/HostScript.jsp?resourceid=" + attribute + "&type=Script Monitor&moname=" + monitorname1 + "&method=showScriptMonitorDetails&resourcename=" + displayname1 + "&fromhost=true&hostid=" + resourceid;
/*      */                         
/* 5570 */                         out.write("\n         <table  width=\"99%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n\t ");
/* 5571 */                         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, url2, out, false);
/* 5572 */                         out.write("\n         <tr>\n         <td width=\"99%\"   class=\"tableheadingtrans\" >\n         <a href=\"showresource.do?method=showResourceForResourceID&resourceid=");
/* 5573 */                         out.print(attribute);
/* 5574 */                         out.write("\" class=\"staticlinks\">");
/* 5575 */                         out.print(FormatUtil.getString("am.webclient.hostResource.servers.scriptmonitor"));
/* 5576 */                         out.write(32);
/* 5577 */                         out.write(45);
/* 5578 */                         out.write(32);
/* 5579 */                         out.print(displayname1);
/* 5580 */                         out.write("</a>\n         </td>\n         </tr>\n         <tr>\n         <td>\n         ");
/* 5581 */                         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, url3, out, false);
/* 5582 */                         out.write("\n         </td>\n         </tr>\n         <br>\n         </table>\n         <br>\n         ");
/* 5583 */                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 5584 */                         attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 5585 */                         j = (Integer)_jspx_page_context.findAttribute("j");
/* 5586 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 5589 */                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 5590 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 5593 */                     if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 5594 */                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*      */                     }
/*      */                     else {
/* 5597 */                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 5598 */                       out.write("\n\t <br>\n\t<!--%@ include file=\"/jsp/includes/HostPerformance.jspf\" %>-->\n</body>\n\n</html>");
/*      */                     }
/* 5600 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5601 */         out = _jspx_out;
/* 5602 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5603 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5604 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5607 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5613 */     PageContext pageContext = _jspx_page_context;
/* 5614 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5616 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5617 */     _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 5618 */     _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */     
/* 5620 */     _jspx_th_logic_005fnotEmpty_005f0.setName("dbdetails");
/* 5621 */     int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 5622 */     if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */       for (;;) {
/* 5624 */         out.write("\nfunction myOnLoad()\n{   \n\t  SORTTABLENAME = 'databaseDetails';  //NO I18N\n\t  var numberOfColumnsToBeSorted =2;\n      var ignoreCheckBox =false;\n\t  var ignoreDefaultSorting=false;\n\t  sortables_init(numberOfColumnsToBeSorted,ignoreCheckBox,ignoreDefaultSorting);\n}\n");
/* 5625 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 5626 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5630 */     if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 5631 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 5632 */       return true;
/*      */     }
/* 5634 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 5635 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5640 */     PageContext pageContext = _jspx_page_context;
/* 5641 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5643 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5644 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 5645 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 5647 */     _jspx_th_c_005fif_005f0.setTest("${not empty param.haid}");
/* 5648 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 5649 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 5651 */         out.write(10);
/* 5652 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 5653 */           return true;
/* 5654 */         out.write(10);
/* 5655 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 5656 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5660 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 5661 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5662 */       return true;
/*      */     }
/* 5664 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5665 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5670 */     PageContext pageContext = _jspx_page_context;
/* 5671 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5673 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5674 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 5675 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 5677 */     _jspx_th_c_005fset_005f0.setVar("myfield_resid");
/*      */     
/* 5679 */     _jspx_th_c_005fset_005f0.setScope("page");
/* 5680 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 5681 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 5682 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5683 */         out = _jspx_page_context.pushBody();
/* 5684 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 5685 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5688 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 5689 */           return true;
/* 5690 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 5691 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5694 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5695 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5698 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5699 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 5700 */       return true;
/*      */     }
/* 5702 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 5703 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5708 */     PageContext pageContext = _jspx_page_context;
/* 5709 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5711 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5712 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5713 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5715 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 5716 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5717 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5718 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5719 */       return true;
/*      */     }
/* 5721 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5722 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5727 */     PageContext pageContext = _jspx_page_context;
/* 5728 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5730 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5731 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 5732 */     _jspx_th_c_005fif_005f1.setParent(null);
/*      */     
/* 5734 */     _jspx_th_c_005fif_005f1.setTest("${not empty param.resourceid}");
/* 5735 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 5736 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 5738 */         out.write(10);
/* 5739 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 5740 */           return true;
/* 5741 */         out.write(10);
/* 5742 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 5743 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5747 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 5748 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5749 */       return true;
/*      */     }
/* 5751 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5752 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5757 */     PageContext pageContext = _jspx_page_context;
/* 5758 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5760 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5761 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 5762 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 5764 */     _jspx_th_c_005fset_005f1.setVar("myfield_resid");
/*      */     
/* 5766 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 5767 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 5768 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 5769 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5770 */         out = _jspx_page_context.pushBody();
/* 5771 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 5772 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5775 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 5776 */           return true;
/* 5777 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 5778 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5781 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5782 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5785 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 5786 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 5787 */       return true;
/*      */     }
/* 5789 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 5790 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5795 */     PageContext pageContext = _jspx_page_context;
/* 5796 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5798 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5799 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5800 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 5802 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 5803 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5804 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5805 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5806 */       return true;
/*      */     }
/* 5808 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5809 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5814 */     PageContext pageContext = _jspx_page_context;
/* 5815 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5817 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5818 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 5819 */     _jspx_th_c_005fset_005f2.setParent(null);
/*      */     
/* 5821 */     _jspx_th_c_005fset_005f2.setVar("trstripclass");
/*      */     
/* 5823 */     _jspx_th_c_005fset_005f2.setScope("page");
/* 5824 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 5825 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 5826 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5827 */         out = _jspx_page_context.pushBody();
/* 5828 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 5829 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5832 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 5833 */           return true;
/* 5834 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 5835 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5838 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 5839 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5842 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 5843 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 5844 */       return true;
/*      */     }
/* 5846 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 5847 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5852 */     PageContext pageContext = _jspx_page_context;
/* 5853 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5855 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5856 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5857 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 5859 */     _jspx_th_c_005fout_005f2.setValue("");
/* 5860 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5861 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5862 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5863 */       return true;
/*      */     }
/* 5865 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5866 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5871 */     PageContext pageContext = _jspx_page_context;
/* 5872 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5874 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5875 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 5876 */     _jspx_th_c_005fset_005f3.setParent(null);
/*      */     
/* 5878 */     _jspx_th_c_005fset_005f3.setVar("myfield_entity");
/*      */     
/* 5880 */     _jspx_th_c_005fset_005f3.setScope("page");
/* 5881 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 5882 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 5883 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5884 */         out = _jspx_page_context.pushBody();
/* 5885 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 5886 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5889 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 5890 */           return true;
/* 5891 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 5892 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5895 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5896 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5899 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 5900 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 5901 */       return true;
/*      */     }
/* 5903 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 5904 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5909 */     PageContext pageContext = _jspx_page_context;
/* 5910 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5912 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5913 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5914 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 5916 */     _jspx_th_c_005fout_005f3.setValue("noalarms");
/* 5917 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5918 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5919 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5920 */       return true;
/*      */     }
/* 5922 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5923 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5928 */     PageContext pageContext = _jspx_page_context;
/* 5929 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5931 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5932 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 5933 */     _jspx_th_c_005fif_005f2.setParent(null);
/*      */     
/* 5935 */     _jspx_th_c_005fif_005f2.setTest("${not empty param.entity}");
/* 5936 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 5937 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 5939 */         out.write(10);
/* 5940 */         if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 5941 */           return true;
/* 5942 */         out.write(10);
/* 5943 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5944 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5948 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5949 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5950 */       return true;
/*      */     }
/* 5952 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5953 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5958 */     PageContext pageContext = _jspx_page_context;
/* 5959 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5961 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5962 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 5963 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5965 */     _jspx_th_c_005fset_005f4.setVar("myfield_entity");
/*      */     
/* 5967 */     _jspx_th_c_005fset_005f4.setScope("page");
/* 5968 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 5969 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 5970 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 5971 */         out = _jspx_page_context.pushBody();
/* 5972 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 5973 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5976 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f4, _jspx_page_context))
/* 5977 */           return true;
/* 5978 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 5979 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5982 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 5983 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5986 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 5987 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 5988 */       return true;
/*      */     }
/* 5990 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 5991 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5996 */     PageContext pageContext = _jspx_page_context;
/* 5997 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5999 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6000 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6001 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 6003 */     _jspx_th_c_005fout_005f4.setValue("${param.entity}");
/* 6004 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 6005 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 6006 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6007 */       return true;
/*      */     }
/* 6009 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6010 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6015 */     PageContext pageContext = _jspx_page_context;
/* 6016 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6018 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6019 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 6020 */     _jspx_th_c_005fif_005f3.setParent(null);
/*      */     
/* 6022 */     _jspx_th_c_005fif_005f3.setTest("${not empty param.includeClass}");
/* 6023 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 6024 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 6026 */         out.write(10);
/* 6027 */         if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 6028 */           return true;
/* 6029 */         out.write(10);
/* 6030 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 6031 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6035 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 6036 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 6037 */       return true;
/*      */     }
/* 6039 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 6040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6045 */     PageContext pageContext = _jspx_page_context;
/* 6046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6048 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6049 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 6050 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6052 */     _jspx_th_c_005fset_005f5.setVar("trstripclass");
/*      */     
/* 6054 */     _jspx_th_c_005fset_005f5.setScope("page");
/* 6055 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 6056 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 6057 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 6058 */         out = _jspx_page_context.pushBody();
/* 6059 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 6060 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6063 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 6064 */           return true;
/* 6065 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 6066 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6069 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 6070 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6073 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 6074 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 6075 */       return true;
/*      */     }
/* 6077 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 6078 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6083 */     PageContext pageContext = _jspx_page_context;
/* 6084 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6086 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6087 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6088 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 6090 */     _jspx_th_c_005fout_005f5.setValue("${param.includeClass}");
/* 6091 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6092 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6093 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6094 */       return true;
/*      */     }
/* 6096 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6097 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6102 */     PageContext pageContext = _jspx_page_context;
/* 6103 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6105 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6106 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 6107 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6109 */     _jspx_th_c_005fif_005f4.setTest("${not empty param.haid}");
/* 6110 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 6111 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 6113 */         out.write(10);
/* 6114 */         out.write(9);
/* 6115 */         out.write(9);
/* 6116 */         if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 6117 */           return true;
/* 6118 */         out.write(10);
/* 6119 */         out.write(9);
/* 6120 */         out.write(9);
/* 6121 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 6122 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6126 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 6127 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6128 */       return true;
/*      */     }
/* 6130 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6131 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6136 */     PageContext pageContext = _jspx_page_context;
/* 6137 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6139 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6140 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 6141 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6143 */     _jspx_th_c_005fset_005f6.setVar("myfield_paramresid");
/*      */     
/* 6145 */     _jspx_th_c_005fset_005f6.setScope("page");
/* 6146 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 6147 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 6148 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 6149 */         out = _jspx_page_context.pushBody();
/* 6150 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 6151 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6154 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f6, _jspx_page_context))
/* 6155 */           return true;
/* 6156 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 6157 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6160 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 6161 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6164 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 6165 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 6166 */       return true;
/*      */     }
/* 6168 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 6169 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6174 */     PageContext pageContext = _jspx_page_context;
/* 6175 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6177 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6178 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 6179 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 6181 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 6182 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 6183 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 6184 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6185 */       return true;
/*      */     }
/* 6187 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6188 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6193 */     PageContext pageContext = _jspx_page_context;
/* 6194 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6196 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6197 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 6198 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6200 */     _jspx_th_c_005fif_005f5.setTest("${not empty param.resourceid}");
/* 6201 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 6202 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 6204 */         out.write(10);
/* 6205 */         out.write(9);
/* 6206 */         out.write(9);
/* 6207 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 6208 */           return true;
/* 6209 */         out.write(10);
/* 6210 */         out.write(9);
/* 6211 */         out.write(9);
/* 6212 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 6213 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6217 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 6218 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6219 */       return true;
/*      */     }
/* 6221 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6222 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6227 */     PageContext pageContext = _jspx_page_context;
/* 6228 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6230 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6231 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 6232 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 6234 */     _jspx_th_c_005fset_005f7.setVar("myfield_paramresid");
/*      */     
/* 6236 */     _jspx_th_c_005fset_005f7.setScope("page");
/* 6237 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 6238 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 6239 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 6240 */         out = _jspx_page_context.pushBody();
/* 6241 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 6242 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6245 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 6246 */           return true;
/* 6247 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 6248 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6251 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 6252 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6255 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 6256 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 6257 */       return true;
/*      */     }
/* 6259 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 6260 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6265 */     PageContext pageContext = _jspx_page_context;
/* 6266 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6268 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6269 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 6270 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 6272 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 6273 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 6274 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 6275 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6276 */       return true;
/*      */     }
/* 6278 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6279 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6284 */     PageContext pageContext = _jspx_page_context;
/* 6285 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6287 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6288 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 6289 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6291 */     _jspx_th_c_005fout_005f8.setValue("${myfield_paramresid}");
/* 6292 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 6293 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 6294 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6295 */       return true;
/*      */     }
/* 6297 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6298 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6303 */     PageContext pageContext = _jspx_page_context;
/* 6304 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6306 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6307 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 6308 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6310 */     _jspx_th_c_005fif_005f6.setTest("${not empty param.haid}");
/* 6311 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 6312 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 6314 */         out.write(10);
/* 6315 */         if (_jspx_meth_c_005fset_005f8(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 6316 */           return true;
/* 6317 */         out.write(10);
/* 6318 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 6319 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6323 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 6324 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6325 */       return true;
/*      */     }
/* 6327 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6328 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f8(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6333 */     PageContext pageContext = _jspx_page_context;
/* 6334 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6336 */     SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6337 */     _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 6338 */     _jspx_th_c_005fset_005f8.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6340 */     _jspx_th_c_005fset_005f8.setVar("myfield_resid");
/*      */     
/* 6342 */     _jspx_th_c_005fset_005f8.setScope("page");
/* 6343 */     int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 6344 */     if (_jspx_eval_c_005fset_005f8 != 0) {
/* 6345 */       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 6346 */         out = _jspx_page_context.pushBody();
/* 6347 */         _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 6348 */         _jspx_th_c_005fset_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6351 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fset_005f8, _jspx_page_context))
/* 6352 */           return true;
/* 6353 */         int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 6354 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6357 */       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 6358 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6361 */     if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 6362 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f8);
/* 6363 */       return true;
/*      */     }
/* 6365 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f8);
/* 6366 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fset_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6371 */     PageContext pageContext = _jspx_page_context;
/* 6372 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6374 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6375 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 6376 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fset_005f8);
/*      */     
/* 6378 */     _jspx_th_c_005fout_005f9.setValue("${param.haid}");
/* 6379 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 6380 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 6381 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6382 */       return true;
/*      */     }
/* 6384 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6385 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6390 */     PageContext pageContext = _jspx_page_context;
/* 6391 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6393 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6394 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 6395 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6397 */     _jspx_th_c_005fif_005f7.setTest("${not empty param.resourceid}");
/* 6398 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 6399 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 6401 */         out.write(10);
/* 6402 */         if (_jspx_meth_c_005fset_005f9(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 6403 */           return true;
/* 6404 */         out.write(10);
/* 6405 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 6406 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6410 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 6411 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 6412 */       return true;
/*      */     }
/* 6414 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 6415 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f9(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6420 */     PageContext pageContext = _jspx_page_context;
/* 6421 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6423 */     SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6424 */     _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 6425 */     _jspx_th_c_005fset_005f9.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6427 */     _jspx_th_c_005fset_005f9.setVar("myfield_resid");
/*      */     
/* 6429 */     _jspx_th_c_005fset_005f9.setScope("page");
/* 6430 */     int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 6431 */     if (_jspx_eval_c_005fset_005f9 != 0) {
/* 6432 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 6433 */         out = _jspx_page_context.pushBody();
/* 6434 */         _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 6435 */         _jspx_th_c_005fset_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6438 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fset_005f9, _jspx_page_context))
/* 6439 */           return true;
/* 6440 */         int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 6441 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6444 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 6445 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6448 */     if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 6449 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f9);
/* 6450 */       return true;
/*      */     }
/* 6452 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f9);
/* 6453 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fset_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6458 */     PageContext pageContext = _jspx_page_context;
/* 6459 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6461 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6462 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 6463 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fset_005f9);
/*      */     
/* 6465 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 6466 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 6467 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 6468 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6469 */       return true;
/*      */     }
/* 6471 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6472 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f10(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6477 */     PageContext pageContext = _jspx_page_context;
/* 6478 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6480 */     SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6481 */     _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 6482 */     _jspx_th_c_005fset_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6484 */     _jspx_th_c_005fset_005f10.setVar("trstripclass");
/*      */     
/* 6486 */     _jspx_th_c_005fset_005f10.setScope("page");
/* 6487 */     int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 6488 */     if (_jspx_eval_c_005fset_005f10 != 0) {
/* 6489 */       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 6490 */         out = _jspx_page_context.pushBody();
/* 6491 */         _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 6492 */         _jspx_th_c_005fset_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6495 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fset_005f10, _jspx_page_context))
/* 6496 */           return true;
/* 6497 */         int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 6498 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6501 */       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 6502 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6505 */     if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 6506 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f10);
/* 6507 */       return true;
/*      */     }
/* 6509 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f10);
/* 6510 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fset_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6515 */     PageContext pageContext = _jspx_page_context;
/* 6516 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6518 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6519 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 6520 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fset_005f10);
/*      */     
/* 6522 */     _jspx_th_c_005fout_005f11.setValue("");
/* 6523 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 6524 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 6525 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6526 */       return true;
/*      */     }
/* 6528 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6529 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f11(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6534 */     PageContext pageContext = _jspx_page_context;
/* 6535 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6537 */     SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6538 */     _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 6539 */     _jspx_th_c_005fset_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6541 */     _jspx_th_c_005fset_005f11.setVar("myfield_entity");
/*      */     
/* 6543 */     _jspx_th_c_005fset_005f11.setScope("page");
/* 6544 */     int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 6545 */     if (_jspx_eval_c_005fset_005f11 != 0) {
/* 6546 */       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 6547 */         out = _jspx_page_context.pushBody();
/* 6548 */         _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 6549 */         _jspx_th_c_005fset_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6552 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fset_005f11, _jspx_page_context))
/* 6553 */           return true;
/* 6554 */         int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 6555 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6558 */       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 6559 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6562 */     if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 6563 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f11);
/* 6564 */       return true;
/*      */     }
/* 6566 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f11);
/* 6567 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fset_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6572 */     PageContext pageContext = _jspx_page_context;
/* 6573 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6575 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6576 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 6577 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fset_005f11);
/*      */     
/* 6579 */     _jspx_th_c_005fout_005f12.setValue("noalarms");
/* 6580 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 6581 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 6582 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6583 */       return true;
/*      */     }
/* 6585 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6586 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6591 */     PageContext pageContext = _jspx_page_context;
/* 6592 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6594 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6595 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 6596 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6598 */     _jspx_th_c_005fif_005f8.setTest("${not empty param.entity}");
/* 6599 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 6600 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 6602 */         out.write(10);
/* 6603 */         if (_jspx_meth_c_005fset_005f12(_jspx_th_c_005fif_005f8, _jspx_page_context))
/* 6604 */           return true;
/* 6605 */         out.write(10);
/* 6606 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 6607 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6611 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 6612 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 6613 */       return true;
/*      */     }
/* 6615 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 6616 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f12(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6621 */     PageContext pageContext = _jspx_page_context;
/* 6622 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6624 */     SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6625 */     _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 6626 */     _jspx_th_c_005fset_005f12.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6628 */     _jspx_th_c_005fset_005f12.setVar("myfield_entity");
/*      */     
/* 6630 */     _jspx_th_c_005fset_005f12.setScope("page");
/* 6631 */     int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 6632 */     if (_jspx_eval_c_005fset_005f12 != 0) {
/* 6633 */       if (_jspx_eval_c_005fset_005f12 != 1) {
/* 6634 */         out = _jspx_page_context.pushBody();
/* 6635 */         _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 6636 */         _jspx_th_c_005fset_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6639 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fset_005f12, _jspx_page_context))
/* 6640 */           return true;
/* 6641 */         int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 6642 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6645 */       if (_jspx_eval_c_005fset_005f12 != 1) {
/* 6646 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6649 */     if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 6650 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f12);
/* 6651 */       return true;
/*      */     }
/* 6653 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f12);
/* 6654 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fset_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6659 */     PageContext pageContext = _jspx_page_context;
/* 6660 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6662 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6663 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 6664 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fset_005f12);
/*      */     
/* 6666 */     _jspx_th_c_005fout_005f13.setValue("${param.entity}");
/* 6667 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 6668 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 6669 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6670 */       return true;
/*      */     }
/* 6672 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6673 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6678 */     PageContext pageContext = _jspx_page_context;
/* 6679 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6681 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6682 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 6683 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6685 */     _jspx_th_c_005fif_005f9.setTest("${not empty param.includeClass}");
/* 6686 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 6687 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 6689 */         out.write(10);
/* 6690 */         if (_jspx_meth_c_005fset_005f13(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 6691 */           return true;
/* 6692 */         out.write(10);
/* 6693 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 6694 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6698 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 6699 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 6700 */       return true;
/*      */     }
/* 6702 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 6703 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f13(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6708 */     PageContext pageContext = _jspx_page_context;
/* 6709 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6711 */     SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6712 */     _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 6713 */     _jspx_th_c_005fset_005f13.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 6715 */     _jspx_th_c_005fset_005f13.setVar("trstripclass");
/*      */     
/* 6717 */     _jspx_th_c_005fset_005f13.setScope("page");
/* 6718 */     int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 6719 */     if (_jspx_eval_c_005fset_005f13 != 0) {
/* 6720 */       if (_jspx_eval_c_005fset_005f13 != 1) {
/* 6721 */         out = _jspx_page_context.pushBody();
/* 6722 */         _jspx_th_c_005fset_005f13.setBodyContent((BodyContent)out);
/* 6723 */         _jspx_th_c_005fset_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6726 */         if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fset_005f13, _jspx_page_context))
/* 6727 */           return true;
/* 6728 */         int evalDoAfterBody = _jspx_th_c_005fset_005f13.doAfterBody();
/* 6729 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6732 */       if (_jspx_eval_c_005fset_005f13 != 1) {
/* 6733 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6736 */     if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 6737 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f13);
/* 6738 */       return true;
/*      */     }
/* 6740 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f13);
/* 6741 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fset_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6746 */     PageContext pageContext = _jspx_page_context;
/* 6747 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6749 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6750 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 6751 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fset_005f13);
/*      */     
/* 6753 */     _jspx_th_c_005fout_005f14.setValue("${param.includeClass}");
/* 6754 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 6755 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 6756 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6757 */       return true;
/*      */     }
/* 6759 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6760 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6765 */     PageContext pageContext = _jspx_page_context;
/* 6766 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6768 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6769 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 6770 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6772 */     _jspx_th_c_005fout_005f15.setValue("${trstripclass}");
/* 6773 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 6774 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 6775 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6776 */       return true;
/*      */     }
/* 6778 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6779 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6784 */     PageContext pageContext = _jspx_page_context;
/* 6785 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6787 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6788 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 6789 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 6790 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 6791 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 6792 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6793 */         out = _jspx_page_context.pushBody();
/* 6794 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 6795 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6798 */         out.write("am.myfield.customfield.text");
/* 6799 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 6800 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6803 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6804 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6807 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 6808 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6809 */       return true;
/*      */     }
/* 6811 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6812 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6817 */     PageContext pageContext = _jspx_page_context;
/* 6818 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6820 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6821 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 6822 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6824 */     _jspx_th_c_005fout_005f16.setValue("${myfield_resid}");
/* 6825 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 6826 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 6827 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6828 */       return true;
/*      */     }
/* 6830 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6831 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6836 */     PageContext pageContext = _jspx_page_context;
/* 6837 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6839 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6840 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 6841 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6843 */     _jspx_th_c_005fout_005f17.setValue("${myfield_entity}");
/* 6844 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 6845 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 6846 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6847 */       return true;
/*      */     }
/* 6849 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6850 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6855 */     PageContext pageContext = _jspx_page_context;
/* 6856 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6858 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6859 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 6860 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6862 */     _jspx_th_c_005fout_005f18.setValue("${param.resourceid}");
/* 6863 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 6864 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 6865 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6866 */       return true;
/*      */     }
/* 6868 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6869 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6874 */     PageContext pageContext = _jspx_page_context;
/* 6875 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6877 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6878 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 6879 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6881 */     _jspx_th_c_005fout_005f19.setValue("${param.resourcename}");
/* 6882 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 6883 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 6884 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6885 */       return true;
/*      */     }
/* 6887 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6888 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6893 */     PageContext pageContext = _jspx_page_context;
/* 6894 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6896 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6897 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 6898 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6900 */     _jspx_th_c_005fout_005f20.setValue("${param.resourceid}");
/* 6901 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 6902 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 6903 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6904 */       return true;
/*      */     }
/* 6906 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6907 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6912 */     PageContext pageContext = _jspx_page_context;
/* 6913 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6915 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6916 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 6917 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6919 */     _jspx_th_c_005fout_005f21.setValue("${param.resourcename}");
/* 6920 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 6921 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 6922 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6923 */       return true;
/*      */     }
/* 6925 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6926 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6931 */     PageContext pageContext = _jspx_page_context;
/* 6932 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6934 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6935 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 6936 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 6938 */     _jspx_th_c_005fout_005f22.setValue("${param.resourceid}");
/* 6939 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 6940 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 6941 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6942 */       return true;
/*      */     }
/* 6944 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6945 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6950 */     PageContext pageContext = _jspx_page_context;
/* 6951 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6953 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6954 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 6955 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 6957 */     _jspx_th_c_005fout_005f23.setValue("${param.resourceid}");
/* 6958 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 6959 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 6960 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6961 */       return true;
/*      */     }
/* 6963 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6964 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6969 */     PageContext pageContext = _jspx_page_context;
/* 6970 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6972 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6973 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 6974 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 6976 */     _jspx_th_c_005fout_005f24.setValue("${resourceid}");
/* 6977 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 6978 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 6979 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6980 */       return true;
/*      */     }
/* 6982 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6983 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6988 */     PageContext pageContext = _jspx_page_context;
/* 6989 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6991 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6992 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 6993 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 6995 */     _jspx_th_c_005fout_005f25.setValue("${resourceid}");
/* 6996 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 6997 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 6998 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6999 */       return true;
/*      */     }
/* 7001 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 7002 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7007 */     PageContext pageContext = _jspx_page_context;
/* 7008 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7010 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7011 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 7012 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 7014 */     _jspx_th_c_005fout_005f26.setValue("${param.resourceid}");
/* 7015 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 7016 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 7017 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 7018 */       return true;
/*      */     }
/* 7020 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 7021 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7026 */     PageContext pageContext = _jspx_page_context;
/* 7027 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7029 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7030 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 7031 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 7033 */     _jspx_th_c_005fif_005f15.setTest("${ !empty param.haid && param.haid!='null' }");
/* 7034 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 7035 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 7037 */         out.write("&haid=");
/* 7038 */         if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 7039 */           return true;
/* 7040 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 7041 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7045 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 7046 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 7047 */       return true;
/*      */     }
/* 7049 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 7050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7055 */     PageContext pageContext = _jspx_page_context;
/* 7056 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7058 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7059 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 7060 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 7062 */     _jspx_th_c_005fout_005f27.setValue("${param.haid}");
/* 7063 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 7064 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 7065 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 7066 */       return true;
/*      */     }
/* 7068 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 7069 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_logic_005fnotEmpty_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7074 */     PageContext pageContext = _jspx_page_context;
/* 7075 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7077 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7078 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 7079 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f4);
/*      */     
/* 7081 */     _jspx_th_c_005fout_005f28.setValue("${param.resourceid}");
/* 7082 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 7083 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 7084 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 7085 */       return true;
/*      */     }
/* 7087 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 7088 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_logic_005fnotEmpty_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7093 */     PageContext pageContext = _jspx_page_context;
/* 7094 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7096 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7097 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 7098 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f4);
/*      */     
/* 7100 */     _jspx_th_c_005fout_005f29.setValue("${param.resourceid}");
/* 7101 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 7102 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 7103 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 7104 */       return true;
/*      */     }
/* 7106 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 7107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_logic_005fnotEmpty_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7112 */     PageContext pageContext = _jspx_page_context;
/* 7113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7115 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7116 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 7117 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f4);
/*      */     
/* 7119 */     _jspx_th_c_005fout_005f30.setValue("${param.resourceid}");
/* 7120 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 7121 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 7122 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 7123 */       return true;
/*      */     }
/* 7125 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 7126 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_logic_005fnotEmpty_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7131 */     PageContext pageContext = _jspx_page_context;
/* 7132 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7134 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7135 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 7136 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f4);
/*      */     
/* 7138 */     _jspx_th_c_005fout_005f31.setValue("${param.resourceid}");
/* 7139 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 7140 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 7141 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 7142 */       return true;
/*      */     }
/* 7144 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 7145 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_logic_005fnotEmpty_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7150 */     PageContext pageContext = _jspx_page_context;
/* 7151 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7153 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7154 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 7155 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f4);
/*      */     
/* 7157 */     _jspx_th_c_005fout_005f32.setValue("${param.resourceid}");
/* 7158 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 7159 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 7160 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 7161 */       return true;
/*      */     }
/* 7163 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 7164 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_logic_005fnotEmpty_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7169 */     PageContext pageContext = _jspx_page_context;
/* 7170 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7172 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7173 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 7174 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f4);
/*      */     
/* 7176 */     _jspx_th_c_005fout_005f33.setValue("${param.resourceid}");
/* 7177 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 7178 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 7179 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 7180 */       return true;
/*      */     }
/* 7182 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 7183 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_logic_005fnotEmpty_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7188 */     PageContext pageContext = _jspx_page_context;
/* 7189 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7191 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7192 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 7193 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f4);
/*      */     
/* 7195 */     _jspx_th_c_005fout_005f34.setValue("${param.resourceid}");
/* 7196 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 7197 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 7198 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 7199 */       return true;
/*      */     }
/* 7201 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 7202 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_logic_005fnotEmpty_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7207 */     PageContext pageContext = _jspx_page_context;
/* 7208 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7210 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7211 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 7212 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f4);
/*      */     
/* 7214 */     _jspx_th_c_005fout_005f35.setValue("${param.resourceid}");
/* 7215 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 7216 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 7217 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 7218 */       return true;
/*      */     }
/* 7220 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 7221 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\mssql\overview_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */