/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
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
/*      */ import javax.servlet.http.HttpServletResponse;
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
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
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
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class WMIPerfDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   64 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   67 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   68 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   69 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   76 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   81 */     ArrayList list = null;
/*   82 */     StringBuffer sbf = new StringBuffer();
/*   83 */     ManagedApplication mo = new ManagedApplication();
/*   84 */     if (distinct)
/*      */     {
/*   86 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   90 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   93 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   95 */       ArrayList row = (ArrayList)list.get(i);
/*   96 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   97 */       if (distinct) {
/*   98 */         sbf.append(row.get(0));
/*      */       } else
/*  100 */         sbf.append(row.get(1));
/*  101 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  104 */     return sbf.toString(); }
/*      */   
/*  106 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  109 */     if (severity == null)
/*      */     {
/*  111 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  113 */     if (severity.equals("5"))
/*      */     {
/*  115 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  117 */     if (severity.equals("1"))
/*      */     {
/*  119 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  124 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  131 */     if (severity == null)
/*      */     {
/*  133 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  135 */     if (severity.equals("1"))
/*      */     {
/*  137 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  139 */     if (severity.equals("4"))
/*      */     {
/*  141 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  143 */     if (severity.equals("5"))
/*      */     {
/*  145 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  150 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  156 */     if (severity == null)
/*      */     {
/*  158 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  160 */     if (severity.equals("5"))
/*      */     {
/*  162 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  164 */     if (severity.equals("1"))
/*      */     {
/*  166 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  170 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  176 */     if (severity == null)
/*      */     {
/*  178 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  180 */     if (severity.equals("1"))
/*      */     {
/*  182 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  184 */     if (severity.equals("4"))
/*      */     {
/*  186 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  188 */     if (severity.equals("5"))
/*      */     {
/*  190 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  194 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  200 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  206 */     if (severity == 5)
/*      */     {
/*  208 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  210 */     if (severity == 1)
/*      */     {
/*  212 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  217 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  223 */     if (severity == null)
/*      */     {
/*  225 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  227 */     if (severity.equals("5"))
/*      */     {
/*  229 */       if (isAvailability) {
/*  230 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  233 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  236 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  238 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  240 */     if (severity.equals("1"))
/*      */     {
/*  242 */       if (isAvailability) {
/*  243 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  246 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  253 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  260 */     if (severity == null)
/*      */     {
/*  262 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  264 */     if (severity.equals("5"))
/*      */     {
/*  266 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  268 */     if (severity.equals("4"))
/*      */     {
/*  270 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  272 */     if (severity.equals("1"))
/*      */     {
/*  274 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  279 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  285 */     if (severity == null)
/*      */     {
/*  287 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  289 */     if (severity.equals("5"))
/*      */     {
/*  291 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  293 */     if (severity.equals("4"))
/*      */     {
/*  295 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  297 */     if (severity.equals("1"))
/*      */     {
/*  299 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  304 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  311 */     if (severity == null)
/*      */     {
/*  313 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  315 */     if (severity.equals("5"))
/*      */     {
/*  317 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  319 */     if (severity.equals("4"))
/*      */     {
/*  321 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  323 */     if (severity.equals("1"))
/*      */     {
/*  325 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  330 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  338 */     StringBuffer out = new StringBuffer();
/*  339 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  340 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  341 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  342 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  343 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  344 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  345 */     out.append("</tr>");
/*  346 */     out.append("</form></table>");
/*  347 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  354 */     if (val == null)
/*      */     {
/*  356 */       return "-";
/*      */     }
/*      */     
/*  359 */     String ret = FormatUtil.formatNumber(val);
/*  360 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  361 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  364 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  368 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  376 */     StringBuffer out = new StringBuffer();
/*  377 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  378 */     out.append("<tr>");
/*  379 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  381 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  383 */     out.append("</tr>");
/*  384 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  388 */       if (j % 2 == 0)
/*      */       {
/*  390 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  394 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  397 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  399 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  402 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  406 */       out.append("</tr>");
/*      */     }
/*  408 */     out.append("</table>");
/*  409 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  410 */     out.append("<tr>");
/*  411 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  412 */     out.append("</tr>");
/*  413 */     out.append("</table>");
/*  414 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  420 */     StringBuffer out = new StringBuffer();
/*  421 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  422 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  423 */     out.append("<tr>");
/*  424 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  425 */     out.append("<tr>");
/*  426 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  427 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  428 */     out.append("</tr>");
/*  429 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  432 */       out.append("<tr>");
/*  433 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  434 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  435 */       out.append("</tr>");
/*      */     }
/*      */     
/*  438 */     out.append("</table>");
/*  439 */     out.append("</table>");
/*  440 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  445 */     if (severity.equals("0"))
/*      */     {
/*  447 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  451 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  458 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  471 */     StringBuffer out = new StringBuffer();
/*  472 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  473 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  475 */       out.append("<tr>");
/*  476 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  477 */       out.append("</tr>");
/*      */       
/*      */ 
/*  480 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  482 */         String borderclass = "";
/*      */         
/*      */ 
/*  485 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  487 */         out.append("<tr>");
/*      */         
/*  489 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  490 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  491 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  497 */     out.append("</table><br>");
/*  498 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  499 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  501 */       List sLinks = secondLevelOfLinks[0];
/*  502 */       List sText = secondLevelOfLinks[1];
/*  503 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  506 */         out.append("<tr>");
/*  507 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  508 */         out.append("</tr>");
/*  509 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  511 */           String borderclass = "";
/*      */           
/*      */ 
/*  514 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  516 */           out.append("<tr>");
/*      */           
/*  518 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  519 */           if (sLinks.get(i).toString().length() == 0) {
/*  520 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  523 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  525 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  529 */     out.append("</table>");
/*  530 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  537 */     StringBuffer out = new StringBuffer();
/*  538 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  539 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  541 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  543 */         out.append("<tr>");
/*  544 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  545 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  549 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  551 */           String borderclass = "";
/*      */           
/*      */ 
/*  554 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  556 */           out.append("<tr>");
/*      */           
/*  558 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  559 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  560 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  563 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  566 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  571 */     out.append("</table><br>");
/*  572 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  573 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  575 */       List sLinks = secondLevelOfLinks[0];
/*  576 */       List sText = secondLevelOfLinks[1];
/*  577 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  580 */         out.append("<tr>");
/*  581 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  582 */         out.append("</tr>");
/*  583 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  585 */           String borderclass = "";
/*      */           
/*      */ 
/*  588 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  590 */           out.append("<tr>");
/*      */           
/*  592 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  593 */           if (sLinks.get(i).toString().length() == 0) {
/*  594 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  597 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  599 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  603 */     out.append("</table>");
/*  604 */     return out.toString();
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
/*  617 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  620 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  623 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  626 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  629 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  632 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  635 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  638 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  646 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  651 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  656 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  661 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  666 */     if (val != null)
/*      */     {
/*  668 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  672 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  677 */     if (val == null) {
/*  678 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  682 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  687 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  693 */     if (val != null)
/*      */     {
/*  695 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  699 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  705 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  710 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  714 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  719 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  724 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  729 */     String hostaddress = "";
/*  730 */     String ip = request.getHeader("x-forwarded-for");
/*  731 */     if (ip == null)
/*  732 */       ip = request.getRemoteAddr();
/*  733 */     InetAddress add = null;
/*  734 */     if (ip.equals("127.0.0.1")) {
/*  735 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  739 */       add = InetAddress.getByName(ip);
/*      */     }
/*  741 */     hostaddress = add.getHostName();
/*  742 */     if (hostaddress.indexOf('.') != -1) {
/*  743 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  744 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  748 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  753 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  759 */     if (severity == null)
/*      */     {
/*  761 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  763 */     if (severity.equals("5"))
/*      */     {
/*  765 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  767 */     if (severity.equals("1"))
/*      */     {
/*  769 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  774 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  779 */     ResultSet set = null;
/*  780 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  781 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  783 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  784 */       if (set.next()) { String str1;
/*  785 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  786 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  789 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  794 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  797 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  799 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  803 */     StringBuffer rca = new StringBuffer();
/*  804 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  805 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  808 */     int rcalength = key.length();
/*  809 */     String split = "6. ";
/*  810 */     int splitPresent = key.indexOf(split);
/*  811 */     String div1 = "";String div2 = "";
/*  812 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  814 */       if (rcalength > 180) {
/*  815 */         rca.append("<span class=\"rca-critical-text\">");
/*  816 */         getRCATrimmedText(key, rca);
/*  817 */         rca.append("</span>");
/*      */       } else {
/*  819 */         rca.append("<span class=\"rca-critical-text\">");
/*  820 */         rca.append(key);
/*  821 */         rca.append("</span>");
/*      */       }
/*  823 */       return rca.toString();
/*      */     }
/*  825 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  826 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  827 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  828 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  829 */     getRCATrimmedText(div1, rca);
/*  830 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  833 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  834 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  835 */     getRCATrimmedText(div2, rca);
/*  836 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  838 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  843 */     String[] st = msg.split("<br>");
/*  844 */     for (int i = 0; i < st.length; i++) {
/*  845 */       String s = st[i];
/*  846 */       if (s.length() > 180) {
/*  847 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  849 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  853 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  854 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  856 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  860 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  861 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  862 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  865 */       if (key == null) {
/*  866 */         return ret;
/*      */       }
/*      */       
/*  869 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  870 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  873 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  874 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  875 */       set = AMConnectionPool.executeQueryStmt(query);
/*  876 */       if (set.next())
/*      */       {
/*  878 */         String helpLink = set.getString("LINK");
/*  879 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  882 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  888 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  907 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  898 */         if (set != null) {
/*  899 */           AMConnectionPool.closeStatement(set);
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
/*  913 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
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
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  927 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  928 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  930 */       String entityStr = (String)keys.nextElement();
/*  931 */       String mmessage = temp.getProperty(entityStr);
/*  932 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  933 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  935 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  940 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  950 */     String des = new String();
/*  951 */     while (str.indexOf(find) != -1) {
/*  952 */       des = des + str.substring(0, str.indexOf(find));
/*  953 */       des = des + replace;
/*  954 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  956 */     des = des + str;
/*  957 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  964 */       if (alert == null)
/*      */       {
/*  966 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  968 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  970 */         return "&nbsp;";
/*      */       }
/*      */       
/*  973 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  975 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  978 */       int rcalength = test.length();
/*  979 */       if (rcalength < 300)
/*      */       {
/*  981 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  985 */       StringBuffer out = new StringBuffer();
/*  986 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  987 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  988 */       out.append("</div>");
/*  989 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  990 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  991 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  996 */       ex.printStackTrace();
/*      */     }
/*  998 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1004 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1009 */     ArrayList attribIDs = new ArrayList();
/* 1010 */     ArrayList resIDs = new ArrayList();
/* 1011 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1013 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1015 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1017 */       String resourceid = "";
/* 1018 */       String resourceType = "";
/* 1019 */       if (type == 2) {
/* 1020 */         resourceid = (String)row.get(0);
/* 1021 */         resourceType = (String)row.get(3);
/*      */       }
/* 1023 */       else if (type == 3) {
/* 1024 */         resourceid = (String)row.get(0);
/* 1025 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1028 */         resourceid = (String)row.get(6);
/* 1029 */         resourceType = (String)row.get(7);
/*      */       }
/* 1031 */       resIDs.add(resourceid);
/* 1032 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1033 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1035 */       String healthentity = null;
/* 1036 */       String availentity = null;
/* 1037 */       if (healthid != null) {
/* 1038 */         healthentity = resourceid + "_" + healthid;
/* 1039 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1042 */       if (availid != null) {
/* 1043 */         availentity = resourceid + "_" + availid;
/* 1044 */         entitylist.add(availentity);
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
/* 1058 */     Properties alert = getStatus(entitylist);
/* 1059 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1064 */     int size = monitorList.size();
/*      */     
/* 1066 */     String[] severity = new String[size];
/*      */     
/* 1068 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1070 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1071 */       String resourceName1 = (String)row1.get(7);
/* 1072 */       String resourceid1 = (String)row1.get(6);
/* 1073 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1074 */       if (severity[j] == null)
/*      */       {
/* 1076 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1080 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1082 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1084 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1087 */         if (sev > 0) {
/* 1088 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1089 */           monitorList.set(k, monitorList.get(j));
/* 1090 */           monitorList.set(j, t);
/* 1091 */           String temp = severity[k];
/* 1092 */           severity[k] = severity[j];
/* 1093 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1099 */     int z = 0;
/* 1100 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1103 */       int i = 0;
/* 1104 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1107 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1111 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1115 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1117 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1120 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1124 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1127 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1128 */       String resourceName1 = (String)row1.get(7);
/* 1129 */       String resourceid1 = (String)row1.get(6);
/* 1130 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1131 */       if (hseverity[j] == null)
/*      */       {
/* 1133 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1138 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1140 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1143 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1146 */         if (hsev > 0) {
/* 1147 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1148 */           monitorList.set(k, monitorList.get(j));
/* 1149 */           monitorList.set(j, t);
/* 1150 */           String temp1 = hseverity[k];
/* 1151 */           hseverity[k] = hseverity[j];
/* 1152 */           hseverity[j] = temp1;
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
/* 1164 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1165 */     boolean forInventory = false;
/* 1166 */     String trdisplay = "none";
/* 1167 */     String plusstyle = "inline";
/* 1168 */     String minusstyle = "none";
/* 1169 */     String haidTopLevel = "";
/* 1170 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1172 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1174 */         haidTopLevel = request.getParameter("haid");
/* 1175 */         forInventory = true;
/* 1176 */         trdisplay = "table-row;";
/* 1177 */         plusstyle = "none";
/* 1178 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1185 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1188 */     ArrayList listtoreturn = new ArrayList();
/* 1189 */     StringBuffer toreturn = new StringBuffer();
/* 1190 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1191 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1192 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1194 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1196 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1197 */       String childresid = (String)singlerow.get(0);
/* 1198 */       String childresname = (String)singlerow.get(1);
/* 1199 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1200 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1201 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1202 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1203 */       String unmanagestatus = (String)singlerow.get(5);
/* 1204 */       String actionstatus = (String)singlerow.get(6);
/* 1205 */       String linkclass = "monitorgp-links";
/* 1206 */       String titleforres = childresname;
/* 1207 */       String titilechildresname = childresname;
/* 1208 */       String childimg = "/images/trcont.png";
/* 1209 */       String flag = "enable";
/* 1210 */       String dcstarted = (String)singlerow.get(8);
/* 1211 */       String configMonitor = "";
/* 1212 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1213 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1215 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1217 */       if (singlerow.get(7) != null)
/*      */       {
/* 1219 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1221 */       String haiGroupType = "0";
/* 1222 */       if ("HAI".equals(childtype))
/*      */       {
/* 1224 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1226 */       childimg = "/images/trend.png";
/* 1227 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1228 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1229 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1231 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1233 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1235 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1236 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1239 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1241 */         linkclass = "disabledtext";
/* 1242 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1244 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1245 */       String availmouseover = "";
/* 1246 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1248 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1250 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1251 */       String healthmouseover = "";
/* 1252 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1254 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1257 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1258 */       int spacing = 0;
/* 1259 */       if (level >= 1)
/*      */       {
/* 1261 */         spacing = 40 * level;
/*      */       }
/* 1263 */       if (childtype.equals("HAI"))
/*      */       {
/* 1265 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1266 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1267 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1269 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1270 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1271 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1272 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1273 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1274 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1275 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1276 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1277 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1278 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1279 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1281 */         if (!forInventory)
/*      */         {
/* 1283 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1286 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1288 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1290 */           actions = editlink + actions;
/*      */         }
/* 1292 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1294 */           actions = actions + associatelink;
/*      */         }
/* 1296 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1297 */         String arrowimg = "";
/* 1298 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1300 */           actions = "";
/* 1301 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1302 */           checkbox = "";
/* 1303 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1305 */         if (isIt360)
/*      */         {
/* 1307 */           actionimg = "";
/* 1308 */           actions = "";
/* 1309 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1310 */           checkbox = "";
/*      */         }
/*      */         
/* 1313 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1315 */           actions = "";
/*      */         }
/* 1317 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1319 */           checkbox = "";
/*      */         }
/*      */         
/* 1322 */         String resourcelink = "";
/*      */         
/* 1324 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1326 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1330 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1333 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1334 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1335 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1336 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1337 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1338 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1339 */         if (!isIt360)
/*      */         {
/* 1341 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1345 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1348 */         toreturn.append("</tr>");
/* 1349 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1351 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1352 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1356 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1357 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1360 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1364 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1366 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1367 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1368 */             toreturn.append(assocMessage);
/* 1369 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1370 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1371 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1372 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1378 */         String resourcelink = null;
/* 1379 */         boolean hideEditLink = false;
/* 1380 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1382 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1383 */           hideEditLink = true;
/* 1384 */           if (isIt360)
/*      */           {
/* 1386 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1390 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1392 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1394 */           hideEditLink = true;
/* 1395 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1396 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1401 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1404 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1405 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1406 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1407 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1408 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1409 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1410 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1411 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1412 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1413 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1414 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1415 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1416 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1418 */         if (hideEditLink)
/*      */         {
/* 1420 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1422 */         if (!forInventory)
/*      */         {
/* 1424 */           removefromgroup = "";
/*      */         }
/* 1426 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1427 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1428 */           actions = actions + configcustomfields;
/*      */         }
/* 1430 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1432 */           actions = editlink + actions;
/*      */         }
/* 1434 */         String managedLink = "";
/* 1435 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1437 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1438 */           actions = "";
/* 1439 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1440 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1443 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1445 */           checkbox = "";
/*      */         }
/*      */         
/* 1448 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1450 */           actions = "";
/*      */         }
/* 1452 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1453 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1454 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1455 */         if (isIt360)
/*      */         {
/* 1457 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1461 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1463 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1464 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1465 */         if (!isIt360)
/*      */         {
/* 1467 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1471 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1473 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1476 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1483 */       StringBuilder toreturn = new StringBuilder();
/* 1484 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1485 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1486 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1487 */       String title = "";
/* 1488 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1489 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1490 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1491 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1493 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1495 */       else if ("5".equals(severity))
/*      */       {
/* 1497 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1501 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1503 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1504 */       toreturn.append(v);
/*      */       
/* 1506 */       toreturn.append(link);
/* 1507 */       if (severity == null)
/*      */       {
/* 1509 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1511 */       else if (severity.equals("5"))
/*      */       {
/* 1513 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1515 */       else if (severity.equals("4"))
/*      */       {
/* 1517 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1519 */       else if (severity.equals("1"))
/*      */       {
/* 1521 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1526 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1528 */       toreturn.append("</a>");
/* 1529 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1533 */       ex.printStackTrace();
/*      */     }
/* 1535 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1542 */       StringBuilder toreturn = new StringBuilder();
/* 1543 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1544 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1545 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1546 */       if (message == null)
/*      */       {
/* 1548 */         message = "";
/*      */       }
/*      */       
/* 1551 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1552 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1554 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1555 */       toreturn.append(v);
/*      */       
/* 1557 */       toreturn.append(link);
/*      */       
/* 1559 */       if (severity == null)
/*      */       {
/* 1561 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1563 */       else if (severity.equals("5"))
/*      */       {
/* 1565 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1567 */       else if (severity.equals("1"))
/*      */       {
/* 1569 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1574 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1576 */       toreturn.append("</a>");
/* 1577 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1583 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1586 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1587 */     if (invokeActions != null) {
/* 1588 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1589 */       while (iterator.hasNext()) {
/* 1590 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1591 */         if (actionmap.containsKey(actionid)) {
/* 1592 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1597 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1601 */     String actionLink = "";
/* 1602 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1603 */     String query = "";
/* 1604 */     ResultSet rs = null;
/* 1605 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1606 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1607 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1608 */       actionLink = "method=" + methodName;
/*      */     }
/* 1610 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1611 */       actionLink = methodName;
/*      */     }
/* 1613 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1614 */     Iterator itr = methodarglist.iterator();
/* 1615 */     boolean isfirstparam = true;
/* 1616 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1617 */     while (itr.hasNext()) {
/* 1618 */       HashMap argmap = (HashMap)itr.next();
/* 1619 */       String argtype = (String)argmap.get("TYPE");
/* 1620 */       String argname = (String)argmap.get("IDENTITY");
/* 1621 */       String paramname = (String)argmap.get("PARAMETER");
/* 1622 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1623 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1624 */         isfirstparam = false;
/* 1625 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1627 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1631 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1635 */         actionLink = actionLink + "&";
/*      */       }
/* 1637 */       String paramValue = null;
/* 1638 */       String tempargname = argname;
/* 1639 */       if (commonValues.getProperty(tempargname) != null) {
/* 1640 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1643 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1644 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1645 */           if (dbType.equals("mysql")) {
/* 1646 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1649 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1651 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1653 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1654 */             if (rs.next()) {
/* 1655 */               paramValue = rs.getString("VALUE");
/* 1656 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1660 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1664 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1667 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1672 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1673 */           paramValue = rowId;
/*      */         }
/* 1675 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1676 */           paramValue = managedObjectName;
/*      */         }
/* 1678 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1679 */           paramValue = resID;
/*      */         }
/* 1681 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1682 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1685 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1687 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1688 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1689 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1691 */     return actionLink;
/*      */   }
/*      */   
/* 1694 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1695 */     String dependentAttribute = null;
/* 1696 */     String align = "left";
/*      */     
/* 1698 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1699 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1700 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1701 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1702 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1703 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1704 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1705 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1706 */       align = "center";
/*      */     }
/*      */     
/* 1709 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1710 */     String actualdata = "";
/*      */     
/* 1712 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1713 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1714 */         actualdata = availValue;
/*      */       }
/* 1716 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1717 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1721 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1722 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1725 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1731 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1732 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1733 */       toreturn.append("<table>");
/* 1734 */       toreturn.append("<tr>");
/* 1735 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1736 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1737 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1738 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1739 */         String toolTip = "";
/* 1740 */         String hideClass = "";
/* 1741 */         String textStyle = "";
/* 1742 */         boolean isreferenced = true;
/* 1743 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1744 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1745 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1746 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1748 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1749 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1750 */           while (valueList.hasMoreTokens()) {
/* 1751 */             String dependentVal = valueList.nextToken();
/* 1752 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1753 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1754 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1756 */               toolTip = "";
/* 1757 */               hideClass = "";
/* 1758 */               isreferenced = false;
/* 1759 */               textStyle = "disabledtext";
/* 1760 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1764 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1765 */           toolTip = "";
/* 1766 */           hideClass = "";
/* 1767 */           isreferenced = false;
/* 1768 */           textStyle = "disabledtext";
/* 1769 */           if (dependentImageMap != null) {
/* 1770 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1771 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1774 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1778 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1779 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1780 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1781 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1782 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1783 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1785 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1786 */           if (isreferenced) {
/* 1787 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1791 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1792 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1793 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1794 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1795 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1796 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1798 */           toreturn.append("</span>");
/* 1799 */           toreturn.append("</a>");
/* 1800 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1803 */       toreturn.append("</tr>");
/* 1804 */       toreturn.append("</table>");
/* 1805 */       toreturn.append("</td>");
/*      */     } else {
/* 1807 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1810 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1814 */     String colTime = null;
/* 1815 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1816 */     if ((rows != null) && (rows.size() > 0)) {
/* 1817 */       Iterator<String> itr = rows.iterator();
/* 1818 */       String maxColQuery = "";
/* 1819 */       for (;;) { if (itr.hasNext()) {
/* 1820 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1821 */           ResultSet maxCol = null;
/*      */           try {
/* 1823 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1824 */             while (maxCol.next()) {
/* 1825 */               if (colTime == null) {
/* 1826 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1829 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1838 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1840 */               if (maxCol != null)
/* 1841 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1843 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1838 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1840 */               if (maxCol != null)
/* 1841 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1843 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1848 */     return colTime;
/*      */   }
/*      */   
/* 1851 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1852 */     tablename = null;
/* 1853 */     ResultSet rsTable = null;
/* 1854 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1856 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1857 */       while (rsTable.next()) {
/* 1858 */         tablename = rsTable.getString("DATATABLE");
/* 1859 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1860 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1873 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1864 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1867 */         if (rsTable != null)
/* 1868 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1870 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1876 */     String argsList = "";
/* 1877 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1879 */       if (showArgsMap.get(row) != null) {
/* 1880 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1881 */         if (showArgslist != null) {
/* 1882 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1883 */             if (argsList.trim().equals("")) {
/* 1884 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1887 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1894 */       e.printStackTrace();
/* 1895 */       return "";
/*      */     }
/* 1897 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1902 */     String argsList = "";
/* 1903 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1906 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1908 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1909 */         if (hideArgsList != null)
/*      */         {
/* 1911 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1913 */             if (argsList.trim().equals(""))
/*      */             {
/* 1915 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1919 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1927 */       ex.printStackTrace();
/*      */     }
/* 1929 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1933 */     StringBuilder toreturn = new StringBuilder();
/* 1934 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1941 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1942 */       Iterator itr = tActionList.iterator();
/* 1943 */       while (itr.hasNext()) {
/* 1944 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1945 */         String confirmmsg = "";
/* 1946 */         String link = "";
/* 1947 */         String isJSP = "NO";
/* 1948 */         HashMap tactionMap = (HashMap)itr.next();
/* 1949 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1950 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1951 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1952 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1953 */           (actionmap.containsKey(actionId))) {
/* 1954 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1955 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1956 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1957 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1958 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1960 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1966 */           if (isTableAction) {
/* 1967 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1970 */             tableName = "Link";
/* 1971 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1972 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1973 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1974 */             toreturn.append("</a></td>");
/*      */           }
/* 1976 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1977 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1978 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1979 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1985 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1991 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1993 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1994 */       Properties prop = (Properties)node.getUserObject();
/* 1995 */       String mgID = prop.getProperty("label");
/* 1996 */       String mgName = prop.getProperty("value");
/* 1997 */       String isParent = prop.getProperty("isParent");
/* 1998 */       int mgIDint = Integer.parseInt(mgID);
/* 1999 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 2001 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2003 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2004 */       if (node.getChildCount() > 0)
/*      */       {
/* 2006 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2008 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2010 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2012 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2016 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2021 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2023 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2025 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2027 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2031 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2034 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2035 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2037 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2041 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2043 */       if (node.getChildCount() > 0)
/*      */       {
/* 2045 */         builder.append("<UL>");
/* 2046 */         printMGTree(node, builder);
/* 2047 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2052 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2053 */     StringBuffer toReturn = new StringBuffer();
/* 2054 */     String table = "-";
/*      */     try {
/* 2056 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2057 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2058 */       float total = 0.0F;
/* 2059 */       while (it.hasNext()) {
/* 2060 */         String attName = (String)it.next();
/* 2061 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2062 */         boolean roundOffData = false;
/* 2063 */         if ((data != null) && (!data.equals(""))) {
/* 2064 */           if (data.indexOf(",") != -1) {
/* 2065 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2068 */             float value = Float.parseFloat(data);
/* 2069 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2072 */             total += value;
/* 2073 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2076 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2081 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2082 */       while (attVsWidthList.hasNext()) {
/* 2083 */         String attName = (String)attVsWidthList.next();
/* 2084 */         String data = (String)attVsWidthProps.get(attName);
/* 2085 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2086 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2087 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2088 */         String className = (String)graphDetails.get("ClassName");
/* 2089 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2090 */         if (percentage < 1.0F)
/*      */         {
/* 2092 */           data = percentage + "";
/*      */         }
/* 2094 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2096 */       if (toReturn.length() > 0) {
/* 2097 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2101 */       e.printStackTrace();
/*      */     }
/* 2103 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2109 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2110 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2111 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2112 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2113 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2114 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2115 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2116 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2117 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2120 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2121 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2122 */       splitvalues[0] = multiplecondition.toString();
/* 2123 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2126 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2131 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2132 */     if (thresholdType != 3) {
/* 2133 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2134 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2135 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2136 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2137 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2138 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2140 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2141 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2142 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2143 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2144 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2145 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2147 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2148 */     if (updateSelected != null) {
/* 2149 */       updateSelected[0] = "selected";
/*      */     }
/* 2151 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2156 */       StringBuffer toreturn = new StringBuffer("");
/* 2157 */       if (commaSeparatedMsgId != null) {
/* 2158 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2159 */         int count = 0;
/* 2160 */         while (msgids.hasMoreTokens()) {
/* 2161 */           String id = msgids.nextToken();
/* 2162 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2163 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2164 */           count++;
/* 2165 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2166 */             if (toreturn.length() == 0) {
/* 2167 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2169 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2170 */             if (!image.trim().equals("")) {
/* 2171 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2173 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2174 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2177 */         if (toreturn.length() > 0) {
/* 2178 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2182 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2185 */       e.printStackTrace(); }
/* 2186 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2192 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2198 */   private static Map<String, Long> _jspx_dependants = new HashMap(7);
/* 2199 */   static { _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2200 */     _jspx_dependants.put("/jsp/includes/MonitorTemplate.jspf", Long.valueOf(1473429417000L));
/* 2201 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2202 */     _jspx_dependants.put("/jsp/includes/CommonLeftPage.jspf", Long.valueOf(1473429417000L));
/* 2203 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2204 */     _jspx_dependants.put("/jsp/includes/WMIPerfLeftPage.jspf", Long.valueOf(1473429417000L));
/* 2205 */     _jspx_dependants.put("/jsp/includes/WMIPerfScreen.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2242 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2246 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2266 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2267 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2268 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2269 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2270 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2271 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2272 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2273 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2274 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2275 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2276 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2280 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2281 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2282 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2283 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2284 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2285 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2286 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2287 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2288 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2289 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2290 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2291 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2292 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2293 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/* 2294 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/* 2295 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.release();
/* 2296 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2297 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty_005fonchange.release();
/* 2298 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2299 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.release();
/* 2300 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2301 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2302 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2303 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2304 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2305 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2306 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2307 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2308 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2315 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2318 */     JspWriter out = null;
/* 2319 */     Object page = this;
/* 2320 */     JspWriter _jspx_out = null;
/* 2321 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2325 */       response.setContentType("text/html;charset=UTF-8");
/* 2326 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2328 */       _jspx_page_context = pageContext;
/* 2329 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2330 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2331 */       session = pageContext.getSession();
/* 2332 */       out = pageContext.getOut();
/* 2333 */       _jspx_out = out;
/*      */       
/* 2335 */       out.write("<!DOCTYPE html>\n");
/* 2336 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n");
/*      */       
/* 2338 */       request.setAttribute("HelpKey", "WMI PERFORMANCE COUNTERS");
/*      */       
/*      */ 
/* 2341 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/* 2342 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2343 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2344 */       if (wlsGraph == null) {
/* 2345 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2346 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2348 */       out.write(10);
/* 2349 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2351 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2352 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2353 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2355 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2357 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2359 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2361 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2362 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2363 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2364 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2367 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2368 */         String available = null;
/* 2369 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2370 */         out.write(10);
/*      */         
/* 2372 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2373 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2374 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2376 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2378 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2380 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2382 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2383 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2384 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2385 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2388 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2389 */           String unavailable = null;
/* 2390 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2391 */           out.write(10);
/*      */           
/* 2393 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2394 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2395 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2397 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2399 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2401 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2403 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2404 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2405 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2406 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2409 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2410 */             String unmanaged = null;
/* 2411 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2412 */             out.write(10);
/*      */             
/* 2414 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2415 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2416 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2418 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2420 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2422 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2424 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2425 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2426 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2427 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2430 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2431 */               String scheduled = null;
/* 2432 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2433 */               out.write(10);
/*      */               
/* 2435 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2436 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2437 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2439 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2441 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2443 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2445 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2446 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2447 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2448 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2451 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2452 */                 String critical = null;
/* 2453 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2454 */                 out.write(10);
/*      */                 
/* 2456 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2457 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2458 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2460 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2462 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2464 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2466 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2467 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2468 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2469 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2472 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2473 */                   String clear = null;
/* 2474 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2475 */                   out.write(10);
/*      */                   
/* 2477 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2478 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2479 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2481 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2483 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2485 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2487 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2488 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2489 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2490 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2493 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2494 */                     String warning = null;
/* 2495 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2496 */                     out.write(10);
/* 2497 */                     out.write(10);
/*      */                     
/* 2499 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2500 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2502 */                     out.write(10);
/* 2503 */                     out.write(10);
/* 2504 */                     out.write(10);
/* 2505 */                     out.write(10);
/* 2506 */                     out.write(10);
/* 2507 */                     out.write(10);
/* 2508 */                     Hashtable motypedisplaynames = null;
/* 2509 */                     synchronized (application) {
/* 2510 */                       motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2511 */                       if (motypedisplaynames == null) {
/* 2512 */                         motypedisplaynames = new Hashtable();
/* 2513 */                         _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                       }
/*      */                     }
/* 2516 */                     out.write(10);
/* 2517 */                     Hashtable availabilitykeys = null;
/* 2518 */                     synchronized (application) {
/* 2519 */                       availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2520 */                       if (availabilitykeys == null) {
/* 2521 */                         availabilitykeys = new Hashtable();
/* 2522 */                         _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                       }
/*      */                     }
/* 2525 */                     out.write(10);
/* 2526 */                     Hashtable healthkeys = null;
/* 2527 */                     synchronized (application) {
/* 2528 */                       healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2529 */                       if (healthkeys == null) {
/* 2530 */                         healthkeys = new Hashtable();
/* 2531 */                         _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                       }
/*      */                     }
/* 2534 */                     out.write(10);
/* 2535 */                     com.adventnet.appmanager.cam.beans.CAMGraphs camGraph = null;
/* 2536 */                     camGraph = (com.adventnet.appmanager.cam.beans.CAMGraphs)_jspx_page_context.getAttribute("camGraph", 1);
/* 2537 */                     if (camGraph == null) {
/* 2538 */                       camGraph = new com.adventnet.appmanager.cam.beans.CAMGraphs();
/* 2539 */                       _jspx_page_context.setAttribute("camGraph", camGraph, 1);
/*      */                     }
/* 2541 */                     out.write(10);
/* 2542 */                     out.write(10);
/* 2543 */                     out.write(10);
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2549 */                     String haid = request.getParameter("haid");
/* 2550 */                     String resourceid = request.getParameter("resourceid");
/* 2551 */                     String name = (String)request.getAttribute("displayname");
/* 2552 */                     ArrayList attribIDs = new ArrayList();
/* 2553 */                     String moname = request.getParameter("moname");
/* 2554 */                     String resourceName = (String)request.getAttribute("monitorname");
/* 2555 */                     String screenid = (String)request.getAttribute("screenid");
/* 2556 */                     ArrayList resIDs = new ArrayList();
/*      */                     
/* 2558 */                     String lastdatacollectedtime = (String)request.getAttribute("LASTDC");
/* 2559 */                     String nextdatacollectedtime = (String)request.getAttribute("NEXTDC");
/*      */                     
/* 2561 */                     String tempdate = "";
/* 2562 */                     if ((lastdatacollectedtime != null) && (!lastdatacollectedtime.equalsIgnoreCase("null"))) {
/*      */                       try
/*      */                       {
/* 2565 */                         tempdate = String.valueOf(Long.parseLong(lastdatacollectedtime));
/* 2566 */                         lastdatacollectedtime = formatDT(tempdate);
/*      */                       }
/*      */                       catch (Exception ex)
/*      */                       {
/* 2570 */                         lastdatacollectedtime = "---";
/*      */                       }
/*      */                       
/*      */                     }
/*      */                     else {
/* 2575 */                       lastdatacollectedtime = "---";
/*      */                     }
/*      */                     
/* 2578 */                     if ((nextdatacollectedtime != null) && (!nextdatacollectedtime.equalsIgnoreCase("null"))) {
/*      */                       try
/*      */                       {
/* 2581 */                         tempdate = String.valueOf(Long.parseLong(nextdatacollectedtime));
/* 2582 */                         nextdatacollectedtime = formatDT(tempdate);
/*      */                       }
/*      */                       catch (Exception ex)
/*      */                       {
/* 2586 */                         nextdatacollectedtime = "---";
/*      */                       }
/*      */                       
/*      */                     }
/*      */                     else {
/* 2591 */                       nextdatacollectedtime = "---";
/*      */                     }
/* 2593 */                     resIDs.add(resourceid);
/* 2594 */                     int healthid = 7100;
/* 2595 */                     int availabilityid = 7101;
/* 2596 */                     attribIDs.add("7100");
/* 2597 */                     attribIDs.add("7101");
/* 2598 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2599 */                     String resourcetype = "Generic WMI";
/* 2600 */                     String seven_days_text = FormatUtil.getString("am.webclient.common.sevendays.tooltip.text");
/* 2601 */                     String thiry_days_text = FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text");
/* 2602 */                     String encodeurl = URLEncoder.encode("/showresource.do?type=Generic WMI&moname=" + moname + "&method=showdetails&resourcename=" + resourceName + "&resourceid=" + resourceid + "&haid=" + haid);
/*      */                     
/*      */ 
/* 2605 */                     out.write(" \n<html>\n<head>\n\n</head>\n<body onLoad=\"javascript:myonload()\">\n");
/*      */                     
/* 2607 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2608 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2609 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2611 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/CAMLayout.jsp");
/* 2612 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2613 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2615 */                         out.write(32);
/*      */                         
/* 2617 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2618 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2619 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2621 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2623 */                         _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.wmi.wmimonitorname.text"));
/* 2624 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2625 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2626 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2629 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2630 */                         out.write(32);
/* 2631 */                         out.write(10);
/* 2632 */                         if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2634 */                         out.write(10);
/* 2635 */                         if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2637 */                         out.write(10);
/*      */                         
/* 2639 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2640 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2641 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2643 */                         _jspx_th_tiles_005fput_005f3.setName("LeftArea");
/*      */                         
/* 2645 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 2646 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2647 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2648 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2649 */                             out = _jspx_page_context.pushBody();
/* 2650 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2651 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2654 */                             out.write(32);
/* 2655 */                             out.write(10);
/* 2656 */                             out.write("\n<!--$Id$-->\n\n\n\n");
/* 2657 */                             if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2659 */                             out.write(10);
/* 2660 */                             if (_jspx_meth_c_005fif_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2662 */                             out.write(10);
/* 2663 */                             out.write(10);
/* 2664 */                             out.write(10);
/* 2665 */                             out.write("\n<SCRIPT language=JavaScript1.2 src=\"/template/mm_menu.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\n");
/*      */                             
/*      */ 
/* 2668 */                             String requestpath = "/images/mm_menu2.jsp";
/*      */                             
/* 2670 */                             ArrayList menupos = new ArrayList(5);
/* 2671 */                             if (request.isUserInRole("OPERATOR"))
/*      */                             {
/* 2673 */                               menupos.add("160");
/* 2674 */                               menupos.add("202");
/* 2675 */                               menupos.add("224");
/* 2676 */                               menupos.add("245");
/* 2677 */                               menupos.add("139");
/* 2678 */                               menupos.add("181");
/* 2679 */                               menupos.add("287");
/* 2680 */                               menupos.add("266");
/* 2681 */                               menupos.add("308");
/* 2682 */                               menupos.add("328");
/*      */ 
/*      */ 
/*      */ 
/*      */                             }
/* 2687 */                             else if (com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 2688 */                               menupos.add("160");
/* 2689 */                               menupos.add("205");
/* 2690 */                               menupos.add("220");
/* 2691 */                               menupos.add("240");
/* 2692 */                               menupos.add("140");
/* 2693 */                               menupos.add("180");
/* 2694 */                               menupos.add("265");
/* 2695 */                               menupos.add("285");
/* 2696 */                               menupos.add("305");
/* 2697 */                               menupos.add("325");
/*      */                             } else {
/* 2699 */                               menupos.add("218");
/* 2700 */                               menupos.add("261");
/* 2701 */                               menupos.add("282");
/* 2702 */                               menupos.add("303");
/* 2703 */                               menupos.add("197");
/* 2704 */                               menupos.add("239");
/* 2705 */                               menupos.add("324");
/* 2706 */                               menupos.add("345");
/* 2707 */                               menupos.add("366");
/* 2708 */                               menupos.add("400");
/*      */                             }
/*      */                             
/* 2711 */                             pageContext.setAttribute("menupos", menupos);
/*      */                             
/* 2713 */                             out.write(10);
/* 2714 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, requestpath, out, false);
/* 2715 */                             out.write("\n</script>\n<SCRIPT language=JavaScript1.2>mmLoadMenus()</SCRIPT>\n\n");
/* 2716 */                             out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n");
/*      */                             
/* 2718 */                             String categorytype = com.adventnet.appmanager.util.Constants.getCategorytype();
/* 2719 */                             pageContext.setAttribute("categorytype", categorytype);
/*      */                             
/* 2721 */                             out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n      <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 2722 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 2723 */                             out.write("</td>\n  </tr>\n\n\n");
/*      */                             
/* 2725 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2726 */                             _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2727 */                             _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2729 */                             _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 2730 */                             int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2731 */                             if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                               for (;;) {
/* 2733 */                                 out.write(10);
/* 2734 */                                 out.write(10);
/*      */                                 
/* 2736 */                                 IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2737 */                                 _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2738 */                                 _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                 
/* 2740 */                                 _jspx_th_c_005fif_005f5.setTest("${categorytype!='DATABASE' && categorytype!='LAMP'}");
/* 2741 */                                 int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2742 */                                 if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                   for (;;) {
/* 2744 */                                     out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=APP\" >");
/* 2745 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver"));
/* 2746 */                                     out.write("</a>\n\n</td>\n</tr>\n");
/* 2747 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2748 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2752 */                                 if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2753 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                 }
/*      */                                 
/* 2756 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2757 */                                 out.write(10);
/*      */                                 
/* 2759 */                                 IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2760 */                                 _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2761 */                                 _jspx_th_c_005fif_005f6.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                 
/* 2763 */                                 _jspx_th_c_005fif_005f6.setTest("${categorytype!='DATABASE' && categorytype!='LAMP' && categorytype!='CLOUD'}");
/* 2764 */                                 int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2765 */                                 if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                   for (;;) {
/* 2767 */                                     out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=ERP\" >");
/* 2768 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp"));
/* 2769 */                                     out.write("</a>\n\n</td>\n</tr>\n");
/* 2770 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2771 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2775 */                                 if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2776 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                 }
/*      */                                 
/* 2779 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2780 */                                 out.write(10);
/*      */                                 
/* 2782 */                                 IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2783 */                                 _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2784 */                                 _jspx_th_c_005fif_005f7.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                 
/* 2786 */                                 _jspx_th_c_005fif_005f7.setTest("${categorytype!='J2EE'}");
/* 2787 */                                 int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2788 */                                 if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                   for (;;) {
/* 2790 */                                     out.write("\n<tr  >\n<td  align=\"left\" class=\"leftlinkstd\"><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\">");
/* 2791 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 2792 */                                     out.write("</a>\n\n</td>\n</tr>\n");
/* 2793 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2794 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2798 */                                 if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2799 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                 }
/*      */                                 
/* 2802 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2803 */                                 out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\">");
/* 2804 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 2805 */                                 out.write("</a>\n\n</td>\n</tr>\n");
/*      */                                 
/* 2807 */                                 IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2808 */                                 _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2809 */                                 _jspx_th_c_005fif_005f8.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                 
/* 2811 */                                 _jspx_th_c_005fif_005f8.setTest("${categorytype!='DATABASE'}");
/* 2812 */                                 int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2813 */                                 if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                   for (;;) {
/* 2815 */                                     out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MS\" >");
/* 2816 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 2817 */                                     out.write("</a>\n\n</td>\n</tr>\n");
/* 2818 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2819 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2823 */                                 if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2824 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                 }
/*      */                                 
/* 2827 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2828 */                                 out.write("\n<tr >\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\">");
/* 2829 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 2830 */                                 out.write("    </a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" >");
/* 2831 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 2832 */                                 out.write("</a>\n\n</td>\n</tr>\n\n");
/* 2833 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2834 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2838 */                             if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2839 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                             }
/*      */                             
/* 2842 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2843 */                             out.write(10);
/* 2844 */                             out.write(10);
/*      */                             
/* 2846 */                             PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2847 */                             _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2848 */                             _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2850 */                             _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 2851 */                             int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2852 */                             if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                               for (;;) {
/* 2854 */                                 out.write(10);
/* 2855 */                                 out.write(10);
/*      */                                 
/* 2857 */                                 IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2858 */                                 _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2859 */                                 _jspx_th_c_005fif_005f9.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                                 
/* 2861 */                                 _jspx_th_c_005fif_005f9.setTest("${categorytype!='DATABASE' && categorytype!='LAMP'}");
/* 2862 */                                 int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2863 */                                 if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                   for (;;) {
/* 2865 */                                     out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=APP\" >");
/* 2866 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver"));
/* 2867 */                                     out.write("</a>\n\n</td>\n</tr>\n");
/* 2868 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2869 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2873 */                                 if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2874 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                 }
/*      */                                 
/* 2877 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2878 */                                 out.write(10);
/*      */                                 
/* 2880 */                                 IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2881 */                                 _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2882 */                                 _jspx_th_c_005fif_005f10.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                                 
/* 2884 */                                 _jspx_th_c_005fif_005f10.setTest("${categorytype!='DATABASE' && categorytype!='LAMP' && categorytype!='CLOUD'}");
/* 2885 */                                 int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2886 */                                 if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                   for (;;) {
/* 2888 */                                     out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=ERP\" >");
/* 2889 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp"));
/* 2890 */                                     out.write("</a>\n\n</td>\n</tr>\n");
/* 2891 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2892 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2896 */                                 if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2897 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                 }
/*      */                                 
/* 2900 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2901 */                                 out.write("\n\n\n<tr  >\n<td  align=\"left\" class=\"leftlinkstd\"><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\">");
/* 2902 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 2903 */                                 out.write("</a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\">");
/* 2904 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 2905 */                                 out.write("</a>\n\n</td>\n</tr>\n\n");
/*      */                                 
/* 2907 */                                 IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2908 */                                 _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2909 */                                 _jspx_th_c_005fif_005f11.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                                 
/* 2911 */                                 _jspx_th_c_005fif_005f11.setTest("${categorytype!='DATABASE'}");
/* 2912 */                                 int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2913 */                                 if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                                   for (;;) {
/* 2915 */                                     out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MS\" >");
/* 2916 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 2917 */                                     out.write("</a>\n\n</td>\n</tr>\n");
/* 2918 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 2919 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2923 */                                 if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 2924 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                                 }
/*      */                                 
/* 2927 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2928 */                                 out.write("\n<tr >\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\">");
/* 2929 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 2930 */                                 out.write(" </a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" >");
/* 2931 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 2932 */                                 out.write("</a>\n\n</td>\n</tr>\n");
/* 2933 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2934 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2938 */                             if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2939 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                             }
/*      */                             
/* 2942 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2943 */                             out.write("\n\n  <tr >\n  <td class=\"leftlinkstd\"><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=CAM&network=Custom-Application\"   class=\"new-left-links\">");
/* 2944 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 2945 */                             out.write("</a></td>\n\n</tr>\n");
/*      */                             
/* 2947 */                             IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2948 */                             _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2949 */                             _jspx_th_c_005fif_005f12.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2951 */                             _jspx_th_c_005fif_005f12.setTest("${categorytype!='CLOUD'}");
/* 2952 */                             int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 2953 */                             if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                               for (;;) {
/* 2955 */                                 out.write("\n<tr >\n  <td class=\"leftlinkstd\"><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MOM\"   class=\"new-left-links\">");
/* 2956 */                                 out.print(FormatUtil.getString("Middleware/Portal"));
/* 2957 */                                 out.write("</a></td>\n \n</tr>\n");
/* 2958 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 2959 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2963 */                             if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 2964 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                             }
/*      */                             
/* 2967 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2968 */                             out.write(10);
/*      */                             
/* 2970 */                             String[] categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 2971 */                             for (int i = 0; i < categoryLink.length; i++)
/*      */                             {
/* 2973 */                               if ((!categoryLink[i].equals("APP")) && (!categoryLink[i].equals("TM")) && (!categoryLink[i].equals("ERP")) && (!categoryLink[i].equals("DBS")) && (!categoryLink[i].equals("SYS")) && (!categoryLink[i].equals("MS")) && (!categoryLink[i].equals("SCR")) && (!categoryLink[i].equals("NWD")) && (!categoryLink[i].equals("SER")) && (!categoryLink[i].equals("URL")) && (!categoryLink[i].equals("CAM")) && (!categoryLink[i].equals("MOM")) && (!categoryLink[i].equals("SAN")) && (!categoryLink[i].equals("EMO")))
/*      */                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2982 */                                 out.write("\n<tr>\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href='");
/* 2983 */                                 out.print("/showresource.do?method=showResourceTypes&detailspage=true&group=" + categoryLink[i]);
/* 2984 */                                 out.write(39);
/* 2985 */                                 out.write(62);
/* 2986 */                                 out.print(FormatUtil.getString(com.adventnet.appmanager.util.Constants.categoryTitle[i]));
/* 2987 */                                 out.write("</a>\n</td>\n</tr>\n");
/*      */                               }
/*      */                             }
/*      */                             
/* 2991 */                             out.write("\n\n</table>\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2992 */                             out.write(10);
/* 2993 */                             response.setContentType("text/html;charset=UTF-8");
/* 2994 */                             out.write(10);
/* 2995 */                             out.write(10);
/* 2996 */                             out.write(10);
/* 2997 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 2998 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3001 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3002 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3005 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3006 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 3009 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3010 */                         out.write(10);
/*      */                         
/* 3012 */                         PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3013 */                         _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3014 */                         _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 3016 */                         _jspx_th_tiles_005fput_005f4.setName("ServerLeftArea");
/*      */                         
/* 3018 */                         _jspx_th_tiles_005fput_005f4.setType("string");
/* 3019 */                         int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3020 */                         if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 3021 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 3022 */                             out = _jspx_page_context.pushBody();
/* 3023 */                             _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 3024 */                             _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 3027 */                             out.write(10);
/* 3028 */                             out.write("<!--$Id$-->\n\n\n\n");
/*      */                             
/*      */ 
/* 3031 */                             String haid1 = "";
/*      */                             
/*      */ 
/* 3034 */                             out.write("\n\n<script language=\"JavaScript\">\n\tfunction confirmDelete()\n \t {\n  var s = confirm(\"");
/* 3035 */                             out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 3036 */                             out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=deleteMO&type=Generic WMI&select=");
/* 3037 */                             if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3039 */                             out.write("\";\n\t }\n        function confirmManage()\n \t {\n  var s = confirm(\"");
/* 3040 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 3041 */                             out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 3042 */                             if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3044 */                             out.write("\";\n\t }\n\n         function confirmUnManage()\n \t {\n        \t ");
/* 3045 */                             if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3047 */                             out.write("\n\t\t  var show_msg=\"false\";\n\t      var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 3048 */                             out.print(request.getParameter("resourceid"));
/* 3049 */                             out.write("; //No i18n\n\t      $.ajax({\n\t        type:'POST', //No i18n\n\t        url:url,\n\t        async:false,\n\t        success: function(data)\n\t        {\n\t          show_msg=data\n\t        }\n\t      });\n\t      if(show_msg.indexOf(\"true\")>-1)\n\t      {\n\t          alert(\"");
/* 3050 */                             out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 3051 */                             out.write("\");\n\t\t   \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 3052 */                             if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3054 */                             out.write("\";\n\t     }\n       else { \n    var s = confirm(\"");
/* 3055 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 3056 */                             out.write("\");\n    if (s){\n     \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 3057 */                             if (_jspx_meth_c_005fout_005f4(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3059 */                             out.write("\"; //No I18N\n\t  }\n   } \n  }\n  </script>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td width=\"90%\" height=\"21\" class=\"leftlinksheading\">\n    ");
/* 3060 */                             out.print(FormatUtil.getString("am.webclient.wmi.wmimonitor.text"));
/* 3061 */                             out.write("\n      </td>\n  </tr>\n\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n     ");
/* 3062 */                             out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 3063 */                             out.write("\n    </td>\n  </tr>\n");
/* 3064 */                             out.write(10);
/* 3065 */                             out.write(32);
/* 3066 */                             out.write(32);
/*      */                             
/* 3068 */                             IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3069 */                             _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3070 */                             _jspx_th_c_005fif_005f13.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3072 */                             _jspx_th_c_005fif_005f13.setTest("${!empty ADMIN || !empty DEMO }");
/* 3073 */                             int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3074 */                             if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                               for (;;) {
/* 3076 */                                 out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n    <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 3077 */                                 if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                                   return;
/* 3079 */                                 out.write("\" class=\"new-left-links\">\n    ");
/* 3080 */                                 out.print(ALERTCONFIG_TEXT);
/* 3081 */                                 out.write("\n    </a>\n    </td>\n  </tr>\n  ");
/* 3082 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3083 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3087 */                             if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3088 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                             }
/*      */                             
/* 3091 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3092 */                             out.write("\n\n\n\n\n");
/* 3093 */                             if (com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled()) {
/* 3094 */                               out.write("  \n ");
/*      */                               
/* 3096 */                               PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3097 */                               _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3098 */                               _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 3100 */                               _jspx_th_logic_005fpresent_005f2.setRole("ENTERPRISEADMIN");
/* 3101 */                               int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3102 */                               if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                 for (;;) {
/* 3104 */                                   out.write("\n  <tr>\n   <td height=\"21\" class=\"leftlinkstd\">\n   ");
/*      */                                   
/* 3106 */                                   String temphaid = request.getParameter("haid");
/*      */                                   try
/*      */                                   {
/* 3109 */                                     Integer.parseInt(temphaid);
/*      */                                     
/* 3111 */                                     out.write("\n    <a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 3112 */                                     out.print(resourceid);
/* 3113 */                                     out.write("&resourcename=");
/* 3114 */                                     out.print(resourceName);
/* 3115 */                                     out.write("&method=showdetails&moname=");
/* 3116 */                                     out.print(moname);
/* 3117 */                                     out.write("&aam_jump=true&editPage=true&type=Generic WMI\"  class=\"new-left-links\">\n   ");
/*      */ 
/*      */                                   }
/*      */                                   catch (NumberFormatException ne)
/*      */                                   {
/*      */ 
/* 3123 */                                     out.write("\n      <a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 3124 */                                     out.print(resourceid);
/* 3125 */                                     out.write("&resourcename=");
/* 3126 */                                     out.print(resourceName);
/* 3127 */                                     out.write("&method=showdetails&moname=");
/* 3128 */                                     out.print(moname);
/* 3129 */                                     out.write("&aam_jump=true&editPage=true&type=Generic WMI\"  class=\"new-left-links\">\n   ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3133 */                                   out.write(10);
/* 3134 */                                   out.write(32);
/* 3135 */                                   out.write(32);
/* 3136 */                                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3137 */                                   out.write("\n   </td>\n  </tr>\n ");
/* 3138 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3139 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3143 */                               if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3144 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                               }
/*      */                               
/* 3147 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3148 */                               out.write(10);
/* 3149 */                               out.write(32);
/*      */                             }
/* 3151 */                             out.write(10);
/*      */                             
/* 3153 */                             IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3154 */                             _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3155 */                             _jspx_th_c_005fif_005f14.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3157 */                             _jspx_th_c_005fif_005f14.setTest("${(!empty ADMIN || !empty DEMO) }");
/* 3158 */                             int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3159 */                             if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                               for (;;) {
/* 3161 */                                 out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n    <a href=\"javascript:void(0)\" onclick=\"showEditMonitor()\" class=\"new-left-links\">\n\n    ");
/* 3162 */                                 out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3163 */                                 out.write("\n\n    </a>\n    ");
/* 3164 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3165 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3169 */                             if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3170 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                             }
/*      */                             
/* 3173 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3174 */                             out.write("</td>\n  </tr>\n\n  ");
/*      */                             
/* 3176 */                             IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3177 */                             _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3178 */                             _jspx_th_c_005fif_005f15.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3180 */                             _jspx_th_c_005fif_005f15.setTest("${!empty ADMIN || !empty DEMO }");
/* 3181 */                             int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3182 */                             if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                               for (;;) {
/* 3184 */                                 out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\"><a href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 3185 */                                 out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3186 */                                 out.write("</a></td>\n  </tr>\n  ");
/* 3187 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3188 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3192 */                             if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3193 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                             }
/*      */                             
/* 3196 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3197 */                             out.write(10);
/* 3198 */                             out.write(32);
/* 3199 */                             out.write(32);
/*      */                             
/* 3201 */                             IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3202 */                             _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3203 */                             _jspx_th_c_005fif_005f16.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3205 */                             _jspx_th_c_005fif_005f16.setTest("${!empty ADMIN || !empty DEMO}");
/* 3206 */                             int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3207 */                             if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                               for (;;) {
/* 3209 */                                 out.write("\n  <tr>\n  ");
/*      */                                 
/* 3211 */                                 if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                                 {
/*      */ 
/* 3214 */                                   out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 3215 */                                   out.print(FormatUtil.getString("Manage"));
/* 3216 */                                   out.write("</A></td>\n    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3222 */                                   out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 3223 */                                   out.print(FormatUtil.getString("UnManage"));
/* 3224 */                                   out.write("</A></td>\n    ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3228 */                                 out.write("\n  </tr>\n  ");
/* 3229 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3230 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3234 */                             if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3235 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                             }
/*      */                             
/* 3238 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3239 */                             out.write(10);
/*      */                             
/* 3241 */                             IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3242 */                             _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 3243 */                             _jspx_th_c_005fif_005f17.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3245 */                             _jspx_th_c_005fif_005f17.setTest("${!empty ADMIN || !empty DEMO }");
/* 3246 */                             int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 3247 */                             if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                               for (;;) {
/* 3249 */                                 out.write("\n<tr>\n    <td height=\"21\" class=\"leftlinkstd\"><a href=\"/WMIPerfCounters.do?resourceid=");
/* 3250 */                                 out.print(resourceid);
/* 3251 */                                 out.write("&method=getDetails\" class=\"new-left-links\">");
/* 3252 */                                 out.print(FormatUtil.getString("am.webclient.wmi.adddelete.link.text"));
/* 3253 */                                 out.write("</a></td>\n</tr>\n");
/* 3254 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 3255 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3259 */                             if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 3260 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                             }
/*      */                             
/* 3263 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 3264 */                             out.write(10);
/*      */                             
/* 3266 */                             String configureLink = "/WMIPerfCounters.do?resourceid=" + resourceid + "&method=getDetails";
/* 3267 */                             if ((request.isUserInRole("ENTERPRISEADMIN")) && (com.adventnet.appmanager.util.Constants.isSsoEnabled()))
/*      */                             {
/* 3269 */                               StringBuilder builder = new StringBuilder();
/* 3270 */                               builder.append("https:").append("//");
/* 3271 */                               builder.append(com.adventnet.appmanager.util.Constants.getAppHostName()).append(":");
/* 3272 */                               builder.append(com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.getsslport()).append(configureLink);
/* 3273 */                               configureLink = builder.toString();
/*      */                               
/* 3275 */                               out.write("\n  <tr>\n    <td colspan=\"2\" class=\"leftlinkstd\">\n\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindowWithHeightWidth('");
/* 3276 */                               out.print(configureLink);
/* 3277 */                               out.write("',1000,550)\" class=\"new-left-links\">");
/* 3278 */                               out.print(FormatUtil.getString("am.webclient.wmi.adddelete.link.text"));
/* 3279 */                               out.write("</a>\n    </td>\n  </tr>\n");
/*      */                             }
/*      */                             
/*      */ 
/* 3283 */                             out.write(10);
/*      */                             
/* 3285 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3286 */                             _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3287 */                             _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3289 */                             _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 3290 */                             int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3291 */                             if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                               for (;;) {
/* 3293 */                                 out.write(10);
/* 3294 */                                 out.write(32);
/* 3295 */                                 out.write(32);
/*      */                                 
/* 3297 */                                 String resourceid_poll = request.getParameter("resourceid");
/* 3298 */                                 String resourcetype_poll = request.getParameter("type");
/*      */                                 
/* 3300 */                                 out.write("\n    <tr>\n    <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n    <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3301 */                                 out.print(resourceid_poll);
/* 3302 */                                 out.write("&resourcetype=");
/* 3303 */                                 out.print(resourcetype_poll);
/* 3304 */                                 out.write("\" class=\"new-left-links\"> ");
/* 3305 */                                 out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3306 */                                 out.write("</a></td>\n  </tr>\n  ");
/* 3307 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3308 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3312 */                             if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3313 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                             }
/*      */                             
/* 3316 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3317 */                             out.write(10);
/* 3318 */                             out.write(32);
/* 3319 */                             out.write(32);
/*      */                             
/* 3321 */                             PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3322 */                             _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3323 */                             _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3325 */                             _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/* 3326 */                             int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3327 */                             if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                               for (;;) {
/* 3329 */                                 out.write("\n        <tr>\n        <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n        <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3330 */                                 out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3331 */                                 out.write("</a></td>\n        </td>\n    ");
/* 3332 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3333 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3337 */                             if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3338 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                             }
/*      */                             
/* 3341 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3342 */                             out.write("\n    ");
/* 3343 */                             out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                             
/* 3345 */                             if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                             {
/* 3347 */                               Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 3348 */                               String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                               
/* 3350 */                               String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 3351 */                               String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 3352 */                               if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                               {
/* 3354 */                                 if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                                 {
/*      */ 
/* 3357 */                                   out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3358 */                                   out.print(ciInfoUrl);
/* 3359 */                                   out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3360 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3361 */                                   out.write("</a></td>");
/* 3362 */                                   out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3363 */                                   out.print(ciRLUrl);
/* 3364 */                                   out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3365 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3366 */                                   out.write("</a></td>");
/* 3367 */                                   out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                                 }
/* 3371 */                                 else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                                 {
/*      */ 
/* 3374 */                                   out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 3375 */                                   out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 3376 */                                   out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3377 */                                   out.print(ciInfoUrl);
/* 3378 */                                   out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3379 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3380 */                                   out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3381 */                                   out.print(ciRLUrl);
/* 3382 */                                   out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3383 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3384 */                                   out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                             
/* 3389 */                             out.write("\n \n \n\n");
/* 3390 */                             out.write("\n</table>\n\n\n <br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3391 */                             out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 3392 */                             out.write("</td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"50%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3393 */                             if (_jspx_meth_c_005fout_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3395 */                             out.write("&attributeid=");
/* 3396 */                             out.print(healthid);
/* 3397 */                             out.write("')\" class=\"new-left-links\">");
/* 3398 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3399 */                             out.write("</a> </td>\n    <td width=\"50%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3400 */                             if (_jspx_meth_c_005fout_005f7(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3402 */                             out.write("&attributeid=");
/* 3403 */                             out.print(healthid);
/* 3404 */                             out.write("')\">");
/* 3405 */                             out.print(getSeverityImageForHealth(alert.getProperty(request.getParameter("resourceid") + "#" + healthid)));
/* 3406 */                             out.write("</a></td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n      <td width=\"50%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3407 */                             if (_jspx_meth_c_005fout_005f8(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3409 */                             out.write("&attributeid=");
/* 3410 */                             out.print(availabilityid);
/* 3411 */                             out.write("')\" class=\"new-left-links\">");
/* 3412 */                             out.print(FormatUtil.getString("Availability"));
/* 3413 */                             out.write("</a> </td>\n      <td width=\"50%\" > <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3414 */                             if (_jspx_meth_c_005fout_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3416 */                             out.write("&attributeid=");
/* 3417 */                             out.print(availabilityid);
/* 3418 */                             out.write("')\">");
/* 3419 */                             out.print(getSeverityImageForAvailability(alert.getProperty(request.getParameter("resourceid") + "#" + availabilityid)));
/* 3420 */                             out.write("</a></td>\n  </tr>\n\n</table>\n<br>\n\n<br>\n");
/* 3421 */                             out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                             
/*      */ 
/*      */ 
/* 3425 */                             boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3426 */                             if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition)
/*      */                             {
/* 3428 */                               showAssociatedBSG = false;
/*      */                               
/*      */ 
/*      */ 
/* 3432 */                               CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3433 */                               CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3434 */                               String loginName = request.getUserPrincipal().getName();
/* 3435 */                               CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                               
/* 3437 */                               if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                               {
/* 3439 */                                 showAssociatedBSG = true;
/*      */                               }
/*      */                             }
/* 3442 */                             String monitorType = request.getParameter("type");
/* 3443 */                             ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3444 */                             boolean mon = conf1.isConfMonitor(monitorType);
/* 3445 */                             if (showAssociatedBSG)
/*      */                             {
/* 3447 */                               Hashtable associatedmgs = new Hashtable();
/* 3448 */                               String resId = request.getParameter("resourceid");
/* 3449 */                               request.setAttribute("associatedmgs", com.adventnet.appmanager.fault.FaultUtil.getAdminAssociatedMG(resId, request));
/* 3450 */                               if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                               {
/* 3452 */                                 mon = false;
/*      */                               }
/*      */                               
/* 3455 */                               if (!mon)
/*      */                               {
/* 3457 */                                 out.write(10);
/* 3458 */                                 out.write(10);
/*      */                                 
/* 3460 */                                 IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3461 */                                 _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 3462 */                                 _jspx_th_c_005fif_005f18.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3464 */                                 _jspx_th_c_005fif_005f18.setTest("${!empty associatedmgs}");
/* 3465 */                                 int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 3466 */                                 if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                                   for (;;) {
/* 3468 */                                     out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3469 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3470 */                                     out.write("</td>\n        </tr>\n        ");
/*      */                                     
/* 3472 */                                     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3473 */                                     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3474 */                                     _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f18);
/*      */                                     
/* 3476 */                                     _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                                     
/* 3478 */                                     _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                                     
/* 3480 */                                     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3481 */                                     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                     try {
/* 3483 */                                       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3484 */                                       if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                         for (;;) {
/* 3486 */                                           out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3487 */                                           if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3545 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3546 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3489 */                                           out.write("&method=showApplication\" title=\"");
/* 3490 */                                           if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3545 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3546 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3492 */                                           out.write("\"  class=\"new-left-links\">\n         ");
/* 3493 */                                           if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3545 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3546 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3495 */                                           out.write("\n    \t");
/* 3496 */                                           out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3497 */                                           out.write("\n         </a></td>\n        <td>");
/*      */                                           
/* 3499 */                                           PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3500 */                                           _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3501 */                                           _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                           
/* 3503 */                                           _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 3504 */                                           int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3505 */                                           if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                             for (;;) {
/* 3507 */                                               out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3508 */                                               if (_jspx_meth_c_005fout_005f13(_jspx_th_logic_005fpresent_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3545 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 3546 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 3510 */                                               out.write(39);
/* 3511 */                                               out.write(44);
/* 3512 */                                               out.write(39);
/* 3513 */                                               out.print(resId);
/* 3514 */                                               out.write(39);
/* 3515 */                                               out.write(44);
/* 3516 */                                               out.write(39);
/* 3517 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3518 */                                               out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3519 */                                               out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3520 */                                               out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3521 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3522 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3526 */                                           if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3527 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3545 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3546 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3530 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3531 */                                           out.write("</td>\n        </tr>\n\t");
/* 3532 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3533 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3537 */                                       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3545 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3546 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 3541 */                                         int tmp8103_8102 = 0; int[] tmp8103_8100 = _jspx_push_body_count_c_005fforEach_005f0; int tmp8105_8104 = tmp8103_8100[tmp8103_8102];tmp8103_8100[tmp8103_8102] = (tmp8105_8104 - 1); if (tmp8105_8104 <= 0) break;
/* 3542 */                                         out = _jspx_page_context.popBody(); }
/* 3543 */                                       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                     } finally {
/* 3545 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3546 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                     }
/* 3548 */                                     out.write("\n      </table>\n ");
/* 3549 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 3550 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3554 */                                 if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 3555 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                                 }
/*      */                                 
/* 3558 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 3559 */                                 out.write(10);
/* 3560 */                                 out.write(32);
/*      */                                 
/* 3562 */                                 IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3563 */                                 _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 3564 */                                 _jspx_th_c_005fif_005f19.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3566 */                                 _jspx_th_c_005fif_005f19.setTest("${empty associatedmgs}");
/* 3567 */                                 int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 3568 */                                 if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                                   for (;;) {
/* 3570 */                                     out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 3571 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3572 */                                     out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 3573 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3574 */                                     out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 3575 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 3576 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3580 */                                 if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 3581 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                                 }
/*      */                                 
/* 3584 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3585 */                                 out.write(10);
/* 3586 */                                 out.write(32);
/* 3587 */                                 out.write(10);
/*      */ 
/*      */                               }
/* 3590 */                               else if (mon)
/*      */                               {
/*      */ 
/*      */ 
/* 3594 */                                 out.write(10);
/*      */                                 
/* 3596 */                                 IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3597 */                                 _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 3598 */                                 _jspx_th_c_005fif_005f20.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3600 */                                 _jspx_th_c_005fif_005f20.setTest("${!empty associatedmgs}");
/* 3601 */                                 int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 3602 */                                 if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */                                   for (;;) {
/* 3604 */                                     out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 3605 */                                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f20, _jspx_page_context))
/*      */                                       return;
/* 3607 */                                     out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                                     
/* 3609 */                                     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3610 */                                     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3611 */                                     _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f20);
/*      */                                     
/* 3613 */                                     _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                                     
/* 3615 */                                     _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                                     
/* 3617 */                                     _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 3618 */                                     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                                     try {
/* 3620 */                                       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3621 */                                       if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                         for (;;) {
/* 3623 */                                           out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 3624 */                                           if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3685 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 3686 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 3626 */                                           out.write("&method=showApplication\" title=\"");
/* 3627 */                                           if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3685 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 3686 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 3629 */                                           out.write("\"  class=\"staticlinks\">\n         ");
/* 3630 */                                           if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3685 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 3686 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 3632 */                                           out.write("\n    \t");
/* 3633 */                                           out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3634 */                                           out.write("</a></span>\t\n\t\t ");
/*      */                                           
/* 3636 */                                           PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3637 */                                           _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3638 */                                           _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                           
/* 3640 */                                           _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 3641 */                                           int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3642 */                                           if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                             for (;;) {
/* 3644 */                                               out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 3645 */                                               if (_jspx_meth_c_005fout_005f17(_jspx_th_logic_005fpresent_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3685 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 3686 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 3647 */                                               out.write(39);
/* 3648 */                                               out.write(44);
/* 3649 */                                               out.write(39);
/* 3650 */                                               out.print(resId);
/* 3651 */                                               out.write(39);
/* 3652 */                                               out.write(44);
/* 3653 */                                               out.write(39);
/* 3654 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3655 */                                               out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 3656 */                                               out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3657 */                                               out.write("\"  title=\"");
/* 3658 */                                               if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3685 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 3686 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 3660 */                                               out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 3661 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3662 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3666 */                                           if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3667 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3685 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 3686 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 3670 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3671 */                                           out.write("\n\n\t\t \t");
/* 3672 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3673 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3677 */                                       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3685 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 3686 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 3681 */                                         int tmp9129_9128 = 0; int[] tmp9129_9126 = _jspx_push_body_count_c_005fforEach_005f1; int tmp9131_9130 = tmp9129_9126[tmp9129_9128];tmp9129_9126[tmp9129_9128] = (tmp9131_9130 - 1); if (tmp9131_9130 <= 0) break;
/* 3682 */                                         out = _jspx_page_context.popBody(); }
/* 3683 */                                       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                                     } finally {
/* 3685 */                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 3686 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     }
/* 3688 */                                     out.write("\n\t\n\t\t\t</td>\n\t ");
/* 3689 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 3690 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3694 */                                 if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 3695 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */                                 }
/*      */                                 
/* 3698 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3699 */                                 out.write(10);
/* 3700 */                                 out.write(32);
/* 3701 */                                 if (_jspx_meth_c_005fif_005f21(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3703 */                                 out.write(32);
/* 3704 */                                 out.write(10);
/*      */                               }
/*      */                               
/*      */                             }
/* 3708 */                             else if (mon)
/*      */                             {
/*      */ 
/* 3711 */                               out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 3712 */                               if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 3714 */                               out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                             }
/*      */                             
/*      */ 
/* 3718 */                             out.write(9);
/* 3719 */                             out.write(9);
/* 3720 */                             out.write(10);
/*      */                             
/* 3722 */                             ArrayList menupos = new ArrayList(5);
/* 3723 */                             menupos.add("350");
/* 3724 */                             pageContext.setAttribute("menupos", menupos);
/*      */                             
/* 3726 */                             out.write(10);
/* 3727 */                             out.write(10);
/* 3728 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 3729 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3732 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 3733 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3736 */                         if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3737 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                         }
/*      */                         
/* 3740 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 3741 */                         out.write(10);
/*      */                         
/* 3743 */                         PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3744 */                         _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 3745 */                         _jspx_th_tiles_005fput_005f5.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 3747 */                         _jspx_th_tiles_005fput_005f5.setName("UserArea");
/*      */                         
/* 3749 */                         _jspx_th_tiles_005fput_005f5.setType("string");
/* 3750 */                         int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 3751 */                         if (_jspx_eval_tiles_005fput_005f5 != 0) {
/* 3752 */                           if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 3753 */                             out = _jspx_page_context.pushBody();
/* 3754 */                             _jspx_th_tiles_005fput_005f5.setBodyContent((BodyContent)out);
/* 3755 */                             _jspx_th_tiles_005fput_005f5.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 3758 */                             out.write("\n\n<script>\nfunction showEditMonitor()\n{\ntoggleDiv(\"edit\");\n}\nfunction showAttribGraph(attribID) {\n       var featurelist = \"toolbar=no,status=no,menubar=no,width=450,height=300,scrollbars=yes\";        \n       var url = \"/WMIPerfCounters.do?method=showSingleGraphScreen&resourceid=");
/* 3759 */                             out.print(resourceid);
/* 3760 */                             out.write("&attributeid=\" + attribID;  \n       popUp = window.open(url,'popUp',featurelist);\n       return false;\n}\nfunction managenewHost()\n{ \n  if(document.AMActionForm.choosehost.value=='-1')\n  {\n        javascript:showDiv('newhost');\n  }\n  else\n  {\n        javascript:hideDiv('newhost');\n  }\n}\nfunction myonload()\n{\n");
/*      */                             
/* 3762 */                             if ((request.getParameter("editPage") != null) && (request.getParameter("editPage").equalsIgnoreCase("true")))
/*      */                             {
/*      */ 
/* 3765 */                               out.write("\ntoggleDiv(\"edit\");\n");
/*      */                             }
/*      */                             
/*      */ 
/* 3769 */                             out.write("\ngetGraphs();\n}\n\nfunction  edithostdetails()\n{\n if(document.AMActionForm.choosehost.value=='-1' ||document.AMActionForm.choosehost.value=='-2' )\n           {\n           alert('");
/* 3770 */                             out.print(FormatUtil.getString("am.webclient.wmi.alert.selecthosttoedit"));
/* 3771 */                             out.write("');\n           return;\n           }\nvar hostid=document.AMActionForm.choosehost.value;\nvar featurelist = \"toolbar=no,status=no,menubar=no,width=450,height=300,scrollbars=yes\";        \nurl=\"/WMIPerfCounters.do?method=EditHost&action=showform&hostid=\"+hostid+\"&resourceid=");
/* 3772 */                             out.print(resourceid);
/* 3773 */                             out.write("\";\n//fnOpenNewWindow(url);\nwindowReference = window.open(url,'windowName',featurelist);\nif (!windowReference.opener)\nwindowReference.opener = self;\nreturn false;\n}\n\nfunction validateAndSubmit()\n{\ndocument.AMActionForm.method.value='createWMIPerfmonitor';\n");
/* 3774 */                             if (_jspx_meth_logic_005fpresent_005f6(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                               return;
/* 3776 */                             out.write(10);
/*      */                             
/* 3778 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3779 */                             _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3780 */                             _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                             
/* 3782 */                             _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 3783 */                             int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3784 */                             if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                               for (;;) {
/* 3786 */                                 out.write("\n\n   if(trimAll(document.AMActionForm.displayname.value)==\"\")\n   {\n   \talert('");
/* 3787 */                                 out.print(FormatUtil.getString("am.webclient.wmi.emptymonitorname.text"));
/* 3788 */                                 out.write("');\n   \treturn;\n   }\n    var temp = trimAll(document.AMActionForm.pollInterval.value);\n   if((temp == '') || !(isPositiveInteger(temp)) || (temp == '0'))\n   {\n\talert(\"");
/* 3789 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.polling"));
/* 3790 */                                 out.write("\");\n\tdocument.AMActionForm.pollInterval.select();\n\treturn;\n   }\n   document.AMActionForm.method.value='createWMIPerfmonitor'\n   document.AMActionForm.submit();\n   ");
/* 3791 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3792 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3796 */                             if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3797 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                             }
/*      */                             
/* 3800 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3801 */                             out.write("\ndocument.AMActionForm.submit;\n\n}\n\n\n</script>\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n <tr>\n \t  <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 3802 */                             out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 3803 */                             out.write("&gt;");
/* 3804 */                             out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes("Generic WMI"));
/* 3805 */                             out.write(" &gt;<span class=\"bcactive\">");
/* 3806 */                             out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getDisplayName(request));
/* 3807 */                             out.write("</span></td>\n </tr>\n \n <tr>\n \n \t\t<td class=\"bcstrip\" height=\"2\"><img src=\"../images/spacer.gif\" height=\"2\" width=\"10\"></td>\n \t</tr>\n \t<tr>\n \t\t<td height=\"2\"><img src=\"../images/spacer.gif\" height=\"9\" width=\"10\"></td>\n\t</tr>\n </table>\n\n\n\n<div id=\"edit\" style=\"Display:none\">\n");
/*      */                             
/* 3809 */                             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 3810 */                             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 3811 */                             _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                             
/* 3813 */                             _jspx_th_html_005fform_005f0.setAction("/WMIPerfCounters.do");
/* 3814 */                             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 3815 */                             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                               for (;;) {
/* 3817 */                                 out.write("\n\n<input type=\"hidden\" name=\"method\"/>\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 3818 */                                 out.print((String)request.getAttribute("resourceid"));
/* 3819 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"updatemonitor\" value=\"true\"/>\n<table class=\"lrtbdarkborder\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"99%\">\n<tr>\n<td class=\"tableheadingbborder\" height=\"19\">");
/* 3820 */                                 out.print(FormatUtil.getString("am.webclient.wta.configurationdetails"));
/* 3821 */                                 out.write("&nbsp; :</td>\n<td class=\"tableheadingbborder\" height=\"19\" align=\"right\"><span class=\"bodytextboldwhiteun\" onclick=\"javascript:hideDiv('edit')\"><img src=\"../images/icon_minus.gif\" height=\"9\" hspace=\"5\" width=\"9\">\n<a href=\"javascript:hideDiv('edit')\" class=\"staticlinks\">");
/* 3822 */                                 out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 3823 */                                 out.write("</a></span></td>\n</tr>\n  <tr> \n    <td width=\"20%\" height=\"32\" valign='top'  class=\"bodytext\"> ");
/* 3824 */                                 out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 3825 */                                 out.write("<span class=\"mandatory\">*</span> </td>\n    <td width=\"80%\" class=\"bodytext\"> ");
/* 3826 */                                 if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3828 */                                 out.write(" \n    </td>\n  </tr>\n  <tr> \n      <td valign='top'  class=\"bodytext\"> ");
/* 3829 */                                 out.print(FormatUtil.getString("Description"));
/* 3830 */                                 out.write("</td>\n      <td  class=\"bodytext\"> ");
/* 3831 */                                 if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3833 */                                 out.write(" \n      </td>\n  </tr>\n  <tr>\n        <td height=\"28\" width=\"25%\" class=\"bodytext\" >");
/* 3834 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.polling"));
/* 3835 */                                 out.write("<span class=\"mandatory\">*</span></td>\n        <td height=\"28\" colspan=\"2\" > ");
/* 3836 */                                 if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3838 */                                 out.write("<span class=\"bodytext\">&nbsp;&nbsp;&nbsp;");
/* 3839 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.minutes"));
/* 3840 */                                 out.write("</span></td>\n  </tr>\n   <tr> \n       \n                <td class=\"bodytext\" width=\"19%\"> ");
/* 3841 */                                 out.print(FormatUtil.getString("am.webclient.wmi.selecthost.text"));
/* 3842 */                                 out.write("</td>\n                <td width=\"81%\"> ");
/*      */                                 
/* 3844 */                                 SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 3845 */                                 _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3846 */                                 _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 3848 */                                 _jspx_th_html_005fselect_005f0.setProperty("choosehost");
/*      */                                 
/* 3850 */                                 _jspx_th_html_005fselect_005f0.setOnchange("javascript:managenewHost()");
/*      */                                 
/* 3852 */                                 _jspx_th_html_005fselect_005f0.setStyle("width: 160px;");
/* 3853 */                                 int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3854 */                                 if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3855 */                                   if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3856 */                                     out = _jspx_page_context.pushBody();
/* 3857 */                                     _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3858 */                                     _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3861 */                                     out.write(" \n                  ");
/*      */                                     
/* 3863 */                                     OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3864 */                                     _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 3865 */                                     _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3867 */                                     _jspx_th_html_005foption_005f0.setValue("-2");
/* 3868 */                                     int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 3869 */                                     if (_jspx_eval_html_005foption_005f0 != 0) {
/* 3870 */                                       if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3871 */                                         out = _jspx_page_context.pushBody();
/* 3872 */                                         _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 3873 */                                         _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3876 */                                         out.print(FormatUtil.getString("am.webclient.wmi.selecthost.text"));
/* 3877 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 3878 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3881 */                                       if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3882 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3885 */                                     if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3886 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                     }
/*      */                                     
/* 3889 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 3890 */                                     out.write(32);
/*      */                                     
/* 3892 */                                     OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 3893 */                                     _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3894 */                                     _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3896 */                                     _jspx_th_html_005foption_005f1.setValue("-1");
/*      */                                     
/* 3898 */                                     _jspx_th_html_005foption_005f1.setStyle("font-weight: bold;");
/* 3899 */                                     int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3900 */                                     if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3901 */                                       if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3902 */                                         out = _jspx_page_context.pushBody();
/* 3903 */                                         _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3904 */                                         _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3907 */                                         out.print(FormatUtil.getString("am.webclient.wmi.newhost"));
/* 3908 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3909 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3912 */                                       if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3913 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3916 */                                     if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3917 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                     }
/*      */                                     
/* 3920 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f1);
/* 3921 */                                     out.write(" \n                  ");
/*      */                                     
/* 3923 */                                     String hostquery = "select ID,HOSTNAME from AM_SCRIPTHOSTDETAILS where MODE='WMI'";
/* 3924 */                                     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */                                     try
/*      */                                     {
/* 3927 */                                       ResultSet rs = AMConnectionPool.executeQueryStmt(hostquery);
/* 3928 */                                       while (rs.next())
/*      */                                       {
/*      */ 
/* 3931 */                                         out.write("\n                  ");
/*      */                                         
/* 3933 */                                         OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3934 */                                         _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 3935 */                                         _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                         
/* 3937 */                                         _jspx_th_html_005foption_005f2.setValue(rs.getString(1));
/* 3938 */                                         int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 3939 */                                         if (_jspx_eval_html_005foption_005f2 != 0) {
/* 3940 */                                           if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3941 */                                             out = _jspx_page_context.pushBody();
/* 3942 */                                             _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 3943 */                                             _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3946 */                                             out.print(rs.getString(2));
/* 3947 */                                             out.write(" \n                  ");
/* 3948 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 3949 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3952 */                                           if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3953 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3956 */                                         if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 3957 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                         }
/*      */                                         
/* 3960 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 3961 */                                         out.write(" \n                  ");
/*      */                                       }
/*      */                                       
/* 3964 */                                       rs.close();
/*      */                                     }
/*      */                                     catch (Exception exc) {}
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/* 3971 */                                     out.write("\n                  ");
/* 3972 */                                     int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 3973 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3976 */                                   if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3977 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3980 */                                 if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 3981 */                                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                 }
/*      */                                 
/* 3984 */                                 this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 3985 */                                 out.write("&nbsp;&nbsp;<a href=\"#\" class=\"staticlinks\" onClick=\"javascript:edithostdetails()\" >");
/* 3986 */                                 out.print(FormatUtil.getString("am.webclient.wmi.edithost"));
/* 3987 */                                 out.write("</a> </td>\n            </tr>\n<tr><td/>\n  <td><div id=\"newhost\" style=\"display:none;\"> \n          <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"grayfullborder\">\n            <tr class=\"yellowgrayborder\"> \n              <td height=28 width=\"25%\"  class=\"bodytext\">");
/* 3988 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.hostname"));
/* 3989 */                                 out.write("<span class=\"mandatory\">*</span></td>\n              <td height=28 width=\"75%\"> ");
/* 3990 */                                 if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3992 */                                 out.write("</td>\n            </tr>\n            <tr class=\"yellowgrayborder\"> \n              <td height=\"28\" width=\"25%\" class=\"bodytext\">");
/* 3993 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 3994 */                                 out.write("<span class=\"mandatory\">*</span></td>\n              <td height=\"28\" width=\"75%\">");
/* 3995 */                                 if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 3997 */                                 out.write("&nbsp;&nbsp; \n              </td>\n            </tr >\n            <tr class=\"yellowgrayborder\"> \n              <td width=\"25%\" height=\"28\" class=\"bodytext\">");
/* 3998 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/* 3999 */                                 out.write("<span class=\"mandatory\">*</span></td>\n              <td width=\"75%\" height=\"28\"> ");
/* 4000 */                                 if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 4002 */                                 out.write(" \n               </td>\n            </tr>\n            </table>\n        </div></td>\n  </tr>\n  <!-- new line -->\n</table>\n");
/*      */                                 
/* 4004 */                                 IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4005 */                                 _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 4006 */                                 _jspx_th_c_005fif_005f22.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 4008 */                                 _jspx_th_c_005fif_005f22.setTest("${empty param.wiz}");
/* 4009 */                                 int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 4010 */                                 if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                                   for (;;) {
/* 4012 */                                     out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"20%\" align=\"left\"  class=\"tablebottom\">&nbsp;</td>\n    <td width=\"80%\" height=\"31\" align=\"left\"  class=\"tablebottom\">\n      <input name=\"addwmiperf\" type=\"button\" class=\"buttons\" value=");
/* 4013 */                                     out.print(FormatUtil.getString("Update"));
/* 4014 */                                     out.write(" onClick=\"validateAndSubmit()\"/> \n      &nbsp;&nbsp;<input type=\"button\" value=\"");
/* 4015 */                                     out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 4016 */                                     out.write("\" class='buttons' onClick=\"toggleDiv('edit');\" />\n    </td>\n  </tr>\n</table>\n");
/* 4017 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 4018 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4022 */                                 if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 4023 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */                                 }
/*      */                                 
/* 4026 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 4027 */                                 out.write(10);
/* 4028 */                                 out.write(10);
/* 4029 */                                 out.write(10);
/* 4030 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 4031 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4035 */                             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 4036 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                             }
/*      */                             
/* 4039 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 4040 */                             out.write("\n<br>\n</div>  \n <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n        <tr> \n          <td width=\"60%\" height=\"26\" valign=\"top\" >\n <table width=\"96%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" class=\"lrtbdarkborder\">\n            <tr> \n          <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 4041 */                             out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 4042 */                             out.write(" </td>\n        </tr>\n        <tr> \n         <td width=\"32%\" class=\"monitorinfoodd\">");
/* 4043 */                             out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 4044 */                             out.write(" </td>\n         <td width=\"68%\" class=\"monitorinfoodd\"> ");
/* 4045 */                             out.print(getTrimmedText((String)request.getAttribute("resourcename"), 35));
/* 4046 */                             out.write("</td>\n        </tr>\n         <tr> \n                 <td class=\"monitorinfoodd\">");
/* 4047 */                             out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 4048 */                             out.write(" </td>\n                 <td class=\"monitorinfoodd\">");
/* 4049 */                             out.print(FormatUtil.getString("am.webclient.wmi.wmimonitorname.text"));
/* 4050 */                             out.write("</td>\n         </tr>\n         <tr> \n\t                 <td class=\"monitorinfoodd\">");
/* 4051 */                             out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 4052 */                             out.write(" </td>\n\t                 <td class=\"monitorinfoodd\">");
/* 4053 */                             out.print(getTrimmedText((String)request.getAttribute("host"), 35));
/* 4054 */                             out.write("</td>\n         </tr>\n         <tr> \n\t          <td class=\"monitorinfoeven\" valign=\"top\" width=\"30%\">");
/* 4055 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4056 */                             out.write("</td>\n\t          <td class=\"monitorinfoeven\" width=\"70%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4057 */                             if (_jspx_meth_c_005fout_005f18(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                               return;
/* 4059 */                             out.write("&attributeid=");
/* 4060 */                             out.print(healthid);
/* 4061 */                             out.write("')\">");
/* 4062 */                             out.print(getSeverityImageForHealth(alert.getProperty(request.getParameter("resourceid") + "#" + healthid)));
/* 4063 */                             out.write("</a>\n\t\t\t");
/* 4064 */                             out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + "7100" + "#" + "MESSAGE"), "7100", alert.getProperty(resourceid + "#" + "7100"), resourceid));
/* 4065 */                             out.write("\n\t\t\t");
/* 4066 */                             if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, "7100") != 0) {
/* 4067 */                               out.write(" \n\t\t\t<br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 4068 */                               out.print(resourceid + "_7100");
/* 4069 */                               out.write("&monitortype=");
/* 4070 */                               out.print(resourcetype);
/* 4071 */                               out.write("')\">");
/* 4072 */                               out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 4073 */                               out.write("</a></span>\n            ");
/*      */                             }
/* 4075 */                             out.write("\n\t\t</td>\n        </tr>\n       ");
/*      */                             
/* 4077 */                             EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 4078 */                             _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 4079 */                             _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                             
/* 4081 */                             _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 4082 */                             int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 4083 */                             if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                               for (;;) {
/* 4085 */                                 out.write("\n               <tr> \n                 <td class=\"monitorinfoeven\">");
/* 4086 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 4087 */                                 out.write("</td>\n                 <td class=\"monitorinfoeven\">");
/* 4088 */                                 out.print(lastdatacollectedtime);
/* 4089 */                                 out.write("</td>\n               </tr>\n               <tr> \n                 <td class=\"monitorinfoodd\">");
/* 4090 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 4091 */                                 out.write("</td>\n                 <td class=\"monitorinfoodd\">");
/* 4092 */                                 out.print(nextdatacollectedtime);
/* 4093 */                                 out.write("</td>\n               </tr>\n               ");
/* 4094 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 4095 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4099 */                             if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 4100 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                             }
/*      */                             
/* 4103 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 4104 */                             out.write("\n               ");
/*      */                             
/* 4106 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4107 */                             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 4108 */                             _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                             
/* 4110 */                             _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 4111 */                             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 4112 */                             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                               for (;;) {
/* 4114 */                                 out.write("\n               <tr> \n                 <td class=\"monitorinfoeven\">");
/* 4115 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 4116 */                                 out.write("</td>\n                 <td class=\"monitorinfoeven\">");
/*      */                                 
/* 4118 */                                 out.write("</td>\n               </tr>\n               <tr> \n                 <td class=\"monitorinfoodd\">");
/* 4119 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 4120 */                                 out.write("</td>\n                 <td class=\"monitorinfoodd\">");
/*      */                                 
/* 4122 */                                 out.write("</td>\n               </tr>\n        ");
/* 4123 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 4124 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4128 */                             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 4129 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                             }
/*      */                             
/* 4132 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 4133 */                             out.write("\n        ");
/* 4134 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_trstrip.jsp", out, false);
/* 4135 */                             out.write("  \n\t    </table>\n  </td>\n\n");
/* 4136 */                             wlsGraph.setParam(resourceid, "AVAILABILITY");
/* 4137 */                             out.write("\n\n\n <td width=\"40%\" valign=\"top\"  ><table width=\"98%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n            <tr> \n\t           <td height=\"28\" colspan=\"3\" class=\"tableheadingbborder\">");
/* 4138 */                             out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 4139 */                             out.write("</td>\n        </tr>\n         <tr> \n\t           <td colspan=\"3\"> <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t               <tr> \n\t                 <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 4140 */                             if (_jspx_meth_c_005fout_005f19(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                               return;
/* 4142 */                             out.write("&period=1')\"> \n\t                   <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4143 */                             out.print(seven_days_text);
/* 4144 */                             out.write("\"></a></td>\n\t                 <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 4145 */                             if (_jspx_meth_c_005fout_005f20(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                               return;
/* 4147 */                             out.write("&period=2')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4148 */                             out.print(thiry_days_text);
/* 4149 */                             out.write("\"></a></td>\n\t               </tr>\n            </table></td>\n         <tr align=\"center\"> \n          <td height=\"28\" colspan=\"3\" class=\"whitegrayborder\"> ");
/* 4150 */                             if (_jspx_meth_awolf_005fpiechart_005f0(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                               return;
/* 4152 */                             out.write("</td>\n        </tr>\n        <tr> \n          <td width=\"39%\" height=\"25\" class=\"yellowgrayborder\"  title=\"");
/* 4153 */                             out.print(FormatUtil.getString("am.webclient.script.tooltip"));
/* 4154 */                             out.write(34);
/* 4155 */                             out.write(62);
/* 4156 */                             out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 4157 */                             out.write(" :</td>\n          <td width=\"10%\" height=\"25\" align=\"left\" class=\"yellowgrayborder\"> <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4158 */                             out.print(resourceid);
/* 4159 */                             out.write("&attributeid=");
/* 4160 */                             out.print(availabilitykeys.get(resourcetype));
/* 4161 */                             out.write("&alertconfigurl=");
/* 4162 */                             out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=" + availabilitykeys.get(resourcetype) + "&attributeToSelect=" + availabilitykeys.get(resourcetype) + "&redirectto="));
/* 4163 */                             out.write("')\"> \n            ");
/* 4164 */                             out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "7101")));
/* 4165 */                             out.write(" \n            </a></td>\n          <td width=\"51%\" align=\"left\" class=\"yellowgrayborder\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4166 */                             out.print(resourceid);
/* 4167 */                             out.write("&attributeIDs=");
/* 4168 */                             out.print(availabilitykeys.get(resourcetype));
/* 4169 */                             out.write(44);
/* 4170 */                             out.print(healthkeys.get(resourcetype));
/* 4171 */                             out.write("&attributeToSelect=");
/* 4172 */                             out.print(availabilitykeys.get(resourcetype));
/* 4173 */                             out.write("&redirectto=");
/* 4174 */                             out.print(encodeurl);
/* 4175 */                             out.write("\" class=\"links\">");
/* 4176 */                             out.print(ALERTCONFIG_TEXT);
/* 4177 */                             out.write("</a> \n          </td>\n        </tr> </table>\n</td>\n</tr>\n</table>\n<br>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 4178 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 4179 */                             out.write("</td></tr></table>\n<br>\n\n\n\n\n");
/* 4180 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f5.doAfterBody();
/* 4181 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4184 */                           if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 4185 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4188 */                         if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 4189 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5); return;
/*      */                         }
/*      */                         
/* 4192 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5);
/* 4193 */                         out.write(10);
/* 4194 */                         out.write(10);
/* 4195 */                         out.write(10);
/*      */                         
/* 4197 */                         PutTag _jspx_th_tiles_005fput_005f6 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 4198 */                         _jspx_th_tiles_005fput_005f6.setPageContext(_jspx_page_context);
/* 4199 */                         _jspx_th_tiles_005fput_005f6.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 4201 */                         _jspx_th_tiles_005fput_005f6.setName("CustomUserArea");
/*      */                         
/* 4203 */                         _jspx_th_tiles_005fput_005f6.setType("string");
/* 4204 */                         int _jspx_eval_tiles_005fput_005f6 = _jspx_th_tiles_005fput_005f6.doStartTag();
/* 4205 */                         if (_jspx_eval_tiles_005fput_005f6 != 0) {
/* 4206 */                           if (_jspx_eval_tiles_005fput_005f6 != 1) {
/* 4207 */                             out = _jspx_page_context.pushBody();
/* 4208 */                             _jspx_th_tiles_005fput_005f6.setBodyContent((BodyContent)out);
/* 4209 */                             _jspx_th_tiles_005fput_005f6.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4212 */                             out.write("  \n\n ");
/* 4213 */                             out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/* 4218 */                             Hashtable SingletonClasses = null;
/*      */                             
/*      */ 
/*      */ 
/* 4222 */                             Hashtable InstancedClasses = null;
/*      */                             
/*      */ 
/*      */ 
/* 4226 */                             Hashtable AllClasses = null;
/*      */                             
/*      */ 
/*      */ 
/* 4230 */                             Hashtable wmidata = null;
/*      */                             
/*      */ 
/*      */ 
/* 4234 */                             Hashtable healthids = new Hashtable();
/*      */                             
/*      */ 
/*      */ 
/* 4238 */                             Hashtable instances = new Hashtable();
/*      */                             
/*      */ 
/*      */ 
/* 4242 */                             Hashtable classAttributes = new Hashtable();
/*      */                             
/*      */ 
/*      */ 
/* 4246 */                             com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil dbutil = new com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil();
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                             try
/*      */                             {
/* 4258 */                               wmidata = dbutil.getWMIInput(resourceid, "Singleton Class");
/*      */                               
/*      */ 
/*      */ 
/* 4262 */                               SingletonClasses = (Hashtable)wmidata.get("classes");
/*      */                               
/*      */ 
/*      */ 
/* 4266 */                               healthids = (Hashtable)wmidata.get("healthids");
/*      */                               
/*      */ 
/*      */ 
/* 4270 */                               instances = (Hashtable)wmidata.get("instances");
/*      */                               
/*      */ 
/*      */ 
/* 4274 */                               classAttributes = (Hashtable)wmidata.get("classAttributes");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                             }
/*      */                             catch (Exception ex)
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4298 */                               ex.printStackTrace();
/*      */                             }
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4311 */                             out.write("\n\n\n\n\n\n\n\n");
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/* 4316 */                             Enumeration AttribKeys = null;
/*      */                             
/*      */ 
/*      */ 
/* 4320 */                             Enumeration conditionkeys = null;
/*      */                             
/*      */ 
/*      */ 
/* 4324 */                             Hashtable singleclass = null;
/*      */                             
/*      */ 
/*      */ 
/* 4328 */                             Hashtable singleinstance = null;
/*      */                             
/*      */ 
/*      */ 
/* 4332 */                             ArrayList AttribList = null;
/*      */                             
/*      */ 
/*      */ 
/* 4336 */                             String ClassName = "";
/*      */                             
/*      */ 
/*      */ 
/* 4340 */                             String AttribID = "";
/*      */                             
/*      */ 
/*      */ 
/* 4344 */                             String AttibName = "";
/*      */                             
/*      */ 
/*      */ 
/* 4348 */                             String value = "";
/*      */                             
/*      */ 
/*      */ 
/* 4352 */                             int tempmarker = 0;
/*      */                             
/*      */ 
/*      */ 
/* 4356 */                             String StyleClass = "whitegrayborder";
/*      */                             
/*      */ 
/*      */ 
/* 4360 */                             String filtercondition = "";
/*      */                             
/*      */ 
/*      */ 
/* 4364 */                             String filterconditionvalue = "";
/*      */                             
/*      */ 
/*      */ 
/* 4368 */                             String classhealthid = "-1";
/*      */                             
/*      */ 
/*      */ 
/* 4372 */                             String attributeIDs = "";
/*      */                             
/*      */ 
/*      */ 
/* 4376 */                             Enumeration instancekeys = null;
/*      */                             
/*      */ 
/*      */ 
/* 4380 */                             Enumeration classkeys = SingletonClasses.keys();
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4392 */                             if ((SingletonClasses != null) && (SingletonClasses.size() > 0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4401 */                               out.write("\n\n\n\n\n\n\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\n\n\n  <tr>\n\n\n\n  ");
/* 4402 */                               if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 4403 */                                 out.write("\n\n\n\n  <td width=\"16%\"></td>\n\n\n\n  ");
/*      */                               }
/* 4405 */                               out.write("\n\n\n\n  <td width=\"69%\">\n\n\n\n  <tr>\n\n\n\n   ");
/*      */                               
/* 4407 */                               ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4408 */                               _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 4409 */                               _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f6);
/* 4410 */                               int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 4411 */                               if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                 for (;;) {
/* 4413 */                                   out.write("\n\n\n\n      ");
/* 4414 */                                   if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*      */                                     return;
/* 4416 */                                   out.write("\n\n\n\n      ");
/*      */                                   
/* 4418 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4419 */                                   _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 4420 */                                   _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 4421 */                                   int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 4422 */                                   if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                     for (;;) {
/* 4424 */                                       out.write("\n\n\n\n\t  ");
/*      */                                       
/*      */ 
/*      */ 
/*      */ 
/* 4429 */                                       if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/* 4434 */                                         out.write("\n\n\n\n      <td  width=\"15%\"> &nbsp;</td>\n\n\n\n\t  ");
/*      */                                       }
/* 4436 */                                       out.write("\n\n\n\n      \n\n\n\n      ");
/* 4437 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 4438 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4442 */                                   if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 4443 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                   }
/*      */                                   
/* 4446 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4447 */                                   out.write("\n\n\n\n");
/* 4448 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 4449 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4453 */                               if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 4454 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                               }
/*      */                               
/* 4457 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4458 */                               out.write("\n\n\n\n \n\n\n\n  \n\n\n\n  <td>\n\n\n\n<table class=\"lrtbdarkborder\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\n\n\n<tr>\n\n\n\n    <td colspan=\"3\" class=\"tableheadingbborder\" height=\"19\">\n\n\n\n       <span class=\"topdesc\"> ");
/* 4459 */                               out.print((String)request.getAttribute("resourcename"));
/* 4460 */                               out.write("</span></td>\n\n\n\n    \n\n\n\n\n\n\n\n</tr>   \n\n\n\n<tr>\n\n\n\n\t<td class=\"columnheading\" width=\"36%\"> ");
/* 4461 */                               out.print(FormatUtil.getString("am.webclient.wmi.attributename.text"));
/* 4462 */                               out.write("</td> \n\n\n\n        <td class=\"columnheading\" width=\"30%\"> ");
/* 4463 */                               out.print(FormatUtil.getString("Value"));
/* 4464 */                               out.write("</td>\n\n\n\n        <td class=\"columnheading\" width=\"7%\" > ");
/* 4465 */                               out.print(FormatUtil.getString("am.webclient.wmi.actions.text"));
/* 4466 */                               out.write("</td>\n\n\n\n</tr>\n\n\n\n");
/*      */                               
/*      */ 
/*      */ 
/*      */                               label14108:
/*      */                               
/*      */ 
/*      */                               for (;;)
/*      */                               {
/* 4475 */                                 if (!classkeys.hasMoreElements()) {
/*      */                                   break label14109;
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4483 */                                 ClassName = (String)classkeys.nextElement();
/*      */                                 
/*      */ 
/*      */ 
/* 4487 */                                 singleclass = (Hashtable)SingletonClasses.get(ClassName);
/*      */                                 
/*      */ 
/*      */ 
/* 4491 */                                 conditionkeys = singleclass.keys();
/*      */                                 
/*      */                                 for (;;)
/*      */                                 {
/* 4495 */                                   if (!conditionkeys.hasMoreElements()) {
/*      */                                     break label14108;
/*      */                                   }
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4503 */                                   filtercondition = (String)conditionkeys.nextElement();
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                   try
/*      */                                   {
/* 4511 */                                     filterconditionvalue = (String)instances.get(filtercondition);
/*      */                                   }
/*      */                                   catch (Exception ex) {}
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4539 */                                   attributeIDs = "";
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                   try
/*      */                                   {
/* 4547 */                                     classhealthid = (String)healthids.get(ClassName);
/*      */                                     
/*      */ 
/*      */ 
/* 4551 */                                     attributeIDs = attributeIDs + classhealthid + ",";
/*      */                                   }
/*      */                                   catch (Exception ex) {}
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4575 */                                   if (!filterconditionvalue.equals("Singleton Class")) {
/*      */                                     break;
/*      */                                   }
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4583 */                                   singleinstance = (Hashtable)singleclass.get(filtercondition);
/*      */                                   
/*      */ 
/*      */ 
/* 4587 */                                   AttribKeys = singleinstance.keys();
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4595 */                                   while (AttribKeys.hasMoreElements())
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4603 */                                     AttribID = (String)AttribKeys.nextElement();
/*      */                                     
/*      */ 
/*      */ 
/* 4607 */                                     attributeIDs = attributeIDs + AttribID + ",";
/*      */                                   }
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4620 */                                   out.write("\n\n\n\n<tr>\n\n\n\n\t<td class=\"secondchildnode\" colspan=\"2\" onmouseover=\"ddrivetip(this,event,'");
/* 4621 */                                   out.print(ClassName);
/* 4622 */                                   out.write("',null,true,'#000000',300)\" onmouseout=\"hideddrivetip()\" height=\"20\"><span class=\"bodytextbold\">");
/* 4623 */                                   out.print(ClassName);
/* 4624 */                                   out.write("</span> </td> \n\n\n\n\t<td class=\"secondchildnode\" align=\"left\"><a href='jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4625 */                                   out.print(filtercondition);
/* 4626 */                                   out.write("&attributeIDs=");
/* 4627 */                                   out.print(attributeIDs);
/* 4628 */                                   out.write("&attributeToSelect=");
/* 4629 */                                   out.print(classhealthid);
/* 4630 */                                   out.write("&redirectto=");
/* 4631 */                                   out.print(encodeurl);
/* 4632 */                                   out.write("' class=\"staticlinks\"> <img src=\"/images/icon_associateaction.gif\" title=\"");
/* 4633 */                                   out.print(ALERTCONFIG_TEXT);
/* 4634 */                                   out.write("\" border=\"0\" ></td>\n\n\n\n</tr>\n\n\n\n\n\n\n\n");
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/* 4639 */                                   AttribKeys = singleinstance.keys();
/*      */                                   
/*      */ 
/*      */ 
/* 4643 */                                   tempmarker = 0;
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4655 */                                   while (AttribKeys.hasMoreElements())
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4663 */                                     String date = "";
/*      */                                     
/*      */ 
/*      */ 
/* 4667 */                                     String shortDate = "";
/*      */                                     
/*      */ 
/*      */ 
/* 4671 */                                     AttribID = (String)AttribKeys.nextElement();
/*      */                                     
/*      */ 
/*      */ 
/* 4675 */                                     AttribList = (ArrayList)singleinstance.get(AttribID);
/*      */                                     
/*      */ 
/*      */ 
/* 4679 */                                     AttibName = (String)AttribList.get(0);
/*      */                                     
/*      */ 
/*      */ 
/* 4683 */                                     value = (String)AttribList.get(1);
/*      */                                     
/*      */ 
/*      */ 
/* 4687 */                                     if ((value == null) || (value.equalsIgnoreCase("null")))
/*      */                                     {
/*      */ 
/*      */ 
/* 4691 */                                       value = "-";
/*      */                                     }
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4699 */                                     StyleClass = "whitegrayborder";
/*      */                                     
/*      */ 
/*      */ 
/* 4703 */                                     if (tempmarker % 2 == 0)
/*      */                                     {
/*      */ 
/*      */ 
/* 4707 */                                       StyleClass = "whitegrayborder";
/*      */ 
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/*      */ 
/* 4715 */                                       StyleClass = "yellowgrayborder";
/*      */                                     }
/*      */                                     
/*      */ 
/* 4719 */                                     tempmarker++;
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/* 4724 */                                     out.write("\n\n\n\n<tr>\n\n\n\n<td class=\"");
/* 4725 */                                     out.print(StyleClass);
/* 4726 */                                     out.write("\" width=\"35%\">");
/* 4727 */                                     out.print(AttibName);
/* 4728 */                                     out.write("</td>\n\n\n\n<td class=\"");
/* 4729 */                                     out.print(StyleClass);
/* 4730 */                                     out.write("\" width=\"15%\">");
/* 4731 */                                     out.print(value);
/* 4732 */                                     out.write("</td>\n\n\n\n<td class=\"");
/* 4733 */                                     out.print(StyleClass);
/* 4734 */                                     out.write("\" width=\"15%\"> <a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4735 */                                     out.print(filtercondition);
/* 4736 */                                     out.write("&attributeIDs=");
/* 4737 */                                     out.print(AttribID);
/* 4738 */                                     out.write("&attributeToSelect=");
/* 4739 */                                     out.print(AttribID);
/* 4740 */                                     out.write("&redirectto=");
/* 4741 */                                     out.print(encodeurl);
/* 4742 */                                     out.write("'> <img src=\"/images/icon_associateaction.gif\" title=\"");
/* 4743 */                                     out.print(ALERTCONFIG_TEXT);
/* 4744 */                                     out.write("\" border=\"0\" ></a> &nbsp;\n\n\n\n<a href=\"javascript:void(0)\" onclick=\"showAttribGraph('");
/* 4745 */                                     out.print(AttribID + "|" + filtercondition);
/* 4746 */                                     out.write("')\"><img src='/images/icon_linegraph.gif' title='Show Graph' border='0' ></a>\n\n\n\n</td>\n\n\n\n</tr>\n\n\n\n");
/*      */                                   }
/*      */                                 }
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                               label14109:
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4768 */                               out.write("</table>\n\n\n\n</td>\n\n\n\n</td>\n\n\n\n\n\n\n\n<td width=\"16%\"></td>\n\n</tr>\n\n\n\n  </td>\n\n\n\n\n\n\n\n  </tr>\n\n\n\n</table>\n\n\n\n\n\n\n\n</td>\n\n\n\n</tr>\n\n\n\n</table>\n\n\n\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\n\n\n  <tr>\n\n\n\n ");
/*      */                               
/* 4770 */                               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4771 */                               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 4772 */                               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f6);
/* 4773 */                               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 4774 */                               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                 for (;;) {
/* 4776 */                                   out.write("\n\n\n\n      ");
/* 4777 */                                   if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/*      */                                     return;
/* 4779 */                                   out.write("\n\n\n\n       ");
/*      */                                   
/* 4781 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4782 */                                   _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 4783 */                                   _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 4784 */                                   int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 4785 */                                   if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                     for (;;) {
/* 4787 */                                       out.write("\n\n\n\n\t   ");
/* 4788 */                                       if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 4789 */                                         out.write("\n\n\n\n       <td   align=\"left\" valign=\"top\"  bgcolor=\"#FFFFFF\"> &nbsp;\n\n\n\n       </td>\n\n\n\n\t   ");
/*      */                                       } else {
/* 4791 */                                         out.write("\n\n\n\n\t   <td>&nbsp;</td>\n\n\n\n\t   ");
/*      */                                       }
/* 4793 */                                       out.write("\n\n\n\n       ");
/* 4794 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 4795 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4799 */                                   if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 4800 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                   }
/*      */                                   
/* 4803 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 4804 */                                   out.write("\n\n\n\n ");
/* 4805 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 4806 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4810 */                               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 4811 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                               }
/*      */                               
/* 4814 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 4815 */                               out.write("\n\n\n\n    <td >&nbsp;</td>\n\n\n\n  </table>\n\n\n\n");
/*      */                             }
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4825 */                             out.write("\n\n\n\n\n\n\n\n");
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4838 */                             wmidata = dbutil.getWMIInput(resourceid, "Instanced Class");
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4846 */                             InstancedClasses = (Hashtable)wmidata.get("classes");
/*      */                             
/*      */ 
/*      */ 
/* 4850 */                             healthids = (Hashtable)wmidata.get("healthids");
/*      */                             
/*      */ 
/*      */ 
/* 4854 */                             instances = (Hashtable)wmidata.get("instances");
/*      */                             
/*      */ 
/*      */ 
/* 4858 */                             classAttributes = (Hashtable)wmidata.get("classAttributes");
/*      */                             
/*      */ 
/*      */ 
/* 4862 */                             classkeys = InstancedClasses.keys();
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4870 */                             String attributeToSelect = "";
/*      */                             
/*      */ 
/*      */ 
/* 4874 */                             while (classkeys.hasMoreElements())
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4886 */                               ClassName = (String)classkeys.nextElement();
/*      */                               
/*      */ 
/*      */ 
/* 4890 */                               Hashtable tempattributes = (Hashtable)classAttributes.get(ClassName);
/*      */                               
/*      */ 
/*      */ 
/* 4894 */                               Enumeration tempattributeskeys = tempattributes.keys();
/*      */                               
/*      */ 
/*      */ 
/* 4898 */                               ArrayList templist = new ArrayList();
/*      */                               
/*      */ 
/*      */ 
/* 4902 */                               while (tempattributeskeys.hasMoreElements())
/*      */                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4910 */                                 String singattribute = (String)tempattributeskeys.nextElement();
/*      */                                 
/*      */ 
/*      */ 
/* 4914 */                                 templist.add(singattribute);
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4922 */                               int attribsize = templist.size();
/*      */                               
/*      */ 
/*      */ 
/* 4926 */                               String tablewidth = "84%";
/*      */                               
/*      */ 
/*      */ 
/* 4930 */                               if (attribsize > 4)
/*      */                               {
/*      */ 
/*      */ 
/* 4934 */                                 tablewidth = attribsize * 200 + "px";
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4943 */                               out.write("\n\n\n\n\n\n\n\n<table width=\"");
/* 4944 */                               out.print(tablewidth);
/* 4945 */                               out.write("\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\n\n\n  <tr>\n\n\n\n  ");
/*      */                               
/* 4947 */                               ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4948 */                               _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 4949 */                               _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f6);
/* 4950 */                               int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 4951 */                               if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                 for (;;) {
/* 4953 */                                   out.write("\n\n\n\n      ");
/* 4954 */                                   if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/*      */                                     return;
/* 4956 */                                   out.write("\n\n\n\n      ");
/*      */                                   
/* 4958 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4959 */                                   _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 4960 */                                   _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 4961 */                                   int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 4962 */                                   if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                                     for (;;) {
/* 4964 */                                       out.write("\n\n\n\n\t  ");
/* 4965 */                                       if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 4966 */                                         out.write("\n\n\n\n      \n\n\n\n\t\t");
/*      */                                       } else {
/* 4968 */                                         out.write("\n\n\n\n\t\t<td>&nbsp;</td>\n\n\n\n\t\t");
/*      */                                       }
/* 4970 */                                       out.write("\n\n\n\n      ");
/* 4971 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 4972 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4976 */                                   if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 4977 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                                   }
/*      */                                   
/* 4980 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 4981 */                                   out.write("\n\n\n\n  ");
/* 4982 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 4983 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4987 */                               if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 4988 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                               }
/*      */                               
/* 4991 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 4992 */                               out.write("\n\n\n\n  \n\n\n\n<td width=\"100%\">\n\n\n\n\n\n\n\n<table width=\"100%\" border=\"0\">\n\n\n\n<tr>\n\n\n\n");
/* 4993 */                               if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 4994 */                                 out.write("\n\n\n\n<td style=\"float:left; padding-left:225px;\">\n\n");
/*      */                               } else {
/* 4996 */                                 out.write("\n\n\n\n<td style=\"float:left; padding-left:5px;\">\n\n\n\n");
/*      */                               }
/* 4998 */                               out.write("\n\n\n\n<table class=\"lrtbdarkborder\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\n\n\n<tr>\n\n\n\n    <td   class=\"tableheadingbborder\" height=\"19\" colspan=\"");
/* 4999 */                               out.print(tempattributes.size() + 2);
/* 5000 */                               out.write("\">\n\n\n\n       <span class=\"topdesc\">");
/* 5001 */                               out.print(ClassName);
/* 5002 */                               out.write(" </span></td>\n\n\n\n \n\n\n\n </tr>   \n\n\n\n <tr>\n\n\n\n<td class=\"columnheading\" valign=\"center\" ><div style=\"width:100px;overflow:auto\"> ");
/* 5003 */                               out.print(FormatUtil.getString("am.webclient.wmi.instancename.text"));
/* 5004 */                               out.write("</div></td>\n\n\n\n");
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/* 5009 */                               attributeIDs = "";
/*      */                               
/*      */ 
/*      */ 
/* 5013 */                               for (int h = 0; h < templist.size(); h++)
/*      */                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5021 */                                 String tempattrib = (String)templist.get(h);
/*      */                                 
/*      */ 
/*      */ 
/* 5025 */                                 String columnattribute = (String)tempattributes.get(tempattrib);
/*      */                                 
/*      */ 
/*      */ 
/* 5029 */                                 if (h != templist.size() - 1)
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5037 */                                   attributeIDs = attributeIDs + tempattrib + ",";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5053 */                                   attributeIDs = attributeIDs + tempattrib;
/*      */                                   
/*      */ 
/*      */ 
/* 5057 */                                   attributeToSelect = tempattrib;
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5070 */                                 out.write("\n\n\n\n <td class=\"columnheading\" valign=\"center\" title=\"");
/* 5071 */                                 out.print(columnattribute);
/* 5072 */                                 out.write("\">\n\n\n\n<div style=\"width:120px;overflow:hidden\"> ");
/* 5073 */                                 out.print(columnattribute);
/* 5074 */                                 out.write("</div>\n\n\n\n </td>\n\n\n\n ");
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/* 5080 */                               out.write("\n\n\n\n <td class=\"columnheading\" align=\"center\">");
/* 5081 */                               out.print(FormatUtil.getString("Actions"));
/* 5082 */                               out.write("</td>\n\n\n\n </tr>\n\n\n\n ");
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/* 5087 */                               singleclass = (Hashtable)InstancedClasses.get(ClassName);
/*      */                               
/*      */ 
/*      */ 
/* 5091 */                               conditionkeys = singleclass.keys();
/*      */                               
/*      */ 
/*      */ 
/* 5095 */                               tempmarker = 0;
/*      */                               
/*      */ 
/*      */ 
/* 5099 */                               while (conditionkeys.hasMoreElements())
/*      */                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5107 */                                 filtercondition = (String)conditionkeys.nextElement();
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                 try
/*      */                                 {
/* 5115 */                                   filterconditionvalue = (String)instances.get(filtercondition);
/*      */                                   
/*      */ 
/*      */ 
/* 5119 */                                   if ((filterconditionvalue == null) || (filterconditionvalue.equalsIgnoreCase("null")))
/*      */                                   {
/*      */ 
/*      */ 
/* 5123 */                                     filterconditionvalue = "-";
/*      */                                   }
/*      */                                   
/*      */ 
/* 5127 */                                   filterconditionvalue = findReplace(filterconditionvalue, "\\\\", "\\");
/*      */                                   
/*      */ 
/*      */ 
/* 5131 */                                   filterconditionvalue = findReplace(filterconditionvalue, "Name=", "");
/*      */                                   
/*      */ 
/*      */ 
/* 5135 */                                   filterconditionvalue = findReplace(filterconditionvalue, "'", "");
/*      */                                 }
/*      */                                 catch (Exception ex) {}
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                 try
/*      */                                 {
/* 5167 */                                   classhealthid = (String)healthids.get(ClassName);
/*      */                                   
/*      */ 
/*      */ 
/* 5171 */                                   attributeIDs = attributeIDs + "," + classhealthid;
/*      */                                 }
/*      */                                 catch (Exception ex) {}
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5195 */                                 singleinstance = (Hashtable)singleclass.get(filtercondition);
/*      */                                 
/*      */ 
/*      */ 
/* 5199 */                                 StyleClass = "whitegrayborder";
/*      */                                 
/*      */ 
/*      */ 
/* 5203 */                                 if (tempmarker % 2 == 0)
/*      */                                 {
/*      */ 
/*      */ 
/* 5207 */                                   StyleClass = "whitegrayborder";
/*      */ 
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/*      */ 
/* 5215 */                                   StyleClass = "yellowgrayborder";
/*      */                                 }
/*      */                                 
/*      */ 
/* 5219 */                                 tempmarker++;
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/* 5224 */                                 out.write("\n\n\n\n<tr> \n\n\n\n<td class=");
/* 5225 */                                 out.print(StyleClass);
/* 5226 */                                 out.write(" align=\"left\">\n\n\n\n");
/* 5227 */                                 out.print(filterconditionvalue);
/* 5228 */                                 out.write("\n\n\n\n</td>\n\n\n\n");
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/* 5233 */                                 for (int g = 0; g < templist.size(); g++)
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5241 */                                   String tempattr = (String)templist.get(g);
/*      */                                   
/*      */ 
/*      */ 
/* 5245 */                                   String columnvalue = "-";
/*      */                                   
/*      */ 
/*      */ 
/* 5249 */                                   ArrayList attribdetails = (ArrayList)singleinstance.get(tempattr);
/*      */                                   
/*      */ 
/*      */ 
/* 5253 */                                   if (attribdetails != null)
/*      */                                   {
/*      */ 
/*      */ 
/* 5257 */                                     columnvalue = (String)attribdetails.get(1);
/*      */                                   }
/*      */                                   
/*      */ 
/* 5261 */                                   if ((columnvalue == null) || (columnvalue.equalsIgnoreCase("null")))
/*      */                                   {
/*      */ 
/*      */ 
/* 5265 */                                     columnvalue = "-";
/*      */                                   }
/*      */                                   
/*      */ 
/*      */ 
/* 5270 */                                   out.write("\n\n\n\n\n\n\n\n<td class=");
/* 5271 */                                   out.print(StyleClass);
/* 5272 */                                   out.write("  title='");
/* 5273 */                                   out.print(FormatUtil.getString("am.webclient.wmi.showgraph.text"));
/* 5274 */                                   out.write("'>\n\n\n\n");
/* 5275 */                                   if (!columnvalue.equals("-"))
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/* 5280 */                                     out.write("\n\n\n\n<a href=\"javascript:void(0)\" onclick=\"showAttribGraph('");
/* 5281 */                                     out.print(tempattr + "|" + filtercondition);
/* 5282 */                                     out.write("')\" class=\"staticlinks\" >");
/* 5283 */                                     out.print(columnvalue);
/* 5284 */                                     out.write("</a>\n\n\n\n");
/*      */                                   } else {
/* 5286 */                                     out.write("\n\n\n\n");
/* 5287 */                                     out.print(columnvalue);
/* 5288 */                                     out.write("\n\n\n\n");
/*      */                                   }
/* 5290 */                                   out.write("\n\n\n\n</td>\n\n\n\n");
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5300 */                                 out.write("\n\n\n\n<td class=\"");
/* 5301 */                                 out.print(StyleClass);
/* 5302 */                                 out.write("\" align=\"center\"><a href='jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5303 */                                 out.print(filtercondition);
/* 5304 */                                 out.write("&attributeIDs=");
/* 5305 */                                 out.print(attributeIDs);
/* 5306 */                                 out.write("&attributeToSelect=");
/* 5307 */                                 out.print(classhealthid);
/* 5308 */                                 out.write("&redirectto=");
/* 5309 */                                 out.print(encodeurl);
/* 5310 */                                 out.write("'> <img src=\"/images/icon_associateaction.gif\" title=\"");
/* 5311 */                                 out.print(ALERTCONFIG_TEXT);
/* 5312 */                                 out.write("\" border=\"0\" ></a> </td>\n\n\n\n</tr>\n\n\n\n");
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5322 */                               out.write("\n\n\n\n\n\n\n\n</table>\n\n\n\n</td>\n\n\n\n</tr>\n\n\n\n</table>\n\n\n\n</td>\n\n\n\n\n\n\n\n</tr>\n\n\n\n</table>\n\n\n\n\n\n\n\n<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\n\n\n  <tr>\n\n\n\n  ");
/*      */                               
/* 5324 */                               ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5325 */                               _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 5326 */                               _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f6);
/* 5327 */                               int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 5328 */                               if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                                 for (;;) {
/* 5330 */                                   out.write("\n\n\n\n        ");
/* 5331 */                                   if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/*      */                                     return;
/* 5333 */                                   out.write("\n\n\n\n        ");
/*      */                                   
/* 5335 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5336 */                                   _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 5337 */                                   _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 5338 */                                   int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 5339 */                                   if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                                     for (;;) {
/* 5341 */                                       out.write("\n\n\n\n\t\t");
/* 5342 */                                       if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 5343 */                                         out.write("\n\n\n\n        <td  align=\"left\" valign=\"top\"  bgcolor=\"#FFFFFF\"> &nbsp;</td>\n\n\n\n\t\t");
/*      */                                       } else {
/* 5345 */                                         out.write("\n\n\n\n\t\t<td> &nbsp; </td>\n\n\n\n\t\t");
/*      */                                       }
/* 5347 */                                       out.write("\n\n\n\n        ");
/* 5348 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 5349 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5353 */                                   if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 5354 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                                   }
/*      */                                   
/* 5357 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 5358 */                                   out.write("\n\n\n\n");
/* 5359 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 5360 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 5364 */                               if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 5365 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                               }
/*      */                               
/* 5368 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 5369 */                               out.write("\n\n\n\n  <td>&nbsp;</td>\n\n\n\n  </table>\n\n\n\n");
/*      */                             }
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5379 */                             out.write("\n\n\n\n\n\n\n\n<table width=\"64%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\n\n\n  <tr>\n\n\n\n  ");
/*      */                             
/* 5381 */                             ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5382 */                             _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 5383 */                             _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f6);
/* 5384 */                             int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 5385 */                             if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                               for (;;) {
/* 5387 */                                 out.write("\n\n\n\n      ");
/* 5388 */                                 if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/*      */                                   return;
/* 5390 */                                 out.write("\n\n\n\n        ");
/*      */                                 
/* 5392 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5393 */                                 _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 5394 */                                 _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 5395 */                                 int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 5396 */                                 if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                   for (;;) {
/* 5398 */                                     out.write("\n\n\n\n\t\t");
/* 5399 */                                     if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 5400 */                                       out.write("\n\n\n\n        <td align=\"left\" valign=\"top\"  bgcolor=\"#FFFFFF\" > &nbsp;</td>\n\n\n\n\t\t");
/*      */                                     } else {
/* 5402 */                                       out.write("\n\n\n\n\t\t<td>&nbsp;</td>\n\n\n\n\t\t");
/*      */                                     }
/* 5404 */                                     out.write("\n\n\n\n        ");
/* 5405 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 5406 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5410 */                                 if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 5411 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                                 }
/*      */                                 
/* 5414 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 5415 */                                 out.write("\n\n\n\n\t\t\n\n\n\n");
/* 5416 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 5417 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 5421 */                             if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 5422 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                             }
/*      */                             
/* 5425 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 5426 */                             out.write("\n\n\n\n \n\n\n\n  <td>\n\n\n\n\n\n\n\n<span class=\"bodytext\"><div id=\"actionstatus\" >&nbsp;</div></span>\n\n\n\n\n\n\n\n</td>\n\n\n\n\n\n\n\n\n\n\n\n</table>\n\n\n\n\n\n\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\n\n\n<tr>\n\n\n\n");
/* 5427 */                             if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 5428 */                               out.write("\n\n\n\n<td width=\"16%\"></td>\n\n\n\n");
/*      */                             }
/* 5430 */                             out.write("\n\n\n\n<td>\n\n\n\n<td width=\"100%\">\n\n\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\n\n\n<tr>\n\n\n\n<td  width=\"98%\">\n\n\n\n<div id='graphdetails' style=\"width:98%;\">\n\n\n\n</div>\n\n\n\n</td>\n\n\n\n</tr>\n\n\n\n</table>\n\n\n\n</td>\n\n\n\n</td>\n\n\n\n<td  width=\"16%\"></td>\n\n</tr>\n\n\n\n</table>\n\n\n\n\n\n\n\n\t     \n\n\n\n<script>\n\n\n\nvar http = getHTTPObject(); // We create the HTTP Object\n\n\n\nfunction getHTTPObject() {\n\n\n\n  var xmlhttp;\n\n\n\n  if (window.ActiveXObject){\n\n\n\n    try {\n\n\n\n      xmlhttp = new ActiveXObject(\"Msxml2.XMLHTTP\");\n\n\n\n    } catch (e) {\n\n\n\n      try {\n\n\n\n        xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n\n\n\n      } catch (E) {\n\n\n\n        xmlhttp = false;\n\n\n\n      }\n\n\n\n    }\n\n\n\n}\n\n\n\n  else if (typeof XMLHttpRequest != 'undefined') {\n\n\n\n    try {\n\n\n\n      xmlhttp = new XMLHttpRequest();\n\n\n\n    } catch (e) {\n\n\n\n      xmlhttp = false;\n\n\n\n    }\n\n\n\n  }\n\n\n\n  return xmlhttp;\n\n\n\n}\n\n\n\nfunction getGraphs()\n\n\n\n{\n\n\n\n");
/* 5431 */                             if ((SingletonClasses != null) && (SingletonClasses.size() > 0) && (InstancedClasses != null) && (InstancedClasses.size() > 0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5440 */                               out.write("\n\n\n\ndocument.getElementById('actionstatus').innerHTML ='<img src=\"../images/icon_cogwheel.gif\" >'+' ");
/* 5441 */                               out.print(FormatUtil.getString("am.webclient.wmi.loadinggraphs.text"));
/* 5442 */                               out.write("...';\n\n\n\n");
/*      */                             }
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5452 */                             out.write("\t     \n\n\n\nURL=\"/WMIPerfCounters.do?method=showGraph\"; //NO I18N\n\n\n\nURL=URL+'&resourceid=");
/* 5453 */                             out.print(resourceid);
/* 5454 */                             out.write("'; //NO I18N\n\n\n\nhttp = getHTTPObject();\n\n\n\nhttp.onreadystatechange = changeServiceStatus;\n\n\n\nhttp.open(\"GET\", URL, true);\n\n\n\nhttp.send(null);\n\n\n\n}\n\n\n\nfunction changeServiceStatus()\n\n\n\n{\n\n\n\n//alert(\"in to first the response loop\");\n\n\n\n//alert(http.responseText);\n\n\n\n\n\n\n\nif(http.readyState == 4)\n\n\n\n\t{\n\n\n\n\t//alert(\"in to first the response loop\");\n\n\n\n\t\tif (http.status == 200)\n\n\n\n\t\t{\n\n\n\n\t\t  //alert(\"in to the response loop\");\n\n\n\n\t\tdocument.getElementById('actionstatus').innerHTML ='&nbsp;';\n\n\n\n\t\tdocument.getElementById('graphdetails').innerHTML = http.responseText;\n\n\n\n\t\t}\n\n\n\n\t\t\n\n\n\n\t}\n\n\n\n\n\n\n\n}\n\n\n\n</script>");
/* 5455 */                             out.write(10);
/* 5456 */                             out.write(32);
/* 5457 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f6.doAfterBody();
/* 5458 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 5461 */                           if (_jspx_eval_tiles_005fput_005f6 != 1) {
/* 5462 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 5465 */                         if (_jspx_th_tiles_005fput_005f6.doEndTag() == 5) {
/* 5466 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f6); return;
/*      */                         }
/*      */                         
/* 5469 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f6);
/* 5470 */                         out.write(10);
/* 5471 */                         out.write(32);
/* 5472 */                         if (_jspx_meth_tiles_005fput_005f7(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 5474 */                         out.write(10);
/* 5475 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5476 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 5480 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5481 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 5484 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5485 */                       out.write("\n \n \n\t\n\t\n\t\n\t \n\t\n\t");
/*      */                     }
/* 5487 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5488 */         out = _jspx_out;
/* 5489 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5490 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5491 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5494 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5500 */     PageContext pageContext = _jspx_page_context;
/* 5501 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5503 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5504 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 5505 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5507 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 5508 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 5509 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 5511 */         out.write(10);
/* 5512 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 5513 */           return true;
/* 5514 */         out.write(10);
/* 5515 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 5516 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5520 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 5521 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5522 */       return true;
/*      */     }
/* 5524 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5525 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5530 */     PageContext pageContext = _jspx_page_context;
/* 5531 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5533 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5534 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 5535 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 5537 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 5539 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 5540 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 5541 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 5542 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5543 */       return true;
/*      */     }
/* 5545 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5546 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5551 */     PageContext pageContext = _jspx_page_context;
/* 5552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5554 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5555 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 5556 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5558 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 5559 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 5560 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 5562 */         out.write(10);
/* 5563 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 5564 */           return true;
/* 5565 */         out.write(10);
/* 5566 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 5567 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5571 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 5572 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5573 */       return true;
/*      */     }
/* 5575 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5576 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5581 */     PageContext pageContext = _jspx_page_context;
/* 5582 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5584 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5585 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 5586 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 5588 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 5590 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 5591 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 5592 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 5593 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 5594 */       return true;
/*      */     }
/* 5596 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 5597 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5602 */     PageContext pageContext = _jspx_page_context;
/* 5603 */     JspWriter out = _jspx_page_context.getOut();
/* 5604 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 5605 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 5607 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5608 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 5609 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5611 */     _jspx_th_c_005fif_005f2.setTest("${empty appservers}");
/* 5612 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 5613 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 5615 */         out.write(10);
/* 5616 */         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/showresource.do?method=showResourceTypes&includeleftmenu=true", out, false);
/* 5617 */         out.write(10);
/* 5618 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5619 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5623 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5624 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5625 */       return true;
/*      */     }
/* 5627 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5628 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5633 */     PageContext pageContext = _jspx_page_context;
/* 5634 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5636 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5637 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 5638 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5640 */     _jspx_th_c_005fif_005f3.setTest("${!empty param.haid}");
/* 5641 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 5642 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 5644 */         out.write(10);
/* 5645 */         if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5646 */           return true;
/* 5647 */         out.write("\n      ");
/* 5648 */         if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 5649 */           return true;
/* 5650 */         out.write(10);
/* 5651 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 5652 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5656 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 5657 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5658 */       return true;
/*      */     }
/* 5660 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5661 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5666 */     PageContext pageContext = _jspx_page_context;
/* 5667 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5669 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 5670 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 5671 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 5673 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 5674 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 5676 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 5677 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 5679 */           out.write("\n      ");
/* 5680 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 5681 */             return true;
/* 5682 */           out.write("\n      ");
/* 5683 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 5684 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5688 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 5689 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5692 */         int tmp185_184 = 0; int[] tmp185_182 = _jspx_push_body_count_c_005fcatch_005f0; int tmp187_186 = tmp185_182[tmp185_184];tmp185_182[tmp185_184] = (tmp187_186 - 1); if (tmp187_186 <= 0) break;
/* 5693 */         out = _jspx_page_context.popBody(); }
/* 5694 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 5696 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 5697 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 5699 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 5704 */     PageContext pageContext = _jspx_page_context;
/* 5705 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5707 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 5708 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 5709 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 5711 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 5713 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 5714 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 5715 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 5716 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 5717 */       return true;
/*      */     }
/* 5719 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 5720 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5725 */     PageContext pageContext = _jspx_page_context;
/* 5726 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5728 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5729 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 5730 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 5732 */     _jspx_th_c_005fif_005f4.setTest("${(empty invalidhaid)}");
/* 5733 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 5734 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 5736 */         out.write(10);
/* 5737 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 5738 */           return true;
/* 5739 */         out.write(10);
/* 5740 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 5741 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5745 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 5746 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5747 */       return true;
/*      */     }
/* 5749 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5750 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5755 */     PageContext pageContext = _jspx_page_context;
/* 5756 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5758 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5759 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 5760 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 5762 */     _jspx_th_c_005fset_005f0.setVar("haid");
/* 5763 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 5764 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 5765 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5766 */         out = _jspx_page_context.pushBody();
/* 5767 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 5768 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5771 */         out.write(10);
/* 5772 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 5773 */           return true;
/* 5774 */         out.write(10);
/* 5775 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 5776 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5779 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5780 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5783 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5784 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 5785 */       return true;
/*      */     }
/* 5787 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 5788 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5793 */     PageContext pageContext = _jspx_page_context;
/* 5794 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5796 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5797 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5798 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5800 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 5801 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5802 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5803 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5804 */       return true;
/*      */     }
/* 5806 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5812 */     PageContext pageContext = _jspx_page_context;
/* 5813 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5815 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5816 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5817 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5819 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 5820 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5821 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5822 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5823 */       return true;
/*      */     }
/* 5825 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5826 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5831 */     PageContext pageContext = _jspx_page_context;
/* 5832 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5834 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5835 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5836 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5838 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 5839 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5840 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5841 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5842 */       return true;
/*      */     }
/* 5844 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5845 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5850 */     PageContext pageContext = _jspx_page_context;
/* 5851 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5853 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5854 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 5855 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5857 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 5858 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 5859 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 5861 */         out.write("\n\t\t\t alertUser();\n\t\t \treturn;\n\t\t");
/* 5862 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 5863 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5867 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 5868 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 5869 */       return true;
/*      */     }
/* 5871 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 5872 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5877 */     PageContext pageContext = _jspx_page_context;
/* 5878 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5880 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5881 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5882 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5884 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 5885 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5886 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5887 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5888 */       return true;
/*      */     }
/* 5890 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5891 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5896 */     PageContext pageContext = _jspx_page_context;
/* 5897 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5899 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5900 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5901 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5903 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 5904 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5905 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5906 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5907 */       return true;
/*      */     }
/* 5909 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5910 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5915 */     PageContext pageContext = _jspx_page_context;
/* 5916 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5918 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5919 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5920 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 5922 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 5923 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5924 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5925 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5926 */       return true;
/*      */     }
/* 5928 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5929 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5934 */     PageContext pageContext = _jspx_page_context;
/* 5935 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5937 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5938 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5939 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5941 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 5942 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5943 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5944 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5945 */       return true;
/*      */     }
/* 5947 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5948 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5953 */     PageContext pageContext = _jspx_page_context;
/* 5954 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5956 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5957 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5958 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5960 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 5961 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5962 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5963 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5964 */       return true;
/*      */     }
/* 5966 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5972 */     PageContext pageContext = _jspx_page_context;
/* 5973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5975 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5976 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5977 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5979 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 5980 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5981 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5982 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5983 */       return true;
/*      */     }
/* 5985 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5986 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5991 */     PageContext pageContext = _jspx_page_context;
/* 5992 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5994 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5995 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5996 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5998 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 5999 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 6000 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 6001 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6002 */       return true;
/*      */     }
/* 6004 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6005 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6010 */     PageContext pageContext = _jspx_page_context;
/* 6011 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6013 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6014 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 6015 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6017 */     _jspx_th_c_005fout_005f10.setValue("${ha.key}");
/* 6018 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 6019 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 6020 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6021 */       return true;
/*      */     }
/* 6023 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6024 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6029 */     PageContext pageContext = _jspx_page_context;
/* 6030 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6032 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6033 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 6034 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6036 */     _jspx_th_c_005fout_005f11.setValue("${ha.value}");
/* 6037 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 6038 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 6039 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6040 */       return true;
/*      */     }
/* 6042 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6043 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6048 */     PageContext pageContext = _jspx_page_context;
/* 6049 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6051 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6052 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 6053 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6055 */     _jspx_th_c_005fset_005f1.setVar("monitorName");
/* 6056 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 6057 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 6058 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 6059 */         out = _jspx_page_context.pushBody();
/* 6060 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 6061 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 6062 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6065 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6066 */           return true;
/* 6067 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 6068 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6071 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 6072 */         out = _jspx_page_context.popBody();
/* 6073 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 6076 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 6077 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 6078 */       return true;
/*      */     }
/* 6080 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 6081 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6086 */     PageContext pageContext = _jspx_page_context;
/* 6087 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6089 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6090 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 6091 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 6093 */     _jspx_th_c_005fout_005f12.setValue("${ha.value}");
/* 6094 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 6095 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 6096 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6097 */       return true;
/*      */     }
/* 6099 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6100 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6105 */     PageContext pageContext = _jspx_page_context;
/* 6106 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6108 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6109 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 6110 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 6112 */     _jspx_th_c_005fout_005f13.setValue("${ha.key}");
/* 6113 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 6114 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 6115 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6116 */       return true;
/*      */     }
/* 6118 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6119 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f20, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6124 */     PageContext pageContext = _jspx_page_context;
/* 6125 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6127 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6128 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 6129 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f20);
/*      */     
/* 6131 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 6132 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 6133 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 6134 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6135 */       return true;
/*      */     }
/* 6137 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6138 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6143 */     PageContext pageContext = _jspx_page_context;
/* 6144 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6146 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6147 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 6148 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6150 */     _jspx_th_c_005fout_005f14.setValue("${ha.key}");
/* 6151 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 6152 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 6153 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6154 */       return true;
/*      */     }
/* 6156 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6157 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6162 */     PageContext pageContext = _jspx_page_context;
/* 6163 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6165 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6166 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 6167 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6169 */     _jspx_th_c_005fout_005f15.setValue("${ha.value}");
/* 6170 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 6171 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 6172 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6173 */       return true;
/*      */     }
/* 6175 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6176 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6181 */     PageContext pageContext = _jspx_page_context;
/* 6182 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6184 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6185 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 6186 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6188 */     _jspx_th_c_005fset_005f2.setVar("monitorName");
/* 6189 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 6190 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 6191 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 6192 */         out = _jspx_page_context.pushBody();
/* 6193 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 6194 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 6195 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6198 */         if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fset_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6199 */           return true;
/* 6200 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 6201 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6204 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 6205 */         out = _jspx_page_context.popBody();
/* 6206 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 6209 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 6210 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 6211 */       return true;
/*      */     }
/* 6213 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 6214 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6219 */     PageContext pageContext = _jspx_page_context;
/* 6220 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6222 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6223 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 6224 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 6226 */     _jspx_th_c_005fout_005f16.setValue("${ha.value}");
/* 6227 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 6228 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 6229 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6230 */       return true;
/*      */     }
/* 6232 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6233 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6238 */     PageContext pageContext = _jspx_page_context;
/* 6239 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6241 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6242 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 6243 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 6245 */     _jspx_th_c_005fout_005f17.setValue("${ha.key}");
/* 6246 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 6247 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 6248 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6249 */       return true;
/*      */     }
/* 6251 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6252 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6257 */     PageContext pageContext = _jspx_page_context;
/* 6258 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6260 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6261 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 6262 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 6264 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 6265 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 6266 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 6267 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6268 */       return true;
/*      */     }
/* 6270 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6271 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f21(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6276 */     PageContext pageContext = _jspx_page_context;
/* 6277 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6279 */     IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6280 */     _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 6281 */     _jspx_th_c_005fif_005f21.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6283 */     _jspx_th_c_005fif_005f21.setTest("${empty associatedmgs}");
/* 6284 */     int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 6285 */     if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */       for (;;) {
/* 6287 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 6288 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f21, _jspx_page_context))
/* 6289 */           return true;
/* 6290 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 6291 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f21, _jspx_page_context))
/* 6292 */           return true;
/* 6293 */         out.write("</td>\n\t ");
/* 6294 */         int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 6295 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6299 */     if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 6300 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 6301 */       return true;
/*      */     }
/* 6303 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 6304 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6309 */     PageContext pageContext = _jspx_page_context;
/* 6310 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6312 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6313 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 6314 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 6316 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 6317 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 6318 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 6319 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 6320 */       return true;
/*      */     }
/* 6322 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 6323 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6328 */     PageContext pageContext = _jspx_page_context;
/* 6329 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6331 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6332 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 6333 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 6335 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.none.text");
/* 6336 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 6337 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 6338 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6339 */       return true;
/*      */     }
/* 6341 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6342 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6347 */     PageContext pageContext = _jspx_page_context;
/* 6348 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6350 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6351 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 6352 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6354 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 6355 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 6356 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 6357 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6358 */       return true;
/*      */     }
/* 6360 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6361 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f6(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6366 */     PageContext pageContext = _jspx_page_context;
/* 6367 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6369 */     PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6370 */     _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 6371 */     _jspx_th_logic_005fpresent_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6373 */     _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 6374 */     int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 6375 */     if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */       for (;;) {
/* 6377 */         out.write("\nalertUser();\n");
/* 6378 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 6379 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6383 */     if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 6384 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 6385 */       return true;
/*      */     }
/* 6387 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 6388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6393 */     PageContext pageContext = _jspx_page_context;
/* 6394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6396 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 6397 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 6398 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6400 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 6402 */     _jspx_th_html_005ftext_005f0.setSize("40");
/*      */     
/* 6404 */     _jspx_th_html_005ftext_005f0.setMaxlength("255");
/*      */     
/* 6406 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/* 6407 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 6408 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 6409 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 6410 */       return true;
/*      */     }
/* 6412 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 6413 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6418 */     PageContext pageContext = _jspx_page_context;
/* 6419 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6421 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 6422 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 6423 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6425 */     _jspx_th_html_005ftextarea_005f0.setProperty("description");
/*      */     
/* 6427 */     _jspx_th_html_005ftextarea_005f0.setCols("38");
/*      */     
/* 6429 */     _jspx_th_html_005ftextarea_005f0.setRows("3");
/*      */     
/* 6431 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea");
/* 6432 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 6433 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 6434 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 6435 */       return true;
/*      */     }
/* 6437 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 6438 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6443 */     PageContext pageContext = _jspx_page_context;
/* 6444 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6446 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6447 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 6448 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6450 */     _jspx_th_html_005ftext_005f1.setProperty("pollInterval");
/*      */     
/* 6452 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/* 6454 */     _jspx_th_html_005ftext_005f1.setSize("15");
/* 6455 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 6456 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 6457 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 6458 */       return true;
/*      */     }
/* 6460 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 6461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6466 */     PageContext pageContext = _jspx_page_context;
/* 6467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6469 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6470 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 6471 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6473 */     _jspx_th_html_005ftext_005f2.setProperty("host");
/*      */     
/* 6475 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 6477 */     _jspx_th_html_005ftext_005f2.setSize("15");
/* 6478 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 6479 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 6480 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 6481 */       return true;
/*      */     }
/* 6483 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 6484 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6489 */     PageContext pageContext = _jspx_page_context;
/* 6490 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6492 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6493 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 6494 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6496 */     _jspx_th_html_005ftext_005f3.setProperty("username");
/*      */     
/* 6498 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/* 6500 */     _jspx_th_html_005ftext_005f3.setSize("15");
/* 6501 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 6502 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 6503 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 6504 */       return true;
/*      */     }
/* 6506 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 6507 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6512 */     PageContext pageContext = _jspx_page_context;
/* 6513 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6515 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 6516 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 6517 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6519 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 6521 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/*      */     
/* 6523 */     _jspx_th_html_005fpassword_005f0.setSize("15");
/* 6524 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 6525 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 6526 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 6527 */       return true;
/*      */     }
/* 6529 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 6530 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6535 */     PageContext pageContext = _jspx_page_context;
/* 6536 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6538 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6539 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 6540 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6542 */     _jspx_th_c_005fout_005f18.setValue("${param.resourceid}");
/* 6543 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 6544 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 6545 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6546 */       return true;
/*      */     }
/* 6548 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6549 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6554 */     PageContext pageContext = _jspx_page_context;
/* 6555 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6557 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6558 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 6559 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6561 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 6562 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 6563 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 6564 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6565 */       return true;
/*      */     }
/* 6567 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6568 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6573 */     PageContext pageContext = _jspx_page_context;
/* 6574 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6576 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6577 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 6578 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6580 */     _jspx_th_c_005fout_005f20.setValue("${param.resourceid}");
/* 6581 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 6582 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 6583 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6584 */       return true;
/*      */     }
/* 6586 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6587 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fpiechart_005f0(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6592 */     PageContext pageContext = _jspx_page_context;
/* 6593 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6595 */     AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 6596 */     _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 6597 */     _jspx_th_awolf_005fpiechart_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 6599 */     _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */     
/* 6601 */     _jspx_th_awolf_005fpiechart_005f0.setWidth("240");
/*      */     
/* 6603 */     _jspx_th_awolf_005fpiechart_005f0.setHeight("180");
/*      */     
/* 6605 */     _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */     
/* 6607 */     _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */     
/* 6609 */     _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */     
/* 6611 */     _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 6612 */     int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 6613 */     if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 6614 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 6615 */         out = _jspx_page_context.pushBody();
/* 6616 */         _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 6617 */         _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6620 */         out.write(" \n            ");
/* 6621 */         if (_jspx_meth_awolf_005fmap_005f0(_jspx_th_awolf_005fpiechart_005f0, _jspx_page_context))
/* 6622 */           return true;
/* 6623 */         out.write(32);
/* 6624 */         int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 6625 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6628 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 6629 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6632 */     if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 6633 */       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 6634 */       return true;
/*      */     }
/* 6636 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 6637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f0(JspTag _jspx_th_awolf_005fpiechart_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6642 */     PageContext pageContext = _jspx_page_context;
/* 6643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6645 */     Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 6646 */     _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 6647 */     _jspx_th_awolf_005fmap_005f0.setParent((Tag)_jspx_th_awolf_005fpiechart_005f0);
/*      */     
/* 6649 */     _jspx_th_awolf_005fmap_005f0.setId("color");
/* 6650 */     int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 6651 */     if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 6652 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 6653 */         out = _jspx_page_context.pushBody();
/* 6654 */         _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 6655 */         _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6658 */         out.write(32);
/* 6659 */         if (_jspx_meth_awolf_005fparam_005f0(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 6660 */           return true;
/* 6661 */         out.write(32);
/* 6662 */         if (_jspx_meth_awolf_005fparam_005f1(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 6663 */           return true;
/* 6664 */         out.write(" \n            ");
/* 6665 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 6666 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6669 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 6670 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6673 */     if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 6674 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 6675 */       return true;
/*      */     }
/* 6677 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 6678 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f0(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6683 */     PageContext pageContext = _jspx_page_context;
/* 6684 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6686 */     AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 6687 */     _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 6688 */     _jspx_th_awolf_005fparam_005f0.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 6690 */     _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */     
/* 6692 */     _jspx_th_awolf_005fparam_005f0.setValue("#29FF29");
/* 6693 */     int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 6694 */     if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 6695 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 6696 */       return true;
/*      */     }
/* 6698 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 6699 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f1(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6704 */     PageContext pageContext = _jspx_page_context;
/* 6705 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6707 */     AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 6708 */     _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 6709 */     _jspx_th_awolf_005fparam_005f1.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 6711 */     _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */     
/* 6713 */     _jspx_th_awolf_005fparam_005f1.setValue("#FF0000");
/* 6714 */     int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 6715 */     if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 6716 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 6717 */       return true;
/*      */     }
/* 6719 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 6720 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6725 */     PageContext pageContext = _jspx_page_context;
/* 6726 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6728 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6729 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 6730 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 6732 */     _jspx_th_c_005fwhen_005f0.setTest("${selectedscheme == 'slim'}");
/* 6733 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 6734 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 6736 */         out.write("\n\n\n\n      ");
/* 6737 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 6738 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6742 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 6743 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 6744 */       return true;
/*      */     }
/* 6746 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 6747 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6752 */     PageContext pageContext = _jspx_page_context;
/* 6753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6755 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6756 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 6757 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 6759 */     _jspx_th_c_005fwhen_005f1.setTest("${selectedscheme == 'slim'}");
/* 6760 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 6761 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 6763 */         out.write("\n\n\n\n       ");
/* 6764 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 6765 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6769 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 6770 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 6771 */       return true;
/*      */     }
/* 6773 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 6774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6779 */     PageContext pageContext = _jspx_page_context;
/* 6780 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6782 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6783 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 6784 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 6786 */     _jspx_th_c_005fwhen_005f2.setTest("${selectedscheme == 'slim'}");
/* 6787 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 6788 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 6790 */         out.write("\n\n\n\n      ");
/* 6791 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 6792 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6796 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 6797 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 6798 */       return true;
/*      */     }
/* 6800 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 6801 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6806 */     PageContext pageContext = _jspx_page_context;
/* 6807 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6809 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6810 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 6811 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 6813 */     _jspx_th_c_005fwhen_005f3.setTest("${selectedscheme == 'slim'}");
/* 6814 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 6815 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 6817 */         out.write("\n\n\n\n        ");
/* 6818 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 6819 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6823 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 6824 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 6825 */       return true;
/*      */     }
/* 6827 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 6828 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6833 */     PageContext pageContext = _jspx_page_context;
/* 6834 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6836 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6837 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 6838 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 6840 */     _jspx_th_c_005fwhen_005f4.setTest("${selectedscheme == 'slim'}");
/* 6841 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 6842 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 6844 */         out.write("\n\n\n\n    \n\n\n\n    ");
/* 6845 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 6846 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6850 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 6851 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 6852 */       return true;
/*      */     }
/* 6854 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 6855 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f7(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6860 */     PageContext pageContext = _jspx_page_context;
/* 6861 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6863 */     PutTag _jspx_th_tiles_005fput_005f7 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6864 */     _jspx_th_tiles_005fput_005f7.setPageContext(_jspx_page_context);
/* 6865 */     _jspx_th_tiles_005fput_005f7.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6867 */     _jspx_th_tiles_005fput_005f7.setName("footer");
/*      */     
/* 6869 */     _jspx_th_tiles_005fput_005f7.setValue("/jsp/footer.jsp");
/* 6870 */     int _jspx_eval_tiles_005fput_005f7 = _jspx_th_tiles_005fput_005f7.doStartTag();
/* 6871 */     if (_jspx_th_tiles_005fput_005f7.doEndTag() == 5) {
/* 6872 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f7);
/* 6873 */       return true;
/*      */     }
/* 6875 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f7);
/* 6876 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\WMIPerfDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */