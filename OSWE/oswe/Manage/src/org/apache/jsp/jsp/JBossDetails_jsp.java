/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.Truncate;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.data.support.DialChartSupport;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.DialChart;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.TimeChart;
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
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
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
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatNumberTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class JBossDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*   83 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
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
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
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
/* 1488 */       message = EnterpriseUtil.decodeString(message);
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
/* 1999 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
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
/*      */ 
/*      */   public String addBreakAt(String str, int len)
/*      */   {
/* 2195 */     if (len == 0) return str;
/* 2196 */     String temp = str;
/* 2197 */     StringBuffer ret = new StringBuffer("");
/* 2198 */     while (temp.length() > len)
/*      */     {
/* 2200 */       ret.append(temp.substring(0, len));
/* 2201 */       ret.append("<br>");
/* 2202 */       temp = temp.substring(len);
/*      */     }
/* 2204 */     ret.append(temp);
/* 2205 */     return ret.toString();
/*      */   }
/*      */   
/* 2208 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2214 */   private static Map<String, Long> _jspx_dependants = new HashMap(12);
/* 2215 */   static { _jspx_dependants.put("/jsp/MyField_div.jsp", Long.valueOf(1473429417000L));
/* 2216 */     _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2217 */     _jspx_dependants.put("/jsp/includes/JBLeftArea.jspf", Long.valueOf(1473429417000L));
/* 2218 */     _jspx_dependants.put("/jsp/includes/mop_actions.jspf", Long.valueOf(1473429417000L));
/* 2219 */     _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2220 */     _jspx_dependants.put("/jsp/includes/cam_screen.jspf", Long.valueOf(1473429417000L));
/* 2221 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2222 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2223 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2224 */     _jspx_dependants.put("/jsp/MyField_trstrip.jsp", Long.valueOf(1473429417000L));
/* 2225 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2226 */     _jspx_dependants.put("/jsp/includes/HostPerformance.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2268 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2272 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2273 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2274 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2275 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2276 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2277 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2278 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2279 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2280 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2281 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2282 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2283 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2284 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2285 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2286 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2287 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2288 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2289 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2290 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2291 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2292 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2293 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2294 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2295 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2296 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2297 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2298 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2299 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2300 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2301 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2302 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2303 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2304 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2305 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2306 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2307 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2311 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2312 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2313 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2314 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2315 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2316 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2317 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2318 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2319 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2320 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2321 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2322 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.release();
/* 2323 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2324 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2325 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2326 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2327 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2328 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2329 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2330 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2331 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.release();
/* 2332 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2333 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.release();
/* 2334 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.release();
/* 2335 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.release();
/* 2336 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2337 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2338 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.release();
/* 2339 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2340 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2341 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.release();
/* 2342 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2343 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2344 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*      */   }
/*      */   
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws java.io.IOException, javax.servlet.ServletException {
/*      */     ;
/*      */     ;
/*      */     ;
/* 2351 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2354 */     JspWriter out = null;
/* 2355 */     Object page = this;
/* 2356 */     JspWriter _jspx_out = null;
/* 2357 */     PageContext _jspx_page_context = null;
/*      */     
/* 2359 */     Object _jspx_acolumn_1 = null;
/* 2360 */     Integer _jspx_i_1 = null;
/*      */     try
/*      */     {
/* 2363 */       response.setContentType("text/html;charset=UTF-8");
/* 2364 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2366 */       _jspx_page_context = pageContext;
/* 2367 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2368 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2369 */       session = pageContext.getSession();
/* 2370 */       out = pageContext.getOut();
/* 2371 */       _jspx_out = out;
/*      */       
/* 2373 */       out.write("<!--$Id$-->\n\n\n");
/*      */       
/* 2375 */       request.setAttribute("HelpKey", "Monitors JBoss Details");
/*      */       
/* 2377 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/*      */ 
/* 2380 */       Properties props = (Properties)request.getAttribute("JBossProps");
/* 2381 */       String version = props.getProperty("Version");
/* 2382 */       String version321 = "false";
/* 2383 */       if ((version != null) && (version.startsWith("3.2.1")))
/*      */       {
/* 2385 */         version321 = "true";
/*      */       }
/*      */       
/* 2388 */       out.write("\n<script>\nfunction myOnJbossLoad()\n{\n\t");
/* 2389 */       if ((request.getAttribute("showdata") != null) && (!request.getAttribute("showdata").equals("1")))
/*      */       {
/*      */ 
/* 2392 */         out.write("\n\tejbDetails();\n\tjdbcDetails();\n\twebappDetails();\n\t");
/*      */       }
/* 2394 */       out.write("\n}\nvar http4,http2,http3;\nfunction ejbDetails()\n{\n\tURL='/jsp/JBossEJBDetailsUserArea.jsp?resourceid=");
/* 2395 */       out.print(request.getParameter("resourceid"));
/* 2396 */       out.write("&collectiontime=");
/* 2397 */       out.print(request.getAttribute("collectiontime"));
/* 2398 */       out.write("';\n\thttp4=getHTTPObject();\n\thttp4.onreadystatechange = new Function('if(http4.readyState == 4 && http4.status == 200){document.getElementById(\"actionstatus1\").innerHTML =\"&nbsp;\",document.getElementById(\"ejbdetails\").innerHTML = http4.responseText;}');\n\thttp4.open(\"GET\", URL, true);\n\thttp4.send(null);\n}\nfunction jdbcDetails()\n{\n\tURL='/jsp/JBossJDBCDetailsUserArea.jsp?resourceid=");
/* 2399 */       out.print(request.getParameter("resourceid"));
/* 2400 */       out.write("&collectiontime=");
/* 2401 */       out.print(request.getAttribute("collectiontime"));
/* 2402 */       out.write("';//No I18N\n\thttp2=getHTTPObject();\n\thttp2.onreadystatechange = new Function('if(http2.readyState == 4 && http2.status == 200){document.getElementById(\"actionstatus2\").innerHTML =\"&nbsp;\",document.getElementById(\"jdbcdetails\").innerHTML = http2.responseText;}');\n\thttp2.open(\"GET\", URL, true);\n\thttp2.send(null);\n}\nfunction webappDetails()\n{\n\tURL='/jsp/JBossWebAppDetailsUserArea.jsp?resourceid=");
/* 2403 */       out.print(request.getParameter("resourceid"));
/* 2404 */       out.write("&version321=");
/* 2405 */       out.print(version321);
/* 2406 */       out.write("';\n\thttp3=getHTTPObject();\n\thttp3.onreadystatechange = new Function('if(http3.readyState == 4 && http3.status == 200){document.getElementById(\"actionstatus3\").innerHTML =\"&nbsp;\",document.getElementById(\"webappdetails\").innerHTML = http3.responseText;}');\n\thttp3.open(\"GET\", URL, true);\n\thttp3.send(null);\n}\n</script>\n");
/* 2407 */       if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */         return;
/* 2409 */       out.write(10);
/*      */       
/* 2411 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2412 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2413 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2415 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2416 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2417 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2419 */           out.write(10);
/* 2420 */           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2422 */           out.write(10);
/* 2423 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2425 */           out.write(10);
/* 2426 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2428 */           out.write(10);
/* 2429 */           out.write(10);
/*      */           
/* 2431 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2432 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2433 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2435 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */           
/* 2437 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2438 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2439 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2440 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2441 */               out = _jspx_page_context.pushBody();
/* 2442 */               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2443 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2446 */               out.write("\n <a name=\"jbtop\"> </a>\n");
/* 2447 */               GetDataFromDB dataHandler = null;
/* 2448 */               dataHandler = (GetDataFromDB)_jspx_page_context.getAttribute("dataHandler", 1);
/* 2449 */               if (dataHandler == null) {
/* 2450 */                 dataHandler = new GetDataFromDB();
/* 2451 */                 _jspx_page_context.setAttribute("dataHandler", dataHandler, 1);
/*      */               }
/* 2453 */               out.write("\n\n\n\n");
/* 2454 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 2456 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2457 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2458 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2460 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 2462 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 2464 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 2466 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2467 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2468 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2469 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 2472 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2473 */               String available = null;
/* 2474 */               available = (String)_jspx_page_context.findAttribute("available");
/* 2475 */               out.write(10);
/*      */               
/* 2477 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2478 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2479 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2481 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 2483 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 2485 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 2487 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2488 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2489 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2490 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 2493 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2494 */               String unavailable = null;
/* 2495 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2496 */               out.write(10);
/*      */               
/* 2498 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2499 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2500 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2502 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 2504 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 2506 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 2508 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2509 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2510 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2511 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 2514 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2515 */               String unmanaged = null;
/* 2516 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2517 */               out.write(10);
/*      */               
/* 2519 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2520 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2521 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2523 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 2525 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 2527 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 2529 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2530 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2531 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2532 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 2535 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2536 */               String scheduled = null;
/* 2537 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2538 */               out.write(10);
/*      */               
/* 2540 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2541 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2542 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2544 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2546 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2548 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2550 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2551 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2552 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2553 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 2556 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2557 */               String critical = null;
/* 2558 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 2559 */               out.write(10);
/*      */               
/* 2561 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2562 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2563 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2565 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 2567 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 2569 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 2571 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2572 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2573 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2574 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 2577 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2578 */               String clear = null;
/* 2579 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 2580 */               out.write(10);
/*      */               
/* 2582 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2583 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2584 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2586 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 2588 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 2590 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 2592 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2593 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2594 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2595 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 2598 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2599 */               String warning = null;
/* 2600 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 2601 */               out.write(10);
/* 2602 */               out.write(10);
/*      */               
/* 2604 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2605 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 2607 */               out.write(10);
/* 2608 */               out.write(10);
/* 2609 */               out.write(10);
/* 2610 */               out.write(10);
/* 2611 */               GetWLSGraph wlsGraph = null;
/* 2612 */               wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 2);
/* 2613 */               if (wlsGraph == null) {
/* 2614 */                 wlsGraph = new GetWLSGraph();
/* 2615 */                 _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 2);
/*      */               }
/* 2617 */               out.write(10);
/* 2618 */               out.write(10);
/* 2619 */               out.write(10);
/*      */               
/* 2621 */               String resourceid = request.getParameter("resourceid");
/*      */               
/* 2623 */               wlsGraph.setParam(resourceid, "AVAILABILITY");
/*      */               
/* 2625 */               String encodeurl = URLEncoder.encode("/showresource.do?haid=" + request.getParameter("haid") + "&type=JBOSS-server&method=showdetails&resourceid=" + resourceid);
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2632 */               String resourcename = request.getParameter("name");
/* 2633 */               String appname = request.getParameter("appName");
/* 2634 */               HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/*      */               
/* 2636 */               Properties jettyprops = null;
/* 2637 */               ArrayList attribIDs = new ArrayList();
/* 2638 */               ArrayList resIDs = new ArrayList();
/* 2639 */               resIDs.add(resourceid);
/* 2640 */               attribIDs.add("308");
/* 2641 */               attribIDs.add("309");
/* 2642 */               attribIDs.add("315");
/* 2643 */               attribIDs.add("316");
/* 2644 */               attribIDs.add("321");
/* 2645 */               attribIDs.add("322");
/* 2646 */               Properties alert = getStatus(resIDs, attribIDs);
/*      */               
/* 2648 */               request.setAttribute("isfromresourcepage", "true");
/*      */               
/* 2650 */               String xaxis_time = FormatUtil.getString("am.webclient.common.axisname.time.text");
/*      */               
/* 2652 */               String yaxis_restime = FormatUtil.getString("am.webclient.urlmonitor.responsewithunit.text");
/* 2653 */               String seven_days_text = FormatUtil.getString("am.webclient.common.sevendays.tooltip.text");
/* 2654 */               String thiry_days_text = FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text");
/* 2655 */               String yaxis_heap_usage = FormatUtil.getString("am.webclient.weblogic.heapusagekb.text");
/*      */               
/*      */ 
/* 2658 */               out.write("\n\n\n  <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t");
/*      */               
/* 2660 */               Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/*      */               
/* 2662 */               String haid = request.getParameter("haid");
/* 2663 */               String haName = null;
/* 2664 */               if ((haid != null) && (haid.trim().length() > 0))
/*      */               {
/* 2666 */                 haName = (String)ht.get(haid);
/*      */               }
/*      */               
/* 2669 */               out.write(10);
/* 2670 */               out.write(9);
/*      */               
/* 2672 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2673 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2674 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2676 */               _jspx_th_c_005fif_005f2.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2677 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2678 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/* 2680 */                   out.write("\n      \t\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2681 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 2682 */                   out.write(" &gt; ");
/* 2683 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2684 */                   out.write(" &gt; <span class=\"bcactive\">");
/* 2685 */                   if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                     return;
/* 2687 */                   out.write(" </span></td>\n\t");
/* 2688 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2689 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2693 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2694 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */               }
/*      */               
/* 2697 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2698 */               out.write(10);
/* 2699 */               out.write(9);
/*      */               
/* 2701 */               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2702 */               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2703 */               _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2705 */               _jspx_th_c_005fif_005f3.setTest("${empty param.haid || (!empty invalidhaid)}");
/* 2706 */               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2707 */               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                 for (;;) {
/* 2709 */                   out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2710 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 2711 */                   out.write(" &gt; ");
/* 2712 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes("JBOSS-server"));
/* 2713 */                   out.write(" &gt; <span class=\"bcactive\">");
/* 2714 */                   if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                     return;
/* 2716 */                   out.write(" </span></td>\n\t");
/* 2717 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2718 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2722 */               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2723 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */               }
/*      */               
/* 2726 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2727 */               out.write("\n    </tr>\n</table>\n<div id=\"Reconfigure\"  style=\"DISPLAY: none\">\n");
/* 2728 */               org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/reconfigure.do?method=getWlogicConfiguration&reconfig=true&include=true", out, false);
/* 2729 */               out.write(10);
/* 2730 */               out.write(32);
/* 2731 */               out.write(32);
/* 2732 */               org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/ConfigureWlogic.jsp?reconfig=true", out, false);
/* 2733 */               out.write("\n </div>\n\n\n ");
/* 2734 */               if (_jspx_meth_c_005fset_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2736 */               out.write(10);
/* 2737 */               out.write(32);
/*      */               
/* 2739 */               ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2740 */               _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2741 */               _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/* 2742 */               int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2743 */               if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                 for (;;) {
/* 2745 */                   out.write(10);
/*      */                   
/* 2747 */                   WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2748 */                   _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2749 */                   _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                   
/* 2751 */                   _jspx_th_c_005fwhen_005f0.setTest("${ param.alert!='true' && param.all!='true' }");
/* 2752 */                   int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2753 */                   if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                     for (;;) {
/* 2755 */                       out.write("\n\n <div id=\"snapshot\"  style=\"DISPLAY: block\">\n<form name=\"frm\" action=\"UpdateApplication.jsp\" method=\"post\" style=\"display:inline;\">\n\n\n  <table width=\"99%\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\">\n    <tr>\n      <td width=\"58%\" valign=\"top\">\n        <table width=\"97%\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n          <tr>\n            <td colspan=\"2\" class=\"tableheadingbborder\" >");
/* 2756 */                       out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2757 */                       out.write("</td>\n          </tr>\n          <tr>\n            <td width=\"35%\" class=\"monitorinfoodd\" >");
/* 2758 */                       out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2759 */                       out.write("</td>\n            <td width=\"65%\" class=\"monitorinfoodd\">");
/*      */                       
/* 2761 */                       Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.get(Truncate.class);
/* 2762 */                       _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 2763 */                       _jspx_th_am_005fTruncate_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 2765 */                       _jspx_th_am_005fTruncate_005f0.setTooltip("true");
/*      */                       
/* 2767 */                       _jspx_th_am_005fTruncate_005f0.setLength(35);
/* 2768 */                       int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 2769 */                       if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 2770 */                         if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 2771 */                           out = _jspx_page_context.pushBody();
/* 2772 */                           _jspx_th_am_005fTruncate_005f0.setBodyContent((BodyContent)out);
/* 2773 */                           _jspx_th_am_005fTruncate_005f0.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2776 */                           out.print(request.getAttribute("monitorname"));
/* 2777 */                           int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 2778 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2781 */                         if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 2782 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2785 */                       if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 2786 */                         this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0); return;
/*      */                       }
/*      */                       
/* 2789 */                       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 2790 */                       out.write("</td>\n          </tr>\n\t\t  ");
/* 2791 */                       out.write("<!--$Id$-->\n");
/*      */                       
/* 2793 */                       String hostName = "localhost";
/*      */                       try {
/* 2795 */                         hostName = InetAddress.getLocalHost().getHostName();
/*      */                       } catch (Exception ex) {
/* 2797 */                         ex.printStackTrace();
/*      */                       }
/* 2799 */                       String portNumber = System.getProperty("webserver.port");
/* 2800 */                       String styleClass = "monitorinfoodd";
/* 2801 */                       if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 2802 */                         styleClass = "whitegrayborder-conf-mon";
/*      */                       }
/*      */                       
/* 2805 */                       out.write(10);
/*      */                       
/* 2807 */                       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2808 */                       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2809 */                       _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 2811 */                       _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2812 */                       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2813 */                       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                         for (;;) {
/* 2815 */                           out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 2816 */                           out.print(styleClass);
/* 2817 */                           out.write(34);
/* 2818 */                           out.write(62);
/* 2819 */                           out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 2820 */                           out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 2821 */                           out.print(styleClass);
/* 2822 */                           out.write(34);
/* 2823 */                           out.write(62);
/* 2824 */                           out.print(hostName);
/* 2825 */                           out.write(95);
/* 2826 */                           out.print(portNumber);
/* 2827 */                           out.write("</td>\n</tr>\n");
/* 2828 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2829 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2833 */                       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2834 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                       }
/*      */                       
/* 2837 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2838 */                       out.write(10);
/* 2839 */                       out.write("\n          <tr>\n            <td class=\"monitorinfoeven\" valign=\"top\">");
/* 2840 */                       out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2841 */                       out.write("</td>\n            <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2842 */                       out.print(resourceid);
/* 2843 */                       out.write("&attributeid=309')\">");
/* 2844 */                       out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "309")));
/* 2845 */                       out.write("</a>\n\t\t\t ");
/* 2846 */                       out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + "309" + "#" + "MESSAGE"), "309", alert.getProperty(resourceid + "#" + "309"), resourceid));
/* 2847 */                       out.write("\n\t\t\t ");
/* 2848 */                       if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, "309") != 0) {
/* 2849 */                         out.write("\n\t\t\t <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2850 */                         out.print(resourceid + "_309");
/* 2851 */                         out.write("&monitortype=JBOSS-server')\">");
/* 2852 */                         out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2853 */                         out.write("</a></span>\n                  ");
/*      */                       }
/* 2855 */                       out.write("\n\t\t\t</td>\n          </tr>\n          <tr>\n            <td class=\"monitorinfoodd\">");
/* 2856 */                       out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2857 */                       out.write("</td>\n            <td class=\"monitorinfoodd\">");
/* 2858 */                       out.print(FormatUtil.getString("JBoss Server"));
/* 2859 */                       out.write("</td>\n          </tr>\n\t\t  ");
/*      */                       
/* 2861 */                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2862 */                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2863 */                       _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 2865 */                       _jspx_th_c_005fif_005f4.setTest("${showdata=='2'}");
/* 2866 */                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2867 */                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                         for (;;) {
/* 2869 */                           out.write(10);
/* 2870 */                           out.write(9);
/*      */                           
/* 2872 */                           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2873 */                           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2874 */                           _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f4);
/*      */                           
/* 2876 */                           _jspx_th_c_005fif_005f5.setTest("${!(empty JBossProps.Version)}");
/* 2877 */                           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2878 */                           if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                             for (;;) {
/* 2880 */                               out.write("\n          <tr>\n            <td width=\"35%\" class=\"monitorinfoeven\" >");
/* 2881 */                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.jbossversion"));
/* 2882 */                               out.write("</td>\n              <td width=\"65%\" class=\"monitorinfoeven\">");
/* 2883 */                               out.print(props.getProperty("Version"));
/* 2884 */                               out.write("\n              </td>\n          </tr>\n          ");
/* 2885 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2886 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2890 */                           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2891 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                           }
/*      */                           
/* 2894 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2895 */                           out.write("\n          ");
/*      */                           
/* 2897 */                           IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2898 */                           _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2899 */                           _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f4);
/*      */                           
/* 2901 */                           _jspx_th_c_005fif_005f6.setTest("${!(empty JBossProps.NAMINGSERVICEPORT)}");
/* 2902 */                           int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2903 */                           if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                             for (;;) {
/* 2905 */                               out.write("\n          <tr>\n            <td height=\"23\" class=\"monitorinfoodd\">");
/* 2906 */                               out.print(FormatUtil.getString("am.webclient.common.listenport.text"));
/* 2907 */                               out.write(" </td>\n              <td class=\"monitorinfoodd\">");
/* 2908 */                               out.print(props.getProperty("NAMINGSERVICEPORT"));
/* 2909 */                               out.write("\n              </td>\n          </tr>\n          ");
/* 2910 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2911 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2915 */                           if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2916 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                           }
/*      */                           
/* 2919 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2920 */                           out.write("\n          ");
/*      */                           
/* 2922 */                           IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2923 */                           _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2924 */                           _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f4);
/*      */                           
/* 2926 */                           _jspx_th_c_005fif_005f7.setTest("${!(empty JBossProps.httpport)}");
/* 2927 */                           int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2928 */                           if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                             for (;;) {
/* 2930 */                               out.write("\n          <tr>\n            <td height=\"23\" class=\"monitorinfoeven\">\n            ");
/* 2931 */                               if ((version != null) && (version.startsWith("6"))) {
/* 2932 */                                 out.print(FormatUtil.getString("am.webclient.jboss.rmiport.text"));
/*      */                               } else {
/* 2934 */                                 out.print(FormatUtil.getString("am.webclient.jboss.webserverport.text"));
/*      */                               }
/* 2936 */                               out.write("</td>\n            <td class=\"monitorinfoeven\">");
/* 2937 */                               if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                 return;
/* 2939 */                               out.write("</td>\n          </tr>\n          ");
/* 2940 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2941 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2945 */                           if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2946 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                           }
/*      */                           
/* 2949 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2950 */                           out.write("\n          ");
/*      */                           
/* 2952 */                           IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2953 */                           _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2954 */                           _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f4);
/*      */                           
/* 2956 */                           _jspx_th_c_005fif_005f8.setTest("${!(empty JBossProps.StartDate)}");
/* 2957 */                           int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2958 */                           if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                             for (;;) {
/* 2960 */                               out.write("\n          <tr>\n            <td height=\"23\" class=\"monitorinfoodd\">");
/* 2961 */                               out.print(FormatUtil.getString("am.webclient.common.activationtime.text"));
/* 2962 */                               out.write("</td>\n            <td class=\"monitorinfoodd\">");
/* 2963 */                               out.print(formatDT(props.getProperty("StartDate")));
/* 2964 */                               out.write("</td>\n          </tr>\n\n          <tr>\n            <td height=\"23\" class=\"monitorinfoeven\">");
/* 2965 */                               out.print(FormatUtil.getString("am.webclient.jboss.monitoringstarttime.text"));
/* 2966 */                               out.write("</td>\n            <td class=\"monitorinfoeven\">");
/* 2967 */                               out.print(formatDT(props.getProperty("collectionTime")));
/* 2968 */                               out.write("</td>\n          </tr>\n          ");
/* 2969 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2970 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2974 */                           if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2975 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                           }
/*      */                           
/* 2978 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2979 */                           out.write("\n\t\t  ");
/* 2980 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2981 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2985 */                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2986 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                       }
/*      */                       
/* 2989 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2990 */                       out.write("\n          ");
/*      */                       
/* 2992 */                       EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2993 */                       _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2994 */                       _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 2996 */                       _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 2997 */                       int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2998 */                       if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                         for (;;) {
/* 3000 */                           out.write("\n          <tr>\n            <td class=\"monitorinfoodd\">");
/* 3001 */                           out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3002 */                           out.write("Host Name</td>\n            <td class=\"monitorinfoodd\">-&nbsp;</td>\n          </tr>\n          <tr>\n            <td class=\"monitorinfoeven\">");
/* 3003 */                           out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 3004 */                           out.write("Host OS</td>\n            <td class=\"monitorinfoeven\">-</td>\n          </tr>\n          <tr>\n            <td class=\"monitorinfoodd\">");
/* 3005 */                           out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3006 */                           out.write("</td>\n            <td class=\"monitorinfoodd\">-</td>\n          </tr>\n          <tr>\n            <td class=\"monitorinfoeven\">");
/* 3007 */                           out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3008 */                           out.write("</td>\n            <td class=\"monitorinfoeven\">-</td>\n          </tr>\n          ");
/* 3009 */                           int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 3010 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3014 */                       if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 3015 */                         this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                       }
/*      */                       
/* 3018 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 3019 */                       out.write(32);
/*      */                       
/* 3021 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3022 */                       _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3023 */                       _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 3025 */                       _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 3026 */                       int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3027 */                       if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                         for (;;) {
/* 3029 */                           out.write("\n          <tr>\n            <td class=\"monitorinfoodd\">");
/* 3030 */                           out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3031 */                           out.write("</td>\n            ");
/*      */                           
/* 3033 */                           if (systeminfo.get("host_resid") != null)
/*      */                           {
/* 3035 */                             out.write("\n\t\t    <td class=\"monitorinfoeven\"><a href=\"showresource.do?resourceid=");
/* 3036 */                             out.print(systeminfo.get("host_resid"));
/* 3037 */                             out.write("&method=showResourceForResourceID\" class=\"staticlinks\" title=\"");
/* 3038 */                             out.print(systeminfo.get("HOSTNAME"));
/* 3039 */                             out.write(34);
/* 3040 */                             out.write(32);
/* 3041 */                             out.write(62);
/* 3042 */                             out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 3043 */                             out.write("&nbsp;(");
/* 3044 */                             out.print(systeminfo.get("HOSTIP"));
/* 3045 */                             out.write(")</a></td>\n\t\t\t");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/* 3050 */                             out.write("\n             <td class=\"monitorinfoeven\" title=\"");
/* 3051 */                             out.print(systeminfo.get("HOSTNAME"));
/* 3052 */                             out.write(34);
/* 3053 */                             out.write(32);
/* 3054 */                             out.write(62);
/* 3055 */                             out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 3056 */                             out.write("&nbsp;(");
/* 3057 */                             out.print(systeminfo.get("HOSTIP"));
/* 3058 */                             out.write(")</td>\n\t\t\t");
/*      */                           }
/* 3060 */                           out.write("\n          </tr>\n          <tr>\n            <td class=\"monitorinfoeven\">");
/* 3061 */                           out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 3062 */                           out.write("</td>\n              <td class=\"monitorinfoeven\">");
/* 3063 */                           out.print(FormatUtil.getString((String)systeminfo.get("HOSTOS")));
/* 3064 */                           out.write(" </td>\n          </tr>\n          <tr>\n            <td class=\"monitorinfoeven\">");
/* 3065 */                           out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3066 */                           out.write("</td>\n            <td class=\"monitorinfoeven\">");
/* 3067 */                           out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 3068 */                           out.write("</td>\n          </tr>\n          <tr>\n            <td class=\"monitorinfoodd\">");
/* 3069 */                           out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3070 */                           out.write("</td>\n            <td class=\"monitorinfoodd\">");
/* 3071 */                           out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 3072 */                           out.write("\n            </td>\n          </tr>\n\t\t  ");
/* 3073 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3074 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3078 */                       if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3079 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                       }
/*      */                       
/* 3082 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3083 */                       out.write(10);
/* 3084 */                       out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n<script>\n $(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 3085 */                       out.write("\n\t{\n\t\t");
/* 3086 */                       if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3088 */                       out.write(10);
/* 3089 */                       out.write(9);
/* 3090 */                       out.write(9);
/* 3091 */                       if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3093 */                       out.write("\n\t\tgetCustomFields('");
/* 3094 */                       if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3096 */                       out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 3097 */                       out.write("\n\t}\n\n});\n</script>\n");
/* 3098 */                       if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3100 */                       out.write(10);
/* 3101 */                       out.write(10);
/* 3102 */                       if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3104 */                       out.write(10);
/* 3105 */                       out.write(10);
/* 3106 */                       if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3108 */                       out.write(10);
/* 3109 */                       if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3111 */                       out.write(10);
/* 3112 */                       out.write(10);
/* 3113 */                       out.write(10);
/* 3114 */                       if (_jspx_meth_c_005fif_005f13(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3116 */                       out.write(10);
/* 3117 */                       out.write(10);
/* 3118 */                       out.write(10);
/* 3119 */                       if (_jspx_meth_c_005fif_005f14(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3121 */                       out.write("\n\n\n<tr>\n<td colspan=\"2\" class=\"");
/* 3122 */                       if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3124 */                       out.write("\" align=\"right\" style=\"padding:2px;\">\n<input type=\"button\" value=\"");
/* 3125 */                       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3127 */                       out.write("\" onclick=\"getCustomFields('");
/* 3128 */                       if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3130 */                       out.write(39);
/* 3131 */                       out.write(44);
/* 3132 */                       out.write(39);
/* 3133 */                       if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3135 */                       out.write("',false,'CustomFieldValues',false);\" class=\"buttons btn_custom\"/>");
/* 3136 */                       out.write("\n</td>\n</tr>\n\n\n");
/* 3137 */                       out.write("\n\t\t  </table>\n\t\t  ");
/*      */                       
/* 3139 */                       PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3140 */                       _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3141 */                       _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 3143 */                       _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 3144 */                       int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3145 */                       if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                         for (;;) {
/* 3147 */                           out.write("\n\t\t   ");
/*      */                           
/* 3149 */                           IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3150 */                           _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3151 */                           _jspx_th_c_005fif_005f15.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */                           
/* 3153 */                           _jspx_th_c_005fif_005f15.setTest("${showdata=='1'}");
/* 3154 */                           int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3155 */                           if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                             for (;;) {
/* 3157 */                               out.write("\n\t\t  <div align=\"center\">\n<a style=cursor:pointer;><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" onclick=\"javascript:toggleDiv('Reconfigure')\">\n\n<tr>\n              <td>&nbsp;</td>\n            </tr>\n            <tr>\n              <td><table width=\"75%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"getmoredatatable\">\n                  <tr>\n                    <td width=\"13%\" background=\"../images/getmoredata_bg.gif\"><img src=\"../images/icon_getmoredata.gif\" width=\"35\" height=\"35\" border=\"0\" vspace=\"2\" hspace=\"5\"></td>\n                    <td width=\"87%\" background=\"../images/getmoredata_bg.gif\">");
/* 3158 */                               out.print(FormatUtil.getString("am.webclient.configureimage.appservers.text"));
/* 3159 */                               out.write("</td>\n                  </tr>\n                </table></td>\n            </tr>\n      </table></a>\n\t\t     ");
/* 3160 */                               out.print(FormatUtil.getString("am.webclient.jboss.troubleshoot.text", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link") }));
/* 3161 */                               out.write("\n\t\t     </div>\n\t\t  ");
/* 3162 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3163 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3167 */                           if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3168 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                           }
/*      */                           
/* 3171 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3172 */                           out.write("\n\t\t  ");
/* 3173 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3174 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3178 */                       if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3179 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                       }
/*      */                       
/* 3182 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3183 */                       out.write("\n      </td>\n      <td width=\"42%\" align=\"center\" valign=\"top\" >\n        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n          <tbody>\n            <tr>\n              <td height=\"36\" colspan=\"3\" class=\"tableheadingbborder\">");
/* 3184 */                       out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 3185 */                       out.write("</td>\n            </tr>\n            <tr>\n              <td colspan=\"3\"> <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n                  <tr>\n                    <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3186 */                       if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3188 */                       out.write("&period=1&resourcename=");
/* 3189 */                       if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3191 */                       out.write("')\">\n                      <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3192 */                       out.print(seven_days_text);
/* 3193 */                       out.write("\"></a></td>\n                    <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3194 */                       if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3196 */                       out.write("&period=2&resourcename=");
/* 3197 */                       if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3199 */                       out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3200 */                       out.print(thiry_days_text);
/* 3201 */                       out.write("\"></a></td>\n                  </tr>\n                </table></td>\n            </tr>\n            <tr align=\"center\">\n              <td height=\"36\" colspan=\"3\">");
/*      */                       
/* 3203 */                       AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3204 */                       _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 3205 */                       _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 3207 */                       _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                       
/* 3209 */                       _jspx_th_awolf_005fpiechart_005f0.setWidth("250");
/*      */                       
/* 3211 */                       _jspx_th_awolf_005fpiechart_005f0.setHeight("200");
/*      */                       
/* 3213 */                       _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                       
/* 3215 */                       _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                       
/* 3217 */                       _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                       
/* 3219 */                       _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 3220 */                       int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3221 */                       if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3222 */                         if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3223 */                           out = _jspx_page_context.pushBody();
/* 3224 */                           _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3225 */                           _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3228 */                           out.write("\n                ");
/*      */                           
/* 3230 */                           Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3231 */                           _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3232 */                           _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                           
/* 3234 */                           _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3235 */                           int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3236 */                           if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3237 */                             if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3238 */                               out = _jspx_page_context.pushBody();
/* 3239 */                               _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3240 */                               _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3243 */                               out.write(32);
/*      */                               
/* 3245 */                               AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3246 */                               _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3247 */                               _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                               
/* 3249 */                               _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                               
/* 3251 */                               _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 3252 */                               int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3253 */                               if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3254 */                                 this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                               }
/*      */                               
/* 3257 */                               this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3258 */                               out.write("\n                ");
/*      */                               
/* 3260 */                               AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3261 */                               _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3262 */                               _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                               
/* 3264 */                               _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                               
/* 3266 */                               _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 3267 */                               int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3268 */                               if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3269 */                                 this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                               }
/*      */                               
/* 3272 */                               this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3273 */                               out.write(32);
/* 3274 */                               int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3275 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3278 */                             if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3279 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3282 */                           if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3283 */                             this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                           }
/*      */                           
/* 3286 */                           this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3287 */                           out.write("\n                ");
/* 3288 */                           int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3289 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3292 */                         if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3293 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3296 */                       if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3297 */                         this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */                       }
/*      */                       
/* 3300 */                       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3301 */                       out.write("</td>\n            </tr>\n\t\t\t<tr>\n\t\t\t  <td width=\"48%\" height=\"36\" class=\"yellowgrayborder\" colspan=\"2\"><span class=\"bodytext\">");
/* 3302 */                       out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 3303 */                       out.write(" :</span>\n\t\t\t <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3304 */                       out.print(resourceid);
/* 3305 */                       out.write("&attributeid=308&alertconfigurl=");
/* 3306 */                       out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=308&attributeToSelect=308&redirectto=" + encodeurl));
/* 3307 */                       out.write("')\">\n\t\t\t\t");
/* 3308 */                       out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "308")));
/* 3309 */                       out.write("</a>\n\t\t\t  </td>\n\t\t\t  <td width=\"51%\" align=\"right\" class=\"yellowgrayborder\"><img src=\"/images/icon_associateaction.gif\" border=\"0\" align=\"absmiddle\">&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3310 */                       if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 3312 */                       out.write("&attributeIDs=308,309&attributeToSelect=308&redirectto=");
/* 3313 */                       out.print(encodeurl);
/* 3314 */                       out.write("' class=\"links\">");
/* 3315 */                       out.print(ALERTCONFIG_TEXT);
/* 3316 */                       out.write("</a>&nbsp;</td>\n\t\t\t</tr>\n          </tbody>\n        </table>\n        </td>\n\n    </tr>\n  </table>\n  <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3317 */                       out.write("<!--$Id$-->\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td>\n<div id=\"customfieldsfullListDiv\" style='overflow: auto; display:none; width: 100%;'>\n</div>\n<div id=\"customfieldsloadingdiv\" style='text-align:center;height:200px;width: 100%;display: none;'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\n</td></tr></table>\n");
/* 3318 */                       out.write("</td></tr></table>\n  <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n      <td>&nbsp;</td>\n    </tr>\n  </table>\n  ");
/*      */                       
/* 3320 */                       IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3321 */                       _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3322 */                       _jspx_th_c_005fif_005f16.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 3324 */                       _jspx_th_c_005fif_005f16.setTest("${showdata=='1'}");
/* 3325 */                       int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3326 */                       if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                         for (;;) {
/* 3328 */                           out.write("\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n  <td width=\"100%\" height=\"29\" class=\"tableheadingtrans\" >");
/* 3329 */                           out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3330 */                           out.write(32);
/* 3331 */                           out.write(45);
/* 3332 */                           out.write(32);
/* 3333 */                           out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3334 */                           out.write("&nbsp;</td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n  <td width=\"405\" height=\"127\" valign=\"top\">\n  <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n  <tr>\n  <td width=\"91%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3335 */                           if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                             return;
/* 3337 */                           out.write("&attributeid=315&period=-7&resourcename=");
/* 3338 */                           if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                             return;
/* 3340 */                           out.write("')\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3341 */                           out.print(seven_days_text);
/* 3342 */                           out.write("\"></a></td>\n  <td width=\"9%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3343 */                           if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                             return;
/* 3345 */                           out.write("&attributeid=315&period=-30&resourcename=");
/* 3346 */                           if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                             return;
/* 3348 */                           out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3349 */                           out.print(thiry_days_text);
/* 3350 */                           out.write("\"></a></td>\n  </tr>\n  ");
/*      */                           
/* 3352 */                           Properties data = dataHandler.getResponseTimePerformanceData(resourceid);
/* 3353 */                           String min = data.getProperty("min");
/* 3354 */                           String max = data.getProperty("max");
/* 3355 */                           String avg = data.getProperty("avg");
/* 3356 */                           pageContext.setAttribute("responsetimestats", data);
/* 3357 */                           wlsGraph.setParam(resourceid, "RESPONSETIME");
/*      */                           
/* 3359 */                           out.write("\n  <tr>\n  <td colspan=\"2\">\n  ");
/*      */                           
/* 3361 */                           TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3362 */                           _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3363 */                           _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_c_005fif_005f16);
/*      */                           
/* 3365 */                           _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("wlsGraph");
/*      */                           
/* 3367 */                           _jspx_th_awolf_005ftimechart_005f0.setWidth("300");
/*      */                           
/* 3369 */                           _jspx_th_awolf_005ftimechart_005f0.setHeight("120");
/*      */                           
/* 3371 */                           _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                           
/* 3373 */                           _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(xaxis_time);
/*      */                           
/* 3375 */                           _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(yaxis_restime);
/* 3376 */                           int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 3377 */                           if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 3378 */                             if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3379 */                               out = _jspx_page_context.pushBody();
/* 3380 */                               _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 3381 */                               _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3384 */                               out.write(10);
/* 3385 */                               out.write(32);
/* 3386 */                               out.write(32);
/* 3387 */                               int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 3388 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3391 */                             if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3392 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3395 */                           if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 3396 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                           }
/*      */                           
/* 3399 */                           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 3400 */                           out.write(" </td>\n  </tr>\n  </table></td>\n  <td width=\"562\" valign=\"top\"> <br> <br>\n  <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n  <tr>\n  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3401 */                           if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                             return;
/* 3403 */                           out.write("</span></td>\n  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3404 */                           if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                             return;
/* 3406 */                           out.write("</span></td>\n  <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 3407 */                           if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                             return;
/* 3409 */                           out.write("</span></td>\n  </tr>\n  <tr>\n  <td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3410 */                           out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 3411 */                           out.write(32);
/* 3412 */                           out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3413 */                           out.write(" </td>\n  <td width=\"26%\" height=\"19\" class=\"whitegrayborder\">\n  ");
/* 3414 */                           if (_jspx_meth_c_005fif_005f17(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                             return;
/* 3416 */                           out.write(10);
/* 3417 */                           out.write(32);
/* 3418 */                           out.write(32);
/*      */                           
/* 3420 */                           IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3421 */                           _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 3422 */                           _jspx_th_c_005fif_005f18.setParent(_jspx_th_c_005fif_005f16);
/*      */                           
/* 3424 */                           _jspx_th_c_005fif_005f18.setTest("${responsetime !='-1'}");
/* 3425 */                           int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 3426 */                           if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                             for (;;) {
/* 3428 */                               out.write(10);
/* 3429 */                               out.write(32);
/* 3430 */                               out.write(32);
/* 3431 */                               if (_jspx_meth_fmt_005fformatNumber_005f0(_jspx_th_c_005fif_005f18, _jspx_page_context))
/*      */                                 return;
/* 3433 */                               out.write(32);
/* 3434 */                               out.print(FormatUtil.getString("ms"));
/* 3435 */                               out.write(10);
/* 3436 */                               out.write(32);
/* 3437 */                               out.write(32);
/* 3438 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 3439 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3443 */                           if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 3444 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                           }
/*      */                           
/* 3447 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 3448 */                           out.write("\n  </td>\n  <td class=\"whitegrayborder\" width=\"29%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3449 */                           if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                             return;
/* 3451 */                           out.write("&attributeid=315&alertconfigurl=");
/* 3452 */                           out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=315&attributeToSelect=315&redirectto=" + encodeurl));
/* 3453 */                           out.write("')\">");
/* 3454 */                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "315")));
/* 3455 */                           out.write("</a></td>\n  </tr>\n  <tr>\n  <td  colspan=\"3\" height=\"21\" class=\"yellowgrayborder\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;\n  <a class=\"links\" href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3456 */                           if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                             return;
/* 3458 */                           out.write("&attributeIDs=315&attributeToSelect=315&redirectto=");
/* 3459 */                           out.print(URLEncoder.encode("/showresource.do?method=showdetails&type=JBOSS-server&" + request.getQueryString()));
/* 3460 */                           out.write("\"\n                >");
/* 3461 */                           out.print(ALERTCONFIG_TEXT);
/* 3462 */                           out.write("</a></td>\n\t</tr>\n\t</table>\n\t</td>\n\t</tr>\n\t</table>\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t<tr>\n\t<td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n\t</tr>\n\t</table>\n  ");
/* 3463 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3464 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3468 */                       if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3469 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                       }
/*      */                       
/* 3472 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3473 */                       out.write(10);
/* 3474 */                       out.write(32);
/* 3475 */                       out.write(32);
/*      */                       
/* 3477 */                       IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3478 */                       _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 3479 */                       _jspx_th_c_005fif_005f19.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 3481 */                       _jspx_th_c_005fif_005f19.setTest("${showdata=='2'}");
/* 3482 */                       int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 3483 */                       if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                         for (;;) {
/* 3485 */                           out.write("\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n    <tr>\n      <td width=\"50%\" height=\"31\" class=\"tableheading\">");
/* 3486 */                           out.print(FormatUtil.getString("am.reporttab.shortname.jvm.text"));
/* 3487 */                           out.write("<a name=\"jvm\" id=\"jvm\"></a> - ");
/* 3488 */                           out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3489 */                           out.write("</td>\n      <td width=\"50%\" class=\"tableheading\">");
/* 3490 */                           out.print(FormatUtil.getString("am.webclient.weblogic.responsetimelastonehr.text"));
/* 3491 */                           out.write("</td>\n    </tr>\n  </table>\n\n  <table width=\"99%\" height=\"299\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n    <tr>\n      ");
/*      */                           
/* 3493 */                           Properties data = dataHandler.getServerStaticData(resourceid);
/*      */                           
/* 3495 */                           data = dataHandler.getJVMPerformanceData(resourceid);
/*      */                           
/* 3497 */                           String min = data.getProperty("min");
/* 3498 */                           String max = data.getProperty("max");
/* 3499 */                           String avg = data.getProperty("avg");
/* 3500 */                           String total = data.getProperty("total");
/*      */                           
/* 3502 */                           String maxfreemem = data.getProperty("maxfreemem");
/* 3503 */                           String maxfreemempercent = "";
/* 3504 */                           if (data.getProperty("maxfreemempercent") != null)
/*      */                           {
/* 3506 */                             maxfreemempercent = data.getProperty("maxfreemempercent");
/*      */                           }
/*      */                           
/* 3509 */                           Double maxmem = Double.valueOf(0.0D);
/*      */                           try
/*      */                           {
/* 3512 */                             if (data.getProperty("maxmemoryjvm") != null)
/*      */                             {
/* 3514 */                               maxmem = Double.valueOf(Double.parseDouble(data.getProperty("maxmemoryjvm")) / 1048576.0D);
/*      */                             }
/*      */                             
/*      */                           }
/*      */                           catch (Exception e)
/*      */                           {
/* 3520 */                             e.printStackTrace();
/*      */                           }
/*      */                           
/* 3523 */                           if (total == null)
/*      */                           {
/* 3525 */                             total = "";
/*      */                           }
/*      */                           else
/*      */                           {
/*      */                             try
/*      */                             {
/* 3531 */                               total = String.valueOf(Long.parseLong(total) * 1024L);
/*      */                             }
/*      */                             catch (NumberFormatException ne) {}
/*      */                           }
/*      */                           
/*      */ 
/* 3537 */                           wlsGraph.setParam(resourceid, "JVM");
/*      */                           
/* 3539 */                           out.write("\n\n      <td width=\"50%\" height=\"64\" class=\"rbborder\">\n\t<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n          <tr>\n            <td width=\"90%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3540 */                           if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3542 */                           out.write("&attributeid=316&period=-7&resourcename=");
/* 3543 */                           if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3545 */                           out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3546 */                           out.print(seven_days_text);
/* 3547 */                           out.write("\"></a></td>\n\t        <td width=\"10%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3548 */                           if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3550 */                           out.write("&attributeid=316&period=-30&resourcename=");
/* 3551 */                           if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3553 */                           out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3554 */                           out.print(thiry_days_text);
/* 3555 */                           out.write("\"></a></td>\n\t</tr>\n\t      <tr align=\"center\">\n            <td colspan=\"2\"> ");
/*      */                           
/* 3557 */                           TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3558 */                           _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3559 */                           _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_c_005fif_005f19);
/*      */                           
/* 3561 */                           _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("wlsGraph");
/*      */                           
/* 3563 */                           _jspx_th_awolf_005ftimechart_005f1.setWidth("300");
/*      */                           
/* 3565 */                           _jspx_th_awolf_005ftimechart_005f1.setHeight("130");
/*      */                           
/* 3567 */                           _jspx_th_awolf_005ftimechart_005f1.setLegend("false");
/*      */                           
/* 3569 */                           _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(xaxis_time);
/*      */                           
/* 3571 */                           _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(yaxis_heap_usage);
/* 3572 */                           int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3573 */                           if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3574 */                             if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3575 */                               out = _jspx_page_context.pushBody();
/* 3576 */                               _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3577 */                               _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3580 */                               out.write("\n              ");
/* 3581 */                               int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3582 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3585 */                             if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3586 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3589 */                           if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3590 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                           }
/*      */                           
/* 3593 */                           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3594 */                           out.write(" </td>\n\t  </tr>\n\t  </table>\n\t  </td>\n\n\n\t  ");
/*      */                           
/* 3596 */                           data = dataHandler.getResponseTimePerformanceData(resourceid);
/*      */                           
/* 3598 */                           min = data.getProperty("min");
/* 3599 */                           max = data.getProperty("max");
/* 3600 */                           avg = data.getProperty("avg");
/* 3601 */                           pageContext.setAttribute("responsetimestats", data);
/* 3602 */                           wlsGraph.setParam(resourceid, "RESPONSETIME");
/* 3603 */                           String jvmversion = FormatUtil.getString("am.webclient.common.nodata.text");
/* 3604 */                           if (props.getProperty("JavaVersion") != null)
/*      */                           {
/* 3606 */                             jvmversion = props.getProperty("JavaVersion");
/*      */                           }
/*      */                           
/* 3609 */                           out.write("\n      <td width=\"50%\" height=\"64\" align=\"center\" class=\"bottomborder\">\n\t<table width=\"95%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n            <td width=\"91%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3610 */                           if (_jspx_meth_c_005fout_005f31(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3612 */                           out.write("&attributeid=315&period=-7&resourcename=");
/* 3613 */                           if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3615 */                           out.write("')\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3616 */                           out.print(seven_days_text);
/* 3617 */                           out.write("\"></a></td>\n\t        <td width=\"9%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3618 */                           if (_jspx_meth_c_005fout_005f33(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3620 */                           out.write("&attributeid=315&period=-30&resourcename=");
/* 3621 */                           if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3623 */                           out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3624 */                           out.print(thiry_days_text);
/* 3625 */                           out.write("\"></a></td>\n\t</tr>\n\t      <tr align=\"center\">\n            <td colspan=\"2\"> ");
/*      */                           
/* 3627 */                           TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3628 */                           _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 3629 */                           _jspx_th_awolf_005ftimechart_005f2.setParent(_jspx_th_c_005fif_005f19);
/*      */                           
/* 3631 */                           _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("wlsGraph");
/*      */                           
/* 3633 */                           _jspx_th_awolf_005ftimechart_005f2.setWidth("300");
/*      */                           
/* 3635 */                           _jspx_th_awolf_005ftimechart_005f2.setHeight("130");
/*      */                           
/* 3637 */                           _jspx_th_awolf_005ftimechart_005f2.setLegend("false");
/*      */                           
/* 3639 */                           _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(xaxis_time);
/*      */                           
/* 3641 */                           _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(yaxis_restime);
/* 3642 */                           int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 3643 */                           if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 3644 */                             if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3645 */                               out = _jspx_page_context.pushBody();
/* 3646 */                               _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 3647 */                               _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3650 */                               out.write("\n              ");
/* 3651 */                               int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 3652 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3655 */                             if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3656 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3659 */                           if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 3660 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                           }
/*      */                           
/* 3663 */                           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3664 */                           out.write(" </td>\n    </tr>\n\t</table>\n\t</td>\n\t</tr>\n    <tr>\n      <td height=\"220\" valign=\"top\" class=\"rborder\">\n        <table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" >\n          <tbody>\n\t\t   <tr >\n              <td width=\"35%\"  class=\"columnheading\" ><span class=\"bodytextbold\">");
/* 3665 */                           if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3667 */                           out.write("</span></td>\n              <td width=\"65%\"  class=\"columnheading\" ><span class=\"bodytextbold\">");
/* 3668 */                           if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3670 */                           out.write("</span></td>\n            </tr>\n            <tr >\n              <td width=\"35%\"  class=\"whitegrayborder\" >");
/* 3671 */                           out.print(FormatUtil.getString("am.webclient.common.total.text"));
/* 3672 */                           out.write(32);
/* 3673 */                           out.print(FormatUtil.getString("Heap Size"));
/* 3674 */                           out.write(" </td>\n                ");
/*      */                           
/* 3676 */                           if ((total != null) && (!total.equals("")))
/*      */                           {
/*      */ 
/* 3679 */                             out.write("\n              <td width=\"65%\"  class=\"whitegrayborder\" >");
/* 3680 */                             out.print(formatNumber(total));
/* 3681 */                             out.write("&nbsp; ");
/* 3682 */                             out.print(FormatUtil.getString("Bytes"));
/* 3683 */                             out.write("</td>\n               ");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 3689 */                             out.write("\n               <td width=\"65%\"  class=\"whitegrayborder\" >");
/* 3690 */                             out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3691 */                             out.write(" &nbsp;</td>\n               ");
/*      */                           }
/*      */                           
/*      */ 
/* 3695 */                           out.write("\n            </tr>\n            <tr>\n              <td  class=\"yellowgrayborder\" >");
/* 3696 */                           out.print(FormatUtil.getString("am.webclient.jboss.javaversion.text"));
/* 3697 */                           out.write("\n                </td>\n              <td  class=\"yellowgrayborder\" >");
/* 3698 */                           out.print(jvmversion);
/* 3699 */                           out.write("\n              </td>\n            </tr>\n            <tr>\n              <td  class=\"whitegrayborder\" >");
/* 3700 */                           out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 3701 */                           out.write(32);
/* 3702 */                           out.print(FormatUtil.getString("Heap Size"));
/* 3703 */                           out.write("\n                </td>\n                ");
/*      */                           
/* 3705 */                           IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3706 */                           _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 3707 */                           _jspx_th_c_005fif_005f20.setParent(_jspx_th_c_005fif_005f19);
/*      */                           
/* 3709 */                           _jspx_th_c_005fif_005f20.setTest("${ !empty heapsizecurrent}");
/* 3710 */                           int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 3711 */                           if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */                             for (;;) {
/* 3713 */                               out.write("\n                <td  class=\"whitegrayborder\" >");
/* 3714 */                               if (_jspx_meth_fmt_005fformatNumber_005f1(_jspx_th_c_005fif_005f20, _jspx_page_context))
/*      */                                 return;
/* 3716 */                               out.write("&nbsp;");
/* 3717 */                               out.print(FormatUtil.getString("Bytes"));
/* 3718 */                               out.write("\n               &nbsp;&nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3719 */                               out.print(resourceid);
/* 3720 */                               out.write("&attributeid=316&alertconfigurl=");
/* 3721 */                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=316&attributeToSelect=316&redirectto=" + encodeurl));
/* 3722 */                               out.write("')\">\n\t\t              ");
/* 3723 */                               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "316")));
/* 3724 */                               out.write("\n              </a>\n                </td>\n                ");
/* 3725 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 3726 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3730 */                           if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 3731 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */                           }
/*      */                           
/* 3734 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3735 */                           out.write("\n                ");
/*      */                           
/* 3737 */                           IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3738 */                           _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 3739 */                           _jspx_th_c_005fif_005f21.setParent(_jspx_th_c_005fif_005f19);
/*      */                           
/* 3741 */                           _jspx_th_c_005fif_005f21.setTest("${ empty heapsizecurrent}");
/* 3742 */                           int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 3743 */                           if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                             for (;;) {
/* 3745 */                               out.write("\n                <td  class=\"whitegrayborder\" >");
/* 3746 */                               out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3747 */                               out.write("\n\n                </td>\n                ");
/* 3748 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 3749 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3753 */                           if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 3754 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                           }
/*      */                           
/* 3757 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 3758 */                           out.write("\n            </tr>\n             <tr >\n\t    \t                  <td width=\"35%\"  class=\"whitegrayborder\" >");
/* 3759 */                           out.print(FormatUtil.getString("am.webclient.common.Maxtotalmemory.text"));
/* 3760 */                           out.write(" </td>\n\t    \t                    ");
/*      */                           
/*      */ 
/* 3763 */                           if ((maxfreemem != null) && (!maxfreemem.equals("")) && (maxfreemem.indexOf("-") == -1))
/*      */                           {
/*      */ 
/* 3766 */                             out.write("\n\t    \t                  <td width=\"65%\"  class=\"whitegrayborder\" >");
/* 3767 */                             out.print(formatNumber(maxfreemem));
/* 3768 */                             out.write("&nbsp;  &nbsp;&nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3769 */                             out.print(resourceid);
/* 3770 */                             out.write("&attributeid=321&alertconfigurl=");
/* 3771 */                             out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=321&attributeToSelect=321&redirectto=" + encodeurl));
/* 3772 */                             out.write("')\">\n                ");
/* 3773 */                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "321")));
/* 3774 */                             out.write("\n                                  </a>\n                               </td>\n\t    \t                   ");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 3780 */                             out.write("\n\t    \t                   <td width=\"65%\"  class=\"whitegrayborder\" >");
/* 3781 */                             out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3782 */                             out.write(" &nbsp;</td>\n\t    \t                   ");
/*      */                           }
/*      */                           
/*      */ 
/* 3786 */                           out.write("\n            </tr>\n            <tr >\n\t    \t    \t                  <td width=\"35%\"  class=\"whitegrayborder\" >");
/* 3787 */                           out.print(FormatUtil.getString("am.webclient.common.MaxtotalmemoryPercent.text"));
/* 3788 */                           out.write(" </td>\n\t    \t    \t                    ");
/*      */                           
/* 3790 */                           if ((maxfreemempercent != null) && (!maxfreemempercent.equals("")) && (maxfreemempercent.indexOf("-") == -1))
/*      */                           {
/*      */ 
/* 3793 */                             out.write("\n\t    \t    \t                  <td width=\"65%\"  class=\"whitegrayborder\" >");
/* 3794 */                             out.print(formatNumber(maxfreemempercent));
/* 3795 */                             out.write("&nbsp;  &nbsp;&nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3796 */                             out.print(resourceid);
/* 3797 */                             out.write("&attributeid=322&alertconfigurl=");
/* 3798 */                             out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=322&attributeToSelect=322&redirectto=" + encodeurl));
/* 3799 */                             out.write("')\">\n\t    \t\t              ");
/* 3800 */                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "322")));
/* 3801 */                             out.write("\n\t                                      </a>\n\t                                   </td>\n\t    \t    \t                   ");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 3807 */                             out.write("\n\t    \t    \t                   <td width=\"65%\"  class=\"whitegrayborder\" >");
/* 3808 */                             out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3809 */                             out.write(" &nbsp;</td>\n\t    \t    \t                   ");
/*      */                           }
/*      */                           
/*      */ 
/* 3813 */                           out.write("\n            </tr>\n               <tr >\n\t    \t    \t                  <td width=\"35%\"  class=\"whitegrayborder\" >");
/* 3814 */                           out.print(FormatUtil.getString("am.webclient.common.Maxjvmmemory.text"));
/* 3815 */                           out.write(" </td>\n\t    \t    \t                    ");
/*      */                           
/* 3817 */                           if ((maxmem != null) && (!maxmem.equals("")) && (maxfreemempercent.indexOf("-") == -1))
/*      */                           {
/*      */ 
/* 3820 */                             out.write("\n\t    \t    \t                  <td width=\"65%\"  class=\"whitegrayborder\" >");
/* 3821 */                             out.print(formatNumber(maxmem));
/* 3822 */                             out.write("&nbsp;\n\n\t                                      </a>\n\t                                   </td>\n\t    \t    \t                   ");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 3828 */                             out.write("\n\t    \t    \t                   <td width=\"65%\"  class=\"whitegrayborder\" >");
/* 3829 */                             out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3830 */                             out.write(" &nbsp;</td>\n\t    \t    \t                   ");
/*      */                           }
/*      */                           
/*      */ 
/* 3834 */                           out.write("\n            </tr>\n\n\n\t\t\t <tr>\n              <td class=\"whitegrayborder\" >&nbsp;\n              </td>\n              <td  align=\"right\" class=\"whitegrayborder\" ><img src=\"/images/icon_associateaction.gif\" border=\"0\" align=\"absmiddle\">&nbsp;\n               <a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3835 */                           if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3837 */                           out.write("&attributeIDs=316,321,322&attributeToSelect=316&redirectto=");
/* 3838 */                           out.print(encodeurl);
/* 3839 */                           out.write("\" class=\"links\">\n               ");
/* 3840 */                           out.print(ALERTCONFIG_TEXT);
/* 3841 */                           out.write("</a>\n </td>\n            </tr>\n          </tbody>\n        </table>\n      </td>\n      <td align=\"center\" valign=\"top\">\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n <tr >\n            <td width=\"60%\" class=\"columnheading\"><span class=\"bodytextbold\">");
/* 3842 */                           if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3844 */                           out.write(" </span></td>\n              <td width=\"65%\"  class=\"columnheading\" ><span class=\"bodytextbold\">");
/* 3845 */                           if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3847 */                           out.write(" </span></td>\n          </tr>\n          <tr >\n            <td width=\"60%\" class=\"whitegrayborder\">");
/* 3848 */                           out.print(FormatUtil.getString("am.webclient.common.minimum.text"));
/* 3849 */                           out.write(32);
/* 3850 */                           out.print(FormatUtil.getString("Response Time"));
/* 3851 */                           out.write("</td>\n            <td width=\"40%\" class=\"whitegrayborder\">");
/* 3852 */                           if (_jspx_meth_fmt_005fformatNumber_005f2(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3854 */                           out.write(32);
/* 3855 */                           out.print(FormatUtil.getString("ms"));
/* 3856 */                           out.write("</td>\n          </tr>\n          <tr>\n            <td  class=\"yellowgrayborder\">");
/* 3857 */                           out.print(FormatUtil.getString("am.webclient.common.maximum.text"));
/* 3858 */                           out.write(32);
/* 3859 */                           out.print(FormatUtil.getString("Response Time"));
/* 3860 */                           out.write("</td>\n            <td class=\"yellowgrayborder\">");
/* 3861 */                           if (_jspx_meth_fmt_005fformatNumber_005f3(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3863 */                           out.write(32);
/* 3864 */                           out.print(FormatUtil.getString("ms"));
/* 3865 */                           out.write("</td>\n          </tr>\n          <tr>\n            <td  class=\"whitegrayborder\">");
/* 3866 */                           out.print(FormatUtil.getString("Average Response Time"));
/* 3867 */                           out.write("</td>\n            <td class=\"whitegrayborder\">");
/* 3868 */                           if (_jspx_meth_fmt_005fformatNumber_005f4(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3870 */                           out.write(32);
/* 3871 */                           out.print(FormatUtil.getString("ms"));
/* 3872 */                           out.write("</td>\n          </tr>\n\t<tr >\n\t<td   class=\"yellowgrayborder\">");
/* 3873 */                           out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 3874 */                           out.write(32);
/* 3875 */                           out.print(FormatUtil.getString("Response Time"));
/* 3876 */                           out.write("</td>\n\t");
/* 3877 */                           if (_jspx_meth_c_005fif_005f22(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3879 */                           out.write(10);
/* 3880 */                           out.write(9);
/*      */                           
/* 3882 */                           IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3883 */                           _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 3884 */                           _jspx_th_c_005fif_005f23.setParent(_jspx_th_c_005fif_005f19);
/*      */                           
/* 3886 */                           _jspx_th_c_005fif_005f23.setTest("${responsetime !='-1'}");
/* 3887 */                           int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 3888 */                           if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */                             for (;;) {
/* 3890 */                               out.write("\n            <td class=\"yellowgrayborder\">");
/* 3891 */                               if (_jspx_meth_fmt_005fformatNumber_005f5(_jspx_th_c_005fif_005f23, _jspx_page_context))
/*      */                                 return;
/* 3893 */                               out.write(32);
/* 3894 */                               out.print(FormatUtil.getString("ms"));
/* 3895 */                               out.write(10);
/* 3896 */                               out.write(9);
/* 3897 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 3898 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3902 */                           if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 3903 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*      */                           }
/*      */                           
/* 3906 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 3907 */                           out.write("\n\t&nbsp;&nbsp;&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3908 */                           if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3910 */                           out.write("&attributeid=315&alertconfigurl=");
/* 3911 */                           out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=315&attributeToSelect=315&redirectto=" + encodeurl));
/* 3912 */                           out.write("')\">");
/* 3913 */                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "315")));
/* 3914 */                           out.write("\n\t</a>\n\t</td>\n\t</tr>\n          <tr align=\"right\">\n            <td colspan=\"2\" class=\"whitegrayborder\">\n            <img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;\n            <a class=\"links\" href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3915 */                           if (_jspx_meth_c_005fout_005f37(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                             return;
/* 3917 */                           out.write("&attributeIDs=315&attributeToSelect=315&redirectto=");
/* 3918 */                           out.print(URLEncoder.encode("/showresource.do?method=showdetails&type=JBOSS-server&" + request.getQueryString()));
/* 3919 */                           out.write("\"\n              >");
/* 3920 */                           out.print(ALERTCONFIG_TEXT);
/* 3921 */                           out.write("</a></td>\n          </tr>\n        </table>\n      </td>\n    </tr>\n  </table>\n\n  <table width=\"100%\"><tr>\n        <td align=\"right\"><a href=\"#jbtop\" class=\"staticlinks\"> ");
/* 3922 */                           out.print(FormatUtil.getString("am.webclient.common.top.text"));
/* 3923 */                           out.write("</a></td>\n      </tr></table>\n    <a name=\"ejb\"> </a>\n\t<div id=\"actionstatus1\">");
/* 3924 */                           out.print(FormatUtil.getString("am.webclient.jboss.ejbstatistics.text"));
/* 3925 */                           out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n\t<div id=\"ejbdetails\"></div>\n\n    <a name=\"jdbc\"> </a>\n\t<div id=\"actionstatus2\">");
/* 3926 */                           out.print(FormatUtil.getString("am.webclient.jboss.jdbcpoolsdetails.text"));
/* 3927 */                           out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n\t<div id=\"jdbcdetails\"></div>\n  <table width=\"100%\"><tr>\n        <td align=\"right\"><a href=\"#jbtop\" class=\"staticlinks\"> ");
/* 3928 */                           out.print(FormatUtil.getString("am.webclient.common.top.text"));
/* 3929 */                           out.write("</a></td>\n      </tr></table>\n    <a name=\"webapp\"> </a>\n\t<div id=\"actionstatus3\">");
/* 3930 */                           out.print(FormatUtil.getString("am.webclient.jboss.webapplications.text"));
/* 3931 */                           out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n\t<div id=\"webappdetails\"></div>\n  <table width=\"100%\"><tr>\n        <td align=\"right\"><a href=\"#jbtop\" class=\"staticlinks\"> ");
/* 3932 */                           out.print(FormatUtil.getString("am.webclient.common.top.text"));
/* 3933 */                           out.write("</a></td>\n      </tr></table>\n\t");
/* 3934 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 3935 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3939 */                       if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 3940 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                       }
/*      */                       
/* 3943 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3944 */                       out.write("\n</form>\n\n\n");
/*      */                       
/* 3946 */                       String mopRedirectString = encodeurl;
/* 3947 */                       String linkForMopAction = "/MBeanOperationAction.do?method=showInitialScreen&resourceid=" + resourceid + "&redirectto=" + mopRedirectString;
/* 3948 */                       ArrayList mopActions = com.adventnet.appmanager.fault.FaultUtil.getMBeanOperationActionsForResourceID(resourceid, request.getRemoteUser(), request.isUserInRole("OPERATOR"));
/* 3949 */                       if (mopActions.size() > 0)
/*      */                       {
/* 3951 */                         request.setAttribute("executeMopActions", mopActions);
/*      */                       }
/* 3953 */                       request.setAttribute("showrefreshnowoption", "true");
/*      */                       
/* 3955 */                       out.write("<br><br>\n");
/* 3956 */                       out.write("<!--$Id$-->\n");
/* 3957 */                       com.adventnet.appmanager.cam.beans.CAMGraphs camGraph = null;
/* 3958 */                       camGraph = (com.adventnet.appmanager.cam.beans.CAMGraphs)_jspx_page_context.getAttribute("camGraph", 1);
/* 3959 */                       if (camGraph == null) {
/* 3960 */                         camGraph = new com.adventnet.appmanager.cam.beans.CAMGraphs();
/* 3961 */                         _jspx_page_context.setAttribute("camGraph", camGraph, 1);
/*      */                       }
/* 3963 */                       out.write("\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n\n");
/*      */                       
/* 3965 */                       long camStartTime = System.currentTimeMillis();
/*      */                       
/* 3967 */                       String camIDI = (String)request.getAttribute("camid");
/* 3968 */                       String screenIDI = (String)request.getAttribute("screenid");
/* 3969 */                       List screenInfoI = (List)request.getAttribute("screeninfo");
/* 3970 */                       boolean perfAvailResourceScreen = false;
/* 3971 */                       String resourceID = "";
/* 3972 */                       String fromConfPage1 = request.getAttribute("fromConfPage") + "";
/* 3973 */                       String haidI = request.getParameter("haid");
/* 3974 */                       if ((haidI == null) || (haidI.trim().length() == 0)) {
/* 3975 */                         haidI = request.getParameter("haid");
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/* 3980 */                       String isFromResourcePage = (String)request.getAttribute("isfromresourcepage");
/* 3981 */                       if (isFromResourcePage == null) {
/* 3982 */                         isFromResourcePage = "NA";
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/* 3987 */                       if ("true".equals(isFromResourcePage))
/*      */                       {
/*      */ 
/*      */ 
/* 3991 */                         resourceID = (String)request.getAttribute("resourceid");
/* 3992 */                         if ((resourceID == null) || (resourceID.trim().length() == 0)) {
/* 3993 */                           resourceID = request.getParameter("resourceid");
/*      */                         }
/*      */                         
/* 3996 */                         camIDI = resourceID;
/* 3997 */                         perfAvailResourceScreen = true;
/*      */                         
/*      */ 
/* 4000 */                         request.setAttribute("screenInfo", screenInfoI);
/* 4001 */                         List tmpList = CAMDBUtil.getScreens(Integer.parseInt(camIDI));
/* 4002 */                         if (tmpList.size() == 0)
/*      */                         {
/* 4004 */                           CAMDBUtil.createDefaultScreenForResource(Integer.parseInt(camIDI));
/* 4005 */                           tmpList = CAMDBUtil.getScreens(Integer.parseInt(camIDI));
/* 4006 */                           screenInfoI = (List)tmpList.get(0);
/*      */                         }
/*      */                         else {
/* 4009 */                           screenInfoI = (List)tmpList.get(0);
/*      */                         }
/*      */                         
/* 4012 */                         screenIDI = (String)screenInfoI.get(0);
/* 4013 */                         request.setAttribute("screenid", screenIDI);
/*      */                       }
/*      */                       
/*      */ 
/* 4017 */                       Map fromDB = CAMDBUtil.getCustomizedDataForScreenAdminActivity(Long.parseLong(screenIDI));
/* 4018 */                       List screenConfigList = (List)fromDB.get("completedata");
/*      */                       
/*      */ 
/* 4021 */                       List reportsData = (List)fromDB.get("reportsdata");
/*      */                       
/*      */ 
/* 4024 */                       Map dcTimeMap = CAMDBUtil.getLatestCollectionTimes(Long.parseLong(screenIDI));
/* 4025 */                       Map attribResourceMap = CAMDBUtil.getResourceNamesForAttributesInScreen(Integer.parseInt(screenIDI));
/*      */                       
/* 4027 */                       List screenAttribInfo = CAMDBUtil.getScreenAttributeInfo(Long.parseLong(screenIDI), Integer.parseInt((String)screenInfoI.get(3)), dcTimeMap);
/* 4028 */                       boolean scalarAttribsPresent = screenAttribInfo.size() > 0;
/* 4029 */                       List colScreenAttribInfo = CAMDBUtil.getScreenInfoForColumnarData(Long.parseLong(screenIDI));
/* 4030 */                       boolean columnarAttribsPresent = colScreenAttribInfo.size() > 0;
/* 4031 */                       boolean attribsPresent = (scalarAttribsPresent == true) || (columnarAttribsPresent == true);
/* 4032 */                       String quickNote = "This page displays the attributes monitored from various resources as configured during design time. Placing the mouse over the value for table data will display the time when the data was collected. Time intervals will be different if the attributes are from different resources.";
/*      */                       
/*      */ 
/*      */ 
/* 4036 */                       if (request.getAttribute("quicknote") == null) {
/* 4037 */                         request.setAttribute("quicknote", quickNote);
/*      */                       }
/* 4039 */                       String configureLink = "/ShowCAM.do?method=configureScreen&screenid=" + screenIDI + "&camid=" + camIDI + "&haid=" + haidI + "&isfromresourcepage=" + isFromResourcePage;
/* 4040 */                       if ((request.isUserInRole("ENTERPRISEADMIN")) && (com.adventnet.appmanager.util.Constants.isSsoEnabled()))
/*      */                       {
/* 4042 */                         StringBuilder builder = new StringBuilder();
/* 4043 */                         builder.append("https:").append("//");
/* 4044 */                         builder.append(com.adventnet.appmanager.util.Constants.getAppHostName()).append(":");
/* 4045 */                         builder.append(com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.getsslport()).append(configureLink);
/* 4046 */                         configureLink = builder.toString();
/*      */                       }
/* 4048 */                       request.setAttribute("configurelink", configureLink);
/* 4049 */                       String secondLevelLinkTitle; if (!perfAvailResourceScreen)
/*      */                       {
/*      */ 
/* 4052 */                         List sLinks = new ArrayList();
/* 4053 */                         List sText = new ArrayList();
/*      */                         
/* 4055 */                         sLinks.add("Add ScreenXXXX");
/* 4056 */                         sText.add("/ShowCAM.do?method=addScreen&camid=" + camIDI + "&haid=" + haidI);
/*      */                         
/*      */ 
/*      */ 
/* 4060 */                         sLinks.add("Customize");
/* 4061 */                         sText.add(configureLink);
/*      */                         
/*      */ 
/* 4064 */                         sLinks.add("<a href=\"/CAMDeleteScreen.do?method=deleteScreen&screenid=" + screenIDI + "&camid=" + camIDI + "&haid=" + haidI + "\" onclick=\"return deleteScreen();\" class='staticlinks'>Delete Screen</a>");
/* 4065 */                         sText.add("");
/*      */                         
/*      */ 
/*      */ 
/* 4069 */                         List[] secondLevelOfLinks = { sText, sLinks };
/* 4070 */                         secondLevelLinkTitle = "Admin Activity";
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4079 */                       String configureThresholdRedirectLink = "/ShowCAM.do?method=showScreen&haid=" + haidI + "&camid=" + camIDI + "&screenid=" + screenIDI;
/*      */                       
/* 4081 */                       if ("true".equals(isFromResourcePage)) {
/* 4082 */                         configureThresholdRedirectLink = "/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID";
/*      */                       }
/*      */                       
/* 4085 */                       configureThresholdRedirectLink = URLEncoder.encode(configureThresholdRedirectLink);
/*      */                       
/*      */ 
/*      */ 
/* 4089 */                       out.write("\n\n\n\n<script language=\"JavaScript1.2\">\n\nfunction showAttribGraph(attribID,mbean) {\n       var featurelist = \"toolbar=no,status=no,menubar=no,width=450,height=300,scrollbars=yes\";\n       var url = \"/ShowCAM.do?method=showSingleGraphScreen&attributeid=\" + attribID+\"&mbean=\" +mbean;\n       popUp = window.open(url,'popUp',featurelist);\n       return false;\n}\n\n</SCRIPT>\n<!--This script is used to show horizontal bar if customer attributes tables have more number of attributes in SNMP Devices.--> \n<script>\n    jQuery(document).ready(function(){\n        var windowWidth = jQuery(window).width();\n        windowWidth = windowWidth*(81/100);\n        jQuery('.horTableScroll').css({'width':windowWidth});//No I18N\n\n    });\n</script>\n\n<style>\n    .horTableScroll {\n        overflow-x:auto;\n    }    \n</style>\n<!--");
/*      */                       
/* 4091 */                       String camid = request.getParameter("camid");
/*      */                       
/*      */ 
/* 4094 */                       out.write("-->\n\n\n<script>\nfunction validateAndSubmit()\n{\n   if(trimAll(document.AMActionForm.camname.value)==\"\")\n   {\n        alert('");
/* 4095 */                       out.print(FormatUtil.getString("am.webclient.cam.namealert"));
/* 4096 */                       out.write("');\n        return;\n   }\n   document.AMActionForm.submit();\n}\n\n</script>\n\n");
/*      */                       
/* 4098 */                       List list = CAMDBUtil.getCAMDetails(camIDI);
/* 4099 */                       String camName = (String)list.get(0);
/* 4100 */                       String camDescription = (String)list.get(2);
/*      */                       
/* 4102 */                       out.write("\n\n\n\n");
/*      */                       
/* 4104 */                       if ("true".equals(request.getParameter("editPage")))
/*      */                       {
/* 4106 */                         out.write("\n<div id=\"edit\" style=\"display:block\">\n");
/*      */                       } else {
/* 4108 */                         out.write("\n<div id=\"edit\" style=\"display:none\">\n");
/*      */                       }
/* 4110 */                       out.write(10);
/* 4111 */                       out.write(10);
/*      */                       
/* 4113 */                       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 4114 */                       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 4115 */                       _jspx_th_html_005fform_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 4117 */                       _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*      */                       
/* 4119 */                       _jspx_th_html_005fform_005f0.setMethod("get");
/*      */                       
/* 4121 */                       _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 4122 */                       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 4123 */                       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                         for (;;) {
/* 4125 */                           out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n<td height=\"28\"   class=\"tableheading\">");
/* 4126 */                           out.print(FormatUtil.getString("am.webclient.common.configurationdetails.text"));
/* 4127 */                           out.write("\n\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 4128 */                           out.print(request.getParameter("name"));
/* 4129 */                           out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 4130 */                           out.print(request.getParameter("haid"));
/* 4131 */                           out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 4132 */                           out.print(request.getParameter("type"));
/* 4133 */                           out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 4134 */                           out.print(request.getParameter("type"));
/* 4135 */                           out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"configureJMX\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 4136 */                           out.print(request.getParameter("resourceid"));
/* 4137 */                           out.write("\">\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 4138 */                           out.print(request.getParameter("resourcename"));
/* 4139 */                           out.write("\">\n<input type=\"hidden\" name=\"moname\" value=\"");
/* 4140 */                           out.print(request.getParameter("moname"));
/* 4141 */                           out.write("\">\n\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\">\n<span class=\"bodytextboldwhiteun\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n");
/* 4142 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 4143 */                           out.write("</span>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n<tr>\n    <td width=\"20%\" height=\"32\" valign=='top' class=\"bodytext\"> ");
/* 4144 */                           out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 4145 */                           out.write("\n</td>\n    <td width=\"80%\" class=\"bodytext\"> <textarea name=\"camname\" cols=\"38\" rows=\"1\" class=\"formtextarea\">");
/* 4146 */                           out.print(camName);
/* 4147 */                           out.write(" </textarea>\n</td>\n</tr>\n\n<tr>\n    <td valign='top'  class=\"bodytext\"> ");
/* 4148 */                           out.print(FormatUtil.getString("Description"));
/* 4149 */                           out.write("</td>\n    <td  class=\"bodytext\"> <textarea name=\"camdesc\" cols=\"38\" rows=\"3\" class=\"formtextarea\" >");
/* 4150 */                           out.print(camDescription);
/* 4151 */                           out.write("</textarea>\n    </td>\n  </tr>\n</table>\n");
/*      */                           
/* 4153 */                           String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/*      */                           
/* 4155 */                           out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"20%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"80%\" class=\"tablebottom\" >\n<input name=\"addcustomapp\" type=\"button\" class=\"buttons btn_highlt\" \" value=\"");
/* 4156 */                           out.print(FormatUtil.getString("am.webclient.common.startmonitoring.text"));
/* 4157 */                           out.write("\" onClick=\"validateAndSubmit()\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons btn_link\" value=\"");
/* 4158 */                           out.print(cancel);
/* 4159 */                           out.write("\" onClick=\"javascript:toggleDiv('edit')\"/>\n     </td>\n  </tr>\n</table>\n");
/* 4160 */                           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 4161 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4165 */                       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 4166 */                         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                       }
/*      */                       
/* 4169 */                       this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 4170 */                       out.write("\n</div>\n");
/*      */                       
/* 4172 */                       if (!attribsPresent)
/*      */                       {
/*      */ 
/* 4175 */                         out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n    <td colspan=3  height=\"19\" class=\"tableheadingbborder\" >");
/* 4176 */                         out.print(camName);
/* 4177 */                         out.write("\n      ");
/* 4178 */                         if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 4179 */                           out.write(": <span class=\"topdesc\">");
/* 4180 */                           out.print(FormatUtil.getString(camDescription));
/* 4181 */                           out.write(" </span>");
/*      */                         }
/* 4183 */                         out.write("</td>\n     <td  height=\"19\" width=\"20%\" class=\"tableheadingbborder\">\n     ");
/*      */                         
/* 4185 */                         if ((attribsPresent) && (request.getAttribute("showrefreshnowoption") != null))
/*      */                         {
/*      */ 
/* 4188 */                           out.write("\n       <a class=\"bodytextboldwhiteun\" href=\"/deleteMO.do?method=fetchDataNowForResource&resourceid=");
/* 4189 */                           out.print(camIDI);
/* 4190 */                           out.write("&redirectto=");
/* 4191 */                           out.print(URLEncoder.encode("/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID" + fromConfPage1));
/* 4192 */                           out.write(34);
/* 4193 */                           out.write(62);
/* 4194 */                           out.print(FormatUtil.getString("am.webclient.availabilityperf.fetchvalue"));
/* 4195 */                           out.write("</a>\n     ");
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/*      */ 
/* 4201 */                           out.write("\n     &nbsp;\n     ");
/*      */                         }
/*      */                         
/*      */ 
/* 4205 */                         out.write("\n\n     </td>\n</tr>\n\n<tr>\n    <td colspan=4  height=\"29\" ><span class=\"bodytext\">&nbsp;");
/* 4206 */                         out.print(FormatUtil.getString("am.webclient.cam.addcustomattributes.message"));
/*      */                         
/* 4208 */                         PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4209 */                         _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 4210 */                         _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                         
/* 4212 */                         _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 4213 */                         int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 4214 */                         if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                           for (;;) {
/* 4216 */                             out.write(" <a class='staticlinks' href=\"");
/* 4217 */                             out.print(configureLink);
/* 4218 */                             out.write("\">\n      ");
/* 4219 */                             out.print(FormatUtil.getString("am.webclient.cam.addattributes.link"));
/* 4220 */                             out.write("</a>.");
/* 4221 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 4222 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 4226 */                         if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 4227 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                         }
/*      */                         
/* 4230 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4231 */                         out.write("</span></td>\n</tr>\n</table>\n");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/* 4236 */                         if (!scalarAttribsPresent) {
/* 4237 */                           out.write(10);
/* 4238 */                           out.write(10);
/*      */ 
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/*      */ 
/*      */ 
/* 4246 */                           List row = null;
/* 4247 */                           int posOfAttribName = 2;
/* 4248 */                           int posOfDispType = 4;
/* 4249 */                           int posOfAttribValue = 7;
/* 4250 */                           int posOfResourceID = 6;
/* 4251 */                           int posOfAttribID = 0;
/* 4252 */                           int posOfAttribType = 3;
/* 4253 */                           String className = "whitegrayborder";
/* 4254 */                           String currentResourceName = null;
/* 4255 */                           String currentMBeanName = null;
/* 4256 */                           Map infoMap = CAMDBUtil.getMBeanBasedScreenData(Long.parseLong(screenIDI));
/* 4257 */                           Map idVsMBeanAndRes = (HashMap)infoMap.get("attrIdVsMBeanName");
/* 4258 */                           List ordListFromDB = (ArrayList)infoMap.get("attributeidsorderedlist");
/* 4259 */                           List orderedList = new ArrayList(screenAttribInfo.size());
/*      */                           
/*      */ 
/* 4262 */                           out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n    <td colspan=3  height=\"19\" class=\"tableheadingbborder\" >");
/* 4263 */                           out.print(camName);
/* 4264 */                           out.write("\n    ");
/* 4265 */                           if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 4266 */                             out.write("  : <span class=\"topdesc\">");
/* 4267 */                             out.print(FormatUtil.getString(camDescription));
/* 4268 */                             out.write(" </span> ");
/*      */                           }
/* 4270 */                           out.write("</td>\n\t<td width=\"30%\" nowrap class=\"tableheadingbborder\">\n\t");
/* 4271 */                           if ((attribsPresent) && (request.getAttribute("showrefreshnowoption") != null)) {
/* 4272 */                             out.write("\n       <a class=\"bodytextboldwhiteun\" href=\"/deleteMO.do?method=fetchDataNowForResource&resourceid=");
/* 4273 */                             out.print(camIDI);
/* 4274 */                             out.write("&redirectto=");
/* 4275 */                             out.print(URLEncoder.encode("/showresource.do?resourceid=" + camIDI + "&method=showResourceForResourceID" + fromConfPage1));
/* 4276 */                             out.write(34);
/* 4277 */                             out.write(62);
/* 4278 */                             out.print(FormatUtil.getString("am.webclient.availabilityperf.fetchvalue"));
/* 4279 */                             out.write("</a>\n       ");
/*      */                           } else {
/* 4281 */                             out.write("\n       <a class=\"staticlinks\" href=\"javascript:void(0);\" onclick=\"getCustomFields('");
/* 4282 */                             out.print(camIDI);
/* 4283 */                             out.write("','noalarms',false,'CustomFieldValues',false);\">");
/* 4284 */                             out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 4285 */                             out.write("</a>&nbsp;");
/* 4286 */                             out.write("\n       ");
/*      */                           }
/* 4288 */                           out.write("\n       </td>\n\n<tr>\n                <td width=\"36%\" class=\"columnheading\" > ");
/* 4289 */                           out.print(FormatUtil.getString("am.webclient.camscreen.attributename"));
/* 4290 */                           out.write("</td>\n            <td width=\"30%\" class=\"columnheading\" > ");
/* 4291 */                           out.print(FormatUtil.getString("am.webclient.camscreen.value"));
/* 4292 */                           out.write("</td>\n        <td width=\"20%\" class=\"columnheading\" > ");
/* 4293 */                           if ((request.getParameter("type") != null) && (request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 4294 */                             out.write(" &nbsp; ");
/*      */                           } else {
/* 4296 */                             out.write(32);
/* 4297 */                             out.print(FormatUtil.getString("am.webclient.camscreen.datacollectiontime"));
/* 4298 */                             out.write("</td> ");
/*      */                           }
/* 4300 */                           out.write("\n    <td width=\"9%\" class=\"columnheading\" >");
/* 4301 */                           out.print(FormatUtil.getString("am.webclient.camscreen.actions.text"));
/* 4302 */                           out.write("</td>\n</tr>\n");
/*      */                           
/* 4304 */                           Hashtable token = new Hashtable();
/*      */                           int m;
/* 4306 */                           for (int i = 0; i < screenAttribInfo.size(); m++)
/*      */                           {
/* 4308 */                             row = (List)screenAttribInfo.get(i);
/* 4309 */                             if (i % 2 == 0) {
/* 4310 */                               className = "whitegrayborder";
/*      */                             } else {
/* 4312 */                               className = "yellowgrayborder";
/*      */                             }
/*      */                             
/* 4315 */                             boolean newResource = false;
/* 4316 */                             boolean newMBean = false;
/* 4317 */                             boolean addMBeanRow = false;
/* 4318 */                             String date = "Could not be obtained";
/* 4319 */                             String shortDate = "Could not be obtained";
/* 4320 */                             String longFormatDate = "Could not be obtained";
/* 4321 */                             String resourceName4Attrib = "";
/*      */                             try
/*      */                             {
/* 4324 */                               resourceName4Attrib = (String)attribResourceMap.get(row.get(posOfAttribID));
/* 4325 */                               String attribID = (String)row.get(posOfAttribID);
/* 4326 */                               String mBeanName = (String)idVsMBeanAndRes.get(attribID);
/* 4327 */                               if (currentMBeanName == null)
/*      */                               {
/* 4329 */                                 currentMBeanName = mBeanName;
/* 4330 */                                 newMBean = true;
/*      */                               }
/* 4332 */                               else if (!currentMBeanName.equals(mBeanName))
/*      */                               {
/* 4334 */                                 currentMBeanName = mBeanName;
/* 4335 */                                 newMBean = true;
/*      */                               }
/* 4337 */                               if (currentResourceName == null)
/*      */                               {
/* 4339 */                                 currentResourceName = resourceName4Attrib;
/* 4340 */                                 newResource = true;
/*      */                               }
/* 4342 */                               else if (!currentResourceName.equals(resourceName4Attrib))
/*      */                               {
/* 4344 */                                 currentResourceName = resourceName4Attrib;
/* 4345 */                                 newResource = true;
/*      */                               }
/* 4347 */                               addMBeanRow = (newMBean) || (newResource);
/* 4348 */                               date = String.valueOf(Long.parseLong((String)dcTimeMap.get(row.get(posOfAttribID))));
/* 4349 */                               shortDate = formatDT(date);
/* 4350 */                               longFormatDate = new java.util.Date(Long.parseLong(date)).toString();
/*      */                             }
/*      */                             catch (Exception e) {}
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4359 */                             String value = (String)row.get(posOfAttribValue);
/* 4360 */                             if (row.get(posOfAttribType).equals("0"))
/*      */                             {
/* 4362 */                               if (value.equals("-1"))
/*      */                               {
/* 4364 */                                 value = FormatUtil.getString("am.webclient.cam.nodata");
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 4369 */                             out.write(10);
/*      */                             
/* 4371 */                             if (addMBeanRow)
/*      */                             {
/* 4373 */                               if (((String)attribResourceMap.get(row.get(posOfAttribID) + "_RESTYPE")).equals("SNMP"))
/*      */                               {
/*      */ 
/* 4376 */                                 out.write("\n<tr>\n       <td height=\"20\" class=\"secondchildnode\" colspan=\"4\"><span class=\"bodytextbold\"><span class=\"bodytext\">(");
/* 4377 */                                 out.print(currentResourceName);
/* 4378 */                                 out.write(")</span></span></td>\n</tr>\n\n");
/*      */ 
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 4385 */                                 out.write("\n\n<tr>\n<td height=\"20\"   class=\"secondchildnode\"  colspan=\"4\" onmouseover=\"ddrivetip(this,event,'");
/* 4386 */                                 out.print(addBreakAt((currentMBeanName + " - " + currentResourceName).replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"), 80));
/* 4387 */                                 out.write("',null,true,'#000000',300)\" onmouseout=\"hideddrivetip()\" <span class=\"bodytextbold\">");
/* 4388 */                                 out.print(addBreakAt(currentMBeanName, 80));
/* 4389 */                                 out.write(" <span class=\"availablity-arrow\">&raquo;&nbsp;</span> ");
/* 4390 */                                 out.print(getTrimmedText(currentResourceName, 25));
/* 4391 */                                 out.write("</span> </td> ");
/* 4392 */                                 out.write("\n</tr>\n");
/*      */                               }
/*      */                             }
/*      */                             try
/*      */                             {
/* 4397 */                               StringTokenizer mbean = new StringTokenizer(currentResourceName, "_");
/* 4398 */                               int j = 0;
/* 4399 */                               int n; while (mbean.hasMoreTokens()) {
/* 4400 */                                 String t = mbean.nextToken();
/* 4401 */                                 token.put(Integer.valueOf(j), t);
/* 4402 */                                 n++;
/*      */                               }
/*      */                               
/*      */ 
/* 4406 */                               String attrbId = (String)row.get(posOfAttribID);
/* 4407 */                               String resType = (String)attribResourceMap.get(attrbId + "_RESTYPE");
/* 4408 */                               if ((resType != null) && (resType.equalsIgnoreCase("snmp"))) {
/* 4409 */                                 String resourceId = (String)row.get(row.size() - 2);
/* 4410 */                                 if ((resourceId != null) && (resourceId.length() > 0)) {
/* 4411 */                                   List l = com.adventnet.appmanager.util.DBUtil.getRows("SELECT RESOURCENAME FROM AM_ManagedObject where RESOURCEID=" + resourceId);
/* 4412 */                                   if ((l != null) && (l.size() == 1)) {
/* 4413 */                                     j = 0;
/* 4414 */                                     String actualResourceName = (String)((ArrayList)l.get(0)).get(0);
/* 4415 */                                     mbean = new StringTokenizer(actualResourceName, "_");
/* 4416 */                                     while (mbean.hasMoreTokens()) {
/* 4417 */                                       String t = mbean.nextToken();
/* 4418 */                                       token.put(Integer.valueOf(j), t);
/* 4419 */                                       n++;
/*      */                                     }
/*      */                                   }
/*      */                                 }
/*      */                               }
/*      */                             } catch (Exception e) {}
/* 4425 */                             String toshow = getTrimmedText(value, 30);
/* 4426 */                             request.setAttribute("toshow", toshow);
/* 4427 */                             request.setAttribute("fullvalue", value);
/* 4428 */                             int len = value.length();
/* 4429 */                             String tooltiptype = (String)row.get(posOfDispType);
/*      */                             
/* 4431 */                             if (tooltiptype.equals("1")) {
/* 4432 */                               tooltiptype = "Counter";
/* 4433 */                               if ((toshow.equals(" ")) || (value.equals(" ")))
/*      */                               {
/* 4435 */                                 Map fromMap = new HashMap();
/* 4436 */                                 fromMap = (HashMap)com.adventnet.appmanager.cam.CAMServerUtil.collectFirstData;
/* 4437 */                                 if (fromMap != null) {
/* 4438 */                                   List lst = new ArrayList();
/* 4439 */                                   lst = (ArrayList)fromMap.get((String)row.get(posOfAttribID));
/* 4440 */                                   if (lst != null) {
/* 4441 */                                     request.setAttribute("toshow", lst.get(2));
/* 4442 */                                     request.setAttribute("fullvalue", lst.get(3));
/*      */                                   }
/*      */                                 }
/*      */                               }
/*      */                             } else {
/* 4447 */                               tooltiptype = "Non-Counter";
/*      */                             }
/*      */                             
/* 4450 */                             out.write("\n\n<tr>\n\t<td class=\"");
/* 4451 */                             out.print(className);
/* 4452 */                             out.write("\" onmouseover=\"ddrivetip(this,event,'");
/* 4453 */                             out.print(FormatUtil.getString("am.webclient.snmp.tootipMsg", new String[] { (String)row.get(posOfAttribName), resourceName4Attrib, tooltiptype }));
/* 4454 */                             out.write("',null,true,'#000000','len')\" onmouseout=\"hideddrivetip()\"> <span class=\"bodytext\">");
/* 4455 */                             out.print(getTrimmedText((String)row.get(posOfAttribName), 25));
/* 4456 */                             out.write(" </span></td>\n\n");
/*      */                             
/* 4458 */                             if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS"))) {
/* 4459 */                               if (len >= 30)
/*      */                               {
/* 4461 */                                 out.write(10);
/* 4462 */                                 out.write(10);
/* 4463 */                                 String breaktext = addBreakAt(value, 50);
/* 4464 */                                 out.write("\n     <td class=\"");
/* 4465 */                                 out.print(className);
/* 4466 */                                 out.write("\" onmouseover=\"ddrivetip(this,event,'");
/* 4467 */                                 out.print(value.replaceAll("\\n", " "));
/* 4468 */                                 out.write("',null,true,'#000000','len')\" onmouseout=\"hideddrivetip()\" ><span class=\"bodytext\"> ");
/* 4469 */                                 if (_jspx_meth_c_005fout_005f38(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                   return;
/* 4471 */                                 out.write(" </span></td>\n\n");
/*      */                               }
/*      */                               else {
/* 4474 */                                 out.write("\n\n<td class=\"");
/* 4475 */                                 out.print(className);
/* 4476 */                                 out.write("\" ><span class=\"bodytext\"> ");
/* 4477 */                                 if (_jspx_meth_c_005fout_005f39(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                   return;
/* 4479 */                                 out.write(" </span></td>\n\n");
/*      */                               }
/*      */                               
/* 4482 */                               out.write("\n\n        <td class=\"");
/* 4483 */                               out.print(className);
/* 4484 */                               out.write("\" title=\"Time : ");
/* 4485 */                               out.print(longFormatDate);
/* 4486 */                               out.write(" Resource : ");
/* 4487 */                               out.print(resourceName4Attrib);
/* 4488 */                               out.write("\"> <span class=\"bodytext\">");
/* 4489 */                               out.print(shortDate);
/* 4490 */                               out.write("</span></td>\n");
/*      */                             } else {
/* 4492 */                               out.write("\n<td colspan=2 class=\"");
/* 4493 */                               out.print(className);
/* 4494 */                               out.write("\"><span class=\"bodytext\">");
/* 4495 */                               out.print(addBreakAt(value, 55));
/* 4496 */                               out.write("</span></td>\n");
/*      */                             }
/* 4498 */                             out.write("\n        <td class=\"");
/* 4499 */                             out.print(className);
/* 4500 */                             out.write("\" >\n        ");
/* 4501 */                             if ((row.get(posOfAttribType).equals("0")) || (row.get(posOfAttribType).equals("1"))) {
/* 4502 */                               if ((request.getParameter("type") != null) && (!request.getParameter("type").equalsIgnoreCase("SAP-CCMS")))
/*      */                               {
/* 4504 */                                 out.write("\n\n<a  style=\"cursor: pointer;\" onClick=\"javascript:MM_openBrWindow('/jsp/attribute_edit.jsp?resourceid=");
/* 4505 */                                 out.print(row.get(posOfResourceID));
/* 4506 */                                 out.write("&attributeid=");
/* 4507 */                                 out.print(row.get(posOfAttribID));
/* 4508 */                                 out.write("&camid=");
/* 4509 */                                 out.print(camIDI);
/* 4510 */                                 out.write("&haid=");
/* 4511 */                                 out.print(haidI);
/* 4512 */                                 out.write("&screenid=");
/* 4513 */                                 out.print(screenIDI);
/* 4514 */                                 out.write("&resourcename=");
/* 4515 */                                 out.print(currentResourceName);
/* 4516 */                                 out.write("&hostname=");
/* 4517 */                                 out.print(token.get(Integer.valueOf(0)));
/* 4518 */                                 out.write("&resourcetype=");
/* 4519 */                                 out.print(token.get(Integer.valueOf(1)));
/* 4520 */                                 out.write("&portno=");
/* 4521 */                                 out.print(token.get(Integer.valueOf(2)));
/* 4522 */                                 out.write("&attributes=");
/* 4523 */                                 out.print(URLEncoder.encode(currentMBeanName + "|" + (String)row.get(1) + "|" + row.get(posOfAttribType)));
/* 4524 */                                 out.write("&displayname=");
/* 4525 */                                 out.print((String)row.get(posOfAttribName));
/* 4526 */                                 out.write("&isfromeditpage=");
/* 4527 */                                 out.print("true");
/* 4528 */                                 out.write("&resourceid=");
/* 4529 */                                 out.print(row.get(posOfResourceID));
/* 4530 */                                 out.write("&dispType=");
/* 4531 */                                 out.print(row.get(posOfDispType));
/* 4532 */                                 out.write("','Personalize','width=390,height=200,screenX=100,screenY=300,scrollbars=yes')\"><img src=\"/images/icon_edit.gif\"  border=\"0\" title='");
/* 4533 */                                 out.print(FormatUtil.getString("jmxnotification.edit"));
/* 4534 */                                 out.write("'></a>\n");
/*      */                               }
/* 4536 */                               out.write("\n\n    <A class='staticlinks' href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4537 */                               out.print(row.get(posOfResourceID));
/* 4538 */                               out.write("&attributeIDs=");
/* 4539 */                               out.print(row.get(posOfAttribID));
/* 4540 */                               out.write("&attributeToSelect=");
/* 4541 */                               out.print(row.get(posOfAttribID));
/* 4542 */                               out.write("&redirectto=");
/* 4543 */                               out.print(configureThresholdRedirectLink);
/* 4544 */                               out.write("'>\n    <img src=\"/images/icon_associateaction.gif\" title='");
/* 4545 */                               out.print(ALERTCONFIG_TEXT);
/* 4546 */                               out.write("' border=\"0\" ></A>\n\n    ");
/*      */                               
/* 4548 */                               if (row.get(posOfAttribType).equals("0"))
/*      */                               {
/*      */ 
/* 4551 */                                 out.write("\n    <a style=\"cursor: pointer;\" onclick=\"showAttribGraph(");
/* 4552 */                                 out.print(row.get(posOfAttribID));
/* 4553 */                                 out.write(44);
/* 4554 */                                 out.write(39);
/* 4555 */                                 out.print(getTrimmedText(currentMBeanName, 50).replaceAll("\"", "&quot;").replaceAll("'", "\\\\'"));
/* 4556 */                                 out.write("')\" ><img src='/images/icon_linegraph.gif' title='");
/* 4557 */                                 out.print(FormatUtil.getString("jmxnotification.showgraph"));
/* 4558 */                                 out.write("' border='0' ></a>\n\n\n        ");
/*      */                               }
/*      */                               
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/* 4565 */                               out.write("\n\t\t&nbsp;\n\t");
/*      */                             }
/*      */                             
/*      */ 
/* 4569 */                             out.write("\n</td>\n\n</tr>\n");
/*      */                           }
/*      */                           
/*      */ 
/* 4573 */                           out.write("\n</tr>\n\n<tr>\n\t<td colspan=4  height='25' class=\"tablebottom\"><span class=\"bodytext\">");
/*      */                           
/* 4575 */                           PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4576 */                           _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 4577 */                           _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/* 4579 */                           _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 4580 */                           int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 4581 */                           if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                             for (;;) {
/* 4583 */                               out.write("\n       <a href=\"");
/* 4584 */                               out.print(configureLink);
/* 4585 */                               out.write("\" class='staticlinks'>");
/* 4586 */                               out.print(FormatUtil.getString("am.webclient.cam.adddeleteattributes.text"));
/* 4587 */                               out.write("</a> ");
/* 4588 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 4589 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4593 */                           if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 4594 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                           }
/*      */                           
/* 4597 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 4598 */                           out.write("</span></td>\n</tr>\n</table>\n");
/*      */                         }
/*      */                         
/*      */ 
/* 4602 */                         out.write("\n<br>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 4603 */                         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/MyField_div.jsp", out, false);
/* 4604 */                         out.write("</td></tr></table>\n");
/*      */                         
/* 4606 */                         if (columnarAttribsPresent)
/*      */                         {
/* 4608 */                           int k = 0;
/* 4609 */                           String titlePrefix = FormatUtil.getString("am.webclient.common.name.text");
/* 4610 */                           for (int i = 0; i < colScreenAttribInfo.size(); ???++)
/*      */                           {
/* 4612 */                             out.write("\n\t<div class=\"horTableScroll\">\n\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"99%\" class=\"lrtbdarkborder\">\n\t");
/*      */                             
/* 4614 */                             List temp1 = (List)colScreenAttribInfo.get(i);
/* 4615 */                             if (temp1.size() > 0)
/*      */                             {
/* 4617 */                               Properties tmpProp = (Properties)((List)temp1.get(0)).get(0);
/* 4618 */                               String mbeanName = tmpProp.getProperty("mbeanname");
/* 4619 */                               List h = (List)tmpProp.get("columnnames");
/*      */                               
/* 4621 */                               out.write("\n\t\t<tr>\n\t\t<td height=\"24\" width=\"75%\" class=\"tableheadingbborder\" colspan=\"");
/* 4622 */                               out.print(h.size() + 1);
/* 4623 */                               out.write("\">\n\t\t");
/* 4624 */                               out.print(titlePrefix);
/* 4625 */                               out.write(32);
/* 4626 */                               out.write(58);
/* 4627 */                               out.write(32);
/* 4628 */                               out.print(getTrimmedText(mbeanName, 50));
/* 4629 */                               out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                             }
/*      */                             
/* 4632 */                             int cnt = 0; for (int size = temp1.size(); cnt < size; ???++)
/*      */                             {
/*      */ 
/* 4635 */                               out.write("\n\t\t<tr><td width=\"100%\" style=\"vertical-align: top;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t");
/*      */                               
/*      */ 
/* 4638 */                               List oneTableList = (List)temp1.get(cnt);
/* 4639 */                               Properties camprops = (Properties)oneTableList.get(0);
/* 4640 */                               List headers = (List)camprops.get("columnnames");
/* 4641 */                               List thresholdPossibleIDs = (List)camprops.get("thresholdpossibleattrids");
/* 4642 */                               if ("snmp table".equals(camprops.get("TableType"))) {
/* 4643 */                                 titlePrefix = "SNMP Table Name";
/*      */                               } else {
/* 4645 */                                 titlePrefix = FormatUtil.getString("am.webclient.camscreen.titleprefix");
/*      */                               }
/*      */                               
/*      */ 
/* 4649 */                               out.write("\n\t\t\t<tr >\n\t\t     ");
/*      */                               
/* 4651 */                               String attrs = "";
/* 4652 */                               for (int a = 0; a < thresholdPossibleIDs.size(); ???++)
/*      */                               {
/* 4654 */                                 attrs = attrs + (String)thresholdPossibleIDs.get(a) + ",";
/*      */                               }
/*      */                               
/*      */ 
/* 4658 */                               out.write("\n\t\t<td height=\"24\" width=\"75%\" class=\"secondchildnode\" colspan=\"");
/* 4659 */                               out.print(headers.size());
/* 4660 */                               out.write("\">\n\t\t");
/* 4661 */                               String temp = (String)camprops.get("attrName");
/* 4662 */                               out.write("\n\t\t<span class=\"bodytextbold\">");
/* 4663 */                               out.print(FormatUtil.getString("am.webclient.camscreen.attributegraphs.attribute.text"));
/* 4664 */                               out.write(32);
/* 4665 */                               out.write(58);
/* 4666 */                               out.write(32);
/* 4667 */                               out.print(getTrimmedText(temp, 50));
/* 4668 */                               out.write("</span></td>\n\n\t<td class=\"secondchildnode\" width=\"25%\">\n\n\t");
/*      */                               
/* 4670 */                               if (thresholdPossibleIDs.size() > 0)
/*      */                               {
/*      */ 
/*      */ 
/* 4674 */                                 out.write("\n\n\t\t<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4675 */                                 out.print(camprops.get("resourceid"));
/* 4676 */                                 out.write("&attributeIDs=");
/* 4677 */                                 out.print(attrs.substring(0, attrs.length() - 1));
/* 4678 */                                 out.write("&attributeToSelect=");
/* 4679 */                                 out.print(thresholdPossibleIDs.get(0));
/* 4680 */                                 out.write("&redirectto=");
/* 4681 */                                 out.print(configureThresholdRedirectLink);
/* 4682 */                                 out.write("' class=\"staticlinks\">\n        <img src=\"/images/icon_associateaction.gif\" alt=\"Associate Action\" border=\"0\" align=\"absmiddle\" hspace=\"5\" >");
/* 4683 */                                 out.print(ALERTCONFIG_TEXT);
/* 4684 */                                 out.write("</a>\n        ");
/*      */                               }
/*      */                               
/*      */ 
/* 4688 */                               out.write("\n\t\t</td>\n\t\t</tr>\n\t        <tr>\n\t\t");
/*      */                               
/* 4690 */                               for (k = 0; k < headers.size(); k++)
/*      */                               {
/*      */ 
/* 4693 */                                 out.write("\n\t\t\t\t<td class=\"columnheading\" align=left>\n\t\t\t\t");
/* 4694 */                                 out.print(headers.get(k));
/* 4695 */                                 out.write("\n\t\t\t\t</td>\n\t\t\t");
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/* 4700 */                               out.write("\n\t\t<td class=\"columnheading\" width=\"19%\">&nbsp;</td>\n\t        </tr>\n\t        ");
/*      */                               int i;
/* 4702 */                               for (int j = 1; j < oneTableList.size(); i++)
/*      */                               {
/* 4704 */                                 String bgclass = "class=\"whitegrayrightalign-reports\"";
/* 4705 */                                 if (j % 2 != 0)
/*      */                                 {
/* 4707 */                                   bgclass = "class=\"whitegrayrightalign-reports\"";
/*      */                                 }
/*      */                                 
/* 4710 */                                 out.write("\n\t        \t\t<tr>\n\t        \t\t");
/*      */                                 int k;
/* 4712 */                                 for (int l = 0; l < headers.size(); k++)
/*      */                                 {
/*      */ 
/* 4715 */                                   out.write("\n\t\t\t\t\t<td height=\"28\"  ");
/* 4716 */                                   out.print(bgclass);
/* 4717 */                                   out.write(" align=\"left\" title=\"");
/* 4718 */                                   out.print(formatDT((String)camprops.get("dctime")));
/* 4719 */                                   out.write("\">\n\t\t\t\t\t\t<div  style=\"WORD-BREAK:BREAK-ALL; word-wrap: break-word; width:100px; white-space:-moz-pre-wrap; white-space: normal;\">");
/* 4720 */                                   out.print(((List)oneTableList.get(j)).get(l));
/* 4721 */                                   out.write("</div>\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4725 */                                 out.write("\n\n\t\t\t<td ");
/* 4726 */                                 out.print(bgclass);
/* 4727 */                                 out.write(" width=\"19%\">&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t");
/*      */                               }
/*      */                               
/*      */ 
/* 4731 */                               out.write("\n\t</table></td></tr>\n\t");
/*      */                             }
/*      */                             
/*      */ 
/* 4735 */                             out.write("\n<tr>\n        <td colspan=");
/* 4736 */                             out.print(k + 1);
/* 4737 */                             out.write("  height='25' class=\"tablebottom\"><span class=\"bodytext\">");
/*      */                             
/* 4739 */                             PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4740 */                             _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 4741 */                             _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 4743 */                             _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 4744 */                             int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 4745 */                             if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                               for (;;) {
/* 4747 */                                 out.write("\n       <a href=\"");
/* 4748 */                                 out.print(configureLink);
/* 4749 */                                 out.write("\" class='staticlinks'>");
/* 4750 */                                 out.print(FormatUtil.getString("am.webclient.cam.adddeleteattributes.text"));
/* 4751 */                                 out.write("</a> ");
/* 4752 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 4753 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4757 */                             if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 4758 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                             }
/*      */                             
/* 4761 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 4762 */                             out.write("</span></td>\n</tr>\n\n\n</table><br></div>\n");
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                       
/*      */ 
/* 4768 */                       out.write("\n<br><br>\n\n<!-- Added graphs to the Normal Screen -->\n<div id=\"status\" style='Display:none'>");
/* 4769 */                       out.print(FormatUtil.getString("am.webclient.gengraph.text"));
/* 4770 */                       out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n<div id=\"attributegraphs\"></div>\n\n\n\n<script>\nmyOnLoad();\nfunction myOnLoad()\n{\n/**\t");
/* 4771 */                       if ((request.getParameter("type") != null) && (request.getParameter("type").equals("JBOSS-server"))) {
/* 4772 */                         out.write("\n\tmyOnJbossLoad();\n\t");
/*      */                       }
/* 4774 */                       out.write("\n\t**/\n\tattributegraphs();\n}\nvar http1;\nfunction attributegraphs()\n{\n        document.getElementById(\"status\").style.display='block';\n        URL='/jsp/cam_graphs.jsp?camIDI=");
/* 4775 */                       out.print(camIDI);
/* 4776 */                       out.write("&haidI=");
/* 4777 */                       out.print(haidI);
/* 4778 */                       out.write("&screenIDI=");
/* 4779 */                       out.print(screenIDI);
/* 4780 */                       out.write("&isfromresourcepage=");
/* 4781 */                       out.print(isFromResourcePage);
/* 4782 */                       out.write("';\n        http1=getHTTPObject();\n        http1.open(\"GET\", URL, true);\n        //http1.onreadystatechange = new Function('if(http1.readyState == 4 && http1.status == 200){document.getElementById(\"status\").innerHTML =\"&nbsp;\",document.getElementById(\"attributegraphs\").innerHTML = http1.responseText;}' );\n\thttp1.onreadystatechange =handleResponse1;\n        http1.send(null);\n}\n\nfunction handleResponse1()\n{\n        if(http1.readyState == 4)\n        {\n                var result = http1.responseText;\n\t\tdocument.getElementById(\"status\").innerHTML =\"&nbsp;\"\n                document.getElementById(\"attributegraphs\").innerHTML = result;\n                document.getElementById(\"attributegraphs\").style.display='block';\n        //      alert('Div similarmonitor display' + document.getElementById(\"multimonitors\").checked);\n        }\n}\n\n\nfunction subAddCustom(linkS) {\n\tlinkS.href = \"");
/* 4783 */                       out.print(configureLink);
/* 4784 */                       out.write("\";\n\treturn true;\n}\n\n$(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 4785 */                       out.write("\n\t{\n\t\t");
/* 4786 */                       if (_jspx_meth_c_005fif_005f24(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 4788 */                       out.write(10);
/* 4789 */                       out.write(9);
/* 4790 */                       out.write(9);
/* 4791 */                       if (_jspx_meth_c_005fif_005f25(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 4793 */                       out.write("\n\t\tgetCustomFields('");
/* 4794 */                       if (_jspx_meth_c_005fout_005f42(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                         return;
/* 4796 */                       out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 4797 */                       out.write("\n\t}\n\n});\n</script>\n\n\n");
/* 4798 */                       out.write(10);
/* 4799 */                       out.write(10);
/* 4800 */                       out.write(10);
/* 4801 */                       out.write(10);
/* 4802 */                       out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n\n\n\n\n<script>\nfunction deleteMopSelections()\n{\n\tvar sel = false;\n \tfor(i=0;i<document.formab.elements.length;i++)\n \t{\n \t\tif(document.formab.elements[i].type==\"checkbox\")\n \t               {\n \t                        var name = document.formab.elements[i].name;\n \t                        if(name==\"execmopcheckbox\")\n \t                        {\n \t                        \tvar value = document.formab.elements[i].value;\n \t                        \tsel=document.formab.elements[i].checked;\n \t                        \tif(sel)\n \t                        \t{\n \t                        \t\tbreak;\n \t                        \t}\n \t                        }\n \t                 }\n         }\n \tif(!sel)\n \t{\n \t     alert('");
/* 4803 */                       out.print(FormatUtil.getString("am.webclient.viewaction.alertmbeandelete"));
/* 4804 */                       out.write("');\n \t}\n \telse if(confirm('");
/* 4805 */                       out.print(FormatUtil.getString("am.webclient.viewaction.alertdeleteconfirm"));
/* 4806 */                       out.write("'))\n\t{\n\t    document.formab.action=\"/adminAction.do?method=deleteMBeanOperationAction\";\n\t    document.formab.method=\"Post\"\n\t    document.formab.submit();\n\t}\n}\nfunction deleteThreadDump(url,id)\n{\n      \tif(!confirm(\"");
/* 4807 */                       out.print(FormatUtil.getString("am.javaruntime.confirm.delete.text"));
/* 4808 */                       out.write("\"))\n      \t{\n       \t\treturn;//No I18N\n      \t}\n\tvar url =\"/CAMUpdateScreenAttributes.do?method=deletethreadURL&url=\"+url; //NO I18N\n\thttp=getHTTPObject(); //NO I18N\n\thttp.onreadystatechange = handleResponse3;//NO I18N\n\thttp.open(\"GET\",url,true);\n\thttp.send(null); //NO I18N\n}\nfunction getThreadDumpData(rid,tabval)\n{\n\tif(document.getElementById('exturl').style.display=='block')\n\t{\n\t    var showall = document.getElementById('more'); //NO I18N\n\t    showall.innerHTML=\"More...\"; //NO I18N\n\t    toggleDiv('exturl'); //NO I18N\n\t    return;\n\t}\n\tvar date = new Date(); //NO I18N\n\tvar url ='/CAMUpdateScreenAttributes.do?resourceid='+rid+'&method=getThreadDump'; //NO I18N\n\thttp=getHTTPObject(); //NO I18N\n\thttp.onreadystatechange = handleResponse2 //NO I18N\n\thttp.open(\"POST\", url, true); //NO I18N\n\thttp.send(null); //NO I18N\n\n}\nfunction handleResponse2() \n{\n\t if(http.readyState == 4 && http.status == 200)\n\t {\n\t\tvar result = http.responseText;\n\t\tdocument.getElementById('exturl').innerHTML = result; //NO I18N\n\t\tvar showall = document.getElementById('more'); //NO I18N\n");
/* 4809 */                       out.write("\t\tshowall.innerHTML=\"Hide...\"; //NO I18N\n\t\ttoggleDiv('exturl'); //NO I18N\n\t }\n}\nfunction handleResponse3() {\n\tif (http.readyState == 4) {\n\t\tvar result = http.responseText;\n\t\tvar ele = document.getElementById('groupContent');\n\t\tif(ele)\n\t\t{\n\t\t\tele.innerHTML = result;\n\t\t\tconfBodyLoad();\n\t\t}\n\t}\n\t\n}\n</script>\n<form name=\"formab\">\n<input type=\"hidden\" name=\"redirectto\" value=\"");
/* 4810 */                       out.print(java.net.URLDecoder.decode(mopRedirectString));
/* 4811 */                       out.write("\">\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t\t    <tr>\n\t\t\t\t          <td width=\"100%\" height=\"31\" class=\"tableheading\" >");
/* 4812 */                       out.print(FormatUtil.getString("am.webclient.common.mbeanoperations.text"));
/* 4813 */                       out.write(" :</td>\n\t\t\t\t        </tr>\n\t\t\t\t      </table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\n\n            ");
/*      */                       
/* 4815 */                       PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.get(PresentTag.class);
/* 4816 */                       _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 4817 */                       _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 4819 */                       _jspx_th_logic_005fpresent_005f5.setName("executeMopActions");
/* 4820 */                       int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 4821 */                       if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                         for (;;) {
/* 4823 */                           out.write("\n            <tr>\n              <td> <SCRIPT LANGUAGE=\"javascript\" SRC=\"../webclient/common/js/windowFunctions.js\"></SCRIPT>\n              </td>\n            </tr>\n            <tr>\n              <td>\n\n\n \t\t<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">\n\t\t<tr>\n\t\t<td width=\"2%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">\n\t\t<input type=\"checkbox\" name=\"headercheckbox\"  onClick=\"javascript:ToggleAll(this,document.formab,'execmopcheckbox')\">\n\t\t</td>\n\n\t\t<td width=\"12%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 4824 */                           out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 4825 */                           out.write("</td>\n\t\t<td width=\"21%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 4826 */                           out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 4827 */                           out.write("</td>\n\t\t<td width=\"27%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 4828 */                           out.print(FormatUtil.getString("am.reporttab.mbeanname.text"));
/* 4829 */                           out.write("</td>\n\t\t<td width=\"23%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 4830 */                           out.print(FormatUtil.getString("am.webclient.viewaction.operation"));
/* 4831 */                           out.write(" </td>\n\t\t<!--td width=\"23%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 4832 */                           out.print(FormatUtil.getString("am.webclient.viewaction.argscount"));
/* 4833 */                           out.write(" </td-->\n\t\t<td width=\"23%\" height=\"28\" valign=\"center\" class=\"columnheadingnotop\">");
/* 4834 */                           out.print(FormatUtil.getString("am.webclient.viewaction.targetemail.text"));
/* 4835 */                           out.write("</td>\n\t\t<td width=\"11%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">  ");
/* 4836 */                           out.print(FormatUtil.getString("am.webclient.threshold.editview"));
/* 4837 */                           out.write("</td>\n\t\t<td width=\"6%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\"> ");
/* 4838 */                           out.print(FormatUtil.getString("am.webclient.viewaction.execute"));
/* 4839 */                           out.write("</td>\n\t\t<td width=\"6%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\"> ");
/* 4840 */                           out.print(FormatUtil.getString("am.webclient.viewaction.manualexecution"));
/* 4841 */                           out.write("</td>\n\t\t</tr>\n\t\t");
/*      */                           
/* 4843 */                           IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.get(IterateTag.class);
/* 4844 */                           _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 4845 */                           _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fpresent_005f5);
/*      */                           
/* 4847 */                           _jspx_th_logic_005fiterate_005f0.setName("executeMopActions");
/*      */                           
/* 4849 */                           _jspx_th_logic_005fiterate_005f0.setScope("request");
/*      */                           
/* 4851 */                           _jspx_th_logic_005fiterate_005f0.setId("moprow");
/*      */                           
/* 4853 */                           _jspx_th_logic_005fiterate_005f0.setIndexId("mopm");
/* 4854 */                           int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 4855 */                           if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 4856 */                             Object moprow = null;
/* 4857 */                             Integer mopm = null;
/* 4858 */                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 4859 */                               out = _jspx_page_context.pushBody();
/* 4860 */                               _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 4861 */                               _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                             }
/* 4863 */                             moprow = _jspx_page_context.findAttribute("moprow");
/* 4864 */                             mopm = (Integer)_jspx_page_context.findAttribute("mopm");
/*      */                             for (;;) {
/* 4866 */                               out.write(10);
/* 4867 */                               out.write(9);
/* 4868 */                               out.write(9);
/*      */                               
/* 4870 */                               String bgclass = "whitegrayborder";
/* 4871 */                               if (mopm.intValue() % 2 != 0)
/*      */                               {
/* 4873 */                                 bgclass = "yellowgrayborder";
/*      */                               }
/*      */                               
/* 4876 */                               out.write("\n\t\t<tr>\n\n\t\t<td class=\"");
/* 4877 */                               out.print(bgclass);
/* 4878 */                               out.write("\">\n\t\t<input type=\"checkbox\" name=\"execmopcheckbox\" value=\"");
/* 4879 */                               out.print(((ArrayList)moprow).get(0));
/* 4880 */                               out.write("\">\n\t\t</td>\n\t\t<td height=\"22\" class=\"");
/* 4881 */                               out.print(bgclass);
/* 4882 */                               out.write("\"><!--a href=\"#\" class=\"resourcename\" onClick=\"MM_openBrWindow('/showActionProfiles.do?method=getActionDetails&actionid=");
/* 4883 */                               out.print(((ArrayList)moprow).get(0));
/* 4884 */                               out.write("','','resizable=yes,width=450,height=185')\"-->\n\t\t");
/* 4885 */                               out.print(getTrimmedText((String)((ArrayList)moprow).get(1), 25));
/* 4886 */                               out.write("</a>\n\t\t</td>\n\t\t");
/*      */                               
/* 4888 */                               boolean hasArgs = false;
/*      */                               
/* 4890 */                               out.write("\n\t\t\t");
/*      */                               
/* 4892 */                               IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.get(IterateTag.class);
/* 4893 */                               _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 4894 */                               _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                               
/* 4896 */                               _jspx_th_logic_005fiterate_005f1.setName("moprow");
/*      */                               
/* 4898 */                               _jspx_th_logic_005fiterate_005f1.setId("acolumn");
/*      */                               
/* 4900 */                               _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/*      */                               
/* 4902 */                               _jspx_th_logic_005fiterate_005f1.setOffset("2");
/* 4903 */                               int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 4904 */                               if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 4905 */                                 Object acolumn = null;
/* 4906 */                                 Integer i = null;
/* 4907 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 4908 */                                   out = _jspx_page_context.pushBody();
/* 4909 */                                   _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 4910 */                                   _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                 }
/* 4912 */                                 acolumn = _jspx_page_context.findAttribute("acolumn");
/* 4913 */                                 i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                 for (;;) {
/* 4915 */                                   out.write("\n\t\t\t");
/*      */                                   
/* 4917 */                                   if (i.intValue() == 6)
/*      */                                   {
/* 4919 */                                     if (Integer.parseInt((String)acolumn) > 0)
/*      */                                     {
/* 4921 */                                       hasArgs = true;
/*      */                                     }
/*      */                                     
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 4927 */                                     out.write("\n\t\t\t<td height='22' class='");
/* 4928 */                                     out.print(bgclass);
/* 4929 */                                     out.write("' title='");
/* 4930 */                                     out.print((String)acolumn);
/* 4931 */                                     out.write("'>\n\n\t\t\t");
/* 4932 */                                     out.print(getTrimmedText((String)acolumn, 25));
/* 4933 */                                     out.write("\n\t\t\t</td>\n\t\t\t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 4937 */                                   out.write("\n\t\t\t");
/* 4938 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 4939 */                                   acolumn = _jspx_page_context.findAttribute("acolumn");
/* 4940 */                                   i = (Integer)_jspx_page_context.findAttribute("i");
/* 4941 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 4944 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 4945 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 4948 */                               if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 4949 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                               }
/*      */                               
/* 4952 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005foffset_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 4953 */                               out.write("\n\t\t<td class=\"");
/* 4954 */                               out.print(bgclass);
/* 4955 */                               out.write("\"><a href=\"/adminAction.do?method=showMBeanOperationAction&actionID=");
/* 4956 */                               out.print(((ArrayList)moprow).get(0));
/* 4957 */                               out.write("&haid=");
/* 4958 */                               out.print(request.getParameter("haid"));
/* 4959 */                               out.write("&redirectto=");
/* 4960 */                               out.print(mopRedirectString);
/* 4961 */                               out.write("\"><img src=\"/images/icon_edit.gif\"  border=\"0\"></a></td>\n\t\t<td width=\"4%\"height=\"28\" align=\"center\"  class=\"");
/* 4962 */                               out.print(bgclass);
/* 4963 */                               out.write("\">\n\t\t");
/* 4964 */                               if (_jspx_meth_logic_005fpresent_005f6(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                                 return;
/* 4966 */                               out.write(10);
/* 4967 */                               out.write(9);
/* 4968 */                               out.write(9);
/*      */                               
/* 4970 */                               NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4971 */                               _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 4972 */                               _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                               
/* 4974 */                               _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO,ENTERPRISEADMIN");
/* 4975 */                               int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 4976 */                               if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                 for (;;) {
/* 4978 */                                   out.write("\n \t\t\t<a href=\"/GlobalActions.do?method=testAction&actionID=");
/* 4979 */                                   out.print(((ArrayList)moprow).get(0));
/* 4980 */                                   out.write("&haid=");
/* 4981 */                                   out.print(request.getParameter("haid") + "&redirectto=" + mopRedirectString);
/* 4982 */                                   out.write("\"><img src=\"/images/icon_executeaction.gif\"  border=\"0\"></a></td>\n \t\t\t<td class=\"");
/* 4983 */                                   out.print(bgclass);
/* 4984 */                                   out.write("\">\n \t\t\t");
/*      */                                   
/* 4986 */                                   if (hasArgs)
/*      */                                   {
/*      */ 
/* 4989 */                                     out.write("\n \t\t\t<a href=\"javascript:void(0)\" onclick=\"javascript:MM_openBrWindow('/MBeanOperationAction.do?method=executeMBeanOperationActionWithUserIntervention&actionID=");
/* 4990 */                                     out.print(((ArrayList)moprow).get(0));
/* 4991 */                                     out.write("&haid=");
/* 4992 */                                     out.print(request.getParameter("haid"));
/* 4993 */                                     out.write("','','width=580,height=385')\"><img src=\"/images/icon_execute_user.gif\"  border=\"0\" title='");
/* 4994 */                                     out.print(FormatUtil.getString("am.webclient.viewaction.mbeantitle"));
/* 4995 */                                     out.write("'></a>\n \t\t\t");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 5001 */                                     out.write("\n \t\t\t");
/* 5002 */                                     out.print(FormatUtil.getString("am.webclient.viewaction.na"));
/* 5003 */                                     out.write("\n \t\t\t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 5007 */                                   out.write("\n \t\t\t</td>\n\t\t");
/* 5008 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 5009 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 5013 */                               if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 5014 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                               }
/*      */                               
/* 5017 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 5018 */                               out.write("\n\t\t</tr>\n\t\t");
/* 5019 */                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 5020 */                               moprow = _jspx_page_context.findAttribute("moprow");
/* 5021 */                               mopm = (Integer)_jspx_page_context.findAttribute("mopm");
/* 5022 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 5025 */                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 5026 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 5029 */                           if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 5030 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                           }
/*      */                           
/* 5033 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 5034 */                           out.write("\n\t\t</table>\n\t  \t\t\t\t<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"Tablebottom\">\n\t  \t\t\t\t<tr>\n\t  \t\t\t\t<td width=\"72%\" height=\"26\" class=\"bodytext\">\n\t  \t\t\t\t");
/*      */                           
/* 5036 */                           AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 5037 */                           _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 5038 */                           _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fpresent_005f5);
/*      */                           
/* 5040 */                           _jspx_th_am_005fadminlink_005f0.setHref("javascript:deleteMopSelections(this.form);");
/*      */                           
/* 5042 */                           _jspx_th_am_005fadminlink_005f0.setEnableClass("staticlinks");
/* 5043 */                           int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 5044 */                           if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 5045 */                             if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 5046 */                               out = _jspx_page_context.pushBody();
/* 5047 */                               _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 5048 */                               _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 5051 */                               out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 5052 */                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 5053 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 5056 */                             if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 5057 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 5060 */                           if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 5061 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                           }
/*      */                           
/* 5064 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 5065 */                           out.write("\n\t  \t\t\t\t|\n\t  \t\t\t\t");
/*      */                           
/* 5067 */                           AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 5068 */                           _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 5069 */                           _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_logic_005fpresent_005f5);
/*      */                           
/* 5071 */                           _jspx_th_am_005fadminlink_005f1.setHref(linkForMopAction);
/*      */                           
/* 5073 */                           _jspx_th_am_005fadminlink_005f1.setEnableClass("staticlinks");
/* 5074 */                           int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 5075 */                           if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 5076 */                             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 5077 */                               out = _jspx_page_context.pushBody();
/* 5078 */                               _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 5079 */                               _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 5082 */                               out.write("\n\t  \t\t\t\t");
/* 5083 */                               out.print(FormatUtil.getString("am.webclient.threshold.addnew"));
/* 5084 */                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 5085 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 5088 */                             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 5089 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 5092 */                           if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 5093 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                           }
/*      */                           
/* 5096 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 5097 */                           out.write("</td>\n\t  \t\t\t\t</tr>\n\t  \t\t\t\t</table>\n\n              </td>\n            </tr>\n            ");
/* 5098 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 5099 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5103 */                       if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 5104 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                       }
/*      */                       
/* 5107 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f5);
/* 5108 */                       out.write("\n            ");
/*      */                       
/* 5110 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.get(NotPresentTag.class);
/* 5111 */                       _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 5112 */                       _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 5114 */                       _jspx_th_logic_005fnotPresent_005f1.setName("executeMopActions");
/* 5115 */                       int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 5116 */                       if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                         for (;;) {
/* 5118 */                           out.write("\n            <tr>\n            <td>\n           \t\t<table>\n           \t\t<tr>\n\n<td class=\"bodytext\" height=\"29\" valign=\"center\">&nbsp;");
/* 5119 */                           out.print(FormatUtil.getString("am.webclient.viewaction.noactionscreated"));
/* 5120 */                           out.write(32);
/*      */                           
/* 5122 */                           IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5123 */                           _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/* 5124 */                           _jspx_th_c_005fif_005f26.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                           
/* 5126 */                           _jspx_th_c_005fif_005f26.setTest("${!empty ADMIN}");
/* 5127 */                           int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/* 5128 */                           if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */                             for (;;) {
/* 5130 */                               out.write(10);
/* 5131 */                               out.write(32);
/* 5132 */                               out.write(32);
/* 5133 */                               out.print(FormatUtil.getString("am.webclient.viewaction.clickto"));
/* 5134 */                               out.write(" <a href=\"");
/* 5135 */                               out.print(linkForMopAction);
/* 5136 */                               out.write("\" class=\"resourcename\">\n  ");
/* 5137 */                               out.print(FormatUtil.getString("am.webclient.threshold.creatembean"));
/* 5138 */                               out.write("</a>");
/* 5139 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/* 5140 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 5144 */                           if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/* 5145 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26); return;
/*      */                           }
/*      */                           
/* 5148 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 5149 */                           out.write("</td>\n           \t\t</tr>\n               \t</table>\n           </td>\n           </tr>\n           ");
/* 5150 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 5151 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5155 */                       if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 5156 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                       }
/*      */                       
/* 5159 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 5160 */                       out.write("\n          \t</table>\n\n");
/*      */                       
/* 5162 */                       HashMap threadProps11 = (HashMap)request.getAttribute("threadProps");
/*      */                       try {
/* 5164 */                         if (threadProps11 != null) {
/* 5165 */                           ArrayList threaddumphistory = (ArrayList)threadProps11.get("threadurls");
/* 5166 */                           int rowCount = ((Integer)threadProps11.get("ROW_COUNT")).intValue();
/* 5167 */                           String resourceid11 = "" + request.getParameter("resourceid");
/*      */                           
/* 5169 */                           out.write(10);
/*      */                           
/* 5171 */                           PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5172 */                           _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 5173 */                           _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/* 5175 */                           _jspx_th_logic_005fpresent_005f7.setRole("ADMIN");
/* 5176 */                           int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 5177 */                           if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                             for (;;) {
/* 5179 */                               out.write("\n <br>\n     <input class=\"buttons\" value=\"");
/* 5180 */                               out.print(FormatUtil.getString("am.webclient.jdk15.threadinfo.text"));
/* 5181 */                               out.write("\" type=\"button\" onClick=\"javascript:MM_openBrWindow('/JavaRuntime.do?method=getThreadInfo&resourceid=");
/* 5182 */                               out.print(resourceid11);
/* 5183 */                               out.write("','ThreadInfo','scrollbars=yes,resizable=yes')\"> \n <br>\n");
/* 5184 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 5185 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 5189 */                           if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 5190 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                           }
/*      */                           
/* 5193 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 5194 */                           out.write("\n\n<br>\n<table width=\"99%\"   border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n<td colspan=\"2\" height=\"31\" class=\"tableheading\">");
/* 5195 */                           out.print(FormatUtil.getString("am.javaruntime.threaddumphistory"));
/* 5196 */                           out.write("</td>\n</tr> \n");
/*      */                           
/* 5198 */                           if (threaddumphistory.size() > 0)
/*      */                           {
/*      */ 
/* 5201 */                             out.write("\n\t<tr>\n\t<td width=\"80%\" class=\"columnheadingb\">");
/* 5202 */                             out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 5203 */                             out.write(32);
/* 5204 */                             out.write(38);
/* 5205 */                             out.write(32);
/* 5206 */                             out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/* 5207 */                             out.write("</td>\n");
/* 5208 */                             if ((request.isUserInRole("ADMIN")) && (!EnterpriseUtil.isAdminServer()))
/*      */                             {
/*      */ 
/* 5211 */                               out.write("\n\t<td width=\"20%%\" class=\"columnheadingb\">");
/* 5212 */                               out.print(FormatUtil.getString("am.webclient.rbm.delete.text"));
/* 5213 */                               out.write("</td>\n");
/*      */                             }
/*      */                             
/*      */ 
/* 5217 */                             out.write("\n</tr>\n\t\n");
/*      */                             
/* 5219 */                             boolean extra = false;
/* 5220 */                             for (int k = 0; k < threaddumphistory.size(); ???++)
/*      */                             {
/* 5222 */                               Properties url = (Properties)threaddumphistory.get(k);
/*      */                               
/* 5224 */                               out.write("\n\t<tr>\n\t<td style=\"padding-left:26px\" class=\"whitegrayborderbr\" title=\"");
/* 5225 */                               out.print(url.getProperty("URL"));
/* 5226 */                               out.write("\">\n\t<a class=\"staticlinks-blue\" href=\"javascript:void(0);\" onclick=\"javascript:MM_openBrWindow('");
/* 5227 */                               out.print(url.getProperty("URL"));
/* 5228 */                               out.write("','ThreadInfo','scrollbars=yes,resizable=yes')\">");
/* 5229 */                               out.print(url.getProperty("DSPNAME"));
/* 5230 */                               out.write("</a>\n\t</td>\n");
/* 5231 */                               if ((request.isUserInRole("ADMIN")) && (!EnterpriseUtil.isAdminServer()))
/*      */                               {
/*      */ 
/* 5234 */                                 out.write("\t\n\t<td class=\"whitegrayborderbr\">\n\t<a title=\"Delete Thread Dump File\"  class=\"staticlinks\" href=\"javascript:void(0);\" onclick=\"javascript:deleteThreadDump('");
/* 5235 */                                 out.print(url.getProperty("ABSURL"));
/* 5236 */                                 out.write(39);
/* 5237 */                                 out.write(44);
/* 5238 */                                 out.write(39);
/* 5239 */                                 out.print(resourceid11);
/* 5240 */                                 out.write("');return false;\">\n\t<img hspace=\"5\" border=\"0\" src=\"/images/deleteWidget.gif\"/>\n\t</a>\n\t</td>\n");
/*      */                               }
/*      */                               
/*      */ 
/* 5244 */                               out.write("\n\t</tr>\n");
/*      */                             }
/*      */                             
/*      */ 
/* 5248 */                             out.write("\t \n\t<tr>\n\t<td colspan=\"2\">\n\t<div id=\"exturl\" style=\"display:none\">\n\n\t</div>\n\t</td>\n\t</tr>\n");
/*      */                             
/* 5250 */                             if (rowCount > 5)
/*      */                             {
/*      */ 
/* 5253 */                               out.write("\n\t<tr>\n\t<td class=\"columnheadingb\" colspan=\"2\" align=\"right\"><a class=\"bodytext-nounderline\" href=\"javascript:void(0)\" id=\"more\" onclick=\"javascript:getThreadDumpData('");
/* 5254 */                               out.print(resourceid11);
/* 5255 */                               out.write("');\" >More...</a></td>");
/* 5256 */                               out.write("\n\t</tr>\n");
/*      */                             }
/*      */                             
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/* 5263 */                             out.write("\n\t<tr>\n\t<td  colspan=\"2\" class=\"whitegrayborderbr\" align=\"center\">");
/* 5264 */                             out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 5265 */                             out.write("</td>\n\t</tr>\n");
/*      */                           }
/*      */                           
/*      */ 
/* 5269 */                           out.write("\n</table>\n");
/*      */                         }
/*      */                       } catch (Exception ex) {
/* 5272 */                         ex.printStackTrace();
/*      */                       }
/* 5274 */                       out.write("\n</form>\n");
/* 5275 */                       out.write("\n</div>\n");
/* 5276 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 5277 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 5281 */                   if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 5282 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                   }
/*      */                   
/* 5285 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5286 */                   out.write(10);
/* 5287 */                   if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*      */                     return;
/* 5289 */                   out.write(10);
/* 5290 */                   out.write(10);
/* 5291 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 5292 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 5296 */               if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 5297 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */               }
/*      */               
/* 5300 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5301 */               out.write("\n\n<br>\n\t");
/* 5302 */               out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 5303 */               DialChartSupport dialGraph = null;
/* 5304 */               dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 5305 */               if (dialGraph == null) {
/* 5306 */                 dialGraph = new DialChartSupport();
/* 5307 */                 _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */               }
/* 5309 */               out.write(10);
/*      */               
/*      */               try
/*      */               {
/* 5313 */                 String hostos = (String)systeminfo.get("HOSTOS");
/* 5314 */                 String hostname = (String)systeminfo.get("HOSTNAME");
/* 5315 */                 String hostid = (String)systeminfo.get("host_resid");
/* 5316 */                 boolean isConf = false;
/* 5317 */                 if ((systeminfo.get("isConf") != null) && (((String)systeminfo.get("isConf")).equals("true"))) {
/* 5318 */                   isConf = true;
/*      */                 }
/* 5320 */                 com.adventnet.appmanager.db.RepairTables rt = new com.adventnet.appmanager.db.RepairTables();
/* 5321 */                 Properties property = new Properties();
/* 5322 */                 if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")) && (!hostos.equalsIgnoreCase("node")))
/*      */                 {
/* 5324 */                   property = com.adventnet.appmanager.db.RepairTables.getValuesForHost(hostname, hostos);
/* 5325 */                   if ((property != null) && (property.size() > 0))
/*      */                   {
/* 5327 */                     String cpuid = property.getProperty("cpuid");
/* 5328 */                     String memid = property.getProperty("memid");
/* 5329 */                     String diskid = property.getProperty("diskid");
/* 5330 */                     String cpuvalue = property.getProperty("CPU Utilization");
/* 5331 */                     String memvalue = property.getProperty("Memory Utilization");
/* 5332 */                     String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + memid + "&period=0')";
/* 5333 */                     String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + cpuid + "&period=0')";
/* 5334 */                     String diskvalue = property.getProperty("Disk Utilization");
/* 5335 */                     String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + diskid + "&period=0')";
/*      */                     
/* 5337 */                     if (!isConf) {
/* 5338 */                       out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 5339 */                       out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 5340 */                       out.write(45);
/* 5341 */                       if (systeminfo.get("host_resid") != null) {
/* 5342 */                         out.write("<a href=\"showresource.do?resourceid=");
/* 5343 */                         out.print(hostid);
/* 5344 */                         out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 5345 */                         out.print(hostname);
/* 5346 */                         out.write("</a>");
/* 5347 */                       } else { out.println(hostname); }
/* 5348 */                       out.write("</td>\t");
/* 5349 */                       out.write("\n  </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"30%\" valign=\"top\">\n    ");
/* 5350 */                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5351 */                       out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">\n\n        <tr>\n         ");
/*      */                       
/*      */ 
/* 5354 */                       if (cpuvalue != null)
/*      */                       {
/*      */ 
/* 5357 */                         dialGraph.setValue(Long.parseLong(cpuvalue));
/* 5358 */                         out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5359 */                         out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5360 */                         out.write(45);
/* 5361 */                         out.print(cpuvalue);
/* 5362 */                         out.write(" %'>\n\n");
/*      */                         
/* 5364 */                         DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5365 */                         _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 5366 */                         _jspx_th_awolf_005fdialchart_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                         
/* 5368 */                         _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                         
/* 5370 */                         _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                         
/* 5372 */                         _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                         
/* 5374 */                         _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                         
/* 5376 */                         _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                         
/* 5378 */                         _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                         
/* 5380 */                         _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                         
/* 5382 */                         _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                         
/* 5384 */                         _jspx_th_awolf_005fdialchart_005f0.setResourceId(hostid);
/*      */                         
/* 5386 */                         _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 5387 */                         int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 5388 */                         if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 5389 */                           if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 5390 */                             out = _jspx_page_context.pushBody();
/* 5391 */                             _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 5392 */                             _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 5395 */                             out.write(10);
/* 5396 */                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 5397 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 5400 */                           if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 5401 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 5404 */                         if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 5405 */                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                         }
/*      */                         
/* 5408 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 5409 */                         out.write("\n         </td>\n            ");
/*      */                       }
/*      */                       else
/*      */                       {
/* 5413 */                         out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5414 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5415 */                         out.write(32);
/* 5416 */                         out.write(62);
/* 5417 */                         out.write(10);
/* 5418 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5419 */                         out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n\n  ");
/*      */                       }
/* 5421 */                       out.write("\n      </tr>\n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 5422 */                       if (cpuvalue != null)
/*      */                       {
/* 5424 */                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5425 */                         out.print(hostid);
/* 5426 */                         out.write("&attributeid=");
/* 5427 */                         out.print(cpuid);
/* 5428 */                         out.write("&period=-7')\" class='bodytextbold'>");
/* 5429 */                         out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5430 */                         out.write(32);
/* 5431 */                         out.write(45);
/* 5432 */                         out.write(32);
/* 5433 */                         out.print(cpuvalue);
/* 5434 */                         out.write("</a> %\n");
/*      */                       }
/* 5436 */                       out.write("\n  </td>\n       </tr>\n       </table>");
/* 5437 */                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5438 */                       out.write("</td>\n      <td width=\"30%\"> ");
/* 5439 */                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5440 */                       out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/*      */                       
/* 5442 */                       if (memvalue != null)
/*      */                       {
/*      */ 
/* 5445 */                         dialGraph.setValue(Long.parseLong(memvalue));
/* 5446 */                         out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5447 */                         out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5448 */                         out.write(45);
/* 5449 */                         out.print(memvalue);
/* 5450 */                         out.write(" %' >\n\n");
/*      */                         
/* 5452 */                         DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5453 */                         _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 5454 */                         _jspx_th_awolf_005fdialchart_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                         
/* 5456 */                         _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                         
/* 5458 */                         _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                         
/* 5460 */                         _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                         
/* 5462 */                         _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                         
/* 5464 */                         _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                         
/* 5466 */                         _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                         
/* 5468 */                         _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                         
/* 5470 */                         _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                         
/* 5472 */                         _jspx_th_awolf_005fdialchart_005f1.setResourceId(hostid);
/*      */                         
/* 5474 */                         _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 5475 */                         int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 5476 */                         if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 5477 */                           if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 5478 */                             out = _jspx_page_context.pushBody();
/* 5479 */                             _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 5480 */                             _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 5483 */                             out.write(32);
/* 5484 */                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 5485 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 5488 */                           if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 5489 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 5492 */                         if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 5493 */                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                         }
/*      */                         
/* 5496 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 5497 */                         out.write(32);
/* 5498 */                         out.write("\n            </td>\n            ");
/*      */                       }
/*      */                       else
/*      */                       {
/* 5502 */                         out.write("\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5503 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5504 */                         out.write(" >\n\n");
/* 5505 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5506 */                         out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                       }
/* 5508 */                       out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 5509 */                       if (memvalue != null)
/*      */                       {
/* 5511 */                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5512 */                         out.print(hostid);
/* 5513 */                         out.write("&attributeid=");
/* 5514 */                         out.print(memid);
/* 5515 */                         out.write("&period=-7')\" class='bodytextbold'>");
/* 5516 */                         out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5517 */                         out.write(45);
/* 5518 */                         out.print(memvalue);
/* 5519 */                         out.write("</a> %\n  ");
/*      */                       }
/* 5521 */                       out.write("\n  </td>\n       </tr>\n    </table>");
/* 5522 */                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5523 */                       out.write("</td>\n      <td width=\"30%\">");
/* 5524 */                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5525 */                       out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                       
/*      */ 
/* 5528 */                       if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                       {
/*      */ 
/*      */ 
/* 5532 */                         dialGraph.setValue(Long.parseLong(diskvalue));
/* 5533 */                         out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5534 */                         out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 5535 */                         out.write(45);
/* 5536 */                         out.print(diskvalue);
/* 5537 */                         out.write("%' >\n");
/*      */                         
/* 5539 */                         DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5540 */                         _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 5541 */                         _jspx_th_awolf_005fdialchart_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                         
/* 5543 */                         _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                         
/* 5545 */                         _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                         
/* 5547 */                         _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                         
/* 5549 */                         _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                         
/* 5551 */                         _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                         
/* 5553 */                         _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                         
/* 5555 */                         _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                         
/* 5557 */                         _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                         
/* 5559 */                         _jspx_th_awolf_005fdialchart_005f2.setResourceId(hostid);
/*      */                         
/* 5561 */                         _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 5562 */                         int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 5563 */                         if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 5564 */                           if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5565 */                             out = _jspx_page_context.pushBody();
/* 5566 */                             _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 5567 */                             _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 5570 */                             out.write(32);
/* 5571 */                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 5572 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 5575 */                           if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5576 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 5579 */                         if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 5580 */                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                         }
/*      */                         
/* 5583 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 5584 */                         out.write(32);
/* 5585 */                         out.write(32);
/* 5586 */                         out.write("\n    </td>\n            ");
/*      */                       }
/*      */                       else
/*      */                       {
/* 5590 */                         out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5591 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5592 */                         out.write(32);
/* 5593 */                         out.write(62);
/* 5594 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5595 */                         out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                       }
/* 5597 */                       out.write("\n  </tr>\n  <tr>\n\n\n\n  <td align='center'  class='bodytextbold'>\n");
/* 5598 */                       if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                       {
/* 5600 */                         out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5601 */                         out.print(hostid);
/* 5602 */                         out.write("&attributeid=");
/* 5603 */                         out.print(diskid);
/* 5604 */                         out.write("&period=-7')\" class='bodytextbold'>");
/* 5605 */                         out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 5606 */                         out.write(45);
/* 5607 */                         out.print(diskvalue);
/* 5608 */                         out.write("</a> %\n     ");
/*      */                       }
/* 5610 */                       out.write("\n  </td>\n  </tr>\n</table>");
/* 5611 */                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5612 */                       out.write("</td></tr></table>\n\n");
/*      */                     } else {
/* 5614 */                       out.write("\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\" width=\"100%\" onMouseOver=\"ShowPicture('configureIcons_ifany',1,'hostresource')\" onMouseOut=\"ShowPicture('configureIcons_ifany',0,'hostresource')\">\n\t<tr><td class=\"conf-mon-heading\" align=\"left\" colspan=\"3\">");
/* 5615 */                       out.print(FormatUtil.getString("am.webclient.serversnapshot.allCaps.heading"));
/* 5616 */                       out.write("&nbsp;-&nbsp;<a href=\"showresource.do?resourceid=");
/* 5617 */                       out.print(systeminfo.get("host_resid"));
/* 5618 */                       out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 5619 */                       out.print(hostname);
/* 5620 */                       out.write("</a></td></tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"><div id=\"configureIcons_ifany\"></div></td></tr>\n\t<tr>\n");
/* 5621 */                       if (cpuvalue != null)
/*      */                       {
/*      */ 
/* 5624 */                         dialGraph.setValue(Long.parseLong(cpuvalue));
/* 5625 */                         out.write("\n         <td align=\"center\" valign=\"center\">\n\t\t\t");
/*      */                         
/* 5627 */                         DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5628 */                         _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 5629 */                         _jspx_th_awolf_005fdialchart_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                         
/* 5631 */                         _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                         
/* 5633 */                         _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                         
/* 5635 */                         _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                         
/* 5637 */                         _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                         
/* 5639 */                         _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                         
/* 5641 */                         _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                         
/* 5643 */                         _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                         
/* 5645 */                         _jspx_th_awolf_005fdialchart_005f3.setLink(cpuurl);
/*      */                         
/* 5647 */                         _jspx_th_awolf_005fdialchart_005f3.setResourceId(hostid);
/*      */                         
/* 5649 */                         _jspx_th_awolf_005fdialchart_005f3.setAttributeId(cpuid);
/* 5650 */                         int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 5651 */                         if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 5652 */                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                         }
/*      */                         
/* 5655 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 5656 */                         out.write("\n         </td>\n     ");
/*      */                       }
/*      */                       else {
/* 5659 */                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5660 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5661 */                         out.write(39);
/* 5662 */                         out.write(32);
/* 5663 */                         out.write(62);
/* 5664 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5665 */                         out.write("\n \t\t</td>\n\t\t");
/*      */                       }
/* 5667 */                       if (memvalue != null) {
/* 5668 */                         dialGraph.setValue(Long.parseLong(memvalue));
/* 5669 */                         out.write("\n            <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                         
/* 5671 */                         DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5672 */                         _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 5673 */                         _jspx_th_awolf_005fdialchart_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                         
/* 5675 */                         _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                         
/* 5677 */                         _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                         
/* 5679 */                         _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                         
/* 5681 */                         _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                         
/* 5683 */                         _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                         
/* 5685 */                         _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                         
/* 5687 */                         _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                         
/* 5689 */                         _jspx_th_awolf_005fdialchart_005f4.setLink(memurl);
/*      */                         
/* 5691 */                         _jspx_th_awolf_005fdialchart_005f4.setResourceId(hostid);
/*      */                         
/* 5693 */                         _jspx_th_awolf_005fdialchart_005f4.setAttributeId(memid);
/* 5694 */                         int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 5695 */                         if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 5696 */                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                         }
/*      */                         
/* 5699 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 5700 */                         out.write("\n            </td>\n         ");
/*      */                       }
/*      */                       else {
/* 5703 */                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5704 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5705 */                         out.write(39);
/* 5706 */                         out.write(32);
/* 5707 */                         out.write(62);
/* 5708 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5709 */                         out.write("\n \t\t</td>\n\t\t");
/*      */                       }
/* 5711 */                       if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 5712 */                         dialGraph.setValue(Long.parseLong(diskvalue));
/* 5713 */                         out.write("\n             <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                         
/* 5715 */                         DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5716 */                         _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 5717 */                         _jspx_th_awolf_005fdialchart_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                         
/* 5719 */                         _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                         
/* 5721 */                         _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                         
/* 5723 */                         _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                         
/* 5725 */                         _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                         
/* 5727 */                         _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                         
/* 5729 */                         _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                         
/* 5731 */                         _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                         
/* 5733 */                         _jspx_th_awolf_005fdialchart_005f5.setLink(diskurl);
/*      */                         
/* 5735 */                         _jspx_th_awolf_005fdialchart_005f5.setResourceId(hostid);
/*      */                         
/* 5737 */                         _jspx_th_awolf_005fdialchart_005f5.setAttributeId(diskid);
/* 5738 */                         int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 5739 */                         if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 5740 */                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                         }
/*      */                         
/* 5743 */                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 5744 */                         out.write(32);
/* 5745 */                         out.write("\n\t          </td>\n\t  ");
/*      */                       }
/*      */                       else {
/* 5748 */                         out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5749 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5750 */                         out.write(39);
/* 5751 */                         out.write(32);
/* 5752 */                         out.write(62);
/* 5753 */                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5754 */                         out.write("\n \t\t</td>\n\t\t");
/*      */                       }
/* 5756 */                       out.write("\n         \t</tr>\n\t<tr id=\"showLinks_hostresource\">\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5757 */                       out.print(hostid);
/* 5758 */                       out.write("&attributeid=");
/* 5759 */                       out.print(cpuid);
/* 5760 */                       out.write("&period=-7')\" class='tooltip'>");
/* 5761 */                       out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5762 */                       out.write(32);
/* 5763 */                       out.write(45);
/* 5764 */                       out.write(32);
/* 5765 */                       if (cpuvalue != null) {
/* 5766 */                         out.print(cpuvalue);
/*      */                       }
/* 5768 */                       out.write(" %</a>\n\t\t</span>\n\t\t</td>\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5769 */                       out.print(hostid);
/* 5770 */                       out.write("&attributeid=");
/* 5771 */                       out.print(memid);
/* 5772 */                       out.write("&period=-7')\" class='tooltip'>");
/* 5773 */                       out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5774 */                       out.write(45);
/* 5775 */                       if (memvalue != null) {
/* 5776 */                         out.print(memvalue);
/*      */                       }
/* 5778 */                       out.write(" %</a>\n  \t\t</span>\n\t\t</td>\n\t\t<td align=\"center\">\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5779 */                       out.print(hostid);
/* 5780 */                       out.write("&attributeid=");
/* 5781 */                       out.print(diskid);
/* 5782 */                       out.write("&period=-7')\" class='tooltip'>");
/* 5783 */                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 5784 */                       out.write(45);
/* 5785 */                       if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 5786 */                         out.print(diskvalue);
/*      */                       }
/* 5788 */                       out.write(" %</a>\n     \t</span>\n\t\t</td>\n\t</tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n</table>\n         \t\n");
/*      */                     }
/* 5790 */                     out.write(10);
/* 5791 */                     out.write(10);
/*      */                   }
/*      */                   
/*      */                 }
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/* 5798 */                 e.printStackTrace();
/*      */               }
/* 5800 */               out.write(10);
/* 5801 */               out.write(10);
/* 5802 */               out.write(10);
/* 5803 */               out.write(10);
/* 5804 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 5805 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 5808 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 5809 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 5812 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 5813 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 5816 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 5817 */           out.write(10);
/* 5818 */           out.write(32);
/* 5819 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 5821 */           out.write(10);
/*      */           
/* 5823 */           PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 5824 */           _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 5825 */           _jspx_th_tiles_005fput_005f5.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 5827 */           _jspx_th_tiles_005fput_005f5.setName("ServerLeftArea");
/*      */           
/* 5829 */           _jspx_th_tiles_005fput_005f5.setType("string");
/* 5830 */           int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 5831 */           if (_jspx_eval_tiles_005fput_005f5 != 0) {
/* 5832 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 5833 */               out = _jspx_page_context.pushBody();
/* 5834 */               _jspx_th_tiles_005fput_005f5.setBodyContent((BodyContent)out);
/* 5835 */               _jspx_th_tiles_005fput_005f5.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 5838 */               out.write(10);
/* 5839 */               out.write("<!--$Id$-->\n\n\n<script>\n");
/*      */               
/* 5841 */               if (request.getParameter("editPage") != null)
/*      */               {
/* 5843 */                 out.write("\n   reconfigure();\n");
/*      */               }
/*      */               
/* 5846 */               out.write("\n\nfunction reconfigure()\n{\n\ttoggleDiv('Reconfigure');\n\t//hideDiv('snapshot');\n}\n</script>\n");
/*      */               
/* 5848 */               String resourceid = request.getParameter("resourceid");
/* 5849 */               String configure_link = (String)request.getAttribute("configurelink");
/*      */               
/* 5851 */               out.write("\n\n<script language=\"JavaScript\">\n\tfunction confirmDelete()\n \t {\n        var s = confirm(\"");
/* 5852 */               out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 5853 */               out.write("\");\n        if (s)\n        document.location.href=\"/deleteMO.do?method=deleteMO&type=JBOSS-server&select=");
/* 5854 */               out.print(resourceid);
/* 5855 */               out.write("\";\n\t }\n\t    function confirmManage()\n \t {\n  var s = confirm(\"");
/* 5856 */               out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 5857 */               out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 5858 */               if (_jspx_meth_c_005fout_005f45(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 5860 */               out.write("\";\n\t }\n\n         function confirmUnManage()\n \t {\n        \t ");
/* 5861 */               if (_jspx_meth_logic_005fpresent_005f8(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 5863 */               out.write("\n\t\t  var show_msg=\"false\";\n\t      var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 5864 */               out.print(request.getParameter("resourceid"));
/* 5865 */               out.write("; //No i18n\n\t      $.ajax({\n\t        type:'POST', //No i18n\n\t        url:url,\n\t        async:false,\n\t        success: function(data)\n\t        {\n\t          show_msg=data\n\t        }\n\t      });\n\t      if(show_msg.indexOf(\"true\")>-1)\n\t      {\n\t          alert(\"");
/* 5866 */               out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 5867 */               out.write("\");\n\t      \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 5868 */               if (_jspx_meth_c_005fout_005f46(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 5870 */               out.write("\";\n         }\n       else { \n\t\t    var s = confirm(\"");
/* 5871 */               out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 5872 */               out.write("\");\n    \t\tif (s){\n   \t\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 5873 */               if (_jspx_meth_c_005fout_005f47(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 5875 */               out.write("\"; //No I18N\n\t\t\t  }\n\t     } \n\t }\n</script>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td>\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"   class=\"leftmnutables\">\n\n  <tr>\n    <td class=\"leftlinksheading\">");
/* 5876 */               out.print(FormatUtil.getString("am.webclient.jboss.quicklinks.text"));
/* 5877 */               out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\" >\n  ");
/*      */               
/* 5879 */               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5880 */               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 5881 */               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f5);
/* 5882 */               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 5883 */               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                 for (;;) {
/* 5885 */                   out.write(10);
/* 5886 */                   out.write(32);
/* 5887 */                   out.write(32);
/*      */                   
/* 5889 */                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5890 */                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 5891 */                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                   
/* 5893 */                   _jspx_th_c_005fwhen_005f1.setTest("${param.method == 'showdetails' && param.all!='true'}");
/* 5894 */                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 5895 */                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                     for (;;) {
/* 5897 */                       out.write("\n         ");
/* 5898 */                       out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 5899 */                       out.write(10);
/* 5900 */                       out.write(32);
/* 5901 */                       out.write(32);
/* 5902 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 5903 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 5907 */                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 5908 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                   }
/*      */                   
/* 5911 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5912 */                   out.write(10);
/* 5913 */                   out.write(32);
/* 5914 */                   out.write(32);
/*      */                   
/* 5916 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5917 */                   _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 5918 */                   _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 5919 */                   int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 5920 */                   if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                     for (;;) {
/* 5922 */                       out.write("\n\n<a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 5923 */                       if (_jspx_meth_c_005fout_005f48(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                         return;
/* 5925 */                       out.write("\"\n      class=\"new-left-links\">");
/* 5926 */                       out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 5927 */                       out.write("</a>\n\n\n  ");
/* 5928 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 5929 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 5933 */                   if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 5934 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                   }
/*      */                   
/* 5937 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 5938 */                   out.write(10);
/* 5939 */                   out.write(32);
/* 5940 */                   out.write(32);
/* 5941 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 5942 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 5946 */               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 5947 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */               }
/*      */               
/* 5950 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 5951 */               out.write("\n  </td>\n  </tr>\n  <!--Alert configuration should be enabled for Admin and Demo users alone-->\n  ");
/*      */               
/* 5953 */               IfTag _jspx_th_c_005fif_005f28 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5954 */               _jspx_th_c_005fif_005f28.setPageContext(_jspx_page_context);
/* 5955 */               _jspx_th_c_005fif_005f28.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 5957 */               _jspx_th_c_005fif_005f28.setTest("${!empty ADMIN || !empty DEMO}");
/* 5958 */               int _jspx_eval_c_005fif_005f28 = _jspx_th_c_005fif_005f28.doStartTag();
/* 5959 */               if (_jspx_eval_c_005fif_005f28 != 0) {
/*      */                 for (;;) {
/* 5961 */                   out.write("\n   <tr>\n          <td width=\"88%\" class=\"leftlinkstd\">\n       \t");
/*      */                   
/* 5963 */                   ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5964 */                   _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 5965 */                   _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fif_005f28);
/* 5966 */                   int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 5967 */                   if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                     for (;;) {
/* 5969 */                       out.write("\n         ");
/*      */                       
/* 5971 */                       WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5972 */                       _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 5973 */                       _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                       
/* 5975 */                       _jspx_th_c_005fwhen_005f2.setTest("${param.all=='true'}");
/* 5976 */                       int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 5977 */                       if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                         for (;;) {
/* 5979 */                           out.write("\n                ");
/* 5980 */                           out.print(ALERTCONFIG_TEXT);
/* 5981 */                           out.write("\n         ");
/* 5982 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 5983 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5987 */                       if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 5988 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                       }
/*      */                       
/* 5991 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 5992 */                       out.write("\n  \t");
/*      */                       
/* 5994 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5995 */                       _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 5996 */                       _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 5997 */                       int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 5998 */                       if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                         for (;;) {
/* 6000 */                           out.write("\n          <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 6001 */                           if (_jspx_meth_c_005fout_005f49(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/*      */                             return;
/* 6003 */                           out.write("\"\n            class=\"new-left-links\">");
/* 6004 */                           out.print(ALERTCONFIG_TEXT);
/* 6005 */                           out.write("</a>\n              ");
/* 6006 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 6007 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 6011 */                       if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 6012 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                       }
/*      */                       
/* 6015 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 6016 */                       out.write("\n\t      ");
/* 6017 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 6018 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 6022 */                   if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 6023 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                   }
/*      */                   
/* 6026 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 6027 */                   out.write("\n            </td>\n        </tr>\n  ");
/* 6028 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f28.doAfterBody();
/* 6029 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 6033 */               if (_jspx_th_c_005fif_005f28.doEndTag() == 5) {
/* 6034 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28); return;
/*      */               }
/*      */               
/* 6037 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 6038 */               out.write("\n<!-- Edit link should be enabled for ADMIN and DEMO Users alone -->\n");
/*      */               
/* 6040 */               PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6041 */               _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 6042 */               _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 6044 */               _jspx_th_logic_005fpresent_005f9.setRole("ENTERPRISEADMIN");
/* 6045 */               int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 6046 */               if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                 for (;;) {
/* 6048 */                   out.write("\n <tr>\n  <td class=\"leftlinkstd\" > <a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 6049 */                   out.print(request.getParameter("resourceid"));
/* 6050 */                   out.write("&type=");
/* 6051 */                   out.print(request.getParameter("type"));
/* 6052 */                   out.write("&moname=");
/* 6053 */                   out.print(request.getParameter("moname"));
/* 6054 */                   out.write("&method=showdetails&resourcename=");
/* 6055 */                   out.print(request.getParameter("resourcename"));
/* 6056 */                   out.write("&aam_jump=true&editPage=true\" class=\"new-left-links\">\n");
/* 6057 */                   out.print(FormatUtil.getString("am.webclient.dotnet.edit"));
/* 6058 */                   out.write("</a> </td>\n </tr>\n");
/* 6059 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 6060 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 6064 */               if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 6065 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */               }
/*      */               
/* 6068 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 6069 */               out.write(10);
/* 6070 */               out.write(10);
/*      */               
/* 6072 */               IfTag _jspx_th_c_005fif_005f29 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6073 */               _jspx_th_c_005fif_005f29.setPageContext(_jspx_page_context);
/* 6074 */               _jspx_th_c_005fif_005f29.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 6076 */               _jspx_th_c_005fif_005f29.setTest("${!empty ADMIN || !empty DEMO}");
/* 6077 */               int _jspx_eval_c_005fif_005f29 = _jspx_th_c_005fif_005f29.doStartTag();
/* 6078 */               if (_jspx_eval_c_005fif_005f29 != 0) {
/*      */                 for (;;) {
/* 6080 */                   out.write("\n  <tr>\n    <td class=\"leftlinkstd\">\n ");
/*      */                   
/* 6082 */                   ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 6083 */                   _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 6084 */                   _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f29);
/* 6085 */                   int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 6086 */                   if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                     for (;;) {
/* 6088 */                       out.write("\n    ");
/*      */                       
/* 6090 */                       WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6091 */                       _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 6092 */                       _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                       
/* 6094 */                       _jspx_th_c_005fwhen_005f3.setTest("${uri=='/reconfigure.do'}");
/* 6095 */                       int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 6096 */                       if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                         for (;;) {
/* 6098 */                           out.write("\n        ");
/* 6099 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 6100 */                           out.write(10);
/* 6101 */                           out.write(32);
/* 6102 */                           out.write(32);
/* 6103 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 6104 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 6108 */                       if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 6109 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                       }
/*      */                       
/* 6112 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 6113 */                       out.write(10);
/* 6114 */                       out.write(32);
/* 6115 */                       out.write(32);
/*      */                       
/* 6117 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 6118 */                       _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 6119 */                       _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 6120 */                       int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 6121 */                       if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                         for (;;) {
/* 6123 */                           out.write("\n    <a href=\"/manageConfMons.do?haid=");
/* 6124 */                           if (_jspx_meth_c_005fout_005f50(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                             return;
/* 6126 */                           out.write("&method=editPreConfMonitor&resourceid=");
/* 6127 */                           out.print(request.getParameter("resourceid"));
/* 6128 */                           out.write("&type=JBoss:8080&resourcename=");
/* 6129 */                           out.print(request.getParameter("moname"));
/* 6130 */                           out.write("&displayName=");
/* 6131 */                           out.print(request.getParameter("resourcename"));
/* 6132 */                           out.write("\"\n      class=\"new-left-links\">");
/* 6133 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 6134 */                           out.write("</a>\n  ");
/* 6135 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 6136 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 6140 */                       if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 6141 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                       }
/*      */                       
/* 6144 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 6145 */                       out.write(10);
/* 6146 */                       out.write(32);
/* 6147 */                       out.write(32);
/* 6148 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 6149 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 6153 */                   if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 6154 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                   }
/*      */                   
/* 6157 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 6158 */                   out.write("\n  </td>\n  </tr>\n");
/* 6159 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f29.doAfterBody();
/* 6160 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 6164 */               if (_jspx_th_c_005fif_005f29.doEndTag() == 5) {
/* 6165 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29); return;
/*      */               }
/*      */               
/* 6168 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 6169 */               out.write(10);
/* 6170 */               out.write(10);
/*      */               
/* 6172 */               IfTag _jspx_th_c_005fif_005f30 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6173 */               _jspx_th_c_005fif_005f30.setPageContext(_jspx_page_context);
/* 6174 */               _jspx_th_c_005fif_005f30.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 6176 */               _jspx_th_c_005fif_005f30.setTest("${!empty ADMIN || !empty DEMO}");
/* 6177 */               int _jspx_eval_c_005fif_005f30 = _jspx_th_c_005fif_005f30.doStartTag();
/* 6178 */               if (_jspx_eval_c_005fif_005f30 != 0) {
/*      */                 for (;;) {
/* 6180 */                   out.write(10);
/* 6181 */                   out.write(10);
/*      */                   
/* 6183 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 6184 */                   _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 6185 */                   _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fif_005f30);
/*      */                   
/* 6187 */                   _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 6188 */                   int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 6189 */                   if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                     for (;;) {
/* 6191 */                       out.write("\n  <tr>\n    <td class=\"leftlinkstd\" >\n     <A href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 6192 */                       out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 6193 */                       out.write("</A></td>\n  \t</td>\n  </tr>\n");
/* 6194 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 6195 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 6199 */                   if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 6200 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                   }
/*      */                   
/* 6203 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 6204 */                   out.write(10);
/* 6205 */                   out.write(10);
/*      */                   
/* 6207 */                   PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6208 */                   _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 6209 */                   _jspx_th_logic_005fpresent_005f10.setParent(_jspx_th_c_005fif_005f30);
/*      */                   
/* 6211 */                   _jspx_th_logic_005fpresent_005f10.setRole("DEMO");
/* 6212 */                   int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 6213 */                   if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                     for (;;) {
/* 6215 */                       out.write("\n\n<td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 6216 */                       out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 6217 */                       out.write("</a></td>\n\n");
/* 6218 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 6219 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 6223 */                   if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 6224 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10); return;
/*      */                   }
/*      */                   
/* 6227 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 6228 */                   out.write("\n\n\n\n  </tr>\n  ");
/* 6229 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f30.doAfterBody();
/* 6230 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 6234 */               if (_jspx_th_c_005fif_005f30.doEndTag() == 5) {
/* 6235 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30); return;
/*      */               }
/*      */               
/* 6238 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 6239 */               out.write(10);
/*      */               
/* 6241 */               IfTag _jspx_th_c_005fif_005f31 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6242 */               _jspx_th_c_005fif_005f31.setPageContext(_jspx_page_context);
/* 6243 */               _jspx_th_c_005fif_005f31.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 6245 */               _jspx_th_c_005fif_005f31.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 6246 */               int _jspx_eval_c_005fif_005f31 = _jspx_th_c_005fif_005f31.doStartTag();
/* 6247 */               if (_jspx_eval_c_005fif_005f31 != 0) {
/*      */                 for (;;) {
/* 6249 */                   out.write("\n  <tr>\n  ");
/*      */                   
/* 6251 */                   if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                   {
/*      */ 
/* 6254 */                     out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 6255 */                     out.print(FormatUtil.getString("Manage"));
/* 6256 */                     out.write("</A></td>\n    ");
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/*      */ 
/* 6262 */                     out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 6263 */                     out.print(FormatUtil.getString("UnManage"));
/* 6264 */                     out.write("</A></td>\n    ");
/*      */                   }
/*      */                   
/*      */ 
/* 6268 */                   out.write("\n  </tr>\n  ");
/* 6269 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f31.doAfterBody();
/* 6270 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 6274 */               if (_jspx_th_c_005fif_005f31.doEndTag() == 5) {
/* 6275 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31); return;
/*      */               }
/*      */               
/* 6278 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 6279 */               out.write("\n\n\n\n");
/*      */               
/* 6281 */               if ((configure_link != null) && (!configure_link.equals("null")))
/*      */               {
/*      */ 
/* 6284 */                 out.write("\n\n   ");
/*      */                 
/* 6286 */                 IfTag _jspx_th_c_005fif_005f32 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6287 */                 _jspx_th_c_005fif_005f32.setPageContext(_jspx_page_context);
/* 6288 */                 _jspx_th_c_005fif_005f32.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                 
/* 6290 */                 _jspx_th_c_005fif_005f32.setTest("${!empty ADMIN || !empty DEMO}");
/* 6291 */                 int _jspx_eval_c_005fif_005f32 = _jspx_th_c_005fif_005f32.doStartTag();
/* 6292 */                 if (_jspx_eval_c_005fif_005f32 != 0) {
/*      */                   for (;;) {
/* 6294 */                     out.write("\n    <tr>\n    <td class=\"leftlinkstd\" >\n\n    \t<a href=\"");
/* 6295 */                     out.print(request.getAttribute("configurelink"));
/* 6296 */                     out.write("\" onclick=\"javascript:return subAddCustom(this);\" class=\"new-left-links\">\n    \t  ");
/* 6297 */                     out.print(FormatUtil.getString("am.webclient.common.addcustomattributes.text"));
/* 6298 */                     out.write("\n    \t</a>\n    </td>\n  </tr>\n   ");
/* 6299 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f32.doAfterBody();
/* 6300 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 6304 */                 if (_jspx_th_c_005fif_005f32.doEndTag() == 5) {
/* 6305 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32); return;
/*      */                 }
/*      */                 
/* 6308 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32);
/* 6309 */                 out.write("\n   ");
/*      */               }
/*      */               
/*      */ 
/* 6313 */               out.write(10);
/* 6314 */               out.write(32);
/* 6315 */               out.write(32);
/*      */               
/* 6317 */               IfTag _jspx_th_c_005fif_005f33 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6318 */               _jspx_th_c_005fif_005f33.setPageContext(_jspx_page_context);
/* 6319 */               _jspx_th_c_005fif_005f33.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 6321 */               _jspx_th_c_005fif_005f33.setTest("${!empty ADMIN}");
/* 6322 */               int _jspx_eval_c_005fif_005f33 = _jspx_th_c_005fif_005f33.doStartTag();
/* 6323 */               if (_jspx_eval_c_005fif_005f33 != 0) {
/*      */                 for (;;) {
/* 6325 */                   out.write("\n  \t   \t<tr>\n  \t       \t <td colspan=\"2\" class=\"leftlinkstd\">\n  \t       \t ");
/* 6326 */                   out.print(com.adventnet.appmanager.fault.FaultUtil.getAlertTemplateURL(request.getParameter("resourceid"), request.getParameter("name"), "JBOSS-server", "JBoss Server"));
/* 6327 */                   out.write("\n  \t       \t </td>\n  \t      \t</tr>\n  ");
/* 6328 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f33.doAfterBody();
/* 6329 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 6333 */               if (_jspx_th_c_005fif_005f33.doEndTag() == 5) {
/* 6334 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33); return;
/*      */               }
/*      */               
/* 6337 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33);
/* 6338 */               out.write(10);
/* 6339 */               out.write(32);
/* 6340 */               out.write(32);
/*      */               
/* 6342 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 6343 */               _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 6344 */               _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 6346 */               _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 6347 */               int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 6348 */               if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                 for (;;) {
/* 6350 */                   out.write("\n    ");
/*      */                   
/* 6352 */                   String resourceid_poll = request.getParameter("resourceid");
/* 6353 */                   String resourcetype = request.getParameter("type");
/*      */                   
/* 6355 */                   out.write("\n      <tr>\n      <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n      <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 6356 */                   out.print(resourceid_poll);
/* 6357 */                   out.write("&resourcetype=");
/* 6358 */                   out.print(resourcetype);
/* 6359 */                   out.write("\" class=\"new-left-links\"> ");
/* 6360 */                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 6361 */                   out.write("</a></td>\n    </tr>\n  ");
/* 6362 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 6363 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 6367 */               if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 6368 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */               }
/*      */               
/* 6371 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 6372 */               out.write(10);
/* 6373 */               out.write(32);
/* 6374 */               out.write(32);
/*      */               
/* 6376 */               PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6377 */               _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 6378 */               _jspx_th_logic_005fpresent_005f11.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 6380 */               _jspx_th_logic_005fpresent_005f11.setRole("DEMO");
/* 6381 */               int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 6382 */               if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                 for (;;) {
/* 6384 */                   out.write("\n        <tr>\n        <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n        <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 6385 */                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 6386 */                   out.write("</a></td>\n        </td>\n    ");
/* 6387 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 6388 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 6392 */               if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 6393 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11); return;
/*      */               }
/*      */               
/* 6396 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 6397 */               out.write("\n    ");
/* 6398 */               out.write("<!-- $Id$-->\n\n\n  \n");
/*      */               
/* 6400 */               if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */               {
/* 6402 */                 Map<com.me.apm.cmdb.APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 6403 */                 String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                 
/* 6405 */                 String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(com.me.apm.cmdb.APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(com.me.apm.cmdb.APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 6406 */                 String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(com.me.apm.cmdb.APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(com.me.apm.cmdb.APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 6407 */                 if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                 {
/* 6409 */                   if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                   {
/*      */ 
/* 6412 */                     out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 6413 */                     out.print(ciInfoUrl);
/* 6414 */                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 6415 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 6416 */                     out.write("</a></td>");
/* 6417 */                     out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 6418 */                     out.print(ciRLUrl);
/* 6419 */                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 6420 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 6421 */                     out.write("</a></td>");
/* 6422 */                     out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                   }
/* 6426 */                   else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                   {
/*      */ 
/* 6429 */                     out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 6430 */                     out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 6431 */                     out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 6432 */                     out.print(ciInfoUrl);
/* 6433 */                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 6434 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 6435 */                     out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 6436 */                     out.print(ciRLUrl);
/* 6437 */                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 6438 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 6439 */                     out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/* 6444 */               out.write("\n \n \n\n");
/* 6445 */               out.write("\n  </table>\n");
/*      */               
/*      */ 
/* 6448 */               ArrayList attribIDs = new ArrayList();
/* 6449 */               ArrayList resIDs = new ArrayList();
/* 6450 */               resIDs.add(resourceid);
/* 6451 */               attribIDs.add("308");
/* 6452 */               attribIDs.add("309");
/* 6453 */               Properties alert = getStatus(resIDs, attribIDs);
/*      */               
/* 6455 */               out.write("\n</td>\n  </tr>\n  <tr>\n  <td>&nbsp;\n\n  </td>\n  </tr>\n  <tr>\n    <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 6456 */               out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 6457 */               out.write("</td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 6458 */               if (_jspx_meth_c_005fout_005f51(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 6460 */               out.write("&attributeid=309')\" class=\"new-left-links\">");
/* 6461 */               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 6462 */               out.write("</a></td>\n        <td><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 6463 */               if (_jspx_meth_c_005fout_005f52(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 6465 */               out.write("&attributeid=309')\">");
/* 6466 */               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "309")));
/* 6467 */               out.write("</a></td>\n   </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 6468 */               if (_jspx_meth_c_005fout_005f53(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 6470 */               out.write("&attributeid=308')\" class=\"new-left-links\">");
/* 6471 */               out.print(FormatUtil.getString("Availability"));
/* 6472 */               out.write("</a></td>\n    <td width=\"51%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 6473 */               if (_jspx_meth_c_005fout_005f54(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 6475 */               out.write("&attributeid=308')\">\n    ");
/* 6476 */               out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "308")));
/* 6477 */               out.write("</a>\n </td>\n  </tr>\n  ");
/* 6478 */               if (_jspx_meth_c_005fif_005f34(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 6480 */               out.write("\n</table></td>\n  </tr>\n    </tr>\n  <tr>\n  <td>&nbsp;\n\n  </td>\n  </tr>\n\n\n");
/* 6481 */               out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */               
/*      */ 
/*      */ 
/* 6485 */               boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 6486 */               if (EnterpriseUtil.isIt360MSPEdition)
/*      */               {
/* 6488 */                 showAssociatedBSG = false;
/*      */                 
/*      */ 
/*      */ 
/* 6492 */                 com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getInstance();Properties assBsgSiteProp = com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getSiteProp(request);
/* 6493 */                 com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getInstance();String customerId = com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getCustomerId(request);
/* 6494 */                 String loginName = request.getUserPrincipal().getName();
/* 6495 */                 com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getInstance();boolean showAllBSGs = com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                 
/* 6497 */                 if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                 {
/* 6499 */                   showAssociatedBSG = true;
/*      */                 }
/*      */               }
/* 6502 */               String monitorType = request.getParameter("type");
/* 6503 */               ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 6504 */               boolean mon = conf1.isConfMonitor(monitorType);
/* 6505 */               if (showAssociatedBSG)
/*      */               {
/* 6507 */                 Hashtable associatedmgs = new Hashtable();
/* 6508 */                 String resId = request.getParameter("resourceid");
/* 6509 */                 request.setAttribute("associatedmgs", com.adventnet.appmanager.fault.FaultUtil.getAdminAssociatedMG(resId, request));
/* 6510 */                 if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                 {
/* 6512 */                   mon = false;
/*      */                 }
/*      */                 
/* 6515 */                 if (!mon)
/*      */                 {
/* 6517 */                   out.write(10);
/* 6518 */                   out.write(10);
/*      */                   
/* 6520 */                   IfTag _jspx_th_c_005fif_005f35 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6521 */                   _jspx_th_c_005fif_005f35.setPageContext(_jspx_page_context);
/* 6522 */                   _jspx_th_c_005fif_005f35.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 6524 */                   _jspx_th_c_005fif_005f35.setTest("${!empty associatedmgs}");
/* 6525 */                   int _jspx_eval_c_005fif_005f35 = _jspx_th_c_005fif_005f35.doStartTag();
/* 6526 */                   if (_jspx_eval_c_005fif_005f35 != 0) {
/*      */                     for (;;) {
/* 6528 */                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 6529 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 6530 */                       out.write("</td>\n        </tr>\n        ");
/*      */                       
/* 6532 */                       ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 6533 */                       _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 6534 */                       _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f35);
/*      */                       
/* 6536 */                       _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                       
/* 6538 */                       _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                       
/* 6540 */                       _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 6541 */                       int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                       try {
/* 6543 */                         int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 6544 */                         if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                           for (;;) {
/* 6546 */                             out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 6547 */                             if (_jspx_meth_c_005fout_005f56(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6605 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 6606 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 6549 */                             out.write("&method=showApplication\" title=\"");
/* 6550 */                             if (_jspx_meth_c_005fout_005f57(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6605 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 6606 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 6552 */                             out.write("\"  class=\"new-left-links\">\n         ");
/* 6553 */                             if (_jspx_meth_c_005fset_005f11(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6605 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 6606 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 6555 */                             out.write("\n    \t");
/* 6556 */                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 6557 */                             out.write("\n         </a></td>\n        <td>");
/*      */                             
/* 6559 */                             PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6560 */                             _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 6561 */                             _jspx_th_logic_005fpresent_005f12.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                             
/* 6563 */                             _jspx_th_logic_005fpresent_005f12.setRole("ADMIN");
/* 6564 */                             int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 6565 */                             if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */                               for (;;) {
/* 6567 */                                 out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 6568 */                                 if (_jspx_meth_c_005fout_005f59(_jspx_th_logic_005fpresent_005f12, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6605 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 6606 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 6570 */                                 out.write(39);
/* 6571 */                                 out.write(44);
/* 6572 */                                 out.write(39);
/* 6573 */                                 out.print(resId);
/* 6574 */                                 out.write(39);
/* 6575 */                                 out.write(44);
/* 6576 */                                 out.write(39);
/* 6577 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 6578 */                                 out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 6579 */                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 6580 */                                 out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 6581 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 6582 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 6586 */                             if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 6587 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6605 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 6606 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 6590 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 6591 */                             out.write("</td>\n        </tr>\n\t");
/* 6592 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 6593 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 6597 */                         if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6605 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 6606 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 6601 */                           int tmp28600_28599 = 0; int[] tmp28600_28597 = _jspx_push_body_count_c_005fforEach_005f0; int tmp28602_28601 = tmp28600_28597[tmp28600_28599];tmp28600_28597[tmp28600_28599] = (tmp28602_28601 - 1); if (tmp28602_28601 <= 0) break;
/* 6602 */                           out = _jspx_page_context.popBody(); }
/* 6603 */                         _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                       } finally {
/* 6605 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 6606 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                       }
/* 6608 */                       out.write("\n      </table>\n ");
/* 6609 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f35.doAfterBody();
/* 6610 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 6614 */                   if (_jspx_th_c_005fif_005f35.doEndTag() == 5) {
/* 6615 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f35); return;
/*      */                   }
/*      */                   
/* 6618 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f35);
/* 6619 */                   out.write(10);
/* 6620 */                   out.write(32);
/*      */                   
/* 6622 */                   IfTag _jspx_th_c_005fif_005f36 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6623 */                   _jspx_th_c_005fif_005f36.setPageContext(_jspx_page_context);
/* 6624 */                   _jspx_th_c_005fif_005f36.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 6626 */                   _jspx_th_c_005fif_005f36.setTest("${empty associatedmgs}");
/* 6627 */                   int _jspx_eval_c_005fif_005f36 = _jspx_th_c_005fif_005f36.doStartTag();
/* 6628 */                   if (_jspx_eval_c_005fif_005f36 != 0) {
/*      */                     for (;;) {
/* 6630 */                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 6631 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 6632 */                       out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 6633 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 6634 */                       out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 6635 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f36.doAfterBody();
/* 6636 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 6640 */                   if (_jspx_th_c_005fif_005f36.doEndTag() == 5) {
/* 6641 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f36); return;
/*      */                   }
/*      */                   
/* 6644 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f36);
/* 6645 */                   out.write(10);
/* 6646 */                   out.write(32);
/* 6647 */                   out.write(10);
/*      */ 
/*      */                 }
/* 6650 */                 else if (mon)
/*      */                 {
/*      */ 
/*      */ 
/* 6654 */                   out.write(10);
/*      */                   
/* 6656 */                   IfTag _jspx_th_c_005fif_005f37 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6657 */                   _jspx_th_c_005fif_005f37.setPageContext(_jspx_page_context);
/* 6658 */                   _jspx_th_c_005fif_005f37.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 6660 */                   _jspx_th_c_005fif_005f37.setTest("${!empty associatedmgs}");
/* 6661 */                   int _jspx_eval_c_005fif_005f37 = _jspx_th_c_005fif_005f37.doStartTag();
/* 6662 */                   if (_jspx_eval_c_005fif_005f37 != 0) {
/*      */                     for (;;) {
/* 6664 */                       out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 6665 */                       if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fif_005f37, _jspx_page_context))
/*      */                         return;
/* 6667 */                       out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                       
/* 6669 */                       ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 6670 */                       _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 6671 */                       _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f37);
/*      */                       
/* 6673 */                       _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                       
/* 6675 */                       _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                       
/* 6677 */                       _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 6678 */                       int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                       try {
/* 6680 */                         int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 6681 */                         if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                           for (;;) {
/* 6683 */                             out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 6684 */                             if (_jspx_meth_c_005fout_005f60(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6745 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 6746 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 6686 */                             out.write("&method=showApplication\" title=\"");
/* 6687 */                             if (_jspx_meth_c_005fout_005f61(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6745 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 6746 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 6689 */                             out.write("\"  class=\"staticlinks\">\n         ");
/* 6690 */                             if (_jspx_meth_c_005fset_005f12(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6745 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 6746 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 6692 */                             out.write("\n    \t");
/* 6693 */                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 6694 */                             out.write("</a></span>\t\n\t\t ");
/*      */                             
/* 6696 */                             PresentTag _jspx_th_logic_005fpresent_005f13 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6697 */                             _jspx_th_logic_005fpresent_005f13.setPageContext(_jspx_page_context);
/* 6698 */                             _jspx_th_logic_005fpresent_005f13.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                             
/* 6700 */                             _jspx_th_logic_005fpresent_005f13.setRole("ADMIN");
/* 6701 */                             int _jspx_eval_logic_005fpresent_005f13 = _jspx_th_logic_005fpresent_005f13.doStartTag();
/* 6702 */                             if (_jspx_eval_logic_005fpresent_005f13 != 0) {
/*      */                               for (;;) {
/* 6704 */                                 out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 6705 */                                 if (_jspx_meth_c_005fout_005f63(_jspx_th_logic_005fpresent_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6745 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 6746 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 6707 */                                 out.write(39);
/* 6708 */                                 out.write(44);
/* 6709 */                                 out.write(39);
/* 6710 */                                 out.print(resId);
/* 6711 */                                 out.write(39);
/* 6712 */                                 out.write(44);
/* 6713 */                                 out.write(39);
/* 6714 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 6715 */                                 out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 6716 */                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 6717 */                                 out.write("\"  title=\"");
/* 6718 */                                 if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_logic_005fpresent_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6745 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 6746 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 6720 */                                 out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 6721 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f13.doAfterBody();
/* 6722 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 6726 */                             if (_jspx_th_logic_005fpresent_005f13.doEndTag() == 5) {
/* 6727 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6745 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 6746 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 6730 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13);
/* 6731 */                             out.write("\n\n\t\t \t");
/* 6732 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 6733 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 6737 */                         if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6745 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 6746 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 6741 */                           int tmp29626_29625 = 0; int[] tmp29626_29623 = _jspx_push_body_count_c_005fforEach_005f1; int tmp29628_29627 = tmp29626_29623[tmp29626_29625];tmp29626_29623[tmp29626_29625] = (tmp29628_29627 - 1); if (tmp29628_29627 <= 0) break;
/* 6742 */                           out = _jspx_page_context.popBody(); }
/* 6743 */                         _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                       } finally {
/* 6745 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 6746 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                       }
/* 6748 */                       out.write("\n\t\n\t\t\t</td>\n\t ");
/* 6749 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f37.doAfterBody();
/* 6750 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 6754 */                   if (_jspx_th_c_005fif_005f37.doEndTag() == 5) {
/* 6755 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f37); return;
/*      */                   }
/*      */                   
/* 6758 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f37);
/* 6759 */                   out.write(10);
/* 6760 */                   out.write(32);
/* 6761 */                   if (_jspx_meth_c_005fif_005f38(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                     return;
/* 6763 */                   out.write(32);
/* 6764 */                   out.write(10);
/*      */                 }
/*      */                 
/*      */               }
/* 6768 */               else if (mon)
/*      */               {
/*      */ 
/* 6771 */                 out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 6772 */                 if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                   return;
/* 6774 */                 out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */               }
/*      */               
/*      */ 
/* 6778 */               out.write(9);
/* 6779 */               out.write(9);
/* 6780 */               out.write("\n\n</table>\n\n\n");
/* 6781 */               out.write(10);
/* 6782 */               response.setContentType("text/html;charset=UTF-8");
/* 6783 */               out.write(10);
/* 6784 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f5.doAfterBody();
/* 6785 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 6788 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 6789 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 6792 */           if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 6793 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5); return;
/*      */           }
/*      */           
/* 6796 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5);
/* 6797 */           out.write(10);
/* 6798 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 6799 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6803 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 6804 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 6807 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 6808 */         out.write(10);
/* 6809 */         out.write(10);
/* 6810 */         out.write(10);
/*      */       }
/* 6812 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 6813 */         out = _jspx_out;
/* 6814 */         if ((out != null) && (out.getBufferSize() != 0))
/* 6815 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 6816 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 6819 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6825 */     PageContext pageContext = _jspx_page_context;
/* 6826 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6828 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 6829 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 6830 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 6832 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 6833 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 6835 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 6836 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 6838 */           out.write(10);
/* 6839 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 6840 */             return true;
/* 6841 */           out.write(10);
/* 6842 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 6843 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6847 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 6848 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6851 */         int tmp177_176 = 0; int[] tmp177_174 = _jspx_push_body_count_c_005fcatch_005f0; int tmp179_178 = tmp177_174[tmp177_176];tmp177_174[tmp177_176] = (tmp179_178 - 1); if (tmp179_178 <= 0) break;
/* 6852 */         out = _jspx_page_context.popBody(); }
/* 6853 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 6855 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 6856 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 6858 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 6863 */     PageContext pageContext = _jspx_page_context;
/* 6864 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6866 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 6867 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 6868 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 6870 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 6872 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 6873 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 6874 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 6875 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 6876 */       return true;
/*      */     }
/* 6878 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 6879 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6884 */     PageContext pageContext = _jspx_page_context;
/* 6885 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6887 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6888 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 6889 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6891 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 6893 */     _jspx_th_tiles_005fput_005f0.setValue("JBoss Server - Snapshot");
/* 6894 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 6895 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 6896 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 6897 */       return true;
/*      */     }
/* 6899 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 6900 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6905 */     PageContext pageContext = _jspx_page_context;
/* 6906 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6908 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6909 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 6910 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6912 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 6913 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 6914 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 6916 */         out.write(10);
/* 6917 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 6918 */           return true;
/* 6919 */         out.write(10);
/* 6920 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 6921 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6925 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 6926 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 6927 */       return true;
/*      */     }
/* 6929 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 6930 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6935 */     PageContext pageContext = _jspx_page_context;
/* 6936 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6938 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6939 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 6940 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 6942 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 6944 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 6945 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 6946 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 6947 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6948 */       return true;
/*      */     }
/* 6950 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6951 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6956 */     PageContext pageContext = _jspx_page_context;
/* 6957 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6959 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6960 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 6961 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6963 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 6964 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 6965 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 6967 */         out.write(10);
/* 6968 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 6969 */           return true;
/* 6970 */         out.write(10);
/* 6971 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 6972 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6976 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 6977 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6978 */       return true;
/*      */     }
/* 6980 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6981 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6986 */     PageContext pageContext = _jspx_page_context;
/* 6987 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6989 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6990 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 6991 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 6993 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 6995 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 6996 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 6997 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 6998 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 6999 */       return true;
/*      */     }
/* 7001 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 7002 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7007 */     PageContext pageContext = _jspx_page_context;
/* 7008 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7010 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7011 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 7012 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 7014 */     _jspx_th_c_005fout_005f0.setValue("${monitorname}");
/* 7015 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 7016 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 7017 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 7018 */       return true;
/*      */     }
/* 7020 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 7021 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7026 */     PageContext pageContext = _jspx_page_context;
/* 7027 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7029 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7030 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 7031 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 7033 */     _jspx_th_c_005fout_005f1.setValue("${monitorname}");
/* 7034 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 7035 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 7036 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 7037 */       return true;
/*      */     }
/* 7039 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 7040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7045 */     PageContext pageContext = _jspx_page_context;
/* 7046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7048 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 7049 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 7050 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 7052 */     _jspx_th_c_005fset_005f0.setVar("redirect");
/*      */     
/* 7054 */     _jspx_th_c_005fset_005f0.setScope("request");
/* 7055 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 7056 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 7057 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 7058 */         out = _jspx_page_context.pushBody();
/* 7059 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 7060 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7063 */         out.write("/showresource.do?method=showResourceForResourceID&resourceid=");
/* 7064 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 7065 */           return true;
/* 7066 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 7067 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7070 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 7071 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7074 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 7075 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 7076 */       return true;
/*      */     }
/* 7078 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 7079 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7084 */     PageContext pageContext = _jspx_page_context;
/* 7085 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7087 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7088 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 7089 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 7091 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 7092 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 7093 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 7094 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 7095 */       return true;
/*      */     }
/* 7097 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 7098 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7103 */     PageContext pageContext = _jspx_page_context;
/* 7104 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7106 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7107 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 7108 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 7110 */     _jspx_th_c_005fout_005f3.setValue("${requestScope.JBossProps.httpport}");
/* 7111 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 7112 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 7113 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 7114 */       return true;
/*      */     }
/* 7116 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 7117 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7122 */     PageContext pageContext = _jspx_page_context;
/* 7123 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7125 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7126 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 7127 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7129 */     _jspx_th_c_005fif_005f9.setTest("${not empty param.haid}");
/* 7130 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 7131 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 7133 */         out.write(10);
/* 7134 */         out.write(9);
/* 7135 */         out.write(9);
/* 7136 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 7137 */           return true;
/* 7138 */         out.write(10);
/* 7139 */         out.write(9);
/* 7140 */         out.write(9);
/* 7141 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 7142 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7146 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 7147 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 7148 */       return true;
/*      */     }
/* 7150 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 7151 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7156 */     PageContext pageContext = _jspx_page_context;
/* 7157 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7159 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 7160 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 7161 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 7163 */     _jspx_th_c_005fset_005f1.setVar("myfield_paramresid");
/*      */     
/* 7165 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 7166 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 7167 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 7168 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 7169 */         out = _jspx_page_context.pushBody();
/* 7170 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 7171 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7174 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 7175 */           return true;
/* 7176 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 7177 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7180 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 7181 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7184 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 7185 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 7186 */       return true;
/*      */     }
/* 7188 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 7189 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7194 */     PageContext pageContext = _jspx_page_context;
/* 7195 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7197 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7198 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 7199 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 7201 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 7202 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 7203 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 7204 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 7205 */       return true;
/*      */     }
/* 7207 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 7208 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7213 */     PageContext pageContext = _jspx_page_context;
/* 7214 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7216 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7217 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 7218 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7220 */     _jspx_th_c_005fif_005f10.setTest("${not empty param.resourceid}");
/* 7221 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 7222 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 7224 */         out.write(10);
/* 7225 */         out.write(9);
/* 7226 */         out.write(9);
/* 7227 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 7228 */           return true;
/* 7229 */         out.write(10);
/* 7230 */         out.write(9);
/* 7231 */         out.write(9);
/* 7232 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 7233 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7237 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 7238 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 7239 */       return true;
/*      */     }
/* 7241 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 7242 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7247 */     PageContext pageContext = _jspx_page_context;
/* 7248 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7250 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 7251 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 7252 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 7254 */     _jspx_th_c_005fset_005f2.setVar("myfield_paramresid");
/*      */     
/* 7256 */     _jspx_th_c_005fset_005f2.setScope("page");
/* 7257 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 7258 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 7259 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 7260 */         out = _jspx_page_context.pushBody();
/* 7261 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 7262 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7265 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 7266 */           return true;
/* 7267 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 7268 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7271 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 7272 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7275 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 7276 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 7277 */       return true;
/*      */     }
/* 7279 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 7280 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7285 */     PageContext pageContext = _jspx_page_context;
/* 7286 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7288 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7289 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 7290 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 7292 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 7293 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 7294 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 7295 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 7296 */       return true;
/*      */     }
/* 7298 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 7299 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7304 */     PageContext pageContext = _jspx_page_context;
/* 7305 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7307 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7308 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 7309 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7311 */     _jspx_th_c_005fout_005f6.setValue("${myfield_paramresid}");
/* 7312 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 7313 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 7314 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 7315 */       return true;
/*      */     }
/* 7317 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 7318 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7323 */     PageContext pageContext = _jspx_page_context;
/* 7324 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7326 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7327 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 7328 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7330 */     _jspx_th_c_005fif_005f11.setTest("${not empty param.haid}");
/* 7331 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 7332 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 7334 */         out.write(10);
/* 7335 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 7336 */           return true;
/* 7337 */         out.write(10);
/* 7338 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 7339 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7343 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 7344 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 7345 */       return true;
/*      */     }
/* 7347 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 7348 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7353 */     PageContext pageContext = _jspx_page_context;
/* 7354 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7356 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 7357 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 7358 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 7360 */     _jspx_th_c_005fset_005f3.setVar("myfield_resid");
/*      */     
/* 7362 */     _jspx_th_c_005fset_005f3.setScope("page");
/* 7363 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 7364 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 7365 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 7366 */         out = _jspx_page_context.pushBody();
/* 7367 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 7368 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7371 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 7372 */           return true;
/* 7373 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 7374 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7377 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 7378 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7381 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 7382 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 7383 */       return true;
/*      */     }
/* 7385 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 7386 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7391 */     PageContext pageContext = _jspx_page_context;
/* 7392 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7394 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7395 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 7396 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 7398 */     _jspx_th_c_005fout_005f7.setValue("${param.haid}");
/* 7399 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 7400 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 7401 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 7402 */       return true;
/*      */     }
/* 7404 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 7405 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7410 */     PageContext pageContext = _jspx_page_context;
/* 7411 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7413 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7414 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 7415 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7417 */     _jspx_th_c_005fif_005f12.setTest("${not empty param.resourceid}");
/* 7418 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 7419 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 7421 */         out.write(10);
/* 7422 */         if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fif_005f12, _jspx_page_context))
/* 7423 */           return true;
/* 7424 */         out.write(10);
/* 7425 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 7426 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7430 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 7431 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 7432 */       return true;
/*      */     }
/* 7434 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 7435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7440 */     PageContext pageContext = _jspx_page_context;
/* 7441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7443 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 7444 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 7445 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 7447 */     _jspx_th_c_005fset_005f4.setVar("myfield_resid");
/*      */     
/* 7449 */     _jspx_th_c_005fset_005f4.setScope("page");
/* 7450 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 7451 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 7452 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 7453 */         out = _jspx_page_context.pushBody();
/* 7454 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 7455 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7458 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fset_005f4, _jspx_page_context))
/* 7459 */           return true;
/* 7460 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 7461 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7464 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 7465 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7468 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 7469 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 7470 */       return true;
/*      */     }
/* 7472 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 7473 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7478 */     PageContext pageContext = _jspx_page_context;
/* 7479 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7481 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7482 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 7483 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 7485 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 7486 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 7487 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 7488 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 7489 */       return true;
/*      */     }
/* 7491 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 7492 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7497 */     PageContext pageContext = _jspx_page_context;
/* 7498 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7500 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 7501 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 7502 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7504 */     _jspx_th_c_005fset_005f5.setVar("trstripclass");
/*      */     
/* 7506 */     _jspx_th_c_005fset_005f5.setScope("page");
/* 7507 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 7508 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 7509 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 7510 */         out = _jspx_page_context.pushBody();
/* 7511 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 7512 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7515 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 7516 */           return true;
/* 7517 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 7518 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7521 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 7522 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7525 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 7526 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 7527 */       return true;
/*      */     }
/* 7529 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 7530 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7535 */     PageContext pageContext = _jspx_page_context;
/* 7536 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7538 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7539 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 7540 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 7542 */     _jspx_th_c_005fout_005f9.setValue("");
/* 7543 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 7544 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 7545 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 7546 */       return true;
/*      */     }
/* 7548 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 7549 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7554 */     PageContext pageContext = _jspx_page_context;
/* 7555 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7557 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 7558 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 7559 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7561 */     _jspx_th_c_005fset_005f6.setVar("myfield_entity");
/*      */     
/* 7563 */     _jspx_th_c_005fset_005f6.setScope("page");
/* 7564 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 7565 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 7566 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 7567 */         out = _jspx_page_context.pushBody();
/* 7568 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 7569 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7572 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fset_005f6, _jspx_page_context))
/* 7573 */           return true;
/* 7574 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 7575 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7578 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 7579 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7582 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 7583 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 7584 */       return true;
/*      */     }
/* 7586 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 7587 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7592 */     PageContext pageContext = _jspx_page_context;
/* 7593 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7595 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7596 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 7597 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 7599 */     _jspx_th_c_005fout_005f10.setValue("noalarms");
/* 7600 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 7601 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 7602 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 7603 */       return true;
/*      */     }
/* 7605 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 7606 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7611 */     PageContext pageContext = _jspx_page_context;
/* 7612 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7614 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7615 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 7616 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7618 */     _jspx_th_c_005fif_005f13.setTest("${not empty param.entity}");
/* 7619 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 7620 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 7622 */         out.write(10);
/* 7623 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f13, _jspx_page_context))
/* 7624 */           return true;
/* 7625 */         out.write(10);
/* 7626 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 7627 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7631 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 7632 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 7633 */       return true;
/*      */     }
/* 7635 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 7636 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7641 */     PageContext pageContext = _jspx_page_context;
/* 7642 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7644 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 7645 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 7646 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 7648 */     _jspx_th_c_005fset_005f7.setVar("myfield_entity");
/*      */     
/* 7650 */     _jspx_th_c_005fset_005f7.setScope("page");
/* 7651 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 7652 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 7653 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 7654 */         out = _jspx_page_context.pushBody();
/* 7655 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 7656 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7659 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 7660 */           return true;
/* 7661 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 7662 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7665 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 7666 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7669 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 7670 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 7671 */       return true;
/*      */     }
/* 7673 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 7674 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7679 */     PageContext pageContext = _jspx_page_context;
/* 7680 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7682 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7683 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 7684 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 7686 */     _jspx_th_c_005fout_005f11.setValue("${param.entity}");
/* 7687 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 7688 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 7689 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 7690 */       return true;
/*      */     }
/* 7692 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 7693 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f14(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7698 */     PageContext pageContext = _jspx_page_context;
/* 7699 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7701 */     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7702 */     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 7703 */     _jspx_th_c_005fif_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7705 */     _jspx_th_c_005fif_005f14.setTest("${not empty param.includeClass}");
/* 7706 */     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 7707 */     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */       for (;;) {
/* 7709 */         out.write(10);
/* 7710 */         if (_jspx_meth_c_005fset_005f8(_jspx_th_c_005fif_005f14, _jspx_page_context))
/* 7711 */           return true;
/* 7712 */         out.write(10);
/* 7713 */         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 7714 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7718 */     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 7719 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 7720 */       return true;
/*      */     }
/* 7722 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 7723 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f8(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7728 */     PageContext pageContext = _jspx_page_context;
/* 7729 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7731 */     SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 7732 */     _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 7733 */     _jspx_th_c_005fset_005f8.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 7735 */     _jspx_th_c_005fset_005f8.setVar("trstripclass");
/*      */     
/* 7737 */     _jspx_th_c_005fset_005f8.setScope("page");
/* 7738 */     int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 7739 */     if (_jspx_eval_c_005fset_005f8 != 0) {
/* 7740 */       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 7741 */         out = _jspx_page_context.pushBody();
/* 7742 */         _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 7743 */         _jspx_th_c_005fset_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7746 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fset_005f8, _jspx_page_context))
/* 7747 */           return true;
/* 7748 */         int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 7749 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7752 */       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 7753 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7756 */     if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 7757 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f8);
/* 7758 */       return true;
/*      */     }
/* 7760 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f8);
/* 7761 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fset_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7766 */     PageContext pageContext = _jspx_page_context;
/* 7767 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7769 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7770 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 7771 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fset_005f8);
/*      */     
/* 7773 */     _jspx_th_c_005fout_005f12.setValue("${param.includeClass}");
/* 7774 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 7775 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 7776 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 7777 */       return true;
/*      */     }
/* 7779 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 7780 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7785 */     PageContext pageContext = _jspx_page_context;
/* 7786 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7788 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7789 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 7790 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7792 */     _jspx_th_c_005fout_005f13.setValue("${trstripclass}");
/* 7793 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 7794 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 7795 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 7796 */       return true;
/*      */     }
/* 7798 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 7799 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7804 */     PageContext pageContext = _jspx_page_context;
/* 7805 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7807 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7808 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 7809 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 7810 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 7811 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 7812 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 7813 */         out = _jspx_page_context.pushBody();
/* 7814 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 7815 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7818 */         out.write("am.myfield.customfield.text");
/* 7819 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 7820 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7823 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 7824 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7827 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 7828 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 7829 */       return true;
/*      */     }
/* 7831 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 7832 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7837 */     PageContext pageContext = _jspx_page_context;
/* 7838 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7840 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7841 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 7842 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7844 */     _jspx_th_c_005fout_005f14.setValue("${myfield_resid}");
/* 7845 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 7846 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 7847 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 7848 */       return true;
/*      */     }
/* 7850 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 7851 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7856 */     PageContext pageContext = _jspx_page_context;
/* 7857 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7859 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7860 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 7861 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7863 */     _jspx_th_c_005fout_005f15.setValue("${myfield_entity}");
/* 7864 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 7865 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 7866 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 7867 */       return true;
/*      */     }
/* 7869 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 7870 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7875 */     PageContext pageContext = _jspx_page_context;
/* 7876 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7878 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7879 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 7880 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7882 */     _jspx_th_c_005fout_005f16.setValue("${param.resourceid}");
/* 7883 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 7884 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 7885 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 7886 */       return true;
/*      */     }
/* 7888 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 7889 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7894 */     PageContext pageContext = _jspx_page_context;
/* 7895 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7897 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7898 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 7899 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7901 */     _jspx_th_c_005fout_005f17.setValue("${param.resourcename}");
/* 7902 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 7903 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 7904 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 7905 */       return true;
/*      */     }
/* 7907 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 7908 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7913 */     PageContext pageContext = _jspx_page_context;
/* 7914 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7916 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7917 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 7918 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7920 */     _jspx_th_c_005fout_005f18.setValue("${param.resourceid}");
/* 7921 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 7922 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 7923 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 7924 */       return true;
/*      */     }
/* 7926 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 7927 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7932 */     PageContext pageContext = _jspx_page_context;
/* 7933 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7935 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7936 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 7937 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7939 */     _jspx_th_c_005fout_005f19.setValue("${param.resourcename}");
/* 7940 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 7941 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 7942 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 7943 */       return true;
/*      */     }
/* 7945 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 7946 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7951 */     PageContext pageContext = _jspx_page_context;
/* 7952 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7954 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7955 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 7956 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 7958 */     _jspx_th_c_005fout_005f20.setValue("${param.resourceid}");
/* 7959 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 7960 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 7961 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 7962 */       return true;
/*      */     }
/* 7964 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 7965 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7970 */     PageContext pageContext = _jspx_page_context;
/* 7971 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7973 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7974 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 7975 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 7977 */     _jspx_th_c_005fout_005f21.setValue("${param.resourceid}");
/* 7978 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 7979 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 7980 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 7981 */       return true;
/*      */     }
/* 7983 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 7984 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7989 */     PageContext pageContext = _jspx_page_context;
/* 7990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7992 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7993 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 7994 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 7996 */     _jspx_th_c_005fout_005f22.setValue("${param.resourcename}");
/* 7997 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 7998 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 7999 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 8000 */       return true;
/*      */     }
/* 8002 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 8003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8008 */     PageContext pageContext = _jspx_page_context;
/* 8009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8011 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8012 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 8013 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 8015 */     _jspx_th_c_005fout_005f23.setValue("${param.resourceid}");
/* 8016 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 8017 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 8018 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 8019 */       return true;
/*      */     }
/* 8021 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 8022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8027 */     PageContext pageContext = _jspx_page_context;
/* 8028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8030 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8031 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 8032 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 8034 */     _jspx_th_c_005fout_005f24.setValue("${param.resourcename}");
/* 8035 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 8036 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 8037 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 8038 */       return true;
/*      */     }
/* 8040 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 8041 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8046 */     PageContext pageContext = _jspx_page_context;
/* 8047 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8049 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8050 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 8051 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fif_005f16);
/* 8052 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 8053 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 8054 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 8055 */         out = _jspx_page_context.pushBody();
/* 8056 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 8057 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8060 */         out.write("table.heading.attribute");
/* 8061 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 8062 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8065 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 8066 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8069 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 8070 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 8071 */       return true;
/*      */     }
/* 8073 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 8074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8079 */     PageContext pageContext = _jspx_page_context;
/* 8080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8082 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8083 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 8084 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f16);
/* 8085 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 8086 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 8087 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 8088 */         out = _jspx_page_context.pushBody();
/* 8089 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 8090 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8093 */         out.write("table.heading.value");
/* 8094 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 8095 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8098 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 8099 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8102 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 8103 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 8104 */       return true;
/*      */     }
/* 8106 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 8107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8112 */     PageContext pageContext = _jspx_page_context;
/* 8113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8115 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8116 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 8117 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f16);
/* 8118 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 8119 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 8120 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 8121 */         out = _jspx_page_context.pushBody();
/* 8122 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 8123 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8126 */         out.write("table.heading.status");
/* 8127 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 8128 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8131 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 8132 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8135 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 8136 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 8137 */       return true;
/*      */     }
/* 8139 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 8140 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8145 */     PageContext pageContext = _jspx_page_context;
/* 8146 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8148 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 8149 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 8150 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 8152 */     _jspx_th_c_005fif_005f17.setTest("${responsetime =='-1'}");
/* 8153 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 8154 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 8156 */         out.write("  0\n  ");
/* 8157 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 8158 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8162 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 8163 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 8164 */       return true;
/*      */     }
/* 8166 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 8167 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f0(JspTag _jspx_th_c_005fif_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8172 */     PageContext pageContext = _jspx_page_context;
/* 8173 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8175 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f0 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 8176 */     _jspx_th_fmt_005fformatNumber_005f0.setPageContext(_jspx_page_context);
/* 8177 */     _jspx_th_fmt_005fformatNumber_005f0.setParent((Tag)_jspx_th_c_005fif_005f18);
/*      */     
/* 8179 */     _jspx_th_fmt_005fformatNumber_005f0.setValue("${responsetime}");
/* 8180 */     int _jspx_eval_fmt_005fformatNumber_005f0 = _jspx_th_fmt_005fformatNumber_005f0.doStartTag();
/* 8181 */     if (_jspx_th_fmt_005fformatNumber_005f0.doEndTag() == 5) {
/* 8182 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 8183 */       return true;
/*      */     }
/* 8185 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 8186 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8191 */     PageContext pageContext = _jspx_page_context;
/* 8192 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8194 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8195 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 8196 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 8198 */     _jspx_th_c_005fout_005f25.setValue("${param.resourceid}");
/* 8199 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 8200 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 8201 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 8202 */       return true;
/*      */     }
/* 8204 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 8205 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8210 */     PageContext pageContext = _jspx_page_context;
/* 8211 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8213 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8214 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 8215 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 8217 */     _jspx_th_c_005fout_005f26.setValue("${param.resourceid}");
/* 8218 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 8219 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 8220 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 8221 */       return true;
/*      */     }
/* 8223 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 8224 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8229 */     PageContext pageContext = _jspx_page_context;
/* 8230 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8232 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8233 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 8234 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8236 */     _jspx_th_c_005fout_005f27.setValue("${param.resourceid}");
/* 8237 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 8238 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 8239 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 8240 */       return true;
/*      */     }
/* 8242 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 8243 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8248 */     PageContext pageContext = _jspx_page_context;
/* 8249 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8251 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8252 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 8253 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8255 */     _jspx_th_c_005fout_005f28.setValue("${param.resourcename}");
/* 8256 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 8257 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 8258 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 8259 */       return true;
/*      */     }
/* 8261 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 8262 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8267 */     PageContext pageContext = _jspx_page_context;
/* 8268 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8270 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8271 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 8272 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8274 */     _jspx_th_c_005fout_005f29.setValue("${param.resourceid}");
/* 8275 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 8276 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 8277 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 8278 */       return true;
/*      */     }
/* 8280 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 8281 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8286 */     PageContext pageContext = _jspx_page_context;
/* 8287 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8289 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8290 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 8291 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8293 */     _jspx_th_c_005fout_005f30.setValue("${param.resourcename}");
/* 8294 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 8295 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 8296 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 8297 */       return true;
/*      */     }
/* 8299 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 8300 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8305 */     PageContext pageContext = _jspx_page_context;
/* 8306 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8308 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8309 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 8310 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8312 */     _jspx_th_c_005fout_005f31.setValue("${param.resourceid}");
/* 8313 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 8314 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 8315 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 8316 */       return true;
/*      */     }
/* 8318 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 8319 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8324 */     PageContext pageContext = _jspx_page_context;
/* 8325 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8327 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8328 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 8329 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8331 */     _jspx_th_c_005fout_005f32.setValue("${param.resourcename}");
/* 8332 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 8333 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 8334 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 8335 */       return true;
/*      */     }
/* 8337 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 8338 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8343 */     PageContext pageContext = _jspx_page_context;
/* 8344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8346 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8347 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 8348 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8350 */     _jspx_th_c_005fout_005f33.setValue("${param.resourceid}");
/* 8351 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 8352 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 8353 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 8354 */       return true;
/*      */     }
/* 8356 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 8357 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8362 */     PageContext pageContext = _jspx_page_context;
/* 8363 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8365 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8366 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 8367 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8369 */     _jspx_th_c_005fout_005f34.setValue("${param.resourcename}");
/* 8370 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 8371 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 8372 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 8373 */       return true;
/*      */     }
/* 8375 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 8376 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8381 */     PageContext pageContext = _jspx_page_context;
/* 8382 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8384 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8385 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 8386 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fif_005f19);
/* 8387 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 8388 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 8389 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 8390 */         out = _jspx_page_context.pushBody();
/* 8391 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 8392 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8395 */         out.write("table.heading.attribute");
/* 8396 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 8397 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8400 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 8401 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8404 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 8405 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 8406 */       return true;
/*      */     }
/* 8408 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 8409 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8414 */     PageContext pageContext = _jspx_page_context;
/* 8415 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8417 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8418 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 8419 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fif_005f19);
/* 8420 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 8421 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 8422 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 8423 */         out = _jspx_page_context.pushBody();
/* 8424 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 8425 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8428 */         out.write("table.heading.value");
/* 8429 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 8430 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8433 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 8434 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8437 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 8438 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 8439 */       return true;
/*      */     }
/* 8441 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 8442 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f1(JspTag _jspx_th_c_005fif_005f20, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8447 */     PageContext pageContext = _jspx_page_context;
/* 8448 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8450 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f1 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 8451 */     _jspx_th_fmt_005fformatNumber_005f1.setPageContext(_jspx_page_context);
/* 8452 */     _jspx_th_fmt_005fformatNumber_005f1.setParent((Tag)_jspx_th_c_005fif_005f20);
/*      */     
/* 8454 */     _jspx_th_fmt_005fformatNumber_005f1.setValue("${heapsizecurrent}");
/* 8455 */     int _jspx_eval_fmt_005fformatNumber_005f1 = _jspx_th_fmt_005fformatNumber_005f1.doStartTag();
/* 8456 */     if (_jspx_th_fmt_005fformatNumber_005f1.doEndTag() == 5) {
/* 8457 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 8458 */       return true;
/*      */     }
/* 8460 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 8461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8466 */     PageContext pageContext = _jspx_page_context;
/* 8467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8469 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8470 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 8471 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8473 */     _jspx_th_c_005fout_005f35.setValue("${param.resourceid}");
/* 8474 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 8475 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 8476 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 8477 */       return true;
/*      */     }
/* 8479 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 8480 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8485 */     PageContext pageContext = _jspx_page_context;
/* 8486 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8488 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8489 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 8490 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fif_005f19);
/* 8491 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 8492 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 8493 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 8494 */         out = _jspx_page_context.pushBody();
/* 8495 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 8496 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8499 */         out.write("table.heading.attribute");
/* 8500 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 8501 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8504 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 8505 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8508 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 8509 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 8510 */       return true;
/*      */     }
/* 8512 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 8513 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8518 */     PageContext pageContext = _jspx_page_context;
/* 8519 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8521 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 8522 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 8523 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fif_005f19);
/* 8524 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 8525 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 8526 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 8527 */         out = _jspx_page_context.pushBody();
/* 8528 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 8529 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8532 */         out.write("table.heading.value");
/* 8533 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 8534 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8537 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 8538 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8541 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 8542 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 8543 */       return true;
/*      */     }
/* 8545 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 8546 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f2(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8551 */     PageContext pageContext = _jspx_page_context;
/* 8552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8554 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f2 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 8555 */     _jspx_th_fmt_005fformatNumber_005f2.setPageContext(_jspx_page_context);
/* 8556 */     _jspx_th_fmt_005fformatNumber_005f2.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8558 */     _jspx_th_fmt_005fformatNumber_005f2.setValue("${responsetimestats.min}");
/* 8559 */     int _jspx_eval_fmt_005fformatNumber_005f2 = _jspx_th_fmt_005fformatNumber_005f2.doStartTag();
/* 8560 */     if (_jspx_th_fmt_005fformatNumber_005f2.doEndTag() == 5) {
/* 8561 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f2);
/* 8562 */       return true;
/*      */     }
/* 8564 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f2);
/* 8565 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f3(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8570 */     PageContext pageContext = _jspx_page_context;
/* 8571 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8573 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f3 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 8574 */     _jspx_th_fmt_005fformatNumber_005f3.setPageContext(_jspx_page_context);
/* 8575 */     _jspx_th_fmt_005fformatNumber_005f3.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8577 */     _jspx_th_fmt_005fformatNumber_005f3.setValue("${responsetimestats.max}");
/* 8578 */     int _jspx_eval_fmt_005fformatNumber_005f3 = _jspx_th_fmt_005fformatNumber_005f3.doStartTag();
/* 8579 */     if (_jspx_th_fmt_005fformatNumber_005f3.doEndTag() == 5) {
/* 8580 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f3);
/* 8581 */       return true;
/*      */     }
/* 8583 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f3);
/* 8584 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f4(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8589 */     PageContext pageContext = _jspx_page_context;
/* 8590 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8592 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f4 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 8593 */     _jspx_th_fmt_005fformatNumber_005f4.setPageContext(_jspx_page_context);
/* 8594 */     _jspx_th_fmt_005fformatNumber_005f4.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8596 */     _jspx_th_fmt_005fformatNumber_005f4.setValue("${responsetimestats.avg}");
/* 8597 */     int _jspx_eval_fmt_005fformatNumber_005f4 = _jspx_th_fmt_005fformatNumber_005f4.doStartTag();
/* 8598 */     if (_jspx_th_fmt_005fformatNumber_005f4.doEndTag() == 5) {
/* 8599 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f4);
/* 8600 */       return true;
/*      */     }
/* 8602 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f4);
/* 8603 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f22(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8608 */     PageContext pageContext = _jspx_page_context;
/* 8609 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8611 */     IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 8612 */     _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 8613 */     _jspx_th_c_005fif_005f22.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8615 */     _jspx_th_c_005fif_005f22.setTest("${responsetime =='-1'}");
/* 8616 */     int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 8617 */     if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */       for (;;) {
/* 8619 */         out.write("\n\t\t<td class=\"yellowgrayborder\">0\n\t");
/* 8620 */         int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 8621 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8625 */     if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 8626 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 8627 */       return true;
/*      */     }
/* 8629 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 8630 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f5(JspTag _jspx_th_c_005fif_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8635 */     PageContext pageContext = _jspx_page_context;
/* 8636 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8638 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f5 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 8639 */     _jspx_th_fmt_005fformatNumber_005f5.setPageContext(_jspx_page_context);
/* 8640 */     _jspx_th_fmt_005fformatNumber_005f5.setParent((Tag)_jspx_th_c_005fif_005f23);
/*      */     
/* 8642 */     _jspx_th_fmt_005fformatNumber_005f5.setValue("${responsetime}");
/* 8643 */     int _jspx_eval_fmt_005fformatNumber_005f5 = _jspx_th_fmt_005fformatNumber_005f5.doStartTag();
/* 8644 */     if (_jspx_th_fmt_005fformatNumber_005f5.doEndTag() == 5) {
/* 8645 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f5);
/* 8646 */       return true;
/*      */     }
/* 8648 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f5);
/* 8649 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8654 */     PageContext pageContext = _jspx_page_context;
/* 8655 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8657 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8658 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 8659 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8661 */     _jspx_th_c_005fout_005f36.setValue("${param.resourceid}");
/* 8662 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 8663 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 8664 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 8665 */       return true;
/*      */     }
/* 8667 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 8668 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8673 */     PageContext pageContext = _jspx_page_context;
/* 8674 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8676 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8677 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 8678 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 8680 */     _jspx_th_c_005fout_005f37.setValue("${param.resourceid}");
/* 8681 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 8682 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 8683 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 8684 */       return true;
/*      */     }
/* 8686 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 8687 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8692 */     PageContext pageContext = _jspx_page_context;
/* 8693 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8695 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8696 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 8697 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 8699 */     _jspx_th_c_005fout_005f38.setValue("${fullvalue}");
/* 8700 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 8701 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 8702 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 8703 */       return true;
/*      */     }
/* 8705 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 8706 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8711 */     PageContext pageContext = _jspx_page_context;
/* 8712 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8714 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8715 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 8716 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 8718 */     _jspx_th_c_005fout_005f39.setValue("${fullvalue}");
/* 8719 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 8720 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 8721 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 8722 */       return true;
/*      */     }
/* 8724 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 8725 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f24(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8730 */     PageContext pageContext = _jspx_page_context;
/* 8731 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8733 */     IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 8734 */     _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 8735 */     _jspx_th_c_005fif_005f24.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 8737 */     _jspx_th_c_005fif_005f24.setTest("${not empty param.haid}");
/* 8738 */     int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 8739 */     if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */       for (;;) {
/* 8741 */         out.write(10);
/* 8742 */         out.write(9);
/* 8743 */         out.write(9);
/* 8744 */         if (_jspx_meth_c_005fset_005f9(_jspx_th_c_005fif_005f24, _jspx_page_context))
/* 8745 */           return true;
/* 8746 */         out.write(10);
/* 8747 */         out.write(9);
/* 8748 */         out.write(9);
/* 8749 */         int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 8750 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8754 */     if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 8755 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 8756 */       return true;
/*      */     }
/* 8758 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 8759 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f9(JspTag _jspx_th_c_005fif_005f24, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8764 */     PageContext pageContext = _jspx_page_context;
/* 8765 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8767 */     SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 8768 */     _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 8769 */     _jspx_th_c_005fset_005f9.setParent((Tag)_jspx_th_c_005fif_005f24);
/*      */     
/* 8771 */     _jspx_th_c_005fset_005f9.setVar("myfield_paramresid");
/*      */     
/* 8773 */     _jspx_th_c_005fset_005f9.setScope("page");
/* 8774 */     int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 8775 */     if (_jspx_eval_c_005fset_005f9 != 0) {
/* 8776 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 8777 */         out = _jspx_page_context.pushBody();
/* 8778 */         _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 8779 */         _jspx_th_c_005fset_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8782 */         if (_jspx_meth_c_005fout_005f40(_jspx_th_c_005fset_005f9, _jspx_page_context))
/* 8783 */           return true;
/* 8784 */         int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 8785 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8788 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 8789 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8792 */     if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 8793 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f9);
/* 8794 */       return true;
/*      */     }
/* 8796 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f9);
/* 8797 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_c_005fset_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8802 */     PageContext pageContext = _jspx_page_context;
/* 8803 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8805 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8806 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 8807 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_c_005fset_005f9);
/*      */     
/* 8809 */     _jspx_th_c_005fout_005f40.setValue("${param.haid}");
/* 8810 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 8811 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 8812 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 8813 */       return true;
/*      */     }
/* 8815 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 8816 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f25(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8821 */     PageContext pageContext = _jspx_page_context;
/* 8822 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8824 */     IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 8825 */     _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 8826 */     _jspx_th_c_005fif_005f25.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 8828 */     _jspx_th_c_005fif_005f25.setTest("${not empty param.resourceid}");
/* 8829 */     int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 8830 */     if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */       for (;;) {
/* 8832 */         out.write(10);
/* 8833 */         out.write(9);
/* 8834 */         out.write(9);
/* 8835 */         if (_jspx_meth_c_005fset_005f10(_jspx_th_c_005fif_005f25, _jspx_page_context))
/* 8836 */           return true;
/* 8837 */         out.write(10);
/* 8838 */         out.write(9);
/* 8839 */         out.write(9);
/* 8840 */         int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 8841 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8845 */     if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 8846 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 8847 */       return true;
/*      */     }
/* 8849 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 8850 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f10(JspTag _jspx_th_c_005fif_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8855 */     PageContext pageContext = _jspx_page_context;
/* 8856 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8858 */     SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 8859 */     _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 8860 */     _jspx_th_c_005fset_005f10.setParent((Tag)_jspx_th_c_005fif_005f25);
/*      */     
/* 8862 */     _jspx_th_c_005fset_005f10.setVar("myfield_paramresid");
/*      */     
/* 8864 */     _jspx_th_c_005fset_005f10.setScope("page");
/* 8865 */     int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 8866 */     if (_jspx_eval_c_005fset_005f10 != 0) {
/* 8867 */       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 8868 */         out = _jspx_page_context.pushBody();
/* 8869 */         _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 8870 */         _jspx_th_c_005fset_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8873 */         if (_jspx_meth_c_005fout_005f41(_jspx_th_c_005fset_005f10, _jspx_page_context))
/* 8874 */           return true;
/* 8875 */         int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 8876 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8879 */       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 8880 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8883 */     if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 8884 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f10);
/* 8885 */       return true;
/*      */     }
/* 8887 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f10);
/* 8888 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_c_005fset_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8893 */     PageContext pageContext = _jspx_page_context;
/* 8894 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8896 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8897 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 8898 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_c_005fset_005f10);
/*      */     
/* 8900 */     _jspx_th_c_005fout_005f41.setValue("${param.resourceid}");
/* 8901 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 8902 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 8903 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 8904 */       return true;
/*      */     }
/* 8906 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 8907 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8912 */     PageContext pageContext = _jspx_page_context;
/* 8913 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8915 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8916 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 8917 */     _jspx_th_c_005fout_005f42.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 8919 */     _jspx_th_c_005fout_005f42.setValue("${myfield_paramresid}");
/* 8920 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 8921 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 8922 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 8923 */       return true;
/*      */     }
/* 8925 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 8926 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f6(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8931 */     PageContext pageContext = _jspx_page_context;
/* 8932 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8934 */     PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 8935 */     _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 8936 */     _jspx_th_logic_005fpresent_005f6.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 8938 */     _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 8939 */     int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 8940 */     if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */       for (;;) {
/* 8942 */         out.write("\n\t\t<a href=\"javascript:alertUser()\"><img src=\"/images/icon_executeaction.gif\"  border=\"0\"></a></td>\n\t\t");
/* 8943 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 8944 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8948 */     if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 8949 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 8950 */       return true;
/*      */     }
/* 8952 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 8953 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8958 */     PageContext pageContext = _jspx_page_context;
/* 8959 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8961 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 8962 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 8963 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 8964 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 8965 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 8967 */         out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td align=\"center\" class=\"bodytextbold11\"><a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 8968 */         if (_jspx_meth_c_005fout_005f43(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 8969 */           return true;
/* 8970 */         if (_jspx_meth_c_005fif_005f27(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 8971 */           return true;
/* 8972 */         out.write("\" class=\"staticlinks\">Goto Snapshot View</a></td>\n  </tr>\n</table>\n");
/* 8973 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 8974 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8978 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 8979 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 8980 */       return true;
/*      */     }
/* 8982 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 8983 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f43(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8988 */     PageContext pageContext = _jspx_page_context;
/* 8989 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8991 */     OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8992 */     _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 8993 */     _jspx_th_c_005fout_005f43.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 8995 */     _jspx_th_c_005fout_005f43.setValue("${param.resourceid}");
/* 8996 */     int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 8997 */     if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 8998 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 8999 */       return true;
/*      */     }
/* 9001 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 9002 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f27(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9007 */     PageContext pageContext = _jspx_page_context;
/* 9008 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9010 */     IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 9011 */     _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/* 9012 */     _jspx_th_c_005fif_005f27.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 9014 */     _jspx_th_c_005fif_005f27.setTest("${ !empty param.haid && param.haid!='null' }");
/* 9015 */     int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/* 9016 */     if (_jspx_eval_c_005fif_005f27 != 0) {
/*      */       for (;;) {
/* 9018 */         out.write("&haid=");
/* 9019 */         if (_jspx_meth_c_005fout_005f44(_jspx_th_c_005fif_005f27, _jspx_page_context))
/* 9020 */           return true;
/* 9021 */         int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/* 9022 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 9026 */     if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/* 9027 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 9028 */       return true;
/*      */     }
/* 9030 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 9031 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f44(JspTag _jspx_th_c_005fif_005f27, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9036 */     PageContext pageContext = _jspx_page_context;
/* 9037 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9039 */     OutTag _jspx_th_c_005fout_005f44 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9040 */     _jspx_th_c_005fout_005f44.setPageContext(_jspx_page_context);
/* 9041 */     _jspx_th_c_005fout_005f44.setParent((Tag)_jspx_th_c_005fif_005f27);
/*      */     
/* 9043 */     _jspx_th_c_005fout_005f44.setValue("${param.haid}");
/* 9044 */     int _jspx_eval_c_005fout_005f44 = _jspx_th_c_005fout_005f44.doStartTag();
/* 9045 */     if (_jspx_th_c_005fout_005f44.doEndTag() == 5) {
/* 9046 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 9047 */       return true;
/*      */     }
/* 9049 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 9050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9055 */     PageContext pageContext = _jspx_page_context;
/* 9056 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9058 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 9059 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 9060 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 9062 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 9064 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 9065 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 9066 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 9067 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 9068 */       return true;
/*      */     }
/* 9070 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 9071 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f45(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9076 */     PageContext pageContext = _jspx_page_context;
/* 9077 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9079 */     OutTag _jspx_th_c_005fout_005f45 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9080 */     _jspx_th_c_005fout_005f45.setPageContext(_jspx_page_context);
/* 9081 */     _jspx_th_c_005fout_005f45.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 9083 */     _jspx_th_c_005fout_005f45.setValue("${param.resourceid}");
/* 9084 */     int _jspx_eval_c_005fout_005f45 = _jspx_th_c_005fout_005f45.doStartTag();
/* 9085 */     if (_jspx_th_c_005fout_005f45.doEndTag() == 5) {
/* 9086 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 9087 */       return true;
/*      */     }
/* 9089 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 9090 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f8(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9095 */     PageContext pageContext = _jspx_page_context;
/* 9096 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9098 */     PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 9099 */     _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 9100 */     _jspx_th_logic_005fpresent_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 9102 */     _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 9103 */     int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 9104 */     if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */       for (;;) {
/* 9106 */         out.write("\n\t\t\t alertUser();\n\t\t \treturn;\n\t\t");
/* 9107 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 9108 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 9112 */     if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 9113 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 9114 */       return true;
/*      */     }
/* 9116 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 9117 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f46(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9122 */     PageContext pageContext = _jspx_page_context;
/* 9123 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9125 */     OutTag _jspx_th_c_005fout_005f46 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9126 */     _jspx_th_c_005fout_005f46.setPageContext(_jspx_page_context);
/* 9127 */     _jspx_th_c_005fout_005f46.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 9129 */     _jspx_th_c_005fout_005f46.setValue("${param.resourceid}");
/* 9130 */     int _jspx_eval_c_005fout_005f46 = _jspx_th_c_005fout_005f46.doStartTag();
/* 9131 */     if (_jspx_th_c_005fout_005f46.doEndTag() == 5) {
/* 9132 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 9133 */       return true;
/*      */     }
/* 9135 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 9136 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f47(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9141 */     PageContext pageContext = _jspx_page_context;
/* 9142 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9144 */     OutTag _jspx_th_c_005fout_005f47 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9145 */     _jspx_th_c_005fout_005f47.setPageContext(_jspx_page_context);
/* 9146 */     _jspx_th_c_005fout_005f47.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 9148 */     _jspx_th_c_005fout_005f47.setValue("${param.resourceid}");
/* 9149 */     int _jspx_eval_c_005fout_005f47 = _jspx_th_c_005fout_005f47.doStartTag();
/* 9150 */     if (_jspx_th_c_005fout_005f47.doEndTag() == 5) {
/* 9151 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 9152 */       return true;
/*      */     }
/* 9154 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 9155 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f48(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9160 */     PageContext pageContext = _jspx_page_context;
/* 9161 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9163 */     OutTag _jspx_th_c_005fout_005f48 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9164 */     _jspx_th_c_005fout_005f48.setPageContext(_jspx_page_context);
/* 9165 */     _jspx_th_c_005fout_005f48.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 9167 */     _jspx_th_c_005fout_005f48.setValue("${param.resourceid}");
/* 9168 */     int _jspx_eval_c_005fout_005f48 = _jspx_th_c_005fout_005f48.doStartTag();
/* 9169 */     if (_jspx_th_c_005fout_005f48.doEndTag() == 5) {
/* 9170 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 9171 */       return true;
/*      */     }
/* 9173 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 9174 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f49(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9179 */     PageContext pageContext = _jspx_page_context;
/* 9180 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9182 */     OutTag _jspx_th_c_005fout_005f49 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9183 */     _jspx_th_c_005fout_005f49.setPageContext(_jspx_page_context);
/* 9184 */     _jspx_th_c_005fout_005f49.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 9186 */     _jspx_th_c_005fout_005f49.setValue("${param.resourceid}");
/* 9187 */     int _jspx_eval_c_005fout_005f49 = _jspx_th_c_005fout_005f49.doStartTag();
/* 9188 */     if (_jspx_th_c_005fout_005f49.doEndTag() == 5) {
/* 9189 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 9190 */       return true;
/*      */     }
/* 9192 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 9193 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f50(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9198 */     PageContext pageContext = _jspx_page_context;
/* 9199 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9201 */     OutTag _jspx_th_c_005fout_005f50 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9202 */     _jspx_th_c_005fout_005f50.setPageContext(_jspx_page_context);
/* 9203 */     _jspx_th_c_005fout_005f50.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 9205 */     _jspx_th_c_005fout_005f50.setValue("${param.haid}");
/* 9206 */     int _jspx_eval_c_005fout_005f50 = _jspx_th_c_005fout_005f50.doStartTag();
/* 9207 */     if (_jspx_th_c_005fout_005f50.doEndTag() == 5) {
/* 9208 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 9209 */       return true;
/*      */     }
/* 9211 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 9212 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f51(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9217 */     PageContext pageContext = _jspx_page_context;
/* 9218 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9220 */     OutTag _jspx_th_c_005fout_005f51 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9221 */     _jspx_th_c_005fout_005f51.setPageContext(_jspx_page_context);
/* 9222 */     _jspx_th_c_005fout_005f51.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 9224 */     _jspx_th_c_005fout_005f51.setValue("${param.resourceid}");
/* 9225 */     int _jspx_eval_c_005fout_005f51 = _jspx_th_c_005fout_005f51.doStartTag();
/* 9226 */     if (_jspx_th_c_005fout_005f51.doEndTag() == 5) {
/* 9227 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 9228 */       return true;
/*      */     }
/* 9230 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 9231 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f52(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9236 */     PageContext pageContext = _jspx_page_context;
/* 9237 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9239 */     OutTag _jspx_th_c_005fout_005f52 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9240 */     _jspx_th_c_005fout_005f52.setPageContext(_jspx_page_context);
/* 9241 */     _jspx_th_c_005fout_005f52.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 9243 */     _jspx_th_c_005fout_005f52.setValue("${param.resourceid}");
/* 9244 */     int _jspx_eval_c_005fout_005f52 = _jspx_th_c_005fout_005f52.doStartTag();
/* 9245 */     if (_jspx_th_c_005fout_005f52.doEndTag() == 5) {
/* 9246 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 9247 */       return true;
/*      */     }
/* 9249 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 9250 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f53(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9255 */     PageContext pageContext = _jspx_page_context;
/* 9256 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9258 */     OutTag _jspx_th_c_005fout_005f53 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9259 */     _jspx_th_c_005fout_005f53.setPageContext(_jspx_page_context);
/* 9260 */     _jspx_th_c_005fout_005f53.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 9262 */     _jspx_th_c_005fout_005f53.setValue("${param.resourceid}");
/* 9263 */     int _jspx_eval_c_005fout_005f53 = _jspx_th_c_005fout_005f53.doStartTag();
/* 9264 */     if (_jspx_th_c_005fout_005f53.doEndTag() == 5) {
/* 9265 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 9266 */       return true;
/*      */     }
/* 9268 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 9269 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f54(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9274 */     PageContext pageContext = _jspx_page_context;
/* 9275 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9277 */     OutTag _jspx_th_c_005fout_005f54 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9278 */     _jspx_th_c_005fout_005f54.setPageContext(_jspx_page_context);
/* 9279 */     _jspx_th_c_005fout_005f54.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 9281 */     _jspx_th_c_005fout_005f54.setValue("${param.resourceid}");
/* 9282 */     int _jspx_eval_c_005fout_005f54 = _jspx_th_c_005fout_005f54.doStartTag();
/* 9283 */     if (_jspx_th_c_005fout_005f54.doEndTag() == 5) {
/* 9284 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 9285 */       return true;
/*      */     }
/* 9287 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 9288 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f34(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9293 */     PageContext pageContext = _jspx_page_context;
/* 9294 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9296 */     IfTag _jspx_th_c_005fif_005f34 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 9297 */     _jspx_th_c_005fif_005f34.setPageContext(_jspx_page_context);
/* 9298 */     _jspx_th_c_005fif_005f34.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 9300 */     _jspx_th_c_005fif_005f34.setTest("${!empty wtaresourceid}");
/* 9301 */     int _jspx_eval_c_005fif_005f34 = _jspx_th_c_005fif_005f34.doStartTag();
/* 9302 */     if (_jspx_eval_c_005fif_005f34 != 0) {
/*      */       for (;;) {
/* 9304 */         out.write("\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td colspan=\"2\"><a href=\"/showresource.do?type=WTA&method=showdetails&resourceid=");
/* 9305 */         if (_jspx_meth_c_005fout_005f55(_jspx_th_c_005fif_005f34, _jspx_page_context))
/* 9306 */           return true;
/* 9307 */         out.write("\" class=\"new-left-links\">Web Transactions</a> ");
/* 9308 */         out.write("\n </td>\n  </tr>\n  ");
/* 9309 */         int evalDoAfterBody = _jspx_th_c_005fif_005f34.doAfterBody();
/* 9310 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 9314 */     if (_jspx_th_c_005fif_005f34.doEndTag() == 5) {
/* 9315 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34);
/* 9316 */       return true;
/*      */     }
/* 9318 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34);
/* 9319 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f55(JspTag _jspx_th_c_005fif_005f34, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9324 */     PageContext pageContext = _jspx_page_context;
/* 9325 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9327 */     OutTag _jspx_th_c_005fout_005f55 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9328 */     _jspx_th_c_005fout_005f55.setPageContext(_jspx_page_context);
/* 9329 */     _jspx_th_c_005fout_005f55.setParent((Tag)_jspx_th_c_005fif_005f34);
/*      */     
/* 9331 */     _jspx_th_c_005fout_005f55.setValue("${wtaresourceid}");
/* 9332 */     int _jspx_eval_c_005fout_005f55 = _jspx_th_c_005fout_005f55.doStartTag();
/* 9333 */     if (_jspx_th_c_005fout_005f55.doEndTag() == 5) {
/* 9334 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 9335 */       return true;
/*      */     }
/* 9337 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 9338 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f56(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 9343 */     PageContext pageContext = _jspx_page_context;
/* 9344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9346 */     OutTag _jspx_th_c_005fout_005f56 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9347 */     _jspx_th_c_005fout_005f56.setPageContext(_jspx_page_context);
/* 9348 */     _jspx_th_c_005fout_005f56.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 9350 */     _jspx_th_c_005fout_005f56.setValue("${ha.key}");
/* 9351 */     int _jspx_eval_c_005fout_005f56 = _jspx_th_c_005fout_005f56.doStartTag();
/* 9352 */     if (_jspx_th_c_005fout_005f56.doEndTag() == 5) {
/* 9353 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f56);
/* 9354 */       return true;
/*      */     }
/* 9356 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f56);
/* 9357 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f57(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 9362 */     PageContext pageContext = _jspx_page_context;
/* 9363 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9365 */     OutTag _jspx_th_c_005fout_005f57 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9366 */     _jspx_th_c_005fout_005f57.setPageContext(_jspx_page_context);
/* 9367 */     _jspx_th_c_005fout_005f57.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 9369 */     _jspx_th_c_005fout_005f57.setValue("${ha.value}");
/* 9370 */     int _jspx_eval_c_005fout_005f57 = _jspx_th_c_005fout_005f57.doStartTag();
/* 9371 */     if (_jspx_th_c_005fout_005f57.doEndTag() == 5) {
/* 9372 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f57);
/* 9373 */       return true;
/*      */     }
/* 9375 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f57);
/* 9376 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f11(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 9381 */     PageContext pageContext = _jspx_page_context;
/* 9382 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9384 */     SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 9385 */     _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 9386 */     _jspx_th_c_005fset_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 9388 */     _jspx_th_c_005fset_005f11.setVar("monitorName");
/* 9389 */     int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 9390 */     if (_jspx_eval_c_005fset_005f11 != 0) {
/* 9391 */       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 9392 */         out = _jspx_page_context.pushBody();
/* 9393 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 9394 */         _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 9395 */         _jspx_th_c_005fset_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 9398 */         if (_jspx_meth_c_005fout_005f58(_jspx_th_c_005fset_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 9399 */           return true;
/* 9400 */         int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 9401 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 9404 */       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 9405 */         out = _jspx_page_context.popBody();
/* 9406 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 9409 */     if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 9410 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 9411 */       return true;
/*      */     }
/* 9413 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 9414 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f58(JspTag _jspx_th_c_005fset_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 9419 */     PageContext pageContext = _jspx_page_context;
/* 9420 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9422 */     OutTag _jspx_th_c_005fout_005f58 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9423 */     _jspx_th_c_005fout_005f58.setPageContext(_jspx_page_context);
/* 9424 */     _jspx_th_c_005fout_005f58.setParent((Tag)_jspx_th_c_005fset_005f11);
/*      */     
/* 9426 */     _jspx_th_c_005fout_005f58.setValue("${ha.value}");
/* 9427 */     int _jspx_eval_c_005fout_005f58 = _jspx_th_c_005fout_005f58.doStartTag();
/* 9428 */     if (_jspx_th_c_005fout_005f58.doEndTag() == 5) {
/* 9429 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f58);
/* 9430 */       return true;
/*      */     }
/* 9432 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f58);
/* 9433 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f59(JspTag _jspx_th_logic_005fpresent_005f12, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 9438 */     PageContext pageContext = _jspx_page_context;
/* 9439 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9441 */     OutTag _jspx_th_c_005fout_005f59 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9442 */     _jspx_th_c_005fout_005f59.setPageContext(_jspx_page_context);
/* 9443 */     _jspx_th_c_005fout_005f59.setParent((Tag)_jspx_th_logic_005fpresent_005f12);
/*      */     
/* 9445 */     _jspx_th_c_005fout_005f59.setValue("${ha.key}");
/* 9446 */     int _jspx_eval_c_005fout_005f59 = _jspx_th_c_005fout_005f59.doStartTag();
/* 9447 */     if (_jspx_th_c_005fout_005f59.doEndTag() == 5) {
/* 9448 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f59);
/* 9449 */       return true;
/*      */     }
/* 9451 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f59);
/* 9452 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fif_005f37, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9457 */     PageContext pageContext = _jspx_page_context;
/* 9458 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9460 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 9461 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 9462 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fif_005f37);
/*      */     
/* 9464 */     _jspx_th_fmt_005fmessage_005f8.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 9465 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 9466 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 9467 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 9468 */       return true;
/*      */     }
/* 9470 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 9471 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f60(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 9476 */     PageContext pageContext = _jspx_page_context;
/* 9477 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9479 */     OutTag _jspx_th_c_005fout_005f60 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9480 */     _jspx_th_c_005fout_005f60.setPageContext(_jspx_page_context);
/* 9481 */     _jspx_th_c_005fout_005f60.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 9483 */     _jspx_th_c_005fout_005f60.setValue("${ha.key}");
/* 9484 */     int _jspx_eval_c_005fout_005f60 = _jspx_th_c_005fout_005f60.doStartTag();
/* 9485 */     if (_jspx_th_c_005fout_005f60.doEndTag() == 5) {
/* 9486 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f60);
/* 9487 */       return true;
/*      */     }
/* 9489 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f60);
/* 9490 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f61(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 9495 */     PageContext pageContext = _jspx_page_context;
/* 9496 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9498 */     OutTag _jspx_th_c_005fout_005f61 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9499 */     _jspx_th_c_005fout_005f61.setPageContext(_jspx_page_context);
/* 9500 */     _jspx_th_c_005fout_005f61.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 9502 */     _jspx_th_c_005fout_005f61.setValue("${ha.value}");
/* 9503 */     int _jspx_eval_c_005fout_005f61 = _jspx_th_c_005fout_005f61.doStartTag();
/* 9504 */     if (_jspx_th_c_005fout_005f61.doEndTag() == 5) {
/* 9505 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f61);
/* 9506 */       return true;
/*      */     }
/* 9508 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f61);
/* 9509 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f12(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 9514 */     PageContext pageContext = _jspx_page_context;
/* 9515 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9517 */     SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 9518 */     _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 9519 */     _jspx_th_c_005fset_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 9521 */     _jspx_th_c_005fset_005f12.setVar("monitorName");
/* 9522 */     int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 9523 */     if (_jspx_eval_c_005fset_005f12 != 0) {
/* 9524 */       if (_jspx_eval_c_005fset_005f12 != 1) {
/* 9525 */         out = _jspx_page_context.pushBody();
/* 9526 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 9527 */         _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 9528 */         _jspx_th_c_005fset_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 9531 */         if (_jspx_meth_c_005fout_005f62(_jspx_th_c_005fset_005f12, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 9532 */           return true;
/* 9533 */         int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 9534 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 9537 */       if (_jspx_eval_c_005fset_005f12 != 1) {
/* 9538 */         out = _jspx_page_context.popBody();
/* 9539 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 9542 */     if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 9543 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 9544 */       return true;
/*      */     }
/* 9546 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 9547 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f62(JspTag _jspx_th_c_005fset_005f12, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 9552 */     PageContext pageContext = _jspx_page_context;
/* 9553 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9555 */     OutTag _jspx_th_c_005fout_005f62 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9556 */     _jspx_th_c_005fout_005f62.setPageContext(_jspx_page_context);
/* 9557 */     _jspx_th_c_005fout_005f62.setParent((Tag)_jspx_th_c_005fset_005f12);
/*      */     
/* 9559 */     _jspx_th_c_005fout_005f62.setValue("${ha.value}");
/* 9560 */     int _jspx_eval_c_005fout_005f62 = _jspx_th_c_005fout_005f62.doStartTag();
/* 9561 */     if (_jspx_th_c_005fout_005f62.doEndTag() == 5) {
/* 9562 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f62);
/* 9563 */       return true;
/*      */     }
/* 9565 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f62);
/* 9566 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f63(JspTag _jspx_th_logic_005fpresent_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 9571 */     PageContext pageContext = _jspx_page_context;
/* 9572 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9574 */     OutTag _jspx_th_c_005fout_005f63 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 9575 */     _jspx_th_c_005fout_005f63.setPageContext(_jspx_page_context);
/* 9576 */     _jspx_th_c_005fout_005f63.setParent((Tag)_jspx_th_logic_005fpresent_005f13);
/*      */     
/* 9578 */     _jspx_th_c_005fout_005f63.setValue("${ha.key}");
/* 9579 */     int _jspx_eval_c_005fout_005f63 = _jspx_th_c_005fout_005f63.doStartTag();
/* 9580 */     if (_jspx_th_c_005fout_005f63.doEndTag() == 5) {
/* 9581 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f63);
/* 9582 */       return true;
/*      */     }
/* 9584 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f63);
/* 9585 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_logic_005fpresent_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 9590 */     PageContext pageContext = _jspx_page_context;
/* 9591 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9593 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 9594 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 9595 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_logic_005fpresent_005f13);
/*      */     
/* 9597 */     _jspx_th_fmt_005fmessage_005f9.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 9598 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 9599 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 9600 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 9601 */       return true;
/*      */     }
/* 9603 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 9604 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f38(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9609 */     PageContext pageContext = _jspx_page_context;
/* 9610 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9612 */     IfTag _jspx_th_c_005fif_005f38 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 9613 */     _jspx_th_c_005fif_005f38.setPageContext(_jspx_page_context);
/* 9614 */     _jspx_th_c_005fif_005f38.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 9616 */     _jspx_th_c_005fif_005f38.setTest("${empty associatedmgs}");
/* 9617 */     int _jspx_eval_c_005fif_005f38 = _jspx_th_c_005fif_005f38.doStartTag();
/* 9618 */     if (_jspx_eval_c_005fif_005f38 != 0) {
/*      */       for (;;) {
/* 9620 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 9621 */         if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_c_005fif_005f38, _jspx_page_context))
/* 9622 */           return true;
/* 9623 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 9624 */         if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_c_005fif_005f38, _jspx_page_context))
/* 9625 */           return true;
/* 9626 */         out.write("</td>\n\t ");
/* 9627 */         int evalDoAfterBody = _jspx_th_c_005fif_005f38.doAfterBody();
/* 9628 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 9632 */     if (_jspx_th_c_005fif_005f38.doEndTag() == 5) {
/* 9633 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f38);
/* 9634 */       return true;
/*      */     }
/* 9636 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f38);
/* 9637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_c_005fif_005f38, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9642 */     PageContext pageContext = _jspx_page_context;
/* 9643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9645 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 9646 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 9647 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_c_005fif_005f38);
/*      */     
/* 9649 */     _jspx_th_fmt_005fmessage_005f10.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 9650 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 9651 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 9652 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 9653 */       return true;
/*      */     }
/* 9655 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 9656 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_c_005fif_005f38, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9661 */     PageContext pageContext = _jspx_page_context;
/* 9662 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9664 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 9665 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 9666 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_c_005fif_005f38);
/*      */     
/* 9668 */     _jspx_th_fmt_005fmessage_005f11.setKey("am.webclient.urlmonitor.none.text");
/* 9669 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 9670 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 9671 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 9672 */       return true;
/*      */     }
/* 9674 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 9675 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 9680 */     PageContext pageContext = _jspx_page_context;
/* 9681 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 9683 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 9684 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 9685 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 9687 */     _jspx_th_fmt_005fmessage_005f12.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 9688 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 9689 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 9690 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 9691 */       return true;
/*      */     }
/* 9693 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 9694 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\JBossDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */