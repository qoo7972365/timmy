/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
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
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ShowExtDevices_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*   70 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
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
/*  347 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
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
/*  720 */     java.net.InetAddress add = null;
/*  721 */     if (ip.equals("127.0.0.1")) {
/*  722 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  726 */       add = java.net.InetAddress.getByName(ip);
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
/*      */           catch (java.sql.SQLException e) {
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
/* 2185 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2186 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/* 2187 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/* 2188 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2214 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2218 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2237 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2241 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2243 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2244 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2245 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2246 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2247 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2249 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2250 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2251 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2252 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2256 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2257 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2258 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2265 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2268 */     JspWriter out = null;
/* 2269 */     Object page = this;
/* 2270 */     JspWriter _jspx_out = null;
/* 2271 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2275 */       response.setContentType("text/html;charset=UTF-8");
/* 2276 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2278 */       _jspx_page_context = pageContext;
/* 2279 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2280 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2281 */       session = pageContext.getSession();
/* 2282 */       out = pageContext.getOut();
/* 2283 */       _jspx_out = out;
/*      */       
/* 2285 */       out.write("<!DOCTYPE html>\n");
/* 2286 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2287 */       out.write("\n\n\n\n\n\n\n\n\n\n");
/* 2288 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2290 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2291 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2292 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2294 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2296 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2298 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2300 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2301 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2302 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2303 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2306 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2307 */         String available = null;
/* 2308 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2309 */         out.write(10);
/*      */         
/* 2311 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2312 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2313 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2315 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2317 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2319 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2321 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2322 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2323 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2324 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2327 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2328 */           String unavailable = null;
/* 2329 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2330 */           out.write(10);
/*      */           
/* 2332 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2333 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2334 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2336 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2338 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2340 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2342 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2343 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2344 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2345 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2348 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2349 */             String unmanaged = null;
/* 2350 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2351 */             out.write(10);
/*      */             
/* 2353 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2354 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2355 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2357 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2359 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2361 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2363 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2364 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2365 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2366 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2369 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2370 */               String scheduled = null;
/* 2371 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2372 */               out.write(10);
/*      */               
/* 2374 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2375 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2376 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2378 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2380 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2382 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2384 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2385 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2386 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2387 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2390 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2391 */                 String critical = null;
/* 2392 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2393 */                 out.write(10);
/*      */                 
/* 2395 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2396 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2397 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2399 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2401 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2403 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2405 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2406 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2407 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2408 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2411 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2412 */                   String clear = null;
/* 2413 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2414 */                   out.write(10);
/*      */                   
/* 2416 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2417 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2418 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2420 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2422 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2424 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2426 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2427 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2428 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2429 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2432 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2433 */                     String warning = null;
/* 2434 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2435 */                     out.write(10);
/* 2436 */                     out.write(10);
/*      */                     
/* 2438 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2439 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2441 */                     out.write(10);
/* 2442 */                     out.write(10);
/* 2443 */                     out.write(10);
/* 2444 */                     out.write(10);
/* 2445 */                     out.write(10);
/* 2446 */                     out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2447 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2449 */                     out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 2450 */                     out.write(10);
/* 2451 */                     out.write("\n<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"600\">\n");
/*      */                     
/* 2453 */                     request.setAttribute("HelpKey", "ServiceDesk Settings");
/*      */                     
/* 2455 */                     out.write("\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<script>\nfunction fnSelectAll(e)\n{\n\tToggleAll(e,document.form1,\"checkbox\")\n}\n\nfunction fnFormSubmit()\n{\n\n ");
/* 2456 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2458 */                     out.write(10);
/* 2459 */                     out.write(32);
/*      */                     
/* 2461 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2462 */                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2463 */                     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                     
/* 2465 */                     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2466 */                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2467 */                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                       for (;;) {
/* 2469 */                         out.write("\n\n  if(trimAll(document.AMActionForm.port.value)=='')\n  {\n\t  alert(\"");
/* 2470 */                         out.print(FormatUtil.getString("Polling Interval can not be empty"));
/* 2471 */                         out.write("\");\n\t  document.AMActionForm.port.focus();\n\t  return false;\n  }\n if(trimAll(document.AMActionForm.port.value)<'5')\n  {\n\t  alert('");
/* 2472 */                         out.print(FormatUtil.getString("am.webclient.pollinterval.alert.txt"));
/* 2473 */                         out.write("');\n\t  document.AMActionForm.port.focus();\n\t  return false;\n  }\n    if(trimAll(document.AMActionForm.synchHr.value)=='' && trimAll(document.AMActionForm.synchMin.value)=='')\n  {\n\t  alert(\"");
/* 2474 */                         out.print(FormatUtil.getString("Polling Interval to update all devices and alarms can not be empty"));
/* 2475 */                         out.write("\");\n\t  document.AMActionForm.port.focus();\n\t  return false;\n  }\n if((trimAll(document.AMActionForm.synchHr.value)=='' || trimAll(document.AMActionForm.synchHr.value)==0) && trimAll(document.AMActionForm.synchMin.value)<30)\n  {\n\t  alert('");
/* 2476 */                         out.print(FormatUtil.getString("am.webclient.fullUpdateInterval.alert.txt"));
/* 2477 */                         out.write("');\n\t  document.AMActionForm.port.focus();\n\t  return false;\n  }\n  document.AMActionForm.submit();\n");
/* 2478 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2479 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2483 */                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2484 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */                     }
/*      */                     else {
/* 2487 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2488 */                       out.write("\n}\n\nfunction showHide(show,hide)\n{\n\tif(show==\"Product Settings\"){\n\n\tdocument.getElementById(\"profilelink-left\").className = \"tbSelected_Left\";\n\tdocument.getElementById(\"profilelink\").className = \"tbSelected_Middle\";\n\tdocument.getElementById(\"profilelink-right\").className = \"tbSelected_Right\";\n\n\tdocument.getElementById(\"permlink-left\").className = \"tbUnSelected_Left\";\n\tdocument.getElementById(\"permlink\").className = \"tbUnSelected_Middle\";\n\tdocument.getElementById(\"permlink-right\").className = \"tbUnSelected_Right\";\n\n\tjavascript:showDiv('networkdiscoverydiv');\n\tjavascript:hideDiv('servicediscoverydiv');\n\n\n}\n\telse{\n\n\n\tdocument.getElementById(\"permlink-left\").className = \"tbSelected_Left\";\n\tdocument.getElementById(\"permlink\").className = \"tbSelected_Middle\";\n\tdocument.getElementById(\"permlink-right\").className = \"tbSelected_Right\";\n\n\tdocument.getElementById(\"profilelink-left\").className = \"tbUnSelected_Left\";\n\tdocument.getElementById(\"profilelink\").className = \"tbUnSelected_Middle\";\n\tdocument.getElementById(\"profilelink-right\").className = \"tbUnSelected_Right\";\n");
/* 2489 */                       out.write("\n\t}\n\tdocument.getElementById(show).style.display = \"block\";\n\tdocument.getElementById(hide).style.display = \"none\";\n}\n\n\n\n\n\n\n\n\n\n\n\n\n\nfunction refreshServers()\n{\n\tfor(i=0;i<document.form1.elements.length;i++)\n\t{\n\t\tif(document.form1.elements[i].type==\"checkbox\")\n\t\t{\n\t\t\tvar checkboxName = document.form1.elements[i].name;\n\t\t\tif(checkboxName==\"checkbox\")\n\t\t\t{\n\t\t\t\tvar value = document.form1.elements[i].value;\n\t\t\t\tselected=document.form1.elements[i].checked;\n\t\t\t\tif(selected)\n\t\t\t\t{\n\t\t\t\t\tbreak;\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t}\n\tif(!selected)\n\t{\n\t\talert(\"");
/* 2490 */                       out.print(FormatUtil.getString("am.webclient.adminserver.fetchnow.nothingselected.message"));
/* 2491 */                       out.write("\");\n\t}\n\telse\n\t{\n\t  document.form1.action=\"/adminAction.do?method=fetchDataNowForManagedServer\";\n\t  document.form1.method=\"Post\"\n\t  document.form1.submit();\n\t}\n}\n\nfunction deleteSelections()\n{\n ");
/* 2492 */                       if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                         return;
/* 2494 */                       out.write(10);
/* 2495 */                       out.write(32);
/*      */                       
/* 2497 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2498 */                       _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2499 */                       _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */                       
/* 2501 */                       _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 2502 */                       int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2503 */                       if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                         for (;;) {
/* 2505 */                           out.write("\n   for(i=0;i<document.form1.elements.length;i++)\n       {\n\t\t   if(document.form1.elements[i].type==\"checkbox\")\n\t\t   {\n\t\t\t   var name = document.form1.elements[i].name;\n\t\t\t   if(name==\"checkbox\")\n\t\t\t   {\n\t\t\t\t   var value = document.form1.elements[i].value;\n\t\t\t\t   sel=document.form1.elements[i].checked;\n\t\t\t\t   if(sel)\n\t\t\t\t   {\n\t\t\t\t\t   break;\n\t\t\t\t   }\n\t\t\t   }\n\t\t   }\n\t   }\n if(!sel)\n {\n\t alert(\"");
/* 2506 */                           out.print(FormatUtil.getString("am.webclient.managedserver.delete.alert"));
/* 2507 */                           out.write("\");\n }\n else if(confirm('");
/* 2508 */                           out.print(FormatUtil.getString("am.webclient.managedserver.delete.confirm"));
/* 2509 */                           out.write("'))\n {\n\t document.form1.action=\"/adminAction.do?method=deleteManagedServers\";\n\t document.form1.method=\"Post\"\n\t document.form1.submit();\n }\n ");
/* 2510 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2511 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2515 */                       if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2516 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*      */                       }
/*      */                       else {
/* 2519 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2520 */                         out.write("\n}\n\nfunction deleteaccount(pid)\n{\n ");
/* 2521 */                         if (_jspx_meth_logic_005fpresent_005f2(_jspx_page_context))
/*      */                           return;
/* 2523 */                         out.write("\n \n  ");
/*      */                         
/* 2525 */                         NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2526 */                         _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 2527 */                         _jspx_th_logic_005fnotPresent_005f2.setParent(null);
/*      */                         
/* 2529 */                         _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 2530 */                         int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 2531 */                         if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                           for (;;) {
/* 2533 */                             out.write("\n\tvar rest = confirm('");
/* 2534 */                             out.print(FormatUtil.getString("am.webclient.site24x7.delete.confirm"));
/* 2535 */                             out.write("');\n\n\tif(rest)\n\t{\n\tdocument.form1.action=\"/extDeviceAction.do?method=deleteDeviceConfiguration&prodName=Site24x7&id=\"+pid;\n\tdocument.form1.method=\"Post\" //No I18N\n    document.form1.submit();\n\t}\n  ");
/* 2536 */                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 2537 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2541 */                         if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 2542 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/*      */                         }
/*      */                         else {
/* 2545 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 2546 */                           out.write("\n}\n\n</script>\n");
/*      */                           
/* 2548 */                           InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2549 */                           _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2550 */                           _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                           
/* 2552 */                           _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2553 */                           int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2554 */                           if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                             for (;;) {
/* 2556 */                               out.write(10);
/* 2557 */                               if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                                 return;
/* 2559 */                               out.write(10);
/* 2560 */                               if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                                 return;
/* 2562 */                               out.write(10);
/*      */                               
/* 2564 */                               PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2565 */                               _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2566 */                               _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                               
/* 2568 */                               _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */                               
/* 2570 */                               _jspx_th_tiles_005fput_005f2.setType("string");
/* 2571 */                               int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2572 */                               if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2573 */                                 if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2574 */                                   out = _jspx_page_context.pushBody();
/* 2575 */                                   _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2576 */                                   _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 2579 */                                   out.write(10);
/* 2580 */                                   out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */                                   
/*      */ 
/* 2583 */                                   String usertype = FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                                   
/* 2585 */                                   out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/* 2586 */                                   out.print(FormatUtil.getString("wizard.disabled"));
/* 2587 */                                   out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/* 2588 */                                   out.print(FormatUtil.getString("am.webclient.admin.heading"));
/* 2589 */                                   out.write("</td>\n  </tr>\n  \n ");
/*      */                                   
/* 2591 */                                   if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                                   {
/*      */ 
/* 2594 */                                     out.write("  \n  <tr>\n\n  ");
/*      */                                     
/* 2596 */                                     if (request.getParameter("wiz") != null)
/*      */                                     {
/* 2598 */                                       out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/* 2599 */                                       out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 2600 */                                       out.write("\" class='disabledlink'>");
/* 2601 */                                       out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 2602 */                                       out.write("</a></td>\n  ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 2606 */                                       out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */                                       
/* 2608 */                                       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2609 */                                       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2610 */                                       _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/* 2611 */                                       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2612 */                                       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                         for (;;) {
/* 2614 */                                           out.write(10);
/*      */                                           
/* 2616 */                                           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2617 */                                           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2618 */                                           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                           
/* 2620 */                                           _jspx_th_c_005fwhen_005f0.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/* 2621 */                                           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2622 */                                           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                             for (;;) {
/* 2624 */                                               out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/* 2625 */                                               out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 2626 */                                               out.write("\n    </a>\n ");
/* 2627 */                                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2628 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 2632 */                                           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2633 */                                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                           }
/*      */                                           
/* 2636 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2637 */                                           out.write(10);
/* 2638 */                                           out.write(32);
/*      */                                           
/* 2640 */                                           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2641 */                                           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2642 */                                           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2643 */                                           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2644 */                                           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                             for (;;) {
/* 2646 */                                               out.write(10);
/* 2647 */                                               out.write(9);
/* 2648 */                                               out.write(32);
/* 2649 */                                               out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 2650 */                                               out.write(10);
/* 2651 */                                               out.write(32);
/* 2652 */                                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2653 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 2657 */                                           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2658 */                                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                           }
/*      */                                           
/* 2661 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2662 */                                           out.write(10);
/* 2663 */                                           out.write(32);
/* 2664 */                                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2665 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2669 */                                       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2670 */                                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                                       }
/*      */                                       
/* 2673 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2674 */                                       out.write("\n    </td>\n\t");
/*      */                                     }
/* 2676 */                                     out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */                                     
/* 2678 */                                     if (request.getParameter("wiz") != null)
/*      */                                     {
/* 2680 */                                       out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/* 2681 */                                       out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 2682 */                                       out.write("\" class='disabledlink'>");
/* 2683 */                                       out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 2684 */                                       out.write("</a></td>\n   ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 2688 */                                       out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */                                       
/* 2690 */                                       ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2691 */                                       _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2692 */                                       _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/* 2693 */                                       int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2694 */                                       if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                         for (;;) {
/* 2696 */                                           out.write(10);
/*      */                                           
/* 2698 */                                           WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2699 */                                           _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2700 */                                           _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                           
/* 2702 */                                           _jspx_th_c_005fwhen_005f1.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/* 2703 */                                           int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2704 */                                           if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                             for (;;) {
/* 2706 */                                               out.write("\n   ");
/* 2707 */                                               out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 2708 */                                               out.write(10);
/* 2709 */                                               out.write(32);
/* 2710 */                                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2711 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 2715 */                                           if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2716 */                                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                           }
/*      */                                           
/* 2719 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2720 */                                           out.write(10);
/* 2721 */                                           out.write(32);
/*      */                                           
/* 2723 */                                           OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2724 */                                           _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2725 */                                           _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2726 */                                           int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2727 */                                           if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                             for (;;) {
/* 2729 */                                               out.write(10);
/* 2730 */                                               String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/* 2731 */                                               out.write("\n\t \n <a href=\"");
/* 2732 */                                               out.print(link);
/* 2733 */                                               out.write("\" class=\"new-left-links\">\n               ");
/* 2734 */                                               out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 2735 */                                               out.write("\n    </a>    \n ");
/* 2736 */                                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2737 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 2741 */                                           if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2742 */                                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                           }
/*      */                                           
/* 2745 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2746 */                                           out.write(10);
/* 2747 */                                           out.write(32);
/* 2748 */                                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2749 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2753 */                                       if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2754 */                                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                                       }
/*      */                                       
/* 2757 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2758 */                                       out.write("\n</td>\n");
/*      */                                     }
/* 2760 */                                     out.write("\n</tr>\n\n ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 2764 */                                   out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                                   
/* 2766 */                                   ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2767 */                                   _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2768 */                                   _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/* 2769 */                                   int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2770 */                                   if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                     for (;;) {
/* 2772 */                                       out.write(10);
/*      */                                       
/* 2774 */                                       WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2775 */                                       _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2776 */                                       _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                       
/* 2778 */                                       _jspx_th_c_005fwhen_005f2.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/* 2779 */                                       int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2780 */                                       if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                         for (;;) {
/* 2782 */                                           out.write("\n    \n       ");
/* 2783 */                                           out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 2784 */                                           out.write(10);
/* 2785 */                                           out.write(32);
/* 2786 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2787 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2791 */                                       if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2792 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                       }
/*      */                                       
/* 2795 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2796 */                                       out.write(10);
/* 2797 */                                       out.write(32);
/*      */                                       
/* 2799 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2800 */                                       _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2801 */                                       _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 2802 */                                       int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2803 */                                       if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                                         for (;;) {
/* 2805 */                                           out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/* 2806 */                                           out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 2807 */                                           out.write("\n    </a>\n ");
/* 2808 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2809 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2813 */                                       if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2814 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                                       }
/*      */                                       
/* 2817 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2818 */                                       out.write(10);
/* 2819 */                                       out.write(32);
/* 2820 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2821 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2825 */                                   if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2826 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                                   }
/*      */                                   
/* 2829 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2830 */                                   out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                                   
/* 2832 */                                   ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2833 */                                   _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2834 */                                   _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/* 2835 */                                   int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2836 */                                   if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                                     for (;;) {
/* 2838 */                                       out.write(10);
/*      */                                       
/* 2840 */                                       WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2841 */                                       _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2842 */                                       _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                       
/* 2844 */                                       _jspx_th_c_005fwhen_005f3.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/* 2845 */                                       int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2846 */                                       if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                         for (;;) {
/* 2848 */                                           out.write("\n    \n       ");
/* 2849 */                                           out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 2850 */                                           out.write(10);
/* 2851 */                                           out.write(32);
/* 2852 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2853 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2857 */                                       if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2858 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                       }
/*      */                                       
/* 2861 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2862 */                                       out.write(10);
/* 2863 */                                       out.write(32);
/*      */                                       
/* 2865 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2866 */                                       _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2867 */                                       _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 2868 */                                       int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2869 */                                       if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                                         for (;;) {
/* 2871 */                                           out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/* 2872 */                                           out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 2873 */                                           out.write("\n\t </a>\n ");
/* 2874 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2875 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2879 */                                       if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2880 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                                       }
/*      */                                       
/* 2883 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2884 */                                       out.write(10);
/* 2885 */                                       out.write(32);
/* 2886 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2887 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2891 */                                   if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2892 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                                   }
/*      */                                   
/* 2895 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2896 */                                   out.write("\n    </td>\n</tr>  \n\n  ");
/*      */                                   
/* 2898 */                                   if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                                   {
/*      */ 
/* 2901 */                                     out.write(32);
/* 2902 */                                     out.write(32);
/* 2903 */                                     out.write(10);
/*      */                                     
/* 2905 */                                     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2906 */                                     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2907 */                                     _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/* 2908 */                                     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2909 */                                     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                                       for (;;) {
/* 2911 */                                         out.write(10);
/*      */                                         
/* 2913 */                                         WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2914 */                                         _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 2915 */                                         _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                                         
/* 2917 */                                         _jspx_th_c_005fwhen_005f4.setTest("${param.method !='showNetworkDiscoveryForm'}");
/* 2918 */                                         int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 2919 */                                         if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                           for (;;) {
/* 2921 */                                             out.write("\n<tr>\n    ");
/* 2922 */                                             if (!request.isUserInRole("OPERATOR")) {
/* 2923 */                                               out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/* 2924 */                                               out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 2925 */                                               out.write("\n    </a>\n        </td>\n     ");
/*      */                                             } else {
/* 2927 */                                               out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/* 2928 */                                               out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 2929 */                                               out.write("\n\t</a>\n\t </td>\n\t");
/*      */                                             }
/* 2931 */                                             out.write("\n    </tr>\n ");
/* 2932 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 2933 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2937 */                                         if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 2938 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                                         }
/*      */                                         
/* 2941 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2942 */                                         out.write(10);
/* 2943 */                                         out.write(32);
/*      */                                         
/* 2945 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2946 */                                         _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2947 */                                         _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 2948 */                                         int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2949 */                                         if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                           for (;;) {
/* 2951 */                                             out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/* 2952 */                                             out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 2953 */                                             out.write("\n\t </td>\n ");
/* 2954 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2955 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2959 */                                         if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2960 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                                         }
/*      */                                         
/* 2963 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2964 */                                         out.write(10);
/* 2965 */                                         out.write(32);
/* 2966 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2967 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2971 */                                     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2972 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                                     }
/*      */                                     
/* 2975 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2976 */                                     out.write("\n \n  ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 2980 */                                   out.write("  \n \n ");
/*      */                                   
/* 2982 */                                   if (!usertype.equals("F"))
/*      */                                   {
/* 2984 */                                     out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */                                     
/* 2986 */                                     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2987 */                                     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2988 */                                     _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/* 2989 */                                     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2990 */                                     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                                       for (;;) {
/* 2992 */                                         out.write(10);
/* 2993 */                                         out.write(9);
/*      */                                         
/* 2995 */                                         WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2996 */                                         _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2997 */                                         _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                                         
/* 2999 */                                         _jspx_th_c_005fwhen_005f5.setTest("${param.method !='maintenanceTaskListView'}");
/* 3000 */                                         int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 3001 */                                         if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                           for (;;) {
/* 3003 */                                             out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 3004 */                                             out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3005 */                                             out.write("</a>\n  \t");
/* 3006 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 3007 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3011 */                                         if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 3012 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                                         }
/*      */                                         
/* 3015 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 3016 */                                         out.write("\n  \t");
/*      */                                         
/* 3018 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3019 */                                         _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 3020 */                                         _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 3021 */                                         int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 3022 */                                         if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                                           for (;;) {
/* 3024 */                                             out.write("\n \t\t");
/* 3025 */                                             out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3026 */                                             out.write("\n  \t");
/* 3027 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 3028 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3032 */                                         if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 3033 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                                         }
/*      */                                         
/* 3036 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 3037 */                                         out.write("\n  \t");
/* 3038 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 3039 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3043 */                                     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 3044 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                                     }
/*      */                                     
/* 3047 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 3048 */                                     out.write("\n     </td>\n </tr>   \n \n ");
/*      */                                     
/* 3050 */                                     if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                                     {
/*      */ 
/* 3053 */                                       out.write(32);
/* 3054 */                                       out.write(32);
/* 3055 */                                       out.write(10);
/*      */                                       
/* 3057 */                                       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3058 */                                       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3059 */                                       _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                       
/* 3061 */                                       _jspx_th_c_005fif_005f0.setTest("${category!='LAMP'}");
/* 3062 */                                       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3063 */                                       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                         for (;;) {
/* 3065 */                                           out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                                           
/* 3067 */                                           ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3068 */                                           _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 3069 */                                           _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_c_005fif_005f0);
/* 3070 */                                           int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 3071 */                                           if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                                             for (;;) {
/* 3073 */                                               out.write(10);
/* 3074 */                                               out.write(32);
/* 3075 */                                               out.write(9);
/*      */                                               
/* 3077 */                                               WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3078 */                                               _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 3079 */                                               _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                                               
/* 3081 */                                               _jspx_th_c_005fwhen_005f6.setTest("${param.method !='listTrapListener'}");
/* 3082 */                                               int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 3083 */                                               if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                                                 for (;;) {
/* 3085 */                                                   out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/* 3086 */                                                   out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3087 */                                                   out.write("</a>\n   \t");
/* 3088 */                                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 3089 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3093 */                                               if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 3094 */                                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                                               }
/*      */                                               
/* 3097 */                                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 3098 */                                               out.write("\n   \t");
/*      */                                               
/* 3100 */                                               OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3101 */                                               _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 3102 */                                               _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/* 3103 */                                               int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 3104 */                                               if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                                                 for (;;) {
/* 3106 */                                                   out.write("\n  \t\t");
/* 3107 */                                                   out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3108 */                                                   out.write(" \n   \t");
/* 3109 */                                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 3110 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3114 */                                               if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 3115 */                                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                                               }
/*      */                                               
/* 3118 */                                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 3119 */                                               out.write("\n   \t");
/* 3120 */                                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 3121 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3125 */                                           if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 3126 */                                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                                           }
/*      */                                           
/* 3129 */                                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 3130 */                                           out.write("\n      </td>\n  </tr>   \n");
/* 3131 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3132 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3136 */                                       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3137 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                                       }
/*      */                                       
/* 3140 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3141 */                                       out.write(10);
/* 3142 */                                       out.write(32);
/*      */                                     }
/*      */                                     
/*      */ 
/* 3146 */                                     out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                                     
/* 3148 */                                     ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3149 */                                     _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 3150 */                                     _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_tiles_005fput_005f2);
/* 3151 */                                     int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 3152 */                                     if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                                       for (;;) {
/* 3154 */                                         out.write(10);
/*      */                                         
/* 3156 */                                         WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3157 */                                         _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 3158 */                                         _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                                         
/* 3160 */                                         _jspx_th_c_005fwhen_005f7.setTest("${param.method =='showScheduleReports'}");
/* 3161 */                                         int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 3162 */                                         if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                                           for (;;) {
/* 3164 */                                             out.write("\n       ");
/* 3165 */                                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3166 */                                             out.write(10);
/* 3167 */                                             out.write(32);
/* 3168 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 3169 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3173 */                                         if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 3174 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                                         }
/*      */                                         
/* 3177 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 3178 */                                         out.write(10);
/* 3179 */                                         out.write(32);
/*      */                                         
/* 3181 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3182 */                                         _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 3183 */                                         _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 3184 */                                         int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 3185 */                                         if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                                           for (;;) {
/* 3187 */                                             out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/* 3188 */                                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3189 */                                             out.write("\n\t </a>\n ");
/* 3190 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 3191 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3195 */                                         if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 3196 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                                         }
/*      */                                         
/* 3199 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 3200 */                                         out.write(10);
/* 3201 */                                         out.write(32);
/* 3202 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 3203 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3207 */                                     if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 3208 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                                     }
/*      */                                     
/* 3211 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 3212 */                                     out.write("\n    </td>\n</tr> \n");
/*      */                                   } else {
/* 3214 */                                     out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 3215 */                                     out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3216 */                                     out.write("</a>\n     </td>\n </tr>   \n");
/*      */                                     
/* 3218 */                                     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3219 */                                     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3220 */                                     _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                     
/* 3222 */                                     _jspx_th_c_005fif_005f1.setTest("${category!='LAMP'}");
/* 3223 */                                     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3224 */                                     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                       for (;;) {
/* 3226 */                                         out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 3227 */                                         out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3228 */                                         out.write("</a>\n\t  </td>\n  </tr>   \n");
/* 3229 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3230 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3234 */                                     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3235 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                                     }
/*      */                                     
/* 3238 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3239 */                                     out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/* 3240 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3241 */                                     out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */                                   }
/* 3243 */                                   out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                                   
/* 3245 */                                   ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3246 */                                   _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 3247 */                                   _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_tiles_005fput_005f2);
/* 3248 */                                   int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 3249 */                                   if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                                     for (;;) {
/* 3251 */                                       out.write(10);
/*      */                                       
/* 3253 */                                       WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3254 */                                       _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 3255 */                                       _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                                       
/* 3257 */                                       _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 3258 */                                       int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 3259 */                                       if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                         for (;;) {
/* 3261 */                                           out.write("\n        ");
/* 3262 */                                           out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 3263 */                                           out.write(10);
/* 3264 */                                           out.write(32);
/* 3265 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 3266 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3270 */                                       if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 3271 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                                       }
/*      */                                       
/* 3274 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 3275 */                                       out.write(10);
/* 3276 */                                       out.write(32);
/*      */                                       
/* 3278 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3279 */                                       _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 3280 */                                       _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/* 3281 */                                       int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 3282 */                                       if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                                         for (;;) {
/* 3284 */                                           out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/* 3285 */                                           out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 3286 */                                           out.write("\n\t </a>\n ");
/* 3287 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 3288 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3292 */                                       if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 3293 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                                       }
/*      */                                       
/* 3296 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 3297 */                                       out.write(10);
/* 3298 */                                       out.write(32);
/* 3299 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 3300 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3304 */                                   if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 3305 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                                   }
/*      */                                   
/* 3308 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 3309 */                                   out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                                   
/* 3311 */                                   ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3312 */                                   _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 3313 */                                   _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_tiles_005fput_005f2);
/* 3314 */                                   int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 3315 */                                   if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                                     for (;;) {
/* 3317 */                                       out.write(10);
/*      */                                       
/* 3319 */                                       WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3320 */                                       _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 3321 */                                       _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                                       
/* 3323 */                                       _jspx_th_c_005fwhen_005f9.setTest("${param.method!='showMailServerConfiguration'}");
/* 3324 */                                       int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 3325 */                                       if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                                         for (;;) {
/* 3327 */                                           out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 3328 */                                           out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 3329 */                                           out.write("\n    </a>    \n ");
/* 3330 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 3331 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3335 */                                       if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 3336 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                                       }
/*      */                                       
/* 3339 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 3340 */                                       out.write(10);
/* 3341 */                                       out.write(32);
/*      */                                       
/* 3343 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3344 */                                       _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 3345 */                                       _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 3346 */                                       int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 3347 */                                       if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                                         for (;;) {
/* 3349 */                                           out.write(10);
/* 3350 */                                           out.write(9);
/* 3351 */                                           out.write(32);
/* 3352 */                                           out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 3353 */                                           out.write(10);
/* 3354 */                                           out.write(32);
/* 3355 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 3356 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3360 */                                       if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 3361 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                                       }
/*      */                                       
/* 3364 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 3365 */                                       out.write(10);
/* 3366 */                                       out.write(32);
/* 3367 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 3368 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3372 */                                   if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 3373 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                                   }
/*      */                                   
/* 3376 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 3377 */                                   out.write("\n    </td>\n</tr>\n\n\n");
/* 3378 */                                   if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 3379 */                                     out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                                     
/* 3381 */                                     ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3382 */                                     _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 3383 */                                     _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_tiles_005fput_005f2);
/* 3384 */                                     int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 3385 */                                     if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                                       for (;;) {
/* 3387 */                                         out.write(10);
/*      */                                         
/* 3389 */                                         WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3390 */                                         _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 3391 */                                         _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                                         
/* 3393 */                                         _jspx_th_c_005fwhen_005f10.setTest("${param.method!='SMSServerConfiguration'}");
/* 3394 */                                         int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 3395 */                                         if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                                           for (;;) {
/* 3397 */                                             out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 3398 */                                             out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 3399 */                                             out.write("\n    </a>\n ");
/* 3400 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 3401 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3405 */                                         if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 3406 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                                         }
/*      */                                         
/* 3409 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 3410 */                                         out.write(10);
/* 3411 */                                         out.write(32);
/*      */                                         
/* 3413 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3414 */                                         _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 3415 */                                         _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 3416 */                                         int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 3417 */                                         if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                                           for (;;) {
/* 3419 */                                             out.write("\n         ");
/* 3420 */                                             out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 3421 */                                             out.write(10);
/* 3422 */                                             out.write(32);
/* 3423 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 3424 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3428 */                                         if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 3429 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                                         }
/*      */                                         
/* 3432 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 3433 */                                         out.write(10);
/* 3434 */                                         out.write(32);
/* 3435 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 3436 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3440 */                                     if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 3441 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                                     }
/*      */                                     
/* 3444 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 3445 */                                     out.write("\n    </td>\n</tr>\n");
/*      */                                   }
/* 3447 */                                   out.write("\n\n\n ");
/*      */                                   
/* 3449 */                                   if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                                   {
/*      */ 
/* 3452 */                                     out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                                     
/* 3454 */                                     ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3455 */                                     _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 3456 */                                     _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_tiles_005fput_005f2);
/* 3457 */                                     int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 3458 */                                     if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                                       for (;;) {
/* 3460 */                                         out.write(10);
/*      */                                         
/* 3462 */                                         WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3463 */                                         _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 3464 */                                         _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                                         
/* 3466 */                                         _jspx_th_c_005fwhen_005f11.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 3467 */                                         int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 3468 */                                         if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                                           for (;;) {
/* 3470 */                                             out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 3471 */                                             out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 3472 */                                             out.write("\n    </a>\n ");
/* 3473 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 3474 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3478 */                                         if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 3479 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                                         }
/*      */                                         
/* 3482 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 3483 */                                         out.write(10);
/* 3484 */                                         out.write(32);
/*      */                                         
/* 3486 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3487 */                                         _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 3488 */                                         _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 3489 */                                         int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 3490 */                                         if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                                           for (;;) {
/* 3492 */                                             out.write(10);
/* 3493 */                                             out.write(9);
/* 3494 */                                             out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 3495 */                                             out.write(10);
/* 3496 */                                             out.write(32);
/* 3497 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 3498 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3502 */                                         if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 3503 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                                         }
/*      */                                         
/* 3506 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 3507 */                                         out.write(10);
/* 3508 */                                         out.write(32);
/* 3509 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 3510 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3514 */                                     if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 3515 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                                     }
/*      */                                     
/* 3518 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 3519 */                                     out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                                     
/* 3521 */                                     ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3522 */                                     _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 3523 */                                     _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_tiles_005fput_005f2);
/* 3524 */                                     int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 3525 */                                     if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                                       for (;;) {
/* 3527 */                                         out.write(10);
/*      */                                         
/* 3529 */                                         WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3530 */                                         _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 3531 */                                         _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                                         
/* 3533 */                                         _jspx_th_c_005fwhen_005f12.setTest("${uri !='/Upload.do'}");
/* 3534 */                                         int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 3535 */                                         if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                                           for (;;) {
/* 3537 */                                             out.write("   \n        ");
/*      */                                             
/* 3539 */                                             AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3540 */                                             _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 3541 */                                             _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_c_005fwhen_005f12);
/*      */                                             
/* 3543 */                                             _jspx_th_am_005fadminlink_005f0.setHref("/Upload.do");
/*      */                                             
/* 3545 */                                             _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 3546 */                                             int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 3547 */                                             if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 3548 */                                               if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3549 */                                                 out = _jspx_page_context.pushBody();
/* 3550 */                                                 _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 3551 */                                                 _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3554 */                                                 out.write("\n           ");
/* 3555 */                                                 out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 3556 */                                                 out.write("\n            ");
/* 3557 */                                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 3558 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3561 */                                               if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3562 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3565 */                                             if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 3566 */                                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                             }
/*      */                                             
/* 3569 */                                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 3570 */                                             out.write(10);
/* 3571 */                                             out.write(10);
/* 3572 */                                             out.write(32);
/* 3573 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 3574 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3578 */                                         if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 3579 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                                         }
/*      */                                         
/* 3582 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 3583 */                                         out.write(10);
/* 3584 */                                         out.write(32);
/*      */                                         
/* 3586 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3587 */                                         _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 3588 */                                         _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/* 3589 */                                         int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 3590 */                                         if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                                           for (;;) {
/* 3592 */                                             out.write(10);
/* 3593 */                                             out.write(9);
/* 3594 */                                             out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 3595 */                                             out.write(10);
/* 3596 */                                             out.write(32);
/* 3597 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 3598 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3602 */                                         if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 3603 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */                                         }
/*      */                                         
/* 3606 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 3607 */                                         out.write(10);
/* 3608 */                                         out.write(32);
/* 3609 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 3610 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3614 */                                     if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 3615 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                                     }
/*      */                                     
/* 3618 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 3619 */                                     out.write("\n    </td>\n</tr>\n \n ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3623 */                                   out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                                   
/* 3625 */                                   ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3626 */                                   _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 3627 */                                   _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_tiles_005fput_005f2);
/* 3628 */                                   int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 3629 */                                   if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                                     for (;;) {
/* 3631 */                                       out.write(10);
/*      */                                       
/* 3633 */                                       WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3634 */                                       _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 3635 */                                       _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                                       
/* 3637 */                                       _jspx_th_c_005fwhen_005f13.setTest("${uri !='/admin/userconfiguration.do'}");
/* 3638 */                                       int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 3639 */                                       if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                                         for (;;) {
/* 3641 */                                           out.write("\n    \n        ");
/*      */                                           
/* 3643 */                                           AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3644 */                                           _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 3645 */                                           _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f13);
/*      */                                           
/* 3647 */                                           _jspx_th_am_005fadminlink_005f1.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                                           
/* 3649 */                                           _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 3650 */                                           int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 3651 */                                           if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 3652 */                                             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 3653 */                                               out = _jspx_page_context.pushBody();
/* 3654 */                                               _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 3655 */                                               _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 3658 */                                               out.write("\n       ");
/* 3659 */                                               out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 3660 */                                               out.write("\n        ");
/* 3661 */                                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 3662 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 3665 */                                             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 3666 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 3669 */                                           if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 3670 */                                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                                           }
/*      */                                           
/* 3673 */                                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 3674 */                                           out.write(10);
/* 3675 */                                           out.write(10);
/* 3676 */                                           out.write(32);
/* 3677 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 3678 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3682 */                                       if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 3683 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                                       }
/*      */                                       
/* 3686 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 3687 */                                       out.write(10);
/* 3688 */                                       out.write(32);
/*      */                                       
/* 3690 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3691 */                                       _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 3692 */                                       _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/* 3693 */                                       int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 3694 */                                       if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */                                         for (;;) {
/* 3696 */                                           out.write(10);
/* 3697 */                                           out.write(9);
/* 3698 */                                           out.write(32);
/* 3699 */                                           out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 3700 */                                           out.write(10);
/* 3701 */                                           out.write(32);
/* 3702 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 3703 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3707 */                                       if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 3708 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */                                       }
/*      */                                       
/* 3711 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 3712 */                                       out.write(10);
/* 3713 */                                       out.write(32);
/* 3714 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 3715 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3719 */                                   if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 3720 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */                                   }
/*      */                                   
/* 3723 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 3724 */                                   out.write("\n    </td>\n</tr>\n   \n\n ");
/* 3725 */                                   if (!OEMUtil.isOEM()) {
/* 3726 */                                     out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                                     
/* 3728 */                                     ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3729 */                                     _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 3730 */                                     _jspx_th_c_005fchoose_005f14.setParent(_jspx_th_tiles_005fput_005f2);
/* 3731 */                                     int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 3732 */                                     if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */                                       for (;;) {
/* 3734 */                                         out.write("\n   ");
/*      */                                         
/* 3736 */                                         WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3737 */                                         _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 3738 */                                         _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */                                         
/* 3740 */                                         _jspx_th_c_005fwhen_005f14.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 3741 */                                         int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 3742 */                                         if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                                           for (;;) {
/* 3744 */                                             out.write("\n    ");
/*      */                                             
/* 3746 */                                             AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3747 */                                             _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 3748 */                                             _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f14);
/*      */                                             
/* 3750 */                                             _jspx_th_am_005fadminlink_005f2.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                                             
/* 3752 */                                             _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 3753 */                                             int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 3754 */                                             if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 3755 */                                               if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 3756 */                                                 out = _jspx_page_context.pushBody();
/* 3757 */                                                 _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 3758 */                                                 _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3761 */                                                 out.write(10);
/* 3762 */                                                 out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 3763 */                                                 out.write("\n    ");
/* 3764 */                                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 3765 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3768 */                                               if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 3769 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3772 */                                             if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 3773 */                                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                                             }
/*      */                                             
/* 3776 */                                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 3777 */                                             out.write("\n   ");
/* 3778 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 3779 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3783 */                                         if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 3784 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                                         }
/*      */                                         
/* 3787 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 3788 */                                         out.write("\n   ");
/*      */                                         
/* 3790 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3791 */                                         _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 3792 */                                         _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/* 3793 */                                         int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 3794 */                                         if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                                           for (;;) {
/* 3796 */                                             out.write(10);
/* 3797 */                                             out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 3798 */                                             out.write("\n   ");
/* 3799 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 3800 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3804 */                                         if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 3805 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */                                         }
/*      */                                         
/* 3808 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 3809 */                                         out.write(10);
/* 3810 */                                         out.write(32);
/* 3811 */                                         out.write(32);
/* 3812 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 3813 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3817 */                                     if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 3818 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */                                     }
/*      */                                     
/* 3821 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 3822 */                                     out.write("\n </td>\n</tr>\n  ");
/*      */                                   }
/* 3824 */                                   out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                                   
/* 3826 */                                   ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3827 */                                   _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 3828 */                                   _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_tiles_005fput_005f2);
/* 3829 */                                   int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 3830 */                                   if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                                     for (;;) {
/* 3832 */                                       out.write("\n   ");
/*      */                                       
/* 3834 */                                       WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3835 */                                       _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 3836 */                                       _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                                       
/* 3838 */                                       _jspx_th_c_005fwhen_005f15.setTest("${param.method!='showDataCleanUp'}");
/* 3839 */                                       int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 3840 */                                       if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                                         for (;;) {
/* 3842 */                                           out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 3843 */                                           out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 3844 */                                           out.write("\n    </a>\n   ");
/* 3845 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 3846 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3850 */                                       if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 3851 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                                       }
/*      */                                       
/* 3854 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 3855 */                                       out.write("\n   ");
/*      */                                       
/* 3857 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3858 */                                       _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 3859 */                                       _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/* 3860 */                                       int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 3861 */                                       if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                                         for (;;) {
/* 3863 */                                           out.write(10);
/* 3864 */                                           out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 3865 */                                           out.write("\n   ");
/* 3866 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 3867 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3871 */                                       if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 3872 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */                                       }
/*      */                                       
/* 3875 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 3876 */                                       out.write(10);
/* 3877 */                                       out.write(32);
/* 3878 */                                       out.write(32);
/* 3879 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 3880 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3884 */                                   if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 3885 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */                                   }
/*      */                                   
/* 3888 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 3889 */                                   out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 3890 */                                   out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\"><tr>\n<td width=\"80%\" class=\"leftlinksquicknote\">");
/* 3891 */                                   out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 3892 */                                   out.write("</td>\n<td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 3893 */                                   if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                     return;
/* 3895 */                                   out.write("/img_quicknote.gif\" hspace=\"5\"></td>\n</tr>\n<tr>\n<td colspan=\"2\" class=\"quicknote\">");
/* 3896 */                                   out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.quicknote"));
/* 3897 */                                   out.write("</td>\n</tr>\n</table>\n\n");
/* 3898 */                                   int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3899 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3902 */                                 if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3903 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3906 */                               if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3907 */                                 this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                               }
/*      */                               
/* 3910 */                               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3911 */                               out.write(10);
/*      */                               
/* 3913 */                               PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3914 */                               _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3915 */                               _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                               
/* 3917 */                               _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                               
/* 3919 */                               _jspx_th_tiles_005fput_005f3.setType("string");
/* 3920 */                               int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3921 */                               if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 3922 */                                 if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3923 */                                   out = _jspx_page_context.pushBody();
/* 3924 */                                   _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 3925 */                                   _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 3928 */                                   out.write(10);
/* 3929 */                                   out.write(10);
/*      */                                   
/* 3931 */                                   FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/* 3932 */                                   String usrtype = fd.getUserType();
/* 3933 */                                   if (!EnterpriseUtil.isAdminServer())
/*      */                                   {
/* 3935 */                                     if (request.getAttribute("ErrorMessage") != null)
/*      */                                     {
/*      */ 
/* 3938 */                                       out.write("\n <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n  <tr>\n   <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n   <td width=\"95%\" class=\"message\"> ");
/* 3939 */                                       out.print(request.getAttribute("ErrorMessage"));
/* 3940 */                                       out.write("</td>\n  </tr>\n </table>\n <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n   <td  height=\"10\" ><img src=\"../images/spacer.gif\" ></td>\n  </tr>\n  </table>\n ");
/*      */                                     }
/* 3942 */                                     out.write(10);
/* 3943 */                                     out.write(32);
/*      */                                     
/* 3945 */                                     if (request.getAttribute("MessageToDisplay") != null)
/*      */                                     {
/*      */ 
/* 3948 */                                       out.write("\n <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n   <tr>\n     <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n     <td width=\"95%\" class=\"message\"> ");
/* 3949 */                                       out.print(request.getAttribute("ErrorMessage"));
/* 3950 */                                       out.write("</td>\n    </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n   <tr>\n    <td  height=\"10\" ><img src=\"../images/spacer.gif\" ></td>\n   </tr>\n  </table>\n ");
/*      */                                     }
/* 3952 */                                     out.write(10);
/* 3953 */                                     out.write(10);
/* 3954 */                                     out.write(32);
/*      */                                     
/* 3956 */                                     if (request.getAttribute("SuccessMessage") != null)
/*      */                                     {
/*      */ 
/* 3959 */                                       out.write("\n  <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n  <tr>\n   <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n   <td width=\"95%\" class=\"message\"> ");
/* 3960 */                                       out.print(request.getAttribute("SuccessMessage"));
/* 3961 */                                       out.write("</td>\n  </tr>\n </table>\n <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n <tr>\n  <td  height=\"10\" ><img src=\"../images/spacer.gif\" ></td>\n </tr>\n </table>\n");
/*      */                                     }
/*      */                                   }
/* 3964 */                                   out.write("\n\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"itadmin-hide\">\n <tr>\n  <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 3965 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 3966 */                                   out.write(" &gt;<span class=\"bcactive\"> ");
/* 3967 */                                   out.print(FormatUtil.getString("am.webclient.addonproduct.servicedesk.title"));
/* 3968 */                                   out.write("</span></td>\n </tr>\n</table>\n\n\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"itadmin-hide\">\n\n\n  <tr class=\"tabBtmLine\">\n\n   <td nowrap=\"nowrap\">\n          \t<table id=\"InnerTab\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"innertab_btm_space\">\n              <tbody>\n                <tr>\n                  <td width=\"17\">\n\n                  </td>\n\n                  <td><table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                      <tbody>\n\n                        <tr>\n                          <td class=\"tbSelected_Left\" id=\"profilelink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                          <td class=\"tbSelected_Middle\" id=\"profilelink\">\n                       <a href=\"javascript:showHide('Product Settings','Polling Interval')\">&nbsp;<span  class=\"tabLink\" >");
/* 3969 */                                   out.print(FormatUtil.getString("Product Settings"));
/* 3970 */                                   out.write("</span></a>\n                          </td>\n                          <td class=\"tbselected_Right\" id=\"profilelink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                        </tr>\n                      </tbody>\n\n                    </table>\n                  </td>\n\t\t  ");
/*      */                                   
/* 3972 */                                   if (!EnterpriseUtil.isAdminServer())
/*      */                                   {
/*      */ 
/* 3975 */                                     out.write("\n                  <td><table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                      <tbody>\n                        <tr>\n                          <td class=\"tbUnselected_Left\" id=\"permlink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                          <td class=\"tbUnselected_Middle\" id=\"permlink\">\n                          <a href=\"javascript:showHide('Polling Interval','Product Settings')\">&nbsp;<span  class=\"tabLink\" >");
/* 3976 */                                     out.print(FormatUtil.getString("Poll Interval"));
/* 3977 */                                     out.write("</span></a>\n\n                          </td>\n                          <td class=\"tbUnselected_Right\" id=\"permlink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n                        </tr>\n                      </tbody>\n                    </table>\n                  </td>\n\t\t  ");
/*      */                                   }
/* 3979 */                                   out.write("\n\n </tr></tbody></table></td></tr>\n </table>\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<div id=\"Product Settings\" style=\"display:block\">\n\n<form name=\"form1\" style=\"display:inline\">\n  <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\"  class=\"lrtborder itadmin-no-decor\">\n    <tr>\n      <td width=\"72%\" height=\"31\" class=\"tableheading itadmin-no-decor\" >&nbsp;");
/* 3980 */                                   out.print(FormatUtil.getString("am.webclient.addonproduct.settings"));
/* 3981 */                                   out.write("</td>\n    </tr>\n  </table>\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n   <tr>\n    <td height=\"28\">\n  \t<table valign=\"center\" width=\"100%\" cellpadding=\"1\" cellspacing=\"0\"  border=\"0\" >\n\t\t    ");
/*      */                                   
/* 3983 */                                   if (!com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                   {
/*      */ 
/* 3986 */                                     out.write("\n         <tr class=\"bodytextbold\">\n\t     <!--<td width=\"5%\" height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">\n          <input type=\"checkbox\" name=\"headercheckbox\"  onClick=\"javascript:fnSelectAll(this)\">\t</td>-->\n\t\t  <td width=\"25%\" height=\"28\" valign=\"center\" class=\"columnheadingnotop\">");
/* 3987 */                                     out.print(FormatUtil.getString("am.webclient.addonproduct.productname"));
/* 3988 */                                     out.write("</td>\n\t     <td width=\"15%\" height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">");
/* 3989 */                                     out.print(FormatUtil.getString("Host"));
/* 3990 */                                     out.write("</td>\n\t\t  <td width=\"10%\" height=\"28\" align=\"center\"  class=\"columnheadingnotop\">");
/* 3991 */                                     out.print(FormatUtil.getString("Port"));
/* 3992 */                                     out.write("</td>\n\t\t <td width=\"25%\" height=\"28\" align=\"center\"  class=\"columnheadingnotop\">");
/* 3993 */                                     out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3994 */                                     out.write("</td>\n\t\t  <td width=\"5%\" height=\"28\" align=\"center\" class=\"columnheadingnotop\">");
/* 3995 */                                     out.print(FormatUtil.getString("am.webclient.common.availability.status"));
/* 3996 */                                     out.write("</td>\n\t\t <td class=\"columnheadingnotop\" height=\"28\" align=\"center\" width=\"10%\">&nbsp;</td>\n\t\t <td class=\"columnheadingnotop\" height=\"28\" align=\"center\" width=\"10%\">");
/* 3997 */                                     out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/* 3998 */                                     out.write("</td>\n\t\t <td class=\"columnheadingnotop\" height=\"28\" align=\"center\" width=\"10%\">&nbsp;</td>\n\t\t \n\t</tr>\n\t");
/*      */                                   } else {
/* 4000 */                                     out.write("\n\t\t <td width=\"15%\" height=\"28\" valign=\"center\" class=\"columnheadingnotop\">");
/* 4001 */                                     out.print(FormatUtil.getString("am.webclient.addonproduct.productname"));
/* 4002 */                                     out.write("</td>\n\t     \t <td width=\"15%\" height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">");
/* 4003 */                                     out.print(FormatUtil.getString("Host"));
/* 4004 */                                     out.write("</td>\n\t\t <td width=\"15%\" height=\"28\" align=\"center\"  class=\"columnheadingnotop\">");
/* 4005 */                                     out.print(FormatUtil.getString("Port"));
/* 4006 */                                     out.write("</td>\n\t\t <td width=\"5%\" class=\"columnheadingnotop\" height=\"28\" align=\"center\">");
/* 4007 */                                     out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/* 4008 */                                     out.write("</td>\n\t\t <td width=\"10%\" height=\"28\" valign=\"center\" class=\"columnheadingnotop\"></td>\n\t\n\t");
/*      */                                   }
/* 4010 */                                   String SDeskHost = "-";
/* 4011 */                                   String SDeskPort = "-";
/* 4012 */                                   String SDeskEnabled = "";
/* 4013 */                                   String SDeskTitle = FormatUtil.getString("HelpDesk is not Configured", new String[] { OEMUtil.getOEMString("am.webclient.helpdesk") });
/* 4014 */                                   if (request.getAttribute("SDeskHost") != null)
/*      */                                   {
/* 4016 */                                     SDeskHost = (String)request.getAttribute("SDeskHost");
/*      */                                   }
/* 4018 */                                   if (request.getAttribute("SDeskPort") != null)
/*      */                                   {
/* 4020 */                                     SDeskPort = (String)request.getAttribute("SDeskPort");
/*      */                                   }
/* 4022 */                                   if (!com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                   {
/*      */ 
/* 4025 */                                     out.write("\n\t<tr>\n\t  <td height=\"20\" class=\"whitegrayborder\" height=\"28\" valign=\"center\" ><img src=/images/jumpto_sdp.gif width=16 height=14 hspace=10 >");
/* 4026 */                                     out.print(FormatUtil.getString("am.webclient.helpdesk"));
/* 4027 */                                     out.write("</td>\n\t  <td height=\"15\" class=\"whitegrayborder\" height=\"28\" valign=\"center\" >");
/* 4028 */                                     out.print(SDeskHost);
/* 4029 */                                     out.write("</td>\n\t  <td height=\"10\" class=\"whitegrayborder\" height=\"28\" align=\"center\" >");
/* 4030 */                                     out.print(SDeskPort);
/* 4031 */                                     out.write("</td>\n\t  <td height=\"25\" class=\"whitegrayborder\" height=\"28\" align=\"center\" >N/A</td>");
/* 4032 */                                     out.write("\n\t  <td height\"5\" class=\"whitegrayborder\" height=\"28\" align=\"center\" >N/A</td>");
/* 4033 */                                     out.write("\n\t\t\n\t  <td class=\"whitegrayborder\" height=\"28\" align=\"center\" width=\"10%\">&nbsp;</td>\n\t   ");
/* 4034 */                                     if (request.getAttribute("SDeskHost") == null) {
/* 4035 */                                       out.write("\n\t\t<td class=\"whitegrayborder\" height=\"28\" align=\"center\" width=\"10%\"><a href=\"/extDeviceAction.do?method=addNewExtDevice&prodName=ServiceDesk\" class=\"bodytext\">");
/* 4036 */                                       out.print(FormatUtil.getString("Add"));
/* 4037 */                                       out.write(" </a></td>\n\t\t<td class=\"whitegrayborder\" height=\"28\"  align=\"left\" width=\"10%\">&nbsp;</td>\n\t   ");
/*      */                                     } else {
/* 4039 */                                       out.write("\n\t\t<td class=\"whitegrayborder\" height=\"28\" align=\"center\" title=\"");
/* 4040 */                                       out.print(FormatUtil.getString("Edit"));
/* 4041 */                                       out.write("\" width=\"10%\"><a href=\"/extDeviceAction.do?method=editExtDevices&prodName=ServiceDesk\">");
/* 4042 */                                       if (_jspx_meth_logic_005fpresent_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                         return;
/* 4044 */                                       out.write("</a></td>\n\t\t<td class=\"whitegrayborder\" height=\"28\" align=\"left\" title=\"");
/* 4045 */                                       out.print(FormatUtil.getString("am.webclient.managedserver.collectdatanow"));
/* 4046 */                                       out.write("\" width=\"10%\"><a href=\"/extDeviceAction.do?method=fetchDataNowForExtDevice&prodName=ServiceDesk\" class=\"bodytext\"><img src=\"/images/icon_refresh.gif\"  border=\"0\"></a></td>");
/* 4047 */                                       out.write("\n      \n\t  ");
/*      */                                     }
/* 4049 */                                     out.write("\n\t</tr>\n\t\n\t");
/*      */                                   } else {
/* 4051 */                                     out.write(" \n\t <tr>\n\t  <td height=\"20\" class=\"whitegrayborder\" height=\"28\" title=\"");
/* 4052 */                                     out.print(FormatUtil.getString(SDeskTitle));
/* 4053 */                                     out.write("\" valign=\"center\" ><img src=/images/jumpto_sdp.gif width=16 height=14 hspace=10 >");
/* 4054 */                                     out.print(OEMUtil.getOEMString("am.servicedeskplus.productname"));
/* 4055 */                                     out.write("</td>\n\t  <td height=\"15\" class=\"whitegrayborder\" height=\"28\" valign=\"center\" >");
/* 4056 */                                     out.print(SDeskHost);
/* 4057 */                                     out.write("</td>\n\t  <td height=\"10\" class=\"whitegrayborder\" height=\"28\" align=\"center\" >");
/* 4058 */                                     out.print(SDeskPort);
/* 4059 */                                     out.write("</td>\n\t  <td><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t  <tr><td height=\"25\" class=\"whitegrayborder\" height=\"28\" align=\"center\" >N/A</td>");
/* 4060 */                                     out.write("\n\t  ");
/* 4061 */                                     if (request.getAttribute("SDeskHost") == null) {
/* 4062 */                                       out.write("\n\t\t<td class=\"whitegrayborder\" height=\"28\" align=\"center\" width=\"10%\"><a href=\"/extDeviceAction.do?method=addNewExtDevice&prodName=ServiceDesk\" class=\"bodytext\">");
/* 4063 */                                       out.print(FormatUtil.getString("Add"));
/* 4064 */                                       out.write(" </a></td>\n\t  ");
/*      */                                     } else {
/* 4066 */                                       out.write("\n\t\t<td class=\"whitegrayborder\" height=\"28\" align=\"center\" title=\"");
/* 4067 */                                       out.print(FormatUtil.getString("Edit"));
/* 4068 */                                       out.write("\" width=\"10%\"><a href=\"/extDeviceAction.do?method=editExtDevices&prodName=ServiceDesk\">");
/* 4069 */                                       if (_jspx_meth_logic_005fpresent_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                         return;
/* 4071 */                                       out.write("</a></td>\n\t\t");
/*      */                                     }
/* 4073 */                                     out.write("\n\t</tr></table>\n\t  </td>\n\t</tr>\n\t ");
/*      */                                   }
/*      */                                   
/* 4076 */                                   if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                                   {
/*      */ 
/* 4079 */                                     String OpManHost = "-";
/* 4080 */                                     String OpManPort = "-";
/* 4081 */                                     String OpManLastDC = "-";
/* 4082 */                                     String OpManId = null;
/* 4083 */                                     String OpManEnabled = "";
/* 4084 */                                     String OpManStatus = "";
/*      */                                     
/* 4086 */                                     if (request.getAttribute("OpManHost") != null)
/*      */                                     {
/* 4088 */                                       OpManHost = (String)request.getAttribute("OpManHost");
/*      */                                     }
/* 4090 */                                     if (request.getAttribute("OpManPort") != null)
/*      */                                     {
/* 4092 */                                       OpManPort = (String)request.getAttribute("OpManPort");
/*      */                                     }
/* 4094 */                                     if ((request.getAttribute("OpManLastDC") != null) && (!request.getAttribute("OpManLastDC").equals("-1")))
/*      */                                     {
/* 4096 */                                       OpManLastDC = FormatUtil.formatDT((String)request.getAttribute("OpManLastDC"));
/*      */                                     }
/* 4098 */                                     if (request.getAttribute("OpManStatus") != null) {
/* 4099 */                                       OpManStatus = (String)request.getAttribute("OpManStatus");
/*      */                                     }
/* 4101 */                                     if ((!com.adventnet.appmanager.util.Constants.isIt360) && (!EnterpriseUtil.isAdminServer()) && (DBUtil.getGlobalConfigValueasBoolean("am.opmanager.addon")))
/*      */                                     {
/*      */ 
/* 4104 */                                       out.write("\n    <tr>\n     <td height=\"20\" class=\"yellowgrayborder\" height=\"28\"  valign=\"center\" ><img src=/images/jumpto_OpM.gif width=16 height=14 hspace=10 >");
/* 4105 */                                       out.print(OEMUtil.getOEMString("am.opmanager.productname"));
/* 4106 */                                       out.write("\n     </td>\n     <td height=\"15\" class=\"yellowgrayborder\" height=\"28\" valign=\"center\" >");
/* 4107 */                                       out.print(OpManHost);
/* 4108 */                                       out.write("</td>\n     <td height=\"10\" class=\"yellowgrayborder\" height=\"28\" align=\"center\" >");
/* 4109 */                                       out.print(OpManPort);
/* 4110 */                                       out.write("</td>\n     <td height=\"25\" class=\"yellowgrayborder\" height=\"28\" align=\"center\" >");
/* 4111 */                                       out.print(OpManLastDC);
/* 4112 */                                       out.write("</td>\n\t ");
/*      */                                       
/* 4114 */                                       if ((request.getAttribute("OpManStatus") != null) && (((String)request.getAttribute("OpManStatus")).equals("up")))
/*      */                                       {
/*      */ 
/* 4117 */                                         out.write("\t\n\t<td height=\"5\" height=\"28\" align=\"center\" title=\"");
/* 4118 */                                         out.print(FormatUtil.getString("am.webclient.common.product.status.up"));
/* 4119 */                                         out.write("\"><img src=\"/images/icon_availability_up_mover.gif\" border=\"0\" /></td>\n\t");
/* 4120 */                                       } else if ((request.getAttribute("OpManStatus") != null) && (!((String)request.getAttribute("OpManStatus")).equals("up")))
/*      */                                       {
/* 4122 */                                         out.write("\n\t<td height=\"5\" height=\"28\" align=\"center\" title=\"");
/* 4123 */                                         out.print(FormatUtil.getString("am.webclient.common.product.status.down"));
/* 4124 */                                         out.write("\"><img src=\"/images/icon_legend_critical.gif\" border=\"0\" /></td>\n\t\t");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4128 */                                         out.write("\n\t\t<td height\"5\" class=\"whitegrayborder\" height=\"28\" align=\"center\" >");
/* 4129 */                                         out.print(OpManStatus);
/* 4130 */                                         out.write("</td>\n\t\t");
/*      */                                       }
/*      */                                       
/* 4133 */                                       if (request.getAttribute("OpManHost") == null)
/*      */                                       {
/*      */ 
/* 4136 */                                         out.write("\n\t  <td class=\"yellowgrayborder\" height=\"28\" align=\"center\" width=\"10%\">&nbsp;</td>\n      <td class=\"yellowgrayborder\" height=\"28\" align=\"center\" width=\"10%\"><a href=\"/extDeviceAction.do?method=addNewExtDevice&prodName=OpManager\" class=\"bodytext\">");
/* 4137 */                                         out.print(FormatUtil.getString("Add"));
/* 4138 */                                         out.write(" </a></td>\n\t  <td class=\"yellowgrayborder\" height=\"28\"  align=\"left\" width=\"10%\">&nbsp;</td>\n\t  ");
/*      */                                       }
/*      */                                       else {
/* 4141 */                                         if ((request.getAttribute("OpManEnabled") != null) && (((String)request.getAttribute("OpManEnabled")).equals("true")))
/*      */                                         {
/*      */ 
/* 4144 */                                           out.write("\n\t\t    <td height=\"28\" class=\"yellowgrayborder\" align=\"right\" title=\"");
/* 4145 */                                           out.print(FormatUtil.getString("am.webclient.schedulereport.showwschedule.enable.text"));
/* 4146 */                                           out.write(34);
/* 4147 */                                           out.write(62);
/* 4148 */                                           out.write(32);
/* 4149 */                                           if (_jspx_meth_logic_005fpresent_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                             return;
/* 4151 */                                           out.write("</td>\n\t\t ");
/*      */                                         }
/*      */                                         else {
/* 4154 */                                           out.write("\n\t\t\t<td height=\"28\" class=\"yellowgrayborder\" align=\"right\" title=\"");
/* 4155 */                                           out.print(FormatUtil.getString("am.webclient.schedulereport.showwschedule.disable.text"));
/* 4156 */                                           out.write(34);
/* 4157 */                                           out.write(62);
/* 4158 */                                           if (_jspx_meth_logic_005fpresent_005f6(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                             return;
/* 4160 */                                           out.write("</td>\n\t\t  ");
/*      */                                         }
/* 4162 */                                         out.write("\n\t\t  <td class=\"yellowgrayborder\" height=\"28\"  align=\"center\" title=\"");
/* 4163 */                                         out.print(FormatUtil.getString("Edit"));
/* 4164 */                                         out.write("\" width=\"10%\"><a href=\"/extDeviceAction.do?method=editExtDevices&prodName=OpManager\">");
/* 4165 */                                         if (_jspx_meth_logic_005fpresent_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                           return;
/* 4167 */                                         out.write("</a></td>\n\t\t  <td class=\"yellowgrayborder\" height=\"28\" align=\"center\" title=\"");
/* 4168 */                                         out.print(FormatUtil.getString("am.webclient.managedserver.collectdatanow"));
/* 4169 */                                         out.write("\" width=\"10%\"><a href=\"/extDeviceAction.do?method=fetchDataNowForExtDevice&prodName=OpManager\" class=\"bodytext\"><img src=\"/images/icon_refresh.gif\"  border=\"0\"></a></td>\n\t\t  \n      ");
/*      */                                       }
/*      */                                     }
/*      */                                     
/* 4173 */                                     out.write("\n\t\t </tr>\n\n\t  ");
/*      */                                     
/*      */ 
/* 4176 */                                     String OpStorHost = "-";
/* 4177 */                                     String OpStorPort = "-";
/* 4178 */                                     String OpStorLastDC = "-";
/* 4179 */                                     String OpStorId = null;
/* 4180 */                                     String OpStorEnabled = "";
/* 4181 */                                     String OpStorStatus = "";
/* 4182 */                                     String OpStorTitle = FormatUtil.getString("OpStor is not Configured", new String[] { OEMUtil.getOEMString("am.opstor.productname") });
/* 4183 */                                     if (request.getAttribute("OpStorHost") != null)
/*      */                                     {
/* 4185 */                                       OpStorHost = (String)request.getAttribute("OpStorHost");
/*      */                                     }
/* 4187 */                                     if (request.getAttribute("OpStorPort") != null)
/*      */                                     {
/* 4189 */                                       OpStorPort = (String)request.getAttribute("OpStorPort");
/*      */                                     }
/* 4191 */                                     if ((request.getAttribute("OpStorLastDC") != null) && (!request.getAttribute("OpStorLastDC").equals("-1")))
/*      */                                     {
/* 4193 */                                       OpStorLastDC = FormatUtil.formatDT((String)request.getAttribute("OpStorLastDC"));
/*      */                                     }
/* 4195 */                                     if (request.getAttribute("OpStorStatus") != null)
/*      */                                     {
/* 4197 */                                       OpStorStatus = (String)request.getAttribute("OpStorStatus");
/*      */                                     }
/* 4199 */                                     if (!com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                     {
/* 4201 */                                       if ((!EnterpriseUtil.isAdminServer()) && (DBUtil.getGlobalConfigValueasBoolean("am.opstor.addon")))
/*      */                                       {
/*      */ 
/* 4204 */                                         out.write("\n    <tr>\n   <td height=\"20\" class=\"whitegrayborder\" height=\"28\" valign=\"center\" ><img src=/images/jumpto_OpM.gif width=16 height=14 hspace=10 >");
/* 4205 */                                         out.print(OEMUtil.getOEMString("am.opstor.productname"));
/* 4206 */                                         out.write("\n    </td>\n   </td>\n    <td height=\"15\" class=\"whitegrayborder\" height=\"28\" valign=\"center\" >");
/* 4207 */                                         out.print(OpStorHost);
/* 4208 */                                         out.write("</td>\n    <td height=\"10\" class=\"whitegrayborder\" height=\"28\" align=\"center\" >");
/* 4209 */                                         out.print(OpStorPort);
/* 4210 */                                         out.write("</td>\n    <td height=\"25\" class=\"whitegrayborder\" height=\"28\" align=\"center\" >");
/* 4211 */                                         out.print(OpStorLastDC);
/* 4212 */                                         out.write("</td>\n\t");
/*      */                                         
/* 4214 */                                         if ((request.getAttribute("OpStorStatus") != null) && (((String)request.getAttribute("OpStorStatus")).equals("up")))
/*      */                                         {
/*      */ 
/* 4217 */                                           out.write("\t\n\t<td height=\"5\" height=\"28\" align=\"center\" title=\"");
/* 4218 */                                           out.print(FormatUtil.getString("am.webclient.common.product.status.up"));
/* 4219 */                                           out.write("\"><img src=\"/images/icon_availability_up_mover.gif\" border=\"0\" /></td>\n\t");
/* 4220 */                                         } else if ((request.getAttribute("OpStorStatus") != null) && (!((String)request.getAttribute("OpStorStatus")).equals("up")))
/*      */                                         {
/* 4222 */                                           out.write("\n\t<td height=\"5\" height=\"28\" align=\"center\" title=\"");
/* 4223 */                                           out.print(FormatUtil.getString("am.webclient.common.product.status.down"));
/* 4224 */                                           out.write("\"><img src=\"/images/icon_legend_critical.gif\" border=\"0\" /></td>\t\n\t");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 4228 */                                           out.write("\n\t\t<td height\"5\" class=\"whitegrayborder\" height=\"28\" align=\"center\" >");
/* 4229 */                                           out.print(OpStorStatus);
/* 4230 */                                           out.write("</td>\n\t\t");
/*      */                                         }
/*      */                                         
/* 4233 */                                         if (request.getAttribute("OpStorHost") == null)
/*      */                                         {
/*      */ 
/* 4236 */                                           out.write("\n\t  <td class=\"whitegrayborder\" height=\"28\" align=\"center\" width=\"10%\">&nbsp;</td>\n      <td class=\"whitegrayborder\" height=\"28\" align=\"center\" width=\"10%\"><a href=\"/extDeviceAction.do?method=addNewExtDevice&prodName=OpStor\" class=\"bodytext\">");
/* 4237 */                                           out.print(FormatUtil.getString("Add"));
/* 4238 */                                           out.write("</a></td>\n\t  <td class=\"whitegrayborder\" height=\"28\"  align=\"left\" width=\"10%\">&nbsp;</td>\n    ");
/*      */                                         } else {
/* 4240 */                                           if ((request.getAttribute("OpStorEnabled") != null) && (((String)request.getAttribute("OpStorEnabled")).equals("true")))
/*      */                                           {
/*      */ 
/* 4243 */                                             out.write("\n    <td height=\"28\" class=\"whitegrayborder\" align=\"right\" title=\"");
/* 4244 */                                             out.print(FormatUtil.getString("am.webclient.schedulereport.showwschedule.enable.text"));
/* 4245 */                                             out.write(34);
/* 4246 */                                             out.write(62);
/* 4247 */                                             out.write(32);
/* 4248 */                                             if (_jspx_meth_logic_005fpresent_005f8(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                               return;
/* 4250 */                                             out.write("</td>\n   ");
/*      */                                           }
/*      */                                           else {
/* 4253 */                                             out.write("\n   <td height=\"28\" class=\"whitegrayborder\" align=\"right\" title=\"");
/* 4254 */                                             out.print(FormatUtil.getString("am.webclient.schedulereport.showwschedule.disable.text"));
/* 4255 */                                             out.write(34);
/* 4256 */                                             out.write(62);
/* 4257 */                                             if (_jspx_meth_logic_005fpresent_005f9(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                               return;
/* 4259 */                                             out.write("</td>\n   ");
/*      */                                           }
/* 4261 */                                           out.write("\n   \t\t<td class=\"whitegrayborder\" height=\"28\"  align=\"center\" title=\"");
/* 4262 */                                           out.print(FormatUtil.getString("Edit"));
/* 4263 */                                           out.write("\" width=\"10%\"><a href=\"/extDeviceAction.do?method=editExtDevices&prodName=OpStor\">");
/* 4264 */                                           if (_jspx_meth_logic_005fpresent_005f10(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                             return;
/* 4266 */                                           out.write("</a></td>\n      <td class=\"whitegrayborder\" height=\"28\" align=\"center\" title=\"");
/* 4267 */                                           out.print(FormatUtil.getString("am.webclient.managedserver.collectdatanow"));
/* 4268 */                                           out.write("\" width=\"10%\"><a href=\"/extDeviceAction.do?method=fetchDataNowForExtDevice&prodName=OpStor\" class=\"bodytext\"><img src=\"/images/icon_refresh.gif\"  border=\"0\"></a></td>\n      \n    ");
/*      */                                         }
/*      */                                         
/*      */ 
/* 4272 */                                         out.write("\n   </tr>\n   ");
/*      */                                       }
/*      */                                     } else {
/* 4275 */                                       out.write("\n\t<tr>\n   \t\t<td height=\"20\" width=\"25%\" class=\"whitegrayborder\" height=\"28\" title=\"");
/* 4276 */                                       out.print(FormatUtil.getString(OpStorTitle));
/* 4277 */                                       out.write("\" valign=\"center\" ><img src=/images/jumpto_OpM.gif width=16 height=14 hspace=10 >");
/* 4278 */                                       out.print(OEMUtil.getOEMString("am.opstor.productname"));
/* 4279 */                                       out.write("\n    \t\t</td>\n    \t\t<td height=\"15\" width=\"15%\" class=\"whitegrayborder\" height=\"28\" valign=\"center\" >");
/* 4280 */                                       out.print(OpStorHost);
/* 4281 */                                       out.write("</td>\n\t\t<td height=\"10\" width=\"20%\" class=\"whitegrayborder\" height=\"28\" align=\"center\" >");
/* 4282 */                                       out.print(OpStorPort);
/* 4283 */                                       out.write("</td>\n     \t\t");
/*      */                                       
/* 4285 */                                       if (request.getAttribute("OpStorHost") == null)
/*      */                                       {
/*      */ 
/* 4288 */                                         out.write("\n\t\t\t        <td class=\"whitegrayborder\" height=\"28\" align=\"center\" width=\"5%\"><a href=\"/extDeviceAction.do?method=addNewExtDevice&prodName=OpStor\" class=\"bodytext\">");
/* 4289 */                                         out.print(FormatUtil.getString("Add"));
/* 4290 */                                         out.write("</a></td>\n\t  \t\t");
/*      */                                       } else {
/* 4292 */                                         out.write("\n\t\t\t<td>\n\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t<tr>\n      \t\t\t\t<td class=\"whitegrayborder itadmin-no-decor\" height=\"28\" align=\"center\" title=\"");
/* 4293 */                                         out.print(FormatUtil.getString("am.webclient.managedserver.collectdatanow"));
/* 4294 */                                         out.write("\" width=\"5%\"><a href=\"/extDeviceAction.do?method=fetchDataNowForExtDevice&prodName=OpStor\" class=\"bodytext\"><img src=\"/images/icon_refresh.gif\"  border=\"0\"></a></td>\n\t\t\t\t<td class=\"whitegrayborder itadmin-no-decor\" height=\"28\"  align=\"center\" title=\"");
/* 4295 */                                         out.print(FormatUtil.getString("Edit"));
/* 4296 */                                         out.write("\" width=\"5%\"><a href=\"/extDeviceAction.do?method=editExtDevices&prodName=OpStor\">");
/* 4297 */                                         if (_jspx_meth_logic_005fpresent_005f11(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                           return;
/* 4299 */                                         out.write("</a></td>\n\t\t\t</tr>\n\t\t\t</table>\n      \t\t\t</td>\n      \t\t\t");
/*      */                                       }
/* 4301 */                                       out.write("\n      \t\t\t<td class=\"whitegrayborder\" height=\"28\"  align=\"left\" width=\"10%\">&nbsp;</td>\n\t\t</tr>\n\n\n\t\t");
/*      */                                       
/* 4303 */                                       String DCIMHost = "-";
/* 4304 */                                       String DCIMPort = "-";
/* 4305 */                                       String DCIMLastDC = "-";
/* 4306 */                                       String DCIMId = null;
/* 4307 */                                       String DCIMEnabled = "";
/* 4308 */                                       String DCIMTitle = FormatUtil.getString("DCIM is not Configured", new String[] { OEMUtil.getOEMString("DCIM") });
/* 4309 */                                       if (request.getAttribute("DCIMHost") != null)
/*      */                                       {
/* 4311 */                                         DCIMHost = (String)request.getAttribute("DCIMHost");
/*      */                                       }
/*      */                                       
/* 4314 */                                       if (request.getAttribute("DCIMPort") != null)
/*      */                                       {
/* 4316 */                                         DCIMPort = (String)request.getAttribute("DCIMPort");
/*      */                                       }
/*      */                                       
/* 4319 */                                       if ((request.getAttribute("DCIMLastDC") != null) && (!request.getAttribute("DCIMLastDC").equals("-1")))
/*      */                                       {
/* 4321 */                                         DCIMLastDC = FormatUtil.formatDT((String)request.getAttribute("DCIMLastDC"));
/*      */                                       }
/*      */                                       
/* 4324 */                                       out.write("\n\t\t<tr>\n\t\t\t<td height=\"20\" width=\"25%\" class=\"whitegrayborder\" height=\"28\" valign=\"center\" ><img src=/images/jumpto_OpM.gif width=16 height=14 hspace=10 >");
/* 4325 */                                       out.print(FormatUtil.getString("it360.webclient.dcimtab.text"));
/* 4326 */                                       out.write("\n\n    \t\t</td>\n    \t\t<td height=\"15\" width=\"15%\" class=\"whitegrayborder\" height=\"28\" valign=\"center\" >");
/* 4327 */                                       out.print(DCIMHost);
/* 4328 */                                       out.write("</td>\n\t\t<td height=\"10\" width=\"20%\" class=\"whitegrayborder\" height=\"28\" align=\"center\" >");
/* 4329 */                                       out.print(DCIMPort);
/* 4330 */                                       out.write("</td>\n     \t\t");
/*      */                                       
/* 4332 */                                       if (request.getAttribute("DCIMHost") == null)
/*      */                                       {
/*      */ 
/* 4335 */                                         out.write("\n\t\t\t        <td class=\"whitegrayborder\" height=\"28\" align=\"center\" width=\"5%\"><a href=\"/extDeviceAction.do?method=addNewExtDevice&prodName=Facilities\" class=\"bodytext\">");
/* 4336 */                                         out.print(FormatUtil.getString("Add"));
/* 4337 */                                         out.write("</a></td>\n\t  \t\t");
/*      */                                       } else {
/* 4339 */                                         out.write("\n\t\t\t<td>\n\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t<tr>\n      \t\t\t\t<td class=\"whitegrayborder itadmin-no-border\" height=\"28\" align=\"center\" title=\"");
/* 4340 */                                         out.print(FormatUtil.getString("am.webclient.managedserver.collectdatanow"));
/* 4341 */                                         out.write("\" width=\"5%\"><a href=\"/extDeviceAction.do?method=fetchDataNowForExtDevice&prodName=Facilities\" class=\"bodytext\"><img src=\"/images/icon_refresh.gif\"  border=\"0\"></a></td>\n\t\t\t\t<td class=\"whitegrayborder itadmin-no-border\" height=\"28\"  align=\"center\" title=\"");
/* 4342 */                                         out.print(FormatUtil.getString("Edit"));
/* 4343 */                                         out.write("\" width=\"5%\"><a href=\"/extDeviceAction.do?method=editExtDevices&prodName=Facilities\">");
/* 4344 */                                         if (_jspx_meth_logic_005fpresent_005f12(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                           return;
/* 4346 */                                         out.write("</a></td>\n\t\t\t</tr>\n\t\t\t</table>\n      \t\t\t</td>\n      \t\t\t");
/*      */                                       }
/* 4348 */                                       out.write("\n      \t\t\t<td class=\"whitegrayborder\" height=\"28\"  align=\"left\" width=\"10%\">&nbsp;</td>\n   \t</tr>\n   ");
/*      */                                     } }
/* 4350 */                                   out.write("\n\n  </table>\n\n\n          </td>\n          </tr>\n        </table>\n\t\t\n\t\t\n<br>\t\n");
/* 4351 */                                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && (!EnterpriseUtil.isAdminServer()) && 
/* 4352 */                                     (!OEMUtil.isOEM())) {
/* 4353 */                                     out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtborder itadmin-no-decor\">\n    <tr>\n\t  <td width=\"2%\" height=\"31\" class=\"tableheading itadmin-no-decor\" ><img src=/images/site24x7-logo.gif width=26 height=17 hspace=5 style=\"padding-bottom=1px;\"></td>\n      <td width=\"80%\" height=\"31\" class=\"tableheading itadmin-no-decor\" style=\"padding-bottom:0px;\">");
/* 4354 */                                     out.print(FormatUtil.getString("am.webclient.site24x7.datainteg"));
/* 4355 */                                     out.write("</td>\n      <td width=\"10%\" align=\"right\" height=\"31\" class=\"tableheading itadmin-no-decor\" ><a href=\"/extDeviceAction.do?method=addNewExtDevice&prodName=Site24x7\" class=\"staticlinks\">");
/* 4356 */                                     out.print(FormatUtil.getString("am.webclient.site24x7.addnew.account"));
/* 4357 */                                     out.write("</a> &nbsp;</td>   \n   </tr>\n  </table>\n \n\t<table valign=\"center\" width=\"100%\" cellpadding=\"1\" cellspacing=\"0\"  border=\"0\" class=\"lrtbdarkborder\">\t\n\t <tr class=\"bodytextbold\">\n\t\t   <td width=\"30%\" height=\"30%\" valign=\"center\" class=\"columnheadingnotop\" style=\"padding-left:10px;\">");
/* 4358 */                                     out.print(FormatUtil.getString("am.webclient.site24x7.account.name"));
/* 4359 */                                     out.write("</td>\n\t       <td width=\"30%\" height=\"30%\" valign=\"center\" class=\"columnheadingnotop\">");
/* 4360 */                                     out.print(FormatUtil.getString("am.webclient.eumdashboard.lastupdate"));
/* 4361 */                                     out.write("</td>\n\t\t   <td width=\"30%\" height=\"30%\" valign=\"center\" class=\"columnheadingnotop\">");
/* 4362 */                                     out.print(FormatUtil.getString("Last Sync status"));
/* 4363 */                                     out.write("</td>\n\t\t   <td width=\"10%\" height=\"28\"   align=\"center\" class=\"columnheadingnotop\">");
/* 4364 */                                     out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/* 4365 */                                     out.write("</td>\n\t </tr>\t  \n\t\t  \n   ");
/*      */                                     
/* 4367 */                                     ChooseTag _jspx_th_c_005fchoose_005f16 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4368 */                                     _jspx_th_c_005fchoose_005f16.setPageContext(_jspx_page_context);
/* 4369 */                                     _jspx_th_c_005fchoose_005f16.setParent(_jspx_th_tiles_005fput_005f3);
/* 4370 */                                     int _jspx_eval_c_005fchoose_005f16 = _jspx_th_c_005fchoose_005f16.doStartTag();
/* 4371 */                                     if (_jspx_eval_c_005fchoose_005f16 != 0) {
/*      */                                       for (;;) {
/* 4373 */                                         out.write("\n   ");
/*      */                                         
/* 4375 */                                         WhenTag _jspx_th_c_005fwhen_005f16 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4376 */                                         _jspx_th_c_005fwhen_005f16.setPageContext(_jspx_page_context);
/* 4377 */                                         _jspx_th_c_005fwhen_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/*      */                                         
/* 4379 */                                         _jspx_th_c_005fwhen_005f16.setTest("${(empty Site24x4Details)}");
/* 4380 */                                         int _jspx_eval_c_005fwhen_005f16 = _jspx_th_c_005fwhen_005f16.doStartTag();
/* 4381 */                                         if (_jspx_eval_c_005fwhen_005f16 != 0) {
/*      */                                           for (;;) {
/* 4383 */                                             out.write("\n   \t\t <tr>\n\t      <td class=\"bodytext\" align=\"center\" height=\"26\" colspan='10'> \n\t\t     ");
/* 4384 */                                             out.print(FormatUtil.getString("am.webclient.site24x7.addnew.account.link"));
/* 4385 */                                             out.write("\n\t\t  </td> \n\t\t</tr>\t\n   ");
/* 4386 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f16.doAfterBody();
/* 4387 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4391 */                                         if (_jspx_th_c_005fwhen_005f16.doEndTag() == 5) {
/* 4392 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16); return;
/*      */                                         }
/*      */                                         
/* 4395 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 4396 */                                         out.write("\n   ");
/*      */                                         
/* 4398 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f16 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4399 */                                         _jspx_th_c_005fotherwise_005f16.setPageContext(_jspx_page_context);
/* 4400 */                                         _jspx_th_c_005fotherwise_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/* 4401 */                                         int _jspx_eval_c_005fotherwise_005f16 = _jspx_th_c_005fotherwise_005f16.doStartTag();
/* 4402 */                                         if (_jspx_eval_c_005fotherwise_005f16 != 0) {
/*      */                                           for (;;) {
/* 4404 */                                             out.write("\n\n      ");
/*      */                                             
/* 4406 */                                             ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4407 */                                             _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4408 */                                             _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fotherwise_005f16);
/*      */                                             
/* 4410 */                                             _jspx_th_c_005fforEach_005f0.setItems("${Site24x4Details}");
/*      */                                             
/* 4412 */                                             _jspx_th_c_005fforEach_005f0.setVar("row");
/*      */                                             
/* 4414 */                                             _jspx_th_c_005fforEach_005f0.setVarStatus("rowcount");
/* 4415 */                                             int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                             try {
/* 4417 */                                               int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4418 */                                               if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                                 for (;;) {
/* 4420 */                                                   out.write("\n\t    <tr class=\"whitegrayborder\">\n\t\t  <td width=\"30%\" class=\"whitegrayborder\" style=\"padding-left:10px;\">");
/* 4421 */                                                   if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4487 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4488 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4423 */                                                   out.write("</td>\n\t\t  <td width=\"30%\" class=\"whitegrayborder\">");
/* 4424 */                                                   if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4487 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4488 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4426 */                                                   out.write("</td>\n\t\t  <td width=\"30%\" class=\"whitegrayborder\">");
/* 4427 */                                                   if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4487 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4488 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4429 */                                                   out.write("</td>\n\t\t  ");
/* 4430 */                                                   if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4487 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4488 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4432 */                                                   out.write("\n\t\t <td width=\"10%\" class=\"whitegrayborder\">  \n\t\t\t\t <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td><a title=\"Edit Account Details\" href=\"/extDeviceAction.do?method=editExtDevices&prodName=Site24x7&id=");
/* 4433 */                                                   if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4487 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4488 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4435 */                                                   out.write(34);
/* 4436 */                                                   out.write(62);
/* 4437 */                                                   if (_jspx_meth_logic_005fpresent_005f13(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4487 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4488 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4439 */                                                   out.write("</a> </td>\n\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t");
/* 4440 */                                                   if (_jspx_meth_c_005fchoose_005f17(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4487 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4488 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4442 */                                                   out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td><a title=\"");
/* 4443 */                                                   out.print(FormatUtil.getString("am.webclient.managedserver.collectdatanow"));
/* 4444 */                                                   out.write("\" href=\"/extDeviceAction.do?method=fetchDataNowForExtDevice&prodName=Site24x7&id=");
/* 4445 */                                                   if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/* 4487 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4488 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4447 */                                                   out.write("\" class=\"bodytext\"><img src=\"/images/icon_refresh.gif\" vspace=\"5\" border=\"0\"></a></td>\n\t\t\t\t\t<td>");
/*      */                                                   
/* 4449 */                                                   PresentTag _jspx_th_logic_005fpresent_005f16 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4450 */                                                   _jspx_th_logic_005fpresent_005f16.setPageContext(_jspx_page_context);
/* 4451 */                                                   _jspx_th_logic_005fpresent_005f16.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                                   
/* 4453 */                                                   _jspx_th_logic_005fpresent_005f16.setRole("ADMIN");
/* 4454 */                                                   int _jspx_eval_logic_005fpresent_005f16 = _jspx_th_logic_005fpresent_005f16.doStartTag();
/* 4455 */                                                   if (_jspx_eval_logic_005fpresent_005f16 != 0) {
/*      */                                                     for (;;) {
/* 4457 */                                                       out.write(" <a href=\"#\" onclick=\"deleteaccount(");
/* 4458 */                                                       if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fpresent_005f16, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4487 */                                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 4488 */                                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                       }
/* 4460 */                                                       out.write(");\" title=\"");
/* 4461 */                                                       out.print(FormatUtil.getString("Delete"));
/* 4462 */                                                       out.write("\" class=\"bodytext\"><img src=\"/images/deleteWidget.gif\" vspace=\"5\" border=\"0\">");
/* 4463 */                                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f16.doAfterBody();
/* 4464 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/*      */                                                   }
/* 4468 */                                                   if (_jspx_th_logic_005fpresent_005f16.doEndTag() == 5) {
/* 4469 */                                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f16);
/*      */                                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4487 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4488 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4472 */                                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f16);
/* 4473 */                                                   out.write(" </td>\n\t\t\t\t\t</tr>\n\t\t\t\t </table>\n\t\t</td>\n\t\t</tr>\n    ");
/* 4474 */                                                   int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 4475 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 4479 */                                               if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4487 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 4488 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/*      */                                             }
/*      */                                             catch (Throwable _jspx_exception)
/*      */                                             {
/*      */                                               for (;;)
/*      */                                               {
/* 4483 */                                                 int tmp13980_13979 = 0; int[] tmp13980_13977 = _jspx_push_body_count_c_005fforEach_005f0; int tmp13982_13981 = tmp13980_13977[tmp13980_13979];tmp13980_13977[tmp13980_13979] = (tmp13982_13981 - 1); if (tmp13982_13981 <= 0) break;
/* 4484 */                                                 out = _jspx_page_context.popBody(); }
/* 4485 */                                               _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                             } finally {
/* 4487 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 4488 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                             }
/* 4490 */                                             out.write("\n   ");
/* 4491 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f16.doAfterBody();
/* 4492 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4496 */                                         if (_jspx_th_c_005fotherwise_005f16.doEndTag() == 5) {
/* 4497 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16); return;
/*      */                                         }
/*      */                                         
/* 4500 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16);
/* 4501 */                                         out.write("\n   ");
/* 4502 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f16.doAfterBody();
/* 4503 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4507 */                                     if (_jspx_th_c_005fchoose_005f16.doEndTag() == 5) {
/* 4508 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16); return;
/*      */                                     }
/*      */                                     
/* 4511 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16);
/* 4512 */                                     out.write(" \n\n</table>\t\n\t\t\n\t\t");
/*      */                                   }
/*      */                                   
/* 4515 */                                   out.write("\n<br>\n\n</form>\n</div>\n<div id=\"Polling Interval\" style=\"display:none\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"70%\" valign=\"top\">\n");
/*      */                                   
/* 4517 */                                   FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 4518 */                                   _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 4519 */                                   _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                   
/* 4521 */                                   _jspx_th_html_005fform_005f0.setAction("/extDeviceAction.do");
/*      */                                   
/* 4523 */                                   _jspx_th_html_005fform_005f0.setStyle("display:inline");
/* 4524 */                                   int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 4525 */                                   if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                     for (;;) {
/* 4527 */                                       out.write("\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\" class=\"lrtbdarkborder\">\n<input type=\"hidden\" name=\"method\" value=\"editExtDevicePollInterval\" >\n<tr>\n<td colspan=\"2\" class=\"tableheadingbborder\">\n");
/* 4528 */                                       out.print(FormatUtil.getString("am.webclient.extdevices.checkupdates"));
/* 4529 */                                       out.write("</a>\n</td>\n</tr>\n<tr>\n\t<td>&nbsp;</td>\n</tr>\n<tr>\n<td colspan=\"2\" style=\"padding-left:8px\"><span class=\"bodytext\">");
/* 4530 */                                       out.print(FormatUtil.getString("am.webclient.extdevices.alertspoll"));
/* 4531 */                                       out.write("</span>&nbsp;");
/* 4532 */                                       if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 4534 */                                       out.write("<span class=\"bodytext\">&nbsp;");
/* 4535 */                                       out.print(FormatUtil.getString("Minutes"));
/* 4536 */                                       out.write("</span></td>\n\n</tr>\n<tr>\n <td>&nbsp;</td>\n</tr>\n\n<tr>\n\t <td colspan=\"2\" style=\"padding-left:8px\"><span class=\"bodytext\">");
/* 4537 */                                       out.print(FormatUtil.getString("am.webclient.extdevices.fullSync"));
/* 4538 */                                       out.write("</span>\n\t &nbsp;");
/* 4539 */                                       if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 4541 */                                       out.write(" <span class=\"bodytext\">");
/* 4542 */                                       out.print(FormatUtil.getString("Hours"));
/* 4543 */                                       out.write("</span>\n\t &nbsp;");
/* 4544 */                                       if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 4546 */                                       out.write("<span class=\"bodytext\">");
/* 4547 */                                       out.print(FormatUtil.getString("Minutes"));
/* 4548 */                                       out.write("</span>\n\t </td>\n\t </tr>\n<tr>\n\t<td>&nbsp;</td>\n</tr>\n\t \n <tr>\n  <td colspan=\"2\" height=\"31\" align=\"center\" class=\"tablebottom\" ><input name=\"Submit\" value=\"");
/* 4549 */                                       out.print(FormatUtil.getString("Save"));
/* 4550 */                                       out.write("\" type=\"button\" class=\"buttons btn_highlt\" onclick=\"fnFormSubmit();\">\n  <input type=\"reset\" align=\"center\" class=\"buttons btn_reset\" value=\"");
/* 4551 */                                       out.print(FormatUtil.getString("webclient.admin.adduser.clear"));
/* 4552 */                                       out.write("\">\n  <input type=\"button\" align=\"center\" class=\"buttons btn_link\" value=\"");
/* 4553 */                                       out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.cancel"));
/* 4554 */                                       out.write("\" onclick=\"javascript:history.back();\">\n  </td>\n  \n </tr>\n </table>\n");
/* 4555 */                                       int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 4556 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4560 */                                   if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 4561 */                                     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                                   }
/*      */                                   
/* 4564 */                                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 4565 */                                   out.write("\n</td>\n<td width=\"30%\" valign=\"top\">\n");
/* 4566 */                                   org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.Extdevicepollnow.helpcard")), request.getCharacterEncoding()), out, false);
/* 4567 */                                   out.write("\n</td>\n</tr>\n</table>\n</div>\n\n");
/* 4568 */                                   int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 4569 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 4572 */                                 if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4573 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 4576 */                               if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4577 */                                 this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                               }
/*      */                               
/* 4580 */                               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 4581 */                               out.write(32);
/* 4582 */                               if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                                 return;
/* 4584 */                               out.write(10);
/* 4585 */                               out.write(32);
/* 4586 */                               int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4587 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4591 */                           if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4592 */                             this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                           }
/*      */                           else {
/* 4595 */                             this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4596 */                             out.write(10);
/*      */                           }
/* 4598 */                         } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4599 */         out = _jspx_out;
/* 4600 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4601 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4602 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4605 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4611 */     PageContext pageContext = _jspx_page_context;
/* 4612 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4614 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 4615 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4616 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 4618 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 4620 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 4621 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4622 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4623 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4624 */       return true;
/*      */     }
/* 4626 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4627 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4632 */     PageContext pageContext = _jspx_page_context;
/* 4633 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4635 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4636 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 4637 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 4639 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 4640 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 4641 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 4643 */         out.write("\n alertUser();\n ");
/* 4644 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 4645 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4649 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 4650 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4651 */       return true;
/*      */     }
/* 4653 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4654 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4659 */     PageContext pageContext = _jspx_page_context;
/* 4660 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4662 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4663 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 4664 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 4666 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 4667 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 4668 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 4670 */         out.write("\n alertUser();\n return;\n ");
/* 4671 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 4672 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4676 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 4677 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 4678 */       return true;
/*      */     }
/* 4680 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 4681 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4686 */     PageContext pageContext = _jspx_page_context;
/* 4687 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4689 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4690 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 4691 */     _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */     
/* 4693 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 4694 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 4695 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 4697 */         out.write("\n alertUser();\n return;\n ");
/* 4698 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 4699 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4703 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 4704 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4705 */       return true;
/*      */     }
/* 4707 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4708 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4713 */     PageContext pageContext = _jspx_page_context;
/* 4714 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4716 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4717 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 4718 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4720 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 4722 */     _jspx_th_tiles_005fput_005f0.setValue("Add-on/Product Settings");
/* 4723 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 4724 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 4725 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4726 */       return true;
/*      */     }
/* 4728 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4729 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4734 */     PageContext pageContext = _jspx_page_context;
/* 4735 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4737 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4738 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4739 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4741 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4743 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=3");
/* 4744 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4745 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4746 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4747 */       return true;
/*      */     }
/* 4749 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4750 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4755 */     PageContext pageContext = _jspx_page_context;
/* 4756 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4758 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 4759 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4760 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 4762 */     _jspx_th_c_005fout_005f1.setValue("${selectedskin}");
/*      */     
/* 4764 */     _jspx_th_c_005fout_005f1.setDefault("${initParam.defaultSkin}");
/* 4765 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4766 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4767 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4768 */       return true;
/*      */     }
/* 4770 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4771 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4776 */     PageContext pageContext = _jspx_page_context;
/* 4777 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4779 */     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4780 */     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 4781 */     _jspx_th_logic_005fpresent_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4783 */     _jspx_th_logic_005fpresent_005f3.setRole("ADMIN,ENTERPRISEADMIN");
/* 4784 */     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 4785 */     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */       for (;;) {
/* 4787 */         out.write(" <img src=\"/images/icon_edit.gif\"  border=\"0\">");
/* 4788 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 4789 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4793 */     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 4794 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 4795 */       return true;
/*      */     }
/* 4797 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 4798 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4803 */     PageContext pageContext = _jspx_page_context;
/* 4804 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4806 */     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4807 */     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 4808 */     _jspx_th_logic_005fpresent_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4810 */     _jspx_th_logic_005fpresent_005f4.setRole("ADMIN,ENTERPRISEADMIN");
/* 4811 */     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 4812 */     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */       for (;;) {
/* 4814 */         out.write(" <img src=\"/images/icon_edit.gif\"  border=\"0\">");
/* 4815 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 4816 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4820 */     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 4821 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 4822 */       return true;
/*      */     }
/* 4824 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 4825 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4830 */     PageContext pageContext = _jspx_page_context;
/* 4831 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4833 */     PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4834 */     _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 4835 */     _jspx_th_logic_005fpresent_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4837 */     _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 4838 */     int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 4839 */     if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */       for (;;) {
/* 4841 */         out.write(" <a href=\"/extDeviceAction.do?method=showExtDeviceConfigurations&updateStatus=false&prodName=OpManager\" class=\"bodytext\"><img src=\"/images/icon_tickmark.gif\"  vspace=\"5\" border=\"0\">");
/* 4842 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 4843 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4847 */     if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 4848 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 4849 */       return true;
/*      */     }
/* 4851 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 4852 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f6(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4857 */     PageContext pageContext = _jspx_page_context;
/* 4858 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4860 */     PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4861 */     _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 4862 */     _jspx_th_logic_005fpresent_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4864 */     _jspx_th_logic_005fpresent_005f6.setRole("ADMIN");
/* 4865 */     int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 4866 */     if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */       for (;;) {
/* 4868 */         out.write("<a href=\"/extDeviceAction.do?method=showExtDeviceConfigurations&updateStatus=true&prodName=OpManager\" class=\"bodytext\"><img src=\"/images/cross.gif\" vspace=\"5\" border=\"0\">");
/* 4869 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 4870 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4874 */     if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 4875 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 4876 */       return true;
/*      */     }
/* 4878 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 4879 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4884 */     PageContext pageContext = _jspx_page_context;
/* 4885 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4887 */     PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4888 */     _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 4889 */     _jspx_th_logic_005fpresent_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4891 */     _jspx_th_logic_005fpresent_005f7.setRole("ADMIN,ENTERPRISEADMIN");
/* 4892 */     int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 4893 */     if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */       for (;;) {
/* 4895 */         out.write(" <img src=\"/images/icon_edit.gif\"  border=\"0\">");
/* 4896 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 4897 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4901 */     if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 4902 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 4903 */       return true;
/*      */     }
/* 4905 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 4906 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f8(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4911 */     PageContext pageContext = _jspx_page_context;
/* 4912 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4914 */     PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4915 */     _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 4916 */     _jspx_th_logic_005fpresent_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4918 */     _jspx_th_logic_005fpresent_005f8.setRole("ADMIN");
/* 4919 */     int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 4920 */     if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */       for (;;) {
/* 4922 */         out.write(" <a href=\"/extDeviceAction.do?method=showExtDeviceConfigurations&updateStatus=false&prodName=OpStor\" class=\"bodytext\"><img src=\"/images/icon_tickmark.gif\"  vspace=\"5\" border=\"0\">");
/* 4923 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 4924 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4928 */     if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 4929 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4930 */       return true;
/*      */     }
/* 4932 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4933 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f9(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4938 */     PageContext pageContext = _jspx_page_context;
/* 4939 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4941 */     PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4942 */     _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 4943 */     _jspx_th_logic_005fpresent_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4945 */     _jspx_th_logic_005fpresent_005f9.setRole("ADMIN");
/* 4946 */     int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 4947 */     if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */       for (;;) {
/* 4949 */         out.write("<a href=\"/extDeviceAction.do?method=showExtDeviceConfigurations&updateStatus=true&prodName=OpStor\" class=\"bodytext\"><img src=\"/images/cross.gif\" vspace=\"5\" border=\"0\">");
/* 4950 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 4951 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4955 */     if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 4956 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 4957 */       return true;
/*      */     }
/* 4959 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 4960 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f10(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4965 */     PageContext pageContext = _jspx_page_context;
/* 4966 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4968 */     PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4969 */     _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 4970 */     _jspx_th_logic_005fpresent_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4972 */     _jspx_th_logic_005fpresent_005f10.setRole("ADMIN,ENTERPRISEADMIN");
/* 4973 */     int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 4974 */     if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */       for (;;) {
/* 4976 */         out.write(" <img src=\"/images/icon_edit.gif\"  border=\"0\">");
/* 4977 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 4978 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4982 */     if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 4983 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 4984 */       return true;
/*      */     }
/* 4986 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 4987 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f11(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4992 */     PageContext pageContext = _jspx_page_context;
/* 4993 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4995 */     PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4996 */     _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 4997 */     _jspx_th_logic_005fpresent_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4999 */     _jspx_th_logic_005fpresent_005f11.setRole("ADMIN,ENTERPRISEADMIN");
/* 5000 */     int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 5001 */     if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */       for (;;) {
/* 5003 */         out.write(" <img src=\"/images/icon_edit.gif\"  border=\"0\">");
/* 5004 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 5005 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5009 */     if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 5010 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 5011 */       return true;
/*      */     }
/* 5013 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 5014 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f12(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5019 */     PageContext pageContext = _jspx_page_context;
/* 5020 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5022 */     PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5023 */     _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 5024 */     _jspx_th_logic_005fpresent_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5026 */     _jspx_th_logic_005fpresent_005f12.setRole("ADMIN,ENTERPRISEADMIN");
/* 5027 */     int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 5028 */     if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */       for (;;) {
/* 5030 */         out.write(" <img src=\"/images/icon_edit.gif\"  border=\"0\">");
/* 5031 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 5032 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5036 */     if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 5037 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 5038 */       return true;
/*      */     }
/* 5040 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 5041 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5046 */     PageContext pageContext = _jspx_page_context;
/* 5047 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5049 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5050 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5051 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5053 */     _jspx_th_c_005fout_005f2.setValue("${row.value[0]}");
/* 5054 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5055 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5056 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5057 */       return true;
/*      */     }
/* 5059 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5060 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5065 */     PageContext pageContext = _jspx_page_context;
/* 5066 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5068 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5069 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5070 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5072 */     _jspx_th_c_005fout_005f3.setValue("${row.value[5]}");
/* 5073 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5074 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5075 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5076 */       return true;
/*      */     }
/* 5078 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5079 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5084 */     PageContext pageContext = _jspx_page_context;
/* 5085 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5087 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5088 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5089 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5091 */     _jspx_th_c_005fout_005f4.setValue("${row.value[4]}");
/* 5092 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5093 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5094 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5095 */       return true;
/*      */     }
/* 5097 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5098 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5103 */     PageContext pageContext = _jspx_page_context;
/* 5104 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5106 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 5107 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 5108 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5110 */     _jspx_th_c_005fset_005f0.setVar("status");
/*      */     
/* 5112 */     _jspx_th_c_005fset_005f0.setValue("${row.value[1]}");
/* 5113 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 5114 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5115 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 5116 */       return true;
/*      */     }
/* 5118 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 5119 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5124 */     PageContext pageContext = _jspx_page_context;
/* 5125 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5127 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5128 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5129 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5131 */     _jspx_th_c_005fout_005f5.setValue("${row.key}");
/* 5132 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5133 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5134 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5135 */       return true;
/*      */     }
/* 5137 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5138 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f13(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5143 */     PageContext pageContext = _jspx_page_context;
/* 5144 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5146 */     PresentTag _jspx_th_logic_005fpresent_005f13 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5147 */     _jspx_th_logic_005fpresent_005f13.setPageContext(_jspx_page_context);
/* 5148 */     _jspx_th_logic_005fpresent_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5150 */     _jspx_th_logic_005fpresent_005f13.setRole("ADMIN,ENTERPRISEADMIN");
/* 5151 */     int _jspx_eval_logic_005fpresent_005f13 = _jspx_th_logic_005fpresent_005f13.doStartTag();
/* 5152 */     if (_jspx_eval_logic_005fpresent_005f13 != 0) {
/*      */       for (;;) {
/* 5154 */         out.write(" <img src=\"/images/icon_edit.gif\"  border=\"0\">");
/* 5155 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f13.doAfterBody();
/* 5156 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5160 */     if (_jspx_th_logic_005fpresent_005f13.doEndTag() == 5) {
/* 5161 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13);
/* 5162 */       return true;
/*      */     }
/* 5164 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13);
/* 5165 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f17(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5170 */     PageContext pageContext = _jspx_page_context;
/* 5171 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5173 */     ChooseTag _jspx_th_c_005fchoose_005f17 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5174 */     _jspx_th_c_005fchoose_005f17.setPageContext(_jspx_page_context);
/* 5175 */     _jspx_th_c_005fchoose_005f17.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/* 5176 */     int _jspx_eval_c_005fchoose_005f17 = _jspx_th_c_005fchoose_005f17.doStartTag();
/* 5177 */     if (_jspx_eval_c_005fchoose_005f17 != 0) {
/*      */       for (;;) {
/* 5179 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 5180 */         if (_jspx_meth_c_005fwhen_005f17(_jspx_th_c_005fchoose_005f17, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5181 */           return true;
/* 5182 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 5183 */         if (_jspx_meth_c_005fotherwise_005f17(_jspx_th_c_005fchoose_005f17, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5184 */           return true;
/* 5185 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 5186 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f17.doAfterBody();
/* 5187 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5191 */     if (_jspx_th_c_005fchoose_005f17.doEndTag() == 5) {
/* 5192 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 5193 */       return true;
/*      */     }
/* 5195 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 5196 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f17(JspTag _jspx_th_c_005fchoose_005f17, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5201 */     PageContext pageContext = _jspx_page_context;
/* 5202 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5204 */     WhenTag _jspx_th_c_005fwhen_005f17 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5205 */     _jspx_th_c_005fwhen_005f17.setPageContext(_jspx_page_context);
/* 5206 */     _jspx_th_c_005fwhen_005f17.setParent((Tag)_jspx_th_c_005fchoose_005f17);
/*      */     
/* 5208 */     _jspx_th_c_005fwhen_005f17.setTest("${status == 'true'}");
/* 5209 */     int _jspx_eval_c_005fwhen_005f17 = _jspx_th_c_005fwhen_005f17.doStartTag();
/* 5210 */     if (_jspx_eval_c_005fwhen_005f17 != 0) {
/*      */       for (;;) {
/* 5212 */         out.write("\n\t\t\t\t\t\t\t\t\t\t ");
/* 5213 */         if (_jspx_meth_logic_005fpresent_005f14(_jspx_th_c_005fwhen_005f17, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5214 */           return true;
/* 5215 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 5216 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f17.doAfterBody();
/* 5217 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5221 */     if (_jspx_th_c_005fwhen_005f17.doEndTag() == 5) {
/* 5222 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 5223 */       return true;
/*      */     }
/* 5225 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 5226 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f14(JspTag _jspx_th_c_005fwhen_005f17, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5231 */     PageContext pageContext = _jspx_page_context;
/* 5232 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5234 */     PresentTag _jspx_th_logic_005fpresent_005f14 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5235 */     _jspx_th_logic_005fpresent_005f14.setPageContext(_jspx_page_context);
/* 5236 */     _jspx_th_logic_005fpresent_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f17);
/*      */     
/* 5238 */     _jspx_th_logic_005fpresent_005f14.setRole("ADMIN");
/* 5239 */     int _jspx_eval_logic_005fpresent_005f14 = _jspx_th_logic_005fpresent_005f14.doStartTag();
/* 5240 */     if (_jspx_eval_logic_005fpresent_005f14 != 0) {
/*      */       for (;;) {
/* 5242 */         out.write(" <a title=\"Disable Data Collection\" href=\"/extDeviceAction.do?method=showExtDeviceConfigurations&updateStatus=false&prodName=Site24x7&id=");
/* 5243 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_logic_005fpresent_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5244 */           return true;
/* 5245 */         out.write("\" class=\"bodytext\"><img src=\"/images/icon-pause.png\"  vspace=\"5\" border=\"0\">");
/* 5246 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f14.doAfterBody();
/* 5247 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5251 */     if (_jspx_th_logic_005fpresent_005f14.doEndTag() == 5) {
/* 5252 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f14);
/* 5253 */       return true;
/*      */     }
/* 5255 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f14);
/* 5256 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5261 */     PageContext pageContext = _jspx_page_context;
/* 5262 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5264 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5265 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5266 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 5268 */     _jspx_th_c_005fout_005f6.setValue("${row.key}");
/* 5269 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5270 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5271 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5272 */       return true;
/*      */     }
/* 5274 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5275 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f17(JspTag _jspx_th_c_005fchoose_005f17, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5280 */     PageContext pageContext = _jspx_page_context;
/* 5281 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5283 */     OtherwiseTag _jspx_th_c_005fotherwise_005f17 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5284 */     _jspx_th_c_005fotherwise_005f17.setPageContext(_jspx_page_context);
/* 5285 */     _jspx_th_c_005fotherwise_005f17.setParent((Tag)_jspx_th_c_005fchoose_005f17);
/* 5286 */     int _jspx_eval_c_005fotherwise_005f17 = _jspx_th_c_005fotherwise_005f17.doStartTag();
/* 5287 */     if (_jspx_eval_c_005fotherwise_005f17 != 0) {
/*      */       for (;;) {
/* 5289 */         out.write("\n\t\t\t\t\t\t\t\t\t\t ");
/* 5290 */         if (_jspx_meth_logic_005fpresent_005f15(_jspx_th_c_005fotherwise_005f17, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5291 */           return true;
/* 5292 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 5293 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f17.doAfterBody();
/* 5294 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5298 */     if (_jspx_th_c_005fotherwise_005f17.doEndTag() == 5) {
/* 5299 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 5300 */       return true;
/*      */     }
/* 5302 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 5303 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f15(JspTag _jspx_th_c_005fotherwise_005f17, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5308 */     PageContext pageContext = _jspx_page_context;
/* 5309 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5311 */     PresentTag _jspx_th_logic_005fpresent_005f15 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5312 */     _jspx_th_logic_005fpresent_005f15.setPageContext(_jspx_page_context);
/* 5313 */     _jspx_th_logic_005fpresent_005f15.setParent((Tag)_jspx_th_c_005fotherwise_005f17);
/*      */     
/* 5315 */     _jspx_th_logic_005fpresent_005f15.setRole("ADMIN");
/* 5316 */     int _jspx_eval_logic_005fpresent_005f15 = _jspx_th_logic_005fpresent_005f15.doStartTag();
/* 5317 */     if (_jspx_eval_logic_005fpresent_005f15 != 0) {
/*      */       for (;;) {
/* 5319 */         out.write("<a title=\"Enable Data Collection\"  href=\"/extDeviceAction.do?method=showExtDeviceConfigurations&updateStatus=true&prodName=Site24x7&id=");
/* 5320 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_logic_005fpresent_005f15, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5321 */           return true;
/* 5322 */         out.write("\" class=\"bodytext\"><img src=\"/images/icon-play.gif\" vspace=\"5\" border=\"0\">");
/* 5323 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f15.doAfterBody();
/* 5324 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5328 */     if (_jspx_th_logic_005fpresent_005f15.doEndTag() == 5) {
/* 5329 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f15);
/* 5330 */       return true;
/*      */     }
/* 5332 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f15);
/* 5333 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_logic_005fpresent_005f15, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5338 */     PageContext pageContext = _jspx_page_context;
/* 5339 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5341 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5342 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5343 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_logic_005fpresent_005f15);
/*      */     
/* 5345 */     _jspx_th_c_005fout_005f7.setValue("${row.key}");
/* 5346 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5347 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5348 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5349 */       return true;
/*      */     }
/* 5351 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5352 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5357 */     PageContext pageContext = _jspx_page_context;
/* 5358 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5360 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5361 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5362 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5364 */     _jspx_th_c_005fout_005f8.setValue("${row.key}");
/* 5365 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5366 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5367 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5368 */       return true;
/*      */     }
/* 5370 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5371 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fpresent_005f16, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5376 */     PageContext pageContext = _jspx_page_context;
/* 5377 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5379 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5380 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5381 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fpresent_005f16);
/*      */     
/* 5383 */     _jspx_th_c_005fout_005f9.setValue("${row.key}");
/* 5384 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5385 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5386 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5387 */       return true;
/*      */     }
/* 5389 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5390 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5395 */     PageContext pageContext = _jspx_page_context;
/* 5396 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5398 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5399 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 5400 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5402 */     _jspx_th_html_005ftext_005f0.setProperty("port");
/*      */     
/* 5404 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*      */     
/* 5406 */     _jspx_th_html_005ftext_005f0.setSize("4");
/* 5407 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 5408 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 5409 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5410 */       return true;
/*      */     }
/* 5412 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5413 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5418 */     PageContext pageContext = _jspx_page_context;
/* 5419 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5421 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 5422 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 5423 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5425 */     _jspx_th_html_005ftext_005f1.setProperty("synchHr");
/*      */     
/* 5427 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/* 5429 */     _jspx_th_html_005ftext_005f1.setSize("1");
/*      */     
/* 5431 */     _jspx_th_html_005ftext_005f1.setMaxlength("2");
/* 5432 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 5433 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 5434 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 5435 */       return true;
/*      */     }
/* 5437 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 5438 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5443 */     PageContext pageContext = _jspx_page_context;
/* 5444 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5446 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 5447 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 5448 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5450 */     _jspx_th_html_005ftext_005f2.setProperty("synchMin");
/*      */     
/* 5452 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 5454 */     _jspx_th_html_005ftext_005f2.setSize("1");
/*      */     
/* 5456 */     _jspx_th_html_005ftext_005f2.setMaxlength("2");
/* 5457 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 5458 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 5459 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5460 */       return true;
/*      */     }
/* 5462 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5463 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5468 */     PageContext pageContext = _jspx_page_context;
/* 5469 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5471 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5472 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 5473 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5475 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 5477 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 5478 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 5479 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 5480 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 5481 */       return true;
/*      */     }
/* 5483 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 5484 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ShowExtDevices_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */