/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.Truncate;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
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
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
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
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParamTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class AssociatedThresholds_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  873 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  874 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  877 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  878 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  879 */       set = AMConnectionPool.executeQueryStmt(query);
/*  880 */       if (set.next())
/*      */       {
/*  882 */         String helpLink = set.getString("LINK");
/*  883 */         DBUtil.searchLinks.put(key, helpLink);
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
/* 1399 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1444 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/*      */           catch (SQLException e) {
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
/* 2196 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2202 */   private static Map<String, Long> _jspx_dependants = new HashMap(6);
/* 2203 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/* 2204 */     _jspx_dependants.put("/jsp/includes/MonitorTemplate.jspf", Long.valueOf(1473429417000L));
/* 2205 */     _jspx_dependants.put("/jsp/includes/CommonLeftPage.jspf", Long.valueOf(1473429417000L));
/* 2206 */     _jspx_dependants.put("/jsp/includes/ApplicationLinks.jspf", Long.valueOf(1473429417000L));
/* 2207 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2208 */     _jspx_dependants.put("/jsp/includes/MonitorDiscoveryStatus.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005ffilter_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparam;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2245 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2249 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2266 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2267 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2268 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2269 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2270 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2271 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2272 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2273 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2274 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2275 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2276 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2277 */     this._005fjspx_005ftagPool_005ffmt_005fparam = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2278 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2279 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2283 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2284 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2285 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2286 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2287 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2288 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2289 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2290 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2291 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.release();
/* 2292 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2293 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2294 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2295 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2296 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2297 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2298 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2299 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2300 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2301 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/* 2302 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2303 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2304 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2305 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2306 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2307 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/* 2308 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/* 2309 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005ffilter_005fnobody.release();
/* 2310 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.release();
/* 2311 */     this._005fjspx_005ftagPool_005ffmt_005fparam.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2318 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2321 */     JspWriter out = null;
/* 2322 */     Object page = this;
/* 2323 */     JspWriter _jspx_out = null;
/* 2324 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2328 */       response.setContentType("text/html;charset=UTF-8");
/* 2329 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2331 */       _jspx_page_context = pageContext;
/* 2332 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2333 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2334 */       session = pageContext.getSession();
/* 2335 */       out = pageContext.getOut();
/* 2336 */       _jspx_out = out;
/*      */       
/* 2338 */       out.write("<!DOCTYPE html>\n");
/* 2339 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2340 */       out.write("\n\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n\n");
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 2345 */         out.write(10);
/*      */         
/* 2347 */         boolean showMonType = true;
/* 2348 */         if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2349 */           showMonType = false;
/*      */         }
/* 2351 */         HashMap extDeviceMap = null;
/* 2352 */         if (com.adventnet.appmanager.util.Constants.isExtDeviceConfigured())
/*      */         {
/* 2354 */           extDeviceMap = com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil.getExtAllDevicesLink();
/*      */         }
/* 2356 */         request.setAttribute("extDeviceMap", extDeviceMap);
/*      */         
/* 2358 */         out.write(10);
/*      */         
/* 2360 */         String monitor = request.getParameter("monitor");
/* 2361 */         String wiz = request.getParameter("wiz");
/* 2362 */         String haid = request.getParameter("haid");
/* 2363 */         boolean isMSPEdition = EnterpriseUtil.isIt360MSPEdition();
/* 2364 */         boolean admin = false;
/* 2365 */         if (wiz != null)
/*      */         {
/* 2367 */           wiz = "&wiz=true";
/*      */         }
/*      */         else
/*      */         {
/* 2371 */           wiz = "";
/*      */         }
/* 2373 */         if ((request.getParameter("admin") != null) && (request.getParameter("admin").equals("true")))
/*      */         {
/* 2375 */           admin = true;
/*      */         }
/*      */         
/* 2378 */         String modisplayname = null;
/*      */         try
/*      */         {
/* 2381 */           LinkedHashMap hadetails1 = (LinkedHashMap)request.getAttribute("hadetails");
/* 2382 */           if ((hadetails1 != null) && (!hadetails1.isEmpty()))
/*      */           {
/* 2384 */             String key = (String)hadetails1.get("baname");
/* 2385 */             LinkedHashMap prop = (LinkedHashMap)hadetails1.get(key);
/* 2386 */             if (prop != null)
/*      */             {
/* 2388 */               modisplayname = (String)prop.get("displayname");
/*      */             }
/*      */             else
/*      */             {
/* 2392 */               modisplayname = key;
/*      */             }
/*      */           }
/*      */         } catch (Exception ex) {
/* 2396 */           ex.printStackTrace();
/*      */         }
/* 2398 */         AMActionForm form = (AMActionForm)request.getAttribute("AMActionForm");
/*      */         
/* 2400 */         String view = form.getViewBy();
/* 2401 */         request.setAttribute("viewBy", view);
/*      */         
/* 2403 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 2408 */           ArrayList orgs = com.adventnet.appmanager.struts.beans.AlarmUtil.getCustomerNames();
/*      */           
/* 2410 */           if (orgs != null)
/*      */           {
/* 2412 */             request.setAttribute("customers", orgs);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2419 */           if (form != null)
/*      */           {
/* 2421 */             String customerName = form.getOrganization();
/* 2422 */             if (customerName != null)
/*      */             {
/* 2424 */               if (customerName.equals("-"))
/*      */               {
/* 2426 */                 customerName = "-1";
/*      */               }
/* 2428 */               ArrayList applications2 = com.adventnet.appmanager.struts.beans.AlarmUtil.getSiteMonitorGroups(customerName);
/* 2429 */               if (applications2 != null)
/*      */               {
/* 2431 */                 request.setAttribute("applications2", applications2);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2443 */         out.write(10);
/* 2444 */         com.adventnet.appmanager.fault.AMDependentAttributesListProvider depAttribProvider = null;
/* 2445 */         depAttribProvider = (com.adventnet.appmanager.fault.AMDependentAttributesListProvider)_jspx_page_context.getAttribute("depAttribProvider", 2);
/* 2446 */         if (depAttribProvider == null) {
/* 2447 */           depAttribProvider = new com.adventnet.appmanager.fault.AMDependentAttributesListProvider();
/* 2448 */           _jspx_page_context.setAttribute("depAttribProvider", depAttribProvider, 2);
/*      */         }
/* 2450 */         out.write("\n\n\n\n\n\n\n\n");
/* 2451 */         out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */         
/* 2453 */         DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2454 */         _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2455 */         _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */         
/* 2457 */         _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */         
/* 2459 */         _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */         
/* 2461 */         _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */         
/* 2463 */         _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2464 */         int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2465 */         if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2466 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */         }
/*      */         
/* 2469 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2470 */         String available = null;
/* 2471 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2472 */         out.write(10);
/*      */         
/* 2474 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2475 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2476 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2478 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2480 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2482 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2484 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2485 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2486 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2487 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */         }
/*      */         
/* 2490 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2491 */         String unavailable = null;
/* 2492 */         unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2493 */         out.write(10);
/*      */         
/* 2495 */         DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2496 */         _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2497 */         _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */         
/* 2499 */         _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */         
/* 2501 */         _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */         
/* 2503 */         _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */         
/* 2505 */         _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2506 */         int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2507 */         if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2508 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */         }
/*      */         
/* 2511 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2512 */         String unmanaged = null;
/* 2513 */         unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2514 */         out.write(10);
/*      */         
/* 2516 */         DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2517 */         _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2518 */         _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */         
/* 2520 */         _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */         
/* 2522 */         _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */         
/* 2524 */         _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */         
/* 2526 */         _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2527 */         int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2528 */         if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2529 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */         }
/*      */         
/* 2532 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2533 */         String scheduled = null;
/* 2534 */         scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2535 */         out.write(10);
/*      */         
/* 2537 */         DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2538 */         _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2539 */         _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */         
/* 2541 */         _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */         
/* 2543 */         _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */         
/* 2545 */         _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */         
/* 2547 */         _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2548 */         int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2549 */         if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2550 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */         }
/*      */         
/* 2553 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2554 */         String critical = null;
/* 2555 */         critical = (String)_jspx_page_context.findAttribute("critical");
/* 2556 */         out.write(10);
/*      */         
/* 2558 */         DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2559 */         _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2560 */         _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */         
/* 2562 */         _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */         
/* 2564 */         _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */         
/* 2566 */         _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */         
/* 2568 */         _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2569 */         int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2570 */         if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2571 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */         }
/*      */         
/* 2574 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2575 */         String clear = null;
/* 2576 */         clear = (String)_jspx_page_context.findAttribute("clear");
/* 2577 */         out.write(10);
/*      */         
/* 2579 */         DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2580 */         _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2581 */         _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */         
/* 2583 */         _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */         
/* 2585 */         _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */         
/* 2587 */         _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */         
/* 2589 */         _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2590 */         int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2591 */         if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2592 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */         }
/*      */         
/* 2595 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2596 */         String warning = null;
/* 2597 */         warning = (String)_jspx_page_context.findAttribute("warning");
/* 2598 */         out.write(10);
/* 2599 */         out.write(10);
/*      */         
/* 2601 */         String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2602 */         boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */         
/* 2604 */         out.write(10);
/* 2605 */         out.write(10);
/* 2606 */         out.write(10);
/* 2607 */         out.write("\n\n\n\n\n\n\n\n\n \n");
/*      */         
/* 2609 */         InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2610 */         _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2611 */         _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */         
/* 2613 */         _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2614 */         int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2615 */         if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */           for (;;) {
/* 2617 */             out.write(32);
/*      */             
/* 2619 */             PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2620 */             _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2621 */             _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/* 2623 */             _jspx_th_tiles_005fput_005f0.setName("title");
/*      */             
/* 2625 */             _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.configurealert.title"));
/* 2626 */             int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2627 */             if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2628 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */             }
/*      */             
/* 2631 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2632 */             out.write(10);
/*      */             
/* 2634 */             ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2635 */             _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2636 */             _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/* 2637 */             int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2638 */             if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */               for (;;) {
/* 2640 */                 out.write(10);
/*      */                 
/* 2642 */                 WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2643 */                 _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2644 */                 _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                 
/* 2646 */                 _jspx_th_c_005fwhen_005f0.setTest("${empty param.all}");
/* 2647 */                 int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2648 */                 if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                   for (;;) {
/* 2650 */                     out.write(10);
/* 2651 */                     if (!admin) {
/* 2652 */                       out.write(10);
/* 2653 */                       if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 2655 */                       out.write(32);
/*      */                       
/* 2657 */                       PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2658 */                       _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2659 */                       _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 2661 */                       _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */                       
/* 2663 */                       _jspx_th_tiles_005fput_005f2.setType("string");
/* 2664 */                       int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2665 */                       if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2666 */                         if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2667 */                           out = _jspx_page_context.pushBody();
/* 2668 */                           _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2669 */                           _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2672 */                           out.write(10);
/* 2673 */                           out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*      */                           
/* 2675 */                           int subGroupLevels1 = com.adventnet.appmanager.util.Constants.getSubGroupLevels();
/*      */                           
/* 2677 */                           out.write("\n<SCRIPT language=JavaScript1.2 src=\"/template/mm_menu.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\n");
/*      */                           
/* 2679 */                           String requestpathnew = "/images/mm_menu3.jsp";
/* 2680 */                           String category_2 = String.valueOf(com.adventnet.appmanager.util.Constants.isMinimizedversion());
/* 2681 */                           pageContext.setAttribute("category_2", category_2);
/*      */                           
/* 2683 */                           out.write(10);
/* 2684 */                           JspRuntimeLibrary.include(request, response, requestpathnew, out, false);
/* 2685 */                           out.write("\n</script>\n  <SCRIPT language=JavaScript1.2>mmLoadMenus()</SCRIPT>\n      ");
/*      */                           
/* 2687 */                           IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2688 */                           _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2689 */                           _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                           
/* 2691 */                           _jspx_th_c_005fif_005f0.setTest("${!empty param.haid }");
/* 2692 */                           int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2693 */                           if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                             for (;;) {
/* 2695 */                               out.write(" \n      \t  ");
/* 2696 */                               if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 2698 */                               out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr> \n          <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 2699 */                               out.print(FormatUtil.getString("am.webclient.applicationlinks.monitorgrouplinks.text"));
/* 2700 */                               out.write("</td>\n        </tr>\n\t\t        <tr> \n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" > ");
/* 2701 */                               if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 2703 */                               out.write(32);
/* 2704 */                               if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 2706 */                               out.write(" </td>\n        </tr>\n\n\t");
/*      */                               
/* 2708 */                               PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2709 */                               _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2710 */                               _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                               
/* 2712 */                               _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 2713 */                               int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2714 */                               if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                 for (;;) {
/* 2716 */                                   out.write(" \t \n\t<tr> \t \n\t");
/*      */                                   
/* 2718 */                                   Vector ParentMos1 = (Vector)request.getAttribute("ParentMos");
/* 2719 */                                   if ((ParentMos1 != null) && (ParentMos1.size() < subGroupLevels1)) {
/* 2720 */                                     out.write(" \t \n\t\t<td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > \t \n\t\t\t<a href=\"#\"  onClick=\"toggleDiv('subgroup');\" class=\"new-left-links\">");
/* 2721 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 2722 */                                     out.write("</a> \t \n\t\t\t</td> \t \n\t\t\t");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2726 */                                     out.write(" \t \n\t<td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > \t \n\t\t<a href=\"#\"   class=\"disabledtext\">");
/* 2727 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 2728 */                                     out.write("</a> \t \n\t\t</td> \t \n\t\t");
/*      */                                   }
/* 2730 */                                   out.write(" \t \n\t\t</tr> \t \n\t\t");
/* 2731 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2732 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2736 */                               if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2737 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                               }
/*      */                               
/* 2740 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2741 */                               out.write("\n\n       ");
/*      */                               
/* 2743 */                               PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2744 */                               _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2745 */                               _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f0);
/*      */                               
/* 2747 */                               _jspx_th_logic_005fpresent_005f1.setRole("ENTERPRISEADMIN");
/* 2748 */                               int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2749 */                               if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                 for (;;) {
/* 2751 */                                   out.write("\n\t   <tr>\n\t    ");
/*      */                                   
/* 2753 */                                   Vector ParentMos2 = (Vector)request.getAttribute("ParentMos");
/* 2754 */                                   int mgResourceId = Integer.parseInt(request.getParameter("haid"));
/* 2755 */                                   if ((ParentMos2 != null) && (ParentMos2.size() < subGroupLevels1) && (mgResourceId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE)) {
/* 2756 */                                     out.write("\n\t\t  <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" >\n\t\t    <a href=\"#\"  onClick=\"toggleDiv('subgroup');\" class=\"new-left-links\">");
/* 2757 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 2758 */                                     out.write("</a>\n\t\t  </td>\n\t\t  ");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2762 */                                     out.write("\n\t\t   <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" >\n\t\t     <a href=\"#\"   class=\"disabledtext\">");
/* 2763 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 2764 */                                     out.write("</a>\n\t\t   </td>\n\t\t   ");
/*      */                                   }
/* 2766 */                                   out.write("\n\t   </tr>\n\t   ");
/* 2767 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2768 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2772 */                               if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2773 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                               }
/*      */                               
/* 2776 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2777 */                               out.write("\n\n        <tr> \n\t\t               ");
/*      */                               
/* 2779 */                               PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2780 */                               _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2781 */                               _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f0);
/*      */                               
/* 2783 */                               _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 2784 */                               int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2785 */                               if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                 for (;;) {
/* 2787 */                                   out.write("\n\t\t                 <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > ");
/*      */                                   
/* 2789 */                                   IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2790 */                                   _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2791 */                                   _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fpresent_005f2);
/*      */                                   
/* 2793 */                                   _jspx_th_c_005fif_005f4.setTest("${param.method!='getHAProfiles'}");
/* 2794 */                                   int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2795 */                                   if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                     for (;;) {
/* 2797 */                                       out.write(" \n\t\t                   <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 2798 */                                       if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                         return;
/* 2800 */                                       out.write("\" \n\t\t                   class=\"new-left-links\">");
/* 2801 */                                       out.print(ALERTCONFIG_TEXT);
/* 2802 */                                       out.write("</a>");
/* 2803 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2804 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2808 */                                   if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2809 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                   }
/*      */                                   
/* 2812 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2813 */                                   out.write(32);
/*      */                                   
/* 2815 */                                   IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2816 */                                   _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2817 */                                   _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fpresent_005f2);
/*      */                                   
/* 2819 */                                   _jspx_th_c_005fif_005f5.setTest("${param.method=='getHAProfiles'}");
/* 2820 */                                   int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2821 */                                   if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                     for (;;) {
/* 2823 */                                       out.write(" \n\t\t                   ");
/* 2824 */                                       out.print(ALERTCONFIG_TEXT);
/* 2825 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2826 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2830 */                                   if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2831 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                   }
/*      */                                   
/* 2834 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2835 */                                   out.write("</td>\n\t\t                   ");
/* 2836 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2837 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2841 */                               if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2842 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                               }
/*      */                               
/* 2845 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2846 */                               out.write("\t\n\t\t       \n\t\t                   ");
/*      */                               
/* 2848 */                               PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2849 */                               _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2850 */                               _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f0);
/*      */                               
/* 2852 */                               _jspx_th_logic_005fpresent_005f3.setRole("OPERATOR");
/* 2853 */                               int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2854 */                               if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                 for (;;) {
/* 2856 */                                   out.write("\n\t\t                   <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > \n\t\t                   ");
/*      */                                   
/* 2858 */                                   AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2859 */                                   _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 2860 */                                   _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                                   
/* 2862 */                                   _jspx_th_am_005fadminlink_005f0.setHref(ALERTCONFIG_TEXT);
/*      */                                   
/* 2864 */                                   _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 2865 */                                   int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 2866 */                                   if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 2867 */                                     if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2868 */                                       out = _jspx_page_context.pushBody();
/* 2869 */                                       _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 2870 */                                       _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 2873 */                                       out.print(FormatUtil.getString("am.webclient.toolbar.configurealert.text"));
/* 2874 */                                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 2875 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 2878 */                                     if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2879 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 2882 */                                   if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 2883 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                   }
/*      */                                   
/* 2886 */                                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 2887 */                                   out.write("</td>\n\t\t                ");
/* 2888 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2889 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2893 */                               if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2894 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                               }
/*      */                               
/* 2897 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2898 */                               out.write("\t\t                   \n       </tr>\n        \n\t\t");
/*      */                               
/* 2900 */                               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2901 */                               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2902 */                               _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f0);
/*      */                               
/* 2904 */                               _jspx_th_c_005fif_005f6.setTest("${category_2!='true'}");
/* 2905 */                               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2906 */                               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                 for (;;) {
/* 2908 */                                   out.write("\n          <tr>   ");
/*      */                                   
/* 2910 */                                   PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2911 */                                   _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 2912 */                                   _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f6);
/*      */                                   
/* 2914 */                                   _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 2915 */                                   int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 2916 */                                   if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                     for (;;) {
/* 2918 */                                       out.write("\n          ");
/* 2919 */                                       if (OEMUtil.isOEM()) {
/* 2920 */                                         out.write("\n          <td width=\"98%\" height=\"21\"  class=\"leftlinkstd\"  > \n          ");
/*      */                                       } else {
/* 2922 */                                         out.write("\n\t          <td width=\"98%\" height=\"21\"  class=\"leftlinkstd\"> \n\t          ");
/*      */                                       }
/* 2924 */                                       out.write("\n\t          ");
/*      */                                       
/* 2926 */                                       IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2927 */                                       _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2928 */                                       _jspx_th_c_005fif_005f7.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                                       
/* 2930 */                                       _jspx_th_c_005fif_005f7.setTest("${!empty ADMIN}");
/* 2931 */                                       int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2932 */                                       if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                         for (;;) {
/* 2934 */                                           out.write("\n\t          ");
/*      */                                           
/* 2936 */                                           IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2937 */                                           _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2938 */                                           _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f7);
/*      */                                           
/* 2940 */                                           _jspx_th_c_005fif_005f8.setTest("${param.method!='getMonitorForm' }");
/* 2941 */                                           int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2942 */                                           if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                             for (;;) {
/* 2944 */                                               out.write(" \n\t            <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=");
/* 2945 */                                               if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                                                 return;
/* 2947 */                                               out.write("\" \n\t            class=\"new-left-links\" >");
/* 2948 */                                               out.print(FormatUtil.getString("am.webclient.applicationlinks.associatemonitor.text"));
/* 2949 */                                               out.write(32);
/* 2950 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2951 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 2955 */                                           if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2956 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                           }
/*      */                                           
/* 2959 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2960 */                                           out.write(" \n\t            ");
/*      */                                           
/* 2962 */                                           IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2963 */                                           _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2964 */                                           _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f7);
/*      */                                           
/* 2966 */                                           _jspx_th_c_005fif_005f9.setTest("${param.method=='getMonitorForm' || (empty ADMIN)}");
/* 2967 */                                           int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2968 */                                           if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                             for (;;) {
/* 2970 */                                               out.write(" \n\t            ");
/* 2971 */                                               out.print(FormatUtil.getString("am.webclient.applicationlinks.associatemonitor.text"));
/* 2972 */                                               out.write(32);
/* 2973 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2974 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 2978 */                                           if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2979 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                           }
/*      */                                           
/* 2982 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2983 */                                           out.write("\n\t            ");
/* 2984 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2985 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2989 */                                       if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2990 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                       }
/*      */                                       
/* 2993 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2994 */                                       out.write("\n\t            ");
/*      */                                       
/* 2996 */                                       IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2997 */                                       _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2998 */                                       _jspx_th_c_005fif_005f10.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                                       
/* 3000 */                                       _jspx_th_c_005fif_005f10.setTest("${empty ADMIN}");
/* 3001 */                                       int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3002 */                                       if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                         for (;;) {
/* 3004 */                                           out.write("\n\t            ");
/*      */                                           
/* 3006 */                                           AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3007 */                                           _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 3008 */                                           _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fif_005f10);
/*      */                                           
/* 3010 */                                           _jspx_th_am_005fadminlink_005f1.setHref("/jsp/CreateApplication.jsp");
/*      */                                           
/* 3012 */                                           _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 3013 */                                           int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 3014 */                                           if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 3015 */                                             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 3016 */                                               out = _jspx_page_context.pushBody();
/* 3017 */                                               _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 3018 */                                               _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 3021 */                                               out.write("\n\t            \t");
/* 3022 */                                               out.print(FormatUtil.getString("am.webclient.applicationlinks.associatemonitor.text"));
/* 3023 */                                               out.write("\n\t            ");
/* 3024 */                                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 3025 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 3028 */                                             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 3029 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 3032 */                                           if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 3033 */                                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                                           }
/*      */                                           
/* 3036 */                                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 3037 */                                           out.write("  \t\n\t            ");
/* 3038 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3039 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3043 */                                       if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3044 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                       }
/*      */                                       
/* 3047 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3048 */                                       out.write("   \n\t            </td>\n\t             ");
/* 3049 */                                       if (OEMUtil.isOEM()) {
/* 3050 */                                         out.write("\n\t             <td class=\"leftlinkstd\"  > \n\t\t\t\t<A class=\"new-left-links\" href=\"#\" ></a>   \n\t\t\t\t</td>\n          \n          ");
/*      */                                       } else {
/* 3052 */                                         out.write("\n\t\t\t\t<td class=\"leftlinkstd\"  > \n\t\t\t\t<A class=\"new-left-links\" href=\"#\" ></a>   \n\t\t\t\t</td>\n\t\t\t\t  ");
/*      */                                       }
/* 3054 */                                       out.write("\n          ");
/* 3055 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3056 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3060 */                                   if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3061 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                   }
/*      */                                   
/* 3064 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3065 */                                   out.write("\n        </tr>\n        ");
/* 3066 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3067 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3071 */                               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3072 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                               }
/*      */                               
/* 3075 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3076 */                               out.write("\n\n        <tr> \n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" ><a href=\"/fault/AMAlarmView.do?displayName=Alerts&haid=");
/* 3077 */                               if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 3079 */                               out.write("\" \n            class=\"new-left-links\">");
/* 3080 */                               out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/* 3081 */                               out.write(" </td>\n        </tr>\n        <tr> \n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" ><a href=\"javascript:fnOpenNewWindow('/showReports.do?period=0&actionMethod=generateHAAvailabilityReport&haid=");
/* 3082 */                               if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                 return;
/* 3084 */                               out.write("')\" \n            class=\"new-left-links\"> ");
/* 3085 */                               out.print(FormatUtil.getString("am.webclient.applicationlinks.applicationreport.text"));
/* 3086 */                               out.write("</td>\n        </tr>\n        \n        <tr> \n        ");
/*      */                               
/* 3088 */                               String editlink = "/showapplication.do?method=editApplication&haid=" + request.getParameter("haid");
/*      */                               
/* 3090 */                               out.write(10);
/* 3091 */                               out.write(9);
/* 3092 */                               out.write(9);
/*      */                               
/* 3094 */                               PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3095 */                               _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3096 */                               _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f0);
/*      */                               
/* 3098 */                               _jspx_th_logic_005fpresent_005f5.setRole("ENTERPRISEADMIN");
/* 3099 */                               int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3100 */                               if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                 for (;;) {
/* 3102 */                                   out.write("\n\t\t\t<td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" ><a href=\"");
/* 3103 */                                   out.print(editlink);
/* 3104 */                                   out.write("\"\n\t\t\tClass=\"new-left-links\">");
/* 3105 */                                   out.print(FormatUtil.getString("am.webclient.maintenance.edit"));
/* 3106 */                                   out.write("</a></td>\n\t\t");
/* 3107 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3108 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3112 */                               if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3113 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                               }
/*      */                               
/* 3116 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3117 */                               out.write("\t\n\t\t");
/*      */                               
/* 3119 */                               NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3120 */                               _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3121 */                               _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                               
/* 3123 */                               _jspx_th_logic_005fnotPresent_005f0.setRole("ENTERPRISEADMIN");
/* 3124 */                               int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3125 */                               if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                 for (;;) {
/* 3127 */                                   out.write("\n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" >");
/*      */                                   
/* 3129 */                                   AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3130 */                                   _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 3131 */                                   _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                   
/* 3133 */                                   _jspx_th_am_005fadminlink_005f2.setHref(editlink);
/*      */                                   
/* 3135 */                                   _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 3136 */                                   int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 3137 */                                   if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 3138 */                                     if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 3139 */                                       out = _jspx_page_context.pushBody();
/* 3140 */                                       _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 3141 */                                       _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3144 */                                       out.print(FormatUtil.getString("am.webclient.maintenance.edit"));
/* 3145 */                                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 3146 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3149 */                                     if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 3150 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3153 */                                   if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 3154 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                                   }
/*      */                                   
/* 3157 */                                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 3158 */                                   out.write("</td>");
/* 3159 */                                   out.write(" \n\t\t");
/* 3160 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3161 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3165 */                               if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3166 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                               }
/*      */                               
/* 3169 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3170 */                               out.write("\t\n        </tr>\n        \n        <tr> \n            <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" >\n\t\t\t");
/*      */                               
/* 3172 */                               int mgResId = Integer.parseInt(request.getParameter("haid"));
/* 3173 */                               if ((EnterpriseUtil.isAdminServer()) && (mgResId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE) && (request.isUserInRole("ENTERPRISEADMIN")))
/*      */                               {
/*      */ 
/* 3176 */                                 out.write("\n\t\t\t\t<a href=\"javascript:applndelete()\" class=\"new-left-links\">");
/* 3177 */                                 out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3178 */                                 out.write("</a>\n\t\t\t");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/* 3183 */                                 out.write(" \n\t\t");
/*      */                                 
/* 3185 */                                 AdminLink _jspx_th_am_005fadminlink_005f3 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3186 */                                 _jspx_th_am_005fadminlink_005f3.setPageContext(_jspx_page_context);
/* 3187 */                                 _jspx_th_am_005fadminlink_005f3.setParent(_jspx_th_c_005fif_005f0);
/*      */                                 
/* 3189 */                                 _jspx_th_am_005fadminlink_005f3.setHref("javascript:applndelete()");
/*      */                                 
/* 3191 */                                 _jspx_th_am_005fadminlink_005f3.setEnableClass("new-left-links");
/* 3192 */                                 int _jspx_eval_am_005fadminlink_005f3 = _jspx_th_am_005fadminlink_005f3.doStartTag();
/* 3193 */                                 if (_jspx_eval_am_005fadminlink_005f3 != 0) {
/* 3194 */                                   if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/* 3195 */                                     out = _jspx_page_context.pushBody();
/* 3196 */                                     _jspx_th_am_005fadminlink_005f3.setBodyContent((BodyContent)out);
/* 3197 */                                     _jspx_th_am_005fadminlink_005f3.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3200 */                                     out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3201 */                                     int evalDoAfterBody = _jspx_th_am_005fadminlink_005f3.doAfterBody();
/* 3202 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3205 */                                   if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/* 3206 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3209 */                                 if (_jspx_th_am_005fadminlink_005f3.doEndTag() == 5) {
/* 3210 */                                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f3); return;
/*      */                                 }
/*      */                                 
/* 3213 */                                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f3);
/* 3214 */                                 out.write(10);
/* 3215 */                                 out.write(9);
/* 3216 */                                 out.write(9);
/*      */                               }
/* 3218 */                               out.write("\n\t\t\t</td>\n        </tr>\n        \n\t\t\t");
/*      */                               
/*      */ 
/* 3221 */                               if (((EnterpriseUtil.isAdminServer()) && (mgResId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE)) || (!EnterpriseUtil.isAdminServer()))
/*      */                               {
/*      */ 
/* 3224 */                                 out.write("\n\t        <tr> \n            <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" >\n\t\t\t\t<a href=\"javascript:refreshstatus()\" class=\"new-left-links\">");
/* 3225 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.refresh.status.text"));
/* 3226 */                                 out.write("</a>&nbsp;<img  src=\"../images/icon_refresh.gif\" align=\"absmiddle\" border=\"0\"/>\n\t\t\t</td>\n\t\t\t </tr>\n\t\t\t");
/*      */                               }
/* 3228 */                               out.write("\n\t\t\n\t\t\t\n       \n\n        \n\n            </table>\n     ");
/* 3229 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3230 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3234 */                           if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3235 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                           }
/*      */                           
/* 3238 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3239 */                           out.write("\n     \n<script>\n\nfunction applndelete()\n{\n");
/*      */                           
/* 3241 */                           IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3242 */                           _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3243 */                           _jspx_th_c_005fif_005f11.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                           
/* 3245 */                           _jspx_th_c_005fif_005f11.setTest("${MGtype=='1' }");
/* 3246 */                           int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3247 */                           if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                             for (;;) {
/* 3249 */                               out.write(" \n\tif(confirm('");
/* 3250 */                               out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertfordeletesub-group.text"));
/* 3251 */                               out.write("')) {\n\tlocation.href = \"/manageApplications.do?method=delete&select=");
/* 3252 */                               if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                 return;
/* 3254 */                               out.write("\";\n\t}\n");
/* 3255 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3256 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3260 */                           if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3261 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                           }
/*      */                           
/* 3264 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3265 */                           out.write("\n        \n");
/*      */                           
/* 3267 */                           IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3268 */                           _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3269 */                           _jspx_th_c_005fif_005f12.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                           
/* 3271 */                           _jspx_th_c_005fif_005f12.setTest("${MGtype!='1' }");
/* 3272 */                           int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3273 */                           if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                             for (;;) {
/* 3275 */                               out.write("         \n\tif(confirm('");
/* 3276 */                               out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertfordeletemg.text"));
/* 3277 */                               out.write("'))\n\t{\n\t location.href = \"/manageApplications.do?method=delete&select=");
/* 3278 */                               if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f12, _jspx_page_context))
/*      */                                 return;
/* 3280 */                               out.write("\";\n\t}\n");
/* 3281 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3282 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3286 */                           if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3287 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                           }
/*      */                           
/* 3290 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3291 */                           out.write("\n} \nfunction refreshstatus()\n{\t\n\t location.href = \"/manageApplications.do?method=refreshNow&haid=");
/* 3292 */                           if (_jspx_meth_c_005fout_005f9(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                             return;
/* 3294 */                           out.write("\";\n}\n</script>\n");
/* 3295 */                           out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr><td width=\"80%\" class=\"leftlinksquicknote\">");
/* 3296 */                           out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 3297 */                           out.write("</td>\n    <td width=\"20%\" class=\"leftlinksheading\"><img src=\"/images/");
/* 3298 */                           if (_jspx_meth_c_005fout_005f10(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                             return;
/* 3300 */                           out.write("/img_quicknote.gif\"      hspace=\"5\"></td>\n  </tr>\n  <tr>\n    <td colspan=\"2\" class=\"quicknote\"> ");
/* 3301 */                           out.print(FormatUtil.getString("am.webclient.configurealert.quicknotethresholdpage"));
/* 3302 */                           out.write(" </td>\n  </tr>\n</table>\n");
/* 3303 */                           int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3304 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3307 */                         if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3308 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3311 */                       if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3312 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                       }
/*      */                       
/* 3315 */                       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3316 */                       out.write(10);
/*      */                     } else {
/* 3318 */                       out.write(10);
/* 3319 */                       if (_jspx_meth_tiles_005fput_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3321 */                       out.write(32);
/*      */                       
/* 3323 */                       PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3324 */                       _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3325 */                       _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 3327 */                       _jspx_th_tiles_005fput_005f4.setName("LeftArea");
/*      */                       
/* 3329 */                       _jspx_th_tiles_005fput_005f4.setType("string");
/* 3330 */                       int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3331 */                       if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 3332 */                         if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 3333 */                           out = _jspx_page_context.pushBody();
/* 3334 */                           _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 3335 */                           _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3338 */                           out.write(10);
/* 3339 */                           out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */                           
/*      */ 
/* 3342 */                           String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                           
/* 3344 */                           out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/* 3345 */                           out.print(FormatUtil.getString("wizard.disabled"));
/* 3346 */                           out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/* 3347 */                           out.print(FormatUtil.getString("am.webclient.admin.heading"));
/* 3348 */                           out.write("</td>\n  </tr>\n  \n ");
/*      */                           
/* 3350 */                           if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                           {
/*      */ 
/* 3353 */                             out.write("  \n  <tr>\n\n  ");
/*      */                             
/* 3355 */                             if (request.getParameter("wiz") != null)
/*      */                             {
/* 3357 */                               out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/* 3358 */                               out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 3359 */                               out.write("\" class='disabledlink'>");
/* 3360 */                               out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 3361 */                               out.write("</a></td>\n  ");
/*      */                             }
/*      */                             else
/*      */                             {
/* 3365 */                               out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */                               
/* 3367 */                               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3368 */                               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 3369 */                               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/* 3370 */                               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 3371 */                               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                 for (;;) {
/* 3373 */                                   out.write(10);
/*      */                                   
/* 3375 */                                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3376 */                                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 3377 */                                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                   
/* 3379 */                                   _jspx_th_c_005fwhen_005f1.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/* 3380 */                                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 3381 */                                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                     for (;;) {
/* 3383 */                                       out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/* 3384 */                                       out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 3385 */                                       out.write("\n    </a>\n ");
/* 3386 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 3387 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3391 */                                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 3392 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                   }
/*      */                                   
/* 3395 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3396 */                                   out.write(10);
/* 3397 */                                   out.write(32);
/*      */                                   
/* 3399 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3400 */                                   _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3401 */                                   _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f1);
/* 3402 */                                   int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3403 */                                   if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                     for (;;) {
/* 3405 */                                       out.write(10);
/* 3406 */                                       out.write(9);
/* 3407 */                                       out.write(32);
/* 3408 */                                       out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 3409 */                                       out.write(10);
/* 3410 */                                       out.write(32);
/* 3411 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3412 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3416 */                                   if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3417 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                   }
/*      */                                   
/* 3420 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3421 */                                   out.write(10);
/* 3422 */                                   out.write(32);
/* 3423 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 3424 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3428 */                               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 3429 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                               }
/*      */                               
/* 3432 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3433 */                               out.write("\n    </td>\n\t");
/*      */                             }
/* 3435 */                             out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */                             
/* 3437 */                             if (request.getParameter("wiz") != null)
/*      */                             {
/* 3439 */                               out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/* 3440 */                               out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 3441 */                               out.write("\" class='disabledlink'>");
/* 3442 */                               out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 3443 */                               out.write("</a></td>\n   ");
/*      */                             }
/*      */                             else
/*      */                             {
/* 3447 */                               out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */                               
/* 3449 */                               ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3450 */                               _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 3451 */                               _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/* 3452 */                               int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 3453 */                               if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                 for (;;) {
/* 3455 */                                   out.write(10);
/*      */                                   
/* 3457 */                                   WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3458 */                                   _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3459 */                                   _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                   
/* 3461 */                                   _jspx_th_c_005fwhen_005f2.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/* 3462 */                                   int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3463 */                                   if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                     for (;;) {
/* 3465 */                                       out.write("\n   ");
/* 3466 */                                       out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 3467 */                                       out.write(10);
/* 3468 */                                       out.write(32);
/* 3469 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3470 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3474 */                                   if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3475 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                   }
/*      */                                   
/* 3478 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3479 */                                   out.write(10);
/* 3480 */                                   out.write(32);
/*      */                                   
/* 3482 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3483 */                                   _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 3484 */                                   _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f2);
/* 3485 */                                   int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 3486 */                                   if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                     for (;;) {
/* 3488 */                                       out.write(10);
/* 3489 */                                       String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/* 3490 */                                       out.write("\n\t \n <a href=\"");
/* 3491 */                                       out.print(link);
/* 3492 */                                       out.write("\" class=\"new-left-links\">\n               ");
/* 3493 */                                       out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 3494 */                                       out.write("\n    </a>    \n ");
/* 3495 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3496 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3500 */                                   if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3501 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                   }
/*      */                                   
/* 3504 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3505 */                                   out.write(10);
/* 3506 */                                   out.write(32);
/* 3507 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 3508 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3512 */                               if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 3513 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                               }
/*      */                               
/* 3516 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 3517 */                               out.write("\n</td>\n");
/*      */                             }
/* 3519 */                             out.write("\n</tr>\n\n ");
/*      */                           }
/*      */                           
/*      */ 
/* 3523 */                           out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                           
/* 3525 */                           ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3526 */                           _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 3527 */                           _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/* 3528 */                           int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 3529 */                           if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                             for (;;) {
/* 3531 */                               out.write(10);
/*      */                               
/* 3533 */                               WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3534 */                               _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 3535 */                               _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                               
/* 3537 */                               _jspx_th_c_005fwhen_005f3.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/* 3538 */                               int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 3539 */                               if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                 for (;;) {
/* 3541 */                                   out.write("\n    \n       ");
/* 3542 */                                   out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 3543 */                                   out.write(10);
/* 3544 */                                   out.write(32);
/* 3545 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 3546 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3550 */                               if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 3551 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                               }
/*      */                               
/* 3554 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3555 */                               out.write(10);
/* 3556 */                               out.write(32);
/*      */                               
/* 3558 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3559 */                               _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 3560 */                               _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f3);
/* 3561 */                               int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 3562 */                               if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                                 for (;;) {
/* 3564 */                                   out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/* 3565 */                                   out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 3566 */                                   out.write("\n    </a>\n ");
/* 3567 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3568 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3572 */                               if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3573 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                               }
/*      */                               
/* 3576 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3577 */                               out.write(10);
/* 3578 */                               out.write(32);
/* 3579 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 3580 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3584 */                           if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 3585 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                           }
/*      */                           
/* 3588 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 3589 */                           out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                           
/* 3591 */                           ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3592 */                           _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 3593 */                           _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/* 3594 */                           int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 3595 */                           if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                             for (;;) {
/* 3597 */                               out.write(10);
/*      */                               
/* 3599 */                               WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3600 */                               _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 3601 */                               _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                               
/* 3603 */                               _jspx_th_c_005fwhen_005f4.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/* 3604 */                               int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 3605 */                               if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                 for (;;) {
/* 3607 */                                   out.write("\n    \n       ");
/* 3608 */                                   out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 3609 */                                   out.write(10);
/* 3610 */                                   out.write(32);
/* 3611 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 3612 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3616 */                               if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 3617 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                               }
/*      */                               
/* 3620 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 3621 */                               out.write(10);
/* 3622 */                               out.write(32);
/*      */                               
/* 3624 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3625 */                               _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 3626 */                               _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f4);
/* 3627 */                               int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 3628 */                               if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                                 for (;;) {
/* 3630 */                                   out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/* 3631 */                                   out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 3632 */                                   out.write("\n\t </a>\n ");
/* 3633 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 3634 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3638 */                               if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 3639 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                               }
/*      */                               
/* 3642 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3643 */                               out.write(10);
/* 3644 */                               out.write(32);
/* 3645 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 3646 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3650 */                           if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 3651 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                           }
/*      */                           
/* 3654 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 3655 */                           out.write("\n    </td>\n</tr>  \n\n  ");
/*      */                           
/* 3657 */                           if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                           {
/*      */ 
/* 3660 */                             out.write(32);
/* 3661 */                             out.write(32);
/* 3662 */                             out.write(10);
/*      */                             
/* 3664 */                             ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3665 */                             _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 3666 */                             _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/* 3667 */                             int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 3668 */                             if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                               for (;;) {
/* 3670 */                                 out.write(10);
/*      */                                 
/* 3672 */                                 WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3673 */                                 _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 3674 */                                 _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                                 
/* 3676 */                                 _jspx_th_c_005fwhen_005f5.setTest("${param.method !='showNetworkDiscoveryForm'}");
/* 3677 */                                 int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 3678 */                                 if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                   for (;;) {
/* 3680 */                                     out.write("\n<tr>\n    ");
/* 3681 */                                     if (!request.isUserInRole("OPERATOR")) {
/* 3682 */                                       out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/* 3683 */                                       out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 3684 */                                       out.write("\n    </a>\n        </td>\n     ");
/*      */                                     } else {
/* 3686 */                                       out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/* 3687 */                                       out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 3688 */                                       out.write("\n\t</a>\n\t </td>\n\t");
/*      */                                     }
/* 3690 */                                     out.write("\n    </tr>\n ");
/* 3691 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 3692 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3696 */                                 if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 3697 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                                 }
/*      */                                 
/* 3700 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 3701 */                                 out.write(10);
/* 3702 */                                 out.write(32);
/*      */                                 
/* 3704 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3705 */                                 _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 3706 */                                 _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f5);
/* 3707 */                                 int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 3708 */                                 if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                   for (;;) {
/* 3710 */                                     out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/* 3711 */                                     out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 3712 */                                     out.write("\n\t </td>\n ");
/* 3713 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 3714 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3718 */                                 if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 3719 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                                 }
/*      */                                 
/* 3722 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 3723 */                                 out.write(10);
/* 3724 */                                 out.write(32);
/* 3725 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 3726 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3730 */                             if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 3731 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                             }
/*      */                             
/* 3734 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 3735 */                             out.write("\n \n  ");
/*      */                           }
/*      */                           
/*      */ 
/* 3739 */                           out.write("  \n \n ");
/*      */                           
/* 3741 */                           if (!usertype.equals("F"))
/*      */                           {
/* 3743 */                             out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */                             
/* 3745 */                             ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3746 */                             _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 3747 */                             _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_tiles_005fput_005f4);
/* 3748 */                             int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 3749 */                             if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                               for (;;) {
/* 3751 */                                 out.write(10);
/* 3752 */                                 out.write(9);
/*      */                                 
/* 3754 */                                 WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3755 */                                 _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 3756 */                                 _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                                 
/* 3758 */                                 _jspx_th_c_005fwhen_005f6.setTest("${param.method !='maintenanceTaskListView'}");
/* 3759 */                                 int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 3760 */                                 if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                                   for (;;) {
/* 3762 */                                     out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 3763 */                                     out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3764 */                                     out.write("</a>\n  \t");
/* 3765 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 3766 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3770 */                                 if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 3771 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                                 }
/*      */                                 
/* 3774 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 3775 */                                 out.write("\n  \t");
/*      */                                 
/* 3777 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3778 */                                 _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 3779 */                                 _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f6);
/* 3780 */                                 int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 3781 */                                 if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                                   for (;;) {
/* 3783 */                                     out.write("\n \t\t");
/* 3784 */                                     out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3785 */                                     out.write("\n  \t");
/* 3786 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 3787 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3791 */                                 if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 3792 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                                 }
/*      */                                 
/* 3795 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 3796 */                                 out.write("\n  \t");
/* 3797 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 3798 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3802 */                             if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 3803 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                             }
/*      */                             
/* 3806 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 3807 */                             out.write("\n     </td>\n </tr>   \n \n ");
/*      */                             
/* 3809 */                             if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                             {
/*      */ 
/* 3812 */                               out.write(32);
/* 3813 */                               out.write(32);
/* 3814 */                               out.write(10);
/*      */                               
/* 3816 */                               IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3817 */                               _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3818 */                               _jspx_th_c_005fif_005f13.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 3820 */                               _jspx_th_c_005fif_005f13.setTest("${category!='LAMP'}");
/* 3821 */                               int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3822 */                               if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                 for (;;) {
/* 3824 */                                   out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                                   
/* 3826 */                                   ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3827 */                                   _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 3828 */                                   _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_c_005fif_005f13);
/* 3829 */                                   int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 3830 */                                   if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                                     for (;;) {
/* 3832 */                                       out.write(10);
/* 3833 */                                       out.write(32);
/* 3834 */                                       out.write(9);
/*      */                                       
/* 3836 */                                       WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3837 */                                       _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 3838 */                                       _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                                       
/* 3840 */                                       _jspx_th_c_005fwhen_005f7.setTest("${param.method !='listTrapListener'}");
/* 3841 */                                       int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 3842 */                                       if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                                         for (;;) {
/* 3844 */                                           out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/* 3845 */                                           out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3846 */                                           out.write("</a>\n   \t");
/* 3847 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 3848 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3852 */                                       if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 3853 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                                       }
/*      */                                       
/* 3856 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 3857 */                                       out.write("\n   \t");
/*      */                                       
/* 3859 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3860 */                                       _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 3861 */                                       _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f7);
/* 3862 */                                       int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 3863 */                                       if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                                         for (;;) {
/* 3865 */                                           out.write("\n  \t\t");
/* 3866 */                                           out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3867 */                                           out.write(" \n   \t");
/* 3868 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 3869 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3873 */                                       if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 3874 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                                       }
/*      */                                       
/* 3877 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 3878 */                                       out.write("\n   \t");
/* 3879 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 3880 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3884 */                                   if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 3885 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                                   }
/*      */                                   
/* 3888 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 3889 */                                   out.write("\n      </td>\n  </tr>   \n");
/* 3890 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3891 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3895 */                               if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3896 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                               }
/*      */                               
/* 3899 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3900 */                               out.write(10);
/* 3901 */                               out.write(32);
/*      */                             }
/*      */                             
/*      */ 
/* 3905 */                             out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                             
/* 3907 */                             ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3908 */                             _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 3909 */                             _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_tiles_005fput_005f4);
/* 3910 */                             int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 3911 */                             if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                               for (;;) {
/* 3913 */                                 out.write(10);
/*      */                                 
/* 3915 */                                 WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3916 */                                 _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 3917 */                                 _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                                 
/* 3919 */                                 _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showScheduleReports'}");
/* 3920 */                                 int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 3921 */                                 if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                   for (;;) {
/* 3923 */                                     out.write("\n       ");
/* 3924 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3925 */                                     out.write(10);
/* 3926 */                                     out.write(32);
/* 3927 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 3928 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3932 */                                 if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 3933 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                                 }
/*      */                                 
/* 3936 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 3937 */                                 out.write(10);
/* 3938 */                                 out.write(32);
/*      */                                 
/* 3940 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3941 */                                 _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 3942 */                                 _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f8);
/* 3943 */                                 int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 3944 */                                 if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                                   for (;;) {
/* 3946 */                                     out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/* 3947 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3948 */                                     out.write("\n\t </a>\n ");
/* 3949 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 3950 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3954 */                                 if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 3955 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                                 }
/*      */                                 
/* 3958 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 3959 */                                 out.write(10);
/* 3960 */                                 out.write(32);
/* 3961 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 3962 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3966 */                             if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 3967 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                             }
/*      */                             
/* 3970 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 3971 */                             out.write("\n    </td>\n</tr> \n");
/*      */                           } else {
/* 3973 */                             out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 3974 */                             out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3975 */                             out.write("</a>\n     </td>\n </tr>   \n");
/*      */                             
/* 3977 */                             IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3978 */                             _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3979 */                             _jspx_th_c_005fif_005f14.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3981 */                             _jspx_th_c_005fif_005f14.setTest("${category!='LAMP'}");
/* 3982 */                             int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3983 */                             if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                               for (;;) {
/* 3985 */                                 out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 3986 */                                 out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3987 */                                 out.write("</a>\n\t  </td>\n  </tr>   \n");
/* 3988 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3989 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3993 */                             if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3994 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                             }
/*      */                             
/* 3997 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3998 */                             out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/* 3999 */                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 4000 */                             out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */                           }
/* 4002 */                           out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                           
/* 4004 */                           ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4005 */                           _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 4006 */                           _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_tiles_005fput_005f4);
/* 4007 */                           int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 4008 */                           if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                             for (;;) {
/* 4010 */                               out.write(10);
/*      */                               
/* 4012 */                               WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4013 */                               _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 4014 */                               _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                               
/* 4016 */                               _jspx_th_c_005fwhen_005f9.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 4017 */                               int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 4018 */                               if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                                 for (;;) {
/* 4020 */                                   out.write("\n        ");
/* 4021 */                                   out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 4022 */                                   out.write(10);
/* 4023 */                                   out.write(32);
/* 4024 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 4025 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4029 */                               if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 4030 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                               }
/*      */                               
/* 4033 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 4034 */                               out.write(10);
/* 4035 */                               out.write(32);
/*      */                               
/* 4037 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4038 */                               _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 4039 */                               _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f9);
/* 4040 */                               int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 4041 */                               if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                                 for (;;) {
/* 4043 */                                   out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/* 4044 */                                   out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 4045 */                                   out.write("\n\t </a>\n ");
/* 4046 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 4047 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4051 */                               if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 4052 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                               }
/*      */                               
/* 4055 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 4056 */                               out.write(10);
/* 4057 */                               out.write(32);
/* 4058 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 4059 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4063 */                           if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 4064 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                           }
/*      */                           
/* 4067 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 4068 */                           out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                           
/* 4070 */                           ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4071 */                           _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 4072 */                           _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_tiles_005fput_005f4);
/* 4073 */                           int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 4074 */                           if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                             for (;;) {
/* 4076 */                               out.write(10);
/*      */                               
/* 4078 */                               WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4079 */                               _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 4080 */                               _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                               
/* 4082 */                               _jspx_th_c_005fwhen_005f10.setTest("${param.method!='showMailServerConfiguration'}");
/* 4083 */                               int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 4084 */                               if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                                 for (;;) {
/* 4086 */                                   out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 4087 */                                   out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 4088 */                                   out.write("\n    </a>    \n ");
/* 4089 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 4090 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4094 */                               if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 4095 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                               }
/*      */                               
/* 4098 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 4099 */                               out.write(10);
/* 4100 */                               out.write(32);
/*      */                               
/* 4102 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4103 */                               _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 4104 */                               _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f10);
/* 4105 */                               int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 4106 */                               if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                                 for (;;) {
/* 4108 */                                   out.write(10);
/* 4109 */                                   out.write(9);
/* 4110 */                                   out.write(32);
/* 4111 */                                   out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 4112 */                                   out.write(10);
/* 4113 */                                   out.write(32);
/* 4114 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 4115 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4119 */                               if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 4120 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                               }
/*      */                               
/* 4123 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 4124 */                               out.write(10);
/* 4125 */                               out.write(32);
/* 4126 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 4127 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4131 */                           if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 4132 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                           }
/*      */                           
/* 4135 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 4136 */                           out.write("\n    </td>\n</tr>\n\n\n");
/* 4137 */                           if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 4138 */                             out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                             
/* 4140 */                             ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4141 */                             _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 4142 */                             _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_tiles_005fput_005f4);
/* 4143 */                             int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 4144 */                             if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                               for (;;) {
/* 4146 */                                 out.write(10);
/*      */                                 
/* 4148 */                                 WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4149 */                                 _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 4150 */                                 _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                                 
/* 4152 */                                 _jspx_th_c_005fwhen_005f11.setTest("${param.method!='SMSServerConfiguration'}");
/* 4153 */                                 int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 4154 */                                 if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                                   for (;;) {
/* 4156 */                                     out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 4157 */                                     out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 4158 */                                     out.write("\n    </a>\n ");
/* 4159 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 4160 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4164 */                                 if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 4165 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                                 }
/*      */                                 
/* 4168 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 4169 */                                 out.write(10);
/* 4170 */                                 out.write(32);
/*      */                                 
/* 4172 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4173 */                                 _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 4174 */                                 _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f11);
/* 4175 */                                 int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 4176 */                                 if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                                   for (;;) {
/* 4178 */                                     out.write("\n         ");
/* 4179 */                                     out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 4180 */                                     out.write(10);
/* 4181 */                                     out.write(32);
/* 4182 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 4183 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4187 */                                 if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 4188 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                                 }
/*      */                                 
/* 4191 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 4192 */                                 out.write(10);
/* 4193 */                                 out.write(32);
/* 4194 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 4195 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4199 */                             if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 4200 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                             }
/*      */                             
/* 4203 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 4204 */                             out.write("\n    </td>\n</tr>\n");
/*      */                           }
/* 4206 */                           out.write("\n\n\n ");
/*      */                           
/* 4208 */                           if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                           {
/*      */ 
/* 4211 */                             out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                             
/* 4213 */                             ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4214 */                             _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 4215 */                             _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_tiles_005fput_005f4);
/* 4216 */                             int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 4217 */                             if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                               for (;;) {
/* 4219 */                                 out.write(10);
/*      */                                 
/* 4221 */                                 WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4222 */                                 _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 4223 */                                 _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                                 
/* 4225 */                                 _jspx_th_c_005fwhen_005f12.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 4226 */                                 int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 4227 */                                 if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                                   for (;;) {
/* 4229 */                                     out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 4230 */                                     out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 4231 */                                     out.write("\n    </a>\n ");
/* 4232 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 4233 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4237 */                                 if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 4238 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                                 }
/*      */                                 
/* 4241 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 4242 */                                 out.write(10);
/* 4243 */                                 out.write(32);
/*      */                                 
/* 4245 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4246 */                                 _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 4247 */                                 _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f12);
/* 4248 */                                 int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 4249 */                                 if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                                   for (;;) {
/* 4251 */                                     out.write(10);
/* 4252 */                                     out.write(9);
/* 4253 */                                     out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 4254 */                                     out.write(10);
/* 4255 */                                     out.write(32);
/* 4256 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 4257 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4261 */                                 if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 4262 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                                 }
/*      */                                 
/* 4265 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 4266 */                                 out.write(10);
/* 4267 */                                 out.write(32);
/* 4268 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 4269 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4273 */                             if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 4274 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                             }
/*      */                             
/* 4277 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 4278 */                             out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                             
/* 4280 */                             ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4281 */                             _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 4282 */                             _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_tiles_005fput_005f4);
/* 4283 */                             int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 4284 */                             if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                               for (;;) {
/* 4286 */                                 out.write(10);
/*      */                                 
/* 4288 */                                 WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4289 */                                 _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 4290 */                                 _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                                 
/* 4292 */                                 _jspx_th_c_005fwhen_005f13.setTest("${uri !='/Upload.do'}");
/* 4293 */                                 int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 4294 */                                 if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                                   for (;;) {
/* 4296 */                                     out.write("   \n        ");
/*      */                                     
/* 4298 */                                     AdminLink _jspx_th_am_005fadminlink_005f4 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 4299 */                                     _jspx_th_am_005fadminlink_005f4.setPageContext(_jspx_page_context);
/* 4300 */                                     _jspx_th_am_005fadminlink_005f4.setParent(_jspx_th_c_005fwhen_005f13);
/*      */                                     
/* 4302 */                                     _jspx_th_am_005fadminlink_005f4.setHref("/Upload.do");
/*      */                                     
/* 4304 */                                     _jspx_th_am_005fadminlink_005f4.setEnableClass("new-left-links");
/* 4305 */                                     int _jspx_eval_am_005fadminlink_005f4 = _jspx_th_am_005fadminlink_005f4.doStartTag();
/* 4306 */                                     if (_jspx_eval_am_005fadminlink_005f4 != 0) {
/* 4307 */                                       if (_jspx_eval_am_005fadminlink_005f4 != 1) {
/* 4308 */                                         out = _jspx_page_context.pushBody();
/* 4309 */                                         _jspx_th_am_005fadminlink_005f4.setBodyContent((BodyContent)out);
/* 4310 */                                         _jspx_th_am_005fadminlink_005f4.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 4313 */                                         out.write("\n           ");
/* 4314 */                                         out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 4315 */                                         out.write("\n            ");
/* 4316 */                                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f4.doAfterBody();
/* 4317 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 4320 */                                       if (_jspx_eval_am_005fadminlink_005f4 != 1) {
/* 4321 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 4324 */                                     if (_jspx_th_am_005fadminlink_005f4.doEndTag() == 5) {
/* 4325 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f4); return;
/*      */                                     }
/*      */                                     
/* 4328 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f4);
/* 4329 */                                     out.write(10);
/* 4330 */                                     out.write(10);
/* 4331 */                                     out.write(32);
/* 4332 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 4333 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4337 */                                 if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 4338 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                                 }
/*      */                                 
/* 4341 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 4342 */                                 out.write(10);
/* 4343 */                                 out.write(32);
/*      */                                 
/* 4345 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4346 */                                 _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 4347 */                                 _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f13);
/* 4348 */                                 int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 4349 */                                 if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                                   for (;;) {
/* 4351 */                                     out.write(10);
/* 4352 */                                     out.write(9);
/* 4353 */                                     out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 4354 */                                     out.write(10);
/* 4355 */                                     out.write(32);
/* 4356 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 4357 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4361 */                                 if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 4362 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */                                 }
/*      */                                 
/* 4365 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 4366 */                                 out.write(10);
/* 4367 */                                 out.write(32);
/* 4368 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 4369 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4373 */                             if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 4374 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */                             }
/*      */                             
/* 4377 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 4378 */                             out.write("\n    </td>\n</tr>\n \n ");
/*      */                           }
/*      */                           
/*      */ 
/* 4382 */                           out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                           
/* 4384 */                           ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4385 */                           _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 4386 */                           _jspx_th_c_005fchoose_005f14.setParent(_jspx_th_tiles_005fput_005f4);
/* 4387 */                           int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 4388 */                           if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */                             for (;;) {
/* 4390 */                               out.write(10);
/*      */                               
/* 4392 */                               WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4393 */                               _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 4394 */                               _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */                               
/* 4396 */                               _jspx_th_c_005fwhen_005f14.setTest("${uri !='/admin/userconfiguration.do'}");
/* 4397 */                               int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 4398 */                               if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                                 for (;;) {
/* 4400 */                                   out.write("\n    \n        ");
/*      */                                   
/* 4402 */                                   AdminLink _jspx_th_am_005fadminlink_005f5 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 4403 */                                   _jspx_th_am_005fadminlink_005f5.setPageContext(_jspx_page_context);
/* 4404 */                                   _jspx_th_am_005fadminlink_005f5.setParent(_jspx_th_c_005fwhen_005f14);
/*      */                                   
/* 4406 */                                   _jspx_th_am_005fadminlink_005f5.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                                   
/* 4408 */                                   _jspx_th_am_005fadminlink_005f5.setEnableClass("new-left-links");
/* 4409 */                                   int _jspx_eval_am_005fadminlink_005f5 = _jspx_th_am_005fadminlink_005f5.doStartTag();
/* 4410 */                                   if (_jspx_eval_am_005fadminlink_005f5 != 0) {
/* 4411 */                                     if (_jspx_eval_am_005fadminlink_005f5 != 1) {
/* 4412 */                                       out = _jspx_page_context.pushBody();
/* 4413 */                                       _jspx_th_am_005fadminlink_005f5.setBodyContent((BodyContent)out);
/* 4414 */                                       _jspx_th_am_005fadminlink_005f5.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 4417 */                                       out.write("\n       ");
/* 4418 */                                       out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 4419 */                                       out.write("\n        ");
/* 4420 */                                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f5.doAfterBody();
/* 4421 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 4424 */                                     if (_jspx_eval_am_005fadminlink_005f5 != 1) {
/* 4425 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 4428 */                                   if (_jspx_th_am_005fadminlink_005f5.doEndTag() == 5) {
/* 4429 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f5); return;
/*      */                                   }
/*      */                                   
/* 4432 */                                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f5);
/* 4433 */                                   out.write(10);
/* 4434 */                                   out.write(10);
/* 4435 */                                   out.write(32);
/* 4436 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 4437 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4441 */                               if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 4442 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                               }
/*      */                               
/* 4445 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 4446 */                               out.write(10);
/* 4447 */                               out.write(32);
/*      */                               
/* 4449 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4450 */                               _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 4451 */                               _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f14);
/* 4452 */                               int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 4453 */                               if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */                                 for (;;) {
/* 4455 */                                   out.write(10);
/* 4456 */                                   out.write(9);
/* 4457 */                                   out.write(32);
/* 4458 */                                   out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 4459 */                                   out.write(10);
/* 4460 */                                   out.write(32);
/* 4461 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 4462 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4466 */                               if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 4467 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */                               }
/*      */                               
/* 4470 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 4471 */                               out.write(10);
/* 4472 */                               out.write(32);
/* 4473 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 4474 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4478 */                           if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 4479 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */                           }
/*      */                           
/* 4482 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 4483 */                           out.write("\n    </td>\n</tr>\n   \n\n ");
/* 4484 */                           if (!OEMUtil.isOEM()) {
/* 4485 */                             out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                             
/* 4487 */                             ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4488 */                             _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 4489 */                             _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_tiles_005fput_005f4);
/* 4490 */                             int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 4491 */                             if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                               for (;;) {
/* 4493 */                                 out.write("\n   ");
/*      */                                 
/* 4495 */                                 WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4496 */                                 _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 4497 */                                 _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                                 
/* 4499 */                                 _jspx_th_c_005fwhen_005f15.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 4500 */                                 int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 4501 */                                 if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                                   for (;;) {
/* 4503 */                                     out.write("\n    ");
/*      */                                     
/* 4505 */                                     AdminLink _jspx_th_am_005fadminlink_005f6 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 4506 */                                     _jspx_th_am_005fadminlink_005f6.setPageContext(_jspx_page_context);
/* 4507 */                                     _jspx_th_am_005fadminlink_005f6.setParent(_jspx_th_c_005fwhen_005f15);
/*      */                                     
/* 4509 */                                     _jspx_th_am_005fadminlink_005f6.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                                     
/* 4511 */                                     _jspx_th_am_005fadminlink_005f6.setEnableClass("new-left-links");
/* 4512 */                                     int _jspx_eval_am_005fadminlink_005f6 = _jspx_th_am_005fadminlink_005f6.doStartTag();
/* 4513 */                                     if (_jspx_eval_am_005fadminlink_005f6 != 0) {
/* 4514 */                                       if (_jspx_eval_am_005fadminlink_005f6 != 1) {
/* 4515 */                                         out = _jspx_page_context.pushBody();
/* 4516 */                                         _jspx_th_am_005fadminlink_005f6.setBodyContent((BodyContent)out);
/* 4517 */                                         _jspx_th_am_005fadminlink_005f6.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 4520 */                                         out.write(10);
/* 4521 */                                         out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 4522 */                                         out.write("\n    ");
/* 4523 */                                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f6.doAfterBody();
/* 4524 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 4527 */                                       if (_jspx_eval_am_005fadminlink_005f6 != 1) {
/* 4528 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 4531 */                                     if (_jspx_th_am_005fadminlink_005f6.doEndTag() == 5) {
/* 4532 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f6); return;
/*      */                                     }
/*      */                                     
/* 4535 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f6);
/* 4536 */                                     out.write("\n   ");
/* 4537 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 4538 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4542 */                                 if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 4543 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                                 }
/*      */                                 
/* 4546 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 4547 */                                 out.write("\n   ");
/*      */                                 
/* 4549 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4550 */                                 _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 4551 */                                 _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f15);
/* 4552 */                                 int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 4553 */                                 if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                                   for (;;) {
/* 4555 */                                     out.write(10);
/* 4556 */                                     out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 4557 */                                     out.write("\n   ");
/* 4558 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 4559 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4563 */                                 if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 4564 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */                                 }
/*      */                                 
/* 4567 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 4568 */                                 out.write(10);
/* 4569 */                                 out.write(32);
/* 4570 */                                 out.write(32);
/* 4571 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 4572 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4576 */                             if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 4577 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */                             }
/*      */                             
/* 4580 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 4581 */                             out.write("\n </td>\n</tr>\n  ");
/*      */                           }
/* 4583 */                           out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                           
/* 4585 */                           ChooseTag _jspx_th_c_005fchoose_005f16 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4586 */                           _jspx_th_c_005fchoose_005f16.setPageContext(_jspx_page_context);
/* 4587 */                           _jspx_th_c_005fchoose_005f16.setParent(_jspx_th_tiles_005fput_005f4);
/* 4588 */                           int _jspx_eval_c_005fchoose_005f16 = _jspx_th_c_005fchoose_005f16.doStartTag();
/* 4589 */                           if (_jspx_eval_c_005fchoose_005f16 != 0) {
/*      */                             for (;;) {
/* 4591 */                               out.write("\n   ");
/*      */                               
/* 4593 */                               WhenTag _jspx_th_c_005fwhen_005f16 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4594 */                               _jspx_th_c_005fwhen_005f16.setPageContext(_jspx_page_context);
/* 4595 */                               _jspx_th_c_005fwhen_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/*      */                               
/* 4597 */                               _jspx_th_c_005fwhen_005f16.setTest("${param.method!='showDataCleanUp'}");
/* 4598 */                               int _jspx_eval_c_005fwhen_005f16 = _jspx_th_c_005fwhen_005f16.doStartTag();
/* 4599 */                               if (_jspx_eval_c_005fwhen_005f16 != 0) {
/*      */                                 for (;;) {
/* 4601 */                                   out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 4602 */                                   out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 4603 */                                   out.write("\n    </a>\n   ");
/* 4604 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f16.doAfterBody();
/* 4605 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4609 */                               if (_jspx_th_c_005fwhen_005f16.doEndTag() == 5) {
/* 4610 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16); return;
/*      */                               }
/*      */                               
/* 4613 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 4614 */                               out.write("\n   ");
/*      */                               
/* 4616 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4617 */                               _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 4618 */                               _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f16);
/* 4619 */                               int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 4620 */                               if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                                 for (;;) {
/* 4622 */                                   out.write(10);
/* 4623 */                                   out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 4624 */                                   out.write("\n   ");
/* 4625 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 4626 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4630 */                               if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 4631 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */                               }
/*      */                               
/* 4634 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 4635 */                               out.write(10);
/* 4636 */                               out.write(32);
/* 4637 */                               out.write(32);
/* 4638 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f16.doAfterBody();
/* 4639 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4643 */                           if (_jspx_th_c_005fchoose_005f16.doEndTag() == 5) {
/* 4644 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16); return;
/*      */                           }
/*      */                           
/* 4647 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16);
/* 4648 */                           out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 4649 */                           out.write(10);
/* 4650 */                           int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 4651 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 4654 */                         if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 4655 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 4658 */                       if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4659 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                       }
/*      */                       
/* 4662 */                       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 4663 */                       out.write(10);
/*      */                     }
/* 4665 */                     out.write(10);
/* 4666 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 4667 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 4671 */                 if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 4672 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                 }
/*      */                 
/* 4675 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4676 */                 out.write(10);
/*      */                 
/* 4678 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f16 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4679 */                 _jspx_th_c_005fotherwise_005f16.setPageContext(_jspx_page_context);
/* 4680 */                 _jspx_th_c_005fotherwise_005f16.setParent(_jspx_th_c_005fchoose_005f0);
/* 4681 */                 int _jspx_eval_c_005fotherwise_005f16 = _jspx_th_c_005fotherwise_005f16.doStartTag();
/* 4682 */                 if (_jspx_eval_c_005fotherwise_005f16 != 0) {
/*      */                   for (;;) {
/* 4684 */                     out.write(10);
/* 4685 */                     if (_jspx_meth_tiles_005fput_005f5(_jspx_th_c_005fotherwise_005f16, _jspx_page_context))
/*      */                       return;
/* 4687 */                     out.write(32);
/*      */                     
/* 4689 */                     PutTag _jspx_th_tiles_005fput_005f6 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 4690 */                     _jspx_th_tiles_005fput_005f6.setPageContext(_jspx_page_context);
/* 4691 */                     _jspx_th_tiles_005fput_005f6.setParent(_jspx_th_c_005fotherwise_005f16);
/*      */                     
/* 4693 */                     _jspx_th_tiles_005fput_005f6.setName("LeftArea");
/*      */                     
/* 4695 */                     _jspx_th_tiles_005fput_005f6.setType("string");
/* 4696 */                     int _jspx_eval_tiles_005fput_005f6 = _jspx_th_tiles_005fput_005f6.doStartTag();
/* 4697 */                     if (_jspx_eval_tiles_005fput_005f6 != 0) {
/* 4698 */                       if (_jspx_eval_tiles_005fput_005f6 != 1) {
/* 4699 */                         out = _jspx_page_context.pushBody();
/* 4700 */                         _jspx_th_tiles_005fput_005f6.setBodyContent((BodyContent)out);
/* 4701 */                         _jspx_th_tiles_005fput_005f6.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/* 4704 */                         out.write(10);
/* 4705 */                         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 4706 */                           out.write(10);
/* 4707 */                           out.write("\n<!--$Id$-->\n\n\n\n");
/* 4708 */                           if (_jspx_meth_c_005fif_005f15(_jspx_th_tiles_005fput_005f6, _jspx_page_context))
/*      */                             return;
/* 4710 */                           out.write(10);
/* 4711 */                           if (_jspx_meth_c_005fif_005f16(_jspx_th_tiles_005fput_005f6, _jspx_page_context))
/*      */                             return;
/* 4713 */                           out.write(10);
/* 4714 */                           out.write(10);
/* 4715 */                           out.write(10);
/* 4716 */                           out.write("\n<SCRIPT language=JavaScript1.2 src=\"/template/mm_menu.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\n");
/*      */                           
/*      */ 
/* 4719 */                           String requestpath = "/images/mm_menu2.jsp";
/*      */                           
/* 4721 */                           ArrayList menupos = new ArrayList(5);
/* 4722 */                           if (request.isUserInRole("OPERATOR"))
/*      */                           {
/* 4724 */                             menupos.add("160");
/* 4725 */                             menupos.add("202");
/* 4726 */                             menupos.add("224");
/* 4727 */                             menupos.add("245");
/* 4728 */                             menupos.add("139");
/* 4729 */                             menupos.add("181");
/* 4730 */                             menupos.add("287");
/* 4731 */                             menupos.add("266");
/* 4732 */                             menupos.add("308");
/* 4733 */                             menupos.add("328");
/*      */ 
/*      */ 
/*      */ 
/*      */                           }
/* 4738 */                           else if (OEMUtil.isOEM()) {
/* 4739 */                             menupos.add("160");
/* 4740 */                             menupos.add("205");
/* 4741 */                             menupos.add("220");
/* 4742 */                             menupos.add("240");
/* 4743 */                             menupos.add("140");
/* 4744 */                             menupos.add("180");
/* 4745 */                             menupos.add("265");
/* 4746 */                             menupos.add("285");
/* 4747 */                             menupos.add("305");
/* 4748 */                             menupos.add("325");
/*      */                           } else {
/* 4750 */                             menupos.add("218");
/* 4751 */                             menupos.add("261");
/* 4752 */                             menupos.add("282");
/* 4753 */                             menupos.add("303");
/* 4754 */                             menupos.add("197");
/* 4755 */                             menupos.add("239");
/* 4756 */                             menupos.add("324");
/* 4757 */                             menupos.add("345");
/* 4758 */                             menupos.add("366");
/* 4759 */                             menupos.add("400");
/*      */                           }
/*      */                           
/* 4762 */                           pageContext.setAttribute("menupos", menupos);
/*      */                           
/* 4764 */                           out.write(10);
/* 4765 */                           JspRuntimeLibrary.include(request, response, requestpath, out, false);
/* 4766 */                           out.write("\n</script>\n<SCRIPT language=JavaScript1.2>mmLoadMenus()</SCRIPT>\n\n");
/* 4767 */                           out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n");
/*      */                           
/* 4769 */                           String categorytype = com.adventnet.appmanager.util.Constants.getCategorytype();
/* 4770 */                           pageContext.setAttribute("categorytype", categorytype);
/*      */                           
/* 4772 */                           out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n      <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 4773 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 4774 */                           out.write("</td>\n  </tr>\n\n\n");
/*      */                           
/* 4776 */                           NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4777 */                           _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 4778 */                           _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_tiles_005fput_005f6);
/*      */                           
/* 4780 */                           _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/* 4781 */                           int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 4782 */                           if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                             for (;;) {
/* 4784 */                               out.write(10);
/* 4785 */                               out.write(10);
/*      */                               
/* 4787 */                               IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4788 */                               _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 4789 */                               _jspx_th_c_005fif_005f18.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                               
/* 4791 */                               _jspx_th_c_005fif_005f18.setTest("${categorytype!='DATABASE' && categorytype!='LAMP'}");
/* 4792 */                               int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 4793 */                               if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                                 for (;;) {
/* 4795 */                                   out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=APP\" >");
/* 4796 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver"));
/* 4797 */                                   out.write("</a>\n\n</td>\n</tr>\n");
/* 4798 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 4799 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4803 */                               if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 4804 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                               }
/*      */                               
/* 4807 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 4808 */                               out.write(10);
/*      */                               
/* 4810 */                               IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4811 */                               _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 4812 */                               _jspx_th_c_005fif_005f19.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                               
/* 4814 */                               _jspx_th_c_005fif_005f19.setTest("${categorytype!='DATABASE' && categorytype!='LAMP' && categorytype!='CLOUD'}");
/* 4815 */                               int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 4816 */                               if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                                 for (;;) {
/* 4818 */                                   out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=ERP\" >");
/* 4819 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp"));
/* 4820 */                                   out.write("</a>\n\n</td>\n</tr>\n");
/* 4821 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 4822 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4826 */                               if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 4827 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                               }
/*      */                               
/* 4830 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 4831 */                               out.write(10);
/*      */                               
/* 4833 */                               IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4834 */                               _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 4835 */                               _jspx_th_c_005fif_005f20.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                               
/* 4837 */                               _jspx_th_c_005fif_005f20.setTest("${categorytype!='J2EE'}");
/* 4838 */                               int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 4839 */                               if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */                                 for (;;) {
/* 4841 */                                   out.write("\n<tr  >\n<td  align=\"left\" class=\"leftlinkstd\"><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\">");
/* 4842 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 4843 */                                   out.write("</a>\n\n</td>\n</tr>\n");
/* 4844 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 4845 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4849 */                               if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 4850 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */                               }
/*      */                               
/* 4853 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 4854 */                               out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\">");
/* 4855 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 4856 */                               out.write("</a>\n\n</td>\n</tr>\n");
/*      */                               
/* 4858 */                               IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4859 */                               _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 4860 */                               _jspx_th_c_005fif_005f21.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                               
/* 4862 */                               _jspx_th_c_005fif_005f21.setTest("${categorytype!='DATABASE'}");
/* 4863 */                               int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 4864 */                               if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                                 for (;;) {
/* 4866 */                                   out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MS\" >");
/* 4867 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 4868 */                                   out.write("</a>\n\n</td>\n</tr>\n");
/* 4869 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 4870 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4874 */                               if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 4875 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                               }
/*      */                               
/* 4878 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 4879 */                               out.write("\n<tr >\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\">");
/* 4880 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 4881 */                               out.write("    </a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" >");
/* 4882 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 4883 */                               out.write("</a>\n\n</td>\n</tr>\n\n");
/* 4884 */                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 4885 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4889 */                           if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 4890 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                           }
/*      */                           
/* 4893 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 4894 */                           out.write(10);
/* 4895 */                           out.write(10);
/*      */                           
/* 4897 */                           PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4898 */                           _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 4899 */                           _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_tiles_005fput_005f6);
/*      */                           
/* 4901 */                           _jspx_th_logic_005fpresent_005f6.setRole("OPERATOR");
/* 4902 */                           int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 4903 */                           if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                             for (;;) {
/* 4905 */                               out.write(10);
/* 4906 */                               out.write(10);
/*      */                               
/* 4908 */                               IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4909 */                               _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 4910 */                               _jspx_th_c_005fif_005f22.setParent(_jspx_th_logic_005fpresent_005f6);
/*      */                               
/* 4912 */                               _jspx_th_c_005fif_005f22.setTest("${categorytype!='DATABASE' && categorytype!='LAMP'}");
/* 4913 */                               int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 4914 */                               if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                                 for (;;) {
/* 4916 */                                   out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=APP\" >");
/* 4917 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver"));
/* 4918 */                                   out.write("</a>\n\n</td>\n</tr>\n");
/* 4919 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 4920 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4924 */                               if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 4925 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */                               }
/*      */                               
/* 4928 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 4929 */                               out.write(10);
/*      */                               
/* 4931 */                               IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4932 */                               _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 4933 */                               _jspx_th_c_005fif_005f23.setParent(_jspx_th_logic_005fpresent_005f6);
/*      */                               
/* 4935 */                               _jspx_th_c_005fif_005f23.setTest("${categorytype!='DATABASE' && categorytype!='LAMP' && categorytype!='CLOUD'}");
/* 4936 */                               int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 4937 */                               if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */                                 for (;;) {
/* 4939 */                                   out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=ERP\" >");
/* 4940 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp"));
/* 4941 */                                   out.write("</a>\n\n</td>\n</tr>\n");
/* 4942 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 4943 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4947 */                               if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 4948 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*      */                               }
/*      */                               
/* 4951 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 4952 */                               out.write("\n\n\n<tr  >\n<td  align=\"left\" class=\"leftlinkstd\"><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\">");
/* 4953 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 4954 */                               out.write("</a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\">");
/* 4955 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 4956 */                               out.write("</a>\n\n</td>\n</tr>\n\n");
/*      */                               
/* 4958 */                               IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4959 */                               _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 4960 */                               _jspx_th_c_005fif_005f24.setParent(_jspx_th_logic_005fpresent_005f6);
/*      */                               
/* 4962 */                               _jspx_th_c_005fif_005f24.setTest("${categorytype!='DATABASE'}");
/* 4963 */                               int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 4964 */                               if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */                                 for (;;) {
/* 4966 */                                   out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MS\" >");
/* 4967 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 4968 */                                   out.write("</a>\n\n</td>\n</tr>\n");
/* 4969 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 4970 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4974 */                               if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 4975 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24); return;
/*      */                               }
/*      */                               
/* 4978 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 4979 */                               out.write("\n<tr >\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\">");
/* 4980 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 4981 */                               out.write(" </a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" >");
/* 4982 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 4983 */                               out.write("</a>\n\n</td>\n</tr>\n");
/* 4984 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 4985 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4989 */                           if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 4990 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                           }
/*      */                           
/* 4993 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 4994 */                           out.write("\n\n  <tr >\n  <td class=\"leftlinkstd\"><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=CAM&network=Custom-Application\"   class=\"new-left-links\">");
/* 4995 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 4996 */                           out.write("</a></td>\n\n</tr>\n");
/*      */                           
/* 4998 */                           IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4999 */                           _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 5000 */                           _jspx_th_c_005fif_005f25.setParent(_jspx_th_tiles_005fput_005f6);
/*      */                           
/* 5002 */                           _jspx_th_c_005fif_005f25.setTest("${categorytype!='CLOUD'}");
/* 5003 */                           int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 5004 */                           if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */                             for (;;) {
/* 5006 */                               out.write("\n<tr >\n  <td class=\"leftlinkstd\"><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MOM\"   class=\"new-left-links\">");
/* 5007 */                               out.print(FormatUtil.getString("Middleware/Portal"));
/* 5008 */                               out.write("</a></td>\n \n</tr>\n");
/* 5009 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 5010 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 5014 */                           if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 5015 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*      */                           }
/*      */                           
/* 5018 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 5019 */                           out.write(10);
/*      */                           
/* 5021 */                           String[] categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 5022 */                           for (int i = 0; i < categoryLink.length; i++)
/*      */                           {
/* 5024 */                             if ((!categoryLink[i].equals("APP")) && (!categoryLink[i].equals("TM")) && (!categoryLink[i].equals("ERP")) && (!categoryLink[i].equals("DBS")) && (!categoryLink[i].equals("SYS")) && (!categoryLink[i].equals("MS")) && (!categoryLink[i].equals("SCR")) && (!categoryLink[i].equals("NWD")) && (!categoryLink[i].equals("SER")) && (!categoryLink[i].equals("URL")) && (!categoryLink[i].equals("CAM")) && (!categoryLink[i].equals("MOM")) && (!categoryLink[i].equals("SAN")) && (!categoryLink[i].equals("EMO")))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5033 */                               out.write("\n<tr>\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href='");
/* 5034 */                               out.print("/showresource.do?method=showResourceTypes&detailspage=true&group=" + categoryLink[i]);
/* 5035 */                               out.write(39);
/* 5036 */                               out.write(62);
/* 5037 */                               out.print(FormatUtil.getString(com.adventnet.appmanager.util.Constants.categoryTitle[i]));
/* 5038 */                               out.write("</a>\n</td>\n</tr>\n");
/*      */                             }
/*      */                           }
/*      */                           
/* 5042 */                           out.write("\n\n</table>\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 5043 */                           out.write(10);
/* 5044 */                           response.setContentType("text/html;charset=UTF-8");
/* 5045 */                           out.write(10);
/* 5046 */                           out.write(10);
/* 5047 */                           out.write(10);
/*      */                         }
/* 5049 */                         out.write(10);
/* 5050 */                         int evalDoAfterBody = _jspx_th_tiles_005fput_005f6.doAfterBody();
/* 5051 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 5054 */                       if (_jspx_eval_tiles_005fput_005f6 != 1) {
/* 5055 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 5058 */                     if (_jspx_th_tiles_005fput_005f6.doEndTag() == 5) {
/* 5059 */                       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f6); return;
/*      */                     }
/*      */                     
/* 5062 */                     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f6);
/* 5063 */                     out.write(10);
/* 5064 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f16.doAfterBody();
/* 5065 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 5069 */                 if (_jspx_th_c_005fotherwise_005f16.doEndTag() == 5) {
/* 5070 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16); return;
/*      */                 }
/*      */                 
/* 5073 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16);
/* 5074 */                 out.write(10);
/* 5075 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 5076 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 5080 */             if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 5081 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */             }
/*      */             
/* 5084 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5085 */             out.write(10);
/*      */             
/* 5087 */             PutTag _jspx_th_tiles_005fput_005f7 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 5088 */             _jspx_th_tiles_005fput_005f7.setPageContext(_jspx_page_context);
/* 5089 */             _jspx_th_tiles_005fput_005f7.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/* 5091 */             _jspx_th_tiles_005fput_005f7.setName("UserArea");
/*      */             
/* 5093 */             _jspx_th_tiles_005fput_005f7.setType("string");
/* 5094 */             int _jspx_eval_tiles_005fput_005f7 = _jspx_th_tiles_005fput_005f7.doStartTag();
/* 5095 */             if (_jspx_eval_tiles_005fput_005f7 != 0) {
/* 5096 */               if (_jspx_eval_tiles_005fput_005f7 != 1) {
/* 5097 */                 out = _jspx_page_context.pushBody();
/* 5098 */                 _jspx_th_tiles_005fput_005f7.setBodyContent((BodyContent)out);
/* 5099 */                 _jspx_th_tiles_005fput_005f7.doInitBody();
/*      */               }
/*      */               for (;;) {
/* 5102 */                 out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/chosen.jquery.min.js\"></SCRIPT>\n\n\n");
/*      */                 
/*      */ 
/* 5105 */                 ArrayList dynamicSites = com.adventnet.appmanager.struts.beans.AlarmUtil.getSiteMonitorGroups();
/* 5106 */                 if (dynamicSites != null)
/*      */                 {
/* 5108 */                   request.setAttribute("dynamicSites", dynamicSites);
/*      */                 }
/*      */                 
/* 5111 */                 out.write("\n<script>\njQuery(document).ready(function(){\n\t\tsortSelectItemsAndRetainFirstElement(\"montype\",\"");
/* 5112 */                 out.print(FormatUtil.getString("am.webclient.selectmonitor.alert.text"));
/* 5113 */                 out.write("\");// NO I18N\t\n\t\tsortSelectItemsAndRetainFirstElement(\"resourceid\",\"");
/* 5114 */                 out.print(FormatUtil.getString("am.webclient.configurealert.selectmonitor"));
/* 5115 */                 out.write("\");// NO I18N\t\n\t//\tsortSelectItemsAndRetainFirstElement(\"haid\",\"");
/* 5116 */                 out.print(FormatUtil.getString("am.webclient.configurealert.selectmonitorgroup"));
/* 5117 */                 out.write("\");// NO I18N\t\n        $('.chzn-select').chosen();\n        $('.chzn-select1').chosen(\n        \t\t{\n        \t\t      placeholder_text_single: '");
/* 5118 */                 out.print(FormatUtil.getString("am.webclient.configurealert.selectmonitor"));
/* 5119 */                 out.write("',no_results_text: '");
/* 5120 */                 out.print(FormatUtil.getString("am.webclient.choosen.nodata"));
/* 5121 */                 out.write("'// NO I18N\n        \t\t}\n        );\n});\n\nfunction invokePage(resourceid, url, prop1, prop2)\n{\n\tif(resourceid == 0)\n\t{\n\t\talert('");
/* 5122 */                 out.print(FormatUtil.getString("am.webclient.configurealert.selectmonitor.alertMgs"));
/* 5123 */                 out.write("');\n\t\treturn false;\n\t}\n\tMM_openBrWindow(url, prop1, prop2);\n}\n\nfunction loadSite(object)\n{\n\tdocument.AMActionForm.siteName.options.length=0; // Necessary to reset the select box before loading the sites\n\n\tvar formCustomerId = document.AMActionForm.organization.value;\n//\talert(formCustomerId);\n\tvar site;\n\tvar siteId;\n\tvar customerId;\n\tvar rowCount=0;\n\tdocument.AMActionForm.siteName.options[rowCount++] = new Option('");
/* 5124 */                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.siteGroup"));
/* 5125 */                 out.write("','-'); //No I18N\n\t");
/* 5126 */                 if (_jspx_meth_c_005fforEach_005f0(_jspx_th_tiles_005fput_005f7, _jspx_page_context))
/*      */                   return;
/* 5128 */                 out.write("\n\t\tloadTypes();\n}\n\n\n\nfunction showAdvancedOptions(a,b)\n{\n\tif(a.checked && b=='true')\n\t{\n\t\tshowDiv('advanceOptionstrue');\n\t}\n\telse if(b=='true')\n\t{\n\t\thideDiv('advanceOptionstrue');\n\t}\n\telse if(a.checked && b=='false')\n\t{\n\t\tshowDiv('advanceOptionsfalse');\n\t}\n\telse\n\t{\n\t\thideDiv('advanceOptionsfalse');\n\t}\n}\nfunction selectAnyRadioButton(a)\n{\n   a.checked=true;\n}\nfunction onfnSubmit(object)\n{\n\tvar selectedValue = jQuery(\"input[name='viewBy']:checked\").val()\t\t// NO I18N \n\t\tif(selectedValue == 'monitorType')\n\t\t{\n\t\t\tdocument.AMActionForm.resourceid.value=0;\n\t\t}\n\t\n\tobject.form.submit();\n}\nfunction setDefaultType(object){\n\n\tif(document.AMActionForm.montype.value){\n\t\tvar element=document.getElementsByName(\"montype\");\t\t\n\t\tvar exists = false; \n\t\t$(element).html($(\"option\").each(function(){\n\t\t\t\t if (this.value == \"ActiveDirectory\") {\n\t\t\t\t    exists = true;\n\t\t\t\t  }\n\t\t\t}));\n\t\t\tif(!exists){\n\t\t\t\t var newOption = $('<option value=\"ActiveDirectory\">Active Directory</option>');//No I18N\n\t\t\t\t $(element).append(newOption);\n\t\t\t}\n\t\t\tdocument.AMActionForm.montype.value=\"ActiveDirectory\";//No i18N\n");
/* 5129 */                 out.write("\t\t}\t\n\t\n\t\n\tonfnSubmit(object);\n}\nfunction selectTypeAttribute(value){\n\t\n\tvar haid = jQuery(\"select[name='haid']\").val()\t\t// NO I18N \n\tjQuery.ajax({\n\t\turl : '/showActionProfiles.do?method=getResourceProfiles&include=true&groupTemplate=true&resourceid=0&viewBy=monitorType&haidValue='+haid+'&montype='+value,\t\t// NO I18N \n\t\tsuccess : function(data){\n\t\t\t\n\t\t\tjQuery(\"#thresholdsInGroup\").html(data)\t\t// NO I18N \n\t\t}\n\t})\n}\n\n\n\nfunction myOnLoad(object)\n{\n\tvar selectedValue = jQuery(\"input[name='viewBy']:checked\").val()\t\t// NO I18N \n\t\n\t");
/* 5130 */                 if (_jspx_meth_c_005fchoose_005f17(_jspx_th_tiles_005fput_005f7, _jspx_page_context))
/*      */                   return;
/* 5132 */                 out.write("\n\t\n  if(selectedValue == 'monitorGroups')\n  {\n\t  jQuery(\"input[name='viewBy'][value='monitorGroups']\").attr(\"checked\", true); \t\t// NO I18N\n\t  document.getElementById(\"view_monitors\").style.display=\"none\";\n\t  document.getElementById(\"view_monitors1\").style.display=\"none\";\n\t  document.getElementById(\"viewmonitorgroup\").style.display=\"\";\n\t  var monType = jQuery(\"select[name='mgTemplateType']\").val()\t\t// NO I18N\n\t  selectTypeAttribute(monType)\n\t  if( document.AMActionForm.siteName!=null)\n \t  {\t  \n\t  \tdocument.AMActionForm.siteName.disabled=true;\t\n\t\tdocument.AMActionForm.siteName.selectedIndex=0\t\n\t  }\n  }\n  else if(selectedValue == 'monitorType')\n  {\n\t  jQuery(\"input[name='viewBy'][value='monitorType']\").attr(\"checked\", true); \t\t// NO I18N\n\t  document.getElementById(\"viewmonitorgroup\").style.display=\"none\";\n\t  document.getElementById(\"view_monitors\").style.display=\"\";\n\t  document.getElementById(\"view_monitors1\").style.display=\"none\";\n\t  if( document.AMActionForm.organization!=null)\n \t  {\t  \n\t \t document.AMActionForm.organization.disabled=true;\n");
/* 5133 */                 out.write("\t \tdocument.AMActionForm.organization.selectedIndex=0;\n\t  }\n\t  if( document.AMActionForm.siteName!=null)\n \t  {\t  \n\t  \tdocument.AMActionForm.siteName.disabled=true;\t\n\t\tdocument.AMActionForm.siteName.selectedIndex=0;\t\n\t  }\t\t\n\t  \n  }\n  else\n  {\n\t  jQuery(\"input[name='viewBy'][value='monitors']\").attr(\"checked\", true); \t\t// NO I18N\n\t  document.getElementById(\"viewmonitorgroup\").style.display=\"none\";\n\t  if(");
/* 5134 */                 out.print(showMonType);
/* 5135 */                 out.write(") {\n\t  \tdocument.getElementById(\"view_monitors\").style.display=\"\";\n\t  }\n\t  document.getElementById(\"view_monitors1\").style.display=\"\";\n  }\n \n}\n\nfunction showMonDiv(stat)\n{\n  if(stat=='true')\n  {\n\t  document.getElementById(\"view_monitors\").style.display=\"\";\n\t  document.getElementById(\"view_monitors1\").style.display=\"\";\n\t  document.getElementById(\"viewmonitorgroup\").style.display=\"none\";\n  }\n  else\n  {\n\t  document.getElementById(\"view_monitors\").style.display=\"none\";\n\t  document.getElementById(\"view_monitors1\").style.display=\"none\";\n\t  document.getElementById(\"viewmonitorgroup\").style.display=\"\";\n  } \n}\n\n\t \nfunction loadMonitor()\n{\n\n       //obj = getHTTPObject();\n        obj = GetXmlHttpObjectA();\n       if(");
/* 5136 */                 out.print(isMSPEdition);
/* 5137 */                 out.write(")\n\t   \t{\n        \t\tvar url = '/showActionProfiles.do?organization='+document.AMActionForm.organization.value+'&method=getResourceProfiles&montype='+document.AMActionForm.montype.value+'&siteName='+document.AMActionForm.siteName.value+'&reqID=getFilteredMonitors'; //No I18N\n       \t}\n    \telse\n        {\n        \t\tvar url = '/showActionProfiles.do?method=getResourceProfiles&montype='+document.AMActionForm.montype.value+'&reqID=getFilteredMonitors'; //No I18N\n        }\n\t\tobj.onreadystatechange = handleResponseForMonitor;\n        obj.open(\"GET\",url,true);\n        obj.send(null);\n}\n//In Firefox, if the size of a nodeValue exceeds 4k, the resultant is truncated.\n//The following method ensures that the entire content is obtained in both IE and Firefox\nfunction getNodeText(xmlNode) {\n    if(typeof(xmlNode.textContent) != \"undefined\") \n\t{\n\t\treturn xmlNode.textContent;\n\t}\n    else\n\t{\n\t\treturn xmlNode.firstChild.data;\n\t}\n}\n\nfunction handleResponseForMonitor()\n{\n\tif(obj.readyState == 4)\n    {\n\t\t//var result = obj.responseText;\n");
/* 5138 */                 out.write("\t\tresponse= obj.responseXML.documentElement;\n\t\t//response= obj.responseXML; //This also will work\n\t\tval =response.getElementsByTagName(\"monValue\");\n\t\tlabel =response.getElementsByTagName(\"monLabel\");\t\n\t\tallMonitorsId=getNodeText(val[0]);\n\t\tallMonitorsName=getNodeText(label[0]);\n\t\t\n\n\n\n\t\tresIdBox=document.AMActionForm.resourceid;\n\n\n\t\tresIdBox.innerHTML=\"\";\n\n\t\tresIdArray = new Array();\n\t\tresIdArray = allMonitorsId.split(\",\");\n\t\tresIdArrayLength = resIdArray.length;\n\n\t\tresNameArray = new Array();\n\t\tresNameArray = allMonitorsName.split(\",\");\n\t\tresNameArrayLength = resNameArray.length;\n\n\n\t\tvar teobj = document.createElement(\"OPTION\");\n\t\tteobj.value =0;\n\t\tteobj.text='");
/* 5139 */                 out.print(FormatUtil.getString("am.webclient.configurealert.selectmonitor"));
/* 5140 */                 out.write("'; //No I18N\n\t//\tresIdBox.appendChild(teobj); //commented this and added using options[]. Since it works in Firefox but not in IE\n                resIdBox.options[0] = teobj;    \n\t        \n\t\tfor(var i=0;i<resIdArrayLength;i++)\n\t\t{\n\t\tteobj = document.createElement(\"OPTION\");\n\t\t//teobj.innerHTML=resIdArray[i];\n\t\tteobj.value =resIdArray[i];\n\t\tteobj.text=resNameArray[i];\n\t\t//resIdBox.appendChild(teobj); //commented this and added using options[]. Since it works in Firefox but not in IE\n\t\tresIdBox.options[i+1] = teobj;    \n                     \n\t\t}\n\t\t\n\t\t$(\".chzn-select1\").val('').trigger(\"liszt:updated\");//No I18N\n\t \n     }\n}\n\n\nfunction loadTypes()\n{\n\n        //obj = getHTTPObject();\n        obj = GetXmlHttpObjectA();\n        var url = '/showActionProfiles.do?organization='+document.AMActionForm.organization.value+'&method=getResourceProfiles&siteName='+document.AMActionForm.siteName.value+'&reqID=getFilteredTypes'; //No I18N\n        obj.open(\"GET\",url,true);\n        obj.onreadystatechange = handleResponseForType;\n");
/* 5141 */                 out.write("\t\tobj.send(null);\n}\n\nfunction handleResponseForType()\n{\n        if(obj.readyState == 4)\n        {\n\t\t//var result = obj.responseText;\n\t\t//alert(result);\n\t\tresponse= obj.responseXML.documentElement;\n\t\t//response= obj.responseXML; //This also will work\n\t\tval =response.getElementsByTagName(\"monTypeValue\");\n\t\tvar label =response.getElementsByTagName(\"monTypeLabel\");\n\t\t\n\t\tif(val[0].childNodes.length > 0 && label[0].childNodes.length > 0)\n\t\t{\n\t\t\tallMonitorsId=val[0].firstChild.data;\n\t\t\tallMonitorsName=label[0].firstChild.data;\n\t\t\t\n\t\t\t\n\t\t\tresIdBox=document.AMActionForm.montype;\n\t\n\t\n\t\t\tresIdBox.innerHTML=\"\";\n\t\n\t\t\tresIdArray = new Array();\n\t\t\tresIdArray = allMonitorsId.split(\",\");\n\t\t\tresIdArrayLength = resIdArray.length;\n\t\n\t\t\tresNameArray = new Array();\n\t\t\tresNameArray = allMonitorsName.split(\",\");\n\t\t\tresNameArrayLength = resNameArray.length;\n\t\n\t\t\tvar teobj = document.createElement(\"OPTION\");\n\t\t\tteobj.value =0;\n\t\t\tteobj.text='");
/* 5142 */                 out.print(FormatUtil.getString("Select a Resource Type"));
/* 5143 */                 out.write("'; //No I18N\n\t\t\t//\tresIdBox.appendChild(teobj); //commented this and added using options[]. Since it works in Firefox but not in IE\n\t                resIdBox.options[0] = teobj;   \n\t\t\tfor(var i=0;i<resIdArrayLength;i++)\n\t\t\t{\n\t\t\t\tteobj = document.createElement(\"OPTION\");\n\t\t\t\t//teobj.innerHTML=resIdArray[i];\n\t\t\t\tteobj.value =resIdArray[i];\n\t\t\t\tvar devTypeLabel = resNameArray[i];\n\t\t\t\tif(devTypeLabel != null && (devTypeLabel.indexOf(\"OpManager-\") >= 0))\n\t\t\t\t{ \n\t\t\t\t\tdevTypeLabel = devTypeLabel.substring(10);\n\t\t\t\t\tif(devTypeLabel.indexOf(\"-1\") >=0)\n\t\t\t\t\t{\n\t\t\t\t\t\tvar length = devTypeLabel.length-2;\n\t\t\t\t\t\tdevTypeLabel = devTypeLabel.substring(0,length);\n\t\t\t\t\t}\n\t\t\t\t\tteobj.text=devTypeLabel;\t\t\n\t\t\t\t}\t\n\t\t\t\telse{\n\t\t\t\t\tteobj.text=resNameArray[i];\t\n\t\t\t\t}\t\n\t\t\t\t//resIdBox.appendChild(teobj); //commented this and added using options[]. Since it works in Firefox but not in IE\n\t\t\t\tresIdBox.options[i+1] = teobj;    \t\n\t\t\t}\n\t\t\tloadMonitor();\n\t\t\tdocument.AMActionForm.viewBy[0].checked=true;\n\t\t\tdocument.AMActionForm.siteName.disabled=false;\n");
/* 5144 */                 out.write("\t\t\tdocument.getElementById(\"viewmonitorgroup\").style.display=\"none\";\n\t\t\tdocument.getElementById(\"view_monitors\").style.display=\"\";\n\t\t\tdocument.getElementById(\"view_monitors1\").style.display=\"\";\n\t\n\t}\n\telse\n\t{\n\t\talert(\"No monitors added for this customer\"); //No I18N\n\t\tdocument.getElementById(\"viewmonitorgroup\").style.display=\"none\";\n\t\tdocument.getElementById(\"view_monitors\").style.display=\"none\";\n\t\tdocument.getElementById(\"view_monitors1\").style.display=\"none\";\n\t}\n    }\n}\n\nfunction GetXmlHttpObjectA()\n{ \n\tvar objXMLHttp=null;\n\n\tif (window.ActiveXObject)\n\t{\n\t\ttry \n\t\t{\n\t\t\tobjXMLHttp = new ActiveXObject(\"Msxml2.XMLHTTP\");\n\t    }\n\t\tcatch (e)\n\t\t{\n\t\t\ttry {\n\t\t\t\tobjXMLHttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n\t\t\t    } \n\t\t\tcatch (e) \n\t\t\t{}\n\t\t}\t\n\t}    \n\telse if (XMLHttpRequest )\n\t{\n\t\ttry\n\t\t{\n\t\t\tobjXMLHttp=new XMLHttpRequest();\n\t\t\tif (objXMLHttp.overrideMimeType)\n\t       \t{\n\t\t\t\t// set type accordingly to anticipated content type\n\t\t\t\tobjXMLHttp.overrideMimeType('text/xml'); //No I18N\n\t\t\t}\n\t\t}\n\t\tcatch(e)\n     \t{\n\t\t//\talert(e);\n");
/* 5145 */                 out.write("\t\t}\n\tobjXMLHttp.overrideMimeType('text/xml'); //No I18N\n\t}\n\nreturn objXMLHttp;\n\n}\n\nfunction mgactionTemplate(value){\n\tif(value == 'template'){\n\t\tjQuery(\"#thresholdsInGroup\").show();\t\t// NO I18N\n\t\tjQuery(\"#monitorsInGroup\").hide();\t\t// NO I18N\n\t\tjQuery(\"#mgtemplate_monitortype\").show();\t\t// NO I18N\n\t\t\n\t}else{\n\t\tjQuery(\"#monitorsInGroup\").show();\t\t// NO I18N\n\t\tjQuery(\"#thresholdsInGroup\").hide();\t\t// NO I18N\n\t\tjQuery(\"#mgtemplate_monitortype\").hide();\t\t// NO I18N\n\t}\n}\n</script>\n\n\n");
/*      */                 
/* 5147 */                 FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 5148 */                 _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 5149 */                 _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f7);
/*      */                 
/* 5151 */                 _jspx_th_html_005fform_005f0.setAction("/showActionProfiles");
/* 5152 */                 int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 5153 */                 if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                   for (;;) {
/* 5155 */                     out.write(10);
/* 5156 */                     out.write(10);
/* 5157 */                     out.write(10);
/*      */                     
/* 5159 */                     IfTag _jspx_th_c_005fif_005f29 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5160 */                     _jspx_th_c_005fif_005f29.setPageContext(_jspx_page_context);
/* 5161 */                     _jspx_th_c_005fif_005f29.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 5163 */                     _jspx_th_c_005fif_005f29.setTest("${!empty param.all}");
/* 5164 */                     int _jspx_eval_c_005fif_005f29 = _jspx_th_c_005fif_005f29.doStartTag();
/* 5165 */                     if (_jspx_eval_c_005fif_005f29 != 0) {
/*      */                       for (;;) {
/* 5167 */                         out.write("\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n<td class=\"bcsign breadcrumb_btm_space\" height=\"22\" align=\"left\"> <span class=\"bcactivebig\">");
/* 5168 */                         out.print(FormatUtil.getString("am.webclient.configurealert.configurealerts"));
/* 5169 */                         out.write("</span></td>\n<td class=\"bodytextbold\" align=\"right\">\n\n \n");
/* 5170 */                         if (DBUtil.isSite24x7Monitor(request.getParameter("resourceid"))) {
/* 5171 */                           out.write("\n<a href=\"/showresource.do?method=showResourceTypes&group=EMO&detailspage=true\" class=\"widget-links\">\n");
/* 5172 */                         } else if ((extDeviceMap != null) && (extDeviceMap.get(request.getParameter("resourceid")) != null)) {
/* 5173 */                           out.write("\n<a href=\"");
/* 5174 */                           out.print(extDeviceMap.get(request.getParameter("resourceid")));
/* 5175 */                           out.write("\" class=\"widget-links\">\n");
/*      */                         } else {
/* 5177 */                           String moLink = "/showresource.do?method=showResourceForResourceID&resourceid=" + request.getParameter("resourceid");
/* 5178 */                           String monitorType = DBUtil.getResourceType(request.getParameter("resourceid"));
/* 5179 */                           if ((monitorType != null) && (com.adventnet.appmanager.util.ChildMOHandler.isChildMonitorTypeSupportedForMG(monitorType)))
/*      */                           {
/* 5181 */                             String resId = request.getParameter("resourceid");
/* 5182 */                             Vector resList = new Vector();
/* 5183 */                             resList.add(resId);
/* 5184 */                             HashMap<String, HashMap<String, String>> parentInfo = com.adventnet.appmanager.util.ChildMOHandler.getChildMonitorWithParentInfo(resList);
/* 5185 */                             if ((parentInfo != null) && (parentInfo.containsKey(resId)))
/*      */                             {
/* 5187 */                               HashMap<String, String> monitorInfo = (HashMap)parentInfo.get(resId);
/* 5188 */                               String parentId = (String)monitorInfo.get("parentResourceid");
/* 5189 */                               moLink = "/showresource.do?method=showResourceForResourceID&resourceid=" + parentId;
/*      */                             }
/*      */                           }
/*      */                           
/* 5193 */                           out.write("\n<a href=\"");
/* 5194 */                           out.print(moLink);
/* 5195 */                           out.write("\" class=\"widget-links staticlinks\">\n");
/*      */                         }
/* 5197 */                         out.write(10);
/* 5198 */                         out.write(10);
/* 5199 */                         out.print(FormatUtil.getString("am.webclient.configurealert.backtodetails"));
/* 5200 */                         out.write("</a>\n</td>\n</tr>\n</table>\n");
/* 5201 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f29.doAfterBody();
/* 5202 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 5206 */                     if (_jspx_th_c_005fif_005f29.doEndTag() == 5) {
/* 5207 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29); return;
/*      */                     }
/*      */                     
/* 5210 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 5211 */                     out.write(10);
/*      */                     
/* 5213 */                     IfTag _jspx_th_c_005fif_005f30 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5214 */                     _jspx_th_c_005fif_005f30.setPageContext(_jspx_page_context);
/* 5215 */                     _jspx_th_c_005fif_005f30.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 5217 */                     _jspx_th_c_005fif_005f30.setTest("${empty param.all}");
/* 5218 */                     int _jspx_eval_c_005fif_005f30 = _jspx_th_c_005fif_005f30.doStartTag();
/* 5219 */                     if (_jspx_eval_c_005fif_005f30 != 0) {
/*      */                       for (;;) {
/* 5221 */                         out.write(10);
/* 5222 */                         out.write(32);
/* 5223 */                         out.write(32);
/* 5224 */                         if (admin) {
/* 5225 */                           out.write("\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td class=\"bcsign\"  height=\"22\" valign=\"top\"> \n    ");
/* 5226 */                           if (EnterpriseUtil.isAdminServer()) {
/* 5227 */                             out.write("\n\t\t ");
/* 5228 */                             out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getEnterpriseAdminPage(request));
/* 5229 */                             out.write(10);
/* 5230 */                             out.write(9);
/*      */                           } else {
/* 5232 */                             out.write("\n\t \t ");
/* 5233 */                             out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 5234 */                             out.write(32);
/* 5235 */                             out.write(10);
/* 5236 */                             out.write(9);
/*      */                           }
/* 5238 */                           out.write("\n      &gt;<span class=\"bcactive\"> ");
/* 5239 */                           out.print(FormatUtil.getString("am.webclient.configurealert.configurealerts"));
/* 5240 */                           out.write("</span></td>\n  </tr>\n</table>\n  ");
/*      */                         }
/* 5242 */                         out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"padd-bottom\">\n  ");
/* 5243 */                         if (!admin) {
/* 5244 */                           out.write("\n<tr>\n<td class=\"bcsign breadcrumb_btm_space\" height=\"22\" align=\"left\">");
/* 5245 */                           if (modisplayname != null) {
/* 5246 */                             out.write("<a href='/showapplication.do?method=showApplication&haid=");
/* 5247 */                             if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fif_005f30, _jspx_page_context))
/*      */                               return;
/* 5249 */                             out.write("' class=\"bcinactive\" >");
/* 5250 */                             out.print(modisplayname);
/* 5251 */                             out.write("</a> <span class=\"bcsign\">&gt;</span>");
/*      */                           }
/* 5253 */                           out.write(" <span class=\"bcactivebig\">");
/* 5254 */                           out.print(FormatUtil.getString("am.webclient.configurealert.configurealerts"));
/* 5255 */                           out.write("</span></td>\n<td class=\"bodytextbold\" align=\"right\"><a href=\"/showapplication.do?method=showApplication&haid=");
/* 5256 */                           if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fif_005f30, _jspx_page_context))
/*      */                             return;
/* 5258 */                           out.write("\" class=\"staticlinks\">");
/* 5259 */                           out.print(FormatUtil.getString("am.webclient.configurealert.backtodetails"));
/* 5260 */                           out.write("</a>\n</td>\n</tr>\n\n");
/*      */                         }
/* 5262 */                         out.write(10);
/* 5263 */                         out.write(32);
/* 5264 */                         out.write(32);
/*      */                         
/* 5266 */                         IfTag _jspx_th_c_005fif_005f31 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5267 */                         _jspx_th_c_005fif_005f31.setPageContext(_jspx_page_context);
/* 5268 */                         _jspx_th_c_005fif_005f31.setParent(_jspx_th_c_005fif_005f30);
/*      */                         
/* 5270 */                         _jspx_th_c_005fif_005f31.setTest("${empty param.wiz}");
/* 5271 */                         int _jspx_eval_c_005fif_005f31 = _jspx_th_c_005fif_005f31.doStartTag();
/* 5272 */                         if (_jspx_eval_c_005fif_005f31 != 0) {
/*      */                           for (;;) {
/* 5274 */                             out.write("\n  <tr>\n      ");
/* 5275 */                             if (admin) {
/* 5276 */                               out.write("\n    <td width=\"100%\" class=\"bodytext\">\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrtborder itadmin-hide\">\n<tr>\n<td class=\"tableheading-monitor-config \"><img class=\"tableheading-add-icon\" src=\"../images/trap.png\"/>\n    <span style=\"position:relative; left:5px;\">");
/* 5277 */                               out.print(FormatUtil.getString("am.webclient.configurealert.configurealerts"));
/* 5278 */                               out.write("</span></td>\n");
/* 5279 */                               if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 5280 */                                 out.write("\n<td class=\"tableheading-monitor-config\" align=\"right\" style=\"padding-right:10px;\"><span class=\"bodytext\"><a class=\"staticlinks-red\" target=\"_blank\" href='");
/* 5281 */                                 out.print(FormatUtil.getString("am.webclient.configurealert.stepstoconfigure.link"));
/* 5282 */                                 out.write(39);
/* 5283 */                                 out.write(62);
/* 5284 */                                 out.print(FormatUtil.getString("am.webclient.configurealert.stepstoconfigure.text"));
/* 5285 */                                 out.write("</a><span></td>\n");
/*      */                               }
/* 5287 */                               out.write("\n</tr>\n</table>\n\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"6\" class=\"lrbborder itadmin-no-decor\">\n\n    ");
/* 5288 */                               if (EnterpriseUtil.isIt360MSPEdition()) {
/* 5289 */                                 out.write(" \n  <tr>\n    <td width=\"25%\" height=\"28\" valign=\"middle\" class=\"bodytext label-align\">");
/* 5290 */                                 out.print(FormatUtil.getString("it360.sp.customermgmt.customer.txt", new String[] { MGSTR }));
/* 5291 */                                 out.write("\n    </td>\n    <td width=\"75%\" height=\"28\" >\n\n\t    \n");
/*      */                                 
/* 5293 */                                 SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5294 */                                 _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 5295 */                                 _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fif_005f31);
/*      */                                 
/* 5297 */                                 _jspx_th_html_005fselect_005f0.setProperty("organization");
/*      */                                 
/* 5299 */                                 _jspx_th_html_005fselect_005f0.setStyleClass("formtext normal");
/*      */                                 
/* 5301 */                                 _jspx_th_html_005fselect_005f0.setOnchange("javascript:loadSite(this)");
/* 5302 */                                 int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 5303 */                                 if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 5304 */                                   if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 5305 */                                     out = _jspx_page_context.pushBody();
/* 5306 */                                     _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 5307 */                                     _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 5310 */                                     out.write("\n      ");
/*      */                                     
/* 5312 */                                     OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5313 */                                     _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 5314 */                                     _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 5316 */                                     _jspx_th_html_005foption_005f0.setValue("-");
/* 5317 */                                     int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 5318 */                                     if (_jspx_eval_html_005foption_005f0 != 0) {
/* 5319 */                                       if (_jspx_eval_html_005foption_005f0 != 1) {
/* 5320 */                                         out = _jspx_page_context.pushBody();
/* 5321 */                                         _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 5322 */                                         _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 5325 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.customer", new String[] { MGSTR }));
/* 5326 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 5327 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 5330 */                                       if (_jspx_eval_html_005foption_005f0 != 1) {
/* 5331 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 5334 */                                     if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 5335 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                     }
/*      */                                     
/* 5338 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 5339 */                                     out.write("\n      ");
/*      */                                     
/* 5341 */                                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5342 */                                     _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 5343 */                                     _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 5345 */                                     _jspx_th_logic_005fnotEmpty_005f0.setName("customers");
/* 5346 */                                     int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 5347 */                                     if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                       for (;;) {
/* 5349 */                                         out.write(32);
/*      */                                         
/* 5351 */                                         IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5352 */                                         _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 5353 */                                         _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                         
/* 5355 */                                         _jspx_th_logic_005fiterate_005f0.setName("customers");
/*      */                                         
/* 5357 */                                         _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                         
/* 5359 */                                         _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                                         
/* 5361 */                                         _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 5362 */                                         int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 5363 */                                         if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 5364 */                                           ArrayList row = null;
/* 5365 */                                           Integer j = null;
/* 5366 */                                           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 5367 */                                             out = _jspx_page_context.pushBody();
/* 5368 */                                             _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 5369 */                                             _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                           }
/* 5371 */                                           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5372 */                                           j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                           for (;;) {
/* 5374 */                                             out.write("\n      ");
/*      */                                             
/* 5376 */                                             OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5377 */                                             _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 5378 */                                             _jspx_th_html_005foption_005f1.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                             
/* 5380 */                                             _jspx_th_html_005foption_005f1.setValue((String)row.get(1));
/* 5381 */                                             int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 5382 */                                             if (_jspx_eval_html_005foption_005f1 != 0) {
/* 5383 */                                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 5384 */                                                 out = _jspx_page_context.pushBody();
/* 5385 */                                                 _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 5386 */                                                 _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5389 */                                                 out.print(row.get(0));
/* 5390 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 5391 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5394 */                                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 5395 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5398 */                                             if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 5399 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                             }
/*      */                                             
/* 5402 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 5403 */                                             out.write("\n      ");
/* 5404 */                                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 5405 */                                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5406 */                                             j = (Integer)_jspx_page_context.findAttribute("j");
/* 5407 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5410 */                                           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 5411 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5414 */                                         if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 5415 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                         }
/*      */                                         
/* 5418 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 5419 */                                         out.write(32);
/* 5420 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 5421 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5425 */                                     if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 5426 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                     }
/*      */                                     
/* 5429 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 5430 */                                     out.write(32);
/* 5431 */                                     int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 5432 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 5435 */                                   if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 5436 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 5439 */                                 if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 5440 */                                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                 }
/*      */                                 
/* 5443 */                                 this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 5444 */                                 out.write("\n      \t</td>\n\t</tr>\n\n\n\n\n\t\t\n        <tr>\n    <td  height=\"28\" valign=\"middle\" class=\"bodytext label-align\">");
/* 5445 */                                 out.print(FormatUtil.getString("it360.sp.customermgmt.site.txt", new String[] { MGSTR }));
/* 5446 */                                 out.write("\n    </td>\n    <td width=\"25%\" height=\"28\" align=\"left\" >\n\t    ");
/*      */                                 
/* 5448 */                                 SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 5449 */                                 _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 5450 */                                 _jspx_th_html_005fselect_005f1.setParent(_jspx_th_c_005fif_005f31);
/*      */                                 
/* 5452 */                                 _jspx_th_html_005fselect_005f1.setProperty("siteName");
/*      */                                 
/* 5454 */                                 _jspx_th_html_005fselect_005f1.setStyleClass("formtext normal");
/*      */                                 
/* 5456 */                                 _jspx_th_html_005fselect_005f1.setOnchange("javascript:loadTypes();");
/*      */                                 
/* 5458 */                                 _jspx_th_html_005fselect_005f1.setStyle("width:50%");
/* 5459 */                                 int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 5460 */                                 if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 5461 */                                   if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 5462 */                                     out = _jspx_page_context.pushBody();
/* 5463 */                                     _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 5464 */                                     _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 5467 */                                     out.write("\n\n      ");
/*      */                                     
/* 5469 */                                     OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5470 */                                     _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 5471 */                                     _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                     
/* 5473 */                                     _jspx_th_html_005foption_005f2.setValue("-");
/* 5474 */                                     int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 5475 */                                     if (_jspx_eval_html_005foption_005f2 != 0) {
/* 5476 */                                       if (_jspx_eval_html_005foption_005f2 != 1) {
/* 5477 */                                         out = _jspx_page_context.pushBody();
/* 5478 */                                         _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 5479 */                                         _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 5482 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.siteGroup", new String[] { MGSTR }));
/* 5483 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 5484 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 5487 */                                       if (_jspx_eval_html_005foption_005f2 != 1) {
/* 5488 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 5491 */                                     if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 5492 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                     }
/*      */                                     
/* 5495 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 5496 */                                     out.write("\n      ");
/*      */                                     
/* 5498 */                                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5499 */                                     _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 5500 */                                     _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                     
/* 5502 */                                     _jspx_th_logic_005fnotEmpty_005f1.setName("applications2");
/* 5503 */                                     int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 5504 */                                     if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                       for (;;) {
/* 5506 */                                         out.write(32);
/*      */                                         
/* 5508 */                                         IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5509 */                                         _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 5510 */                                         _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                         
/* 5512 */                                         _jspx_th_logic_005fiterate_005f1.setName("applications2");
/*      */                                         
/* 5514 */                                         _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                                         
/* 5516 */                                         _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                                         
/* 5518 */                                         _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/* 5519 */                                         int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 5520 */                                         if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 5521 */                                           ArrayList row = null;
/* 5522 */                                           Integer j = null;
/* 5523 */                                           if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5524 */                                             out = _jspx_page_context.pushBody();
/* 5525 */                                             _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 5526 */                                             _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                           }
/* 5528 */                                           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5529 */                                           j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                           for (;;) {
/* 5531 */                                             out.write("\n      ");
/*      */                                             
/* 5533 */                                             OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5534 */                                             _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 5535 */                                             _jspx_th_html_005foption_005f3.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                             
/* 5537 */                                             _jspx_th_html_005foption_005f3.setValue((String)row.get(1));
/* 5538 */                                             int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 5539 */                                             if (_jspx_eval_html_005foption_005f3 != 0) {
/* 5540 */                                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 5541 */                                                 out = _jspx_page_context.pushBody();
/* 5542 */                                                 _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 5543 */                                                 _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5546 */                                                 out.print(row.get(0));
/* 5547 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 5548 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5551 */                                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 5552 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5555 */                                             if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 5556 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                             }
/*      */                                             
/* 5559 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 5560 */                                             out.write("\n      ");
/* 5561 */                                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 5562 */                                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5563 */                                             j = (Integer)_jspx_page_context.findAttribute("j");
/* 5564 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5567 */                                           if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5568 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5571 */                                         if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 5572 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                         }
/*      */                                         
/* 5575 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 5576 */                                         out.write(32);
/* 5577 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 5578 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5582 */                                     if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 5583 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                     }
/*      */                                     
/* 5586 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 5587 */                                     out.write(32);
/* 5588 */                                     int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 5589 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 5592 */                                   if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 5593 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 5596 */                                 if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 5597 */                                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                                 }
/*      */                                 
/* 5600 */                                 this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 5601 */                                 out.write("\n      </td>\n        </tr>\n      ");
/*      */                               }
/* 5603 */                               out.write("\n      <tr>\n  <td  width=\"25%\" height=\"28\"  valign=\"middle\" class=\"bodytext label-align\" ><b>");
/* 5604 */                               out.print(FormatUtil.getString("am.webclient.configureby.option"));
/* 5605 */                               out.write("</b></td > ");
/* 5606 */                               out.write("\n\n\n  <td width=\"75%\" class=\"bodytext\" height=\"28\" >\n\t  <table border=\"0\" >\n        <tr>\t\n\t\t  \t\t\t<td   align=\"left\" class=\"bodytext padd-rt-15\">");
/* 5607 */                               if (_jspx_meth_html_005fradio_005f0(_jspx_th_c_005fif_005f31, _jspx_page_context))
/*      */                                 return;
/* 5609 */                               out.print(FormatUtil.getString("am.webclient.monitor"));
/* 5610 */                               out.write("&nbsp;&nbsp;</td> ");
/* 5611 */                               out.write("\n\t\t  \t\t<td   align=\"left\" class=\"bodytext\" nowrap=\"nowrap\" >");
/* 5612 */                               if (_jspx_meth_html_005fradio_005f1(_jspx_th_c_005fif_005f31, _jspx_page_context))
/*      */                                 return;
/* 5614 */                               out.print(FormatUtil.getString("am.webclient.monitorgroup"));
/* 5615 */                               out.write("&nbsp;&nbsp;</td> ");
/* 5616 */                               out.write("\n\t\t\t\t");
/* 5617 */                               if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 5618 */                                 out.write("\n\t\t\t  ");
/* 5619 */                                 if (!com.adventnet.appmanager.util.Constants.isPrivilegedUser(request))
/* 5620 */                                   if (showMonType) {
/* 5621 */                                     out.write("\n\t\t\t\t  <td  align=\"left\" class=\"bodytext\" nowrap=\"nowrap\" style=\"padding-left:10px;\" >");
/* 5622 */                                     if (_jspx_meth_html_005fradio_005f2(_jspx_th_c_005fif_005f31, _jspx_page_context))
/*      */                                       return;
/* 5624 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 5625 */                                     out.write("</td> ");
/* 5626 */                                     out.write("\n\t\t\t  ");
/*      */                                   } else {
/* 5628 */                                     out.write("\n\t\t\t\t  <td  align=\"left\" class=\"bodytext\" nowrap=\"nowrap\" style=\"display:none;\">");
/* 5629 */                                     if (_jspx_meth_html_005fradio_005f3(_jspx_th_c_005fif_005f31, _jspx_page_context))
/*      */                                       return;
/* 5631 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 5632 */                                     out.write("</td> ");
/* 5633 */                                     out.write("\n\t\t\t  ");
/*      */                                   } }
/* 5635 */                               out.write("\n        </tr>\n        </table>\n    </td>\n    </tr>\n   \n");
/* 5636 */                               if (_jspx_meth_c_005fchoose_005f18(_jspx_th_c_005fif_005f31, _jspx_page_context))
/*      */                                 return;
/* 5638 */                               out.write("\n\t\t\n    <td height=\"28\" valign=\"middle\" class=\"bodytext label-align\" >");
/* 5639 */                               out.print(FormatUtil.getString("am.webclient.monitortype"));
/* 5640 */                               out.write("</td> ");
/* 5641 */                               out.write("\n    <td  height=\"28\" align=\"left\" class=\"z-index-overwrite\" nowrap>\n    \n            ");
/* 5642 */                               if (_jspx_meth_c_005fchoose_005f19(_jspx_th_c_005fif_005f31, _jspx_page_context))
/*      */                                 return;
/* 5644 */                               out.write("\n            \n\t    </td>\n\t    \n    </tr>\n  ");
/* 5645 */                               if (_jspx_meth_c_005fchoose_005f20(_jspx_th_c_005fif_005f31, _jspx_page_context))
/*      */                                 return;
/* 5647 */                               out.write("\n \n          <td   height=\"28\" valign=\"middle\" align=\"left\" class=\"bodytext  label-align\" >");
/* 5648 */                               out.print(FormatUtil.getString("am.webclient.monitor"));
/* 5649 */                               out.write("</td> ");
/* 5650 */                               out.write("\n\t  <td   height=\"28\" align=\"left\" nowrap>\n            ");
/* 5651 */                               if (_jspx_meth_html_005fselect_005f4(_jspx_th_c_005fif_005f31, _jspx_page_context))
/*      */                                 return;
/* 5653 */                               out.write("\n\t    </td>\n\t</tr>\n    \n      ");
/* 5654 */                               if (_jspx_meth_c_005fchoose_005f21(_jspx_th_c_005fif_005f31, _jspx_page_context))
/*      */                                 return;
/* 5656 */                               out.write("\n\n          <td  height=\"28\" valign=\"middle\" class=\"bodytext label-align\" >");
/* 5657 */                               out.print(FormatUtil.getString("am.webclient.monitorgroup"));
/* 5658 */                               out.write("</td> ");
/* 5659 */                               out.write("\n          <td  height=\"28\" align=\"left\" class=\"z-index-overwrite\">  \n\t\t\t<input type=\"hidden\" name=\"method\" value=\"getResourceProfiles\" >");
/* 5660 */                               if (!admin) {
/* 5661 */                                 out.write(32);
/* 5662 */                                 out.print(FormatUtil.getString("am.webclient.configurealert.selectmonitorgroup"));
/* 5663 */                                 out.write(32);
/* 5664 */                                 out.write(58);
/* 5665 */                                 out.write(32);
/*      */                               } else {
/* 5667 */                                 out.write(" <input type=\"hidden\" name=\"admin\" value=\"true\" > ");
/*      */                               }
/* 5669 */                               out.write("\n\t\t\t");
/* 5670 */                               if (_jspx_meth_html_005fselect_005f5(_jspx_th_c_005fif_005f31, _jspx_page_context))
/*      */                                 return;
/* 5672 */                               out.write("\n\t\t  </td>\n\t\t</tr>\n\t\t\n\t\t");
/*      */                               
/* 5674 */                               IfTag _jspx_th_c_005fif_005f32 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5675 */                               _jspx_th_c_005fif_005f32.setPageContext(_jspx_page_context);
/* 5676 */                               _jspx_th_c_005fif_005f32.setParent(_jspx_th_c_005fif_005f31);
/*      */                               
/* 5678 */                               _jspx_th_c_005fif_005f32.setTest("${viewBy == \"monitorGroups\" }");
/* 5679 */                               int _jspx_eval_c_005fif_005f32 = _jspx_th_c_005fif_005f32.doStartTag();
/* 5680 */                               if (_jspx_eval_c_005fif_005f32 != 0) {
/*      */                                 for (;;) {
/* 5682 */                                   out.write(10);
/* 5683 */                                   out.write(9);
/* 5684 */                                   out.write(9);
/*      */                                   
/* 5686 */                                   IfTag _jspx_th_c_005fif_005f33 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5687 */                                   _jspx_th_c_005fif_005f33.setPageContext(_jspx_page_context);
/* 5688 */                                   _jspx_th_c_005fif_005f33.setParent(_jspx_th_c_005fif_005f32);
/*      */                                   
/* 5690 */                                   _jspx_th_c_005fif_005f33.setTest("${!isAdminServer }");
/* 5691 */                                   int _jspx_eval_c_005fif_005f33 = _jspx_th_c_005fif_005f33.doStartTag();
/* 5692 */                                   if (_jspx_eval_c_005fif_005f33 != 0) {
/*      */                                     for (;;) {
/* 5694 */                                       out.write("\n\t\t\t<tr>\n\t\t\t\t<td height=\"28\" valign=\"middle\"  class=\"bodytext label-align\">");
/* 5695 */                                       out.print(FormatUtil.getString("am.actiontype"));
/* 5696 */                                       out.write("</td>\n\t\t\t\t\n\t\t\t\t<td  height=\"28\" valign=\"top\" class=\"bodytext\">\n\t\t\t\t  <table border=\"0\" >\n\t\t\t        <tr>\t\n\t\t\t  \t\t\t<td  align=\"left\"  class=\"bodytext\" ><input type=\"radio\" name=\"groupActions\"  value=\"individual\" checked onclick=\"mgactionTemplate(this.value)\" />");
/* 5697 */                                       out.print(FormatUtil.getString("am.webclient.configurealert.individual.text"));
/* 5698 */                                       out.write("&nbsp;&nbsp;</td> ");
/* 5699 */                                       out.write("\n\t\t\t  \t\t\t<td  align=\"left\"  class=\"bodytext\" ><input type=\"radio\" name=\"groupActions\"  value=\"template\" onclick=\"mgactionTemplate(this.value)\" />");
/* 5700 */                                       out.print(FormatUtil.getString("am.webclient.configurealert.template.text"));
/* 5701 */                                       out.write("</td> ");
/* 5702 */                                       out.write("\n\t\t\t\t\t  \t\t\n\t\t\t        </tr>\n\t\t\t        </table>\n\t    \t\t</td>\n\t\t\t</tr>\n\t\t");
/* 5703 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f33.doAfterBody();
/* 5704 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5708 */                                   if (_jspx_th_c_005fif_005f33.doEndTag() == 5) {
/* 5709 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33); return;
/*      */                                   }
/*      */                                   
/* 5712 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33);
/* 5713 */                                   out.write("\n\t\t<tr id=\"mgtemplate_monitortype\">\n\t\t\t<td height=\"28\" valign=\"middle\"  class=\"bodytext label-align\">");
/* 5714 */                                   out.print(FormatUtil.getString("am.webclient.selectmonitor.alert.text"));
/* 5715 */                                   out.write("</td>\n\t\t\t<td height=\"28\" align=\"left\" class=\"bodytext\">\n\t\t\t\t");
/* 5716 */                                   if (_jspx_meth_html_005fselect_005f6(_jspx_th_c_005fif_005f32, _jspx_page_context))
/*      */                                     return;
/* 5718 */                                   out.write("\n\t\t\t</td>\n\t\t</tr>\n\t\t");
/* 5719 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f32.doAfterBody();
/* 5720 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 5724 */                               if (_jspx_th_c_005fif_005f32.doEndTag() == 5) {
/* 5725 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32); return;
/*      */                               }
/*      */                               
/* 5728 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32);
/* 5729 */                               out.write("\n  <tr>\n    <td  height=\"2\"><img src=\"/images/spacer.gif\" width=\"10\" height=\"7\"></td>\n  </tr>\n</table></td>\n\n\t\t\n\t\t");
/*      */                             }
/* 5731 */                             out.write("\n\t  </tr>\n  ");
/* 5732 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f31.doAfterBody();
/* 5733 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 5737 */                         if (_jspx_th_c_005fif_005f31.doEndTag() == 5) {
/* 5738 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31); return;
/*      */                         }
/*      */                         
/* 5741 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 5742 */                         out.write("\n</table>\n");
/* 5743 */                         if (com.adventnet.appmanager.util.Constants.isIt360) {
/* 5744 */                           out.write("\n</td>\n</tr>\n");
/*      */                         }
/* 5746 */                         out.write(10);
/* 5747 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f30.doAfterBody();
/* 5748 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 5752 */                     if (_jspx_th_c_005fif_005f30.doEndTag() == 5) {
/* 5753 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30); return;
/*      */                     }
/*      */                     
/* 5756 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 5757 */                     out.write(10);
/* 5758 */                     out.write(10);
/*      */                     
/* 5760 */                     ChooseTag _jspx_th_c_005fchoose_005f22 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5761 */                     _jspx_th_c_005fchoose_005f22.setPageContext(_jspx_page_context);
/* 5762 */                     _jspx_th_c_005fchoose_005f22.setParent(_jspx_th_html_005fform_005f0);
/* 5763 */                     int _jspx_eval_c_005fchoose_005f22 = _jspx_th_c_005fchoose_005f22.doStartTag();
/* 5764 */                     if (_jspx_eval_c_005fchoose_005f22 != 0) {
/*      */                       for (;;) {
/* 5766 */                         out.write(10);
/*      */                         
/* 5768 */                         WhenTag _jspx_th_c_005fwhen_005f22 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5769 */                         _jspx_th_c_005fwhen_005f22.setPageContext(_jspx_page_context);
/* 5770 */                         _jspx_th_c_005fwhen_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/*      */                         
/* 5772 */                         _jspx_th_c_005fwhen_005f22.setTest("${viewBy != 'monitorType'}");
/* 5773 */                         int _jspx_eval_c_005fwhen_005f22 = _jspx_th_c_005fwhen_005f22.doStartTag();
/* 5774 */                         if (_jspx_eval_c_005fwhen_005f22 != 0) {
/*      */                           for (;;) {
/* 5776 */                             out.write(10);
/*      */                             
/* 5778 */                             if ((monitor == null) || (!monitor.equals("true")))
/*      */                             {
/*      */ 
/* 5781 */                               out.write(10);
/*      */                               
/* 5783 */                               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5784 */                               _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 5785 */                               _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_c_005fwhen_005f22);
/*      */                               
/* 5787 */                               _jspx_th_logic_005fnotEmpty_005f2.setName("discoverystatus");
/* 5788 */                               int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 5789 */                               if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                                 for (;;) {
/* 5791 */                                   out.write(10);
/* 5792 */                                   out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n");
/*      */                                   
/* 5794 */                                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5795 */                                   _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 5796 */                                   _jspx_th_logic_005fnotEmpty_005f3.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                   
/* 5798 */                                   _jspx_th_logic_005fnotEmpty_005f3.setName("discoverystatus");
/* 5799 */                                   int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 5800 */                                   if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */                                     for (;;) {
/* 5802 */                                       out.write(10);
/*      */                                       
/*      */ 
/* 5805 */                                       if ((request.getAttribute("type") == null) || ((!request.getAttribute("type").equals("Script Monitor")) && ((request.getAttribute("basetype") == null) || ((request.getAttribute("basetype") != null) && (!request.getAttribute("basetype").equals("Script Monitor")))) && (!request.getAttribute("type").equals("QENGINE")) && (!request.getAttribute("type").equals("Web Service")) && (!request.getAttribute("type").equals("file")) && (!request.getAttribute("type").equals("directory")) && (!request.getAttribute("type").equals("File System Monitor")) && (!request.getAttribute("type").equals("Ping Monitor")) && (!request.getAttribute("type").equals("SAP-CCMS"))))
/*      */                                       {
/*      */ 
/* 5808 */                                         out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n  <tr>\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 5809 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 5810 */                                         out.write("</span> </td>\n    <td width=\"7%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 5811 */                                         out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 5812 */                                         out.write("\n      </span> </td>\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 5813 */                                         out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 5814 */                                         out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 5815 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 5816 */                                         out.write("\n </span></td>\n  <tr>\n  ");
/*      */                                         
/* 5818 */                                         int failedNumber = 1;
/*      */                                         
/* 5820 */                                         out.write(10);
/*      */                                         
/* 5822 */                                         IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5823 */                                         _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 5824 */                                         _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f3);
/*      */                                         
/* 5826 */                                         _jspx_th_logic_005fiterate_005f2.setName("discoverystatus");
/*      */                                         
/* 5828 */                                         _jspx_th_logic_005fiterate_005f2.setId("row");
/*      */                                         
/* 5830 */                                         _jspx_th_logic_005fiterate_005f2.setIndexId("i");
/*      */                                         
/* 5832 */                                         _jspx_th_logic_005fiterate_005f2.setType("java.util.ArrayList");
/* 5833 */                                         int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 5834 */                                         if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 5835 */                                           ArrayList row = null;
/* 5836 */                                           Integer i = null;
/* 5837 */                                           if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 5838 */                                             out = _jspx_page_context.pushBody();
/* 5839 */                                             _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 5840 */                                             _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                                           }
/* 5842 */                                           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5843 */                                           i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                           for (;;) {
/* 5845 */                                             out.write("\n<tr>\n<td height=\"18\" class=\"bodytext\">");
/* 5846 */                                             out.print(row.get(0));
/* 5847 */                                             out.write("</td>\n<td height=\"18\" class=\"bodytext\">");
/* 5848 */                                             out.print(row.get(1));
/* 5849 */                                             out.write("</td>\n\n    <td height=\"18\" class=\"bodytext\">\n      ");
/*      */                                             
/* 5851 */                                             if ((row.get(2).equals("Success")) || (row.get(2).equals("Present")))
/*      */                                             {
/* 5853 */                                               request.setAttribute("isDiscoverySuccess", "true");
/*      */                                               
/* 5855 */                                               out.write("\n      <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 5856 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 5857 */                                               out.write("\" align=\"absmiddle\">\n      ");
/*      */ 
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 5862 */                                               request.setAttribute("isDiscoverySuccess", "false");
/*      */                                               
/* 5864 */                                               out.write("\n      <img alt=\"");
/* 5865 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.notiniatedimagetip.text"));
/* 5866 */                                               out.write("\" src=\"/images/icon_monitor_failure.gif\" align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                             }
/*      */                                             
/*      */ 
/* 5870 */                                             out.write("\n      <span class=\"bodytextbold\">");
/* 5871 */                                             out.print(FormatUtil.getString(String.valueOf(row.get(2))));
/* 5872 */                                             out.write("</span> </td>\n\n      ");
/*      */                                             
/* 5874 */                                             pageContext.setAttribute("ret_msg", String.valueOf(row.get(3)));
/*      */                                             
/* 5876 */                                             out.write("\n                     ");
/*      */                                             
/* 5878 */                                             IfTag _jspx_th_c_005fif_005f34 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5879 */                                             _jspx_th_c_005fif_005f34.setPageContext(_jspx_page_context);
/* 5880 */                                             _jspx_th_c_005fif_005f34.setParent(_jspx_th_logic_005fiterate_005f2);
/*      */                                             
/* 5882 */                                             _jspx_th_c_005fif_005f34.setTest("${ret_msg=='Monitoring Initiated Succesfully'}");
/* 5883 */                                             int _jspx_eval_c_005fif_005f34 = _jspx_th_c_005fif_005f34.doStartTag();
/* 5884 */                                             if (_jspx_eval_c_005fif_005f34 != 0) {
/*      */                                               for (;;) {
/* 5886 */                                                 out.write("\n                           <td height=\"18\" class=\"bodytext\">");
/* 5887 */                                                 out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 5888 */                                                 out.write("\n                                 ");
/* 5889 */                                                 int evalDoAfterBody = _jspx_th_c_005fif_005f34.doAfterBody();
/* 5890 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 5894 */                                             if (_jspx_th_c_005fif_005f34.doEndTag() == 5) {
/* 5895 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34); return;
/*      */                                             }
/*      */                                             
/* 5898 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34);
/* 5899 */                                             out.write("\n                                       ");
/*      */                                             
/* 5901 */                                             IfTag _jspx_th_c_005fif_005f35 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5902 */                                             _jspx_th_c_005fif_005f35.setPageContext(_jspx_page_context);
/* 5903 */                                             _jspx_th_c_005fif_005f35.setParent(_jspx_th_logic_005fiterate_005f2);
/*      */                                             
/* 5905 */                                             _jspx_th_c_005fif_005f35.setTest("${ret_msg!='Monitoring Initiated Succesfully'}");
/* 5906 */                                             int _jspx_eval_c_005fif_005f35 = _jspx_th_c_005fif_005f35.doStartTag();
/* 5907 */                                             if (_jspx_eval_c_005fif_005f35 != 0) {
/*      */                                               for (;;) {
/* 5909 */                                                 out.write("\n                                             <td height=\"18\" class=\"bodytext\">");
/* 5910 */                                                 out.print(row.get(3));
/* 5911 */                                                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                 
/* 5913 */                                                 if ((request.getAttribute("type") != null) && (!request.getAttribute("type").equals("All")) && (!request.getAttribute("type").equals("TELNET")) && (!request.getAttribute("type").equals("SNMP")) && (!request.getAttribute("type").equals("SERVICE")) && (!request.getAttribute("type").equals("JMX1.2-MX4J-RMI")) && (!request.getAttribute("type").equals("JDK1.5")) && (!request.getAttribute("type").equals("RMI")) && (!request.getAttribute("type").equals("PHP")) && (!request.getAttribute("type").equals("MAIL")) && (!request.getAttribute("type").equals("ORACLEDB")) && (!request.getAttribute("type").equals("MYSQLDB")) && (!request.getAttribute("type").equals("MSSQLDB")) && (!request.getAttribute("type").equals("DB2")) && (!request.getAttribute("type").equals("WTA")) && (!request.getAttribute("type").equals("SAP")))
/*      */                                                 {
/* 5915 */                                                   if (((String)pageContext.getAttribute("ret_msg")).indexOf(FormatUtil.getString("am.webclient.discovery.host.monitoradded.text")) == -1)
/*      */                                                   {
/* 5917 */                                                     String fWhr = request.getParameter("hideFieldsForIT360");
/* 5918 */                                                     if (fWhr == null)
/*      */                                                     {
/* 5920 */                                                       fWhr = (String)request.getAttribute("hideFieldsForIT360");
/*      */                                                     }
/*      */                                                     
/* 5923 */                                                     if (((fWhr == null) || (!fWhr.equals("true"))) && 
/* 5924 */                                                       (!request.getAttribute("type").equals("SYBASEDB")))
/*      */                                                     {
/* 5926 */                                                       out.write("\n\t\t\t\t\t\t\t\t\t\t\t &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"bodytext\" href=\"javascript:void(0)\" onClick=\"getAllDetailsOfHost()\">");
/* 5927 */                                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.diagnose.link"));
/* 5928 */                                                       out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                     }
/*      */                                                   } }
/* 5931 */                                                 if ((request.getAttribute("showSupportMessage") != null) && (failedNumber == 1) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                                                 {
/* 5933 */                                                   failedNumber++;
/*      */                                                   
/*      */ 
/* 5936 */                                                   out.write("\n\t\t\t\t\t\t\t\t\t\t<br>");
/* 5937 */                                                   out.print(FormatUtil.getString("am.webclient.discovery.host.support.link", new String[] { OEMUtil.getOEMString("product.talkback.mailid"), OEMUtil.getOEMString("product.tollfree.number") }));
/* 5938 */                                                   out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                                 }
/* 5940 */                                                 out.write("\n                                                   ");
/* 5941 */                                                 int evalDoAfterBody = _jspx_th_c_005fif_005f35.doAfterBody();
/* 5942 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 5946 */                                             if (_jspx_th_c_005fif_005f35.doEndTag() == 5) {
/* 5947 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f35); return;
/*      */                                             }
/*      */                                             
/* 5950 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f35);
/* 5951 */                                             out.write(10);
/* 5952 */                                             out.write(10);
/* 5953 */                                             out.write(10);
/*      */                                             
/*      */ 
/* 5956 */                                             if (row.size() > 4)
/*      */                                             {
/*      */ 
/* 5959 */                                               out.write("<br>\n");
/* 5960 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)row.get(4) }));
/* 5961 */                                               out.write(10);
/*      */                                             }
/*      */                                             
/*      */ 
/* 5965 */                                             out.write("\n</td>\n\n</tr>\n");
/* 5966 */                                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 5967 */                                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5968 */                                             i = (Integer)_jspx_page_context.findAttribute("i");
/* 5969 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5972 */                                           if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 5973 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5976 */                                         if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 5977 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                                         }
/*      */                                         
/* 5980 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 5981 */                                         out.write("\n</table>\n");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 5986 */                                         ArrayList al1 = (ArrayList)request.getAttribute("discoverystatus");
/*      */                                         
/* 5988 */                                         out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n<tr>\n");
/* 5989 */                                         String mtype = (String)request.getAttribute("type");
/* 5990 */                                         out.write(10);
/* 5991 */                                         if (mtype.equals("File System Monitor")) {
/* 5992 */                                           out.write("\n  <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 5993 */                                           out.print(FormatUtil.getString("File/Directory Name"));
/* 5994 */                                           out.write("</span> </td>\n");
/* 5995 */                                         } else if ((((String)request.getAttribute("type")).equals("Ping Monitor")) || (((String)request.getAttribute("type")).equals("SAP-CCMS"))) {
/* 5996 */                                           out.write("\n\t<td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 5997 */                                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 5998 */                                           out.write("</span> </td>\n");
/*      */                                         } else {
/* 6000 */                                           out.write("\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 6001 */                                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 6002 */                                           out.write("</span> </td>\n");
/*      */                                         }
/* 6004 */                                         out.write("\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 6005 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 6006 */                                         out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 6007 */                                         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 6008 */                                         out.write("</span></td>\n  </tr>\n  <tr>\n   <td height=\"18\" class=\"bodytext\">");
/* 6009 */                                         out.print(al1.get(0));
/* 6010 */                                         out.write("</td>\n   <td height=\"18\" class=\"bodytext\">\n   ");
/*      */                                         
/* 6012 */                                         if (al1.get(1).equals("Success"))
/*      */                                         {
/* 6014 */                                           request.setAttribute("isDiscoverySuccess", "true");
/*      */                                           
/* 6016 */                                           out.write("\n   <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 6017 */                                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 6018 */                                           out.write("\" align=\"absmiddle\">\n    ");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 6023 */                                           request.setAttribute("isDiscoverySuccess", "false");
/*      */                                           
/*      */ 
/* 6026 */                                           out.write("\n      <img  src=\"/images/icon_monitor_failure.gif\"  align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                         }
/*      */                                         
/*      */ 
/* 6030 */                                         out.write("\n<span class=\"bodytextbold\">");
/* 6031 */                                         out.print(FormatUtil.getString(String.valueOf(al1.get(1))));
/* 6032 */                                         out.write("</span> </td>\n");
/*      */                                         
/* 6034 */                                         if (al1.get(1).equals("Success"))
/*      */                                         {
/* 6036 */                                           boolean isAdminServer = EnterpriseUtil.isAdminServer();
/* 6037 */                                           if (isAdminServer) {
/* 6038 */                                             String masDisplayName = (String)al1.get(3);
/* 6039 */                                             String format = FormatUtil.getString("am.webclient.admin.add.monitor.successfully.configured.text", new String[] { masDisplayName, "", (String)al1.get(2) });
/*      */                                             
/* 6041 */                                             out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\">");
/* 6042 */                                             out.print(format);
/* 6043 */                                             out.write("</td>\n");
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 6047 */                                             out.write("\t\t\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"> ");
/* 6048 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 6049 */                                             out.write("<br> ");
/* 6050 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)al1.get(2) }));
/* 6051 */                                             out.write("</td>\n");
/*      */                                           }
/*      */                                           
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 6058 */                                           out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"><span class=\"bodytext\">");
/* 6059 */                                           out.print(al1.get(2));
/* 6060 */                                           out.write("</span></td>\n");
/*      */                                         }
/*      */                                         
/*      */ 
/* 6064 */                                         out.write("\n  </tr>\n</table>\n\n");
/*      */                                       }
/*      */                                       
/*      */ 
/* 6068 */                                       out.write(10);
/* 6069 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 6070 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 6074 */                                   if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 6075 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3); return;
/*      */                                   }
/*      */                                   
/* 6078 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 6079 */                                   out.write(10);
/* 6080 */                                   out.write(10);
/* 6081 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 6082 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 6086 */                               if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 6087 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                               }
/*      */                               
/* 6090 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 6091 */                               out.write(10);
/* 6092 */                               out.write(32);
/* 6093 */                               out.write(32);
/*      */                               
/* 6095 */                               int i = 0;
/* 6096 */                               LinkedHashMap hadetails = (LinkedHashMap)request.getAttribute("hadetails");
/* 6097 */                               boolean actionAvailable = ((Boolean)request.getAttribute("actionsAvailable")).booleanValue();
/* 6098 */                               boolean dependentConfigured = ((Boolean)request.getAttribute("actionsAvailable")).booleanValue();
/* 6099 */                               String baname = (String)hadetails.get("baname");
/* 6100 */                               hadetails.remove("baname");
/* 6101 */                               String bgcolour = "whitegrayborder";
/* 6102 */                               String redirectto = "/showActionProfiles.do?method=getHAProfiles&haid=" + haid + wiz;
/* 6103 */                               if (admin)
/*      */                               {
/* 6105 */                                 redirectto = redirectto + "&admin=true";
/*      */                               }
/* 6107 */                               if ((request.getParameter("redirectto") != null) && (!request.getParameter("redirectto").equalsIgnoreCase("null")))
/*      */                               {
/* 6109 */                                 redirectto = request.getParameter("redirectto");
/*      */                               }
/*      */                               
/* 6112 */                               out.write("\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 6113 */                               out.print(haid);
/* 6114 */                               out.write("\">\n<input type=\"hidden\" name=\"redirectto\" value=\"");
/* 6115 */                               out.print(redirectto);
/* 6116 */                               out.write(34);
/* 6117 */                               out.write(62);
/* 6118 */                               out.write(10);
/*      */                               
/* 6120 */                               if (!hadetails.isEmpty())
/*      */                               {
/* 6122 */                                 LinkedHashMap badetails = (LinkedHashMap)hadetails.get(baname);
/* 6123 */                                 hadetails.remove(baname);
/* 6124 */                                 Hashtable baAvailability = (Hashtable)badetails.get("Availability");
/* 6125 */                                 Hashtable baHealth = (Hashtable)badetails.get("Health");
/* 6126 */                                 badetails.remove("Availability");
/* 6127 */                                 badetails.remove("Health");
/* 6128 */                                 String redirect = "";
/* 6129 */                                 if (admin)
/*      */                                 {
/* 6131 */                                   redirect = "/showActionProfiles.do?method=getHAProfiles&haid=" + haid + "&admin=true" + wiz;
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 6135 */                                   redirect = "/showActionProfiles.do?method=getHAProfiles&haid=" + haid + wiz;
/*      */                                 }
/*      */                                 
/* 6138 */                                 out.write(10);
/* 6139 */                                 out.write(9);
/* 6140 */                                 if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fwhen_005f22, _jspx_page_context))
/*      */                                   return;
/* 6142 */                                 out.write(10);
/* 6143 */                                 out.write(9);
/*      */                                 
/* 6145 */                                 IfTag _jspx_th_c_005fif_005f36 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6146 */                                 _jspx_th_c_005fif_005f36.setPageContext(_jspx_page_context);
/* 6147 */                                 _jspx_th_c_005fif_005f36.setParent(_jspx_th_c_005fwhen_005f22);
/*      */                                 
/* 6149 */                                 _jspx_th_c_005fif_005f36.setTest("${!isAdminServer}");
/* 6150 */                                 int _jspx_eval_c_005fif_005f36 = _jspx_th_c_005fif_005f36.doStartTag();
/* 6151 */                                 if (_jspx_eval_c_005fif_005f36 != 0) {
/*      */                                   for (;;) {
/* 6153 */                                     out.write(10);
/* 6154 */                                     out.write(9);
/* 6155 */                                     out.write(9);
/* 6156 */                                     if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f36, _jspx_page_context))
/*      */                                       return;
/* 6158 */                                     out.write(10);
/* 6159 */                                     out.write(9);
/* 6160 */                                     out.write(9);
/* 6161 */                                     JspRuntimeLibrary.include(request, response, "/jsp/GlobalHealthAvailabilityConfig.jsp" + ("/jsp/GlobalHealthAvailabilityConfig.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("redirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(redirect), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(haid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("isMG", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("true", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("PRINTER_FRIENDLY", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("true", request.getCharacterEncoding()), out, false);
/* 6162 */                                     out.write("\n\t\t<br>\n\t");
/* 6163 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f36.doAfterBody();
/* 6164 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 6168 */                                 if (_jspx_th_c_005fif_005f36.doEndTag() == 5) {
/* 6169 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f36); return;
/*      */                                 }
/*      */                                 
/* 6172 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f36);
/* 6173 */                                 out.write("\n\n\n \n <div id=\"monitorsInGroup\" style=\"");
/* 6174 */                                 if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fwhen_005f22, _jspx_page_context))
/*      */                                   return;
/* 6176 */                                 out.write("\">\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n    <tr>\n      <td  colspan=\"2\" class=\"tableheadingbborder\">");
/* 6177 */                                 if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fwhen_005f22, _jspx_page_context))
/*      */                                   return;
/* 6179 */                                 out.write("</td>\n      <td  colspan=\"2\" align=\"right\" class=\"tableheadingbborder\">\n        <div id=\"showall\" style=\"display:block; position:relative; right:5px;\" onclick=\"javascript:showallDiv('monitor'),hideallDiv('monitorShow'),showallDivInline('monitorHide'),hideDiv('showall'),showDiv('hideall');\"><span class=\"bodytextboldwhiteun\">[");
/* 6180 */                                 out.print(FormatUtil.getString("am.webclient.configurealert.expandall"));
/* 6181 */                                 out.write("]</span></div>\n        <div id=\"hideall\" style=\"display:none; position:relative; right:5px;\" onclick=\"javascript:hideallDiv('monitor'),hideallDiv('monitorHide'),showallDivInline('monitorShow'),hideDiv('hideall'),showDiv('showall');\"><span class=\"bodytextboldwhiteun\">[");
/* 6182 */                                 out.print(FormatUtil.getString("am.webclient.configurealert.collapseall"));
/* 6183 */                                 out.write("]</span></div>\n        </td>\n    </tr>\n    <tr class=\"\">\n  \t  <td width=\"28%\" align=\"left\" class=\"columnheading bodytextbold\">&nbsp;");
/* 6184 */                                 out.print(FormatUtil.getString("am.webclient.configurealert.monitorname"));
/* 6185 */                                 out.write("</td>\n  \t  <td width=\"20%\" align=\"left\" class=\"columnheading bodytextbold\">");
/* 6186 */                                 out.print(FormatUtil.getString("am.webclient.configurealert.type"));
/* 6187 */                                 out.write("</td>\n  \t  <td width=\"35%\" align=\"left\" class=\"columnheading bodytextbold\">");
/* 6188 */                                 out.print(FormatUtil.getString("Threshold"));
/* 6189 */                                 out.write("</td>\n       <td width=\"15%\" align=\"left\" class=\"columnheading bodytextbold\">");
/* 6190 */                                 out.print(FormatUtil.getString("Anomaly"));
/* 6191 */                                 out.write("&nbsp;</td>\n    </tr>\n\t");
/*      */                                 
/* 6193 */                                 Hashtable ht1 = (Hashtable)pageContext.findAttribute("motypedisplaynames");
/* 6194 */                                 for (Iterator e = hadetails.keySet().iterator(); e.hasNext();)
/*      */                                 {
/*      */ 
/* 6197 */                                   String moname = "";
/* 6198 */                                   LinkedHashMap modetails = new LinkedHashMap();
/* 6199 */                                   Properties p = new Properties();
/* 6200 */                                   moname = (String)e.next();
/* 6201 */                                   modetails = (LinkedHashMap)hadetails.get(moname);
/* 6202 */                                   String displayname = FormatUtil.getString((String)modetails.get("displayname"));
/* 6203 */                                   String icon = (String)modetails.get("icon");
/* 6204 */                                   String resourceid = (String)modetails.get("resourceid");
/* 6205 */                                   String type = FormatUtil.getString((String)modetails.get("type"));
/* 6206 */                                   modetails.remove("displayname");
/* 6207 */                                   modetails.remove("resourceid");
/* 6208 */                                   modetails.remove("icon");
/* 6209 */                                   modetails.remove("type");
/* 6210 */                                   p = (Properties)modetails.get("Availability");
/* 6211 */                                   String attributeid_availability = p.getProperty("attributeid");
/* 6212 */                                   if (modetails.get("Health") != null)
/*      */                                   {
/* 6214 */                                     p.putAll((Properties)modetails.get("Health"));
/*      */                                   }
/* 6216 */                                   String attributeid_health = p.getProperty("attributeid");
/* 6217 */                                   String attributeid_both = attributeid_availability + "," + attributeid_health;
/* 6218 */                                   if (type.equals("Service"))
/*      */                                   {
/* 6220 */                                     attributeid_both = attributeid_availability;
/*      */                                   }
/* 6222 */                                   String health_actions = "";
/* 6223 */                                   int j = 0;
/* 6224 */                                   if (p.getProperty("1") != null)
/*      */                                   {
/* 6226 */                                     StringTokenizer st = new StringTokenizer(p.getProperty("1"), ",");
/* 6227 */                                     while (st.hasMoreTokens())
/*      */                                     {
/* 6229 */                                       String temp = st.nextToken();
/* 6230 */                                       StringTokenizer st1 = new StringTokenizer(temp, ":");
/* 6231 */                                       if (st1.hasMoreTokens())
/*      */                                       {
/* 6233 */                                         if (j == 0)
/*      */                                         {
/* 6235 */                                           health_actions = st1.nextToken();
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 6239 */                                           health_actions = health_actions + ", " + st1.nextToken();
/*      */                                         }
/*      */                                       }
/* 6242 */                                       j++;
/*      */                                     }
/*      */                                     
/* 6245 */                                     j = 0;
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 6249 */                                     health_actions = FormatUtil.getString("am.webclient.configurealert.associateactionsglobal");
/*      */                                   }
/* 6251 */                                   if (i % 2 == 0)
/*      */                                   {
/* 6253 */                                     bgcolour = "whitegrayborder";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 6257 */                                     bgcolour = "yellowgrayborder";
/*      */                                   }
/* 6259 */                                   i++;
/* 6260 */                                   modetails.remove("Health");
/* 6261 */                                   modetails.remove("Availability");
/*      */                                   
/* 6263 */                                   out.write("\n  \t\t<tr  class=\"configalarmsHeader\" onmouseover=\"this.className='configalarmsHeaderHover'\"  onmouseout=\"this.className='configalarmsHeader'\">\n          <td width=\"28%\" class=\"");
/* 6264 */                                   out.print(bgcolour);
/* 6265 */                                   out.write("\" height=\"20\" title=\"");
/* 6266 */                                   out.print(getTrimmedText(displayname, 50));
/* 6267 */                                   out.write("\">\n          \t<a href=\"javascript:void(0);\" onClick=\"toggleDiv('monitor");
/* 6268 */                                   out.print(i);
/* 6269 */                                   out.write("'),toggleDivInline('monitorShow");
/* 6270 */                                   out.print(i);
/* 6271 */                                   out.write("$monitorHide");
/* 6272 */                                   out.print(i);
/* 6273 */                                   out.write("');\" class=\"widget-links\">\n\t\t  <div id=\"monitorShow");
/* 6274 */                                   out.print(i);
/* 6275 */                                   out.write("\" style=\"display:inline;\">\n\t\t  \t<img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\">\n\t\t  </div>\n\t\t  <div id=\"monitorHide");
/* 6276 */                                   out.print(i);
/* 6277 */                                   out.write("\" style=\"display:none;\">\n\t\t  \t<img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\">\n\t\t  </div>\n\t\t  <span class=\"bodytext\">");
/* 6278 */                                   out.print(getTrimmedText(displayname, 50));
/* 6279 */                                   out.write("</span>\n\t\t  </a>\n\t</td>\n          <td  width=\"20%\" title=\"");
/* 6280 */                                   out.print(ht1.get(type));
/* 6281 */                                   out.write("\" class=\"");
/* 6282 */                                   out.print(bgcolour);
/* 6283 */                                   out.write(34);
/* 6284 */                                   out.write(62);
/* 6285 */                                   out.print(type);
/* 6286 */                                   out.write("</td>\n          <td  width=\"35%\" class=\"");
/* 6287 */                                   out.print(bgcolour);
/* 6288 */                                   out.write("\"> <a href=\"javascript:MM_openBrWindow('/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 6289 */                                   out.print(resourceid);
/* 6290 */                                   out.write("&attributeIDs=");
/* 6291 */                                   out.print(attributeid_both);
/* 6292 */                                   out.write("&attributeToSelect=");
/* 6293 */                                   out.print(attributeid_health);
/* 6294 */                                   out.write("&global=true&redirectto=");
/* 6295 */                                   out.print(URLEncoder.encode(redirect));
/* 6296 */                                   out.write("','','resizable=yes,scrollbars=yes,width=800,height=440')\" class=\"widget-links\">");
/* 6297 */                                   out.print(health_actions);
/* 6298 */                                   out.write("</a></td>\n          <td  width=\"15%\" class=\"");
/* 6299 */                                   out.print(bgcolour);
/* 6300 */                                   out.write("\">&nbsp;</td>\n        </tr>\n        <tr>\n        <td colspan=\"4\" width=\"100%\">\n\t    <div id=\"monitor");
/* 6301 */                                   out.print(i);
/* 6302 */                                   out.write("\" style=\"display:none;\">\n\t    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n          ");
/*      */                                   
/* 6304 */                                   for (Iterator e1 = modetails.keySet().iterator(); e1.hasNext();)
/*      */                                   {
/* 6306 */                                     if (j % 2 == 0)
/*      */                                     {
/* 6308 */                                       bgcolour = "whitegrayborder";
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 6312 */                                       bgcolour = "whitegrayborder";
/*      */                                     }
/* 6314 */                                     j++;
/* 6315 */                                     String thname = "-";
/* 6316 */                                     String attname = "-";
/* 6317 */                                     String anmname = FormatUtil.getString("am.webclient.configurealert.associate");
/* 6318 */                                     String anmid = " ";
/* 6319 */                                     p = new Properties();
/* 6320 */                                     attname = (String)e1.next();
/* 6321 */                                     p = (Properties)modetails.get(attname);
/* 6322 */                                     attname = FormatUtil.getString(p.getProperty("displayname"));
/* 6323 */                                     if ((p.getProperty("attributeunit") != null) && (!p.getProperty("attributeunit").equals("null")) && (!p.getProperty("attributeunit").equals("")))
/* 6324 */                                       attname = attname + " (" + FormatUtil.getString(p.getProperty("attributeunit")) + ")";
/* 6325 */                                     String attributeid = p.getProperty("attributeid");
/* 6326 */                                     String attributeunit = FormatUtil.getString(p.getProperty("attributeunit"));
/* 6327 */                                     String wscritical = "";
/* 6328 */                                     Hashtable ht = (Hashtable)pageContext.findAttribute("thresholdconditions");
/*      */                                     
/* 6330 */                                     String tooltip = FormatUtil.getString("am.webclient.configurealert.newthresholdsandactions");
/* 6331 */                                     String details = FormatUtil.getString("am.webclient.configurealert.associate");
/* 6332 */                                     if (p.getProperty("name") != null)
/*      */                                     {
/* 6334 */                                       thname = p.getProperty("name");
/*      */                                       
/*      */ 
/* 6337 */                                       tooltip = thname + ": Critical " + ht.get(p.getProperty("criticalcondition")) + " " + p.getProperty("criticalvalue") + " " + attributeunit + ", Warning " + ht.get(p.getProperty("warningcondition")) + " " + p.getProperty("warningvalue") + " " + attributeunit + ", Clear " + ht.get(p.getProperty("clearcondition")) + " " + p.getProperty("clearvalue") + " " + attributeunit + ".";
/* 6338 */                                       details = thname + " - " + "Cr " + ht.get(p.getProperty("criticalcondition")) + " " + p.getProperty("criticalvalue");
/* 6339 */                                       if (p.getProperty("1") != null)
/*      */                                       {
/* 6341 */                                         StringTokenizer st = new StringTokenizer(p.getProperty("1"), ",");
/* 6342 */                                         while (st.hasMoreTokens())
/*      */                                         {
/* 6344 */                                           String temp = st.nextToken();
/* 6345 */                                           StringTokenizer st1 = new StringTokenizer(temp, ":");
/* 6346 */                                           if (st1.hasMoreTokens())
/*      */                                           {
/* 6348 */                                             wscritical = wscritical + ", " + st1.nextToken();
/*      */                                           }
/*      */                                         }
/*      */                                       }
/*      */                                       
/* 6353 */                                       details = details + wscritical;
/*      */                                     }
/* 6355 */                                     if (p.getProperty("anomalyid") != null) {
/* 6356 */                                       anmid = p.getProperty("anomalyid");
/* 6357 */                                       anmname = p.getProperty("anomalyname");
/*      */                                     }
/*      */                                     
/*      */ 
/* 6361 */                                     out.write("\n          <tr  class=\"widget-links\" onmouseover=\"this.className='ajaxMapHeaderHover'\"  onmouseout=\"this.className='ajaxMapHeader'\">\n            <td  class=\"");
/* 6362 */                                     out.print(bgcolour);
/* 6363 */                                     out.write("\"  width=\"28%\" valign=\"middle\" title=\"");
/* 6364 */                                     out.print(FormatUtil.getString(attname));
/* 6365 */                                     out.write("\">&nbsp;&nbsp;<img src=\"/images/icon_arrow_childattribute.gif\" border=\"0\">");
/* 6366 */                                     out.print(getTrimmedText(FormatUtil.getString(attname), 35));
/* 6367 */                                     out.write("</td>\n            <td  class=\"");
/* 6368 */                                     out.print(bgcolour);
/* 6369 */                                     out.write("\" width=\"20%\">&nbsp;&nbsp;</td>\n\n               <td width=\"35%\" title=\"");
/* 6370 */                                     out.print(tooltip);
/* 6371 */                                     out.write("\"  class=\"");
/* 6372 */                                     out.print(bgcolour);
/* 6373 */                                     out.write("\"><a href=\"javascript:MM_openBrWindow('/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 6374 */                                     out.print(resourceid);
/* 6375 */                                     out.write("&attributeIDs=");
/* 6376 */                                     out.print(attributeid);
/* 6377 */                                     out.write("&attributeToSelect=");
/* 6378 */                                     out.print(attributeid);
/* 6379 */                                     out.write("&global=true&redirectto=");
/* 6380 */                                     out.print(URLEncoder.encode(redirect));
/* 6381 */                                     out.write("&isThreshold=true','','resizable=yes,scrollbars=yes,width=800,height=440')\" class=\"widget-links\">");
/* 6382 */                                     out.print(details);
/* 6383 */                                     out.write("</a></td>\n\n\n            ");
/* 6384 */                                     if ("-".equals(anmname)) {
/* 6385 */                                       out.write("\n             <td width=\"15%\" class=\"");
/* 6386 */                                       out.print(bgcolour);
/* 6387 */                                       out.write("\"><a href=\"javascript:MM_openBrWindow('/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 6388 */                                       out.print(resourceid);
/* 6389 */                                       out.write("&attributeIDs=");
/* 6390 */                                       out.print(attributeid);
/* 6391 */                                       out.write("&attributeToSelect=");
/* 6392 */                                       out.print(attributeid);
/* 6393 */                                       out.write("&global=true&redirectto=");
/* 6394 */                                       out.print(URLEncoder.encode(redirect));
/* 6395 */                                       out.write("&isAnomaly=true','','resizable=yes,scrollbars=yes,width=800,height=440')\" class=\"widget-links\">");
/* 6396 */                                       out.print(anmname);
/* 6397 */                                       out.write("</a></td>\n             ");
/*      */                                     } else {
/* 6399 */                                       out.write("\n             <td width=\"15%\" class=\"");
/* 6400 */                                       out.print(bgcolour);
/* 6401 */                                       out.write("\"><a href=\"javascript:MM_openBrWindow('/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 6402 */                                       out.print(resourceid);
/* 6403 */                                       out.write("&attributeIDs=");
/* 6404 */                                       out.print(attributeid);
/* 6405 */                                       out.write("&attributeToSelect=");
/* 6406 */                                       out.print(attributeid);
/* 6407 */                                       out.write("&global=true&redirectto=");
/* 6408 */                                       out.print(URLEncoder.encode(redirect));
/* 6409 */                                       out.write("&isAnomaly=true','','resizable=yes,scrollbars=yes,width=800,height=440')\" class=\"widget-links\">");
/* 6410 */                                       out.print(anmname);
/* 6411 */                                       out.write("</a></td>\n             ");
/*      */                                     }
/* 6413 */                                     out.write("\n           </tr>\n\t  ");
/*      */                                   }
/* 6415 */                                   out.write("\n\t\t</table>\n        </div>\n        </td></tr>\n        ");
/*      */                                 }
/* 6417 */                                 out.write("\n        </table>\n\t\t");
/*      */                               }
/*      */                               else
/*      */                               {
/* 6421 */                                 out.write("\n\t\t\t<table class=\"lrtborder\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n\t\t\t<tr class=\"tableheadingbborder\"><td  style=\"padding-left:4px;\">");
/* 6422 */                                 out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/* 6423 */                                 out.write("</td></tr>\n\t\t\t<tr>\n\t\t\t<td height=\"40\" class=\"whitegrayrightalign\" style=\"padding-left:6px;\">");
/* 6424 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text"));
/* 6425 */                                 out.write("</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t");
/*      */                               }
/* 6427 */                               out.write("\n\t\t</div>\n\t\t<div id=\"thresholdsInGroup\" style=\"display: none;\">\n\t\t\t\n\t\t</div>\n\t");
/*      */                             }
/*      */                             else
/*      */                             {
/* 6431 */                               String resid = request.getParameter("resourceid");
/* 6432 */                               if (resid == null)
/*      */                               {
/* 6434 */                                 resid = ((AMActionForm)request.getAttribute("AMActionForm")).getResourceid();
/*      */                               }
/*      */                               
/* 6437 */                               out.write(10);
/* 6438 */                               out.write(9);
/* 6439 */                               out.write(9);
/* 6440 */                               JspRuntimeLibrary.include(request, response, "/jsp/MonitorThresholdConfiguration.jsp", out, false);
/* 6441 */                               out.write(10);
/*      */                             }
/*      */                             
/*      */ 
/* 6445 */                             out.write(10);
/* 6446 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f22.doAfterBody();
/* 6447 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 6451 */                         if (_jspx_th_c_005fwhen_005f22.doEndTag() == 5) {
/* 6452 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22); return;
/*      */                         }
/*      */                         
/* 6455 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22);
/* 6456 */                         out.write(10);
/* 6457 */                         if (_jspx_meth_c_005fotherwise_005f22(_jspx_th_c_005fchoose_005f22, _jspx_page_context))
/*      */                           return;
/* 6459 */                         out.write(10);
/* 6460 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f22.doAfterBody();
/* 6461 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 6465 */                     if (_jspx_th_c_005fchoose_005f22.doEndTag() == 5) {
/* 6466 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22); return;
/*      */                     }
/*      */                     
/* 6469 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22);
/* 6470 */                     out.write(10);
/* 6471 */                     out.write(10);
/*      */                     
/* 6473 */                     IfTag _jspx_th_c_005fif_005f37 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6474 */                     _jspx_th_c_005fif_005f37.setPageContext(_jspx_page_context);
/* 6475 */                     _jspx_th_c_005fif_005f37.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 6477 */                     _jspx_th_c_005fif_005f37.setTest("${!empty param.wiz}");
/* 6478 */                     int _jspx_eval_c_005fif_005f37 = _jspx_th_c_005fif_005f37.doStartTag();
/* 6479 */                     if (_jspx_eval_c_005fif_005f37 != 0) {
/*      */                       for (;;) {
/* 6481 */                         out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td colspan=\"3\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n  </tr>\n  <tr>\n    <td width=\"2%\" background=\"/images/wiz_bg_graylind.gif\"><img src=\"../images/wiz_startimage_bottom.gif\" width=\"20\" height=\"19\"></td>\n    <td width=\"94%\" background=\"../images/wiz_bg_grayline_bottom.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"19\"></td>\n    <td width=\"4%\" align=\"right\" background=\"../images/wiz_bg_grayline_bottom.gif\"><img src=\"../images/wiz_endicon_bottom.gif\" width=\"32\" height=\"19\"></td>\n  </tr>\n</table>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td width=\"86%\" height=\"26\" align=\"center\" >\n      <!--<input type=\"button\" name=\"xx2\" value=\"Add More..\" class=\"buttons\"  onClick=\"javascript:location.href='/showresource.do?method=associateMonitors&haid=");
/* 6482 */                         if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fif_005f37, _jspx_page_context))
/*      */                           return;
/* 6484 */                         out.write("&wiz=true'\">-->\n      <input type=\"button\" name=\"xx1\" value=\"");
/* 6485 */                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.finish"));
/* 6486 */                         out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:location.href='/showapplication.do?method=showApplication&haid=");
/* 6487 */                         if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fif_005f37, _jspx_page_context))
/*      */                           return;
/* 6489 */                         out.write("'\"> </td>\n  </tr>\n</table>\n");
/* 6490 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f37.doAfterBody();
/* 6491 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 6495 */                     if (_jspx_th_c_005fif_005f37.doEndTag() == 5) {
/* 6496 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f37); return;
/*      */                     }
/*      */                     
/* 6499 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f37);
/* 6500 */                     int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 6501 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 6505 */                 if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 6506 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                 }
/*      */                 
/* 6509 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 6510 */                 out.write(32);
/* 6511 */                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f7.doAfterBody();
/* 6512 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 6515 */               if (_jspx_eval_tiles_005fput_005f7 != 1) {
/* 6516 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 6519 */             if (_jspx_th_tiles_005fput_005f7.doEndTag() == 5) {
/* 6520 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f7); return;
/*      */             }
/*      */             
/* 6523 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f7);
/* 6524 */             if (_jspx_meth_tiles_005fput_005f8(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/* 6526 */             out.write(32);
/* 6527 */             int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 6528 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 6532 */         if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 6533 */           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */         }
/*      */         
/* 6536 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 6537 */         out.write("\n\n\n</body>\n</html>\n");
/*      */ 
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 6542 */         ex.printStackTrace();
/*      */       }
/*      */       
/* 6545 */       out.write(10);
/* 6546 */       out.write(10);
/*      */     } catch (Throwable t) {
/* 6548 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 6549 */         out = _jspx_out;
/* 6550 */         if ((out != null) && (out.getBufferSize() != 0))
/* 6551 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 6552 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 6555 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6561 */     PageContext pageContext = _jspx_page_context;
/* 6562 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6564 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6565 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 6566 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6568 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 6570 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 6571 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 6572 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 6573 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6574 */       return true;
/*      */     }
/* 6576 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6577 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6582 */     PageContext pageContext = _jspx_page_context;
/* 6583 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6585 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6586 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 6587 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 6589 */     _jspx_th_c_005fif_005f1.setTest("${param.method!='showApplication' && param.method!='showSnapshot' && param.method!='getHAProfiles' && param.method!='associateMonitors' && param.method!='getMonitorForm'}");
/* 6590 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 6591 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 6593 */         out.write(" \n\t  \t<br>\n\t  ");
/* 6594 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 6595 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6599 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 6600 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6601 */       return true;
/*      */     }
/* 6603 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6604 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6609 */     PageContext pageContext = _jspx_page_context;
/* 6610 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6612 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6613 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 6614 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 6616 */     _jspx_th_c_005fif_005f2.setTest("${param.method!='showApplication'}");
/* 6617 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 6618 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 6620 */         out.write("  \n           <a href=\"/showapplication.do?haid=");
/* 6621 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 6622 */           return true;
/* 6623 */         out.write("&method=showApplication\" \n            class=\"new-left-links\"> ");
/* 6624 */         if (_jspx_meth_am_005fTruncate_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 6625 */           return true;
/* 6626 */         out.write("</a>");
/* 6627 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 6628 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6632 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 6633 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6634 */       return true;
/*      */     }
/* 6636 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6642 */     PageContext pageContext = _jspx_page_context;
/* 6643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6645 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6646 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 6647 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6649 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 6650 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 6651 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 6652 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6653 */       return true;
/*      */     }
/* 6655 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6656 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fTruncate_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6661 */     PageContext pageContext = _jspx_page_context;
/* 6662 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6664 */     Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 6665 */     _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 6666 */     _jspx_th_am_005fTruncate_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6668 */     _jspx_th_am_005fTruncate_005f0.setLength(25);
/* 6669 */     int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 6670 */     if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 6671 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 6672 */         out = _jspx_page_context.pushBody();
/* 6673 */         _jspx_th_am_005fTruncate_005f0.setBodyContent((BodyContent)out);
/* 6674 */         _jspx_th_am_005fTruncate_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6677 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_am_005fTruncate_005f0, _jspx_page_context))
/* 6678 */           return true;
/* 6679 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 6680 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6683 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 6684 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6687 */     if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 6688 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 6689 */       return true;
/*      */     }
/* 6691 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 6692 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_am_005fTruncate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6697 */     PageContext pageContext = _jspx_page_context;
/* 6698 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6700 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6701 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 6702 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_am_005fTruncate_005f0);
/*      */     
/* 6704 */     _jspx_th_c_005fout_005f1.setValue("${applicationScope.applications[param.haid]}");
/* 6705 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 6706 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 6707 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6708 */       return true;
/*      */     }
/* 6710 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6711 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6716 */     PageContext pageContext = _jspx_page_context;
/* 6717 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6719 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6720 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 6721 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 6723 */     _jspx_th_c_005fif_005f3.setTest("${param.method=='showApplication'}");
/* 6724 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 6725 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 6727 */         out.write(" \n          ");
/* 6728 */         if (_jspx_meth_am_005fTruncate_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 6729 */           return true;
/* 6730 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 6731 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6735 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 6736 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 6737 */       return true;
/*      */     }
/* 6739 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 6740 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fTruncate_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6745 */     PageContext pageContext = _jspx_page_context;
/* 6746 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6748 */     Truncate _jspx_th_am_005fTruncate_005f1 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 6749 */     _jspx_th_am_005fTruncate_005f1.setPageContext(_jspx_page_context);
/* 6750 */     _jspx_th_am_005fTruncate_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6752 */     _jspx_th_am_005fTruncate_005f1.setLength(25);
/* 6753 */     int _jspx_eval_am_005fTruncate_005f1 = _jspx_th_am_005fTruncate_005f1.doStartTag();
/* 6754 */     if (_jspx_eval_am_005fTruncate_005f1 != 0) {
/* 6755 */       if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 6756 */         out = _jspx_page_context.pushBody();
/* 6757 */         _jspx_th_am_005fTruncate_005f1.setBodyContent((BodyContent)out);
/* 6758 */         _jspx_th_am_005fTruncate_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6761 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_am_005fTruncate_005f1, _jspx_page_context))
/* 6762 */           return true;
/* 6763 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f1.doAfterBody();
/* 6764 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6767 */       if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 6768 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6771 */     if (_jspx_th_am_005fTruncate_005f1.doEndTag() == 5) {
/* 6772 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f1);
/* 6773 */       return true;
/*      */     }
/* 6775 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f1);
/* 6776 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_am_005fTruncate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6781 */     PageContext pageContext = _jspx_page_context;
/* 6782 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6784 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6785 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 6786 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_am_005fTruncate_005f1);
/*      */     
/* 6788 */     _jspx_th_c_005fout_005f2.setValue("${applicationScope.applications[param.haid]}");
/* 6789 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 6790 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 6791 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6792 */       return true;
/*      */     }
/* 6794 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6795 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6800 */     PageContext pageContext = _jspx_page_context;
/* 6801 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6803 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6804 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 6805 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6807 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 6808 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 6809 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 6810 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6811 */       return true;
/*      */     }
/* 6813 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6814 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6819 */     PageContext pageContext = _jspx_page_context;
/* 6820 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6822 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6823 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6824 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6826 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 6827 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 6828 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 6829 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6830 */       return true;
/*      */     }
/* 6832 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6833 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6838 */     PageContext pageContext = _jspx_page_context;
/* 6839 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6841 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6842 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6843 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 6845 */     _jspx_th_c_005fout_005f5.setValue("${param.haid}");
/* 6846 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6847 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6848 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6849 */       return true;
/*      */     }
/* 6851 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6852 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6857 */     PageContext pageContext = _jspx_page_context;
/* 6858 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6860 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6861 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 6862 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 6864 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 6865 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 6866 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 6867 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6868 */       return true;
/*      */     }
/* 6870 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6871 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6876 */     PageContext pageContext = _jspx_page_context;
/* 6877 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6879 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6880 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 6881 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 6883 */     _jspx_th_c_005fout_005f7.setValue("${param.haid}");
/* 6884 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 6885 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 6886 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6887 */       return true;
/*      */     }
/* 6889 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6890 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6895 */     PageContext pageContext = _jspx_page_context;
/* 6896 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6898 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6899 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 6900 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 6902 */     _jspx_th_c_005fout_005f8.setValue("${param.haid}");
/* 6903 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 6904 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 6905 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6906 */       return true;
/*      */     }
/* 6908 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6909 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6914 */     PageContext pageContext = _jspx_page_context;
/* 6915 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6917 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6918 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 6919 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 6921 */     _jspx_th_c_005fout_005f9.setValue("${param.haid}");
/* 6922 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 6923 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 6924 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6925 */       return true;
/*      */     }
/* 6927 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6933 */     PageContext pageContext = _jspx_page_context;
/* 6934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6936 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 6937 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 6938 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 6940 */     _jspx_th_c_005fout_005f10.setValue("${selectedskin}");
/*      */     
/* 6942 */     _jspx_th_c_005fout_005f10.setDefault("${initParam.defaultSkin}");
/* 6943 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 6944 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 6945 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6946 */       return true;
/*      */     }
/* 6948 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6949 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6954 */     PageContext pageContext = _jspx_page_context;
/* 6955 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6957 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6958 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 6959 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6961 */     _jspx_th_tiles_005fput_005f3.setName("Header");
/*      */     
/* 6963 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/header.jsp?tabtoselect=3");
/* 6964 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 6965 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 6966 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 6967 */       return true;
/*      */     }
/* 6969 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 6970 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_c_005fotherwise_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6975 */     PageContext pageContext = _jspx_page_context;
/* 6976 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6978 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6979 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 6980 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f16);
/*      */     
/* 6982 */     _jspx_th_tiles_005fput_005f5.setName("Header");
/*      */     
/* 6984 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/header.jsp?tabtoselect=1");
/* 6985 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 6986 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 6987 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 6988 */       return true;
/*      */     }
/* 6990 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 6991 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_tiles_005fput_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6996 */     PageContext pageContext = _jspx_page_context;
/* 6997 */     JspWriter out = _jspx_page_context.getOut();
/* 6998 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 6999 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 7001 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7002 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 7003 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f6);
/*      */     
/* 7005 */     _jspx_th_c_005fif_005f15.setTest("${empty appservers}");
/* 7006 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 7007 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 7009 */         out.write(10);
/* 7010 */         JspRuntimeLibrary.include(request, response, "/showresource.do?method=showResourceTypes&includeleftmenu=true", out, false);
/* 7011 */         out.write(10);
/* 7012 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 7013 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7017 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 7018 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 7019 */       return true;
/*      */     }
/* 7021 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 7022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_tiles_005fput_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7027 */     PageContext pageContext = _jspx_page_context;
/* 7028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7030 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7031 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 7032 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_tiles_005fput_005f6);
/*      */     
/* 7034 */     _jspx_th_c_005fif_005f16.setTest("${!empty param.haid}");
/* 7035 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 7036 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 7038 */         out.write(10);
/* 7039 */         if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 7040 */           return true;
/* 7041 */         out.write("\n      ");
/* 7042 */         if (_jspx_meth_c_005fif_005f17(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 7043 */           return true;
/* 7044 */         out.write(10);
/* 7045 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 7046 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7050 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 7051 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 7052 */       return true;
/*      */     }
/* 7054 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 7055 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7060 */     PageContext pageContext = _jspx_page_context;
/* 7061 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7063 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 7064 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 7065 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 7067 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 7068 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 7070 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 7071 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 7073 */           out.write("\n      ");
/* 7074 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 7075 */             return true;
/* 7076 */           out.write("\n      ");
/* 7077 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 7078 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 7082 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 7083 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 7086 */         int tmp185_184 = 0; int[] tmp185_182 = _jspx_push_body_count_c_005fcatch_005f0; int tmp187_186 = tmp185_182[tmp185_184];tmp185_182[tmp185_184] = (tmp187_186 - 1); if (tmp187_186 <= 0) break;
/* 7087 */         out = _jspx_page_context.popBody(); }
/* 7088 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 7090 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 7091 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 7093 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 7098 */     PageContext pageContext = _jspx_page_context;
/* 7099 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7101 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 7102 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 7103 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 7105 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 7107 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 7108 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 7109 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 7110 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 7111 */       return true;
/*      */     }
/* 7113 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 7114 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7119 */     PageContext pageContext = _jspx_page_context;
/* 7120 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7122 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7123 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 7124 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 7126 */     _jspx_th_c_005fif_005f17.setTest("${(empty invalidhaid)}");
/* 7127 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 7128 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 7130 */         out.write(10);
/* 7131 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f17, _jspx_page_context))
/* 7132 */           return true;
/* 7133 */         out.write(10);
/* 7134 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 7135 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7139 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 7140 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 7141 */       return true;
/*      */     }
/* 7143 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 7144 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7149 */     PageContext pageContext = _jspx_page_context;
/* 7150 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7152 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 7153 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 7154 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 7156 */     _jspx_th_c_005fset_005f0.setVar("haid");
/* 7157 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 7158 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 7159 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 7160 */         out = _jspx_page_context.pushBody();
/* 7161 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 7162 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7165 */         out.write(10);
/* 7166 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 7167 */           return true;
/* 7168 */         out.write(10);
/* 7169 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 7170 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7173 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 7174 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7177 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 7178 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 7179 */       return true;
/*      */     }
/* 7181 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 7182 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7187 */     PageContext pageContext = _jspx_page_context;
/* 7188 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7190 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7191 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 7192 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 7194 */     _jspx_th_c_005fout_005f11.setValue("${param.haid}");
/* 7195 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 7196 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 7197 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 7198 */       return true;
/*      */     }
/* 7200 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 7201 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_tiles_005fput_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7206 */     PageContext pageContext = _jspx_page_context;
/* 7207 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7209 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 7210 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 7211 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f7);
/*      */     
/* 7213 */     _jspx_th_c_005fforEach_005f0.setItems("${dynamicSites}");
/*      */     
/* 7215 */     _jspx_th_c_005fforEach_005f0.setVar("a");
/*      */     
/* 7217 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowCounter");
/* 7218 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 7220 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 7221 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 7223 */           out.write(10);
/* 7224 */           out.write(9);
/* 7225 */           out.write(9);
/* 7226 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 7227 */             return true;
/* 7228 */           out.write("\n\t\tif(formCustomerId == customerId)\n\t\t{\n\t\t\tdocument.AMActionForm.siteName.options[rowCount++] = new Option(site,siteId);\n\t\t}\n\n\t\t");
/* 7229 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 7230 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 7234 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 7235 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 7238 */         int tmp214_213 = 0; int[] tmp214_211 = _jspx_push_body_count_c_005fforEach_005f0; int tmp216_215 = tmp214_211[tmp214_213];tmp214_211[tmp214_213] = (tmp216_215 - 1); if (tmp216_215 <= 0) break;
/* 7239 */         out = _jspx_page_context.popBody(); }
/* 7240 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 7242 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 7243 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 7245 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7250 */     PageContext pageContext = _jspx_page_context;
/* 7251 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7253 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 7254 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 7255 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 7257 */     _jspx_th_c_005fforEach_005f1.setItems("${a}");
/*      */     
/* 7259 */     _jspx_th_c_005fforEach_005f1.setVar("b");
/*      */     
/* 7261 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowCounter1");
/* 7262 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 7264 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 7265 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 7267 */           out.write("\n\t\t\t");
/* 7268 */           boolean bool; if (_jspx_meth_c_005fif_005f26(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7269 */             return true;
/* 7270 */           out.write("\n\t\t\t");
/* 7271 */           if (_jspx_meth_c_005fif_005f27(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7272 */             return true;
/* 7273 */           out.write("\n\t\t\t");
/* 7274 */           if (_jspx_meth_c_005fif_005f28(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7275 */             return true;
/* 7276 */           out.write(10);
/* 7277 */           out.write(9);
/* 7278 */           out.write(9);
/* 7279 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 7280 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 7284 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 7285 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 7288 */         int tmp295_294 = 0; int[] tmp295_292 = _jspx_push_body_count_c_005fforEach_005f1; int tmp297_296 = tmp295_292[tmp295_294];tmp295_292[tmp295_294] = (tmp297_296 - 1); if (tmp297_296 <= 0) break;
/* 7289 */         out = _jspx_page_context.popBody(); }
/* 7290 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 7292 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 7293 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 7295 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f26(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7300 */     PageContext pageContext = _jspx_page_context;
/* 7301 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7303 */     IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7304 */     _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/* 7305 */     _jspx_th_c_005fif_005f26.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7307 */     _jspx_th_c_005fif_005f26.setTest("${rowCounter1.count == 1}");
/* 7308 */     int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/* 7309 */     if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */       for (;;) {
/* 7311 */         out.write("\n\t\t\t\tsite = '");
/* 7312 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fif_005f26, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7313 */           return true;
/* 7314 */         out.write("';\n\t\t\t");
/* 7315 */         int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/* 7316 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7320 */     if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/* 7321 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 7322 */       return true;
/*      */     }
/* 7324 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 7325 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7330 */     PageContext pageContext = _jspx_page_context;
/* 7331 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7333 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7334 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 7335 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7337 */     _jspx_th_c_005fout_005f12.setValue("${b}");
/* 7338 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 7339 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 7340 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 7341 */       return true;
/*      */     }
/* 7343 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 7344 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f27(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7349 */     PageContext pageContext = _jspx_page_context;
/* 7350 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7352 */     IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7353 */     _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/* 7354 */     _jspx_th_c_005fif_005f27.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7356 */     _jspx_th_c_005fif_005f27.setTest("${rowCounter1.count == 2}");
/* 7357 */     int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/* 7358 */     if (_jspx_eval_c_005fif_005f27 != 0) {
/*      */       for (;;) {
/* 7360 */         out.write("\n\t\t\t\tsiteId = '");
/* 7361 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fif_005f27, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7362 */           return true;
/* 7363 */         out.write("';\n\t\t\t");
/* 7364 */         int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/* 7365 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7369 */     if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/* 7370 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 7371 */       return true;
/*      */     }
/* 7373 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 7374 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fif_005f27, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7379 */     PageContext pageContext = _jspx_page_context;
/* 7380 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7382 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7383 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 7384 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fif_005f27);
/*      */     
/* 7386 */     _jspx_th_c_005fout_005f13.setValue("${b}");
/* 7387 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 7388 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 7389 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 7390 */       return true;
/*      */     }
/* 7392 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 7393 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f28(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7398 */     PageContext pageContext = _jspx_page_context;
/* 7399 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7401 */     IfTag _jspx_th_c_005fif_005f28 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7402 */     _jspx_th_c_005fif_005f28.setPageContext(_jspx_page_context);
/* 7403 */     _jspx_th_c_005fif_005f28.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7405 */     _jspx_th_c_005fif_005f28.setTest("${rowCounter1.count == 3}");
/* 7406 */     int _jspx_eval_c_005fif_005f28 = _jspx_th_c_005fif_005f28.doStartTag();
/* 7407 */     if (_jspx_eval_c_005fif_005f28 != 0) {
/*      */       for (;;) {
/* 7409 */         out.write("\n\t\t\t\tcustomerId = '");
/* 7410 */         if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fif_005f28, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7411 */           return true;
/* 7412 */         out.write("';\n\t\t\t");
/* 7413 */         int evalDoAfterBody = _jspx_th_c_005fif_005f28.doAfterBody();
/* 7414 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7418 */     if (_jspx_th_c_005fif_005f28.doEndTag() == 5) {
/* 7419 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 7420 */       return true;
/*      */     }
/* 7422 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 7423 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fif_005f28, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7428 */     PageContext pageContext = _jspx_page_context;
/* 7429 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7431 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7432 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 7433 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fif_005f28);
/*      */     
/* 7435 */     _jspx_th_c_005fout_005f14.setValue("${b}");
/* 7436 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 7437 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 7438 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 7439 */       return true;
/*      */     }
/* 7441 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 7442 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f17(JspTag _jspx_th_tiles_005fput_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7447 */     PageContext pageContext = _jspx_page_context;
/* 7448 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7450 */     ChooseTag _jspx_th_c_005fchoose_005f17 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 7451 */     _jspx_th_c_005fchoose_005f17.setPageContext(_jspx_page_context);
/* 7452 */     _jspx_th_c_005fchoose_005f17.setParent((Tag)_jspx_th_tiles_005fput_005f7);
/* 7453 */     int _jspx_eval_c_005fchoose_005f17 = _jspx_th_c_005fchoose_005f17.doStartTag();
/* 7454 */     if (_jspx_eval_c_005fchoose_005f17 != 0) {
/*      */       for (;;) {
/* 7456 */         out.write(10);
/* 7457 */         out.write(9);
/* 7458 */         out.write(9);
/* 7459 */         if (_jspx_meth_c_005fwhen_005f17(_jspx_th_c_005fchoose_005f17, _jspx_page_context))
/* 7460 */           return true;
/* 7461 */         out.write(10);
/* 7462 */         out.write(9);
/* 7463 */         out.write(9);
/* 7464 */         if (_jspx_meth_c_005fotherwise_005f17(_jspx_th_c_005fchoose_005f17, _jspx_page_context))
/* 7465 */           return true;
/* 7466 */         out.write(10);
/* 7467 */         out.write(9);
/* 7468 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f17.doAfterBody();
/* 7469 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7473 */     if (_jspx_th_c_005fchoose_005f17.doEndTag() == 5) {
/* 7474 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 7475 */       return true;
/*      */     }
/* 7477 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 7478 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f17(JspTag _jspx_th_c_005fchoose_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7483 */     PageContext pageContext = _jspx_page_context;
/* 7484 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7486 */     WhenTag _jspx_th_c_005fwhen_005f17 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 7487 */     _jspx_th_c_005fwhen_005f17.setPageContext(_jspx_page_context);
/* 7488 */     _jspx_th_c_005fwhen_005f17.setParent((Tag)_jspx_th_c_005fchoose_005f17);
/*      */     
/* 7490 */     _jspx_th_c_005fwhen_005f17.setTest("${not empty isAdminServer && isAdminServer}");
/* 7491 */     int _jspx_eval_c_005fwhen_005f17 = _jspx_th_c_005fwhen_005f17.doStartTag();
/* 7492 */     if (_jspx_eval_c_005fwhen_005f17 != 0) {
/*      */       for (;;) {
/* 7494 */         out.write("\n\t\t\tif(!selectedValue){\n\t\t\t\tselectedValue = 'monitorGroups';\t\t// NO I18N \n\t\t\t}\n\t\t\tmgactionTemplate('template')\t\t// NO I18N\n\t\t");
/* 7495 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f17.doAfterBody();
/* 7496 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7500 */     if (_jspx_th_c_005fwhen_005f17.doEndTag() == 5) {
/* 7501 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 7502 */       return true;
/*      */     }
/* 7504 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 7505 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f17(JspTag _jspx_th_c_005fchoose_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7510 */     PageContext pageContext = _jspx_page_context;
/* 7511 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7513 */     OtherwiseTag _jspx_th_c_005fotherwise_005f17 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 7514 */     _jspx_th_c_005fotherwise_005f17.setPageContext(_jspx_page_context);
/* 7515 */     _jspx_th_c_005fotherwise_005f17.setParent((Tag)_jspx_th_c_005fchoose_005f17);
/* 7516 */     int _jspx_eval_c_005fotherwise_005f17 = _jspx_th_c_005fotherwise_005f17.doStartTag();
/* 7517 */     if (_jspx_eval_c_005fotherwise_005f17 != 0) {
/*      */       for (;;) {
/* 7519 */         out.write("\n\t\t\tjQuery(\"#mgtemplate_monitortype\").hide();\t\t// NO I18N \n\t\t");
/* 7520 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f17.doAfterBody();
/* 7521 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7525 */     if (_jspx_th_c_005fotherwise_005f17.doEndTag() == 5) {
/* 7526 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 7527 */       return true;
/*      */     }
/* 7529 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 7530 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fif_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7535 */     PageContext pageContext = _jspx_page_context;
/* 7536 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7538 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7539 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 7540 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fif_005f30);
/*      */     
/* 7542 */     _jspx_th_c_005fout_005f15.setValue("${param.haid}");
/* 7543 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 7544 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 7545 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 7546 */       return true;
/*      */     }
/* 7548 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 7549 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fif_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7554 */     PageContext pageContext = _jspx_page_context;
/* 7555 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7557 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7558 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 7559 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fif_005f30);
/*      */     
/* 7561 */     _jspx_th_c_005fout_005f16.setValue("${param.haid}");
/* 7562 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 7563 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 7564 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 7565 */       return true;
/*      */     }
/* 7567 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 7568 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7573 */     PageContext pageContext = _jspx_page_context;
/* 7574 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7576 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 7577 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 7578 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_c_005fif_005f31);
/*      */     
/* 7580 */     _jspx_th_html_005fradio_005f0.setProperty("viewBy");
/*      */     
/* 7582 */     _jspx_th_html_005fradio_005f0.setValue("monitors");
/*      */     
/* 7584 */     _jspx_th_html_005fradio_005f0.setOnclick("javascript:onfnSubmit(this);");
/* 7585 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 7586 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 7587 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 7588 */       return true;
/*      */     }
/* 7590 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 7591 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7596 */     PageContext pageContext = _jspx_page_context;
/* 7597 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7599 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 7600 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 7601 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_c_005fif_005f31);
/*      */     
/* 7603 */     _jspx_th_html_005fradio_005f1.setProperty("viewBy");
/*      */     
/* 7605 */     _jspx_th_html_005fradio_005f1.setValue("monitorGroups");
/*      */     
/* 7607 */     _jspx_th_html_005fradio_005f1.setOnclick("javascript:onfnSubmit(this);");
/* 7608 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 7609 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 7610 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 7611 */       return true;
/*      */     }
/* 7613 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 7614 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7619 */     PageContext pageContext = _jspx_page_context;
/* 7620 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7622 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 7623 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 7624 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_c_005fif_005f31);
/*      */     
/* 7626 */     _jspx_th_html_005fradio_005f2.setProperty("viewBy");
/*      */     
/* 7628 */     _jspx_th_html_005fradio_005f2.setValue("monitorType");
/*      */     
/* 7630 */     _jspx_th_html_005fradio_005f2.setOnclick("javascript:setDefaultType(this);");
/* 7631 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 7632 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 7633 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 7634 */       return true;
/*      */     }
/* 7636 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 7637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7642 */     PageContext pageContext = _jspx_page_context;
/* 7643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7645 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 7646 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 7647 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_c_005fif_005f31);
/*      */     
/* 7649 */     _jspx_th_html_005fradio_005f3.setProperty("viewBy");
/*      */     
/* 7651 */     _jspx_th_html_005fradio_005f3.setValue("monitorType");
/*      */     
/* 7653 */     _jspx_th_html_005fradio_005f3.setOnclick("javascript:onfnSubmit(this);");
/* 7654 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 7655 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 7656 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 7657 */       return true;
/*      */     }
/* 7659 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 7660 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f18(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7665 */     PageContext pageContext = _jspx_page_context;
/* 7666 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7668 */     ChooseTag _jspx_th_c_005fchoose_005f18 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 7669 */     _jspx_th_c_005fchoose_005f18.setPageContext(_jspx_page_context);
/* 7670 */     _jspx_th_c_005fchoose_005f18.setParent((Tag)_jspx_th_c_005fif_005f31);
/* 7671 */     int _jspx_eval_c_005fchoose_005f18 = _jspx_th_c_005fchoose_005f18.doStartTag();
/* 7672 */     if (_jspx_eval_c_005fchoose_005f18 != 0) {
/*      */       for (;;) {
/* 7674 */         out.write("\n\t  ");
/* 7675 */         if (_jspx_meth_c_005fwhen_005f18(_jspx_th_c_005fchoose_005f18, _jspx_page_context))
/* 7676 */           return true;
/* 7677 */         out.write(" \n\t  ");
/* 7678 */         if (_jspx_meth_c_005fotherwise_005f18(_jspx_th_c_005fchoose_005f18, _jspx_page_context))
/* 7679 */           return true;
/* 7680 */         out.write(10);
/* 7681 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f18.doAfterBody();
/* 7682 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7686 */     if (_jspx_th_c_005fchoose_005f18.doEndTag() == 5) {
/* 7687 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18);
/* 7688 */       return true;
/*      */     }
/* 7690 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18);
/* 7691 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f18(JspTag _jspx_th_c_005fchoose_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7696 */     PageContext pageContext = _jspx_page_context;
/* 7697 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7699 */     WhenTag _jspx_th_c_005fwhen_005f18 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 7700 */     _jspx_th_c_005fwhen_005f18.setPageContext(_jspx_page_context);
/* 7701 */     _jspx_th_c_005fwhen_005f18.setParent((Tag)_jspx_th_c_005fchoose_005f18);
/*      */     
/* 7703 */     _jspx_th_c_005fwhen_005f18.setTest("${(viewBy == 'monitorGroups') || (!showMonType)}");
/* 7704 */     int _jspx_eval_c_005fwhen_005f18 = _jspx_th_c_005fwhen_005f18.doStartTag();
/* 7705 */     if (_jspx_eval_c_005fwhen_005f18 != 0) {
/*      */       for (;;) {
/* 7707 */         out.write(" \n\t\t <tr   id=\"view_monitors\" style=\"display:none;\" >\n\t  ");
/* 7708 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f18.doAfterBody();
/* 7709 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7713 */     if (_jspx_th_c_005fwhen_005f18.doEndTag() == 5) {
/* 7714 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 7715 */       return true;
/*      */     }
/* 7717 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 7718 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f18(JspTag _jspx_th_c_005fchoose_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7723 */     PageContext pageContext = _jspx_page_context;
/* 7724 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7726 */     OtherwiseTag _jspx_th_c_005fotherwise_005f18 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 7727 */     _jspx_th_c_005fotherwise_005f18.setPageContext(_jspx_page_context);
/* 7728 */     _jspx_th_c_005fotherwise_005f18.setParent((Tag)_jspx_th_c_005fchoose_005f18);
/* 7729 */     int _jspx_eval_c_005fotherwise_005f18 = _jspx_th_c_005fotherwise_005f18.doStartTag();
/* 7730 */     if (_jspx_eval_c_005fotherwise_005f18 != 0) {
/*      */       for (;;) {
/* 7732 */         out.write("\n\t\t<tr  id=\"view_monitors\">\n\t  ");
/* 7733 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f18.doAfterBody();
/* 7734 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7738 */     if (_jspx_th_c_005fotherwise_005f18.doEndTag() == 5) {
/* 7739 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18);
/* 7740 */       return true;
/*      */     }
/* 7742 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18);
/* 7743 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f19(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7748 */     PageContext pageContext = _jspx_page_context;
/* 7749 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7751 */     ChooseTag _jspx_th_c_005fchoose_005f19 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 7752 */     _jspx_th_c_005fchoose_005f19.setPageContext(_jspx_page_context);
/* 7753 */     _jspx_th_c_005fchoose_005f19.setParent((Tag)_jspx_th_c_005fif_005f31);
/* 7754 */     int _jspx_eval_c_005fchoose_005f19 = _jspx_th_c_005fchoose_005f19.doStartTag();
/* 7755 */     if (_jspx_eval_c_005fchoose_005f19 != 0) {
/*      */       for (;;) {
/* 7757 */         if (_jspx_meth_c_005fwhen_005f19(_jspx_th_c_005fchoose_005f19, _jspx_page_context))
/* 7758 */           return true;
/* 7759 */         out.write("\n            ");
/* 7760 */         if (_jspx_meth_c_005fotherwise_005f19(_jspx_th_c_005fchoose_005f19, _jspx_page_context))
/* 7761 */           return true;
/* 7762 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f19.doAfterBody();
/* 7763 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7767 */     if (_jspx_th_c_005fchoose_005f19.doEndTag() == 5) {
/* 7768 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19);
/* 7769 */       return true;
/*      */     }
/* 7771 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19);
/* 7772 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f19(JspTag _jspx_th_c_005fchoose_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7777 */     PageContext pageContext = _jspx_page_context;
/* 7778 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7780 */     WhenTag _jspx_th_c_005fwhen_005f19 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 7781 */     _jspx_th_c_005fwhen_005f19.setPageContext(_jspx_page_context);
/* 7782 */     _jspx_th_c_005fwhen_005f19.setParent((Tag)_jspx_th_c_005fchoose_005f19);
/*      */     
/* 7784 */     _jspx_th_c_005fwhen_005f19.setTest("${viewBy == 'monitorType'}");
/* 7785 */     int _jspx_eval_c_005fwhen_005f19 = _jspx_th_c_005fwhen_005f19.doStartTag();
/* 7786 */     if (_jspx_eval_c_005fwhen_005f19 != 0) {
/*      */       for (;;) {
/* 7788 */         out.write("\n            ");
/* 7789 */         if (_jspx_meth_html_005fselect_005f2(_jspx_th_c_005fwhen_005f19, _jspx_page_context))
/* 7790 */           return true;
/* 7791 */         out.write("\n            ");
/* 7792 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f19.doAfterBody();
/* 7793 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7797 */     if (_jspx_th_c_005fwhen_005f19.doEndTag() == 5) {
/* 7798 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19);
/* 7799 */       return true;
/*      */     }
/* 7801 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19);
/* 7802 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_c_005fwhen_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7807 */     PageContext pageContext = _jspx_page_context;
/* 7808 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7810 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 7811 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 7812 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f19);
/*      */     
/* 7814 */     _jspx_th_html_005fselect_005f2.setProperty("montype");
/*      */     
/* 7816 */     _jspx_th_html_005fselect_005f2.setOnchange("javascript:onfnSubmit(this);");
/*      */     
/* 7818 */     _jspx_th_html_005fselect_005f2.setStyleClass("chzn-select formtext normal");
/*      */     
/* 7820 */     _jspx_th_html_005fselect_005f2.setStyle("width:350px;");
/* 7821 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 7822 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 7823 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 7824 */         out = _jspx_page_context.pushBody();
/* 7825 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 7826 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7829 */         out.write("\n            ");
/* 7830 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 7831 */           return true;
/* 7832 */         out.write(32);
/* 7833 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 7834 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7837 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 7838 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7841 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 7842 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/* 7843 */       return true;
/*      */     }
/* 7845 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/* 7846 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7851 */     PageContext pageContext = _jspx_page_context;
/* 7852 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7854 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7855 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 7856 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 7858 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("availableResourceTypes");
/* 7859 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 7860 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 7861 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 7862 */       return true;
/*      */     }
/* 7864 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 7865 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f19(JspTag _jspx_th_c_005fchoose_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7870 */     PageContext pageContext = _jspx_page_context;
/* 7871 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7873 */     OtherwiseTag _jspx_th_c_005fotherwise_005f19 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 7874 */     _jspx_th_c_005fotherwise_005f19.setPageContext(_jspx_page_context);
/* 7875 */     _jspx_th_c_005fotherwise_005f19.setParent((Tag)_jspx_th_c_005fchoose_005f19);
/* 7876 */     int _jspx_eval_c_005fotherwise_005f19 = _jspx_th_c_005fotherwise_005f19.doStartTag();
/* 7877 */     if (_jspx_eval_c_005fotherwise_005f19 != 0) {
/*      */       for (;;) {
/* 7879 */         out.write("\n            ");
/* 7880 */         if (_jspx_meth_html_005fselect_005f3(_jspx_th_c_005fotherwise_005f19, _jspx_page_context))
/* 7881 */           return true;
/* 7882 */         out.write("\n            ");
/* 7883 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f19.doAfterBody();
/* 7884 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7888 */     if (_jspx_th_c_005fotherwise_005f19.doEndTag() == 5) {
/* 7889 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19);
/* 7890 */       return true;
/*      */     }
/* 7892 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19);
/* 7893 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_c_005fotherwise_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7898 */     PageContext pageContext = _jspx_page_context;
/* 7899 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7901 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 7902 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 7903 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f19);
/*      */     
/* 7905 */     _jspx_th_html_005fselect_005f3.setProperty("montype");
/*      */     
/* 7907 */     _jspx_th_html_005fselect_005f3.setOnchange("javascript:loadMonitor();");
/*      */     
/* 7909 */     _jspx_th_html_005fselect_005f3.setStyleClass("chzn-select formtext normal");
/*      */     
/* 7911 */     _jspx_th_html_005fselect_005f3.setStyle("width:350px;");
/* 7912 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 7913 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 7914 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 7915 */         out = _jspx_page_context.pushBody();
/* 7916 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 7917 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7920 */         out.write("\n            ");
/* 7921 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 7922 */           return true;
/* 7923 */         out.write(32);
/* 7924 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 7925 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7928 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 7929 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7932 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 7933 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3);
/* 7934 */       return true;
/*      */     }
/* 7936 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3);
/* 7937 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7942 */     PageContext pageContext = _jspx_page_context;
/* 7943 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7945 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7946 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 7947 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 7949 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("availableResourceTypes");
/* 7950 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 7951 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 7952 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 7953 */       return true;
/*      */     }
/* 7955 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 7956 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f20(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7961 */     PageContext pageContext = _jspx_page_context;
/* 7962 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7964 */     ChooseTag _jspx_th_c_005fchoose_005f20 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 7965 */     _jspx_th_c_005fchoose_005f20.setPageContext(_jspx_page_context);
/* 7966 */     _jspx_th_c_005fchoose_005f20.setParent((Tag)_jspx_th_c_005fif_005f31);
/* 7967 */     int _jspx_eval_c_005fchoose_005f20 = _jspx_th_c_005fchoose_005f20.doStartTag();
/* 7968 */     if (_jspx_eval_c_005fchoose_005f20 != 0) {
/*      */       for (;;) {
/* 7970 */         out.write(10);
/* 7971 */         out.write(32);
/* 7972 */         out.write(32);
/* 7973 */         if (_jspx_meth_c_005fwhen_005f20(_jspx_th_c_005fchoose_005f20, _jspx_page_context))
/* 7974 */           return true;
/* 7975 */         out.write(" \n\t\t ");
/* 7976 */         if (_jspx_meth_c_005fotherwise_005f20(_jspx_th_c_005fchoose_005f20, _jspx_page_context))
/* 7977 */           return true;
/* 7978 */         out.write("\n\t\t ");
/* 7979 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f20.doAfterBody();
/* 7980 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7984 */     if (_jspx_th_c_005fchoose_005f20.doEndTag() == 5) {
/* 7985 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20);
/* 7986 */       return true;
/*      */     }
/* 7988 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20);
/* 7989 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f20(JspTag _jspx_th_c_005fchoose_005f20, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7994 */     PageContext pageContext = _jspx_page_context;
/* 7995 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7997 */     WhenTag _jspx_th_c_005fwhen_005f20 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 7998 */     _jspx_th_c_005fwhen_005f20.setPageContext(_jspx_page_context);
/* 7999 */     _jspx_th_c_005fwhen_005f20.setParent((Tag)_jspx_th_c_005fchoose_005f20);
/*      */     
/* 8001 */     _jspx_th_c_005fwhen_005f20.setTest("${viewBy == 'monitorGroups' || viewBy=='monitorType'}");
/* 8002 */     int _jspx_eval_c_005fwhen_005f20 = _jspx_th_c_005fwhen_005f20.doStartTag();
/* 8003 */     if (_jspx_eval_c_005fwhen_005f20 != 0) {
/*      */       for (;;) {
/* 8005 */         out.write("  \n\t <tr  id=\"view_monitors1\" style=\"display:none;\" >\n\t");
/* 8006 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f20.doAfterBody();
/* 8007 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8011 */     if (_jspx_th_c_005fwhen_005f20.doEndTag() == 5) {
/* 8012 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20);
/* 8013 */       return true;
/*      */     }
/* 8015 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20);
/* 8016 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f20(JspTag _jspx_th_c_005fchoose_005f20, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8021 */     PageContext pageContext = _jspx_page_context;
/* 8022 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8024 */     OtherwiseTag _jspx_th_c_005fotherwise_005f20 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 8025 */     _jspx_th_c_005fotherwise_005f20.setPageContext(_jspx_page_context);
/* 8026 */     _jspx_th_c_005fotherwise_005f20.setParent((Tag)_jspx_th_c_005fchoose_005f20);
/* 8027 */     int _jspx_eval_c_005fotherwise_005f20 = _jspx_th_c_005fotherwise_005f20.doStartTag();
/* 8028 */     if (_jspx_eval_c_005fotherwise_005f20 != 0) {
/*      */       for (;;) {
/* 8030 */         out.write("\n\t <tr  id=\"view_monitors1\">\n\t\t ");
/* 8031 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f20.doAfterBody();
/* 8032 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8036 */     if (_jspx_th_c_005fotherwise_005f20.doEndTag() == 5) {
/* 8037 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20);
/* 8038 */       return true;
/*      */     }
/* 8040 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20);
/* 8041 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f4(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8046 */     PageContext pageContext = _jspx_page_context;
/* 8047 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8049 */     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 8050 */     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 8051 */     _jspx_th_html_005fselect_005f4.setParent((Tag)_jspx_th_c_005fif_005f31);
/*      */     
/* 8053 */     _jspx_th_html_005fselect_005f4.setProperty("resourceid");
/*      */     
/* 8055 */     _jspx_th_html_005fselect_005f4.setOnchange("javascript:onfnSubmit(this);");
/*      */     
/* 8057 */     _jspx_th_html_005fselect_005f4.setStyleClass("chzn-select1 formtext normal");
/*      */     
/* 8059 */     _jspx_th_html_005fselect_005f4.setStyle("width:350px;");
/* 8060 */     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 8061 */     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 8062 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 8063 */         out = _jspx_page_context.pushBody();
/* 8064 */         _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 8065 */         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8068 */         out.write("\n            ");
/* 8069 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/* 8070 */           return true;
/* 8071 */         out.write(32);
/* 8072 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 8073 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8076 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 8077 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8080 */     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 8081 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4);
/* 8082 */       return true;
/*      */     }
/* 8084 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4);
/* 8085 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8090 */     PageContext pageContext = _jspx_page_context;
/* 8091 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8093 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 8094 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 8095 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 8097 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("availableResources");
/* 8098 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 8099 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 8100 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 8101 */       return true;
/*      */     }
/* 8103 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 8104 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f21(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8109 */     PageContext pageContext = _jspx_page_context;
/* 8110 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8112 */     ChooseTag _jspx_th_c_005fchoose_005f21 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 8113 */     _jspx_th_c_005fchoose_005f21.setPageContext(_jspx_page_context);
/* 8114 */     _jspx_th_c_005fchoose_005f21.setParent((Tag)_jspx_th_c_005fif_005f31);
/* 8115 */     int _jspx_eval_c_005fchoose_005f21 = _jspx_th_c_005fchoose_005f21.doStartTag();
/* 8116 */     if (_jspx_eval_c_005fchoose_005f21 != 0) {
/*      */       for (;;) {
/* 8118 */         out.write("\n  \t\t");
/* 8119 */         if (_jspx_meth_c_005fwhen_005f21(_jspx_th_c_005fchoose_005f21, _jspx_page_context))
/* 8120 */           return true;
/* 8121 */         out.write(" \n\t\t");
/* 8122 */         if (_jspx_meth_c_005fotherwise_005f21(_jspx_th_c_005fchoose_005f21, _jspx_page_context))
/* 8123 */           return true;
/* 8124 */         out.write(10);
/* 8125 */         out.write(9);
/* 8126 */         out.write(32);
/* 8127 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f21.doAfterBody();
/* 8128 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8132 */     if (_jspx_th_c_005fchoose_005f21.doEndTag() == 5) {
/* 8133 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21);
/* 8134 */       return true;
/*      */     }
/* 8136 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21);
/* 8137 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f21(JspTag _jspx_th_c_005fchoose_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8142 */     PageContext pageContext = _jspx_page_context;
/* 8143 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8145 */     WhenTag _jspx_th_c_005fwhen_005f21 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 8146 */     _jspx_th_c_005fwhen_005f21.setPageContext(_jspx_page_context);
/* 8147 */     _jspx_th_c_005fwhen_005f21.setParent((Tag)_jspx_th_c_005fchoose_005f21);
/*      */     
/* 8149 */     _jspx_th_c_005fwhen_005f21.setTest("${viewBy == 'monitors'}");
/* 8150 */     int _jspx_eval_c_005fwhen_005f21 = _jspx_th_c_005fwhen_005f21.doStartTag();
/* 8151 */     if (_jspx_eval_c_005fwhen_005f21 != 0) {
/*      */       for (;;) {
/* 8153 */         out.write("  \n\t \t\t<tr  id=\"viewmonitorgroup\" style=\"display:none;\" >\n\t\t");
/* 8154 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f21.doAfterBody();
/* 8155 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8159 */     if (_jspx_th_c_005fwhen_005f21.doEndTag() == 5) {
/* 8160 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21);
/* 8161 */       return true;
/*      */     }
/* 8163 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21);
/* 8164 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f21(JspTag _jspx_th_c_005fchoose_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8169 */     PageContext pageContext = _jspx_page_context;
/* 8170 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8172 */     OtherwiseTag _jspx_th_c_005fotherwise_005f21 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 8173 */     _jspx_th_c_005fotherwise_005f21.setPageContext(_jspx_page_context);
/* 8174 */     _jspx_th_c_005fotherwise_005f21.setParent((Tag)_jspx_th_c_005fchoose_005f21);
/* 8175 */     int _jspx_eval_c_005fotherwise_005f21 = _jspx_th_c_005fotherwise_005f21.doStartTag();
/* 8176 */     if (_jspx_eval_c_005fotherwise_005f21 != 0) {
/*      */       for (;;) {
/* 8178 */         out.write("\n\t \t\t<tr id=\"viewmonitorgroup\" align=\"left\" >\n\t\t");
/* 8179 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f21.doAfterBody();
/* 8180 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8184 */     if (_jspx_th_c_005fotherwise_005f21.doEndTag() == 5) {
/* 8185 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21);
/* 8186 */       return true;
/*      */     }
/* 8188 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21);
/* 8189 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f5(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8194 */     PageContext pageContext = _jspx_page_context;
/* 8195 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8197 */     SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 8198 */     _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 8199 */     _jspx_th_html_005fselect_005f5.setParent((Tag)_jspx_th_c_005fif_005f31);
/*      */     
/* 8201 */     _jspx_th_html_005fselect_005f5.setProperty("haid");
/*      */     
/* 8203 */     _jspx_th_html_005fselect_005f5.setOnchange("javascript:onfnSubmit(this);");
/*      */     
/* 8205 */     _jspx_th_html_005fselect_005f5.setStyleClass("chzn-select formtext normal");
/*      */     
/* 8207 */     _jspx_th_html_005fselect_005f5.setStyle("width:350px;");
/* 8208 */     int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 8209 */     if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 8210 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 8211 */         out = _jspx_page_context.pushBody();
/* 8212 */         _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 8213 */         _jspx_th_html_005fselect_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8216 */         out.write("\n\t\t\t");
/* 8217 */         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f5, _jspx_page_context))
/* 8218 */           return true;
/* 8219 */         out.write(32);
/* 8220 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 8221 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8224 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 8225 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8228 */     if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 8229 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f5);
/* 8230 */       return true;
/*      */     }
/* 8232 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f5);
/* 8233 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8238 */     PageContext pageContext = _jspx_page_context;
/* 8239 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8241 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005ffilter_005fnobody.get(OptionsCollectionTag.class);
/* 8242 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 8243 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f5);
/*      */     
/* 8245 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("applications");
/*      */     
/* 8247 */     _jspx_th_html_005foptionsCollection_005f3.setFilter(false);
/* 8248 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 8249 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 8250 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005ffilter_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 8251 */       return true;
/*      */     }
/* 8253 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005ffilter_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 8254 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f6(JspTag _jspx_th_c_005fif_005f32, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8259 */     PageContext pageContext = _jspx_page_context;
/* 8260 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8262 */     SelectTag _jspx_th_html_005fselect_005f6 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 8263 */     _jspx_th_html_005fselect_005f6.setPageContext(_jspx_page_context);
/* 8264 */     _jspx_th_html_005fselect_005f6.setParent((Tag)_jspx_th_c_005fif_005f32);
/*      */     
/* 8266 */     _jspx_th_html_005fselect_005f6.setProperty("mgTemplateType");
/*      */     
/* 8268 */     _jspx_th_html_005fselect_005f6.setOnchange("javascript:selectTypeAttribute(this.value);");
/*      */     
/* 8270 */     _jspx_th_html_005fselect_005f6.setStyleClass("chzn-select");
/*      */     
/* 8272 */     _jspx_th_html_005fselect_005f6.setStyle("width:350px;");
/* 8273 */     int _jspx_eval_html_005fselect_005f6 = _jspx_th_html_005fselect_005f6.doStartTag();
/* 8274 */     if (_jspx_eval_html_005fselect_005f6 != 0) {
/* 8275 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 8276 */         out = _jspx_page_context.pushBody();
/* 8277 */         _jspx_th_html_005fselect_005f6.setBodyContent((BodyContent)out);
/* 8278 */         _jspx_th_html_005fselect_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8281 */         out.write("\n\t            ");
/* 8282 */         if (_jspx_meth_html_005foptionsCollection_005f4(_jspx_th_html_005fselect_005f6, _jspx_page_context))
/* 8283 */           return true;
/* 8284 */         out.write(32);
/* 8285 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f6.doAfterBody();
/* 8286 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8289 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 8290 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8293 */     if (_jspx_th_html_005fselect_005f6.doEndTag() == 5) {
/* 8294 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f6);
/* 8295 */       return true;
/*      */     }
/* 8297 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f6);
/* 8298 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f4(JspTag _jspx_th_html_005fselect_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8303 */     PageContext pageContext = _jspx_page_context;
/* 8304 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8306 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f4 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 8307 */     _jspx_th_html_005foptionsCollection_005f4.setPageContext(_jspx_page_context);
/* 8308 */     _jspx_th_html_005foptionsCollection_005f4.setParent((Tag)_jspx_th_html_005fselect_005f6);
/*      */     
/* 8310 */     _jspx_th_html_005foptionsCollection_005f4.setProperty("availableResourceTypes");
/* 8311 */     int _jspx_eval_html_005foptionsCollection_005f4 = _jspx_th_html_005foptionsCollection_005f4.doStartTag();
/* 8312 */     if (_jspx_th_html_005foptionsCollection_005f4.doEndTag() == 5) {
/* 8313 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 8314 */       return true;
/*      */     }
/* 8316 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 8317 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fwhen_005f22, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8322 */     PageContext pageContext = _jspx_page_context;
/* 8323 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8325 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 8326 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 8327 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f22);
/*      */     
/* 8329 */     _jspx_th_c_005fset_005f1.setVar("monitorGroupStyle");
/* 8330 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 8331 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 8332 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 8333 */         out = _jspx_page_context.pushBody();
/* 8334 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 8335 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8338 */         out.write("display:none");
/* 8339 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 8340 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8343 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 8344 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8347 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 8348 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 8349 */       return true;
/*      */     }
/* 8351 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 8352 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f36, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8357 */     PageContext pageContext = _jspx_page_context;
/* 8358 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8360 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 8361 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 8362 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f36);
/*      */     
/* 8364 */     _jspx_th_c_005fset_005f2.setVar("monitorGroupStyle");
/* 8365 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 8366 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 8367 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 8368 */         out = _jspx_page_context.pushBody();
/* 8369 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 8370 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8373 */         out.write("display:block");
/* 8374 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 8375 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8378 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 8379 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8382 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 8383 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 8384 */       return true;
/*      */     }
/* 8386 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 8387 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fwhen_005f22, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8392 */     PageContext pageContext = _jspx_page_context;
/* 8393 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8395 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8396 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 8397 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fwhen_005f22);
/*      */     
/* 8399 */     _jspx_th_c_005fout_005f17.setValue("${monitorGroupStyle}");
/* 8400 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 8401 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 8402 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 8403 */       return true;
/*      */     }
/* 8405 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 8406 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fwhen_005f22, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8411 */     PageContext pageContext = _jspx_page_context;
/* 8412 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8414 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 8415 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 8416 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f22);
/*      */     
/* 8418 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.configurealert.titlealertsformonitors");
/* 8419 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 8420 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 8421 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 8422 */         out = _jspx_page_context.pushBody();
/* 8423 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 8424 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8427 */         if (_jspx_meth_fmt_005fparam_005f0(_jspx_th_fmt_005fmessage_005f0, _jspx_page_context))
/* 8428 */           return true;
/* 8429 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 8430 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8433 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 8434 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8437 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 8438 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 8439 */       return true;
/*      */     }
/* 8441 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 8442 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f0(JspTag _jspx_th_fmt_005fmessage_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8447 */     PageContext pageContext = _jspx_page_context;
/* 8448 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8450 */     ParamTag _jspx_th_fmt_005fparam_005f0 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 8451 */     _jspx_th_fmt_005fparam_005f0.setPageContext(_jspx_page_context);
/* 8452 */     _jspx_th_fmt_005fparam_005f0.setParent((Tag)_jspx_th_fmt_005fmessage_005f0);
/* 8453 */     int _jspx_eval_fmt_005fparam_005f0 = _jspx_th_fmt_005fparam_005f0.doStartTag();
/* 8454 */     if (_jspx_eval_fmt_005fparam_005f0 != 0) {
/* 8455 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 8456 */         out = _jspx_page_context.pushBody();
/* 8457 */         _jspx_th_fmt_005fparam_005f0.setBodyContent((BodyContent)out);
/* 8458 */         _jspx_th_fmt_005fparam_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8461 */         if (_jspx_meth_c_005fout_005f18(_jspx_th_fmt_005fparam_005f0, _jspx_page_context))
/* 8462 */           return true;
/* 8463 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f0.doAfterBody();
/* 8464 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8467 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 8468 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8471 */     if (_jspx_th_fmt_005fparam_005f0.doEndTag() == 5) {
/* 8472 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 8473 */       return true;
/*      */     }
/* 8475 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 8476 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_fmt_005fparam_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8481 */     PageContext pageContext = _jspx_page_context;
/* 8482 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8484 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8485 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 8486 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_fmt_005fparam_005f0);
/*      */     
/* 8488 */     _jspx_th_c_005fout_005f18.setValue("${applicationScope.applications[param.haid]}");
/* 8489 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 8490 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 8491 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 8492 */       return true;
/*      */     }
/* 8494 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 8495 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f22(JspTag _jspx_th_c_005fchoose_005f22, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8500 */     PageContext pageContext = _jspx_page_context;
/* 8501 */     JspWriter out = _jspx_page_context.getOut();
/* 8502 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 8503 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 8505 */     OtherwiseTag _jspx_th_c_005fotherwise_005f22 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 8506 */     _jspx_th_c_005fotherwise_005f22.setPageContext(_jspx_page_context);
/* 8507 */     _jspx_th_c_005fotherwise_005f22.setParent((Tag)_jspx_th_c_005fchoose_005f22);
/* 8508 */     int _jspx_eval_c_005fotherwise_005f22 = _jspx_th_c_005fotherwise_005f22.doStartTag();
/* 8509 */     if (_jspx_eval_c_005fotherwise_005f22 != 0) {
/*      */       for (;;) {
/* 8511 */         out.write(10);
/* 8512 */         JspRuntimeLibrary.include(request, response, "/jsp/MonitorThresholdConfiguration.jsp", out, false);
/* 8513 */         out.write(10);
/* 8514 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f22.doAfterBody();
/* 8515 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8519 */     if (_jspx_th_c_005fotherwise_005f22.doEndTag() == 5) {
/* 8520 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22);
/* 8521 */       return true;
/*      */     }
/* 8523 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22);
/* 8524 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fif_005f37, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8529 */     PageContext pageContext = _jspx_page_context;
/* 8530 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8532 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8533 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 8534 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fif_005f37);
/*      */     
/* 8536 */     _jspx_th_c_005fout_005f19.setValue("${param.haid}");
/* 8537 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 8538 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 8539 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 8540 */       return true;
/*      */     }
/* 8542 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 8543 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fif_005f37, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8548 */     PageContext pageContext = _jspx_page_context;
/* 8549 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8551 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8552 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 8553 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fif_005f37);
/*      */     
/* 8555 */     _jspx_th_c_005fout_005f20.setValue("${param.haid}");
/* 8556 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 8557 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 8558 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 8559 */       return true;
/*      */     }
/* 8561 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 8562 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f8(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8567 */     PageContext pageContext = _jspx_page_context;
/* 8568 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8570 */     PutTag _jspx_th_tiles_005fput_005f8 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 8571 */     _jspx_th_tiles_005fput_005f8.setPageContext(_jspx_page_context);
/* 8572 */     _jspx_th_tiles_005fput_005f8.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 8574 */     _jspx_th_tiles_005fput_005f8.setName("footer");
/*      */     
/* 8576 */     _jspx_th_tiles_005fput_005f8.setValue("/jsp/footer.jsp");
/* 8577 */     int _jspx_eval_tiles_005fput_005f8 = _jspx_th_tiles_005fput_005f8.doStartTag();
/* 8578 */     if (_jspx_th_tiles_005fput_005f8.doEndTag() == 5) {
/* 8579 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f8);
/* 8580 */       return true;
/*      */     }
/* 8582 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f8);
/* 8583 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AssociatedThresholds_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */