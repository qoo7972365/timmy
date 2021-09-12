/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
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
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ 
/*      */ public final class CreateVMwareHorizon_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 2184 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2185 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2186 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/* 2187 */     _jspx_dependants.put("/jsp/includes/MonitorDiscoveryStatus.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2210 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2230 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2237 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2239 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2240 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2242 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2243 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/* 2244 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2245 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2246 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2247 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2255 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2258 */     JspWriter out = null;
/* 2259 */     Object page = this;
/* 2260 */     JspWriter _jspx_out = null;
/* 2261 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2265 */       response.setContentType("text/html;charset=UTF-8");
/* 2266 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2268 */       _jspx_page_context = pageContext;
/* 2269 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2270 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2271 */       session = pageContext.getSession();
/* 2272 */       out = pageContext.getOut();
/* 2273 */       _jspx_out = out;
/*      */       
/* 2275 */       out.write("<!--$Id$-->\n");
/*      */       
/* 2277 */       request.setAttribute("HelpKey", "New Horizon");
/*      */       
/*      */ 
/* 2280 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2281 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2282 */       out.write("\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n\n");
/*      */       
/*      */ 
/* 2285 */       ArrayList dynamicSites = AlarmUtil.getSiteMonitorGroups();
/* 2286 */       if (dynamicSites != null)
/*      */       {
/* 2288 */         request.setAttribute("dynamicSites", dynamicSites);
/*      */       }
/* 2290 */       String isEditMode = "false";
/*      */       
/*      */ 
/* 2293 */       out.write("\n\n<script>\n\tvar isit360msp = 'false';\n\t");
/*      */       
/* 2295 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*      */ 
/* 2298 */         out.write("\n\tisit360msp = 'true';\n\t");
/*      */       }
/*      */       
/*      */ 
/* 2302 */       out.write("\n\t\nfunction loadSite()\n{\n\tdocument.forms[1].site.options.length=0;\n\tvar formCustomerId = document.forms[1].organization.value;\n\tvar siteName;\n\tvar siteId;\n\tvar customerId;\n\tvar rowCount=0;\n\tdocument.forms[1].site.options[rowCount++] = new Option('-Select Site-','-'); //No I18N\n\t");
/* 2303 */       if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*      */         return;
/* 2305 */       out.write("\n}\n\t\nfunction validateAndSubmit()\n{\n\t//alert(document.forms[1].vcHost.value);\n\tif(document.forms[1].displayname.value == '')\n\t{\n\t\talert('");
/* 2306 */       out.print(FormatUtil.getString("am.webclient.vcenter.config.displayname.alert"));
/* 2307 */       out.write("');\n\t\treturn;\n\t}\n\telse if(document.forms[1].vcHost.value == '')\n\t{\n\t\talert('");
/* 2308 */       out.print(FormatUtil.getString("am.webclient.vcenter.config.ip.alert"));
/* 2309 */       out.write("');\n\t\treturn;\n\t}\n\telse if(document.forms[1].username.value == '')\n\t{\n\t\talert('");
/* 2310 */       out.print(FormatUtil.getString("am.webclient.vmware.horizon.config.username.alert"));
/* 2311 */       out.write("');\n\t\treturn;\n\t}\n\telse if(document.forms[1].password.value == '')\n\t{\n\t\talert('");
/* 2312 */       out.print(FormatUtil.getString("am.webclient.vmware.horizon.config.password.alert"));
/* 2313 */       out.write("');\n\t\treturn;\n\t}\n\telse if((document.forms[1].pollInterval.value == '') || isNaN(document.forms[1].pollInterval.value) || (document.forms[1].pollInterval.value <= 0))\n\t{\n\t\talert('");
/* 2314 */       out.print(FormatUtil.getString("am.webclient.common.validpollinginterval.text"));
/* 2315 */       out.write("');\n\t\treturn;\n\t}\n\t//the below else if it360 msp\n\telse if((isit360msp == 'true') && (document.forms[1].organization.value== \"-\"))\n\t{\n\t\talert('");
/* 2316 */       out.print(FormatUtil.getString("it360.addnewmonitor.err.checkcustomer"));
/* 2317 */       out.write("');\n    \t\treturn;\n\t}\n\telse if((isit360msp == 'true') && (document.forms[1].site.value== \"-\"))\n\t{\n\t        alert(\"");
/* 2318 */       out.print(FormatUtil.getString("it360.addnewmonitor.err.checksite"));
/* 2319 */       out.write("\");\n    \t\treturn;\n\t}\n\telse\n\t{\n        ");
/*      */       
/* 2321 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/*      */ 
/* 2324 */         out.write("                                \n            if (document.forms[1].masSelectType[1].checked) {\n            \tvar selectedVal=document.forms[1].masGroupName.value;\n            \tif (selectedVal != null && selectedVal == \"-\") {\n                    alert('");
/* 2325 */         out.print(FormatUtil.getString("am.webclient.admin.add.monitor.select.masgroup.text"));
/* 2326 */         out.write("');\n                    document.forms[1].masGroupName.focus();\n                    return;\n                }                                \t\n            } else if (document.forms[1].masSelectType[2].checked) {\n            \tselectedVal=document.forms[1].selectedServer.value;\n            \tif (selectedVal != null && selectedVal == \"-\") {\n                    alert('");
/* 2327 */         out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.mas.text"));
/* 2328 */         out.write("');\n                    document.forms[1].selectedServer.focus();\n                    return;\n                }                                 \t\n            }\n        ");
/*      */       }
/*      */       
/*      */ 
/* 2332 */       out.write(" \t\t\t\n\tdocument.forms[1].submit();\n\t}\n}\nfunction onMyLoad(){\n\tif(");
/* 2333 */       out.print(EnterpriseUtil.isAdminServer());
/* 2334 */       out.write(") {\n\t\tvar radioObj = document.forms[1].masSelectType;\n\t\tif (radioObj !=null && radioObj != \"undefined\") {\n\t\t\tvar val='0';\n\t\t\tif (radioObj[1].checked) {\n\t\t\t\tval='1';\n\t\t\t} else if (radioObj[2].checked){\n\t\t\t\tval='2';\n\t\t\t}\n\t\t\tshowAsPerSelection(val);\n\t\t}\n\t}\n}\n</script>\n\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
/*      */       
/* 2336 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2337 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2338 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2340 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2341 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2342 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2344 */           out.write(32);
/*      */           
/* 2346 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2347 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2348 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2350 */           _jspx_th_tiles_005fput_005f0.setName("title");
/*      */           
/* 2352 */           _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.create.vmware.horizon.browsertitle"));
/* 2353 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2354 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2355 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */           }
/*      */           
/* 2358 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2359 */           out.write(10);
/* 2360 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2362 */           out.write(32);
/*      */           
/* 2364 */           PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2365 */           _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2366 */           _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2368 */           _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */           
/* 2370 */           _jspx_th_tiles_005fput_005f2.setType("string");
/* 2371 */           int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2372 */           if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2373 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2374 */               out = _jspx_page_context.pushBody();
/* 2375 */               _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2376 */               _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2379 */               out.write(32);
/* 2380 */               out.write(10);
/* 2381 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 2383 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2384 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2385 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2387 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 2389 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 2391 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 2393 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2394 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2395 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2396 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 2399 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2400 */               String available = null;
/* 2401 */               available = (String)_jspx_page_context.findAttribute("available");
/* 2402 */               out.write(10);
/*      */               
/* 2404 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2405 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2406 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2408 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 2410 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 2412 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 2414 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2415 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2416 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2417 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 2420 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2421 */               String unavailable = null;
/* 2422 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2423 */               out.write(10);
/*      */               
/* 2425 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2426 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2427 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2429 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 2431 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 2433 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 2435 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2436 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2437 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2438 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 2441 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2442 */               String unmanaged = null;
/* 2443 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2444 */               out.write(10);
/*      */               
/* 2446 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2447 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2448 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2450 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 2452 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 2454 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 2456 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2457 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2458 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2459 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 2462 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2463 */               String scheduled = null;
/* 2464 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2465 */               out.write(10);
/*      */               
/* 2467 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2468 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2469 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2471 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2473 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2475 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2477 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2478 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2479 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2480 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 2483 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2484 */               String critical = null;
/* 2485 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 2486 */               out.write(10);
/*      */               
/* 2488 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2489 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2490 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2492 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 2494 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 2496 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 2498 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2499 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2500 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2501 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 2504 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2505 */               String clear = null;
/* 2506 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 2507 */               out.write(10);
/*      */               
/* 2509 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2510 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2511 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2513 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 2515 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 2517 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 2519 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2520 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2521 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2522 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 2525 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2526 */               String warning = null;
/* 2527 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 2528 */               out.write(10);
/* 2529 */               out.write(10);
/*      */               
/* 2531 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2532 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 2534 */               out.write(10);
/* 2535 */               out.write(10);
/* 2536 */               out.write(10);
/* 2537 */               out.write(10);
/*      */               
/* 2539 */               if (EnterpriseUtil.isAdminServer())
/*      */               {
/* 2541 */                 out.write(10);
/* 2542 */                 out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n");
/*      */                 
/* 2544 */                 NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2545 */                 _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2546 */                 _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                 
/* 2548 */                 _jspx_th_logic_005fnotEmpty_005f0.setName("discoverystatus");
/* 2549 */                 int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2550 */                 if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                   for (;;) {
/* 2552 */                     out.write(10);
/*      */                     
/*      */ 
/* 2555 */                     if ((request.getAttribute("type") == null) || ((!request.getAttribute("type").equals("Script Monitor")) && ((request.getAttribute("basetype") == null) || ((request.getAttribute("basetype") != null) && (!request.getAttribute("basetype").equals("Script Monitor")))) && (!request.getAttribute("type").equals("QENGINE")) && (!request.getAttribute("type").equals("Web Service")) && (!request.getAttribute("type").equals("file")) && (!request.getAttribute("type").equals("directory")) && (!request.getAttribute("type").equals("File System Monitor")) && (!request.getAttribute("type").equals("Ping Monitor")) && (!request.getAttribute("type").equals("SAP-CCMS"))))
/*      */                     {
/*      */ 
/* 2558 */                       out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n  <tr>\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2559 */                       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2560 */                       out.write("</span> </td>\n    <td width=\"7%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2561 */                       out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 2562 */                       out.write("\n      </span> </td>\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2563 */                       out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 2564 */                       out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2565 */                       out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2566 */                       out.write("\n </span></td>\n  <tr>\n  ");
/*      */                       
/* 2568 */                       int failedNumber = 1;
/*      */                       
/* 2570 */                       out.write(10);
/*      */                       
/* 2572 */                       IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2573 */                       _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2574 */                       _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                       
/* 2576 */                       _jspx_th_logic_005fiterate_005f0.setName("discoverystatus");
/*      */                       
/* 2578 */                       _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                       
/* 2580 */                       _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */                       
/* 2582 */                       _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2583 */                       int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2584 */                       if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2585 */                         ArrayList row = null;
/* 2586 */                         Integer i = null;
/* 2587 */                         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2588 */                           out = _jspx_page_context.pushBody();
/* 2589 */                           _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2590 */                           _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                         }
/* 2592 */                         row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2593 */                         i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                         for (;;) {
/* 2595 */                           out.write("\n<tr>\n<td height=\"18\" class=\"bodytext\">");
/* 2596 */                           out.print(row.get(0));
/* 2597 */                           out.write("</td>\n<td height=\"18\" class=\"bodytext\">");
/* 2598 */                           out.print(row.get(1));
/* 2599 */                           out.write("</td>\n\n    <td height=\"18\" class=\"bodytext\">\n      ");
/*      */                           
/* 2601 */                           if ((row.get(2).equals("Success")) || (row.get(2).equals("Present")))
/*      */                           {
/* 2603 */                             request.setAttribute("isDiscoverySuccess", "true");
/*      */                             
/* 2605 */                             out.write("\n      <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2606 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2607 */                             out.write("\" align=\"absmiddle\">\n      ");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/* 2612 */                             request.setAttribute("isDiscoverySuccess", "false");
/*      */                             
/* 2614 */                             out.write("\n      <img alt=\"");
/* 2615 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.notiniatedimagetip.text"));
/* 2616 */                             out.write("\" src=\"/images/icon_monitor_failure.gif\" align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                           }
/*      */                           
/*      */ 
/* 2620 */                           out.write("\n      <span class=\"bodytextbold\">");
/* 2621 */                           out.print(FormatUtil.getString(String.valueOf(row.get(2))));
/* 2622 */                           out.write("</span> </td>\n\n      ");
/*      */                           
/* 2624 */                           pageContext.setAttribute("ret_msg", String.valueOf(row.get(3)));
/*      */                           
/* 2626 */                           out.write("\n                     ");
/*      */                           
/* 2628 */                           IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2629 */                           _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2630 */                           _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                           
/* 2632 */                           _jspx_th_c_005fif_005f3.setTest("${ret_msg=='Monitoring Initiated Succesfully'}");
/* 2633 */                           int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2634 */                           if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                             for (;;) {
/* 2636 */                               out.write("\n                           <td height=\"18\" class=\"bodytext\">");
/* 2637 */                               out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 2638 */                               out.write("\n                                 ");
/* 2639 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2640 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2644 */                           if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2645 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                           }
/*      */                           
/* 2648 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2649 */                           out.write("\n                                       ");
/*      */                           
/* 2651 */                           IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2652 */                           _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2653 */                           _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                           
/* 2655 */                           _jspx_th_c_005fif_005f4.setTest("${ret_msg!='Monitoring Initiated Succesfully'}");
/* 2656 */                           int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2657 */                           if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                             for (;;) {
/* 2659 */                               out.write("\n                                             <td height=\"18\" class=\"bodytext\">");
/* 2660 */                               out.print(row.get(3));
/* 2661 */                               out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                               
/* 2663 */                               if ((request.getAttribute("type") != null) && (!request.getAttribute("type").equals("All")) && (!request.getAttribute("type").equals("TELNET")) && (!request.getAttribute("type").equals("SNMP")) && (!request.getAttribute("type").equals("SERVICE")) && (!request.getAttribute("type").equals("JMX1.2-MX4J-RMI")) && (!request.getAttribute("type").equals("JDK1.5")) && (!request.getAttribute("type").equals("RMI")) && (!request.getAttribute("type").equals("PHP")) && (!request.getAttribute("type").equals("MAIL")) && (!request.getAttribute("type").equals("ORACLEDB")) && (!request.getAttribute("type").equals("MYSQLDB")) && (!request.getAttribute("type").equals("MSSQLDB")) && (!request.getAttribute("type").equals("DB2")) && (!request.getAttribute("type").equals("WTA")) && (!request.getAttribute("type").equals("SAP")))
/*      */                               {
/* 2665 */                                 if (((String)pageContext.getAttribute("ret_msg")).indexOf(FormatUtil.getString("am.webclient.discovery.host.monitoradded.text")) == -1)
/*      */                                 {
/* 2667 */                                   String fWhr = request.getParameter("hideFieldsForIT360");
/* 2668 */                                   if (fWhr == null)
/*      */                                   {
/* 2670 */                                     fWhr = (String)request.getAttribute("hideFieldsForIT360");
/*      */                                   }
/*      */                                   
/* 2673 */                                   if (((fWhr == null) || (!fWhr.equals("true"))) && 
/* 2674 */                                     (!request.getAttribute("type").equals("SYBASEDB")))
/*      */                                   {
/* 2676 */                                     out.write("\n\t\t\t\t\t\t\t\t\t\t\t &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"bodytext\" href=\"javascript:void(0)\" onClick=\"getAllDetailsOfHost()\">");
/* 2677 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.diagnose.link"));
/* 2678 */                                     out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                   }
/*      */                                 } }
/* 2681 */                               if ((request.getAttribute("showSupportMessage") != null) && (failedNumber == 1) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                               {
/* 2683 */                                 failedNumber++;
/*      */                                 
/*      */ 
/* 2686 */                                 out.write("\n\t\t\t\t\t\t\t\t\t\t<br>");
/* 2687 */                                 out.print(FormatUtil.getString("am.webclient.discovery.host.support.link", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.talkback.mailid"), com.adventnet.appmanager.util.OEMUtil.getOEMString("product.tollfree.number") }));
/* 2688 */                                 out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                               }
/* 2690 */                               out.write("\n                                                   ");
/* 2691 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2692 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2696 */                           if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2697 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                           }
/*      */                           
/* 2700 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2701 */                           out.write(10);
/* 2702 */                           out.write(10);
/* 2703 */                           out.write(10);
/*      */                           
/*      */ 
/* 2706 */                           if (row.size() > 4)
/*      */                           {
/*      */ 
/* 2709 */                             out.write("<br>\n");
/* 2710 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)row.get(4) }));
/* 2711 */                             out.write(10);
/*      */                           }
/*      */                           
/*      */ 
/* 2715 */                           out.write("\n</td>\n\n</tr>\n");
/* 2716 */                           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2717 */                           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2718 */                           i = (Integer)_jspx_page_context.findAttribute("i");
/* 2719 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2722 */                         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2723 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2726 */                       if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2727 */                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                       }
/*      */                       
/* 2730 */                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2731 */                       out.write("\n</table>\n");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/* 2736 */                       ArrayList al1 = (ArrayList)request.getAttribute("discoverystatus");
/*      */                       
/* 2738 */                       out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n<tr>\n");
/* 2739 */                       String mtype = (String)request.getAttribute("type");
/* 2740 */                       out.write(10);
/* 2741 */                       if (mtype.equals("File System Monitor")) {
/* 2742 */                         out.write("\n  <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2743 */                         out.print(FormatUtil.getString("File/Directory Name"));
/* 2744 */                         out.write("</span> </td>\n");
/* 2745 */                       } else if ((((String)request.getAttribute("type")).equals("Ping Monitor")) || (((String)request.getAttribute("type")).equals("SAP-CCMS"))) {
/* 2746 */                         out.write("\n\t<td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2747 */                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2748 */                         out.write("</span> </td>\n");
/*      */                       } else {
/* 2750 */                         out.write("\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2751 */                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 2752 */                         out.write("</span> </td>\n");
/*      */                       }
/* 2754 */                       out.write("\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2755 */                       out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 2756 */                       out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2757 */                       out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2758 */                       out.write("</span></td>\n  </tr>\n  <tr>\n   <td height=\"18\" class=\"bodytext\">");
/* 2759 */                       out.print(al1.get(0));
/* 2760 */                       out.write("</td>\n   <td height=\"18\" class=\"bodytext\">\n   ");
/*      */                       
/* 2762 */                       if (al1.get(1).equals("Success"))
/*      */                       {
/* 2764 */                         request.setAttribute("isDiscoverySuccess", "true");
/*      */                         
/* 2766 */                         out.write("\n   <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2767 */                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2768 */                         out.write("\" align=\"absmiddle\">\n    ");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/* 2773 */                         request.setAttribute("isDiscoverySuccess", "false");
/*      */                         
/*      */ 
/* 2776 */                         out.write("\n      <img  src=\"/images/icon_monitor_failure.gif\"  align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                       }
/*      */                       
/*      */ 
/* 2780 */                       out.write("\n<span class=\"bodytextbold\">");
/* 2781 */                       out.print(FormatUtil.getString(String.valueOf(al1.get(1))));
/* 2782 */                       out.write("</span> </td>\n");
/*      */                       
/* 2784 */                       if (al1.get(1).equals("Success"))
/*      */                       {
/* 2786 */                         boolean isAdminServer = EnterpriseUtil.isAdminServer();
/* 2787 */                         if (isAdminServer) {
/* 2788 */                           String masDisplayName = (String)al1.get(3);
/* 2789 */                           String format = FormatUtil.getString("am.webclient.admin.add.monitor.successfully.configured.text", new String[] { masDisplayName, "", (String)al1.get(2) });
/*      */                           
/* 2791 */                           out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\">");
/* 2792 */                           out.print(format);
/* 2793 */                           out.write("</td>\n");
/*      */                         }
/*      */                         else
/*      */                         {
/* 2797 */                           out.write("\t\t\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"> ");
/* 2798 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2799 */                           out.write("<br> ");
/* 2800 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)al1.get(2) }));
/* 2801 */                           out.write("</td>\n");
/*      */                         }
/*      */                         
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/* 2808 */                         out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"><span class=\"bodytext\">");
/* 2809 */                         out.print(al1.get(2));
/* 2810 */                         out.write("</span></td>\n");
/*      */                       }
/*      */                       
/*      */ 
/* 2814 */                       out.write("\n  </tr>\n</table>\n\n");
/*      */                     }
/*      */                     
/*      */ 
/* 2818 */                     out.write(10);
/* 2819 */                     int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2820 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2824 */                 if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2825 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                 }
/*      */                 
/* 2828 */                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2829 */                 out.write(10);
/* 2830 */                 out.write(10);
/*      */               }
/*      */               
/* 2833 */               if (EnterpriseUtil.isIt360MSPEdition()) {
/* 2834 */                 com.adventnet.appmanager.struts.form.AMActionForm form = (com.adventnet.appmanager.struts.form.AMActionForm)request.getAttribute("AMActionForm");
/*      */                 
/* 2836 */                 ArrayList orgs = AlarmUtil.getCustomerNames();
/*      */                 
/* 2838 */                 if (orgs != null)
/*      */                 {
/* 2840 */                   request.setAttribute("customers", orgs);
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/* 2845 */                 if (form != null)
/*      */                 {
/* 2847 */                   String customerName = form.getOrganization();
/* 2848 */                   if (customerName != null)
/*      */                   {
/* 2850 */                     ArrayList applications2 = AlarmUtil.getSiteMonitorGroups(customerName);
/* 2851 */                     if (applications2 != null)
/*      */                     {
/* 2853 */                       request.setAttribute("applications2", applications2);
/*      */                     }
/*      */                     
/*      */                   }
/*      */                 }
/*      */               }
/*      */               else
/*      */               {
/* 2861 */                 ArrayList applications = AlarmUtil.getConfiguredGroups();
/* 2862 */                 if (applications != null)
/*      */                 {
/* 2864 */                   request.setAttribute("applications", applications);
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/* 2869 */               out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t\t<td width=\"65%\" valign=\"top\">\n");
/*      */               
/* 2871 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 2872 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2873 */               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2875 */               _jspx_th_html_005fform_005f0.setAction("/admin/createapplication.do");
/* 2876 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2877 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                 for (;;) {
/* 2879 */                   out.write("\n <input type=\"hidden\" name=\"method\" value=\"configureHorizonDiscovery\">\n<input type=\"hidden\" name=\"type\" value=\"VMwareView\">\n<input type=\"hidden\" name=\"isEdit\" value=\"");
/* 2880 */                   out.print(request.getParameter("isEdit"));
/* 2881 */                   out.write("\">\n<input type=\"hidden\" name=\"haRESID\" value=\"");
/* 2882 */                   if (_jspx_meth_c_005fout_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2884 */                   out.write("\">\n\t");
/*      */                   
/* 2886 */                   String pollinterval_append = "value='5'";
/*      */                   
/* 2888 */                   out.write("\n\n\t      \t<table  cellpadding=\"0\" CELLSPACING=\"0\" class=\"lrtborder\" width=\"99%\">\n\t      \t<tr>\n\t    \t<TD class=\"tableheading-monitor-config\" height=\"30\" width=\"100%\">");
/* 2889 */                   out.print(FormatUtil.getString("am.webclient.vmware.horizon.configure.heading"));
/* 2890 */                   out.write("</TD>\n\t      \t</tr>\n\t      \t</table>\n\n<TABLE  class=\"lrborder\" width=\"99%\" BORDER=\"0\" cellpadding=\"5\" CELLSPACING=\"0\" >\n       <TR>\n        <TD height=\"30\" class=\"bodytext label-align addmonitor-label\" width=\"25%\"><label>&nbsp;");
/* 2891 */                   out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 2892 */                   out.write("<span class=\"mandatory\">*</span></label></TD>\n        <TD  width=\"75%\">\n\t");
/* 2893 */                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2895 */                   out.write("\n        </TD>\n      </TR>\n\n      <TR>\n        <TD height=\"30\" class=\"bodytext label-align addmonitor-label\" width=\"25%\"><label>&nbsp;");
/* 2896 */                   out.print(FormatUtil.getString("am.webclient.vmware.horizon.config.ip.text"));
/* 2897 */                   out.write("<span class=\"mandatory\">*</span></label></TD>\n        <TD  width=\"75%\"> ");
/* 2898 */                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2900 */                   out.write("\n        </TD>\n      </TR>\n      ");
/* 2901 */                   out.write("\n      <TR>\n        <TD height=\"30\"  class=\"bodytext label-align addmonitor-label\"><label>&nbsp;");
/* 2902 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 2903 */                   out.write("<span class=\"mandatory\">*</span></label></TD>\n        <TD  >");
/* 2904 */                   if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2906 */                   out.write("\n          &nbsp;<span class=\"bodytext\"></span> </TD>\n      </TR>\n      <TR>\n        <TD height=\"30\" class=\"bodytext label-align addmonitor-label\"><label>&nbsp;");
/* 2907 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/* 2908 */                   out.write("<span class=\"mandatory\">*</span></label></TD>\n        <TD   > ");
/* 2909 */                   if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2911 */                   out.write("\n        </TD>\n      </TR>\n      <TR>\n        <TD height=\"30\" class=\"bodytext label-align addmonitor-label\"><label>&nbsp;");
/* 2912 */                   out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 2913 */                   out.write("<span class=\"mandatory\">*</span></label></TD>\n        <TD   > ");
/* 2914 */                   if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2916 */                   out.write("<span class=\"bodytext\">&nbsp;");
/* 2917 */                   out.print(FormatUtil.getString("am.webclient.urlmonitor.unitofpoll.text"));
/* 2918 */                   out.write("</span></td>\n        </TD>\n      </TR>\n       <tr><td colspan=\"2\" style=\"height:10px;\"></td></tr>\n      ");
/* 2919 */                   if (EnterpriseUtil.isIt360MSPEdition())
/*      */                   {
/* 2921 */                     if (request.getParameter("isEdit") != null)
/*      */                     {
/* 2923 */                       isEditMode = request.getParameter("isEdit");
/*      */                     }
/*      */                     
/* 2926 */                     out.write("\n \t <TR>\n   \t <TD height=\"30\" class=\"bodytext\">");
/* 2927 */                     out.print(FormatUtil.getString("it360.sp.customermgmt.customer.txt", new String[] { MGSTR }));
/* 2928 */                     out.write("</TD>\n   \t <TD>\n\t\t \n\t\t\t");
/*      */                     
/* 2930 */                     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 2931 */                     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2932 */                     _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 2934 */                     _jspx_th_html_005fselect_005f0.setProperty("organization");
/*      */                     
/* 2936 */                     _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */                     
/* 2938 */                     _jspx_th_html_005fselect_005f0.setOnchange("javascript:loadSite()");
/* 2939 */                     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2940 */                     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2941 */                       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2942 */                         out = _jspx_page_context.pushBody();
/* 2943 */                         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2944 */                         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/* 2947 */                         out.write("\n\t\t\t");
/*      */                         
/* 2949 */                         OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2950 */                         _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2951 */                         _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                         
/* 2953 */                         _jspx_th_html_005foption_005f0.setValue("-");
/* 2954 */                         int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2955 */                         if (_jspx_eval_html_005foption_005f0 != 0) {
/* 2956 */                           if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2957 */                             out = _jspx_page_context.pushBody();
/* 2958 */                             _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 2959 */                             _jspx_th_html_005foption_005f0.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2962 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.customer", new String[] { MGSTR }));
/* 2963 */                             int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 2964 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2967 */                           if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2968 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2971 */                         if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2972 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                         }
/*      */                         
/* 2975 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 2976 */                         out.write("\n\t      \t\t");
/*      */                         
/* 2978 */                         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2979 */                         _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2980 */                         _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                         
/* 2982 */                         _jspx_th_logic_005fnotEmpty_005f1.setName("customers");
/* 2983 */                         int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2984 */                         if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                           for (;;) {
/* 2986 */                             out.write(32);
/*      */                             
/* 2988 */                             IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2989 */                             _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 2990 */                             _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                             
/* 2992 */                             _jspx_th_logic_005fiterate_005f1.setName("customers");
/*      */                             
/* 2994 */                             _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                             
/* 2996 */                             _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                             
/* 2998 */                             _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/* 2999 */                             int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 3000 */                             if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 3001 */                               ArrayList row = null;
/* 3002 */                               Integer j = null;
/* 3003 */                               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3004 */                                 out = _jspx_page_context.pushBody();
/* 3005 */                                 _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 3006 */                                 _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                               }
/* 3008 */                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3009 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                               for (;;) {
/* 3011 */                                 out.write("\n\t      \t\t");
/*      */                                 
/* 3013 */                                 OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3014 */                                 _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3015 */                                 _jspx_th_html_005foption_005f1.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                 
/* 3017 */                                 _jspx_th_html_005foption_005f1.setValue((String)row.get(1));
/* 3018 */                                 int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3019 */                                 if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3020 */                                   if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3021 */                                     out = _jspx_page_context.pushBody();
/* 3022 */                                     _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3023 */                                     _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3026 */                                     out.print(row.get(0));
/* 3027 */                                     int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3028 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3031 */                                   if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3032 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3035 */                                 if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3036 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                 }
/*      */                                 
/* 3039 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 3040 */                                 out.write("\n\t\t\t");
/* 3041 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 3042 */                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3043 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/* 3044 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3047 */                               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3048 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3051 */                             if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 3052 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                             }
/*      */                             
/* 3055 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 3056 */                             out.write(32);
/* 3057 */                             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3058 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3062 */                         if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3063 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                         }
/*      */                         
/* 3066 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3067 */                         out.write(" \n\t\t\t");
/* 3068 */                         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 3069 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 3072 */                       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3073 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 3076 */                     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 3077 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                     }
/*      */                     
/* 3080 */                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 3081 */                     out.write("\n\t\t\t\n\t\n\t\t  \n\t </TD>\n </TR> \n\t <TR>\n\t\t <TD height=\"30\" class=\"bodytext\">");
/* 3082 */                     out.print(FormatUtil.getString("it360.sp.customermgmt.site.txt", new String[] { MGSTR }));
/* 3083 */                     out.write("</TD>\n\t\t\t <TD>\n\t\t\t\t\t");
/*      */                     
/* 3085 */                     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3086 */                     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 3087 */                     _jspx_th_html_005fselect_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 3089 */                     _jspx_th_html_005fselect_005f1.setProperty("site");
/*      */                     
/* 3091 */                     _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/* 3092 */                     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 3093 */                     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 3094 */                       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3095 */                         out = _jspx_page_context.pushBody();
/* 3096 */                         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 3097 */                         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/* 3100 */                         out.write("\n\t\t\t\t\t      ");
/*      */                         
/* 3102 */                         OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3103 */                         _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 3104 */                         _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f1);
/*      */                         
/* 3106 */                         _jspx_th_html_005foption_005f2.setValue("-");
/* 3107 */                         int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 3108 */                         if (_jspx_eval_html_005foption_005f2 != 0) {
/* 3109 */                           if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3110 */                             out = _jspx_page_context.pushBody();
/* 3111 */                             _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 3112 */                             _jspx_th_html_005foption_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 3115 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.siteGroup", new String[] { MGSTR }));
/* 3116 */                             int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 3117 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3120 */                           if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3121 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3124 */                         if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 3125 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                         }
/*      */                         
/* 3128 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 3129 */                         out.write("\n\t\t\t\t\t      ");
/*      */                         
/* 3131 */                         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3132 */                         _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 3133 */                         _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_html_005fselect_005f1);
/*      */                         
/* 3135 */                         _jspx_th_logic_005fnotEmpty_005f2.setName("applications2");
/* 3136 */                         int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 3137 */                         if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                           for (;;) {
/* 3139 */                             out.write(32);
/*      */                             
/* 3141 */                             IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3142 */                             _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 3143 */                             _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                             
/* 3145 */                             _jspx_th_logic_005fiterate_005f2.setName("applications2");
/*      */                             
/* 3147 */                             _jspx_th_logic_005fiterate_005f2.setId("row");
/*      */                             
/* 3149 */                             _jspx_th_logic_005fiterate_005f2.setIndexId("j");
/*      */                             
/* 3151 */                             _jspx_th_logic_005fiterate_005f2.setType("java.util.ArrayList");
/* 3152 */                             int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 3153 */                             if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 3154 */                               ArrayList row = null;
/* 3155 */                               Integer j = null;
/* 3156 */                               if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 3157 */                                 out = _jspx_page_context.pushBody();
/* 3158 */                                 _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 3159 */                                 _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                               }
/* 3161 */                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3162 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                               for (;;) {
/* 3164 */                                 out.write("\n\t\t\t\t\t      ");
/*      */                                 
/* 3166 */                                 OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3167 */                                 _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 3168 */                                 _jspx_th_html_005foption_005f3.setParent(_jspx_th_logic_005fiterate_005f2);
/*      */                                 
/* 3170 */                                 _jspx_th_html_005foption_005f3.setValue((String)row.get(1));
/* 3171 */                                 int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 3172 */                                 if (_jspx_eval_html_005foption_005f3 != 0) {
/* 3173 */                                   if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3174 */                                     out = _jspx_page_context.pushBody();
/* 3175 */                                     _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 3176 */                                     _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3179 */                                     out.print(row.get(0));
/* 3180 */                                     int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 3181 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3184 */                                   if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3185 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3188 */                                 if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 3189 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                 }
/*      */                                 
/* 3192 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 3193 */                                 out.write("\n\t\t\t\t\t      ");
/* 3194 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 3195 */                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3196 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/* 3197 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3200 */                               if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 3201 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3204 */                             if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 3205 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                             }
/*      */                             
/* 3208 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 3209 */                             out.write(32);
/* 3210 */                             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 3211 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3215 */                         if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 3216 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                         }
/*      */                         
/* 3219 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 3220 */                         out.write(" \n\t\t\t\t\t");
/* 3221 */                         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 3222 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 3225 */                       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3226 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 3229 */                     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 3230 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                     }
/*      */                     
/* 3233 */                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 3234 */                     out.write("\n\t      \t\t</TD>\n\t      </TR> \n \n\n\t");
/*      */                   }
/* 3236 */                   out.write("\n\n\t<TR>\n\n\t</TABLE>\n    ");
/*      */                   
/* 3238 */                   if (EnterpriseUtil.isAdminServer())
/*      */                   {
/*      */ 
/* 3241 */                     out.write(10);
/* 3242 */                     out.write(9);
/* 3243 */                     out.write(9);
/* 3244 */                     JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("managedServerGroups", request.getCharacterEncoding()), out, false);
/* 3245 */                     out.write(9);
/* 3246 */                     out.write(10);
/*      */                   }
/*      */                   
/*      */ 
/* 3250 */                   out.write("\n\t<TABLE  class=\"lrbborder\" width=\"99%\" BORDER=\"0\" cellpadding=\"5\" CELLSPACING=\"0\" >\n\t<TR>\t\n\t<TD class=\"tablebottom\" width=\"25%\">&nbsp;</td>\n    \t<TD class=\"tablebottom\" align=\"left\">\n\n      \t<input name=\"button1\" class=\"buttons btn_highlt\" value='");
/* 3251 */                   out.print(FormatUtil.getString("am.webclient.vmware.horizon.config.button.text"));
/* 3252 */                   out.write("' onClick=\"validateAndSubmit();\" type=\"button\">\n      \t&nbsp; <input value='");
/* 3253 */                   out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3254 */                   out.write("' onClick=\"history.back();\" class=\"buttons btn_link\" id=\"cancelButtonMod\" type=\"button\">\n    \t</TD>\n\t</TR>\n\n </TABLE>\n</td>\n<td width=\"30%\" valign=\"top\">\n");
/*      */                   
/* 3256 */                   StringBuffer helpcardKey = new StringBuffer();
/* 3257 */                   String hideStyle = "";
/* 3258 */                   helpcardKey.append(FormatUtil.getString("am.vmware.horizon.helpcard.text", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") }));
/*      */                   
/*      */ 
/* 3261 */                   out.write(10);
/* 3262 */                   JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(helpcardKey.toString()), request.getCharacterEncoding()), out, false);
/* 3263 */                   out.write(10);
/* 3264 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3265 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3269 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3270 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */               }
/*      */               
/* 3273 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3274 */               out.write("\n</td>\n        </tr>\n        </table>\n");
/* 3275 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3276 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3279 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3280 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3283 */           if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3284 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */           }
/*      */           
/* 3287 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3288 */           if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 3290 */           out.write(32);
/* 3291 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3292 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3296 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3297 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 3300 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3301 */         out.write("\n<script>\n\t$(document.body).ready(function(){\n\t\t");
/* 3302 */         if (isEditMode.equals("true")) {
/* 3303 */           out.write("\n\t\t\n\t\t\tdocument.forms[1].organization.disabled = true;\n\t\t\t\tdocument.forms[1].site.disabled = true;\n\t\t\t\n\t\t\t\t\n\t\t");
/*      */         }
/* 3305 */         out.write("\n\t});\n\tonMyLoad();\n</script>");
/*      */       }
/* 3307 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3308 */         out = _jspx_out;
/* 3309 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3310 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3311 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3314 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3320 */     PageContext pageContext = _jspx_page_context;
/* 3321 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3323 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3324 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3325 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */     
/* 3327 */     _jspx_th_c_005fforEach_005f0.setItems("${dynamicSites}");
/*      */     
/* 3329 */     _jspx_th_c_005fforEach_005f0.setVar("a");
/*      */     
/* 3331 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowCounter");
/* 3332 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 3334 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3335 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 3337 */           out.write(10);
/* 3338 */           out.write(9);
/* 3339 */           out.write(9);
/* 3340 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3341 */             return true;
/* 3342 */           out.write("\n\t\tif(formCustomerId == customerId)\n\t\t{\n\t\t\tdocument.forms[1].site.options[rowCount++] = new Option(siteName,siteId);\n\t\t}\n\t");
/* 3343 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3344 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3348 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 3349 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3352 */         int tmp206_205 = 0; int[] tmp206_203 = _jspx_push_body_count_c_005fforEach_005f0; int tmp208_207 = tmp206_203[tmp206_205];tmp206_203[tmp206_205] = (tmp208_207 - 1); if (tmp208_207 <= 0) break;
/* 3353 */         out = _jspx_page_context.popBody(); }
/* 3354 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3356 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3357 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 3359 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3364 */     PageContext pageContext = _jspx_page_context;
/* 3365 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3367 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3368 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3369 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3371 */     _jspx_th_c_005fforEach_005f1.setItems("${a}");
/*      */     
/* 3373 */     _jspx_th_c_005fforEach_005f1.setVar("b");
/*      */     
/* 3375 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowCounter1");
/* 3376 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 3378 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3379 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 3381 */           out.write("\n\t\t\t");
/* 3382 */           boolean bool; if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3383 */             return true;
/* 3384 */           out.write("\n\t\t\t");
/* 3385 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3386 */             return true;
/* 3387 */           out.write("\n\t\t\t");
/* 3388 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3389 */             return true;
/* 3390 */           out.write(10);
/* 3391 */           out.write(9);
/* 3392 */           out.write(9);
/* 3393 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3394 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3398 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 3399 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3402 */         int tmp295_294 = 0; int[] tmp295_292 = _jspx_push_body_count_c_005fforEach_005f1; int tmp297_296 = tmp295_292[tmp295_294];tmp295_292[tmp295_294] = (tmp297_296 - 1); if (tmp297_296 <= 0) break;
/* 3403 */         out = _jspx_page_context.popBody(); }
/* 3404 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 3406 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 3407 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 3409 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3414 */     PageContext pageContext = _jspx_page_context;
/* 3415 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3417 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3418 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3419 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3421 */     _jspx_th_c_005fif_005f0.setTest("${rowCounter1.count == 1}");
/* 3422 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3423 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3425 */         out.write("\n\t\t\t\tsiteName = '");
/* 3426 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3427 */           return true;
/* 3428 */         out.write("';\n\t\t\t");
/* 3429 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3430 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3434 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3435 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3436 */       return true;
/*      */     }
/* 3438 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3439 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3444 */     PageContext pageContext = _jspx_page_context;
/* 3445 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3447 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3448 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3449 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3451 */     _jspx_th_c_005fout_005f0.setValue("${b}");
/* 3452 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3453 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3454 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3455 */       return true;
/*      */     }
/* 3457 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3458 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3463 */     PageContext pageContext = _jspx_page_context;
/* 3464 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3466 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3467 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3468 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3470 */     _jspx_th_c_005fif_005f1.setTest("${rowCounter1.count == 2}");
/* 3471 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3472 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3474 */         out.write("\n\t\t\t\tsiteId = '");
/* 3475 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3476 */           return true;
/* 3477 */         out.write("';\n\t\t\t");
/* 3478 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3479 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3483 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3484 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3485 */       return true;
/*      */     }
/* 3487 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3488 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3493 */     PageContext pageContext = _jspx_page_context;
/* 3494 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3496 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3497 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3498 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3500 */     _jspx_th_c_005fout_005f1.setValue("${b}");
/* 3501 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3502 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3503 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3504 */       return true;
/*      */     }
/* 3506 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3507 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3512 */     PageContext pageContext = _jspx_page_context;
/* 3513 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3515 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3516 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3517 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3519 */     _jspx_th_c_005fif_005f2.setTest("${rowCounter1.count == 3}");
/* 3520 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3521 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3523 */         out.write("\n\t\t\t\tcustomerId = '");
/* 3524 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3525 */           return true;
/* 3526 */         out.write("';\n\t\t\t");
/* 3527 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3528 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3532 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3533 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3534 */       return true;
/*      */     }
/* 3536 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3537 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3542 */     PageContext pageContext = _jspx_page_context;
/* 3543 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3545 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3546 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3547 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3549 */     _jspx_th_c_005fout_005f2.setValue("${b}");
/* 3550 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3551 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3552 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3553 */       return true;
/*      */     }
/* 3555 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3556 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3561 */     PageContext pageContext = _jspx_page_context;
/* 3562 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3564 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3565 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3566 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3568 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3570 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 3571 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3572 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3573 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3574 */       return true;
/*      */     }
/* 3576 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3577 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3582 */     PageContext pageContext = _jspx_page_context;
/* 3583 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3585 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3586 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3587 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3589 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 3590 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3591 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3592 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3593 */       return true;
/*      */     }
/* 3595 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3596 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3601 */     PageContext pageContext = _jspx_page_context;
/* 3602 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3604 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 3605 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3606 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3608 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 3610 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/* 3611 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3612 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3613 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3614 */       return true;
/*      */     }
/* 3616 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3617 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3622 */     PageContext pageContext = _jspx_page_context;
/* 3623 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3625 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 3626 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3627 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3629 */     _jspx_th_html_005ftext_005f1.setProperty("vcHost");
/*      */     
/* 3631 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext default");
/* 3632 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 3633 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 3634 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3635 */       return true;
/*      */     }
/* 3637 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3638 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3643 */     PageContext pageContext = _jspx_page_context;
/* 3644 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3646 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 3647 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 3648 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3650 */     _jspx_th_html_005ftext_005f2.setProperty("username");
/*      */     
/* 3652 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext default");
/* 3653 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 3654 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 3655 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3656 */       return true;
/*      */     }
/* 3658 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3659 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3664 */     PageContext pageContext = _jspx_page_context;
/* 3665 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3667 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/* 3668 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 3669 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3671 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 3673 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext default");
/* 3674 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 3675 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 3676 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 3677 */       return true;
/*      */     }
/* 3679 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 3680 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3685 */     PageContext pageContext = _jspx_page_context;
/* 3686 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3688 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 3689 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 3690 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3692 */     _jspx_th_html_005ftext_005f3.setProperty("pollInterval");
/*      */     
/* 3694 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext small");
/* 3695 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 3696 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 3697 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 3698 */       return true;
/*      */     }
/* 3700 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 3701 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3706 */     PageContext pageContext = _jspx_page_context;
/* 3707 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3709 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3710 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3711 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3713 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 3715 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 3716 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3717 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3718 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3719 */       return true;
/*      */     }
/* 3721 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3722 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\CreateVMwareHorizon_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */