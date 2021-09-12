/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.CpuGraph;
/*      */ import com.adventnet.appmanager.bean.MemGraph;
/*      */ import com.adventnet.appmanager.bean.PerformanceBean;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.StackBarChart;
/*      */ import com.adventnet.awolf.tags.TimeChart;
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
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatNumberTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class HostResourceToSend_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  727 */     java.net.InetAddress add = null;
/*  728 */     if (ip.equals("127.0.0.1")) {
/*  729 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  733 */       add = java.net.InetAddress.getByName(ip);
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
/* 1389 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1434 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/* 1715 */           String attributeName = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
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
/* 2156 */           String message = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2157 */           String image = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
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
/* 2192 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2193 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbarcolors;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2223 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbarcolors = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2250 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2255 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2256 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2257 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2258 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2259 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2260 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2261 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2262 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2263 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2264 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2265 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2266 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2267 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2268 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2269 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2270 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2271 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2272 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2273 */     this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbarcolors.release();
/* 2274 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2275 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2282 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2285 */     JspWriter out = null;
/* 2286 */     Object page = this;
/* 2287 */     JspWriter _jspx_out = null;
/* 2288 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2292 */       response.setContentType("text/html;charset=UTF-8");
/* 2293 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2295 */       _jspx_page_context = pageContext;
/* 2296 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2297 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2298 */       session = pageContext.getSession();
/* 2299 */       out = pageContext.getOut();
/* 2300 */       _jspx_out = out;
/*      */       
/* 2302 */       out.write(" <!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2303 */       CpuGraph cpugraph = null;
/* 2304 */       cpugraph = (CpuGraph)_jspx_page_context.getAttribute("cpugraph", 1);
/* 2305 */       if (cpugraph == null) {
/* 2306 */         cpugraph = new CpuGraph();
/* 2307 */         _jspx_page_context.setAttribute("cpugraph", cpugraph, 1);
/*      */       }
/* 2309 */       out.write(10);
/* 2310 */       MemGraph memgraph = null;
/* 2311 */       memgraph = (MemGraph)_jspx_page_context.getAttribute("memgraph", 1);
/* 2312 */       if (memgraph == null) {
/* 2313 */         memgraph = new MemGraph();
/* 2314 */         _jspx_page_context.setAttribute("memgraph", memgraph, 1);
/*      */       }
/* 2316 */       out.write(10);
/* 2317 */       com.adventnet.appmanager.bean.SysloadGraph sysgraph = null;
/* 2318 */       sysgraph = (com.adventnet.appmanager.bean.SysloadGraph)_jspx_page_context.getAttribute("sysgraph", 1);
/* 2319 */       if (sysgraph == null) {
/* 2320 */         sysgraph = new com.adventnet.appmanager.bean.SysloadGraph();
/* 2321 */         _jspx_page_context.setAttribute("sysgraph", sysgraph, 1);
/*      */       }
/* 2323 */       out.write(10);
/* 2324 */       com.adventnet.appmanager.bean.HostResourceBean hrbean = null;
/* 2325 */       hrbean = (com.adventnet.appmanager.bean.HostResourceBean)_jspx_page_context.getAttribute("hrbean", 1);
/* 2326 */       if (hrbean == null) {
/* 2327 */         hrbean = new com.adventnet.appmanager.bean.HostResourceBean();
/* 2328 */         _jspx_page_context.setAttribute("hrbean", hrbean, 1);
/*      */       }
/* 2330 */       out.write(10);
/* 2331 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2332 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2333 */       if (wlsGraph == null) {
/* 2334 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2335 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2337 */       out.write(10);
/* 2338 */       com.adventnet.appmanager.bean.DiskSpaceGraph diskgraph = null;
/* 2339 */       diskgraph = (com.adventnet.appmanager.bean.DiskSpaceGraph)_jspx_page_context.getAttribute("diskgraph", 2);
/* 2340 */       if (diskgraph == null) {
/* 2341 */         diskgraph = new com.adventnet.appmanager.bean.DiskSpaceGraph();
/* 2342 */         _jspx_page_context.setAttribute("diskgraph", diskgraph, 2);
/*      */       }
/* 2344 */       out.write(10);
/* 2345 */       PerformanceBean perfgraph = null;
/* 2346 */       perfgraph = (PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2347 */       if (perfgraph == null) {
/* 2348 */         perfgraph = new PerformanceBean();
/* 2349 */         _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */       }
/* 2351 */       out.write(10);
/* 2352 */       Hashtable availabilitykeys = null;
/* 2353 */       synchronized (application) {
/* 2354 */         availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2355 */         if (availabilitykeys == null) {
/* 2356 */           availabilitykeys = new Hashtable();
/* 2357 */           _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */         }
/*      */       }
/* 2360 */       out.write(10);
/* 2361 */       Hashtable healthkeys = null;
/* 2362 */       synchronized (application) {
/* 2363 */         healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2364 */         if (healthkeys == null) {
/* 2365 */           healthkeys = new Hashtable();
/* 2366 */           _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */         }
/*      */       }
/* 2369 */       out.write(10);
/* 2370 */       Hashtable motypedisplaynames = null;
/* 2371 */       synchronized (application) {
/* 2372 */         motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2373 */         if (motypedisplaynames == null) {
/* 2374 */           motypedisplaynames = new Hashtable();
/* 2375 */           _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */         }
/*      */       }
/* 2378 */       out.write("\n\n\n\n\n\n\n\n\n  \n");
/*      */       
/*      */ 
/* 2381 */       Properties alert = null;
/*      */       
/* 2383 */       out.write(10);
/* 2384 */       if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */         return;
/* 2386 */       out.write("\n\n<html>\n<body marginheight=0 marginwidth=0 leftmargin=0 topmargin=0>\n");
/*      */       
/*      */ 
/* 2389 */       String Colur = null;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2395 */       String haid = null;
/* 2396 */       String appname = null;
/* 2397 */       String network = null;
/*      */       
/* 2399 */       out.write(10);
/*      */       
/* 2401 */       haid = (String)request.getAttribute("haid");
/* 2402 */       appname = request.getParameter("appName");
/* 2403 */       network = request.getParameter("network");
/* 2404 */       String resourcename = request.getParameter("name");
/* 2405 */       String displayname = (String)request.getAttribute("displayname");
/* 2406 */       request.setAttribute("name", resourcename);
/* 2407 */       request.setAttribute("haid", request.getParameter("haid"));
/* 2408 */       request.setAttribute("appName", request.getParameter("appName"));
/* 2409 */       String resourceid = (String)request.getAttribute("resourceid");
/* 2410 */       Properties ids = (Properties)request.getAttribute("ids");
/* 2411 */       String redirect = "/HostResource.do?name=" + resourcename + "&haid=" + request.getParameter("haid") + "&appName=" + request.getParameter("appName") + "&resourceid=" + resourceid + "";
/* 2412 */       String encodeurl = URLEncoder.encode(redirect);
/* 2413 */       String redirecting = "/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid;
/* 2414 */       String encoding = URLEncoder.encode(redirecting);
/* 2415 */       HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/* 2416 */       String tab = "1";
/* 2417 */       String search = null;
/*      */       
/* 2419 */       request.setAttribute("breadcumps", search);
/* 2420 */       request.setAttribute("tabtoselect", tab);
/* 2421 */       List servicesInHost = null;
/*      */       
/* 2423 */       if (request.isUserInRole("OPERATOR"))
/*      */       {
/* 2425 */         String owner = request.getRemoteUser();
/* 2426 */         servicesInHost = com.adventnet.appmanager.client.views.ViewsCreator.getServicesInHostForOwner(resourcename, owner);
/*      */       }
/*      */       else
/*      */       {
/* 2430 */         servicesInHost = com.adventnet.appmanager.client.views.ViewsCreator.getServicesInHost(resourcename);
/*      */       }
/* 2432 */       request.setAttribute("servicesinhost", servicesInHost);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2437 */       out.write(10);
/*      */       
/* 2439 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2440 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2441 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2443 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/OpManagerLayout.jsp");
/* 2444 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2445 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2447 */           out.write(10);
/* 2448 */           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2450 */           out.write(10);
/* 2451 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2453 */           out.write(10);
/* 2454 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2456 */           out.write(10);
/* 2457 */           if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2459 */           out.write(10);
/* 2460 */           out.write(10);
/* 2461 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2463 */           out.write(32);
/* 2464 */           out.write(32);
/* 2465 */           out.write(10);
/*      */           
/* 2467 */           PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2468 */           _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 2469 */           _jspx_th_tiles_005fput_005f5.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2471 */           _jspx_th_tiles_005fput_005f5.setName("UserArea");
/*      */           
/* 2473 */           _jspx_th_tiles_005fput_005f5.setType("string");
/* 2474 */           int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 2475 */           if (_jspx_eval_tiles_005fput_005f5 != 0) {
/* 2476 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 2477 */               out = _jspx_page_context.pushBody();
/* 2478 */               _jspx_th_tiles_005fput_005f5.setBodyContent((BodyContent)out);
/* 2479 */               _jspx_th_tiles_005fput_005f5.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2482 */               out.write("\n<a name=\"top1\"></a>\n");
/* 2483 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 2485 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2486 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2487 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2489 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 2491 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 2493 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 2495 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2496 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2497 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2498 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 2501 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2502 */               String available = null;
/* 2503 */               available = (String)_jspx_page_context.findAttribute("available");
/* 2504 */               out.write(10);
/*      */               
/* 2506 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2507 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2508 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2510 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 2512 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 2514 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 2516 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2517 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2518 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2519 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 2522 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2523 */               String unavailable = null;
/* 2524 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2525 */               out.write(10);
/*      */               
/* 2527 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2528 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2529 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2531 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 2533 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 2535 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 2537 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2538 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2539 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2540 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 2543 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2544 */               String unmanaged = null;
/* 2545 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2546 */               out.write(10);
/*      */               
/* 2548 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2549 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2550 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2552 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 2554 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 2556 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 2558 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2559 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2560 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2561 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 2564 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2565 */               String scheduled = null;
/* 2566 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2567 */               out.write(10);
/*      */               
/* 2569 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2570 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2571 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2573 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2575 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2577 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2579 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2580 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2581 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2582 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 2585 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2586 */               String critical = null;
/* 2587 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 2588 */               out.write(10);
/*      */               
/* 2590 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2591 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2592 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2594 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 2596 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 2598 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 2600 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2601 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2602 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2603 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 2606 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2607 */               String clear = null;
/* 2608 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 2609 */               out.write(10);
/*      */               
/* 2611 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2612 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2613 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2615 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 2617 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 2619 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 2621 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2622 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2623 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2624 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 2627 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2628 */               String warning = null;
/* 2629 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 2630 */               out.write(10);
/* 2631 */               out.write(10);
/*      */               
/* 2633 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2634 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 2636 */               out.write(10);
/* 2637 */               out.write(10);
/* 2638 */               out.write(10);
/* 2639 */               out.write(32);
/* 2640 */               out.write(32);
/* 2641 */               out.write(10);
/*      */               
/* 2643 */               Vector v = (Vector)request.getAttribute("category");
/* 2644 */               boolean cpudata = false;
/* 2645 */               boolean showmem = false;
/* 2646 */               boolean showdisk = false;
/* 2647 */               boolean showsys = false;
/* 2648 */               String showdata = (String)request.getAttribute("showdata");
/* 2649 */               for (int k = 0; k < v.size(); k++)
/*      */               {
/* 2651 */                 if (((String)v.get(k)).equals("CPU Utilization"))
/*      */                 {
/* 2653 */                   cpudata = true;
/* 2654 */                   request.setAttribute("cpudata", "true");
/*      */                 }
/* 2656 */                 else if (((String)v.get(k)).equals("Disk Utilization"))
/*      */                 {
/* 2658 */                   showdisk = true;
/*      */                 }
/* 2660 */                 else if (((String)v.get(k)).equals("Memory Utilization"))
/*      */                 {
/* 2662 */                   showmem = true;
/*      */                 }
/* 2664 */                 else if (((String)v.get(k)).equals("System Load"))
/*      */                 {
/* 2666 */                   showsys = true;
/*      */                 }
/*      */               }
/*      */               
/* 2670 */               com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI api = (com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI)com.adventnet.nms.util.NmsUtil.getAPI("ApplnDataCollectionAPI");
/* 2671 */               com.adventnet.nms.appln.hostresource.datacollection.server.model.HostDetails host = (com.adventnet.nms.appln.hostresource.datacollection.server.model.HostDetails)api.getCollectData(resourcename, "HOST");
/* 2672 */               String hostname = host.getResourceName();
/* 2673 */               String resType = host.getResourceType();
/* 2674 */               wlsGraph.setParam(resourceid, "AVAILABILITY");
/* 2675 */               perfgraph.setresourceid(Integer.parseInt(resourceid));
/* 2676 */               perfgraph.setEntity("Response Time");
/* 2677 */               Properties memprops1 = null;
/* 2678 */               double c = 0.0D;
/* 2679 */               if (host.getConfigured())
/*      */               {
/*      */ 
/*      */ 
/* 2683 */                 out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"84%\" valign=\"top\"> \n      ");
/*      */                 
/* 2685 */                 hrbean.setresourceName(resourcename);
/* 2686 */                 hrbean.setResourceId(resourceid);
/* 2687 */                 long maxcollectiontime = hrbean.getmaxcollectiontime();
/* 2688 */                 Properties cpuprops = cpugraph.getCpuData(resourcename, maxcollectiontime, resourceid);
/* 2689 */                 String errormessage = host.getErrorMsg();
/* 2690 */                 if (v.size() != 0)
/*      */                 {
/*      */ 
/*      */ 
/* 2694 */                   ArrayList attribIDs = new ArrayList();
/* 2695 */                   ArrayList resIDs = new ArrayList();
/* 2696 */                   attribIDs.add((String)systeminfo.get("HEALTHID"));
/* 2697 */                   attribIDs.add(ids.getProperty("Availability"));
/* 2698 */                   attribIDs.add(ids.getProperty("Response Time"));
/* 2699 */                   attribIDs.add(ids.getProperty("CPU Utilization"));
/* 2700 */                   attribIDs.add("715");
/* 2701 */                   attribIDs.add("716");
/* 2702 */                   attribIDs.add("400");
/* 2703 */                   attribIDs.add("401");
/* 2704 */                   attribIDs.add("404");
/* 2705 */                   attribIDs.add("405");
/* 2706 */                   if (ids.getProperty("Swap Memory Utilization") != null)
/*      */                   {
/* 2708 */                     attribIDs.add(ids.getProperty("Swap Memory Utilization"));
/*      */                   }
/* 2710 */                   if (ids.getProperty("Physical Memory Utilization") != null)
/*      */                   {
/* 2712 */                     attribIDs.add(ids.getProperty("Physical Memory Utilization"));
/*      */                   }
/* 2714 */                   if (ids.getProperty("Jobs in Minute") != null)
/*      */                   {
/* 2716 */                     attribIDs.add(ids.getProperty("Jobs in Minute"));
/*      */                   }
/* 2718 */                   if (ids.getProperty("Jobs in 15 Minutes") != null)
/*      */                   {
/* 2720 */                     attribIDs.add(ids.getProperty("Jobs in 15 Minutes"));
/*      */                   }
/* 2722 */                   if (ids.getProperty("Jobs in 5 Minutes") != null)
/*      */                   {
/* 2724 */                     attribIDs.add(ids.getProperty("Jobs in 5 Minutes"));
/*      */                   }
/* 2726 */                   resIDs.add(request.getParameter("resourceid"));
/* 2727 */                   diskgraph.setresourceName(resourcename);
/* 2728 */                   Hashtable diskdata = diskgraph.getDiskData(resourcename, maxcollectiontime, request.getParameter("resourceid"));
/* 2729 */                   for (Enumeration e = diskdata.keys(); e.hasMoreElements();)
/*      */                   {
/* 2731 */                     String entity = (String)e.nextElement();
/* 2732 */                     Properties diskprops = (Properties)diskdata.get(entity);
/* 2733 */                     resIDs.add(diskprops.getProperty("ID"));
/*      */                   }
/* 2735 */                   ArrayList processlists = (ArrayList)request.getAttribute("process");
/* 2736 */                   if (processlists != null)
/*      */                   {
/* 2738 */                     for (int i = 0; i < processlists.size(); i++)
/*      */                     {
/* 2740 */                       ArrayList processlist = (ArrayList)processlists.get(i);
/* 2741 */                       resIDs.add((String)processlist.get(0));
/*      */                     }
/*      */                   }
/*      */                   
/* 2745 */                   out.write(10);
/* 2746 */                   out.write(10);
/* 2747 */                   out.write(10);
/*      */                   
/* 2749 */                   IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2750 */                   _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2751 */                   _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 2753 */                   _jspx_th_logic_005fiterate_005f0.setName("servicesinhost");
/*      */                   
/* 2755 */                   _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                   
/* 2757 */                   _jspx_th_logic_005fiterate_005f0.setType("java.util.Map");
/*      */                   
/* 2759 */                   _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/* 2760 */                   int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2761 */                   if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2762 */                     Map row = null;
/* 2763 */                     Integer i = null;
/* 2764 */                     if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2765 */                       out = _jspx_page_context.pushBody();
/* 2766 */                       _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2767 */                       _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                     }
/* 2769 */                     row = (Map)_jspx_page_context.findAttribute("row");
/* 2770 */                     i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                     for (;;) {
/* 2772 */                       out.write(32);
/* 2773 */                       out.write(10);
/*      */                       
/* 2775 */                       resIDs.add(row.get("RESOURCEID"));
/* 2776 */                       attribIDs.add(availabilitykeys.get(row.get("TYPE")));
/* 2777 */                       attribIDs.add(healthkeys.get(row.get("TYPE")));
/*      */                       
/* 2779 */                       out.write(10);
/* 2780 */                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2781 */                       row = (Map)_jspx_page_context.findAttribute("row");
/* 2782 */                       i = (Integer)_jspx_page_context.findAttribute("i");
/* 2783 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 2786 */                     if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2787 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 2790 */                   if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2791 */                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                   }
/*      */                   
/* 2794 */                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2795 */                   out.write(10);
/*      */                   
/* 2797 */                   IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2798 */                   _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 2799 */                   _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 2801 */                   _jspx_th_logic_005fiterate_005f1.setName("url_ids");
/*      */                   
/* 2803 */                   _jspx_th_logic_005fiterate_005f1.setId("attribute");
/*      */                   
/* 2805 */                   _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                   
/* 2807 */                   _jspx_th_logic_005fiterate_005f1.setType("java.lang.String");
/* 2808 */                   int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 2809 */                   if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 2810 */                     String attribute = null;
/* 2811 */                     Integer j = null;
/* 2812 */                     if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2813 */                       out = _jspx_page_context.pushBody();
/* 2814 */                       _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 2815 */                       _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                     }
/* 2817 */                     attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 2818 */                     j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                     for (;;) {
/* 2820 */                       out.write(10);
/*      */                       
/* 2822 */                       resIDs.add(String.valueOf(attribute));
/*      */                       
/* 2824 */                       out.write(10);
/* 2825 */                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 2826 */                       attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 2827 */                       j = (Integer)_jspx_page_context.findAttribute("j");
/* 2828 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 2831 */                     if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2832 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 2835 */                   if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 2836 */                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                   }
/*      */                   
/* 2839 */                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 2840 */                   out.write(10);
/* 2841 */                   out.write(10);
/*      */                   
/*      */ 
/* 2844 */                   alert = getStatus(resIDs, attribIDs);
/*      */                   
/*      */ 
/*      */ 
/* 2848 */                   out.write("\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t");
/*      */                   
/* 2850 */                   Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2851 */                   String appId = request.getParameter("haid");
/* 2852 */                   String haName = null;
/* 2853 */                   if (appId != null)
/*      */                   {
/* 2855 */                     haName = (String)ht.get(appId);
/*      */                   }
/*      */                   
/* 2858 */                   HashMap hm = (HashMap)request.getAttribute("systeminfo");
/* 2859 */                   String systemType = (String)hm.get("HOSTOS");
/* 2860 */                   if (systemType != null)
/*      */                   {
/* 2862 */                     if (systemType.startsWith("Windows"))
/*      */                     {
/* 2864 */                       systemType = "Windows";
/*      */                     }
/* 2866 */                     else if (systemType.equals("SUN"))
/*      */                     {
/* 2868 */                       systemType = "Sun Solaris";
/*      */                     }
/*      */                   }
/*      */                   
/* 2872 */                   out.write("\n    </tr>\n\t<!--<tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>-->\n</table>\n");
/* 2873 */                   if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                     return;
/* 2875 */                   out.write(10);
/*      */                   
/* 2877 */                   ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2878 */                   _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2879 */                   _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/* 2880 */                   int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2881 */                   if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                     for (;;) {
/* 2883 */                       out.write(10);
/*      */                       
/* 2885 */                       WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2886 */                       _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2887 */                       _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                       
/* 2889 */                       _jspx_th_c_005fwhen_005f0.setTest("${ param.alert!='true' && param.all!='true' }");
/* 2890 */                       int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2891 */                       if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                         for (;;) {
/* 2893 */                           out.write("\n\n\t\t\t\n      <!--<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n        <tr> \n          <td width=\"60%\" height=\"26\" valign=\"top\" >\n     <table width=\"96%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" class=\"lrtbdarkborder\">\n            <tr> \n                <td colspan=\"2\" class=\"tableheadingbborder\">Monitor Information \n                </td>\n              </tr>\n              <tr> \n                <td width=\"30%\" class=\"monitorinfoodd\">Name </td>\n                <td width=\"70%\" class=\"monitorinfoodd\">");
/* 2894 */                           out.print(displayname);
/* 2895 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */                           
/* 2897 */                           EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2898 */                           _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2899 */                           _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/* 2901 */                           _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 2902 */                           int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2903 */                           if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                             for (;;) {
/* 2905 */                               out.write("\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">System Health</td>\n\t\t\t\t<td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2906 */                               out.print(resourceid);
/* 2907 */                               out.write("&attributeid=");
/* 2908 */                               out.print((String)systeminfo.get("HEALTHID"));
/* 2909 */                               out.write("')\">");
/* 2910 */                               out.print(getSeverityImageForHealth("-1"));
/* 2911 */                               out.write("</a></td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 2912 */                               int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2913 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2917 */                           if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2918 */                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                           }
/*      */                           
/* 2921 */                           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2922 */                           out.write("\n\t\t\t\t");
/*      */                           
/* 2924 */                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2925 */                           _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2926 */                           _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/* 2928 */                           _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 2929 */                           int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2930 */                           if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                             for (;;) {
/* 2932 */                               out.write("\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">System Health</td>\n\t\t\t\t<td class=\"monitorinfoeven\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2933 */                               out.print(resourceid);
/* 2934 */                               out.write("&attributeid=");
/* 2935 */                               out.print((String)systeminfo.get("HEALTHID"));
/* 2936 */                               out.write("')\">");
/* 2937 */                               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + (String)systeminfo.get("HEALTHID"))));
/* 2938 */                               out.write("</a>\n\t\t\t\t\t\t\t\t");
/* 2939 */                               out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + (String)systeminfo.get("HEALTHID") + "#" + "MESSAGE"), (String)systeminfo.get("HEALTHID"), alert.getProperty(resourceid + "#" + (String)systeminfo.get("HEALTHID")), resourceid));
/* 2940 */                               out.write("\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 2941 */                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2942 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2946 */                           if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2947 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                           }
/*      */                           
/* 2950 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2951 */                           out.write("\n              <tr> \n                <td class=\"monitorinfoodd\">Type </td>\n                <td class=\"monitorinfoodd\">Server</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 2952 */                           if (_jspx_meth_logic_005fempty_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                             return;
/* 2954 */                           out.write("\n\t\t\t\t");
/*      */                           
/* 2956 */                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2957 */                           _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2958 */                           _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/* 2960 */                           _jspx_th_logic_005fnotEmpty_005f1.setName("systeminfo");
/* 2961 */                           int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2962 */                           if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                             for (;;) {
/* 2964 */                               out.write("\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">Host Name</td>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2965 */                               out.print(systeminfo.get("HOSTNAME"));
/* 2966 */                               out.write("&nbsp;(");
/* 2967 */                               out.print(systeminfo.get("HOSTIP"));
/* 2968 */                               out.write(")</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">Host OS</td>\n\t\t\t\t<td class=\"monitorinfoodd\">\n\t\t\t\t");
/*      */                               
/* 2970 */                               if (systeminfo.get("HOSTOS").equals("SUN"))
/*      */                               {
/*      */ 
/* 2973 */                                 out.write("\n\t\t\t\tSun Solaris\n\t\t\t\t");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 2979 */                                 out.write("\n\t\t\t\t");
/* 2980 */                                 out.print(systeminfo.get("HOSTOS"));
/* 2981 */                                 out.write("\n\t\t\t\t");
/*      */                               }
/*      */                               
/*      */ 
/* 2985 */                               out.write("\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td class=\"monitorinfoodd\">Last Polled at</td>\n\t\t\t<td class=\"monitorinfoodd\">");
/* 2986 */                               out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 2987 */                               out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td class=\"monitorinfoeven\">Next Poll at</td>\n\t\t\t<td class=\"monitorinfoeven\">");
/* 2988 */                               out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 2989 */                               out.write("</td>\n\t\t\t</tr>\n\t\t\t");
/* 2990 */                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2991 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2995 */                           if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2996 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                           }
/*      */                           
/* 2999 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3000 */                           out.write("\n\t\t\t\n            </table>\n                         ");
/* 3001 */                           if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                             return;
/* 3003 */                           out.write("\n          </td>-->\n          <!--<td width=\"40%\" valign=\"top\"  ><table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n            <tr> \n                <td class=\"tableheadingbborder\">Today's Availability</td>\n              </tr>\n              <tr>\n                <td align=\"center\" class=\"whitegrayborder\"><table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n          \t<tr>\n          \t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3004 */                           if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                             return;
/* 3006 */                           out.write("&period=1&resourcename=");
/* 3007 */                           if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                             return;
/* 3009 */                           out.write("')\">\n      <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"Click to view last seven days data\"></a></td>\n            <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3010 */                           if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                             return;
/* 3012 */                           out.write("&period=2&resourcename=");
/* 3013 */                           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                             return;
/* 3015 */                           out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"Click to view last thiry days data\"></a></td>  \n      </tr>\n</table></td>\n              </tr>\n              <tr> \n                <td align=\"center\" class=\"whitegrayborder\"> ");
/*      */                           
/* 3017 */                           AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3018 */                           _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 3019 */                           _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/* 3021 */                           _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                           
/* 3023 */                           _jspx_th_awolf_005fpiechart_005f0.setWidth("250");
/*      */                           
/* 3025 */                           _jspx_th_awolf_005fpiechart_005f0.setHeight("200");
/*      */                           
/* 3027 */                           _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                           
/* 3029 */                           _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                           
/* 3031 */                           _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                           
/* 3033 */                           _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 3034 */                           int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3035 */                           if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3036 */                             if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3037 */                               out = _jspx_page_context.pushBody();
/* 3038 */                               _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3039 */                               _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3042 */                               out.write(" \n                  ");
/*      */                               
/* 3044 */                               Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3045 */                               _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3046 */                               _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                               
/* 3048 */                               _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3049 */                               int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3050 */                               if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3051 */                                 if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3052 */                                   out = _jspx_page_context.pushBody();
/* 3053 */                                   _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3054 */                                   _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 3057 */                                   out.write(32);
/*      */                                   
/* 3059 */                                   AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3060 */                                   _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3061 */                                   _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                   
/* 3063 */                                   _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                                   
/* 3065 */                                   _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 3066 */                                   int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3067 */                                   if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3068 */                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                   }
/*      */                                   
/* 3071 */                                   this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3072 */                                   out.write(" \n                  ");
/*      */                                   
/* 3074 */                                   AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3075 */                                   _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3076 */                                   _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                   
/* 3078 */                                   _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                                   
/* 3080 */                                   _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 3081 */                                   int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3082 */                                   if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3083 */                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                   }
/*      */                                   
/* 3086 */                                   this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3087 */                                   out.write(32);
/* 3088 */                                   int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3089 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3092 */                                 if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3093 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3096 */                               if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3097 */                                 this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                               }
/*      */                               
/* 3100 */                               this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3101 */                               out.write("\t\n                  ");
/* 3102 */                               int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3103 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3106 */                             if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3107 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3110 */                           if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3111 */                             this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */                           }
/*      */                           
/* 3114 */                           this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3115 */                           out.write(" </td>\n              </tr>\n              <tr> \n                <td><table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n                    <tr> \n                      <td width=\"33%\" height=\"63\" class=\"yellowgrayborder\" >Current \n                        Status </td>\n                      <td width=\"17%\" height=\"63\" class=\"yellowgrayborder\"> \n                        <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3116 */                           out.print(resourceid);
/* 3117 */                           out.write("&attributeid=");
/* 3118 */                           out.print(ids.getProperty("Availability"));
/* 3119 */                           out.write("&alertconfigurl=");
/* 3120 */                           out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=" + ids.getProperty("Availability") + "&attributeToSelect=" + ids.getProperty("Availability") + "&redirectto=" + encodeurl));
/* 3121 */                           out.write("')\"> \n                        ");
/* 3122 */                           out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + ids.getProperty("Availability"))));
/* 3123 */                           out.write("</a> </td>\n                         <td width=\"50%\" class=\"yellowgrayborder\" align=\"right\"> \n                        <img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3124 */                           out.print(resourceid);
/* 3125 */                           out.write("&attributeIDs=");
/* 3126 */                           out.print(ids.getProperty("Availability"));
/* 3127 */                           out.write(44);
/* 3128 */                           out.print(ids.getProperty("Health"));
/* 3129 */                           out.write("&attributeToSelect=");
/* 3130 */                           out.print(ids.getProperty("Availability"));
/* 3131 */                           out.write("&redirectto=");
/* 3132 */                           out.print(encodeurl);
/* 3133 */                           out.write("\" class=\"staticlinks\">");
/* 3134 */                           out.print(ALERTCONFIG_TEXT);
/* 3135 */                           out.write("</a>&nbsp;</td>\n                    </tr>\n                    \n                  </table></td>\n              </tr>\n            </table>\n            </td>\n        </tr>\n      </table>-->\n      <br>\n\t  ");
/*      */                           
/* 3137 */                           IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3138 */                           _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3139 */                           _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/* 3141 */                           _jspx_th_c_005fif_005f7.setTest("${!empty cpudata && cpudata=='true'}");
/* 3142 */                           int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3143 */                           if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                             for (;;) {
/* 3145 */                               out.write("\n\t  ");
/*      */                               
/* 3147 */                               cpugraph.setresourceName(resourcename);
/* 3148 */                               cpugraph.setCategory("CPU Utilization");
/* 3149 */                               cpugraph.setEntity("CPUUtilization");
/*      */                               
/* 3151 */                               out.write("\t\n\t  ");
/* 3152 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3153 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3157 */                           if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3158 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                           }
/*      */                           
/* 3161 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3162 */                           out.write("\n      <!--<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n        <tr>\n\t\t");
/* 3163 */                           if (_jspx_meth_c_005fif_005f8(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                             return;
/* 3165 */                           out.write(10);
/* 3166 */                           out.write(9);
/* 3167 */                           out.write(9);
/* 3168 */                           if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                             return;
/* 3170 */                           out.write("\t\t \n          \n        </tr>\n      </table>\n      \n\t\t");
/*      */                           
/* 3172 */                           IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3173 */                           _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3174 */                           _jspx_th_c_005fif_005f10.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/* 3176 */                           _jspx_th_c_005fif_005f10.setTest("${!empty cpudata && cpudata=='true'}");
/* 3177 */                           int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3178 */                           if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                             for (;;) {
/* 3180 */                               out.write("\n    <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\" height=\"200\">\n      <tr> \n        <td  class=\"rbborder\"> \n\t\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"80%\">\n            <tr> \n              <td width=\"94%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3181 */                               if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3183 */                               out.write("&attributeid=");
/* 3184 */                               out.print(ids.getProperty("CPU Utilization"));
/* 3185 */                               out.write("&period=-7')\"> \n                <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"Click to view last seven days data\"></a></td>\n              <td width=\"6%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3186 */                               if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3188 */                               out.write("&attributeid=");
/* 3189 */                               out.print(ids.getProperty("CPU Utilization"));
/* 3190 */                               out.write("&period=-30')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"Click to view last thiry days data\"></a></td>\n            </tr>\n            <tr> \n              <td colspan=\"2\"> ");
/* 3191 */                               if (_jspx_meth_awolf_005ftimechart_005f0(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3193 */                               out.write(" </td>\n            </tr>\n          </table></td>\n\t\t  <td width=\"50%\" valign=\"top\" class=\"bottomborder\">\n\t\t  \n        <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t<tr>\n\t\t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3194 */                               if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3196 */                               out.write("&attributeid=");
/* 3197 */                               out.print(ids.getProperty("Response Time"));
/* 3198 */                               out.write("&period=-7',740,550)\">\n\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"></td>\n\t\t<td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3199 */                               if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3201 */                               out.write("&attributeid=");
/* 3202 */                               out.print(ids.getProperty("Response Time"));
/* 3203 */                               out.write("&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\"\n\t\twidth=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"Click to view last thiry days data\"></a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td colspan=\"2\">\n\t\t");
/* 3204 */                               if (_jspx_meth_awolf_005ftimechart_005f1(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3206 */                               out.write(" \n\t\t</td>\n\t\t</tr>\n\t\t</table></td>\n      </tr>\n      <tr>\n        <td class=\"rborder\" valign=\"top\"> \n\t\t");
/*      */                               
/* 3208 */                               if (cpuprops.getProperty("CURVALUE") != null)
/*      */                               {
/*      */ 
/* 3211 */                                 out.write("\n          <table width=\"100%\" border=\"0\" align=center cellpadding=\"0\" cellspacing=\"0\" >\n            <tr> \n              <td width=\"29%\" height=\"22\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3212 */                                 if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                   return;
/* 3214 */                                 out.write("</span> \n              </td>\n              <td width=\"25%\" height=\"22\" class=\"columnheadingnotop\"> <span class=\"bodytextbold\">");
/* 3215 */                                 if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                   return;
/* 3217 */                                 out.write("</span></td>\n              <td width=\"46%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3218 */                                 if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                   return;
/* 3220 */                                 out.write("</span></td>\n            </tr>\n            <tr> \n              <td height=\"22\" class=\"whitegrayborder\">Current Value</td>\n              <td width=\"25%\" height=\"22\" class=\"whitegrayborder\">");
/* 3221 */                                 out.print(formatNumber(cpuprops.getProperty("CURVALUE")));
/* 3222 */                                 out.write("%</td>\n              <td width=\"46%\" class=\"whitegrayborder\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3223 */                                 out.print(resourceid);
/* 3224 */                                 out.write("&attributeid=");
/* 3225 */                                 out.print(ids.getProperty("CPU Utilization"));
/* 3226 */                                 out.write("')\">");
/* 3227 */                                 out.print(getSeverityImage(alert.getProperty(resourceid + "#" + ids.getProperty("CPU Utilization"))));
/* 3228 */                                 out.write("</a></td>\n            </tr>\n            <tr> \n              <td height=\"22\" class=\"yellowgrayborder\">Peak Value </td>\n              <td height=\"22\" class=\"yellowgrayborder\">");
/* 3229 */                                 out.print(formatNumber(cpuprops.getProperty("MAXVALUE")));
/* 3230 */                                 out.write("%</td>\n              <td class=\"yellowgrayborder\">&nbsp;</td>\n            </tr>\n            <tr align=\"right\"> \n              <td height=\"22\" colspan=\"3\" class=\"whitegrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3231 */                                 out.print(resourceid);
/* 3232 */                                 out.write("&attributeIDs=");
/* 3233 */                                 out.print(ids.getProperty("CPU Utilization"));
/* 3234 */                                 out.write("&attributeToSelect=");
/* 3235 */                                 out.print(ids.getProperty("CPU Utilization"));
/* 3236 */                                 out.write("&redirectto=");
/* 3237 */                                 out.print(encodeurl);
/* 3238 */                                 out.write("\" class=\"staticlinks\">");
/* 3239 */                                 out.print(ALERTCONFIG_TEXT);
/* 3240 */                                 out.write("</a></td>\n            </tr>\n          </table>\n          ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3246 */                                 out.write("\n          <table width=\"100%\" border=\"0\" align=center cellpadding=\"0\" cellspacing=\"0\" >\n            <tr> \n              <td width=\"60%\" height=\"22\" class=\"columnheadingnotop\">CPU Usage \n                Metrics </td>\n              <td width=\"40%\" height=\"22\" class=\"columnheadingnotop\"> Value</td>\n            </tr>\n            <tr> \n              <td width=\"40%\" height=\"22\" class=\"whitegrayborder\" colspan=\"2\" align=\"center\"> \n                No Data Available</td>\n            </tr>\n          </table>\n          ");
/*      */                               }
/*      */                               
/*      */ 
/* 3250 */                               out.write("\n        </td>\n\t\t<td valign=\"top\">\n        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n            <tr> \n              <td height=\"22\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3251 */                               if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3253 */                               out.write("</span> \n              </td>\n              <td height=\"22\" class=\"columnheadingnotop\"> <span class=\"bodytextbold\">");
/* 3254 */                               if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3256 */                               out.write("</span></td>\n              <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3257 */                               if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                                 return;
/* 3259 */                               out.write("</span></td>\n            </tr>\n           \n              <tr> \n                <td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >Current Response \n                  Time </td>\n                <td width=\"26%\" height=\"19\" class=\"whitegrayborder\"> \n                  ");
/*      */                               
/* 3261 */                               if (perfgraph.getResponseTime(Integer.parseInt(resourceid)) != -1L)
/*      */                               {
/* 3263 */                                 out.write("\n                  ");
/* 3264 */                                 out.print(formatNumber(perfgraph.getResponseTime(Integer.parseInt(resourceid))));
/* 3265 */                                 out.write(" \n                  ms \n                  ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/* 3270 */                                 out.write("\n                  - \n                  ");
/*      */                               }
/*      */                               
/*      */ 
/* 3274 */                               out.write("\n                </td>\n                <td width=\"18%\" class=\"whitegrayborder\"> \n                  ");
/*      */                               
/* 3276 */                               int resTimeStatus = -1;
/* 3277 */                               if (alert.getProperty("Response Time") != null)
/*      */                               {
/* 3279 */                                 resTimeStatus = Integer.parseInt(alert.getProperty("Response Time"));
/*      */                               }
/*      */                               
/* 3282 */                               out.write("\n                  <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3283 */                               out.print(resourceid);
/* 3284 */                               out.write("&attributeid=");
/* 3285 */                               out.print(ids.getProperty("Response Time"));
/* 3286 */                               out.write("&alertconfigurl=");
/* 3287 */                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=" + ids.getProperty("Response Time") + "&attributeToSelect=" + ids.getProperty("Response Time") + "&redirectto=" + encodeurl));
/* 3288 */                               out.write("')\">");
/* 3289 */                               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + ids.getProperty("Response Time"))));
/* 3290 */                               out.write("</a> \n\t\t\t\t  </td>\n              </tr>\n              <tr> \n                <td height=\"32\" colspan=\"3\" class=\"yellowgrayborder\" align=\"right\"> \n                  <img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3291 */                               out.print(resourceid);
/* 3292 */                               out.write("&attributeIDs=");
/* 3293 */                               out.print(ids.getProperty("Response Time"));
/* 3294 */                               out.write("&attributeToSelect=");
/* 3295 */                               out.print(ids.getProperty("Response Time"));
/* 3296 */                               out.write("&redirectto=");
/* 3297 */                               out.print(encodeurl);
/* 3298 */                               out.write("\" class=\"staticlinks\">");
/* 3299 */                               out.print(ALERTCONFIG_TEXT);
/* 3300 */                               out.write("</a>&nbsp;</td>\n              </tr>\n          </table></td>\n      </tr>\n    </table>\n    <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n</table>\n");
/* 3301 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3302 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3306 */                           if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3307 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                           }
/*      */                           
/* 3310 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3311 */                           out.write(10);
/*      */                           
/* 3313 */                           IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3314 */                           _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3315 */                           _jspx_th_c_005fif_005f11.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/* 3317 */                           _jspx_th_c_005fif_005f11.setTest("${(empty cpudata && showdata=='1') || (empty cpudata && showdata=='2')}");
/* 3318 */                           int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3319 */                           if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                             for (;;) {
/* 3321 */                               out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n      <td width=\"405\" height=\"127\" valign=\"top\">\n\t  <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n\t  <tr>\n\t  <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3322 */                               if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                 return;
/* 3324 */                               out.write("&attributeid=");
/* 3325 */                               out.print(ids.getProperty("Response Time"));
/* 3326 */                               out.write("&period=-7',740,550)\">\n\t  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"Click to view last seven days data\"></td>\n\t  <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3327 */                               if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                 return;
/* 3329 */                               out.write("&attributeid=");
/* 3330 */                               out.print(ids.getProperty("Response Time"));
/* 3331 */                               out.write("&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\"\n\t  width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"Click to view last thiry days data\"></a></td>\n\t  </tr>\n\t  <tr>\n\t  <td colspan=\"2\">\n\t  ");
/* 3332 */                               if (_jspx_meth_awolf_005ftimechart_005f2(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                 return;
/* 3334 */                               out.write("\n\t  </tr>\n\t  </table></td>\n\t  <td width=\"562\" valign=\"top\"> <br> <br>\n\t  <table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n\t  <tr>\n\t  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3335 */                               if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                 return;
/* 3337 */                               out.write("</span></td>\n\t  <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3338 */                               if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                 return;
/* 3340 */                               out.write("</span></td>\n\t  <td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 3341 */                               if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                 return;
/* 3343 */                               out.write("</span></td>\n\t  </tr>\n\t  <tr>\n\t  <tr>\n\t  <td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >Current Response\n\t  Time </td>\n\t  <td width=\"26%\" height=\"19\" class=\"whitegrayborder\">\n\t  ");
/*      */                               
/* 3345 */                               if (perfgraph.getResponseTime(Integer.parseInt(resourceid)) != -1L)
/*      */                               {
/* 3347 */                                 out.write("\n\t\t\t  ");
/* 3348 */                                 out.print(formatNumber(perfgraph.getResponseTime(Integer.parseInt(resourceid))));
/* 3349 */                                 out.write("\n\t\t\t  ms\n\t\t\t  ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/* 3354 */                                 out.write("\n\t\t\t  -\n\t\t\t  ");
/*      */                               }
/*      */                               
/*      */ 
/* 3358 */                               out.write("\n\t\t</td>\n\t\t<td width=\"18%\" class=\"whitegrayborder\">\n\t\t");
/*      */                               
/* 3360 */                               int resTimeStatus = -1;
/* 3361 */                               if (alert.getProperty("Response Time") != null)
/*      */                               {
/* 3363 */                                 resTimeStatus = Integer.parseInt(alert.getProperty("Response Time"));
/*      */                               }
/*      */                               
/* 3366 */                               out.write("\n\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3367 */                               out.print(resourceid);
/* 3368 */                               out.write("&attributeid=");
/* 3369 */                               out.print(ids.getProperty("Response Time"));
/* 3370 */                               out.write("&alertconfigurl=");
/* 3371 */                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=" + ids.getProperty("Response Time") + "&attributeToSelect=" + ids.getProperty("Response Time") + "&redirectto=" + encodeurl));
/* 3372 */                               out.write("')\">");
/* 3373 */                               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + ids.getProperty("Response Time"))));
/* 3374 */                               out.write("</a>\n\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td height=\"32\" colspan=\"4\" class=\"yellowgrayborder\" align=\"right\">\n\t\t<img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3375 */                               out.print(resourceid);
/* 3376 */                               out.write("&attributeIDs=");
/* 3377 */                               out.print(ids.getProperty("Response Time"));
/* 3378 */                               out.write("&attributeToSelect=");
/* 3379 */                               out.print(ids.getProperty("Response Time"));
/* 3380 */                               out.write("&redirectto=");
/* 3381 */                               out.print(encodeurl);
/* 3382 */                               out.write("\" class=\"staticlinks\">");
/* 3383 */                               out.print(ALERTCONFIG_TEXT);
/* 3384 */                               out.write("</a>&nbsp;</td>\n\t\t</tr>\n\t\t</table></td>\n\t\t</tr>\n\t\t</table>\n \t\n \t");
/* 3385 */                               if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                                 return;
/* 3387 */                               out.write(10);
/* 3388 */                               out.write(10);
/* 3389 */                               out.write(32);
/* 3390 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3391 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3395 */                           if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3396 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                           }
/*      */                           
/* 3399 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3400 */                           out.write("-->\n\t\n\t");
/*      */                           
/* 3402 */                           if (showdisk)
/*      */                           {
/*      */ 
/* 3405 */                             out.write("\n      <!--<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n        <tr> \n          <td width=\"48%\" height=\"29\" class=\"tableheadingtrans\" >Disk Utilization \n           <a name=\"Disk Utilization\"></a> </td>\n\t\t\t\n          \n        <td width=\"52%\" height=\"29\" class=\"tableheadingtrans\">&nbsp;Memory Utilization \n          - Last One Hour<a name=\"Memory Utilization\"></a> </td>\n        </tr>\n      </table>\n      \n\t  \n    <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\" height=\"200\">\n      <tr> \n        <td width=\"48%\" height=\"60\" class=\"rbborder\"><br> ");
/*      */                             
/* 3407 */                             StackBarChart _jspx_th_awolf_005fstackbarchart_005f0 = (StackBarChart)this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbarcolors.get(StackBarChart.class);
/* 3408 */                             _jspx_th_awolf_005fstackbarchart_005f0.setPageContext(_jspx_page_context);
/* 3409 */                             _jspx_th_awolf_005fstackbarchart_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 3411 */                             _jspx_th_awolf_005fstackbarchart_005f0.setDataSetProducer("diskgraph");
/*      */                             
/* 3413 */                             _jspx_th_awolf_005fstackbarchart_005f0.setWidth("300");
/*      */                             
/* 3415 */                             _jspx_th_awolf_005fstackbarchart_005f0.setHeight("170");
/*      */                             
/* 3417 */                             _jspx_th_awolf_005fstackbarchart_005f0.setLegend("true");
/*      */                             
/* 3419 */                             _jspx_th_awolf_005fstackbarchart_005f0.setUrl(false);
/*      */                             
/* 3421 */                             _jspx_th_awolf_005fstackbarchart_005f0.setXaxisLabel("Disk Partitions");
/*      */                             
/* 3423 */                             _jspx_th_awolf_005fstackbarchart_005f0.setYaxisLabel("Value in %");
/*      */                             
/* 3425 */                             _jspx_th_awolf_005fstackbarchart_005f0.setBarcolors(new java.awt.Paint[] { new java.awt.Color(255, 89, 89), new java.awt.Color(152, 255, 150) });
/* 3426 */                             int _jspx_eval_awolf_005fstackbarchart_005f0 = _jspx_th_awolf_005fstackbarchart_005f0.doStartTag();
/* 3427 */                             if (_jspx_eval_awolf_005fstackbarchart_005f0 != 0) {
/* 3428 */                               if (_jspx_eval_awolf_005fstackbarchart_005f0 != 1) {
/* 3429 */                                 out = _jspx_page_context.pushBody();
/* 3430 */                                 _jspx_th_awolf_005fstackbarchart_005f0.setBodyContent((BodyContent)out);
/* 3431 */                                 _jspx_th_awolf_005fstackbarchart_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3434 */                                 out.write("\t\n          ");
/* 3435 */                                 int evalDoAfterBody = _jspx_th_awolf_005fstackbarchart_005f0.doAfterBody();
/* 3436 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3439 */                               if (_jspx_eval_awolf_005fstackbarchart_005f0 != 1) {
/* 3440 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3443 */                             if (_jspx_th_awolf_005fstackbarchart_005f0.doEndTag() == 5) {
/* 3444 */                               this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbarcolors.reuse(_jspx_th_awolf_005fstackbarchart_005f0); return;
/*      */                             }
/*      */                             
/* 3447 */                             this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbarcolors.reuse(_jspx_th_awolf_005fstackbarchart_005f0);
/* 3448 */                             out.write(" \n          </td>\n        <td width=\"52%\" valign=\"top\" class=\"bottomborder\">\n        \n        ");
/*      */                             
/* 3450 */                             if (showmem)
/*      */                             {
/* 3452 */                               memgraph.setresourceName(resourcename);
/* 3453 */                               memgraph.setCategory("Memory Utilization");
/* 3454 */                               memgraph.setResourceId(resourceid);
/* 3455 */                               Hashtable memdata = memgraph.getMemoryData(resourcename, maxcollectiontime, resourceid);
/* 3456 */                               String mode = (String)request.getAttribute("mode");
/* 3457 */                               String attributeid = ids.getProperty("Swap Memory Utilization");
/* 3458 */                               if (ids.getProperty("Physical Memory Utilization") != null)
/*      */                               {
/* 3460 */                                 if ((mode != null) && (mode.equals("SNMP")))
/*      */                                 {
/* 3462 */                                   attributeid = ids.getProperty("Physical Memory Utilization");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3466 */                                   attributeid = attributeid + "," + ids.getProperty("Physical Memory Utilization");
/*      */                                 }
/*      */                               }
/*      */                               
/* 3470 */                               String aid = null;
/* 3471 */                               if (resType.equals("SUN"))
/*      */                               {
/* 3473 */                                 if ((mode != null) && (mode.equals("SNMP")))
/*      */                                 {
/* 3475 */                                   aid = ids.getProperty("Physical Memory Utilization");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3479 */                                   aid = ids.getProperty("Swap Memory Utilization");
/*      */                                 }
/*      */                                 
/*      */                               }
/*      */                               else {
/* 3484 */                                 aid = ids.getProperty("Physical Memory Utilization");
/*      */                               }
/*      */                               
/* 3487 */                               out.write("\n\n          <table width=\"100%\" border=\"0\" cellpadding=\"0\"  cellspacing=\"0\" >\n            <tr>\n      \t  \t  \t        \t<td width=\"94%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3488 */                               if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 3490 */                               out.write("&attributeid=");
/* 3491 */                               out.print(aid);
/* 3492 */                               out.write("&period=-7')\">\n      \t  \t  \t\t\t    <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"Click to view last seven days data\"></a></td>\n      \t  \t  \t\t\t          <td width=\"6%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3493 */                               if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 3495 */                               out.write("&attributeid=");
/* 3496 */                               out.print(aid);
/* 3497 */                               out.write("&period=-30')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"Click to view last thiry days data\"></a></td>  \n      \t  \t  \t\t\t    </tr>\n      \t  \t  \t\t\t    <tr>\n      \t  \t  \t\t\t    <td colspan=\"2\">\n      \t  \t  \t\t\t       \t\t\n      \t    ");
/* 3498 */                               if (_jspx_meth_awolf_005ftimechart_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 3500 */                               out.write(" \n      \t    \t  \t\t\t         \n      \t  \t  \t\t\t        </td>\n      \t  \t  \t\t\t        </tr>\n    </table>      \n\t\t\t\t\t\t\n</td>\n      </tr>\n      <tr>\n      <td class=\"rborder\" valign=\"top\">\n<table border=0 cellspacing=0 cellpadding=0 valign=center width=\"100%\"  >\n            <tr> \n              <td class=\"columnheadingnotop\">Disk Utilization</td>\n              <td class=\"columnheadingnotop\">&nbsp; %</td>\n              <td class=\"columnheadingnotop\">&nbsp; </td>\n              <td class=\"columnheadingnotop\">&nbsp; MB</td>\n\t\t\t  <td class=\"columnheadingnotop\">&nbsp; </td>\t\t\t\t\t\t\t\n              <td class=\"columnheadingnotop\" ><span class=\"bodytextbold\">");
/* 3501 */                               if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 3503 */                               out.write("</span></td>\n            </tr>\n            ");
/*      */                               
/* 3505 */                               if (diskdata.isEmpty())
/*      */                               {
/*      */ 
/* 3508 */                                 out.write("\n            <tr> \n              <td colspan=\"4\" align=\"center\" class=\"whitegrayborder\">No Data Available</td>\n            </tr>\n            ");
/*      */                               }
/*      */                               
/* 3511 */                               int i = 0;
/* 3512 */                               for (Enumeration e = diskdata.keys(); e.hasMoreElements();)
/*      */                               {
/* 3514 */                                 String entity = (String)e.nextElement();
/* 3515 */                                 Properties diskprops = (Properties)diskdata.get(entity);
/* 3516 */                                 if (i % 2 == 0)
/*      */                                 {
/* 3518 */                                   Colur = "whitegrayborder";
/*      */                                 }
/*      */                                 else {
/* 3521 */                                   Colur = "yellowgrayborder";
/*      */                                 }
/* 3523 */                                 i++;
/* 3524 */                                 int diskPropsStatus = -1;
/* 3525 */                                 if (diskprops.getProperty("SEVERITY") != null)
/*      */                                 {
/* 3527 */                                   diskPropsStatus = Integer.parseInt(diskprops.getProperty("SEVERITY"));
/*      */                                 }
/*      */                                 
/* 3530 */                                 out.write("\n            <tr> \n              <td class=\"");
/* 3531 */                                 out.print(Colur);
/* 3532 */                                 out.write("\" title=\"");
/* 3533 */                                 out.print(entity);
/* 3534 */                                 out.write(34);
/* 3535 */                                 out.write(62);
/* 3536 */                                 out.print(getTrimmedText(entity, 24));
/* 3537 */                                 out.write("</td>\n              <td class=\"");
/* 3538 */                                 out.print(Colur);
/* 3539 */                                 out.write(34);
/* 3540 */                                 out.write(62);
/* 3541 */                                 out.print(diskprops.getProperty("CURVALUE"));
/* 3542 */                                 out.write("&nbsp;</td>\t      \n              <td class=\"");
/* 3543 */                                 out.print(Colur);
/* 3544 */                                 out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3545 */                                 out.print(diskprops.getProperty("ID"));
/* 3546 */                                 out.write("&attributeid=711&period=-7')\" class=\"resourcename\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"Click to view last seven days data\"></a>&nbsp;</td>\n              <td class=\"");
/* 3547 */                                 out.print(Colur);
/* 3548 */                                 out.write(34);
/* 3549 */                                 out.write(62);
/* 3550 */                                 out.print(formatNumber(diskprops.getProperty("CURVALUEMB")));
/* 3551 */                                 out.write("&nbsp;</td>\n              <td  class=\"");
/* 3552 */                                 out.print(Colur);
/* 3553 */                                 out.write("\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3554 */                                 out.print(diskprops.getProperty("ID"));
/* 3555 */                                 out.write("&attributeid=710&alertconfigurl=");
/* 3556 */                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + diskprops.getProperty("ID") + "&attributeIDs=710&attributeToSelect=710&redirectto=" + encodeurl));
/* 3557 */                                 out.write("')\">");
/* 3558 */                                 out.print(getSeverityImage(alert.getProperty(diskprops.getProperty("ID") + "#" + "710")));
/* 3559 */                                 out.write("</a></td>               \n              <td  class=\"");
/* 3560 */                                 out.print(Colur);
/* 3561 */                                 out.write("\" align=\"center\"><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3562 */                                 out.print(diskprops.getProperty("ID"));
/* 3563 */                                 out.write("&attributeIDs=711,712&attributeToSelect=711&redirectto=");
/* 3564 */                                 out.print(encodeurl);
/* 3565 */                                 out.write("\" class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\"  border=0></a>&nbsp;&nbsp;&nbsp;</td> \n            </tr>\n        \n            ");
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/* 3571 */                               out.write("\n \n          </table>     \n      \n      \n      </td>\n      <td valign=\"top\">\n            \n      <table  border=0 cellspacing=0 cellpadding=0 valign=center width=\"100%\">\n            <tr> \n              <td width=\"40%\" class=\"columnheadingnotop\">");
/* 3572 */                               if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 3574 */                               out.write("</td>\n              <td width=\"30%\" class=\"columnheadingnotop\">&nbsp; %</td>\n              <td width=\"30%\" class=\"columnheadingnotop\">&nbsp;MB</td>\n              <td width=\"5%\" class=\"columnheadingnotop\">");
/* 3575 */                               if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 3577 */                               out.write("</td>\n            </tr>\n            ");
/*      */                               
/* 3579 */                               if (memdata.isEmpty())
/*      */                               {
/*      */ 
/* 3582 */                                 out.write("\n            <tr> \n              <td class=\"whitegrayborder\" colspan=\"4\" align=\"center\"> No Data Available. \n\t\t\t  ");
/*      */                                 
/* 3584 */                                 if (resType.equals("AIX"))
/*      */                                 {
/*      */ 
/* 3587 */                                   out.write("\n\t\t\t  Kindly configure root user for getting the Memory Utilization values.\n\t\t\t  \n\t\t\t  ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3591 */                                 out.write("\n\t\t\t  </td>\n            </tr>\n            ");
/*      */                               }
/*      */                               
/* 3594 */                               i = 0;
/*      */                               
/* 3596 */                               for (Enumeration e = memdata.keys(); e.hasMoreElements();)
/*      */                               {
/* 3598 */                                 String entity = (String)e.nextElement();
/* 3599 */                                 Properties memprops = (Properties)memdata.get(entity);
/* 3600 */                                 memprops1 = (Properties)memdata.get(entity);
/*      */                                 
/* 3602 */                                 if (i % 2 == 0)
/*      */                                 {
/* 3604 */                                   Colur = "whitegrayborder";
/*      */                                 }
/*      */                                 else {
/* 3607 */                                   Colur = "yellowgrayborder";
/*      */                                 }
/* 3609 */                                 i++;
/* 3610 */                                 String attrId = "702";
/*      */                                 
/* 3612 */                                 int memStatus = -1;
/*      */                                 String entitytoshow;
/* 3614 */                                 if (entity.equals("SwapMemUtilization"))
/*      */                                 {
/* 3616 */                                   attrId = ids.getProperty("Swap Memory Utilization");
/* 3617 */                                   String entitytoshow = "Swap Memory Utilization";
/* 3618 */                                   if (alert.getProperty("Swap Memory Utilization") != null)
/*      */                                   {
/* 3620 */                                     memStatus = Integer.parseInt(alert.getProperty("Swap Memory Utilization"));
/*      */                                   }
/*      */                                 }
/* 3623 */                                 else if (entity.equals("VirtualMemUtilization"))
/*      */                                 {
/* 3625 */                                   attrId = ids.getProperty("Swap Memory Utilization");
/* 3626 */                                   String entitytoshow = "Swap Memory Utilization";
/* 3627 */                                   if (alert.getProperty("Swap Memory Utilization") != null)
/*      */                                   {
/* 3629 */                                     memStatus = Integer.parseInt(alert.getProperty("Swap Memory Utilization"));
/*      */                                   }
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3634 */                                   attrId = ids.getProperty("Physical Memory Utilization");
/* 3635 */                                   entitytoshow = "Physical Memory Utilization";
/* 3636 */                                   if (alert.getProperty("Physical Memory Utilization") != null)
/*      */                                   {
/* 3638 */                                     memStatus = Integer.parseInt(alert.getProperty("Physical Memory Utilization"));
/*      */                                   }
/*      */                                 }
/*      */                                 
/* 3642 */                                 out.write("\n            <tr> \n              <td class=\"");
/* 3643 */                                 out.print(Colur);
/* 3644 */                                 out.write("\" width=\"41%\">");
/* 3645 */                                 out.print(entitytoshow);
/* 3646 */                                 out.write("</td>\n              <td class=\"");
/* 3647 */                                 out.print(Colur);
/* 3648 */                                 out.write("\" width=\"22%\">");
/* 3649 */                                 out.print(memprops.getProperty("CURVALUE"));
/* 3650 */                                 out.write("</td>\n              <td class=\"");
/* 3651 */                                 out.print(Colur);
/* 3652 */                                 out.write("\" width=\"22%\">");
/* 3653 */                                 out.print(formatNumber(memprops.getProperty("CURVALUEMB")));
/* 3654 */                                 out.write("&nbsp;</td>\n              <td  width=\"16%\" class=\"");
/* 3655 */                                 out.print(Colur);
/* 3656 */                                 out.write("\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3657 */                                 out.print(resourceid);
/* 3658 */                                 out.write("&attributeid=");
/* 3659 */                                 out.print(attrId);
/* 3660 */                                 out.write("&alertconfigurl=");
/* 3661 */                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=" + attributeid + "&attributeToSelect=" + aid + "&redirectto=" + encodeurl));
/* 3662 */                                 out.write("')\">");
/* 3663 */                                 out.print(getSeverityImage(alert.getProperty(resourceid + "#" + attrId)));
/* 3664 */                                 out.write("</a></td>\n            </tr>\n        \n            ");
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/* 3669 */                               if (resType.equals("HP-UX"))
/*      */                               {
/*      */                                 try
/*      */                                 {
/* 3673 */                                   String a = memprops1.getProperty("CURVALUE");
/* 3674 */                                   double a2 = Double.parseDouble(a);
/* 3675 */                                   String b = memprops1.getProperty("CURVALUEMB");
/* 3676 */                                   double b2 = Double.parseDouble(b);
/* 3677 */                                   c = b2 * 1024.0D / a2;
/*      */                                 }
/*      */                                 catch (Exception e)
/*      */                                 {
/* 3681 */                                   c = 0.0D;
/*      */                                 }
/*      */                               }
/*      */                               
/* 3685 */                               if ((Colur != null) && (Colur.equals("whitegrayborder")))
/*      */                               {
/* 3687 */                                 Colur = "yellowgrayborder";
/*      */                               }
/*      */                               else
/*      */                               {
/* 3691 */                                 Colur = "whitegrayborder";
/*      */                               }
/*      */                               
/* 3694 */                               if (!memdata.isEmpty())
/*      */                               {
/*      */ 
/* 3697 */                                 out.write("\n\t        <tr align=\"right\"> \n              <td colspan=\"4\" height=\"21\" class=\"");
/* 3698 */                                 out.print(Colur);
/* 3699 */                                 out.write("\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3700 */                                 out.print(resourceid);
/* 3701 */                                 out.write("&attributeIDs=");
/* 3702 */                                 out.print(attributeid);
/* 3703 */                                 out.write("&attributeToSelect=");
/* 3704 */                                 out.print(aid);
/* 3705 */                                 out.write("&redirectto=");
/* 3706 */                                 out.print(encodeurl);
/* 3707 */                                 out.write("\" class=\"staticlinks\">");
/* 3708 */                                 out.print(ALERTCONFIG_TEXT);
/* 3709 */                                 out.write("</a> &nbsp;\n              </td>\n            </tr>\n            ");
/*      */                               }
/*      */                               
/*      */ 
/* 3713 */                               out.write("\n\t  \n          </table>\n      \n      </td>\n      </tr>\n    </table>\n    \n    \n      \n    ");
/*      */                             }
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3720 */                             out.write("-->\n    <!--<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr> \n          <td>&nbsp;</td>\n        </tr>\n      </table>-->\n      ");
/*      */                           }
/*      */                           
/*      */ 
/*      */ 
/* 3725 */                           if (showsys)
/*      */                           {
/* 3727 */                             sysgraph.setresourceName(resourcename);
/* 3728 */                             sysgraph.setCategory("System Load");
/* 3729 */                             sysgraph.setResourceId(resourceid);
/* 3730 */                             Hashtable sysdata = sysgraph.getSysloadData(resourcename, maxcollectiontime, resourceid);
/*      */                             
/* 3732 */                             out.write("\n      <!--<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n        <tr> \n          <td colspan=\"2\" width=\"48%\" height=\"29\" class=\"tableheading\">&nbsp;System Load - Last One Hour<a name=\"System Load\"></a> </td>\n        </tr>\n      </table>\n     \n\t  <table  width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\" height=\"200\">\n        <tr> \n          <td width=\"48%\" height=\"60\"> \n\t");
/* 3733 */                             if (_jspx_meth_awolf_005ftimechart_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                               return;
/* 3735 */                             out.write("\n          </td>\n          <td width=\"52%\" valign=\"top\"> <br><table  border=0 cellspacing=0 cellpadding=0 valign=center width=\"100%\" class=\"lrbtborder\">\n              <tr> \n                <td class=\"columnheadingnotop\">Parameter Name</td>\n                <td class=\"columnheadingnotop\">Current Value</td>\n                <td class=\"columnheadingnotop\">Peak Value</td>\n                <td class=\"columnheadingnotop\">Status</td>\n\t\t<td class=\"columnheadingnotop\">&nbsp;</td>\n              </tr>\n              ");
/*      */                             
/* 3737 */                             int i = 0;
/* 3738 */                             if (sysdata.isEmpty())
/*      */                             {
/*      */ 
/* 3741 */                               out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"whitegrayborder\" colspan=\"4\" align=\"center\">Parameter Name</td>\n\t\t\t\t\t</tr>\t\n\t\t\t\t");
/*      */                             }
/*      */                             
/* 3744 */                             for (Enumeration e = sysdata.keys(); e.hasMoreElements();)
/*      */                             {
/* 3746 */                               String atId = "705";
/* 3747 */                               String entity = (String)e.nextElement();
/* 3748 */                               Properties sysprops = (Properties)sysdata.get(entity);
/* 3749 */                               Colur = "whitegrayborder";
/* 3750 */                               int jobsStatus = -1;
/* 3751 */                               entity = "Jobs in Minute";
/* 3752 */                               atId = ids.getProperty("Jobs in Minute");
/* 3753 */                               if (alert.getProperty(resourceid + "#" + atId) != null)
/*      */                               {
/* 3755 */                                 jobsStatus = Integer.parseInt(alert.getProperty(resourceid + "#" + atId));
/*      */                               }
/*      */                               
/* 3758 */                               out.write("\n              <tr> \n                <td align=left class=\"");
/* 3759 */                               out.print(Colur);
/* 3760 */                               out.write("br\">");
/* 3761 */                               out.print(entity);
/* 3762 */                               out.write("</td>\n                <td align=left class=\"");
/* 3763 */                               out.print(Colur);
/* 3764 */                               out.write("br\">");
/* 3765 */                               out.print(formatNumber(sysprops.getProperty("CURVALUE")));
/* 3766 */                               out.write("</td>\n                <td align=left class=\"");
/* 3767 */                               out.print(Colur);
/* 3768 */                               out.write("br\">");
/* 3769 */                               out.print(formatNumber(sysprops.getProperty("MAXVALUE")));
/* 3770 */                               out.write("</td>\n\t\t<td align=left width=\"10\" class=\"");
/* 3771 */                               out.print(Colur);
/* 3772 */                               out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3773 */                               out.print(resourceid);
/* 3774 */                               out.write("&attributeid=");
/* 3775 */                               out.print(atId);
/* 3776 */                               out.write("&alertconfigurl=");
/* 3777 */                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=" + ids.getProperty("Jobs in Minute") + "," + ids.getProperty("Jobs in 15 Minutes") + "," + ids.getProperty("Jobs in 5 Minutes") + "&attributeToSelect=" + ids.getProperty("Jobs in Minute") + "&redirectto=" + encodeurl));
/* 3778 */                               out.write("')\">");
/* 3779 */                               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + atId)));
/* 3780 */                               out.write("</a></td>\n\t\t<td align=left class=\"");
/* 3781 */                               out.print(Colur);
/* 3782 */                               out.write("br\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3783 */                               if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 3785 */                               out.write("&attributeid=");
/* 3786 */                               out.print(atId);
/* 3787 */                               out.write("&period=-7')\">\n\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"Click to view last seven days data\"></a>\n\n\t\t</td>\n\n              </tr>\n              \n             ");
/*      */                             }
/*      */                             
/*      */ 
/* 3791 */                             if (Colur.equals("whitegrayborder"))
/*      */                             {
/* 3793 */                               Colur = "yellowgrayborder";
/*      */                             }
/*      */                             else
/*      */                             {
/* 3797 */                               Colur = "whitegrayborder";
/*      */                             }
/*      */                             
/* 3800 */                             out.write("\n\t        <tr> \n                <td height=\"20\" colspan=\"4\" align=\"right\" class=\"");
/* 3801 */                             out.print(Colur);
/* 3802 */                             out.write("\">\n\t              <img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3803 */                             out.print(resourceid);
/* 3804 */                             out.write("&attributeIDs=");
/* 3805 */                             out.print(ids.getProperty("Jobs in Minute"));
/* 3806 */                             out.write(44);
/* 3807 */                             out.print(ids.getProperty("Jobs in 15 Minutes"));
/* 3808 */                             out.write(44);
/* 3809 */                             out.print(ids.getProperty("Jobs in 5 Minutes"));
/* 3810 */                             out.write("&attributeToSelect=");
/* 3811 */                             out.print(ids.getProperty("Jobs in Minute"));
/* 3812 */                             out.write("&redirectto=");
/* 3813 */                             out.print(encodeurl);
/* 3814 */                             out.write("\" class=\"staticlinks\">");
/* 3815 */                             out.print(ALERTCONFIG_TEXT);
/* 3816 */                             out.write("</a>&nbsp;\n\t         </td>\n\t         </tr>\n\n\t</table>\n\t    </td>\n        </tr>\n      </table>-->\n      \n    <!--<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n      <tr> \n        <td>&nbsp;</td>\n      </tr>\n    </table>-->\n    ");
/*      */                           }
/*      */                           
/*      */ 
/* 3820 */                           if (showdata.equals("2"))
/*      */                           {
/* 3822 */                             String SPercent = null;
/* 3823 */                             String SValue = null;
/*      */                             
/* 3825 */                             out.write("\n    <!--<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t <tr>\n\t <td width=\"72%\" height=\"31\" class=\"tableheadingtrans\">Process Details <a\n\t name=\"Process\" id=\"Process\"></a></td>\n\t \t");
/* 3826 */                             if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                               return;
/* 3828 */                             out.write(10);
/* 3829 */                             out.write(9);
/* 3830 */                             out.write(9);
/*      */                             
/* 3832 */                             PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3833 */                             _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3834 */                             _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 3836 */                             _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 3837 */                             int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3838 */                             if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                               for (;;) {
/* 3840 */                                 out.write("\n\t\t<td width=\"28%\" height=\"31\" align=\"right\" class=\"tableheading\">&nbsp;<a href=\"javascript:void(0)\" class=\"bodytextboldwhiteun\" onClick=\"fnOpenNewWindow('../HostResource.do?getProcessList=true&resourceid=");
/* 3841 */                                 if (_jspx_meth_c_005fout_005f13(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                                   return;
/* 3843 */                                 out.write("&PercentMB=");
/* 3844 */                                 out.print(SPercent);
/* 3845 */                                 out.write("&MBValue=");
/* 3846 */                                 out.print(SValue);
/* 3847 */                                 out.write("&resType=");
/* 3848 */                                 out.print(resType);
/* 3849 */                                 out.write("')\">Add New Process</a>&nbsp;</td>\n          \n\t\t  ");
/* 3850 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3851 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3855 */                             if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3856 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                             }
/*      */                             
/* 3859 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3860 */                             out.write("\n\t </tr>\n\t </table>\n\t ");
/*      */                           }
/*      */                           
/*      */ 
/* 3864 */                           out.write(10);
/* 3865 */                           out.write(9);
/* 3866 */                           out.write(32);
/*      */                           
/* 3868 */                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3869 */                           _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 3870 */                           _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/* 3872 */                           _jspx_th_logic_005fnotEmpty_005f2.setName("process");
/* 3873 */                           int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 3874 */                           if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                             for (;;) {
/* 3876 */                               out.write("\n\t \t <form action=\"/HostResource.do?removeProcess=true\" name=\"removemonitor\" method=\"post\" style=\"display:inline\">\t \n\t <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\n\t <tr>\n\t <td colspan=\"12\" align=\"left\" class=\"columnheading\">\n\t  ");
/* 3877 */                               if (_jspx_meth_logic_005fpresent_005f4(_jspx_th_logic_005fnotEmpty_005f2, _jspx_page_context))
/*      */                                 return;
/* 3879 */                               out.write(10);
/* 3880 */                               out.write(9);
/* 3881 */                               out.write(32);
/* 3882 */                               if (_jspx_meth_logic_005fpresent_005f5(_jspx_th_logic_005fnotEmpty_005f2, _jspx_page_context))
/*      */                                 return;
/* 3884 */                               out.write("\n\t </tr>\n\t <tr>\n\t<input type=\"hidden\" name=\"haid\" value=\"");
/* 3885 */                               out.print(haid);
/* 3886 */                               out.write("\"/>\n\t<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 3887 */                               out.print(resourceid);
/* 3888 */                               out.write("\"/>\n\t<input type=\"hidden\" size=\"15\" name=\"configured\" value=\"true\"/>\n\t<input type=\"hidden\" name=\"appName\" value=\"");
/* 3889 */                               out.print(appname);
/* 3890 */                               out.write("\"/>\n\t<input type=\"hidden\" name=\"name\" value=\"");
/* 3891 */                               out.print(resourcename);
/* 3892 */                               out.write("\"/></td>\n    <td width=\"2%\" height=\"28\" class=\"columnheading\"><input type=\"checkbox\" name=\"headercheckbox\" onClick=\"javascript:fnSelectAll(this)\"></td>\t\t\n\t <td width=\"12%\" height=\"28\"  class=\"columnheading\"> Name</td>\n\t <td width=\"12%\" height=\"28\"  class=\"columnheading\">Process </td>\n\t <td width=\"20%\" height=\"28\"  class=\"columnheading\">Command</td>\n\t <td width=\"10%\" height=\"28\"  class=\"columnheading\">CPU(%)</td>\n\t <td width=\"10%\" height=\"28\"  class=\"columnheading\">Mem(%)</td>\t\t  \n\t <td width=\"10%\" height=\"28\"  class=\"columnheading\">Number Of Instances</td>\n\t <td width=\"12%\" height=\"28\"  class=\"columnheading\">Health<br></td>\n\t <td width=\"12%\" height=\"28\"  class=\"columnheading\">Availability<br></td>\n          \n         ");
/* 3893 */                               if (_jspx_meth_logic_005fpresent_005f6(_jspx_th_logic_005fnotEmpty_005f2, _jspx_page_context))
/*      */                                 return;
/* 3895 */                               out.write("\n     <td width=\"12%\" height=\"28\"  class=\"columnheading\">");
/* 3896 */                               out.print(ALERTCONFIG_TEXT);
/* 3897 */                               out.write("</td>\t  \n\t </tr>\n\t ");
/*      */                               
/* 3899 */                               IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3900 */                               _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 3901 */                               _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                               
/* 3903 */                               _jspx_th_logic_005fiterate_005f2.setName("process");
/*      */                               
/* 3905 */                               _jspx_th_logic_005fiterate_005f2.setId("row");
/*      */                               
/* 3907 */                               _jspx_th_logic_005fiterate_005f2.setIndexId("j");
/*      */                               
/* 3909 */                               _jspx_th_logic_005fiterate_005f2.setType("java.util.ArrayList");
/* 3910 */                               int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 3911 */                               if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 3912 */                                 ArrayList row = null;
/* 3913 */                                 Integer j = null;
/* 3914 */                                 if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 3915 */                                   out = _jspx_page_context.pushBody();
/* 3916 */                                   _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 3917 */                                   _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                                 }
/* 3919 */                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3920 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                 for (;;) {
/* 3922 */                                   out.write(10);
/* 3923 */                                   out.write(9);
/* 3924 */                                   out.write(32);
/*      */                                   
/* 3926 */                                   String processid = (String)row.get(0);
/* 3927 */                                   String processname = (String)row.get(1);
/* 3928 */                                   String process = (String)row.get(2);
/* 3929 */                                   String command = (String)row.get(3);
/* 3930 */                                   String instance1 = (String)row.get(4);
/* 3931 */                                   String pcpu = (String)row.get(5);
/* 3932 */                                   if ((pcpu == null) || (pcpu.equals("null")) || (pcpu.equals("NULL")) || (pcpu.equals("-1")) || (pcpu.equals("Idle")))
/*      */                                   {
/* 3934 */                                     pcpu = "-";
/*      */                                   }
/* 3936 */                                   String pmem = (String)row.get(6);
/* 3937 */                                   if ((pmem == null) || (pmem.equals("null")) || (pmem.equals("NULL")) || (pmem.equals("Process")))
/*      */                                   {
/* 3939 */                                     pmem = "-";
/*      */                                   }
/* 3941 */                                   if (resType.equals("HP-UX"))
/*      */                                   {
/*      */                                     try
/*      */                                     {
/* 3945 */                                       if (c == 0.0D)
/*      */                                       {
/* 3947 */                                         pmem = "-";
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 3951 */                                         double d1 = Double.parseDouble(pmem);
/* 3952 */                                         double e = d1 / c;
/* 3953 */                                         java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
/* 3954 */                                         pmem = df.format(e);
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Exception e) {}
/*      */                                   }
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/* 3963 */                                   String bgclass = "class=\"whitegrayborder\"";
/* 3964 */                                   if (j.intValue() % 2 == 0)
/*      */                                   {
/* 3966 */                                     bgclass = "class=\"whitegrayborder\"";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3970 */                                     bgclass = "class=\"yellowgrayborder\"";
/*      */                                   }
/*      */                                   
/* 3973 */                                   out.write("\n\t<tr>\t\n\t<td   ");
/* 3974 */                                   out.print(bgclass);
/* 3975 */                                   out.write(" ><input type=\"checkbox\" name=\"monitors\" value=\"");
/* 3976 */                                   out.print(processid);
/* 3977 */                                   out.write("\">\n\t </td>\n\t <td   ");
/* 3978 */                                   out.print(bgclass);
/* 3979 */                                   out.write(" > <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3980 */                                   out.print(processid);
/* 3981 */                                   out.write("&period=0&resourcename=");
/* 3982 */                                   out.print(processname);
/* 3983 */                                   out.write("')\" class=\"resourcename\">");
/* 3984 */                                   out.print(processname);
/* 3985 */                                   out.write("</a></td>\n\t <td   ");
/* 3986 */                                   out.print(bgclass);
/* 3987 */                                   out.write(" title=\"");
/* 3988 */                                   out.print(process);
/* 3989 */                                   out.write("\" > ");
/* 3990 */                                   out.print(getTrimmedText(process, 15));
/* 3991 */                                   out.write("</td>\n\t <td   ");
/* 3992 */                                   out.print(bgclass);
/* 3993 */                                   out.write(" title=\"");
/* 3994 */                                   out.print(command);
/* 3995 */                                   out.write("\" > ");
/* 3996 */                                   out.print(getTrimmedText(command, 15));
/* 3997 */                                   out.write("</td>\n\t <td   ");
/* 3998 */                                   out.print(bgclass);
/* 3999 */                                   out.write(" title=\"");
/* 4000 */                                   out.print(pcpu);
/* 4001 */                                   out.write("\" > ");
/* 4002 */                                   out.print(pcpu);
/* 4003 */                                   out.write("</td>\n\t <td   ");
/* 4004 */                                   out.print(bgclass);
/* 4005 */                                   out.write(" title=\"");
/* 4006 */                                   out.print(pmem);
/* 4007 */                                   out.write("\" > ");
/* 4008 */                                   out.print(getTrimmedText(pmem, 7));
/* 4009 */                                   out.write("</td>\n\t <td   ");
/* 4010 */                                   out.print(bgclass);
/* 4011 */                                   out.write(" title=\"");
/* 4012 */                                   out.print(instance1);
/* 4013 */                                   out.write("\" > ");
/* 4014 */                                   out.print(instance1);
/* 4015 */                                   out.write("</td>\n\t <td    ");
/* 4016 */                                   out.print(bgclass);
/* 4017 */                                   out.write("  ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4018 */                                   out.print(processid);
/* 4019 */                                   out.write("&attributeid=716')\">");
/* 4020 */                                   out.print(getSeverityImageForHealth(alert.getProperty(processid + "#" + "716")));
/* 4021 */                                   out.write("&nbsp;</a></td> \n\t<td    ");
/* 4022 */                                   out.print(bgclass);
/* 4023 */                                   out.write("  ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4024 */                                   out.print(processid);
/* 4025 */                                   out.write("&attributeid=715')\">");
/* 4026 */                                   out.print(getSeverityImageForAvailability(alert.getProperty(processid + "#" + "715")));
/* 4027 */                                   out.write("&nbsp;</a></td>\n         ");
/*      */                                   
/* 4029 */                                   PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4030 */                                   _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 4031 */                                   _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_logic_005fiterate_005f2);
/*      */                                   
/* 4033 */                                   _jspx_th_logic_005fpresent_005f7.setRole("ADMIN");
/* 4034 */                                   int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 4035 */                                   if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                     for (;;) {
/* 4037 */                                       out.write("\n\t<td    ");
/* 4038 */                                       out.print(bgclass);
/* 4039 */                                       out.write("  ><a href=\"/HostResource.do?editProcess=true&processid=");
/* 4040 */                                       out.print(processid);
/* 4041 */                                       out.write("&resourceid=");
/* 4042 */                                       out.print(resourceid);
/* 4043 */                                       out.write("&name=");
/* 4044 */                                       out.print(resourcename);
/* 4045 */                                       out.write("&haid=");
/* 4046 */                                       out.print(haid);
/* 4047 */                                       out.write("&appName=");
/* 4048 */                                       out.print(appname);
/* 4049 */                                       out.write("\"><img src=\"/images/icon_edit.gif\" title=\"Edit Process\"  border=\"0\"></a></td>\n           ");
/* 4050 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 4051 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4055 */                                   if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 4056 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                                   }
/*      */                                   
/* 4059 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 4060 */                                   out.write("\n\t<td    ");
/* 4061 */                                   out.print(bgclass);
/* 4062 */                                   out.write(" > <a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4063 */                                   out.print(processid);
/* 4064 */                                   out.write("&attributeIDs=715,716,717&attributeToSelect=715&redirectto=");
/* 4065 */                                   out.print(encodeurl);
/* 4066 */                                   out.write("'> <img src=\"/images/icon_associateaction.gif\" title=\"");
/* 4067 */                                   out.print(ALERTCONFIG_TEXT);
/* 4068 */                                   out.write("\" border=\"0\" ></a></td>\n\t</tr>\n\t");
/* 4069 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 4070 */                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 4071 */                                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 4072 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 4075 */                                 if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 4076 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 4079 */                               if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 4080 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                               }
/*      */                               
/* 4083 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 4084 */                               out.write("\n\t<tr><td colspan=\"12\" align=\"right\" class=\"tablebottom\">\n\t\t\n\t\t<a href=\"#top1\" class=\"staticlinks\">Top</a>&nbsp;&nbsp;</td>\n\t\t</tr>\n\t\t\n\t\t</table>\n\t</form>\n\t ");
/* 4085 */                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 4086 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4090 */                           if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 4091 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                           }
/*      */                           
/* 4094 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 4095 */                           out.write(10);
/* 4096 */                           out.write(9);
/* 4097 */                           out.write(32);
/* 4098 */                           if (_jspx_meth_logic_005fempty_005f2(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                             return;
/* 4100 */                           out.write("-->\n<!-- Code added GKM -->\t \t\n");
/*      */                           
/* 4102 */                           int widthofOuterTable = 99;
/* 4103 */                           int secondLevelTableWidth = 49;
/* 4104 */                           if (servicesInHost.size() == 1) {
/* 4105 */                             widthofOuterTable = 55;
/* 4106 */                             secondLevelTableWidth = 99;
/*      */                           }
/*      */                           
/*      */ 
/* 4110 */                           out.write("\n\n<br>\n    <table width=\"");
/* 4111 */                           out.print(widthofOuterTable);
/* 4112 */                           out.write("%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t <tr>\n\t      <td width=\"70%\" height=\"31\" class=\"tableheadingtrans\"> Monitors in this System </td>\n\t <td width=\"30%\" height=\"31\" class=\"tableheading\" align=\"right\">\n\t ");
/*      */                           
/* 4114 */                           PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4115 */                           _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 4116 */                           _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/* 4118 */                           _jspx_th_logic_005fpresent_005f8.setRole("ADMIN");
/* 4119 */                           int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 4120 */                           if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                             for (;;) {
/* 4122 */                               out.write("\n\t         <a class=\"bodytextboldwhiteun\" href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=Apache-server&haid=");
/* 4123 */                               out.print(request.getParameter("haid"));
/* 4124 */                               out.write("&addtoha=false&redirectto=");
/* 4125 */                               out.print(encoding);
/* 4126 */                               out.write("&host=");
/* 4127 */                               out.print(hostname);
/* 4128 */                               out.write("\" class=\"resourcename\">Add Monitors</a>&nbsp;\n\t \t\n\t ");
/* 4129 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 4130 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4134 */                           if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 4135 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                           }
/*      */                           
/* 4138 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4139 */                           out.write("\t\n\t </td>\n\t </tr>\n      </table>\n\t\n");
/* 4140 */                           if (servicesInHost.size() == 0) {
/* 4141 */                             out.write("\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrborder\">\n\t<tr>\n\t<td class=\"bodytext\" align=\"center\" height=\"26\"> \n \tNo monitors found on this server.");
/*      */                             
/* 4143 */                             PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4144 */                             _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 4145 */                             _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 4147 */                             _jspx_th_logic_005fpresent_005f9.setRole("ADMIN");
/* 4148 */                             int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 4149 */                             if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                               for (;;) {
/* 4151 */                                 out.write(" <a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=Apache-server&haid=");
/* 4152 */                                 out.print(request.getParameter("haid"));
/* 4153 */                                 out.write("&addtoha=false&redirectto=");
/* 4154 */                                 out.print(encoding);
/* 4155 */                                 out.write("&host=");
/* 4156 */                                 out.print(hostname);
/* 4157 */                                 out.write("\" class=\"resourcename\">Add monitors</a>&nbsp;\n        \n\t");
/* 4158 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 4159 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4163 */                             if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 4164 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                             }
/*      */                             
/* 4167 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 4168 */                             out.write("\n\t </td>\n\t ");
/*      */                             
/* 4170 */                             IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4171 */                             _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 4172 */                             _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 4174 */                             _jspx_th_c_005fif_005f13.setTest("${!empty DEMO}");
/* 4175 */                             int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 4176 */                             if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                               for (;;) {
/* 4178 */                                 out.write("\n  \t          <td class=\"bodytext\" align=\"center\" height=\"26\">\n  \t          No monitors found on this server. <a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=UrlMonitor&haid=");
/* 4179 */                                 out.print(request.getParameter("haid"));
/* 4180 */                                 out.write("&addtoha=false&redirectto=");
/* 4181 */                                 out.print(encoding);
/* 4182 */                                 out.write("&host=");
/* 4183 */                                 out.print(hostname);
/* 4184 */                                 out.write("\" class=\"resourcename\">Add monitors</a>&nbsp;\n  \t \n  \t          ");
/* 4185 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 4186 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4190 */                             if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 4191 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                             }
/*      */                             
/* 4194 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4195 */                             out.write("\n  \t \n  \t          </td>\n\t</tr>\n\t</table>\n\n\t\n");
/*      */                           }
/* 4197 */                           out.write("\n\n\n\n<table width=\"");
/* 4198 */                           out.print(widthofOuterTable);
/* 4199 */                           out.write("%\" border=\"0\" cellpadding=\"0\" cellspacing=\"10\" class=\"lrbborder\">\n<tr><td>\n<table WIDTH=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\n    ");
/*      */                           
/* 4201 */                           IterateTag _jspx_th_logic_005fiterate_005f3 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4202 */                           _jspx_th_logic_005fiterate_005f3.setPageContext(_jspx_page_context);
/* 4203 */                           _jspx_th_logic_005fiterate_005f3.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/* 4205 */                           _jspx_th_logic_005fiterate_005f3.setName("servicesinhost");
/*      */                           
/* 4207 */                           _jspx_th_logic_005fiterate_005f3.setId("row");
/*      */                           
/* 4209 */                           _jspx_th_logic_005fiterate_005f3.setIndexId("j");
/*      */                           
/* 4211 */                           _jspx_th_logic_005fiterate_005f3.setType("java.util.Map");
/* 4212 */                           int _jspx_eval_logic_005fiterate_005f3 = _jspx_th_logic_005fiterate_005f3.doStartTag();
/* 4213 */                           if (_jspx_eval_logic_005fiterate_005f3 != 0) {
/* 4214 */                             Map row = null;
/* 4215 */                             Integer j = null;
/* 4216 */                             if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 4217 */                               out = _jspx_page_context.pushBody();
/* 4218 */                               _jspx_th_logic_005fiterate_005f3.setBodyContent((BodyContent)out);
/* 4219 */                               _jspx_th_logic_005fiterate_005f3.doInitBody();
/*      */                             }
/* 4221 */                             row = (Map)_jspx_page_context.findAttribute("row");
/* 4222 */                             j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                             for (;;) {
/* 4224 */                               out.write(" \n    ");
/*      */                               
/* 4226 */                               String monitorid = (String)row.get("RESOURCEID");
/* 4227 */                               String bgclass = "class=\"whitegrayborder\"";
/* 4228 */                               if (j.intValue() % 2 == 0)
/*      */                               {
/* 4230 */                                 bgclass = "class=\"whitegrayborder\"";
/*      */                               }
/*      */                               else
/*      */                               {
/* 4234 */                                 bgclass = "class=\"yellowgrayborder\"";
/*      */                               }
/*      */                               
/*      */ 
/* 4238 */                               String resourcetype = (String)row.get("TYPE");
/* 4239 */                               Object availabilityid = availabilitykeys.get(resourcetype);
/* 4240 */                               Object healthid = healthkeys.get(resourcetype);
/*      */                               
/* 4242 */                               boolean isLeftBox = j.intValue() % 2 == 0;
/* 4243 */                               boolean isLastBox = j.intValue() == servicesInHost.size() - 1;
/*      */                               
/*      */ 
/*      */ 
/* 4247 */                               out.write(10);
/* 4248 */                               out.write(10);
/*      */                               
/* 4250 */                               if (isLeftBox) {}
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4259 */                               out.write("\t\t\t\n\t\t\n\t    \t\t\n      \n      <td  width=\"");
/* 4260 */                               out.print(secondLevelTableWidth);
/* 4261 */                               out.write("%\">\n  \t<table WIDTH=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"grayfullborder\">\n          <tr> \n\t\t  ");
/*      */                               
/* 4263 */                               int a = 1;
/*      */                               try
/*      */                               {
/* 4266 */                                 a = Integer.parseInt(alert.getProperty(monitorid + "#" + availabilityid));
/*      */                               }
/*      */                               catch (Exception e)
/*      */                               {
/* 4270 */                                 e.printStackTrace();
/*      */                               }
/* 4272 */                               if (a == 1)
/*      */                               {
/* 4274 */                                 out.write("\n             <td height=\"20\" colspan=\"3\" class=\"monitorinfoeven1\">\n\t\t\t ");
/*      */                               }
/*      */                               else
/*      */                               {
/* 4278 */                                 out.write("\n\t\t\t  <td height=\"20\" colspan=\"3\" class=\"monitorinfoeven\">\n\t\t\t  ");
/*      */                               }
/* 4280 */                               out.write("\n\t\t\t <b>Name </b>: <a href=\"/showresource.do?haid=");
/* 4281 */                               if (_jspx_meth_c_005fout_005f14(_jspx_th_logic_005fiterate_005f3, _jspx_page_context))
/*      */                                 return;
/* 4283 */                               out.write("&resourceid=");
/* 4284 */                               out.print(monitorid);
/* 4285 */                               out.write("&resourcename=");
/* 4286 */                               out.print(row.get("RESOURCENAME"));
/* 4287 */                               out.write("&type=");
/* 4288 */                               out.print(row.get("TYPE"));
/* 4289 */                               out.write("&method=showdetails&moname=");
/* 4290 */                               out.print(row.get("RESOURCENAME"));
/* 4291 */                               out.write("\" \n              class=\"resourcename\" title=\"");
/* 4292 */                               out.print((String)row.get("DISPLAYNAME"));
/* 4293 */                               out.write(34);
/* 4294 */                               out.write(62);
/* 4295 */                               out.print(getTrimmedText((String)row.get("DISPLAYNAME"), 40));
/* 4296 */                               out.write("</a> \n            </td>\n          </tr>\n\t\t  <tr>\n\t\t  ");
/*      */                               
/* 4298 */                               int b = 1;
/*      */                               try
/*      */                               {
/* 4301 */                                 b = Integer.parseInt(alert.getProperty(monitorid + "#" + availabilityid));
/*      */                               }
/*      */                               catch (Exception e)
/*      */                               {
/* 4305 */                                 e.printStackTrace();
/*      */                               }
/* 4307 */                               if (b == 1)
/*      */                               {
/* 4309 */                                 out.write("\n\t          <td colspan=\"3\" class=\"monitorinfoevenborder1\">\n\t\t    ");
/*      */                               }
/*      */                               else
/*      */                               {
/* 4313 */                                 out.write("\n\t          <td colspan=\"3\" class=\"monitorinfoevenborder\">\n\t\t     ");
/*      */                               }
/* 4315 */                               out.write("\n\n            <b>Type </b>: ");
/* 4316 */                               out.print(motypedisplaynames.get(row.get("TYPE")));
/* 4317 */                               out.write(" </td>\n          </tr>\n          <tr valign=\"top\"> \n            <td width=\"45%\"><table WIDTH=\"65%\" valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                <tr> \n                  <td align=\"center\" valign=\"top\"  class=\"bodytextbold\" height=10>&nbsp;</td>\n                </tr>\n                <tr> \n                  <td align=\"center\" valign=\"top\"  class=\"bodytextbold\">Today's \n                    Availability</td>\n                </tr>\n                <tr> \n                  <td rowspan=\"4\" colspan=\"1\" width=\"200\"> <a href=\"/showresource.do?haid=");
/* 4318 */                               if (_jspx_meth_c_005fout_005f15(_jspx_th_logic_005fiterate_005f3, _jspx_page_context))
/*      */                                 return;
/* 4320 */                               out.write("&resourceid=");
/* 4321 */                               out.print(monitorid);
/* 4322 */                               out.write("&resourcename=");
/* 4323 */                               out.print(row.get("RESOURCENAME"));
/* 4324 */                               out.write("&type=");
/* 4325 */                               out.print(row.get("TYPE"));
/* 4326 */                               out.write("&method=showdetails&moname=");
/* 4327 */                               out.print(row.get("RESOURCENAME"));
/* 4328 */                               out.write("\"> \n                    ");
/* 4329 */                               wlsGraph.setParam(monitorid, "AVAILABILITY");
/* 4330 */                               out.write("\n                    ");
/* 4331 */                               if (_jspx_meth_awolf_005fpiechart_005f1(_jspx_th_logic_005fiterate_005f3, _jspx_page_context))
/*      */                                 return;
/* 4333 */                               out.write(" \n                  </td>\n                </tr>\n              </table> </td>\n            <td width=\"1\" class=\"dotverticalborder\"><img src=\"/images/spacer.gif\" height=\"10\" width=\"1\"></td>\n            <td width=\"55%\" height=\"100%\"> <table  WIDTH=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                <tr> \n                  <td height=\"88\"  valign=\"top\"> \n                    <table WIDTH=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                      <tr> \n                        <td align=\"center\" valign=\"top\"  height=10 class=\"bodytextbold\">&nbsp;</td>\n                      </tr>\n                      <tr> \n                        <td width=\"50%\"  align=\"center\" valign=\"top\"  class=\"bodytextbold\">Response \n                          Time</td>\n                      </tr>\n                      <tr> \n                        <td align=\"center\" height=50>\n                          <font class=\"textResponseTime\"> \n                          ");
/*      */                               
/* 4335 */                               if ((row.get("RESPONSETIME") == null) || ("-1".equals(row.get("RESPONSETIME"))) || ("null".equals(row.get("RESPONSETIME")))) {
/* 4336 */                                 out.println("-");
/*      */                               } else {
/* 4338 */                                 out.println(row.get("RESPONSETIME") + " ms");
/*      */                               }
/*      */                               
/* 4341 */                               out.write("\n                          </font></font></td>\n                      </tr>\n                    </table></td>\n                </tr>\n                <tr> \n                  <td height=\"1\" valign=\"top\" class=\"dothorizontalborder\"><img src=\"/images/spacer.gif\" height=\"1\" width=\"10\"></td>\n                </tr>\n                <tr> \n                  <td height=\"50%\"  valign=\"top\"> \n                    <table WIDTH=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"5\" class=\"ttborder\">\n                      <tr> \n                        <td class=\"bodytext\">&nbsp;</td>\n                      </tr>\n                      <tr> \n                        <td class=\"bodytext\">Availability : <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4342 */                               out.print(monitorid);
/* 4343 */                               out.write("&attributeid=");
/* 4344 */                               out.print(availabilityid);
/* 4345 */                               out.write("')\">");
/* 4346 */                               out.print(getSeverityImageForAvailability(alert.getProperty(monitorid + "#" + availabilityid)));
/* 4347 */                               out.write("</a> \n                        </td>\n\t\t                </tr>\n                        <tr> \n                        <td class=\"bodytext\">Health : <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4348 */                               out.print(monitorid);
/* 4349 */                               out.write("&attributeid=");
/* 4350 */                               out.print(healthid);
/* 4351 */                               out.write("')\">");
/* 4352 */                               out.print(getSeverityImageForHealth(alert.getProperty(monitorid + "#" + healthid)));
/* 4353 */                               out.write("</a> \n                        </td>\n\t\t\t\t\t\t</tr>\n                    </table></td>\n                </tr>\n              </table></td>\n          </tr>\n        </table>\n        \n       </td>\n\n");
/*      */                               
/* 4355 */                               if ((isLastBox) && (isLeftBox)) {
/* 4356 */                                 out.println("<td>&nbsp;</td></tr>");
/*      */                               }
/*      */                               
/* 4359 */                               if (!isLeftBox) {
/* 4360 */                                 out.println("</tr>");
/*      */                               }
/*      */                               
/* 4363 */                               out.write("\t\t\t\n\n    ");
/* 4364 */                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f3.doAfterBody();
/* 4365 */                               row = (Map)_jspx_page_context.findAttribute("row");
/* 4366 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/* 4367 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 4370 */                             if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 4371 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 4374 */                           if (_jspx_th_logic_005fiterate_005f3.doEndTag() == 5) {
/* 4375 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3); return;
/*      */                           }
/*      */                           
/* 4378 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3);
/* 4379 */                           out.write(" \n  \n</table></td></tr></table>  \n");
/* 4380 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 4381 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4385 */                       if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 4386 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                       }
/*      */                       
/* 4389 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4390 */                       out.write(10);
/* 4391 */                       if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*      */                         return;
/* 4393 */                       out.write(10);
/* 4394 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 4395 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4399 */                   if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 4400 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                   }
/*      */                   
/* 4403 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4404 */                   out.write("\n  <!-- Code added Ends GKM -->\n");
/*      */                 }
/* 4406 */                 out.write("\n</td></tr></table>\n");
/*      */               }
/* 4408 */               out.write(10);
/* 4409 */               out.write(10);
/* 4410 */               out.write(10);
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4418 */               out.write("\n\n\n<br>\n<!--");
/*      */               
/* 4420 */               IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4421 */               _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 4422 */               _jspx_th_c_005fif_005f15.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 4424 */               _jspx_th_c_005fif_005f15.setTest("${!empty url_ids}");
/* 4425 */               int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 4426 */               if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                 for (;;) {
/* 4428 */                   out.write(" \n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t <tr>\n\t <td width=\"100%\" height=\"31\" class=\"tableheadingtrans\">URL Details</td>\n\t </tr>\n         <tr>\n         <td>\n         <table  border=0 cellspacing=0 cellpadding=0 valign=center width=\"100%\" class=\"lrbtborder\">\n              <tr> \n                <td width=\"30%\" class=\"columnheadingnotop\">URL Name</td>\n                <td width=\"13%\" class=\"columnheadingnotop\">Availability</td>\n                <td width=\"10%\" class=\"columnheadingnotop\">Health</td>\n                <td width=\"16%\" class=\"columnheadingnotop\">Response Time</td>\n                <td width=\"13%\" class=\"columnheadingnotop\">Page Size</td>\n                <td width=\"12%\" height=\"28\"  class=\"columnheading\">");
/* 4429 */                   out.print(ALERTCONFIG_TEXT);
/* 4430 */                   out.write("</td>\n              </tr>\n            \n");
/*      */                   
/* 4432 */                   IterateTag _jspx_th_logic_005fiterate_005f4 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4433 */                   _jspx_th_logic_005fiterate_005f4.setPageContext(_jspx_page_context);
/* 4434 */                   _jspx_th_logic_005fiterate_005f4.setParent(_jspx_th_c_005fif_005f15);
/*      */                   
/* 4436 */                   _jspx_th_logic_005fiterate_005f4.setName("url_ids");
/*      */                   
/* 4438 */                   _jspx_th_logic_005fiterate_005f4.setId("attribute");
/*      */                   
/* 4440 */                   _jspx_th_logic_005fiterate_005f4.setIndexId("j");
/*      */                   
/* 4442 */                   _jspx_th_logic_005fiterate_005f4.setType("java.lang.String");
/* 4443 */                   int _jspx_eval_logic_005fiterate_005f4 = _jspx_th_logic_005fiterate_005f4.doStartTag();
/* 4444 */                   if (_jspx_eval_logic_005fiterate_005f4 != 0) {
/* 4445 */                     String attribute = null;
/* 4446 */                     Integer j = null;
/* 4447 */                     if (_jspx_eval_logic_005fiterate_005f4 != 1) {
/* 4448 */                       out = _jspx_page_context.pushBody();
/* 4449 */                       _jspx_th_logic_005fiterate_005f4.setBodyContent((BodyContent)out);
/* 4450 */                       _jspx_th_logic_005fiterate_005f4.doInitBody();
/*      */                     }
/* 4452 */                     attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4453 */                     j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                     for (;;) {
/* 4455 */                       out.write(10);
/*      */                       
/* 4457 */                       String url_query = "select * from AM_ManagedObject where RESOURCEID=" + attribute;
/* 4458 */                       String monitorname2 = null;
/* 4459 */                       String displayname2 = null;
/* 4460 */                       ManagedApplication mo = new ManagedApplication();
/* 4461 */                       AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */                       try
/*      */                       {
/* 4464 */                         ResultSet rs = AMConnectionPool.executeQueryStmt(url_query);
/* 4465 */                         if (rs.next())
/*      */                         {
/* 4467 */                           monitorname2 = rs.getString("DISPLAYNAME");
/*      */                         }
/*      */                         try
/*      */                         {
/* 4471 */                           rs.close();
/*      */                         }
/*      */                         catch (Exception exc) {}
/*      */                       }
/*      */                       catch (Exception exc) {}
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4482 */                       String border = "whitegrayborder";
/* 4483 */                       if (j.intValue() % 2 == 0)
/*      */                       {
/* 4485 */                         border = "whitegrayborder";
/*      */                       }
/*      */                       else
/*      */                       {
/* 4489 */                         border = "yellowgrayborder";
/*      */                       }
/* 4491 */                       ArrayList rows = mo.getRows("select max(collectiontime) from AM_ManagedObjectData where resid=" + attribute);
/* 4492 */                       String temp = null;
/* 4493 */                       String lastdatacollectedtime = null;
/* 4494 */                       if (rows.size() > 0)
/*      */                       {
/* 4496 */                         rows = (ArrayList)rows.get(0);
/* 4497 */                         temp = (String)rows.get(0);
/* 4498 */                         if (temp == null)
/*      */                         {
/* 4500 */                           temp = null;
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/* 4505 */                           lastdatacollectedtime = (String)rows.get(0);
/*      */                         }
/*      */                       }
/* 4508 */                       if (lastdatacollectedtime != null) {
/* 4509 */                         request.setAttribute("lastdatacollectedtime", new java.util.Date(Long.parseLong(lastdatacollectedtime)));
/*      */                       }
/* 4511 */                       rows = mo.getRows("select RESPONSETIME from AM_ManagedObjectData where COLLECTIONTIME=" + lastdatacollectedtime);
/* 4512 */                       String responseTime = null;
/* 4513 */                       if (rows.size() > 0)
/*      */                       {
/* 4515 */                         rows = (ArrayList)rows.get(0);
/* 4516 */                         responseTime = (String)rows.get(0);
/*      */                       }
/*      */                       
/* 4519 */                       String query1 = "select CONTENTLENGTH,COLLECTIONTIME from AM_URLData where URLID=" + attribute + " ORDER BY COLLECTIONTIME DESC LIMIT 1";
/* 4520 */                       String contentlength = "-";
/*      */                       try
/*      */                       {
/* 4523 */                         ResultSet rs = AMConnectionPool.executeQueryStmt(query1);
/* 4524 */                         if (rs.next())
/*      */                         {
/*      */                           try
/*      */                           {
/* 4528 */                             long temp1 = Integer.parseInt(rs.getString("CONTENTLENGTH"));
/* 4529 */                             contentlength = String.valueOf(temp1) + " bytes";
/*      */                           }
/*      */                           catch (NumberFormatException exc)
/*      */                           {
/* 4533 */                             exc.printStackTrace();
/*      */                           }
/*      */                         }
/*      */                         try
/*      */                         {
/* 4538 */                           rs.close();
/*      */                         }
/*      */                         catch (Exception exc) {}
/*      */                       }
/*      */                       catch (Exception exc3) {}
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4548 */                       pageContext.setAttribute("url_contentlength", contentlength);
/* 4549 */                       String type_query = "select type from AM_ManagedObject where resourceid=" + attribute;
/* 4550 */                       String resource_type = null;
/* 4551 */                       String health_id = null;
/* 4552 */                       String avail_id = null;
/* 4553 */                       String health_ids = null;
/*      */                       
/*      */                       try
/*      */                       {
/* 4557 */                         ResultSet rs = AMConnectionPool.executeQueryStmt(type_query);
/* 4558 */                         if (rs.next())
/*      */                         {
/* 4560 */                           resource_type = rs.getString("type");
/*      */                         }
/*      */                         try
/*      */                         {
/* 4564 */                           rs.close();
/*      */                         }
/*      */                         catch (Exception exc4) {}
/*      */                       }
/*      */                       catch (Exception exc2) {}
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/* 4573 */                       if (resource_type.equals("UrlMonitor"))
/*      */                       {
/* 4575 */                         health_id = "401";
/* 4576 */                         avail_id = "400";
/* 4577 */                         health_ids = "401,402";
/*      */                       }
/*      */                       else
/*      */                       {
/* 4581 */                         health_id = "405";
/* 4582 */                         avail_id = "404";
/* 4583 */                         health_ids = "405";
/* 4584 */                         responseTime = com.adventnet.appmanager.hostresource.struts.HostResourceAction.getSeqresponsetime(attribute);
/*      */                       }
/* 4586 */                       pageContext.setAttribute("url_responsetime", responseTime);
/*      */                       
/* 4588 */                       out.write("\n<tr>\n<td height=\"25\" width=\"30%\" class=\"");
/* 4589 */                       out.print(border);
/* 4590 */                       out.write("\"><a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 4591 */                       out.print(attribute);
/* 4592 */                       out.write("\" class=\"staticlinks\">");
/* 4593 */                       out.print(monitorname2);
/* 4594 */                       out.write("</a></td>\n<td height=\"25\" width=\"13%\" class=\"");
/* 4595 */                       out.print(border);
/* 4596 */                       out.write("\"> <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4597 */                       out.print(attribute);
/* 4598 */                       out.write("&attributeid=");
/* 4599 */                       out.print(avail_id);
/* 4600 */                       out.write("')\"> \n");
/* 4601 */                       out.print(getSeverityImageForAvailability(alert.getProperty(attribute + "#" + avail_id)));
/* 4602 */                       out.write("</a> \n</td>\n<td height=\"25\" width=\"7%\" class=\"");
/* 4603 */                       out.print(border);
/* 4604 */                       out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4605 */                       out.print(attribute);
/* 4606 */                       out.write("&attributeid=");
/* 4607 */                       out.print(health_id);
/* 4608 */                       out.write("')\">");
/* 4609 */                       out.print(getSeverityImageForHealth(alert.getProperty(attribute + "#" + health_id)));
/* 4610 */                       out.write("</a>\n\n</td>\n<td class=\"");
/* 4611 */                       out.print(border);
/* 4612 */                       out.write("\" width=\"13%\">\n");
/* 4613 */                       if (_jspx_meth_c_005fif_005f16(_jspx_th_logic_005fiterate_005f4, _jspx_page_context))
/*      */                         return;
/* 4615 */                       out.write("\n           ");
/* 4616 */                       if (_jspx_meth_c_005fif_005f17(_jspx_th_logic_005fiterate_005f4, _jspx_page_context))
/*      */                         return;
/* 4618 */                       out.write("           \n           ");
/* 4619 */                       if (_jspx_meth_c_005fif_005f18(_jspx_th_logic_005fiterate_005f4, _jspx_page_context))
/*      */                         return;
/* 4621 */                       out.write(" \n</td>\n<td class=\"");
/* 4622 */                       out.print(border);
/* 4623 */                       out.write(34);
/* 4624 */                       out.write(62);
/* 4625 */                       if (_jspx_meth_c_005fout_005f19(_jspx_th_logic_005fiterate_005f4, _jspx_page_context))
/*      */                         return;
/* 4627 */                       out.write("\n</td>\n<td height=\"25\" align=\"center\" class=\"");
/* 4628 */                       out.print(border);
/* 4629 */                       out.write("\"><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4630 */                       out.print(attribute);
/* 4631 */                       out.write("&attributeIDs=");
/* 4632 */                       out.print(avail_id);
/* 4633 */                       out.write(44);
/* 4634 */                       out.print(health_ids);
/* 4635 */                       out.write("&attributeToSelect=");
/* 4636 */                       out.print(avail_id);
/* 4637 */                       out.write("&redirectto=");
/* 4638 */                       out.print(encodeurl);
/* 4639 */                       out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" alt=\"Down\" width=\"20\" height=\"20\" style=\"width: 13px; height: 13px;\" border=\"0\" title=\"\"></a></td>\n</tr>\n");
/* 4640 */                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f4.doAfterBody();
/* 4641 */                       attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4642 */                       j = (Integer)_jspx_page_context.findAttribute("j");
/* 4643 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 4646 */                     if (_jspx_eval_logic_005fiterate_005f4 != 1) {
/* 4647 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 4650 */                   if (_jspx_th_logic_005fiterate_005f4.doEndTag() == 5) {
/* 4651 */                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f4); return;
/*      */                   }
/*      */                   
/* 4654 */                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f4);
/* 4655 */                   out.write("\n         </table>\n         </td>\n         </tr>\n</table>\n");
/* 4656 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4657 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4661 */               if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4662 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */               }
/*      */               
/* 4665 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4666 */               out.write("\n<br>\n\t ");
/*      */               
/* 4668 */               IterateTag _jspx_th_logic_005fiterate_005f5 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4669 */               _jspx_th_logic_005fiterate_005f5.setPageContext(_jspx_page_context);
/* 4670 */               _jspx_th_logic_005fiterate_005f5.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 4672 */               _jspx_th_logic_005fiterate_005f5.setName("script_ids");
/*      */               
/* 4674 */               _jspx_th_logic_005fiterate_005f5.setId("attribute");
/*      */               
/* 4676 */               _jspx_th_logic_005fiterate_005f5.setIndexId("j");
/*      */               
/* 4678 */               _jspx_th_logic_005fiterate_005f5.setType("java.lang.String");
/* 4679 */               int _jspx_eval_logic_005fiterate_005f5 = _jspx_th_logic_005fiterate_005f5.doStartTag();
/* 4680 */               if (_jspx_eval_logic_005fiterate_005f5 != 0) {
/* 4681 */                 String attribute = null;
/* 4682 */                 Integer j = null;
/* 4683 */                 if (_jspx_eval_logic_005fiterate_005f5 != 1) {
/* 4684 */                   out = _jspx_page_context.pushBody();
/* 4685 */                   _jspx_th_logic_005fiterate_005f5.setBodyContent((BodyContent)out);
/* 4686 */                   _jspx_th_logic_005fiterate_005f5.doInitBody();
/*      */                 }
/* 4688 */                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4689 */                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                 for (;;) {
/* 4691 */                   out.write(10);
/* 4692 */                   out.write(9);
/* 4693 */                   out.write(32);
/*      */                   
/* 4695 */                   String query = "select scriptname,displayname from AM_ScriptArgs where resourceid=" + attribute;
/* 4696 */                   String monitorname1 = null;
/* 4697 */                   String displayname1 = null;
/*      */                   try
/*      */                   {
/* 4700 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4701 */                     ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 4702 */                     if (rs.next())
/*      */                     {
/* 4704 */                       monitorname1 = rs.getString("scriptname");
/* 4705 */                       displayname1 = rs.getString("displayname");
/*      */                     }
/* 4707 */                     rs.close();
/*      */                   }
/*      */                   catch (Exception exc) {}
/*      */                   
/*      */ 
/* 4712 */                   String url2 = "/showresource.do?resourceid=" + attribute + "&type=Script Monitor&moname=" + monitorname1 + "&method=showScriptMonitorDetails&resourcename=" + displayname1 + "&fromhost=true";
/* 4713 */                   String url3 = "/jsp/HostScript.jsp?resourceid=" + attribute + "&type=Script Monitor&moname=" + monitorname1 + "&method=showScriptMonitorDetails&resourcename=" + displayname1 + "&fromhost=true&hostid=" + resourceid;
/*      */                   
/* 4715 */                   out.write("\n         <table  width=\"99%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n\t ");
/* 4716 */                   JspRuntimeLibrary.include(request, response, url2, out, false);
/* 4717 */                   out.write(" \n         <tr>\n         <td width=\"99%\"   class=\"tableheadingtrans\" >\n         <a href=\"showresource.do?method=showResourceForResourceID&resourceid=");
/* 4718 */                   out.print(attribute);
/* 4719 */                   out.write("\" class=\"staticlinks\">Script Montior - ");
/* 4720 */                   out.print(displayname1);
/* 4721 */                   out.write("</a>\n         </td>\n         </tr>\n         <tr>\n         <td>\n         ");
/* 4722 */                   JspRuntimeLibrary.include(request, response, url3, out, false);
/* 4723 */                   out.write("\n         </td>\n         </tr>\n         <br>\n         </table>\n         <br>\n         ");
/* 4724 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f5.doAfterBody();
/* 4725 */                   attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4726 */                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 4727 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 4730 */                 if (_jspx_eval_logic_005fiterate_005f5 != 1) {
/* 4731 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 4734 */               if (_jspx_th_logic_005fiterate_005f5.doEndTag() == 5) {
/* 4735 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f5); return;
/*      */               }
/*      */               
/* 4738 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f5);
/* 4739 */               out.write("-->\n\t <br>\n\n\n");
/* 4740 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f5.doAfterBody();
/* 4741 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 4744 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 4745 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 4748 */           if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 4749 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5); return;
/*      */           }
/*      */           
/* 4752 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5);
/* 4753 */           out.write(10);
/* 4754 */           if (_jspx_meth_tiles_005fput_005f6(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 4756 */           out.write(10);
/* 4757 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4758 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4762 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4763 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 4766 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4767 */         out.write("\n</body>\n</html>\n");
/*      */       }
/* 4769 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4770 */         out = _jspx_out;
/* 4771 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4772 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4773 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4776 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4782 */     PageContext pageContext = _jspx_page_context;
/* 4783 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4785 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 4786 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 4787 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 4789 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 4790 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 4792 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 4793 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 4795 */           out.write(10);
/* 4796 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 4797 */             return true;
/* 4798 */           out.write(10);
/* 4799 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 4800 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4804 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 4805 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4808 */         int tmp177_176 = 0; int[] tmp177_174 = _jspx_push_body_count_c_005fcatch_005f0; int tmp179_178 = tmp177_174[tmp177_176];tmp177_174[tmp177_176] = (tmp179_178 - 1); if (tmp179_178 <= 0) break;
/* 4809 */         out = _jspx_page_context.popBody(); }
/* 4810 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 4812 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 4813 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 4815 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 4820 */     PageContext pageContext = _jspx_page_context;
/* 4821 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4823 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 4824 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 4825 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 4827 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 4829 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 4830 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 4831 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 4832 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4833 */       return true;
/*      */     }
/* 4835 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4836 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4841 */     PageContext pageContext = _jspx_page_context;
/* 4842 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4844 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4845 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 4846 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4848 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 4850 */     _jspx_th_tiles_005fput_005f0.setValue("");
/* 4851 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 4852 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 4853 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4854 */       return true;
/*      */     }
/* 4856 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4857 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4862 */     PageContext pageContext = _jspx_page_context;
/* 4863 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4865 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4866 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 4867 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4869 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 4870 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 4871 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 4873 */         out.write(10);
/* 4874 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 4875 */           return true;
/* 4876 */         out.write(10);
/* 4877 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 4878 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4882 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4883 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4884 */       return true;
/*      */     }
/* 4886 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4887 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4892 */     PageContext pageContext = _jspx_page_context;
/* 4893 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4895 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4896 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4897 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4899 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4901 */     _jspx_th_tiles_005fput_005f1.setValue("");
/* 4902 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4903 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4904 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4905 */       return true;
/*      */     }
/* 4907 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4908 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4913 */     PageContext pageContext = _jspx_page_context;
/* 4914 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4916 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4917 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 4918 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4920 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 4921 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 4922 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 4924 */         out.write(10);
/* 4925 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 4926 */           return true;
/* 4927 */         out.write(10);
/* 4928 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4929 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4933 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4934 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4935 */       return true;
/*      */     }
/* 4937 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4938 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4943 */     PageContext pageContext = _jspx_page_context;
/* 4944 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4946 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4947 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 4948 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4950 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 4952 */     _jspx_th_tiles_005fput_005f2.setValue("");
/* 4953 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 4954 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 4955 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4956 */       return true;
/*      */     }
/* 4958 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4959 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4964 */     PageContext pageContext = _jspx_page_context;
/* 4965 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4967 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 4968 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 4969 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4971 */     _jspx_th_tiles_005fput_005f3.setName("LeftArea");
/*      */     
/* 4973 */     _jspx_th_tiles_005fput_005f3.setType("string");
/* 4974 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 4975 */     if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 4976 */       if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4977 */         out = _jspx_page_context.pushBody();
/* 4978 */         _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 4979 */         _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4982 */         out.write(10);
/* 4983 */         int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 4984 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4987 */       if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4988 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4991 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4992 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 4993 */       return true;
/*      */     }
/* 4995 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 4996 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5001 */     PageContext pageContext = _jspx_page_context;
/* 5002 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5004 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5005 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 5006 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5008 */     _jspx_th_tiles_005fput_005f4.setName("ServerLeftArea");
/*      */     
/* 5010 */     _jspx_th_tiles_005fput_005f4.setValue("");
/* 5011 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 5012 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 5013 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 5014 */       return true;
/*      */     }
/* 5016 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 5017 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5022 */     PageContext pageContext = _jspx_page_context;
/* 5023 */     JspWriter out = _jspx_page_context.getOut();
/* 5024 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 5025 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 5027 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5028 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 5029 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 5031 */     _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 5032 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 5033 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 5035 */         out.write(10);
/* 5036 */         if (_jspx_meth_c_005fif_005f2(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/* 5037 */           return true;
/* 5038 */         out.write(10);
/* 5039 */         if (_jspx_meth_c_005fif_005f3(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/* 5040 */           return true;
/* 5041 */         out.write(10);
/* 5042 */         if (_jspx_meth_c_005fif_005f4(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/* 5043 */           return true;
/* 5044 */         out.write(10);
/* 5045 */         if (_jspx_meth_c_005fif_005f5(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/* 5046 */           return true;
/* 5047 */         out.write(10);
/* 5048 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 5049 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5053 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 5054 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5055 */       return true;
/*      */     }
/* 5057 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5058 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5063 */     PageContext pageContext = _jspx_page_context;
/* 5064 */     JspWriter out = _jspx_page_context.getOut();
/* 5065 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 5066 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 5068 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5069 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 5070 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 5072 */     _jspx_th_c_005fif_005f2.setTest("${!empty param.showconfigdiv && param.showconfigdiv=='true'}");
/* 5073 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 5074 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 5076 */         out.write("\n<div id=\"edit\" style=\"DISPLAY: block\">\n\n");
/* 5077 */         JspRuntimeLibrary.include(request, response, "/jsp/HostResourceConfig.jsp?reconfigure=true", out, false);
/* 5078 */         out.write("\n\n<br>\n</div>\n");
/* 5079 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5080 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5084 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5085 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5086 */       return true;
/*      */     }
/* 5088 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5089 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5094 */     PageContext pageContext = _jspx_page_context;
/* 5095 */     JspWriter out = _jspx_page_context.getOut();
/* 5096 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 5097 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 5099 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5100 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 5101 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 5103 */     _jspx_th_c_005fif_005f3.setTest("${empty param.showconfigdiv && param.showconfigdiv!='true'}");
/* 5104 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 5105 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 5107 */         out.write("\n<div id=\"edit\" style=\"DISPLAY: none\">\n\n");
/* 5108 */         JspRuntimeLibrary.include(request, response, "/HostResource.do?reconfigure=true&include=true&configure=false", out, false);
/* 5109 */         out.write(32);
/* 5110 */         out.write(10);
/* 5111 */         JspRuntimeLibrary.include(request, response, "/jsp/HostResourceConfig.jsp?reconfigure=true", out, false);
/* 5112 */         out.write("\n\n<br>\n</div>\n");
/* 5113 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 5114 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5118 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 5119 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5120 */       return true;
/*      */     }
/* 5122 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5123 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5128 */     PageContext pageContext = _jspx_page_context;
/* 5129 */     JspWriter out = _jspx_page_context.getOut();
/* 5130 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 5131 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 5133 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5134 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 5135 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 5137 */     _jspx_th_c_005fif_005f4.setTest("${!empty param.showadddiv && param.showadddiv=='true'}");
/* 5138 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 5139 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 5141 */         out.write("\n<div id=\"addprocess\" style=\"DISPLAY: block\">\n");
/* 5142 */         JspRuntimeLibrary.include(request, response, "/jsp/HostResourceConfig.jsp?addProcesScreen=true&addProcess=true", out, false);
/* 5143 */         out.write("\n<br>\n</div>\n");
/* 5144 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 5145 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5149 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 5150 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5151 */       return true;
/*      */     }
/* 5153 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5154 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5159 */     PageContext pageContext = _jspx_page_context;
/* 5160 */     JspWriter out = _jspx_page_context.getOut();
/* 5161 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 5162 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 5164 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5165 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 5166 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 5168 */     _jspx_th_c_005fif_005f5.setTest("${empty param.showadddiv && param.showadddiv!='true'}");
/* 5169 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 5170 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 5172 */         out.write("\n<div id=\"addprocess\" style=\"DISPLAY: none\">\n");
/* 5173 */         JspRuntimeLibrary.include(request, response, "/jsp/HostResourceConfig.jsp?addProcesScreen=true&addProcess=true", out, false);
/* 5174 */         out.write("\n<br>\n</div>\n");
/* 5175 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 5176 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5180 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 5181 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5182 */       return true;
/*      */     }
/* 5184 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5185 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fempty_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5190 */     PageContext pageContext = _jspx_page_context;
/* 5191 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5193 */     EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 5194 */     _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 5195 */     _jspx_th_logic_005fempty_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5197 */     _jspx_th_logic_005fempty_005f1.setName("systeminfo");
/* 5198 */     int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 5199 */     if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */       for (;;) {
/* 5201 */         out.write("\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">Host Name</td>\n\t\t\t\t<td class=\"monitorinfoeven\">-&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">Host OS</td>\n\t\t\t\t<td class=\"monitorinfoodd\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">Last Polled at</td>\n\t\t\t\t<td class=\"monitorinfoeven\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">Next Poll at</td>\n\t\t\t\t<td class=\"monitorinfoodd\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 5202 */         int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 5203 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5207 */     if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 5208 */       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 5209 */       return true;
/*      */     }
/* 5211 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 5212 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5217 */     PageContext pageContext = _jspx_page_context;
/* 5218 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5220 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5221 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 5222 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5224 */     _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 5225 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 5226 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 5228 */         out.write("\n\t\t\t");
/* 5229 */         if (_jspx_meth_c_005fif_005f6(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/* 5230 */           return true;
/* 5231 */         out.write("\n                         ");
/* 5232 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 5233 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5237 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 5238 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 5239 */       return true;
/*      */     }
/* 5241 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 5242 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5247 */     PageContext pageContext = _jspx_page_context;
/* 5248 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5250 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5251 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 5252 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 5254 */     _jspx_th_c_005fif_005f6.setTest("${empty cpudata && showdata=='1'}");
/* 5255 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 5256 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 5258 */         out.write("\n\t\t\t\n\t\t\t<div align=\"center\"><a href=\"javascript:toggleDiv('edit')\"><img src=\"../images/getmoredata_host.gif\"  hspace=\"5\" vspace=\"15\" border=\"0\"></a></div>\n\t\t\t");
/* 5259 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 5260 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5264 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 5265 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5266 */       return true;
/*      */     }
/* 5268 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5269 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5274 */     PageContext pageContext = _jspx_page_context;
/* 5275 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5277 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5278 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5279 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5281 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 5282 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5283 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5284 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5285 */       return true;
/*      */     }
/* 5287 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5288 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5293 */     PageContext pageContext = _jspx_page_context;
/* 5294 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5296 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5297 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5298 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5300 */     _jspx_th_c_005fout_005f1.setValue("${param.displayname}");
/* 5301 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5302 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5303 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5304 */       return true;
/*      */     }
/* 5306 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5307 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5312 */     PageContext pageContext = _jspx_page_context;
/* 5313 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5315 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5316 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5317 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5319 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 5320 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5321 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5322 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5323 */       return true;
/*      */     }
/* 5325 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5326 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5331 */     PageContext pageContext = _jspx_page_context;
/* 5332 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5334 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5335 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5336 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5338 */     _jspx_th_c_005fout_005f3.setValue("${param.displayname}");
/* 5339 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5340 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5341 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5342 */       return true;
/*      */     }
/* 5344 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5345 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5350 */     PageContext pageContext = _jspx_page_context;
/* 5351 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5353 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5354 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 5355 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5357 */     _jspx_th_c_005fif_005f8.setTest("${!empty cpudata && cpudata=='true'}");
/* 5358 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 5359 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 5361 */         out.write("\n          <td width=\"50%\" height=\"29\" class=\"tableheadingtrans\" >CPU Utilization \n             - Last One Hour<a name=\"CPU Utilization\"></a>  </td>\n        <td width=\"50%\" height=\"29\" class=\"tableheading\">Response Time - Last \n          One Hour&nbsp;</td>\n\t\t\t  ");
/* 5362 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 5363 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5367 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 5368 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 5369 */       return true;
/*      */     }
/* 5371 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 5372 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5377 */     PageContext pageContext = _jspx_page_context;
/* 5378 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5380 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5381 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 5382 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5384 */     _jspx_th_c_005fif_005f9.setTest("${(empty cpudata && showdata=='1') || (empty cpudata && showdata=='2')}");
/* 5385 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 5386 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 5388 */         out.write("\t  \n\t\t<td width=\"100%\" height=\"29\" class=\"tableheadingtrans\" >Response Time - Last One Hour&nbsp;</td>\n\t\t");
/* 5389 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 5390 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5394 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 5395 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 5396 */       return true;
/*      */     }
/* 5398 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 5399 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5404 */     PageContext pageContext = _jspx_page_context;
/* 5405 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5407 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5408 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5409 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 5411 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 5412 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5413 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5414 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5415 */       return true;
/*      */     }
/* 5417 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5418 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5423 */     PageContext pageContext = _jspx_page_context;
/* 5424 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5426 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5427 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5428 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 5430 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 5431 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5432 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5433 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5434 */       return true;
/*      */     }
/* 5436 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5437 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005ftimechart_005f0(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5442 */     PageContext pageContext = _jspx_page_context;
/* 5443 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5445 */     TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 5446 */     _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 5447 */     _jspx_th_awolf_005ftimechart_005f0.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 5449 */     _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("cpugraph");
/*      */     
/* 5451 */     _jspx_th_awolf_005ftimechart_005f0.setWidth("310");
/*      */     
/* 5453 */     _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */     
/* 5455 */     _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */     
/* 5457 */     _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel("Time");
/*      */     
/* 5459 */     _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel("Value in %");
/* 5460 */     int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 5461 */     if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 5462 */       if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 5463 */         out = _jspx_page_context.pushBody();
/* 5464 */         _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 5465 */         _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5468 */         out.write(" \n                ");
/* 5469 */         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 5470 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5473 */       if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 5474 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5477 */     if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 5478 */       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 5479 */       return true;
/*      */     }
/* 5481 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 5482 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5487 */     PageContext pageContext = _jspx_page_context;
/* 5488 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5490 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5491 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5492 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 5494 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 5495 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5496 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5497 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5498 */       return true;
/*      */     }
/* 5500 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5501 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5506 */     PageContext pageContext = _jspx_page_context;
/* 5507 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5509 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5510 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5511 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 5513 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 5514 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5515 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5516 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5517 */       return true;
/*      */     }
/* 5519 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5520 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005ftimechart_005f1(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5525 */     PageContext pageContext = _jspx_page_context;
/* 5526 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5528 */     TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 5529 */     _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 5530 */     _jspx_th_awolf_005ftimechart_005f1.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 5532 */     _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("perfgraph");
/*      */     
/* 5534 */     _jspx_th_awolf_005ftimechart_005f1.setWidth("320");
/*      */     
/* 5536 */     _jspx_th_awolf_005ftimechart_005f1.setHeight("170");
/*      */     
/* 5538 */     _jspx_th_awolf_005ftimechart_005f1.setLegend("false");
/*      */     
/* 5540 */     _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel("Time");
/*      */     
/* 5542 */     _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel("Response Time in ms");
/* 5543 */     int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 5544 */     if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 5545 */       if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 5546 */         out = _jspx_page_context.pushBody();
/* 5547 */         _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 5548 */         _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5551 */         out.write(10);
/* 5552 */         out.write(9);
/* 5553 */         out.write(9);
/* 5554 */         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 5555 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5558 */       if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 5559 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5562 */     if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 5563 */       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 5564 */       return true;
/*      */     }
/* 5566 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 5567 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5572 */     PageContext pageContext = _jspx_page_context;
/* 5573 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5575 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5576 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 5577 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 5578 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 5579 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 5580 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 5581 */         out = _jspx_page_context.pushBody();
/* 5582 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 5583 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5586 */         out.write("table.heading.attribute");
/* 5587 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 5588 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5591 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 5592 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5595 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 5596 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5597 */       return true;
/*      */     }
/* 5599 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5600 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5605 */     PageContext pageContext = _jspx_page_context;
/* 5606 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5608 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5609 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 5610 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 5611 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 5612 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 5613 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 5614 */         out = _jspx_page_context.pushBody();
/* 5615 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 5616 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5619 */         out.write("table.heading.value");
/* 5620 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 5621 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5624 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 5625 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5628 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 5629 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5630 */       return true;
/*      */     }
/* 5632 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5633 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5638 */     PageContext pageContext = _jspx_page_context;
/* 5639 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5641 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5642 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 5643 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 5644 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 5645 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 5646 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 5647 */         out = _jspx_page_context.pushBody();
/* 5648 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 5649 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5652 */         out.write("table.heading.status");
/* 5653 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 5654 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5657 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 5658 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5661 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 5662 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5663 */       return true;
/*      */     }
/* 5665 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5666 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5671 */     PageContext pageContext = _jspx_page_context;
/* 5672 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5674 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5675 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 5676 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 5677 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 5678 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 5679 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 5680 */         out = _jspx_page_context.pushBody();
/* 5681 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 5682 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5685 */         out.write("table.heading.attribute");
/* 5686 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 5687 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5690 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 5691 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5694 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 5695 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5696 */       return true;
/*      */     }
/* 5698 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5699 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5704 */     PageContext pageContext = _jspx_page_context;
/* 5705 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5707 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5708 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 5709 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 5710 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 5711 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 5712 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 5713 */         out = _jspx_page_context.pushBody();
/* 5714 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 5715 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5718 */         out.write("table.heading.value");
/* 5719 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 5720 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5723 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 5724 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5727 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 5728 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5729 */       return true;
/*      */     }
/* 5731 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5737 */     PageContext pageContext = _jspx_page_context;
/* 5738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5740 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5741 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 5742 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 5743 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 5744 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 5745 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 5746 */         out = _jspx_page_context.pushBody();
/* 5747 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 5748 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5751 */         out.write("table.heading.status");
/* 5752 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 5753 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5756 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 5757 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5760 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 5761 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 5762 */       return true;
/*      */     }
/* 5764 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 5765 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5770 */     PageContext pageContext = _jspx_page_context;
/* 5771 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5773 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5774 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5775 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 5777 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 5778 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5779 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5780 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5781 */       return true;
/*      */     }
/* 5783 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5784 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5789 */     PageContext pageContext = _jspx_page_context;
/* 5790 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5792 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5793 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5794 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 5796 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 5797 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5798 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5799 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5800 */       return true;
/*      */     }
/* 5802 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5803 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005ftimechart_005f2(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5808 */     PageContext pageContext = _jspx_page_context;
/* 5809 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5811 */     TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 5812 */     _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 5813 */     _jspx_th_awolf_005ftimechart_005f2.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 5815 */     _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("perfgraph");
/*      */     
/* 5817 */     _jspx_th_awolf_005ftimechart_005f2.setWidth("320");
/*      */     
/* 5819 */     _jspx_th_awolf_005ftimechart_005f2.setHeight("170");
/*      */     
/* 5821 */     _jspx_th_awolf_005ftimechart_005f2.setLegend("false");
/*      */     
/* 5823 */     _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel("Time");
/*      */     
/* 5825 */     _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel("Response Time(ms)");
/* 5826 */     int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 5827 */     if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 5828 */       if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 5829 */         out = _jspx_page_context.pushBody();
/* 5830 */         _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 5831 */         _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5834 */         out.write("\n\t  ");
/* 5835 */         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 5836 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5839 */       if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 5840 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5843 */     if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 5844 */       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 5845 */       return true;
/*      */     }
/* 5847 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 5848 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5853 */     PageContext pageContext = _jspx_page_context;
/* 5854 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5856 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5857 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 5858 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fif_005f11);
/* 5859 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 5860 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 5861 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 5862 */         out = _jspx_page_context.pushBody();
/* 5863 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 5864 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5867 */         out.write("table.heading.attribute");
/* 5868 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 5869 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5872 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 5873 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5876 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 5877 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 5878 */       return true;
/*      */     }
/* 5880 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 5881 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5886 */     PageContext pageContext = _jspx_page_context;
/* 5887 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5889 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5890 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 5891 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fif_005f11);
/* 5892 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 5893 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 5894 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 5895 */         out = _jspx_page_context.pushBody();
/* 5896 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 5897 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5900 */         out.write("table.heading.value");
/* 5901 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 5902 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5905 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 5906 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5909 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 5910 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 5911 */       return true;
/*      */     }
/* 5913 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 5914 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5919 */     PageContext pageContext = _jspx_page_context;
/* 5920 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5922 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5923 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 5924 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fif_005f11);
/* 5925 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 5926 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 5927 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 5928 */         out = _jspx_page_context.pushBody();
/* 5929 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 5930 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5933 */         out.write("table.heading.status");
/* 5934 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 5935 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5938 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 5939 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5942 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 5943 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 5944 */       return true;
/*      */     }
/* 5946 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 5947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5952 */     PageContext pageContext = _jspx_page_context;
/* 5953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5955 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5956 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 5957 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 5959 */     _jspx_th_c_005fif_005f12.setTest("${empty cpudata && showdata=='2'}");
/* 5960 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 5961 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 5963 */         out.write("\n \t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"bborder\">\n        <tr><td></td></tr>\n        </table>\n        <br>\n \t");
/* 5964 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 5965 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5969 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 5970 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 5971 */       return true;
/*      */     }
/* 5973 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 5974 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5979 */     PageContext pageContext = _jspx_page_context;
/* 5980 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5982 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5983 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 5984 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5986 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 5987 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 5988 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 5989 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5990 */       return true;
/*      */     }
/* 5992 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5993 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5998 */     PageContext pageContext = _jspx_page_context;
/* 5999 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6001 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6002 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 6003 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6005 */     _jspx_th_c_005fout_005f11.setValue("${param.resourceid}");
/* 6006 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 6007 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 6008 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6009 */       return true;
/*      */     }
/* 6011 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6012 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005ftimechart_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6017 */     PageContext pageContext = _jspx_page_context;
/* 6018 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6020 */     TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 6021 */     _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 6022 */     _jspx_th_awolf_005ftimechart_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6024 */     _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("memgraph");
/*      */     
/* 6026 */     _jspx_th_awolf_005ftimechart_005f3.setWidth("320");
/*      */     
/* 6028 */     _jspx_th_awolf_005ftimechart_005f3.setHeight("170");
/*      */     
/* 6030 */     _jspx_th_awolf_005ftimechart_005f3.setLegend("true");
/*      */     
/* 6032 */     _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel("Time");
/*      */     
/* 6034 */     _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel("Value in %");
/* 6035 */     int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 6036 */     if (_jspx_eval_awolf_005ftimechart_005f3 != 0) {
/* 6037 */       if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 6038 */         out = _jspx_page_context.pushBody();
/* 6039 */         _jspx_th_awolf_005ftimechart_005f3.setBodyContent((BodyContent)out);
/* 6040 */         _jspx_th_awolf_005ftimechart_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6043 */         out.write("          \n                   ");
/* 6044 */         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f3.doAfterBody();
/* 6045 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6048 */       if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 6049 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6052 */     if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 6053 */       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 6054 */       return true;
/*      */     }
/* 6056 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 6057 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6062 */     PageContext pageContext = _jspx_page_context;
/* 6063 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6065 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6066 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 6067 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 6068 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 6069 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 6070 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6071 */         out = _jspx_page_context.pushBody();
/* 6072 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 6073 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6076 */         out.write("table.heading.status");
/* 6077 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 6078 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6081 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6082 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6085 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 6086 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6087 */       return true;
/*      */     }
/* 6089 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6090 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6095 */     PageContext pageContext = _jspx_page_context;
/* 6096 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6098 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6099 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 6100 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 6101 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 6102 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 6103 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6104 */         out = _jspx_page_context.pushBody();
/* 6105 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 6106 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6109 */         out.write("table.heading.attribute");
/* 6110 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 6111 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6114 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6115 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6118 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 6119 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6120 */       return true;
/*      */     }
/* 6122 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6123 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6128 */     PageContext pageContext = _jspx_page_context;
/* 6129 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6131 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6132 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 6133 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 6134 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 6135 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 6136 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 6137 */         out = _jspx_page_context.pushBody();
/* 6138 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 6139 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6142 */         out.write("table.heading.status");
/* 6143 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 6144 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6147 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 6148 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6151 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 6152 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6153 */       return true;
/*      */     }
/* 6155 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6156 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005ftimechart_005f4(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6161 */     PageContext pageContext = _jspx_page_context;
/* 6162 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6164 */     TimeChart _jspx_th_awolf_005ftimechart_005f4 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 6165 */     _jspx_th_awolf_005ftimechart_005f4.setPageContext(_jspx_page_context);
/* 6166 */     _jspx_th_awolf_005ftimechart_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6168 */     _jspx_th_awolf_005ftimechart_005f4.setDataSetProducer("sysgraph");
/*      */     
/* 6170 */     _jspx_th_awolf_005ftimechart_005f4.setWidth("300");
/*      */     
/* 6172 */     _jspx_th_awolf_005ftimechart_005f4.setHeight("170");
/*      */     
/* 6174 */     _jspx_th_awolf_005ftimechart_005f4.setLegend("true");
/*      */     
/* 6176 */     _jspx_th_awolf_005ftimechart_005f4.setXaxisLabel("Time");
/*      */     
/* 6178 */     _jspx_th_awolf_005ftimechart_005f4.setYaxisLabel("Number of Jobs");
/* 6179 */     int _jspx_eval_awolf_005ftimechart_005f4 = _jspx_th_awolf_005ftimechart_005f4.doStartTag();
/* 6180 */     if (_jspx_eval_awolf_005ftimechart_005f4 != 0) {
/* 6181 */       if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 6182 */         out = _jspx_page_context.pushBody();
/* 6183 */         _jspx_th_awolf_005ftimechart_005f4.setBodyContent((BodyContent)out);
/* 6184 */         _jspx_th_awolf_005ftimechart_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6187 */         out.write(" \n        ");
/* 6188 */         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f4.doAfterBody();
/* 6189 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6192 */       if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 6193 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6196 */     if (_jspx_th_awolf_005ftimechart_005f4.doEndTag() == 5) {
/* 6197 */       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4);
/* 6198 */       return true;
/*      */     }
/* 6200 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4);
/* 6201 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6206 */     PageContext pageContext = _jspx_page_context;
/* 6207 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6209 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6210 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 6211 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6213 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 6214 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 6215 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 6216 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6217 */       return true;
/*      */     }
/* 6219 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6220 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6225 */     PageContext pageContext = _jspx_page_context;
/* 6226 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6228 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6229 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 6230 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6232 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 6233 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 6234 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 6236 */         out.write("\n\t\t<td width=\"28%\" height=\"31\" align=\"right\" class=\"tableheading\">&nbsp;<a href=\"javascript:alertUser()\" class=\"bodytextboldwhiteun\">Add\n\t\t          New Process</a>&nbsp;</td>\n\t\t");
/* 6237 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 6238 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6242 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 6243 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 6244 */       return true;
/*      */     }
/* 6246 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 6247 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6252 */     PageContext pageContext = _jspx_page_context;
/* 6253 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6255 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6256 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 6257 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 6259 */     _jspx_th_c_005fout_005f13.setValue("${param.resourceid}");
/* 6260 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 6261 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 6262 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6263 */       return true;
/*      */     }
/* 6265 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6266 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f4(JspTag _jspx_th_logic_005fnotEmpty_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6271 */     PageContext pageContext = _jspx_page_context;
/* 6272 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6274 */     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6275 */     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 6276 */     _jspx_th_logic_005fpresent_005f4.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f2);
/*      */     
/* 6278 */     _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 6279 */     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 6280 */     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */       for (;;) {
/* 6282 */         out.write("\n\t  <a href=\"javascript:alertUser();\" class=\"staticlinks\">Delete</a>\n\t ");
/* 6283 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 6284 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6288 */     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 6289 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 6290 */       return true;
/*      */     }
/* 6292 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 6293 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f5(JspTag _jspx_th_logic_005fnotEmpty_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6298 */     PageContext pageContext = _jspx_page_context;
/* 6299 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6301 */     PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6302 */     _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 6303 */     _jspx_th_logic_005fpresent_005f5.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f2);
/*      */     
/* 6305 */     _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 6306 */     int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 6307 */     if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */       for (;;) {
/* 6309 */         out.write("\n\t <a href=\"javascript:removeMonitors();\" class=\"staticlinks\">Delete</a>\n\t ");
/* 6310 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 6311 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6315 */     if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 6316 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 6317 */       return true;
/*      */     }
/* 6319 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 6320 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f6(JspTag _jspx_th_logic_005fnotEmpty_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6325 */     PageContext pageContext = _jspx_page_context;
/* 6326 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6328 */     PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6329 */     _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 6330 */     _jspx_th_logic_005fpresent_005f6.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f2);
/*      */     
/* 6332 */     _jspx_th_logic_005fpresent_005f6.setRole("ADMIN");
/* 6333 */     int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 6334 */     if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */       for (;;) {
/* 6336 */         out.write("\n\t <td width=\"12%\" height=\"28\"  class=\"columnheading\">Edit</td>\n          ");
/* 6337 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 6338 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6342 */     if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 6343 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 6344 */       return true;
/*      */     }
/* 6346 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 6347 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fempty_005f2(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6352 */     PageContext pageContext = _jspx_page_context;
/* 6353 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6355 */     EmptyTag _jspx_th_logic_005fempty_005f2 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 6356 */     _jspx_th_logic_005fempty_005f2.setPageContext(_jspx_page_context);
/* 6357 */     _jspx_th_logic_005fempty_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6359 */     _jspx_th_logic_005fempty_005f2.setName("process");
/* 6360 */     int _jspx_eval_logic_005fempty_005f2 = _jspx_th_logic_005fempty_005f2.doStartTag();
/* 6361 */     if (_jspx_eval_logic_005fempty_005f2 != 0) {
/*      */       for (;;) {
/* 6363 */         out.write("\n\t  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t \t<tr>\n\t \t<td height=\"15\" align=\"right\" class=\"tablebottom\"><a href=\"#top1\" class=\"staticlinks\">Top </a>&nbsp;</td>\n\t \t</tr>\n\t</table>\n\t ");
/* 6364 */         int evalDoAfterBody = _jspx_th_logic_005fempty_005f2.doAfterBody();
/* 6365 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6369 */     if (_jspx_th_logic_005fempty_005f2.doEndTag() == 5) {
/* 6370 */       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 6371 */       return true;
/*      */     }
/* 6373 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 6374 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_logic_005fiterate_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6379 */     PageContext pageContext = _jspx_page_context;
/* 6380 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6382 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6383 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 6384 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_logic_005fiterate_005f3);
/*      */     
/* 6386 */     _jspx_th_c_005fout_005f14.setValue("${param.haid}");
/* 6387 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 6388 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 6389 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6390 */       return true;
/*      */     }
/* 6392 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6393 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_logic_005fiterate_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6398 */     PageContext pageContext = _jspx_page_context;
/* 6399 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6401 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6402 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 6403 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_logic_005fiterate_005f3);
/*      */     
/* 6405 */     _jspx_th_c_005fout_005f15.setValue("${param.haid}");
/* 6406 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 6407 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 6408 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6409 */       return true;
/*      */     }
/* 6411 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6412 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fpiechart_005f1(JspTag _jspx_th_logic_005fiterate_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6417 */     PageContext pageContext = _jspx_page_context;
/* 6418 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6420 */     AMWolf _jspx_th_awolf_005fpiechart_005f1 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 6421 */     _jspx_th_awolf_005fpiechart_005f1.setPageContext(_jspx_page_context);
/* 6422 */     _jspx_th_awolf_005fpiechart_005f1.setParent((Tag)_jspx_th_logic_005fiterate_005f3);
/*      */     
/* 6424 */     _jspx_th_awolf_005fpiechart_005f1.setDataSetProducer("wlsGraph");
/*      */     
/* 6426 */     _jspx_th_awolf_005fpiechart_005f1.setWidth("200");
/*      */     
/* 6428 */     _jspx_th_awolf_005fpiechart_005f1.setHeight("130");
/*      */     
/* 6430 */     _jspx_th_awolf_005fpiechart_005f1.setLegend("true");
/*      */     
/* 6432 */     _jspx_th_awolf_005fpiechart_005f1.setUnits("%");
/*      */     
/* 6434 */     _jspx_th_awolf_005fpiechart_005f1.setUrl(true);
/*      */     
/* 6436 */     _jspx_th_awolf_005fpiechart_005f1.setDecimal(true);
/* 6437 */     int _jspx_eval_awolf_005fpiechart_005f1 = _jspx_th_awolf_005fpiechart_005f1.doStartTag();
/* 6438 */     if (_jspx_eval_awolf_005fpiechart_005f1 != 0) {
/* 6439 */       if (_jspx_eval_awolf_005fpiechart_005f1 != 1) {
/* 6440 */         out = _jspx_page_context.pushBody();
/* 6441 */         _jspx_th_awolf_005fpiechart_005f1.setBodyContent((BodyContent)out);
/* 6442 */         _jspx_th_awolf_005fpiechart_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6445 */         out.write(" \n                    ");
/* 6446 */         if (_jspx_meth_awolf_005fmap_005f1(_jspx_th_awolf_005fpiechart_005f1, _jspx_page_context))
/* 6447 */           return true;
/* 6448 */         out.write(32);
/* 6449 */         int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f1.doAfterBody();
/* 6450 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6453 */       if (_jspx_eval_awolf_005fpiechart_005f1 != 1) {
/* 6454 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6457 */     if (_jspx_th_awolf_005fpiechart_005f1.doEndTag() == 5) {
/* 6458 */       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f1);
/* 6459 */       return true;
/*      */     }
/* 6461 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f1);
/* 6462 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f1(JspTag _jspx_th_awolf_005fpiechart_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6467 */     PageContext pageContext = _jspx_page_context;
/* 6468 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6470 */     Property _jspx_th_awolf_005fmap_005f1 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 6471 */     _jspx_th_awolf_005fmap_005f1.setPageContext(_jspx_page_context);
/* 6472 */     _jspx_th_awolf_005fmap_005f1.setParent((Tag)_jspx_th_awolf_005fpiechart_005f1);
/*      */     
/* 6474 */     _jspx_th_awolf_005fmap_005f1.setId("color");
/* 6475 */     int _jspx_eval_awolf_005fmap_005f1 = _jspx_th_awolf_005fmap_005f1.doStartTag();
/* 6476 */     if (_jspx_eval_awolf_005fmap_005f1 != 0) {
/* 6477 */       if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 6478 */         out = _jspx_page_context.pushBody();
/* 6479 */         _jspx_th_awolf_005fmap_005f1.setBodyContent((BodyContent)out);
/* 6480 */         _jspx_th_awolf_005fmap_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6483 */         out.write(32);
/* 6484 */         if (_jspx_meth_awolf_005fparam_005f2(_jspx_th_awolf_005fmap_005f1, _jspx_page_context))
/* 6485 */           return true;
/* 6486 */         out.write(" \n                    ");
/* 6487 */         if (_jspx_meth_awolf_005fparam_005f3(_jspx_th_awolf_005fmap_005f1, _jspx_page_context))
/* 6488 */           return true;
/* 6489 */         out.write(32);
/* 6490 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f1.doAfterBody();
/* 6491 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6494 */       if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 6495 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6498 */     if (_jspx_th_awolf_005fmap_005f1.doEndTag() == 5) {
/* 6499 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1);
/* 6500 */       return true;
/*      */     }
/* 6502 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1);
/* 6503 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f2(JspTag _jspx_th_awolf_005fmap_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6508 */     PageContext pageContext = _jspx_page_context;
/* 6509 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6511 */     AMParam _jspx_th_awolf_005fparam_005f2 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 6512 */     _jspx_th_awolf_005fparam_005f2.setPageContext(_jspx_page_context);
/* 6513 */     _jspx_th_awolf_005fparam_005f2.setParent((Tag)_jspx_th_awolf_005fmap_005f1);
/*      */     
/* 6515 */     _jspx_th_awolf_005fparam_005f2.setName("1");
/*      */     
/* 6517 */     _jspx_th_awolf_005fparam_005f2.setValue("#29FF29");
/* 6518 */     int _jspx_eval_awolf_005fparam_005f2 = _jspx_th_awolf_005fparam_005f2.doStartTag();
/* 6519 */     if (_jspx_th_awolf_005fparam_005f2.doEndTag() == 5) {
/* 6520 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2);
/* 6521 */       return true;
/*      */     }
/* 6523 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2);
/* 6524 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f3(JspTag _jspx_th_awolf_005fmap_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6529 */     PageContext pageContext = _jspx_page_context;
/* 6530 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6532 */     AMParam _jspx_th_awolf_005fparam_005f3 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 6533 */     _jspx_th_awolf_005fparam_005f3.setPageContext(_jspx_page_context);
/* 6534 */     _jspx_th_awolf_005fparam_005f3.setParent((Tag)_jspx_th_awolf_005fmap_005f1);
/*      */     
/* 6536 */     _jspx_th_awolf_005fparam_005f3.setName("0");
/*      */     
/* 6538 */     _jspx_th_awolf_005fparam_005f3.setValue("#FF0000");
/* 6539 */     int _jspx_eval_awolf_005fparam_005f3 = _jspx_th_awolf_005fparam_005f3.doStartTag();
/* 6540 */     if (_jspx_th_awolf_005fparam_005f3.doEndTag() == 5) {
/* 6541 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3);
/* 6542 */       return true;
/*      */     }
/* 6544 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3);
/* 6545 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6550 */     PageContext pageContext = _jspx_page_context;
/* 6551 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6553 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 6554 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 6555 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 6556 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 6557 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 6559 */         out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr> \n    <td align=\"center\" class=\"bodytextbold11\"><a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 6560 */         if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 6561 */           return true;
/* 6562 */         if (_jspx_meth_c_005fif_005f14(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 6563 */           return true;
/* 6564 */         out.write("\" class=\"staticlinks\">Goto Snapshot View</a></td>\n  </tr>\n</table>\n");
/* 6565 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 6566 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6570 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 6571 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 6572 */       return true;
/*      */     }
/* 6574 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 6575 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6580 */     PageContext pageContext = _jspx_page_context;
/* 6581 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6583 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6584 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 6585 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 6587 */     _jspx_th_c_005fout_005f16.setValue("${param.resourceid}");
/* 6588 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 6589 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 6590 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6591 */       return true;
/*      */     }
/* 6593 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6594 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f14(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6599 */     PageContext pageContext = _jspx_page_context;
/* 6600 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6602 */     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6603 */     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 6604 */     _jspx_th_c_005fif_005f14.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 6606 */     _jspx_th_c_005fif_005f14.setTest("${ !empty param.haid && param.haid!='null' }");
/* 6607 */     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 6608 */     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */       for (;;) {
/* 6610 */         out.write("&haid=");
/* 6611 */         if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fif_005f14, _jspx_page_context))
/* 6612 */           return true;
/* 6613 */         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 6614 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6618 */     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 6619 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 6620 */       return true;
/*      */     }
/* 6622 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 6623 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6628 */     PageContext pageContext = _jspx_page_context;
/* 6629 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6631 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6632 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 6633 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 6635 */     _jspx_th_c_005fout_005f17.setValue("${param.haid}");
/* 6636 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 6637 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 6638 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6639 */       return true;
/*      */     }
/* 6641 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6642 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_logic_005fiterate_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6647 */     PageContext pageContext = _jspx_page_context;
/* 6648 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6650 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6651 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 6652 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_logic_005fiterate_005f4);
/*      */     
/* 6654 */     _jspx_th_c_005fif_005f16.setTest("${!empty pageScope.url_responsetime}");
/* 6655 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 6656 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 6658 */         out.write("\n           ");
/* 6659 */         if (_jspx_meth_fmt_005fformatNumber_005f0(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 6660 */           return true;
/* 6661 */         out.write("\n           ");
/* 6662 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 6663 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6667 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 6668 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 6669 */       return true;
/*      */     }
/* 6671 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 6672 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f0(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6677 */     PageContext pageContext = _jspx_page_context;
/* 6678 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6680 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f0 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.get(FormatNumberTag.class);
/* 6681 */     _jspx_th_fmt_005fformatNumber_005f0.setPageContext(_jspx_page_context);
/* 6682 */     _jspx_th_fmt_005fformatNumber_005f0.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 6684 */     _jspx_th_fmt_005fformatNumber_005f0.setMaxFractionDigits("0");
/* 6685 */     int _jspx_eval_fmt_005fformatNumber_005f0 = _jspx_th_fmt_005fformatNumber_005f0.doStartTag();
/* 6686 */     if (_jspx_eval_fmt_005fformatNumber_005f0 != 0) {
/* 6687 */       if (_jspx_eval_fmt_005fformatNumber_005f0 != 1) {
/* 6688 */         out = _jspx_page_context.pushBody();
/* 6689 */         _jspx_th_fmt_005fformatNumber_005f0.setBodyContent((BodyContent)out);
/* 6690 */         _jspx_th_fmt_005fformatNumber_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6693 */         out.write(32);
/* 6694 */         if (_jspx_meth_c_005fout_005f18(_jspx_th_fmt_005fformatNumber_005f0, _jspx_page_context))
/* 6695 */           return true;
/* 6696 */         int evalDoAfterBody = _jspx_th_fmt_005fformatNumber_005f0.doAfterBody();
/* 6697 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6700 */       if (_jspx_eval_fmt_005fformatNumber_005f0 != 1) {
/* 6701 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6704 */     if (_jspx_th_fmt_005fformatNumber_005f0.doEndTag() == 5) {
/* 6705 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 6706 */       return true;
/*      */     }
/* 6708 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 6709 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_fmt_005fformatNumber_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6714 */     PageContext pageContext = _jspx_page_context;
/* 6715 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6717 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6718 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 6719 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_fmt_005fformatNumber_005f0);
/*      */     
/* 6721 */     _jspx_th_c_005fout_005f18.setValue("${pageScope.url_responsetime }");
/* 6722 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 6723 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 6724 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6725 */       return true;
/*      */     }
/* 6727 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6728 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_logic_005fiterate_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6733 */     PageContext pageContext = _jspx_page_context;
/* 6734 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6736 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6737 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 6738 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_logic_005fiterate_005f4);
/*      */     
/* 6740 */     _jspx_th_c_005fif_005f17.setTest("${empty pageScope.url_responsetime}");
/* 6741 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 6742 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 6744 */         out.write(45);
/* 6745 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 6746 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6750 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 6751 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 6752 */       return true;
/*      */     }
/* 6754 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 6755 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f18(JspTag _jspx_th_logic_005fiterate_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6760 */     PageContext pageContext = _jspx_page_context;
/* 6761 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6763 */     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6764 */     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 6765 */     _jspx_th_c_005fif_005f18.setParent((Tag)_jspx_th_logic_005fiterate_005f4);
/*      */     
/* 6767 */     _jspx_th_c_005fif_005f18.setTest("${!empty pageScope.url_responsetime}");
/* 6768 */     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 6769 */     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */       for (;;) {
/* 6771 */         out.write("ms            \n            ");
/* 6772 */         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 6773 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6777 */     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 6778 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 6779 */       return true;
/*      */     }
/* 6781 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 6782 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_logic_005fiterate_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6787 */     PageContext pageContext = _jspx_page_context;
/* 6788 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6790 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6791 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 6792 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_logic_005fiterate_005f4);
/*      */     
/* 6794 */     _jspx_th_c_005fout_005f19.setValue("${pageScope.url_contentlength }");
/* 6795 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 6796 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 6797 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6798 */       return true;
/*      */     }
/* 6800 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6801 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f6(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6806 */     PageContext pageContext = _jspx_page_context;
/* 6807 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6809 */     PutTag _jspx_th_tiles_005fput_005f6 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6810 */     _jspx_th_tiles_005fput_005f6.setPageContext(_jspx_page_context);
/* 6811 */     _jspx_th_tiles_005fput_005f6.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6813 */     _jspx_th_tiles_005fput_005f6.setName("footer");
/*      */     
/* 6815 */     _jspx_th_tiles_005fput_005f6.setValue("");
/* 6816 */     int _jspx_eval_tiles_005fput_005f6 = _jspx_th_tiles_005fput_005f6.doStartTag();
/* 6817 */     if (_jspx_th_tiles_005fput_005f6.doEndTag() == 5) {
/* 6818 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f6);
/* 6819 */       return true;
/*      */     }
/* 6821 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f6);
/* 6822 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HostResourceToSend_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */