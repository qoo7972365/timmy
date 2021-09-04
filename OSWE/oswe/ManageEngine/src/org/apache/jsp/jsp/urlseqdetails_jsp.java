/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.SummaryBean;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.StackedXYAreaChart;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import com.adventnet.awolf.tags.XYAreaChart;
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
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
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
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatNumberTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class urlseqdetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*   77 */     ManagedApplication mo = new ManagedApplication();
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
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
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
/*      */ 
/*      */ 
/*      */   private String getType(String resourcetype)
/*      */   {
/* 2190 */     return "";
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getPort(String resourcetype)
/*      */   {
/* 2230 */     if (resourcetype.equals("JBOSS-server"))
/*      */     {
/* 2232 */       return "8080";
/*      */     }
/* 2234 */     if (resourcetype.equals("WEBLOGIC-server"))
/*      */     {
/* 2236 */       return "7001";
/*      */     }
/* 2238 */     if (resourcetype.equals("Tomcat-server"))
/*      */     {
/* 2240 */       return "8080";
/*      */     }
/* 2242 */     if (resourcetype.equals("MYSQL-DB-server"))
/*      */     {
/* 2244 */       return "3306";
/*      */     }
/* 2246 */     if (resourcetype.equals("ORACLE-DB-server"))
/*      */     {
/* 2248 */       return "1521";
/*      */     }
/* 2250 */     if (resourcetype.equals("RMI"))
/*      */     {
/* 2252 */       return "1099";
/*      */     }
/* 2254 */     if (resourcetype.equals("MAIL-server"))
/*      */     {
/* 2256 */       return "110:25";
/*      */     }
/* 2258 */     if (resourcetype.equals("WEB-server"))
/*      */     {
/* 2260 */       return "80";
/*      */     }
/* 2262 */     return null;
/*      */   }
/*      */   
/*      */ 
/* 2266 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2272 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/* 2273 */   static { _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2274 */     _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2275 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2276 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fstackedxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2309 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2313 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2314 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2315 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2316 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2317 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2318 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2319 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2320 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2321 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2322 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2323 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2324 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2325 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2326 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2327 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2328 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2329 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2330 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2331 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2332 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2333 */     this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2334 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2335 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2336 */     this._005fjspx_005ftagPool_005fawolf_005fstackedxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2337 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2338 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2339 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2343 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2344 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2345 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2346 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2347 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2348 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2349 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2350 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2351 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2352 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2353 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2354 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2355 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2356 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2357 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2358 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2359 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2360 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2361 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2362 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/* 2363 */     this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer.release();
/* 2364 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2365 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.release();
/* 2366 */     this._005fjspx_005ftagPool_005fawolf_005fstackedxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer.release();
/* 2367 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2374 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2377 */     JspWriter out = null;
/* 2378 */     Object page = this;
/* 2379 */     JspWriter _jspx_out = null;
/* 2380 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2384 */       response.setContentType("text/html;charset=UTF-8");
/* 2385 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2387 */       _jspx_page_context = pageContext;
/* 2388 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2389 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2390 */       session = pageContext.getSession();
/* 2391 */       out = pageContext.getOut();
/* 2392 */       _jspx_out = out;
/*      */       
/* 2394 */       out.write("<!DOCTYPE html>\n");
/* 2395 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!-- $Id$ -->\n\n");
/*      */       
/* 2397 */       request.setAttribute("HelpKey", "Monitors URL Details");
/* 2398 */       if ((request.getParameter("type") != null) && ("RBM".equalsIgnoreCase(request.getParameter("type"))))
/*      */       {
/* 2400 */         request.setAttribute("HelpKey", "RBM Monitor Details");
/*      */       }
/*      */       
/* 2403 */       out.write("\n\n\n\n\n\n");
/* 2404 */       ManagedApplication mo = null;
/* 2405 */       synchronized (application) {
/* 2406 */         mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 4);
/* 2407 */         if (mo == null) {
/* 2408 */           mo = new ManagedApplication();
/* 2409 */           _jspx_page_context.setAttribute("mo", mo, 4);
/*      */         }
/*      */       }
/* 2412 */       out.write(10);
/* 2413 */       Hashtable availabilitykeys = null;
/* 2414 */       synchronized (application) {
/* 2415 */         availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2416 */         if (availabilitykeys == null) {
/* 2417 */           availabilitykeys = new Hashtable();
/* 2418 */           _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */         }
/*      */       }
/* 2421 */       out.write(10);
/* 2422 */       Hashtable healthkeys = null;
/* 2423 */       synchronized (application) {
/* 2424 */         healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2425 */         if (healthkeys == null) {
/* 2426 */           healthkeys = new Hashtable();
/* 2427 */           _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */         }
/*      */       }
/* 2430 */       out.write(10);
/* 2431 */       GetWLSGraph wlsGraph = null;
/* 2432 */       wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2433 */       if (wlsGraph == null) {
/* 2434 */         wlsGraph = new GetWLSGraph();
/* 2435 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2437 */       out.write("\n\n\n\n\n\n\n ");
/* 2438 */       SummaryBean sumgraph = null;
/* 2439 */       sumgraph = (SummaryBean)_jspx_page_context.getAttribute("sumgraph", 2);
/* 2440 */       if (sumgraph == null) {
/* 2441 */         sumgraph = new SummaryBean();
/* 2442 */         _jspx_page_context.setAttribute("sumgraph", sumgraph, 2);
/*      */       }
/* 2444 */       out.write(10);
/*      */       
/* 2446 */       Hashtable table = mo.getDistinctManagedObjects();
/* 2447 */       String resourceName = "UrlSeq";
/* 2448 */       String haid = request.getParameter("haid");
/* 2449 */       String seqid = request.getParameter("seqid");
/*      */       
/* 2451 */       Properties systeminfo = (Properties)request.getAttribute("props");
/* 2452 */       String seqname = systeminfo.getProperty("resourcename");
/*      */       
/* 2454 */       String resourcetype = request.getParameter("type");
/* 2455 */       String resType = "UrlSeq";
/* 2456 */       if (resourcetype.startsWith("RBM"))
/*      */       {
/* 2458 */         resourceName = FormatUtil.getString("am.webclient.rbmurlmonitor.type.text");
/* 2459 */         resType = "RBM";
/*      */       }
/*      */       
/* 2462 */       String resourceid = request.getParameter("resourceid");
/*      */       
/* 2464 */       String encodeurl = java.net.URLEncoder.encode("/showresource.do?resourceid=" + request.getParameter("resourceid") + "&method=showResourceForResourceID");
/* 2465 */       ArrayList resIDs = new ArrayList();
/* 2466 */       resIDs.add(resourceid);
/* 2467 */       String healthAttID = "405";
/* 2468 */       String availAttID = "404";
/* 2469 */       String responseTimeAttID = "417";
/* 2470 */       String avgresponseTimeAttID = "410";
/* 2471 */       String dnsid = "53009";
/* 2472 */       String connid = "53010";
/* 2473 */       String fbtid = "53011";
/* 2474 */       String lbtid = "53012";
/* 2475 */       if (resType.equals("RBM"))
/*      */       {
/* 2477 */         healthAttID = "8111";
/* 2478 */         availAttID = "8110";
/* 2479 */         responseTimeAttID = "8112";
/* 2480 */         avgresponseTimeAttID = "8122";
/*      */       }
/*      */       
/* 2483 */       ArrayList attribIDs = new ArrayList();
/* 2484 */       if (resType.equals("UrlSeq"))
/*      */       {
/* 2486 */         attribIDs.add("404");
/* 2487 */         attribIDs.add("405");
/* 2488 */         attribIDs.add("417");
/* 2489 */         attribIDs.add("53009");
/* 2490 */         attribIDs.add("53010");
/* 2491 */         attribIDs.add("53011");
/* 2492 */         attribIDs.add("53012");
/*      */       }
/*      */       else
/*      */       {
/* 2496 */         attribIDs.add("8111");
/* 2497 */         attribIDs.add("8110");
/* 2498 */         attribIDs.add("8112");
/* 2499 */         attribIDs.add("8120");
/* 2500 */         attribIDs.add("8121");
/* 2501 */         attribIDs.add("8122");
/* 2502 */         attribIDs.add("8123");
/* 2503 */         attribIDs.add("8124");
/*      */       }
/*      */       
/* 2506 */       Properties alert = getStatus(resIDs, attribIDs);
/* 2507 */       System.out.println("dddddddd: " + request.getAttribute("reloadperiod"));
/*      */       
/* 2509 */       out.write("\n\n\n\n\n");
/*      */       
/* 2511 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2512 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2513 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2515 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2516 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2517 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2519 */           out.write(10);
/*      */           
/* 2521 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2522 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2523 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2525 */           _jspx_th_tiles_005fput_005f0.setName("title");
/*      */           
/* 2527 */           _jspx_th_tiles_005fput_005f0.setValue(resourceName);
/* 2528 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2529 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2530 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */           }
/*      */           
/* 2533 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2534 */           out.write(10);
/* 2535 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2537 */           out.write(10);
/*      */           
/* 2539 */           PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2540 */           _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2541 */           _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2543 */           _jspx_th_tiles_005fput_005f2.setName("ServerLeftArea");
/*      */           
/* 2545 */           _jspx_th_tiles_005fput_005f2.setType("string");
/* 2546 */           int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2547 */           if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2548 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2549 */               out = _jspx_page_context.pushBody();
/* 2550 */               _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2551 */               _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2554 */               out.write("\n<SCRIPT language=JavaScript1.2 src=\"/template/mm_menu.js\"></SCRIPT>\n<script>\nvar scriptmanager;\nfunction openScriptManager(tab)\n{\n\tscriptmanager = window.open('/jsp/RBMScriptManager.jsp?from=urlseqdetails&tab='+tab+'&bcname=");
/* 2555 */               if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2557 */               out.write("','Application_ManagerRBM_Script_Manager','toolbar=no,status=no,menubar=no,width=1000,height=650,scrollbars=yes,location=no');//No I18N\n\n}\nfunction change(val,ind,pxy,usr)\n{\n\tscriptmanager.change(val,ind,pxy,usr);\n}\n\nfunction changeState(val)\n{\n\tif(val==\"recordOff\")\n\t{\n\t\tsetTimeout(\"test()\",100)\t;//No I18N\n\t}\n\telse if(val==\"recordOn\")\n\t{\n\t\t//recordOn = true;\n\t}\n\n}\n\n");
/*      */               
/* 2559 */               String editPage = request.getParameter("editPage");
/* 2560 */               if ((editPage != null) && (editPage.equals("true")) && ((resType.equals("RBM")) || (resType.equals("UrlSeq"))))
/*      */               {
/*      */ 
/* 2563 */                 out.write("\n\t\ttoggleDiv(\"edit\");\n\t");
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 2568 */               out.write("\nfunction test()\n{\n\tscriptmanager.stopActions();\n//\tdocument.RbmForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=RBM\";//No I18N\n//\tdocument.RbmForm.submit();\n}\n</script>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td width=\"90%\" height=\"21\" class=\"leftlinksheading\">\n\t");
/*      */               
/* 2570 */               if (resType.equals("RBM"))
/*      */               {
/*      */ 
/* 2573 */                 out.write("\n\t\t ");
/* 2574 */                 out.print(FormatUtil.getString("am.reporttab.shortname.rbmmonitoring.text"));
/* 2575 */                 out.write(10);
/* 2576 */                 out.write(9);
/* 2577 */                 out.write(9);
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/* 2583 */                 out.write("\n\t\t  ");
/* 2584 */                 out.print(FormatUtil.getString("am.webclient.urlseq.type.text"));
/* 2585 */                 out.write("\n\t\t  ");
/*      */               }
/*      */               
/*      */ 
/* 2589 */               out.write("\n      </td>\n  </tr>\n\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n\n   ");
/* 2590 */               out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2591 */               out.write("\n\n     </td>\n  </tr>\n  ");
/*      */               
/* 2593 */               IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2594 */               _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2595 */               _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2597 */               _jspx_th_c_005fif_005f0.setTest("${!empty ADMIN || !empty DEMO}");
/* 2598 */               int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2599 */               if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                 for (;;) {
/* 2601 */                   out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n    <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 2602 */                   if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                     return;
/* 2604 */                   out.write("\" class=\"new-left-links\">\n    ");
/* 2605 */                   out.print(ALERTCONFIG_TEXT);
/* 2606 */                   out.write("\n    </a>\n\n     </td>\n  </tr>\n  ");
/* 2607 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2608 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2612 */               if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2613 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */               }
/*      */               
/* 2616 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2617 */               out.write(10);
/* 2618 */               out.write(32);
/* 2619 */               out.write(32);
/* 2620 */               if (com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled()) {
/* 2621 */                 out.write(" \n  ");
/*      */                 
/* 2623 */                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2624 */                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2625 */                 _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                 
/* 2627 */                 _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2628 */                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2629 */                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                   for (;;) {
/* 2631 */                     out.write("\n\t<tr>\n\t\t<td height=\"21\" class=\"leftlinkstd\"><a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 2632 */                     out.print(request.getParameter("resourceid"));
/* 2633 */                     out.write("&type=");
/* 2634 */                     out.print(request.getParameter("type"));
/* 2635 */                     out.write("&moname=");
/* 2636 */                     out.print(request.getParameter("moname"));
/* 2637 */                     out.write("&method=showdetails&resourcename=");
/* 2638 */                     out.print(request.getParameter("resourcename"));
/* 2639 */                     out.write("&viewType=showResourceTypes&aam_jump=true&editPage=true\" class=\"new-left-links\">");
/* 2640 */                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2641 */                     out.write("</a>\n\t\t</td>\n\t</tr>\n  ");
/* 2642 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2643 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2647 */                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2648 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                 }
/*      */                 
/* 2651 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2652 */                 out.write(10);
/* 2653 */                 out.write(32);
/* 2654 */                 out.write(32);
/*      */               }
/* 2656 */               out.write(10);
/* 2657 */               out.write(32);
/* 2658 */               out.write(32);
/*      */               
/* 2660 */               IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2661 */               _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2662 */               _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2664 */               _jspx_th_c_005fif_005f1.setTest("${!empty ADMIN || !empty DEMO}");
/* 2665 */               int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2666 */               if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                 for (;;) {
/* 2668 */                   out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n\n");
/*      */                   
/* 2670 */                   IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2671 */                   _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2672 */                   _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fif_005f1);
/*      */                   
/* 2674 */                   _jspx_th_c_005fif_005f2.setTest("${!empty ADMIN || !empty DEMO}");
/* 2675 */                   int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2676 */                   if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                     for (;;) {
/* 2678 */                       out.write("\n      <a href=\"javascript:toggleDiv('edit')\"  class=\"new-left-links\">");
/* 2679 */                       out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2680 */                       out.write("</a>\n      ");
/* 2681 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2682 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2686 */                   if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2687 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                   }
/*      */                   
/* 2690 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2691 */                   out.write("\n\n  </td>\n  </tr>\n  ");
/* 2692 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2693 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2697 */               if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2698 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */               }
/*      */               
/* 2701 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2702 */               out.write("\n\n  <script language=\"JavaScript\">\nvar resType= \"");
/* 2703 */               out.print(resType);
/* 2704 */               out.write("\";\n\tfunction confirmDelete()\n \t {\n          var s = confirm(\"");
/* 2705 */               out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 2706 */               out.write("\")\n           if (s)\n            document.location.href=\"/deleteMO.do?method=deleteMO&type=\"+resType+\"&select=");
/* 2707 */               if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2709 */               out.write("\";\n\t }\n\t        function confirmManage()\n \t {\n  var s = confirm(\"");
/* 2710 */               out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2711 */               out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2712 */               if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2714 */               out.write("\";\n\t }\n\n         function confirmUnManage()\n \t {\n        \t  var show_msg=\"false\";\n              var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2715 */               out.print(request.getParameter("resourceid"));
/* 2716 */               out.write("; //No i18n\n              $.ajax({\n                type:'POST', //No i18n\n                url:url,\n                async:false,\n                success: function(data)\n                {\n                  show_msg=data\n                }\n              });\n              if(show_msg.indexOf(\"true\")>-1)\n              {\n                  alert(\"");
/* 2717 */               out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2718 */               out.write("\");\n\t\t      \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2719 */               if (_jspx_meth_c_005fout_005f4(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2721 */               out.write("\";\n             }\n           else { \n        \t   var s = confirm(\"");
/* 2722 */               out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2723 */               out.write("\");\n        \t\tif (s){ \n   \t\t\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2724 */               if (_jspx_meth_c_005fout_005f5(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2726 */               out.write("\"; //No I18N\n\t\t\t      }\n\t       } \n\t }\n  </script>\n  ");
/*      */               
/* 2728 */               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2729 */               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2730 */               _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2732 */               _jspx_th_c_005fif_005f3.setTest("${!empty ADMIN || !empty DEMO}");
/* 2733 */               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2734 */               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                 for (;;) {
/* 2736 */                   out.write(10);
/*      */                   
/* 2738 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2739 */                   _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2740 */                   _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f3);
/*      */                   
/* 2742 */                   _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2743 */                   int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2744 */                   if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                     for (;;) {
/* 2746 */                       out.write("\n  <tr>\n    <td class=\"leftlinkstd\" >\n  <A href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 2747 */                       out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 2748 */                       out.write("</A></td>\n\n  </tr>\n");
/* 2749 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2750 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2754 */                   if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2755 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                   }
/*      */                   
/* 2758 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2759 */                   out.write(10);
/* 2760 */                   out.write(10);
/*      */                   
/* 2762 */                   PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2763 */                   _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2764 */                   _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f3);
/*      */                   
/* 2766 */                   _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 2767 */                   int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2768 */                   if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                     for (;;) {
/* 2770 */                       out.write("\n\n<td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 2771 */                       out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 2772 */                       out.write("</a></td>\n\n");
/* 2773 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2774 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2778 */                   if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2779 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                   }
/*      */                   
/* 2782 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2783 */                   out.write("\n\n\n\n  ");
/* 2784 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2785 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2789 */               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2790 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */               }
/*      */               
/* 2793 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2794 */               out.write(10);
/* 2795 */               out.write(32);
/* 2796 */               out.write(32);
/*      */               
/* 2798 */               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2799 */               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2800 */               _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2802 */               _jspx_th_c_005fif_005f4.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 2803 */               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2804 */               if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                 for (;;) {
/* 2806 */                   out.write("\n  <tr>\n  ");
/*      */                   
/* 2808 */                   if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                   {
/*      */ 
/* 2811 */                     out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 2812 */                     out.print(FormatUtil.getString("Manage"));
/* 2813 */                     out.write("</A></td>\n    ");
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/*      */ 
/* 2819 */                     out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 2820 */                     out.print(FormatUtil.getString("UnManage"));
/* 2821 */                     out.write("</A></td>\n    ");
/*      */                   }
/*      */                   
/*      */ 
/* 2825 */                   out.write("\n  </tr>\n  ");
/* 2826 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2827 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2831 */               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2832 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */               }
/*      */               
/* 2835 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2836 */               out.write("\n\n\n  ");
/*      */               
/* 2838 */               IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2839 */               _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2840 */               _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2842 */               _jspx_th_c_005fif_005f5.setTest("${!empty ADMIN || !empty DEMO }");
/* 2843 */               int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2844 */               if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                 for (;;) {
/* 2846 */                   out.write("\n      \t<tr>\n          \t <td colspan=\"2\" class=\"leftlinkstd\">\n          \t ");
/* 2847 */                   out.print(com.adventnet.appmanager.fault.FaultUtil.getAlertTemplateURL(seqid, seqname, "UrlSeq", "HTTP URL Sequence"));
/* 2848 */                   out.write("\n          \t </td>\n         \t</tr>\n  ");
/* 2849 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2850 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2854 */               if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2855 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */               }
/*      */               
/* 2858 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2859 */               out.write(10);
/* 2860 */               out.write(32);
/* 2861 */               out.write(32);
/*      */               
/* 2863 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2864 */               _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2865 */               _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2867 */               _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 2868 */               int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2869 */               if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                 for (;;) {
/* 2871 */                   out.write("\n      ");
/*      */                   
/* 2873 */                   String resourceid_poll = request.getParameter("resourceid");
/*      */                   
/*      */ 
/* 2876 */                   out.write("\n        <tr>\n        <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n        <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 2877 */                   out.print(resourceid_poll);
/* 2878 */                   out.write("&resourcetype=");
/* 2879 */                   out.print(resourcetype);
/* 2880 */                   out.write("\" class=\"new-left-links\"> ");
/* 2881 */                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 2882 */                   out.write("</a></td>\n      </tr>\n    ");
/* 2883 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2884 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2888 */               if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2889 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */               }
/*      */               
/* 2892 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2893 */               out.write("\n    ");
/*      */               
/* 2895 */               PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2896 */               _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2897 */               _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2899 */               _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 2900 */               int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2901 */               if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                 for (;;) {
/* 2903 */                   out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 2904 */                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 2905 */                   out.write("</a></td>\n          </td>\n    ");
/* 2906 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2907 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2911 */               if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2912 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */               }
/*      */               
/* 2915 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2916 */               out.write("\n    ");
/* 2917 */               out.write("<!-- $Id$-->\n\n\n  \n");
/*      */               
/* 2919 */               if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */               {
/* 2921 */                 Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 2922 */                 String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                 
/* 2924 */                 String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 2925 */                 String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 2926 */                 if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                 {
/* 2928 */                   if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                   {
/*      */ 
/* 2931 */                     out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2932 */                     out.print(ciInfoUrl);
/* 2933 */                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 2934 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 2935 */                     out.write("</a></td>");
/* 2936 */                     out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2937 */                     out.print(ciRLUrl);
/* 2938 */                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 2939 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 2940 */                     out.write("</a></td>");
/* 2941 */                     out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                   }
/* 2945 */                   else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                   {
/*      */ 
/* 2948 */                     out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 2949 */                     out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 2950 */                     out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2951 */                     out.print(ciInfoUrl);
/* 2952 */                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 2953 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 2954 */                     out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2955 */                     out.print(ciRLUrl);
/* 2956 */                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 2957 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 2958 */                     out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/* 2963 */               out.write("\n \n \n\n");
/* 2964 */               out.write("\n</table>\n <br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 2965 */               out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 2966 */               out.write("</td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2967 */               out.print(resourceid);
/* 2968 */               out.write("&attributeid=");
/* 2969 */               out.print(healthAttID);
/* 2970 */               out.write("')\"   class=\"new-left-links\">");
/* 2971 */               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2972 */               out.write("</a> </td>\n    <td ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2973 */               out.print(resourceid);
/* 2974 */               out.write("&attributeid=");
/* 2975 */               out.print(healthAttID);
/* 2976 */               out.write("')\">");
/* 2977 */               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + healthAttID)));
/* 2978 */               out.write("</a></td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2979 */               out.print(resourceid);
/* 2980 */               out.write("&attributeid=");
/* 2981 */               out.print(availAttID);
/* 2982 */               out.write("')\" class=\"new-left-links\">");
/* 2983 */               out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 2984 */               out.write("</a> </td>\n    <td><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2985 */               out.print(resourceid);
/* 2986 */               out.write("&attributeid=");
/* 2987 */               out.print(availAttID);
/* 2988 */               out.write("')\">");
/* 2989 */               out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + availAttID)));
/* 2990 */               out.write("</a></td>\n  </tr>\n</table>\n<br>\n");
/* 2991 */               out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */               
/*      */ 
/*      */ 
/* 2995 */               boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 2996 */               if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition)
/*      */               {
/* 2998 */                 showAssociatedBSG = false;
/*      */                 
/*      */ 
/*      */ 
/* 3002 */                 CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3003 */                 CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3004 */                 String loginName = request.getUserPrincipal().getName();
/* 3005 */                 CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                 
/* 3007 */                 if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                 {
/* 3009 */                   showAssociatedBSG = true;
/*      */                 }
/*      */               }
/* 3012 */               String monitorType = request.getParameter("type");
/* 3013 */               ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3014 */               boolean mon = conf1.isConfMonitor(monitorType);
/* 3015 */               if (showAssociatedBSG)
/*      */               {
/* 3017 */                 Hashtable associatedmgs = new Hashtable();
/* 3018 */                 String resId = request.getParameter("resourceid");
/* 3019 */                 request.setAttribute("associatedmgs", com.adventnet.appmanager.fault.FaultUtil.getAdminAssociatedMG(resId, request));
/* 3020 */                 if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                 {
/* 3022 */                   mon = false;
/*      */                 }
/*      */                 
/* 3025 */                 if (!mon)
/*      */                 {
/* 3027 */                   out.write(10);
/* 3028 */                   out.write(10);
/*      */                   
/* 3030 */                   IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3031 */                   _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3032 */                   _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                   
/* 3034 */                   _jspx_th_c_005fif_005f6.setTest("${!empty associatedmgs}");
/* 3035 */                   int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3036 */                   if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                     for (;;) {
/* 3038 */                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3039 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3040 */                       out.write("</td>\n        </tr>\n        ");
/*      */                       
/* 3042 */                       ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3043 */                       _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3044 */                       _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                       
/* 3046 */                       _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                       
/* 3048 */                       _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                       
/* 3050 */                       _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3051 */                       int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                       try {
/* 3053 */                         int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3054 */                         if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                           for (;;) {
/* 3056 */                             out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3057 */                             if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3115 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3116 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3059 */                             out.write("&method=showApplication\" title=\"");
/* 3060 */                             if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3115 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3116 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3062 */                             out.write("\"  class=\"new-left-links\">\n         ");
/* 3063 */                             if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3115 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3116 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3065 */                             out.write("\n    \t");
/* 3066 */                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3067 */                             out.write("\n         </a></td>\n        <td>");
/*      */                             
/* 3069 */                             PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3070 */                             _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3071 */                             _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                             
/* 3073 */                             _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 3074 */                             int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3075 */                             if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                               for (;;) {
/* 3077 */                                 out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3078 */                                 if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fpresent_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3115 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 3116 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 3080 */                                 out.write(39);
/* 3081 */                                 out.write(44);
/* 3082 */                                 out.write(39);
/* 3083 */                                 out.print(resId);
/* 3084 */                                 out.write(39);
/* 3085 */                                 out.write(44);
/* 3086 */                                 out.write(39);
/* 3087 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3088 */                                 out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3089 */                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3090 */                                 out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3091 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3092 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3096 */                             if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3097 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3115 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3116 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3100 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3101 */                             out.write("</td>\n        </tr>\n\t");
/* 3102 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3103 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3107 */                         if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3115 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 3116 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 3111 */                           int tmp4901_4900 = 0; int[] tmp4901_4898 = _jspx_push_body_count_c_005fforEach_005f0; int tmp4903_4902 = tmp4901_4898[tmp4901_4900];tmp4901_4898[tmp4901_4900] = (tmp4903_4902 - 1); if (tmp4903_4902 <= 0) break;
/* 3112 */                           out = _jspx_page_context.popBody(); }
/* 3113 */                         _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                       } finally {
/* 3115 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3116 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                       }
/* 3118 */                       out.write("\n      </table>\n ");
/* 3119 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3120 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3124 */                   if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3125 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                   }
/*      */                   
/* 3128 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3129 */                   out.write(10);
/* 3130 */                   out.write(32);
/*      */                   
/* 3132 */                   IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3133 */                   _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3134 */                   _jspx_th_c_005fif_005f7.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                   
/* 3136 */                   _jspx_th_c_005fif_005f7.setTest("${empty associatedmgs}");
/* 3137 */                   int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3138 */                   if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                     for (;;) {
/* 3140 */                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 3141 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3142 */                       out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 3143 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3144 */                       out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 3145 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3146 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3150 */                   if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3151 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                   }
/*      */                   
/* 3154 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3155 */                   out.write(10);
/* 3156 */                   out.write(32);
/* 3157 */                   out.write(10);
/*      */ 
/*      */                 }
/* 3160 */                 else if (mon)
/*      */                 {
/*      */ 
/*      */ 
/* 3164 */                   out.write(10);
/*      */                   
/* 3166 */                   IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3167 */                   _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3168 */                   _jspx_th_c_005fif_005f8.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                   
/* 3170 */                   _jspx_th_c_005fif_005f8.setTest("${!empty associatedmgs}");
/* 3171 */                   int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3172 */                   if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                     for (;;) {
/* 3174 */                       out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 3175 */                       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                         return;
/* 3177 */                       out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                       
/* 3179 */                       ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3180 */                       _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3181 */                       _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f8);
/*      */                       
/* 3183 */                       _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                       
/* 3185 */                       _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                       
/* 3187 */                       _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 3188 */                       int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                       try {
/* 3190 */                         int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3191 */                         if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                           for (;;) {
/* 3193 */                             out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 3194 */                             if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3255 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3256 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3196 */                             out.write("&method=showApplication\" title=\"");
/* 3197 */                             if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3255 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3256 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3199 */                             out.write("\"  class=\"staticlinks\">\n         ");
/* 3200 */                             if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3255 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3256 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3202 */                             out.write("\n    \t");
/* 3203 */                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3204 */                             out.write("</a></span>\t\n\t\t ");
/*      */                             
/* 3206 */                             PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3207 */                             _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3208 */                             _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                             
/* 3210 */                             _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 3211 */                             int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3212 */                             if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                               for (;;) {
/* 3214 */                                 out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 3215 */                                 if (_jspx_meth_c_005fout_005f13(_jspx_th_logic_005fpresent_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3255 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 3256 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 3217 */                                 out.write(39);
/* 3218 */                                 out.write(44);
/* 3219 */                                 out.write(39);
/* 3220 */                                 out.print(resId);
/* 3221 */                                 out.write(39);
/* 3222 */                                 out.write(44);
/* 3223 */                                 out.write(39);
/* 3224 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3225 */                                 out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 3226 */                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3227 */                                 out.write("\"  title=\"");
/* 3228 */                                 if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3255 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 3256 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 3230 */                                 out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 3231 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3232 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3236 */                             if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3237 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3255 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3256 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3240 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3241 */                             out.write("\n\n\t\t \t");
/* 3242 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3243 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3247 */                         if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3255 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 3256 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 3251 */                           int tmp5927_5926 = 0; int[] tmp5927_5924 = _jspx_push_body_count_c_005fforEach_005f1; int tmp5929_5928 = tmp5927_5924[tmp5927_5926];tmp5927_5924[tmp5927_5926] = (tmp5929_5928 - 1); if (tmp5929_5928 <= 0) break;
/* 3252 */                           out = _jspx_page_context.popBody(); }
/* 3253 */                         _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                       } finally {
/* 3255 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 3256 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                       }
/* 3258 */                       out.write("\n\t\n\t\t\t</td>\n\t ");
/* 3259 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3260 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3264 */                   if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3265 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                   }
/*      */                   
/* 3268 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3269 */                   out.write(10);
/* 3270 */                   out.write(32);
/* 3271 */                   if (_jspx_meth_c_005fif_005f9(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                     return;
/* 3273 */                   out.write(32);
/* 3274 */                   out.write(10);
/*      */                 }
/*      */                 
/*      */               }
/* 3278 */               else if (mon)
/*      */               {
/*      */ 
/* 3281 */                 out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 3282 */                 if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                   return;
/* 3284 */                 out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */               }
/*      */               
/*      */ 
/* 3288 */               out.write(9);
/* 3289 */               out.write(9);
/* 3290 */               out.write(10);
/* 3291 */               if (_jspx_meth_c_005fset_005f2(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 3293 */               out.write("\n\n<script language=JavaScript1.2>\n");
/*      */               
/* 3295 */               String requestpath = "/images/mm_menu2.jsp";
/*      */               
/*      */ 
/* 3298 */               out.write(10);
/* 3299 */               JspRuntimeLibrary.include(request, response, requestpath, out, false);
/* 3300 */               out.write("\n</script>\n<SCRIPT language=JavaScript1.2>mmLoadMenus()</SCRIPT>\n");
/* 3301 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3302 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3305 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3306 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3309 */           if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3310 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */           }
/*      */           
/* 3313 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3314 */           out.write(10);
/*      */           
/* 3316 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3317 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3318 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 3320 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */           
/* 3322 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 3323 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3324 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 3325 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3326 */               out = _jspx_page_context.pushBody();
/* 3327 */               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 3328 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 3331 */               out.write(10);
/* 3332 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 3334 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3335 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 3336 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3338 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 3340 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 3342 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 3344 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 3345 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 3346 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 3347 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 3350 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 3351 */               String available = null;
/* 3352 */               available = (String)_jspx_page_context.findAttribute("available");
/* 3353 */               out.write(10);
/*      */               
/* 3355 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3356 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 3357 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3359 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 3361 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 3363 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 3365 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 3366 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 3367 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 3368 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 3371 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 3372 */               String unavailable = null;
/* 3373 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 3374 */               out.write(10);
/*      */               
/* 3376 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3377 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 3378 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3380 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 3382 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 3384 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 3386 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 3387 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 3388 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 3389 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 3392 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 3393 */               String unmanaged = null;
/* 3394 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 3395 */               out.write(10);
/*      */               
/* 3397 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3398 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 3399 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3401 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 3403 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 3405 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 3407 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 3408 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 3409 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 3410 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 3413 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 3414 */               String scheduled = null;
/* 3415 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 3416 */               out.write(10);
/*      */               
/* 3418 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3419 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 3420 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3422 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 3424 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 3426 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 3428 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 3429 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 3430 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 3431 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 3434 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 3435 */               String critical = null;
/* 3436 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 3437 */               out.write(10);
/*      */               
/* 3439 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3440 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 3441 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3443 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 3445 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 3447 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 3449 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 3450 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 3451 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 3452 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 3455 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 3456 */               String clear = null;
/* 3457 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 3458 */               out.write(10);
/*      */               
/* 3460 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3461 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 3462 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3464 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 3466 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 3468 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 3470 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 3471 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 3472 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 3473 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 3476 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 3477 */               String warning = null;
/* 3478 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 3479 */               out.write(10);
/* 3480 */               out.write(10);
/*      */               
/* 3482 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 3483 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 3485 */               out.write(10);
/* 3486 */               out.write(10);
/* 3487 */               out.write(10);
/* 3488 */               out.write("\n<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t");
/*      */               
/* 3490 */               Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 3491 */               String aid = request.getParameter("haid");
/* 3492 */               String haName = null;
/* 3493 */               if (aid != null)
/*      */               {
/* 3495 */                 haName = (String)ht.get(aid);
/*      */               }
/*      */               
/* 3498 */               out.write(10);
/* 3499 */               out.write(9);
/* 3500 */               if (_jspx_meth_c_005fcatch_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3502 */               out.write(10);
/* 3503 */               out.write(9);
/* 3504 */               if (_jspx_meth_c_005fset_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3506 */               out.write(10);
/* 3507 */               out.write(9);
/* 3508 */               String nameofurlseq = (String)pageContext.getAttribute("breadcrumbname");
/* 3509 */               out.write(10);
/* 3510 */               out.write(9);
/*      */               
/* 3512 */               IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3513 */               _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3514 */               _jspx_th_c_005fif_005f10.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3516 */               _jspx_th_c_005fif_005f10.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 3517 */               int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3518 */               if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                 for (;;) {
/* 3520 */                   out.write("\n\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 3521 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 3522 */                   out.write(" &gt; ");
/* 3523 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 3524 */                   out.write(" &gt; <span class=\"bcactive\">");
/* 3525 */                   out.print(nameofurlseq);
/* 3526 */                   out.write(" </span></td>\n\t");
/* 3527 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3528 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3532 */               if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3533 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */               }
/*      */               
/* 3536 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3537 */               out.write(10);
/* 3538 */               out.write(9);
/*      */               
/* 3540 */               IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3541 */               _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3542 */               _jspx_th_c_005fif_005f11.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3544 */               _jspx_th_c_005fif_005f11.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 3545 */               int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3546 */               if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                 for (;;) {
/* 3548 */                   out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 3549 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 3550 */                   out.write(" &gt; ");
/* 3551 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes(resType));
/* 3552 */                   out.write(" &gt; <span class=\"bcactive\">");
/* 3553 */                   out.print(nameofurlseq);
/* 3554 */                   out.write("  </span></td>\n\t");
/* 3555 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3556 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3560 */               if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3561 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */               }
/*      */               
/* 3564 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3565 */               out.write("\n    </tr>\n</table>\n\n\n<script>\nfunction fnFormSubmit()\n{\n   ");
/* 3566 */               if (_jspx_meth_logic_005fpresent_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3568 */               out.write(10);
/* 3569 */               out.write(9);
/*      */               
/* 3571 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3572 */               _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3573 */               _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3575 */               _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 3576 */               int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3577 */               if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                 for (;;) {
/* 3579 */                   out.write("\n\tif(document.AMActionForm.displayname.value=='')\n\t{\n             alert(\"URL Sequence name cannot be empty\");\n\t\treturn;\n\t}\n\tvar poll=trimAll(document.AMActionForm.pollInterval.value);\n\tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0'  )\n\t{\n       \talert(\"");
/* 3580 */                   out.print(FormatUtil.getString("am.webclient.common.validpollinginterval.text"));
/* 3581 */                   out.write("\");\n\t\treturn;\n\t}\n\n\n\n\tif(resType==\"RBM\")\n\t{\n\t\tif(poll<5)\n\t\t{\n\t\t\talert(\"");
/* 3582 */                   out.print(FormatUtil.getString("am.webclient.rbm.validpollinginterval.text"));
/* 3583 */                   out.write("\");\n\t\t\treturn;\n\t\t}\n\n\t\tdocument.AMActionForm.moname.value=\"RBM\";\t//No I18N\n\t}\n\tdocument.AMActionForm.submit();\n\t");
/* 3584 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3585 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3589 */               if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3590 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */               }
/*      */               
/* 3593 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3594 */               out.write("\n}\n </script>\n<div id=\"edit\" style=\"DISPLAY: none\">\n\n\n ");
/*      */               
/* 3596 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 3597 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 3598 */               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3600 */               _jspx_th_html_005fform_005f0.setAction("/showresource.do");
/*      */               
/* 3602 */               _jspx_th_html_005fform_005f0.setMethod("post");
/*      */               
/* 3604 */               _jspx_th_html_005fform_005f0.setStyle("display:inline;\"  accept-charset=\"UTF-8\"");
/* 3605 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 3606 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                 for (;;) {
/* 3608 */                   out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"bdarkborder\">\n\t<tr >\n   <td width=\"50%\">\n\t\t <table width=\"99%\" border=0 class=\"lrtbdarkborder\" cellspacing=\"0\">\n\t\t\t <tr>\n    <td height=\"28\"   class=\"tableheading\">");
/* 3609 */                   out.print(FormatUtil.getString("am.webclient.common.configurationdetails.text"));
/* 3610 */                   out.write("\n      <input type=\"hidden\" name=\"seqid\" value=\"");
/* 3611 */                   out.print(seqid);
/* 3612 */                   out.write("\">\n      <input type=\"hidden\" name=\"moname\" value=\"");
/* 3613 */                   if (_jspx_meth_c_005fout_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3615 */                   out.write("\">\n      <input type=\"hidden\" name=\"method\" value=\"editUrlSequenceDetails\">\n\n\t\t</td>\n\n<!-- td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\" > <img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\" >\n\tspan class=\"bodytextboldwhiteun\" ><a href=\"javascript:hideDiv('edit')\" class=\"tableheading\">");
/* 3616 */                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 3617 */                   out.write("</a></span>\n</td -->\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n\t<TR>\n        <TD height=\"28\" width=\"25%\" class=\"bodytext label-align\">");
/* 3618 */                   out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 3619 */                   out.write("<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\"> <INPUT NAME=\"displayname\" TYPE=\"text\" class=\"formtext\" VALUE=\"");
/* 3620 */                   if (_jspx_meth_c_005fout_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3622 */                   out.write("\" SIZE=\"35\">\n\t</TR>\n\t<TR>\n        <TD height=\"28\" class=\"bodytext label-align\">");
/* 3623 */                   out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 3624 */                   out.write("<span class=\"mandatory\">*</span></TD>\n        <TD height=\"28\"> <INPUT NAME=\"pollInterval\" TYPE=\"text\" class=\"formtext\" VALUE=\"");
/* 3625 */                   if (_jspx_meth_c_005fout_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3627 */                   out.write("\" SIZE=\"15\"><span class=\"bodytext\">&nbsp;");
/* 3628 */                   out.print(FormatUtil.getString("am.webclient.urlmonitor.unitofpoll.text"));
/* 3629 */                   out.write("</span>\n\t</TD>\n\t</TR>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"25%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"75%\" class=\"tablebottom\">\n    <input name=\"Submit\" value=\" ");
/* 3630 */                   out.print(FormatUtil.getString("Update"));
/* 3631 */                   out.write("\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"fnFormSubmit();\">\n      <input name=\"reset\" type=\"reset\" class=\"buttons btn_link\" value=\"");
/* 3632 */                   out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3633 */                   out.write("\" onClick=\"javascript:toggleDiv('edit')\" /> \n\t\t</td> \n\t  <tr>\n\t\t</table>\n\t</td>\n\t<td width=\"49%\">\n\t\t<table class=\"\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\" height=\"90%\" >\n\t\t\t<tr>\n\t\t\t\t<td class=helpCardHdrTopLeft> </td>\n\t\t\t\t<td class=\"helpCardHdrTopBg\">\n\t\t\t\t\t<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td class=\"helpCardContentBg\" align=\"left\" valign=\"middle\">\n\t\t\t\t\t\t\t\t\t<span class=\"helpHdrTxt\">");
/* 3634 */                   out.print(FormatUtil.getString("am.mypage.healp.card.text"));
/* 3635 */                   out.write("</span>\n\t\t\t\t\t\t\t\t\t<img src=\"/images/helpCue.gif\" align=\"texttop\" height=\"16\" width=\"19\"/>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td class=\"helpCardHdrRightEar\" align=\"left\" valign=\"middle\"/>\n\t\t\t\t\t\t\t\t<td align=\"left\" valign=\"middle\"/> </td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</tbody>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t\t<td class=\"helpCardHdrRightTop\" > </td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\"/>\n\t\t\t\t<td valign=\"top\">\n\t\t\t\t\t<table align=\"center\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" width=\"100%\">\n\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td style=\"padding-top: 10px;\" class=\"boxedContent\">\n\t\t\t\t\t\t\t\t\t<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t\t\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerTopLeft\"/>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerTopBg\"/>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerTopRight\"/>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n");
/* 3636 */                   out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerBoxBg\"/>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerBoxBg product-help\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 3637 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.helpcard.text"));
/* 3638 */                   out.write(32);
/* 3639 */                   out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<BR>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<BR>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerBoxBg\"/>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</tbody>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\"/>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td class=\"helpCardMainBtmLeft\"/>\n\t\t\t\t<td class=\"helpCardMainBtmBg\"/>\n\t\t\t\t<td class=\"helpCardMainBtmRight\"/>\n\t\t\t</tr>\n\t\t</table>\t\n\t</td>\n\t</tr>\n</table>\n");
/* 3640 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3641 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3645 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3646 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */               }
/*      */               
/* 3649 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3650 */               out.write("\n\n<br>\n</div>\n\n\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n  <tr>\n   <td valign=\"top\" width=\"60%\"> <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"lrtbdarkborder\">\n        <tr>\n        <td colspan=\"2\"   class=\"tableheadingbborder\">");
/* 3651 */               out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 3652 */               out.write("</td>\n      </tr>\n      <tr>\n        <td class=\"monitorinfoodd\">");
/* 3653 */               out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 3654 */               out.write(" </td>\n        <td class=\"monitorinfoodd\">");
/* 3655 */               if (_jspx_meth_c_005fout_005f18(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3657 */               out.write("</td>\n\t\t</tr>\n\t\t");
/* 3658 */               out.write("<!--$Id$-->\n");
/*      */               
/* 3660 */               String hostName = "localhost";
/*      */               try {
/* 3662 */                 hostName = InetAddress.getLocalHost().getHostName();
/*      */               } catch (Exception ex) {
/* 3664 */                 ex.printStackTrace();
/*      */               }
/* 3666 */               String portNumber = System.getProperty("webserver.port");
/* 3667 */               String styleClass = "monitorinfoodd";
/* 3668 */               if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 3669 */                 styleClass = "whitegrayborder-conf-mon";
/*      */               }
/*      */               
/* 3672 */               out.write(10);
/*      */               
/* 3674 */               PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3675 */               _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3676 */               _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3678 */               _jspx_th_logic_005fpresent_005f6.setRole("ENTERPRISEADMIN");
/* 3679 */               int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3680 */               if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                 for (;;) {
/* 3682 */                   out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 3683 */                   out.print(styleClass);
/* 3684 */                   out.write(34);
/* 3685 */                   out.write(62);
/* 3686 */                   out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 3687 */                   out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 3688 */                   out.print(styleClass);
/* 3689 */                   out.write(34);
/* 3690 */                   out.write(62);
/* 3691 */                   out.print(hostName);
/* 3692 */                   out.write(95);
/* 3693 */                   out.print(portNumber);
/* 3694 */                   out.write("</td>\n</tr>\n");
/* 3695 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3696 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3700 */               if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3701 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */               }
/*      */               
/* 3704 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3705 */               out.write(10);
/* 3706 */               out.write(10);
/* 3707 */               String message = getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + healthAttID + "#" + "MESSAGE"), healthAttID, alert.getProperty(resourceid + "#" + healthAttID), resourceid);
/*      */               
/* 3709 */               String healthreport = getTrimmedText(message, 200);
/* 3710 */               out.write("\n\n\t\t<tr>\n\t\t<td class=\"monitorinfoeven\" valign=\"top\">");
/* 3711 */               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3712 */               out.write("</td>\n\t\t<td class=\"monitorinfoeven apm-breakword\" style=\"width:340px\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3713 */               if (_jspx_meth_c_005fout_005f19(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3715 */               out.write("&attributeid=");
/* 3716 */               out.print(healthAttID);
/* 3717 */               out.write("')\">");
/* 3718 */               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + healthAttID)));
/* 3719 */               out.write("</a>\n\t\t ");
/* 3720 */               out.print(message);
/* 3721 */               out.write("\n\t\t ");
/* 3722 */               if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, healthAttID) != 0) {
/* 3723 */                 out.write("\n\t\t <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 3724 */                 out.print(resourceid + "_" + healthAttID);
/* 3725 */                 out.write("&monitortype=");
/* 3726 */                 out.print(resourcetype);
/* 3727 */                 out.write("')\">");
/* 3728 */                 out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 3729 */                 out.write("</a></span>\n         ");
/*      */               }
/* 3731 */               out.write("\n\t\t</td>\n\t\t</tr>\n\n\t\t  <tr>\n\t\t  <td class=\"monitorinfoodd\">");
/* 3732 */               out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 3733 */               out.write(" </td>\n\t\t");
/*      */               
/* 3735 */               if (resType.equals("RBM"))
/*      */               {
/*      */ 
/* 3738 */                 out.write("\n\t\t  <td class=\"monitorinfoodd\">");
/* 3739 */                 out.print(FormatUtil.getString("am.reporttab.shortname.rbmmonitoring.text"));
/* 3740 */                 out.write("</td>\n\t\t");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/* 3746 */                 out.write("\n\t\t  <td class=\"monitorinfoodd\">");
/* 3747 */                 out.print(FormatUtil.getString("am.webclient.urlseq.type.text"));
/* 3748 */                 out.write("</td>\n\t\t  ");
/*      */               }
/*      */               
/*      */ 
/* 3752 */               out.write("\n\t\t  </tr>\n      <tr >\n        <td class=\"monitorinfoeven\"> ");
/* 3753 */               out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 3754 */               out.write("  </td>\n        <td class=\"monitorinfoeven\"> ");
/* 3755 */               if (_jspx_meth_c_005fout_005f20(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3757 */               out.write(32);
/* 3758 */               out.print(FormatUtil.getString("am.webclient.urlmonitor.unitofpoll.text"));
/* 3759 */               out.write("\n        </td>\n\t\t</tr>\n\n\t\t");
/*      */               
/* 3761 */               if (resType.equals("RBM"))
/*      */               {
/*      */ 
/* 3764 */                 out.write("\n\t\t<tr >\n        <td class=\"monitorinfoeven\"> ");
/* 3765 */                 out.print(FormatUtil.getString("am.webclient.rbm.agent.text"));
/* 3766 */                 out.write("  </td>\n        <td class=\"monitorinfoeven\"> ");
/* 3767 */                 if (_jspx_meth_c_005fout_005f21(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 3769 */                 out.write("&nbsp;\n\n\t\t");
/* 3770 */                 if (_jspx_meth_logic_005fnotPresent_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 3772 */                 out.write("\n\t\t</td>\n\t\t</tr>\n\n\t\t<tr>\n        <td class=\"monitorinfoeven\"> ");
/* 3773 */                 out.print(FormatUtil.getString("am.webclient.rbm.script.text"));
/* 3774 */                 out.write("  </td>\n        <td class=\"monitorinfoeven\"> ");
/* 3775 */                 if (_jspx_meth_c_005fout_005f22(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 3777 */                 out.write("&nbsp;\n\n\t\t");
/*      */                 
/* 3779 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3780 */                 _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 3781 */                 _jspx_th_logic_005fnotPresent_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 3783 */                 _jspx_th_logic_005fnotPresent_005f5.setRole("OPERATOR");
/* 3784 */                 int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 3785 */                 if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                   for (;;) {
/* 3787 */                     out.write(10);
/* 3788 */                     out.write(9);
/* 3789 */                     out.write(9);
/*      */                     
/* 3791 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3792 */                     _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 3793 */                     _jspx_th_logic_005fnotPresent_005f6.setParent(_jspx_th_logic_005fnotPresent_005f5);
/*      */                     
/* 3795 */                     _jspx_th_logic_005fnotPresent_005f6.setRole("ENTERPRISEADMIN");
/* 3796 */                     int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 3797 */                     if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */                       for (;;) {
/* 3799 */                         out.write("\n\t\t|&nbsp;<a href=\"#\" style=\"color: #595959;\" onclick=\"javascript:openScriptManager('webscripttab');\"   class=\"staticlinks\" >");
/* 3800 */                         out.print(FormatUtil.getString("am.webclient.rbm.recordbutton.text"));
/* 3801 */                         out.write("</a> </td>\n\t\t</tr>\n\t\t");
/* 3802 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 3803 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3807 */                     if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 3808 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6); return;
/*      */                     }
/*      */                     
/* 3811 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 3812 */                     out.write(10);
/* 3813 */                     out.write(9);
/* 3814 */                     out.write(9);
/* 3815 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 3816 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3820 */                 if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 3821 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5); return;
/*      */                 }
/*      */                 
/* 3824 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 3825 */                 out.write("\n\n\t\t");
/*      */               }
/*      */               
/*      */ 
/* 3829 */               out.write("\n\n\n\t\t<tr>\n\t\t<td class=\"monitorinfoeven\">");
/* 3830 */               out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3831 */               out.write("</td>\n\t\t<td class=\"monitorinfoeven\">");
/* 3832 */               if (_jspx_meth_c_005fout_005f23(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3834 */               out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td class=\"monitorinfoodd\">");
/* 3835 */               out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3836 */               out.write("</td>\n\t\t<td class=\"monitorinfoodd\">");
/* 3837 */               if (_jspx_meth_c_005fout_005f24(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3839 */               out.write("</td>\n\t\t</tr>\n\t\t\t");
/* 3840 */               JspRuntimeLibrary.include(request, response, "MyField_trstrip.jsp", out, false);
/* 3841 */               out.write("\n    </table>\n\n    </td>\n     <br> </td>\n    <td valign=\"top\"> <table align=left width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n\n\n    <tr>\n      <td height=\"25\" colspan=\"3\" class=\"tableheadingbborder\" >");
/* 3842 */               out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 3843 */               out.write("  </td>\n    </tr>\n\n\n        <tr align=\"center\">\n          <td height=\"25\" colspan=\"3\" class=\"whitegrayborder\" >\n\n<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n              <tr>\n                <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3844 */               if (_jspx_meth_c_005fout_005f25(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3846 */               out.write("&period=1')\">\n                  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3847 */               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3848 */               out.write("\"></a></td>\n                <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3849 */               if (_jspx_meth_c_005fout_005f26(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 3851 */               out.write("&period=2')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3852 */               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3853 */               out.write("\"></a></td>\n              </tr>\n            </table>\n<!-- </td>\n</tr>\n        <tr align=\"center\">\n          <td height=\"23\" colspan=\"3\" class=\"whitegrayborder\" > -->\n\n\t      ");
/*      */               
/* 3855 */               wlsGraph.setParam(seqid, "AVAILABILITY");
/*      */               
/* 3857 */               out.write("\n            ");
/*      */               
/* 3859 */               AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3860 */               _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 3861 */               _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3863 */               _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */               
/* 3865 */               _jspx_th_awolf_005fpiechart_005f0.setWidth("300");
/*      */               
/* 3867 */               _jspx_th_awolf_005fpiechart_005f0.setHeight("180");
/*      */               
/* 3869 */               _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */               
/* 3871 */               _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */               
/* 3873 */               _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */               
/* 3875 */               _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 3876 */               int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3877 */               if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3878 */                 if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3879 */                   out = _jspx_page_context.pushBody();
/* 3880 */                   _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3881 */                   _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3884 */                   out.write("\n            ");
/*      */                   
/* 3886 */                   Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3887 */                   _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3888 */                   _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                   
/* 3890 */                   _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3891 */                   int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3892 */                   if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3893 */                     if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3894 */                       out = _jspx_page_context.pushBody();
/* 3895 */                       _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3896 */                       _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 3899 */                       out.write(32);
/*      */                       
/* 3901 */                       AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3902 */                       _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3903 */                       _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                       
/* 3905 */                       _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                       
/* 3907 */                       _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 3908 */                       int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3909 */                       if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3910 */                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                       }
/*      */                       
/* 3913 */                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3914 */                       out.write("\n            ");
/*      */                       
/* 3916 */                       AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3917 */                       _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3918 */                       _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                       
/* 3920 */                       _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                       
/* 3922 */                       _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 3923 */                       int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3924 */                       if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3925 */                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                       }
/*      */                       
/* 3928 */                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3929 */                       out.write(32);
/* 3930 */                       int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3931 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 3934 */                     if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3935 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 3938 */                   if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3939 */                     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                   }
/*      */                   
/* 3942 */                   this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3943 */                   out.write(32);
/* 3944 */                   int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3945 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3948 */                 if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3949 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3952 */               if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3953 */                 this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */               }
/*      */               
/* 3956 */               this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3957 */               out.write("\n\t<!--<//awolf:stackbarchart dataSetProducer=\"stackgraph\" width=\"300\" height=\"180\" legend=\"true\" url=\"false\" xaxisLabel=\"<//%= FormatUtil.getString(\"am.webclient.urlseq.xaxis.text\")%>\" yaxisLabel=\"<//%= FormatUtil.getString(\"am.webclient.urlseq.yaxis.text\")%>\" legendanchor=\"EAST\" >\n\t<///awolf:stackbarchart>-->\n\n          </td>\n        </tr>\n        <tr>\n\t\t          <td height=\"25\" colspan=\"2\" width=\"50%\" class=\"yellowgrayborder\">");
/* 3958 */               out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 3959 */               out.write("\n\t\t           <a style=\"position:relative; top:2px;\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3960 */               out.print(seqid);
/* 3961 */               out.write("&attributeid=");
/* 3962 */               out.print(availAttID);
/* 3963 */               out.write("')\">\n\t\t            ");
/* 3964 */               out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + availAttID)));
/* 3965 */               out.write("</a>\n\t\t          </td>\n\t\t                    <td height=\"25\" width=\"50%\" align=\"right\" class=\"yellowgrayborder\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3966 */               out.print(seqid);
/* 3967 */               out.write("&attributeIDs=");
/* 3968 */               out.print(availabilitykeys.get(resType));
/* 3969 */               out.write(44);
/* 3970 */               out.print(healthkeys.get(resType));
/* 3971 */               out.write("&attributeToSelect=");
/* 3972 */               out.print(availabilitykeys.get(resType));
/* 3973 */               out.write("&redirectto=");
/* 3974 */               out.print(encodeurl);
/* 3975 */               out.write("\" class=\"links\">");
/* 3976 */               out.print(ALERTCONFIG_TEXT);
/* 3977 */               out.write("</a>&nbsp;\n\t\t          </td>\n        </tr>\n\n\n\n</table>\n</td>\n</tr>\n</table>\n<br>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3978 */               JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 3979 */               out.write("</td></tr></table>\n\n\n\t");
/*      */               
/* 3981 */               ArrayList list = new ArrayList();
/*      */               
/* 3983 */               Vector v = new Vector();
/* 3984 */               if (resourceName != null)
/*      */               {
/* 3986 */                 String oldquery = "select resourcename ,COALESCE(min(SEVERITY),5) , COALESCE(count(*),0) , null ,source ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedObject,AM_PARENTCHILDMAPPER Left outer join Alert on  AM_ManagedObject.RESOURCEID=source where AM_PARENTCHILDMAPPER.PARENTID=" + seqid + " AND AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID group by RESOURCENAME,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID,source order by AM_ManagedObject.RESOURCEID";
/*      */                 
/*      */ 
/* 3989 */                 String query = "select resourcename ,COALESCE(min(SEVERITY),5) , COALESCE(count(*),0) , null ,source ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedObject join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID Left outer join Alert on  AM_ManagedObject.RESOURCEID=source where AM_PARENTCHILDMAPPER.PARENTID=" + seqid + "  group by RESOURCENAME,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID,source, AM_ManagedObject.TYPE order by AM_ManagedObject.RESOURCEID";
/*      */                 
/*      */ 
/* 3992 */                 FormatUtil.printQueryChange("urlseqdetails.jsp", oldquery, query);
/*      */                 
/* 3994 */                 list = mo.getRows(query);
/*      */                 
/* 3996 */                 request.setAttribute("parentid", request.getParameter("resourceid"));
/* 3997 */                 request.setAttribute("resourcetable", list);
/*      */               }
/* 3999 */               for (int j = 0; j < list.size(); j++)
/*      */               {
/* 4001 */                 ArrayList l1 = (ArrayList)list.get(j);
/* 4002 */                 for (int k = 0; k < l1.size(); k++)
/*      */                 {
/* 4004 */                   if (!v.contains((String)l1.get(6)))
/* 4005 */                     v.add((String)l1.get(6));
/*      */                 }
/*      */               }
/* 4008 */               request.setAttribute("Resids", v);
/*      */               
/* 4010 */               StringBuffer condition = new StringBuffer("");
/* 4011 */               StringBuffer keycondition = new StringBuffer("");
/* 4012 */               if ((v != null) && (v.size() > 0))
/*      */               {
/* 4014 */                 for (int i = 0; i < v.size(); i++)
/*      */                 {
/* 4016 */                   if (i + 1 == v.size())
/*      */                   {
/*      */ 
/* 4019 */                     condition.append(v.get(i) + "");
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/*      */ 
/* 4025 */                     condition.append(v.get(i) + ",");
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/* 4031 */               String s = condition.toString();
/*      */               
/* 4033 */               sumgraph.setResid(s);
/* 4034 */               sumgraph.setAttributeid(avgresponseTimeAttID);
/*      */               
/* 4036 */               sumgraph.setArchivedforUrl(false);
/* 4037 */               sumgraph.setCompareUrls(true);
/* 4038 */               int height = 350;
/* 4039 */               if (list != null)
/*      */               {
/* 4041 */                 if (list.size() >= 5)
/* 4042 */                   height = 350 + (list.size() - 5) * 30;
/*      */               }
/* 4044 */               String height_graph = String.valueOf(height);
/*      */               
/*      */ 
/* 4047 */               out.write("\n      <br>\n     <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbtborder\">\n\t\t  <tr>\n\t\t\t    <td width=\"82%\" height=\"26\" class=\"tableheadingbborder\">\n\t\t");
/* 4048 */               if (resType.equals("RBM"))
/*      */               {
/*      */ 
/* 4051 */                 out.write("\n\t\t  ");
/* 4052 */                 out.print(FormatUtil.getString("am.webclient.rbmurlseq.heading.text"));
/* 4053 */                 out.write(10);
/* 4054 */                 out.write(9);
/* 4055 */                 out.write(9);
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/* 4061 */                 out.write("\n\t\t  ");
/* 4062 */                 out.print(FormatUtil.getString("am.webclient.urlseq.heading.text"));
/* 4063 */                 out.write("\n\t\t  ");
/*      */               }
/*      */               
/*      */ 
/* 4067 */               out.write("\n\n\n\t\t\t\t </td>\n\t\t  </tr>\n\n\t\t  <tr>\n\t\t      <td width=\"99%\" height=\"24\">\n\t\t          <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t");
/*      */               
/* 4069 */               if (resourceName != null)
/*      */               {
/* 4071 */                 if (resType.equals("RBM"))
/*      */                 {
/*      */ 
/* 4074 */                   out.write("\n\t\t\t\t\t\t\t\t");
/* 4075 */                   if (_jspx_meth_c_005fset_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/* 4077 */                   out.write("\n\t\t\t\t\t\t\t\t");
/* 4078 */                   JspRuntimeLibrary.include(request, response, "displayurls.jsp?network=RBMURL", out, false);
/* 4079 */                   out.write("\n\t\t\t\t\t");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 4085 */                   out.write("\n\t\t\t\t\t\t\t\t");
/* 4086 */                   if (_jspx_meth_c_005fset_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/* 4088 */                   out.write("\n\t\t\t\t\t\t\t\t");
/* 4089 */                   JspRuntimeLibrary.include(request, response, "displayurls.jsp?network=UrlEle", out, false);
/* 4090 */                   out.write("\n\n\t\t\t\t");
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/* 4095 */               out.write("\n\t\t          </table>\n\t\t       </td>\n\t\t  </tr>\n      </table>\n<br>\n      <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n  <tr>\n    <td align=\"left\" class=\"tableheadingbborder\">");
/* 4096 */               out.print(FormatUtil.getString("am.webclient.urlseq.totalresponsetime.text"));
/* 4097 */               out.write(" </td>\n\n  <tr>\n    <td width=\"99%\" align=\"left\" valign=\"top\" class=\"whitegrayborder\">\n      ");
/*      */               
/* 4099 */               wlsGraph.setParam(resourceid, "TOTALRESPONSETIME");
/*      */               
/*      */ 
/* 4102 */               out.write("\n      <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n          <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4103 */               if (_jspx_meth_c_005fout_005f27(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4105 */               out.write("&attributeid=");
/* 4106 */               out.print(responseTimeAttID);
/* 4107 */               out.write("&period=-7&resourcename=");
/* 4108 */               if (_jspx_meth_c_005fout_005f28(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4110 */               out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 4111 */               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4112 */               out.write("'></td>\n          <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4113 */               if (_jspx_meth_c_005fout_005f29(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4115 */               out.write("&attributeid=");
/* 4116 */               out.print(responseTimeAttID);
/* 4117 */               out.write("&period=-30&resourcename=");
/* 4118 */               if (_jspx_meth_c_005fout_005f30(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4120 */               out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 4121 */               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4122 */               out.write("'></td>\n        </tr>\n        <tr>\n          <td colspan=\"2\"> \n            ");
/*      */               
/* 4124 */               XYAreaChart _jspx_th_awolf_005fxyareachart_005f0 = (XYAreaChart)this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer.get(XYAreaChart.class);
/* 4125 */               _jspx_th_awolf_005fxyareachart_005f0.setPageContext(_jspx_page_context);
/* 4126 */               _jspx_th_awolf_005fxyareachart_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4128 */               _jspx_th_awolf_005fxyareachart_005f0.setDataSetProducer("wlsGraph");
/*      */               
/* 4130 */               _jspx_th_awolf_005fxyareachart_005f0.setWidth("650");
/*      */               
/* 4132 */               _jspx_th_awolf_005fxyareachart_005f0.setHeight("200");
/*      */               
/* 4134 */               _jspx_th_awolf_005fxyareachart_005f0.setLegend("false");
/*      */               
/* 4136 */               _jspx_th_awolf_005fxyareachart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/*      */               
/* 4138 */               _jspx_th_awolf_005fxyareachart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.urlmonitor.responsewithunit.text"));
/*      */               
/* 4140 */               _jspx_th_awolf_005fxyareachart_005f0.setDataSetType("SubSeriesDataset");
/*      */               
/* 4142 */               _jspx_th_awolf_005fxyareachart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4143 */               int _jspx_eval_awolf_005fxyareachart_005f0 = _jspx_th_awolf_005fxyareachart_005f0.doStartTag();
/* 4144 */               if (_jspx_eval_awolf_005fxyareachart_005f0 != 0) {
/* 4145 */                 if (_jspx_eval_awolf_005fxyareachart_005f0 != 1) {
/* 4146 */                   out = _jspx_page_context.pushBody();
/* 4147 */                   _jspx_th_awolf_005fxyareachart_005f0.setBodyContent((BodyContent)out);
/* 4148 */                   _jspx_th_awolf_005fxyareachart_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 4151 */                   out.write("\n            ");
/* 4152 */                   int evalDoAfterBody = _jspx_th_awolf_005fxyareachart_005f0.doAfterBody();
/* 4153 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 4156 */                 if (_jspx_eval_awolf_005fxyareachart_005f0 != 1) {
/* 4157 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 4160 */               if (_jspx_th_awolf_005fxyareachart_005f0.doEndTag() == 5) {
/* 4161 */                 this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer.reuse(_jspx_th_awolf_005fxyareachart_005f0); return;
/*      */               }
/*      */               
/* 4164 */               this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer.reuse(_jspx_th_awolf_005fxyareachart_005f0);
/* 4165 */               out.write(32);
/* 4166 */               out.write(32);
/* 4167 */               out.write(" \n          </td>\n        </tr>\n      </table>\n    </td>\n    </tr>\n    <tr>\n    <td width=\"99%\" align=\"center\" valign=\"top\" >\n      <table align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" >\n        <tr>\n          <td class=\"columnheadingtb\"><span class=\"bodytextbold\">");
/* 4168 */               if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4170 */               out.write("</span></td>");
/* 4171 */               out.write("\n          <td class=\"columnheadingtb\"><span class=\"bodytextbold\">");
/* 4172 */               if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4174 */               out.write("</span></td>");
/* 4175 */               out.write("\n          <td class=\"columnheadingtb\"><span class=\"bodytextbold\">");
/* 4176 */               if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4178 */               out.write("</span></td>");
/* 4179 */               out.write("\n        </tr>\n        <tr>\n          <td width=\"47%\" height=\"25\" class=\"yellowgrayborderbr\" >");
/* 4180 */               out.print(FormatUtil.getString("am.webclient.urlmonitor.avgresponse.text"));
/* 4181 */               out.write("\n            ");
/* 4182 */               out.print(FormatUtil.getString("Time"));
/* 4183 */               out.write(" </td>\n          <td width=\"25%\" height=\"25\" class=\"yellowgrayborderbr\"> ");
/* 4184 */               if (_jspx_meth_c_005fif_005f12(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4186 */               out.write("\n            ");
/* 4187 */               if (_jspx_meth_c_005fif_005f13(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4189 */               out.write(32);
/*      */               
/* 4191 */               IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4192 */               _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 4193 */               _jspx_th_c_005fif_005f14.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4195 */               _jspx_th_c_005fif_005f14.setTest("${!empty requestScope.avgresponsetime}");
/* 4196 */               int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 4197 */               if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                 for (;;) {
/* 4199 */                   out.write("\n           ");
/* 4200 */                   out.print(FormatUtil.getString("ms"));
/* 4201 */                   out.write(32);
/* 4202 */                   out.write(32);
/* 4203 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 4204 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4208 */               if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 4209 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */               }
/*      */               
/* 4212 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 4213 */               out.write(" </td>\n          <td width=\"28%\" class=\"yellowgrayborder\">&nbsp;</td>\n        </tr>\n        <tr>\n          <td width=\"47%\" height=\"25\" class=\"whitegrayborderbr\">");
/* 4214 */               out.print(FormatUtil.getString("am.webclient.urlmonitor.curresponse.text"));
/* 4215 */               out.write("\n            ");
/* 4216 */               out.print(FormatUtil.getString("Time"));
/* 4217 */               out.write(" </td>\n          <td width=\"25%\" height=\"25\" class=\"whitegrayborderbr\">\n\n           ");
/* 4218 */               if (_jspx_meth_c_005fif_005f15(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4220 */               out.write("\n            ");
/* 4221 */               if (_jspx_meth_c_005fif_005f16(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4223 */               out.write(32);
/*      */               
/* 4225 */               IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4226 */               _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 4227 */               _jspx_th_c_005fif_005f17.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4229 */               _jspx_th_c_005fif_005f17.setTest("${!empty requestScope.currentvalue}");
/* 4230 */               int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 4231 */               if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                 for (;;) {
/* 4233 */                   out.write("\n           ");
/* 4234 */                   out.print(FormatUtil.getString("ms"));
/* 4235 */                   out.write(32);
/* 4236 */                   out.write(32);
/* 4237 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 4238 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4242 */               if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 4243 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */               }
/*      */               
/* 4246 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 4247 */               out.write("\n\n\n\n<!--<//c:if test=\"$//{requestScope.availability!=1}\">\n          <//c:if test=\"$//{!empty requestScope.currentvalue}\">\n           <//fmt:formatNumber maxFractionDigits=\"0\"> <//c:out value=\"$//{requestScope.currentvalue }\"/><///fmt:formatNumber>\n           <///c:if>\n          <//c:if test=\"$//{empty requestScope.currentvalue}\">-<///c:if>\n           <//c:if test=\"$//{!empty requestScope.currentvalue}\"><//%=FormatUtil.getString(\"am.webclient.hostResource.ms\")%>\n            <///c:if> <///c:if>-->\n          </td>\n          <td width=\"28%\" class=\"whitegrayborder\">&nbsp; ");
/*      */               
/* 4249 */               IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4250 */               _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 4251 */               _jspx_th_c_005fif_005f18.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4253 */               _jspx_th_c_005fif_005f18.setTest("${!empty requestScope.currentvalue}");
/* 4254 */               int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 4255 */               if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                 for (;;) {
/* 4257 */                   out.write("\n            <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4258 */                   out.print(resourceid);
/* 4259 */                   out.write("&attributeid=");
/* 4260 */                   out.print(responseTimeAttID);
/* 4261 */                   out.write("')\">");
/* 4262 */                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + responseTimeAttID)));
/* 4263 */                   out.write(" </a>\n            ");
/* 4264 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 4265 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4269 */               if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 4270 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */               }
/*      */               
/* 4273 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 4274 */               out.write("</td>\n        </tr>\n\n        <tr>\n          <td  colspan=\"3\" height=\"35\" class=\"yellowgrayborderbr\" align=\"right\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\"  title=\"\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4275 */               out.print(resourceid);
/* 4276 */               out.write("&attributeIDs=");
/* 4277 */               out.print(responseTimeAttID);
/* 4278 */               out.write("&attributeToSelect=");
/* 4279 */               out.print(responseTimeAttID);
/* 4280 */               out.write("&redirectto=");
/* 4281 */               out.print(encodeurl);
/* 4282 */               out.write("\" class=\"staticlinks\">");
/* 4283 */               out.print(ALERTCONFIG_TEXT);
/* 4284 */               out.write("</a>\n          </td>\n        </tr>\n      </table>\n    </td>\n    </tr>\n</table>\n<br /><br />\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n   <td colspan=\"2\" width=\"72%\" height=\"26\" class=\"tableheadingbborder\">");
/* 4285 */               out.print(FormatUtil.getString("am.webclient.urlmonitor.response.splitup.text"));
/* 4286 */               out.write(" </td>\n  </tr>\n  <tr>\n    <td colspan=\"2\"> \n      ");
/*      */               
/* 4288 */               wlsGraph.setParam(resourceid, "URLTOTALRESPONSESPLITUP");
/*      */               
/* 4290 */               out.write("\n      <br /><br />\n      ");
/*      */               
/* 4292 */               StackedXYAreaChart _jspx_th_awolf_005fstackedxyareachart_005f0 = (StackedXYAreaChart)this._005fjspx_005ftagPool_005fawolf_005fstackedxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer.get(StackedXYAreaChart.class);
/* 4293 */               _jspx_th_awolf_005fstackedxyareachart_005f0.setPageContext(_jspx_page_context);
/* 4294 */               _jspx_th_awolf_005fstackedxyareachart_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4296 */               _jspx_th_awolf_005fstackedxyareachart_005f0.setDataSetProducer("wlsGraph");
/*      */               
/* 4298 */               _jspx_th_awolf_005fstackedxyareachart_005f0.setWidth("650");
/*      */               
/* 4300 */               _jspx_th_awolf_005fstackedxyareachart_005f0.setHeight("230");
/*      */               
/* 4302 */               _jspx_th_awolf_005fstackedxyareachart_005f0.setLegend("true");
/*      */               
/* 4304 */               _jspx_th_awolf_005fstackedxyareachart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/*      */               
/* 4306 */               _jspx_th_awolf_005fstackedxyareachart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.urlmonitor.responsewithunit.text"));
/*      */               
/* 4308 */               _jspx_th_awolf_005fstackedxyareachart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/*      */               
/* 4310 */               _jspx_th_awolf_005fstackedxyareachart_005f0.setDataSetType("TableXYDataset");
/* 4311 */               int _jspx_eval_awolf_005fstackedxyareachart_005f0 = _jspx_th_awolf_005fstackedxyareachart_005f0.doStartTag();
/* 4312 */               if (_jspx_eval_awolf_005fstackedxyareachart_005f0 != 0) {
/* 4313 */                 if (_jspx_eval_awolf_005fstackedxyareachart_005f0 != 1) {
/* 4314 */                   out = _jspx_page_context.pushBody();
/* 4315 */                   _jspx_th_awolf_005fstackedxyareachart_005f0.setBodyContent((BodyContent)out);
/* 4316 */                   _jspx_th_awolf_005fstackedxyareachart_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 4319 */                   out.write(32);
/* 4320 */                   out.write("\n      ");
/* 4321 */                   int evalDoAfterBody = _jspx_th_awolf_005fstackedxyareachart_005f0.doAfterBody();
/* 4322 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 4325 */                 if (_jspx_eval_awolf_005fstackedxyareachart_005f0 != 1) {
/* 4326 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 4329 */               if (_jspx_th_awolf_005fstackedxyareachart_005f0.doEndTag() == 5) {
/* 4330 */                 this._005fjspx_005ftagPool_005fawolf_005fstackedxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer.reuse(_jspx_th_awolf_005fstackedxyareachart_005f0); return;
/*      */               }
/*      */               
/* 4333 */               this._005fjspx_005ftagPool_005fawolf_005fstackedxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer.reuse(_jspx_th_awolf_005fstackedxyareachart_005f0);
/* 4334 */               out.write(32);
/* 4335 */               out.write("\n    </td>\n  </tr>\n     <td width=\"99%\" align=\"center\" valign=\"top\" >\n      <table align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" >\n        <tr>\n          <td class=\"columnheadingtb\" width=\"40%\" style=\"white-space: nowrap;\"><span class=\"bodytextbold\">");
/* 4336 */               if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4338 */               out.write("</span></td>");
/* 4339 */               out.write("\n          <td class=\"columnheadingtb\" width=\"25%\" style=\"white-space: nowrap;\"><span class=\"bodytextbold\">");
/* 4340 */               if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4342 */               out.write("</span></td>");
/* 4343 */               out.write("\n          <td class=\"columnheadingtb\" width=\"25%\" style=\"white-space: nowrap;\"><span class=\"bodytextbold\">");
/* 4344 */               if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4346 */               out.write("</span></td>");
/* 4347 */               out.write("\n          <td class=\"columnheadingtb\" width=\"10%\" style=\"white-space: nowrap;\"><span class=\"bodytextbold\">");
/* 4348 */               if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4350 */               out.write("</span></td>");
/* 4351 */               out.write("\n        </tr>\n          <tr>\n          <td height=\"25\" class=\"yellowgrayborderbr\" >\n            ");
/* 4352 */               out.print(FormatUtil.getString("am.webclient.urlmonitor.dns.text"));
/* 4353 */               out.write(32);
/* 4354 */               out.print(FormatUtil.getString("Time"));
/* 4355 */               out.write("\n          </td>\n          <td height=\"25\" class=\"yellowgrayborderbr\"> \n            ");
/* 4356 */               if (_jspx_meth_fmt_005fformatNumber_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4358 */               out.write("\n            ");
/* 4359 */               if (_jspx_meth_c_005fif_005f19(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4361 */               out.write(" \n            ");
/*      */               
/* 4363 */               IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4364 */               _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 4365 */               _jspx_th_c_005fif_005f20.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4367 */               _jspx_th_c_005fif_005f20.setTest("${!empty requestScope.avgdnstime}");
/* 4368 */               int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 4369 */               if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */                 for (;;) {
/* 4371 */                   out.print(FormatUtil.getString("ms"));
/* 4372 */                   out.write(32);
/* 4373 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 4374 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4378 */               if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 4379 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */               }
/*      */               
/* 4382 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 4383 */               out.write(" \n          </td>\n          <td height=\"25\" class=\"yellowgrayborderbr\"> \n            ");
/* 4384 */               if (_jspx_meth_fmt_005fformatNumber_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4386 */               out.write("\n            ");
/* 4387 */               if (_jspx_meth_c_005fif_005f21(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4389 */               out.write(" \n            ");
/*      */               
/* 4391 */               IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4392 */               _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 4393 */               _jspx_th_c_005fif_005f22.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4395 */               _jspx_th_c_005fif_005f22.setTest("${!empty requestScope.currentdnsvalue}");
/* 4396 */               int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 4397 */               if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                 for (;;) {
/* 4399 */                   out.print(FormatUtil.getString("ms"));
/* 4400 */                   out.write(32);
/* 4401 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 4402 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4406 */               if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 4407 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */               }
/*      */               
/* 4410 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 4411 */               out.write(" \n          </td>\n          <td class=\"whitegrayborder\">&nbsp; ");
/*      */               
/* 4413 */               IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4414 */               _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 4415 */               _jspx_th_c_005fif_005f23.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4417 */               _jspx_th_c_005fif_005f23.setTest("${!empty requestScope.currentdnsvalue}");
/* 4418 */               int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 4419 */               if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */                 for (;;) {
/* 4421 */                   out.write("\n            <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4422 */                   out.print(resourceid);
/* 4423 */                   out.write("&attributeid=");
/* 4424 */                   out.print(dnsid);
/* 4425 */                   out.write("')\">");
/* 4426 */                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + dnsid)));
/* 4427 */                   out.write(" </a>\n            ");
/* 4428 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 4429 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4433 */               if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 4434 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*      */               }
/*      */               
/* 4437 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 4438 */               out.write("\n          </td>\n        </tr>\n        <tr>\n          <td height=\"25\" class=\"yellowgrayborderbr\" >\n            ");
/* 4439 */               out.print(FormatUtil.getString("am.webclient.urlmonitor.conn.text"));
/* 4440 */               out.write(32);
/* 4441 */               out.print(FormatUtil.getString("Time"));
/* 4442 */               out.write("\n          </td>\n          <td height=\"25\" class=\"yellowgrayborderbr\"> \n            ");
/* 4443 */               if (_jspx_meth_fmt_005fformatNumber_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4445 */               out.write("\n            ");
/* 4446 */               if (_jspx_meth_c_005fif_005f24(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4448 */               out.write(" \n            ");
/*      */               
/* 4450 */               IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4451 */               _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 4452 */               _jspx_th_c_005fif_005f25.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4454 */               _jspx_th_c_005fif_005f25.setTest("${!empty requestScope.avgconntime}");
/* 4455 */               int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 4456 */               if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */                 for (;;) {
/* 4458 */                   out.print(FormatUtil.getString("ms"));
/* 4459 */                   out.write(32);
/* 4460 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 4461 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4465 */               if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 4466 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*      */               }
/*      */               
/* 4469 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 4470 */               out.write(" \n          </td>\n          <td height=\"25\" class=\"yellowgrayborderbr\"> \n            ");
/* 4471 */               if (_jspx_meth_fmt_005fformatNumber_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4473 */               out.write("\n            ");
/* 4474 */               if (_jspx_meth_c_005fif_005f26(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4476 */               out.write(" \n            ");
/*      */               
/* 4478 */               IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4479 */               _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/* 4480 */               _jspx_th_c_005fif_005f27.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4482 */               _jspx_th_c_005fif_005f27.setTest("${!empty requestScope.currentconnvalue}");
/* 4483 */               int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/* 4484 */               if (_jspx_eval_c_005fif_005f27 != 0) {
/*      */                 for (;;) {
/* 4486 */                   out.print(FormatUtil.getString("ms"));
/* 4487 */                   out.write(32);
/* 4488 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/* 4489 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4493 */               if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/* 4494 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27); return;
/*      */               }
/*      */               
/* 4497 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 4498 */               out.write(" \n          </td>\n          <td class=\"whitegrayborder\">&nbsp; ");
/*      */               
/* 4500 */               IfTag _jspx_th_c_005fif_005f28 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4501 */               _jspx_th_c_005fif_005f28.setPageContext(_jspx_page_context);
/* 4502 */               _jspx_th_c_005fif_005f28.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4504 */               _jspx_th_c_005fif_005f28.setTest("${!empty requestScope.currentconnvalue}");
/* 4505 */               int _jspx_eval_c_005fif_005f28 = _jspx_th_c_005fif_005f28.doStartTag();
/* 4506 */               if (_jspx_eval_c_005fif_005f28 != 0) {
/*      */                 for (;;) {
/* 4508 */                   out.write("\n            <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4509 */                   out.print(resourceid);
/* 4510 */                   out.write("&attributeid=");
/* 4511 */                   out.print(connid);
/* 4512 */                   out.write("')\">");
/* 4513 */                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + connid)));
/* 4514 */                   out.write(" </a>\n            ");
/* 4515 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f28.doAfterBody();
/* 4516 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4520 */               if (_jspx_th_c_005fif_005f28.doEndTag() == 5) {
/* 4521 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28); return;
/*      */               }
/*      */               
/* 4524 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 4525 */               out.write("\n          </td>\n        </tr>\n        <tr>\n          <td height=\"25\" class=\"yellowgrayborderbr\" >\n            ");
/* 4526 */               out.print(FormatUtil.getString("am.webclient.urlmonitor.fbt.text"));
/* 4527 */               out.write(32);
/* 4528 */               out.print(FormatUtil.getString("Time"));
/* 4529 */               out.write("\n          </td>\n          <td height=\"25\" class=\"yellowgrayborderbr\"> \n            ");
/* 4530 */               if (_jspx_meth_fmt_005fformatNumber_005f6(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4532 */               out.write("\n            ");
/* 4533 */               if (_jspx_meth_c_005fif_005f29(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4535 */               out.write(" \n            ");
/*      */               
/* 4537 */               IfTag _jspx_th_c_005fif_005f30 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4538 */               _jspx_th_c_005fif_005f30.setPageContext(_jspx_page_context);
/* 4539 */               _jspx_th_c_005fif_005f30.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4541 */               _jspx_th_c_005fif_005f30.setTest("${!empty requestScope.avgfbt}");
/* 4542 */               int _jspx_eval_c_005fif_005f30 = _jspx_th_c_005fif_005f30.doStartTag();
/* 4543 */               if (_jspx_eval_c_005fif_005f30 != 0) {
/*      */                 for (;;) {
/* 4545 */                   out.print(FormatUtil.getString("ms"));
/* 4546 */                   out.write(32);
/* 4547 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f30.doAfterBody();
/* 4548 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4552 */               if (_jspx_th_c_005fif_005f30.doEndTag() == 5) {
/* 4553 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30); return;
/*      */               }
/*      */               
/* 4556 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 4557 */               out.write(" \n          </td>\n          <td height=\"25\" class=\"yellowgrayborderbr\"> \n            ");
/* 4558 */               if (_jspx_meth_fmt_005fformatNumber_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4560 */               out.write("\n            ");
/* 4561 */               if (_jspx_meth_c_005fif_005f31(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4563 */               out.write(" \n            ");
/*      */               
/* 4565 */               IfTag _jspx_th_c_005fif_005f32 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4566 */               _jspx_th_c_005fif_005f32.setPageContext(_jspx_page_context);
/* 4567 */               _jspx_th_c_005fif_005f32.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4569 */               _jspx_th_c_005fif_005f32.setTest("${!empty requestScope.currentfbtvalue}");
/* 4570 */               int _jspx_eval_c_005fif_005f32 = _jspx_th_c_005fif_005f32.doStartTag();
/* 4571 */               if (_jspx_eval_c_005fif_005f32 != 0) {
/*      */                 for (;;) {
/* 4573 */                   out.print(FormatUtil.getString("ms"));
/* 4574 */                   out.write(32);
/* 4575 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f32.doAfterBody();
/* 4576 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4580 */               if (_jspx_th_c_005fif_005f32.doEndTag() == 5) {
/* 4581 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32); return;
/*      */               }
/*      */               
/* 4584 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32);
/* 4585 */               out.write(" \n          </td>\n          <td class=\"whitegrayborder\">&nbsp; ");
/*      */               
/* 4587 */               IfTag _jspx_th_c_005fif_005f33 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4588 */               _jspx_th_c_005fif_005f33.setPageContext(_jspx_page_context);
/* 4589 */               _jspx_th_c_005fif_005f33.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4591 */               _jspx_th_c_005fif_005f33.setTest("${!empty requestScope.currentfbtvalue}");
/* 4592 */               int _jspx_eval_c_005fif_005f33 = _jspx_th_c_005fif_005f33.doStartTag();
/* 4593 */               if (_jspx_eval_c_005fif_005f33 != 0) {
/*      */                 for (;;) {
/* 4595 */                   out.write("\n            <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4596 */                   out.print(resourceid);
/* 4597 */                   out.write("&attributeid=");
/* 4598 */                   out.print(fbtid);
/* 4599 */                   out.write("')\">");
/* 4600 */                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + fbtid)));
/* 4601 */                   out.write(" </a>\n            ");
/* 4602 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f33.doAfterBody();
/* 4603 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4607 */               if (_jspx_th_c_005fif_005f33.doEndTag() == 5) {
/* 4608 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33); return;
/*      */               }
/*      */               
/* 4611 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33);
/* 4612 */               out.write("\n          </td>\n        </tr>\n        <tr>\n          <td height=\"25\" class=\"yellowgrayborderbr\" >\n            ");
/* 4613 */               out.print(FormatUtil.getString("am.webclient.urlmonitor.lbt.text"));
/* 4614 */               out.write(32);
/* 4615 */               out.print(FormatUtil.getString("Time"));
/* 4616 */               out.write("\n          </td>\n          <td height=\"25\" class=\"yellowgrayborderbr\"> \n            ");
/* 4617 */               if (_jspx_meth_fmt_005fformatNumber_005f8(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4619 */               out.write("\n            ");
/* 4620 */               if (_jspx_meth_c_005fif_005f34(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4622 */               out.write(" \n            ");
/*      */               
/* 4624 */               IfTag _jspx_th_c_005fif_005f35 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4625 */               _jspx_th_c_005fif_005f35.setPageContext(_jspx_page_context);
/* 4626 */               _jspx_th_c_005fif_005f35.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4628 */               _jspx_th_c_005fif_005f35.setTest("${!empty requestScope.avglbt}");
/* 4629 */               int _jspx_eval_c_005fif_005f35 = _jspx_th_c_005fif_005f35.doStartTag();
/* 4630 */               if (_jspx_eval_c_005fif_005f35 != 0) {
/*      */                 for (;;) {
/* 4632 */                   out.print(FormatUtil.getString("ms"));
/* 4633 */                   out.write(32);
/* 4634 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f35.doAfterBody();
/* 4635 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4639 */               if (_jspx_th_c_005fif_005f35.doEndTag() == 5) {
/* 4640 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f35); return;
/*      */               }
/*      */               
/* 4643 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f35);
/* 4644 */               out.write(" \n          </td>\n         <td height=\"25\" class=\"yellowgrayborderbr\"> \n            ");
/* 4645 */               if (_jspx_meth_fmt_005fformatNumber_005f9(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4647 */               out.write("\n            ");
/* 4648 */               if (_jspx_meth_c_005fif_005f36(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4650 */               out.write(" \n            ");
/*      */               
/* 4652 */               IfTag _jspx_th_c_005fif_005f37 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4653 */               _jspx_th_c_005fif_005f37.setPageContext(_jspx_page_context);
/* 4654 */               _jspx_th_c_005fif_005f37.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4656 */               _jspx_th_c_005fif_005f37.setTest("${!empty requestScope.currentltbvalue}");
/* 4657 */               int _jspx_eval_c_005fif_005f37 = _jspx_th_c_005fif_005f37.doStartTag();
/* 4658 */               if (_jspx_eval_c_005fif_005f37 != 0) {
/*      */                 for (;;) {
/* 4660 */                   out.print(FormatUtil.getString("ms"));
/* 4661 */                   out.write(32);
/* 4662 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f37.doAfterBody();
/* 4663 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4667 */               if (_jspx_th_c_005fif_005f37.doEndTag() == 5) {
/* 4668 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f37); return;
/*      */               }
/*      */               
/* 4671 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f37);
/* 4672 */               out.write(" \n          </td>\n          <td class=\"whitegrayborder\">&nbsp; ");
/*      */               
/* 4674 */               IfTag _jspx_th_c_005fif_005f38 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4675 */               _jspx_th_c_005fif_005f38.setPageContext(_jspx_page_context);
/* 4676 */               _jspx_th_c_005fif_005f38.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4678 */               _jspx_th_c_005fif_005f38.setTest("${!empty requestScope.currentltbvalue}");
/* 4679 */               int _jspx_eval_c_005fif_005f38 = _jspx_th_c_005fif_005f38.doStartTag();
/* 4680 */               if (_jspx_eval_c_005fif_005f38 != 0) {
/*      */                 for (;;) {
/* 4682 */                   out.write("\n            <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4683 */                   out.print(resourceid);
/* 4684 */                   out.write("&attributeid=");
/* 4685 */                   out.print(lbtid);
/* 4686 */                   out.write("')\">");
/* 4687 */                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + lbtid)));
/* 4688 */                   out.write(" </a>\n            ");
/* 4689 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f38.doAfterBody();
/* 4690 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4694 */               if (_jspx_th_c_005fif_005f38.doEndTag() == 5) {
/* 4695 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f38); return;
/*      */               }
/*      */               
/* 4698 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f38);
/* 4699 */               out.write("\n          </td>\n        </tr>\n\n        <tr>\n          <td  colspan=\"4\" height=\"35\" class=\"yellowgrayborderbr\" align=\"right\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\"  title=\"\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4700 */               out.print(resourceid);
/* 4701 */               out.write("&attributeIDs=");
/* 4702 */               out.print(dnsid);
/* 4703 */               out.write(44);
/* 4704 */               out.print(connid);
/* 4705 */               out.write(44);
/* 4706 */               out.print(fbtid);
/* 4707 */               out.write(44);
/* 4708 */               out.print(lbtid);
/* 4709 */               out.write("&attributeToSelect=");
/* 4710 */               out.print(dnsid);
/* 4711 */               out.write("&redirectto=");
/* 4712 */               out.print(encodeurl);
/* 4713 */               out.write("\" class=\"staticlinks\">");
/* 4714 */               out.print(ALERTCONFIG_TEXT);
/* 4715 */               out.write("</a>\n          </td>\n        </tr>\n      </table>\n    </td>\n  <tr>\n  </tr>\n</table>\n<br /><br />\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t<tr>\n\t\t<td colspan=\"2\" width=\"72%\" height=\"26\" class=\"tableheadingbborder\">");
/* 4716 */               out.print(FormatUtil.getString("am.webclient.urlseq.responsetimeofindividualURL.text"));
/* 4717 */               out.write(" </td>\n\t</tr>\n\t<tr>\n\t\t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getIndividualURLandCompareReportsData&resourceid=");
/* 4718 */               if (_jspx_meth_c_005fout_005f31(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4720 */               out.write("&childid=");
/* 4721 */               out.print(s);
/* 4722 */               out.write("&attributeid=");
/* 4723 */               out.print(avgresponseTimeAttID);
/* 4724 */               out.write("&period=-7&resourcename=");
/* 4725 */               if (_jspx_meth_c_005fout_005f32(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4727 */               out.write("',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 4728 */               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4729 */               out.write("'></td>\n\t\t<td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getIndividualURLandCompareReportsData&resourceid=");
/* 4730 */               if (_jspx_meth_c_005fout_005f33(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4732 */               out.write("&childid=");
/* 4733 */               out.print(s);
/* 4734 */               out.write("&attributeid=");
/* 4735 */               out.print(avgresponseTimeAttID);
/* 4736 */               out.write("&period=-30&resourcename=");
/* 4737 */               if (_jspx_meth_c_005fout_005f34(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 4739 */               out.write("',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title='");
/* 4740 */               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4741 */               out.write("'></td>\n\t</tr>\n\t<tr>\n\t\t<td height=\"190\" align=\"center\" class=\"bodytext\" >\n\t\t");
/*      */               
/* 4743 */               TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 4744 */               _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 4745 */               _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 4747 */               _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("sumgraph");
/*      */               
/* 4749 */               _jspx_th_awolf_005ftimechart_005f0.setWidth("650");
/*      */               
/* 4751 */               _jspx_th_awolf_005ftimechart_005f0.setHeight(height_graph);
/*      */               
/* 4753 */               _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */               
/* 4755 */               _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/*      */               
/* 4757 */               _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.urlmonitor.responsewithunit.text"));
/*      */               
/* 4759 */               _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/* 4760 */               int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 4761 */               if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 4762 */                 if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 4763 */                   out = _jspx_page_context.pushBody();
/* 4764 */                   _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 4765 */                   _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 4768 */                   out.write(10);
/* 4769 */                   out.write(9);
/* 4770 */                   out.write(9);
/* 4771 */                   int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 4772 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 4775 */                 if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 4776 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 4779 */               if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 4780 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */               }
/*      */               
/* 4783 */               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 4784 */               out.write("\n\t\t</td>\n\t</tr>\n</table>\n<br>\n\n\n");
/* 4785 */               response.setContentType("text/html;charset=UTF-8");
/* 4786 */               out.write(10);
/* 4787 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 4788 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 4791 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4792 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 4795 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4796 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 4799 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 4800 */           out.write(10);
/* 4801 */           out.write(32);
/* 4802 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 4804 */           out.write(10);
/* 4805 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4806 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4810 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4811 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 4814 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4815 */         out.write(10);
/* 4816 */         out.write(10);
/*      */       }
/* 4818 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4819 */         out = _jspx_out;
/* 4820 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4821 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4822 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4825 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4831 */     PageContext pageContext = _jspx_page_context;
/* 4832 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4834 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4835 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4836 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4838 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4840 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=1");
/* 4841 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4842 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4843 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4844 */       return true;
/*      */     }
/* 4846 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4847 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4852 */     PageContext pageContext = _jspx_page_context;
/* 4853 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4855 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4856 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4857 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 4859 */     _jspx_th_c_005fout_005f0.setValue("${props.scriptname}");
/* 4860 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4861 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4862 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4863 */       return true;
/*      */     }
/* 4865 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4866 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4871 */     PageContext pageContext = _jspx_page_context;
/* 4872 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4874 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4875 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4876 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4878 */     _jspx_th_c_005fout_005f1.setValue("${param.seqid}");
/* 4879 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4880 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4881 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4882 */       return true;
/*      */     }
/* 4884 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4885 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4890 */     PageContext pageContext = _jspx_page_context;
/* 4891 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4893 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4894 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4895 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 4897 */     _jspx_th_c_005fout_005f2.setValue("${param.seqid} ");
/* 4898 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4899 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4900 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4901 */       return true;
/*      */     }
/* 4903 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4904 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4909 */     PageContext pageContext = _jspx_page_context;
/* 4910 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4912 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4913 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4914 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 4916 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 4917 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4918 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4919 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4920 */       return true;
/*      */     }
/* 4922 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4923 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4928 */     PageContext pageContext = _jspx_page_context;
/* 4929 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4931 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4932 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4933 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 4935 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 4936 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4937 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4938 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4939 */       return true;
/*      */     }
/* 4941 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4942 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4947 */     PageContext pageContext = _jspx_page_context;
/* 4948 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4950 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4951 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4952 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 4954 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 4955 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4956 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4957 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4958 */       return true;
/*      */     }
/* 4960 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4961 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4966 */     PageContext pageContext = _jspx_page_context;
/* 4967 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4969 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4970 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4971 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4973 */     _jspx_th_c_005fout_005f6.setValue("${ha.key}");
/* 4974 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4975 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4976 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4977 */       return true;
/*      */     }
/* 4979 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4980 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4985 */     PageContext pageContext = _jspx_page_context;
/* 4986 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4988 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4989 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4990 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4992 */     _jspx_th_c_005fout_005f7.setValue("${ha.value}");
/* 4993 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4994 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4995 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4996 */       return true;
/*      */     }
/* 4998 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4999 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5004 */     PageContext pageContext = _jspx_page_context;
/* 5005 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5007 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5008 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 5009 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5011 */     _jspx_th_c_005fset_005f0.setVar("monitorName");
/* 5012 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 5013 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 5014 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5015 */         out = _jspx_page_context.pushBody();
/* 5016 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 5017 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 5018 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5021 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fset_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5022 */           return true;
/* 5023 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 5024 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5027 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5028 */         out = _jspx_page_context.popBody();
/* 5029 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 5032 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5033 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 5034 */       return true;
/*      */     }
/* 5036 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 5037 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5042 */     PageContext pageContext = _jspx_page_context;
/* 5043 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5045 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5046 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5047 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5049 */     _jspx_th_c_005fout_005f8.setValue("${ha.value}");
/* 5050 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5051 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5052 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5053 */       return true;
/*      */     }
/* 5055 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5056 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5061 */     PageContext pageContext = _jspx_page_context;
/* 5062 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5064 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5065 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5066 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 5068 */     _jspx_th_c_005fout_005f9.setValue("${ha.key}");
/* 5069 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5070 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5071 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5072 */       return true;
/*      */     }
/* 5074 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5075 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5080 */     PageContext pageContext = _jspx_page_context;
/* 5081 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5083 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5084 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 5085 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 5087 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 5088 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 5089 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 5090 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5091 */       return true;
/*      */     }
/* 5093 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5094 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5099 */     PageContext pageContext = _jspx_page_context;
/* 5100 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5102 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5103 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 5104 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5106 */     _jspx_th_c_005fout_005f10.setValue("${ha.key}");
/* 5107 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 5108 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 5109 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5110 */       return true;
/*      */     }
/* 5112 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5113 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5118 */     PageContext pageContext = _jspx_page_context;
/* 5119 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5121 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5122 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 5123 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5125 */     _jspx_th_c_005fout_005f11.setValue("${ha.value}");
/* 5126 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 5127 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 5128 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5129 */       return true;
/*      */     }
/* 5131 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5132 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5137 */     PageContext pageContext = _jspx_page_context;
/* 5138 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5140 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5141 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 5142 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5144 */     _jspx_th_c_005fset_005f1.setVar("monitorName");
/* 5145 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 5146 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 5147 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5148 */         out = _jspx_page_context.pushBody();
/* 5149 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 5150 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 5151 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5154 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5155 */           return true;
/* 5156 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 5157 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5160 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 5161 */         out = _jspx_page_context.popBody();
/* 5162 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 5165 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 5166 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 5167 */       return true;
/*      */     }
/* 5169 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 5170 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5175 */     PageContext pageContext = _jspx_page_context;
/* 5176 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5178 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5179 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 5180 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 5182 */     _jspx_th_c_005fout_005f12.setValue("${ha.value}");
/* 5183 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 5184 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 5185 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5186 */       return true;
/*      */     }
/* 5188 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5189 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5194 */     PageContext pageContext = _jspx_page_context;
/* 5195 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5197 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5198 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 5199 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 5201 */     _jspx_th_c_005fout_005f13.setValue("${ha.key}");
/* 5202 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 5203 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 5204 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5205 */       return true;
/*      */     }
/* 5207 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5208 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5213 */     PageContext pageContext = _jspx_page_context;
/* 5214 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5216 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5217 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 5218 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 5220 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 5221 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 5222 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 5223 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5224 */       return true;
/*      */     }
/* 5226 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5227 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5232 */     PageContext pageContext = _jspx_page_context;
/* 5233 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5235 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5236 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 5237 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 5239 */     _jspx_th_c_005fif_005f9.setTest("${empty associatedmgs}");
/* 5240 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 5241 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 5243 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 5244 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 5245 */           return true;
/* 5246 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 5247 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 5248 */           return true;
/* 5249 */         out.write("</td>\n\t ");
/* 5250 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 5251 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5255 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 5256 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 5257 */       return true;
/*      */     }
/* 5259 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 5260 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5265 */     PageContext pageContext = _jspx_page_context;
/* 5266 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5268 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5269 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 5270 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 5272 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 5273 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 5274 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 5275 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5276 */       return true;
/*      */     }
/* 5278 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5279 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5284 */     PageContext pageContext = _jspx_page_context;
/* 5285 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5287 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5288 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 5289 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 5291 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.none.text");
/* 5292 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 5293 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 5294 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5295 */       return true;
/*      */     }
/* 5297 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5298 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5303 */     PageContext pageContext = _jspx_page_context;
/* 5304 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5306 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5307 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 5308 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 5310 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 5311 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 5312 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 5313 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5314 */       return true;
/*      */     }
/* 5316 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5317 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5322 */     PageContext pageContext = _jspx_page_context;
/* 5323 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5325 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 5326 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 5327 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 5329 */     _jspx_th_c_005fset_005f2.setVar("scriptpath");
/*      */     
/* 5331 */     _jspx_th_c_005fset_005f2.setValue("${selectedskin}");
/* 5332 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 5333 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 5334 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 5335 */       return true;
/*      */     }
/* 5337 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 5338 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5343 */     PageContext pageContext = _jspx_page_context;
/* 5344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5346 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 5347 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 5348 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5350 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 5351 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 5353 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 5354 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 5356 */           out.write("\n\t    ");
/* 5357 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 5358 */             return true;
/* 5359 */           out.write(10);
/* 5360 */           out.write(9);
/* 5361 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 5362 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5366 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 5367 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5370 */         int tmp191_190 = 0; int[] tmp191_188 = _jspx_push_body_count_c_005fcatch_005f0; int tmp193_192 = tmp191_188[tmp191_190];tmp191_188[tmp191_190] = (tmp193_192 - 1); if (tmp193_192 <= 0) break;
/* 5371 */         out = _jspx_page_context.popBody(); }
/* 5372 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 5374 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 5375 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 5377 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 5382 */     PageContext pageContext = _jspx_page_context;
/* 5383 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5385 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 5386 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 5387 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 5389 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 5391 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 5392 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 5393 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 5394 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 5395 */       return true;
/*      */     }
/* 5397 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 5398 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5403 */     PageContext pageContext = _jspx_page_context;
/* 5404 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5406 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5407 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 5408 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5410 */     _jspx_th_c_005fset_005f3.setVar("breadcrumbname");
/* 5411 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 5412 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 5413 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5414 */         out = _jspx_page_context.pushBody();
/* 5415 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 5416 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5419 */         if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 5420 */           return true;
/* 5421 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 5422 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5425 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5426 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5429 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 5430 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 5431 */       return true;
/*      */     }
/* 5433 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 5434 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5439 */     PageContext pageContext = _jspx_page_context;
/* 5440 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5442 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5443 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 5444 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 5446 */     _jspx_th_c_005fout_005f14.setValue("${props.name}");
/* 5447 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 5448 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 5449 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5450 */       return true;
/*      */     }
/* 5452 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5453 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5458 */     PageContext pageContext = _jspx_page_context;
/* 5459 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5461 */     PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5462 */     _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 5463 */     _jspx_th_logic_005fpresent_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5465 */     _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 5466 */     int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 5467 */     if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */       for (;;) {
/* 5469 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 5470 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 5471 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5475 */     if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 5476 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 5477 */       return true;
/*      */     }
/* 5479 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 5480 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5485 */     PageContext pageContext = _jspx_page_context;
/* 5486 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5488 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5489 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 5490 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5492 */     _jspx_th_c_005fout_005f15.setValue("${props.resourcename}");
/* 5493 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 5494 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 5495 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5496 */       return true;
/*      */     }
/* 5498 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5499 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5504 */     PageContext pageContext = _jspx_page_context;
/* 5505 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5507 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5508 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 5509 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5511 */     _jspx_th_c_005fout_005f16.setValue("${props.name}");
/* 5512 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 5513 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 5514 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5515 */       return true;
/*      */     }
/* 5517 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5518 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5523 */     PageContext pageContext = _jspx_page_context;
/* 5524 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5526 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5527 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 5528 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5530 */     _jspx_th_c_005fout_005f17.setValue("${props.pollInterval}");
/* 5531 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 5532 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 5533 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5534 */       return true;
/*      */     }
/* 5536 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5537 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5542 */     PageContext pageContext = _jspx_page_context;
/* 5543 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5545 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5546 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 5547 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5549 */     _jspx_th_c_005fout_005f18.setValue("${props.name}");
/* 5550 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 5551 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 5552 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5553 */       return true;
/*      */     }
/* 5555 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5556 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5561 */     PageContext pageContext = _jspx_page_context;
/* 5562 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5564 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5565 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 5566 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5568 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 5569 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 5570 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 5571 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5572 */       return true;
/*      */     }
/* 5574 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5575 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5580 */     PageContext pageContext = _jspx_page_context;
/* 5581 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5583 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5584 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 5585 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5587 */     _jspx_th_c_005fout_005f20.setValue("${props.pollInterval}");
/* 5588 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 5589 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 5590 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5591 */       return true;
/*      */     }
/* 5593 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5594 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5599 */     PageContext pageContext = _jspx_page_context;
/* 5600 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5602 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5603 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 5604 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5606 */     _jspx_th_c_005fout_005f21.setValue("${props.agentname}");
/* 5607 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 5608 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 5609 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5610 */       return true;
/*      */     }
/* 5612 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5613 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5618 */     PageContext pageContext = _jspx_page_context;
/* 5619 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5621 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 5622 */     _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 5623 */     _jspx_th_logic_005fnotPresent_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5625 */     _jspx_th_logic_005fnotPresent_005f3.setRole("OPERATOR");
/* 5626 */     int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 5627 */     if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */       for (;;) {
/* 5629 */         out.write(10);
/* 5630 */         out.write(9);
/* 5631 */         out.write(9);
/* 5632 */         if (_jspx_meth_logic_005fnotPresent_005f4(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/* 5633 */           return true;
/* 5634 */         out.write(10);
/* 5635 */         out.write(9);
/* 5636 */         out.write(9);
/* 5637 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 5638 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5642 */     if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 5643 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 5644 */       return true;
/*      */     }
/* 5646 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 5647 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f4(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5652 */     PageContext pageContext = _jspx_page_context;
/* 5653 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5655 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 5656 */     _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 5657 */     _jspx_th_logic_005fnotPresent_005f4.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5659 */     _jspx_th_logic_005fnotPresent_005f4.setRole("ENTERPRISEADMIN");
/* 5660 */     int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 5661 */     if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */       for (;;) {
/* 5663 */         out.write("\n\t\t</td>\n\t\t</tr>\n\t\t");
/* 5664 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 5665 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5669 */     if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 5670 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 5671 */       return true;
/*      */     }
/* 5673 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 5674 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5679 */     PageContext pageContext = _jspx_page_context;
/* 5680 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5682 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5683 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 5684 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5686 */     _jspx_th_c_005fout_005f22.setValue("${props.scriptname}");
/* 5687 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 5688 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 5689 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5690 */       return true;
/*      */     }
/* 5692 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5693 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5698 */     PageContext pageContext = _jspx_page_context;
/* 5699 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5701 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 5702 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 5703 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5705 */     _jspx_th_c_005fout_005f23.setValue("${props.collectiontime}");
/*      */     
/* 5707 */     _jspx_th_c_005fout_005f23.setDefault("-");
/* 5708 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 5709 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 5710 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5711 */       return true;
/*      */     }
/* 5713 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5714 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5719 */     PageContext pageContext = _jspx_page_context;
/* 5720 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5722 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 5723 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 5724 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5726 */     _jspx_th_c_005fout_005f24.setValue("${props.nextcollectiontime}");
/*      */     
/* 5728 */     _jspx_th_c_005fout_005f24.setDefault("-");
/* 5729 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 5730 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 5731 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5732 */       return true;
/*      */     }
/* 5734 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5735 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5740 */     PageContext pageContext = _jspx_page_context;
/* 5741 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5743 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5744 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 5745 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5747 */     _jspx_th_c_005fout_005f25.setValue("${param.resourceid}");
/* 5748 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 5749 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 5750 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5751 */       return true;
/*      */     }
/* 5753 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5754 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5759 */     PageContext pageContext = _jspx_page_context;
/* 5760 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5762 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5763 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 5764 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5766 */     _jspx_th_c_005fout_005f26.setValue("${param.resourceid}");
/* 5767 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 5768 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 5769 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5770 */       return true;
/*      */     }
/* 5772 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5773 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5778 */     PageContext pageContext = _jspx_page_context;
/* 5779 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5781 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 5782 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 5783 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5785 */     _jspx_th_c_005fset_005f4.setVar("parentname");
/*      */     
/* 5787 */     _jspx_th_c_005fset_005f4.setScope("request");
/*      */     
/* 5789 */     _jspx_th_c_005fset_005f4.setValue("${props.name}");
/* 5790 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 5791 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 5792 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/* 5793 */       return true;
/*      */     }
/* 5795 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/* 5796 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5801 */     PageContext pageContext = _jspx_page_context;
/* 5802 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5804 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 5805 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 5806 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5808 */     _jspx_th_c_005fset_005f5.setVar("parentname");
/*      */     
/* 5810 */     _jspx_th_c_005fset_005f5.setScope("request");
/*      */     
/* 5812 */     _jspx_th_c_005fset_005f5.setValue("${props.name}");
/* 5813 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 5814 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 5815 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 5816 */       return true;
/*      */     }
/* 5818 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 5819 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5824 */     PageContext pageContext = _jspx_page_context;
/* 5825 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5827 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5828 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 5829 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5831 */     _jspx_th_c_005fout_005f27.setValue("${param.resourceid}");
/* 5832 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 5833 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 5834 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5835 */       return true;
/*      */     }
/* 5837 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5838 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5843 */     PageContext pageContext = _jspx_page_context;
/* 5844 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5846 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5847 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 5848 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5850 */     _jspx_th_c_005fout_005f28.setValue("${param.resourcename}");
/* 5851 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 5852 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 5853 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5854 */       return true;
/*      */     }
/* 5856 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5857 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5862 */     PageContext pageContext = _jspx_page_context;
/* 5863 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5865 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5866 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 5867 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5869 */     _jspx_th_c_005fout_005f29.setValue("${param.resourceid}");
/* 5870 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 5871 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 5872 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5873 */       return true;
/*      */     }
/* 5875 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5876 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5881 */     PageContext pageContext = _jspx_page_context;
/* 5882 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5884 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5885 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 5886 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 5888 */     _jspx_th_c_005fout_005f30.setValue("${param.resourcename}");
/* 5889 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 5890 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 5891 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5892 */       return true;
/*      */     }
/* 5894 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5895 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5900 */     PageContext pageContext = _jspx_page_context;
/* 5901 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5903 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5904 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 5905 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 5906 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 5907 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 5908 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 5909 */         out = _jspx_page_context.pushBody();
/* 5910 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 5911 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5914 */         out.write("table.heading.attribute");
/* 5915 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 5916 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5919 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 5920 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5923 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 5924 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 5925 */       return true;
/*      */     }
/* 5927 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 5928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5933 */     PageContext pageContext = _jspx_page_context;
/* 5934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5936 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5937 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 5938 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 5939 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 5940 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 5941 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 5942 */         out = _jspx_page_context.pushBody();
/* 5943 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 5944 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5947 */         out.write("table.heading.value");
/* 5948 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 5949 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5952 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 5953 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5956 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 5957 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 5958 */       return true;
/*      */     }
/* 5960 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 5961 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5966 */     PageContext pageContext = _jspx_page_context;
/* 5967 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5969 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5970 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 5971 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 5972 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 5973 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 5974 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 5975 */         out = _jspx_page_context.pushBody();
/* 5976 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 5977 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5980 */         out.write("table.heading.status");
/* 5981 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 5982 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5985 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 5986 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5989 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 5990 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 5991 */       return true;
/*      */     }
/* 5993 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 5994 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5999 */     PageContext pageContext = _jspx_page_context;
/* 6000 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6002 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6003 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 6004 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6006 */     _jspx_th_c_005fif_005f12.setTest("${requestScope.avgresponsetime!=-1}");
/* 6007 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 6008 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 6010 */         out.write(32);
/* 6011 */         out.write(32);
/* 6012 */         if (_jspx_meth_fmt_005fformatNumber_005f0(_jspx_th_c_005fif_005f12, _jspx_page_context))
/* 6013 */           return true;
/* 6014 */         out.write(32);
/* 6015 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 6016 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6020 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 6021 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 6022 */       return true;
/*      */     }
/* 6024 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 6025 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f0(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6030 */     PageContext pageContext = _jspx_page_context;
/* 6031 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6033 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f0 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.get(FormatNumberTag.class);
/* 6034 */     _jspx_th_fmt_005fformatNumber_005f0.setPageContext(_jspx_page_context);
/* 6035 */     _jspx_th_fmt_005fformatNumber_005f0.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 6037 */     _jspx_th_fmt_005fformatNumber_005f0.setValue("${requestScope.avgresponsetime}");
/*      */     
/* 6039 */     _jspx_th_fmt_005fformatNumber_005f0.setMaxFractionDigits("0");
/* 6040 */     int _jspx_eval_fmt_005fformatNumber_005f0 = _jspx_th_fmt_005fformatNumber_005f0.doStartTag();
/* 6041 */     if (_jspx_th_fmt_005fformatNumber_005f0.doEndTag() == 5) {
/* 6042 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 6043 */       return true;
/*      */     }
/* 6045 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 6046 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6051 */     PageContext pageContext = _jspx_page_context;
/* 6052 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6054 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6055 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 6056 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6058 */     _jspx_th_c_005fif_005f13.setTest("${empty requestScope.avgresponsetime}");
/* 6059 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 6060 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 6062 */         out.write(32);
/* 6063 */         out.write(45);
/* 6064 */         out.write(32);
/* 6065 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 6066 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6070 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 6071 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 6072 */       return true;
/*      */     }
/* 6074 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 6075 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6080 */     PageContext pageContext = _jspx_page_context;
/* 6081 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6083 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6084 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 6085 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6087 */     _jspx_th_c_005fif_005f15.setTest("${requestScope.currentvalue!=-1}");
/* 6088 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 6089 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 6091 */         out.write(32);
/* 6092 */         out.write(32);
/* 6093 */         if (_jspx_meth_fmt_005fformatNumber_005f1(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 6094 */           return true;
/* 6095 */         out.write(32);
/* 6096 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 6097 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6101 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 6102 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 6103 */       return true;
/*      */     }
/* 6105 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 6106 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f1(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6111 */     PageContext pageContext = _jspx_page_context;
/* 6112 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6114 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f1 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.get(FormatNumberTag.class);
/* 6115 */     _jspx_th_fmt_005fformatNumber_005f1.setPageContext(_jspx_page_context);
/* 6116 */     _jspx_th_fmt_005fformatNumber_005f1.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 6118 */     _jspx_th_fmt_005fformatNumber_005f1.setValue("${requestScope.currentvalue}");
/*      */     
/* 6120 */     _jspx_th_fmt_005fformatNumber_005f1.setMaxFractionDigits("0");
/* 6121 */     int _jspx_eval_fmt_005fformatNumber_005f1 = _jspx_th_fmt_005fformatNumber_005f1.doStartTag();
/* 6122 */     if (_jspx_th_fmt_005fformatNumber_005f1.doEndTag() == 5) {
/* 6123 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 6124 */       return true;
/*      */     }
/* 6126 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 6127 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6132 */     PageContext pageContext = _jspx_page_context;
/* 6133 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6135 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6136 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 6137 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6139 */     _jspx_th_c_005fif_005f16.setTest("${empty requestScope.currentvalue}");
/* 6140 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 6141 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 6143 */         out.write(32);
/* 6144 */         out.write(45);
/* 6145 */         out.write(32);
/* 6146 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 6147 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6151 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 6152 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 6153 */       return true;
/*      */     }
/* 6155 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 6156 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6161 */     PageContext pageContext = _jspx_page_context;
/* 6162 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6164 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6165 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 6166 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 6167 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 6168 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 6169 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6170 */         out = _jspx_page_context.pushBody();
/* 6171 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 6172 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6175 */         out.write("table.heading.attribute");
/* 6176 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 6177 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6180 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6181 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6184 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 6185 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6186 */       return true;
/*      */     }
/* 6188 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6189 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6194 */     PageContext pageContext = _jspx_page_context;
/* 6195 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6197 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6198 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 6199 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 6200 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 6201 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 6202 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6203 */         out = _jspx_page_context.pushBody();
/* 6204 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 6205 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6208 */         out.write("table.heading.avg.value");
/* 6209 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 6210 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6213 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6214 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6217 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 6218 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6219 */       return true;
/*      */     }
/* 6221 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6222 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6227 */     PageContext pageContext = _jspx_page_context;
/* 6228 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6230 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6231 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 6232 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 6233 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 6234 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 6235 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6236 */         out = _jspx_page_context.pushBody();
/* 6237 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 6238 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6241 */         out.write("table.heading.current.value");
/* 6242 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 6243 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6246 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6247 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6250 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 6251 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6252 */       return true;
/*      */     }
/* 6254 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6255 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6260 */     PageContext pageContext = _jspx_page_context;
/* 6261 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6263 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6264 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 6265 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 6266 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 6267 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 6268 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 6269 */         out = _jspx_page_context.pushBody();
/* 6270 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 6271 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6274 */         out.write("table.heading.status");
/* 6275 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 6276 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6279 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 6280 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6283 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 6284 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6285 */       return true;
/*      */     }
/* 6287 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6288 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6293 */     PageContext pageContext = _jspx_page_context;
/* 6294 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6296 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f2 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.get(FormatNumberTag.class);
/* 6297 */     _jspx_th_fmt_005fformatNumber_005f2.setPageContext(_jspx_page_context);
/* 6298 */     _jspx_th_fmt_005fformatNumber_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6300 */     _jspx_th_fmt_005fformatNumber_005f2.setValue("${requestScope.avgdnstime}");
/*      */     
/* 6302 */     _jspx_th_fmt_005fformatNumber_005f2.setMaxFractionDigits("0");
/* 6303 */     int _jspx_eval_fmt_005fformatNumber_005f2 = _jspx_th_fmt_005fformatNumber_005f2.doStartTag();
/* 6304 */     if (_jspx_th_fmt_005fformatNumber_005f2.doEndTag() == 5) {
/* 6305 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f2);
/* 6306 */       return true;
/*      */     }
/* 6308 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f2);
/* 6309 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f19(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6314 */     PageContext pageContext = _jspx_page_context;
/* 6315 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6317 */     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6318 */     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 6319 */     _jspx_th_c_005fif_005f19.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6321 */     _jspx_th_c_005fif_005f19.setTest("${empty requestScope.avgdnstime}");
/* 6322 */     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 6323 */     if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */       for (;;) {
/* 6325 */         out.write(32);
/* 6326 */         out.write(45);
/* 6327 */         out.write(32);
/* 6328 */         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 6329 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6333 */     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 6334 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 6335 */       return true;
/*      */     }
/* 6337 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 6338 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6343 */     PageContext pageContext = _jspx_page_context;
/* 6344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6346 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f3 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.get(FormatNumberTag.class);
/* 6347 */     _jspx_th_fmt_005fformatNumber_005f3.setPageContext(_jspx_page_context);
/* 6348 */     _jspx_th_fmt_005fformatNumber_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6350 */     _jspx_th_fmt_005fformatNumber_005f3.setValue("${requestScope.currentdnsvalue}");
/*      */     
/* 6352 */     _jspx_th_fmt_005fformatNumber_005f3.setMaxFractionDigits("0");
/* 6353 */     int _jspx_eval_fmt_005fformatNumber_005f3 = _jspx_th_fmt_005fformatNumber_005f3.doStartTag();
/* 6354 */     if (_jspx_th_fmt_005fformatNumber_005f3.doEndTag() == 5) {
/* 6355 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f3);
/* 6356 */       return true;
/*      */     }
/* 6358 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f3);
/* 6359 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f21(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6364 */     PageContext pageContext = _jspx_page_context;
/* 6365 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6367 */     IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6368 */     _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 6369 */     _jspx_th_c_005fif_005f21.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6371 */     _jspx_th_c_005fif_005f21.setTest("${empty requestScope.currentdnsvalue}");
/* 6372 */     int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 6373 */     if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */       for (;;) {
/* 6375 */         out.write(32);
/* 6376 */         out.write(45);
/* 6377 */         out.write(32);
/* 6378 */         int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 6379 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6383 */     if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 6384 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 6385 */       return true;
/*      */     }
/* 6387 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 6388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6393 */     PageContext pageContext = _jspx_page_context;
/* 6394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6396 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f4 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.get(FormatNumberTag.class);
/* 6397 */     _jspx_th_fmt_005fformatNumber_005f4.setPageContext(_jspx_page_context);
/* 6398 */     _jspx_th_fmt_005fformatNumber_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6400 */     _jspx_th_fmt_005fformatNumber_005f4.setValue("${requestScope.avgconntime}");
/*      */     
/* 6402 */     _jspx_th_fmt_005fformatNumber_005f4.setMaxFractionDigits("0");
/* 6403 */     int _jspx_eval_fmt_005fformatNumber_005f4 = _jspx_th_fmt_005fformatNumber_005f4.doStartTag();
/* 6404 */     if (_jspx_th_fmt_005fformatNumber_005f4.doEndTag() == 5) {
/* 6405 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f4);
/* 6406 */       return true;
/*      */     }
/* 6408 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f4);
/* 6409 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f24(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6414 */     PageContext pageContext = _jspx_page_context;
/* 6415 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6417 */     IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6418 */     _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 6419 */     _jspx_th_c_005fif_005f24.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6421 */     _jspx_th_c_005fif_005f24.setTest("${empty requestScope.avgconntime}");
/* 6422 */     int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 6423 */     if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */       for (;;) {
/* 6425 */         out.write(32);
/* 6426 */         out.write(45);
/* 6427 */         out.write(32);
/* 6428 */         int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 6429 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6433 */     if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 6434 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 6435 */       return true;
/*      */     }
/* 6437 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 6438 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6443 */     PageContext pageContext = _jspx_page_context;
/* 6444 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6446 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f5 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.get(FormatNumberTag.class);
/* 6447 */     _jspx_th_fmt_005fformatNumber_005f5.setPageContext(_jspx_page_context);
/* 6448 */     _jspx_th_fmt_005fformatNumber_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6450 */     _jspx_th_fmt_005fformatNumber_005f5.setValue("${requestScope.currentconnvalue}");
/*      */     
/* 6452 */     _jspx_th_fmt_005fformatNumber_005f5.setMaxFractionDigits("0");
/* 6453 */     int _jspx_eval_fmt_005fformatNumber_005f5 = _jspx_th_fmt_005fformatNumber_005f5.doStartTag();
/* 6454 */     if (_jspx_th_fmt_005fformatNumber_005f5.doEndTag() == 5) {
/* 6455 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f5);
/* 6456 */       return true;
/*      */     }
/* 6458 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f5);
/* 6459 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f26(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6464 */     PageContext pageContext = _jspx_page_context;
/* 6465 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6467 */     IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6468 */     _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/* 6469 */     _jspx_th_c_005fif_005f26.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6471 */     _jspx_th_c_005fif_005f26.setTest("${empty requestScope.currentconnvalue}");
/* 6472 */     int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/* 6473 */     if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */       for (;;) {
/* 6475 */         out.write(32);
/* 6476 */         out.write(45);
/* 6477 */         out.write(32);
/* 6478 */         int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/* 6479 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6483 */     if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/* 6484 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 6485 */       return true;
/*      */     }
/* 6487 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 6488 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f6(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6493 */     PageContext pageContext = _jspx_page_context;
/* 6494 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6496 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f6 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.get(FormatNumberTag.class);
/* 6497 */     _jspx_th_fmt_005fformatNumber_005f6.setPageContext(_jspx_page_context);
/* 6498 */     _jspx_th_fmt_005fformatNumber_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6500 */     _jspx_th_fmt_005fformatNumber_005f6.setValue("${requestScope.avgfbt}");
/*      */     
/* 6502 */     _jspx_th_fmt_005fformatNumber_005f6.setMaxFractionDigits("0");
/* 6503 */     int _jspx_eval_fmt_005fformatNumber_005f6 = _jspx_th_fmt_005fformatNumber_005f6.doStartTag();
/* 6504 */     if (_jspx_th_fmt_005fformatNumber_005f6.doEndTag() == 5) {
/* 6505 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f6);
/* 6506 */       return true;
/*      */     }
/* 6508 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f6);
/* 6509 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f29(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6514 */     PageContext pageContext = _jspx_page_context;
/* 6515 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6517 */     IfTag _jspx_th_c_005fif_005f29 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6518 */     _jspx_th_c_005fif_005f29.setPageContext(_jspx_page_context);
/* 6519 */     _jspx_th_c_005fif_005f29.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6521 */     _jspx_th_c_005fif_005f29.setTest("${empty requestScope.avgfbt}");
/* 6522 */     int _jspx_eval_c_005fif_005f29 = _jspx_th_c_005fif_005f29.doStartTag();
/* 6523 */     if (_jspx_eval_c_005fif_005f29 != 0) {
/*      */       for (;;) {
/* 6525 */         out.write(32);
/* 6526 */         out.write(45);
/* 6527 */         out.write(32);
/* 6528 */         int evalDoAfterBody = _jspx_th_c_005fif_005f29.doAfterBody();
/* 6529 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6533 */     if (_jspx_th_c_005fif_005f29.doEndTag() == 5) {
/* 6534 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 6535 */       return true;
/*      */     }
/* 6537 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 6538 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6543 */     PageContext pageContext = _jspx_page_context;
/* 6544 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6546 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f7 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.get(FormatNumberTag.class);
/* 6547 */     _jspx_th_fmt_005fformatNumber_005f7.setPageContext(_jspx_page_context);
/* 6548 */     _jspx_th_fmt_005fformatNumber_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6550 */     _jspx_th_fmt_005fformatNumber_005f7.setValue("${requestScope.currentfbtvalue}");
/*      */     
/* 6552 */     _jspx_th_fmt_005fformatNumber_005f7.setMaxFractionDigits("0");
/* 6553 */     int _jspx_eval_fmt_005fformatNumber_005f7 = _jspx_th_fmt_005fformatNumber_005f7.doStartTag();
/* 6554 */     if (_jspx_th_fmt_005fformatNumber_005f7.doEndTag() == 5) {
/* 6555 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f7);
/* 6556 */       return true;
/*      */     }
/* 6558 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f7);
/* 6559 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f31(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6564 */     PageContext pageContext = _jspx_page_context;
/* 6565 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6567 */     IfTag _jspx_th_c_005fif_005f31 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6568 */     _jspx_th_c_005fif_005f31.setPageContext(_jspx_page_context);
/* 6569 */     _jspx_th_c_005fif_005f31.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6571 */     _jspx_th_c_005fif_005f31.setTest("${empty requestScope.currentfbtvalue}");
/* 6572 */     int _jspx_eval_c_005fif_005f31 = _jspx_th_c_005fif_005f31.doStartTag();
/* 6573 */     if (_jspx_eval_c_005fif_005f31 != 0) {
/*      */       for (;;) {
/* 6575 */         out.write(32);
/* 6576 */         out.write(45);
/* 6577 */         out.write(32);
/* 6578 */         int evalDoAfterBody = _jspx_th_c_005fif_005f31.doAfterBody();
/* 6579 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6583 */     if (_jspx_th_c_005fif_005f31.doEndTag() == 5) {
/* 6584 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 6585 */       return true;
/*      */     }
/* 6587 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 6588 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f8(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6593 */     PageContext pageContext = _jspx_page_context;
/* 6594 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6596 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f8 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.get(FormatNumberTag.class);
/* 6597 */     _jspx_th_fmt_005fformatNumber_005f8.setPageContext(_jspx_page_context);
/* 6598 */     _jspx_th_fmt_005fformatNumber_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6600 */     _jspx_th_fmt_005fformatNumber_005f8.setValue("${requestScope.avglbt}");
/*      */     
/* 6602 */     _jspx_th_fmt_005fformatNumber_005f8.setMaxFractionDigits("0");
/* 6603 */     int _jspx_eval_fmt_005fformatNumber_005f8 = _jspx_th_fmt_005fformatNumber_005f8.doStartTag();
/* 6604 */     if (_jspx_th_fmt_005fformatNumber_005f8.doEndTag() == 5) {
/* 6605 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f8);
/* 6606 */       return true;
/*      */     }
/* 6608 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f8);
/* 6609 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f34(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6614 */     PageContext pageContext = _jspx_page_context;
/* 6615 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6617 */     IfTag _jspx_th_c_005fif_005f34 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6618 */     _jspx_th_c_005fif_005f34.setPageContext(_jspx_page_context);
/* 6619 */     _jspx_th_c_005fif_005f34.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6621 */     _jspx_th_c_005fif_005f34.setTest("${empty requestScope.avglbt}");
/* 6622 */     int _jspx_eval_c_005fif_005f34 = _jspx_th_c_005fif_005f34.doStartTag();
/* 6623 */     if (_jspx_eval_c_005fif_005f34 != 0) {
/*      */       for (;;) {
/* 6625 */         out.write(32);
/* 6626 */         out.write(45);
/* 6627 */         out.write(32);
/* 6628 */         int evalDoAfterBody = _jspx_th_c_005fif_005f34.doAfterBody();
/* 6629 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6633 */     if (_jspx_th_c_005fif_005f34.doEndTag() == 5) {
/* 6634 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34);
/* 6635 */       return true;
/*      */     }
/* 6637 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34);
/* 6638 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f9(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6643 */     PageContext pageContext = _jspx_page_context;
/* 6644 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6646 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f9 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.get(FormatNumberTag.class);
/* 6647 */     _jspx_th_fmt_005fformatNumber_005f9.setPageContext(_jspx_page_context);
/* 6648 */     _jspx_th_fmt_005fformatNumber_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6650 */     _jspx_th_fmt_005fformatNumber_005f9.setValue("${requestScope.currentltbvalue}");
/*      */     
/* 6652 */     _jspx_th_fmt_005fformatNumber_005f9.setMaxFractionDigits("0");
/* 6653 */     int _jspx_eval_fmt_005fformatNumber_005f9 = _jspx_th_fmt_005fformatNumber_005f9.doStartTag();
/* 6654 */     if (_jspx_th_fmt_005fformatNumber_005f9.doEndTag() == 5) {
/* 6655 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f9);
/* 6656 */       return true;
/*      */     }
/* 6658 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fmaxFractionDigits_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f9);
/* 6659 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f36(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6664 */     PageContext pageContext = _jspx_page_context;
/* 6665 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6667 */     IfTag _jspx_th_c_005fif_005f36 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6668 */     _jspx_th_c_005fif_005f36.setPageContext(_jspx_page_context);
/* 6669 */     _jspx_th_c_005fif_005f36.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6671 */     _jspx_th_c_005fif_005f36.setTest("${empty requestScope.currentltbvalue}");
/* 6672 */     int _jspx_eval_c_005fif_005f36 = _jspx_th_c_005fif_005f36.doStartTag();
/* 6673 */     if (_jspx_eval_c_005fif_005f36 != 0) {
/*      */       for (;;) {
/* 6675 */         out.write(32);
/* 6676 */         out.write(45);
/* 6677 */         out.write(32);
/* 6678 */         int evalDoAfterBody = _jspx_th_c_005fif_005f36.doAfterBody();
/* 6679 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6683 */     if (_jspx_th_c_005fif_005f36.doEndTag() == 5) {
/* 6684 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f36);
/* 6685 */       return true;
/*      */     }
/* 6687 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f36);
/* 6688 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6693 */     PageContext pageContext = _jspx_page_context;
/* 6694 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6696 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6697 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 6698 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6700 */     _jspx_th_c_005fout_005f31.setValue("${param.resourceid}");
/* 6701 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 6702 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 6703 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 6704 */       return true;
/*      */     }
/* 6706 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 6707 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6712 */     PageContext pageContext = _jspx_page_context;
/* 6713 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6715 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6716 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 6717 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6719 */     _jspx_th_c_005fout_005f32.setValue("${param.resourcename}");
/* 6720 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 6721 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 6722 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 6723 */       return true;
/*      */     }
/* 6725 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 6726 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6731 */     PageContext pageContext = _jspx_page_context;
/* 6732 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6734 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6735 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 6736 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6738 */     _jspx_th_c_005fout_005f33.setValue("${param.resourceid}");
/* 6739 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 6740 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 6741 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 6742 */       return true;
/*      */     }
/* 6744 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 6745 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6750 */     PageContext pageContext = _jspx_page_context;
/* 6751 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6753 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6754 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 6755 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6757 */     _jspx_th_c_005fout_005f34.setValue("${param.resourcename}");
/* 6758 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 6759 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 6760 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 6761 */       return true;
/*      */     }
/* 6763 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 6764 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6769 */     PageContext pageContext = _jspx_page_context;
/* 6770 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6772 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6773 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 6774 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6776 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 6778 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 6779 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 6780 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 6781 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 6782 */       return true;
/*      */     }
/* 6784 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 6785 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\urlseqdetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */