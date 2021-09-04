/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
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
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.ButtonTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.ResetTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class pingMonitor_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   60 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   63 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   64 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   65 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   72 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   77 */     ArrayList list = null;
/*   78 */     StringBuffer sbf = new StringBuffer();
/*   79 */     ManagedApplication mo = new ManagedApplication();
/*   80 */     if (distinct)
/*      */     {
/*   82 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   86 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   89 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   91 */       ArrayList row = (ArrayList)list.get(i);
/*   92 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   93 */       if (distinct) {
/*   94 */         sbf.append(row.get(0));
/*      */       } else
/*   96 */         sbf.append(row.get(1));
/*   97 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  100 */     return sbf.toString(); }
/*      */   
/*  102 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  105 */     if (severity == null)
/*      */     {
/*  107 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  109 */     if (severity.equals("5"))
/*      */     {
/*  111 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  113 */     if (severity.equals("1"))
/*      */     {
/*  115 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  120 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  127 */     if (severity == null)
/*      */     {
/*  129 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  131 */     if (severity.equals("1"))
/*      */     {
/*  133 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  135 */     if (severity.equals("4"))
/*      */     {
/*  137 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  139 */     if (severity.equals("5"))
/*      */     {
/*  141 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  146 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  152 */     if (severity == null)
/*      */     {
/*  154 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  156 */     if (severity.equals("5"))
/*      */     {
/*  158 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  160 */     if (severity.equals("1"))
/*      */     {
/*  162 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  166 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  172 */     if (severity == null)
/*      */     {
/*  174 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  176 */     if (severity.equals("1"))
/*      */     {
/*  178 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  180 */     if (severity.equals("4"))
/*      */     {
/*  182 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  184 */     if (severity.equals("5"))
/*      */     {
/*  186 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  190 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  196 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  202 */     if (severity == 5)
/*      */     {
/*  204 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  206 */     if (severity == 1)
/*      */     {
/*  208 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  213 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  219 */     if (severity == null)
/*      */     {
/*  221 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  223 */     if (severity.equals("5"))
/*      */     {
/*  225 */       if (isAvailability) {
/*  226 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  229 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  232 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  234 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  236 */     if (severity.equals("1"))
/*      */     {
/*  238 */       if (isAvailability) {
/*  239 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  242 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  249 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  256 */     if (severity == null)
/*      */     {
/*  258 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  260 */     if (severity.equals("5"))
/*      */     {
/*  262 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  264 */     if (severity.equals("4"))
/*      */     {
/*  266 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  268 */     if (severity.equals("1"))
/*      */     {
/*  270 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  275 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  281 */     if (severity == null)
/*      */     {
/*  283 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  285 */     if (severity.equals("5"))
/*      */     {
/*  287 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  289 */     if (severity.equals("4"))
/*      */     {
/*  291 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  293 */     if (severity.equals("1"))
/*      */     {
/*  295 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  300 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  307 */     if (severity == null)
/*      */     {
/*  309 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  311 */     if (severity.equals("5"))
/*      */     {
/*  313 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  315 */     if (severity.equals("4"))
/*      */     {
/*  317 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  319 */     if (severity.equals("1"))
/*      */     {
/*  321 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  326 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  334 */     StringBuffer out = new StringBuffer();
/*  335 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  336 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  337 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  338 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  339 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  340 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  341 */     out.append("</tr>");
/*  342 */     out.append("</form></table>");
/*  343 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  350 */     if (val == null)
/*      */     {
/*  352 */       return "-";
/*      */     }
/*      */     
/*  355 */     String ret = FormatUtil.formatNumber(val);
/*  356 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  357 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  360 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  364 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  372 */     StringBuffer out = new StringBuffer();
/*  373 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  374 */     out.append("<tr>");
/*  375 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  377 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  379 */     out.append("</tr>");
/*  380 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  384 */       if (j % 2 == 0)
/*      */       {
/*  386 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  390 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  393 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  395 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  398 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  402 */       out.append("</tr>");
/*      */     }
/*  404 */     out.append("</table>");
/*  405 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  406 */     out.append("<tr>");
/*  407 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  408 */     out.append("</tr>");
/*  409 */     out.append("</table>");
/*  410 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  416 */     StringBuffer out = new StringBuffer();
/*  417 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  418 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  419 */     out.append("<tr>");
/*  420 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  421 */     out.append("<tr>");
/*  422 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  423 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  424 */     out.append("</tr>");
/*  425 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  428 */       out.append("<tr>");
/*  429 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  430 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  431 */       out.append("</tr>");
/*      */     }
/*      */     
/*  434 */     out.append("</table>");
/*  435 */     out.append("</table>");
/*  436 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  441 */     if (severity.equals("0"))
/*      */     {
/*  443 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  447 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  454 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  467 */     StringBuffer out = new StringBuffer();
/*  468 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  469 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  471 */       out.append("<tr>");
/*  472 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  473 */       out.append("</tr>");
/*      */       
/*      */ 
/*  476 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  478 */         String borderclass = "";
/*      */         
/*      */ 
/*  481 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  483 */         out.append("<tr>");
/*      */         
/*  485 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  486 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  487 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  493 */     out.append("</table><br>");
/*  494 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  495 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  497 */       List sLinks = secondLevelOfLinks[0];
/*  498 */       List sText = secondLevelOfLinks[1];
/*  499 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  502 */         out.append("<tr>");
/*  503 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  504 */         out.append("</tr>");
/*  505 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  507 */           String borderclass = "";
/*      */           
/*      */ 
/*  510 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  512 */           out.append("<tr>");
/*      */           
/*  514 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  515 */           if (sLinks.get(i).toString().length() == 0) {
/*  516 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  519 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  521 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  525 */     out.append("</table>");
/*  526 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  533 */     StringBuffer out = new StringBuffer();
/*  534 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  535 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  537 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  539 */         out.append("<tr>");
/*  540 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  541 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  545 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  547 */           String borderclass = "";
/*      */           
/*      */ 
/*  550 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  552 */           out.append("<tr>");
/*      */           
/*  554 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  555 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  556 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  559 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  562 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  567 */     out.append("</table><br>");
/*  568 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  569 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  571 */       List sLinks = secondLevelOfLinks[0];
/*  572 */       List sText = secondLevelOfLinks[1];
/*  573 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  576 */         out.append("<tr>");
/*  577 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  578 */         out.append("</tr>");
/*  579 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  581 */           String borderclass = "";
/*      */           
/*      */ 
/*  584 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  586 */           out.append("<tr>");
/*      */           
/*  588 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  589 */           if (sLinks.get(i).toString().length() == 0) {
/*  590 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  593 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  595 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  599 */     out.append("</table>");
/*  600 */     return out.toString();
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
/*  613 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  616 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  619 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  622 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  625 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  628 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  631 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  634 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  642 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  647 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  652 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  657 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  662 */     if (val != null)
/*      */     {
/*  664 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  668 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  673 */     if (val == null) {
/*  674 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  678 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  683 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  689 */     if (val != null)
/*      */     {
/*  691 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  695 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  701 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  706 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  710 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  715 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  720 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  725 */     String hostaddress = "";
/*  726 */     String ip = request.getHeader("x-forwarded-for");
/*  727 */     if (ip == null)
/*  728 */       ip = request.getRemoteAddr();
/*  729 */     InetAddress add = null;
/*  730 */     if (ip.equals("127.0.0.1")) {
/*  731 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  735 */       add = InetAddress.getByName(ip);
/*      */     }
/*  737 */     hostaddress = add.getHostName();
/*  738 */     if (hostaddress.indexOf('.') != -1) {
/*  739 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  740 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  744 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  749 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  755 */     if (severity == null)
/*      */     {
/*  757 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  759 */     if (severity.equals("5"))
/*      */     {
/*  761 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  763 */     if (severity.equals("1"))
/*      */     {
/*  765 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  770 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  775 */     ResultSet set = null;
/*  776 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  777 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  779 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  780 */       if (set.next()) { String str1;
/*  781 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  782 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  785 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  790 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  793 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  795 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  799 */     StringBuffer rca = new StringBuffer();
/*  800 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  801 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  804 */     int rcalength = key.length();
/*  805 */     String split = "6. ";
/*  806 */     int splitPresent = key.indexOf(split);
/*  807 */     String div1 = "";String div2 = "";
/*  808 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  810 */       if (rcalength > 180) {
/*  811 */         rca.append("<span class=\"rca-critical-text\">");
/*  812 */         getRCATrimmedText(key, rca);
/*  813 */         rca.append("</span>");
/*      */       } else {
/*  815 */         rca.append("<span class=\"rca-critical-text\">");
/*  816 */         rca.append(key);
/*  817 */         rca.append("</span>");
/*      */       }
/*  819 */       return rca.toString();
/*      */     }
/*  821 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  822 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  823 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  824 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  825 */     getRCATrimmedText(div1, rca);
/*  826 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  829 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  830 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  831 */     getRCATrimmedText(div2, rca);
/*  832 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  834 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  839 */     String[] st = msg.split("<br>");
/*  840 */     for (int i = 0; i < st.length; i++) {
/*  841 */       String s = st[i];
/*  842 */       if (s.length() > 180) {
/*  843 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  845 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  849 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  850 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  852 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  856 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  857 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  858 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  861 */       if (key == null) {
/*  862 */         return ret;
/*      */       }
/*      */       
/*  865 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  866 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  869 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  870 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  871 */       set = AMConnectionPool.executeQueryStmt(query);
/*  872 */       if (set.next())
/*      */       {
/*  874 */         String helpLink = set.getString("LINK");
/*  875 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  878 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  884 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  903 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  894 */         if (set != null) {
/*  895 */           AMConnectionPool.closeStatement(set);
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
/*  909 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  910 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  912 */       String entityStr = (String)keys.nextElement();
/*  913 */       String mmessage = temp.getProperty(entityStr);
/*  914 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  915 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  917 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  923 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  924 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  926 */       String entityStr = (String)keys.nextElement();
/*  927 */       String mmessage = temp.getProperty(entityStr);
/*  928 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  929 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  931 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  936 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  946 */     String des = new String();
/*  947 */     while (str.indexOf(find) != -1) {
/*  948 */       des = des + str.substring(0, str.indexOf(find));
/*  949 */       des = des + replace;
/*  950 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  952 */     des = des + str;
/*  953 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  960 */       if (alert == null)
/*      */       {
/*  962 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  964 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  966 */         return "&nbsp;";
/*      */       }
/*      */       
/*  969 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  971 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  974 */       int rcalength = test.length();
/*  975 */       if (rcalength < 300)
/*      */       {
/*  977 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  981 */       StringBuffer out = new StringBuffer();
/*  982 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  983 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  984 */       out.append("</div>");
/*  985 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  986 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  987 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  992 */       ex.printStackTrace();
/*      */     }
/*  994 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1000 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1005 */     ArrayList attribIDs = new ArrayList();
/* 1006 */     ArrayList resIDs = new ArrayList();
/* 1007 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1009 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1011 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1013 */       String resourceid = "";
/* 1014 */       String resourceType = "";
/* 1015 */       if (type == 2) {
/* 1016 */         resourceid = (String)row.get(0);
/* 1017 */         resourceType = (String)row.get(3);
/*      */       }
/* 1019 */       else if (type == 3) {
/* 1020 */         resourceid = (String)row.get(0);
/* 1021 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1024 */         resourceid = (String)row.get(6);
/* 1025 */         resourceType = (String)row.get(7);
/*      */       }
/* 1027 */       resIDs.add(resourceid);
/* 1028 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1029 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1031 */       String healthentity = null;
/* 1032 */       String availentity = null;
/* 1033 */       if (healthid != null) {
/* 1034 */         healthentity = resourceid + "_" + healthid;
/* 1035 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1038 */       if (availid != null) {
/* 1039 */         availentity = resourceid + "_" + availid;
/* 1040 */         entitylist.add(availentity);
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
/* 1054 */     Properties alert = getStatus(entitylist);
/* 1055 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1060 */     int size = monitorList.size();
/*      */     
/* 1062 */     String[] severity = new String[size];
/*      */     
/* 1064 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1066 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1067 */       String resourceName1 = (String)row1.get(7);
/* 1068 */       String resourceid1 = (String)row1.get(6);
/* 1069 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1070 */       if (severity[j] == null)
/*      */       {
/* 1072 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1076 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1078 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1080 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1083 */         if (sev > 0) {
/* 1084 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1085 */           monitorList.set(k, monitorList.get(j));
/* 1086 */           monitorList.set(j, t);
/* 1087 */           String temp = severity[k];
/* 1088 */           severity[k] = severity[j];
/* 1089 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1095 */     int z = 0;
/* 1096 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1099 */       int i = 0;
/* 1100 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1103 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1107 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1111 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1113 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1116 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1120 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1123 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1124 */       String resourceName1 = (String)row1.get(7);
/* 1125 */       String resourceid1 = (String)row1.get(6);
/* 1126 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1127 */       if (hseverity[j] == null)
/*      */       {
/* 1129 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1134 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1136 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1139 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1142 */         if (hsev > 0) {
/* 1143 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1144 */           monitorList.set(k, monitorList.get(j));
/* 1145 */           monitorList.set(j, t);
/* 1146 */           String temp1 = hseverity[k];
/* 1147 */           hseverity[k] = hseverity[j];
/* 1148 */           hseverity[j] = temp1;
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
/* 1160 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1161 */     boolean forInventory = false;
/* 1162 */     String trdisplay = "none";
/* 1163 */     String plusstyle = "inline";
/* 1164 */     String minusstyle = "none";
/* 1165 */     String haidTopLevel = "";
/* 1166 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1168 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1170 */         haidTopLevel = request.getParameter("haid");
/* 1171 */         forInventory = true;
/* 1172 */         trdisplay = "table-row;";
/* 1173 */         plusstyle = "none";
/* 1174 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1181 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1184 */     ArrayList listtoreturn = new ArrayList();
/* 1185 */     StringBuffer toreturn = new StringBuffer();
/* 1186 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1187 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1188 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1190 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1192 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1193 */       String childresid = (String)singlerow.get(0);
/* 1194 */       String childresname = (String)singlerow.get(1);
/* 1195 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1196 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1197 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1198 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1199 */       String unmanagestatus = (String)singlerow.get(5);
/* 1200 */       String actionstatus = (String)singlerow.get(6);
/* 1201 */       String linkclass = "monitorgp-links";
/* 1202 */       String titleforres = childresname;
/* 1203 */       String titilechildresname = childresname;
/* 1204 */       String childimg = "/images/trcont.png";
/* 1205 */       String flag = "enable";
/* 1206 */       String dcstarted = (String)singlerow.get(8);
/* 1207 */       String configMonitor = "";
/* 1208 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1209 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1211 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1213 */       if (singlerow.get(7) != null)
/*      */       {
/* 1215 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1217 */       String haiGroupType = "0";
/* 1218 */       if ("HAI".equals(childtype))
/*      */       {
/* 1220 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1222 */       childimg = "/images/trend.png";
/* 1223 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1224 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1225 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1227 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1229 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1231 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1232 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1235 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1237 */         linkclass = "disabledtext";
/* 1238 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1240 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1241 */       String availmouseover = "";
/* 1242 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1244 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1246 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1247 */       String healthmouseover = "";
/* 1248 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1250 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1253 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1254 */       int spacing = 0;
/* 1255 */       if (level >= 1)
/*      */       {
/* 1257 */         spacing = 40 * level;
/*      */       }
/* 1259 */       if (childtype.equals("HAI"))
/*      */       {
/* 1261 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1262 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1263 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1265 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1266 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1267 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1268 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1269 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1270 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1271 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1272 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1273 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1274 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1275 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1277 */         if (!forInventory)
/*      */         {
/* 1279 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1282 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1284 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1286 */           actions = editlink + actions;
/*      */         }
/* 1288 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1290 */           actions = actions + associatelink;
/*      */         }
/* 1292 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1293 */         String arrowimg = "";
/* 1294 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1296 */           actions = "";
/* 1297 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1298 */           checkbox = "";
/* 1299 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1301 */         if (isIt360)
/*      */         {
/* 1303 */           actionimg = "";
/* 1304 */           actions = "";
/* 1305 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1306 */           checkbox = "";
/*      */         }
/*      */         
/* 1309 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1311 */           actions = "";
/*      */         }
/* 1313 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1315 */           checkbox = "";
/*      */         }
/*      */         
/* 1318 */         String resourcelink = "";
/*      */         
/* 1320 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1322 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1326 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1329 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1330 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1331 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1332 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1333 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1334 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1335 */         if (!isIt360)
/*      */         {
/* 1337 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1341 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1344 */         toreturn.append("</tr>");
/* 1345 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1347 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1348 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1352 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1353 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1356 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1360 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1362 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1363 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1364 */             toreturn.append(assocMessage);
/* 1365 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1366 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1367 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1368 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1374 */         String resourcelink = null;
/* 1375 */         boolean hideEditLink = false;
/* 1376 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1378 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1379 */           hideEditLink = true;
/* 1380 */           if (isIt360)
/*      */           {
/* 1382 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1386 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1388 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1390 */           hideEditLink = true;
/* 1391 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1392 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1397 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1400 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1401 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1402 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1403 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1404 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1405 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1406 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1407 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1408 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1409 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1410 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1411 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1412 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1414 */         if (hideEditLink)
/*      */         {
/* 1416 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1418 */         if (!forInventory)
/*      */         {
/* 1420 */           removefromgroup = "";
/*      */         }
/* 1422 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1423 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1424 */           actions = actions + configcustomfields;
/*      */         }
/* 1426 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1428 */           actions = editlink + actions;
/*      */         }
/* 1430 */         String managedLink = "";
/* 1431 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1433 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1434 */           actions = "";
/* 1435 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1436 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1439 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1441 */           checkbox = "";
/*      */         }
/*      */         
/* 1444 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1446 */           actions = "";
/*      */         }
/* 1448 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1449 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1450 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1451 */         if (isIt360)
/*      */         {
/* 1453 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1457 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1459 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1460 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1461 */         if (!isIt360)
/*      */         {
/* 1463 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1467 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1469 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1472 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1479 */       StringBuilder toreturn = new StringBuilder();
/* 1480 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1481 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1482 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1483 */       String title = "";
/* 1484 */       message = EnterpriseUtil.decodeString(message);
/* 1485 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1486 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1487 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1489 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1491 */       else if ("5".equals(severity))
/*      */       {
/* 1493 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1497 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1499 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1500 */       toreturn.append(v);
/*      */       
/* 1502 */       toreturn.append(link);
/* 1503 */       if (severity == null)
/*      */       {
/* 1505 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1507 */       else if (severity.equals("5"))
/*      */       {
/* 1509 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1511 */       else if (severity.equals("4"))
/*      */       {
/* 1513 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1515 */       else if (severity.equals("1"))
/*      */       {
/* 1517 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1522 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1524 */       toreturn.append("</a>");
/* 1525 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1529 */       ex.printStackTrace();
/*      */     }
/* 1531 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1538 */       StringBuilder toreturn = new StringBuilder();
/* 1539 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1540 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1541 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1542 */       if (message == null)
/*      */       {
/* 1544 */         message = "";
/*      */       }
/*      */       
/* 1547 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1548 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1550 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1551 */       toreturn.append(v);
/*      */       
/* 1553 */       toreturn.append(link);
/*      */       
/* 1555 */       if (severity == null)
/*      */       {
/* 1557 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1559 */       else if (severity.equals("5"))
/*      */       {
/* 1561 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1563 */       else if (severity.equals("1"))
/*      */       {
/* 1565 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1570 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1572 */       toreturn.append("</a>");
/* 1573 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1579 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1582 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1583 */     if (invokeActions != null) {
/* 1584 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1585 */       while (iterator.hasNext()) {
/* 1586 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1587 */         if (actionmap.containsKey(actionid)) {
/* 1588 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1593 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1597 */     String actionLink = "";
/* 1598 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1599 */     String query = "";
/* 1600 */     ResultSet rs = null;
/* 1601 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1602 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1603 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1604 */       actionLink = "method=" + methodName;
/*      */     }
/* 1606 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1607 */       actionLink = methodName;
/*      */     }
/* 1609 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1610 */     Iterator itr = methodarglist.iterator();
/* 1611 */     boolean isfirstparam = true;
/* 1612 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1613 */     while (itr.hasNext()) {
/* 1614 */       HashMap argmap = (HashMap)itr.next();
/* 1615 */       String argtype = (String)argmap.get("TYPE");
/* 1616 */       String argname = (String)argmap.get("IDENTITY");
/* 1617 */       String paramname = (String)argmap.get("PARAMETER");
/* 1618 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1619 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1620 */         isfirstparam = false;
/* 1621 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1623 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1627 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1631 */         actionLink = actionLink + "&";
/*      */       }
/* 1633 */       String paramValue = null;
/* 1634 */       String tempargname = argname;
/* 1635 */       if (commonValues.getProperty(tempargname) != null) {
/* 1636 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1639 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1640 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1641 */           if (dbType.equals("mysql")) {
/* 1642 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1645 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1647 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1649 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1650 */             if (rs.next()) {
/* 1651 */               paramValue = rs.getString("VALUE");
/* 1652 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1656 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1660 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1663 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1668 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1669 */           paramValue = rowId;
/*      */         }
/* 1671 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1672 */           paramValue = managedObjectName;
/*      */         }
/* 1674 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1675 */           paramValue = resID;
/*      */         }
/* 1677 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1678 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1681 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1683 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1684 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1685 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1687 */     return actionLink;
/*      */   }
/*      */   
/* 1690 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1691 */     String dependentAttribute = null;
/* 1692 */     String align = "left";
/*      */     
/* 1694 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1695 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1696 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1697 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1698 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1699 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1700 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1701 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1702 */       align = "center";
/*      */     }
/*      */     
/* 1705 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1706 */     String actualdata = "";
/*      */     
/* 1708 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1709 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1710 */         actualdata = availValue;
/*      */       }
/* 1712 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1713 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1717 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1718 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1721 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1727 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1728 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1729 */       toreturn.append("<table>");
/* 1730 */       toreturn.append("<tr>");
/* 1731 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1732 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1733 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1734 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1735 */         String toolTip = "";
/* 1736 */         String hideClass = "";
/* 1737 */         String textStyle = "";
/* 1738 */         boolean isreferenced = true;
/* 1739 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1740 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1741 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1742 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1744 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1745 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1746 */           while (valueList.hasMoreTokens()) {
/* 1747 */             String dependentVal = valueList.nextToken();
/* 1748 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1749 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1750 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1752 */               toolTip = "";
/* 1753 */               hideClass = "";
/* 1754 */               isreferenced = false;
/* 1755 */               textStyle = "disabledtext";
/* 1756 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1760 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1761 */           toolTip = "";
/* 1762 */           hideClass = "";
/* 1763 */           isreferenced = false;
/* 1764 */           textStyle = "disabledtext";
/* 1765 */           if (dependentImageMap != null) {
/* 1766 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1767 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1770 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1774 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1775 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1776 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1777 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1778 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1779 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1781 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1782 */           if (isreferenced) {
/* 1783 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1787 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1788 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1789 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1790 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1791 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1792 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1794 */           toreturn.append("</span>");
/* 1795 */           toreturn.append("</a>");
/* 1796 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1799 */       toreturn.append("</tr>");
/* 1800 */       toreturn.append("</table>");
/* 1801 */       toreturn.append("</td>");
/*      */     } else {
/* 1803 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1806 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1810 */     String colTime = null;
/* 1811 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1812 */     if ((rows != null) && (rows.size() > 0)) {
/* 1813 */       Iterator<String> itr = rows.iterator();
/* 1814 */       String maxColQuery = "";
/* 1815 */       for (;;) { if (itr.hasNext()) {
/* 1816 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1817 */           ResultSet maxCol = null;
/*      */           try {
/* 1819 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1820 */             while (maxCol.next()) {
/* 1821 */               if (colTime == null) {
/* 1822 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1825 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1834 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1836 */               if (maxCol != null)
/* 1837 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1839 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1834 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1836 */               if (maxCol != null)
/* 1837 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1839 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1844 */     return colTime;
/*      */   }
/*      */   
/* 1847 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1848 */     tablename = null;
/* 1849 */     ResultSet rsTable = null;
/* 1850 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1852 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1853 */       while (rsTable.next()) {
/* 1854 */         tablename = rsTable.getString("DATATABLE");
/* 1855 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1856 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1869 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1860 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1863 */         if (rsTable != null)
/* 1864 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1866 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1872 */     String argsList = "";
/* 1873 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1875 */       if (showArgsMap.get(row) != null) {
/* 1876 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1877 */         if (showArgslist != null) {
/* 1878 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1879 */             if (argsList.trim().equals("")) {
/* 1880 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1883 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1890 */       e.printStackTrace();
/* 1891 */       return "";
/*      */     }
/* 1893 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1898 */     String argsList = "";
/* 1899 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1902 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1904 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1905 */         if (hideArgsList != null)
/*      */         {
/* 1907 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1909 */             if (argsList.trim().equals(""))
/*      */             {
/* 1911 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1915 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1923 */       ex.printStackTrace();
/*      */     }
/* 1925 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1929 */     StringBuilder toreturn = new StringBuilder();
/* 1930 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1937 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1938 */       Iterator itr = tActionList.iterator();
/* 1939 */       while (itr.hasNext()) {
/* 1940 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1941 */         String confirmmsg = "";
/* 1942 */         String link = "";
/* 1943 */         String isJSP = "NO";
/* 1944 */         HashMap tactionMap = (HashMap)itr.next();
/* 1945 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1946 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1947 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1948 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1949 */           (actionmap.containsKey(actionId))) {
/* 1950 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1951 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1952 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1953 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1954 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1956 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1962 */           if (isTableAction) {
/* 1963 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1966 */             tableName = "Link";
/* 1967 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1968 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1969 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1970 */             toreturn.append("</a></td>");
/*      */           }
/* 1972 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1973 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1974 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1975 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1981 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1987 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1989 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1990 */       Properties prop = (Properties)node.getUserObject();
/* 1991 */       String mgID = prop.getProperty("label");
/* 1992 */       String mgName = prop.getProperty("value");
/* 1993 */       String isParent = prop.getProperty("isParent");
/* 1994 */       int mgIDint = Integer.parseInt(mgID);
/* 1995 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1997 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1999 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2000 */       if (node.getChildCount() > 0)
/*      */       {
/* 2002 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2004 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2006 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2008 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2012 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2017 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2019 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2021 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2023 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2027 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2030 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2031 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2033 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2037 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2039 */       if (node.getChildCount() > 0)
/*      */       {
/* 2041 */         builder.append("<UL>");
/* 2042 */         printMGTree(node, builder);
/* 2043 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2048 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2049 */     StringBuffer toReturn = new StringBuffer();
/* 2050 */     String table = "-";
/*      */     try {
/* 2052 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2053 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2054 */       float total = 0.0F;
/* 2055 */       while (it.hasNext()) {
/* 2056 */         String attName = (String)it.next();
/* 2057 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2058 */         boolean roundOffData = false;
/* 2059 */         if ((data != null) && (!data.equals(""))) {
/* 2060 */           if (data.indexOf(",") != -1) {
/* 2061 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2064 */             float value = Float.parseFloat(data);
/* 2065 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2068 */             total += value;
/* 2069 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2072 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2077 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2078 */       while (attVsWidthList.hasNext()) {
/* 2079 */         String attName = (String)attVsWidthList.next();
/* 2080 */         String data = (String)attVsWidthProps.get(attName);
/* 2081 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2082 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2083 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2084 */         String className = (String)graphDetails.get("ClassName");
/* 2085 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2086 */         if (percentage < 1.0F)
/*      */         {
/* 2088 */           data = percentage + "";
/*      */         }
/* 2090 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2092 */       if (toReturn.length() > 0) {
/* 2093 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2097 */       e.printStackTrace();
/*      */     }
/* 2099 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2105 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2106 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2107 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2108 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2109 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2110 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2111 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2112 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2113 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2116 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2117 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2118 */       splitvalues[0] = multiplecondition.toString();
/* 2119 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2122 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2127 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2128 */     if (thresholdType != 3) {
/* 2129 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2130 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2131 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2132 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2133 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2134 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2136 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2137 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2138 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2139 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2140 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2141 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2143 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2144 */     if (updateSelected != null) {
/* 2145 */       updateSelected[0] = "selected";
/*      */     }
/* 2147 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2152 */       StringBuffer toreturn = new StringBuffer("");
/* 2153 */       if (commaSeparatedMsgId != null) {
/* 2154 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2155 */         int count = 0;
/* 2156 */         while (msgids.hasMoreTokens()) {
/* 2157 */           String id = msgids.nextToken();
/* 2158 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2159 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2160 */           count++;
/* 2161 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2162 */             if (toreturn.length() == 0) {
/* 2163 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2165 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2166 */             if (!image.trim().equals("")) {
/* 2167 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2169 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2170 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2173 */         if (toreturn.length() > 0) {
/* 2174 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2178 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2181 */       e.printStackTrace(); }
/* 2182 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2188 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2194 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2195 */   static { _jspx_dependants.put("/jsp/includes/newresourcetypes.jspf", Long.valueOf(1473429417000L));
/* 2196 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2197 */     _jspx_dependants.put("/jsp/includes/MonitorDiscoveryStatus.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2232 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2236 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2264 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2268 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2269 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2270 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2271 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2272 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2273 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2274 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2275 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2276 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.release();
/* 2277 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2278 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2279 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/* 2280 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2281 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2282 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2283 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2284 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2285 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/* 2286 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2287 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fnobody.release();
/* 2288 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2289 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.release();
/* 2290 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2291 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/* 2292 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.release();
/* 2293 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody.release();
/* 2294 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2301 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2304 */     JspWriter out = null;
/* 2305 */     Object page = this;
/* 2306 */     JspWriter _jspx_out = null;
/* 2307 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2311 */       response.setContentType("text/html;charset=UTF-8");
/* 2312 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2314 */       _jspx_page_context = pageContext;
/* 2315 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2316 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2317 */       session = pageContext.getSession();
/* 2318 */       out = pageContext.getOut();
/* 2319 */       _jspx_out = out;
/*      */       
/* 2321 */       out.write("<!DOCTYPE html>\n");
/* 2322 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n <!--$Id$-->\n\n");
/*      */       
/* 2324 */       request.setAttribute("HelpKey", "Configuring Ping Monitor");
/*      */       
/* 2326 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2327 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2329 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2330 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2331 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2333 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2335 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2337 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2339 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2340 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2341 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2342 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2345 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2346 */         String available = null;
/* 2347 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2348 */         out.write(10);
/*      */         
/* 2350 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2351 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2352 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2354 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2356 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2358 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2360 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2361 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2362 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2363 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2366 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2367 */           String unavailable = null;
/* 2368 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2369 */           out.write(10);
/*      */           
/* 2371 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2372 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2373 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2375 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2377 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2379 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2381 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2382 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2383 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2384 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2387 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2388 */             String unmanaged = null;
/* 2389 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2390 */             out.write(10);
/*      */             
/* 2392 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2393 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2394 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2396 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2398 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2400 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2402 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2403 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2404 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2405 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2408 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2409 */               String scheduled = null;
/* 2410 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2411 */               out.write(10);
/*      */               
/* 2413 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2414 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2415 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2417 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2419 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2421 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2423 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2424 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2425 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2426 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2429 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2430 */                 String critical = null;
/* 2431 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2432 */                 out.write(10);
/*      */                 
/* 2434 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2435 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2436 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2438 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2440 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2442 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2444 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2445 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2446 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2447 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2450 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2451 */                   String clear = null;
/* 2452 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2453 */                   out.write(10);
/*      */                   
/* 2455 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2456 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2457 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2459 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2461 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2463 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2465 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2466 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2467 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2468 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2471 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2472 */                     String warning = null;
/* 2473 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2474 */                     out.write(10);
/* 2475 */                     out.write(10);
/*      */                     
/* 2477 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2478 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2480 */                     out.write(10);
/* 2481 */                     out.write(10);
/* 2482 */                     out.write(10);
/* 2483 */                     out.write(10);
/* 2484 */                     ManagedApplication mo = null;
/* 2485 */                     mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2486 */                     if (mo == null) {
/* 2487 */                       mo = new ManagedApplication();
/* 2488 */                       _jspx_page_context.setAttribute("mo", mo, 1);
/*      */                     }
/* 2490 */                     out.write(10);
/* 2491 */                     Hashtable applications = null;
/* 2492 */                     synchronized (application) {
/* 2493 */                       applications = (Hashtable)_jspx_page_context.getAttribute("applications", 4);
/* 2494 */                       if (applications == null) {
/* 2495 */                         applications = new Hashtable();
/* 2496 */                         _jspx_page_context.setAttribute("applications", applications, 4);
/*      */                       }
/*      */                     }
/* 2499 */                     out.write("\n\n\n<SCRIPT LANGAUGE =\"javascript\" SRC=\"../template/appmanager.js\" ></SCRIPT>\n\n");
/*      */                     
/*      */ 
/* 2502 */                     ArrayList dynamicSites = AlarmUtil.getSiteMonitorGroups();
/* 2503 */                     if (dynamicSites != null)
/*      */                     {
/* 2505 */                       request.setAttribute("dynamicSites", dynamicSites);
/*      */                     }
/*      */                     
/* 2508 */                     out.write("\n\n\n<script>\n\nfunction loadSite()\n{\n\tdocument.AMActionForm.haid.options.length=0;\n\tvar formCustomerId = document.AMActionForm.organization.value;\n\tvar siteName;\n\tvar siteId;\n\tvar customerId;\n\tvar rowCount=0;\n\tdocument.AMActionForm.haid.options[rowCount++] = new Option('-Select Site-','-'); //No I18N\n\t");
/* 2509 */                     if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*      */                       return;
/* 2511 */                     out.write("\n}\n\nfunction resetname(name)\n{\n\tif(name='groupname')\n\t{\n\t\tdocument.AMActionForm.groupname.value='';\n\t}\n\tif(name='subgroupname')\n\t{\n\t\tdocument.AMActionForm.subgroupname.value='';\n\t}\n}\nfunction createGroup()\n{\n\tif(document.AMActionForm.groupname.value=='')\n\t{\n\t\talert(\"");
/* 2512 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2513 */                     out.write("\");\n\t\thideDiv('group');\n\t\tshowDiv('creategroup');\n\t\tdocument.AMActionForm.groupname.focus();\n\t\treturn false;\n\t}\n\telse\n\t{\n\t\thideDiv('creategroup');\n\t\tvar a=document.AMActionForm.groupname.value;\n\t\turl=\"/adminAction.do?method=createMonitorGroup&groupname=\"+encodeURIComponent(a);\n\t\thttp.open(\"GET\",url,true);\n\t\thttp.onreadystatechange = getActionTypes;\n\t\thttp.send(null);\n\t\tshowDiv('group');\n\t}\n\n}  \nfunction check()\n{\n\thideDiv(\"creategroup\");\n\thideDiv(\"createsubgroup\");\n\thideDiv(\"groupmessage\");\n\tvar flag='");
/* 2514 */                     out.print(com.adventnet.appmanager.util.Constants.subGroupsEnabled);
/* 2515 */                     out.write("';\n\tif(flag==\"true\")\n\t{\n\t\tif(document.AMActionForm.haid.value=='-')\n\t\t{\n\t\t\thideDiv(\"subgroup\");\n\t\t\tshowDiv(\"group\");\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv(\"group\");\n\t\t\tshowDiv(\"subgroup\");\n\t\t}\n\t}\n\telse\n\t{\n\t\tshowDiv(\"group\");\n\t}\n}\nfunction createsubGroup()\n{\n\tif(document.AMActionForm.haid.value=='-')\n\t{\n\t\talert(\"");
/* 2516 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.monitoralert"));
/* 2517 */                     out.write("\");\t\t\n\t\tdocument.AMActionForm.haid.focus();\n\t}\n\telse\n\t{\n\t\tif(document.AMActionForm.subgroupname.value=='')\n\t\t{\n\t\t\talert(\"");
/* 2518 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2519 */                     out.write("\");\n\t\t\tdocument.AMActionForm.subgroupname.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv('createsubgroup');\n\t\t\tvar a=document.AMActionForm.subgroupname.value;\n\t\t\tvar haid=document.AMActionForm.haid.value;\n\t\t\turl=\"/adminAction.do?method=createSubGroup&haid=\"+haid+\"&subgroupname=\"+encodeURIComponent(a);\n\t\t\thttp.open(\"GET\",url,true);\n\t\t\thttp.onreadystatechange = getActionTypes;\n\t\t\thttp.send(null);\n\t\t}\n\t\tshowDiv('subgroup');\n    }\n\n}  \n\n function getActionTypes()\n{\n    if(http.readyState == 4)\n    {\n\t\tvar result = http.responseText;\n\t\tvar id=result;\n\t\tvar stringtokens=id.split(\",\");\n\t\tsid = stringtokens[2];\n\t\tsname=stringtokens[1];\n\t\tsname=decodeURIComponent(stringtokens[1]);\n\t\tsmessage=stringtokens[0];\n\t\tif (sname==null || sname=='undefined')\n\t\t{\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.AMActionForm.haid.options[document.AMActionForm.haid.length] =new Option(sname,sid,false,true);\n\t\t\thideDiv(\"creategroup\");\n\t\t\thideDiv(\"createsubgroup\");\n");
/* 2520 */                     out.write("\t\t\thideDiv(\"group\");\n\t\t\tshowDiv(\"subgroup\");\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t  \t}\n\t}\n}\n\nfunction formReload()\n{\n\tvar v=document.AMActionForm.type.value;\n\t//var portNo=v.substring(v.indexOf(\":\")+1,v.length);\n\t//document.AMActionForm.method=\"post\";\n\tdocument.AMActionForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=\"+v+\"&hideFieldsForIT360=");
/* 2521 */                     out.print(request.getParameter("hideFieldsForIT360"));
/* 2522 */                     out.write("\";\n\t//enableAll();\n\tdocument.AMActionForm.submit();\n\n}\n\nfunction validateAndSubmit(){\n\nif(document.AMActionForm.hostname.value == ''){\n\talert('");
/* 2523 */                     out.print(FormatUtil.getString("am.webclient.script.hostname.alert"));
/* 2524 */                     out.write("');\n\tdocument.AMActionForm.hostname.focus();\n\treturn ;\n}\n var poll=trimAll(document.AMActionForm.pollInterval.value);\n        if(poll == '' || !(isPositiveInteger(poll)) || poll =='0' )\n            {\n                alert(\"");
/* 2525 */                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */                       return;
/* 2527 */                     out.write("\");\n\t\t document.AMActionForm.pollInterval.focus();\n                return;\n\n        }\n//var retry = document.AMActionForm.retries.value;\n//if(retry == ' ' || !(isPositiveInteger(retry)))\n//{\n\n//\talert(\"Enter a positive value for No. of retries\");\n//\t document.AMActionForm.retries.focus();\n//\treturn;\n//}\nvar timeout = document.AMActionForm.ptimeout.value;\n\nif(timeout == ' ' || !(isPositiveInteger(timeout)))\n{\n\n        alert('");
/* 2528 */                     out.print(FormatUtil.getString("am.webclient.timeout.alert"));
/* 2529 */                     out.write("');\n\tdocument.AMActionForm.ptimeout.focus();\n\treturn;\n}\n\n\nif(document.AMActionForm.haid.value != '-'){\ndocument.AMActionForm.addtoha.value=\"true\";\n}else{\ndocument.AMActionForm.addtoha.value=\"false\";\n}\n\n/***  IT360-1762 ISSUES CHANGES STARTS HERE ***/\n\n");
/* 2530 */                     if (EnterpriseUtil.isIt360MSPEdition()) {
/* 2531 */                       out.write("\n\nif (document.AMActionForm.organization.value== \"-\")\n {\n\talert(\"");
/* 2532 */                       out.print(FormatUtil.getString("it360.addnewmonitor.err.checkcustomer"));
/* 2533 */                       out.write("\");\n\treturn;\n }\n\nif (document.AMActionForm.haid.value== \"-\")\n {\n    alert(\"");
/* 2534 */                       out.print(FormatUtil.getString("it360.addnewmonitor.err.checksite"));
/* 2535 */                       out.write("\");\n    return;\n }\n");
/*      */                     }
/* 2537 */                     out.write("\n\n/***  IT360-1762 ISSUES CHANGES ENDS HERE ***/\ndocument.AMActionForm.action='/createPing.do?method=createMonitor';\ndocument.AMActionForm.submit();\n}\n\n\n\n\nfunction showServiceDetail()\n{\n\tvar hostNameToSend = trimAll(document.AMActionForm.hostname.value) ;\n\tif(hostNameToSend==\"\")\n\t{\n\t\t//document.getElementById(\"showNotRunningMessage\").style.display='none';\n\t\t//document.AMActionForm.host.focus();\n\t\treturn;\n\t}\n\tif(http)\n\t{\n\t\tvar url = '/jsp/HostNameDiscovery.jsp?hostName='+escape(hostNameToSend);\n\t\thttp.open(\"GET\",url,true);\n\t\thttp.onreadystatechange = handleServiceMessage;\n\t\thttp.send(null);\n\t}\n\treturn false;\n}\n\nfunction handleServiceMessage()\n{\n        if (http.readyState == 4)\n        {\n                var result = http.responseText ;\n                var element=(document.getElementsByName(\"hostname\"))[0];\n                var temp=null;\n                var isPointerReq=false;\n                var red=\"#FF0000\";\n                ddrivetip(element,temp,result,isPointerReq,true,null,red);\n                setTimeout(\"hideDialog()\",6000);\n");
/* 2538 */                     out.write("        }\n}\n\nfunction hideDialog()\n{\n        startHideFade(\"dhtmltooltip\",0.04);\n}\n\n\nfunction handleGlobalSecurity(checkbox)\n{\n        if(checkbox.checked)\n        {\n                showDiv(\"advOption\");\n        }\n        else\n        {\n                hideDiv(\"advOption\");\n        }\n}\n</script>\n\n    ");
/*      */                     
/* 2540 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2541 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2542 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2544 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayout.jsp");
/* 2545 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2546 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2548 */                         out.write("\n    ");
/*      */                         
/* 2550 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2551 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2552 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2554 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2556 */                         _jspx_th_tiles_005fput_005f0.setValue("Ping Monitor");
/* 2557 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2558 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2559 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2562 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2563 */                         out.write("\n    ");
/* 2564 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2566 */                         out.write("\n\n\t ");
/* 2567 */                         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2569 */                         out.write(10);
/*      */                         
/* 2571 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2572 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2573 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2575 */                         _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                         
/* 2577 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 2578 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2579 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2580 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2581 */                             out = _jspx_page_context.pushBody();
/* 2582 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2583 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2586 */                             out.write(10);
/*      */                             
/* 2588 */                             String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/* 2589 */                             if (hideFieldsForIT360 == null)
/*      */                             {
/* 2591 */                               hideFieldsForIT360 = (String)request.getAttribute("hideFieldsForIT360");
/*      */                             }
/*      */                             
/* 2594 */                             boolean hideFields = false;
/* 2595 */                             String hideStyle = "";
/* 2596 */                             if ((hideFieldsForIT360 != null) && (hideFieldsForIT360.equals("true")))
/*      */                             {
/* 2598 */                               hideFields = true;
/* 2599 */                               hideStyle = "hideContent";
/*      */                             }
/* 2601 */                             boolean isDiscoveryComplete = false;
/* 2602 */                             boolean isDiscoverySuccess = false;
/* 2603 */                             String title = request.getParameter("type");
/*      */                             
/* 2605 */                             out.write(10);
/* 2606 */                             out.write(10);
/* 2607 */                             out.write(32);
/*      */                             
/* 2609 */                             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.get(FormTag.class);
/* 2610 */                             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2611 */                             _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2613 */                             _jspx_th_html_005fform_005f0.setAction("/createPing");
/*      */                             
/* 2615 */                             _jspx_th_html_005fform_005f0.setEnctype("multipart/form-data");
/*      */                             
/* 2617 */                             _jspx_th_html_005fform_005f0.setStyle("display:inline");
/* 2618 */                             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2619 */                             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                               for (;;) {
/* 2621 */                                 out.write("\n        <input type=\"hidden\" name=\"addtoha\" value=\"true\">\n        <input type=\"hidden\" name=\"hideFieldsForIT360\" value=\"");
/* 2622 */                                 out.print(request.getParameter("hideFieldsForIT360"));
/* 2623 */                                 out.write("\">\n\t");
/*      */                                 
/* 2625 */                                 NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2626 */                                 _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2627 */                                 _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2629 */                                 _jspx_th_logic_005fnotEmpty_005f0.setName("discoverystatus");
/* 2630 */                                 int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2631 */                                 if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                   for (;;) {
/* 2633 */                                     out.write(32);
/* 2634 */                                     out.write(32);
/* 2635 */                                     out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n");
/*      */                                     
/* 2637 */                                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2638 */                                     _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2639 */                                     _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                     
/* 2641 */                                     _jspx_th_logic_005fnotEmpty_005f1.setName("discoverystatus");
/* 2642 */                                     int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2643 */                                     if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                       for (;;) {
/* 2645 */                                         out.write(10);
/*      */                                         
/*      */ 
/* 2648 */                                         if ((request.getAttribute("type") == null) || ((!request.getAttribute("type").equals("Script Monitor")) && ((request.getAttribute("basetype") == null) || ((request.getAttribute("basetype") != null) && (!request.getAttribute("basetype").equals("Script Monitor")))) && (!request.getAttribute("type").equals("QENGINE")) && (!request.getAttribute("type").equals("Web Service")) && (!request.getAttribute("type").equals("file")) && (!request.getAttribute("type").equals("directory")) && (!request.getAttribute("type").equals("File System Monitor")) && (!request.getAttribute("type").equals("Ping Monitor")) && (!request.getAttribute("type").equals("SAP-CCMS"))))
/*      */                                         {
/*      */ 
/* 2651 */                                           out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n  <tr>\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2652 */                                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2653 */                                           out.write("</span> </td>\n    <td width=\"7%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2654 */                                           out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 2655 */                                           out.write("\n      </span> </td>\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2656 */                                           out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 2657 */                                           out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2658 */                                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2659 */                                           out.write("\n </span></td>\n  <tr>\n  ");
/*      */                                           
/* 2661 */                                           int failedNumber = 1;
/*      */                                           
/* 2663 */                                           out.write(10);
/*      */                                           
/* 2665 */                                           IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2666 */                                           _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2667 */                                           _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                           
/* 2669 */                                           _jspx_th_logic_005fiterate_005f0.setName("discoverystatus");
/*      */                                           
/* 2671 */                                           _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                           
/* 2673 */                                           _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */                                           
/* 2675 */                                           _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2676 */                                           int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2677 */                                           if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2678 */                                             ArrayList row = null;
/* 2679 */                                             Integer i = null;
/* 2680 */                                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2681 */                                               out = _jspx_page_context.pushBody();
/* 2682 */                                               _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2683 */                                               _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                             }
/* 2685 */                                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2686 */                                             i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                             for (;;) {
/* 2688 */                                               out.write("\n<tr>\n<td height=\"18\" class=\"bodytext\">");
/* 2689 */                                               out.print(row.get(0));
/* 2690 */                                               out.write("</td>\n<td height=\"18\" class=\"bodytext\">");
/* 2691 */                                               out.print(row.get(1));
/* 2692 */                                               out.write("</td>\n\n    <td height=\"18\" class=\"bodytext\">\n      ");
/*      */                                               
/* 2694 */                                               if ((row.get(2).equals("Success")) || (row.get(2).equals("Present")))
/*      */                                               {
/* 2696 */                                                 request.setAttribute("isDiscoverySuccess", "true");
/*      */                                                 
/* 2698 */                                                 out.write("\n      <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2699 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2700 */                                                 out.write("\" align=\"absmiddle\">\n      ");
/*      */ 
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 2705 */                                                 request.setAttribute("isDiscoverySuccess", "false");
/*      */                                                 
/* 2707 */                                                 out.write("\n      <img alt=\"");
/* 2708 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.notiniatedimagetip.text"));
/* 2709 */                                                 out.write("\" src=\"/images/icon_monitor_failure.gif\" align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                               }
/*      */                                               
/*      */ 
/* 2713 */                                               out.write("\n      <span class=\"bodytextbold\">");
/* 2714 */                                               out.print(FormatUtil.getString(String.valueOf(row.get(2))));
/* 2715 */                                               out.write("</span> </td>\n\n      ");
/*      */                                               
/* 2717 */                                               pageContext.setAttribute("ret_msg", String.valueOf(row.get(3)));
/*      */                                               
/* 2719 */                                               out.write("\n                     ");
/*      */                                               
/* 2721 */                                               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2722 */                                               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2723 */                                               _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                               
/* 2725 */                                               _jspx_th_c_005fif_005f3.setTest("${ret_msg=='Monitoring Initiated Succesfully'}");
/* 2726 */                                               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2727 */                                               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                                 for (;;) {
/* 2729 */                                                   out.write("\n                           <td height=\"18\" class=\"bodytext\">");
/* 2730 */                                                   out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 2731 */                                                   out.write("\n                                 ");
/* 2732 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2733 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 2737 */                                               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2738 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                               }
/*      */                                               
/* 2741 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2742 */                                               out.write("\n                                       ");
/*      */                                               
/* 2744 */                                               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2745 */                                               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2746 */                                               _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                               
/* 2748 */                                               _jspx_th_c_005fif_005f4.setTest("${ret_msg!='Monitoring Initiated Succesfully'}");
/* 2749 */                                               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2750 */                                               if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                                 for (;;) {
/* 2752 */                                                   out.write("\n                                             <td height=\"18\" class=\"bodytext\">");
/* 2753 */                                                   out.print(row.get(3));
/* 2754 */                                                   out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                   
/* 2756 */                                                   if ((request.getAttribute("type") != null) && (!request.getAttribute("type").equals("All")) && (!request.getAttribute("type").equals("TELNET")) && (!request.getAttribute("type").equals("SNMP")) && (!request.getAttribute("type").equals("SERVICE")) && (!request.getAttribute("type").equals("JMX1.2-MX4J-RMI")) && (!request.getAttribute("type").equals("JDK1.5")) && (!request.getAttribute("type").equals("RMI")) && (!request.getAttribute("type").equals("PHP")) && (!request.getAttribute("type").equals("MAIL")) && (!request.getAttribute("type").equals("ORACLEDB")) && (!request.getAttribute("type").equals("MYSQLDB")) && (!request.getAttribute("type").equals("MSSQLDB")) && (!request.getAttribute("type").equals("DB2")) && (!request.getAttribute("type").equals("WTA")) && (!request.getAttribute("type").equals("SAP")))
/*      */                                                   {
/* 2758 */                                                     if (((String)pageContext.getAttribute("ret_msg")).indexOf(FormatUtil.getString("am.webclient.discovery.host.monitoradded.text")) == -1)
/*      */                                                     {
/* 2760 */                                                       String fWhr = request.getParameter("hideFieldsForIT360");
/* 2761 */                                                       if (fWhr == null)
/*      */                                                       {
/* 2763 */                                                         fWhr = (String)request.getAttribute("hideFieldsForIT360");
/*      */                                                       }
/*      */                                                       
/* 2766 */                                                       if (((fWhr == null) || (!fWhr.equals("true"))) && 
/* 2767 */                                                         (!request.getAttribute("type").equals("SYBASEDB")))
/*      */                                                       {
/* 2769 */                                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"bodytext\" href=\"javascript:void(0)\" onClick=\"getAllDetailsOfHost()\">");
/* 2770 */                                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.diagnose.link"));
/* 2771 */                                                         out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                       }
/*      */                                                     } }
/* 2774 */                                                   if ((request.getAttribute("showSupportMessage") != null) && (failedNumber == 1) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                                                   {
/* 2776 */                                                     failedNumber++;
/*      */                                                     
/*      */ 
/* 2779 */                                                     out.write("\n\t\t\t\t\t\t\t\t\t\t<br>");
/* 2780 */                                                     out.print(FormatUtil.getString("am.webclient.discovery.host.support.link", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.talkback.mailid"), com.adventnet.appmanager.util.OEMUtil.getOEMString("product.tollfree.number") }));
/* 2781 */                                                     out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                                   }
/* 2783 */                                                   out.write("\n                                                   ");
/* 2784 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2785 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 2789 */                                               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2790 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                               }
/*      */                                               
/* 2793 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2794 */                                               out.write(10);
/* 2795 */                                               out.write(10);
/* 2796 */                                               out.write(10);
/*      */                                               
/*      */ 
/* 2799 */                                               if (row.size() > 4)
/*      */                                               {
/*      */ 
/* 2802 */                                                 out.write("<br>\n");
/* 2803 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)row.get(4) }));
/* 2804 */                                                 out.write(10);
/*      */                                               }
/*      */                                               
/*      */ 
/* 2808 */                                               out.write("\n</td>\n\n</tr>\n");
/* 2809 */                                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2810 */                                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2811 */                                               i = (Integer)_jspx_page_context.findAttribute("i");
/* 2812 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 2815 */                                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2816 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 2819 */                                           if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2820 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                           }
/*      */                                           
/* 2823 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2824 */                                           out.write("\n</table>\n");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 2829 */                                           ArrayList al1 = (ArrayList)request.getAttribute("discoverystatus");
/*      */                                           
/* 2831 */                                           out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n<tr>\n");
/* 2832 */                                           String mtype = (String)request.getAttribute("type");
/* 2833 */                                           out.write(10);
/* 2834 */                                           if (mtype.equals("File System Monitor")) {
/* 2835 */                                             out.write("\n  <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2836 */                                             out.print(FormatUtil.getString("File/Directory Name"));
/* 2837 */                                             out.write("</span> </td>\n");
/* 2838 */                                           } else if ((((String)request.getAttribute("type")).equals("Ping Monitor")) || (((String)request.getAttribute("type")).equals("SAP-CCMS"))) {
/* 2839 */                                             out.write("\n\t<td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2840 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2841 */                                             out.write("</span> </td>\n");
/*      */                                           } else {
/* 2843 */                                             out.write("\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2844 */                                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 2845 */                                             out.write("</span> </td>\n");
/*      */                                           }
/* 2847 */                                           out.write("\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2848 */                                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 2849 */                                           out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2850 */                                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2851 */                                           out.write("</span></td>\n  </tr>\n  <tr>\n   <td height=\"18\" class=\"bodytext\">");
/* 2852 */                                           out.print(al1.get(0));
/* 2853 */                                           out.write("</td>\n   <td height=\"18\" class=\"bodytext\">\n   ");
/*      */                                           
/* 2855 */                                           if (al1.get(1).equals("Success"))
/*      */                                           {
/* 2857 */                                             request.setAttribute("isDiscoverySuccess", "true");
/*      */                                             
/* 2859 */                                             out.write("\n   <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2860 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2861 */                                             out.write("\" align=\"absmiddle\">\n    ");
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 2866 */                                             request.setAttribute("isDiscoverySuccess", "false");
/*      */                                             
/*      */ 
/* 2869 */                                             out.write("\n      <img  src=\"/images/icon_monitor_failure.gif\"  align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                           }
/*      */                                           
/*      */ 
/* 2873 */                                           out.write("\n<span class=\"bodytextbold\">");
/* 2874 */                                           out.print(FormatUtil.getString(String.valueOf(al1.get(1))));
/* 2875 */                                           out.write("</span> </td>\n");
/*      */                                           
/* 2877 */                                           if (al1.get(1).equals("Success"))
/*      */                                           {
/* 2879 */                                             boolean isAdminServer = EnterpriseUtil.isAdminServer();
/* 2880 */                                             if (isAdminServer) {
/* 2881 */                                               String masDisplayName = (String)al1.get(3);
/* 2882 */                                               String format = FormatUtil.getString("am.webclient.admin.add.monitor.successfully.configured.text", new String[] { masDisplayName, "", (String)al1.get(2) });
/*      */                                               
/* 2884 */                                               out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\">");
/* 2885 */                                               out.print(format);
/* 2886 */                                               out.write("</td>\n");
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 2890 */                                               out.write("\t\t\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"> ");
/* 2891 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2892 */                                               out.write("<br> ");
/* 2893 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)al1.get(2) }));
/* 2894 */                                               out.write("</td>\n");
/*      */                                             }
/*      */                                             
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 2901 */                                             out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"><span class=\"bodytext\">");
/* 2902 */                                             out.print(al1.get(2));
/* 2903 */                                             out.write("</span></td>\n");
/*      */                                           }
/*      */                                           
/*      */ 
/* 2907 */                                           out.write("\n  </tr>\n</table>\n\n");
/*      */                                         }
/*      */                                         
/*      */ 
/* 2911 */                                         out.write(10);
/* 2912 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2913 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2917 */                                     if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2918 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                     }
/*      */                                     
/* 2921 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2922 */                                     out.write(10);
/* 2923 */                                     out.write(10);
/* 2924 */                                     out.write(9);
/*      */                                     
/* 2926 */                                     String discSucc = (String)request.getAttribute("isDiscoverySuccess");
/* 2927 */                                     isDiscoveryComplete = true;
/* 2928 */                                     if ((discSucc != null) && (discSucc.equals("true")))
/*      */                                     {
/* 2930 */                                       isDiscoverySuccess = true;
/*      */                                     }
/*      */                                     
/* 2933 */                                     out.write("\n\t<br>\n");
/* 2934 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2935 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2939 */                                 if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2940 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                 }
/*      */                                 
/* 2943 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2944 */                                 out.write("\n        ");
/* 2945 */                                 if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2947 */                                 out.write(10);
/*      */                                 
/* 2949 */                                 if ((!hideFields) || ((!isDiscoveryComplete) && (hideFields)))
/*      */                                 {
/*      */ 
/* 2952 */                                   out.write(10);
/* 2953 */                                   out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link rel=\"stylesheet\" href=\"/images/chosen.css\" />\n");
/* 2954 */                                   String message = (String)request.getAttribute("typemessage");
/*      */                                   
/* 2956 */                                   ManagedApplication mo1 = new ManagedApplication();
/* 2957 */                                   Properties props = com.adventnet.appmanager.util.Constants.getValueForNewMonitor();
/* 2958 */                                   boolean isConfMonitor = false;
/* 2959 */                                   ConfMonitorConfiguration confMonConfig = ConfMonitorConfiguration.getInstance();
/* 2960 */                                   if (message != null)
/*      */                                   {
/* 2962 */                                     out.write("\n    <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n    <tr>\n      <td><img src=\"/images/icon_message_success.gif\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"bodytext\">\n        ");
/* 2963 */                                     out.print(message);
/* 2964 */                                     out.write("</span> </td>\n    </tr>\n  </table>\n     ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 2968 */                                   out.write("\n\n\n<table id=\"newResourceTypes\" width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n\t<td width=\"25%\" class=\"tableheading-monitor-config bodytext label-align addmonitor-label\">&nbsp;");
/* 2969 */                                   out.print(FormatUtil.getString("am.webclient.newresourcetypes.addmonitor.text"));
/* 2970 */                                   out.write("</td>\n    <td class=\"tableheading-monitor-config \" valign=\"middle\">\n");
/* 2971 */                                   if ("UrlSeq".equals(request.getParameter("type"))) {
/* 2972 */                                     DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 2973 */                                     if (frm != null) {
/* 2974 */                                       frm.set("type", "UrlSeq");
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 2978 */                                   if ("UrlMonitor".equals(request.getParameter("type"))) {
/* 2979 */                                     DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 2980 */                                     if (frm != null) {
/* 2981 */                                       frm.set("type", "UrlMonitor");
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 2985 */                                   if ("RBM".equals(request.getParameter("type"))) {
/* 2986 */                                     DynaActionForm frm = (DynaActionForm)request.getAttribute("RbmForm");
/* 2987 */                                     if (frm != null) {
/* 2988 */                                       frm.set("type", "RBM");
/*      */                                     }
/*      */                                   }
/*      */                                   
/*      */ 
/* 2993 */                                   out.write("\n\n    ");
/*      */                                   
/* 2995 */                                   IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2996 */                                   _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2997 */                                   _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 2999 */                                   _jspx_th_c_005fif_005f5.setTest("${empty param.wiz && empty param.fromAssociate}");
/* 3000 */                                   int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3001 */                                   if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                     for (;;) {
/* 3003 */                                       out.write("\n     ");
/*      */                                       
/* 3005 */                                       SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 3006 */                                       _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3007 */                                       _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */                                       
/* 3009 */                                       _jspx_th_html_005fselect_005f0.setProperty("type");
/*      */                                       
/* 3011 */                                       _jspx_th_html_005fselect_005f0.setStyle("background-color:#FDFEF2; font-size:13px;");
/*      */                                       
/* 3013 */                                       _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */                                       
/* 3015 */                                       _jspx_th_html_005fselect_005f0.setOnchange("javascript:formReload()");
/* 3016 */                                       int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3017 */                                       if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3018 */                                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3019 */                                           out = _jspx_page_context.pushBody();
/* 3020 */                                           _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3021 */                                           _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3024 */                                           out.write("\n\n      <!-- If you are changing any of the values in this select box then kindly update the corresponding strings in HostDiscoveryHandler.java also-->\n      ");
/*      */                                           
/* 3026 */                                           if ((!com.adventnet.appmanager.util.Constants.isMinimizedversion()) || (com.adventnet.appmanager.util.Constants.getUserType().equals("F")) || (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                           {
/*      */ 
/*      */ 
/* 3030 */                                             out.write("\n\n\t <optgroup label=\"");
/* 3031 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3032 */                                             out.write("\">\n                                        \n                                        ");
/*      */                                             
/* 3034 */                                             OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3035 */                                             _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 3036 */                                             _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3038 */                                             _jspx_th_html_005foption_005f0.setValue("AIX");
/* 3039 */                                             int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 3040 */                                             if (_jspx_eval_html_005foption_005f0 != 0) {
/* 3041 */                                               if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3042 */                                                 out = _jspx_page_context.pushBody();
/* 3043 */                                                 _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 3044 */                                                 _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3047 */                                                 out.print(FormatUtil.getString("AIX"));
/* 3048 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 3049 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3052 */                                               if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3053 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3056 */                                             if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3057 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                             }
/*      */                                             
/* 3060 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 3061 */                                             out.write("\n                                        ");
/*      */                                             
/* 3063 */                                             OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3064 */                                             _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3065 */                                             _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3067 */                                             _jspx_th_html_005foption_005f1.setValue("AS400");
/* 3068 */                                             int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3069 */                                             if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3070 */                                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3071 */                                                 out = _jspx_page_context.pushBody();
/* 3072 */                                                 _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3073 */                                                 _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3076 */                                                 out.print(FormatUtil.getString("AS400/iSeries"));
/* 3077 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3078 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3081 */                                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3082 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3085 */                                             if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3086 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                             }
/*      */                                             
/* 3089 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 3090 */                                             out.write("\n                                        ");
/*      */                                             
/* 3092 */                                             OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3093 */                                             _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 3094 */                                             _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3096 */                                             _jspx_th_html_005foption_005f2.setValue("FreeBSD");
/* 3097 */                                             int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 3098 */                                             if (_jspx_eval_html_005foption_005f2 != 0) {
/* 3099 */                                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3100 */                                                 out = _jspx_page_context.pushBody();
/* 3101 */                                                 _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 3102 */                                                 _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3105 */                                                 out.print(FormatUtil.getString("FreeBSD/OpenBSD"));
/* 3106 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 3107 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3110 */                                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3111 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3114 */                                             if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 3115 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                             }
/*      */                                             
/* 3118 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 3119 */                                             out.write("\n                                        ");
/*      */                                             
/* 3121 */                                             OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3122 */                                             _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 3123 */                                             _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3125 */                                             _jspx_th_html_005foption_005f3.setValue("HP-UX");
/* 3126 */                                             int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 3127 */                                             if (_jspx_eval_html_005foption_005f3 != 0) {
/* 3128 */                                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3129 */                                                 out = _jspx_page_context.pushBody();
/* 3130 */                                                 _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 3131 */                                                 _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3134 */                                                 out.print(FormatUtil.getString("HP-UX  / Tru64"));
/* 3135 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 3136 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3139 */                                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3140 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3143 */                                             if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 3144 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                             }
/*      */                                             
/* 3147 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 3148 */                                             out.write("\n                                        ");
/*      */                                             
/* 3150 */                                             OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3151 */                                             _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 3152 */                                             _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3154 */                                             _jspx_th_html_005foption_005f4.setValue("Linux");
/* 3155 */                                             int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 3156 */                                             if (_jspx_eval_html_005foption_005f4 != 0) {
/* 3157 */                                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3158 */                                                 out = _jspx_page_context.pushBody();
/* 3159 */                                                 _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 3160 */                                                 _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3163 */                                                 out.print(FormatUtil.getString("Linux"));
/* 3164 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 3165 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3168 */                                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3169 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3172 */                                             if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 3173 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                             }
/*      */                                             
/* 3176 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 3177 */                                             out.write("\n                                        ");
/*      */                                             
/* 3179 */                                             OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3180 */                                             _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 3181 */                                             _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3183 */                                             _jspx_th_html_005foption_005f5.setValue("Mac OS");
/* 3184 */                                             int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 3185 */                                             if (_jspx_eval_html_005foption_005f5 != 0) {
/* 3186 */                                               if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3187 */                                                 out = _jspx_page_context.pushBody();
/* 3188 */                                                 _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 3189 */                                                 _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3192 */                                                 out.print(FormatUtil.getString("Mac OS"));
/* 3193 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 3194 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3197 */                                               if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3198 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3201 */                                             if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 3202 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                             }
/*      */                                             
/* 3205 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 3206 */                                             out.write("\n                                        ");
/*      */                                             
/* 3208 */                                             OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3209 */                                             _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 3210 */                                             _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3212 */                                             _jspx_th_html_005foption_005f6.setValue("Novell");
/* 3213 */                                             int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 3214 */                                             if (_jspx_eval_html_005foption_005f6 != 0) {
/* 3215 */                                               if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3216 */                                                 out = _jspx_page_context.pushBody();
/* 3217 */                                                 _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 3218 */                                                 _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3221 */                                                 out.print(FormatUtil.getString("Novell"));
/* 3222 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 3223 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3226 */                                               if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3227 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3230 */                                             if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 3231 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                             }
/*      */                                             
/* 3234 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 3235 */                                             out.write("\n                                        ");
/*      */                                             
/* 3237 */                                             OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3238 */                                             _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 3239 */                                             _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3241 */                                             _jspx_th_html_005foption_005f7.setValue("Sun Solaris");
/* 3242 */                                             int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 3243 */                                             if (_jspx_eval_html_005foption_005f7 != 0) {
/* 3244 */                                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3245 */                                                 out = _jspx_page_context.pushBody();
/* 3246 */                                                 _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 3247 */                                                 _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3250 */                                                 out.print(FormatUtil.getString("Sun Solaris"));
/* 3251 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 3252 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3255 */                                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3256 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3259 */                                             if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 3260 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                             }
/*      */                                             
/* 3263 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 3264 */                                             out.write("\n                                        ");
/*      */                                             
/* 3266 */                                             OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3267 */                                             _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 3268 */                                             _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3270 */                                             _jspx_th_html_005foption_005f8.setValue("Windows");
/* 3271 */                                             int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 3272 */                                             if (_jspx_eval_html_005foption_005f8 != 0) {
/* 3273 */                                               if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3274 */                                                 out = _jspx_page_context.pushBody();
/* 3275 */                                                 _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 3276 */                                                 _jspx_th_html_005foption_005f8.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3279 */                                                 out.print(FormatUtil.getString("Windows"));
/* 3280 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 3281 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3284 */                                               if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3285 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3288 */                                             if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 3289 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                             }
/*      */                                             
/* 3292 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/* 3293 */                                             out.write("\n                                        ");
/*      */                                             
/* 3295 */                                             OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3296 */                                             _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 3297 */                                             _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3299 */                                             _jspx_th_html_005foption_005f9.setValue("Windows Cluster");
/* 3300 */                                             int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 3301 */                                             if (_jspx_eval_html_005foption_005f9 != 0) {
/* 3302 */                                               if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3303 */                                                 out = _jspx_page_context.pushBody();
/* 3304 */                                                 _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 3305 */                                                 _jspx_th_html_005foption_005f9.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3308 */                                                 out.print(FormatUtil.getString("Windows Cluster"));
/* 3309 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 3310 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3313 */                                               if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3314 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3317 */                                             if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 3318 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                             }
/*      */                                             
/* 3321 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/* 3322 */                                             out.write("\n                                        \n\n  ");
/*      */                                             
/* 3324 */                                             ArrayList rows1 = mo1.getRows("select RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH from AM_ManagedResourceType,AM_MONITOR_TYPES where TYPEID=RESOURCETYPEID and RESOURCEGROUP='SYS' and AMCREATED='NO' ORDER BY UPPER(DISPLAYNAME)");
/* 3325 */                                             if ((rows1 != null) && (rows1.size() > 0))
/*      */                                             {
/* 3327 */                                               for (int i = 0; i < rows1.size(); i++)
/*      */                                               {
/* 3329 */                                                 ArrayList row = (ArrayList)rows1.get(i);
/* 3330 */                                                 String res = (String)row.get(0);
/* 3331 */                                                 String dname = (String)row.get(1);
/* 3332 */                                                 String values = props.getProperty(res);
/* 3333 */                                                 if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                 {
/*      */ 
/* 3336 */                                                   out.write("\n\t\t\t\t");
/*      */                                                   
/* 3338 */                                                   OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3339 */                                                   _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 3340 */                                                   _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3342 */                                                   _jspx_th_html_005foption_005f10.setValue(values);
/* 3343 */                                                   int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 3344 */                                                   if (_jspx_eval_html_005foption_005f10 != 0) {
/* 3345 */                                                     if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3346 */                                                       out = _jspx_page_context.pushBody();
/* 3347 */                                                       _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/* 3348 */                                                       _jspx_th_html_005foption_005f10.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3351 */                                                       out.write(32);
/* 3352 */                                                       out.print(FormatUtil.getString(dname));
/* 3353 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/* 3354 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3357 */                                                     if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3358 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3361 */                                                   if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 3362 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                                   }
/*      */                                                   
/* 3365 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/* 3366 */                                                   out.write("\n\t\t\t");
/*      */                                                 }
/*      */                                               }
/*      */                                             }
/*      */                                             
/*      */ 
/* 3372 */                                             String[] categoryLink = { "APP", "ERP", "TM", "SYS", "DBS", "SER", "URL", "MS", "MOM", "CAM", "VIR", "CLD" };
/*      */                                             
/* 3374 */                                             String[] categoryTitle = { "am.webclient.monitorgroupsecond.category.appserver", "am.webclient.monitorgroupsecond.category.erp", "am.webclient.monitorgroupsecond.category.transaction", "am.webclient.monitorgroupsecond.category.servers", "am.webclient.monitorgroupsecond.category.dbserver", "am.webclient.monitorgroupsecond.category.services", "am.webclient.monitorgroupsecond.category.webservices.title", "am.webclient.monitorgroupsecond.category.mailserver", "Middleware/Portal", "am.webclient.monitorgroupsecond.category.custom", "am.webclient.monitorgroupsecond.category.virtualserver", "am.webclient.monitorgroupsecond.category.cloudapps" };
/*      */                                             
/*      */ 
/* 3377 */                                             if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD"))
/*      */                                             {
/*      */ 
/* 3380 */                                               categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 3381 */                                               categoryTitle = com.adventnet.appmanager.util.Constants.categoryTitle;
/*      */                                             }
/* 3383 */                                             for (int c = 0; c < categoryLink.length; c++)
/*      */                                             {
/* 3385 */                                               ArrayList unSupportedTypes = com.adventnet.appmanager.util.Constants.getUnSupportedAsList();
/* 3386 */                                               if ((!categoryLink[c].equals("SYS")) && (!categoryLink[c].equals("NWD")) && (!categoryLink[c].equals("SAN")) && (!categoryLink[c].equals("EMO")) && (!categoryLink[c].equals("SCR")) && ((!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")) || (!unSupportedTypes.contains(categoryLink[c]))))
/*      */                                               {
/*      */ 
/*      */ 
/* 3390 */                                                 StringBuffer queryBuf = new StringBuffer();
/* 3391 */                                                 queryBuf.append("SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='");
/* 3392 */                                                 queryBuf.append(categoryLink[c] + "'");
/* 3393 */                                                 queryBuf.append(" ");
/* 3394 */                                                 queryBuf.append("and RESOURCETYPE in");
/* 3395 */                                                 queryBuf.append(" ");
/* 3396 */                                                 queryBuf.append(com.adventnet.appmanager.util.Constants.resourceTypes);
/* 3397 */                                                 if (categoryLink[c].equals("APP"))
/*      */                                                 {
/* 3399 */                                                   queryBuf.append(" ");
/* 3400 */                                                   queryBuf.append("and DISPLAYNAME NOT IN ('WebLogic Clusters')");
/* 3401 */                                                   queryBuf.append(" ");
/*      */                                                 }
/* 3403 */                                                 else if (categoryLink[c].equals("SER"))
/*      */                                                 {
/* 3405 */                                                   queryBuf.append(" ");
/* 3406 */                                                   queryBuf.append("and RESOURCETYPE<>'RMI'");
/* 3407 */                                                   queryBuf.append(" ");
/*      */                                                 }
/* 3409 */                                                 else if (categoryLink[c].equals("CAM"))
/*      */                                                 {
/* 3411 */                                                   queryBuf.append(" ");
/* 3412 */                                                   queryBuf.append("and RESOURCETYPE != 'directory'");
/* 3413 */                                                   queryBuf.append(" ");
/*      */                                                 }
/* 3415 */                                                 queryBuf.append(" ");
/* 3416 */                                                 queryBuf.append("ORDER BY UPPER(DISPLAYNAME)");
/* 3417 */                                                 ArrayList rows = mo1.getRows(queryBuf.toString());
/* 3418 */                                                 if ((rows != null) && (rows.size() != 0))
/*      */                                                 {
/*      */ 
/*      */ 
/*      */ 
/* 3423 */                                                   out.write("\n</optgroup>\t\t\t\t<optgroup label=\"");
/* 3424 */                                                   out.print(FormatUtil.getString(categoryTitle[c]));
/* 3425 */                                                   out.write(34);
/* 3426 */                                                   out.write(62);
/* 3427 */                                                   out.write(10);
/*      */                                                   
/*      */ 
/* 3430 */                                                   for (int i = 0; i < rows.size(); i++)
/*      */                                                   {
/* 3432 */                                                     ArrayList row = (ArrayList)rows.get(i);
/* 3433 */                                                     String res = (String)row.get(0);
/* 3434 */                                                     if (res.equals("file"))
/*      */                                                     {
/* 3436 */                                                       res = "File / Directory Monitor";
/*      */                                                     }
/* 3438 */                                                     String dname = (String)row.get(1);
/* 3439 */                                                     String values = props.getProperty(res);
/* 3440 */                                                     if ((!EnterpriseUtil.isCloudEdition()) || (!unSupportedTypes.contains(values)))
/*      */                                                     {
/*      */ 
/* 3443 */                                                       if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                       {
/*      */ 
/* 3446 */                                                         out.write("\n\t\t\t\t \t");
/*      */                                                         
/* 3448 */                                                         OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3449 */                                                         _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 3450 */                                                         _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                         
/* 3452 */                                                         _jspx_th_html_005foption_005f11.setValue(values);
/* 3453 */                                                         int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 3454 */                                                         if (_jspx_eval_html_005foption_005f11 != 0) {
/* 3455 */                                                           if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3456 */                                                             out = _jspx_page_context.pushBody();
/* 3457 */                                                             _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/* 3458 */                                                             _jspx_th_html_005foption_005f11.doInitBody();
/*      */                                                           }
/*      */                                                           for (;;) {
/* 3461 */                                                             out.write(32);
/* 3462 */                                                             out.print(FormatUtil.getString(dname));
/* 3463 */                                                             int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/* 3464 */                                                             if (evalDoAfterBody != 2)
/*      */                                                               break;
/*      */                                                           }
/* 3467 */                                                           if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3468 */                                                             out = _jspx_page_context.popBody();
/*      */                                                           }
/*      */                                                         }
/* 3471 */                                                         if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 3472 */                                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                                                         }
/*      */                                                         
/* 3475 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11);
/* 3476 */                                                         out.write("\n\t\t\t\t");
/*      */                                                       }
/*      */                                                     }
/*      */                                                   }
/*      */                                                   
/* 3481 */                                                   if (categoryLink[c].equals("VIR"))
/*      */                                                   {
/*      */ 
/* 3484 */                                                     out.write("\n\t\t\t\t\t");
/*      */                                                     
/* 3486 */                                                     OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3487 */                                                     _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 3488 */                                                     _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                     
/* 3490 */                                                     _jspx_th_html_005foption_005f12.setValue("VCenter");
/* 3491 */                                                     int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 3492 */                                                     if (_jspx_eval_html_005foption_005f12 != 0) {
/* 3493 */                                                       if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3494 */                                                         out = _jspx_page_context.pushBody();
/* 3495 */                                                         _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/* 3496 */                                                         _jspx_th_html_005foption_005f12.doInitBody();
/*      */                                                       }
/*      */                                                       for (;;) {
/* 3499 */                                                         out.write(32);
/* 3500 */                                                         out.print(FormatUtil.getString("am.webclient.addmonitor.vcenter.name"));
/* 3501 */                                                         int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/* 3502 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/* 3505 */                                                       if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3506 */                                                         out = _jspx_page_context.popBody();
/*      */                                                       }
/*      */                                                     }
/* 3509 */                                                     if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 3510 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                                                     }
/*      */                                                     
/* 3513 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/* 3514 */                                                     out.write("\n\t\t\t\t");
/*      */                                                   }
/*      */                                                 }
/*      */                                               } }
/* 3518 */                                             String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/* 3519 */                                             if (!usertype.equals("F"))
/*      */                                             {
/* 3521 */                                               if (((!EnterpriseUtil.isIt360MSPEdition()) || (!DBUtil.isSharedProbe())) && (!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                               {
/* 3523 */                                                 out.write("\n    </optgroup> <optgroup label=\"");
/* 3524 */                                                 out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3525 */                                                 out.write("\">\n     ");
/*      */                                                 
/* 3527 */                                                 OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3528 */                                                 _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/* 3529 */                                                 _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                 
/* 3531 */                                                 _jspx_th_html_005foption_005f13.setValue("All:1008");
/* 3532 */                                                 int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/* 3533 */                                                 if (_jspx_eval_html_005foption_005f13 != 0) {
/* 3534 */                                                   if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3535 */                                                     out = _jspx_page_context.pushBody();
/* 3536 */                                                     _jspx_th_html_005foption_005f13.setBodyContent((BodyContent)out);
/* 3537 */                                                     _jspx_th_html_005foption_005f13.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 3540 */                                                     out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3541 */                                                     out.write(32);
/* 3542 */                                                     int evalDoAfterBody = _jspx_th_html_005foption_005f13.doAfterBody();
/* 3543 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 3546 */                                                   if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3547 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 3550 */                                                 if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/* 3551 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                                                 }
/*      */                                                 
/* 3554 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/* 3555 */                                                 out.write("\n\n     ");
/*      */                                               }
/*      */                                               
/*      */                                             }
/*      */                                             
/*      */                                           }
/* 3561 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("LAMP"))
/*      */                                           {
/*      */ 
/* 3564 */                                             out.write("\n\t\t\t </optgroup>  <optgroup label=\"");
/* 3565 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3566 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3568 */                                             OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3569 */                                             _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/* 3570 */                                             _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3572 */                                             _jspx_th_html_005foption_005f14.setValue("SYSTEM:9999");
/* 3573 */                                             int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/* 3574 */                                             if (_jspx_eval_html_005foption_005f14 != 0) {
/* 3575 */                                               if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3576 */                                                 out = _jspx_page_context.pushBody();
/* 3577 */                                                 _jspx_th_html_005foption_005f14.setBodyContent((BodyContent)out);
/* 3578 */                                                 _jspx_th_html_005foption_005f14.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3581 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 3582 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f14.doAfterBody();
/* 3583 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3586 */                                               if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3587 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3590 */                                             if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/* 3591 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14); return;
/*      */                                             }
/*      */                                             
/* 3594 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/* 3595 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3596 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 3597 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3599 */                                             OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3600 */                                             _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/* 3601 */                                             _jspx_th_html_005foption_005f15.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3603 */                                             _jspx_th_html_005foption_005f15.setValue("MYSQLDB:3306");
/* 3604 */                                             int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/* 3605 */                                             if (_jspx_eval_html_005foption_005f15 != 0) {
/* 3606 */                                               if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3607 */                                                 out = _jspx_page_context.pushBody();
/* 3608 */                                                 _jspx_th_html_005foption_005f15.setBodyContent((BodyContent)out);
/* 3609 */                                                 _jspx_th_html_005foption_005f15.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3612 */                                                 out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 3613 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f15.doAfterBody();
/* 3614 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3617 */                                               if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3618 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3621 */                                             if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/* 3622 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15); return;
/*      */                                             }
/*      */                                             
/* 3625 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/* 3626 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3627 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 3628 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3630 */                                             OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3631 */                                             _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/* 3632 */                                             _jspx_th_html_005foption_005f16.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3634 */                                             _jspx_th_html_005foption_005f16.setValue("JMX1.2-MX4J-RMI:1099");
/* 3635 */                                             int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/* 3636 */                                             if (_jspx_eval_html_005foption_005f16 != 0) {
/* 3637 */                                               if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3638 */                                                 out = _jspx_page_context.pushBody();
/* 3639 */                                                 _jspx_th_html_005foption_005f16.setBodyContent((BodyContent)out);
/* 3640 */                                                 _jspx_th_html_005foption_005f16.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3643 */                                                 out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 3644 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f16.doAfterBody();
/* 3645 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3648 */                                               if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3649 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3652 */                                             if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/* 3653 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16); return;
/*      */                                             }
/*      */                                             
/* 3656 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/* 3657 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3659 */                                             OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3660 */                                             _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/* 3661 */                                             _jspx_th_html_005foption_005f17.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3663 */                                             _jspx_th_html_005foption_005f17.setValue("SERVICE:9090");
/* 3664 */                                             int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/* 3665 */                                             if (_jspx_eval_html_005foption_005f17 != 0) {
/* 3666 */                                               if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3667 */                                                 out = _jspx_page_context.pushBody();
/* 3668 */                                                 _jspx_th_html_005foption_005f17.setBodyContent((BodyContent)out);
/* 3669 */                                                 _jspx_th_html_005foption_005f17.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3672 */                                                 out.write(32);
/* 3673 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 3674 */                                                 out.write(32);
/* 3675 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f17.doAfterBody();
/* 3676 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3679 */                                               if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3680 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3683 */                                             if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/* 3684 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17); return;
/*      */                                             }
/*      */                                             
/* 3687 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/* 3688 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3690 */                                             OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3691 */                                             _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/* 3692 */                                             _jspx_th_html_005foption_005f18.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3694 */                                             _jspx_th_html_005foption_005f18.setValue("RMI:1099");
/* 3695 */                                             int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/* 3696 */                                             if (_jspx_eval_html_005foption_005f18 != 0) {
/* 3697 */                                               if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3698 */                                                 out = _jspx_page_context.pushBody();
/* 3699 */                                                 _jspx_th_html_005foption_005f18.setBodyContent((BodyContent)out);
/* 3700 */                                                 _jspx_th_html_005foption_005f18.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3703 */                                                 out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 3704 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f18.doAfterBody();
/* 3705 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3708 */                                               if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3709 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3712 */                                             if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/* 3713 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18); return;
/*      */                                             }
/*      */                                             
/* 3716 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/* 3717 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3719 */                                             OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3720 */                                             _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/* 3721 */                                             _jspx_th_html_005foption_005f19.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3723 */                                             _jspx_th_html_005foption_005f19.setValue("SNMP:161");
/* 3724 */                                             int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/* 3725 */                                             if (_jspx_eval_html_005foption_005f19 != 0) {
/* 3726 */                                               if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3727 */                                                 out = _jspx_page_context.pushBody();
/* 3728 */                                                 _jspx_th_html_005foption_005f19.setBodyContent((BodyContent)out);
/* 3729 */                                                 _jspx_th_html_005foption_005f19.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3732 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 3733 */                                                 out.write(" (V1 or V2c)");
/* 3734 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f19.doAfterBody();
/* 3735 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3738 */                                               if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3739 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3742 */                                             if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/* 3743 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19); return;
/*      */                                             }
/*      */                                             
/* 3746 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/* 3747 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3749 */                                             OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3750 */                                             _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/* 3751 */                                             _jspx_th_html_005foption_005f20.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3753 */                                             _jspx_th_html_005foption_005f20.setValue("TELNET:23");
/* 3754 */                                             int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/* 3755 */                                             if (_jspx_eval_html_005foption_005f20 != 0) {
/* 3756 */                                               if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3757 */                                                 out = _jspx_page_context.pushBody();
/* 3758 */                                                 _jspx_th_html_005foption_005f20.setBodyContent((BodyContent)out);
/* 3759 */                                                 _jspx_th_html_005foption_005f20.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3762 */                                                 out.print(FormatUtil.getString("Telnet"));
/* 3763 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f20.doAfterBody();
/* 3764 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3767 */                                               if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3768 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3771 */                                             if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/* 3772 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20); return;
/*      */                                             }
/*      */                                             
/* 3775 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/* 3776 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3777 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 3778 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3780 */                                             OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3781 */                                             _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/* 3782 */                                             _jspx_th_html_005foption_005f21.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3784 */                                             _jspx_th_html_005foption_005f21.setValue("APACHE:80");
/* 3785 */                                             int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/* 3786 */                                             if (_jspx_eval_html_005foption_005f21 != 0) {
/* 3787 */                                               if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3788 */                                                 out = _jspx_page_context.pushBody();
/* 3789 */                                                 _jspx_th_html_005foption_005f21.setBodyContent((BodyContent)out);
/* 3790 */                                                 _jspx_th_html_005foption_005f21.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3793 */                                                 out.write(32);
/* 3794 */                                                 out.print(FormatUtil.getString("Apache Server"));
/* 3795 */                                                 out.write(32);
/* 3796 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f21.doAfterBody();
/* 3797 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3800 */                                               if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3801 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3804 */                                             if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/* 3805 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21); return;
/*      */                                             }
/*      */                                             
/* 3808 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/* 3809 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3811 */                                             OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3812 */                                             _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/* 3813 */                                             _jspx_th_html_005foption_005f22.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3815 */                                             _jspx_th_html_005foption_005f22.setValue("PHP:80");
/* 3816 */                                             int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/* 3817 */                                             if (_jspx_eval_html_005foption_005f22 != 0) {
/* 3818 */                                               if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3819 */                                                 out = _jspx_page_context.pushBody();
/* 3820 */                                                 _jspx_th_html_005foption_005f22.setBodyContent((BodyContent)out);
/* 3821 */                                                 _jspx_th_html_005foption_005f22.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3824 */                                                 out.print(FormatUtil.getString("PHP"));
/* 3825 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f22.doAfterBody();
/* 3826 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3829 */                                               if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3830 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3833 */                                             if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/* 3834 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22); return;
/*      */                                             }
/*      */                                             
/* 3837 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/* 3838 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3840 */                                             OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3841 */                                             _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/* 3842 */                                             _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3844 */                                             _jspx_th_html_005foption_005f23.setValue("UrlMonitor");
/* 3845 */                                             int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/* 3846 */                                             if (_jspx_eval_html_005foption_005f23 != 0) {
/* 3847 */                                               if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3848 */                                                 out = _jspx_page_context.pushBody();
/* 3849 */                                                 _jspx_th_html_005foption_005f23.setBodyContent((BodyContent)out);
/* 3850 */                                                 _jspx_th_html_005foption_005f23.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3853 */                                                 out.print(FormatUtil.getString("HTTP-URLs"));
/* 3854 */                                                 out.write(32);
/* 3855 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f23.doAfterBody();
/* 3856 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3859 */                                               if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3860 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3863 */                                             if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/* 3864 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23); return;
/*      */                                             }
/*      */                                             
/* 3867 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23);
/* 3868 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3870 */                                             OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3871 */                                             _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/* 3872 */                                             _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3874 */                                             _jspx_th_html_005foption_005f24.setValue("UrlSeq");
/* 3875 */                                             int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/* 3876 */                                             if (_jspx_eval_html_005foption_005f24 != 0) {
/* 3877 */                                               if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3878 */                                                 out = _jspx_page_context.pushBody();
/* 3879 */                                                 _jspx_th_html_005foption_005f24.setBodyContent((BodyContent)out);
/* 3880 */                                                 _jspx_th_html_005foption_005f24.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3883 */                                                 out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 3884 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f24.doAfterBody();
/* 3885 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3888 */                                               if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3889 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3892 */                                             if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/* 3893 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24); return;
/*      */                                             }
/*      */                                             
/* 3896 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24);
/* 3897 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3899 */                                             OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3900 */                                             _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/* 3901 */                                             _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3903 */                                             _jspx_th_html_005foption_005f25.setValue("WEB:80");
/* 3904 */                                             int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/* 3905 */                                             if (_jspx_eval_html_005foption_005f25 != 0) {
/* 3906 */                                               if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3907 */                                                 out = _jspx_page_context.pushBody();
/* 3908 */                                                 _jspx_th_html_005foption_005f25.setBodyContent((BodyContent)out);
/* 3909 */                                                 _jspx_th_html_005foption_005f25.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3912 */                                                 out.write(32);
/* 3913 */                                                 out.print(FormatUtil.getString("Web Server"));
/* 3914 */                                                 out.write(32);
/* 3915 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f25.doAfterBody();
/* 3916 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3919 */                                               if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3920 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3923 */                                             if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/* 3924 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25); return;
/*      */                                             }
/*      */                                             
/* 3927 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25);
/* 3928 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3930 */                                             OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3931 */                                             _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/* 3932 */                                             _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3934 */                                             _jspx_th_html_005foption_005f26.setValue("Web Service");
/* 3935 */                                             int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/* 3936 */                                             if (_jspx_eval_html_005foption_005f26 != 0) {
/* 3937 */                                               if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3938 */                                                 out = _jspx_page_context.pushBody();
/* 3939 */                                                 _jspx_th_html_005foption_005f26.setBodyContent((BodyContent)out);
/* 3940 */                                                 _jspx_th_html_005foption_005f26.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3943 */                                                 out.write(32);
/* 3944 */                                                 out.print(FormatUtil.getString("Web Service"));
/* 3945 */                                                 out.write(32);
/* 3946 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f26.doAfterBody();
/* 3947 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3950 */                                               if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3951 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3954 */                                             if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/* 3955 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26); return;
/*      */                                             }
/*      */                                             
/* 3958 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26);
/* 3959 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3960 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 3961 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3963 */                                             OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3964 */                                             _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/* 3965 */                                             _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3967 */                                             _jspx_th_html_005foption_005f27.setValue("MAIL:25");
/* 3968 */                                             int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/* 3969 */                                             if (_jspx_eval_html_005foption_005f27 != 0) {
/* 3970 */                                               if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3971 */                                                 out = _jspx_page_context.pushBody();
/* 3972 */                                                 _jspx_th_html_005foption_005f27.setBodyContent((BodyContent)out);
/* 3973 */                                                 _jspx_th_html_005foption_005f27.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3976 */                                                 out.print(FormatUtil.getString("Mail Server"));
/* 3977 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f27.doAfterBody();
/* 3978 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3981 */                                               if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3982 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3985 */                                             if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/* 3986 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27); return;
/*      */                                             }
/*      */                                             
/* 3989 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27);
/* 3990 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3991 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 3992 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3994 */                                             OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3995 */                                             _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/* 3996 */                                             _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3998 */                                             _jspx_th_html_005foption_005f28.setValue("Custom-Application");
/* 3999 */                                             int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/* 4000 */                                             if (_jspx_eval_html_005foption_005f28 != 0) {
/* 4001 */                                               if (_jspx_eval_html_005foption_005f28 != 1) {
/* 4002 */                                                 out = _jspx_page_context.pushBody();
/* 4003 */                                                 _jspx_th_html_005foption_005f28.setBodyContent((BodyContent)out);
/* 4004 */                                                 _jspx_th_html_005foption_005f28.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4007 */                                                 out.write(32);
/* 4008 */                                                 out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4009 */                                                 out.write(32);
/* 4010 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f28.doAfterBody();
/* 4011 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4014 */                                               if (_jspx_eval_html_005foption_005f28 != 1) {
/* 4015 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4018 */                                             if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/* 4019 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28); return;
/*      */                                             }
/*      */                                             
/* 4022 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28);
/* 4023 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 4025 */                                             OptionTag _jspx_th_html_005foption_005f29 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4026 */                                             _jspx_th_html_005foption_005f29.setPageContext(_jspx_page_context);
/* 4027 */                                             _jspx_th_html_005foption_005f29.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4029 */                                             _jspx_th_html_005foption_005f29.setValue("Script Monitor");
/* 4030 */                                             int _jspx_eval_html_005foption_005f29 = _jspx_th_html_005foption_005f29.doStartTag();
/* 4031 */                                             if (_jspx_eval_html_005foption_005f29 != 0) {
/* 4032 */                                               if (_jspx_eval_html_005foption_005f29 != 1) {
/* 4033 */                                                 out = _jspx_page_context.pushBody();
/* 4034 */                                                 _jspx_th_html_005foption_005f29.setBodyContent((BodyContent)out);
/* 4035 */                                                 _jspx_th_html_005foption_005f29.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4038 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 4039 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f29.doAfterBody();
/* 4040 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4043 */                                               if (_jspx_eval_html_005foption_005f29 != 1) {
/* 4044 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4047 */                                             if (_jspx_th_html_005foption_005f29.doEndTag() == 5) {
/* 4048 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29); return;
/*      */                                             }
/*      */                                             
/* 4051 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29);
/* 4052 */                                             out.write("\n\n    ");
/*      */ 
/*      */                                           }
/* 4055 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("J2EE"))
/*      */                                           {
/*      */ 
/* 4058 */                                             out.write("\n        ");
/*      */                                             
/* 4060 */                                             OptionTag _jspx_th_html_005foption_005f30 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4061 */                                             _jspx_th_html_005foption_005f30.setPageContext(_jspx_page_context);
/* 4062 */                                             _jspx_th_html_005foption_005f30.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4064 */                                             _jspx_th_html_005foption_005f30.setValue("SYSTEM:9999");
/* 4065 */                                             int _jspx_eval_html_005foption_005f30 = _jspx_th_html_005foption_005f30.doStartTag();
/* 4066 */                                             if (_jspx_eval_html_005foption_005f30 != 0) {
/* 4067 */                                               if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4068 */                                                 out = _jspx_page_context.pushBody();
/* 4069 */                                                 _jspx_th_html_005foption_005f30.setBodyContent((BodyContent)out);
/* 4070 */                                                 _jspx_th_html_005foption_005f30.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4073 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4074 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f30.doAfterBody();
/* 4075 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4078 */                                               if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4079 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4082 */                                             if (_jspx_th_html_005foption_005f30.doEndTag() == 5) {
/* 4083 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30); return;
/*      */                                             }
/*      */                                             
/* 4086 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30);
/* 4087 */                                             out.write("\n       ");
/*      */                                             
/* 4089 */                                             OptionTag _jspx_th_html_005foption_005f31 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4090 */                                             _jspx_th_html_005foption_005f31.setPageContext(_jspx_page_context);
/* 4091 */                                             _jspx_th_html_005foption_005f31.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4093 */                                             _jspx_th_html_005foption_005f31.setValue("JBoss:8080");
/* 4094 */                                             int _jspx_eval_html_005foption_005f31 = _jspx_th_html_005foption_005f31.doStartTag();
/* 4095 */                                             if (_jspx_eval_html_005foption_005f31 != 0) {
/* 4096 */                                               if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4097 */                                                 out = _jspx_page_context.pushBody();
/* 4098 */                                                 _jspx_th_html_005foption_005f31.setBodyContent((BodyContent)out);
/* 4099 */                                                 _jspx_th_html_005foption_005f31.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4102 */                                                 out.write(32);
/* 4103 */                                                 out.print(FormatUtil.getString("JBoss Server"));
/* 4104 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f31.doAfterBody();
/* 4105 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4108 */                                               if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4109 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4112 */                                             if (_jspx_th_html_005foption_005f31.doEndTag() == 5) {
/* 4113 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31); return;
/*      */                                             }
/*      */                                             
/* 4116 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31);
/* 4117 */                                             out.write("\n      ");
/*      */                                             
/* 4119 */                                             OptionTag _jspx_th_html_005foption_005f32 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4120 */                                             _jspx_th_html_005foption_005f32.setPageContext(_jspx_page_context);
/* 4121 */                                             _jspx_th_html_005foption_005f32.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4123 */                                             _jspx_th_html_005foption_005f32.setValue("Tomcat:8080");
/* 4124 */                                             int _jspx_eval_html_005foption_005f32 = _jspx_th_html_005foption_005f32.doStartTag();
/* 4125 */                                             if (_jspx_eval_html_005foption_005f32 != 0) {
/* 4126 */                                               if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4127 */                                                 out = _jspx_page_context.pushBody();
/* 4128 */                                                 _jspx_th_html_005foption_005f32.setBodyContent((BodyContent)out);
/* 4129 */                                                 _jspx_th_html_005foption_005f32.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4132 */                                                 out.print(FormatUtil.getString("Tomcat Server"));
/* 4133 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f32.doAfterBody();
/* 4134 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4137 */                                               if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4138 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4141 */                                             if (_jspx_th_html_005foption_005f32.doEndTag() == 5) {
/* 4142 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32); return;
/*      */                                             }
/*      */                                             
/* 4145 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32);
/* 4146 */                                             out.write("\n       ");
/*      */                                             
/* 4148 */                                             OptionTag _jspx_th_html_005foption_005f33 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4149 */                                             _jspx_th_html_005foption_005f33.setPageContext(_jspx_page_context);
/* 4150 */                                             _jspx_th_html_005foption_005f33.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4152 */                                             _jspx_th_html_005foption_005f33.setValue("WEBLOGIC:7001");
/* 4153 */                                             int _jspx_eval_html_005foption_005f33 = _jspx_th_html_005foption_005f33.doStartTag();
/* 4154 */                                             if (_jspx_eval_html_005foption_005f33 != 0) {
/* 4155 */                                               if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4156 */                                                 out = _jspx_page_context.pushBody();
/* 4157 */                                                 _jspx_th_html_005foption_005f33.setBodyContent((BodyContent)out);
/* 4158 */                                                 _jspx_th_html_005foption_005f33.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4161 */                                                 out.write(32);
/* 4162 */                                                 out.print(FormatUtil.getString("WebLogic Server"));
/* 4163 */                                                 out.write(32);
/* 4164 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f33.doAfterBody();
/* 4165 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4168 */                                               if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4169 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4172 */                                             if (_jspx_th_html_005foption_005f33.doEndTag() == 5) {
/* 4173 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33); return;
/*      */                                             }
/*      */                                             
/* 4176 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33);
/* 4177 */                                             out.write("\n      ");
/*      */                                             
/* 4179 */                                             OptionTag _jspx_th_html_005foption_005f34 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4180 */                                             _jspx_th_html_005foption_005f34.setPageContext(_jspx_page_context);
/* 4181 */                                             _jspx_th_html_005foption_005f34.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4183 */                                             _jspx_th_html_005foption_005f34.setValue("WEBSPHERE:9080");
/* 4184 */                                             int _jspx_eval_html_005foption_005f34 = _jspx_th_html_005foption_005f34.doStartTag();
/* 4185 */                                             if (_jspx_eval_html_005foption_005f34 != 0) {
/* 4186 */                                               if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4187 */                                                 out = _jspx_page_context.pushBody();
/* 4188 */                                                 _jspx_th_html_005foption_005f34.setBodyContent((BodyContent)out);
/* 4189 */                                                 _jspx_th_html_005foption_005f34.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4192 */                                                 out.write(32);
/* 4193 */                                                 out.print(FormatUtil.getString("WebSphere Server"));
/* 4194 */                                                 out.write(32);
/* 4195 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f34.doAfterBody();
/* 4196 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4199 */                                               if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4200 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4203 */                                             if (_jspx_th_html_005foption_005f34.doEndTag() == 5) {
/* 4204 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34); return;
/*      */                                             }
/*      */                                             
/* 4207 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34);
/* 4208 */                                             out.write("\n      ");
/*      */                                             
/* 4210 */                                             OptionTag _jspx_th_html_005foption_005f35 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4211 */                                             _jspx_th_html_005foption_005f35.setPageContext(_jspx_page_context);
/* 4212 */                                             _jspx_th_html_005foption_005f35.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4214 */                                             _jspx_th_html_005foption_005f35.setValue("WTA:55555");
/* 4215 */                                             int _jspx_eval_html_005foption_005f35 = _jspx_th_html_005foption_005f35.doStartTag();
/* 4216 */                                             if (_jspx_eval_html_005foption_005f35 != 0) {
/* 4217 */                                               if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4218 */                                                 out = _jspx_page_context.pushBody();
/* 4219 */                                                 _jspx_th_html_005foption_005f35.setBodyContent((BodyContent)out);
/* 4220 */                                                 _jspx_th_html_005foption_005f35.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4223 */                                                 out.print(FormatUtil.getString("Web Transactions"));
/* 4224 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f35.doAfterBody();
/* 4225 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4228 */                                               if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4229 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4232 */                                             if (_jspx_th_html_005foption_005f35.doEndTag() == 5) {
/* 4233 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35); return;
/*      */                                             }
/*      */                                             
/* 4236 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35);
/* 4237 */                                             out.write("\n      ");
/*      */                                             
/* 4239 */                                             OptionTag _jspx_th_html_005foption_005f36 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4240 */                                             _jspx_th_html_005foption_005f36.setPageContext(_jspx_page_context);
/* 4241 */                                             _jspx_th_html_005foption_005f36.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4243 */                                             _jspx_th_html_005foption_005f36.setValue("MAIL:25");
/* 4244 */                                             int _jspx_eval_html_005foption_005f36 = _jspx_th_html_005foption_005f36.doStartTag();
/* 4245 */                                             if (_jspx_eval_html_005foption_005f36 != 0) {
/* 4246 */                                               if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4247 */                                                 out = _jspx_page_context.pushBody();
/* 4248 */                                                 _jspx_th_html_005foption_005f36.setBodyContent((BodyContent)out);
/* 4249 */                                                 _jspx_th_html_005foption_005f36.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4252 */                                                 out.print(FormatUtil.getString("Mail Server"));
/* 4253 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f36.doAfterBody();
/* 4254 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4257 */                                               if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4258 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4261 */                                             if (_jspx_th_html_005foption_005f36.doEndTag() == 5) {
/* 4262 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36); return;
/*      */                                             }
/*      */                                             
/* 4265 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36);
/* 4266 */                                             out.write("\n      ");
/*      */                                             
/* 4268 */                                             OptionTag _jspx_th_html_005foption_005f37 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4269 */                                             _jspx_th_html_005foption_005f37.setPageContext(_jspx_page_context);
/* 4270 */                                             _jspx_th_html_005foption_005f37.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4272 */                                             _jspx_th_html_005foption_005f37.setValue("JMX1.2-MX4J-RMI:1099");
/* 4273 */                                             int _jspx_eval_html_005foption_005f37 = _jspx_th_html_005foption_005f37.doStartTag();
/* 4274 */                                             if (_jspx_eval_html_005foption_005f37 != 0) {
/* 4275 */                                               if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4276 */                                                 out = _jspx_page_context.pushBody();
/* 4277 */                                                 _jspx_th_html_005foption_005f37.setBodyContent((BodyContent)out);
/* 4278 */                                                 _jspx_th_html_005foption_005f37.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4281 */                                                 out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 4282 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f37.doAfterBody();
/* 4283 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4286 */                                               if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4287 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4290 */                                             if (_jspx_th_html_005foption_005f37.doEndTag() == 5) {
/* 4291 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37); return;
/*      */                                             }
/*      */                                             
/* 4294 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37);
/* 4295 */                                             out.write("\n      ");
/*      */                                             
/* 4297 */                                             OptionTag _jspx_th_html_005foption_005f38 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4298 */                                             _jspx_th_html_005foption_005f38.setPageContext(_jspx_page_context);
/* 4299 */                                             _jspx_th_html_005foption_005f38.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4301 */                                             _jspx_th_html_005foption_005f38.setValue("SERVICE:9090");
/* 4302 */                                             int _jspx_eval_html_005foption_005f38 = _jspx_th_html_005foption_005f38.doStartTag();
/* 4303 */                                             if (_jspx_eval_html_005foption_005f38 != 0) {
/* 4304 */                                               if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4305 */                                                 out = _jspx_page_context.pushBody();
/* 4306 */                                                 _jspx_th_html_005foption_005f38.setBodyContent((BodyContent)out);
/* 4307 */                                                 _jspx_th_html_005foption_005f38.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4310 */                                                 out.write(32);
/* 4311 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 4312 */                                                 out.write(32);
/* 4313 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f38.doAfterBody();
/* 4314 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4317 */                                               if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4318 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4321 */                                             if (_jspx_th_html_005foption_005f38.doEndTag() == 5) {
/* 4322 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38); return;
/*      */                                             }
/*      */                                             
/* 4325 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38);
/* 4326 */                                             out.write("\n      ");
/*      */                                             
/* 4328 */                                             OptionTag _jspx_th_html_005foption_005f39 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4329 */                                             _jspx_th_html_005foption_005f39.setPageContext(_jspx_page_context);
/* 4330 */                                             _jspx_th_html_005foption_005f39.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4332 */                                             _jspx_th_html_005foption_005f39.setValue("RMI:1099");
/* 4333 */                                             int _jspx_eval_html_005foption_005f39 = _jspx_th_html_005foption_005f39.doStartTag();
/* 4334 */                                             if (_jspx_eval_html_005foption_005f39 != 0) {
/* 4335 */                                               if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4336 */                                                 out = _jspx_page_context.pushBody();
/* 4337 */                                                 _jspx_th_html_005foption_005f39.setBodyContent((BodyContent)out);
/* 4338 */                                                 _jspx_th_html_005foption_005f39.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4341 */                                                 out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 4342 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f39.doAfterBody();
/* 4343 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4346 */                                               if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4347 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4350 */                                             if (_jspx_th_html_005foption_005f39.doEndTag() == 5) {
/* 4351 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39); return;
/*      */                                             }
/*      */                                             
/* 4354 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39);
/* 4355 */                                             out.write("\n      ");
/*      */                                             
/* 4357 */                                             OptionTag _jspx_th_html_005foption_005f40 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4358 */                                             _jspx_th_html_005foption_005f40.setPageContext(_jspx_page_context);
/* 4359 */                                             _jspx_th_html_005foption_005f40.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4361 */                                             _jspx_th_html_005foption_005f40.setValue("SNMP:161");
/* 4362 */                                             int _jspx_eval_html_005foption_005f40 = _jspx_th_html_005foption_005f40.doStartTag();
/* 4363 */                                             if (_jspx_eval_html_005foption_005f40 != 0) {
/* 4364 */                                               if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4365 */                                                 out = _jspx_page_context.pushBody();
/* 4366 */                                                 _jspx_th_html_005foption_005f40.setBodyContent((BodyContent)out);
/* 4367 */                                                 _jspx_th_html_005foption_005f40.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4370 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 4371 */                                                 out.write(" (V1 or V2c)");
/* 4372 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f40.doAfterBody();
/* 4373 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4376 */                                               if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4377 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4380 */                                             if (_jspx_th_html_005foption_005f40.doEndTag() == 5) {
/* 4381 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40); return;
/*      */                                             }
/*      */                                             
/* 4384 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40);
/* 4385 */                                             out.write("\n      ");
/*      */                                             
/* 4387 */                                             OptionTag _jspx_th_html_005foption_005f41 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4388 */                                             _jspx_th_html_005foption_005f41.setPageContext(_jspx_page_context);
/* 4389 */                                             _jspx_th_html_005foption_005f41.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4391 */                                             _jspx_th_html_005foption_005f41.setValue("Custom-Application");
/* 4392 */                                             int _jspx_eval_html_005foption_005f41 = _jspx_th_html_005foption_005f41.doStartTag();
/* 4393 */                                             if (_jspx_eval_html_005foption_005f41 != 0) {
/* 4394 */                                               if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4395 */                                                 out = _jspx_page_context.pushBody();
/* 4396 */                                                 _jspx_th_html_005foption_005f41.setBodyContent((BodyContent)out);
/* 4397 */                                                 _jspx_th_html_005foption_005f41.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4400 */                                                 out.write(32);
/* 4401 */                                                 out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4402 */                                                 out.write(32);
/* 4403 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f41.doAfterBody();
/* 4404 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4407 */                                               if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4408 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4411 */                                             if (_jspx_th_html_005foption_005f41.doEndTag() == 5) {
/* 4412 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41); return;
/*      */                                             }
/*      */                                             
/* 4415 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41);
/* 4416 */                                             out.write("\n      ");
/*      */                                             
/* 4418 */                                             OptionTag _jspx_th_html_005foption_005f42 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4419 */                                             _jspx_th_html_005foption_005f42.setPageContext(_jspx_page_context);
/* 4420 */                                             _jspx_th_html_005foption_005f42.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4422 */                                             _jspx_th_html_005foption_005f42.setValue("Script Monitor");
/* 4423 */                                             int _jspx_eval_html_005foption_005f42 = _jspx_th_html_005foption_005f42.doStartTag();
/* 4424 */                                             if (_jspx_eval_html_005foption_005f42 != 0) {
/* 4425 */                                               if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4426 */                                                 out = _jspx_page_context.pushBody();
/* 4427 */                                                 _jspx_th_html_005foption_005f42.setBodyContent((BodyContent)out);
/* 4428 */                                                 _jspx_th_html_005foption_005f42.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4431 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 4432 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f42.doAfterBody();
/* 4433 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4436 */                                               if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4437 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4440 */                                             if (_jspx_th_html_005foption_005f42.doEndTag() == 5) {
/* 4441 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42); return;
/*      */                                             }
/*      */                                             
/* 4444 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42);
/* 4445 */                                             out.write("\n       ");
/*      */ 
/*      */                                           }
/* 4448 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("WINDOWS"))
/*      */                                           {
/*      */ 
/* 4451 */                                             out.write("\n        ");
/*      */                                             
/* 4453 */                                             OptionTag _jspx_th_html_005foption_005f43 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4454 */                                             _jspx_th_html_005foption_005f43.setPageContext(_jspx_page_context);
/* 4455 */                                             _jspx_th_html_005foption_005f43.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4457 */                                             _jspx_th_html_005foption_005f43.setValue("SYSTEM:9999");
/* 4458 */                                             int _jspx_eval_html_005foption_005f43 = _jspx_th_html_005foption_005f43.doStartTag();
/* 4459 */                                             if (_jspx_eval_html_005foption_005f43 != 0) {
/* 4460 */                                               if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4461 */                                                 out = _jspx_page_context.pushBody();
/* 4462 */                                                 _jspx_th_html_005foption_005f43.setBodyContent((BodyContent)out);
/* 4463 */                                                 _jspx_th_html_005foption_005f43.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4466 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4467 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f43.doAfterBody();
/* 4468 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4471 */                                               if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4472 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4475 */                                             if (_jspx_th_html_005foption_005f43.doEndTag() == 5) {
/* 4476 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43); return;
/*      */                                             }
/*      */                                             
/* 4479 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43);
/* 4480 */                                             out.write("\n       ");
/*      */                                             
/* 4482 */                                             OptionTag _jspx_th_html_005foption_005f44 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4483 */                                             _jspx_th_html_005foption_005f44.setPageContext(_jspx_page_context);
/* 4484 */                                             _jspx_th_html_005foption_005f44.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4486 */                                             _jspx_th_html_005foption_005f44.setValue(".Net:9080");
/* 4487 */                                             int _jspx_eval_html_005foption_005f44 = _jspx_th_html_005foption_005f44.doStartTag();
/* 4488 */                                             if (_jspx_eval_html_005foption_005f44 != 0) {
/* 4489 */                                               if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4490 */                                                 out = _jspx_page_context.pushBody();
/* 4491 */                                                 _jspx_th_html_005foption_005f44.setBodyContent((BodyContent)out);
/* 4492 */                                                 _jspx_th_html_005foption_005f44.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4495 */                                                 out.print(FormatUtil.getString("Microsoft .NET"));
/* 4496 */                                                 out.write(32);
/* 4497 */                                                 out.write(32);
/* 4498 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f44.doAfterBody();
/* 4499 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4502 */                                               if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4503 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4506 */                                             if (_jspx_th_html_005foption_005f44.doEndTag() == 5) {
/* 4507 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44); return;
/*      */                                             }
/*      */                                             
/* 4510 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44);
/* 4511 */                                             out.write("\n      ");
/*      */                                             
/* 4513 */                                             OptionTag _jspx_th_html_005foption_005f45 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4514 */                                             _jspx_th_html_005foption_005f45.setPageContext(_jspx_page_context);
/* 4515 */                                             _jspx_th_html_005foption_005f45.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4517 */                                             _jspx_th_html_005foption_005f45.setValue("MSSQLDB:1433");
/* 4518 */                                             int _jspx_eval_html_005foption_005f45 = _jspx_th_html_005foption_005f45.doStartTag();
/* 4519 */                                             if (_jspx_eval_html_005foption_005f45 != 0) {
/* 4520 */                                               if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4521 */                                                 out = _jspx_page_context.pushBody();
/* 4522 */                                                 _jspx_th_html_005foption_005f45.setBodyContent((BodyContent)out);
/* 4523 */                                                 _jspx_th_html_005foption_005f45.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4526 */                                                 out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4527 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f45.doAfterBody();
/* 4528 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4531 */                                               if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4532 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4535 */                                             if (_jspx_th_html_005foption_005f45.doEndTag() == 5) {
/* 4536 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45); return;
/*      */                                             }
/*      */                                             
/* 4539 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45);
/* 4540 */                                             out.write("\n      ");
/*      */                                             
/* 4542 */                                             OptionTag _jspx_th_html_005foption_005f46 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4543 */                                             _jspx_th_html_005foption_005f46.setPageContext(_jspx_page_context);
/* 4544 */                                             _jspx_th_html_005foption_005f46.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4546 */                                             _jspx_th_html_005foption_005f46.setValue("Exchange:25");
/* 4547 */                                             int _jspx_eval_html_005foption_005f46 = _jspx_th_html_005foption_005f46.doStartTag();
/* 4548 */                                             if (_jspx_eval_html_005foption_005f46 != 0) {
/* 4549 */                                               if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4550 */                                                 out = _jspx_page_context.pushBody();
/* 4551 */                                                 _jspx_th_html_005foption_005f46.setBodyContent((BodyContent)out);
/* 4552 */                                                 _jspx_th_html_005foption_005f46.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4555 */                                                 out.print(FormatUtil.getString("Exchange Server"));
/* 4556 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f46.doAfterBody();
/* 4557 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4560 */                                               if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4561 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4564 */                                             if (_jspx_th_html_005foption_005f46.doEndTag() == 5) {
/* 4565 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46); return;
/*      */                                             }
/*      */                                             
/* 4568 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46);
/* 4569 */                                             out.write("\n\t  ");
/*      */                                             
/* 4571 */                                             OptionTag _jspx_th_html_005foption_005f47 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4572 */                                             _jspx_th_html_005foption_005f47.setPageContext(_jspx_page_context);
/* 4573 */                                             _jspx_th_html_005foption_005f47.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4575 */                                             _jspx_th_html_005foption_005f47.setValue("IIS:80");
/* 4576 */                                             int _jspx_eval_html_005foption_005f47 = _jspx_th_html_005foption_005f47.doStartTag();
/* 4577 */                                             if (_jspx_eval_html_005foption_005f47 != 0) {
/* 4578 */                                               if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4579 */                                                 out = _jspx_page_context.pushBody();
/* 4580 */                                                 _jspx_th_html_005foption_005f47.setBodyContent((BodyContent)out);
/* 4581 */                                                 _jspx_th_html_005foption_005f47.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4584 */                                                 out.write(32);
/* 4585 */                                                 out.print(FormatUtil.getString("IIS Server"));
/* 4586 */                                                 out.write(32);
/* 4587 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f47.doAfterBody();
/* 4588 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4591 */                                               if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4592 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4595 */                                             if (_jspx_th_html_005foption_005f47.doEndTag() == 5) {
/* 4596 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47); return;
/*      */                                             }
/*      */                                             
/* 4599 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47);
/* 4600 */                                             out.write("\n      ");
/*      */                                             
/* 4602 */                                             OptionTag _jspx_th_html_005foption_005f48 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4603 */                                             _jspx_th_html_005foption_005f48.setPageContext(_jspx_page_context);
/* 4604 */                                             _jspx_th_html_005foption_005f48.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4606 */                                             _jspx_th_html_005foption_005f48.setValue("SERVICE:9090");
/* 4607 */                                             int _jspx_eval_html_005foption_005f48 = _jspx_th_html_005foption_005f48.doStartTag();
/* 4608 */                                             if (_jspx_eval_html_005foption_005f48 != 0) {
/* 4609 */                                               if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4610 */                                                 out = _jspx_page_context.pushBody();
/* 4611 */                                                 _jspx_th_html_005foption_005f48.setBodyContent((BodyContent)out);
/* 4612 */                                                 _jspx_th_html_005foption_005f48.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4615 */                                                 out.write(32);
/* 4616 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 4617 */                                                 out.write(32);
/* 4618 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f48.doAfterBody();
/* 4619 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4622 */                                               if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4623 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4626 */                                             if (_jspx_th_html_005foption_005f48.doEndTag() == 5) {
/* 4627 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48); return;
/*      */                                             }
/*      */                                             
/* 4630 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48);
/* 4631 */                                             out.write("\n\t  ");
/*      */                                             
/* 4633 */                                             OptionTag _jspx_th_html_005foption_005f49 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4634 */                                             _jspx_th_html_005foption_005f49.setPageContext(_jspx_page_context);
/* 4635 */                                             _jspx_th_html_005foption_005f49.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4637 */                                             _jspx_th_html_005foption_005f49.setValue("SNMP:161");
/* 4638 */                                             int _jspx_eval_html_005foption_005f49 = _jspx_th_html_005foption_005f49.doStartTag();
/* 4639 */                                             if (_jspx_eval_html_005foption_005f49 != 0) {
/* 4640 */                                               if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4641 */                                                 out = _jspx_page_context.pushBody();
/* 4642 */                                                 _jspx_th_html_005foption_005f49.setBodyContent((BodyContent)out);
/* 4643 */                                                 _jspx_th_html_005foption_005f49.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4646 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 4647 */                                                 out.write(" (V1 or V2c)");
/* 4648 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f49.doAfterBody();
/* 4649 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4652 */                                               if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4653 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4656 */                                             if (_jspx_th_html_005foption_005f49.doEndTag() == 5) {
/* 4657 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49); return;
/*      */                                             }
/*      */                                             
/* 4660 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49);
/* 4661 */                                             out.write("\n      ");
/*      */                                             
/* 4663 */                                             OptionTag _jspx_th_html_005foption_005f50 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4664 */                                             _jspx_th_html_005foption_005f50.setPageContext(_jspx_page_context);
/* 4665 */                                             _jspx_th_html_005foption_005f50.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4667 */                                             _jspx_th_html_005foption_005f50.setValue("Script Monitor");
/* 4668 */                                             int _jspx_eval_html_005foption_005f50 = _jspx_th_html_005foption_005f50.doStartTag();
/* 4669 */                                             if (_jspx_eval_html_005foption_005f50 != 0) {
/* 4670 */                                               if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4671 */                                                 out = _jspx_page_context.pushBody();
/* 4672 */                                                 _jspx_th_html_005foption_005f50.setBodyContent((BodyContent)out);
/* 4673 */                                                 _jspx_th_html_005foption_005f50.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4676 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 4677 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f50.doAfterBody();
/* 4678 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4681 */                                               if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4682 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4685 */                                             if (_jspx_th_html_005foption_005f50.doEndTag() == 5) {
/* 4686 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50); return;
/*      */                                             }
/*      */                                             
/* 4689 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50);
/* 4690 */                                             out.write(10);
/*      */ 
/*      */                                           }
/* 4693 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("DATABASE"))
/*      */                                           {
/*      */ 
/* 4696 */                                             out.write("\n\t\t</optgroup>\t<optgroup label=\"");
/* 4697 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 4698 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 4700 */                                             OptionTag _jspx_th_html_005foption_005f51 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4701 */                                             _jspx_th_html_005foption_005f51.setPageContext(_jspx_page_context);
/* 4702 */                                             _jspx_th_html_005foption_005f51.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4704 */                                             _jspx_th_html_005foption_005f51.setValue("SYSTEM:9999");
/* 4705 */                                             int _jspx_eval_html_005foption_005f51 = _jspx_th_html_005foption_005f51.doStartTag();
/* 4706 */                                             if (_jspx_eval_html_005foption_005f51 != 0) {
/* 4707 */                                               if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4708 */                                                 out = _jspx_page_context.pushBody();
/* 4709 */                                                 _jspx_th_html_005foption_005f51.setBodyContent((BodyContent)out);
/* 4710 */                                                 _jspx_th_html_005foption_005f51.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4713 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4714 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f51.doAfterBody();
/* 4715 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4718 */                                               if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4719 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4722 */                                             if (_jspx_th_html_005foption_005f51.doEndTag() == 5) {
/* 4723 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51); return;
/*      */                                             }
/*      */                                             
/* 4726 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51);
/* 4727 */                                             out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4728 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 4729 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 4731 */                                             OptionTag _jspx_th_html_005foption_005f52 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4732 */                                             _jspx_th_html_005foption_005f52.setPageContext(_jspx_page_context);
/* 4733 */                                             _jspx_th_html_005foption_005f52.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4735 */                                             _jspx_th_html_005foption_005f52.setValue("DB2:50000");
/* 4736 */                                             int _jspx_eval_html_005foption_005f52 = _jspx_th_html_005foption_005f52.doStartTag();
/* 4737 */                                             if (_jspx_eval_html_005foption_005f52 != 0) {
/* 4738 */                                               if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4739 */                                                 out = _jspx_page_context.pushBody();
/* 4740 */                                                 _jspx_th_html_005foption_005f52.setBodyContent((BodyContent)out);
/* 4741 */                                                 _jspx_th_html_005foption_005f52.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4744 */                                                 out.print(FormatUtil.getString("am.webclient.db2.servertype"));
/* 4745 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f52.doAfterBody();
/* 4746 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4749 */                                               if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4750 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4753 */                                             if (_jspx_th_html_005foption_005f52.doEndTag() == 5) {
/* 4754 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52); return;
/*      */                                             }
/*      */                                             
/* 4757 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52);
/* 4758 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4760 */                                             OptionTag _jspx_th_html_005foption_005f53 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4761 */                                             _jspx_th_html_005foption_005f53.setPageContext(_jspx_page_context);
/* 4762 */                                             _jspx_th_html_005foption_005f53.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4764 */                                             _jspx_th_html_005foption_005f53.setValue("MSSQLDB:1433");
/* 4765 */                                             int _jspx_eval_html_005foption_005f53 = _jspx_th_html_005foption_005f53.doStartTag();
/* 4766 */                                             if (_jspx_eval_html_005foption_005f53 != 0) {
/* 4767 */                                               if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4768 */                                                 out = _jspx_page_context.pushBody();
/* 4769 */                                                 _jspx_th_html_005foption_005f53.setBodyContent((BodyContent)out);
/* 4770 */                                                 _jspx_th_html_005foption_005f53.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4773 */                                                 out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4774 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f53.doAfterBody();
/* 4775 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4778 */                                               if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4779 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4782 */                                             if (_jspx_th_html_005foption_005f53.doEndTag() == 5) {
/* 4783 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53); return;
/*      */                                             }
/*      */                                             
/* 4786 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53);
/* 4787 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4789 */                                             OptionTag _jspx_th_html_005foption_005f54 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4790 */                                             _jspx_th_html_005foption_005f54.setPageContext(_jspx_page_context);
/* 4791 */                                             _jspx_th_html_005foption_005f54.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4793 */                                             _jspx_th_html_005foption_005f54.setValue("MYSQLDB:3306");
/* 4794 */                                             int _jspx_eval_html_005foption_005f54 = _jspx_th_html_005foption_005f54.doStartTag();
/* 4795 */                                             if (_jspx_eval_html_005foption_005f54 != 0) {
/* 4796 */                                               if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4797 */                                                 out = _jspx_page_context.pushBody();
/* 4798 */                                                 _jspx_th_html_005foption_005f54.setBodyContent((BodyContent)out);
/* 4799 */                                                 _jspx_th_html_005foption_005f54.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4802 */                                                 out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 4803 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f54.doAfterBody();
/* 4804 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4807 */                                               if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4808 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4811 */                                             if (_jspx_th_html_005foption_005f54.doEndTag() == 5) {
/* 4812 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54); return;
/*      */                                             }
/*      */                                             
/* 4815 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54);
/* 4816 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4818 */                                             OptionTag _jspx_th_html_005foption_005f55 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4819 */                                             _jspx_th_html_005foption_005f55.setPageContext(_jspx_page_context);
/* 4820 */                                             _jspx_th_html_005foption_005f55.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4822 */                                             _jspx_th_html_005foption_005f55.setValue("ORACLEDB:1521");
/* 4823 */                                             int _jspx_eval_html_005foption_005f55 = _jspx_th_html_005foption_005f55.doStartTag();
/* 4824 */                                             if (_jspx_eval_html_005foption_005f55 != 0) {
/* 4825 */                                               if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4826 */                                                 out = _jspx_page_context.pushBody();
/* 4827 */                                                 _jspx_th_html_005foption_005f55.setBodyContent((BodyContent)out);
/* 4828 */                                                 _jspx_th_html_005foption_005f55.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4831 */                                                 out.print(FormatUtil.getString("am.webclient.oracle.servertype"));
/* 4832 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f55.doAfterBody();
/* 4833 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4836 */                                               if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4837 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4840 */                                             if (_jspx_th_html_005foption_005f55.doEndTag() == 5) {
/* 4841 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55); return;
/*      */                                             }
/*      */                                             
/* 4844 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55);
/* 4845 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4847 */                                             OptionTag _jspx_th_html_005foption_005f56 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4848 */                                             _jspx_th_html_005foption_005f56.setPageContext(_jspx_page_context);
/* 4849 */                                             _jspx_th_html_005foption_005f56.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4851 */                                             _jspx_th_html_005foption_005f56.setValue("SYBASEDB:5000");
/* 4852 */                                             int _jspx_eval_html_005foption_005f56 = _jspx_th_html_005foption_005f56.doStartTag();
/* 4853 */                                             if (_jspx_eval_html_005foption_005f56 != 0) {
/* 4854 */                                               if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4855 */                                                 out = _jspx_page_context.pushBody();
/* 4856 */                                                 _jspx_th_html_005foption_005f56.setBodyContent((BodyContent)out);
/* 4857 */                                                 _jspx_th_html_005foption_005f56.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4860 */                                                 out.print(FormatUtil.getString("Sybase"));
/* 4861 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f56.doAfterBody();
/* 4862 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4865 */                                               if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4866 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4869 */                                             if (_jspx_th_html_005foption_005f56.doEndTag() == 5) {
/* 4870 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56); return;
/*      */                                             }
/*      */                                             
/* 4873 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56);
/* 4874 */                                             out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4875 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 4876 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 4878 */                                             OptionTag _jspx_th_html_005foption_005f57 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4879 */                                             _jspx_th_html_005foption_005f57.setPageContext(_jspx_page_context);
/* 4880 */                                             _jspx_th_html_005foption_005f57.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4882 */                                             _jspx_th_html_005foption_005f57.setValue("SERVICE:9090");
/* 4883 */                                             int _jspx_eval_html_005foption_005f57 = _jspx_th_html_005foption_005f57.doStartTag();
/* 4884 */                                             if (_jspx_eval_html_005foption_005f57 != 0) {
/* 4885 */                                               if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4886 */                                                 out = _jspx_page_context.pushBody();
/* 4887 */                                                 _jspx_th_html_005foption_005f57.setBodyContent((BodyContent)out);
/* 4888 */                                                 _jspx_th_html_005foption_005f57.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4891 */                                                 out.write(32);
/* 4892 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 4893 */                                                 out.write(32);
/* 4894 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f57.doAfterBody();
/* 4895 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4898 */                                               if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4899 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4902 */                                             if (_jspx_th_html_005foption_005f57.doEndTag() == 5) {
/* 4903 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57); return;
/*      */                                             }
/*      */                                             
/* 4906 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57);
/* 4907 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4909 */                                             OptionTag _jspx_th_html_005foption_005f58 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4910 */                                             _jspx_th_html_005foption_005f58.setPageContext(_jspx_page_context);
/* 4911 */                                             _jspx_th_html_005foption_005f58.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4913 */                                             _jspx_th_html_005foption_005f58.setValue("SNMP:161");
/* 4914 */                                             int _jspx_eval_html_005foption_005f58 = _jspx_th_html_005foption_005f58.doStartTag();
/* 4915 */                                             if (_jspx_eval_html_005foption_005f58 != 0) {
/* 4916 */                                               if (_jspx_eval_html_005foption_005f58 != 1) {
/* 4917 */                                                 out = _jspx_page_context.pushBody();
/* 4918 */                                                 _jspx_th_html_005foption_005f58.setBodyContent((BodyContent)out);
/* 4919 */                                                 _jspx_th_html_005foption_005f58.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4922 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 4923 */                                                 out.write(" (V1 or V2c)");
/* 4924 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f58.doAfterBody();
/* 4925 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4928 */                                               if (_jspx_eval_html_005foption_005f58 != 1) {
/* 4929 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4932 */                                             if (_jspx_th_html_005foption_005f58.doEndTag() == 5) {
/* 4933 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58); return;
/*      */                                             }
/*      */                                             
/* 4936 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58);
/* 4937 */                                             out.write("</optgroup>");
/* 4938 */                                             out.write("\n\t\t\t<optgroup label=\"");
/* 4939 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 4940 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 4942 */                                             OptionTag _jspx_th_html_005foption_005f59 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4943 */                                             _jspx_th_html_005foption_005f59.setPageContext(_jspx_page_context);
/* 4944 */                                             _jspx_th_html_005foption_005f59.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4946 */                                             _jspx_th_html_005foption_005f59.setValue("UrlMonitor");
/* 4947 */                                             int _jspx_eval_html_005foption_005f59 = _jspx_th_html_005foption_005f59.doStartTag();
/* 4948 */                                             if (_jspx_eval_html_005foption_005f59 != 0) {
/* 4949 */                                               if (_jspx_eval_html_005foption_005f59 != 1) {
/* 4950 */                                                 out = _jspx_page_context.pushBody();
/* 4951 */                                                 _jspx_th_html_005foption_005f59.setBodyContent((BodyContent)out);
/* 4952 */                                                 _jspx_th_html_005foption_005f59.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4955 */                                                 out.print(FormatUtil.getString("HTTP-URLs"));
/* 4956 */                                                 out.write(32);
/* 4957 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f59.doAfterBody();
/* 4958 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4961 */                                               if (_jspx_eval_html_005foption_005f59 != 1) {
/* 4962 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4965 */                                             if (_jspx_th_html_005foption_005f59.doEndTag() == 5) {
/* 4966 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59); return;
/*      */                                             }
/*      */                                             
/* 4969 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59);
/* 4970 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4972 */                                             OptionTag _jspx_th_html_005foption_005f60 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4973 */                                             _jspx_th_html_005foption_005f60.setPageContext(_jspx_page_context);
/* 4974 */                                             _jspx_th_html_005foption_005f60.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4976 */                                             _jspx_th_html_005foption_005f60.setValue("UrlSeq");
/* 4977 */                                             int _jspx_eval_html_005foption_005f60 = _jspx_th_html_005foption_005f60.doStartTag();
/* 4978 */                                             if (_jspx_eval_html_005foption_005f60 != 0) {
/* 4979 */                                               if (_jspx_eval_html_005foption_005f60 != 1) {
/* 4980 */                                                 out = _jspx_page_context.pushBody();
/* 4981 */                                                 _jspx_th_html_005foption_005f60.setBodyContent((BodyContent)out);
/* 4982 */                                                 _jspx_th_html_005foption_005f60.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4985 */                                                 out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 4986 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f60.doAfterBody();
/* 4987 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4990 */                                               if (_jspx_eval_html_005foption_005f60 != 1) {
/* 4991 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4994 */                                             if (_jspx_th_html_005foption_005f60.doEndTag() == 5) {
/* 4995 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60); return;
/*      */                                             }
/*      */                                             
/* 4998 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60);
/* 4999 */                                             out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 5000 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 5001 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 5003 */                                             OptionTag _jspx_th_html_005foption_005f61 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5004 */                                             _jspx_th_html_005foption_005f61.setPageContext(_jspx_page_context);
/* 5005 */                                             _jspx_th_html_005foption_005f61.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 5007 */                                             _jspx_th_html_005foption_005f61.setValue("Script Monitor");
/* 5008 */                                             int _jspx_eval_html_005foption_005f61 = _jspx_th_html_005foption_005f61.doStartTag();
/* 5009 */                                             if (_jspx_eval_html_005foption_005f61 != 0) {
/* 5010 */                                               if (_jspx_eval_html_005foption_005f61 != 1) {
/* 5011 */                                                 out = _jspx_page_context.pushBody();
/* 5012 */                                                 _jspx_th_html_005foption_005f61.setBodyContent((BodyContent)out);
/* 5013 */                                                 _jspx_th_html_005foption_005f61.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5016 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 5017 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f61.doAfterBody();
/* 5018 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5021 */                                               if (_jspx_eval_html_005foption_005f61 != 1) {
/* 5022 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5025 */                                             if (_jspx_th_html_005foption_005f61.doEndTag() == 5) {
/* 5026 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61); return;
/*      */                                             }
/*      */                                             
/* 5029 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61);
/* 5030 */                                             out.write(10);
/* 5031 */                                             out.write(10);
/*      */                                           }
/*      */                                           
/*      */ 
/* 5035 */                                           out.write("\n\n\n\n      ");
/* 5036 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 5037 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5040 */                                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 5041 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5044 */                                       if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 5045 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                       }
/*      */                                       
/* 5048 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 5049 */                                       out.write("\n                      \n      ");
/* 5050 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 5051 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5055 */                                   if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 5056 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                   }
/*      */                                   
/* 5059 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5060 */                                   out.write("\n      ");
/* 5061 */                                   if (_jspx_meth_c_005fif_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5063 */                                   out.write("\n      </td>\n      \n      ");
/* 5064 */                                   if (request.getParameter("type") != null) {
/* 5065 */                                     isConfMonitor = confMonConfig.isConfMonitor(request.getParameter("type"));
/* 5066 */                                     String restype = request.getParameter("type");
/* 5067 */                                     if (restype.indexOf(":") != -1) {
/* 5068 */                                       restype = restype.substring(0, restype.indexOf(":"));
/*      */                                     }
/* 5070 */                                     if (((isConfMonitor) && (!restype.equals("QueryMonitor"))) || ((!restype.equals("JMX1.2-MX4J-RMI")) && (!restype.equals("Generic WMI")) && (!restype.equals("Script Monitor")) && (!restype.equals("Custom-Application")) && (!restype.equals("File System Monitor")) && (!restype.equals("QueryMonitor")) && (!restype.equals("SNMP")) && (!restype.equals("TELNET")) && (!restype.equals("Exchange")) && (!restype.equals("WTA")) && (!restype.equals("WEB")) && (!restype.equals("UrlSeq")) && (!restype.equals("PHP")) && (!restype.equals("IIS")) && (!restype.equals("APACHE")) && (!restype.equals("MAIL")) && (!restype.equals("All")) && (restype.indexOf("SAP") == -1))) {
/* 5071 */                                       out.write("\n      \t<td class=\"tableheading-monitor-config itadmin-hide\" align=\"right\">\n      \n      \t\t<div id=\"Conf-bulk-configuration\"> \n\t\t\t    \t\t<a href=\"javascript:void(0)\"  onclick=\"window.open('/BulkAddMonitors.do?method=showBulkImportForm&type=");
/* 5072 */                                       out.print(restype);
/* 5073 */                                       out.write("','windowName','toolbar=no,status=no,menubar=no,width=1000,height=500,scrollbars=yes')\" class=\"staticlinks\" >");
/* 5074 */                                       out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 5075 */                                       out.write("</a>\n\t    \t</div><img src=\"/images/script-icon.gif\">\n   \t   </td>\n      \n      \t");
/*      */                                     }
/*      */                                   }
/* 5078 */                                   out.write("     \n      \n  </tr>\n</table>\n <script src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script><script type=\"text/javascript\"> $(\".formtext\").chosen();  </script>\n");
/* 5079 */                                   out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"3\" cellspacing=\"1\" class=\"lrborder\">\n  <tr>\n    <td height=\"20\" width=\"25%\" class=\"bodytext\">\n\t <a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 5080 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.textmessage.hostexample"));
/* 5081 */                                   out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 5082 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.hostname"));
/* 5083 */                                   out.write("</a>\n<span class=\"mandatory\">*</span>\n    </TD>\n      <td height=\"20\" colspan=\"2\">\n        <table width=\"75%\" cellspacing=\"0\"><tr>\n        <td>");
/* 5084 */                                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5086 */                                   out.write("\n    </td>\n         <td align=\"right\" width=\"20%\">\n        <div id=\"ping-bulk-configuration\" class=\"");
/* 5087 */                                   out.print(hideStyle);
/* 5088 */                                   out.write("\"> <img src=\"/images/script-icon.gif\" style=\"position:relative; top:3px;\">\t\t\t <a href=\"javascript:void(0)\" onclick=\"window.open('/BulkAddMonitors.do?method=showBulkImportForm&type=Ping Monitor','windowName','toolbar=no,status=no,menubar=no,width=1000,height=500,scrollbars=yes')\" class=\"staticlinks\" >");
/* 5089 */                                   out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 5090 */                                   out.write("</a></div>\n    </td>\n</tr></table></td>\n  </tr>\n<TR>                                                                                                                  <TD height=\"28\" width=\"25%\" class=\"bodytext\" >");
/* 5091 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.polling"));
/* 5092 */                                   out.write("<span class=\"mandatory\"> *</span></TD>                                                                            <TD height=\"28\" colspan=\"2\" > ");
/* 5093 */                                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5095 */                                   out.write("<span class=\"bodytext\">&nbsp;");
/* 5096 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.minutes"));
/* 5097 */                                   out.write("</span></TD>   </TR>\n\n                         <!--tr >\n                        <td height=\"28\" width=\"25%\" colspan=\"2\" class=\"bodytext\">");
/* 5098 */                                   out.print(FormatUtil.getString("Advanced Option"));
/* 5099 */                                   out.write("</td>\n                        <td height=\"28\" width=\"75%\" style=\"padding-left:0px\">");
/* 5100 */                                   if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5102 */                                   out.write("</td>\n                        </tr>\n<tr>\n<td> </td>\n<td colspan=\"2\" align=\"right\">\n<div id=\"advOption\" style=\"display:none;\">\n<table width=\"99%\" class=\"grayfullborder\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n                      <TR  class=\"yellowgrayborder\">\n                        <TD height=\"28\" width=\"15%\" class=\"bodytext\">");
/* 5103 */                                   out.print(FormatUtil.getString("No. of retries"));
/* 5104 */                                   out.write("</TD>\n                        <TD height=\"28\" width=\"75%\"  colspan=\"2\">");
/* 5105 */                                   if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5107 */                                   out.write("</td>\n                        </TR-->\n                        <TR >\n                        <TD width=\"15%\" height=\"28\" class=\"bodytext\">");
/* 5108 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.timeout"));
/* 5109 */                                   out.write("</TD>\n                        <TD width=\"75%\" height=\"28\"  colspan=\"2\"> ");
/* 5110 */                                   if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5112 */                                   out.write("<span class=\"bodytext\">&nbsp;");
/* 5113 */                                   out.print(FormatUtil.getString("am.webclient.newscript.insecondshelp.seconds"));
/* 5114 */                                   out.write("</span>\n\n<!--/td>\n</tr>\n</table>\n</div>\n</td>\n</tr-->\n\n</table>\n");
/*      */                                   
/* 5116 */                                   if (EnterpriseUtil.isIt360MSPEdition()) {
/* 5117 */                                     com.adventnet.appmanager.struts.form.AMActionForm form = (com.adventnet.appmanager.struts.form.AMActionForm)request.getAttribute("AMActionForm");
/*      */                                     
/* 5119 */                                     ArrayList orgs = AlarmUtil.getCustomerNames();
/*      */                                     
/* 5121 */                                     if (orgs != null)
/*      */                                     {
/* 5123 */                                       request.setAttribute("customers", orgs);
/*      */                                     }
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5130 */                                     if (form != null)
/*      */                                     {
/* 5132 */                                       String customerName = form.getOrganization();
/* 5133 */                                       if (customerName != null)
/*      */                                       {
/* 5135 */                                         ArrayList applications2 = AlarmUtil.getSiteMonitorGroups(customerName);
/* 5136 */                                         if (applications2 != null)
/*      */                                         {
/* 5138 */                                           request.setAttribute("applications2", applications2);
/*      */                                         }
/*      */                                         
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 5146 */                                     ArrayList applications1 = AlarmUtil.getConfiguredGroups();
/* 5147 */                                     if (applications1 != null)
/*      */                                     {
/* 5149 */                                       request.setAttribute("applications1", applications1);
/*      */                                     }
/*      */                                   }
/*      */                                   
/*      */ 
/* 5154 */                                   out.write("\n\n\t\t<TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"2\" CELLSPACING=\"0\" class=\"lrborder ");
/* 5155 */                                   out.print(hideStyle);
/* 5156 */                                   out.write("\" >\n\t\t\t<tr><td style=\"height:20px;\"></td></tr>\n\t\t\t\t<tr>\n\t\t\t\t<td height=\"28\" colspan=\"4\"  class=\"tablebottom bodytextbold\">");
/* 5157 */                                   out.print(FormatUtil.getString("am.webclient.newscript.associatemonitorinstance.text"));
/* 5158 */                                   out.write(32);
/* 5159 */                                   out.print(MGSTR);
/* 5160 */                                   out.write("</td>\n\t\t\t\t</tr>\n\t\t        \t      \n\t        ");
/*      */                                   
/* 5162 */                                   if (EnterpriseUtil.isIt360MSPEdition())
/*      */                                   {
/* 5164 */                                     out.write("\n\t        <tr><td style=\"height:25px;\"></td></tr>\n\t\t    <tr>\n\t\t    \t<td width=\"25%\" height=\"28\" valign=\"middle\" class=\"bodytext\">");
/* 5165 */                                     out.print(FormatUtil.getString("it360.sp.customermgmt.customer.txt", new String[] { MGSTR }));
/* 5166 */                                     out.write("</td>\n\t\t\t    <td height=\"28\" align=\"left\" >\n\t\t\t\t\t");
/*      */                                     
/* 5168 */                                     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5169 */                                     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 5170 */                                     _jspx_th_html_005fselect_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5172 */                                     _jspx_th_html_005fselect_005f1.setProperty("organization");
/*      */                                     
/* 5174 */                                     _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/*      */                                     
/* 5176 */                                     _jspx_th_html_005fselect_005f1.setOnchange("javascript:loadSite()");
/* 5177 */                                     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 5178 */                                     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 5179 */                                       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 5180 */                                         out = _jspx_page_context.pushBody();
/* 5181 */                                         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 5182 */                                         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 5185 */                                         out.write("\n\t\t\t      \t");
/*      */                                         
/* 5187 */                                         OptionTag _jspx_th_html_005foption_005f62 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5188 */                                         _jspx_th_html_005foption_005f62.setPageContext(_jspx_page_context);
/* 5189 */                                         _jspx_th_html_005foption_005f62.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                         
/* 5191 */                                         _jspx_th_html_005foption_005f62.setValue("-");
/* 5192 */                                         int _jspx_eval_html_005foption_005f62 = _jspx_th_html_005foption_005f62.doStartTag();
/* 5193 */                                         if (_jspx_eval_html_005foption_005f62 != 0) {
/* 5194 */                                           if (_jspx_eval_html_005foption_005f62 != 1) {
/* 5195 */                                             out = _jspx_page_context.pushBody();
/* 5196 */                                             _jspx_th_html_005foption_005f62.setBodyContent((BodyContent)out);
/* 5197 */                                             _jspx_th_html_005foption_005f62.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5200 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.customer", new String[] { MGSTR }));
/* 5201 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f62.doAfterBody();
/* 5202 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5205 */                                           if (_jspx_eval_html_005foption_005f62 != 1) {
/* 5206 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5209 */                                         if (_jspx_th_html_005foption_005f62.doEndTag() == 5) {
/* 5210 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f62); return;
/*      */                                         }
/*      */                                         
/* 5213 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f62);
/* 5214 */                                         out.write("\n\t\t\t      \t");
/*      */                                         
/* 5216 */                                         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5217 */                                         _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 5218 */                                         _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                         
/* 5220 */                                         _jspx_th_logic_005fnotEmpty_005f2.setName("customers");
/* 5221 */                                         int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 5222 */                                         if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                                           for (;;) {
/* 5224 */                                             out.write(32);
/*      */                                             
/* 5226 */                                             IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5227 */                                             _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 5228 */                                             _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                             
/* 5230 */                                             _jspx_th_logic_005fiterate_005f1.setName("customers");
/*      */                                             
/* 5232 */                                             _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                                             
/* 5234 */                                             _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                                             
/* 5236 */                                             _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/* 5237 */                                             int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 5238 */                                             if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 5239 */                                               ArrayList row = null;
/* 5240 */                                               Integer j = null;
/* 5241 */                                               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5242 */                                                 out = _jspx_page_context.pushBody();
/* 5243 */                                                 _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 5244 */                                                 _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                               }
/* 5246 */                                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5247 */                                               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                               for (;;) {
/* 5249 */                                                 out.write("\n\t\t\t      \t");
/*      */                                                 
/* 5251 */                                                 OptionTag _jspx_th_html_005foption_005f63 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5252 */                                                 _jspx_th_html_005foption_005f63.setPageContext(_jspx_page_context);
/* 5253 */                                                 _jspx_th_html_005foption_005f63.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                                 
/* 5255 */                                                 _jspx_th_html_005foption_005f63.setValue((String)row.get(1));
/* 5256 */                                                 int _jspx_eval_html_005foption_005f63 = _jspx_th_html_005foption_005f63.doStartTag();
/* 5257 */                                                 if (_jspx_eval_html_005foption_005f63 != 0) {
/* 5258 */                                                   if (_jspx_eval_html_005foption_005f63 != 1) {
/* 5259 */                                                     out = _jspx_page_context.pushBody();
/* 5260 */                                                     _jspx_th_html_005foption_005f63.setBodyContent((BodyContent)out);
/* 5261 */                                                     _jspx_th_html_005foption_005f63.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 5264 */                                                     out.print(row.get(0));
/* 5265 */                                                     int evalDoAfterBody = _jspx_th_html_005foption_005f63.doAfterBody();
/* 5266 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 5269 */                                                   if (_jspx_eval_html_005foption_005f63 != 1) {
/* 5270 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 5273 */                                                 if (_jspx_th_html_005foption_005f63.doEndTag() == 5) {
/* 5274 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f63); return;
/*      */                                                 }
/*      */                                                 
/* 5277 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f63);
/* 5278 */                                                 out.write("\n\t\t\t      \t");
/* 5279 */                                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 5280 */                                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5281 */                                                 j = (Integer)_jspx_page_context.findAttribute("j");
/* 5282 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5285 */                                               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5286 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5289 */                                             if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 5290 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                             }
/*      */                                             
/* 5293 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 5294 */                                             out.write(32);
/* 5295 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 5296 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 5300 */                                         if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 5301 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                                         }
/*      */                                         
/* 5304 */                                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 5305 */                                         out.write(32);
/* 5306 */                                         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 5307 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 5310 */                                       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 5311 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 5314 */                                     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 5315 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                                     }
/*      */                                     
/* 5318 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 5319 */                                     out.write("\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td width=\"25%\" height=\"28\" valign=\"middle\" class=\"bodytext\">");
/* 5320 */                                     out.print(FormatUtil.getString("it360.sp.customermgmt.site.txt", new String[] { MGSTR }));
/* 5321 */                                     out.write("</td>\n\t\t\t\t<td height=\"28\" align=\"left\" >\n\t\t\t\t\t");
/*      */                                     
/* 5323 */                                     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5324 */                                     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 5325 */                                     _jspx_th_html_005fselect_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5327 */                                     _jspx_th_html_005fselect_005f2.setProperty("haid");
/*      */                                     
/* 5329 */                                     _jspx_th_html_005fselect_005f2.setStyleClass("formtext");
/*      */                                     
/* 5331 */                                     _jspx_th_html_005fselect_005f2.setOnchange("javascript:check()");
/* 5332 */                                     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 5333 */                                     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 5334 */                                       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 5335 */                                         out = _jspx_page_context.pushBody();
/* 5336 */                                         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 5337 */                                         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 5340 */                                         out.write("\n\t\t\t\t\t      ");
/*      */                                         
/* 5342 */                                         OptionTag _jspx_th_html_005foption_005f64 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5343 */                                         _jspx_th_html_005foption_005f64.setPageContext(_jspx_page_context);
/* 5344 */                                         _jspx_th_html_005foption_005f64.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                         
/* 5346 */                                         _jspx_th_html_005foption_005f64.setValue("-");
/* 5347 */                                         int _jspx_eval_html_005foption_005f64 = _jspx_th_html_005foption_005f64.doStartTag();
/* 5348 */                                         if (_jspx_eval_html_005foption_005f64 != 0) {
/* 5349 */                                           if (_jspx_eval_html_005foption_005f64 != 1) {
/* 5350 */                                             out = _jspx_page_context.pushBody();
/* 5351 */                                             _jspx_th_html_005foption_005f64.setBodyContent((BodyContent)out);
/* 5352 */                                             _jspx_th_html_005foption_005f64.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5355 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.siteGroup", new String[] { MGSTR }));
/* 5356 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f64.doAfterBody();
/* 5357 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5360 */                                           if (_jspx_eval_html_005foption_005f64 != 1) {
/* 5361 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5364 */                                         if (_jspx_th_html_005foption_005f64.doEndTag() == 5) {
/* 5365 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f64); return;
/*      */                                         }
/*      */                                         
/* 5368 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f64);
/* 5369 */                                         out.write("\n\t\t\t\t\t      ");
/*      */                                         
/* 5371 */                                         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5372 */                                         _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 5373 */                                         _jspx_th_logic_005fnotEmpty_005f3.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                         
/* 5375 */                                         _jspx_th_logic_005fnotEmpty_005f3.setName("applications2");
/* 5376 */                                         int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 5377 */                                         if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */                                           for (;;) {
/* 5379 */                                             out.write(32);
/*      */                                             
/* 5381 */                                             IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5382 */                                             _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 5383 */                                             _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f3);
/*      */                                             
/* 5385 */                                             _jspx_th_logic_005fiterate_005f2.setName("applications2");
/*      */                                             
/* 5387 */                                             _jspx_th_logic_005fiterate_005f2.setId("row");
/*      */                                             
/* 5389 */                                             _jspx_th_logic_005fiterate_005f2.setIndexId("j");
/*      */                                             
/* 5391 */                                             _jspx_th_logic_005fiterate_005f2.setType("java.util.ArrayList");
/* 5392 */                                             int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 5393 */                                             if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 5394 */                                               ArrayList row = null;
/* 5395 */                                               Integer j = null;
/* 5396 */                                               if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 5397 */                                                 out = _jspx_page_context.pushBody();
/* 5398 */                                                 _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 5399 */                                                 _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                                               }
/* 5401 */                                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5402 */                                               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                               for (;;) {
/* 5404 */                                                 out.write("\n\t\t\t\t\t      ");
/*      */                                                 
/* 5406 */                                                 OptionTag _jspx_th_html_005foption_005f65 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5407 */                                                 _jspx_th_html_005foption_005f65.setPageContext(_jspx_page_context);
/* 5408 */                                                 _jspx_th_html_005foption_005f65.setParent(_jspx_th_logic_005fiterate_005f2);
/*      */                                                 
/* 5410 */                                                 _jspx_th_html_005foption_005f65.setValue((String)row.get(1));
/* 5411 */                                                 int _jspx_eval_html_005foption_005f65 = _jspx_th_html_005foption_005f65.doStartTag();
/* 5412 */                                                 if (_jspx_eval_html_005foption_005f65 != 0) {
/* 5413 */                                                   if (_jspx_eval_html_005foption_005f65 != 1) {
/* 5414 */                                                     out = _jspx_page_context.pushBody();
/* 5415 */                                                     _jspx_th_html_005foption_005f65.setBodyContent((BodyContent)out);
/* 5416 */                                                     _jspx_th_html_005foption_005f65.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 5419 */                                                     out.print(row.get(0));
/* 5420 */                                                     int evalDoAfterBody = _jspx_th_html_005foption_005f65.doAfterBody();
/* 5421 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 5424 */                                                   if (_jspx_eval_html_005foption_005f65 != 1) {
/* 5425 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 5428 */                                                 if (_jspx_th_html_005foption_005f65.doEndTag() == 5) {
/* 5429 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f65); return;
/*      */                                                 }
/*      */                                                 
/* 5432 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f65);
/* 5433 */                                                 out.write("\n\t\t\t\t\t      ");
/* 5434 */                                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 5435 */                                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5436 */                                                 j = (Integer)_jspx_page_context.findAttribute("j");
/* 5437 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5440 */                                               if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 5441 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5444 */                                             if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 5445 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                                             }
/*      */                                             
/* 5448 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 5449 */                                             out.write(32);
/* 5450 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 5451 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 5455 */                                         if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 5456 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3); return;
/*      */                                         }
/*      */                                         
/* 5459 */                                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 5460 */                                         out.write(" \n\t\t\t\t\t");
/* 5461 */                                         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 5462 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 5465 */                                       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 5466 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 5469 */                                     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 5470 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2); return;
/*      */                                     }
/*      */                                     
/* 5473 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/* 5474 */                                     out.write("\n\t      \t\t</td>\n\t      \t</tr>\n\t  ");
/*      */                                   } else {
/* 5476 */                                     out.write("\n\t\t\t<tr height=\"35\">\n\t\t    <td width=\"25%\" style=\"padding-left:10px;\" class=\"bodytext\">");
/* 5477 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.monitorgroupselect", new String[] { MGSTR }));
/* 5478 */                                     out.write("</td>\n\t\t    <td  align=\"left\" width=\"75%\">\n\t\t\t\t<table width=\"100%\" BORDER=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t<tr height=\"35\">\n\t\t\t\t\t\t<td width=\"25%\" style=\"padding-left:5px;\" >\n\t\t\t\t\t\t\t");
/*      */                                     
/* 5480 */                                     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5481 */                                     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 5482 */                                     _jspx_th_html_005fselect_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5484 */                                     _jspx_th_html_005fselect_005f3.setProperty("haid");
/*      */                                     
/* 5486 */                                     _jspx_th_html_005fselect_005f3.setStyleClass("formtext");
/*      */                                     
/* 5488 */                                     _jspx_th_html_005fselect_005f3.setOnchange("javascript:check()");
/* 5489 */                                     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 5490 */                                     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 5491 */                                       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 5492 */                                         out = _jspx_page_context.pushBody();
/* 5493 */                                         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 5494 */                                         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 5497 */                                         out.write("\n\t\t\t\t\t      \t\t");
/*      */                                         
/* 5499 */                                         OptionTag _jspx_th_html_005foption_005f66 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5500 */                                         _jspx_th_html_005foption_005f66.setPageContext(_jspx_page_context);
/* 5501 */                                         _jspx_th_html_005foption_005f66.setParent(_jspx_th_html_005fselect_005f3);
/*      */                                         
/* 5503 */                                         _jspx_th_html_005foption_005f66.setValue("-");
/* 5504 */                                         int _jspx_eval_html_005foption_005f66 = _jspx_th_html_005foption_005f66.doStartTag();
/* 5505 */                                         if (_jspx_eval_html_005foption_005f66 != 0) {
/* 5506 */                                           if (_jspx_eval_html_005foption_005f66 != 1) {
/* 5507 */                                             out = _jspx_page_context.pushBody();
/* 5508 */                                             _jspx_th_html_005foption_005f66.setBodyContent((BodyContent)out);
/* 5509 */                                             _jspx_th_html_005foption_005f66.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5512 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.monitorgroup", new String[] { MGSTR }));
/* 5513 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f66.doAfterBody();
/* 5514 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5517 */                                           if (_jspx_eval_html_005foption_005f66 != 1) {
/* 5518 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5521 */                                         if (_jspx_th_html_005foption_005f66.doEndTag() == 5) {
/* 5522 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f66); return;
/*      */                                         }
/*      */                                         
/* 5525 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f66);
/* 5526 */                                         out.write("\n\t\t\t\t\t      \t\t");
/*      */                                         
/* 5528 */                                         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f4 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5529 */                                         _jspx_th_logic_005fnotEmpty_005f4.setPageContext(_jspx_page_context);
/* 5530 */                                         _jspx_th_logic_005fnotEmpty_005f4.setParent(_jspx_th_html_005fselect_005f3);
/*      */                                         
/* 5532 */                                         _jspx_th_logic_005fnotEmpty_005f4.setName("applications1");
/* 5533 */                                         int _jspx_eval_logic_005fnotEmpty_005f4 = _jspx_th_logic_005fnotEmpty_005f4.doStartTag();
/* 5534 */                                         if (_jspx_eval_logic_005fnotEmpty_005f4 != 0) {
/*      */                                           for (;;) {
/* 5536 */                                             out.write(" \n\t\t\t\t\t      \t\t");
/*      */                                             
/* 5538 */                                             IterateTag _jspx_th_logic_005fiterate_005f3 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5539 */                                             _jspx_th_logic_005fiterate_005f3.setPageContext(_jspx_page_context);
/* 5540 */                                             _jspx_th_logic_005fiterate_005f3.setParent(_jspx_th_logic_005fnotEmpty_005f4);
/*      */                                             
/* 5542 */                                             _jspx_th_logic_005fiterate_005f3.setName("applications1");
/*      */                                             
/* 5544 */                                             _jspx_th_logic_005fiterate_005f3.setId("row");
/*      */                                             
/* 5546 */                                             _jspx_th_logic_005fiterate_005f3.setIndexId("j");
/*      */                                             
/* 5548 */                                             _jspx_th_logic_005fiterate_005f3.setType("java.util.ArrayList");
/* 5549 */                                             int _jspx_eval_logic_005fiterate_005f3 = _jspx_th_logic_005fiterate_005f3.doStartTag();
/* 5550 */                                             if (_jspx_eval_logic_005fiterate_005f3 != 0) {
/* 5551 */                                               ArrayList row = null;
/* 5552 */                                               Integer j = null;
/* 5553 */                                               if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 5554 */                                                 out = _jspx_page_context.pushBody();
/* 5555 */                                                 _jspx_th_logic_005fiterate_005f3.setBodyContent((BodyContent)out);
/* 5556 */                                                 _jspx_th_logic_005fiterate_005f3.doInitBody();
/*      */                                               }
/* 5558 */                                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5559 */                                               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                               for (;;) {
/* 5561 */                                                 out.write("\n\t\t\t\t\t      \t\t\t");
/*      */                                                 
/* 5563 */                                                 OptionTag _jspx_th_html_005foption_005f67 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5564 */                                                 _jspx_th_html_005foption_005f67.setPageContext(_jspx_page_context);
/* 5565 */                                                 _jspx_th_html_005foption_005f67.setParent(_jspx_th_logic_005fiterate_005f3);
/*      */                                                 
/* 5567 */                                                 _jspx_th_html_005foption_005f67.setValue((String)row.get(1));
/* 5568 */                                                 int _jspx_eval_html_005foption_005f67 = _jspx_th_html_005foption_005f67.doStartTag();
/* 5569 */                                                 if (_jspx_eval_html_005foption_005f67 != 0) {
/* 5570 */                                                   if (_jspx_eval_html_005foption_005f67 != 1) {
/* 5571 */                                                     out = _jspx_page_context.pushBody();
/* 5572 */                                                     _jspx_th_html_005foption_005f67.setBodyContent((BodyContent)out);
/* 5573 */                                                     _jspx_th_html_005foption_005f67.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 5576 */                                                     out.print(row.get(0));
/* 5577 */                                                     int evalDoAfterBody = _jspx_th_html_005foption_005f67.doAfterBody();
/* 5578 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 5581 */                                                   if (_jspx_eval_html_005foption_005f67 != 1) {
/* 5582 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 5585 */                                                 if (_jspx_th_html_005foption_005f67.doEndTag() == 5) {
/* 5586 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f67); return;
/*      */                                                 }
/*      */                                                 
/* 5589 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f67);
/* 5590 */                                                 out.write("\n\t\t\t\t\t      \t\t");
/* 5591 */                                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f3.doAfterBody();
/* 5592 */                                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5593 */                                                 j = (Integer)_jspx_page_context.findAttribute("j");
/* 5594 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5597 */                                               if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 5598 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5601 */                                             if (_jspx_th_logic_005fiterate_005f3.doEndTag() == 5) {
/* 5602 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3); return;
/*      */                                             }
/*      */                                             
/* 5605 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3);
/* 5606 */                                             out.write(" \n\t\t\t\t\t      \t\t");
/* 5607 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f4.doAfterBody();
/* 5608 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 5612 */                                         if (_jspx_th_logic_005fnotEmpty_005f4.doEndTag() == 5) {
/* 5613 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4); return;
/*      */                                         }
/*      */                                         
/* 5616 */                                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 5617 */                                         out.write(" \n\t\t\t\t\t      \t");
/* 5618 */                                         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 5619 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 5622 */                                       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 5623 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 5626 */                                     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 5627 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3); return;
/*      */                                     }
/*      */                                     
/* 5630 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3);
/* 5631 */                                     out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t<table BORDER=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td colspan=\"2\">\n\t\t\t\t\t\t\t\t\t<div id=\"group\" width=\"15%\" nowrap=\"nowrap\" style=\"padding-left:20px;white-space: nowrap;\"><a href=\"#\" class=\"staticlinks\" onClick=\"javascript:hideDiv('group');hideDiv('createsubgroup');hideDiv('groupmessage');showDiv('creategroup');resetname('groupname'); return false;\">");
/* 5632 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.browsertitle"));
/* 5633 */                                     out.write("</a></div>\n\t\t\t\t\t\t\t\t\t<div id=\"subgroup\" width=\"25%\" nowrap=\"nowrap\" Style=\"Display:none; padding-left:20px;white-space: nowrap;\"><a href=\"#\" class=\"staticlinks\" onClick=\"javascript:hideDiv('subgroup');hideDiv('creategroup');hideDiv('groupmessage');showDiv('createsubgroup');resetname('subgroupname'); return false;\">");
/* 5634 */                                     out.print(FormatUtil.getString("am.webclient.monitorsubgroupfirst.browsertitle"));
/* 5635 */                                     out.write("</a></div>\n\t\t\t\t\t\t\t\t\t<div id=\"creategroup\" style=\"display:none;padding-left:20px;\">\n\t\t\t\t\t\t\t\t\t\t<span class=\"bodytext\">");
/* 5636 */                                     out.print(FormatUtil.getString("webclient.map.mapsymboldetails.groupname"));
/* 5637 */                                     out.write(":</span>\n\t\t\t\t\t\t\t\t\t\t<input name=\"groupname\" type=\"text\" class=\"formtext\">\n\t\t\t\t\t\t\t\t\t\t<input name=\"Create\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 5638 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.create"));
/* 5639 */                                     out.write("\" onClick=\"javascript:createGroup();\"> <input name=\"cancel\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 5640 */                                     out.print(FormatUtil.getString("Cancel"));
/* 5641 */                                     out.write("\" onClick=\"javascript:hideDiv('creategroup'); showDiv('group'); return false;\">\n\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t<div id=\"createsubgroup\" style=\"display:none;padding-left:20px;\">\n\t\t\t\t\t\t\t\t\t\t<span class=\"bodytext\">");
/* 5642 */                                     out.print(FormatUtil.getString("webclient.map.mapsymboldetails.subgroupname"));
/* 5643 */                                     out.write(":</span>\n\t\t\t\t\t\t\t\t\t\t<input name=\"subgroupname\" type=\"text\" class=\"formtext\">\n\t\t\t\t\t\t\t\t\t\t<input name=\"Create\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 5644 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.create"));
/* 5645 */                                     out.write("\" onClick=\"javascript:createsubGroup();\"> <input name=\"cancel\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 5646 */                                     out.print(FormatUtil.getString("Cancel"));
/* 5647 */                                     out.write("\" onClick=\"javascript:hideDiv('createsubgroup'); showDiv('subgroup'); return false;\">\n\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t<div id=\"groupmessage\" style=\"display:block; padding-left:20px;\" class='error-text'></div>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t ");
/*      */                                   }
/* 5649 */                                   out.write("\n\t</table>\n\n\n  <table class=\"lrbborder\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" width=\"99%\">\n   <tbody>\n    <tr>\n      <td width=\"24%\" height=\"22\" class=\"tablebottom\">&nbsp;</td>\n      <td width=\"76%\" height=\"26\" class=\"tablebottom\"> ");
/*      */                                   
/* 5651 */                                   IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5652 */                                   _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 5653 */                                   _jspx_th_c_005fif_005f7.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5655 */                                   _jspx_th_c_005fif_005f7.setTest("${empty method}");
/* 5656 */                                   int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 5657 */                                   if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                     for (;;) {
/* 5659 */                                       out.write(32);
/*      */                                       
/* 5661 */                                       ButtonTag _jspx_th_html_005fbutton_005f0 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5662 */                                       _jspx_th_html_005fbutton_005f0.setPageContext(_jspx_page_context);
/* 5663 */                                       _jspx_th_html_005fbutton_005f0.setParent(_jspx_th_c_005fif_005f7);
/*      */                                       
/* 5665 */                                       _jspx_th_html_005fbutton_005f0.setStyleClass("buttons btn_highlt");
/*      */                                       
/* 5667 */                                       _jspx_th_html_005fbutton_005f0.setValue(FormatUtil.getString("am.webclient.newscript.addmonitors.text"));
/*      */                                       
/* 5669 */                                       _jspx_th_html_005fbutton_005f0.setOnclick("validateAndSubmit();");
/*      */                                       
/* 5671 */                                       _jspx_th_html_005fbutton_005f0.setProperty("submitbutton1");
/* 5672 */                                       int _jspx_eval_html_005fbutton_005f0 = _jspx_th_html_005fbutton_005f0.doStartTag();
/* 5673 */                                       if (_jspx_th_html_005fbutton_005f0.doEndTag() == 5) {
/* 5674 */                                         this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0); return;
/*      */                                       }
/*      */                                       
/* 5677 */                                       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0);
/* 5678 */                                       out.write("\n        ");
/* 5679 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 5680 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5684 */                                   if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 5685 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                   }
/*      */                                   
/* 5688 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5689 */                                   out.write(32);
/*      */                                   
/* 5691 */                                   IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5692 */                                   _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 5693 */                                   _jspx_th_c_005fif_005f8.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5695 */                                   _jspx_th_c_005fif_005f8.setTest("${!empty method}");
/* 5696 */                                   int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 5697 */                                   if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                     for (;;) {
/* 5699 */                                       out.write("\n        ");
/*      */                                       
/* 5701 */                                       ButtonTag _jspx_th_html_005fbutton_005f1 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5702 */                                       _jspx_th_html_005fbutton_005f1.setPageContext(_jspx_page_context);
/* 5703 */                                       _jspx_th_html_005fbutton_005f1.setParent(_jspx_th_c_005fif_005f8);
/*      */                                       
/* 5705 */                                       _jspx_th_html_005fbutton_005f1.setOnclick("return validateAndSubmit();");
/*      */                                       
/* 5707 */                                       _jspx_th_html_005fbutton_005f1.setStyleClass("buttons btn_highlt");
/*      */                                       
/* 5709 */                                       _jspx_th_html_005fbutton_005f1.setProperty("submitbutton1");
/*      */                                       
/* 5711 */                                       _jspx_th_html_005fbutton_005f1.setValue(FormatUtil.getString("am.webclient.hostdiscovery.qengine.button.update"));
/* 5712 */                                       int _jspx_eval_html_005fbutton_005f1 = _jspx_th_html_005fbutton_005f1.doStartTag();
/* 5713 */                                       if (_jspx_th_html_005fbutton_005f1.doEndTag() == 5) {
/* 5714 */                                         this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1); return;
/*      */                                       }
/*      */                                       
/* 5717 */                                       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1);
/* 5718 */                                       out.write("\n        ");
/* 5719 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 5720 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5724 */                                   if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 5725 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                   }
/*      */                                   
/* 5728 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 5729 */                                   out.write(" &nbsp;\n                ");
/*      */                                   
/* 5731 */                                   ResetTag _jspx_th_html_005freset_005f0 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.get(ResetTag.class);
/* 5732 */                                   _jspx_th_html_005freset_005f0.setPageContext(_jspx_page_context);
/* 5733 */                                   _jspx_th_html_005freset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5735 */                                   _jspx_th_html_005freset_005f0.setStyleClass("buttons btn_reset");
/*      */                                   
/* 5737 */                                   _jspx_th_html_005freset_005f0.setValue(FormatUtil.getString("am.webclient.hostdiscovery.qengine.button.restore"));
/* 5738 */                                   int _jspx_eval_html_005freset_005f0 = _jspx_th_html_005freset_005f0.doStartTag();
/* 5739 */                                   if (_jspx_th_html_005freset_005f0.doEndTag() == 5) {
/* 5740 */                                     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005freset_005f0); return;
/*      */                                   }
/*      */                                   
/* 5743 */                                   this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005freset_005f0);
/* 5744 */                                   out.write(" &nbsp;\n                ");
/*      */                                   
/* 5746 */                                   ResetTag _jspx_th_html_005freset_005f1 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody.get(ResetTag.class);
/* 5747 */                                   _jspx_th_html_005freset_005f1.setPageContext(_jspx_page_context);
/* 5748 */                                   _jspx_th_html_005freset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5750 */                                   _jspx_th_html_005freset_005f1.setStyleClass("buttons btn_reset");
/*      */                                   
/* 5752 */                                   _jspx_th_html_005freset_005f1.setValue(FormatUtil.getString("webclient.fault.alarm.customview.button.cancel"));
/*      */                                   
/* 5754 */                                   _jspx_th_html_005freset_005f1.setOnclick("javascript:history.back();");
/*      */                                   
/* 5756 */                                   _jspx_th_html_005freset_005f1.setStyleId("cancelButtonMod");
/* 5757 */                                   int _jspx_eval_html_005freset_005f1 = _jspx_th_html_005freset_005f1.doStartTag();
/* 5758 */                                   if (_jspx_th_html_005freset_005f1.doEndTag() == 5) {
/* 5759 */                                     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f1); return;
/*      */                                   }
/*      */                                   
/* 5762 */                                   this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f1);
/* 5763 */                                   out.write("</td>\n    </tr>\n        </tbody>\n</table>\n");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 5769 */                                   out.write("\n<TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n  <tr>\n    <td align=\"center\">\n      <input name=\"closeButton\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 5770 */                                   out.print(FormatUtil.getString("Close Window"));
/* 5771 */                                   out.write("\" onClick=\"closePopUpWindow();\">\n      ");
/* 5772 */                                   if (!isDiscoverySuccess) {
/* 5773 */                                     out.write("\n              ");
/*      */                                     
/* 5775 */                                     ResetTag _jspx_th_html_005freset_005f2 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.get(ResetTag.class);
/* 5776 */                                     _jspx_th_html_005freset_005f2.setPageContext(_jspx_page_context);
/* 5777 */                                     _jspx_th_html_005freset_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5779 */                                     _jspx_th_html_005freset_005f2.setStyleClass("buttons btn_highlt");
/*      */                                     
/* 5781 */                                     _jspx_th_html_005freset_005f2.setValue(FormatUtil.getString("am.webclient.goback.readd.txt"));
/*      */                                     
/* 5783 */                                     _jspx_th_html_005freset_005f2.setOnclick("javascript:history.back();");
/* 5784 */                                     int _jspx_eval_html_005freset_005f2 = _jspx_th_html_005freset_005f2.doStartTag();
/* 5785 */                                     if (_jspx_th_html_005freset_005f2.doEndTag() == 5) {
/* 5786 */                                       this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f2); return;
/*      */                                     }
/*      */                                     
/* 5789 */                                     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f2);
/* 5790 */                                     out.write("\n      ");
/*      */                                   }
/* 5792 */                                   out.write("\n      </td>\n      </tr>\n      </table>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 5796 */                                 if (hideFields)
/*      */                                 {
/*      */ 
/* 5799 */                                   out.write("\n\t<script>\n\t\t$(document.body).ready(function(){\n\t\tdocument.getElementById(\"cancelButtonMod\").onclick = null;\n\t\t$(\"#cancelButtonMod\").click(function(){ //No I18N\n\t\t\tclosePopUpWindow();\n\t\t});\n\t\t});\n\t</script>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 5803 */                                 out.write(10);
/* 5804 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 5805 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 5809 */                             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 5810 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                             }
/*      */                             
/* 5813 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 5814 */                             out.write(10);
/* 5815 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 5816 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 5819 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 5820 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 5823 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 5824 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 5827 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 5828 */                         out.write(10);
/* 5829 */                         if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 5831 */                         out.write(32);
/* 5832 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5833 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 5837 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5838 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 5841 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5842 */                       out.write(10);
/*      */                     }
/* 5844 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5845 */         out = _jspx_out;
/* 5846 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5847 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5848 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5851 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5857 */     PageContext pageContext = _jspx_page_context;
/* 5858 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5860 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 5861 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 5862 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */     
/* 5864 */     _jspx_th_c_005fforEach_005f0.setItems("${dynamicSites}");
/*      */     
/* 5866 */     _jspx_th_c_005fforEach_005f0.setVar("a");
/*      */     
/* 5868 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowCounter");
/* 5869 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 5871 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 5872 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 5874 */           out.write(10);
/* 5875 */           out.write(9);
/* 5876 */           out.write(9);
/* 5877 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5878 */             return true;
/* 5879 */           out.write("\n\t\tif(formCustomerId == customerId)\n\t\t{\n\t\t\tdocument.AMActionForm.haid.options[rowCount++] = new Option(siteName,siteId);\n\t\t}\n\t");
/* 5880 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 5881 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5885 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 5886 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5889 */         int tmp206_205 = 0; int[] tmp206_203 = _jspx_push_body_count_c_005fforEach_005f0; int tmp208_207 = tmp206_203[tmp206_205];tmp206_203[tmp206_205] = (tmp208_207 - 1); if (tmp208_207 <= 0) break;
/* 5890 */         out = _jspx_page_context.popBody(); }
/* 5891 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 5893 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 5894 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 5896 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5901 */     PageContext pageContext = _jspx_page_context;
/* 5902 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5904 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 5905 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 5906 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5908 */     _jspx_th_c_005fforEach_005f1.setItems("${a}");
/*      */     
/* 5910 */     _jspx_th_c_005fforEach_005f1.setVar("b");
/*      */     
/* 5912 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowCounter1");
/* 5913 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 5915 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 5916 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 5918 */           out.write("\n\t\t\t");
/* 5919 */           boolean bool; if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5920 */             return true;
/* 5921 */           out.write("\n\t\t\t");
/* 5922 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5923 */             return true;
/* 5924 */           out.write("\n\t\t\t");
/* 5925 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5926 */             return true;
/* 5927 */           out.write(10);
/* 5928 */           out.write(9);
/* 5929 */           out.write(9);
/* 5930 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 5931 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5935 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 5936 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5939 */         int tmp295_294 = 0; int[] tmp295_292 = _jspx_push_body_count_c_005fforEach_005f1; int tmp297_296 = tmp295_292[tmp295_294];tmp295_292[tmp295_294] = (tmp297_296 - 1); if (tmp297_296 <= 0) break;
/* 5940 */         out = _jspx_page_context.popBody(); }
/* 5941 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 5943 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 5944 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 5946 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5951 */     PageContext pageContext = _jspx_page_context;
/* 5952 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5954 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5955 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 5956 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5958 */     _jspx_th_c_005fif_005f0.setTest("${rowCounter1.count == 1}");
/* 5959 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 5960 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 5962 */         out.write("\n\t\t\t\tsiteName = '");
/* 5963 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5964 */           return true;
/* 5965 */         out.write("';\n\t\t\t");
/* 5966 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 5967 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5971 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 5972 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5973 */       return true;
/*      */     }
/* 5975 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5976 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5981 */     PageContext pageContext = _jspx_page_context;
/* 5982 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5984 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5985 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5986 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 5988 */     _jspx_th_c_005fout_005f0.setValue("${b}");
/* 5989 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5990 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5991 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5992 */       return true;
/*      */     }
/* 5994 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5995 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6000 */     PageContext pageContext = _jspx_page_context;
/* 6001 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6003 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6004 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 6005 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6007 */     _jspx_th_c_005fif_005f1.setTest("${rowCounter1.count == 2}");
/* 6008 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 6009 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 6011 */         out.write("\n\t\t\t\tsiteId = '");
/* 6012 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6013 */           return true;
/* 6014 */         out.write("';\n\t\t\t");
/* 6015 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 6016 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6020 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 6021 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6022 */       return true;
/*      */     }
/* 6024 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6025 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6030 */     PageContext pageContext = _jspx_page_context;
/* 6031 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6033 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6034 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 6035 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 6037 */     _jspx_th_c_005fout_005f1.setValue("${b}");
/* 6038 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 6039 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 6040 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6041 */       return true;
/*      */     }
/* 6043 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6044 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6049 */     PageContext pageContext = _jspx_page_context;
/* 6050 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6052 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6053 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 6054 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6056 */     _jspx_th_c_005fif_005f2.setTest("${rowCounter1.count == 3}");
/* 6057 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 6058 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 6060 */         out.write("\n\t\t\t\tcustomerId = '");
/* 6061 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6062 */           return true;
/* 6063 */         out.write("';\n\t\t\t");
/* 6064 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 6065 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6069 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 6070 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6071 */       return true;
/*      */     }
/* 6073 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6079 */     PageContext pageContext = _jspx_page_context;
/* 6080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6082 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6083 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 6084 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6086 */     _jspx_th_c_005fout_005f2.setValue("${b}");
/* 6087 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 6088 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 6089 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6090 */       return true;
/*      */     }
/* 6092 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6093 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6098 */     PageContext pageContext = _jspx_page_context;
/* 6099 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6101 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6102 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 6103 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/* 6104 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 6105 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 6106 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6107 */         out = _jspx_page_context.pushBody();
/* 6108 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 6109 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6112 */         out.write("am.webclient.common.validpollinginterval.text");
/* 6113 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 6114 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6117 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6118 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6121 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 6122 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6123 */       return true;
/*      */     }
/* 6125 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6126 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6131 */     PageContext pageContext = _jspx_page_context;
/* 6132 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6134 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6135 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 6136 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6138 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 6140 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 6141 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 6142 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 6143 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6144 */       return true;
/*      */     }
/* 6146 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6147 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6152 */     PageContext pageContext = _jspx_page_context;
/* 6153 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6155 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6156 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 6157 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6159 */     _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */     
/* 6161 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/DiscoveryLeftLinks.jsp");
/* 6162 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 6163 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 6164 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 6165 */       return true;
/*      */     }
/* 6167 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 6168 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6173 */     PageContext pageContext = _jspx_page_context;
/* 6174 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6176 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 6177 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 6178 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6180 */     _jspx_th_am_005fhiddenparam_005f0.setName("wiz");
/* 6181 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 6182 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 6183 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 6184 */       return true;
/*      */     }
/* 6186 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 6187 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6192 */     PageContext pageContext = _jspx_page_context;
/* 6193 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6195 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6196 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 6197 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6199 */     _jspx_th_c_005fif_005f6.setTest("${!empty param.wiz ||  !empty param.fromAssociate}");
/* 6200 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 6201 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 6203 */         out.write("\n      ");
/* 6204 */         if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 6205 */           return true;
/* 6206 */         out.write("\n      ");
/* 6207 */         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 6208 */           return true;
/* 6209 */         out.write("\n      ");
/* 6210 */         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 6211 */           return true;
/* 6212 */         out.write("\n      ");
/* 6213 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 6214 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6218 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 6219 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6220 */       return true;
/*      */     }
/* 6222 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6223 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6228 */     PageContext pageContext = _jspx_page_context;
/* 6229 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6231 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 6232 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 6233 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fif_005f6);
/* 6234 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 6235 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 6237 */         out.write("\n        ");
/* 6238 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 6239 */           return true;
/* 6240 */         out.write("\n        ");
/* 6241 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 6242 */           return true;
/* 6243 */         out.write("\n\n        ");
/* 6244 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 6245 */           return true;
/* 6246 */         out.write("\n      ");
/* 6247 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 6248 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6252 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 6253 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 6254 */       return true;
/*      */     }
/* 6256 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 6257 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6262 */     PageContext pageContext = _jspx_page_context;
/* 6263 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6265 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6266 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 6267 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 6269 */     _jspx_th_c_005fwhen_005f0.setTest("${param.type=='WTA'}");
/* 6270 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 6271 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 6273 */         out.write("\n          ");
/* 6274 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 6275 */           return true;
/* 6276 */         out.write("\n        ");
/* 6277 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 6278 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6282 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 6283 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 6284 */       return true;
/*      */     }
/* 6286 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 6287 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6292 */     PageContext pageContext = _jspx_page_context;
/* 6293 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6295 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6296 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 6297 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6299 */     _jspx_th_c_005fout_005f3.setValue("Web Transaction Monitor");
/* 6300 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 6301 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 6302 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6303 */       return true;
/*      */     }
/* 6305 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6306 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6311 */     PageContext pageContext = _jspx_page_context;
/* 6312 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6314 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6315 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 6316 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 6318 */     _jspx_th_c_005fwhen_005f1.setTest("${param.type=='.Net'}");
/* 6319 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 6320 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 6322 */         out.write("\n          ");
/* 6323 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 6324 */           return true;
/* 6325 */         out.write("\n        ");
/* 6326 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 6327 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6331 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 6332 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 6333 */       return true;
/*      */     }
/* 6335 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 6336 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6341 */     PageContext pageContext = _jspx_page_context;
/* 6342 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6344 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6345 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6346 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 6348 */     _jspx_th_c_005fout_005f4.setValue("Tomcat Server");
/* 6349 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 6350 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 6351 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6352 */       return true;
/*      */     }
/* 6354 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6355 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6360 */     PageContext pageContext = _jspx_page_context;
/* 6361 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6363 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 6364 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 6365 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 6366 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 6367 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 6369 */         out.write("\n         ");
/* 6370 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 6371 */           return true;
/* 6372 */         out.write("\n        ");
/* 6373 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 6374 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6378 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 6379 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 6380 */       return true;
/*      */     }
/* 6382 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 6383 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6388 */     PageContext pageContext = _jspx_page_context;
/* 6389 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6391 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 6392 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6393 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 6395 */     _jspx_th_c_005fout_005f5.setValue("${param.type}");
/* 6396 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6397 */     if (_jspx_eval_c_005fout_005f5 != 0) {
/* 6398 */       if (_jspx_eval_c_005fout_005f5 != 1) {
/* 6399 */         out = _jspx_page_context.pushBody();
/* 6400 */         _jspx_th_c_005fout_005f5.setBodyContent((BodyContent)out);
/* 6401 */         _jspx_th_c_005fout_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6404 */         out.write(45);
/* 6405 */         int evalDoAfterBody = _jspx_th_c_005fout_005f5.doAfterBody();
/* 6406 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6409 */       if (_jspx_eval_c_005fout_005f5 != 1) {
/* 6410 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6413 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6414 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f5);
/* 6415 */       return true;
/*      */     }
/* 6417 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f5);
/* 6418 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6423 */     PageContext pageContext = _jspx_page_context;
/* 6424 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6426 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6427 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 6428 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6430 */     _jspx_th_html_005fhidden_005f0.setProperty("type");
/* 6431 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 6432 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 6433 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 6434 */       return true;
/*      */     }
/* 6436 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 6437 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6442 */     PageContext pageContext = _jspx_page_context;
/* 6443 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6445 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6446 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 6447 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6449 */     _jspx_th_html_005fhidden_005f1.setProperty("haid");
/* 6450 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 6451 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 6452 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 6453 */       return true;
/*      */     }
/* 6455 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 6456 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6461 */     PageContext pageContext = _jspx_page_context;
/* 6462 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6464 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fnobody.get(TextTag.class);
/* 6465 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 6466 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6468 */     _jspx_th_html_005ftext_005f0.setProperty("hostname");
/*      */     
/* 6470 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*      */     
/* 6472 */     _jspx_th_html_005ftext_005f0.setSize("15");
/*      */     
/* 6474 */     _jspx_th_html_005ftext_005f0.setOnblur("javascript:showServiceDetail()");
/* 6475 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 6476 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 6477 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 6478 */       return true;
/*      */     }
/* 6480 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 6481 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6486 */     PageContext pageContext = _jspx_page_context;
/* 6487 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6489 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6490 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 6491 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6493 */     _jspx_th_html_005ftext_005f1.setProperty("pollInterval");
/*      */     
/* 6495 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/* 6497 */     _jspx_th_html_005ftext_005f1.setSize("5");
/* 6498 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 6499 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 6500 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 6501 */       return true;
/*      */     }
/* 6503 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 6504 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6509 */     PageContext pageContext = _jspx_page_context;
/* 6510 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6512 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6513 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 6514 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6516 */     _jspx_th_html_005fcheckbox_005f0.setProperty("authEnabled");
/*      */     
/* 6518 */     _jspx_th_html_005fcheckbox_005f0.setOnclick("handleGlobalSecurity(this)");
/* 6519 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 6520 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 6521 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 6522 */       return true;
/*      */     }
/* 6524 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 6525 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6530 */     PageContext pageContext = _jspx_page_context;
/* 6531 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6533 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6534 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 6535 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6537 */     _jspx_th_html_005ftext_005f2.setProperty("retries");
/*      */     
/* 6539 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 6541 */     _jspx_th_html_005ftext_005f2.setSize("5");
/* 6542 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 6543 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 6544 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 6545 */       return true;
/*      */     }
/* 6547 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 6548 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6553 */     PageContext pageContext = _jspx_page_context;
/* 6554 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6556 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6557 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 6558 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6560 */     _jspx_th_html_005ftext_005f3.setProperty("ptimeout");
/*      */     
/* 6562 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/* 6564 */     _jspx_th_html_005ftext_005f3.setSize("5");
/* 6565 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 6566 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 6567 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 6568 */       return true;
/*      */     }
/* 6570 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 6571 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6576 */     PageContext pageContext = _jspx_page_context;
/* 6577 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6579 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6580 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 6581 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6583 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 6585 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 6586 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 6587 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 6588 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 6589 */       return true;
/*      */     }
/* 6591 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 6592 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\pingMonitor_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */