/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.tags.Truncate;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.data.support.DialChartSupport;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.DialChart;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
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
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatNumberTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class PortDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   58 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   61 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   62 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   63 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   70 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   75 */     ArrayList list = null;
/*   76 */     StringBuffer sbf = new StringBuffer();
/*   77 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
/*   78 */     if (distinct)
/*      */     {
/*   80 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   84 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   87 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   89 */       ArrayList row = (ArrayList)list.get(i);
/*   90 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   91 */       if (distinct) {
/*   92 */         sbf.append(row.get(0));
/*      */       } else
/*   94 */         sbf.append(row.get(1));
/*   95 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   98 */     return sbf.toString(); }
/*      */   
/*  100 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  103 */     if (severity == null)
/*      */     {
/*  105 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  107 */     if (severity.equals("5"))
/*      */     {
/*  109 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  111 */     if (severity.equals("1"))
/*      */     {
/*  113 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  118 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  125 */     if (severity == null)
/*      */     {
/*  127 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  129 */     if (severity.equals("1"))
/*      */     {
/*  131 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  133 */     if (severity.equals("4"))
/*      */     {
/*  135 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  137 */     if (severity.equals("5"))
/*      */     {
/*  139 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  144 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  150 */     if (severity == null)
/*      */     {
/*  152 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  154 */     if (severity.equals("5"))
/*      */     {
/*  156 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  158 */     if (severity.equals("1"))
/*      */     {
/*  160 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  164 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  170 */     if (severity == null)
/*      */     {
/*  172 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  174 */     if (severity.equals("1"))
/*      */     {
/*  176 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  178 */     if (severity.equals("4"))
/*      */     {
/*  180 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  182 */     if (severity.equals("5"))
/*      */     {
/*  184 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  188 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  194 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  200 */     if (severity == 5)
/*      */     {
/*  202 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  204 */     if (severity == 1)
/*      */     {
/*  206 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  211 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  217 */     if (severity == null)
/*      */     {
/*  219 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  221 */     if (severity.equals("5"))
/*      */     {
/*  223 */       if (isAvailability) {
/*  224 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  227 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  230 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  232 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  234 */     if (severity.equals("1"))
/*      */     {
/*  236 */       if (isAvailability) {
/*  237 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  240 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  247 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  254 */     if (severity == null)
/*      */     {
/*  256 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  258 */     if (severity.equals("5"))
/*      */     {
/*  260 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  262 */     if (severity.equals("4"))
/*      */     {
/*  264 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  266 */     if (severity.equals("1"))
/*      */     {
/*  268 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  273 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  279 */     if (severity == null)
/*      */     {
/*  281 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  283 */     if (severity.equals("5"))
/*      */     {
/*  285 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  287 */     if (severity.equals("4"))
/*      */     {
/*  289 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  291 */     if (severity.equals("1"))
/*      */     {
/*  293 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  298 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  305 */     if (severity == null)
/*      */     {
/*  307 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  309 */     if (severity.equals("5"))
/*      */     {
/*  311 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  313 */     if (severity.equals("4"))
/*      */     {
/*  315 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  317 */     if (severity.equals("1"))
/*      */     {
/*  319 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  324 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  332 */     StringBuffer out = new StringBuffer();
/*  333 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  334 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  335 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  336 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  337 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  338 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  339 */     out.append("</tr>");
/*  340 */     out.append("</form></table>");
/*  341 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  348 */     if (val == null)
/*      */     {
/*  350 */       return "-";
/*      */     }
/*      */     
/*  353 */     String ret = FormatUtil.formatNumber(val);
/*  354 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  355 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  358 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  362 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  370 */     StringBuffer out = new StringBuffer();
/*  371 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  372 */     out.append("<tr>");
/*  373 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  375 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  377 */     out.append("</tr>");
/*  378 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  382 */       if (j % 2 == 0)
/*      */       {
/*  384 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  388 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  391 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  393 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  396 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  400 */       out.append("</tr>");
/*      */     }
/*  402 */     out.append("</table>");
/*  403 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  404 */     out.append("<tr>");
/*  405 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  406 */     out.append("</tr>");
/*  407 */     out.append("</table>");
/*  408 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
/*      */   {
/*  414 */     StringBuffer out = new StringBuffer();
/*  415 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  416 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  417 */     out.append("<tr>");
/*  418 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  419 */     out.append("<tr>");
/*  420 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  421 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  422 */     out.append("</tr>");
/*  423 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  426 */       out.append("<tr>");
/*  427 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  428 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  429 */       out.append("</tr>");
/*      */     }
/*      */     
/*  432 */     out.append("</table>");
/*  433 */     out.append("</table>");
/*  434 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  439 */     if (severity.equals("0"))
/*      */     {
/*  441 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  445 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  452 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  465 */     StringBuffer out = new StringBuffer();
/*  466 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  467 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  469 */       out.append("<tr>");
/*  470 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  471 */       out.append("</tr>");
/*      */       
/*      */ 
/*  474 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  476 */         String borderclass = "";
/*      */         
/*      */ 
/*  479 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  481 */         out.append("<tr>");
/*      */         
/*  483 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  484 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  485 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  491 */     out.append("</table><br>");
/*  492 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  493 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  495 */       List sLinks = secondLevelOfLinks[0];
/*  496 */       List sText = secondLevelOfLinks[1];
/*  497 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  500 */         out.append("<tr>");
/*  501 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  502 */         out.append("</tr>");
/*  503 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  505 */           String borderclass = "";
/*      */           
/*      */ 
/*  508 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  510 */           out.append("<tr>");
/*      */           
/*  512 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  513 */           if (sLinks.get(i).toString().length() == 0) {
/*  514 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  517 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  519 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  523 */     out.append("</table>");
/*  524 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  531 */     StringBuffer out = new StringBuffer();
/*  532 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  533 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  535 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  537 */         out.append("<tr>");
/*  538 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  539 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  543 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  545 */           String borderclass = "";
/*      */           
/*      */ 
/*  548 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  550 */           out.append("<tr>");
/*      */           
/*  552 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  553 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  554 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  557 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  560 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  565 */     out.append("</table><br>");
/*  566 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  567 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  569 */       List sLinks = secondLevelOfLinks[0];
/*  570 */       List sText = secondLevelOfLinks[1];
/*  571 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  574 */         out.append("<tr>");
/*  575 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  576 */         out.append("</tr>");
/*  577 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  579 */           String borderclass = "";
/*      */           
/*      */ 
/*  582 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  584 */           out.append("<tr>");
/*      */           
/*  586 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  587 */           if (sLinks.get(i).toString().length() == 0) {
/*  588 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  591 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  593 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  597 */     out.append("</table>");
/*  598 */     return out.toString();
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
/*  611 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  614 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  617 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  620 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  623 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  626 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  629 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  632 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  640 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  645 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  650 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  655 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  660 */     if (val != null)
/*      */     {
/*  662 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  666 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  671 */     if (val == null) {
/*  672 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  676 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  681 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  687 */     if (val != null)
/*      */     {
/*  689 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  693 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  699 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  704 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  708 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  713 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  718 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  723 */     String hostaddress = "";
/*  724 */     String ip = request.getHeader("x-forwarded-for");
/*  725 */     if (ip == null)
/*  726 */       ip = request.getRemoteAddr();
/*  727 */     InetAddress add = null;
/*  728 */     if (ip.equals("127.0.0.1")) {
/*  729 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  733 */       add = InetAddress.getByName(ip);
/*      */     }
/*  735 */     hostaddress = add.getHostName();
/*  736 */     if (hostaddress.indexOf('.') != -1) {
/*  737 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  738 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  742 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  747 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  753 */     if (severity == null)
/*      */     {
/*  755 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  757 */     if (severity.equals("5"))
/*      */     {
/*  759 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  761 */     if (severity.equals("1"))
/*      */     {
/*  763 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  768 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  773 */     ResultSet set = null;
/*  774 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  775 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  777 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  778 */       if (set.next()) { String str1;
/*  779 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  780 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  783 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  788 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  791 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  793 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  797 */     StringBuffer rca = new StringBuffer();
/*  798 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  799 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  802 */     int rcalength = key.length();
/*  803 */     String split = "6. ";
/*  804 */     int splitPresent = key.indexOf(split);
/*  805 */     String div1 = "";String div2 = "";
/*  806 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  808 */       if (rcalength > 180) {
/*  809 */         rca.append("<span class=\"rca-critical-text\">");
/*  810 */         getRCATrimmedText(key, rca);
/*  811 */         rca.append("</span>");
/*      */       } else {
/*  813 */         rca.append("<span class=\"rca-critical-text\">");
/*  814 */         rca.append(key);
/*  815 */         rca.append("</span>");
/*      */       }
/*  817 */       return rca.toString();
/*      */     }
/*  819 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  820 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  821 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  822 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  823 */     getRCATrimmedText(div1, rca);
/*  824 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  827 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  828 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  829 */     getRCATrimmedText(div2, rca);
/*  830 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  832 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  837 */     String[] st = msg.split("<br>");
/*  838 */     for (int i = 0; i < st.length; i++) {
/*  839 */       String s = st[i];
/*  840 */       if (s.length() > 180) {
/*  841 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  843 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  847 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  848 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  850 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  854 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  855 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  856 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  859 */       if (key == null) {
/*  860 */         return ret;
/*      */       }
/*      */       
/*  863 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  864 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  867 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  868 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  869 */       set = AMConnectionPool.executeQueryStmt(query);
/*  870 */       if (set.next())
/*      */       {
/*  872 */         String helpLink = set.getString("LINK");
/*  873 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  876 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  882 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  901 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  892 */         if (set != null) {
/*  893 */           AMConnectionPool.closeStatement(set);
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
/*  907 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  908 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  910 */       String entityStr = (String)keys.nextElement();
/*  911 */       String mmessage = temp.getProperty(entityStr);
/*  912 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  913 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  915 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  921 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  922 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  924 */       String entityStr = (String)keys.nextElement();
/*  925 */       String mmessage = temp.getProperty(entityStr);
/*  926 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  927 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  929 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  934 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  944 */     String des = new String();
/*  945 */     while (str.indexOf(find) != -1) {
/*  946 */       des = des + str.substring(0, str.indexOf(find));
/*  947 */       des = des + replace;
/*  948 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  950 */     des = des + str;
/*  951 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  958 */       if (alert == null)
/*      */       {
/*  960 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  962 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  964 */         return "&nbsp;";
/*      */       }
/*      */       
/*  967 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  969 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  972 */       int rcalength = test.length();
/*  973 */       if (rcalength < 300)
/*      */       {
/*  975 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  979 */       StringBuffer out = new StringBuffer();
/*  980 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  981 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  982 */       out.append("</div>");
/*  983 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  984 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  985 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  990 */       ex.printStackTrace();
/*      */     }
/*  992 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  998 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1003 */     ArrayList attribIDs = new ArrayList();
/* 1004 */     ArrayList resIDs = new ArrayList();
/* 1005 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1007 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1009 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1011 */       String resourceid = "";
/* 1012 */       String resourceType = "";
/* 1013 */       if (type == 2) {
/* 1014 */         resourceid = (String)row.get(0);
/* 1015 */         resourceType = (String)row.get(3);
/*      */       }
/* 1017 */       else if (type == 3) {
/* 1018 */         resourceid = (String)row.get(0);
/* 1019 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1022 */         resourceid = (String)row.get(6);
/* 1023 */         resourceType = (String)row.get(7);
/*      */       }
/* 1025 */       resIDs.add(resourceid);
/* 1026 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1027 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1029 */       String healthentity = null;
/* 1030 */       String availentity = null;
/* 1031 */       if (healthid != null) {
/* 1032 */         healthentity = resourceid + "_" + healthid;
/* 1033 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1036 */       if (availid != null) {
/* 1037 */         availentity = resourceid + "_" + availid;
/* 1038 */         entitylist.add(availentity);
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
/* 1052 */     Properties alert = getStatus(entitylist);
/* 1053 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1058 */     int size = monitorList.size();
/*      */     
/* 1060 */     String[] severity = new String[size];
/*      */     
/* 1062 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1064 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1065 */       String resourceName1 = (String)row1.get(7);
/* 1066 */       String resourceid1 = (String)row1.get(6);
/* 1067 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1068 */       if (severity[j] == null)
/*      */       {
/* 1070 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1074 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1076 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1078 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1081 */         if (sev > 0) {
/* 1082 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1083 */           monitorList.set(k, monitorList.get(j));
/* 1084 */           monitorList.set(j, t);
/* 1085 */           String temp = severity[k];
/* 1086 */           severity[k] = severity[j];
/* 1087 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1093 */     int z = 0;
/* 1094 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1097 */       int i = 0;
/* 1098 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1101 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1105 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1109 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1111 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1114 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1118 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1121 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1122 */       String resourceName1 = (String)row1.get(7);
/* 1123 */       String resourceid1 = (String)row1.get(6);
/* 1124 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1125 */       if (hseverity[j] == null)
/*      */       {
/* 1127 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1132 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1134 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1137 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1140 */         if (hsev > 0) {
/* 1141 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1142 */           monitorList.set(k, monitorList.get(j));
/* 1143 */           monitorList.set(j, t);
/* 1144 */           String temp1 = hseverity[k];
/* 1145 */           hseverity[k] = hseverity[j];
/* 1146 */           hseverity[j] = temp1;
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
/* 1158 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1159 */     boolean forInventory = false;
/* 1160 */     String trdisplay = "none";
/* 1161 */     String plusstyle = "inline";
/* 1162 */     String minusstyle = "none";
/* 1163 */     String haidTopLevel = "";
/* 1164 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1166 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1168 */         haidTopLevel = request.getParameter("haid");
/* 1169 */         forInventory = true;
/* 1170 */         trdisplay = "table-row;";
/* 1171 */         plusstyle = "none";
/* 1172 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1179 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1182 */     ArrayList listtoreturn = new ArrayList();
/* 1183 */     StringBuffer toreturn = new StringBuffer();
/* 1184 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1185 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1186 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1188 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1190 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1191 */       String childresid = (String)singlerow.get(0);
/* 1192 */       String childresname = (String)singlerow.get(1);
/* 1193 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1194 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1195 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1196 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1197 */       String unmanagestatus = (String)singlerow.get(5);
/* 1198 */       String actionstatus = (String)singlerow.get(6);
/* 1199 */       String linkclass = "monitorgp-links";
/* 1200 */       String titleforres = childresname;
/* 1201 */       String titilechildresname = childresname;
/* 1202 */       String childimg = "/images/trcont.png";
/* 1203 */       String flag = "enable";
/* 1204 */       String dcstarted = (String)singlerow.get(8);
/* 1205 */       String configMonitor = "";
/* 1206 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1207 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1209 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1211 */       if (singlerow.get(7) != null)
/*      */       {
/* 1213 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1215 */       String haiGroupType = "0";
/* 1216 */       if ("HAI".equals(childtype))
/*      */       {
/* 1218 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1220 */       childimg = "/images/trend.png";
/* 1221 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1222 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1223 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1225 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1227 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1229 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1230 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1233 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1235 */         linkclass = "disabledtext";
/* 1236 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1238 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1239 */       String availmouseover = "";
/* 1240 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1242 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1244 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1245 */       String healthmouseover = "";
/* 1246 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1248 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1251 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1252 */       int spacing = 0;
/* 1253 */       if (level >= 1)
/*      */       {
/* 1255 */         spacing = 40 * level;
/*      */       }
/* 1257 */       if (childtype.equals("HAI"))
/*      */       {
/* 1259 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1260 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1261 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1263 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1264 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1265 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1266 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1267 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1268 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1269 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1270 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1271 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1272 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1273 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1275 */         if (!forInventory)
/*      */         {
/* 1277 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1280 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1282 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1284 */           actions = editlink + actions;
/*      */         }
/* 1286 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1288 */           actions = actions + associatelink;
/*      */         }
/* 1290 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1291 */         String arrowimg = "";
/* 1292 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1294 */           actions = "";
/* 1295 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1296 */           checkbox = "";
/* 1297 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1299 */         if (isIt360)
/*      */         {
/* 1301 */           actionimg = "";
/* 1302 */           actions = "";
/* 1303 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1304 */           checkbox = "";
/*      */         }
/*      */         
/* 1307 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1309 */           actions = "";
/*      */         }
/* 1311 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1313 */           checkbox = "";
/*      */         }
/*      */         
/* 1316 */         String resourcelink = "";
/*      */         
/* 1318 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1320 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1324 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1327 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1329 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1330 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1331 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1332 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1333 */         if (!isIt360)
/*      */         {
/* 1335 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1339 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1342 */         toreturn.append("</tr>");
/* 1343 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1345 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1346 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1350 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1351 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1354 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1358 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1360 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1361 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1362 */             toreturn.append(assocMessage);
/* 1363 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1364 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1365 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1366 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1372 */         String resourcelink = null;
/* 1373 */         boolean hideEditLink = false;
/* 1374 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1376 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1377 */           hideEditLink = true;
/* 1378 */           if (isIt360)
/*      */           {
/* 1380 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1384 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1386 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1388 */           hideEditLink = true;
/* 1389 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1390 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1395 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1398 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1399 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1400 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1401 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1402 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1403 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1404 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1405 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1406 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1407 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1408 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1409 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1410 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1412 */         if (hideEditLink)
/*      */         {
/* 1414 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1416 */         if (!forInventory)
/*      */         {
/* 1418 */           removefromgroup = "";
/*      */         }
/* 1420 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1421 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1422 */           actions = actions + configcustomfields;
/*      */         }
/* 1424 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1426 */           actions = editlink + actions;
/*      */         }
/* 1428 */         String managedLink = "";
/* 1429 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1431 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1432 */           actions = "";
/* 1433 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1434 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1437 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1439 */           checkbox = "";
/*      */         }
/*      */         
/* 1442 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1444 */           actions = "";
/*      */         }
/* 1446 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1447 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1448 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1449 */         if (isIt360)
/*      */         {
/* 1451 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1455 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1457 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1458 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1459 */         if (!isIt360)
/*      */         {
/* 1461 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1465 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1467 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1470 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1477 */       StringBuilder toreturn = new StringBuilder();
/* 1478 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1479 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1480 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1481 */       String title = "";
/* 1482 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1483 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1484 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1485 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1487 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1489 */       else if ("5".equals(severity))
/*      */       {
/* 1491 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1495 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1497 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1498 */       toreturn.append(v);
/*      */       
/* 1500 */       toreturn.append(link);
/* 1501 */       if (severity == null)
/*      */       {
/* 1503 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1505 */       else if (severity.equals("5"))
/*      */       {
/* 1507 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1509 */       else if (severity.equals("4"))
/*      */       {
/* 1511 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1513 */       else if (severity.equals("1"))
/*      */       {
/* 1515 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1520 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1522 */       toreturn.append("</a>");
/* 1523 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1527 */       ex.printStackTrace();
/*      */     }
/* 1529 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1536 */       StringBuilder toreturn = new StringBuilder();
/* 1537 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1538 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1539 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1540 */       if (message == null)
/*      */       {
/* 1542 */         message = "";
/*      */       }
/*      */       
/* 1545 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1546 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1548 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1549 */       toreturn.append(v);
/*      */       
/* 1551 */       toreturn.append(link);
/*      */       
/* 1553 */       if (severity == null)
/*      */       {
/* 1555 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1557 */       else if (severity.equals("5"))
/*      */       {
/* 1559 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1561 */       else if (severity.equals("1"))
/*      */       {
/* 1563 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1568 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1570 */       toreturn.append("</a>");
/* 1571 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1577 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1580 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1581 */     if (invokeActions != null) {
/* 1582 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1583 */       while (iterator.hasNext()) {
/* 1584 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1585 */         if (actionmap.containsKey(actionid)) {
/* 1586 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1591 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1595 */     String actionLink = "";
/* 1596 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1597 */     String query = "";
/* 1598 */     ResultSet rs = null;
/* 1599 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1600 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1601 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1602 */       actionLink = "method=" + methodName;
/*      */     }
/* 1604 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1605 */       actionLink = methodName;
/*      */     }
/* 1607 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1608 */     Iterator itr = methodarglist.iterator();
/* 1609 */     boolean isfirstparam = true;
/* 1610 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1611 */     while (itr.hasNext()) {
/* 1612 */       HashMap argmap = (HashMap)itr.next();
/* 1613 */       String argtype = (String)argmap.get("TYPE");
/* 1614 */       String argname = (String)argmap.get("IDENTITY");
/* 1615 */       String paramname = (String)argmap.get("PARAMETER");
/* 1616 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1617 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1618 */         isfirstparam = false;
/* 1619 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1621 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1625 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1629 */         actionLink = actionLink + "&";
/*      */       }
/* 1631 */       String paramValue = null;
/* 1632 */       String tempargname = argname;
/* 1633 */       if (commonValues.getProperty(tempargname) != null) {
/* 1634 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1637 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1638 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1639 */           if (dbType.equals("mysql")) {
/* 1640 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1643 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1645 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1647 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1648 */             if (rs.next()) {
/* 1649 */               paramValue = rs.getString("VALUE");
/* 1650 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1654 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1658 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1661 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1666 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1667 */           paramValue = rowId;
/*      */         }
/* 1669 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1670 */           paramValue = managedObjectName;
/*      */         }
/* 1672 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1673 */           paramValue = resID;
/*      */         }
/* 1675 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1676 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1679 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1681 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1682 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1683 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1685 */     return actionLink;
/*      */   }
/*      */   
/* 1688 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1689 */     String dependentAttribute = null;
/* 1690 */     String align = "left";
/*      */     
/* 1692 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1693 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1694 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1695 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1696 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1697 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1698 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1699 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1700 */       align = "center";
/*      */     }
/*      */     
/* 1703 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1704 */     String actualdata = "";
/*      */     
/* 1706 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1707 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1708 */         actualdata = availValue;
/*      */       }
/* 1710 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1711 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1715 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1716 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1719 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1725 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1726 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1727 */       toreturn.append("<table>");
/* 1728 */       toreturn.append("<tr>");
/* 1729 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1730 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1731 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1732 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1733 */         String toolTip = "";
/* 1734 */         String hideClass = "";
/* 1735 */         String textStyle = "";
/* 1736 */         boolean isreferenced = true;
/* 1737 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1738 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1739 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1740 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1742 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1743 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1744 */           while (valueList.hasMoreTokens()) {
/* 1745 */             String dependentVal = valueList.nextToken();
/* 1746 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1747 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1748 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1750 */               toolTip = "";
/* 1751 */               hideClass = "";
/* 1752 */               isreferenced = false;
/* 1753 */               textStyle = "disabledtext";
/* 1754 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1758 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1759 */           toolTip = "";
/* 1760 */           hideClass = "";
/* 1761 */           isreferenced = false;
/* 1762 */           textStyle = "disabledtext";
/* 1763 */           if (dependentImageMap != null) {
/* 1764 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1765 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1768 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1772 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1773 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1774 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1775 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1776 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1777 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1779 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1780 */           if (isreferenced) {
/* 1781 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1785 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1786 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1787 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1788 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1789 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1790 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1792 */           toreturn.append("</span>");
/* 1793 */           toreturn.append("</a>");
/* 1794 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1797 */       toreturn.append("</tr>");
/* 1798 */       toreturn.append("</table>");
/* 1799 */       toreturn.append("</td>");
/*      */     } else {
/* 1801 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1804 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1808 */     String colTime = null;
/* 1809 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1810 */     if ((rows != null) && (rows.size() > 0)) {
/* 1811 */       Iterator<String> itr = rows.iterator();
/* 1812 */       String maxColQuery = "";
/* 1813 */       for (;;) { if (itr.hasNext()) {
/* 1814 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1815 */           ResultSet maxCol = null;
/*      */           try {
/* 1817 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1818 */             while (maxCol.next()) {
/* 1819 */               if (colTime == null) {
/* 1820 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1823 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1832 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1834 */               if (maxCol != null)
/* 1835 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1837 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1832 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1834 */               if (maxCol != null)
/* 1835 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1837 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1842 */     return colTime;
/*      */   }
/*      */   
/* 1845 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1846 */     tablename = null;
/* 1847 */     ResultSet rsTable = null;
/* 1848 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1850 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1851 */       while (rsTable.next()) {
/* 1852 */         tablename = rsTable.getString("DATATABLE");
/* 1853 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1854 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1867 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1858 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1861 */         if (rsTable != null)
/* 1862 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1864 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1870 */     String argsList = "";
/* 1871 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1873 */       if (showArgsMap.get(row) != null) {
/* 1874 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1875 */         if (showArgslist != null) {
/* 1876 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1877 */             if (argsList.trim().equals("")) {
/* 1878 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1881 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1888 */       e.printStackTrace();
/* 1889 */       return "";
/*      */     }
/* 1891 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1896 */     String argsList = "";
/* 1897 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1900 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1902 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1903 */         if (hideArgsList != null)
/*      */         {
/* 1905 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1907 */             if (argsList.trim().equals(""))
/*      */             {
/* 1909 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1913 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1921 */       ex.printStackTrace();
/*      */     }
/* 1923 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1927 */     StringBuilder toreturn = new StringBuilder();
/* 1928 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1935 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1936 */       Iterator itr = tActionList.iterator();
/* 1937 */       while (itr.hasNext()) {
/* 1938 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1939 */         String confirmmsg = "";
/* 1940 */         String link = "";
/* 1941 */         String isJSP = "NO";
/* 1942 */         HashMap tactionMap = (HashMap)itr.next();
/* 1943 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1944 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1945 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1946 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1947 */           (actionmap.containsKey(actionId))) {
/* 1948 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1949 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1950 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1951 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1952 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1954 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1960 */           if (isTableAction) {
/* 1961 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1964 */             tableName = "Link";
/* 1965 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1966 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1967 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1968 */             toreturn.append("</a></td>");
/*      */           }
/* 1970 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1971 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1972 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1973 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1979 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1985 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1987 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1988 */       Properties prop = (Properties)node.getUserObject();
/* 1989 */       String mgID = prop.getProperty("label");
/* 1990 */       String mgName = prop.getProperty("value");
/* 1991 */       String isParent = prop.getProperty("isParent");
/* 1992 */       int mgIDint = Integer.parseInt(mgID);
/* 1993 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1995 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1997 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1998 */       if (node.getChildCount() > 0)
/*      */       {
/* 2000 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2002 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2004 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2006 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2010 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2015 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2017 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2019 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2021 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2025 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2028 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2029 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2031 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2035 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2037 */       if (node.getChildCount() > 0)
/*      */       {
/* 2039 */         builder.append("<UL>");
/* 2040 */         printMGTree(node, builder);
/* 2041 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2046 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2047 */     StringBuffer toReturn = new StringBuffer();
/* 2048 */     String table = "-";
/*      */     try {
/* 2050 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2051 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2052 */       float total = 0.0F;
/* 2053 */       while (it.hasNext()) {
/* 2054 */         String attName = (String)it.next();
/* 2055 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2056 */         boolean roundOffData = false;
/* 2057 */         if ((data != null) && (!data.equals(""))) {
/* 2058 */           if (data.indexOf(",") != -1) {
/* 2059 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2062 */             float value = Float.parseFloat(data);
/* 2063 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2066 */             total += value;
/* 2067 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2070 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2075 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2076 */       while (attVsWidthList.hasNext()) {
/* 2077 */         String attName = (String)attVsWidthList.next();
/* 2078 */         String data = (String)attVsWidthProps.get(attName);
/* 2079 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2080 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2081 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2082 */         String className = (String)graphDetails.get("ClassName");
/* 2083 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2084 */         if (percentage < 1.0F)
/*      */         {
/* 2086 */           data = percentage + "";
/*      */         }
/* 2088 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2090 */       if (toReturn.length() > 0) {
/* 2091 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2095 */       e.printStackTrace();
/*      */     }
/* 2097 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2103 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2104 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2105 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2106 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2107 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2108 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2109 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2110 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2111 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2114 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2115 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2116 */       splitvalues[0] = multiplecondition.toString();
/* 2117 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2120 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2125 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2126 */     if (thresholdType != 3) {
/* 2127 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2128 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2129 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2130 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2131 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2132 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2134 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2135 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2136 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2137 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2138 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2139 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2141 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2142 */     if (updateSelected != null) {
/* 2143 */       updateSelected[0] = "selected";
/*      */     }
/* 2145 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2150 */       StringBuffer toreturn = new StringBuffer("");
/* 2151 */       if (commaSeparatedMsgId != null) {
/* 2152 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2153 */         int count = 0;
/* 2154 */         while (msgids.hasMoreTokens()) {
/* 2155 */           String id = msgids.nextToken();
/* 2156 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2157 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2158 */           count++;
/* 2159 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2160 */             if (toreturn.length() == 0) {
/* 2161 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2163 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2164 */             if (!image.trim().equals("")) {
/* 2165 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2167 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2168 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2171 */         if (toreturn.length() > 0) {
/* 2172 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2176 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2179 */       e.printStackTrace(); }
/* 2180 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2186 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2192 */   private static Map<String, Long> _jspx_dependants = new HashMap(7);
/* 2193 */   static { _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2194 */     _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2195 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2196 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2197 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2198 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2199 */     _jspx_dependants.put("/jsp/includes/HostPerformance.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2234 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2238 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2266 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2270 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2271 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2272 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2273 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2274 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2275 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2276 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2277 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2278 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2279 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2280 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2281 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2282 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2283 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2284 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2285 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2286 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2287 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.release();
/* 2288 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2289 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2290 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2291 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2292 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/* 2293 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2294 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.release();
/* 2295 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2296 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2303 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2306 */     JspWriter out = null;
/* 2307 */     Object page = this;
/* 2308 */     JspWriter _jspx_out = null;
/* 2309 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2313 */       response.setContentType("text/html;charset=UTF-8");
/* 2314 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2316 */       _jspx_page_context = pageContext;
/* 2317 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2318 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2319 */       session = pageContext.getSession();
/* 2320 */       out = pageContext.getOut();
/* 2321 */       _jspx_out = out;
/*      */       
/* 2323 */       out.write("<!DOCTYPE html>\n");
/* 2324 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n");
/*      */       
/* 2326 */       request.setAttribute("HelpKey", "Monitors Service Details");
/*      */       
/* 2328 */       out.write("\n\n\n\n\n\n\n\n\n\n");
/* 2329 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2331 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2332 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2333 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2335 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2337 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2339 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2341 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2342 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2343 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2344 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2347 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2348 */         String available = null;
/* 2349 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2350 */         out.write(10);
/*      */         
/* 2352 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2353 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2354 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2356 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2358 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2360 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2362 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2363 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2364 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2365 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2368 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2369 */           String unavailable = null;
/* 2370 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2371 */           out.write(10);
/*      */           
/* 2373 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2374 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2375 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2377 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2379 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2381 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2383 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2384 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2385 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2386 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2389 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2390 */             String unmanaged = null;
/* 2391 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2392 */             out.write(10);
/*      */             
/* 2394 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2395 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2396 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2398 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2400 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2402 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2404 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2405 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2406 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2407 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2410 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2411 */               String scheduled = null;
/* 2412 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2413 */               out.write(10);
/*      */               
/* 2415 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2416 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2417 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2419 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2421 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2423 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2425 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2426 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2427 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2428 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2431 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2432 */                 String critical = null;
/* 2433 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2434 */                 out.write(10);
/*      */                 
/* 2436 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2437 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2438 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2440 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2442 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2444 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2446 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2447 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2448 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2449 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2452 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2453 */                   String clear = null;
/* 2454 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2455 */                   out.write(10);
/*      */                   
/* 2457 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2458 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2459 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2461 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2463 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2465 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2467 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2468 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2469 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2470 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2473 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2474 */                     String warning = null;
/* 2475 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2476 */                     out.write(10);
/* 2477 */                     out.write(10);
/*      */                     
/* 2479 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2480 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2482 */                     out.write(10);
/* 2483 */                     out.write(10);
/* 2484 */                     out.write(10);
/* 2485 */                     out.write("\n\n\n\n\n\n\n");
/* 2486 */                     com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2487 */                     wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2488 */                     if (wlsGraph == null) {
/* 2489 */                       wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2490 */                       _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */                     }
/* 2492 */                     out.write(10);
/* 2493 */                     com.adventnet.appmanager.server.portmonitoring.bean.PortPerformanceBean perfGraph = null;
/* 2494 */                     perfGraph = (com.adventnet.appmanager.server.portmonitoring.bean.PortPerformanceBean)_jspx_page_context.getAttribute("perfGraph", 2);
/* 2495 */                     if (perfGraph == null) {
/* 2496 */                       perfGraph = new com.adventnet.appmanager.server.portmonitoring.bean.PortPerformanceBean();
/* 2497 */                       _jspx_page_context.setAttribute("perfGraph", perfGraph, 2);
/*      */                     }
/* 2499 */                     out.write(10);
/*      */                     
/* 2501 */                     String resourceid = request.getParameter("resourceid");
/* 2502 */                     String resourcename = request.getParameter("resourcename");
/* 2503 */                     String moname = request.getParameter("moname");
/*      */                     
/*      */ 
/* 2506 */                     wlsGraph.setParam(resourceid, "AVAILABILITY");
/* 2507 */                     perfGraph.setresourceID(Integer.parseInt(resourceid));
/*      */                     
/* 2509 */                     String appname = request.getParameter("name");
/* 2510 */                     String haid = request.getParameter("haid");
/* 2511 */                     String redirecto = null;
/* 2512 */                     String encodeurl = null;
/* 2513 */                     ArrayList attribIDs = new ArrayList();
/* 2514 */                     ArrayList resIDs = new ArrayList();
/* 2515 */                     for (int i = 151; i < 154; i++)
/*      */                     {
/* 2517 */                       attribIDs.add("" + i);
/*      */                     }
/* 2519 */                     resIDs.add("" + resourceid);
/* 2520 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2521 */                     String search = null;
/* 2522 */                     if (haid != null) {
/* 2523 */                       if (haid.equals("null")) {
/* 2524 */                         search = "<a href='network_content.jsp' class='arial10'>Monitors</a> &nbsp;<img src='../images/icon_arrow.gif'> &nbsp; <a href='networkdetails.jsp?network=Port-Test&appname=null&haid=null' class='arial10'>Port-Test</a> &nbsp;<img src='../images/icon_arrow.gif'> &nbsp;" + resourcename + "";
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/* 2529 */                         search = "<a href='applications.jsp' class='arial10'>Applications</a> &nbsp;<img src='../images/icon_arrow.gif'> &nbsp;<a href='showapplication.jsp?haid=" + haid + "' class='arial10'>" + appname + "</a> &nbsp;<img src='../images/icon_arrow.gif'> &nbsp;" + resourcename + "";
/*      */                       }
/*      */                     }
/*      */                     
/*      */ 
/* 2534 */                     redirecto = "/showresource.do?resourceid=" + resourceid + "&type=Port-Test&moname=" + moname + "&method=showdetails&resourcename=" + resourcename + "&haid=" + haid + "&appname=" + appname + "";
/*      */                     
/* 2536 */                     encodeurl = java.net.URLEncoder.encode(redirecto);
/* 2537 */                     HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/* 2538 */                     HashMap info = (HashMap)request.getAttribute("info");
/*      */                     
/* 2540 */                     out.write(10);
/* 2541 */                     out.write(10);
/* 2542 */                     if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */                       return;
/* 2544 */                     out.write(10);
/*      */                     
/* 2546 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2547 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2548 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2550 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2551 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2552 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2554 */                         out.write(32);
/*      */                         
/* 2556 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2557 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2558 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2560 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2562 */                         _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("Service Monitoring"));
/* 2563 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2564 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2565 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2568 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2569 */                         out.write(10);
/* 2570 */                         if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2572 */                         out.write(10);
/* 2573 */                         if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2575 */                         out.write(10);
/* 2576 */                         out.write(10);
/*      */                         
/* 2578 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2579 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2580 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2582 */                         _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */                         
/* 2584 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 2585 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2586 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2587 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2588 */                             out = _jspx_page_context.pushBody();
/* 2589 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2590 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2593 */                             out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td align=\"left\" class=\"leftlinksheading\">");
/* 2594 */                             out.print(FormatUtil.getString("am.webclient.servicemonitor.monitorname"));
/* 2595 */                             out.write("</td>\n  </tr>\n  <tr>\n     <td class=\"leftlinkstd\">\n     ");
/*      */                             
/* 2597 */                             ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2598 */                             _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2599 */                             _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/* 2600 */                             int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2601 */                             if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                               for (;;) {
/* 2603 */                                 out.write(10);
/* 2604 */                                 out.write(9);
/*      */                                 
/* 2606 */                                 WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2607 */                                 _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2608 */                                 _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                 
/* 2610 */                                 _jspx_th_c_005fwhen_005f0.setTest("${((! empty param.reconfigure && param.reconfigure =='true') || ! empty param.context) || (param.all=='true')}");
/* 2611 */                                 int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2612 */                                 if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                   for (;;) {
/* 2614 */                                     out.write("\n          <a href=\"/showresource.do?resourceid=");
/* 2615 */                                     out.print(resourceid);
/* 2616 */                                     out.write("&method=showResourceForResourceID&haid=");
/* 2617 */                                     out.print(haid);
/* 2618 */                                     out.write("\" class=\"new-left-links\">\n      ");
/* 2619 */                                     out.print(FormatUtil.getString("am.webclient.mssqldetails.snapshot"));
/* 2620 */                                     out.write("</a>\n   ");
/* 2621 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2622 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2626 */                                 if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2627 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                 }
/*      */                                 
/* 2630 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2631 */                                 out.write(10);
/* 2632 */                                 out.write(32);
/* 2633 */                                 out.write(32);
/*      */                                 
/* 2635 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2636 */                                 _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2637 */                                 _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2638 */                                 int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2639 */                                 if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                   for (;;) {
/* 2641 */                                     out.write("\n    ");
/* 2642 */                                     out.print(FormatUtil.getString("am.webclient.mssqldetails.snapshot"));
/* 2643 */                                     out.write("\n   ");
/* 2644 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2645 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2649 */                                 if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2650 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                 }
/*      */                                 
/* 2653 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2654 */                                 out.write("\n   ");
/* 2655 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2656 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2660 */                             if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2661 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                             }
/*      */                             
/* 2664 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2665 */                             out.write("\n\n\n  </tr>\n\n  ");
/*      */                             
/* 2667 */                             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2668 */                             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2669 */                             _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2671 */                             _jspx_th_c_005fif_005f2.setTest("${!empty ADMIN || !empty DEMO}");
/* 2672 */                             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2673 */                             if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                               for (;;) {
/* 2675 */                                 out.write("\n  <tr>\n    <td class=\"leftlinkstd\"> <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 2676 */                                 if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                   return;
/* 2678 */                                 out.write("\" class=\"new-left-links\">\n      ");
/* 2679 */                                 out.print(ALERTCONFIG_TEXT);
/* 2680 */                                 out.write(" </a> </td>\n  </tr>\n  ");
/* 2681 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2682 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2686 */                             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2687 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                             }
/*      */                             
/* 2690 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2691 */                             out.write(10);
/* 2692 */                             out.write(32);
/* 2693 */                             out.write(32);
/*      */                             
/* 2695 */                             PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2696 */                             _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2697 */                             _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2699 */                             _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2700 */                             int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2701 */                             if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                               for (;;) {
/* 2703 */                                 out.write("\n  <tr>\n   <td class=\"leftlinkstd\" > <a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 2704 */                                 out.print(request.getParameter("resourceid"));
/* 2705 */                                 out.write("&type=");
/* 2706 */                                 out.print(request.getParameter("type"));
/* 2707 */                                 out.write("&moname=");
/* 2708 */                                 out.print(request.getParameter("moname"));
/* 2709 */                                 out.write("&method=showdetails&resourcename=");
/* 2710 */                                 out.print(request.getParameter("resourcename"));
/* 2711 */                                 out.write("&aam_jump=true&editPage=true\" class=\"new-left-links\">\n  ");
/* 2712 */                                 out.print(FormatUtil.getString("am.webclient.dotnet.edit"));
/* 2713 */                                 out.write("</a> </td>\n   </tr>\n  ");
/* 2714 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2715 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2719 */                             if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2720 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                             }
/*      */                             
/* 2723 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2724 */                             out.write("\n\n  ");
/*      */                             
/* 2726 */                             IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2727 */                             _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2728 */                             _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2730 */                             _jspx_th_c_005fif_005f3.setTest("${!empty ADMIN || !empty DEMO}");
/* 2731 */                             int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2732 */                             if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                               for (;;) {
/* 2734 */                                 out.write("\n  <tr>\n    <td class=\"leftlinkstd\"> <a href=\"javascript:toggleDiv('edit')\" class=\"new-left-links\">\n      ");
/* 2735 */                                 out.print(FormatUtil.getString("am.webclient.dotnet.edit"));
/* 2736 */                                 out.write("</a> </td>\n  </tr>\n  ");
/* 2737 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2738 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2742 */                             if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2743 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                             }
/*      */                             
/* 2746 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2747 */                             out.write("\n  <script language=\"JavaScript\">\n\tfunction confirmDelete()\n \t {\n  var s = confirm(\"");
/* 2748 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.delete1.text"));
/* 2749 */                             out.write("\")\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=deleteMO&type=Port-Test&select=");
/* 2750 */                             out.print(resourceid);
/* 2751 */                             out.write("\";\n\t }\n\t function confirmManage()\n \t {\n  var s = confirm(\"");
/* 2752 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2753 */                             out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2754 */                             if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2756 */                             out.write("\";\n\t }\n\n         function confirmUnManage()\n \t {\n        \t  var show_msg=\"false\";\n              var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2757 */                             out.print(request.getParameter("resourceid"));
/* 2758 */                             out.write("; //No i18n\n              $.ajax({\n                type:'POST', //No i18n\n                url:url,\n                async:false,\n                success: function(data)\n                {\n                  show_msg=data\n                }\n              });\n              if(show_msg.indexOf(\"true\")>-1)\n              {\n                  alert(\"");
/* 2759 */                             out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2760 */                             out.write("\");\n\t        \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2761 */                             if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2763 */                             out.write("\";\n\t         }\n           else { \n\t\t        var s = confirm(\"");
/* 2764 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2765 */                             out.write("\");\n      \t\t\t  if (s){\n      \t\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2766 */                             if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2768 */                             out.write("\"; //No I18N\n\t\t\t\t\t  }\n\t\t       } \n\t }\n\t \t");
/*      */                             
/* 2770 */                             if (request.getParameter("editPage") != null)
/*      */                             {
/* 2772 */                               out.write("\n\t\t\ttoggleDiv('edit');\n\t\t");
/*      */                             }
/*      */                             
/* 2775 */                             out.write("\n  </script>\n  ");
/*      */                             
/* 2777 */                             IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2778 */                             _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2779 */                             _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2781 */                             _jspx_th_c_005fif_005f4.setTest("${!empty ADMIN || !empty DEMO}");
/* 2782 */                             int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2783 */                             if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                               for (;;) {
/* 2785 */                                 out.write(10);
/*      */                                 
/* 2787 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2788 */                                 _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2789 */                                 _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f4);
/*      */                                 
/* 2791 */                                 _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2792 */                                 int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2793 */                                 if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 2795 */                                     out.write("\n  <tr>\n    <td class=\"leftlinkstd\" >\n  <A href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 2796 */                                     out.print(FormatUtil.getString("am.webclient.dotnet.delete"));
/* 2797 */                                     out.write("</A></td>\n  \t</td>\n  </tr>\n");
/* 2798 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2799 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2803 */                                 if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2804 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 2807 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2808 */                                 out.write(10);
/* 2809 */                                 out.write(10);
/*      */                                 
/* 2811 */                                 PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2812 */                                 _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2813 */                                 _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f4);
/*      */                                 
/* 2815 */                                 _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 2816 */                                 int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2817 */                                 if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                   for (;;) {
/* 2819 */                                     out.write("\n\n<td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 2820 */                                     out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 2821 */                                     out.write("</a></td>\n\n");
/* 2822 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2823 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2827 */                                 if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2828 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                 }
/*      */                                 
/* 2831 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2832 */                                 out.write("\n\n  ");
/* 2833 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2834 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2838 */                             if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2839 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                             }
/*      */                             
/* 2842 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2843 */                             out.write("\n    ");
/*      */                             
/* 2845 */                             IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2846 */                             _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2847 */                             _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2849 */                             _jspx_th_c_005fif_005f5.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 2850 */                             int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2851 */                             if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                               for (;;) {
/* 2853 */                                 out.write("\n    <tr>\n    ");
/*      */                                 
/* 2855 */                                 if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                                 {
/*      */ 
/* 2858 */                                   out.write("\n      <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 2859 */                                   out.print(FormatUtil.getString("Manage"));
/* 2860 */                                   out.write("</A></td>\n      ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 2866 */                                   out.write("\n      <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 2867 */                                   out.print(FormatUtil.getString("UnManage"));
/* 2868 */                                   out.write("</A></td>\n      ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 2872 */                                 out.write("\n    </tr>\n    ");
/* 2873 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2874 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2878 */                             if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2879 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                             }
/*      */                             
/* 2882 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2883 */                             out.write(10);
/* 2884 */                             out.write(32);
/* 2885 */                             out.write(32);
/*      */                             
/* 2887 */                             IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2888 */                             _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2889 */                             _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2891 */                             _jspx_th_c_005fif_005f6.setTest("${!empty ADMIN || !empty DEMO }");
/* 2892 */                             int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2893 */                             if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                               for (;;) {
/* 2895 */                                 out.write("\n    \t<tr>\n        \t <td colspan=\"2\" class=\"leftlinkstd\">\n        \t ");
/* 2896 */                                 out.print(com.adventnet.appmanager.fault.FaultUtil.getAlertTemplateURL(request.getParameter("resourceid"), request.getParameter("name"), "Port-Test", "Service Monitoring"));
/* 2897 */                                 out.write("\n        \t </td>\n       \t</tr>\n  ");
/* 2898 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2899 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2903 */                             if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2904 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                             }
/*      */                             
/* 2907 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2908 */                             out.write("\n   ");
/*      */                             
/* 2910 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2911 */                             _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2912 */                             _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2914 */                             _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 2915 */                             int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2916 */                             if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                               for (;;) {
/* 2918 */                                 out.write("\n    ");
/*      */                                 
/* 2920 */                                 String resourceid_poll = request.getParameter("resourceid");
/* 2921 */                                 String resourcetype = request.getParameter("type");
/*      */                                 
/* 2923 */                                 out.write("\n      <tr>\n      <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n      <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 2924 */                                 out.print(resourceid_poll);
/* 2925 */                                 out.write("&resourcetype=");
/* 2926 */                                 out.print(resourcetype);
/* 2927 */                                 out.write("\" class=\"new-left-links\"> ");
/* 2928 */                                 out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 2929 */                                 out.write("</a></td>\n    </tr>\n    ");
/* 2930 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2931 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2935 */                             if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2936 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                             }
/*      */                             
/* 2939 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2940 */                             out.write("\n    ");
/*      */                             
/* 2942 */                             PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2943 */                             _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2944 */                             _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2946 */                             _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 2947 */                             int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2948 */                             if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                               for (;;) {
/* 2950 */                                 out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 2951 */                                 out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 2952 */                                 out.write("</a></td>\n          </td>\n    ");
/* 2953 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2954 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2958 */                             if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2959 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                             }
/*      */                             
/* 2962 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2963 */                             out.write("\n    ");
/* 2964 */                             out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                             
/* 2966 */                             if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                             {
/* 2968 */                               Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 2969 */                               String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                               
/* 2971 */                               String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 2972 */                               String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 2973 */                               if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                               {
/* 2975 */                                 if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                                 {
/*      */ 
/* 2978 */                                   out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2979 */                                   out.print(ciInfoUrl);
/* 2980 */                                   out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 2981 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 2982 */                                   out.write("</a></td>");
/* 2983 */                                   out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2984 */                                   out.print(ciRLUrl);
/* 2985 */                                   out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 2986 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 2987 */                                   out.write("</a></td>");
/* 2988 */                                   out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                                 }
/* 2992 */                                 else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                                 {
/*      */ 
/* 2995 */                                   out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 2996 */                                   out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 2997 */                                   out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2998 */                                   out.print(ciInfoUrl);
/* 2999 */                                   out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3000 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3001 */                                   out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3002 */                                   out.print(ciRLUrl);
/* 3003 */                                   out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3004 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3005 */                                   out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                             
/* 3010 */                             out.write("\n \n \n\n");
/* 3011 */                             out.write("\n</table>\n<br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3012 */                             out.print(FormatUtil.getString("am.webclient.dotnet.rca"));
/* 3013 */                             out.write("</td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n  <td width=\"49%\" >\n  <a class=\"new-left-links\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3014 */                             out.print(resourceid);
/* 3015 */                             out.write("&attributeid=");
/* 3016 */                             out.print((String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID"));
/* 3017 */                             out.write("')\"> ");
/* 3018 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3019 */                             out.write(" </a>\n  </td>\n  <td width=\"51%\"  > <a  href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3020 */                             out.print(resourceid);
/* 3021 */                             out.write("&attributeid=");
/* 3022 */                             out.print((String)((HashMap)request.getAttribute("systeminfo")).get("HEALTHID"));
/* 3023 */                             out.write("')\">");
/* 3024 */                             out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "153")));
/* 3025 */                             out.write("</a></td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\">\n\t <a href=\"javascript:void(0)\" class=\"new-left-links\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3026 */                             out.print(resourceid);
/* 3027 */                             out.write("&attributeid=152')\">");
/* 3028 */                             out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 3029 */                             out.write("</a></td>\n\t <td width=\"51%\"> <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3030 */                             out.print(resourceid);
/* 3031 */                             out.write("&attributeid=152')\" >");
/* 3032 */                             out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "152")));
/* 3033 */                             out.write("</a></td>\n    </td>\n  </tr>\n</table>\n");
/*      */                             
/* 3035 */                             ArrayList menupos = new ArrayList(5);
/* 3036 */                             if (request.isUserInRole("OPERATOR"))
/*      */                             {
/*      */ 
/* 3039 */                               menupos.add("179");
/* 3040 */                               menupos.add("200");
/* 3041 */                               menupos.add("222");
/* 3042 */                               menupos.add("242");
/* 3043 */                               menupos.add("158");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 3049 */                               menupos.add("300");
/*      */                             }
/* 3051 */                             pageContext.setAttribute("menupos", menupos);
/*      */                             
/* 3053 */                             out.write("\n<br>\n  ");
/* 3054 */                             out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                             
/*      */ 
/*      */ 
/* 3058 */                             boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3059 */                             if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition)
/*      */                             {
/* 3061 */                               showAssociatedBSG = false;
/*      */                               
/*      */ 
/*      */ 
/* 3065 */                               CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3066 */                               CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3067 */                               String loginName = request.getUserPrincipal().getName();
/* 3068 */                               CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                               
/* 3070 */                               if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                               {
/* 3072 */                                 showAssociatedBSG = true;
/*      */                               }
/*      */                             }
/* 3075 */                             String monitorType = request.getParameter("type");
/* 3076 */                             ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3077 */                             boolean mon = conf1.isConfMonitor(monitorType);
/* 3078 */                             if (showAssociatedBSG)
/*      */                             {
/* 3080 */                               Hashtable associatedmgs = new Hashtable();
/* 3081 */                               String resId = request.getParameter("resourceid");
/* 3082 */                               request.setAttribute("associatedmgs", com.adventnet.appmanager.fault.FaultUtil.getAdminAssociatedMG(resId, request));
/* 3083 */                               if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                               {
/* 3085 */                                 mon = false;
/*      */                               }
/*      */                               
/* 3088 */                               if (!mon)
/*      */                               {
/* 3090 */                                 out.write(10);
/* 3091 */                                 out.write(10);
/*      */                                 
/* 3093 */                                 IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3094 */                                 _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3095 */                                 _jspx_th_c_005fif_005f7.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3097 */                                 _jspx_th_c_005fif_005f7.setTest("${!empty associatedmgs}");
/* 3098 */                                 int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3099 */                                 if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                   for (;;) {
/* 3101 */                                     out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3102 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3103 */                                     out.write("</td>\n        </tr>\n        ");
/*      */                                     
/* 3105 */                                     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3106 */                                     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3107 */                                     _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f7);
/*      */                                     
/* 3109 */                                     _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                                     
/* 3111 */                                     _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                                     
/* 3113 */                                     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3114 */                                     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                     try {
/* 3116 */                                       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3117 */                                       if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                         for (;;) {
/* 3119 */                                           out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3120 */                                           if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3178 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3179 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3122 */                                           out.write("&method=showApplication\" title=\"");
/* 3123 */                                           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3178 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3179 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3125 */                                           out.write("\"  class=\"new-left-links\">\n         ");
/* 3126 */                                           if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3178 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3179 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3128 */                                           out.write("\n    \t");
/* 3129 */                                           out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3130 */                                           out.write("\n         </a></td>\n        <td>");
/*      */                                           
/* 3132 */                                           PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3133 */                                           _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3134 */                                           _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                           
/* 3136 */                                           _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 3137 */                                           int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3138 */                                           if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                             for (;;) {
/* 3140 */                                               out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3141 */                                               if (_jspx_meth_c_005fout_005f7(_jspx_th_logic_005fpresent_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3178 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 3179 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 3143 */                                               out.write(39);
/* 3144 */                                               out.write(44);
/* 3145 */                                               out.write(39);
/* 3146 */                                               out.print(resId);
/* 3147 */                                               out.write(39);
/* 3148 */                                               out.write(44);
/* 3149 */                                               out.write(39);
/* 3150 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3151 */                                               out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3152 */                                               out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3153 */                                               out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3154 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3155 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3159 */                                           if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3160 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3178 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3179 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3163 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3164 */                                           out.write("</td>\n        </tr>\n\t");
/* 3165 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3166 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3170 */                                       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3178 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3179 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 3174 */                                         int tmp5839_5838 = 0; int[] tmp5839_5836 = _jspx_push_body_count_c_005fforEach_005f0; int tmp5841_5840 = tmp5839_5836[tmp5839_5838];tmp5839_5836[tmp5839_5838] = (tmp5841_5840 - 1); if (tmp5841_5840 <= 0) break;
/* 3175 */                                         out = _jspx_page_context.popBody(); }
/* 3176 */                                       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                     } finally {
/* 3178 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3179 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                     }
/* 3181 */                                     out.write("\n      </table>\n ");
/* 3182 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3183 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3187 */                                 if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3188 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                 }
/*      */                                 
/* 3191 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3192 */                                 out.write(10);
/* 3193 */                                 out.write(32);
/*      */                                 
/* 3195 */                                 IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3196 */                                 _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3197 */                                 _jspx_th_c_005fif_005f8.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3199 */                                 _jspx_th_c_005fif_005f8.setTest("${empty associatedmgs}");
/* 3200 */                                 int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3201 */                                 if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                   for (;;) {
/* 3203 */                                     out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 3204 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3205 */                                     out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 3206 */                                     out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3207 */                                     out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 3208 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3209 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3213 */                                 if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3214 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                 }
/*      */                                 
/* 3217 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3218 */                                 out.write(10);
/* 3219 */                                 out.write(32);
/* 3220 */                                 out.write(10);
/*      */ 
/*      */                               }
/* 3223 */                               else if (mon)
/*      */                               {
/*      */ 
/*      */ 
/* 3227 */                                 out.write(10);
/*      */                                 
/* 3229 */                                 IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3230 */                                 _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3231 */                                 _jspx_th_c_005fif_005f9.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 3233 */                                 _jspx_th_c_005fif_005f9.setTest("${!empty associatedmgs}");
/* 3234 */                                 int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3235 */                                 if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                   for (;;) {
/* 3237 */                                     out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 3238 */                                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f9, _jspx_page_context))
/*      */                                       return;
/* 3240 */                                     out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                                     
/* 3242 */                                     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3243 */                                     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3244 */                                     _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f9);
/*      */                                     
/* 3246 */                                     _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                                     
/* 3248 */                                     _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                                     
/* 3250 */                                     _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 3251 */                                     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                                     try {
/* 3253 */                                       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3254 */                                       if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                         for (;;) {
/* 3256 */                                           out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 3257 */                                           if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3318 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 3319 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 3259 */                                           out.write("&method=showApplication\" title=\"");
/* 3260 */                                           if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3318 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 3319 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 3262 */                                           out.write("\"  class=\"staticlinks\">\n         ");
/* 3263 */                                           if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3318 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 3319 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 3265 */                                           out.write("\n    \t");
/* 3266 */                                           out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3267 */                                           out.write("</a></span>\t\n\t\t ");
/*      */                                           
/* 3269 */                                           PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3270 */                                           _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3271 */                                           _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                           
/* 3273 */                                           _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 3274 */                                           int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3275 */                                           if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                             for (;;) {
/* 3277 */                                               out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 3278 */                                               if (_jspx_meth_c_005fout_005f11(_jspx_th_logic_005fpresent_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3318 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 3319 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 3280 */                                               out.write(39);
/* 3281 */                                               out.write(44);
/* 3282 */                                               out.write(39);
/* 3283 */                                               out.print(resId);
/* 3284 */                                               out.write(39);
/* 3285 */                                               out.write(44);
/* 3286 */                                               out.write(39);
/* 3287 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3288 */                                               out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 3289 */                                               out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3290 */                                               out.write("\"  title=\"");
/* 3291 */                                               if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3318 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 3319 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                               }
/* 3293 */                                               out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 3294 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3295 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3299 */                                           if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3300 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3318 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 3319 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 3303 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3304 */                                           out.write("\n\n\t\t \t");
/* 3305 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3306 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3310 */                                       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3318 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 3319 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 3314 */                                         int tmp6865_6864 = 0; int[] tmp6865_6862 = _jspx_push_body_count_c_005fforEach_005f1; int tmp6867_6866 = tmp6865_6862[tmp6865_6864];tmp6865_6862[tmp6865_6864] = (tmp6867_6866 - 1); if (tmp6867_6866 <= 0) break;
/* 3315 */                                         out = _jspx_page_context.popBody(); }
/* 3316 */                                       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                                     } finally {
/* 3318 */                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 3319 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                     }
/* 3321 */                                     out.write("\n\t\n\t\t\t</td>\n\t ");
/* 3322 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3323 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3327 */                                 if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3328 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                 }
/*      */                                 
/* 3331 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3332 */                                 out.write(10);
/* 3333 */                                 out.write(32);
/* 3334 */                                 if (_jspx_meth_c_005fif_005f10(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 3336 */                                 out.write(32);
/* 3337 */                                 out.write(10);
/*      */                               }
/*      */                               
/*      */                             }
/* 3341 */                             else if (mon)
/*      */                             {
/*      */ 
/* 3344 */                               out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 3345 */                               if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 3347 */                               out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                             }
/*      */                             
/*      */ 
/* 3351 */                             out.write(9);
/* 3352 */                             out.write(9);
/* 3353 */                             out.write(10);
/* 3354 */                             out.write(10);
/* 3355 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3356 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3359 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3360 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3363 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3364 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 3367 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3368 */                         out.write(32);
/*      */                         
/* 3370 */                         PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3371 */                         _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3372 */                         _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 3374 */                         _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                         
/* 3376 */                         _jspx_th_tiles_005fput_005f4.setType("string");
/* 3377 */                         int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3378 */                         if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 3379 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 3380 */                             out = _jspx_page_context.pushBody();
/* 3381 */                             _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 3382 */                             _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 3385 */                             out.write(10);
/*      */                             
/*      */ 
/* 3388 */                             String tipStartMonitor = FormatUtil.getString("am.webclient.portdetails.starttime.tooltip");
/* 3389 */                             String tipDownTime = FormatUtil.getString("am.webclient.portdetails.downtime.tooltip");
/* 3390 */                             String tipTimePeriod = FormatUtil.getString("am.webclient.portdetails.timeperiod.tooltip");
/* 3391 */                             String tipCurrentStatus = FormatUtil.getString("am.webclient.portdetails.currentstatus.tooltip");
/* 3392 */                             String tipAssThr = FormatUtil.getString("am.webclient.portdetails.assthreshold.tooltip");
/* 3393 */                             String tipCurResp = FormatUtil.getString("am.webclient.portdetails.curresp.tooltip");
/* 3394 */                             String tipCurRespThr = FormatUtil.getString("am.webclient.portdetails.currespthres.tooltip");
/* 3395 */                             String tipAssThrRes = FormatUtil.getString("am.webclient.portdetails.assthresRes.tooltip");
/*      */                             
/*      */ 
/* 3398 */                             out.write("\n<script language=\"JavaScript1.2\" src=\"../template/appmanager.js\"></script>\n<SCRIPT LANGUAGE=\"javascript\" SRC=\"../webclient/common/js/windowFunctions.js\"></SCRIPT>\n");
/*      */                             
/* 3400 */                             IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3401 */                             _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3402 */                             _jspx_th_c_005fif_005f11.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3404 */                             _jspx_th_c_005fif_005f11.setTest("${ !empty error}");
/* 3405 */                             int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3406 */                             if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                               for (;;) {
/* 3408 */                                 out.write(" <span class=\"message\" >");
/* 3409 */                                 out.print((String)request.getAttribute("error"));
/* 3410 */                                 out.write("</span>\n");
/* 3411 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3412 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3416 */                             if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3417 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                             }
/*      */                             
/* 3420 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3421 */                             out.write("\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n\t");
/*      */                             
/* 3423 */                             Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 3424 */                             String aid = request.getParameter("haid");
/* 3425 */                             String haName = null;
/* 3426 */                             if (aid != null)
/*      */                             {
/* 3428 */                               haName = (String)ht.get(aid);
/*      */                             }
/*      */                             
/* 3431 */                             out.write(10);
/* 3432 */                             out.write(9);
/*      */                             
/* 3434 */                             IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3435 */                             _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3436 */                             _jspx_th_c_005fif_005f12.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3438 */                             _jspx_th_c_005fif_005f12.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 3439 */                             int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3440 */                             if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                               for (;;) {
/* 3442 */                                 out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 3443 */                                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 3444 */                                 out.write(" &gt; ");
/* 3445 */                                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 3446 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 3447 */                                 out.print(resourcename);
/* 3448 */                                 out.write(" </span></td>\n\t");
/* 3449 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3450 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3454 */                             if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3455 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                             }
/*      */                             
/* 3458 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3459 */                             out.write(10);
/* 3460 */                             out.write(9);
/*      */                             
/* 3462 */                             IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3463 */                             _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3464 */                             _jspx_th_c_005fif_005f13.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3466 */                             _jspx_th_c_005fif_005f13.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 3467 */                             int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3468 */                             if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                               for (;;) {
/* 3470 */                                 out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 3471 */                                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 3472 */                                 out.write(" &gt; ");
/* 3473 */                                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes("Port-Test"));
/* 3474 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 3475 */                                 out.print(resourcename);
/* 3476 */                                 out.write(" </span></td>\n\t");
/* 3477 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3478 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3482 */                             if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3483 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                             }
/*      */                             
/* 3486 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3487 */                             out.write("\n    </tr>\n</table>\n<div id=\"edit\" style=\"DISPLAY: none\">\n");
/* 3488 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/TCPMonitoring.jsp?reconfigure=true", out, false);
/* 3489 */                             out.write("</div>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"60%\" valign=\"top\">\n<table width=\"96%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n        <tr>\n          <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 3490 */                             out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 3491 */                             out.write(" </td>\n        </tr>\n        <tr>\n          <td width=\"39%\" class=\"monitorinfoodd\">");
/* 3492 */                             out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 3493 */                             out.write(" </td>\n          <td width=\"61%\" class=\"monitorinfoodd\">");
/* 3494 */                             out.print(resourcename);
/* 3495 */                             out.write("</td>\n        </tr>\n\t\t");
/* 3496 */                             out.write("<!--$Id$-->\n");
/*      */                             
/* 3498 */                             String hostName = "localhost";
/*      */                             try {
/* 3500 */                               hostName = InetAddress.getLocalHost().getHostName();
/*      */                             } catch (Exception ex) {
/* 3502 */                               ex.printStackTrace();
/*      */                             }
/* 3504 */                             String portNumber = System.getProperty("webserver.port");
/* 3505 */                             String styleClass = "monitorinfoodd";
/* 3506 */                             if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 3507 */                               styleClass = "whitegrayborder-conf-mon";
/*      */                             }
/*      */                             
/* 3510 */                             out.write(10);
/*      */                             
/* 3512 */                             PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3513 */                             _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3514 */                             _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3516 */                             _jspx_th_logic_005fpresent_005f5.setRole("ENTERPRISEADMIN");
/* 3517 */                             int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3518 */                             if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                               for (;;) {
/* 3520 */                                 out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 3521 */                                 out.print(styleClass);
/* 3522 */                                 out.write(34);
/* 3523 */                                 out.write(62);
/* 3524 */                                 out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 3525 */                                 out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 3526 */                                 out.print(styleClass);
/* 3527 */                                 out.write(34);
/* 3528 */                                 out.write(62);
/* 3529 */                                 out.print(hostName);
/* 3530 */                                 out.write(95);
/* 3531 */                                 out.print(portNumber);
/* 3532 */                                 out.write("</td>\n</tr>\n");
/* 3533 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3534 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3538 */                             if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3539 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                             }
/*      */                             
/* 3542 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3543 */                             out.write(10);
/* 3544 */                             out.write("\n        <tr>\n          <td class=\"monitorinfoeven\" valign=\"top\">");
/* 3545 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3546 */                             out.write("</td>\n          <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3547 */                             out.print(resourceid);
/* 3548 */                             out.write("&attributeid=");
/* 3549 */                             out.print((String)systeminfo.get("HEALTHID"));
/* 3550 */                             out.write("')\">");
/* 3551 */                             out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "153")));
/* 3552 */                             out.write("</a>\n\t\t   ");
/* 3553 */                             out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + "153" + "#" + "MESSAGE"), "153", alert.getProperty(resourceid + "#" + "153"), resourceid));
/* 3554 */                             out.write("\n\t\t   ");
/* 3555 */                             if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, "153") != 0) {
/* 3556 */                               out.write("\n\t\t   <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 3557 */                               out.print(resourceid + "_153");
/* 3558 */                               out.write("&monitortype=Port-Test')\">");
/* 3559 */                               out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 3560 */                               out.write("</a></span>\n           ");
/*      */                             }
/* 3562 */                             out.write("\n\t\t  </td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3563 */                             out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 3564 */                             out.write(" </td>\n          <td class=\"monitorinfoodd\">");
/* 3565 */                             out.print(FormatUtil.getString("am.webclient.servicemonitor.monitorname"));
/* 3566 */                             out.write("&nbsp;</td>\n        </tr>\n        ");
/*      */                             
/* 3568 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3569 */                             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3570 */                             _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3572 */                             _jspx_th_logic_005fnotEmpty_005f0.setName("info");
/* 3573 */                             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3574 */                             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                               for (;;) {
/* 3576 */                                 out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3577 */                                 out.print(FormatUtil.getString("am.webclient.servicemonitor.serviceport"));
/* 3578 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">");
/* 3579 */                                 out.print(info.get("PORT"));
/* 3580 */                                 out.write("&nbsp;</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3581 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.command.execute"));
/* 3582 */                                 out.write("</td>\n          <td class=\"monitorinfoodd\">");
/*      */                                 
/* 3584 */                                 Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.get(Truncate.class);
/* 3585 */                                 _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 3586 */                                 _jspx_th_am_005fTruncate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                 
/* 3588 */                                 _jspx_th_am_005fTruncate_005f0.setTooltip("true");
/*      */                                 
/* 3590 */                                 _jspx_th_am_005fTruncate_005f0.setLength(50);
/* 3591 */                                 int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 3592 */                                 if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 3593 */                                   if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 3594 */                                     out = _jspx_page_context.pushBody();
/* 3595 */                                     _jspx_th_am_005fTruncate_005f0.setBodyContent((BodyContent)out);
/* 3596 */                                     _jspx_th_am_005fTruncate_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3599 */                                     out.print(info.get("COMMAND"));
/* 3600 */                                     int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 3601 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3604 */                                   if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 3605 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3608 */                                 if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 3609 */                                   this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0); return;
/*      */                                 }
/*      */                                 
/* 3612 */                                 this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 3613 */                                 out.write("&nbsp;</td>");
/* 3614 */                                 out.write("\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3615 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.matchcontent"));
/* 3616 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">");
/* 3617 */                                 out.print(info.get("SEARCH"));
/* 3618 */                                 out.write("&nbsp;</td>\n        </tr>\n        ");
/* 3619 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3620 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3624 */                             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3625 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                             }
/*      */                             
/* 3628 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3629 */                             out.write(32);
/*      */                             
/* 3631 */                             EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3632 */                             _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 3633 */                             _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3635 */                             _jspx_th_logic_005fempty_005f0.setName("info");
/* 3636 */                             int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 3637 */                             if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                               for (;;) {
/* 3639 */                                 out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3640 */                                 out.print(FormatUtil.getString("am.webclient.servicemonitor.serviceport"));
/* 3641 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">-</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3642 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.command.execute"));
/* 3643 */                                 out.write("</td>\n          <td class=\"monitorinfoodd\">-</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3644 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.matchcontent"));
/* 3645 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">-</td>\n        </tr>\n        ");
/* 3646 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 3647 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3651 */                             if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 3652 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                             }
/*      */                             
/* 3655 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 3656 */                             out.write(32);
/*      */                             
/* 3658 */                             EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3659 */                             _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 3660 */                             _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3662 */                             _jspx_th_logic_005fempty_005f1.setName("systeminfo");
/* 3663 */                             int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 3664 */                             if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */                               for (;;) {
/* 3666 */                                 out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3667 */                                 out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3668 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">-&nbsp;</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3669 */                                 out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 3670 */                                 out.write("</td>\n          <td class=\"whitegrayborder\">-</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3671 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3672 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">-</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3673 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3674 */                                 out.write("</td>\n          <td class=\"whitegrayborder\">-</td>\n        </tr>\n        ");
/* 3675 */                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 3676 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3680 */                             if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 3681 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */                             }
/*      */                             
/* 3684 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 3685 */                             out.write(32);
/*      */                             
/* 3687 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3688 */                             _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3689 */                             _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3691 */                             _jspx_th_logic_005fnotEmpty_005f1.setName("systeminfo");
/* 3692 */                             int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3693 */                             if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                               for (;;) {
/* 3695 */                                 out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3696 */                                 out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3697 */                                 out.write("</td>\n          ");
/*      */                                 
/* 3699 */                                 if (systeminfo.get("host_resid") != null)
/*      */                                 {
/* 3701 */                                   out.write("\n\t\t    <td class=\"monitorinfoeven\"><a href=\"showresource.do?resourceid=");
/* 3702 */                                   out.print(systeminfo.get("host_resid"));
/* 3703 */                                   out.write("&method=showResourceForResourceID\" class=\"staticlinks\" title=\"");
/* 3704 */                                   out.print(systeminfo.get("HOSTNAME"));
/* 3705 */                                   out.write(34);
/* 3706 */                                   out.write(32);
/* 3707 */                                   out.write(62);
/* 3708 */                                   out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 3709 */                                   out.write("&nbsp;(");
/* 3710 */                                   out.print(systeminfo.get("HOSTIP"));
/* 3711 */                                   out.write(")</a></td>\n\t\t\t");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3716 */                                   out.write("\n             <td class=\"monitorinfoeven\" title=\"");
/* 3717 */                                   out.print(systeminfo.get("HOSTNAME"));
/* 3718 */                                   out.write(34);
/* 3719 */                                   out.write(32);
/* 3720 */                                   out.write(62);
/* 3721 */                                   out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 3722 */                                   out.write("&nbsp;(");
/* 3723 */                                   out.print(systeminfo.get("HOSTIP"));
/* 3724 */                                   out.write(")</td>\n\t\t\t");
/*      */                                 }
/* 3726 */                                 out.write("\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3727 */                                 out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 3728 */                                 out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 3729 */                                 out.print(FormatUtil.getString((String)systeminfo.get("HOSTOS")));
/* 3730 */                                 out.write("&nbsp;</td>\n        </tr>\n        ");
/*      */                                 
/* 3732 */                                 NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3733 */                                 _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 3734 */                                 _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                 
/* 3736 */                                 _jspx_th_logic_005fnotEmpty_005f2.setName("recent5Alarms");
/* 3737 */                                 int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 3738 */                                 if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                                   for (;;) {
/* 3740 */                                     out.write("\n        ");
/*      */                                     
/* 3742 */                                     ArrayList recent = (ArrayList)((ArrayList)request.getAttribute("recent5Alarms")).get(0);
/*      */                                     
/* 3744 */                                     out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3745 */                                     out.print(FormatUtil.getString("am.webclient.mysql.lastalarm"));
/* 3746 */                                     out.write("</td>\n          <td class=\"monitorinfoeven\"><a href=\"/fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 3747 */                                     out.print(recent.get(2));
/* 3748 */                                     out.write("&source=");
/* 3749 */                                     out.print(recent.get(4));
/* 3750 */                                     out.write("&category=");
/* 3751 */                                     out.print(recent.get(0));
/* 3752 */                                     out.write("&redirectto=");
/* 3753 */                                     out.print(encodeurl);
/* 3754 */                                     out.write("\"  class=\"resourcename\">");
/* 3755 */                                     out.print(getTruncatedAlertMessage((String)recent.get(3)));
/* 3756 */                                     out.write("</a>&nbsp;</td>\n        </tr>\n        ");
/* 3757 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 3758 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3762 */                                 if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 3763 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                                 }
/*      */                                 
/* 3766 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 3767 */                                 out.write(32);
/*      */                                 
/* 3769 */                                 EmptyTag _jspx_th_logic_005fempty_005f2 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3770 */                                 _jspx_th_logic_005fempty_005f2.setPageContext(_jspx_page_context);
/* 3771 */                                 _jspx_th_logic_005fempty_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                 
/* 3773 */                                 _jspx_th_logic_005fempty_005f2.setName("recent5Alarms");
/* 3774 */                                 int _jspx_eval_logic_005fempty_005f2 = _jspx_th_logic_005fempty_005f2.doStartTag();
/* 3775 */                                 if (_jspx_eval_logic_005fempty_005f2 != 0) {
/*      */                                   for (;;) {
/* 3777 */                                     out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3778 */                                     out.print(FormatUtil.getString("am.webclient.mysql.lastalarm"));
/* 3779 */                                     out.write("</td>\n          <td class=\"monitorinfoeven\">-</td>\n        </tr>\n        ");
/* 3780 */                                     int evalDoAfterBody = _jspx_th_logic_005fempty_005f2.doAfterBody();
/* 3781 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3785 */                                 if (_jspx_th_logic_005fempty_005f2.doEndTag() == 5) {
/* 3786 */                                   this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2); return;
/*      */                                 }
/*      */                                 
/* 3789 */                                 this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 3790 */                                 out.write("\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3791 */                                 out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3792 */                                 out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 3793 */                                 out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 3794 */                                 out.write("&nbsp;</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3795 */                                 out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3796 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\">");
/* 3797 */                                 out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 3798 */                                 out.write("&nbsp;</td>\n        </tr>\n        ");
/* 3799 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3800 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3804 */                             if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3805 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                             }
/*      */                             
/* 3808 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3809 */                             out.write("\n        ");
/* 3810 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_trstrip.jsp", out, false);
/* 3811 */                             out.write("\n        </table>\n    </td>\n    <td valign=\"top\">\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n        <tr>\n          <td class=\"tableheadingbborder\">");
/* 3812 */                             out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 3813 */                             out.write(" </td>\n        </tr>\n        <tr>\n          <td align=\"center\" ><table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n          \t<tr>\n          \t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3814 */                             if (_jspx_meth_c_005fout_005f12(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3816 */                             out.write("&period=1&resourcename=");
/* 3817 */                             if (_jspx_meth_c_005fout_005f13(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3819 */                             out.write("')\">\n      <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3820 */                             out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3821 */                             out.write("\"></a></td>\n          <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3822 */                             if (_jspx_meth_c_005fout_005f14(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3824 */                             out.write("&period=2&resourcename=");
/* 3825 */                             if (_jspx_meth_c_005fout_005f15(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3827 */                             out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3828 */                             out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3829 */                             out.write("\"></a></td>\n      </tr>\n</table></td>\n        </tr>\n        <tr>\n          <td align=\"center\" >");
/*      */                             
/* 3831 */                             AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3832 */                             _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 3833 */                             _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3835 */                             _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                             
/* 3837 */                             _jspx_th_awolf_005fpiechart_005f0.setWidth("300");
/*      */                             
/* 3839 */                             _jspx_th_awolf_005fpiechart_005f0.setHeight("180");
/*      */                             
/* 3841 */                             _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                             
/* 3843 */                             _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                             
/* 3845 */                             _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                             
/* 3847 */                             _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 3848 */                             int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3849 */                             if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3850 */                               if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3851 */                                 out = _jspx_page_context.pushBody();
/* 3852 */                                 _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3853 */                                 _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3856 */                                 out.write("\n            ");
/*      */                                 
/* 3858 */                                 Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3859 */                                 _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3860 */                                 _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                                 
/* 3862 */                                 _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3863 */                                 int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3864 */                                 if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3865 */                                   if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3866 */                                     out = _jspx_page_context.pushBody();
/* 3867 */                                     _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3868 */                                     _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3871 */                                     out.write(32);
/*      */                                     
/* 3873 */                                     AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3874 */                                     _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3875 */                                     _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                     
/* 3877 */                                     _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                                     
/* 3879 */                                     _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 3880 */                                     int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3881 */                                     if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3882 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                     }
/*      */                                     
/* 3885 */                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3886 */                                     out.write("\n            ");
/*      */                                     
/* 3888 */                                     AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3889 */                                     _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3890 */                                     _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                     
/* 3892 */                                     _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                                     
/* 3894 */                                     _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 3895 */                                     int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3896 */                                     if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3897 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                     }
/*      */                                     
/* 3900 */                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3901 */                                     out.write(32);
/* 3902 */                                     int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3903 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3906 */                                   if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3907 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3910 */                                 if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3911 */                                   this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                                 }
/*      */                                 
/* 3914 */                                 this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3915 */                                 out.write(32);
/* 3916 */                                 int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3917 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3920 */                               if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3921 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3924 */                             if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3925 */                               this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */                             }
/*      */                             
/* 3928 */                             this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3929 */                             out.write("\n          </td>\n        </tr>\n        <tr>\n          <td><table align=left width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t<tr>\n\t\t\t<td width=\"50%\" colspan=\"2\" height=\"25\" class=\"yellowgrayborder\" title=\"");
/* 3930 */                             out.print(tipCurrentStatus);
/* 3931 */                             out.write(34);
/* 3932 */                             out.write(32);
/* 3933 */                             out.write(62);
/* 3934 */                             out.print(FormatUtil.getString("am.webclient.mssqldetails.currnetstatus"));
/* 3935 */                             out.write("\n\t\t\t<a style=\"position:relative; top:2px;\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3936 */                             out.print(resourceid);
/* 3937 */                             out.write("&attributeid=152')\">");
/* 3938 */                             out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "152")));
/* 3939 */                             out.write("</a></td>\n\t\t\t<td width=\"49%\" class=\"yellowgrayborder\" align=\"right\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3940 */                             out.print(resourceid);
/* 3941 */                             out.write("&attributeIDs=152,153&attributeToSelect=152&redirectto=");
/* 3942 */                             out.print(encodeurl);
/* 3943 */                             out.write("\" class=\"staticlinks\">");
/* 3944 */                             out.print(ALERTCONFIG_TEXT);
/* 3945 */                             out.write("</a>&nbsp;</td>\n\t\t\t</tr>\n            </table></td>\n        </tr>\n      </table>\n    </td>\n  </tr>\n</table>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3946 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 3947 */                             out.write("</td></tr></table>\n <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td>&nbsp;</td>\n        </tr>\n      </table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n  <tr>\n    <td valign=\"top\">\n\n      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\" >\n        <tr>\n          <td height=\"25\"  class=\"tableheading\"  title=\"");
/* 3948 */                             out.print(tipCurResp);
/* 3949 */                             out.write(34);
/* 3950 */                             out.write(32);
/* 3951 */                             out.write(62);
/* 3952 */                             out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3953 */                             out.write(" </td>\n        </tr>\n      </table>\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\">\n        <tr>\n          <td width=\"50%\" valign=\"top\">\n            <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t<td width=\"100%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3954 */                             if (_jspx_meth_c_005fout_005f16(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3956 */                             out.write("&attributeid=151&period=-7',740,550)\">\n\t\t\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"0\" vspace=\"5\" border=\"0\"  title='");
/* 3957 */                             out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3958 */                             out.write("'></a>\n\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3959 */                             if (_jspx_meth_c_005fout_005f17(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 3961 */                             out.write("&attributeid=151&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"20\" vspace=\"5\" border=\"0\"  title='");
/* 3962 */                             out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3963 */                             out.write("'></a></td>\n\t\t\t\t</tr>\n              <tr>\n                <td colspan=\"2\"> ");
/*      */                             
/* 3965 */                             TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3966 */                             _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3967 */                             _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 3969 */                             _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("perfGraph");
/*      */                             
/* 3971 */                             _jspx_th_awolf_005ftimechart_005f0.setWidth("330");
/*      */                             
/* 3973 */                             _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                             
/* 3975 */                             _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                             
/* 3977 */                             _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                             
/* 3979 */                             _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.responsetimeinms"));
/*      */                             
/* 3981 */                             _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/* 3982 */                             int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 3983 */                             if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 3984 */                               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3985 */                                 out = _jspx_page_context.pushBody();
/* 3986 */                                 _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 3987 */                                 _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3990 */                                 out.write("\n                  ");
/* 3991 */                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 3992 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3995 */                               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3996 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3999 */                             if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 4000 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                             }
/*      */                             
/* 4003 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 4004 */                             out.write(" </td>\n              </tr>\n            </table>\n          <td align=\"left\" width=\"50%\" valign=\"top\">\n            <br><br>\n            <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"lrbtborder\">\n              <tr>\n                <td class=\"columnheadingdelete\"><span class=\"bodytextbold\">");
/* 4005 */                             if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4007 */                             out.write("</span></td>");
/* 4008 */                             out.write("\n                <td class=\"columnheadingdelete\"><span class=\"bodytextbold\">");
/* 4009 */                             if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4011 */                             out.write("</span></td>");
/* 4012 */                             out.write("\n                <td class=\"columnheadingdelete\" title=\"");
/* 4013 */                             out.print(tipCurRespThr);
/* 4014 */                             out.write("\" ><span class=\"bodytextbold\">");
/* 4015 */                             if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4017 */                             out.write("</span></td>");
/* 4018 */                             out.write("\n              </tr>\n              <tr>\n                <td width=\"31%\" height=\"25\" class=\"whitegrayborder\" title=\"");
/* 4019 */                             out.print(tipCurResp);
/* 4020 */                             out.write(34);
/* 4021 */                             out.write(32);
/* 4022 */                             out.write(62);
/* 4023 */                             out.print(FormatUtil.getString("Current Value"));
/* 4024 */                             out.write(" </td>\n                <td width=\"26%\" height=\"25\" class=\"whitegrayborder\"> ");
/* 4025 */                             if (_jspx_meth_c_005fif_005f14(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 4027 */                             out.write(32);
/*      */                             
/* 4029 */                             IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4030 */                             _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 4031 */                             _jspx_th_c_005fif_005f15.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 4033 */                             _jspx_th_c_005fif_005f15.setTest("${responsetime != '-1'}");
/* 4034 */                             int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 4035 */                             if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                               for (;;) {
/* 4037 */                                 out.write(32);
/* 4038 */                                 if (_jspx_meth_fmt_005fformatNumber_005f0(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                   return;
/* 4040 */                                 out.write("\n                  ");
/* 4041 */                                 out.print(FormatUtil.getString("ms"));
/* 4042 */                                 out.write(32);
/* 4043 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4044 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4048 */                             if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4049 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                             }
/*      */                             
/* 4052 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4053 */                             out.write(" </td>\n                <td width=\"43%\" class=\"whitegrayborder\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4054 */                             out.print(resourceid);
/* 4055 */                             out.write("&attributeid=151')\">");
/* 4056 */                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "151")));
/* 4057 */                             out.write("</a></td>\n              </tr>\n              <tr>\n                <td  colspan=\"3\" height=\"22\" class=\"yellowgrayborder\" align=\"right\"  title=\"");
/* 4058 */                             out.print(tipAssThrRes);
/* 4059 */                             out.write("\" ><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4060 */                             out.print(resourceid);
/* 4061 */                             out.write("&attributeIDs=151&attributeToSelect=151&redirectto=");
/* 4062 */                             out.print(encodeurl);
/* 4063 */                             out.write("\" class=\"staticlinks\">");
/* 4064 */                             out.print(ALERTCONFIG_TEXT);
/* 4065 */                             out.write("</a></td>\n              </tr>\n            </table></td>\n        </tr>\n      </table>\n</table>\n<br>\n\t");
/* 4066 */                             out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 4067 */                             DialChartSupport dialGraph = null;
/* 4068 */                             dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 4069 */                             if (dialGraph == null) {
/* 4070 */                               dialGraph = new DialChartSupport();
/* 4071 */                               _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */                             }
/* 4073 */                             out.write(10);
/*      */                             
/*      */                             try
/*      */                             {
/* 4077 */                               String hostos = (String)systeminfo.get("HOSTOS");
/* 4078 */                               String hostname = (String)systeminfo.get("HOSTNAME");
/* 4079 */                               String hostid = (String)systeminfo.get("host_resid");
/* 4080 */                               boolean isConf = false;
/* 4081 */                               if ((systeminfo.get("isConf") != null) && (((String)systeminfo.get("isConf")).equals("true"))) {
/* 4082 */                                 isConf = true;
/*      */                               }
/* 4084 */                               com.adventnet.appmanager.db.RepairTables rt = new com.adventnet.appmanager.db.RepairTables();
/* 4085 */                               Properties property = new Properties();
/* 4086 */                               if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")) && (!hostos.equalsIgnoreCase("node")))
/*      */                               {
/* 4088 */                                 property = com.adventnet.appmanager.db.RepairTables.getValuesForHost(hostname, hostos);
/* 4089 */                                 if ((property != null) && (property.size() > 0))
/*      */                                 {
/* 4091 */                                   String cpuid = property.getProperty("cpuid");
/* 4092 */                                   String memid = property.getProperty("memid");
/* 4093 */                                   String diskid = property.getProperty("diskid");
/* 4094 */                                   String cpuvalue = property.getProperty("CPU Utilization");
/* 4095 */                                   String memvalue = property.getProperty("Memory Utilization");
/* 4096 */                                   String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + memid + "&period=0')";
/* 4097 */                                   String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + cpuid + "&period=0')";
/* 4098 */                                   String diskvalue = property.getProperty("Disk Utilization");
/* 4099 */                                   String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + diskid + "&period=0')";
/*      */                                   
/* 4101 */                                   if (!isConf) {
/* 4102 */                                     out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 4103 */                                     out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 4104 */                                     out.write(45);
/* 4105 */                                     if (systeminfo.get("host_resid") != null) {
/* 4106 */                                       out.write("<a href=\"showresource.do?resourceid=");
/* 4107 */                                       out.print(hostid);
/* 4108 */                                       out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 4109 */                                       out.print(hostname);
/* 4110 */                                       out.write("</a>");
/* 4111 */                                     } else { out.println(hostname); }
/* 4112 */                                     out.write("</td>\t");
/* 4113 */                                     out.write("\n  </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"30%\" valign=\"top\">\n    ");
/* 4114 */                                     out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 4115 */                                     out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">\n\n        <tr>\n         ");
/*      */                                     
/*      */ 
/* 4118 */                                     if (cpuvalue != null)
/*      */                                     {
/*      */ 
/* 4121 */                                       dialGraph.setValue(Long.parseLong(cpuvalue));
/* 4122 */                                       out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4123 */                                       out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4124 */                                       out.write(45);
/* 4125 */                                       out.print(cpuvalue);
/* 4126 */                                       out.write(" %'>\n\n");
/*      */                                       
/* 4128 */                                       DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4129 */                                       _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 4130 */                                       _jspx_th_awolf_005fdialchart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 4132 */                                       _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                                       
/* 4134 */                                       _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                                       
/* 4136 */                                       _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                                       
/* 4138 */                                       _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                                       
/* 4140 */                                       _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                                       
/* 4142 */                                       _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                                       
/* 4144 */                                       _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                                       
/* 4146 */                                       _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                                       
/* 4148 */                                       _jspx_th_awolf_005fdialchart_005f0.setResourceId(hostid);
/*      */                                       
/* 4150 */                                       _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 4151 */                                       int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 4152 */                                       if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 4153 */                                         if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 4154 */                                           out = _jspx_page_context.pushBody();
/* 4155 */                                           _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 4156 */                                           _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 4159 */                                           out.write(10);
/* 4160 */                                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 4161 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 4164 */                                         if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 4165 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 4168 */                                       if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 4169 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                                       }
/*      */                                       
/* 4172 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 4173 */                                       out.write("\n         </td>\n            ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 4177 */                                       out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 4178 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4179 */                                       out.write(32);
/* 4180 */                                       out.write(62);
/* 4181 */                                       out.write(10);
/* 4182 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4183 */                                       out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n\n  ");
/*      */                                     }
/* 4185 */                                     out.write("\n      </tr>\n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 4186 */                                     if (cpuvalue != null)
/*      */                                     {
/* 4188 */                                       out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4189 */                                       out.print(hostid);
/* 4190 */                                       out.write("&attributeid=");
/* 4191 */                                       out.print(cpuid);
/* 4192 */                                       out.write("&period=-7')\" class='bodytextbold'>");
/* 4193 */                                       out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4194 */                                       out.write(32);
/* 4195 */                                       out.write(45);
/* 4196 */                                       out.write(32);
/* 4197 */                                       out.print(cpuvalue);
/* 4198 */                                       out.write("</a> %\n");
/*      */                                     }
/* 4200 */                                     out.write("\n  </td>\n       </tr>\n       </table>");
/* 4201 */                                     out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 4202 */                                     out.write("</td>\n      <td width=\"30%\"> ");
/* 4203 */                                     out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 4204 */                                     out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/*      */                                     
/* 4206 */                                     if (memvalue != null)
/*      */                                     {
/*      */ 
/* 4209 */                                       dialGraph.setValue(Long.parseLong(memvalue));
/* 4210 */                                       out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4211 */                                       out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 4212 */                                       out.write(45);
/* 4213 */                                       out.print(memvalue);
/* 4214 */                                       out.write(" %' >\n\n");
/*      */                                       
/* 4216 */                                       DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4217 */                                       _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 4218 */                                       _jspx_th_awolf_005fdialchart_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 4220 */                                       _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                                       
/* 4222 */                                       _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                                       
/* 4224 */                                       _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                                       
/* 4226 */                                       _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                                       
/* 4228 */                                       _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                                       
/* 4230 */                                       _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                                       
/* 4232 */                                       _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                                       
/* 4234 */                                       _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                                       
/* 4236 */                                       _jspx_th_awolf_005fdialchart_005f1.setResourceId(hostid);
/*      */                                       
/* 4238 */                                       _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 4239 */                                       int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 4240 */                                       if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 4241 */                                         if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 4242 */                                           out = _jspx_page_context.pushBody();
/* 4243 */                                           _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 4244 */                                           _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 4247 */                                           out.write(32);
/* 4248 */                                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 4249 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 4252 */                                         if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 4253 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 4256 */                                       if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 4257 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                                       }
/*      */                                       
/* 4260 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 4261 */                                       out.write(32);
/* 4262 */                                       out.write("\n            </td>\n            ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 4266 */                                       out.write("\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 4267 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4268 */                                       out.write(" >\n\n");
/* 4269 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4270 */                                       out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                     }
/* 4272 */                                     out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 4273 */                                     if (memvalue != null)
/*      */                                     {
/* 4275 */                                       out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4276 */                                       out.print(hostid);
/* 4277 */                                       out.write("&attributeid=");
/* 4278 */                                       out.print(memid);
/* 4279 */                                       out.write("&period=-7')\" class='bodytextbold'>");
/* 4280 */                                       out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 4281 */                                       out.write(45);
/* 4282 */                                       out.print(memvalue);
/* 4283 */                                       out.write("</a> %\n  ");
/*      */                                     }
/* 4285 */                                     out.write("\n  </td>\n       </tr>\n    </table>");
/* 4286 */                                     out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 4287 */                                     out.write("</td>\n      <td width=\"30%\">");
/* 4288 */                                     out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 4289 */                                     out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                                     
/*      */ 
/* 4292 */                                     if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                     {
/*      */ 
/*      */ 
/* 4296 */                                       dialGraph.setValue(Long.parseLong(diskvalue));
/* 4297 */                                       out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4298 */                                       out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 4299 */                                       out.write(45);
/* 4300 */                                       out.print(diskvalue);
/* 4301 */                                       out.write("%' >\n");
/*      */                                       
/* 4303 */                                       DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4304 */                                       _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 4305 */                                       _jspx_th_awolf_005fdialchart_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 4307 */                                       _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                                       
/* 4309 */                                       _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                                       
/* 4311 */                                       _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                                       
/* 4313 */                                       _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                                       
/* 4315 */                                       _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                                       
/* 4317 */                                       _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                                       
/* 4319 */                                       _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                                       
/* 4321 */                                       _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                                       
/* 4323 */                                       _jspx_th_awolf_005fdialchart_005f2.setResourceId(hostid);
/*      */                                       
/* 4325 */                                       _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 4326 */                                       int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 4327 */                                       if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 4328 */                                         if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 4329 */                                           out = _jspx_page_context.pushBody();
/* 4330 */                                           _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 4331 */                                           _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 4334 */                                           out.write(32);
/* 4335 */                                           int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 4336 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 4339 */                                         if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 4340 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 4343 */                                       if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 4344 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                                       }
/*      */                                       
/* 4347 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 4348 */                                       out.write(32);
/* 4349 */                                       out.write(32);
/* 4350 */                                       out.write("\n    </td>\n            ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 4354 */                                       out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 4355 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4356 */                                       out.write(32);
/* 4357 */                                       out.write(62);
/* 4358 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4359 */                                       out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                     }
/* 4361 */                                     out.write("\n  </tr>\n  <tr>\n\n\n\n  <td align='center'  class='bodytextbold'>\n");
/* 4362 */                                     if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                     {
/* 4364 */                                       out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4365 */                                       out.print(hostid);
/* 4366 */                                       out.write("&attributeid=");
/* 4367 */                                       out.print(diskid);
/* 4368 */                                       out.write("&period=-7')\" class='bodytextbold'>");
/* 4369 */                                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 4370 */                                       out.write(45);
/* 4371 */                                       out.print(diskvalue);
/* 4372 */                                       out.write("</a> %\n     ");
/*      */                                     }
/* 4374 */                                     out.write("\n  </td>\n  </tr>\n</table>");
/* 4375 */                                     out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 4376 */                                     out.write("</td></tr></table>\n\n");
/*      */                                   } else {
/* 4378 */                                     out.write("\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\" width=\"100%\" onMouseOver=\"ShowPicture('configureIcons_ifany',1,'hostresource')\" onMouseOut=\"ShowPicture('configureIcons_ifany',0,'hostresource')\">\n\t<tr><td class=\"conf-mon-heading\" align=\"left\" colspan=\"3\">");
/* 4379 */                                     out.print(FormatUtil.getString("am.webclient.serversnapshot.allCaps.heading"));
/* 4380 */                                     out.write("&nbsp;-&nbsp;<a href=\"showresource.do?resourceid=");
/* 4381 */                                     out.print(systeminfo.get("host_resid"));
/* 4382 */                                     out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 4383 */                                     out.print(hostname);
/* 4384 */                                     out.write("</a></td></tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"><div id=\"configureIcons_ifany\"></div></td></tr>\n\t<tr>\n");
/* 4385 */                                     if (cpuvalue != null)
/*      */                                     {
/*      */ 
/* 4388 */                                       dialGraph.setValue(Long.parseLong(cpuvalue));
/* 4389 */                                       out.write("\n         <td align=\"center\" valign=\"center\">\n\t\t\t");
/*      */                                       
/* 4391 */                                       DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 4392 */                                       _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 4393 */                                       _jspx_th_awolf_005fdialchart_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 4395 */                                       _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                                       
/* 4397 */                                       _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                                       
/* 4399 */                                       _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                                       
/* 4401 */                                       _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                                       
/* 4403 */                                       _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                                       
/* 4405 */                                       _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                                       
/* 4407 */                                       _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                                       
/* 4409 */                                       _jspx_th_awolf_005fdialchart_005f3.setLink(cpuurl);
/*      */                                       
/* 4411 */                                       _jspx_th_awolf_005fdialchart_005f3.setResourceId(hostid);
/*      */                                       
/* 4413 */                                       _jspx_th_awolf_005fdialchart_005f3.setAttributeId(cpuid);
/* 4414 */                                       int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 4415 */                                       if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 4416 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                                       }
/*      */                                       
/* 4419 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 4420 */                                       out.write("\n         </td>\n     ");
/*      */                                     }
/*      */                                     else {
/* 4423 */                                       out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4424 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4425 */                                       out.write(39);
/* 4426 */                                       out.write(32);
/* 4427 */                                       out.write(62);
/* 4428 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4429 */                                       out.write("\n \t\t</td>\n\t\t");
/*      */                                     }
/* 4431 */                                     if (memvalue != null) {
/* 4432 */                                       dialGraph.setValue(Long.parseLong(memvalue));
/* 4433 */                                       out.write("\n            <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                       
/* 4435 */                                       DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 4436 */                                       _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 4437 */                                       _jspx_th_awolf_005fdialchart_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 4439 */                                       _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                                       
/* 4441 */                                       _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                                       
/* 4443 */                                       _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                                       
/* 4445 */                                       _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                                       
/* 4447 */                                       _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                                       
/* 4449 */                                       _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                                       
/* 4451 */                                       _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                                       
/* 4453 */                                       _jspx_th_awolf_005fdialchart_005f4.setLink(memurl);
/*      */                                       
/* 4455 */                                       _jspx_th_awolf_005fdialchart_005f4.setResourceId(hostid);
/*      */                                       
/* 4457 */                                       _jspx_th_awolf_005fdialchart_005f4.setAttributeId(memid);
/* 4458 */                                       int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 4459 */                                       if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 4460 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                                       }
/*      */                                       
/* 4463 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 4464 */                                       out.write("\n            </td>\n         ");
/*      */                                     }
/*      */                                     else {
/* 4467 */                                       out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4468 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4469 */                                       out.write(39);
/* 4470 */                                       out.write(32);
/* 4471 */                                       out.write(62);
/* 4472 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4473 */                                       out.write("\n \t\t</td>\n\t\t");
/*      */                                     }
/* 4475 */                                     if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 4476 */                                       dialGraph.setValue(Long.parseLong(diskvalue));
/* 4477 */                                       out.write("\n             <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                       
/* 4479 */                                       DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 4480 */                                       _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 4481 */                                       _jspx_th_awolf_005fdialchart_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                       
/* 4483 */                                       _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                                       
/* 4485 */                                       _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                                       
/* 4487 */                                       _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                                       
/* 4489 */                                       _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                                       
/* 4491 */                                       _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                                       
/* 4493 */                                       _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                                       
/* 4495 */                                       _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                                       
/* 4497 */                                       _jspx_th_awolf_005fdialchart_005f5.setLink(diskurl);
/*      */                                       
/* 4499 */                                       _jspx_th_awolf_005fdialchart_005f5.setResourceId(hostid);
/*      */                                       
/* 4501 */                                       _jspx_th_awolf_005fdialchart_005f5.setAttributeId(diskid);
/* 4502 */                                       int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 4503 */                                       if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 4504 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                                       }
/*      */                                       
/* 4507 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 4508 */                                       out.write(32);
/* 4509 */                                       out.write("\n\t          </td>\n\t  ");
/*      */                                     }
/*      */                                     else {
/* 4512 */                                       out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4513 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4514 */                                       out.write(39);
/* 4515 */                                       out.write(32);
/* 4516 */                                       out.write(62);
/* 4517 */                                       out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4518 */                                       out.write("\n \t\t</td>\n\t\t");
/*      */                                     }
/* 4520 */                                     out.write("\n         \t</tr>\n\t<tr id=\"showLinks_hostresource\">\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4521 */                                     out.print(hostid);
/* 4522 */                                     out.write("&attributeid=");
/* 4523 */                                     out.print(cpuid);
/* 4524 */                                     out.write("&period=-7')\" class='tooltip'>");
/* 4525 */                                     out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4526 */                                     out.write(32);
/* 4527 */                                     out.write(45);
/* 4528 */                                     out.write(32);
/* 4529 */                                     if (cpuvalue != null) {
/* 4530 */                                       out.print(cpuvalue);
/*      */                                     }
/* 4532 */                                     out.write(" %</a>\n\t\t</span>\n\t\t</td>\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4533 */                                     out.print(hostid);
/* 4534 */                                     out.write("&attributeid=");
/* 4535 */                                     out.print(memid);
/* 4536 */                                     out.write("&period=-7')\" class='tooltip'>");
/* 4537 */                                     out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 4538 */                                     out.write(45);
/* 4539 */                                     if (memvalue != null) {
/* 4540 */                                       out.print(memvalue);
/*      */                                     }
/* 4542 */                                     out.write(" %</a>\n  \t\t</span>\n\t\t</td>\n\t\t<td align=\"center\">\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4543 */                                     out.print(hostid);
/* 4544 */                                     out.write("&attributeid=");
/* 4545 */                                     out.print(diskid);
/* 4546 */                                     out.write("&period=-7')\" class='tooltip'>");
/* 4547 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 4548 */                                     out.write(45);
/* 4549 */                                     if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 4550 */                                       out.print(diskvalue);
/*      */                                     }
/* 4552 */                                     out.write(" %</a>\n     \t</span>\n\t\t</td>\n\t</tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n</table>\n         \t\n");
/*      */                                   }
/* 4554 */                                   out.write(10);
/* 4555 */                                   out.write(10);
/*      */                                 }
/*      */                                 
/*      */                               }
/*      */                             }
/*      */                             catch (Exception e)
/*      */                             {
/* 4562 */                               e.printStackTrace();
/*      */                             }
/* 4564 */                             out.write(10);
/* 4565 */                             out.write(10);
/* 4566 */                             response.setContentType("text/html;charset=UTF-8");
/* 4567 */                             out.write(10);
/* 4568 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 4569 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4572 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 4573 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4576 */                         if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4577 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                         }
/*      */                         
/* 4580 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 4581 */                         out.write(32);
/* 4582 */                         if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 4584 */                         out.write(32);
/* 4585 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4586 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 4590 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4591 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 4594 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4595 */                       out.write(10);
/* 4596 */                       out.write(10);
/* 4597 */                       out.write(10);
/*      */                     }
/* 4599 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4600 */         out = _jspx_out;
/* 4601 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4602 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4603 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4606 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4612 */     PageContext pageContext = _jspx_page_context;
/* 4613 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4615 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 4616 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 4617 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 4619 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 4620 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 4622 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 4623 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 4625 */           out.write(10);
/* 4626 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 4627 */             return true;
/* 4628 */           out.write(10);
/* 4629 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 4630 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4634 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 4635 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4638 */         int tmp177_176 = 0; int[] tmp177_174 = _jspx_push_body_count_c_005fcatch_005f0; int tmp179_178 = tmp177_174[tmp177_176];tmp177_174[tmp177_176] = (tmp179_178 - 1); if (tmp179_178 <= 0) break;
/* 4639 */         out = _jspx_page_context.popBody(); }
/* 4640 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 4642 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 4643 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 4645 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 4650 */     PageContext pageContext = _jspx_page_context;
/* 4651 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4653 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 4654 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 4655 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 4657 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 4659 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 4660 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 4661 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 4662 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4663 */       return true;
/*      */     }
/* 4665 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4666 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4671 */     PageContext pageContext = _jspx_page_context;
/* 4672 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4674 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4675 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 4676 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4678 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 4679 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 4680 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 4682 */         out.write(10);
/* 4683 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 4684 */           return true;
/* 4685 */         out.write(10);
/* 4686 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 4687 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4691 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4692 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4693 */       return true;
/*      */     }
/* 4695 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4696 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4701 */     PageContext pageContext = _jspx_page_context;
/* 4702 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4704 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4705 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4706 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4708 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4710 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 4711 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4712 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4713 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4714 */       return true;
/*      */     }
/* 4716 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4717 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4722 */     PageContext pageContext = _jspx_page_context;
/* 4723 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4725 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4726 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 4727 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4729 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 4730 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 4731 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 4733 */         out.write(10);
/* 4734 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 4735 */           return true;
/* 4736 */         out.write(10);
/* 4737 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4738 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4742 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4743 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4744 */       return true;
/*      */     }
/* 4746 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4747 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4752 */     PageContext pageContext = _jspx_page_context;
/* 4753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4755 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4756 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 4757 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4759 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 4761 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 4762 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 4763 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 4764 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4765 */       return true;
/*      */     }
/* 4767 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4768 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4773 */     PageContext pageContext = _jspx_page_context;
/* 4774 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4776 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4777 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4778 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4780 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 4781 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4782 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4783 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4784 */       return true;
/*      */     }
/* 4786 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4787 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4792 */     PageContext pageContext = _jspx_page_context;
/* 4793 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4795 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4796 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4797 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4799 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 4800 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4801 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4802 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4803 */       return true;
/*      */     }
/* 4805 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4806 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4811 */     PageContext pageContext = _jspx_page_context;
/* 4812 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4814 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4815 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4816 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4818 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 4819 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4820 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4821 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4822 */       return true;
/*      */     }
/* 4824 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4825 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4830 */     PageContext pageContext = _jspx_page_context;
/* 4831 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4833 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4834 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4835 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4837 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 4838 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4839 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4840 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4841 */       return true;
/*      */     }
/* 4843 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4849 */     PageContext pageContext = _jspx_page_context;
/* 4850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4852 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4853 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4854 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4856 */     _jspx_th_c_005fout_005f4.setValue("${ha.key}");
/* 4857 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4858 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4859 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4860 */       return true;
/*      */     }
/* 4862 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4863 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4868 */     PageContext pageContext = _jspx_page_context;
/* 4869 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4871 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4872 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4873 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4875 */     _jspx_th_c_005fout_005f5.setValue("${ha.value}");
/* 4876 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4877 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4878 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4879 */       return true;
/*      */     }
/* 4881 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4882 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4887 */     PageContext pageContext = _jspx_page_context;
/* 4888 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4890 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4891 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4892 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4894 */     _jspx_th_c_005fset_005f0.setVar("monitorName");
/* 4895 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4896 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 4897 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4898 */         out = _jspx_page_context.pushBody();
/* 4899 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 4900 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 4901 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4904 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4905 */           return true;
/* 4906 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 4907 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4910 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4911 */         out = _jspx_page_context.popBody();
/* 4912 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 4915 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4916 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4917 */       return true;
/*      */     }
/* 4919 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4920 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4925 */     PageContext pageContext = _jspx_page_context;
/* 4926 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4928 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4929 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4930 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 4932 */     _jspx_th_c_005fout_005f6.setValue("${ha.value}");
/* 4933 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4934 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4935 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4936 */       return true;
/*      */     }
/* 4938 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4939 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4944 */     PageContext pageContext = _jspx_page_context;
/* 4945 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4947 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4948 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4949 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 4951 */     _jspx_th_c_005fout_005f7.setValue("${ha.key}");
/* 4952 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4953 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4954 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4955 */       return true;
/*      */     }
/* 4957 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4958 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4963 */     PageContext pageContext = _jspx_page_context;
/* 4964 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4966 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4967 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4968 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4970 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4971 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4972 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4973 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4974 */       return true;
/*      */     }
/* 4976 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4977 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4982 */     PageContext pageContext = _jspx_page_context;
/* 4983 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4985 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4986 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4987 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4989 */     _jspx_th_c_005fout_005f8.setValue("${ha.key}");
/* 4990 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4991 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4992 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4993 */       return true;
/*      */     }
/* 4995 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4996 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5001 */     PageContext pageContext = _jspx_page_context;
/* 5002 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5004 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5005 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5006 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5008 */     _jspx_th_c_005fout_005f9.setValue("${ha.value}");
/* 5009 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5010 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5011 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5012 */       return true;
/*      */     }
/* 5014 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5015 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5020 */     PageContext pageContext = _jspx_page_context;
/* 5021 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5023 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5024 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 5025 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5027 */     _jspx_th_c_005fset_005f1.setVar("monitorName");
/* 5028 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 5029 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 5030 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5031 */         out = _jspx_page_context.pushBody();
/* 5032 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 5033 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 5034 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5037 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5038 */           return true;
/* 5039 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 5040 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5043 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5044 */         out = _jspx_page_context.popBody();
/* 5045 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 5048 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 5049 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 5050 */       return true;
/*      */     }
/* 5052 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 5053 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5058 */     PageContext pageContext = _jspx_page_context;
/* 5059 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5061 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5062 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 5063 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 5065 */     _jspx_th_c_005fout_005f10.setValue("${ha.value}");
/* 5066 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 5067 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 5068 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5069 */       return true;
/*      */     }
/* 5071 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5072 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5077 */     PageContext pageContext = _jspx_page_context;
/* 5078 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5080 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5081 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 5082 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 5084 */     _jspx_th_c_005fout_005f11.setValue("${ha.key}");
/* 5085 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 5086 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 5087 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5088 */       return true;
/*      */     }
/* 5090 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5091 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5096 */     PageContext pageContext = _jspx_page_context;
/* 5097 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5099 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5100 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 5101 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 5103 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 5104 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 5105 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 5106 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5107 */       return true;
/*      */     }
/* 5109 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5110 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5115 */     PageContext pageContext = _jspx_page_context;
/* 5116 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5118 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5119 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 5120 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5122 */     _jspx_th_c_005fif_005f10.setTest("${empty associatedmgs}");
/* 5123 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 5124 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 5126 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 5127 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 5128 */           return true;
/* 5129 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 5130 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 5131 */           return true;
/* 5132 */         out.write("</td>\n\t ");
/* 5133 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 5134 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5138 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 5139 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 5140 */       return true;
/*      */     }
/* 5142 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 5143 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5148 */     PageContext pageContext = _jspx_page_context;
/* 5149 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5151 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5152 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 5153 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 5155 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 5156 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 5157 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 5158 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5159 */       return true;
/*      */     }
/* 5161 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5167 */     PageContext pageContext = _jspx_page_context;
/* 5168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5170 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5171 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 5172 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 5174 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.none.text");
/* 5175 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 5176 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 5177 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5178 */       return true;
/*      */     }
/* 5180 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5181 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5186 */     PageContext pageContext = _jspx_page_context;
/* 5187 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5189 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5190 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 5191 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5193 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 5194 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 5195 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 5196 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5197 */       return true;
/*      */     }
/* 5199 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5200 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5205 */     PageContext pageContext = _jspx_page_context;
/* 5206 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5208 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5209 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 5210 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5212 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 5213 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 5214 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 5215 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5216 */       return true;
/*      */     }
/* 5218 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5219 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5224 */     PageContext pageContext = _jspx_page_context;
/* 5225 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5227 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5228 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 5229 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5231 */     _jspx_th_c_005fout_005f13.setValue("${param.resourcename}");
/* 5232 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 5233 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 5234 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5235 */       return true;
/*      */     }
/* 5237 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5238 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5243 */     PageContext pageContext = _jspx_page_context;
/* 5244 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5246 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5247 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 5248 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5250 */     _jspx_th_c_005fout_005f14.setValue("${param.resourceid}");
/* 5251 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 5252 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 5253 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5254 */       return true;
/*      */     }
/* 5256 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5257 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5262 */     PageContext pageContext = _jspx_page_context;
/* 5263 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5265 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5266 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 5267 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5269 */     _jspx_th_c_005fout_005f15.setValue("${param.resourcename}");
/* 5270 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 5271 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 5272 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5273 */       return true;
/*      */     }
/* 5275 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5276 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5281 */     PageContext pageContext = _jspx_page_context;
/* 5282 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5284 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5285 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 5286 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5288 */     _jspx_th_c_005fout_005f16.setValue("${param.resourceid}");
/* 5289 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 5290 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 5291 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5292 */       return true;
/*      */     }
/* 5294 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5295 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5300 */     PageContext pageContext = _jspx_page_context;
/* 5301 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5303 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5304 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 5305 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5307 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid}");
/* 5308 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 5309 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 5310 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5311 */       return true;
/*      */     }
/* 5313 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5314 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5319 */     PageContext pageContext = _jspx_page_context;
/* 5320 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5322 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5323 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 5324 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5325 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 5326 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 5327 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 5328 */         out = _jspx_page_context.pushBody();
/* 5329 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 5330 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5333 */         out.write("table.heading.attribute");
/* 5334 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 5335 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5338 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 5339 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5342 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 5343 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 5344 */       return true;
/*      */     }
/* 5346 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 5347 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5352 */     PageContext pageContext = _jspx_page_context;
/* 5353 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5355 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5356 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 5357 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5358 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 5359 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 5360 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 5361 */         out = _jspx_page_context.pushBody();
/* 5362 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 5363 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5366 */         out.write("table.heading.value");
/* 5367 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 5368 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5371 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 5372 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5375 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 5376 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 5377 */       return true;
/*      */     }
/* 5379 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 5380 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5385 */     PageContext pageContext = _jspx_page_context;
/* 5386 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5388 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5389 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 5390 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 5391 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 5392 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 5393 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 5394 */         out = _jspx_page_context.pushBody();
/* 5395 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 5396 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5399 */         out.write("table.heading.status");
/* 5400 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 5401 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5404 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 5405 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5408 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 5409 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 5410 */       return true;
/*      */     }
/* 5412 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 5413 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f14(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5418 */     PageContext pageContext = _jspx_page_context;
/* 5419 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5421 */     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5422 */     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 5423 */     _jspx_th_c_005fif_005f14.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5425 */     _jspx_th_c_005fif_005f14.setTest("${responsetime == '-1'}");
/* 5426 */     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 5427 */     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */       for (;;) {
/* 5429 */         out.write("\n                  - ");
/* 5430 */         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 5431 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5435 */     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 5436 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 5437 */       return true;
/*      */     }
/* 5439 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 5440 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f0(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5445 */     PageContext pageContext = _jspx_page_context;
/* 5446 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5448 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f0 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 5449 */     _jspx_th_fmt_005fformatNumber_005f0.setPageContext(_jspx_page_context);
/* 5450 */     _jspx_th_fmt_005fformatNumber_005f0.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 5452 */     _jspx_th_fmt_005fformatNumber_005f0.setValue("${responsetime}");
/* 5453 */     int _jspx_eval_fmt_005fformatNumber_005f0 = _jspx_th_fmt_005fformatNumber_005f0.doStartTag();
/* 5454 */     if (_jspx_th_fmt_005fformatNumber_005f0.doEndTag() == 5) {
/* 5455 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 5456 */       return true;
/*      */     }
/* 5458 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 5459 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5464 */     PageContext pageContext = _jspx_page_context;
/* 5465 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5467 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5468 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 5469 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5471 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 5473 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 5474 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 5475 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 5476 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 5477 */       return true;
/*      */     }
/* 5479 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 5480 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\PortDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */