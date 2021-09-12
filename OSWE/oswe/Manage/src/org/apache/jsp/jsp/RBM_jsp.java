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
/*      */ import com.adventnet.appmanager.util.CustomExpressionUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
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
/*      */ import javax.servlet.jsp.SkipPageException;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.ButtonTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.MultiboxTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
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
/*      */ 
/*      */ public final class RBM_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   79 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   82 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   83 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   84 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   91 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   96 */     ArrayList list = null;
/*   97 */     StringBuffer sbf = new StringBuffer();
/*   98 */     ManagedApplication mo = new ManagedApplication();
/*   99 */     if (distinct)
/*      */     {
/*  101 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*  105 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*  108 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*  110 */       ArrayList row = (ArrayList)list.get(i);
/*  111 */       sbf.append("<option value='" + row.get(0) + "'>");
/*  112 */       if (distinct) {
/*  113 */         sbf.append(row.get(0));
/*      */       } else
/*  115 */         sbf.append(row.get(1));
/*  116 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  119 */     return sbf.toString(); }
/*      */   
/*  121 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  124 */     if (severity == null)
/*      */     {
/*  126 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  128 */     if (severity.equals("5"))
/*      */     {
/*  130 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  132 */     if (severity.equals("1"))
/*      */     {
/*  134 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  139 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  146 */     if (severity == null)
/*      */     {
/*  148 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  150 */     if (severity.equals("1"))
/*      */     {
/*  152 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  154 */     if (severity.equals("4"))
/*      */     {
/*  156 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  158 */     if (severity.equals("5"))
/*      */     {
/*  160 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  165 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  171 */     if (severity == null)
/*      */     {
/*  173 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  175 */     if (severity.equals("5"))
/*      */     {
/*  177 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  179 */     if (severity.equals("1"))
/*      */     {
/*  181 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  185 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  191 */     if (severity == null)
/*      */     {
/*  193 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  195 */     if (severity.equals("1"))
/*      */     {
/*  197 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  199 */     if (severity.equals("4"))
/*      */     {
/*  201 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  203 */     if (severity.equals("5"))
/*      */     {
/*  205 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  209 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  215 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  221 */     if (severity == 5)
/*      */     {
/*  223 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  225 */     if (severity == 1)
/*      */     {
/*  227 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  232 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  238 */     if (severity == null)
/*      */     {
/*  240 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  242 */     if (severity.equals("5"))
/*      */     {
/*  244 */       if (isAvailability) {
/*  245 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  248 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  251 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  253 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  255 */     if (severity.equals("1"))
/*      */     {
/*  257 */       if (isAvailability) {
/*  258 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  261 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  268 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  275 */     if (severity == null)
/*      */     {
/*  277 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  279 */     if (severity.equals("5"))
/*      */     {
/*  281 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  283 */     if (severity.equals("4"))
/*      */     {
/*  285 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  287 */     if (severity.equals("1"))
/*      */     {
/*  289 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  294 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  300 */     if (severity == null)
/*      */     {
/*  302 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  304 */     if (severity.equals("5"))
/*      */     {
/*  306 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  308 */     if (severity.equals("4"))
/*      */     {
/*  310 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  312 */     if (severity.equals("1"))
/*      */     {
/*  314 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  319 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  326 */     if (severity == null)
/*      */     {
/*  328 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  330 */     if (severity.equals("5"))
/*      */     {
/*  332 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  334 */     if (severity.equals("4"))
/*      */     {
/*  336 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  338 */     if (severity.equals("1"))
/*      */     {
/*  340 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  345 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  353 */     StringBuffer out = new StringBuffer();
/*  354 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  355 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  356 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  357 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  358 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  359 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  360 */     out.append("</tr>");
/*  361 */     out.append("</form></table>");
/*  362 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  369 */     if (val == null)
/*      */     {
/*  371 */       return "-";
/*      */     }
/*      */     
/*  374 */     String ret = FormatUtil.formatNumber(val);
/*  375 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  376 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  379 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  383 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  391 */     StringBuffer out = new StringBuffer();
/*  392 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  393 */     out.append("<tr>");
/*  394 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  396 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  398 */     out.append("</tr>");
/*  399 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  403 */       if (j % 2 == 0)
/*      */       {
/*  405 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  409 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  412 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  414 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  417 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  421 */       out.append("</tr>");
/*      */     }
/*  423 */     out.append("</table>");
/*  424 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  425 */     out.append("<tr>");
/*  426 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  427 */     out.append("</tr>");
/*  428 */     out.append("</table>");
/*  429 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  435 */     StringBuffer out = new StringBuffer();
/*  436 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  437 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  438 */     out.append("<tr>");
/*  439 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  440 */     out.append("<tr>");
/*  441 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  442 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  443 */     out.append("</tr>");
/*  444 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  447 */       out.append("<tr>");
/*  448 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  449 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  450 */       out.append("</tr>");
/*      */     }
/*      */     
/*  453 */     out.append("</table>");
/*  454 */     out.append("</table>");
/*  455 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  460 */     if (severity.equals("0"))
/*      */     {
/*  462 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  466 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  473 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  486 */     StringBuffer out = new StringBuffer();
/*  487 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  488 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  490 */       out.append("<tr>");
/*  491 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  492 */       out.append("</tr>");
/*      */       
/*      */ 
/*  495 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  497 */         String borderclass = "";
/*      */         
/*      */ 
/*  500 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  502 */         out.append("<tr>");
/*      */         
/*  504 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  505 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  506 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  512 */     out.append("</table><br>");
/*  513 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  514 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  516 */       List sLinks = secondLevelOfLinks[0];
/*  517 */       List sText = secondLevelOfLinks[1];
/*  518 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  521 */         out.append("<tr>");
/*  522 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  523 */         out.append("</tr>");
/*  524 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  526 */           String borderclass = "";
/*      */           
/*      */ 
/*  529 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  531 */           out.append("<tr>");
/*      */           
/*  533 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  534 */           if (sLinks.get(i).toString().length() == 0) {
/*  535 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  538 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  540 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  544 */     out.append("</table>");
/*  545 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  552 */     StringBuffer out = new StringBuffer();
/*  553 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  554 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  556 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  558 */         out.append("<tr>");
/*  559 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  560 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  564 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  566 */           String borderclass = "";
/*      */           
/*      */ 
/*  569 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  571 */           out.append("<tr>");
/*      */           
/*  573 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  574 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  575 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  578 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  581 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  586 */     out.append("</table><br>");
/*  587 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  588 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  590 */       List sLinks = secondLevelOfLinks[0];
/*  591 */       List sText = secondLevelOfLinks[1];
/*  592 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  595 */         out.append("<tr>");
/*  596 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  597 */         out.append("</tr>");
/*  598 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  600 */           String borderclass = "";
/*      */           
/*      */ 
/*  603 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  605 */           out.append("<tr>");
/*      */           
/*  607 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  608 */           if (sLinks.get(i).toString().length() == 0) {
/*  609 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  612 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  614 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  618 */     out.append("</table>");
/*  619 */     return out.toString();
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
/*  632 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  635 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  638 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  641 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  644 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  647 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  650 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  653 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  661 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  666 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  671 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  676 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  681 */     if (val != null)
/*      */     {
/*  683 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  687 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  692 */     if (val == null) {
/*  693 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  697 */       val = new SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  702 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  708 */     if (val != null)
/*      */     {
/*  710 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  714 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  720 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  725 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  729 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  734 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  739 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  744 */     String hostaddress = "";
/*  745 */     String ip = request.getHeader("x-forwarded-for");
/*  746 */     if (ip == null)
/*  747 */       ip = request.getRemoteAddr();
/*  748 */     InetAddress add = null;
/*  749 */     if (ip.equals("127.0.0.1")) {
/*  750 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  754 */       add = InetAddress.getByName(ip);
/*      */     }
/*  756 */     hostaddress = add.getHostName();
/*  757 */     if (hostaddress.indexOf('.') != -1) {
/*  758 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  759 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  763 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  768 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  774 */     if (severity == null)
/*      */     {
/*  776 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  778 */     if (severity.equals("5"))
/*      */     {
/*  780 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  782 */     if (severity.equals("1"))
/*      */     {
/*  784 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  789 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  794 */     ResultSet set = null;
/*  795 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  796 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  798 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  799 */       if (set.next()) { String str1;
/*  800 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  801 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  804 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  809 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  812 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  814 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  818 */     StringBuffer rca = new StringBuffer();
/*  819 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  820 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  823 */     int rcalength = key.length();
/*  824 */     String split = "6. ";
/*  825 */     int splitPresent = key.indexOf(split);
/*  826 */     String div1 = "";String div2 = "";
/*  827 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  829 */       if (rcalength > 180) {
/*  830 */         rca.append("<span class=\"rca-critical-text\">");
/*  831 */         getRCATrimmedText(key, rca);
/*  832 */         rca.append("</span>");
/*      */       } else {
/*  834 */         rca.append("<span class=\"rca-critical-text\">");
/*  835 */         rca.append(key);
/*  836 */         rca.append("</span>");
/*      */       }
/*  838 */       return rca.toString();
/*      */     }
/*  840 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  841 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  842 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  843 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  844 */     getRCATrimmedText(div1, rca);
/*  845 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  848 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  849 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  850 */     getRCATrimmedText(div2, rca);
/*  851 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  853 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  858 */     String[] st = msg.split("<br>");
/*  859 */     for (int i = 0; i < st.length; i++) {
/*  860 */       String s = st[i];
/*  861 */       if (s.length() > 180) {
/*  862 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  864 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  868 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  869 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  871 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  875 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  876 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  877 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  880 */       if (key == null) {
/*  881 */         return ret;
/*      */       }
/*      */       
/*  884 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  885 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  888 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  889 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  890 */       set = AMConnectionPool.executeQueryStmt(query);
/*  891 */       if (set.next())
/*      */       {
/*  893 */         String helpLink = set.getString("LINK");
/*  894 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  897 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  903 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  922 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  913 */         if (set != null) {
/*  914 */           AMConnectionPool.closeStatement(set);
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
/*  928 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  929 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  931 */       String entityStr = (String)keys.nextElement();
/*  932 */       String mmessage = temp.getProperty(entityStr);
/*  933 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  934 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  936 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  942 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  943 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  945 */       String entityStr = (String)keys.nextElement();
/*  946 */       String mmessage = temp.getProperty(entityStr);
/*  947 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  948 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  950 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  955 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  965 */     String des = new String();
/*  966 */     while (str.indexOf(find) != -1) {
/*  967 */       des = des + str.substring(0, str.indexOf(find));
/*  968 */       des = des + replace;
/*  969 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  971 */     des = des + str;
/*  972 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  979 */       if (alert == null)
/*      */       {
/*  981 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  983 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  985 */         return "&nbsp;";
/*      */       }
/*      */       
/*  988 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  990 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  993 */       int rcalength = test.length();
/*  994 */       if (rcalength < 300)
/*      */       {
/*  996 */         return test;
/*      */       }
/*      */       
/*      */ 
/* 1000 */       StringBuffer out = new StringBuffer();
/* 1001 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/* 1002 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/* 1003 */       out.append("</div>");
/* 1004 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/* 1005 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/* 1006 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1011 */       ex.printStackTrace();
/*      */     }
/* 1013 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1019 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1024 */     ArrayList attribIDs = new ArrayList();
/* 1025 */     ArrayList resIDs = new ArrayList();
/* 1026 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1028 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1030 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1032 */       String resourceid = "";
/* 1033 */       String resourceType = "";
/* 1034 */       if (type == 2) {
/* 1035 */         resourceid = (String)row.get(0);
/* 1036 */         resourceType = (String)row.get(3);
/*      */       }
/* 1038 */       else if (type == 3) {
/* 1039 */         resourceid = (String)row.get(0);
/* 1040 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1043 */         resourceid = (String)row.get(6);
/* 1044 */         resourceType = (String)row.get(7);
/*      */       }
/* 1046 */       resIDs.add(resourceid);
/* 1047 */       String healthid = AMAttributesCache.getHealthId(resourceType);
/* 1048 */       String availid = AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1050 */       String healthentity = null;
/* 1051 */       String availentity = null;
/* 1052 */       if (healthid != null) {
/* 1053 */         healthentity = resourceid + "_" + healthid;
/* 1054 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1057 */       if (availid != null) {
/* 1058 */         availentity = resourceid + "_" + availid;
/* 1059 */         entitylist.add(availentity);
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
/* 1073 */     Properties alert = getStatus(entitylist);
/* 1074 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1079 */     int size = monitorList.size();
/*      */     
/* 1081 */     String[] severity = new String[size];
/*      */     
/* 1083 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1085 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1086 */       String resourceName1 = (String)row1.get(7);
/* 1087 */       String resourceid1 = (String)row1.get(6);
/* 1088 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1089 */       if (severity[j] == null)
/*      */       {
/* 1091 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1095 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1097 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1099 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1102 */         if (sev > 0) {
/* 1103 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1104 */           monitorList.set(k, monitorList.get(j));
/* 1105 */           monitorList.set(j, t);
/* 1106 */           String temp = severity[k];
/* 1107 */           severity[k] = severity[j];
/* 1108 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1114 */     int z = 0;
/* 1115 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1118 */       int i = 0;
/* 1119 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1122 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1126 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1130 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1132 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1135 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1139 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1142 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1143 */       String resourceName1 = (String)row1.get(7);
/* 1144 */       String resourceid1 = (String)row1.get(6);
/* 1145 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1146 */       if (hseverity[j] == null)
/*      */       {
/* 1148 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1153 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1155 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1158 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1161 */         if (hsev > 0) {
/* 1162 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1163 */           monitorList.set(k, monitorList.get(j));
/* 1164 */           monitorList.set(j, t);
/* 1165 */           String temp1 = hseverity[k];
/* 1166 */           hseverity[k] = hseverity[j];
/* 1167 */           hseverity[j] = temp1;
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
/* 1179 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1180 */     boolean forInventory = false;
/* 1181 */     String trdisplay = "none";
/* 1182 */     String plusstyle = "inline";
/* 1183 */     String minusstyle = "none";
/* 1184 */     String haidTopLevel = "";
/* 1185 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1187 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1189 */         haidTopLevel = request.getParameter("haid");
/* 1190 */         forInventory = true;
/* 1191 */         trdisplay = "table-row;";
/* 1192 */         plusstyle = "none";
/* 1193 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1200 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1203 */     ArrayList listtoreturn = new ArrayList();
/* 1204 */     StringBuffer toreturn = new StringBuffer();
/* 1205 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1206 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1207 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1209 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1211 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1212 */       String childresid = (String)singlerow.get(0);
/* 1213 */       String childresname = (String)singlerow.get(1);
/* 1214 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1215 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1216 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1217 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1218 */       String unmanagestatus = (String)singlerow.get(5);
/* 1219 */       String actionstatus = (String)singlerow.get(6);
/* 1220 */       String linkclass = "monitorgp-links";
/* 1221 */       String titleforres = childresname;
/* 1222 */       String titilechildresname = childresname;
/* 1223 */       String childimg = "/images/trcont.png";
/* 1224 */       String flag = "enable";
/* 1225 */       String dcstarted = (String)singlerow.get(8);
/* 1226 */       String configMonitor = "";
/* 1227 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1228 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1230 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1232 */       if (singlerow.get(7) != null)
/*      */       {
/* 1234 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1236 */       String haiGroupType = "0";
/* 1237 */       if ("HAI".equals(childtype))
/*      */       {
/* 1239 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1241 */       childimg = "/images/trend.png";
/* 1242 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1243 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1244 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1246 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1248 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1250 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1251 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1254 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1256 */         linkclass = "disabledtext";
/* 1257 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1259 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1260 */       String availmouseover = "";
/* 1261 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1263 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1265 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1266 */       String healthmouseover = "";
/* 1267 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1269 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1272 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1273 */       int spacing = 0;
/* 1274 */       if (level >= 1)
/*      */       {
/* 1276 */         spacing = 40 * level;
/*      */       }
/* 1278 */       if (childtype.equals("HAI"))
/*      */       {
/* 1280 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1281 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1282 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1284 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1285 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1286 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1287 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1288 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1289 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1290 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1291 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1292 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1293 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1294 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1296 */         if (!forInventory)
/*      */         {
/* 1298 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1301 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1303 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1305 */           actions = editlink + actions;
/*      */         }
/* 1307 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1309 */           actions = actions + associatelink;
/*      */         }
/* 1311 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1312 */         String arrowimg = "";
/* 1313 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1315 */           actions = "";
/* 1316 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1317 */           checkbox = "";
/* 1318 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1320 */         if (isIt360)
/*      */         {
/* 1322 */           actionimg = "";
/* 1323 */           actions = "";
/* 1324 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1325 */           checkbox = "";
/*      */         }
/*      */         
/* 1328 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1330 */           actions = "";
/*      */         }
/* 1332 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1334 */           checkbox = "";
/*      */         }
/*      */         
/* 1337 */         String resourcelink = "";
/*      */         
/* 1339 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1341 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1345 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1348 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1349 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1350 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1351 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1352 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1353 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1354 */         if (!isIt360)
/*      */         {
/* 1356 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1360 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1363 */         toreturn.append("</tr>");
/* 1364 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1366 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1367 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1371 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1372 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1375 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1379 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1381 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1382 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1383 */             toreturn.append(assocMessage);
/* 1384 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1385 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1386 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1387 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1393 */         String resourcelink = null;
/* 1394 */         boolean hideEditLink = false;
/* 1395 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1397 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1398 */           hideEditLink = true;
/* 1399 */           if (isIt360)
/*      */           {
/* 1401 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1405 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1407 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1409 */           hideEditLink = true;
/* 1410 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1411 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1416 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1419 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1420 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1421 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1422 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1423 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1424 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1425 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1426 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1427 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1428 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1429 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1430 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1431 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1433 */         if (hideEditLink)
/*      */         {
/* 1435 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1437 */         if (!forInventory)
/*      */         {
/* 1439 */           removefromgroup = "";
/*      */         }
/* 1441 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1442 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1443 */           actions = actions + configcustomfields;
/*      */         }
/* 1445 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1447 */           actions = editlink + actions;
/*      */         }
/* 1449 */         String managedLink = "";
/* 1450 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1452 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1453 */           actions = "";
/* 1454 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1455 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1458 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1460 */           checkbox = "";
/*      */         }
/*      */         
/* 1463 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1465 */           actions = "";
/*      */         }
/* 1467 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1468 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1469 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1470 */         if (isIt360)
/*      */         {
/* 1472 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1476 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1478 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1479 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1480 */         if (!isIt360)
/*      */         {
/* 1482 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1486 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1488 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1491 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1498 */       StringBuilder toreturn = new StringBuilder();
/* 1499 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1500 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1501 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1502 */       String title = "";
/* 1503 */       message = EnterpriseUtil.decodeString(message);
/* 1504 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1505 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1506 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1508 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1510 */       else if ("5".equals(severity))
/*      */       {
/* 1512 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1516 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1518 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1519 */       toreturn.append(v);
/*      */       
/* 1521 */       toreturn.append(link);
/* 1522 */       if (severity == null)
/*      */       {
/* 1524 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1526 */       else if (severity.equals("5"))
/*      */       {
/* 1528 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1530 */       else if (severity.equals("4"))
/*      */       {
/* 1532 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1534 */       else if (severity.equals("1"))
/*      */       {
/* 1536 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1541 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1543 */       toreturn.append("</a>");
/* 1544 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1548 */       ex.printStackTrace();
/*      */     }
/* 1550 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1557 */       StringBuilder toreturn = new StringBuilder();
/* 1558 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1559 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1560 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1561 */       if (message == null)
/*      */       {
/* 1563 */         message = "";
/*      */       }
/*      */       
/* 1566 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1567 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1569 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1570 */       toreturn.append(v);
/*      */       
/* 1572 */       toreturn.append(link);
/*      */       
/* 1574 */       if (severity == null)
/*      */       {
/* 1576 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1578 */       else if (severity.equals("5"))
/*      */       {
/* 1580 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1582 */       else if (severity.equals("1"))
/*      */       {
/* 1584 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1589 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1591 */       toreturn.append("</a>");
/* 1592 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1598 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1601 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1602 */     if (invokeActions != null) {
/* 1603 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1604 */       while (iterator.hasNext()) {
/* 1605 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1606 */         if (actionmap.containsKey(actionid)) {
/* 1607 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1612 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1616 */     String actionLink = "";
/* 1617 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1618 */     String query = "";
/* 1619 */     ResultSet rs = null;
/* 1620 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1621 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1622 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1623 */       actionLink = "method=" + methodName;
/*      */     }
/* 1625 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1626 */       actionLink = methodName;
/*      */     }
/* 1628 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1629 */     Iterator itr = methodarglist.iterator();
/* 1630 */     boolean isfirstparam = true;
/* 1631 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1632 */     while (itr.hasNext()) {
/* 1633 */       HashMap argmap = (HashMap)itr.next();
/* 1634 */       String argtype = (String)argmap.get("TYPE");
/* 1635 */       String argname = (String)argmap.get("IDENTITY");
/* 1636 */       String paramname = (String)argmap.get("PARAMETER");
/* 1637 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1638 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1639 */         isfirstparam = false;
/* 1640 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1642 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1646 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1650 */         actionLink = actionLink + "&";
/*      */       }
/* 1652 */       String paramValue = null;
/* 1653 */       String tempargname = argname;
/* 1654 */       if (commonValues.getProperty(tempargname) != null) {
/* 1655 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1658 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1659 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1660 */           if (dbType.equals("mysql")) {
/* 1661 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1664 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1666 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1668 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1669 */             if (rs.next()) {
/* 1670 */               paramValue = rs.getString("VALUE");
/* 1671 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1675 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1679 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1682 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1687 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1688 */           paramValue = rowId;
/*      */         }
/* 1690 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1691 */           paramValue = managedObjectName;
/*      */         }
/* 1693 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1694 */           paramValue = resID;
/*      */         }
/* 1696 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1697 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1700 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1702 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1703 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1704 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1706 */     return actionLink;
/*      */   }
/*      */   
/* 1709 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1710 */     String dependentAttribute = null;
/* 1711 */     String align = "left";
/*      */     
/* 1713 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1714 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1715 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1716 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1717 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1718 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1719 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1720 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1721 */       align = "center";
/*      */     }
/*      */     
/* 1724 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1725 */     String actualdata = "";
/*      */     
/* 1727 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1728 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1729 */         actualdata = availValue;
/*      */       }
/* 1731 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1732 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1736 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1737 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1740 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1746 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1747 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1748 */       toreturn.append("<table>");
/* 1749 */       toreturn.append("<tr>");
/* 1750 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1751 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1752 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1753 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1754 */         String toolTip = "";
/* 1755 */         String hideClass = "";
/* 1756 */         String textStyle = "";
/* 1757 */         boolean isreferenced = true;
/* 1758 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1759 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1760 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1761 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1763 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1764 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1765 */           while (valueList.hasMoreTokens()) {
/* 1766 */             String dependentVal = valueList.nextToken();
/* 1767 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1768 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1769 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1771 */               toolTip = "";
/* 1772 */               hideClass = "";
/* 1773 */               isreferenced = false;
/* 1774 */               textStyle = "disabledtext";
/* 1775 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1779 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1780 */           toolTip = "";
/* 1781 */           hideClass = "";
/* 1782 */           isreferenced = false;
/* 1783 */           textStyle = "disabledtext";
/* 1784 */           if (dependentImageMap != null) {
/* 1785 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1786 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1789 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1793 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1794 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1795 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1796 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1797 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1798 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1800 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1801 */           if (isreferenced) {
/* 1802 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1806 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1807 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1808 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1809 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1810 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1811 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1813 */           toreturn.append("</span>");
/* 1814 */           toreturn.append("</a>");
/* 1815 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1818 */       toreturn.append("</tr>");
/* 1819 */       toreturn.append("</table>");
/* 1820 */       toreturn.append("</td>");
/*      */     } else {
/* 1822 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1825 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1829 */     String colTime = null;
/* 1830 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1831 */     if ((rows != null) && (rows.size() > 0)) {
/* 1832 */       Iterator<String> itr = rows.iterator();
/* 1833 */       String maxColQuery = "";
/* 1834 */       for (;;) { if (itr.hasNext()) {
/* 1835 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1836 */           ResultSet maxCol = null;
/*      */           try {
/* 1838 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1839 */             while (maxCol.next()) {
/* 1840 */               if (colTime == null) {
/* 1841 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1844 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1853 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1855 */               if (maxCol != null)
/* 1856 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1858 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1853 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1855 */               if (maxCol != null)
/* 1856 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1858 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1863 */     return colTime;
/*      */   }
/*      */   
/* 1866 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1867 */     tablename = null;
/* 1868 */     ResultSet rsTable = null;
/* 1869 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1871 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1872 */       while (rsTable.next()) {
/* 1873 */         tablename = rsTable.getString("DATATABLE");
/* 1874 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1875 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1888 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1879 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1882 */         if (rsTable != null)
/* 1883 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1885 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1891 */     String argsList = "";
/* 1892 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1894 */       if (showArgsMap.get(row) != null) {
/* 1895 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1896 */         if (showArgslist != null) {
/* 1897 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1898 */             if (argsList.trim().equals("")) {
/* 1899 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1902 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1909 */       e.printStackTrace();
/* 1910 */       return "";
/*      */     }
/* 1912 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1917 */     String argsList = "";
/* 1918 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1921 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1923 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1924 */         if (hideArgsList != null)
/*      */         {
/* 1926 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1928 */             if (argsList.trim().equals(""))
/*      */             {
/* 1930 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1934 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1942 */       ex.printStackTrace();
/*      */     }
/* 1944 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1948 */     StringBuilder toreturn = new StringBuilder();
/* 1949 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1956 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1957 */       Iterator itr = tActionList.iterator();
/* 1958 */       while (itr.hasNext()) {
/* 1959 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1960 */         String confirmmsg = "";
/* 1961 */         String link = "";
/* 1962 */         String isJSP = "NO";
/* 1963 */         HashMap tactionMap = (HashMap)itr.next();
/* 1964 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1965 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1966 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1967 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1968 */           (actionmap.containsKey(actionId))) {
/* 1969 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1970 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1971 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1972 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1973 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1975 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1981 */           if (isTableAction) {
/* 1982 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1985 */             tableName = "Link";
/* 1986 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1987 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1988 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1989 */             toreturn.append("</a></td>");
/*      */           }
/* 1991 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1992 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1993 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1994 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2000 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 2006 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 2008 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 2009 */       Properties prop = (Properties)node.getUserObject();
/* 2010 */       String mgID = prop.getProperty("label");
/* 2011 */       String mgName = prop.getProperty("value");
/* 2012 */       String isParent = prop.getProperty("isParent");
/* 2013 */       int mgIDint = Integer.parseInt(mgID);
/* 2014 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 2016 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2018 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2019 */       if (node.getChildCount() > 0)
/*      */       {
/* 2021 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2023 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2025 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2027 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2031 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2036 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2038 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2040 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2042 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2046 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2049 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2050 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2052 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2056 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2058 */       if (node.getChildCount() > 0)
/*      */       {
/* 2060 */         builder.append("<UL>");
/* 2061 */         printMGTree(node, builder);
/* 2062 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2067 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2068 */     StringBuffer toReturn = new StringBuffer();
/* 2069 */     String table = "-";
/*      */     try {
/* 2071 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2072 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2073 */       float total = 0.0F;
/* 2074 */       while (it.hasNext()) {
/* 2075 */         String attName = (String)it.next();
/* 2076 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2077 */         boolean roundOffData = false;
/* 2078 */         if ((data != null) && (!data.equals(""))) {
/* 2079 */           if (data.indexOf(",") != -1) {
/* 2080 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2083 */             float value = Float.parseFloat(data);
/* 2084 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2087 */             total += value;
/* 2088 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2091 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2096 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2097 */       while (attVsWidthList.hasNext()) {
/* 2098 */         String attName = (String)attVsWidthList.next();
/* 2099 */         String data = (String)attVsWidthProps.get(attName);
/* 2100 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2101 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2102 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2103 */         String className = (String)graphDetails.get("ClassName");
/* 2104 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2105 */         if (percentage < 1.0F)
/*      */         {
/* 2107 */           data = percentage + "";
/*      */         }
/* 2109 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2111 */       if (toReturn.length() > 0) {
/* 2112 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2116 */       e.printStackTrace();
/*      */     }
/* 2118 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2124 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2125 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2126 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2127 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2128 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2129 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2130 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2131 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2132 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2135 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2136 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2137 */       splitvalues[0] = multiplecondition.toString();
/* 2138 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2141 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2146 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2147 */     if (thresholdType != 3) {
/* 2148 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2149 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2150 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2151 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2152 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2153 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2155 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2156 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2157 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2158 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2159 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2160 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2162 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2163 */     if (updateSelected != null) {
/* 2164 */       updateSelected[0] = "selected";
/*      */     }
/* 2166 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2171 */       StringBuffer toreturn = new StringBuffer("");
/* 2172 */       if (commaSeparatedMsgId != null) {
/* 2173 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2174 */         int count = 0;
/* 2175 */         while (msgids.hasMoreTokens()) {
/* 2176 */           String id = msgids.nextToken();
/* 2177 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2178 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2179 */           count++;
/* 2180 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2181 */             if (toreturn.length() == 0) {
/* 2182 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2184 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2185 */             if (!image.trim().equals("")) {
/* 2186 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2188 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2189 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2192 */         if (toreturn.length() > 0) {
/* 2193 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2197 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2200 */       e.printStackTrace(); }
/* 2201 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2207 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2213 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2214 */   static { _jspx_dependants.put("/jsp/includes/newresourcetypes.jspf", Long.valueOf(1473429417000L));
/* 2215 */     _jspx_dependants.put("/jsp/includes/agentLocations.jspf", Long.valueOf(1473429417000L));
/* 2216 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fsize_005fproperty_005fmultiple;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2252 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2256 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2266 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2267 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2268 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2269 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2270 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2271 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2272 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2273 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2274 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fsize_005fproperty_005fmultiple = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2275 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2276 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2277 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2278 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2279 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2280 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2281 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2282 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2283 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2284 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2285 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2289 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2290 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2291 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2292 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2293 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2294 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2295 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2296 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/* 2297 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2298 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2299 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2300 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2301 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2302 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/* 2303 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2304 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2305 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/* 2306 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.release();
/* 2307 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fsize_005fproperty_005fmultiple.release();
/* 2308 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2309 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2310 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2311 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick.release();
/* 2312 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.release();
/* 2313 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled.release();
/* 2314 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
/* 2315 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/* 2316 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2323 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2326 */     JspWriter out = null;
/* 2327 */     Object page = this;
/* 2328 */     JspWriter _jspx_out = null;
/* 2329 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2333 */       response.setContentType("text/html;charset=UTF-8");
/* 2334 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2336 */       _jspx_page_context = pageContext;
/* 2337 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2338 */       ServletConfig config = pageContext.getServletConfig();
/* 2339 */       session = pageContext.getSession();
/* 2340 */       out = pageContext.getOut();
/* 2341 */       _jspx_out = out;
/*      */       
/* 2343 */       out.write("<!DOCTYPE html>\n");
/* 2344 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\t\n");
/* 2345 */       out.write(10);
/*      */       
/* 2347 */       request.setAttribute("HelpKey", "RBM Monitor Details");
/*      */       
/* 2349 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/* 2350 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2352 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2353 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2354 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2356 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2358 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2360 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2362 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2363 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2364 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2365 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2368 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2369 */         String available = null;
/* 2370 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2371 */         out.write(10);
/*      */         
/* 2373 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2374 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2375 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2377 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2379 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2381 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2383 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2384 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2385 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2386 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2389 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2390 */           String unavailable = null;
/* 2391 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2392 */           out.write(10);
/*      */           
/* 2394 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2395 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2396 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2398 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2400 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2402 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2404 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2405 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2406 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2407 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2410 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2411 */             String unmanaged = null;
/* 2412 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2413 */             out.write(10);
/*      */             
/* 2415 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2416 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2417 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2419 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2421 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2423 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2425 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2426 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2427 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2428 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2431 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2432 */               String scheduled = null;
/* 2433 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2434 */               out.write(10);
/*      */               
/* 2436 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2437 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2438 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2440 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2442 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2444 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2446 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2447 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2448 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2449 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2452 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2453 */                 String critical = null;
/* 2454 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2455 */                 out.write(10);
/*      */                 
/* 2457 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2458 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2459 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2461 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2463 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2465 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2467 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2468 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2469 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2470 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2473 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2474 */                   String clear = null;
/* 2475 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2476 */                   out.write(10);
/*      */                   
/* 2478 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2479 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2480 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2482 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2484 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2486 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2488 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2489 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2490 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2491 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2494 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2495 */                     String warning = null;
/* 2496 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2497 */                     out.write(10);
/* 2498 */                     out.write(10);
/*      */                     
/* 2500 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2501 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2503 */                     out.write(10);
/* 2504 */                     out.write(10);
/* 2505 */                     out.write(10);
/* 2506 */                     out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2507 */                     ManagedApplication mo = null;
/* 2508 */                     mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2509 */                     if (mo == null) {
/* 2510 */                       mo = new ManagedApplication();
/* 2511 */                       _jspx_page_context.setAttribute("mo", mo, 1);
/*      */                     }
/* 2513 */                     out.write("\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/hostdiscoveryform.js\"></SCRIPT>\n\n");
/* 2514 */                     JspRuntimeLibrary.include(request, response, "/jsp/includes/monitorGroups.jsp", out, false);
/* 2515 */                     out.write(10);
/*      */                     
/* 2517 */                     request.setAttribute("isRBM", "true");
/* 2518 */                     String fromWhere = "RBM";
/*      */                     
/* 2520 */                     out.write("\n\n\n<script>\n\nfunction loadSite()\n{\n\tdocument.RbmForm.haid.options.length=0;\n\tvar formCustomerId = document.RbmForm.organization.value;\n\tvar siteName;\n\tvar siteId;\n\tvar customerId;\n\tvar rowCount=0;\n\tdocument.RbmForm.haid.options[rowCount++] = new Option('-Select Site-','-'); //No I18N\n\t");
/* 2521 */                     if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*      */                       return;
/* 2523 */                     out.write("\n}\n\nfunction resetname(name)\n{\n\tif(name='groupname')\n\t{\n\t\tdocument.RbmForm.groupname.value='';\n\t}\n\tif(name='subgroupname')\n\t{\n\t\tdocument.RbmForm.subgroupname.value='';\n\t}\n}\nfunction createGroup()\n{\n\tif(document.RbmForm.groupname.value=='')\n\t{\n\t\talert(\"");
/* 2524 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2525 */                     out.write("\");\n\t\thideDiv('group');\n\t\tshowDiv('creategroup');\n\t\tdocument.RbmForm.groupname.focus();\n\t\treturn false;\n\t}\n\telse\n\t{\n\t\thideDiv('creategroup');\n\t\tvar a=document.RbmForm.groupname.value;\n\t\thttp= getHTTPObject();\n\t\turl=\"/adminAction.do?method=createMonitorGroup&groupname=\"+encodeURIComponent(a);\t//NO I18N\n\t\thttp.open(\"GET\",url,true);\n\t\thttp.onreadystatechange = getActionTypes;\n\t\thttp.send(null);\n\t\tshowDiv('group');\n\t}\n\n}  \nfunction check()\n{\n\thideDiv(\"creategroup\");\n\thideDiv(\"createsubgroup\");\n\thideDiv(\"groupmessage\");\n\tvar flag='");
/* 2526 */                     out.print(com.adventnet.appmanager.util.Constants.subGroupsEnabled);
/* 2527 */                     out.write("';\n\tif(flag==\"true\")\n\t{\n\t\tif(document.RbmForm.haid.value=='-')\n\t\t{\n\t\t\thideDiv(\"subgroup\");\n\t\t\tshowDiv(\"group\");\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv(\"group\");\n\t\t\tshowDiv(\"subgroup\");\n\t\t}\n\t}\n\telse\n\t{\n\t\tshowDiv(\"group\");\n\t}\n}\nfunction createsubGroup()\n{\n\tif(document.RbmForm.haid.value=='-')\n\t{\n\t\talert(\"");
/* 2528 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.monitoralert"));
/* 2529 */                     out.write("\");\t\t\n\t\tdocument.RbmForm.haid.focus();\n\t}\n\telse\n\t{\n\t\tif(document.RbmForm.subgroupname.value=='')\n\t\t{\n\t\t\talert(\"");
/* 2530 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2531 */                     out.write("\");\n\t\t\tdocument.RbmForm.subgroupname.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv('createsubgroup');\n\t\t\tvar a=document.RbmForm.subgroupname.value;\n\t\t\thttp= getHTTPObject();\n\t\t\tvar haid=document.RbmForm.haid.value;\n\t\t\turl=\"/adminAction.do?method=createSubGroup&haid=\"+haid+\"&subgroupname=\"+encodeURIComponent(a);\t//NO I18N\n\t\t\thttp.open(\"GET\",url,true);\n\t\t\thttp.onreadystatechange = getActionTypes;\n\t\t\thttp.send(null);\n\t\t}\n\t\tshowDiv('subgroup');\n    }\n\n}  \n\n function getActionTypes()\n{\n    if(http.readyState == 4)\n    {\n\t\tvar result = http.responseText;\n\t\tvar id=result;\n\t\tvar stringtokens=id.split(\",\");\n\t\tsid = stringtokens[2];\n\t\tsname=stringtokens[1];\n\t\tsname=decodeURIComponent(stringtokens[1]);\n\t\tsmessage=stringtokens[0];\n\t\tif (sname==null || sname=='undefined')\n\t\t{\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.RbmForm.haid.options[document.RbmForm.haid.length] =new Option(sname,sid,false,true);\n\t\t\thideDiv(\"creategroup\");\n\t\t\thideDiv(\"createsubgroup\");\n");
/* 2532 */                     out.write("\t\t\thideDiv(\"group\");\n\t\t\tshowDiv(\"subgroup\");\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t  \t}\n\t}\n}\n\n");
/*      */                     
/* 2534 */                     String hideStyle = "";
/* 2535 */                     String dName = request.getParameter("dname");
/* 2536 */                     if (dName != null)
/*      */                     {
/*      */ 
/* 2539 */                       out.write("\n\t\t\t\ttry\n\t\t\t\t{\n\t\t\t\t\tdocument.RbmForm.displayname.value='");
/* 2540 */                       out.print(dName);
/* 2541 */                       out.write("';\n\t\t\t\t}\n\t\t\t\tcatch(e)\n\t\t\t\t{\n\t\t\t\t\t//alert(e);\n\t\t\t\t}\n\n\t\t\t");
/*      */                     }
/*      */                     
/*      */ 
/* 2545 */                     out.write("\nfunction getHTTPObject()\n{\n\tvar xmlhttp;\n \t/*@cc_on\n  \t@if (@_jscript_version >= 5)\n  \t  try\n  \t  {\n  \t    xmlhttp = new ActiveXObject(\"Msxml2.XMLHTTP\");//No I18N\n  \t  } catch (e)\n  \t  {\n  \t    try\n  \t    {\n  \t      xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");//No I18N\n  \t    } catch (E)\n  \t    {\n  \t      xmlhttp = false;//No I18N\n  \t    }\n  \t  }\n  \t@else\n  \txmlhttp = false;//No I18N\n\t  @end @*/\n\tif (!xmlhttp && typeof XMLHttpRequest != 'undefined')\n\t{\n\t  try\n          {\n      \t    xmlhttp = new XMLHttpRequest();//No I18N\n    \t  } catch (e)\n    \t  {\n      \t    xmlhttp = false;//No I18N\n    \t  }\n  \t}\n\treturn xmlhttp;\n}\n\nfunction formReload()\n{\n\t//alert(\"Form Reload\");\n    var v=document.RbmForm.type.value;\n    document.RbmForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=\"+v;//No I18N\n    document.RbmForm.submit();\n}\n\nfunction saveMonitor()\n{\n\tvar monitorName = document.getElementById(\"mon_name\").value;//No I18N\n\tvar script = document.getElementById(\"script_names\").options[document.getElementById(\"script_names\").selectedIndex].value;//No I18N\n");
/* 2546 */                     out.write("\thttp = getHTTPObject();\n\tvar url=\"/jsp/ScriptUpdator.jsp?method=savemonitor&monitorname=\"+monitorName+\"&scriptname=\"+script+\"&ind=\"+clientName+\"&usr=\"+user+\"&pxy=\"+proxy;//No I18N\n\thttp.open(\"GET\", url, true);\n\thttp.onreadystatechange = saveMonitorResult;\n\thttp.send(null);\n\n}\n\nfunction saveMonitorResult()\n{\n\tif(http.readyState == 4)\n\t{\n\n\t}\n}\n\n\nfunction validateAndSubmit()\n{\n\tif(document.RbmForm.displayname.value==\"\")\n\t{\n\t\talert('");
/* 2547 */                     out.print(FormatUtil.getString("am.webclient.rbm.errormessage.displaynameempty"));
/* 2548 */                     out.write("');//No I18N\n\t\treturn false;\n\t}\n\n\tvar scrp = document.RbmForm.scriptname.options[document.RbmForm.scriptname.selectedIndex].value;\n\tif(scrp==\"-\")\n\t{\n\t\talert('");
/* 2549 */                     out.print(FormatUtil.getString("am.webclient.rbm.errormessage.webscriptnotselect"));
/* 2550 */                     out.write("');//No I18N\n\t\treturn false;\n\t}\n\t/*var agt = document.RbmForm.rbmagent.options[document.RbmForm.rbmagent.selectedIndex].value;\n\tif(agt==\"-\")\n\t{\n\t\talert(\"Select the Playaback Agent\");\n\t\treturn false;\n\t}\n\tvar agt = document.RbmForm.rbmagent.options;\n\tvar agentSelected =false;\n\tfor(i=0;i<agt.length;i++)\n\t{\n\t\tif(agt[i].selected && agt[i].value!=\"-\")\n\t\t{\n\t\t\tagentSelected=true;\n\t\t}\n\t\tif( agt[i].value==\"-\")\n\t\t{\n\t\t\tagt[i].selected=false;\n\t\t}\n\t}\n\n\tif(!agentSelected)\n\t{\n\t\talert('");
/* 2551 */                     out.print(FormatUtil.getString("am.webclient.rbm.errormessage.selectagent"));
/* 2552 */                     out.write("');//No I18N\n\t\treturn false;\n\t}\n\n\n\n\t//if(document.RbmForm.description.value==\"\")\n\t//{\n\t//\talert(\"Description should not be empty\");\n\t//\treturn false;\n\t//}*/\n\tif(!checkAgentSelectedForRBM()){\n\t\talert('");
/* 2553 */                     out.print(FormatUtil.getString("am.webclient.rbm.errormessage.selectagent"));
/* 2554 */                     out.write("');//No I18N\n\t\treturn false;\n\t}\n\tif(document.RbmForm.pollinterval.value==\"\")\n\t{\n\t\talert('");
/* 2555 */                     out.print(FormatUtil.getString("am.webclient.rbm.errormessage.pollintervalempty"));
/* 2556 */                     out.write("');//No I18N\n\t\treturn false;\n\t}\n\telse\n\t{\n\t\tvar p= document.RbmForm.pollinterval.value;\n\t\tif(isNaN(p))\n\t\t{\n\t\t\talert('");
/* 2557 */                     out.print(FormatUtil.getString("am.webclient.rbm.errormessage.pollintervalnotnumeric"));
/* 2558 */                     out.write("');//No I18N\n\t\t\treturn false;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tif(p<5)\n\t\t\t{\n\t\t\t\talert('");
/* 2559 */                     out.print(FormatUtil.getString("am.webclient.rbm.errormessage.pollintervalminvalue"));
/* 2560 */                     out.write("');//No I18N\n\t\t\t\treturn false;\n\t\t\t}\n\t\t}\n\t}\n\n\tif(document.RbmForm.timeout.value==\"\")\n\t{\n\t\talert('");
/* 2561 */                     out.print(FormatUtil.getString("am.webclient.rbm.errormessage.timeoutempty"));
/* 2562 */                     out.write("');//No I18N\n\t\treturn false;\n\t}\n\telse\n\t{\n\t\tvar p= document.RbmForm.timeout.value;\n\t\tif(isNaN(p))\n\t\t{\n\t\t\t\talert('");
/* 2563 */                     out.print(FormatUtil.getString("am.webclient.rbm.errormessage.timeoutnotnumeric"));
/* 2564 */                     out.write("');//No I18N\n\t\t\treturn false;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tif(p<1)\n\t\t\t{\n\t\t\t\talert('");
/* 2565 */                     out.print(FormatUtil.getString("am.webclient.rbm.errormessage.timeoutminvalue"));
/* 2566 */                     out.write("');//No I18N\n\t\t\t\treturn false;\n\t\t\t}\n\t\t}\n\t}\n\nif(document.RbmForm.haid.value != '-'){\ndocument.RbmForm.addtoha.value=\"true\";\n}else{\ndocument.RbmForm.addtoha.value=\"false\";\n}\n\n/***  IT360-1762 ISSUES CHANGES STARTS HERE ***/\n\n");
/* 2567 */                     if (EnterpriseUtil.isIt360MSPEdition()) {
/* 2568 */                       out.write("\n\nif (document.RbmForm.organization.value== \"-\")\n {\n\talert(\"");
/* 2569 */                       out.print(FormatUtil.getString("it360.addnewmonitor.err.checkcustomer"));
/* 2570 */                       out.write("\");\n\treturn;\n }\n\nif (document.RbmForm.haid.value== \"-\")\n {\n    alert(\"");
/* 2571 */                       out.print(FormatUtil.getString("it360.addnewmonitor.err.checksite"));
/* 2572 */                       out.write("\");\n    return;\n }\n");
/*      */                     }
/* 2574 */                     out.write("\n\n/***  IT360-1762 ISSUES CHANGES ENDS HERE ***/\n\n\n\tdocument.RbmForm.submit();\n\treturn true;\n}\n\nvar scriptmanager;\nfunction openScriptManager(tab)\n{\n\tscriptmanager = window.open('/jsp/RBMScriptManager.jsp?tab='+tab,'Applications','left=200,top=100,toolbar=no,status=no,menubar=no,width=1000,height=610,scrollbars=yes,location=no,resizable=yes');//No I18N\n\n}\n\nfunction change(val,ind,pxy,usr)\n{\n\ttry\n\t{\n\t\tscriptmanager.change(val,ind,pxy,usr);\n\t}\n\tcatch(e){\n\t}\n}\n\nfunction changeState(val)\n{\n\tif(val==\"recordOff\")\n\t{\n\t\tsetTimeout(\"callStatChange()\",100)\t;//No I18N\n\t}\n\telse if(val==\"recordOn\")\n\t{\n\t\t//recordOn = true;\n\t}\n\n}\n\nfunction callStatChange()\n{\n\tvar dName =\"\";\n\ttry\n\t{\n\t\tvar dName = document.RbmForm.displayname.value;\n\t\t//alert(dName);\n\t\tif(dName==\"\" || dName==null || dName==\"undefined\")\n\t\t{\n\t\t\tdName=\"\";//No I18N\n\t\t}\n\t\tdocument.RbmForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&showintrotab=false&type=RBM&dname=\"+dName;//No I18N\n\t\tscriptmanager.focus();\n\t\tscriptmanager.stopActions();\n\t\tdocument.RbmForm.submit();\n");
/* 2575 */                     out.write("\n\t}\n\tcatch(e)\n\t{\n\t\tdocument.RbmForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&showintrotab=false&type=RBM&dname=\"+dName;//No I18N\n\t\tdocument.RbmForm.submit();\n\t}\n}\n\nfunction navigate(url)\n{\n\tlocation.href=url;\n}\n\n</script>\n  ");
/*      */                     
/* 2577 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2578 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2579 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2581 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2582 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2583 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2585 */                         out.write("\n    ");
/*      */                         
/* 2587 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2588 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2589 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2591 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2593 */                         _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.rbmurlmonitor.type.text"));
/* 2594 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2595 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2596 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2599 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2600 */                         out.write("\n    ");
/* 2601 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2603 */                         out.write("\n\n\t \n");
/*      */                         
/* 2605 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2606 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2607 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2609 */                         _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                         
/* 2611 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2612 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2613 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2614 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2615 */                             out = _jspx_page_context.pushBody();
/* 2616 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2617 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2620 */                             out.write(10);
/* 2621 */                             out.write(10);
/* 2622 */                             out.write(10);
/*      */                             
/*      */ 
/*      */                             try
/*      */                             {
/* 2627 */                               out.write("\n\t<title>");
/* 2628 */                               out.print(FormatUtil.getString("am.webclient.rbm.title.text", new String[] { OEMUtil.getOEMString("product.name") }));
/* 2629 */                               out.write("</title>");
/* 2630 */                               out.write(10);
/* 2631 */                               out.write(10);
/*      */                               
/* 2633 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 2634 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2635 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 2637 */                               _jspx_th_html_005fform_005f0.setAction("/createrbm");
/* 2638 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2639 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 2641 */                                   out.write(10);
/* 2642 */                                   out.write(10);
/* 2643 */                                   out.write(9);
/*      */                                   
/* 2645 */                                   String introtab = request.getParameter("showintrotab");
/* 2646 */                                   String dontshowintrotab = request.getParameter("donshowagain");
/* 2647 */                                   if ((dontshowintrotab != null) && (dontshowintrotab.equals("true")))
/*      */                                   {
/* 2649 */                                     CustomExpressionUtil.updateRBMGlobalSettings("false");
/*      */                                   }
/* 2651 */                                   boolean showIntroTab = true;
/* 2652 */                                   if (introtab == null)
/*      */                                   {
/* 2654 */                                     introtab = CustomExpressionUtil.getRBMIntroTab();
/* 2655 */                                     if (introtab.equals("false"))
/*      */                                     {
/* 2657 */                                       showIntroTab = false;
/*      */                                     }
/*      */                                     
/*      */ 
/*      */                                   }
/* 2662 */                                   else if ((introtab != null) && (introtab.equals("false")))
/*      */                                   {
/* 2664 */                                     showIntroTab = false;
/*      */                                   }
/*      */                                   
/*      */ 
/* 2668 */                                   if (showIntroTab)
/*      */                                   {
/*      */ 
/* 2671 */                                     out.write("\n\t\t\t<table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td  width=\"100%\" align=\"center\" >\n\n\n\t\t\t\t\t\t\t\t<table width=\"99%\" align=\"center\">\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td align=\"right\" width=\"65%\">\n\n\t\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n\n\t\t\t\t\t\t\t\t<tr><td colspan=\"5\" height=\"10\"></td></tr>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"150\"></td>\n\t\t\t\t\t\t\t\t<td align=\"left\" onmouseup=\"className='btnover'\" onmousedown=\"className='btnclick'\" onmouseover=\"className='btnover';\" onmouseout=\"className='btnout';\" class=\"btnout\" onclick=\"javascript:location.href('/adminAction.do?method=reloadHostDiscoveryForm&type=RBM&restype=RBM&showintrotab=false');\"><a class=\"bodytext-nounderline\" href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=RBM&restype=RBM&showintrotab=false\"><b>");
/* 2672 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.continue"));
/* 2673 */                                     out.write(" </b></a>\n\t\t\t\t\t\t\t\t</td>\n\n\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t<td width=\"35%\" align=\"right\">\n\t\t\t\t\t\t\t\t<table>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"10\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"left\"><a class=\"bodytext\" href=\"/help/real-browser-monitor.html\" target=\"_blank\">");
/* 2674 */                                     out.print(FormatUtil.getString("am.webclient.rbm.help.text"));
/* 2675 */                                     out.write(" </a></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"ancillary1\">|</td>\n\t\t\t\t\t\t\t\t<td align=\"left\" ><a class=\"bodytext\" href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=RBM&restype=RBM&showintrotab=false&donshowagain=true\">");
/* 2676 */                                     out.print(FormatUtil.getString("am.webclient.anomalyintrotab.dontshow.text"));
/* 2677 */                                     out.write(" </a></td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\n\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t</table>\n\n\n\n\n\n\t\t\t <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t\t        <tr>\n\t\t\t         <td class=\"helpCardHdrTopLeft\"/>\n\t\t\t         <td class=\"helpCardHdrTopBg\"><table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t        <tr>\n\t\t\t         <td  valign=\"middle\" align=\"left\" class=\"helpCardContentBg\"><span class=\"helpHdrTxt\">");
/* 2678 */                                     out.print(FormatUtil.getString("am.mypage.healp.card.text"));
/* 2679 */                                     out.write("</span><img width=\"19\" height=\"16\" align=\"texttop\" src=\"/images/helpCue.gif\"/></td>");
/* 2680 */                                     out.write("\n\t\t\t         <td valign=\"middle\" align=\"left\" class=\"helpCardHdrRightEar\">&nbsp;</td>\n\t\t\t         <td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t        </tr>\n\t\t\t        </table></td>\n\t\t\t        <td class=\"helpCardHdrRightTop\">&nbsp;</td>\n\t\t\t         </tr>\n\t\t\t         <tr>\n\t\t\t         <td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t         <td valign=\"top\">\n\t\t\t        <!--//include your Helpcard template table here..-->\n\n\n\t\t\t    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n\t\t\t        <tr>\n\t\t\t        <td style=\"padding-top: 10px;\" class=\"boxedContent\">\n\t\t\t        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" class=\"help-content-bg\">\n\t\t\t          <tr>\n\t\t\t            <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t\t            <tr>\n\t\t\t              <td class=\"hCardInnerTopLeft\"/>\n\t\t\t              <td class=\"hCardInnerTopBg\"/>\n\t\t\t              <td class=\"hCardInnerTopRight\"/>\n\t\t\t            </tr>\n\t\t\t            <tr><td colspan=\"3\" height=\"10\" style=\"font-size:15px;\" align=\"center\"></td></tr>\n");
/* 2681 */                                     out.write("\t\t\t\t\t\t <tr><td colspan=\"3\" height=\"10\" style=\"font-size:15px;\" align=\"center\"><b>");
/* 2682 */                                     out.print(FormatUtil.getString("am.webclient.rbm.intro.rbmtext"));
/* 2683 */                                     out.write("</b></td></tr>\n\t\t\t            <tr>\n\t\t\t            <td colspan=\"3\" >\n\t\t\t            <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n\n\t\t\t \t\t\t<tr><td colspan=\"3\" height=\"10\" style=\"font-size:15px;\" align=\"center\"></td></tr>\n\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td width=\"70%\" valign=\"top\" style=\"padding:15px; line-height:18px; text-align:justify;\"> <span class=\"bodytext\">\n\t\t\t\t\t\t\t\t\t");
/* 2684 */                                     out.print(FormatUtil.getString("am.rbmmonitor.helpcard.text", new String[] { OEMUtil.getOEMString("product.name") }));
/* 2685 */                                     out.write("\n\t\t\t           \t\t\t\t </span>\n\t\t\t           \t\t\t\t </td>\n\n\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\">\n\n\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td   align=\"center\"  width=\"352\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"rbm-intro\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"20\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr><td class=\"bodytext\" align=\"center\"><b>");
/* 2686 */                                     out.print(FormatUtil.getString("am.webclient.rbm.intropage.palybackwebapp"));
/* 2687 */                                     out.write("</b></td></tr>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"150\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td  width=\"352\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\"  width=\"352\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td  width=\"10\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"footer\" width=\"110\" align=\"center\">");
/* 2688 */                                     out.print(FormatUtil.getString("am.webclient.rbm.intropage.branchoff", new String[] { "1" }));
/* 2689 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"footer\" width=\"110\" align=\"center\">");
/* 2690 */                                     out.print(FormatUtil.getString("am.webclient.rbm.intropage.branchoff", new String[] { "2" }));
/* 2691 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td  width=\"10\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"footer\" width=\"102\" align=\"center\">");
/* 2692 */                                     out.print(FormatUtil.getString("am.webclient.rbm.intropage.branchoff", new String[] { "3" }));
/* 2693 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr><td colspan=\"6\" height=\"70\"></td></tr>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"footer\" align=\"center\" valign=\"top\">");
/* 2694 */                                     out.print(FormatUtil.getString("am.webclient.rbm.type.text"));
/* 2695 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"footer\" align=\"center\" valign=\"top\">");
/* 2696 */                                     out.print(FormatUtil.getString("am.webclient.rbm.type.text"));
/* 2697 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td  width=\"10\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"footer\" align=\"center\" valign=\"top\"><b>");
/* 2698 */                                     out.print(FormatUtil.getString("am.webclient.rbm.intro.rbmtext"));
/* 2699 */                                     out.write("</b></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr><td colspan=\"6\" height=\"130\"></td></tr>\n\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td colspan=\"6\"  width=\"352\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\"  width=\"352\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"23\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"footer\" width=\"157\" align=\"center\">");
/* 2700 */                                     out.print(FormatUtil.getString("am.webclient.rbm.intro.apptext"));
/* 2701 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"45\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"footer\" width=\"157\" align=\"center\">");
/* 2702 */                                     out.print(FormatUtil.getString("am.webclient.rbm.intro.crmtext"));
/* 2703 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr><td colspan=\"5\" height=\"20\"></td></tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td colspan=\"3\" class=\"footer\" align=\"center\"><b>");
/* 2704 */                                     out.print(FormatUtil.getString("am.webclient.rbm.intro.headofftext"));
/* 2705 */                                     out.write("</b></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t            </td>\n\n\t\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t           \t\t\t\t </tr>\n\n\t\t\t            <tr>\n\t\t\t              <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n\t\t\t                <td class=\"hCardInnerBoxBg product-help\">\n\n\t\t\t                </td>\n\t\t\t                  <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n\t\t\t                </tr>\n\t\t\t                 </table></td>\n\t\t\t                </tr>\n\t\t\t                </table>\n\t\t\t                </td>\n\t\t\t              </tr>\n\t\t\t             </table>\n\t\t\t           </td> <td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t            </tr>\n\t\t\t             <tr>\n\t\t\t                <td class=\"helpCardMainBtmLeft\"/>\n");
/* 2706 */                                     out.write("\t\t\t                <td class=\"helpCardMainBtmBg\"/>\n\t\t\t                <td class=\"helpCardMainBtmRight\"/>\n\t\t\t             </tr>\n\t\t\t            </table>\n\n\t\t\t<table width=\"99%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td  width=\"100%\" align=\"center\" >\n\n\n\t\t\t\t\t\t\t\t<table width=\"99%\" align=\"center\">\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td align=\"right\" width=\"65%\">\n\n\t\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n\n\t\t\t\t\t\t\t\t<tr><td colspan=\"5\" height=\"10\"></td></tr>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"150\"></td>\n\t\t\t\t\t\t\t\t<td align=\"left\" onmouseup=\"className='btnover'\" onmousedown=\"className='btnclick'\" onmouseover=\"className='btnover';\" onmouseout=\"className='btnout';\" class=\"btnout\" onclick=\"javascript:location.href('/adminAction.do?method=reloadHostDiscoveryForm&type=RBM&restype=RBM&showintrotab=false');\"><a class=\"bodytext-nounderline\" href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=RBM&restype=RBM&showintrotab=false\"><b>");
/* 2707 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.continue"));
/* 2708 */                                     out.write("</b> </a>\n\t\t\t\t\t\t\t\t</td>\n\n\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t<td width=\"35%\" align=\"right\">\n\t\t\t\t\t\t\t\t<table>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"10\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"left\"><a class=\"bodytext\" href=\"/help/real-browser-monitor.html\" target=\"_blank\">");
/* 2709 */                                     out.print(FormatUtil.getString("am.webclient.rbm.help.text"));
/* 2710 */                                     out.write(" </a></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"ancillary1\">|</td>\n\t\t\t\t\t\t\t\t<td align=\"left\" ><a class=\"bodytext\" href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=RBM&restype=RBM&showintrotab=false&donshowagain=true\">");
/* 2711 */                                     out.print(FormatUtil.getString("am.webclient.anomalyintrotab.dontshow.text"));
/* 2712 */                                     out.write(" </a></td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\n\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t</tr>\n\n\t\t</table>\n\t\t");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 2718 */                                     out.write("\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t\t<td width=\"65%\" valign=\"top\">\n");
/* 2719 */                                     out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link rel=\"stylesheet\" href=\"/images/chosen.css\" />\n");
/* 2720 */                                     String message = (String)request.getAttribute("typemessage");
/*      */                                     
/* 2722 */                                     ManagedApplication mo1 = new ManagedApplication();
/* 2723 */                                     Properties props = com.adventnet.appmanager.util.Constants.getValueForNewMonitor();
/* 2724 */                                     boolean isConfMonitor = false;
/* 2725 */                                     ConfMonitorConfiguration confMonConfig = ConfMonitorConfiguration.getInstance();
/* 2726 */                                     if (message != null)
/*      */                                     {
/* 2728 */                                       out.write("\n    <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n    <tr>\n      <td><img src=\"/images/icon_message_success.gif\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"bodytext\">\n        ");
/* 2729 */                                       out.print(message);
/* 2730 */                                       out.write("</span> </td>\n    </tr>\n  </table>\n     ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 2734 */                                     out.write("\n\n\n<table id=\"newResourceTypes\" width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n\t<td width=\"25%\" class=\"tableheading-monitor-config bodytext label-align addmonitor-label\">&nbsp;");
/* 2735 */                                     out.print(FormatUtil.getString("am.webclient.newresourcetypes.addmonitor.text"));
/* 2736 */                                     out.write("</td>\n    <td class=\"tableheading-monitor-config \" valign=\"middle\">\n");
/* 2737 */                                     if ("UrlSeq".equals(request.getParameter("type"))) {
/* 2738 */                                       DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 2739 */                                       if (frm != null) {
/* 2740 */                                         frm.set("type", "UrlSeq");
/*      */                                       }
/*      */                                     }
/*      */                                     
/* 2744 */                                     if ("UrlMonitor".equals(request.getParameter("type"))) {
/* 2745 */                                       DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 2746 */                                       if (frm != null) {
/* 2747 */                                         frm.set("type", "UrlMonitor");
/*      */                                       }
/*      */                                     }
/*      */                                     
/* 2751 */                                     if ("RBM".equals(request.getParameter("type"))) {
/* 2752 */                                       DynaActionForm frm = (DynaActionForm)request.getAttribute("RbmForm");
/* 2753 */                                       if (frm != null) {
/* 2754 */                                         frm.set("type", "RBM");
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/* 2759 */                                     out.write("\n\n    ");
/*      */                                     
/* 2761 */                                     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2762 */                                     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2763 */                                     _jspx_th_c_005fif_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 2765 */                                     _jspx_th_c_005fif_005f3.setTest("${empty param.wiz && empty param.fromAssociate}");
/* 2766 */                                     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2767 */                                     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                       for (;;) {
/* 2769 */                                         out.write("\n     ");
/*      */                                         
/* 2771 */                                         SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 2772 */                                         _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2773 */                                         _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fif_005f3);
/*      */                                         
/* 2775 */                                         _jspx_th_html_005fselect_005f0.setProperty("type");
/*      */                                         
/* 2777 */                                         _jspx_th_html_005fselect_005f0.setStyle("background-color:#FDFEF2; font-size:13px;");
/*      */                                         
/* 2779 */                                         _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */                                         
/* 2781 */                                         _jspx_th_html_005fselect_005f0.setOnchange("javascript:formReload()");
/* 2782 */                                         int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2783 */                                         if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2784 */                                           if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2785 */                                             out = _jspx_page_context.pushBody();
/* 2786 */                                             _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2787 */                                             _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 2790 */                                             out.write("\n\n      <!-- If you are changing any of the values in this select box then kindly update the corresponding strings in HostDiscoveryHandler.java also-->\n      ");
/*      */                                             
/* 2792 */                                             if ((!com.adventnet.appmanager.util.Constants.isMinimizedversion()) || (com.adventnet.appmanager.util.Constants.getUserType().equals("F")) || (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                             {
/*      */ 
/*      */ 
/* 2796 */                                               out.write("\n\n\t <optgroup label=\"");
/* 2797 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 2798 */                                               out.write("\">\n                                        \n                                        ");
/*      */                                               
/* 2800 */                                               OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2801 */                                               _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2802 */                                               _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 2804 */                                               _jspx_th_html_005foption_005f0.setValue("AIX");
/* 2805 */                                               int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2806 */                                               if (_jspx_eval_html_005foption_005f0 != 0) {
/* 2807 */                                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2808 */                                                   out = _jspx_page_context.pushBody();
/* 2809 */                                                   _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 2810 */                                                   _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 2813 */                                                   out.print(FormatUtil.getString("AIX"));
/* 2814 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 2815 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 2818 */                                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2819 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 2822 */                                               if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2823 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                               }
/*      */                                               
/* 2826 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 2827 */                                               out.write("\n                                        ");
/*      */                                               
/* 2829 */                                               OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2830 */                                               _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2831 */                                               _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 2833 */                                               _jspx_th_html_005foption_005f1.setValue("AS400");
/* 2834 */                                               int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2835 */                                               if (_jspx_eval_html_005foption_005f1 != 0) {
/* 2836 */                                                 if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2837 */                                                   out = _jspx_page_context.pushBody();
/* 2838 */                                                   _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 2839 */                                                   _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 2842 */                                                   out.print(FormatUtil.getString("AS400/iSeries"));
/* 2843 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 2844 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 2847 */                                                 if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2848 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 2851 */                                               if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2852 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                               }
/*      */                                               
/* 2855 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 2856 */                                               out.write("\n                                        ");
/*      */                                               
/* 2858 */                                               OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2859 */                                               _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 2860 */                                               _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 2862 */                                               _jspx_th_html_005foption_005f2.setValue("FreeBSD");
/* 2863 */                                               int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 2864 */                                               if (_jspx_eval_html_005foption_005f2 != 0) {
/* 2865 */                                                 if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2866 */                                                   out = _jspx_page_context.pushBody();
/* 2867 */                                                   _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 2868 */                                                   _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 2871 */                                                   out.print(FormatUtil.getString("FreeBSD/OpenBSD"));
/* 2872 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 2873 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 2876 */                                                 if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2877 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 2880 */                                               if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 2881 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                               }
/*      */                                               
/* 2884 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 2885 */                                               out.write("\n                                        ");
/*      */                                               
/* 2887 */                                               OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2888 */                                               _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 2889 */                                               _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 2891 */                                               _jspx_th_html_005foption_005f3.setValue("HP-UX");
/* 2892 */                                               int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 2893 */                                               if (_jspx_eval_html_005foption_005f3 != 0) {
/* 2894 */                                                 if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2895 */                                                   out = _jspx_page_context.pushBody();
/* 2896 */                                                   _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 2897 */                                                   _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 2900 */                                                   out.print(FormatUtil.getString("HP-UX  / Tru64"));
/* 2901 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 2902 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 2905 */                                                 if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2906 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 2909 */                                               if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 2910 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                               }
/*      */                                               
/* 2913 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 2914 */                                               out.write("\n                                        ");
/*      */                                               
/* 2916 */                                               OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2917 */                                               _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 2918 */                                               _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 2920 */                                               _jspx_th_html_005foption_005f4.setValue("Linux");
/* 2921 */                                               int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 2922 */                                               if (_jspx_eval_html_005foption_005f4 != 0) {
/* 2923 */                                                 if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2924 */                                                   out = _jspx_page_context.pushBody();
/* 2925 */                                                   _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 2926 */                                                   _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 2929 */                                                   out.print(FormatUtil.getString("Linux"));
/* 2930 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 2931 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 2934 */                                                 if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2935 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 2938 */                                               if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 2939 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                               }
/*      */                                               
/* 2942 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 2943 */                                               out.write("\n                                        ");
/*      */                                               
/* 2945 */                                               OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2946 */                                               _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 2947 */                                               _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 2949 */                                               _jspx_th_html_005foption_005f5.setValue("Mac OS");
/* 2950 */                                               int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 2951 */                                               if (_jspx_eval_html_005foption_005f5 != 0) {
/* 2952 */                                                 if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2953 */                                                   out = _jspx_page_context.pushBody();
/* 2954 */                                                   _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 2955 */                                                   _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 2958 */                                                   out.print(FormatUtil.getString("Mac OS"));
/* 2959 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 2960 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 2963 */                                                 if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2964 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 2967 */                                               if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 2968 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                               }
/*      */                                               
/* 2971 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 2972 */                                               out.write("\n                                        ");
/*      */                                               
/* 2974 */                                               OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2975 */                                               _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 2976 */                                               _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 2978 */                                               _jspx_th_html_005foption_005f6.setValue("Novell");
/* 2979 */                                               int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 2980 */                                               if (_jspx_eval_html_005foption_005f6 != 0) {
/* 2981 */                                                 if (_jspx_eval_html_005foption_005f6 != 1) {
/* 2982 */                                                   out = _jspx_page_context.pushBody();
/* 2983 */                                                   _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 2984 */                                                   _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 2987 */                                                   out.print(FormatUtil.getString("Novell"));
/* 2988 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 2989 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 2992 */                                                 if (_jspx_eval_html_005foption_005f6 != 1) {
/* 2993 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 2996 */                                               if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 2997 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                               }
/*      */                                               
/* 3000 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 3001 */                                               out.write("\n                                        ");
/*      */                                               
/* 3003 */                                               OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3004 */                                               _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 3005 */                                               _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3007 */                                               _jspx_th_html_005foption_005f7.setValue("Sun Solaris");
/* 3008 */                                               int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 3009 */                                               if (_jspx_eval_html_005foption_005f7 != 0) {
/* 3010 */                                                 if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3011 */                                                   out = _jspx_page_context.pushBody();
/* 3012 */                                                   _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 3013 */                                                   _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3016 */                                                   out.print(FormatUtil.getString("Sun Solaris"));
/* 3017 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 3018 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3021 */                                                 if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3022 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3025 */                                               if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 3026 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                               }
/*      */                                               
/* 3029 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 3030 */                                               out.write("\n                                        ");
/*      */                                               
/* 3032 */                                               OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3033 */                                               _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 3034 */                                               _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3036 */                                               _jspx_th_html_005foption_005f8.setValue("Windows");
/* 3037 */                                               int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 3038 */                                               if (_jspx_eval_html_005foption_005f8 != 0) {
/* 3039 */                                                 if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3040 */                                                   out = _jspx_page_context.pushBody();
/* 3041 */                                                   _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 3042 */                                                   _jspx_th_html_005foption_005f8.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3045 */                                                   out.print(FormatUtil.getString("Windows"));
/* 3046 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 3047 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3050 */                                                 if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3051 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3054 */                                               if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 3055 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                               }
/*      */                                               
/* 3058 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/* 3059 */                                               out.write("\n                                        ");
/*      */                                               
/* 3061 */                                               OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3062 */                                               _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 3063 */                                               _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3065 */                                               _jspx_th_html_005foption_005f9.setValue("Windows Cluster");
/* 3066 */                                               int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 3067 */                                               if (_jspx_eval_html_005foption_005f9 != 0) {
/* 3068 */                                                 if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3069 */                                                   out = _jspx_page_context.pushBody();
/* 3070 */                                                   _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 3071 */                                                   _jspx_th_html_005foption_005f9.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3074 */                                                   out.print(FormatUtil.getString("Windows Cluster"));
/* 3075 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 3076 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3079 */                                                 if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3080 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3083 */                                               if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 3084 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                               }
/*      */                                               
/* 3087 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/* 3088 */                                               out.write("\n                                        \n\n  ");
/*      */                                               
/* 3090 */                                               ArrayList rows1 = mo1.getRows("select RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH from AM_ManagedResourceType,AM_MONITOR_TYPES where TYPEID=RESOURCETYPEID and RESOURCEGROUP='SYS' and AMCREATED='NO' ORDER BY UPPER(DISPLAYNAME)");
/* 3091 */                                               if ((rows1 != null) && (rows1.size() > 0))
/*      */                                               {
/* 3093 */                                                 for (int i = 0; i < rows1.size(); i++)
/*      */                                                 {
/* 3095 */                                                   ArrayList row = (ArrayList)rows1.get(i);
/* 3096 */                                                   String res = (String)row.get(0);
/* 3097 */                                                   String dname = (String)row.get(1);
/* 3098 */                                                   String values = props.getProperty(res);
/* 3099 */                                                   if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                   {
/*      */ 
/* 3102 */                                                     out.write("\n\t\t\t\t");
/*      */                                                     
/* 3104 */                                                     OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3105 */                                                     _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 3106 */                                                     _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                     
/* 3108 */                                                     _jspx_th_html_005foption_005f10.setValue(values);
/* 3109 */                                                     int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 3110 */                                                     if (_jspx_eval_html_005foption_005f10 != 0) {
/* 3111 */                                                       if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3112 */                                                         out = _jspx_page_context.pushBody();
/* 3113 */                                                         _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/* 3114 */                                                         _jspx_th_html_005foption_005f10.doInitBody();
/*      */                                                       }
/*      */                                                       for (;;) {
/* 3117 */                                                         out.write(32);
/* 3118 */                                                         out.print(FormatUtil.getString(dname));
/* 3119 */                                                         int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/* 3120 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/* 3123 */                                                       if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3124 */                                                         out = _jspx_page_context.popBody();
/*      */                                                       }
/*      */                                                     }
/* 3127 */                                                     if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 3128 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                                     }
/*      */                                                     
/* 3131 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/* 3132 */                                                     out.write("\n\t\t\t");
/*      */                                                   }
/*      */                                                 }
/*      */                                               }
/*      */                                               
/*      */ 
/* 3138 */                                               String[] categoryLink = { "APP", "ERP", "TM", "SYS", "DBS", "SER", "URL", "MS", "MOM", "CAM", "VIR", "CLD" };
/*      */                                               
/* 3140 */                                               String[] categoryTitle = { "am.webclient.monitorgroupsecond.category.appserver", "am.webclient.monitorgroupsecond.category.erp", "am.webclient.monitorgroupsecond.category.transaction", "am.webclient.monitorgroupsecond.category.servers", "am.webclient.monitorgroupsecond.category.dbserver", "am.webclient.monitorgroupsecond.category.services", "am.webclient.monitorgroupsecond.category.webservices.title", "am.webclient.monitorgroupsecond.category.mailserver", "Middleware/Portal", "am.webclient.monitorgroupsecond.category.custom", "am.webclient.monitorgroupsecond.category.virtualserver", "am.webclient.monitorgroupsecond.category.cloudapps" };
/*      */                                               
/*      */ 
/* 3143 */                                               if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD"))
/*      */                                               {
/*      */ 
/* 3146 */                                                 categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 3147 */                                                 categoryTitle = com.adventnet.appmanager.util.Constants.categoryTitle;
/*      */                                               }
/* 3149 */                                               for (int c = 0; c < categoryLink.length; c++)
/*      */                                               {
/* 3151 */                                                 ArrayList unSupportedTypes = com.adventnet.appmanager.util.Constants.getUnSupportedAsList();
/* 3152 */                                                 if ((!categoryLink[c].equals("SYS")) && (!categoryLink[c].equals("NWD")) && (!categoryLink[c].equals("SAN")) && (!categoryLink[c].equals("EMO")) && (!categoryLink[c].equals("SCR")) && ((!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")) || (!unSupportedTypes.contains(categoryLink[c]))))
/*      */                                                 {
/*      */ 
/*      */ 
/* 3156 */                                                   StringBuffer queryBuf = new StringBuffer();
/* 3157 */                                                   queryBuf.append("SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='");
/* 3158 */                                                   queryBuf.append(categoryLink[c] + "'");
/* 3159 */                                                   queryBuf.append(" ");
/* 3160 */                                                   queryBuf.append("and RESOURCETYPE in");
/* 3161 */                                                   queryBuf.append(" ");
/* 3162 */                                                   queryBuf.append(com.adventnet.appmanager.util.Constants.resourceTypes);
/* 3163 */                                                   if (categoryLink[c].equals("APP"))
/*      */                                                   {
/* 3165 */                                                     queryBuf.append(" ");
/* 3166 */                                                     queryBuf.append("and DISPLAYNAME NOT IN ('WebLogic Clusters')");
/* 3167 */                                                     queryBuf.append(" ");
/*      */                                                   }
/* 3169 */                                                   else if (categoryLink[c].equals("SER"))
/*      */                                                   {
/* 3171 */                                                     queryBuf.append(" ");
/* 3172 */                                                     queryBuf.append("and RESOURCETYPE<>'RMI'");
/* 3173 */                                                     queryBuf.append(" ");
/*      */                                                   }
/* 3175 */                                                   else if (categoryLink[c].equals("CAM"))
/*      */                                                   {
/* 3177 */                                                     queryBuf.append(" ");
/* 3178 */                                                     queryBuf.append("and RESOURCETYPE != 'directory'");
/* 3179 */                                                     queryBuf.append(" ");
/*      */                                                   }
/* 3181 */                                                   queryBuf.append(" ");
/* 3182 */                                                   queryBuf.append("ORDER BY UPPER(DISPLAYNAME)");
/* 3183 */                                                   ArrayList rows = mo1.getRows(queryBuf.toString());
/* 3184 */                                                   if ((rows != null) && (rows.size() != 0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/* 3189 */                                                     out.write("\n</optgroup>\t\t\t\t<optgroup label=\"");
/* 3190 */                                                     out.print(FormatUtil.getString(categoryTitle[c]));
/* 3191 */                                                     out.write(34);
/* 3192 */                                                     out.write(62);
/* 3193 */                                                     out.write(10);
/*      */                                                     
/*      */ 
/* 3196 */                                                     for (int i = 0; i < rows.size(); i++)
/*      */                                                     {
/* 3198 */                                                       ArrayList row = (ArrayList)rows.get(i);
/* 3199 */                                                       String res = (String)row.get(0);
/* 3200 */                                                       if (res.equals("file"))
/*      */                                                       {
/* 3202 */                                                         res = "File / Directory Monitor";
/*      */                                                       }
/* 3204 */                                                       String dname = (String)row.get(1);
/* 3205 */                                                       String values = props.getProperty(res);
/* 3206 */                                                       if ((!EnterpriseUtil.isCloudEdition()) || (!unSupportedTypes.contains(values)))
/*      */                                                       {
/*      */ 
/* 3209 */                                                         if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                         {
/*      */ 
/* 3212 */                                                           out.write("\n\t\t\t\t \t");
/*      */                                                           
/* 3214 */                                                           OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3215 */                                                           _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 3216 */                                                           _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                           
/* 3218 */                                                           _jspx_th_html_005foption_005f11.setValue(values);
/* 3219 */                                                           int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 3220 */                                                           if (_jspx_eval_html_005foption_005f11 != 0) {
/* 3221 */                                                             if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3222 */                                                               out = _jspx_page_context.pushBody();
/* 3223 */                                                               _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/* 3224 */                                                               _jspx_th_html_005foption_005f11.doInitBody();
/*      */                                                             }
/*      */                                                             for (;;) {
/* 3227 */                                                               out.write(32);
/* 3228 */                                                               out.print(FormatUtil.getString(dname));
/* 3229 */                                                               int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/* 3230 */                                                               if (evalDoAfterBody != 2)
/*      */                                                                 break;
/*      */                                                             }
/* 3233 */                                                             if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3234 */                                                               out = _jspx_page_context.popBody();
/*      */                                                             }
/*      */                                                           }
/* 3237 */                                                           if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 3238 */                                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                                                           }
/*      */                                                           
/* 3241 */                                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11);
/* 3242 */                                                           out.write("\n\t\t\t\t");
/*      */                                                         }
/*      */                                                       }
/*      */                                                     }
/*      */                                                     
/* 3247 */                                                     if (categoryLink[c].equals("VIR"))
/*      */                                                     {
/*      */ 
/* 3250 */                                                       out.write("\n\t\t\t\t\t");
/*      */                                                       
/* 3252 */                                                       OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3253 */                                                       _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 3254 */                                                       _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3256 */                                                       _jspx_th_html_005foption_005f12.setValue("VCenter");
/* 3257 */                                                       int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 3258 */                                                       if (_jspx_eval_html_005foption_005f12 != 0) {
/* 3259 */                                                         if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3260 */                                                           out = _jspx_page_context.pushBody();
/* 3261 */                                                           _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/* 3262 */                                                           _jspx_th_html_005foption_005f12.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3265 */                                                           out.write(32);
/* 3266 */                                                           out.print(FormatUtil.getString("am.webclient.addmonitor.vcenter.name"));
/* 3267 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/* 3268 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3271 */                                                         if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3272 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3275 */                                                       if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 3276 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                                                       }
/*      */                                                       
/* 3279 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/* 3280 */                                                       out.write("\n\t\t\t\t");
/*      */                                                     }
/*      */                                                   }
/*      */                                                 } }
/* 3284 */                                               String usertype = FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/* 3285 */                                               if (!usertype.equals("F"))
/*      */                                               {
/* 3287 */                                                 if (((!EnterpriseUtil.isIt360MSPEdition()) || (!DBUtil.isSharedProbe())) && (!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                                 {
/* 3289 */                                                   out.write("\n    </optgroup> <optgroup label=\"");
/* 3290 */                                                   out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3291 */                                                   out.write("\">\n     ");
/*      */                                                   
/* 3293 */                                                   OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3294 */                                                   _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/* 3295 */                                                   _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3297 */                                                   _jspx_th_html_005foption_005f13.setValue("All:1008");
/* 3298 */                                                   int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/* 3299 */                                                   if (_jspx_eval_html_005foption_005f13 != 0) {
/* 3300 */                                                     if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3301 */                                                       out = _jspx_page_context.pushBody();
/* 3302 */                                                       _jspx_th_html_005foption_005f13.setBodyContent((BodyContent)out);
/* 3303 */                                                       _jspx_th_html_005foption_005f13.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3306 */                                                       out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3307 */                                                       out.write(32);
/* 3308 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f13.doAfterBody();
/* 3309 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3312 */                                                     if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3313 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3316 */                                                   if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/* 3317 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                                                   }
/*      */                                                   
/* 3320 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/* 3321 */                                                   out.write("\n\n     ");
/*      */                                                 }
/*      */                                                 
/*      */                                               }
/*      */                                               
/*      */                                             }
/* 3327 */                                             else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("LAMP"))
/*      */                                             {
/*      */ 
/* 3330 */                                               out.write("\n\t\t\t </optgroup>  <optgroup label=\"");
/* 3331 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3332 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 3334 */                                               OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3335 */                                               _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/* 3336 */                                               _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3338 */                                               _jspx_th_html_005foption_005f14.setValue("SYSTEM:9999");
/* 3339 */                                               int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/* 3340 */                                               if (_jspx_eval_html_005foption_005f14 != 0) {
/* 3341 */                                                 if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3342 */                                                   out = _jspx_page_context.pushBody();
/* 3343 */                                                   _jspx_th_html_005foption_005f14.setBodyContent((BodyContent)out);
/* 3344 */                                                   _jspx_th_html_005foption_005f14.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3347 */                                                   out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 3348 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f14.doAfterBody();
/* 3349 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3352 */                                                 if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3353 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3356 */                                               if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/* 3357 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14); return;
/*      */                                               }
/*      */                                               
/* 3360 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/* 3361 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3362 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 3363 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 3365 */                                               OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3366 */                                               _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/* 3367 */                                               _jspx_th_html_005foption_005f15.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3369 */                                               _jspx_th_html_005foption_005f15.setValue("MYSQLDB:3306");
/* 3370 */                                               int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/* 3371 */                                               if (_jspx_eval_html_005foption_005f15 != 0) {
/* 3372 */                                                 if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3373 */                                                   out = _jspx_page_context.pushBody();
/* 3374 */                                                   _jspx_th_html_005foption_005f15.setBodyContent((BodyContent)out);
/* 3375 */                                                   _jspx_th_html_005foption_005f15.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3378 */                                                   out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 3379 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f15.doAfterBody();
/* 3380 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3383 */                                                 if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3384 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3387 */                                               if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/* 3388 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15); return;
/*      */                                               }
/*      */                                               
/* 3391 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/* 3392 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3393 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 3394 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 3396 */                                               OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3397 */                                               _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/* 3398 */                                               _jspx_th_html_005foption_005f16.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3400 */                                               _jspx_th_html_005foption_005f16.setValue("JMX1.2-MX4J-RMI:1099");
/* 3401 */                                               int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/* 3402 */                                               if (_jspx_eval_html_005foption_005f16 != 0) {
/* 3403 */                                                 if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3404 */                                                   out = _jspx_page_context.pushBody();
/* 3405 */                                                   _jspx_th_html_005foption_005f16.setBodyContent((BodyContent)out);
/* 3406 */                                                   _jspx_th_html_005foption_005f16.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3409 */                                                   out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 3410 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f16.doAfterBody();
/* 3411 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3414 */                                                 if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3415 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3418 */                                               if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/* 3419 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16); return;
/*      */                                               }
/*      */                                               
/* 3422 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/* 3423 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3425 */                                               OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3426 */                                               _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/* 3427 */                                               _jspx_th_html_005foption_005f17.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3429 */                                               _jspx_th_html_005foption_005f17.setValue("SERVICE:9090");
/* 3430 */                                               int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/* 3431 */                                               if (_jspx_eval_html_005foption_005f17 != 0) {
/* 3432 */                                                 if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3433 */                                                   out = _jspx_page_context.pushBody();
/* 3434 */                                                   _jspx_th_html_005foption_005f17.setBodyContent((BodyContent)out);
/* 3435 */                                                   _jspx_th_html_005foption_005f17.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3438 */                                                   out.write(32);
/* 3439 */                                                   out.print(FormatUtil.getString("Service Monitoring"));
/* 3440 */                                                   out.write(32);
/* 3441 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f17.doAfterBody();
/* 3442 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3445 */                                                 if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3446 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3449 */                                               if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/* 3450 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17); return;
/*      */                                               }
/*      */                                               
/* 3453 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/* 3454 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3456 */                                               OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3457 */                                               _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/* 3458 */                                               _jspx_th_html_005foption_005f18.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3460 */                                               _jspx_th_html_005foption_005f18.setValue("RMI:1099");
/* 3461 */                                               int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/* 3462 */                                               if (_jspx_eval_html_005foption_005f18 != 0) {
/* 3463 */                                                 if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3464 */                                                   out = _jspx_page_context.pushBody();
/* 3465 */                                                   _jspx_th_html_005foption_005f18.setBodyContent((BodyContent)out);
/* 3466 */                                                   _jspx_th_html_005foption_005f18.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3469 */                                                   out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 3470 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f18.doAfterBody();
/* 3471 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3474 */                                                 if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3475 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3478 */                                               if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/* 3479 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18); return;
/*      */                                               }
/*      */                                               
/* 3482 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/* 3483 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3485 */                                               OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3486 */                                               _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/* 3487 */                                               _jspx_th_html_005foption_005f19.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3489 */                                               _jspx_th_html_005foption_005f19.setValue("SNMP:161");
/* 3490 */                                               int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/* 3491 */                                               if (_jspx_eval_html_005foption_005f19 != 0) {
/* 3492 */                                                 if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3493 */                                                   out = _jspx_page_context.pushBody();
/* 3494 */                                                   _jspx_th_html_005foption_005f19.setBodyContent((BodyContent)out);
/* 3495 */                                                   _jspx_th_html_005foption_005f19.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3498 */                                                   out.print(FormatUtil.getString("SNMP"));
/* 3499 */                                                   out.write(" (V1 or V2c)");
/* 3500 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f19.doAfterBody();
/* 3501 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3504 */                                                 if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3505 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3508 */                                               if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/* 3509 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19); return;
/*      */                                               }
/*      */                                               
/* 3512 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/* 3513 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3515 */                                               OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3516 */                                               _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/* 3517 */                                               _jspx_th_html_005foption_005f20.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3519 */                                               _jspx_th_html_005foption_005f20.setValue("TELNET:23");
/* 3520 */                                               int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/* 3521 */                                               if (_jspx_eval_html_005foption_005f20 != 0) {
/* 3522 */                                                 if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3523 */                                                   out = _jspx_page_context.pushBody();
/* 3524 */                                                   _jspx_th_html_005foption_005f20.setBodyContent((BodyContent)out);
/* 3525 */                                                   _jspx_th_html_005foption_005f20.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3528 */                                                   out.print(FormatUtil.getString("Telnet"));
/* 3529 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f20.doAfterBody();
/* 3530 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3533 */                                                 if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3534 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3537 */                                               if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/* 3538 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20); return;
/*      */                                               }
/*      */                                               
/* 3541 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/* 3542 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3543 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 3544 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 3546 */                                               OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3547 */                                               _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/* 3548 */                                               _jspx_th_html_005foption_005f21.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3550 */                                               _jspx_th_html_005foption_005f21.setValue("APACHE:80");
/* 3551 */                                               int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/* 3552 */                                               if (_jspx_eval_html_005foption_005f21 != 0) {
/* 3553 */                                                 if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3554 */                                                   out = _jspx_page_context.pushBody();
/* 3555 */                                                   _jspx_th_html_005foption_005f21.setBodyContent((BodyContent)out);
/* 3556 */                                                   _jspx_th_html_005foption_005f21.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3559 */                                                   out.write(32);
/* 3560 */                                                   out.print(FormatUtil.getString("Apache Server"));
/* 3561 */                                                   out.write(32);
/* 3562 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f21.doAfterBody();
/* 3563 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3566 */                                                 if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3567 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3570 */                                               if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/* 3571 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21); return;
/*      */                                               }
/*      */                                               
/* 3574 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/* 3575 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3577 */                                               OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3578 */                                               _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/* 3579 */                                               _jspx_th_html_005foption_005f22.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3581 */                                               _jspx_th_html_005foption_005f22.setValue("PHP:80");
/* 3582 */                                               int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/* 3583 */                                               if (_jspx_eval_html_005foption_005f22 != 0) {
/* 3584 */                                                 if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3585 */                                                   out = _jspx_page_context.pushBody();
/* 3586 */                                                   _jspx_th_html_005foption_005f22.setBodyContent((BodyContent)out);
/* 3587 */                                                   _jspx_th_html_005foption_005f22.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3590 */                                                   out.print(FormatUtil.getString("PHP"));
/* 3591 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f22.doAfterBody();
/* 3592 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3595 */                                                 if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3596 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3599 */                                               if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/* 3600 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22); return;
/*      */                                               }
/*      */                                               
/* 3603 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/* 3604 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3606 */                                               OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3607 */                                               _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/* 3608 */                                               _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3610 */                                               _jspx_th_html_005foption_005f23.setValue("UrlMonitor");
/* 3611 */                                               int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/* 3612 */                                               if (_jspx_eval_html_005foption_005f23 != 0) {
/* 3613 */                                                 if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3614 */                                                   out = _jspx_page_context.pushBody();
/* 3615 */                                                   _jspx_th_html_005foption_005f23.setBodyContent((BodyContent)out);
/* 3616 */                                                   _jspx_th_html_005foption_005f23.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3619 */                                                   out.print(FormatUtil.getString("HTTP-URLs"));
/* 3620 */                                                   out.write(32);
/* 3621 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f23.doAfterBody();
/* 3622 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3625 */                                                 if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3626 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3629 */                                               if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/* 3630 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23); return;
/*      */                                               }
/*      */                                               
/* 3633 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23);
/* 3634 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3636 */                                               OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3637 */                                               _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/* 3638 */                                               _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3640 */                                               _jspx_th_html_005foption_005f24.setValue("UrlSeq");
/* 3641 */                                               int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/* 3642 */                                               if (_jspx_eval_html_005foption_005f24 != 0) {
/* 3643 */                                                 if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3644 */                                                   out = _jspx_page_context.pushBody();
/* 3645 */                                                   _jspx_th_html_005foption_005f24.setBodyContent((BodyContent)out);
/* 3646 */                                                   _jspx_th_html_005foption_005f24.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3649 */                                                   out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 3650 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f24.doAfterBody();
/* 3651 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3654 */                                                 if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3655 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3658 */                                               if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/* 3659 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24); return;
/*      */                                               }
/*      */                                               
/* 3662 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24);
/* 3663 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3665 */                                               OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3666 */                                               _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/* 3667 */                                               _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3669 */                                               _jspx_th_html_005foption_005f25.setValue("WEB:80");
/* 3670 */                                               int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/* 3671 */                                               if (_jspx_eval_html_005foption_005f25 != 0) {
/* 3672 */                                                 if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3673 */                                                   out = _jspx_page_context.pushBody();
/* 3674 */                                                   _jspx_th_html_005foption_005f25.setBodyContent((BodyContent)out);
/* 3675 */                                                   _jspx_th_html_005foption_005f25.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3678 */                                                   out.write(32);
/* 3679 */                                                   out.print(FormatUtil.getString("Web Server"));
/* 3680 */                                                   out.write(32);
/* 3681 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f25.doAfterBody();
/* 3682 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3685 */                                                 if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3686 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3689 */                                               if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/* 3690 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25); return;
/*      */                                               }
/*      */                                               
/* 3693 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25);
/* 3694 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3696 */                                               OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3697 */                                               _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/* 3698 */                                               _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3700 */                                               _jspx_th_html_005foption_005f26.setValue("Web Service");
/* 3701 */                                               int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/* 3702 */                                               if (_jspx_eval_html_005foption_005f26 != 0) {
/* 3703 */                                                 if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3704 */                                                   out = _jspx_page_context.pushBody();
/* 3705 */                                                   _jspx_th_html_005foption_005f26.setBodyContent((BodyContent)out);
/* 3706 */                                                   _jspx_th_html_005foption_005f26.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3709 */                                                   out.write(32);
/* 3710 */                                                   out.print(FormatUtil.getString("Web Service"));
/* 3711 */                                                   out.write(32);
/* 3712 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f26.doAfterBody();
/* 3713 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3716 */                                                 if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3717 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3720 */                                               if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/* 3721 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26); return;
/*      */                                               }
/*      */                                               
/* 3724 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26);
/* 3725 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3726 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 3727 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 3729 */                                               OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3730 */                                               _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/* 3731 */                                               _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3733 */                                               _jspx_th_html_005foption_005f27.setValue("MAIL:25");
/* 3734 */                                               int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/* 3735 */                                               if (_jspx_eval_html_005foption_005f27 != 0) {
/* 3736 */                                                 if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3737 */                                                   out = _jspx_page_context.pushBody();
/* 3738 */                                                   _jspx_th_html_005foption_005f27.setBodyContent((BodyContent)out);
/* 3739 */                                                   _jspx_th_html_005foption_005f27.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3742 */                                                   out.print(FormatUtil.getString("Mail Server"));
/* 3743 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f27.doAfterBody();
/* 3744 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3747 */                                                 if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3748 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3751 */                                               if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/* 3752 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27); return;
/*      */                                               }
/*      */                                               
/* 3755 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27);
/* 3756 */                                               out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3757 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 3758 */                                               out.write("\">\n\t\t\t   ");
/*      */                                               
/* 3760 */                                               OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3761 */                                               _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/* 3762 */                                               _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3764 */                                               _jspx_th_html_005foption_005f28.setValue("Custom-Application");
/* 3765 */                                               int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/* 3766 */                                               if (_jspx_eval_html_005foption_005f28 != 0) {
/* 3767 */                                                 if (_jspx_eval_html_005foption_005f28 != 1) {
/* 3768 */                                                   out = _jspx_page_context.pushBody();
/* 3769 */                                                   _jspx_th_html_005foption_005f28.setBodyContent((BodyContent)out);
/* 3770 */                                                   _jspx_th_html_005foption_005f28.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3773 */                                                   out.write(32);
/* 3774 */                                                   out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 3775 */                                                   out.write(32);
/* 3776 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f28.doAfterBody();
/* 3777 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3780 */                                                 if (_jspx_eval_html_005foption_005f28 != 1) {
/* 3781 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3784 */                                               if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/* 3785 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28); return;
/*      */                                               }
/*      */                                               
/* 3788 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28);
/* 3789 */                                               out.write("\n\t\t\t   ");
/*      */                                               
/* 3791 */                                               OptionTag _jspx_th_html_005foption_005f29 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3792 */                                               _jspx_th_html_005foption_005f29.setPageContext(_jspx_page_context);
/* 3793 */                                               _jspx_th_html_005foption_005f29.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3795 */                                               _jspx_th_html_005foption_005f29.setValue("Script Monitor");
/* 3796 */                                               int _jspx_eval_html_005foption_005f29 = _jspx_th_html_005foption_005f29.doStartTag();
/* 3797 */                                               if (_jspx_eval_html_005foption_005f29 != 0) {
/* 3798 */                                                 if (_jspx_eval_html_005foption_005f29 != 1) {
/* 3799 */                                                   out = _jspx_page_context.pushBody();
/* 3800 */                                                   _jspx_th_html_005foption_005f29.setBodyContent((BodyContent)out);
/* 3801 */                                                   _jspx_th_html_005foption_005f29.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3804 */                                                   out.print(FormatUtil.getString("Script Monitor"));
/* 3805 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f29.doAfterBody();
/* 3806 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3809 */                                                 if (_jspx_eval_html_005foption_005f29 != 1) {
/* 3810 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3813 */                                               if (_jspx_th_html_005foption_005f29.doEndTag() == 5) {
/* 3814 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29); return;
/*      */                                               }
/*      */                                               
/* 3817 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29);
/* 3818 */                                               out.write("\n\n    ");
/*      */ 
/*      */                                             }
/* 3821 */                                             else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("J2EE"))
/*      */                                             {
/*      */ 
/* 3824 */                                               out.write("\n        ");
/*      */                                               
/* 3826 */                                               OptionTag _jspx_th_html_005foption_005f30 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3827 */                                               _jspx_th_html_005foption_005f30.setPageContext(_jspx_page_context);
/* 3828 */                                               _jspx_th_html_005foption_005f30.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3830 */                                               _jspx_th_html_005foption_005f30.setValue("SYSTEM:9999");
/* 3831 */                                               int _jspx_eval_html_005foption_005f30 = _jspx_th_html_005foption_005f30.doStartTag();
/* 3832 */                                               if (_jspx_eval_html_005foption_005f30 != 0) {
/* 3833 */                                                 if (_jspx_eval_html_005foption_005f30 != 1) {
/* 3834 */                                                   out = _jspx_page_context.pushBody();
/* 3835 */                                                   _jspx_th_html_005foption_005f30.setBodyContent((BodyContent)out);
/* 3836 */                                                   _jspx_th_html_005foption_005f30.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3839 */                                                   out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 3840 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f30.doAfterBody();
/* 3841 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3844 */                                                 if (_jspx_eval_html_005foption_005f30 != 1) {
/* 3845 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3848 */                                               if (_jspx_th_html_005foption_005f30.doEndTag() == 5) {
/* 3849 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30); return;
/*      */                                               }
/*      */                                               
/* 3852 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30);
/* 3853 */                                               out.write("\n       ");
/*      */                                               
/* 3855 */                                               OptionTag _jspx_th_html_005foption_005f31 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3856 */                                               _jspx_th_html_005foption_005f31.setPageContext(_jspx_page_context);
/* 3857 */                                               _jspx_th_html_005foption_005f31.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3859 */                                               _jspx_th_html_005foption_005f31.setValue("JBoss:8080");
/* 3860 */                                               int _jspx_eval_html_005foption_005f31 = _jspx_th_html_005foption_005f31.doStartTag();
/* 3861 */                                               if (_jspx_eval_html_005foption_005f31 != 0) {
/* 3862 */                                                 if (_jspx_eval_html_005foption_005f31 != 1) {
/* 3863 */                                                   out = _jspx_page_context.pushBody();
/* 3864 */                                                   _jspx_th_html_005foption_005f31.setBodyContent((BodyContent)out);
/* 3865 */                                                   _jspx_th_html_005foption_005f31.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3868 */                                                   out.write(32);
/* 3869 */                                                   out.print(FormatUtil.getString("JBoss Server"));
/* 3870 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f31.doAfterBody();
/* 3871 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3874 */                                                 if (_jspx_eval_html_005foption_005f31 != 1) {
/* 3875 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3878 */                                               if (_jspx_th_html_005foption_005f31.doEndTag() == 5) {
/* 3879 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31); return;
/*      */                                               }
/*      */                                               
/* 3882 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31);
/* 3883 */                                               out.write("\n      ");
/*      */                                               
/* 3885 */                                               OptionTag _jspx_th_html_005foption_005f32 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3886 */                                               _jspx_th_html_005foption_005f32.setPageContext(_jspx_page_context);
/* 3887 */                                               _jspx_th_html_005foption_005f32.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3889 */                                               _jspx_th_html_005foption_005f32.setValue("Tomcat:8080");
/* 3890 */                                               int _jspx_eval_html_005foption_005f32 = _jspx_th_html_005foption_005f32.doStartTag();
/* 3891 */                                               if (_jspx_eval_html_005foption_005f32 != 0) {
/* 3892 */                                                 if (_jspx_eval_html_005foption_005f32 != 1) {
/* 3893 */                                                   out = _jspx_page_context.pushBody();
/* 3894 */                                                   _jspx_th_html_005foption_005f32.setBodyContent((BodyContent)out);
/* 3895 */                                                   _jspx_th_html_005foption_005f32.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3898 */                                                   out.print(FormatUtil.getString("Tomcat Server"));
/* 3899 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f32.doAfterBody();
/* 3900 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3903 */                                                 if (_jspx_eval_html_005foption_005f32 != 1) {
/* 3904 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3907 */                                               if (_jspx_th_html_005foption_005f32.doEndTag() == 5) {
/* 3908 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32); return;
/*      */                                               }
/*      */                                               
/* 3911 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32);
/* 3912 */                                               out.write("\n       ");
/*      */                                               
/* 3914 */                                               OptionTag _jspx_th_html_005foption_005f33 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3915 */                                               _jspx_th_html_005foption_005f33.setPageContext(_jspx_page_context);
/* 3916 */                                               _jspx_th_html_005foption_005f33.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3918 */                                               _jspx_th_html_005foption_005f33.setValue("WEBLOGIC:7001");
/* 3919 */                                               int _jspx_eval_html_005foption_005f33 = _jspx_th_html_005foption_005f33.doStartTag();
/* 3920 */                                               if (_jspx_eval_html_005foption_005f33 != 0) {
/* 3921 */                                                 if (_jspx_eval_html_005foption_005f33 != 1) {
/* 3922 */                                                   out = _jspx_page_context.pushBody();
/* 3923 */                                                   _jspx_th_html_005foption_005f33.setBodyContent((BodyContent)out);
/* 3924 */                                                   _jspx_th_html_005foption_005f33.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3927 */                                                   out.write(32);
/* 3928 */                                                   out.print(FormatUtil.getString("WebLogic Server"));
/* 3929 */                                                   out.write(32);
/* 3930 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f33.doAfterBody();
/* 3931 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3934 */                                                 if (_jspx_eval_html_005foption_005f33 != 1) {
/* 3935 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3938 */                                               if (_jspx_th_html_005foption_005f33.doEndTag() == 5) {
/* 3939 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33); return;
/*      */                                               }
/*      */                                               
/* 3942 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33);
/* 3943 */                                               out.write("\n      ");
/*      */                                               
/* 3945 */                                               OptionTag _jspx_th_html_005foption_005f34 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3946 */                                               _jspx_th_html_005foption_005f34.setPageContext(_jspx_page_context);
/* 3947 */                                               _jspx_th_html_005foption_005f34.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3949 */                                               _jspx_th_html_005foption_005f34.setValue("WEBSPHERE:9080");
/* 3950 */                                               int _jspx_eval_html_005foption_005f34 = _jspx_th_html_005foption_005f34.doStartTag();
/* 3951 */                                               if (_jspx_eval_html_005foption_005f34 != 0) {
/* 3952 */                                                 if (_jspx_eval_html_005foption_005f34 != 1) {
/* 3953 */                                                   out = _jspx_page_context.pushBody();
/* 3954 */                                                   _jspx_th_html_005foption_005f34.setBodyContent((BodyContent)out);
/* 3955 */                                                   _jspx_th_html_005foption_005f34.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3958 */                                                   out.write(32);
/* 3959 */                                                   out.print(FormatUtil.getString("WebSphere Server"));
/* 3960 */                                                   out.write(32);
/* 3961 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f34.doAfterBody();
/* 3962 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3965 */                                                 if (_jspx_eval_html_005foption_005f34 != 1) {
/* 3966 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3969 */                                               if (_jspx_th_html_005foption_005f34.doEndTag() == 5) {
/* 3970 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34); return;
/*      */                                               }
/*      */                                               
/* 3973 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34);
/* 3974 */                                               out.write("\n      ");
/*      */                                               
/* 3976 */                                               OptionTag _jspx_th_html_005foption_005f35 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3977 */                                               _jspx_th_html_005foption_005f35.setPageContext(_jspx_page_context);
/* 3978 */                                               _jspx_th_html_005foption_005f35.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 3980 */                                               _jspx_th_html_005foption_005f35.setValue("WTA:55555");
/* 3981 */                                               int _jspx_eval_html_005foption_005f35 = _jspx_th_html_005foption_005f35.doStartTag();
/* 3982 */                                               if (_jspx_eval_html_005foption_005f35 != 0) {
/* 3983 */                                                 if (_jspx_eval_html_005foption_005f35 != 1) {
/* 3984 */                                                   out = _jspx_page_context.pushBody();
/* 3985 */                                                   _jspx_th_html_005foption_005f35.setBodyContent((BodyContent)out);
/* 3986 */                                                   _jspx_th_html_005foption_005f35.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3989 */                                                   out.print(FormatUtil.getString("Web Transactions"));
/* 3990 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f35.doAfterBody();
/* 3991 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3994 */                                                 if (_jspx_eval_html_005foption_005f35 != 1) {
/* 3995 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3998 */                                               if (_jspx_th_html_005foption_005f35.doEndTag() == 5) {
/* 3999 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35); return;
/*      */                                               }
/*      */                                               
/* 4002 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35);
/* 4003 */                                               out.write("\n      ");
/*      */                                               
/* 4005 */                                               OptionTag _jspx_th_html_005foption_005f36 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4006 */                                               _jspx_th_html_005foption_005f36.setPageContext(_jspx_page_context);
/* 4007 */                                               _jspx_th_html_005foption_005f36.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4009 */                                               _jspx_th_html_005foption_005f36.setValue("MAIL:25");
/* 4010 */                                               int _jspx_eval_html_005foption_005f36 = _jspx_th_html_005foption_005f36.doStartTag();
/* 4011 */                                               if (_jspx_eval_html_005foption_005f36 != 0) {
/* 4012 */                                                 if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4013 */                                                   out = _jspx_page_context.pushBody();
/* 4014 */                                                   _jspx_th_html_005foption_005f36.setBodyContent((BodyContent)out);
/* 4015 */                                                   _jspx_th_html_005foption_005f36.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4018 */                                                   out.print(FormatUtil.getString("Mail Server"));
/* 4019 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f36.doAfterBody();
/* 4020 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4023 */                                                 if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4024 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4027 */                                               if (_jspx_th_html_005foption_005f36.doEndTag() == 5) {
/* 4028 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36); return;
/*      */                                               }
/*      */                                               
/* 4031 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36);
/* 4032 */                                               out.write("\n      ");
/*      */                                               
/* 4034 */                                               OptionTag _jspx_th_html_005foption_005f37 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4035 */                                               _jspx_th_html_005foption_005f37.setPageContext(_jspx_page_context);
/* 4036 */                                               _jspx_th_html_005foption_005f37.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4038 */                                               _jspx_th_html_005foption_005f37.setValue("JMX1.2-MX4J-RMI:1099");
/* 4039 */                                               int _jspx_eval_html_005foption_005f37 = _jspx_th_html_005foption_005f37.doStartTag();
/* 4040 */                                               if (_jspx_eval_html_005foption_005f37 != 0) {
/* 4041 */                                                 if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4042 */                                                   out = _jspx_page_context.pushBody();
/* 4043 */                                                   _jspx_th_html_005foption_005f37.setBodyContent((BodyContent)out);
/* 4044 */                                                   _jspx_th_html_005foption_005f37.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4047 */                                                   out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 4048 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f37.doAfterBody();
/* 4049 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4052 */                                                 if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4053 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4056 */                                               if (_jspx_th_html_005foption_005f37.doEndTag() == 5) {
/* 4057 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37); return;
/*      */                                               }
/*      */                                               
/* 4060 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37);
/* 4061 */                                               out.write("\n      ");
/*      */                                               
/* 4063 */                                               OptionTag _jspx_th_html_005foption_005f38 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4064 */                                               _jspx_th_html_005foption_005f38.setPageContext(_jspx_page_context);
/* 4065 */                                               _jspx_th_html_005foption_005f38.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4067 */                                               _jspx_th_html_005foption_005f38.setValue("SERVICE:9090");
/* 4068 */                                               int _jspx_eval_html_005foption_005f38 = _jspx_th_html_005foption_005f38.doStartTag();
/* 4069 */                                               if (_jspx_eval_html_005foption_005f38 != 0) {
/* 4070 */                                                 if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4071 */                                                   out = _jspx_page_context.pushBody();
/* 4072 */                                                   _jspx_th_html_005foption_005f38.setBodyContent((BodyContent)out);
/* 4073 */                                                   _jspx_th_html_005foption_005f38.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4076 */                                                   out.write(32);
/* 4077 */                                                   out.print(FormatUtil.getString("Service Monitoring"));
/* 4078 */                                                   out.write(32);
/* 4079 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f38.doAfterBody();
/* 4080 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4083 */                                                 if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4084 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4087 */                                               if (_jspx_th_html_005foption_005f38.doEndTag() == 5) {
/* 4088 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38); return;
/*      */                                               }
/*      */                                               
/* 4091 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38);
/* 4092 */                                               out.write("\n      ");
/*      */                                               
/* 4094 */                                               OptionTag _jspx_th_html_005foption_005f39 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4095 */                                               _jspx_th_html_005foption_005f39.setPageContext(_jspx_page_context);
/* 4096 */                                               _jspx_th_html_005foption_005f39.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4098 */                                               _jspx_th_html_005foption_005f39.setValue("RMI:1099");
/* 4099 */                                               int _jspx_eval_html_005foption_005f39 = _jspx_th_html_005foption_005f39.doStartTag();
/* 4100 */                                               if (_jspx_eval_html_005foption_005f39 != 0) {
/* 4101 */                                                 if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4102 */                                                   out = _jspx_page_context.pushBody();
/* 4103 */                                                   _jspx_th_html_005foption_005f39.setBodyContent((BodyContent)out);
/* 4104 */                                                   _jspx_th_html_005foption_005f39.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4107 */                                                   out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 4108 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f39.doAfterBody();
/* 4109 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4112 */                                                 if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4113 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4116 */                                               if (_jspx_th_html_005foption_005f39.doEndTag() == 5) {
/* 4117 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39); return;
/*      */                                               }
/*      */                                               
/* 4120 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39);
/* 4121 */                                               out.write("\n      ");
/*      */                                               
/* 4123 */                                               OptionTag _jspx_th_html_005foption_005f40 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4124 */                                               _jspx_th_html_005foption_005f40.setPageContext(_jspx_page_context);
/* 4125 */                                               _jspx_th_html_005foption_005f40.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4127 */                                               _jspx_th_html_005foption_005f40.setValue("SNMP:161");
/* 4128 */                                               int _jspx_eval_html_005foption_005f40 = _jspx_th_html_005foption_005f40.doStartTag();
/* 4129 */                                               if (_jspx_eval_html_005foption_005f40 != 0) {
/* 4130 */                                                 if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4131 */                                                   out = _jspx_page_context.pushBody();
/* 4132 */                                                   _jspx_th_html_005foption_005f40.setBodyContent((BodyContent)out);
/* 4133 */                                                   _jspx_th_html_005foption_005f40.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4136 */                                                   out.print(FormatUtil.getString("SNMP"));
/* 4137 */                                                   out.write(" (V1 or V2c)");
/* 4138 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f40.doAfterBody();
/* 4139 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4142 */                                                 if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4143 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4146 */                                               if (_jspx_th_html_005foption_005f40.doEndTag() == 5) {
/* 4147 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40); return;
/*      */                                               }
/*      */                                               
/* 4150 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40);
/* 4151 */                                               out.write("\n      ");
/*      */                                               
/* 4153 */                                               OptionTag _jspx_th_html_005foption_005f41 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4154 */                                               _jspx_th_html_005foption_005f41.setPageContext(_jspx_page_context);
/* 4155 */                                               _jspx_th_html_005foption_005f41.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4157 */                                               _jspx_th_html_005foption_005f41.setValue("Custom-Application");
/* 4158 */                                               int _jspx_eval_html_005foption_005f41 = _jspx_th_html_005foption_005f41.doStartTag();
/* 4159 */                                               if (_jspx_eval_html_005foption_005f41 != 0) {
/* 4160 */                                                 if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4161 */                                                   out = _jspx_page_context.pushBody();
/* 4162 */                                                   _jspx_th_html_005foption_005f41.setBodyContent((BodyContent)out);
/* 4163 */                                                   _jspx_th_html_005foption_005f41.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4166 */                                                   out.write(32);
/* 4167 */                                                   out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4168 */                                                   out.write(32);
/* 4169 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f41.doAfterBody();
/* 4170 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4173 */                                                 if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4174 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4177 */                                               if (_jspx_th_html_005foption_005f41.doEndTag() == 5) {
/* 4178 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41); return;
/*      */                                               }
/*      */                                               
/* 4181 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41);
/* 4182 */                                               out.write("\n      ");
/*      */                                               
/* 4184 */                                               OptionTag _jspx_th_html_005foption_005f42 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4185 */                                               _jspx_th_html_005foption_005f42.setPageContext(_jspx_page_context);
/* 4186 */                                               _jspx_th_html_005foption_005f42.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4188 */                                               _jspx_th_html_005foption_005f42.setValue("Script Monitor");
/* 4189 */                                               int _jspx_eval_html_005foption_005f42 = _jspx_th_html_005foption_005f42.doStartTag();
/* 4190 */                                               if (_jspx_eval_html_005foption_005f42 != 0) {
/* 4191 */                                                 if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4192 */                                                   out = _jspx_page_context.pushBody();
/* 4193 */                                                   _jspx_th_html_005foption_005f42.setBodyContent((BodyContent)out);
/* 4194 */                                                   _jspx_th_html_005foption_005f42.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4197 */                                                   out.print(FormatUtil.getString("Script Monitor"));
/* 4198 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f42.doAfterBody();
/* 4199 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4202 */                                                 if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4203 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4206 */                                               if (_jspx_th_html_005foption_005f42.doEndTag() == 5) {
/* 4207 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42); return;
/*      */                                               }
/*      */                                               
/* 4210 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42);
/* 4211 */                                               out.write("\n       ");
/*      */ 
/*      */                                             }
/* 4214 */                                             else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("WINDOWS"))
/*      */                                             {
/*      */ 
/* 4217 */                                               out.write("\n        ");
/*      */                                               
/* 4219 */                                               OptionTag _jspx_th_html_005foption_005f43 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4220 */                                               _jspx_th_html_005foption_005f43.setPageContext(_jspx_page_context);
/* 4221 */                                               _jspx_th_html_005foption_005f43.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4223 */                                               _jspx_th_html_005foption_005f43.setValue("SYSTEM:9999");
/* 4224 */                                               int _jspx_eval_html_005foption_005f43 = _jspx_th_html_005foption_005f43.doStartTag();
/* 4225 */                                               if (_jspx_eval_html_005foption_005f43 != 0) {
/* 4226 */                                                 if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4227 */                                                   out = _jspx_page_context.pushBody();
/* 4228 */                                                   _jspx_th_html_005foption_005f43.setBodyContent((BodyContent)out);
/* 4229 */                                                   _jspx_th_html_005foption_005f43.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4232 */                                                   out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4233 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f43.doAfterBody();
/* 4234 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4237 */                                                 if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4238 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4241 */                                               if (_jspx_th_html_005foption_005f43.doEndTag() == 5) {
/* 4242 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43); return;
/*      */                                               }
/*      */                                               
/* 4245 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43);
/* 4246 */                                               out.write("\n       ");
/*      */                                               
/* 4248 */                                               OptionTag _jspx_th_html_005foption_005f44 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4249 */                                               _jspx_th_html_005foption_005f44.setPageContext(_jspx_page_context);
/* 4250 */                                               _jspx_th_html_005foption_005f44.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4252 */                                               _jspx_th_html_005foption_005f44.setValue(".Net:9080");
/* 4253 */                                               int _jspx_eval_html_005foption_005f44 = _jspx_th_html_005foption_005f44.doStartTag();
/* 4254 */                                               if (_jspx_eval_html_005foption_005f44 != 0) {
/* 4255 */                                                 if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4256 */                                                   out = _jspx_page_context.pushBody();
/* 4257 */                                                   _jspx_th_html_005foption_005f44.setBodyContent((BodyContent)out);
/* 4258 */                                                   _jspx_th_html_005foption_005f44.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4261 */                                                   out.print(FormatUtil.getString("Microsoft .NET"));
/* 4262 */                                                   out.write(32);
/* 4263 */                                                   out.write(32);
/* 4264 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f44.doAfterBody();
/* 4265 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4268 */                                                 if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4269 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4272 */                                               if (_jspx_th_html_005foption_005f44.doEndTag() == 5) {
/* 4273 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44); return;
/*      */                                               }
/*      */                                               
/* 4276 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44);
/* 4277 */                                               out.write("\n      ");
/*      */                                               
/* 4279 */                                               OptionTag _jspx_th_html_005foption_005f45 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4280 */                                               _jspx_th_html_005foption_005f45.setPageContext(_jspx_page_context);
/* 4281 */                                               _jspx_th_html_005foption_005f45.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4283 */                                               _jspx_th_html_005foption_005f45.setValue("MSSQLDB:1433");
/* 4284 */                                               int _jspx_eval_html_005foption_005f45 = _jspx_th_html_005foption_005f45.doStartTag();
/* 4285 */                                               if (_jspx_eval_html_005foption_005f45 != 0) {
/* 4286 */                                                 if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4287 */                                                   out = _jspx_page_context.pushBody();
/* 4288 */                                                   _jspx_th_html_005foption_005f45.setBodyContent((BodyContent)out);
/* 4289 */                                                   _jspx_th_html_005foption_005f45.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4292 */                                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4293 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f45.doAfterBody();
/* 4294 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4297 */                                                 if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4298 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4301 */                                               if (_jspx_th_html_005foption_005f45.doEndTag() == 5) {
/* 4302 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45); return;
/*      */                                               }
/*      */                                               
/* 4305 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45);
/* 4306 */                                               out.write("\n      ");
/*      */                                               
/* 4308 */                                               OptionTag _jspx_th_html_005foption_005f46 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4309 */                                               _jspx_th_html_005foption_005f46.setPageContext(_jspx_page_context);
/* 4310 */                                               _jspx_th_html_005foption_005f46.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4312 */                                               _jspx_th_html_005foption_005f46.setValue("Exchange:25");
/* 4313 */                                               int _jspx_eval_html_005foption_005f46 = _jspx_th_html_005foption_005f46.doStartTag();
/* 4314 */                                               if (_jspx_eval_html_005foption_005f46 != 0) {
/* 4315 */                                                 if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4316 */                                                   out = _jspx_page_context.pushBody();
/* 4317 */                                                   _jspx_th_html_005foption_005f46.setBodyContent((BodyContent)out);
/* 4318 */                                                   _jspx_th_html_005foption_005f46.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4321 */                                                   out.print(FormatUtil.getString("Exchange Server"));
/* 4322 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f46.doAfterBody();
/* 4323 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4326 */                                                 if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4327 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4330 */                                               if (_jspx_th_html_005foption_005f46.doEndTag() == 5) {
/* 4331 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46); return;
/*      */                                               }
/*      */                                               
/* 4334 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46);
/* 4335 */                                               out.write("\n\t  ");
/*      */                                               
/* 4337 */                                               OptionTag _jspx_th_html_005foption_005f47 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4338 */                                               _jspx_th_html_005foption_005f47.setPageContext(_jspx_page_context);
/* 4339 */                                               _jspx_th_html_005foption_005f47.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4341 */                                               _jspx_th_html_005foption_005f47.setValue("IIS:80");
/* 4342 */                                               int _jspx_eval_html_005foption_005f47 = _jspx_th_html_005foption_005f47.doStartTag();
/* 4343 */                                               if (_jspx_eval_html_005foption_005f47 != 0) {
/* 4344 */                                                 if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4345 */                                                   out = _jspx_page_context.pushBody();
/* 4346 */                                                   _jspx_th_html_005foption_005f47.setBodyContent((BodyContent)out);
/* 4347 */                                                   _jspx_th_html_005foption_005f47.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4350 */                                                   out.write(32);
/* 4351 */                                                   out.print(FormatUtil.getString("IIS Server"));
/* 4352 */                                                   out.write(32);
/* 4353 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f47.doAfterBody();
/* 4354 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4357 */                                                 if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4358 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4361 */                                               if (_jspx_th_html_005foption_005f47.doEndTag() == 5) {
/* 4362 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47); return;
/*      */                                               }
/*      */                                               
/* 4365 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47);
/* 4366 */                                               out.write("\n      ");
/*      */                                               
/* 4368 */                                               OptionTag _jspx_th_html_005foption_005f48 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4369 */                                               _jspx_th_html_005foption_005f48.setPageContext(_jspx_page_context);
/* 4370 */                                               _jspx_th_html_005foption_005f48.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4372 */                                               _jspx_th_html_005foption_005f48.setValue("SERVICE:9090");
/* 4373 */                                               int _jspx_eval_html_005foption_005f48 = _jspx_th_html_005foption_005f48.doStartTag();
/* 4374 */                                               if (_jspx_eval_html_005foption_005f48 != 0) {
/* 4375 */                                                 if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4376 */                                                   out = _jspx_page_context.pushBody();
/* 4377 */                                                   _jspx_th_html_005foption_005f48.setBodyContent((BodyContent)out);
/* 4378 */                                                   _jspx_th_html_005foption_005f48.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4381 */                                                   out.write(32);
/* 4382 */                                                   out.print(FormatUtil.getString("Service Monitoring"));
/* 4383 */                                                   out.write(32);
/* 4384 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f48.doAfterBody();
/* 4385 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4388 */                                                 if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4389 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4392 */                                               if (_jspx_th_html_005foption_005f48.doEndTag() == 5) {
/* 4393 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48); return;
/*      */                                               }
/*      */                                               
/* 4396 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48);
/* 4397 */                                               out.write("\n\t  ");
/*      */                                               
/* 4399 */                                               OptionTag _jspx_th_html_005foption_005f49 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4400 */                                               _jspx_th_html_005foption_005f49.setPageContext(_jspx_page_context);
/* 4401 */                                               _jspx_th_html_005foption_005f49.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4403 */                                               _jspx_th_html_005foption_005f49.setValue("SNMP:161");
/* 4404 */                                               int _jspx_eval_html_005foption_005f49 = _jspx_th_html_005foption_005f49.doStartTag();
/* 4405 */                                               if (_jspx_eval_html_005foption_005f49 != 0) {
/* 4406 */                                                 if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4407 */                                                   out = _jspx_page_context.pushBody();
/* 4408 */                                                   _jspx_th_html_005foption_005f49.setBodyContent((BodyContent)out);
/* 4409 */                                                   _jspx_th_html_005foption_005f49.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4412 */                                                   out.print(FormatUtil.getString("SNMP"));
/* 4413 */                                                   out.write(" (V1 or V2c)");
/* 4414 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f49.doAfterBody();
/* 4415 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4418 */                                                 if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4419 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4422 */                                               if (_jspx_th_html_005foption_005f49.doEndTag() == 5) {
/* 4423 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49); return;
/*      */                                               }
/*      */                                               
/* 4426 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49);
/* 4427 */                                               out.write("\n      ");
/*      */                                               
/* 4429 */                                               OptionTag _jspx_th_html_005foption_005f50 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4430 */                                               _jspx_th_html_005foption_005f50.setPageContext(_jspx_page_context);
/* 4431 */                                               _jspx_th_html_005foption_005f50.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4433 */                                               _jspx_th_html_005foption_005f50.setValue("Script Monitor");
/* 4434 */                                               int _jspx_eval_html_005foption_005f50 = _jspx_th_html_005foption_005f50.doStartTag();
/* 4435 */                                               if (_jspx_eval_html_005foption_005f50 != 0) {
/* 4436 */                                                 if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4437 */                                                   out = _jspx_page_context.pushBody();
/* 4438 */                                                   _jspx_th_html_005foption_005f50.setBodyContent((BodyContent)out);
/* 4439 */                                                   _jspx_th_html_005foption_005f50.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4442 */                                                   out.print(FormatUtil.getString("Script Monitor"));
/* 4443 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f50.doAfterBody();
/* 4444 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4447 */                                                 if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4448 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4451 */                                               if (_jspx_th_html_005foption_005f50.doEndTag() == 5) {
/* 4452 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50); return;
/*      */                                               }
/*      */                                               
/* 4455 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50);
/* 4456 */                                               out.write(10);
/*      */ 
/*      */                                             }
/* 4459 */                                             else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("DATABASE"))
/*      */                                             {
/*      */ 
/* 4462 */                                               out.write("\n\t\t</optgroup>\t<optgroup label=\"");
/* 4463 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 4464 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 4466 */                                               OptionTag _jspx_th_html_005foption_005f51 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4467 */                                               _jspx_th_html_005foption_005f51.setPageContext(_jspx_page_context);
/* 4468 */                                               _jspx_th_html_005foption_005f51.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4470 */                                               _jspx_th_html_005foption_005f51.setValue("SYSTEM:9999");
/* 4471 */                                               int _jspx_eval_html_005foption_005f51 = _jspx_th_html_005foption_005f51.doStartTag();
/* 4472 */                                               if (_jspx_eval_html_005foption_005f51 != 0) {
/* 4473 */                                                 if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4474 */                                                   out = _jspx_page_context.pushBody();
/* 4475 */                                                   _jspx_th_html_005foption_005f51.setBodyContent((BodyContent)out);
/* 4476 */                                                   _jspx_th_html_005foption_005f51.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4479 */                                                   out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4480 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f51.doAfterBody();
/* 4481 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4484 */                                                 if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4485 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4488 */                                               if (_jspx_th_html_005foption_005f51.doEndTag() == 5) {
/* 4489 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51); return;
/*      */                                               }
/*      */                                               
/* 4492 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51);
/* 4493 */                                               out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4494 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 4495 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 4497 */                                               OptionTag _jspx_th_html_005foption_005f52 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4498 */                                               _jspx_th_html_005foption_005f52.setPageContext(_jspx_page_context);
/* 4499 */                                               _jspx_th_html_005foption_005f52.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4501 */                                               _jspx_th_html_005foption_005f52.setValue("DB2:50000");
/* 4502 */                                               int _jspx_eval_html_005foption_005f52 = _jspx_th_html_005foption_005f52.doStartTag();
/* 4503 */                                               if (_jspx_eval_html_005foption_005f52 != 0) {
/* 4504 */                                                 if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4505 */                                                   out = _jspx_page_context.pushBody();
/* 4506 */                                                   _jspx_th_html_005foption_005f52.setBodyContent((BodyContent)out);
/* 4507 */                                                   _jspx_th_html_005foption_005f52.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4510 */                                                   out.print(FormatUtil.getString("am.webclient.db2.servertype"));
/* 4511 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f52.doAfterBody();
/* 4512 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4515 */                                                 if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4516 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4519 */                                               if (_jspx_th_html_005foption_005f52.doEndTag() == 5) {
/* 4520 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52); return;
/*      */                                               }
/*      */                                               
/* 4523 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52);
/* 4524 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 4526 */                                               OptionTag _jspx_th_html_005foption_005f53 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4527 */                                               _jspx_th_html_005foption_005f53.setPageContext(_jspx_page_context);
/* 4528 */                                               _jspx_th_html_005foption_005f53.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4530 */                                               _jspx_th_html_005foption_005f53.setValue("MSSQLDB:1433");
/* 4531 */                                               int _jspx_eval_html_005foption_005f53 = _jspx_th_html_005foption_005f53.doStartTag();
/* 4532 */                                               if (_jspx_eval_html_005foption_005f53 != 0) {
/* 4533 */                                                 if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4534 */                                                   out = _jspx_page_context.pushBody();
/* 4535 */                                                   _jspx_th_html_005foption_005f53.setBodyContent((BodyContent)out);
/* 4536 */                                                   _jspx_th_html_005foption_005f53.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4539 */                                                   out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4540 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f53.doAfterBody();
/* 4541 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4544 */                                                 if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4545 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4548 */                                               if (_jspx_th_html_005foption_005f53.doEndTag() == 5) {
/* 4549 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53); return;
/*      */                                               }
/*      */                                               
/* 4552 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53);
/* 4553 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 4555 */                                               OptionTag _jspx_th_html_005foption_005f54 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4556 */                                               _jspx_th_html_005foption_005f54.setPageContext(_jspx_page_context);
/* 4557 */                                               _jspx_th_html_005foption_005f54.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4559 */                                               _jspx_th_html_005foption_005f54.setValue("MYSQLDB:3306");
/* 4560 */                                               int _jspx_eval_html_005foption_005f54 = _jspx_th_html_005foption_005f54.doStartTag();
/* 4561 */                                               if (_jspx_eval_html_005foption_005f54 != 0) {
/* 4562 */                                                 if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4563 */                                                   out = _jspx_page_context.pushBody();
/* 4564 */                                                   _jspx_th_html_005foption_005f54.setBodyContent((BodyContent)out);
/* 4565 */                                                   _jspx_th_html_005foption_005f54.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4568 */                                                   out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 4569 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f54.doAfterBody();
/* 4570 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4573 */                                                 if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4574 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4577 */                                               if (_jspx_th_html_005foption_005f54.doEndTag() == 5) {
/* 4578 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54); return;
/*      */                                               }
/*      */                                               
/* 4581 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54);
/* 4582 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 4584 */                                               OptionTag _jspx_th_html_005foption_005f55 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4585 */                                               _jspx_th_html_005foption_005f55.setPageContext(_jspx_page_context);
/* 4586 */                                               _jspx_th_html_005foption_005f55.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4588 */                                               _jspx_th_html_005foption_005f55.setValue("ORACLEDB:1521");
/* 4589 */                                               int _jspx_eval_html_005foption_005f55 = _jspx_th_html_005foption_005f55.doStartTag();
/* 4590 */                                               if (_jspx_eval_html_005foption_005f55 != 0) {
/* 4591 */                                                 if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4592 */                                                   out = _jspx_page_context.pushBody();
/* 4593 */                                                   _jspx_th_html_005foption_005f55.setBodyContent((BodyContent)out);
/* 4594 */                                                   _jspx_th_html_005foption_005f55.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4597 */                                                   out.print(FormatUtil.getString("am.webclient.oracle.servertype"));
/* 4598 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f55.doAfterBody();
/* 4599 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4602 */                                                 if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4603 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4606 */                                               if (_jspx_th_html_005foption_005f55.doEndTag() == 5) {
/* 4607 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55); return;
/*      */                                               }
/*      */                                               
/* 4610 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55);
/* 4611 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 4613 */                                               OptionTag _jspx_th_html_005foption_005f56 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4614 */                                               _jspx_th_html_005foption_005f56.setPageContext(_jspx_page_context);
/* 4615 */                                               _jspx_th_html_005foption_005f56.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4617 */                                               _jspx_th_html_005foption_005f56.setValue("SYBASEDB:5000");
/* 4618 */                                               int _jspx_eval_html_005foption_005f56 = _jspx_th_html_005foption_005f56.doStartTag();
/* 4619 */                                               if (_jspx_eval_html_005foption_005f56 != 0) {
/* 4620 */                                                 if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4621 */                                                   out = _jspx_page_context.pushBody();
/* 4622 */                                                   _jspx_th_html_005foption_005f56.setBodyContent((BodyContent)out);
/* 4623 */                                                   _jspx_th_html_005foption_005f56.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4626 */                                                   out.print(FormatUtil.getString("Sybase"));
/* 4627 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f56.doAfterBody();
/* 4628 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4631 */                                                 if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4632 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4635 */                                               if (_jspx_th_html_005foption_005f56.doEndTag() == 5) {
/* 4636 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56); return;
/*      */                                               }
/*      */                                               
/* 4639 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56);
/* 4640 */                                               out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4641 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 4642 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 4644 */                                               OptionTag _jspx_th_html_005foption_005f57 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4645 */                                               _jspx_th_html_005foption_005f57.setPageContext(_jspx_page_context);
/* 4646 */                                               _jspx_th_html_005foption_005f57.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4648 */                                               _jspx_th_html_005foption_005f57.setValue("SERVICE:9090");
/* 4649 */                                               int _jspx_eval_html_005foption_005f57 = _jspx_th_html_005foption_005f57.doStartTag();
/* 4650 */                                               if (_jspx_eval_html_005foption_005f57 != 0) {
/* 4651 */                                                 if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4652 */                                                   out = _jspx_page_context.pushBody();
/* 4653 */                                                   _jspx_th_html_005foption_005f57.setBodyContent((BodyContent)out);
/* 4654 */                                                   _jspx_th_html_005foption_005f57.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4657 */                                                   out.write(32);
/* 4658 */                                                   out.print(FormatUtil.getString("Service Monitoring"));
/* 4659 */                                                   out.write(32);
/* 4660 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f57.doAfterBody();
/* 4661 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4664 */                                                 if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4665 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4668 */                                               if (_jspx_th_html_005foption_005f57.doEndTag() == 5) {
/* 4669 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57); return;
/*      */                                               }
/*      */                                               
/* 4672 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57);
/* 4673 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 4675 */                                               OptionTag _jspx_th_html_005foption_005f58 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4676 */                                               _jspx_th_html_005foption_005f58.setPageContext(_jspx_page_context);
/* 4677 */                                               _jspx_th_html_005foption_005f58.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4679 */                                               _jspx_th_html_005foption_005f58.setValue("SNMP:161");
/* 4680 */                                               int _jspx_eval_html_005foption_005f58 = _jspx_th_html_005foption_005f58.doStartTag();
/* 4681 */                                               if (_jspx_eval_html_005foption_005f58 != 0) {
/* 4682 */                                                 if (_jspx_eval_html_005foption_005f58 != 1) {
/* 4683 */                                                   out = _jspx_page_context.pushBody();
/* 4684 */                                                   _jspx_th_html_005foption_005f58.setBodyContent((BodyContent)out);
/* 4685 */                                                   _jspx_th_html_005foption_005f58.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4688 */                                                   out.print(FormatUtil.getString("SNMP"));
/* 4689 */                                                   out.write(" (V1 or V2c)");
/* 4690 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f58.doAfterBody();
/* 4691 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4694 */                                                 if (_jspx_eval_html_005foption_005f58 != 1) {
/* 4695 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4698 */                                               if (_jspx_th_html_005foption_005f58.doEndTag() == 5) {
/* 4699 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58); return;
/*      */                                               }
/*      */                                               
/* 4702 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58);
/* 4703 */                                               out.write("</optgroup>");
/* 4704 */                                               out.write("\n\t\t\t<optgroup label=\"");
/* 4705 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 4706 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 4708 */                                               OptionTag _jspx_th_html_005foption_005f59 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4709 */                                               _jspx_th_html_005foption_005f59.setPageContext(_jspx_page_context);
/* 4710 */                                               _jspx_th_html_005foption_005f59.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4712 */                                               _jspx_th_html_005foption_005f59.setValue("UrlMonitor");
/* 4713 */                                               int _jspx_eval_html_005foption_005f59 = _jspx_th_html_005foption_005f59.doStartTag();
/* 4714 */                                               if (_jspx_eval_html_005foption_005f59 != 0) {
/* 4715 */                                                 if (_jspx_eval_html_005foption_005f59 != 1) {
/* 4716 */                                                   out = _jspx_page_context.pushBody();
/* 4717 */                                                   _jspx_th_html_005foption_005f59.setBodyContent((BodyContent)out);
/* 4718 */                                                   _jspx_th_html_005foption_005f59.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4721 */                                                   out.print(FormatUtil.getString("HTTP-URLs"));
/* 4722 */                                                   out.write(32);
/* 4723 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f59.doAfterBody();
/* 4724 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4727 */                                                 if (_jspx_eval_html_005foption_005f59 != 1) {
/* 4728 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4731 */                                               if (_jspx_th_html_005foption_005f59.doEndTag() == 5) {
/* 4732 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59); return;
/*      */                                               }
/*      */                                               
/* 4735 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59);
/* 4736 */                                               out.write("\n\t\t\t");
/*      */                                               
/* 4738 */                                               OptionTag _jspx_th_html_005foption_005f60 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4739 */                                               _jspx_th_html_005foption_005f60.setPageContext(_jspx_page_context);
/* 4740 */                                               _jspx_th_html_005foption_005f60.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4742 */                                               _jspx_th_html_005foption_005f60.setValue("UrlSeq");
/* 4743 */                                               int _jspx_eval_html_005foption_005f60 = _jspx_th_html_005foption_005f60.doStartTag();
/* 4744 */                                               if (_jspx_eval_html_005foption_005f60 != 0) {
/* 4745 */                                                 if (_jspx_eval_html_005foption_005f60 != 1) {
/* 4746 */                                                   out = _jspx_page_context.pushBody();
/* 4747 */                                                   _jspx_th_html_005foption_005f60.setBodyContent((BodyContent)out);
/* 4748 */                                                   _jspx_th_html_005foption_005f60.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4751 */                                                   out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 4752 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f60.doAfterBody();
/* 4753 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4756 */                                                 if (_jspx_eval_html_005foption_005f60 != 1) {
/* 4757 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4760 */                                               if (_jspx_th_html_005foption_005f60.doEndTag() == 5) {
/* 4761 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60); return;
/*      */                                               }
/*      */                                               
/* 4764 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60);
/* 4765 */                                               out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4766 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 4767 */                                               out.write("\">\n\t\t\t");
/*      */                                               
/* 4769 */                                               OptionTag _jspx_th_html_005foption_005f61 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4770 */                                               _jspx_th_html_005foption_005f61.setPageContext(_jspx_page_context);
/* 4771 */                                               _jspx_th_html_005foption_005f61.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4773 */                                               _jspx_th_html_005foption_005f61.setValue("Script Monitor");
/* 4774 */                                               int _jspx_eval_html_005foption_005f61 = _jspx_th_html_005foption_005f61.doStartTag();
/* 4775 */                                               if (_jspx_eval_html_005foption_005f61 != 0) {
/* 4776 */                                                 if (_jspx_eval_html_005foption_005f61 != 1) {
/* 4777 */                                                   out = _jspx_page_context.pushBody();
/* 4778 */                                                   _jspx_th_html_005foption_005f61.setBodyContent((BodyContent)out);
/* 4779 */                                                   _jspx_th_html_005foption_005f61.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4782 */                                                   out.print(FormatUtil.getString("Script Monitor"));
/* 4783 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f61.doAfterBody();
/* 4784 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4787 */                                                 if (_jspx_eval_html_005foption_005f61 != 1) {
/* 4788 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4791 */                                               if (_jspx_th_html_005foption_005f61.doEndTag() == 5) {
/* 4792 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61); return;
/*      */                                               }
/*      */                                               
/* 4795 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61);
/* 4796 */                                               out.write(10);
/* 4797 */                                               out.write(10);
/*      */                                             }
/*      */                                             
/*      */ 
/* 4801 */                                             out.write("\n\n\n\n      ");
/* 4802 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 4803 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4806 */                                           if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4807 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4810 */                                         if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 4811 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                         }
/*      */                                         
/* 4814 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 4815 */                                         out.write("\n                      \n      ");
/* 4816 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 4817 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4821 */                                     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 4822 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                     }
/*      */                                     
/* 4825 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4826 */                                     out.write("\n      ");
/* 4827 */                                     if (_jspx_meth_c_005fif_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 4829 */                                     out.write("\n      </td>\n      \n      ");
/* 4830 */                                     if (request.getParameter("type") != null) {
/* 4831 */                                       isConfMonitor = confMonConfig.isConfMonitor(request.getParameter("type"));
/* 4832 */                                       String restype = request.getParameter("type");
/* 4833 */                                       if (restype.indexOf(":") != -1) {
/* 4834 */                                         restype = restype.substring(0, restype.indexOf(":"));
/*      */                                       }
/* 4836 */                                       if (((isConfMonitor) && (!restype.equals("QueryMonitor"))) || ((!restype.equals("JMX1.2-MX4J-RMI")) && (!restype.equals("Generic WMI")) && (!restype.equals("Script Monitor")) && (!restype.equals("Custom-Application")) && (!restype.equals("File System Monitor")) && (!restype.equals("QueryMonitor")) && (!restype.equals("SNMP")) && (!restype.equals("TELNET")) && (!restype.equals("Exchange")) && (!restype.equals("WTA")) && (!restype.equals("WEB")) && (!restype.equals("UrlSeq")) && (!restype.equals("PHP")) && (!restype.equals("IIS")) && (!restype.equals("APACHE")) && (!restype.equals("MAIL")) && (!restype.equals("All")) && (restype.indexOf("SAP") == -1))) {
/* 4837 */                                         out.write("\n      \t<td class=\"tableheading-monitor-config itadmin-hide\" align=\"right\">\n      \n      \t\t<div id=\"Conf-bulk-configuration\"> \n\t\t\t    \t\t<a href=\"javascript:void(0)\"  onclick=\"window.open('/BulkAddMonitors.do?method=showBulkImportForm&type=");
/* 4838 */                                         out.print(restype);
/* 4839 */                                         out.write("','windowName','toolbar=no,status=no,menubar=no,width=1000,height=500,scrollbars=yes')\" class=\"staticlinks\" >");
/* 4840 */                                         out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 4841 */                                         out.write("</a>\n\t    \t</div><img src=\"/images/script-icon.gif\">\n   \t   </td>\n      \n      \t");
/*      */                                       }
/*      */                                     }
/* 4844 */                                     out.write("     \n      \n  </tr>\n</table>\n <script src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script><script type=\"text/javascript\"> $(\".formtext\").chosen();  </script>\n");
/* 4845 */                                     out.write("\n\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"1\" class=\"lrborder\">\n<input type=\"hidden\" name=\"actionmethod\" value=\"createrbm\">\n<input type=\"hidden\" name=\"addtoha\" value=\"true\">\n<tr>\n    \t<td height=\"20\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 4846 */                                     out.print(FormatUtil.getString("am.webclient.newaction.displayname"));
/* 4847 */                                     out.write("<span class=\"mandatory\">*</span></label></td>\n\n\t\t<td  height=\"25\" class=\"bodytext\" >\n\t\t\t");
/* 4848 */                                     if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 4850 */                                     out.write("\n\t\t</td>\n\t</tr>\n\n\t\t<tr>\n\t\t<td width=\"25%\"  class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/* 4851 */                                     out.print(FormatUtil.getString("am.webclient.rbm.textmessage.webscripts"));
/* 4852 */                                     out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 4853 */                                     out.print(FormatUtil.getString("am.webclient.rbm.availablescripts.text"));
/* 4854 */                                     out.write("</a><span class=\"mandatory\">*</span></label></td>\n\t\t<td align=left  >\n\t\t");
/* 4855 */                                     if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 4857 */                                     out.write("&nbsp;\n\t\t<a href=\"#\" class=\"staticlinks\" onclick=\"javascript:openScriptManager('webscripttab');\">");
/* 4858 */                                     out.print(FormatUtil.getString("am.webclient.rbm.recordbutton.text"));
/* 4859 */                                     out.write("</a>\n\n\n\t\t\t</td>\n\t\t</tr>\n\t\t<!--tr>");
/* 4860 */                                     out.write(" \n\t\t<td width=\"20%\"  class=\"bodytext\" valign=top ><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 4861 */                                     out.print(FormatUtil.getString("am.webclient.rbm.textmessage.agent"));
/* 4862 */                                     out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 4863 */                                     out.print(FormatUtil.getString("am.webclient.rbm.availableagents.text"));
/* 4864 */                                     out.write(" </a><span class=\"mandatory\">*</span></td>\n\t\t<td align=left valign=top >\n\t\t<table width=100% cellspacing=0 cellpadding=0 border=0 >\n\t\t<tr>\n\t\t\t<td width=\"20%\" align='left'>\n\t\t");
/* 4865 */                                     if (_jspx_meth_html_005fselect_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 4867 */                                     out.write("\n\t\t</td>\n\t\t\t<td valign=top>&nbsp;\n\t\t<a href=\"#\" class=\"staticlinks\"  valign=top onclick=\"javascript:openScriptManager('agenttab');\"   >");
/* 4868 */                                     out.print(FormatUtil.getString("am.webclient.rbm.viewagent.text"));
/* 4869 */                                     out.write("</a>\n\t\t</td></tr>\n\t\t</table>\n\t  </td>\n</tr -->\n\n\t<tr>\n\t\t\t<td width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 4870 */                                     out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.pollinginterval"));
/* 4871 */                                     out.write(" <span class=\"mandatory\">*</span></label></td>\n\n\t\t<td  height=\"25\" class=\"bodytext\" >\n\t\t\t");
/* 4872 */                                     if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 4874 */                                     out.write(10);
/* 4875 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.minutes"));
/* 4876 */                                     out.write("\n\t\t</td>\n\t</tr>\n\n<tr>\n    \t<td width=\"25%\"  class=\"bodytext label-align addmonitor-label\" valign=top><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/* 4877 */                                     out.print(FormatUtil.getString("am.webclient.rbm.textmessage.timeout"));
/* 4878 */                                     out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 4879 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.timeout"));
/* 4880 */                                     out.write(" </a><span class=\"mandatory\">*</span></label></td>\n\n\t\t<td  height=\"25\" class=\"bodytext\" >\n\t\t\t");
/* 4881 */                                     if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 4883 */                                     out.write("\n\t\t\t");
/* 4884 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.minutes"));
/* 4885 */                                     out.write("\n\t\t</td>\n\t</tr>\n\n\n<TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"6\" CELLSPACING=\"0\" class=\"lrbborder  ");
/* 4886 */                                     out.print(hideStyle);
/* 4887 */                                     out.write("\" CELLPADING=\"4\">\n\t\t\t\t<tr>\n\t\t\t\t<td height=\"28\" colspan=\"4\" class=\"plainheading1\">");
/* 4888 */                                     out.print(FormatUtil.getString("am.webclient.newscript.associatemonitorinstance.text"));
/* 4889 */                                     out.write(32);
/* 4890 */                                     out.print(MGSTR);
/* 4891 */                                     out.write(" </td>\n\t\t\t\t</tr>\n\t\t        \t      \n        ");
/*      */                                     
/* 4893 */                                     if (EnterpriseUtil.isIt360MSPEdition())
/*      */                                     {
/* 4895 */                                       out.write("\n        <tr><td style=\"height:25px;\"></td></tr>\n\t    <tr>\n\t    \t<td width=\"25%\" height=\"28\" valign=\"middle\" class=\"bodytext\">");
/* 4896 */                                       out.print(FormatUtil.getString("it360.sp.customermgmt.customer.txt", new String[] { MGSTR }));
/* 4897 */                                       out.write("<span class=\"mandatory\">*</span></td>\n\t\t    <td height=\"28\" align=\"left\" >\n\t\t\t\t");
/*      */                                       
/* 4899 */                                       SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 4900 */                                       _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 4901 */                                       _jspx_th_html_005fselect_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 4903 */                                       _jspx_th_html_005fselect_005f3.setProperty("organization");
/*      */                                       
/* 4905 */                                       _jspx_th_html_005fselect_005f3.setStyleClass("formtext");
/*      */                                       
/* 4907 */                                       _jspx_th_html_005fselect_005f3.setOnchange("javascript:loadSite()");
/* 4908 */                                       int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 4909 */                                       if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 4910 */                                         if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 4911 */                                           out = _jspx_page_context.pushBody();
/* 4912 */                                           _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 4913 */                                           _jspx_th_html_005fselect_005f3.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 4916 */                                           out.write("\n\t\t      \t");
/*      */                                           
/* 4918 */                                           OptionTag _jspx_th_html_005foption_005f62 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4919 */                                           _jspx_th_html_005foption_005f62.setPageContext(_jspx_page_context);
/* 4920 */                                           _jspx_th_html_005foption_005f62.setParent(_jspx_th_html_005fselect_005f3);
/*      */                                           
/* 4922 */                                           _jspx_th_html_005foption_005f62.setValue("-");
/* 4923 */                                           int _jspx_eval_html_005foption_005f62 = _jspx_th_html_005foption_005f62.doStartTag();
/* 4924 */                                           if (_jspx_eval_html_005foption_005f62 != 0) {
/* 4925 */                                             if (_jspx_eval_html_005foption_005f62 != 1) {
/* 4926 */                                               out = _jspx_page_context.pushBody();
/* 4927 */                                               _jspx_th_html_005foption_005f62.setBodyContent((BodyContent)out);
/* 4928 */                                               _jspx_th_html_005foption_005f62.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 4931 */                                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.customer", new String[] { MGSTR }));
/* 4932 */                                               int evalDoAfterBody = _jspx_th_html_005foption_005f62.doAfterBody();
/* 4933 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 4936 */                                             if (_jspx_eval_html_005foption_005f62 != 1) {
/* 4937 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 4940 */                                           if (_jspx_th_html_005foption_005f62.doEndTag() == 5) {
/* 4941 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f62); return;
/*      */                                           }
/*      */                                           
/* 4944 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f62);
/* 4945 */                                           out.write("\n\t\t      \t");
/*      */                                           
/* 4947 */                                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4948 */                                           _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 4949 */                                           _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fselect_005f3);
/*      */                                           
/* 4951 */                                           _jspx_th_logic_005fnotEmpty_005f0.setName("customers");
/* 4952 */                                           int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 4953 */                                           if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                             for (;;) {
/* 4955 */                                               out.write(32);
/*      */                                               
/* 4957 */                                               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4958 */                                               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 4959 */                                               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                               
/* 4961 */                                               _jspx_th_logic_005fiterate_005f0.setName("customers");
/*      */                                               
/* 4963 */                                               _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                               
/* 4965 */                                               _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                                               
/* 4967 */                                               _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 4968 */                                               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 4969 */                                               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 4970 */                                                 ArrayList row = null;
/* 4971 */                                                 Integer j = null;
/* 4972 */                                                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 4973 */                                                   out = _jspx_page_context.pushBody();
/* 4974 */                                                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 4975 */                                                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                                 }
/* 4977 */                                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 4978 */                                                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                                 for (;;) {
/* 4980 */                                                   out.write("\n\t\t      \t");
/*      */                                                   
/* 4982 */                                                   OptionTag _jspx_th_html_005foption_005f63 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4983 */                                                   _jspx_th_html_005foption_005f63.setPageContext(_jspx_page_context);
/* 4984 */                                                   _jspx_th_html_005foption_005f63.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                                   
/* 4986 */                                                   _jspx_th_html_005foption_005f63.setValue((String)row.get(1));
/* 4987 */                                                   int _jspx_eval_html_005foption_005f63 = _jspx_th_html_005foption_005f63.doStartTag();
/* 4988 */                                                   if (_jspx_eval_html_005foption_005f63 != 0) {
/* 4989 */                                                     if (_jspx_eval_html_005foption_005f63 != 1) {
/* 4990 */                                                       out = _jspx_page_context.pushBody();
/* 4991 */                                                       _jspx_th_html_005foption_005f63.setBodyContent((BodyContent)out);
/* 4992 */                                                       _jspx_th_html_005foption_005f63.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4995 */                                                       out.print(row.get(0));
/* 4996 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f63.doAfterBody();
/* 4997 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5000 */                                                     if (_jspx_eval_html_005foption_005f63 != 1) {
/* 5001 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5004 */                                                   if (_jspx_th_html_005foption_005f63.doEndTag() == 5) {
/* 5005 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f63); return;
/*      */                                                   }
/*      */                                                   
/* 5008 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f63);
/* 5009 */                                                   out.write("\n\t\t      \t");
/* 5010 */                                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 5011 */                                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5012 */                                                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 5013 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5016 */                                                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 5017 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5020 */                                               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 5021 */                                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                               }
/*      */                                               
/* 5024 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 5025 */                                               out.write(32);
/* 5026 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 5027 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 5031 */                                           if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 5032 */                                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                           }
/*      */                                           
/* 5035 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 5036 */                                           out.write(32);
/* 5037 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 5038 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5041 */                                         if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 5042 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5045 */                                       if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 5046 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3); return;
/*      */                                       }
/*      */                                       
/* 5049 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3);
/* 5050 */                                       out.write("\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td width=\"25%\" height=\"28\" valign=\"middle\" class=\"bodytext\">");
/* 5051 */                                       out.print(FormatUtil.getString("it360.sp.customermgmt.site.txt", new String[] { MGSTR }));
/* 5052 */                                       out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t<td height=\"28\" align=\"left\" >\n\t\t\t\t");
/*      */                                       
/* 5054 */                                       SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5055 */                                       _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 5056 */                                       _jspx_th_html_005fselect_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 5058 */                                       _jspx_th_html_005fselect_005f4.setProperty("haid");
/*      */                                       
/* 5060 */                                       _jspx_th_html_005fselect_005f4.setStyleClass("formtext");
/*      */                                       
/* 5062 */                                       _jspx_th_html_005fselect_005f4.setOnchange("javascript:check()");
/* 5063 */                                       int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 5064 */                                       if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 5065 */                                         if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 5066 */                                           out = _jspx_page_context.pushBody();
/* 5067 */                                           _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 5068 */                                           _jspx_th_html_005fselect_005f4.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 5071 */                                           out.write("\n\t\t\t\t      ");
/*      */                                           
/* 5073 */                                           OptionTag _jspx_th_html_005foption_005f64 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5074 */                                           _jspx_th_html_005foption_005f64.setPageContext(_jspx_page_context);
/* 5075 */                                           _jspx_th_html_005foption_005f64.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                           
/* 5077 */                                           _jspx_th_html_005foption_005f64.setValue("-");
/* 5078 */                                           int _jspx_eval_html_005foption_005f64 = _jspx_th_html_005foption_005f64.doStartTag();
/* 5079 */                                           if (_jspx_eval_html_005foption_005f64 != 0) {
/* 5080 */                                             if (_jspx_eval_html_005foption_005f64 != 1) {
/* 5081 */                                               out = _jspx_page_context.pushBody();
/* 5082 */                                               _jspx_th_html_005foption_005f64.setBodyContent((BodyContent)out);
/* 5083 */                                               _jspx_th_html_005foption_005f64.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 5086 */                                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.siteGroup", new String[] { MGSTR }));
/* 5087 */                                               int evalDoAfterBody = _jspx_th_html_005foption_005f64.doAfterBody();
/* 5088 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 5091 */                                             if (_jspx_eval_html_005foption_005f64 != 1) {
/* 5092 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 5095 */                                           if (_jspx_th_html_005foption_005f64.doEndTag() == 5) {
/* 5096 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f64); return;
/*      */                                           }
/*      */                                           
/* 5099 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f64);
/* 5100 */                                           out.write("\n\t\t\t\t      ");
/*      */                                           
/* 5102 */                                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5103 */                                           _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 5104 */                                           _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                           
/* 5106 */                                           _jspx_th_logic_005fnotEmpty_005f1.setName("applications");
/* 5107 */                                           int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 5108 */                                           if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                             for (;;) {
/* 5110 */                                               out.write(32);
/*      */                                               
/* 5112 */                                               IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5113 */                                               _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 5114 */                                               _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                               
/* 5116 */                                               _jspx_th_logic_005fiterate_005f1.setName("applications");
/*      */                                               
/* 5118 */                                               _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                                               
/* 5120 */                                               _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                                               
/* 5122 */                                               _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/* 5123 */                                               int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 5124 */                                               if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 5125 */                                                 ArrayList row = null;
/* 5126 */                                                 Integer j = null;
/* 5127 */                                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5128 */                                                   out = _jspx_page_context.pushBody();
/* 5129 */                                                   _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 5130 */                                                   _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                                 }
/* 5132 */                                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5133 */                                                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                                 for (;;) {
/* 5135 */                                                   out.write("\n\t\t\t\t      ");
/*      */                                                   
/* 5137 */                                                   OptionTag _jspx_th_html_005foption_005f65 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5138 */                                                   _jspx_th_html_005foption_005f65.setPageContext(_jspx_page_context);
/* 5139 */                                                   _jspx_th_html_005foption_005f65.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                                   
/* 5141 */                                                   _jspx_th_html_005foption_005f65.setValue((String)row.get(1));
/* 5142 */                                                   int _jspx_eval_html_005foption_005f65 = _jspx_th_html_005foption_005f65.doStartTag();
/* 5143 */                                                   if (_jspx_eval_html_005foption_005f65 != 0) {
/* 5144 */                                                     if (_jspx_eval_html_005foption_005f65 != 1) {
/* 5145 */                                                       out = _jspx_page_context.pushBody();
/* 5146 */                                                       _jspx_th_html_005foption_005f65.setBodyContent((BodyContent)out);
/* 5147 */                                                       _jspx_th_html_005foption_005f65.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5150 */                                                       out.print(row.get(0));
/* 5151 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f65.doAfterBody();
/* 5152 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5155 */                                                     if (_jspx_eval_html_005foption_005f65 != 1) {
/* 5156 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5159 */                                                   if (_jspx_th_html_005foption_005f65.doEndTag() == 5) {
/* 5160 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f65); return;
/*      */                                                   }
/*      */                                                   
/* 5163 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f65);
/* 5164 */                                                   out.write("\n\t\t\t\t      ");
/* 5165 */                                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 5166 */                                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5167 */                                                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 5168 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5171 */                                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5172 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5175 */                                               if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 5176 */                                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                               }
/*      */                                               
/* 5179 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 5180 */                                               out.write(32);
/* 5181 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 5182 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 5186 */                                           if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 5187 */                                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                           }
/*      */                                           
/* 5190 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 5191 */                                           out.write(" \n\t\t\t\t");
/* 5192 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 5193 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5196 */                                         if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 5197 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5200 */                                       if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 5201 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4); return;
/*      */                                       }
/*      */                                       
/* 5204 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4);
/* 5205 */                                       out.write("\n      \t\t</td>\n      \t</tr>\n  ");
/*      */                                     } else {
/* 5207 */                                       out.write("\n\t\t<tr height=\"35\">\n\t    <td width=\"25%\" style=\"padding-left:10px;\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5208 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.monitorgroupselect", new String[] { MGSTR }));
/* 5209 */                                       out.write("</label></td>\n\t    <td  align=\"left\" width=\"75%\">\n\t\t\t<table width=\"100%\" BORDER=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr height=\"35\">\n\t\t\t\t\t<td width=\"25%\" style=\"padding-left:5px;\" >\n\t\t\t\t\t\t");
/*      */                                       
/* 5211 */                                       SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5212 */                                       _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 5213 */                                       _jspx_th_html_005fselect_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 5215 */                                       _jspx_th_html_005fselect_005f5.setProperty("haid");
/*      */                                       
/* 5217 */                                       _jspx_th_html_005fselect_005f5.setStyleClass("formtext default");
/*      */                                       
/* 5219 */                                       _jspx_th_html_005fselect_005f5.setOnchange("javascript:check()");
/* 5220 */                                       int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 5221 */                                       if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 5222 */                                         if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 5223 */                                           out = _jspx_page_context.pushBody();
/* 5224 */                                           _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 5225 */                                           _jspx_th_html_005fselect_005f5.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 5228 */                                           out.write("\n\t\t\t\t      \t\t");
/*      */                                           
/* 5230 */                                           OptionTag _jspx_th_html_005foption_005f66 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5231 */                                           _jspx_th_html_005foption_005f66.setPageContext(_jspx_page_context);
/* 5232 */                                           _jspx_th_html_005foption_005f66.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                           
/* 5234 */                                           _jspx_th_html_005foption_005f66.setValue("-");
/* 5235 */                                           int _jspx_eval_html_005foption_005f66 = _jspx_th_html_005foption_005f66.doStartTag();
/* 5236 */                                           if (_jspx_eval_html_005foption_005f66 != 0) {
/* 5237 */                                             if (_jspx_eval_html_005foption_005f66 != 1) {
/* 5238 */                                               out = _jspx_page_context.pushBody();
/* 5239 */                                               _jspx_th_html_005foption_005f66.setBodyContent((BodyContent)out);
/* 5240 */                                               _jspx_th_html_005foption_005f66.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 5243 */                                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.monitorgroup", new String[] { MGSTR }));
/* 5244 */                                               int evalDoAfterBody = _jspx_th_html_005foption_005f66.doAfterBody();
/* 5245 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 5248 */                                             if (_jspx_eval_html_005foption_005f66 != 1) {
/* 5249 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 5252 */                                           if (_jspx_th_html_005foption_005f66.doEndTag() == 5) {
/* 5253 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f66); return;
/*      */                                           }
/*      */                                           
/* 5256 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f66);
/* 5257 */                                           out.write("\n\t\t\t\t      \t\t");
/*      */                                           
/* 5259 */                                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5260 */                                           _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 5261 */                                           _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                           
/* 5263 */                                           _jspx_th_logic_005fnotEmpty_005f2.setName("applications");
/* 5264 */                                           int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 5265 */                                           if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                                             for (;;) {
/* 5267 */                                               out.write(" \n\t\t\t\t      \t\t");
/*      */                                               
/* 5269 */                                               IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5270 */                                               _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 5271 */                                               _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                               
/* 5273 */                                               _jspx_th_logic_005fiterate_005f2.setName("applications");
/*      */                                               
/* 5275 */                                               _jspx_th_logic_005fiterate_005f2.setId("row");
/*      */                                               
/* 5277 */                                               _jspx_th_logic_005fiterate_005f2.setIndexId("j");
/*      */                                               
/* 5279 */                                               _jspx_th_logic_005fiterate_005f2.setType("java.util.ArrayList");
/* 5280 */                                               int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 5281 */                                               if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 5282 */                                                 ArrayList row = null;
/* 5283 */                                                 Integer j = null;
/* 5284 */                                                 if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 5285 */                                                   out = _jspx_page_context.pushBody();
/* 5286 */                                                   _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 5287 */                                                   _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                                                 }
/* 5289 */                                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5290 */                                                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                                 for (;;) {
/* 5292 */                                                   out.write("\n\t\t\t\t      \t\t\t");
/*      */                                                   
/* 5294 */                                                   OptionTag _jspx_th_html_005foption_005f67 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5295 */                                                   _jspx_th_html_005foption_005f67.setPageContext(_jspx_page_context);
/* 5296 */                                                   _jspx_th_html_005foption_005f67.setParent(_jspx_th_logic_005fiterate_005f2);
/*      */                                                   
/* 5298 */                                                   _jspx_th_html_005foption_005f67.setValue((String)row.get(1));
/* 5299 */                                                   int _jspx_eval_html_005foption_005f67 = _jspx_th_html_005foption_005f67.doStartTag();
/* 5300 */                                                   if (_jspx_eval_html_005foption_005f67 != 0) {
/* 5301 */                                                     if (_jspx_eval_html_005foption_005f67 != 1) {
/* 5302 */                                                       out = _jspx_page_context.pushBody();
/* 5303 */                                                       _jspx_th_html_005foption_005f67.setBodyContent((BodyContent)out);
/* 5304 */                                                       _jspx_th_html_005foption_005f67.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5307 */                                                       out.print(row.get(0));
/* 5308 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f67.doAfterBody();
/* 5309 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5312 */                                                     if (_jspx_eval_html_005foption_005f67 != 1) {
/* 5313 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5316 */                                                   if (_jspx_th_html_005foption_005f67.doEndTag() == 5) {
/* 5317 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f67); return;
/*      */                                                   }
/*      */                                                   
/* 5320 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f67);
/* 5321 */                                                   out.write("\n\t\t\t\t      \t\t");
/* 5322 */                                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 5323 */                                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5324 */                                                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 5325 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5328 */                                                 if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 5329 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5332 */                                               if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 5333 */                                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                                               }
/*      */                                               
/* 5336 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 5337 */                                               out.write(" \n\t\t\t\t      \t\t");
/* 5338 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 5339 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 5343 */                                           if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 5344 */                                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                                           }
/*      */                                           
/* 5347 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 5348 */                                           out.write(" \n\t\t\t\t      \t");
/* 5349 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 5350 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5353 */                                         if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 5354 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5357 */                                       if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 5358 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f5); return;
/*      */                                       }
/*      */                                       
/* 5361 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f5);
/* 5362 */                                       out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td>\n\t\t\t\t\t\t<table BORDER=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td colspan=\"2\">\n\t\t\t\t\t\t\t\t<div id=\"group\" width=\"15%\" nowrap=\"nowrap\" style=\"padding-left:20px;white-space: nowrap;\"><a href=\"#\" class=\"staticlinks\" onclick=\"javascript:hideDiv('group');hideDiv('createsubgroup');hideDiv('groupmessage');showDiv('creategroup');resetname('groupname'); return false;\">");
/* 5363 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.browsertitle"));
/* 5364 */                                       out.write("</a></div>\n\t\t\t\t\t\t\t\t<div id=\"subgroup\" width=\"25%\" nowrap=\"nowrap\" Style=\"Display:none; padding-left:20px;white-space: nowrap;\"><a href=\"#\" class=\"staticlinks\" onclick=\"javascript:hideDiv('subgroup');hideDiv('creategroup');hideDiv('groupmessage');showDiv('createsubgroup');resetname('subgroupname'); return false;\">");
/* 5365 */                                       out.print(FormatUtil.getString("am.webclient.monitorsubgroupfirst.browsertitle"));
/* 5366 */                                       out.write("</a></div>\n\t\t\t\t\t\t\t\t<div id=\"creategroup\" style=\"display:none;padding-left:20px;\">\n\t\t\t\t\t\t\t\t\t<span class=\"bodytext\">");
/* 5367 */                                       out.print(FormatUtil.getString("webclient.map.mapsymboldetails.groupname"));
/* 5368 */                                       out.write(":</span>\n\t\t\t\t\t\t\t\t\t<input name=\"groupname\" type=\"text\" class=\"formtext medium\">\n\t\t\t\t\t\t\t\t\t<input name=\"Create\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 5369 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.create"));
/* 5370 */                                       out.write("\" onclick=\"javascript:createGroup();\"> <input name=\"cancel\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 5371 */                                       out.print(FormatUtil.getString("Cancel"));
/* 5372 */                                       out.write("\" onclick=\"javascript:hideDiv('creategroup'); showDiv('group'); return false;\">\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t<div id=\"createsubgroup\" style=\"display:none;padding-left:20px;\">\n\t\t\t\t\t\t\t\t\t<span class=\"bodytext\">");
/* 5373 */                                       out.print(FormatUtil.getString("webclient.map.mapsymboldetails.subgroupname"));
/* 5374 */                                       out.write(":</span>\n\t\t\t\t\t\t\t\t\t<input name=\"subgroupname\" type=\"text\" class=\"formtext medium\">\n\t\t\t\t\t\t\t\t\t<input name=\"Create\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 5375 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.create"));
/* 5376 */                                       out.write("\" onclick=\"javascript:createsubGroup();\"> <input name=\"cancel\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 5377 */                                       out.print(FormatUtil.getString("Cancel"));
/* 5378 */                                       out.write("\" onclick=\"javascript:hideDiv('createsubgroup'); showDiv('subgroup'); return false;\">\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t<div id=\"groupmessage\" style=\"display:block; padding-left:20px;\" class='error-text'></div>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n ");
/*      */                                     }
/* 5380 */                                     out.write("\n<tr>\n\t\t\t<td align=\"left\" width=\"99%\" colspan=\"3\">\n\t\t\t\t");
/* 5381 */                                     out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n");
/*      */                                     
/* 5383 */                                     String isRBM = (String)request.getAttribute("isRBM");
/* 5384 */                                     String selQuery = "select AGENTID,DISPLAYNAME,STATUS,DESCRIPTION,AGENTVERSION from AM_RBMAGENTDATA where AGENTID >" + EnterpriseUtil.getDistributedStartResourceId();
/* 5385 */                                     if ((isRBM != null) && (isRBM.equals("true")))
/*      */                                     {
/* 5387 */                                       selQuery = "select AGENTID,DISPLAYNAME,STATUS,DESCRIPTION,AGENTVERSION from AM_RBMAGENTDATA where AGENTID NOT IN(" + EnterpriseUtil.getDistributedStartResourceId() + ") and AGENTNAME NOT LIKE ('%(Local)')";
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 5391 */                                       isRBM = "false";
/*      */                                     }
/* 5393 */                                     System.out.println("is RBM : " + isRBM);
/* 5394 */                                     ArrayList agentLocation = new ArrayList();
/* 5395 */                                     ArrayList agentStatus = new ArrayList();
/* 5396 */                                     ArrayList<String> isDisabled = new ArrayList();
/* 5397 */                                     ResultSet rs = AMConnectionPool.executeQueryStmt(selQuery);
/* 5398 */                                     while (rs.next())
/*      */                                     {
/* 5400 */                                       String agentId = rs.getString("AGENTID");
/* 5401 */                                       String displayName = FormatUtil.getTrimmedText(rs.getString("DISPLAYNAME"), 23);
/* 5402 */                                       String version = rs.getString("AGENTVERSION");
/* 5403 */                                       if (isRBM.equals("true"))
/*      */                                       {
/* 5405 */                                         String browserType = "1";
/* 5406 */                                         Properties argProps = (Properties)request.getAttribute("argsasprops");
/*      */                                         try {
/* 5408 */                                           browserType = argProps.getProperty("browserType");
/*      */                                         }
/*      */                                         catch (NullPointerException npe) {}
/* 5411 */                                         String desc = rs.getString("DESCRIPTION");
/* 5412 */                                         if (("1".equals(browserType)) && (version != null) && (Integer.parseInt(version.replace(".", "")) <= com.adventnet.appmanager.util.Constants.eumAppAgent))
/*      */                                         {
/* 5414 */                                           isDisabled.add("true");
/*      */                                         } else {
/* 5416 */                                           isDisabled.add("false");
/*      */                                         }
/*      */                                       } else {
/* 5419 */                                         isDisabled.add("false");
/*      */                                       }
/* 5421 */                                       agentLocation.add(new org.apache.struts.util.LabelValueBean(displayName, agentId));
/* 5422 */                                       agentStatus.add(rs.getString("STATUS"));
/*      */                                     }
/*      */                                     
/* 5425 */                                     pageContext.setAttribute("agentLocation", agentLocation);
/* 5426 */                                     String disable = "";
/* 5427 */                                     String licenseTooltip = "";
/* 5428 */                                     String hideClass = "";
/* 5429 */                                     if ((!FreeEditionDetails.getFreeEditionDetails().isEUMAllowed()) || (EnterpriseUtil.isCloudEdition())) {
/* 5430 */                                       disable = "disabled='disabled'";
/* 5431 */                                       licenseTooltip = FormatUtil.getString("am.webclient.eumdashboard.license.addon");
/* 5432 */                                       hideClass = "hideddrivetip()";
/*      */                                     }
/*      */                                     
/*      */ 
/* 5436 */                                     out.write("\n\n\n<style type=\"text/css\">\n.upgradeAmountBg1 {\n\tbackground-color: #f7f7f7;\n}\n\n.labeltd {\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-size: 11px;\n\tcolor: #333333;\n\theight: 25px;\n\tpadding-left: 10px;\n}\n</style>\n\n\n<table width='");
/* 5437 */                                     out.print(fromWhere.equals("CONF") ? "99%" : "100%");
/* 5438 */                                     out.write("' border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class='");
/* 5439 */                                     out.print(fromWhere.equals("CONF") ? "lrbborder" : "");
/* 5440 */                                     out.write("'>\n\t\n\t<tr>\n\t\t<td height=\"25\" class=\"plainheading1\">");
/* 5441 */                                     out.print(FormatUtil.getString("am.webclient.eumagent.associate"));
/* 5442 */                                     out.write("</td>\n\t</tr>\n\t<tr height=\"25\">\n\t\t<td style=\"font-size: 12px;padding: 10px;\" nowrap>\n\t\t\t");
/* 5443 */                                     if (fromWhere.equals("CONF")) {
/* 5444 */                                       out.write("\n\t\t\t");
/*      */                                       
/* 5446 */                                       IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5447 */                                       _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 5448 */                                       _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 5450 */                                       _jspx_th_c_005fif_005f5.setTest("${!isRBM}");
/* 5451 */                                       int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 5452 */                                       if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                         for (;;) {
/* 5454 */                                           out.write("\n\t\t\t\t<span style=\"padding-left:10px;\" class=\"bcactive\"><input style=\"position:relative;\" type=\"checkbox\" name=\"runOnServer\" value=\"runOnServer\" checked='checked'/>");
/* 5455 */                                           out.print(FormatUtil.getString("am.webclient.eumagent.runonserver"));
/* 5456 */                                           out.write("</span>\n\t\t\t");
/* 5457 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 5458 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 5462 */                                       if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 5463 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                       }
/*      */                                       
/* 5466 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5467 */                                       out.write("\n\t\t\t<span style=\"padding-left:10px;\" class=\"bcactive\"  onMouseOver=\"ddrivetip(this,event,'");
/* 5468 */                                       out.print(FormatUtil.getString(licenseTooltip));
/* 5469 */                                       out.write("',false,true,'#000000',150,'lightyellow')\" onmouseout=\"");
/* 5470 */                                       out.print(licenseTooltip.equals("") ? "" : "hideddrivetip();");
/* 5471 */                                       out.write("\">\t\t\t\t\n\t\t\t\t<input style=\"position:relative;\" type=\"checkbox\" class=\"agentLocationCheckbox\" name=\"runOnAgent\" id=\"runOnAgent\" value=\"runOnAgent\" ");
/* 5472 */                                       out.print(disable);
/* 5473 */                                       out.write("/>\t\t\t\n\t\t\t\t");
/* 5474 */                                       out.print(FormatUtil.getString("am.webclient.eumagent.runonagent"));
/* 5475 */                                       out.write("&nbsp;&nbsp;(");
/* 5476 */                                       out.print(FormatUtil.getString("am.webclient.common.eum"));
/* 5477 */                                       out.write(41);
/* 5478 */                                       if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 5479 */                                         out.write("&nbsp;<img border=\"0\" align=\"middle\" title=\"Add On is Enabled for Evaluation / Trial Versions\" style=\"position: relative; bottom: 2px;\" src=\"/images/icon_addon.gif\"></span>");
/*      */                                       }
/* 5481 */                                       out.write("\n\t\t\t\t&nbsp;<span>");
/* 5482 */                                       out.print(FormatUtil.getString("am.webclient.eum.learneumlink"));
/* 5483 */                                       out.write("\n\t\t\t\t\n\t\t\t</span>\n\t\t\t<div class=\"agentLoactionDiv\" style=\"padding: 10px;display: none;width: 99%;\">\n\t\t\t");
/*      */                                     } else {
/* 5485 */                                       out.write("\n\t\t\t\t<div class=\"agentLoactionDiv\" style=\"padding: 10px;display: block;width: 99%;\">\n\t\t\t\t\n\t\t\t");
/*      */                                     }
/* 5487 */                                     if (!agentLocation.isEmpty()) {
/* 5488 */                                       out.write("\n\t\t\t\t<table class=\"bodytext upgradeAmountBg1\" width=\"100%\">\n\t\t\t\t\t<tr><td>\n\t\t\t\t\t<table class=\"bodytext upgradeAmountBg1\" width=\"55%\">\n\t\t\t\t\t\n\t\t\t\t\t<tr height=\"28\">\n\t\t\t\t\t\t<span class=\"bodytext\">\n\t\t\t\t\t\t");
/*      */                                       
/* 5490 */                                       CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick.get(CheckboxTag.class);
/* 5491 */                                       _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 5492 */                                       _jspx_th_html_005fcheckbox_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 5494 */                                       _jspx_th_html_005fcheckbox_005f0.setProperty("selectAllAgents");
/*      */                                       
/* 5496 */                                       _jspx_th_html_005fcheckbox_005f0.setOnclick("toggleChecked(this.checked)");
/* 5497 */                                       int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 5498 */                                       if (_jspx_eval_html_005fcheckbox_005f0 != 0) {
/* 5499 */                                         if (_jspx_eval_html_005fcheckbox_005f0 != 1) {
/* 5500 */                                           out = _jspx_page_context.pushBody();
/* 5501 */                                           _jspx_th_html_005fcheckbox_005f0.setBodyContent((BodyContent)out);
/* 5502 */                                           _jspx_th_html_005fcheckbox_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 5505 */                                           out.print(FormatUtil.getString("am.webclient.eumagent.selectall"));
/* 5506 */                                           int evalDoAfterBody = _jspx_th_html_005fcheckbox_005f0.doAfterBody();
/* 5507 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5510 */                                         if (_jspx_eval_html_005fcheckbox_005f0 != 1) {
/* 5511 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5514 */                                       if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 5515 */                                         this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick.reuse(_jspx_th_html_005fcheckbox_005f0); return;
/*      */                                       }
/*      */                                       
/* 5518 */                                       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 5519 */                                       out.write("</span><br><br>\n\t\t\t\t\t\t");
/*      */                                       
/* 5521 */                                       IterateTag _jspx_th_logic_005fiterate_005f3 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 5522 */                                       _jspx_th_logic_005fiterate_005f3.setPageContext(_jspx_page_context);
/* 5523 */                                       _jspx_th_logic_005fiterate_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 5525 */                                       _jspx_th_logic_005fiterate_005f3.setName("agentLocation");
/*      */                                       
/* 5527 */                                       _jspx_th_logic_005fiterate_005f3.setId("userAgent");
/*      */                                       
/* 5529 */                                       _jspx_th_logic_005fiterate_005f3.setIndexId("j");
/* 5530 */                                       int _jspx_eval_logic_005fiterate_005f3 = _jspx_th_logic_005fiterate_005f3.doStartTag();
/* 5531 */                                       if (_jspx_eval_logic_005fiterate_005f3 != 0) {
/* 5532 */                                         Object userAgent = null;
/* 5533 */                                         Integer j = null;
/* 5534 */                                         if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 5535 */                                           out = _jspx_page_context.pushBody();
/* 5536 */                                           _jspx_th_logic_005fiterate_005f3.setBodyContent((BodyContent)out);
/* 5537 */                                           _jspx_th_logic_005fiterate_005f3.doInitBody();
/*      */                                         }
/* 5539 */                                         userAgent = _jspx_page_context.findAttribute("userAgent");
/* 5540 */                                         j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                         for (;;) {
/* 5542 */                                           out.write("\n\t\t\t\t\t\t\t");
/*      */                                           
/* 5544 */                                           String textclass = "color='black'";
/* 5545 */                                           String status = "";
/* 5546 */                                           if (!((String)agentStatus.get(j.intValue())).equals("0")) {
/* 5547 */                                             textclass = "color='red'";
/* 5548 */                                             status = FormatUtil.getString("am.webclient.eumdashboard.agentdown");
/*      */                                           }
/*      */                                           
/*      */ 
/* 5552 */                                           out.write("\n\t\t\t\t\t\t\t<td nowrap=\"nowrap\" style=\"padding-left:18px;\">");
/*      */                                           
/* 5554 */                                           MultiboxTag _jspx_th_html_005fmultibox_005f0 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled.get(MultiboxTag.class);
/* 5555 */                                           _jspx_th_html_005fmultibox_005f0.setPageContext(_jspx_page_context);
/* 5556 */                                           _jspx_th_html_005fmultibox_005f0.setParent(_jspx_th_logic_005fiterate_005f3);
/*      */                                           
/* 5558 */                                           _jspx_th_html_005fmultibox_005f0.setProperty("selectedAgents");
/*      */                                           
/* 5560 */                                           _jspx_th_html_005fmultibox_005f0.setOnclick("javascript:findSelectedIndex('" + j + "');");
/*      */                                           
/* 5562 */                                           _jspx_th_html_005fmultibox_005f0.setDisabled(Boolean.parseBoolean((String)isDisabled.get(j.intValue())));
/* 5563 */                                           int _jspx_eval_html_005fmultibox_005f0 = _jspx_th_html_005fmultibox_005f0.doStartTag();
/* 5564 */                                           if (_jspx_eval_html_005fmultibox_005f0 != 0) {
/* 5565 */                                             if (_jspx_eval_html_005fmultibox_005f0 != 1) {
/* 5566 */                                               out = _jspx_page_context.pushBody();
/* 5567 */                                               _jspx_th_html_005fmultibox_005f0.setBodyContent((BodyContent)out);
/* 5568 */                                               _jspx_th_html_005fmultibox_005f0.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 5571 */                                               out.write("\n\t\t\t\t\t\t\t\t");
/* 5572 */                                               if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmultibox_005f0, _jspx_page_context))
/*      */                                                 return;
/* 5574 */                                               out.write("\n\t\t\t\t\t\t\t\t");
/* 5575 */                                               int evalDoAfterBody = _jspx_th_html_005fmultibox_005f0.doAfterBody();
/* 5576 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 5579 */                                             if (_jspx_eval_html_005fmultibox_005f0 != 1) {
/* 5580 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 5583 */                                           if (_jspx_th_html_005fmultibox_005f0.doEndTag() == 5) {
/* 5584 */                                             this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled.reuse(_jspx_th_html_005fmultibox_005f0); return;
/*      */                                           }
/*      */                                           
/* 5587 */                                           this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled.reuse(_jspx_th_html_005fmultibox_005f0);
/* 5588 */                                           out.write("\n\t\t\t\t\t\t\t\t<a style=\"cursor:pointer\" class=\"tooltip\" onMouseOver=\"ddrivetip(this,event,'");
/* 5589 */                                           out.print(status);
/* 5590 */                                           out.write("',false,true,'#000000',100,'lightyellow')\" onmouseout='");
/* 5591 */                                           out.print(status.equals("") ? "" : "hideddrivetip()");
/* 5592 */                                           out.write("'>\n\t\t\t\t\t\t\t\t\t<font ");
/* 5593 */                                           out.print(textclass);
/* 5594 */                                           out.write(62);
/* 5595 */                                           if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_logic_005fiterate_005f3, _jspx_page_context))
/*      */                                             return;
/* 5597 */                                           out.write("</font>\n\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t");
/* 5598 */                                           if (_jspx_meth_c_005fif_005f6(_jspx_th_logic_005fiterate_005f3, _jspx_page_context))
/*      */                                             return;
/* 5600 */                                           out.write("\n\t\t\t\t\t\t");
/* 5601 */                                           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f3.doAfterBody();
/* 5602 */                                           userAgent = _jspx_page_context.findAttribute("userAgent");
/* 5603 */                                           j = (Integer)_jspx_page_context.findAttribute("j");
/* 5604 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5607 */                                         if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 5608 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5611 */                                       if (_jspx_th_logic_005fiterate_005f3.doEndTag() == 5) {
/* 5612 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3); return;
/*      */                                       }
/*      */                                       
/* 5615 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3);
/* 5616 */                                       out.write("\n\t\t\t\t\t</table></td></tr>\t\n\t\t\t\t</table>\n\t\t\t\t\n\t\t\t\t\n\t\t\t\t");
/*      */                                     } else {
/* 5618 */                                       out.write("\n\t\t\t\t\t<table class=\"bodytext upgradeAmountBg1\" width=\"100%\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td><span class=\"bodytext\">");
/* 5619 */                                       out.print(FormatUtil.getString("am.webclient.eumdashboard.noagents"));
/* 5620 */                                       out.write("</span></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t");
/*      */                                     }
/* 5622 */                                     out.write("\n\t\t\t\t\n\t\t\t</div>\n\t\t</td>\n\t</tr>\n\t<tr>\n\t</tr>\n</table>\n\n<script>\n\n$(document).ready(function(){\n\t$('.agentLocationCheckbox').click(function() //NO I18N \n   \t{\n\t\tif($(this).attr(\"checked\"))\n\t\t{\n\t\t\t");
/*      */                                     
/* 5624 */                                     if (EnterpriseUtil.isAdminServer())
/*      */                                     {
/* 5626 */                                       out.write("\t\n\t\t\t\t\tvar masObj = document.AMActionForm.selectedServer;\t\t\t\t\t\n\t\t\t\t\tif(masObj != 'undefined' && masObj.value == '-')\n\t\t\t\t\t{\n\t\t\t\t\t\talert('");
/* 5627 */                                       out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.mas.text"));
/* 5628 */                                       out.write("');\n\t\t\t\t\t\treturn false;\n\t\t\t\t\t}\n\t\t\t\t\telse if(masObj != 'undefined')\n\t\t\t\t\t{\n\t\t\t\t\t\t$.post(\"/showAgent.do?method=getAgentDetails&selectedServer=\"+masObj.value+\"&ajaxRequest=true\", function (data){$('.agentLoactionDiv').html(data);});//NO I18N\n\t\t\t\t\t}\n\t\t\t");
/*      */                                     }
/*      */                                     
/*      */ 
/* 5632 */                                     out.write("\n\t\t\t$('.agentLoactionDiv').slideDown(\"slow\"); //NO I18N\n\t\t}\n\t\telse\n\t\t{\n\t\t\ttoggleChecked(false);\n\t\t\t$('.agentLoactionDiv').slideUp(\"slow\"); //NO I18N\n\t\t}\n\t})\n  }\n);\n\nfunction toggleChecked(status) \n{\n\t$(\".agentLoactionDiv input:enabled\").each( function() { //NO I18N \n\t{\n\t\t$(this).attr(\"checked\",status); //NO I18N\n\t}})\n}\n\nfunction findSelectedIndex(id){\n\t");
/* 5633 */                                     if (fromWhere.equals("CONF")) {
/* 5634 */                                       out.write("\n\tif(document.AMActionForm.selectAllAgents.checked == true && document.AMActionForm.selectedAgents[id].checked==false)\n\t{\n\t\tdocument.AMActionForm.selectAllAgents.checked =false;\n\t}\n\t");
/*      */                                     } else {
/* 5636 */                                       out.write("\n\t\tif(document.RbmForm.selectAllAgents.checked == true && document.RbmForm.selectedAgents[id].checked==false)\n\t\t{\n\t\tdocument.RbmForm.selectAllAgents.checked =false;\n\t\t}\t\n\t");
/*      */                                     }
/* 5638 */                                     out.write("\n}\nfunction checkAgentSelected(){\n\tif(document.AMActionForm.runOnServer && document.AMActionForm.runOnServer.checked == true){\n\t\treturn true;\n\t}\n\telse if(document.AMActionForm.runOnAgent.checked == true){\n\t\tif(document.AMActionForm.selectAllAgents){\n\t\t\tnoOfAgents=");
/* 5639 */                                     out.print(agentLocation.size());
/* 5640 */                                     out.write(";\t\t\t\n\t\t\tif(document.AMActionForm.selectAllAgents.checked == true){\n\t\t\t\treturn true;\n\t\t\t}\t\t\t\n\t\t\telse if(noOfAgents>1){\t\t\t\t\n\t\t\t\t");
/*      */                                     
/* 5642 */                                     IterateTag _jspx_th_logic_005fiterate_005f4 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 5643 */                                     _jspx_th_logic_005fiterate_005f4.setPageContext(_jspx_page_context);
/* 5644 */                                     _jspx_th_logic_005fiterate_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5646 */                                     _jspx_th_logic_005fiterate_005f4.setName("agentLocation");
/*      */                                     
/* 5648 */                                     _jspx_th_logic_005fiterate_005f4.setId("userAgent");
/*      */                                     
/* 5650 */                                     _jspx_th_logic_005fiterate_005f4.setIndexId("j");
/* 5651 */                                     int _jspx_eval_logic_005fiterate_005f4 = _jspx_th_logic_005fiterate_005f4.doStartTag();
/* 5652 */                                     if (_jspx_eval_logic_005fiterate_005f4 != 0) {
/* 5653 */                                       Object userAgent = null;
/* 5654 */                                       Integer j = null;
/* 5655 */                                       if (_jspx_eval_logic_005fiterate_005f4 != 1) {
/* 5656 */                                         out = _jspx_page_context.pushBody();
/* 5657 */                                         _jspx_th_logic_005fiterate_005f4.setBodyContent((BodyContent)out);
/* 5658 */                                         _jspx_th_logic_005fiterate_005f4.doInitBody();
/*      */                                       }
/* 5660 */                                       userAgent = _jspx_page_context.findAttribute("userAgent");
/* 5661 */                                       j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                       for (;;) {
/* 5663 */                                         out.write("\n\t\t\t\t\tif(document.AMActionForm.selectedAgents[");
/* 5664 */                                         out.print(j);
/* 5665 */                                         out.write("].checked==true){\n\t\t\t\t\t\treturn true;\n\t\t\t\t\t}\n\t\t\t\t\n\t\t\t\t");
/* 5666 */                                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f4.doAfterBody();
/* 5667 */                                         userAgent = _jspx_page_context.findAttribute("userAgent");
/* 5668 */                                         j = (Integer)_jspx_page_context.findAttribute("j");
/* 5669 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 5672 */                                       if (_jspx_eval_logic_005fiterate_005f4 != 1) {
/* 5673 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 5676 */                                     if (_jspx_th_logic_005fiterate_005f4.doEndTag() == 5) {
/* 5677 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f4); return;
/*      */                                     }
/*      */                                     
/* 5680 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f4);
/* 5681 */                                     out.write("\n\t\t\t}else{\n\t\t\t\tif(document.AMActionForm.selectedAgents.checked==true){\n\t\t\t\t\treturn true;\n\t\t\t\t}\t\t\t\t\n\t\t\t}\n\t\t}\n\t\telse{\n\t\t\talert(\"");
/* 5682 */                                     out.print(FormatUtil.getString("am.webclient.eumagent.validate.noagent"));
/* 5683 */                                     out.write("\");\n\t\t\tnoOfAgents=0;\n\t\t}\n\t}\n\t\n\treturn false;\n}\nfunction checkAgentSelectedForRBM(){\n\tif(document.RbmForm.selectAllAgents){\n\t\tnoOfAgents=");
/* 5684 */                                     out.print(agentLocation.size());
/* 5685 */                                     out.write(";\t\n\t\tif(document.RbmForm.selectAllAgents.checked == true){\n\t\t\t\treturn true;\n\t\t}\n\t\telse if(noOfAgents>1){\t\t\t\t\n\t\t\t");
/*      */                                     
/* 5687 */                                     IterateTag _jspx_th_logic_005fiterate_005f5 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 5688 */                                     _jspx_th_logic_005fiterate_005f5.setPageContext(_jspx_page_context);
/* 5689 */                                     _jspx_th_logic_005fiterate_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5691 */                                     _jspx_th_logic_005fiterate_005f5.setName("agentLocation");
/*      */                                     
/* 5693 */                                     _jspx_th_logic_005fiterate_005f5.setId("userAgent");
/*      */                                     
/* 5695 */                                     _jspx_th_logic_005fiterate_005f5.setIndexId("j");
/* 5696 */                                     int _jspx_eval_logic_005fiterate_005f5 = _jspx_th_logic_005fiterate_005f5.doStartTag();
/* 5697 */                                     if (_jspx_eval_logic_005fiterate_005f5 != 0) {
/* 5698 */                                       Object userAgent = null;
/* 5699 */                                       Integer j = null;
/* 5700 */                                       if (_jspx_eval_logic_005fiterate_005f5 != 1) {
/* 5701 */                                         out = _jspx_page_context.pushBody();
/* 5702 */                                         _jspx_th_logic_005fiterate_005f5.setBodyContent((BodyContent)out);
/* 5703 */                                         _jspx_th_logic_005fiterate_005f5.doInitBody();
/*      */                                       }
/* 5705 */                                       userAgent = _jspx_page_context.findAttribute("userAgent");
/* 5706 */                                       j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                       for (;;) {
/* 5708 */                                         out.write("\n\t\t\t\tif(document.RbmForm.selectedAgents[");
/* 5709 */                                         out.print(j);
/* 5710 */                                         out.write("].checked==true){\n\t\t\t\t\t\t\treturn true;\n\t\t\t\t}\t\t\t\t\t\n\t\t\t");
/* 5711 */                                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f5.doAfterBody();
/* 5712 */                                         userAgent = _jspx_page_context.findAttribute("userAgent");
/* 5713 */                                         j = (Integer)_jspx_page_context.findAttribute("j");
/* 5714 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 5717 */                                       if (_jspx_eval_logic_005fiterate_005f5 != 1) {
/* 5718 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 5721 */                                     if (_jspx_th_logic_005fiterate_005f5.doEndTag() == 5) {
/* 5722 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f5); return;
/*      */                                     }
/*      */                                     
/* 5725 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f5);
/* 5726 */                                     out.write("\n\t\t}else if(document.RbmForm.selectedAgents.checked==true){\n\t\t\t\treturn true;\n\t\t\t\t\t\t\t\n\t\t}\n\t}\n\t\n\treturn false;\n}\n</script>\n");
/* 5727 */                                     out.write("\t\t\n\t\t\t</td>\t\t\t\n\t\t</tr>\n\t\t<tr>\n<tr>\n\t\t<td class=\"tablebottom\" >&nbsp;</td>\n\t\t<td class=\"tablebottom\" align=\"left\" colspan=\"2\">\n\n\t\t");
/*      */                                     
/* 5729 */                                     ButtonTag _jspx_th_html_005fbutton_005f0 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5730 */                                     _jspx_th_html_005fbutton_005f0.setPageContext(_jspx_page_context);
/* 5731 */                                     _jspx_th_html_005fbutton_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5733 */                                     _jspx_th_html_005fbutton_005f0.setOnclick("return validateAndSubmit();");
/*      */                                     
/* 5735 */                                     _jspx_th_html_005fbutton_005f0.setStyleClass("buttons btn_highlt");
/*      */                                     
/* 5737 */                                     _jspx_th_html_005fbutton_005f0.setProperty("submitbutton1");
/*      */                                     
/* 5739 */                                     _jspx_th_html_005fbutton_005f0.setValue(FormatUtil.getString("am.webclient.rbm.addbutton.text"));
/* 5740 */                                     int _jspx_eval_html_005fbutton_005f0 = _jspx_th_html_005fbutton_005f0.doStartTag();
/* 5741 */                                     if (_jspx_th_html_005fbutton_005f0.doEndTag() == 5) {
/* 5742 */                                       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0); return;
/*      */                                     }
/*      */                                     
/* 5745 */                                     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0);
/* 5746 */                                     out.write(10);
/*      */                                     
/* 5748 */                                     ResetTag _jspx_th_html_005freset_005f0 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.get(ResetTag.class);
/* 5749 */                                     _jspx_th_html_005freset_005f0.setPageContext(_jspx_page_context);
/* 5750 */                                     _jspx_th_html_005freset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5752 */                                     _jspx_th_html_005freset_005f0.setStyleClass("buttons btn_link");
/*      */                                     
/* 5754 */                                     _jspx_th_html_005freset_005f0.setValue(FormatUtil.getString("am.webclient.admintab.servicedesk.cancel"));
/*      */                                     
/* 5756 */                                     _jspx_th_html_005freset_005f0.setOnclick("javascript:history.back();");
/* 5757 */                                     int _jspx_eval_html_005freset_005f0 = _jspx_th_html_005freset_005f0.doStartTag();
/* 5758 */                                     if (_jspx_th_html_005freset_005f0.doEndTag() == 5) {
/* 5759 */                                       this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f0); return;
/*      */                                     }
/*      */                                     
/* 5762 */                                     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f0);
/* 5763 */                                     out.write("\n\t\t</td>\n\t</tr>\n\n\t</table>\n<div height=\"250\" id=\"rec_script_div\" STYLE=\"display:none;scroll:auto;position:absolute;\" width=\"100%\">\n\n\t\t<table width=\"100%\" class=\"leftmnutables\" >\n\t\t\t<tr>\n\t\t\t\t<td height=\"21\"  class=\"leftlinksheading\"  >");
/* 5764 */                                     out.print(FormatUtil.getString("am.webclient.rbmurlmonitor.type.text"));
/* 5765 */                                     out.write(58);
/* 5766 */                                     out.print(FormatUtil.getString("am.webclient.rbm.script.text"));
/* 5767 */                                     out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\n\t\t\t\t<td width=\"100%\" height=\"200px\" align=\"left\" class=\"bodytext\">\n\t\t\t\t\t<div id=\"scriptlinesdiv\" STYLE=\"display:block;scroll:auto;height:200;overflow:auto\" height=\"200\" width=\"100%\" align=\"left\"><table width=\"100%\"><tr><td width=\"100%\"  id=\"scriptlinestd\"  class=\"bodytext\" align=\"left\">\n\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t\t</div>\n\t\t\t\t</td>\n\n\t  \t   </tr>\n\t\t<tr>\n\t\t\t<td colspan=2 class=\"tablebottom\" align=center>\n\t\t</tr>\n\t</table>\n</div>\n</td>\n<td width=\"30%\" valign=\"top\">\n\n");
/* 5768 */                                     StringBuffer helpcardKey = new StringBuffer();
/* 5769 */                                     helpcardKey.append(FormatUtil.getString("am.rbmmonitor.helpcard.text", new String[] { OEMUtil.getOEMString("product.name") }));
/*      */                                     
/* 5771 */                                     out.write(10);
/* 5772 */                                     JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(helpcardKey.toString()), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()), out, false);
/* 5773 */                                     out.write(10);
/*      */                                   }
/*      */                                   
/*      */ 
/* 5777 */                                   out.write("\n\t</td>\n\t</tr>\n\t</table>\n");
/* 5778 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 5779 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 5783 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 5784 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 5787 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 5788 */                               out.write("\n<script>\nvar http;\n");
/*      */                               
/* 5790 */                               dName = request.getParameter("dname");
/* 5791 */                               if (dName != null)
/*      */                               {
/*      */ 
/* 5794 */                                 out.write("\n\t\t\t\ttry //No I18N\n\t\t\t\t{\n\t\t\t\t\tdocument.RbmForm.displayname.value='");
/* 5795 */                                 out.print(dName);
/* 5796 */                                 out.write("';\n\t\t\t\t}\n\t\t\tcatch(e)//No I18N\n\t\t\t{\n\t\t\t\t//alert(e);\n\t\t\t}\n\n\t\t\t");
/*      */                               }
/*      */                               
/*      */ 
/* 5800 */                               out.write("\n</script>\n");
/*      */                             }
/*      */                             catch (Exception e)
/*      */                             {
/* 5804 */                               e.printStackTrace();
/*      */                             }
/*      */                             
/* 5807 */                             out.write(10);
/* 5808 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 5809 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 5812 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 5813 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 5816 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 5817 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 5820 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 5821 */                         out.write(10);
/* 5822 */                         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 5824 */                         out.write(32);
/* 5825 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5826 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 5830 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5831 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 5834 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5835 */                       out.write(10);
/* 5836 */                       out.write(10);
/*      */                     }
/* 5838 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof SkipPageException)) {
/* 5839 */         out = _jspx_out;
/* 5840 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5841 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 5842 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5845 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5851 */     PageContext pageContext = _jspx_page_context;
/* 5852 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5854 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 5855 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 5856 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */     
/* 5858 */     _jspx_th_c_005fforEach_005f0.setItems("${dynamicSites}");
/*      */     
/* 5860 */     _jspx_th_c_005fforEach_005f0.setVar("a");
/*      */     
/* 5862 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowCounter");
/* 5863 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 5865 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 5866 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 5868 */           out.write(10);
/* 5869 */           out.write(9);
/* 5870 */           out.write(9);
/* 5871 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5872 */             return true;
/* 5873 */           out.write("\n\t\tif(formCustomerId == customerId)\n\t\t{\n\t\t\tdocument.RbmForm.haid.options[rowCount++] = new Option(siteName,siteId);\n\t\t}\n\t");
/* 5874 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 5875 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5879 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 5880 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5883 */         int tmp206_205 = 0; int[] tmp206_203 = _jspx_push_body_count_c_005fforEach_005f0; int tmp208_207 = tmp206_203[tmp206_205];tmp206_203[tmp206_205] = (tmp208_207 - 1); if (tmp208_207 <= 0) break;
/* 5884 */         out = _jspx_page_context.popBody(); }
/* 5885 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 5887 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 5888 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 5890 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5895 */     PageContext pageContext = _jspx_page_context;
/* 5896 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5898 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 5899 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 5900 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5902 */     _jspx_th_c_005fforEach_005f1.setItems("${a}");
/*      */     
/* 5904 */     _jspx_th_c_005fforEach_005f1.setVar("b");
/*      */     
/* 5906 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowCounter1");
/* 5907 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 5909 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 5910 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 5912 */           out.write("\n\t\t\t");
/* 5913 */           boolean bool; if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5914 */             return true;
/* 5915 */           out.write("\n\t\t\t");
/* 5916 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5917 */             return true;
/* 5918 */           out.write("\n\t\t\t");
/* 5919 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5920 */             return true;
/* 5921 */           out.write(10);
/* 5922 */           out.write(9);
/* 5923 */           out.write(9);
/* 5924 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 5925 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5929 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 5930 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5933 */         int tmp295_294 = 0; int[] tmp295_292 = _jspx_push_body_count_c_005fforEach_005f1; int tmp297_296 = tmp295_292[tmp295_294];tmp295_292[tmp295_294] = (tmp297_296 - 1); if (tmp297_296 <= 0) break;
/* 5934 */         out = _jspx_page_context.popBody(); }
/* 5935 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 5937 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 5938 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 5940 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5945 */     PageContext pageContext = _jspx_page_context;
/* 5946 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5948 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5949 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 5950 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5952 */     _jspx_th_c_005fif_005f0.setTest("${rowCounter1.count == 1}");
/* 5953 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 5954 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 5956 */         out.write("\n\t\t\t\tsiteName = '");
/* 5957 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5958 */           return true;
/* 5959 */         out.write("';\n\t\t\t");
/* 5960 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 5961 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5965 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 5966 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5967 */       return true;
/*      */     }
/* 5969 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5970 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5975 */     PageContext pageContext = _jspx_page_context;
/* 5976 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5978 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5979 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5980 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 5982 */     _jspx_th_c_005fout_005f0.setValue("${b}");
/* 5983 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5984 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5985 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5986 */       return true;
/*      */     }
/* 5988 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5989 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5994 */     PageContext pageContext = _jspx_page_context;
/* 5995 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5997 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5998 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 5999 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6001 */     _jspx_th_c_005fif_005f1.setTest("${rowCounter1.count == 2}");
/* 6002 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 6003 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 6005 */         out.write("\n\t\t\t\tsiteId = '");
/* 6006 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6007 */           return true;
/* 6008 */         out.write("';\n\t\t\t");
/* 6009 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 6010 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6014 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 6015 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6016 */       return true;
/*      */     }
/* 6018 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6019 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6024 */     PageContext pageContext = _jspx_page_context;
/* 6025 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6027 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6028 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 6029 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 6031 */     _jspx_th_c_005fout_005f1.setValue("${b}");
/* 6032 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 6033 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 6034 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6035 */       return true;
/*      */     }
/* 6037 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6038 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6043 */     PageContext pageContext = _jspx_page_context;
/* 6044 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6046 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6047 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 6048 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6050 */     _jspx_th_c_005fif_005f2.setTest("${rowCounter1.count == 3}");
/* 6051 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 6052 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 6054 */         out.write("\n\t\t\t\tcustomerId = '");
/* 6055 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6056 */           return true;
/* 6057 */         out.write("';\n\t\t\t");
/* 6058 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 6059 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6063 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 6064 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6065 */       return true;
/*      */     }
/* 6067 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6068 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6073 */     PageContext pageContext = _jspx_page_context;
/* 6074 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6076 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6077 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 6078 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6080 */     _jspx_th_c_005fout_005f2.setValue("${b}");
/* 6081 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 6082 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 6083 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6084 */       return true;
/*      */     }
/* 6086 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6087 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6092 */     PageContext pageContext = _jspx_page_context;
/* 6093 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6095 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6096 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 6097 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6099 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 6101 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 6102 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 6103 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 6104 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6105 */       return true;
/*      */     }
/* 6107 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6108 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6113 */     PageContext pageContext = _jspx_page_context;
/* 6114 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6116 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6117 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 6118 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6120 */     _jspx_th_c_005fif_005f4.setTest("${!empty param.wiz ||  !empty param.fromAssociate}");
/* 6121 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 6122 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 6124 */         out.write("\n      ");
/* 6125 */         if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 6126 */           return true;
/* 6127 */         out.write("\n      ");
/* 6128 */         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 6129 */           return true;
/* 6130 */         out.write("\n      ");
/* 6131 */         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 6132 */           return true;
/* 6133 */         out.write("\n      ");
/* 6134 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 6135 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6139 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 6140 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6141 */       return true;
/*      */     }
/* 6143 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6144 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6149 */     PageContext pageContext = _jspx_page_context;
/* 6150 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6152 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 6153 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 6154 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fif_005f4);
/* 6155 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 6156 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 6158 */         out.write("\n        ");
/* 6159 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 6160 */           return true;
/* 6161 */         out.write("\n        ");
/* 6162 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 6163 */           return true;
/* 6164 */         out.write("\n\n        ");
/* 6165 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 6166 */           return true;
/* 6167 */         out.write("\n      ");
/* 6168 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 6169 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6173 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 6174 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 6175 */       return true;
/*      */     }
/* 6177 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 6178 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6183 */     PageContext pageContext = _jspx_page_context;
/* 6184 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6186 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6187 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 6188 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 6190 */     _jspx_th_c_005fwhen_005f0.setTest("${param.type=='WTA'}");
/* 6191 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 6192 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 6194 */         out.write("\n          ");
/* 6195 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 6196 */           return true;
/* 6197 */         out.write("\n        ");
/* 6198 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 6199 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6203 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 6204 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 6205 */       return true;
/*      */     }
/* 6207 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 6208 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6213 */     PageContext pageContext = _jspx_page_context;
/* 6214 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6216 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6217 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 6218 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6220 */     _jspx_th_c_005fout_005f3.setValue("Web Transaction Monitor");
/* 6221 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 6222 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 6223 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6224 */       return true;
/*      */     }
/* 6226 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6227 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6232 */     PageContext pageContext = _jspx_page_context;
/* 6233 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6235 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6236 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 6237 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 6239 */     _jspx_th_c_005fwhen_005f1.setTest("${param.type=='.Net'}");
/* 6240 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 6241 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 6243 */         out.write("\n          ");
/* 6244 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 6245 */           return true;
/* 6246 */         out.write("\n        ");
/* 6247 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 6248 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6252 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 6253 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 6254 */       return true;
/*      */     }
/* 6256 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 6257 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6262 */     PageContext pageContext = _jspx_page_context;
/* 6263 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6265 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6266 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6267 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 6269 */     _jspx_th_c_005fout_005f4.setValue("Tomcat Server");
/* 6270 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 6271 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 6272 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6273 */       return true;
/*      */     }
/* 6275 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6276 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6281 */     PageContext pageContext = _jspx_page_context;
/* 6282 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6284 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 6285 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 6286 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 6287 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 6288 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 6290 */         out.write("\n         ");
/* 6291 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 6292 */           return true;
/* 6293 */         out.write("\n        ");
/* 6294 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 6295 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6299 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 6300 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 6301 */       return true;
/*      */     }
/* 6303 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 6304 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6309 */     PageContext pageContext = _jspx_page_context;
/* 6310 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6312 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 6313 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6314 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 6316 */     _jspx_th_c_005fout_005f5.setValue("${param.type}");
/* 6317 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6318 */     if (_jspx_eval_c_005fout_005f5 != 0) {
/* 6319 */       if (_jspx_eval_c_005fout_005f5 != 1) {
/* 6320 */         out = _jspx_page_context.pushBody();
/* 6321 */         _jspx_th_c_005fout_005f5.setBodyContent((BodyContent)out);
/* 6322 */         _jspx_th_c_005fout_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6325 */         out.write(45);
/* 6326 */         int evalDoAfterBody = _jspx_th_c_005fout_005f5.doAfterBody();
/* 6327 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6330 */       if (_jspx_eval_c_005fout_005f5 != 1) {
/* 6331 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6334 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6335 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f5);
/* 6336 */       return true;
/*      */     }
/* 6338 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f5);
/* 6339 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6344 */     PageContext pageContext = _jspx_page_context;
/* 6345 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6347 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6348 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 6349 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6351 */     _jspx_th_html_005fhidden_005f0.setProperty("type");
/* 6352 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 6353 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 6354 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 6355 */       return true;
/*      */     }
/* 6357 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 6358 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6363 */     PageContext pageContext = _jspx_page_context;
/* 6364 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6366 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6367 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 6368 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6370 */     _jspx_th_html_005fhidden_005f1.setProperty("haid");
/* 6371 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 6372 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 6373 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 6374 */       return true;
/*      */     }
/* 6376 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 6377 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6382 */     PageContext pageContext = _jspx_page_context;
/* 6383 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6385 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6386 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 6387 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6389 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 6391 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/* 6392 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 6393 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 6394 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 6395 */       return true;
/*      */     }
/* 6397 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 6398 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6403 */     PageContext pageContext = _jspx_page_context;
/* 6404 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6406 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 6407 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 6408 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6410 */     _jspx_th_html_005fselect_005f1.setProperty("scriptname");
/*      */     
/* 6412 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtext default");
/* 6413 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 6414 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 6415 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 6416 */         out = _jspx_page_context.pushBody();
/* 6417 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 6418 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6421 */         out.write(10);
/* 6422 */         out.write(9);
/* 6423 */         out.write(9);
/* 6424 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 6425 */           return true;
/* 6426 */         out.write("\n      \t");
/* 6427 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 6428 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6431 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 6432 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6435 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 6436 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 6437 */       return true;
/*      */     }
/* 6439 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 6440 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6445 */     PageContext pageContext = _jspx_page_context;
/* 6446 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6448 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.get(OptionsCollectionTag.class);
/* 6449 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 6450 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 6452 */     _jspx_th_html_005foptionsCollection_005f0.setName("AMActionForm");
/*      */     
/* 6454 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("RBMScripts");
/* 6455 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 6456 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 6457 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 6458 */       return true;
/*      */     }
/* 6460 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 6461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6466 */     PageContext pageContext = _jspx_page_context;
/* 6467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6469 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 6470 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 6471 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6473 */     _jspx_th_html_005fselect_005f2.setProperty("rbmagent");
/*      */     
/* 6475 */     _jspx_th_html_005fselect_005f2.setStyle("width:100%");
/*      */     
/* 6477 */     _jspx_th_html_005fselect_005f2.setStyleClass("formselectsm");
/*      */     
/* 6479 */     _jspx_th_html_005fselect_005f2.setMultiple("true");
/*      */     
/* 6481 */     _jspx_th_html_005fselect_005f2.setSize("5");
/* 6482 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 6483 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 6484 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 6485 */         out = _jspx_page_context.pushBody();
/* 6486 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 6487 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6490 */         out.write("\n\t\t\t");
/* 6491 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 6492 */           return true;
/* 6493 */         out.write("\n      \t");
/* 6494 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 6495 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6498 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 6499 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6502 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 6503 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f2);
/* 6504 */       return true;
/*      */     }
/* 6506 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f2);
/* 6507 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6512 */     PageContext pageContext = _jspx_page_context;
/* 6513 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6515 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.get(OptionsCollectionTag.class);
/* 6516 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 6517 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 6519 */     _jspx_th_html_005foptionsCollection_005f1.setName("AMActionForm");
/*      */     
/* 6521 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("RBMAgents");
/* 6522 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 6523 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 6524 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 6525 */       return true;
/*      */     }
/* 6527 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 6528 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6533 */     PageContext pageContext = _jspx_page_context;
/* 6534 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6536 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6537 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 6538 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6540 */     _jspx_th_html_005ftext_005f1.setProperty("pollinterval");
/*      */     
/* 6542 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext small");
/* 6543 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 6544 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 6545 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 6546 */       return true;
/*      */     }
/* 6548 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 6549 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6554 */     PageContext pageContext = _jspx_page_context;
/* 6555 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6557 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6558 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 6559 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6561 */     _jspx_th_html_005ftext_005f2.setProperty("timeout");
/*      */     
/* 6563 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext small");
/* 6564 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 6565 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 6566 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 6567 */       return true;
/*      */     }
/* 6569 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 6570 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmultibox_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6575 */     PageContext pageContext = _jspx_page_context;
/* 6576 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6578 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 6579 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 6580 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmultibox_005f0);
/*      */     
/* 6582 */     _jspx_th_bean_005fwrite_005f0.setName("userAgent");
/*      */     
/* 6584 */     _jspx_th_bean_005fwrite_005f0.setProperty("value");
/* 6585 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 6586 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 6587 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 6588 */       return true;
/*      */     }
/* 6590 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 6591 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_logic_005fiterate_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6596 */     PageContext pageContext = _jspx_page_context;
/* 6597 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6599 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 6600 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 6601 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_logic_005fiterate_005f3);
/*      */     
/* 6603 */     _jspx_th_bean_005fwrite_005f1.setName("userAgent");
/*      */     
/* 6605 */     _jspx_th_bean_005fwrite_005f1.setProperty("label");
/* 6606 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 6607 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 6608 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 6609 */       return true;
/*      */     }
/* 6611 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 6612 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_logic_005fiterate_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6617 */     PageContext pageContext = _jspx_page_context;
/* 6618 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6620 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6621 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 6622 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_logic_005fiterate_005f3);
/*      */     
/* 6624 */     _jspx_th_c_005fif_005f6.setTest("${((j+1)%4) == 0}");
/* 6625 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 6626 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 6628 */         out.write("\n\t\t\t\t\t\t\t</tr><tr height=\"5px\"><td colspan=\"3\"><img src=\"/images/spacer.gif\"></img></tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t");
/* 6629 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 6630 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6634 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 6635 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6636 */       return true;
/*      */     }
/* 6638 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6639 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6644 */     PageContext pageContext = _jspx_page_context;
/* 6645 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6647 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6648 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 6649 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6651 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 6653 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 6654 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 6655 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 6656 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 6657 */       return true;
/*      */     }
/* 6659 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 6660 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\RBM_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */