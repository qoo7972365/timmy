/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.EnterpriseAdminLink;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.net.InetAddress;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
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
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParamTag;
/*      */ 
/*      */ public final class DiagnosticDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   51 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   54 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   55 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   56 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   63 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   68 */     ArrayList list = null;
/*   69 */     StringBuffer sbf = new StringBuffer();
/*   70 */     ManagedApplication mo = new ManagedApplication();
/*   71 */     if (distinct)
/*      */     {
/*   73 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   77 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   80 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   82 */       ArrayList row = (ArrayList)list.get(i);
/*   83 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   84 */       if (distinct) {
/*   85 */         sbf.append(row.get(0));
/*      */       } else
/*   87 */         sbf.append(row.get(1));
/*   88 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   91 */     return sbf.toString(); }
/*      */   
/*   93 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   96 */     if (severity == null)
/*      */     {
/*   98 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  100 */     if (severity.equals("5"))
/*      */     {
/*  102 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  104 */     if (severity.equals("1"))
/*      */     {
/*  106 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  111 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  118 */     if (severity == null)
/*      */     {
/*  120 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  122 */     if (severity.equals("1"))
/*      */     {
/*  124 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  126 */     if (severity.equals("4"))
/*      */     {
/*  128 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  130 */     if (severity.equals("5"))
/*      */     {
/*  132 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  137 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  143 */     if (severity == null)
/*      */     {
/*  145 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  147 */     if (severity.equals("5"))
/*      */     {
/*  149 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  151 */     if (severity.equals("1"))
/*      */     {
/*  153 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  157 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  163 */     if (severity == null)
/*      */     {
/*  165 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  167 */     if (severity.equals("1"))
/*      */     {
/*  169 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  171 */     if (severity.equals("4"))
/*      */     {
/*  173 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  175 */     if (severity.equals("5"))
/*      */     {
/*  177 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  181 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  187 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  193 */     if (severity == 5)
/*      */     {
/*  195 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  197 */     if (severity == 1)
/*      */     {
/*  199 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  204 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  210 */     if (severity == null)
/*      */     {
/*  212 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  214 */     if (severity.equals("5"))
/*      */     {
/*  216 */       if (isAvailability) {
/*  217 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  220 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  223 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  225 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  227 */     if (severity.equals("1"))
/*      */     {
/*  229 */       if (isAvailability) {
/*  230 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  233 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  240 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  247 */     if (severity == null)
/*      */     {
/*  249 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  251 */     if (severity.equals("5"))
/*      */     {
/*  253 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  255 */     if (severity.equals("4"))
/*      */     {
/*  257 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  259 */     if (severity.equals("1"))
/*      */     {
/*  261 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  266 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  272 */     if (severity == null)
/*      */     {
/*  274 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  276 */     if (severity.equals("5"))
/*      */     {
/*  278 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  280 */     if (severity.equals("4"))
/*      */     {
/*  282 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  284 */     if (severity.equals("1"))
/*      */     {
/*  286 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  291 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  298 */     if (severity == null)
/*      */     {
/*  300 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  302 */     if (severity.equals("5"))
/*      */     {
/*  304 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  306 */     if (severity.equals("4"))
/*      */     {
/*  308 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  310 */     if (severity.equals("1"))
/*      */     {
/*  312 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  317 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  325 */     StringBuffer out = new StringBuffer();
/*  326 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  327 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  328 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  329 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  330 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  331 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  332 */     out.append("</tr>");
/*  333 */     out.append("</form></table>");
/*  334 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  341 */     if (val == null)
/*      */     {
/*  343 */       return "-";
/*      */     }
/*      */     
/*  346 */     String ret = FormatUtil.formatNumber(val);
/*  347 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  348 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  351 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  355 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  363 */     StringBuffer out = new StringBuffer();
/*  364 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  365 */     out.append("<tr>");
/*  366 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  368 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  370 */     out.append("</tr>");
/*  371 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  375 */       if (j % 2 == 0)
/*      */       {
/*  377 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  381 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  384 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  386 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  389 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  393 */       out.append("</tr>");
/*      */     }
/*  395 */     out.append("</table>");
/*  396 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  397 */     out.append("<tr>");
/*  398 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  399 */     out.append("</tr>");
/*  400 */     out.append("</table>");
/*  401 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  407 */     StringBuffer out = new StringBuffer();
/*  408 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  409 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  410 */     out.append("<tr>");
/*  411 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  412 */     out.append("<tr>");
/*  413 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  414 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  415 */     out.append("</tr>");
/*  416 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  419 */       out.append("<tr>");
/*  420 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  421 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  422 */       out.append("</tr>");
/*      */     }
/*      */     
/*  425 */     out.append("</table>");
/*  426 */     out.append("</table>");
/*  427 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  432 */     if (severity.equals("0"))
/*      */     {
/*  434 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  438 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  445 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  458 */     StringBuffer out = new StringBuffer();
/*  459 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  460 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  462 */       out.append("<tr>");
/*  463 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  464 */       out.append("</tr>");
/*      */       
/*      */ 
/*  467 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  469 */         String borderclass = "";
/*      */         
/*      */ 
/*  472 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  474 */         out.append("<tr>");
/*      */         
/*  476 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  477 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  478 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  484 */     out.append("</table><br>");
/*  485 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  486 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  488 */       List sLinks = secondLevelOfLinks[0];
/*  489 */       List sText = secondLevelOfLinks[1];
/*  490 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  493 */         out.append("<tr>");
/*  494 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  495 */         out.append("</tr>");
/*  496 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  498 */           String borderclass = "";
/*      */           
/*      */ 
/*  501 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  503 */           out.append("<tr>");
/*      */           
/*  505 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  506 */           if (sLinks.get(i).toString().length() == 0) {
/*  507 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  510 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  512 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  516 */     out.append("</table>");
/*  517 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  524 */     StringBuffer out = new StringBuffer();
/*  525 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  526 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  528 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  530 */         out.append("<tr>");
/*  531 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  532 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  536 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  538 */           String borderclass = "";
/*      */           
/*      */ 
/*  541 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  543 */           out.append("<tr>");
/*      */           
/*  545 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  546 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  547 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  550 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  553 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  558 */     out.append("</table><br>");
/*  559 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  560 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  562 */       List sLinks = secondLevelOfLinks[0];
/*  563 */       List sText = secondLevelOfLinks[1];
/*  564 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  567 */         out.append("<tr>");
/*  568 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  569 */         out.append("</tr>");
/*  570 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  572 */           String borderclass = "";
/*      */           
/*      */ 
/*  575 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  577 */           out.append("<tr>");
/*      */           
/*  579 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  580 */           if (sLinks.get(i).toString().length() == 0) {
/*  581 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  584 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  586 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  590 */     out.append("</table>");
/*  591 */     return out.toString();
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
/*  604 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  607 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  610 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  613 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  616 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  619 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  622 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  625 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  633 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  638 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  643 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  648 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  653 */     if (val != null)
/*      */     {
/*  655 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  659 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  664 */     if (val == null) {
/*  665 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  669 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  674 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  680 */     if (val != null)
/*      */     {
/*  682 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  686 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  692 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  697 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  701 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  706 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  711 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  716 */     String hostaddress = "";
/*  717 */     String ip = request.getHeader("x-forwarded-for");
/*  718 */     if (ip == null)
/*  719 */       ip = request.getRemoteAddr();
/*  720 */     InetAddress add = null;
/*  721 */     if (ip.equals("127.0.0.1")) {
/*  722 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  726 */       add = InetAddress.getByName(ip);
/*      */     }
/*  728 */     hostaddress = add.getHostName();
/*  729 */     if (hostaddress.indexOf('.') != -1) {
/*  730 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  731 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  735 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  740 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  746 */     if (severity == null)
/*      */     {
/*  748 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  750 */     if (severity.equals("5"))
/*      */     {
/*  752 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  754 */     if (severity.equals("1"))
/*      */     {
/*  756 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  761 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  766 */     ResultSet set = null;
/*  767 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  768 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  770 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  771 */       if (set.next()) { String str1;
/*  772 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  773 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  776 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  781 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  784 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  786 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  790 */     StringBuffer rca = new StringBuffer();
/*  791 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  792 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  795 */     int rcalength = key.length();
/*  796 */     String split = "6. ";
/*  797 */     int splitPresent = key.indexOf(split);
/*  798 */     String div1 = "";String div2 = "";
/*  799 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  801 */       if (rcalength > 180) {
/*  802 */         rca.append("<span class=\"rca-critical-text\">");
/*  803 */         getRCATrimmedText(key, rca);
/*  804 */         rca.append("</span>");
/*      */       } else {
/*  806 */         rca.append("<span class=\"rca-critical-text\">");
/*  807 */         rca.append(key);
/*  808 */         rca.append("</span>");
/*      */       }
/*  810 */       return rca.toString();
/*      */     }
/*  812 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  813 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  814 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  815 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  816 */     getRCATrimmedText(div1, rca);
/*  817 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  820 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  821 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  822 */     getRCATrimmedText(div2, rca);
/*  823 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  825 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  830 */     String[] st = msg.split("<br>");
/*  831 */     for (int i = 0; i < st.length; i++) {
/*  832 */       String s = st[i];
/*  833 */       if (s.length() > 180) {
/*  834 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  836 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  840 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  841 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  843 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  847 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  848 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  849 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  852 */       if (key == null) {
/*  853 */         return ret;
/*      */       }
/*      */       
/*  856 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  857 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  860 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  861 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  862 */       set = AMConnectionPool.executeQueryStmt(query);
/*  863 */       if (set.next())
/*      */       {
/*  865 */         String helpLink = set.getString("LINK");
/*  866 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  869 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  875 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  894 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  885 */         if (set != null) {
/*  886 */           AMConnectionPool.closeStatement(set);
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
/*  900 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  901 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  903 */       String entityStr = (String)keys.nextElement();
/*  904 */       String mmessage = temp.getProperty(entityStr);
/*  905 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  906 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  908 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  914 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  915 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  917 */       String entityStr = (String)keys.nextElement();
/*  918 */       String mmessage = temp.getProperty(entityStr);
/*  919 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  920 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  922 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  927 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  937 */     String des = new String();
/*  938 */     while (str.indexOf(find) != -1) {
/*  939 */       des = des + str.substring(0, str.indexOf(find));
/*  940 */       des = des + replace;
/*  941 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  943 */     des = des + str;
/*  944 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  951 */       if (alert == null)
/*      */       {
/*  953 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  955 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  957 */         return "&nbsp;";
/*      */       }
/*      */       
/*  960 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  962 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  965 */       int rcalength = test.length();
/*  966 */       if (rcalength < 300)
/*      */       {
/*  968 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  972 */       StringBuffer out = new StringBuffer();
/*  973 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  974 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  975 */       out.append("</div>");
/*  976 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  977 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  978 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  983 */       ex.printStackTrace();
/*      */     }
/*  985 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  991 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  996 */     ArrayList attribIDs = new ArrayList();
/*  997 */     ArrayList resIDs = new ArrayList();
/*  998 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1000 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1002 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1004 */       String resourceid = "";
/* 1005 */       String resourceType = "";
/* 1006 */       if (type == 2) {
/* 1007 */         resourceid = (String)row.get(0);
/* 1008 */         resourceType = (String)row.get(3);
/*      */       }
/* 1010 */       else if (type == 3) {
/* 1011 */         resourceid = (String)row.get(0);
/* 1012 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1015 */         resourceid = (String)row.get(6);
/* 1016 */         resourceType = (String)row.get(7);
/*      */       }
/* 1018 */       resIDs.add(resourceid);
/* 1019 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1020 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1022 */       String healthentity = null;
/* 1023 */       String availentity = null;
/* 1024 */       if (healthid != null) {
/* 1025 */         healthentity = resourceid + "_" + healthid;
/* 1026 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1029 */       if (availid != null) {
/* 1030 */         availentity = resourceid + "_" + availid;
/* 1031 */         entitylist.add(availentity);
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
/* 1045 */     Properties alert = getStatus(entitylist);
/* 1046 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1051 */     int size = monitorList.size();
/*      */     
/* 1053 */     String[] severity = new String[size];
/*      */     
/* 1055 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1057 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1058 */       String resourceName1 = (String)row1.get(7);
/* 1059 */       String resourceid1 = (String)row1.get(6);
/* 1060 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1061 */       if (severity[j] == null)
/*      */       {
/* 1063 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1067 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1069 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1071 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1074 */         if (sev > 0) {
/* 1075 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1076 */           monitorList.set(k, monitorList.get(j));
/* 1077 */           monitorList.set(j, t);
/* 1078 */           String temp = severity[k];
/* 1079 */           severity[k] = severity[j];
/* 1080 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1086 */     int z = 0;
/* 1087 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1090 */       int i = 0;
/* 1091 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1094 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1098 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1102 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1104 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1107 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1111 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1114 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1115 */       String resourceName1 = (String)row1.get(7);
/* 1116 */       String resourceid1 = (String)row1.get(6);
/* 1117 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1118 */       if (hseverity[j] == null)
/*      */       {
/* 1120 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1125 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1127 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1130 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1133 */         if (hsev > 0) {
/* 1134 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1135 */           monitorList.set(k, monitorList.get(j));
/* 1136 */           monitorList.set(j, t);
/* 1137 */           String temp1 = hseverity[k];
/* 1138 */           hseverity[k] = hseverity[j];
/* 1139 */           hseverity[j] = temp1;
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
/* 1151 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1152 */     boolean forInventory = false;
/* 1153 */     String trdisplay = "none";
/* 1154 */     String plusstyle = "inline";
/* 1155 */     String minusstyle = "none";
/* 1156 */     String haidTopLevel = "";
/* 1157 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1159 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1161 */         haidTopLevel = request.getParameter("haid");
/* 1162 */         forInventory = true;
/* 1163 */         trdisplay = "table-row;";
/* 1164 */         plusstyle = "none";
/* 1165 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1172 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1175 */     ArrayList listtoreturn = new ArrayList();
/* 1176 */     StringBuffer toreturn = new StringBuffer();
/* 1177 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1178 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1179 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1181 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1183 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1184 */       String childresid = (String)singlerow.get(0);
/* 1185 */       String childresname = (String)singlerow.get(1);
/* 1186 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1187 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1188 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1189 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1190 */       String unmanagestatus = (String)singlerow.get(5);
/* 1191 */       String actionstatus = (String)singlerow.get(6);
/* 1192 */       String linkclass = "monitorgp-links";
/* 1193 */       String titleforres = childresname;
/* 1194 */       String titilechildresname = childresname;
/* 1195 */       String childimg = "/images/trcont.png";
/* 1196 */       String flag = "enable";
/* 1197 */       String dcstarted = (String)singlerow.get(8);
/* 1198 */       String configMonitor = "";
/* 1199 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1200 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1202 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1204 */       if (singlerow.get(7) != null)
/*      */       {
/* 1206 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1208 */       String haiGroupType = "0";
/* 1209 */       if ("HAI".equals(childtype))
/*      */       {
/* 1211 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1213 */       childimg = "/images/trend.png";
/* 1214 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1215 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1216 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1218 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1220 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1222 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1223 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1226 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1228 */         linkclass = "disabledtext";
/* 1229 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1231 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1232 */       String availmouseover = "";
/* 1233 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1235 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1237 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1238 */       String healthmouseover = "";
/* 1239 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1241 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1244 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1245 */       int spacing = 0;
/* 1246 */       if (level >= 1)
/*      */       {
/* 1248 */         spacing = 40 * level;
/*      */       }
/* 1250 */       if (childtype.equals("HAI"))
/*      */       {
/* 1252 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1253 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1254 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1256 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1257 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1258 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1259 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1260 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1261 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1262 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1263 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1264 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1265 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1266 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1268 */         if (!forInventory)
/*      */         {
/* 1270 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1273 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1275 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1277 */           actions = editlink + actions;
/*      */         }
/* 1279 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1281 */           actions = actions + associatelink;
/*      */         }
/* 1283 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1284 */         String arrowimg = "";
/* 1285 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1287 */           actions = "";
/* 1288 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1289 */           checkbox = "";
/* 1290 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1292 */         if (isIt360)
/*      */         {
/* 1294 */           actionimg = "";
/* 1295 */           actions = "";
/* 1296 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1297 */           checkbox = "";
/*      */         }
/*      */         
/* 1300 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1302 */           actions = "";
/*      */         }
/* 1304 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1306 */           checkbox = "";
/*      */         }
/*      */         
/* 1309 */         String resourcelink = "";
/*      */         
/* 1311 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1313 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1317 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1320 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1321 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1322 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1323 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1324 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1325 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1326 */         if (!isIt360)
/*      */         {
/* 1328 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1332 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1335 */         toreturn.append("</tr>");
/* 1336 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1338 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1339 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1343 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1344 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1347 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1351 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1353 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1354 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1355 */             toreturn.append(assocMessage);
/* 1356 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1357 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1358 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1359 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1365 */         String resourcelink = null;
/* 1366 */         boolean hideEditLink = false;
/* 1367 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1369 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1370 */           hideEditLink = true;
/* 1371 */           if (isIt360)
/*      */           {
/* 1373 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1377 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1379 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1381 */           hideEditLink = true;
/* 1382 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1383 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1388 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1391 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1392 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1393 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1394 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1395 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1396 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1397 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1398 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1399 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1400 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1401 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1402 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1403 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1405 */         if (hideEditLink)
/*      */         {
/* 1407 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1409 */         if (!forInventory)
/*      */         {
/* 1411 */           removefromgroup = "";
/*      */         }
/* 1413 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1414 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1415 */           actions = actions + configcustomfields;
/*      */         }
/* 1417 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1419 */           actions = editlink + actions;
/*      */         }
/* 1421 */         String managedLink = "";
/* 1422 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1424 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1425 */           actions = "";
/* 1426 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1427 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1430 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1432 */           checkbox = "";
/*      */         }
/*      */         
/* 1435 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1437 */           actions = "";
/*      */         }
/* 1439 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1440 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1441 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1442 */         if (isIt360)
/*      */         {
/* 1444 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1448 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1450 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1451 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1452 */         if (!isIt360)
/*      */         {
/* 1454 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1458 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1460 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1463 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1470 */       StringBuilder toreturn = new StringBuilder();
/* 1471 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1472 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1473 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1474 */       String title = "";
/* 1475 */       message = EnterpriseUtil.decodeString(message);
/* 1476 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1477 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1478 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1480 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1482 */       else if ("5".equals(severity))
/*      */       {
/* 1484 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1488 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1490 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1491 */       toreturn.append(v);
/*      */       
/* 1493 */       toreturn.append(link);
/* 1494 */       if (severity == null)
/*      */       {
/* 1496 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1498 */       else if (severity.equals("5"))
/*      */       {
/* 1500 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1502 */       else if (severity.equals("4"))
/*      */       {
/* 1504 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1506 */       else if (severity.equals("1"))
/*      */       {
/* 1508 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1513 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1515 */       toreturn.append("</a>");
/* 1516 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1520 */       ex.printStackTrace();
/*      */     }
/* 1522 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1529 */       StringBuilder toreturn = new StringBuilder();
/* 1530 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1531 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1532 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1533 */       if (message == null)
/*      */       {
/* 1535 */         message = "";
/*      */       }
/*      */       
/* 1538 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1539 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1541 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1542 */       toreturn.append(v);
/*      */       
/* 1544 */       toreturn.append(link);
/*      */       
/* 1546 */       if (severity == null)
/*      */       {
/* 1548 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1550 */       else if (severity.equals("5"))
/*      */       {
/* 1552 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1554 */       else if (severity.equals("1"))
/*      */       {
/* 1556 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1561 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1563 */       toreturn.append("</a>");
/* 1564 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1570 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1573 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1574 */     if (invokeActions != null) {
/* 1575 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1576 */       while (iterator.hasNext()) {
/* 1577 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1578 */         if (actionmap.containsKey(actionid)) {
/* 1579 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1584 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1588 */     String actionLink = "";
/* 1589 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1590 */     String query = "";
/* 1591 */     ResultSet rs = null;
/* 1592 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1593 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1594 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1595 */       actionLink = "method=" + methodName;
/*      */     }
/* 1597 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1598 */       actionLink = methodName;
/*      */     }
/* 1600 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1601 */     Iterator itr = methodarglist.iterator();
/* 1602 */     boolean isfirstparam = true;
/* 1603 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1604 */     while (itr.hasNext()) {
/* 1605 */       HashMap argmap = (HashMap)itr.next();
/* 1606 */       String argtype = (String)argmap.get("TYPE");
/* 1607 */       String argname = (String)argmap.get("IDENTITY");
/* 1608 */       String paramname = (String)argmap.get("PARAMETER");
/* 1609 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1610 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1611 */         isfirstparam = false;
/* 1612 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1614 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1618 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1622 */         actionLink = actionLink + "&";
/*      */       }
/* 1624 */       String paramValue = null;
/* 1625 */       String tempargname = argname;
/* 1626 */       if (commonValues.getProperty(tempargname) != null) {
/* 1627 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1630 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1631 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1632 */           if (dbType.equals("mysql")) {
/* 1633 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1636 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1638 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1640 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1641 */             if (rs.next()) {
/* 1642 */               paramValue = rs.getString("VALUE");
/* 1643 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1647 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1651 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1654 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1659 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1660 */           paramValue = rowId;
/*      */         }
/* 1662 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1663 */           paramValue = managedObjectName;
/*      */         }
/* 1665 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1666 */           paramValue = resID;
/*      */         }
/* 1668 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1669 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1672 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1674 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1675 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1676 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1678 */     return actionLink;
/*      */   }
/*      */   
/* 1681 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1682 */     String dependentAttribute = null;
/* 1683 */     String align = "left";
/*      */     
/* 1685 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1686 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1687 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1688 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1689 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1690 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1691 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1692 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1693 */       align = "center";
/*      */     }
/*      */     
/* 1696 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1697 */     String actualdata = "";
/*      */     
/* 1699 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1700 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1701 */         actualdata = availValue;
/*      */       }
/* 1703 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1704 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1708 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1709 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1712 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1718 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1719 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1720 */       toreturn.append("<table>");
/* 1721 */       toreturn.append("<tr>");
/* 1722 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1723 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1724 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1725 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1726 */         String toolTip = "";
/* 1727 */         String hideClass = "";
/* 1728 */         String textStyle = "";
/* 1729 */         boolean isreferenced = true;
/* 1730 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1731 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1732 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1733 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1735 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1736 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1737 */           while (valueList.hasMoreTokens()) {
/* 1738 */             String dependentVal = valueList.nextToken();
/* 1739 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1740 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1741 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1743 */               toolTip = "";
/* 1744 */               hideClass = "";
/* 1745 */               isreferenced = false;
/* 1746 */               textStyle = "disabledtext";
/* 1747 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1751 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1752 */           toolTip = "";
/* 1753 */           hideClass = "";
/* 1754 */           isreferenced = false;
/* 1755 */           textStyle = "disabledtext";
/* 1756 */           if (dependentImageMap != null) {
/* 1757 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1758 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1761 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1765 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1766 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1767 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1768 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1769 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1770 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1772 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1773 */           if (isreferenced) {
/* 1774 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1778 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1779 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1780 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1781 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1782 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1783 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1785 */           toreturn.append("</span>");
/* 1786 */           toreturn.append("</a>");
/* 1787 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1790 */       toreturn.append("</tr>");
/* 1791 */       toreturn.append("</table>");
/* 1792 */       toreturn.append("</td>");
/*      */     } else {
/* 1794 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1797 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1801 */     String colTime = null;
/* 1802 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1803 */     if ((rows != null) && (rows.size() > 0)) {
/* 1804 */       Iterator<String> itr = rows.iterator();
/* 1805 */       String maxColQuery = "";
/* 1806 */       for (;;) { if (itr.hasNext()) {
/* 1807 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1808 */           ResultSet maxCol = null;
/*      */           try {
/* 1810 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1811 */             while (maxCol.next()) {
/* 1812 */               if (colTime == null) {
/* 1813 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1816 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1825 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1827 */               if (maxCol != null)
/* 1828 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1830 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1825 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1827 */               if (maxCol != null)
/* 1828 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1830 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1835 */     return colTime;
/*      */   }
/*      */   
/* 1838 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1839 */     tablename = null;
/* 1840 */     ResultSet rsTable = null;
/* 1841 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1843 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1844 */       while (rsTable.next()) {
/* 1845 */         tablename = rsTable.getString("DATATABLE");
/* 1846 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1847 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1860 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1851 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1854 */         if (rsTable != null)
/* 1855 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1857 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1863 */     String argsList = "";
/* 1864 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1866 */       if (showArgsMap.get(row) != null) {
/* 1867 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1868 */         if (showArgslist != null) {
/* 1869 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1870 */             if (argsList.trim().equals("")) {
/* 1871 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1874 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1881 */       e.printStackTrace();
/* 1882 */       return "";
/*      */     }
/* 1884 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1889 */     String argsList = "";
/* 1890 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1893 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1895 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1896 */         if (hideArgsList != null)
/*      */         {
/* 1898 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1900 */             if (argsList.trim().equals(""))
/*      */             {
/* 1902 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1906 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1914 */       ex.printStackTrace();
/*      */     }
/* 1916 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1920 */     StringBuilder toreturn = new StringBuilder();
/* 1921 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1928 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1929 */       Iterator itr = tActionList.iterator();
/* 1930 */       while (itr.hasNext()) {
/* 1931 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1932 */         String confirmmsg = "";
/* 1933 */         String link = "";
/* 1934 */         String isJSP = "NO";
/* 1935 */         HashMap tactionMap = (HashMap)itr.next();
/* 1936 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1937 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1938 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1939 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1940 */           (actionmap.containsKey(actionId))) {
/* 1941 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1942 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1943 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1944 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1945 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1947 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1953 */           if (isTableAction) {
/* 1954 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1957 */             tableName = "Link";
/* 1958 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1959 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1960 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1961 */             toreturn.append("</a></td>");
/*      */           }
/* 1963 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1964 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1965 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1966 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1972 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1978 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1980 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1981 */       Properties prop = (Properties)node.getUserObject();
/* 1982 */       String mgID = prop.getProperty("label");
/* 1983 */       String mgName = prop.getProperty("value");
/* 1984 */       String isParent = prop.getProperty("isParent");
/* 1985 */       int mgIDint = Integer.parseInt(mgID);
/* 1986 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1988 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1990 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1991 */       if (node.getChildCount() > 0)
/*      */       {
/* 1993 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1995 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1997 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1999 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2003 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2008 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2010 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2012 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2014 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2018 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2021 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2022 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2024 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2028 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2030 */       if (node.getChildCount() > 0)
/*      */       {
/* 2032 */         builder.append("<UL>");
/* 2033 */         printMGTree(node, builder);
/* 2034 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2039 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2040 */     StringBuffer toReturn = new StringBuffer();
/* 2041 */     String table = "-";
/*      */     try {
/* 2043 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2044 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2045 */       float total = 0.0F;
/* 2046 */       while (it.hasNext()) {
/* 2047 */         String attName = (String)it.next();
/* 2048 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2049 */         boolean roundOffData = false;
/* 2050 */         if ((data != null) && (!data.equals(""))) {
/* 2051 */           if (data.indexOf(",") != -1) {
/* 2052 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2055 */             float value = Float.parseFloat(data);
/* 2056 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2059 */             total += value;
/* 2060 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2063 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2068 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2069 */       while (attVsWidthList.hasNext()) {
/* 2070 */         String attName = (String)attVsWidthList.next();
/* 2071 */         String data = (String)attVsWidthProps.get(attName);
/* 2072 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2073 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2074 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2075 */         String className = (String)graphDetails.get("ClassName");
/* 2076 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2077 */         if (percentage < 1.0F)
/*      */         {
/* 2079 */           data = percentage + "";
/*      */         }
/* 2081 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2083 */       if (toReturn.length() > 0) {
/* 2084 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2088 */       e.printStackTrace();
/*      */     }
/* 2090 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2096 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2097 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2098 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2099 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2100 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2101 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2102 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2103 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2104 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2107 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2108 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2109 */       splitvalues[0] = multiplecondition.toString();
/* 2110 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2113 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2118 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2119 */     if (thresholdType != 3) {
/* 2120 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2121 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2122 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2123 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2124 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2125 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2127 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2128 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2129 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2130 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2131 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2132 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2134 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2135 */     if (updateSelected != null) {
/* 2136 */       updateSelected[0] = "selected";
/*      */     }
/* 2138 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2143 */       StringBuffer toreturn = new StringBuffer("");
/* 2144 */       if (commaSeparatedMsgId != null) {
/* 2145 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2146 */         int count = 0;
/* 2147 */         while (msgids.hasMoreTokens()) {
/* 2148 */           String id = msgids.nextToken();
/* 2149 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2150 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2151 */           count++;
/* 2152 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2153 */             if (toreturn.length() == 0) {
/* 2154 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2156 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2157 */             if (!image.trim().equals("")) {
/* 2158 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2160 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2161 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2164 */         if (toreturn.length() > 0) {
/* 2165 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2169 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2172 */       e.printStackTrace(); }
/* 2173 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2179 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2185 */   private static Map<String, Long> _jspx_dependants = new HashMap(5);
/* 2186 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/* 2187 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/* 2188 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2189 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/* 2190 */     _jspx_dependants.put("/jsp/includes/EnterpriseAdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparam;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2215 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005ffmt_005fparam = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2237 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2242 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2243 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2244 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2245 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2249 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.release();
/* 2250 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2251 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2252 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.release();
/* 2253 */     this._005fjspx_005ftagPool_005ffmt_005fparam.release();
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2255 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2256 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2257 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2264 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2267 */     JspWriter out = null;
/* 2268 */     Object page = this;
/* 2269 */     JspWriter _jspx_out = null;
/* 2270 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2274 */       response.setContentType("text/html;charset=UTF-8");
/* 2275 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2277 */       _jspx_page_context = pageContext;
/* 2278 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2279 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2280 */       session = pageContext.getSession();
/* 2281 */       out = pageContext.getOut();
/* 2282 */       _jspx_out = out;
/*      */       
/* 2284 */       out.write("<!DOCTYPE html>\n");
/* 2285 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2286 */       out.write("\n\n\n\n\n\n\n\n");
/*      */       
/* 2288 */       request.setAttribute("HelpKey", "CAM Templates List");
/*      */       
/* 2290 */       out.write(10);
/* 2291 */       out.write(10);
/* 2292 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2293 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/* 2295 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 2296 */       out.write(10);
/* 2297 */       out.write("\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n\t");
/* 2298 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2299 */       out.write("\n\t<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n\n");
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
/* 2456 */                     out.write("\n\n\n<script>\n\n</script>\n");
/*      */                     
/* 2458 */                     String headerJSP = "/jsp/header.jsp?tabtoselect=3";
/*      */                     
/* 2460 */                     if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                     {
/* 2462 */                       headerJSP = "/jsp/test.jsp";
/*      */                     }
/*      */                     
/* 2465 */                     out.write(10);
/*      */                     
/* 2467 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2468 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2469 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2471 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2472 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2473 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2475 */                         out.write(10);
/* 2476 */                         if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2478 */                         out.write(10);
/*      */                         
/* 2480 */                         PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2481 */                         _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2482 */                         _jspx_th_tiles_005fput_005f1.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2484 */                         _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */                         
/* 2486 */                         _jspx_th_tiles_005fput_005f1.setValue(headerJSP);
/* 2487 */                         int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2488 */                         if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2489 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1); return;
/*      */                         }
/*      */                         
/* 2492 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2493 */                         out.write(10);
/*      */                         
/* 2495 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2496 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2497 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2499 */                         _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */                         
/* 2501 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2502 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2503 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2504 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2505 */                             out = _jspx_page_context.pushBody();
/* 2506 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2507 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2510 */                             out.write("\n  \t");
/*      */                             
/* 2512 */                             if (EnterpriseUtil.isAdminServer())
/*      */                             {
/* 2514 */                               out.write("\n                ");
/* 2515 */                               out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*      */                               
/*      */ 
/* 2518 */                               String usertype = FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                               
/* 2520 */                               out.write("\n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n <tr>\n\t     <td height=\"21\"  class=\"leftlinksheading\">");
/* 2521 */                               out.print(FormatUtil.getString("am.webclient.admin.heading"));
/* 2522 */                               out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\" >\n");
/*      */                               
/* 2524 */                               ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2525 */                               _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2526 */                               _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/* 2527 */                               int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2528 */                               if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                 for (;;) {
/* 2530 */                                   out.write(10);
/*      */                                   
/* 2532 */                                   WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2533 */                                   _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2534 */                                   _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                   
/* 2536 */                                   _jspx_th_c_005fwhen_005f0.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 2537 */                                   int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2538 */                                   if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                     for (;;) {
/* 2540 */                                       out.write("\n        ");
/* 2541 */                                       out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 2542 */                                       out.write(10);
/* 2543 */                                       out.write(32);
/* 2544 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2545 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2549 */                                   if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2550 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                   }
/*      */                                   
/* 2553 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2554 */                                   out.write(10);
/* 2555 */                                   out.write(32);
/*      */                                   
/* 2557 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2558 */                                   _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2559 */                                   _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2560 */                                   int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2561 */                                   if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                     for (;;) {
/* 2563 */                                       out.write("\n     ");
/*      */                                       
/* 2565 */                                       EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f0 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 2566 */                                       _jspx_th_ea_005feeadminlink_005f0.setPageContext(_jspx_page_context);
/* 2567 */                                       _jspx_th_ea_005feeadminlink_005f0.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                                       
/* 2569 */                                       _jspx_th_ea_005feeadminlink_005f0.setHref("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general");
/*      */                                       
/* 2571 */                                       _jspx_th_ea_005feeadminlink_005f0.setEnableClass("new-left-links");
/* 2572 */                                       int _jspx_eval_ea_005feeadminlink_005f0 = _jspx_th_ea_005feeadminlink_005f0.doStartTag();
/* 2573 */                                       if (_jspx_eval_ea_005feeadminlink_005f0 != 0) {
/* 2574 */                                         if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/* 2575 */                                           out = _jspx_page_context.pushBody();
/* 2576 */                                           _jspx_th_ea_005feeadminlink_005f0.setBodyContent((BodyContent)out);
/* 2577 */                                           _jspx_th_ea_005feeadminlink_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 2580 */                                           out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 2581 */                                           int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f0.doAfterBody();
/* 2582 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 2585 */                                         if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/* 2586 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 2589 */                                       if (_jspx_th_ea_005feeadminlink_005f0.doEndTag() == 5) {
/* 2590 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0); return;
/*      */                                       }
/*      */                                       
/* 2593 */                                       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0);
/* 2594 */                                       out.write(10);
/* 2595 */                                       out.write(32);
/* 2596 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2597 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2601 */                                   if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2602 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                   }
/*      */                                   
/* 2605 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2606 */                                   out.write(10);
/* 2607 */                                   out.write(32);
/* 2608 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2609 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2613 */                               if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2614 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                               }
/*      */                               
/* 2617 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2618 */                               out.write("\n    </td>\n</tr>\n\n<tr>\n\n<tr>\n    <td class=\"leftlinkstd\" >\n");
/*      */                               
/* 2620 */                               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2621 */                               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2622 */                               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/* 2623 */                               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2624 */                               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                 for (;;) {
/* 2626 */                                   out.write(10);
/*      */                                   
/* 2628 */                                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2629 */                                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2630 */                                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                   
/* 2632 */                                   _jspx_th_c_005fwhen_005f1.setTest("${param.method!='showMailServerConfiguration'}");
/* 2633 */                                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2634 */                                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                     for (;;) {
/* 2636 */                                       out.write("\n    ");
/*      */                                       
/* 2638 */                                       EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f1 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 2639 */                                       _jspx_th_ea_005feeadminlink_005f1.setPageContext(_jspx_page_context);
/* 2640 */                                       _jspx_th_ea_005feeadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                                       
/* 2642 */                                       _jspx_th_ea_005feeadminlink_005f1.setHref("/adminAction.do?method=showMailServerConfiguration");
/*      */                                       
/* 2644 */                                       _jspx_th_ea_005feeadminlink_005f1.setEnableClass("new-left-links");
/* 2645 */                                       int _jspx_eval_ea_005feeadminlink_005f1 = _jspx_th_ea_005feeadminlink_005f1.doStartTag();
/* 2646 */                                       if (_jspx_eval_ea_005feeadminlink_005f1 != 0) {
/* 2647 */                                         if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/* 2648 */                                           out = _jspx_page_context.pushBody();
/* 2649 */                                           _jspx_th_ea_005feeadminlink_005f1.setBodyContent((BodyContent)out);
/* 2650 */                                           _jspx_th_ea_005feeadminlink_005f1.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 2653 */                                           out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 2654 */                                           int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f1.doAfterBody();
/* 2655 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 2658 */                                         if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/* 2659 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 2662 */                                       if (_jspx_th_ea_005feeadminlink_005f1.doEndTag() == 5) {
/* 2663 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f1); return;
/*      */                                       }
/*      */                                       
/* 2666 */                                       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f1);
/* 2667 */                                       out.write(10);
/* 2668 */                                       out.write(32);
/* 2669 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2670 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2674 */                                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2675 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                   }
/*      */                                   
/* 2678 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2679 */                                   out.write(10);
/* 2680 */                                   out.write(32);
/*      */                                   
/* 2682 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2683 */                                   _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2684 */                                   _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2685 */                                   int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2686 */                                   if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                     for (;;) {
/* 2688 */                                       out.write(10);
/* 2689 */                                       out.write(9);
/* 2690 */                                       out.write(32);
/* 2691 */                                       out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 2692 */                                       out.write(10);
/* 2693 */                                       out.write(32);
/* 2694 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2695 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2699 */                                   if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2700 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                   }
/*      */                                   
/* 2703 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2704 */                                   out.write(10);
/* 2705 */                                   out.write(32);
/* 2706 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2707 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2711 */                               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2712 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                               }
/*      */                               
/* 2715 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2716 */                               out.write("\n    </td>\n</tr>\n\n");
/* 2717 */                               if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 2718 */                                 out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                                 
/* 2720 */                                 ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2721 */                                 _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2722 */                                 _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/* 2723 */                                 int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2724 */                                 if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                   for (;;) {
/* 2726 */                                     out.write(10);
/*      */                                     
/* 2728 */                                     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2729 */                                     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2730 */                                     _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                     
/* 2732 */                                     _jspx_th_c_005fwhen_005f2.setTest("${param.method!='SMSServerConfiguration'}");
/* 2733 */                                     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2734 */                                     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                       for (;;) {
/* 2736 */                                         out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 2737 */                                         out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 2738 */                                         out.write("\n    </a>\n ");
/* 2739 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2740 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2744 */                                     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2745 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                     }
/*      */                                     
/* 2748 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2749 */                                     out.write(10);
/* 2750 */                                     out.write(32);
/*      */                                     
/* 2752 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2753 */                                     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2754 */                                     _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 2755 */                                     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2756 */                                     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                                       for (;;) {
/* 2758 */                                         out.write("\n         ");
/* 2759 */                                         out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 2760 */                                         out.write(10);
/* 2761 */                                         out.write(32);
/* 2762 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2763 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2767 */                                     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2768 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                                     }
/*      */                                     
/* 2771 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2772 */                                     out.write(10);
/* 2773 */                                     out.write(32);
/* 2774 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2775 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2779 */                                 if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2780 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                                 }
/*      */                                 
/* 2783 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2784 */                                 out.write("\n    </td>\n</tr>\n");
/*      */                               }
/* 2786 */                               out.write("\n\n\n<tr>\n\n    <td class=\"leftlinkstd\">\n");
/*      */                               
/* 2788 */                               ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2789 */                               _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2790 */                               _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/* 2791 */                               int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2792 */                               if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                                 for (;;) {
/* 2794 */                                   out.write(10);
/*      */                                   
/* 2796 */                                   WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2797 */                                   _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2798 */                                   _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                   
/* 2800 */                                   _jspx_th_c_005fwhen_005f3.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 2801 */                                   int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2802 */                                   if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                     for (;;) {
/* 2804 */                                       out.write("\n    ");
/*      */                                       
/* 2806 */                                       EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f2 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 2807 */                                       _jspx_th_ea_005feeadminlink_005f2.setPageContext(_jspx_page_context);
/* 2808 */                                       _jspx_th_ea_005feeadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                                       
/* 2810 */                                       _jspx_th_ea_005feeadminlink_005f2.setHref("/jsp/ProxyConfiguration.jsp");
/*      */                                       
/* 2812 */                                       _jspx_th_ea_005feeadminlink_005f2.setEnableClass("new-left-links");
/* 2813 */                                       int _jspx_eval_ea_005feeadminlink_005f2 = _jspx_th_ea_005feeadminlink_005f2.doStartTag();
/* 2814 */                                       if (_jspx_eval_ea_005feeadminlink_005f2 != 0) {
/* 2815 */                                         if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/* 2816 */                                           out = _jspx_page_context.pushBody();
/* 2817 */                                           _jspx_th_ea_005feeadminlink_005f2.setBodyContent((BodyContent)out);
/* 2818 */                                           _jspx_th_ea_005feeadminlink_005f2.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 2821 */                                           out.write("\n    ");
/* 2822 */                                           out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 2823 */                                           out.write("\n    ");
/* 2824 */                                           int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f2.doAfterBody();
/* 2825 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 2828 */                                         if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/* 2829 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 2832 */                                       if (_jspx_th_ea_005feeadminlink_005f2.doEndTag() == 5) {
/* 2833 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2); return;
/*      */                                       }
/*      */                                       
/* 2836 */                                       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2);
/* 2837 */                                       out.write(10);
/* 2838 */                                       out.write(32);
/* 2839 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2840 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2844 */                                   if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2845 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                   }
/*      */                                   
/* 2848 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2849 */                                   out.write(10);
/* 2850 */                                   out.write(32);
/*      */                                   
/* 2852 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2853 */                                   _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2854 */                                   _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 2855 */                                   int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2856 */                                   if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                                     for (;;) {
/* 2858 */                                       out.write(10);
/* 2859 */                                       out.write(9);
/* 2860 */                                       out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 2861 */                                       out.write(10);
/* 2862 */                                       out.write(32);
/* 2863 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2864 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2868 */                                   if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2869 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                                   }
/*      */                                   
/* 2872 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2873 */                                   out.write(10);
/* 2874 */                                   out.write(32);
/* 2875 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2876 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2880 */                               if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2881 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                               }
/*      */                               
/* 2884 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2885 */                               out.write("\n    </td>\n</tr>\n<tr>\n\n<td class=\"leftlinkstd\">\n");
/*      */                               
/* 2887 */                               ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2888 */                               _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2889 */                               _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/* 2890 */                               int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2891 */                               if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                                 for (;;) {
/* 2893 */                                   out.write(10);
/*      */                                   
/* 2895 */                                   WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2896 */                                   _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 2897 */                                   _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                                   
/* 2899 */                                   _jspx_th_c_005fwhen_005f4.setTest("${uri !='/admin/userconfiguration.do'}");
/* 2900 */                                   int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 2901 */                                   if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                     for (;;) {
/* 2903 */                                       out.write("\n\n        ");
/*      */                                       
/* 2905 */                                       EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f3 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 2906 */                                       _jspx_th_ea_005feeadminlink_005f3.setPageContext(_jspx_page_context);
/* 2907 */                                       _jspx_th_ea_005feeadminlink_005f3.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                                       
/* 2909 */                                       _jspx_th_ea_005feeadminlink_005f3.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                                       
/* 2911 */                                       _jspx_th_ea_005feeadminlink_005f3.setEnableClass("new-left-links");
/* 2912 */                                       int _jspx_eval_ea_005feeadminlink_005f3 = _jspx_th_ea_005feeadminlink_005f3.doStartTag();
/* 2913 */                                       if (_jspx_eval_ea_005feeadminlink_005f3 != 0) {
/* 2914 */                                         if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/* 2915 */                                           out = _jspx_page_context.pushBody();
/* 2916 */                                           _jspx_th_ea_005feeadminlink_005f3.setBodyContent((BodyContent)out);
/* 2917 */                                           _jspx_th_ea_005feeadminlink_005f3.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 2920 */                                           out.write("\n       ");
/* 2921 */                                           out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2922 */                                           out.write("\n        ");
/* 2923 */                                           int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f3.doAfterBody();
/* 2924 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 2927 */                                         if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/* 2928 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 2931 */                                       if (_jspx_th_ea_005feeadminlink_005f3.doEndTag() == 5) {
/* 2932 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f3); return;
/*      */                                       }
/*      */                                       
/* 2935 */                                       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f3);
/* 2936 */                                       out.write(10);
/* 2937 */                                       out.write(10);
/* 2938 */                                       out.write(32);
/* 2939 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 2940 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2944 */                                   if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 2945 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                                   }
/*      */                                   
/* 2948 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2949 */                                   out.write(10);
/* 2950 */                                   out.write(32);
/*      */                                   
/* 2952 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2953 */                                   _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2954 */                                   _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 2955 */                                   int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2956 */                                   if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                     for (;;) {
/* 2958 */                                       out.write(10);
/* 2959 */                                       out.write(9);
/* 2960 */                                       out.write(32);
/* 2961 */                                       out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2962 */                                       out.write(10);
/* 2963 */                                       out.write(32);
/* 2964 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2965 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2969 */                                   if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2970 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                                   }
/*      */                                   
/* 2973 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2974 */                                   out.write(10);
/* 2975 */                                   out.write(32);
/* 2976 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2977 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2981 */                               if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2982 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                               }
/*      */                               
/* 2985 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2986 */                               out.write("\n</td>\n</tr>\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                               
/* 2988 */                               ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2989 */                               _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2990 */                               _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/* 2991 */                               int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2992 */                               if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                                 for (;;) {
/* 2994 */                                   out.write("\n   ");
/*      */                                   
/* 2996 */                                   WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2997 */                                   _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2998 */                                   _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                                   
/* 3000 */                                   _jspx_th_c_005fwhen_005f5.setTest("${param.method!='showManagedServers'}");
/* 3001 */                                   int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 3002 */                                   if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                     for (;;) {
/* 3004 */                                       out.write("\n    ");
/*      */                                       
/* 3006 */                                       EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f4 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 3007 */                                       _jspx_th_ea_005feeadminlink_005f4.setPageContext(_jspx_page_context);
/* 3008 */                                       _jspx_th_ea_005feeadminlink_005f4.setParent(_jspx_th_c_005fwhen_005f5);
/*      */                                       
/* 3010 */                                       _jspx_th_ea_005feeadminlink_005f4.setHref("/adminAction.do?method=showManagedServers");
/*      */                                       
/* 3012 */                                       _jspx_th_ea_005feeadminlink_005f4.setEnableClass("new-left-links");
/* 3013 */                                       int _jspx_eval_ea_005feeadminlink_005f4 = _jspx_th_ea_005feeadminlink_005f4.doStartTag();
/* 3014 */                                       if (_jspx_eval_ea_005feeadminlink_005f4 != 0) {
/* 3015 */                                         if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/* 3016 */                                           out = _jspx_page_context.pushBody();
/* 3017 */                                           _jspx_th_ea_005feeadminlink_005f4.setBodyContent((BodyContent)out);
/* 3018 */                                           _jspx_th_ea_005feeadminlink_005f4.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3021 */                                           out.write("\n     ");
/* 3022 */                                           out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/* 3023 */                                           out.write(10);
/* 3024 */                                           out.write(9);
/* 3025 */                                           int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f4.doAfterBody();
/* 3026 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3029 */                                         if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/* 3030 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3033 */                                       if (_jspx_th_ea_005feeadminlink_005f4.doEndTag() == 5) {
/* 3034 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4); return;
/*      */                                       }
/*      */                                       
/* 3037 */                                       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4);
/* 3038 */                                       out.write("\n   ");
/* 3039 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 3040 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3044 */                                   if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 3045 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                                   }
/*      */                                   
/* 3048 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 3049 */                                   out.write("\n   ");
/*      */                                   
/* 3051 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3052 */                                   _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 3053 */                                   _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 3054 */                                   int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 3055 */                                   if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                                     for (;;) {
/* 3057 */                                       out.write("\n     ");
/* 3058 */                                       out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/* 3059 */                                       out.write("\n   ");
/* 3060 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 3061 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3065 */                                   if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 3066 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                                   }
/*      */                                   
/* 3069 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 3070 */                                   out.write("\n   ");
/* 3071 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 3072 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3076 */                               if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 3077 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                               }
/*      */                               
/* 3080 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 3081 */                               out.write("\n  </td>\n</tr>\n\n\n<td class=\"leftlinkstd\" >\n");
/*      */                               
/* 3083 */                               ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3084 */                               _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 3085 */                               _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_tiles_005fput_005f2);
/* 3086 */                               int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 3087 */                               if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                                 for (;;) {
/* 3089 */                                   out.write(10);
/*      */                                   
/* 3091 */                                   WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3092 */                                   _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 3093 */                                   _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                                   
/* 3095 */                                   _jspx_th_c_005fwhen_005f6.setTest("${param.server!='admin'}");
/* 3096 */                                   int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 3097 */                                   if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                                     for (;;) {
/* 3099 */                                       out.write(10);
/*      */                                       
/* 3101 */                                       EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f5 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 3102 */                                       _jspx_th_ea_005feeadminlink_005f5.setPageContext(_jspx_page_context);
/* 3103 */                                       _jspx_th_ea_005feeadminlink_005f5.setParent(_jspx_th_c_005fwhen_005f6);
/*      */                                       
/* 3105 */                                       _jspx_th_ea_005feeadminlink_005f5.setHref("/adminAction.do?method=showActionProfiles");
/*      */                                       
/* 3107 */                                       _jspx_th_ea_005feeadminlink_005f5.setEnableClass("new-left-links");
/* 3108 */                                       int _jspx_eval_ea_005feeadminlink_005f5 = _jspx_th_ea_005feeadminlink_005f5.doStartTag();
/* 3109 */                                       if (_jspx_eval_ea_005feeadminlink_005f5 != 0) {
/* 3110 */                                         if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/* 3111 */                                           out = _jspx_page_context.pushBody();
/* 3112 */                                           _jspx_th_ea_005feeadminlink_005f5.setBodyContent((BodyContent)out);
/* 3113 */                                           _jspx_th_ea_005feeadminlink_005f5.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3116 */                                           out.write(10);
/* 3117 */                                           out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/* 3118 */                                           out.write(10);
/* 3119 */                                           int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f5.doAfterBody();
/* 3120 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3123 */                                         if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/* 3124 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3127 */                                       if (_jspx_th_ea_005feeadminlink_005f5.doEndTag() == 5) {
/* 3128 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f5); return;
/*      */                                       }
/*      */                                       
/* 3131 */                                       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f5);
/* 3132 */                                       out.write(10);
/* 3133 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 3134 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3138 */                                   if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 3139 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                                   }
/*      */                                   
/* 3142 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 3143 */                                   out.write(10);
/*      */                                   
/* 3145 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3146 */                                   _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 3147 */                                   _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/* 3148 */                                   int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 3149 */                                   if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                                     for (;;) {
/* 3151 */                                       out.write(10);
/* 3152 */                                       out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/* 3153 */                                       out.write(10);
/* 3154 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 3155 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3159 */                                   if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 3160 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                                   }
/*      */                                   
/* 3163 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 3164 */                                   out.write(10);
/* 3165 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 3166 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3170 */                               if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 3171 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                               }
/*      */                               
/* 3174 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 3175 */                               out.write("\n</td>\n</tr>\n");
/*      */                               
/* 3177 */                               if ((!usertype.equals("F")) || (com.adventnet.appmanager.util.Constants.isIt360))
/*      */                               {
/* 3179 */                                 out.write("\n<tr>\n<td class=\"leftlinkstd\" >\n ");
/*      */                                 
/* 3181 */                                 ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3182 */                                 _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 3183 */                                 _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_tiles_005fput_005f2);
/* 3184 */                                 int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 3185 */                                 if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                                   for (;;) {
/* 3187 */                                     out.write(10);
/* 3188 */                                     out.write(32);
/* 3189 */                                     out.write(32);
/*      */                                     
/* 3191 */                                     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3192 */                                     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 3193 */                                     _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                                     
/* 3195 */                                     _jspx_th_c_005fwhen_005f7.setTest("${param.method =='showScheduleReports'}");
/* 3196 */                                     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 3197 */                                     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                                       for (;;) {
/* 3199 */                                         out.write(10);
/* 3200 */                                         out.write(32);
/* 3201 */                                         out.write(32);
/* 3202 */                                         out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3203 */                                         out.write(10);
/* 3204 */                                         out.write(32);
/* 3205 */                                         out.write(32);
/* 3206 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 3207 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3211 */                                     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 3212 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                                     }
/*      */                                     
/* 3215 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 3216 */                                     out.write(10);
/* 3217 */                                     out.write(32);
/*      */                                     
/* 3219 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3220 */                                     _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 3221 */                                     _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 3222 */                                     int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 3223 */                                     if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                                       for (;;) {
/* 3225 */                                         out.write("\n   ");
/*      */                                         
/* 3227 */                                         EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f6 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 3228 */                                         _jspx_th_ea_005feeadminlink_005f6.setPageContext(_jspx_page_context);
/* 3229 */                                         _jspx_th_ea_005feeadminlink_005f6.setParent(_jspx_th_c_005fotherwise_005f7);
/*      */                                         
/* 3231 */                                         _jspx_th_ea_005feeadminlink_005f6.setHref("/scheduleReports.do?method=showScheduleReports");
/*      */                                         
/* 3233 */                                         _jspx_th_ea_005feeadminlink_005f6.setEnableClass("new-left-links");
/* 3234 */                                         int _jspx_eval_ea_005feeadminlink_005f6 = _jspx_th_ea_005feeadminlink_005f6.doStartTag();
/* 3235 */                                         if (_jspx_eval_ea_005feeadminlink_005f6 != 0) {
/* 3236 */                                           if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/* 3237 */                                             out = _jspx_page_context.pushBody();
/* 3238 */                                             _jspx_th_ea_005feeadminlink_005f6.setBodyContent((BodyContent)out);
/* 3239 */                                             _jspx_th_ea_005feeadminlink_005f6.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3242 */                                             out.write("\n   ");
/* 3243 */                                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3244 */                                             out.write(10);
/* 3245 */                                             out.write(32);
/* 3246 */                                             out.write(32);
/* 3247 */                                             int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f6.doAfterBody();
/* 3248 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3251 */                                           if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/* 3252 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3255 */                                         if (_jspx_th_ea_005feeadminlink_005f6.doEndTag() == 5) {
/* 3256 */                                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6); return;
/*      */                                         }
/*      */                                         
/* 3259 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6);
/* 3260 */                                         out.write(10);
/* 3261 */                                         out.write(32);
/* 3262 */                                         out.write(32);
/* 3263 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 3264 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3268 */                                     if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 3269 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                                     }
/*      */                                     
/* 3272 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 3273 */                                     out.write(10);
/* 3274 */                                     out.write(32);
/* 3275 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 3276 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3280 */                                 if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 3281 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                                 }
/*      */                                 
/* 3284 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 3285 */                                 out.write("\n</td>\n</tr>\n");
/*      */                               } else {
/* 3287 */                                 out.write("\n<tr>\n    <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 3288 */                                 out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3289 */                                 out.write("\n    </td>\n</tr>\n");
/*      */                               }
/* 3291 */                               out.write("\n<tr>\n<td class=\"leftlinkstd\" >\n ");
/*      */                               
/* 3293 */                               ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3294 */                               _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 3295 */                               _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_tiles_005fput_005f2);
/* 3296 */                               int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 3297 */                               if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                                 for (;;) {
/* 3299 */                                   out.write(10);
/* 3300 */                                   out.write(32);
/* 3301 */                                   out.write(32);
/*      */                                   
/* 3303 */                                   WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3304 */                                   _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 3305 */                                   _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                                   
/* 3307 */                                   _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showDataCleanUp'}");
/* 3308 */                                   int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 3309 */                                   if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                     for (;;) {
/* 3311 */                                       out.write(10);
/* 3312 */                                       out.write(32);
/* 3313 */                                       out.write(32);
/* 3314 */                                       out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 3315 */                                       out.write(10);
/* 3316 */                                       out.write(32);
/* 3317 */                                       out.write(32);
/* 3318 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 3319 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3323 */                                   if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 3324 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                                   }
/*      */                                   
/* 3327 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 3328 */                                   out.write(10);
/* 3329 */                                   out.write(32);
/*      */                                   
/* 3331 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3332 */                                   _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 3333 */                                   _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/* 3334 */                                   int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 3335 */                                   if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                                     for (;;) {
/* 3337 */                                       out.write("\n   ");
/*      */                                       
/* 3339 */                                       EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f7 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 3340 */                                       _jspx_th_ea_005feeadminlink_005f7.setPageContext(_jspx_page_context);
/* 3341 */                                       _jspx_th_ea_005feeadminlink_005f7.setParent(_jspx_th_c_005fotherwise_005f8);
/*      */                                       
/* 3343 */                                       _jspx_th_ea_005feeadminlink_005f7.setHref("/adminAction.do?method=showDataCleanUp");
/*      */                                       
/* 3345 */                                       _jspx_th_ea_005feeadminlink_005f7.setEnableClass("new-left-links");
/* 3346 */                                       int _jspx_eval_ea_005feeadminlink_005f7 = _jspx_th_ea_005feeadminlink_005f7.doStartTag();
/* 3347 */                                       if (_jspx_eval_ea_005feeadminlink_005f7 != 0) {
/* 3348 */                                         if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/* 3349 */                                           out = _jspx_page_context.pushBody();
/* 3350 */                                           _jspx_th_ea_005feeadminlink_005f7.setBodyContent((BodyContent)out);
/* 3351 */                                           _jspx_th_ea_005feeadminlink_005f7.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3354 */                                           out.write("\n   ");
/* 3355 */                                           out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 3356 */                                           out.write(10);
/* 3357 */                                           out.write(32);
/* 3358 */                                           out.write(32);
/* 3359 */                                           int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f7.doAfterBody();
/* 3360 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3363 */                                         if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/* 3364 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3367 */                                       if (_jspx_th_ea_005feeadminlink_005f7.doEndTag() == 5) {
/* 3368 */                                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f7); return;
/*      */                                       }
/*      */                                       
/* 3371 */                                       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f7);
/* 3372 */                                       out.write(10);
/* 3373 */                                       out.write(32);
/* 3374 */                                       out.write(32);
/* 3375 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 3376 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3380 */                                   if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 3381 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                                   }
/*      */                                   
/* 3384 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 3385 */                                   out.write(10);
/* 3386 */                                   out.write(32);
/* 3387 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 3388 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3392 */                               if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 3393 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                               }
/*      */                               
/* 3396 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 3397 */                               out.write("\n</td>\n</tr>\n</table>\n\n");
/* 3398 */                               out.write("\n        ");
/*      */                             }
/*      */                             else
/*      */                             {
/* 3402 */                               out.write("\n                ");
/* 3403 */                               out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */                               
/*      */ 
/* 3406 */                               String usertype = FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                               
/* 3408 */                               out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/* 3409 */                               out.print(FormatUtil.getString("wizard.disabled"));
/* 3410 */                               out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/* 3411 */                               out.print(FormatUtil.getString("am.webclient.admin.heading"));
/* 3412 */                               out.write("</td>\n  </tr>\n  \n ");
/*      */                               
/* 3414 */                               if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                               {
/*      */ 
/* 3417 */                                 out.write("  \n  <tr>\n\n  ");
/*      */                                 
/* 3419 */                                 if (request.getParameter("wiz") != null)
/*      */                                 {
/* 3421 */                                   out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/* 3422 */                                   out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 3423 */                                   out.write("\" class='disabledlink'>");
/* 3424 */                                   out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 3425 */                                   out.write("</a></td>\n  ");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3429 */                                   out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */                                   
/* 3431 */                                   ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3432 */                                   _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 3433 */                                   _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_tiles_005fput_005f2);
/* 3434 */                                   int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 3435 */                                   if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                                     for (;;) {
/* 3437 */                                       out.write(10);
/*      */                                       
/* 3439 */                                       WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3440 */                                       _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 3441 */                                       _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                                       
/* 3443 */                                       _jspx_th_c_005fwhen_005f9.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/* 3444 */                                       int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 3445 */                                       if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                                         for (;;) {
/* 3447 */                                           out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/* 3448 */                                           out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 3449 */                                           out.write("\n    </a>\n ");
/* 3450 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 3451 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3455 */                                       if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 3456 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                                       }
/*      */                                       
/* 3459 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 3460 */                                       out.write(10);
/* 3461 */                                       out.write(32);
/*      */                                       
/* 3463 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3464 */                                       _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 3465 */                                       _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 3466 */                                       int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 3467 */                                       if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                                         for (;;) {
/* 3469 */                                           out.write(10);
/* 3470 */                                           out.write(9);
/* 3471 */                                           out.write(32);
/* 3472 */                                           out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 3473 */                                           out.write(10);
/* 3474 */                                           out.write(32);
/* 3475 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 3476 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3480 */                                       if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 3481 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                                       }
/*      */                                       
/* 3484 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 3485 */                                       out.write(10);
/* 3486 */                                       out.write(32);
/* 3487 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 3488 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3492 */                                   if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 3493 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                                   }
/*      */                                   
/* 3496 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 3497 */                                   out.write("\n    </td>\n\t");
/*      */                                 }
/* 3499 */                                 out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */                                 
/* 3501 */                                 if (request.getParameter("wiz") != null)
/*      */                                 {
/* 3503 */                                   out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/* 3504 */                                   out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 3505 */                                   out.write("\" class='disabledlink'>");
/* 3506 */                                   out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 3507 */                                   out.write("</a></td>\n   ");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3511 */                                   out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */                                   
/* 3513 */                                   ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3514 */                                   _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 3515 */                                   _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_tiles_005fput_005f2);
/* 3516 */                                   int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 3517 */                                   if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                                     for (;;) {
/* 3519 */                                       out.write(10);
/*      */                                       
/* 3521 */                                       WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3522 */                                       _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 3523 */                                       _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                                       
/* 3525 */                                       _jspx_th_c_005fwhen_005f10.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/* 3526 */                                       int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 3527 */                                       if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                                         for (;;) {
/* 3529 */                                           out.write("\n   ");
/* 3530 */                                           out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 3531 */                                           out.write(10);
/* 3532 */                                           out.write(32);
/* 3533 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 3534 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3538 */                                       if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 3539 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                                       }
/*      */                                       
/* 3542 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 3543 */                                       out.write(10);
/* 3544 */                                       out.write(32);
/*      */                                       
/* 3546 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3547 */                                       _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 3548 */                                       _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 3549 */                                       int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 3550 */                                       if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                                         for (;;) {
/* 3552 */                                           out.write(10);
/* 3553 */                                           String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/* 3554 */                                           out.write("\n\t \n <a href=\"");
/* 3555 */                                           out.print(link);
/* 3556 */                                           out.write("\" class=\"new-left-links\">\n               ");
/* 3557 */                                           out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 3558 */                                           out.write("\n    </a>    \n ");
/* 3559 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 3560 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3564 */                                       if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 3565 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                                       }
/*      */                                       
/* 3568 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 3569 */                                       out.write(10);
/* 3570 */                                       out.write(32);
/* 3571 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 3572 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3576 */                                   if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 3577 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                                   }
/*      */                                   
/* 3580 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 3581 */                                   out.write("\n</td>\n");
/*      */                                 }
/* 3583 */                                 out.write("\n</tr>\n\n ");
/*      */                               }
/*      */                               
/*      */ 
/* 3587 */                               out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                               
/* 3589 */                               ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3590 */                               _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 3591 */                               _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_tiles_005fput_005f2);
/* 3592 */                               int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 3593 */                               if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                                 for (;;) {
/* 3595 */                                   out.write(10);
/*      */                                   
/* 3597 */                                   WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3598 */                                   _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 3599 */                                   _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                                   
/* 3601 */                                   _jspx_th_c_005fwhen_005f11.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/* 3602 */                                   int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 3603 */                                   if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                                     for (;;) {
/* 3605 */                                       out.write("\n    \n       ");
/* 3606 */                                       out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 3607 */                                       out.write(10);
/* 3608 */                                       out.write(32);
/* 3609 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 3610 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3614 */                                   if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 3615 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                                   }
/*      */                                   
/* 3618 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 3619 */                                   out.write(10);
/* 3620 */                                   out.write(32);
/*      */                                   
/* 3622 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3623 */                                   _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 3624 */                                   _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 3625 */                                   int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 3626 */                                   if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                                     for (;;) {
/* 3628 */                                       out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/* 3629 */                                       out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 3630 */                                       out.write("\n    </a>\n ");
/* 3631 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 3632 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3636 */                                   if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 3637 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                                   }
/*      */                                   
/* 3640 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 3641 */                                   out.write(10);
/* 3642 */                                   out.write(32);
/* 3643 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 3644 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3648 */                               if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 3649 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                               }
/*      */                               
/* 3652 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 3653 */                               out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                               
/* 3655 */                               ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3656 */                               _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 3657 */                               _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_tiles_005fput_005f2);
/* 3658 */                               int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 3659 */                               if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                                 for (;;) {
/* 3661 */                                   out.write(10);
/*      */                                   
/* 3663 */                                   WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3664 */                                   _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 3665 */                                   _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                                   
/* 3667 */                                   _jspx_th_c_005fwhen_005f12.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/* 3668 */                                   int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 3669 */                                   if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                                     for (;;) {
/* 3671 */                                       out.write("\n    \n       ");
/* 3672 */                                       out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 3673 */                                       out.write(10);
/* 3674 */                                       out.write(32);
/* 3675 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 3676 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3680 */                                   if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 3681 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                                   }
/*      */                                   
/* 3684 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 3685 */                                   out.write(10);
/* 3686 */                                   out.write(32);
/*      */                                   
/* 3688 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3689 */                                   _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 3690 */                                   _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/* 3691 */                                   int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 3692 */                                   if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                                     for (;;) {
/* 3694 */                                       out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/* 3695 */                                       out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 3696 */                                       out.write("\n\t </a>\n ");
/* 3697 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 3698 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3702 */                                   if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 3703 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */                                   }
/*      */                                   
/* 3706 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 3707 */                                   out.write(10);
/* 3708 */                                   out.write(32);
/* 3709 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 3710 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3714 */                               if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 3715 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                               }
/*      */                               
/* 3718 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 3719 */                               out.write("\n    </td>\n</tr>  \n\n  ");
/*      */                               
/* 3721 */                               if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                               {
/*      */ 
/* 3724 */                                 out.write(32);
/* 3725 */                                 out.write(32);
/* 3726 */                                 out.write(10);
/*      */                                 
/* 3728 */                                 ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3729 */                                 _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 3730 */                                 _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_tiles_005fput_005f2);
/* 3731 */                                 int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 3732 */                                 if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                                   for (;;) {
/* 3734 */                                     out.write(10);
/*      */                                     
/* 3736 */                                     WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3737 */                                     _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 3738 */                                     _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                                     
/* 3740 */                                     _jspx_th_c_005fwhen_005f13.setTest("${param.method !='showNetworkDiscoveryForm'}");
/* 3741 */                                     int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 3742 */                                     if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                                       for (;;) {
/* 3744 */                                         out.write("\n<tr>\n    ");
/* 3745 */                                         if (!request.isUserInRole("OPERATOR")) {
/* 3746 */                                           out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/* 3747 */                                           out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 3748 */                                           out.write("\n    </a>\n        </td>\n     ");
/*      */                                         } else {
/* 3750 */                                           out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/* 3751 */                                           out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 3752 */                                           out.write("\n\t</a>\n\t </td>\n\t");
/*      */                                         }
/* 3754 */                                         out.write("\n    </tr>\n ");
/* 3755 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 3756 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3760 */                                     if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 3761 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                                     }
/*      */                                     
/* 3764 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 3765 */                                     out.write(10);
/* 3766 */                                     out.write(32);
/*      */                                     
/* 3768 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3769 */                                     _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 3770 */                                     _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/* 3771 */                                     int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 3772 */                                     if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */                                       for (;;) {
/* 3774 */                                         out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/* 3775 */                                         out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 3776 */                                         out.write("\n\t </td>\n ");
/* 3777 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 3778 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3782 */                                     if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 3783 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */                                     }
/*      */                                     
/* 3786 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 3787 */                                     out.write(10);
/* 3788 */                                     out.write(32);
/* 3789 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 3790 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3794 */                                 if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 3795 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */                                 }
/*      */                                 
/* 3798 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 3799 */                                 out.write("\n \n  ");
/*      */                               }
/*      */                               
/*      */ 
/* 3803 */                               out.write("  \n \n ");
/*      */                               
/* 3805 */                               if (!usertype.equals("F"))
/*      */                               {
/* 3807 */                                 out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */                                 
/* 3809 */                                 ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3810 */                                 _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 3811 */                                 _jspx_th_c_005fchoose_005f14.setParent(_jspx_th_tiles_005fput_005f2);
/* 3812 */                                 int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 3813 */                                 if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */                                   for (;;) {
/* 3815 */                                     out.write(10);
/* 3816 */                                     out.write(9);
/*      */                                     
/* 3818 */                                     WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3819 */                                     _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 3820 */                                     _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */                                     
/* 3822 */                                     _jspx_th_c_005fwhen_005f14.setTest("${param.method !='maintenanceTaskListView'}");
/* 3823 */                                     int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 3824 */                                     if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                                       for (;;) {
/* 3826 */                                         out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 3827 */                                         out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3828 */                                         out.write("</a>\n  \t");
/* 3829 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 3830 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3834 */                                     if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 3835 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                                     }
/*      */                                     
/* 3838 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 3839 */                                     out.write("\n  \t");
/*      */                                     
/* 3841 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3842 */                                     _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 3843 */                                     _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/* 3844 */                                     int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 3845 */                                     if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                                       for (;;) {
/* 3847 */                                         out.write("\n \t\t");
/* 3848 */                                         out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3849 */                                         out.write("\n  \t");
/* 3850 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 3851 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3855 */                                     if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 3856 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */                                     }
/*      */                                     
/* 3859 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 3860 */                                     out.write("\n  \t");
/* 3861 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 3862 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3866 */                                 if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 3867 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */                                 }
/*      */                                 
/* 3870 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 3871 */                                 out.write("\n     </td>\n </tr>   \n \n ");
/*      */                                 
/* 3873 */                                 if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                                 {
/*      */ 
/* 3876 */                                   out.write(32);
/* 3877 */                                   out.write(32);
/* 3878 */                                   out.write(10);
/*      */                                   
/* 3880 */                                   IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3881 */                                   _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3882 */                                   _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                   
/* 3884 */                                   _jspx_th_c_005fif_005f0.setTest("${category!='LAMP'}");
/* 3885 */                                   int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3886 */                                   if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                     for (;;) {
/* 3888 */                                       out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                                       
/* 3890 */                                       ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3891 */                                       _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 3892 */                                       _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_c_005fif_005f0);
/* 3893 */                                       int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 3894 */                                       if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                                         for (;;) {
/* 3896 */                                           out.write(10);
/* 3897 */                                           out.write(32);
/* 3898 */                                           out.write(9);
/*      */                                           
/* 3900 */                                           WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3901 */                                           _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 3902 */                                           _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                                           
/* 3904 */                                           _jspx_th_c_005fwhen_005f15.setTest("${param.method !='listTrapListener'}");
/* 3905 */                                           int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 3906 */                                           if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                                             for (;;) {
/* 3908 */                                               out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/* 3909 */                                               out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3910 */                                               out.write("</a>\n   \t");
/* 3911 */                                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 3912 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3916 */                                           if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 3917 */                                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                                           }
/*      */                                           
/* 3920 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 3921 */                                           out.write("\n   \t");
/*      */                                           
/* 3923 */                                           OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3924 */                                           _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 3925 */                                           _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/* 3926 */                                           int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 3927 */                                           if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                                             for (;;) {
/* 3929 */                                               out.write("\n  \t\t");
/* 3930 */                                               out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3931 */                                               out.write(" \n   \t");
/* 3932 */                                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 3933 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3937 */                                           if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 3938 */                                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */                                           }
/*      */                                           
/* 3941 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 3942 */                                           out.write("\n   \t");
/* 3943 */                                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 3944 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3948 */                                       if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 3949 */                                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */                                       }
/*      */                                       
/* 3952 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 3953 */                                       out.write("\n      </td>\n  </tr>   \n");
/* 3954 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3955 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3959 */                                   if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3960 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                                   }
/*      */                                   
/* 3963 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3964 */                                   out.write(10);
/* 3965 */                                   out.write(32);
/*      */                                 }
/*      */                                 
/*      */ 
/* 3969 */                                 out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                                 
/* 3971 */                                 ChooseTag _jspx_th_c_005fchoose_005f16 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3972 */                                 _jspx_th_c_005fchoose_005f16.setPageContext(_jspx_page_context);
/* 3973 */                                 _jspx_th_c_005fchoose_005f16.setParent(_jspx_th_tiles_005fput_005f2);
/* 3974 */                                 int _jspx_eval_c_005fchoose_005f16 = _jspx_th_c_005fchoose_005f16.doStartTag();
/* 3975 */                                 if (_jspx_eval_c_005fchoose_005f16 != 0) {
/*      */                                   for (;;) {
/* 3977 */                                     out.write(10);
/*      */                                     
/* 3979 */                                     WhenTag _jspx_th_c_005fwhen_005f16 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3980 */                                     _jspx_th_c_005fwhen_005f16.setPageContext(_jspx_page_context);
/* 3981 */                                     _jspx_th_c_005fwhen_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/*      */                                     
/* 3983 */                                     _jspx_th_c_005fwhen_005f16.setTest("${param.method =='showScheduleReports'}");
/* 3984 */                                     int _jspx_eval_c_005fwhen_005f16 = _jspx_th_c_005fwhen_005f16.doStartTag();
/* 3985 */                                     if (_jspx_eval_c_005fwhen_005f16 != 0) {
/*      */                                       for (;;) {
/* 3987 */                                         out.write("\n       ");
/* 3988 */                                         out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3989 */                                         out.write(10);
/* 3990 */                                         out.write(32);
/* 3991 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f16.doAfterBody();
/* 3992 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3996 */                                     if (_jspx_th_c_005fwhen_005f16.doEndTag() == 5) {
/* 3997 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16); return;
/*      */                                     }
/*      */                                     
/* 4000 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 4001 */                                     out.write(10);
/* 4002 */                                     out.write(32);
/*      */                                     
/* 4004 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f16 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4005 */                                     _jspx_th_c_005fotherwise_005f16.setPageContext(_jspx_page_context);
/* 4006 */                                     _jspx_th_c_005fotherwise_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/* 4007 */                                     int _jspx_eval_c_005fotherwise_005f16 = _jspx_th_c_005fotherwise_005f16.doStartTag();
/* 4008 */                                     if (_jspx_eval_c_005fotherwise_005f16 != 0) {
/*      */                                       for (;;) {
/* 4010 */                                         out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/* 4011 */                                         out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 4012 */                                         out.write("\n\t </a>\n ");
/* 4013 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f16.doAfterBody();
/* 4014 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4018 */                                     if (_jspx_th_c_005fotherwise_005f16.doEndTag() == 5) {
/* 4019 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16); return;
/*      */                                     }
/*      */                                     
/* 4022 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16);
/* 4023 */                                     out.write(10);
/* 4024 */                                     out.write(32);
/* 4025 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f16.doAfterBody();
/* 4026 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4030 */                                 if (_jspx_th_c_005fchoose_005f16.doEndTag() == 5) {
/* 4031 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16); return;
/*      */                                 }
/*      */                                 
/* 4034 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16);
/* 4035 */                                 out.write("\n    </td>\n</tr> \n");
/*      */                               } else {
/* 4037 */                                 out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 4038 */                                 out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 4039 */                                 out.write("</a>\n     </td>\n </tr>   \n");
/*      */                                 
/* 4041 */                                 IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4042 */                                 _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 4043 */                                 _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                 
/* 4045 */                                 _jspx_th_c_005fif_005f1.setTest("${category!='LAMP'}");
/* 4046 */                                 int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 4047 */                                 if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                   for (;;) {
/* 4049 */                                     out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 4050 */                                     out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 4051 */                                     out.write("</a>\n\t  </td>\n  </tr>   \n");
/* 4052 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4053 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4057 */                                 if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4058 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                                 }
/*      */                                 
/* 4061 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4062 */                                 out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/* 4063 */                                 out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 4064 */                                 out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */                               }
/* 4066 */                               out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                               
/* 4068 */                               ChooseTag _jspx_th_c_005fchoose_005f17 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4069 */                               _jspx_th_c_005fchoose_005f17.setPageContext(_jspx_page_context);
/* 4070 */                               _jspx_th_c_005fchoose_005f17.setParent(_jspx_th_tiles_005fput_005f2);
/* 4071 */                               int _jspx_eval_c_005fchoose_005f17 = _jspx_th_c_005fchoose_005f17.doStartTag();
/* 4072 */                               if (_jspx_eval_c_005fchoose_005f17 != 0) {
/*      */                                 for (;;) {
/* 4074 */                                   out.write(10);
/*      */                                   
/* 4076 */                                   WhenTag _jspx_th_c_005fwhen_005f17 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4077 */                                   _jspx_th_c_005fwhen_005f17.setPageContext(_jspx_page_context);
/* 4078 */                                   _jspx_th_c_005fwhen_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/*      */                                   
/* 4080 */                                   _jspx_th_c_005fwhen_005f17.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 4081 */                                   int _jspx_eval_c_005fwhen_005f17 = _jspx_th_c_005fwhen_005f17.doStartTag();
/* 4082 */                                   if (_jspx_eval_c_005fwhen_005f17 != 0) {
/*      */                                     for (;;) {
/* 4084 */                                       out.write("\n        ");
/* 4085 */                                       out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 4086 */                                       out.write(10);
/* 4087 */                                       out.write(32);
/* 4088 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f17.doAfterBody();
/* 4089 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4093 */                                   if (_jspx_th_c_005fwhen_005f17.doEndTag() == 5) {
/* 4094 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17); return;
/*      */                                   }
/*      */                                   
/* 4097 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 4098 */                                   out.write(10);
/* 4099 */                                   out.write(32);
/*      */                                   
/* 4101 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f17 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4102 */                                   _jspx_th_c_005fotherwise_005f17.setPageContext(_jspx_page_context);
/* 4103 */                                   _jspx_th_c_005fotherwise_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/* 4104 */                                   int _jspx_eval_c_005fotherwise_005f17 = _jspx_th_c_005fotherwise_005f17.doStartTag();
/* 4105 */                                   if (_jspx_eval_c_005fotherwise_005f17 != 0) {
/*      */                                     for (;;) {
/* 4107 */                                       out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/* 4108 */                                       out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 4109 */                                       out.write("\n\t </a>\n ");
/* 4110 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f17.doAfterBody();
/* 4111 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4115 */                                   if (_jspx_th_c_005fotherwise_005f17.doEndTag() == 5) {
/* 4116 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17); return;
/*      */                                   }
/*      */                                   
/* 4119 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 4120 */                                   out.write(10);
/* 4121 */                                   out.write(32);
/* 4122 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f17.doAfterBody();
/* 4123 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4127 */                               if (_jspx_th_c_005fchoose_005f17.doEndTag() == 5) {
/* 4128 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17); return;
/*      */                               }
/*      */                               
/* 4131 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 4132 */                               out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                               
/* 4134 */                               ChooseTag _jspx_th_c_005fchoose_005f18 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4135 */                               _jspx_th_c_005fchoose_005f18.setPageContext(_jspx_page_context);
/* 4136 */                               _jspx_th_c_005fchoose_005f18.setParent(_jspx_th_tiles_005fput_005f2);
/* 4137 */                               int _jspx_eval_c_005fchoose_005f18 = _jspx_th_c_005fchoose_005f18.doStartTag();
/* 4138 */                               if (_jspx_eval_c_005fchoose_005f18 != 0) {
/*      */                                 for (;;) {
/* 4140 */                                   out.write(10);
/*      */                                   
/* 4142 */                                   WhenTag _jspx_th_c_005fwhen_005f18 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4143 */                                   _jspx_th_c_005fwhen_005f18.setPageContext(_jspx_page_context);
/* 4144 */                                   _jspx_th_c_005fwhen_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/*      */                                   
/* 4146 */                                   _jspx_th_c_005fwhen_005f18.setTest("${param.method!='showMailServerConfiguration'}");
/* 4147 */                                   int _jspx_eval_c_005fwhen_005f18 = _jspx_th_c_005fwhen_005f18.doStartTag();
/* 4148 */                                   if (_jspx_eval_c_005fwhen_005f18 != 0) {
/*      */                                     for (;;) {
/* 4150 */                                       out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 4151 */                                       out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 4152 */                                       out.write("\n    </a>    \n ");
/* 4153 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f18.doAfterBody();
/* 4154 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4158 */                                   if (_jspx_th_c_005fwhen_005f18.doEndTag() == 5) {
/* 4159 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18); return;
/*      */                                   }
/*      */                                   
/* 4162 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 4163 */                                   out.write(10);
/* 4164 */                                   out.write(32);
/*      */                                   
/* 4166 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f18 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4167 */                                   _jspx_th_c_005fotherwise_005f18.setPageContext(_jspx_page_context);
/* 4168 */                                   _jspx_th_c_005fotherwise_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/* 4169 */                                   int _jspx_eval_c_005fotherwise_005f18 = _jspx_th_c_005fotherwise_005f18.doStartTag();
/* 4170 */                                   if (_jspx_eval_c_005fotherwise_005f18 != 0) {
/*      */                                     for (;;) {
/* 4172 */                                       out.write(10);
/* 4173 */                                       out.write(9);
/* 4174 */                                       out.write(32);
/* 4175 */                                       out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 4176 */                                       out.write(10);
/* 4177 */                                       out.write(32);
/* 4178 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f18.doAfterBody();
/* 4179 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4183 */                                   if (_jspx_th_c_005fotherwise_005f18.doEndTag() == 5) {
/* 4184 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18); return;
/*      */                                   }
/*      */                                   
/* 4187 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18);
/* 4188 */                                   out.write(10);
/* 4189 */                                   out.write(32);
/* 4190 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f18.doAfterBody();
/* 4191 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4195 */                               if (_jspx_th_c_005fchoose_005f18.doEndTag() == 5) {
/* 4196 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18); return;
/*      */                               }
/*      */                               
/* 4199 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18);
/* 4200 */                               out.write("\n    </td>\n</tr>\n\n\n");
/* 4201 */                               if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 4202 */                                 out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                                 
/* 4204 */                                 ChooseTag _jspx_th_c_005fchoose_005f19 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4205 */                                 _jspx_th_c_005fchoose_005f19.setPageContext(_jspx_page_context);
/* 4206 */                                 _jspx_th_c_005fchoose_005f19.setParent(_jspx_th_tiles_005fput_005f2);
/* 4207 */                                 int _jspx_eval_c_005fchoose_005f19 = _jspx_th_c_005fchoose_005f19.doStartTag();
/* 4208 */                                 if (_jspx_eval_c_005fchoose_005f19 != 0) {
/*      */                                   for (;;) {
/* 4210 */                                     out.write(10);
/*      */                                     
/* 4212 */                                     WhenTag _jspx_th_c_005fwhen_005f19 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4213 */                                     _jspx_th_c_005fwhen_005f19.setPageContext(_jspx_page_context);
/* 4214 */                                     _jspx_th_c_005fwhen_005f19.setParent(_jspx_th_c_005fchoose_005f19);
/*      */                                     
/* 4216 */                                     _jspx_th_c_005fwhen_005f19.setTest("${param.method!='SMSServerConfiguration'}");
/* 4217 */                                     int _jspx_eval_c_005fwhen_005f19 = _jspx_th_c_005fwhen_005f19.doStartTag();
/* 4218 */                                     if (_jspx_eval_c_005fwhen_005f19 != 0) {
/*      */                                       for (;;) {
/* 4220 */                                         out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 4221 */                                         out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 4222 */                                         out.write("\n    </a>\n ");
/* 4223 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f19.doAfterBody();
/* 4224 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4228 */                                     if (_jspx_th_c_005fwhen_005f19.doEndTag() == 5) {
/* 4229 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19); return;
/*      */                                     }
/*      */                                     
/* 4232 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19);
/* 4233 */                                     out.write(10);
/* 4234 */                                     out.write(32);
/*      */                                     
/* 4236 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f19 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4237 */                                     _jspx_th_c_005fotherwise_005f19.setPageContext(_jspx_page_context);
/* 4238 */                                     _jspx_th_c_005fotherwise_005f19.setParent(_jspx_th_c_005fchoose_005f19);
/* 4239 */                                     int _jspx_eval_c_005fotherwise_005f19 = _jspx_th_c_005fotherwise_005f19.doStartTag();
/* 4240 */                                     if (_jspx_eval_c_005fotherwise_005f19 != 0) {
/*      */                                       for (;;) {
/* 4242 */                                         out.write("\n         ");
/* 4243 */                                         out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 4244 */                                         out.write(10);
/* 4245 */                                         out.write(32);
/* 4246 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f19.doAfterBody();
/* 4247 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4251 */                                     if (_jspx_th_c_005fotherwise_005f19.doEndTag() == 5) {
/* 4252 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19); return;
/*      */                                     }
/*      */                                     
/* 4255 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19);
/* 4256 */                                     out.write(10);
/* 4257 */                                     out.write(32);
/* 4258 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f19.doAfterBody();
/* 4259 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4263 */                                 if (_jspx_th_c_005fchoose_005f19.doEndTag() == 5) {
/* 4264 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19); return;
/*      */                                 }
/*      */                                 
/* 4267 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19);
/* 4268 */                                 out.write("\n    </td>\n</tr>\n");
/*      */                               }
/* 4270 */                               out.write("\n\n\n ");
/*      */                               
/* 4272 */                               if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                               {
/*      */ 
/* 4275 */                                 out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                                 
/* 4277 */                                 ChooseTag _jspx_th_c_005fchoose_005f20 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4278 */                                 _jspx_th_c_005fchoose_005f20.setPageContext(_jspx_page_context);
/* 4279 */                                 _jspx_th_c_005fchoose_005f20.setParent(_jspx_th_tiles_005fput_005f2);
/* 4280 */                                 int _jspx_eval_c_005fchoose_005f20 = _jspx_th_c_005fchoose_005f20.doStartTag();
/* 4281 */                                 if (_jspx_eval_c_005fchoose_005f20 != 0) {
/*      */                                   for (;;) {
/* 4283 */                                     out.write(10);
/*      */                                     
/* 4285 */                                     WhenTag _jspx_th_c_005fwhen_005f20 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4286 */                                     _jspx_th_c_005fwhen_005f20.setPageContext(_jspx_page_context);
/* 4287 */                                     _jspx_th_c_005fwhen_005f20.setParent(_jspx_th_c_005fchoose_005f20);
/*      */                                     
/* 4289 */                                     _jspx_th_c_005fwhen_005f20.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 4290 */                                     int _jspx_eval_c_005fwhen_005f20 = _jspx_th_c_005fwhen_005f20.doStartTag();
/* 4291 */                                     if (_jspx_eval_c_005fwhen_005f20 != 0) {
/*      */                                       for (;;) {
/* 4293 */                                         out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 4294 */                                         out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 4295 */                                         out.write("\n    </a>\n ");
/* 4296 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f20.doAfterBody();
/* 4297 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4301 */                                     if (_jspx_th_c_005fwhen_005f20.doEndTag() == 5) {
/* 4302 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20); return;
/*      */                                     }
/*      */                                     
/* 4305 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20);
/* 4306 */                                     out.write(10);
/* 4307 */                                     out.write(32);
/*      */                                     
/* 4309 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f20 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4310 */                                     _jspx_th_c_005fotherwise_005f20.setPageContext(_jspx_page_context);
/* 4311 */                                     _jspx_th_c_005fotherwise_005f20.setParent(_jspx_th_c_005fchoose_005f20);
/* 4312 */                                     int _jspx_eval_c_005fotherwise_005f20 = _jspx_th_c_005fotherwise_005f20.doStartTag();
/* 4313 */                                     if (_jspx_eval_c_005fotherwise_005f20 != 0) {
/*      */                                       for (;;) {
/* 4315 */                                         out.write(10);
/* 4316 */                                         out.write(9);
/* 4317 */                                         out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 4318 */                                         out.write(10);
/* 4319 */                                         out.write(32);
/* 4320 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f20.doAfterBody();
/* 4321 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4325 */                                     if (_jspx_th_c_005fotherwise_005f20.doEndTag() == 5) {
/* 4326 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20); return;
/*      */                                     }
/*      */                                     
/* 4329 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20);
/* 4330 */                                     out.write(10);
/* 4331 */                                     out.write(32);
/* 4332 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f20.doAfterBody();
/* 4333 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4337 */                                 if (_jspx_th_c_005fchoose_005f20.doEndTag() == 5) {
/* 4338 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20); return;
/*      */                                 }
/*      */                                 
/* 4341 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20);
/* 4342 */                                 out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                                 
/* 4344 */                                 ChooseTag _jspx_th_c_005fchoose_005f21 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4345 */                                 _jspx_th_c_005fchoose_005f21.setPageContext(_jspx_page_context);
/* 4346 */                                 _jspx_th_c_005fchoose_005f21.setParent(_jspx_th_tiles_005fput_005f2);
/* 4347 */                                 int _jspx_eval_c_005fchoose_005f21 = _jspx_th_c_005fchoose_005f21.doStartTag();
/* 4348 */                                 if (_jspx_eval_c_005fchoose_005f21 != 0) {
/*      */                                   for (;;) {
/* 4350 */                                     out.write(10);
/*      */                                     
/* 4352 */                                     WhenTag _jspx_th_c_005fwhen_005f21 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4353 */                                     _jspx_th_c_005fwhen_005f21.setPageContext(_jspx_page_context);
/* 4354 */                                     _jspx_th_c_005fwhen_005f21.setParent(_jspx_th_c_005fchoose_005f21);
/*      */                                     
/* 4356 */                                     _jspx_th_c_005fwhen_005f21.setTest("${uri !='/Upload.do'}");
/* 4357 */                                     int _jspx_eval_c_005fwhen_005f21 = _jspx_th_c_005fwhen_005f21.doStartTag();
/* 4358 */                                     if (_jspx_eval_c_005fwhen_005f21 != 0) {
/*      */                                       for (;;) {
/* 4360 */                                         out.write("   \n        ");
/*      */                                         
/* 4362 */                                         AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 4363 */                                         _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 4364 */                                         _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_c_005fwhen_005f21);
/*      */                                         
/* 4366 */                                         _jspx_th_am_005fadminlink_005f0.setHref("/Upload.do");
/*      */                                         
/* 4368 */                                         _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 4369 */                                         int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 4370 */                                         if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 4371 */                                           if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 4372 */                                             out = _jspx_page_context.pushBody();
/* 4373 */                                             _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 4374 */                                             _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4377 */                                             out.write("\n           ");
/* 4378 */                                             out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 4379 */                                             out.write("\n            ");
/* 4380 */                                             int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 4381 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4384 */                                           if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 4385 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4388 */                                         if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 4389 */                                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                         }
/*      */                                         
/* 4392 */                                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 4393 */                                         out.write(10);
/* 4394 */                                         out.write(10);
/* 4395 */                                         out.write(32);
/* 4396 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f21.doAfterBody();
/* 4397 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4401 */                                     if (_jspx_th_c_005fwhen_005f21.doEndTag() == 5) {
/* 4402 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21); return;
/*      */                                     }
/*      */                                     
/* 4405 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21);
/* 4406 */                                     out.write(10);
/* 4407 */                                     out.write(32);
/*      */                                     
/* 4409 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f21 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4410 */                                     _jspx_th_c_005fotherwise_005f21.setPageContext(_jspx_page_context);
/* 4411 */                                     _jspx_th_c_005fotherwise_005f21.setParent(_jspx_th_c_005fchoose_005f21);
/* 4412 */                                     int _jspx_eval_c_005fotherwise_005f21 = _jspx_th_c_005fotherwise_005f21.doStartTag();
/* 4413 */                                     if (_jspx_eval_c_005fotherwise_005f21 != 0) {
/*      */                                       for (;;) {
/* 4415 */                                         out.write(10);
/* 4416 */                                         out.write(9);
/* 4417 */                                         out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 4418 */                                         out.write(10);
/* 4419 */                                         out.write(32);
/* 4420 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f21.doAfterBody();
/* 4421 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4425 */                                     if (_jspx_th_c_005fotherwise_005f21.doEndTag() == 5) {
/* 4426 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21); return;
/*      */                                     }
/*      */                                     
/* 4429 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21);
/* 4430 */                                     out.write(10);
/* 4431 */                                     out.write(32);
/* 4432 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f21.doAfterBody();
/* 4433 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4437 */                                 if (_jspx_th_c_005fchoose_005f21.doEndTag() == 5) {
/* 4438 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21); return;
/*      */                                 }
/*      */                                 
/* 4441 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21);
/* 4442 */                                 out.write("\n    </td>\n</tr>\n \n ");
/*      */                               }
/*      */                               
/*      */ 
/* 4446 */                               out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                               
/* 4448 */                               ChooseTag _jspx_th_c_005fchoose_005f22 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4449 */                               _jspx_th_c_005fchoose_005f22.setPageContext(_jspx_page_context);
/* 4450 */                               _jspx_th_c_005fchoose_005f22.setParent(_jspx_th_tiles_005fput_005f2);
/* 4451 */                               int _jspx_eval_c_005fchoose_005f22 = _jspx_th_c_005fchoose_005f22.doStartTag();
/* 4452 */                               if (_jspx_eval_c_005fchoose_005f22 != 0) {
/*      */                                 for (;;) {
/* 4454 */                                   out.write(10);
/*      */                                   
/* 4456 */                                   WhenTag _jspx_th_c_005fwhen_005f22 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4457 */                                   _jspx_th_c_005fwhen_005f22.setPageContext(_jspx_page_context);
/* 4458 */                                   _jspx_th_c_005fwhen_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/*      */                                   
/* 4460 */                                   _jspx_th_c_005fwhen_005f22.setTest("${uri !='/admin/userconfiguration.do'}");
/* 4461 */                                   int _jspx_eval_c_005fwhen_005f22 = _jspx_th_c_005fwhen_005f22.doStartTag();
/* 4462 */                                   if (_jspx_eval_c_005fwhen_005f22 != 0) {
/*      */                                     for (;;) {
/* 4464 */                                       out.write("\n    \n        ");
/*      */                                       
/* 4466 */                                       AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 4467 */                                       _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 4468 */                                       _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f22);
/*      */                                       
/* 4470 */                                       _jspx_th_am_005fadminlink_005f1.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                                       
/* 4472 */                                       _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 4473 */                                       int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 4474 */                                       if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 4475 */                                         if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 4476 */                                           out = _jspx_page_context.pushBody();
/* 4477 */                                           _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 4478 */                                           _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 4481 */                                           out.write("\n       ");
/* 4482 */                                           out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 4483 */                                           out.write("\n        ");
/* 4484 */                                           int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 4485 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 4488 */                                         if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 4489 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 4492 */                                       if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 4493 */                                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                                       }
/*      */                                       
/* 4496 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 4497 */                                       out.write(10);
/* 4498 */                                       out.write(10);
/* 4499 */                                       out.write(32);
/* 4500 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f22.doAfterBody();
/* 4501 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4505 */                                   if (_jspx_th_c_005fwhen_005f22.doEndTag() == 5) {
/* 4506 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22); return;
/*      */                                   }
/*      */                                   
/* 4509 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22);
/* 4510 */                                   out.write(10);
/* 4511 */                                   out.write(32);
/*      */                                   
/* 4513 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f22 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4514 */                                   _jspx_th_c_005fotherwise_005f22.setPageContext(_jspx_page_context);
/* 4515 */                                   _jspx_th_c_005fotherwise_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/* 4516 */                                   int _jspx_eval_c_005fotherwise_005f22 = _jspx_th_c_005fotherwise_005f22.doStartTag();
/* 4517 */                                   if (_jspx_eval_c_005fotherwise_005f22 != 0) {
/*      */                                     for (;;) {
/* 4519 */                                       out.write(10);
/* 4520 */                                       out.write(9);
/* 4521 */                                       out.write(32);
/* 4522 */                                       out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 4523 */                                       out.write(10);
/* 4524 */                                       out.write(32);
/* 4525 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f22.doAfterBody();
/* 4526 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4530 */                                   if (_jspx_th_c_005fotherwise_005f22.doEndTag() == 5) {
/* 4531 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22); return;
/*      */                                   }
/*      */                                   
/* 4534 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22);
/* 4535 */                                   out.write(10);
/* 4536 */                                   out.write(32);
/* 4537 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f22.doAfterBody();
/* 4538 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4542 */                               if (_jspx_th_c_005fchoose_005f22.doEndTag() == 5) {
/* 4543 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22); return;
/*      */                               }
/*      */                               
/* 4546 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22);
/* 4547 */                               out.write("\n    </td>\n</tr>\n   \n\n ");
/* 4548 */                               if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 4549 */                                 out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                                 
/* 4551 */                                 ChooseTag _jspx_th_c_005fchoose_005f23 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4552 */                                 _jspx_th_c_005fchoose_005f23.setPageContext(_jspx_page_context);
/* 4553 */                                 _jspx_th_c_005fchoose_005f23.setParent(_jspx_th_tiles_005fput_005f2);
/* 4554 */                                 int _jspx_eval_c_005fchoose_005f23 = _jspx_th_c_005fchoose_005f23.doStartTag();
/* 4555 */                                 if (_jspx_eval_c_005fchoose_005f23 != 0) {
/*      */                                   for (;;) {
/* 4557 */                                     out.write("\n   ");
/*      */                                     
/* 4559 */                                     WhenTag _jspx_th_c_005fwhen_005f23 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4560 */                                     _jspx_th_c_005fwhen_005f23.setPageContext(_jspx_page_context);
/* 4561 */                                     _jspx_th_c_005fwhen_005f23.setParent(_jspx_th_c_005fchoose_005f23);
/*      */                                     
/* 4563 */                                     _jspx_th_c_005fwhen_005f23.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 4564 */                                     int _jspx_eval_c_005fwhen_005f23 = _jspx_th_c_005fwhen_005f23.doStartTag();
/* 4565 */                                     if (_jspx_eval_c_005fwhen_005f23 != 0) {
/*      */                                       for (;;) {
/* 4567 */                                         out.write("\n    ");
/*      */                                         
/* 4569 */                                         AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 4570 */                                         _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 4571 */                                         _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f23);
/*      */                                         
/* 4573 */                                         _jspx_th_am_005fadminlink_005f2.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                                         
/* 4575 */                                         _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 4576 */                                         int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 4577 */                                         if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 4578 */                                           if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 4579 */                                             out = _jspx_page_context.pushBody();
/* 4580 */                                             _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 4581 */                                             _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4584 */                                             out.write(10);
/* 4585 */                                             out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 4586 */                                             out.write("\n    ");
/* 4587 */                                             int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 4588 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4591 */                                           if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 4592 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4595 */                                         if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 4596 */                                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                                         }
/*      */                                         
/* 4599 */                                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 4600 */                                         out.write("\n   ");
/* 4601 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f23.doAfterBody();
/* 4602 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4606 */                                     if (_jspx_th_c_005fwhen_005f23.doEndTag() == 5) {
/* 4607 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f23); return;
/*      */                                     }
/*      */                                     
/* 4610 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f23);
/* 4611 */                                     out.write("\n   ");
/*      */                                     
/* 4613 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f23 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4614 */                                     _jspx_th_c_005fotherwise_005f23.setPageContext(_jspx_page_context);
/* 4615 */                                     _jspx_th_c_005fotherwise_005f23.setParent(_jspx_th_c_005fchoose_005f23);
/* 4616 */                                     int _jspx_eval_c_005fotherwise_005f23 = _jspx_th_c_005fotherwise_005f23.doStartTag();
/* 4617 */                                     if (_jspx_eval_c_005fotherwise_005f23 != 0) {
/*      */                                       for (;;) {
/* 4619 */                                         out.write(10);
/* 4620 */                                         out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 4621 */                                         out.write("\n   ");
/* 4622 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f23.doAfterBody();
/* 4623 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4627 */                                     if (_jspx_th_c_005fotherwise_005f23.doEndTag() == 5) {
/* 4628 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f23); return;
/*      */                                     }
/*      */                                     
/* 4631 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f23);
/* 4632 */                                     out.write(10);
/* 4633 */                                     out.write(32);
/* 4634 */                                     out.write(32);
/* 4635 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f23.doAfterBody();
/* 4636 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4640 */                                 if (_jspx_th_c_005fchoose_005f23.doEndTag() == 5) {
/* 4641 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f23); return;
/*      */                                 }
/*      */                                 
/* 4644 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f23);
/* 4645 */                                 out.write("\n </td>\n</tr>\n  ");
/*      */                               }
/* 4647 */                               out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                               
/* 4649 */                               ChooseTag _jspx_th_c_005fchoose_005f24 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4650 */                               _jspx_th_c_005fchoose_005f24.setPageContext(_jspx_page_context);
/* 4651 */                               _jspx_th_c_005fchoose_005f24.setParent(_jspx_th_tiles_005fput_005f2);
/* 4652 */                               int _jspx_eval_c_005fchoose_005f24 = _jspx_th_c_005fchoose_005f24.doStartTag();
/* 4653 */                               if (_jspx_eval_c_005fchoose_005f24 != 0) {
/*      */                                 for (;;) {
/* 4655 */                                   out.write("\n   ");
/*      */                                   
/* 4657 */                                   WhenTag _jspx_th_c_005fwhen_005f24 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4658 */                                   _jspx_th_c_005fwhen_005f24.setPageContext(_jspx_page_context);
/* 4659 */                                   _jspx_th_c_005fwhen_005f24.setParent(_jspx_th_c_005fchoose_005f24);
/*      */                                   
/* 4661 */                                   _jspx_th_c_005fwhen_005f24.setTest("${param.method!='showDataCleanUp'}");
/* 4662 */                                   int _jspx_eval_c_005fwhen_005f24 = _jspx_th_c_005fwhen_005f24.doStartTag();
/* 4663 */                                   if (_jspx_eval_c_005fwhen_005f24 != 0) {
/*      */                                     for (;;) {
/* 4665 */                                       out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 4666 */                                       out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 4667 */                                       out.write("\n    </a>\n   ");
/* 4668 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f24.doAfterBody();
/* 4669 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4673 */                                   if (_jspx_th_c_005fwhen_005f24.doEndTag() == 5) {
/* 4674 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f24); return;
/*      */                                   }
/*      */                                   
/* 4677 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f24);
/* 4678 */                                   out.write("\n   ");
/*      */                                   
/* 4680 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f24 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4681 */                                   _jspx_th_c_005fotherwise_005f24.setPageContext(_jspx_page_context);
/* 4682 */                                   _jspx_th_c_005fotherwise_005f24.setParent(_jspx_th_c_005fchoose_005f24);
/* 4683 */                                   int _jspx_eval_c_005fotherwise_005f24 = _jspx_th_c_005fotherwise_005f24.doStartTag();
/* 4684 */                                   if (_jspx_eval_c_005fotherwise_005f24 != 0) {
/*      */                                     for (;;) {
/* 4686 */                                       out.write(10);
/* 4687 */                                       out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 4688 */                                       out.write("\n   ");
/* 4689 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f24.doAfterBody();
/* 4690 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4694 */                                   if (_jspx_th_c_005fotherwise_005f24.doEndTag() == 5) {
/* 4695 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f24); return;
/*      */                                   }
/*      */                                   
/* 4698 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f24);
/* 4699 */                                   out.write(10);
/* 4700 */                                   out.write(32);
/* 4701 */                                   out.write(32);
/* 4702 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f24.doAfterBody();
/* 4703 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4707 */                               if (_jspx_th_c_005fchoose_005f24.doEndTag() == 5) {
/* 4708 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f24); return;
/*      */                               }
/*      */                               
/* 4711 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f24);
/* 4712 */                               out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 4713 */                               out.write("\n        ");
/*      */                             }
/* 4715 */                             out.write(10);
/* 4716 */                             out.write(9);
/* 4717 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 4718 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4721 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 4722 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4725 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 4726 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 4729 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 4730 */                         out.write(32);
/* 4731 */                         out.write(32);
/* 4732 */                         out.write(10);
/* 4733 */                         out.write(9);
/* 4734 */                         out.write(10);
/*      */                         
/* 4736 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 4737 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 4738 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 4740 */                         _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                         
/* 4742 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 4743 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 4744 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 4745 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4746 */                             out = _jspx_page_context.pushBody();
/* 4747 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 4748 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4751 */                             out.write(" \n\n<table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\" display:none\" class=\"itadmin-hide\">\n        <tr>\n         ");
/*      */                             
/* 4753 */                             if (EnterpriseUtil.isAdminServer())
/*      */                             {
/*      */ 
/* 4756 */                               out.write("\n          <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 4757 */                               out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getEnterpriseAdminPage(request));
/* 4758 */                               out.write(" &gt; <span class=\"bcactive\">\n\t\t");
/* 4759 */                               if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 4761 */                               out.write("\n\t</span></td>\n         ");
/*      */                             }
/*      */                             else
/*      */                             {
/* 4765 */                               out.write("\n                 <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 4766 */                               out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 4767 */                               out.write(" &gt; <span class=\"bcactive\">\n\t\t");
/* 4768 */                               if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 4770 */                               out.write("\n\t\t</span></td>\n         ");
/*      */                             }
/* 4772 */                             out.write("\n\n        </tr>\n</table>\n\t");
/*      */                             
/* 4774 */                             ChooseTag _jspx_th_c_005fchoose_005f25 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4775 */                             _jspx_th_c_005fchoose_005f25.setPageContext(_jspx_page_context);
/* 4776 */                             _jspx_th_c_005fchoose_005f25.setParent(_jspx_th_tiles_005fput_005f3);
/* 4777 */                             int _jspx_eval_c_005fchoose_005f25 = _jspx_th_c_005fchoose_005f25.doStartTag();
/* 4778 */                             if (_jspx_eval_c_005fchoose_005f25 != 0) {
/*      */                               for (;;) {
/* 4780 */                                 out.write(10);
/* 4781 */                                 out.write(9);
/* 4782 */                                 out.write(9);
/* 4783 */                                 if (_jspx_meth_c_005fwhen_005f25(_jspx_th_c_005fchoose_005f25, _jspx_page_context))
/*      */                                   return;
/* 4785 */                                 out.write(10);
/* 4786 */                                 out.write(9);
/* 4787 */                                 out.write(9);
/*      */                                 
/* 4789 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f25 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4790 */                                 _jspx_th_c_005fotherwise_005f25.setPageContext(_jspx_page_context);
/* 4791 */                                 _jspx_th_c_005fotherwise_005f25.setParent(_jspx_th_c_005fchoose_005f25);
/* 4792 */                                 int _jspx_eval_c_005fotherwise_005f25 = _jspx_th_c_005fotherwise_005f25.doStartTag();
/* 4793 */                                 if (_jspx_eval_c_005fotherwise_005f25 != 0) {
/*      */                                   for (;;) {
/* 4795 */                                     out.write("\n\t\t\t<div style=\"padding:0px 0;clear:both; margin-top:3px;\"></div> \n\t\t\t <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"\" style=\"padding-bottom:10px\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"buttons btn_back\" onclick=\"fnOpenNewWindowWithHeightWidthPlacement('/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general&reloadParent=true',1150,600,300,100)\" align=\"right\"><b>");
/* 4796 */                                     if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fotherwise_005f25, _jspx_page_context))
/*      */                                       return;
/* 4798 */                                     out.write("</b></td>");
/* 4799 */                                     out.write("\n\t\t\t   </tr>\n\t\t\t </table>\t\t\t\n\t\t \t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"lrtbdarkborder\">\n\t\t  \t\t<tr>\n\t\t    \t\t<td width=\"100%\" align=\"left\" colspan=\"6\" class=\"tableheadingbborder itadmin-hide\">\t\n\t\t\t  \t\t\t");
/* 4800 */                                     if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fotherwise_005f25, _jspx_page_context))
/*      */                                       return;
/* 4802 */                                     out.write(9);
/* 4803 */                                     out.write(9);
/* 4804 */                                     out.write("\n\t\t\t\t\t</td>\n\t\t  \t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t    <td width=\"20%\" class=\"columnheading itadmin-decor\">");
/* 4805 */                                     if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fotherwise_005f25, _jspx_page_context))
/*      */                                       return;
/* 4807 */                                     out.write("</td> ");
/* 4808 */                                     out.write("\n\t\t\t\t    <td width=\"30%\" class=\"columnheading itadmin-decor\">");
/* 4809 */                                     if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fotherwise_005f25, _jspx_page_context))
/*      */                                       return;
/* 4811 */                                     out.write("</td> ");
/* 4812 */                                     out.write("\n\t\t\t\t    <td width=\"12%\" align=\"center\" class=\"columnheading itadmin-decor\" >");
/* 4813 */                                     if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fotherwise_005f25, _jspx_page_context))
/*      */                                       return;
/* 4815 */                                     out.write("</td>  ");
/* 4816 */                                     out.write("\n\t\t\t\t    <td width=\"14%\" align=\"center\" class=\"columnheading itadmin-decor\" >");
/* 4817 */                                     if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_c_005fotherwise_005f25, _jspx_page_context))
/*      */                                       return;
/* 4819 */                                     out.write("</td>  ");
/* 4820 */                                     out.write("\n\t\t\t\t    <td width=\"14%\" align=\"center\" class=\"columnheading itadmin-decor\" >");
/* 4821 */                                     if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_c_005fotherwise_005f25, _jspx_page_context))
/*      */                                       return;
/* 4823 */                                     out.write("</td>  ");
/* 4824 */                                     out.write("\n\t\t\t\t    <td width=\"10%\" align=\"center\" class=\"columnheading itadmin-decor\">&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */                                     
/* 4826 */                                     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4827 */                                     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4828 */                                     _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fotherwise_005f25);
/*      */                                     
/* 4830 */                                     _jspx_th_c_005fforEach_005f0.setItems("${diagnosticinfo}");
/*      */                                     
/* 4832 */                                     _jspx_th_c_005fforEach_005f0.setVar("diagnosticrow");
/*      */                                     
/* 4834 */                                     _jspx_th_c_005fforEach_005f0.setVarStatus("rowstatus");
/* 4835 */                                     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                     try {
/* 4837 */                                       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4838 */                                       if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                         for (;;) {
/* 4840 */                                           out.write("\n\t\t\t\t\t\n\t\t\t\t\t<tr  height=\"50\" class='");
/* 4841 */                                           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4843 */                                           out.write("'>\n\t\t\t\t\t\t<td  width=\"20%\"   class=\"whitegrayborder\">\n\t\t\t\t\t\t\t");
/* 4844 */                                           if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4846 */                                           out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td  width=\"30%\"   class=\"whitegrayborder\">\n\t\t\t\t\t\t\t<div id=\"");
/* 4847 */                                           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4849 */                                           out.write("_description\">");
/* 4850 */                                           if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4852 */                                           out.write("</div>\n\t\t\t\t\t\t</td>\t\n\t\t\t\t\t\t<td  width=\"12%\"  align=\"center\" class=\"whitegrayborder\" >\n\t\t\t\t\t\t\t<div style=\"display: block;\" id=\"view_pollinterval_");
/* 4853 */                                           if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4855 */                                           out.write("\">\n\t\t\t\t\t\t\t\t");
/* 4856 */                                           if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4858 */                                           out.write("\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div style=\"display: none; \" id=\"edit_pollinterval_");
/* 4859 */                                           if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4861 */                                           out.write("\">\n\t\t\t\t\t\t\t\t<input id=\"pollinterval_");
/* 4862 */                                           if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4864 */                                           out.write("\" size=\"3\" type=\"text\" value=\"");
/* 4865 */                                           if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4867 */                                           out.write("\" >\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width=\"14%\"  align=\"center\" class=\"whitegrayborder\" >\n\t\t\t\t\t\t\t");
/* 4868 */                                           if (_jspx_meth_c_005fchoose_005f26(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4870 */                                           out.write("\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td  width=\"14%\"  align=\"center\" class=\"whitegrayborder\" >\n\t\t\t\t\t\t\t<div style=\"display: block;\" id=\"view_criticalvalue_");
/* 4871 */                                           if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4873 */                                           out.write("\">\n\t\t\t\t\t\t\t\t");
/* 4874 */                                           if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4876 */                                           out.write("&nbsp;");
/* 4877 */                                           if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4879 */                                           out.write("\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div style=\"display: none; \" id=\"edit_criticalvalue_");
/* 4880 */                                           if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4882 */                                           out.write("\">\n\t\t\t\t\t\t\t\t<input id=\"criticalvalue_");
/* 4883 */                                           if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4885 */                                           out.write("\" size=\"3\" type=\"text\" value=\"");
/* 4886 */                                           if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4888 */                                           out.write("\" >&nbsp;");
/* 4889 */                                           if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4891 */                                           out.write("\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td  width=\"10%\" align=\"center\" class=\"whitegrayborder\" >\n\t\t\t\t\t\t\t<div style=\"display: block;\" id=\"edit_icon_");
/* 4892 */                                           if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4894 */                                           out.write("\">\n\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"editDiagnosticDetails(");
/* 4895 */                                           if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4897 */                                           out.write(")\"> <img src=\"/images/icon_edit.gif\" border=\"0\"></a>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div style=\"display: none;\" id=\"save_icon_");
/* 4898 */                                           if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4900 */                                           out.write("\">\n\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"saveDiagnosticDetails('");
/* 4901 */                                           if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4903 */                                           out.write(39);
/* 4904 */                                           out.write(44);
/* 4905 */                                           out.write(39);
/* 4906 */                                           if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4908 */                                           out.write(39);
/* 4909 */                                           out.write(44);
/* 4910 */                                           out.write(39);
/* 4911 */                                           if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4913 */                                           out.write("')\"> <img title=\"");
/* 4914 */                                           out.print(FormatUtil.getString("am.webclient.admintab.opmanager.save"));
/* 4915 */                                           out.write("\" src=\"/images/icon_customfield_save.gif\"  border=\"0\"></a>\n\t\t\t\t\t\t\t\t&nbsp;&nbsp;\n\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"closeDiagnosticDetails(");
/* 4916 */                                           if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 4918 */                                           out.write(")\"> <img title=\"");
/* 4919 */                                           out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 4920 */                                           out.write("\" src=\"/images/icon_customfield_delete.gif\" border=\"0\"></a>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\t\n\t\t\t\t");
/* 4921 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 4922 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4926 */                                       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4934 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 4930 */                                         int tmp16484_16483 = 0; int[] tmp16484_16481 = _jspx_push_body_count_c_005fforEach_005f0; int tmp16486_16485 = tmp16484_16481[tmp16484_16483];tmp16484_16481[tmp16484_16483] = (tmp16486_16485 - 1); if (tmp16486_16485 <= 0) break;
/* 4931 */                                         out = _jspx_page_context.popBody(); }
/* 4932 */                                       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                     } finally {
/* 4934 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4935 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                     }
/* 4937 */                                     out.write("\n\t\t\t</table>\t\n\t\t");
/* 4938 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f25.doAfterBody();
/* 4939 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4943 */                                 if (_jspx_th_c_005fotherwise_005f25.doEndTag() == 5) {
/* 4944 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f25); return;
/*      */                                 }
/*      */                                 
/* 4947 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f25);
/* 4948 */                                 out.write(10);
/* 4949 */                                 out.write(9);
/* 4950 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f25.doAfterBody();
/* 4951 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4955 */                             if (_jspx_th_c_005fchoose_005f25.doEndTag() == 5) {
/* 4956 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f25); return;
/*      */                             }
/*      */                             
/* 4959 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f25);
/* 4960 */                             out.write(9);
/* 4961 */                             out.write(10);
/* 4962 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 4963 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4966 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4967 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4970 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4971 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 4974 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 4975 */                         out.write(32);
/* 4976 */                         out.write(32);
/* 4977 */                         out.write(10);
/* 4978 */                         if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 4980 */                         out.write(10);
/* 4981 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4982 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 4986 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4987 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 4990 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4991 */                       out.write("\n\n<script>\n\tfunction editDiagnosticDetails(id){\n\t\tvar flag = '");
/* 4992 */                       out.print(DBUtil.getGlobalConfigValueasBoolean("selfMonitoring"));
/* 4993 */                       out.write("'\n\t\tif (flag.toLowerCase() == \"false\") {\n\t\t\talert('");
/* 4994 */                       out.print(FormatUtil.getString("am.webclient.setting.self.monitoring.edit.disable.js.text"));
/* 4995 */                       out.write("'); // NO I18N\n\t\t\treturn;\n\t\t}\n\t\t$(\"#view_pollinterval_\"+id).hide();\n\t\t$(\"#view_pollcount_\"+id).hide();\n\t\t$(\"#view_criticalvalue_\"+id).hide();\n\t\t$(\"#edit_icon_\"+id).hide();\n\t\t\n\t\t$(\"#edit_pollinterval_\"+id).show();\n\t\t$(\"#edit_pollcount_\"+id).show();\n\t\t$(\"#edit_criticalvalue_\"+id).show();\n\t\t$(\"#save_icon_\"+id).show();\n\t}\n\t\n\tfunction closeDiagnosticDetails(id,update,pollinterval,pollcount,criticalvalue,units){\n\t\t$(\"#view_pollinterval_\"+id).show();\n\t\t$(\"#view_pollcount_\"+id).show();\n\t\t$(\"#view_criticalvalue_\"+id).show();\n\t\t$(\"#edit_icon_\"+id).show();\n\t\t\n\t\t$(\"#edit_pollinterval_\"+id).hide();\n\t\t$(\"#edit_pollcount_\"+id).hide();\n\t\t$(\"#edit_criticalvalue_\"+id).hide();\n\t\t$(\"#save_icon_\"+id).hide();\n\t\t\n\t\tif(update){\n\t\t\t$(\"#view_pollinterval_\"+id).html(pollinterval);\n\t\t\t$(\"#view_pollcount_\"+id).html(pollcount);\n\t\t\t$(\"#view_criticalvalue_\"+id).html(criticalvalue+\"&nbsp;\"+units);\n\t\t}\n\t}\n\t\n\tfunction saveDiagnosticDetails(id,name,units){\n\t\t\n\t\tvar pollinterval = jQuery(\"#pollinterval_\"+id).val()\t\t// NO I18N\n\t\tvar pollcount = '1'\n");
/* 4996 */                       out.write("\t\tvar criticalvalue = jQuery(\"#criticalvalue_\"+id).val()\t\t// NO I18N\n\t\t\n\t\tif(name != 'SYNCINGDELAY'){\n\t\tpollcount = jQuery(\"#pollcount_\"+id).val()\t\t\t// NO I18N\n\t\t}\n\t\t\n\t\tif(trimAll(pollinterval) == '' || !(isPositiveInteger(pollinterval)) || pollinterval =='0' )\n\t\t{\n\t        alert(\"");
/* 4997 */                       out.print(FormatUtil.getString("am.webclient.diagnostic.pollinterval.integer.validation.text"));
/* 4998 */                       out.write("\");\n\t\t\treturn;\n\t\t}\n\t\t\n\t\tif(pollinterval < 5){\n\t\t\t alert(\"");
/* 4999 */                       out.print(FormatUtil.getString("am.webclient.diagnostic.pollinterval.validation.minimumvalue.text"));
/* 5000 */                       out.write("\");\n\t\t\t return;\n\t\t}\n\t\t\n\t\tif(trimAll(pollcount) == '' || !(isPositiveInteger(pollcount)) || pollcount =='0' )\n\t\t{\n\t        alert(\"");
/* 5001 */                       out.print(FormatUtil.getString("am.webclient.diagnostic.consecutivepolls.integer.validation.text"));
/* 5002 */                       out.write("\");\n\t\t\treturn;\n\t\t}\n\t\t\n\t\tif(trimAll(criticalvalue) == '' || criticalvalue == 'null'){\n\t\t\talert(\"");
/* 5003 */                       out.print(FormatUtil.getString("am.webclient.diagnostic.critical.threshold.empty.text"));
/* 5004 */                       out.write("\");\n\t\t\treturn;\n\t\t}\n\t\tif(!isPositiveInteger(criticalvalue) || criticalvalue==0 || (criticalvalue > 100 && name != 'SYNCINGDELAY')){\n\t\t\tif( name == 'SYNCINGDELAY'){\n\t\t\t\talert(\"");
/* 5005 */                       out.print(FormatUtil.getString("am.webclient.diagnostic.synchingdelay.validation.value.text"));
/* 5006 */                       out.write("\");\n\t\t\t}else{\n\t\t\t\talert(\"");
/* 5007 */                       out.print(FormatUtil.getString("am.webclient.diagnostic.threshold.validation.value.text"));
/* 5008 */                       out.write("\");\n\t\t\t}\n\t\t\treturn;\n\t\t}\n\t\tvar dataString = \"&method=editDiagnosticInfo&pollinterval=\"+pollinterval+\"&pollcount=\"+pollcount+\"&criticalvalue=\"+criticalvalue+\"&diagnosticname=\"+name; //No I18N\n\t\t $.ajax({\n \t         type: \"POST\", //No I18N\n \t         url: \"/DiagnosticInfo.do\",//No I18N\n \t         data: dataString,            // Query String parameters\n \t         success: function(response)\n \t         {\n \t        \tcloseDiagnosticDetails(id,true,pollinterval,pollcount,criticalvalue,units)\n \t        \tif(response != null){\n \t        \t\tjQuery(\"#\"+id+\"_description\").html(response)\n \t         \t}\n \t         }\n \t \t});\n\t}\n</script>\n\n");
/*      */                     }
/* 5010 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5011 */         out = _jspx_out;
/* 5012 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5013 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5014 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5017 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5023 */     PageContext pageContext = _jspx_page_context;
/* 5024 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5026 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 5027 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5028 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 5030 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 5032 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 5033 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5034 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5035 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5036 */       return true;
/*      */     }
/* 5038 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5039 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5044 */     PageContext pageContext = _jspx_page_context;
/* 5045 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5047 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5048 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 5049 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5051 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 5053 */     _jspx_th_tiles_005fput_005f0.setValue("Diagnostic Details");
/* 5054 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 5055 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 5056 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5057 */       return true;
/*      */     }
/* 5059 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5060 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5065 */     PageContext pageContext = _jspx_page_context;
/* 5066 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5068 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 5069 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 5070 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5072 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.admin.jmx.templates.link");
/* 5073 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 5074 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 5075 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 5076 */         out = _jspx_page_context.pushBody();
/* 5077 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 5078 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5081 */         out.write(32);
/* 5082 */         if (_jspx_meth_fmt_005fparam_005f0(_jspx_th_fmt_005fmessage_005f0, _jspx_page_context))
/* 5083 */           return true;
/* 5084 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 5085 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5088 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 5089 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5092 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 5093 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5094 */       return true;
/*      */     }
/* 5096 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5097 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f0(JspTag _jspx_th_fmt_005fmessage_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5102 */     PageContext pageContext = _jspx_page_context;
/* 5103 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5105 */     ParamTag _jspx_th_fmt_005fparam_005f0 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 5106 */     _jspx_th_fmt_005fparam_005f0.setPageContext(_jspx_page_context);
/* 5107 */     _jspx_th_fmt_005fparam_005f0.setParent((Tag)_jspx_th_fmt_005fmessage_005f0);
/* 5108 */     int _jspx_eval_fmt_005fparam_005f0 = _jspx_th_fmt_005fparam_005f0.doStartTag();
/* 5109 */     if (_jspx_eval_fmt_005fparam_005f0 != 0) {
/* 5110 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 5111 */         out = _jspx_page_context.pushBody();
/* 5112 */         _jspx_th_fmt_005fparam_005f0.setBodyContent((BodyContent)out);
/* 5113 */         _jspx_th_fmt_005fparam_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5116 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_fmt_005fparam_005f0, _jspx_page_context))
/* 5117 */           return true;
/* 5118 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f0.doAfterBody();
/* 5119 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5122 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 5123 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5126 */     if (_jspx_th_fmt_005fparam_005f0.doEndTag() == 5) {
/* 5127 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 5128 */       return true;
/*      */     }
/* 5130 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 5131 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_fmt_005fparam_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5136 */     PageContext pageContext = _jspx_page_context;
/* 5137 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5139 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5140 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5141 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_fmt_005fparam_005f0);
/*      */     
/* 5143 */     _jspx_th_c_005fout_005f1.setValue("${templatetypestr}");
/* 5144 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5145 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5146 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5147 */       return true;
/*      */     }
/* 5149 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5150 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5155 */     PageContext pageContext = _jspx_page_context;
/* 5156 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5158 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 5159 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 5160 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5162 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.admin.jmx.templates.link");
/* 5163 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 5164 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 5165 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 5166 */         out = _jspx_page_context.pushBody();
/* 5167 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 5168 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5171 */         out.write(32);
/* 5172 */         if (_jspx_meth_fmt_005fparam_005f1(_jspx_th_fmt_005fmessage_005f1, _jspx_page_context))
/* 5173 */           return true;
/* 5174 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 5175 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5178 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 5179 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5182 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 5183 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5184 */       return true;
/*      */     }
/* 5186 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5187 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f1(JspTag _jspx_th_fmt_005fmessage_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5192 */     PageContext pageContext = _jspx_page_context;
/* 5193 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5195 */     ParamTag _jspx_th_fmt_005fparam_005f1 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 5196 */     _jspx_th_fmt_005fparam_005f1.setPageContext(_jspx_page_context);
/* 5197 */     _jspx_th_fmt_005fparam_005f1.setParent((Tag)_jspx_th_fmt_005fmessage_005f1);
/* 5198 */     int _jspx_eval_fmt_005fparam_005f1 = _jspx_th_fmt_005fparam_005f1.doStartTag();
/* 5199 */     if (_jspx_eval_fmt_005fparam_005f1 != 0) {
/* 5200 */       if (_jspx_eval_fmt_005fparam_005f1 != 1) {
/* 5201 */         out = _jspx_page_context.pushBody();
/* 5202 */         _jspx_th_fmt_005fparam_005f1.setBodyContent((BodyContent)out);
/* 5203 */         _jspx_th_fmt_005fparam_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5206 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_fmt_005fparam_005f1, _jspx_page_context))
/* 5207 */           return true;
/* 5208 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f1.doAfterBody();
/* 5209 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5212 */       if (_jspx_eval_fmt_005fparam_005f1 != 1) {
/* 5213 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5216 */     if (_jspx_th_fmt_005fparam_005f1.doEndTag() == 5) {
/* 5217 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f1);
/* 5218 */       return true;
/*      */     }
/* 5220 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f1);
/* 5221 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_fmt_005fparam_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5226 */     PageContext pageContext = _jspx_page_context;
/* 5227 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5229 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5230 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5231 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_fmt_005fparam_005f1);
/*      */     
/* 5233 */     _jspx_th_c_005fout_005f2.setValue("${templatetypestr}");
/* 5234 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5235 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5236 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5237 */       return true;
/*      */     }
/* 5239 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5240 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f25(JspTag _jspx_th_c_005fchoose_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5245 */     PageContext pageContext = _jspx_page_context;
/* 5246 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5248 */     WhenTag _jspx_th_c_005fwhen_005f25 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5249 */     _jspx_th_c_005fwhen_005f25.setPageContext(_jspx_page_context);
/* 5250 */     _jspx_th_c_005fwhen_005f25.setParent((Tag)_jspx_th_c_005fchoose_005f25);
/*      */     
/* 5252 */     _jspx_th_c_005fwhen_005f25.setTest("${empty diagnosticinfo}");
/* 5253 */     int _jspx_eval_c_005fwhen_005f25 = _jspx_th_c_005fwhen_005f25.doStartTag();
/* 5254 */     if (_jspx_eval_c_005fwhen_005f25 != 0) {
/*      */       for (;;) {
/* 5256 */         out.write("\n\t\t\t<div style=\"padding:0px 0;clear:both; margin-top:3px;\"></div> \n\t\t    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"lrtbdarkborder itadmin-no-decor\">\n\t\t  \t\t<tr>\n\t\t    \t\t<td height=\"31\" class=\"tableheadingbborder itadmin-hide\" align=\"left\">\n\t\t\t  \t\t\t");
/* 5257 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fwhen_005f25, _jspx_page_context))
/* 5258 */           return true;
/* 5259 */         out.write(9);
/* 5260 */         out.write(9);
/* 5261 */         out.write("\n\t\t\t  \t\t</td>\n\t\t  \t\t</tr>\n\t\t\t\t<tr>\n\t\t            <td class=\"bodytext cenAlign\" align=\"center\" height=\"26\"  colspan='2'>\n\t\t\t\t\t\t");
/* 5262 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fwhen_005f25, _jspx_page_context))
/* 5263 */           return true;
/* 5264 */         out.write(9);
/* 5265 */         out.write(9);
/* 5266 */         out.write("\n\t\t          \t</td>\n\t\t      </tr>\n\t\t     </table>\n\t\t");
/* 5267 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f25.doAfterBody();
/* 5268 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5272 */     if (_jspx_th_c_005fwhen_005f25.doEndTag() == 5) {
/* 5273 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f25);
/* 5274 */       return true;
/*      */     }
/* 5276 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f25);
/* 5277 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fwhen_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5282 */     PageContext pageContext = _jspx_page_context;
/* 5283 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5285 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5286 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 5287 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f25);
/*      */     
/* 5289 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.diagnostic.info.text");
/* 5290 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 5291 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 5292 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5293 */       return true;
/*      */     }
/* 5295 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5296 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fwhen_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5301 */     PageContext pageContext = _jspx_page_context;
/* 5302 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5304 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5305 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 5306 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f25);
/*      */     
/* 5308 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.diagnostic.info.empty.text");
/* 5309 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 5310 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 5311 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5312 */       return true;
/*      */     }
/* 5314 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5315 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fotherwise_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5320 */     PageContext pageContext = _jspx_page_context;
/* 5321 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5323 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5324 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 5325 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f25);
/* 5326 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 5327 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 5328 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 5329 */         out = _jspx_page_context.pushBody();
/* 5330 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 5331 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5334 */         out.write("am.webclient.setting.self.monitoring.enable.text");
/* 5335 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 5336 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5339 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 5340 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5343 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 5344 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5345 */       return true;
/*      */     }
/* 5347 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5348 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fotherwise_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5353 */     PageContext pageContext = _jspx_page_context;
/* 5354 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5356 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5357 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 5358 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f25);
/*      */     
/* 5360 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.diagnostic.info.text");
/* 5361 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 5362 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 5363 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 5364 */       return true;
/*      */     }
/* 5366 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 5367 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fotherwise_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5372 */     PageContext pageContext = _jspx_page_context;
/* 5373 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5375 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5376 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 5377 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f25);
/* 5378 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 5379 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 5380 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 5381 */         out = _jspx_page_context.pushBody();
/* 5382 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 5383 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5386 */         out.write("am.webclient.diagnostic.name.text");
/* 5387 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 5388 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5391 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 5392 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5395 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 5396 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 5397 */       return true;
/*      */     }
/* 5399 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 5400 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fotherwise_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5405 */     PageContext pageContext = _jspx_page_context;
/* 5406 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5408 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5409 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 5410 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f25);
/* 5411 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 5412 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 5413 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 5414 */         out = _jspx_page_context.pushBody();
/* 5415 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 5416 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5419 */         out.write("Description");
/* 5420 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 5421 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5424 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 5425 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5428 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 5429 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 5430 */       return true;
/*      */     }
/* 5432 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 5433 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fotherwise_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5438 */     PageContext pageContext = _jspx_page_context;
/* 5439 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5441 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5442 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 5443 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f25);
/* 5444 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 5445 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 5446 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 5447 */         out = _jspx_page_context.pushBody();
/* 5448 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 5449 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5452 */         out.write("Poll Interval");
/* 5453 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 5454 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5457 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 5458 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5461 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 5462 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 5463 */       return true;
/*      */     }
/* 5465 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 5466 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_c_005fotherwise_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5471 */     PageContext pageContext = _jspx_page_context;
/* 5472 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5474 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5475 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 5476 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_c_005fotherwise_005f25);
/* 5477 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 5478 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 5479 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 5480 */         out = _jspx_page_context.pushBody();
/* 5481 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 5482 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5485 */         out.write("am.webclient.threshold.Consecutivepolls");
/* 5486 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 5487 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5490 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 5491 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5494 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 5495 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 5496 */       return true;
/*      */     }
/* 5498 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 5499 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_c_005fotherwise_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5504 */     PageContext pageContext = _jspx_page_context;
/* 5505 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5507 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5508 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 5509 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_c_005fotherwise_005f25);
/* 5510 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 5511 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 5512 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 5513 */         out = _jspx_page_context.pushBody();
/* 5514 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 5515 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5518 */         out.write("am.webclient.diagnostic.critical.threshold.value.text");
/* 5519 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 5520 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5523 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 5524 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5527 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 5528 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 5529 */       return true;
/*      */     }
/* 5531 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 5532 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5537 */     PageContext pageContext = _jspx_page_context;
/* 5538 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5540 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5541 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5542 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5544 */     _jspx_th_c_005fout_005f3.setValue("${rowbgcolor}");
/* 5545 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5546 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5547 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5548 */       return true;
/*      */     }
/* 5550 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5551 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5556 */     PageContext pageContext = _jspx_page_context;
/* 5557 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5559 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5560 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5561 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5563 */     _jspx_th_c_005fout_005f4.setValue("${diagnosticrow['DIAGNOSTICDISPLAYNAME']}");
/* 5564 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5565 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5566 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5567 */       return true;
/*      */     }
/* 5569 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5570 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5575 */     PageContext pageContext = _jspx_page_context;
/* 5576 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5578 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5579 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5580 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5582 */     _jspx_th_c_005fout_005f5.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 5583 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5584 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5585 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5586 */       return true;
/*      */     }
/* 5588 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5589 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5594 */     PageContext pageContext = _jspx_page_context;
/* 5595 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5597 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5598 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5599 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5601 */     _jspx_th_c_005fout_005f6.setValue("${diagnosticrow['DESCRIPTION']}");
/* 5602 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5603 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5604 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5605 */       return true;
/*      */     }
/* 5607 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5608 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5613 */     PageContext pageContext = _jspx_page_context;
/* 5614 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5616 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5617 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5618 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5620 */     _jspx_th_c_005fout_005f7.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 5621 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5622 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5623 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5624 */       return true;
/*      */     }
/* 5626 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5627 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5632 */     PageContext pageContext = _jspx_page_context;
/* 5633 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5635 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5636 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5637 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5639 */     _jspx_th_c_005fout_005f8.setValue("${diagnosticrow['POLLINTERVAL']}");
/* 5640 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5641 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5642 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5643 */       return true;
/*      */     }
/* 5645 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5646 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5651 */     PageContext pageContext = _jspx_page_context;
/* 5652 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5654 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5655 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5656 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5658 */     _jspx_th_c_005fout_005f9.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 5659 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5660 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5661 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5662 */       return true;
/*      */     }
/* 5664 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5665 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5670 */     PageContext pageContext = _jspx_page_context;
/* 5671 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5673 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5674 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 5675 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5677 */     _jspx_th_c_005fout_005f10.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 5678 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 5679 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 5680 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5681 */       return true;
/*      */     }
/* 5683 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5684 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5689 */     PageContext pageContext = _jspx_page_context;
/* 5690 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5692 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5693 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 5694 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5696 */     _jspx_th_c_005fout_005f11.setValue("${diagnosticrow['POLLINTERVAL']}");
/* 5697 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 5698 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 5699 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5700 */       return true;
/*      */     }
/* 5702 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5703 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f26(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5708 */     PageContext pageContext = _jspx_page_context;
/* 5709 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5711 */     ChooseTag _jspx_th_c_005fchoose_005f26 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5712 */     _jspx_th_c_005fchoose_005f26.setPageContext(_jspx_page_context);
/* 5713 */     _jspx_th_c_005fchoose_005f26.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/* 5714 */     int _jspx_eval_c_005fchoose_005f26 = _jspx_th_c_005fchoose_005f26.doStartTag();
/* 5715 */     if (_jspx_eval_c_005fchoose_005f26 != 0) {
/*      */       for (;;) {
/* 5717 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 5718 */         if (_jspx_meth_c_005fwhen_005f26(_jspx_th_c_005fchoose_005f26, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5719 */           return true;
/* 5720 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 5721 */         if (_jspx_meth_c_005fotherwise_005f26(_jspx_th_c_005fchoose_005f26, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5722 */           return true;
/* 5723 */         out.write("\n\t\t\t\t\t\t\t");
/* 5724 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f26.doAfterBody();
/* 5725 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5729 */     if (_jspx_th_c_005fchoose_005f26.doEndTag() == 5) {
/* 5730 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f26);
/* 5731 */       return true;
/*      */     }
/* 5733 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f26);
/* 5734 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f26(JspTag _jspx_th_c_005fchoose_005f26, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5739 */     PageContext pageContext = _jspx_page_context;
/* 5740 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5742 */     WhenTag _jspx_th_c_005fwhen_005f26 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5743 */     _jspx_th_c_005fwhen_005f26.setPageContext(_jspx_page_context);
/* 5744 */     _jspx_th_c_005fwhen_005f26.setParent((Tag)_jspx_th_c_005fchoose_005f26);
/*      */     
/* 5746 */     _jspx_th_c_005fwhen_005f26.setTest("${diagnosticrow[\"DIAGONISTICNAME\"] == \"SYNCINGDELAY\" }");
/* 5747 */     int _jspx_eval_c_005fwhen_005f26 = _jspx_th_c_005fwhen_005f26.doStartTag();
/* 5748 */     if (_jspx_eval_c_005fwhen_005f26 != 0) {
/*      */       for (;;) {
/* 5750 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 5751 */         if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_c_005fwhen_005f26, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5752 */           return true;
/* 5753 */         out.write(9);
/* 5754 */         out.write(9);
/* 5755 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 5756 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f26.doAfterBody();
/* 5757 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5761 */     if (_jspx_th_c_005fwhen_005f26.doEndTag() == 5) {
/* 5762 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f26);
/* 5763 */       return true;
/*      */     }
/* 5765 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f26);
/* 5766 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_c_005fwhen_005f26, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5771 */     PageContext pageContext = _jspx_page_context;
/* 5772 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5774 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5775 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 5776 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f26);
/*      */     
/* 5778 */     _jspx_th_fmt_005fmessage_005f11.setKey("am.webclient.diagnostic.notapplicable.text");
/* 5779 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 5780 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 5781 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 5782 */       return true;
/*      */     }
/* 5784 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 5785 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f26(JspTag _jspx_th_c_005fchoose_005f26, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5790 */     PageContext pageContext = _jspx_page_context;
/* 5791 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5793 */     OtherwiseTag _jspx_th_c_005fotherwise_005f26 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5794 */     _jspx_th_c_005fotherwise_005f26.setPageContext(_jspx_page_context);
/* 5795 */     _jspx_th_c_005fotherwise_005f26.setParent((Tag)_jspx_th_c_005fchoose_005f26);
/* 5796 */     int _jspx_eval_c_005fotherwise_005f26 = _jspx_th_c_005fotherwise_005f26.doStartTag();
/* 5797 */     if (_jspx_eval_c_005fotherwise_005f26 != 0) {
/*      */       for (;;) {
/* 5799 */         out.write("\n\t\t\t\t\t\t\t\t\t<div style=\"display: block;\" id=\"view_pollcount_");
/* 5800 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fotherwise_005f26, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5801 */           return true;
/* 5802 */         out.write("\">\n\t\t\t\t\t\t\t\t\t");
/* 5803 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fotherwise_005f26, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5804 */           return true;
/* 5805 */         out.write("\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t<div style=\"display: none; \" id=\"edit_pollcount_");
/* 5806 */         if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fotherwise_005f26, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5807 */           return true;
/* 5808 */         out.write("\">\n\t\t\t\t\t\t\t\t\t<input id=\"pollcount_");
/* 5809 */         if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fotherwise_005f26, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5810 */           return true;
/* 5811 */         out.write("\" size=\"3\" type=\"text\" value=\"");
/* 5812 */         if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fotherwise_005f26, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5813 */           return true;
/* 5814 */         out.write("\" >\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t");
/* 5815 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f26.doAfterBody();
/* 5816 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5820 */     if (_jspx_th_c_005fotherwise_005f26.doEndTag() == 5) {
/* 5821 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f26);
/* 5822 */       return true;
/*      */     }
/* 5824 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f26);
/* 5825 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fotherwise_005f26, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5830 */     PageContext pageContext = _jspx_page_context;
/* 5831 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5833 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5834 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 5835 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fotherwise_005f26);
/*      */     
/* 5837 */     _jspx_th_c_005fout_005f12.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 5838 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 5839 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 5840 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5841 */       return true;
/*      */     }
/* 5843 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fotherwise_005f26, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5849 */     PageContext pageContext = _jspx_page_context;
/* 5850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5852 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5853 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 5854 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fotherwise_005f26);
/*      */     
/* 5856 */     _jspx_th_c_005fout_005f13.setValue("${diagnosticrow['CONSECUTIVEPOLLCOUNT']}");
/* 5857 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 5858 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 5859 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5860 */       return true;
/*      */     }
/* 5862 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5863 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fotherwise_005f26, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5868 */     PageContext pageContext = _jspx_page_context;
/* 5869 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5871 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5872 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 5873 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fotherwise_005f26);
/*      */     
/* 5875 */     _jspx_th_c_005fout_005f14.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 5876 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 5877 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 5878 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5879 */       return true;
/*      */     }
/* 5881 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5882 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fotherwise_005f26, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5887 */     PageContext pageContext = _jspx_page_context;
/* 5888 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5890 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5891 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 5892 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fotherwise_005f26);
/*      */     
/* 5894 */     _jspx_th_c_005fout_005f15.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 5895 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 5896 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 5897 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5898 */       return true;
/*      */     }
/* 5900 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5901 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fotherwise_005f26, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5906 */     PageContext pageContext = _jspx_page_context;
/* 5907 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5909 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5910 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 5911 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fotherwise_005f26);
/*      */     
/* 5913 */     _jspx_th_c_005fout_005f16.setValue("${diagnosticrow['CONSECUTIVEPOLLCOUNT']}");
/* 5914 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 5915 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 5916 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5917 */       return true;
/*      */     }
/* 5919 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5920 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5925 */     PageContext pageContext = _jspx_page_context;
/* 5926 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5928 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5929 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 5930 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5932 */     _jspx_th_c_005fout_005f17.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 5933 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 5934 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 5935 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5936 */       return true;
/*      */     }
/* 5938 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5939 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5944 */     PageContext pageContext = _jspx_page_context;
/* 5945 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5947 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5948 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 5949 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5951 */     _jspx_th_c_005fout_005f18.setValue("${diagnosticrow['CRITICALVALUE']}");
/* 5952 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 5953 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 5954 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5955 */       return true;
/*      */     }
/* 5957 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5958 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5963 */     PageContext pageContext = _jspx_page_context;
/* 5964 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5966 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5967 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 5968 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5970 */     _jspx_th_c_005fout_005f19.setValue("${diagnosticrow['UNITS']}");
/* 5971 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 5972 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 5973 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5974 */       return true;
/*      */     }
/* 5976 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5977 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5982 */     PageContext pageContext = _jspx_page_context;
/* 5983 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5985 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5986 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 5987 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5989 */     _jspx_th_c_005fout_005f20.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 5990 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 5991 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 5992 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5993 */       return true;
/*      */     }
/* 5995 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5996 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6001 */     PageContext pageContext = _jspx_page_context;
/* 6002 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6004 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6005 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 6006 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6008 */     _jspx_th_c_005fout_005f21.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 6009 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 6010 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 6011 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6012 */       return true;
/*      */     }
/* 6014 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6015 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6020 */     PageContext pageContext = _jspx_page_context;
/* 6021 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6023 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6024 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 6025 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6027 */     _jspx_th_c_005fout_005f22.setValue("${diagnosticrow['CRITICALVALUE']}");
/* 6028 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 6029 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 6030 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6031 */       return true;
/*      */     }
/* 6033 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6034 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6039 */     PageContext pageContext = _jspx_page_context;
/* 6040 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6042 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6043 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 6044 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6046 */     _jspx_th_c_005fout_005f23.setValue("${diagnosticrow['UNITS']}");
/* 6047 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 6048 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 6049 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6050 */       return true;
/*      */     }
/* 6052 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6053 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6058 */     PageContext pageContext = _jspx_page_context;
/* 6059 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6061 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6062 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 6063 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6065 */     _jspx_th_c_005fout_005f24.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 6066 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 6067 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 6068 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6069 */       return true;
/*      */     }
/* 6071 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6072 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6077 */     PageContext pageContext = _jspx_page_context;
/* 6078 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6080 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6081 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 6082 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6084 */     _jspx_th_c_005fout_005f25.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 6085 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 6086 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 6087 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6088 */       return true;
/*      */     }
/* 6090 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6091 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6096 */     PageContext pageContext = _jspx_page_context;
/* 6097 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6099 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6100 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 6101 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6103 */     _jspx_th_c_005fout_005f26.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 6104 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 6105 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 6106 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 6107 */       return true;
/*      */     }
/* 6109 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 6110 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6115 */     PageContext pageContext = _jspx_page_context;
/* 6116 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6118 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6119 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 6120 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6122 */     _jspx_th_c_005fout_005f27.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 6123 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 6124 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 6125 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 6126 */       return true;
/*      */     }
/* 6128 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 6129 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6134 */     PageContext pageContext = _jspx_page_context;
/* 6135 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6137 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6138 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 6139 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6141 */     _jspx_th_c_005fout_005f28.setValue("${diagnosticrow['DIAGONISTICNAME']}");
/* 6142 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 6143 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 6144 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 6145 */       return true;
/*      */     }
/* 6147 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 6148 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6153 */     PageContext pageContext = _jspx_page_context;
/* 6154 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6156 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6157 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 6158 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6160 */     _jspx_th_c_005fout_005f29.setValue("${diagnosticrow['UNITS']}");
/* 6161 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 6162 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 6163 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 6164 */       return true;
/*      */     }
/* 6166 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 6167 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6172 */     PageContext pageContext = _jspx_page_context;
/* 6173 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6175 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6176 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 6177 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6179 */     _jspx_th_c_005fout_005f30.setValue("${diagnosticrow['DIAGNOSTICID']}");
/* 6180 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 6181 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 6182 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 6183 */       return true;
/*      */     }
/* 6185 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 6186 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6191 */     PageContext pageContext = _jspx_page_context;
/* 6192 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6194 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6195 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 6196 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6198 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 6200 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 6201 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 6202 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 6203 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 6204 */       return true;
/*      */     }
/* 6206 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 6207 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\DiagnosticDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */