/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.server.framework.confparser.PreConfMonitorXMLParser;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
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
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
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
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.MultiboxTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.ResetTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.logic.EqualTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
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
/*      */ public final class newConfType_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   80 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   83 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   84 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   85 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   92 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   97 */     ArrayList list = null;
/*   98 */     StringBuffer sbf = new StringBuffer();
/*   99 */     ManagedApplication mo = new ManagedApplication();
/*  100 */     if (distinct)
/*      */     {
/*  102 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*  106 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*  109 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*  111 */       ArrayList row = (ArrayList)list.get(i);
/*  112 */       sbf.append("<option value='" + row.get(0) + "'>");
/*  113 */       if (distinct) {
/*  114 */         sbf.append(row.get(0));
/*      */       } else
/*  116 */         sbf.append(row.get(1));
/*  117 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  120 */     return sbf.toString(); }
/*      */   
/*  122 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  125 */     if (severity == null)
/*      */     {
/*  127 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  129 */     if (severity.equals("5"))
/*      */     {
/*  131 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  133 */     if (severity.equals("1"))
/*      */     {
/*  135 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  140 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  147 */     if (severity == null)
/*      */     {
/*  149 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  151 */     if (severity.equals("1"))
/*      */     {
/*  153 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  155 */     if (severity.equals("4"))
/*      */     {
/*  157 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  159 */     if (severity.equals("5"))
/*      */     {
/*  161 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  166 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  172 */     if (severity == null)
/*      */     {
/*  174 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  176 */     if (severity.equals("5"))
/*      */     {
/*  178 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  180 */     if (severity.equals("1"))
/*      */     {
/*  182 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  186 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  192 */     if (severity == null)
/*      */     {
/*  194 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  196 */     if (severity.equals("1"))
/*      */     {
/*  198 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  200 */     if (severity.equals("4"))
/*      */     {
/*  202 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  204 */     if (severity.equals("5"))
/*      */     {
/*  206 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  210 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  216 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  222 */     if (severity == 5)
/*      */     {
/*  224 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  226 */     if (severity == 1)
/*      */     {
/*  228 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  233 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  239 */     if (severity == null)
/*      */     {
/*  241 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  243 */     if (severity.equals("5"))
/*      */     {
/*  245 */       if (isAvailability) {
/*  246 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  249 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  252 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  254 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  256 */     if (severity.equals("1"))
/*      */     {
/*  258 */       if (isAvailability) {
/*  259 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  262 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  269 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  276 */     if (severity == null)
/*      */     {
/*  278 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  280 */     if (severity.equals("5"))
/*      */     {
/*  282 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  284 */     if (severity.equals("4"))
/*      */     {
/*  286 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  288 */     if (severity.equals("1"))
/*      */     {
/*  290 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  295 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  301 */     if (severity == null)
/*      */     {
/*  303 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  305 */     if (severity.equals("5"))
/*      */     {
/*  307 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  309 */     if (severity.equals("4"))
/*      */     {
/*  311 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  313 */     if (severity.equals("1"))
/*      */     {
/*  315 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  320 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  327 */     if (severity == null)
/*      */     {
/*  329 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  331 */     if (severity.equals("5"))
/*      */     {
/*  333 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  335 */     if (severity.equals("4"))
/*      */     {
/*  337 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  339 */     if (severity.equals("1"))
/*      */     {
/*  341 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  346 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  354 */     StringBuffer out = new StringBuffer();
/*  355 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  356 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  357 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  358 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  359 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  360 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  361 */     out.append("</tr>");
/*  362 */     out.append("</form></table>");
/*  363 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  370 */     if (val == null)
/*      */     {
/*  372 */       return "-";
/*      */     }
/*      */     
/*  375 */     String ret = FormatUtil.formatNumber(val);
/*  376 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  377 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  380 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  384 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  392 */     StringBuffer out = new StringBuffer();
/*  393 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  394 */     out.append("<tr>");
/*  395 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  397 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  399 */     out.append("</tr>");
/*  400 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  404 */       if (j % 2 == 0)
/*      */       {
/*  406 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  410 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  413 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  415 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  418 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  422 */       out.append("</tr>");
/*      */     }
/*  424 */     out.append("</table>");
/*  425 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  426 */     out.append("<tr>");
/*  427 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  428 */     out.append("</tr>");
/*  429 */     out.append("</table>");
/*  430 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  436 */     StringBuffer out = new StringBuffer();
/*  437 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  438 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  439 */     out.append("<tr>");
/*  440 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  441 */     out.append("<tr>");
/*  442 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  443 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  444 */     out.append("</tr>");
/*  445 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  448 */       out.append("<tr>");
/*  449 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  450 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  451 */       out.append("</tr>");
/*      */     }
/*      */     
/*  454 */     out.append("</table>");
/*  455 */     out.append("</table>");
/*  456 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  461 */     if (severity.equals("0"))
/*      */     {
/*  463 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  467 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  474 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  487 */     StringBuffer out = new StringBuffer();
/*  488 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  489 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  491 */       out.append("<tr>");
/*  492 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  493 */       out.append("</tr>");
/*      */       
/*      */ 
/*  496 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  498 */         String borderclass = "";
/*      */         
/*      */ 
/*  501 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  503 */         out.append("<tr>");
/*      */         
/*  505 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  506 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  507 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  513 */     out.append("</table><br>");
/*  514 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  515 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  517 */       List sLinks = secondLevelOfLinks[0];
/*  518 */       List sText = secondLevelOfLinks[1];
/*  519 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  522 */         out.append("<tr>");
/*  523 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  524 */         out.append("</tr>");
/*  525 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  527 */           String borderclass = "";
/*      */           
/*      */ 
/*  530 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  532 */           out.append("<tr>");
/*      */           
/*  534 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  535 */           if (sLinks.get(i).toString().length() == 0) {
/*  536 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  539 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  541 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  545 */     out.append("</table>");
/*  546 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  553 */     StringBuffer out = new StringBuffer();
/*  554 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  555 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  557 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  559 */         out.append("<tr>");
/*  560 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  561 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  565 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  567 */           String borderclass = "";
/*      */           
/*      */ 
/*  570 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  572 */           out.append("<tr>");
/*      */           
/*  574 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  575 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  576 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  579 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  582 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  587 */     out.append("</table><br>");
/*  588 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  589 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  591 */       List sLinks = secondLevelOfLinks[0];
/*  592 */       List sText = secondLevelOfLinks[1];
/*  593 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  596 */         out.append("<tr>");
/*  597 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  598 */         out.append("</tr>");
/*  599 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  601 */           String borderclass = "";
/*      */           
/*      */ 
/*  604 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  606 */           out.append("<tr>");
/*      */           
/*  608 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  609 */           if (sLinks.get(i).toString().length() == 0) {
/*  610 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  613 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  615 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  619 */     out.append("</table>");
/*  620 */     return out.toString();
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
/*  633 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  636 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  639 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  642 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  645 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  648 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  651 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  654 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  662 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  667 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  672 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  677 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  682 */     if (val != null)
/*      */     {
/*  684 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  688 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  693 */     if (val == null) {
/*  694 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  698 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  703 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  709 */     if (val != null)
/*      */     {
/*  711 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  715 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  721 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  726 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  730 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  735 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  740 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  745 */     String hostaddress = "";
/*  746 */     String ip = request.getHeader("x-forwarded-for");
/*  747 */     if (ip == null)
/*  748 */       ip = request.getRemoteAddr();
/*  749 */     InetAddress add = null;
/*  750 */     if (ip.equals("127.0.0.1")) {
/*  751 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  755 */       add = InetAddress.getByName(ip);
/*      */     }
/*  757 */     hostaddress = add.getHostName();
/*  758 */     if (hostaddress.indexOf('.') != -1) {
/*  759 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  760 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  764 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  769 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  775 */     if (severity == null)
/*      */     {
/*  777 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  779 */     if (severity.equals("5"))
/*      */     {
/*  781 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  783 */     if (severity.equals("1"))
/*      */     {
/*  785 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  790 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  795 */     ResultSet set = null;
/*  796 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  797 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  799 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  800 */       if (set.next()) { String str1;
/*  801 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  802 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  805 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  810 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  813 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  815 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  819 */     StringBuffer rca = new StringBuffer();
/*  820 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  821 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  824 */     int rcalength = key.length();
/*  825 */     String split = "6. ";
/*  826 */     int splitPresent = key.indexOf(split);
/*  827 */     String div1 = "";String div2 = "";
/*  828 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  830 */       if (rcalength > 180) {
/*  831 */         rca.append("<span class=\"rca-critical-text\">");
/*  832 */         getRCATrimmedText(key, rca);
/*  833 */         rca.append("</span>");
/*      */       } else {
/*  835 */         rca.append("<span class=\"rca-critical-text\">");
/*  836 */         rca.append(key);
/*  837 */         rca.append("</span>");
/*      */       }
/*  839 */       return rca.toString();
/*      */     }
/*  841 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  842 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  843 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  844 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  845 */     getRCATrimmedText(div1, rca);
/*  846 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  849 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  850 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  851 */     getRCATrimmedText(div2, rca);
/*  852 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  854 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  859 */     String[] st = msg.split("<br>");
/*  860 */     for (int i = 0; i < st.length; i++) {
/*  861 */       String s = st[i];
/*  862 */       if (s.length() > 180) {
/*  863 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  865 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  869 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  870 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  872 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  876 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  877 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  878 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  881 */       if (key == null) {
/*  882 */         return ret;
/*      */       }
/*      */       
/*  885 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  886 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  889 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  890 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  891 */       set = AMConnectionPool.executeQueryStmt(query);
/*  892 */       if (set.next())
/*      */       {
/*  894 */         String helpLink = set.getString("LINK");
/*  895 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  898 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  904 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  923 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  914 */         if (set != null) {
/*  915 */           AMConnectionPool.closeStatement(set);
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
/*  929 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  930 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  932 */       String entityStr = (String)keys.nextElement();
/*  933 */       String mmessage = temp.getProperty(entityStr);
/*  934 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  935 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  937 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  943 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  944 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  946 */       String entityStr = (String)keys.nextElement();
/*  947 */       String mmessage = temp.getProperty(entityStr);
/*  948 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  949 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  951 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  956 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  966 */     String des = new String();
/*  967 */     while (str.indexOf(find) != -1) {
/*  968 */       des = des + str.substring(0, str.indexOf(find));
/*  969 */       des = des + replace;
/*  970 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  972 */     des = des + str;
/*  973 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  980 */       if (alert == null)
/*      */       {
/*  982 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  984 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  986 */         return "&nbsp;";
/*      */       }
/*      */       
/*  989 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  991 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  994 */       int rcalength = test.length();
/*  995 */       if (rcalength < 300)
/*      */       {
/*  997 */         return test;
/*      */       }
/*      */       
/*      */ 
/* 1001 */       StringBuffer out = new StringBuffer();
/* 1002 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/* 1003 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/* 1004 */       out.append("</div>");
/* 1005 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/* 1006 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/* 1007 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1012 */       ex.printStackTrace();
/*      */     }
/* 1014 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1020 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1025 */     ArrayList attribIDs = new ArrayList();
/* 1026 */     ArrayList resIDs = new ArrayList();
/* 1027 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1029 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1031 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1033 */       String resourceid = "";
/* 1034 */       String resourceType = "";
/* 1035 */       if (type == 2) {
/* 1036 */         resourceid = (String)row.get(0);
/* 1037 */         resourceType = (String)row.get(3);
/*      */       }
/* 1039 */       else if (type == 3) {
/* 1040 */         resourceid = (String)row.get(0);
/* 1041 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1044 */         resourceid = (String)row.get(6);
/* 1045 */         resourceType = (String)row.get(7);
/*      */       }
/* 1047 */       resIDs.add(resourceid);
/* 1048 */       String healthid = AMAttributesCache.getHealthId(resourceType);
/* 1049 */       String availid = AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1051 */       String healthentity = null;
/* 1052 */       String availentity = null;
/* 1053 */       if (healthid != null) {
/* 1054 */         healthentity = resourceid + "_" + healthid;
/* 1055 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1058 */       if (availid != null) {
/* 1059 */         availentity = resourceid + "_" + availid;
/* 1060 */         entitylist.add(availentity);
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
/* 1074 */     Properties alert = getStatus(entitylist);
/* 1075 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1080 */     int size = monitorList.size();
/*      */     
/* 1082 */     String[] severity = new String[size];
/*      */     
/* 1084 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1086 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1087 */       String resourceName1 = (String)row1.get(7);
/* 1088 */       String resourceid1 = (String)row1.get(6);
/* 1089 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1090 */       if (severity[j] == null)
/*      */       {
/* 1092 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1096 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1098 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1100 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1103 */         if (sev > 0) {
/* 1104 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1105 */           monitorList.set(k, monitorList.get(j));
/* 1106 */           monitorList.set(j, t);
/* 1107 */           String temp = severity[k];
/* 1108 */           severity[k] = severity[j];
/* 1109 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1115 */     int z = 0;
/* 1116 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1119 */       int i = 0;
/* 1120 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1123 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1127 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1131 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1133 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1136 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1140 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1143 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1144 */       String resourceName1 = (String)row1.get(7);
/* 1145 */       String resourceid1 = (String)row1.get(6);
/* 1146 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1147 */       if (hseverity[j] == null)
/*      */       {
/* 1149 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1154 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1156 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1159 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1162 */         if (hsev > 0) {
/* 1163 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1164 */           monitorList.set(k, monitorList.get(j));
/* 1165 */           monitorList.set(j, t);
/* 1166 */           String temp1 = hseverity[k];
/* 1167 */           hseverity[k] = hseverity[j];
/* 1168 */           hseverity[j] = temp1;
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
/* 1180 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1181 */     boolean forInventory = false;
/* 1182 */     String trdisplay = "none";
/* 1183 */     String plusstyle = "inline";
/* 1184 */     String minusstyle = "none";
/* 1185 */     String haidTopLevel = "";
/* 1186 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1188 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1190 */         haidTopLevel = request.getParameter("haid");
/* 1191 */         forInventory = true;
/* 1192 */         trdisplay = "table-row;";
/* 1193 */         plusstyle = "none";
/* 1194 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1201 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1204 */     ArrayList listtoreturn = new ArrayList();
/* 1205 */     StringBuffer toreturn = new StringBuffer();
/* 1206 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1207 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1208 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1210 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1212 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1213 */       String childresid = (String)singlerow.get(0);
/* 1214 */       String childresname = (String)singlerow.get(1);
/* 1215 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1216 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1217 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1218 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1219 */       String unmanagestatus = (String)singlerow.get(5);
/* 1220 */       String actionstatus = (String)singlerow.get(6);
/* 1221 */       String linkclass = "monitorgp-links";
/* 1222 */       String titleforres = childresname;
/* 1223 */       String titilechildresname = childresname;
/* 1224 */       String childimg = "/images/trcont.png";
/* 1225 */       String flag = "enable";
/* 1226 */       String dcstarted = (String)singlerow.get(8);
/* 1227 */       String configMonitor = "";
/* 1228 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1229 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1231 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1233 */       if (singlerow.get(7) != null)
/*      */       {
/* 1235 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1237 */       String haiGroupType = "0";
/* 1238 */       if ("HAI".equals(childtype))
/*      */       {
/* 1240 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1242 */       childimg = "/images/trend.png";
/* 1243 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1244 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1245 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1247 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1249 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1251 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1252 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1255 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1257 */         linkclass = "disabledtext";
/* 1258 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1260 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1261 */       String availmouseover = "";
/* 1262 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1264 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1266 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1267 */       String healthmouseover = "";
/* 1268 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1270 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1273 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1274 */       int spacing = 0;
/* 1275 */       if (level >= 1)
/*      */       {
/* 1277 */         spacing = 40 * level;
/*      */       }
/* 1279 */       if (childtype.equals("HAI"))
/*      */       {
/* 1281 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1282 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1283 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1285 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1286 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1287 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1288 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1289 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1290 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1291 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1292 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1293 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1294 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1295 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1297 */         if (!forInventory)
/*      */         {
/* 1299 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1302 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1304 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1306 */           actions = editlink + actions;
/*      */         }
/* 1308 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1310 */           actions = actions + associatelink;
/*      */         }
/* 1312 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1313 */         String arrowimg = "";
/* 1314 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1316 */           actions = "";
/* 1317 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1318 */           checkbox = "";
/* 1319 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1321 */         if (isIt360)
/*      */         {
/* 1323 */           actionimg = "";
/* 1324 */           actions = "";
/* 1325 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1326 */           checkbox = "";
/*      */         }
/*      */         
/* 1329 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1331 */           actions = "";
/*      */         }
/* 1333 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1335 */           checkbox = "";
/*      */         }
/*      */         
/* 1338 */         String resourcelink = "";
/*      */         
/* 1340 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1342 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1346 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1349 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1350 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1351 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1352 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1353 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1354 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1355 */         if (!isIt360)
/*      */         {
/* 1357 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1361 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1364 */         toreturn.append("</tr>");
/* 1365 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1367 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1368 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1372 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1373 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1376 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1380 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1382 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1383 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1384 */             toreturn.append(assocMessage);
/* 1385 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1386 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1387 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1388 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1394 */         String resourcelink = null;
/* 1395 */         boolean hideEditLink = false;
/* 1396 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1398 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1399 */           hideEditLink = true;
/* 1400 */           if (isIt360)
/*      */           {
/* 1402 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1406 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1408 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1410 */           hideEditLink = true;
/* 1411 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1412 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1417 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1420 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1421 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1422 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1423 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1424 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1425 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1426 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1427 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1428 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1429 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1430 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1431 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1432 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1434 */         if (hideEditLink)
/*      */         {
/* 1436 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1438 */         if (!forInventory)
/*      */         {
/* 1440 */           removefromgroup = "";
/*      */         }
/* 1442 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1443 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1444 */           actions = actions + configcustomfields;
/*      */         }
/* 1446 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1448 */           actions = editlink + actions;
/*      */         }
/* 1450 */         String managedLink = "";
/* 1451 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1453 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1454 */           actions = "";
/* 1455 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1456 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1459 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1461 */           checkbox = "";
/*      */         }
/*      */         
/* 1464 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1466 */           actions = "";
/*      */         }
/* 1468 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1469 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1470 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1471 */         if (isIt360)
/*      */         {
/* 1473 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1477 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1479 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1480 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1481 */         if (!isIt360)
/*      */         {
/* 1483 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1487 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1489 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1492 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1499 */       StringBuilder toreturn = new StringBuilder();
/* 1500 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1501 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1502 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1503 */       String title = "";
/* 1504 */       message = EnterpriseUtil.decodeString(message);
/* 1505 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1506 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1507 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1509 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1511 */       else if ("5".equals(severity))
/*      */       {
/* 1513 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1517 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1519 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1520 */       toreturn.append(v);
/*      */       
/* 1522 */       toreturn.append(link);
/* 1523 */       if (severity == null)
/*      */       {
/* 1525 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1527 */       else if (severity.equals("5"))
/*      */       {
/* 1529 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1531 */       else if (severity.equals("4"))
/*      */       {
/* 1533 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1535 */       else if (severity.equals("1"))
/*      */       {
/* 1537 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1542 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1544 */       toreturn.append("</a>");
/* 1545 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1549 */       ex.printStackTrace();
/*      */     }
/* 1551 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1558 */       StringBuilder toreturn = new StringBuilder();
/* 1559 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1560 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1561 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1562 */       if (message == null)
/*      */       {
/* 1564 */         message = "";
/*      */       }
/*      */       
/* 1567 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1568 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1570 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1571 */       toreturn.append(v);
/*      */       
/* 1573 */       toreturn.append(link);
/*      */       
/* 1575 */       if (severity == null)
/*      */       {
/* 1577 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1579 */       else if (severity.equals("5"))
/*      */       {
/* 1581 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1583 */       else if (severity.equals("1"))
/*      */       {
/* 1585 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1590 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1592 */       toreturn.append("</a>");
/* 1593 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1599 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1602 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1603 */     if (invokeActions != null) {
/* 1604 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1605 */       while (iterator.hasNext()) {
/* 1606 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1607 */         if (actionmap.containsKey(actionid)) {
/* 1608 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1613 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1617 */     String actionLink = "";
/* 1618 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1619 */     String query = "";
/* 1620 */     ResultSet rs = null;
/* 1621 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1622 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1623 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1624 */       actionLink = "method=" + methodName;
/*      */     }
/* 1626 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1627 */       actionLink = methodName;
/*      */     }
/* 1629 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1630 */     Iterator itr = methodarglist.iterator();
/* 1631 */     boolean isfirstparam = true;
/* 1632 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1633 */     while (itr.hasNext()) {
/* 1634 */       HashMap argmap = (HashMap)itr.next();
/* 1635 */       String argtype = (String)argmap.get("TYPE");
/* 1636 */       String argname = (String)argmap.get("IDENTITY");
/* 1637 */       String paramname = (String)argmap.get("PARAMETER");
/* 1638 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1639 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1640 */         isfirstparam = false;
/* 1641 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1643 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1647 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1651 */         actionLink = actionLink + "&";
/*      */       }
/* 1653 */       String paramValue = null;
/* 1654 */       String tempargname = argname;
/* 1655 */       if (commonValues.getProperty(tempargname) != null) {
/* 1656 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1659 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1660 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1661 */           if (dbType.equals("mysql")) {
/* 1662 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1665 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1667 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1669 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1670 */             if (rs.next()) {
/* 1671 */               paramValue = rs.getString("VALUE");
/* 1672 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1676 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1680 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1683 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1688 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1689 */           paramValue = rowId;
/*      */         }
/* 1691 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1692 */           paramValue = managedObjectName;
/*      */         }
/* 1694 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1695 */           paramValue = resID;
/*      */         }
/* 1697 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1698 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1701 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1703 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1704 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1705 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1707 */     return actionLink;
/*      */   }
/*      */   
/* 1710 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1711 */     String dependentAttribute = null;
/* 1712 */     String align = "left";
/*      */     
/* 1714 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1715 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1716 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1717 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1718 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1719 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1720 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1721 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1722 */       align = "center";
/*      */     }
/*      */     
/* 1725 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1726 */     String actualdata = "";
/*      */     
/* 1728 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1729 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1730 */         actualdata = availValue;
/*      */       }
/* 1732 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1733 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1737 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1738 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1741 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1747 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1748 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1749 */       toreturn.append("<table>");
/* 1750 */       toreturn.append("<tr>");
/* 1751 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1752 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1753 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1754 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1755 */         String toolTip = "";
/* 1756 */         String hideClass = "";
/* 1757 */         String textStyle = "";
/* 1758 */         boolean isreferenced = true;
/* 1759 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1760 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1761 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1762 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1764 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1765 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1766 */           while (valueList.hasMoreTokens()) {
/* 1767 */             String dependentVal = valueList.nextToken();
/* 1768 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1769 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1770 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1772 */               toolTip = "";
/* 1773 */               hideClass = "";
/* 1774 */               isreferenced = false;
/* 1775 */               textStyle = "disabledtext";
/* 1776 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1780 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1781 */           toolTip = "";
/* 1782 */           hideClass = "";
/* 1783 */           isreferenced = false;
/* 1784 */           textStyle = "disabledtext";
/* 1785 */           if (dependentImageMap != null) {
/* 1786 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1787 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1790 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1794 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1795 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1796 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1797 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1798 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1799 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1801 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1802 */           if (isreferenced) {
/* 1803 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1807 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1808 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1809 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1810 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1811 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1812 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1814 */           toreturn.append("</span>");
/* 1815 */           toreturn.append("</a>");
/* 1816 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1819 */       toreturn.append("</tr>");
/* 1820 */       toreturn.append("</table>");
/* 1821 */       toreturn.append("</td>");
/*      */     } else {
/* 1823 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1826 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1830 */     String colTime = null;
/* 1831 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1832 */     if ((rows != null) && (rows.size() > 0)) {
/* 1833 */       Iterator<String> itr = rows.iterator();
/* 1834 */       String maxColQuery = "";
/* 1835 */       for (;;) { if (itr.hasNext()) {
/* 1836 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1837 */           ResultSet maxCol = null;
/*      */           try {
/* 1839 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1840 */             while (maxCol.next()) {
/* 1841 */               if (colTime == null) {
/* 1842 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1845 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1854 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1856 */               if (maxCol != null)
/* 1857 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1859 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1854 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1856 */               if (maxCol != null)
/* 1857 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1859 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1864 */     return colTime;
/*      */   }
/*      */   
/* 1867 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1868 */     tablename = null;
/* 1869 */     ResultSet rsTable = null;
/* 1870 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1872 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1873 */       while (rsTable.next()) {
/* 1874 */         tablename = rsTable.getString("DATATABLE");
/* 1875 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1876 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1889 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1880 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1883 */         if (rsTable != null)
/* 1884 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1886 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1892 */     String argsList = "";
/* 1893 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1895 */       if (showArgsMap.get(row) != null) {
/* 1896 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1897 */         if (showArgslist != null) {
/* 1898 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1899 */             if (argsList.trim().equals("")) {
/* 1900 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1903 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1910 */       e.printStackTrace();
/* 1911 */       return "";
/*      */     }
/* 1913 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1918 */     String argsList = "";
/* 1919 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1922 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1924 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1925 */         if (hideArgsList != null)
/*      */         {
/* 1927 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1929 */             if (argsList.trim().equals(""))
/*      */             {
/* 1931 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1935 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1943 */       ex.printStackTrace();
/*      */     }
/* 1945 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1949 */     StringBuilder toreturn = new StringBuilder();
/* 1950 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1957 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1958 */       Iterator itr = tActionList.iterator();
/* 1959 */       while (itr.hasNext()) {
/* 1960 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1961 */         String confirmmsg = "";
/* 1962 */         String link = "";
/* 1963 */         String isJSP = "NO";
/* 1964 */         HashMap tactionMap = (HashMap)itr.next();
/* 1965 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1966 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1967 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1968 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1969 */           (actionmap.containsKey(actionId))) {
/* 1970 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1971 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1972 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1973 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1974 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1976 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1982 */           if (isTableAction) {
/* 1983 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1986 */             tableName = "Link";
/* 1987 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1988 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1989 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1990 */             toreturn.append("</a></td>");
/*      */           }
/* 1992 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1993 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1994 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1995 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2001 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 2007 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 2009 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 2010 */       Properties prop = (Properties)node.getUserObject();
/* 2011 */       String mgID = prop.getProperty("label");
/* 2012 */       String mgName = prop.getProperty("value");
/* 2013 */       String isParent = prop.getProperty("isParent");
/* 2014 */       int mgIDint = Integer.parseInt(mgID);
/* 2015 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 2017 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2019 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2020 */       if (node.getChildCount() > 0)
/*      */       {
/* 2022 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2024 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2026 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2028 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2032 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2037 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2039 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2041 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2043 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2047 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2050 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2051 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2053 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2057 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2059 */       if (node.getChildCount() > 0)
/*      */       {
/* 2061 */         builder.append("<UL>");
/* 2062 */         printMGTree(node, builder);
/* 2063 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2068 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2069 */     StringBuffer toReturn = new StringBuffer();
/* 2070 */     String table = "-";
/*      */     try {
/* 2072 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2073 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2074 */       float total = 0.0F;
/* 2075 */       while (it.hasNext()) {
/* 2076 */         String attName = (String)it.next();
/* 2077 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2078 */         boolean roundOffData = false;
/* 2079 */         if ((data != null) && (!data.equals(""))) {
/* 2080 */           if (data.indexOf(",") != -1) {
/* 2081 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2084 */             float value = Float.parseFloat(data);
/* 2085 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2088 */             total += value;
/* 2089 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2092 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2097 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2098 */       while (attVsWidthList.hasNext()) {
/* 2099 */         String attName = (String)attVsWidthList.next();
/* 2100 */         String data = (String)attVsWidthProps.get(attName);
/* 2101 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2102 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2103 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2104 */         String className = (String)graphDetails.get("ClassName");
/* 2105 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2106 */         if (percentage < 1.0F)
/*      */         {
/* 2108 */           data = percentage + "";
/*      */         }
/* 2110 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2112 */       if (toReturn.length() > 0) {
/* 2113 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2117 */       e.printStackTrace();
/*      */     }
/* 2119 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2125 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2126 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2127 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2128 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2129 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2130 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2131 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2132 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2133 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2136 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2137 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2138 */       splitvalues[0] = multiplecondition.toString();
/* 2139 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2142 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2147 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2148 */     if (thresholdType != 3) {
/* 2149 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2150 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2151 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2152 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2153 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2154 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2156 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2157 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2158 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2159 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2160 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2161 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2163 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2164 */     if (updateSelected != null) {
/* 2165 */       updateSelected[0] = "selected";
/*      */     }
/* 2167 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2172 */       StringBuffer toreturn = new StringBuffer("");
/* 2173 */       if (commaSeparatedMsgId != null) {
/* 2174 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2175 */         int count = 0;
/* 2176 */         while (msgids.hasMoreTokens()) {
/* 2177 */           String id = msgids.nextToken();
/* 2178 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2179 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2180 */           count++;
/* 2181 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2182 */             if (toreturn.length() == 0) {
/* 2183 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2185 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2186 */             if (!image.trim().equals("")) {
/* 2187 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2189 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2190 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2193 */         if (toreturn.length() > 0) {
/* 2194 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2198 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2201 */       e.printStackTrace(); }
/* 2202 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2208 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2214 */   private static Map<String, Long> _jspx_dependants = new HashMap(5);
/* 2215 */   static { _jspx_dependants.put("/jsp/includes/newresourcetypes.jspf", Long.valueOf(1473429417000L));
/* 2216 */     _jspx_dependants.put("/jsp/includes/agentLocations.jspf", Long.valueOf(1473429417000L));
/* 2217 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2218 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/* 2219 */     _jspx_dependants.put("/jsp/includes/MonitorDiscoveryStatus.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparam;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2258 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2262 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2266 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2267 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2268 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2269 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2270 */     this._005fjspx_005ftagPool_005ffmt_005fparam = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2271 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2272 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2273 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2274 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2275 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2276 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2277 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2278 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2279 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2280 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2281 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2282 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2283 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2284 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2285 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2286 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2287 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2288 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2289 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2290 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2291 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2292 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2293 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2294 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2298 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2299 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2300 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2301 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2302 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2303 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2304 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.release();
/* 2305 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.release();
/* 2306 */     this._005fjspx_005ftagPool_005ffmt_005fparam.release();
/* 2307 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2308 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2309 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2310 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2311 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2312 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.release();
/* 2313 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
/* 2314 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2315 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2316 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2317 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2318 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2319 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/* 2320 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2321 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2322 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/* 2323 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick.release();
/* 2324 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.release();
/* 2325 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled.release();
/* 2326 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
/* 2327 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.release();
/* 2328 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.release();
/*      */   }
/*      */   
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*      */     ;
/* 2335 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2338 */     JspWriter out = null;
/* 2339 */     Object page = this;
/* 2340 */     JspWriter _jspx_out = null;
/* 2341 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2345 */       response.setContentType("text/html;charset=UTF-8");
/* 2346 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2348 */       _jspx_page_context = pageContext;
/* 2349 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2350 */       ServletConfig config = pageContext.getServletConfig();
/* 2351 */       session = pageContext.getSession();
/* 2352 */       out = pageContext.getOut();
/* 2353 */       _jspx_out = out;
/*      */       
/* 2355 */       out.write("<!DOCTYPE html>\n");
/* 2356 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2357 */       out.write(10);
/*      */       
/* 2359 */       request.setAttribute("HelpKey", request.getParameter("type"));
/*      */       
/* 2361 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2362 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2364 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2365 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2366 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2368 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2370 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2372 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2374 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2375 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2376 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2377 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2380 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2381 */         String available = null;
/* 2382 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2383 */         out.write(10);
/*      */         
/* 2385 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2386 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2387 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2389 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2391 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2393 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2395 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2396 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2397 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2398 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2401 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2402 */           String unavailable = null;
/* 2403 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2404 */           out.write(10);
/*      */           
/* 2406 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2407 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2408 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2410 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2412 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2414 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2416 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2417 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2418 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2419 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2422 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2423 */             String unmanaged = null;
/* 2424 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2425 */             out.write(10);
/*      */             
/* 2427 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2428 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2429 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2431 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2433 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2435 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2437 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2438 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2439 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2440 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2443 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2444 */               String scheduled = null;
/* 2445 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2446 */               out.write(10);
/*      */               
/* 2448 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2449 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2450 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2452 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2454 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2456 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2458 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2459 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2460 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2461 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2464 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2465 */                 String critical = null;
/* 2466 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2467 */                 out.write(10);
/*      */                 
/* 2469 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2470 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2471 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2473 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2475 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2477 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2479 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2480 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2481 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2482 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2485 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2486 */                   String clear = null;
/* 2487 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2488 */                   out.write(10);
/*      */                   
/* 2490 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2491 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2492 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2494 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2496 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2498 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2500 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2501 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2502 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2503 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2506 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2507 */                     String warning = null;
/* 2508 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2509 */                     out.write(10);
/* 2510 */                     out.write(10);
/*      */                     
/* 2512 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2513 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2515 */                     out.write(10);
/* 2516 */                     out.write(10);
/* 2517 */                     out.write(10);
/* 2518 */                     out.write("\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/hostdiscoveryform.js\"></SCRIPT>\n<SCRIPT LANGAUGE =\"javascript\" SRC=\"../template/appmanager.js\" ></SCRIPT>\n");
/* 2519 */                     out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2520 */                     out.write(10);
/* 2521 */                     JspRuntimeLibrary.include(request, response, "/jsp/includes/monitorGroups.jsp", out, false);
/* 2522 */                     out.write(10);
/*      */                     
/* 2524 */                     PreConfMonitorXMLParser preConfXMLParser = new PreConfMonitorXMLParser();
/* 2525 */                     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/* 2526 */                     if (hideFieldsForIT360 == null)
/*      */                     {
/* 2528 */                       hideFieldsForIT360 = (String)request.getAttribute("hideFieldsForIT360");
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2534 */                     boolean hideFields = false;
/* 2535 */                     String hideStyle = "";
/* 2536 */                     if ((hideFieldsForIT360 != null) && (hideFieldsForIT360.equals("true")))
/*      */                     {
/* 2538 */                       hideFields = true;
/* 2539 */                       hideStyle = "hideContent";
/* 2540 */                       request.setAttribute("hideFieldsForIT360", hideFieldsForIT360);
/*      */                     }
/* 2542 */                     ArrayList args = (ArrayList)request.getAttribute("args");
/* 2543 */                     ArrayList args_name = (ArrayList)args.get(0);
/* 2544 */                     pageContext.setAttribute("args_name", args_name);
/* 2545 */                     ArrayList args_type = (ArrayList)args.get(1);
/* 2546 */                     ArrayList args_dimension = (ArrayList)args.get(2);
/* 2547 */                     ArrayList args_mandataory = (ArrayList)args.get(3);
/* 2548 */                     HashMap hm = (HashMap)args.get(4);
/* 2549 */                     ArrayList args_disp = (ArrayList)args.get(5);
/* 2550 */                     ArrayList args_default = (ArrayList)args.get(6);
/* 2551 */                     ArrayList args_Tooltip = (ArrayList)args.get(7);
/* 2552 */                     ArrayList args_ShowOnCondition = (ArrayList)args.get(8);
/* 2553 */                     HashMap showArgsMap = (HashMap)args.get(9);
/* 2554 */                     ArrayList args_colSpan = (ArrayList)args.get(10);
/* 2555 */                     HashMap onChangeMethod = (HashMap)args.get(11);
/* 2556 */                     HashMap hideArgsMap = (HashMap)args.get(12);
/* 2557 */                     ArrayList args_sideNote = (ArrayList)args.get(13);
/* 2558 */                     String testCredStyle = (String)args.get(14);
/* 2559 */                     HashMap onChangeArgs = (HashMap)args.get(15);
/*      */                     
/* 2561 */                     String haid = request.getParameter("haid");
/*      */                     
/* 2563 */                     String resourceid = "";
/* 2564 */                     String resourceName = (String)request.getAttribute("resourcename");
/* 2565 */                     String addmonitors = FormatUtil.getString("am.webclient.newscript.addmonitors.text");
/* 2566 */                     String restoredefaults = FormatUtil.getString("am.webclient.global.Reset.text");
/* 2567 */                     String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/* 2568 */                     String isAgentEnabled = "NO";
/* 2569 */                     Properties discoverPageUIDetails = ConfMonitorConfiguration.getInstance().getTypeDescription(request.getParameter("type"));
/* 2570 */                     AMActionForm form1 = (AMActionForm)request.getAttribute("AMActionForm");
/* 2571 */                     String monTitle = request.getParameter("type");
/* 2572 */                     if (monTitle == null)
/*      */                     {
/* 2574 */                       monTitle = (String)request.getAttribute("type");
/*      */                     }
/* 2576 */                     if (monTitle.equals("SYSTEM:9999"))
/*      */                     {
/* 2578 */                       monTitle = form1.getOs();
/*      */                     }
/* 2580 */                     String isPreConfMonitor = preConfXMLParser.getPreConfMonitorListSupported().containsKey(monTitle) ? "true" : "false";
/* 2581 */                     pageContext.setAttribute("isPreConfMonitor", isPreConfMonitor);
/* 2582 */                     String serverOS = System.getProperty("os.name");
/* 2583 */                     serverOS = serverOS.toLowerCase().indexOf("windows") != -1 ? "windows" : "linux";
/* 2584 */                     String isAgentAssociated = request.getAttribute("isAgentAssociated") != null ? (String)request.getAttribute("isAgentAssociated") : "false";
/* 2585 */                     boolean isAssociatedtoLocal = request.getAttribute("isAssociatedtoLocal") != null ? ((Boolean)request.getAttribute("isAssociatedtoLocal")).booleanValue() : true;
/* 2586 */                     if ((discoverPageUIDetails != null) && (discoverPageUIDetails.getProperty("IS-AGENT-ENABLED") != null)) {
/* 2587 */                       isAgentEnabled = discoverPageUIDetails.getProperty("IS-AGENT-ENABLED");
/*      */                     }
/*      */                     
/* 2590 */                     pageContext.setAttribute("isAgentEnabled", isAgentEnabled);
/* 2591 */                     if (request.getParameter("resourceid") != null)
/*      */                     {
/* 2593 */                       resourceid = request.getParameter("resourceid");
/*      */                     }
/* 2595 */                     Properties argsasprops = new Properties();
/*      */                     
/* 2597 */                     if (request.getAttribute("argsasprops") != null)
/*      */                     {
/* 2599 */                       argsasprops = (Properties)request.getAttribute("argsasprops");
/*      */                     }
/*      */                     
/*      */ 
/* 2603 */                     Iterator aitr = args_disp.iterator();
/* 2604 */                     int argDisplaysize = 25;
/* 2605 */                     String argDisplayWidth = "25%";
/* 2606 */                     String fromWhere = "CONF";
/* 2607 */                     while (aitr.hasNext())
/*      */                     {
/* 2609 */                       int tempsize = FormatUtil.getString((String)aitr.next()).length();
/* 2610 */                       if (tempsize > argDisplaysize) {
/* 2611 */                         argDisplaysize = tempsize;
/*      */                       }
/*      */                     }
/*      */                     
/* 2615 */                     if ((argDisplaysize > 25) && (argDisplaysize < 35)) {
/* 2616 */                       argDisplayWidth = "25%";
/*      */                     }
/* 2618 */                     else if (argDisplaysize > 35) {
/* 2619 */                       argDisplayWidth = "25%";
/*      */                     }
/*      */                     
/* 2622 */                     out.write("\n\n\n<script type=\"text/javascript\">\nvar noOfAgents=1;\nfunction loadSite()\n{\n    document.AMActionForm.haid.options.length=0;\n    var formCustomerId = document.AMActionForm.organization.value;\n    var siteName;\n    var siteId;\n    var customerId;\n    var rowCount=0;\n    document.AMActionForm.haid.options[rowCount++] = new Option('-Select Site-','-'); //No I18N\n    ");
/* 2623 */                     if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*      */                       return;
/* 2625 */                     out.write("\n}\n\nfunction validateAndPerformTestCredential()\n{\n    if(!formValidation())\n    {\n        return;\n    }\n    var cacheid = (new Date()).getTime();\n    var str = $(\"form\").serialize(); //No I18N\n    var type = '");
/* 2626 */                     out.print(request.getParameter("type"));
/* 2627 */                     out.write("'; //No I18N\n    var dataString = \"&method=testCredentialForConfMonitors&cacheid=\"+cacheid+\"&type=\"+type+\"&serializedData=\"+str; //No I18N\n    $(\"#testCredentialResult\").show(); //No I18N\n     $(\"#testCredentialResult\").css(\"color\",\"blue\"); //No I18N\n    var waitString = '");
/* 2628 */                     out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.pleasewait"));
/* 2629 */                     out.write("';\n     $(\"#testCredentialResult\").html(\"<font size=2>\"+waitString+\"</font>\"); //No I18N\n     $(\"#testCredentialResult\").append(\"<img src=\\\"/images/LoadingTC.gif\\\"/>\"); //No I18N\n\n      $.ajax({\n                 type: \"POST\", //No I18N\n             url: \"/testCredential.do\", // Action URL //No I18N\n             data: dataString,                                                        // Query String parameters\n        success: function(response) {\n                                    $(\"#testCredentialResult\").html(response);        // Set response into particular div ID .. //No I18N\n             }\n     });\n}\n\nfunction toggleDNS() {\n $(\"#resolveDNSDiv\").toggle(); //No I18N\n\n}\n\nfunction toggleCredSSP() {\n $(\"#CredSSPDiv\").toggle(); //No I18N\n}\n\nfunction showSubnetMessage() {\n\n    alert('");
/* 2630 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.subnetmask.alert.whatisthis"));
/* 2631 */                     out.write("'); //No I18N\n\n}\n\nfunction checkOS(){\n        if(");
/* 2632 */                     out.print(!EnterpriseUtil.isAdminServer());
/* 2633 */                     out.write(" && '");
/* 2634 */                     out.print(serverOS);
/* 2635 */                     out.write("' !== 'windows'){\n            $(\"#mode option[value='WMI']\").remove();\n        }        \n}\n\nfunction onChangeOS()\n{\n    checkOS();\n    onChangeMode();\n\n}\n\nfunction hideArgs(argsToPerform) {\n    if (argsToPerform === undefined || argsToPerform === \"\") {\n        return;\n    }\n    var argsInArray = argsToPerform.split(\",\");    \n    for (var i = 0; i < argsInArray.length; i++) {\n        $(\"#\" + argsInArray[i].trim() + \"Div\").css(\"display\", \"none\"); //No I18N\n        var select = $(\"#\" + argsInArray[i].trim()); //No I18N\n        if(select !== 'undefined' && select.length > 0){ //No I18N\n            select.val($(\"#\" + argsInArray[i].trim() + \" option:first\").val()); //No I18N\n        }\n    }\n}\n\nfunction showArgs(argsToPerform) {\n    if (argsToPerform === undefined || argsToPerform === \"\") {\n        return;\n    }\n    var argsInArray = argsToPerform.split(\",\");\n    for (var i = 0; i < argsInArray.length; i++) {\n        $(\"#\" + argsInArray[i].trim() + \"Div\").css({'display':'table-row'}); //No I18N\n\n    }\n}\n\nfunction showAsPerTomcatVersion() {\n");
/* 2636 */                     out.write("    var tomcatVersion = $('select[name=version]').val(); //No I18N\n    if (tomcatVersion > 4) {\n        showArgs('username,password,tomcatmanagerurl'); //No I18N\n        $(\"#message5\").show(\"slow\"); //No I18N\n        $(\"#message3\").hide(\"slow\"); //No I18N\n    }\n    if (tomcatVersion === 3 || tomcatVersion === 4) {\n        hideArgs('username,password,tomcatmanagerurl'); //No I18N\n        $(\"#message3\").show(\"slow\"); //No I18N\n        $(\"#message5\").hide(\"slow\"); //No I18N\n    }\n    if (tomcatVersion === 0) {\n        hideArgs('username,password,tomcatmanagerurl'); //No I18N\n        $(\"#message3\").hide(\"slow\"); //No I18N\n        $(\"#message5\").hide(\"slow\"); //No I18N\n    }\n}\n\nfunction onChangeMode()\n{    \n    var selectedValue = $('select[name=mode]').val(); //No I18N    \n    var toHideArgs = 'username,password,prompt,cmTelnetValue,cmSSHValue,cmSNMPValue,cmWMIValue,PrivateKey,snmpCommunityString,sshPKAuth,passphrase,snmpSecurityLevel,snmpUserName,snmpContextName,snmpAuthPassword,snmpAuthProtocol,snmpPrivPassword,snmpVersionValue,eventlog_status'; //No I18N\n");
/* 2637 */                     out.write("    hideArgs(toHideArgs);    \n    var toShow=\"\",toHide=\"\";    //No I18N\n\n    if(selectedValue === 'TELNET')\n    {\n        $(\"#TelnetCredentialDetailsDiv\").show();  //No I18N\n        $(\"input:radio[value=nocmTelnet]\").attr('checked',true); //No I18N\n        $(\"#snmptelnetport\").val(\"23\"); //No I18N\n    $(\"#timeout\").val(\"40\"); //No I18N\n        $(\"#prompt\").val(\"$\");\n        toHide = 'SSHCredentialDetails,SNMPCredentialDetails,WMICredentialDetails,sshPKAuth,passphrase,snmpPort,eventlog_status'; //No I18N\n        toShow = 'username,password,prompt,snmptelnetport'; //No I18N\n    }\n    if(selectedValue === 'SNMP')\n    {\n        $(\"#SNMPCredentialDetailsDiv\").show(); //No I18N\n        $(\"input:radio[value=nocmSNMP]\").attr('checked',true); //No I18N\n        toHide ='SSHCredentialDetails,TelnetCredentialDetails,WMICredentialDetails,sshPKAuth,passphrase,snmptelnetport,eventlog_status'; //No I18N\n        toShow = 'snmpPort,snmpCommunityString,snmpVersionValue'; //No I18N\n        $(\"input:radio[value=v1v2]\").attr('checked',true); //No I18N\n");
/* 2638 */                     out.write("    $(\"#timeout\").val(\"5\"); //No I18N\n    }\n    if(selectedValue === 'SSH')\n    {\n        $(\"#SSHCredentialDetailsDiv\").show(); //No I18N\n        $(\"input:radio[value=nocmSSH]\").attr('checked',true); //No I18N\n        $(\"#snmptelnetport\").val(\"22\"); //No I18N\n        toHide = 'SNMPCredentialDetails,TelnetCredentialDetails,WMICredentialDetails,snmpPort,eventlog_status'; //No I18N\n        toShow = 'username,password,sshPKAuth,prompt,snmptelnetport'; //No I18N\n    $(\"#timeout\").val(\"40\"); //No I18N\n        $(\"#prompt\").val(\"$\");\n    }\n    if(selectedValue === 'WMI')\n    {\n        $(\"#WMICredentialDetailsDiv\").show(); //No I18N\n        toHide = 'SSHCredentialDetails,TelnetCredentialDetails,SNMPCredentialDetails,sshPKAuth,passphrase,snmptelnetport,snmpPort,snmpCommunityString'; //No I18N\n        toShow = 'username,password,eventlog_status'; //No I18N\n        $(\"#timeout\").val(\"300\"); //No I18N\n    }        \n    showArgs(toShow);\n    hideArgs(toHide);    \n}\n\nfunction showSecurityLevelProps()\n{\n                var toShow = \"\";\n");
/* 2639 */                     out.write("                var toHide = \"\";\n    snmpSecurityLevel = $('select[name=snmpSecurityLevel]').val();\n    if (snmpSecurityLevel === 'NOAUTHNOPRIV') {\n                    toShow = \"snmpUserName,snmpContextName\"; //No I18N\n                    toHide = \"snmpAuthPassword,snmpAuthProtocol,snmpPrivPassword\"; //No I18N\n             }\n    if (snmpSecurityLevel === 'AUTHNOPRIV') {\n                 toShow = \"snmpUserName,snmpContextName,snmpAuthPassword,snmpAuthProtocol\"; //No I18N\n                     toHide = \"snmpPrivPassword\"; //No I18N\n             }\n    if (snmpSecurityLevel === 'AUTHPRIV') {\n                 toShow = \"snmpUserName,snmpContextName,snmpAuthPassword,snmpAuthProtocol,snmpPrivPassword\"; //No I18N\n                     toHide = \"\"; //No I18N\n             }\n              hideArgs(toHide);\n              showArgs(toShow);\n}\n\nfunction showHideArgument(chkBox, argumentList, hideArgsList, editOnLoad) \n{\n        var hideArgs = \"\";\n    if (argumentList !== null && argumentList !== \"\") {\n        var argnames=argumentList.split(\",\");\n");
/* 2640 */                     out.write("        if (hideArgsList != null && hideArgsList !== \"\") {\n                    hideArgs = hideArgsList.split(\",\");\n                }\n        if (editOnLoad === \"true\" || chkBox.checked === true) {\n            for(var k=0;k<argnames.length;k++){                                \n                $(\"#\"+argnames[k]+\"Div\").css({'display':'table-row'}); //No I18N\n                                }\n            for(var b=0; b < hideArgs.length; b++){           \n                $(\"#\"+hideArgs[b]+\"Div\").hide();            \n\n        }\n        } else {\n            for(var k=0;k<argnames.length;k++){ \n                $(\"#\"+argnames[k]+\"Div\").hide();\n            }\n            for(var b=0; b < hideArgs.length; b++){\n                 $(\"#\"+hideArgs[b]+\"Div\").css({'display':'table-row'}); //No I18N\n    }\n}\n    }\n}\n    \nfunction onChangeToExecute(rowName)\n{    \n    cacheid = (new Date()).getTime();\n    var valueSelected = $('select[name='+rowName+']').val(); //No I18N\n    var type = '");
/* 2641 */                     out.print(request.getParameter("type"));
/* 2642 */                     out.write("'; //No I18N\n    if(type == 'null') {\n        type = $('#montype').val();\n    }\n    var dataString = \"&method=getDropDownValues&rowName=\"+rowName+\"&selectedOption=\"+valueSelected+\"&cacheid=\"+cacheid+\"&type=\"+type; //No I18N\n    var toShowArgs = \"\"; //No I18N\n    var toHideArgs = \"\"; //No I18N\n    $.ajax({\n         type:\"POST\", //No I18N\n                url:\"/manageConfMons.do\", //No I18N\n                data:dataString,\n        success: function(response) {\n                    toShowArgs = response.toShowArgs;\n                    toHideArgs = response.toHideArgs;\n                    showArgs(toShowArgs);\n                    hideArgs(toHideArgs);                    \n                }\n    });   \n}\n\nfunction goBack() \n{\n    var appendHash='");
/* 2643 */                     out.print(request.getAttribute("appendHash") != null ? (String)request.getAttribute("appendHash") : "");
/* 2644 */                     out.write("';//No I18N \n    var resId='");
/* 2645 */                     out.print(resourceid);
/* 2646 */                     out.write("';//No I18N \n    var fromdetails='");
/* 2647 */                     out.print(request.getParameter("tabId") != null ? "true" : "false");
/* 2648 */                     out.write("';//No I18N\n        var editMethod = '");
/* 2649 */                     out.print(request.getParameter("method") != null ? "true" : "false");
/* 2650 */                     out.write("';//No I18N\n    var hreference = \"/showresource.do?method=showResourceTypes&direct=true&network=\" + '");
/* 2651 */                     out.print(request.getParameter("type"));
/* 2652 */                     out.write("' + \"&detailspage=true\"; //No I18N \n    if (appendHash.trim() !== \"\") { //No I18N \n        hreference = \"/showresource.do?resourceid=\" + resId + \"&method=showResourceForResourceID\" + appendHash; //No I18N      \n    }\n    else if (fromdetails === \"true\" || editMethod === \"true\") { //No I18N \n        hreference = \"/showresource.do?resourceid=\" + resId + \"&method=showResourceForResourceID\"   //NO I18N\n    }\n        document.location.href=hreference;      \n}\n\nfunction showAsPerArgs(rowName,showArgs,hideArgs,editOnClick,onEdit)\n{    \n\n    if (showArgs !== 'null' || hideArgs !== 'null') {\n    var showArguments= showArgs.split(\",\");\n    var hideArguments = hideArgs.split(\",\");\n        if (editOnClick === \"true\") {\n        for(var i=0;i < showArguments.length;i++){\n            $(\"#\"+showArguments[i]+\"Div\").css({'display':'table-row'}); //No I18N\n            }\n     \n        for(var j=0;j < hideArguments.length;j++){\n            $(\"#\"+hideArguments[j]+\"Div\").hide();\n            }\n     \n    }        \n        if (rowName === \"snmpVersionValue\") {\n");
/* 2653 */                     out.write("            if ($('[name=snmpVersionValue]:checked').val() == \"v3\") {\n                showSecurityLevelProps();\n            }\n\n        }               \n        \n        if (rowName === \"mode\") {\n            var mode = $('input[name=mode]:checked').val();\n            if (mode === \"ND\") { //NO I18N\n                $('input[name=isDmgr]').val(true);              \n            } else if (mode === \"BASE\") { //NO I18N\n                $('input[name=isDmgr]').val(false);\n            }\n        }\n    }\n}\n\nfunction formReload()\n{\n    type = document.AMActionForm.type.value;\n    hideFields = '");
/* 2654 */                     out.print(request.getParameter("hideFieldsForIT360"));
/* 2655 */                     out.write("'; //NO I18N\n\n\n    document.AMActionForm.action = \"/adminAction.do?method=reloadHostDiscoveryForm&type=\" + type + \"&hideFieldsForIT360=\"+hideFields;   //NO I18N\n    enableAll();\n    document.AMActionForm.submit();\n\n\n\n}\n\nfunction resetname(name)\n{\n    if (name === 'groupname') {\n        document.AMActionForm.groupname.value='';\n    }\n    if (name === 'subgroupname') {\n        document.AMActionForm.subgroupname.value='';\n    }\n}\nfunction createGroup()\n{\n    if (document.AMActionForm.groupname.value === '') {\n        alert('");
/* 2656 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2657 */                     out.write("');\n        hideDiv('group');\n        showDiv('creategroup');\n        document.AMActionForm.groupname.focus();\n        return false;\n    } else {\n        hideDiv('creategroup');\n        var a=document.AMActionForm.groupname.value;\n        url=\"/adminAction.do?method=createMonitorGroup&groupname=\"+encodeURIComponent(a);   //NO I18N\n        http.open(\"GET\",url,true);\n        http.onreadystatechange = getActionTypes;\n        http.send(null);\n        showDiv('group');\n    }\n\n}\n\nfunction cancelModify(idString)\n{    \n    var toReplaceInput = \"\";    \n    var toReplaceModifyPart = \"<a href=\\\"javascript:void(0)\\\" style=\\\"color:blue;text-decoration:underline;\\\" onclick=\\\"modifyPassword(\\'\"+idString+\"\\')\\\">");
/* 2658 */                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */                       return;
/* 2660 */                     out.write("&nbsp;\"+idString+\"</a>\";\n    $(\"#tdSpan_\"+idString).html(toReplaceInput);\n    $(\"#modifySpan_\"+idString).html(toReplaceModifyPart);\n}\n\nfunction modifyPassword(idString)\n{    \n    var toReplaceInput = \"<input id=\\\"toCheck\\\" class=\\\"formtext\\\" type=\\\"password\\\" value=\\\"\\\" name=\\\"\"+idString+\"\\\">\";\n    var toReplaceModifyPart = \"<a href=\\\"javascript:void(0)\\\" style=\\\"color:blue\\\" onclick=\\\"cancelModify(\\'\"+idString+\"\\')\\\">");
/* 2661 */                     if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */                       return;
/* 2663 */                     out.write("</a>\";\n    $(\"#tdSpan_\"+idString).html(toReplaceInput);\n    $(\"#modifySpan_\"+idString).html(toReplaceModifyPart);\n    $(\"#toCheck\").focus();\n}\n\nfunction check()\n{\n    hideDiv(\"creategroup\");\n    hideDiv(\"createsubgroup\");\n    hideDiv(\"groupmessage\");    \n    var flag='");
/* 2664 */                     out.print(com.adventnet.appmanager.util.Constants.subGroupsEnabled);
/* 2665 */                     out.write("';\n    if(trimAll(document.AMActionForm.haid.value) === '' || document.AMActionForm.haid.value === '-') {\n        if (document.AMActionForm.haid.value === '-') {\n            hideDiv(\"subgroup\");\n            showDiv(\"group\");\n        } else {\n            hideDiv(\"group\");\n            showDiv(\"subgroup\");\n        }\n    } else {\n        showDiv(\"group\");\n    }\n}\n\nfunction createsubGroup()\n{\n    if(trimAll(document.AMActionForm.haid.value) === '' || document.AMActionForm.haid.value === '-') {\n        alert('");
/* 2666 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.monitoralert"));
/* 2667 */                     out.write("');\n        document.AMActionForm.haid.focus();\n    } else {\n        if (document.AMActionForm.subgroupname.value === '') {\n            alert('");
/* 2668 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2669 */                     out.write("');\n            document.AMActionForm.subgroupname.focus();\n            return false;\n        } else {\n            hideDiv('createsubgroup');\n            var a=document.AMActionForm.subgroupname.value;\n            var haid=document.AMActionForm.haid.value;\n            url=\"/adminAction.do?method=createSubGroup&haid=\"+haid+\"&subgroupname=\"+encodeURIComponent(a);\n            http.open(\"GET\",url,true);\n            http.onreadystatechange = getActionTypes;\n            http.send(null);\n        }\n        showDiv('subgroup');\n    }\n\n}  \n\n function getActionTypes()\n{\n    if(http.readyState == 4)\n    {\n        var result = http.responseText;\n        var id=result;\n        var stringtokens=id.split(\",\");\n        sid = stringtokens[2];\n        sname=stringtokens[1];\n        sname=decodeURIComponent(stringtokens[1]);\n        smessage=stringtokens[0];\n        if (sname===null || sname==='undefined')\n        {\n            showDiv(\"groupmessage\");\n            document.getElementById(\"groupmessage\").innerHTML=smessage;\n        }\n");
/* 2670 */                     out.write("        else\n        {\n            document.AMActionForm.haid.options[document.AMActionForm.haid.length] =new Option(sname,sid,false,true);\n            hideDiv(\"creategroup\");\n            hideDiv(\"createsubgroup\");\n            hideDiv(\"group\");\n            showDiv(\"subgroup\");\n            showDiv(\"groupmessage\");\n            document.getElementById(\"groupmessage\").innerHTML=smessage;\n        }\n    }\n}\n\n  function validateAndSubmit(){            \n            ");
/* 2671 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2673 */                     out.write("\n\n    if(document.AMActionForm.displayname.value == \"\"){\n        alert('");
/* 2674 */                     out.print(FormatUtil.getString("am.webclient.newscript.alert.displaynameempty"));
/* 2675 */                     out.write("');\n                                    return;\n                                 }\n                             var disp=document.AMActionForm.displayname.value;\n\n                            if(disp.length > 500){\n        alert('");
/* 2676 */                     out.print(FormatUtil.getString("am.webclient.newscript.alert.displaynameexceed"));
/* 2677 */                     out.write("');\n                                return;\n                            }\n                                \n    if(displayNameHasQuotes(disp,'");
/* 2678 */                     out.print(FormatUtil.getString("am.webclient.specialchar.alert.displayname"));
/* 2679 */                     out.write("')){\n                                    document.AMActionForm.displayname.focus();\n                                    return ;\n                            }\n\n    if(!formValidation()){\n                            return;\n                         }\n                          \n                            var pollinterval=document.AMActionForm.pollinterval.value;\n    if (pollinterval === \"\" || !(isPositiveInteger(pollinterval)) || pollinterval === '0') {\n        alert('");
/* 2680 */                     out.print(FormatUtil.getString("am.webclient.newscript.alert.pollingintervalzero.text"));
/* 2681 */                     out.write("');\n                                        document.AMActionForm.pollInterval.focus();\n                                        return;\n                                }\n    ");
/* 2682 */                     if ("true".equalsIgnoreCase((String)request.getAttribute("isRBM")))
/*      */                     {
/* 2684 */                       out.write("\n        if(pollinterval < 10)\n        {\n            alert('");
/* 2685 */                       out.print(FormatUtil.getString("am.webclient.rbm.alert.pollinginterval.text"));
/* 2686 */                       out.write("');\n            document.AMActionForm.pollInterval.focus();\n            return;\n        }\n    ");
/*      */                     }
/* 2688 */                     if (EnterpriseUtil.isAdminServer()) {
/* 2689 */                       out.write("                                \n                                    if (document.AMActionForm.masSelectType[1].checked) {\n                                        var selectedVal=document.AMActionForm.masGroupName.value;\n                                        if (selectedVal != null && selectedVal == \"-\") {\n                                            alert('");
/* 2690 */                       out.print(FormatUtil.getString("am.webclient.admin.add.monitor.select.masgroup.text"));
/* 2691 */                       out.write("');\n                                            document.AMActionForm.masGroupName.focus();\n                                            return;\n                                        }                                   \n            }\n            else if (document.AMActionForm.masSelectType[2].checked) {\n                                        selectedVal=document.AMActionForm.selectedServer.value;\n                                        if (selectedVal != null && selectedVal == \"-\") {\n                                            alert('");
/* 2692 */                       out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.mas.text"));
/* 2693 */                       out.write("');\n                                            document.AMActionForm.selectedServer.focus();\n                                            return;\n                                        }                                   \n                                    }\n    ");
/*      */                     }
/* 2695 */                     if (resourceid.equals("")) {
/* 2696 */                       out.write("\n        if(trimAll(document.AMActionForm.haid.value) === '' || document.AMActionForm.haid.value === '-') {}\n        else {\n                                                        document.AMActionForm.addtoha.value=\"true\";\n                                                }\n    ");
/*      */                     }
/*      */                     
/* 2699 */                     if (!resourceid.equals("")) {
/* 2700 */                       String appendURL = "";
/* 2701 */                       if (request.getParameter("tabId") != null) {
/* 2702 */                         appendURL = appendURL + "&tabId=" + request.getParameter("tabId") + "&TimeUnit=" + request.getParameter("TimeUnit") + "&granularity=" + request.getParameter("granularity") + "&customDate=" + request.getParameter("customDate") + "&monthUnit=" + request.getParameter("monthUnit") + "&weekUnit=" + request.getParameter("weekUnit");
/*      */                       }
/*      */                       
/*      */ 
/* 2706 */                       out.write("\n        document.AMActionForm.action='/manageConfMons.do?method=updateMonitor&resourceid=");
/* 2707 */                       out.print(resourceid);
/* 2708 */                       out.write("&montype=");
/* 2709 */                       out.print(request.getParameter("type"));
/* 2710 */                       out.print(appendURL);
/* 2711 */                       out.write("';//No I18N\n        ");
/*      */                     }
/*      */                     
/*      */ 
/* 2715 */                     if ((EnterpriseUtil.isIt360MSPEdition()) && (resourceid.equals(""))) {
/* 2716 */                       out.write("\n\n            if (document.AMActionForm.organization.value === \"-\"){\n                alert('");
/* 2717 */                       out.print(FormatUtil.getString("it360.addnewmonitor.err.checkcustomer"));
/* 2718 */                       out.write("');\n                                    return;\n                                  }\n\n            if (trimAll(document.AMActionForm.haid.value) === '' || document.AMActionForm.haid.value === \"-\"){\n                alert('");
/* 2719 */                       out.print(FormatUtil.getString("it360.addnewmonitor.err.checksite"));
/* 2720 */                       out.write("');\n                                     return;\n                } ");
/*      */                     }
/*      */                     
/* 2723 */                     out.write("\n\n\n                             \n                             \n                            ");
/*      */                     
/* 2725 */                     EqualTag _jspx_th_logic_005fequal_005f0 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.get(EqualTag.class);
/* 2726 */                     _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
/* 2727 */                     _jspx_th_logic_005fequal_005f0.setParent(null);
/*      */                     
/* 2729 */                     _jspx_th_logic_005fequal_005f0.setName("isAgentEnabled");
/*      */                     
/* 2731 */                     _jspx_th_logic_005fequal_005f0.setValue("YES");
/* 2732 */                     int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
/* 2733 */                     if (_jspx_eval_logic_005fequal_005f0 != 0) {
/*      */                       for (;;) {
/* 2735 */                         out.write(" \n                                if(!checkAgentSelected()){\n                                    if(noOfAgents!=0){\n                    ");
/* 2736 */                         if ("true".equalsIgnoreCase((String)request.getAttribute("isRBM"))) {
/* 2737 */                           out.write("\n                        alert('");
/* 2738 */                           out.print(FormatUtil.getString("am.webclient.eumagent.validate.noAgentassociation"));
/* 2739 */                           out.write("');\n                    ");
/*      */                         } else {
/* 2741 */                           out.write("\n                    alert('");
/* 2742 */                           out.print(FormatUtil.getString("am.webclient.eumagent.validate.noassociation"));
/* 2743 */                           out.write("');\n                    ");
/*      */                         }
/* 2745 */                         out.write("\n                                    }\n                                    else{\n                                        noOfAgents=1;   \n                                    }\n                                    return;\n                                }\n                                \n                            ");
/* 2746 */                         int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
/* 2747 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2751 */                     if (_jspx_th_logic_005fequal_005f0.doEndTag() == 5) {
/* 2752 */                       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/*      */                     }
/*      */                     else {
/* 2755 */                       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 2756 */                       out.write("\n                            \n                            \n                                ");
/*      */                       
/* 2758 */                       IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2759 */                       _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2760 */                       _jspx_th_c_005fif_005f3.setParent(null);
/*      */                       
/* 2762 */                       _jspx_th_c_005fif_005f3.setTest("${checkForMonitorGroup}");
/* 2763 */                       int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2764 */                       if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                         for (;;) {
/* 2766 */                           out.write("\n                                        var haidValue = document.AMActionForm.haid.value\n                                        var resid = document.AMActionForm.resourceid.value;\n                                        if((haidValue == '-' || haidValue == '') && resid =='' ){\n            alert('");
/* 2767 */                           out.print(FormatUtil.getString("am.webclient.newmonitor.selectmg.text"));
/* 2768 */                           out.write("')\n                                        return;\n                                        }\n                                ");
/* 2769 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2770 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2774 */                       if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2775 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*      */                       }
/*      */                       else {
/* 2778 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2779 */                         out.write("\n                                \n                            document.AMActionForm.submit();\n      }\n\nfunction formValidation()\n{ ");
/*      */                         
/* 2781 */                         for (int i = 0; i < args_name.size(); i++) {
/* 2782 */                           if ((args_type.get(i).toString().equals("1")) || ((args_type.get(i).toString().equals("7")) && (resourceid.equals("")))) {
/* 2783 */                             out.write("\n                          var temp=document.getElementById(\"");
/* 2784 */                             out.print(args_name.get(i));
/* 2785 */                             out.write("\").value;\n\n            if (temp == \"\") { ");
/*      */                             
/* 2787 */                             if ((args_mandataory != null) && (args_mandataory.get(i).equals("1"))) {
/* 2788 */                               if ((args_ShowOnCondition != null) && (args_ShowOnCondition.get(i).equals("YES"))) {
/* 2789 */                                 out.write("\n                                            if(document.getElementById(\"");
/* 2790 */                                 out.print((String)args_name.get(i));
/* 2791 */                                 out.write("Div\").style.display==\"table-row\"){\n\n                                                alert('");
/* 2792 */                                 out.print(FormatUtil.getString("am.webclient.confmon.jscheck.empty", new String[] { FormatUtil.getString((String)args_disp.get(i)) }));
/* 2793 */                                 out.write("'); //No I18N\n                                            return;\n                        } ");
/*      */                               }
/*      */                               else
/*      */                               {
/* 2797 */                                 out.write("\n                        if (document.getElementById(\"");
/* 2798 */                                 out.print((String)args_name.get(i));
/* 2799 */                                 out.write("Div\").style.display == \"table-row\") {\n                                                        alert('");
/* 2800 */                                 out.print(FormatUtil.getString("am.webclient.confmon.jscheck.empty", new String[] { FormatUtil.getString((String)args_disp.get(i)) }));
/* 2801 */                                 out.write("'); //No I18N\n                                                        return;\n                                                      }\n\n                              ");
/*      */                               }
/*      */                             }
/*      */                             
/* 2805 */                             out.write("\n\n            } ");
/*      */                           }
/* 2807 */                           else if (!args_type.get(i).toString().equals("2"))
/*      */                           {
/* 2809 */                             if (args_type.get(i).toString().equals("3")) {
/* 2810 */                               out.write("\n                                  var temp=document.getElementById(\"");
/* 2811 */                               out.print(args_name.get(i));
/* 2812 */                               out.write("\").value; //No I18N\n            if (temp == \"\") { ");
/*      */                               
/* 2814 */                               if ((args_mandataory != null) && (args_mandataory.get(i).equals("1"))) {
/* 2815 */                                 if ((args_ShowOnCondition != null) && (args_ShowOnCondition.get(i).equals("YES")))
/*      */                                 {
/*      */ 
/* 2818 */                                   out.write("\n                                            if(document.getElementById(\"");
/* 2819 */                                   out.print((String)args_name.get(i));
/* 2820 */                                   out.write("Div\").style.display==\"table-row\"){\n\n                                        alert('");
/* 2821 */                                   out.print(FormatUtil.getString("am.webclient.confmon.jscheck.empty", new String[] { FormatUtil.getString((String)args_disp.get(i)) }));
/* 2822 */                                   out.write("'); //No I18N\n                                        return;\n                        } ");
/*      */                                 }
/*      */                                 else {
/* 2825 */                                   out.write("\n                        if (document.getElementById(\"");
/* 2826 */                                   out.print((String)args_name.get(i));
/* 2827 */                                   out.write("Div\").style.display == \"table-row\") {\n                                                        alert('");
/* 2828 */                                   out.print(FormatUtil.getString("am.webclient.confmon.jscheck.empty", new String[] { FormatUtil.getString((String)args_disp.get(i)) }));
/* 2829 */                                   out.write("'); //No I18N\n                                                        return;\n                        } ");
/*      */                                 }
/*      */                               }
/*      */                               
/* 2833 */                               out.write("\n            } ");
/*      */                               
/* 2835 */                               if (monTitle.contains("QueryMonitor")) {
/* 2836 */                                 out.write("\n                                        var st=temp.split(\"\\n\"); //No I18N\n                for (var i = 0; i < st.length; i++) {\n                                            var qry = st[i];\n                                            var tqry1=qry.substring(0,(qry.toLowerCase()).indexOf(\" from \")); //No I18N\n                                            var tqry=tqry1.split(\",\"); //No I18N\n                    if (tqry.length == 1) {\n                        if ((tqry1.toLowerCase()).indexOf(\"count(\") != -1 || (tqry1.toLowerCase()).indexOf(\"min(\") != -1 || (tqry1.toLowerCase()).indexOf(\"max(\") != -1 || (tqry1.toLowerCase()).indexOf(\"avg(\") != -1 || (tqry1.toLowerCase()).indexOf(\"sum(\") != -1) {\n                                                showDiv(\"querymessagediv\"); //No I18N\n                                                jQuery('#queryalert').html('");
/* 2837 */                                 if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*      */                                   return;
/* 2839 */                                 out.write("'); //No I18N\n                                                return;\n                                                }\n                                            }\n                } ");
/*      */                               }
/*      */                             }
/* 2842 */                             else if (args_type.get(i).toString().equals("4")) {
/* 2843 */                               out.write("\n            for (var i = 0; i < document.getElementsByName(\"");
/* 2844 */                               out.print(args_name.get(i));
/* 2845 */                               out.write("\").length; i++) {\n                if (document.getElementsByName(\"");
/* 2846 */                               out.print(args_name.get(i));
/* 2847 */                               out.write("\")[i].checked) {\n                    isonechecked = true;\n                    break;\n                } else {\n                    if (i == (document.getElementsByName(\"");
/* 2848 */                               out.print(args_name.get(i));
/* 2849 */                               out.write("\").length - 1)) { ");
/*      */                               
/* 2851 */                               if ((args_mandataory != null) && (args_mandataory.get(i).equals("1"))) {
/* 2852 */                                 if ((args_ShowOnCondition != null) && (args_ShowOnCondition.get(i).equals("YES"))) {
/* 2853 */                                   out.write("\n                                if (document.getElementById(\"");
/* 2854 */                                   out.print((String)args_name.get(i));
/* 2855 */                                   out.write("Div\").style.display == \"table-row\") {\n                                    alert('");
/* 2856 */                                   out.print(FormatUtil.getString("am.webclient.confmon.jscheck.selectOne", new String[] { FormatUtil.getString((String)args_disp.get(i)) }));
/* 2857 */                                   out.write("'); //No I18N\n                                    return;\n                                } ");
/*      */                                 }
/*      */                                 else {
/* 2860 */                                   out.write("\n                                if (document.getElementById(\"");
/* 2861 */                                   out.print((String)args_name.get(i));
/* 2862 */                                   out.write("Div\").style.display == \"table-row\") {\n                                    alert('");
/* 2863 */                                   out.print(FormatUtil.getString("am.webclient.confmon.jscheck.selectOne", new String[] { FormatUtil.getString((String)args_disp.get(i)) }));
/* 2864 */                                   out.write("'); //No I18N\n                                    return;\n                                }\n                             ");
/*      */                                 }
/*      */                               }
/*      */                               
/* 2868 */                               out.write("\n                              }\n                }\n            }\n            ");
/*      */ 
/*      */                             }
/* 2871 */                             else if (args_type.get(i).toString().equals("8"))
/*      */                             {
/* 2873 */                               out.write("\n            var selected = $('select[name=\"select-");
/* 2874 */                               out.print(args_name.get(i));
/* 2875 */                               out.write("\"]').val()\n            if(selected != null){\n                var divName = \"");
/* 2876 */                               out.print(args_name.get(i));
/* 2877 */                               out.write("\" + \"Div\";\n                if (document.getElementById(divName)!=null && document.getElementById(divName)!=\"undefined\") {\n                    var dispVal = document.getElementById(divName).style.display;\n                    if (dispVal == \"none\") {\n                        document.getElementById(\"");
/* 2878 */                               out.print(args_name.get(i));
/* 2879 */                               out.write("\").value=\"False\";//No I18N\n                    } else {\n                        document.getElementById(\"");
/* 2880 */                               out.print(args_name.get(i));
/* 2881 */                               out.write("\").value=selected.toString();\n                    }\n                } else {\n                    document.getElementById(\"");
/* 2882 */                               out.print(args_name.get(i));
/* 2883 */                               out.write("\").value=selected.toString();\n                }\n                                                }\n            else{\n                document.getElementById(\"");
/* 2884 */                               out.print(args_name.get(i));
/* 2885 */                               out.write("\").value=\"False\";//No I18N\n                ");
/*      */                               
/* 2887 */                               if ((args_mandataory != null) && (args_mandataory.get(i).equals("1")))
/*      */                               {
/* 2889 */                                 if ((args_ShowOnCondition != null) && (args_ShowOnCondition.get(i).equals("YES")))
/*      */                                 {
/* 2891 */                                   out.write("\n                        if(document.getElementById(\"");
/* 2892 */                                   out.print((String)args_name.get(i));
/* 2893 */                                   out.write("Div\").style.display==\"table-row\")\n                                                                        {\n\n                                                                alert('");
/* 2894 */                                   out.print(FormatUtil.getString("am.webclient.confmon.jscheck.selectOne", new String[] { FormatUtil.getString((String)args_disp.get(i)) }));
/* 2895 */                                   out.write("'); //No I18N\n                                                                    return;\n                                                                                    }\n                    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 2900 */                                   out.write("\n                                                                                    if(document.getElementById(\"");
/* 2901 */                                   out.print((String)args_name.get(i));
/* 2902 */                                   out.write("Div\").style.display==\"table-row\")\n                                                                                    {\n                                            alert('");
/* 2903 */                                   out.print(FormatUtil.getString("am.webclient.confmon.jscheck.selectOne", new String[] { FormatUtil.getString((String)args_disp.get(i)) }));
/* 2904 */                                   out.write("'); //No I18N\n                                            return;\n                                                                                    }\n\n                    ");
/*      */                                 }
/*      */                               }
/*      */                               
/* 2908 */                               out.write("\n                                        }\n\n                              ");
/*      */                             }
/* 2910 */                             else if (args_type.get(i).toString().equals("5")) {
/* 2911 */                               out.write("\n                                      var temp=\"\"; //No I18N\n                                      var isoneChecked=\"false\"; //No I18N\n                                      for(var i=0;i<document.getElementsByName(\"");
/* 2912 */                               out.print(args_name.get(i));
/* 2913 */                               out.print(i);
/* 2914 */                               out.write("\").length;i++){\n                                          if(document.getElementsByName(\"");
/* 2915 */                               out.print(args_name.get(i));
/* 2916 */                               out.print(i);
/* 2917 */                               out.write("\")[i].checked){\n                                              if(temp==\"\"){\n                                                  temp=document.getElementsByName(\"");
/* 2918 */                               out.print(args_name.get(i));
/* 2919 */                               out.print(i);
/* 2920 */                               out.write("\")[i].value; //No I18N\n                    } else {\n                                                  temp=temp+\",\"+document.getElementsByName(\"");
/* 2921 */                               out.print(args_name.get(i));
/* 2922 */                               out.print(i);
/* 2923 */                               out.write("\")[i].value; //No I18N\n                                              }\n                                              isoneChecked=\"true\"; //No I18N\n                                          }\n                                      }\n                                      if(isoneChecked==\"true\"){\n                                          document.getElementById(\"");
/* 2924 */                               out.print(args_name.get(i));
/* 2925 */                               out.write("\").value=temp;\n            } else {\n                                          document.getElementById(\"");
/* 2926 */                               out.print(args_name.get(i));
/* 2927 */                               out.write("\").value=\"False\";//No I18N\n                ");
/*      */                               
/* 2929 */                               if ((args_mandataory != null) && (args_mandataory.get(i).equals("1"))) {
/* 2930 */                                 if ((args_ShowOnCondition != null) && (args_ShowOnCondition.get(i).equals("YES"))) {
/* 2931 */                                   out.write("\n                                                if(document.getElementById(\"");
/* 2932 */                                   out.print((String)args_name.get(i));
/* 2933 */                                   out.write("Div\").style.display==\"table-row\"){\n                                              alert('");
/* 2934 */                                   out.print(FormatUtil.getString("am.webclient.confmon.jscheck.selectOne", new String[] { FormatUtil.getString((String)args_disp.get(i)) }));
/* 2935 */                                   out.write("'); //No I18N\n                                              return;\n                        } ");
/*      */                                 }
/*      */                                 else {
/* 2938 */                                   out.write("\n                        if (document.getElementById(\"");
/* 2939 */                                   out.print((String)args_name.get(i));
/* 2940 */                                   out.write("Div\").style.display == \"table-row\") {\n                                alert('");
/* 2941 */                                   out.print(FormatUtil.getString("am.webclient.confmon.jscheck.selectOne", new String[] { FormatUtil.getString((String)args_disp.get(i)) }));
/* 2942 */                                   out.write("'); //No I18N\n                                return;\n                                                             }\n\n                        ");
/*      */                                 }
/*      */                               }
/*      */                               
/* 2946 */                               out.write("\n                                      }\n\n            ");
/*      */                             }
/* 2948 */                             else if ((args_type.get(i).toString().equals("6")) && (args_name.get(i).equals("UT"))) {
/* 2949 */                               out.write("\n            document.getElementById(\"");
/* 2950 */                               out.print(args_name.get(i));
/* 2951 */                               out.write("\").value = \"");
/* 2952 */                               out.print(System.currentTimeMillis());
/* 2953 */                               out.write(34);
/* 2954 */                               out.write(59);
/* 2955 */                               out.write(32);
/*      */                             }
/*      */                           }
/*      */                         }
/* 2959 */                         out.write("\n                          \n                            return true;\n}\n\nfunction myOnLoad() {\n    document.AMActionForm.displayname.focus();\n        if($('#mode').length) //This is just checking for the mode element is present. If id by name 'mode' is present, then execute the mode change operations.\n        {\n        if ( ");
/* 2960 */                         out.print(argsasprops.size());
/* 2961 */                         out.write(" == 0) {\n                    checkOS();\n                }\n             onChangeMode();\n    } ");
/*      */                         
/* 2963 */                         String onChangeMethodNameToExecute = "";
/* 2964 */                         ArrayList onChangeToExecuteOrder = new ArrayList();
/* 2965 */                         if (argsasprops.size() > 0) {
/* 2966 */                           for (int i = 0; i < args_name.size(); i++) {
/* 2967 */                             String name1 = (String)args_name.get(i);
/* 2968 */                             if ((!EnterpriseUtil.isAdminServer) && (name1.equalsIgnoreCase("netmask"))) {
/* 2969 */                               out.write("\n                                    $(\"#\"+\"");
/* 2970 */                               out.print(name1);
/* 2971 */                               out.write("\"+\"Div\").hide(\"fast\"); //No I18N\n                                ");
/*      */                             }
/*      */                             
/* 2974 */                             if ((!EnterpriseUtil.isAdminServer) && (isPreConfMonitor.equalsIgnoreCase("true")) && (name1.equalsIgnoreCase("host")))
/*      */                             {
/* 2976 */                               out.write("\n                                    $(\"#\"+\"");
/* 2977 */                               out.print(name1);
/* 2978 */                               out.write("\"+\"Div\").hide(\"fast\"); //No I18N\n                                ");
/*      */                             }
/*      */                             
/* 2981 */                             if (!args_type.get(i).toString().equals("3"))
/*      */                             {
/* 2983 */                               String temp = "";
/* 2984 */                               if ((argsasprops.getProperty((String)args_name.get(i)) != null) && (!argsasprops.getProperty((String)args_name.get(i)).trim().equals(""))) {
/* 2985 */                                 temp = argsasprops.getProperty((String)args_name.get(i));
/*      */                               }
/*      */                               
/*      */ 
/* 2989 */                               if (args_type.get(i).toString().equals("7")) {
/* 2990 */                                 out.write("\n                        temp = \"\";\n                                                document.getElementById(\"");
/* 2991 */                                 out.print(args_name.get(i));
/* 2992 */                                 out.write("\").value='");
/* 2993 */                                 out.print(temp);
/* 2994 */                                 out.write("';\n                    ");
/*      */                               }
/*      */                               
/* 2997 */                               if (args_type.get(i).toString().equals("2")) {
/* 2998 */                                 onChangeMethodNameToExecute = (String)onChangeMethod.get(args_name.get(i));
/* 2999 */                                 if ((onChangeMethodNameToExecute != null) || (onChangeMethodNameToExecute != "")) {
/* 3000 */                                   out.write("\n                        if (\"onChangeOS()\" != \"");
/* 3001 */                                   out.print(onChangeMethodNameToExecute);
/* 3002 */                                   out.write("\") { ");
/* 3003 */                                   out.print(onChangeMethodNameToExecute);
/* 3004 */                                   out.write(" ;\n                                                 }\n                    ");
/*      */                                 }
/*      */                               }
/*      */                               
/*      */ 
/* 3009 */                               if (!args_type.get(i).toString().equals("4"))
/*      */                               {
/* 3011 */                                 out.write("\n                                                        \n                            document.getElementById(\"");
/* 3012 */                                 out.print(args_name.get(i));
/* 3013 */                                 out.write("\").value='");
/* 3014 */                                 out.print(temp);
/* 3015 */                                 out.write("';\n                    ");
/*      */                                 
/* 3017 */                                 if ((args_type.get(i).toString().equals("5")) && (showArgsMap != null) && (showArgsMap.get((String)args_name.get(i)) != null)) {
/* 3018 */                                   out.write("\n                            for(var i=0;i<document.getElementsByName(\"");
/* 3019 */                                   out.print(args_name.get(i));
/* 3020 */                                   out.print(i);
/* 3021 */                                   out.write("\").length;i++){\n                            if (document.getElementsByName(\"");
/* 3022 */                                   out.print(args_name.get(i));
/* 3023 */                                   out.print(i);
/* 3024 */                                   out.write("\")[i].checked) { ");
/* 3025 */                                   String argsList = getArgsListtoShowonClick(showArgsMap, (String)args_name.get(i));
/* 3026 */                                   String hideArgsList = "";
/* 3027 */                                   if ((hideArgsMap != null) && (hideArgsMap.get((String)args_name.get(i)) != null)) {
/* 3028 */                                     hideArgsList = getArgsListToHideOnClick(hideArgsMap, (String)args_name.get(i));
/*      */                                   }
/* 3030 */                                   out.write("\n\n                                    showHideArgument(null,'");
/* 3031 */                                   out.print(argsList);
/* 3032 */                                   out.write(39);
/* 3033 */                                   out.write(44);
/* 3034 */                                   out.write(39);
/* 3035 */                                   out.print(hideArgsList);
/* 3036 */                                   out.write("',\"true\");\n\n                                }\n                            }\n                        ");
/*      */                                 }
/*      */                                 
/* 3039 */                                 if ((args_type.get(i).toString().equals("5")) && (temp.equalsIgnoreCase("true"))) {
/* 3040 */                                   out.write("\n                                                                                                \n                                                $(\"#\"+\"");
/* 3041 */                                   out.print(args_name.get(i));
/* 3042 */                                   out.print(i);
/* 3043 */                                   out.write("\").attr(\"checked\", true); //No I18N\n                                                $(\"#\"+\"");
/* 3044 */                                   out.print(args_name.get(i));
/* 3045 */                                   out.print(i);
/* 3046 */                                   out.write("\").attr(\"checked\", \"checked\"); //No I18N\n                        ");
/*      */                                 }
/*      */                                 
/*      */                               }
/*      */                               else
/*      */                               {
/* 3052 */                                 boolean defaultValuesAlreadyCalled = false;
/* 3053 */                                 out.write("\n\n                                 document.getElementsByName(\"");
/* 3054 */                                 out.print(args_name.get(i));
/* 3055 */                                 out.write("\").value='");
/* 3056 */                                 out.print(temp);
/* 3057 */                                 out.write("';\n                                                           \n                    $(\"input:radio[value=");
/* 3058 */                                 out.print(temp);
/* 3059 */                                 out.write("]\").attr(\"checked\",\"checked\"); //No I18N\n                    ");
/*      */                                 
/* 3061 */                                 if (((String)args_name.get(i)).equalsIgnoreCase("mode"))
/*      */                                 {
/* 3063 */                                   LinkedHashMap modeMap = (LinkedHashMap)hm.get("mode");
/* 3064 */                                   String argsToShow = "";
/* 3065 */                                   String argsToHide = "";
/* 3066 */                                   if (modeMap.size() > 0) {
/* 3067 */                                     argsToShow = (String)modeMap.get(temp + "_OnClickShowArgs");
/* 3068 */                                     argsToHide = (String)modeMap.get(temp + "_OnClickHideArgs");
/* 3069 */                                     out.write("\n                                                                    showAsPerArgs('");
/* 3070 */                                     out.print(args_name.get(i));
/* 3071 */                                     out.write(39);
/* 3072 */                                     out.write(44);
/* 3073 */                                     out.write(39);
/* 3074 */                                     out.print(argsToShow);
/* 3075 */                                     out.write(39);
/* 3076 */                                     out.write(44);
/* 3077 */                                     out.write(39);
/* 3078 */                                     out.print(argsToHide);
/* 3079 */                                     out.write("','true','true');\n                            ");
/*      */                                   }
/*      */                                   
/* 3082 */                                   defaultValuesAlreadyCalled = true;
/*      */                                 }
/*      */                                 
/* 3085 */                                 if (((String)args_name.get(i)).equalsIgnoreCase("CredentialDetails")) {
/* 3086 */                                   LinkedHashMap credDetailsMap = (LinkedHashMap)hm.get("CredentialDetails");
/* 3087 */                                   String argsToShow = "";
/* 3088 */                                   String argsToHide = "";
/* 3089 */                                   if (credDetailsMap.size() > 0) {
/* 3090 */                                     argsToShow = (String)credDetailsMap.get(temp + "_OnClickShowArgs");
/* 3091 */                                     argsToHide = (String)credDetailsMap.get(temp + "_OnClickHideArgs");
/* 3092 */                                     out.write("                                                                    \n                                                                    showAsPerArgs('");
/* 3093 */                                     out.print(args_name.get(i));
/* 3094 */                                     out.write(39);
/* 3095 */                                     out.write(44);
/* 3096 */                                     out.write(39);
/* 3097 */                                     out.print(argsToShow);
/* 3098 */                                     out.write(39);
/* 3099 */                                     out.write(44);
/* 3100 */                                     out.write(39);
/* 3101 */                                     out.print(argsToHide);
/* 3102 */                                     out.write("','true','true');\n                            ");
/*      */                                   }
/*      */                                   
/* 3105 */                                   defaultValuesAlreadyCalled = true;
/*      */                                 }
/* 3107 */                                 if (((String)args_name.get(i)).equalsIgnoreCase("TelnetCredentialDetails")) {
/* 3108 */                                   LinkedHashMap credDetailsMap = (LinkedHashMap)hm.get("TelnetCredentialDetails");
/* 3109 */                                   String argsToShow = "";
/* 3110 */                                   String argsToHide = "";
/*      */                                   
/* 3112 */                                   if (credDetailsMap.size() > 0) {
/* 3113 */                                     argsToShow = (String)credDetailsMap.get(temp + "_OnClickShowArgs");
/* 3114 */                                     argsToHide = (String)credDetailsMap.get(temp + "_OnClickHideArgs");
/* 3115 */                                     out.write("\n                            showAsPerArgs('");
/* 3116 */                                     out.print(args_name.get(i));
/* 3117 */                                     out.write("', '");
/* 3118 */                                     out.print(argsToShow);
/* 3119 */                                     out.write("', '");
/* 3120 */                                     out.print(argsToHide);
/* 3121 */                                     out.write("', 'true', 'true'); ");
/*      */                                   }
/*      */                                   
/* 3124 */                                   defaultValuesAlreadyCalled = true;
/*      */                                 }
/* 3126 */                                 if (((String)args_name.get(i)).equalsIgnoreCase("SSHCredentialDetails")) {
/* 3127 */                                   LinkedHashMap credDetailsMap = (LinkedHashMap)hm.get("SSHCredentialDetails");
/* 3128 */                                   String argsToShow = "";
/* 3129 */                                   String argsToHide = "";
/* 3130 */                                   if (credDetailsMap.size() > 0) {
/* 3131 */                                     argsToShow = (String)credDetailsMap.get(temp + "_OnClickShowArgs");
/* 3132 */                                     argsToHide = (String)credDetailsMap.get(temp + "_OnClickHideArgs");
/* 3133 */                                     out.write("\n\n                            showAsPerArgs('");
/* 3134 */                                     out.print(args_name.get(i));
/* 3135 */                                     out.write("', '");
/* 3136 */                                     out.print(argsToShow);
/* 3137 */                                     out.write("', '");
/* 3138 */                                     out.print(argsToHide);
/* 3139 */                                     out.write("', 'true', 'true'); ");
/*      */                                   }
/*      */                                   
/* 3142 */                                   defaultValuesAlreadyCalled = true;
/*      */                                 }
/* 3144 */                                 if (((String)args_name.get(i)).equalsIgnoreCase("SNMPCredentialDetails")) {
/* 3145 */                                   LinkedHashMap credDetailsMap = (LinkedHashMap)hm.get("SNMPCredentialDetails");
/* 3146 */                                   String argsToShow = "";
/* 3147 */                                   String argsToHide = "";
/* 3148 */                                   if (credDetailsMap.size() > 0) {
/* 3149 */                                     argsToShow = (String)credDetailsMap.get(temp + "_OnClickShowArgs");
/* 3150 */                                     argsToHide = (String)credDetailsMap.get(temp + "_OnClickHideArgs");
/* 3151 */                                     out.write("\n                                                                    \n                            showAsPerArgs('");
/* 3152 */                                     out.print(args_name.get(i));
/* 3153 */                                     out.write("', '");
/* 3154 */                                     out.print(argsToShow);
/* 3155 */                                     out.write("', '");
/* 3156 */                                     out.print(argsToHide);
/* 3157 */                                     out.write("', 'true', 'true'); ");
/*      */                                   }
/*      */                                   
/* 3160 */                                   defaultValuesAlreadyCalled = true;
/*      */                                 }
/* 3162 */                                 if (((String)args_name.get(i)).equalsIgnoreCase("WMICredentialDetails")) {
/* 3163 */                                   LinkedHashMap credDetailsMap = (LinkedHashMap)hm.get("WMICredentialDetails");
/* 3164 */                                   String argsToShow = "";
/* 3165 */                                   String argsToHide = "";
/* 3166 */                                   if (credDetailsMap.size() > 0) {
/* 3167 */                                     argsToShow = (String)credDetailsMap.get(temp + "_OnClickShowArgs");
/* 3168 */                                     argsToHide = (String)credDetailsMap.get(temp + "_OnClickHideArgs");
/* 3169 */                                     out.write("\n                            showAsPerArgs('");
/* 3170 */                                     out.print(args_name.get(i));
/* 3171 */                                     out.write("', '");
/* 3172 */                                     out.print(argsToShow);
/* 3173 */                                     out.write("', '");
/* 3174 */                                     out.print(argsToHide);
/* 3175 */                                     out.write("', 'true', 'true'); ");
/*      */                                   }
/*      */                                   
/* 3178 */                                   defaultValuesAlreadyCalled = true;
/*      */                                 }
/* 3180 */                                 if (!defaultValuesAlreadyCalled) {
/* 3181 */                                   LinkedHashMap argsDetailsMap = (LinkedHashMap)hm.get(args_name.get(i));
/* 3182 */                                   out.write("                                                                 \n                        showAsPerArgs('");
/* 3183 */                                   out.print(args_name.get(i));
/* 3184 */                                   out.write("', '");
/* 3185 */                                   out.print((String)argsDetailsMap.get(temp + "_OnClickShowArgs"));
/* 3186 */                                   out.write("', '");
/* 3187 */                                   out.print((String)argsDetailsMap.get(temp + "_OnClickHideArgs"));
/* 3188 */                                   out.write("', 'true', 'true'); ");
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                         }
/*      */                         
/*      */ 
/*      */ 
/* 3197 */                         if ((isAgentAssociated != null) && (isAgentAssociated.equals("true")))
/* 3198 */                           out.write("\n        $('.agentLoactionDiv').show();//NO I18N\n        $('input[name=runOnAgent]').attr('checked', true);//NO I18N\n\n    ");
/* 3199 */                         if (!isAssociatedtoLocal) {
/* 3200 */                           out.write("\n        $('input[name=runOnServer]').attr('checked', false);//NO I18N\n    ");
/*      */                         }
/* 3202 */                         out.write("\n    if(!document.getElementById('HelpDetails')){\n        document.getElementById('DiscoveryDetailsTD').style.width=\"99%\";\n    }\n    \n    if(");
/* 3203 */                         out.print(EnterpriseUtil.isAdminServer());
/* 3204 */                         out.write(") {\n        var radioObj = document.AMActionForm.masSelectType;\n        if (radioObj !=null && radioObj != \"undefined\") {\n            var val='0';\n            if (radioObj[1].checked) {\n                val='1';\n            } else if (radioObj[2].checked){\n                val='2';\n            }\n            showAsPerSelection(val);\n        }   \n    }\n}\n</script>\n");
/*      */                         
/* 3206 */                         InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 3207 */                         _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 3208 */                         _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                         
/* 3210 */                         _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 3211 */                         int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 3212 */                         if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                           for (;;) {
/* 3214 */                             out.write("\n    ");
/*      */                             
/* 3216 */                             PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3217 */                             _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 3218 */                             _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                             
/* 3220 */                             _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                             
/* 3222 */                             _jspx_th_tiles_005fput_005f0.setValue(addmonitors);
/* 3223 */                             int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 3224 */                             if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 3225 */                               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                             }
/*      */                             
/* 3228 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3229 */                             out.write("\n    ");
/* 3230 */                             if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                               return;
/* 3232 */                             out.write("\n   \n    ");
/*      */                             
/* 3234 */                             PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3235 */                             _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3236 */                             _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                             
/* 3238 */                             _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                             
/* 3240 */                             _jspx_th_tiles_005fput_005f2.setType("string");
/* 3241 */                             int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3242 */                             if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 3243 */                               if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3244 */                                 out = _jspx_page_context.pushBody();
/* 3245 */                                 _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 3246 */                                 _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3249 */                                 out.write("\n    <link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n    <div id=\"querymessagediv\" style=\"display:none;\">\n        <table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" border=\"0\">\n        <tr>\n            <td  class=\"msg-status-tp-left-corn\"></td>\n            <td  class=\"msg-status-top-mid-bg\"></td>\n            <td  class=\"msg-status-tp-right-corn\"></td>\n        </tr>\n        <tr> \n            <td class=\"msg-status-left-bg\">&nbsp;</td>\n            <td width=\"98%\" class=\"msg-table-width\">\n             <table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" border=\"0\">\n             <tr>\n                <td width=\"3%\" class=\"msg-table-width-bg\"><img src=\"../images/icon_monitor_failure.gif\" alt=\"icon\" height=\"20\" width=\"20\"></td>\n                <td width=\"98%\" class=\"msg-table-width\"><div id=\"queryalert\" class=\"bodytext\"></div></td>\n             </tr>\n             </table>\n            </td>\n            <td class=\"msg-status-right-bg\"></td>\n        </tr>\n        <tr>\n            <td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n");
/* 3250 */                                 out.write("            <td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n            <td class=\"msg-status-btm-right-corn\" >&nbsp;</td>\n        </tr>\n        </table>\n    </div>\n <body onLoad=\"javascript:myOnLoad()\"></body>\n        ");
/*      */                                 
/* 3252 */                                 boolean isDiscoveryComplete = false;
/* 3253 */                                 boolean isDiscoverySuccess = false;
/*      */                                 
/*      */                                 try
/*      */                                 {
/* 3257 */                                   out.write("\n        ");
/*      */                                   
/* 3259 */                                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3260 */                                   _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3261 */                                   _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                   
/* 3263 */                                   _jspx_th_logic_005fnotEmpty_005f0.setName("discoverystatus");
/* 3264 */                                   int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3265 */                                   if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                     for (;;) {
/* 3267 */                                       out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n");
/*      */                                       
/* 3269 */                                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3270 */                                       _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3271 */                                       _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                       
/* 3273 */                                       _jspx_th_logic_005fnotEmpty_005f1.setName("discoverystatus");
/* 3274 */                                       int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3275 */                                       if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                         for (;;) {
/* 3277 */                                           out.write(10);
/*      */                                           
/*      */ 
/* 3280 */                                           if ((request.getAttribute("type") == null) || ((!request.getAttribute("type").equals("Script Monitor")) && ((request.getAttribute("basetype") == null) || ((request.getAttribute("basetype") != null) && (!request.getAttribute("basetype").equals("Script Monitor")))) && (!request.getAttribute("type").equals("QENGINE")) && (!request.getAttribute("type").equals("Web Service")) && (!request.getAttribute("type").equals("file")) && (!request.getAttribute("type").equals("directory")) && (!request.getAttribute("type").equals("File System Monitor")) && (!request.getAttribute("type").equals("Ping Monitor")) && (!request.getAttribute("type").equals("SAP-CCMS"))))
/*      */                                           {
/*      */ 
/* 3283 */                                             out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n  <tr>\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3284 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 3285 */                                             out.write("</span> </td>\n    <td width=\"7%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3286 */                                             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 3287 */                                             out.write("\n      </span> </td>\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3288 */                                             out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 3289 */                                             out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3290 */                                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 3291 */                                             out.write("\n </span></td>\n  <tr>\n  ");
/*      */                                             
/* 3293 */                                             int failedNumber = 1;
/*      */                                             
/* 3295 */                                             out.write(10);
/*      */                                             
/* 3297 */                                             IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3298 */                                             _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 3299 */                                             _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                             
/* 3301 */                                             _jspx_th_logic_005fiterate_005f0.setName("discoverystatus");
/*      */                                             
/* 3303 */                                             _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                             
/* 3305 */                                             _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */                                             
/* 3307 */                                             _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 3308 */                                             int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 3309 */                                             if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 3310 */                                               ArrayList row = null;
/* 3311 */                                               Integer i = null;
/* 3312 */                                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3313 */                                                 out = _jspx_page_context.pushBody();
/* 3314 */                                                 _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 3315 */                                                 _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                               }
/* 3317 */                                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3318 */                                               i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                               for (;;) {
/* 3320 */                                                 out.write("\n<tr>\n<td height=\"18\" class=\"bodytext\">");
/* 3321 */                                                 out.print(row.get(0));
/* 3322 */                                                 out.write("</td>\n<td height=\"18\" class=\"bodytext\">");
/* 3323 */                                                 out.print(row.get(1));
/* 3324 */                                                 out.write("</td>\n\n    <td height=\"18\" class=\"bodytext\">\n      ");
/*      */                                                 
/* 3326 */                                                 if ((row.get(2).equals("Success")) || (row.get(2).equals("Present")))
/*      */                                                 {
/* 3328 */                                                   request.setAttribute("isDiscoverySuccess", "true");
/*      */                                                   
/* 3330 */                                                   out.write("\n      <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 3331 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 3332 */                                                   out.write("\" align=\"absmiddle\">\n      ");
/*      */ 
/*      */                                                 }
/*      */                                                 else
/*      */                                                 {
/* 3337 */                                                   request.setAttribute("isDiscoverySuccess", "false");
/*      */                                                   
/* 3339 */                                                   out.write("\n      <img alt=\"");
/* 3340 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.notiniatedimagetip.text"));
/* 3341 */                                                   out.write("\" src=\"/images/icon_monitor_failure.gif\" align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                                 }
/*      */                                                 
/*      */ 
/* 3345 */                                                 out.write("\n      <span class=\"bodytextbold\">");
/* 3346 */                                                 out.print(FormatUtil.getString(String.valueOf(row.get(2))));
/* 3347 */                                                 out.write("</span> </td>\n\n      ");
/*      */                                                 
/* 3349 */                                                 pageContext.setAttribute("ret_msg", String.valueOf(row.get(3)));
/*      */                                                 
/* 3351 */                                                 out.write("\n                     ");
/*      */                                                 
/* 3353 */                                                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3354 */                                                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3355 */                                                 _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                                 
/* 3357 */                                                 _jspx_th_c_005fif_005f4.setTest("${ret_msg=='Monitoring Initiated Succesfully'}");
/* 3358 */                                                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3359 */                                                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                                   for (;;) {
/* 3361 */                                                     out.write("\n                           <td height=\"18\" class=\"bodytext\">");
/* 3362 */                                                     out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 3363 */                                                     out.write("\n                                 ");
/* 3364 */                                                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3365 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 3369 */                                                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3370 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                                 }
/*      */                                                 
/* 3373 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3374 */                                                 out.write("\n                                       ");
/*      */                                                 
/* 3376 */                                                 IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3377 */                                                 _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3378 */                                                 _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                                 
/* 3380 */                                                 _jspx_th_c_005fif_005f5.setTest("${ret_msg!='Monitoring Initiated Succesfully'}");
/* 3381 */                                                 int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3382 */                                                 if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                                   for (;;) {
/* 3384 */                                                     out.write("\n                                             <td height=\"18\" class=\"bodytext\">");
/* 3385 */                                                     out.print(row.get(3));
/* 3386 */                                                     out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                     
/* 3388 */                                                     if ((request.getAttribute("type") != null) && (!request.getAttribute("type").equals("All")) && (!request.getAttribute("type").equals("TELNET")) && (!request.getAttribute("type").equals("SNMP")) && (!request.getAttribute("type").equals("SERVICE")) && (!request.getAttribute("type").equals("JMX1.2-MX4J-RMI")) && (!request.getAttribute("type").equals("JDK1.5")) && (!request.getAttribute("type").equals("RMI")) && (!request.getAttribute("type").equals("PHP")) && (!request.getAttribute("type").equals("MAIL")) && (!request.getAttribute("type").equals("ORACLEDB")) && (!request.getAttribute("type").equals("MYSQLDB")) && (!request.getAttribute("type").equals("MSSQLDB")) && (!request.getAttribute("type").equals("DB2")) && (!request.getAttribute("type").equals("WTA")) && (!request.getAttribute("type").equals("SAP")))
/*      */                                                     {
/* 3390 */                                                       if (((String)pageContext.getAttribute("ret_msg")).indexOf(FormatUtil.getString("am.webclient.discovery.host.monitoradded.text")) == -1)
/*      */                                                       {
/* 3392 */                                                         String fWhr = request.getParameter("hideFieldsForIT360");
/* 3393 */                                                         if (fWhr == null)
/*      */                                                         {
/* 3395 */                                                           fWhr = (String)request.getAttribute("hideFieldsForIT360");
/*      */                                                         }
/*      */                                                         
/* 3398 */                                                         if (((fWhr == null) || (!fWhr.equals("true"))) && 
/* 3399 */                                                           (!request.getAttribute("type").equals("SYBASEDB")))
/*      */                                                         {
/* 3401 */                                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"bodytext\" href=\"javascript:void(0)\" onClick=\"getAllDetailsOfHost()\">");
/* 3402 */                                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.diagnose.link"));
/* 3403 */                                                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                         }
/*      */                                                       } }
/* 3406 */                                                     if ((request.getAttribute("showSupportMessage") != null) && (failedNumber == 1) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                                                     {
/* 3408 */                                                       failedNumber++;
/*      */                                                       
/*      */ 
/* 3411 */                                                       out.write("\n\t\t\t\t\t\t\t\t\t\t<br>");
/* 3412 */                                                       out.print(FormatUtil.getString("am.webclient.discovery.host.support.link", new String[] { OEMUtil.getOEMString("product.talkback.mailid"), OEMUtil.getOEMString("product.tollfree.number") }));
/* 3413 */                                                       out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                                     }
/* 3415 */                                                     out.write("\n                                                   ");
/* 3416 */                                                     int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3417 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 3421 */                                                 if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3422 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                                 }
/*      */                                                 
/* 3425 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3426 */                                                 out.write(10);
/* 3427 */                                                 out.write(10);
/* 3428 */                                                 out.write(10);
/*      */                                                 
/*      */ 
/* 3431 */                                                 if (row.size() > 4)
/*      */                                                 {
/*      */ 
/* 3434 */                                                   out.write("<br>\n");
/* 3435 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)row.get(4) }));
/* 3436 */                                                   out.write(10);
/*      */                                                 }
/*      */                                                 
/*      */ 
/* 3440 */                                                 out.write("\n</td>\n\n</tr>\n");
/* 3441 */                                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 3442 */                                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3443 */                                                 i = (Integer)_jspx_page_context.findAttribute("i");
/* 3444 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3447 */                                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3448 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3451 */                                             if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 3452 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                             }
/*      */                                             
/* 3455 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 3456 */                                             out.write("\n</table>\n");
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 3461 */                                             ArrayList al1 = (ArrayList)request.getAttribute("discoverystatus");
/*      */                                             
/* 3463 */                                             out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n<tr>\n");
/* 3464 */                                             String mtype = (String)request.getAttribute("type");
/* 3465 */                                             out.write(10);
/* 3466 */                                             if (mtype.equals("File System Monitor")) {
/* 3467 */                                               out.write("\n  <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3468 */                                               out.print(FormatUtil.getString("File/Directory Name"));
/* 3469 */                                               out.write("</span> </td>\n");
/* 3470 */                                             } else if ((((String)request.getAttribute("type")).equals("Ping Monitor")) || (((String)request.getAttribute("type")).equals("SAP-CCMS"))) {
/* 3471 */                                               out.write("\n\t<td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3472 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 3473 */                                               out.write("</span> </td>\n");
/*      */                                             } else {
/* 3475 */                                               out.write("\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3476 */                                               out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 3477 */                                               out.write("</span> </td>\n");
/*      */                                             }
/* 3479 */                                             out.write("\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3480 */                                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 3481 */                                             out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3482 */                                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 3483 */                                             out.write("</span></td>\n  </tr>\n  <tr>\n   <td height=\"18\" class=\"bodytext\">");
/* 3484 */                                             out.print(al1.get(0));
/* 3485 */                                             out.write("</td>\n   <td height=\"18\" class=\"bodytext\">\n   ");
/*      */                                             
/* 3487 */                                             if (al1.get(1).equals("Success"))
/*      */                                             {
/* 3489 */                                               request.setAttribute("isDiscoverySuccess", "true");
/*      */                                               
/* 3491 */                                               out.write("\n   <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 3492 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 3493 */                                               out.write("\" align=\"absmiddle\">\n    ");
/*      */ 
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 3498 */                                               request.setAttribute("isDiscoverySuccess", "false");
/*      */                                               
/*      */ 
/* 3501 */                                               out.write("\n      <img  src=\"/images/icon_monitor_failure.gif\"  align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                             }
/*      */                                             
/*      */ 
/* 3505 */                                             out.write("\n<span class=\"bodytextbold\">");
/* 3506 */                                             out.print(FormatUtil.getString(String.valueOf(al1.get(1))));
/* 3507 */                                             out.write("</span> </td>\n");
/*      */                                             
/* 3509 */                                             if (al1.get(1).equals("Success"))
/*      */                                             {
/* 3511 */                                               boolean isAdminServer = EnterpriseUtil.isAdminServer();
/* 3512 */                                               if (isAdminServer) {
/* 3513 */                                                 String masDisplayName = (String)al1.get(3);
/* 3514 */                                                 String format = FormatUtil.getString("am.webclient.admin.add.monitor.successfully.configured.text", new String[] { masDisplayName, "", (String)al1.get(2) });
/*      */                                                 
/* 3516 */                                                 out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\">");
/* 3517 */                                                 out.print(format);
/* 3518 */                                                 out.write("</td>\n");
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 3522 */                                                 out.write("\t\t\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"> ");
/* 3523 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 3524 */                                                 out.write("<br> ");
/* 3525 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)al1.get(2) }));
/* 3526 */                                                 out.write("</td>\n");
/*      */                                               }
/*      */                                               
/*      */ 
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 3533 */                                               out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"><span class=\"bodytext\">");
/* 3534 */                                               out.print(al1.get(2));
/* 3535 */                                               out.write("</span></td>\n");
/*      */                                             }
/*      */                                             
/*      */ 
/* 3539 */                                             out.write("\n  </tr>\n</table>\n\n");
/*      */                                           }
/*      */                                           
/*      */ 
/* 3543 */                                           out.write(10);
/* 3544 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3545 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3549 */                                       if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3550 */                                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                       }
/*      */                                       
/* 3553 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3554 */                                       out.write(10);
/* 3555 */                                       out.write("\n    ");
/*      */                                       
/* 3557 */                                       String discSucc = (String)request.getAttribute("isDiscoverySuccess");
/* 3558 */                                       isDiscoveryComplete = true;
/* 3559 */                                       if ((discSucc != null) && (discSucc.equals("true")))
/*      */                                       {
/* 3561 */                                         isDiscoverySuccess = true;
/*      */                                       }
/*      */                                       
/* 3564 */                                       out.write("\n    ");
/* 3565 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3566 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3570 */                                   if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3571 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                   }
/*      */                                   
/* 3574 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3575 */                                   out.write("\n        ");
/*      */ 
/*      */                                 }
/*      */                                 catch (Exception exc)
/*      */                                 {
/* 3580 */                                   exc.printStackTrace();
/*      */                                 }
/*      */                                 
/* 3583 */                                 out.write(10);
/*      */                                 
/* 3585 */                                 if ((!hideFields) || ((!isDiscoveryComplete) && (hideFields)))
/*      */                                 {
/*      */ 
/* 3588 */                                   out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td id=\"DiscoveryDetailsTD\" width=\"70%\" valign=\"top\">\n        ");
/*      */                                   
/* 3590 */                                   FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.get(FormTag.class);
/* 3591 */                                   _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 3592 */                                   _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                   
/* 3594 */                                   _jspx_th_html_005fform_005f0.setAction("/manageConfMons");
/*      */                                   
/* 3596 */                                   _jspx_th_html_005fform_005f0.setFocus("displayname");
/*      */                                   
/* 3598 */                                   _jspx_th_html_005fform_005f0.setEnctype("multipart/form-data");
/* 3599 */                                   int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 3600 */                                   if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                     for (;;) {
/* 3602 */                                       out.write("\n            ");
/* 3603 */                                       if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 3605 */                                       out.write("\n            <input type=\"hidden\" name=\"addtoha\" value=\"");
/* 3606 */                                       out.print(request.getParameter("addtoha"));
/* 3607 */                                       out.write("\">\n            <input type=hidden name=\"resourceid\" value=\"");
/* 3608 */                                       out.print(resourceid);
/* 3609 */                                       out.write("\" />\n            <input type=hidden id=\"montype\" name=\"montype\" value=\"");
/* 3610 */                                       out.print(request.getAttribute("type"));
/* 3611 */                                       out.write("\" />\n            <input type=hidden name=\"isAgentEnabled\" value=\"");
/* 3612 */                                       out.print(isAgentEnabled);
/* 3613 */                                       out.write("\" />\n            <input type=hidden name=\"resourcename\" value=\"");
/* 3614 */                                       out.print(resourceName);
/* 3615 */                                       out.write("\" />\n            <input type=hidden name=\"isAgentAssociated\" value=\"");
/* 3616 */                                       out.print(isAgentAssociated);
/* 3617 */                                       out.write("\" />\n        <input type=\"hidden\" name=\"hideFieldsForIT360\" value=\"");
/* 3618 */                                       out.print(request.getParameter("hideFieldsForIT360"));
/* 3619 */                                       out.write("\">\n            ");
/*      */                                       
/* 3621 */                                       String displayname_append = "";
/* 3622 */                                       String pollinterval_append = "value='5'";
/* 3623 */                                       if (argsasprops.size() > 0)
/*      */                                       {
/* 3625 */                                         displayname_append = "value='" + argsasprops.getProperty("DisplayName") + "'";
/* 3626 */                                         pollinterval_append = "value='" + argsasprops.getProperty("pollinterval") + "'";
/*      */                                       }
/* 3628 */                                       if (!resourceid.equals(""))
/*      */                                       {
/* 3630 */                                         addmonitors = FormatUtil.getString("am.webclient.newscript.updatescript.text");
/*      */                                         
/* 3632 */                                         out.write("\n                <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr> <td width=\"72%\" height=\"29\" class=\"tableheading\" valign=\"center\">");
/* 3633 */                                         out.print(FormatUtil.getString("Edit Monitor"));
/* 3634 */                                         out.write("</td></tr></table>\n                ");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3640 */                                         out.write("\n            ");
/* 3641 */                                         out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link rel=\"stylesheet\" href=\"/images/chosen.css\" />\n");
/* 3642 */                                         String message = (String)request.getAttribute("typemessage");
/*      */                                         
/* 3644 */                                         ManagedApplication mo1 = new ManagedApplication();
/* 3645 */                                         Properties props = com.adventnet.appmanager.util.Constants.getValueForNewMonitor();
/* 3646 */                                         boolean isConfMonitor = false;
/* 3647 */                                         ConfMonitorConfiguration confMonConfig = ConfMonitorConfiguration.getInstance();
/* 3648 */                                         if (message != null)
/*      */                                         {
/* 3650 */                                           out.write("\n    <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n    <tr>\n      <td><img src=\"/images/icon_message_success.gif\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"bodytext\">\n        ");
/* 3651 */                                           out.print(message);
/* 3652 */                                           out.write("</span> </td>\n    </tr>\n  </table>\n     ");
/*      */                                         }
/*      */                                         
/*      */ 
/* 3656 */                                         out.write("\n\n\n<table id=\"newResourceTypes\" width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n\t<td width=\"25%\" class=\"tableheading-monitor-config bodytext label-align addmonitor-label\">&nbsp;");
/* 3657 */                                         out.print(FormatUtil.getString("am.webclient.newresourcetypes.addmonitor.text"));
/* 3658 */                                         out.write("</td>\n    <td class=\"tableheading-monitor-config \" valign=\"middle\">\n");
/* 3659 */                                         if ("UrlSeq".equals(request.getParameter("type"))) {
/* 3660 */                                           DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 3661 */                                           if (frm != null) {
/* 3662 */                                             frm.set("type", "UrlSeq");
/*      */                                           }
/*      */                                         }
/*      */                                         
/* 3666 */                                         if ("UrlMonitor".equals(request.getParameter("type"))) {
/* 3667 */                                           DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 3668 */                                           if (frm != null) {
/* 3669 */                                             frm.set("type", "UrlMonitor");
/*      */                                           }
/*      */                                         }
/*      */                                         
/* 3673 */                                         if ("RBM".equals(request.getParameter("type"))) {
/* 3674 */                                           DynaActionForm frm = (DynaActionForm)request.getAttribute("RbmForm");
/* 3675 */                                           if (frm != null) {
/* 3676 */                                             frm.set("type", "RBM");
/*      */                                           }
/*      */                                         }
/*      */                                         
/*      */ 
/* 3681 */                                         out.write("\n\n    ");
/*      */                                         
/* 3683 */                                         IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3684 */                                         _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3685 */                                         _jspx_th_c_005fif_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */                                         
/* 3687 */                                         _jspx_th_c_005fif_005f6.setTest("${empty param.wiz && empty param.fromAssociate}");
/* 3688 */                                         int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3689 */                                         if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                           for (;;) {
/* 3691 */                                             out.write("\n     ");
/*      */                                             
/* 3693 */                                             SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 3694 */                                             _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3695 */                                             _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                                             
/* 3697 */                                             _jspx_th_html_005fselect_005f0.setProperty("type");
/*      */                                             
/* 3699 */                                             _jspx_th_html_005fselect_005f0.setStyle("background-color:#FDFEF2; font-size:13px;");
/*      */                                             
/* 3701 */                                             _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */                                             
/* 3703 */                                             _jspx_th_html_005fselect_005f0.setOnchange("javascript:formReload()");
/* 3704 */                                             int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3705 */                                             if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3706 */                                               if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3707 */                                                 out = _jspx_page_context.pushBody();
/* 3708 */                                                 _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3709 */                                                 _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3712 */                                                 out.write("\n\n      <!-- If you are changing any of the values in this select box then kindly update the corresponding strings in HostDiscoveryHandler.java also-->\n      ");
/*      */                                                 
/* 3714 */                                                 if ((!com.adventnet.appmanager.util.Constants.isMinimizedversion()) || (com.adventnet.appmanager.util.Constants.getUserType().equals("F")) || (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                                 {
/*      */ 
/*      */ 
/* 3718 */                                                   out.write("\n\n\t <optgroup label=\"");
/* 3719 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3720 */                                                   out.write("\">\n                                        \n                                        ");
/*      */                                                   
/* 3722 */                                                   OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3723 */                                                   _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 3724 */                                                   _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3726 */                                                   _jspx_th_html_005foption_005f0.setValue("AIX");
/* 3727 */                                                   int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 3728 */                                                   if (_jspx_eval_html_005foption_005f0 != 0) {
/* 3729 */                                                     if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3730 */                                                       out = _jspx_page_context.pushBody();
/* 3731 */                                                       _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 3732 */                                                       _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3735 */                                                       out.print(FormatUtil.getString("AIX"));
/* 3736 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 3737 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3740 */                                                     if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3741 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3744 */                                                   if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3745 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                                   }
/*      */                                                   
/* 3748 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 3749 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 3751 */                                                   OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3752 */                                                   _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3753 */                                                   _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3755 */                                                   _jspx_th_html_005foption_005f1.setValue("AS400");
/* 3756 */                                                   int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3757 */                                                   if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3758 */                                                     if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3759 */                                                       out = _jspx_page_context.pushBody();
/* 3760 */                                                       _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3761 */                                                       _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3764 */                                                       out.print(FormatUtil.getString("AS400/iSeries"));
/* 3765 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3766 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3769 */                                                     if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3770 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3773 */                                                   if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3774 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                                   }
/*      */                                                   
/* 3777 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 3778 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 3780 */                                                   OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3781 */                                                   _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 3782 */                                                   _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3784 */                                                   _jspx_th_html_005foption_005f2.setValue("FreeBSD");
/* 3785 */                                                   int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 3786 */                                                   if (_jspx_eval_html_005foption_005f2 != 0) {
/* 3787 */                                                     if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3788 */                                                       out = _jspx_page_context.pushBody();
/* 3789 */                                                       _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 3790 */                                                       _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3793 */                                                       out.print(FormatUtil.getString("FreeBSD/OpenBSD"));
/* 3794 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 3795 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3798 */                                                     if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3799 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3802 */                                                   if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 3803 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                                   }
/*      */                                                   
/* 3806 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 3807 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 3809 */                                                   OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3810 */                                                   _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 3811 */                                                   _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3813 */                                                   _jspx_th_html_005foption_005f3.setValue("HP-UX");
/* 3814 */                                                   int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 3815 */                                                   if (_jspx_eval_html_005foption_005f3 != 0) {
/* 3816 */                                                     if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3817 */                                                       out = _jspx_page_context.pushBody();
/* 3818 */                                                       _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 3819 */                                                       _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3822 */                                                       out.print(FormatUtil.getString("HP-UX  / Tru64"));
/* 3823 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 3824 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3827 */                                                     if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3828 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3831 */                                                   if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 3832 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                                   }
/*      */                                                   
/* 3835 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 3836 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 3838 */                                                   OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3839 */                                                   _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 3840 */                                                   _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3842 */                                                   _jspx_th_html_005foption_005f4.setValue("Linux");
/* 3843 */                                                   int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 3844 */                                                   if (_jspx_eval_html_005foption_005f4 != 0) {
/* 3845 */                                                     if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3846 */                                                       out = _jspx_page_context.pushBody();
/* 3847 */                                                       _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 3848 */                                                       _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3851 */                                                       out.print(FormatUtil.getString("Linux"));
/* 3852 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 3853 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3856 */                                                     if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3857 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3860 */                                                   if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 3861 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                                   }
/*      */                                                   
/* 3864 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 3865 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 3867 */                                                   OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3868 */                                                   _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 3869 */                                                   _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3871 */                                                   _jspx_th_html_005foption_005f5.setValue("Mac OS");
/* 3872 */                                                   int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 3873 */                                                   if (_jspx_eval_html_005foption_005f5 != 0) {
/* 3874 */                                                     if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3875 */                                                       out = _jspx_page_context.pushBody();
/* 3876 */                                                       _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 3877 */                                                       _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3880 */                                                       out.print(FormatUtil.getString("Mac OS"));
/* 3881 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 3882 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3885 */                                                     if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3886 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3889 */                                                   if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 3890 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                                   }
/*      */                                                   
/* 3893 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 3894 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 3896 */                                                   OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3897 */                                                   _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 3898 */                                                   _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3900 */                                                   _jspx_th_html_005foption_005f6.setValue("Novell");
/* 3901 */                                                   int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 3902 */                                                   if (_jspx_eval_html_005foption_005f6 != 0) {
/* 3903 */                                                     if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3904 */                                                       out = _jspx_page_context.pushBody();
/* 3905 */                                                       _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 3906 */                                                       _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3909 */                                                       out.print(FormatUtil.getString("Novell"));
/* 3910 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 3911 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3914 */                                                     if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3915 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3918 */                                                   if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 3919 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                                   }
/*      */                                                   
/* 3922 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 3923 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 3925 */                                                   OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3926 */                                                   _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 3927 */                                                   _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3929 */                                                   _jspx_th_html_005foption_005f7.setValue("Sun Solaris");
/* 3930 */                                                   int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 3931 */                                                   if (_jspx_eval_html_005foption_005f7 != 0) {
/* 3932 */                                                     if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3933 */                                                       out = _jspx_page_context.pushBody();
/* 3934 */                                                       _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 3935 */                                                       _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3938 */                                                       out.print(FormatUtil.getString("Sun Solaris"));
/* 3939 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 3940 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3943 */                                                     if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3944 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3947 */                                                   if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 3948 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                                   }
/*      */                                                   
/* 3951 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 3952 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 3954 */                                                   OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3955 */                                                   _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 3956 */                                                   _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3958 */                                                   _jspx_th_html_005foption_005f8.setValue("Windows");
/* 3959 */                                                   int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 3960 */                                                   if (_jspx_eval_html_005foption_005f8 != 0) {
/* 3961 */                                                     if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3962 */                                                       out = _jspx_page_context.pushBody();
/* 3963 */                                                       _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 3964 */                                                       _jspx_th_html_005foption_005f8.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3967 */                                                       out.print(FormatUtil.getString("Windows"));
/* 3968 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 3969 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3972 */                                                     if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3973 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3976 */                                                   if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 3977 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                                   }
/*      */                                                   
/* 3980 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/* 3981 */                                                   out.write("\n                                        ");
/*      */                                                   
/* 3983 */                                                   OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3984 */                                                   _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 3985 */                                                   _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3987 */                                                   _jspx_th_html_005foption_005f9.setValue("Windows Cluster");
/* 3988 */                                                   int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 3989 */                                                   if (_jspx_eval_html_005foption_005f9 != 0) {
/* 3990 */                                                     if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3991 */                                                       out = _jspx_page_context.pushBody();
/* 3992 */                                                       _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 3993 */                                                       _jspx_th_html_005foption_005f9.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3996 */                                                       out.print(FormatUtil.getString("Windows Cluster"));
/* 3997 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 3998 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4001 */                                                     if (_jspx_eval_html_005foption_005f9 != 1) {
/* 4002 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4005 */                                                   if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 4006 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                                   }
/*      */                                                   
/* 4009 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/* 4010 */                                                   out.write("\n                                        \n\n  ");
/*      */                                                   
/* 4012 */                                                   ArrayList rows1 = mo1.getRows("select RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH from AM_ManagedResourceType,AM_MONITOR_TYPES where TYPEID=RESOURCETYPEID and RESOURCEGROUP='SYS' and AMCREATED='NO' ORDER BY UPPER(DISPLAYNAME)");
/* 4013 */                                                   if ((rows1 != null) && (rows1.size() > 0))
/*      */                                                   {
/* 4015 */                                                     for (int i = 0; i < rows1.size(); i++)
/*      */                                                     {
/* 4017 */                                                       ArrayList row = (ArrayList)rows1.get(i);
/* 4018 */                                                       String res = (String)row.get(0);
/* 4019 */                                                       String dname = (String)row.get(1);
/* 4020 */                                                       String values = props.getProperty(res);
/* 4021 */                                                       if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                       {
/*      */ 
/* 4024 */                                                         out.write("\n\t\t\t\t");
/*      */                                                         
/* 4026 */                                                         OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4027 */                                                         _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 4028 */                                                         _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                         
/* 4030 */                                                         _jspx_th_html_005foption_005f10.setValue(values);
/* 4031 */                                                         int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 4032 */                                                         if (_jspx_eval_html_005foption_005f10 != 0) {
/* 4033 */                                                           if (_jspx_eval_html_005foption_005f10 != 1) {
/* 4034 */                                                             out = _jspx_page_context.pushBody();
/* 4035 */                                                             _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/* 4036 */                                                             _jspx_th_html_005foption_005f10.doInitBody();
/*      */                                                           }
/*      */                                                           for (;;) {
/* 4039 */                                                             out.write(32);
/* 4040 */                                                             out.print(FormatUtil.getString(dname));
/* 4041 */                                                             int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/* 4042 */                                                             if (evalDoAfterBody != 2)
/*      */                                                               break;
/*      */                                                           }
/* 4045 */                                                           if (_jspx_eval_html_005foption_005f10 != 1) {
/* 4046 */                                                             out = _jspx_page_context.popBody();
/*      */                                                           }
/*      */                                                         }
/* 4049 */                                                         if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 4050 */                                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                                         }
/*      */                                                         
/* 4053 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/* 4054 */                                                         out.write("\n\t\t\t");
/*      */                                                       }
/*      */                                                     }
/*      */                                                   }
/*      */                                                   
/*      */ 
/* 4060 */                                                   String[] categoryLink = { "APP", "ERP", "TM", "SYS", "DBS", "SER", "URL", "MS", "MOM", "CAM", "VIR", "CLD" };
/*      */                                                   
/* 4062 */                                                   String[] categoryTitle = { "am.webclient.monitorgroupsecond.category.appserver", "am.webclient.monitorgroupsecond.category.erp", "am.webclient.monitorgroupsecond.category.transaction", "am.webclient.monitorgroupsecond.category.servers", "am.webclient.monitorgroupsecond.category.dbserver", "am.webclient.monitorgroupsecond.category.services", "am.webclient.monitorgroupsecond.category.webservices.title", "am.webclient.monitorgroupsecond.category.mailserver", "Middleware/Portal", "am.webclient.monitorgroupsecond.category.custom", "am.webclient.monitorgroupsecond.category.virtualserver", "am.webclient.monitorgroupsecond.category.cloudapps" };
/*      */                                                   
/*      */ 
/* 4065 */                                                   if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD"))
/*      */                                                   {
/*      */ 
/* 4068 */                                                     categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 4069 */                                                     categoryTitle = com.adventnet.appmanager.util.Constants.categoryTitle;
/*      */                                                   }
/* 4071 */                                                   for (int c = 0; c < categoryLink.length; c++)
/*      */                                                   {
/* 4073 */                                                     ArrayList unSupportedTypes = com.adventnet.appmanager.util.Constants.getUnSupportedAsList();
/* 4074 */                                                     if ((!categoryLink[c].equals("SYS")) && (!categoryLink[c].equals("NWD")) && (!categoryLink[c].equals("SAN")) && (!categoryLink[c].equals("EMO")) && (!categoryLink[c].equals("SCR")) && ((!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")) || (!unSupportedTypes.contains(categoryLink[c]))))
/*      */                                                     {
/*      */ 
/*      */ 
/* 4078 */                                                       StringBuffer queryBuf = new StringBuffer();
/* 4079 */                                                       queryBuf.append("SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='");
/* 4080 */                                                       queryBuf.append(categoryLink[c] + "'");
/* 4081 */                                                       queryBuf.append(" ");
/* 4082 */                                                       queryBuf.append("and RESOURCETYPE in");
/* 4083 */                                                       queryBuf.append(" ");
/* 4084 */                                                       queryBuf.append(com.adventnet.appmanager.util.Constants.resourceTypes);
/* 4085 */                                                       if (categoryLink[c].equals("APP"))
/*      */                                                       {
/* 4087 */                                                         queryBuf.append(" ");
/* 4088 */                                                         queryBuf.append("and DISPLAYNAME NOT IN ('WebLogic Clusters')");
/* 4089 */                                                         queryBuf.append(" ");
/*      */                                                       }
/* 4091 */                                                       else if (categoryLink[c].equals("SER"))
/*      */                                                       {
/* 4093 */                                                         queryBuf.append(" ");
/* 4094 */                                                         queryBuf.append("and RESOURCETYPE<>'RMI'");
/* 4095 */                                                         queryBuf.append(" ");
/*      */                                                       }
/* 4097 */                                                       else if (categoryLink[c].equals("CAM"))
/*      */                                                       {
/* 4099 */                                                         queryBuf.append(" ");
/* 4100 */                                                         queryBuf.append("and RESOURCETYPE != 'directory'");
/* 4101 */                                                         queryBuf.append(" ");
/*      */                                                       }
/* 4103 */                                                       queryBuf.append(" ");
/* 4104 */                                                       queryBuf.append("ORDER BY UPPER(DISPLAYNAME)");
/* 4105 */                                                       ArrayList rows = mo1.getRows(queryBuf.toString());
/* 4106 */                                                       if ((rows != null) && (rows.size() != 0))
/*      */                                                       {
/*      */ 
/*      */ 
/*      */ 
/* 4111 */                                                         out.write("\n</optgroup>\t\t\t\t<optgroup label=\"");
/* 4112 */                                                         out.print(FormatUtil.getString(categoryTitle[c]));
/* 4113 */                                                         out.write(34);
/* 4114 */                                                         out.write(62);
/* 4115 */                                                         out.write(10);
/*      */                                                         
/*      */ 
/* 4118 */                                                         for (int i = 0; i < rows.size(); ???++)
/*      */                                                         {
/* 4120 */                                                           ArrayList row = (ArrayList)rows.get(i);
/* 4121 */                                                           String res = (String)row.get(0);
/* 4122 */                                                           if (res.equals("file"))
/*      */                                                           {
/* 4124 */                                                             res = "File / Directory Monitor";
/*      */                                                           }
/* 4126 */                                                           String dname = (String)row.get(1);
/* 4127 */                                                           String values = props.getProperty(res);
/* 4128 */                                                           if ((!EnterpriseUtil.isCloudEdition()) || (!unSupportedTypes.contains(values)))
/*      */                                                           {
/*      */ 
/* 4131 */                                                             if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                             {
/*      */ 
/* 4134 */                                                               out.write("\n\t\t\t\t \t");
/*      */                                                               
/* 4136 */                                                               OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4137 */                                                               _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 4138 */                                                               _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                               
/* 4140 */                                                               _jspx_th_html_005foption_005f11.setValue(values);
/* 4141 */                                                               int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 4142 */                                                               if (_jspx_eval_html_005foption_005f11 != 0) {
/* 4143 */                                                                 if (_jspx_eval_html_005foption_005f11 != 1) {
/* 4144 */                                                                   out = _jspx_page_context.pushBody();
/* 4145 */                                                                   _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/* 4146 */                                                                   _jspx_th_html_005foption_005f11.doInitBody();
/*      */                                                                 }
/*      */                                                                 for (;;) {
/* 4149 */                                                                   out.write(32);
/* 4150 */                                                                   out.print(FormatUtil.getString(dname));
/* 4151 */                                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/* 4152 */                                                                   if (evalDoAfterBody != 2)
/*      */                                                                     break;
/*      */                                                                 }
/* 4155 */                                                                 if (_jspx_eval_html_005foption_005f11 != 1) {
/* 4156 */                                                                   out = _jspx_page_context.popBody();
/*      */                                                                 }
/*      */                                                               }
/* 4159 */                                                               if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 4160 */                                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                                                               }
/*      */                                                               
/* 4163 */                                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11);
/* 4164 */                                                               out.write("\n\t\t\t\t");
/*      */                                                             }
/*      */                                                           }
/*      */                                                         }
/*      */                                                         
/* 4169 */                                                         if (categoryLink[c].equals("VIR"))
/*      */                                                         {
/*      */ 
/* 4172 */                                                           out.write("\n\t\t\t\t\t");
/*      */                                                           
/* 4174 */                                                           OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4175 */                                                           _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 4176 */                                                           _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                           
/* 4178 */                                                           _jspx_th_html_005foption_005f12.setValue("VCenter");
/* 4179 */                                                           int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 4180 */                                                           if (_jspx_eval_html_005foption_005f12 != 0) {
/* 4181 */                                                             if (_jspx_eval_html_005foption_005f12 != 1) {
/* 4182 */                                                               out = _jspx_page_context.pushBody();
/* 4183 */                                                               _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/* 4184 */                                                               _jspx_th_html_005foption_005f12.doInitBody();
/*      */                                                             }
/*      */                                                             for (;;) {
/* 4187 */                                                               out.write(32);
/* 4188 */                                                               out.print(FormatUtil.getString("am.webclient.addmonitor.vcenter.name"));
/* 4189 */                                                               int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/* 4190 */                                                               if (evalDoAfterBody != 2)
/*      */                                                                 break;
/*      */                                                             }
/* 4193 */                                                             if (_jspx_eval_html_005foption_005f12 != 1) {
/* 4194 */                                                               out = _jspx_page_context.popBody();
/*      */                                                             }
/*      */                                                           }
/* 4197 */                                                           if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 4198 */                                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                                                           }
/*      */                                                           
/* 4201 */                                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/* 4202 */                                                           out.write("\n\t\t\t\t");
/*      */                                                         }
/*      */                                                       }
/*      */                                                     } }
/* 4206 */                                                   String usertype = FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/* 4207 */                                                   if (!usertype.equals("F"))
/*      */                                                   {
/* 4209 */                                                     if (((!EnterpriseUtil.isIt360MSPEdition()) || (!DBUtil.isSharedProbe())) && (!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                                     {
/* 4211 */                                                       out.write("\n    </optgroup> <optgroup label=\"");
/* 4212 */                                                       out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 4213 */                                                       out.write("\">\n     ");
/*      */                                                       
/* 4215 */                                                       OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4216 */                                                       _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/* 4217 */                                                       _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4219 */                                                       _jspx_th_html_005foption_005f13.setValue("All:1008");
/* 4220 */                                                       int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/* 4221 */                                                       if (_jspx_eval_html_005foption_005f13 != 0) {
/* 4222 */                                                         if (_jspx_eval_html_005foption_005f13 != 1) {
/* 4223 */                                                           out = _jspx_page_context.pushBody();
/* 4224 */                                                           _jspx_th_html_005foption_005f13.setBodyContent((BodyContent)out);
/* 4225 */                                                           _jspx_th_html_005foption_005f13.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4228 */                                                           out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 4229 */                                                           out.write(32);
/* 4230 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f13.doAfterBody();
/* 4231 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4234 */                                                         if (_jspx_eval_html_005foption_005f13 != 1) {
/* 4235 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4238 */                                                       if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/* 4239 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                                                       }
/*      */                                                       
/* 4242 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/* 4243 */                                                       out.write("\n\n     ");
/*      */                                                     }
/*      */                                                     
/*      */                                                   }
/*      */                                                   
/*      */                                                 }
/* 4249 */                                                 else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("LAMP"))
/*      */                                                 {
/*      */ 
/* 4252 */                                                   out.write("\n\t\t\t </optgroup>  <optgroup label=\"");
/* 4253 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 4254 */                                                   out.write("\">\n\t\t\t   ");
/*      */                                                   
/* 4256 */                                                   OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4257 */                                                   _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/* 4258 */                                                   _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4260 */                                                   _jspx_th_html_005foption_005f14.setValue("SYSTEM:9999");
/* 4261 */                                                   int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/* 4262 */                                                   if (_jspx_eval_html_005foption_005f14 != 0) {
/* 4263 */                                                     if (_jspx_eval_html_005foption_005f14 != 1) {
/* 4264 */                                                       out = _jspx_page_context.pushBody();
/* 4265 */                                                       _jspx_th_html_005foption_005f14.setBodyContent((BodyContent)out);
/* 4266 */                                                       _jspx_th_html_005foption_005f14.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4269 */                                                       out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4270 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f14.doAfterBody();
/* 4271 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4274 */                                                     if (_jspx_eval_html_005foption_005f14 != 1) {
/* 4275 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4278 */                                                   if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/* 4279 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14); return;
/*      */                                                   }
/*      */                                                   
/* 4282 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/* 4283 */                                                   out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4284 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 4285 */                                                   out.write("\">\n\t\t\t   ");
/*      */                                                   
/* 4287 */                                                   OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4288 */                                                   _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/* 4289 */                                                   _jspx_th_html_005foption_005f15.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4291 */                                                   _jspx_th_html_005foption_005f15.setValue("MYSQLDB:3306");
/* 4292 */                                                   int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/* 4293 */                                                   if (_jspx_eval_html_005foption_005f15 != 0) {
/* 4294 */                                                     if (_jspx_eval_html_005foption_005f15 != 1) {
/* 4295 */                                                       out = _jspx_page_context.pushBody();
/* 4296 */                                                       _jspx_th_html_005foption_005f15.setBodyContent((BodyContent)out);
/* 4297 */                                                       _jspx_th_html_005foption_005f15.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4300 */                                                       out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 4301 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f15.doAfterBody();
/* 4302 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4305 */                                                     if (_jspx_eval_html_005foption_005f15 != 1) {
/* 4306 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4309 */                                                   if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/* 4310 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15); return;
/*      */                                                   }
/*      */                                                   
/* 4313 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/* 4314 */                                                   out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4315 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 4316 */                                                   out.write("\">\n\t\t\t   ");
/*      */                                                   
/* 4318 */                                                   OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4319 */                                                   _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/* 4320 */                                                   _jspx_th_html_005foption_005f16.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4322 */                                                   _jspx_th_html_005foption_005f16.setValue("JMX1.2-MX4J-RMI:1099");
/* 4323 */                                                   int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/* 4324 */                                                   if (_jspx_eval_html_005foption_005f16 != 0) {
/* 4325 */                                                     if (_jspx_eval_html_005foption_005f16 != 1) {
/* 4326 */                                                       out = _jspx_page_context.pushBody();
/* 4327 */                                                       _jspx_th_html_005foption_005f16.setBodyContent((BodyContent)out);
/* 4328 */                                                       _jspx_th_html_005foption_005f16.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4331 */                                                       out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 4332 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f16.doAfterBody();
/* 4333 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4336 */                                                     if (_jspx_eval_html_005foption_005f16 != 1) {
/* 4337 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4340 */                                                   if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/* 4341 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16); return;
/*      */                                                   }
/*      */                                                   
/* 4344 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/* 4345 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 4347 */                                                   OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4348 */                                                   _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/* 4349 */                                                   _jspx_th_html_005foption_005f17.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4351 */                                                   _jspx_th_html_005foption_005f17.setValue("SERVICE:9090");
/* 4352 */                                                   int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/* 4353 */                                                   if (_jspx_eval_html_005foption_005f17 != 0) {
/* 4354 */                                                     if (_jspx_eval_html_005foption_005f17 != 1) {
/* 4355 */                                                       out = _jspx_page_context.pushBody();
/* 4356 */                                                       _jspx_th_html_005foption_005f17.setBodyContent((BodyContent)out);
/* 4357 */                                                       _jspx_th_html_005foption_005f17.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4360 */                                                       out.write(32);
/* 4361 */                                                       out.print(FormatUtil.getString("Service Monitoring"));
/* 4362 */                                                       out.write(32);
/* 4363 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f17.doAfterBody();
/* 4364 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4367 */                                                     if (_jspx_eval_html_005foption_005f17 != 1) {
/* 4368 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4371 */                                                   if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/* 4372 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17); return;
/*      */                                                   }
/*      */                                                   
/* 4375 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/* 4376 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 4378 */                                                   OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4379 */                                                   _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/* 4380 */                                                   _jspx_th_html_005foption_005f18.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4382 */                                                   _jspx_th_html_005foption_005f18.setValue("RMI:1099");
/* 4383 */                                                   int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/* 4384 */                                                   if (_jspx_eval_html_005foption_005f18 != 0) {
/* 4385 */                                                     if (_jspx_eval_html_005foption_005f18 != 1) {
/* 4386 */                                                       out = _jspx_page_context.pushBody();
/* 4387 */                                                       _jspx_th_html_005foption_005f18.setBodyContent((BodyContent)out);
/* 4388 */                                                       _jspx_th_html_005foption_005f18.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4391 */                                                       out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 4392 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f18.doAfterBody();
/* 4393 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4396 */                                                     if (_jspx_eval_html_005foption_005f18 != 1) {
/* 4397 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4400 */                                                   if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/* 4401 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18); return;
/*      */                                                   }
/*      */                                                   
/* 4404 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/* 4405 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 4407 */                                                   OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4408 */                                                   _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/* 4409 */                                                   _jspx_th_html_005foption_005f19.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4411 */                                                   _jspx_th_html_005foption_005f19.setValue("SNMP:161");
/* 4412 */                                                   int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/* 4413 */                                                   if (_jspx_eval_html_005foption_005f19 != 0) {
/* 4414 */                                                     if (_jspx_eval_html_005foption_005f19 != 1) {
/* 4415 */                                                       out = _jspx_page_context.pushBody();
/* 4416 */                                                       _jspx_th_html_005foption_005f19.setBodyContent((BodyContent)out);
/* 4417 */                                                       _jspx_th_html_005foption_005f19.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4420 */                                                       out.print(FormatUtil.getString("SNMP"));
/* 4421 */                                                       out.write(" (V1 or V2c)");
/* 4422 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f19.doAfterBody();
/* 4423 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4426 */                                                     if (_jspx_eval_html_005foption_005f19 != 1) {
/* 4427 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4430 */                                                   if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/* 4431 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19); return;
/*      */                                                   }
/*      */                                                   
/* 4434 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/* 4435 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 4437 */                                                   OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4438 */                                                   _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/* 4439 */                                                   _jspx_th_html_005foption_005f20.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4441 */                                                   _jspx_th_html_005foption_005f20.setValue("TELNET:23");
/* 4442 */                                                   int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/* 4443 */                                                   if (_jspx_eval_html_005foption_005f20 != 0) {
/* 4444 */                                                     if (_jspx_eval_html_005foption_005f20 != 1) {
/* 4445 */                                                       out = _jspx_page_context.pushBody();
/* 4446 */                                                       _jspx_th_html_005foption_005f20.setBodyContent((BodyContent)out);
/* 4447 */                                                       _jspx_th_html_005foption_005f20.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4450 */                                                       out.print(FormatUtil.getString("Telnet"));
/* 4451 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f20.doAfterBody();
/* 4452 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4455 */                                                     if (_jspx_eval_html_005foption_005f20 != 1) {
/* 4456 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4459 */                                                   if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/* 4460 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20); return;
/*      */                                                   }
/*      */                                                   
/* 4463 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/* 4464 */                                                   out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4465 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 4466 */                                                   out.write("\">\n\t\t\t   ");
/*      */                                                   
/* 4468 */                                                   OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4469 */                                                   _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/* 4470 */                                                   _jspx_th_html_005foption_005f21.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4472 */                                                   _jspx_th_html_005foption_005f21.setValue("APACHE:80");
/* 4473 */                                                   int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/* 4474 */                                                   if (_jspx_eval_html_005foption_005f21 != 0) {
/* 4475 */                                                     if (_jspx_eval_html_005foption_005f21 != 1) {
/* 4476 */                                                       out = _jspx_page_context.pushBody();
/* 4477 */                                                       _jspx_th_html_005foption_005f21.setBodyContent((BodyContent)out);
/* 4478 */                                                       _jspx_th_html_005foption_005f21.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4481 */                                                       out.write(32);
/* 4482 */                                                       out.print(FormatUtil.getString("Apache Server"));
/* 4483 */                                                       out.write(32);
/* 4484 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f21.doAfterBody();
/* 4485 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4488 */                                                     if (_jspx_eval_html_005foption_005f21 != 1) {
/* 4489 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4492 */                                                   if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/* 4493 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21); return;
/*      */                                                   }
/*      */                                                   
/* 4496 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/* 4497 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 4499 */                                                   OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4500 */                                                   _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/* 4501 */                                                   _jspx_th_html_005foption_005f22.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4503 */                                                   _jspx_th_html_005foption_005f22.setValue("PHP:80");
/* 4504 */                                                   int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/* 4505 */                                                   if (_jspx_eval_html_005foption_005f22 != 0) {
/* 4506 */                                                     if (_jspx_eval_html_005foption_005f22 != 1) {
/* 4507 */                                                       out = _jspx_page_context.pushBody();
/* 4508 */                                                       _jspx_th_html_005foption_005f22.setBodyContent((BodyContent)out);
/* 4509 */                                                       _jspx_th_html_005foption_005f22.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4512 */                                                       out.print(FormatUtil.getString("PHP"));
/* 4513 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f22.doAfterBody();
/* 4514 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4517 */                                                     if (_jspx_eval_html_005foption_005f22 != 1) {
/* 4518 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4521 */                                                   if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/* 4522 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22); return;
/*      */                                                   }
/*      */                                                   
/* 4525 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/* 4526 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 4528 */                                                   OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4529 */                                                   _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/* 4530 */                                                   _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4532 */                                                   _jspx_th_html_005foption_005f23.setValue("UrlMonitor");
/* 4533 */                                                   int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/* 4534 */                                                   if (_jspx_eval_html_005foption_005f23 != 0) {
/* 4535 */                                                     if (_jspx_eval_html_005foption_005f23 != 1) {
/* 4536 */                                                       out = _jspx_page_context.pushBody();
/* 4537 */                                                       _jspx_th_html_005foption_005f23.setBodyContent((BodyContent)out);
/* 4538 */                                                       _jspx_th_html_005foption_005f23.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4541 */                                                       out.print(FormatUtil.getString("HTTP-URLs"));
/* 4542 */                                                       out.write(32);
/* 4543 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f23.doAfterBody();
/* 4544 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4547 */                                                     if (_jspx_eval_html_005foption_005f23 != 1) {
/* 4548 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4551 */                                                   if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/* 4552 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23); return;
/*      */                                                   }
/*      */                                                   
/* 4555 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23);
/* 4556 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 4558 */                                                   OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4559 */                                                   _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/* 4560 */                                                   _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4562 */                                                   _jspx_th_html_005foption_005f24.setValue("UrlSeq");
/* 4563 */                                                   int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/* 4564 */                                                   if (_jspx_eval_html_005foption_005f24 != 0) {
/* 4565 */                                                     if (_jspx_eval_html_005foption_005f24 != 1) {
/* 4566 */                                                       out = _jspx_page_context.pushBody();
/* 4567 */                                                       _jspx_th_html_005foption_005f24.setBodyContent((BodyContent)out);
/* 4568 */                                                       _jspx_th_html_005foption_005f24.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4571 */                                                       out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 4572 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f24.doAfterBody();
/* 4573 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4576 */                                                     if (_jspx_eval_html_005foption_005f24 != 1) {
/* 4577 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4580 */                                                   if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/* 4581 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24); return;
/*      */                                                   }
/*      */                                                   
/* 4584 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24);
/* 4585 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 4587 */                                                   OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4588 */                                                   _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/* 4589 */                                                   _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4591 */                                                   _jspx_th_html_005foption_005f25.setValue("WEB:80");
/* 4592 */                                                   int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/* 4593 */                                                   if (_jspx_eval_html_005foption_005f25 != 0) {
/* 4594 */                                                     if (_jspx_eval_html_005foption_005f25 != 1) {
/* 4595 */                                                       out = _jspx_page_context.pushBody();
/* 4596 */                                                       _jspx_th_html_005foption_005f25.setBodyContent((BodyContent)out);
/* 4597 */                                                       _jspx_th_html_005foption_005f25.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4600 */                                                       out.write(32);
/* 4601 */                                                       out.print(FormatUtil.getString("Web Server"));
/* 4602 */                                                       out.write(32);
/* 4603 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f25.doAfterBody();
/* 4604 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4607 */                                                     if (_jspx_eval_html_005foption_005f25 != 1) {
/* 4608 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4611 */                                                   if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/* 4612 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25); return;
/*      */                                                   }
/*      */                                                   
/* 4615 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25);
/* 4616 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 4618 */                                                   OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4619 */                                                   _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/* 4620 */                                                   _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4622 */                                                   _jspx_th_html_005foption_005f26.setValue("Web Service");
/* 4623 */                                                   int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/* 4624 */                                                   if (_jspx_eval_html_005foption_005f26 != 0) {
/* 4625 */                                                     if (_jspx_eval_html_005foption_005f26 != 1) {
/* 4626 */                                                       out = _jspx_page_context.pushBody();
/* 4627 */                                                       _jspx_th_html_005foption_005f26.setBodyContent((BodyContent)out);
/* 4628 */                                                       _jspx_th_html_005foption_005f26.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4631 */                                                       out.write(32);
/* 4632 */                                                       out.print(FormatUtil.getString("Web Service"));
/* 4633 */                                                       out.write(32);
/* 4634 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f26.doAfterBody();
/* 4635 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4638 */                                                     if (_jspx_eval_html_005foption_005f26 != 1) {
/* 4639 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4642 */                                                   if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/* 4643 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26); return;
/*      */                                                   }
/*      */                                                   
/* 4646 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26);
/* 4647 */                                                   out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4648 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 4649 */                                                   out.write("\">\n\t\t\t   ");
/*      */                                                   
/* 4651 */                                                   OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4652 */                                                   _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/* 4653 */                                                   _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4655 */                                                   _jspx_th_html_005foption_005f27.setValue("MAIL:25");
/* 4656 */                                                   int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/* 4657 */                                                   if (_jspx_eval_html_005foption_005f27 != 0) {
/* 4658 */                                                     if (_jspx_eval_html_005foption_005f27 != 1) {
/* 4659 */                                                       out = _jspx_page_context.pushBody();
/* 4660 */                                                       _jspx_th_html_005foption_005f27.setBodyContent((BodyContent)out);
/* 4661 */                                                       _jspx_th_html_005foption_005f27.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4664 */                                                       out.print(FormatUtil.getString("Mail Server"));
/* 4665 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f27.doAfterBody();
/* 4666 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4669 */                                                     if (_jspx_eval_html_005foption_005f27 != 1) {
/* 4670 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4673 */                                                   if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/* 4674 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27); return;
/*      */                                                   }
/*      */                                                   
/* 4677 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27);
/* 4678 */                                                   out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4679 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 4680 */                                                   out.write("\">\n\t\t\t   ");
/*      */                                                   
/* 4682 */                                                   OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4683 */                                                   _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/* 4684 */                                                   _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4686 */                                                   _jspx_th_html_005foption_005f28.setValue("Custom-Application");
/* 4687 */                                                   int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/* 4688 */                                                   if (_jspx_eval_html_005foption_005f28 != 0) {
/* 4689 */                                                     if (_jspx_eval_html_005foption_005f28 != 1) {
/* 4690 */                                                       out = _jspx_page_context.pushBody();
/* 4691 */                                                       _jspx_th_html_005foption_005f28.setBodyContent((BodyContent)out);
/* 4692 */                                                       _jspx_th_html_005foption_005f28.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4695 */                                                       out.write(32);
/* 4696 */                                                       out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4697 */                                                       out.write(32);
/* 4698 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f28.doAfterBody();
/* 4699 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4702 */                                                     if (_jspx_eval_html_005foption_005f28 != 1) {
/* 4703 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4706 */                                                   if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/* 4707 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28); return;
/*      */                                                   }
/*      */                                                   
/* 4710 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28);
/* 4711 */                                                   out.write("\n\t\t\t   ");
/*      */                                                   
/* 4713 */                                                   OptionTag _jspx_th_html_005foption_005f29 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4714 */                                                   _jspx_th_html_005foption_005f29.setPageContext(_jspx_page_context);
/* 4715 */                                                   _jspx_th_html_005foption_005f29.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4717 */                                                   _jspx_th_html_005foption_005f29.setValue("Script Monitor");
/* 4718 */                                                   int _jspx_eval_html_005foption_005f29 = _jspx_th_html_005foption_005f29.doStartTag();
/* 4719 */                                                   if (_jspx_eval_html_005foption_005f29 != 0) {
/* 4720 */                                                     if (_jspx_eval_html_005foption_005f29 != 1) {
/* 4721 */                                                       out = _jspx_page_context.pushBody();
/* 4722 */                                                       _jspx_th_html_005foption_005f29.setBodyContent((BodyContent)out);
/* 4723 */                                                       _jspx_th_html_005foption_005f29.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4726 */                                                       out.print(FormatUtil.getString("Script Monitor"));
/* 4727 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f29.doAfterBody();
/* 4728 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4731 */                                                     if (_jspx_eval_html_005foption_005f29 != 1) {
/* 4732 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4735 */                                                   if (_jspx_th_html_005foption_005f29.doEndTag() == 5) {
/* 4736 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29); return;
/*      */                                                   }
/*      */                                                   
/* 4739 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29);
/* 4740 */                                                   out.write("\n\n    ");
/*      */ 
/*      */                                                 }
/* 4743 */                                                 else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("J2EE"))
/*      */                                                 {
/*      */ 
/* 4746 */                                                   out.write("\n        ");
/*      */                                                   
/* 4748 */                                                   OptionTag _jspx_th_html_005foption_005f30 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4749 */                                                   _jspx_th_html_005foption_005f30.setPageContext(_jspx_page_context);
/* 4750 */                                                   _jspx_th_html_005foption_005f30.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4752 */                                                   _jspx_th_html_005foption_005f30.setValue("SYSTEM:9999");
/* 4753 */                                                   int _jspx_eval_html_005foption_005f30 = _jspx_th_html_005foption_005f30.doStartTag();
/* 4754 */                                                   if (_jspx_eval_html_005foption_005f30 != 0) {
/* 4755 */                                                     if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4756 */                                                       out = _jspx_page_context.pushBody();
/* 4757 */                                                       _jspx_th_html_005foption_005f30.setBodyContent((BodyContent)out);
/* 4758 */                                                       _jspx_th_html_005foption_005f30.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4761 */                                                       out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4762 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f30.doAfterBody();
/* 4763 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4766 */                                                     if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4767 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4770 */                                                   if (_jspx_th_html_005foption_005f30.doEndTag() == 5) {
/* 4771 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30); return;
/*      */                                                   }
/*      */                                                   
/* 4774 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30);
/* 4775 */                                                   out.write("\n       ");
/*      */                                                   
/* 4777 */                                                   OptionTag _jspx_th_html_005foption_005f31 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4778 */                                                   _jspx_th_html_005foption_005f31.setPageContext(_jspx_page_context);
/* 4779 */                                                   _jspx_th_html_005foption_005f31.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4781 */                                                   _jspx_th_html_005foption_005f31.setValue("JBoss:8080");
/* 4782 */                                                   int _jspx_eval_html_005foption_005f31 = _jspx_th_html_005foption_005f31.doStartTag();
/* 4783 */                                                   if (_jspx_eval_html_005foption_005f31 != 0) {
/* 4784 */                                                     if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4785 */                                                       out = _jspx_page_context.pushBody();
/* 4786 */                                                       _jspx_th_html_005foption_005f31.setBodyContent((BodyContent)out);
/* 4787 */                                                       _jspx_th_html_005foption_005f31.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4790 */                                                       out.write(32);
/* 4791 */                                                       out.print(FormatUtil.getString("JBoss Server"));
/* 4792 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f31.doAfterBody();
/* 4793 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4796 */                                                     if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4797 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4800 */                                                   if (_jspx_th_html_005foption_005f31.doEndTag() == 5) {
/* 4801 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31); return;
/*      */                                                   }
/*      */                                                   
/* 4804 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31);
/* 4805 */                                                   out.write("\n      ");
/*      */                                                   
/* 4807 */                                                   OptionTag _jspx_th_html_005foption_005f32 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4808 */                                                   _jspx_th_html_005foption_005f32.setPageContext(_jspx_page_context);
/* 4809 */                                                   _jspx_th_html_005foption_005f32.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4811 */                                                   _jspx_th_html_005foption_005f32.setValue("Tomcat:8080");
/* 4812 */                                                   int _jspx_eval_html_005foption_005f32 = _jspx_th_html_005foption_005f32.doStartTag();
/* 4813 */                                                   if (_jspx_eval_html_005foption_005f32 != 0) {
/* 4814 */                                                     if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4815 */                                                       out = _jspx_page_context.pushBody();
/* 4816 */                                                       _jspx_th_html_005foption_005f32.setBodyContent((BodyContent)out);
/* 4817 */                                                       _jspx_th_html_005foption_005f32.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4820 */                                                       out.print(FormatUtil.getString("Tomcat Server"));
/* 4821 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f32.doAfterBody();
/* 4822 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4825 */                                                     if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4826 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4829 */                                                   if (_jspx_th_html_005foption_005f32.doEndTag() == 5) {
/* 4830 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32); return;
/*      */                                                   }
/*      */                                                   
/* 4833 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32);
/* 4834 */                                                   out.write("\n       ");
/*      */                                                   
/* 4836 */                                                   OptionTag _jspx_th_html_005foption_005f33 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4837 */                                                   _jspx_th_html_005foption_005f33.setPageContext(_jspx_page_context);
/* 4838 */                                                   _jspx_th_html_005foption_005f33.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4840 */                                                   _jspx_th_html_005foption_005f33.setValue("WEBLOGIC:7001");
/* 4841 */                                                   int _jspx_eval_html_005foption_005f33 = _jspx_th_html_005foption_005f33.doStartTag();
/* 4842 */                                                   if (_jspx_eval_html_005foption_005f33 != 0) {
/* 4843 */                                                     if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4844 */                                                       out = _jspx_page_context.pushBody();
/* 4845 */                                                       _jspx_th_html_005foption_005f33.setBodyContent((BodyContent)out);
/* 4846 */                                                       _jspx_th_html_005foption_005f33.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4849 */                                                       out.write(32);
/* 4850 */                                                       out.print(FormatUtil.getString("WebLogic Server"));
/* 4851 */                                                       out.write(32);
/* 4852 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f33.doAfterBody();
/* 4853 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4856 */                                                     if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4857 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4860 */                                                   if (_jspx_th_html_005foption_005f33.doEndTag() == 5) {
/* 4861 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33); return;
/*      */                                                   }
/*      */                                                   
/* 4864 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33);
/* 4865 */                                                   out.write("\n      ");
/*      */                                                   
/* 4867 */                                                   OptionTag _jspx_th_html_005foption_005f34 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4868 */                                                   _jspx_th_html_005foption_005f34.setPageContext(_jspx_page_context);
/* 4869 */                                                   _jspx_th_html_005foption_005f34.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4871 */                                                   _jspx_th_html_005foption_005f34.setValue("WEBSPHERE:9080");
/* 4872 */                                                   int _jspx_eval_html_005foption_005f34 = _jspx_th_html_005foption_005f34.doStartTag();
/* 4873 */                                                   if (_jspx_eval_html_005foption_005f34 != 0) {
/* 4874 */                                                     if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4875 */                                                       out = _jspx_page_context.pushBody();
/* 4876 */                                                       _jspx_th_html_005foption_005f34.setBodyContent((BodyContent)out);
/* 4877 */                                                       _jspx_th_html_005foption_005f34.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4880 */                                                       out.write(32);
/* 4881 */                                                       out.print(FormatUtil.getString("WebSphere Server"));
/* 4882 */                                                       out.write(32);
/* 4883 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f34.doAfterBody();
/* 4884 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4887 */                                                     if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4888 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4891 */                                                   if (_jspx_th_html_005foption_005f34.doEndTag() == 5) {
/* 4892 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34); return;
/*      */                                                   }
/*      */                                                   
/* 4895 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34);
/* 4896 */                                                   out.write("\n      ");
/*      */                                                   
/* 4898 */                                                   OptionTag _jspx_th_html_005foption_005f35 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4899 */                                                   _jspx_th_html_005foption_005f35.setPageContext(_jspx_page_context);
/* 4900 */                                                   _jspx_th_html_005foption_005f35.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4902 */                                                   _jspx_th_html_005foption_005f35.setValue("WTA:55555");
/* 4903 */                                                   int _jspx_eval_html_005foption_005f35 = _jspx_th_html_005foption_005f35.doStartTag();
/* 4904 */                                                   if (_jspx_eval_html_005foption_005f35 != 0) {
/* 4905 */                                                     if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4906 */                                                       out = _jspx_page_context.pushBody();
/* 4907 */                                                       _jspx_th_html_005foption_005f35.setBodyContent((BodyContent)out);
/* 4908 */                                                       _jspx_th_html_005foption_005f35.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4911 */                                                       out.print(FormatUtil.getString("Web Transactions"));
/* 4912 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f35.doAfterBody();
/* 4913 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4916 */                                                     if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4917 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4920 */                                                   if (_jspx_th_html_005foption_005f35.doEndTag() == 5) {
/* 4921 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35); return;
/*      */                                                   }
/*      */                                                   
/* 4924 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35);
/* 4925 */                                                   out.write("\n      ");
/*      */                                                   
/* 4927 */                                                   OptionTag _jspx_th_html_005foption_005f36 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4928 */                                                   _jspx_th_html_005foption_005f36.setPageContext(_jspx_page_context);
/* 4929 */                                                   _jspx_th_html_005foption_005f36.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4931 */                                                   _jspx_th_html_005foption_005f36.setValue("MAIL:25");
/* 4932 */                                                   int _jspx_eval_html_005foption_005f36 = _jspx_th_html_005foption_005f36.doStartTag();
/* 4933 */                                                   if (_jspx_eval_html_005foption_005f36 != 0) {
/* 4934 */                                                     if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4935 */                                                       out = _jspx_page_context.pushBody();
/* 4936 */                                                       _jspx_th_html_005foption_005f36.setBodyContent((BodyContent)out);
/* 4937 */                                                       _jspx_th_html_005foption_005f36.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4940 */                                                       out.print(FormatUtil.getString("Mail Server"));
/* 4941 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f36.doAfterBody();
/* 4942 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4945 */                                                     if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4946 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4949 */                                                   if (_jspx_th_html_005foption_005f36.doEndTag() == 5) {
/* 4950 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36); return;
/*      */                                                   }
/*      */                                                   
/* 4953 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36);
/* 4954 */                                                   out.write("\n      ");
/*      */                                                   
/* 4956 */                                                   OptionTag _jspx_th_html_005foption_005f37 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4957 */                                                   _jspx_th_html_005foption_005f37.setPageContext(_jspx_page_context);
/* 4958 */                                                   _jspx_th_html_005foption_005f37.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4960 */                                                   _jspx_th_html_005foption_005f37.setValue("JMX1.2-MX4J-RMI:1099");
/* 4961 */                                                   int _jspx_eval_html_005foption_005f37 = _jspx_th_html_005foption_005f37.doStartTag();
/* 4962 */                                                   if (_jspx_eval_html_005foption_005f37 != 0) {
/* 4963 */                                                     if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4964 */                                                       out = _jspx_page_context.pushBody();
/* 4965 */                                                       _jspx_th_html_005foption_005f37.setBodyContent((BodyContent)out);
/* 4966 */                                                       _jspx_th_html_005foption_005f37.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4969 */                                                       out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 4970 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f37.doAfterBody();
/* 4971 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4974 */                                                     if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4975 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4978 */                                                   if (_jspx_th_html_005foption_005f37.doEndTag() == 5) {
/* 4979 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37); return;
/*      */                                                   }
/*      */                                                   
/* 4982 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37);
/* 4983 */                                                   out.write("\n      ");
/*      */                                                   
/* 4985 */                                                   OptionTag _jspx_th_html_005foption_005f38 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4986 */                                                   _jspx_th_html_005foption_005f38.setPageContext(_jspx_page_context);
/* 4987 */                                                   _jspx_th_html_005foption_005f38.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 4989 */                                                   _jspx_th_html_005foption_005f38.setValue("SERVICE:9090");
/* 4990 */                                                   int _jspx_eval_html_005foption_005f38 = _jspx_th_html_005foption_005f38.doStartTag();
/* 4991 */                                                   if (_jspx_eval_html_005foption_005f38 != 0) {
/* 4992 */                                                     if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4993 */                                                       out = _jspx_page_context.pushBody();
/* 4994 */                                                       _jspx_th_html_005foption_005f38.setBodyContent((BodyContent)out);
/* 4995 */                                                       _jspx_th_html_005foption_005f38.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4998 */                                                       out.write(32);
/* 4999 */                                                       out.print(FormatUtil.getString("Service Monitoring"));
/* 5000 */                                                       out.write(32);
/* 5001 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f38.doAfterBody();
/* 5002 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5005 */                                                     if (_jspx_eval_html_005foption_005f38 != 1) {
/* 5006 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5009 */                                                   if (_jspx_th_html_005foption_005f38.doEndTag() == 5) {
/* 5010 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38); return;
/*      */                                                   }
/*      */                                                   
/* 5013 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38);
/* 5014 */                                                   out.write("\n      ");
/*      */                                                   
/* 5016 */                                                   OptionTag _jspx_th_html_005foption_005f39 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5017 */                                                   _jspx_th_html_005foption_005f39.setPageContext(_jspx_page_context);
/* 5018 */                                                   _jspx_th_html_005foption_005f39.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5020 */                                                   _jspx_th_html_005foption_005f39.setValue("RMI:1099");
/* 5021 */                                                   int _jspx_eval_html_005foption_005f39 = _jspx_th_html_005foption_005f39.doStartTag();
/* 5022 */                                                   if (_jspx_eval_html_005foption_005f39 != 0) {
/* 5023 */                                                     if (_jspx_eval_html_005foption_005f39 != 1) {
/* 5024 */                                                       out = _jspx_page_context.pushBody();
/* 5025 */                                                       _jspx_th_html_005foption_005f39.setBodyContent((BodyContent)out);
/* 5026 */                                                       _jspx_th_html_005foption_005f39.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5029 */                                                       out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 5030 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f39.doAfterBody();
/* 5031 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5034 */                                                     if (_jspx_eval_html_005foption_005f39 != 1) {
/* 5035 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5038 */                                                   if (_jspx_th_html_005foption_005f39.doEndTag() == 5) {
/* 5039 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39); return;
/*      */                                                   }
/*      */                                                   
/* 5042 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39);
/* 5043 */                                                   out.write("\n      ");
/*      */                                                   
/* 5045 */                                                   OptionTag _jspx_th_html_005foption_005f40 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5046 */                                                   _jspx_th_html_005foption_005f40.setPageContext(_jspx_page_context);
/* 5047 */                                                   _jspx_th_html_005foption_005f40.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5049 */                                                   _jspx_th_html_005foption_005f40.setValue("SNMP:161");
/* 5050 */                                                   int _jspx_eval_html_005foption_005f40 = _jspx_th_html_005foption_005f40.doStartTag();
/* 5051 */                                                   if (_jspx_eval_html_005foption_005f40 != 0) {
/* 5052 */                                                     if (_jspx_eval_html_005foption_005f40 != 1) {
/* 5053 */                                                       out = _jspx_page_context.pushBody();
/* 5054 */                                                       _jspx_th_html_005foption_005f40.setBodyContent((BodyContent)out);
/* 5055 */                                                       _jspx_th_html_005foption_005f40.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5058 */                                                       out.print(FormatUtil.getString("SNMP"));
/* 5059 */                                                       out.write(" (V1 or V2c)");
/* 5060 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f40.doAfterBody();
/* 5061 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5064 */                                                     if (_jspx_eval_html_005foption_005f40 != 1) {
/* 5065 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5068 */                                                   if (_jspx_th_html_005foption_005f40.doEndTag() == 5) {
/* 5069 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40); return;
/*      */                                                   }
/*      */                                                   
/* 5072 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40);
/* 5073 */                                                   out.write("\n      ");
/*      */                                                   
/* 5075 */                                                   OptionTag _jspx_th_html_005foption_005f41 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5076 */                                                   _jspx_th_html_005foption_005f41.setPageContext(_jspx_page_context);
/* 5077 */                                                   _jspx_th_html_005foption_005f41.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5079 */                                                   _jspx_th_html_005foption_005f41.setValue("Custom-Application");
/* 5080 */                                                   int _jspx_eval_html_005foption_005f41 = _jspx_th_html_005foption_005f41.doStartTag();
/* 5081 */                                                   if (_jspx_eval_html_005foption_005f41 != 0) {
/* 5082 */                                                     if (_jspx_eval_html_005foption_005f41 != 1) {
/* 5083 */                                                       out = _jspx_page_context.pushBody();
/* 5084 */                                                       _jspx_th_html_005foption_005f41.setBodyContent((BodyContent)out);
/* 5085 */                                                       _jspx_th_html_005foption_005f41.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5088 */                                                       out.write(32);
/* 5089 */                                                       out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 5090 */                                                       out.write(32);
/* 5091 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f41.doAfterBody();
/* 5092 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5095 */                                                     if (_jspx_eval_html_005foption_005f41 != 1) {
/* 5096 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5099 */                                                   if (_jspx_th_html_005foption_005f41.doEndTag() == 5) {
/* 5100 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41); return;
/*      */                                                   }
/*      */                                                   
/* 5103 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41);
/* 5104 */                                                   out.write("\n      ");
/*      */                                                   
/* 5106 */                                                   OptionTag _jspx_th_html_005foption_005f42 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5107 */                                                   _jspx_th_html_005foption_005f42.setPageContext(_jspx_page_context);
/* 5108 */                                                   _jspx_th_html_005foption_005f42.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5110 */                                                   _jspx_th_html_005foption_005f42.setValue("Script Monitor");
/* 5111 */                                                   int _jspx_eval_html_005foption_005f42 = _jspx_th_html_005foption_005f42.doStartTag();
/* 5112 */                                                   if (_jspx_eval_html_005foption_005f42 != 0) {
/* 5113 */                                                     if (_jspx_eval_html_005foption_005f42 != 1) {
/* 5114 */                                                       out = _jspx_page_context.pushBody();
/* 5115 */                                                       _jspx_th_html_005foption_005f42.setBodyContent((BodyContent)out);
/* 5116 */                                                       _jspx_th_html_005foption_005f42.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5119 */                                                       out.print(FormatUtil.getString("Script Monitor"));
/* 5120 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f42.doAfterBody();
/* 5121 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5124 */                                                     if (_jspx_eval_html_005foption_005f42 != 1) {
/* 5125 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5128 */                                                   if (_jspx_th_html_005foption_005f42.doEndTag() == 5) {
/* 5129 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42); return;
/*      */                                                   }
/*      */                                                   
/* 5132 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42);
/* 5133 */                                                   out.write("\n       ");
/*      */ 
/*      */                                                 }
/* 5136 */                                                 else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("WINDOWS"))
/*      */                                                 {
/*      */ 
/* 5139 */                                                   out.write("\n        ");
/*      */                                                   
/* 5141 */                                                   OptionTag _jspx_th_html_005foption_005f43 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5142 */                                                   _jspx_th_html_005foption_005f43.setPageContext(_jspx_page_context);
/* 5143 */                                                   _jspx_th_html_005foption_005f43.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5145 */                                                   _jspx_th_html_005foption_005f43.setValue("SYSTEM:9999");
/* 5146 */                                                   int _jspx_eval_html_005foption_005f43 = _jspx_th_html_005foption_005f43.doStartTag();
/* 5147 */                                                   if (_jspx_eval_html_005foption_005f43 != 0) {
/* 5148 */                                                     if (_jspx_eval_html_005foption_005f43 != 1) {
/* 5149 */                                                       out = _jspx_page_context.pushBody();
/* 5150 */                                                       _jspx_th_html_005foption_005f43.setBodyContent((BodyContent)out);
/* 5151 */                                                       _jspx_th_html_005foption_005f43.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5154 */                                                       out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 5155 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f43.doAfterBody();
/* 5156 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5159 */                                                     if (_jspx_eval_html_005foption_005f43 != 1) {
/* 5160 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5163 */                                                   if (_jspx_th_html_005foption_005f43.doEndTag() == 5) {
/* 5164 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43); return;
/*      */                                                   }
/*      */                                                   
/* 5167 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43);
/* 5168 */                                                   out.write("\n       ");
/*      */                                                   
/* 5170 */                                                   OptionTag _jspx_th_html_005foption_005f44 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5171 */                                                   _jspx_th_html_005foption_005f44.setPageContext(_jspx_page_context);
/* 5172 */                                                   _jspx_th_html_005foption_005f44.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5174 */                                                   _jspx_th_html_005foption_005f44.setValue(".Net:9080");
/* 5175 */                                                   int _jspx_eval_html_005foption_005f44 = _jspx_th_html_005foption_005f44.doStartTag();
/* 5176 */                                                   if (_jspx_eval_html_005foption_005f44 != 0) {
/* 5177 */                                                     if (_jspx_eval_html_005foption_005f44 != 1) {
/* 5178 */                                                       out = _jspx_page_context.pushBody();
/* 5179 */                                                       _jspx_th_html_005foption_005f44.setBodyContent((BodyContent)out);
/* 5180 */                                                       _jspx_th_html_005foption_005f44.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5183 */                                                       out.print(FormatUtil.getString("Microsoft .NET"));
/* 5184 */                                                       out.write(32);
/* 5185 */                                                       out.write(32);
/* 5186 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f44.doAfterBody();
/* 5187 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5190 */                                                     if (_jspx_eval_html_005foption_005f44 != 1) {
/* 5191 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5194 */                                                   if (_jspx_th_html_005foption_005f44.doEndTag() == 5) {
/* 5195 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44); return;
/*      */                                                   }
/*      */                                                   
/* 5198 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44);
/* 5199 */                                                   out.write("\n      ");
/*      */                                                   
/* 5201 */                                                   OptionTag _jspx_th_html_005foption_005f45 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5202 */                                                   _jspx_th_html_005foption_005f45.setPageContext(_jspx_page_context);
/* 5203 */                                                   _jspx_th_html_005foption_005f45.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5205 */                                                   _jspx_th_html_005foption_005f45.setValue("MSSQLDB:1433");
/* 5206 */                                                   int _jspx_eval_html_005foption_005f45 = _jspx_th_html_005foption_005f45.doStartTag();
/* 5207 */                                                   if (_jspx_eval_html_005foption_005f45 != 0) {
/* 5208 */                                                     if (_jspx_eval_html_005foption_005f45 != 1) {
/* 5209 */                                                       out = _jspx_page_context.pushBody();
/* 5210 */                                                       _jspx_th_html_005foption_005f45.setBodyContent((BodyContent)out);
/* 5211 */                                                       _jspx_th_html_005foption_005f45.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5214 */                                                       out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 5215 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f45.doAfterBody();
/* 5216 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5219 */                                                     if (_jspx_eval_html_005foption_005f45 != 1) {
/* 5220 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5223 */                                                   if (_jspx_th_html_005foption_005f45.doEndTag() == 5) {
/* 5224 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45); return;
/*      */                                                   }
/*      */                                                   
/* 5227 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45);
/* 5228 */                                                   out.write("\n      ");
/*      */                                                   
/* 5230 */                                                   OptionTag _jspx_th_html_005foption_005f46 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5231 */                                                   _jspx_th_html_005foption_005f46.setPageContext(_jspx_page_context);
/* 5232 */                                                   _jspx_th_html_005foption_005f46.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5234 */                                                   _jspx_th_html_005foption_005f46.setValue("Exchange:25");
/* 5235 */                                                   int _jspx_eval_html_005foption_005f46 = _jspx_th_html_005foption_005f46.doStartTag();
/* 5236 */                                                   if (_jspx_eval_html_005foption_005f46 != 0) {
/* 5237 */                                                     if (_jspx_eval_html_005foption_005f46 != 1) {
/* 5238 */                                                       out = _jspx_page_context.pushBody();
/* 5239 */                                                       _jspx_th_html_005foption_005f46.setBodyContent((BodyContent)out);
/* 5240 */                                                       _jspx_th_html_005foption_005f46.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5243 */                                                       out.print(FormatUtil.getString("Exchange Server"));
/* 5244 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f46.doAfterBody();
/* 5245 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5248 */                                                     if (_jspx_eval_html_005foption_005f46 != 1) {
/* 5249 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5252 */                                                   if (_jspx_th_html_005foption_005f46.doEndTag() == 5) {
/* 5253 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46); return;
/*      */                                                   }
/*      */                                                   
/* 5256 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46);
/* 5257 */                                                   out.write("\n\t  ");
/*      */                                                   
/* 5259 */                                                   OptionTag _jspx_th_html_005foption_005f47 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5260 */                                                   _jspx_th_html_005foption_005f47.setPageContext(_jspx_page_context);
/* 5261 */                                                   _jspx_th_html_005foption_005f47.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5263 */                                                   _jspx_th_html_005foption_005f47.setValue("IIS:80");
/* 5264 */                                                   int _jspx_eval_html_005foption_005f47 = _jspx_th_html_005foption_005f47.doStartTag();
/* 5265 */                                                   if (_jspx_eval_html_005foption_005f47 != 0) {
/* 5266 */                                                     if (_jspx_eval_html_005foption_005f47 != 1) {
/* 5267 */                                                       out = _jspx_page_context.pushBody();
/* 5268 */                                                       _jspx_th_html_005foption_005f47.setBodyContent((BodyContent)out);
/* 5269 */                                                       _jspx_th_html_005foption_005f47.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5272 */                                                       out.write(32);
/* 5273 */                                                       out.print(FormatUtil.getString("IIS Server"));
/* 5274 */                                                       out.write(32);
/* 5275 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f47.doAfterBody();
/* 5276 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5279 */                                                     if (_jspx_eval_html_005foption_005f47 != 1) {
/* 5280 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5283 */                                                   if (_jspx_th_html_005foption_005f47.doEndTag() == 5) {
/* 5284 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47); return;
/*      */                                                   }
/*      */                                                   
/* 5287 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47);
/* 5288 */                                                   out.write("\n      ");
/*      */                                                   
/* 5290 */                                                   OptionTag _jspx_th_html_005foption_005f48 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5291 */                                                   _jspx_th_html_005foption_005f48.setPageContext(_jspx_page_context);
/* 5292 */                                                   _jspx_th_html_005foption_005f48.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5294 */                                                   _jspx_th_html_005foption_005f48.setValue("SERVICE:9090");
/* 5295 */                                                   int _jspx_eval_html_005foption_005f48 = _jspx_th_html_005foption_005f48.doStartTag();
/* 5296 */                                                   if (_jspx_eval_html_005foption_005f48 != 0) {
/* 5297 */                                                     if (_jspx_eval_html_005foption_005f48 != 1) {
/* 5298 */                                                       out = _jspx_page_context.pushBody();
/* 5299 */                                                       _jspx_th_html_005foption_005f48.setBodyContent((BodyContent)out);
/* 5300 */                                                       _jspx_th_html_005foption_005f48.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5303 */                                                       out.write(32);
/* 5304 */                                                       out.print(FormatUtil.getString("Service Monitoring"));
/* 5305 */                                                       out.write(32);
/* 5306 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f48.doAfterBody();
/* 5307 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5310 */                                                     if (_jspx_eval_html_005foption_005f48 != 1) {
/* 5311 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5314 */                                                   if (_jspx_th_html_005foption_005f48.doEndTag() == 5) {
/* 5315 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48); return;
/*      */                                                   }
/*      */                                                   
/* 5318 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48);
/* 5319 */                                                   out.write("\n\t  ");
/*      */                                                   
/* 5321 */                                                   OptionTag _jspx_th_html_005foption_005f49 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5322 */                                                   _jspx_th_html_005foption_005f49.setPageContext(_jspx_page_context);
/* 5323 */                                                   _jspx_th_html_005foption_005f49.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5325 */                                                   _jspx_th_html_005foption_005f49.setValue("SNMP:161");
/* 5326 */                                                   int _jspx_eval_html_005foption_005f49 = _jspx_th_html_005foption_005f49.doStartTag();
/* 5327 */                                                   if (_jspx_eval_html_005foption_005f49 != 0) {
/* 5328 */                                                     if (_jspx_eval_html_005foption_005f49 != 1) {
/* 5329 */                                                       out = _jspx_page_context.pushBody();
/* 5330 */                                                       _jspx_th_html_005foption_005f49.setBodyContent((BodyContent)out);
/* 5331 */                                                       _jspx_th_html_005foption_005f49.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5334 */                                                       out.print(FormatUtil.getString("SNMP"));
/* 5335 */                                                       out.write(" (V1 or V2c)");
/* 5336 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f49.doAfterBody();
/* 5337 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5340 */                                                     if (_jspx_eval_html_005foption_005f49 != 1) {
/* 5341 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5344 */                                                   if (_jspx_th_html_005foption_005f49.doEndTag() == 5) {
/* 5345 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49); return;
/*      */                                                   }
/*      */                                                   
/* 5348 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49);
/* 5349 */                                                   out.write("\n      ");
/*      */                                                   
/* 5351 */                                                   OptionTag _jspx_th_html_005foption_005f50 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5352 */                                                   _jspx_th_html_005foption_005f50.setPageContext(_jspx_page_context);
/* 5353 */                                                   _jspx_th_html_005foption_005f50.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5355 */                                                   _jspx_th_html_005foption_005f50.setValue("Script Monitor");
/* 5356 */                                                   int _jspx_eval_html_005foption_005f50 = _jspx_th_html_005foption_005f50.doStartTag();
/* 5357 */                                                   if (_jspx_eval_html_005foption_005f50 != 0) {
/* 5358 */                                                     if (_jspx_eval_html_005foption_005f50 != 1) {
/* 5359 */                                                       out = _jspx_page_context.pushBody();
/* 5360 */                                                       _jspx_th_html_005foption_005f50.setBodyContent((BodyContent)out);
/* 5361 */                                                       _jspx_th_html_005foption_005f50.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5364 */                                                       out.print(FormatUtil.getString("Script Monitor"));
/* 5365 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f50.doAfterBody();
/* 5366 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5369 */                                                     if (_jspx_eval_html_005foption_005f50 != 1) {
/* 5370 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5373 */                                                   if (_jspx_th_html_005foption_005f50.doEndTag() == 5) {
/* 5374 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50); return;
/*      */                                                   }
/*      */                                                   
/* 5377 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50);
/* 5378 */                                                   out.write(10);
/*      */ 
/*      */                                                 }
/* 5381 */                                                 else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("DATABASE"))
/*      */                                                 {
/*      */ 
/* 5384 */                                                   out.write("\n\t\t</optgroup>\t<optgroup label=\"");
/* 5385 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 5386 */                                                   out.write("\">\n\t\t\t");
/*      */                                                   
/* 5388 */                                                   OptionTag _jspx_th_html_005foption_005f51 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5389 */                                                   _jspx_th_html_005foption_005f51.setPageContext(_jspx_page_context);
/* 5390 */                                                   _jspx_th_html_005foption_005f51.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5392 */                                                   _jspx_th_html_005foption_005f51.setValue("SYSTEM:9999");
/* 5393 */                                                   int _jspx_eval_html_005foption_005f51 = _jspx_th_html_005foption_005f51.doStartTag();
/* 5394 */                                                   if (_jspx_eval_html_005foption_005f51 != 0) {
/* 5395 */                                                     if (_jspx_eval_html_005foption_005f51 != 1) {
/* 5396 */                                                       out = _jspx_page_context.pushBody();
/* 5397 */                                                       _jspx_th_html_005foption_005f51.setBodyContent((BodyContent)out);
/* 5398 */                                                       _jspx_th_html_005foption_005f51.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5401 */                                                       out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 5402 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f51.doAfterBody();
/* 5403 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5406 */                                                     if (_jspx_eval_html_005foption_005f51 != 1) {
/* 5407 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5410 */                                                   if (_jspx_th_html_005foption_005f51.doEndTag() == 5) {
/* 5411 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51); return;
/*      */                                                   }
/*      */                                                   
/* 5414 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51);
/* 5415 */                                                   out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 5416 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 5417 */                                                   out.write("\">\n\t\t\t");
/*      */                                                   
/* 5419 */                                                   OptionTag _jspx_th_html_005foption_005f52 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5420 */                                                   _jspx_th_html_005foption_005f52.setPageContext(_jspx_page_context);
/* 5421 */                                                   _jspx_th_html_005foption_005f52.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5423 */                                                   _jspx_th_html_005foption_005f52.setValue("DB2:50000");
/* 5424 */                                                   int _jspx_eval_html_005foption_005f52 = _jspx_th_html_005foption_005f52.doStartTag();
/* 5425 */                                                   if (_jspx_eval_html_005foption_005f52 != 0) {
/* 5426 */                                                     if (_jspx_eval_html_005foption_005f52 != 1) {
/* 5427 */                                                       out = _jspx_page_context.pushBody();
/* 5428 */                                                       _jspx_th_html_005foption_005f52.setBodyContent((BodyContent)out);
/* 5429 */                                                       _jspx_th_html_005foption_005f52.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5432 */                                                       out.print(FormatUtil.getString("am.webclient.db2.servertype"));
/* 5433 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f52.doAfterBody();
/* 5434 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5437 */                                                     if (_jspx_eval_html_005foption_005f52 != 1) {
/* 5438 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5441 */                                                   if (_jspx_th_html_005foption_005f52.doEndTag() == 5) {
/* 5442 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52); return;
/*      */                                                   }
/*      */                                                   
/* 5445 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52);
/* 5446 */                                                   out.write("\n\t\t\t");
/*      */                                                   
/* 5448 */                                                   OptionTag _jspx_th_html_005foption_005f53 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5449 */                                                   _jspx_th_html_005foption_005f53.setPageContext(_jspx_page_context);
/* 5450 */                                                   _jspx_th_html_005foption_005f53.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5452 */                                                   _jspx_th_html_005foption_005f53.setValue("MSSQLDB:1433");
/* 5453 */                                                   int _jspx_eval_html_005foption_005f53 = _jspx_th_html_005foption_005f53.doStartTag();
/* 5454 */                                                   if (_jspx_eval_html_005foption_005f53 != 0) {
/* 5455 */                                                     if (_jspx_eval_html_005foption_005f53 != 1) {
/* 5456 */                                                       out = _jspx_page_context.pushBody();
/* 5457 */                                                       _jspx_th_html_005foption_005f53.setBodyContent((BodyContent)out);
/* 5458 */                                                       _jspx_th_html_005foption_005f53.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5461 */                                                       out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 5462 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f53.doAfterBody();
/* 5463 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5466 */                                                     if (_jspx_eval_html_005foption_005f53 != 1) {
/* 5467 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5470 */                                                   if (_jspx_th_html_005foption_005f53.doEndTag() == 5) {
/* 5471 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53); return;
/*      */                                                   }
/*      */                                                   
/* 5474 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53);
/* 5475 */                                                   out.write("\n\t\t\t");
/*      */                                                   
/* 5477 */                                                   OptionTag _jspx_th_html_005foption_005f54 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5478 */                                                   _jspx_th_html_005foption_005f54.setPageContext(_jspx_page_context);
/* 5479 */                                                   _jspx_th_html_005foption_005f54.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5481 */                                                   _jspx_th_html_005foption_005f54.setValue("MYSQLDB:3306");
/* 5482 */                                                   int _jspx_eval_html_005foption_005f54 = _jspx_th_html_005foption_005f54.doStartTag();
/* 5483 */                                                   if (_jspx_eval_html_005foption_005f54 != 0) {
/* 5484 */                                                     if (_jspx_eval_html_005foption_005f54 != 1) {
/* 5485 */                                                       out = _jspx_page_context.pushBody();
/* 5486 */                                                       _jspx_th_html_005foption_005f54.setBodyContent((BodyContent)out);
/* 5487 */                                                       _jspx_th_html_005foption_005f54.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5490 */                                                       out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 5491 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f54.doAfterBody();
/* 5492 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5495 */                                                     if (_jspx_eval_html_005foption_005f54 != 1) {
/* 5496 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5499 */                                                   if (_jspx_th_html_005foption_005f54.doEndTag() == 5) {
/* 5500 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54); return;
/*      */                                                   }
/*      */                                                   
/* 5503 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54);
/* 5504 */                                                   out.write("\n\t\t\t");
/*      */                                                   
/* 5506 */                                                   OptionTag _jspx_th_html_005foption_005f55 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5507 */                                                   _jspx_th_html_005foption_005f55.setPageContext(_jspx_page_context);
/* 5508 */                                                   _jspx_th_html_005foption_005f55.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5510 */                                                   _jspx_th_html_005foption_005f55.setValue("ORACLEDB:1521");
/* 5511 */                                                   int _jspx_eval_html_005foption_005f55 = _jspx_th_html_005foption_005f55.doStartTag();
/* 5512 */                                                   if (_jspx_eval_html_005foption_005f55 != 0) {
/* 5513 */                                                     if (_jspx_eval_html_005foption_005f55 != 1) {
/* 5514 */                                                       out = _jspx_page_context.pushBody();
/* 5515 */                                                       _jspx_th_html_005foption_005f55.setBodyContent((BodyContent)out);
/* 5516 */                                                       _jspx_th_html_005foption_005f55.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5519 */                                                       out.print(FormatUtil.getString("am.webclient.oracle.servertype"));
/* 5520 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f55.doAfterBody();
/* 5521 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5524 */                                                     if (_jspx_eval_html_005foption_005f55 != 1) {
/* 5525 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5528 */                                                   if (_jspx_th_html_005foption_005f55.doEndTag() == 5) {
/* 5529 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55); return;
/*      */                                                   }
/*      */                                                   
/* 5532 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55);
/* 5533 */                                                   out.write("\n\t\t\t");
/*      */                                                   
/* 5535 */                                                   OptionTag _jspx_th_html_005foption_005f56 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5536 */                                                   _jspx_th_html_005foption_005f56.setPageContext(_jspx_page_context);
/* 5537 */                                                   _jspx_th_html_005foption_005f56.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5539 */                                                   _jspx_th_html_005foption_005f56.setValue("SYBASEDB:5000");
/* 5540 */                                                   int _jspx_eval_html_005foption_005f56 = _jspx_th_html_005foption_005f56.doStartTag();
/* 5541 */                                                   if (_jspx_eval_html_005foption_005f56 != 0) {
/* 5542 */                                                     if (_jspx_eval_html_005foption_005f56 != 1) {
/* 5543 */                                                       out = _jspx_page_context.pushBody();
/* 5544 */                                                       _jspx_th_html_005foption_005f56.setBodyContent((BodyContent)out);
/* 5545 */                                                       _jspx_th_html_005foption_005f56.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5548 */                                                       out.print(FormatUtil.getString("Sybase"));
/* 5549 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f56.doAfterBody();
/* 5550 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5553 */                                                     if (_jspx_eval_html_005foption_005f56 != 1) {
/* 5554 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5557 */                                                   if (_jspx_th_html_005foption_005f56.doEndTag() == 5) {
/* 5558 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56); return;
/*      */                                                   }
/*      */                                                   
/* 5561 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56);
/* 5562 */                                                   out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 5563 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 5564 */                                                   out.write("\">\n\t\t\t");
/*      */                                                   
/* 5566 */                                                   OptionTag _jspx_th_html_005foption_005f57 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5567 */                                                   _jspx_th_html_005foption_005f57.setPageContext(_jspx_page_context);
/* 5568 */                                                   _jspx_th_html_005foption_005f57.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5570 */                                                   _jspx_th_html_005foption_005f57.setValue("SERVICE:9090");
/* 5571 */                                                   int _jspx_eval_html_005foption_005f57 = _jspx_th_html_005foption_005f57.doStartTag();
/* 5572 */                                                   if (_jspx_eval_html_005foption_005f57 != 0) {
/* 5573 */                                                     if (_jspx_eval_html_005foption_005f57 != 1) {
/* 5574 */                                                       out = _jspx_page_context.pushBody();
/* 5575 */                                                       _jspx_th_html_005foption_005f57.setBodyContent((BodyContent)out);
/* 5576 */                                                       _jspx_th_html_005foption_005f57.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5579 */                                                       out.write(32);
/* 5580 */                                                       out.print(FormatUtil.getString("Service Monitoring"));
/* 5581 */                                                       out.write(32);
/* 5582 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f57.doAfterBody();
/* 5583 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5586 */                                                     if (_jspx_eval_html_005foption_005f57 != 1) {
/* 5587 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5590 */                                                   if (_jspx_th_html_005foption_005f57.doEndTag() == 5) {
/* 5591 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57); return;
/*      */                                                   }
/*      */                                                   
/* 5594 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57);
/* 5595 */                                                   out.write("\n\t\t\t");
/*      */                                                   
/* 5597 */                                                   OptionTag _jspx_th_html_005foption_005f58 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5598 */                                                   _jspx_th_html_005foption_005f58.setPageContext(_jspx_page_context);
/* 5599 */                                                   _jspx_th_html_005foption_005f58.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5601 */                                                   _jspx_th_html_005foption_005f58.setValue("SNMP:161");
/* 5602 */                                                   int _jspx_eval_html_005foption_005f58 = _jspx_th_html_005foption_005f58.doStartTag();
/* 5603 */                                                   if (_jspx_eval_html_005foption_005f58 != 0) {
/* 5604 */                                                     if (_jspx_eval_html_005foption_005f58 != 1) {
/* 5605 */                                                       out = _jspx_page_context.pushBody();
/* 5606 */                                                       _jspx_th_html_005foption_005f58.setBodyContent((BodyContent)out);
/* 5607 */                                                       _jspx_th_html_005foption_005f58.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5610 */                                                       out.print(FormatUtil.getString("SNMP"));
/* 5611 */                                                       out.write(" (V1 or V2c)");
/* 5612 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f58.doAfterBody();
/* 5613 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5616 */                                                     if (_jspx_eval_html_005foption_005f58 != 1) {
/* 5617 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5620 */                                                   if (_jspx_th_html_005foption_005f58.doEndTag() == 5) {
/* 5621 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58); return;
/*      */                                                   }
/*      */                                                   
/* 5624 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58);
/* 5625 */                                                   out.write("</optgroup>");
/* 5626 */                                                   out.write("\n\t\t\t<optgroup label=\"");
/* 5627 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 5628 */                                                   out.write("\">\n\t\t\t");
/*      */                                                   
/* 5630 */                                                   OptionTag _jspx_th_html_005foption_005f59 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5631 */                                                   _jspx_th_html_005foption_005f59.setPageContext(_jspx_page_context);
/* 5632 */                                                   _jspx_th_html_005foption_005f59.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5634 */                                                   _jspx_th_html_005foption_005f59.setValue("UrlMonitor");
/* 5635 */                                                   int _jspx_eval_html_005foption_005f59 = _jspx_th_html_005foption_005f59.doStartTag();
/* 5636 */                                                   if (_jspx_eval_html_005foption_005f59 != 0) {
/* 5637 */                                                     if (_jspx_eval_html_005foption_005f59 != 1) {
/* 5638 */                                                       out = _jspx_page_context.pushBody();
/* 5639 */                                                       _jspx_th_html_005foption_005f59.setBodyContent((BodyContent)out);
/* 5640 */                                                       _jspx_th_html_005foption_005f59.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5643 */                                                       out.print(FormatUtil.getString("HTTP-URLs"));
/* 5644 */                                                       out.write(32);
/* 5645 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f59.doAfterBody();
/* 5646 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5649 */                                                     if (_jspx_eval_html_005foption_005f59 != 1) {
/* 5650 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5653 */                                                   if (_jspx_th_html_005foption_005f59.doEndTag() == 5) {
/* 5654 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59); return;
/*      */                                                   }
/*      */                                                   
/* 5657 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59);
/* 5658 */                                                   out.write("\n\t\t\t");
/*      */                                                   
/* 5660 */                                                   OptionTag _jspx_th_html_005foption_005f60 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5661 */                                                   _jspx_th_html_005foption_005f60.setPageContext(_jspx_page_context);
/* 5662 */                                                   _jspx_th_html_005foption_005f60.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5664 */                                                   _jspx_th_html_005foption_005f60.setValue("UrlSeq");
/* 5665 */                                                   int _jspx_eval_html_005foption_005f60 = _jspx_th_html_005foption_005f60.doStartTag();
/* 5666 */                                                   if (_jspx_eval_html_005foption_005f60 != 0) {
/* 5667 */                                                     if (_jspx_eval_html_005foption_005f60 != 1) {
/* 5668 */                                                       out = _jspx_page_context.pushBody();
/* 5669 */                                                       _jspx_th_html_005foption_005f60.setBodyContent((BodyContent)out);
/* 5670 */                                                       _jspx_th_html_005foption_005f60.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5673 */                                                       out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 5674 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f60.doAfterBody();
/* 5675 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5678 */                                                     if (_jspx_eval_html_005foption_005f60 != 1) {
/* 5679 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5682 */                                                   if (_jspx_th_html_005foption_005f60.doEndTag() == 5) {
/* 5683 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60); return;
/*      */                                                   }
/*      */                                                   
/* 5686 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60);
/* 5687 */                                                   out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 5688 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 5689 */                                                   out.write("\">\n\t\t\t");
/*      */                                                   
/* 5691 */                                                   OptionTag _jspx_th_html_005foption_005f61 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5692 */                                                   _jspx_th_html_005foption_005f61.setPageContext(_jspx_page_context);
/* 5693 */                                                   _jspx_th_html_005foption_005f61.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 5695 */                                                   _jspx_th_html_005foption_005f61.setValue("Script Monitor");
/* 5696 */                                                   int _jspx_eval_html_005foption_005f61 = _jspx_th_html_005foption_005f61.doStartTag();
/* 5697 */                                                   if (_jspx_eval_html_005foption_005f61 != 0) {
/* 5698 */                                                     if (_jspx_eval_html_005foption_005f61 != 1) {
/* 5699 */                                                       out = _jspx_page_context.pushBody();
/* 5700 */                                                       _jspx_th_html_005foption_005f61.setBodyContent((BodyContent)out);
/* 5701 */                                                       _jspx_th_html_005foption_005f61.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5704 */                                                       out.print(FormatUtil.getString("Script Monitor"));
/* 5705 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f61.doAfterBody();
/* 5706 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5709 */                                                     if (_jspx_eval_html_005foption_005f61 != 1) {
/* 5710 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5713 */                                                   if (_jspx_th_html_005foption_005f61.doEndTag() == 5) {
/* 5714 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61); return;
/*      */                                                   }
/*      */                                                   
/* 5717 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61);
/* 5718 */                                                   out.write(10);
/* 5719 */                                                   out.write(10);
/*      */                                                 }
/*      */                                                 
/*      */ 
/* 5723 */                                                 out.write("\n\n\n\n      ");
/* 5724 */                                                 int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 5725 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5728 */                                               if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 5729 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5732 */                                             if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 5733 */                                               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                             }
/*      */                                             
/* 5736 */                                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 5737 */                                             out.write("\n                      \n      ");
/* 5738 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 5739 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 5743 */                                         if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 5744 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                         }
/*      */                                         
/* 5747 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5748 */                                         out.write("\n      ");
/* 5749 */                                         if (_jspx_meth_c_005fif_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                           return;
/* 5751 */                                         out.write("\n      </td>\n      \n      ");
/* 5752 */                                         if (request.getParameter("type") != null) {
/* 5753 */                                           isConfMonitor = confMonConfig.isConfMonitor(request.getParameter("type"));
/* 5754 */                                           String restype = request.getParameter("type");
/* 5755 */                                           if (restype.indexOf(":") != -1) {
/* 5756 */                                             restype = restype.substring(0, restype.indexOf(":"));
/*      */                                           }
/* 5758 */                                           if (((isConfMonitor) && (!restype.equals("QueryMonitor"))) || ((!restype.equals("JMX1.2-MX4J-RMI")) && (!restype.equals("Generic WMI")) && (!restype.equals("Script Monitor")) && (!restype.equals("Custom-Application")) && (!restype.equals("File System Monitor")) && (!restype.equals("QueryMonitor")) && (!restype.equals("SNMP")) && (!restype.equals("TELNET")) && (!restype.equals("Exchange")) && (!restype.equals("WTA")) && (!restype.equals("WEB")) && (!restype.equals("UrlSeq")) && (!restype.equals("PHP")) && (!restype.equals("IIS")) && (!restype.equals("APACHE")) && (!restype.equals("MAIL")) && (!restype.equals("All")) && (restype.indexOf("SAP") == -1))) {
/* 5759 */                                             out.write("\n      \t<td class=\"tableheading-monitor-config itadmin-hide\" align=\"right\">\n      \n      \t\t<div id=\"Conf-bulk-configuration\"> \n\t\t\t    \t\t<a href=\"javascript:void(0)\"  onclick=\"window.open('/BulkAddMonitors.do?method=showBulkImportForm&type=");
/* 5760 */                                             out.print(restype);
/* 5761 */                                             out.write("','windowName','toolbar=no,status=no,menubar=no,width=1000,height=500,scrollbars=yes')\" class=\"staticlinks\" >");
/* 5762 */                                             out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 5763 */                                             out.write("</a>\n\t    \t</div><img src=\"/images/script-icon.gif\">\n   \t   </td>\n      \n      \t");
/*      */                                           }
/*      */                                         }
/* 5766 */                                         out.write("     \n      \n  </tr>\n</table>\n <script src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script><script type=\"text/javascript\"> $(\".formtext\").chosen();  </script>\n");
/* 5767 */                                         out.write("\n            ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 5771 */                                       out.write("\n            <table id=\"DiscoverDetails\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" class=\"lrborder\" >\n                <tr height=\"10px\"><td colspan=\"2\"></td></tr>        \n                <tr>\n                    <td height=\"20\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>&nbsp;&nbsp;");
/* 5772 */                                       out.print(FormatUtil.getString("am.webclient.newaction.displayname"));
/* 5773 */                                       out.write("<span class=\"mandatory\">*</span></label></td>\n                    <td  height=\"20\" ><input type=\"text\" class=\"formtext default\" name =\"displayname\" ");
/* 5774 */                                       out.print(displayname_append);
/* 5775 */                                       out.write("/></td></tr>\n                    \n                    \n            ");
/*      */                                       
/* 5777 */                                       IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5778 */                                       _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 5779 */                                       _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 5781 */                                       _jspx_th_logic_005fiterate_005f1.setName("args_name");
/*      */                                       
/* 5783 */                                       _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                                       
/* 5785 */                                       _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                                       
/* 5787 */                                       _jspx_th_logic_005fiterate_005f1.setType("java.lang.String");
/* 5788 */                                       int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 5789 */                                       if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 5790 */                                         String row = null;
/* 5791 */                                         Integer j = null;
/* 5792 */                                         if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5793 */                                           out = _jspx_page_context.pushBody();
/* 5794 */                                           _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 5795 */                                           _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                         }
/* 5797 */                                         row = (String)_jspx_page_context.findAttribute("row");
/* 5798 */                                         j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                         for (;;) {
/* 5800 */                                           out.write("\n            ");
/*      */                                           
/* 5802 */                                           String mandatory = "";
/* 5803 */                                           String showOnCondition = "display:table-row";
/* 5804 */                                           if ((args_mandataory != null) && (((String)args_mandataory.get(j.intValue())).equals("1")))
/*      */                                           {
/* 5806 */                                             mandatory = "<span class=\"mandatory\">*</span>";
/*      */                                           }
/* 5808 */                                           String extraStuff = "";
/* 5809 */                                           if (row.equals("Timeout")) {
/* 5810 */                                             extraStuff = "(in Seconds)";
/*      */                                           }
/* 5812 */                                           String toolTip = "";
/* 5813 */                                           String className = "body";
/* 5814 */                                           String onMouseOut = "";
/* 5815 */                                           if (args_Tooltip.get(j.intValue()) != null) {
/* 5816 */                                             toolTip = (String)args_Tooltip.get(j.intValue());
/* 5817 */                                             className = "dotteduline";
/* 5818 */                                             onMouseOut = "hideddrivetip()";
/*      */                                           }
/*      */                                           
/* 5821 */                                           if ((args_ShowOnCondition.get(j.intValue()) != null) && (((String)args_ShowOnCondition.get(j.intValue())).equals("YES"))) {
/* 5822 */                                             showOnCondition = "display:none";
/*      */                                           }
/* 5824 */                                           String argsList = getArgsListtoShowonClick(showArgsMap, row);
/* 5825 */                                           String hideArgsList = getArgsListtoShowonClick(hideArgsMap, row);
/*      */                                           
/*      */ 
/*      */ 
/*      */ 
/* 5830 */                                           if (!args_type.get(j.intValue()).toString().equals("6")) {
/* 5831 */                                             if (args_disp.get(j.intValue()) != null)
/*      */                                             {
/* 5833 */                                               out.write("\n\n            <tr id=\"");
/* 5834 */                                               out.print(row);
/* 5835 */                                               out.write("Div\" style=\"");
/* 5836 */                                               out.print(showOnCondition);
/* 5837 */                                               out.write("\">\n            ");
/*      */                                               
/*      */ 
/* 5840 */                                               out.write("\n                <td height=\"20\" width=\"");
/* 5841 */                                               out.print(argDisplayWidth);
/* 5842 */                                               out.write("\" class=\"bodytext label-align addmonitor-label\"><label>&nbsp;\n                    <a class=");
/* 5843 */                                               out.print(className);
/* 5844 */                                               out.write(" onMouseOver=\"ddrivetip(this,event,'");
/* 5845 */                                               out.print(FormatUtil.getString(toolTip));
/* 5846 */                                               out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=");
/* 5847 */                                               out.print(onMouseOut);
/* 5848 */                                               out.write(">\n                        ");
/* 5849 */                                               out.print(FormatUtil.getString((String)args_disp.get(j.intValue())));
/* 5850 */                                               out.print(FormatUtil.getString(extraStuff));
/* 5851 */                                               out.print(mandatory);
/* 5852 */                                               out.write("\n                    </a></label>\n                </td>\n\n");
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 5856 */                                               out.write("\n            <tr id=\"");
/* 5857 */                                               out.print(row);
/* 5858 */                                               out.write("Div\" style=\"");
/* 5859 */                                               out.print(showOnCondition);
/* 5860 */                                               out.write("\">\n             <td height=\"20\" width=\"");
/* 5861 */                                               out.print(argDisplayWidth);
/* 5862 */                                               out.write("\" class=\"bodytext label-align addmonitor-label\"><label>&nbsp;\n                <a style=\"cursor:pointer\" class=");
/* 5863 */                                               out.print(className);
/* 5864 */                                               out.write(" onMouseOver=\"ddrivetip(this,event,'");
/* 5865 */                                               out.print(FormatUtil.getString(toolTip));
/* 5866 */                                               out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=");
/* 5867 */                                               out.print(onMouseOut);
/* 5868 */                                               out.write(">\n                    ");
/* 5869 */                                               out.print(FormatUtil.getString((String)args_name.get(j.intValue())));
/* 5870 */                                               out.print(FormatUtil.getString(extraStuff));
/* 5871 */                                               out.print(mandatory);
/* 5872 */                                               out.write("\n                </a></label>\n             </td>\n\n");
/*      */                                             }
/* 5874 */                                             String dimension = "";
/* 5875 */                                             String sideNote = "";
/* 5876 */                                             if (((String)args_dimension.get(j.intValue()) != null) && (!((String)args_dimension.get(j.intValue())).trim().equals(""))) {
/* 5877 */                                               dimension = (String)args_dimension.get(j.intValue());
/*      */                                             }
/* 5879 */                                             if (((String)args_sideNote.get(j.intValue()) != null) && (!((String)args_sideNote.get(j.intValue())).trim().equals("")))
/*      */                                             {
/* 5881 */                                               sideNote = (String)args_sideNote.get(j.intValue());
/*      */                                             }
/* 5883 */                                             if ((args_type.get(j.intValue()).toString().equals("1")) || (args_type.get(j.intValue()).toString().equals("7")))
/*      */                                             {
/* 5885 */                                               String toappend = "";
/*      */                                               
/*      */ 
/* 5888 */                                               if ((String)args_default.get(j.intValue()) != null) {
/* 5889 */                                                 toappend = "value='" + (String)args_default.get(j.intValue()) + "'";
/*      */                                               }
/*      */                                               
/*      */ 
/* 5893 */                                               if ((argsasprops.size() > 0) && (!args_type.get(j.intValue()).toString().equals("7")))
/*      */                                               {
/* 5895 */                                                 String temp = argsasprops.getProperty(row);
/* 5896 */                                                 toappend = "value='" + temp + "'";
/*      */                                               }
/*      */                                               
/* 5899 */                                               String ttype1 = "type='text'";
/* 5900 */                                               if (args_type.get(j.intValue()).toString().equals("7"))
/*      */                                               {
/* 5902 */                                                 ttype1 = "type='password'";
/*      */                                               }
/* 5904 */                                               if (args_type.get(j.intValue()).toString().equals("7"))
/*      */                                               {
/*      */ 
/* 5907 */                                                 out.write("\n               \n                    <td height=\"20\"> <span id=\"tdSpan_");
/* 5908 */                                                 out.print(row);
/* 5909 */                                                 out.write("\"> </span>\n                 ");
/* 5910 */                                                 if (!resourceid.equals(""))
/*      */                                                 {
/*      */ 
/* 5913 */                                                   out.write("\n                      <span id=\"modifySpan_");
/* 5914 */                                                   out.print(row);
/* 5915 */                                                   out.write("\"><input class=\"formtext default\" id=\"");
/* 5916 */                                                   out.print(row);
/* 5917 */                                                   out.write("\" name =\"");
/* 5918 */                                                   out.print(row);
/* 5919 */                                                   out.write("\" type=\"hidden\"/>\n                         <a href=\"javascript:void(0)\" style=\"color:blue;text-decoration:underline;\" onclick=\"modifyPassword('");
/* 5920 */                                                   out.print(row);
/* 5921 */                                                   out.write("')\">");
/* 5922 */                                                   if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                                                     return;
/* 5924 */                                                   out.write("</a>\n                         </span>\n                     \n                  \n                        \n                  ");
/*      */                                                 } else {
/* 5926 */                                                   out.write("  \n                  <input ");
/* 5927 */                                                   out.print(ttype1);
/* 5928 */                                                   out.write("  class=\"formtext default\" id=\"");
/* 5929 */                                                   out.print(row);
/* 5930 */                                                   out.write("\" name =\"");
/* 5931 */                                                   out.print(row);
/* 5932 */                                                   out.write(34);
/* 5933 */                                                   out.write(32);
/* 5934 */                                                   out.print(toappend);
/* 5935 */                                                   out.write(" autocomplete=\"off\" />");
/* 5936 */                                                   out.print(sideNote);
/* 5937 */                                                   out.write("\n                    \n                    </td>");
/*      */                                                 }
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 5942 */                                                 out.write("\n                    <td height=\"20\"><input ");
/* 5943 */                                                 out.print(ttype1);
/* 5944 */                                                 out.write("  class=\"formtext default\" id=\"");
/* 5945 */                                                 out.print(row);
/* 5946 */                                                 out.write("\" name =\"");
/* 5947 */                                                 out.print(row);
/* 5948 */                                                 out.write(34);
/* 5949 */                                                 out.write(32);
/* 5950 */                                                 out.print(toappend);
/* 5951 */                                                 out.write(" autocomplete=\"off\" />");
/* 5952 */                                                 out.print(sideNote);
/* 5953 */                                                 out.write("</td>\n                    ");
/*      */                                               }
/*      */                                             }
/* 5956 */                                             else if (args_type.get(j.intValue()).toString().equals("2"))
/*      */                                             {
/* 5958 */                                               LinkedHashMap key_val = (LinkedHashMap)hm.get(row);
/* 5959 */                                               if (dimension.trim().equals("")) {
/* 5960 */                                                 dimension = "style=\"width:25%\"";
/*      */                                               }
/* 5962 */                                               if (key_val != null) {
/* 5963 */                                                 Set st = key_val.keySet();
/* 5964 */                                                 Iterator itr = st.iterator();
/* 5965 */                                                 String toappend = "";
/* 5966 */                                                 String data_val = "";
/* 5967 */                                                 String onChangeMethodName = (String)onChangeMethod.get(row);
/*      */                                                 
/* 5969 */                                                 onChangeMethodName = (onChangeMethodName != null) && (onChangeMethodName != "") ? "javascript:" + onChangeMethodName : "";
/* 5970 */                                                 if ((argsasprops != null) && (argsasprops.size() > 0) && (argsasprops.getProperty(row) != null))
/*      */                                                 {
/* 5972 */                                                   data_val = argsasprops.getProperty(row);
/*      */                                                 }
/*      */                                                 
/*      */ 
/* 5976 */                                                 out.write("\n                          <td height=\"20\">\n                            <select  name=\"");
/* 5977 */                                                 out.print(row);
/* 5978 */                                                 out.write("\" id=\"");
/* 5979 */                                                 out.print(row);
/* 5980 */                                                 out.write("\" onchange=\"");
/* 5981 */                                                 out.print(onChangeMethodName);
/* 5982 */                                                 out.write("\" class=\"formtext medium\" id=\"select\" style=\"width:255px\">\n                              ");
/*      */                                                 
/* 5984 */                                                 while (itr.hasNext())
/*      */                                                 {
/* 5986 */                                                   toappend = "";
/* 5987 */                                                   String key = (String)itr.next();
/* 5988 */                                                   String val = (String)key_val.get(key);
/* 5989 */                                                   if (data_val.equals(val))
/*      */                                                   {
/* 5991 */                                                     toappend = "SELECTED";
/*      */                                                   }
/*      */                                                   
/* 5994 */                                                   out.write("\n\n                          <option ");
/* 5995 */                                                   out.print(toappend);
/* 5996 */                                                   out.write(" value='");
/* 5997 */                                                   out.print(val);
/* 5998 */                                                   out.write(39);
/* 5999 */                                                   out.write(62);
/* 6000 */                                                   out.print(FormatUtil.getString(key));
/* 6001 */                                                   out.write(" </option>\n\n                          ");
/*      */                                                 }
/*      */                                                 
/*      */ 
/* 6005 */                                                 out.write("\n                    </select>\n                    ");
/* 6006 */                                                 out.print(sideNote);
/* 6007 */                                                 out.write("\n                </td>\n            ");
/*      */                                               }
/*      */                                             }
/* 6010 */                                             else if (args_type.get(j.intValue()).toString().equals("3"))
/*      */                                             {
/* 6012 */                                               String data_val = "";
/* 6013 */                                               if (dimension.trim().equals("")) {
/* 6014 */                                                 dimension = "style=\"width:25%\"";
/*      */                                               }
/* 6016 */                                               if ((argsasprops != null) && (argsasprops.size() > 0) && (argsasprops.getProperty(row) != null)) {
/* 6017 */                                                 data_val = argsasprops.getProperty(row);
/*      */                                               }
/* 6019 */                                               if (((String)args_default.get(j.intValue()) != null) && (argsasprops.size() == 0)) {
/* 6020 */                                                 data_val = (String)args_default.get(j.intValue());
/*      */                                               }
/*      */                                               
/* 6023 */                                               out.write("\n                        <td height=\"20\"><textarea class=\"formtexarea formtextarea-custom-search\" id=\"");
/* 6024 */                                               out.print(row);
/* 6025 */                                               out.write("\" name =\"");
/* 6026 */                                               out.print(row);
/* 6027 */                                               out.write(34);
/* 6028 */                                               out.write(62);
/* 6029 */                                               out.print(data_val);
/* 6030 */                                               out.write("</textarea>");
/* 6031 */                                               out.print(sideNote);
/* 6032 */                                               out.write("</td>\n                        ");
/*      */ 
/*      */                                             }
/* 6035 */                                             else if (args_type.get(j.intValue()).toString().equals("4"))
/*      */                                             {
/* 6037 */                                               String colspan_val = "";
/* 6038 */                                               colspan_val = (String)args_colSpan.get(j.intValue());
/* 6039 */                                               int colSpan = 0;
/* 6040 */                                               if ((colspan_val != null) && (!colspan_val.trim().equals(""))) {
/* 6041 */                                                 colSpan = Integer.parseInt(colspan_val);
/*      */                                               }
/*      */                                               
/* 6044 */                                               LinkedHashMap key_val = (LinkedHashMap)hm.get(row);
/* 6045 */                                               if (key_val != null) {
/* 6046 */                                                 Set st = key_val.keySet();
/* 6047 */                                                 Iterator itr = st.iterator();
/* 6048 */                                                 String select_text = "";
/* 6049 */                                                 int rad_but = 0;
/* 6050 */                                                 int columnCt = 0;
/* 6051 */                                                 boolean openTable = true;
/* 6052 */                                                 boolean closeTable = false;
/*      */                                                 
/* 6054 */                                                 out.write("\n                       <td height=\"20\" align=\"left\">\n                        ");
/*      */                                                 
/* 6056 */                                                 while (itr.hasNext())
/*      */                                                 {
/* 6058 */                                                   String key = (String)itr.next();
/* 6059 */                                                   String val = (String)key_val.get(key);
/* 6060 */                                                   String toShowArgs = "";
/* 6061 */                                                   String toHideArgs = "";
/* 6062 */                                                   if (key.indexOf("_OnClick") != -1)
/*      */                                                   {
/* 6064 */                                                     columnCt--;
/*      */                                                   }
/*      */                                                   else {
/* 6067 */                                                     if (key_val.containsKey(val + "_OnClickShowArgs"))
/*      */                                                     {
/* 6069 */                                                       toShowArgs = (String)key_val.get(val + "_OnClickShowArgs");
/*      */                                                     }
/*      */                                                     
/* 6072 */                                                     if (key_val.containsKey(val + "_OnClickHideArgs"))
/*      */                                                     {
/* 6074 */                                                       toHideArgs = (String)key_val.get(val + "_OnClickHideArgs");
/*      */                                                     }
/*      */                                                     
/*      */ 
/*      */ 
/* 6079 */                                                     if (((String)args_default.get(j.intValue()) != null) && (argsasprops.size() == 0) && 
/* 6080 */                                                       (((String)args_default.get(j.intValue())).equals(val)))
/*      */                                                     {
/* 6082 */                                                       select_text = "checked=\"checked\"";
/*      */                                                     }
/*      */                                                     
/*      */ 
/*      */ 
/* 6087 */                                                     if (argsasprops.size() > 0)
/*      */                                                     {
/* 6089 */                                                       if ((argsasprops.getProperty(row) != null) && (argsasprops.getProperty(row).equals(val))) {
/* 6090 */                                                         select_text = "checked=\"true\"";
/*      */                                                       }
/*      */                                                     }
/* 6093 */                                                     columnCt++;
/*      */                                                     
/*      */ 
/* 6096 */                                                     out.write(10);
/* 6097 */                                                     if (openTable) { openTable = false;
/* 6098 */                                                       out.write("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" > <tr><td height=\"20\" align=\"left\">");
/*      */                                                     }
/* 6100 */                                                     out.write("\n                <input type=\"radio\" id=\"");
/* 6101 */                                                     out.print(row);
/* 6102 */                                                     out.write("\" name =\"");
/* 6103 */                                                     out.print(row);
/* 6104 */                                                     out.write("\" value=\"");
/* 6105 */                                                     out.print(val);
/* 6106 */                                                     out.write("\" onclick=\"javascript:showAsPerArgs('");
/* 6107 */                                                     out.print(row);
/* 6108 */                                                     out.write(39);
/* 6109 */                                                     out.write(44);
/* 6110 */                                                     out.write(39);
/* 6111 */                                                     out.print(toShowArgs);
/* 6112 */                                                     out.write(39);
/* 6113 */                                                     out.write(44);
/* 6114 */                                                     out.write(39);
/* 6115 */                                                     out.print(toHideArgs);
/* 6116 */                                                     out.write("','true','false');\"  ");
/* 6117 */                                                     out.print(select_text);
/* 6118 */                                                     out.write("><span class=\"bodytext\">");
/* 6119 */                                                     out.print(FormatUtil.getString(key));
/* 6120 */                                                     out.write("</span>\n                  ");
/* 6121 */                                                     select_text = "";
/* 6122 */                                                     if ((colSpan == columnCt) || (!itr.hasNext())) {
/* 6123 */                                                       out.write("</td></tr></table> ");
/* 6124 */                                                       closeTable = true;openTable = true;columnCt = 0; }
/* 6125 */                                                     out.write("\n                        "); } }
/* 6126 */                                                 if (!closeTable) {
/* 6127 */                                                   out.write("\n                                </td></tr></table>\n                ");
/*      */                                                   
/* 6129 */                                                   closeTable = false;
/* 6130 */                                                   columnCt = 0; }
/* 6131 */                                                 out.write("\n                    </td>   \n                ");
/*      */                                               }
/*      */                                             }
/* 6134 */                                             else if (args_type.get(j.intValue()).toString().equals("8"))
/*      */                                             {
/* 6136 */                                               LinkedHashMap key_val = (LinkedHashMap)hm.get(row);
/* 6137 */                                               if (key_val != null)
/*      */                                               {
/* 6139 */                                                 Set st = key_val.keySet();
/* 6140 */                                                 Iterator itr = st.iterator();
/* 6141 */                                                 String select_text = "";
/*      */                                                 
/* 6143 */                                                 out.write("\n                    <td height=\"20\" align=\"left\">\n                    <select class=\"multi-select\" name=\"select-");
/* 6144 */                                                 out.print(row);
/* 6145 */                                                 out.write("\" data-placeholder=\"");
/* 6146 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.multiselect.choose", new String[] { FormatUtil.getString((String)args_disp.get(j.intValue())) }));
/* 6147 */                                                 out.write("\" style=\"width:255px;\" multiple=\"\">\n                    ");
/*      */                                                 
/* 6149 */                                                 while (itr.hasNext())
/*      */                                                 {
/* 6151 */                                                   String key = (String)itr.next();
/* 6152 */                                                   String val = (String)key_val.get(key);
/* 6153 */                                                   String showValue = val;
/* 6154 */                                                   if (val.trim().equals("")) {
/* 6155 */                                                     val = key;
/*      */                                                   }
/* 6157 */                                                   if (((String)args_default.get(j.intValue()) != null) && (argsasprops.size() == 0) && 
/* 6158 */                                                     (((String)args_default.get(j.intValue())).equals(key))) {
/* 6159 */                                                     select_text = "selected";
/*      */                                                   }
/*      */                                                   
/* 6162 */                                                   if (argsasprops.size() > 0)
/*      */                                                   {
/* 6164 */                                                     if ((argsasprops.getProperty(row) != null) && (argsasprops.getProperty(row).contains(val))) {
/* 6165 */                                                       select_text = "selected";
/*      */                                                     }
/*      */                                                   }
/*      */                                                   
/* 6169 */                                                   out.write("\n                        <option value=\"");
/* 6170 */                                                   out.print(val);
/* 6171 */                                                   out.write(34);
/* 6172 */                                                   out.write(32);
/* 6173 */                                                   out.print(select_text);
/* 6174 */                                                   out.write(62);
/* 6175 */                                                   out.print(FormatUtil.getString(key));
/* 6176 */                                                   out.write("</option>\n                        ");
/*      */                                                   
/* 6178 */                                                   select_text = "";
/*      */                                                 }
/*      */                                                 
/* 6181 */                                                 out.write("\n                    <input type=\"hidden\" name=\"");
/* 6182 */                                                 out.print(row);
/* 6183 */                                                 out.write("\" id=\"");
/* 6184 */                                                 out.print(row);
/* 6185 */                                                 out.write("\" value=\"-\">");
/* 6186 */                                                 out.print(sideNote);
/* 6187 */                                                 out.write("\n                    </td>\n                    ");
/*      */                                               }
/*      */                                               
/*      */                                             }
/* 6191 */                                             else if (args_type.get(j.intValue()).toString().equals("5"))
/*      */                                             {
/* 6193 */                                               LinkedHashMap key_val = (LinkedHashMap)hm.get(row);
/* 6194 */                                               if (key_val != null) {
/* 6195 */                                                 Set st = key_val.keySet();
/* 6196 */                                                 Iterator itr = st.iterator();
/* 6197 */                                                 String select_text = "";
/* 6198 */                                                 int chk_but = 0;
/*      */                                                 
/* 6200 */                                                 out.write("\n            <td height=\"20\" align=\"left\">\n         ");
/*      */                                                 
/*      */ 
/* 6203 */                                                 while (itr.hasNext())
/*      */                                                 {
/* 6205 */                                                   String key = (String)itr.next();
/* 6206 */                                                   String val = (String)key_val.get(key);
/* 6207 */                                                   String showValue = val;
/* 6208 */                                                   if (val.trim().equals("")) {
/* 6209 */                                                     val = key;
/*      */                                                   }
/*      */                                                   
/* 6212 */                                                   if (((String)args_default.get(j.intValue()) != null) && (argsasprops.size() == 0) && 
/* 6213 */                                                     (((String)args_default.get(j.intValue())).equals(key))) {
/* 6214 */                                                     select_text = "checked";
/*      */                                                   }
/*      */                                                   
/*      */ 
/* 6218 */                                                   if (argsasprops.size() > 0)
/*      */                                                   {
/* 6220 */                                                     if ((argsasprops.getProperty(row) != null) && (argsasprops.getProperty(row).equals(val))) {
/* 6221 */                                                       select_text = "checked";
/*      */                                                     }
/*      */                                                   }
/* 6224 */                                                   else if (chk_but != 0) {}
/*      */                                                   
/*      */ 
/*      */ 
/* 6228 */                                                   chk_but++;
/*      */                                                   
/* 6230 */                                                   out.write("\n            <input type=\"checkbox\" id=\"");
/* 6231 */                                                   out.print(row);
/* 6232 */                                                   out.print(j);
/* 6233 */                                                   out.write("\" name =\"");
/* 6234 */                                                   out.print(row);
/* 6235 */                                                   out.print(j);
/* 6236 */                                                   out.write("\" value=\"");
/* 6237 */                                                   out.print(val);
/* 6238 */                                                   out.write(34);
/* 6239 */                                                   out.write(32);
/* 6240 */                                                   out.print(select_text);
/* 6241 */                                                   out.write(" onClick=\"javascript:showHideArgument(this,'");
/* 6242 */                                                   out.print(argsList);
/* 6243 */                                                   out.write(39);
/* 6244 */                                                   out.write(44);
/* 6245 */                                                   out.write(39);
/* 6246 */                                                   out.print(hideArgsList);
/* 6247 */                                                   out.write("',false);\" ><span class=\"bodytext\">");
/* 6248 */                                                   out.print(FormatUtil.getString(showValue));
/* 6249 */                                                   out.write("</span>\n             ");
/*      */                                                   
/* 6251 */                                                   select_text = "";
/*      */                                                 }
/*      */                                                 
/* 6254 */                                                 out.write("\n                <input type=\"hidden\" name=\"");
/* 6255 */                                                 out.print(row);
/* 6256 */                                                 out.write("\" id=\"");
/* 6257 */                                                 out.print(row);
/* 6258 */                                                 out.write("\" value=\"-\">");
/* 6259 */                                                 out.print(sideNote);
/* 6260 */                                                 out.write("\n                 </td>\n             ");
/*      */                                               }
/*      */                                             }
/*      */                                             
/*      */ 
/* 6265 */                                             out.write("\n               </tr>\n                \n                ");
/*      */                                           }
/* 6267 */                                           else if (args_type.get(j.intValue()).toString().equals("6")) {
/* 6268 */                                             String toappend = "";
/* 6269 */                                             if ((String)args_default.get(j.intValue()) != null) {
/* 6270 */                                               toappend = "value='" + (String)args_default.get(j.intValue()) + "'";
/*      */                                             }
/*      */                                             
/* 6273 */                                             out.write("\n                <input type=\"hidden\" name=\"");
/* 6274 */                                             out.print(row);
/* 6275 */                                             out.write("\" id=\"");
/* 6276 */                                             out.print(row);
/* 6277 */                                             out.write(34);
/* 6278 */                                             out.write(32);
/* 6279 */                                             out.print(toappend);
/* 6280 */                                             out.write(" >\n            ");
/*      */                                           }
/* 6282 */                                           out.write("\n                    \n                \n\n                ");
/* 6283 */                                           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 6284 */                                           row = (String)_jspx_page_context.findAttribute("row");
/* 6285 */                                           j = (Integer)_jspx_page_context.findAttribute("j");
/* 6286 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 6289 */                                         if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 6290 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 6293 */                                       if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 6294 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                       }
/*      */                                       
/* 6297 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 6298 */                                       out.write("\n                <tr>\n                 <td height=\"20\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>&nbsp;&nbsp;");
/* 6299 */                                       out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 6300 */                                       out.write("<span class=\"mandatory\">*</span></label></td><td  height=\"20\"><input type=\"text\" class=\"formtext small\" name =\"pollinterval\" ");
/* 6301 */                                       out.print(pollinterval_append);
/* 6302 */                                       out.write("/><span class=\"bodytext\">&nbsp;");
/* 6303 */                                       out.print(FormatUtil.getString("am.webclient.urlmonitor.unitofpoll.text"));
/* 6304 */                                       out.write("</span></td>\n\n                </tr>\n                \n                ");
/*      */                                       
/* 6306 */                                       if (!EnterpriseUtil.isAdminServer())
/*      */                                       {
/* 6308 */                                         out.write("\n                 <tr style=\"");
/* 6309 */                                         out.print(testCredStyle);
/* 6310 */                                         out.write("\">\n\n                <td  height=\"20\" width=\"25%\"></td>\n                <td height=\"20\"  class=\"bodytext\"><input name=\"testCredentialButton\" class=\"buttons btn_highlt\" value=\"");
/* 6311 */                                         out.print(FormatUtil.getString("Test Credential"));
/* 6312 */                                         out.write("\" onclick=\"javascript:validateAndPerformTestCredential();\" type=\"button\"> <span id=\"testCredentialResult\"></span></td>\n                </tr>\n\n                 ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 6316 */                                       out.write("\n            </table>\n\n\n    ");
/*      */                                       
/* 6318 */                                       if (EnterpriseUtil.isAdminServer())
/*      */                                       {
/* 6320 */                                         out.write("\n        ");
/* 6321 */                                         JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("managedServerGroups", request.getCharacterEncoding()), out, false);
/* 6322 */                                         out.write("  \n    ");
/*      */                                       }
/*      */                                       
/* 6325 */                                       if (resourceid.equals(""))
/*      */                                       {
/* 6327 */                                         out.write("\n        ");
/* 6328 */                                         JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("monitorGroups", request.getCharacterEncoding()), out, false);
/* 6329 */                                         out.write("   \n    ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 6333 */                                         if (EnterpriseUtil.isIt360MSPEdition())
/*      */                                         {
/* 6335 */                                           out.write("\n        <TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"8\" CELLSPACING=\"0\" class=\"lrborder ");
/* 6336 */                                           out.print(hideStyle);
/* 6337 */                                           out.write("\" CELLPADING=\"0\">\n        <tr><td style=\"height:25px;\"></td></tr>\n        <tr>\n            <td width=\"25%\" height=\"28\" valign=\"middle\" class=\"bodytext\">");
/* 6338 */                                           out.print("Customer");
/* 6339 */                                           out.write("<span class=\"mandatory\">*</span></td>\n            <td height=\"28\" align=\"left\" >\n                ");
/*      */                                           
/* 6341 */                                           SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 6342 */                                           _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 6343 */                                           _jspx_th_html_005fselect_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                           
/* 6345 */                                           _jspx_th_html_005fselect_005f1.setProperty("organization");
/*      */                                           
/* 6347 */                                           _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/*      */                                           
/* 6349 */                                           _jspx_th_html_005fselect_005f1.setOnchange("javascript:loadSite()");
/* 6350 */                                           int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 6351 */                                           if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 6352 */                                             if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 6353 */                                               out = _jspx_page_context.pushBody();
/* 6354 */                                               _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 6355 */                                               _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 6358 */                                               out.write("\n                ");
/*      */                                               
/* 6360 */                                               OptionTag _jspx_th_html_005foption_005f62 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 6361 */                                               _jspx_th_html_005foption_005f62.setPageContext(_jspx_page_context);
/* 6362 */                                               _jspx_th_html_005foption_005f62.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                               
/* 6364 */                                               _jspx_th_html_005foption_005f62.setValue("-");
/* 6365 */                                               int _jspx_eval_html_005foption_005f62 = _jspx_th_html_005foption_005f62.doStartTag();
/* 6366 */                                               if (_jspx_eval_html_005foption_005f62 != 0) {
/* 6367 */                                                 if (_jspx_eval_html_005foption_005f62 != 1) {
/* 6368 */                                                   out = _jspx_page_context.pushBody();
/* 6369 */                                                   _jspx_th_html_005foption_005f62.setBodyContent((BodyContent)out);
/* 6370 */                                                   _jspx_th_html_005foption_005f62.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 6373 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.customer", new String[] { MGSTR }));
/* 6374 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f62.doAfterBody();
/* 6375 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 6378 */                                                 if (_jspx_eval_html_005foption_005f62 != 1) {
/* 6379 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 6382 */                                               if (_jspx_th_html_005foption_005f62.doEndTag() == 5) {
/* 6383 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f62); return;
/*      */                                               }
/*      */                                               
/* 6386 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f62);
/* 6387 */                                               out.write("\n                ");
/*      */                                               
/* 6389 */                                               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 6390 */                                               _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 6391 */                                               _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                               
/* 6393 */                                               _jspx_th_logic_005fnotEmpty_005f2.setName("customers");
/* 6394 */                                               int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 6395 */                                               if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                                                 for (;;) {
/* 6397 */                                                   out.write(32);
/*      */                                                   
/* 6399 */                                                   IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 6400 */                                                   _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 6401 */                                                   _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                                   
/* 6403 */                                                   _jspx_th_logic_005fiterate_005f2.setName("customers");
/*      */                                                   
/* 6405 */                                                   _jspx_th_logic_005fiterate_005f2.setId("row");
/*      */                                                   
/* 6407 */                                                   _jspx_th_logic_005fiterate_005f2.setIndexId("j");
/*      */                                                   
/* 6409 */                                                   _jspx_th_logic_005fiterate_005f2.setType("java.util.ArrayList");
/* 6410 */                                                   int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 6411 */                                                   if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 6412 */                                                     ArrayList row = null;
/* 6413 */                                                     Integer j = null;
/* 6414 */                                                     if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 6415 */                                                       out = _jspx_page_context.pushBody();
/* 6416 */                                                       _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 6417 */                                                       _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                                                     }
/* 6419 */                                                     row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 6420 */                                                     j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                                     for (;;) {
/* 6422 */                                                       out.write("\n                ");
/*      */                                                       
/* 6424 */                                                       OptionTag _jspx_th_html_005foption_005f63 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 6425 */                                                       _jspx_th_html_005foption_005f63.setPageContext(_jspx_page_context);
/* 6426 */                                                       _jspx_th_html_005foption_005f63.setParent(_jspx_th_logic_005fiterate_005f2);
/*      */                                                       
/* 6428 */                                                       _jspx_th_html_005foption_005f63.setValue((String)row.get(1));
/* 6429 */                                                       int _jspx_eval_html_005foption_005f63 = _jspx_th_html_005foption_005f63.doStartTag();
/* 6430 */                                                       if (_jspx_eval_html_005foption_005f63 != 0) {
/* 6431 */                                                         if (_jspx_eval_html_005foption_005f63 != 1) {
/* 6432 */                                                           out = _jspx_page_context.pushBody();
/* 6433 */                                                           _jspx_th_html_005foption_005f63.setBodyContent((BodyContent)out);
/* 6434 */                                                           _jspx_th_html_005foption_005f63.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 6437 */                                                           out.print(row.get(0));
/* 6438 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f63.doAfterBody();
/* 6439 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 6442 */                                                         if (_jspx_eval_html_005foption_005f63 != 1) {
/* 6443 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 6446 */                                                       if (_jspx_th_html_005foption_005f63.doEndTag() == 5) {
/* 6447 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f63); return;
/*      */                                                       }
/*      */                                                       
/* 6450 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f63);
/* 6451 */                                                       out.write("\n                ");
/* 6452 */                                                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 6453 */                                                       row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 6454 */                                                       j = (Integer)_jspx_page_context.findAttribute("j");
/* 6455 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 6458 */                                                     if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 6459 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 6462 */                                                   if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 6463 */                                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                                                   }
/*      */                                                   
/* 6466 */                                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 6467 */                                                   out.write(32);
/* 6468 */                                                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 6469 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 6473 */                                               if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 6474 */                                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                                               }
/*      */                                               
/* 6477 */                                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 6478 */                                               out.write(32);
/* 6479 */                                               int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 6480 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 6483 */                                             if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 6484 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 6487 */                                           if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 6488 */                                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                                           }
/*      */                                           
/* 6491 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 6492 */                                           out.write("\n            </td>\n        </tr>\n        <tr>\n            <td width=\"25%\" height=\"28\" valign=\"middle\" class=\"bodytext\">");
/* 6493 */                                           out.print("Site");
/* 6494 */                                           out.write("<span class=\"mandatory\">*</span></td>\n            <td height=\"28\" align=\"left\" >\n                ");
/*      */                                           
/* 6496 */                                           SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 6497 */                                           _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 6498 */                                           _jspx_th_html_005fselect_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                           
/* 6500 */                                           _jspx_th_html_005fselect_005f2.setProperty("haid");
/*      */                                           
/* 6502 */                                           _jspx_th_html_005fselect_005f2.setStyleClass("formtext");
/* 6503 */                                           int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 6504 */                                           if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 6505 */                                             if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 6506 */                                               out = _jspx_page_context.pushBody();
/* 6507 */                                               _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 6508 */                                               _jspx_th_html_005fselect_005f2.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 6511 */                                               out.write("\n                      ");
/*      */                                               
/* 6513 */                                               OptionTag _jspx_th_html_005foption_005f64 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 6514 */                                               _jspx_th_html_005foption_005f64.setPageContext(_jspx_page_context);
/* 6515 */                                               _jspx_th_html_005foption_005f64.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                               
/* 6517 */                                               _jspx_th_html_005foption_005f64.setValue("-");
/* 6518 */                                               int _jspx_eval_html_005foption_005f64 = _jspx_th_html_005foption_005f64.doStartTag();
/* 6519 */                                               if (_jspx_eval_html_005foption_005f64 != 0) {
/* 6520 */                                                 if (_jspx_eval_html_005foption_005f64 != 1) {
/* 6521 */                                                   out = _jspx_page_context.pushBody();
/* 6522 */                                                   _jspx_th_html_005foption_005f64.setBodyContent((BodyContent)out);
/* 6523 */                                                   _jspx_th_html_005foption_005f64.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 6526 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.siteGroup", new String[] { MGSTR }));
/* 6527 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f64.doAfterBody();
/* 6528 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 6531 */                                                 if (_jspx_eval_html_005foption_005f64 != 1) {
/* 6532 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 6535 */                                               if (_jspx_th_html_005foption_005f64.doEndTag() == 5) {
/* 6536 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f64); return;
/*      */                                               }
/*      */                                               
/* 6539 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f64);
/* 6540 */                                               out.write("\n                      ");
/*      */                                               
/* 6542 */                                               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 6543 */                                               _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 6544 */                                               _jspx_th_logic_005fnotEmpty_005f3.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                               
/* 6546 */                                               _jspx_th_logic_005fnotEmpty_005f3.setName("applications");
/* 6547 */                                               int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 6548 */                                               if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */                                                 for (;;) {
/* 6550 */                                                   out.write(32);
/*      */                                                   
/* 6552 */                                                   IterateTag _jspx_th_logic_005fiterate_005f3 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 6553 */                                                   _jspx_th_logic_005fiterate_005f3.setPageContext(_jspx_page_context);
/* 6554 */                                                   _jspx_th_logic_005fiterate_005f3.setParent(_jspx_th_logic_005fnotEmpty_005f3);
/*      */                                                   
/* 6556 */                                                   _jspx_th_logic_005fiterate_005f3.setName("applications");
/*      */                                                   
/* 6558 */                                                   _jspx_th_logic_005fiterate_005f3.setId("row");
/*      */                                                   
/* 6560 */                                                   _jspx_th_logic_005fiterate_005f3.setIndexId("j");
/*      */                                                   
/* 6562 */                                                   _jspx_th_logic_005fiterate_005f3.setType("java.util.ArrayList");
/* 6563 */                                                   int _jspx_eval_logic_005fiterate_005f3 = _jspx_th_logic_005fiterate_005f3.doStartTag();
/* 6564 */                                                   if (_jspx_eval_logic_005fiterate_005f3 != 0) {
/* 6565 */                                                     ArrayList row = null;
/* 6566 */                                                     Integer j = null;
/* 6567 */                                                     if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 6568 */                                                       out = _jspx_page_context.pushBody();
/* 6569 */                                                       _jspx_th_logic_005fiterate_005f3.setBodyContent((BodyContent)out);
/* 6570 */                                                       _jspx_th_logic_005fiterate_005f3.doInitBody();
/*      */                                                     }
/* 6572 */                                                     row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 6573 */                                                     j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                                     for (;;) {
/* 6575 */                                                       out.write("\n                      ");
/*      */                                                       
/* 6577 */                                                       OptionTag _jspx_th_html_005foption_005f65 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 6578 */                                                       _jspx_th_html_005foption_005f65.setPageContext(_jspx_page_context);
/* 6579 */                                                       _jspx_th_html_005foption_005f65.setParent(_jspx_th_logic_005fiterate_005f3);
/*      */                                                       
/* 6581 */                                                       _jspx_th_html_005foption_005f65.setValue((String)row.get(1));
/* 6582 */                                                       int _jspx_eval_html_005foption_005f65 = _jspx_th_html_005foption_005f65.doStartTag();
/* 6583 */                                                       if (_jspx_eval_html_005foption_005f65 != 0) {
/* 6584 */                                                         if (_jspx_eval_html_005foption_005f65 != 1) {
/* 6585 */                                                           out = _jspx_page_context.pushBody();
/* 6586 */                                                           _jspx_th_html_005foption_005f65.setBodyContent((BodyContent)out);
/* 6587 */                                                           _jspx_th_html_005foption_005f65.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 6590 */                                                           out.print(row.get(0));
/* 6591 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f65.doAfterBody();
/* 6592 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 6595 */                                                         if (_jspx_eval_html_005foption_005f65 != 1) {
/* 6596 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 6599 */                                                       if (_jspx_th_html_005foption_005f65.doEndTag() == 5) {
/* 6600 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f65); return;
/*      */                                                       }
/*      */                                                       
/* 6603 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f65);
/* 6604 */                                                       out.write("\n                      ");
/* 6605 */                                                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f3.doAfterBody();
/* 6606 */                                                       row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 6607 */                                                       j = (Integer)_jspx_page_context.findAttribute("j");
/* 6608 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 6611 */                                                     if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 6612 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 6615 */                                                   if (_jspx_th_logic_005fiterate_005f3.doEndTag() == 5) {
/* 6616 */                                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3); return;
/*      */                                                   }
/*      */                                                   
/* 6619 */                                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3);
/* 6620 */                                                   out.write(32);
/* 6621 */                                                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 6622 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 6626 */                                               if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 6627 */                                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3); return;
/*      */                                               }
/*      */                                               
/* 6630 */                                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 6631 */                                               out.write(" \n                ");
/* 6632 */                                               int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 6633 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 6636 */                                             if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 6637 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 6640 */                                           if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 6641 */                                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2); return;
/*      */                                           }
/*      */                                           
/* 6644 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 6645 */                                           out.write("\n            </td>\n        </tr>\n    ");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 6649 */                                           out.write(" \n        <input type=\"hidden\" name=\"haid\" value=\"-\"> \n");
/*      */                                         }
/*      */                                         
/*      */ 
/* 6653 */                                         out.write("\n        </table>\n");
/*      */                                       }
/*      */                                       
/*      */ 
/* 6657 */                                       out.write("\n    \n");
/*      */                                       
/* 6659 */                                       EqualTag _jspx_th_logic_005fequal_005f1 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.get(EqualTag.class);
/* 6660 */                                       _jspx_th_logic_005fequal_005f1.setPageContext(_jspx_page_context);
/* 6661 */                                       _jspx_th_logic_005fequal_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6663 */                                       _jspx_th_logic_005fequal_005f1.setName("isAgentEnabled");
/*      */                                       
/* 6665 */                                       _jspx_th_logic_005fequal_005f1.setValue("YES");
/* 6666 */                                       int _jspx_eval_logic_005fequal_005f1 = _jspx_th_logic_005fequal_005f1.doStartTag();
/* 6667 */                                       if (_jspx_eval_logic_005fequal_005f1 != 0) {
/*      */                                         for (;;) {
/* 6669 */                                           out.write(32);
/* 6670 */                                           out.write(10);
/* 6671 */                                           out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n");
/*      */                                           
/* 6673 */                                           String isRBM = (String)request.getAttribute("isRBM");
/* 6674 */                                           String selQuery = "select AGENTID,DISPLAYNAME,STATUS,DESCRIPTION,AGENTVERSION from AM_RBMAGENTDATA where AGENTID >" + EnterpriseUtil.getDistributedStartResourceId();
/* 6675 */                                           if ((isRBM != null) && (isRBM.equals("true")))
/*      */                                           {
/* 6677 */                                             selQuery = "select AGENTID,DISPLAYNAME,STATUS,DESCRIPTION,AGENTVERSION from AM_RBMAGENTDATA where AGENTID NOT IN(" + EnterpriseUtil.getDistributedStartResourceId() + ") and AGENTNAME NOT LIKE ('%(Local)')";
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 6681 */                                             isRBM = "false";
/*      */                                           }
/* 6683 */                                           System.out.println("is RBM : " + isRBM);
/* 6684 */                                           ArrayList agentLocation = new ArrayList();
/* 6685 */                                           ArrayList agentStatus = new ArrayList();
/* 6686 */                                           ArrayList<String> isDisabled = new ArrayList();
/* 6687 */                                           ResultSet rs = AMConnectionPool.executeQueryStmt(selQuery);
/* 6688 */                                           while (rs.next())
/*      */                                           {
/* 6690 */                                             String agentId = rs.getString("AGENTID");
/* 6691 */                                             String displayName = FormatUtil.getTrimmedText(rs.getString("DISPLAYNAME"), 23);
/* 6692 */                                             String version = rs.getString("AGENTVERSION");
/* 6693 */                                             if (isRBM.equals("true"))
/*      */                                             {
/* 6695 */                                               String browserType = "1";
/* 6696 */                                               Properties argProps = (Properties)request.getAttribute("argsasprops");
/*      */                                               try {
/* 6698 */                                                 browserType = argProps.getProperty("browserType");
/*      */                                               }
/*      */                                               catch (NullPointerException npe) {}
/* 6701 */                                               String desc = rs.getString("DESCRIPTION");
/* 6702 */                                               if (("1".equals(browserType)) && (version != null) && (Integer.parseInt(version.replace(".", "")) <= com.adventnet.appmanager.util.Constants.eumAppAgent))
/*      */                                               {
/* 6704 */                                                 isDisabled.add("true");
/*      */                                               } else {
/* 6706 */                                                 isDisabled.add("false");
/*      */                                               }
/*      */                                             } else {
/* 6709 */                                               isDisabled.add("false");
/*      */                                             }
/* 6711 */                                             agentLocation.add(new org.apache.struts.util.LabelValueBean(displayName, agentId));
/* 6712 */                                             agentStatus.add(rs.getString("STATUS"));
/*      */                                           }
/*      */                                           
/* 6715 */                                           pageContext.setAttribute("agentLocation", agentLocation);
/* 6716 */                                           String disable = "";
/* 6717 */                                           String licenseTooltip = "";
/* 6718 */                                           String hideClass = "";
/* 6719 */                                           if ((!FreeEditionDetails.getFreeEditionDetails().isEUMAllowed()) || (EnterpriseUtil.isCloudEdition())) {
/* 6720 */                                             disable = "disabled='disabled'";
/* 6721 */                                             licenseTooltip = FormatUtil.getString("am.webclient.eumdashboard.license.addon");
/* 6722 */                                             hideClass = "hideddrivetip()";
/*      */                                           }
/*      */                                           
/*      */ 
/* 6726 */                                           out.write("\n\n\n<style type=\"text/css\">\n.upgradeAmountBg1 {\n\tbackground-color: #f7f7f7;\n}\n\n.labeltd {\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-size: 11px;\n\tcolor: #333333;\n\theight: 25px;\n\tpadding-left: 10px;\n}\n</style>\n\n\n<table width='");
/* 6727 */                                           out.print(fromWhere.equals("CONF") ? "99%" : "100%");
/* 6728 */                                           out.write("' border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class='");
/* 6729 */                                           out.print(fromWhere.equals("CONF") ? "lrbborder" : "");
/* 6730 */                                           out.write("'>\n\t\n\t<tr>\n\t\t<td height=\"25\" class=\"plainheading1\">");
/* 6731 */                                           out.print(FormatUtil.getString("am.webclient.eumagent.associate"));
/* 6732 */                                           out.write("</td>\n\t</tr>\n\t<tr height=\"25\">\n\t\t<td style=\"font-size: 12px;padding: 10px;\" nowrap>\n\t\t\t");
/* 6733 */                                           if (fromWhere.equals("CONF")) {
/* 6734 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 6736 */                                             IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6737 */                                             _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 6738 */                                             _jspx_th_c_005fif_005f8.setParent(_jspx_th_logic_005fequal_005f1);
/*      */                                             
/* 6740 */                                             _jspx_th_c_005fif_005f8.setTest("${!isRBM}");
/* 6741 */                                             int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 6742 */                                             if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                               for (;;) {
/* 6744 */                                                 out.write("\n\t\t\t\t<span style=\"padding-left:10px;\" class=\"bcactive\"><input style=\"position:relative;\" type=\"checkbox\" name=\"runOnServer\" value=\"runOnServer\" checked='checked'/>");
/* 6745 */                                                 out.print(FormatUtil.getString("am.webclient.eumagent.runonserver"));
/* 6746 */                                                 out.write("</span>\n\t\t\t");
/* 6747 */                                                 int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 6748 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 6752 */                                             if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 6753 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                             }
/*      */                                             
/* 6756 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 6757 */                                             out.write("\n\t\t\t<span style=\"padding-left:10px;\" class=\"bcactive\"  onMouseOver=\"ddrivetip(this,event,'");
/* 6758 */                                             out.print(FormatUtil.getString(licenseTooltip));
/* 6759 */                                             out.write("',false,true,'#000000',150,'lightyellow')\" onmouseout=\"");
/* 6760 */                                             out.print(licenseTooltip.equals("") ? "" : "hideddrivetip();");
/* 6761 */                                             out.write("\">\t\t\t\t\n\t\t\t\t<input style=\"position:relative;\" type=\"checkbox\" class=\"agentLocationCheckbox\" name=\"runOnAgent\" id=\"runOnAgent\" value=\"runOnAgent\" ");
/* 6762 */                                             out.print(disable);
/* 6763 */                                             out.write("/>\t\t\t\n\t\t\t\t");
/* 6764 */                                             out.print(FormatUtil.getString("am.webclient.eumagent.runonagent"));
/* 6765 */                                             out.write("&nbsp;&nbsp;(");
/* 6766 */                                             out.print(FormatUtil.getString("am.webclient.common.eum"));
/* 6767 */                                             out.write(41);
/* 6768 */                                             if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 6769 */                                               out.write("&nbsp;<img border=\"0\" align=\"middle\" title=\"Add On is Enabled for Evaluation / Trial Versions\" style=\"position: relative; bottom: 2px;\" src=\"/images/icon_addon.gif\"></span>");
/*      */                                             }
/* 6771 */                                             out.write("\n\t\t\t\t&nbsp;<span>");
/* 6772 */                                             out.print(FormatUtil.getString("am.webclient.eum.learneumlink"));
/* 6773 */                                             out.write("\n\t\t\t\t\n\t\t\t</span>\n\t\t\t<div class=\"agentLoactionDiv\" style=\"padding: 10px;display: none;width: 99%;\">\n\t\t\t");
/*      */                                           } else {
/* 6775 */                                             out.write("\n\t\t\t\t<div class=\"agentLoactionDiv\" style=\"padding: 10px;display: block;width: 99%;\">\n\t\t\t\t\n\t\t\t");
/*      */                                           }
/* 6777 */                                           if (!agentLocation.isEmpty()) {
/* 6778 */                                             out.write("\n\t\t\t\t<table class=\"bodytext upgradeAmountBg1\" width=\"100%\">\n\t\t\t\t\t<tr><td>\n\t\t\t\t\t<table class=\"bodytext upgradeAmountBg1\" width=\"55%\">\n\t\t\t\t\t\n\t\t\t\t\t<tr height=\"28\">\n\t\t\t\t\t\t<span class=\"bodytext\">\n\t\t\t\t\t\t");
/*      */                                             
/* 6780 */                                             CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick.get(CheckboxTag.class);
/* 6781 */                                             _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 6782 */                                             _jspx_th_html_005fcheckbox_005f0.setParent(_jspx_th_logic_005fequal_005f1);
/*      */                                             
/* 6784 */                                             _jspx_th_html_005fcheckbox_005f0.setProperty("selectAllAgents");
/*      */                                             
/* 6786 */                                             _jspx_th_html_005fcheckbox_005f0.setOnclick("toggleChecked(this.checked)");
/* 6787 */                                             int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 6788 */                                             if (_jspx_eval_html_005fcheckbox_005f0 != 0) {
/* 6789 */                                               if (_jspx_eval_html_005fcheckbox_005f0 != 1) {
/* 6790 */                                                 out = _jspx_page_context.pushBody();
/* 6791 */                                                 _jspx_th_html_005fcheckbox_005f0.setBodyContent((BodyContent)out);
/* 6792 */                                                 _jspx_th_html_005fcheckbox_005f0.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 6795 */                                                 out.print(FormatUtil.getString("am.webclient.eumagent.selectall"));
/* 6796 */                                                 int evalDoAfterBody = _jspx_th_html_005fcheckbox_005f0.doAfterBody();
/* 6797 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 6800 */                                               if (_jspx_eval_html_005fcheckbox_005f0 != 1) {
/* 6801 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 6804 */                                             if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 6805 */                                               this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick.reuse(_jspx_th_html_005fcheckbox_005f0); return;
/*      */                                             }
/*      */                                             
/* 6808 */                                             this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 6809 */                                             out.write("</span><br><br>\n\t\t\t\t\t\t");
/*      */                                             
/* 6811 */                                             IterateTag _jspx_th_logic_005fiterate_005f4 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 6812 */                                             _jspx_th_logic_005fiterate_005f4.setPageContext(_jspx_page_context);
/* 6813 */                                             _jspx_th_logic_005fiterate_005f4.setParent(_jspx_th_logic_005fequal_005f1);
/*      */                                             
/* 6815 */                                             _jspx_th_logic_005fiterate_005f4.setName("agentLocation");
/*      */                                             
/* 6817 */                                             _jspx_th_logic_005fiterate_005f4.setId("userAgent");
/*      */                                             
/* 6819 */                                             _jspx_th_logic_005fiterate_005f4.setIndexId("j");
/* 6820 */                                             int _jspx_eval_logic_005fiterate_005f4 = _jspx_th_logic_005fiterate_005f4.doStartTag();
/* 6821 */                                             if (_jspx_eval_logic_005fiterate_005f4 != 0) {
/* 6822 */                                               Object userAgent = null;
/* 6823 */                                               Integer j = null;
/* 6824 */                                               if (_jspx_eval_logic_005fiterate_005f4 != 1) {
/* 6825 */                                                 out = _jspx_page_context.pushBody();
/* 6826 */                                                 _jspx_th_logic_005fiterate_005f4.setBodyContent((BodyContent)out);
/* 6827 */                                                 _jspx_th_logic_005fiterate_005f4.doInitBody();
/*      */                                               }
/* 6829 */                                               userAgent = _jspx_page_context.findAttribute("userAgent");
/* 6830 */                                               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                               for (;;) {
/* 6832 */                                                 out.write("\n\t\t\t\t\t\t\t");
/*      */                                                 
/* 6834 */                                                 String textclass = "color='black'";
/* 6835 */                                                 String status = "";
/* 6836 */                                                 if (!((String)agentStatus.get(j.intValue())).equals("0")) {
/* 6837 */                                                   textclass = "color='red'";
/* 6838 */                                                   status = FormatUtil.getString("am.webclient.eumdashboard.agentdown");
/*      */                                                 }
/*      */                                                 
/*      */ 
/* 6842 */                                                 out.write("\n\t\t\t\t\t\t\t<td nowrap=\"nowrap\" style=\"padding-left:18px;\">");
/*      */                                                 
/* 6844 */                                                 MultiboxTag _jspx_th_html_005fmultibox_005f0 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled.get(MultiboxTag.class);
/* 6845 */                                                 _jspx_th_html_005fmultibox_005f0.setPageContext(_jspx_page_context);
/* 6846 */                                                 _jspx_th_html_005fmultibox_005f0.setParent(_jspx_th_logic_005fiterate_005f4);
/*      */                                                 
/* 6848 */                                                 _jspx_th_html_005fmultibox_005f0.setProperty("selectedAgents");
/*      */                                                 
/* 6850 */                                                 _jspx_th_html_005fmultibox_005f0.setOnclick("javascript:findSelectedIndex('" + j + "');");
/*      */                                                 
/* 6852 */                                                 _jspx_th_html_005fmultibox_005f0.setDisabled(Boolean.parseBoolean((String)isDisabled.get(j.intValue())));
/* 6853 */                                                 int _jspx_eval_html_005fmultibox_005f0 = _jspx_th_html_005fmultibox_005f0.doStartTag();
/* 6854 */                                                 if (_jspx_eval_html_005fmultibox_005f0 != 0) {
/* 6855 */                                                   if (_jspx_eval_html_005fmultibox_005f0 != 1) {
/* 6856 */                                                     out = _jspx_page_context.pushBody();
/* 6857 */                                                     _jspx_th_html_005fmultibox_005f0.setBodyContent((BodyContent)out);
/* 6858 */                                                     _jspx_th_html_005fmultibox_005f0.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 6861 */                                                     out.write("\n\t\t\t\t\t\t\t\t");
/* 6862 */                                                     if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmultibox_005f0, _jspx_page_context))
/*      */                                                       return;
/* 6864 */                                                     out.write("\n\t\t\t\t\t\t\t\t");
/* 6865 */                                                     int evalDoAfterBody = _jspx_th_html_005fmultibox_005f0.doAfterBody();
/* 6866 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 6869 */                                                   if (_jspx_eval_html_005fmultibox_005f0 != 1) {
/* 6870 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 6873 */                                                 if (_jspx_th_html_005fmultibox_005f0.doEndTag() == 5) {
/* 6874 */                                                   this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled.reuse(_jspx_th_html_005fmultibox_005f0); return;
/*      */                                                 }
/*      */                                                 
/* 6877 */                                                 this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled.reuse(_jspx_th_html_005fmultibox_005f0);
/* 6878 */                                                 out.write("\n\t\t\t\t\t\t\t\t<a style=\"cursor:pointer\" class=\"tooltip\" onMouseOver=\"ddrivetip(this,event,'");
/* 6879 */                                                 out.print(status);
/* 6880 */                                                 out.write("',false,true,'#000000',100,'lightyellow')\" onmouseout='");
/* 6881 */                                                 out.print(status.equals("") ? "" : "hideddrivetip()");
/* 6882 */                                                 out.write("'>\n\t\t\t\t\t\t\t\t\t<font ");
/* 6883 */                                                 out.print(textclass);
/* 6884 */                                                 out.write(62);
/* 6885 */                                                 if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_logic_005fiterate_005f4, _jspx_page_context))
/*      */                                                   return;
/* 6887 */                                                 out.write("</font>\n\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t");
/* 6888 */                                                 if (_jspx_meth_c_005fif_005f9(_jspx_th_logic_005fiterate_005f4, _jspx_page_context))
/*      */                                                   return;
/* 6890 */                                                 out.write("\n\t\t\t\t\t\t");
/* 6891 */                                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f4.doAfterBody();
/* 6892 */                                                 userAgent = _jspx_page_context.findAttribute("userAgent");
/* 6893 */                                                 j = (Integer)_jspx_page_context.findAttribute("j");
/* 6894 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 6897 */                                               if (_jspx_eval_logic_005fiterate_005f4 != 1) {
/* 6898 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 6901 */                                             if (_jspx_th_logic_005fiterate_005f4.doEndTag() == 5) {
/* 6902 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f4); return;
/*      */                                             }
/*      */                                             
/* 6905 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f4);
/* 6906 */                                             out.write("\n\t\t\t\t\t</table></td></tr>\t\n\t\t\t\t</table>\n\t\t\t\t\n\t\t\t\t\n\t\t\t\t");
/*      */                                           } else {
/* 6908 */                                             out.write("\n\t\t\t\t\t<table class=\"bodytext upgradeAmountBg1\" width=\"100%\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td><span class=\"bodytext\">");
/* 6909 */                                             out.print(FormatUtil.getString("am.webclient.eumdashboard.noagents"));
/* 6910 */                                             out.write("</span></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t");
/*      */                                           }
/* 6912 */                                           out.write("\n\t\t\t\t\n\t\t\t</div>\n\t\t</td>\n\t</tr>\n\t<tr>\n\t</tr>\n</table>\n\n<script>\n\n$(document).ready(function(){\n\t$('.agentLocationCheckbox').click(function() //NO I18N \n   \t{\n\t\tif($(this).attr(\"checked\"))\n\t\t{\n\t\t\t");
/*      */                                           
/* 6914 */                                           if (EnterpriseUtil.isAdminServer())
/*      */                                           {
/* 6916 */                                             out.write("\t\n\t\t\t\t\tvar masObj = document.AMActionForm.selectedServer;\t\t\t\t\t\n\t\t\t\t\tif(masObj != 'undefined' && masObj.value == '-')\n\t\t\t\t\t{\n\t\t\t\t\t\talert('");
/* 6917 */                                             out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.mas.text"));
/* 6918 */                                             out.write("');\n\t\t\t\t\t\treturn false;\n\t\t\t\t\t}\n\t\t\t\t\telse if(masObj != 'undefined')\n\t\t\t\t\t{\n\t\t\t\t\t\t$.post(\"/showAgent.do?method=getAgentDetails&selectedServer=\"+masObj.value+\"&ajaxRequest=true\", function (data){$('.agentLoactionDiv').html(data);});//NO I18N\n\t\t\t\t\t}\n\t\t\t");
/*      */                                           }
/*      */                                           
/*      */ 
/* 6922 */                                           out.write("\n\t\t\t$('.agentLoactionDiv').slideDown(\"slow\"); //NO I18N\n\t\t}\n\t\telse\n\t\t{\n\t\t\ttoggleChecked(false);\n\t\t\t$('.agentLoactionDiv').slideUp(\"slow\"); //NO I18N\n\t\t}\n\t})\n  }\n);\n\nfunction toggleChecked(status) \n{\n\t$(\".agentLoactionDiv input:enabled\").each( function() { //NO I18N \n\t{\n\t\t$(this).attr(\"checked\",status); //NO I18N\n\t}})\n}\n\nfunction findSelectedIndex(id){\n\t");
/* 6923 */                                           if (fromWhere.equals("CONF")) {
/* 6924 */                                             out.write("\n\tif(document.AMActionForm.selectAllAgents.checked == true && document.AMActionForm.selectedAgents[id].checked==false)\n\t{\n\t\tdocument.AMActionForm.selectAllAgents.checked =false;\n\t}\n\t");
/*      */                                           } else {
/* 6926 */                                             out.write("\n\t\tif(document.RbmForm.selectAllAgents.checked == true && document.RbmForm.selectedAgents[id].checked==false)\n\t\t{\n\t\tdocument.RbmForm.selectAllAgents.checked =false;\n\t\t}\t\n\t");
/*      */                                           }
/* 6928 */                                           out.write("\n}\nfunction checkAgentSelected(){\n\tif(document.AMActionForm.runOnServer && document.AMActionForm.runOnServer.checked == true){\n\t\treturn true;\n\t}\n\telse if(document.AMActionForm.runOnAgent.checked == true){\n\t\tif(document.AMActionForm.selectAllAgents){\n\t\t\tnoOfAgents=");
/* 6929 */                                           out.print(agentLocation.size());
/* 6930 */                                           out.write(";\t\t\t\n\t\t\tif(document.AMActionForm.selectAllAgents.checked == true){\n\t\t\t\treturn true;\n\t\t\t}\t\t\t\n\t\t\telse if(noOfAgents>1){\t\t\t\t\n\t\t\t\t");
/*      */                                           
/* 6932 */                                           IterateTag _jspx_th_logic_005fiterate_005f5 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 6933 */                                           _jspx_th_logic_005fiterate_005f5.setPageContext(_jspx_page_context);
/* 6934 */                                           _jspx_th_logic_005fiterate_005f5.setParent(_jspx_th_logic_005fequal_005f1);
/*      */                                           
/* 6936 */                                           _jspx_th_logic_005fiterate_005f5.setName("agentLocation");
/*      */                                           
/* 6938 */                                           _jspx_th_logic_005fiterate_005f5.setId("userAgent");
/*      */                                           
/* 6940 */                                           _jspx_th_logic_005fiterate_005f5.setIndexId("j");
/* 6941 */                                           int _jspx_eval_logic_005fiterate_005f5 = _jspx_th_logic_005fiterate_005f5.doStartTag();
/* 6942 */                                           if (_jspx_eval_logic_005fiterate_005f5 != 0) {
/* 6943 */                                             Object userAgent = null;
/* 6944 */                                             Integer j = null;
/* 6945 */                                             if (_jspx_eval_logic_005fiterate_005f5 != 1) {
/* 6946 */                                               out = _jspx_page_context.pushBody();
/* 6947 */                                               _jspx_th_logic_005fiterate_005f5.setBodyContent((BodyContent)out);
/* 6948 */                                               _jspx_th_logic_005fiterate_005f5.doInitBody();
/*      */                                             }
/* 6950 */                                             userAgent = _jspx_page_context.findAttribute("userAgent");
/* 6951 */                                             j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                             for (;;) {
/* 6953 */                                               out.write("\n\t\t\t\t\tif(document.AMActionForm.selectedAgents[");
/* 6954 */                                               out.print(j);
/* 6955 */                                               out.write("].checked==true){\n\t\t\t\t\t\treturn true;\n\t\t\t\t\t}\n\t\t\t\t\n\t\t\t\t");
/* 6956 */                                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f5.doAfterBody();
/* 6957 */                                               userAgent = _jspx_page_context.findAttribute("userAgent");
/* 6958 */                                               j = (Integer)_jspx_page_context.findAttribute("j");
/* 6959 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 6962 */                                             if (_jspx_eval_logic_005fiterate_005f5 != 1) {
/* 6963 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 6966 */                                           if (_jspx_th_logic_005fiterate_005f5.doEndTag() == 5) {
/* 6967 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f5); return;
/*      */                                           }
/*      */                                           
/* 6970 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f5);
/* 6971 */                                           out.write("\n\t\t\t}else{\n\t\t\t\tif(document.AMActionForm.selectedAgents.checked==true){\n\t\t\t\t\treturn true;\n\t\t\t\t}\t\t\t\t\n\t\t\t}\n\t\t}\n\t\telse{\n\t\t\talert(\"");
/* 6972 */                                           out.print(FormatUtil.getString("am.webclient.eumagent.validate.noagent"));
/* 6973 */                                           out.write("\");\n\t\t\tnoOfAgents=0;\n\t\t}\n\t}\n\t\n\treturn false;\n}\nfunction checkAgentSelectedForRBM(){\n\tif(document.RbmForm.selectAllAgents){\n\t\tnoOfAgents=");
/* 6974 */                                           out.print(agentLocation.size());
/* 6975 */                                           out.write(";\t\n\t\tif(document.RbmForm.selectAllAgents.checked == true){\n\t\t\t\treturn true;\n\t\t}\n\t\telse if(noOfAgents>1){\t\t\t\t\n\t\t\t");
/*      */                                           
/* 6977 */                                           IterateTag _jspx_th_logic_005fiterate_005f6 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 6978 */                                           _jspx_th_logic_005fiterate_005f6.setPageContext(_jspx_page_context);
/* 6979 */                                           _jspx_th_logic_005fiterate_005f6.setParent(_jspx_th_logic_005fequal_005f1);
/*      */                                           
/* 6981 */                                           _jspx_th_logic_005fiterate_005f6.setName("agentLocation");
/*      */                                           
/* 6983 */                                           _jspx_th_logic_005fiterate_005f6.setId("userAgent");
/*      */                                           
/* 6985 */                                           _jspx_th_logic_005fiterate_005f6.setIndexId("j");
/* 6986 */                                           int _jspx_eval_logic_005fiterate_005f6 = _jspx_th_logic_005fiterate_005f6.doStartTag();
/* 6987 */                                           if (_jspx_eval_logic_005fiterate_005f6 != 0) {
/* 6988 */                                             Object userAgent = null;
/* 6989 */                                             Integer j = null;
/* 6990 */                                             if (_jspx_eval_logic_005fiterate_005f6 != 1) {
/* 6991 */                                               out = _jspx_page_context.pushBody();
/* 6992 */                                               _jspx_th_logic_005fiterate_005f6.setBodyContent((BodyContent)out);
/* 6993 */                                               _jspx_th_logic_005fiterate_005f6.doInitBody();
/*      */                                             }
/* 6995 */                                             userAgent = _jspx_page_context.findAttribute("userAgent");
/* 6996 */                                             j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                             for (;;) {
/* 6998 */                                               out.write("\n\t\t\t\tif(document.RbmForm.selectedAgents[");
/* 6999 */                                               out.print(j);
/* 7000 */                                               out.write("].checked==true){\n\t\t\t\t\t\t\treturn true;\n\t\t\t\t}\t\t\t\t\t\n\t\t\t");
/* 7001 */                                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f6.doAfterBody();
/* 7002 */                                               userAgent = _jspx_page_context.findAttribute("userAgent");
/* 7003 */                                               j = (Integer)_jspx_page_context.findAttribute("j");
/* 7004 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 7007 */                                             if (_jspx_eval_logic_005fiterate_005f6 != 1) {
/* 7008 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 7011 */                                           if (_jspx_th_logic_005fiterate_005f6.doEndTag() == 5) {
/* 7012 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f6); return;
/*      */                                           }
/*      */                                           
/* 7015 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f6);
/* 7016 */                                           out.write("\n\t\t}else if(document.RbmForm.selectedAgents.checked==true){\n\t\t\t\treturn true;\n\t\t\t\t\t\t\t\n\t\t}\n\t}\n\t\n\treturn false;\n}\n</script>\n");
/* 7017 */                                           out.write(10);
/* 7018 */                                           int evalDoAfterBody = _jspx_th_logic_005fequal_005f1.doAfterBody();
/* 7019 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 7023 */                                       if (_jspx_th_logic_005fequal_005f1.doEndTag() == 5) {
/* 7024 */                                         this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f1); return;
/*      */                                       }
/*      */                                       
/* 7027 */                                       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/* 7028 */                                       out.write(10);
/*      */                                       
/* 7030 */                                       String goBackStr = "goBack();";
/* 7031 */                                       if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                       {
/* 7033 */                                         goBackStr = "history.back();";
/*      */                                       }
/*      */                                       
/* 7036 */                                       out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"25%\" class=\"tablebottom\" style=\"height:30px;\"></td>\n    <td align=\"left\" class=\"tablebottom\">\n      <input name=\"button1\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 7037 */                                       out.print(addmonitors);
/* 7038 */                                       out.write("\" onClick=\"validateAndSubmit();\">\n      &nbsp; ");
/*      */                                       
/* 7040 */                                       ResetTag _jspx_th_html_005freset_005f0 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.get(ResetTag.class);
/* 7041 */                                       _jspx_th_html_005freset_005f0.setPageContext(_jspx_page_context);
/* 7042 */                                       _jspx_th_html_005freset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 7044 */                                       _jspx_th_html_005freset_005f0.setStyleClass("buttons btn_reset");
/*      */                                       
/* 7046 */                                       _jspx_th_html_005freset_005f0.setValue(restoredefaults);
/* 7047 */                                       int _jspx_eval_html_005freset_005f0 = _jspx_th_html_005freset_005f0.doStartTag();
/* 7048 */                                       if (_jspx_th_html_005freset_005f0.doEndTag() == 5) {
/* 7049 */                                         this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005freset_005f0); return;
/*      */                                       }
/*      */                                       
/* 7052 */                                       this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005freset_005f0);
/* 7053 */                                       out.write(" &nbsp; <input type=\"button\" value=\"");
/* 7054 */                                       out.print(cancel);
/* 7055 */                                       out.write("\" onClick=\"");
/* 7056 */                                       out.print(goBackStr);
/* 7057 */                                       out.write("\" class=\"buttons btn_link\" id=\"cancelButtonMod\"/> ");
/* 7058 */                                       out.write("\n    </td>\n  </tr>\n</table>\n\n</td>\n<td width=\"30%\" valign=\"top\">\n\n\n\n\n");
/*      */                                       
/*      */ 
/* 7061 */                                       String workingdir = File.separator;
/* 7062 */                                       if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 7063 */                                         workingdir = new File(com.adventnet.nms.util.NmsUtil.getAIM_ROOT()).getAbsoluteFile().getParentFile().getParentFile().getAbsolutePath();
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 7067 */                                         workingdir = new File(com.adventnet.nms.util.NmsUtil.getAIM_ROOT()).getAbsoluteFile().getParentFile().getParentFile().getAbsolutePath();
/*      */                                       }
/* 7069 */                                       StringBuffer helpcardKey = new StringBuffer();
/* 7070 */                                       if (((discoverPageUIDetails != null) && (discoverPageUIDetails.containsKey("HELP-KEY"))) || (monTitle.equals("ORACLE-DB-server")) || (monTitle.equals("WebsphereMQ")) || (monTitle.equals("SSLCertificateMonitor")) || (monTitle.equals("MSSQLDB:1433")) || (monTitle.equals("MYSQLDB:3306")) || (monTitle.equals("MYSQL-DB-server")) || (monTitle.equals("SYBASEDB:5000")) || (monTitle.equals("MSSQL-DB-server")) || (monTitle.equals("DB2-server")) || (monTitle.equals("Tomcat-server")) || (monTitle.equals("ORACLE-DB-server")) || (monTitle.equals("DB2:50000")) || (monTitle.equals("ORACLEDB:1521")) || (monTitle.equals("AIX")) || (monTitle.equals("Linux")) || (monTitle.equals("Windows")) || (monTitle.toLowerCase().indexOf("windows") != -1) || (monTitle.equals("AS400")) || (monTitle.equals("AS400/iSeries")) || (monTitle.equals("APACHE:80")) || (monTitle.equals("FreeBSD")) || (monTitle.equals("HP-UX")) || (monTitle.equals("HP-UX / Tru64")) || (monTitle.equals("Mac OS")) || (monTitle.equals("Novell")) || (monTitle.equals("Sun Solaris")) || (monTitle.equals("Tomcat:8080")) || (monTitle.equals("WEBLOGIC:7001")) || (monTitle.equals("WEBLOGIC-server")) || (monTitle.equals("WebSphere-server")) || (monTitle.equals("WEBSPHERE:9080")) || (monTitle.equals("SNMP:161")) || (monTitle.equals("SNMP")) || (monTitle.equals("JBoss:8080")) || (monTitle.equals("JDK1.5:1099")) || (monTitle.equals("JDK1.5")) || (monTitle.equals(".Net:9080")) || (monTitle.equals(".Net")) || (monTitle.equals("SUN")) || (monTitle.equals("Sun Solaris")) || (monTitle.equals("SYBASE-DB-server")) || (monTitle.equals("JBOSS-server")) || (monTitle.equals("FreeBSD / OpenBSD"))) {
/* 7071 */                                         if ((discoverPageUIDetails != null) && (discoverPageUIDetails.containsKey("HELP-KEY"))) {
/* 7072 */                                           Properties helpProps = (Properties)discoverPageUIDetails.get("HELP-KEY");
/* 7073 */                                           helpcardKey.append(helpProps.getProperty("AddProductName").equals("YES") ? FormatUtil.getString(helpProps.getProperty("Key"), new String[] { OEMUtil.getOEMString("product.name") }) : FormatUtil.getString(helpProps.getProperty("Key")));
/*      */                                         }
/* 7075 */                                         else if (monTitle.equals("WebsphereMQ"))
/*      */                                         {
/* 7077 */                                           helpcardKey.append(FormatUtil.getString("am.websphereMQ.helpcard.text", new String[] { workingdir + File.separator + "jre" + File.separator + "lib" + File.separator + "ext" }));
/*      */                                         }
/* 7079 */                                         else if (monTitle.equals("SSLCertificateMonitor"))
/*      */                                         {
/* 7081 */                                           if (EnterpriseUtil.isAdminServer()) {
/* 7082 */                                             helpcardKey.append(FormatUtil.getString("am.webclient.admin.add.monitor.sslcert.helpcard.text"));
/*      */                                           } else {
/* 7084 */                                             helpcardKey.append(FormatUtil.getString("am.sslcert.helpcard.text"));
/*      */                                           }
/*      */                                         }
/* 7087 */                                         else if ((monTitle.equals("MSSQLDB:1433")) || (monTitle.equals("MSSQL-DB-server"))) {
/* 7088 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.mssql.helpcard"));
/*      */                                         }
/* 7090 */                                         else if ((monTitle.equals("MYSQLDB:3306")) || (monTitle.equals("MYSQL-DB-server"))) {
/* 7091 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.mysql.helpcard"));
/*      */                                         }
/* 7093 */                                         else if ((monTitle.equals("SYBASEDB:5000")) || (monTitle.equals("SYBASE-DB-server"))) {
/* 7094 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.SybaseDB.helpcard"));
/*      */                                         }
/* 7096 */                                         else if ((monTitle.equals("DB2:50000")) || (monTitle.equals("DB2-server"))) {
/* 7097 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.DB2.helpcard"));
/*      */                                         }
/* 7099 */                                         else if ((monTitle.equals("ORACLEDB:1521")) || (monTitle.equals("ORACLE-DB-server"))) {
/* 7100 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.OracleDB.helpcard"));
/*      */                                         }
/* 7102 */                                         else if ((monTitle.equals("AIX")) || (monTitle.equals("Linux")) || (monTitle.equals("FreeBSD")) || (monTitle.equals("FreeBSD / OpenBSD")) || (monTitle.equals("HP-UX")) || (monTitle.equals("HP-UX / Tru64")) || (monTitle.equals("Mac OS")) || (monTitle.equals("Novell")) || (monTitle.equals("SUN")) || (monTitle.equals("Sun Solaris"))) {
/* 7103 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.Severs.helpcard"));
/*      */                                         }
/* 7105 */                                         else if (monTitle.equals("APACHE:80")) {
/* 7106 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.apache.helpcard"));
/* 7107 */                                         } else if (monTitle.toLowerCase().indexOf("windows") != -1) {
/* 7108 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.Windows.helpcard"));
/*      */                                         }
/* 7110 */                                         else if ((monTitle.equals("AS400")) || (monTitle.equals("AS400/iSeries"))) {
/* 7111 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.AS400.helpcard"));
/*      */                                         }
/* 7113 */                                         else if ((monTitle.equals("Tomcat:8080")) || (monTitle.equals("Tomcat-server"))) {
/* 7114 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.tomcat.helpcard"));
/*      */                                         }
/* 7116 */                                         else if ((monTitle.equals("WEBLOGIC:7001")) || (monTitle.equals("WEBLOGIC-server"))) {
/* 7117 */                                           String[] array = new String[6];
/* 7118 */                                           String add = null;
/*      */                                           try
/*      */                                           {
/* 7121 */                                             add = InetAddress.getLocalHost().getHostName();
/*      */                                           }
/*      */                                           catch (Exception e)
/*      */                                           {
/* 7125 */                                             System.out.println("Exception in hostdiscoveryform" + e);
/*      */                                           }
/* 7127 */                                           if (EnterpriseUtil.isAdminServer()) {
/* 7128 */                                             array[0] = ("APP_HOME_DIR" + File.separator + "<br>working" + File.separator + "classes" + File.separator + "weblogic" + File.separator);
/* 7129 */                                             helpcardKey.append(FormatUtil.getString("am.webclient.admin.add.monitor.weblogicserver.helpcard", array));
/*      */                                           } else {
/* 7131 */                                             array[0] = add;
/* 7132 */                                             array[1] = (workingdir + File.separator + "<br>working" + File.separator + "classes" + File.separator + "weblogic" + File.separator);
/* 7133 */                                             helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.weblogicserver.helpcard", array));
/*      */                                           }
/*      */                                           
/*      */                                         }
/* 7137 */                                         else if ((monTitle.equals("WEBSPHERE:9080")) || (monTitle.equals("WebSphere-server"))) {
/* 7138 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.webspheare.helpcard"));
/*      */                                         }
/* 7140 */                                         else if ((monTitle.equals("SNMP:161")) || (monTitle.equals("SNMP"))) {
/* 7141 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.SNMP.helpcard"));
/*      */                                         }
/* 7143 */                                         else if ((monTitle.equals("JBoss:8080")) || (monTitle.equals("JBOSS-server"))) {
/* 7144 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.jboss.helpcard"));
/*      */                                         }
/* 7146 */                                         else if ((monTitle.equals("JDK1.5:1099")) || (monTitle.equals("JDK1.5"))) {
/* 7147 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.jdk1.5.helpcard"));
/*      */                                         }
/* 7149 */                                         else if ((monTitle.equals(".Net:9080")) || (monTitle.equals(".Net"))) {
/* 7150 */                                           helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.DotNet.helpcard"));
/*      */                                         }
/*      */                                         
/*      */ 
/* 7154 */                                         out.write("\n                 \n");
/* 7155 */                                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(helpcardKey.toString()), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()), out, false);
/* 7156 */                                         out.write(32);
/* 7157 */                                         out.write(10);
/*      */                                       }
/* 7159 */                                       out.write("\n        ");
/* 7160 */                                       int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 7161 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 7165 */                                   if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 7166 */                                     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                                   }
/*      */                                   
/* 7169 */                                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 7170 */                                   out.write("\n        </td>\n        </tr>\n        </table>\n");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 7176 */                                   out.write("\n</tr>\n\n\n\n<TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n  <tr>\n    <td align=\"center\">\n      <input name=\"closeButton\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 7177 */                                   out.print(FormatUtil.getString("Close Window"));
/* 7178 */                                   out.write("\" onClick=\"closePopUpWindow();\">\n      ");
/* 7179 */                                   if (!isDiscoverySuccess) {
/* 7180 */                                     out.write("\n              ");
/*      */                                     
/* 7182 */                                     ResetTag _jspx_th_html_005freset_005f1 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.get(ResetTag.class);
/* 7183 */                                     _jspx_th_html_005freset_005f1.setPageContext(_jspx_page_context);
/* 7184 */                                     _jspx_th_html_005freset_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                     
/* 7186 */                                     _jspx_th_html_005freset_005f1.setStyleClass("buttons btn_back");
/*      */                                     
/* 7188 */                                     _jspx_th_html_005freset_005f1.setValue(FormatUtil.getString("am.webclient.goback.readd.txt"));
/*      */                                     
/* 7190 */                                     _jspx_th_html_005freset_005f1.setOnclick("javascript:history.back();");
/* 7191 */                                     int _jspx_eval_html_005freset_005f1 = _jspx_th_html_005freset_005f1.doStartTag();
/* 7192 */                                     if (_jspx_th_html_005freset_005f1.doEndTag() == 5) {
/* 7193 */                                       this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f1); return;
/*      */                                     }
/*      */                                     
/* 7196 */                                     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f1);
/* 7197 */                                     out.write("\n      ");
/*      */                                   }
/* 7199 */                                   out.write("\n      </td>\n      </tr>\n      </table>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 7203 */                                 out.write("\n <script src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script>\n  <script type=\"text/javascript\">\n  $(document).ready(function() {\n    $('.multi-select').chosen();\n  });\n  </script>\n    ");
/* 7204 */                                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 7205 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 7208 */                               if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 7209 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 7212 */                             if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 7213 */                               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                             }
/*      */                             
/* 7216 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 7217 */                             out.write("\n    ");
/* 7218 */                             if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                               return;
/* 7220 */                             out.write(10);
/* 7221 */                             int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 7222 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 7226 */                         if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 7227 */                           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                         }
/*      */                         else {
/* 7230 */                           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 7231 */                           out.write("\n\n\n    <script>\n        $(document.body).ready(function(){\n        ");
/* 7232 */                           if (hideFields)
/* 7233 */                             out.write("\n        document.getElementById(\"cancelButtonMod\").onclick = null;\n        $(\"#cancelButtonMod\").click(function(){ //No I18N\n            closePopUpWindow();\n        });\n            ");
/* 7234 */                           if ((!resourceid.equals("")) && (EnterpriseUtil.isIt360MSPEdition))
/*      */                           {
/*      */ 
/* 7237 */                             out.write("\n            \n            document.AMActionForm.organization.disabled = true;\n                document.AMActionForm.haid.disabled = true;\n            \n                \n        ");
/*      */                           }
/* 7239 */                           out.write("\n        });\n    </script>\n\n");
/*      */                         }
/* 7241 */                       } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 7242 */         out = _jspx_out;
/* 7243 */         if ((out != null) && (out.getBufferSize() != 0))
/* 7244 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 7245 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 7248 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7254 */     PageContext pageContext = _jspx_page_context;
/* 7255 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7257 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 7258 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 7259 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */     
/* 7261 */     _jspx_th_c_005fforEach_005f0.setItems("${dynamicSites}");
/*      */     
/* 7263 */     _jspx_th_c_005fforEach_005f0.setVar("a");
/*      */     
/* 7265 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowCounter");
/* 7266 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 7268 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 7269 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 7271 */           out.write("\n        ");
/* 7272 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 7273 */             return true;
/* 7274 */           out.write("\n        if(formCustomerId == customerId)\n        {\n            document.AMActionForm.haid.options[rowCount++] = new Option(siteName,siteId);\n        }\n    ");
/* 7275 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 7276 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 7280 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 7281 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 7284 */         int tmp195_194 = 0; int[] tmp195_192 = _jspx_push_body_count_c_005fforEach_005f0; int tmp197_196 = tmp195_192[tmp195_194];tmp195_192[tmp195_194] = (tmp197_196 - 1); if (tmp197_196 <= 0) break;
/* 7285 */         out = _jspx_page_context.popBody(); }
/* 7286 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 7288 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 7289 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 7291 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7296 */     PageContext pageContext = _jspx_page_context;
/* 7297 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7299 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 7300 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 7301 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 7303 */     _jspx_th_c_005fforEach_005f1.setItems("${a}");
/*      */     
/* 7305 */     _jspx_th_c_005fforEach_005f1.setVar("b");
/*      */     
/* 7307 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowCounter1");
/* 7308 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 7310 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 7311 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 7313 */           out.write("\n            ");
/* 7314 */           boolean bool; if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7315 */             return true;
/* 7316 */           out.write("\n            ");
/* 7317 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7318 */             return true;
/* 7319 */           out.write("\n            ");
/* 7320 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7321 */             return true;
/* 7322 */           out.write("\n        ");
/* 7323 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 7324 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 7328 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 7329 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 7332 */         int tmp282_281 = 0; int[] tmp282_279 = _jspx_push_body_count_c_005fforEach_005f1; int tmp284_283 = tmp282_279[tmp282_281];tmp282_279[tmp282_281] = (tmp284_283 - 1); if (tmp284_283 <= 0) break;
/* 7333 */         out = _jspx_page_context.popBody(); }
/* 7334 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 7336 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 7337 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 7339 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7344 */     PageContext pageContext = _jspx_page_context;
/* 7345 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7347 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7348 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 7349 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7351 */     _jspx_th_c_005fif_005f0.setTest("${rowCounter1.count == 1}");
/* 7352 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 7353 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 7355 */         out.write("\n                siteName = '");
/* 7356 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7357 */           return true;
/* 7358 */         out.write("';\n            ");
/* 7359 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 7360 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7364 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 7365 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 7366 */       return true;
/*      */     }
/* 7368 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 7369 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7374 */     PageContext pageContext = _jspx_page_context;
/* 7375 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7377 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7378 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 7379 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 7381 */     _jspx_th_c_005fout_005f0.setValue("${b}");
/* 7382 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 7383 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 7384 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 7385 */       return true;
/*      */     }
/* 7387 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 7388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7393 */     PageContext pageContext = _jspx_page_context;
/* 7394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7396 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7397 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 7398 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7400 */     _jspx_th_c_005fif_005f1.setTest("${rowCounter1.count == 2}");
/* 7401 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 7402 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 7404 */         out.write("\n                siteId = '");
/* 7405 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7406 */           return true;
/* 7407 */         out.write("';\n            ");
/* 7408 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 7409 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7413 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 7414 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 7415 */       return true;
/*      */     }
/* 7417 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 7418 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7423 */     PageContext pageContext = _jspx_page_context;
/* 7424 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7426 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7427 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 7428 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 7430 */     _jspx_th_c_005fout_005f1.setValue("${b}");
/* 7431 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 7432 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 7433 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 7434 */       return true;
/*      */     }
/* 7436 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 7437 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7442 */     PageContext pageContext = _jspx_page_context;
/* 7443 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7445 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7446 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 7447 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7449 */     _jspx_th_c_005fif_005f2.setTest("${rowCounter1.count == 3}");
/* 7450 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 7451 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 7453 */         out.write("\n                customerId = '");
/* 7454 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7455 */           return true;
/* 7456 */         out.write("';\n            ");
/* 7457 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 7458 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7462 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 7463 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 7464 */       return true;
/*      */     }
/* 7466 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 7467 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7472 */     PageContext pageContext = _jspx_page_context;
/* 7473 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7475 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7476 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 7477 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 7479 */     _jspx_th_c_005fout_005f2.setValue("${b}");
/* 7480 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 7481 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 7482 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 7483 */       return true;
/*      */     }
/* 7485 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 7486 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7491 */     PageContext pageContext = _jspx_page_context;
/* 7492 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7494 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7495 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 7496 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*      */     
/* 7498 */     _jspx_th_fmt_005fmessage_005f0.setKey("Modify");
/* 7499 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 7500 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 7501 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 7502 */       return true;
/*      */     }
/* 7504 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 7505 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7510 */     PageContext pageContext = _jspx_page_context;
/* 7511 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7513 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7514 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 7515 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/*      */     
/* 7517 */     _jspx_th_fmt_005fmessage_005f1.setKey("Cancel");
/* 7518 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 7519 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 7520 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 7521 */       return true;
/*      */     }
/* 7523 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 7524 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7529 */     PageContext pageContext = _jspx_page_context;
/* 7530 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7532 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 7533 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 7534 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 7536 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 7537 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 7538 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 7540 */         out.write("\n                alertUser();\n                return false;\n            ");
/* 7541 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 7542 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7546 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 7547 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 7548 */       return true;
/*      */     }
/* 7550 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 7551 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7556 */     PageContext pageContext = _jspx_page_context;
/* 7557 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7559 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 7560 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 7561 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/*      */     
/* 7563 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.querymonitor.counterror.text");
/* 7564 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 7565 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 7566 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 7567 */         out = _jspx_page_context.pushBody();
/* 7568 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 7569 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7572 */         if (_jspx_meth_fmt_005fparam_005f0(_jspx_th_fmt_005fmessage_005f2, _jspx_page_context))
/* 7573 */           return true;
/* 7574 */         if (_jspx_meth_fmt_005fparam_005f1(_jspx_th_fmt_005fmessage_005f2, _jspx_page_context))
/* 7575 */           return true;
/* 7576 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 7577 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7580 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 7581 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7584 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 7585 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 7586 */       return true;
/*      */     }
/* 7588 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 7589 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f0(JspTag _jspx_th_fmt_005fmessage_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7594 */     PageContext pageContext = _jspx_page_context;
/* 7595 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7597 */     ParamTag _jspx_th_fmt_005fparam_005f0 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 7598 */     _jspx_th_fmt_005fparam_005f0.setPageContext(_jspx_page_context);
/* 7599 */     _jspx_th_fmt_005fparam_005f0.setParent((Tag)_jspx_th_fmt_005fmessage_005f2);
/* 7600 */     int _jspx_eval_fmt_005fparam_005f0 = _jspx_th_fmt_005fparam_005f0.doStartTag();
/* 7601 */     if (_jspx_eval_fmt_005fparam_005f0 != 0) {
/* 7602 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 7603 */         out = _jspx_page_context.pushBody();
/* 7604 */         _jspx_th_fmt_005fparam_005f0.setBodyContent((BodyContent)out);
/* 7605 */         _jspx_th_fmt_005fparam_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7608 */         out.write("'+qry+'");
/* 7609 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f0.doAfterBody();
/* 7610 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7613 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 7614 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7617 */     if (_jspx_th_fmt_005fparam_005f0.doEndTag() == 5) {
/* 7618 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 7619 */       return true;
/*      */     }
/* 7621 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 7622 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f1(JspTag _jspx_th_fmt_005fmessage_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7627 */     PageContext pageContext = _jspx_page_context;
/* 7628 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7630 */     ParamTag _jspx_th_fmt_005fparam_005f1 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 7631 */     _jspx_th_fmt_005fparam_005f1.setPageContext(_jspx_page_context);
/* 7632 */     _jspx_th_fmt_005fparam_005f1.setParent((Tag)_jspx_th_fmt_005fmessage_005f2);
/* 7633 */     int _jspx_eval_fmt_005fparam_005f1 = _jspx_th_fmt_005fparam_005f1.doStartTag();
/* 7634 */     if (_jspx_eval_fmt_005fparam_005f1 != 0) {
/* 7635 */       if (_jspx_eval_fmt_005fparam_005f1 != 1) {
/* 7636 */         out = _jspx_page_context.pushBody();
/* 7637 */         _jspx_th_fmt_005fparam_005f1.setBodyContent((BodyContent)out);
/* 7638 */         _jspx_th_fmt_005fparam_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7641 */         out.write("http://apm.manageengine.com/form-sql-query.html");
/* 7642 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f1.doAfterBody();
/* 7643 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7646 */       if (_jspx_eval_fmt_005fparam_005f1 != 1) {
/* 7647 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7650 */     if (_jspx_th_fmt_005fparam_005f1.doEndTag() == 5) {
/* 7651 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f1);
/* 7652 */       return true;
/*      */     }
/* 7654 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f1);
/* 7655 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7660 */     PageContext pageContext = _jspx_page_context;
/* 7661 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7663 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 7664 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 7665 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 7667 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 7669 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 7670 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 7671 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 7672 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 7673 */       return true;
/*      */     }
/* 7675 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 7676 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7681 */     PageContext pageContext = _jspx_page_context;
/* 7682 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7684 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 7685 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 7686 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7688 */     _jspx_th_html_005fhidden_005f0.setProperty("method");
/*      */     
/* 7690 */     _jspx_th_html_005fhidden_005f0.setValue("createMonitor");
/* 7691 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 7692 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 7693 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 7694 */       return true;
/*      */     }
/* 7696 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 7697 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7702 */     PageContext pageContext = _jspx_page_context;
/* 7703 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7705 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7706 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 7707 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7709 */     _jspx_th_c_005fif_005f7.setTest("${!empty param.wiz ||  !empty param.fromAssociate}");
/* 7710 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 7711 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 7713 */         out.write("\n      ");
/* 7714 */         if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 7715 */           return true;
/* 7716 */         out.write("\n      ");
/* 7717 */         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 7718 */           return true;
/* 7719 */         out.write("\n      ");
/* 7720 */         if (_jspx_meth_html_005fhidden_005f2(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 7721 */           return true;
/* 7722 */         out.write("\n      ");
/* 7723 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 7724 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7728 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 7729 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 7730 */       return true;
/*      */     }
/* 7732 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 7733 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7738 */     PageContext pageContext = _jspx_page_context;
/* 7739 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7741 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 7742 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 7743 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 7744 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 7745 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 7747 */         out.write("\n        ");
/* 7748 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 7749 */           return true;
/* 7750 */         out.write("\n        ");
/* 7751 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 7752 */           return true;
/* 7753 */         out.write("\n\n        ");
/* 7754 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 7755 */           return true;
/* 7756 */         out.write("\n      ");
/* 7757 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 7758 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7762 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 7763 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 7764 */       return true;
/*      */     }
/* 7766 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 7767 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7772 */     PageContext pageContext = _jspx_page_context;
/* 7773 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7775 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 7776 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 7777 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 7779 */     _jspx_th_c_005fwhen_005f0.setTest("${param.type=='WTA'}");
/* 7780 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 7781 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 7783 */         out.write("\n          ");
/* 7784 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 7785 */           return true;
/* 7786 */         out.write("\n        ");
/* 7787 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 7788 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7792 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 7793 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 7794 */       return true;
/*      */     }
/* 7796 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 7797 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7802 */     PageContext pageContext = _jspx_page_context;
/* 7803 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7805 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7806 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 7807 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7809 */     _jspx_th_c_005fout_005f3.setValue("Web Transaction Monitor");
/* 7810 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 7811 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 7812 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 7813 */       return true;
/*      */     }
/* 7815 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 7816 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7821 */     PageContext pageContext = _jspx_page_context;
/* 7822 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7824 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 7825 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 7826 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 7828 */     _jspx_th_c_005fwhen_005f1.setTest("${param.type=='.Net'}");
/* 7829 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 7830 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 7832 */         out.write("\n          ");
/* 7833 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 7834 */           return true;
/* 7835 */         out.write("\n        ");
/* 7836 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 7837 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7841 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 7842 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 7843 */       return true;
/*      */     }
/* 7845 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 7846 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7851 */     PageContext pageContext = _jspx_page_context;
/* 7852 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7854 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7855 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 7856 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 7858 */     _jspx_th_c_005fout_005f4.setValue("Tomcat Server");
/* 7859 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 7860 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 7861 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 7862 */       return true;
/*      */     }
/* 7864 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 7865 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7870 */     PageContext pageContext = _jspx_page_context;
/* 7871 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7873 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 7874 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 7875 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 7876 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 7877 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 7879 */         out.write("\n         ");
/* 7880 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 7881 */           return true;
/* 7882 */         out.write("\n        ");
/* 7883 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 7884 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7888 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 7889 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 7890 */       return true;
/*      */     }
/* 7892 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 7893 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7898 */     PageContext pageContext = _jspx_page_context;
/* 7899 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7901 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 7902 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 7903 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 7905 */     _jspx_th_c_005fout_005f5.setValue("${param.type}");
/* 7906 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 7907 */     if (_jspx_eval_c_005fout_005f5 != 0) {
/* 7908 */       if (_jspx_eval_c_005fout_005f5 != 1) {
/* 7909 */         out = _jspx_page_context.pushBody();
/* 7910 */         _jspx_th_c_005fout_005f5.setBodyContent((BodyContent)out);
/* 7911 */         _jspx_th_c_005fout_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7914 */         out.write(45);
/* 7915 */         int evalDoAfterBody = _jspx_th_c_005fout_005f5.doAfterBody();
/* 7916 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7919 */       if (_jspx_eval_c_005fout_005f5 != 1) {
/* 7920 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7923 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 7924 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f5);
/* 7925 */       return true;
/*      */     }
/* 7927 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f5);
/* 7928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7933 */     PageContext pageContext = _jspx_page_context;
/* 7934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7936 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 7937 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 7938 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 7940 */     _jspx_th_html_005fhidden_005f1.setProperty("type");
/* 7941 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 7942 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 7943 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 7944 */       return true;
/*      */     }
/* 7946 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 7947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7952 */     PageContext pageContext = _jspx_page_context;
/* 7953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7955 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 7956 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 7957 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 7959 */     _jspx_th_html_005fhidden_005f2.setProperty("haid");
/* 7960 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 7961 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 7962 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 7963 */       return true;
/*      */     }
/* 7965 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 7966 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7971 */     PageContext pageContext = _jspx_page_context;
/* 7972 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7974 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7975 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 7976 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*      */     
/* 7978 */     _jspx_th_fmt_005fmessage_005f3.setKey("Modify Password");
/* 7979 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 7980 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 7981 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 7982 */       return true;
/*      */     }
/* 7984 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 7985 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmultibox_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7990 */     PageContext pageContext = _jspx_page_context;
/* 7991 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7993 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 7994 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 7995 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmultibox_005f0);
/*      */     
/* 7997 */     _jspx_th_bean_005fwrite_005f0.setName("userAgent");
/*      */     
/* 7999 */     _jspx_th_bean_005fwrite_005f0.setProperty("value");
/* 8000 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 8001 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 8002 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 8003 */       return true;
/*      */     }
/* 8005 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 8006 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_logic_005fiterate_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8011 */     PageContext pageContext = _jspx_page_context;
/* 8012 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8014 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 8015 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 8016 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_logic_005fiterate_005f4);
/*      */     
/* 8018 */     _jspx_th_bean_005fwrite_005f1.setName("userAgent");
/*      */     
/* 8020 */     _jspx_th_bean_005fwrite_005f1.setProperty("label");
/* 8021 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 8022 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 8023 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 8024 */       return true;
/*      */     }
/* 8026 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 8027 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_logic_005fiterate_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8032 */     PageContext pageContext = _jspx_page_context;
/* 8033 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8035 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 8036 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 8037 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_logic_005fiterate_005f4);
/*      */     
/* 8039 */     _jspx_th_c_005fif_005f9.setTest("${((j+1)%4) == 0}");
/* 8040 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 8041 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 8043 */         out.write("\n\t\t\t\t\t\t\t</tr><tr height=\"5px\"><td colspan=\"3\"><img src=\"/images/spacer.gif\"></img></tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t");
/* 8044 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 8045 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8049 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 8050 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 8051 */       return true;
/*      */     }
/* 8053 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 8054 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8059 */     PageContext pageContext = _jspx_page_context;
/* 8060 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8062 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 8063 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 8064 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 8066 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 8068 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 8069 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 8070 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 8071 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 8072 */       return true;
/*      */     }
/* 8074 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 8075 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\newConfType_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */