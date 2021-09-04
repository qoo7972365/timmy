/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
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
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.ButtonTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.MultiboxTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.ResetTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class newurlconf_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   68 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   71 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   72 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   73 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   80 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   85 */     ArrayList list = null;
/*   86 */     StringBuffer sbf = new StringBuffer();
/*   87 */     ManagedApplication mo = new ManagedApplication();
/*   88 */     if (distinct)
/*      */     {
/*   90 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   94 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   97 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   99 */       ArrayList row = (ArrayList)list.get(i);
/*  100 */       sbf.append("<option value='" + row.get(0) + "'>");
/*  101 */       if (distinct) {
/*  102 */         sbf.append(row.get(0));
/*      */       } else
/*  104 */         sbf.append(row.get(1));
/*  105 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  108 */     return sbf.toString(); }
/*      */   
/*  110 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  113 */     if (severity == null)
/*      */     {
/*  115 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  117 */     if (severity.equals("5"))
/*      */     {
/*  119 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  121 */     if (severity.equals("1"))
/*      */     {
/*  123 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  128 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  135 */     if (severity == null)
/*      */     {
/*  137 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  139 */     if (severity.equals("1"))
/*      */     {
/*  141 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  143 */     if (severity.equals("4"))
/*      */     {
/*  145 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  147 */     if (severity.equals("5"))
/*      */     {
/*  149 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  154 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  160 */     if (severity == null)
/*      */     {
/*  162 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  164 */     if (severity.equals("5"))
/*      */     {
/*  166 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  168 */     if (severity.equals("1"))
/*      */     {
/*  170 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  174 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  180 */     if (severity == null)
/*      */     {
/*  182 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  184 */     if (severity.equals("1"))
/*      */     {
/*  186 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  188 */     if (severity.equals("4"))
/*      */     {
/*  190 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  192 */     if (severity.equals("5"))
/*      */     {
/*  194 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  198 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  204 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  210 */     if (severity == 5)
/*      */     {
/*  212 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  214 */     if (severity == 1)
/*      */     {
/*  216 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  221 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  227 */     if (severity == null)
/*      */     {
/*  229 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  231 */     if (severity.equals("5"))
/*      */     {
/*  233 */       if (isAvailability) {
/*  234 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  237 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  240 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  242 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  244 */     if (severity.equals("1"))
/*      */     {
/*  246 */       if (isAvailability) {
/*  247 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  250 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  257 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  264 */     if (severity == null)
/*      */     {
/*  266 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  268 */     if (severity.equals("5"))
/*      */     {
/*  270 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  272 */     if (severity.equals("4"))
/*      */     {
/*  274 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  276 */     if (severity.equals("1"))
/*      */     {
/*  278 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  283 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  289 */     if (severity == null)
/*      */     {
/*  291 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  293 */     if (severity.equals("5"))
/*      */     {
/*  295 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  297 */     if (severity.equals("4"))
/*      */     {
/*  299 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  301 */     if (severity.equals("1"))
/*      */     {
/*  303 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  308 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  315 */     if (severity == null)
/*      */     {
/*  317 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  319 */     if (severity.equals("5"))
/*      */     {
/*  321 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  323 */     if (severity.equals("4"))
/*      */     {
/*  325 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  327 */     if (severity.equals("1"))
/*      */     {
/*  329 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  334 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  342 */     StringBuffer out = new StringBuffer();
/*  343 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  344 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  345 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  346 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  347 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  348 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  349 */     out.append("</tr>");
/*  350 */     out.append("</form></table>");
/*  351 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  358 */     if (val == null)
/*      */     {
/*  360 */       return "-";
/*      */     }
/*      */     
/*  363 */     String ret = FormatUtil.formatNumber(val);
/*  364 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  365 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  368 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  372 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  380 */     StringBuffer out = new StringBuffer();
/*  381 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  382 */     out.append("<tr>");
/*  383 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  385 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  387 */     out.append("</tr>");
/*  388 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  392 */       if (j % 2 == 0)
/*      */       {
/*  394 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  398 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  401 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  403 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  406 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  410 */       out.append("</tr>");
/*      */     }
/*  412 */     out.append("</table>");
/*  413 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  414 */     out.append("<tr>");
/*  415 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  416 */     out.append("</tr>");
/*  417 */     out.append("</table>");
/*  418 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  424 */     StringBuffer out = new StringBuffer();
/*  425 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  426 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  427 */     out.append("<tr>");
/*  428 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  429 */     out.append("<tr>");
/*  430 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  431 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  432 */     out.append("</tr>");
/*  433 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  436 */       out.append("<tr>");
/*  437 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  438 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  439 */       out.append("</tr>");
/*      */     }
/*      */     
/*  442 */     out.append("</table>");
/*  443 */     out.append("</table>");
/*  444 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  449 */     if (severity.equals("0"))
/*      */     {
/*  451 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  455 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  462 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  475 */     StringBuffer out = new StringBuffer();
/*  476 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  477 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  479 */       out.append("<tr>");
/*  480 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  481 */       out.append("</tr>");
/*      */       
/*      */ 
/*  484 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  486 */         String borderclass = "";
/*      */         
/*      */ 
/*  489 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  491 */         out.append("<tr>");
/*      */         
/*  493 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  494 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  495 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  501 */     out.append("</table><br>");
/*  502 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  503 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  505 */       List sLinks = secondLevelOfLinks[0];
/*  506 */       List sText = secondLevelOfLinks[1];
/*  507 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  510 */         out.append("<tr>");
/*  511 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  512 */         out.append("</tr>");
/*  513 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  515 */           String borderclass = "";
/*      */           
/*      */ 
/*  518 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  520 */           out.append("<tr>");
/*      */           
/*  522 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  523 */           if (sLinks.get(i).toString().length() == 0) {
/*  524 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  527 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  529 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  533 */     out.append("</table>");
/*  534 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  541 */     StringBuffer out = new StringBuffer();
/*  542 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  543 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  545 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  547 */         out.append("<tr>");
/*  548 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  549 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  553 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  555 */           String borderclass = "";
/*      */           
/*      */ 
/*  558 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  560 */           out.append("<tr>");
/*      */           
/*  562 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  563 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  564 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  567 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  570 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  575 */     out.append("</table><br>");
/*  576 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  577 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  579 */       List sLinks = secondLevelOfLinks[0];
/*  580 */       List sText = secondLevelOfLinks[1];
/*  581 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  584 */         out.append("<tr>");
/*  585 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  586 */         out.append("</tr>");
/*  587 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  589 */           String borderclass = "";
/*      */           
/*      */ 
/*  592 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  594 */           out.append("<tr>");
/*      */           
/*  596 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  597 */           if (sLinks.get(i).toString().length() == 0) {
/*  598 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  601 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  603 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  607 */     out.append("</table>");
/*  608 */     return out.toString();
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
/*  621 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  624 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  627 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  630 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  633 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  636 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  639 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  642 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  650 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  655 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  660 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  665 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  670 */     if (val != null)
/*      */     {
/*  672 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  676 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  681 */     if (val == null) {
/*  682 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  686 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  691 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  697 */     if (val != null)
/*      */     {
/*  699 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  703 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  709 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  714 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  718 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  723 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  728 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  733 */     String hostaddress = "";
/*  734 */     String ip = request.getHeader("x-forwarded-for");
/*  735 */     if (ip == null)
/*  736 */       ip = request.getRemoteAddr();
/*  737 */     InetAddress add = null;
/*  738 */     if (ip.equals("127.0.0.1")) {
/*  739 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  743 */       add = InetAddress.getByName(ip);
/*      */     }
/*  745 */     hostaddress = add.getHostName();
/*  746 */     if (hostaddress.indexOf('.') != -1) {
/*  747 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  748 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  752 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  757 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  763 */     if (severity == null)
/*      */     {
/*  765 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  767 */     if (severity.equals("5"))
/*      */     {
/*  769 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  771 */     if (severity.equals("1"))
/*      */     {
/*  773 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  778 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  783 */     ResultSet set = null;
/*  784 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  785 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  787 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  788 */       if (set.next()) { String str1;
/*  789 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  790 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  793 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  798 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  801 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  803 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  807 */     StringBuffer rca = new StringBuffer();
/*  808 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  809 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  812 */     int rcalength = key.length();
/*  813 */     String split = "6. ";
/*  814 */     int splitPresent = key.indexOf(split);
/*  815 */     String div1 = "";String div2 = "";
/*  816 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  818 */       if (rcalength > 180) {
/*  819 */         rca.append("<span class=\"rca-critical-text\">");
/*  820 */         getRCATrimmedText(key, rca);
/*  821 */         rca.append("</span>");
/*      */       } else {
/*  823 */         rca.append("<span class=\"rca-critical-text\">");
/*  824 */         rca.append(key);
/*  825 */         rca.append("</span>");
/*      */       }
/*  827 */       return rca.toString();
/*      */     }
/*  829 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  830 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  831 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  832 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  833 */     getRCATrimmedText(div1, rca);
/*  834 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  837 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  838 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  839 */     getRCATrimmedText(div2, rca);
/*  840 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  842 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  847 */     String[] st = msg.split("<br>");
/*  848 */     for (int i = 0; i < st.length; i++) {
/*  849 */       String s = st[i];
/*  850 */       if (s.length() > 180) {
/*  851 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  853 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  857 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  858 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  860 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  864 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  865 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  866 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  869 */       if (key == null) {
/*  870 */         return ret;
/*      */       }
/*      */       
/*  873 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  874 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  877 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  878 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  879 */       set = AMConnectionPool.executeQueryStmt(query);
/*  880 */       if (set.next())
/*      */       {
/*  882 */         String helpLink = set.getString("LINK");
/*  883 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  886 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  892 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  911 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  902 */         if (set != null) {
/*  903 */           AMConnectionPool.closeStatement(set);
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
/*  917 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  918 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  920 */       String entityStr = (String)keys.nextElement();
/*  921 */       String mmessage = temp.getProperty(entityStr);
/*  922 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  923 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  925 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  931 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  932 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  934 */       String entityStr = (String)keys.nextElement();
/*  935 */       String mmessage = temp.getProperty(entityStr);
/*  936 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  937 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  939 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  944 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  954 */     String des = new String();
/*  955 */     while (str.indexOf(find) != -1) {
/*  956 */       des = des + str.substring(0, str.indexOf(find));
/*  957 */       des = des + replace;
/*  958 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  960 */     des = des + str;
/*  961 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  968 */       if (alert == null)
/*      */       {
/*  970 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  972 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  974 */         return "&nbsp;";
/*      */       }
/*      */       
/*  977 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  979 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  982 */       int rcalength = test.length();
/*  983 */       if (rcalength < 300)
/*      */       {
/*  985 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  989 */       StringBuffer out = new StringBuffer();
/*  990 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  991 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  992 */       out.append("</div>");
/*  993 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  994 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  995 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1000 */       ex.printStackTrace();
/*      */     }
/* 1002 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1008 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1013 */     ArrayList attribIDs = new ArrayList();
/* 1014 */     ArrayList resIDs = new ArrayList();
/* 1015 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1017 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1019 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1021 */       String resourceid = "";
/* 1022 */       String resourceType = "";
/* 1023 */       if (type == 2) {
/* 1024 */         resourceid = (String)row.get(0);
/* 1025 */         resourceType = (String)row.get(3);
/*      */       }
/* 1027 */       else if (type == 3) {
/* 1028 */         resourceid = (String)row.get(0);
/* 1029 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1032 */         resourceid = (String)row.get(6);
/* 1033 */         resourceType = (String)row.get(7);
/*      */       }
/* 1035 */       resIDs.add(resourceid);
/* 1036 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1037 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1039 */       String healthentity = null;
/* 1040 */       String availentity = null;
/* 1041 */       if (healthid != null) {
/* 1042 */         healthentity = resourceid + "_" + healthid;
/* 1043 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1046 */       if (availid != null) {
/* 1047 */         availentity = resourceid + "_" + availid;
/* 1048 */         entitylist.add(availentity);
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
/* 1062 */     Properties alert = getStatus(entitylist);
/* 1063 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1068 */     int size = monitorList.size();
/*      */     
/* 1070 */     String[] severity = new String[size];
/*      */     
/* 1072 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1074 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1075 */       String resourceName1 = (String)row1.get(7);
/* 1076 */       String resourceid1 = (String)row1.get(6);
/* 1077 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1078 */       if (severity[j] == null)
/*      */       {
/* 1080 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1084 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1086 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1088 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1091 */         if (sev > 0) {
/* 1092 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1093 */           monitorList.set(k, monitorList.get(j));
/* 1094 */           monitorList.set(j, t);
/* 1095 */           String temp = severity[k];
/* 1096 */           severity[k] = severity[j];
/* 1097 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1103 */     int z = 0;
/* 1104 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1107 */       int i = 0;
/* 1108 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1111 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1115 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1119 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1121 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1124 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1128 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1131 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1132 */       String resourceName1 = (String)row1.get(7);
/* 1133 */       String resourceid1 = (String)row1.get(6);
/* 1134 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1135 */       if (hseverity[j] == null)
/*      */       {
/* 1137 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1142 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1144 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1147 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1150 */         if (hsev > 0) {
/* 1151 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1152 */           monitorList.set(k, monitorList.get(j));
/* 1153 */           monitorList.set(j, t);
/* 1154 */           String temp1 = hseverity[k];
/* 1155 */           hseverity[k] = hseverity[j];
/* 1156 */           hseverity[j] = temp1;
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
/* 1168 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1169 */     boolean forInventory = false;
/* 1170 */     String trdisplay = "none";
/* 1171 */     String plusstyle = "inline";
/* 1172 */     String minusstyle = "none";
/* 1173 */     String haidTopLevel = "";
/* 1174 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1176 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1178 */         haidTopLevel = request.getParameter("haid");
/* 1179 */         forInventory = true;
/* 1180 */         trdisplay = "table-row;";
/* 1181 */         plusstyle = "none";
/* 1182 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1189 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1192 */     ArrayList listtoreturn = new ArrayList();
/* 1193 */     StringBuffer toreturn = new StringBuffer();
/* 1194 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1195 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1196 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1198 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1200 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1201 */       String childresid = (String)singlerow.get(0);
/* 1202 */       String childresname = (String)singlerow.get(1);
/* 1203 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1204 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1205 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1206 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1207 */       String unmanagestatus = (String)singlerow.get(5);
/* 1208 */       String actionstatus = (String)singlerow.get(6);
/* 1209 */       String linkclass = "monitorgp-links";
/* 1210 */       String titleforres = childresname;
/* 1211 */       String titilechildresname = childresname;
/* 1212 */       String childimg = "/images/trcont.png";
/* 1213 */       String flag = "enable";
/* 1214 */       String dcstarted = (String)singlerow.get(8);
/* 1215 */       String configMonitor = "";
/* 1216 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1217 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1219 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1221 */       if (singlerow.get(7) != null)
/*      */       {
/* 1223 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1225 */       String haiGroupType = "0";
/* 1226 */       if ("HAI".equals(childtype))
/*      */       {
/* 1228 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1230 */       childimg = "/images/trend.png";
/* 1231 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1232 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1233 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1235 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1237 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1239 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1240 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1243 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1245 */         linkclass = "disabledtext";
/* 1246 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1248 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1249 */       String availmouseover = "";
/* 1250 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1252 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1254 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1255 */       String healthmouseover = "";
/* 1256 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1258 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1261 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1262 */       int spacing = 0;
/* 1263 */       if (level >= 1)
/*      */       {
/* 1265 */         spacing = 40 * level;
/*      */       }
/* 1267 */       if (childtype.equals("HAI"))
/*      */       {
/* 1269 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1270 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1271 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1273 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1274 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1275 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1276 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1277 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1278 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1279 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1280 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1281 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1282 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1283 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1285 */         if (!forInventory)
/*      */         {
/* 1287 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1290 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1292 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1294 */           actions = editlink + actions;
/*      */         }
/* 1296 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1298 */           actions = actions + associatelink;
/*      */         }
/* 1300 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1301 */         String arrowimg = "";
/* 1302 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1304 */           actions = "";
/* 1305 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1306 */           checkbox = "";
/* 1307 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1309 */         if (isIt360)
/*      */         {
/* 1311 */           actionimg = "";
/* 1312 */           actions = "";
/* 1313 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1314 */           checkbox = "";
/*      */         }
/*      */         
/* 1317 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1319 */           actions = "";
/*      */         }
/* 1321 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1323 */           checkbox = "";
/*      */         }
/*      */         
/* 1326 */         String resourcelink = "";
/*      */         
/* 1328 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1330 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1334 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1337 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1338 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1339 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1340 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1341 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1342 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1343 */         if (!isIt360)
/*      */         {
/* 1345 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1349 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1352 */         toreturn.append("</tr>");
/* 1353 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1355 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1356 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1360 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1361 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1364 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1368 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1370 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1371 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1372 */             toreturn.append(assocMessage);
/* 1373 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1374 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1375 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1376 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1382 */         String resourcelink = null;
/* 1383 */         boolean hideEditLink = false;
/* 1384 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1386 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1387 */           hideEditLink = true;
/* 1388 */           if (isIt360)
/*      */           {
/* 1390 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1394 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1396 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1398 */           hideEditLink = true;
/* 1399 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1400 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1405 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1408 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1409 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1410 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1411 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1412 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1413 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1414 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1415 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1416 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1417 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1418 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1419 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1420 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1422 */         if (hideEditLink)
/*      */         {
/* 1424 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1426 */         if (!forInventory)
/*      */         {
/* 1428 */           removefromgroup = "";
/*      */         }
/* 1430 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1431 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1432 */           actions = actions + configcustomfields;
/*      */         }
/* 1434 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1436 */           actions = editlink + actions;
/*      */         }
/* 1438 */         String managedLink = "";
/* 1439 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1441 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1442 */           actions = "";
/* 1443 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1444 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1447 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1449 */           checkbox = "";
/*      */         }
/*      */         
/* 1452 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1454 */           actions = "";
/*      */         }
/* 1456 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1457 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1458 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1459 */         if (isIt360)
/*      */         {
/* 1461 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1465 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1467 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1468 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1469 */         if (!isIt360)
/*      */         {
/* 1471 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1475 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1477 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1480 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1487 */       StringBuilder toreturn = new StringBuilder();
/* 1488 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1489 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1490 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1491 */       String title = "";
/* 1492 */       message = EnterpriseUtil.decodeString(message);
/* 1493 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1494 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1495 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1497 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1499 */       else if ("5".equals(severity))
/*      */       {
/* 1501 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1505 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1507 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1508 */       toreturn.append(v);
/*      */       
/* 1510 */       toreturn.append(link);
/* 1511 */       if (severity == null)
/*      */       {
/* 1513 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1515 */       else if (severity.equals("5"))
/*      */       {
/* 1517 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1519 */       else if (severity.equals("4"))
/*      */       {
/* 1521 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1523 */       else if (severity.equals("1"))
/*      */       {
/* 1525 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1530 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1532 */       toreturn.append("</a>");
/* 1533 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1537 */       ex.printStackTrace();
/*      */     }
/* 1539 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1546 */       StringBuilder toreturn = new StringBuilder();
/* 1547 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1548 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1549 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1550 */       if (message == null)
/*      */       {
/* 1552 */         message = "";
/*      */       }
/*      */       
/* 1555 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1556 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1558 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1559 */       toreturn.append(v);
/*      */       
/* 1561 */       toreturn.append(link);
/*      */       
/* 1563 */       if (severity == null)
/*      */       {
/* 1565 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1567 */       else if (severity.equals("5"))
/*      */       {
/* 1569 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1571 */       else if (severity.equals("1"))
/*      */       {
/* 1573 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1578 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1580 */       toreturn.append("</a>");
/* 1581 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1587 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1590 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1591 */     if (invokeActions != null) {
/* 1592 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1593 */       while (iterator.hasNext()) {
/* 1594 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1595 */         if (actionmap.containsKey(actionid)) {
/* 1596 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1601 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1605 */     String actionLink = "";
/* 1606 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1607 */     String query = "";
/* 1608 */     ResultSet rs = null;
/* 1609 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1610 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1611 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1612 */       actionLink = "method=" + methodName;
/*      */     }
/* 1614 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1615 */       actionLink = methodName;
/*      */     }
/* 1617 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1618 */     Iterator itr = methodarglist.iterator();
/* 1619 */     boolean isfirstparam = true;
/* 1620 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1621 */     while (itr.hasNext()) {
/* 1622 */       HashMap argmap = (HashMap)itr.next();
/* 1623 */       String argtype = (String)argmap.get("TYPE");
/* 1624 */       String argname = (String)argmap.get("IDENTITY");
/* 1625 */       String paramname = (String)argmap.get("PARAMETER");
/* 1626 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1627 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1628 */         isfirstparam = false;
/* 1629 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1631 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1635 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1639 */         actionLink = actionLink + "&";
/*      */       }
/* 1641 */       String paramValue = null;
/* 1642 */       String tempargname = argname;
/* 1643 */       if (commonValues.getProperty(tempargname) != null) {
/* 1644 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1647 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1648 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1649 */           if (dbType.equals("mysql")) {
/* 1650 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1653 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1655 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1657 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1658 */             if (rs.next()) {
/* 1659 */               paramValue = rs.getString("VALUE");
/* 1660 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1664 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1668 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1671 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1676 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1677 */           paramValue = rowId;
/*      */         }
/* 1679 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1680 */           paramValue = managedObjectName;
/*      */         }
/* 1682 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1683 */           paramValue = resID;
/*      */         }
/* 1685 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1686 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1689 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1691 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1692 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1693 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1695 */     return actionLink;
/*      */   }
/*      */   
/* 1698 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1699 */     String dependentAttribute = null;
/* 1700 */     String align = "left";
/*      */     
/* 1702 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1703 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1704 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1705 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1706 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1707 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1708 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1709 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1710 */       align = "center";
/*      */     }
/*      */     
/* 1713 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1714 */     String actualdata = "";
/*      */     
/* 1716 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1717 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1718 */         actualdata = availValue;
/*      */       }
/* 1720 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1721 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1725 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1726 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1729 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1735 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1736 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1737 */       toreturn.append("<table>");
/* 1738 */       toreturn.append("<tr>");
/* 1739 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1740 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1741 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1742 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1743 */         String toolTip = "";
/* 1744 */         String hideClass = "";
/* 1745 */         String textStyle = "";
/* 1746 */         boolean isreferenced = true;
/* 1747 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1748 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1749 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1750 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1752 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1753 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1754 */           while (valueList.hasMoreTokens()) {
/* 1755 */             String dependentVal = valueList.nextToken();
/* 1756 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1757 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1758 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1760 */               toolTip = "";
/* 1761 */               hideClass = "";
/* 1762 */               isreferenced = false;
/* 1763 */               textStyle = "disabledtext";
/* 1764 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1768 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1769 */           toolTip = "";
/* 1770 */           hideClass = "";
/* 1771 */           isreferenced = false;
/* 1772 */           textStyle = "disabledtext";
/* 1773 */           if (dependentImageMap != null) {
/* 1774 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1775 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1778 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1782 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1783 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1784 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1785 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1786 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1787 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1789 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1790 */           if (isreferenced) {
/* 1791 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1795 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1796 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1797 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1798 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1799 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1800 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1802 */           toreturn.append("</span>");
/* 1803 */           toreturn.append("</a>");
/* 1804 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1807 */       toreturn.append("</tr>");
/* 1808 */       toreturn.append("</table>");
/* 1809 */       toreturn.append("</td>");
/*      */     } else {
/* 1811 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1814 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1818 */     String colTime = null;
/* 1819 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1820 */     if ((rows != null) && (rows.size() > 0)) {
/* 1821 */       Iterator<String> itr = rows.iterator();
/* 1822 */       String maxColQuery = "";
/* 1823 */       for (;;) { if (itr.hasNext()) {
/* 1824 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1825 */           ResultSet maxCol = null;
/*      */           try {
/* 1827 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1828 */             while (maxCol.next()) {
/* 1829 */               if (colTime == null) {
/* 1830 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1833 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1842 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1844 */               if (maxCol != null)
/* 1845 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1847 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1842 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1844 */               if (maxCol != null)
/* 1845 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1847 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1852 */     return colTime;
/*      */   }
/*      */   
/* 1855 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1856 */     tablename = null;
/* 1857 */     ResultSet rsTable = null;
/* 1858 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1860 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1861 */       while (rsTable.next()) {
/* 1862 */         tablename = rsTable.getString("DATATABLE");
/* 1863 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1864 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1877 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1868 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1871 */         if (rsTable != null)
/* 1872 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1874 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1880 */     String argsList = "";
/* 1881 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1883 */       if (showArgsMap.get(row) != null) {
/* 1884 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1885 */         if (showArgslist != null) {
/* 1886 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1887 */             if (argsList.trim().equals("")) {
/* 1888 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1891 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1898 */       e.printStackTrace();
/* 1899 */       return "";
/*      */     }
/* 1901 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1906 */     String argsList = "";
/* 1907 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1910 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1912 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1913 */         if (hideArgsList != null)
/*      */         {
/* 1915 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1917 */             if (argsList.trim().equals(""))
/*      */             {
/* 1919 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1923 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1931 */       ex.printStackTrace();
/*      */     }
/* 1933 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1937 */     StringBuilder toreturn = new StringBuilder();
/* 1938 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1945 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1946 */       Iterator itr = tActionList.iterator();
/* 1947 */       while (itr.hasNext()) {
/* 1948 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1949 */         String confirmmsg = "";
/* 1950 */         String link = "";
/* 1951 */         String isJSP = "NO";
/* 1952 */         HashMap tactionMap = (HashMap)itr.next();
/* 1953 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1954 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1955 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1956 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1957 */           (actionmap.containsKey(actionId))) {
/* 1958 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1959 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1960 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1961 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1962 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1964 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1970 */           if (isTableAction) {
/* 1971 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1974 */             tableName = "Link";
/* 1975 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1976 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1977 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1978 */             toreturn.append("</a></td>");
/*      */           }
/* 1980 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1981 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1982 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1983 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1989 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1995 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1997 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1998 */       Properties prop = (Properties)node.getUserObject();
/* 1999 */       String mgID = prop.getProperty("label");
/* 2000 */       String mgName = prop.getProperty("value");
/* 2001 */       String isParent = prop.getProperty("isParent");
/* 2002 */       int mgIDint = Integer.parseInt(mgID);
/* 2003 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 2005 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2007 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2008 */       if (node.getChildCount() > 0)
/*      */       {
/* 2010 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2012 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2014 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2016 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2020 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2025 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2027 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2029 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2031 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2035 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2038 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2039 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2041 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2045 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2047 */       if (node.getChildCount() > 0)
/*      */       {
/* 2049 */         builder.append("<UL>");
/* 2050 */         printMGTree(node, builder);
/* 2051 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2056 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2057 */     StringBuffer toReturn = new StringBuffer();
/* 2058 */     String table = "-";
/*      */     try {
/* 2060 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2061 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2062 */       float total = 0.0F;
/* 2063 */       while (it.hasNext()) {
/* 2064 */         String attName = (String)it.next();
/* 2065 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2066 */         boolean roundOffData = false;
/* 2067 */         if ((data != null) && (!data.equals(""))) {
/* 2068 */           if (data.indexOf(",") != -1) {
/* 2069 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2072 */             float value = Float.parseFloat(data);
/* 2073 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2076 */             total += value;
/* 2077 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2080 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2085 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2086 */       while (attVsWidthList.hasNext()) {
/* 2087 */         String attName = (String)attVsWidthList.next();
/* 2088 */         String data = (String)attVsWidthProps.get(attName);
/* 2089 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2090 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2091 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2092 */         String className = (String)graphDetails.get("ClassName");
/* 2093 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2094 */         if (percentage < 1.0F)
/*      */         {
/* 2096 */           data = percentage + "";
/*      */         }
/* 2098 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2100 */       if (toReturn.length() > 0) {
/* 2101 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2105 */       e.printStackTrace();
/*      */     }
/* 2107 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2113 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2114 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2115 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2116 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2117 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2118 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2119 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2120 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2121 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2124 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2125 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2126 */       splitvalues[0] = multiplecondition.toString();
/* 2127 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2130 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2135 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2136 */     if (thresholdType != 3) {
/* 2137 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2138 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2139 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2140 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2141 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2142 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2144 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2145 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2146 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2147 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2148 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2149 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2151 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2152 */     if (updateSelected != null) {
/* 2153 */       updateSelected[0] = "selected";
/*      */     }
/* 2155 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2160 */       StringBuffer toreturn = new StringBuffer("");
/* 2161 */       if (commaSeparatedMsgId != null) {
/* 2162 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2163 */         int count = 0;
/* 2164 */         while (msgids.hasMoreTokens()) {
/* 2165 */           String id = msgids.nextToken();
/* 2166 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2167 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2168 */           count++;
/* 2169 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2170 */             if (toreturn.length() == 0) {
/* 2171 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2173 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2174 */             if (!image.trim().equals("")) {
/* 2175 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2177 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2178 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2181 */         if (toreturn.length() > 0) {
/* 2182 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2186 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2189 */       e.printStackTrace(); }
/* 2190 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getMGroupsCreatedInAdminServer(ArrayList aListOfAllMonitorGroups)
/*      */   {
/* 2199 */     ArrayList aListAdminMonitorGrps = new ArrayList();
/*      */     try {
/* 2201 */       for (int i = 0; i < aListOfAllMonitorGroups.size(); i++) {
/* 2202 */         ArrayList innerList = (ArrayList)aListOfAllMonitorGroups.get(i);
/* 2203 */         if ((innerList != null) && (innerList.size() >= 2))
/*      */         {
/*      */           try
/*      */           {
/* 2207 */             String strMgId = (String)innerList.get(1);
/* 2208 */             int mgId = Integer.parseInt(strMgId);
/* 2209 */             if (mgId < 10000000) {
/* 2210 */               aListAdminMonitorGrps.add(innerList);
/*      */             }
/* 2212 */             String grpCreatedMasName = com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(strMgId);
/* 2213 */             innerList.add(grpCreatedMasName);
/*      */           }
/*      */           catch (Exception ex1) {}
/*      */         }
/*      */       }
/*      */     } catch (Exception ex2) {
/* 2219 */       ex2.printStackTrace();
/*      */     }
/* 2221 */     return aListAdminMonitorGrps;
/*      */   }
/*      */   
/* 2224 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2230 */   private static Map<String, Long> _jspx_dependants = new HashMap(6);
/* 2231 */   static { _jspx_dependants.put("/jsp/includes/monitorGroups.jsp", Long.valueOf(1473429417000L));
/* 2232 */     _jspx_dependants.put("/jsp/includes/urlUtil.jspf", Long.valueOf(1473429417000L));
/* 2233 */     _jspx_dependants.put("/jsp/includes/newresourcetypes.jspf", Long.valueOf(1473429417000L));
/* 2234 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2235 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/* 2236 */     _jspx_dependants.put("/jsp/includes/MonitorDiscoveryStatus.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2281 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2285 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2286 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2287 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2288 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2289 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2290 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2291 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2292 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2293 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2294 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2295 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2296 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2297 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2298 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2299 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2300 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2301 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2302 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2303 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2304 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2305 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2306 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2307 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2308 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2309 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2310 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2311 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2312 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2313 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2314 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2315 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2316 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2317 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2318 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2319 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2320 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2321 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2322 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2323 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2327 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2328 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2329 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2330 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2331 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2332 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2333 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2334 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction.release();
/* 2335 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/* 2336 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2337 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2338 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2339 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2340 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2341 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2342 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/* 2343 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2344 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2345 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.release();
/* 2346 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2347 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.release();
/* 2348 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2349 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2350 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/* 2351 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2352 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.release();
/* 2353 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.release();
/* 2354 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2355 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.release();
/* 2356 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody.release();
/* 2357 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty.release();
/* 2358 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/* 2359 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody.release();
/* 2360 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2361 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2362 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2363 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2370 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2373 */     JspWriter out = null;
/* 2374 */     Object page = this;
/* 2375 */     JspWriter _jspx_out = null;
/* 2376 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2380 */       response.setContentType("text/html;charset=UTF-8");
/* 2381 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2383 */       _jspx_page_context = pageContext;
/* 2384 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2385 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2386 */       session = pageContext.getSession();
/* 2387 */       out = pageContext.getOut();
/* 2388 */       _jspx_out = out;
/*      */       
/* 2390 */       out.write("<!DOCTYPE html>\n");
/* 2391 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\t\n");
/* 2392 */       out.write(10);
/*      */       
/* 2394 */       request.setAttribute("HelpKey", "Configuring URL");
/*      */       
/* 2396 */       out.write("\n\n\n\n\n\n\n\n");
/* 2397 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2399 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2400 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2401 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2403 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2405 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2407 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2409 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2410 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2411 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2412 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2415 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2416 */         String available = null;
/* 2417 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2418 */         out.write(10);
/*      */         
/* 2420 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2421 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2422 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2424 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2426 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2428 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2430 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2431 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2432 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2433 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2436 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2437 */           String unavailable = null;
/* 2438 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2439 */           out.write(10);
/*      */           
/* 2441 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2442 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2443 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2445 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2447 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2449 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2451 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2452 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2453 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2454 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2457 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2458 */             String unmanaged = null;
/* 2459 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2460 */             out.write(10);
/*      */             
/* 2462 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2463 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2464 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2466 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2468 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2470 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2472 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2473 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2474 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2475 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2478 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2479 */               String scheduled = null;
/* 2480 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2481 */               out.write(10);
/*      */               
/* 2483 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2484 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2485 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2487 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2489 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2491 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2493 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2494 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2495 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2496 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2499 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2500 */                 String critical = null;
/* 2501 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2502 */                 out.write(10);
/*      */                 
/* 2504 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2505 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2506 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2508 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2510 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2512 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2514 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2515 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2516 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2517 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2520 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2521 */                   String clear = null;
/* 2522 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2523 */                   out.write(10);
/*      */                   
/* 2525 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2526 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2527 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2529 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2531 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2533 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2535 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2536 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2537 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2538 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2541 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2542 */                     String warning = null;
/* 2543 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2544 */                     out.write(10);
/* 2545 */                     out.write(10);
/*      */                     
/* 2547 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2548 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2550 */                     out.write(10);
/* 2551 */                     out.write(10);
/* 2552 */                     out.write(10);
/* 2553 */                     out.write(10);
/* 2554 */                     out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2555 */                     out.write(10);
/* 2556 */                     out.write(10);
/* 2557 */                     out.write(10);
/* 2558 */                     ManagedApplication mo = null;
/* 2559 */                     mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2560 */                     if (mo == null) {
/* 2561 */                       mo = new ManagedApplication();
/* 2562 */                       _jspx_page_context.setAttribute("mo", mo, 1);
/*      */                     }
/* 2564 */                     out.write(10);
/* 2565 */                     Hashtable applications = null;
/* 2566 */                     synchronized (application) {
/* 2567 */                       applications = (Hashtable)_jspx_page_context.getAttribute("applications", 4);
/* 2568 */                       if (applications == null) {
/* 2569 */                         applications = new Hashtable();
/* 2570 */                         _jspx_page_context.setAttribute("applications", applications, 4);
/*      */                       }
/*      */                     }
/* 2573 */                     out.write(10);
/* 2574 */                     out.write(10);
/* 2575 */                     out.write(10);
/* 2576 */                     out.write("<!--$Id$-->\n\n\n\n");
/*      */                     
/*      */                     try
/*      */                     {
/* 2580 */                       boolean isprivilege = false;
/* 2581 */                       if (com.adventnet.appmanager.struts.beans.ClientDBUtil.isPrivilegedUser(request)) {
/* 2582 */                         isprivilege = true;
/*      */                       }
/* 2584 */                       request.setAttribute("checkForMonitorGroup", Boolean.valueOf(isprivilege));
/*      */                       
/*      */ 
/* 2587 */                       ArrayList dynamicSites = AlarmUtil.getSiteMonitorGroups();
/* 2588 */                       if (dynamicSites != null)
/*      */                       {
/* 2590 */                         request.setAttribute("dynamicSites", dynamicSites);
/*      */                       }
/*      */                       
/* 2593 */                       ArrayList mgList = new ArrayList();
/* 2594 */                       if (EnterpriseUtil.isIt360MSPEdition())
/*      */                       {
/* 2596 */                         com.adventnet.appmanager.struts.form.AMActionForm form = (com.adventnet.appmanager.struts.form.AMActionForm)request.getAttribute("AMActionForm");
/* 2597 */                         ArrayList orgs = AlarmUtil.getCustomerNames();
/*      */                         
/* 2599 */                         if (orgs != null)
/*      */                         {
/* 2601 */                           request.setAttribute("customers", orgs);
/*      */                         }
/*      */                         
/*      */ 
/* 2605 */                         if (form != null)
/*      */                         {
/* 2607 */                           String customerName = form.getOrganization();
/* 2608 */                           if (customerName != null)
/*      */                           {
/* 2610 */                             mgList = AlarmUtil.getSiteMonitorGroups(customerName);
/*      */                           }
/*      */                           
/*      */                         }
/*      */                         
/*      */ 
/*      */                       }
/* 2617 */                       else if (isprivilege)
/*      */                       {
/* 2619 */                         mgList = AlarmUtil.getConfiguredGroups(request, false, false, true);
/*      */                       }
/*      */                       else
/*      */                       {
/* 2623 */                         mgList = AlarmUtil.getConfiguredGroups(null, false, false, true);
/*      */                       }
/*      */                       
/* 2626 */                       if (mgList != null)
/*      */                       {
/* 2628 */                         request.setAttribute("applications", mgList);
/* 2629 */                         if (EnterpriseUtil.isAdminServer()) {
/* 2630 */                           ArrayList adminMGroups = getMGroupsCreatedInAdminServer(mgList);
/* 2631 */                           request.setAttribute("AllMonitorGroupsInAdminServer", mgList);
/* 2632 */                           request.setAttribute("MonitorGroupsCreatedInAdminServer", adminMGroups);
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 2638 */                       e.printStackTrace();
/*      */                     }
/*      */                     
/* 2641 */                     out.write(10);
/* 2642 */                     out.write(10);
/* 2643 */                     out.write(10);
/* 2644 */                     out.write(10);
/*      */                     
/* 2646 */                     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/* 2647 */                     String isDiscUrlMonitorComplete = (String)request.getAttribute("isDiscUrlMonitorComplete");
/* 2648 */                     String title = request.getParameter("type");
/* 2649 */                     boolean isDiscComplete = false;
/* 2650 */                     if ((isDiscUrlMonitorComplete != null) && (isDiscUrlMonitorComplete.equals("true")))
/*      */                     {
/* 2652 */                       isDiscComplete = true;
/*      */                     }
/* 2654 */                     if (hideFieldsForIT360 == null)
/*      */                     {
/* 2656 */                       hideFieldsForIT360 = (String)request.getAttribute("hideFieldsForIT360");
/*      */                     }
/*      */                     
/* 2659 */                     boolean hideFields = false;
/* 2660 */                     String hideStyle = "";
/* 2661 */                     if ((hideFieldsForIT360 != null) && (hideFieldsForIT360.equals("true")))
/*      */                     {
/* 2663 */                       hideFields = true;
/* 2664 */                       hideStyle = "hideContent";
/*      */                     }
/*      */                     
/*      */ 
/* 2668 */                     String resourceid = request.getParameter("resourceid");
/* 2669 */                     String haid = request.getParameter("haid");
/*      */                     
/* 2671 */                     String host = "http://localhost";
/* 2672 */                     String port = "80";
/*      */                     
/* 2674 */                     org.apache.struts.util.TokenProcessor token = org.apache.struts.util.TokenProcessor.getInstance();
/* 2675 */                     token.saveToken(request);
/*      */                     
/*      */ 
/*      */ 
/* 2679 */                     out.write("\n<html>\n<head>\n<LINK REL=StyleSheet HREF=\"/images/urlForm.css\" TYPE=\"text/css\" />\n</head>\n\n<!--  Your area begins here -->\n");
/*      */                     
/* 2681 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2682 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2683 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2685 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2686 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2687 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2689 */                         out.write(10);
/* 2690 */                         if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2692 */                         out.write(10);
/* 2693 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2695 */                         out.write(10);
/* 2696 */                         out.write(10);
/*      */                         
/* 2698 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2699 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2700 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2702 */                         _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                         
/* 2704 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2705 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2706 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2707 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2708 */                             out = _jspx_page_context.pushBody();
/* 2709 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2710 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2713 */                             out.write(10);
/*      */                             
/* 2715 */                             if (EnterpriseUtil.isAdminServer())
/*      */                             {
/* 2717 */                               out.write("\n<script>\nvar http = \"\";\n\nfunction resetname(name)\n{\n  if(name='groupname')\n  {\n    document.UrlForm.groupname.value='';\n  }\n  if(name='subgroupname')\n  {\n    document.UrlForm.subgroupname.value='';\n  }\n}\nfunction createGroup()\n{\n  if(document.UrlForm.groupname.value=='')\n  {\n    alert(\"");
/* 2718 */                               out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2719 */                               out.write("\");\n    hideDiv('group');\n    showDiv('creategroup');\n    document.UrlForm.groupname.focus();\n    return false;\n  }\n  else\n  {\n    hideDiv('creategroup');\n    var a=document.UrlForm.groupname.value;\n    url=\"/adminAction.do?method=createMonitorGroup&groupname=\"+encodeURIComponent(a); //NO I18N\n    http.open(\"GET\",url,true);\n    http.onreadystatechange = getActionTypes;\n    http.send(null);\n    showDiv('group');\n  }\n\n}  \nfunction check()\n{\n  hideDiv(\"creategroup\");\n  hideDiv(\"createsubgroup\");\n  hideDiv(\"groupmessage\");\n  showDiv(\"group\");\n}\nfunction createsubGroup()\n{\n  if(trimAll(document.UrlForm.haid.value) == '' || document.UrlForm.haid.value=='-')\n  {\n    alert(\"");
/* 2720 */                               out.print(FormatUtil.getString("am.webclient.monitorsubgroup.monitoralert"));
/* 2721 */                               out.write("\");    \n    document.UrlForm.haid.focus();\n  }\n  else\n  {\n    if(document.UrlForm.subgroupname.value=='')\n    {\n      alert(\"");
/* 2722 */                               out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2723 */                               out.write("\");\n      document.UrlForm.subgroupname.focus();\n      return false;\n    }\n    else\n    {\n      hideDiv('createsubgroup');\n      var a=document.UrlForm.subgroupname.value;\n      var haid=document.UrlForm.haid.value;\n      url=\"/adminAction.do?method=createSubGroup&haid=\"+haid+\"&subgroupname=\"+encodeURIComponent(a);  //NO I18N\n      http.open(\"GET\",url,true);\n      http.onreadystatechange = getActionTypes;\n      http.send(null);\n    }\n    showDiv('subgroup');\n    }\n\n}  \n\n function getActionTypes()\n{\n    if(http.readyState == 4)\n    {\n    var result = http.responseText;\n    var id=result;\n    var stringtokens=id.split(\",\");\n    sid = stringtokens[2];\n    sname=stringtokens[1];\n    sname=decodeURIComponent(stringtokens[1]);\n    smessage=stringtokens[0];\n    if (sname==null || sname=='undefined')\n    {\n      showDiv(\"groupmessage\");\n      document.getElementById(\"groupmessage\").innerHTML=smessage;\n    }\n    else\n    {\n      document.UrlForm.haid.options[document.UrlForm.haid.length] =new Option(sname,sid,false,true);\n");
/* 2724 */                               out.write("      hideDiv(\"creategroup\");\n      hideDiv(\"createsubgroup\");\n      hideDiv(\"group\");\n      showDiv(\"subgroup\");\n      showDiv(\"groupmessage\");\n      document.getElementById(\"groupmessage\").innerHTML=smessage;\n      }\n  }\n}\n\n</script>\n");
/*      */                               
/* 2726 */                               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2727 */                               _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2728 */                               _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 2730 */                               _jspx_th_logic_005fnotEmpty_005f0.setName("discoverystatus");
/* 2731 */                               int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2732 */                               if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                 for (;;) {
/* 2734 */                                   out.write(32);
/* 2735 */                                   out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n");
/*      */                                   
/* 2737 */                                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2738 */                                   _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2739 */                                   _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                   
/* 2741 */                                   _jspx_th_logic_005fnotEmpty_005f1.setName("discoverystatus");
/* 2742 */                                   int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2743 */                                   if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                     for (;;) {
/* 2745 */                                       out.write(10);
/*      */                                       
/*      */ 
/* 2748 */                                       if ((request.getAttribute("type") == null) || ((!request.getAttribute("type").equals("Script Monitor")) && ((request.getAttribute("basetype") == null) || ((request.getAttribute("basetype") != null) && (!request.getAttribute("basetype").equals("Script Monitor")))) && (!request.getAttribute("type").equals("QENGINE")) && (!request.getAttribute("type").equals("Web Service")) && (!request.getAttribute("type").equals("file")) && (!request.getAttribute("type").equals("directory")) && (!request.getAttribute("type").equals("File System Monitor")) && (!request.getAttribute("type").equals("Ping Monitor")) && (!request.getAttribute("type").equals("SAP-CCMS"))))
/*      */                                       {
/*      */ 
/* 2751 */                                         out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n  <tr>\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2752 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2753 */                                         out.write("</span> </td>\n    <td width=\"7%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2754 */                                         out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 2755 */                                         out.write("\n      </span> </td>\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2756 */                                         out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 2757 */                                         out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2758 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2759 */                                         out.write("\n </span></td>\n  <tr>\n  ");
/*      */                                         
/* 2761 */                                         int failedNumber = 1;
/*      */                                         
/* 2763 */                                         out.write(10);
/*      */                                         
/* 2765 */                                         IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2766 */                                         _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2767 */                                         _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                         
/* 2769 */                                         _jspx_th_logic_005fiterate_005f0.setName("discoverystatus");
/*      */                                         
/* 2771 */                                         _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                         
/* 2773 */                                         _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */                                         
/* 2775 */                                         _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2776 */                                         int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2777 */                                         if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2778 */                                           ArrayList row = null;
/* 2779 */                                           Integer i = null;
/* 2780 */                                           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2781 */                                             out = _jspx_page_context.pushBody();
/* 2782 */                                             _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2783 */                                             _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                           }
/* 2785 */                                           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2786 */                                           i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                           for (;;) {
/* 2788 */                                             out.write("\n<tr>\n<td height=\"18\" class=\"bodytext\">");
/* 2789 */                                             out.print(row.get(0));
/* 2790 */                                             out.write("</td>\n<td height=\"18\" class=\"bodytext\">");
/* 2791 */                                             out.print(row.get(1));
/* 2792 */                                             out.write("</td>\n\n    <td height=\"18\" class=\"bodytext\">\n      ");
/*      */                                             
/* 2794 */                                             if ((row.get(2).equals("Success")) || (row.get(2).equals("Present")))
/*      */                                             {
/* 2796 */                                               request.setAttribute("isDiscoverySuccess", "true");
/*      */                                               
/* 2798 */                                               out.write("\n      <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2799 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2800 */                                               out.write("\" align=\"absmiddle\">\n      ");
/*      */ 
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 2805 */                                               request.setAttribute("isDiscoverySuccess", "false");
/*      */                                               
/* 2807 */                                               out.write("\n      <img alt=\"");
/* 2808 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.notiniatedimagetip.text"));
/* 2809 */                                               out.write("\" src=\"/images/icon_monitor_failure.gif\" align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                             }
/*      */                                             
/*      */ 
/* 2813 */                                             out.write("\n      <span class=\"bodytextbold\">");
/* 2814 */                                             out.print(FormatUtil.getString(String.valueOf(row.get(2))));
/* 2815 */                                             out.write("</span> </td>\n\n      ");
/*      */                                             
/* 2817 */                                             pageContext.setAttribute("ret_msg", String.valueOf(row.get(3)));
/*      */                                             
/* 2819 */                                             out.write("\n                     ");
/*      */                                             
/* 2821 */                                             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2822 */                                             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2823 */                                             _jspx_th_c_005fif_005f0.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                             
/* 2825 */                                             _jspx_th_c_005fif_005f0.setTest("${ret_msg=='Monitoring Initiated Succesfully'}");
/* 2826 */                                             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2827 */                                             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                               for (;;) {
/* 2829 */                                                 out.write("\n                           <td height=\"18\" class=\"bodytext\">");
/* 2830 */                                                 out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 2831 */                                                 out.write("\n                                 ");
/* 2832 */                                                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2833 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 2837 */                                             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2838 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                                             }
/*      */                                             
/* 2841 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2842 */                                             out.write("\n                                       ");
/*      */                                             
/* 2844 */                                             IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2845 */                                             _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2846 */                                             _jspx_th_c_005fif_005f1.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                             
/* 2848 */                                             _jspx_th_c_005fif_005f1.setTest("${ret_msg!='Monitoring Initiated Succesfully'}");
/* 2849 */                                             int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2850 */                                             if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                               for (;;) {
/* 2852 */                                                 out.write("\n                                             <td height=\"18\" class=\"bodytext\">");
/* 2853 */                                                 out.print(row.get(3));
/* 2854 */                                                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                 
/* 2856 */                                                 if ((request.getAttribute("type") != null) && (!request.getAttribute("type").equals("All")) && (!request.getAttribute("type").equals("TELNET")) && (!request.getAttribute("type").equals("SNMP")) && (!request.getAttribute("type").equals("SERVICE")) && (!request.getAttribute("type").equals("JMX1.2-MX4J-RMI")) && (!request.getAttribute("type").equals("JDK1.5")) && (!request.getAttribute("type").equals("RMI")) && (!request.getAttribute("type").equals("PHP")) && (!request.getAttribute("type").equals("MAIL")) && (!request.getAttribute("type").equals("ORACLEDB")) && (!request.getAttribute("type").equals("MYSQLDB")) && (!request.getAttribute("type").equals("MSSQLDB")) && (!request.getAttribute("type").equals("DB2")) && (!request.getAttribute("type").equals("WTA")) && (!request.getAttribute("type").equals("SAP")))
/*      */                                                 {
/* 2858 */                                                   if (((String)pageContext.getAttribute("ret_msg")).indexOf(FormatUtil.getString("am.webclient.discovery.host.monitoradded.text")) == -1)
/*      */                                                   {
/* 2860 */                                                     String fWhr = request.getParameter("hideFieldsForIT360");
/* 2861 */                                                     if (fWhr == null)
/*      */                                                     {
/* 2863 */                                                       fWhr = (String)request.getAttribute("hideFieldsForIT360");
/*      */                                                     }
/*      */                                                     
/* 2866 */                                                     if (((fWhr == null) || (!fWhr.equals("true"))) && 
/* 2867 */                                                       (!request.getAttribute("type").equals("SYBASEDB")))
/*      */                                                     {
/* 2869 */                                                       out.write("\n\t\t\t\t\t\t\t\t\t\t\t &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"bodytext\" href=\"javascript:void(0)\" onClick=\"getAllDetailsOfHost()\">");
/* 2870 */                                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.diagnose.link"));
/* 2871 */                                                       out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                     }
/*      */                                                   } }
/* 2874 */                                                 if ((request.getAttribute("showSupportMessage") != null) && (failedNumber == 1) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                                                 {
/* 2876 */                                                   failedNumber++;
/*      */                                                   
/*      */ 
/* 2879 */                                                   out.write("\n\t\t\t\t\t\t\t\t\t\t<br>");
/* 2880 */                                                   out.print(FormatUtil.getString("am.webclient.discovery.host.support.link", new String[] { OEMUtil.getOEMString("product.talkback.mailid"), OEMUtil.getOEMString("product.tollfree.number") }));
/* 2881 */                                                   out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                                 }
/* 2883 */                                                 out.write("\n                                                   ");
/* 2884 */                                                 int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2885 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 2889 */                                             if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2890 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                                             }
/*      */                                             
/* 2893 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2894 */                                             out.write(10);
/* 2895 */                                             out.write(10);
/* 2896 */                                             out.write(10);
/*      */                                             
/*      */ 
/* 2899 */                                             if (row.size() > 4)
/*      */                                             {
/*      */ 
/* 2902 */                                               out.write("<br>\n");
/* 2903 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)row.get(4) }));
/* 2904 */                                               out.write(10);
/*      */                                             }
/*      */                                             
/*      */ 
/* 2908 */                                             out.write("\n</td>\n\n</tr>\n");
/* 2909 */                                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2910 */                                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2911 */                                             i = (Integer)_jspx_page_context.findAttribute("i");
/* 2912 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 2915 */                                           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2916 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 2919 */                                         if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2920 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                         }
/*      */                                         
/* 2923 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2924 */                                         out.write("\n</table>\n");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 2929 */                                         ArrayList al1 = (ArrayList)request.getAttribute("discoverystatus");
/*      */                                         
/* 2931 */                                         out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n<tr>\n");
/* 2932 */                                         String mtype = (String)request.getAttribute("type");
/* 2933 */                                         out.write(10);
/* 2934 */                                         if (mtype.equals("File System Monitor")) {
/* 2935 */                                           out.write("\n  <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2936 */                                           out.print(FormatUtil.getString("File/Directory Name"));
/* 2937 */                                           out.write("</span> </td>\n");
/* 2938 */                                         } else if ((((String)request.getAttribute("type")).equals("Ping Monitor")) || (((String)request.getAttribute("type")).equals("SAP-CCMS"))) {
/* 2939 */                                           out.write("\n\t<td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2940 */                                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2941 */                                           out.write("</span> </td>\n");
/*      */                                         } else {
/* 2943 */                                           out.write("\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2944 */                                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 2945 */                                           out.write("</span> </td>\n");
/*      */                                         }
/* 2947 */                                         out.write("\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2948 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 2949 */                                         out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2950 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2951 */                                         out.write("</span></td>\n  </tr>\n  <tr>\n   <td height=\"18\" class=\"bodytext\">");
/* 2952 */                                         out.print(al1.get(0));
/* 2953 */                                         out.write("</td>\n   <td height=\"18\" class=\"bodytext\">\n   ");
/*      */                                         
/* 2955 */                                         if (al1.get(1).equals("Success"))
/*      */                                         {
/* 2957 */                                           request.setAttribute("isDiscoverySuccess", "true");
/*      */                                           
/* 2959 */                                           out.write("\n   <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2960 */                                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2961 */                                           out.write("\" align=\"absmiddle\">\n    ");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 2966 */                                           request.setAttribute("isDiscoverySuccess", "false");
/*      */                                           
/*      */ 
/* 2969 */                                           out.write("\n      <img  src=\"/images/icon_monitor_failure.gif\"  align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                         }
/*      */                                         
/*      */ 
/* 2973 */                                         out.write("\n<span class=\"bodytextbold\">");
/* 2974 */                                         out.print(FormatUtil.getString(String.valueOf(al1.get(1))));
/* 2975 */                                         out.write("</span> </td>\n");
/*      */                                         
/* 2977 */                                         if (al1.get(1).equals("Success"))
/*      */                                         {
/* 2979 */                                           boolean isAdminServer = EnterpriseUtil.isAdminServer();
/* 2980 */                                           if (isAdminServer) {
/* 2981 */                                             String masDisplayName = (String)al1.get(3);
/* 2982 */                                             String format = FormatUtil.getString("am.webclient.admin.add.monitor.successfully.configured.text", new String[] { masDisplayName, "", (String)al1.get(2) });
/*      */                                             
/* 2984 */                                             out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\">");
/* 2985 */                                             out.print(format);
/* 2986 */                                             out.write("</td>\n");
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 2990 */                                             out.write("\t\t\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"> ");
/* 2991 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2992 */                                             out.write("<br> ");
/* 2993 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)al1.get(2) }));
/* 2994 */                                             out.write("</td>\n");
/*      */                                           }
/*      */                                           
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 3001 */                                           out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"><span class=\"bodytext\">");
/* 3002 */                                           out.print(al1.get(2));
/* 3003 */                                           out.write("</span></td>\n");
/*      */                                         }
/*      */                                         
/*      */ 
/* 3007 */                                         out.write("\n  </tr>\n</table>\n\n");
/*      */                                       }
/*      */                                       
/*      */ 
/* 3011 */                                       out.write(10);
/* 3012 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3013 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3017 */                                   if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3018 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                   }
/*      */                                   
/* 3021 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3022 */                                   out.write(10);
/* 3023 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3024 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3028 */                               if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3029 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                               }
/*      */                               
/* 3032 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3033 */                               out.write(10);
/*      */                             }
/*      */                             
/* 3036 */                             if ((!hideFields) || ((!isDiscComplete) && (hideFields)))
/*      */                             {
/*      */ 
/* 3039 */                               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td width=\"65%\" valign=\"top\">\n  ");
/*      */                               
/* 3041 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction.get(FormTag.class);
/* 3042 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 3043 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 3045 */                               _jspx_th_html_005fform_005f0.setAction("/updateUrl");
/*      */                               
/* 3047 */                               _jspx_th_html_005fform_005f0.setFocus("url");
/* 3048 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 3049 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 3051 */                                   out.write(10);
/* 3052 */                                   out.write(32);
/* 3053 */                                   out.write(32);
/* 3054 */                                   if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 3056 */                                   out.write(10);
/* 3057 */                                   out.write(10);
/* 3058 */                                   out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link rel=\"stylesheet\" href=\"/images/chosen.css\" />\n");
/* 3059 */                                   String message = (String)request.getAttribute("typemessage");
/*      */                                   
/* 3061 */                                   ManagedApplication mo1 = new ManagedApplication();
/* 3062 */                                   Properties props = com.adventnet.appmanager.util.Constants.getValueForNewMonitor();
/* 3063 */                                   boolean isConfMonitor = false;
/* 3064 */                                   ConfMonitorConfiguration confMonConfig = ConfMonitorConfiguration.getInstance();
/* 3065 */                                   if (message != null)
/*      */                                   {
/* 3067 */                                     out.write("\n    <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n    <tr>\n      <td><img src=\"/images/icon_message_success.gif\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"bodytext\">\n        ");
/* 3068 */                                     out.print(message);
/* 3069 */                                     out.write("</span> </td>\n    </tr>\n  </table>\n     ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3073 */                                   out.write("\n\n\n<table id=\"newResourceTypes\" width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n\t<td width=\"25%\" class=\"tableheading-monitor-config bodytext label-align addmonitor-label\">&nbsp;");
/* 3074 */                                   out.print(FormatUtil.getString("am.webclient.newresourcetypes.addmonitor.text"));
/* 3075 */                                   out.write("</td>\n    <td class=\"tableheading-monitor-config \" valign=\"middle\">\n");
/* 3076 */                                   if ("UrlSeq".equals(request.getParameter("type"))) {
/* 3077 */                                     DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 3078 */                                     if (frm != null) {
/* 3079 */                                       frm.set("type", "UrlSeq");
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 3083 */                                   if ("UrlMonitor".equals(request.getParameter("type"))) {
/* 3084 */                                     DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 3085 */                                     if (frm != null) {
/* 3086 */                                       frm.set("type", "UrlMonitor");
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 3090 */                                   if ("RBM".equals(request.getParameter("type"))) {
/* 3091 */                                     DynaActionForm frm = (DynaActionForm)request.getAttribute("RbmForm");
/* 3092 */                                     if (frm != null) {
/* 3093 */                                       frm.set("type", "RBM");
/*      */                                     }
/*      */                                   }
/*      */                                   
/*      */ 
/* 3098 */                                   out.write("\n\n    ");
/*      */                                   
/* 3100 */                                   IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3101 */                                   _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3102 */                                   _jspx_th_c_005fif_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 3104 */                                   _jspx_th_c_005fif_005f2.setTest("${empty param.wiz && empty param.fromAssociate}");
/* 3105 */                                   int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3106 */                                   if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                     for (;;) {
/* 3108 */                                       out.write("\n     ");
/*      */                                       
/* 3110 */                                       SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 3111 */                                       _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3112 */                                       _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fif_005f2);
/*      */                                       
/* 3114 */                                       _jspx_th_html_005fselect_005f0.setProperty("type");
/*      */                                       
/* 3116 */                                       _jspx_th_html_005fselect_005f0.setStyle("background-color:#FDFEF2; font-size:13px;");
/*      */                                       
/* 3118 */                                       _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */                                       
/* 3120 */                                       _jspx_th_html_005fselect_005f0.setOnchange("javascript:formReload()");
/* 3121 */                                       int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3122 */                                       if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3123 */                                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3124 */                                           out = _jspx_page_context.pushBody();
/* 3125 */                                           _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3126 */                                           _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3129 */                                           out.write("\n\n      <!-- If you are changing any of the values in this select box then kindly update the corresponding strings in HostDiscoveryHandler.java also-->\n      ");
/*      */                                           
/* 3131 */                                           if ((!com.adventnet.appmanager.util.Constants.isMinimizedversion()) || (com.adventnet.appmanager.util.Constants.getUserType().equals("F")) || (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                           {
/*      */ 
/*      */ 
/* 3135 */                                             out.write("\n\n\t <optgroup label=\"");
/* 3136 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3137 */                                             out.write("\">\n                                        \n                                        ");
/*      */                                             
/* 3139 */                                             OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3140 */                                             _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 3141 */                                             _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3143 */                                             _jspx_th_html_005foption_005f0.setValue("AIX");
/* 3144 */                                             int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 3145 */                                             if (_jspx_eval_html_005foption_005f0 != 0) {
/* 3146 */                                               if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3147 */                                                 out = _jspx_page_context.pushBody();
/* 3148 */                                                 _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 3149 */                                                 _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3152 */                                                 out.print(FormatUtil.getString("AIX"));
/* 3153 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 3154 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3157 */                                               if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3158 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3161 */                                             if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3162 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                             }
/*      */                                             
/* 3165 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 3166 */                                             out.write("\n                                        ");
/*      */                                             
/* 3168 */                                             OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3169 */                                             _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3170 */                                             _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3172 */                                             _jspx_th_html_005foption_005f1.setValue("AS400");
/* 3173 */                                             int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3174 */                                             if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3175 */                                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3176 */                                                 out = _jspx_page_context.pushBody();
/* 3177 */                                                 _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3178 */                                                 _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3181 */                                                 out.print(FormatUtil.getString("AS400/iSeries"));
/* 3182 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3183 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3186 */                                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3187 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3190 */                                             if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3191 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                             }
/*      */                                             
/* 3194 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 3195 */                                             out.write("\n                                        ");
/*      */                                             
/* 3197 */                                             OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3198 */                                             _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 3199 */                                             _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3201 */                                             _jspx_th_html_005foption_005f2.setValue("FreeBSD");
/* 3202 */                                             int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 3203 */                                             if (_jspx_eval_html_005foption_005f2 != 0) {
/* 3204 */                                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3205 */                                                 out = _jspx_page_context.pushBody();
/* 3206 */                                                 _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 3207 */                                                 _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3210 */                                                 out.print(FormatUtil.getString("FreeBSD/OpenBSD"));
/* 3211 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 3212 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3215 */                                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3216 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3219 */                                             if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 3220 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                             }
/*      */                                             
/* 3223 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 3224 */                                             out.write("\n                                        ");
/*      */                                             
/* 3226 */                                             OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3227 */                                             _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 3228 */                                             _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3230 */                                             _jspx_th_html_005foption_005f3.setValue("HP-UX");
/* 3231 */                                             int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 3232 */                                             if (_jspx_eval_html_005foption_005f3 != 0) {
/* 3233 */                                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3234 */                                                 out = _jspx_page_context.pushBody();
/* 3235 */                                                 _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 3236 */                                                 _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3239 */                                                 out.print(FormatUtil.getString("HP-UX  / Tru64"));
/* 3240 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 3241 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3244 */                                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3245 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3248 */                                             if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 3249 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                             }
/*      */                                             
/* 3252 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 3253 */                                             out.write("\n                                        ");
/*      */                                             
/* 3255 */                                             OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3256 */                                             _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 3257 */                                             _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3259 */                                             _jspx_th_html_005foption_005f4.setValue("Linux");
/* 3260 */                                             int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 3261 */                                             if (_jspx_eval_html_005foption_005f4 != 0) {
/* 3262 */                                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3263 */                                                 out = _jspx_page_context.pushBody();
/* 3264 */                                                 _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 3265 */                                                 _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3268 */                                                 out.print(FormatUtil.getString("Linux"));
/* 3269 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 3270 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3273 */                                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3274 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3277 */                                             if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 3278 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                             }
/*      */                                             
/* 3281 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 3282 */                                             out.write("\n                                        ");
/*      */                                             
/* 3284 */                                             OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3285 */                                             _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 3286 */                                             _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3288 */                                             _jspx_th_html_005foption_005f5.setValue("Mac OS");
/* 3289 */                                             int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 3290 */                                             if (_jspx_eval_html_005foption_005f5 != 0) {
/* 3291 */                                               if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3292 */                                                 out = _jspx_page_context.pushBody();
/* 3293 */                                                 _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 3294 */                                                 _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3297 */                                                 out.print(FormatUtil.getString("Mac OS"));
/* 3298 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 3299 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3302 */                                               if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3303 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3306 */                                             if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 3307 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                             }
/*      */                                             
/* 3310 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 3311 */                                             out.write("\n                                        ");
/*      */                                             
/* 3313 */                                             OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3314 */                                             _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 3315 */                                             _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3317 */                                             _jspx_th_html_005foption_005f6.setValue("Novell");
/* 3318 */                                             int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 3319 */                                             if (_jspx_eval_html_005foption_005f6 != 0) {
/* 3320 */                                               if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3321 */                                                 out = _jspx_page_context.pushBody();
/* 3322 */                                                 _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 3323 */                                                 _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3326 */                                                 out.print(FormatUtil.getString("Novell"));
/* 3327 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 3328 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3331 */                                               if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3332 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3335 */                                             if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 3336 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                             }
/*      */                                             
/* 3339 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 3340 */                                             out.write("\n                                        ");
/*      */                                             
/* 3342 */                                             OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3343 */                                             _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 3344 */                                             _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3346 */                                             _jspx_th_html_005foption_005f7.setValue("Sun Solaris");
/* 3347 */                                             int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 3348 */                                             if (_jspx_eval_html_005foption_005f7 != 0) {
/* 3349 */                                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3350 */                                                 out = _jspx_page_context.pushBody();
/* 3351 */                                                 _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 3352 */                                                 _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3355 */                                                 out.print(FormatUtil.getString("Sun Solaris"));
/* 3356 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 3357 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3360 */                                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3361 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3364 */                                             if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 3365 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                             }
/*      */                                             
/* 3368 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 3369 */                                             out.write("\n                                        ");
/*      */                                             
/* 3371 */                                             OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3372 */                                             _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 3373 */                                             _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3375 */                                             _jspx_th_html_005foption_005f8.setValue("Windows");
/* 3376 */                                             int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 3377 */                                             if (_jspx_eval_html_005foption_005f8 != 0) {
/* 3378 */                                               if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3379 */                                                 out = _jspx_page_context.pushBody();
/* 3380 */                                                 _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 3381 */                                                 _jspx_th_html_005foption_005f8.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3384 */                                                 out.print(FormatUtil.getString("Windows"));
/* 3385 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 3386 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3389 */                                               if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3390 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3393 */                                             if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 3394 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                             }
/*      */                                             
/* 3397 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/* 3398 */                                             out.write("\n                                        ");
/*      */                                             
/* 3400 */                                             OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3401 */                                             _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 3402 */                                             _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3404 */                                             _jspx_th_html_005foption_005f9.setValue("Windows Cluster");
/* 3405 */                                             int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 3406 */                                             if (_jspx_eval_html_005foption_005f9 != 0) {
/* 3407 */                                               if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3408 */                                                 out = _jspx_page_context.pushBody();
/* 3409 */                                                 _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 3410 */                                                 _jspx_th_html_005foption_005f9.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3413 */                                                 out.print(FormatUtil.getString("Windows Cluster"));
/* 3414 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 3415 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3418 */                                               if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3419 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3422 */                                             if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 3423 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                             }
/*      */                                             
/* 3426 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/* 3427 */                                             out.write("\n                                        \n\n  ");
/*      */                                             
/* 3429 */                                             ArrayList rows1 = mo1.getRows("select RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH from AM_ManagedResourceType,AM_MONITOR_TYPES where TYPEID=RESOURCETYPEID and RESOURCEGROUP='SYS' and AMCREATED='NO' ORDER BY UPPER(DISPLAYNAME)");
/* 3430 */                                             if ((rows1 != null) && (rows1.size() > 0))
/*      */                                             {
/* 3432 */                                               for (int i = 0; i < rows1.size(); i++)
/*      */                                               {
/* 3434 */                                                 ArrayList row = (ArrayList)rows1.get(i);
/* 3435 */                                                 String res = (String)row.get(0);
/* 3436 */                                                 String dname = (String)row.get(1);
/* 3437 */                                                 String values = props.getProperty(res);
/* 3438 */                                                 if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                 {
/*      */ 
/* 3441 */                                                   out.write("\n\t\t\t\t");
/*      */                                                   
/* 3443 */                                                   OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3444 */                                                   _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 3445 */                                                   _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3447 */                                                   _jspx_th_html_005foption_005f10.setValue(values);
/* 3448 */                                                   int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 3449 */                                                   if (_jspx_eval_html_005foption_005f10 != 0) {
/* 3450 */                                                     if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3451 */                                                       out = _jspx_page_context.pushBody();
/* 3452 */                                                       _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/* 3453 */                                                       _jspx_th_html_005foption_005f10.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3456 */                                                       out.write(32);
/* 3457 */                                                       out.print(FormatUtil.getString(dname));
/* 3458 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/* 3459 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3462 */                                                     if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3463 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3466 */                                                   if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 3467 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                                   }
/*      */                                                   
/* 3470 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/* 3471 */                                                   out.write("\n\t\t\t");
/*      */                                                 }
/*      */                                               }
/*      */                                             }
/*      */                                             
/*      */ 
/* 3477 */                                             String[] categoryLink = { "APP", "ERP", "TM", "SYS", "DBS", "SER", "URL", "MS", "MOM", "CAM", "VIR", "CLD" };
/*      */                                             
/* 3479 */                                             String[] categoryTitle = { "am.webclient.monitorgroupsecond.category.appserver", "am.webclient.monitorgroupsecond.category.erp", "am.webclient.monitorgroupsecond.category.transaction", "am.webclient.monitorgroupsecond.category.servers", "am.webclient.monitorgroupsecond.category.dbserver", "am.webclient.monitorgroupsecond.category.services", "am.webclient.monitorgroupsecond.category.webservices.title", "am.webclient.monitorgroupsecond.category.mailserver", "Middleware/Portal", "am.webclient.monitorgroupsecond.category.custom", "am.webclient.monitorgroupsecond.category.virtualserver", "am.webclient.monitorgroupsecond.category.cloudapps" };
/*      */                                             
/*      */ 
/* 3482 */                                             if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD"))
/*      */                                             {
/*      */ 
/* 3485 */                                               categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 3486 */                                               categoryTitle = com.adventnet.appmanager.util.Constants.categoryTitle;
/*      */                                             }
/* 3488 */                                             for (int c = 0; c < categoryLink.length; c++)
/*      */                                             {
/* 3490 */                                               ArrayList unSupportedTypes = com.adventnet.appmanager.util.Constants.getUnSupportedAsList();
/* 3491 */                                               if ((!categoryLink[c].equals("SYS")) && (!categoryLink[c].equals("NWD")) && (!categoryLink[c].equals("SAN")) && (!categoryLink[c].equals("EMO")) && (!categoryLink[c].equals("SCR")) && ((!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")) || (!unSupportedTypes.contains(categoryLink[c]))))
/*      */                                               {
/*      */ 
/*      */ 
/* 3495 */                                                 StringBuffer queryBuf = new StringBuffer();
/* 3496 */                                                 queryBuf.append("SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='");
/* 3497 */                                                 queryBuf.append(categoryLink[c] + "'");
/* 3498 */                                                 queryBuf.append(" ");
/* 3499 */                                                 queryBuf.append("and RESOURCETYPE in");
/* 3500 */                                                 queryBuf.append(" ");
/* 3501 */                                                 queryBuf.append(com.adventnet.appmanager.util.Constants.resourceTypes);
/* 3502 */                                                 if (categoryLink[c].equals("APP"))
/*      */                                                 {
/* 3504 */                                                   queryBuf.append(" ");
/* 3505 */                                                   queryBuf.append("and DISPLAYNAME NOT IN ('WebLogic Clusters')");
/* 3506 */                                                   queryBuf.append(" ");
/*      */                                                 }
/* 3508 */                                                 else if (categoryLink[c].equals("SER"))
/*      */                                                 {
/* 3510 */                                                   queryBuf.append(" ");
/* 3511 */                                                   queryBuf.append("and RESOURCETYPE<>'RMI'");
/* 3512 */                                                   queryBuf.append(" ");
/*      */                                                 }
/* 3514 */                                                 else if (categoryLink[c].equals("CAM"))
/*      */                                                 {
/* 3516 */                                                   queryBuf.append(" ");
/* 3517 */                                                   queryBuf.append("and RESOURCETYPE != 'directory'");
/* 3518 */                                                   queryBuf.append(" ");
/*      */                                                 }
/* 3520 */                                                 queryBuf.append(" ");
/* 3521 */                                                 queryBuf.append("ORDER BY UPPER(DISPLAYNAME)");
/* 3522 */                                                 ArrayList rows = mo1.getRows(queryBuf.toString());
/* 3523 */                                                 if ((rows != null) && (rows.size() != 0))
/*      */                                                 {
/*      */ 
/*      */ 
/*      */ 
/* 3528 */                                                   out.write("\n</optgroup>\t\t\t\t<optgroup label=\"");
/* 3529 */                                                   out.print(FormatUtil.getString(categoryTitle[c]));
/* 3530 */                                                   out.write(34);
/* 3531 */                                                   out.write(62);
/* 3532 */                                                   out.write(10);
/*      */                                                   
/*      */ 
/* 3535 */                                                   for (int i = 0; i < rows.size(); i++)
/*      */                                                   {
/* 3537 */                                                     ArrayList row = (ArrayList)rows.get(i);
/* 3538 */                                                     String res = (String)row.get(0);
/* 3539 */                                                     if (res.equals("file"))
/*      */                                                     {
/* 3541 */                                                       res = "File / Directory Monitor";
/*      */                                                     }
/* 3543 */                                                     String dname = (String)row.get(1);
/* 3544 */                                                     String values = props.getProperty(res);
/* 3545 */                                                     if ((!EnterpriseUtil.isCloudEdition()) || (!unSupportedTypes.contains(values)))
/*      */                                                     {
/*      */ 
/* 3548 */                                                       if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                       {
/*      */ 
/* 3551 */                                                         out.write("\n\t\t\t\t \t");
/*      */                                                         
/* 3553 */                                                         OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3554 */                                                         _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 3555 */                                                         _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                         
/* 3557 */                                                         _jspx_th_html_005foption_005f11.setValue(values);
/* 3558 */                                                         int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 3559 */                                                         if (_jspx_eval_html_005foption_005f11 != 0) {
/* 3560 */                                                           if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3561 */                                                             out = _jspx_page_context.pushBody();
/* 3562 */                                                             _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/* 3563 */                                                             _jspx_th_html_005foption_005f11.doInitBody();
/*      */                                                           }
/*      */                                                           for (;;) {
/* 3566 */                                                             out.write(32);
/* 3567 */                                                             out.print(FormatUtil.getString(dname));
/* 3568 */                                                             int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/* 3569 */                                                             if (evalDoAfterBody != 2)
/*      */                                                               break;
/*      */                                                           }
/* 3572 */                                                           if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3573 */                                                             out = _jspx_page_context.popBody();
/*      */                                                           }
/*      */                                                         }
/* 3576 */                                                         if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 3577 */                                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                                                         }
/*      */                                                         
/* 3580 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11);
/* 3581 */                                                         out.write("\n\t\t\t\t");
/*      */                                                       }
/*      */                                                     }
/*      */                                                   }
/*      */                                                   
/* 3586 */                                                   if (categoryLink[c].equals("VIR"))
/*      */                                                   {
/*      */ 
/* 3589 */                                                     out.write("\n\t\t\t\t\t");
/*      */                                                     
/* 3591 */                                                     OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3592 */                                                     _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 3593 */                                                     _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                     
/* 3595 */                                                     _jspx_th_html_005foption_005f12.setValue("VCenter");
/* 3596 */                                                     int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 3597 */                                                     if (_jspx_eval_html_005foption_005f12 != 0) {
/* 3598 */                                                       if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3599 */                                                         out = _jspx_page_context.pushBody();
/* 3600 */                                                         _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/* 3601 */                                                         _jspx_th_html_005foption_005f12.doInitBody();
/*      */                                                       }
/*      */                                                       for (;;) {
/* 3604 */                                                         out.write(32);
/* 3605 */                                                         out.print(FormatUtil.getString("am.webclient.addmonitor.vcenter.name"));
/* 3606 */                                                         int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/* 3607 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/* 3610 */                                                       if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3611 */                                                         out = _jspx_page_context.popBody();
/*      */                                                       }
/*      */                                                     }
/* 3614 */                                                     if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 3615 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                                                     }
/*      */                                                     
/* 3618 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/* 3619 */                                                     out.write("\n\t\t\t\t");
/*      */                                                   }
/*      */                                                 }
/*      */                                               } }
/* 3623 */                                             String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/* 3624 */                                             if (!usertype.equals("F"))
/*      */                                             {
/* 3626 */                                               if (((!EnterpriseUtil.isIt360MSPEdition()) || (!com.adventnet.appmanager.util.DBUtil.isSharedProbe())) && (!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                               {
/* 3628 */                                                 out.write("\n    </optgroup> <optgroup label=\"");
/* 3629 */                                                 out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3630 */                                                 out.write("\">\n     ");
/*      */                                                 
/* 3632 */                                                 OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3633 */                                                 _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/* 3634 */                                                 _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                 
/* 3636 */                                                 _jspx_th_html_005foption_005f13.setValue("All:1008");
/* 3637 */                                                 int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/* 3638 */                                                 if (_jspx_eval_html_005foption_005f13 != 0) {
/* 3639 */                                                   if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3640 */                                                     out = _jspx_page_context.pushBody();
/* 3641 */                                                     _jspx_th_html_005foption_005f13.setBodyContent((BodyContent)out);
/* 3642 */                                                     _jspx_th_html_005foption_005f13.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 3645 */                                                     out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3646 */                                                     out.write(32);
/* 3647 */                                                     int evalDoAfterBody = _jspx_th_html_005foption_005f13.doAfterBody();
/* 3648 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 3651 */                                                   if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3652 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 3655 */                                                 if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/* 3656 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                                                 }
/*      */                                                 
/* 3659 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/* 3660 */                                                 out.write("\n\n     ");
/*      */                                               }
/*      */                                               
/*      */                                             }
/*      */                                             
/*      */                                           }
/* 3666 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("LAMP"))
/*      */                                           {
/*      */ 
/* 3669 */                                             out.write("\n\t\t\t </optgroup>  <optgroup label=\"");
/* 3670 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3671 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3673 */                                             OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3674 */                                             _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/* 3675 */                                             _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3677 */                                             _jspx_th_html_005foption_005f14.setValue("SYSTEM:9999");
/* 3678 */                                             int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/* 3679 */                                             if (_jspx_eval_html_005foption_005f14 != 0) {
/* 3680 */                                               if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3681 */                                                 out = _jspx_page_context.pushBody();
/* 3682 */                                                 _jspx_th_html_005foption_005f14.setBodyContent((BodyContent)out);
/* 3683 */                                                 _jspx_th_html_005foption_005f14.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3686 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 3687 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f14.doAfterBody();
/* 3688 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3691 */                                               if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3692 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3695 */                                             if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/* 3696 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14); return;
/*      */                                             }
/*      */                                             
/* 3699 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/* 3700 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3701 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 3702 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3704 */                                             OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3705 */                                             _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/* 3706 */                                             _jspx_th_html_005foption_005f15.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3708 */                                             _jspx_th_html_005foption_005f15.setValue("MYSQLDB:3306");
/* 3709 */                                             int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/* 3710 */                                             if (_jspx_eval_html_005foption_005f15 != 0) {
/* 3711 */                                               if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3712 */                                                 out = _jspx_page_context.pushBody();
/* 3713 */                                                 _jspx_th_html_005foption_005f15.setBodyContent((BodyContent)out);
/* 3714 */                                                 _jspx_th_html_005foption_005f15.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3717 */                                                 out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 3718 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f15.doAfterBody();
/* 3719 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3722 */                                               if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3723 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3726 */                                             if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/* 3727 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15); return;
/*      */                                             }
/*      */                                             
/* 3730 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/* 3731 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3732 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 3733 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3735 */                                             OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3736 */                                             _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/* 3737 */                                             _jspx_th_html_005foption_005f16.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3739 */                                             _jspx_th_html_005foption_005f16.setValue("JMX1.2-MX4J-RMI:1099");
/* 3740 */                                             int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/* 3741 */                                             if (_jspx_eval_html_005foption_005f16 != 0) {
/* 3742 */                                               if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3743 */                                                 out = _jspx_page_context.pushBody();
/* 3744 */                                                 _jspx_th_html_005foption_005f16.setBodyContent((BodyContent)out);
/* 3745 */                                                 _jspx_th_html_005foption_005f16.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3748 */                                                 out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 3749 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f16.doAfterBody();
/* 3750 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3753 */                                               if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3754 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3757 */                                             if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/* 3758 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16); return;
/*      */                                             }
/*      */                                             
/* 3761 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/* 3762 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3764 */                                             OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3765 */                                             _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/* 3766 */                                             _jspx_th_html_005foption_005f17.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3768 */                                             _jspx_th_html_005foption_005f17.setValue("SERVICE:9090");
/* 3769 */                                             int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/* 3770 */                                             if (_jspx_eval_html_005foption_005f17 != 0) {
/* 3771 */                                               if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3772 */                                                 out = _jspx_page_context.pushBody();
/* 3773 */                                                 _jspx_th_html_005foption_005f17.setBodyContent((BodyContent)out);
/* 3774 */                                                 _jspx_th_html_005foption_005f17.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3777 */                                                 out.write(32);
/* 3778 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 3779 */                                                 out.write(32);
/* 3780 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f17.doAfterBody();
/* 3781 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3784 */                                               if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3785 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3788 */                                             if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/* 3789 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17); return;
/*      */                                             }
/*      */                                             
/* 3792 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/* 3793 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3795 */                                             OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3796 */                                             _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/* 3797 */                                             _jspx_th_html_005foption_005f18.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3799 */                                             _jspx_th_html_005foption_005f18.setValue("RMI:1099");
/* 3800 */                                             int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/* 3801 */                                             if (_jspx_eval_html_005foption_005f18 != 0) {
/* 3802 */                                               if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3803 */                                                 out = _jspx_page_context.pushBody();
/* 3804 */                                                 _jspx_th_html_005foption_005f18.setBodyContent((BodyContent)out);
/* 3805 */                                                 _jspx_th_html_005foption_005f18.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3808 */                                                 out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 3809 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f18.doAfterBody();
/* 3810 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3813 */                                               if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3814 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3817 */                                             if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/* 3818 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18); return;
/*      */                                             }
/*      */                                             
/* 3821 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/* 3822 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3824 */                                             OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3825 */                                             _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/* 3826 */                                             _jspx_th_html_005foption_005f19.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3828 */                                             _jspx_th_html_005foption_005f19.setValue("SNMP:161");
/* 3829 */                                             int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/* 3830 */                                             if (_jspx_eval_html_005foption_005f19 != 0) {
/* 3831 */                                               if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3832 */                                                 out = _jspx_page_context.pushBody();
/* 3833 */                                                 _jspx_th_html_005foption_005f19.setBodyContent((BodyContent)out);
/* 3834 */                                                 _jspx_th_html_005foption_005f19.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3837 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 3838 */                                                 out.write(" (V1 or V2c)");
/* 3839 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f19.doAfterBody();
/* 3840 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3843 */                                               if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3844 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3847 */                                             if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/* 3848 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19); return;
/*      */                                             }
/*      */                                             
/* 3851 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/* 3852 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3854 */                                             OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3855 */                                             _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/* 3856 */                                             _jspx_th_html_005foption_005f20.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3858 */                                             _jspx_th_html_005foption_005f20.setValue("TELNET:23");
/* 3859 */                                             int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/* 3860 */                                             if (_jspx_eval_html_005foption_005f20 != 0) {
/* 3861 */                                               if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3862 */                                                 out = _jspx_page_context.pushBody();
/* 3863 */                                                 _jspx_th_html_005foption_005f20.setBodyContent((BodyContent)out);
/* 3864 */                                                 _jspx_th_html_005foption_005f20.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3867 */                                                 out.print(FormatUtil.getString("Telnet"));
/* 3868 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f20.doAfterBody();
/* 3869 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3872 */                                               if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3873 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3876 */                                             if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/* 3877 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20); return;
/*      */                                             }
/*      */                                             
/* 3880 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/* 3881 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3882 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 3883 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3885 */                                             OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3886 */                                             _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/* 3887 */                                             _jspx_th_html_005foption_005f21.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3889 */                                             _jspx_th_html_005foption_005f21.setValue("APACHE:80");
/* 3890 */                                             int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/* 3891 */                                             if (_jspx_eval_html_005foption_005f21 != 0) {
/* 3892 */                                               if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3893 */                                                 out = _jspx_page_context.pushBody();
/* 3894 */                                                 _jspx_th_html_005foption_005f21.setBodyContent((BodyContent)out);
/* 3895 */                                                 _jspx_th_html_005foption_005f21.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3898 */                                                 out.write(32);
/* 3899 */                                                 out.print(FormatUtil.getString("Apache Server"));
/* 3900 */                                                 out.write(32);
/* 3901 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f21.doAfterBody();
/* 3902 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3905 */                                               if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3906 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3909 */                                             if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/* 3910 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21); return;
/*      */                                             }
/*      */                                             
/* 3913 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/* 3914 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3916 */                                             OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3917 */                                             _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/* 3918 */                                             _jspx_th_html_005foption_005f22.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3920 */                                             _jspx_th_html_005foption_005f22.setValue("PHP:80");
/* 3921 */                                             int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/* 3922 */                                             if (_jspx_eval_html_005foption_005f22 != 0) {
/* 3923 */                                               if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3924 */                                                 out = _jspx_page_context.pushBody();
/* 3925 */                                                 _jspx_th_html_005foption_005f22.setBodyContent((BodyContent)out);
/* 3926 */                                                 _jspx_th_html_005foption_005f22.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3929 */                                                 out.print(FormatUtil.getString("PHP"));
/* 3930 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f22.doAfterBody();
/* 3931 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3934 */                                               if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3935 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3938 */                                             if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/* 3939 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22); return;
/*      */                                             }
/*      */                                             
/* 3942 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/* 3943 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3945 */                                             OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3946 */                                             _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/* 3947 */                                             _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3949 */                                             _jspx_th_html_005foption_005f23.setValue("UrlMonitor");
/* 3950 */                                             int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/* 3951 */                                             if (_jspx_eval_html_005foption_005f23 != 0) {
/* 3952 */                                               if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3953 */                                                 out = _jspx_page_context.pushBody();
/* 3954 */                                                 _jspx_th_html_005foption_005f23.setBodyContent((BodyContent)out);
/* 3955 */                                                 _jspx_th_html_005foption_005f23.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3958 */                                                 out.print(FormatUtil.getString("HTTP-URLs"));
/* 3959 */                                                 out.write(32);
/* 3960 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f23.doAfterBody();
/* 3961 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3964 */                                               if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3965 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3968 */                                             if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/* 3969 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23); return;
/*      */                                             }
/*      */                                             
/* 3972 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23);
/* 3973 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3975 */                                             OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3976 */                                             _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/* 3977 */                                             _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3979 */                                             _jspx_th_html_005foption_005f24.setValue("UrlSeq");
/* 3980 */                                             int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/* 3981 */                                             if (_jspx_eval_html_005foption_005f24 != 0) {
/* 3982 */                                               if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3983 */                                                 out = _jspx_page_context.pushBody();
/* 3984 */                                                 _jspx_th_html_005foption_005f24.setBodyContent((BodyContent)out);
/* 3985 */                                                 _jspx_th_html_005foption_005f24.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3988 */                                                 out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 3989 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f24.doAfterBody();
/* 3990 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3993 */                                               if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3994 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3997 */                                             if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/* 3998 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24); return;
/*      */                                             }
/*      */                                             
/* 4001 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24);
/* 4002 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 4004 */                                             OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4005 */                                             _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/* 4006 */                                             _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4008 */                                             _jspx_th_html_005foption_005f25.setValue("WEB:80");
/* 4009 */                                             int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/* 4010 */                                             if (_jspx_eval_html_005foption_005f25 != 0) {
/* 4011 */                                               if (_jspx_eval_html_005foption_005f25 != 1) {
/* 4012 */                                                 out = _jspx_page_context.pushBody();
/* 4013 */                                                 _jspx_th_html_005foption_005f25.setBodyContent((BodyContent)out);
/* 4014 */                                                 _jspx_th_html_005foption_005f25.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4017 */                                                 out.write(32);
/* 4018 */                                                 out.print(FormatUtil.getString("Web Server"));
/* 4019 */                                                 out.write(32);
/* 4020 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f25.doAfterBody();
/* 4021 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4024 */                                               if (_jspx_eval_html_005foption_005f25 != 1) {
/* 4025 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4028 */                                             if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/* 4029 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25); return;
/*      */                                             }
/*      */                                             
/* 4032 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25);
/* 4033 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 4035 */                                             OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4036 */                                             _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/* 4037 */                                             _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4039 */                                             _jspx_th_html_005foption_005f26.setValue("Web Service");
/* 4040 */                                             int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/* 4041 */                                             if (_jspx_eval_html_005foption_005f26 != 0) {
/* 4042 */                                               if (_jspx_eval_html_005foption_005f26 != 1) {
/* 4043 */                                                 out = _jspx_page_context.pushBody();
/* 4044 */                                                 _jspx_th_html_005foption_005f26.setBodyContent((BodyContent)out);
/* 4045 */                                                 _jspx_th_html_005foption_005f26.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4048 */                                                 out.write(32);
/* 4049 */                                                 out.print(FormatUtil.getString("Web Service"));
/* 4050 */                                                 out.write(32);
/* 4051 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f26.doAfterBody();
/* 4052 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4055 */                                               if (_jspx_eval_html_005foption_005f26 != 1) {
/* 4056 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4059 */                                             if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/* 4060 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26); return;
/*      */                                             }
/*      */                                             
/* 4063 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26);
/* 4064 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4065 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 4066 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 4068 */                                             OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4069 */                                             _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/* 4070 */                                             _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4072 */                                             _jspx_th_html_005foption_005f27.setValue("MAIL:25");
/* 4073 */                                             int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/* 4074 */                                             if (_jspx_eval_html_005foption_005f27 != 0) {
/* 4075 */                                               if (_jspx_eval_html_005foption_005f27 != 1) {
/* 4076 */                                                 out = _jspx_page_context.pushBody();
/* 4077 */                                                 _jspx_th_html_005foption_005f27.setBodyContent((BodyContent)out);
/* 4078 */                                                 _jspx_th_html_005foption_005f27.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4081 */                                                 out.print(FormatUtil.getString("Mail Server"));
/* 4082 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f27.doAfterBody();
/* 4083 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4086 */                                               if (_jspx_eval_html_005foption_005f27 != 1) {
/* 4087 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4090 */                                             if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/* 4091 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27); return;
/*      */                                             }
/*      */                                             
/* 4094 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27);
/* 4095 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4096 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 4097 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 4099 */                                             OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4100 */                                             _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/* 4101 */                                             _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4103 */                                             _jspx_th_html_005foption_005f28.setValue("Custom-Application");
/* 4104 */                                             int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/* 4105 */                                             if (_jspx_eval_html_005foption_005f28 != 0) {
/* 4106 */                                               if (_jspx_eval_html_005foption_005f28 != 1) {
/* 4107 */                                                 out = _jspx_page_context.pushBody();
/* 4108 */                                                 _jspx_th_html_005foption_005f28.setBodyContent((BodyContent)out);
/* 4109 */                                                 _jspx_th_html_005foption_005f28.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4112 */                                                 out.write(32);
/* 4113 */                                                 out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4114 */                                                 out.write(32);
/* 4115 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f28.doAfterBody();
/* 4116 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4119 */                                               if (_jspx_eval_html_005foption_005f28 != 1) {
/* 4120 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4123 */                                             if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/* 4124 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28); return;
/*      */                                             }
/*      */                                             
/* 4127 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28);
/* 4128 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 4130 */                                             OptionTag _jspx_th_html_005foption_005f29 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4131 */                                             _jspx_th_html_005foption_005f29.setPageContext(_jspx_page_context);
/* 4132 */                                             _jspx_th_html_005foption_005f29.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4134 */                                             _jspx_th_html_005foption_005f29.setValue("Script Monitor");
/* 4135 */                                             int _jspx_eval_html_005foption_005f29 = _jspx_th_html_005foption_005f29.doStartTag();
/* 4136 */                                             if (_jspx_eval_html_005foption_005f29 != 0) {
/* 4137 */                                               if (_jspx_eval_html_005foption_005f29 != 1) {
/* 4138 */                                                 out = _jspx_page_context.pushBody();
/* 4139 */                                                 _jspx_th_html_005foption_005f29.setBodyContent((BodyContent)out);
/* 4140 */                                                 _jspx_th_html_005foption_005f29.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4143 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 4144 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f29.doAfterBody();
/* 4145 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4148 */                                               if (_jspx_eval_html_005foption_005f29 != 1) {
/* 4149 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4152 */                                             if (_jspx_th_html_005foption_005f29.doEndTag() == 5) {
/* 4153 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29); return;
/*      */                                             }
/*      */                                             
/* 4156 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29);
/* 4157 */                                             out.write("\n\n    ");
/*      */ 
/*      */                                           }
/* 4160 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("J2EE"))
/*      */                                           {
/*      */ 
/* 4163 */                                             out.write("\n        ");
/*      */                                             
/* 4165 */                                             OptionTag _jspx_th_html_005foption_005f30 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4166 */                                             _jspx_th_html_005foption_005f30.setPageContext(_jspx_page_context);
/* 4167 */                                             _jspx_th_html_005foption_005f30.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4169 */                                             _jspx_th_html_005foption_005f30.setValue("SYSTEM:9999");
/* 4170 */                                             int _jspx_eval_html_005foption_005f30 = _jspx_th_html_005foption_005f30.doStartTag();
/* 4171 */                                             if (_jspx_eval_html_005foption_005f30 != 0) {
/* 4172 */                                               if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4173 */                                                 out = _jspx_page_context.pushBody();
/* 4174 */                                                 _jspx_th_html_005foption_005f30.setBodyContent((BodyContent)out);
/* 4175 */                                                 _jspx_th_html_005foption_005f30.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4178 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4179 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f30.doAfterBody();
/* 4180 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4183 */                                               if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4184 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4187 */                                             if (_jspx_th_html_005foption_005f30.doEndTag() == 5) {
/* 4188 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30); return;
/*      */                                             }
/*      */                                             
/* 4191 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30);
/* 4192 */                                             out.write("\n       ");
/*      */                                             
/* 4194 */                                             OptionTag _jspx_th_html_005foption_005f31 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4195 */                                             _jspx_th_html_005foption_005f31.setPageContext(_jspx_page_context);
/* 4196 */                                             _jspx_th_html_005foption_005f31.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4198 */                                             _jspx_th_html_005foption_005f31.setValue("JBoss:8080");
/* 4199 */                                             int _jspx_eval_html_005foption_005f31 = _jspx_th_html_005foption_005f31.doStartTag();
/* 4200 */                                             if (_jspx_eval_html_005foption_005f31 != 0) {
/* 4201 */                                               if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4202 */                                                 out = _jspx_page_context.pushBody();
/* 4203 */                                                 _jspx_th_html_005foption_005f31.setBodyContent((BodyContent)out);
/* 4204 */                                                 _jspx_th_html_005foption_005f31.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4207 */                                                 out.write(32);
/* 4208 */                                                 out.print(FormatUtil.getString("JBoss Server"));
/* 4209 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f31.doAfterBody();
/* 4210 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4213 */                                               if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4214 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4217 */                                             if (_jspx_th_html_005foption_005f31.doEndTag() == 5) {
/* 4218 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31); return;
/*      */                                             }
/*      */                                             
/* 4221 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31);
/* 4222 */                                             out.write("\n      ");
/*      */                                             
/* 4224 */                                             OptionTag _jspx_th_html_005foption_005f32 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4225 */                                             _jspx_th_html_005foption_005f32.setPageContext(_jspx_page_context);
/* 4226 */                                             _jspx_th_html_005foption_005f32.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4228 */                                             _jspx_th_html_005foption_005f32.setValue("Tomcat:8080");
/* 4229 */                                             int _jspx_eval_html_005foption_005f32 = _jspx_th_html_005foption_005f32.doStartTag();
/* 4230 */                                             if (_jspx_eval_html_005foption_005f32 != 0) {
/* 4231 */                                               if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4232 */                                                 out = _jspx_page_context.pushBody();
/* 4233 */                                                 _jspx_th_html_005foption_005f32.setBodyContent((BodyContent)out);
/* 4234 */                                                 _jspx_th_html_005foption_005f32.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4237 */                                                 out.print(FormatUtil.getString("Tomcat Server"));
/* 4238 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f32.doAfterBody();
/* 4239 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4242 */                                               if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4243 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4246 */                                             if (_jspx_th_html_005foption_005f32.doEndTag() == 5) {
/* 4247 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32); return;
/*      */                                             }
/*      */                                             
/* 4250 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32);
/* 4251 */                                             out.write("\n       ");
/*      */                                             
/* 4253 */                                             OptionTag _jspx_th_html_005foption_005f33 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4254 */                                             _jspx_th_html_005foption_005f33.setPageContext(_jspx_page_context);
/* 4255 */                                             _jspx_th_html_005foption_005f33.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4257 */                                             _jspx_th_html_005foption_005f33.setValue("WEBLOGIC:7001");
/* 4258 */                                             int _jspx_eval_html_005foption_005f33 = _jspx_th_html_005foption_005f33.doStartTag();
/* 4259 */                                             if (_jspx_eval_html_005foption_005f33 != 0) {
/* 4260 */                                               if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4261 */                                                 out = _jspx_page_context.pushBody();
/* 4262 */                                                 _jspx_th_html_005foption_005f33.setBodyContent((BodyContent)out);
/* 4263 */                                                 _jspx_th_html_005foption_005f33.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4266 */                                                 out.write(32);
/* 4267 */                                                 out.print(FormatUtil.getString("WebLogic Server"));
/* 4268 */                                                 out.write(32);
/* 4269 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f33.doAfterBody();
/* 4270 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4273 */                                               if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4274 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4277 */                                             if (_jspx_th_html_005foption_005f33.doEndTag() == 5) {
/* 4278 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33); return;
/*      */                                             }
/*      */                                             
/* 4281 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33);
/* 4282 */                                             out.write("\n      ");
/*      */                                             
/* 4284 */                                             OptionTag _jspx_th_html_005foption_005f34 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4285 */                                             _jspx_th_html_005foption_005f34.setPageContext(_jspx_page_context);
/* 4286 */                                             _jspx_th_html_005foption_005f34.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4288 */                                             _jspx_th_html_005foption_005f34.setValue("WEBSPHERE:9080");
/* 4289 */                                             int _jspx_eval_html_005foption_005f34 = _jspx_th_html_005foption_005f34.doStartTag();
/* 4290 */                                             if (_jspx_eval_html_005foption_005f34 != 0) {
/* 4291 */                                               if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4292 */                                                 out = _jspx_page_context.pushBody();
/* 4293 */                                                 _jspx_th_html_005foption_005f34.setBodyContent((BodyContent)out);
/* 4294 */                                                 _jspx_th_html_005foption_005f34.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4297 */                                                 out.write(32);
/* 4298 */                                                 out.print(FormatUtil.getString("WebSphere Server"));
/* 4299 */                                                 out.write(32);
/* 4300 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f34.doAfterBody();
/* 4301 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4304 */                                               if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4305 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4308 */                                             if (_jspx_th_html_005foption_005f34.doEndTag() == 5) {
/* 4309 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34); return;
/*      */                                             }
/*      */                                             
/* 4312 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34);
/* 4313 */                                             out.write("\n      ");
/*      */                                             
/* 4315 */                                             OptionTag _jspx_th_html_005foption_005f35 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4316 */                                             _jspx_th_html_005foption_005f35.setPageContext(_jspx_page_context);
/* 4317 */                                             _jspx_th_html_005foption_005f35.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4319 */                                             _jspx_th_html_005foption_005f35.setValue("WTA:55555");
/* 4320 */                                             int _jspx_eval_html_005foption_005f35 = _jspx_th_html_005foption_005f35.doStartTag();
/* 4321 */                                             if (_jspx_eval_html_005foption_005f35 != 0) {
/* 4322 */                                               if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4323 */                                                 out = _jspx_page_context.pushBody();
/* 4324 */                                                 _jspx_th_html_005foption_005f35.setBodyContent((BodyContent)out);
/* 4325 */                                                 _jspx_th_html_005foption_005f35.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4328 */                                                 out.print(FormatUtil.getString("Web Transactions"));
/* 4329 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f35.doAfterBody();
/* 4330 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4333 */                                               if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4334 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4337 */                                             if (_jspx_th_html_005foption_005f35.doEndTag() == 5) {
/* 4338 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35); return;
/*      */                                             }
/*      */                                             
/* 4341 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35);
/* 4342 */                                             out.write("\n      ");
/*      */                                             
/* 4344 */                                             OptionTag _jspx_th_html_005foption_005f36 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4345 */                                             _jspx_th_html_005foption_005f36.setPageContext(_jspx_page_context);
/* 4346 */                                             _jspx_th_html_005foption_005f36.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4348 */                                             _jspx_th_html_005foption_005f36.setValue("MAIL:25");
/* 4349 */                                             int _jspx_eval_html_005foption_005f36 = _jspx_th_html_005foption_005f36.doStartTag();
/* 4350 */                                             if (_jspx_eval_html_005foption_005f36 != 0) {
/* 4351 */                                               if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4352 */                                                 out = _jspx_page_context.pushBody();
/* 4353 */                                                 _jspx_th_html_005foption_005f36.setBodyContent((BodyContent)out);
/* 4354 */                                                 _jspx_th_html_005foption_005f36.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4357 */                                                 out.print(FormatUtil.getString("Mail Server"));
/* 4358 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f36.doAfterBody();
/* 4359 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4362 */                                               if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4363 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4366 */                                             if (_jspx_th_html_005foption_005f36.doEndTag() == 5) {
/* 4367 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36); return;
/*      */                                             }
/*      */                                             
/* 4370 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36);
/* 4371 */                                             out.write("\n      ");
/*      */                                             
/* 4373 */                                             OptionTag _jspx_th_html_005foption_005f37 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4374 */                                             _jspx_th_html_005foption_005f37.setPageContext(_jspx_page_context);
/* 4375 */                                             _jspx_th_html_005foption_005f37.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4377 */                                             _jspx_th_html_005foption_005f37.setValue("JMX1.2-MX4J-RMI:1099");
/* 4378 */                                             int _jspx_eval_html_005foption_005f37 = _jspx_th_html_005foption_005f37.doStartTag();
/* 4379 */                                             if (_jspx_eval_html_005foption_005f37 != 0) {
/* 4380 */                                               if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4381 */                                                 out = _jspx_page_context.pushBody();
/* 4382 */                                                 _jspx_th_html_005foption_005f37.setBodyContent((BodyContent)out);
/* 4383 */                                                 _jspx_th_html_005foption_005f37.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4386 */                                                 out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 4387 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f37.doAfterBody();
/* 4388 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4391 */                                               if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4392 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4395 */                                             if (_jspx_th_html_005foption_005f37.doEndTag() == 5) {
/* 4396 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37); return;
/*      */                                             }
/*      */                                             
/* 4399 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37);
/* 4400 */                                             out.write("\n      ");
/*      */                                             
/* 4402 */                                             OptionTag _jspx_th_html_005foption_005f38 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4403 */                                             _jspx_th_html_005foption_005f38.setPageContext(_jspx_page_context);
/* 4404 */                                             _jspx_th_html_005foption_005f38.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4406 */                                             _jspx_th_html_005foption_005f38.setValue("SERVICE:9090");
/* 4407 */                                             int _jspx_eval_html_005foption_005f38 = _jspx_th_html_005foption_005f38.doStartTag();
/* 4408 */                                             if (_jspx_eval_html_005foption_005f38 != 0) {
/* 4409 */                                               if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4410 */                                                 out = _jspx_page_context.pushBody();
/* 4411 */                                                 _jspx_th_html_005foption_005f38.setBodyContent((BodyContent)out);
/* 4412 */                                                 _jspx_th_html_005foption_005f38.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4415 */                                                 out.write(32);
/* 4416 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 4417 */                                                 out.write(32);
/* 4418 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f38.doAfterBody();
/* 4419 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4422 */                                               if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4423 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4426 */                                             if (_jspx_th_html_005foption_005f38.doEndTag() == 5) {
/* 4427 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38); return;
/*      */                                             }
/*      */                                             
/* 4430 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38);
/* 4431 */                                             out.write("\n      ");
/*      */                                             
/* 4433 */                                             OptionTag _jspx_th_html_005foption_005f39 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4434 */                                             _jspx_th_html_005foption_005f39.setPageContext(_jspx_page_context);
/* 4435 */                                             _jspx_th_html_005foption_005f39.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4437 */                                             _jspx_th_html_005foption_005f39.setValue("RMI:1099");
/* 4438 */                                             int _jspx_eval_html_005foption_005f39 = _jspx_th_html_005foption_005f39.doStartTag();
/* 4439 */                                             if (_jspx_eval_html_005foption_005f39 != 0) {
/* 4440 */                                               if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4441 */                                                 out = _jspx_page_context.pushBody();
/* 4442 */                                                 _jspx_th_html_005foption_005f39.setBodyContent((BodyContent)out);
/* 4443 */                                                 _jspx_th_html_005foption_005f39.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4446 */                                                 out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 4447 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f39.doAfterBody();
/* 4448 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4451 */                                               if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4452 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4455 */                                             if (_jspx_th_html_005foption_005f39.doEndTag() == 5) {
/* 4456 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39); return;
/*      */                                             }
/*      */                                             
/* 4459 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39);
/* 4460 */                                             out.write("\n      ");
/*      */                                             
/* 4462 */                                             OptionTag _jspx_th_html_005foption_005f40 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4463 */                                             _jspx_th_html_005foption_005f40.setPageContext(_jspx_page_context);
/* 4464 */                                             _jspx_th_html_005foption_005f40.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4466 */                                             _jspx_th_html_005foption_005f40.setValue("SNMP:161");
/* 4467 */                                             int _jspx_eval_html_005foption_005f40 = _jspx_th_html_005foption_005f40.doStartTag();
/* 4468 */                                             if (_jspx_eval_html_005foption_005f40 != 0) {
/* 4469 */                                               if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4470 */                                                 out = _jspx_page_context.pushBody();
/* 4471 */                                                 _jspx_th_html_005foption_005f40.setBodyContent((BodyContent)out);
/* 4472 */                                                 _jspx_th_html_005foption_005f40.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4475 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 4476 */                                                 out.write(" (V1 or V2c)");
/* 4477 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f40.doAfterBody();
/* 4478 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4481 */                                               if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4482 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4485 */                                             if (_jspx_th_html_005foption_005f40.doEndTag() == 5) {
/* 4486 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40); return;
/*      */                                             }
/*      */                                             
/* 4489 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40);
/* 4490 */                                             out.write("\n      ");
/*      */                                             
/* 4492 */                                             OptionTag _jspx_th_html_005foption_005f41 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4493 */                                             _jspx_th_html_005foption_005f41.setPageContext(_jspx_page_context);
/* 4494 */                                             _jspx_th_html_005foption_005f41.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4496 */                                             _jspx_th_html_005foption_005f41.setValue("Custom-Application");
/* 4497 */                                             int _jspx_eval_html_005foption_005f41 = _jspx_th_html_005foption_005f41.doStartTag();
/* 4498 */                                             if (_jspx_eval_html_005foption_005f41 != 0) {
/* 4499 */                                               if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4500 */                                                 out = _jspx_page_context.pushBody();
/* 4501 */                                                 _jspx_th_html_005foption_005f41.setBodyContent((BodyContent)out);
/* 4502 */                                                 _jspx_th_html_005foption_005f41.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4505 */                                                 out.write(32);
/* 4506 */                                                 out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4507 */                                                 out.write(32);
/* 4508 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f41.doAfterBody();
/* 4509 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4512 */                                               if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4513 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4516 */                                             if (_jspx_th_html_005foption_005f41.doEndTag() == 5) {
/* 4517 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41); return;
/*      */                                             }
/*      */                                             
/* 4520 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41);
/* 4521 */                                             out.write("\n      ");
/*      */                                             
/* 4523 */                                             OptionTag _jspx_th_html_005foption_005f42 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4524 */                                             _jspx_th_html_005foption_005f42.setPageContext(_jspx_page_context);
/* 4525 */                                             _jspx_th_html_005foption_005f42.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4527 */                                             _jspx_th_html_005foption_005f42.setValue("Script Monitor");
/* 4528 */                                             int _jspx_eval_html_005foption_005f42 = _jspx_th_html_005foption_005f42.doStartTag();
/* 4529 */                                             if (_jspx_eval_html_005foption_005f42 != 0) {
/* 4530 */                                               if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4531 */                                                 out = _jspx_page_context.pushBody();
/* 4532 */                                                 _jspx_th_html_005foption_005f42.setBodyContent((BodyContent)out);
/* 4533 */                                                 _jspx_th_html_005foption_005f42.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4536 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 4537 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f42.doAfterBody();
/* 4538 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4541 */                                               if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4542 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4545 */                                             if (_jspx_th_html_005foption_005f42.doEndTag() == 5) {
/* 4546 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42); return;
/*      */                                             }
/*      */                                             
/* 4549 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42);
/* 4550 */                                             out.write("\n       ");
/*      */ 
/*      */                                           }
/* 4553 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("WINDOWS"))
/*      */                                           {
/*      */ 
/* 4556 */                                             out.write("\n        ");
/*      */                                             
/* 4558 */                                             OptionTag _jspx_th_html_005foption_005f43 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4559 */                                             _jspx_th_html_005foption_005f43.setPageContext(_jspx_page_context);
/* 4560 */                                             _jspx_th_html_005foption_005f43.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4562 */                                             _jspx_th_html_005foption_005f43.setValue("SYSTEM:9999");
/* 4563 */                                             int _jspx_eval_html_005foption_005f43 = _jspx_th_html_005foption_005f43.doStartTag();
/* 4564 */                                             if (_jspx_eval_html_005foption_005f43 != 0) {
/* 4565 */                                               if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4566 */                                                 out = _jspx_page_context.pushBody();
/* 4567 */                                                 _jspx_th_html_005foption_005f43.setBodyContent((BodyContent)out);
/* 4568 */                                                 _jspx_th_html_005foption_005f43.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4571 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4572 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f43.doAfterBody();
/* 4573 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4576 */                                               if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4577 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4580 */                                             if (_jspx_th_html_005foption_005f43.doEndTag() == 5) {
/* 4581 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43); return;
/*      */                                             }
/*      */                                             
/* 4584 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43);
/* 4585 */                                             out.write("\n       ");
/*      */                                             
/* 4587 */                                             OptionTag _jspx_th_html_005foption_005f44 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4588 */                                             _jspx_th_html_005foption_005f44.setPageContext(_jspx_page_context);
/* 4589 */                                             _jspx_th_html_005foption_005f44.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4591 */                                             _jspx_th_html_005foption_005f44.setValue(".Net:9080");
/* 4592 */                                             int _jspx_eval_html_005foption_005f44 = _jspx_th_html_005foption_005f44.doStartTag();
/* 4593 */                                             if (_jspx_eval_html_005foption_005f44 != 0) {
/* 4594 */                                               if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4595 */                                                 out = _jspx_page_context.pushBody();
/* 4596 */                                                 _jspx_th_html_005foption_005f44.setBodyContent((BodyContent)out);
/* 4597 */                                                 _jspx_th_html_005foption_005f44.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4600 */                                                 out.print(FormatUtil.getString("Microsoft .NET"));
/* 4601 */                                                 out.write(32);
/* 4602 */                                                 out.write(32);
/* 4603 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f44.doAfterBody();
/* 4604 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4607 */                                               if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4608 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4611 */                                             if (_jspx_th_html_005foption_005f44.doEndTag() == 5) {
/* 4612 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44); return;
/*      */                                             }
/*      */                                             
/* 4615 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44);
/* 4616 */                                             out.write("\n      ");
/*      */                                             
/* 4618 */                                             OptionTag _jspx_th_html_005foption_005f45 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4619 */                                             _jspx_th_html_005foption_005f45.setPageContext(_jspx_page_context);
/* 4620 */                                             _jspx_th_html_005foption_005f45.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4622 */                                             _jspx_th_html_005foption_005f45.setValue("MSSQLDB:1433");
/* 4623 */                                             int _jspx_eval_html_005foption_005f45 = _jspx_th_html_005foption_005f45.doStartTag();
/* 4624 */                                             if (_jspx_eval_html_005foption_005f45 != 0) {
/* 4625 */                                               if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4626 */                                                 out = _jspx_page_context.pushBody();
/* 4627 */                                                 _jspx_th_html_005foption_005f45.setBodyContent((BodyContent)out);
/* 4628 */                                                 _jspx_th_html_005foption_005f45.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4631 */                                                 out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4632 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f45.doAfterBody();
/* 4633 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4636 */                                               if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4637 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4640 */                                             if (_jspx_th_html_005foption_005f45.doEndTag() == 5) {
/* 4641 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45); return;
/*      */                                             }
/*      */                                             
/* 4644 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45);
/* 4645 */                                             out.write("\n      ");
/*      */                                             
/* 4647 */                                             OptionTag _jspx_th_html_005foption_005f46 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4648 */                                             _jspx_th_html_005foption_005f46.setPageContext(_jspx_page_context);
/* 4649 */                                             _jspx_th_html_005foption_005f46.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4651 */                                             _jspx_th_html_005foption_005f46.setValue("Exchange:25");
/* 4652 */                                             int _jspx_eval_html_005foption_005f46 = _jspx_th_html_005foption_005f46.doStartTag();
/* 4653 */                                             if (_jspx_eval_html_005foption_005f46 != 0) {
/* 4654 */                                               if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4655 */                                                 out = _jspx_page_context.pushBody();
/* 4656 */                                                 _jspx_th_html_005foption_005f46.setBodyContent((BodyContent)out);
/* 4657 */                                                 _jspx_th_html_005foption_005f46.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4660 */                                                 out.print(FormatUtil.getString("Exchange Server"));
/* 4661 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f46.doAfterBody();
/* 4662 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4665 */                                               if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4666 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4669 */                                             if (_jspx_th_html_005foption_005f46.doEndTag() == 5) {
/* 4670 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46); return;
/*      */                                             }
/*      */                                             
/* 4673 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46);
/* 4674 */                                             out.write("\n\t  ");
/*      */                                             
/* 4676 */                                             OptionTag _jspx_th_html_005foption_005f47 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4677 */                                             _jspx_th_html_005foption_005f47.setPageContext(_jspx_page_context);
/* 4678 */                                             _jspx_th_html_005foption_005f47.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4680 */                                             _jspx_th_html_005foption_005f47.setValue("IIS:80");
/* 4681 */                                             int _jspx_eval_html_005foption_005f47 = _jspx_th_html_005foption_005f47.doStartTag();
/* 4682 */                                             if (_jspx_eval_html_005foption_005f47 != 0) {
/* 4683 */                                               if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4684 */                                                 out = _jspx_page_context.pushBody();
/* 4685 */                                                 _jspx_th_html_005foption_005f47.setBodyContent((BodyContent)out);
/* 4686 */                                                 _jspx_th_html_005foption_005f47.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4689 */                                                 out.write(32);
/* 4690 */                                                 out.print(FormatUtil.getString("IIS Server"));
/* 4691 */                                                 out.write(32);
/* 4692 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f47.doAfterBody();
/* 4693 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4696 */                                               if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4697 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4700 */                                             if (_jspx_th_html_005foption_005f47.doEndTag() == 5) {
/* 4701 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47); return;
/*      */                                             }
/*      */                                             
/* 4704 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47);
/* 4705 */                                             out.write("\n      ");
/*      */                                             
/* 4707 */                                             OptionTag _jspx_th_html_005foption_005f48 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4708 */                                             _jspx_th_html_005foption_005f48.setPageContext(_jspx_page_context);
/* 4709 */                                             _jspx_th_html_005foption_005f48.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4711 */                                             _jspx_th_html_005foption_005f48.setValue("SERVICE:9090");
/* 4712 */                                             int _jspx_eval_html_005foption_005f48 = _jspx_th_html_005foption_005f48.doStartTag();
/* 4713 */                                             if (_jspx_eval_html_005foption_005f48 != 0) {
/* 4714 */                                               if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4715 */                                                 out = _jspx_page_context.pushBody();
/* 4716 */                                                 _jspx_th_html_005foption_005f48.setBodyContent((BodyContent)out);
/* 4717 */                                                 _jspx_th_html_005foption_005f48.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4720 */                                                 out.write(32);
/* 4721 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 4722 */                                                 out.write(32);
/* 4723 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f48.doAfterBody();
/* 4724 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4727 */                                               if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4728 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4731 */                                             if (_jspx_th_html_005foption_005f48.doEndTag() == 5) {
/* 4732 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48); return;
/*      */                                             }
/*      */                                             
/* 4735 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48);
/* 4736 */                                             out.write("\n\t  ");
/*      */                                             
/* 4738 */                                             OptionTag _jspx_th_html_005foption_005f49 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4739 */                                             _jspx_th_html_005foption_005f49.setPageContext(_jspx_page_context);
/* 4740 */                                             _jspx_th_html_005foption_005f49.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4742 */                                             _jspx_th_html_005foption_005f49.setValue("SNMP:161");
/* 4743 */                                             int _jspx_eval_html_005foption_005f49 = _jspx_th_html_005foption_005f49.doStartTag();
/* 4744 */                                             if (_jspx_eval_html_005foption_005f49 != 0) {
/* 4745 */                                               if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4746 */                                                 out = _jspx_page_context.pushBody();
/* 4747 */                                                 _jspx_th_html_005foption_005f49.setBodyContent((BodyContent)out);
/* 4748 */                                                 _jspx_th_html_005foption_005f49.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4751 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 4752 */                                                 out.write(" (V1 or V2c)");
/* 4753 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f49.doAfterBody();
/* 4754 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4757 */                                               if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4758 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4761 */                                             if (_jspx_th_html_005foption_005f49.doEndTag() == 5) {
/* 4762 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49); return;
/*      */                                             }
/*      */                                             
/* 4765 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49);
/* 4766 */                                             out.write("\n      ");
/*      */                                             
/* 4768 */                                             OptionTag _jspx_th_html_005foption_005f50 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4769 */                                             _jspx_th_html_005foption_005f50.setPageContext(_jspx_page_context);
/* 4770 */                                             _jspx_th_html_005foption_005f50.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4772 */                                             _jspx_th_html_005foption_005f50.setValue("Script Monitor");
/* 4773 */                                             int _jspx_eval_html_005foption_005f50 = _jspx_th_html_005foption_005f50.doStartTag();
/* 4774 */                                             if (_jspx_eval_html_005foption_005f50 != 0) {
/* 4775 */                                               if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4776 */                                                 out = _jspx_page_context.pushBody();
/* 4777 */                                                 _jspx_th_html_005foption_005f50.setBodyContent((BodyContent)out);
/* 4778 */                                                 _jspx_th_html_005foption_005f50.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4781 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 4782 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f50.doAfterBody();
/* 4783 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4786 */                                               if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4787 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4790 */                                             if (_jspx_th_html_005foption_005f50.doEndTag() == 5) {
/* 4791 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50); return;
/*      */                                             }
/*      */                                             
/* 4794 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50);
/* 4795 */                                             out.write(10);
/*      */ 
/*      */                                           }
/* 4798 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("DATABASE"))
/*      */                                           {
/*      */ 
/* 4801 */                                             out.write("\n\t\t</optgroup>\t<optgroup label=\"");
/* 4802 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 4803 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 4805 */                                             OptionTag _jspx_th_html_005foption_005f51 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4806 */                                             _jspx_th_html_005foption_005f51.setPageContext(_jspx_page_context);
/* 4807 */                                             _jspx_th_html_005foption_005f51.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4809 */                                             _jspx_th_html_005foption_005f51.setValue("SYSTEM:9999");
/* 4810 */                                             int _jspx_eval_html_005foption_005f51 = _jspx_th_html_005foption_005f51.doStartTag();
/* 4811 */                                             if (_jspx_eval_html_005foption_005f51 != 0) {
/* 4812 */                                               if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4813 */                                                 out = _jspx_page_context.pushBody();
/* 4814 */                                                 _jspx_th_html_005foption_005f51.setBodyContent((BodyContent)out);
/* 4815 */                                                 _jspx_th_html_005foption_005f51.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4818 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4819 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f51.doAfterBody();
/* 4820 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4823 */                                               if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4824 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4827 */                                             if (_jspx_th_html_005foption_005f51.doEndTag() == 5) {
/* 4828 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51); return;
/*      */                                             }
/*      */                                             
/* 4831 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51);
/* 4832 */                                             out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4833 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 4834 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 4836 */                                             OptionTag _jspx_th_html_005foption_005f52 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4837 */                                             _jspx_th_html_005foption_005f52.setPageContext(_jspx_page_context);
/* 4838 */                                             _jspx_th_html_005foption_005f52.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4840 */                                             _jspx_th_html_005foption_005f52.setValue("DB2:50000");
/* 4841 */                                             int _jspx_eval_html_005foption_005f52 = _jspx_th_html_005foption_005f52.doStartTag();
/* 4842 */                                             if (_jspx_eval_html_005foption_005f52 != 0) {
/* 4843 */                                               if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4844 */                                                 out = _jspx_page_context.pushBody();
/* 4845 */                                                 _jspx_th_html_005foption_005f52.setBodyContent((BodyContent)out);
/* 4846 */                                                 _jspx_th_html_005foption_005f52.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4849 */                                                 out.print(FormatUtil.getString("am.webclient.db2.servertype"));
/* 4850 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f52.doAfterBody();
/* 4851 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4854 */                                               if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4855 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4858 */                                             if (_jspx_th_html_005foption_005f52.doEndTag() == 5) {
/* 4859 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52); return;
/*      */                                             }
/*      */                                             
/* 4862 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52);
/* 4863 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4865 */                                             OptionTag _jspx_th_html_005foption_005f53 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4866 */                                             _jspx_th_html_005foption_005f53.setPageContext(_jspx_page_context);
/* 4867 */                                             _jspx_th_html_005foption_005f53.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4869 */                                             _jspx_th_html_005foption_005f53.setValue("MSSQLDB:1433");
/* 4870 */                                             int _jspx_eval_html_005foption_005f53 = _jspx_th_html_005foption_005f53.doStartTag();
/* 4871 */                                             if (_jspx_eval_html_005foption_005f53 != 0) {
/* 4872 */                                               if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4873 */                                                 out = _jspx_page_context.pushBody();
/* 4874 */                                                 _jspx_th_html_005foption_005f53.setBodyContent((BodyContent)out);
/* 4875 */                                                 _jspx_th_html_005foption_005f53.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4878 */                                                 out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4879 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f53.doAfterBody();
/* 4880 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4883 */                                               if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4884 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4887 */                                             if (_jspx_th_html_005foption_005f53.doEndTag() == 5) {
/* 4888 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53); return;
/*      */                                             }
/*      */                                             
/* 4891 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53);
/* 4892 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4894 */                                             OptionTag _jspx_th_html_005foption_005f54 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4895 */                                             _jspx_th_html_005foption_005f54.setPageContext(_jspx_page_context);
/* 4896 */                                             _jspx_th_html_005foption_005f54.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4898 */                                             _jspx_th_html_005foption_005f54.setValue("MYSQLDB:3306");
/* 4899 */                                             int _jspx_eval_html_005foption_005f54 = _jspx_th_html_005foption_005f54.doStartTag();
/* 4900 */                                             if (_jspx_eval_html_005foption_005f54 != 0) {
/* 4901 */                                               if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4902 */                                                 out = _jspx_page_context.pushBody();
/* 4903 */                                                 _jspx_th_html_005foption_005f54.setBodyContent((BodyContent)out);
/* 4904 */                                                 _jspx_th_html_005foption_005f54.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4907 */                                                 out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 4908 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f54.doAfterBody();
/* 4909 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4912 */                                               if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4913 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4916 */                                             if (_jspx_th_html_005foption_005f54.doEndTag() == 5) {
/* 4917 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54); return;
/*      */                                             }
/*      */                                             
/* 4920 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54);
/* 4921 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4923 */                                             OptionTag _jspx_th_html_005foption_005f55 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4924 */                                             _jspx_th_html_005foption_005f55.setPageContext(_jspx_page_context);
/* 4925 */                                             _jspx_th_html_005foption_005f55.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4927 */                                             _jspx_th_html_005foption_005f55.setValue("ORACLEDB:1521");
/* 4928 */                                             int _jspx_eval_html_005foption_005f55 = _jspx_th_html_005foption_005f55.doStartTag();
/* 4929 */                                             if (_jspx_eval_html_005foption_005f55 != 0) {
/* 4930 */                                               if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4931 */                                                 out = _jspx_page_context.pushBody();
/* 4932 */                                                 _jspx_th_html_005foption_005f55.setBodyContent((BodyContent)out);
/* 4933 */                                                 _jspx_th_html_005foption_005f55.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4936 */                                                 out.print(FormatUtil.getString("am.webclient.oracle.servertype"));
/* 4937 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f55.doAfterBody();
/* 4938 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4941 */                                               if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4942 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4945 */                                             if (_jspx_th_html_005foption_005f55.doEndTag() == 5) {
/* 4946 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55); return;
/*      */                                             }
/*      */                                             
/* 4949 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55);
/* 4950 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4952 */                                             OptionTag _jspx_th_html_005foption_005f56 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4953 */                                             _jspx_th_html_005foption_005f56.setPageContext(_jspx_page_context);
/* 4954 */                                             _jspx_th_html_005foption_005f56.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4956 */                                             _jspx_th_html_005foption_005f56.setValue("SYBASEDB:5000");
/* 4957 */                                             int _jspx_eval_html_005foption_005f56 = _jspx_th_html_005foption_005f56.doStartTag();
/* 4958 */                                             if (_jspx_eval_html_005foption_005f56 != 0) {
/* 4959 */                                               if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4960 */                                                 out = _jspx_page_context.pushBody();
/* 4961 */                                                 _jspx_th_html_005foption_005f56.setBodyContent((BodyContent)out);
/* 4962 */                                                 _jspx_th_html_005foption_005f56.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4965 */                                                 out.print(FormatUtil.getString("Sybase"));
/* 4966 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f56.doAfterBody();
/* 4967 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4970 */                                               if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4971 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4974 */                                             if (_jspx_th_html_005foption_005f56.doEndTag() == 5) {
/* 4975 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56); return;
/*      */                                             }
/*      */                                             
/* 4978 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56);
/* 4979 */                                             out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4980 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 4981 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 4983 */                                             OptionTag _jspx_th_html_005foption_005f57 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4984 */                                             _jspx_th_html_005foption_005f57.setPageContext(_jspx_page_context);
/* 4985 */                                             _jspx_th_html_005foption_005f57.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4987 */                                             _jspx_th_html_005foption_005f57.setValue("SERVICE:9090");
/* 4988 */                                             int _jspx_eval_html_005foption_005f57 = _jspx_th_html_005foption_005f57.doStartTag();
/* 4989 */                                             if (_jspx_eval_html_005foption_005f57 != 0) {
/* 4990 */                                               if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4991 */                                                 out = _jspx_page_context.pushBody();
/* 4992 */                                                 _jspx_th_html_005foption_005f57.setBodyContent((BodyContent)out);
/* 4993 */                                                 _jspx_th_html_005foption_005f57.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4996 */                                                 out.write(32);
/* 4997 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 4998 */                                                 out.write(32);
/* 4999 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f57.doAfterBody();
/* 5000 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5003 */                                               if (_jspx_eval_html_005foption_005f57 != 1) {
/* 5004 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5007 */                                             if (_jspx_th_html_005foption_005f57.doEndTag() == 5) {
/* 5008 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57); return;
/*      */                                             }
/*      */                                             
/* 5011 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57);
/* 5012 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 5014 */                                             OptionTag _jspx_th_html_005foption_005f58 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5015 */                                             _jspx_th_html_005foption_005f58.setPageContext(_jspx_page_context);
/* 5016 */                                             _jspx_th_html_005foption_005f58.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 5018 */                                             _jspx_th_html_005foption_005f58.setValue("SNMP:161");
/* 5019 */                                             int _jspx_eval_html_005foption_005f58 = _jspx_th_html_005foption_005f58.doStartTag();
/* 5020 */                                             if (_jspx_eval_html_005foption_005f58 != 0) {
/* 5021 */                                               if (_jspx_eval_html_005foption_005f58 != 1) {
/* 5022 */                                                 out = _jspx_page_context.pushBody();
/* 5023 */                                                 _jspx_th_html_005foption_005f58.setBodyContent((BodyContent)out);
/* 5024 */                                                 _jspx_th_html_005foption_005f58.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5027 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 5028 */                                                 out.write(" (V1 or V2c)");
/* 5029 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f58.doAfterBody();
/* 5030 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5033 */                                               if (_jspx_eval_html_005foption_005f58 != 1) {
/* 5034 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5037 */                                             if (_jspx_th_html_005foption_005f58.doEndTag() == 5) {
/* 5038 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58); return;
/*      */                                             }
/*      */                                             
/* 5041 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58);
/* 5042 */                                             out.write("</optgroup>");
/* 5043 */                                             out.write("\n\t\t\t<optgroup label=\"");
/* 5044 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 5045 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 5047 */                                             OptionTag _jspx_th_html_005foption_005f59 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5048 */                                             _jspx_th_html_005foption_005f59.setPageContext(_jspx_page_context);
/* 5049 */                                             _jspx_th_html_005foption_005f59.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 5051 */                                             _jspx_th_html_005foption_005f59.setValue("UrlMonitor");
/* 5052 */                                             int _jspx_eval_html_005foption_005f59 = _jspx_th_html_005foption_005f59.doStartTag();
/* 5053 */                                             if (_jspx_eval_html_005foption_005f59 != 0) {
/* 5054 */                                               if (_jspx_eval_html_005foption_005f59 != 1) {
/* 5055 */                                                 out = _jspx_page_context.pushBody();
/* 5056 */                                                 _jspx_th_html_005foption_005f59.setBodyContent((BodyContent)out);
/* 5057 */                                                 _jspx_th_html_005foption_005f59.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5060 */                                                 out.print(FormatUtil.getString("HTTP-URLs"));
/* 5061 */                                                 out.write(32);
/* 5062 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f59.doAfterBody();
/* 5063 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5066 */                                               if (_jspx_eval_html_005foption_005f59 != 1) {
/* 5067 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5070 */                                             if (_jspx_th_html_005foption_005f59.doEndTag() == 5) {
/* 5071 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59); return;
/*      */                                             }
/*      */                                             
/* 5074 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59);
/* 5075 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 5077 */                                             OptionTag _jspx_th_html_005foption_005f60 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5078 */                                             _jspx_th_html_005foption_005f60.setPageContext(_jspx_page_context);
/* 5079 */                                             _jspx_th_html_005foption_005f60.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 5081 */                                             _jspx_th_html_005foption_005f60.setValue("UrlSeq");
/* 5082 */                                             int _jspx_eval_html_005foption_005f60 = _jspx_th_html_005foption_005f60.doStartTag();
/* 5083 */                                             if (_jspx_eval_html_005foption_005f60 != 0) {
/* 5084 */                                               if (_jspx_eval_html_005foption_005f60 != 1) {
/* 5085 */                                                 out = _jspx_page_context.pushBody();
/* 5086 */                                                 _jspx_th_html_005foption_005f60.setBodyContent((BodyContent)out);
/* 5087 */                                                 _jspx_th_html_005foption_005f60.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5090 */                                                 out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 5091 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f60.doAfterBody();
/* 5092 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5095 */                                               if (_jspx_eval_html_005foption_005f60 != 1) {
/* 5096 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5099 */                                             if (_jspx_th_html_005foption_005f60.doEndTag() == 5) {
/* 5100 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60); return;
/*      */                                             }
/*      */                                             
/* 5103 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60);
/* 5104 */                                             out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 5105 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 5106 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 5108 */                                             OptionTag _jspx_th_html_005foption_005f61 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5109 */                                             _jspx_th_html_005foption_005f61.setPageContext(_jspx_page_context);
/* 5110 */                                             _jspx_th_html_005foption_005f61.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 5112 */                                             _jspx_th_html_005foption_005f61.setValue("Script Monitor");
/* 5113 */                                             int _jspx_eval_html_005foption_005f61 = _jspx_th_html_005foption_005f61.doStartTag();
/* 5114 */                                             if (_jspx_eval_html_005foption_005f61 != 0) {
/* 5115 */                                               if (_jspx_eval_html_005foption_005f61 != 1) {
/* 5116 */                                                 out = _jspx_page_context.pushBody();
/* 5117 */                                                 _jspx_th_html_005foption_005f61.setBodyContent((BodyContent)out);
/* 5118 */                                                 _jspx_th_html_005foption_005f61.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5121 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 5122 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f61.doAfterBody();
/* 5123 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5126 */                                               if (_jspx_eval_html_005foption_005f61 != 1) {
/* 5127 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5130 */                                             if (_jspx_th_html_005foption_005f61.doEndTag() == 5) {
/* 5131 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61); return;
/*      */                                             }
/*      */                                             
/* 5134 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61);
/* 5135 */                                             out.write(10);
/* 5136 */                                             out.write(10);
/*      */                                           }
/*      */                                           
/*      */ 
/* 5140 */                                           out.write("\n\n\n\n      ");
/* 5141 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 5142 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5145 */                                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 5146 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5149 */                                       if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 5150 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                       }
/*      */                                       
/* 5153 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 5154 */                                       out.write("\n                      \n      ");
/* 5155 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5156 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5160 */                                   if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5161 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                                   }
/*      */                                   
/* 5164 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5165 */                                   out.write("\n      ");
/* 5166 */                                   if (_jspx_meth_c_005fif_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5168 */                                   out.write("\n      </td>\n      \n      ");
/* 5169 */                                   if (request.getParameter("type") != null) {
/* 5170 */                                     isConfMonitor = confMonConfig.isConfMonitor(request.getParameter("type"));
/* 5171 */                                     String restype = request.getParameter("type");
/* 5172 */                                     if (restype.indexOf(":") != -1) {
/* 5173 */                                       restype = restype.substring(0, restype.indexOf(":"));
/*      */                                     }
/* 5175 */                                     if (((isConfMonitor) && (!restype.equals("QueryMonitor"))) || ((!restype.equals("JMX1.2-MX4J-RMI")) && (!restype.equals("Generic WMI")) && (!restype.equals("Script Monitor")) && (!restype.equals("Custom-Application")) && (!restype.equals("File System Monitor")) && (!restype.equals("QueryMonitor")) && (!restype.equals("SNMP")) && (!restype.equals("TELNET")) && (!restype.equals("Exchange")) && (!restype.equals("WTA")) && (!restype.equals("WEB")) && (!restype.equals("UrlSeq")) && (!restype.equals("PHP")) && (!restype.equals("IIS")) && (!restype.equals("APACHE")) && (!restype.equals("MAIL")) && (!restype.equals("All")) && (restype.indexOf("SAP") == -1))) {
/* 5176 */                                       out.write("\n      \t<td class=\"tableheading-monitor-config itadmin-hide\" align=\"right\">\n      \n      \t\t<div id=\"Conf-bulk-configuration\"> \n\t\t\t    \t\t<a href=\"javascript:void(0)\"  onclick=\"window.open('/BulkAddMonitors.do?method=showBulkImportForm&type=");
/* 5177 */                                       out.print(restype);
/* 5178 */                                       out.write("','windowName','toolbar=no,status=no,menubar=no,width=1000,height=500,scrollbars=yes')\" class=\"staticlinks\" >");
/* 5179 */                                       out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 5180 */                                       out.write("</a>\n\t    \t</div><img src=\"/images/script-icon.gif\">\n   \t   </td>\n      \n      \t");
/*      */                                     }
/*      */                                   }
/* 5183 */                                   out.write("     \n      \n  </tr>\n</table>\n <script src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script><script type=\"text/javascript\"> $(\".formtext\").chosen();  </script>\n");
/* 5184 */                                   out.write("\n\n\n\n  ");
/* 5185 */                                   if (_jspx_meth_logic_005fnotEmpty_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5187 */                                   out.write(32);
/* 5188 */                                   if (_jspx_meth_logic_005fempty_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5190 */                                   out.write("\n<input type=\"hidden\" name=\"hideFieldsForIT360\" value=\"");
/* 5191 */                                   out.print(request.getParameter("hideFieldsForIT360"));
/* 5192 */                                   out.write("\">\n  <table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"1\" class=\"lrborder\">\n    <tr>\n        <td height=\"20\"width=\"20%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5193 */                                   out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 5194 */                                   out.write("<span class=\"mandatory\">*</span></label></td>\n        <td height=\"20\" width=\"75%\"> ");
/* 5195 */                                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5197 */                                   out.write(" </td>\n      </tr>\n      <tr>\n        <td height=\"20\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/* 5198 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.urladd.message"));
/* 5199 */                                   out.write("',false,true,'#000000',400,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 5200 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.urladd"));
/* 5201 */                                   out.write("</a><span class=\"mandatory\">*</span></label></td>\n        <td height=\"20\" colspan=\"2\"> ");
/* 5202 */                                   if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5204 */                                   out.write("</td>\n      </tr>\n      ");
/* 5205 */                                   if (_jspx_meth_c_005fcatch_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5207 */                                   out.write(" \n      ");
/* 5208 */                                   if (_jspx_meth_c_005fcatch_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5210 */                                   out.write("\n      ");
/* 5211 */                                   if (_jspx_meth_c_005fif_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5213 */                                   out.write("\n      <tr>\n        <td width=\"25%\" height=\"26\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5214 */                                   out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 5215 */                                   out.write(" <span class=\"mandatory\">*</span></label></td>\n        <td height=\"26\" colspan=\"2\" class=\"footer\"> ");
/* 5216 */                                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5218 */                                   out.write(32);
/* 5219 */                                   out.print(FormatUtil.getString("am.webclient.urlmonitor.unitofpoll.text"));
/* 5220 */                                   out.write("</td>\n      </tr>\n      <tr>\n        <td width=\"25%\" height=\"25\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5221 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.timeout"));
/* 5222 */                                   out.write("</label></td>\n        <td width=\"75%\" height=\"25\" class=\"bodytext\"> ");
/* 5223 */                                   if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5225 */                                   out.print(FormatUtil.getString("am.webclient.newaction.seconds"));
/* 5226 */                                   out.write(" </td>\n      </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"1\" class=\"lrborder\">\n    <tr> \n        <td colspan=\"2\" id=\"http_heading\">\n          <image id=\"http_plus\" class=\"arrows\" src=\"images/arrow_right.png\" />&nbsp;\n            <label style=\"font-size:15px;\">");
/* 5227 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.httpconfig"));
/* 5228 */                                   out.write("</label>\n            <hr />\n        </td>\n      </tr>\n      <tr>\n        <td colspan=\"2\">\n        <div id=\"httpConfigDiv\" style=\"display:none;\">\n        <table id=\"httpConfig\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"1\">\n          <tr>\n            <td width=\"25%\" height=\"20\" class=\"bodytext label-align addmonitor-label\">\n              <label>");
/* 5229 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.formsub"));
/* 5230 */                                   out.write("</label></td>\n            <td height=\"20\" colspan=\"2\">\n              ");
/* 5231 */                                   if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5233 */                                   out.write(" <span class=\"bodytext\">");
/* 5234 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.post"));
/* 5235 */                                   out.write("&nbsp;&nbsp;</span> \n              ");
/* 5236 */                                   if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5238 */                                   out.write(" <span class=\"bodytext\">");
/* 5239 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.get"));
/* 5240 */                                   out.write("&nbsp;&nbsp;</span> \n            </td>\n          </tr>\n          <tr>\n            <td width=\"25%\" height=\"20\" valign=\"top\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/* 5241 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.optionname"));
/* 5242 */                                   out.write("',false,true,'#000000',400,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 5243 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.reqparams"));
/* 5244 */                                   out.write("</a></label><br></td>\n            <td width=\"75%\"  height=\"20\" class=\"footer\"> ");
/* 5245 */                                   if (_jspx_meth_html_005ftextarea_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5247 */                                   out.write(" <br> ");
/* 5248 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.optionname"));
/* 5249 */                                   out.write("</td>\n          </tr>\n          <tr>\n            <td height=\"25\" width=\"25%\" class=\"bodytext label-align addmonitor-label\" valign=\"top\">");
/* 5250 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.urlauthinfo"));
/* 5251 */                                   out.write("</td>\n            <td height=\"25\" width=\"75%\" class=\"bodytext\">\n            <table width=\"100%\" border=\"0\" cellspacing=\"0\">\n                  <tr style=\"padding-top:5px;padding-bottom:5px;\">\n                      <td class=\"bodytext\">");
/* 5252 */                                   if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5254 */                                   out.write("</td>\n                  </tr>\n                  <tr style=\"padding-top:5px;padding-bottom:5px;\">\n                      <td class=\"bodytext\"><input type=\"password\" name=\"password\" class=\"formtext xlarge\" autocomplete=\"off\" placeholder=\"");
/* 5255 */                                   out.print(FormatUtil.getString("am.webclient.common.password.text"));
/* 5256 */                                   out.write("\" /></td>\n                  </tr>\n                </table>\n            </td>\n          </tr>\n          <tr>\n            <td width=\"24%\" height=\"25\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5257 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.responsecodeerror"));
/* 5258 */                                   out.write("</label></td>\n            <td width=\"75%\" height=\"25\" class=\"bodytext\"> ");
/* 5259 */                                   if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5261 */                                   out.write("\n            ");
/* 5262 */                                   if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5264 */                                   out.write(" </td>\n          </tr>\n          <tr>\n            <td></td>\n            <td height=\"25\"  class=\"bodytext\"> ");
/* 5265 */                                   if (_jspx_meth_html_005fmultibox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5267 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.errormessage"));
/* 5268 */                                   out.write("</td>\n          </tr>\n          <tr>\n            <td width=\"25%\" height=\"25\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5269 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.useragent"));
/* 5270 */                                   out.write(" </label></td>\n            <td width=\"75%\" height=\"25\" class=\"bodytext\"> ");
/* 5271 */                                   if (_jspx_meth_html_005ftextarea_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5273 */                                   out.write("</td>\n          </tr>\n          <tr id=\"advanceOptionDiv\" class=\"bodytext\">\n          <td height=\"25\" class=\"bodytext label-align addmonitor-label\" width=\"20%\" valign=\"top\" style=\"padding-top:14px;\">");
/* 5274 */                                   out.print(FormatUtil.getString("urlcreation.customheaders"));
/* 5275 */                                   out.write("</td>\n          <td colspan=\"1\" width=\"100%\">\n              <table border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" id=\"tableToInsert\">\n                <tr id=\"searchCriteria1\" class=\"trToRemove\">\n                  <td>\n                    <table cellspacing=\"0\" cellpadding=\"5\" border=\"0\" class=\"customHeadersTable\" width=\"65%\">\n                    <tr>\n                      <td width=\"15%\" valign=\"top\">\n                    <input type=\"text\" id=\"headerName\" name=\"headerInput\" class=\"formtext headername\" autocomplete=\"off\" size=\"25\" placeholder=\"Header Name\" />\n                    <div id=\"suggestName\" class=\"suggestname\" style=\"display:none;\"></div>\n                      </td>\n                      <td width=\"15%\" valign=\"top\">\n                    <input type=\"text\" id=\"headerValue\" name=\"headerInput\" class=\"formtext headervalue\" autocomplete=\"off\" size=\"25\" placeholder=\"Header Value\" />\n                    <div id=\"suggestValue\" class=\"suggestvalue\" style=\"display:none;\"></div>\n                      </td>\n                      <td width=\"5%\" align=\"center\" valign=\"middle\">\n");
/* 5276 */                                   out.write("                    <a href=\"javascript:void(0);\" class=\"addFieldSelector\"><image src=\"images/icon_plus.gif\"></image></a>\n                      </td>\n                      <td width=\"5%\" align=\"center\" valign=\"middle\">\n                    <a href=\"javascript:void(0);\" class=\"removeFieldSelector\"><image src=\"images/icon_minus.gif\"></image></a>\n                      </td>\n                    </tr>\n                  </table>\n                </td>\n              </tr>\n            </table>  \n          </td>\n          </tr>\n          </table> \n          </div> \n          </td>\n          </tr>\n        </table>\n        </td>\n      </tr>\n      \n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"1\" class=\"lrborder\">\n      <tr id=\"content_heading\"> \n        <td colspan=\"2\">\n          <image src=\"images/arrow_right.png\" id=\"content_plus\" class=\"arrows\">&nbsp;\n            <label style=\"font-size:15px;\">");
/* 5277 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.contentcheck"));
/* 5278 */                                   out.write("</label>\n            <hr />\n        </td>\n      </tr>\n      <tr>\n        <td colspan=\"2\">\n        <div id=\"contentCheckDiv\"  style=\"display:none;\">\n          <table id=\"contentCheck\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"1\">\n            <tr>\n              <td height=\"20\" width=\"20%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5279 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.htmlcontent"));
/* 5280 */                                   out.write(" </label></td>\n              <td height=\"20\" colspan=\"2\">");
/* 5281 */                                   if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5283 */                                   out.write(32);
/* 5284 */                                   out.write(32);
/*      */                                   
/* 5286 */                                   CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty.get(CheckboxTag.class);
/* 5287 */                                   _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 5288 */                                   _jspx_th_html_005fcheckbox_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5290 */                                   _jspx_th_html_005fcheckbox_005f0.setProperty("regEx");
/* 5291 */                                   int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 5292 */                                   if (_jspx_eval_html_005fcheckbox_005f0 != 0) {
/* 5293 */                                     if (_jspx_eval_html_005fcheckbox_005f0 != 1) {
/* 5294 */                                       out = _jspx_page_context.pushBody();
/* 5295 */                                       _jspx_th_html_005fcheckbox_005f0.setBodyContent((BodyContent)out);
/* 5296 */                                       _jspx_th_html_005fcheckbox_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 5299 */                                       out.print(FormatUtil.getString("am.webclient.filedir.regexp.text"));
/* 5300 */                                       int evalDoAfterBody = _jspx_th_html_005fcheckbox_005f0.doAfterBody();
/* 5301 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 5304 */                                     if (_jspx_eval_html_005fcheckbox_005f0 != 1) {
/* 5305 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 5308 */                                   if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 5309 */                                     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty.reuse(_jspx_th_html_005fcheckbox_005f0); return;
/*      */                                   }
/*      */                                   
/* 5312 */                                   this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 5313 */                                   out.write("</td>\n            </tr> \n            <tr>\n              <td width=\"25%\" height=\"25\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5314 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.matcherror"));
/* 5315 */                                   out.write(" </label></td>\n              <td width=\"76%\" height=\"25\" class=\"bodytext\"> ");
/* 5316 */                                   if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5318 */                                   out.write("</td>\n            </tr>\n            <tr>\n              <td></td>\n              <td height=\"25\" class=\"bodytext\"> ");
/*      */                                   
/* 5320 */                                   CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty.get(CheckboxTag.class);
/* 5321 */                                   _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 5322 */                                   _jspx_th_html_005fcheckbox_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5324 */                                   _jspx_th_html_005fcheckbox_005f1.setProperty("caseSensitive");
/* 5325 */                                   int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 5326 */                                   if (_jspx_eval_html_005fcheckbox_005f1 != 0) {
/* 5327 */                                     if (_jspx_eval_html_005fcheckbox_005f1 != 1) {
/* 5328 */                                       out = _jspx_page_context.pushBody();
/* 5329 */                                       _jspx_th_html_005fcheckbox_005f1.setBodyContent((BodyContent)out);
/* 5330 */                                       _jspx_th_html_005fcheckbox_005f1.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 5333 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.caseSensitive"));
/* 5334 */                                       int evalDoAfterBody = _jspx_th_html_005fcheckbox_005f1.doAfterBody();
/* 5335 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 5338 */                                     if (_jspx_eval_html_005fcheckbox_005f1 != 1) {
/* 5339 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 5342 */                                   if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 5343 */                                     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty.reuse(_jspx_th_html_005fcheckbox_005f1); return;
/*      */                                   }
/*      */                                   
/* 5346 */                                   this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 5347 */                                   out.write("</td> ");
/* 5348 */                                   out.write("\n            </tr>\n            <tr>\n              <td><br /></td>\n            </tr>\n          </table>\n          </div>\n        </td>\n      </tr>\n  </table>\n  ");
/*      */                                   
/* 5350 */                                   if (EnterpriseUtil.isAdminServer())
/*      */                                   {
/*      */ 
/* 5353 */                                     out.write("\n      ");
/* 5354 */                                     JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("managedServerGroups", request.getCharacterEncoding()), out, false);
/* 5355 */                                     out.write("    \n  ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 5359 */                                   out.write("\n    ");
/* 5360 */                                   JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("monitorGroups", request.getCharacterEncoding()), out, false);
/* 5361 */                                   out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"25%\" height=\"22\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"75%\" height=\"26\" class=\"tablebottom\"> ");
/*      */                                   
/* 5363 */                                   IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5364 */                                   _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 5365 */                                   _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5367 */                                   _jspx_th_c_005fif_005f5.setTest("${empty method}");
/* 5368 */                                   int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 5369 */                                   if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                     for (;;) {
/* 5371 */                                       out.write("\n      ");
/*      */                                       
/* 5373 */                                       ButtonTag _jspx_th_html_005fbutton_005f0 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5374 */                                       _jspx_th_html_005fbutton_005f0.setPageContext(_jspx_page_context);
/* 5375 */                                       _jspx_th_html_005fbutton_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */                                       
/* 5377 */                                       _jspx_th_html_005fbutton_005f0.setOnclick("return validateAndSubmit();");
/*      */                                       
/* 5379 */                                       _jspx_th_html_005fbutton_005f0.setStyleClass("buttons btn_highlt");
/*      */                                       
/* 5381 */                                       _jspx_th_html_005fbutton_005f0.setProperty("submitbutton1");
/*      */                                       
/* 5383 */                                       _jspx_th_html_005fbutton_005f0.setValue(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.button.addurl"));
/* 5384 */                                       int _jspx_eval_html_005fbutton_005f0 = _jspx_th_html_005fbutton_005f0.doStartTag();
/* 5385 */                                       if (_jspx_th_html_005fbutton_005f0.doEndTag() == 5) {
/* 5386 */                                         this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0); return;
/*      */                                       }
/*      */                                       
/* 5389 */                                       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0);
/* 5390 */                                       out.write("\n      ");
/* 5391 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 5392 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5396 */                                   if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 5397 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                   }
/*      */                                   
/* 5400 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5401 */                                   out.write(32);
/*      */                                   
/* 5403 */                                   IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5404 */                                   _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 5405 */                                   _jspx_th_c_005fif_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5407 */                                   _jspx_th_c_005fif_005f6.setTest("${!empty method}");
/* 5408 */                                   int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 5409 */                                   if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                     for (;;) {
/* 5411 */                                       out.write("\n      ");
/*      */                                       
/* 5413 */                                       ButtonTag _jspx_th_html_005fbutton_005f1 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5414 */                                       _jspx_th_html_005fbutton_005f1.setPageContext(_jspx_page_context);
/* 5415 */                                       _jspx_th_html_005fbutton_005f1.setParent(_jspx_th_c_005fif_005f6);
/*      */                                       
/* 5417 */                                       _jspx_th_html_005fbutton_005f1.setOnclick("return validateAndSubmit();");
/*      */                                       
/* 5419 */                                       _jspx_th_html_005fbutton_005f1.setStyleClass("buttons btn_update");
/*      */                                       
/* 5421 */                                       _jspx_th_html_005fbutton_005f1.setProperty("submitbutton1");
/*      */                                       
/* 5423 */                                       _jspx_th_html_005fbutton_005f1.setValue(FormatUtil.getString("am.webclient.common.update.text"));
/* 5424 */                                       int _jspx_eval_html_005fbutton_005f1 = _jspx_th_html_005fbutton_005f1.doStartTag();
/* 5425 */                                       if (_jspx_th_html_005fbutton_005f1.doEndTag() == 5) {
/* 5426 */                                         this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1); return;
/*      */                                       }
/*      */                                       
/* 5429 */                                       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1);
/* 5430 */                                       out.write("\n      ");
/* 5431 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 5432 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5436 */                                   if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 5437 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                   }
/*      */                                   
/* 5440 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5441 */                                   out.write(" &nbsp;\n      ");
/*      */                                   
/* 5443 */                                   ResetTag _jspx_th_html_005freset_005f0 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody.get(ResetTag.class);
/* 5444 */                                   _jspx_th_html_005freset_005f0.setPageContext(_jspx_page_context);
/* 5445 */                                   _jspx_th_html_005freset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5447 */                                   _jspx_th_html_005freset_005f0.setStyleClass("buttons btn_link");
/*      */                                   
/* 5449 */                                   _jspx_th_html_005freset_005f0.setValue(FormatUtil.getString("am.webclient.common.cancel.text"));
/*      */                                   
/* 5451 */                                   _jspx_th_html_005freset_005f0.setOnclick("javascript:history.back();");
/*      */                                   
/* 5453 */                                   _jspx_th_html_005freset_005f0.setStyleId("cancelButtonMod");
/* 5454 */                                   int _jspx_eval_html_005freset_005f0 = _jspx_th_html_005freset_005f0.doStartTag();
/* 5455 */                                   if (_jspx_th_html_005freset_005f0.doEndTag() == 5) {
/* 5456 */                                     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f0); return;
/*      */                                   }
/*      */                                   
/* 5459 */                                   this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleId_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f0);
/* 5460 */                                   out.write("</td>\n  </tr>\n</table>\n");
/* 5461 */                                   if (_jspx_meth_c_005fif_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5463 */                                   out.write("\n        ");
/* 5464 */                                   if (_jspx_meth_c_005fif_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5466 */                                   out.write("\n        <script>\n        if(document.UrlForm.verifyerror.value=='true')\n        {\n          document.UrlForm.verifyerror.checked=true;\n        }\n        </script>\n\n\n");
/*      */                                   
/* 5468 */                                   if (hideFields)
/*      */                                   {
/*      */ 
/* 5471 */                                     out.write("\n  <script>\n    $(document.body).ready(function(){\n    document.getElementById(\"cancelButtonMod\").onclick = null;\n    $(\"#cancelButtonMod\").click(function(){ //No I18N\n      closePopUpWindow();\n    });\n    });\n  </script>\n");
/*      */                                   }
/*      */                                   
/*      */ 
/* 5475 */                                   out.write("\n</td>\n<td width=\"20%\" valign=\"top\">\n");
/* 5476 */                                   if (title.equals("UrlMonitor")) {
/* 5477 */                                     StringBuffer helpcardKey = new StringBuffer();
/* 5478 */                                     helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.Urlmonitor.helpcard", new String[] { OEMUtil.getOEMString("product.name") }));
/*      */                                     
/* 5480 */                                     out.write(10);
/* 5481 */                                     JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(helpcardKey.toString()), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()), out, false);
/* 5482 */                                     out.write(10);
/*      */                                   }
/* 5484 */                                   out.write(10);
/* 5485 */                                   out.write(32);
/* 5486 */                                   out.write(32);
/* 5487 */                                   if (_jspx_meth_html_005fhidden_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5489 */                                   out.write(10);
/* 5490 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 5491 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 5495 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 5496 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 5499 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 5500 */                               out.write("\n</td>\n        </tr>\n        </table>\n");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 5506 */                               out.write("\n<TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n  <tr>\n    <td align=\"center\">\n      <input name=\"closeButton\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 5507 */                               out.print(FormatUtil.getString("Close Window"));
/* 5508 */                               out.write("\" onclick=\"closePopUpWindow();\">\n      </td>\n      </tr>\n      </table>\n");
/*      */                             }
/*      */                             
/*      */ 
/* 5512 */                             out.write(10);
/* 5513 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 5514 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 5517 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 5518 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 5521 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 5522 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 5525 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 5526 */                         out.write(10);
/* 5527 */                         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 5529 */                         out.write(10);
/* 5530 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5531 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 5535 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5536 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 5539 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5540 */                       out.write("\n<!--  Your area ends here -->\n");
/* 5541 */                       out.write("\n\n\n\n\n\n<script type=\"text/javascript\">\n//startsWith function is not supported in IE so adding a shim for it. \nif (!String.prototype.startsWith) {\n  String.prototype.startsWith = function (str){\n    return this.lastIndexOf(str, 0) === 0;\n  };\n}\n\nfunction cancelModify(idString)\n{    \n    var toReplaceInput = \"\";    \n    var toReplaceModifyPart = \"<a href=\\\"javascript:void(0)\\\" style=\\\"color:blue;text-decoration:underline;\\\" onclick=\\\"modifyPassword(\\'\"+idString+\"\\')\\\">");
/* 5542 */                       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */                         return;
/* 5544 */                       out.write("&nbsp;\"+idString+\"</a>\";\n    $(\"#tdSpan_\"+idString).html(toReplaceInput);\n    $(\"#modifySpan_\"+idString).html(toReplaceModifyPart);\n}\n\nfunction modifyPassword(idString)\n{    \n  var toReplaceInput = \"<input id=\\\"toCheck\\\" class=\\\"formtext xlarge\\\" type=\\\"password\\\" value=\\\"\\\" name=\\\"\"+idString+\"\\\" placeholder=\\\"Password\\\">\";\n    var toReplaceModifyPart = \"<a href=\\\"javascript:void(0)\\\" style=\\\"color:blue\\\" onclick=\\\"cancelModify(\\'\"+idString+\"\\')\\\">");
/* 5545 */                       if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */                         return;
/* 5547 */                       out.write("</a>\";\n    $(\"#tdSpan_\"+idString).html(toReplaceInput);\n    $(\"#modifySpan_\"+idString).html(toReplaceModifyPart);\n    $(\"#toCheck\").focus();\n}\n\nfunction fnOpenWindow()\n{\n  var window2=open('/html/reqparams.html','secondWindow','resizable=no,scrollbars=no,width=250,height=140'); //No I18N\n  window2.focus();\n}\n\nfunction createSuggestionList(items,id,className)\n{\n    var list = document.createElement(\"ul\");\n      for(var i in items)\n      {\n        var listItem = document.createElement(\"li\");\n        listItem.innerHTML='<a>'+items[i]+'</a>';\n        listItem.className=className;\n        listItem.id=items[i];\n        list.appendChild(listItem);\n      }\n      $(id).empty();\n      $(id).append(list);\n      $(id).show();\n}\n\nvar headersPresent = {\n        'Accept':['text/plain', 'text/html', 'text/javascript', 'application/json'],  //No I18N\n        'Accept-Charset':['utf-8', 'utf-16', 'iso-8859-1'], //No I18N\n        'Accept-Datetime':[], //No I18N\n        'Accept-Encoding':['compress','gzip','deflate','identity'], //No I18N\n");
/* 5548 */                       out.write("        'Accept-Language':['en-US'], //No I18N\n        'Authorization':['Basic '], //No I18N\n        'Cache-Control':['no-cache'], //No I18N\n        'Connection':['close'], //No I18N\n        'Content-MD5':[], //No I18N\n        'Content-Length':[], //No I18N\n        'Content-Type':['application/atom+xml','application/json','application/xml','application/x-www-form-urlencoded','multipart/form-data'],    //No I18N\n        'Cookie':[], //No I18N\n        'Date':[], //No I18N\n        'DNT':[0,1], //No I18N\n        'Expect':['100-continue'], //No I18N\n        'From':[], //No I18N\n        'Front-End-Https':['on','off'], //No I18N\n        'Host':[], //No I18N\n        'If-Match':[], //No I18N\n        'If-Modified-Since':[], //No I18N\n        'If-None-Match':[], //No I18N\n        'If-Range':[], //No I18N\n        'If-Unmodified-Since':[], //No I18N\n        'Max-Forwards':[], //No I18N\n        'Origin':[], //No I18N\n        'Pragma':['no-cache'], //No I18N\n        'Proxy-Authorization':[], //No I18N\n        'Proxy-Connection':['keep-alive'], //No I18N\n");
/* 5549 */                       out.write("        'Range':[], //No I18N\n        'Referer':[], //No I18N\n        'TE':[], //No I18N\n        'Upgrade':[], //No I18N\n        'User-Agent':[], //No I18N\n        'Via':[], //No I18N\n        'Warning':[], //No I18N\n        'X-ATT-DeviceId':[], //No I18N\n        'X-Forwarded-For':[], //No I18N\n        'X-Forwarded-Proto':['http','https'], //No I18N\n        'X-Requested-With':['XMLHttpRequest'], //No I18N\n        'X-Wap-Profile':[] //No I18N\n};\n\n$(document).ready(function()\n{\n  var position=0,valposition=0;\n  var idCount = 1;\n  $('.addFieldSelector').click(function() //NO I18N \n  {\n    idCount++;\n    var actualObj = $(this).closest('.trToRemove'); //NO I18N\n    var clonedObj = $(actualObj).clone(true);\n    $(clonedObj).find(\"input:text[name='headerInput']\").val(\"\"); //NO I18N\n    clonedObj.attr('id', 'customHeadersTr'+idCount); //NO I18N\n      $(clonedObj).find(\".headername\").attr(\"id\",\"headerName\"+idCount); //No I18N\n        $(clonedObj).find(\".headervalue\").attr(\"id\",\"headerValue\"+idCount); //No I18N\n        $(clonedObj).find(\"div.suggestname\").attr(\"id\",\"suggestName\"+idCount); //No I18N\n");
/* 5550 */                       out.write("        $(clonedObj).find(\"div.suggestvalue\").attr(\"id\",\"suggestValue\"+idCount); //No I18N\n        $(clonedObj).find(\"div\").empty();\n    clonedObj.insertAfter(actualObj);\n  });\n  $('.removeFieldSelector').click(function() //NO I18N \n  {\n    if($(\".customHeadersTable\").length > 1)\n    {\n      $(this).closest('.trToRemove').remove(); //NO I18N\n    }\n  });\n  $(\"#errorcontent\").attr(\"placeholder\", \"Error,failed,...\"); //No I18N\n  $(\"#checkfor\").attr(\"placeholder\", \"Success,Hello World,...\"); //No I18N\n  $(\"#regEx\").attr(\"placeholder\", \"[a-z]*,...\"); //No I18N\n  $(\"#userid\").attr(\"placeholder\",'");
/* 5551 */                       out.print(FormatUtil.getString("am.webclient.common.username.text"));
/* 5552 */                       out.write("'); //No I18N\n  $('.headername').keyup(function(e) //NO I18N \n  {\n      var findEle,childEle;\n      if(e.which==$.ui.keyCode.UP)\n      {\n        findEle = \":nth-child(\"+position+\")\";  //No I18N\n        childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n        document.getElementById(''+childEle[0].id).style.backgroundColor=\"#fff\";\n        position=(position>1)?position-1:1;\n        findEle = \":nth-child(\"+position+\")\"; //No I18N\n        childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n        document.getElementById(''+childEle[0].id).style.backgroundColor=\"#e6e6e6\";\n      }\n      else if(e.which==$.ui.keyCode.DOWN)\n      {   \n        var len = $(this).siblings(\"div\").children().children().length; //No I18N\n        if(position != len)\n        {\n          if(position!=0)\n          {\n            findEle = \":nth-child(\"+position+\")\"; //No I18N\n            childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n            document.getElementById(''+childEle[0].id).style.backgroundColor=\"#fff\";\n");
/* 5553 */                       out.write("          }\n          position++;\n          findEle = \":nth-child(\"+position+\")\"; //No I18N\n          childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n          document.getElementById(''+childEle[0].id).style.backgroundColor=\"#e6e6e6\";\n        }\n        else\n        {\n          findEle = \":nth-child(\"+position+\")\"; //No I18N\n          childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n          document.getElementById(''+childEle[0].id).style.backgroundColor=\"#fff\";\n          position=1;\n          findEle = \":nth-child(\"+position+\")\"; //No I18N\n          childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n          document.getElementById(''+childEle[0].id).style.backgroundColor=\"#e6e6e6\";\n        }\n      }\n      else if(e.which==$.ui.keyCode.ENTER)\n      {\n        findEle = \":nth-child(\"+position+\")\"; //No I18N\n        childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n        $(this).val(childEle[0].id);\n        $(this).siblings(\"div\").empty(); //No I18N\n");
/* 5554 */                       out.write("        $(this).siblings(\"div\").hide(); //No I18N\n      }\n      else \n      {\n        position=0;\n        var count = $(this).attr('id'); //No I18N\n        count=count.substring(10,count.length);\n        var valEntered = $(this).val();\n        var headers = Object.keys(headersPresent).filter(function(key)\n        {\n          if(key.toLowerCase().startsWith(valEntered.toLowerCase()))\n          {\n            return key;\n          }\n        });\n        if(headers.length!=0)\n        {\n          if(headers.length==1 && headers[0]==valEntered)\n          {\n            $(\"#suggestName\"+count).hide();\n            position=0;\n          }\n          else\n          {\n            createSuggestionList(headers,\"#suggestName\"+count,\"headerNameList\"); //No I18N\n          }\n        }\n        else\n        {\n          $(\"#suggestName\"+count).hide();\n          position=0;\n        }     \n      }   \n  }); \n  $('.suggestname').delegate('.headerNameList','mousedown',function(event) //No I18N\n  {\n    $(event.target).closest('.customHeadersTable').find('.headername').first().val($(event.target).text()); //No I18N\n");
/* 5555 */                       out.write("    $(event.target).closest('.suggestname').empty(); //No I18N\n    $(event.target).closest('.suggestname').hide(); //No I18N\n  });\n  $('.headername').focusout(function()\n  {\n    $('#'+$(this).siblings('.suggestname')[0].id).empty();\n    $('#'+$(this).siblings('.suggestname')[0].id).hide();\n  });\n  $('.headervalue').keyup(function(e) //NO I18N \n  {\n    if(e.which==$.ui.keyCode.UP)\n    {\n      findEle = \":nth-child(\"+valposition+\")\"; //No I18N\n      childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n      document.getElementById(''+childEle[0].id).style.backgroundColor=\"#fff\";\n      valposition=(valposition>1)?valposition-1:1;\n      findEle = \":nth-child(\"+valposition+\")\"; //No I18N\n      childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n      document.getElementById(''+childEle[0].id).style.backgroundColor=\"#e6e6e6\";\n    }\n    else if(e.which==$.ui.keyCode.DOWN)\n    {   \n      var len = $(this).siblings(\"div\").children().children().length; //No I18N\n      if(valposition != len)\n");
/* 5556 */                       out.write("      {\n        if(valposition!=0)\n        {\n          findEle = \":nth-child(\"+valposition+\")\"; //No I18N\n          childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n          document.getElementById(''+childEle[0].id).style.backgroundColor=\"#fff\";\n        }\n        valposition++;\n        findEle = \":nth-child(\"+valposition+\")\"; //No I18N\n        childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n        document.getElementById(''+childEle[0].id).style.backgroundColor=\"#e6e6e6\";\n      }\n      else\n      {\n        findEle = \":nth-child(\"+valposition+\")\"; //No I18N\n        childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n        document.getElementById(''+childEle[0].id).style.backgroundColor=\"#fff\";\n        valposition=1;\n        findEle = \":nth-child(\"+valposition+\")\"; //No I18N\n        childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n        document.getElementById(''+childEle[0].id).style.backgroundColor=\"#e6e6e6\";\n");
/* 5557 */                       out.write("      }\n    }\n    else if(e.which==$.ui.keyCode.ENTER)\n    {\n      findEle = \":nth-child(\"+valposition+\")\"; //No I18N\n      childEle = $(this).siblings(\"div\").children().children(findEle); //No I18N\n      $(this).val(childEle[0].id);\n      $(this).siblings(\"div\").empty(); //No I18N\n      $(this).siblings(\"div\").hide(); //No I18N\n    }\n    else \n    {\n      valposition=0; \n      var count = $(this).attr('id'); //No I18N\n      count=count.substring(11,count.length);\n      var valEntered = $(this).val();\n      if(valEntered!==\"\")\n      {\n        var header = $(this).closest('.customHeadersTable').find('.headername').first().val(); //No I18N\n        var values = headersPresent[header];\n        var matchedValues=[];\n        for(var val in values)\n        {\n          if(values[val].toLowerCase().startsWith(valEntered.toLowerCase()))\n          {\n            matchedValues.push(values[val]);\n          }\n        }\n        if(matchedValues.length!=0)\n        {\n          createSuggestionList(matchedValues,\"#suggestValue\"+count,\"headerValueList\"); //No I18N\n");
/* 5558 */                       out.write("        }\n        else\n        {\n          $(\"#suggestValue\"+count).hide();\n        }       \n      }\n      else\n      {\n        $(\"#suggestValue\"+count).hide();\n      }\n    }\n  });\n  $('.headervalue').focusout(function()\n  {\n    $('#'+$(this).siblings('.suggestvalue')[0].id).empty();\n    $('#'+$(this).siblings('.suggestvalue')[0].id).hide();\n  }),\n  $('.suggestvalue').delegate('.headerValueList','mousedown',function(event) //No I18N\n  {\n    $(event.target).closest('.customHeadersTable').find('.headervalue').first().val($(event.target).text()); //No I18N\n    $(event.target).closest('.suggestvalue').empty(); //No I18N\n    $(event.target).closest('.suggestvalue').hide(); //No I18N\n  });\n  $('#http_heading').click(function(e)\n  {\n    if($('#http_plus').attr('src').indexOf(\"right\")>-1)\n    {\n      $('#httpConfigDiv').slideToggle(\"slow\"); //No I18N\n      $('#http_plus').attr('src','images/arrow_down.png'); //No I18N\n    }\n    else\n    {\n      $('#httpConfigDiv').slideToggle(\"slow\"); //No I18N\n      $('#http_plus').attr('src','images/arrow_right.png'); //No I18N\n");
/* 5559 */                       out.write("    }\n  });\n  $('#content_heading').click(function(e)\n  {\n    if($('#content_plus').attr('src').indexOf(\"right\")>-1)\n    {\n      $('#contentCheckDiv').slideToggle(\"slow\"); //No I18N\n      $('#content_plus').attr('src','images/arrow_down.png'); //No I18N\n    }\n    else\n    {\n      $('#contentCheckDiv').slideToggle(\"slow\"); //No I18N\n      $('#content_plus').attr('src','images/arrow_right.png'); //No I18N\n    }\n  });\n});\n\nfunction loadSite()\n{\n  document.UrlForm.haid.options.length=0;\n  var formCustomerId = document.UrlForm.organization.value;\n  var siteName;\n  var siteId;\n  var customerId;\n  var rowCount=0;\n  document.UrlForm.haid.options[rowCount++] = new Option('-Select Site-','-'); //No I18N\n  ");
/* 5560 */                       if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*      */                         return;
/* 5562 */                       out.write("\n}\n\nfunction resetname(name)\n{\n  if(name='groupname')\n  {\n    document.UrlForm.groupname.value='';\n  }\n  if(name='subgroupname')\n  {\n    document.UrlForm.subgroupname.value='';\n  }\n}\n\n</script>\n");
/* 5563 */                       out.write("\n<script type=\"text/javascript\">\n\n\nfunction validateAndSubmit()\n\n{\n\tvar empties = $(\"input:text[name='headerInput']\").filter(function () {\n\t    return $.trim($(this).val()) == '';\n\t});\n  \tif (empties.length==$(\"input:text[name='headerInput']\").length) \n\t{\n      $(\"#customHeadersId\").val(\"\");\n\t}\n  \telse\n  \t{\n\t\tvar temp = \"\";\n\n\t\ttemp = $.map($(\"input:text[name='headerInput']\"), function(vals, i) // NO I18N\n\t\t{\n\t\tif(vals.value=='')\n      \t{\n       \t\t return \"\";\n      \t}\n      \telse if(i==0)\n    \t{\n\t\t\treturn vals.value;\n\t\t}\n\t\telse if(i%2 ==0)\n\t\t{\n\t\t\treturn \"#\"+vals.value;\t\n\t\t}\n\t\telse\n\t\t{\n\t\t\treturn \"_sep_\"+vals.value; // NO I18N\n\t\t}\n\t\t\n\t}).join('');\n\t$(\"#customHeadersId\").val(temp);\n}\n\n    var dispname=document.UrlForm.monitorname.value;\n        if(dispname== '')\n          {\n\n            alert('");
/* 5564 */                       out.print(FormatUtil.getString("am.webclient.common.jsalertfordisplayname.text"));
/* 5565 */                       out.write("');\n             document.UrlForm.monitorname.select();\n                return;\n           }\n           var quote1=\"'\" ;\n\tif(dispname.indexOf(quote1) != -1)\n        {\n            alert(\"");
/* 5566 */                       out.print(FormatUtil.getString("am.webclient.common.jsalertforsinglequote.text"));
/* 5567 */                       out.write("\");\n                document.UrlForm.monitorname.select();\n                return;\n         }\n\n\tvar url=trimAll(document.UrlForm.url.value);\n\tif(url == ''  )\n\t{\n\t\talert(\"");
/* 5568 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.alert.url"));
/* 5569 */                       out.write("\");\n\t\tdocument.UrlForm.url.focus();\n\t\treturn;\n\t}\n\tif(!checkUrlPattern(url))\n\t{\n\t\talert(\"");
/* 5570 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.alert.urladd"));
/* 5571 */                       out.write("\");\n\t\tdocument.UrlForm.url.focus();\n\t\treturn;\n\t}\n\tif(document.UrlForm.regEx.checked)\n    {\n      if(document.UrlForm.checkfor.value === '')\n      {\n        alert('");
/* 5572 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.regEx.emptycontent"));
/* 5573 */                       out.write("');\n        document.UrlForm.checkfor.select();\n        return;\n      }  \n    }\n\t");
/*      */                       
/* 5575 */                       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5576 */                       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 5577 */                       _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                       
/* 5579 */                       _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 5580 */                       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 5581 */                       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                         for (;;) {
/* 5583 */                           out.write("\n\n\t\tif(url.indexOf(\"google.com\") != -1 || url.indexOf(\"google.co\") != -1)\n\t\t{\n\t\t\talert(\"");
/* 5584 */                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.google"));
/* 5585 */                           out.write("\");\n\t\t\tdocument.UrlForm.url.value='http://';\n\t\t\treturn ;\n\t\t}\n\t   if(url.indexOf(\"yahoo.com\") != -1 || url.indexOf(\"yahoo.co\") != -1)\n\t    {\n\t\t   alert(\"");
/* 5586 */                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.yahoo"));
/* 5587 */                           out.write("\");\n\t\t   document.UrlForm.url.value='http://';\n\t\t   return ;\n\t    }\n\n\t");
/* 5588 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 5589 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5593 */                       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 5594 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*      */                       }
/*      */                       else {
/* 5597 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5598 */                         out.write("\n\n\n\tvar poll=trimAll(document.UrlForm.pollInterval.value);\n\tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0' )\n\t{\n\t\talert(\"");
/* 5599 */                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.polling"));
/* 5600 */                         out.write("\");\n\t\tdocument.UrlForm.pollInterval.focus();\n\t\treturn;\n\n\t}\n\t");
/*      */                         
/* 5602 */                         PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5603 */                         _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 5604 */                         _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */                         
/* 5606 */                         _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 5607 */                         int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 5608 */                         if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                           for (;;) {
/* 5610 */                             out.write("\n\n\t\tif(parseInt(poll) <15)\n\t\t{\n\t\t\talert(\"");
/* 5611 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.alert.pollgr15"));
/* 5612 */                             out.write("\");\n\t\t\tdocument.UrlForm.pollInterval.value=15;\n\t\t\tdocument.UrlForm.pollInterval.focus();\n\t\t\treturn;\n\t\t}\n\n\t\t");
/* 5613 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 5614 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 5618 */                         if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 5619 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*      */                         }
/*      */                         else {
/* 5622 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 5623 */                           out.write("\n\n\nif(trimAll(document.UrlForm.haid.value) != '' && document.UrlForm.haid.value != '-'){\ndocument.UrlForm.addtoha.value=\"true\";\n}else{\ndocument.UrlForm.addtoha.value=\"false\";\n}\n\n/***  IT360-1762 ISSUES CHANGES STARTS HERE ***/\n\n");
/* 5624 */                           if (EnterpriseUtil.isIt360MSPEdition()) {
/* 5625 */                             out.write("\n\nif (document.UrlForm.organization.value== \"-\")\n {\n\talert(\"");
/* 5626 */                             out.print(FormatUtil.getString("it360.addnewmonitor.err.checkcustomer"));
/* 5627 */                             out.write("\");\n\treturn;\n }\n\nif (trimAll(document.UrlForm.haid.value) == '' || document.UrlForm.haid.value== \"-\")\n {\n    alert(\"");
/* 5628 */                             out.print(FormatUtil.getString("it360.addnewmonitor.err.checksite"));
/* 5629 */                             out.write("\");\n    return;\n }\n");
/*      */                           }
/* 5631 */                           out.write(10);
/* 5632 */                           out.write(10);
/*      */                           
/* 5634 */                           IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5635 */                           _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 5636 */                           _jspx_th_c_005fif_005f12.setParent(null);
/*      */                           
/* 5638 */                           _jspx_th_c_005fif_005f12.setTest("${checkForMonitorGroup}");
/* 5639 */                           int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 5640 */                           if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                             for (;;) {
/* 5642 */                               out.write("\n\tvar haidValue = document.UrlForm.haid.value\n\tif(haidValue == '-' || haidValue == ''){\n\talert(\"");
/* 5643 */                               out.print(FormatUtil.getString("am.webclient.newmonitor.selectmg.text"));
/* 5644 */                               out.write("\")\n\treturn;\n\t}\n");
/* 5645 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 5646 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 5650 */                           if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 5651 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/*      */                           }
/*      */                           else {
/* 5654 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 5655 */                             out.write("\n   ");
/*      */                             
/* 5657 */                             if (EnterpriseUtil.isAdminServer())
/*      */                             {
/*      */ 
/* 5660 */                               out.write("                                \n    if (document.UrlForm.masSelectType[1].checked) {\n    \tvar selectedVal=document.UrlForm.masGroupName.value;\n    \tif (selectedVal != null && selectedVal == \"-\") {\n            alert('");
/* 5661 */                               out.print(FormatUtil.getString("am.webclient.admin.add.monitor.select.masgroup.text"));
/* 5662 */                               out.write("');\n            document.UrlForm.masGroupName.focus();\n            return;\n        }                                \t\n    } else if (document.UrlForm.masSelectType[2].checked) {\n    \tselectedVal=document.UrlForm.selectedServer.value;\n    \tif (selectedVal != null && selectedVal == \"-\") {\n            alert('");
/* 5663 */                               out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.mas.text"));
/* 5664 */                               out.write("');\n            document.UrlForm.selectedServer.focus();\n            return;\n        }                                 \t\n    }\n   ");
/*      */                             }
/*      */                             
/*      */ 
/* 5668 */                             out.write(" \n\n/***  IT360-1762 ISSUES CHANGES ENDS HERE ***/\n\n\tfrmSelectAll(document.UrlForm.requestparams)\n\tdocument.UrlForm.submit();\n}\n\nfunction formReload()\n{\n    var v=document.UrlForm.type.value;\n    //var portNo=v.substring(v.indexOf(\":\")+1,v.length);\n    //document.AMActionForm.method=\"post\";\n    document.UrlForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=\"+v+\"&hideFieldsForIT360=");
/* 5669 */                             out.print(request.getParameter("hideFieldsForIT360"));
/* 5670 */                             out.write("\";\n    //enableAll();\n    document.UrlForm.submit();\n}\n\n</script>\n</html>\n");
/*      */                           }
/* 5672 */                         } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5673 */         out = _jspx_out;
/* 5674 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5675 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5676 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5679 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5685 */     PageContext pageContext = _jspx_page_context;
/* 5686 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5688 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5689 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 5690 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5692 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 5694 */     _jspx_th_tiles_005fput_005f0.setValue("URL Monitoring");
/* 5695 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 5696 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 5697 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5698 */       return true;
/*      */     }
/* 5700 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5701 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5706 */     PageContext pageContext = _jspx_page_context;
/* 5707 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5709 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5710 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 5711 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5713 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 5715 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 5716 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 5717 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 5718 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5719 */       return true;
/*      */     }
/* 5721 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5722 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5727 */     PageContext pageContext = _jspx_page_context;
/* 5728 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5730 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 5731 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 5732 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5734 */     _jspx_th_am_005fhiddenparam_005f0.setName("wiz");
/* 5735 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 5736 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 5737 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 5738 */       return true;
/*      */     }
/* 5740 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 5741 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5746 */     PageContext pageContext = _jspx_page_context;
/* 5747 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5749 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5750 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 5751 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5753 */     _jspx_th_c_005fif_005f3.setTest("${!empty param.wiz ||  !empty param.fromAssociate}");
/* 5754 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 5755 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 5757 */         out.write("\n      ");
/* 5758 */         if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5759 */           return true;
/* 5760 */         out.write("\n      ");
/* 5761 */         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5762 */           return true;
/* 5763 */         out.write("\n      ");
/* 5764 */         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5765 */           return true;
/* 5766 */         out.write("\n      ");
/* 5767 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 5768 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5772 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 5773 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5774 */       return true;
/*      */     }
/* 5776 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5777 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5782 */     PageContext pageContext = _jspx_page_context;
/* 5783 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5785 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5786 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 5787 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/* 5788 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 5789 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 5791 */         out.write("\n        ");
/* 5792 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 5793 */           return true;
/* 5794 */         out.write("\n        ");
/* 5795 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 5796 */           return true;
/* 5797 */         out.write("\n\n        ");
/* 5798 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 5799 */           return true;
/* 5800 */         out.write("\n      ");
/* 5801 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 5802 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5806 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 5807 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5808 */       return true;
/*      */     }
/* 5810 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5811 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5816 */     PageContext pageContext = _jspx_page_context;
/* 5817 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5819 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5820 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 5821 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 5823 */     _jspx_th_c_005fwhen_005f0.setTest("${param.type=='WTA'}");
/* 5824 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 5825 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 5827 */         out.write("\n          ");
/* 5828 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 5829 */           return true;
/* 5830 */         out.write("\n        ");
/* 5831 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 5832 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5836 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 5837 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5838 */       return true;
/*      */     }
/* 5840 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5841 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5846 */     PageContext pageContext = _jspx_page_context;
/* 5847 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5849 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5850 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5851 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5853 */     _jspx_th_c_005fout_005f0.setValue("Web Transaction Monitor");
/* 5854 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5855 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5856 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5857 */       return true;
/*      */     }
/* 5859 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5860 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5865 */     PageContext pageContext = _jspx_page_context;
/* 5866 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5868 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5869 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 5870 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 5872 */     _jspx_th_c_005fwhen_005f1.setTest("${param.type=='.Net'}");
/* 5873 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 5874 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 5876 */         out.write("\n          ");
/* 5877 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 5878 */           return true;
/* 5879 */         out.write("\n        ");
/* 5880 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 5881 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5885 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 5886 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5887 */       return true;
/*      */     }
/* 5889 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5890 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5895 */     PageContext pageContext = _jspx_page_context;
/* 5896 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5898 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5899 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5900 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 5902 */     _jspx_th_c_005fout_005f1.setValue("Tomcat Server");
/* 5903 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5904 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5905 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5906 */       return true;
/*      */     }
/* 5908 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5909 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5914 */     PageContext pageContext = _jspx_page_context;
/* 5915 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5917 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5918 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 5919 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 5920 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 5921 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 5923 */         out.write("\n         ");
/* 5924 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 5925 */           return true;
/* 5926 */         out.write("\n        ");
/* 5927 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 5928 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5932 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 5933 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 5934 */       return true;
/*      */     }
/* 5936 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 5937 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5942 */     PageContext pageContext = _jspx_page_context;
/* 5943 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5945 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 5946 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5947 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5949 */     _jspx_th_c_005fout_005f2.setValue("${param.type}");
/* 5950 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5951 */     if (_jspx_eval_c_005fout_005f2 != 0) {
/* 5952 */       if (_jspx_eval_c_005fout_005f2 != 1) {
/* 5953 */         out = _jspx_page_context.pushBody();
/* 5954 */         _jspx_th_c_005fout_005f2.setBodyContent((BodyContent)out);
/* 5955 */         _jspx_th_c_005fout_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5958 */         out.write(45);
/* 5959 */         int evalDoAfterBody = _jspx_th_c_005fout_005f2.doAfterBody();
/* 5960 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5963 */       if (_jspx_eval_c_005fout_005f2 != 1) {
/* 5964 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5967 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5968 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f2);
/* 5969 */       return true;
/*      */     }
/* 5971 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f2);
/* 5972 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5977 */     PageContext pageContext = _jspx_page_context;
/* 5978 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5980 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 5981 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 5982 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 5984 */     _jspx_th_html_005fhidden_005f0.setProperty("type");
/* 5985 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 5986 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 5987 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 5988 */       return true;
/*      */     }
/* 5990 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 5991 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5996 */     PageContext pageContext = _jspx_page_context;
/* 5997 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5999 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6000 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 6001 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6003 */     _jspx_th_html_005fhidden_005f1.setProperty("haid");
/* 6004 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 6005 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 6006 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 6007 */       return true;
/*      */     }
/* 6009 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 6010 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6015 */     PageContext pageContext = _jspx_page_context;
/* 6016 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6018 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 6019 */     _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 6020 */     _jspx_th_logic_005fnotEmpty_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6022 */     _jspx_th_logic_005fnotEmpty_005f2.setName("method");
/* 6023 */     int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 6024 */     if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */       for (;;) {
/* 6026 */         out.write(32);
/* 6027 */         if (_jspx_meth_html_005fhidden_005f2(_jspx_th_logic_005fnotEmpty_005f2, _jspx_page_context))
/* 6028 */           return true;
/* 6029 */         out.write("\n  <input type=\"hidden\" name=\"actionmethod\" value=\"updateUrlMonitor\">\n  ");
/* 6030 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 6031 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6035 */     if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 6036 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 6037 */       return true;
/*      */     }
/* 6039 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 6040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_logic_005fnotEmpty_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6045 */     PageContext pageContext = _jspx_page_context;
/* 6046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6048 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6049 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 6050 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f2);
/*      */     
/* 6052 */     _jspx_th_html_005fhidden_005f2.setProperty("urlid");
/* 6053 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 6054 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 6055 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 6056 */       return true;
/*      */     }
/* 6058 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 6059 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fempty_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6064 */     PageContext pageContext = _jspx_page_context;
/* 6065 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6067 */     EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 6068 */     _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 6069 */     _jspx_th_logic_005fempty_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6071 */     _jspx_th_logic_005fempty_005f0.setName("method");
/* 6072 */     int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 6073 */     if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */       for (;;) {
/* 6075 */         out.write("\n  <input type=\"hidden\" name=\"actionmethod\" value=\"createUrlMonitor\">\n  ");
/* 6076 */         if (_jspx_meth_html_005fhidden_005f3(_jspx_th_logic_005fempty_005f0, _jspx_page_context))
/* 6077 */           return true;
/* 6078 */         out.write("\n  <input type=\"hidden\" name=\"addtoha\" value=\"true\">\n  ");
/* 6079 */         int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 6080 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6084 */     if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 6085 */       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 6086 */       return true;
/*      */     }
/* 6088 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 6089 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f3(JspTag _jspx_th_logic_005fempty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6094 */     PageContext pageContext = _jspx_page_context;
/* 6095 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6097 */     HiddenTag _jspx_th_html_005fhidden_005f3 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.get(HiddenTag.class);
/* 6098 */     _jspx_th_html_005fhidden_005f3.setPageContext(_jspx_page_context);
/* 6099 */     _jspx_th_html_005fhidden_005f3.setParent((Tag)_jspx_th_logic_005fempty_005f0);
/*      */     
/* 6101 */     _jspx_th_html_005fhidden_005f3.setStyleId("customHeadersId");
/*      */     
/* 6103 */     _jspx_th_html_005fhidden_005f3.setProperty("customHeaders");
/* 6104 */     int _jspx_eval_html_005fhidden_005f3 = _jspx_th_html_005fhidden_005f3.doStartTag();
/* 6105 */     if (_jspx_th_html_005fhidden_005f3.doEndTag() == 5) {
/* 6106 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 6107 */       return true;
/*      */     }
/* 6109 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 6110 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6115 */     PageContext pageContext = _jspx_page_context;
/* 6116 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6118 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6119 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 6120 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6122 */     _jspx_th_html_005ftext_005f0.setProperty("monitorname");
/*      */     
/* 6124 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext xlarge");
/* 6125 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 6126 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 6127 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 6128 */       return true;
/*      */     }
/* 6130 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 6131 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6136 */     PageContext pageContext = _jspx_page_context;
/* 6137 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6139 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 6140 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 6141 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6143 */     _jspx_th_html_005ftextarea_005f0.setProperty("url");
/*      */     
/* 6145 */     _jspx_th_html_005ftextarea_005f0.setCols("50");
/*      */     
/* 6147 */     _jspx_th_html_005ftextarea_005f0.setRows("5");
/*      */     
/* 6149 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea xlarge");
/* 6150 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 6151 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 6152 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 6153 */       return true;
/*      */     }
/* 6155 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 6156 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6161 */     PageContext pageContext = _jspx_page_context;
/* 6162 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6164 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 6165 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 6166 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6168 */     _jspx_th_c_005fcatch_005f0.setVar("invalidseqid");
/* 6169 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 6171 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 6172 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 6174 */           out.write(32);
/* 6175 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 6176 */             return true;
/* 6177 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 6178 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6182 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 6183 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6186 */         int tmp176_175 = 0; int[] tmp176_173 = _jspx_push_body_count_c_005fcatch_005f0; int tmp178_177 = tmp176_173[tmp176_175];tmp176_173[tmp176_175] = (tmp178_177 - 1); if (tmp178_177 <= 0) break;
/* 6187 */         out = _jspx_page_context.popBody(); }
/* 6188 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 6190 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 6191 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 6193 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 6198 */     PageContext pageContext = _jspx_page_context;
/* 6199 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6201 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 6202 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 6203 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 6205 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 6207 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${UrlForm.userseqid}");
/* 6208 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 6209 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 6210 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 6211 */       return true;
/*      */     }
/* 6213 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 6214 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6219 */     PageContext pageContext = _jspx_page_context;
/* 6220 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6222 */     CatchTag _jspx_th_c_005fcatch_005f1 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 6223 */     _jspx_th_c_005fcatch_005f1.setPageContext(_jspx_page_context);
/* 6224 */     _jspx_th_c_005fcatch_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6226 */     _jspx_th_c_005fcatch_005f1.setVar("invalidhaid");
/* 6227 */     int[] _jspx_push_body_count_c_005fcatch_005f1 = { 0 };
/*      */     try {
/* 6229 */       int _jspx_eval_c_005fcatch_005f1 = _jspx_th_c_005fcatch_005f1.doStartTag();
/* 6230 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f1 != 0) {
/*      */         for (;;) {
/* 6232 */           out.write(32);
/* 6233 */           if (_jspx_meth_fmt_005fparseNumber_005f1(_jspx_th_c_005fcatch_005f1, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f1))
/* 6234 */             return true;
/* 6235 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f1.doAfterBody();
/* 6236 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6240 */       if (_jspx_th_c_005fcatch_005f1.doEndTag() == 5)
/* 6241 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6244 */         int tmp176_175 = 0; int[] tmp176_173 = _jspx_push_body_count_c_005fcatch_005f1; int tmp178_177 = tmp176_173[tmp176_175];tmp176_173[tmp176_175] = (tmp178_177 - 1); if (tmp178_177 <= 0) break;
/* 6245 */         out = _jspx_page_context.popBody(); }
/* 6246 */       _jspx_th_c_005fcatch_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 6248 */       _jspx_th_c_005fcatch_005f1.doFinally();
/* 6249 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f1);
/*      */     }
/* 6251 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f1(JspTag _jspx_th_c_005fcatch_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f1) throws Throwable
/*      */   {
/* 6256 */     PageContext pageContext = _jspx_page_context;
/* 6257 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6259 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f1 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 6260 */     _jspx_th_fmt_005fparseNumber_005f1.setPageContext(_jspx_page_context);
/* 6261 */     _jspx_th_fmt_005fparseNumber_005f1.setParent((Tag)_jspx_th_c_005fcatch_005f1);
/*      */     
/* 6263 */     _jspx_th_fmt_005fparseNumber_005f1.setVar("wnhaid");
/*      */     
/* 6265 */     _jspx_th_fmt_005fparseNumber_005f1.setValue("${param.haid}");
/* 6266 */     int _jspx_eval_fmt_005fparseNumber_005f1 = _jspx_th_fmt_005fparseNumber_005f1.doStartTag();
/* 6267 */     if (_jspx_th_fmt_005fparseNumber_005f1.doEndTag() == 5) {
/* 6268 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f1);
/* 6269 */       return true;
/*      */     }
/* 6271 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f1);
/* 6272 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6277 */     PageContext pageContext = _jspx_page_context;
/* 6278 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6280 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6281 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 6282 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6284 */     _jspx_th_c_005fif_005f4.setTest("${(empty invalidseqid)}");
/* 6285 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 6286 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 6288 */         out.write(32);
/* 6289 */         if (_jspx_meth_html_005fhidden_005f4(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 6290 */           return true;
/* 6291 */         out.write("\n      ");
/* 6292 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 6293 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6297 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 6298 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6299 */       return true;
/*      */     }
/* 6301 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6302 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f4(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6307 */     PageContext pageContext = _jspx_page_context;
/* 6308 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6310 */     HiddenTag _jspx_th_html_005fhidden_005f4 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6311 */     _jspx_th_html_005fhidden_005f4.setPageContext(_jspx_page_context);
/* 6312 */     _jspx_th_html_005fhidden_005f4.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6314 */     _jspx_th_html_005fhidden_005f4.setProperty("userseqid");
/* 6315 */     int _jspx_eval_html_005fhidden_005f4 = _jspx_th_html_005fhidden_005f4.doStartTag();
/* 6316 */     if (_jspx_th_html_005fhidden_005f4.doEndTag() == 5) {
/* 6317 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 6318 */       return true;
/*      */     }
/* 6320 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 6321 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6326 */     PageContext pageContext = _jspx_page_context;
/* 6327 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6329 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6330 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 6331 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6333 */     _jspx_th_html_005ftext_005f1.setProperty("pollInterval");
/*      */     
/* 6335 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext small");
/* 6336 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 6337 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 6338 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 6339 */       return true;
/*      */     }
/* 6341 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 6342 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6347 */     PageContext pageContext = _jspx_page_context;
/* 6348 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6350 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6351 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 6352 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6354 */     _jspx_th_html_005ftext_005f2.setProperty("timeout");
/*      */     
/* 6356 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext small");
/* 6357 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 6358 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 6359 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 6360 */       return true;
/*      */     }
/* 6362 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 6363 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6368 */     PageContext pageContext = _jspx_page_context;
/* 6369 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6371 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6372 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 6373 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6375 */     _jspx_th_html_005fradio_005f0.setProperty("method");
/*      */     
/* 6377 */     _jspx_th_html_005fradio_005f0.setValue("P");
/* 6378 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 6379 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 6380 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 6381 */       return true;
/*      */     }
/* 6383 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 6384 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6389 */     PageContext pageContext = _jspx_page_context;
/* 6390 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6392 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6393 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 6394 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6396 */     _jspx_th_html_005fradio_005f1.setProperty("method");
/*      */     
/* 6398 */     _jspx_th_html_005fradio_005f1.setValue("G");
/* 6399 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 6400 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 6401 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 6402 */       return true;
/*      */     }
/* 6404 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 6405 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6410 */     PageContext pageContext = _jspx_page_context;
/* 6411 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6413 */     TextareaTag _jspx_th_html_005ftextarea_005f1 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 6414 */     _jspx_th_html_005ftextarea_005f1.setPageContext(_jspx_page_context);
/* 6415 */     _jspx_th_html_005ftextarea_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6417 */     _jspx_th_html_005ftextarea_005f1.setProperty("requestparams");
/*      */     
/* 6419 */     _jspx_th_html_005ftextarea_005f1.setStyleClass("formtextarea xlarge");
/*      */     
/* 6421 */     _jspx_th_html_005ftextarea_005f1.setRows("5");
/*      */     
/* 6423 */     _jspx_th_html_005ftextarea_005f1.setCols("50");
/* 6424 */     int _jspx_eval_html_005ftextarea_005f1 = _jspx_th_html_005ftextarea_005f1.doStartTag();
/* 6425 */     if (_jspx_th_html_005ftextarea_005f1.doEndTag() == 5) {
/* 6426 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f1);
/* 6427 */       return true;
/*      */     }
/* 6429 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f1);
/* 6430 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6435 */     PageContext pageContext = _jspx_page_context;
/* 6436 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6438 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6439 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 6440 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6442 */     _jspx_th_html_005ftext_005f3.setProperty("userid");
/*      */     
/* 6444 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext xlarge");
/*      */     
/* 6446 */     _jspx_th_html_005ftext_005f3.setSize("20");
/*      */     
/* 6448 */     _jspx_th_html_005ftext_005f3.setStyleId("userid");
/* 6449 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 6450 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 6451 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 6452 */       return true;
/*      */     }
/* 6454 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 6455 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6460 */     PageContext pageContext = _jspx_page_context;
/* 6461 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6463 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.get(SelectTag.class);
/* 6464 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 6465 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6467 */     _jspx_th_html_005fselect_005f1.setProperty("httpcondition");
/* 6468 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 6469 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 6470 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 6471 */         out = _jspx_page_context.pushBody();
/* 6472 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 6473 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6476 */         out.write("\n            ");
/* 6477 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 6478 */           return true;
/* 6479 */         out.write(32);
/* 6480 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 6481 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6484 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 6485 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6488 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 6489 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 6490 */       return true;
/*      */     }
/* 6492 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 6493 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6498 */     PageContext pageContext = _jspx_page_context;
/* 6499 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6501 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.get(OptionsCollectionTag.class);
/* 6502 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 6503 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 6505 */     _jspx_th_html_005foptionsCollection_005f0.setName("AMActionForm");
/*      */     
/* 6507 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("conditions");
/* 6508 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 6509 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 6510 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 6511 */       return true;
/*      */     }
/* 6513 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 6514 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6519 */     PageContext pageContext = _jspx_page_context;
/* 6520 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6522 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6523 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 6524 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6526 */     _jspx_th_html_005ftext_005f4.setProperty("httpvalue");
/*      */     
/* 6528 */     _jspx_th_html_005ftext_005f4.setSize("16");
/*      */     
/* 6530 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext medium");
/* 6531 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 6532 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 6533 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 6534 */       return true;
/*      */     }
/* 6536 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 6537 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6542 */     PageContext pageContext = _jspx_page_context;
/* 6543 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6545 */     MultiboxTag _jspx_th_html_005fmultibox_005f0 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6546 */     _jspx_th_html_005fmultibox_005f0.setPageContext(_jspx_page_context);
/* 6547 */     _jspx_th_html_005fmultibox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6549 */     _jspx_th_html_005fmultibox_005f0.setProperty("verifyerror");
/*      */     
/* 6551 */     _jspx_th_html_005fmultibox_005f0.setValue("true");
/* 6552 */     int _jspx_eval_html_005fmultibox_005f0 = _jspx_th_html_005fmultibox_005f0.doStartTag();
/* 6553 */     if (_jspx_th_html_005fmultibox_005f0.doEndTag() == 5) {
/* 6554 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 6555 */       return true;
/*      */     }
/* 6557 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 6558 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6563 */     PageContext pageContext = _jspx_page_context;
/* 6564 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6566 */     TextareaTag _jspx_th_html_005ftextarea_005f2 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 6567 */     _jspx_th_html_005ftextarea_005f2.setPageContext(_jspx_page_context);
/* 6568 */     _jspx_th_html_005ftextarea_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6570 */     _jspx_th_html_005ftextarea_005f2.setRows("5");
/*      */     
/* 6572 */     _jspx_th_html_005ftextarea_005f2.setCols("50");
/*      */     
/* 6574 */     _jspx_th_html_005ftextarea_005f2.setProperty("userAgent");
/*      */     
/* 6576 */     _jspx_th_html_005ftextarea_005f2.setStyleClass("formtextarea xlarge");
/* 6577 */     int _jspx_eval_html_005ftextarea_005f2 = _jspx_th_html_005ftextarea_005f2.doStartTag();
/* 6578 */     if (_jspx_th_html_005ftextarea_005f2.doEndTag() == 5) {
/* 6579 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f2);
/* 6580 */       return true;
/*      */     }
/* 6582 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f2);
/* 6583 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6588 */     PageContext pageContext = _jspx_page_context;
/* 6589 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6591 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6592 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 6593 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6595 */     _jspx_th_html_005ftext_005f5.setProperty("checkfor");
/*      */     
/* 6597 */     _jspx_th_html_005ftext_005f5.setStyleId("checkfor");
/*      */     
/* 6599 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext xlarge");
/* 6600 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 6601 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 6602 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 6603 */       return true;
/*      */     }
/* 6605 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 6606 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6611 */     PageContext pageContext = _jspx_page_context;
/* 6612 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6614 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6615 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 6616 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6618 */     _jspx_th_html_005ftext_005f6.setStyleId("errorcontent");
/*      */     
/* 6620 */     _jspx_th_html_005ftext_005f6.setProperty("errorcontent");
/*      */     
/* 6622 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext xlarge");
/* 6623 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 6624 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 6625 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 6626 */       return true;
/*      */     }
/* 6628 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 6629 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6634 */     PageContext pageContext = _jspx_page_context;
/* 6635 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6637 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6638 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 6639 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6641 */     _jspx_th_c_005fif_005f7.setTest("${!empty param.haid && empty invalidhaid}");
/* 6642 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 6643 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 6645 */         out.write("\n  <input type=\"hidden\" name=\"haid\"  value='");
/* 6646 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 6647 */           return true;
/* 6648 */         out.write("'>\n  ");
/* 6649 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 6650 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6654 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 6655 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 6656 */       return true;
/*      */     }
/* 6658 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 6659 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6664 */     PageContext pageContext = _jspx_page_context;
/* 6665 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6667 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6668 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 6669 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6671 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 6672 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 6673 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 6674 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6675 */       return true;
/*      */     }
/* 6677 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6678 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6683 */     PageContext pageContext = _jspx_page_context;
/* 6684 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6686 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6687 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 6688 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6690 */     _jspx_th_c_005fif_005f8.setTest("${!empty param.name}");
/* 6691 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 6692 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 6694 */         out.write("\n        <input type=\"hidden\" name=\"name\"  value='");
/* 6695 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f8, _jspx_page_context))
/* 6696 */           return true;
/* 6697 */         out.write("'>\n        ");
/* 6698 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 6699 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6703 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 6704 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 6705 */       return true;
/*      */     }
/* 6707 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 6708 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6713 */     PageContext pageContext = _jspx_page_context;
/* 6714 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6716 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6717 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6718 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6720 */     _jspx_th_c_005fout_005f4.setValue("${param.name}");
/* 6721 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 6722 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 6723 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6724 */       return true;
/*      */     }
/* 6726 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6727 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6732 */     PageContext pageContext = _jspx_page_context;
/* 6733 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6735 */     HiddenTag _jspx_th_html_005fhidden_005f5 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody.get(HiddenTag.class);
/* 6736 */     _jspx_th_html_005fhidden_005f5.setPageContext(_jspx_page_context);
/* 6737 */     _jspx_th_html_005fhidden_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6739 */     _jspx_th_html_005fhidden_005f5.setProperty("subgroupname");
/*      */     
/* 6741 */     _jspx_th_html_005fhidden_005f5.setStyleClass("formtext");
/* 6742 */     int _jspx_eval_html_005fhidden_005f5 = _jspx_th_html_005fhidden_005f5.doStartTag();
/* 6743 */     if (_jspx_th_html_005fhidden_005f5.doEndTag() == 5) {
/* 6744 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f5);
/* 6745 */       return true;
/*      */     }
/* 6747 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f5);
/* 6748 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6753 */     PageContext pageContext = _jspx_page_context;
/* 6754 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6756 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6757 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 6758 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6760 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 6762 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 6763 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 6764 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 6765 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 6766 */       return true;
/*      */     }
/* 6768 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 6769 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6774 */     PageContext pageContext = _jspx_page_context;
/* 6775 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6777 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6778 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 6779 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*      */     
/* 6781 */     _jspx_th_fmt_005fmessage_005f0.setKey("Modify");
/* 6782 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 6783 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 6784 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6785 */       return true;
/*      */     }
/* 6787 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6788 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6793 */     PageContext pageContext = _jspx_page_context;
/* 6794 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6796 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6797 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 6798 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/*      */     
/* 6800 */     _jspx_th_fmt_005fmessage_005f1.setKey("Cancel");
/* 6801 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 6802 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 6803 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6804 */       return true;
/*      */     }
/* 6806 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6812 */     PageContext pageContext = _jspx_page_context;
/* 6813 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6815 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 6816 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 6817 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */     
/* 6819 */     _jspx_th_c_005fforEach_005f0.setItems("${dynamicSites}");
/*      */     
/* 6821 */     _jspx_th_c_005fforEach_005f0.setVar("a");
/*      */     
/* 6823 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowCounter");
/* 6824 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 6826 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 6827 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 6829 */           out.write("\n    ");
/* 6830 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6831 */             return true;
/* 6832 */           out.write("\n    if(formCustomerId == customerId)\n    {\n      document.UrlForm.haid.options[rowCount++] = new Option(siteName,siteId);\n    }\n  ");
/* 6833 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 6834 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6838 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 6839 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6842 */         int tmp195_194 = 0; int[] tmp195_192 = _jspx_push_body_count_c_005fforEach_005f0; int tmp197_196 = tmp195_192[tmp195_194];tmp195_192[tmp195_194] = (tmp197_196 - 1); if (tmp197_196 <= 0) break;
/* 6843 */         out = _jspx_page_context.popBody(); }
/* 6844 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 6846 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 6847 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 6849 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6854 */     PageContext pageContext = _jspx_page_context;
/* 6855 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6857 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 6858 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 6859 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6861 */     _jspx_th_c_005fforEach_005f1.setItems("${a}");
/*      */     
/* 6863 */     _jspx_th_c_005fforEach_005f1.setVar("b");
/*      */     
/* 6865 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowCounter1");
/* 6866 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 6868 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 6869 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 6871 */           out.write("\n      ");
/* 6872 */           boolean bool; if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6873 */             return true;
/* 6874 */           out.write("\n      ");
/* 6875 */           if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6876 */             return true;
/* 6877 */           out.write("\n      ");
/* 6878 */           if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6879 */             return true;
/* 6880 */           out.write("\n    ");
/* 6881 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 6882 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6886 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 6887 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6890 */         int tmp282_281 = 0; int[] tmp282_279 = _jspx_push_body_count_c_005fforEach_005f1; int tmp284_283 = tmp282_279[tmp282_281];tmp282_279[tmp282_281] = (tmp284_283 - 1); if (tmp284_283 <= 0) break;
/* 6891 */         out = _jspx_page_context.popBody(); }
/* 6892 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 6894 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 6895 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 6897 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6902 */     PageContext pageContext = _jspx_page_context;
/* 6903 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6905 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6906 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 6907 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6909 */     _jspx_th_c_005fif_005f9.setTest("${rowCounter1.count == 1}");
/* 6910 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 6911 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 6913 */         out.write("\n        siteName = '");
/* 6914 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6915 */           return true;
/* 6916 */         out.write("';\n      ");
/* 6917 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 6918 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6922 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 6923 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 6924 */       return true;
/*      */     }
/* 6926 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 6927 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6932 */     PageContext pageContext = _jspx_page_context;
/* 6933 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6935 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6936 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6937 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 6939 */     _jspx_th_c_005fout_005f5.setValue("${b}");
/* 6940 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6941 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6942 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6943 */       return true;
/*      */     }
/* 6945 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6946 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6951 */     PageContext pageContext = _jspx_page_context;
/* 6952 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6954 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6955 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 6956 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6958 */     _jspx_th_c_005fif_005f10.setTest("${rowCounter1.count == 2}");
/* 6959 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 6960 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 6962 */         out.write("\n        siteId = '");
/* 6963 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6964 */           return true;
/* 6965 */         out.write("';\n      ");
/* 6966 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 6967 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6971 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 6972 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 6973 */       return true;
/*      */     }
/* 6975 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 6976 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6981 */     PageContext pageContext = _jspx_page_context;
/* 6982 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6984 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6985 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 6986 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 6988 */     _jspx_th_c_005fout_005f6.setValue("${b}");
/* 6989 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 6990 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 6991 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6992 */       return true;
/*      */     }
/* 6994 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6995 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7000 */     PageContext pageContext = _jspx_page_context;
/* 7001 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7003 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7004 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 7005 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7007 */     _jspx_th_c_005fif_005f11.setTest("${rowCounter1.count == 3}");
/* 7008 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 7009 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 7011 */         out.write("\n        customerId = '");
/* 7012 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7013 */           return true;
/* 7014 */         out.write("';\n      ");
/* 7015 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 7016 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7020 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 7021 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 7022 */       return true;
/*      */     }
/* 7024 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 7025 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7030 */     PageContext pageContext = _jspx_page_context;
/* 7031 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7033 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7034 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 7035 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 7037 */     _jspx_th_c_005fout_005f7.setValue("${b}");
/* 7038 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 7039 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 7040 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 7041 */       return true;
/*      */     }
/* 7043 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 7044 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\newurlconf_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */